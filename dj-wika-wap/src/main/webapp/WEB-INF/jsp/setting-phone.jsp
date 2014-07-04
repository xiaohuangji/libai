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
          tel : {
            reg : 'tel',
            regErr : '手机号格式不对',
            regEmpty : '手机号不能为空',
           // url : '/up/dj-mobile/vcard/json/ajax.php',
            //urlErr : '别人已经绑定了！'
          }
        }
      }
    });
  </script>
  <title>重新绑定手机号-微卡</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap p-top-gap">
    <form id="jp-form" action="/account/rebind-mobile" method="post" class="p-form-wrap">
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>手机号</span>
          <div class="t-flex-item">
            <input name="tel" type="tel" placeholder="请输入要绑定的手机号" maxlength="11"/>
            <div class="error"></div>
          </div>
        </li>
      </ul>
      <a id="jp-submit" class="p-btn i-btn" href="#">下一步</a>
      <a class="p-btn i-btn-cancel" href="/setting/setting">取消</a>
    </form>
  </div>
</div>
</body>
</html>