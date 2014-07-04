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

<trial:assertcss files="/up/dj-mobile/vcard/css/phone-number.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/use-vilidate.js" />


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
          password : {
            reg : 'password',
            regErr : '密码长度为4-20字符',
            regEmpty : '密码不能为空',
            url : '/up/dj-mobile/vcard/json/ajax.php',
            urlErr : '密码输入有误'
          },
          newpassword : {
            reg : 'fun:equal,password', // 逗号后面的是需要相等的元素的name，如改动这里需要一起修改
            regErr : '两次密码输入不一致',
            regEmpty : '密码不能为空'
          }
        }
      }
    });
  </script>
  <title>重新设置密码-微卡</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <form id="jp-form" action="/setting/setting-reset-password-service" method="post" class="p-form-wrap">
      <h1>重新设置密码</h1>
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>新密码　</span>
          <div class="t-flex-item">
            <input name="password" type="password" placeholder="新密码"/>
            <div class="error"></div>
          </div>
        </li>
        <li class="t-flex-wrap">
          <span>确认密码</span>
          <div class="t-flex-item">
            <input name="newpassword" type="password" placeholder="请再次输入密码"/>
            <div class="error"></div>
          </div>
        </li>
      </ul>
      <input name="tel" type="hidden" value="${tel}"/>
      <input name="code" type="hidden" value="${code}"/>
      <a id="jp-submit" class="p-btn i-btn" href="#">确定</a>
    </form>
  </div>
</div>
</body>
</html>