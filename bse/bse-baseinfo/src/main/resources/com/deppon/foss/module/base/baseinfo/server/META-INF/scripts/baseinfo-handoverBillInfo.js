// 转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
/**----------------------------------------------交单管理*Model*begin----------------------------------**/
 Ext.define('Foss.baseinfo.handoverBillInfo.HandoverBillInfoModel',{
     extend:'Ext.data.Model',
     idProperty : 'extid',
	idgen : 'uuid',
	fields : [{                                                                   
			name:'id',                                                    
			 type:'String'//                                            
			},{                                                                   
			name:'department',                                                    
			 type:'String'//    部门信息                                          
			}, {                                                                  
			 name:'startdateOneday',                                               
			 type:'String'//    自动交当日运单开始时间                            
			},                                                                    
			 {                                                                    
			name:'enddateOneday',                                                 
			 type:'String'//    自动交当日运单结束时间                            
			},                                                                    
			    {                                                                 
			name:'startdateTwoday',                                               
			 type:'String'//    自动交第二日运单开始时间                          
			},                                                                    
			 {                                                                    
			name:'enddateTwoday',                                                 
			 type:'String'//    自动交第二日运单结束时间                          
			},{
			 name:'automationHandoverBillNow',//自动交当日运单  时间
			 type:'String',
			 convert:function(v, record){
						 if(!Ext.isEmpty(record.data.startdateOneday)&&!Ext.isEmpty(record.data.enddateOneday)){
						 	return record.data.startdateOneday+'-'+record.data.enddateOneday;
						 }
						 return null;
						}
			},{
			 name:'automationHandoverBillTwo',//自动交第二日运单  时间
			 type:'String',
			 convert:function(v, record){
						 if(!Ext.isEmpty(record.data.startdateTwoday)&&!Ext.isEmpty(record.data.enddateTwoday)){
						 	return record.data.startdateTwoday+'-'+record.data.enddateTwoday;
						 }
						 return null;
						}
			},{                                                                  
			name:'automationMark',                                                
			 type:'String'//    只自动交入库的运单（Y：是，N否）                  
			},                                                                    
			  {                                                              
			 name:'artificialMark',                                                
			 type:'String'//    人工可交未入库的运单（Y：是，N否）-->
			  },{                                                                   
			name:'createTime',                                                    
			 type:'date'//    建时间-->
			 ,convert : baseinfo.changeLongToDate
			  },{                                                                  
			name:'modifyTime',                                                    
			 type:'fate' //修改时间 
			 ,convert : baseinfo.changeLongToDate
			},{                                                                   
			name:'active',                                                        
			 type:'String'//是否启用                                    
			},{                                                                  
			name:'createUser',                                                    
			 type:'String'//创建人工号                                   
			},{                                                                   
			name:'modifyUser',
			type:'String'//更新人工号                     
			},{
			name:'createDepartment',
			type:'String'//创建人部门编码
			},{
			name:'departmentName',
			type:'String'//创建人部门名称
			},{
			name:'empName',
			type:'String'//创建人名称
			}]
 });
/**----------------------------------------------交单管理*Model*end----------------------------------**/
/**----------------------------------------------交单管理*Store*begin----------------------------------**/
 Ext.define('Foss.baseinfo.handoverBillInfo.HandoverBillInfoStore',{
   extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.handoverBillInfo.HandoverBillInfoModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryHandoverBillInfoList.action'),
		reader : {
			type : 'json',
			root : 'handoverBillInfoVo.handoverBillInfoEntitys',
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
			var queryForm = Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content').getHandoverBillInfoQueryForm().getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'handoverBillInfoVo.handoverBillInfoEntity.department' : queryParams.department
					}
				});
			}
		}
	}
 }),
/**----------------------------------------------交单管理*Store*end----------------------------------**/
 


/**----------------------------------------------交单管理*查询Form*begin----------------------------------**/
Ext.define('Foss.baseinfo.handoverBillInfo.QueryHandoverBillInfoForm',{
      extend:'Ext.form.Panel',
      title:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.handoverBillInfoQueryForm'), //交单管理 查询
      frame:true,
      collapsible: true,
	  defaults: {
		margin: '8 10 5 10',
		anchor: '100%'
	  },
	  height: 180,
	  defaultType: 'textfield',
	  layout: 'column',
	  items:[{
			xtype : 'dynamicorgcombselector',
			name : 'department',
			fieldLabel : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.department'),//部门
			transDepartment : 'Y'
		}],
	  constructor: function (config) {
		var me = this,
			cfg = Ext.apply({}, config);
		me.fbar=[
		{
		  xtype:'button',
		  widtn:70,
		  text :baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.reset'),//重置
		  disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoQueryButton'),
		  hidden:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoQueryButton'),
		  margin:'0 800 0 0',
		  handler : function(but) {
		    but.up('form').getForm().rest();
		  }
		},{
		    xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.query'),//查询
			disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoQueryButton'),
			hidden:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoQueryButton'),
			handler : function(but) {
			  Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content').getHandoverBillInfoGrid().getPagingToolbar().moveFirst();
			}
		}]
		me.callParent([cfg]);
	  }
});
/**----------------------------------------------交单管理*查询Form*end----------------------------------**/

/**----------------------------------------------交单管理*新增修改Form*begin----------------------------------**/
Ext.define('Foss.baseinfo.handoverBillInfo.HandoverBillInfoAddUpdateForm',{
    extend : 'Ext.form.Panel',
	frame : true,
	width : '100%',
	collapsible : true,
	isSearchComb : true,
	defaults : {
		//margin : '5 25 5 25',
		//labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	items:[{
	   xtype:'fieldset',
	   collapsible : true,
	   title:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationHandoverBill'),//自动交单管理
	   layout : {
			type : 'table',
			columns : 2
	   },
	   items:[{
			xtype : 'dynamicorgcombselector',
			name : 'department',
			width:320,
			fieldLabel : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.department'),//部门
			transDepartment : 'Y',
			allowBlank : false,
			colspan:2
	   },{
	      xtype:'textfield',
	      name:'startdateOneday',
	      allowBlank : false,
	      readOnly : true,
	      labelWidth:120,
	      fieldLabel: baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationHandoverBillNow'),//自动交当日运单
	      value:'00:00:00'
	   },{
	   	xtype:'minutesSecondsField',
	   	name:'enddateOneday',
	    labelWidth:80,
	    increment:60*30,//分秒
	   	id: 'my97datetime',
	   	fieldLabel:'至',
	   	format:'H:m:s',
	   	minValue: '00:00:00',
        maxValue:'23:59:59', //Ext.Date.parse('23:59:59', 'h:i:s'),
		allowBlank : false
	   },{
	   	xtype:'minutesSecondsField',
	   	id: 'my97datetime2',
	   	fieldLabel: baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationHandoverBillTwo'),//自动交第二日运单
	   	name:'startdateTwoday',
	   	allowBlank : false,
	   	increment:60*30,
	   	labelWidth:120,
		format:'H:m:s',
	   	minValue:'00:00:00',
        maxValue: '23:59:58'//Ext.Date.parse('23:59:58', 'h:i:s')
	   },{
	    xtype:'textfield',
	    name:'enddateTwoday',
	    fieldLabel:'至',
	    labelWidth:80,
	    readOnly : true,
	    allowBlank : false,
	    value:'23:59:59'
	   },{
	    xtype:'checkboxfield',
	    fieldLabel:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationMark'),//只自动交入库位的运单
	    name:'automationMark',
	    labelWidth:135,
	    allowBlank : false,
	    readOnly : false,
		inputValue : 'Y'
	   }]
	},{
	  xtype:'fieldset',
	  title:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.artificialHandoverBill'),//人工交单管理
	  name:'automationMark',
	  items:[{
	    xtype:'checkboxfield',
	    checked:true,
	    fieldLabel:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.artificialMark'),//人工可交未入库位的运单
	    name:'artificialMark',
	    labelWidth:160,
	    allowBlank : false,
	    readOnly : false,
		inputValue : 'Y'
//	    defaultType:'checkboxgroup',
//	    items:[{
//	 			name:'artificialMark',
//	 			inputValue:'Y'
//	 		}]
	   
	  }]
	}]
});

/**----------------------------------------------交单管理*新增修改Form*end----------------------------------**/
/**----------------------------------------------交单管理*新增修改Window*begin----------------------------------**/
Ext.define('Foss.baseinfo.handoverBillInfo.AddUpdateHandoverBillInfoWindow',{
    extend:'Ext.window.Window',
    title:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.add'),//交单管理 新增
    width : 630,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	parent:null,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.handoverBillInfo.HandoverBillInfoAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.addUpdateFormModel = record;
		var tempForm = this.getAddUpdateForm().getForm().loadRecord(record);
	},
	listeners: {
		beforehide: function (me) { //隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();
			me.getAddUpdateForm().getForm().findField('department').setReadOnly(false);
			me.addUpdateFormModel=null;
		},
		beforeshow: function (me) { //显示WINDOW的时候清除数据
//			me.getAddUpdateForm().getForm().reset();
		}
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.handoverBillInfo.HandoverBillInfoModel");
		}
		return this.addUpdateFormModel;
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	operationType:null,
	setOperationType:function(operationType){
	this.operationType = operationType;
	},
	saveValue:function(addupdateWindow){	
		var addupdateWindow=addupdateWindow;
		var addUpdateForm=addupdateWindow.getAddUpdateForm().getForm();
		if (addUpdateForm.isValid()) { //校验FORM是否通过校验
			var formValue=addUpdateForm.getValues();	
			tempModel=addupdateWindow.getAddUpdateFormModel(formValue);
			addUpdateForm.updateRecord(tempModel);
			var automationMark = tempModel.get('automationMark');
			tempModel.set('automationMark', Ext.isBoolean(automationMark) ? (automationMark ? 'Y' : 'N') : (automationMark) == 'true' ? 'Y' : 'N');
			automationMark = null;
			var artificialMark = tempModel.get('artificialMark');
			tempModel.set('artificialMark', Ext.isBoolean(artificialMark) ? (artificialMark ? 'Y' : 'N') : (artificialMark) == 'true' ? 'Y' : 'N');
			artificialMark=null;
			if (addupdateWindow.operationType=== 'add') {
			  tempModel.set('modifyTime',null);
			  tempModel.set('createTime',null);
		    }
			var params = {
							'handoverBillInfoVo' : {
								'handoverBillInfoEntity' :addupdateWindow.getAddUpdateFormModel().data 
							}
						};
			
			params.handoverBillInfoVo.handoverBillInfoEntity.enddateOneday=
			addUpdateForm.findField('enddateOneday').getRawValue();
			params.handoverBillInfoVo.handoverBillInfoEntity.startdateTwoday=
			addUpdateForm.findField('startdateTwoday').getRawValue();
			
		var successFun = function(json) {
			// 隐藏页面
			addupdateWindow.close();
			// 新增成功
			Ext.ux.Toast.msg('FOSS提醒您', '保存成功!');
			// 重新加载
			Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content').getHandoverBillInfoGrid().getPagingToolbar().moveFirst();
			 
		};
		var failureFun = function(json) {
			 if (Ext.isEmpty(json)) {
				 baseinfo.showErrorMes(baseinfo.handoverBillInfo.i18n('foss.baseinfo.requestTimeout'));// 请求超时
			 } else {
				 baseinfo.showErrorMes(json.message);
			}
		};
		var url=baseinfo.realPath('addHandoverBillInfo.action');
		if (addupdateWindow.operationType=== 'upd') {
			url=baseinfo.realPath('updateHandoverBillInfo.action');
		}	
		baseinfo.requestJsonAjax(url, params, successFun,failureFun);
		}
		
		
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items=[me.getAddUpdateForm(),
		{
		 border:false,
		 xtype:'container',
		 layout:'column',
		 margin:'15 0 0 260',
		 defaults:{
		   margin:'0 0 30 0'
		 },
		 items:[{
		    xtype:'button',
		    columnWidth:.16,
		    text:baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.cancel'),//取消
		    handler:function(){
			me.hide().close();
		    }
		 },{
		    border : false,
			columnWidth : .10,
			html : '&nbsp;'
		 },{
			xtype : 'button',
			columnWidth : .16,
			text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.reset'),//重置
			handler : function () {
				if (me.operationType=== 'upd') {
					me.loadAddUpdateForm(me.getAddUpdateFormModel());
				}
                if (me.operationType === 'add') {
					 var form = me.getAddUpdateForm().getForm().reset();
				 }
				 me.fireEvent('beforeshow', me);
						
			}
		 },{
		  border : false,
		  columnWidth : .10,
		  html : '&nbsp;'
		 },{
		    columnWidth : .16,
		    xtype : 'button',
			cls : 'yellow_button',
			text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.save'),//保存
			plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds : 5
			}),
			handler:function(but){
				var addupdateWindow=but.up('window');
			  addupdateWindow.saveValue(addupdateWindow);
			}
		 }]
		}
		
		];
		me.callParent([cfg]);
		
	}
});
/**----------------------------------------------交单管理*新增修改Window*end----------------------------------**/


/**----------------------------------------------交单管理*Grid*begin----------------------------------**/
 Ext.define('Foss.baseinfo.handoverBillInfo.HandoverBillInfoGrid',{
 	extend:'Ext.grid.Panel',
 	columnLines : true, // 增加表格列的分割线
//	id : 'Foss_baseinfo_handoverBillInfo_handoverBillInfoGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.handoverBillInfoGridTitle'), // 定义表格的标题(交单管理信息)
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
//	plugins : [{
//			ptype : 'rowexpander',
//			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
//			rowBodyElement : 'Foss.baseinfo.handoverBillInfo.handoverBillInfoDetailPanel' // 行体内容
//		}
//	],
	addUpdatehandoverBillWindow:null,
	getAddUpdatehandoverBillWindow:function(windowTitle, actionType){
	 var me = this;
		if (this.addUpdatehandoverBillWindow == null) {
			this.addUpdatehandoverBillWindow = Ext.create('Foss.baseinfo.handoverBillInfo.AddUpdateHandoverBillInfoWindow');
		}
	this.addUpdatehandoverBillWindow.parent = this; //父元素
	this.addUpdatehandoverBillWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addHandoverBillInfo.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateHandoverBillInfo.action');
				} else {}
				return operationUrl;
			})(actionType));
	this.addUpdatehandoverBillWindow.setOperationType(actionType);
	this.addUpdatehandoverBillWindow.getAddUpdateForm().setTitle(windowTitle);
	return this.addUpdatehandoverBillWindow;
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
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	viewConfig: {forceFit:true},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.handoverBillInfo.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoUpdateButton'),
					/**
					 * baseinfo.handoverBillInfo.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						
						var addUpdatehandoverBillWindow = grid.up().getAddUpdatehandoverBillWindow(item.tooltip, 'upd');
						var form = addUpdatehandoverBillWindow.getAddUpdateForm().getForm();
						addUpdatehandoverBillWindow.loadAddUpdateForm(record);
					//	form.findField('department').setCombValue(record.data.department,record.data.departmentName);
						form.findField('department').setCombValue(record.get('departmentName'),record.get('department'));
						form.findField('department').setReadOnly(true);
						form.findField('automationMark').setValue(record.get('automationMark') == 'Y' ? true : false);
						form.findField('artificialMark').setValue(record.get('artificialMark') == 'Y' ? true : false);
						addUpdatehandoverBillWindow.show();
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.handoverBillInfo.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoCancelButton'),
					/**
					 * baseinfo.handoverBillInfo.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.handoverBillInfo.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.handoverBillInfo.i18n('foss.baseinfo.voidHandoverBillInfoMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
										var url =baseinfo.realPath('deleteHandoverBillInfo.action');
										var params= {
											'handoverBillInfoVo' : {
												'ids' : new Array(record.data.id)
											}
										};
										//baseinfo.handoverBillInfo.i18n('foss.baseinfo.void')成功
										var successFun = function (response) {
													//var json = Ext.decode(response.responseText);
													// 新增成功
													Ext.ux.Toast.msg('FOSS提醒您', '删除成功!');
													// 重新加载
													Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content').getHandoverBillInfoGrid().getPagingToolbar().moveFirst();
													 
												};
										var failureFun = function(json) {
													 if (Ext.isEmpty(json)) {
														 baseinfo.showErrorMes(baseinfo.handoverBillInfo.i18n('foss.baseinfo.requestTimeout'));// 请求超时
													 } else {
														 baseinfo.showErrorMes(json.message);
													}
										};
								 baseinfo.requestJsonAjax(url, params, successFun,failureFun);
								}
							}
						});
					}
				}
			]
		},{     
			xtype : 'ellipsiscolumn',
			dataIndex:'departmentName',                                                                                                        
			width:160,                                                                                                                      
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.department')//   部门信息                                       
		  },{  
		  	xtype : 'ellipsiscolumn',
			dataIndex:'automationHandoverBillNow',                                                                                                   
			width:130,                                                                                                                      
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationHandoverBillNow')// 自动交当日运单 时间                         
		},{          
			xtype : 'ellipsiscolumn',
			dataIndex:'automationHandoverBillTwo',                                                                                                     
			width:130,                                                                                                                      
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationHandoverBillTwo')// 自动交第二日运单 时间                         
		},{                                                                                                                           
			dataIndex:'automationMark',                                                                                                    
			width:100, 
			renderer : function (value) {
				if (!Ext.isEmpty(value)) {
					if(value=='Y'||value=='true'){
						return "是";
					}else{
					
					return "否";
					}
				} else {
					return value;
				}
			},
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.automationMark')//   只自动交入库的运单（Y：是，N否）               
		},{                                                                                                                           
			dataIndex:'artificialMark',                                                                                                    
			width:100,  
			renderer : function (value) {
				if (!Ext.isEmpty(value)) {
					if(value=='Y'||value=='true'){
						return "是";
					}else{
					
					return "否";
					}
				} else {
					return value;
				}
			},
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.artificialMark')//   人工可交未入库的运单（Y：是，N否）-->          
		},{           
			xtype : 'ellipsiscolumn',
			dataIndex:'createTime',                                                                                                        
			width:140,                                                                                          
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value),'Y-m-d H:i:s');
				}
			},
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.createTime')//   操作时间-->                                      
		},{                                                                                                                            
			dataIndex:'empName',                                                                                                        
			width:110,                                                                                                                      
			 header : baseinfo.handoverBillInfo.i18n('foss.baseinfo.handoverBillInfo.createUser')//  操作人                                     
		}                                                                                                                      
],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.handoverBillInfo.HandoverBillInfoStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : [{
						xtype : 'button',
						text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoAddButton'),
						hidden:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoAddButton'),
						width : 80,
						//弹出baseinfo.handoverBillInfo.i18n('foss.baseinfo.add')的外请拖头的窗口
						handler : function (mine) {
						  me.getAddUpdatehandoverBillWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.handoverBillInfo.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoCancelButton'),
						hidden:!baseinfo.handoverBillInfo.isPermission('handoverBillInfo/handoverBillInfoCancelButton'),
						width : 80,
						/**
						 * 作废 交接单管理信息
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.handoverBillInfo.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.handoverBillInfo.i18n('foss.baseinfo.voidHandoverBillInfoMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes' && !Ext.isEmpty(ids)) {
												var url = baseinfo.realPath('deleteHandoverBillInfo.action');
												var params= {
													'handoverBillInfoVo' : {
														'ids' : ids
													}
												};
												//baseinfo.handoverBillInfo.i18n('foss.baseinfo.void')成功
												var successFun = function (response) {
													//var json = Ext.decode(response.responseText);
													// 新增成功
													Ext.ux.Toast.msg('FOSS提醒您', '删除成功!');
													// 重新加载
													Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content').getHandoverBillInfoGrid().getPagingToolbar().moveFirst();
													 
												};
												var failureFun = function(json) {
													 if (Ext.isEmpty(json)) {
														 baseinfo.showErrorMes(baseinfo.handoverBillInfo.i18n('foss.baseinfo.requestTimeout'));// 请求超时
													 } else {
														 baseinfo.showErrorMes(json.message);
													}
												};
											 baseinfo.requestJsonAjax(url, params, successFun,failureFun);
												
										}
									}
								});
							} else {
								Ext.MessageBox.show({
									title : baseinfo.handoverBillInfo.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.handoverBillInfo.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					}]
			}
		];
		me.callParent([cfg]);
	}
 });
/**----------------------------------------------交单管理*Grid*end----------------------------------**/

/**----------------------------------------------交单管理**基础资料----------------------------------**/
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-handoverBillInfoIndex_content')) {
		return;
	};
	var handoverBillInfoQureyForm=Ext.create('Foss.baseinfo.handoverBillInfo.QueryHandoverBillInfoForm');
	var handoverBillInfoGrid=Ext.create('Foss.baseinfo.handoverBillInfo.HandoverBillInfoGrid');
	Ext.getCmp('T_baseinfo-handoverBillInfoIndex').add(
	Ext.create('Ext.panel.Panel',{
	id:'T_baseinfo-handoverBillInfoIndex_content',
	cls:'panelContentNToolbar',
	bodyCls:'panelContentNToolbar-body',
	getHandoverBillInfoQueryForm:function(){
	 return handoverBillInfoQureyForm;
	},
	getHandoverBillInfoGrid:function(){
	 return handoverBillInfoGrid;
	},
	items:[handoverBillInfoQureyForm,handoverBillInfoGrid]
	}));
});