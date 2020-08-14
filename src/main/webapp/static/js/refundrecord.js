$(function () {
    $('#startTime').val(new Date(new Date() - 1000 * 60 * 60 * 24 * 30).format("yyyy-MM-dd"));
    $('#endTime').val(new Date().format("yyyy-MM-dd"));

    $.getJSON("trade/getChannels", {}, function (rs) {
        var channel = $("#channels");
        if (rs) {
            $.each(rs, function (index, val) {
                channel.append('<option value="' + val.code + '">' + val.name + '</option>');
            });
        }
    });

    var table = $('.table').bootstrapTable({
        url: 'refund/search',
        pagination: true,
        sidePagination: 'server',
        locale: "zh-CN",
        buttonsClass: 'sm',
        toolbar: "#toolbar",
        buttonsAlign: "right",  //按钮位置
        toolbarAlign: "left",
        idField: 'id',
        cache: false,
        queryParams: function (params) {
            return {
                currentPage: (params.offset / params.limit) + 1,
                pageSize: params.limit,
                mchId: $("#merchant").val(),
                channel: $("#channels").val(),
                product: $("#productType").val(),
                targetRecordId: $("#recordNo").val(),
                orderId: $("#orderNo").val(),
                replyId: $("#replyNo").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val()+" 23:59:59",
                status: $("#status").val()
            }
        },
        responseHandler: function (rs) {
            if (rs && rs.code == 200 && rs.content)
                return {
                    rows: rs.content.list,
                    total: (rs.content.totalRows ? rs.content.totalRows : 0)
                }
            return {
                rows: [],
                total: 0
            };
        },
        paginationLoop: false,
        columns: [
            {field: 'recordId', title: '退款流水号'},
            {field: 'targetRecordId', title: '付款流水号'},
            {field: 'orderId', title: '业务单号'},
            {field: 'replyId', title: '渠道流水号'},
            {field: 'mchId', title: '商户编号'},
            {field: 'appId', title: '应用编号'},
            {field: 'channel', title: '支付渠道', formatter: 'channelFormater'},
            {field: 'amount', title: '金额（元）', formatter: 'amountFormater'},
            {field: 'subject', title: '订单标题'},
            {field: 'status', title: '状态', formatter: 'statusFormater'},
            {field: 'createTime', title: '创建时间', formatter: 'timeFormater'},
            {field: 'payTime', title: '退款时间', formatter: 'timeFormater'},
            {
                field: 'action', title: '操作',
                formatter: function (value, row, index) {
                    return '<span id="detail" class="label btn btn-success">订单详情</span>';
                },
                events: {
                    'click #detail': function (e, value, row, index) {
                        $.confirm(detail(row));
                    },
                }
            }
        ]
    });

    var option = {
        language: "zh-CN",
        minuteStep: 5,
        showSecond: false, //显示秒
        minView: "month",
        format: 'yyyy-mm-dd',
        autoclose: true,// 选中之后自动隐藏日期选择框
        todayBtn: true,  //显示今天按钮
        todayHighlight: true,   //当天高亮显示
        endDate: new Date()
    }
    $('#startTime').datetimepicker(option).on('show', function (e) {
        $("#startTime").datetimepicker("setEndDate", $("#endTime").val());
        $("#startTime").datetimepicker("setStartDate",
            new Date(new Date($("#endTime").val()).getTime() - 1000 * 60 * 60 * 24 * 30).
                format("yyyy-MM-dd"));
    });

    $('#endTime').datetimepicker(option).on('show', function (e) {
        $("#endTime").datetimepicker("setStartDate", $("#startTime").val());

        var endTime = new Date(new Date($("#startTime").val()).getTime() + 1000 * 60 * 60 * 24 * 30);
        if (new Date().getTime() < endTime.getTime())
            endTime = new Date();
        $("#endTime").datetimepicker("setEndDate", endTime.format("yyyy-MM-dd"));
    });


    $(document).on('click', ".queryButton", function () {
        table.bootstrapTable('refresh');
    });

    $.getJSON("merchant/search", {}, function (rs) {
        var merchant = $("#merchant");
        if (rs && rs.code == 200 && rs.content) {
            $.each(rs.content.list, function (index, val) {
                merchant.append('<option value="' + val.mchId + '">' + val.name + '</option>');
            });
        }
    });
});

function detail(row) {
    return {
        icon: 'fa fa-book',
        type: 'blue',
        closeIcon: true,
        title: '订单详情',
        animationSpeed: 300,
        columnClass: 'col-md-12 col-xs-12',
        content: getContent(row),
        buttons: {
            cancel: {
                text: '关闭',
                btnClass: 'btn btn-default'
            }
        },
        onContentReady: function () {
            $.getJSON("refund/detail", {recordId: row.recordId}, function (rs) {
                if (rs) {
                    var params = $('.form').serializeArray();
                    $.each(params, function (i, v) {
                        var val = rs.content[v.name];
                        if ('channel' == v.name) {
                            val = channelFormater(val);
                        } else if ('amount' == v.name) {
                            val = amountFormater(val);
                        } else if ('status' == v.name) {
                            val = statusFormater(val);
                        } else if ('createTime' == v.name || 'payTime' == v.name || 'expireTime' == v.name) {
                            val = timeFormater(val);
                        }
                        $("input[name='" + v.name + "']").val(val);
                    });
                }
            });
        }
    };
}

function getContent() {
    return '<form id="detail" class="form-horizontal form" role="form" novalidate="novalidate">' +
        '<div class="form-group"><label class="col-md-2 control-label">交易流水号</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="recordId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">目标流水号</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="targetRecordId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">订单号</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="orderId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">金额(元)</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="amount" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">币种</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="currency" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">订单状态</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="status" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">商品标题</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="subject" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">商品描述</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="body" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">支付渠道</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="channel" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">支付产品</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="product" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">商户号</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="mchId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">应用ID</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="appId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">渠道商户</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="channelMchId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">渠道单号</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="replyId" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">错误信息</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="resultMsg" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">通知地址</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="notifyUrl" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">创建时间</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="createTime" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">失效时间</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="expireTime" readonly/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">成功时间</label>' +
        '<div class="col-md-10"><input type="text" class="form-control" name="payTime" readonly/></div></div>' +
        '</form>';
}

function timeFormater(value, row, index) {
    if (value) {
        return new Date(value).format("yyyy-MM-dd HH:mm:ss");
    }
}
function amountFormater(value, row, index) {
    if (value) {
        return (value / 100).format(2);
    }
}
function channelFormater(value, row, index) {
    if (value) {
        return $("#channels option[value='" + value + "']").text();
    }
}
function statusFormater(value, row, index) {
    if (value == 1) {
        return '已退款';
    } else if (value == 2) {
        return '待退款';
    } else {
        return '失败';
    }
}