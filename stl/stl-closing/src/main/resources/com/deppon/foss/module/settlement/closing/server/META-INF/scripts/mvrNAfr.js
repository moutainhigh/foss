/**
 * 声明账期model
 */
Ext.define('Foss.mvrNAfr.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNAfr.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNAfr.PeriodModel',
	proxy:{
		type:'ajax',
		url:closing.realPath('queryClosingPeriod.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'periodList'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'load': function(store, operation, eOpts){

	   			if(operation.length == 0){
	   				Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
					return false;
	   			}
	   		}
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 查询空运月报表
 */
closing.mvrNAfr.querymvrNAfr = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNAfr_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
		closing.mvrNAfr.setParamsValue(paramsV,form);
		// 设置参数
		grid.store.setSubmitParams(paramsV);
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		// 设置统计值
		grid.store.loadPage(1, {
			callback : function(records, operation, success) {
				var result = Ext.decode(operation.response.responseText);
				me.enable(true);
			}
		});
	} else {
		Ext.Msg.alert('温馨提示', '请检查输入条件是否合法');
	}
}

/**
 * 设置参数
 */
closing.mvrNAfr.setParamsValue = function(paramsV,form){
	//客户类型
	var custType = form.findField('mvrNAfrDto.customerType').getValue();
	//给客户默认值
	var customerCode = null;
	//客户类型
	if(custType=='A'){
		customerCode = form.findField('mvrNAfrDto.customerDetail').getValue();
	}else if(custType=='AA'){
		customerCode = form.findField('mvrNAfrDto.agencyDetail').getValue();
	}
	Ext.apply(paramsV,{ 
		'mvrNAfrDto.customerCode':customerCode
	});
}
/**
 * 导出空运月报表
 */
closing.mvrNAfr.exportmvrNAfr = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrNAfr_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出空运月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrNAfrForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrNAfrForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNAfrExport.action'), 
				form: Ext.fly('exportmvrNAfrForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//var json = Ext.decode(response.responseText);
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				}
		    });
			
		}
	});	
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrNAfr.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNAfr.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 空运月报表数据模型
Ext.define('Foss.closing.mvrNAfrModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'String'
	},{
		name : 'period',
		type : 'String'
	},{
		name : 'productCode',
		type : 'String'
	},{
		name : 'customerCode',
		type : 'String'
	},{
		name : 'customerName',
		type : 'String'
	},{
		name : 'origOrgCode',
		type : 'String'
	},{
		name : 'origOrgName',
		type : 'String'
	},{
		name : 'destOrgCode',
		type : 'String'
	},{
		name : 'destOrgName',
		type : 'String'
	},{
		name : 'origUnifiedCode',
		type : 'String'
	},{
		name : 'destUnifiedCode',
		type : 'String'
	},{
		name : 'voucherBeginTime',
		type:'Date',
		convert:function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'voucherEndTime',
		type : 'Date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'customerType',
		type : 'String'
	}, {
		name : 'uroaDestChNpod',
		type : 'decimal'
	}, {
		name : 'uroaDestCdPod',
		type : 'decimal'
	}, {
		name : 'urtaDestCdNpod',
		type : 'decimal'
	}, {
		name : 'airCostFeeConfirm',
		type : 'decimal'
	}, {
		name : 'airCostDestAgencyPayCfrm',
		type : 'decimal'
	}, {
		name : 'airCostOthPayCostCfrm',
		type : 'decimal'
	}, {
		name : 'airCostPayApply',
		type : 'decimal'
	}, {
		name : 'apwrOthPayWoDestRcvoPod',
		type : 'decimal'
	}, {
		name : 'apwrCostWoDestRcvtNpod',
		type : 'decimal'
	}, {
		name : 'apwrOthPayWoDestRcvtNpod',
		type : 'decimal'
	}, {
		name : 'airCodPodWo',
		type : 'decimal'
	}, {
		name : 'airCodCdUrNpod',
		type : 'decimal'
	}, {
		name : 'airCodCostWoCodRcvNpod',
		type : 'decimal'
	}, {
		name : 'airDrCh',
		type : 'decimal'
	}, {
		name : 'airDrWoDestRcvoNpod',
		type : 'decimal'
	}, {
		name : 'airDrWoCodRcvNpod',
		type : 'decimal'
	}, {
		name : 'aptAirCompany',
		type : 'decimal'
	}, {
		name : 'aptWoOthPay',
		type : 'decimal'
	}, {
		name : 'uroaDestCdNpod',
		type : 'decimal'
	}, {
		name : 'uroaDestChPod',
		type : 'decimal'
	}, {
		name : 'urtaDestChNpod',
		type : 'decimal'
	}, {
		name : 'urtaDestChPod',
		type : 'decimal'
	}, {
		name : 'urtaDestCdPod',
		type : 'decimal'
	}, {
		name : 'airCostOrigAgencyPayCfrm',
		type : 'decimal'
	}, {
		name : 'airCostDestAgencyPayGen',
		type : 'decimal'
	}, {
		name : 'airCostDestAgencyPayNcfrm',
		type : 'decimal'
	}, {
		name : 'aorEntry',
		type : 'decimal'
	}, {
		name : 'aorChUr',
		type : 'decimal'
	}, {
		name : 'aorCdUr',
		type : 'decimal'
	}, {
		name : 'apwrCostWoDestRcvoPod',
		type : 'decimal'
	}, {
		name : 'apwrCostWoDestRcvoNpod',
		type : 'decimal'
	}, {
		name : 'apwrOthPayWoDestRcvoNpod',
		type : 'decimal'
	}, {
		name : 'apwrCostWoDestRcvtPod',
		type : 'decimal'
	}, {
		name : 'apwrOthPayWoDestRcvtPod',
		type : 'decimal'
	}, {
		name : 'apwrOthPayWoOthRcv',
		type : 'decimal'
	}, {
		name : 'airCodChUrPod',
		type : 'decimal'
	}, {
		name : 'airCodCdUrPod',
		type : 'decimal'
	}, {
		name : 'airCodUpdWo',
		type : 'decimal'
	}, {
		name : 'airCodChUrNpod',
		type : 'decimal'
	}, {
		name : 'airCodDpayWoCodRcvPod',
		type : 'decimal'
	}, {
		name : 'airCodOthPayWoCodRcvPod',
		type : 'decimal'
	}, {
		name : 'airCodOpayWoCodRcvNpod',
		type : 'decimal'
	}, {
		name : 'airDrCd',
		type : 'decimal'
	}, {
		name : 'airDrWoDestRcvoPod',
		type : 'decimal'
	}, {
		name : 'airDrWoDestRcvtPod',
		type : 'decimal'
	}, {
		name : 'airDrWoDestRcvtNpod',
		type : 'decimal'
	}, {
		name : 'airDrWoOthRcv',
		type : 'decimal'
	}, {
		name : 'airDrWoCodRcvPod',
		type : 'decimal'
	}, {
		name : 'airDrPayApply',
		type : 'decimal'
	}, {
		name : 'aptWoAirPay',
		type : 'decimal'
	}, {
		name : 'bwor',
		type : 'decimal'
	}]
})

// Store
Ext.define('Foss.closing.mvrNAfrStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrNAfrModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('mvrNAfrQuery.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNAfrDto.queryList',
			totalProperty : 'mvrNAfrDto.count'
		}
	},
	submitParams : {},
	setSubmitParams : function(submitParams) {
		this.submitParams = submitParams;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.listeners = {
			'beforeload' : function(store, operation, eOpts) {
				Ext.apply(me.submitParams, {
					"limit" : operation.limit,
					"page" : operation.page,
					"start" : operation.start
				});
				Ext.apply(operation, {
					params : me.submitParams
				});
			}
		};
		me.callParent([ cfg ]);
	}
});

// 定义查询Form
Ext.define('Foss.closing.mvrNAfrQueryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	title : '查询条件',
	items : [{
		xtype:'container',
		layout:'column',
		width:800,
		bodyCls : 'autoHeight',
		defaultType : 'textfield',
		defaults : {
			margin : '10 5 10 5',
			labelWidth : 85,
			colspan : 1
		},
		items:[{
			xtype : 'combo',
			name : 'mvrNAfrDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrNAfr.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		}, {
	    	xtype: 'combobox',
			fieldLabel:'客户类型',
			name:'mvrNAfrDto.customerType',
			labelWidth:85,
	    	editable:false,
			store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
					[closing.mvrNAfr.SETTLEMENT__CUSTOMER_TYPE__AIR,
					 closing.mvrNAfr.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value:closing.mvrNAfr.SETTLEMENT__CUSTOMER_TYPE__AIR,
	    	columnWidth:.33,
	    	listeners:{
    		    	'change':function(th,newValue,oldValue){
					//获取表单等控件
					var form,//表单
						customerDetail,
						agencyDetial;
					//获取表单	
					form= this.up('form').getForm();
					//获取下面组件
					customerDetail = form.findField('mvrNAfrDto.customerDetail');
					agencyDetail = form.findField('mvrNAfrDto.agencyDetail');
					if(newValue=='A'){
						customerDetail.show();
						agencyDetail.hide();
						agencyDetail.setValue("");
					}else if(newValue=='AA'){
						customerDetail.hide();
						customerDetail.setValue("");
						agencyDetail.show();
					}
				}
			}
	    },{
			xtype : 'commonairlinesselector',
			fieldLabel : '航空公司',
			name : 'mvrNAfrDto.customerDetail',
			listWidth:300,//设置下拉框宽度
			isPaging:true, //分页
			columnWidth : .33
		}, {
			xtype : 'commonallagentselector',
			fieldLabel : '空运代理',
			name : 'mvrNAfrDto.agencyDetail',
			hidden:true,
			listWidth:300,//设置下拉框宽度
			isPaging:true, //分页
			columnWidth : .33
		}, {
			xtype : 'combo',
			name : 'mvrNAfrDto.productCode',
			fieldLabel : '业务类型',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrNAfr.getComboProductCodeStore().first().get('code'),
			store : closing.mvrNAfr.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrNAfrDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrNAfrDto.destOrgCode',
			fieldLabel : '到达部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		},  {
			border : 1,
			xtype : 'container',
			columnWidth : 1,
			colspan : 3,
			defaultType : 'button',
			layout : 'column',
			items : [ {
				text : '重置',
				columnWidth : .1,
				handler : function(){
					var form = this.up('form');
					form.getForm().reset();
				}
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .79
			}, {
				text : '查询',
				columnWidth : .1,
				cls : 'yellow_button',
				handler:function(){
					var form = this.up('form');
					var me = this;
					closing.mvrNAfr.querymvrNAfr(form,me);
				}
			} ]
		} ]
	}]
})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrNAfrQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	columnWidth : 1,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	bodyCls : 'autoHeight',
	frame : true,
	cls : 'autoHeight',
	store : null,
	autoScroll : true,
	height : 650,
	emptyText : '查询结果为空',
	viewConfig : {
		enableTextSelection : true,
		// 设置行可以选择，进而可以复制
		getRowClass:function(record, rowIndex, rowParams, store){
			count = store.getCount();
			if(count > 0 && rowIndex == count - 1){
				return 'closing-totalBgColor';
			}
		}
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : me.store,
				pageSize : 100,
				maximumSize : 500,
				plugins : 'pagesizeplugin',
				items:[me.getExportButton()]
			});
		}
		return me.pagingToolbar;
	},
	exportButton:null,
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrNAfr.exportmvrNAfr,
				disabled:!closing.mvrNAfr.isPermission('/stl-web/closing/mvrNAfrExport.action'),
				hidden:!closing.mvrNAfr.isPermission('/stl-web/closing/mvrNAfrExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		columns : [{
			text:'序号',
			xtype:'rownumberer',
			width:40,
			align:'center'
		}, {
			text : 'ID',
			width : 100,
			dataIndex : 'id',
			align:'center',
			hidden:true
		}, {
			text : '期间',
			width : 100,
			dataIndex : 'period',
			align:'center'
		}, {
			text : '业务类型',
			width : 100,
			dataIndex : 'productCode',
			align:'center',
			renderer:Foss.pkp.ProductData.rendererSubmitToDisplay
		}, {
			text : '客户编码',
			width : 100,
			dataIndex : 'customerCode',
			align:'center'
		}, {
			text : '客户名称',
			width : 100,
			dataIndex : 'customerName',
			align:'center'
		}, {
			text : '始发部门编码',
			width : 100,
			dataIndex : 'origOrgCode',
			align:'center'
		}, {
			text : '始发部门名称',
			width : 100,
			dataIndex : 'origOrgName',
			align:'center'
		}, {
			text : '到达部门编码',
			width : 100,
			dataIndex : 'destOrgCode',
			align:'center'
		}, {
			text : '到达部门名称',
			width : 100,
			dataIndex : 'destOrgName',
			align:'center'
		} ]
	}, {
		text : '空运成本',
		columns : [ {
			text : '空运航空公司运费确认',
			width : 200,
			dataIndex : 'airCostFeeConfirm',
			align:'right'
		}, {
			text : '空运出发代理应付确认',
			width : 200,
			dataIndex : 'airCostOrigAgencyPayCfrm',
			align:'right'
		}, {
			text : '空运到达代理应付生成',
			width : 200,
			dataIndex : 'airCostDestAgencyPayGen',
			align:'right'
		}, {
			text : '空运到达代理应付成本确认',
			width : 200,
			dataIndex : 'airCostDestAgencyPayCfrm',
			align:'right'
		}, {
			text : '空运到达代理应付成本反确认',
			width : 200,
			dataIndex : 'airCostDestAgencyPayNcfrm',
			align:'right'
		}, {
			text : '其它应付成本确认',
			width : 200,
			dataIndex : 'airCostOthPayCostCfrm',
			align:'right'
		}, {
			text : '空运成本付款申请',
			width : 200,
			dataIndex : 'airCostPayApply',
			align:'right'
		} ]
	}, {
		text : '空运其他应收',
		columns : [ {
			text : '空运其他应收录入',
			width : 200,
			dataIndex : 'aorEntry',
			align:'right'
		}, {
			text : '还款空运其他应收现金',
			width : 200,
			dataIndex : 'aorChUr',
			align:'right'
		}, {
			text : '还款空运其他应收银行',
			width : 200,
			dataIndex : 'aorCdUr',
			align:'right'
		} ]
	}, {
		text : '空运应付冲应收',
		columns : [ {
			text : '应付到达代理成本冲01应收到付运费已签收',
			width : 200,
			dataIndex : 'apwrCostWoDestRcvoPod',
			align:'right'
		}, {
			text : '应付到达代理成本冲01应收到付运费未签收',
			width : 200,
			dataIndex : 'apwrCostWoDestRcvoNpod',
			align:'right'
		}, {
			text : '其他应付冲01应收到付运费已签收',
			width : 200,
			dataIndex : 'apwrOthPayWoDestRcvoPod',
			align:'right'
		}, {
			text : '其他应付冲01应收到付运费未签收',
			width : 200,
			dataIndex : 'apwrOthPayWoDestRcvoNpod',
			align:'right'
		}, {
			text : '应付到达代理成本冲02应收到付运费已签收',
			width : 200,
			dataIndex : 'apwrCostWoDestRcvtPod',
			align:'right'
		}, {
			text : '应付到达代理成本冲02应收到付运费未签收',
			width : 200,
			dataIndex : 'apwrCostWoDestRcvtNpod',
			align:'right'
		}, {
			text : '其他应付冲02应收到付运费已签收',
			width : 200,
			dataIndex : 'apwrOthPayWoDestRcvtPod',
			align:'right'
		}, {
			text : '其他应付冲02应收到付运费未签收',
			width : 200,
			dataIndex : 'apwrOthPayWoDestRcvtNpod',
			align:'right'
		}, {
			text : '其他应付冲其他应收',
			width : 200,
			dataIndex : 'apwrOthPayWoOthRcv',
			align:'right'
		} ]
	}, {
		text : '空运代收货款',
		columns : [ {
			text : '空运还款代收货款现金已签收',
			width : 200,
			dataIndex : 'airCodChUrPod',
			align:'right'
		}, {
			text : '空运还款代收货款银行已签收',
			width : 200,
			dataIndex : 'airCodCdUrPod',
			align:'right'
		}, {
			text : '空运签收时已核销代收货款',
			width : 200,
			dataIndex : 'airCodPodWo',
			align:'right'
		}, {
			text : '空运反签收时已核销代收货款',
			width : 200,
			dataIndex : 'airCodUpdWo',
			align:'right'
		}, {
			text : '空运还款代收货款现金未签收',
			width : 200,
			dataIndex : 'airCodChUrNpod',
			align:'right'
		}, {
			text : '空运还款代收货款银行未签收',
			width : 200,
			dataIndex : 'airCodCdUrNpod',
			align:'right'
		}, {
			text : '空运到达代理应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'airCodDpayWoCodRcvPod',
			align:'right'
		}, {
			text : '空运其他应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'airCodOthPayWoCodRcvPod',
			align:'right'
		}, {
			text : '空运到达代理应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'airCodCostWoCodRcvNpod',
			align:'right'
		} , {
			text : '空运其他应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'airCodOpayWoCodRcvNpod',
			align:'right'
		} ]
	}, {
		text : '预收空运代理',
		columns : [ {
			text : '预收空运代理现金',
			width : 200,
			dataIndex : 'airDrCh',
			align:'right'
		}, {
			text : '预收空运代理银行',
			width : 200,
			dataIndex : 'airDrCd',
			align:'right'
		}, {
			text : '预收空运代理冲01应收到付运费已签收',
			width : 200,
			dataIndex : 'airDrWoDestRcvoPod',
			align:'right'
		}, {
			text : '预收空运代理冲02应收到付运费已签收',
			width : 200,
			dataIndex : 'airDrWoDestRcvtPod',
			align:'right'
		}, {
			text : '预收空运代理冲01应收到付运费未签收',
			width : 200,
			dataIndex : 'airDrWoDestRcvoNpod',
			align:'right'
		}, {
			text : '预收空运代理冲02应收到付运费未签收',
			width : 200,
			dataIndex : 'airDrWoDestRcvtNpod',
			align:'right'
		}, {
			text : '预收空运代理冲其他应收',
			width : 200,
			dataIndex : 'airDrWoOthRcv',
			align:'right'
		}, {
			text : '预收空运代理冲应收代收货款已签收',
			width : 200,
			dataIndex : 'airDrWoCodRcvPod',
			align:'right'
		}, {
			text : '预收空运代理冲应收代收货款未签收',
			width : 200,
			dataIndex : 'airDrWoCodRcvNpod',
			align:'right'
		}, {
			text : '空运退预收付款申请',
			width : 200,
			dataIndex : 'airDrPayApply',
			align:'right'
		} ]
	}, {
		text : '预付',
		columns : [ {
			text : '预付航空公司',
			width : 200,
			dataIndex : 'aptAirCompany',
			align:'right'
		}, {
			text : '预付冲应付航空公司',
			width : 200,
			dataIndex : 'aptWoAirPay',
			align:'right'
		}, {
			text : '预付冲其他应付',
			width : 200,
			dataIndex : 'aptWoOthPay',
			align:'right'
		} ]
	},{
		text : '坏账冲其它应收',
		columns : [ {
			text : '坏账冲其他应收',
			width : 200,
			dataIndex : 'bwor',
			align:'right'
		} ]
	}, {
		text : '还款运单总运费（到付）【01】',
		columns : [ {
			text : '还款现金未签收',
			width : 200,
			dataIndex : 'uroaDestChNpod',
			align:'right'
		}, {
			text : '还款银行未签收',
			width : 200,
			dataIndex : 'uroaDestCdNpod',
			align:'right'
		}, {
			text : '还款现金已签收',
			width : 200,
			dataIndex : 'uroaDestChPod',
			align:'right'
		} , {
			text : '还款银行已签收',
			width : 200,
			dataIndex : 'uroaDestCdPod',
			align:'right'
		} ]
	} , {
		text : '还款运单总运费（到付）【02】',
		columns : [ {
			text : '还款现金未签收',
			width : 200,
			dataIndex : 'urtaDestChNpod',
			align:'right'
		}, {
			text : '还款银行未签收',
			width : 200,
			dataIndex : 'urtaDestCdNpod',
			align:'right'
		}, {
			text : '还款现金已签收',
			width : 200,
			dataIndex : 'urtaDestChPod',
			align:'right'
		} , {
			text : '还款银行已签收',
			width : 200,
			dataIndex : 'urtaDestCdPod',
			align:'right'
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrNAfrStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrNAfr_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrNAfrQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrNAfrQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrNAfr_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrNAfr-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});