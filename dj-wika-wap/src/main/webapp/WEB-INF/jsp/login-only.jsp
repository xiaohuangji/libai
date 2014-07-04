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

<trial:assertjs files="/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/use-vilidate.js" />
<trial:assertcss files="/up/dj-mobile/vcard/css/phone-number.css"/>

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/phone-number.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/use-vilidate.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      validate : {
        strBtnID : 'jp-submit',
        strFormID : 'jp-form',
        nodeType : 'div',
        item : {
          tel : {
            reg : 'tel',
            regErr : '手机号格式不对',
            regEmpty : '手机号不能为空',
           //url : '/account/login-service',
            urlPlus :'tel password',
            urlErr1 : '登陆错误'
          },
          password : {
            reg : 'password',
            regErr : '密码格式不对',
            regEmpty : '密码不能为空'
          }
        }
      }
    });
  </script>
  <title>登录微卡</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>使用手机号登录微卡</h1>
    <form id="jp-form" action="/account/login-service" method="post" class="p-form-wrap">
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>手机号　</span>
          <div class="t-flex-item">
            <input name="tel" type="tel" placeholder="请输入手机号" maxlength="11" value="${tel}"/>
            <div class="error"></div>
          </div>
        </li>
        <li class="t-flex-wrap">
          <span>登录密码</span>
          <div class="t-flex-item">
            <input name="password" type="password" placeholder="登录密码"/>
             <c:if test="${not empty errmsg}">
           	 	<div class="error showError">${errmsg}</div>
            </c:if> 
            <c:if test="${empty errmsg}">
           	 	<div class="error"></div>
            </c:if>
          </div>
        </li>
      </ul>
      <p class="p-btn">忘记密码？点击此处 <a href="/setting/setting-find-password">忘记密码</a></p>
      <a id="jp-submit" class="p-btn i-btn" href="#">登录</a>
      <a class="p-btn i-btn-cancel" href="/account/phone-number">注册新帐号</a>
      <a class="p-btn" href="/account/login-dajie">使用大街网帐号登录</a>
    </form>
  </div>
</div>
</body>
</html>