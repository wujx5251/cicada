<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>Cicada 交易系统</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
</head>

<body class="login-layout">
<div class="main-container">
    <div class="login-container" style="margin-top: 6%">
        <div class="center">
            <h1>
                <i class="ace-icon fa fa-bitcoin green"></i>
                <span class="red">Cicada</span>
                <span class="white" id="id-text2">交易系统登陆</span>
            </h1>
        </div>

        <div class="space-6"></div>
        <div class="space-6"></div>

        <div class="position-relative">
            <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                    <div class="widget-main">
                        <h4 class="header blue lighter bigger">
                            <i class="ace-icon fa fa-coffee green"></i>
                            请输入登录信息
                        </h4>

                        <div class="space-6"></div>

                        <fieldset>
                            <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <input type="text" id="account" class="form-control"
                                                               title="登录账户"
                                                               placeholder="邮箱不带后缀"/>
                                                        <i class="ace-icon fa fa-user"></i>
                                                    </span>
                            </label>

                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" class="form-control"
                                                                   title="登录密钥"
                                                                   placeholder="邮箱密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                            </label>

                            <div class="space"></div>

                            <div class="clearfix">
                                <%--  <label class="inline">
                                      <input type="checkbox" class="ace"/>
                                      <span class="lbl"> Remember Me</span>
                                  </label>--%>

                                <button type="button" id="submit"
                                        class="width-35 pull-right btn btn-sm btn-primary">
                                    <i class="ace-icon fa fa-key"></i>
                                    <span class="bigger-110">登录</span>
                                </button>
                            </div>

                            <div class="space-4"></div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="assets/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#account").bind('keypress', function (event) {
            if (event.keyCode == "13") {
                $("#password").focus();
            }
        });
        $("#password").bind('keypress', function (event) {
            if (event.keyCode == "13") {
                $("#submit").trigger("click");
            }
        });

        $("#submit").click(function () {
            $.post("doLogin", {"loginId": $("#account").val(), "password": $("#password").val()}, function (data) {
                if (!data.status) {
                    alert(data.message)
                } else {
                    location.href = "./";
                }
            }, "json");
        });

    });

</script>
</html>