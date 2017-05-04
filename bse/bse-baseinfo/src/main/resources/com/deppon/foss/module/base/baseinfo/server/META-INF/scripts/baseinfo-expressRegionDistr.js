/**
 * @author 094463-foss-xieyantao
 * 快递大区与行政区域映射关系主界面
 */
baseinfo.expressRegionDistr.expressCityConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//消息提醒框
baseinfo.expressRegionDistr.showWarningMsg = function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	};

/**
 * Form查询方法: 
 */
baseinfo.expressRegionDistr.expressCityListQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var areaCode = form.findField('areaCode').getValue();
	var orgCode = form.findField('orgCode').getValue();
	var cityCode = form.findField('cityCode').getValue();
	var provCode = form.findField('provCode').getValue();
	
	//如果选择了省份，就一定要选择到市
	if(provCode!=null && provCode!=''){
		if(cityCode==null || cityCode==''){
			Ext.MessageBox.show({
				title : '温馨提示',
				msg : '通过行政区域查询必须选择到市',
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
		'regionDistrVo.entity.countyCode' : areaCode,
		'regionDistrVo.entity.cityCode' : cityCode,
		'regionDistrVo.entity.orgCode' : orgCode
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
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
				if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
					grid.show();
				}
	    	}
	       }
	    }); 
}

//修改明细
baseinfo.expressRegionDistr.showDetialExpressCity = function(grid, rowIndex, colIndex){

	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	var id = selection.data.id;
	
	// 打开注释
	Ext.Ajax.request({
		url:baseinfo.realPath('queryBigRegionDistrById.action'),
		params:{
			'regionDistrVo.entity.id':id
		},
		success:function(response){
			var result = Ext.decode(response.responseText);	
			// 获取弹出窗口
			var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
			if (Ext.isEmpty(a_win)) {
				a_win = Ext.create('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityWindow');
			}
			//获取要修改的信息实体
			var entity = result.regionDistrVo.entity;
			if(!Ext.isEmpty(entity)){
				
				var orgForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
				//设置部门名称
				orgForm.getForm().findField('orgCode').setCombValue(entity.orgName,entity.orgCode);
				orgForm.getForm().findField('orgCode').setReadOnly(true);
				
				var selectedRecord = new Foss.baseinfo.expressRegionDistr.ExpressCityListModel(entity);
				var panelGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
				panelGrid.store.removeAll();
				panelGrid.store.add(selectedRecord);
				panelGrid.store.setRecord(selectedRecord);
			}
			a_win.show();		
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

//批量作废
baseinfo.expressRegionDistr.disableExpressCityBatch = function(){
	
	// grid
	var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.MessageBox.show({
			title : '温馨提示',
			msg : '请至少选择一条数据进行操作',
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
 			url:baseinfo.realPath('deleteBigRegionDistr.action'),
 			params:{
 				'regionDistrVo.codeList':selectDisableIds
 			},
 			success:function(response){
 				var json = Ext.decode(response.responseText);
 				baseinfo.expressRegionDistr.showWarningMsg('温馨提醒',json.message);
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
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
							if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
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
 				
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
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
							if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
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
	baseinfo.expressRegionDistr.expressCityConfirmAlert('是否确认作废',yesFn,noFn);
}

//作废
baseinfo.expressRegionDistr.disableExpressCity = function(grid, rowIndex, colIndex){
	
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	

	var yesFn=function(){
		// 调用
		Ext.Ajax.request({
			url:baseinfo.realPath('deleteBigRegionDistr.action'),
			params:{
				'regionDistrVo.codeList':selection.data.id
			},
			success:function(response){
				var json = Ext.decode(response.responseText);
 				baseinfo.expressRegionDistr.showWarningMsg('温馨提醒',json.message);
				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
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
							if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
								grid.show();
							}
				    	}
				       }
				    }); 
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
 				
 				//重新查询数据
				var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
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
							if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
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
	baseinfo.expressRegionDistr.expressCityConfirmAlert('是否确认作废',yesFn,noFn);
}

Ext.define('Foss.baseinfo.expressRegionDistr.ExpressCityTypeStore', {
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

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.expressRegionDistr.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.expressRegionDistr.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_expressRegionDistr_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	items :[{
		name : 'orgCode',
		fieldLabel : '部门名称',
		columnWidth : .25,
        xtype : 'dynamicorgcombselector',
        type : 'ORG',
        expressBigRegion:'Y',//--是否快递大区
        colspan:3
	}, {
		fieldLabel : '行政区域',
//		id:'test_ssq',
		labelWidth:100,
		provinceWidth : 160,
		cityWidth : 160,
		cityLabel : '市',
		cityName : 'cityCode',//名称
		provinceLabel : '省',
		provinceName:'provCode',//省名称
		areaLabel : '县',
		areaName : 'areaCode',// 县名称
		areaWidth : 160,
		type : 'P-C-C',
		xtype : 'linkregincombselector',
		provinceIsBlank:true,
		cityIsBlank:true,
		areaIsBlank : true,
		areaLabelWidth:null,
		colspan:3
	},{
		xtype : 'container',
		colspan:3,
		defaultType:'button',
		layout:'column',
		items : [{
			xtype : 'button', 
			text:baseinfo.expressRegionDistr.i18n('foss.baseinfo.reset'),
			columnWidth:.1,
			handler: function(){
				var form=this.up('form').getForm();
				form.reset();
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.535
		},{
			xtype : 'button', 
			width:70,
			columnWidth:.1,
			text : baseinfo.expressRegionDistr.i18n('foss.baseinfo.query'),//查询
//			hidden:!baseinfo.destinationLine.isPermission('destinationLine/destinationLineQueryButton'),
			cls:'yellow_button',
			handler : function() {
				var form=this.up('form');
				baseinfo.expressRegionDistr.expressCityListQuery(form)
			}
		}]
	
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 快递大区-行政区域映射关系Model
 */
Ext.define('Foss.baseinfo.expressRegionDistr.ExpressCityListModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'orgCode', //部门编码
		type : 'string'
	},{
		name:'orgName', //部门名称
		type : 'string'
	},{
		name:'districtCode',//行政区域编码
		type : 'string'
	},{
		name:'districtName',//行政区域名称
		type : 'string'
	},{
		name:'provCode',//省份编码
		type : 'string'
	},{
		name:'provName',//省份名称
		type : 'string'
	},{
		name:'cityCode',//城市编码
		type : 'string'
	},{
		name:'cityName',//城市名称
		type : 'string'
	},{
		name:'countyCode',//区县编码
		type : 'string'
	},{
		name:'countyName',//区县名称
		type : 'string'
	},{
		name:'active',
		type : 'string'
	},{
		name:'createUser',
		type : 'string'
	},{
		name:'modifyUser',
		type : 'string'
	},{
		name:'versionNo',
		type : 'string'
	}]
});

/**
 * 快递大区-行政区域映射关系 Store
 */
Ext.define('Foss.baseinfo.expressRegionDistr.ExpressCityListStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressRegionDistr.ExpressCityListModel',
	pageSize: 20,
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryBigRegionDistrs.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'regionDistrVo.entityList',
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
Ext.define('Foss.baseinfo.expressRegionDistr.ExpressCityListGrid',{
	extend:'Ext.grid.Panel',
    title: '查询结果列表',
    frame:true,
    collapsible: true,
    hidden:false,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressRegionDistr.ExpressCityListStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:'修改',
//			getClass:function(v,m,r,rowIndex){
//				if(!pay.payment.isPermission('/stl-web/pay/detailPaymentA.action')){
//					return 'statementBill_hide';
//				}else{
//					return 'deppon_icons_showdetail';
//				}
//			},
			disabled: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/updatebutton'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressRegionDistr.showDetialExpressCity(grid, rowIndex, colIndex)
			}
    	},{
    		iconCls : 'deppon_icons_delete',
			tooltip:'作废',
//			getClass:function(v,m,r,rowIndex){
//				if(!pay.payment.isPermission('/stl-web/pay/disabledBillPaymentA.action')){
//					return 'statementBill_hide';
//				}else{
//					return 'deppon_icons_delete';
//				}
//			},
			disabled: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/deletebutton'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressRegionDistr.disableExpressCity(grid, rowIndex, colIndex)
			}
    	}]
    
	},{
		header: '部门名称',
		width:240,
		dataIndex: 'orgName'
	},{
		header: '行政区域',
		flex:1,
		dataIndex: 'districtName'
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
				text : '新增',
				columnWidth :.06,
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityWindow');
					}
					
					var orgForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
					//设置部门名称为可选
					orgForm.getForm().reset();
					orgForm.getForm().findField('orgCode').setReadOnly(false);
					
					a_win.show();
				},
//				handler: pay.operatingLog.OperatingLogListExport,
				disabled: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/addbutton'),
				hidden: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/addbutton')
			},{
				xtype :'button',
				text : '作废',
				columnWidth :.06,
				handler: baseinfo.expressRegionDistr.disableExpressCityBatch,
				disabled: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/deletebutton'),
				hidden: !baseinfo.expressRegionDistr.isPermission('expressRegionDistr/deletebutton')
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

//新增/修改快递大区与行政区域映射Form
Ext.define('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityTypeForm',{
	extend:'Ext.form.Panel',
	id : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id',
	frame:false,
	height:120,
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
				name : 'orgCode',
				fieldLabel : '部门名称',
				columnWidth : .4,
		        xtype : 'dynamicorgcombselector',
		        type : 'ORG',
		        expressBigRegion:'Y'//--是否快递大区
			},{
					xtype:'container',
					border:false,
					height:24,
					html:'&nbsp;',
					columnWidth:.59
			}
			],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}			
			
});


/**
 * 新增/修改快递大区与行政区域映射左侧行政区域树store
 */
Ext.define('Foss.baseinfo.expressRegionDistr.AdministrativeRegionsTreeStore', {
	extend : 'Ext.data.TreeStore',
	autoSync : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryDistrictCode.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
	},
	root : {
		id : '01',
		text : '行政区域',
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

//新增/修改快递大区与行政区域映射左侧行政区域树
Ext.define('Foss.baseinfo.expressRegionDistr.AdministrativeRegionsTreePanel', {
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
			/**
			* 父节点选中时，选择所有子节点
			*/
			selectChildFunction : function(node, checked) {
				var me = this;
				var a_code=node.data.id;
				Ext.Array.each(node.childNodes, function(childNode) {
					me.selectChildFunction(childNode, checked);
					childNode.set("checked", checked);	   
				});
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.expressRegionDistr.AdministrativeRegionsTreeStore');	
				// 监听事件
				me.listeners = {
					checkchange : function(node, checked, eOpts) {
						//如果为地区市级别，同时这是子节点的状态和该节点一致 
//						if (node.data.depth==3){
//							this.selectChildFunction(node, checked);
//						}
					
						node.addListeners;
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

//新增/修改快递大区与行政区域映射右侧已选行政区域
Ext.define('Foss.baseinfo.expressRegionDistr.selectedAdministrativeRegionsPanel', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	title:'已授权行政区域',
	store:null,
	width : 500,
	addList:null,//存放添加的数据
	deleteList:null,//存放删除的数据
	flex : 1,
	frame : true,
	columns : [{
				header : '省',
				dataIndex : 'provName',
				width : 150
			},{
				header : '市',
				dataIndex : 'cityName',
				width : 120
			},{
				header : '区县',
				dataIndex : 'countyName',
				flex:1
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
		me.store = Ext.create('Foss.baseinfo.expressRegionDistr.selectedAdministrativeRegionsStore');
		me.callParent([cfg]);
	}
});

/**
 * 选择行政区域store
 */
Ext.define('Foss.baseinfo.expressRegionDistr.selectedAdministrativeRegionsStore', {
			extend : 'Ext.data.Store',
			model:'Foss.baseinfo.expressRegionDistr.ExpressCityListModel',
			record: null,
			setRecord: function(record){
				this.record = record;
			},
			getRecord: function(){
				return  this.record;
			},
//			//构造函数
		    constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

//按钮panel
Ext.define('Foss.baseinfo.expressRegionDistr.LeftRightButtonPanel', {
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
					title : '温馨提示',
					msg : '请勿直接选择国家/省份,请选择到市',
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
		        return false;
		    }
		}
		
		var selections = this.getRightMove().getStore().data.items;
		//定义一个存放新增对象集合
		var addList = new Array();
		Ext.Array.each(checked, function(item, index, allItems) {
					var isSelected = false;
					Ext.Array.each(selections, function(gridItem, gridIndex,
							gridItems) {
							
						if(gridItem.data.countyCode!=null&&gridItem.data.countyCode!=''){
							if (item.data.id == gridItem.data.countyCode) {
								isSelected = true;
								baseinfo.expressRegionDistr.showWarningMsg('温馨提示','所选行政区域已经添加！');
							}
						}else if(gridItem.data.cityCode!=null&&gridItem.data.cityCode!=''){
							if (item.data.id == gridItem.data.cityCode) {
								isSelected = true;
								baseinfo.expressRegionDistr.showWarningMsg('温馨提示','所限行政区域已经添加！');
							}
						}		
					});
					if (!isSelected) {
						var selectedExpressCityDto = new Object();
						if(item.raw.leaf){//如果是子节点（选择是区县）
							//设置省
							selectedExpressCityDto.provName=item.parentNode.raw.entity.parentDistrictName;
							selectedExpressCityDto.provCode=item.parentNode.raw.entity.parentDistrictCode;
							//设置市
							selectedExpressCityDto.cityName=item.raw.entity.parentDistrictName;
							selectedExpressCityDto.cityCode=item.raw.entity.parentDistrictCode;
							//设置区县
							selectedExpressCityDto.countyName=item.raw.entity.name;
							selectedExpressCityDto.countyCode=item.raw.entity.code;
						}else{
							selectedExpressCityDto.provName=item.raw.entity.parentDistrictName;
							selectedExpressCityDto.provCode=item.raw.entity.parentDistrictCode;
							selectedExpressCityDto.cityName=item.raw.entity.name;
							selectedExpressCityDto.cityCode=item.raw.entity.code;
						}
						
						var record = new Foss.baseinfo.expressRegionDistr.ExpressCityListModel(selectedExpressCityDto);
						rightStore.add(record);
						//把添加的数据放到集合里面
						addList.push(record);
					}
		});
		//获取已选grid
		var grid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
		grid.addList = addList;
			
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
		//定义删除数据集合
		var deleteList = new Array();
		Ext.Array.each(selections, function(gridItem, girdIndex, allRows) {
			var record = new Foss.baseinfo.expressRegionDistr.ExpressCityListModel(gridItem.data);
		//	var orgForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id').getForm();
		//	record.set('orgCode',orgForm.findField('orgCode').getValue());
			deleteList.push(record);
			Ext.Array.each(treeNodes, function(treeItem, treeIndex,
							allNodes) {
						if(gridItem.data.countyCode!=null&&gridItem.data.countyCode!=''){
							if (treeItem.data.id == gridItem.data.countyCode) {
							treeItem.data.checked = false;
							treeItem.updateInfo({
										checked : false
									});
							}
						}else if(gridItem.data.cityCode!=null&&gridItem.data.cityCode!=''){
							if (treeItem.data.id == gridItem.data.cityCode) {
							treeItem.data.checked = false;
							treeItem.updateInfo({
										checked : false
									});
							}
						}	
						
					})
		});
		this.getRightMove().getStore().remove(selections);
		//获取已选grid
		var grid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
		grid.deleteList = deleteList;
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

//新增/修改快递大区与行政区域映射界面panel
Ext.define('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityPanel', {
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
		var selectableAdministrativeRegionsTree = Ext.create('Foss.baseinfo.expressRegionDistr.AdministrativeRegionsTreePanel');
		var selectedAdministrativeRegionsPanel = Ext.create('Foss.baseinfo.expressRegionDistr.selectedAdministrativeRegionsPanel');
		var selectButtonPanel = Ext.create('Foss.baseinfo.expressRegionDistr.LeftRightButtonPanel');
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
 * 声明新增与修改快递大区-行政区域windows
 */
Ext.define('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressCity_addOrUpdateExpressCityWindow_Id',
	title : '新增/修改快递大区与行政区域映射',
	width : 950,
	height : 710,
	// resizable : false,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'destroy',
	layout : 'column',
	getCancelButton : function(me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				text : '取消',
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
					var addOrUpdateExpressCityTypeForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
					addOrUpdateExpressCityTypeForm.getForm().reset();
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
				text : '重置',
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
				text : '保存',
				//hidden:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthoritySaveButton'),
				margin : '0 10 0 0',
				handler : function() {
					
					var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
					
					var toDelRecord = selectedGrid.store.record;
					var selections = selectedGrid.store.data.items;;
					
					if(selections.length==0){
						Ext.MessageBox.show({
							title : '温馨提示',
							msg : '至少配置一条城市信息',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
				        return false;
					}
					//获取列表中记录
					/*var recordArray = new Array();
					if(!Ext.isEmpty(selectedGrid.store)){
						selectedGrid.store.each(function(record){
							recordArray.push(record.data);
						});
					}*/
					//调用保存方法
					if(toDelRecord==null||toDelRecord==''){
						
						var addOrUpdateExpressCityTypeForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
						//获取部门编码
						var orgCode = addOrUpdateExpressCityTypeForm.getForm().findField('orgCode').getValue();
						
						//判断快递大区是否选择
						if(orgCode==null||orgCode==''){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '部门名称为必填',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}
						
						var entityList = new Array();
						for(var i=0;i<selections.length;i++){
							selections[i].data.orgCode = orgCode;
							entityList.push(selections[i].data);
						}
					
						var jsonData = {'regionDistrVo':{'entityList':entityList}};
						
						var expressRegionGrid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
						var params = expressRegionGrid.store.getSubmitParams();
						if(params.regionDistrVo==null||params.regionDistrVo==''){
							Ext.apply(params, {
								'regionDistrVo.entity.countyCode' : null,
								'regionDistrVo.entity.cityCode' : null,
								'regionDistrVo.entity.orgCode' : null
							});
							expressRegionGrid.store.setSubmitParams(params);
						}
						
						Ext.Ajax.request({
							url:baseinfo.realPath('addBigRegionDistr.action'),
							jsonData:jsonData,
							method:'post',
							success:function(response){
								var json = Ext.decode(response.responseText);
 								baseinfo.expressRegionDistr.showWarningMsg('温馨提醒',json.message);
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
								addOrUpdateExpressCityTypeForm.getForm().reset();
								a_win.setVisible(false);
								
								//重新查询数据
								var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
								grid.store.loadPage(1,{
								      callback: function(records, operation, success) {
								    	  
								    	//抛出异常  
									    var rawData = grid.store.proxy.reader.rawData;
									    if(!success && ! rawData.isException){
									    	Ext.MessageBox.show({
												title : '温馨提示',
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
											if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
												grid.show();
											}
								    	}
								       }
								    }); 	
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
								return false;
							}			
						});
						
					//调用修改方法	
					}else{
						
						var orgForm = Ext.getCmp('Foss_baseinfo_expressCity_addOrUpdateExpressCityTypeForm_Id');
						//获取部门编码
						var orgCode = orgForm.getForm().findField('orgCode').getValue();
						
						//判断快递大区是否选择
						if(orgCode==null||orgCode==''){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '部门名称为必填',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}
						
						//提交后台修改
						var entityList = new Array();
						for(var i=0;i<selections.length;i++){
							//获取新增的数据
							if(Ext.isEmpty(selections[i].data.id)){
								selections[i].data.orgCode = orgCode;
								entityList.push(selections[i].data);
							}
						}
						//获取已选grid
						var cityGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
						var deleteList = cityGrid.deleteList;
						var deleteArray = new Array();
						if(!Ext.isEmpty(deleteList)){
							for(var j= 0; j < deleteList.length; j++){
								if(!Ext.isEmpty(deleteList[j].get('id'))){
									//获取已经删除的数据
									deleteArray.push(deleteList[j].data);
								}
							}
						}else{
							if(Ext.isEmpty(entityList)){
								baseinfo.expressRegionDistr.showWarningMsg('温馨提醒','未修改任何数据,请取消或关闭界面!');
								return false;
							}
						}
						var jsonData = {'regionDistrVo':{'addList':entityList,'deleteList':deleteArray}};
						Ext.Ajax.request({
							url:baseinfo.realPath('updateBigRegionDistr.action'),
							jsonData:jsonData,
							method:'post',
							success:function(response){
								var json = Ext.decode(response.responseText);
 								baseinfo.expressRegionDistr.showWarningMsg('温馨提醒',json.message);
								//关掉修改win
//								var selectedGrid = Ext.getCmp('Foss_baseinfo_expressCity_selectedAdministrativeRegionsPanel_Id');
								cityGrid.store.removeAll();
								cityGrid.addList = null;
								cityGrid.deleteList = null;
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
								orgForm.getForm().reset();
								a_win.setVisible(false);
								
								
								//重新查询数据
								var grid = Ext.getCmp('T_baseinfo-expressRegionDistrindex_content').getExpressCityGrid();
								grid.store.loadPage(1,{
								      callback: function(records, operation, success) {
								    	  
								    	//抛出异常  
									    var rawData = grid.store.proxy.reader.rawData;
									    if(!success && ! rawData.isException){
									    	Ext.MessageBox.show({
												title : '温馨提示',
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
											if(result.regionDistrVo.entityList!=null&&result.regionDistrVo.entityList.length>0){
												grid.show();
											}
								    	}
								       }
								    }); 	
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
		            Ext.create('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityTypeForm'),
		            Ext.create('Foss.baseinfo.expressRegionDistr.addOrUpdateExpressCityPanel')
		            ];
		// me.getResetButton(),
		me.fbar = [me.getResetButton(), me.getSaveButton(), me.getCancelButton()]
		me.callParent([cfg]);
	}
})

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-expressRegionDistrindex_content')) {
		return;
	} 
	
	//查询FORM
	var expressCityQueryForm = Ext.create('Foss.baseinfo.expressRegionDistr.QueryForm');
	
	//显示grid
	var expressCityGrid = Ext.create('Foss.baseinfo.expressRegionDistr.ExpressCityListGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-expressRegionDistrindex_content',
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
		renderTo : 'T_baseinfo-expressRegionDistrindex-body'
	});
});