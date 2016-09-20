<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>抓取展示Demo查询页面</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/bootstrap-table.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker-standalone.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-table.js"></script>
<script src="../js/bootstrap-table-zh-CN.js"></script>
</head>
<body>
	
	<ol class="breadcrumb">
		<li><span class="fa fa-fw fa-dashboard"></span>&nbsp;<H4>左栏抓取信息查询</H4></li>
	</ol>
	
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="page-toolbar">
			<div class="form-inline" role="form">
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">信息标题</label>
					<input type="text" class="form-control" placeholder="信息标题" id="filter-title">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-merchantName">来源网站</label>
					<input type="text" class="form-control" placeholder="来源网站(名称)" id="filter-source">
				</div>
				<div class="form-group form-group-sm">
					<label class="sr-only" for="filter-beginDate">开始日期</label>
					<div class="input-group date" id="filter-beginDate-group">
		                <input type="text" class="form-control" size="20" id="beginDate" placeholder="开始日期(默认查询当天)" required/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					-
					<label class="sr-only" for="filter-endDate">结束日期</label>
					<div class="input-group date" id="filter-endDate-group">
		                <input type="text" class="form-control" size="20" id="endDate" placeholder="结束日期" required/>
		                <span class="input-group-addon">
		                	<span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
				</div>
				
				<button type="submit" class="btn btn-primary btn-sm" id="filter-submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查找
				</button>
			</div>
		</div>
		<table id="page"></table>
	</div>
	
</body>
<footer-scripts>
<script src="../js/moment.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"></script>
<script src="../js/news.js"></script>
</footer-scripts>
</html>
