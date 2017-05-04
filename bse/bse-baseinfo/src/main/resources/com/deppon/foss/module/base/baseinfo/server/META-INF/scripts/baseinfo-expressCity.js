baseinfo.expressCity.EXPRESSCITY_TYPE_L = 'L';//快递代理类型
baseinfo.expressCity.EXPRESSCITY_TYPE_P = 'P';//试点类型

baseinfo.expressCity.expressCityConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(baseinfo.expressCity.i18n('foss.baseinfo.tips'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * Form查询方法: 
 */
baseinfo.expressCity.expressCityListQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var expressCityType = form.findField('expressCityType').getValue();
	var provCode = form.findField('provCode').getValue();
	var cityCode = form.findField('cityCode').getValue();
	
	//如果选择了省份，就一定要选择到市
	if(provCode!=null && provCode!=''){
		if(cityCode==null || cityCode==''){
			Ext.MessageBox.show({
				title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
				msg : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.queryExpressCityRequiredCity'),
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
		}
	}
	// 设置参数
	params = form.getValues();
	Ext.apply(params, {
		'expressCityVo.expressCityQueryDto.type' : expressCityType,
		'expressCityVo.expressCityQueryDto.districtCode' : cityCode
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				if(result.expressCityVo.expressCityResultDtoList.length>0){
					grid.show();
				}
	    	}
	       }
	    }); 
}

//修改明细
baseinfo.expressCity.showDetialExpressCity = function(grid, rowIndex, colIndex){

	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	var id = selection.data.id;
	
	// 打开注释
	Ext.Ajax.request({
		url:baseinfo.realPath('showExpressCity.action'),
		params:{
			'expressCityVo.expressCityQueryDto.id':id
		},
		success:function(response){
			var result = Ext.decode(response.responseText);	
			// 获取弹出窗口
			var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
			if (Ext.isEmpty(a_win)) {
				a_win = Ext.create('Foss.baseinfo.expressCity.addOrUpdateExpressCityWindow');
			}
			
			if(result.expressCityVo.expressCityResultDto!=null){
				
				var addOrUpdateExpressCityTypeForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
				addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeCode').setValue(result.expressCityVo.expressCityResultDto.type);
				if(result.expressCityVo.expressCityResultDto.type==baseinfo.expressCity.EXPRESSCITY_TYPE_L){
					addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').setRawValue(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.luodipeiCity'));
					addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').setValue(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.luodipeiCity'));
				}else if(result.expressCityVo.expressCityResultDto.type==baseinfo.expressCity.EXPRESSCITY_TYPE_P){
					addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').setRawValue(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.poiltCity'));
					addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').setValue(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.poiltCity'));
				}
				addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').setReadOnly(true);
				
				var selectedRecord = new Foss.baseinfo.expressCity.ExpressCityListModel(result.expressCityVo.expressCityResultDto);
				var selectedAdministrativeRegionsPanelGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
				selectedAdministrativeRegionsPanelGrid.store.removeAll();
				selectedAdministrativeRegionsPanelGrid.store.add(selectedRecord);
				selectedAdministrativeRegionsPanelGrid.store.setRecord(selectedRecord);
			}
			a_win.show();		
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.show({
				title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
				msg : result.message,
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
		}			
	});

}

//批量作废
baseinfo.expressCity.disableExpressCityBatch = function(){
	
	// grid
	var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.MessageBox.show({
			title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
			msg : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.toDoByNoSelelcts'),
			width : 300,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.WARNING
		});
		return false;
	}
	
	var selectDisableIds = [];
	for(var i=0;i<selections.length;i++){
		selectDisableIds.push(selections[i].get('id'));
	}
	
	var yesFn=function(){
		// 调用
 		Ext.Ajax.request({
 			url:baseinfo.realPath('disableExpressCity.action'),
 			params:{
 				'expressCityVo.expressCityQueryDto.selectedIds':selectDisableIds
 			},
 			success:function(response){
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
							if(result.expressCityVo.expressCityResultDtoList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
 			},
 			exception:function(response){
 				var result = Ext.decode(response.responseText);	
 				Ext.MessageBox.show({
					title :baseinfo.expressCity.i18n('foss.baseinfo.tips'),
					msg : result.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
 				
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
							if(result.expressCityVo.expressCityResultDtoList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
 			}
 		});	
	};
	var noFn=function(){
	 	return false;
	};
	baseinfo.expressCity.expressCityConfirmAlert(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.isSureToDisabled'),yesFn,noFn);
}

//作废
baseinfo.expressCity.disableExpressCity = function(grid, rowIndex, colIndex){
	
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	

	var yesFn=function(){
		// 调用
		Ext.Ajax.request({
			url:baseinfo.realPath('disableExpressCity.action'),
			params:{
				'expressCityVo.expressCityQueryDto.selectedIds':selection.data.id
			},
			success:function(response){
				
				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
							if(result.expressCityVo.expressCityResultDtoList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
 				Ext.MessageBox.show({
					title :baseinfo.expressCity.i18n('foss.baseinfo.tips'),
					msg : result.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
 				
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
							if(result.expressCityVo.expressCityResultDtoList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
			}
		});	
	};
	var noFn=function(){
	 	return false;
	};
	baseinfo.expressCity.expressCityConfirmAlert(baseinfo.expressCity.i18n('foss.baseinfo.expressCity.isSureToDisabled'),yesFn,noFn);
}

Ext.define('Foss.baseinfo.expressCity.ExpressCityTypeStore', {
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

//查询 Form
Ext.define('Foss.baseinfo.expressCity.ExpressCityQueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.expressCity.i18n('foss.baseinfo.queryCondition'),
	frame:true,
	collapsible: true,
	height:200,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [
				{
					xtype : 'combobox',
					name : 'expressCityType',
					fieldLabel : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.expressCityType'),
					columnWidth : .4,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : Ext.create('Foss.baseinfo.expressCity.ExpressCityTypeStore', {
						data : {
							'items' : [{
									'valueCode' : '',
									'valueName' :  baseinfo.expressCity.i18n('foss.baseinfo.alllabel')
								}, {
									'valueCode' : baseinfo.expressCity.EXPRESSCITY_TYPE_L,
									'valueName' : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.luodipeiCity')
								}, {
									'valueCode' : baseinfo.expressCity.EXPRESSCITY_TYPE_P,
									'valueName' : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.poiltCity')
								}
							]
						}
					}),
					value : ''
				},{
					xtype:'container',
					border:false,
					height:24,
					html:'&nbsp;',
					columnWidth:.4
				},{
					xtype : 'linkregincombselector',
					type : 'P-C',
					fieldLabel:baseinfo.expressCity.i18n('foss.baseinfo.expressCity.administrative'),
					//hideLabel:true,
					provinceWidth:150,// 省份长度
					provinceLabel:baseinfo.expressCity.i18n('foss.baseinfo.expressCity.prov'),// 省份label
					provinceName:'provCode',// 省份名称—对应name
					cityWidth:150,// 城市长度
					cityLabel:baseinfo.expressCity.i18n('foss.baseinfo.expressCity.city'),// 城市label
					cityName:'cityCode',// 城市name
					provinceIsBlank:true,
					cityIsBlank:true,
					labelWid : 40,
					columnWidth:.8
					
				},{
					xtype:'container',
					border:false,
					height:24,
					html:'&nbsp;',
					columnWidth:.1
				},{
					border: 1,
					xtype:'container',
					columnWidth:1,
					defaultType:'button',
					layout:'column',
					items:[{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.8
						},
					  	{
						  text:baseinfo.expressCity.i18n('foss.baseinfo.query'),
						  columnWidth:.1,
						  cls:'yellow_button',  
						  handler:function(){
							  var form=this.up('form');
							  baseinfo.expressCity.expressCityListQuery(form)
						  }
					  	}]
				}
				]			
});

Ext.define('Foss.baseinfo.expressCity.ExpressCityListModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'districtCode'
	},{
		name:'provCode'
	},{
		name:'provName'
	},{
		name:'cityCode'
	},{
		name:'cityName'		
	},{	
		name:'type'
	},{
		name:'createUserCode'
	},{
		name:'modifyUserCode'
	},{
		name:'versionNo'		
	}]
});
//store
Ext.define('Foss.baseinfo.expressCity.ExpressCityListStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressCity.ExpressCityListModel',
	pageSize: 20,
//	sorters: [{
//	     property: 'operateTime',
//	     direction: 'DESC'
//	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryExpressCity.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'expressCityVo.expressCityResultDtoList',
			totalProperty:'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//城市列表
Ext.define('Foss.baseinfo.expressCity.ExpressCityListGrid',{
	extend:'Ext.grid.Panel',
    title: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.queryResultList'),
    frame:true,
    collapsible: true,
    hidden:false,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressCity.ExpressCityListStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:baseinfo.expressCity.i18n('foss.baseinfo.operate'),
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:baseinfo.expressCity.i18n('foss.baseinfo.update'),
			//disabled:!baseinfo.expressCity.isPermission('baseinfo/updateExpressCity'),
			getClass : function (v, m, r, rowIndex) {
				if (baseinfo.expressCity.isPermission('baseinfo/updateExpressCity')) {
				    return 'deppon_icons_edit';
				} else {
				    return 'statementBill_hide';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressCity.showDetialExpressCity(grid, rowIndex, colIndex)
			}
    	},{
    		iconCls : 'deppon_icons_delete',
			tooltip:baseinfo.expressCity.i18n('foss.baseinfo.void'),
			//disabled:!baseinfo.expressCity.isPermission('baseinfo/disableExpressCity'),
			getClass : function (v, m, r, rowIndex) {
				if (baseinfo.expressCity.isPermission('baseinfo/disableExpressCity')) {
				    return 'deppon_icons_delete';
				} else {
				    return 'statementBill_hide';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressCity.disableExpressCity(grid, rowIndex, colIndex)
			}
    	}]
    
	},{//
		header: 'ID',
		dataIndex: 'id',
		hidden:true
	},{
		header: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.expressCityType'),
		width:240,
		dataIndex: 'type',
		renderer:function(value){
			//var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.OPERATING_LOG__OPERATE_BILL_TYPE);
    		//return displayField;
			if(value==baseinfo.expressCity.EXPRESSCITY_TYPE_P){
				return baseinfo.expressCity.i18n('foss.baseinfo.expressCity.poiltCity');
			}else if(value==baseinfo.expressCity.EXPRESSCITY_TYPE_L){
				return baseinfo.expressCity.i18n('foss.baseinfo.expressCity.luodipeiCity');
			}else{
				return value;
			}
		}
	},{
		header: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.prov'),
		width:240,
		dataIndex: 'provName'
	},{
		header: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.provCode'),
		width:350,
		dataIndex: 'provCode',
		hidden:true
	},{
		header: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.city'),
		width:350,
		dataIndex: 'cityName'
	},{
		header: baseinfo.expressCity.i18n('foss.baseinfo.expressCity.cityCode'),
		width:240,
		dataIndex: 'cityCode',
		hidden:true	
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
	
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text : baseinfo.expressCity.i18n('foss.baseinfo.add'),
				columnWidth :.06,
				disabled:!baseinfo.expressCity.isPermission('baseinfo/addExpressCity'),
				hidden:!baseinfo.expressCity.isPermission('baseinfo/addExpressCity'),
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.expressCity.addOrUpdateExpressCityWindow');
					}
					a_win.show();
				}
			},{
				xtype :'button',
				text : baseinfo.expressCity.i18n('foss.baseinfo.void'),
				columnWidth :.06,
				disabled:!baseinfo.expressCity.isPermission('baseinfo/disableExpressCity'),
				hidden:!baseinfo.expressCity.isPermission('baseinfo/disableExpressCity'),
				handler: baseinfo.expressCity.disableExpressCityBatch
			},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.8
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});

//新增/修改快递代理/试点城市界面城市类型Form
Ext.define('Foss.baseinfo.expressCity.addOrUpdateExpressCityTypeForm',{
	extend:'Ext.form.Panel',
	id : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id',
	frame:false,
	height:60,
	columnWidth : 0.98,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [
				{
					xtype : 'combobox',
					name : 'expressCityTypeName',
					fieldLabel : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.expressCityType'),
					columnWidth : .4,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					allowBlank:false,
					store : Ext.create('Foss.baseinfo.expressCity.ExpressCityTypeStore', {
						data : {
							'items' : [{
									'valueCode' : baseinfo.expressCity.EXPRESSCITY_TYPE_L,
									'valueName' : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.luodipeiCity')
								}, {
									'valueCode' : baseinfo.expressCity.EXPRESSCITY_TYPE_P,
									'valueName' : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.poiltCity')
								}
							]
						}
					}),
					value : baseinfo.expressCity.EXPRESSCITY_TYPE_L
				},{
					xtype : 'combobox',
					name : 'expressCityTypeCode',
					fieldLabel : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.expressCityType'),
					columnWidth : .4,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					hidden:true
				},{
					xtype:'container',
					border:false,
					height:24,
					html:'&nbsp;',
					columnWidth:.59
				}
				]			
});


/**
 * 新增/修改快递代理/试点城市左侧行政区域树store
 */
Ext.define('Foss.baseinfo.expressCity.AdministrativeRegionsTreeStore', {
	extend : 'Ext.data.TreeStore',
	autoSync : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryCityByParentDistrictCode.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
	},
	root : {
		id : '01',
		text : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.administrative'),
		expanded: true
	},
	sorters : [{
		property : 'leaf',
		direction : 'ASC',
		checked : false
	}],
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		}
	},
	nodeParam: 'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode'
});

//新增/修改快递代理/试点城市左侧行政区域树
Ext.define('Foss.baseinfo.expressCity.AdministrativeRegionsTreePanel', {
			extend : 'Ext.tree.Panel',
			id : 'Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id',
			width : 300,
			frame : true,
			oldFullPath : null,// 刷新之前展开的路径
			useArrows : true,
			rootVisible : true,
			viewConfig : {
//				plugins : {
//					ptype : 'treeviewdragdrop',
//					appendOnly : true
//				}
			},
			layoutConfig : {
				// 展开折叠是否有动画效果
				animate : true
			},
			oldId : null,
			// 左键单击事件
			treeLeftKeyAction : function(node, record, item, index, e) {
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.expressCity.AdministrativeRegionsTreeStore');	
				// 监听事件
				me.listeners = {
					checkchange : function(node, checked, eOpts) {
						node.addListeners
						// 下面一句，设置时，checked一个结点，会自动加载下级结点
						//changeChildrenState(node, checked, eOpts);
					},
					scrollershow : function(scroller) {
						if (scroller && scroller.scrollEl) {
							scroller.clearManagedListeners();
							scroller.mon(scroller.scrollEl, 'scroll',
									scroller.onElScroll, scroller);
						}
					},
					itemclick : me.treeLeftKeyAction
					// 单击事件
					// itemdblclick:treeDbLeftKeyAction//双击事件
				};
				me.callParent([cfg]);
			}
});

//新增/修改快递代理/试点城市右侧已选行政区域
Ext.define('Foss.baseinfo.expressCity.selectedAdministrativeRegionsPanel', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	title:baseinfo.expressCity.i18n('foss.baseinfo.expressCity.addedAdministrative'),
	store:null,
	width : 400,
	flex : 1,
	frame : true,
	columns : [{
				header : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.prov'),
				dataIndex : 'provName',
				width : 160
			},{
				header : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.city'),
				dataIndex : 'cityName',
				width : 160
			}, {
				header : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.provCode'),
				hidden : true,
				dataIndex : 'provCode'
			}, {
				header : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.cityCode'),
				hidden : true,
				dataIndex : 'cityCode'
			},{
				header : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.configedCityCode'),
				hidden : true,
				dataIndex : 'districtCode'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			},
			itemdblclick : function(ths, record, item, index, e, eOpts) {
				ths.store.remove(record);
				var root = Ext.getCmp('Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id').getRootNode();
				var findchildnode = function(node) {
					var parentNode;
					if (record.raw.parentId == node.data.id) {
						parentNode = node;
						return parentNode;
					}
					if (node.hasChildNodes()) { // 判断子节点下是否存在子节点
						var childnodes = node.childNodes;
						for (var i = 0; i < childnodes.length; i++) { // 从节点中取出子节点依次遍历
							if (record.raw.parentId == childnodes[i].data.id) {
								parentNode = childnodes[i];
								return parentNode;
							}
							findchildnode(childnodes[i]); // 如果存在子节点 递归
						}
					}
				}
				var parentNode = findchildnode(root);
				parentNode.appendChild(record);
			}
		}
		me.store = Ext.create('Foss.baseinfo.expressCity.selectedAdministrativeRegionsStore');
		me.callParent([cfg]);
	}
});


Ext.define('Foss.baseinfo.expressCity.selectedAdministrativeRegionsStore', {
			extend : 'Ext.data.Store',
			//fields : ['provName', 'provCode', 'cityName','cityCode'],
			model:'Foss.baseinfo.expressCity.ExpressCityListModel',
			record: null,
			setRecord: function(record){
				this.record = record;
			},
			getRecord: function(){
				return  this.record;
			}
//			proxy : {
//				type : 'ajax',
//				url :  baseinfo.realPath('searchUserDepts.action'),
//				actionMethods : 'post',
//				reader : {
//					type : 'json',
//					root : 'nodes'
//				}
//			}
		});

//按钮panel
Ext.define('Foss.baseinfo.expressCity.LeftRightButtonPanel', {
	extend : 'Ext.panel.Panel',
	id:'Foss_baseinfo_expressCity_LeftRightButtonPanel_ID',
	// height : 150,
	width : 80,
	// frame:true,
	// 左移框
	leftMoveFrame : null,
	// 右移框
	rightMoveFrame : null,

	setLeftMove : function(a_moveFrame) {
		this.leftMoveFrame = a_moveFrame;
	},
	getLeftMove : function() {
		return this.leftMoveFrame;
	},
	setRightMove : function(a_moveFrame) {
		this.rightMoveFrame = a_moveFrame;
	},
	getRightMove : function() {
		return this.rightMoveFrame;
	},
	//获取树的全部节点
	getTreeAllNode : function() {
		var me = this;
		var nodes = new Array();
		var rootNode = me.getLeftMove().getRootNode();
		me.getNode(rootNode, nodes);
		return nodes;
	},
	//把节点中的节点和子节点，依序（递归）添加至数组中
	getNode : function(node, nodes) {
		var me = this;
		nodes.push(node);
		if (node.hasChildNodes) {
			node.eachChild(function(cNode) {
						// nodes.push(cNode);
						me.getNode(cNode, nodes);
					})
				/*		
			var a_nodes =node.childNodes;
			if(a_nodes && a_nodes.length>0){
				for(var i=0,l=a_nodes.length;i<l;i++){
					nodes.push(a_nodes[i]);
				}
				
			}
			*/
		}
		return nodes;
	},
	// 右移全部
	rightMoveAll : function() {
		var me = this;
		var treeNodes = me.getTreeAllNode();
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		rightStore.removeAll();
		Ext.Array.each(treeNodes, function(item, index, allNodes) {
					if (!Ext.isEmpty(item.data.parentId)) {
						rightStore.add(item.raw);
					}
				});
	},
	// 右移
	rightMove : function() {
		var me = this;
		var rightStore = this.getRightMove().getStore();
		var checked = me.getLeftMove().getView().getChecked();
		if (checked.length < 1) {
			return false;
		}
		for(var i=0;i<checked.length;i++){
		    if(checked[i].data.parentId=='100000'
		    	||checked[i].data.id=='100000'){
		    	Ext.MessageBox.show({
					title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
					msg : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.cannotSelectCountryOrPorv'),
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
		        return false;
		    }
		}
		
		var selections = this.getRightMove().getStore().data.items;
		
		Ext.Array.each(checked, function(item, index, allItems) {
					var isSelected = false;
					Ext.Array.each(selections, function(gridItem, gridIndex,
									gridItems) {
								if (item.data.id == gridItem.data.cityCode) {
									isSelected = true;
								}
							})
					if (!isSelected) {
						var selectedExpressCityDto = new Object();
						selectedExpressCityDto.provName=item.raw.entity.parentDistrictName;
						selectedExpressCityDto.provCode=item.raw.entity.parentDistrictCode;
						selectedExpressCityDto.cityName=item.raw.entity.name;
						selectedExpressCityDto.cityCode=item.raw.entity.code;
						var record = new Foss.baseinfo.expressCity.ExpressCityListModel(selectedExpressCityDto);
						rightStore.add(record);
					}
				})
	},
	// 左移全部
	leftMoveAll : function() {
		var me = this;
		var rightStore = this.getRightMove().getStore();
		var leftStore = this.getLeftMove().getStore();
		rightStore.each(function(record) {
					// var moveRecord = {
					// 'name' : record.get('name'),
					// 'code' : record.get('code')
					// };
					// leftStore.addMembers(record);
				});
		rightStore.removeAll();
	},
	// 左移
	leftMove : function() {
		var me = this;
		var selections = this.getRightMove().getSelectionModel().getSelection();
		if (selections.length < 1) {
			return;
		}
		var treeNodes = me.getTreeAllNode();
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		Ext.Array.each(selections, function(gridItem, girdIndex, allRows) {
					Ext.Array.each(treeNodes, function(treeItem, treeIndex,
									allNodes) {
								if (treeItem.data.id == gridItem.data.cityCode) {
									treeItem.data.checked = false;
									treeItem.updateInfo({
												checked : false
											});
								}
							})
				});
		this.getRightMove().getStore().remove(selections);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = {
			xtype : 'button',
			width : 60,
			disabled : false,
			height : 20,
			margin : '20 0 0 10'
		};
		me.items = [{
//					text : '>>',
//					margin : '50 0 0 10',
//					handler : function() {
//						me.rightMoveAll();
//					}
//				}, {
					text : '>',
					margin : '150 0 0 10',
					handler : function() {
						me.rightMove();
					}
				}, {
					text : '<',
					handler : function() {
						me.leftMove();
					}
//				}, {
//					text : '<<',
//					handler : function() {
//						me.leftMoveAll();
//					}
				}]
		me.callParent([cfg]);
	}
});

//新增/修改快递代理/试点城市界面panel
Ext.define('Foss.baseinfo.expressCity.addOrUpdateExpressCityPanel', {
	extend : 'Ext.panel.Panel',
	id : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityPanel_Id',
	//itemId : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityPanel_ItemId',
	height : 450,
	columnWidth : 0.99,
	layout : 'column',
	layout : {
		type : 'hbox',
		align : 'stretch'
	},
	defaults : {
		readOnly : false
	},
	frame : true,
	initComponent : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var selectableAdministrativeRegionsTree = Ext.create('Foss.baseinfo.expressCity.AdministrativeRegionsTreePanel');
		var selectedAdministrativeRegionsPanel = Ext.create('Foss.baseinfo.expressCity.selectedAdministrativeRegionsPanel');
		var selectButtonPanel = Ext.create('Foss.baseinfo.expressCity.LeftRightButtonPanel');
		selectButtonPanel.setRightMove(selectedAdministrativeRegionsPanel);
		selectButtonPanel.setLeftMove(selectableAdministrativeRegionsTree);
		me.items = [
		            selectableAdministrativeRegionsTree,
		            selectButtonPanel,
		            selectedAdministrativeRegionsPanel
		            ];
		me.callParent([cfg]);
	}		
});

/**
 * 声明新增与修改快递代理/试点城市windows
 */
Ext.define('Foss.baseinfo.expressCity.addOrUpdateExpressCityWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id',
	title : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.addOrUpdateExpressCity'),
	width : 850,
	height : 650,
	// resizable : false,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'destroy',
	layout : 'column',
	getCancelButton : function(me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				text : baseinfo.expressCity.i18n('foss.baseinfo.cancel'),
				// margin : '0 397 0 0',
				hidden : false,
				handler : function() {
					var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
					selectedGrid.store.removeAll();
					var treePanel = Ext.getCmp('Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id');
					var treeChecked = treePanel.getChecked();
					for(var i=0;i<treeChecked.length;i++){
					    treeChecked[i].data.checked = false;
						treeChecked[i].updateInfo({
								checked : false
						});
					}
					treePanel.collapseAll();
					var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
					a_win.setVisible(false);
				}
			});
		}
		return this.cancelButton;
	},
	getResetButton : function(me) {
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				hidden : false,
				cls : 'yellow_button',
				name : 'reset',
				text : baseinfo.expressCity.i18n('foss.baseinfo.reset'),
				margin : '0 10 0 0',
				handler : function() {
					var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
					selectedGrid.store.removeAll();
					selectedGrid.store.add(selectedGrid.store.record);
					var treePanel = Ext.getCmp('Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id');
					var treeChecked = treePanel.getChecked();
					for(var i=0;i<treeChecked.length;i++){
					    treeChecked[i].data.checked = false;
						treeChecked[i].updateInfo({
								checked : false
						});
					}
					treePanel.collapseAll();
				}
			});
		}
		return this.resetButton;
	},
	saveUserDepts : function() {
		var wind = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');

	},
	getSaveButton : function() {
		var me = this;
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
				cls : 'yellow_button',
				text : baseinfo.expressCity.i18n('foss.baseinfo.save'),
				//hidden:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthoritySaveButton'),
				margin : '0 10 0 0',
				handler : function() {
					
					var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
					
					var toDelRecord = selectedGrid.store.record;
					var selections = selectedGrid.store.data.items;;
					
					if(selections.length==0){
						Ext.MessageBox.show({
							title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
							msg : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.atLeastConfigOneDate'),
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
				        return false;
					}
					
					//调用保存方法
					if(toDelRecord==null||toDelRecord==''){
						
						var addOrUpdateExpressCityTypeForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
						var type = addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeName').getValue();
						
						var jsonData = new Array();
						for(var i=0;i<selections.length;i++){
							var expressCityEntity = new Object();
							expressCityEntity.districtCode = selections[i].data.cityCode;
							expressCityEntity.type = type;
							jsonData.push(expressCityEntity);
						}
						var expressCityQueryDto = new Object();
						expressCityQueryDto.newExpressCityEntityList = jsonData;
						var expressCityVo = new Object();
						expressCityVo.expressCityQueryDto = expressCityQueryDto;

						
						var expressCityGrid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
						var params = expressCityGrid.store.getSubmitParams();
						if(params.expressCityVo==null||params.expressCityVo==''){
							Ext.apply(params, {
								'expressCityVo.expressCityQueryDto.type' : null,
								'expressCityVo.expressCityQueryDto.districtCode' : null
							});
							expressCityGrid.store.setSubmitParams(params);
						}
							
						Ext.Ajax.request({
							url:baseinfo.realPath('addExpressCity.action'),
							jsonData:{'expressCityVo':expressCityVo},
							method:'post',
							success:function(response){
								
								//关掉修改win
								var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
								selectedGrid.store.removeAll();
								var treePanel = Ext.getCmp('Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id');
								var treeChecked = treePanel.getChecked();
								for(var i=0;i<treeChecked.length;i++){
								    treeChecked[i].data.checked = false;
									treeChecked[i].updateInfo({
											checked : false
									});
								}
								treePanel.collapseAll();
								var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
								a_win.setVisible(false);
								
								//重新查询数据
								var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
								grid.store.loadPage(1,{
								      callback: function(records, operation, success) {
								    	  
								    	//抛出异常  
									    var rawData = grid.store.proxy.reader.rawData;
									    if(!success && ! rawData.isException){
									    	Ext.MessageBox.show({
												title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
												msg : rawData.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
											return false;
										}  
								    	//正常返回
								    	if(success){
								    		var result =   Ext.decode(operation.response.responseText);
											if(result.expressCityVo.expressCityResultDtoList.length>0){
												grid.show();
											}
								    	}
								       }
								    }); 	
							},
							exception:function(response){
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.show({
									title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
									msg : result.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								return false;
							}			
						});
						
					//调用修改方法	
					}else{
						
						var addOrUpdateExpressCityTypeForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
						var type = addOrUpdateExpressCityTypeForm.getForm().findField('expressCityTypeCode').getValue();
						
						//验证是否修改了数据
						if(selections.length==1){
							if(selections[0].get('cityCode')==toDelRecord.data.cityCode
								||selections[0].get('cityCode')==toDelRecord.data.districtCode){
								Ext.MessageBox.show({
									title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
									msg : baseinfo.expressCity.i18n('foss.baseinfo.expressCity.noDataChangeAlert'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								return false;
							}
						}
						
						//提交后台修改
						var jsonData = new Array();
						for(var i=0;i<selections.length;i++){
							var expressCityEntity = new Object();
							expressCityEntity.districtCode = selections[i].data.cityCode;
							expressCityEntity.type = type;
							jsonData.push(expressCityEntity);
						}
						var expressCityQueryDto = new Object();
						expressCityQueryDto.newExpressCityEntityList = jsonData;
						
						var expressCityEntityTemp = new Object();
						expressCityEntityTemp.districtCode = toDelRecord.data.districtCode;
						expressCityEntityTemp.type = type;
						expressCityQueryDto.expressCityEntity = expressCityEntityTemp;
						
						var expressCityVo = new Object();
						expressCityVo.expressCityQueryDto = expressCityQueryDto;

						Ext.Ajax.request({
							url:baseinfo.realPath('updateExpressCity.action'),
							jsonData:{'expressCityVo':expressCityVo},
							method:'post',
							success:function(response){
								
								//关掉修改win
								var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
								selectedGrid.store.removeAll();
								var treePanel = Ext.getCmp('Foss_baseinfo_expressCity_AdministrativeRegionsTreePanel_Id');
								var treeChecked = treePanel.getChecked();
								for(var i=0;i<treeChecked.length;i++){
								    treeChecked[i].data.checked = false;
									treeChecked[i].updateInfo({
											checked : false
									});
								}
								treePanel.collapseAll();
								var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
								a_win.setVisible(false);
								
								//重新查询数据
								var grid = Ext.getCmp('T_baseinfo-expressCityindex_content').getExpressCityGrid();
								grid.store.loadPage(1,{
								      callback: function(records, operation, success) {
								    	  
								    	//抛出异常  
									    var rawData = grid.store.proxy.reader.rawData;
									    if(!success && ! rawData.isException){
									    	Ext.MessageBox.show({
												title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
												msg : rawData.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
											return false;
										}  
								    	//正常返回
								    	if(success){
								    		var result =   Ext.decode(operation.response.responseText);
											if(result.expressCityVo.expressCityResultDtoList.length>0){
												grid.show();
											}
								    	}
								       }
								    }); 	
							},
							exception:function(response){
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.show({
									title : baseinfo.expressCity.i18n('foss.baseinfo.tips'),
									msg : result.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								return false;
							}			
						});
					}
				}
			});
		}
		return this.saveButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.expressCity.addOrUpdateExpressCityTypeForm'),
		            Ext.create('Foss.baseinfo.expressCity.addOrUpdateExpressCityPanel')
		            ];
		// me.getResetButton(),
		me.fbar = [me.getResetButton(), me.getSaveButton(), me.getCancelButton()]
		me.callParent([cfg]);
	}
})

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-expressCityindex_content')) {
		return;
	} 
	
	//查询FORM
	var expressCityQueryForm = Ext.create('Foss.baseinfo.expressCity.ExpressCityQueryForm');
	
	//显示grid
	var expressCityGrid = Ext.create('Foss.baseinfo.expressCity.ExpressCityListGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-expressCityindex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getExpressCityQueryForm:function(){
			return expressCityQueryForm;
		},
		getExpressCityGrid:function(){
			return expressCityGrid;
		},
		items: [expressCityQueryForm,expressCityGrid],
		renderTo : 'T_baseinfo-expressCityindex-body'
	});
});