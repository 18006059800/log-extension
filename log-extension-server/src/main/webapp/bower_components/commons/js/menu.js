/**
 * 设置高亮的节点
 * 
 * @param active
 *            高亮节点的主键
 * 
 */
function setActiveMenu(active) {
	$("#menu li").removeClass();
	$("#" + active).addClass("active");
}