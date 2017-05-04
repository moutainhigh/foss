//model
Ext.define('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'satelliteDeptCode',
		type:'string'
	},{
		name:'satelliteDeptName',
		type:'string'
	},{
		name:'salesDeptCode',
		type:'string'
	},{
		name:'salesDeptName',
		type:'string'
	},{
		name:'active',
		type:'string'
	}]
});
//store
Ext.define('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('querySatellitePartSalesList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.satellitePartSalesList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
//查询表单
Ext.define('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptQueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.queryCondition'),
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :150,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[{
				xtype:'commonsaledepartmentselector',
				name:'salesDeptCode',
				fieldLabel:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.salesDept'), //营业部
				columnWidth:0.33,
			},{
				xtype:'commonsaledepartmentselector',
				name:'satelliteDeptCode',
				fieldLabel:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.satellite'),//卫星点部
				satelliteDept:'Y',
				columnWidth:0.33,
			},{
				xtype:'container', 
				columnWidth:0.33,
			},{
				xtype:'container',
				columnWidth:1, 
				defaultType:'button',
				layout:'column',
				items:[{
					text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.reset'),//重置
					disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/queryButton'),
					hidden:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/queryButton'),
					columnWidth:.08,
					handler : function() {
						me.getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
					text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.query'),//查询
					disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/queryButton'),
					hidden:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/queryButton'),
					columnWidth:.08,
					handler : function() {
						me.up().getGrid().getPagingToolbar().moveFirst();
					}
				}]
			}];
		me.callParent([cfg]);
	}
});
//列表
Ext.define('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.queryGrid'),
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar,分页
	pagingToolbar : null,
	getPagingToolbar :function(){
		if(Ext.isEmpty(this.pagingToolbar)){
			this.pagingToolbar =Ext.create('Deppon.StandardPaging',{
				store:this.store,
				pageSize:10,
			});
		}
		return this.pagingToolbar;
	},
	//新增窗口
	valueAddWindow:null,
	getAddWindow:function(){
		if(this.valueAddWindow == null){
			this.valueAddWindow =Ext.create('Foss.baseinfo.satellitePartSalesDept.AddValueWidow');
			this.valueAddWindow.parent = this;
		}
		return this.valueAddWindow;
	},
	//修改窗口
	updateWindow:null,
	getUpdateWidow:function(){
		if(this.updateWindow == null){
			this.updateWindow =Ext.create('Foss.baseinfo.satellitePartSalesDept.updateValueWidow');
			this.updateWindow.parent = this;
		}
		return this.updateWindow;
	},
	constructor:function(config){
		var me =this,cfg= Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.operate'),//操作
			items:[{
					iconCls: 'deppon_icons_edit',
					tooltip:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.edit'),//编辑
					disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/updateButton'),
					width:30,
					handler:function(grid,rowIndex,colIndex){
						var record =grid.getStore().getAt(rowIndex);
						var updateWin =me.getUpdateWidow();
						updateWin.satellitePartSalesDeptModel =new Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptModel(record.data);
						updateWin.show();
					}
				},{
					iconCls: 'deppon_icons_cancel',
					tooltip:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.void'),//作废
					disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/deleteButton'),
					//disabled:!baseinfo.satellitePartSalesDept.isPermission(''),
					width:30,
					handler:function(grid,rowIndex,colIndex){
					 var record =grid.getStore().getAt(rowIndex);
					 	//判断是否要作废
						baseinfo.showQuestionMes(baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
							if(e=='yes'){
								Ext.Ajax.request({
									params:{'vo.satellitePartSalesDeptEntity.id':record.get('id')},
									url:baseinfo.realPath('deleteSatellitePartSalesById.action'),
									success:function(response){
										var json =Ext.decode(response.responseText);
										baseinfo.showInfoMes(json.message);
					    				grid.getStore().remove(record);
									},
									exception:function(response){
										var json =Ext.decode(response.responseText);
										baseinfo.showErrorMes(json.message);
									}
								});
							}
						});
					}
				}]
		},{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.satelliteCode'), //卫星点部编码
			dataIndex:'satelliteDeptCode',
			flex:1
		},{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.satellite'),//卫星点部名称
			dataIndex:'satelliteDeptName',
			flex:1
		},{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.salesDeptCode'),//营业部编码
			dataIndex:'salesDeptCode',
			flex:1
		},{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.salesDept'),//营业部名称
			dataIndex:'salesDeptName',
			flex:1
		}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		};
		me.store =Ext.create('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptStore',{
			autoLoad:true,
			pageSize:10,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-satellitePartSalesDept_content').getQueryForm();
					if(queryForm != null){
						var queryParams =queryForm.getValues();
						Ext.apply(operation,{
							params:{
								'vo.satellitePartSalesDeptEntity.satelliteDeptCode':queryParams.satelliteDeptCode,
								'vo.satellitePartSalesDeptEntity.salesDeptCode':queryParams.salesDeptCode,
							}
						});
					}
				}
			}
		});
		me.selModel =Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.tbar =[{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.add'),
			disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/addButton'),
			hidden:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/addButton'),
			handler:function(){
				me.getAddWindow().show();
			}
		},{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.void'),
			disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/deleteButton'),
			hidden:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/deleteButton'),
			handler:function(){
				//获取已经选中的
				var selections =me.getSelectionModel().getSelection();
				var idList =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.deleteNoticeMsg'));
		    		return;
				}
				for(var i=0;i<selections.length;i++){
					idList.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'idList':idList}},
							url:baseinfo.realPath('deleteSatellitePartSalesByIdList.action'),
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
		me.getPagingToolbar().store =me.store;
		me.callParent([cfg]);
	},
});
//------------------widow ---------------------
//新增
Ext.define('Foss.baseinfo.satellitePartSalesDept.AddValueWidow',{
	extend:'Ext.window.Window',
	title:'新增卫星点营业部关系',
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
	height :400,
	listeners:{
		beforeshow:function(me){
			me.getAddValueForm().getSatelliteSelectArea().getDockedItems()[0].items.items[0].setDisabled(true);
		},
		beforehide:function(me){
			me.getAddValueForm().getForm().reset();
			me.getAddValueForm().getSatelliteSelectArea().getStore().removeAll();
		}
	},
	addValueForm:null,
	getAddValueForm:function(){
		if(this.addValueForm == null){
			this.addValueForm  =Ext.create('Foss.baseinfo.satellitePartSalesDept.AddUpdateValueForm',{
				isUpdate:false,
			});
		}
		return this.addValueForm;
	},
	//保存新增信息
	saveAddValue:function(){
		var me =this;
		var addStore =	me.getAddValueForm().getSatelliteSelectArea().getStore();
		var addRecordModelList =addStore.getNewRecords();
		var addSatellitePartSalesList =new Array();
		//若没有添加卫星点，不让提交
		if(addRecordModelList.length ==0){
			baseinfo.showErrorMes();
			return;
		}
		for ( var i = 0; i < addRecordModelList.length; i++) {
			addSatellitePartSalesList.push(addRecordModelList[i].data);
		}
		var params ={'vo':{'addSatellitePartSalesList':addSatellitePartSalesList}};
		Ext.Ajax.request({
			jsonData:params,
			url:baseinfo.realPath('addSatellitePartSales.action'),
			success:function(response){
				var json =Ext.decode(response.responseText);
				//新增成功
				baseinfo.showInfoMes(json.message);
				//重新加载
				Ext.getCmp('T_baseinfo-satellitePartSalesDept_content').getGrid().getPagingToolbar().moveFirst();
				//隐藏页面
				me.hide();
			},
			exception:function(response){
				var json =Ext.decode(response.responseText);
				baseinfo.showErrorMes(json.message);
			}
		});
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[me.getAddValueForm()];
		me.fbar =[{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getAddValueForm().getForm().reset();
				me.getAddValueForm().getSatelliteSelectArea().getStore().removeAll();
			} 
		},{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
				me.saveAddValue(); 
			} 
		}];
		me.callParent([cfg]);
	},
});
//修改
Ext.define('Foss.baseinfo.satellitePartSalesDept.updateValueWidow',{
	extend:'Ext.window.Window',
	title:'修改卫星点营业部关系',
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
	height :400,
	satellitePartSalesDeptModel:null,
	listeners:{
		beforeshow:function(me){
			if(me.satellitePartSalesDeptModel!=null){
				var model =me.satellitePartSalesDeptModel;
				//me.getUpdateValueForm().getForm().loadRecord(model);
				me.getUpdateValueForm().getForm().findField('salesDeptCode').setCombValue(model.get('salesDeptName'),model.get('salesDeptCode'));
				me.getUpdateValueForm().getForm().findField('salesDeptCode').setReadOnly(true);
				me.getUpdateValueForm().getSatelliteSelectArea().getStore().add(model);
				me.getUpdateValueForm().getSatelliteSelectArea().getDockedItems()[0].items.items[0].setDisabled(false);
			}
		},
		beforehide:function(me){
			me.getUpdateValueForm().getForm().reset();
			me.getUpdateValueForm().getSatelliteSelectArea().getStore().removeAll();
			me.getUpdateValueForm().getForm().findField('salesDeptCode').setReadOnly(false);
		}
	},
	updateValueForm:null,
	getUpdateValueForm:function(){
		if(this.updateValueForm == null){
			this.updateValueForm  =Ext.create('Foss.baseinfo.satellitePartSalesDept.AddUpdateValueForm',{
				isUpdate:true,
			});
		}
		return this.updateValueForm;
	},
	//保存修改信息
	saveUpdateValue:function(){
		var me =this;
		var updateStore =me.getUpdateValueForm().getSatelliteSelectArea().getStore();
		//新增列表信息
		var addRecordModelList =updateStore.getNewRecords();
		var addSatellitePartSalesList =new Array();
		for ( var i = 0; i < addRecordModelList.length; i++) {
			addSatellitePartSalesList.push(addRecordModelList[i].data);
		}
		//作废的列表信息
		var deleteRecordModelList =updateStore.getRemovedRecords();
		var deleteSatellitePartSalesList =new Array();
		for ( var i = 0; i < deleteRecordModelList.length; i++) {
			deleteSatellitePartSalesList.push(deleteRecordModelList[i].data);
		}
		var vo =new Object();
		vo.addSatellitePartSalesList =addSatellitePartSalesList;
		vo.deleteSatellitePartSalesList =deleteSatellitePartSalesList;
		var params ={'vo':vo};
		Ext.Ajax.request({
			jsonData:params,
			url:baseinfo.realPath('updateSatellitePartSales.action'),
			success:function(response){
				var json =Ext.decode(response.responseText);
				//新增成功
				baseinfo.showInfoMes(json.message);
				//重新加载
				Ext.getCmp('T_baseinfo-satellitePartSalesDept_content').getGrid().getPagingToolbar().moveFirst();
				//隐藏页面
				me.hide();
			},
			exception:function(response){
				var json =Ext.decode(response.responseText);
				baseinfo.showErrorMes(json.message);
			}
		});
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[me.getUpdateValueForm()];
		me.fbar =[{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				//先清空store
				me.getUpdateValueForm().getSatelliteSelectArea().getStore().removeAll();
				if(me.satellitePartSalesDeptModel!=null){
					var model =me.satellitePartSalesDeptModel;
					//me.getUpdateValueForm().getForm().loadRecord(model);
					me.getUpdateValueForm().getForm().findField('salesDeptCode').setCombValue(model.get('salesDeptName'),model.get('salesDeptCode'));
					me.getUpdateValueForm().getSatelliteSelectArea().getStore().add(model);
					me.getUpdateValueForm().getSatelliteSelectArea().getDockedItems()[0].items.items[0].setDisabled(false);
				}
			} 
		},{
			text : baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 0',
			handler :function(){
				me.saveUpdateValue(); 
			} 
		}];
		me.callParent([cfg]);
	},
});
//---------------------FORM-------------------------------
Ext.define('Foss.baseinfo.satellitePartSalesDept.AddUpdateValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:280,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '5 5 5 5',
		maxLength:50,
		allowBlank:false,
		labelWidth:100,
		anchor : '100%'
	},
	defaultType:'textfield',
	layout: {
        type: 'table',
        columns: 3
    },
	//layout:'column',
    addSatellitePartSalesList:null, //新增的卫星点部营业部映射的集合
    deleteSatellitePartSalesList:null,//作废卫星点部营业部映射的集合
    isupdate:false,
    satelliteSelectArea:null,
    getSatelliteSelectArea:function(){
    	if(this.satelliteSelectArea ==null){
    		this.satelliteSelectArea =Ext.create('Foss.baseinfo.satellitePartSalesDept.SatelliteSelectArea');
    	}
    	return this.satelliteSelectArea;
    },
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[{
	    		xtype:'commonsaledepartmentselector',
				name:'salesDeptCode',
				fieldLabel:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.salesDept'), //营业部
				width:280,
				colspan:3,
			},{
				xtype:'commonsaledepartmentselector',
				name:'salesDeptCode',
				fieldLabel:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.selectSatellite'), //卫星点部
				width:280,
				satelliteDept:'Y',
				allowBlank:true,
				listeners:{
					select:function(comb,records,obj){
						var salesDeptCode =me.getForm().findField('salesDeptCode').getValue();
						//若营业部取值为空的话，提示用户 先选择
						if(Ext.isEmpty(salesDeptCode)){
							baseinfo.showErrorMes('请先选择营业部');
							return;
						}
						var satellitPartSalesDeptModel =new Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptModel();
						var satelliteDeptCode =records[0].get('code');
						Ext.Ajax.request({
							params:{'vo.satellitePartSalesDeptEntity.satelliteDeptCode':satelliteDeptCode},
							url:baseinfo.realPath('querySatellitePartSaleDeptBySatelliteDeptCode.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								var satellitPartSalesDept =json.vo.satellitePartSalesDeptEntity;
								//若库中已经存在该卫星点部对应关系
								if(!Ext.isEmpty(satellitPartSalesDept)){
									baseinfo.showErrorMes('已经存在'+satellitPartSalesDept.satelliteDeptName+'映射营业部的关系！请勿再添加');
									return;
								}
								//把营业部编码添加到model中
								satellitPartSalesDeptModel.set('salesDeptCode',salesDeptCode);
								var boolean =false;
								me.getSatelliteSelectArea().getStore().each(function(record){
									//若库中已展示的grid中已经存在该卫星点，请勿重复添加
									if(record.get('satelliteDeptCode')==records[0].get('code')){
										 boolean = true;
									}
								});
								if(boolean){
									return;
								}
								satellitPartSalesDeptModel.set('satelliteDeptCode',satelliteDeptCode);
								satellitPartSalesDeptModel.set('satelliteDeptName',records[0].get('name'));
								//把选中的实体添加给库中
								me.getSatelliteSelectArea().getStore().add(satellitPartSalesDeptModel);
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				}
			},{
				text:'--->',
				xtype:'label',
				width:50
			},me.getSatelliteSelectArea()];
    	me.callParent([cfg]);
    },
});
//显示的form
Ext.define('Foss.baseinfo.satellitePartSalesDept.SatelliteSelectArea',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:260,
	height:180,
	columns:[{dataIndex:'satelliteDeptName',text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.satelliteServerSales'),flex:1}],//卫星点部名称
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		};
		me.selModel =Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.store =baseinfo.getStore(null,'Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptModel',null,[]);
		me.tbar =[{
			text:baseinfo.satellitePartSalesDept.i18n('foss.baseinfo.satellitePartSalesDept.voidSatellite'), //移除卫星点
			disabled:true,
			handler:function(){
				var selections =me.getSelectionModel().getSelection();
				if(selections.length ==0){
					baseinfo.showErrorMes('请选择要作废记录');
						return;
					}
				me.getStore().remove(selections);
				}
		}];
		me.callParent([cfg]);
	},	
});
//--------------------初始化面板---------------------------
Ext.onReady(function(){
	//tips初始化
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-satellitePartSalesDept_content')){
		return ;
	}
	var satellitePartSalesDeptQueryForm =Ext.create('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptQueryForm');
	var satellitePartSalesDeptGrid =Ext.create('Foss.baseinfo.satellitePartSalesDept.SatellitePartSalesDeptGrid');
	Ext.getCmp('T_baseinfo-satellitePartSalesDept').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-satellitePartSalesDept_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm:function(){
			return satellitePartSalesDeptQueryForm;
		},
		getGrid:function(){
			return satellitePartSalesDeptGrid;
		},
		items:[satellitePartSalesDeptQueryForm,satellitePartSalesDeptGrid]
	}));
});