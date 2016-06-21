document.write(" <script  language=\"javascript\"  src=\"../scripts/commons/js/url-util.js \"  > <\/script>");
var pager = {
	page : 0,
	size : 20,
	totalPages : 0,
	elementNumber : 0,
	totalElements : 0,
	url : "",
	queryString : ""
};

/**
 * 初始化分页组件
 */
function initPager() {
	var firstPageResult = firstPage();
	var prevPageResult = prevPage();
	var nextPageResult = nextPage();
	var endPageResult = endPage();
	var pageInfoResult = pageInfo();
	$("#pager-first").attr("href", firstPageResult);
	$("#pager-prev").attr("href", prevPageResult);
	$("#pager-next").attr("href", nextPageResult);
	$("#pager-end").attr("href", endPageResult);
	$("#pager-info").text(pageInfoResult);
}

/**
 * 首页
 */
function firstPage() {
	var nUrl = "";
	if (isKeyContains(pager.queryString, "page")) {
		nUrl = replaceParamVal(pager.url, "page", 0);
		return nUrl;
	}
	return pager.url;
}

/**
 * 上一页
 */
function prevPage() {
	var nUrl = "";
	if (isKeyContains(pager.queryString, "page")) {
		nUrl = replaceParamVal(pager.url, "page",
				(pager.page - 1 > 0 ? pager.page - 1 : 0));
		return nUrl;
	}
	nUrl = addParam(pager.url, "page",
			(pager.page - 1 > 0 ? pager.page - 1 : 0));
	return nUrl;
}

/**
 * 下一页
 */
function nextPage() {
	var nUrl = "";
	if (isKeyContains(pager.queryString, "page")) {
		nUrl = replaceParamVal(pager.url, "page",
				(pager.page + 1 < pager.totalPages  ? pager.page + 1
						: (pager.totalPages -1 < 0 ? 0 : pager.totalPages -1)));
		return nUrl;
	}

	nUrl = addParam(pager.url, "page",
			(pager.page + 1 < pager.totalPages ? pager.page + 1
					: (pager.totalPages -1 <  0 ? 0 : pager.totalPages -1)));
	
	return nUrl;
}

/**
 * 末页
 */
function endPage() {
	var nUrl = "";
	if (isKeyContains(pager.queryString, "page")) {
		nUrl = replaceParamVal(pager.url, "page", (pager.totalPages - 1 < 0 ? 0 :pager.totalPages - 1 ));
		return nUrl;
	}
	return pager.url;
}

/**
 * 分页信息
 */
function pageInfo() {
	var result = "共" + pager.page + " / " + pager.totalPages + "页";
	return result;
}

/**
 * 改变单页数量
 */
function changePagerSize() {
	// pager.size = 50;
}
