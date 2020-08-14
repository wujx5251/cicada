var table;
var merchants = '<select class="form-control" name="mchId">';
$(function () {
    table = $('.table').bootstrapTable({
        url: 'application/search',
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
                mchId: $("#merchant").val(),
                name: $("#appName").val(),
                appId: $("#appCode").val()
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
            {field: 'mchName', title: '所属商户'},
            {field: 'name', title: '应用名称'},
            {field: 'appId', title: '应用编号'},
            {
                field: 'action', title: '操作',
                formatter: function (value, row, index) {
                    return '<div class="action-buttons">' +
                        '<a class="orange settring" href="#" title="应用配置"><i class="ace-icon fa fa-cog bigger-130"></i></a>' +
                        '<a class="green edit" href="#" title="修改应用"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                        '<a class="red remove" href="#" title="删除应用"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        $.confirm(edit(row));
                    },
                    'click .remove': function (e, value, row, index) {
                        $.confirm(remove(row.appId));
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

    $.getJSON("merchant/search", {}, function (rs) {
        var merchant = $("#merchant");
        if (rs && rs.code == 200 && rs.content) {
            $.each(rs.content.list, function (index, val) {
                merchants += '<option value="' + val.mchId + '">' + val.name + '</option>';
                merchant.append('<option value="' + val.mchId + '">' + val.name + '</option>');
            });
            merchants += '</select>'
        }
    });
});

function edit(row) {
    return {
        icon: row ? 'fa fa-pencil' : 'fa fa-plus',
        type: 'blue',
        title: row ? '编辑应用' : '新增应用',
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
                            modifyBy(row.appId, this);
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

function remove(id) {
    return {
        type: 'red',
        animationSpeed: 300,
        closeIcon: true,
        title: false,
        content: '确认删除该应用吗？',
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn btn-primary',
                action: function () {
                    deleteBy(id, this);
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
    $.post("application/remove", {appId: id}, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "应用删除失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function modifyBy(id, that) {
    var params = $('.form').serializeArray();
    $.post("application/update", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "应用修改失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function add(that) {
    var params = $('.form').serializeArray();
    $.post("application/save", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "应用添加失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}


function getContent(row) {
    if (row) {
        var cs = $(merchants);
        cs.find("option[value='" + row.mchId + "']").attr("selected", true);
        return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
            '<div class="form-group"><label class="col-md-2 control-label">应用编码<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="appId" type="number" placeholder="请输入应用编码" class="form-control" maxlength="64" readonly value="' + row.appId + '"/></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">应用名称<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="name" type="text" placeholder="请输入应用名称" class="form-control" maxlength="64"  value="' + row.name + '"/></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">所属商户<font color="red">*</font></label>' +
            '<div class="col-md-10">' + (cs.prop("outerHTML")) + '</div></div>' +
            '</form>';
    }
    return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
        '<div class="form-group"><label class="col-md-2 control-label">应用编码<font color="red">*</font></label>' +
        '<div class="col-md-10"><input name="appId" type="number" placeholder="请输入应用编码" class="form-control" maxlength="64"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">应用名称<font color="red">*</font></label>' +
        '<div class="col-md-10"><input name="name" type="text" placeholder="请输入应用名称" class="form-control" maxlength="64"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">所属商户<font color="red">*</font></label>' +
        '<div class="col-md-10">' + merchants + '</div></div>' +
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
            appId: {
                validators: {
                    notEmpty: {
                        message: '应用编码不能为空'
                    }
                }
            },
            mchlId: {
                validators: {
                    notEmpty: {
                        message: '所属商户不能为空'
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: '应用名称不能为空'
                    }
                }
            }
        }
    });
    validator.data('bootstrapValidator').validate();//启用验证
    return validator.data('bootstrapValidator').isValid()
}
