<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="assets/css/jquery-confirm.min.css"/>
    <link rel="stylesheet" href="assets/css/bootstrapValidator.css"/>
    <title>应用管理</title>
</head>
<body style="overflow-x: hidden">
<div class="col-xs-12">
    <div class="page-header" style="margin: 4px 0px">
        <h1 style="float: left">
            <i class="menu-icon fa fa-cogs"></i>
            应用列表
        </h1>
        <span id="add" class="label label-white btn btn-white btn-info">新增应用</span>
    </div>

    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">
                <label class="sr-only" for="merchant">所属商户</label>
                <div class="input-group">
                    <div class="input-group-addon">所属商户</div>
                    <select class="form-control" name="merchant" id="merchant">
                        <option value="">请选择商户...</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="appName">应用名称</label>

                <div class="input-group">
                    <div class="input-group-addon">应用名称</div>
                    <input type="text" class="form-control" name="appName" id="appName" placeholder="请输入应用名称...">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="appCode">应用编号</label>

                <div class="input-group">
                    <div class="input-group-addon">应用编号</div>
                    <input type="text" class="form-control" name="appCode" id="appCode" placeholder="请输入应用编号...">
                </div>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-primary queryButton">查询</button>
            </div>
        </form>
    </div>

    <table class="table table-striped table-bordered table-hover" style="word-break:break-all; word-wrap:break-all;"/>
</div>

</body>
<script src="assets/js/jquery-2.1.4.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-table.min.js"></script>
<script src="assets/js/bootstrap-table-zh-CN.min.js"></script>
<script src="assets/js/jquery-confirm.min.js"></script>
<script src="assets/js/bootstrapValidator.js"></script>
<script src="js/ajax.js?_<%=Math.random()%>"></script>
<script src="js/application.js?_<%=Math.random()%>"></script>
</html>
