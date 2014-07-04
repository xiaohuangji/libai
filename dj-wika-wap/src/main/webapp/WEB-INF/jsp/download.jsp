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
<trial:assertcss files="/up/dj-mobile/vcard/css/download.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/js/download.js" /> 
  <script type="text/javascript">
    dj['setPageData']({
      url_com : 'http://bcs.duapp.com/weikaapp/vcard_v1.0_dl.apk'
    });
  </script>

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="/up/dj-mobile/vcard/css/download.css"/>
  <script type="text/javascript" src="/up/dj-mobile/vcard/js/download.js"></script> -->
  <title>下载微卡App</title>
      <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>${downloadinfo}</h1>
    <div class="p-show"></div>
    <a id="jp-showMask" class="i-btn" href="#">立即下载</a>
  </div>
</div>
</body>
</html>