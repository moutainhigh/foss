sign.queryReturnBill.RETURN_BILL_TYPE = 'RETURNBILLTYPE';
(function() {
	sign.queryReturnBill.returnBillTypeStore1 = FossDataDictionary
		.getDataDictionaryStore(sign.queryReturnBill.RETURN_BILL_TYPE,
		null, {
			'valueCode' : '',
			'valueName' : '全部'
		});
	sign.queryReturnBill.returnBillTypeStore1
		.removeAt(sign.queryReturnBill.returnBillTypeStore1.find(
			'valueCode', 'NONE'));
})();
/**
 * @param {}
 *            date--比较日期 day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
sign.queryReturnBill.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);// 天数增加
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);
	return t2;
};

/**
 * @param {}
 *            date--比较日期 day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期 23:59:59
 */
sign.queryReturnBill.getTargetDateEnd = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);// 天数增加
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);
	return t2;
};

// 删除左右两端的空格
sign.queryReturnBill.trim = function(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
};

// 定义查询模型
Ext.define('Foss.QueryReturnBill.Model.ReturnBillModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'waybillNo',
		type : 'string'
	}, // 运单号
		{
			name : 'issendSms',
			type : 'string'
		}, // 是否发了短信通知
		{
			name : 'returnbillStatus',
			type : 'string',// 返单状态
			convert : function(value) {
				if (value != null) {
					if (value == 'NONE_RETURN_BILL') {
						return '未返单';// 未返单
					} else if (value == 'ALREADY_RETURN_BILL') {
						return '已返单';// 已返单
					} else {
						return value;// 其他值
					}
				} else {
					return null;
				}
			}
		}, // 返单状态
		{
			name : 'returnbillTime',
			type : 'date',
			convert : dateConvert
		}, // 返单时间
		{
			name : 'returnbillType',
			type : 'string',// 返单类型
			convert : function(value) {
				if (value != null) {
					return FossDataDictionary
						.rendererSubmitToDisplay(
						value,
						sign.queryReturnBill.RETURN_BILL_TYPE);

				} else {
					return null;
				}
			}

		}, // 返单类型 确定
		{
			name : 'handler',
			type : 'string'
		}, // 处理人
		{
			name : 'verifyTime',
			type : 'date',
			convert : dateConvert
		}, // 核实时间
		{
			name : 'deliveryCustomerContact',
			type : 'string'
		}, // 发货人
		{
			name : 'deliveryCustomerMobilephone',
			type : 'string'
		}, // 发货人手机
		{
			name : 'expressCompany',
			type : 'string'
		}, // 快递公司
		{
			name : 'expressNo',
			type : 'string'
		}, // 快递单号
		{
			name : 'feedbackInfo',
			type : 'string'
		}, // 核实反馈信息
		{
			name : 'deliveryCustomerTel',
			type : 'string'
		},// 核实反馈信息
		{
			name : 'receiveOrgName',
			type : 'string'
		},// 核实反馈信息
		{
			name : 'orgDiff',
			type : 'string'
		},// 部门区别
		{
			name:'returnbillConfirm',
			type:'string'
		},//	返单确认
		{
			name:'confirmTime',
			type:'date',
			convert : dateConvert
		},//确认时间
		{
			name:'confirmHandler',
			type:'string'
		}//确认人
	]
});

/**
 * 定义更新返单明细的数据模型
 */
Ext.define('Foss.QueryReturnBill.Model.ReturnBillUpdateModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},// id
		{
			name : 'waybillNo',
			type : 'string'
		}, // 运单号
		{
			name : 'verifyTime',
			type : 'date',// 返单核定时间
			convert : function(value) {// 转换器
				if (value != null) {
					return Ext.Date.format(new Date(value),
						'Y-m-d H:i:s');// Y-m-d H:i:s
				} else {
					return null;
				}
			}

		}, {
			name : 'returnbillType',
			type : 'string',// 返单类型
			convert : function(value) {
				if (value != null) {
					return FossDataDictionary
						.rendererSubmitToDisplay(
						value,
						sign.queryReturnBill.RETURN_BILL_TYPE);
				} else {
					return null;// null
				}
			}

		}, // 返单类型
		{
			name : 'handler',
			type : 'string'
		}, // 处理人
		{
			name : 'expressCompany',
			type : 'string'
		}, // 快递公司
		{
			name : 'expressNo',
			type : 'expressNo'
		}, // 快递号
		{
			name : 'feedbackInfo',
			type : 'string'
		}// 核实反馈信息
	]
});

// 创建一个枚举store
Ext.define('Foss.QueryReturnBillInfo.Store.QueryReturnBillTypeStore', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	// 定义一个代理对象
	proxy : {
		// 代理的类型为内存代理
		type : 'memory',
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'items'
		}
	},
	/** 将枚举实际用于对象 */
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 签收时间查询
Ext.define('Foss.QueryReturnBillInfo.Form.QuerySignForm', {
	extend : 'Ext.form.Panel',
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100
	},
	cls:'autoHeight',
	bodyCls:'autoHeight',
	collapsible: true,
	animCollapse: true,
	title:sign.queryReturnBill.i18n('pkp.sign.sign.queryBuilder'),
	frame:true,
	defaultType : 'textfield',// 默认textfield输入类型
	layout : 'column',
	items : [{
		name : 'waybillNo',// 运单号
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.waybillNo'), //运单号,// 字段标题
		regex : /^[0-9]{8,9}$/i,
		regexText : '输入8-9位纯数字单号',
		columnWidth : 0.25
	}, {
		name : 'type', // 返单类型
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillType'), //返单类型,// 字段标题
		xtype : 'combobox',
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : true,
		columnWidth : 0.25,
		store : sign.queryReturnBill.returnBillTypeStore1,
		listeners : {
			'select' : function(combo, records, eOpts) {// 下拉以后没有任何级联操作
				// 所以暂留
			},
			'afterrender' : function(combo, records, eOpts) { // 页面上有默认值
				// 使用该默认值
				combo.setValue('');
				combo.setRawValue('全部')
			}
		}
	}, {
		xtype : 'rangeDateField',
		fieldId : 'FOSS_Time_Id2',
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.openBillTime'), //开单时间,// 开单时间
		allowFromBlank : true,
		allowToBlank : true,
		editable : false,
		dateType : 'datetimefield_date97',
		fromName : 'billStartTime',
		toName : 'billEndTime',
		fromValue : Ext.Date.format(sign.queryReturnBill.getTargetDate(
			new Date(), -1), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(sign.queryReturnBill.getTargetDateEnd(
			new Date(), 0), 'Y-m-d H:i:s'),
		format : 'Y-m-d H:i:s',
		columnWidth : .5
	}, {
		xtype : 'rangeDateField',
		fieldId : 'FOSS_Time_Id3',
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.settlePayment.arriveTime'), // 到达时间
		allowFromBlank : true,
		allowToBlank : true,
		editable : false,
		dateType : 'datetimefield_date97',
		fromName : 'arriveStartTime',
		toName : 'arriveEndTime',
		fromValue : Ext.Date.format(sign.queryReturnBill.getTargetDate(
			new Date(), -1), 'Y-m-d H:i:s'),
		toValue : Ext.Date
			.format(sign.queryReturnBill.getTargetDateEnd(
				new Date(), 0), 'Y-m-d H:i:s'),

		format : 'Y-m-d H:i:s',
		columnWidth : .5
	}, {
		xtype : 'rangeDateField',
		fieldId : 'FOSS_Time_Id4',
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.settlePayment.storeTime'), //入库时间
		allowFromBlank : true,
		allowToBlank : true,
		editable : false,
		dateType : 'datetimefield_date97',
		fromName : 'instockStartTime',
		toName : 'instockEndTime',
		format : 'Y-m-d H:i:s',
		columnWidth : .5
	}, {
		xtype : 'dynamicorgcombselector', // 出发部门 这里应该使用公共选择器
		columnWidth : 0.25,
		name : 'fromDepartmentCode',
		id : 'fromDepartmentCode',// 关联的名称
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.fromDepartment') //出发部门// 字段标题
	}, {
		xtype : 'hidden',
		name : 'signOrWaybillType',// 查询类型0 表示签收时间查询 1表示开单时间查询
		value : '0'
	}, {
		xtype : 'combobox',
		displayField : 'name',
		valueField : 'code',
		queryMode : 'local',
		triggerAction : 'all',
		editable : true,
		name : 'status',// 返单状态
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillStatus'), //返单状态,// 字段标题
		allowBlank : false,
		columnWidth : 0.25,
		store : Ext
			.create(
			'Foss.QueryReturnBillInfo.Store.QueryReturnBillTypeStore',// 下拉匡内容
			{
				data : {
					'items' : [{
						'code' : 'NONE_RETURN_BILL',
						'name' : '未返单'
					},// 未返单
						{
							'code' : 'ALREADY_RETURN_BILL',
							'name' : '已返单'
						}// 已返单
					]
				}
			}),
		listeners : {
			'select' : function(combo, records, eOpts) {// 下拉以后没有任何级联操作
				// 所以暂留
			},
			'afterrender' : function(combo, records, eOpts) { // 页面上有默认值
				// 使用该默认值
				combo.setValue('NONE_RETURN_BILL');
				combo.setRawValue('未返单')
			}
		}
	}, {
		xtype : 'rangeDateField',// 签收时间
		fieldId : 'FOSS_Time_Id',
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.signTime'), //签收时间,// 字段标题
		allowFromBlank : true,
		allowToBlank : true,
		editable : false,
		dateType : 'datetimefield_date97',
		fromName : 'signStartTime',
		toName : 'signEndTime',
		// showDefaultFlag: false,
		format : 'Y-m-d H:i:s',
		columnWidth : .5
	}, {
		xtype : 'radiogroup', // 部门属性
		vertical : true,
		columnWidth : 0.32,
		name : 'orgDiffGroup',
		fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.orgDiff'), //部门属性
		items : [
			{boxLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.fromDepartment1'),name : 'orgDiff',inputValue : 'Y', itemId:'Y', style: {marginTop: '5px'}},//'本部门出发'
			{boxLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.toDepartment'),name : 'orgDiff',inputValue : 'N', itemId:'N', checked : true, style: {marginTop: '5px'}}//'到达本部门'
		]
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.reset'), //重置,
			columnWidth : .08,
			handler : function() {
				sign.queryReturnBill.querySignForm.getForm().reset();// 重置表单内容
				var form = sign.queryReturnBill.querySignForm.getForm();// 得到输入表单
				form.findField('status').setValue('NONE_RETURN_BILL');// 将返单状态设置为默认值
				form.findField('status').setRawValue('未返单');// 将返单状态设置为默认值

				form.findField('type').setValue('');// 将返单状态设置为默认值设置为默认值
				form.findField('type').setRawValue('全部');// 将返单状态设置为默认值设置为默认值
				form.findField('arriveStartTime').setValue(Ext.Date.format(
					sign.queryReturnBill.getTargetDate(new Date(),
						-1), 'Y-m-d H:i:s'));
				form.findField('arriveEndTime').setValue(Ext.Date.format(
					sign.queryReturnBill.getTargetDateEnd(
						new Date(), 0), 'Y-m-d H:i:s'));
				form.findField('billStartTime').setValue(Ext.Date.format(
					sign.queryReturnBill.getTargetDate(new Date(),
						-1), 'Y-m-d H:i:s'));
				form.findField('billEndTime').setValue(Ext.Date.format(
					sign.queryReturnBill.getTargetDateEnd(
						new Date(), 0), 'Y-m-d H:i:s'));

			}
		}, {
			xtype : 'container',
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.search'), //查询,
			hidden:!sign.queryReturnBill.isPermission('returnbillprocessindex/returnbillprocessindexquerybutton'),
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {

				var form = sign.queryReturnBill.querySignForm.getForm();

				// 查询条件是否合法（非空等相关约束）
				var arriveStartTime = form.findField('arriveStartTime').getValue(),// 得到开始时间
					arriveEndTime = form.findField('arriveEndTime').getValue(),
					signStartTime = form.findField('signStartTime').getValue(),// 得到开始时间
					signEndTime = form.findField('signEndTime').getValue(),
					billStartTime = form.findField('billStartTime').getValue(),// 得到开始时间
					billEndTime = form.findField('billEndTime').getValue(),
					instockStartTime = form.findField('instockStartTime').getValue(),// 得到开始时间
					instockEndtTime = form.findField('instockEndTime').getValue(), // 得到结束时间
					waybillNo = form.findField('waybillNo').getValue();// 运单号
				if (Ext.isEmpty(waybillNo)) {
					// 运单号为空时，日期必须输入
					if(Ext.isEmpty(billStartTime) && Ext.isEmpty(arriveStartTime) &&
						Ext.isEmpty(instockStartTime) && Ext.isEmpty(signStartTime)) {
						// 结束时间要在开始时间7天以内
						Ext.ux.Toast.msg('警告', '必须输入任意一组时间区间', 'error', 2000);
						return;
					}
				}
				if ((Ext.isEmpty(billStartTime) && !Ext.isEmpty(billEndTime)) || (!Ext.isEmpty(billStartTime) && Ext.isEmpty(billEndTime))) {
					Ext.ux.Toast.msg('警告', '开单起止时间必须都输入！',
						'error', 2000);
					return;
				}
				if (!Ext.isEmpty(billStartTime) && !Ext.isEmpty(billEndTime)) {
					var billStartDate = new Date(Date.parse(billStartTime
						.replace(/-/g, "/"))); // 解析开始时间
					var billEndDate = new Date(Date.parse(billEndTime.replace(
						/-/g, "/"))); // 解析结束时间

					if (billStartDate < sign.queryReturnBill.getTargetDate(
							billEndDate, -6)) {
						// 结束时间要在开始时间7天以内
						Ext.ux.Toast.msg('警告', '结束时间要在开始时间7天以内',
							'error', 2000);
						return;
					}
				}
				if ((Ext.isEmpty(arriveStartTime) && !Ext.isEmpty(arriveEndTime)) || (!Ext.isEmpty(arriveStartTime) &&Ext.isEmpty(arriveEndTime))) {
					// 结束时间要在开始时间7天以内
					Ext.ux.Toast.msg('警告', '到达起止时间必须都输入！',
						'error', 2000);
					return;
				}
				if(!Ext.isEmpty(arriveStartTime) && !Ext.isEmpty(arriveEndTime)){
					var arriveStartDate = new Date(Date.parse(arriveStartTime
						.replace(/-/g, "/"))); // 解析开始时间
					var arriveEndDate = new Date(Date.parse(arriveEndTime.replace(
						/-/g, "/"))); // 解析结束时间

					if (arriveStartDate < sign.queryReturnBill.getTargetDate(
							arriveEndDate, -6)) {
						// 结束时间要在开始时间7天以内
						Ext.ux.Toast.msg('警告', '结束时间要在开始时间7天以内',
							'error', 2000);
						return;
					}
				}
				if(!Ext.isEmpty(arriveEndTime) && Ext.isEmpty(billStartTime)){//如果到达时间不为空，开单时间也不能为空
					Ext.ux.Toast.msg('警告', '到达时间不为空，开单时间也必须都输入！',
						'error', 2000);
					return;
				}
				if ((Ext.isEmpty(signStartTime) && !Ext.isEmpty(signEndTime)) || (!Ext.isEmpty(signStartTime) && Ext.isEmpty(signEndTime))) {
					Ext.ux.Toast.msg('警告', '签收起止时间必须都输入！',
						'error', 2000);
					return;
				}
				if (!Ext.isEmpty(signStartTime) && !Ext.isEmpty(signEndTime)) {
					var signStartDate = new Date(Date.parse(signStartTime
						.replace(/-/g, "/"))); // 解析开始时间
					var signEndDate = new Date(Date.parse(signEndTime.replace(
						/-/g, "/"))); // 解析结束时间

					if (signStartDate < sign.queryReturnBill.getTargetDate(
							signEndDate, -6)) {
						// 结束时间要在开始时间7天以内
						Ext.ux.Toast.msg('警告', '结束时间要在开始时间7天以内',
							'error', 2000);
						return;
					}
				}

				if ((Ext.isEmpty(instockStartTime) && !Ext.isEmpty(instockEndtTime)) || (!Ext.isEmpty(instockStartTime) && Ext.isEmpty(instockEndtTime))) {
					Ext.ux.Toast.msg('警告', '入库起止时间必须都输入！',
						'error', 2000);
					return;
				}
				if (!Ext.isEmpty(instockStartTime) && !Ext.isEmpty(instockEndtTime)) {
					var instockStartDate = new Date(Date.parse(instockStartTime
						.replace(/-/g, "/"))); // 解析开始时间
					var instockEndDate = new Date(Date.parse(instockEndtTime.replace(
						/-/g, "/"))); // 解析结束时间

					if (instockStartDate < sign.queryReturnBill.getTargetDate(
							instockEndDate, -6)) {
						// 结束时间要在开始时间7天以内
						Ext.ux.Toast.msg('警告', '结束时间要在开始时间7天以内',
							'error', 2000);
						return;
					}
				}

				if(form.isValid()){
					sign.queryReturnBill.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

//// 开单时间查询
//Ext.define('Foss.QueryReturnBillInfo.Form.QueryWaybillForm', {
//	extend : 'Ext.form.Panel',
//	defaults : {
//		margin : '5 10 5 10',
//		labelWidth : 100
//	},
//	defaultType : 'textfield',
//	layout : 'column',
//	items : [{
//				name : 'waybillNo', // 运单号
//				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.waybillNo'), //运单号,// 字段标题
//				regex : /^[0-9]{8,9}$/i,
//				regexText : '输入8-9位纯数字单号',
//				columnWidth : 0.25
//			}, {
//				name : 'type', // 返单类型
//				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillType'), //返单类型,// 字段标题
//				columnWidth : 0.25,
//				xtype : 'combobox',
//				displayField : 'valueName',
//				valueField : 'valueCode',
//				queryMode : 'local',
//				triggerAction : 'all',
//				editable : true,
//				store : sign.queryReturnBill.returnBillTypeStore1,
//				listeners : {
//					'select' : function(combo, records, eOpts) {// 下拉以后没有任何级联操作
//																// 所以暂留
//					},
//					'afterrender' : function(combo, records, eOpts) { // 页面上有默认值
//																		// 使用该默认值
//						combo.setValue('');
//						combo.setRawValue('全部')
//					}
//				}
//			}, {
//				xtype : 'dynamicorgcombselector', // 出发部门 这里应该使用公共选择器
//				columnWidth : 0.25,
//				name : 'fromDepartmentCode',
//				id : 'fromDepartmentCode2',// 关联的名称
//				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.fromDepartment') //出发部门// 字段标题
//			}, {
//				xtype : 'hidden',
//				name : 'signOrWaybillType',// 查询类型0 表示签收时间查询 1表示开单时间查询
//				value : '1'
//			}, {
//				xtype : 'combobox',
//				displayField : 'name',
//				valueField : 'code',
//				queryMode : 'local',
//				triggerAction : 'all',
//				editable : true,
//				name : 'status',// 返单状态
//				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillStatus'), //返单状态,// 字段标题
//				allowBlank : false,
//				columnWidth : 0.25,
//				store : Ext
//						.create(
//								'Foss.QueryReturnBillInfo.Store.QueryReturnBillTypeStore',// 下拉匡内容
//								{
//									data : {
//										'items' : [{
//													'code' : 'NONE_RETURN_BILL',
//													'name' : '未返单'
//												},// 未返单
//												{
//													'code' : 'ALREADY_RETURN_BILL',
//													'name' : '已返单'
//												}// 已返单
//										]
//									}
//								}),
//				listeners : {
//					'select' : function(combo, records, eOpts) {// 下拉以后没有任何级联操作
//																// 所以暂留
//
//					},
//					'afterrender' : function(combo, records, eOpts) {
//						// 页面上有默认值 使用该默认值
//						combo.setValue('NONE_RETURN_BILL');
//						combo.setRawValue('未返单');
//					}
//				}
//			}, {
//				xtype : 'rangeDateField',
//				fieldId : 'FOSS_Time_Id2',
//				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.openBillTime'), //开单时间,// 开单时间
//				allowFromBlank : true,
//				allowToBlank : true,
//				editable : false,
//				disallowBlank: true,
//				dateType : 'datetimefield_date97',
//				fromName : 'startTime',
//				toName : 'endTime',
//				fromValue : Ext.Date.format(sign.queryReturnBill.getTargetDate(
//								new Date(), -1), 'Y-m-d H:i:s'),
//				toValue : Ext.Date
//						.format(sign.queryReturnBill.getTargetDateEnd(
//										new Date(), 0), 'Y-m-d H:i:s'),
//
//				format : 'Y-m-d H:i:s',
//				columnWidth : .5
//			}, {
//				border : 1,
//				xtype : 'container',
//				columnWidth : 1,
//				defaultType : 'button',
//				layout : 'column',
//				items : [{
//					text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.reset'), //重置,
//					columnWidth : .08,
//					handler : function() {
//						sign.queryReturnBill.queryWaybillForm.getForm().reset();// 重置表单内容
//						var form = sign.queryReturnBill.queryWaybillForm
//								.getForm();// 得到输入表单
//
//						form.findField('status').setValue('NONE_RETURN_BILL');// 将返单状态设置为默认值
//						form.findField('status').setRawValue('未返单');// 将返单状态设置为默认值
//
//						form.findField('type').setValue('');// 将返单状态设置为默认值设置为默认值
//						form.findField('type').setRawValue('全部');// 将返单状态设置为默认值设置为默认值
//
//						form.findField('startTime').setValue(Ext.Date.format(
//								sign.queryReturnBill.getTargetDate(new Date(),
//										-1), 'Y-m-d H:i:s'));
//						form.findField('endTime').setValue(Ext.Date.format(
//								sign.queryReturnBill.getTargetDateEnd(
//										new Date(), 0), 'Y-m-d H:i:s'));
//
//					}
//				}, {
//					xtype : 'container',
//					border : false,
//					columnWidth : .84,
//					html : '&nbsp;'
//				}, {
//					text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.search'), //查询,// 查询
//					cls : 'yellow_button',
//					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
//						seconds: 3
//					}),
//					columnWidth : .08,
//					handler : function() {
//						// 查询条件是否合法（非空等相关约束）
//						var form = sign.queryReturnBill.queryWaybillForm
//								.getForm();
//
//						if (form.isValid()) {
//
//							var startTime = form.findField('startTime')
//									.getValue();// 得到开始时间
//							var endTime = form.findField('endTime').getValue(); // 得到结束时间
//							if (startTime != '' && endTime != '') {
//								var startDate = new Date(Date.parse(startTime
//										.replace(/-/g, "/"))); // 解析开始时间
//								var endDate = new Date(Date.parse(endTime
//										.replace(/-/g, "/"))); // 解析结束时间
//
//								if (startDate < sign.queryReturnBill
//										.getTargetDate(endDate, -6)) {
//
//									// 结束时间要在开始时间7天以内
//									Ext.ux.Toast.msg('警告', '结束时间要在开始时间7天以内',
//											'error', 2000);
//
//									return;
//								}
//
//							}
//							if(form.isValid()){
//								sign.queryReturnBill.pagingBar.moveFirst();
//							}
//						}
//
//					}
//				}]
//			}]
//});

// 签单返单信息
Ext.define('Foss.QueryReturnBillInfo.QueryReturnBillInfoGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_QueryReturnBillInfo_QueryReturnBillInfoGrid_Id',// id
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillDetaill'), //返单明细,
	collapsible : true, // 可以展开
	animCollapse : true,
	emptyText : "查询结果为空",
	store : null,
	viewConfig : {
		// 显示重复样式，不用隔行显示
		stripeRows : false,
		getRowClass : function(record, rowIndex, p, ds) {
			// 短信如果失败
			if (record.get('issendSms') == 'FAILURE') {
				return 'failProcess_mark_color';// 变成灰色
				// 短信如果成功
			} else if (record.get('issendSms') == 'SUCCESS') {
				return 'passProcess_mark_color';// 变成绿色
			}
		}
	},
	selModel:Ext.create('Ext.selection.CheckboxModel',{//268377 表头的单选框，每行头都会生成单选框
		listeners: {
			deselect: function(model,record,index) {//取消选中时产生的事件

			},
			select: function(model,record,index) {

			}
		}
	}),
	// 定义表格列信息
	columns : [{
		xtype : 'actioncolumn',
		width : 100,
		align : 'center',
		items : [{
			id:'queryReturnBill_operate_id',
			header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.operate'), //操作,
			iconCls : 'deppon_icons_edit',
			getClass : function(value, metadata, record, rowIndex, colIndex,
								store) {
				// 返单状态
				// if(record.get('returnbillStatus') =='已返单'){
				// return 'deppon_icons_edit_hide';//不能编辑
				// }else {

				// }
				if (record.get('orgDiff') == 'N') { //选择部门为到达部门

					Ext.getCmp("queryReturnBill_confirmButton").setDisabled(true);//隐藏确认按钮
					return 'deppon_icons_edit'; //返回显示触笔按钮样式
				} else {//选择本部门出发
					Ext.getCmp("queryReturnBill_confirmButton").setDisabled(true);//隐藏确认按钮
					if(record.get('returnbillStatus') == '已返单'){//如果已返单
						Ext.getCmp("queryReturnBill_confirmButton").setDisabled(false);//显示确认按钮
					}
					return 'deppon_icons_edit_hidden';//返回隐藏触笔按钮样式
				}

			},
			xtype : 'button',
			handler : function(grid, rowIndex, colIndex) {
				var selection = grid.getStore().getAt(rowIndex);
				var ids = selection.get("id");// 得到id
				// 通过id调用ajax获得明细
				Ext.Ajax.request({
					url : sign.realPath('queryBillProcessById.action'),// 老的url格式：'../sign/queryBillProcessById.action',
					params : {
						'vo.searchReturnBillProcessDto.ids' : ids
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);// ajax
						// response
						sign.queryReturnBill.initReturnBillForm(result);// 初始化明细表格
					}
				});

			}
		}, {
			iconCls : 'deppon_icons_notice',// 短信通知
			xtype : 'button',
			hidden:!sign.queryReturnBill.isPermission('returnbillprocessindex/returnbillprocessindexsmsbutton'),
			getClass : function(value, metadata, record, rowIndex, colIndex,
								store) {

				// var statusRs
				// =FossDataDictionary.rendererDisplayToSubmit(record.get('returnbillType'),
				// sign.queryReturnBill.RETURN_BILL_TYPE);
				return 'deppon_icons_notice';// 显示短信通知
				/*
				 * if(record.get('returnbillStatus') =='已返单'){ return
				 * 'deppon_icons_notice_hidden';//不显示短信通知 }else {
				 * //扫描件件返回的运单，通知按钮“ ”不显示，已返单运单，“ ”“ ”都不显示 if(statusRs ==
				 * 'SCANNED'){ return 'deppon_icons_notice_hidden';//不显示短信通知
				 * }else{ return 'deppon_icons_notice';//显示短信通知 } }
				 */
			},

			handler : function(grid, rowIndex, colIndex) {
				var selection = grid.getStore().getAt(rowIndex);
				var ids = selection.get("id");// 得到id
				// 通过id调用ajax短信通知
				Ext.Ajax.request({
					url : sign
						.realPath('sendBillProcessSmsById.action'),
					// '../sign/sendBillProcessSmsById.action',
					params : {
						'vo.searchReturnBillProcessDto.ids' : ids
					},

					success : function(response) {

						var result = Ext.decode(response.responseText);// ajax
						// response
						var successResult = result.vo.resultDto.code;
						var errormsg = result.vo.resultDto.msg;
						if (successResult == "2") {// 不通知
						} else if (successResult == "1") {// 短信通知成功
							Ext.ux.Toast.msg('提示', '短信通知已发送!', 'ok',
								1000);
						} else {// 短信通知失败
							Ext.ux.Toast.msg('短信通知失败！', errormsg,
								'error', 2000);
						}
					},
					exception : function(response) {// 短信通知失败
						var json = Ext.decode(response.responseTefxt);
						Ext.ux.Toast.msg('短信通知失败！', json.message,
							'error', 2000);
					}
				});

			}

		}]
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.waybillNo'), //运单号,
		// 关联model中的字段名
		dataIndex : 'waybillNo',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.fromDepartment'), //运单号,
		// 关联model中的字段名
		dataIndex : 'receiveOrgName',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillTime'), //返单时间,
		// 关联model中的字段名
		dataIndex : 'returnbillTime',
		width : 120,
		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillType'), //返单类型,
		// 关联model中的字段名
		dataIndex : 'returnbillType',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.handler'), //处理人,
		// 关联model中的字段名
		dataIndex : 'handler',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillStatus'), //返单状态,
		// 关联model中的字段名
		dataIndex : 'returnbillStatus',

		width : 100
	},{
		header:'返单确认',//返单确认
		dataIndex:'returnbillConfirm',
		width:100
	},{
		header:'确认时间',//确认时间
		dataIndex:'confirmTime',
		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			} else {
				return null;
			}
		},
		width:100
	},{
		header:'确认人',//确认人
		dataIndex:'confirmHandler',
		width:100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.verifyTime1'), //核实时间,
		// 关联model中的字段名
		dataIndex : 'verifyTime',

		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			} else {
				return null;
			}
		},
		width : 120
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.feedbackInfo'), //核实反馈信息,
		// 关联model中的字段名
		dataIndex : 'feedbackInfo',
		width : 200
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.deliveryCustomerName'), //发货人,
		// 关联model中的字段名
		dataIndex : 'deliveryCustomerContact',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.deliveryCustomerMobilephone'), //发货人手机,
		// 关联model中的字段名
		dataIndex : 'deliveryCustomerMobilephone',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.deliveryCustomerTel'), //发货人电话
		// 关联model中的字段名
		dataIndex : 'deliveryCustomerTel',
		width : 100
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.expressCompany'), //快递公司,
		// 关联model中的字段名
		dataIndex : 'expressCompany',
		width : 150
	}, {
		// 字段标题
		header : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.expressNo'), //快递单号,
		// 关联model中的字段名
		dataIndex : 'expressNo',
		width : 100
	}],
	constructor : function(config) {// 初始化分页组件
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
			.create('Foss.QueryReturnBillInfo.Store.QueryReturnBillInfoStore');
		// 自定义分页控件
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		me.tbar = ['->',{
			xtype : 'button',
			text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.export'),
			handler : function() {
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				var result = this.up('grid').getStore();
				//若异常信息不为空
				if(result.getCount()!=0){
					var queryParams = sign.queryReturnBill.querySignForm.getValues(),
						params = {
							'vo.searchReturnBillProcessDto.waybillNo' : queryParams.waybillNo,// 运单号
							'vo.searchReturnBillProcessDto.type' : queryParams.type,// 返单类型
							'vo.searchReturnBillProcessDto.fromDepartmentCode' : queryParams.fromDepartmentCode,// 出发部门
							'vo.searchReturnBillProcessDto.status' : queryParams.status,// 返单状态
							'vo.searchReturnBillProcessDto.signOrWaybillType' : '0',// 签收时间查询
							'vo.searchReturnBillProcessDto.signStartTime' : queryParams.signStartTime,// 签收开始时间
							'vo.searchReturnBillProcessDto.signEndTime' : queryParams.signEndTime,// 签收end时间
							'vo.searchReturnBillProcessDto.billStartTime' : queryParams.billStartTime,// 开单开始时间
							'vo.searchReturnBillProcessDto.billEndTime' : queryParams.billEndTime,// 开单end时间
							'vo.searchReturnBillProcessDto.arriveStartTime' : queryParams.arriveStartTime,// 到达开始时间
							'vo.searchReturnBillProcessDto.arriveEndTime' : queryParams.arriveEndTime,// 到达end时间
							'vo.searchReturnBillProcessDto.instockStartTime' : queryParams.instockStartTime,// 入库开始时间
							'vo.searchReturnBillProcessDto.instockEndTime' : queryParams.instockEndTime,// 入库end时间
							'vo.searchReturnBillProcessDto.orgDiff' : queryParams.orgDiff// 部门类型
						};
					Ext.Ajax.request({
						url:sign.realPath('exportReturnBill.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : params,
						isUpload: true
					});
				}else{
					//或者提示不能导出
					Ext.ux.Toast.msg('提示','数据为空不能导出', 'error', 3000);
				}
			}
		}]
		sign.queryReturnBill.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	buttons: [{
		id:'queryReturnBill_confirmButton', //确认  yanling 268377
		text: '确认',
		disabled:true,
		handler: function(){
			var confirmModel = Ext.getCmp('Foss_QueryReturnBillInfo_QueryReturnBillInfoGrid_Id').
				getSelectionModel();
			confirmData = confirmModel.getSelection();
			var ids = new Array();
			//判断是否有选中信息
			if(confirmData!=null && confirmData.length >0){
				//循环选中信息
				for(var i = 0; i < confirmData.length; i++) {
					var id = confirmData[i].data.id;//获取id
					ids.push(id);//将获取的id放入数组中
				}
			}else{
				Ext.ux.Toast.msg('提示','只是选择一条确认信息','ok',3000);
				return;
			}

			Ext.Msg.confirm( '提示', '确定提交信息？', function(btn,text){
				if(btn == 'yes') {
					Ext.Ajax.request({
						url:sign.realPath('confirmReturnBill.action'),
						method: 'POST',
						jsonData: {'ids':ids},
						success: function(response){
							var json = Ext.decode(response.responseText);
							//如果确认成功 进入移除选中操作 否则不做任何操作
							if(json.message == '确认成功'){
								//清除已确认返单信息。
								for(var i = 0; i < ids.length; i++) {
									//获取选中返单信息下表
									var index = confirmModel.getStore().find('id',ids[i]);
									//通过获取的下班移除相应选中信息
									confirmModel.getStore().removeAt(index);
								}
							}
							Ext.ux.Toast.msg('提示',json.message,'ok',3000);
						},exception: function(response){//异常处理
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg('提示', json.message, 'error', 3000);
						}

					});
				}
			});
		}
	}]

});

// 创建一个查询货量store
Ext.define('Foss.QueryReturnBillInfo.Store.QueryReturnBillInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.QueryReturnBill.Model.ReturnBillModel',
	// 默认每页数据大小
	pageSize : 10,
	// 是否自动查询
	autoLoad : false,
	// 定义一个代理对象
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		// 提交方式
		actionMethods : 'POST',
		url : sign.realPath('queryReturnBill.action'),
		// '../sign/queryReturnBill.action',
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.rtSearchReturnBillProcessList',
			// 返回总数
			totalProperty : 'totalCount'
		}
	},
	filterOnLoad:true,
	filters:[{
		property:'returnbillConfirm',
		value:'未确认'
	}],
	// 事件监听
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
//			// 执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
//			if (sign.queryReturnBill.queryPanel.getActiveTab().title == sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.signDateSearch')) {
//				var queryParams = sign.queryReturnBill.querySignForm
//						.getValues();// 获得表单数据
//				Ext.apply(operation, {
//					params : {
//						'vo.searchReturnBillProcessDto.waybillNo' : queryParams.waybillNo,// 运单号
//						'vo.searchReturnBillProcessDto.type' : queryParams.type,// 返单类型
//						'vo.searchReturnBillProcessDto.fromDepartmentCode' : queryParams.fromDepartmentCode,// 出发部门
//						'vo.searchReturnBillProcessDto.status' : queryParams.status,// 返单状态
//						'vo.searchReturnBillProcessDto.signOrWaybillType' : '0',// 签收时间查询
//						'vo.searchReturnBillProcessDto.startTime' : queryParams.startTime,// 开始时间
//						'vo.searchReturnBillProcessDto.endTime' : queryParams.endTime
//						// end时间
//					}
//				});
//			} else {
//				var queryParams = sign.queryReturnBill.queryWaybillForm
//						.getValues();// 获得表单数据
//				Ext.apply(operation, {
//					params : {
//						'vo.searchReturnBillProcessDto.waybillNo' : queryParams.waybillNo,// 运单号
//						'vo.searchReturnBillProcessDto.type' : queryParams.type,// 返单类型
//						'vo.searchReturnBillProcessDto.fromDepartmentCode' : queryParams.fromDepartmentCode,// 出发部门
//						'vo.searchReturnBillProcessDto.status' : queryParams.status,// 返单状态
//						'vo.searchReturnBillProcessDto.signOrWaybillType' : '1',// 开单时间查询
//						'vo.searchReturnBillProcessDto.startTime' : queryParams.startTime,// 开始时间
//						'vo.searchReturnBillProcessDto.endTime' : queryParams.endTime
//						// end时间
//					}
//				});
//			}


			var queryParams = sign.queryReturnBill.querySignForm.getValues();// 获得表单数据

			if(!queryParams.isEmpty){//    Y 本部门出发   N 到达  NONE_RETURN_BILL
				if(queryParams.orgDiff == 'Y' && queryParams.status == 'ALREADY_RETURN_BILL'){//如果是本部分出发且一返单
					//开启过滤
					s.filterOnLoad=true;
					Ext.getCmp("queryReturnBill_confirmButton").setVisible(true);
				}else{
					//关闭过滤
					s.filterOnLoad=false;
					Ext.getCmp("queryReturnBill_confirmButton").setVisible(false);
				}
			}

			Ext.apply(operation, {
				params : {
					'vo.searchReturnBillProcessDto.waybillNo' : queryParams.waybillNo,// 运单号
					'vo.searchReturnBillProcessDto.type' : queryParams.type,// 返单类型
					'vo.searchReturnBillProcessDto.fromDepartmentCode' : queryParams.fromDepartmentCode,// 出发部门
					'vo.searchReturnBillProcessDto.status' : queryParams.status,// 返单状态
					'vo.searchReturnBillProcessDto.signOrWaybillType' : '0',// 签收时间查询
					'vo.searchReturnBillProcessDto.signStartTime' : queryParams.signStartTime,// 签收开始时间
					'vo.searchReturnBillProcessDto.signEndTime' : queryParams.signEndTime,// 签收end时间
					'vo.searchReturnBillProcessDto.billStartTime' : queryParams.billStartTime,// 开单开始时间
					'vo.searchReturnBillProcessDto.billEndTime' : queryParams.billEndTime,// 开单end时间
					'vo.searchReturnBillProcessDto.arriveStartTime' : queryParams.arriveStartTime,// 到达开始时间
					'vo.searchReturnBillProcessDto.arriveEndTime' : queryParams.arriveEndTime,// 到达end时间
					'vo.searchReturnBillProcessDto.instockStartTime' : queryParams.instockStartTime,// 入库开始时间
					'vo.searchReturnBillProcessDto.instockEndTime' : queryParams.instockEndTime,// 入库end时间
					'vo.searchReturnBillProcessDto.orgDiff' : queryParams.orgDiff// 部门类型
				}
			});
		}
	}
});

/**
 * extjs初始化
 */
Ext.onReady(function() {
	Ext.QuickTips.init();//初始化

	// 处理未返单运单panel
	sign.queryReturnBill.initReturnBillForm = function(data) {
		var showPanel = sign.queryReturnBill
			.initReturnBillPanel(data.vo.rtSearchReturnBillProcessDto);
		var showWindow = Ext.create('Ext.window.Window', {
			id : 'Foss_sign_window_returnBill_Id',
			width : 900,
			resizable : false,
			modal : true,
			items : [showPanel]
		});
		if(data.vo.rtSearchReturnBillProcessDto.returnbillStatus !='NONE_RETURN_BILL'){
			showPanel.query('button')[0].setDisabled(true);
			var fields = showPanel.getForm().getFields();
			fields.each(function(field){
				field.setDisabled(true);
			});
		}else {
			showPanel.query('button')[0].setDisabled(false);
			var fields = showPanel.getForm().getFields();
			fields.each(function(field){
				field.setDisabled(false);
			});
		}
		// 数据模型
		var returnBillUpdateModel = Ext.ModelManager.create(
			data.vo.rtSearchReturnBillProcessDto,
			'Foss.QueryReturnBill.Model.ReturnBillUpdateModel');

		// 读取数据
		showPanel.loadRecord(returnBillUpdateModel);

		// 窗体展现
		showWindow.show();

	}

	// 处理未返单运单数据
	sign.queryReturnBill.initReturnBillPanel = function(data) {

		return Ext.create('Ext.form.Panel', {

			id : 'Foss_sign_ReturnBill_ID',// id
			title : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.prossNotReturnbill'), //处理未返单运单,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 100
			},
			frame:true,
			//收缩
			collapsible: true,
			//动画收缩
			animCollapse: true,
			bodyCls: 'autoHeight',
			cls: 'autoHeight',
			defaultType : 'textfield',
			layout : 'column',
			items : [{
				name : 'id',// id
				xtype : 'hidden'

			}, {
				name : 'waybillNo',// 运单号
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.waybillNo'), //运单号,// 字段标题
				columnWidth : 1 / 3,
				readOnly : true
			}, {
				name : 'returnbillType',// 运单类型'
				// id:'sign-QueryPanel-returnbillType',//id
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.returnbillType1'), //运单类型,// 字段标题
				columnWidth : 1 / 3,
				readOnly : true
			}, {

				xtype : 'datetimefield_date97',// 核定时间
				format : 'Y-m-d H:i:s',
				id : 'sign-QueryPanel-verifyTimeEnd',// id 必须填写
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.verifyTime'), //核定时间, // 字段标题
				allowBlank : false,
				columnWidth : 1 / 3,
				name : 'verifyTime',
				time : true,
				dateConfig : {
					el : 'sign-QueryPanel-verifyTimeEnd-inputEl',
					dateFmt : 'yyyy-MM-dd HH:ii:ss'
				}
			}, {
				name : 'handler',// 处理人
				maxLength:100,
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.handler'), //处理人,// 字段标题
				allowBlank : false,
				columnWidth : 1 / 3
			}, {
				name : 'expressCompany',// 发货快递公司
				maxLength:100,
				id : 'sign-QueryPanel-expressCompany',// id
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.expressCompany1'), //发货快递公司,// 字段标题
				columnWidth : 1 / 3
			}, {
				name : 'expressNo',// 发货单号
				id : 'sign-QueryPanel-expressNo',// id
				maxLength:16,
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.expressNo1'), //发货单号,// 字段标题
				columnWidth : 1 / 3
			}, {
				name : 'feedbackInfo',// 反馈信息
				xtype : 'textareafield',
				grow : true,
				maxLength:500,
				fieldLabel : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.feedbackInfo1'), //反馈信息,// 字段标题
				columnWidth : 1
			}, {
				xtype : 'container',
				border : false,
				columnWidth : .8,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				cls : 'yellow_button',
				align : 'right',
				columnWidth : 0.1,
				text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.confirm'), //确认,// 字段标题
				hidden:!sign.queryReturnBill.isPermission('returnbillprocessindex/returnbillprocessindexconfirmbutton'),
				// 3秒内不能重新点plugin
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds : 3
					// 3秒内不能重新点
				}),
				handler : function() {

					// 得到form
					var form = this.up('form').getForm();

					if (form.isValid()) {
						var values = form.getValues();

						// 返单类型是原件返回时，外发快递公司为空，则系统提示异常信息
						var returnbillTypeValue = form
							.findField('returnbillType').getValue();
						var statusRs = FossDataDictionary
							.rendererDisplayToSubmit(
							returnbillTypeValue,
							sign.queryReturnBill.RETURN_BILL_TYPE);

						if (statusRs == 'ORIGINAL') {// 原件返回
							var company = form.findField('expressCompany')
								.getValue();//
							// 快递公司不能为空
							if (sign.queryReturnBill.trim(company) == '') {
								Ext.ux.Toast.msg('警告',
									'返单类型是原件返回时 ，快递公司不能为空，请录入',
									'error', 2000);
								return;
							}

							var expressNo = form.findField('expressNo')
								.getValue();
							// 快递单号不能为空
							if (sign.queryReturnBill.trim(expressNo) == '') {

								Ext.ux.Toast.msg('警告',
									'返单类型是原件返回时 ，快递单号不能为空，请录入！',
									'error', 2000);
								return;
							}
						}
						// 组合数据model
						var array = {
							'vo' : {
								'searchReturnBillProcessDto' : {
									'returnBillProcessEntity' : values
								}
							}
						};
						Ext.Ajax.request({// ajax请求
							url : sign
								.realPath('updateReturnBillProcess.action'),// 老的url格式：'../writeoff/queryStatement.action'

							jsonData : array,
							success : function(response) {
								var result = Ext.decode(response.responseText);// ajax
								var successResult = result.vo.resultDto.code;
								var errormsg = result.vo.resultDto.msg;
								if (successResult == "2") {// 不通知
									//'提交成功
									Ext.ux.Toast.msg('提示', '提交成功！', 'ok', 1000);
								} else if (successResult == "1") {// 短信通知成功
									Ext.ux.Toast.msg('提示', '提交成功！短信通知已发送!', 'ok',1000);
								} else {// 短信通知失败
									Ext.ux.Toast.msg('提示', '提交成功！' + errormsg,'error', 2000);
								}
								sign.queryReturnBill.pagingBar.moveFirst();
								Ext.getCmp('Foss_sign_window_returnBill_Id').close();
							},
							exception : function(response) {
								//保存失败
								var json = Ext
									.decode(response.responseText);
								Ext.ux.Toast.msg('保存失败', json.message,
									'error', 2000);
							}
						});
					}else {
						Ext.ux.Toast.msg('提示', '部分信息未填写完整！', 'error',1000);
					}
				}
			}, {
				xtype : 'button',
				cls : 'yellow_button',
				align : 'center',
				columnWidth : 0.1,
				text : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.cancel'), //取消,

				handler : function() {
					Ext.getCmp('Foss_sign_window_returnBill_Id').close();
				}
			}

			]

		});
	}

	//查询querySignForm
	sign.queryReturnBill.querySignForm = Ext
		.create('Foss.QueryReturnBillInfo.Form.QuerySignForm');
//		//查询queryWaybillForm
//		sign.queryReturnBill.queryWaybillForm = Ext
//				.create('Foss.QueryReturnBillInfo.Form.QueryWaybillForm');
	//查询结果
	sign.queryReturnBill.queryResult = Ext
		.create('Foss.QueryReturnBillInfo.QueryReturnBillInfoGrid');

//		//查询panel
//		sign.queryReturnBill.queryPanel = Ext.create('Ext.tab.Panel', {
//					bodyCls : 'autoHeight',
//					cls : 'innerTabPanel',
//					height : 200,
//					items : [{
//								title : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.signDateSearch'), //签收时间查询,//签收时间查询tab
//								tabConfig : {
//									width : 100
//									//宽度
//								},
//								layout : 'fit',
//								items : [sign.queryReturnBill.querySignForm]
//							}, {
//								title : sign.queryReturnBill.i18n('pkp.sign.queryReturnBill.openWaybillSearch'), //开单时间查询,//开单时间查询tab
//								tabConfig : {
//									width : 100
//									//宽度
//								},
//								layout : 'fit',
//								items : [sign.queryReturnBill.queryWaybillForm]
//							}],
//					listeners : {
//						//切换tab的事件
//						'tabchange' : function(tabPanel, newCard, oldCard, eOpts) {
//							sign.queryReturnBill.queryResult.store.removeAll();//画面切换的时候清空查询纪录
//							sign.queryReturnBill.pagingBar.onLoad();//画面切换的时候清空查询纪录
//						}
//					}
//				});
	Ext.create('Ext.panel.Panel', {

		id : 'T_sign-returnBillProcessIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [sign.queryReturnBill.querySignForm,//查询条件panel
			sign.queryReturnBill.queryResult],//查询结果panel
		renderTo : 'T_sign-returnBillProcessIndex-body'
	});
});
