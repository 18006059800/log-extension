var urlUtilJS = {
	getUrlParam: (function (queryString, name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = queryString.match(reg);  //匹配目标参数
		if (r != null)
			return decodeURI(r[2]);
		return null; // 返回参数值
	})
};

// /**
// * 替换URL属性值
// *
// * @param url
// * @param arg
// * @param arg_val
// * @returns {String}
// */
// function changeURLArg(url, arg, arg_val) {
// var pattern = arg + '=([^&]*)';
// var replaceText = arg + '=' + arg_val;
// if (url.match(pattern)) {
// var tmp = '/(' + arg + '=)([^&]*)/gi';
// tmp = url.replace(eval(tmp), replaceText);
// return tmp;
// } else {
// if (url.match('[\?]')) {
// return url + '&' + replaceText;
// } else {
// return url + '?' + replaceText;
// }
// }
// return url + '\n' + arg + '\n' + arg_val;
// }

/**
 * 替换URL中属性
 * 
 * @param url
 * @param paramName
 * @param replaceWith
 */
function replaceParamVal(url, paramName, replaceWith) {
	var re = eval('/(' + paramName + '=)([^&]*)/gi');
	var nUrl = url.replace(re, paramName + '=' + replaceWith);
	return nUrl;
}

/**
 * 移出属性
 * 
 * @param url
 * @param paramName
 * @returns
 */
function removeParamVal(url, paramName) {
	var re = eval('/(' + paramName + '=)([^&]*)/gi');
	var nUrl = url.replace(re, '');
	return nUrl;
}

/**
 * 增加属性
 * 
 * @param url
 * @param paramName
 * @param paramValue
 */
function addParam(url, paramName, paramValue) {
	var hasParams = url.split("?");
	var nUrl = "";
	if (hasParams.length > 1) {
		nUrl = url + "&" + paramName + "=" + paramValue;
		return nUrl;
	}
	nUrl = url + "?" + paramName + "=" + paramValue;
	return nUrl;
}

/**
 * 是否包含某个属性值
 * 
 * @param name
 * @returns {Boolean}
 */
function isKeyContains(queryString, name) {
	var result = getUrlParam(queryString, name);
	if (null == result) {
		return false;
	}
	return true;
}
