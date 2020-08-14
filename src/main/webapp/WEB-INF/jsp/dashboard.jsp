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
    <link rel="stylesheet" href="assets/css/daterangepicker.min.css"/>
    <title>监控大盘</title>
</head>
<body style="overflow-x: hidden">


<div class="main-content">
    <div class="content">
        <div class="infobox infobox-blue col-md-3 col-sm-3 col-xs-12">
            <div class="infobox-icon">
                <i class="ace-icon fa fa-cny"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">收款金额 <l id="channelSize">--</l></span>

                <div class="infobox-content">平台累计收款金额</div>
            </div>
        </div>
        <div class="infobox infobox-green col-md-3 col-sm-3 col-xs-12">
            <div class="infobox-icon">
                <i class="ace-icon fa fa-tasks"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">收款数量 <l id="msgSize">--</l></span>

                <div class="infobox-content">平台累计收款数量</div>
            </div>
        </div>
        <div class="infobox infobox-orange col-md-3 col-sm-3 col-xs-12">
            <div class="infobox-icon">
                <i class="ace-icon fa fa-reply"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">退款金额 <l id="gatewaySize">--</l></span>

                <div class="infobox-content">平台累计退款金额</div>
            </div>
        </div>
        <div class="infobox infobox-green col-md-3 col-sm-3 col-xs-12">
            <div class="infobox-icon">
                <i class="ace-icon fa fa-tasks"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">退款数量 <l id="msgSize">--</l></span>

                <div class="infobox-content">平台累计退款数量</div>
            </div>
        </div>
    </div>

    <div class="col-md-12">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">信息报表</h3>

                <div class="pull-right box-tools">
                    <button type="button" class="btn btn-primary btn-sm daterange pull-right" data-toggle="tooltip"
                            id="filterTime">
                        <i class="fa fa-calendar"></i>
                    </button>
                </div>

            </div>
            <div class="box-body">
                <div class="row">
                    <div class="col-md-8">
                        <div id="lineChart" style="height: 350px;"></div>
                    </div>
                    <div class="col-md-4">
                        <div id="pieChart" style="height: 350px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="assets/js/jquery-2.1.4.min.js"></script>
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/daterangepicker.min.js"></script>
<script src="assets/js/echarts.common.min.js"></script>
<script src="js/ajax.js?_<%=Math.random()%>"></script>
<script src="js/dashboard.js?_<%=Math.random()%>"></script>
</html>
