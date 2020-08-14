<%@ page import="com.cicada.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>Cicada 交易系统</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="assets/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="css/tab_menu.css"/>
</head>

<body class="no-skin">
<div id="navbar" class="navbar navbar-default navbar-fixed-top ace-save-state">
    <button type="button" class="navbar-toggle menu-toggler pull-left display" id="menu-toggler" data-target="#sidebar">
        <span class="sr-only">Toggle sidebar</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <div class="navbar-header pull-left">
        <a href="" class="navbar-brand">
            <small>
                <i class="fa fa-laptop"></i>
                Cicada 交易系统
            </small>
        </a>
    </div>

    <div class="navbar-buttons navbar-header pull-right" role="navigation">
        <ul class="nav ace-nav">
            <li class="dropdown-modal">
                <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                    <img class="nav-user-photo" src="assets/images/avatars/avatar.png"/>
                    <span class="user-info">
									<small>Welcome,</small>
									${empty sessionScope.user ? "Guest" : sessionScope.user.userName}
                            </span>
                    <i class="ace-icon fa fa-caret-down"></i>
                </a>
                <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                    <li>
                        <a href="login">
                            <i class="ace-icon fa fa-exchange"></i>
                            Switch
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="javascript:void(0)" id="logout">
                            <i class="ace-icon fa fa-power-off"></i>
                            Logout
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<div class="main-container ace-save-state" id="main-container">
    <div class="sidebar responsive ace-save-state" id="sidebar">
        <ul class="nav nav-list">
            <li class="hover">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text">
                       系统环境：<%=Config.getEnv()%>
                    </span>
                </a>
            </li>

            <li class="open active">
                <a href="#" id="dashboard" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text">
                        Dashboard
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="payrecord" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-cny"></i>
                    <span class="menu-text">
                        收款记录
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="refundrecord" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-reply"></i>
                    <span class="menu-text">
                        退款记录
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="billrecord" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-bookmark"></i>
                    <span class="menu-text">
                        财务对账
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="notifyrecord" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-envelope"></i>
                    <span class="menu-text">
                        交易通知
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="traderecord" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-file-text"></i>
                    <span class="menu-text">
                        交易日志
                    </span>
                </a>
            </li>


            <li class="open">
                <a href="#" id="application" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-cogs"></i>
                    <span class="menu-text">
                        应用管理
                    </span>
                </a>
            </li>

            <li class="open">
                <a href="#" id="merchant" class="menu-but dropdown-toggle">
                    <i class="menu-icon fa fa-cubes"></i>
                    <span class="menu-text">
                        商户管理
                    </span>
                </a>
            </li>

        </ul>
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
               data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

    <div class="main-content">
        <div class="col-xs-12" style="padding-top:5px;">
            <ul class="nav nav-tabs" role="tablist"></ul>
            <div class="tab-content"></div>
        </div>
    </div>

    <div class="footer" style="background-color: #fff">
        <div class="footer-inner">
            <div class="footer-content">
                <span class="blue bolder">Cicada</span>
                Application © 2018-2020
                &nbsp; &nbsp;
                Powered by <b>久众架构</b> v1.0.1
            </div>
        </div>
    </div>
</div>

</body>
<script src="assets/js/jquery-2.1.4.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>
<script src="js/tab_menu.js?_<%=Math.random()%>"></script>
<script src="js/index.js?_"></script>
</html>