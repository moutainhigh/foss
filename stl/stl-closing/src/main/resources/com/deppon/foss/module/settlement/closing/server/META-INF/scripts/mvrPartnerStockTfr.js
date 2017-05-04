/**
 * 声明账期model
 */
Ext.define('Foss.mvrPartnerStockTfr.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPartnerStockTfr.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPartnerStockTfr.PeriodModel',
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
 * 查询合伙人股份中转月报表
 */
closing.mvrPartnerStockTfr.querymvrPartnerStockTfr = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrPartnerStockTfr_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
//		closing.mvrPartnerStockTfr.setParamsValue(paramsV,form);
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
				//var result = Ext.decode(operation.response.responseText);
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
//closing.mvrPartnerStockTfr.setParamsValue = function(paramsV,form){
//	var customerCode = form.findField('mvrPtpStDto.partner').getValue();
//	Ext.apply(paramsV,{ 
//		'mvrPtpStDto.customerCode':customerCode
//	});
//}
/**
 * 导出快递代理月报表
 */
closing.mvrPartnerStockTfr.exportmvrPartnerStockTfr = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrPartnerStockTfr_content');
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
			if(!Ext.fly('exportmvrPartnerStockTfrForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrPartnerStockTfrForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrPartnerStockTfrExport.action'), 
				form: Ext.fly('exportmvrPartnerStockTfrForm'),
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
closing.mvrPartnerStockTfr.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrPartnerStockTfr.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 合伙人股份中转月报表数据模型
Ext.define('Foss.closing.mvrPartnerStockTfrModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',//序号
		type : 'String'
	},{
		name : 'period',//期间
		type : 'String'
	},{
		name : 'productCode',//运输性质
		type : 'String'
	},{
		name : 'customerCode',//合伙人编码
		type : 'String'
	},{
		name : 'customerName',//合伙人名称
		type : 'String'
	},{
		name : 'origOrgCode',//始发部门编码
		type : 'String'
	},{
		name : 'origOrgName',//始发部门名称
		type : 'String'
	},{
		name : 'destOrgCode',//到达部门编码
		type : 'String'
	},{
		name : 'destOrgName',//到达部门名称
		type : 'String'
	},{
		name : 'origUnifiedCode',//始发部门标杆编码
		type : 'String'
	},{
		name : 'destUnifiedCode',//到达部门标杆编码
		type : 'String'
	},{
		name : 'depOrgCode',//预收部门编码
		type : 'String'
	},{
		name : 'depOrgName',//预收部门名称
		type : 'String'
	},{
		name : 'recOrgCode',//应收部门编码
		type : 'String'
	},{
		name : 'recOrgName',//应收部门名称
		type : 'String'
	},{
		name : 'payOrgCode',//应付部门编码
		type : 'String'
	},{
		name : 'payOrgName',//应付部门名称
		type : 'String'
	},{
		name : 'voucherBeginTime',//凭证开始日期
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
		name : 'voucherEndTime',//凭证结束日期
		type:'Date',
		convert:function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	},{ name:'depPtpAmount',type : 'decimal' }//预收加盟商款项
	,{ name:'depWoRecPod',type : 'decimal' }//预收加盟冲应收代收货款已签收
	,{ name:'rdDrPayApply',type : 'decimal' }//退预收付款申请
	,{ name:'orTtPod',type : 'decimal' }//还款电汇未签收
	,{ name:'orTtNpod',type : 'decimal' }//还款电汇已签收
	,{ name:'claimOrigtCost',type : 'decimal' }//理赔冲成本
	,{ name:'claimOrigtIncome',type : 'decimal' }//理赔入收入
	,{ name:'hitWoodenRec',type : 'decimal' }//代打木架应收
	,{ name:'arrivePayApply',type : 'decimal' }//到达提成付款申请
	,{ name:'deAdvanceApply',type : 'decimal' }//委托派费代付申请
	,{ name:'destAdvanceApply',type : 'decimal' }//到付运费代付申请
	,{ name:'adPayApply',type : 'decimal' }//奖励付款申请
	,{ name:'stTrcRec',type : 'decimal' }//培训费收款
	,{ name:'stCmcRec',type : 'decimal' }//会务费收款
	,{ name:'stTtReph',type : 'decimal' }//还款电汇（H）
	,{ name:'stPdeCsrd',type : 'decimal' }//预收加盟冲应收到付运费已签收(D)
	,{ name:'stPdeCsrh',type : 'decimal' }//预收加盟冲应收到付运费已签收（H）
	,{ name:'stRtfRtp',type : 'decimal' }//还款到付运费
	,{ name:'stCphFpc',type : 'decimal' }// 委托派费代付已签收
	,{ name:'stCphPrc',type : 'decimal' }// 委托派费代付反签收
	,{ name:'stAdPayPmApply',type : 'decimal' }// 奖励应付付款申请
	,{ name:'stEePayPmApply',type : 'decimal' }// 快递差错应付付款申请
	]
})

// Store
Ext.define('Foss.closing.mvrPartnerStockTfrStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrPartnerStockTfrModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('mvrPstQuery.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrPtpStDto.mvrPtpStEntityList',
			totalProperty : 'totalCount'
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
Ext.define('Foss.closing.mvrPartnerStockTfrQueryForm', {
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
			name : 'mvrPtpStDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrPartnerStockTfr.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		},{
			xtype : 'combo',
			name : 'mvrPtpStDto.productCode',
			fieldLabel : '运输性质',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrPartnerStockTfr.getComboProductCodeStore().first().get('code'),
			store : closing.mvrPartnerStockTfr.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype:'commonsaledepartmentselector',
	    	fieldLabel :'合伙人名称',
	    	name : 'mvrPtpStDto.customerCode',
			isPaging:true ,// 分页
			columnWidth:.33
    	},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpStDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpStDto.destOrgCode',
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
					closing.mvrPartnerStockTfr.querymvrPartnerStockTfr(form,me);
				}
			} ]
		} ]
	}]
})

// 合伙人股份中转月报表查询Grid
Ext.define('Foss.closing.mvrPartnerStockTfrQueryGrid', {
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
		enableTextSelection : true
		// 设置行可以选择，进而可以复制
		/*getRowClass:function(record, rowIndex, rowParams, store){
			count = store.getCount();
			if(count > 0 && rowIndex == count - 1){
				return 'closing-totalBgColor';
			}
		}*/
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : me.store,
				pageSize : 100,
				maximumSize : 500,
				plugins : 'pagesizeplugin'
				/*items:[me.getExportButton()]*/
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
				handler:closing.mvrPartnerStockTfr.exportmvrPartnerStockTfr,
				disabled:!closing.mvrPartnerStockTfr.isPermission('/stl-web/closing/mvrPartnerStockTfrExport.action'),
				hidden:!closing.mvrPartnerStockTfr.isPermission('/stl-web/closing/mvrPartnerStockTfrExport.action')
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
			text : '合伙人编码',
			width : 100,
			dataIndex : 'customerCode',
			align:'center'
		}, {
			text : '合伙人名称',
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
			text : '始发部门标杆编码',
			width : 110,
			dataIndex : 'origUnifiedCode',
			hidden:true,
			align:'center'
		},{
			text : '到达部门编码',
			width : 100,
			dataIndex : 'destOrgCode',
			align:'center'
		}, {
			text : '到达部门名称',
			width : 100,
			dataIndex : 'destOrgName',
			align:'center'
		}, {
			text : '到达部门标杆编码',
			width : 120,
			dataIndex : 'destUnifiedCode',
			hidden:true,
			align:'center'
		},{
			text : '预收部门编码',
			width : 100,
			dataIndex : 'depOrgCode',
			align:'center'
		}, {
			text : '预收部门名称',
			width : 100,
			dataIndex : 'depOrgName',
			align:'center'
		},{
			text : '应收部门编码',
			width : 100,
			dataIndex : 'recOrgCode',
			align:'center'
		}, {
			text : '应收部门名称',
			width : 100,
			dataIndex : 'recOrgName',
			align:'center'
		},{
			text : '应付部门编码',
			width : 100,
			dataIndex : 'payOrgCode',
			align:'center'
		}, {
			text : '应付部门名称',
			width : 100,
			dataIndex : 'payOrgName',
			align:'center'
		} ]
	}, {
		text : '合伙人预存款【H】',
		columns : [ {
			text : '预收加盟商款项',
			width : 100,
			dataIndex : 'depPtpAmount',
			align:'right'
		},{
			text : '预收加盟冲应收到付运费已签收(D)',
			width : 100,
			dataIndex : 'stPdeCsrd',
			align:'right'
		}, {
			text : '预收加盟冲应收到付运费已签收（H）',
			width : 100,
			dataIndex : 'stPdeCsrh',
			align:'right'
		},{
			text : '预收加盟冲应收代收货款已签收',
			width : 200,
			dataIndex : 'depWoRecPod',
			align:'right'
		}, {
			text : '退预收付款申请',
			width : 120,
			dataIndex : 'rdDrPayApply',
			align:'right'
		}]
	},{
		text : '还款运单总运费（到付）【H】',
		columns : [ {
			text : '还款到付运费',
			width : 100,
			dataIndex : 'stRtfRtp',
			align:'right'
		}]
	}, {
		text : '运单月结还款金额【H】',
		columns : [ {
			text : '还款电汇未签收',
			width : 120,
			dataIndex : 'orTtPod',
			align:'right'
		}, {
			text : '还款电汇已签收',
			width : 120,
			dataIndex : 'orTtNpod',
			align:'right'
		},{
			text : '还款电汇（H）',
			width : 120,
			dataIndex : 'stTtReph',
			align:'right'
		}]
	},{
		text : '运单委托应付款【H】',
		columns : [ {
			text : '委托派费代付已签收',
			width : 120,
			dataIndex : 'stCphFpc',
			align:'right'
		}, {
			text : '委托派费代付反签收',
			width : 120,
			dataIndex : 'stCphPrc',
			align:'right'
		}]
	}, {
		text : '收取合伙人款项',
		columns : [ {
			text : '理赔冲始发成本',
			width : 100,
			dataIndex : 'claimOrigtCost',
			align:'right'
		}, {
			text : '理赔冲到达成本',
			width : 100,
			dataIndex : 'claimOrigtIncome',
			align:'right'
		}, {
			text : '代打木架应收',
			width : 100,
			dataIndex : 'hitWoodenRec',
			align:'right'
		}]
	}, {
		text : '发起付款申请[H]',
		columns : [ {
			text : '到达提成付款申请',
			hidden:true,
			width : 120,
			dataIndex : 'arrivePayApply',
			align:'right'
		}, {
			text : '委托派费代付申请',
			width : 120,
			dataIndex : 'deAdvanceApply',
			align:'right'
		}, {
			text : '到付运费代付申请',
			width : 120,
			dataIndex : 'destAdvanceApply',
			align:'right'
		}]
	},{
		text : '支付合伙人款项',
		columns : [ {
			text : '奖励付款申请',
			width : 120,
			dataIndex : 'adPayApply',
			align:'right'
		} ,{
			text : '奖励应付付款申请',
			width : 120,
			dataIndex : 'stAdPayPmApply',
			align:'right'
		},{
			text : '快递差错应付付款申请',
			width : 120,
			dataIndex : 'stEePayPmApply',
			align:'right'
		}]
	},{
		text : '合伙人培训会务',
		columns : [ {
			text : '培训费收款',
			width : 120,
			dataIndex : 'stTrcRec',
			align:'right'
		},{
			text : '会务费收款',
			width : 120,
			dataIndex : 'stCmcRec',
			align:'right'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrPartnerStockTfrStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrPartnerStockTfr_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrPartnerStockTfrQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrPartnerStockTfrQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrPartnerStockTfr_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrPartnerStockTfr-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});