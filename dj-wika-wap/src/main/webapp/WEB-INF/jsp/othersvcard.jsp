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
  <%@include file="../include/base-style.html"%>

<trial:assertcss files="/up/dj-mobile/vcard/css/people-i-know.css,/up/dj-mobile/vcard/css/no-login-receive.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/ajax-limit.js,/up/dj-mobile/vcard/js/no-login-receive.js"/>


  <!-- 本页样式 -->
  <%@include file="../include/mycard-skin.html"%>
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/people-i-know.css"/>
   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/no-login-receive.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/ajax-limit.js"></script> -->
	<script type="text/javascript">
    dj['setPageData']({
      reload : '/account/phone-number'
    });
  </script>
  <title>${userbase.name}的微卡</title>
      <!-- 微信分享配置-->
  <%@include file="../include/shareweixin3.html"%>
</head>
<body>
<div class="dj-base-wrap">
<!--     <div class="p-ad t-flex-wrap">
      <p class="t-flex-item">下载微卡App,获取更多微卡模板和专属二维码！还能帮你把名片保存在本地或分享到微信哦~ </p>
      <p><a href="/system/download?sId=1">下载微卡APP</a></p>
    </div> -->
  <div class="p-wrap">
    <section class="i-mycard">
      <div id="jp-mycard"><span class="loading"></span></div>
    </section>
    <aside class="p-aside">
      <a class="p-btn p-f11" href="http://mp.weixin.qq.com/mp/appmsg/show?__biz=MzA4OTE2NDYyNw==&appmsgid=10000087&itemidx=1&sign=b319f7ede049a2b60351bb828c2c37f8#wechat_redirect">关注公众帐号，立刻有惊喜！</a>
    
      <a class="i-btn p-btn" href="/account/phone-number">添加联系人</a>
      <a class="i-btn-cancel p-btn" href="/account/phone-number">制作我的微卡</a>
      <p>
        微卡——让每一张名片都能扫<br/>
        欢迎关注我们的微信公众号:<a href="http://mp.weixin.qq.com/mp/appmsg/show?__biz=MzA4OTE2NDYyNw==&appmsgid=10000087&itemidx=1&sign=b319f7ede049a2b60351bb828c2c37f8#wechat_redirect">微卡</a><br/>
        ==============================<br/>
        Apple Store或Google Play中搜索"微卡"<br/>
        下载<a href="/system/download?sId=5">微卡应用</a>，助力成功人生
      </p>
    </aside>
  </div>
      <nav class="p-foot-nav jp-show-before">
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
</body>
</html>