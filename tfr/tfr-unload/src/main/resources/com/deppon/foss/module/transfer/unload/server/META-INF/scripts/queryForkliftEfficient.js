
var operate;//判断电叉个数配置是新增还是修改操作
/********************************电叉个数配置star******************************************/
/**
 * 定义电叉个数查询的列模型
 */
Ext.define('Foss.unload.queryForkliftEfficient.queryForkliftEfficientModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',//id
		type : 'string'
	},{
		name : 'transfFieldName',//转运场
		type : 'string'
	},{
		name : 'transfFieldCode',//转运场编码
		type : 'string'
	},{
		name : 'forkliftCount',//叉车数量
		type : 'number'
	},{
		name : 'configDate',//配置日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'workFromDate',//生效起始日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'workToDate',//生效截止日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'createPerson',//创建人
		type : 'string'
	},{
		name : 'createDate',//创建时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'createPersonCode',//创建人工号
		type : 'string'
	},{
		name : 'modifyPerson',//修改人
		type : 'string'
	},{
		name : 'modifyDate',//修改时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'modifyPersonCode',//修改人工号
		type : 'string'
	}]
});

/**
 * 电叉个数配置store
 * */
Ext.define('Foss.unload.queryForkliftEfficient.queryForkliftEfficientStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.unload.queryForkliftEfficient.queryForkliftEfficientModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :unload.realPath('queryForkliftData.action'),
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'forkliftEfficientVo.forkliftEfficientEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
		listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryForm = Ext.getCmp('T_unload-ElectricalForksConfigPanel_content').getElectricalForksConfigForm()
			var queryParams = queryForm.getForm().getValues();
		
			Ext.apply(operation, {
				params : {
							'forkliftEfficientVo.transfField' : queryParams.transfField,//转运场
							'forkliftEfficientVo.configDate':queryParams.configDate
						}
			});	
		}
	}
});
/**
 * 新增电叉数量配置表单form
 */
Ext.define('Foss.unload.queryForkliftEfficient.forkliftCountConfigForm',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',           
		layout:'column',
		columnWidth: 1,
		frame:true,
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:70
		},
		items : [{
					xtype: 'textfield',
					name: 'id',
					allowBlank: true,	
					autoWidth:true,
					fieldLabel : 'id',//id
					columnWidth: .99,
					readOnly:true,
					hidden:true
				},{
					name: 'transfField',
					fieldLabel:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfField'),//生效日期
					xtype : 'dynamicorgcombselector', 
					type : 'ORG',
					transferCenter : 'Y',
					allowBlank: false,	
					columnWidth: 0.99,
					autoWidth:false
					
				},{
					xtype:'datefield',
					format:'Y-m-d ',
					altFormats:'Y,m,d|Y.m.d ',
					autoWidth:true,
					name: 'workFromDate',
					allowBlank: true,
					readOnly:true,
					hidden:true,
					fieldLabel: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.workFromDate'),//生效日期
					columnWidth:.99
				},{
					xtype: 'numberfield',
					name: 'forkliftCount',
					allowBlank: false,	
					autoWidth:true,
					fieldLabel : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.forkliftCount'),//叉车数量
					regex:/^\d{0,6}$/,
					columnWidth: .99
				},{
					xtype: 'container',
					border : false,
					columnWidth:1,
					html: '&nbsp;'
				},{
			        xtype: 'container',
			        columnWidth:1,
			        buttonAlign: 'center',
					layout:'column',
					defaults: {
						margin:'5 0 5 0'
					},
			        items: [{
					xtype:'button',
					text:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.button.close'),//关闭
					columnWidth: .25,
					handler:function(){
						Ext.getCmp('T_unload-queryForkliftEfficientindex_content')
								.getforkliftCountConfigWindow().hide();
						}
					},{
						border : false,
						columnWidth:.5,
						html: '&nbsp;'
					},{
					xtype:'button',
					text:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.button.save'),//保存
					cls:'yellow_button',
					columnWidth: .25,
					handler:function(){
						
					var forkliftCountConfigForm = unload.queryForkliftEfficient.forkliftCountConfigForm
					var formParams = forkliftCountConfigForm.getForm().getValues();
					var transfFieldName=forkliftCountConfigForm.getForm().findField('transfField').getRawValue();
					var forkliftEfficientEntity = new Object();
						forkliftEfficientEntity.transfFieldName = transfFieldName;//转运场名称
						forkliftEfficientEntity.transfFieldCode = formParams.transfField;//转运场编码
						forkliftEfficientEntity.workFromDate=formParams.workFromDate;//生效日期
						forkliftEfficientEntity.forkliftCount=formParams.forkliftCount;//
					if (!forkliftCountConfigForm.getForm().isValid()){
							return false;
						}
						
					var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-queryForkliftEfficientindex_content')
								.getforkliftCountConfigWindow(), { 
								msg:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.pleaseWait')//提交中，请稍候。。。。。。
								});
					myMask.show();
					var url;
					if(operate == 'add'){
						url = unload.realPath('addForkliftData.action');
					
					}else if(operate == 'update'){
						url = unload.realPath('updateForkliftData.action');
						forkliftEfficientEntity.id = formParams.id;
					}else{
						return false;
					}
					var forkliftEfficientVo = {
						forkliftEfficientVo: {"forkliftEfficientEntity" : forkliftEfficientEntity}
					};
					Ext.Ajax.request({
								url : url,
								jsonData : forkliftEfficientVo,
								success : function(response) {
									myMask.hide();//解锁屏
									Ext.MessageBox.alert(unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.prompt'),unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.opreatSuccess'));//('提示','操作成功')
									Ext.getCmp('T_unload-queryForkliftEfficientindex_content')
									.getforkliftCountConfigWindow().hide();
									Ext.getCmp('T_unload-queryForkliftEfficient_ElectricalForksConfigGrid_content').store.load();
										
								},
								exception :function(response)
								{
									myMask.hide();//解锁屏
								    var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.prompt'),result.message);
								}
							});
						
						}
					}]
				}],
			constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
		}
});
/**新增电叉个数配置的window***/
Ext.define('Foss.unload.queryForkliftEfficient.forkliftCountConfigWindow', {
	extend : 'Ext.window.Window',
	title :unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigWindow.title'),// '电叉数量配置',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	closeAction : 'hide',
	width : 400,
	height:400,
	items:[unload.queryForkliftEfficient.forkliftCountConfigForm=Ext.create('Foss.unload.queryForkliftEfficient.forkliftCountConfigForm')],
	listeners:{
		'hide':function(window,eOpts){
	      	window.items.items[0].getForm().reset();
		 }	
		}
});
/**
 *电叉个数配置grid
 **/
Ext.define('Foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid', {
 extend : 'Ext.grid.Panel',
	frame : true,
	id:'T_unload-queryForkliftEfficient_ElectricalForksConfigGrid_content',
	columnLines: true,
	autoScroll : true,
	title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.title'),//叉车数量信息
	height : 390,
	emptyText : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.queryGrid.noData'),//暂无数据
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.tbar = [{
			xtype : 'button',
			text : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.button.addNew'),//新增
			gridContainer : this,
			disabled: !unload.queryForkliftEfficient.isPermission('unload/electricalForksConfigAddButton'),
			hidden: !unload.queryForkliftEfficient.isPermission('unload/electricalForksConfigAddButton'),
			handler : function(){
				operate = 'add';
				var forkliftCountConfigWindow = Ext.getCmp('T_unload-queryForkliftEfficientindex_content').getforkliftCountConfigWindow();
					forkliftCountConfigWindow.items.items[0].getForm().reset();
					unload.queryForkliftEfficient.forkliftCountConfigForm.getForm().findField('transfField').setReadOnly(false);
					forkliftCountConfigWindow.show();
			}
		}]
		me.store = Ext.create('Foss.unload.queryForkliftEfficient.queryForkliftEfficientStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});	
		unload.queryForkliftEfficient.pagingBar = me.bbar;
		me.callParent([ cfg ]);
	},
	columns:[{
		        xtype: 'rownumberer',
		        text : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.rownumberer'),//序号
		        align : 'center',
		        width: 35,
		        sortable: false
				},{
				xtype:'actioncolumn',
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.actioncolumn'),//操作
				// 关联model中的字段名
				width : 50,
				dataIndex : 'Operation',
				  items: [
//				          {
//		                iconCls: 'deppon_icons_delete',
//		                tooltip: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.delete'),//'删除',
//		                handler: function(grid, rowIndex, colIndex) {
//		                	
//		                var forkliftEfficientEntity = new Object();
//						forkliftEfficientEntity.id =grid.getStore().getAt(rowIndex).get('id') ;//数据id
//						var forkliftEfficientVo = {
//						forkliftEfficientVo: {"forkliftEfficientEntity" : forkliftEfficientEntity}
//					};
//		                	Ext.Ajax.request({
//								url : unload.realPath('deleteForkliftData.action'),
//								jsonData : forkliftEfficientVo,
//								success : function(response) {
//								Ext.MessageBox.alert(unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.prompt'),unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.deleteSuccess'));//（提示，删除成功）
//								Ext.getCmp('T_unload-queryForkliftEfficient_ElectricalForksConfigGrid_content').store.load();
//								},exception :function(response)
//								{
//								    var result = Ext.decode(response.responseText);
//									Ext.MessageBox.alert(unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.prompt'),result.message);
//								}
//							});
//		                	
//		                }
//		
//				  },
				  {
		                iconCls: 'deppon_icons_edit',
		                tooltip:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.update'),// '编辑',
		                handler: function(grid, rowIndex, colIndex) {
		                	operate = 'update';
		                	var workToDate=grid.getStore().getAt(rowIndex).get('workToDate');
		                	if(workToDate!=null&&workToDate!=''){
		                		Ext.MessageBox.alert(unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.prompt'),unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.cannotUpdate'));//（提示，不能修改失效数据）
		                		return false;
		                	}
		                 	var forkliftCountConfigWindow = Ext.getCmp('T_unload-queryForkliftEfficientindex_content').getforkliftCountConfigWindow();
		                  unload.queryForkliftEfficient.forkliftCountConfigForm.loadRecord(grid.getStore().getAt(rowIndex));
		                  unload.queryForkliftEfficient.forkliftCountConfigForm.getForm().findField('transfField')
		                  		.setCombValue(grid.getStore().getAt(rowIndex).get('transfFieldName'),grid.getStore().getAt(rowIndex).get('transfFieldCode'));
						unload.queryForkliftEfficient.forkliftCountConfigForm.getForm().findField('transfField').setReadOnly(true);
						forkliftCountConfigWindow.show();
		                }
				  }]
			},{
				header:"ID",//转运场
				dataIndex: 'id',
				align : 'center',
				hidden:true
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfField'),//转运场
			dataIndex: 'transfFieldName',
			align : 'center'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfFieldCode'),//转运场编码
			align : 'center',
			hidden:true,
			dataIndex: 'transfFieldCode'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.forkliftCount'),//叉车数量
			align : 'center',
			dataIndex: 'forkliftCount'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.configDate'),//配置日期
			align : 'center',
			dataIndex: 'configDate',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.workFromDate'),//生效日期
			dataIndex: 'workFromDate',
			align : 'center',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.workToDate'),//截止日期
			dataIndex: 'workToDate',
			align : 'center',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.createPerson'),//创建人
			align : 'center',
			dataIndex: 'createPerson'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.createPersonCode'),//创建人代码
			align : 'center',
			dataIndex: 'createPersonCode'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.createDate'),//创建时间
			align : 'center',
			dataIndex: 'createDate',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.modifyPerson'),//修改人
			align : 'center',
			dataIndex: 'modifyPerson'
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.modifyDate'),//修改时间
			dataIndex: 'modifyDate',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
				}
			}
			},{
			header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.modifyPersonCode'),//修改人工号
			dataIndex: 'modifyPersonCode'
			}]
 });
 

/**
 *电叉个数查询form
 ***/
Ext.define('Foss.unload.queryForkliftEfficient.ElectricalForksConfigForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title :unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.title'),//查询条件 	
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 75
	},
	layout:'column',
	defaults : {
		margin : '5 5 5 0',
		columns : 2
	},
	items: [{
			name: 'transfField',
			fieldLabel:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfField'),//转运场
			xtype : 'dynamicorgcombselector', 
			type : 'ORG',
			transferCenter : 'Y',
			allowBlank: true,
			columnWidth: .25,
			autoWidth:true
			
		},{
			xtype: 'container',
			columnWidth:.200,
			html: '&nbsp;'
		},{
			xtype:'datefield',
			format:'Y-m-d ',
			altFormats:'Y,m,d|Y.m.d ',
			allowBlank: true,
			autoWidth:true,
			name: 'configDate',
			fieldLabel: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid.configDate'),//配置日期
			columnWidth:.25
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
				}
			},{
				xtype: 'container',
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.query'),//查询
				disabled: !unload.queryForkliftEfficient.isPermission('unload/queryElectricalForksConfigButton'),
				hidden: !unload.queryForkliftEfficient.isPermission('unload/queryElectricalForksConfigButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					if(form.isValid()){
						unload.queryForkliftEfficient.pagingBar.moveFirst();
					}
				}
			}]
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**电叉个数配置panel**/
Ext.define('Foss.unload.queryForkliftEfficient.ElectricalForksConfigPanel', {
	extend : 'Ext.container.Container',
	id:'T_unload-ElectricalForksConfigPanel_content',
	frame : true,
	autoScroll : true,
	electricalForksConfigForm : null,
	getElectricalForksConfigForm : function() {
		if (this.electricalForksConfigForm == null) {
			this.electricalForksConfigForm = Ext.create('Foss.unload.queryForkliftEfficient.ElectricalForksConfigForm');
		}
		return this.electricalForksConfigForm;
	},
	electricalForksConfigGrid : null,
	getelectricalForksConfigGrid : function() {
		if (this.electricalForksConfigGrid == null) {
			this.electricalForksConfigGrid = Ext.create('Foss.unload.queryForkliftEfficient.ElectricalForksConfigGrid');
		}
		return this.electricalForksConfigGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getElectricalForksConfigForm(), me.getelectricalForksConfigGrid()]
		me.callParent([cfg]);
	}
});

/********************************电叉个数配置end******************************************/

/*********************************************叉车司机数据star********************************************************/
/**
 * 定义电叉司机查询的列模型
 */
Ext.define('Foss.unload.queryForkliftEfficient.queryForkliftDriverDataModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'forkliftDriverName',//司机姓名
		type : 'string'
	},{
		name : 'forkliftDriverCode',//司机工号
		type : 'string'
	},{
		name : 'queryDate',//查询日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'dayBoardsEfficiency',//当日板均效率
		type : 'string'
	},{
		name : 'dayTicketsEfficiency',//当日票效率
		type : 'string'
	},{
		name : 'monthBoardsEfficiency',//当月板均效率
		type : 'string'
	},{
		name : 'monthTicketsEfficiency',//当月票效率
		type : 'string'
	}]
});
/**
 * 电叉司机数据store
 * */
Ext.define('Foss.unload.queryForkliftEfficient.forkliftDriverDataStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.unload.queryForkliftEfficient.queryForkliftDriverDataModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :unload.realPath('queryForkliftDriverData.action'),
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'forkliftEfficientVo.forkliftDriverEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
		listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryForm = Ext.getCmp('T_unload-ForkliftDriverDataPanel_content').getForkliftDriverDataForm()
			var queryParams = queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
							'forkliftEfficientVo.forkliftDriverCode' : queryParams.forkliftDriverCode,//转运场
							'forkliftEfficientVo.queryDate':queryParams.queryDate
						}
			});	
		}
	}
});
/**
 *电叉司机数据grid
 **/
Ext.define('Foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid', {
 extend : 'Ext.grid.Panel',
	frame : true,
	id:'T_unload-queryForkliftEfficient_ForkliftDriverDataGrid_content',
	columnLines: true,
	autoScroll : true,
	title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.title'),//叉车效率信息
	height : 390,
	emptyText : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.queryGrid.noData'),//暂无数据
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.queryForkliftEfficient.forkliftDriverDataStore');
		me.tbar=['->',{
		      xtype: 'displayfield',
		      name:'attention',
		      fieldStyle:'color:red;font-size:8px;',
		      labelAlign : 'right',
		      value:"注意：当月叉车效率为截止查询日期"
		    }];
			
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});	
		unload.queryForkliftEfficient.pagingBarDriver = me.bbar;
		
		me.callParent([ cfg ]);
	},
	
	columns:[{
		        xtype: 'rownumberer',
		        text : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.rownumberer'),//序号
		        align : 'center',
		        width: 50,
		        sortable: false
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.forkliftDriverName'),//叉车司机姓名
				width: 120,
				dataIndex: 'forkliftDriverName',
				align : 'center',
				hidden:false
				
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.forkliftDriverCode'),//叉车司机工号
				width: 120,
				dataIndex: 'forkliftDriverCode'
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.queryDate'),//日期
				width: 120,
				align : 'center',
				hidden:false,
				dataIndex: 'queryDate',
				renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
				},{
				header:unload.queryForkliftEfficient.i18n("foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.dayEfficiency"),//当日叉车效率
				columns:[{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.boardsEfficiency'),//板均效率
					align : 'center',
					 width: 140,
					dataIndex: 'dayBoardsEfficiency'
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.ticketsEfficiency'),//票均效率
					align : 'center',
					 width: 140,
					dataIndex: 'dayTicketsEfficiency'
					}]
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.monthEfficiency'),//当月叉车效率
				columns:[{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.boardsEfficiency'),//板均效率
					align : 'center',
					width: 140,
					dataIndex: 'monthBoardsEfficiency'
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.ticketsEfficiency'),//票均效率
					align : 'center',
					width: 140,
					dataIndex: 'monthTicketsEfficiency'
					}]
				}]
 });
/**
 *叉车司机数据form
 ***/
Ext.define('Foss.unload.queryForkliftEfficient.ForkliftDriverDataForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title :unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.title'),//查询条件 	
	fieldDefaults: {
		msgTarget: 'side'
	},
	layout:'column',
	defaults : {
		margin : '5 5 5 0',
		columns : 2
	},
	items: [{
			name: 'forkliftDriverCode',
			fieldLabel:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.forkliftDriver'),//叉车司机
			xtype:'commonemployeeselector', 
			labelWidth: 90,
			allowBlank: true,
			columnWidth: .25,
			autoWidth:true
			
		},{
			xtype: 'container',
			columnWidth:.200,
			html: '&nbsp;'
		},{
			xtype: 'datefield',
			format: 'Y-m-d',
			allowBlank: false,
			autoWidth:true,
			name: 'queryDate',
			labelWidth: 70,
			fieldLabel: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.queryDate'),//查询日期
			value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
			//value: new Date(),
			editable:false,
			columnWidth:.25
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
				}
			},{
				xtype: 'container',
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.query'),//查询
				disabled: !unload.queryForkliftEfficient.isPermission('unload/queryForkliftDriverDataButton'),
				hidden: !unload.queryForkliftEfficient.isPermission('unload/queryForkliftDriverDataButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					if(form.isValid()){
						unload.queryForkliftEfficient.pagingBarDriver.moveFirst();
					}
				}
			}]
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**叉车司机数据**/
Ext.define('Foss.unload.queryForkliftEfficient.ForkliftDriverDataPanel', {
	extend : 'Ext.container.Container',
	id:'T_unload-ForkliftDriverDataPanel_content',
	frame : true,
	autoScroll : true,
	forkliftDriverDataForm : null,
	getForkliftDriverDataForm : function() {
		if (this.forkliftDriverDataForm == null) {
			this.forkliftDriverDataForm = Ext.create('Foss.unload.queryForkliftEfficient.ForkliftDriverDataForm');
		}
		return this.forkliftDriverDataForm;
	},
	forkliftDriverDataGrid : null,
	getForkliftDriverDataGrid : function() {
		if (this.forkliftDriverDataGrid == null) {
			this.forkliftDriverDataGrid = Ext.create('Foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid');
		}
		return this.forkliftDriverDataGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getForkliftDriverDataForm(), me.getForkliftDriverDataGrid()]
		me.callParent([cfg]);
	}
});
/*********************************************叉车司机数据end********************************************************/

/***************************************转运场数据**********************************************************/

/**
 * 定义电叉司机查询的列模型
 */
Ext.define('Foss.unload.queryForkliftEfficient.queryTransferFieldDataModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'transfFieldName',//转运长
		type : 'string'
	},{
		name : 'queryDate',//查询日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'dayTicketsEfficiency',//当日票效率
		type : 'string'
	},{
		name : 'dayBoardsEfficiency',//当日板均效率
		type : 'string'
	},{
		name : 'dayCarEfficiency',//当日车均效率
		type : 'string'
	},{
		name : 'dayPeopleEfficiency',//当日人均效率
		type : 'string'
	},{
		name : 'dayWaitTime',//当日等待时长
		type : 'string'
	},{
		name : 'monthTicketsEfficiency',//当月票效率
		type : 'string'
	},{
		name : 'monthBoardsEfficiency',//当月板均效率
		type : 'string'
	},{
		name : 'monthCarEfficiency',//当月车均效率
		type : 'string'
	},{
		name : 'monthPeopleEfficiency',//当月人均效率
		type : 'string'
	},{
		name : 'monthWaitTime',//当日等待时长
		type : 'string'
	}]
});
/**
 * 转运场store
 * */
Ext.define('Foss.unload.queryForkliftEfficient.transferFieldDataStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.unload.queryForkliftEfficient.queryTransferFieldDataModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :unload.realPath('queryTransferFieldData.action'),
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'forkliftEfficientVo.transferFieldEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
		listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryForm = Ext.getCmp('T_unload-TransferFieldDataPanel_content').getTransferFieldDataForm()
			var queryParams = queryForm.getForm().getValues();
		
			Ext.apply(operation, {
				params : {
							'forkliftEfficientVo.transfField' : queryParams.transfField,//转运场
							'forkliftEfficientVo.queryDate':queryParams.queryDate
						}
			});	
		}
	}
});
/**
 *转运场grid
 **/
Ext.define('Foss.unload.queryForkliftEfficient.TransferFieldDataGrid', {
 extend : 'Ext.grid.Panel',
	frame : true,
	id:'T_unload-queryForkliftEfficient_TransferFieldDataGrid_content',
	columnLines: true,
	autoScroll : true,
	title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.title'),//叉车效率信息
	height : 390,
	emptyText : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.queryGrid.noData'),//暂无数据
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.queryForkliftEfficient.transferFieldDataStore');
		me.tbar=['->',{
		      xtype: 'displayfield',
		      name:'attention',
		      fieldStyle:'color:red;font-size:8px;',
		      labelAlign : 'right',
		      value:"注意：当月叉车效率为截止查询日期"
		    }];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});	
		unload.queryForkliftEfficient.pagingBarTransf = me.bbar;
		
		me.callParent([ cfg ]);
	},
	columns:[{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfField'),//转运场
				width: 100,
				dataIndex: 'transfFieldName',
				align : 'center',
				hidden:false
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.TransferFieldDataGrid.date'),//日期
				width: 100,
				hidden:false,
				align : 'center',
				dataIndex: 'queryDate',
				renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
						value.getDate()), 'Y-m-d');
					}
				}
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.dayEfficiency'),//当日叉车效率
				columns:[{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.ticketsEfficiency'),//票均效率
					align : 'center',
					 width: 80,
					dataIndex: 'dayTicketsEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.boardsEfficiency'),//板均效率
					 width: 90,
					 align : 'center',
					dataIndex: 'dayBoardsEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.carEfficiency'),//车均票数
					 width: 90,
					 align : 'center',
					dataIndex: 'dayCarEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.peopleEfficiency'),//人均效率
					 width: 90,
					 align : 'center',
					dataIndex: 'dayPeopleEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.waitTime'),//等待时长
					 width: 90,
					 align : 'center',
					dataIndex: 'dayWaitTime',
					renderer:function(value){
						return covent(value);
					}
					}]
				},{
				header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.monthEfficiency'),//当月叉车效率
				columns:[{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.ticketsEfficiency'),//票均效率
					 width: 90,
					 align : 'center',
					dataIndex: 'monthTicketsEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.boardsEfficiency'),//板均效率
					 width: 90,
					 align : 'center',
					dataIndex: 'monthBoardsEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.carEfficiency'),//车均票数
					 width: 90,
					 align : 'center',
					dataIndex: 'monthCarEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.peopleEfficiency'),//人均效率
					 width: 90,
					 align : 'center',
					dataIndex: 'monthPeopleEfficiency',
					renderer:function(value){
						return covent(value);
					}
					},{
					header:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.waitTime'),//等待时长
					 width: 90,
					 align : 'center',
					dataIndex: 'monthWaitTime',
					renderer:function(value){
						return covent(value);
					}
					}]
				}]
 });
/**如果为null则返回0**/
function covent(str){
	if(str==null||str==""||str=='0'){
		return "0.000";
	}else{
		return str;
	}
}
/**
 *转运场数据form
 ***/
Ext.define('Foss.unload.queryForkliftEfficient.TransferFieldDataForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title :unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.title'),//查询条件
	fieldDefaults: {
		msgTarget: 'side'
	},
	layout:'column',
	defaults : {
		margin : '5 5 5 0',
		columns : 2
	},
	items: [{
			name: 'transfField',
			fieldLabel:unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.forkliftCountConfigForm.transfField'),//转运场
			xtype : 'dynamicorgcombselector', 
			type : 'ORG',
			labelWidth: 90,
			transferCenter : 'Y',
			allowBlank: true,
			columnWidth: .25,
			autoWidth:true
			
		},{
			xtype: 'container',
			columnWidth:.200,
			html: '&nbsp;'
		},{
			xtype: 'datefield',
			format: 'Y-m-d',
			allowBlank: false,
			autoWidth:true,
			name: 'queryDate',
			labelWidth: 70,
			fieldLabel: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ForkliftDriverDataGrid.queryDate'),//查询日期
			value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
			editable:false,
			columnWidth:.25
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
				}
			},{
				xtype: 'container',
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.ElectricalForksConfigForm.button.query'),//查询
				disabled: !unload.queryForkliftEfficient.isPermission('unload/queryTransferFieldDataButton'),
				hidden: !unload.queryForkliftEfficient.isPermission('unload/queryTransferFieldDataButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					if(form.isValid()){
						unload.queryForkliftEfficient.pagingBarTransf.moveFirst();
					}
				}
			}]
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**转运场数据**/
Ext.define('Foss.unload.queryForkliftEfficient.TransferFieldDataPanel', {
	extend : 'Ext.container.Container',
	id:'T_unload-TransferFieldDataPanel_content',
	frame : true,
	autoScroll : true,
	transferFieldDataForm : null,
	getTransferFieldDataForm : function() {
		if (this.transferFieldDataForm == null) {
			this.transferFieldDataForm = Ext.create('Foss.unload.queryForkliftEfficient.TransferFieldDataForm');
		}
		return this.transferFieldDataForm;
	},
	transferFieldDataGrid : null,
	getTransferFieldDataGrid : function() {
		if (this.transferFieldDataGrid == null) {
			this.transferFieldDataGrid = Ext.create('Foss.unload.queryForkliftEfficient.TransferFieldDataGrid');
		}
		return this.transferFieldDataGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTransferFieldDataForm(), me.getTransferFieldDataGrid()]
		me.callParent([cfg]);
	}
});
/**
 *程序入口 
 ***/
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	//电叉个数配置
	var electricalForksConfigPanel = Ext.create('Foss.unload.queryForkliftEfficient.ElectricalForksConfigPanel');
	//叉车司机数据
	var forkliftDriverDataPanel = Ext.create('Foss.unload.queryForkliftEfficient.ForkliftDriverDataPanel');
	//转运场数据	
	var transferFieldDataPanel =  Ext.create('Foss.unload.queryForkliftEfficient.TransferFieldDataPanel');
	unload.queryForkliftEfficient.electricalForksConfigPanel = electricalForksConfigPanel;
	unload.queryForkliftEfficient.forkliftDriverDataPanel = forkliftDriverDataPanel;
	unload.queryForkliftEfficient.transferFieldDataPanel = transferFieldDataPanel;

	Ext.create('Ext.panel.Panel', {
				id : 'T_unload-queryForkliftEfficientindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				forkliftCountConfigWindow : null,
				getforkliftCountConfigWindow : function() {
						if (this.forkliftCountConfigWindow == null) {
							this.forkliftCountConfigWindow = Ext.create('Foss.unload.queryForkliftEfficient.forkliftCountConfigWindow');
						}
						unload.queryForkliftEfficient.forkliftCountConfigWindow = this.forkliftCountConfigWindow;
						return this.forkliftCountConfigWindow;
					},
				layout : 'auto',
				// 定义容器中的项
				items : [{
							xtype : 'tabpanel',
							frame : false,
							bodyCls : 'autoHeight',
							cls : 'innerTabPanel',
							activeTab : 2,
							items : [{
										title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.Panel.transfFieldData'),//转运场数据
										tabConfig : {
											width : 120
										},
										itemId : 'transferFieldDataPanel',
										items : transferFieldDataPanel
									}, {
										title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.Panel.driverDate'),//叉车司机数据
										tabConfig : {
											width : 120
										},
										itemId : 'forkliftDriverDataPanel',
										items : forkliftDriverDataPanel
									},{
										title : unload.queryForkliftEfficient.i18n('foss.unload.queryForkliftEfficient.Panel.forkliftCountConfig'),//电叉个数配置
										tabConfig : {
											width : 120
										},
										itemId : 'electricalForksConfigPanel',
										items : electricalForksConfigPanel
									}]
						}],
				renderTo : 'T_unload-queryForkliftEfficientindex-body'
			});
});