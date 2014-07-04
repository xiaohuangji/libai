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
<trial:assertjs files="/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/phone-number.js" />

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/phone-number.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/phone-number.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      'get-code' : {
        // 触发事件的id
        dom : 'jp-get-code',
        // 申请验证码的地址
        url : '/account/get-code-ajax',
        // 提交方式
        type : 'post',
        // 除了手机号意外还需要提交的数据
        data : {
          id : 123
        },
        // 申请验证码的等待间隔(秒)
        wait : 60,
        // 默认的按钮文案，下面html结构也要填写一样的
        str_default : '获取验证码',
        // 等待的文案，$会变成秒数
        str_wait : '$秒后，获取验证码'
      }
    });
    dj['setPageData']({
      validate : {
        strBtnID : 'jp-submit',
        strFormID : 'jp-form',
        nodeType : 'div',
        item : {
          tel : {
            reg : 'tel',
            regErr : '手机号不正确，请确认后重试',
            regEmpty : '手机号不能为空'
          },
          code : {
            reg : '.+',
            regErr : '要填写验证码',
            //url : '/up/dj-mobile/vcard/json/ajax.php',
            urlPlus : 'tel',
            urlErr : '远程检测验证码不对'
          }
        }
      }
    });
  </script>
  <title>找回密码</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <form id="jp-form" action="/setting/validateVerifyCode-service" method="post" class="p-form-wrap">
      <h1>找回密码，请输入手机号</h1>
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>手机号</span>
          <div class="t-flex-item">
            <input name="tel" type="tel" placeholder="输入手机号" maxlength="11" value="${tel}" />
            <div class="error"></div>
          </div>
          <em id="jp-get-code" class="p-get-code">获取验证码</em>
        </li>
        <li class="t-flex-wrap">
          <span>验证码</span>
          <div class="t-flex-item">
            <input name="code" type="text" placeholder="短信验证码"/>
            <c:if test="${not empty errmsg}">
           	 	<div class="error showError">${errmsg}</div>
            </c:if> 
            <c:if test="${empty errmsg}">
           	 	<div class="error"></div>
            </c:if>
          </div>
        </li>
      </ul>
      <a id="jp-submit" class="p-btn i-btn" href="#">下一步</a>
    </form>
  </div>
</div>
</body>
</html>