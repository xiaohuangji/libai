<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="
    width=device-width,
    initial-scale=1,
    minimum-scale=1,
    maximum-scale=1,
    user-scalable=no">
  <!-- 项目公用静态文件 -->
  <%@include file="../include/base-style.html"%>

<trial:assertcss files="/up/dj-mobile/vcard/css/receive.css"/>
  <!-- 本页样式 -->
  <%@include file="../include/mycard-skin.html"%>
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/receive.css"/> -->
  <title>领取-微卡</title>
    <!-- 微信分享配置-->
  <%@include file="../include/shareweixin1.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <section class="i-mycard">
      <div id="jp-mycard"><span class="loading"></span></div>
      <div class="p-mask"></div>
    </section>
    <aside class="p-aside">
      <a class="i-btn" href="/account/login?userId=${userId}">我就是${userbase.name}，现在领取</a>
      <p>
        微卡——让每一张名片都能扫<br/>
        欢迎关注我们的微信公众号:<a href="#">微卡</a><br/>
        ==============================<br/>
        Apple Store或Google Play中搜索"微卡"<br/>
        下载<a href="/system/download?sId=5">微卡应用</a>，助力成功人生
      </p>
    </aside>
  </div>
</div>
</body>
</html>