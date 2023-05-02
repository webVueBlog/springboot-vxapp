package com.wxapp.app.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wxapp.app.common.RedisKey;
import com.wxapp.app.common.Result;
import com.wxapp.app.handler.UserThreadLocal;
import com.wxapp.app.mapper.UserMapper;
import com.wxapp.app.model.WXAuth;
import com.wxapp.app.model.WxUserInfo;
import com.wxapp.app.pojo.User;
import com.wxapp.app.pojo.dto.UserDto;
import com.wxapp.app.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserService {

    @Value("${wxmini.appid}")
    private String appid;

    @Value("${wxmini.secret}")
    private String secret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Result getSessionId(String code) {
        /**
         * 1. 拼接一个url，微信登录凭证校验接口
         * 2. 发起一个http的调用，获取微信的返回结果
         * 3. 存到redis
         * 4. 生成一个sessionId，返回给前端，作为当前需要登录用户的标识
         * 5. 生成一个sessionId，用户在点击微信登录的生活，我们可以标识是谁点击微信登录
         */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        String res = HttpUtil.get(replaceUrl);
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + uuid, res, 30, TimeUnit.MINUTES);
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", uuid);
        return Result.SUCCESS(map);
    }

    @Autowired
    private WxService wxService;

    @Resource
    private UserMapper userMapper;

    public Result authLogin(WXAuth wxAuth) {
        /**
         * 1. 通过wxAuth中的值，要对它进行解密
         * 2. 解密完成之后，会获取到微信用户信息 其中包含 openId，性别，昵称，头像等信息
         * 3. openId 是唯一的，需要去user表中查询openId是否存在，存在，已此用户的身份登录成功
         * 4. 不存在，新用户，注册流程，登录成功
         * 5. 使用jwt技术，生成一个token，提供给前端 token 令牌，用户在下次访问的时候，携带token来访问
         * 6. 后端通过对token的验证，找到此用户是否处于登录状态，以及是哪个用户登录的
         */
        try {
            log.info("获取请求参数："+wxAuth);
            String wxRes = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            log.info("信息："+wxRes);
            WxUserInfo wxUserInfo = JSON.parseObject(wxRes, WxUserInfo.class);
            String openId = wxUserInfo.getOpenId();
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId).last("limit 1"));
            UserDto userDto = new UserDto();
            userDto.from(wxUserInfo);
            if (user == null) {
                // 注册
                return this.register(userDto);
            } else {
                userDto.setId(user.getId());
                // 登录
                return this.login(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.FAIL();
    }

    private Result login(UserDto userDto) {
        String token = JWTUtils.sign(userDto.getId());
        userDto.setToken(token);
        userDto.setOpenId(null);
        userDto.setUnionId(null);
        //需要把token 存入redis，value存为 userDto，下次用户访问需要登录资源的时候，可以根据token拿到用户的详细信息
        //缓存
        redisTemplate.opsForValue().set(RedisKey.TOKEN+token, JSON.toJSONString(userDto), 7, TimeUnit.DAYS);
        return Result.SUCCESS(userDto);
    }

    private Result register(UserDto userDto) {
        // 注册之前 判断 用户是否存在
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        this.userMapper.insert(user);
        // return Result.SUCCESS(userDto);
        userDto.setId(user.getId());
        return this.login(userDto);
    }

    // 参数 String token
    public Result userinfo(Boolean refresh) {
        /**
         * 1. 根据token 来验证此token 是否有效
         * 2. refresh 如果为true 代表刷新 重新生成token和redis里面重新储存 续期
         * 3. false直接返回用户信息 redis中 查询出来 直接返回
         */
        /**
         token = token.replace("Bearer ", "");
         boolean verify = JWTUtils.verify(token);
         if (!verify) {
         return Result.FAIL("未登录");
         }
         String userJson = redisTemplate.opsForValue().get(RedisKey.TOKEN + token);
         if (StringUtils.isBlank(userJson)) {
         return Result.FAIL("未登录");
         }
         */
        UserDto userDto = UserThreadLocal.get();
        if (refresh) {
            String token = JWTUtils.sign(userDto.getId());
            userDto.setToken(token);
            redisTemplate.opsForValue().set(RedisKey.TOKEN+token, JSON.toJSONString(userDto), 7, TimeUnit.DAYS);
        }
        return Result.SUCCESS(userDto);
    }
}
