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
  
<trial:assertcss files="/up/dj-mobile/vcard/css/reset.css,/up/dj-mobile/vcard/css/tool.css,/up/dj-mobile/vcard/css/item.css,/up/dj-mobile/vcard/css/404.css"/>
<trial:assertjs files="/up/dj-mobile/vcard/js/base.js,/up/dj-mobile/vcard/lib/zepto.js" />

<title>500-微卡</title>
</head>
<body>
<h1>500</h1>
<div id="jp-more" style="display:none;">
<p>出错啦(つд⊂)...再过5秒带你回去</p>
<a id="jp-go-to-next" href="javascript:location.href = document.referrer;;void(0);">返回上一页</a>
</div>
<script type="text/javascript">
  (function(exports){
    if(!document.referrer) return;
    document.getElementById('jp-more').style.display = 'block';
    setTimeout(function(){
//    	location.href = document.getElementById('jp-go-to-next').href;
      location.href = document.referrer;
    }, 5000);
  })(window);
</script>
</body>
</html>