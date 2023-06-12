# springboot-vxapp

- SpringBoot2.X整合微信小程序登录
- 发送短信验证码-腾讯云
- 手机号，验证码登录
- 接口，按钮级别的权限验证
- 支持一个用户多个角色
- 使用token作为登录凭证，不使用session，避免跨域问题
- 文章增删改查操作接口
- 多模块服务
- springboot-swagger2添加swagger-ui

```java
// 比如 文章:查看/编辑/发布/删除
//@RequiresPermissions("article:add")
```