/**
 * 声明账期model
 */
Ext.define('Foss.mvrAfr.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrAfr.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAfr.PeriodModel',
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
closing.mvrAfr.queryMvrAfr = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrAfr_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
		closing.mvrAfr.setParamsValue(paramsV,form);
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
closing.mvrAfr.setParamsValue = function(paramsV,form){
	//客户类型
	var custType = form.findField('mvrAfrDto.customerType').getValue();
	//给客户默认值
	var customerCode = null;
	//客户类型
	if(custType=='A'){
		customerCode = form.findField('mvrAfrDto.customerDetail').getValue();
	}else if(custType=='AA'){
		customerCode = form.findField('mvrAfrDto.agencyDetail').getValue();
	}
	Ext.apply(paramsV,{ 
		'mvrAfrDto.customerCode':customerCode
	});
}
/**
 * 导出空运月报表
 */
closing.mvrAfr.exportMvrAfr = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrAfr_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出空运月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportMvrAfrForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrAfrForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrAfrExport.action'), 
				form: Ext.fly('exportMvrAfrForm'),
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
closing.mvrAfr.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrAfr.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 空运月报表数据模型
Ext.define('Foss.closing.MvrAfrModel', {
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
	},{
		name : 'voucherEndTime',
		type:'Date',
		convert:function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	},{
		name : 'customerType',
		type : 'String'
	},{
			/** 还款现金未签收 */
    		name : 'urDestChNpod',
    		type : 'decimal'
		},
		{
			/** 还款银行未签收 */
    		name : 'urDestCdNpod',
    		type : 'decimal'
		},
		{
    		/** 还款现金已签收 */
    		name : 'urDestChPod',
    		type : 'decimal'
		},
		{
    		/** 还款银行已签收 */
    		name : 'urDestCdPod',
    		type : 'decimal'
		},
		{
    		/** 空运航空公司运费确认 */
    		name : 'airCostComConfirm',
    		type : 'decimal'
		},
		{
    		/** 空运出发代理应付确认 */
    		name : 'airCostOrigAgencyCfm',
    		type : 'decimal'
		},
		{
    		/** 空运到达代理应付生成 */
    		name : 'airCostDestAgencyGen',
    		type : 'decimal'
		},
		{
    		/** 空运到达代理应付成本确认 */
    		name : 'airCostDestAgencyCfm',
    		type : 'decimal'
		},
		{
    		/** 空运到达代理应付成本反确认 */
    		name : 'airCostDestAgencyNcfm',
    		type : 'decimal'
		},{
			/** 其它应付成本确认 */
			name : 'airCostOtherConfirm',
			type : 'decimal'
		},
		{
    		/** 空运成本付款申请 */
    		name : 'airCostPayApply',
    		type : 'decimal'
		},
		{
    		/** 空运其他应收录入 */
    		name : 'othEntry',
    		type : 'decimal'
		},
		{
    		/** 还款空运其他应收现金 */
    		name : 'othRcvCh',
    		type : 'decimal'
		},
		{
    		/** 还款空运其他应收银行 */
    		name : 'othRcvCd',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理冲应收到付运费已签收 */
    		name : 'airDrDestRcvPod',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理冲应收到付运费未签收 */
    		name : 'airDrDestRcvNpod',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理现金 */
    		name : 'airDrCh',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理银行 */
    		name : 'airDrCd',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理冲其他应收 */
    		name : 'airDrWoOtherRcv',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理冲应收代收货款已签收 */
    		name : 'airDrWoCodRcvPod',
    		type : 'decimal'
		},
		{
    		/** 预收空运代理冲应收代收货款未签收 */
    		name : 'airDrWoCodRcvNpod',
    		type : 'decimal'
		},
		{
    		/** 空运退预收付款申请 */
    		name : 'airDrPayApply',
    		type : 'decimal'
		},
		{
    		/** 应付到达代理成本冲应收到付运费已签收 */
    		name : 'airPrAgencyWoDestRcvPod',
    		type : 'decimal'
		},
		{
    		/** 应付到达代理成本冲应收到付运费未签收 */
    		name : 'airPrAgencyWoDestRcvNpod',
    		type : 'decimal'
		},
		{
    		/** 其他应付冲应收到付运费已签收 */
    		name : 'airPrOtWoDestRcvPod',
    		type : 'decimal'
		},
		{
    		/** 其他应付冲应收到付运费未签收 */
    		name : 'airPrOthWoDestRcvNpod',
    		type : 'decimal'
		},
		{
    		/** 其他应付冲其他应收 */
    		name : 'airPrOthWoOthRcv',
    		type : 'decimal'
		},
		{
    		/** 空运还款代收货款现金已签收 */
    		name : 'airCodChPod',
    		type : 'decimal'
		},
		{
    		/** 空运还款代收货款银行已签收 */
    		name : 'airCodCdPod',
    		type : 'decimal'
		},
		{
    		/** 空运签收时已核销代收货款 */
    		name : 'airCodPodWoCod',
    		type : 'decimal'
		},
		{
    		/** 空运反签收时已核销代收货款 */
    		name : 'airCodNpodWoCod',
    		type : 'decimal'
		},
		{
    		/** 空运还款代收货款现金未签收 */
    		name : 'airCodChNpod',
    		type : 'decimal'
		},
		{
    		/** 空运还款代收货款银行未签收 */
    		name : 'airCodCdNpod',
    		type : 'decimal'
		},
		{
    		/** 空运到达代理应付冲应收代收货款已签收 */
    		name : 'airCodWoAgencyPayPod',
    		type : 'decimal'
		},
		{
    		/** 空运其他应付冲应收代收货款已签收 */
    		name : 'airCodWoOthPayCod',
    		type : 'decimal'
		},
		{
    		/** 空运到达代理应付冲应收代收货款未签收 */
    		name : 'airCodWoAgencyPayNpod',
    		type : 'decimal'
		},
		{
    		/** 空运其他应付冲应收代收货款未签收 */
    		name : 'airCodWoOthNpod',
    		type : 'decimal'
		},
		{
    		/** 预付航空公司 */
    		name : 'aptCom',
    		type : 'decimal'
		},
		{
    		/** 预付冲应付航空公司 */
    		name : 'aptWoComPay',
    		type : 'decimal'
		},
		{
    		/** 预付冲其他应付 */
    		name : 'aptWoOthPay',
    		type : 'decimal'
		},
		{
    		/** 坏账冲其他应收 */
    		name : 'bdrWoOthRcv',
    		type : 'decimal'
		}]
})

// Store
Ext.define('Foss.closing.MvrAfrStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.MvrAfrModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('mvrAfrQuery.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrAfrDto.queryList',
			totalProperty : 'mvrAfrDto.count'
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
Ext.define('Foss.closing.MvrAfrQueryForm', {
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
			name : 'mvrAfrDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrAfr.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		}, {
	    	xtype: 'combobox',
			fieldLabel:'客户类型',
			name:'mvrAfrDto.customerType',
			labelWidth:85,
	    	editable:false,
			store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
					[closing.mvrAfr.SETTLEMENT__CUSTOMER_TYPE__AIR,
					 closing.mvrAfr.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value:closing.mvrAfr.SETTLEMENT__CUSTOMER_TYPE__AIR,
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
					customerDetail = form.findField('mvrAfrDto.customerDetail');
					agencyDetail = form.findField('mvrAfrDto.agencyDetail');
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
			name : 'mvrAfrDto.customerDetail',
			listWidth:300,//设置下拉框宽度
			isPaging:true, //分页
			columnWidth : .33
		}, {
			xtype : 'commonallagentselector',
			fieldLabel : '空运代理',
			name : 'mvrAfrDto.agencyDetail',
			hidden:true,
			listWidth:300,//设置下拉框宽度
			isPaging:true, //分页
			columnWidth : .33
		}, {
			xtype : 'combo',
			name : 'mvrAfrDto.productCode',
			fieldLabel : '业务类型',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrAfr.getComboProductCodeStore().first().get('code'),
			store : closing.mvrAfr.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrAfrDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrAfrDto.destOrgCode',
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
					closing.mvrAfr.queryMvrAfr(form,me);
				}
			} ]
		} ]
	}]
})

// 空运月报表查询Grid
Ext.define('Foss.closing.MvrAfrQueryGrid', {
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
				handler:closing.mvrAfr.exportMvrAfr,
				disabled:!closing.mvrAfr.isPermission('/stl-web/closing/mvrAfrExport.action'),
				hidden:!closing.mvrAfr.isPermission('/stl-web/closing/mvrAfrExport.action')
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
			dataIndex : 'airCostComConfirm',
			align:'right'
		}, {
			text : '空运出发代理应付确认',
			width : 200,
			dataIndex : 'airCostOrigAgencyCfm',
			align:'right'
		}, {
			text : '空运到达代理应付生成',
			width : 200,
			dataIndex : 'airCostDestAgencyGen',
			align:'right'
		}, {
			text : '空运到达代理应付成本确认',
			width : 200,
			dataIndex : 'airCostDestAgencyCfm',
			align:'right'
		}, {
			text : '空运到达代理应付成本反确认',
			width : 200,
			dataIndex : 'airCostDestAgencyNcfm',
			align:'right'
		}, {
			text : '其它应付成本确认',
			width : 200,
			dataIndex : 'airCostOtherConfirm',
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
			dataIndex : 'othEntry',
			align:'right'
		}, {
			text : '还款空运其他应收现金',
			width : 200,
			dataIndex : 'othRcvCh',
			align:'right'
		}, {
			text : '还款空运其他应收银行',
			width : 200,
			dataIndex : 'othRcvCd',
			align:'right'
		} ]
	}, {
		text : '空运应付冲应收',
		columns : [ {
			text : '应付到达代理成本冲应收到付运费已签收',
			width : 200,
			dataIndex : 'airPrAgencyWoDestRcvPod',
			align:'right'
		}, {
			text : '应付到达代理成本冲应收到付运费未签收',
			width : 200,
			dataIndex : 'airPrAgencyWoDestRcvNpod',
			align:'right'
		}, {
			text : '其他应付冲应收到付运费已签收',
			width : 200,
			dataIndex : 'airPrOtWoDestRcvPod',
			align:'right'
		}, {
			text : '其他应付冲应收到付运费未签收',
			width : 200,
			dataIndex : 'airPrOthWoDestRcvNpod',
			align:'right'
		}, {
			text : '其他应付冲其他应收',
			width : 200,
			dataIndex : 'airPrOthWoOthRcv',
			align:'right'
		} ]
	}, {
		text : '空运代收货款',
		columns : [ {
			text : '空运还款代收货款现金已签收',
			width : 200,
			dataIndex : 'airCodChPod',
			align:'right'
		}, {
			text : '空运还款代收货款银行已签收',
			width : 200,
			dataIndex : 'airCodCdPod',
			align:'right'
		}, {
			text : '空运签收时已核销代收货款',
			width : 200,
			dataIndex : 'airCodPodWoCod',
			align:'right'
		}, {
			text : '空运反签收时已核销代收货款',
			width : 200,
			dataIndex : 'airCodNpodWoCod',
			align:'right'
		}, {
			text : '空运还款代收货款现金未签收',
			width : 200,
			dataIndex : 'airCodChNpod',
			align:'right'
		}, {
			text : '空运还款代收货款银行未签收',
			width : 200,
			dataIndex : 'airCodCdNpod',
			align:'right'
		}, {
			text : '空运到达代理应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'airCodWoAgencyPayPod',
			align:'right'
		}, {
			text : '空运其他应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'airCodWoOthPayCod',
			align:'right'
		}, {
			text : '空运到达代理应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'airCodWoAgencyPayNpod',
			align:'right'
		} , {
			text : '空运其他应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'airCodWoOthNpod',
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
			text : '预收空运代理冲应收到付运费已签收',
			width : 200,
			dataIndex : 'airDrDestRcvPod',
			align:'right'
		}, {
			text : '预收空运代理冲应收到付运费未签收',
			width : 200,
			dataIndex : 'airDrDestRcvNpod',
			align:'right'
		}, {
			text : '预收空运代理冲其他应收',
			width : 200,
			dataIndex : 'airDrWoOtherRcv',
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
			dataIndex : 'aptCom',
			align:'right'
		}, {
			text : '预付冲应付航空公司',
			width : 200,
			dataIndex : 'aptWoComPay',
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
			text : '坏账冲其它应收',
			width : 200,
			dataIndex : 'bdrWoOthRcv',
			align:'right'
		} ]
	}, {
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width : 200,
			dataIndex : 'urDestChNpod',
			align:'right'
		}, {
			text : '还款银行未签收',
			width : 200,
			dataIndex : 'urDestCdNpod',
			align:'right'
		}, {
			text : '还款现金已签收',
			width : 200,
			dataIndex : 'urDestChPod',
			align:'right'
		} , {
			text : '还款银行已签收',
			width : 200,
			dataIndex : 'urDestCdPod',
			align:'right'
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.MvrAfrStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrAfr_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.MvrAfrQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.MvrAfrQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrAfr_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrAfr-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});