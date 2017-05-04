//定义方法，生成查询条件中“制单时间”的起始和结束时间
unload.unloaddiffenencesreportquery.getCreateTime4QueryForm = function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	}else{
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
}

//查询条件form
Ext.define('Foss.unload.unloaddiffenencesreportquery.QueryForm', {
	extend : 'Ext.form.Panel',
	title : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.title'),
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.diffReportNoLabel'),
		name : 'diffReportNo'
	}, {
		fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.vehicleNoLabel'),
		name : 'vehicleNo',
		xtype : 'commontruckselector'
	},{
		fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.unloadTaskNoLabel'),
		name : 'unloadTaskNo'
	},FossDataDictionary.getDataDictionaryCombo('UNLOAD_DIFF_RESOLVE_STATE',{
			fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.handleStateLabel'),
			labelWidth : 85,
			allowBlank : false,
			name : 'handleStatus',
			value : 'ALL',
			editable : false
	}),{
		xtype : 'rangeDateField',
		fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.reportCreateTimeLabel'),
		columnWidth : 1/2,
		editable : false,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_unload_unloaddifferencesreport_QueryForm_CreateTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 3,
		fromName : 'beginCreateTime',
		fromValue : Ext.Date.format(unload.unloaddiffenencesreportquery.getCreateTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(unload.unloaddiffenencesreportquery.getCreateTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endCreateTime',
		allowBlank : false,
		disallowBlank : true
	}, {
		fieldLabel : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.loadManLabel'),
		name : 'loadManCode',
		xtype : 'commonemployeeselector',
		parentOrgCode : unload.unloaddiffenencesreportquery.superOrgCode
	}, {
		fieldLabel: unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportshow.detailGrid.waybillNoColumn'), 
		name: 'waybillNo',
		vtype: 'waybill'
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.resetButtonText'),
			handler : function() {
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginCreateTime').setValue(Ext.Date.format(unload.unloaddiffenencesreportquery.getCreateTime4QueryForm(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endCreateTime').setValue(Ext.Date.format(unload.unloaddiffenencesreportquery.getCreateTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .525,
			html : '&nbsp;'
		}, {
		    xtype: 'checkbox', 
		    columnWidth : .315,
		    name: 'departStatus', 
		    boxLabel: unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.checkBoxText'),
		    inputValue: 'true',
		    uncheckedValue: 'false'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !unload.unloaddiffenencesreportquery.isPermission('unload/queryUnloadDiffReportButton'),
			hidden : !unload.unloaddiffenencesreportquery.isPermission('unload/queryUnloadDiffReportButton'),
			text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.queryForm.queryButtonText'),
			handler : function(){
				if(this.up('form').getForm().isValid()){
					unload.unloaddiffenencesreportquery.queryGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//查询结果Model
Ext.define('Foss.unload.unloaddiffenencesreportquery.QueryResultModel',{
	extend : 'Ext.data.Model',
	fields : [{
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
		name : 'unloadType'
	}, {
		type : 'number',
		name : 'moreGoodsPieces'
	}, {
		type : 'number',
		name : 'lackGoodsPieces'
	}, {//手输件数
		type : 'number',
		name : 'byhandGoodsPieces'
	}, {
		type : 'string',
		name : 'handleStatus'
	}, {
		type : 'string',
		name : 'loadManName'
	}, {
		type : 'string',
		name : 'loadManCode'
	}, {
		type : 'date',
		name : 'reportCreateTime',
		convert : dateConvert
	},{
		type : 'string',
		name : 'loadManDeptName'
	}]
});

//查询结果store
Ext.define('Foss.unload.unloaddiffenencesreportquery.queryResultStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloaddiffenencesreportquery.QueryResultModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : unload.realPath('queryUnloadDiffReport.action'),
		timeout : 200000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadDiffReportVo.unloadDiffReportEntityList',
			//totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store,operation,eOpts){
			var queryParams = unload.unloaddiffenencesreportquery.queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.diffReportNo' : queryParams.diffReportNo,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.vehicleNo' :queryParams.vehicleNo,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.unloadTaskNo' : queryParams.unloadTaskNo,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.beginCreateTime' : queryParams.beginCreateTime,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.endCreateTime' : queryParams.endCreateTime,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.loadManCode' : queryParams.loadManCode,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.handleStatus' : queryParams.handleStatus,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.waybillNo' : queryParams.waybillNo,
					'unloadDiffReportVo.queryUnloadDiffReportConditionDto.departStatus' : queryParams.departStatus

				}
			});	
		}
	}
});

//定义查询结果列表
Ext.define('Foss.unload.unloaddiffenencesreportquery.queryResultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.title'),
//	bodyCls : 'autoHeight',
	height : 550,
	cls : 'autoHeight',
	emptyText : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.noResultAlertInfo'),
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloaddiffenencesreportquery.queryResultStore');
		//unload.unloaddiffenencesreportquery.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'diffReportNo',
		align : 'center',
		width : 160,
		xtype : 'ellipsiscolumn',
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.diffReportNoColumn'),
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:unload.unloaddiffenencesreportquery.showDiffReportDetail('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	}, {
		dataIndex : 'unloadTaskNo',
		align : 'center',
		width : 160,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.unloadTaskNoColumn'),
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:unload.unloaddiffenencesreportquery.showUnloadTaskDetail('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 120,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.vehicleNoColumn')
	}, {
		dataIndex : 'unloadType',
		align : 'center',
		width : 120,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.unloadTypeColumn'),
		renderer : function(value){
			if(value=='PACKAGE_AIR'){  // 特准快件卸车
				return unload.unloaddiffenencesreportquery.i18n("foss.unload.unloaddiffenencesreportquery.resultGrid.packageAir");
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,'UNLOAD_TYPE');
			}
		}
	}, {
		dataIndex : 'moreGoodsPieces',
		align : 'center',
		width : 100,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.moreGoodsPiecesColumn')
	}, {
		dataIndex : 'lackGoodsPieces',
		align : 'center',
		width : 100,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.lessGoodsPiecesColumn')
	}, {
		dataIndex : 'byhandGoodsPieces',
		align : 'center',
		width : 100,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.byhandGoodsPiecesColumn')
	},{
		dataIndex : 'handleStatus',
		align : 'center',
		width : 120,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.handleStateColumn'),
		// 313352 gouyangyang bengin  2016-06-21
		renderer : function(value){
			//return FossDataDictionary.rendererSubmitToDisplay(value,'UNLOAD_DIFF_RESOLVE_STATE');  // 处理状态
			if(value=='RESOLVING'){       //处理中
				return unload.unloaddiffenencesreportquery.i18n("Foss.unload.orderDifferReport.status.ING");
			}else if(value=='RESOLVED'){  //已处理
				return unload.unloaddiffenencesreportquery.i18n("foss.unload.changelabelgoods.PROCESSED");
			}else{
				return value;
			}
		}
	    // 313352 gouyangyang end
	},{
		dataIndex : 'loadManName',
		align : 'center',
		width : 120,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.loadManColumn')
	}, {
		dataIndex : 'loadManDeptName',
		align : 'center',
		width : 180,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.loadManDeptColumn')
	},{
		dataIndex : 'reportCreateTime',
		align : 'center',
		width : 180,
		text : unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.reportCreateTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}]
});
//点击列表中“差异报告编号”超链接方法
unload.unloaddiffenencesreportquery.showDiffReportDetail = function(value){
	unload.addTab('T_unload-unloaddiffenencesreportshowindex',
			unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.diffReportDetailTabTitle'),
			unload.realPath('unloaddiffenencesreportshowindex.action') + '?diffReportNo="' + value + '"');
}

//点击列表中“卸车任务编号”超链接方法
unload.unloaddiffenencesreportquery.showUnloadTaskDetail = function(value){
	unload.addTab('T_unload-unloadtaskdetailqueryIndex',
			unload.unloaddiffenencesreportquery.i18n('foss.unload.unloaddiffenencesreportquery.resultGrid.unloadTaskDetailTabTitle'),
			unload.realPath('unloadtaskdetailqueryindex.action?unloadTaskNo='+ value+'&state='+'FINISHED'));
}
//定义查询表单
unload.unloaddiffenencesreportquery.queryForm = Ext.create('Foss.unload.unloaddiffenencesreportquery.QueryForm');
//定义查询结果列表
unload.unloaddiffenencesreportquery.queryGrid = Ext.create('Foss.unload.unloaddiffenencesreportquery.queryResultGrid');

Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-unloaddiffenencesreportqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [unload.unloaddiffenencesreportquery.queryForm,unload.unloaddiffenencesreportquery.queryGrid],
		renderTo : 'T_unload-unloaddiffenencesreportqueryindex-body'
	});
});