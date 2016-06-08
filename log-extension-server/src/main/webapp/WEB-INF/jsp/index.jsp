<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<!-- saved from url=(0041)http://v3.bootcss.com/examples/dashboard/ -->
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="./images/favicon.ico">

	<title>日志监控系统</title>
	<link href="/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="/bower_components/dashboard/dashboard.css" rel="stylesheet">
	<link href="/bower_components/commons/css/core.css" rel="stylesheet">
	<script src="/bower_components/dashboard/ie-emulation-modes-warning.js"></script>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">日志监控系统</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">帮助</a></li>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="Search...">
			</form>
		</div>
	</div>
</nav>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<h4>
					<span style="margin-left: -15px;"
						  class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;监控报表
			</h4>
			<ul class="nav nav-sidebar">
				<li class="active"><a href="#">应用信息</a></li>
				<li><a href="association.html">调用关系</a></li>
				<li><a href="association.html">调用统计</a></li>
				<li><a href="association.html">异常超时</a></li>
			</ul>
		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h2 class="page-header">应用信息</h2>

			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
				<li class="active">应用信息</li>
			</ol>

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">操作区</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-11">
							<span>应用：</span> <input id="theme_query_word" type="text" name="word"/>
						</div>
						<div class="col-xs-1">
							<input type="button" class="btn btn-primary" value="查询"/>
						</div>
					</div>
				</div>
			</div>

			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
					<tr>
						<th width="15%">应用</th>
						<th width="50%">类名称</th>
						<th>方法名</th>
					</tr>
					</thead>
					<tbody id="theme-content">
					</tbody>
				</table>
			</div>
			<!-- 分页开始 -->
			<div id="cell-pager" class="row">
				<nav>
					<ul class="pager">
						<li><a href="#" onclick="first()">首页</a></li>
						<li><a href="#" onclick="previous()">上一页</a></li>
						<li><a href="#" onclick="next()">下一页</a></li>
						<li><a href="#" onclick="end()">末页</a></li>
						<li id="theme_page-info">共 1/14 页</li>
					</ul>
				</nav>
			</div>
			<!-- 分页结束 -->

		</div>
	</div>
</div>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/bower_components/dashboard/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/bower_components/dashboard/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
