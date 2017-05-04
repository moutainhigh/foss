// 查询的最大天数为31天
order.specialPickupAddress.MAXDAYS = 31 * 24 * 60 * 60 * 1000;
/**
 * 特殊地址库管理
 * 
 * @author zhaobin
 */

//时间默认值
order.specialPickupAddress.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

order.specialPickupAddress.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

//定义特殊地址库模型
Ext.define('Foss.order.specialPickupAddress.SpecialPickupAddressModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'id'},//ID
				{ name: 'address'},//ID
				{ name: 'addressType',
			    	convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'SPECIAL_ADDRESS_TYPE');
				}}, //异常地址类型
				{ name: 'operatorCode'}, //操作人code
				{ name: 'operator'},  //操作人
				{ name: 'operateOrgCode'},  //操作人部门code
				{ name: 'operateOrgName'},  //操作人部门名称
				{ name: 'operateTime',type:'date',
					 convert:function(value){
						 if(value!=null){
							 var date = new Date(value);
							 return date;
						 }else{
							 return null;
						 }
					 }}, //操作时间
				{ name: 'vehicleNo'},  //车牌号
				{ name: 'vehicleDept'}  //车辆所属部门
			 ]
});

//定义特殊地址store
Ext.define('Foss.order.specialPickupAddress.SpecialPickupAddressStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.order.specialPickupAddress.SpecialPickupAddressModel',
	//是否自动查询
	autoLoad: false,
	//默认每页数据大小
	pageSize:10,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		url:order.realPath('querySpecialAddressList.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'specialAddressVo.specialAddressEntityList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var form = Ext.getCmp('Foss_order_specialPickupAddress_QueryForm_Id').getForm();
			var queryParams = form.getValues();
			if(queryParams.queryTimeBegin ==""||queryParams.queryTimeEnd ==""){//14.7.15 gcl AUTO-162时间应该为必填项
				Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.queryBeginTimeNull'));
				return false;
			}
			//zxy 20140702 AUTO-85 start 新增:日期不能超过 30天
			var beginTime = Ext.Date.parse(queryParams.queryTimeBegin, 'Y-m-d H:i:s'), endTime = Ext.Date
			.parse(queryParams.queryTimeEnd, 'Y-m-d H:i:s');
	
			// 判断结束日期和开始日期是否在范围之内
			//14.8.1 gcl AUTO-215
			//if (endTime.getTime() - beginTime.getTime() > order.specialPickupAddress.MAXDAYS) {
			//	Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.expressWorkerStatus.tips'), order.specialPickupAddress
			//	.i18n('pkp.order.expressWorkerStatus.maxDays'));
			//	return false;
			//}
			//zxy 20140702 AUTO-85 end 新增:日期不能超过 30天
			Ext.apply(operation, {
				params : {					
						'specialAddressVo.specialAddressConditionDto.address': queryParams.address,
						'specialAddressVo.specialAddressConditionDto.operator': queryParams.operator,
						'specialAddressVo.specialAddressConditionDto.vehicleNo': queryParams.vehicleNo,
						'specialAddressVo.specialAddressConditionDto.addressType': queryParams.addressType,
						'specialAddressVo.specialAddressConditionDto.queryTimeBegin': queryParams.queryTimeBegin,
						'specialAddressVo.specialAddressConditionDto.queryTimeEnd': queryParams.queryTimeEnd
					}
			});	
		}
	}
});

// 查询条件
Ext.define('Foss.order.specialPickupAddress.QueryForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_order_specialPickupAddress_QueryForm_Id',
	title : order.specialPickupAddress.i18n('pkp.order.orderHandle.queryCondition'),
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	// 收缩
	collapsible : true,
	// 动画收缩
	animCollapse : true,
	layout : {
		type : 'column'
	},
	defaults : {
		xtype : 'textfield',
		margin : '5 10 5 10',
		labelWidth : 100
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [
					{
						fieldLabel : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.address'),
						columnWidth : .33,
						name : 'address'
					},
					{
						fieldLabel : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.operator'),
						columnWidth : .33,
						name : 'operator'
					},
					{
						fieldLabel : order.specialPickupAddress.i18n('pkp.order.orderHandle.vehicleNo'),
						columnWidth : .34,
						xtype : 'commontruckselector',
						//showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
						name : 'vehicleNo'
					},
					{
						xtype : 'combobox',
						name : 'addressType',
						columnWidth : .33,
						fieldLabel : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.specialPickupAddressType'),
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						editable : false,
						labelWidth : 120,
						value:'',
						store : FossDataDictionary.getDataDictionaryStore('SPECIAL_ADDRESS_TYPE', null, {
							'valueCode' : '',
							'valueName' : '全部'
						})
					},
					{
						xtype : 'rangeDateField',
						fieldId : 'Foss_order_QueryForm_specialPickupAddress_ID',
						fieldLabel : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.queryTime'),
						dateType : 'datetimefield_date97',
						fromName : 'queryTimeBegin',
						toName : 'queryTimeEnd',
						editable : false,
						allowBlank : false, /*14.7.15 gcl AUTO-162查询时间为必填项*/
						fromValue: Ext.Date.format(order.specialPickupAddress.getTargetDate(new Date(),-30),'Y-m-d H:i:s'),
						toValue: Ext.Date.format(order.specialPickupAddress.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
						columnWidth : .5,
						labelWidth : 120
					},
					{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [
								{
									text : order.specialPickupAddress.i18n('pkp.order.orderHandle.reset'),
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form').getForm();
										myform.reset();
										myform.findField('queryTimeBegin').setValue(Ext.Date.format(order.specialPickupAddress.getTargetDate(new Date(),-30),'Y-m-d H:i:s'));
										myform.findField('queryTimeEnd').setValue(Ext.Date.format(order.specialPickupAddress.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
									}
								},
								{
									xtype : 'container',
									border : false,
									columnWidth : .84,
									html : '&nbsp;'
								},
								{
									text : order.specialPickupAddress.i18n('pkp.order.orderHandle.query'),
									cls : 'yellow_button',
									columnWidth : .08,
									handler : function() {
										var resultGrid = Ext.getCmp('T_order-specialPickupAddressIndex_content').getResultGrid();
										resultGrid.getPagingToolbar().moveFirst();
									}
								} ]
					} ]
		});
		me.callParent(arguments);
	}
});

var updateAddressId = null;
var updateAddressVehicleNo = null;//14.7.28
var bundleWin = null;
Ext.define('Foss.order.specialPickupAddress.addressInfoGrid',
				{
					extend : 'Ext.grid.Panel',
					title : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.addressInfo'),
					frame : true,
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					// 收缩
					collapsible : true,
					// 动画收缩
					animCollapse : true,
					emptyText : order.specialPickupAddress.i18n('pkp.order.orderHandle.queryResultNull'),
					selModel :  Ext.create('Ext.selection.CheckboxModel'),
					columns : [
							{
								xtype : 'actioncolumn',
								width : 60,
								align : 'center',
								items : [ {
									iconCls : 'foss_icons_bse_applyReturn',
									tooltip : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.Invalid'),
									handler : function(grid, rowIndex, colIndex) {
										var selection = grid.getStore().getAt(rowIndex);
										var addressId = selection.get('id');
										Ext.Msg.confirm(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.isDelete'),//确认取消派送单吗？
												function(btn, text) {
													if (btn == "yes") {
														Ext.Ajax.request({
																	url : order.realPath('invalidSpecialAddress.action'),
																	params : {
																		'specialAddressVo.specialAddressConditionDto.id' : addressId,
																	},
																	success : function(response) {
																		var result = Ext.decode(response.responseText);
																		Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message);
																		Ext.getCmp('Foss_specialPickupAddress_GridPanel_Id').store.load();
																	},
																	exception : function(response) {
																		var result = Ext.decode(response.responseText);
																		Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message,'error',4000);
																	}
																});
													}
												});
									}
								} ]
							},
							{
								header:'id',//id,
								dateIndex:'id',
								hidden: true,
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.address'),
								dataIndex : 'address',
								xtype: 'ellipsiscolumn',
								width : 200
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.specialPickupAddressType'),
								dataIndex : 'addressType',
								width : 120
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.operator'),
								dataIndex : 'operator',
								width : 100
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.operateDept'),
								dataIndex : 'operateOrgName',
								xtype: 'ellipsiscolumn',
								width : 100
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.queryDispatchOrder.usecarTime'),
								dataIndex : 'operateTime',
								xtype: 'ellipsiscolumn',
								width : 150,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.pickupVehicle'),
								dataIndex : 'vehicleNo',
								showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
								width : 100
							},
							{
								header : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.vehicleDept'),
								dataIndex : 'vehicleDept',
								xtype: 'ellipsiscolumn',
								flex:1
							} ],
					viewConfig : {
						stripeRows : false,
						enableTextSelection : true,
					},
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (this.pagingToolbar == null) {
							this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
								store : this.store,
								plugins : 'pagesizeplugin',
								displayInfo : true
							});
						}
						return this.pagingToolbar;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext.create('Foss.order.specialPickupAddress.SpecialPickupAddressStore');
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [ {
								xtype : 'button',
								text : order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.bundleVehicle'),
								handler : function() {
									var records = Ext.getCmp('T_order-specialPickupAddressIndex_content').getResultGrid().getSelectionModel().getSelection();
									if(records.length != 1){
										Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'), 
												order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.choseOneRow'), 'error', 3000);
										return;
									}	
									Ext.getCmp('pkp_order_specialPickupAddress_vehicleNo_Id').setValue('');
									updateAddressId = records[0].get('id');
									updateAddressVehicleNo = records[0].get('vehicleNo');
									bundleWin = Ext.create('Foss.order.specialPickupAddress.VehicleWindow').show();
								}
							} ]
						} ]
						me.bbar = me.getPagingToolbar();
						me.callParent(arguments);
					}
				});

Ext.define('Foss.order.specialPickupAddress.updateVehicleForm',{
	extend:'Ext.form.Panel',	
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	initComponent : function(){
		var me = this;
		me.items = [{
			fieldLabel : order.specialPickupAddress.i18n('pkp.order.orderHandle.vehicleNo'),
			xtype : 'commontruckselector',
			id : 'pkp_order_specialPickupAddress_vehicleNo_Id',
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
			name : 'vehicleNo'
		}];
		me.callParent();
	}
});	

order.specialPickupAddress.updateVehicleForm = Ext.create('Foss.order.specialPickupAddress.updateVehicleForm');
//修改车辆
Ext.define('Foss.order.specialPickupAddress.VehicleWindow', {
	extend : 'Ext.window.Window',
	title: '车辆调整',
	resizable : false,
	closeAction : 'hide',
	width : 330,
	height : 220,
	items : [order.specialPickupAddress.updateVehicleForm],
	buttons : [{
		cls : 'yellow_button',
		text:order.specialPickupAddress.i18n('pkp.order.orderHandle.save'),//保存,
		handler : function() {
			var vehicleNo = Ext.getCmp('pkp_order_specialPickupAddress_vehicleNo_Id').getRawValue();
			var orgName = Ext.getCmp('pkp_order_specialPickupAddress_vehicleNo_Id').valueModels[0].data.orgName;
			var orgId = Ext.getCmp('pkp_order_specialPickupAddress_vehicleNo_Id').valueModels[0].data.orgId;
			Ext.Ajax.request({
				url:order.realPath('updateVehicleByid.action'),
				params : {
					'specialAddressVo.specialAddressEntity.id' : updateAddressId,
					'specialAddressVo.specialAddressEntity.vehicleNo': vehicleNo, //车牌号
					'specialAddressVo.specialAddressEntity.vehicleDept': orgName, //车队
					'specialAddressVo.specialAddressEntity.vehicleDeptCode': orgId //车队code
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message);
					Ext.getCmp('Foss_specialPickupAddress_GridPanel_Id').store.load();
					bundleWin.close();
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message,'error',4000);
				}
		    });
		}
	},{
		xtype: 'container',
		border : false,
		columnWidth:.1,
		html: '&nbsp;'
	},{
		cls : 'yellow_button',
		text:order.specialPickupAddress.i18n('pkp.order.specialPickupAddress.delete'),//删除
		handler : function() {
			Ext.Ajax.request({
				url:order.realPath('updateVehicleByid.action'),
				params : {
					'specialAddressVo.specialAddressEntity.id' : updateAddressId,
					'specialAddressVo.specialAddressEntity.vehicleNo': '', //车牌号
					'specialAddressVo.specialAddressEntity.vehicleDept': '', //车队
					'specialAddressVo.specialAddressEntity.vehicleDeptCode': '' //车队code
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message);
					Ext.getCmp('Foss_specialPickupAddress_GridPanel_Id').store.load();
					bundleWin.close();
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.specialPickupAddress.i18n('pkp.order.orderHandle.warning'),result.message,'error',4000);
				}
		    });
		}
	}],
	listeners : {  //14.7.28 gcl 修改车辆时设置默认值
		beforeshow:function(component, eOpts){
			if(updateAddressVehicleNo!=null&&updateAddressVehicleNo!=''){
				component.down('form').getForm().findField('vehicleNo').setValue(updateAddressVehicleNo);
			}
		}
	}
});	


Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create("Foss.order.specialPickupAddress.QueryForm");
	var vehicleGrid = Ext.create("Foss.order.specialPickupAddress.addressInfoGrid", {
		id : "Foss_specialPickupAddress_GridPanel_Id"
	});

	Ext.create('Ext.panel.Panel', {
		id : 'T_order-specialPickupAddressIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getResultGrid : function() {
			return vehicleGrid;
		},
		items : [ queryForm, vehicleGrid ],
		renderTo : 'T_order-specialPickupAddressIndex-body'
	});
});
