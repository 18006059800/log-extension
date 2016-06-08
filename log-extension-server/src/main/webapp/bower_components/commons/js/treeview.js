var newJson = [];

function handleResult(data) {
	GetFirstNode(data);
	return newjson;
}

function handleData(data) {
	var root = {
		id : -1,
		name : "请选择部门",
		pid : null
	};
	for (var i = 0; i < data.length; i++) {
		var temp = data[i];
		if (temp.pid == null) {
			temp.pid = -1;
		}
	}
	data.push(root);
	return data;
}

function GetFirstNode(data) {
	data = handleData(data);
	for (var i = 0; i < data.length; i++) {
		if (data[i].pid == null || data[i].pid == "") {
			var node = data[i];
			node.text = data[i].name;
			newJson.push(node);
			CreateNewJson(data, data[i].id, node);
		}
	}
}

function CreateNewJson(data, parentid, node) {
	if (!data)
		return;
	if (data.length <= 0)
		return;
	for (var i = 0; i < data.length; i++) {
		if (data[i].pid == parentid) {
			var newnode = data[i];
			newnode.text = data[i].name;
			if (node.nodes == undefined) {
				createJson("nodes", new Array(newnode), node);
			} else {
				node.nodes.push(newnode);
			}
			CreateNewJson(data, newnode.id, newnode);
		}
	}
}

function createJson(prop, val, node) {
	// 如果 val 被忽略
	if (typeof val === "undefined") {
		// 删除属性
		delete node[prop];
	} else {
		// 添加 或 修改
		node[prop] = val;
	}
}