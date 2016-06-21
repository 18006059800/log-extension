var rootMessageJs = {
    // 加载应用信息
    loadAppInfo : (function () {
        $.get("/app/list", function (data) {
            if (null == data) {
                return;
            }
            var ops = "";
            for (var i = 0; i < data.length; i++) {
                ops += "<option value='" + data[i].name + "'>" + data[i].name + "</option>";
            }
            $("#domain-id").append(ops);
        });
    }),

    domainChanged : (function () {
        $("#className-id").empty();
        $("#classMethod-id").empty();
        $("#className-id").prepend("<option value=''>请选择</option>");
        $("#classMethod-id").prepend("<option value=''>请选择</option>");
        rootMessageJs.loadClassInfo();
    }),

    // 加载类信息
    loadClassInfo : (function () {
        var appName = $("#domain-id").val();
        $.get("/app/findSelectedMessage?domain=" + appName, function (data) {
            if (null == data) {
                return;
            }
            var ops = "";
            for (var i = 0; i < data.length; i++) {
                ops += "<option value='" + data[i].className + "'>" + data[i].className + "</option>";
            }
            $("#className-id").append(ops);
        });
    }),

    classNameChanged : (function () {
        $("#classMethod-id").empty();
        $("#classMethod-id").prepend("<option value=''>请选择</option>");


    }),

    // 加载方法信息
    loadMethodInfo : (function () {

    }),

    // 多条件分页查询
    query : (function () {
        var domain = $("#domain-id").val();
        if (null == domain || '' == domain) {
            alert("请选择应用");
            return;
        }
        $("#query-message-form").submit();
    })
   
};
