<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
		
		<!-- 可选的Bootstrap主题文件（一般不用引入） -->
		<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
		
		<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
		<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
		
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		
		<style>
			.loginform{
				background-color:#f3f3f3;
				padding:15px;
				margin:10px;
			}
			.surround{
				margin-top:100px;
			}
		</style>

</head>
<body>

		<div class="surround">
			<div class="container col-md-3 loginform">
				<form class="form-horizontal" action="/user/post" method="post">
					<div class="form-group">
						<label for="name" class="col-md-3">用户名:</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="name" id="name"/>
							</div>
					</div>
					<div class="form-group">
							<label for="age" class="col-md-3">年龄:</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="age" id="age"/>
							</div>
					</div>
					<input type="submit" class="btn btn-info" value="新增"/>
				</form>
	
			</div>
			
			<div class="container col-md-3 loginform">
				<div class="row" style="width:100px;margin-left:100px;">
					<a class="btn btn-danger" href="/user/delete/1">删除用户</a>
				</div>
			</div>
			
			<div class="container col-md-3 loginform">
					<form class="form-horizontal" action="/user/update" method="post">
						<div class="form-group">
							<label for="name" class="col-md-3">用户名:</label>
								<div class="col-md-9">
									<input type="text" class="form-control" name="name" id="name"/>
								</div>
						</div>
						<div class="form-group">
								<label for="age" class="col-md-3">年龄:</label>
								<div class="col-md-9">
									<input type="text" class="form-control" name="age" id="age"/>
								</div>
						</div>
						<input type="submit" class="btn btn-info" value="修改"/>
					</form>
			</div>
			
			
			
		</div>
	
</body>
</html>