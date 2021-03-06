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
          email : {
            reg : 'loginname',
            regErr : '请输入正确的大街网帐号',
            regEmpty : '请输入大街网帐号',
            //url : '/up/dj-mobile/vcard/json/ajax.php',
            //urlErr : '别人已经绑定了！'
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
  <title>大街网帐号登录</title>
     <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>使用大街网帐号登录微卡</h1>
    <form id="jp-form" action="/account/login-dajie-service" method="post" class="p-form-wrap">
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>大街网帐号邮箱</span>
          <div class="t-flex-item">
            <input name="email" type="email" placeholder="大街网帐号" maxlength="50" value="${email}" />
            <div class="error"></div>
          </div>
        </li>
        <li class="t-flex-wrap">
          <span>大街网登录密码</span>
          <div class="t-flex-item">
            <input name="password" type="password" placeholder="大街网登录密码"/>
               <c:if test="${not empty errmsg}">
           	 	<div class="error showError">${errmsg}</div>
            </c:if> 
            <c:if test="${empty errmsg}">
           	 	<div class="error"></div>
            </c:if> 
          </div>
        </li>
      </ul>
      <a id="jp-submit" class="p-btn i-btn" href="#">登录</a>
    </form>
  </div>
</div>
</body>
</html>