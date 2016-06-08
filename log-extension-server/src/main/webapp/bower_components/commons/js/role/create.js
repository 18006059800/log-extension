/**
 * 初始化页面
 */
$(function () {
	
	// 高亮菜单
	setActiveMenu("menu-role");
	// 验证表单
	validateForm();
	
});

function validateForm() {
	$("#role-form").validate({
		rules: {
			name: {
				required: true,
				maxlength: 10
			},
			"app.id": {
				required: true,
				minlength:1
			}
		},
		messages: {
			name: {
				required: "请输入角色名称",
				maxlength: "最大长度不能超过10个字符"
			},
			"app.id": {
				required: "请选择应用",
				minlength:"请选择应用"
			}
		}
	});
}