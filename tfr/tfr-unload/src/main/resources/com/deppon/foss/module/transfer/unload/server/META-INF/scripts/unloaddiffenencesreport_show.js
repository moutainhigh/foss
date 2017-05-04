count=0;
//定义卸车差异报告基本信息Model
Ext.define('Foss.unload.unloaddiffenencesreportshow.basicInfoModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'diffReportNo',
		type : 'string'
	},{
		type : 'string',
		name : 'unloadTaskNo'
	}, {
		type : 'string',
		name : 'vehicleNo'
	},  {
		type : 'string',
		name : 'unloadType',
		convert : function(value){
			if(value=='PACKAGE_AIR'){  // 特准快件卸车
				return unload.unloaddiffenencesreportquery.i18n("foss.unload.unloaddiffenencesreportquery.resultGrid.packageAir");
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,'UNLOAD_TYPE');
			}
		}
	},  {
		type : 'string',
		name : 'loadManName'
	}, {
		type : 'string',
		name : 'loadManCode'
	}, {
		type : 'date',
		name : 'reportCreateTime',
		convert : function(value) {
			if(!value) return '';
			var date = new Date(value);
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
			}
	}]
});

//定义上方基本信息之form
Ext.define('Foss.unload.unloaddiffenencesreportshow.basicInfoForm', {
	extend : 'Ext.form.Panel',
//	title : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.title'),
	height : 130,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 95,
		columnWidth : 1 / 3,
		xtype : 'textfield',
		readOnly : true
	},
	layout : 'column',
	items : [{
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.diffReportNoLabel'),
		name : 'diffReportNo'
	}, {
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.vehicleNoLabel'),
		name : 'vehicleNo'
	}, {
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.loadManLabel'),
		name : 'loadManName'
	}, {
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.unloadTaskNoLabel'),
		name : 'unloadTaskNo'
	},{
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.diffReportCreateTimeLabel'),
		name : 'reportCreateTime'
	}, {
		fieldLabel : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.basicInfoForm.unloadTypeLabel'),
		name : 'unloadType'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义差异报告详情Model
Ext.define('Foss.unload.unloaddiffenencesreportshow.diffReportDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'packageNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'oaMistakeBillNo',
		type : 'string'
	}, {
		name : 'diffType',
		type : 'string'
	}, {
		name : 'transProperty',
		type : 'string'
	}, {
		name : 'isScanedLastTime',
		type : 'string'
	}, {
		name : 'unloadTime',
		type : 'string',
		convert : dateConvert
	},{
		name : 'exceptionHandleTime',
		type : 'string',
		convert : dateConvert
	},{
		name : 'targetOrg',
		type : 'string'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'stockState',
		type : 'string'
	}]
});

//定义差异报告详情Model(快递)
Ext.define('Foss.unload.unloaddiffenencesreportshow.expressDiffReportDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'cageNo',
		type : 'string'
	},{
		name : 'packageNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'oaMistakeBillNo',
		type : 'string'
	}, {
		name : 'diffType',
		type : 'string'
	}, {
		name : 'productType',
		type : 'string'
	}, {
		name : 'isScanedLastTime',
		type : 'string'
	}, {
		name : 'unloadTime',
		type : 'string'
	},{
		name : 'exceptionHandleTime',
		type : 'string'
	},{
		name : 'targetOrg',
		type : 'string'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'stockState',
		type : 'string'
	}]
});

//查询结果store(零担)
Ext.define('Foss.unload.unloaddiffenencesreportshow.diffReportDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloaddiffenencesreportshow.diffReportDetailModel',
	autoLoad : true,
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		url : unload.realPath('showUnloadDiffReportAndDetail.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadDiffReportVo.unloadDiffReportDetailEntityList'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var diffReportNo = unload.unloaddiffenencesreportshow.diffReportNo;
			Ext.apply(operation, {
				params : {
					'unloadDiffReportVo.diffReportNo': diffReportNo
				}
			});
		},
		load : function(store, records, successful, oOpts) {
				var data = store.getProxy().getReader().rawData;
				if(!data.success &&(!data.isException)){
					Ext.ux.Toast.msg("提示", data.message, 'error', 2000);	//提示			
					return;
				}
				//获取基本信息实体
				var basicInfo = data.unloadDiffReportVo.baseEntity;
				//绑定model
				var basicInfoRecord = unload.unloaddiffenencesreportshow.basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.unload.unloaddiffenencesreportshow.basicInfoModel');
				unload.unloaddiffenencesreportshow.basicInfoForm.loadRecord(basicInfoRecord);
		}
	}
	/*,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}*/
});

//查询结果store(快递)
Ext.define('Foss.unload.unloaddiffenencesreportshow.expressDiffReportDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloaddiffenencesreportshow.expressDiffReportDetailModel',
	pageSize:25,
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		url : unload.realPath('showUnloadDiffReportMarkDetail.action'),
		// 定义一个读取器
		reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'unloadDiffReportVo.expressEntityList',
				totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var diffReportNo = unload.unloaddiffenencesreportshow.diffReportNo;
			Ext.apply(operation, {
				params : {
					'unloadDiffReportVo.diffReportNo': diffReportNo
				}
			});
		}
	}/*,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}*/
});

//差异报告详情列表   零担
Ext.define('Foss.unload.unloaddiffenencesreportshow.diffReportDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.title'),
	height : 600,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloaddiffenencesreportshow.diffReportDetailStore'),
		//TODO:
//		me.tbar = [{
////			xtype : 'button',
////			text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloadtaskquery.export'),
////			name : 'export',
//			handler : function(){
//				if(!Ext.fly('downloadAttachFileForm')){
//					    var frm = document.createElement('form');
//					    frm.id = 'downloadAttachFileForm';
//					    frm.style.display = 'none';
//					    document.body.appendChild(frm);
//				}		
//				Ext.Ajax.request({
//					url:unload.realPath('exportUnloadDifferencesReportDetail.action'),
//					form: Ext.fly('downloadAttachFileForm'),
//					method : 'POST',
//					params : {'unloadDiffReportVo.diffReportNo': unload.unloaddiffenencesreportshow.diffReportNo},
//	    			isUpload: true,
//	    			exception : function(response) {
//	    				var result = Ext.decode(response.responseText);
//	    				top.Ext.MessageBox.alert(unload.unloaddiffenencesreportshow.i18n('foss.unload.unloadtaskquery.exportFail'),result.message);
//	    			}
//				});
//			}
//		}];
		me.callParent([cfg]);
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.actionColumn'),
		align : 'center',
		items : [ {
			tooltip : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.handleButtonToolTip'),
			iconCls : 'foss_icons_tfr_markHandeledAfter',
			handler : function(grid, rowIndex, colIndex, item, e) {
				var x = e.getX();
				var y = e.getY();
				var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
				var serialNo = grid.store.getAt(rowIndex).get('serialNo');
				var window = unload.unloaddiffenencesreportshow.handleExceptionWindow;
				if(window != null){
					window.showAt(x + 10,y + 10);
				}else{
					window = Ext.create('Foss.unload.unloaddiffenencesreportshow.handleExceptionWindow');
					window.showAt(x + 10,y + 10);
				}
				//给窗口中的运单号、流水号赋值
				window.items.items[0].setValue(waybillNo);
				window.items.items[1].setValue(serialNo);
			}
		}],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			//如果异常类型为多货，或者异常已处理，则隐藏处理按钮
			if(record.get('diffType') == 'MOREGOODS'){
				this.items[0].iconCls = null;
			}else if(record.get('exceptionHandleTime') != null){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '标准快递'){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '3.60特惠件'){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '电商尊享'){
				this.items[0].iconCls = null;
			}/*else if(record.get('stockState') == 'IN_STOCK'){
				this.items[0].iconCls = null;
			}*/else{
				this.items[0].iconCls = 'foss_icons_tfr_markHandeledAfter';
			}
		}
	}, {
		dataIndex : 'packageNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.packageNo')
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.waybillNoColumn')
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 80,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.serialNoColumn')
	}, {
		dataIndex : 'oaMistakeBillNo',
		align : 'center',
		width : 110,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.oaErrorNoColumn')
	}, {
		dataIndex : 'diffType',
		align : 'center',
		width : 60,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.diffTypeColumn'),
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'UNLOAD_EXCEPTION_TYPE');
//			var store = FossDataDictionary. getDataDictionaryStore('UNLOAD_EXCEPTION_TYPE');
//			var record = store.findRecord('valueCode',value,0,false,true,true);
//			if(record == null){
//				return value;
//			}else{
//				return record.get('valueName');
//			}
		}
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 80,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.transPropertyColumn')
	}, {
		dataIndex : 'isScanedLastTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.isLastScanedColumn'),
		
	},{
		dataIndex : 'unloadTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.unloadTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'exceptionHandleTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.exceptionHandleTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'targetOrg',
		align : 'center',
		flex : 1,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.targetOrg')
	},{
		dataIndex : 'note',
		align : 'center',
		flex : 1,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.noteColumn')
	}]
});


//差异报告详情列表(快递)
Ext.define('Foss.unload.unloaddiffenencesreportshow.diffReportDetailMarkGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.title'),
	height : 600,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloaddiffenencesreportshow.expressDiffReportDetailStore');
		me.bbar = me.getPagingToolbar();
		unload.unloaddiffenencesreportshow.expressPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.actionColumn'),
		align : 'center',
		items : [ {
			//tooltip : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.handleButtonToolTip'),
			iconCls : 'foss_icons_tfr_markHandeledAfter',
			handler : function(grid, rowIndex, colIndex, item, e) {
				var x = e.getX();
				var y = e.getY();
				var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
				var serialNo = grid.store.getAt(rowIndex).get('serialNo');
				var window = unload.unloaddiffenencesreportshow.handleExceptionWindow;
				if(window != null){
					window.showAt(x + 10,y + 10);
				}else{
					//window = Ext.create('Foss.unload.unloaddiffenencesreportshow.handleExceptionWindow');
					window.showAt(x + 10,y + 10);
				}
				//给窗口中的运单号、流水号赋值
				window.items.items[0].setValue(waybillNo);
				window.items.items[1].setValue(serialNo);
			}
		}],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			//如果异常类型为多货，或者异常已处理，则隐藏处理按钮
			if(record.get('diffType') == 'MOREGOODS'){
				this.items[0].iconCls = null;
			}else if(record.get('exceptionHandleTime') != null){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '标准快递'){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '3.60特惠件'){
				this.items[0].iconCls = null;
			}else if(record.get('transProperty') == '电商尊享'){
				this.items[0].iconCls = null;
			}/*else if(record.get('stockState') == 'IN_STOCK'){
				this.items[0].iconCls = null;
			}*/else{
				this.items[0].iconCls = 'foss_icons_tfr_markHandeledAfter';
			}
		}
	}, {
		dataIndex : 'cageNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '笼号'
	},{
		dataIndex : 'packageNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.packageNo')
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.waybillNoColumn')
	}, {
		dataIndex : 'oaMistakeBillNo',
		align : 'center',
		width : 110,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.oaErrorNoColumn')
	}, {
		dataIndex : 'diffType',
		align : 'center',
		width : 60,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.diffTypeColumn'),
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'UNLOAD_EXCEPTION_TYPE');
		}
	}, {
		dataIndex : 'productType',
		align : 'center',
		width : 80,
		text : '产品类型'
	}, {
		dataIndex : 'isScanedLastTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.isLastScanedColumn'),
	},{
		dataIndex : 'unloadTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.unloadTimeColumn'),
		//xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'exceptionHandleTime',
		align : 'center',
		width : 130,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.exceptionHandleTimeColumn'),
		//xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'targetOrg',
		align : 'center',
		flex : 1,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.targetOrg')
	},{
		dataIndex : 'note',
		align : 'center',
		flex : 1,
		text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.noteColumn')
	}]
});



//定义弹出输入备注的窗口
Ext.define('Foss.unload.unloaddiffenencesreportshow.handleExceptionWindow', {
	extend : 'Ext.window.Window',
    title : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.title'),
    height : 240,
    modal : 'true',
    closeAction : 'hide',
    draggable : false,//拖动
   	resizable : false,//变大小
    width : 300,
    items :  [{
    	xtype : 'textarea',
    	name : 'waybillNo',
    	hidden : true
    },{
    	xtype : 'textarea',
    	name : 'serialNo',
    	hidden : true
    },{
    	xtype : 'textarea',
    	name : 'notes',
    	allowBlank : false,
    	height : 135,
    	width : 278
    }],
    buttons : [{
    	text : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.submitButtonText'),
    	disabled : !unload.unloaddiffenencesreportshow.isPermission('unload/handleUnloadLackGoodsExceptionButton'),
    	hidden : !unload.unloaddiffenencesreportshow.isPermission('unload/handleUnloadLackGoodsExceptionButton'),
    	handler : function(){
    		//备注必填
    		var noteCmp = this.ownerCt.ownerCt.items.items[2];
    		var waybillNo = this.ownerCt.ownerCt.items.items[0].getValue();
    		var serialNo = this.ownerCt.ownerCt.items.items[1].getValue();
    		var diffReportId = unload.unloaddiffenencesreportshow.basicInfoRecord.get('id');
    		var diffReportNo = unload.unloaddiffenencesreportshow.basicInfoRecord.get('diffReportNo');
    		if(noteCmp.isValid()){
    			//构造jsonData
    			var data = {
    				'unloadDiffReportVo' : {
    					'diffReportId' : diffReportId,
    					'waybillNo' : waybillNo,
    					'serialNo' : serialNo,
    					'note' : noteCmp.getValue()
    				}
    			};
    			var myMask = new Ext.LoadMask(unload.unloaddiffenencesreportshow.diffReportDetailGrid,{
					msg : unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.dataSavingAlertInfo')
				});
				myMask.show();
    			//发出请求，处理该少货差异
    			Ext.Ajax.request({
					url : unload.realPath('handleUnloadLackGoodsException.action'),
					jsonData : data,
					success : function(response){
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.alertInfoTitle'), 
						unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.handleSuccess'), 
						'info', 2000);
						//重新加载数据
						unload.unloaddiffenencesreportshow.diffReportDetailGrid.store.load({
							params : {
								'unloadDiffReportVo.diffReportNo': diffReportNo
							}
						});
						myMask.hide();
					},
					exception : function(response){
						var result = Ext.decode(response.responseText);
				    	top.Ext.MessageBox.alert(unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.alertInfoTitle'),
				    	unload.unloaddiffenencesreportshow.i18n('foss.unload.unloaddiffenencesreportshow.handleWindow.handleFailure') + result.message);
				    	//重新加载数据
						unload.unloaddiffenencesreportshow.diffReportDetailGrid.store.load({
							params : {
								'unloadDiffReportVo.diffReportNo': diffReportNo
							}
						});
						myMask.hide();
					}
				});
    			this.ownerCt.ownerCt.close();
    		}
    	}
    }]
});

//零担
unload.unloaddiffenencesreportshow.diffReportDetailGrid = Ext.create('Foss.unload.unloaddiffenencesreportshow.diffReportDetailGrid');
//快递
unload.unloaddiffenencesreportshow.diffReportDetailGridExpress = Ext.create('Foss.unload.unloaddiffenencesreportshow.diffReportDetailMarkGrid');

//TODO
Ext.define('Foss.unload.unloaddiffenencesreportshow.TemprentalTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
				tabConfig : {
					width : 100
				},
				itemId : 'diffReportDetailGrid',
				title : '零担列表', //零担列表页签
				items : unload.unloaddiffenencesreportshow.diffReportDetailGrid
			}, {
				tabConfig : {
					width : 100
				},
				itemId : 'expressDiffReportDetailGrid',
				title : '快递列表', //快递列表页签
				items : unload.unloaddiffenencesreportshow.diffReportDetailGridExpress
			}],
			listeners : {
				'tabchange' : function(tabPanel,newCard,oldCard,eOpts){
					if(this.getActiveTab().title == '零担列表') {
						//unload.unloaddiffenencesreportshow.diffReportDetailGrid.store.load();
					} else {
						if(count==0){
							unload.unloaddiffenencesreportshow.expressPagingBar.moveFirst();
							count=count+1;
						}
					}
				}
			}
});


//定义基本信息表单
unload.unloaddiffenencesreportshow.basicInfoForm = Ext.create('Foss.unload.unloaddiffenencesreportshow.basicInfoForm');
//定义详情tab
unload.unloaddiffenencesreportshow.diffReportTabPanel = Ext.create('Foss.unload.unloaddiffenencesreportshow.TemprentalTabPanel');


Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-unloaddiffenencesreportshowindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [unload.unloaddiffenencesreportshow.basicInfoForm,unload.unloaddiffenencesreportshow.diffReportTabPanel],
		renderTo : 'T_unload-unloaddiffenencesreportshowindex-body'
	});
	//获取前一页面传入的卸车差异报告编号
	var diffReportNo = unload.unloaddiffenencesreportshow.diffReportNo;
	//请求后台，获取卸车差异报告基本信息和详情列表
	/*
	Ext.Ajax.request({
		url : unload.realPath('showUnloadDiffReportAndDetail.action'),
		params : {'unloadDiffReportVo.diffReportNo': diffReportNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//获取基本信息实体
			var basicInfo = result.unloadDiffReportVo.baseEntity;
			//绑定model
			var basicInfoRecord = unload.unloaddiffenencesreportshow.basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.unload.unloaddiffenencesreportshow.basicInfoModel');
			unload.unloaddiffenencesreportshow.basicInfoForm.loadRecord(basicInfoRecord);
			//获取详情list
			var detailList = result.unloadDiffReportVo.unloadDiffReportDetailEntityList;
			var expressList = result.unloadDiffReportVo.expressEntityList;
			var detailRecordList = new Array();
			for(var i in detailList){
				var record =  Ext.ModelManager.create(detailList[i], 'Foss.unload.unloaddiffenencesreportshow.diffReportDetailModel');
				detailRecordList.push(record);
			}
//			var expressDetailRecordList = new Array();
//			for (var express in expressList) {
//				var record =  Ext.ModelManager.create(expressList[i], 'Foss.unload.unloaddiffenencesreportshow.expressDiffReportDetailModel');
//				expressDetailRecordList.push(record);
//			}
			//列表加载数据
			//unload.unloaddiffenencesreportshow.diffReportDetailGrid.store.loadData(detailRecordList);
//			unload.unloaddiffenencesreportshow.diffReportDetailGridExpress.store.loadData(expressDetailRecordList);
			}
	});
	*/
});