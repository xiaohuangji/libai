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

<trial:assertcss files="/up/dj-mobile/vcard/css/login.css,/up/dj-mobile/vcard/css/no-login-receive.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/login.js" />


  <!-- 本页样式 -->
   <%@include file="../include/mycard-skin.html"%>
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/login.css"/>
  <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/no-login-receive.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/login.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      validate : {
        strBtnID : 'jp-submit',
        strFormID : 'jp-form',
        nodeType : 'div',
        item : {
          password : {
            reg : 'password',
            regErr : '密码不对',
            regEmpty : '密码不能为空'
          }
        }
      }
    });
  </script>
  <title>未登陆领取-微卡</title>
  <!-- 微信分享配置-->
  <%@include file="../include/shareweixin1.html"%>
</head>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <div class="p-ad t-flex-wrap">
      <p class="t-flex-item">下载微卡App,获取更多微卡模板和专属二维码！还能帮你把名片保存在本地或分享到微信哦~ </p>
      <p><a href="/system/download?sId=1">下载微卡APP</a></p>
    </div>
    <section class="i-mycard">
      <div id="jp-mycard"><span class="loading"></span></div>
    </section>
    <aside class="p-aside-before jp-show-before" ${not empty errmsg?'style="display:none;"':''}>
      <a class="i-btn" href="http://mp.weixin.qq.com/mp/appmsg/show?__biz=MzA4OTE2NDYyNw==&appmsgid=10000084&itemidx=1&sign=1690645f2fa39a17ed79f9dd434d1328#wechat_redirect">我就是${userbase.name}，现在领取</a>
      <p>
        微卡——让每一张名片都能扫<br/>
        欢迎关注我们的微信公众号:<a href="#">微卡</a>
      </p>
    </aside>
    
       <aside class="p-aside jp-show-after" ${empty errmsg?'style="display:none;"':''} >
      <hgroup>
        <h1>轻松领取这张精英卡，为你所用！</h1>
        <h2>身份验证，使用你的大街网帐号登录</h2>
      </hgroup>
      <form id="jp-form" action="/account/login-dajie-service" method="post">
        <ul class="i-form-list">
          <li class="t-flex-wrap">
            <span>邮箱</span>
            <div class="t-flex-item">${userbase.email}</div>
            <input name="email" type="hidden" value="${userbase.email}" />
          </li>
          <li class="t-flex-wrap">
            <span>密码</span>
            <div class="t-flex-item">
              <input name="password" type="password" placeholder="大街网登录密码"/>
              <c:if test="${not empty errmsg}">
           	 	<div class="error showError">${errmsg}</div>
              </c:if>
            </div>
          </li>
        </ul>
        <a id="jp-submit" class="i-btn" href="#">登录验证</a>
        <a class="p-change" href="/account/login-dajie">不是你的帐号？使用其他帐号登录</a>
      </form>
    </aside>
    <nav class="p-foot-nav jp-show-before" ${not empty errmsg?'style="display:none;"':''}>
      <menu class="menu t-flex-wrap">
        <li class="t-flex-item select">
          <a href="/account/phone-number">制作我的微卡</a>
        </li>
        <li class="gap"></li>
        <li class="t-flex-item">
          <a href="/account/login-only">　　登录　　</a>
        </li>
      </menu>
    </nav>
  </div>

</div>
</body>
</html>