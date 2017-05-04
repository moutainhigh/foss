/*	
 * @author:ibm-wangfei Build date: 2013-1-29	
 */	
	
/**	
 * @param date--比较日期 day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前	
 * @return 返回目标日期	
 */	
waybill.waybillRfcAccount.getTargetDate = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 = new Date(t + day * DyMilli);	
	t2.setHours(0);	
	t2.setMinutes(0);	
	t2.setSeconds(0);	
	t2.setMilliseconds(0);	
	return t2;	
};	
	
waybill.waybillRfcAccount.getTargetDate1 = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 = new Date(t + day * DyMilli);	
	t2.setHours(23);	
	t2.setMinutes(59);	
	t2.setSeconds(59);	
	t2.setMilliseconds(0);	
	return t2;	
};	
	
// model	
Ext.define('Foss.waybill.waybillRfcAccount.WaybillRfcAccountModel', {	
	extend : 'Ext.data.Model',	
	fields : [	
	  {name : 'id', type : 'string'}, 	
	  {name : 'contactHandy', type : 'string'}, 	
	  {name : 'unifieldCode', type : 'string'}, 	
	  {name : 'applyPerson', type : 'string'},	
	  {name : 'changeContent', type : 'string'}, /** 通知状态 */	
	  {name : 'contact', type : 'string'}, /** 通知状态 */	
	  {name : 'waybillNumber', type : 'string'},	
	  {name : 'processUserName', type : 'string'},	
	  {name : 'processOrgName', type : 'string'},	
	  {name : 'applyTime', convert:dateConvert },	
	  {name : 'processTime', convert:dateConvert },
	  { name: 'active',type:'string',	
		convert:function(value){	
			 if(value=='Y'){	
				 return waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.yes');//'是'
			 }else{	
				 return waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.no')//'否'	
			 }	
		  }	
	  }]	
});	
	
/*	
 * 定义：一个'机型'的查询数据模型'Store'交互后台	
 */	
Ext.define('Foss.waybill.waybillRfcAccount.WaybillRfcAccountStore', {	
	extend : 'Ext.data.Store',	
	//页面条数定义	
	pageSize : 10,	
	//绑定model	
	model : 'Foss.waybill.waybillRfcAccount.WaybillRfcAccountModel',	
	proxy : {	
		//以JSON的方式加载数据	
		type : 'ajax',	
		actionMethods : 'POST',	
		url : waybill.realPath('queryChangeOrderList.action'),	
		reader : {	
			type : 'json',	
			root : 'vo.waybillRfcForAccountServiceDto',	
			totalProperty : 'totalCount',	
			successProperty : 'success'	
		}	
	},	
	//构造函数	
	constructor : function (config) {	
		var me = this,	
		cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	},	
	//监听器	
	listeners : {	
		beforeload : function (store, operation, eOpts) {	
			var form = Ext.getCmp('Foss_waybill_waybillRfcAccount_SearchForm_ID').getForm();
			var startDate = form.getValues().startDate;	
			var endDate = form.getValues().endDate;	
			var result = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(startDate,'Y-m-d H:i:s');	
			if(result / (24 * 60 * 60 * 1000) >= 8){	
				Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.date.not.more.seven.days.apart'), 'error', 3000);//起止日期相隔不能超过7天！	
				return false;	
			}	
			if(!form.isValid())	
			{
				return false;
			}
			if (form != null) {	
				var queryParams = form.getValues();	
				Ext.apply(operation, {	
					params : {	
						'vo.waybillRfcForAccountServiceCondition.waybillNumber' : queryParams.waybillNumber,	
						'vo.waybillRfcForAccountServiceCondition.startDate' : queryParams.startDate,	
						'vo.waybillRfcForAccountServiceCondition.endDate' : queryParams.endDate	
					}	
				});	
			}	
		}	
	}	
});	
	
//查询条件	
Ext.define('Foss.waybill.waybillRfcAccount.SearchForm',{	
	extend:'Ext.form.Panel',	
	title: waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.select.condition'),	 // 查询条件
	frame:true,	
	id:'Foss_waybill_waybillRfcAccount_SearchForm_ID',	
	collapsible: true,	
	animCollapse: true,	
	defaults: {	
		margin: '5 10 5 10',	
		labelWidth: 100	
	},	
	defaultType: 'textfield',	
	layout: 'column',	
	items: [{	
		name:'waybillNumber',	
		fieldLabel:waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.no'),	 // 运单号
		vtype: 'waybill',	
		columnWidth: 0.33	
	},{	
		xtype: 'rangeDateField',	
		fieldLabel: waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.rfc.apply.time'),	 // 更改单申请时间
		dateType: 'datetimefield_date97',	
		fieldId: 'Foss_waybill_waybillRfcAccount_start_Id',	
		fromName: 'startDate',	
		toName: 'endDate',	
		disallowBlank:true,	
		editable:false,	
		fromValue: Ext.Date.format(waybill.waybillRfcAccount.getTargetDate(new Date(),0),'Y-m-d H:i:s'),	
		toValue: Ext.Date.format(waybill.waybillRfcAccount.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),	
		columnWidth: .66	
	},{	
		border: 1,	
		xtype:'container',	
		columnWidth:1,	
		defaultType:'button',	
		layout:'column',	
		items:[{	
			text:waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.reset'),	 // 重置
			columnWidth:.08,	
			handler:function(){	
				var form = this.up('form').getForm();	
				form.findField('waybillNumber').setValue('');	
				form.findField('startDate').setValue(Ext.Date.format(waybill.waybillRfcAccount.getTargetDate(new Date(),0),'Y-m-d H:i:s')),	
				form.findField('endDate').setValue(Ext.Date.format(waybill.waybillRfcAccount.getTargetDate1(new Date(),0),'Y-m-d H:i:s'))	
			}	
		},{	
			xtype: 'container',	
			border : false,	
			columnWidth:.84,	
			html: '&nbsp;'	
		},{	
			text:waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.select'),	 // 查询
			disabled:!waybill.waybillRfcAccount.isPermission('waybillrfcaccountindex/waybillrfcaccountindexquerybutton'),
			hidden:!waybill.waybillRfcAccount.isPermission('waybillrfcaccountindex/waybillrfcaccountindexquerybutton'),
			cls:'yellow_button',	
			columnWidth:.08,	
			handler:function(){	
				Ext.getCmp('Foss_waybill_grid_WaybillRfcAccountGrid_Id').getPagingToolbar().moveFirst();	
			}	
		}]	
	}],	
	constructor : function (config) {	
		var me = this,	
		cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
});	
	
Ext.define('Foss.waybill.waybillRfcAccount.WaybillRfcAccountGrid', {	
	extend : 'Ext.grid.Panel',	
	title : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.select.result'),	 // 搜索结果
	cls : 'autoHeight',	
	bodyCls : 'autoHeight',	
	frame : true,	
	emptyText: waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.select.result.empty'),	 // 查询结果为空
	id : 'Foss_waybill_grid_WaybillRfcAccountGrid_Id',	
	// 增加滚动条	
	autoScroll : false,	
	collapsible : true,	
	store : null,	
	selModel : Ext.create('Ext.selection.CheckboxModel'),	
	viewConfig : {	
		// 单元格可复制	
		enableTextSelection: true,	
		//显示重复样式，不用隔行显示	
		stripeRows : false	
	},	
	tbar : [{	
		xtype : 'button',	
		text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.accept'),	 // 同意
		disabled:!waybill.waybillRfcAccount.isPermission('waybillrfcaccountindex/waybillrfcaccountindexagreebutton'),
		hidden:!waybill.waybillRfcAccount.isPermission('waybillrfcaccountindex/waybillrfcaccountindexagreebutton'),
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {	
			seconds: 3	
		}),	
		handler : function() {	
			var ids = '', records = Ext.getCmp('Foss_waybill_grid_WaybillRfcAccountGrid_Id').getSelectionModel().getSelection();	
				
			for (var i = 0; i < records.length; i++) {	
				ids += records[i].get('id') + ',';	
			}	
			if (ids == '') {	
				Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.must.select.result'), 'error', 1000);	//'请选择记录。'
				return;	
			}	
			var newVo = {	
				'vo': {	
					'ids' : ids	
				}	
			}	
			Ext.Ajax.request({	
				url:waybill.realPath('updateWaybillRfcIds.action'),	
				jsonData: newVo,	
				success: function(response){	
					Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.rfc.apply.consent.successful'), 'ok', 2000);// '更改单申请同意成功'
					Ext.getCmp('Foss_waybill_grid_WaybillRfcAccountGrid_Id').getPagingToolbar().moveFirst();	
				},	
				exception: function(response) {	
	                var json = Ext.decode(response.responseText);	
	                Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), json.message, 'error', 2000);	
	            }	
			});	
		}	
	}],	
	columns : [{	
			xtype : 'actioncolumn',	
			width : 60,	
			text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.operator'),	 // 操作
			align : 'center',	
			items : [{	
					iconCls : 'deppon_icons_dispose',	
					tooltip : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.accept'),	 // 同意
					/**	
					 * '修改'响应事件	
					 * @param {Object} grid 当前表格	
					 * @param {Number} rowIndex 行索引	
					 * @param {Number} colIndex 列索引	
					 */	
					handler : function (grid, rowIndex, colIndex) {	
						var record = grid.getStore().getAt(rowIndex);	
						if (record.data.active == waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.yes')) {
							Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.rfc.apply.consent.succeed'), 'ok', 2000);//'更改单申请已同意！'
							return;
						}
						Ext.Ajax.request({	
							url : waybill.realPath('updateWaybillRfcIds.action'),	
							jsonData : {	
								'vo' : {	
									'ids' : record.data.id	
								}	
							},	
							//'同意'成功	
							success : function (response) {	
								Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.rfc.apply.consent.successful'), 'ok', 2000);//'更改单申请同意成功'	
								grid.up().getPagingToolbar().moveFirst();	
							},	
							//'同意'失败	
							exception : function (response) {	
								var json = Ext.decode(response.responseText);	
								Ext.ux.Toast.msg(waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.tip'), json.message, 'error', 2000);	
							}	
						});	
					}	
				}	
			]	
		}, 	
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.contact.handy'),width : 120,dataIndex : 'contactHandy', xtype : 'ellipsiscolumn'},	 // 联系人手机号
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.unifield.code'),width : 120,dataIndex : 'unifieldCode', xtype : 'ellipsiscolumn'},	 // 部门标杆编码
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.apply.person'),width : 65,dataIndex : 'applyPerson', xtype : 'ellipsiscolumn'},	 // 用户名
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.modify.'),flex : 1,dataIndex : 'changeContent', xtype : 'ellipsiscolumn'},	 // 修改内容
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.contact'),width : 65,dataIndex : 'contact'},	 // 联系人
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.waybill.no'),width : 80, dataIndex : 'waybillNumber'},	 // 运单号
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.apply.time'),width : 140, dataIndex : 'applyTime',	 // 申请时间
			renderer : function(value) {	
				if (value != null) {	
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
					return date;	
				} else {	
					return null;	
				}	
			} 	
		},	
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.is.accept'),width : 60,dataIndex : 'active'},	 // 是否同意
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.accept.time'),width : 140,dataIndex : 'processTime', 	 // 同意时间
			renderer : function(value) {	
				if (value != null) {	
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
					return date;	
				} else {	
					return null;	
				}	
			} 	
		},	
		{text : waybill.waybillRfcAccount.i18n('pkp.waybill.waybillRfcAccount.accept.user'),width : 65,dataIndex : 'processUserName'},	 // 同意人
		{dataIndex : 'id',hidden : true}	
	],	
	pagingToolbar : null,	
  	getPagingToolbar : function() {	
  		if (this.pagingToolbar == null) {	
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {	
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
  			});	
  		}	
  		return this.pagingToolbar;	
  	},	
  	constructor : function(config) {	
  		var me = this,	
			cfg = Ext.apply({}, config);	
		me.store = Ext.create('Foss.waybill.waybillRfcAccount.WaybillRfcAccountStore');	
		me.bbar = me.getPagingToolbar();	
		me.callParent(cfg);	
	}	
});	
	
/****************************************************  窗口初始化 *****************************************************/	
Ext.onReady(function () {	
	Ext.QuickTips.init();	
	/*	
	 * 创建查询表单'FORM'	
	 */	
	var queryForm = Ext.create('Foss.waybill.waybillRfcAccount.SearchForm');	
	/*	
	 * 创建查询结果列表结果窗口	
	 */	
	var queryResult = Ext.create('Foss.waybill.waybillRfcAccount.WaybillRfcAccountGrid');	
	/*	
	 * 执行页面的初始化布局	
	 */	
	Ext.create('Ext.panel.Panel', {	
		id : 'T_waybill-waybillRfcAccountIndex_content',	
		cls : 'panelContentNToolbar',	
		bodyCls : 'panelContentNToolbar-body',	
		layout : 'auto',	
		//获得查询表单'FORM'	
		getQueryForm : function () {	
			return queryForm;	
		},	
		//获得查询结果列表结果窗口	
		getWaybillRfcAccountGrid : function () {	
			return queryResult;	
		},	
		items : [	
			queryForm, //加入查询表单'FORM'	
			queryResult //加入结果列表	
		],	
		renderTo : 'T_waybill-waybillRfcAccountIndex-body'	
	});	
});	
