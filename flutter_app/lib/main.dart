import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData( // 顶部栏背景色
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Hello World',
              style: TextStyle(
                fontStyle: FontStyle.italic,
                fontSize: 20.0,
                color: Colors.red
              ),
              textAlign: TextAlign.center,
            ),
            Text(
              'inherit: 为 false 的时候不显示',
              style: TextStyle(
                fontSize: 18.0,
                color: Colors.redAccent,
                inherit: true,
              ),
            ),
            Text(
              'color/fontSize: 字体颜色，字号等',
              style: TextStyle(
                color: Color.fromARGB(255, 150, 150, 150),
                fontSize: 22.0,
              ),
            ),
            Text(
              'fontWeight: 字重',
              style: TextStyle(
                fontSize: 18.0,
                color: Colors.redAccent,
                fontWeight: FontWeight.w700
              ),
            ),
            Text(
              'letterSpacing: 字符间距',
              style: TextStyle(
                letterSpacing: 10.0
              ),
            ),
            Text(
              'wordSpacing world, hello: 字或单词 间距',
              style: TextStyle(
                wordSpacing: 15.0
              ),
            ),
            Text(
              'text line 一行 值为TextBaseline.alphabetic',
              style: TextStyle(textBaseline: TextBaseline.alphabetic),
            ),
            Text(
              'textBaseline:这一行的值为TextBaseline.ideographic',
              style: TextStyle(textBaseline: TextBaseline.ideographic),
            ),
            Text(
              'height: 用在Text控件上的时候，会乘以fontSize做为行高,所以这个值不能设置过大',
              style: TextStyle(
                height: 1.0,
              ),
            ),
            Text(
              'decoration: TextDecoration.overline 上划线',
              style: TextStyle(
                fontSize: 18.0,
                color: Colors.redAccent,
                decoration: TextDecoration.overline,
                decorationStyle: TextDecorationStyle.wavy
              ),
            ),
            Text(
              'decoration: TextDecoration.lineThrough 删除线',
              style: TextStyle(
                decoration: TextDecoration.lineThrough,
                decorationStyle: TextDecorationStyle.dashed
              ),
            ),
            Text(
              'decoration: TextDecoration.underline 下划线',
              style: TextStyle(
                fontSize: 18.0,
                color:Colors.redAccent,
                decoration: TextDecoration.underline,
                decorationStyle: TextDecorationStyle.dotted
              ),
            )
          ],
        ),
      ),
    );
  }
}
