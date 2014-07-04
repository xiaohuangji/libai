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

<trial:assertcss files="/up/dj-mobile/vcard/css/setting.css"/>

  <!-- 本页样式 -->
  <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/setting.css"/>
  <title>设置</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <nav class="p-nav">
    <a href="/setting/privacy-settings">隐私设置</a>
    <a href="/setting/setting-password">修改密码</a>
    <a href="/account/rebind-mobile">修改手机号</a>
  </nav>
</div>
</body>
</html>