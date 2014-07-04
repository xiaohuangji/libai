<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<trial:assertcss files="/up/dj-mobile/vcard/css/avatar-large.css"/>

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/avatar-large.css"/> -->
  <title>我的微卡-微卡</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin1.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <section class="p-avatar">
       <c:if test="${flag!=1}">
      		<img src="${avatarPicUrls.l}" />
       </c:if>
       <c:if test="${flag==1}">
      		<img src="${qrPicUrls.l}" />
       </c:if>
    </section>
    <aside class="p-aside">
      <p>下载App使用更多二维码头像模板</p>
      <a class="i-btn" href="/system/download?sId=7">立即下载</a>
      <div>
        <c:forEach var="qrt" items="${qrtlist}">
        <img src="${qrt.QTUrl}" />
        </c:forEach>
      </div>
    </aside>
  </div>
</div>
</body>
</html>