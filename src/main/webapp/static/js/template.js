var table;
var channels = '<select class="form-control" name="channelId">';
$(function () {
    table = $('.table').bootstrapTable({
        url: 'template/search',
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
                channelId: $("#channel").val(),
                tempName: $("#tempName").val(),
                groupName: $("#groupName").val()
            }
        },
        responseHandler: function (rs) {
            if (rs && rs.code == 200 && rs.content)
                return {
                    rows: rs.content.list,
                    total: (rs.content.totalRows ? rs.content.totalRows : 0)
                }
            return null;
        },
        paginationLoop: false,
        columns: [
            {field: 'tempName', title: '模版编码'},
            {field: 'channelCode', title: '所属通道'},
            {field: 'groupName', title: '模版分组'},
            {field: 'content', title: '模版内容'},
            {field: 'desc', title: '模版描述'},
            {
                field: 'action', title: '操作',
                formatter: function (value, row, index) {
                    return '<div class="action-buttons">' +
                        '<a class="green edit" href="#" title="修改模版"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                        '<a class="red remove" href="#" title="删除模版"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
                },
                events: {
                    'click .edit': function (e, value, row, index) {
                        $.confirm(edit(row));
                    },
                    'click .remove': function (e, value, row, index) {
                        $.confirm(remove(row.id));
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

    $.getJSON("channel/search", {}, function (rs) {
        var channel = $("#channel");
        if (rs && rs.code == 200 && rs.content) {
            $.each(rs.content.list, function (index, val) {
                channels += '<option value="' + val.id + '">' + val.channelName + '</option>';
                channel.append('<option value="' + val.id + '">' + val.channelName + '</option>');
            });
            channels += '</select>'
        }
    });
});

function edit(row) {
    return {
        icon: row ? 'fa fa-pencil' : 'fa fa-plus',
        type: 'blue',
        title: row ? '编辑模版' : '新增模版',
        animationSpeed: 300,
        columnClass: 'col-md-8 col-md-offset-2 col-xs-12',
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

function remove(id) {
    return {
        type: 'red',
        animationSpeed: 300,
        title: false,
        content: '确认删除该模版吗？',
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn btn-primary',
                action: function () {
                    deleteBy(id);
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn btn-default'
            }
        }
    };
}

function deleteBy(id) {
    $.post("template/remove", {id: id}, function (rs) {
        if (rs && rs.code == 200) {
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "模版删除失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function modifyBy(id, that) {
    var params = $('.form').serializeArray();
    params.push({name: 'id', value: id});
    $.post("template/update", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "模版修改失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}

function add(that) {
    var params = $('.form').serializeArray();
    $.post("template/save", params, function (rs) {
        if (rs && rs.code == 200) {
            that.close();
            table.bootstrapTable('refresh');
        } else {
            $.alert({
                type: 'red',
                title: '系统提示',
                content: rs.msg ? rs.msg : "模版添加失败",
                icon: 'glyphicon glyphicon-info-sign'
            });
        }
    }, 'json');
}


function getContent(row) {
    if (row) {
        var cs = $(channels);
        cs.find("option[value='" + row.channelId + "']").attr("selected", true);
        return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
            '<div class="form-group"><label class="col-md-2 control-label">模版编码<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="tempName" type="text" placeholder="请输入名称" class="form-control" value="' + row.tempName + '"/></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">所属应用<font color="red">*</font></label>' +
            '<div class="col-md-10">' + (cs.prop("outerHTML")) + '</div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">模版分组<font color="red">*</font></label>' +
            '<div class="col-md-10"><input name="groupName" type="text" placeholder="请输入分组" class="form-control" value="' + (row.groupName ? row.groupName : '') + '"/></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">模版数据<font color="red">*</font></label>' +
            '<div class="col-md-10"><textarea name="content" maxlength="512" rows="6" placeholder="请输入内容" class="form-control">' + (row.content ? row.content : '') + '</textarea></div></div>' +
            '<div class="form-group"><label class="col-md-2 control-label">模版描述</label>' +
            '<div class="col-md-10"><textarea name="desc" maxlength="512" placeholder="请输入描述" class="form-control">' + (row.desc ? row.desc : '') +
            '</textarea></div></div></form>';
    }

    return '<form class="form-horizontal form" role="form" novalidate="novalidate">' +
        '<div class="form-group"><label class="col-md-2 control-label">模版编码<font color="red">*</font></label>' +
        '<div class="col-md-10"><input name="tempName" type="text" placeholder="请输入名称" class="form-control"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">所属通道<font color="red">*</font></label>' +
        '<div class="col-md-10">' + channels + '</div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">模版分组</label>' +
        '<div class="col-md-10"><input name="groupName" type="text" placeholder="请输入分组" class="form-control"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">模版内容<font color="red">*</font></label>' +
        '<div class="col-md-10"><textarea name="content" maxlength="512" rows="6" placeholder="请输入内容" class="form-control"/></div></div>' +
        '<div class="form-group"><label class="col-md-2 control-label">模版描述</label>' +
        '<div class="col-md-10"><textarea name="desc" maxlength="512" placeholder="请输入描述" class="form-control"/></div></div></form>';
}

function validate() {
    var validator = $('.form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            tempName: {
                validators: {
                    notEmpty: {
                        message: '模版编码不能为空'
                    }
                }
            },
            channelId: {
                validators: {
                    notEmpty: {
                        message: '所属应用不能为空'
                    }
                }
            },
            content: {
                validators: {
                    notEmpty: {
                        message: '模版内容不能为空'
                    }
                }
            }
        }
    });
    validator.data('bootstrapValidator').validate();//启用验证
    return validator.data('bootstrapValidator').isValid()
}
