var chainJs = {
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
        chainJs.loadClassInfo();
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
        chainJs.loadMethodInfo();
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
    }),
    initPage:(function (data) {
        var virtual = {"id": "-1", "pid": null};
        console.log("======添加虚拟节点前:" + JSON.stringify(data));
        data = addVirtual(data, virtual);

        console.log("======添加虚拟节点后:" + JSON.stringify(data));
        var result = func(data);
        console.log("======func======:" + JSON.stringify(result));

        result = removeVirtual(result, virtual);
        console.log("======removeVirtual======:" + JSON.stringify(result));


        var finalResult = chainJs.renderTreeGrid(result);

        $("#treegrid tbody").html(finalResult);
        $('.tree').treegrid({
            expanderExpandedClass: 'glyphicon glyphicon-minus',
            expanderCollapsedClass: 'glyphicon glyphicon-plus'
        });
    }),
    renderTreeGrid: (function (result) {

        var content = "";
        for (var i = 0; i < result.length; i++) {
            var t = result[i];
            if (!t.pid) {
                content += "<tr class='treegrid-" + t.id + "' id='" + t.id + "'>";
            } else {
                content += "<tr class='treegrid-" + t.id + " treegrid-parent-" + t.pid + "' id='" + t.id + "'>";
            }
            content += "<td>" + t.domain + "</td>";
            content += "<td>" + t.className + "</td>";
            content += "<td>" + t.classMethod + "</td>";
            content += "<td>" + !t.hasError + "</td>";
            content += "</tr>";
        }
        return content;
    })

   
};

$(function () {
    chainJs.loadAppInfo();
    var substr = window.location.search.substr(1);
    var rootMessageId = urlUtilJS.getUrlParam(substr, 'rootMessageId');
    var error = urlUtilJS.getUrlParam(substr, 'error');
    if (rootMessageId) {
        var url = "/app/getShowChainMessage?rootMessageId=" + rootMessageId;
        if (error && '' != error) {
            url = "/app/getShowChainMessage?rootMessageId=" + rootMessageId + "&error=" + error;
        }
        $.get(url, function (data) {
            chainJs.initPage(data)
        });
    }

});

