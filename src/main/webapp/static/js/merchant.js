var table;
$(function () {
    table = $('.table').bootstrapTable({
        url: 'merchant/search',
        pagination: true,
        sidePagination: 'server',
        locale: "zh-CN",
        buttonsClass: 'sm',
        toolbar: "#toolbar",
        idField: 'id',
        cache: false,
        queryParams: function (params) {
            return {
                currentPage: (params.offset / params.limit) + 1,
                pageSize: params.limit,
                name: $("#mchName").val(),
                mchId: $("#mchCode").val()
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
            {field: 'mchId', title: '商户编号'},
            {field: 'name', title: '商户名称'},
            {field: 'mchKey', title: '商户密钥'},
            {
                field: 'action', title: '操作',
                formatter: function (value, row, index) {
                    return '<div class="action-buttons">' +
                        '<a class="orange refresh" href="#" title="更新密钥"><i class="ace-icon fa fa-refresh bigger-130"></i></a>' +
                        '<a class="green edit" href="#" title="编辑商户"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                        '<a class="red remove" href="#" title="删除商户"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
                },
                events: {
                    'click .refresh': function (e, value, row, index) {
                        $.confirm(option(row.mchId, 1));
                    },
                    'click .edit': function (e, value, row, index) {
                        $.confirm(edit(row));
                    },
                    'click .remove': function (e, value, row, index) {
                        $.confirm(option(row.mchId));
                    }
                }
            }
        ]
    });

    $("#add").click(function () {
        $.confirm(edit());
    });

    $(document).on('click', ".queryButton", function () {
        table.bootstrapTable('refresh');
    });
});

function edit(row) {
    return {
        icon: row ? 'fa fa-pencil' : 'fa fa-plus',
        type: 'blue',
        title: row ? '编辑商户' : '新增商户',
        animationSpeed: 300,
        columnClass: 'col-md-6 col-md-offset-2 col-xs-10',
        content: getContent(row),
        buttons: {
            confirm: {
                text: '保存',
                btnClass: 'btn btn-primary',
                action: function () {
                    if (validate()) {
                        if (row) {
                            modifyBy(row.id, this);
                        } else {
                            add(this);
                        }
                    }
                    return false;
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn btn-default'
            }
        }
    };
}

function option(id, type) {
    return {
        type: 'red',
        animationSpeed: 300,
        closeIcon: true,
        title: false,
        content: type ? '更新商户密钥可能会影响线上交易，若非密钥泄漏，建议不要随意更新商户密钥。<p style="color: #6c6c6c">您是否继续更新商户密钥？</p>' : '确认删除该商户吗？',
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn btn-primary',
                action: function () {
                    type ? refresh(id, this) : deleteBy(id, this);
                    return false;
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn btn-default'
            }
        }
    };
}

function deleteBy(id, that) {
    $.post("merchant/remove", {mchId: id}, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "商户删除失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function modifyBy(id, that) {
    var params = $('.form').serializeArray();
    params.push({name: 'id', value: id});
    $.post("merchant/update", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "商户修改失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function refresh(id, that) {
    $.post("merchant/refresh", {mchId: id}, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "商户密钥更新失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function add(that) {
    var params = $('.form').serializeArray();
    $.post("merchant/save", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "商户添加失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}


function getContent(row) {
    if (row) {
        return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
            '<div class="form-group"><label class="col-md-2 control-label">商户编码<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="mchId" type="number" placeholder="请输入商户编码" class="form-control" maxlength="64" readonly value="'+row.mchId+'"/></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">商户名称<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="name" type="text" placeholder="请输入商户名称" class="form-control" maxlength="64" value="'+row.name+'"/></div></div>' +
            '</form>';
    }

    return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
        '<div class="form-group"><label class="col-md-2 control-label">商户编码<font color="red">*</font></label>' +
        '<div class="col-md-10"><input name="mchId" type="number" placeholder="请输入商户编码" class="form-control" maxlength="64"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">商户名称<font color="red">*</font></label>' +
        '<div class="col-md-10"><input name="name" type="text" placeholder="请输入商户名称" class="form-control" maxlength="64"/></div></div>' +
        '</form>';
}

function validate() {
    var validator = $('.form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '商户名称不能为空'
                    }
                }
            },
            mchId: {
                validators: {
                    notEmpty: {
                        message: '商户编码不能为空'
                    }
                }
            }
        }
    });
    validator.data('bootstrapValidator').validate();//启用验证
    return validator.data('bootstrapValidator').isValid()
}
