// ------------------------------------常量----------------------------------
baseinfo.operatorCount = {
	defaultV : 0,
	successV : 1,
	failureV : -1
}; // 操作返回值 1为成功，-1为失败
baseinfo.delAgencyType = 'MANY'; // 删除的类型，批量
baseinfo.delType = 'MANY'; // 删除的类型，批量
baseinfo.viewState = {
	add : 'ADD',
	update : 'UPDATE',
	view : 'VIEW'
}; // 查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.booleanType = {
	yes : 'Y',
	no : 'N'
}; // booleanType 对应后台常量 "布尔类型"
baseinfo.effectiveState = {
	active : 'Y',
	inactive : 'N'
}; // booleanType 对应后台常量 "生效/未生效"
baseinfo.booleanStr = {
	yes : 'true',
	no : 'false'
}; // booleanStr 从复选框中得到值
baseinfo.operateType = {
	save : 'SAVE'
}; // 标识 是否 保存操作
baseinfo.levelType = {
	p : 'PARENT',
	c : 'CHILDREN'
}; // 标识 是父容器还是子容器
/**
 * .
 * <p>
 * AJAX请求<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param url请求地址,PARAMS参数,successFn调用成功,
 *            exceptionFn调用异常,FAILFN调用失败,ASYNC是否异步
 * @时间 2012-12-07
 */
baseinfo.requestAjaxJson = function(url, params, successFn, exceptionFn,
		failFn, async) {
	Ext.Ajax.request({
		url : url,
		async : Ext.isEmpty(async) ? true : false,// 默认是异步
		jsonData : params,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				exceptionFn(result);
			}
		},
		exception : function(response) {
			exceptionFn(Ext.decode(response.responseText));
		}
			// ,failure:function(response){//平台已拦截
			// var result = Ext.decode(response.responseText);
			// failFn(result);
			// }
		});
};
/**
 * .
 * <p>
 * 填值方法<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param form[]需要加载数据的form,formRecord[]需要加载的数据model
 *            grid[]需要加载数据的grid,girdData[]需要加载的数据data
 * @时间 2012-12-07
 */
baseinfo.formReset = function(form, formRecord, grid, girdData) {
	if (!Ext.isEmpty(form) && !Ext.isEmpty(formRecord)) {
		Ext.Array.each(form, function(name, index, countriesItSelf) {
					form[index].loadRecord(formRecord[index]);
				});
	}
	if (!Ext.isEmpty(grid)) {
		Ext.Array.each(grid, function(name, index, countriesItSelf) {
					if (Ext.isEmpty(girdData)) {
						grid[index].store.loadData([]);
					} else {
						grid[index].store.loadPage(1);
					}
				});
	}
};
/**
 * .
 * <p>
 * form表单所有元素 设置 readOnly值<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param form需要设值的表单，flag表单readOnly值
 * @时间 2012-12-13
 */
baseinfo.formSetReadOnly = function(flag, form) {
	var arr = form.items.items;
	if (!Ext.isEmpty(arr)) {
		for (var i = 0; i < arr.length; i++) {
			arr[i].setReadOnly(flag);
		}
	}
};
baseinfo.formFieldSetReadOnly = function(flag, form) {
	var arr = form.query('field');
	if (!Ext.isEmpty(arr)) {
		for (var i = 0; i < arr.length; i++) {
			arr[i].setReadOnly(flag);
		}
	}
};
/**
 * .
 * <p>
 * 消息提示框 ，无国际化<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.showInfoMsg = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};

/**
 * .
 * <p>
 * 查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.operateWinBtn = function(win, viewState, operateType) {
	// 查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
	if (baseinfo.viewState.view === viewState) {
		var btnArr = win.query('button');
		for (var i = 0; i < btnArr.length; i++) {
			btnArr[i].setDisabled(i != 2);
		}
	} else if (!Ext.isEmpty(operateType)
			&& baseinfo.operateType.save === operateType) {
		var btnArr = win.query('button');
		for (var i = 0; i < btnArr.length; i++) {
			btnArr[i].setDisabled(i > 2);
		}
	}
};
/**
 * .
 * <p>
 * 覆盖Ext.form.RadioGroup的setValue方法<br/> item.getRawValue全为false
 * <p>
 * 
 * @author 张斌
 * @时间 2012-3-25
 */
Ext.override(Ext.form.RadioGroup, {
			setValue : function(v) {
				if (this.rendered)
					this.items.each(function(item) {
								item.setValue(item.inputValue == v);
							});
				else {
					for (var k = 0; k < this.items.items.length; k++) {
						this.items.items[k]
								.setValue(this.items.items[k].inputValue == v);
					}
				}
			}
		});

/**
 * .
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * 
 * @param storeId
 * @param model
 *            store所用到的model名
 * @param fields
 *            store所用到的fields
 * @returns store 返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						model : model,
						data : data
					});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						fields : fields,
						data : data
					});
		}
	}
	return store;
};
// Ajax请求--json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

// Ajax请求--文件表单的请求
baseinfo.requestFileUploadAjax = function(url, form, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				form : form,
				isUpload : true,
				success : function(form, action) {
					var result = action.result;
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(form, action) {
					var result = action.result;
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

// Ajax请求--非json字符串
baseinfo.requestAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

/**
 * .
 * <p>
 * 设置元素为readOnly<br/>
 * <p>
 * 
 * @param readOnlyIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setReadOnly = function(readOnlyIdList) {
	for (var i = 0; i < readOnlyIdList.length; i++) {
		Ext.getCmp(readOnlyIdList[i]).setReadOnly(true);
		Ext.getCmp(readOnlyIdList[i]).addCls('readonly');
	}
};

/**
 * .
 * <p>
 * 设置元素为隐藏并且销毁，使其不在校验<br/>
 * <p>
 * 
 * @param hiddenIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHiddenAndDestroy = function(hiddenIdList) {
	for (var i = 0; i < hiddenIdList.length; i++) {
		Ext.getCmp(hiddenIdList[i]).hide();
		Ext.getCmp(hiddenIdList[i]).destroy();
	}
};
/**
 * .
 * <p>
 * 设置元素为隐藏<br/>
 * <p>
 * 
 * @param hiddenIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHidden = function(hiddenIdList) {
	for (var i = 0; i < hiddenIdList.length; i++) {
		Ext.getCmp(hiddenIdList[i]).hide();
	}
};
/**
 * .
 * <p>
 * 设置元素为销毁<br/>
 * <p>
 * 
 * @param destoryIdList
 *            设置为destory的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDestroy = function(destoryIdList) {
	for (var i = 0; i < destoryIdList.length; i++) {
		Ext.getCmp(destoryIdList[i]).destroy();
	}
};
/**
 * .
 * <p>
 * 设置元素为不可用<br/>
 * <p>
 * 
 * @param disabledIdList
 *            设置为Disabled的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDisabled = function(disabledIdList) {
	for (var i = 0; i < disabledIdList.length; i++) {
		Ext.getCmp(disabledIdList[i]).setDisabled(true);
	}
};
/**
 * .
 * <p>
 * 清楚事件<br/>
 * <p>
 * 
 * @param clearIdList
 *            设置为清楚时间的元素ID数组
 * @author 张斌
 * @时间 2012-3-22
 */
baseinfo.clearListeners = function(clearIdList) {
	for (var i = 0; i < clearIdList.length; i++) {
		Ext.getCmp(clearIdList[i]).clearListeners();
	}
};

/**
 * .
 * <p>
 * 数组中是否有空值<br/>
 * <p>
 * 
 * @param array
 *            数组
 * @author 张斌
 * @时间 2012-3-24
 */
baseinfo.isHaveEmpty = function(array) {
	var boolen = false;
	for (var i = 0; i < array.length; i++) {
		if (Ext.isEmpty(array[i])) {
			boolen = true;
			return boolen;
		}
	}
	return boolen;
};
/**
 * .
 * <p>
 * JS日期的format方法<br/>
 * <p>
 * 
 * @param format
 *            日期格式
 * @author 张斌
 * @时间 2012-3-23
 */
Date.prototype.format = function(format) {
	if (Ext.isEmpty(this) || this.getTime() == 0
			|| this.toString().indexOf('GMT') == -1) {
		return null;
	}
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	};

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	};
	return format;
};

/**
 * .
 * <p>
 * 根据传的参数生成查询条件<br/>
 * <p>
 * 
 * @param modelList
 *            要转换的Modellist
 * @returns dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.changeModelListToDataList = function(modelList) {
	var dataList = new Array();
	for (var i = 0; i < modelList.length; i++) {
		dataList.push(modelList[i].data);
	}
	return dataList;
};
/**
 * .
 * <p>
 * 数据的将全局变量复制出来<br/>
 * <p>
 * 
 * @param modelList
 *            要转换的Modellist
 * @returns dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.copyModelListToDataList = function(modelList) {
	var dataList = new Array();
	for (var i = 0; i < modelList.length; i++) {
		dataList.push(modelList[i]);
	}
	return dataList;
};
/**
 * @功能：为js中的STRING加上trim方法
 * @作者： 张斌
 * @创建时间：2012-02-20
 */
String.prototype.trim = function() {
	// 用正则表达式将前后空格
	// 用空字符串替代。
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

// 警告
baseinfo.showWoringMessage = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				msg : message,
				// cls:'mesbox',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};
// 是和否选择
baseinfo.showQuestionMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.YESNO,
				icon : Ext.MessageBox.QUESTION,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						fun(e);
					}
				}
			});
};
// 信息
baseinfo.showInfoMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};
// 错误
baseinfo.showErrorMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.ERROR,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});
};
// ADD -ALL
baseinfo.addAll = function(list, all) {
	var newlist = new Array();
	newlist.push(all);
	for (var i = 0; i < list.length; i++) {
		newlist.push(list[i]);
	}
	return newlist;
};

// changeCodeToName(LIST)
baseinfo.changeCodeToName = function(list, code) {
	var name = '';
	for (var i = 0; i < list.length; i++) {
		if (list[i].valueCode == code) {
			name = list[i].valueName;
		}
	}
	return name;
};
// changeCodeToName(store)
baseinfo.changeCodeToNameStore = function(store, code) {
	var name = '';
	if (!Ext.isEmpty(store)) {
		store.each(function(record) {
					if (record.get('valueCode') == code) {
						name = record.get('valueName');
					}
				});
	}
	return name;
};
// ------------------------------------常量----------------------------------
baseinfo.operatorCount = {
	defaultV : 0,
	successV : 1,
	failureV : -1
}; // 操作返回值 1为成功，-1为失败
baseinfo.delAgencyType = 'MANY'; // 删除的类型，批量
baseinfo.delType = 'MANY'; // 删除的类型，批量
baseinfo.viewState = {
	add : 'ADD',
	update : 'UPDATE',
	view : 'VIEW'
}; // 查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.booleanType = {
	yes : 'Y',
	no : 'N'
}; // booleanType 对应后台常量 "布尔类型"
baseinfo.effectiveState = {
	active : 'Y',
	inactive : 'N'
}; // booleanType 对应后台常量 "生效/未生效"
baseinfo.booleanStr = {
	yes : 'true',
	no : 'false'
}; // booleanStr 从复选框中得到值
baseinfo.operateType = {
	save : 'SAVE'
}; // 标识 是否 保存操作
baseinfo.levelType = {
	p : 'PARENT',
	c : 'CHILDREN'
}; // 标识 是父容器还是子容器
/**
 * .
 * <p>
 * AJAX请求<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param url请求地址,PARAMS参数,successFn调用成功,
 *            exceptionFn调用异常,FAILFN调用失败,ASYNC是否异步
 * @时间 2012-12-07
 */
baseinfo.requestAjaxJson = function(url, params, successFn, exceptionFn,
		failFn, async) {
	Ext.Ajax.request({
		url : url,
		async : Ext.isEmpty(async) ? true : false,// 默认是异步
		jsonData : params,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				exceptionFn(result);
			}
		},
		exception : function(response) {
			exceptionFn(Ext.decode(response.responseText));
		}
			// ,failure:function(response){//平台已拦截
			// var result = Ext.decode(response.responseText);
			// failFn(result);
			// }
		});
};
/**
 * .
 * <p>
 * 填值方法<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param form[]需要加载数据的form,formRecord[]需要加载的数据model
 *            grid[]需要加载数据的grid,girdData[]需要加载的数据data
 * @时间 2012-12-07
 */
baseinfo.formReset = function(form, formRecord, grid, girdData) {
	if (!Ext.isEmpty(form) && !Ext.isEmpty(formRecord)) {
		Ext.Array.each(form, function(name, index, countriesItSelf) {
					form[index].loadRecord(formRecord[index]);
				});
	}
	if (!Ext.isEmpty(grid)) {
		Ext.Array.each(grid, function(name, index, countriesItSelf) {
					if (Ext.isEmpty(girdData)) {
						grid[index].store.loadData([]);
					} else {
						grid[index].store.loadPage(1);
					}
				});
	}
};
/**
 * .
 * <p>
 * form表单所有元素 设置 readOnly值<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param form需要设值的表单，flag表单readOnly值
 * @时间 2012-12-13
 */
baseinfo.formSetReadOnly = function(flag, form) {
	var arr = form.items.items;
	if (!Ext.isEmpty(arr)) {
		for (var i = 0; i < arr.length; i++) {
			arr[i].setReadOnly(flag);
		}
	}
};
baseinfo.formFieldSetReadOnly = function(flag, form) {
	var arr = form.query('field');
	if (!Ext.isEmpty(arr)) {
		for (var i = 0; i < arr.length; i++) {
			arr[i].setReadOnly(flag);
		}
	}
};
/**
 * .
 * <p>
 * 消息提示框 ，无国际化<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.showInfoMsg = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};

/**
 * .
 * <p>
 * 查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1<br/>
 * <p>
 * 
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.operateWinBtn = function(win, viewState, operateType) {
	// 查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
	if (baseinfo.viewState.view === viewState) {
		var btnArr = win.query('button');
		for (var i = 0; i < btnArr.length; i++) {
			btnArr[i].setDisabled(i != 2);
		}
	} else if (!Ext.isEmpty(operateType)
			&& baseinfo.operateType.save === operateType) {
		var btnArr = win.query('button');
		for (var i = 0; i < btnArr.length; i++) {
			btnArr[i].setDisabled(i > 2);
		}
	}
};
/**
 * .
 * <p>
 * 覆盖Ext.form.RadioGroup的setValue方法<br/> item.getRawValue全为false
 * <p>
 * 
 * @author 张斌
 * @时间 2012-3-25
 */
Ext.override(Ext.form.RadioGroup, {
			setValue : function(v) {
				if (this.rendered)
					this.items.each(function(item) {
								item.setValue(item.inputValue == v);
							});
				else {
					for (var k = 0; k < this.items.items.length; k++) {
						this.items.items[k]
								.setValue(this.items.items[k].inputValue == v);
					}
				}
			}
		});

/**
 * .
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * 
 * @param storeId
 * @param model
 *            store所用到的model名
 * @param fields
 *            store所用到的fields
 * @returns store 返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						model : model,
						data : data
					});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						fields : fields,
						data : data
					});
		}
	}
	return store;
};
// Ajax请求--json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

// Ajax请求--文件表单的请求
baseinfo.requestFileUploadAjax = function(url, form, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				form : form,
				isUpload : true,
				success : function(form, action) {
					var result = action.result;
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(form, action) {
					var result = action.result;
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

// Ajax请求--非json字符串
baseinfo.requestAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};

/**
 * .
 * <p>
 * 设置元素为readOnly<br/>
 * <p>
 * 
 * @param readOnlyIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setReadOnly = function(readOnlyIdList) {
	for (var i = 0; i < readOnlyIdList.length; i++) {
		Ext.getCmp(readOnlyIdList[i]).setReadOnly(true);
		Ext.getCmp(readOnlyIdList[i]).addCls('readonly');
	}
};

/**
 * .
 * <p>
 * 设置元素为隐藏并且销毁，使其不在校验<br/>
 * <p>
 * 
 * @param hiddenIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHiddenAndDestroy = function(hiddenIdList) {
	for (var i = 0; i < hiddenIdList.length; i++) {
		Ext.getCmp(hiddenIdList[i]).hide();
		Ext.getCmp(hiddenIdList[i]).destroy();
	}
};
/**
 * .
 * <p>
 * 设置元素为隐藏<br/>
 * <p>
 * 
 * @param hiddenIdList
 *            设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHidden = function(hiddenIdList) {
	for (var i = 0; i < hiddenIdList.length; i++) {
		Ext.getCmp(hiddenIdList[i]).hide();
	}
};
/**
 * .
 * <p>
 * 设置元素为销毁<br/>
 * <p>
 * 
 * @param destroyIdList
 *            设置为destroy的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDestroy = function(destroyIdList) {
	for (var i = 0; i < destroyIdList.length; i++) {
		Ext.getCmp(destroyIdList[i]).destroy();
	}
};
/**
 * .
 * <p>
 * 设置元素为不可用<br/>
 * <p>
 * 
 * @param disabledIdList
 *            设置为Disabled的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDisabled = function(disabledIdList) {
	for (var i = 0; i < disabledIdList.length; i++) {
		Ext.getCmp(disabledIdList[i]).setDisabled(true);
	}
};
/**
 * .
 * <p>
 * 清楚事件<br/>
 * <p>
 * 
 * @param clearIdList
 *            设置为清楚时间的元素ID数组
 * @author 张斌
 * @时间 2012-3-22
 */
baseinfo.clearListeners = function(clearIdList) {
	for (var i = 0; i < clearIdList.length; i++) {
		Ext.getCmp(clearIdList[i]).clearListeners();
	}
};

/**
 * .
 * <p>
 * 数组中是否有空值<br/>
 * <p>
 * 
 * @param array
 *            数组
 * @author 张斌
 * @时间 2012-3-24
 */
baseinfo.isHaveEmpty = function(array) {
	var boolen = false;
	for (var i = 0; i < array.length; i++) {
		if (Ext.isEmpty(array[i])) {
			boolen = true;
			return boolen;
		}
	}
	return boolen;
};
/**
 * .
 * <p>
 * JS日期的format方法<br/>
 * <p>
 * 
 * @param format
 *            日期格式
 * @author 张斌
 * @时间 2012-3-23
 */
Date.prototype.format = function(format) {
	if (Ext.isEmpty(this) || this.getTime() == 0
			|| this.toString().indexOf('GMT') == -1) {
		return null;
	}
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	};

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	};
	return format;
};

/**
 * .
 * <p>
 * 根据传的参数生成查询条件<br/>
 * <p>
 * 
 * @param modelList
 *            要转换的Modellist
 * @returns dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.changeModelListToDataList = function(modelList) {
	var dataList = new Array();
	for (var i = 0; i < modelList.length; i++) {
		dataList.push(modelList[i].data);
	}
	return dataList;
};
/**
 * .
 * <p>
 * 数据的将全局变量复制出来<br/>
 * <p>
 * 
 * @param modelList
 *            要转换的Modellist
 * @returns dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.copyModelListToDataList = function(modelList) {
	var dataList = new Array();
	for (var i = 0; i < modelList.length; i++) {
		dataList.push(modelList[i]);
	}
	return dataList;
};
/**
 * @功能：为js中的STRING加上trim方法
 * @作者： 张斌
 * @创建时间：2012-02-20
 */
String.prototype.trim = function() {
	// 用正则表达式将前后空格
	// 用空字符串替代。
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

// 警告
baseinfo.showWoringMessage = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				msg : message,
				// cls:'mesbox',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};
// 是和否选择
baseinfo.showQuestionMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.YESNO,
				icon : Ext.MessageBox.QUESTION,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						fun(e);
					}
				}
			});
};
// 信息
baseinfo.showInfoMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});

	// setTimeout(function(){
	// Ext.Msg.hide();
	// }, 3000);
};
// 错误
baseinfo.showErrorMes = function(message, fun) {
	var len = message.length;
	Ext.Msg.show({
				title : 'FOSS提醒您:',
				width : 110 + len * 15,
				msg : '<div id="message">' + message + '</div>',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.ERROR,
				callback : function(e) {
					if (!Ext.isEmpty(fun)) {
						if (e == 'ok') {
							fun();
						}
					}
				}
			});
};
// ADD -ALL
baseinfo.addAll = function(list, all) {
	var newlist = new Array();
	newlist.push(all);
	for (var i = 0; i < list.length; i++) {
		newlist.push(list[i]);
	}
	return newlist;
};

// changeCodeToName(LIST)
baseinfo.changeCodeToName = function(list, code) {
	var name = '';
	for (var i = 0; i < list.length; i++) {
		if (list[i].valueCode == code) {
			name = list[i].valueName;
		}
	}
	return name;
};
// changeCodeToName(store)
baseinfo.changeCodeToNameStore = function(store, code) {
	var name = '';
	if (!Ext.isEmpty(store)) {
		store.each(function(record) {
					if (record.get('valueCode') == code) {
						name = record.get('valueName');
					}
				});
	}
	return name;
};
/*
 * 转换long类型为日期
 */
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
/*
 * ADD -ALL
 */
baseinfo.addAll = function(store, all) {
	if (!Ext.isEmpty(store)) {
		store.add(all);
	}
	return store;
};
/*
 * Ajax请求--json
 */
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};
/**
 * 公共方法，通过storeId和model创建STORE
 * 
 * @param {Object}
 *            storeId
 * @param {Object}
 *            model store所用到的model名
 * @param {Object}
 *            fields store所用到的fields
 * @param {Object}
 *            data
 * @return {Object} 返回创建的store
 */
baseinfo.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						model : model,
						data : data
					});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						fields : fields,
						data : data
					});
		}
	}
	return store;
};
// --------------------------------------baseinfo----------------------------------------
/*
 * 库区MODEL
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'organizationCode', // 组织
						type : 'string'
					}, {
						name : 'organizationName', // 组织名称
						type : 'string'
					}, {
						name : 'transferCode', // 外场编码
						type : 'string'
					}, {
						name : 'goodsAreaCode', // 库区编码
						type : 'string'
					}, {
						name : 'virtualCode', // 虚拟编码
						type : 'string'
					}, {
						name : 'goodsAreaName', // 库区名称
						type : 'string'
					}, {
						name : 'goodsAreaType', // 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等)
						type : 'string'
					}, {
						name : 'goodsType', // 货物类型（A货，B货）
						type : 'string'
					}, {
						name : 'arriveRegionCode', // 目的站
						type : 'string'
					}, {
						name : 'arriveRegionName', // 目的站名称
						type : 'string'
					}, {
						name : 'goodsAreaUsage', // 库区类别（长途，短途）
						type : 'string'
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'notes', // 备注
						type : 'string'
					}, {
						name : 'storageList' // 所关联的库位列表s
					}, {
						name : 'transferCode', // 外场编码
						type : 'string'
					}, {
						name : 'countingModeKp', // 计票方式(卡普)
						type : 'string'
					}, {
						name : 'countingModeAb', // 计票方式（AB）
						type : 'string'
					}, {
						name : 'countingMode', // 计票方式
						type : 'string'
					}, {
						name : 'asteriskCode', // 星标编码
						type : 'string'
					}, {
						name : 'goodsAreaWidth', // 库区宽度
						type : 'string'
					}, {
						name : 'goodsAreaLength', // 库区长度
						type : 'string'
					}, {
						name : 'goodsAreaHeight', // 库区高度
						type : 'string'
					}, {
						name : 'abscissa', // 横坐标
						type : 'string'
					}, {
						name : 'ordinate', // 纵坐标
						type : 'string'
					}, {
						name : 'distanceList' // 到各个月台的距离列表
					},{
					  name:'area',//面积
					  type:'String'
					}]
		});
baseinfo.goodsArea.goodsAreaType = 'BSE_GOODSAREA_TYPE'; // 库区类型
// baseinfo.goodsArea.goodsAreaType
// =
// 'BSE_GOODSAREA_TYPE';
baseinfo.goodsArea.goodsType = 'BSE_GOODS_TYPE'; // 货物类型
baseinfo.goodsArea.goodsAreaUsage = 'BSE_GOODSAREA_USAGE'; // 库区类别
baseinfo.goodsArea.asteriskType = 'ASTERISK_TYPE'; // 星标类型
baseinfo.goodsArea.goodsAreaTypeHZ = 'BSE_GOODSAREA_TYPE_COMMON'; // 库区类型的
// 混装库区
baseinfo.goodsArea.goodsAreaTypeKH = 'BSE_GOODSAREA_TYPE_FAST'; // 库区类型的 卡货库区
baseinfo.goodsArea.goodsAreaTypePH = 'BSE_GOODSAREA_TYPE_NORMAL'; // 库区类型的
// 普货库区
baseinfo.goodsArea.goodsAreaTypePX = 'BSE_GOODSAREA_TYPE_OTHER'; // 库区类型的偏线库区
// 获取星标线路配置员角色(库区权限分配需求dujunhui-187862)
baseinfo.getAsteriskMaintenanceRole = function() {
	if (Ext.Array.contains(FossUserContext.getCurrentUser().roleids,
			'TFR_ASTERISK_MAINTENANCE')) {// 包含星标线路配置员角色
		return true;
	} else {
		return false;
	}
};
// 获取一线角色(即外场经理角色)(库区权限分配需求dujunhui-187862)
baseinfo.getFrontLineRoles = function() {
	if (Ext.Array.contains(FossUserContext.getCurrentUser().roleids,
			'TFR_TRANSFER_MANAGER')) {// 包含外场经理角色
		return true;
	} else {
		return false;
	}
};
// ------------------------------------model---------------------------------------------------
/**
 * 库区的Store（Foss.baseinfo.goodsArea.GoodsAreaEntity）
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.goodsArea.GoodsAreaEntity', // 库区的MODEL
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo.realPath('queryGoodsAreaByCondition.action'), // 请求地址
				reader : {
					type : 'json',
					root : 'goodsAreaVo.goodsAreaEntityList', // 获取的数据
					totalProperty : 'totalCount' // 总个数
				}
			}
		});
// ----------------------------------------store---------------------------------
/**
 * 库区表单
 */
Ext.define('Foss.baseinfo.goodsArea.QueryGoodsAreaForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.goodsArea
			.i18n('foss.baseinfo.informationQueryInTheReservoirArea'), // 库区信息查询
	frame : true,
	collapsible : true,
	defaults : {
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 230,
	defaultType : 'textfield',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var all = {
			'valueName' : '全部',
			'valueCode' : ''
		};
		var goodsAreaTypeStore = baseinfo
				.addAll(
						FossDataDictionary
								.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaType),
						all);
		var goodsAreaUsageStore = baseinfo
				.addAll(
						FossDataDictionary
								.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaUsage),
						all);
		var goodsTypeStore = baseinfo.addAll(FossDataDictionary
						.getDataDictionaryStore(baseinfo.goodsArea.goodsType),
				all);
		var asteriskTypeStore = baseinfo
				.addAll(
						FossDataDictionary
								.getDataDictionaryStore(baseinfo.goodsArea.asteriskType),
						all);
		me.items = [{
			xtype : 'commontransfercenterselector',
			name : 'organizationCode',
			userCode : Ext.Array.contains(
					FossUserContext.getCurrentUser().roleids,
					'FOSS_SYSTEM_ADMIN') ? null : FossUserContext
					.getCurrentUserEmp().empCode,
			currentOrgCode : Ext.Array.contains(FossUserContext
							.getCurrentUser().roleids, 'FOSS_SYSTEM_ADMIN')
					? null
					: FossUserContext.getCurrentDeptCode(),
			organizationCode : null, // 组织编码
			fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.outfield'),
			columnWidth : 0.33,
			listeners : {
				select : function(comb, records, empo) {
					comb.organizationCode = records[0].get('orgCode');
				}
			}
		}, {
			name : 'goodsAreaName',
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaName'), // 库区名称
			xtype : 'textfield',
			columnWidth : 0.33
		}, {
			name : 'arriveRegionCode', // 目的站
			fieldLabel : '目的站',
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			salesDepartment : 'Y', // 营业部
			transferCenter : 'Y', // 外场
			airDispatch : 'Y', // 空运总调
			columnWidth : 0.33
		}, {
			name : 'goodsAreaType',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			value : '',
			columnWidth : 0.33,
			store : goodsAreaTypeStore,
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaType'), // 库区类型
			xtype : 'combo'
		}, {
			name : 'goodsType',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			value : '',
			columnWidth : 0.33,
			store : goodsTypeStore,
			fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.cargoType'), // 货物类型
			xtype : 'combo'
		}, {
			name : 'goodsAreaUsage',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			value : '',
			columnWidth : 0.33,
			store : goodsAreaUsageStore,
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaCategory'), // 库区类别
			xtype : 'combo'
		}, {
			xtype : 'container',
			columnWidth : 1,
			items : [{
						xtype : 'checkboxgroup',
						name : 'countingMode',
						// width: 120, //宽度220
						// columns: 0.33, //在上面定义的宽度上展示3列
						// columns:2,
						fieldLabel : '计票方式',
						items : [{
									boxLabel : '计票（卡普）',
									width : 100,
									name : 'countingModeKp'
								}, {
									boxLabel : '计票（AB）',
									width : 100,
									name : 'countingModeAb'
								}]
					}]
		}, {
			border : 1,
			xtype : 'container',
			columnWidth : 1,
			defaultType : 'button',
			layout : 'column',
			items : [{
				xtype : 'button',
				width : 70,
				text : baseinfo.goodsArea.i18n('foss.baseinfo.reset'), // 重置
				disabled : !baseinfo.goodsArea
				.isPermission('goodsArea/goodsAreaQueryButton'),
				hidden : !baseinfo.goodsArea
						.isPermission('goodsArea/goodsAreaQueryButton'),
				columnWidth : .08,
				handler : function() {
					me.getForm().reset();
					me.getForm().findField('organizationCode').organizationCode = null;
				}
			}, {
				xtype : 'container',
				html : '&nbsp;',
				columnWidth : .84
			}, {
				xtype : 'button',
				width : 70,
				cls : 'yellow_button',
				text : baseinfo.goodsArea.i18n('foss.baseinfo.query'), // 查询
				disabled : !baseinfo.goodsArea
				.isPermission('goodsArea/goodsAreaQueryButton'),
				hidden : !baseinfo.goodsArea
						.isPermission('goodsArea/goodsAreaQueryButton'),
				columnWidth : .08,
				handler : function() {
					if (me.getForm().isValid()) {
						if (Ext.isEmpty(me.getForm()
								.findField('organizationCode').rawValue)) {
							me.getForm().findField('organizationCode').organizationCode = null;
							me.up().getGoodsAreaGrid().getPagingToolbar()
									.moveFirst();
						} else {
							me.up().getGoodsAreaGrid().getPagingToolbar()
									.moveFirst();

						}
					}
				}
			}]
		}], me.callParent([cfg]);
	}
});
/**
 * 库区列表
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.goodsArea.i18n('foss.baseinfo.reservoirAreaInformation'), // 库区信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
	// 库区新增WINDOW
	goodsAreaAddWindo : null,
	getGoodsAreaAddWindow : function() {
		if (this.goodsAreaAddWindo == null) {
			this.goodsAreaAddWindo = Ext
					.create('Foss.baseinfo.goodsArea.GoodsAreaAddWindow');
			this.goodsAreaAddWindo.parent = this; // 父元素
		}
		return this.goodsAreaAddWindo;
	},
	// 修改库区WINDOW
	goodsAreaUpdateWindow : null,
	getGoodsAreaUpdateWindow : function() {
		if (this.goodsAreaUpdateWindow == null) {
			this.goodsAreaUpdateWindow = Ext
					.create('Foss.baseinfo.goodsArea.GoodsAreaUpdateWindow');
			this.goodsAreaUpdateWindow.parent = this; // 父元素
		}
		return this.goodsAreaUpdateWindow;
	},
	// 查看库区WINDOW
	goodsAreaShowWindow : null,
	getGoodsAreaShowWindow : function() {
		if (this.goodsAreaShowWindow == null) {
			this.goodsAreaShowWindow = Ext
					.create('Foss.baseinfo.goodsArea.GoodsAreaShowWindow');
			this.goodsAreaShowWindow.parent = this; // 父元素
		}
		this.goodsAreaShowWindow.getGoodsAreaForm().getForm().getFields().each(
				function(item) {
					item.setReadOnly(true);
				});
		return this.goodsAreaShowWindow;
	},
	// 作废库区
	toVoidGoodsArea : function(btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length < 1) { // 判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！'); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.goodsArea
						.i18n('foss.baseinfo.wantSetAsideTheseReservoirArea'),
				function(e) { // 是否要作废这些库区？
					if (e == 'yes') { // 询问是否删除，是则发送请求
						var goodsAreaVirtualCodes = new Array; // 库区ID数组
						for (var i = 0; i < selections.length; i++) {
							goodsAreaVirtualCodes.push(selections[i]
									.get('virtualCode'));
						}
						var params = {
							'goodsAreaVo' : {
								'goodsAreaVirtualCodes' : goodsAreaVirtualCodes
							}
						};
						var successFun = function(json) {
							baseinfo.showInfoMes(json.message);
							me.getPagingToolbar().moveFirst();
						};
						var failureFun = function(json) {
							if (Ext.isEmpty(json)) {
								baseinfo.showErrorMes(baseinfo.goodsArea
										.i18n('foss.baseinfo.requestTimeout')); // 请求超时
							} else {
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo.realPath('deleteGoodsArea.action');
						baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);
					}
				})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					xtype : 'rownumberer',
					width : 40,
					text : '序号' // 序号
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.operate'), // 操作
					// dataIndex : 'id',
					xtype : 'actioncolumn',
					align : 'center',
					width : 80,
					items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.goodsArea
								.i18n('foss.baseinfo.update'), // 修改
						disabled : !baseinfo.goodsArea
								.isPermission('goodsArea/goodsAreaUpdateButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var virtualCode = record.get('virtualCode'); // 库区虚拟编码
							var params = {
								'goodsAreaVo' : {
									'goodsAreaEntity' : {
										'virtualCode' : virtualCode
									}
								}
							};
							var successFun = function(json) {
								var updateWindow = me
										.getGoodsAreaUpdateWindow(); // 获得修改窗口
								updateWindow.goodsAreaEntity = json.goodsAreaVo.goodsAreaEntity; // 库区
								updateWindow.show(); // 显示修改窗口
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									baseinfo
											.showErrorMes(baseinfo.goodsArea
													.i18n('foss.baseinfo.requestTimeout')); // 请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo
									.realPath('queryGoodsAreaByVirtualCode.action');
							baseinfo.requestJsonAjax(url, params, successFun,
									failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.goodsArea.i18n('foss.baseinfo.void'), // 作废
						disabled : !baseinfo.goodsArea
								.isPermission('goodsArea/goodsAreaVoidButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo
									.showQuestionMes(
											baseinfo.goodsArea
													.i18n('foss.baseinfo.wantVoidReservoirArea'),
											function(e) { // 是否要作废这个库区？
												if (e == 'yes') { // 询问是否删除，是则发送请求
													var goodsAreaVirtualCodes = new Array; // 库区ID数组
													goodsAreaVirtualCodes
															.push(record
																	.get('virtualCode'));
													var params = {
														'goodsAreaVo' : {
															'goodsAreaVirtualCodes' : goodsAreaVirtualCodes
														}
													};
													var successFun = function(
															json) {
														baseinfo
																.showInfoMes(json.message);
														me.getPagingToolbar()
																.moveFirst();
													};
													var failureFun = function(
															json) {
														if (Ext.isEmpty(json)) {
															baseinfo
																	.showErrorMes(baseinfo.goodsArea
																			.i18n('foss.baseinfo.requestTimeout')); // 请求超时
														} else {
															baseinfo
																	.showErrorMes(json.message);
														}
													};
													var url = baseinfo
															.realPath('deleteGoodsArea.action');
													baseinfo.requestJsonAjax(
															url, params,
															successFun,
															failureFun);
												}
											})
						}
					}, {
						iconCls : 'deppon_icons_showdetail',
						tooltip : baseinfo.goodsArea
								.i18n('foss.baseinfo.details'), // 查看详情
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var virtualCode = record.get('virtualCode'); // 库区虚拟编码
							var params = {
								'goodsAreaVo' : {
									'goodsAreaEntity' : {
										'virtualCode' : virtualCode
									}
								}
							};
							var successFun = function(json) {
								var showWindow = me.getGoodsAreaShowWindow(); // 获得修改窗口
								showWindow.goodsAreaEntity = json.goodsAreaVo.goodsAreaEntity; // 库区
								showWindow.show(); // 显示修改窗口
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									baseinfo
											.showErrorMes(baseinfo.goodsArea
													.i18n('foss.baseinfo.requestTimeout')); // 请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo
									.realPath('queryGoodsAreaByVirtualCode.action');
							baseinfo.requestJsonAjax(url, params, successFun,
									failureFun);
						}
					}]
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.fieldID'), // 外场编号
					dataIndex : 'transferCode'
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.fieldName'), // 外场名称
					dataIndex : 'organizationName'
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.reservoirAreaNumber'), // 库区编号
					dataIndex : 'goodsAreaCode'
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.reservoirAreaName'), // 库区名称
					dataIndex : 'goodsAreaName'
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.reservoirAreaType'), // 库区类型
					dataIndex : 'goodsAreaType',
					renderer : function(value) {
						return baseinfo
								.changeCodeToNameStore(
										FossDataDictionary
												.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaType),
										value);
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.cargoType'), // 库区类型
					dataIndex : 'goodsType',
					renderer : function(value) {
						return baseinfo
								.changeCodeToNameStore(
										FossDataDictionary
												.getDataDictionaryStore(baseinfo.goodsArea.goodsType),
										value);
					}
				}, {
					text : '目的站', // 目的站
					dataIndex : 'arriveRegionName'
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.reservoirAreaCategory'), // 库区类别
					dataIndex : 'goodsAreaUsage',
					renderer : function(value) {
						return baseinfo
								.changeCodeToNameStore(
										FossDataDictionary
												.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaUsage),
										value);
					}
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.countingMode'), // 目的站
					dataIndex : 'countingMode',
					renderer : function(value) {
						if (value == 'KPAB') { // 'Y'表示生效
							return baseinfo.goodsArea
									.i18n('foss.baseinfo.countingModeKpAb'); //
						} else if (value == 'KP') { // 'N'表示失效
							return baseinfo.goodsArea
									.i18n('foss.baseinfo.countingModeKp'); //
						} else if (value == 'AB') {
							return baseinfo.goodsArea
									.i18n('foss.baseinfo.countingModeAB');
						} else {
							return null;
						}
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.asterisk'), // 星标
					dataIndex : 'asteriskCode',
					renderer : function(value) {
						if (value == 'N') {
							return baseinfo.goodsArea
									.i18n('foss.baseinfo.null'); // 无
						}
						return baseinfo
								.changeCodeToNameStore(
										FossDataDictionary
												.getDataDictionaryStore(baseinfo.goodsArea.asteriskType),
										value);
					}
				}, {
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.airagencycompany.remark'), // 备注
					flex : 2,
					dataIndex : 'notes'
				}, {
				 text : "库区面积", // 库区面积
					width : 80,
					dataIndex : 'area'
				}
//					{
//					text : "库区宽度", // 库区宽度
//					width : 80,
//					dataIndex : 'goodsAreaWidth'
//				}, 
//					{
//					text : "库区长度", // 库区长度
//					width : 80,
//					dataIndex : 'goodsAreaLength'
//				}
				, {
					text : "横坐标", // 横坐标
					width : 80,
					dataIndex : 'abscissa'
				}, {
					text : "纵坐标", // 纵坐标
					width : 80,
					dataIndex : 'ordinate'
				}, {
					text : "高度", // 库区高度
					width : 80,
					dataIndex : 'goodsAreaHeight'
				}];
		me.store = Ext.create('Foss.baseinfo.goodsArea.GoodsAreaStore', {
			autoLoad : false, // 不自动加载
			pageSize : 20,
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = me.up().getQueryGoodsAreaForm();
					if (queryForm != null) {
						Ext.apply(operation, {
							params : { // 查询站点组大查询，查询条件组织
								'goodsAreaVo.goodsAreaEntity.goodsAreaUsage' : queryForm
										.getForm().findField('goodsAreaUsage')
										.getValue(), // 库区类别
								'goodsAreaVo.goodsAreaEntity.goodsType' : queryForm
										.getForm().findField('goodsType')
										.getValue(), // 货物类型
								'goodsAreaVo.goodsAreaEntity.goodsAreaType' : queryForm
										.getForm().findField('goodsAreaType')
										.getValue(), // 库区类型
								'goodsAreaVo.goodsAreaEntity.arriveRegionCode' : queryForm
										.getForm()
										.findField('arriveRegionCode')
										.getValue(), // 目的站
								'goodsAreaVo.goodsAreaEntity.organizationCode' : queryForm
										.getForm()
										.findField('organizationCode').organizationCode, // 外场
								'goodsAreaVo.goodsAreaEntity.goodsAreaName' : queryForm
										.getForm().findField('goodsAreaName')
										.getValue(), // 库区名称
								'goodsAreaVo.goodsAreaEntity.countingModeKp' : queryForm
										.getForm().findField('countingModeKp')
										.getValue(), // 计票方式
								'goodsAreaVo.goodsAreaEntity.countingModeAb' : queryForm
										.getForm().findField('countingModeAb')
										.getValue()
								 // 计票方式
							}
						});
					}
				}
			}
		});
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
			xtype : 'button',
			text : baseinfo.goodsArea.i18n('foss.baseinfo.add'), // 新增
			disabled : !baseinfo.goodsArea
			.isPermission('goodsArea/goodsAreaAddButton'),
			hidden : !baseinfo.goodsArea
					.isPermission('goodsArea/goodsAreaAddButton'),
			handler : function() {
				me.getGoodsAreaAddWindow().show();
			}
		},
				/*
				 * '-',{ text : baseinfo.goodsArea.i18n('foss.baseinfo.void'),
				 * //作废
				 * hidden:!baseinfo.goodsArea.isPermission('goodsArea/goodsAreaVoidButton'),
				 * handler : function () { me.toVoidGoodsArea(); } }
				 */
				{
					xtype : 'button',
					text : baseinfo.goodsArea.i18n('foss.baseinfo.export'), // 导出
					// hidden:
					// !baseinfo.goodsArea.isPermission('goodsArea/goodsAreaAddButton'),
					hidden : false,
					plugins : {
						ptype : 'buttondisabledplugin',
						seconds : 5
					},
					handler : me.exportToExcel,
					scope : me
				}, '-', {
					xtype : 'button',
					text : baseinfo.goodsArea.i18n('foss.baseinfo.import'), // 导入
					// hidden:
					// !baseinfo.goodsArea.isPermission('goodsArea/goodsAreaAddButton'),
					hidden : false,
					handler : function() {
						me.getImportWin().show();
					},
					scope : me
				}, '-', {
					xtype : 'button',
					text : baseinfo.goodsArea
							.i18n('foss.baseinfo.import.template.download'), // 导入模板下载
					// hidden:
					// !baseinfo.goodsArea.isPermission('goodsArea/goodsAreaAddButton'),
					hidden : false,
					plugins : {
						ptype : 'buttondisabledplugin',
						seconds : 5
					},
					handler : me.downloadTemplate,
					scope : me
				}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	},
	// 导入月台
	importWin : null,
	getImportWin : function() {
		if (!this.importWin) {
			this.importWin = Ext.create('Foss.baseinfo.goodsArea.ImportWin');
			this.importWin.parent = this;// 父元素
		}
		return this.importWin;
	},
	exportToExcel : function() {
		var me = this, queryForm = me.up().getQueryGoodsAreaForm().getForm();
		var orgCode = queryForm.findField('organizationCode').getValue();
		if (Ext.isEmpty(orgCode)) {
			Ext.MessageBox.alert(baseinfo.goodsArea
							.i18n('foss.baseinfo.tipInfo'), baseinfo.goodsArea
							.i18n('foss.baseinfo.import.tip'));
			return;
		}
		Ext.MessageBox.buttonText.yes = baseinfo.goodsArea
				.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no = baseinfo.goodsArea
				.i18n('foss.baseinfo.cancel');
		if (!Ext.fly('downloadGoodsAreaForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadGoodsAreaForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		Ext.Msg.confirm(baseinfo.goodsArea.i18n('foss.baseinfo.tipInfo'),
				baseinfo.goodsArea.i18n('foss.baseinfo.exportMsg'), function(
						btn, text) {
					if (btn == 'yes') {
						var params = {
							'goodsAreaVo.goodsAreaEntity.goodsAreaUsage' : queryForm
									.findField('goodsAreaUsage').getValue(), // 库区类别
							'goodsAreaVo.goodsAreaEntity.goodsType' : queryForm
									.findField('goodsType').getValue(), // 货物类型
							'goodsAreaVo.goodsAreaEntity.goodsAreaType' : queryForm
									.findField('goodsAreaType').getValue(), // 库区类型
							'goodsAreaVo.goodsAreaEntity.arriveRegionCode' : queryForm
									.findField('arriveRegionCode').getValue(), // 目的站
							'goodsAreaVo.goodsAreaEntity.organizationCode' : queryForm
									.findField('organizationCode').organizationCode, // 外场
							'goodsAreaVo.goodsAreaEntity.goodsAreaName' : queryForm
									.findField('goodsAreaName').getValue(), // 库区名称
							'goodsAreaVo.goodsAreaEntity.countingModeKp' : queryForm
									.findField('countingModeKp').getValue(), // 计票方式
							'goodsAreaVo.goodsAreaEntity.countingModeAb' : queryForm
									.findField('countingModeAb').getValue()
							// 计票方式
						};
						Ext.Ajax.request({
							url : baseinfo.realPath('exportGoodsArea.action'),
							form : Ext.fly('downloadGoodsAreaForm'),
							params : params,
							method : 'post',
							isUpload : true,
							failure : function(response) {
								Ext.MessageBox
										.alert(
												baseinfo.goodsArea
														.i18n('foss.baseinfo.tipInfo'),
												baseinfo.goodsArea
														.i18n('foss.baseinfo.exportFailed'));
							}
						});
					}
				});
	},
	downloadTemplate : function() {
		if (!Ext.fly('downloadGoodsAreaForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadGoodsAreaForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		Ext.Ajax.request({
			url : baseinfo.realPath('downloadTemplate.action'),
			form : Ext.fly('downloadGoodsAreaForm'),
			method : 'POST',
			isUpload : true,
			exception : function(response, opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(baseinfo.goodsArea
								.i18n('foss.baseinfo.tipInfo'), result.message);
			}
		});
	}
});
// 上传附件弹出框
Ext.define('Foss.baseinfo.goodsArea.ImportWin', {
	extend : 'Ext.window.Window',
	title : baseinfo.goodsArea.i18n('foss.baseinfo.import'),
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 400,
	height : 150,
	closeAction : 'hide',
	listeners : {
		beforehide : function(me) {
			var form = me.down('form');
			form.getForm().findField('uploadFile').reset();
		}
	},
	parent : null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'form',
			flex : 1,
			layout : {
				type : 'hbox'
			},
			defaults : {
				margins : '0 5 0 0'
			},
			items : [{
				xtype : 'filefield',
				name : 'uploadFile',
				fieldLabel : baseinfo.goodsArea
						.i18n('foss.baseinfo.pleaseSelectAttachments'),
				labelWidth : 100,
				buttonText : baseinfo.goodsArea.i18n('foss.baseinfo.browse'),
				flex : 3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar : function() {
		var me = this;
		return [{
					text : baseinfo.goodsArea.i18n('foss.baseinfo.import'),
					xtype : 'button',
					scope : me,
					handler : me.uploadFile
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.cancel'),
					xtype : 'button',
					handler : function() {
						me.close();
					}
				}];
	},
	// 文件上传
	uploadFile : function() {
		var me = this;
		var successFn = function(json) {
			if (Ext.isEmpty(json.goodsAreaVo.numList)) {
				baseinfo.showInfoMes(baseinfo.goodsArea
						.i18n('foss.baseinfo.allDataImportSuccess'));// 全部数据导入成功！
				me.close();
				Ext.getCmp('T_baseinfo-goodsArea_content').getGoodsAreaGrid()
						.getPagingToolbar().moveFirst();
			} else {
				var message = baseinfo.goodsArea.i18n('foss.baseinfo.di');// 第
				for (var i = 0; i < json.goodsAreaVo.numList; i++) {
					message = message + json.goodsAreaVo.numList[i] + ','
				}
				message = message
						+ baseinfo.goodsArea.i18n('foss.baseinfo.lineNoImport');
				baseinfo.showWoringMessage(message);
			}
		};
		var failureFn = function(json) {
			if (Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.goodsArea
						.i18n('foss.baseinfo.postAttachmentsIsNull'));// baseinfo.goodsArea.i18n('foss.baseinfo.requestOvertime')
			} else {
				baseinfo.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = baseinfo.realPath('importGoodsArea.action');
		form.submit({
					url : url,
					waitMsg : baseinfo.goodsArea
							.i18n('foss.baseinfo.uploadYourAttachments'),
					success : function(form, action) {
						var result = action.result;
						if (result.success) {
							successFn(result);
						} else {
							failureFn(result);
						}
					},
					failure : function(form, action) {
						var result = action.result;
						failureFn(result);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						failureFn(result);
					}
				});
	}
});
/**
 * @description 站点组主页
 */
Ext.onReady(function() {
			Ext.QuickTips.init();
			if (Ext.getCmp('T_baseinfo-goodsArea_content')) {
				return;
			};
			var queryGoodsAreaForm = Ext
					.create('Foss.baseinfo.goodsArea.QueryGoodsAreaForm'); // 查询FORM
			var goodsAreaGrid = Ext
					.create('Foss.baseinfo.goodsArea.GoodsAreaGrid'); // 查询结果GRID
			Ext.getCmp('T_baseinfo-goodsArea').add(Ext.create(
					'Ext.panel.Panel', {
						id : 'T_baseinfo-goodsArea_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						// 获得查询FORM
						getQueryGoodsAreaForm : function() {
							return queryGoodsAreaForm;
						},
						// 获得查询结果GRID
						getGoodsAreaGrid : function() {
							return goodsAreaGrid;
						},
						items : [queryGoodsAreaForm, goodsAreaGrid]
					}));
		});
// ----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增库区信息
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.goodsArea.i18n('foss.baseinfo.newReservoirArea'), // 新增库区
	closable : true,
	parent : null, // 父元素（弹出这个window的gird——Foss.baseinfo.goodsArea.GoodsAreaGrid）
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	modal : true,
	width : 820,
	height : 520,
	layout : 'fit',
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.resetData();
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
		}
	},
	// 新增库区FORM
	goodsAreaForm : null,
	getGoodsAreaForm : function() {
		if (Ext.isEmpty(this.goodsAreaForm)) {
			this.goodsAreaForm = Ext.create(
					'Foss.baseinfo.goodsArea.GoodsAreaForm', {
						'isUpdate' : false
						// 新增
				});
		}
		return this.goodsAreaForm;
	},
	// 提交库区数据
	commitGoodsArea : function(button) {
		var me = this;
		var form = me.getGoodsAreaForm();
		if (form.getForm().isValid()) { // 校验form是否通过校验
			var goodsAreaModel = new Foss.baseinfo.goodsArea.GoodsAreaEntity();
			var storageList = new Array(); // 到各个库区所拥有的月台
			form.getForm().updateRecord(goodsAreaModel); // 将FORM中数据设置到MODEL里面
			if (!Ext.isEmpty(form.oldItems)) {
				for (var i = 0; i < form.oldItems.length; i++) {
					if (form.oldItems[i].getValue()) { // 选中了
						storageList.push({
							'virtualCode' : form.oldItems[i].storageEntity.virtualCode
						});
					}
				}
			}
			goodsAreaModel.set('storageList', storageList);
			goodsAreaModel.set('distanceList', null);
			var params = {
				'goodsAreaVo' : {
					'goodsAreaEntity' : goodsAreaModel.data
				}
			}; // 组织新增数据
			var goodsAreaType = form.getForm().findField('goodsAreaType');
			var notes = form.getForm().findField('notes');
			if (goodsAreaType.getValue() === 'BSE_GOODSAREA_TYPE_STATION') {
				if (notes.getValue().length > 1500) {
					baseinfo.showErrorMes('不能超过1500字符');
					return;
				}
			} else if (notes.getValue().length > 200) {
				baseinfo.showErrorMes('不能超过200字符');
				return;
			}
			var successFun = function(json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message); // 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.goodsArea
							.i18n('foss.baseinfo.requestTimeout')); // 请求超时
				} else {
					baseinfo.showErrorMes(json.message); // 提示失败原因
				}
			};
			var url = baseinfo.realPath('addGoodsArea.action'); // 请求库区新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
		}
	},
	resetData : function() {
		var me = this;
		var form = me.getGoodsAreaForm();
		if (!Ext.isEmpty(form.oldItems)) { // 将多余的元素清掉
			var oldItems = form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				var oldItem = oldItems[i];
				if (oldItem.isVisible()) {
					form.remove(oldItem);
				}
			}
		}
		form.getForm().reset(); // 表格重置
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : baseinfo.goodsArea.i18n('foss.baseinfo.cancel'), // 取消
					handler : function() {
						me.close();
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.reset'), // 重置
					handler : function() {
						me.resetData();
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.save'), // 保存
					cls : 'yellow_button',
					margin : '0 0 0 325',
					handler : function() {
						me.commitGoodsArea(this);
					}
				}];
		me.items = [me.getGoodsAreaForm()];
		me.callParent([cfg]);
	}
});
/**
 * 查看库区
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaShowWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.goodsArea.i18n('foss.baseinfo.viewReservoirArea'), // 查看库区
	closable : true,
	resizable : false,
	goodsAreaEntity : null, // 查看库区数据
	parent : null, // 父元素（弹出这个window的gird——Foss.baseinfo.goodsArea.GoodsAreaGrid）
	closeAction : 'hide',
	modal : true,
	width : 820,
	height : 680,
	layout : 'fit',
	listeners : {
		beforehide : function(me) {
			me.resetData(); // 清除掉这次的数据
		},
		beforeshow : function(me) {
			me.getGoodsAreaForm().getForm().findField('organizationName')
					.setCombValue(me.goodsAreaEntity.organizationName,
							me.goodsAreaEntity.transferCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getGoodsAreaForm().getForm().findField('organizationCode')
					.setValue(me.goodsAreaEntity.organizationCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getGoodsAreaForm().getForm().findField('arriveRegionCode')
					.setCombValue(me.goodsAreaEntity.arriveRegionName,
							me.goodsAreaEntity.arriveRegionCode); // 目的站
			me.getGoodsAreaForm().getForm().findField('transferCode')
					.setValue(me.goodsAreaEntity.transferCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			if (me.goodsAreaEntity.countingMode == 'KP') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : true,
									countingModeAb : false
								}); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.goodsAreaEntity.countingMode == 'AB') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : false,
									countingModeAb : true
								}); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.goodsAreaEntity.countingMode == 'KPAB') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : true,
									countingModeAb : true
								});
			}
			me.loadValue();
		}
	},
	// 库区FORM
	goodsAreaForm : null,
	getGoodsAreaForm : function() {
		if (Ext.isEmpty(this.goodsAreaForm)) {
			this.goodsAreaForm = Ext.create(
					'Foss.baseinfo.goodsArea.GoodsAreaForm', {
						'isUpdate' : true, // 证明是修改
						'isShow' : true
					});
			this.goodsAreaForm.getForm().findField('organizationName')
					.setReadOnly(true);
		}
		return this.goodsAreaForm;
	},
	// 清楚数据
	resetData : function() {
		var me = this;
		var form = me.getGoodsAreaForm();
		if (!Ext.isEmpty(form.oldItems)) { // 将多余的元素清掉
			var oldItems = form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				var oldItem = oldItems[i];
				if (oldItem.isVisible()) {
					form.remove(oldItem);
				}
			}
		}
		form.getForm().reset(); // 表格重置
	},
	// 加载原有数据
	loadValue : function() { // 外场名称和外场code不进行处理
		var me = this;
		var goodsAreaModel = new Foss.baseinfo.goodsArea.GoodsAreaEntity(me.goodsAreaEntity);
		me.getGoodsAreaForm().getForm().findField('notes')
				.setValue(goodsAreaModel.get('notes'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaCode')
				.setValue(goodsAreaModel.get('goodsAreaCode'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaName')
				.setValue(goodsAreaModel.get('goodsAreaName'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaType')
				.setValue(goodsAreaModel.get('goodsAreaType'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaUsage')
				.setValue(goodsAreaModel.get('goodsAreaUsage'));
		me.getGoodsAreaForm().getForm().findField('goodsType')
				.setValue(goodsAreaModel.get('goodsType'));
		me.getGoodsAreaForm().getForm().findField('arriveRegionCode')
				.setValue(goodsAreaModel.get('arriveRegionCode'));
		if (goodsAreaModel.get('asteriskCode') == 'N') {
			me.getGoodsAreaForm().getForm().findField('asteriskCode')
					.setValue(baseinfo.goodsArea.i18n('foss.baseinfo.null')); // 无
		} else {
			me.getGoodsAreaForm().getForm().findField('asteriskCode')
					.setValue(goodsAreaModel.get('asteriskCode'));
		}
//		me.getGoodsAreaForm().getForm().findField('goodsAreaWidth')
//				.setValue(goodsAreaModel.get('goodsAreaWidth'));
//		me.getGoodsAreaForm().getForm().findField('goodsAreaLength')
//				.setValue(goodsAreaModel.get('goodsAreaLength'));
		me.getGoodsAreaForm().getForm().findField('area')
				.setValue(goodsAreaModel.get('area'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaHeight')
				.setValue(goodsAreaModel.get('goodsAreaHeight'));
		me.getGoodsAreaForm().getForm().findField('abscissa')
				.setValue(goodsAreaModel.get('abscissa'));
		me.getGoodsAreaForm().getForm().findField('ordinate')
				.setValue(goodsAreaModel.get('ordinate'));
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : baseinfo.goodsArea.i18n('foss.baseinfo.cancel'), // 取消
					handler : function() {
						me.close();
					}
				}];
		me.items = [me.getGoodsAreaForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改库区
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.goodsArea.i18n('foss.baseinfo.modifyReservoirArea'), // 修改库区
	closable : true,
	resizable : false,
	goodsAreaEntity : null, // 修改库区数据
	parent : null, // 父元素（弹出这个window的gird——Foss.baseinfo.goodsArea.GoodsAreaGrid）
	closeAction : 'hide',
	modal : true,
	width : 820,
	height : 680,
	layout : 'fit',
	listeners : {
		beforehide : function(me) {
			me.resetData(); // 清除掉这次的数据
		},
		beforeshow : function(me) {
			me.getGoodsAreaForm().getForm().findField('organizationName')
					.setCombValue(me.goodsAreaEntity.organizationName,
							me.goodsAreaEntity.transferCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getGoodsAreaForm().getForm().findField('organizationCode')
					.setValue(me.goodsAreaEntity.organizationCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getGoodsAreaForm().getForm().findField('arriveRegionCode')
					.setCombValue(me.goodsAreaEntity.arriveRegionName,
							me.goodsAreaEntity.arriveRegionCode); // 目的站
			me.getGoodsAreaForm().getForm().findField('transferCode')
					.setValue(me.goodsAreaEntity.transferCode); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			if (me.goodsAreaEntity.countingMode == 'KP') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : true,
									countingModeAb : false
								}); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.goodsAreaEntity.countingMode == 'AB') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : false,
									countingModeAb : true
								}); // 只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.goodsAreaEntity.countingMode == 'KPAB') {
				me.getGoodsAreaForm().getForm().findField('countingMode')
						.setValue({
									countingModeKp : true,
									countingModeAb : true
								});
			}
			me.loadValue();
		}
	},
	// 库区FORM
	goodsAreaForm : null,
	getGoodsAreaForm : function() {
		if (Ext.isEmpty(this.goodsAreaForm)) {
			this.goodsAreaForm = Ext.create(
					'Foss.baseinfo.goodsArea.GoodsAreaForm', {
						'isUpdate' : true
						// 证明是修改
				});
			this.goodsAreaForm.getForm().findField('organizationName')
					.setReadOnly(true);
			if (baseinfo.getFrontLineRoles() == true) {// 判断登陆人为一线角色（外场经理），则按需求设置只读属性
				this.goodsAreaForm.getForm().findField('goodsAreaCode')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('goodsAreaName')
						.setReadOnly(true)
				this.goodsAreaForm.getForm().findField('goodsAreaType')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('goodsAreaUsage')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('goodsType')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('arriveRegionCode')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('asteriskCode')
						.setReadOnly(true);
				this.goodsAreaForm.getForm().findField('countingMode')
						.setReadOnly(true);
			}
		}
		return this.goodsAreaForm;
	},
	// 修改库区
	commitGoodsArea : function(button) {
		var me = this;
		var form = me.getGoodsAreaForm();
		if (form.getForm().isValid()) { // 校验form是否通过校验
			var goodsAreaModel = new Foss.baseinfo.goodsArea.GoodsAreaEntity(me.goodsAreaEntity);
			var storageList = new Array(); // 到各个库区所拥有的月台
			form.getForm().updateRecord(goodsAreaModel); // 将FORM中数据设置到MODEL里面
			if (!Ext.isEmpty(form.oldItems)) {
				for (var i = 0; i < form.oldItems.length; i++) {
					if (form.oldItems[i].getValue()) { // 选中了
						storageList.push({
							'virtualCode' : form.oldItems[i].storageEntity.virtualCode
						});
					}
				}
			}
			goodsAreaModel.set('storageList', storageList);
			// Start..
			var distanceList = [], platformItems = form.oldPlatformItems; // 到各个月台的距离列表
			if (!Ext.isEmpty(platformItems)) {
				for (var i = 0; i < platformItems.length; i++) {
					if (!Ext.isEmpty(platformItems[i].getValue())) {
						var distance = {
							'platformVirtualCode' : platformItems[i].platformEntity.virtualCode,
							'distance' : platformItems[i].getValue()
						};
						distanceList.push(distance);
					}
				}
			}
			goodsAreaModel.set('distanceList', distanceList);
			// End...
			var params = {
				'goodsAreaVo' : {
					'goodsAreaEntity' : goodsAreaModel.data
				}
			}; // 组织新增数据
			var goodsAreaType = form.getForm().findField('goodsAreaType');
			var notes = form.getForm().findField('notes');
			if (goodsAreaType.getValue() === 'BSE_GOODSAREA_TYPE_STATION') {
				if (notes.getValue().length > 1500) {
					baseinfo.showErrorMes('不能超过1500字符');
					return;
				}
			} else if (notes.getValue().length > 200) {
				baseinfo.showErrorMes('不能超过200字符');
				return;
			}
			var successFun = function(json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message); // 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.goodsArea
							.i18n('foss.baseinfo.requestTimeout')); // 请求超时
				} else {
					baseinfo.showErrorMes(json.message); // 提示失败原因
				}
			};
			var url = baseinfo.realPath('updateGoodsArea.action'); // 请求库区新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
		}
	},
	// 清楚数据
	resetData : function() {
		var me = this;
		var form = me.getGoodsAreaForm();
		if (!Ext.isEmpty(form.oldItems)) { // 将多余的元素清掉
			var oldItems = form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				var oldItem = oldItems[i];
				if (oldItem.isVisible()) {
					form.remove(oldItem);
				}
			}
		}
		form.getForm().reset(); // 表格重置
	},
	// 加载原有数据
	loadValue : function() { // 外场名称和外场code不进行处理
		var me = this;
		var goodsAreaModel = new Foss.baseinfo.goodsArea.GoodsAreaEntity(me.goodsAreaEntity);
		me.getGoodsAreaForm().getForm().findField('notes')
				.setValue(goodsAreaModel.get('notes'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaCode')
				.setValue(goodsAreaModel.get('goodsAreaCode'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaName')
				.setValue(goodsAreaModel.get('goodsAreaName'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaType')
				.setValue(goodsAreaModel.get('goodsAreaType'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaUsage')
				.setValue(goodsAreaModel.get('goodsAreaUsage'));
		me.getGoodsAreaForm().getForm().findField('goodsType')
				.setValue(goodsAreaModel.get('goodsType'));
		me.getGoodsAreaForm().getForm().findField('arriveRegionCode')
				.setValue(goodsAreaModel.get('arriveRegionCode'));
		if (goodsAreaModel.get('asteriskCode') == 'N') {
			me.getGoodsAreaForm().getForm().findField('asteriskCode')
					.setValue(baseinfo.goodsArea.i18n('foss.baseinfo.null')); // 无
		} else {
			me.getGoodsAreaForm().getForm().findField('asteriskCode')
					.setValue(goodsAreaModel.get('asteriskCode'));
		}
//		me.getGoodsAreaForm().getForm().findField('goodsAreaWidth')
//				.setValue(goodsAreaModel.get('goodsAreaWidth'));
//		me.getGoodsAreaForm().getForm().findField('goodsAreaLength')
//				.setValue(goodsAreaModel.get('goodsAreaLength'));
		me.getGoodsAreaForm().getForm().findField('area')
				.setValue(goodsAreaModel.get('area'));
		me.getGoodsAreaForm().getForm().findField('goodsAreaHeight')
				.setValue(goodsAreaModel.get('goodsAreaHeight'));
		me.getGoodsAreaForm().getForm().findField('abscissa')
				.setValue(goodsAreaModel.get('abscissa'));
		me.getGoodsAreaForm().getForm().findField('ordinate')
				.setValue(goodsAreaModel.get('ordinate'));
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : baseinfo.goodsArea.i18n('foss.baseinfo.cancel'), // 取消
					handler : function() {
						me.close();
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.reset'), // 重置
					handler : function() {
						// me.getGoodsAreaForm().getForm().findField('organizationName').setCombValue(me.goodsAreaEntity.organizationName
						// ,me.goodsAreaEntity.organizationCode);
						// 在充重置时不进行处理
						me.getGoodsAreaForm().getForm()
								.findField('arriveRegionCode').setCombValue(
										me.goodsAreaEntity.arriveRegionName,
										me.goodsAreaEntity.arriveRegionCode); // 目的站
						me.loadValue();
						var form = me.getGoodsAreaForm();
						if (!Ext.isEmpty(form.oldItems)) { // 将多余的元素清掉
							for (var i = 0; i < form.oldItems.length; i++) {
								form.oldItems[i]
										.setValue(form.oldItems[i].isCheck);
							}
						}
					}
				}, {
					text : baseinfo.goodsArea.i18n('foss.baseinfo.save'), // 保存
					cls : 'yellow_button',
					margin : '0 0 0 325',
					handler : function() {
						me.commitGoodsArea(this);
					}
				}];
		me.items = [me.getGoodsAreaForm()];
		me.callParent([cfg]);
	}
});
/**
 * 库区组-FORM
 */
Ext.define('Foss.baseinfo.goodsArea.GoodsAreaForm', {
	extend : 'Ext.form.Panel',
	// title: baseinfo.goodsArea.i18n('foss.baseinfo.reservoirAreaInformation'),
	// //库区信息
	// frame: true,
	// autoScroll: true,
	// height: 330,
	oldItems : [], // 上一次的库位元素
	oldPlatformItems : [], // 上一次的库位元素
	isUpdate : false, // 是否为修改
	// padding: '0 10px 0 10px',
	// collapsible: true,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 60,
		columnWidth : 1 / 3
	},
	bodyStyle : 'overflow-y:auto;',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'container',
			defaults : {
				margin : '5 5 5 5',
				labelWidth : 60
			},
			columnWidth : 1,
			layout : 'column',
			items : [{
				name : 'organizationName', // 外场名称
				allowBlank : false,
				columnWidth : 1 / 3,
				xtype : 'commontransfercenterselector',
				userCode : FossUserContext.getCurrentUserEmp().empCode,
				currentOrgCode : FossUserContext.getCurrentDeptCode(),
				fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.fieldName'),
				listeners : {
					select : function(text, records, eops) {
						me.getForm().findField('organizationCode')
								.setValue(records[0].get('orgCode'));
						me.getForm().findField('transferCode')
								.setValue(records[0].get('code'));
					}
				}
			}, {
				name : 'transferCode', // 外场编号
				readOnly : true,
				columnWidth : 1 / 3,
				fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.fieldID'),
				xtype : 'textfield'
			}, {
				xtype : 'container',
				columnWidth : 1 / 3,
				height : 20
			}, {
				name : 'organizationCode', // 外场编号
				readOnly : true,
				hidden : true,
				xtype : 'textfield'
			}]
		}, {
			name : 'goodsAreaCode', // 库区编号
			allowBlank : false,
			maxLength : '20',
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaNumber'),
			xtype : 'textfield'
		}, {
			name : 'goodsAreaName', // 库区名称
			allowBlank : false,
			maxLength : '20',
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaName'),
			xtype : 'textfield'
		}, {
			//name : 'goodsAreaWidth', // 库区宽度
			name : 'area', // 库区面积
			allowBlank : baseinfo.getAsteriskMaintenanceRole() == true,
			fieldLabel : baseinfo.goodsArea
					//.i18n('foss.baseinfo.goodsArea.width'),
				.i18n('foss.baseinfo.goodsArea.area'),	
			xtype : 'numberfield'
		}, {
			name : 'goodsAreaType',
			queryMode : 'local',
			allowBlank : false,
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			store : FossDataDictionary
					.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaType),
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaType'), // 库区类型
			xtype : 'combo',
			listeners : {
				change : function(item, newValue) {
					if (newValue == baseinfo.goodsArea.goodsAreaTypeHZ
							|| newValue == baseinfo.goodsArea.goodsAreaTypeKH
							|| newValue == baseinfo.goodsArea.goodsAreaTypePH) {
						me.getForm().findField('arriveRegionCode').allowBlank = false;
					} else {
						me.getForm().findField('arriveRegionCode').allowBlank = true;
						// 若库区为偏线库区
						if (newValue == baseinfo.goodsArea.goodsAreaTypePX) {
							me.getForm().findField('arriveRegionCode')
									.setValue('');
							me.getForm().findField('arriveRegionCode')
									.setDisabled(true);
						} else {
							me.getForm().findField('arriveRegionCode')
									.setDisabled(false);
						}
					}
				},
				select : function(combo, records, eOpt) {
					var notes = me.getForm().findField('notes');
					if (Ext.isEmpty(notes.getValue())
							|| Ext.isEmpty(combo.getValue())) {
						return;
					}
					if (combo.getValue() === 'BSE_GOODSAREA_TYPE_STATION') {
						if (notes.getValue().length > 1500) {
							baseinfo.showErrorMes('不能超过1500字符');
						}
					} else if (notes.getValue().length > 200) {
						baseinfo.showErrorMes('不能超过200字符');
					}
				}
			}
		}, {
			name : 'goodsAreaUsage',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			allowBlank : false,
			store : FossDataDictionary
					.getDataDictionaryStore(baseinfo.goodsArea.goodsAreaUsage),
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.reservoirAreaCategory'), // 库区类别
			xtype : 'combo'
		},
//		{
//			name : 'goodsAreaLength', // 库区长度
//			allowBlank : baseinfo.getAsteriskMaintenanceRole() == true,
//			fieldLabel : baseinfo.goodsArea
//					.i18n('foss.baseinfo.goodsArea.length'),
//			xtype : 'numberfield'
//		}, 
			{
			name : 'goodsAreaHeight', // 高度
			allowBlank : baseinfo.getAsteriskMaintenanceRole() == true,
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.goodsArea.height'),
			xtype : 'numberfield'
		},{
			name : 'goodsType',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			store : FossDataDictionary
					.getDataDictionaryStore(baseinfo.goodsArea.goodsType),
			fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.cargoType'), // 货物类型
			xtype : 'combo'
		}, {
			name : 'arriveRegionCode', // 目的站
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.flightsDestinationStation'),
			xtype : 'dynamicorgcombselector',
			// forceSelection : true,
			type : 'ORG',
			salesDepartment : 'Y', // 营业部
			transferCenter : 'Y', // 外场
			airDispatch : 'Y' // 空运总调
		},  {
			colspan : 3,
			name : 'asteriskCode',
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			value : '',
			store : FossDataDictionary.getDataDictionaryStore(
					baseinfo.goodsArea.asteriskType, null, {
						'valueCode' : '',
						'valueName' : baseinfo.goodsArea
								.i18n('foss.baseinfo.null')
						 // 无
					}),
			fieldLabel : baseinfo.goodsArea.i18n('foss.baseinfo.asterisk'), // 星标
			xtype : 'combo'
		}, {
			xtype : 'container',
			layout : 'column',
			defaults : {
				labelWidth : 50
			},
			items : [{
				name : 'abscissa',
				columnWidth : 0.5,
				allowBlank : baseinfo.getAsteriskMaintenanceRole() == true,
				fieldLabel : baseinfo.goodsArea
						.i18n('foss.baseinfo.goodsArea.abscissa'),
				xtype : 'numberfield',
				listeners : {
					'blur' : {
						fn : me.onBlurPoint,
						scope : me
					}
				}
			}, {
				name : 'ordinate',
				columnWidth : 0.5,
				allowBlank : baseinfo.getAsteriskMaintenanceRole() == true,
				fieldLabel : baseinfo.goodsArea
						.i18n('foss.baseinfo.goodsArea.ordinate'),
				xtype : 'numberfield',
				listeners : {
					'blur' : {
						fn : me.onBlurPoint,
						scope : me
					}
				}
			}]
		}, {
			xtype : 'container',
			defaults : {
				labelWidth : 60
			},
			items : [{
				xtype : 'checkboxgroup',
				name : 'countingMode',
				fieldLabel : baseinfo.goodsArea
						.i18n('foss.baseinfo.countingMode'),
				columns : 2,
				items : [{
							boxLabel : '计票(卡普)',
							width : 100,
							name : 'countingModeKp'
						}, {
							boxLabel : '计票(AB)',
							width : 100,
							name : 'countingModeAb'
						}]
			}]
		}, {
			name : 'notes', // 备注
			columnWidth : 1,
			fieldLabel : baseinfo.goodsArea
					.i18n('foss.baseinfo.airagencycompany.remark'),
			// maxLength: 200,
			xtype : 'textareafield',
			listeners : {
				blur : function(notes, eOpts) {
					var goodsAreaType = me.getForm().findField('goodsAreaType');
					if (Ext.isEmpty(notes.getValue())
							|| Ext.isEmpty(goodsAreaType.getValue())) {
						return;
					}
					if (goodsAreaType.getValue() === 'BSE_GOODSAREA_TYPE_STATION') {
						if (notes.getValue().length > 1500) {
							baseinfo.showErrorMes('不能超过1500字符');
						}
					} else if (notes.getValue().length > 200) {
						baseinfo.showErrorMes('不能超过200字符');
					}
				}
			}
		}];
		me.platformLabel = {
			itemId : 'platformItem',
			columnWidth : 1,
			xtype : 'displayfield',
			value : baseinfo.goodsArea
					.i18n('foss.baseinfo.goodsArea.platform.distance')
		};
		//5.28去掉库位的版本
//		me.storageLabel = {
//			itemId : 'storageItem',
//			columnWidth : 1,
//			xtype : 'displayfield',
//			value : baseinfo.goodsArea
//					.i18n('foss.baseinfo.correspondingLocation')
//			// 对应库位
//		};
		me.callParent([cfg]);
		if (me.isUpdate) { // 修改
			me.getForm().findField('organizationCode').on('change',
					function(text, newValue, oldValue) {
						if (Ext.isEmpty(newValue)) {
							return;
						}
						// 优化性能 -> 暂停布局，防止多次回流和重绘
						Ext.suspendLayouts();
						me.checkPlatformItems(me, newValue);
						//5.28 去掉库位信息
						//me.checkStorageItems(me, newValue);
						// 优化性能 -> 恢复布局，多次布局操作，一次执行
						Ext.resumeLayouts(true);
					});
		} else {
			me.getForm().findField('organizationName').on('blur',
					function(text, obj) {
						// 优化性能 -> 暂停布局，防止多次回流和重绘
						Ext.suspendLayouts();
						// me.genPlatformItems(me);
						//5.28 去掉库位信息
						//me.genStorageItems(me);
						// 优化性能 -> 恢复布局，多次布局操作，一次执行
						Ext.resumeLayouts(true);
					});
		}
	},
	onBlurPoint : function(field, eObj) {
		var me = this, goodsAreaEntity = me.up('window').goodsAreaEntity, currentFiled = field
				.getName(), currentVal = field.getValue(), oldVal;
		if (currentFiled == "abscissa") {
			oldVal = goodsAreaEntity.abscissa;
		} else if (currentFiled == "ordinate") {
			oldVal = goodsAreaEntity.ordinate;
		} else {
			return;
		}
		if (currentVal == oldVal) {
			return;
		}
		var goodsAreaModel = new Foss.baseinfo.goodsArea.GoodsAreaEntity(goodsAreaEntity);
		me.getForm().updateRecord(goodsAreaModel);
		goodsAreaModel.set('storageList', null);
		goodsAreaModel.set('distanceList', null);
		var params = {
			'goodsAreaVo' : {
				'goodsAreaEntity' : goodsAreaModel.data
			}
		};
		var successFun = function(json) {
			var distanceList = json.goodsAreaVo.goodsAreaEntity.distanceList;
			if (!Ext.isEmpty(distanceList)) {
				for (var i = 0; i < distanceList.length; i++) {
					var platformVirtualCode = distanceList[i].platformVirtualCode;
					if (!Ext.isEmpty(platformVirtualCode)) {
						var item = me.child('#' + platformVirtualCode + '');
						if (item) {
							item.setValue(distanceList[i].distance)
						}
					}
				}
			}
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.storage
						.i18n('foss.baseinfo.requestTimeout'));// 请求超时
			} else {
				baseinfo.showErrorMes(json.message);// 提示失败原因
			}
		}
		var url = baseinfo.realPath('initPlatAreaDistance.action');// 查询该外场的月台
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
	},
	checkPlatformItems : function(scope, newValue) {
		var me = scope;
		if (me.platformLabelItem != undefined) {
			me.remove(me.platformLabelItem);
		}
		if (!Ext.isEmpty(me.oldPlatformItems)) {
			for (var i = 0; i < me.oldPlatformItems.length; i++) {
				me.remove(me.oldPlatformItems[i]);
			}
		}
		var organizationCode = newValue;
		var params = {
			'platformVo' : {
				'platformEntity' : {
					'organizationCode' : organizationCode
				}
			}
		};
		var successFun = function(json) {
			var platformEntityList = json.platformVo.platformEntityList;
			if (!Ext.isEmpty(platformEntityList)) {
				var items = [];
				for (var i = 0; i < platformEntityList.length; i++) {
					var distanceList = me.up('window').goodsAreaEntity.distanceList;
					var numberfieldValue = null;
					// distanceList不为空表示设置了部分距离月台的距离
					if (!Ext.isEmpty(distanceList)) {
						for (var j = 0; j < distanceList.length; j++) {
							// 如果相同则表示这个语态以前被赋值
							if (distanceList[j].platformVirtualCode == platformEntityList[i].virtualCode) {
								numberfieldValue = distanceList[j].distance;
							}
						}
					}
					var item = null;// 月台对应的元素
					if (me.isShow) {
						if (Ext.isEmpty(numberfieldValue)) {
							item = {
								itemId : platformEntityList[i].virtualCode,
								xtype : 'numberfield',
								fieldLabel : platformEntityList[i].platformCode,
								platformEntity : platformEntityList[i],// 月台相关数据
								maxValue : 999999.999,
								readOnly : true,
								minValue : 0,
								step : 0.001,
								decimalPrecision : 3
							};
						} else {
							item = {
								itemId : platformEntityList[i].virtualCode,
								xtype : 'numberfield',
								fieldLabel : platformEntityList[i].platformCode,
								platformEntity : platformEntityList[i],// 月台相关数据
								maxValue : 999999.999,
								minValue : 0,
								readOnly : true,
								value : numberfieldValue,// 这样设置值，点击reset的时候就不用再重新赋值了
								step : 0.001,
								decimalPrecision : 3
							};
						}
					} else {
						if (Ext.isEmpty(numberfieldValue)) {
							item = {
								itemId : platformEntityList[i].virtualCode,
								xtype : 'numberfield',
								fieldLabel : platformEntityList[i].platformCode,
								platformEntity : platformEntityList[i],// 月台相关数据
								maxValue : 999999.999,
								minValue : 0,
								step : 0.001,
								decimalPrecision : 3
							};
						} else {
							item = {
								itemId : platformEntityList[i].virtualCode,
								xtype : 'numberfield',
								fieldLabel : platformEntityList[i].platformCode,
								platformEntity : platformEntityList[i],// 月台相关数据
								maxValue : 999999.999,
								minValue : 0,
								value : numberfieldValue,// 这样设置值，点击reset的时候就不用再重新赋值了
								step : 0.001,
								decimalPrecision : 3
							};
						}
					}
					items.push(item);
				}
				me.platformLabelItem = me.add(me.platformLabel);
				me.oldPlatformItems = me.add(items);
			}
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.storage
						.i18n('foss.baseinfo.requestTimeout'));// 请求超时
			} else {
				baseinfo.showErrorMes(json.message);// 提示失败原因
			}
		}
		var url = baseinfo.realPath('searchPlatformByOrganizationCode.action');// 查询该外场的月台
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
	},
//	checkStorageItems : function(scope, newValue) {
//		var me = scope;
//		if (me.storageLabelItem != undefined) {
//			me.remove(me.storageLabelItem);
//		}
//		if (!Ext.isEmpty(me.oldItems)) {
//			for (var i = 0; i < me.oldItems.length; i++) {
//				me.remove(me.oldItems[i]);
//			}
//		}
//		if (!Ext.isEmpty(newValue)) { // 设置的值不为空时才执行操作
//			var organizationCode = newValue; // 查询该外场下的所有月台
//			var params = {
//				'storageVo' : {
//					'storageEntity' : {
//						'organizationCode' : organizationCode
//					}
//				}
//			};
//			var successFun = function(json) {
//				var storageEntityList = json.storageVo.storageEntityList;
//				if (!Ext.isEmpty(storageEntityList)) {
//					var items = new Array();
//					for (var i = 0; i < storageEntityList.length; i++) {
//						var isHidden = false; // 是否隐藏
//						var isChecked = false; // 是否选中
//						if (!Ext
//								.isEmpty(storageEntityList[i].goodsAreaVirtualCode)) {
//							if (storageEntityList[i].goodsAreaVirtualCode == me
//									.up('window').goodsAreaEntity.virtualCode) {
//								isHidden = false;
//								isChecked = true;
//							} else {
//								isHidden = true;
//								isChecked = false;
//							}
//						} else {
//							isChecked = false;
//							isHidden = false;
//						}
//						// 库位编码
//						var boxLabel = storageEntityList[i].storageCode;
//						var item = null;
//						if (me.isShow) { // 如果仅仅查看则设置为只读
//							item = { // 根据库位的checkbox
//								xtype : 'checkbox',
//								colspan : 1,
//								isHidden : isHidden, // 是否隐藏
//								isCheck : isChecked, // 是否选中
//								readOnly : true,
//								boxLabel : boxLabel, // 库位编码
//								storageEntity : storageEntityList[i], // 库位相关数据
//								hidden : isHidden,
//								checked : isChecked
//							};
//						} else {
//							item = { // 根据库位的checkbox
//								xtype : 'checkbox',
//								colspan : 1,
//								isHidden : isHidden, // 是否隐藏
//								isCheck : isChecked, // 是否选中
//								readOnly : isHidden,
//								boxLabel : boxLabel, // 库位编码
//								storageEntity : storageEntityList[i], // 库位相关数据
//								hidden : isHidden,
//								checked : isChecked
//							};
//						}
//						items.push(item);
//					}
//					me.storageLabelItem = me.add(me.storageLabel);
//					me.oldItems = me.add(items);
//				}
//			};
//			var failureFun = function(json) {
//				if (Ext.isEmpty(json)) {
//					baseinfo.showErrorMes(baseinfo.goodsArea
//							.i18n('foss.baseinfo.requestTimeout')); // 请求超时
//				} else {
//					baseinfo.showErrorMes(json.message); // 提示失败原因
//				}
//			}
//			var url = baseinfo
//					.realPath('queryStorageListByOrganizationCode.action'); // 查询该外场的月台
//			baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
//		}
//	},
	genPlatformItems : function(scope) {
		var me = scope;
		if (me.platformLabelItem != undefined) {
			me.remove(me.platformLabelItem);
		}
		if (!Ext.isEmpty(me.oldPlatformItems)) {
			for (var i = 0; i < me.oldPlatformItems.length; i++) {
				me.remove(me.oldPlatformItems[i]);
			}
		}
		var organizationCode = me.getForm().findField('organizationCode')
				.getValue();// 查询该外场下的所有月台
		var params = {
			'platformVo' : {
				'platformEntity' : {
					'organizationCode' : organizationCode
				}
			}
		};
		var successFun = function(json) {
			var platformEntityList = json.platformVo.platformEntityList;
			if (!Ext.isEmpty(platformEntityList)) {
				var items = [];
				for (var i = 0; i < platformEntityList.length; i++) {
					var item = {// 根据月台构建numberfiled
						itemId : platformEntityList[i].virtualCode,
						xtype : 'numberfield',
						fieldLabel : platformEntityList[i].platformCode,
						platformEntity : platformEntityList[i],// 月台相关数据
						maxValue : 999999.999,
						minValue : 0,
						step : 0.001,
						decimalPrecision : 3
					};
					items.push(item);
				}
				me.platformLabelItem = me.add(me.platformLabel);
				me.oldPlatformItems = me.add(items);
			}
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.storage
						.i18n('foss.baseinfo.requestTimeout'));// 请求超时
			} else {
				baseinfo.showErrorMes(json.message);// 提示失败原因
			}
		}
		var url = baseinfo.realPath('searchPlatformByOrganizationCode.action');// 查询该外场的月台
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
	}
	//5.28  去掉库位的版本
//	genStorageItems : function(scope) {
//		var me = scope;
//		if (me.storageLabelItem != undefined) {
//			me.remove(me.storageLabelItem);
//		}
//		if (!Ext.isEmpty(me.oldItems)) {
//			for (var i = 0; i < me.oldItems.length; i++) {
//				me.remove(me.oldItems[i]);
//			}
//		}
//		var organizationCode = me.getForm().findField('organizationCode')
//				.getValue(); // 查询该外场下的所有月台
//		var params = {
//			'storageVo' : {
//				'storageEntity' : {
//					'organizationCode' : organizationCode
//				}
//			}
//		};
//		var successFun = function(json) {
//			var storageEntityList = json.storageVo.storageEntityList;
//			if (!Ext.isEmpty(storageEntityList)) {
//				var items = [];
//				for (var i = 0; i < storageEntityList.length; i++) {
//					var isHidden = false; // 是否隐藏
//					if (!Ext.isEmpty(storageEntityList[i].goodsAreaVirtualCode)) { // 如果该库位属于其他库区，则不给显示，不可编辑，默认隐藏
//						isHidden = true;
//					} else {
//						isHidden = false;
//					}
//					var boxLabel = storageEntityList[i].storageCode;
//					var item = { // 根据库位的checkbox
//						xtype : 'checkbox',
//						colspan : 1,
//						isHidden : isHidden, // 标记其默认是否隐藏
//						readOnly : isHidden,
//						boxLabel : boxLabel, // 库位编码
//						storageEntity : storageEntityList[i], // 库位相关数据
//						hidden : isHidden
//					};
//					items.push(item);
//				}
//				me.storageLabelItem = me.add(me.storageLabel);
//				me.oldItems = me.add(items);
//			}
//		};
//		var failureFun = function(json) {
//			if (Ext.isEmpty(json)) {
//				baseinfo.showErrorMes(baseinfo.goodsArea
//						.i18n('foss.baseinfo.requestTimeout')); // 请求超时
//			} else {
//				baseinfo.showErrorMes(json.message); // 提示失败原因
//			}
//		};
//		var url = baseinfo
//				.realPath('queryStorageListByOrganizationCode.action'); // 查询该外场的库位
//		baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
//	}
});
