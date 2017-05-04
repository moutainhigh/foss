
//运输性质
Ext.define('Foss.stock.prioritygoods.ProductStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: backupquery.realPath('queryProductList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.productList'
		}
    },
	data:[
		    {"valueName": "全部", "valueCode": "ALL"},
		    {"valueName": "标准快递", "valueCode": "PACKAGE"},
		    {"valueName": "3.60特惠件", "valueCode": "RCP"},
		    {"valueName": "电商尊享", "valueCode": "EPEP"},
		    {"valueName": "精准卡航", "valueCode": "FLF"},
		    {"valueName": "精准城运", "valueCode": "FSF"},
		    {"valueName": "精准汽运(长途)", "valueCode": "LRF"},
		    {"valueName": "精准汽运(短途)", "valueCode": "SRF"},
		    {"valueName": "精准大票卡航", "valueCode": "BGFLF"},
		    {"valueName": "精准大票汽运(长)", "valueCode": "BGLRF"},
		    {"valueName": "精准大票城运", "valueCode": "BGFSF"},
		    {"valueName": "精准大票汽运(短)", "valueCode": "BGSRF"},
		    {"valueName": "汽运偏线", "valueCode": "PLF"},
		    {"valueName": "商务专递", "valueCode": "DEAP"},
		    {"valueName": "精准包裹", "valueCode": "PCP"}
		]
});
//库存查询条件表单
Ext.define('Foss.stock.prioritygoods.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_backupquery_prioritygoods_QueryForm_ID',
	layout: 'column',
	frame: true,
	border: false,
	//title : '查询必走货',
	title : backupquery.prioritygoods.i18n('foss.stock.query.prioritygoods'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		//fieldLabel: '部门',
		fieldLabel: backupquery.prioritygoods.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'orgCode',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commongoodsareaselector',
		//fieldLabel: '货区',
		fieldLabel: backupquery.prioritygoods.i18n('foss.stock.goodsarea'),
		name: 'goodsAreaCode',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		//fieldLabel: '运输性质',
		fieldLabel: backupquery.prioritygoods.i18n('foss.stock.product'),
		name: 'productCode',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: 'ALL',
		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.prioritygoods.ProductStore')
	},{
		xtype: 'rangeDateField',
		//fieldLabel: '入库时间',
		fieldLabel: backupquery.prioritygoods.i18n('foss.stock.instocktime'),
		fieldId: 'Foss_backupquery_prioritygoods_QueryForm_InstockTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'beginInStockTime',
				
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-5,
										'00', '00'), 'Y-m-d H:i:s'),		
				
		toName: 'endInStockTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		//blankText:'字段不能为空',
		blankText: backupquery.prioritygoods.i18n('foss.stock.notnull'),
		columnWidth: .5
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			//text: '重置',
			text: backupquery.prioritygoods.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){
				backupquery.prioritygoods.queryform.getForm().reset();
				backupquery.prioritygoods.queryform.getForm().findField('beginInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-5,
										'00', '00'), 'Y-m-d H:i:s'));
				backupquery.prioritygoods.queryform.getForm().findField('endInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
				backupquery.prioritygoods.queryform.getForm().findField('orgCode').setValue(backupquery.prioritygoods.orgCode);
				backupquery.prioritygoods.queryform.getForm().findField('orgCodeName').setValue(backupquery.prioritygoods.orgName);
				
			}
		},{
			xtype: 'container',
			columnWidth:.65,
			html: '&nbsp;'
		},{
			text: '导出',
			columnWidth:.08,
			disabled: !backupquery.prioritygoods.isPermission('stock/exportPriorityGoodsButton'),
			hidden: !backupquery.prioritygoods.isPermission('stock/exportPriorityGoodsButton'),
			handler: function(){
				var queryParams = backupquery.prioritygoods.queryform.getValues();
				
				if(queryParams.productCode == 'ALL'){
					queryParams.productCode = '';
				}
				
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
            	}
            	
            	Ext.Ajax.request({
            		url: backupquery.realPath('exportPriorityGoods.action'),
	    			form: Ext.fly('downloadAttachFileForm'),
	    			method : 'POST',
	    			params: {
	    				'stockVO.waybillStockQueryDto.orgCode' : queryParams.orgCode,
						'stockVO.waybillStockQueryDto.waybillNO' :queryParams.waybillNO,
						'stockVO.waybillStockQueryDto.goodsAreaCode' : queryParams.goodsAreaCode,
						'stockVO.waybillStockQueryDto.productCode' : queryParams.productCode,
						'stockVO.waybillStockQueryDto.beginInStockTime' : queryParams.beginInStockTime,
						'stockVO.waybillStockQueryDto.endInStockTime' : queryParams.endInStockTime
					},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert("导出失败", result.message);
	    			}
	    		});
			}
		},{
			//text: '查询'
			text: backupquery.prioritygoods.i18n('foss.stock.search'),
			disabled: !backupquery.prioritygoods.isPermission('stock/queryPriorityGoodsButton'),
			hidden: !backupquery.prioritygoods.isPermission('stock/queryPriorityGoodsButton'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				if(backupquery.prioritygoods.queryform.getForm().isValid()){
					backupquery.prioritygoods.pagingBar.moveFirst();
				}
				
			}
		}]
	}
	],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});




//运单库存 model
Ext.define('Foss.stock.prioritygoods.WaybillModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNO' , type: 'string'},
		{name: 'goodsAreaCode' , type: 'string'},
		{name: 'goodsAreaName' , type: 'string'},
		{name: 'waybillGoodsCount' , type: 'string'},
		{name: 'stockGoodsCount', type: 'int'},
		{name: 'goodsName', type: 'string'},  
		{name: 'packageType', type: 'string'},
		{name: 'productCode', type: 'string'},
		{name: 'weightTotal', type: 'string'},
		{name: 'volumeTotal', type: 'string'},
		{name: 'departureCode', type: 'string'},
		// 下一部门
		{name: 'nextOrgCode', type: 'string'},
		
		{name: 'receiveOrgCode', type: 'string'},
		{name: 'inStockTime',type:'date', convert: dateConvert},
		{name: 'inStockDuration', type: 'string'},
		{name: 'orgCode', type: 'string'}
	]
});
//货件库存 model
Ext.define('Foss.stock.prioritygoods.GoodsModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'waybillNO', type: 'string'},
		{name: 'serialNO' , type: 'string'},
		{name: 'inStockTime',type:'date', convert: dateConvert},
		{name: 'operatorName', type: 'string'},
		{name: 'orgCode', type: 'string'},
		{name: 'goodsAreaCode', type: 'string'}
	]
});

//货件库存 Store
Ext.define('Foss.stock.prioritygoods.GoodsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.prioritygoods.GoodsModel',
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: backupquery.realPath('queryStock.action'),
		reader : {
			type : 'json',
			root : 'stockVO.stockList'
		}
    }
});

//运单库存 Store
Ext.define('Foss.stock.prioritygoods.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.prioritygoods.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: backupquery.realPath('queryPriorityGoods.action'),
		reader : {
			type : 'json',
			root : 'stockVO.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = backupquery.prioritygoods.queryform.getValues();
				
				if(queryParams.productCode == 'ALL'){
					queryParams.productCode = '';
				}
				
				Ext.apply(operation, {
					params : {
						'stockVO.waybillStockQueryDto.orgCode' : queryParams.orgCode,
						'stockVO.waybillStockQueryDto.waybillNO' :queryParams.waybillNO ,
						'stockVO.waybillStockQueryDto.goodsAreaCode' : queryParams.goodsAreaCode,
						'stockVO.waybillStockQueryDto.productCode' : queryParams.productCode,
						'stockVO.waybillStockQueryDto.beginInStockTime' : queryParams.beginInStockTime,
						'stockVO.waybillStockQueryDto.endInStockTime' : queryParams.endInStockTime
					}
				});	
		},
		load : function( store, records, successful, eOpts){
			var waybillStockStatisticsDto = store.proxy.reader.rawData.stockVO.waybillStockStatisticsDto;
			
			Ext.getCmp('Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_waybillTotal_ID').setValue(waybillStockStatisticsDto.waybillQty);
			Ext.getCmp('Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_goodsTotal_ID').setValue(waybillStockStatisticsDto.stockGoodsQty);
			Ext.getCmp('Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_weightTotal_ID').setValue(waybillStockStatisticsDto.weightTotal + ' 公斤');
			Ext.getCmp('Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_volumeTotal_ID').setValue(waybillStockStatisticsDto.volumeTotal + ' 方');
			Ext.getCmp('Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_srcVolumeTotal_ID').setValue(waybillStockStatisticsDto.srcVolumeTotal + ' 方');
		}
	}
	
});



// 货件库存 Grid （第二层表格）
Ext.define('Foss.stock.prioritygoods.GoodsGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_backupquery_prioritygoods_GoodsGrid_ID',
	columnLines: true,
    frame: true,
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var waybillNO = record.get('waybillNO');
		var goodsAreaCode = record.get('goodsAreaCode');
		var orgCode = record.get('orgCode');
		var grid = this;
		Ext.Ajax.request({
			url: backupquery.realPath('queryStock.action'),
			params:{'stockVO.waybillStockEntity.waybillNO': waybillNO,
					'stockVO.waybillStockEntity.orgCode': orgCode,
					'stockVO.waybillStockEntity.goodsAreaCode': goodsAreaCode
					},
			success:function(response){
				var result = Ext.decode(response.responseText);
				/*if(!result.stockVO.stockList || result.stockVO.stockList.length == 0) {
					Ext.MessageBox.alert("提示", "没有数据行!");
					return;
				}*/
				grid.store.loadData(result.stockVO.stockList);
				var serialNO;
				var goodsMap = backupquery.prioritygoods.waybillGoodsMap.get(waybillNO);
				if(goodsMap != null){
		
					for(var i=0;i<grid.store.getCount();i++){
						serialNO = grid.store.getAt(i).get('serialNO');
						if(goodsMap.get(serialNO) != null){
							grid.getSelectionModel().select(grid.store.getAt(i),true,true);
						}
					}
				}
			}
		})
		
	},
	columns: [{
			//header: '流水号', 
			header: backupquery.prioritygoods.i18n('foss.stock.serialno'), 
			dataIndex: 'serialNO' 
		},{ 
			//header: '入库时间', 
			header: backupquery.prioritygoods.i18n('foss.stock.instocktime'), 
			dataIndex: 'inStockTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s'
		},{
			//header: '操作人', 
			header: backupquery.prioritygoods.i18n('foss.stock.operator'), 
			dataIndex: 'operatorName' 
		}],    
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.prioritygoods.GoodsStore');
		me.callParent([cfg]);
	}
});

//运单库存表格
Ext.define('Foss.stock.prioritygoods.WaybillGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_backupquery_prioritygoods_WaybillGrid_ID',
	height: 450,
	autoScroll:true,
	columnLines: true,
    frame: true,
    plugins: [{
        ptype: 'rowexpander',
        pluginId: 'Foss.stock.prioritygoods.WaybillGrid_Plugin_ID',
		rowsExpander: false,
		rowBodyElement : 'Foss.stock.prioritygoods.GoodsGrid'
	}],
	columns: [{
			//header: '运单号', 
			header: backupquery.prioritygoods.i18n('foss.stock.waybill'), 
			dataIndex: 'waybillNO',
			width : 100,
			align: 'center'
		},{ 
			//header: '货区', 
			header: backupquery.prioritygoods.i18n('foss.stock.goodsarea'), 
			dataIndex: 'goodsAreaName',
			width : 100,
			align: 'center',
			renderer : function(value,metaData,record){
				var goodsAreaCode = record.get('goodsAreaCode');
				if(!Ext.isEmpty(goodsAreaCode)){
					if(goodsAreaCode == 'BULK_GOODS_AREA'){
						value = '散货';
					}
					if(goodsAreaCode == 'WHOLE_GOODS_AREA'){
						value = '整车';
					}
				}
				return value;
			}
		},{ 
			//header: '开单件数', 
			header: backupquery.prioritygoods.i18n('foss.stock.waybillgoodsqty'),
			dataIndex: 'waybillGoodsCount',
			width : 100,
			align: 'center'
		},{ 
			//header: '库存件数', 
			header: backupquery.prioritygoods.i18n('foss.stock.goodsqty'),
			dataIndex: 'stockGoodsCount',
			width : 100,
			align: 'center'
		},{ 
			//header: '货物名称', 
			header: backupquery.prioritygoods.i18n('foss.stock.goodsname'),
			dataIndex: 'goodsName',
			width : 100,
			align: 'center'
		},{ 
			//header: '包装', 
			header: backupquery.prioritygoods.i18n('foss.stock.package'),
			dataIndex: 'packageType',
			width : 100,
			align: 'center'
		},{ 
			//header: '运输性质',
			header: backupquery.prioritygoods.i18n('foss.stock.product'),
			dataIndex: 'productCode',
			width : 100,
			align: 'center'
		},{ 
			//header: '重量（公斤）', 
			header: backupquery.prioritygoods.i18n('foss.stock.weight'),
			dataIndex: 'weightTotal',
			width : 100,
			align: 'center'
		},{ 
			//header: '体积（方）', 
			header: backupquery.prioritygoods.i18n('foss.stock.volume'),
			dataIndex: 'volumeTotal',
			width : 100,
			align: 'center'
		},{ 
			//header: '出发部门', 
			header: backupquery.prioritygoods.i18n('foss.stock.departureorg'),
			dataIndex: 'departureCode',
			width : 180,
			align: 'center'
		},{ 
			//下一部门
			//header: '下一部门', 
			header: backupquery.prioritygoods.i18n('foss.stock.nextorg'),
			dataIndex: 'nextOrgCode',
			width : 180,
			align: 'center'
		},{ 
			//header: '到达部门', 
			header: backupquery.prioritygoods.i18n('foss.stock.receiveorg'),
			dataIndex: 'receiveOrgCode',
			width : 180,
			align: 'center'
		},{ 
			//header: '入库时间', 
			header: backupquery.prioritygoods.i18n('foss.stock.instocktime'),
			dataIndex: 'inStockTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			//header: '在库时长(小时)', 
			header: backupquery.prioritygoods.i18n('foss.stock.instock.duration'),
			dataIndex: 'inStockDuration',
			width : 110,
			align: 'center'
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
			   //fieldLabel:'总票数',
			   fieldLabel: backupquery.prioritygoods.i18n('foss.stock.waybilltotal'),
			   id:'Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_waybillTotal_ID',
			   columnWidth:.12,
			   labelWidth:60,
			   dataIndex: 'waybillTotal'
		   },{
			   //fieldLabel:'总件数',
			   fieldLabel: backupquery.prioritygoods.i18n('foss.stock.goodstotal'),
			   id:'Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_goodsTotal_ID',
			   columnWidth:.13,
			   labelWidth:60,
			   dataIndex: 'goodsTotal'
		   },{
			   //fieldLabel:'总重量',
			   fieldLabel: backupquery.prioritygoods.i18n('foss.stock.weighttotal'),
			   id:'Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_weightTotal_ID',
			   columnWidth:.25,
			   labelWidth:60,
			   dataIndex: 'weightTotal'
		   },{
			   //fieldLabel:'总体积',
			   fieldLabel: backupquery.prioritygoods.i18n('foss.stock.volumetotal'),
			   id:'Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_volumeTotal_ID',
			   columnWidth:.25,
			   labelWidth:60,
			   dataIndex: 'volumeTotal'
		   },{
			   //fieldLabel:'为转换总体积',
			   fieldLabel: backupquery.prioritygoods.i18n('foss.stock.srcvolumetotal'),
			   id:'Foss_backupquery_prioritygoods_WaybillGrid_Toolbar_srcVolumeTotal_ID',
			   columnWidth:.25,
			   labelWidth:90,
			   dataIndex: 'srcVolumeTotal'
		   }]
		}],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.prioritygoods.WaybillStore');
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			backupquery.prioritygoods.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});
Ext.onReady(function(){
	Ext.Ajax.request({
		url: backupquery.realPath('queryStockOrgCode.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			backupquery.prioritygoods.orgCode = result.stockVO.stockOrgCode;
			backupquery.prioritygoods.orgName = result.stockVO.stockOrgName;
			
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.stock.prioritygoods.QueryForm');
			backupquery.prioritygoods.queryform = queryform;
			var waybillGrid = Ext.create('Foss.stock.prioritygoods.WaybillGrid');
			backupquery.prioritygoods.waybillGrid = waybillGrid;
			Ext.create('Ext.panel.Panel',{
				id:'T_backupquery-prioritygoodsindex_content',
				cls:"panelContentNToolbar",
				bodyCls:'panelContent-body',
				items : [queryform,waybillGrid],
				renderTo: 'T_backupquery-prioritygoodsindex-body'
			});
			
			backupquery.prioritygoods.queryform.getForm().findField('goodsAreaCode').deptCode = backupquery.prioritygoods.orgCode;
			backupquery.prioritygoods.queryform.getForm().findField('orgCode').setValue(backupquery.prioritygoods.orgCode);
			backupquery.prioritygoods.queryform.getForm().findField('orgCodeName').setValue(backupquery.prioritygoods.orgName);
		},
		exception : function(response) {
				var result = Ext.decode(response.responseText);
				//Ext.ux.Toast.msg('获取库存部门失败', result.message, 'error', 3000);
				Ext.ux.Toast.msg(backupquery.prioritygoods.i18n('foss.stock.get.stockorg.failure'), result.message, 'error', 3000);
			}
	});
});
