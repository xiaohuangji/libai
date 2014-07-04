<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
 <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="
    width=device-width,
    initial-scale=1,
    user-scalable=no">
   <style type="text/css">
   body, input, textarea, radio{
	font-size:16px;   
   }
   </style>
</head>
<body>

别人刷卡我刷脸，快做一个可以扫的头像，微信朋友圈都传疯了！
<br>
设计师不在家，更多功能敬请期待哦：）
<br>
填写好你的信息，聊聊你的感受
<br>

<form action="/h/s" method="get">
<br>
姓名：<input type="text" name="name" />
<br>
部门：<input type="text" name="depa" />
<br>
性别：
<input type="radio" name="sex" value="male" checked="checked"/> Male
<input type="radio" name="sex" value="female" /> Female
<br>
手机号：<input type="text" name="mobile" />
<br>
反馈：<textarea type="text" name="fb" ></textarea>
<br>
<input type="submit" value="Submit" />

</form>

</body>
</html>