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


<trial:assertjs files="/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/phone-number.js"/>	
<trial:assertcss files="/up/dj-mobile/vcard/css/phone-number.css"/>

  <!-- 本页样式 -->
<%--   <link rel="stylesheet" href="${appProps['a.url.prefix']}/up/dj-mobile/vcard/css/phone-number.css"/>
   <script type="text/javascript" src="${appProps['a.url.prefix']}/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="${appProps['a.url.prefix']}/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="${appProps['a.url.prefix']}/up/dj-mobile/vcard/js/phone-number.js"></script> --%>
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
        data : {},
        // 申请验证码的等待间隔(秒)
        wait : 60,
        // 默认的按钮文案，下面html结构也要填写一样的
        str_default : '获取验证码',
        // 等待的文案，$会变成秒数
        str_wait : '重发($s)'
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
            reg : 'code',
            regErr : '请输入正确的验证码',
           // url : '/up/dj-mobile/vcard/json/ajax.php',
           // urlPlus : 'tel',
           // urlErr : '远程检测验证码不对'
          },
          password : {
            reg : 'password',
            regErr : '密码长度为4-20字符',
            regEmpty : '密码不能为空'
          }
        }
      }
    });
  </script>
  <title>注册微卡</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>完善信息，分分钟涨身价</h1>
    <form id="jp-form" action="/account/register-service" method="post" class="p-form-wrap">
      <ul class="i-form-list">
        <li class="t-flex-wrap">
          <span>手机号</span>
          <div class="t-flex-item">
            <input name="tel" type="tel" placeholder="输入手机号" maxlength="11" />
            <div class="error"></div>
          </div>
          <em id="jp-get-code" class="p-get-code">获取验证码</em>
        </li>
        <li class="t-flex-wrap" style="display:none;">
          <span>验证码</span>
          <div class="t-flex-item">
            <input name="code" type="text" placeholder="短信验证码"/>
            <div class="error"></div>
          </div>
        </li>
        <li class="t-flex-wrap">
          <span>密码　</span>
          <div class="t-flex-item">
            <input name="password" type="password" placeholder="设置微卡登录密码"/>
            <c:if test="${not empty errmsg}">
           	 	<div class="error showError">${errmsg}</div>
            </c:if> 
            <c:if test="${empty errmsg}">
           	 	<div class="error"></div>
            </c:if>
          </div>
        </li>
      </ul>
      <a id="jp-submit" class="p-btn i-btn" href="#">立即领取</a>
      <a class="p-btn" href="/account/login-dajie">使用大街网帐号登录</a>
      <a class="p-btn" href="/account/login-only">使用已有微卡帐号登录</a>
      <p class="p-btn"><span style="color:#666;">继续注册表示我接受</span><a href="/system/licence">《微卡许可及服务条款》</a></p>
    </form>
  </div>
</div>
</body>
</html>