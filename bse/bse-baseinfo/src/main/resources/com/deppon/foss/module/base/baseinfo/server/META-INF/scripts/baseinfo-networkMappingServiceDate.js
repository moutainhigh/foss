
/**
 * 添加开始,结束时间方法 day--
 * 比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 */
baseinfo.networkMappingServiceDate.getStartTime =function(date,day){
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
baseinfo.networkMappingServiceDate.getEndTime =function(date,day){
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

/**
 * 查询条件的表单from
 */
Ext.define('Foss.baseinfo.networkMapping.networkMappingQueryForm', {
	extend:'Ext.form.Panel',
	title:'查询条件',
	id : 'Foss_baseinfo_networkMapping_QueryForm_Id',
	layout: 'form',
	frame : true,
	height:200,
	collapsible : true,
	defaults : {
		xtype : 'textfield',
		margin : '9 9 9 9',
		allowBlank : true,
		labelSeparator : ':',
		labelAlign : 'left'
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name:'twoPartnerCode',
		style:'font-size:30px',
		labelWidth:120,
		xtype : 'commonTwoLevelNetworkselector',
		fieldLabel : '二级合伙人网点名称',
		width:350,
	}, {
		name:'onePartnerCode',
		style:'font-size:30px',
		labelWidth:120,
		xtype : 'commonLeagueSaleDeptselector',
		fieldLabel : '一级合伙人网点名称',
		width:350,
	}, {
		style:'font-size:20px',
		xtype: 'container',
		border : false,
		html: '&nbsp;'
	},{
		name:'startDate',
		xtype : 'datefield',
		fieldLabel : '日期时间',
		allowBlank : false,//必填项
		editable : false,//不可编辑
		labelWidth:60,
		format : 'Y-m-d H:i:s',
		value : baseinfo.networkMappingServiceDate.getStartTime(new Date(),0)//默认当天的0时0分0秒
	}, {
		xtype : 'datefield',
		fieldLabel : '至',
		editable : false,//不可编辑
		allowBlank : false,//必填项
		name : 'endDate',
		format : 'Y-m-d H:i:s',
		labelWidth:30,
		value : baseinfo.networkMappingServiceDate.getEndTime(new Date(),0)//默认当天的23时59分59秒
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			text : ('重置'),
			margin:'0 800 0 0',
			width:70,
			disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkServiceDateQueryButton'),
			hidden:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkServiceDateQueryButton'),
			handler : function() {
				Ext.getCmp('Foss_baseinfo_networkMapping_QueryForm_Id').getForm().reset();
			}
		},{
			xtype : 'button', 
			text : ('查询'),
			width:70,
			cls:'yellow_button',
			disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkServiceDateQueryButton'),
			hidden:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkServiceDateQueryButton'),
			handler : function() {
				var selectform = Ext.getCmp('Foss_baseinfo_networkMapping_QueryForm_Id').getForm();
				var formDate=selectform.getValues();
				//定义两个用来判断
				var getStartTime = Ext.Date.parse(formDate.startDate, 'Y-m-d H:i:s'); 
			    var getEndTime = Ext.Date.parse(formDate.endDate, 'Y-m-d H:i:s');
				/**
				 * 添加查询时间逻辑
				 * 1、起始日期大于截止日期
				 * 2、同一自然月
				 */
				if(getStartTime>getEndTime){
					Ext.Msg.alert("FOSS提醒","起始时间不能大于结束时间！");  //FOSS提醒    起始时间不能大于结束时间！
					selectform.findField('startDate').setValue(baseinfo.networkMappingServiceDate.getStartTime(new Date(),0));
					selectform.findField('endDate').setValue(baseinfo.networkMappingServiceDate.getEndTime(new Date(),0));
					return;
				}
				//获取开始时间到结束的时间的天数差
				var diff =Math.round((getEndTime.getTime()-getStartTime.getTime())/(86400*1000));
				if(diff>31){
					Ext.Msg.alert("FOSS提醒","起止时间最大跨度为31天");
					selectform.findField('startDate').setValue(baseinfo.networkMappingServiceDate.getStartTime(new Date(),0));
					selectform.findField('endDate').setValue(baseinfo.networkMappingServiceDate.getEndTime(new Date(),0));
					return;
				}
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-networkMappingServiceDate_content').getNetworkMappingGrid().getPagingToolbar().moveFirst()
				}
			}
		}];
		me.callParent([ cfg ]);
	}
});

/**
 * 定义一个网点映射的Model
 */
Ext.define('Foss.baseinfo.networkMapping.networkMappingModel',{
	extend:'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields: [{
		name : 'extid'
	},{
		name : 'id', //ID标识
		type : 'string'
	},{
		//一级合伙人网点编码
		name: 'onePartnerCode', 
		type: 'string'
	},{
		//一级合伙人网点名称
		name: 'onePartnerName', 
		type: 'string'
	},{
		//二级合伙人网点编码
		name: 'twoPartnerCode', 
		type: 'string'
	},{
		//二级合伙人网点名称
		name: 'twoPartnerName', 
		type: 'string'
	},{
		//对接营业部编码
		name: 'dockingNumber', 
		type: 'string'
	},{
		//对接营业部名称
		name: 'dockingDepName', 
		type: 'string'
	},{
		//所属子公司编码
		name: 'subCompanyCode', 
		type: 'string'
	},{
		//所属子公司名称
		name: 'subCompanyName', 
		type: 'string'
	},{
		//二级网点所属子公司编码
		name: 'twoSubCompanyCode', 
		type: 'string'
	},{
		//二级网点所属子公司名称
		name: 'twoSubCompanyName', 
		type: 'string'
	},{
		//创建时间                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		name: 'createDate', 
		type: 'date',
		convert : function (value) {
			if (Ext.isEmpty(value)) {
				return null;
			}
			return new Date(value);
		}
	},{
		//创建人工号
		name: 'createCode', 
		type: 'string',
		convert:function(value){
			if(':'==value){
				return null;
			}else{
				return value;
			}
		}
	},{
		//修改时间                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		name: 'modifyDate', 
		type: 'date',
		convert : function (value) {
			if (Ext.isEmpty(value)) {
				return null;
			}
			return new Date(value);
		}
	},{
		//修改人工号
		name: 'modifyCode', 
		type: 'string',
		convert:function(value){
			if(':'==value){
				return null;
			}else{
				return value;
			}
		}
	},{
		//状态
		name: 'active', 
		type: 'string'
	}]
});

/**
 * 定义网点映射store
 */
Ext.define('Foss.baseinfo.networkMapping.networkMappingStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定网点映射的Model
	model : 'Foss.baseinfo.networkMapping.networkMappingModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryNetworkMappingServiceDate.action'),//查询url
		reader : {
			type : 'json',
			root : 'vo.partnerRelationList',//查询结果集
			totalProperty : 'totalCount',//统计总页数
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_networkMapping_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {//查询条件  参数
						'vo.partnerRelationEntity.twoPartnerCode':queryParams.twoPartnerCode,
						'vo.partnerRelationEntity.onePartnerCode':queryParams.onePartnerCode,
						'vo.partnerRelationEntity.startDate':queryParams.startDate,
						'vo.partnerRelationEntity.endDate':queryParams.endDate
					}
				});
			}
		}
	}
});

/**
 * 网点映射列表grid
 */
Ext.define('Foss.baseinfo.networkMapping.networkMappingGrid', {
	extend : 'Ext.grid.Panel',
	title : '网点映射列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true,
	flex : 1,
	sortableColumns : true,// 表头排序功能
	enableColumnHide : true,// 是否显示列字段
	enableColumnMove : true,// 是否可以挪动位置
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : "查询结果为空",// 查询结果为空
	multiSelect : true, // 复选框支持多选
	selModel : Ext.create('Ext.selection.CheckboxModel'),// 复选框
	viewConfig : {
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
	/****************** 新增/修改窗口 *************************/
	addUpdateWindow:null,
	getAddWindow:function(windowTitle, actionType){
		var me = this;
		if (this.addUpdateWindow == null) {
			this.addUpdateWindow = Ext.create('Foss.baseinfo.networkMapping.AddValueWidow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {//添加url
					operationUrl = baseinfo.realPath('addNetworkMappingDate.action');
				}
				if (actionType === 'upd') {//修改url
					operationUrl = baseinfo.realPath('updateNetworkMappingDate.action');
				}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		return this.addUpdateWindow;
	},
	loadAddUpdateForm : function (record) {
		this.addUpdateFormModel = record;
		this.getAddUpdateForm().getForm().loadRecord(record);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.networkMapping.networkMappingStore'),
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.columns = [ {
			xtype:'actioncolumn',
			width : 60,
			text : "操作列",
			items:[{
					iconCls : 'deppon_icons_edit',
					tooltip : '修改',
					disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateUpdateButton'),
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddWindow(item.tooltip, 'upd');
						var form = addUpdateWindow.getAddUpdateForm().getForm();
						addUpdateWindow.loadAddUpdateForm(record);
						//重新给公共选择器赋值
						form.findField('onePartnerCode').setCombValue(record.data.onePartnerName,record.data.onePartnerCode);
						form.findField('twoPartnerCode').setCombValue(record.data.twoPartnerName,record.data.twoPartnerCode);
						//要求保存二级网点所属子公司
						form.findField('twoSubCompanyName').setValue(record.get('twoSubCompanyName'));
						form.findField('oneSubCompanyName').setValue(record.get('subCompanyName'));
						//页面隐藏状态
						form.findField('twoSubCompanyCode').setValue(record.get('subCompanyCode'));
						form.findField('oneSubCompanyCode').setValue(record.get('subCompanyCode'));
						me.getAddWindow('修改','upd').show();
					}
				},{
					iconCls : 'deppon_icons_delete',
					tooltip : '作废',
					disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateDelButton'),
					getClass:function(value,metadata,record,rowIndex,colIndex,store){
						//获取数据数是否有效
						var active =FossDataDictionary.rendererDisplayToSubmit(record.get('active'),'FOSS_ACTIVE');
						if(active =='Y'){
							return 'deppon_icons_delete';
						}else{
							return 'deppon_icons_delete_hide';
						}
					},
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : '确认提示',
							msg : '作废（网点映射关系）后不可恢复，确认是否继续？',
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteNetworkMappingDate.action'),
										jsonData : {
											'vo' : {
												'idList' : new Array(record.data.id)
											}
										},
										//"作废"成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '信息（成功）提示',
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//"作废"失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '信息（失败）提示',
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
					}
				}]
		},{
			hidden:true,
			dataIndex : 'twoPartnerCode'
		},{
			hidden:true,
			dataIndex : 'onePartnerCode'
		},{
			width : 180,
			header : '二级合伙人网点名称',
			dataIndex : 'twoPartnerName'
		}, {
			width : 180,
			header : '一级合伙人网点名称',
			dataIndex : 'onePartnerName'
		},{
			width : 180,
			header : '对接营业部名称',
			dataIndex : 'dockingDepName'
		},{
			width : 100,
			header : '创建人',
			dataIndex : 'createCode'
		},{
			width : 100,
			header : '创建时间',
			dataIndex : 'createDate',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value),'Y-m-d H:i:s');
				}
			}
		},{
			header : '是否有效',
			dataIndex : 'active',
			renderer : function (value) {
				if(value === 'Y') {
					return '是';
				}else{
					return '否';
				}
			}
		}];
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		},
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
			xtype : 'button',
			text : '新增',
			disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateAddButton'),
			hidden:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateAddButton'),
			handler : function() {
				me.getAddWindow('新增','add').show();
			}
		}, {
			xtype : 'button',
			text : ('作废'),
			disabled:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateAddButton'),
			hidden:!baseinfo.networkMappingServiceDate.isPermission('networkMappingServiceDate/networkMappingServiceDateAddButton'),
			handler :function () {
				var selectionRecord = me.getSelectionModel().getSelection();
				if (selectionRecord && selectionRecord.length > 0) {
					var ids = new Array();
					for (var i = 0; i < selectionRecord.length; i++) {
						Ext.Array.include(ids, selectionRecord[i].data.id);
					}
					Ext.MessageBox.show({
						title : '确认提示',
						msg : '作废（网点映射关系）后不可恢复，确认是否继续？',
						buttons : Ext.Msg.YESNO,
						icon : Ext.MessageBox.QUESTION,
						fn : function (btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : baseinfo.realPath('deleteNetworkMappingDate.action'),
									jsonData : {
										'vo' : {
											'idList' : ids
										}
									},
									//"作废"成功
									success : function (response) {
										me.getPagingToolbar().moveFirst();
										var json = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '信息（成功）提示',
											msg : json.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
									},
									//"作废"失败
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '信息（失败）提示',
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
				}else{
					Ext.MessageBox.show({
						title : '信息（失败）提示',
						msg : '无任何选中记录！',
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});
				}
			}
		}];
		me.callParent([cfg]);
	}
});

//------------------widow ---------------------
//新增
Ext.define('Foss.baseinfo.networkMapping.AddValueWidow',{
	extend:'Ext.window.Window',
	title:'新增/修改网点映射关系',
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	//关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :450,
	addUpdateForm:null,
	getAddUpdateForm:function(){
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.networkMapping.AddUpdateValueForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.addUpdateFormModel = record;
		this.getAddUpdateForm().getForm().loadRecord(record);
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.networkMapping.networkMappingModel");
		}
		return this.addUpdateFormModel;
	},
	buttons : null,
	getButtons : function (index) {
		this.buttons = new Array();
		if (Ext.isEmpty(this.buttons)) {
			Ext.Array.include(this.buttons, this.items.items[1].items.items[0]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[1]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[2]);
		}
		if (arguments.length === 0) {
			return this.buttons;
		} else {
			return this.buttons[index];
		}
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	getWindowAction : function () {
		if (this.operationUrl.toString().indexOf('updateNetworkMappingDate') != -1) {
			return 'upd';
		}
		if (this.operationUrl.toString().indexOf('addNetworkMappingDate') != -1) {
			return 'add';
		}
		return null;
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[me.getAddUpdateForm()];
		me.fbar =[{
			text : '取消',
			handler :function(){
				me.fireEvent('beforeshow', me);
				me.hide().close();
			} 
		},{
			text : '重置',
			handler :function(){
				if (me.getWindowAction() === 'upd') {
					me.loadAddUpdateForm(me.getAddUpdateFormModel());
					//点击重置给选择器重新赋原来值
					me.getAddUpdateForm().getForm().findField('onePartnerCode').setCombValue(me.getAddUpdateFormModel().data.onePartnerName,me.getAddUpdateFormModel().data.onePartnerCode);
					me.getAddUpdateForm().getForm().findField('twoPartnerCode').setCombValue(me.getAddUpdateFormModel().data.twoPartnerName,me.getAddUpdateFormModel().data.twoPartnerCode);
				}
				if (me.getWindowAction() === 'add') {
					var form = me.getAddUpdateForm().getForm().reset();
					form = null;
				}
				me.fireEvent('beforeshow', me);
			} 
		},{
			text : '确定',
			cls:'yellow_button',
			margin:'0 0 0 235',
			plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds : 5
			}),
			listeners : {
				click : function (mine, event, options) {
					var addUpdateForm = me.getAddUpdateForm().getForm();
					if (addUpdateForm.isValid()) { //校验FORM是否通过校验
						//刷新数据Model
						addUpdateForm.updateRecord(me.getAddUpdateFormModel());
						//执行数据请求
						var partnerRelationEntity=addUpdateForm.getValues();
						
						partnerRelationEntity.onePartnerCode=addUpdateForm.findField('onePartnerCode').getValue();
						partnerRelationEntity.twoPartnerCode=addUpdateForm.findField('twoPartnerCode').getValue();
						
						partnerRelationEntity.onePartnerName=addUpdateForm.findField('onePartnerCode').getRawValue();
						partnerRelationEntity.twoPartnerName=addUpdateForm.findField('twoPartnerCode').getRawValue();
						Ext.Ajax.request({
							url : me.operationUrl.toString(),
							jsonData : {
								'vo' : {
									'partnerRelationEntity' : partnerRelationEntity
								}
							},
							success : function (response) {
								var json = Ext.decode(response.responseText);
								Ext.getCmp('T_baseinfo-networkMappingServiceDate_content').getNetworkMappingGrid().store.load();
								Ext.getCmp('T_baseinfo-networkMappingServiceDate_content').getNetworkMappingGrid().getPagingToolbar().moveFirst()
								Ext.MessageBox.show({
									title : '信息（成功）提示',
									msg : json.message,
									width : 300,
									buttons : Ext.Msg.OK,
									callback : function () {
										//修改成功之后依旧关闭窗口
										if (me.getWindowAction() === 'upd') {
											me.getAddUpdateForm().up('window').hide();
										}
										//添加成功之后内容清空窗口不关闭
										if(me.getWindowAction() === 'add'){
											var form = me.getAddUpdateForm().getForm().reset();
											form = null;
										}
									},
									icon : Ext.MessageBox.INFO
								});
								addUpdateForm = null;
							},
							exception : function (response) {
								var json = Ext.decode(response.responseText);
								Ext.MessageBox.show({
									title : '信息（失败）提示',
									msg : json.message,
									width : 300,
									buttons : Ext.Msg.OK,
									callback : function () {
//										me.getAddUpdateForm().up('window').hide();
									},
									icon : Ext.MessageBox.WARNING
								});
								addUpdateForm = null;
							}
						});
					} else {
						top.Ext.MessageBox.show({
							title : '信息（失败）提示',
							msg : (function () {
								var message = "<ul>";
								addUpdateForm.getFields().filterBy(function (value) {
									return value.getErrors().length > 0;
								}).each(function (item, index, length) {
									message += "<li>" + item.getErrors() + "</li>";
								});
								return message + "</ul>";
							})(),
							width : 350,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						addUpdateForm = null;
					}
				}
			}
		}];
		me.callParent([cfg]);
	},
	listeners : {
		beforeshow : function (me, eOpts) {
			var fielsds = me.getAddUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		},
		beforehide : function (me, eOpts) {
			me.getAddUpdateForm().getForm().reset();
		},
		beforeclose : function (me, eOpts) {
			me.fireEvent('beforehide', me);
		}
	}
});
//--------------------FORM-------------------------------
Ext.define('Foss.baseinfo.networkMapping.AddUpdateValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	width : '100%',
	collapsible : true,
	isSearchComb : true,
	allowBlank : true,
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
    partnerRelationEntity:null, //新增网点关系映射
    deletePartnerRelationList:null,//作废网点映射的集合
    isupdate:false,
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[{xtype : 'fieldset',
    				columnWidth : 0.5,
    				title : '二级合伙人网点信息',
    				collapsible : true,
    				defaultType : 'textfield',
    				defaults : {
    					anchor : '100%',
    					margin : '5 25 5 25',
    					labelWidth : 125,
    					labelStyle : 'text-align: left;'
    				},
    				layout : {
    					type : 'table',
    					columns : 2
    				},
    				items : [{
    					xtype:'commonTwoLevelNetworkselector',
        				fieldLabel:'二级合伙人网点名称',
        				name:'twoPartnerCode',
        				allowBlank : false,
        				width:350,
        				listeners:{
        					'select':function(combo,records,eOpts){
        						if(records.length>0){
        							var form = this.up('form').getForm();
        							var record = records[0];
        							if(record!=null||record!=''){
    									//调用后台根据code获取所属子公司								
        								Ext.Ajax.request({
        									url:baseinfo.realPath('getSubCompanyNameByTwoCode.action'),
        									params:{
        										'vo.twoVo.code':record.get('code')
        									},
        									success:function(response){
        										var result = Ext.decode(response.responseText);	
        										form.findField('twoSubCompanyName').setValue(null);
        										form.findField('twoSubCompanyCode').setValue(null);
        										if(result.vo.twoVo.entityByTwoCode!=null){
        											form.findField('twoSubCompanyName').setValue(result.vo.twoVo.entityByTwoCode.subCompanyName);
        											form.findField('twoSubCompanyCode').setValue(result.vo.twoVo.entityByTwoCode.subCompanyCode);
        										}
        									},
        									exception:function(response){
        										var result = Ext.decode(response.responseText);
        										Ext.MessageBox.show({
        											title : '温馨提示',
        											msg : result.message,
        											width : 300,
        											buttons : Ext.Msg.OK,
        											icon : Ext.MessageBox.WARNING
        										});
        									}			
        								});
        							}else{
        								form.findField('twoPartnerCode').setValue(null);
        			 					form.findField('twoSubCompanyName').setValue(null);
        			 					form.findField('twoSubCompanyCode').setValue(null);
        							}
        							
        						}
        					}
        				}
        			},{
    					xtype:'container',
    					html:'&nbsp;',
    					width: 100
    				},{
        				fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属子公司名称',
        				name:'twoSubCompanyName',
        				readOnly:true
        			},{
        				xtype:'hiddenfield',//隐藏，提交传到后台
        				name:'twoSubCompanyCode'
        			},{
        				xtype:'hiddenfield',//隐藏，提交传到后台
        				name:'id'
        			}]
    			},
    			{xtype : 'fieldset',
    			 columnWidth : 0.5,
    			 title : '一级合伙人网点信息',
    			 collapsible : true,
    			 defaultType : 'textfield',
    			 defaults : {
 					anchor : '100%',
 					margin : '5 25 5 25',
 					labelWidth : 125,
 					labelStyle : 'text-align: left;'
 					
			},
		    layout : {
			  type : 'table',
			  columns : 1
		    },
			items : [{
				xtype:'commonLeagueSaleDeptselector',
				fieldLabel:'一级合伙人网点名称',
				name:'onePartnerCode',
				allowBlank : false,
				width:350,
				listeners:{
					'select':function(combo,records,eOpts){
						if(records.length>0){
							var form = this.up('form').getForm();
							var record = records[0];
							if(record!=null||record!=''){
								//调用后台根据code获取所属子公司								
								Ext.Ajax.request({
									url:baseinfo.realPath('getSubCompanyNameByOneCode.action'),
									params:{
										'vo.oneVo.code':record.get('code')
									},
									success:function(response){
										var result = Ext.decode(response.responseText);	
										form.findField('oneSubCompanyName').setValue(null);
										form.findField('oneSubCompanyCode').setValue(null);
										if(result.vo.oneVo.entityByOneCode!=null){
											form.findField('oneSubCompanyName').setValue(result.vo.oneVo.entityByOneCode.subCompanyName);
											form.findField('oneSubCompanyCode').setValue(result.vo.oneVo.entityByOneCode.subCompanyCode);
										}
									},
									exception:function(response){
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : result.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}			
								});
							}else{
								form.findField('onePartnerCode').setValue(null);
			 					form.findField('oneSubCompanyName').setValue(null);
			 					form.findField('oneSubCompanyCode').setValue(null);
							}
							
						}
					}
				}
			},{
				fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属子公司名称',
				name:'oneSubCompanyName',
    			readOnly:true
    		},{
    			xtype:'hiddenfield',
    			fieldLabel:'所属子公司编码',
    			name:'oneSubCompanyCode'
        	}]
			},
	      ];
    	me.callParent([cfg]);
    },
});
/**
 * ------------------------------------------初始化界面------------------------------------------------------------------------
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-networkMappingServiceDate_content')) {
		return;
	}
	//表单from
	var networkMappingQueryForm = Ext.create('Foss.baseinfo.networkMapping.networkMappingQueryForm');

	//一级二级网点映射关系的列表grid
	var networkMappingGrid = Ext.create('Foss.baseinfo.networkMapping.networkMappingGrid');
	
	//面板
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-networkMappingServiceDate_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getNetworkMappingQueryForm : function() {
			return networkMappingQueryForm;
		},
		getNetworkMappingGrid : function() {
			return networkMappingGrid;
		},
		items : [networkMappingQueryForm,networkMappingGrid],
		renderTo : 'T_baseinfo-networkMappingServiceDate-body'
	});
});