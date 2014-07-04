<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<trial:assertcss files="/up/dj-mobile/vcard/css/qr-large.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/template.min.js,/up/dj-mobile/vcard/js/qr-large.js,/up/dj-mobile/vcard/tpl/qr-large.tpl.js" />

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/qr-large.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/template.min.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/qr-large.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/tpl/qr-large.tpl.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      'p-qr-large' : {
        dom : 'jp-qr-wrap',
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
            href : '/user/use-qr?id={id}'//使用
          },
          useApp : {
            btn : 'i-btn',
            str : '下载应用，立即解锁',
            href : '/system/download?sId=7'//到下载页
          },
          useSns : {
            btn : 'i-btn',
            str : '分享到朋友圈，立即解锁',
            href : '/user/unlock-qr?id={id}'//直接解锁，并跳转到首页分享
          }
        },
        // 进入时所处的页数，一页3个
        page : 1,
        qrList : [
           		<c:forEach var="qr" items="${qrcodeswrapper}">
        		 {
                    id : '${qr.QTId}',
                    url : '${qr.qrUrl}', 
                    name : '${qr.name}',
                    typeName:'${qr.tips}',
                    <c:if test="${qr.QTId==userbase.faceCodeType}"> 
                    selected : true,
                    type : 'used'
                    </c:if>
                    <c:if test="${qr.QTId!=userbase.faceCodeType}">
                    	<c:if test="${qr.unlockAction==2}">
                    		mask:true,
                    		type : 'useApp'
                    	</c:if>
                    	<c:if test="${qr.unlockAction!=2}">
                    		<c:if test="${qr.status==1}">
                				type : 'useTrue'
                			</c:if>
               				 <c:if test="${qr.status==0}">
             					type : 'useSns'
             				</c:if>
                		</c:if>
                    	
                    </c:if>
        			
                  },
        </c:forEach>
/*           {
            id : '1',
            url : '/up/dj-mobile/vcard/pic/person.jpg',
            type : 'used',
            selected : true
          },
          {
            id : '2',
            url : '/up/dj-mobile/vcard/pic/2.jpg',
            type : 'useTrue'
          },
          {
            id : '3',
            url : '/up/dj-mobile/vcard/pic/3.jpg',
            type : 'useSns'
          },
          {
            id : '4',
            url : '/up/dj-mobile/vcard/pic/4.jpg',
            type : 'useTrue'
          },
          {
            id : '5',
            url : '/up/dj-mobile/vcard/pic/5.jpg',
            type : 'useApp'
          },
          {
            id : '6',
            url : '/up/dj-mobile/vcard/pic/6.jpg',
            type : 'useSns'
          },
          {
            id : '7',
            url : '/up/dj-mobile/vcard/pic/7.jpg',
            type : 'useApp'
          }  */
        ]
      }
    });
  </script>
  <title>我的脸码</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <section id="jp-qr-wrap" class="p-qr"></section>
    <aside class="p-aside">
      <div id="jp-btn-wrap"></div>
    </aside>
    <p class="p-btn"><span style="color:#666;">最潮最酷的二维码头像，扫我吧！</span></p>
  </div>
    <!-- 引入底部导航 -->
  <%@include file="../include/foot-nav.html"%>
</div>
</body>
</html>