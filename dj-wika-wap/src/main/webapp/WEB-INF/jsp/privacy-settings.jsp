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

<trial:assertcss files="/up/dj-mobile/vcard/css/privacy-setting.css"/>

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/privacy-setting.css"/> -->
  <title>隐私设置</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>微卡提供了多项贴心的隐私选择，以确保你的个人隐私安全</h1>
    <form action="/setting/privacy-settings-service" method="post">
      <dl class="p-radio">
        <dt>手机号隐私设定</dt>
        <dd>
          <input name="tel" type="radio" id="fp-tel-01"  value=0 ${privacysettings.mobilePrivacy==0?'checked':''} />
          <label for="fp-tel-01"><i></i><span>所有人可见</span></label>
        </dd>
        <dd>
          <input name="tel" type="radio" id="fp-tel-02" value=1  ${privacysettings.mobilePrivacy==1?'checked':''} />
          <label for="fp-tel-02"><i></i><span>熟人可见</span></label>
        </dd>
        <dd>
          <input name="tel" type="radio" id="fp-tel-03"  value=2  ${privacysettings.mobilePrivacy==2?'checked':''}/>
          <label for="fp-tel-03"><i></i><span>仅自己可见</span></label>
        </dd>
      </dl>
      <dl class="p-radio">
        <dt>邮箱信息隐私设定</dt>
        <dd>
          <input name="mail" type="radio" id="fp-mail-01" value=0 ${privacysettings.emailPrivacy==0?'checked':''}/>
          <label for="fp-mail-01"><i></i><span>所有人可见</span></label>
        </dd>
        <dd>
          <input name="mail" type="radio" id="fp-mail-02" value=1 ${privacysettings.emailPrivacy==1?'checked':''}/>
          <label for="fp-mail-02"><i></i><span>熟人可见</span></label>
        </dd>
      </dl>
      <input type="submit" id="fp-submit" style="display:none;"/>
      <label for="fp-submit" class="p-btn i-btn">保存修改</label>
      <a class="i-btn-cancel" href="/setting/setting">取消</a>
    </form>
  </div>
</div>
</body>
</html>