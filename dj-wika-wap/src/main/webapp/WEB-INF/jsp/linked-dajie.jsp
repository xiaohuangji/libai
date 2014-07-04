<!DOCTYPE html>
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
  <!--#include virtual="/up/dj-mobile/vcard/include/base-style.html"-->


  <!-- 本页样式 -->
  <link rel="stylesheet" href="/up/dj-mobile/vcard/css/privacy-setting.css"/>
  <script type="text/javascript" src="/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="/up/dj-mobile/vcard/js/use-vilidate.js"></script>
  <script type="text/javascript" src="/up/dj-mobile/vcard/js/linked-dajie.js"></script>
  <script type="text/javascript">
    dj['setPageData']({
      validate : {
        strBtnID : 'jp-submit',
        strFormID : 'jp-form',
        nodeType : 'div',
        item : {
          password : {
            reg : 'password',
            regErr : '密码格式不对',
            regEmpty : '密码不能为空'
          }
        },
        radioName : 'change'
      }
    });
  </script>
  <title>手机号已经和大街已有帐号关联过的界面-微卡</title>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>我们检测到您的手机号<span>186****3375</span>已和大街网以下帐号关联，请确认是否直接使用大街帐号登录</h1>
    <form action="#" id="jp-form" method="post">
      <dl class="p-radio">
        <dd>
          <input name="change" type="radio" id="fp-change-01" checked/>
          <label for="fp-change-01"><i></i><span>大街网账号asdf@s3df.sdf</span></label>
        </dd>
        <dd>
          <input name="change" type="radio" id="fp-change-02"/>
          <label for="fp-change-02"><i></i><span>大街网账号asdfsdf@sdfsdf.sdf</span></label>
        </dd>
        <dd>
          <input name="change" type="radio" id="fp-change-03"/>
          <label for="fp-change-03"><i></i><span>不使用大街账号，只使用此手机注册</span></label>
        </dd>
      </dl>
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <div class="t-flex-item">
            <input id="jp-password" name="password" type="password" placeholder="大街网登录密码"/>
            <div class="error"></div>
          </div>
        </li>
      </ul>
      <a id="jp-submit" class="p-btn i-btn" href="#">登录领取</a>
    </form>
  </div>
</div>
</body>
</html>