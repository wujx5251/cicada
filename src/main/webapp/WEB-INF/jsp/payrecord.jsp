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
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css"/>
    <title>收款记录列表</title>
</head>
<body style="overflow-x: hidden;">

<div class="col-xs-12">
    <div class="page-header" style="margin: 4px 0px">
        <h1>
            <i class="menu-icon fa fa-cny"></i> 收款记录列表
        </h1>
    </div>

    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">
                <label class="sr-only" for="channels">支付渠道</label>

                <div class="input-group">
                    <div class="input-group-addon">支付渠道</div>
                    <select class="form-control" name="channels" id="channels">
                        <option value="">请选择支付渠道...</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="productType">支付产品</label>

                <div class="input-group">
                    <div class="input-group-addon">支付产品</div>
                    <select class="form-control" name="productType" id="productType">
                        <option value="">请选择支付产品...</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="status">状态</label>

                <div class="input-group">
                    <div class="input-group-addon">付款状态</div>
                    <select class="form-control" name="status" id="status">
                        <option value="">请选择状态...</option>
                        <option value="1">已付款</option>
                        <option value="2">待付款</option>
                        <option value="0">失败</option>
                        <option value="4">已完成</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="merchant">商户编号</label>

                <div class="input-group">
                    <div class="input-group-addon">商户编号</div>
                    <input type="text" class="form-control" name="merchant" id="merchant" placeholder="请输入商户编号...">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="recordNo">交易流水号</label>

                <div class="input-group">
                    <div class="input-group-addon">交易流水号</div>
                    <input type="text" class="form-control" name="recordNo" id="recordNo" placeholder="请输入平台交易流水号...">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="orderNo">业务单号</label>

                <div class="input-group">
                    <div class="input-group-addon">业务单号</div>
                    <input type="text" class="form-control" name="orderNo" id="orderNo" placeholder="请输入业务单号...">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="replyNo">渠道流水号</label>

                <div class="input-group">
                    <div class="input-group-addon">渠道流水号</div>
                    <input type="text" class="form-control" name="replyNo" id="replyNo" placeholder="请输入渠道流水号...">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input type="text" class="form-control" readonly placeholder="请选择开始配置时间..." id="startTime">
                    <span class="input-group-addon">
                        <i class="fa fa-exchange"></i>
                    </span>
                    <input type="text" class="form-control" readonly placeholder="请选择结束配置时间..." id="endTime">
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
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="assets/js/jquery-confirm.min.js"></script>
<script src="js/dateformat.js"></script>
<script src="js/numberformat.js"></script>
<script src="js/ajax.js?_<%=Math.random()%>"></script>
<script src="js/payrecord.js?_<%=Math.random()%>"></script>
</html>
