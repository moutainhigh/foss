/**
 * 刘振华
 * 进仓地址管理
 */

function requestJsonAjax(url, params, successFn, failFn) {
	Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content').getEl()
			.mask("数据加载中,请稍等...");
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				timeout : 60000,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				}
			});
};

// 信息
baseinfo.showInfoMsg = function(message, fun) {
	var len = 1;
	if (!Ext.isEmpty(message)) {
		len = message.length;
	}
	Ext.Msg.show({
		title : baseinfo.regionalVehicleIndex
				.i18n('i18n.baseinfo-util.fossAlert'),
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
	setTimeout(function() {
		Ext.Msg.hide();
	}, 3000);
};

/*
 * 定义：一个"仓库"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.depotAddressIndex.depotAddressIndexModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [ {
		name : 'extid'
	}, {
		name : 'id', // ID标识
		type : 'string'
	}, {
		name : 'depotName', // 仓库名称
		type : 'string'
	}, {
		name : 'depotState', // 仓库状态
		type : 'string'
	}, {
		name : 'depotType', // 仓库类型
		type : 'string'
	}, {
		name : 'depotStateStr', // 仓库状态
		type : 'string'
	}, {
		name : 'depotTypeStr', // 仓库类型
		type : 'string'
	}, {
		name : 'depotRemark', // 仓库备注
		type : 'string'
	}, {
		name : 'provCode', // 省code
		type : 'string'
	}, {
		name : 'cityCode', // 城市code
		type : 'string'
	}, {
		name : 'countyCode', // 区县code
		type : 'string'
	}, {
		name : 'provCodeStr', // 省code
		type : 'string'
	}, {
		name : 'cityCodeStr', // 城市code
		type : 'string'
	}, {
		name : 'countyCodeStr', // 区县code
		type : 'string'
	}, {
		name : 'address', // 街道地址
		type : 'string'
	}, {
		name : 'orgCode', // 部门code
		type : 'string'
	}, {
		name : 'orgName', // 部门名称
		type : 'string'
	}, {
		name : 'longitude', // 经度
		type : 'string'
	}, {
		name : 'latitude', // 纬度
		type : 'string'
	}, {
		name : 'lonLat', // 纬度
		type : 'string'
	}, {
		name : 'repulseReason', // 退回原因
		type : 'string'
	} ]
});

// 创建仓库类型和状态 store
Ext.define('Foss.baseinfo.depotAddressIndex.depotAddressIndexStore', {
		extend : 'Ext.data.Store',
		autoLoad : false,
		//页面条数定义
		pageSize : 10,
		//绑定model
		model : 'Foss.baseinfo.depotAddressIndex.depotAddressIndexModel',
		proxy : {
			//以JSON的方式加载数据
			type : 'ajax',
			actionMethods : 'POST',
			url : baseinfo.realPath('queryDepotAddress.action'),
			reader : {
				type : 'json',
				root : 'depotAddressVo.depotAddressEntitys',
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
				var queryForm = Ext.getCmp('Foss_baseinfo_depotAddressIndex_QueryForm_Id').getForm();
				if (queryForm != null) {
					var queryParams = queryForm.getValues();
					Ext.apply(operation, {
						params : {
							'depotAddressVo.depotAddress.depotName' : queryParams.depotName,
							'depotAddressVo.depotAddress.depotState' : queryParams.depotState,
							'depotAddressVo.depotAddress.depotType' : queryParams.depotType,
							'depotAddressVo.depotAddress.provCode' : queryParams.provCode,
							'depotAddressVo.depotAddress.cityCode' : queryParams.cityCode, 
							'depotAddressVo.depotAddress.countyCode' : queryParams.countyCode,
							'depotAddressVo.depotAddress.orgCode' : queryParams.orgCode
						}
					});
				}
			}
		}
	});

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.depotAddressIndex.depotAddressIndexQueryForm',
				{
					extend : 'Ext.form.Panel',
					title : baseinfo.depotAddressIndex
							.i18n('foss.baseinfo.queryCondition'),
					id : 'Foss_baseinfo_depotAddressIndex_QueryForm_Id',
					frame : true,
					collapsible : true,
					layout : {
						type : 'table',
						columns : 4
					},
					defaults : {
						labelSeparator : ':',
						margin : '8 10 5 10',
						anchor : '100%'
					},
					constructor : function(config) {							//构造器
						var me = this,cfg = Ext.apply({}, config);
						me.items = me.getItems();
						me.callParent([cfg]);
					},
					height : 220,
					defaultType : 'textfield',
					layout : 'column',
					getItems : function() {
						var me = this;
						return [
								{
									colspan : 6,
									// labelWidth : 120,
									// hidden :(baseinfo.viewState.view ===
									// config.viewState),
									fieldLabel : baseinfo.depotAddressIndex
											.i18n('foss.baseinfo.expressDeliveryBigZone.Provinces'),
									provinceName : 'provCode',// 省份名称—对应name
									cityName : 'cityCode',// 城市name
									areaName : 'countyCode',// 县name
									allowBlank : true,
									nationIsBlank : true,
									provinceIsBlank : true,
									cityIsBlank : true,
									areaIsBlank : true,
									provinceLabel : '省份',
									cityLabel : '城市',
									areaLabel : '区县',
									width : 841,
									hideLabel : true,
									provinceWidth : 280,
									cityWidth : 280,
									areaWidth : 280,
									labelWid : 120,
									type : 'P-C-C',
									xtype : 'linkregincombselector'
								},
								{
									name : 'depotName',
									fieldLabel : '仓库名称',
									allowBlank : true
								},
								{
									name : 'depotState',
									fieldLabel : '状态',
									allowBlank : true,
									xtype : 'combo',
									store : baseinfo.getStore(null, null, [
											'valueCode', 'valueName' ], [ {
										'valueCode' : '1',
										'valueName' : '已提交'
									}, {
										'valueCode' : '2',
										'valueName' : '已确认'
									}, {
										'valueCode' : '3',
										'valueName' : '已退回'
									}, {
										'valueCode' : '4',
										'valueName' : '已作废'
									} ]),
									displayField : 'valueName',
									valueField : 'valueCode'

								},
								{
									name : 'depotType',
									fieldLabel : '仓库类型',
									allowBlank : true,
									xtype : 'combo',
									store : baseinfo.getStore(null, null, [
											'valueCode', 'valueName' ], [ {
										'valueCode' : '1',
										'valueName' : '电商仓'
									}, {
										'valueCode' : '2',
										'valueName' : '商超仓'
									}, {
										'valueCode' : '3',
										'valueName' : '海关仓'
									}, {
										'valueCode' : '4',
										'valueName' : '会展仓'
									}, {
										'valueCode' : '5',
										'valueName' : '普通仓'
									} ]),
									displayField : 'valueName',
									valueField : 'valueCode'

								},
								{
									name : 'orgCode',// 部门
									xtype : 'commonsaledepartmentselector',
									fieldLabel : '管理部门',
									delivery : 'Y',
									allowBlank : true
								},
								{
									xtype : 'button',
									width : 70,
									text : baseinfo.depotAddressIndex
											.i18n('foss.baseinfo.reset'),// 重置
									margin : '0 800 0 0',
									handler : function() {
										me.getForm().reset();
									}
								},
								{
									xtype : 'button',
									width : 70,
									text : baseinfo.depotAddressIndex
											.i18n('foss.baseinfo.query'),// 查询
									cls : 'yellow_button',
									handler : function() {
										if (me.getForm().isValid()) {
											Ext
													.getCmp(
															'T_baseinfo-depotAddressIndex_content')
													.getDepotAddressIndexDateGrid()
													.getPagingToolbar()
													.moveFirst()
										}
									}
								} ]
					}
				});


		/**
		 * 定义：一个"仓库"的查询数据表单
		 */
		Ext.define('Foss.baseinfo.depotAddressIndex.depotAddressIndexGrid',
				{
					extend : 'Ext.grid.Panel',
					title : '进仓地址信息',
					id : 'Foss_baseinfo_DepotAddressIndex_DepotAddressIndexGrid_Id',
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
							if (record.get('depotName') != ''
									&& record.get('depotName') != null) {
								return 'order-depotAddressIndex-row-green';
							}
						}
					},
					// 列表分页组件对象
					pagingToolbar : null,
					getPagingToolbar : function() {
						var me = this;
						if (Ext.isEmpty(me.pagingToolbar)) {
							me.pagingToolbar = Ext.create(
									'Deppon.StandardPaging', {
										store : me.store,
										pageSize : 10,
										prependButtons : true,
										plugins : Ext.create(
												'Deppon.ux.PageSizePlugin', {
													limitWarning : '最大查询记录不能超过'
												}),
										defaults : {
											margin : '0 0 15 3'
										}
									});
						}
						return me.pagingToolbar;
					},
					// 新增窗口
					getdepotAddressIndexAddWin : null,
					getdepotAddressIndexAddWindow : function() {
						if (this.getdepotAddressIndexAddWin == null) {
							this.getdepotAddressIndexAddWin = Ext
									.create('Foss.baseinfo.depotAddressIndex.DepotAddressIndexWin');
							this.getdepotAddressIndexAddWin.parent = this;// 父元素
						}
						/*
						 * var win = Ext.create(
						 * 'Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerWin', {
						 * 'title' : title }); return win;
						 */
						return this.getdepotAddressIndexAddWin;
					},
					constructor : function(config) {
						var me = this, cfgGrid = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.baseinfo.depotAddressIndex.depotAddressIndexStore');
						me.bbar = me.getPagingToolbar();
						me.getPagingToolbar().store = me.store;
						me.columns = [
								{
									/*text : "操作",
									width : 70,
									align : "center",
									renderer : function(value, cellmeta) {
										if (-1 < FossUserContext
												.getCurrentUserRoleCodes()
												.indexOf('YG00100')) {
											var updateStr = "<button  onclick='javscript:return false;' class='depot_update'>修改</button>";
											return "<div class='controlBtn'>"
													+ updateStr + "</div>";
										}
										if (-1 < FossUserContext
												.getCurrentUserRoleCodes()
												.indexOf('JCZJ00100')) {
											var examineStr = "<button  onclick='javscript:return false;' class='depot_examine'>审核</button>";
											return "<div class='controlBtn'>"
													+ examineStr + "</div>";
										}
									}*/
									text : '操作',// 操作
									xtype : 'actioncolumn',
									align : 'center',
									width : 80,
									items : [{
										iconCls : 'deppon_icons_edit',
										tooltip : '',// 修改
										width : 42,
										handler : function(grid, rowIndex, colIndex) {
											var record = grid.getStore().getAt(rowIndex);
											var updatewindow = me.getdepotAddressIndexAddWindow();
											var form = updatewindow.down('form').getForm();
											updatewindow.loadAddUpdateForm(record);
											updatewindow.getForm().down('linkregincombselector').setReginValue(
													record.data.provCodeStr,record.data.provCode,'1');
											updatewindow.getForm().down('linkregincombselector').setReginValue(
													record.data.cityCodeStr,record.data.cityCode,'2');
											updatewindow.getForm().down('linkregincombselector').setReginValue(
													record.data.countyCodeStr,
													record.data.countyCode,
													'3');
											// updatewindow.getForm().down('linkregincombselector').setDisabled(true);

											form.findField('lonLat').setValue(record.data.longitude
															+ ","+ record.data.latitude);
											// form.findField('orgCode').setReginValue(record.data.orgName);
											form.findField('orgCode').setCombValue(
													record.data.orgName,
													record.data.orgCode);
									
											updatewindow.show();
										}
									}]
								
								
								}, {
									header : '状态',
									dataIndex : 'depotStateStr'
								}, {
									header : '省',
									dataIndex : 'provCodeStr'
								}, {
									header : '市',
									dataIndex : 'cityCodeStr'
								}, {
									header : '区/县',
									dataIndex : 'countyCodeStr'
								}, {
									header : '地址',
									dataIndex : 'address'
								}, {
									header : '仓库名称',
									dataIndex : 'depotName'
								}, {
									header : '仓库类型',
									dataIndex : 'depotTypeStr'
								}, {
									header : '进仓备注',
									dataIndex : 'depotRemark'
								}, {
									header : '退回原因',
									dataIndex : 'repulseReason'
								}, {
									header : '管理部门',
									dataIndex : 'orgName',
									width : 150,
								}, {
									header : '',
									dataIndex : 'orgCode',
									hidden : true
								}, {
									header : '',
									dataIndex : 'provCode',
									hidden : true
								}, {
									header : '',
									dataIndex : 'cityCode',
									hidden : true
								}, {
									header : '',
									dataIndex : 'countyCode',
									hidden : true
								}, {
									header : '',
									dataIndex : 'depotState',
									hidden : true
								}, {
									header : '',
									dataIndex : 'depotType',
									hidden : true
								}, {
									header : 'id',
									dataIndex : 'id',
									hidden : true
								} ];

								me.listeners = {
									scrollershow : function(scroller) {
										if (scroller && scroller.scrollEl) {
											scroller.clearManagedListeners();
											scroller.mon(scroller.scrollEl,
													'scroll',
													scroller.onElScroll,
													scroller);
										}
									}/*,
									cellClick : function(thisTab, td,
											cellIndex, record, tr, rowIndex,
											event, eventObj) {
										
										var innerStr = event.getTarget().innerHTML;
										
										if (innerStr.indexOf("修改") >= 0
												|| innerStr.indexOf("审核") >= 0) {
											var updatewindow = me.getdepotAddressIndexAddWindow();
											var form = updatewindow.down('form').getForm();
											var record = me.getStore().getAt(rowIndex);
											updatewindow.loadAddUpdateForm(record);

											updatewindow.getForm().down('linkregincombselector').setReginValue(
															record.data.provCodeStr,record.data.provCode,'1');
											updatewindow.getForm().down('linkregincombselector').setReginValue(
															record.data.cityCodeStr,record.data.cityCode,'2');
											updatewindow
													.getForm()
													.down(
															'linkregincombselector')
													.setReginValue(
															record.data.countyCodeStr,
															record.data.countyCode,
															'3');
											// updatewindow.getForm().down('linkregincombselector').setDisabled(true);

											form
													.findField('lonLat')
													.setValue(
															record.data.longitude
																	+ ","
																	+ record.data.latitude);
											// form.findField('orgCode').setReginValue(record.data.orgName);
											form
													.findField('orgCode')
													.setCombValue(
															record.data.orgName,
															record.data.orgCode);
											
											updatewindow.show();
										}
										// Ext.MessageBox.alert('用户编号='+me.getStore().getAt(rowIndex).data.depotStateStr);
									}*/
								}, me.selModel = Ext.create(
										'Ext.selection.CheckboxModel', {// 多选框
											mode : 'MULTI',
											checkOnly : true
										});//  
						//me.tbar = [ ];
						me.tbar = me.getTbar(config);
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfgGrid ]);
					},
					getTbar:function(config){
						var me = this;
						return[{
							xtype : 'button',
							text : '新增',
							disabled : !baseinfo.depotAddressIndex
									.isPermission('depotAddress/depotAddressAddButton'),
							hidden : !baseinfo.depotAddressIndex
									.isPermission('depotAddress/depotAddressAddButton'),
							width : 80,
							/**
							 * 
							 */
							handler : function() {
								me.getdepotAddressIndexAddWindow()
										.show();
							}
						},
						{
							xtype : 'button',
							text : '作废',
							disabled : !baseinfo.depotAddressIndex
									.isPermission('depotAddress/depotAddressDeleteButton'),
							hidden : !baseinfo.depotAddressIndex
									.isPermission('depotAddress/depotAddressDeleteButton'),
							width : 80,
							handler : function() {
								var selectionRecord = me
										.getSelectionModel()
										.getSelection();
								var ids = new Array();
								if (selectionRecord
										&& selectionRecord.length > 0) {
									for ( var i = 0; i < selectionRecord.length; i++) {
										Ext.Array
												.include(
														ids,
														selectionRecord[i].data.id);
									}
									Ext.MessageBox
											.show({
												title : '确认提示',
												msg : '是否确定作废？',
												buttons : Ext.Msg.YESNO,
												icon : Ext.MessageBox.QUESTION,
												fn : function(btn) {
													if (btn == 'yes'
															&& !Ext
																	.isEmpty(ids)) {
														Ext.Ajax
																.request({
																	url : baseinfo
																			.realPath('deleteDepotAddress.action'),
																	jsonData : {
																		'depotAddressVo' : {
																			'depotAddress' : {
																				'batchIds' : ids,
																				'depotState' : '4',
																			}
																		}
																	},

																	// 成功
																	success : function(
																			response) {
																		me
																				.getPagingToolbar()
																				.moveFirst();
																		var json = Ext
																				.decode(response.responseText);
																		Ext.MessageBox
																				.show({
																					title : '信息提示',
																					msg : json.message,
																					width : 300,
																					buttons : Ext.Msg.OK,
																					icon : Ext.MessageBox.INFO
																				});
																	},
																	//失败
																	exception : function(
																			response) {
																		var json = Ext
																				.decode(response.responseText);
																		Ext.MessageBox
																				.show({
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
						},
						//    靠右显示
						'->',
						{
							xtype : 'button',
							text : '导出',
							width : 80,
							handler : function() {
								//	Ext.MessageBox.alert("导出！！");
								baseinfo.depotAddressIndex
										.exportDepotAddress();
							}
						}]
					}
				});


//通过查询条件导出数据
baseinfo.depotAddressIndex.exportDepotAddress = function(queryForm){
	var queryForm = Ext.getCmp('Foss_baseinfo_depotAddressIndex_QueryForm_Id').getForm();//得到查询的FORM表单

    if (queryForm != null) {
    	var queryParams = queryForm.getValues();
		Ext.MessageBox.buttonText.yes = "确定";  
		Ext.MessageBox.buttonText.no = "取消"; 
		if(!Ext.fly('downloadDepotAddress')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadDepotAddress';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm( baseinfo.depotAddressIndex.i18n('foss.baseinfo.tipInfo'), '确定要导出查询结果吗?', function(btn,text){
			if(btn == 'yes'){
				var params ={
					'depotAddressVo.depotAddress.depotName' : queryParams.depotName,
					'depotAddressVo.depotAddress.depotState' : queryParams.depotState,
					'depotAddressVo.depotAddress.depotType' : queryParams.depotType,
					'depotAddressVo.depotAddress.provCode' : queryParams.provCode,
					'depotAddressVo.depotAddress.cityCode' : queryParams.cityCode, 
					'depotAddressVo.depotAddress.countyCode' : queryParams.countyCode,
					'depotAddressVo.depotAddress.orgCode' : queryParams.orgCode
				}
		
				Ext.Ajax.request({
					url:baseinfo.realPath('exportDepotAddress.action'),
					form: Ext.fly('downloadDepotAddress'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.exportFailed'),result.message);
					}
				});
			}
		});
	}
};


/**
 * 新增/修改/审核界面表单
 */
Ext.define('Foss.baseinfo.depotAddressIndex.AddOrUpdateDepotAddressForm',
{
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
/*	defaults : {
		margin : '5 5 5 0',
		labelWidth : 85
	},*/
	layout:{
		type: 'table',
        columns: 6
    },
    constructor : function(config) {	
		var me = this,cfg = Ext.apply({}, config);
	//	me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	layout : 'column',
	getItems:function(){
		var me = this;
		return [{
			colspan:4,
//			labelWidth : 120,
			fieldLabel : '实际进仓地址',
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			allowBlank:true,
			nationIsBlank:true,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:false,
			provinceLabel : '实际进仓地址',
		//	cityLabel : baseinfo.depotAddressIndex.i18n('foss.baseinfo.airagencycompany.city'),
		//	areaLabel : baseinfo.depotAddressIndex.i18n('foss.baseinfo.airagencycompany.area'),
			width: 620,
			hideLabel:true,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
			colspan:2,
			labelWidth:120,
			name:'address',
			allowBlank : false,
			blankText: '请输入街道信息',
			maxLength:300,
			maxLengthText: '街道信息不能超过300个字符'
		},{
			colspan:2,
			labelWidth:120,
			name : 'depotName',
			fieldLabel : '仓库名称',
			allowBlank : false,
			blankText: '请输入仓库名称'
		},{
			colspan:2,
			labelWidth:120,
			name:'depotType',
			fieldLabel: '仓库类型',
		    allowBlank: false,
		    xtype : 'combo',
		    store:baseinfo.getStore(null,null,['valueCode','valueName']
		    ,[
		      {'valueCode':'1','valueName':'电商仓'},
		      {'valueCode':'2','valueName':'商超仓'},
		      {'valueCode':'3','valueName':'海关仓'},
		      {'valueCode':'4','valueName':'会展仓'},
		      {'valueCode':'5','valueName':'普通仓'}
		      ]),
		     displayField: 'valueName',
			 valueField: 'valueCode',
			 blankText: '请选择仓库类型'
		}, {
			colspan:2,
			labelWidth:120,
			name:'depotRemark',
			fieldLabel : '进仓备注',
			allowBlank : false,
			blankText: '请输入进仓备注',
			maxLength:300,
			maxLengthText: '街道信息不能超过300个字符'
		},{
			colspan:2,
			labelWidth:120,
			name:'lonLat',
			fieldLabel : '实际地址坐标',
			allowBlank : true,
			readOnly: true
		},{
			colspan:2,
			labelWidth:120,
			name : 'orgCode',// 部门
			xtype : 'commonsaledepartmentselector',
			fieldLabel : '管理部门',
			delivery: 'Y',
			allowBlank : false,
			maxLengthText: '管理部门不可为空'
		},{
			xtype : 'button',
			text : '查询',
			colspan:2,
			labelWidth:120,
			/**
			 * 
			 */
			handler : function() {
			//	var form = this.up('form').getForm();
			
			//	Ext.MessageBox.alert(form.findField('orgCode').getValue());
				var mapAddress = null;
				if(me.getForm().findField('provCode').getRawValue() != undefined
						||me.getForm().findField('provCode').getRawValue() != ""
						||me.getForm().findField('provCode').getRawValue() != null){
					mapAddress = me.getForm().findField('provCode').getRawValue();
				}
				if(me.getForm().findField('cityCode').getRawValue() != undefined
						||me.getForm().findField('cityCode').getRawValue() != ""
						||me.getForm().findField('cityCode').getRawValue() != null){
					mapAddress += me.getForm().findField('cityCode').getRawValue();
				}
				if(me.getForm().findField('countyCode').getRawValue() != undefined
						||me.getForm().findField('countyCode').getRawValue() != ""
						||me.getForm().findField('countyCode').getRawValue() != null){
					mapAddress += me.getForm().findField('cityCode').getRawValue();
				}
				if(me.getForm().findField('address').getValue() != undefined
						||me.getForm().findField('address').getValue() != ""
						||me.getForm().findField('address').getValue() != null){
					mapAddress += me.getForm().findField('address').getValue();
				}
				if(mapAddress != undefined||mapAddress != ""||mapAddress != null){
					
					 //定义回调函数
					 var callback = function(data){
					     
					     me.getForm().findField('lonLat').setValue(data.lng+","+data.lat);
					     me.getForm().findField('longitude').setValue(data.lng);
					     me.getForm().findField('latitude').setValue(data.lat);
						 //展示到地图上
					     baseinfo.depotAddressIndex.ployfeature.clearOverlays();
					//     baseinfo.depotAddressIndex.ployfeature.showNormalPoint(data.lng,data.lat);
					     baseinfo.depotAddressIndex.ployfeature.showMoveNormalPoint(data.lng,data.lat,callbackMove)
					     baseinfo.depotAddressIndex.ployfeature.setCenter(data);
					 }
					 var callbackMove = function(point,data){
						 console.log("移动后的经纬度:      "+point.lng+","+point.lat);
						 console.log("地址解析:    "+data);
						 me.getForm().findField('lonLat').setValue(point.lng+","+point.lat);
					     me.getForm().findField('longitude').setValue(point.lng);
					     me.getForm().findField('latitude').setValue(point.lat);
					     var obj = JSON.parse(data);
					     var province = obj.province;
					     var city = obj.city;
					     var district = obj.district;
					     var street = obj.address;
					     
					     var provinceCode = '';
					     var cityeCode = '';
					     var districteCode = '';
					     
					     var provinceParams ={
									'areaEntity.name' : province,
									'areaEntity.degree' : 'DISTRICT_PROVINCE'
								}
					     
					     var cityParams ={
					    		 'areaEntity.name' : city,
									'areaEntity.degree' : 'CITY'
								}
					     
					     var districtParams ={
					    		 'areaEntity.name' : district,
									'areaEntity.degree' : 'DISTRICT_COUNTY'
								}
					     
					     Ext.Ajax.request({
								url: '../baseinfo/queryDepotAddressAreaByName.action',
								params:provinceParams,
								actionMethods : 'POST',// 否则可能会出现中文乱码
								async: false,
								//得到路径成功
								success : function(response, opts) {
									var result = Ext.decode(response.responseText);
									provinceCode = result.resultStr;
								}
							});
					     
					     Ext.Ajax.request({
								url: '../baseinfo/queryDepotAddressAreaByName.action',
								params : cityParams,
								actionMethods : 'POST',// 否则可能会出现中文乱码
								async: false,
								//得到路径成功
								success : function(response, opts) {
									var result = Ext.decode(response.responseText);
									cityeCode = result.resultStr;
								}
							});
					     
					     Ext.Ajax.request({
								url: '../baseinfo/queryDepotAddressAreaByName.action',
								params : districtParams,
								actionMethods : 'POST',// 否则可能会出现中文乱码
								async: false,
								//得到路径成功
								success : function(response, opts) {
									var result = Ext.decode(response.responseText);
									districteCode = result.resultStr;
								}
							});
					     
					     if(null != provinceCode && 'null' != provinceCode && '' != provinceCode){
					    	 if(null != province && 'null' != province && '' != province){
					    		 me.down('linkregincombselector').setReginValue(
							    		 province,provinceCode,'1');
					    	 }
					     }
					     if(null != cityeCode && 'null' != cityeCode && '' != cityeCode){
					    	 if(null != city && 'null' != city && '' != city){
					    		 me.down('linkregincombselector').setReginValue(
							    		 city,cityeCode,'2');
					    	 }
					     }
					     if(null != districteCode && 'null' != districteCode && '' != districteCode){
					    	 if(null != district && 'null' != district && '' != district){
					    		 me.down('linkregincombselector').setReginValue(
							    		 district,districteCode,'3');
					    	 }
					     }
					     me.getForm().findField('address').setValue(street);
					 }
					baseinfo.depotAddressIndex.ployfeature.getPointByAddress(mapAddress, callback);
				}
			}
		},{
			colspan:6,
			xtype: 'container',
			height:300,
			width: 840,
			listeners: {
				afterrender: function(field) {
					var map = new DPMap.MyMap('VIEW', field.getId(),{center : "上海市", zoom : "STREET" },function(map) {
						baseinfo.depotAddressIndex.ployfeature = new DMap.PointFeature(map,{isAddable:false});
					})
				}
			}
		},{
			colspan:6,
			xtype: 'textareafield',
			fieldLabel:'退回原因',
			name: 'repulseReason',
			disabled : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressAgreeButton'),
			hidden : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressAgreeButton'),
			width: 840,
			height:40,
			maxLength:300,
			maxLengthText: '退回原因不能超过300个字符'
		},{
			colspan:2,
			labelWidth:120,
			name:'id',
			hidden : true,
		},{
			colspan:2,
			labelWidth:120,
			name:'longitude',
			hidden : true,
		},{
			colspan:2,
			labelWidth:120,
			name:'latitude',
			hidden : true,
		},{
			colspan:2,
			labelWidth:120,
			name:'depotState',
			hidden : true,
		}]
	}
	//items : []
});

/**
 * 新增界面
 */
Ext.define('Foss.baseinfo.depotAddressIndex.DepotAddressIndexWin',{
	extend : 'Ext.window.Window',
	title : '新增/修改进仓地址',					
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :890,
	height :610,	
	layout : 'fit',
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
//			Ext.getCmp('deppon_queryDestination_mapPane').destroy();
		//	baseinfo.depotAddressIndex.initData = [];
			this.down('form').getForm().reset();// 表格重置
		}
	},
	form : null,
	getForm : function() {
		if (this.form == null) {
			this.form = Ext
					.create('Foss.baseinfo.depotAddressIndex.AddOrUpdateDepotAddressForm');
		}
		return this.form;
	},
	loadAddUpdateForm : function (record) {
	//	this.addUpdateFormModel = record;
		this.getForm().loadRecord(record);
	},//接送货大区 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
	//	me.editForm = Ext.create('Foss.baseinfo.depotAddressIndex.AddOrUpdateDepotAddressForm',{'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.getForm()];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text : baseinfo.depotAddressIndex.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.depotAddressIndex.i18n('foss.baseinfo.reset'),
			disabled : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressResetButton'),
			hidden : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressResetButton'),
			handler :function(){
				var form = me.down('form').getForm();
				// 重置
				form.reset();			
			} 
		},{
			text : baseinfo.depotAddressIndex.i18n('foss.baseinfo.save'),
			disabled : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressSubmitButton'),
			hidden : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressSubmitButton'),
			handler :function(){
				var form = me.down('form').getForm();
		    	if(form.isValid()){
		    		if('4' == form.findField('depotState').getValue()){
						baseinfo.showInfoMes("已经作废，不可修改");
						return false;
					}
					if('2' == form.findField('depotState').getValue()){
						baseinfo.showInfoMes("已经确认，不可修改");
						return false;
					}
					if('' == form.findField('longitude').getValue() || null == form.findField('longitude').getValue()){
						baseinfo.showInfoMes("经纬度不可为空");
						return false;
					}
					if('' == form.findField('latitude').getValue() || null == form.findField('latitude').getValue()){
						baseinfo.showInfoMes("经纬度不可为空");
						return false;
					}
			    	console.log('测试！！！！   '+form.findField('provCode').getRawValue());
			    	var params = {
							'depotAddressVo' : {
								'depotAddress' : {
									'depotName' : form.findField('depotName').getValue(),
									'depotState' : '1',
									'depotType' : form.findField('depotType').getValue(),
									'depotRemark' : form.findField('depotRemark').getValue(),
									'provCode' : form.findField('provCode').getValue(),
									'cityCode' : form.findField('cityCode').getValue(),
									'countyCode' : form.findField('countyCode').getValue(),
									'provCodeStr' : form.findField('provCode').getRawValue(),
									'cityCodeStr' : form.findField('cityCode').getRawValue(),
									'countyCodeStr' : form.findField('countyCode').getRawValue(),
									'address' : form.findField('address').getValue(),
									'orgCode' : form.findField('orgCode').getValue(),
									'orgName' : form.findField('orgCode').getRawValue(),
									'lonLat' : form.findField('lonLat').getValue(),
									'longitude' : form.findField('longitude').getValue(),
									'latitude' : form.findField('latitude').getValue(),
									'id' : form.findField('id').getValue()
								}
							}
						};
			    	var successFun = function(json) {
						baseinfo.showInfoMes("保存成功");// 提示保存成功
						me.close();
						Ext.getCmp('T_baseinfo-depotAddressIndex_content').getDepotAddressIndexDateGrid()
								.getPagingToolbar().moveFirst();
					};
					var failureFun = function(json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时');// 请求超时
						} else {
							baseinfo.showErrorMes(json.message);// 提示失败原因
						}
					};
					
					
					var ajaxUrl = "";
					if("" != form.findField('id').getValue() && null != form.findField('id').getValue()){
						ajaxUrl = baseinfo.realPath('updateDepotAddress.action');
					} else {
						ajaxUrl = baseinfo.realPath('addDepotAddress.action');
					}
					
					baseinfo.requestJsonAjax(ajaxUrl, params,
							successFun, failureFun);// 发送AJAX请求
		    	}
			}
		},{
			text : '同意',
			disabled : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressAgreeButton'),
			hidden : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressAgreeButton'),
			handler :function(){
				var form = me.down('form').getForm();
				if('3' == form.findField('depotState').getValue()){
					baseinfo.showInfoMes("已经退回，不可再次审核");
					return false;
				}
				if('4' == form.findField('depotState').getValue()){
					baseinfo.showInfoMes("已经作废，不可再次审核");
					return false;
				}
		    	if(form.isValid()){
			    	var params = {
							'depotAddressVo' : {
								'depotAddress' : {
									'id' : form.findField('id').getValue(),
									'depotState' : '2'
								}
							}
						};
			    	var successFun = function(json) {
						baseinfo.showInfoMes("保存成功");// 提示保存成功
						me.close();
						Ext.getCmp('T_baseinfo-depotAddressIndex_content').getDepotAddressIndexDateGrid()
								.getPagingToolbar().moveFirst();
					};
					var failureFun = function(json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时');// 请求超时
						} else {
							baseinfo.showErrorMes(json.message);// 提示失败原因
						}
					};
					var ajaxUrl = baseinfo
							.realPath('updateDepotAddress.action');
					baseinfo.requestJsonAjax(ajaxUrl, params,
							successFun, failureFun);// 发送AJAX请求
		    	}
			}
		},{
			text : '退回',
			disabled : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressBackButton'),
			hidden : !baseinfo.depotAddressIndex
			.isPermission('depotAddress/depotAddressBackButton'),
			handler :function(){
				var form = me.down('form').getForm();
				if('2' == form.findField('depotState').getValue()){
					baseinfo.showInfoMes("已经同意，不可再次审核");
					return false;
				}
				if('4' == form.findField('depotState').getValue()){
					baseinfo.showInfoMes("已经作废，不可再次审核");
					return false;
				}
		    	if(form.isValid()){
		    		if(!form.findField('repulseReason').getValue()){
		    			baseinfo.showErrorMes("退回理由不能为空！");
		    			return false;
		    		}
			    	var params = {
							'depotAddressVo' : {
								'depotAddress' : {
									'id' : form.findField('id').getValue(),
									'depotState' : '3',
									'repulseReason':form.findField('repulseReason').getValue()
								}
							}
						};
			    	var successFun = function(json) {
						baseinfo.showInfoMes("保存成功");// 提示保存成功
						me.close();
						Ext.getCmp('T_baseinfo-depotAddressIndex_content').getDepotAddressIndexDateGrid()
								.getPagingToolbar().moveFirst();
					};
					var failureFun = function(json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时');// 请求超时
						} else {
							baseinfo.showErrorMes(json.message);// 提示失败原因
						}
					};
					var ajaxUrl = baseinfo
							.realPath('updateDepotAddress.action');
					baseinfo.requestJsonAjax(ajaxUrl, params,
							successFun, failureFun);// 发送AJAX请求
		    	}
			}
		}];
	}
});

/**
 * 初始化界面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-depotAddressIndex_content')) {
		return;
	}

	var depotAddressIndexQueryForm = Ext
			.create('Foss.baseinfo.depotAddressIndex.depotAddressIndexQueryForm');

	var depotAddressIndexDateGrid = Ext
			.create('Foss.baseinfo.depotAddressIndex.depotAddressIndexGrid');
//	depotAddressIndexDateGrid.addListener('cellclick',cellclick);  

	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-depotAddressIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getDepotAddressIndexQueryForm : function() {
			return depotAddressIndexQueryForm;
		},
		getDepotAddressIndexDateGrid : function() {
			return depotAddressIndexDateGrid;
		},
		items : [ depotAddressIndexQueryForm, depotAddressIndexDateGrid ],
		renderTo : 'T_baseinfo-depotAddressIndex-body'
	});
});