<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html nobar="true">
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
 <!-- 本页样式 -->
  <%@include file="../include/mycard-skin.html"%>
  
<trial:assertcss files="/up/dj-mobile/vcard/css/login-unlock.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/template.min.js,/up/dj-mobile/vcard/js/login-unlock.js,/up/dj-mobile/vcard/tpl/login-unlock.tpl.js" />

 
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/login-unlock.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/template.min.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/login-unlock.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/tpl/login-unlock.tpl.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      'p-login-unlock' : {
        dom : 'jp-change-wrap',
        btnDom : 'jp-btn-wrap',
        // 按钮样式预设
        type : {
          used : {
            btn : 'i-btn',
            str : '正在使用，秀给好友',
            href : '/user/myvcard?userId=${userbase.userId}&djshare=1'
          },
          useTrue : {
            btn : 'i-btn',
            str : '使用',
            href : '/user/use-wika?id={id}'
          },
          useApp : {
            btn : 'i-btn',
            str : '下载应用，立即解锁',
            href : '/system/download?sId=7'
          },
          useSns : {
            btn : 'i-btn',
            str : '分享到朋友圈，立即解锁',
            href : '/user/unlock-wika?id={id}'
          }
        },
        // 进入时所处的页数，一页3个
        page : 1,
        qrList : [
  /*    {
            id : '1',
            type : 'used',
            typeName : '免费',
            style : '1',
            name : '模板名称'
          },
          {
            id : '2',
            type : 'used',
            typeName : '免费',
            style : '2',
            name : '模板名称'
          },
          {
            id : '3',
            type : 'used',
            typeName : '免费',
            style : '3',
            selected : true,
            name : '模板名称'
          },
          {
            id : '4',
            type : 'useTrue',
            typeName : '免费2',
            style : '4',
            name : '模板名称2'
          },
          {
            id : '5',
            type : 'useTrue',
            typeName : '免费2',
            style : '5',
            name : '模板名称2'
          },
          {
            id : '6',
            type : 'useSns',
            typeName : '免费3',
            style : '6',
            name : '模板名称3'
          } */
 		<c:forEach var="wkt" items="${wktlist}">
 		 {
             id : '${wkt.WTId}',
             style : '${wkt.WTId}', 
             <c:if test="${wkt.WTId==userbase.wikaTemplate}">
             	type : 'used',
             	selected : true,
             </c:if>
             <c:if test="${wkt.WTId!=userbase.wikaTemplate}">
             	<c:if test="${wkt.unlockAction==2}">
     				type : 'useApp',
     			</c:if>
     			<c:if test="${wkt.unlockAction!=2}">
     				<c:if test="${wkt.status==1}">
 						type : 'useTrue',
 					</c:if>
					<c:if test="${wkt.status==0}">
						type : 'useSns',
					</c:if>
 			</c:if>
         	</c:if>
             typeName : '${wkt.name}',
             name:'${wkt.tips}'
           },
 		</c:forEach>
        ]
      }
    });
  </script>
  <title>我的微卡</title> 
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>

<div class="dj-base-wrap">
  <div class="p-wrap">
    <section class="i-mycard">
      <div id="jp-mycard"><span class="loading"></span></div>
      <div class="p-mask"></div>
    </section>
    <section id="jp-change-wrap"></section>
    <aside class="p-aside">
      <div id="jp-btn-wrap"></div>
    </aside>
  </div>
  <!-- 引入底部导航 -->
  <%@include file="../include/foot-nav.html"%>
</div>
</body>
</html>
