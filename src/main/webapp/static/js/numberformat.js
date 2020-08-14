Number.prototype.format = function (n, f) {
    n = n > 0 && n <= 20 ? n : 2;
    var s = parseFloat((this + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1], t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return (f ? f : "") + t.split("").reverse().join("") + "." + r;
}