//model
Ext.define('Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'deptCode',
		type:'string'
	},{
		name:'deptName',
		type:'string'
	},{
		name:'fthNetworkCode',
		type:'string'
	},{
		name:'fthNetworkName',
		type:'string'
	},{
		name:'secNetworkCode',
		type:'string'
	},{
		name:'secNetworkName',
		type:'string'
	},{
		name:'isOutfield',
		type:'string'
	},{
		name:'createUserName',
		type:'string'
	},{
		name:'createUserCode',
		type:'string'
	},{
		name:'updateUserName',
		type:'string'
	},{
		name:'updateUserCode',
		type:'string'
	}]
});

Ext.define('Foss.baseinfo.deptTransferMapping.isOutfield', {
	extend : 'Ext.data.Store',
	fields : [{
	name : 'valueCode',
	type : 'string'
	}, {
	name : 'valueName',
	type : 'string'
	}
	],
	proxy : {
	type : 'memory',
	reader : {
	type : 'json',
	root : 'items' //定义读取JSON数据的根对象
	}
	},
	constructor : function (config) {
	var me = this,
	cfg = Ext.apply({}, config);
	me.callParent([cfg]);
	}
	});

//store
Ext.define('Foss.baseinfo.deptTransferMapping.DeptTransferMappingStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryDeptTransferMappingList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.deptTransferMappingList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
//查询表单
Ext.define('Foss.baseinfo.deptTransferMapping.DeptTransferMappingQueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.deptTransferMapping.i18n('foss.baseinfo.queryCondition'),
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
				xtype:'dynamicorgcombselector',
				name:'deptCode',
				types:'ORG',
				salesDepartment:'Y',//自己定义参数就OK  --定义查询营业部
				transferCenter:'Y',//--或者查询外场
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.deptName'), //对接部门名称
				columnWidth:0.33,
			},{

				xtype : 'combobox',
				name : 'isOutfield',
				fieldLabel : '是否外场',
				labelWidth :70,
				columnWidth : .21,
				displayField : 'valueName',
				valueField : 'valueCode',
				value:'ALL',
				queryMode : 'local',
				//triggerAction : 'ALL',
				editable : false,
				store : Ext.create('Foss.baseinfo.deptTransferMapping.isOutfield', {
					data : {
						'items' : [  {
								'valueCode' : 'ALL',
								'valueName' : '全部'
								}, 
						           {
								'valueCode' : 'N',
								'valueName' : '否'
							}, {
								'valueCode' : 'Y',
								'valueName' : '是'
							}
						]
					}
				}),
				value : ''
			
			},{
				
				xtype:'commonLeagueSaleDeptselector',
				name:'fthNetworkCode',
				labelWidth :160,
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.fthNetworkName'),//一级合伙人网点名称
				columnWidth:0.45,
			},{
				xtype:'container',
				columnWidth:1, 
				defaultType:'button',
				layout:'column',
				items:[
				       {
					text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.query'),//查询
					disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/queryButton'),
					hidden:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/queryButton'),
					columnWidth:.08,
					handler : function() {
						me.up().getGrid().getPagingToolbar().moveFirst();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
					text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.reset'),//重置
					disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/queryButton'),
					hidden:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/queryButton'),
					columnWidth:.08,
					handler : function() {
						me.getForm().reset();
					}
				}]
			}];
		me.callParent([cfg]);
	}
});
//列表
Ext.define('Foss.baseinfo.deptTransferMapping.DeptTransferMappingGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.deptTransferMapping.i18n('foss.baseinfo.queryGrid'),
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为:行选择
	emptyText:baseinfo.deptTransferMapping.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
	//新增窗口
	valueAddWindow:null,
	getAddWindow:function(){
		if(this.valueAddWindow == null){
			this.valueAddWindow =Ext.create('Foss.baseinfo.deptTransferMapping.AddValueWidow');
			this.valueAddWindow.parent = this;
		}
		return this.valueAddWindow;
	},
	//修改窗口
	updateWindow:null,
	getUpdateWidow:function(){
		if(this.updateWindow == null){
			this.updateWindow =Ext.create('Foss.baseinfo.deptTransferMapping.UpdateValueWidow');
			this.updateWindow.parent = this;
		}
		return this.updateWindow;
	},
	showWindow:null,
	getShowWindow:function(){
		if(this.showWindow == null){
			this.showWindow =Ext.create('Foss.baseinfo.deptTransferMapping.ShowValueWidow');
			this.showWindow.parent = this;
		}
		return this.showWindow;
	},
	constructor:function(config){
		var me =this,cfg= Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.operate'),//操作
			items:[{
	    		iconCls : 'deppon_icons_showdetail',
				tooltip:'查看',
				handler:function(grid, rowIndex, colIndex){
					var record = grid.getStore().getAt(rowIndex);
					var showWin = me.getShowWindow();
					showWin.deptTransferMappingModel =new Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel(record.data);
					showWin.show();
				}
	    	},{
					iconCls: 'deppon_icons_edit',
					tooltip:baseinfo.deptTransferMapping.i18n('foss.baseinfo.edit'),//编辑
					disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/updateButton'),
					width:30,
					handler:function(grid,rowIndex,colIndex){
						var record =grid.getStore().getAt(rowIndex);
						var updateWin =me.getUpdateWidow();
						updateWin.deptTransferMappingModel =new Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel(record.data);
						updateWin.show();
					}
				},{
					iconCls : 'deppon_icons_delete',
					tooltip:baseinfo.deptTransferMapping.i18n('foss.baseinfo.void'),//作废
					disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/deleteButton'),
					//disabled:!baseinfo.deptTransferMapping.isPermission(''),
					width:30,
					handler:function(grid,rowIndex,colIndex){
					 var record =grid.getStore().getAt(rowIndex);
					 	//判断是否要作废
						baseinfo.showQuestionMes(baseinfo.deptTransferMapping.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
							if(e=='yes'){
								Ext.Ajax.request({
									
									url:baseinfo.realPath('deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode.action'),
									params:{
									         'vo.deptCode':record.get('deptCode'),
									         'vo.fthNetworkCode':record.get('fthNetworkCode'),
									},
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
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.deptName'), //对接部门名称
			dataIndex:'deptName',
			flex:1
		},{
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.fthNetworkName'),//一级合伙人网点名称
			dataIndex:'fthNetworkName',
			flex:1
		},{
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.isOutfield'),//是否外场
			dataIndex:'isOutfield',
			flex:1,
			renderer:function(isOutfield){
				if(!Ext.isEmpty(isOutfield)){
					if(isOutfield=="Y"){
						return "是";
					}else{
						return "否";
					}
				}else{
					return "否";
				}
				
			}
		},{
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.createUserName'),//创建人
			dataIndex:'createUserName',
			flex:1
		},{
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.updateUserName'),//修改人
			dataIndex:'updateUserName',
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
		me.store =Ext.create('Foss.baseinfo.deptTransferMapping.DeptTransferMappingStore',{
			autoLoad:true,
			pageSize:10,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-deptTransferMapping_content').getQueryForm();
					if(queryForm != null){
						var queryParams =queryForm.getValues();
						Ext.apply(operation,{
							params:{
								'vo.deptCode':queryParams.deptCode,
								'vo.isOutfield':queryParams.isOutfield,
								'vo.fthNetworkCode':queryParams.fthNetworkCode,
								
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
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.add'),
			disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/addButton'),
			hidden:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/addButton'),
			handler:function(){
				me.getAddWindow().show();
			}
		},{
			text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.void'),
			disabled:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/deleteButton'),
			hidden:!baseinfo.deptTransferMapping.isPermission('deptTransferMapping/deleteButton'),
			handler:function(){
				//获取已经选中的
				var selections =me.getSelectionModel().getSelection();
				var idList =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.deptTransferMapping.i18n('foss.baseinfo.deleteNoticeMsg'));
		    		return;
				}
				for(var i=0;i<selections.length;i++){
					idList.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.deptTransferMapping.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'idList':idList}},
							url:baseinfo.realPath('deleteDeptTransferMappingsByIdList.action'),
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

//显示
Ext.define('Foss.baseinfo.deptTransferMapping.ShowValueWidow',{
	extend:'Ext.window.Window',
	title:'营业部对接映射关系',
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
	width :900,
	height :600,
	deptTransferMappingModel:null,
	listeners:{
						beforeshow : function(me) {
							if (me.deptTransferMappingModel != null) {
								var model = me.deptTransferMappingModel;
								// me.getUpdateValueForm().getForm().loadRecord(model);
								var isOutfield = model.get('isOutfield');
								if (isOutfield == "Y") {
									isOutfield = "是";
								} else {
									isOutfield = "否";
								}
								me.getShowValueForm().getForm().findField(
										'isOutfield').setValue(isOutfield);
								me.getShowValueForm().getShowDepartListForm()
										.getStore().add(model);
								me.getShowValueForm()
										.getShowFthNetworkListForm().getStore()
										.add(model);
								me.getShowValueForm()
										.getShowSecNetworkListForm().getStore()
										.removeAll();
								Ext.Ajax
										.request({
											url : baseinfo
													.realPath('queryDeptTransferMappingListByDeptCodeAndFthNetwork.action'),
											params : {
												'vo.deptCode' : model
														.get('deptCode'),
												'vo.fthNetworkCode' : model
														.get('fthNetworkCode'),
											},
											success : function(response) {
												var result = Ext
														.decode(response.responseText);
												if (result.vo.deptTransferMappingList != null) {
													for ( var i = 0; i < result.vo.deptTransferMappingList.length; i++) {
														me
																.getShowValueForm()
																.getShowSecNetworkListForm()
																.getStore()
																.add(
																		result.vo.deptTransferMappingList[i]);
													}

												}
											},
											exception : function(response) {
												var result = Ext
														.decode(response.responseText);
												Ext.MessageBox
														.show({
															title : '温馨提示',
															msg : result.message,
															width : 300,
															buttons : Ext.Msg.OK,
															icon : Ext.MessageBox.WARNING
														});
											}
										});
								//把选中的实体添加给库中
								//me.getUpdateValueForm().getSecNetworkListForm().getStore().add(model);

							}
						},
						beforehide:function(me){
							me.getShowValueForm().getForm().reset();
							me.getShowValueForm().getShowDepartListForm().getStore().removeAll();
							me.getShowValueForm().getShowFthNetworkListForm().getStore().removeAll();
							me.getShowValueForm().getShowSecNetworkListForm().getStore().removeAll();
						}
					},
	showValueForm:null,
	getShowValueForm:function(){
		if(this.showValueForm == null){
			this.showValueForm  =Ext.create('Foss.baseinfo.deptTransferMapping.ShowValueForm',{
			});
		}
		return this.showValueForm;
	},
	
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[me.getShowValueForm()];
		me.callParent([cfg]);
	},
	
});
//新增
Ext.define('Foss.baseinfo.deptTransferMapping.AddValueWidow',{
	extend:'Ext.window.Window',
	title:'新增营业部对接映射关系',
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
	width :900,
	height :600,
	listeners:{
		beforeshow:function(me){
			me.getAddValueForm().getDepartListForm().getDockedItems()[0].items.items[0].setDisabled(true);
			me.getAddValueForm().getFthNetworkListForm().getDockedItems()[0].items.items[0].setDisabled(true);
			me.getAddValueForm().getSecNetworkListForm().getDockedItems()[0].items.items[0].setDisabled(true);
		},
		beforehide:function(me){
			me.getAddValueForm().getForm().reset();
			me.getAddValueForm().getDepartListForm().getStore().removeAll();
			me.getAddValueForm().getFthNetworkListForm().getStore().removeAll();
			me.getAddValueForm().getSecNetworkListForm().getStore().removeAll();
		}
	},
	addValueForm:null,
	getAddValueForm:function(){
		if(this.addValueForm == null){
			this.addValueForm  =Ext.create('Foss.baseinfo.deptTransferMapping.AddValueForm',{
				isUpdate:false,
				isDelete:false,
			});
		}
		return this.addValueForm;
	},
	//保存新增信息
	saveAddValue:function(){
		var me =this;
		var addStore =	me.getAddValueForm().getSecNetworkListForm().getStore();
		var addRecordModelList =addStore.getNewRecords();
		var deptTransferMappingList =new Array();
		//若没有添加卫星点，不让提交
		if(addRecordModelList.length ==0){
			baseinfo.showErrorMes();
			return;
		}
		for ( var i = 0; i < addRecordModelList.length; i++) {
			deptTransferMappingList.push(addRecordModelList[i].data);
		}
		var params ={'vo':{'deptTransferMappingList':deptTransferMappingList}};
		Ext.Ajax.request({
			jsonData:params,
			url:baseinfo.realPath('addDeptTransferMapping.action'),
			success:function(response){
				var json =Ext.decode(response.responseText);
				//新增成功
				baseinfo.showInfoMes(json.message);
				//重新加载
				
				Ext.getCmp('T_baseinfo-deptTransferMapping_content').getGrid().getPagingToolbar().moveFirst();
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
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getAddValueForm().getForm().reset();
				me.getAddValueForm().getDepartListForm().getStore().removeAll();
				me.getAddValueForm().getFthNetworkListForm().getStore().removeAll();
				me.getAddValueForm().getSecNetworkListForm().getStore().removeAll();
			} 
		},{
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 0',
			handler :function(){
				me.saveAddValue(); 
			} 
		}];
		me.callParent([cfg]);
	},
});
//修改
Ext.define('Foss.baseinfo.deptTransferMapping.UpdateValueWidow',{
	extend:'Ext.window.Window',
	title:'修改营业部对接映射关系',
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
	width :900,
	height :600,
	deptTransferMappingModel:null,
	listeners:{
		beforeshow:function(me){
			if(me.deptTransferMappingModel!=null){
				var model =me.deptTransferMappingModel;
				//me.getUpdateValueForm().getForm().loadRecord(model);
				me.getUpdateValueForm().getForm().findField('deptCode').setCombValue(model.get('deptName'),model.get('deptCode'));
				me.getUpdateValueForm().getForm().findField('deptCode').setReadOnly(true);
				me.getUpdateValueForm().getForm().findField('fthNetworkCode').setCombValue(model.get('fthNetworkName'),model.get('fthNetworkCode'));
				me.getUpdateValueForm().getForm().findField('fthNetworkCode').setReadOnly(true);
				var isOutfield=model.get('isOutfield');
				if(isOutfield=="Y"){
					isOutfield="是";
				}else{
					isOutfield="否";
				}
				me.getUpdateValueForm().getForm().findField('isOutfield').setValue(isOutfield);
				me.getUpdateValueForm().getDepartListForm().getStore().add(model);
				me.getUpdateValueForm().getFthNetworkListForm().getStore().add(model);
				me.getUpdateValueForm().getSecNetworkListForm().getStore().removeAll();
				Ext.Ajax.request({
			         url:baseinfo.realPath('queryDeptTransferMappingListByDeptCodeAndFthNetwork.action'),
			         params:{
			         'vo.deptCode':model.get('deptCode'),
			         'vo.fthNetworkCode':model.get('fthNetworkCode'),
			         },
			         success:function(response){
			         var result = Ext.decode(response.responseText); 
			         if(result.vo.deptTransferMappingList!=null){
			        	 for(var i=0;i<result.vo.deptTransferMappingList.length;i++){
			        		 me.getUpdateValueForm().getSecNetworkListForm().getStore().add(result.vo.deptTransferMappingList[i]);
			        	 }
			        	
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
				//把选中的实体添加给库中
				//me.getUpdateValueForm().getSecNetworkListForm().getStore().add(model);
				me.getUpdateValueForm().getDepartListForm().getDockedItems()[0].items.items[0].setDisabled(false);
				me.getUpdateValueForm().getFthNetworkListForm().getDockedItems()[0].items.items[0].setDisabled(false);
			}
		},
		beforehide:function(me){
			me.getUpdateValueForm().getForm().reset();
			me.getUpdateValueForm().getDepartListForm().getStore().removeAll();
			me.getUpdateValueForm().getFthNetworkListForm().getStore().removeAll();
			me.getUpdateValueForm().getSecNetworkListForm().getStore().removeAll();
			me.getUpdateValueForm().getForm().findField('deptCode').setReadOnly(false);
			me.getUpdateValueForm().getForm().findField('fthNetworkCode').setReadOnly(false);
			if(me.getUpdateValueForm().isDelete){
				//重新加载
				Ext.getCmp('T_baseinfo-deptTransferMapping_content').getGrid().getPagingToolbar().moveFirst();
			}
		}
	},
	updateValueForm:null,
	getUpdateValueForm:function(){
		if(this.updateValueForm == null){
			this.updateValueForm  =Ext.create('Foss.baseinfo.deptTransferMapping.AddValueForm',{
				isUpdate:true,
				isDelete:false,
			});
		}
		return this.updateValueForm;
	},
	//保存修改信息
	saveUpdateValue:function(){
		var me =this;
		var updateStore =me.getUpdateValueForm().getSecNetworkListForm().getStore();
		var addRecordModelList =updateStore.getNewRecords();
		var deptTransferMappingList =new Array();
		for ( var i = 0; i < addRecordModelList.length; i++) {
			deptTransferMappingList.push(addRecordModelList[i].data);
		}
		//作废的列表信息
	
		var vo =new Object();
		vo.deptTransferMappingList =deptTransferMappingList;
		var params ={'vo':vo};
		Ext.Ajax.request({
			jsonData:params,
			url:baseinfo.realPath('updateDeptTransferMapping.action'),
			success:function(response){
				var json =Ext.decode(response.responseText);
				//新增成功
				baseinfo.showInfoMes(json.message);
				//重新加载
				Ext.getCmp('T_baseinfo-deptTransferMapping_content').getGrid().getPagingToolbar().moveFirst();
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
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				if(me.getUpdateValueForm().isDelete){
					//重新加载
					Ext.getCmp('T_baseinfo-deptTransferMapping_content').getGrid().getPagingToolbar().moveFirst();
				}
				me.close();
			} 
		},{
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				//先清空store
				me.getUpdateValueForm().getDepartListForm().getStore().removeAll();
				me.getUpdateValueForm().getFthNetworkListForm().getStore().removeAll();
				me.getUpdateValueForm().getSecNetworkListForm().getStore().removeAll();
				if(me.deptTransferMappingModel!=null){
					var model =me.deptTransferMappingModel;
					me.getUpdateValueForm().getForm().findField('deptCode').setCombValue(model.get('deptName'),model.get('deptCode'));
					me.getUpdateValueForm().getForm().findField('deptCode').setReadOnly(true);
					me.getUpdateValueForm().getForm().findField('fthNetworkCode').setCombValue(model.get('fthNetworkName'),model.get('fthNetworkCode'));
					me.getUpdateValueForm().getForm().findField('fthNetworkCode').setReadOnly(true);
					var isOutfield=model.get('isOutfield');
					if(isOutfield=="Y"){
						isOutfield="是";
					}else{
						isOutfield="否";
					}
					
					me.getUpdateValueForm().getForm().findField('isOutfield').setValue(isOutfield);
					me.getUpdateValueForm().getDepartListForm().getStore().add(model);
					me.getUpdateValueForm().getFthNetworkListForm().getStore().add(model);
					Ext.Ajax.request({
				         url:baseinfo.realPath('queryDeptTransferMappingListByDeptCodeAndFthNetwork.action'),
				         params:{
				         'vo.deptCode':model.get('deptCode'),
				         'vo.fthNetworkCode':model.get('fthNetworkCode'),
				         },
				         success:function(response){
				         var result = Ext.decode(response.responseText); 
				         if(result.vo.deptTransferMappingList!=null){
					       for(var i=0;i<result.vo.deptTransferMappingList.length;i++){
					    	   me.getUpdateValueForm().getSecNetworkListForm().getStore().add(result.vo.deptTransferMappingList[i]);
					       }
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
					//把选中的实体添加给库中
					//me.getUpdateValueForm().getSecNetworkListForm().getStore().add(model);
					me.getUpdateValueForm().getDepartListForm().getDockedItems()[0].items.items[0].setDisabled(false);
					me.getUpdateValueForm().getFthNetworkListForm().getDockedItems()[0].items.items[0].setDisabled(false);
				}
			} 
		},{
			text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.confirm'),//确定
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

//显示
Ext.define('Foss.baseinfo.deptTransferMapping.ShowValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:550,
	width:950,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '5 5 5 5',
		maxLength:50,
		allowBlank:false,
		labelWidth:100,
		anchor : '100%'
	},
	defaultType : 'textfield',
	 //列布局
	layout: {
        type: 'table',
        columns: 3
    },
	//layout:'column',
    showDepartListForm:null, //对接营业部集合
    showFthNetworkListForm:null,//一级合伙人网点
    showSecNetworkListForm:null,//二级合伙人网点

    getShowDepartListForm:function(){
    	if(this.showDepartListForm ==null){
    		this.showDepartListForm =Ext.create('Foss.baseinfo.deptTransferMapping.ShowDepartListForm');
    	}
    	return this.showDepartListForm;
    },
    getShowFthNetworkListForm:function(){
    	if(this.showFthNetworkListForm ==null){
    		this.showFthNetworkListForm =Ext.create('Foss.baseinfo.deptTransferMapping.ShowFthNetworkListForm');
    	}
    	return this.showFthNetworkListForm;
    },
    getShowSecNetworkListForm:function(){
    	if(this.showSecNetworkListForm ==null){
    		this.showSecNetworkListForm =Ext.create('Foss.baseinfo.deptTransferMapping.ShowSecNetworkListForm');
    	}
    	return this.showSecNetworkListForm;
    },
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[
			{
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.isOutfield'), //是否对接外场
				name:'isOutfield',
				value:'',
				columns:1,
				readOnly:true,
				
			},
			{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:0.33
				
			},	{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:0.33
				
			},
			me.getShowDepartListForm(),
			me.getShowFthNetworkListForm(),
			me.getShowSecNetworkListForm()
			];
    	me.callParent([cfg]);
    },
});
//新增
Ext.define('Foss.baseinfo.deptTransferMapping.AddValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:550,
	width:950,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '5 5 5 5',
		maxLength:50,
		allowBlank:false,
		labelWidth:100,
		anchor : '100%'
	},
	defaultType : 'textfield',
	 //列布局
	layout: {
        type: 'table',
        columns: 3
    },
	//layout:'column',
    departListForm:null, //对接营业部集合
    fthNetworkListForm:null,//一级合伙人网点
    secNetworkListForm:null,//二级合伙人网点
    isUpdate:false,
    isDelete:false,
    getIsUpdate:function(){
    	return this.isUpdate;
    },

    getDepartListForm:function(){
    	if(this.departListForm ==null){
    		this.departListForm =Ext.create('Foss.baseinfo.deptTransferMapping.DepartListForm');
    	}
    	return this.departListForm;
    },
    getFthNetworkListForm:function(){
    	if(this.fthNetworkListForm ==null){
    		this.fthNetworkListForm =Ext.create('Foss.baseinfo.deptTransferMapping.FthNetworkListForm');
    	}
    	return this.fthNetworkListForm;
    },
    getSecNetworkListForm:function(){
    	if(this.secNetworkListForm ==null){
    		this.secNetworkListForm =Ext.create('Foss.baseinfo.deptTransferMapping.SecNetworkListForm');
    	}
    	return this.secNetworkListForm;
    },
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[{
	    		xtype:'dynamicorgcombselector',
				name:'deptCode',
				types:'ORG',
				salesDepartment:'Y',//自己定义参数就OK  --定义查询营业部
				transferCenter:'Y',//--或者查询外场
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.deptName'), //营业部
				columnWidth:0.16,
				labelWidth:90,
				allowBlank:true,
				listeners:{
					select:function(){
						var deptCode=me.getForm().findField('deptCode').getValue();
						Ext.Ajax.request({
					         url:baseinfo.realPath('checkIsOutField.action'),
					         params:{
					         'vo.deptCode':deptCode
					         },
					         success:function(response){
					         var result = Ext.decode(response.responseText); 
					         me.getForm().findField('isOutfield').setValue(null);
					         if(result.vo.isOutfield=='Y'){
					        	 me.getForm().findField('isOutfield').setValue('是');
					         }else{
					        	 me.getForm().findField('isOutfield').setValue('否');
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
						
					}
				}
			},{
				xtype:'commonLeagueSaleDeptselector',
				name:'fthNetworkCode',
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.fthNetworkName'), //一级合伙人网点
				columnWidth:0.34,
				labelWidth:128,
				allowBlank:true,
				listeners:{
					select:function(comb,records,obj){
						var fthNetworkCode=me.getForm().findField('fthNetworkCode').getValue();
						var fthNetworkName=me.getForm().findField('fthNetworkCode').getRawValue();
						//若营业部取值为空的话，提示用户 先选择
						if(Ext.isEmpty(fthNetworkCode)){
							baseinfo.showErrorMes('请先选择一级合伙人网点');
							return;
						}
						Ext.Ajax.request({
							params:{'vo.fthNetworkCode':fthNetworkCode},
							url:baseinfo.realPath('queryDeptTransferMappingModelByFthNetworkCode.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								//若库中已经存在该二级网点的信息
								if(json.vo.isExists){
									baseinfo.showErrorMes('已经存在  '+fthNetworkName+'  映射营业部的关系！请勿再添加');
									me.getForm().findField('fthNetworkCode').setValue("");
									me.getForm().findField('fthNetworkCode').setRawValue("");
									return;
								}
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				}
				
			},{
	    		xtype:'commonTwoLevelNetworkselector',
				name:'secNetworkCode',
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.secNetworkName'), //营业部
				columnWidth:0.34,
				labelWidth:128,
				allowBlank:true,
				listeners:{
					select:function(comb,records,obj){
						var secNetworkCode=me.getForm().findField('secNetworkCode').getValue();
						var secNetworkName=me.getForm().findField('secNetworkCode').getRawValue();
						//若营业部取值为空的话，提示用户 先选择
						if(Ext.isEmpty(secNetworkCode)){
							//baseinfo.showErrorMes('请先选择二级合伙人网点');
							return;
						}
						Ext.Ajax.request({
							params:{'vo.secNetworkCode':secNetworkCode},
							url:baseinfo.realPath('queryDeptTransferMappingModelBySecNetworkCode.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								//若库中已经存在该二级网点的信息
								if(json.vo.isExists){
									baseinfo.showErrorMes('已经存在  '+secNetworkName+'  映射营业部的关系！请勿再添加');
									me.getForm().findField('secNetworkCode').setValue("");
									me.getForm().findField('secNetworkCode').setRawValue("");
									return;
								}
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				}
			},
			{
			 	xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:0.33
			  },
			  {
				 	xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:0.33
				  },
			{
				xtype:'button',
				width:70,
				text : baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.add'),//添加
				margin:'0 0 0 210',
				handler :function(){
					var deptCode =me.getForm().findField('deptCode').getValue();
					var deptName=me.getForm().findField('deptCode').getRawValue();
					//若营业部取值为空的话，提示用户 先选择
					if(Ext.isEmpty(deptCode)){
						baseinfo.showErrorMes('请先选择营业部');
						return;
					}
					var fthNetworkCode=me.getForm().findField('fthNetworkCode').getValue();
					var fthNetworkName=me.getForm().findField('fthNetworkCode').getRawValue();
					//若一级网点为空的话，提示用户 先选择
					if(Ext.isEmpty(fthNetworkCode)){
						baseinfo.showErrorMes('请先选择一级网点');
						return;
					}
					var secNetworkCode=me.getForm().findField('secNetworkCode').getValue();
					if(secNetworkCode==null){
						secNetworkCode="";
					}
					var secNetworkName=me.getForm().findField('secNetworkCode').getRawValue();
					//若二级网点为空的话，提示用户 先选择
//					if(Ext.isEmpty(secNetworkCode)){
//						baseinfo.showErrorMes('请先选择二级网点');
//						return;
//					}
					
					var  isOutfield=me.getForm().findField('isOutfield').getValue();
					if(isOutfield=="是"){
						isOutfield="Y";
					}else{
						isOutfield="N";
					}
					var deptTransferMappingModel =Ext.create('Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel');
					
					//把营业部编码添加到model中
				
					var booleanDept =false;
					var booleanFthNetwork=false;
					var booleanSecNetwork=false;
					me.getDepartListForm().getStore().each(function(record){
						//若库中已展示的grid中已经存在该卫星点，请勿重复添加
						if(record.get('deptCode')==deptCode){
							booleanDept = true;
						}
					});
					
					me.getFthNetworkListForm().getStore().each(function(record){
						//若库中已展示的grid中已经存在该卫星点，请勿重复添加
						if(record.get('fthNetworkCode')==fthNetworkCode){
							booleanFthNetwork = true;
						}
					});
					
					me.getSecNetworkListForm().getStore().each(function(record){
						//若库中已展示的grid中已经存在该卫星点，请勿重复添加
						if(record.get('secNetworkCode')==secNetworkCode){
							booleanSecNetwork = true;
						}
						
					});
					if(booleanDept&&booleanFthNetwork){

						if(booleanSecNetwork){
							return;
						}else{
							deptTransferMappingModel.set('deptCode',deptCode);
							deptTransferMappingModel.set('deptCode',deptCode);
							deptTransferMappingModel.set('deptName',deptName);
							deptTransferMappingModel.set('fthNetworkCode',fthNetworkCode);
							deptTransferMappingModel.set('fthNetworkName',fthNetworkName);
							deptTransferMappingModel.set('secNetworkCode',secNetworkCode);
							deptTransferMappingModel.set('secNetworkName',secNetworkName);
							deptTransferMappingModel.set('isOutfield',isOutfield);
							//把选中的实体添加给库中
							me.getSecNetworkListForm().getStore().add(deptTransferMappingModel);
						}
					}else{
						me.getDepartListForm().getStore().removeAll();
						me.getFthNetworkListForm().getStore().removeAll();
						me.getSecNetworkListForm().getStore().removeAll();
						deptTransferMappingModel.set('deptCode',deptCode);
						deptTransferMappingModel.set('deptName',deptName);
						deptTransferMappingModel.set('fthNetworkCode',fthNetworkCode);
						deptTransferMappingModel.set('fthNetworkName',fthNetworkName);
						me.getDepartListForm().getStore().add(deptTransferMappingModel);
						me.getFthNetworkListForm().getStore().add(deptTransferMappingModel);
						deptTransferMappingModel.set('secNetworkCode',secNetworkCode);
						deptTransferMappingModel.set('secNetworkName',secNetworkName);
						deptTransferMappingModel.set('isOutfield',isOutfield);
						//把选中的实体添加给库中
						me.getSecNetworkListForm().getStore().add(deptTransferMappingModel);
					}
					
					
					
				} 
			},
			{
			 	xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:0.33
			  },
			{
				fieldLabel:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.isOutfield'), //是否对接外场
				name:'isOutfield',
				value:'',
				columns:1,
				readOnly:true,
				
			},
			{
				fieldLabel : baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.operator'),//操作人
				columns:2,
				name:'createName',
				readOnly:true,
				value:FossUserContext.getCurrentUser().empName,
				
			},
			me.getDepartListForm(),
			me.getFthNetworkListForm(),
			me.getSecNetworkListForm()
			];
    	me.callParent([cfg]);
    },
});
//显示的form
Ext.define('Foss.baseinfo.deptTransferMapping.DepartListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'对接部门列表',
	columns:[
	         {
	        	 xtype:'actioncolumn',
	        	 width : 60,
	        	 text : "操作",
	        	 items:[{
	        		 iconCls : 'deppon_icons_delete',
	        		 tooltip : '作废',
	        		 handler : function (grid, rowIndex, colIndex) {
	        		 var addForm=this.up().up().up();
	        		 Ext.MessageBox.show({
	        		 title : '确认提示',
	        		 msg : '作废（对接营业部映射）后不可恢复，确认是否继续？',
	        		 buttons : Ext.Msg.YESNO,
	        		 icon : Ext.MessageBox.QUESTION,
	        		 fn : function (btn) {
	        		 if (btn == 'yes') {
	        		 //获取结果表格对象
	        		 var isUpdate=addForm.isUpdate;
	        		 var isDelete=true;
	        		 if(!isUpdate){
	        			 isDelete=false;
	        			 addForm.getDepartListForm().getStore().removeAll();
	        			 addForm.getFthNetworkListForm().getStore().removeAll();
	        			 addForm.getSecNetworkListForm().getStore().removeAll();
	        			 return;
	        		 }
	        		 var record = grid.getStore().getAt(rowIndex);
	        		 var fthNetworkCode=addForm.getForm().findField('fthNetworkCode').getValue();
	        		 Ext.Ajax.request({
	        		 url : baseinfo.realPath('deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode.action'),
	        		 jsonData : {
	        		 'vo' : {
	        		 'deptCode' : record.data.deptCode,
	        		 'fthNetworkCode':fthNetworkCode,
	        		 }
	        		 },
	        		 //"作废"成功
	        		 success : function (response) {
	        		 var json = Ext.decode(response.responseText);
	        		 addForm.isDelete=isDelete;
	        		 addForm.getDepartListForm().getStore().removeAll();
        			 addForm.getFthNetworkListForm().getStore().removeAll();
        			 addForm.getSecNetworkListForm().getStore().removeAll();
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
	         },
	         {dataIndex:'deptName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.deptName'),flex:1}],//对接部门名称
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
	    me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});

Ext.define('Foss.baseinfo.deptTransferMapping.ShowDepartListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'对接部门列表',
	columns:[
	         
	         {dataIndex:'deptName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.deptName'),flex:1}],//对接部门名称
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
	    me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});

//显示的form
Ext.define('Foss.baseinfo.deptTransferMapping.FthNetworkListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'一级合伙人网点列表',
	columns:[
	         {
	        	 xtype:'actioncolumn',
	        	 width : 60,
	        	 text : "操作",
	        	 items:[{
	        		 iconCls : 'deppon_icons_delete',
	        		 tooltip : '作废',
	        		 handler : function (grid, rowIndex, colIndex) {
	        			 var addForm=this.up().up().up();
	        		 Ext.MessageBox.show({
	        		 title : '确认提示',
	        		 msg : '作废（合伙人一级网点）后不可恢复，确认是否继续？',
	        		 buttons : Ext.Msg.YESNO,
	        		 icon : Ext.MessageBox.QUESTION,
	        		 fn : function (btn) {
	        		 if (btn == 'yes') {
		        		 var isUpdate=addForm.isUpdate;
		        		 var isDelete=true;
		        		 if(!isUpdate){
		        			 isDelete=false;
		        			 addForm.getDepartListForm().getStore().removeAll();
		        			 addForm.getFthNetworkListForm().getStore().removeAll();
		        			 addForm.getSecNetworkListForm().getStore().removeAll();
		        			 return;
		        		 }
	        		//获取结果表格对象
	        		 var record = grid.getStore().getAt(rowIndex);
	        		 var deptCode=addForm.getForm().findField('deptCode').getValue();
	        		 Ext.Ajax.request({
	        		 url : baseinfo.realPath('deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode.action'),
	        		 jsonData : {
	        		 'vo' : {
	        		 'fthNetworkCode' : record.data.fthNetworkCode,
	        		 'deptCode':deptCode,
	        		 }
	        		 },
	        		 //"作废"成功
	        		 success : function (response) {
	        		 var json = Ext.decode(response.responseText);
	        		 addForm.isDelete=isDelete;
	        		 addForm.getDepartListForm().getStore().removeAll();
        			 addForm.getFthNetworkListForm().getStore().removeAll();
        			 addForm.getSecNetworkListForm().getStore().removeAll();
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
	         },
	         {dataIndex:'fthNetworkName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.fthNetworkName'),flex:1}],//一级合伙人网点
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
		me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});

Ext.define('Foss.baseinfo.deptTransferMapping.ShowFthNetworkListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'一级合伙人网点列表',
	columns:[
	         
	         {dataIndex:'fthNetworkName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.fthNetworkName'),flex:1}],//一级合伙人网点
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
		me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});

//显示的form
Ext.define('Foss.baseinfo.deptTransferMapping.SecNetworkListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'二级合伙人网点列表',
	columns:[
	         {
        	 xtype:'actioncolumn',
        	 width : 60,
        	 text : "操作",
        	 items:[{
        		 iconCls : 'deppon_icons_delete',
        		 tooltip : '作废',
        		 handler : function (grid, rowIndex, colIndex) {
        			 var addForm=this.up().up().up();
        		 Ext.MessageBox.show({
        		 title : '确认提示',
        		 msg : '作废（合伙人二级网点）后不可恢复，确认是否继续？',
        		 buttons : Ext.Msg.YESNO,
        		 icon : Ext.MessageBox.QUESTION,
        		 fn : function (btn) {
        		 if (btn == 'yes') {
        			
        		//获取结果表格对象
        		 var record = grid.getStore().getAt(rowIndex);
        		 var isUpdate=addForm.isUpdate;
        		 var isDelete=true;
        		 if(!isUpdate){
        			 addForm.isDelete=false;
        			 addForm.getSecNetworkListForm().getStore().remove(record);
        			 return;
        		 }
        		 var id=record.data.id;
        		 if(id==""){
        			 addForm.isDelete=isDelete;
        			 addForm.getSecNetworkListForm().getStore().remove(record);
        			 return;
        		 }
        		 Ext.Ajax.request({
        		 url : baseinfo.realPath('deleteDeptTransferMappingById.action'),
        		 jsonData : {
        		 'vo' : {
        		 'id' : id,
        		 }
        		 },
        		 //"作废"成功
        		 success : function (response) {
        		 var json = Ext.decode(response.responseText);
        		 addForm.isDelete=isDelete;
        		 addForm.getSecNetworkListForm().getStore().remove(record);
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
	         },
	         {dataIndex:'secNetworkName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.secNetworkName'),flex:1}],//二级合伙人网点
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
		me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});

Ext.define('Foss.baseinfo.deptTransferMapping.ShowSecNetworkListForm',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:230,
	height:180,
	title:'二级合伙人网点列表',
	columns:[{dataIndex:'secNetworkName',text:baseinfo.deptTransferMapping.i18n('foss.baseinfo.deptTransferMapping.secNetworkName'),flex:1}],//二级合伙人网点
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
		me.store =baseinfo.getStore(null,'Foss.baseinfo.deptTransferMapping.DeptTransferMappingModel',null,[]);
		me.callParent([cfg]);
	},	
});
//--------------------初始化面板---------------------------
Ext.onReady(function(){
	//tips初始化
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-deptTransferMapping_content')){
		return ;
	}
	var deptTransferMappingQueryForm =Ext.create('Foss.baseinfo.deptTransferMapping.DeptTransferMappingQueryForm');
	var deptTransferMappingGrid =Ext.create('Foss.baseinfo.deptTransferMapping.DeptTransferMappingGrid');
	Ext.getCmp('T_baseinfo-deptTransferMapping').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-deptTransferMapping_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm:function(){
			return deptTransferMappingQueryForm;
		},
		getGrid:function(){
			return deptTransferMappingGrid;
		},
		items:[deptTransferMappingQueryForm,deptTransferMappingGrid]
	}));
});