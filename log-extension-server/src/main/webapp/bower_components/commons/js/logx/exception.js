var exceptionMessageJs = {
    // 加载应用信息
    loadAppInfo : (function () {
        $.get("/app/list", function (data) {
            if (null == data) {
                return;
            }

            var appSet = new HashSet();

            for (var i = 0; i < data.length; i++) {
                appSet.add(data[i].name);
            }

            var ops = "";
            appSet.values().forEach(function (e) {
                ops += "<option value='" + e + "'>" + e + "</option>";
            });
            $("#domain-id").append(ops);
        });

    }),

    domainChanged : (function () {
        $("#className-id").empty();
        $("#classMethod-id").empty();
        $("#className-id").prepend("<option value=''>请选择</option>");
        $("#classMethod-id").prepend("<option value=''>请选择</option>");
        exceptionMessageJs.loadClassInfo();
    }),

    // 加载类信息
    loadClassInfo : (function () {
        var appName = $("#domain-id").val();
        $.get("/app/findSelectedMessage?domain=" + appName, function (data) {
            if (null == data) {
                return;
            }
            var classNameSet = new HashSet();
            for(var i = 0; i < data.length; i++) {
                classNameSet.add(data[i].className);
            }

            var ops = "";
            classNameSet.values().forEach(function (e) {
                ops += "<option value='" + e + "'>" + e + "</option>";
            });
            
            $("#className-id").append(ops);
        });
    }),

    classNameChanged : (function () {
        $("#classMethod-id").empty();
        $("#classMethod-id").prepend("<option value=''>请选择</option>");
        exceptionMessageJs.loadMethodInfo();

    }),

    // 加载方法信息
    loadMethodInfo : (function () {
        var appName = $("#domain-id").val();
        var className = $("#className-id").val();

        $.get("/app/findSelectedMessage?domain=" + appName + "&className=" + className, function (data) {
            if (null == data) {
                return;
            }

            var classMethodSet = new HashSet();
            for (var i = 0; i < data.length; i++) {
                classMethodSet.add(data[i].classMethod);
            }

            var ops = "";
            classMethodSet.values().forEach(function (e) {
                ops += "<option value='" + e + "'>" + e + "</option>";
            });
            $("#classMethod-id").append(ops);
        });

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

