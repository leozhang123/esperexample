<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试选项</title>
</head>
<body>

<form action="<%=request.getContextPath()%>/web/door/testindex" method="post">
开门:
<select name="door">
<option value="A1">A1</option> 
<option value="A2">A2</option> 
<option value="A3">A3</option> 
<option value="A4">A4</option> 
</select>
<input name="opr" type="hidden" value="open">
<input type="submit">
</form>

<form action="<%=request.getContextPath()%>/web/door/testindex" method="post">
关门:
<select name="door">
<option value="A1">A1</option> 
<option value="A2">A2</option> 
<option value="A3">A3</option> 
<option value="A4">A4</option> 
</select>
<input name="opr" type="hidden" value="close">
<input type="submit">
</form>

<form action="<%=request.getContextPath()%>/web/randomdoor/testindex" method="post">
模拟开关门:
<input name="num" type="text" value="10">
<input name="sleeptime" type="text" value="100">
<input type="submit" value="run">
</form>

<br><br>
<a target="=_blank" href="<%=request.getContextPath()%>/web/showImg">打开查看</a>
</body>
</html>