$(function () {
    $("a.menu-but").click(function () {
        var that = $(this);
        var id = that.attr("id");
        addTabs({
            id: id, title: that.find("span").html(), url: id, delegate: function () {
                reset(that);
            }
        });
        reset(that);
    });

    $("#dashboard").trigger('click');
})

function reset(that) {
    $(".nav-list li").removeClass("active open");
    that.parent().parent().show();
    that.parent().parent().parent().addClass("active open");
    that.parent().addClass("active");
}
$("#logout").click(function () {
    $.post("doLogout", null, function () {
        location.reload();
    });//登出
});