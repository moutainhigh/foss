/**
 * 声明账期model
 */
Ext.define('Foss.mvrLdd.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrLdd.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrLdd.PeriodModel',
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
 * 查询快递代理月报表
 */
closing.mvrLdd.querymvrLdd = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrLdd_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
		closing.mvrLdd.setParamsValue(paramsV,form);
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
closing.mvrLdd.setParamsValue = function(paramsV,form){
	var customerCode = form.findField('mvrLddDto.landStowage').getValue();
	Ext.apply(paramsV,{ 
		'mvrLddDto.customerCode':customerCode
	});
}
/**
 * 导出快递代理月报表
 */
closing.mvrLdd.exportmvrLdd = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrLdd_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出快递代理月报表吗?',function(btn,text){
		if('yes' == btn){
			
			if(queryGrid.store.data.length==0){
				Ext.Msg.alert('温馨提示', '报表明细为空，不能进行导出操作！');
				return false;
			}
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrLddForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrLddForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrLddExport.action'), 
				form: Ext.fly('exportmvrLddForm'),
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
closing.mvrLdd.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrLdd.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 快递代理月报表数据模型
Ext.define('Foss.closing.mvrLddModel', {
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
	},{ name:'landCostAgencyGen',type : 'decimal' }
	,{ name:'landCostAgencyCfm',type : 'decimal' }
	,{ name:'landCostAgencyNcfm',type : 'decimal' }
	,{ name:'landCostOtherConfirm',type : 'decimal' }
	,{ name:'landCostPayApply',type : 'decimal' }
	,{ name:'landOthEntry',type : 'decimal' }
	,{ name:'landOthRcvCh',type : 'decimal' }
	,{ name:'landOthRcvCd',type : 'decimal' }
	,{ name:'landCodChPod',type : 'decimal' }
	,{ name:'landCodCdPod',type : 'decimal' }
	,{ name:'landCodPodWoCod',type : 'decimal' }
	,{ name:'landCodNpodWoCod',type : 'decimal' }
	,{ name:'landCodChNpod',type : 'decimal' }
	,{ name:'landCodCdNpod',type : 'decimal' }
	,{ name:'landCodWoAgencyPayPod',type : 'decimal' }
	,{ name:'landCodWoOthPayCod',type : 'decimal' }
	,{ name:'landCodWoAgencyPayNpod',type : 'decimal' }
	,{ name:'landCodWoOthNpod',type : 'decimal' }
	,{ name:'landBdrWoOthRcv',type : 'decimal' }
	,{ name:'landUrDestChNpod',type : 'decimal' }
	,{ name:'landUrDestCdNpod',type : 'decimal' }
	,{ name:'landUrDestChPod',type : 'decimal' }
	,{ name:'landUrDestCdPod',type : 'decimal' }
	,{ name:'landPrAgencyWoDestRcvPod',type : 'decimal' }
	,{ name:'landPrAgencyWoDestRcvNp',type : 'decimal' }
	,{ name:'landPrOtWoDestRcvPod',type : 'decimal' }
	,{ name:'landPrOthWoDestRcvNpod',type : 'decimal' }
	,{ name:'landPrOthWoOthRcv',type : 'decimal' }
	,{ name:'landDrDestRcvPod',type : 'decimal' }
	,{ name:'landDrDestRcvNpod',type : 'decimal' }
	,{ name:'landDrCh',type : 'decimal' }
	,{ name:'landDrCd',type : 'decimal' }
	,{ name:'landDrWoOtherRcv',type : 'decimal' }
	,{ name:'landDrWoCodRcvPod',type : 'decimal' }
	,{ name:'landDrWoCodRcvNpod',type : 'decimal' }
	,{ name:'landDrPayApply',type : 'decimal' }
	,{ name:'landAptCom',type : 'decimal' }
	,{ name:'landAptWoComPay',type : 'decimal' }
	,{ name:'landAptWoOthPay',type : 'decimal' }]
})

// Store
Ext.define('Foss.closing.mvrLddStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrLddModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('mvrLddQuery.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrLddDto.queryList',
			totalProperty : 'mvrLddDto.count'
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
Ext.define('Foss.closing.mvrLddQueryForm', {
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
			name : 'mvrLddDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrLdd.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		},{
			xtype:'commonLdpAgencyCompanySelector',
	    	fieldLabel :'快递代理',
	    	name : 'mvrLddDto.landStowage',
			isPaging:true ,// 分页
			columnWidth:.33
    	},{
			xtype : 'combo',
			name : 'mvrLddDto.productCode',
			fieldLabel : '运输性质',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrLdd.getComboProductCodeStore().first().get('code'),
			store : closing.mvrLdd.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrLddDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrLddDto.destOrgCode',
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
					closing.mvrLdd.querymvrLdd(form,me);
				}
			} ]
		} ]
	}]
})

// 快递代理月报表查询Grid
Ext.define('Foss.closing.mvrLddQueryGrid', {
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
				handler:closing.mvrLdd.exportmvrLdd,
				disabled:!closing.mvrLdd.isPermission('/stl-web/closing/mvrLddExport.action'),
				hidden:!closing.mvrLdd.isPermission('/stl-web/closing/mvrLddExport.action')
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
			text : '运输性质',
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
		text : '快递代理成本',
		columns : [ {
			text : '快递代理应付生成',
			width : 200,
			dataIndex : 'landCostAgencyGen',
			align:'right'
		}, {
			text : '快递代理应付成本确认',
			width : 200,
			dataIndex : 'landCostAgencyCfm',
			align:'right'
		}, {
			text : '快递代理应付成本反确认',
			width : 200,
			dataIndex : 'landCostAgencyNcfm',
			align:'right'
		}, {
			text : '其它应付成本确认',
			width : 200,
			dataIndex : 'landCostOtherConfirm',
			align:'right'
		}, {
			text : '快递代理成本付款申请',
			width : 200,
			dataIndex : 'landCostPayApply',
			align:'right'
		}]
	}, {
		text : '快递代理其他应收',
		columns : [ {
			text : '快递代理其他应收录入',
			width : 200,
			dataIndex : 'landOthEntry',
			align:'right'
		}, {
			text : '还款快递代理其他应收现金',
			width : 200,
			dataIndex : 'landOthRcvCh',
			align:'right'
		}, {
			text : '还款快递代理其他应收银行',
			width : 200,
			dataIndex : 'landOthRcvCd',
			align:'right'
		} ]
	}, {
		text : '快递代理代收货款',
		columns : [ {
			text : '快递代理还款代收货款现金已签收',
			width : 200,
			dataIndex : 'landCodChPod',
			align:'right'
		}, {
			text : '快递代理还款代收货款银行已签收',
			width : 200,
			dataIndex : 'landCodCdPod',
			align:'right'
		}, {
			text : '快递代理还款代收货款现金未签收',
			width : 200,
			dataIndex : 'landCodChNpod',
			align:'right'
		}, {
			text : '快递代理签收时已核销代收货款',
			width : 200,
			dataIndex : 'landCodPodWoCod',
			align:'right'
		}, {
			text : '快递代理反签收时已核销代收货款',
			width : 200,
			dataIndex : 'landCodNpodWoCod',
			align:'right'
		} , {
			text : '快递代理还款代收货款银行未签收',
			width : 200,
			dataIndex : 'landCodCdNpod',
			align:'right'
		} , {
			text : '快递代理应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'landCodWoAgencyPayPod',
			align:'right'
		} , {
			text : '快递代理其他应付冲应收代收货款已签收',
			width : 200,
			dataIndex : 'landCodWoOthPayCod',
			align:'right'
		} , {		
			text : '快递代理应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'landCodWoAgencyPayNpod',
			align:'right'
		}, {
			text : '快递代理其他应付冲应收代收货款未签收',
			width : 200,
			dataIndex : 'landCodWoOthNpod',
			align:'right'
		}]
	},{
		text : '坏账冲其它应收',
		columns : [ {
			text : '坏账冲其他应收',
			width : 200,
			dataIndex : 'landBdrWoOthRcv',
			align:'right'
		} ]
	}, {
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width : 200,
			dataIndex : 'landUrDestChNpod',
			align:'right'
		}, {
			text : '还款银行未签收',
			width : 200,
			dataIndex : 'landUrDestCdNpod',
			align:'right'
		}, {
			text : '还款现金已签收',
			width : 200,
			dataIndex : 'landUrDestChPod',
			align:'right'
		}, {
			text : '还款银行已签收',
			width : 200,
			dataIndex : 'landUrDestCdPod',
			align:'right'
		}]
	}, {
		text : '快递代理应付冲应收',
		columns : [ {
			text : '应付代理成本冲应收到付运费已签收',
			width : 200,
			dataIndex : 'landPrAgencyWoDestRcvPod',
			align:'right'
		}, {
			text : '应付到达代理成本冲应收到付运费未签收',
			width : 200,
			dataIndex : 'landPrAgencyWoDestRcvNp',
			align:'right'
		}, {
			text : '其他应付冲应收到付运费已签收',
			width : 200,
			dataIndex : 'landPrOtWoDestRcvPod',
			align:'right'
		}, {
			text : '其他应付冲应收到付运费未签收',
			width : 200,
			dataIndex : 'landPrOthWoDestRcvNpod',
			align:'right'
		}, {
			text : '应付冲其他应收',
			width : 200,
			dataIndex : 'landPrOthWoOthRcv',
			align:'right'
		}]
	}, {
		text : '预收快递代理',
		columns : [ {
			text : '预收快递代理现金',
			width : 200,
			dataIndex : 'landDrCh',
			align:'right'
		}, {
			text : '预收快递代理银行',
			width : 200,
			dataIndex : 'landDrCd',
			align:'right'
		}, {
			text : '预收快递代理冲应收到付运费已签收',
			width : 200,
			dataIndex : 'landDrDestRcvPod',
			align:'right'
		} , {
			text : '预收快递代理冲应收到付运费未签收',
			width : 200,
			dataIndex : 'landDrDestRcvNpod',
			align:'right'
		} , {
			text : '预收快递代理冲其他应收',
			width : 200,
			dataIndex : 'landDrWoOtherRcv',
			align:'right'
		}, {
			text : '预收快递代理冲应收代收货款已签收',
			width : 200,
			dataIndex : 'landDrWoCodRcvPod',
			align:'right'
		}, {
			text : '预收快递代理冲应收代收货款未签收',
			width : 200,
			dataIndex : 'landDrWoCodRcvNpod',
			align:'right'
		}, {
			text : '快递代理退预收付款申请',
			width : 200,
			dataIndex : 'landDrPayApply',
			align:'right'
		}]
	} , {
		text : '预付',
		columns : [ {
			text : '预付快递代理公司',
			width : 200,
			dataIndex : 'landAptCom',
			align:'right'
		}, {
			text : '预付冲应付快递代理公司',
			width : 200,
			dataIndex : 'landAptWoComPay',
			align:'right'
		}, {
			text : '预付冲其他应付',
			width : 200,
			dataIndex : 'landAptWoOthPay',
			align:'right'
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrLddStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrLdd_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrLddQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrLddQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrLdd_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrLdd-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});