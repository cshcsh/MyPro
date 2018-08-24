<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="indexer" method="get">
城市:<input type="text" name="city"><font>${message1 }</font><br>
描述:<textarea rows="3" cols="30" name="desc"></textarea>
<input type="submit" value="提交"><font>${message2 }</font>
</form>
<hr>
<form action="searcher" method="get">
关键字:<input type="text" name="keyWord">
<input type="submit" value="搜索"><font>${message5 }</font>
</form>
<div>${message3 }</div>
<div>${message4 }</div>
</body>
</html>