<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
  
<trial:assertcss files="/up/dj-mobile/vcard/css/i-know.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/js/i-know.js" />

  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/i-know.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/i-know.js"></script> -->
  <script type="text/javascript">
    dj['setPageData']({
      'p-change-card' : {
        url : '/relation/follow-service-ajax',
        // 提交方式
        type : 'post',
        // 需要提交的数据，对应dom结构中的 data-[] 属性
        data : ['id', 'other'],
        // 交互后的文案
        strChanged : '熟人'
      }
    })
  </script>
  <c:if test="${following==0}">
  <title>新的朋友</title>
  </c:if>
  <c:if test="${following==1}">
  <title>联系人</title>
  </c:if>
  <!-- 微信分享配置-->
  <%@include file="../include/shareweixin2.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
  
  	<c:if test="${following==1}">
    <nav class="p-nav-new">
      <a href="/relation/i-know?following=0">新的朋友</a>
    </nav>
    </c:if>
    <section  class="p-list">
     <ul>
      <c:forEach var="user" items="${userlist}">
         <li class="t-flex-wrap" data-url="/user/people-i-know?t=${user.relationType}&id=${user.userId}">
          <div class="img">
            <img src="${user.avatar}"/>
          </div>
          <div class="content t-flex-item">
            <p class="name">${user.name}</p>
            <p>${user.position}</p>
            <p>${user.corp}</p>
          </div>
          <c:if test="${user.relationType==3}">
          	<div class="tool changed">
            	<span>熟人</span>
          	</div>
          </c:if>
           <c:if test="${user.relationType!=1&&user.relationType!=3}">
           <div class="tool" data-id="${user.userId}" data-other="456">
           	 <span>接受</span>
         	 </div>
           </c:if>
        </li>
      </c:forEach>
      </ul>
<%--       <c:if test="${fn:length(list)<5}"> --%>
     	 <div class="ad">
     	 <c:if test="${following==0}">
       	 	<p>守株待兔是等不来机遇的<br/>分享微卡到朋友圈，发现更多人脉机会</p>
       	 </c:if>
       	 <c:if test="${following==1}">
       	 	<p>在家靠父母，出门靠朋友<br/>趁年轻去认识更多朋友吧</p>
       	 </c:if>
       	 	<a class="i-btn" href="/user/myvcard?userId=${userId}&djshare=1">速度分享</a>
     	 </div>
 <%--      </c:if> --%>
      
<%--       <c:if test="${fn:length(list)>=5}">
      	<div class="ad">
       		<p>下载应用，管理全部联系人</p>
       	 	<a class="i-btn" href="/system/download?sId=3">下载APP</a>
      	</div>
      </c:if> --%>
    </section>
  </div>
  <!-- 引入底部导航 -->
  <%@include file="../include/foot-nav.html"%>
</div>
</body>
</html>