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

<trial:assertcss files="/up/dj-mobile/vcard/lib/matchbox/matchbox.css,/up/dj-mobile/vcard/css/edit.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/lib/matchbox/matchbox.js,/up/dj-mobile/vcard/lib/validate.js,/up/dj-mobile/vcard/lib/easy-validate.js,/up/dj-mobile/vcard/js/edit.js" />


  <!-- 本页样式 -->
<!--   <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/matchbox/matchbox.css"/>
  <link rel="stylesheet" href="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/css/edit.css"/>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/matchbox/matchbox.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/lib/easy-validate.js"></script>
  <script type="text/javascript" src="http://assets.dajieimg.com.wika/up/dj-mobile/vcard/js/edit.js"></script>
 -->
  <script type="text/javascript">
    dj['setPageData']({
      matchbox : {
        dom : 'jp-matchbox-input',
        // 请求地址，{1}会换成输入内容
        query : '/user/corp-search-ajax?keyword={1}',
        // 至少多少字开始查询
        limitQuery : '1',
        // 最多显示几个结果
        maxList : '6'
      }
    });
    dj['setPageData']({
      validate : {
        strBtnID : 'jp-submit',
        strFormID : 'jp-form',
        nodeType : 'div',
        item : {
          name : {
            reg : 'name',
            regErr : '姓名不对'
          },
          email : {
            reg : 'email',
            regErr : '邮箱格式不正确',
            //url : '/up/dj-mobile/vcard/json/ajax.php',
            //urlErr : '邮箱已经注册了！',
            blanksubmit : 'true'
          },
          corp : {
            reg : 'corpName',
            regErr : '请输入真实的公司名称'
          },
          industry : {
            reg : '.+',
            regErr : '选择行业'
          },
          position : {
            reg : 'jobName',
            regErr : '请输入真实的职位名称'
          },
          department : {
            reg : 'department',
            regErr : '请输入真实的部门名称'
          },
          location : {
            reg : 'area',
            regErr : '地区中存在特殊字符，请去掉后重试'
          }
        }
      }
    });
  </script>
  <title>个人资料</title>
        <!-- 微信分享配置-->
  <%@include file="../include/shareweixin1.html"%>
</head>
<body>
<div class="dj-base-wrap">
  <div class="p-wrap">
    <h1>完善信息，可以在微卡中更好的展现自己</h1>
    <form id="jp-form" action="/user/update-service" method="post" class="p-form-wrap">
      <fieldset>
        <!--<p>基本信息</p>-->
        <ul class="i-form-list">
          <li class="t-flex-wrap">
            <span>姓名*   </span>
            <div class="t-flex-item">
              <input name="name" type="text" value="${userbase.name}" maxlength="40" />
              <div class="error">${errmsg}</div>
            </div>            
            <em class="p-sex">
              <i class="i-clear">
              <!-- ${userbase.gender==1?'checked':''} -->
                <input id="fp-sex-man" value="1" name="sex" type="radio" checked />
                <label for="fp-sex-man">男</label>
                <input id="fp-sex-woman" value="2" name="sex" type="radio" ${userbase.gender==2?'checked':''} />
                <label for="fp-sex-woman">女</label>
              </i>
            </em>
          </li>
          <li class="t-flex-wrap">
            <span>手机号　</span>
            <div class="t-flex-item">${userbase.mobile}</div>
            <em class="p-change-tel"><a href="/account/rebind-mobile">更换号码</a></em>
          </li>
          <li class="t-flex-wrap">
            <span>邮箱　　</span>
            <div class="t-flex-item">
              <input name="email" type="email" value="${userbase.email}" maxlength="50"/>
              <div class="error"></div>
            </div>
          </li>
        </ul>
        <figure>手机号不会被陌生人看见，在设置中控制由谁可见。</figure>
      </fieldset>
      <fieldset>
        <!--<p>职场信息</p>-->
        <ul class="i-form-list">
          <li class="t-flex-wrap">
            <span>行业　　</span>
            <div class="t-flex-item">
              <select name="industry">
              <option value="0" selected >请选择</option>
              <c:forEach var="industry" items="${industries}">
		          <optgroup label="${industry.name}">
		            <c:forEach var="option" items="${industry.sub}">
		           		 <c:if test="${option.id==occupation.industry}">
					 		<option value="${option.id}" selected >${option.name}</option>
					 	 </c:if>
					 	 <c:if test="${option.id!=occupation.industry}">
					 		<option value="${option.id}" >${option.name}</option>
					 	 </c:if>
					 </c:forEach>
			     </optgroup>
			  </c:forEach>
              </select>
              <div class="error"></div>
            </div>
          </li>
          <li class="t-flex-wrap">
            <span>公司*　　</span>
            <div class="t-flex-item">
              <input name="corp" type="text" id="jp-matchbox-input" autocomplete="off" value="${occupation.corp}" />
              <div class="error"></div>
            </div>
          </li>
          <li class="t-flex-wrap">
            <span>职位*　　</span>
            <div class="t-flex-item">
              <input name="position" type="text" value="${occupation.position}" />
              <div class="error"></div>
            </div>
          </li>
        </ul>
      </fieldset>
      <fieldset>
        <ul id="jp-more-select-content" class="i-form-list p-more-select-content p-hide-more-select">
          <li class="t-flex-wrap">
            <span>部门　　</span>
            <div class="t-flex-item">
              <input name="department" type="text" value="${occupation.department}" maxlength="40" />
            </div>
          </li>
          <li class="t-flex-wrap">
            <span>职位类型</span>
            <div class="t-flex-item">
               <select name="jobType">
                <option value="0" selected >请选择</option>
                 <c:forEach var="job" items="${jobtype}">
                 	<c:if test="${job.id==occupation.jobType}">
		            	<option value="${job.id}" selected >${job.name}</option>
		            </c:if>
		            <c:if test="${job.id!=occupation.jobType}">
		            	<option value="${job.id}" >${job.name}</option>
		            </c:if>
		         </c:forEach>
              </select>
            </div>
          </li>
          
          <li class="t-flex-wrap">
            <span>所在地</span>
            <div class="t-flex-item">
              <input name="location" type="text" value="${userbase.location}" maxlength="40" />
            </div>
          </li>
          <li class="t-flex-wrap">
            <span>状态</span>
            <div class="t-flex-item">
              <textarea id="jp-textarea" name="introduce" maxlength="30">${userbase.introduce}</textarea>
              <div class="error"></div>
            </div>
          </li>
        </ul>
        <div class="p-more-select">
          <input id="jp-more-select" type="checkbox" name="has-more" autocomplete="off"/>
          <label for="jp-more-select"></label>
        </div>
      </fieldset>
      <a id="jp-submit" class="i-btn p-btn" href="#">完成</a>
      <a class="i-btn-cancel p-btn" href="/user/myvcard?userId=${userbase.userId}">取消</a>
    </form>
  </div>
</div>
</body>
</html>