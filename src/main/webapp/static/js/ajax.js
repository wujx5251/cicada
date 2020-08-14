$.ajaxSetup({
    //完成请求后触发。即在success或error触发后触发
    complete: function (request, textStatus) {
        // 一般这里写一些公共处理方法，比如捕获后台异常，展示到页面
        var status = request.status;
        if (status == 508) {
            var top = getTopWinow();
            top.location.href = 'login';
        }
    },
    error: function (jqXHR, textStatus, errorMsg) { // 出错时默认的处理函数
        console.log('发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg);
    }
});

function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
}