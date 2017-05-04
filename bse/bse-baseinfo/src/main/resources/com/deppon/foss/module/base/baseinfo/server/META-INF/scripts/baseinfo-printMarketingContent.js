/**
 * 打印营销内容岗位基础资料 
 * dujunhui-187862
 */

/**
 * 数据Model
 */
Ext.define('Foss.baseinfo.printMarketingContent.DataModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',//ID
		type:'string'
	},{
		name:'cityCode',//城市编码
		type:'string'
	},{
		name:'cityName',//城市名称
		type:'string'
	},{
		name:'cityPattern',//城市类型
		type:'string'
	},{
		name:'content',//营销内容
		type:'string'
	}]
});
/**
 * 缓存Store
 */
Ext.define('Foss.baseinfo.printMarketingContent.DataStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.printMarketingContent.DataModel',
	pageSize:20,
	proxy:{
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryPrintMarketingContent.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'printMarketingContentVo.printMarketingContentEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * 查询Form
 */
Ext.define('Foss.baseinfo.printMarketingContent.QueryForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.printMarketingContent.i18n('foss.baseinfo.queryCondition'),// 查询条件
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
			fieldLabel:'城市',
			xtype:'commoncityselector',
			name:'cityCode',
			forceSelection:true,
			columnWidth:0.33
		},{
			columnWidth:0.33,
			name: 'cityPattern',
			fieldLabel:'城市类型',
	        queryMode: 'local',
	        displayField: 'name',
	        valueField: 'value',
	        xtype:'combo',
	        editable:false,
	        store:Ext.create('Ext.data.Store', {
	            fields: ['value', 'name'],
	            data : [
	                {"value":"DEPARTURE_CITY", "name":"出发城市"},
	                {"value":"ARRIVAL_CITY", "name":"到达城市"}
	            ]
	        })
		},{
			fieldLabel:'营销内容',
			xtype:'textfield',
			maxLength:10,
			maxLengthText: "输入查询内容过长",
			name:'content',
			columnWidth:0.33
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.8
		},{
//			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.printMarketingContent.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/resetButton'),
				  hidden:!baseinfo.printMarketingContent.isPermission('printMarketingContent/resetButton'),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.printMarketingContent.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/queryButton'),
				  hidden:!baseinfo.printMarketingContent.isPermission('printMarketingContent/queryButton'),
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
Ext.define('Foss.baseinfo.printMarketingContent.QueryGrid',{
	extend:'Ext.grid.Panel',
	title : baseinfo.printMarketingContent.i18n('foss.baseinfo.queryResults'),// 查询条件
	frame : true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.printMarketingContent.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
			rowBodyElement : 'Foss.baseinfo.printMarketingContent.QueryDataPanel' // 行体内容
		}
	],
	addDataWin:null,
	getAddDataWin:function(){
		if(this.addDataWin==null){
			this.addDataWin =Ext.create('Foss.baseinfo.printMarketingContent.AddNewDataWindow');
			this.addDataWin.parent =this;
		}
		return this.addDataWin;
	},
	updateDataWin:null,
	getUpdateDataWin:function(){
		if(this.updateDataWin==null){
			this.updateDataWin =Ext.create('Foss.baseinfo.printMarketingContent.UpdateDataWindow');
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
						tooltip:baseinfo.printMarketingContent.i18n('foss.baseinfo.edit'),//编辑
						disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/editButton'),
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var rowModel =grid.getStore().getAt(rowIndex);
							me.getUpdateDataWin().updateDataModel =rowModel;
							me.getUpdateDataWin().show();
						}
					},{
						iconCls: 'deppon_icons_cancel',
						tooltip:baseinfo.printMarketingContent.i18n('foss.baseinfo.void'),//作废
						disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/voidButton'),
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var rowModel =grid.getStore().getAt(rowIndex);
							var ids =new Array();
							ids.push(rowModel.data.id);
							//判断是否要作废
							baseinfo.showQuestionMes(baseinfo.printMarketingContent.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
								if(e=='yes'){
									Ext.Ajax.request({
										jsonData:{'printMarketingContentVo':{'codeList':ids}},
										url:baseinfo.realPath('deletePrintMarketingContent.action'),
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
				text:'城市名',
				dataIndex:'cityName',
				width:220
			},{
				text:'城市类型',
				dataIndex:'cityPattern',
				renderer: function (value) {
					if (value == 'DEPARTURE_CITY') { //'DEPARTURE_CITY'表示出发城市
						return baseinfo.printMarketingContent.i18n('foss.baseinfo.departureCity');
					} else if (value == 'ARRIVAL_CITY') { //'ARRIVAL_CITY'表示到达城市
						return baseinfo.printMarketingContent.i18n('foss.baseinfo.arrivalCity'); 
					} else {
						return value;
					}
				},
				width:220
			},{
				text:'营销内容',
				dataIndex:'content',
				width:450
			}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.store =Ext.create('Foss.baseinfo.printMarketingContent.DataStore',{
			autoLoad:false,
			pageSize:20,
			listeners:{
				beforeload:function(store,operation,eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-printMarketingContent_content').getQueryForm();
					if(queryForm !=null){
						var queryParams =queryForm.getForm().getValues();	
						Ext.apply(operation,{
							params:{
								'printMarketingContentVo.printMarketingContentEntity.cityCode':queryParams.cityCode,
								'printMarketingContentVo.printMarketingContentEntity.cityPattern':queryParams.cityPattern,
								'printMarketingContentVo.printMarketingContentEntity.content':queryParams.content
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			text:'新增',
			disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/addButton'),
			hidden:!baseinfo.printMarketingContent.isPermission('printMarketingContent/addButton'),
			handler:function(){
				me.getAddDataWin().show();
			}
		},{
			text:'作废',
			disabled:!baseinfo.printMarketingContent.isPermission('printMarketingContent/delButton'),
			hidden:!baseinfo.printMarketingContent.isPermission('printMarketingContent/delButton'),
			handler:function(){
				var selections = me.getSelectionModel().getSelection();
				var ids =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes('请至少选择一条数据进行作废！');
					return ;
				}
				for ( var i = 0; i < selections.length; i++) {
					ids.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.printMarketingContent.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'printMarketingContentVo':{'codeList':ids}},
							url:baseinfo.realPath('deletePrintMarketingContent.action'),
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
Ext.define('Foss.baseinfo.printMarketingContent.AddNewDataWindow',{
	extend:'Ext.window.Window',
	title : '新增/修改营销内容信息',//
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
			this.addNewDataForm =Ext.create('Foss.baseinfo.printMarketingContent.AddAndUpdateDataForm');
		}
		return this.addNewDataForm;
	},
	listeners:{
		beforehide:function(me){
			me.getAddNewDataForm().getForm().reset();
//			me.getAddNewDataForm().getForm().findField('receiveTitleCode').clearValue();
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
					var model =new Foss.baseinfo.printMarketingContent.DataModel();
					me.getAddNewDataForm().getForm().updateRecord(model);
					var cityName=me.getAddNewDataForm().getForm().findField('cityCode').rawValue;
					model.set('cityName',cityName);
					var params ={'printMarketingContentVo':{'printMarketingContentEntity':model.data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('addPrintMarketingContent.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-printMarketingContent_content').getQueryGrid().getPagingToolbar().moveFirst();
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
Ext.define('Foss.baseinfo.printMarketingContent.UpdateDataWindow',{
	extend:'Ext.window.Window',
	title : '新增/修改营销内容信息',//
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
			this.updateDataForm =Ext.create('Foss.baseinfo.printMarketingContent.AddAndUpdateDataForm');
		}
		return this.updateDataForm;
	},
	listeners:{
		beforeshow:function(me){
			if(!Ext.isEmpty(me.updateDataModel)){
				me.getUpdateDataForm().getForm().loadRecord(me.updateDataModel);
				me.getUpdateDataForm().getForm().findField('cityCode').setCombValue(me.updateDataModel.get('cityName'),me.updateDataModel.get('cityCode'));
				me.getUpdateDataForm().getForm().findField('cityPattern').setValue(me.updateDataModel.get('cityPattern'));
				me.getUpdateDataForm().getForm().findField('content').setValue(me.updateDataModel.get('content'));
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
					var model =new Foss.baseinfo.printMarketingContent.DataModel();
					me.getUpdateDataForm().getForm().updateRecord(model);
					var cityName=me.getUpdateDataForm().getForm().findField('cityCode').rawValue;
					model.set('cityName',cityName);
					model.set('id',me.updateDataModel.get('id'));
					var params ={'printMarketingContentVo':{'printMarketingContentEntity':model.data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('updatePrintMarketingContent.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-printMarketingContent_content').getQueryGrid().getPagingToolbar().moveFirst();
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
Ext.define('Foss.baseinfo.printMarketingContent.QueryDataPanel', {
	extend : 'Ext.panel.Panel',
	title : '营销内容详细信息',
	frame : true,
	height :160,
	informationForm : null,
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.printMarketingContent.ShowDataForm');
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
		if (record.data.cityPattern =='DEPARTURE_CITY' ) { //'DEPARTURE_CITY'表示出发城市
			record.set('cityPattern',baseinfo.printMarketingContent.i18n('foss.baseinfo.departureCity'));
		} else if (record.data.cityPattern == 'ARRIVAL_CITY') { //'ARRIVAL_CITY'表示到达城市
			record.set('cityPattern',baseinfo.printMarketingContent.i18n('foss.baseinfo.arrivalCity')); 
		}
		this.getInformationForm().getForm().loadRecord(record);
	}
});
/**
 * 新增/修改Form
 */
Ext.define('Foss.baseinfo.printMarketingContent.AddAndUpdateDataForm',{
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
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'城市',
			xtype:'commoncityselector',
			columnWidth:0.35,
			forceSelection:true,
			allowBlank:false,
			name:'cityCode',
		},{
			columnWidth:0.35,
			name: 'cityPattern',
			fieldLabel:'城市类型',
			allowBlank:false,
	        queryMode: 'local',
	        displayField: 'name',
	        valueField: 'value',
	        xtype:'combo',
	        editable:false,
	        store:Ext.create('Ext.data.Store', {
	            fields: ['value', 'name'],
	            data : [
	                {"value":"DEPARTURE_CITY", "name":"出发城市"},
	                {"value":"ARRIVAL_CITY", "name":"到达城市"}
	            ]
	        })
		},{
			fieldLabel:'营销内容',
			xtype:'textareafield',
			columnWidth:0.55,
			allowBlank:false,
			name:'content'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查看Form
 */
Ext.define('Foss.baseinfo.printMarketingContent.ShowDataForm',{
	extend:'Ext.form.Panel',
	height : 140,
	width : 800,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : 'column',
	items:[{
		fieldLabel:'城市',
		readOnly:true,
		columnWidth:0.35,
		name:'cityName'
	},{
		fieldLabel:'城市类型',
		readOnly:true,
		columnWidth:0.35,
		name:'cityPattern'
	},{
		fieldLabel:'营销内容',
		readOnly:true,
		xtype:'textareafield',
		columnWidth:0.55,
		name:'content'
	}]
});
/**
 * 界面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-printMarketingContent_content')){
		return;
	}
	var queryForm =Ext.create('Foss.baseinfo.printMarketingContent.QueryForm');
	var queryGrid =Ext.create('Foss.baseinfo.printMarketingContent.QueryGrid');
	Ext.getCmp('T_baseinfo-printMarketingContent').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-printMarketingContent_content',
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