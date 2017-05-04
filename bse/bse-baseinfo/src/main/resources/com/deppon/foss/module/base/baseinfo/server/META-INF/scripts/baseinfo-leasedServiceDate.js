

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.leasedServiceDate.leasedServiceDateQueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedServiceDate.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_leasedServiceDate_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 190,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name:'orgCode',//部门
		xtype : 'commonmotorcadeselector',
		fieldLabel : '服务车队',
		allowBlank : true
		},{
			name:'vehicleNo',
			fieldLabel : '车牌号', 
			allowBlank : true,
			parentOrgCode : null
		},{
		name:'vehicleLengthCode',//车型
		xtype : 'commonvehicletypeselector',
		vehicleSort : 'ownership_leased',
		displayField : 'vehicleLengthCode',
		fieldLabel : '车型',
		allowBlank : true
		},{
		name:'leasedDriverName',
		fieldLabel : '司机姓名', 
		allowBlank : true,
		parentOrgCode : null
	},{
		name:'driverPhone',
		fieldLabel : '司机电话',
		allowBlank : true,
		parentOrgCode : null
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.leasedServiceDate.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateQueryButton'),
			hidden:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.leasedServiceDate.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateQueryButton'),
			hidden:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateQueryButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-leasedServiceDate_content').getLeasedServiceDateGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});

/*
 * 定义：一个"厢式车"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedServiceDate.leasedServiceDateModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'vehicleNo', //车牌号
			type : 'string'
		}, {
			name : 'vehicleLength', //车长
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}	
		}, {
			name : 'selfVolume', //净空
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}	
		}, {
			name : 'selfWeight', //自重
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}
		}, {
			name : 'deadLoad', //载重
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}	
		}, {
			name : 'driverPhone', //司机电话
			type : 'string'
		}, {
			name : 'leasedDriverName', //司机姓名
			type : 'string'
		}, {
			name : 'name', //车队名称
			type : 'string'
		}, {
			name : 'orgCode', //部门code
			type : 'string'
		}, {
			name : 'operatorName', //操作人
			type : 'string',
			convert:function(value){
				if(':'==value){
					return null;
				}else{
					return value;
				}
			}	
		}
	]
});

/*
 * 定义：一个"厢式车"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedServiceDate.leasedServiceDateStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedServiceDate.leasedServiceDateModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedServiceDateList.action'),
		reader : {
			type : 'json',
			root : 'leasedVehicleVo.leasedVehicleList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedServiceDate_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
						'leasedVehicleVo.leasedVehicle.vehicleLengthCode' : queryParams.vehicleLengthCode,
						'leasedVehicleVo.leasedVehicle.leasedDriverName' : queryParams.leasedDriverName,
						'leasedVehicleVo.leasedVehicle.driverPhone' : queryParams.driverPhone,
						'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_van', //"厢式车"数据字典常量
						'leasedVehicleVo.leasedVehicle.orgCode' : queryParams.orgCode
					}
				});
			}
		}
	}
});

Ext.define('Foss.baseinfo.leasedServiceDate.leasedServiceDateGrid', {
	extend : 'Ext.grid.Panel',
	title : '外请厢式车信息',
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : "查询结果为空",// 查询结果为空
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			if (record.get('name') != '' && record.get('name') != null) {
				return 'order-leasedServiceDate-row-green';
			}
		}
	},
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : true,
					plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    limitWarning:'最大查询记录不能超过'
                    }),
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedServiceDate.leasedServiceDateStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.columns = [{
					xtype : 'rownumberer',
					width : 90,
					text : '序号'// 序号
				},{
					header : '车牌号',
					dataIndex : 'vehicleNo'
				}, {
					header : '所属司机',
					dataIndex : 'leasedDriverName'
				}, {
					header : '司机电话',
					dataIndex : 'driverPhone'
				}, {
					header : '载重(吨)',
					dataIndex : 'deadLoad'
				}, {
					header : '自重(吨)',
					dataIndex : 'selfWeight'
				}, {
					header : '净空(方)',
					dataIndex : 'selfVolume'
				}, {
					header : '车长(米)',
					dataIndex : 'vehicleLength'
				}, {
					header : '所属车队',
					dataIndex : 'name'
				}, {
					header : '部门code',
					dataIndex : 'orgCode',
					hidden : true
				}, {
					header : '操作人',
					dataIndex : 'operatorName'
				}, {
					header : 'ID',
					dataIndex : 'id',
					hidden : true
				}];

		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});//  
		me.tbar = [{
			xtype : 'button',
			text : '绑定',
			disabled:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateBindingButton'),
			hidden:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateBindingButton'),
			width : 80,
			/**
			 * 绑定车队
			 */
			handler : function () {
				var selectionRecord = me.getSelectionModel().getSelection();
				if (selectionRecord && selectionRecord.length > 0) {
					var ids = new Array();
					for (var i = 0; i < selectionRecord.length; i++) {
						if(selectionRecord[i].data.name != null 
								&& selectionRecord[i].data.name != ''){
							Ext.MessageBox.show({
								title : '信息（失败）提示',
								msg : '【存在已绑定服务车队的车辆，请核实处理！】',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.INFO
							});
							return;
						}
						Ext.Array.include(ids, selectionRecord[i].data.vehicleNo);
					}
					Ext.MessageBox.show({
						title : '确认提示',
						msg : '绑定车队，确认是否继续？',
						buttons : Ext.Msg.YESNO,
						icon : Ext.MessageBox.QUESTION,
						fn : function (btn) {
							if (btn == 'yes' && !Ext.isEmpty(ids)) {
								Ext.Ajax.request({
									url : baseinfo.realPath('addLeasedServiceDateTream.action'),
									jsonData : {
										'leasedVehicleVo' : {
											'batchIds' : ids
										}
									},
									//"绑定"成功
									success : function (response) {
										me.getPagingToolbar().moveFirst();
										var json = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '信息提示',
											msg : json.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
									},
									//"绑定"失败
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '信息提示',
											msg : json.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
								});
							}
						}
					});
				} else {
					Ext.MessageBox.show({
						title : '信息提示',
						msg : '【无任何选中记录，请核实处理！】',
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});
				}
			}
		},{
					xtype : 'button',
					text : '释放',
					disabled:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateReleaseButton'),
					hidden:!baseinfo.leasedServiceDate.isPermission('leasedServiceDate/leasedServiceDateReleaseButton'),
					width : 80,
					/**
					 * 释放车队
					 */
					handler : function () {
						var selectionRecord = me.getSelectionModel().getSelection();
						if (selectionRecord && selectionRecord.length > 0) {
							var ids = new Array();
							var bindingOgrCode = new Array();
							for (var i = 0; i < selectionRecord.length; i++) {
								if(selectionRecord[i].data.name == null 
										|| selectionRecord[i].data.name == ''){
									Ext.MessageBox.show({
										title : '信息提示',
										msg : '【未绑定服务车队，请核实处理！】',
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.INFO
									});
									return;
								}
								Ext.Array.include(ids, selectionRecord[i].data.vehicleNo);
								Ext.Array.include(bindingOgrCode, selectionRecord[i].data.orgCode);
							}
							Ext.MessageBox.show({
								title : '确认提示',
								msg : '释放车队，确认是否继续？',
								buttons : Ext.Msg.YESNO,
								icon : Ext.MessageBox.QUESTION,
								fn : function (btn) {
									if (btn == 'yes' && !Ext.isEmpty(ids)) {
										Ext.Ajax.request({
											url : baseinfo.realPath('deleteLeasedServiceDateTream.action'),
											jsonData : {
												'leasedVehicleVo' : {
													'batchIds' : ids,
													'bindingOgrCodes' : bindingOgrCode
												}
											},
											//"释放"成功
											success : function (response) {
												me.getPagingToolbar().moveFirst();
												var json = Ext.decode(response.responseText);
												Ext.MessageBox.show({
													title : '信息提示',
													msg : json.message,
													width : 300,
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.INFO
												});
											},
											//"释放"失败
											exception : function (response) {
												var json = Ext.decode(response.responseText);
												Ext.MessageBox.show({
													title : '信息提示',
													msg : json.message,
													width : 300,
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.WARNING
												});
											}
										});
									}
								}
							});
						} else {
							Ext.MessageBox.show({
								title : '信息提示',
								msg : '【无任何选中记录，请核实处理！】',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					}
				}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});



/**
 * 初始化界面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
//	Ext.MessageBox.alert("liu");

	if (Ext.getCmp('T_baseinfo-leasedServiceDate_content')) {
		return;
	}

	var leasedServiceDateQueryForm = Ext
			.create('Foss.baseinfo.leasedServiceDate.leasedServiceDateQueryForm');

	// 外请车服务资料的列表grid
	var leasedServiceDateGrid = Ext
			.create('Foss.baseinfo.leasedServiceDate.leasedServiceDateGrid');

	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedServiceDate_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getLeasedServiceDateQueryForm : function() {
			return leasedServiceDateQueryForm;
		},
		getLeasedServiceDateGrid : function() {
			return leasedServiceDateGrid;
		},
		items : [ leasedServiceDateQueryForm, leasedServiceDateGrid ],
		renderTo : 'T_baseinfo-leasedServiceDate-body'
	});
});