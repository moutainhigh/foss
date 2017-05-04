//model
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'expressBranchCode',
		type:'string'
	},{
		name:'expressBranchName',
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
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExpressBranchSalesList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.expressBranchSalesList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
//查询表单
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.queryCondition'),
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
				fieldLabel:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.salesDept'), //营业部
				columnWidth:0.33,
			},{
				xtype:'commontransfercenterselector',
				name:'expressBranchCode',
				fieldLabel:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.expressBranch'),//快递分部
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
					text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.reset'),//重置
					disabled:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/queryButton'),
					hidden:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/queryButton'),
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
					text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.query'),//查询
					disabled:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/queryButton'),
					hidden:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/queryButton'),
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
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.queryGrid'),
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
			this.valueAddWindow =Ext.create('Foss.baseinfo.expressBranchSalesDept.AddValueWidow');
			this.valueAddWindow.parent = this;
		}
		return this.valueAddWindow;
	},
	constructor:function(config){
		var me =this,cfg= Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.operate'),//操作
			items:[{
					iconCls: 'deppon_icons_cancel',
					tooltip:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.void'),//作废
					disabled:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/deleteButton'),
					//disabled:!baseinfo.expressBranchSalesDept.isPermission(''),
					width:30,
					handler:function(grid,rowIndex,colIndex){
					 var record =grid.getStore().getAt(rowIndex);
					 	//判断是否要作废
						baseinfo.showQuestionMes(baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
							if(e=='yes'){
								Ext.Ajax.request({
									params:{'vo.expressBranchSalesDeptEntity.id':record.get('id')},
									url:baseinfo.realPath('deleteExpressBranchSalesById.action'),
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
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.expressBranchCode'), //快递分部编码
			dataIndex:'expressBranchCode',
			flex:1
		},{
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.expressBranch'),//快递分部名称
			dataIndex:'expressBranchName',
			flex:1
		},{
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.salesDeptCode'),//营业部编码
			dataIndex:'salesDeptCode',
			flex:1
		},{
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.salesDept'),//营业部名称
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
		me.store =Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptStore',{
			autoLoad:true,
			pageSize:10,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getQueryForm();
					if(queryForm != null){
						var queryParams =queryForm.getValues();
						Ext.apply(operation,{
							params:{
								'vo.expressBranchSalesDeptEntity.expressBranchCode':queryParams.expressBranchCode,
								'vo.expressBranchSalesDeptEntity.salesDeptCode':queryParams.salesDeptCode,
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
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.add'),
			disabled:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/addButton'),
			hidden:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/addButton'),
			handler:function(){
				me.getAddWindow().show();
			}
		},{
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.void'),
			disabled:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/deleteButton'),
			hidden:!baseinfo.expressBranchSalesDept.isPermission('expressBranchSalesDept/deleteButton'),
			handler:function(){
				//获取已经选中的
				var selections =me.getSelectionModel().getSelection();
				var idList =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.deleteNoticeMsg'));
		    		return;
				}
				for(var i=0;i<selections.length;i++){
					idList.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'idList':idList}},
							url:baseinfo.realPath('deleteExpressBranchSalesByIdList.action'),
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
Ext.define('Foss.baseinfo.expressBranchSalesDept.AddValueWidow',{
	extend:'Ext.window.Window',
	title:'新增快递分部营业部关系',
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
//			me.getAddValueForm().getExpressBranchSelectArea().getDockedItems()[0].items.items[0].setDisabled(true);
		},
		beforehide:function(me){
			me.getAddValueForm().getForm().reset();
			me.getAddValueForm().getExpressBranchSelectArea().getStore().removeAll();
		}
	},
	addValueForm:null,
	getAddValueForm:function(){
		if(this.addValueForm == null){
			this.addValueForm  =Ext.create('Foss.baseinfo.expressBranchSalesDept.AddUpdateValueForm',{
				isUpdate:false,
			});
		}
		return this.addValueForm;
	},
	//保存新增信息
	saveAddValue:function(){
		var me =this;
		var addStore =	me.getAddValueForm().getExpressBranchSelectArea().getStore();
		var addRecordModelList =addStore.getNewRecords();
		var addExpressBranchSalesList =new Array();
		//若没有添加快递分部，不让提交
		if(addRecordModelList.length ==0){
			baseinfo.showErrorMes("请选择快递分部");
			return;
		}
		for ( var i = 0; i < addRecordModelList.length; i++) {
			addExpressBranchSalesList.push(addRecordModelList[i].data);
		}
		var params ={'vo':{'addExpressBranchSalesList':addExpressBranchSalesList}};
		Ext.Ajax.request({
			jsonData:params,
			url:baseinfo.realPath('addExpressBranchSales.action'),
			success:function(response){
				var json =Ext.decode(response.responseText);
				//新增成功
				baseinfo.showInfoMes(json.message);
				//重新加载
				Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getGrid().getPagingToolbar().moveFirst();
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
			text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getAddValueForm().getForm().reset();
				me.getAddValueForm().getExpressBranchSelectArea().getStore().removeAll();
			} 
		},{
			text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
				me.saveAddValue(); 
			} 
		}];
		me.callParent([cfg]);
	},
});

//---------------------FORM-------------------------------
Ext.define('Foss.baseinfo.expressBranchSalesDept.AddUpdateValueForm',{
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
    addExpressBranchSalesList:null, //新增的快递分部营业部映射的集合
    deleteExpressBranchSalesList:null,//作废快递分部营业部映射的集合
    isupdate:false,
    expressBranchSelectArea:null,
    getExpressBranchSelectArea:function(){
    	if(this.expressBranchSelectArea ==null){
    		this.expressBranchSelectArea =Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSelectArea');
    	}
    	return this.expressBranchSelectArea;
    },
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[{
	    		xtype:'commonsaledepartmentselector',
				name:'salesDeptCode',
				fieldLabel:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.salesDept'), //营业部
				width:280,
				colspan:3,
				listeners:{
					select:function(comb,records,obj){
						if(me.getExpressBranchSelectArea().getStore().data.items.length>0){
							me.getExpressBranchSelectArea().getStore().getAt(0).data.salesDeptCode=records[0].get('code');
							me.getExpressBranchSelectArea().getStore().getAt(0).data.salesDeptName=records[0].get('name');
						}
					}
				}
			},{
				xtype:'commontransfercenterselector',
				name:'expressBranchCode',
				fieldLabel:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.selectExpressBranch'), //快遞分部
				width:280,
				allowBlank:true,
				listeners:{
					select:function(comb,records,obj){
						var salesDeptCode =me.getForm().findField('salesDeptCode').getValue();
						var salesDeptName=me.getForm().findField('salesDeptCode').getRawValue();
						//若营业部取值为空的话，提示用户 先选择
						if(Ext.isEmpty(salesDeptCode)){
							baseinfo.showErrorMes('请先选择营业部');
							me.getForm().findField('expressBranchCode').setValue('');
							return;
						}
						var expressBranchSalesDeptModel =new Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel();
						var expressBranchCode =records[0].get('code');
						Ext.Ajax.request({
							params:{'vo.expressBranchSalesDeptEntity.expressBranchCode':expressBranchCode,
											 'vo.expressBranchSalesDeptEntity.salesDeptCode':salesDeptCode
											},
							url:baseinfo.realPath('queryExpressBranchSaleDeptByExpressBranchSalesDeptCode.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								var expressBranchSalesDept =json.vo.expressBranchSalesDeptEntity;
								//若库中已经存在该快递分部对应关系
								if(!Ext.isEmpty(expressBranchSalesDept)){
									baseinfo.showErrorMes('已经存在'+expressBranchSalesDept.expressBranchName+'映射'+expressBranchSalesDept.salesDeptName+'的关系！请勿再添加');
									return;
								}
								//把营业部编码添加到model中
								expressBranchSalesDeptModel.set('salesDeptCode',salesDeptCode);
								expressBranchSalesDeptModel.set('salesDeptName',salesDeptName);
								if(me.getExpressBranchSelectArea().getStore().data.items.length>0){
									 baseinfo.showErrorMes('快递分部和营业部只能一对一映射');
									 return;
								}
								expressBranchSalesDeptModel.set('expressBranchCode',expressBranchCode);
								expressBranchSalesDeptModel.set('expressBranchName',records[0].get('name'));
								//把选中的实体添加给库中
								me.getExpressBranchSelectArea().getStore().add(expressBranchSalesDeptModel);
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
			},me.getExpressBranchSelectArea()];
    	me.callParent([cfg]);
    },
});
//显示的form
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSelectArea',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:260,
	height:180,
	columns:[{dataIndex:'expressBranchName',text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.expressBranchServerSales'),flex:1}],//快递分部名称
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
		me.store =baseinfo.getStore(null,'Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel',null,[]);
		me.tbar =[{
			text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.expressBranchSalesDept.voidExpressBranch'), //移除快遞分部
//			disabled:true,
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
	if(Ext.getCmp('T_baseinfo-expressBranchSalesDept_content')){
		return ;
	}
	var expressBranchSalesDeptQueryForm =Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm');
	var expressBranchSalesDeptGrid =Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptGrid');
	Ext.getCmp('T_baseinfo-expressBranchSalesDept').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-expressBranchSalesDept_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm:function(){
			return expressBranchSalesDeptQueryForm;
		},
		getGrid:function(){
			return expressBranchSalesDeptGrid;
		},
		items:[expressBranchSalesDeptQueryForm,expressBranchSalesDeptGrid]
	}));
});