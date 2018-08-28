<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login4" method="post">
		<table>
			<tr>
				<th>姓名</th>
				<th>年龄</th>
				<th>性别</th>
			</tr>
			<tr>
				<th><input type="text" name="students[0].name" /></th>
				<th><input type="text" name="students[0].age" /></th>
				<th><input type="text" name="students[0].sex" /></th>
			</tr>
			<tr>
				<th><input type="text" name="students[1].name" /></th>
				<th><input type="text" name="students[1].age" /></th>
				<th><input type="text" name="students[1].sex" /></th>
			</tr>
			<tr>
				<th><input type="text" name="students[2].name" /></th>
				<th><input type="text" name="students[2].age" /></th>
				<th><input type="text" name="students[2].sex" /></th>
			</tr>
			<tr>
				<th colspan="3"><input type="submit" value="提交" /></th>
			</tr>
		</table>
	</form>
</body>
</html>