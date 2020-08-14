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
    <title>商户管理</title>
</head>
<body>

<div class="col-xs-12">
    <div class="page-header" style="margin: 4px 0px">
        <h1 style="float: left">
            <i class="menu-icon fa fa-cubes"></i> 商户列表
        </h1>
        <span id="add" class="label label-white btn btn-white btn-info">新增商户</span>
    </div>

    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">
                <label class="sr-only" for="mchName">商户名称</label>

                <div class="input-group">
                    <div class="input-group-addon">商户名称</div>
                    <input type="text" class="form-control" name="mchName" id="mchName" placeholder="请输入商户名称...">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="mchCode">商户编号</label>

                <div class="input-group">
                    <div class="input-group-addon">商户编号</div>
                    <input type="text" class="form-control" name="mchCode" id="mchCode" placeholder="请输入商户编号...">
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
<script src="assets/js/bootstrap-table.min.js"></script>
<script src="assets/js/bootstrap-table-zh-CN.min.js"></script>
<script src="assets/js/jquery-confirm.min.js"></script>
<script src="assets/js/bootstrapValidator.js"></script>
<script src="js/ajax.js?_<%=Math.random()%>"></script>
<script src="js/merchant.js?_<%=Math.random()%>"></script>
</html>
