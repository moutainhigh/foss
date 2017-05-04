/**
 * 查询方式类型
 * 
 * @param queryType
 */
consumer.queryOtherRevenue.QUERY_BY_DATE = 'TD';
consumer.queryOtherRevenue.QUERY_BY_OTHERREVENUE_NO = 'OR';

/**
 * 客户编码值改变时监听
 */
consumer.queryOtherRevenue.customerChangeListener = function(me, tEvent, eOpts) {
	var customerCode = me.value;
	var form = me.up('form').getForm();
	consumer.queryOtherRevenue.queryCustomerInfoByCode(customerCode, form);
}

/**
 * 根据客户编码查询客户信息
 */
consumer.queryOtherRevenue.queryCustomerInfoByCode = function(customerCode, form) {
	var newValue = form.findField('vo.dto.paymentType').getValue();
	var customerType = form.findField('vo.dto.customerType').getValue();

	Ext.Ajax.request({
		url : consumer.realPath('queryCustomerInfo.action'),
		params : {
			'vo.custDto.code' : customerCode,
			'vo.custDto.type' : customerType
		},
		method : 'post',
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var customerDto = result.vo.custDto;
			if (customerDto) {
				var custName = customerDto.name;
				var custPhone = customerDto.phone;

				if (Ext.isEmpty(custName)) {
					// 客户不存在,请先到CRM中录入客户信息，再进行操作
					Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
							consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.custNotExist.pleaseInputFirst'));
				}
			}

		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), result.message, 'error', 2000);
		}
	});
}

/**
 * 客户类型选择组件监听
 */
consumer.queryOtherRevenue.customerTypeChangeListener = function(value, form) {
	var customerTypes = {
		'LC' : 'consumer.queryOtherRevenue.commoncustomerselector',
		'PA' : 'consumer.queryOtherRevenue.commonvehagencycompselector',
		'AA' : 'consumer.queryOtherRevenue.commonallagentselector',
		'LS' : 'consumer.queryOtherRevenue.landStowage'
	}

	var typeCmpName = customerTypes[value]
	for (item in customerTypes) {
		var cmp = form.findField(customerTypes[item]);
		if (item == value) {
			cmp.show();
		} else {
			cmp.reset();
			cmp.hide();
		}
	}
}

// 请输入1个或10内的多个单号,单号之间用半角逗号隔开
consumer.queryOtherRevenue.PROMPT = consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.waybillPrompt');

/**
 * 
 * @param {}
 *            产品类型
 * @return {}此处如果有全选，则需要转化下
 */
consumer.queryOtherRevenue.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for (var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

/* 设置小票的查询参数 */
consumer.queryOtherRevenue.setParams = function(form, queryType) {
	// 定义查询参数
	var params = {};
	// 按日期查询
	if (consumer.queryOtherRevenue.QUERY_BY_DATE == queryType) {
		
		var timeType  = form.findField('vo.dto.timeType').getGroupValue();//@218392 zhangyongxue 2015-10-12 时间类型 timeType
		var startDate = form.findField('vo.dto.createStartTime').getValue();
		var endDate = form.findField('vo.dto.createEndTime').getValue();
		var landStowage = form.findField('consumer.queryOtherRevenue.landStowage').getValue();
		var productCodeList = consumer.queryOtherRevenue.convertProductCode(form.findField('vo.dto.productCodeList').getValue());

		if (!startDate) {

			// 开始日期不能为空
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.createStartDateCannotBeNull'));
			return null;
		}
		if (!endDate) {

			// 结束日期不能为空
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.createEndDateCannotBeNull'));
			return null;
		}

		// 转化成日期对象
		startDate = Ext.Date.parse(startDate, "Y-m-d H:i:s", true);
		endDate = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);

		var diffDays = stl.compareTwoDate(startDate, endDate);
		if (diffDays > stl.DATELIMITDAYS_MONTH) {

			// 录入起始日期和结束日期间隔不能超过{0}天
			// stl.DATELIMITDAYS_MONTH
			var promptMsg = consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.createDateDiffCannotExceedSomeDays')
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), promptMsg);
			return null;
		} else if (diffDays < 1) {

			// 录入开始日期不能晚于结束日期
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.createStartDateCannotGtEndDate'));
			return null;
		}

		var customerCode = null;

		// 后台默认传递属性到struts报错，所以手动删除该对象
		if (!Ext.isEmpty(form.findField("consumer.queryOtherRevenue.commonallagentselector").getValue())) {
			customerCode = form.findField('consumer.queryOtherRevenue.commonallagentselector').getValue();
		} else if (!Ext.isEmpty(form.findField("consumer.queryOtherRevenue.commonvehagencycompselector").getValue())) {
			customerCode = form.findField('consumer.queryOtherRevenue.commonvehagencycompselector').getValue();
		} else if (!Ext.isEmpty(landStowage)) {
			customerCode = landStowage;
		} else {
			customerCode = form.findField('consumer.queryOtherRevenue.commoncustomerselector').getValue();
		}

		var generatingOrgCodeLast = form.findField('vo.dto.generatingOrgCode').lastValue;
		var paymentType = form.findField('vo.dto.paymentType').getValue();

		var generatingOrgCode = "";
		var generatingOrgName = "";

		if (generatingOrgCodeLast == stl.currentDept.name) {
			generatingOrgCode = stl.currentDept.code;
			generatingOrgName = stl.currentDept.name;
		} else {
			generatingOrgCode = generatingOrgCodeLast;
			generatingOrgName = form.findField('vo.dto.generatingOrgCode').getRawValue();
		}
		// 获取FORM所有值
		params = {
			'vo.dto.timeType'		: timeType,//@author 218392 zhangyongxue 2015-10-12
			'vo.dto.createStartTime' : startDate,
			'vo.dto.createEndTime' : endDate,
			'vo.dto.generatingOrgCode' : generatingOrgCode,
			'vo.dto.generatingOrgName' : generatingOrgName,
			'vo.dto.customerCode' : customerCode,
			'vo.dto.paymentType' : paymentType,
			'vo.dto.productCodeList' : productCodeList
		};

	} else if (consumer.queryOtherRevenue.QUERY_BY_OTHERREVENUE_NO == queryType) {// 按运单号查询
		var otherRevenueNos = form.findField('otherRevenueNos').getValue();
		var otherRevenueNosArray_tmp = stl.splitToArray(otherRevenueNos);
		var otherRevenueNosArray = new Array();

		for (var i = 0; i < otherRevenueNosArray_tmp.length; i++) {
			if (otherRevenueNosArray_tmp[i].trim() != '') {
				otherRevenueNosArray.push(otherRevenueNosArray_tmp[i]);
			}
		}

		if (otherRevenueNosArray.length == 0) {
			Ext.Msg
					.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
							consumer.queryOtherRevenue.PROMPT);
			return null;
		}
		if (otherRevenueNosArray.length > 10) {
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.canInput10NoMost'));
			return null;
		}
		Ext.apply(params, {
					'vo.dto.otherRevenueNos' : otherRevenueNosArray
				});
	}
	// 设置查询类型
	Ext.apply(params, {
				'vo.dto.queryPageTab' : queryType
			});
	return params;
}
/**
 * 获取Grid组件信息
 * 
 * @returns
 */
consumer.queryOtherRevenue.getPageObj = function() {
	var me = Ext.getCmp('T_consumer-initQueryRevenuePage_content');
	if (me) {
		var grid = me.getQueryGrid();
		if (grid.store.data.length == 0) {

			// 查询结果集为空不能进行该操作
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.queryResultIsEmpty.cannotOperate'));
			return null;
		}
		return grid;
	}
	return null;
};
/**
 * Form查询方法
 */
consumer.queryOtherRevenue.query = function(f, me, queryType) {
	var form = f.getForm();
	var root = Ext.getCmp('T_consumer-initQueryRevenuePage_content');
	var grid = root.getQueryGrid();
	var totalAmountField = root.getTotalAmountField();
	if (form.isValid()) {
		var params = consumer.queryOtherRevenue.setParams(form, queryType);
		if (null == params) {
			return;
		}
		// 设置查询参数
		grid.store.setSubmitParams(params);
		// 设置该按钮灰掉
		me.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
					me.enable(true);
				}, 30000);
		// 设置统计值
		grid.store.loadPage(1, {
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);

						if (!Ext.isEmpty(result)) {
							totalAmountField.setValue(stl.amountFormat(result.vo.totalAmount))
						}
						me.enable(true);
					}
				});
	} else {

		// 请检查输入条件是否合法
		Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
						.i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
	}
}
/**
 * 财务经理 有权限作废还借支小票方法
 */
consumer.queryOtherRevenue.disableOtherRevenueOfRB = function(grid, rowIndex, colIndex) {
	Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
	Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');

	// 确定要作废吗?
	Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
					.i18n('foss.stl.consumer.otherRevenue.sureObsolete'), function(btn, text) {
				if (btn == 'yes') {
					var record = grid.getStore().getAt(rowIndex);
					var id = record.get('id');
					if (!id) {

						// 请选中要作废的记录
						Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
								consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseSelectRecordToObsolete'));
						return;
					}
					//如果选择作废的记录为结清货款时产生的小票记录 则只有包装与代收产品管理组可以作废
					var invoiceCategory = record.get('invoiceCategory');
					var otherRevenueNo = record.get('otherRevenueNo');
					var active = record.get('active');
					var params = {
						'vo.dto.id' : id,
						'vo.dto.otherRevenueNo' : otherRevenueNo,
						'vo.dto.active' : active,
						'vo.dto.invoiceCategory':invoiceCategory
					};
					Ext.Ajax.request({
								url : consumer.realPath('canxelOtherRevenueOfRB.action'),
								params : params,
								method : 'post',
								success : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											result.message);
									var grid = Ext.getCmp('T_consumer-initQueryRevenuePage_content').getQueryGrid();
									grid.getPagingToolbar().moveFirst();
								},
								failure : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											result.message);
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											json.message);
								}
							});
				}
			});

}
/**
 * 导出方法
 */
consumer.queryOtherRevenue.exportOtherRevenue = function() {
	var grid = consumer.queryOtherRevenue.getPageObj();

	// 判断是否有数据
	if (grid.store.data.length == 0) {
		Ext.Msg.alert('温馨提示', '表格没有数据，不能进行导出操作！');
		return false;
	}
	/**
	 * if(grid==null){ return; }
	 */
	Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
	Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');

	// 确定要导出查询结果吗?
	Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
					.i18n('foss.stl.consumer.otherRevenue.sureExportQueryResult'), function(btn, text) {
				if (btn == 'yes') {
					var params = grid.store.submitParams;
					if (!Ext.fly('downloadQueryOtherForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadQueryOtherForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					Ext.Ajax.request({
								url : consumer.realPath('exprotOtherRevenue.action'),
								form : Ext.fly('downloadQueryOtherForm'),
								params : params,
								method : 'post',
								isUpload : true,
								success : function(response) {
									var result = Ext.decode(response.responseText);
								},
								failure : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											json.message);
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											json.message);
								}
							});
				}
			});
}
/**
 * 设置打印公共参数
 * 
 * @returns
 */
consumer.queryOtherRevenue.getPrintOtherRenvenueNoParams = function(grid) {
	// 获取Form信息
	var params = grid.store.submitParams;
	if (params["vo.dto.queryPageTab"] == consumer.queryOtherRevenue.QUERY_BY_DATE) {
		// BUG-51962
		if (!params["vo.dto.generatingOrgCode"]) {
			Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
							.i18n('foss.stl.consumer.otherRevenue.nodept'));
			return false;
		}
	}

	var generatingComName = grid.store.data.items[0].data.generatingComName;
	var generatingComCode = grid.store.data.items[0].data.generatingComCode;

	var otherRevenueNos = params["vo.dto.otherRevenueNos"];

	if (otherRevenueNos) {
		otherRevenueNos = otherRevenueNos.toString();
	}

	var formParams = {
		'queryPageTab' : params["vo.dto.queryPageTab"],
		'timeType' :	params["vo.dto.timeType"], //@author 218392 zhangyongxue 2015-10-12
		'createStartTime' : params["vo.dto.createStartTime"],
		'createEndTime' : params["vo.dto.createEndTime"],
		'generatingComName' : params["generatingComName"],
		'generatingComCode' : params["generatingComCode"],
		'customerType' : params["vo.dto.customerType"],
		'customerCode' : params["vo.dto.customerCode"],
		'generatingOrgName' : params["vo.dto.generatingOrgName"],
		'generatingOrgCode' : params["vo.dto.generatingOrgCode"],
		'paymentType' : params["vo.dto.paymentType"],
		'otherRevenueNos' : otherRevenueNos,
		'printUserName' : stl.emp.empName,
		'printUserCode' : stl.emp.empCode,
		'printOrgName' : stl.dept.name,
		'printOrgCode' : stl.dept.code,
		'productCodeList' : params["vo.dto.productCodeList"]
	};

	// 转化列头和列明
	var columns = grid.columns;
	var arrayColumns = [];
	var index = 0;// 列标
	// 传入后台打印，与index拼接为后台列明
	var headerStr = "{";
	for (var i = 1; i < columns.length; i++) {
		if (columns[i].isHidden() == false) {
			var hederName = columns[i].text;
			if (i != columns.length - 1) {

				// 操作列
				if (columns[i].text != consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.actionColumn')) {
					index = index + 1;
					headerStr = headerStr + '\'' + 'columnName' + index + '\':\'' + hederName + '\',';
					arrayColumns.push(columns[i].dataIndex);
				}
			} else {
				index = index + 1;
				arrayColumns.push(columns[i].dataIndex);
				headerStr = headerStr + '\'' + 'columnName' + index + '\':\'' + hederName + '\'';
			}
		}
	}
	// 控制打印列数
	if (arrayColumns.length > 15) {

		// 最多不能超过15列
		Ext.Msg.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
						.i18n('foss.stl.consumer.otherRevenue.15ColumnMost'));
		return false;
	}

	headerStr = headerStr + '}';
	var headerObject = Ext.decode(headerStr);
	targetObject = Ext.merge(headerObject, formParams);
	targetObject.arrayColumns = arrayColumns;

	return targetObject;

}

/**
 * 打印明细
 */
consumer.queryOtherRevenue.printOtherRevenueDetail = function() {
	var grid = consumer.queryOtherRevenue.getPageObj();
	if (grid == null) {
		return;
	}
	Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
	Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');

	// 确定要打印吗?
	Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
					.i18n('foss.stl.consumer.otherRevenue.surePrint'), function(btn, text) {
				if (btn == 'yes') {
					// 获取打印参数信息
					targetObject = consumer.queryOtherRevenue.getPrintOtherRenvenueNoParams(grid);
					if (targetObject != false) {
						do_printpreview('otherrenvenuenototal', targetObject, ContextPath.STL_WEB);
					}
				}
			});
}
/**
 * 打印
 */
consumer.queryOtherRevenue.printOtherRevenue = function(grid, rowIndex, colIndex) {
	Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
	Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');

	// 确定要打印吗?
	Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
					.i18n('foss.stl.consumer.otherRevenue.surePrint'), function(btn, text) {
				if (btn == 'yes') {
					var record = grid.getStore().getAt(rowIndex);
					var id = record.get('id');
					var active = record.get('active');
					if (!id) {

						// 请选中要打印的记录
						Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
								consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseSelectRecordToPrint'));
						return;
					}
					if (active == 'N') {

						// 小票无效不能打印
						Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
								consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.invalid.cannotPrint'));
						return;
					}
					// 获取打印参数信息
					var formParams = {
						'id' : id
					};
					do_printpreview('otherrenvenueno', formParams, ContextPath.STL_WEB);
				}
			});
}

/**
 * 作废小票
 */
consumer.queryOtherRevenue.disableOtherRevenue = function(grid, rowIndex, colIndex) {

	Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
	Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');

	// 确定要作废吗?
	Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
					.i18n('foss.stl.consumer.otherRevenue.sureObsolete'), function(btn, text) {
				if (btn == 'yes') {
					var record = grid.getStore().getAt(rowIndex);
					var id = record.get('id');
					if (!id) {

						// 请选中要作废的记录
						Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
								consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseSelectRecordToObsolete'));
						return;
					}
					//如果选择作废的记录为结清货款时产生的小票记录 则只有包装与代收产品管理组可以作废
					var invoiceCategory = record.get('invoiceCategory');
					var otherRevenueNo = record.get('otherRevenueNo');
					var active = record.get('active');
					var params = {
						'vo.dto.id' : id,
						'vo.dto.otherRevenueNo' : otherRevenueNo,
						'vo.dto.active' : active,
						'vo.dto.invoiceCategory':invoiceCategory
					};
					Ext.Ajax.request({
								url : consumer.realPath('cancelOtherRevenue.action'),
								params : params,
								method : 'post',
								success : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											result.message);
									var grid = Ext.getCmp('T_consumer-initQueryRevenuePage_content').getQueryGrid();
									grid.getPagingToolbar().moveFirst();
								},
								failure : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											result.message);
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
											json.message);
								}
							});
				}
			});

}

/**
 * Form重置方法
 */
consumer.queryOtherRevenue.reset = function() {
	this.up('form').getForm().reset();
}
/**
 * 小票 model
 */
Ext.define('Foss.queryOtherRevenue.QueryOtherRevenueModel', {
			extend : 'Ext.data.Model',
			fields : ['id', {
						name : 'generatingOrgCode'
					}, {
						name : 'generatingOrgName'
					}, {
						name : 'generatingComCode'
					}, {
						name : 'generatingComName'
					}, 'otherRevenueNo', 'amount', 'customerCode', 'customerName', 'customerPhone', 'paymentType',
					'incomeCategories', 'waybillNo', 'createUserCode', 'createUserName', 'active','invoiceCategory','posBatchNo',{
						name : 'createTime', // 录入时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'businessDate', // 业务时间
						type : 'date',
						convert : dateConvert
					},{
						name : 'disableTime', // 作废时间
						type : 'date',
						convert : dateConvert
					}, 'customerType', 'customerPhone', 'isDisable', 'versionNo', {
						name : 'productCode'
					}, 'notes']
		});
/**
 * 客户类型
 */
Ext.define('Foss.queryOtherRevenue.customerTypeStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'LC',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.W')
						},// 客户
						{
							code : 'PA',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.PD')
						}, // 偏线代理
						{
							code : 'AA',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.AD')
						}// 空运代理
				]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
/**
 * 收款方式
 */
Ext.define('Foss.queryOtherRevenue.PaymentTypeStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : '',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.all')
						},// 全部
						{
							code : 'CH',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType.CH')
						},// 现金
						{
							code : 'CD',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType.CD')
						},// 银行卡
						{
							code : 'CT',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType.CT')
						},// 月结
						{
							code : 'DT',
							name : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType.DT')
						}// 临时欠款
				]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});

/**
 * 小票查询 store
 */
Ext.define('Foss.queryOtherRevenue.QueryOtherRevenueStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.queryOtherRevenue.QueryOtherRevenueModel',
			pageSize : 50,//@author 218392 zhangyongxue 2015-10-16 页面分页选项卡默认初始值为50，改成默认为50
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : consumer.realPath('queryOtherRevenue.action'),
				reader : {
					type : 'json',
					root : 'vo.list',
					totalProperty : 'totalCount'
				}
			},
			submitParams : {},
			setSubmitParams : function(submitParams) {
				this.submitParams = submitParams;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.listeners = {
					'beforeload' : function(store, operation, eOpts) {
						Ext.apply(me.submitParams, {
									"limit" : operation.limit,
									"page" : operation.page,
									"start" : operation.start
								});
						Ext.apply(operation, {
									params : me.submitParams
								});
					}
				};
				me.callParent([cfg]);
			}
		});

/**
 * FORM 定义
 */

// 按日期查询 Form
Ext.define('Foss.queryOtherRevenue.QueryDateForm', {
			extend : 'Ext.form.Panel',
			frame : false,
			columnWidth : 1,
			defaults : {
				margin : '15 5 5 0',
				labelWidth : 85,
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'table',
				columns : 3
			},
			items : [
			         
			         {//@author 218392 张永雪 2015-10-10 11:09:20
						xtype : 'fieldcontainer',
						fieldLabel : '时间类型',
						columnWidth : 1,
						layout : 'column',
						items:[{
								xtype : 'radiofield',
								name : 'vo.dto.timeType',
								inputValue : '1',
								columnWidth : .5,
								id:'querying_consumer_queryOtherRevenue_lrsj',
								checked : true,
								boxLabel : '录入时间',
					        	listeners:{
					        		change:function(field,newV,oldV ){
					        			
					        		}
					        	}
							},{
								xtype : 'radiofield',
								name : 'vo.dto.timeType',
								inputValue : '2',
								columnWidth : .5,
								id:'querying_consumer_queryOtherRevenue_zfsj',
								boxLabel : '作废时间',
					        	listeners:{
					        		change:function(field,newV,oldV ){
					        			
					        		}
					        	}
						}]
					}
			         
			         
			         ,{//@author 218392 张永雪  2015-10-10
						xtype : 'container',
						border : false,
						html : '&nbsp;&nbsp;'
					},{//@author 218392 张永雪  2015-10-10
						xtype : 'container',
						border : false,
						html : '&nbsp;&nbsp;'
					},{
						xtype : 'datetimefield_date97',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.inputStartTime'),// 开始时间 @218392 张永雪 2015-10-10
						id : 'FOSS_Consumer_QueryOtherRevenueQueryDateForm_StartTime_ID',
						time : true,
						name : 'vo.dto.createStartTime',
						editable : 'false',
						value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.START_PREFIX,
						dateConfig : {
							el : 'FOSS_Consumer_QueryOtherRevenueQueryDateForm_StartTime_ID-inputEl',
							dateFmt : 'yyyy-MM-dd HH:mi:ss'
						}
					}, {
						xtype : 'datetimefield_date97',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.inputEndTime'),// 结束时间 @218392 张永雪 2015-10-10
						id : 'FOSS_Consumer_QueryOtherRevenueQueryDateForm_EndTime_ID',
						time : true,
						name : 'vo.dto.createEndTime',
						editable : 'false',
						value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX,
						dateConfig : {
							el : 'FOSS_Consumer_QueryOtherRevenueQueryDateForm_EndTime_ID-inputEl',
							dateFmt : 'yyyy-MM-dd HH:mi:ss',
							maxDate : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX
						}
					}, {
						xtype : 'dynamicorgcombselector',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingOrgName'),
						name : 'vo.dto.generatingOrgCode',
						value : stl.currentDept.name,
						labelWidth : 85,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
					},/*
						 * { name:'vo.dto.generatingOrgName',
						 * fieldLabel:consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingOrgName'),//收入部门
						 * readOnly:true, value:stl.currentDept.name }, {
						 * name:'vo.dto.generatingOrgCode',
						 * fieldLabel:consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingOrgCode'),//收入部门编码
						 * hidden:true, value:stl.currentDept.code },
						 */
					{
						xtype : 'combo',
						name : 'vo.dto.customerType',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType'),// 客户类型
						value : '',
						displayField : 'name',
						valueField : 'code',
						queryMode : 'local',
						triggerAction : 'all',
						allowBlank : true,
						editable : false,
						store : Ext.create('Foss.queryOtherRevenue.customerTypeStore'),
						listeners : {
							'change' : function(me, newValue, oldValue, eOpts) {
								var form = me.up('form').getForm()
								consumer.queryOtherRevenue.customerTypeChangeListener(newValue, form);
							}
						}
					}, {
						xtype : 'commoncustomerselector',
						// name:'vo.dto.customerCode',
						name : 'consumer.queryOtherRevenue.commoncustomerselector',
						isPaging : true,
						singlePeopleFlag : 'Y',
						all : 'true',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerInfo')
						// 客户信息
				}	, {
						xtype : 'commonLdpAgencyCompanySelector',
						name : 'consumer.queryOtherRevenue.landStowage',
						isPaging : true,
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.landStowage')
						// 快递代理
				}	, {
						xtype : 'commonairagentselector',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.airAgency'),
						name : 'consumer.queryOtherRevenue.commonallagentselector',
						isPaging : true, // 分页
						// hidden:true,
						listeners : {
							blur : consumer.queryOtherRevenue.customerChangeListener
						}
					}, {
						xtype : 'commonvehagencycompselector',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.partLineAgency'),
						name : 'consumer.queryOtherRevenue.commonvehagencycompselector',
						isPaging : true, // 分页
						// hidden:true,
						listeners : {
							blur : consumer.queryOtherRevenue.customerChangeListener
						}
					}, {
						xtype : 'combo',
						name : 'vo.dto.paymentType',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType'),// 收款方式
						value : '',
						displayField : 'name',
						valueField : 'code',
						queryMode : 'local',
						triggerAction : 'all',
						allowBlank : true,
						editable : false,
						store : Ext.create('Foss.queryOtherRevenue.PaymentTypeStore')
					}, {
						xtype : 'combobox',
						name : 'vo.dto.productCodeList',
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.pay.queryBillPayable.productCode'),
						store : Ext.create('Foss.pkp.ProductStore'),
						multiSelect : true,
						queryMode : 'local',
						displayField : 'name',
						valueField : 'code',
						colspan : 3
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						colspan : 3,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : consumer.queryOtherRevenue.reset
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.query'),
									columnWidth : .1,
									cls : 'yellow_button',
									handler : function() {
										var form = this.up('form');
										var me = this;
										consumer.queryOtherRevenue.query(form, me, consumer.queryOtherRevenue.QUERY_BY_DATE)
									}
								}]
					}]
		});

// 按小票单号查询 Form
Ext.define('Foss.queryOtherRevenue.QueryWaybillForm', {
			extend : 'Ext.form.Panel',
			frame : false,
			defaults : {
				margin : '15 5 5 0',
				labelWidth : 85,
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
						xtype : 'textarea',
						autoScroll : true,
						emptyText : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.waybillPrompt'),// 请输入1个或10内的多个单号,单号之间用半角逗号隔开
						fieldLabel : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.otherRevenueNo'),// 小票单号
						name : 'otherRevenueNos',
						height : 80,
						width : 600,
						allowBlank : false,
						colspan : 3,
						columnWidth : .4,
						listeners : {
							blur : function(_this, newValue, oldValue, eOpts) {
							},
							focus : function(e, t, eOpts) {
							}
						}
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						colspan : 3,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : consumer.queryOtherRevenue.reset
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.query'),
									columnWidth : .1,
									cls : 'yellow_button',
									handler : function() {
										var form = this.up('form');
										var me = this;
										consumer.queryOtherRevenue.query(form, me,
												consumer.queryOtherRevenue.QUERY_BY_OTHERREVENUE_NO);
									}
								}]
					}]
		});

// 小票信息 Grid
Ext.define('Foss.queryOtherRevenue.QueryOtherRevenueGrid', {
			extend : 'Ext.grid.Panel',
			title : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.otherRevenueInfo'),// 小票信息
			columnWidth : 1,
			stripeRows : true,
			columnLines : true,
			collapsible : false,
			// bodyCls: 'autoHeight',
			frame : true,
			// cls: 'autoHeight',
			store : null,
			autoScroll : true,
			height : 420,
			emptyText : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.emptyText'),
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			pagingToolbar : null,
			getPagingToolbar : function() {
				var me = this;
				if (Ext.isEmpty(me.pagingToolbar)) {
					me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
								store : me.store,
								pageSize : 50,
								maximumSize : 500,
								plugins : 'pagesizeplugin',
								height : 25,
								columnWidth : .7
							});
				}
				return me.pagingToolbar;
			},
			bottomBar : null,
			getBottomBar : function() {
				var me = this;
				if (Ext.isEmpty(me.bottomBar)) {
					me.bottomBar = Ext.create('Ext.panel.Panel', {
								border : 0,
								layout : {
									type : 'column'
								},
								items : [{
											xtype : 'displayfield',
											allowBlank : true,
											name : 'totalAmount',
											labelWidth : 80,
											width : 185,
											fieldLabel : '总金额',
											value : '',
											columnWidth : .3
										}, me.getPagingToolbar()]
							})
				}
				return me.bottomBar;
			},
			columns : [{
						xtype : 'actioncolumn',
						width : 100,
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.actionColumn'),// 操作列
						align : 'center',
						items : [{
									tooltip : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.invalid'),// 作废
									iconCls : 'deppon_icons_cancel',
									width : 42,
									handler : function(grid, rowIndex, colIndex) {
										consumer.queryOtherRevenue.disableOtherRevenue(grid, rowIndex, colIndex);
									},
									// hidden:!consumer.queryOtherRevenue.isPermission('/stl-web/consumer/cancelOtherRevenue.action')
									getClass : function(v, m, r, rowIndex) {
										if (!consumer.queryOtherRevenue
												.isPermission('/stl-web/consumer/cancelOtherRevenue.action')) {
											return 'statementBill_hide';
										} else {
											return 'deppon_icons_cancel';
										}
									}
								}, {
									tooltip : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.print'),// 打印
									iconCls : 'deppon_icons_print',
									width : 42,
									handler : function(grid, rowIndex, colIndex) {
										consumer.queryOtherRevenue.printOtherRevenue(grid, rowIndex, colIndex);
									}
								}]
					}, {
						text : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingOrgName'),
						dataIndex : 'generatingOrgName',
						width : 138
					},// 收入部门
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingOrgCode'),
						dataIndex : 'generatingOrgCode',
						hidden : true
					},// 收入部门编码
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generatingComName'),
						dataIndex : 'generatingComName',
						width : 138
					},// 收入公司
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.generationComCode'),
						dataIndex : 'generatingComCode',
						hidden : true
					},// 收入公司编码
					{
						header : consumer.queryOtherRevenue.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),
						dataIndex : 'productCode',
						renderer : function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.otherRevenueNo'),
						dataIndex : 'otherRevenueNo',
						width : 90
					},// 小票单号
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.amount'),
						dataIndex : 'amount',
						align : 'right',
						width : 90
					},// 金额
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerCode'),
						dataIndex : 'customerCode',
						width : 100
					},// 客户编码
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerName'),
						dataIndex : 'customerName',
						width : 100
					},// 客户名称
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType'),
						dataIndex : 'paymentType',
						width : 90,// 收款方式
						renderer : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(value, 'OTHER_REVENUE__PAYMENT_TYPE');
						}
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.incomeCategoriesType'),
						dataIndex : 'incomeCategories',
						width : 90,// 收入类别
						renderer : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__COLLECTION_TYPE');
						}
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.waybillNo'),
						dataIndex : 'waybillNo',
						width : 90
					},// 运单单号
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.creatorName'),
						dataIndex : 'createUserName',
						width : 100
					},// 录入人员
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.creatorCode'),
						dataIndex : 'createUserCode',
						width : 100,
						hidden : true
					},// 录入人员编码
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.createTime'),
						dataIndex : 'createTime',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 140
					},// 录入时间

					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.businessDate'),
						dataIndex : 'businessDate',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 140,
						hidden : true
					},// 业务时间
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType'),
						dataIndex : 'customerType',
						width : 90,
						hidden : true,// 客户类别
						renderer : function(value) {
							if (value == 'LC') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.W');// 客户
							} else if (value == 'PA') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.PD');// 偏线代理
							} else if (value == 'AA') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.AD');// 空运代理
							} else if (value == 'LS') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.landStowage');// 快递代理
							} else {
								return value;
							}
						}
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerPhone'),
						dataIndex : 'customerPhone',
						width : 100,
						hidden : true
					},// 客户电话
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.version'),
						dataIndex : 'versionNo',
						width : 90,
						hidden : true
					},// 版本号
					{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.note.active'),
						dataIndex : 'active',
						width : 100,
						hidden : true, // 是否有效
						renderer : function(value) {
							if (value == 'Y') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.note.active.Y');// 有效
							} else if (value == 'N') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.note.active.N');// 无效
							} else {
								return value;
							}
						}
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.isInvalid'),
						dataIndex : 'isDisable',
						width : 100,
						//hidden : true, // 是否作废 @218392 zhangyongxue 2015-10-12
						renderer : function(value) {
							if (value == 'Y') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.isInvalid.Y');// 是
							} else if (value == 'N') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.isInvalid.N');// 否
							} else {
								return value;
							}
						}
					},{//作废时间@218392 zhangyongxue 2015-10-12
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.disableTime'),
						dataIndex : 'disableTime',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 140
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.posBatchNo'),
						dataIndex : 'posBatchNo',
						width : 100,
						hidden : true
					}, {
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.notes'),
						dataIndex : 'notes',
						width : 100,
						hidden : false//@218392 zhangyongxue 2015-10-16 需求要求配合前台页面打印清单不能超过15列
					}// 备注
					,{
						text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.invoiceCategory'),
						dataIndex : 'invoiceCategory',
						width : 100,
						hidden : true,
						renderer : function(value) {
							if (value == 'WAYBILL') {
								return consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.invoiceCategoryClass');// 系统
							}else {
								return value;
							} 
						}
				}//小票产生类别
			],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				
				me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'button',
										text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.printList'),// 打印清单
										columnWidth : .1,
										handler : consumer.queryOtherRevenue.printOtherRevenueDetail
									}, {
										xtype : 'button',
										text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.export'),// 导出
										columnWidth : .1,
										handler : consumer.queryOtherRevenue.exportOtherRevenue,
										disabled : !consumer.queryOtherRevenue
												.isPermission('/stl-web/consumer/exprotOtherRevenue.action'),
										hidden : !consumer.queryOtherRevenue
												.isPermission('/stl-web/consumer/exprotOtherRevenue.action')
									},{
										xtype : 'button',
										text : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.alsoborrow'),// 作废还借支小票
										columnWidth : .1,
										
										handler :function (){// grid, rowIndex, colIndex
											Ext.MessageBox.buttonText.yes = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.OK');
											Ext.MessageBox.buttonText.no = consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.cancel');
											var grid = Ext.getCmp('Foss_queryOtherRevenue_queryGrid');
						                    var selectionModel = grid.getSelectionModel();
						                    var rows = selectionModel.getSelection();
											// 确定要作废吗?
											Ext.Msg.confirm(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.queryOtherRevenue
															.i18n('foss.stl.consumer.otherRevenue.sureObsolete'), function(btn, text) {
														if (btn == 'yes') {
/*															var record = grid.getStore().getAt(rowIndex);
															var id = record.get('id');*/
										                    if(rows.length <1){
																// 请选中要作废的记录
																Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
																		consumer.queryOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseSelectRecordToObsolete'));
																return;
															}
										                  //如果选择作废的记录为结清货款时产生的小票记录 则只有包装与代收产品管理组可以作废
										                 for(var i =0;i<rows.length;i++){
										                    	var invoiceCategory = rows[i].get('invoiceCategory');
										                    	var otherRevenueNo 	= rows[i].get('otherRevenueNo');
										                    	var active 			= rows[i].get('active');
										                    	var id 				= rows[i].get('id');
	/*														
															var invoiceCategory = record.get('invoiceCategory');
															var otherRevenueNo = record.get('otherRevenueNo');
															var active = record.get('active');*/
															var params = {
																'vo.dto.id' : id,
																'vo.dto.otherRevenueNo' : otherRevenueNo,
																'vo.dto.active' : active,
																'vo.dto.invoiceCategory':invoiceCategory
															};
															Ext.Ajax.request({
																		url : consumer.realPath('canxelOtherRevenueOfRB.action'),
																		params : params,
																		method : 'post',
																		success : function(response) {
																			var result = Ext.decode(response.responseText);
																			Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
																					result.message);
																			var grid = Ext.getCmp('T_consumer-initQueryRevenuePage_content').getQueryGrid();
																			grid.getPagingToolbar().moveFirst();
																		},
																		failure : function(response) {
																			var result = Ext.decode(response.responseText);
																			Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
																					result.message);
																		},
																		exception : function(response) {
																			var json = Ext.decode(response.responseText);
																			Ext.MessageBox.alert(consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
																					json.message);
																		}
																	});
															
										                    }
														}
													});


											},
										hidden : !consumer.queryOtherRevenue
												.isPermission('/stl-web/consumer/canxelOtherRevenueOfRB.action')}]
						}];

				me.store = Ext.create('Foss.queryOtherRevenue.QueryOtherRevenueStore');

				me.bbar = me.getBottomBar();
				me.getPagingToolbar().store = me.store;
				me.callParent([cfg]);
			}
		});

/**
 * 小票查询tab
 */
Ext.define('Foss.queryOtherRevenue.QueryOtherRevenueTab', {
			extend : 'Ext.tab.Panel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
			columnWidth : 1,
			// columnHeight: 'autoHeight',
			height : 276,
			items : [{
						title : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.queryByDate'),// 日期查询
						tabConfig : {
							width : 120
						},
						items : [Ext.create('Foss.queryOtherRevenue.QueryDateForm')]
					}, {
						title : consumer.queryOtherRevenue.i18n('foss.stl.consumer.common.queryByNo'),// 按小票单号查询
						tabConfig : {
							width : 120
						},
						items : [Ext.create('Foss.queryOtherRevenue.QueryWaybillForm')]
					}]
		});

// 初始化界面
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_consumer-initQueryRevenuePage_content')) {
				return;
			}
			var queryTab = Ext.create('Foss.queryOtherRevenue.QueryOtherRevenueTab');// 查询TAB
			var queryGrid = Ext.create('Foss.queryOtherRevenue.QueryOtherRevenueGrid', {id: 'Foss_queryOtherRevenue_queryGrid'});// 查询结果GRID

			Ext.create('Ext.panel.Panel', {
						id : 'T_consumer-initQueryRevenuePage_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获得查询FORM
						getQueryTab : function() {
							return queryTab;
						},
						// 获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						getTotalAmountField : function() {
							return queryGrid.down('displayfield');
						},
						items : [queryTab, queryGrid],
						renderTo : 'T_consumer-initQueryRevenuePage-body',
						listeners : {
							'boxready' : function(me) {
								tab = me.getQueryTab().items.items[0].items.items[0];
								form = tab.getForm();
								form.findField('consumer.queryOtherRevenue.commonallagentselector').hide();
								form.findField('consumer.queryOtherRevenue.commonvehagencycompselector').hide();
								form.findField('consumer.queryOtherRevenue.landStowage').hide();
							}
						}
					});
		});