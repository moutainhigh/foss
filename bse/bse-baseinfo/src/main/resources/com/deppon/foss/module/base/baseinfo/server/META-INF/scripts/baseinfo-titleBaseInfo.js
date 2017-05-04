/**
 * 仓库预警短信接收岗位基础资料 -zjf -130566
 */
/**
 * 数据模型
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.DataModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'transferCenterCode',//外场编码
		type:'string'
	},{
		name:'transferCenterName',//外场名称
		type:'string'
	},{
		name:'receiveDeptCode',//接收部门编码
		type:'string'
	},{
		name:'receiveDeptName',//接收部门名称
		type:'string'
	},{
		name:'receiveTitleCode',//接收岗位编码
		type:'string'
	},{
		name:'receiveTitleName',//接收岗位名称
		type:'string'
	}]
});
/**
 * 缓存Store
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.DataStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.outFieldOrgPositionMapping.DataModel',
	pageSize:20,
	proxy:{
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryTitleBaseInfo.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'titleBaseInfoVo.titleBaseInfoEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * 查询Form
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.QueryForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.titleBaseInfo.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 100,
		width : 120
	},
	height :160,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items =[{
			fieldLabel:'外场',
			xtype:'dynamicorgcombselector',
			transferCenter : 'Y',// 查询外场 配置此值
			type:'ORG',
			name:'transferCenterCode',
			columnWidth:0.33
		},{
			fieldLabel:'接收部门',
			xtype:'dynamicorgcombselector',
			type:'ORG',
			name:'receiveDeptCode',
			columnWidth:0.33
		},{
			fieldLabel:'接收岗位',
			xtype:'commonDynamicTitleSelector',
			name:'receiveTitleCode',
			columnWidth:0.33
		},{
			xtype:'container' ,
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.titleBaseInfo.i18n('foss.baseinfo.reset'),//重置
				  //hidden:!baseinfo.titleBaseInfo.isPermission(''),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.titleBaseInfo.i18n('foss.baseinfo.query'),//查询
				  //hidden:!baseinfo.titleBaseInfo.isPermission(''),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					  if(me.getForm().isValid()){
						  me.up().getQueryGrid().getPagingToolbar().moveFirst();
					  }
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}	
});
/**
 * 查询Grid
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.QueryGrid',{
	extend:'Ext.grid.Panel',
	title : baseinfo.titleBaseInfo.i18n('foss.baseinfo.queryResults'),// 查询条件
	frame : true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.titleBaseInfo.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//表格分页工具插件
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	//表格多选框插件
	selModel:Ext.create('Ext.selection.CheckboxModel',{//多选框
		mode:'MULTI',
		checkOnly:true
	}),
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.outFieldOrgPositionMapping.QueryDataPanel' // 行体内容
		}
	],
	addDataWin:null,
	getAddDataWin:function(){
		if(this.addDataWin==null){
			this.addDataWin =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.AddNewDataWindow');
			this.addDataWin.parent =this;
		}
		return this.addDataWin;
	},
	updateDataWin:null,
	getUpdateDataWin:function(){
		if(this.updateDataWin==null){
			this.updateDataWin =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.UpdateDataWindow');
			this.updateDataWin.parent =this;
		}
		return this.updateDataWin;
	},
	constructor:function(config){
		var me =this,cfg=Ext.apply({},config);
		me.columns=[{
					xtype:'rownumberer',
					text:'序号',
					width:40,
				},{
					xtype:'actioncolumn',
					align:'center',
					text:'',
					items:[{
						iconCls: 'deppon_icons_edit',
						tooltip:baseinfo.titleBaseInfo.i18n('foss.baseinfo.edit'),//编辑
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var rowModel =grid.getStore().getAt(rowIndex);
							me.getUpdateDataWin().updateDataModel =rowModel;
							me.getUpdateDataWin().show();
						}
					},{
						iconCls: 'deppon_icons_cancel',
						tooltip:baseinfo.titleBaseInfo.i18n('foss.baseinfo.void'),//作废
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var rowModel =grid.getStore().getAt(rowIndex);
							var ids =new Array();
							ids.push(rowModel.data.id);
							//判断是否要作废
							baseinfo.showQuestionMes(baseinfo.titleBaseInfo.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
								if(e=='yes'){
									Ext.Ajax.request({
										jsonData:{'titleBaseInfoVo':{'codeList':ids}},
										url:baseinfo.realPath('deleteTitleBaseInfo.action'),
										success:function(response){
											var json =Ext.decode(response.responseText);
											baseinfo.showInfoMes(json.message);
						    				me.getPagingToolbar().moveFirst();
										},
										exception:function(response){
											var json =Ext.decode(response.responseText);
											baseinfo.showErrorMes(json.message);
										}
									});
								}
							});
						}
					}],
			},{
				text:'外场',
				dataIndex:'transferCenterName',
				width:220,
			},{
				text:'外场编码',
				dataIndex:'transferCenterCode',
				width:220,
				hidden:true
			},{
				text:'接收部门',
				dataIndex:'receiveDeptName',
				width:220,
			},{
				text:'接收部门编码',
				dataIndex:'receiveDeptCode',
				width:220,
				hidden:true
			},{
				text:'接收岗位',
				dataIndex:'receiveTitleName',
				width:220,
			},{
				text:'接收岗位编码',
				dataIndex:'receiveTitleCode',
				width:220,
				hidden:true
			}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.store =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.DataStore',{
			autoLoad:false,
			pageSize:20,
			listeners:{
				beforeload:function(store,operation,eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-outFieldOrgPositionMapping_content').getQueryForm();
					if(queryForm !=null){
						var queryParams =queryForm.getForm().getValues();	
						Ext.apply(operation,{
							params:{
								'titleBaseInfoVo.titleBaseInfoEntity.transferCenterCode':queryParams.transferCenterCode,
								'titleBaseInfoVo.titleBaseInfoEntity.receiveDeptCode':queryParams.receiveDeptCode,
								'titleBaseInfoVo.titleBaseInfoEntity.receiveTitleCode':queryParams.receiveTitleCode
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			text:'新增',
			handler:function(){
				me.getAddDataWin().show();
			}
		},{
			text:'删除',
			handler:function(){
				var selections = me.getSelectionModel().getSelection();
				var ids =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes('请至少选择一条数据进行删除！');
					return ;
				}
				for ( var i = 0; i < selections.length; i++) {
					ids.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.titleBaseInfo.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'titleBaseInfoVo':{'codeList':ids}},
							url:baseinfo.realPath('deleteTitleBaseInfo.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showInfoMes(json.message);
			    				me.getPagingToolbar().moveFirst();
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				});
			}
		}];
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store=me.store;
		me.callParent([cfg]);
	}
});
/**
 * 新增窗口window/panel
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.AddNewDataWindow',{
	extend:'Ext.window.Window',
	title : '新增/修改接收岗位信息',//
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	width :700,
	height :'auto',
	addNewDataForm:null,
	getAddNewDataForm:function(){
		if(this.addNewDataForm==null){
			this.addNewDataForm =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.AddAndUpdateDataForm');
		}
		return this.addNewDataForm;
	},
	listeners:{
		beforehide:function(me){
			me.getAddNewDataForm().getForm().reset();
			me.getAddNewDataForm().getForm().findField('receiveTitleCode').clearValue();//resetOriginalValue()/initComponent()
		}
	},
	constructor:function(config){
		var me =this, cfg=Ext.apply({},config);
		me.items=[me.getAddNewDataForm()];
		me.fbar =[{
			text:'取消',
			width:80,
			handler:function(){
				me.close();
			}
		},{
			text:'重置',
			width:80,
			handler:function(){
				me.getAddNewDataForm().getForm().reset();
				me.getAddNewDataForm().getForm().getFields().each(function(item){
					if(item.readOnly){
						item.setReadOnly(false);
					}
				});
			}
		},{
			text:'保存',
			width:80,
			handler:function(){
				if(me.getAddNewDataForm().getForm().isValid()){
					var model =new Foss.baseinfo.outFieldOrgPositionMapping.DataModel();
					me.getAddNewDataForm().getForm().updateRecord(model);
					var transferCenterName=me.getAddNewDataForm().getForm().findField('transferCenterCode').rawValue;
					var receiveDeptName=me.getAddNewDataForm().getForm().findField('receiveDeptCode').rawValue;
					var receiveTitleName=me.getAddNewDataForm().getForm().findField('receiveTitleCode').rawValue;
					model.set('transferCenterName',transferCenterName);
					model.set('receiveDeptName',receiveDeptName);
					model.set('receiveTitleName',receiveTitleName);
					var params ={'titleBaseInfoVo':{'titleBaseInfoEntity':model.data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('addTitleBaseInfo.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-outFieldOrgPositionMapping_content').getQueryGrid().getPagingToolbar().moveFirst();
							me.close();
						},
						exception:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showErrorMes(json.message);
						}
					});
				}
			}
		}];
		me.callParent([cfg]);
	}
});
//修改窗口
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.UpdateDataWindow',{
	extend:'Ext.window.Window',
	title : '新增/修改接收岗位信息',//
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	width :700,
	height :'auto',
	updateDataModel:null,
	updateDataForm:null,
	getUpdateDataForm:function(){
		if(this.updateDataForm==null){
			this.updateDataForm =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.AddAndUpdateDataForm');
		}
		return this.updateDataForm;
	},
	listeners:{
		beforeshow:function(me){
			if(!Ext.isEmpty(me.updateDataModel)){
				me.getUpdateDataForm().getForm().loadRecord(me.updateDataModel);
				me.getUpdateDataForm().getForm().findField('transferCenterCode').setCombValue(me.updateDataModel.get('transferCenterName'),me.updateDataModel.get('transferCenterCode'));
				me.getUpdateDataForm().getForm().findField('receiveDeptCode').setCombValue(me.updateDataModel.get('receiveDeptName'),me.updateDataModel.get('receiveDeptCode'));
				me.getUpdateDataForm().getForm().findField('receiveTitleCode').setCombValue(me.updateDataModel.get('receiveTitleName'),me.updateDataModel.get('receiveTitleCode'));
				me.getUpdateDataForm().orgCode =me.updateDataModel.get('receiveDeptCode');
			}
		},
		beforehide:function(me){
			me.getUpdateDataForm().getForm().reset();
		}
	},
	constructor:function(config){
		var me =this, cfg=Ext.apply({},config);
		me.items=[me.getUpdateDataForm()];
		me.fbar =[{
			text:'取消',
			width:80,
			handler:function(){
				me.close();
			}
		},{
			text:'重置',
			width:80,
			handler:function(){
				me.getUpdateDataForm().getForm().reset();
				me.getUpdateDataForm().getForm().getFields().each(function(item){
					if(item.readOnly){
						item.setReadOnly(false);
					}
				});
			}
		},{
			text:'保存',
			width:80,
			handler:function(){
				if(me.getUpdateDataForm().getForm().isValid()){
					var model =new Foss.baseinfo.outFieldOrgPositionMapping.DataModel();
					me.getUpdateDataForm().getForm().updateRecord(model);
					var transferCenterName=me.getUpdateDataForm().getForm().findField('transferCenterCode').rawValue;
					var receiveDeptName=me.getUpdateDataForm().getForm().findField('receiveDeptCode').rawValue;
					var receiveTitleName=me.getUpdateDataForm().getForm().findField('receiveTitleCode').rawValue;
					model.set('transferCenterName',transferCenterName);
					model.set('receiveDeptName',receiveDeptName);
					model.set('receiveTitleName',receiveTitleName);
					model.set('id',me.updateDataModel.get('id'));
					var params ={'titleBaseInfoVo':{'titleBaseInfoEntity':model.data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('updateTitleBaseInfo.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-outFieldOrgPositionMapping_content').getQueryGrid().getPagingToolbar().moveFirst();
							me.close();
						},
						exception:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showErrorMes(json.message);
						}
					});
				}
			}
		}];
		me.callParent([cfg]);
	}
});
//查询详细信息面板
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.QueryDataPanel', {
	extend : 'Ext.panel.Panel',
	title : '仓库预警短信接收岗位详细信息',
	frame : true,
	height :130,
	informationForm : null,
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.ShowDataForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
	}
});
/**
 * 新增/修改Form
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.AddAndUpdateDataForm',{
	extend:'Ext.form.Panel',
	title : '',// 
	frame : true,
	collapsible : true,
	margin:'0 5 0 5',
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 80,
		width : 100
	},
	height :'auto',
	defaultType : 'textfield',
	layout: 'column',
	orgCode:null,//用于接收部门与岗位之间传值
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'外场',
			xtype:'dynamicorgcombselector',
			transferCenter : 'Y',// 查询外场 配置此值
			type:'ORG',
			columnWidth:0.35,
			name:'transferCenterCode',
		},{
			fieldLabel:'接收部门',
			xtype:'dynamicorgcombselector',
			type:'ORG',
			columnWidth:0.35,
			name:'receiveDeptCode',
			listeners:{
				select:function(comb,records,eOpts){
				if(!Ext.isEmpty(records[0].data)){
						me.orgCode =records[0].data.code;
					}
				}
			}
		},{
			fieldLabel:'接收岗位',
			xtype:'commonDynamicTitleSelector',
			columnWidth:0.35,
			name:'receiveTitleCode',
			queryMode: 'local', //查询模式： 默认‘remote’
			listeners:{
				expand:function(_this,eOpts){
					var receiveDeptCode=me.getForm().findField('receiveDeptCode').getValue();
					if(Ext.isEmpty(receiveDeptCode)){
						//提示用户
						baseinfo.showInfoMes('请先选取接收部门！');
						me.getForm().findField('receiveTitleCode').setValue('');
					}else{
						_this.store.load({
							params:{
								'objectVo.entity.orgCode':me.orgCode
							},
							callback: function(records, operation, success) {
						        // 对象 operation 包含
						        // 所有 load 操作的详细信息
						        console.log(records);
						    }
						});
					}
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查看Form
 */
Ext.define('Foss.baseinfo.outFieldOrgPositionMapping.ShowDataForm',{
	extend:'Ext.form.Panel',
	height : 300,
	width : 800,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : 'column',
	items:[{
		fieldLabel:'外场',
		readOnly:true,
		columnWidth:0.35,
		name:'transferCenterName'
	},{
		fieldLabel:'接收部门',
		readOnly:true,
		columnWidth:0.35,
		name:'receiveDeptName'
	},{
		fieldLabel:'接收岗位',
		readOnly:true,
		columnWidth:0.35,
		name:'receiveTitleName'
	}]
});
/**
 * 界面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-outFieldOrgPositionMapping_content')){
		return;
	}
	var queryForm =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.QueryForm');
	var queryGrid =Ext.create('Foss.baseinfo.outFieldOrgPositionMapping.QueryGrid');
	Ext.getCmp('T_baseinfo-titleBaseInfo').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-outFieldOrgPositionMapping_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getQueryGrid : function() {
			return queryGrid;
		},
		items: [queryForm,queryGrid]
	}));
});