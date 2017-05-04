//未卸车明细的Model 卡货 and 城际
Ext.define('Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'goodsQtyTotal',
		type : 'string'
	}, {
		name : 'handoverGoodsQty',
		type : 'string'
	}, {
		name : 'nounloadGoodsQty',
		type : 'string'
	}, {
		name : 'goodsWeightTotal',
		type : 'string'
	}, {
		name : 'goodsVolumeTotal',
		type : 'string'
	}, {
		name : 'nextOrgname',
		type : 'string'
	},{
		name : 'goodsWeightSum',
		type : 'string'
	},{
		name : 'goodsVolumeSum',
		type : 'string'
	}]
});

//未卸车明细的 store 卡货
Ext.define('Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailKHStore', {
	extend : 'Ext.data.Store',
	autoLoad : true,
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryNoUnloadGoodsDetailKH.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryUnloadingProgressVo.noUnloadGoodsDetailListKH',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'queryUnloadingProgressVo.taskId' : platform.noUnloadingProgressDetail.crurrentTaskId
					}
				});
		},
		'load' : function( store, records, successful, eOpts){
			var record;
			var weightSumTemp=0;
			var volumeSumTemp=0;
			for(var i in records){
				record = records[i];
				weightSumTemp =  record.get('goodsWeightSum');
				volumeSumTemp =  record.get('goodsVolumeSum');
			}
			Ext.getCmp('Foss.platform.noUnloadingProgressDetail_KH_Toolbar_WeightTotal_ID').setValue(weightSumTemp);
			Ext.getCmp('Foss.platform.noUnloadingProgressDetail_KH_Toolbar_VolumeTotal_ID').setValue(volumeSumTemp);
			
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//未卸车明细的列表 卡货
Ext.define('Foss.platform.noUnloadingProgressDetail.noUnloadingDetailKHGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '卡货',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailKHStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.noUnloadingProgressDetail.totalKHGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var actionUrl=platform.realPath('exportNoUnloadGoodsDetailKH.action');
			var exportParams = {
					'queryUnloadingProgressVo.taskId' : platform.noUnloadingProgressDetail.crurrentTaskId
			}; 
			if(!Ext.fly('downloadNoUnloadGoodsDetailKHFileForm')){
								var frm = document.createElement('form');
								frm.id = 'downloadNoUnloadGoodsDetailKHFileForm';
								frm.style.display = 'none';
								document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadNoUnloadGoodsDetailKHFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单件数'
	}, {
		dataIndex : 'handoverGoodsQty',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '交接件数'
	},{
		dataIndex : 'nounloadGoodsQty',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '未卸件数'
	},{
		dataIndex : 'goodsWeightTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单重量(公斤)'
	},{
		dataIndex : 'goodsVolumeTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单体积(方)'
	}, {
		dataIndex : 'nextOrgname',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '下一目的站'	
	}],dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总重量(吨)',
			   fieldLabel: '总重量(吨)',
			   id:'Foss.platform.noUnloadingProgressDetail_KH_Toolbar_WeightTotal_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'goodsWeightTotal_toolbar'
		   },{
			   //fieldLabel:'总体积(方)',
			   fieldLabel: '总体积(方)',
			   id:'Foss.platform.noUnloadingProgressDetail_KH_Toolbar_VolumeTotal_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'goodsVolumeTotal_toolbar'
		   }]
		}]
});


//未卸车明细的 store 城际
Ext.define('Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailCJStore', {
	extend : 'Ext.data.Store',
	//autoLoad : true,
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryNoUnloadGoodsDetailCJ.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryUnloadingProgressVo.noUnloadGoodsDetailListCJ',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'queryUnloadingProgressVo.taskId' : platform.noUnloadingProgressDetail.crurrentTaskId
					}
				});
		},
		'load' : function( store, records, successful, eOpts){
			var record;
			var weightSumTemp=0;
			var volumeSumTemp=0;
			for(var i in records){
				record = records[i];
				weightSumTemp =  record.get('goodsWeightSum');
				volumeSumTemp =  record.get('goodsVolumeSum');
			}
			Ext.getCmp('Foss.platform.noUnloadingProgressDetail_CJ_Toolbar_WeightTotal_ID').setValue(weightSumTemp);
			Ext.getCmp('Foss.platform.noUnloadingProgressDetail_CJ_Toolbar_VolumeTotal_ID').setValue(volumeSumTemp);
			
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


//未卸车明细的列表 城际
Ext.define('Foss.platform.noUnloadingProgressDetail.noUnloadingDetailCJGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '城际',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.noUnloadingProgressDetail.NoUnloadingInfoDetailCJStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.noUnloadingProgressDetail.totalCJPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var actionUrl=platform.realPath('exportNoUnloadGoodsDetailCJ.action');
			var exportParams = {
					'queryUnloadingProgressVo.taskId' : platform.noUnloadingProgressDetail.crurrentTaskId
			}; 
			if(!Ext.fly('downloadNoUnloadGoodsDetailCJFileForm')){
								var frm = document.createElement('form');
								frm.id = 'downloadNoUnloadGoodsDetailCJFileForm';
								frm.style.display = 'none';
								document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadNoUnloadGoodsDetailCJFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单件数'
	}, {
		dataIndex : 'handoverGoodsQty',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '交接件数'
	},{
		dataIndex : 'nounloadGoodsQty',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '未卸件数'
	},{
		dataIndex : 'goodsWeightTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单重量(公斤)'
	},{
		dataIndex : 'goodsVolumeTotal',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '开单体积(方)'
	}, {
		dataIndex : 'nextOrgname',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '下一目的站'		
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总重量(吨)',
			   fieldLabel: '总重量(吨)',
			   id:'Foss.platform.noUnloadingProgressDetail_CJ_Toolbar_WeightTotal_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'goodsWeightTotal_toolbar'
		   },{
			   //fieldLabel:'总体积(方)',
			   fieldLabel: '总体积(方)',
			   id:'Foss.platform.noUnloadingProgressDetail_CJ_Toolbar_VolumeTotal_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'goodsVolumeTotal_toolbar'
		   }]
		}]
});


//主页面
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout=300000;
	//卡货
	var resultKHGrid = Ext.create('Foss.platform.noUnloadingProgressDetail.noUnloadingDetailKHGrid');
	platform.noUnloadingProgressDetail.resultKHGrid = resultKHGrid;

	//城际
	var resultCJGrid = Ext.create('Foss.platform.noUnloadingProgressDetail.noUnloadingDetailCJGrid');
	platform.noUnloadingProgressDetail.resultCJGrid = resultCJGrid;
		
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-noUnloadingProgressDetail_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '卡货',
				itemId: 'KH',
				tabConfig : {
					width : 120
				},
				items : [resultKHGrid]
			}
			,{
				title : '城际',
				itemId: 'CJ',
				tabConfig : {
					width : 120
				},
				items : [resultCJGrid]
			}
			],
			listeners: {
				tabchange: function( tabPanel, newCard, oldCard, eOpts ){
					if(newCard.itemId==='KH'){
						resultKHGrid.getStore().load();
					}
					if(newCard.itemId==='CJ'){
						resultCJGrid.getStore().load();
					}
				}
			}
		}],
		renderTo: 'T_platform-noUnloadingProgressDetail-body'
	});
});