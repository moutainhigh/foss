/**
 * 声明账期model
 */
Ext.define('Foss.mvrPartnerCom.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPartnerCom.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPartnerCom.PeriodModel',
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
closing.mvrPartnerCom.querymvrPartnerCom = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrPartnerCom_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
		//closing.mvrPartnerCom.setParamsValue(paramsV,form);
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
/*closing.mvrPartnerCom.setParamsValue = function(paramsV,form){
	//var customerCode = form.findField('mvrPartnerComDto.landStowage').getValue();
	Ext.apply(paramsV,{ 
		'mvrPtpPscVo.mvrPtpPscDto.customerCode':customerCode
	});
}*/
/**
 * 导出快递代理月报表
 */
closing.mvrPartnerCom.exportmvrPartnerCom = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrPartnerCom_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定月报表吗?',function(btn,text){
		if('yes' == btn){
			
			if(queryGrid.store.data.length==0){
				Ext.Msg.alert('温馨提示', '报表为空，不能进行导出操作！');
				return false;
			}
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrPartnerComForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrPartnerComForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrParentCom.action'),
				form: Ext.fly('exportmvrPartnerComForm'),
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
closing.mvrPartnerCom.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrPartnerCom.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 快递代理月报表数据模型
Ext.define('Foss.closing.mvrPartnerComModel', {
	extend : 'Ext.data.Model',
	fields : [{name:'id'},
		{name:'voucherBeginTime',
		type : 'Date',
			convert : function(value) {
				if (value != null) {
					var date = new Date(value);
					return date;
				} else {
					return null;
				}
			}},
		{name:'voucherEndTime',
			type : 'Date',
			convert : function(value) {
				if (value != null) {
					var date = new Date(value);
					return date;
				} else {
					return null;
				}
			}},
		{name:'period'},
		{name:'productCode'},
		{name:'customerName'},
		{name:'customerCode'},
		{name:'origOrgName'},
		{name:'origOrgCode'},
		{name:'destOrgName'},
		{name:'destOrgCode'},
		{name:'generatingOrgName'},
		{name:'generatingOrgCode'},
		{name:'payableOrgName'},
		{name:'payableOrgCode'},
		{name:'receivableOrgCode'},
		{name:'receivableOrgName'},
		{name:'expressOrigOrgCode'},
		{name:'expressOrigOrgName'},
		{name:'expressDestOrgCode'},
		{name:'expressDestOrgName'},
		{name:'expressCustomerOrgCode'},
		{name:'expressCustomerOrgName'},
		{name:'ptpPdeFcus'},
		{name:'ptpPdeApf'},
		{name:'ptpPdeAra'},
		{name:'ptpPdeApc'},
		{name:'ptpPdeCsrd'},
		{name:'ptpPdeCsrh'},
		{name:'ptpPdeRrpc'},
		{name:'ptpPdeFhbw'},
		{name:'ptpPdeBapa'},
		{name:'ptpRtfRtp'},
		{name:'ptpWfrRtr'},
		{name:'ptpWfrRtpr'},
		{name:'ptpSctLcrc'},
		{name:'ptpSctEac'},
		{name:'ptpSctZld'},
		{name:'ptpSctUeac'},
		{name:'ptpCphFpc'},
		{name:'ptpCphPrc'},
		{name:'ptpIprAcpa'},
		{name:'ptpIprPafc'},
		{name:'ptpIprPfpa'},
		{name:'ptpQfyjOcrfyPpf'},
		{name:'ptpQfyjOcrfyBof'},
		{name:'ptpQfyjOcrfyPc'},
		{name:'ptpQfyjOcrfyTif'},
		{name:'ptpQfyjOcrfyCf'},
		{name:'ptpQfyjOcrfyDf'},
		{name:'ptpQfyjOcrfyBdc'},
		{name:'ptpQfyjOcrfyOe'},
		{name:'ptpQfyjArbfyFrt'},
		{name:'ptpQfyjArbfyPup'},
		{name:'ptpQfyjArbfyDel'},
		{name:'ptpQfyjArbfyPkg'},
		{name:'ptpQfyjArbfyCod'},
		{name:'ptpQfyjArbfyDv'},
		{name:'ptpQfyjArbfyOt'},
		{name:'ptpQfyjWtpfyDf'},
		{name:'ptpQfyjWtpfyDc'},
		{name:'ptpQfyjWtpfyLuf'},
		{name:'ptpQfyjWtpfyUld'},
		{name:'ptpQfyjWtpfySr'},
		{name:'ptpQyjSftcyPpf'},
		{name:'ptpQyjSftcyBof'},
		{name:'ptpQyjSftcyPc'},
		{name:'ptpQyjSftcyTif'},
		{name:'ptpQyjSftcyCf'},
		{name:'ptpQyjSftcyDf'},
		{name:'ptpQyjSftcyBdc'},
		{name:'ptpQyjSftcyOe'},
		{name:'ptpQyjWtpfyDf'},
		{name:'ptpQyjWtpfyDc'},
		{name:'ptpQyjWtpfyLuf'},
		{name:'ptpQyjWtpfyUld'},
		{name:'ptpQyjWtpfySr'},
		{name:'ptpQyjSftcwPpf'},
		{name:'ptpQyjSftcwBof'},
		{name:'ptpQyjSftcwPc'},
		{name:'ptpQyjSftcwTif'},
		{name:'ptpQyjSftcwCf'},
		{name:'ptpQyjSftcwDf'},
		{name:'ptpQyjSftcwBdc'},
		{name:'ptpQyjSftcwOe'},
		{name:'ptpQyjWtpfwDf'},
		{name:'ptpQyjWtpfwDc'},
		{name:'ptpQyjWtpfwLuf'},
		{name:'ptpQyjWtpfwUld'},
		{name:'ptpQyjWtpfwSr'},
		{name:'ptpFqfyjSftcyPpf'},
		{name:'ptpFqfyjSftcyBof'},
		{name:'ptpFqfyjSftcyPc'},
		{name:'ptpFqfyjSftcyTif'},
		{name:'ptpFqfyjSftcyCf'},
		{name:'ptpFqfyjSftcyDf'},
		{name:'ptpFqfyjSftcyBdc'},
		{name:'ptpFqfyjSftcyOe'},
		{name:'ptpFqfyjDdysyFrt'},
		{name:'ptpFqfyjDdysyPup'},
		{name:'ptpFqfyjDdysyDel'},
		{name:'ptpFqfyjDdysyPkg'},
		{name:'ptpFqfyjDdysyCod'},
		{name:'ptpFqfyjDdysyDv'},
		{name:'ptpFqfyjDdysyOt'},
		{name:'ptpFqfyjWtpfyDf'},
		{name:'ptpFqfyjWtpfyDc'},
		{name:'ptpFqfyjWtpfyLuf'},
		{name:'ptpFqfyjWtpfyUld'},
		{name:'ptpFqfyjWtpfySr'},
		{name:'ptpFqyjSftcyPpf'},
		{name:'ptpFqyjSftcyBof'},
		{name:'ptpFqyjSftcyPc'},
		{name:'ptpFqyjSftcyTif'},
		{name:'ptpFqyjSftcyCf'},
		{name:'ptpFqyjSftcyDf'},
		{name:'ptpFqyjSftcyBdc'},
		{name:'ptpFqyjSftcyOe'},
		{name:'ptpFqyjWtpfyDf'},
		{name:'ptpFqyjWtpfyDc'},
		{name:'ptpFqyjWtpfyLuf'},
		{name:'ptpFqyjWtpfyUld'},
		{name:'ptpFqyjWtpfySr'},
		{name:'ptpFqyjSftcwPpf'},
		{name:'ptpFqyjSftcwBof'},
		{name:'ptpFqyjSftcwPc'},
		{name:'ptpFqyjSftcwTif'},
		{name:'ptpFqyjSftcwCf'},
		{name:'ptpFqyjSftcwDf'},
		{name:'ptpFqyjSftcwBdc'},
		{name:'ptpFqyjSftcwOe'},
		{name:'ptpFqyjWtpfwDf'},
		{name:'ptpFqyjWtpfwDc'},
		{name:'ptpFqyjWtpfwLuf'},
		{name:'ptpFqyjWtpfwUld'},
		{name:'ptpFqyjWtpfwSr'},
		{name:'pscTtReph'},

		/* 空运凭证报表新增 */
		{name:'pscAaRepUpd'},
		{name:'pscAaRepPod'},
		{name:'pscAaDesUpd'},
		{name:'pscAaDesPod'},
		{name:'pscPaOtUpd'},
		{name:'pscPaOtPod'}]
})

// Store
Ext.define('Foss.closing.mvrPartnerComStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrPartnerComModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('queryMvrParentCom.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrPtpPscVo.mvrPtpPscDto.mvrPtpPscEntityList',
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
Ext.define('Foss.closing.mvrPartnerComQueryForm', {
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
			name : 'mvrPtpPscVo.mvrPtpPscDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrPartnerCom.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		},{
			xtype: 'commonsaledepartmentselector',
			name:'mvrPtpPscVo.mvrPtpPscDto.customerCode',
			fieldLabel:'合伙人名称',       //合伙人名称
			columnWidth : .33,
			allowBlank: true,
			listWidth:300,//设置下拉框宽度
			isPaging:true //分页
		},{
			xtype : 'combo',
			name : 'mvrPtpPscVo.mvrPtpPscDto.productCode',
			fieldLabel : '运输性质',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrPartnerCom.getComboProductCodeStore().first().get('code'),
			store : closing.mvrPartnerCom.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpPscVo.mvrPtpPscDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpPscVo.mvrPtpPscDto.destOrgCode',
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
					closing.mvrPartnerCom.querymvrPartnerCom(form,me);
				}
			} ]
		} ]
	}]
})

// 快递代理月报表查询Grid
Ext.define('Foss.closing.mvrPartnerComQueryGrid', {
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
	/*viewConfig : {
		enableTextSelection : true,
		// 设置行可以选择，进而可以复制
		getRowClass:function(record, rowIndex, rowParams, store){
			count = store.getCount();
			if(count > 0 && rowIndex == count - 1){
				return 'closing-totalBgColor';
			}
		}
	},*/
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
				handler:closing.mvrPartnerCom.exportmvrPartnerCom,
				disabled:!closing.mvrPartnerCom.isPermission('/stl-web/closing/exportMvrParentCom.action'),
				hidden:!closing.mvrPartnerCom.isPermission('/stl-web/closing/exportMvrParentCom.action')
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
		}/*,  {
			text : '始发部门标杆编码',
			width : 100,
			dataIndex : '',
			align:'center'
		}*/,{
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
			text : '预收部门名称',
			width : 100,
			dataIndex : 'generatingOrgName',
			align:'center'
		}, {
			text : '预收部门编码',
			width : 100,
			dataIndex : 'generatingOrgCode',
			align:'center'
		}, {
			text : '应付部门名称',
			width : 100,
			dataIndex : 'payableOrgName',
			align:'center'
		}, {
			text : '应付部门编码',
			width : 100,
			dataIndex : 'payableOrgCode',
			align:'center'
		}, {
			text : '应收部门名称',
			width : 100,
			dataIndex : 'receivableOrgName',
			align:'center'
		}, {
			text : '应收部门编码',
			width : 100,
			dataIndex : 'receivableOrgCode',
			align:'center'
		}, {
			text : '出发快递点部名称',
			width : 100,
			dataIndex : 'expressOrigOrgName',
			align:'center'
		}, {
			text : '出发快递点部编码',
			width : 100,
			dataIndex : 'expressOrigOrgCode',
			align:'center'
		}, {
			text : '到达快递点部名称',
			width : 100,
			dataIndex : 'expressDestOrgName',
			align:'center'
		}, {
			text : '到达快递点部编码',
			width : 100,
			dataIndex : 'expressDestOrgCode',
			align:'center'
		} , {
			text : '客户名称快递点部名称',
			width : 100,
			dataIndex : 'expressCustomerOrgName',
			align:'center'
		}, {
			text : '客户名称快递点部编码',
			width : 100,
			dataIndex : 'expressCustomerOrgCode',
			align:'center'
		} ]
	},{
		text : '合伙人预存款【H】',
		columns : [ {
			text : '预收加盟商款项',
			width : 200,
			dataIndex : 'ptpPdeApf',
			align:'right'
		},{
			text : '预存款项充值',
			width : 200,
			dataIndex : 'ptpPdeFcus',
			align:'right'
		}, {
			text : '预收加盟冲应收始发提成已签收',
			width : 200,
			dataIndex : 'ptpPdeAra',
			align:'right'
		}, {
			text : '预收加盟冲应收委托派费已签收',
			width : 200,
			dataIndex : 'ptpPdeApc',
			align:'right'
		}, {
			text : '预收加盟冲应收到付运费已签收(D)',
			width : 200,
			dataIndex : 'ptpPdeCsrd',
			align:'right'
		}, {
			text : '预收加盟冲应收到付运费已签收（H）',
			width : 200,
			dataIndex : 'ptpPdeCsrh',
			align:'right'
		} , {
			text : '预收加盟冲应收代收货款已签收',
			width : 200,
			dataIndex : 'ptpPdeRrpc',
			align:'right'
		} , {
			text : '应收罚款已核销',
			width : 200,
			dataIndex : 'ptpPdeFhbw',
			align:'right'
		} , {
			text : '退预收付款申请',
			width : 200,
			dataIndex : 'ptpPdeBapa',
			align:'right'
		} ]
	},{
		text : '还款运单总运费（到付）【H】',
		columns : [ {
			text : '还款到付运费',
			width : 200,
			dataIndex : 'ptpRtfRtp',
			align:'right'
		},{
			text : '还款空运到付运费未签收',
			width : 200,
			dataIndex : 'pscAaRepUpd',
			align:'right'
		},{
			text : '还款空运到付运费已签收',
			width : 200,
			dataIndex : 'pscAaRepPod',
			align:'right'
		} ]
	},{
		text : '预收空运冲空运到付运费',
		columns : [ {
			text : '预收客户冲空运到付运费未签收',
			width : 200,
			dataIndex : 'pscAaDesUpd',
			align:'right'
		},{
			text : '预收客户冲空运到付运费已签收',
			width : 200,
			dataIndex : 'pscAaDesPod',
			align:'right'
		} ]
	},{
		text : '空运应付冲空运到付运费',
		columns : [ {
			text : '应付到达代理/其他应付冲到付运费未签收',
			width : 200,
			dataIndex : 'pscPaOtUpd',
			align:'right'
		},{
			text : '应付到达代理/其他应付冲到付运费已签收',
			width : 200,
			dataIndex : 'pscPaOtPod',
			align:'right'
		} ]
	}, {
		text : '运单月结还款金额【H】',
		columns : [ {
			text : '还款电汇未签收',
			width : 200,
			dataIndex : 'ptpWfrRtr',
			align:'right'
		}, {
			text : '还款电汇已签收',
			width : 200,
			dataIndex : 'ptpWfrRtpr',
			align:'right'
		},{
			text : '还款电汇（H）',
			width : 200,
			dataIndex : 'pscTtReph',
			align:'right'
		}]
	}, {
		text : '运单成本【H】',
		columns : [ {
			text : '零担到达提成入成本',
			width : 200,
			dataIndex : 'ptpSctLcrc',
			align:'right'
		}, {
			text : '快递到达提成入成本',
			width : 200,
			dataIndex : 'ptpSctEac',
			align:'right'
		}, {
			text : '零担到达提成反确认',
			width : 200,
			dataIndex : 'ptpSctZld',
			align:'right'
		}, {
			text : '快递到达提成反确认',
			width : 200,
			dataIndex : 'ptpSctUeac',
			align:'right'
		}]
	}, {
		text : '运单委托应付款【H】',
		columns : [ {
			text : '委托派费代付已签收',
			width : 200,
			dataIndex : 'ptpCphFpc',
			align:'right'
		}, {
			text : '委托派费代付反签收',
			width : 200,
			dataIndex : 'ptpCphPrc',
			align:'right'
		}]
	} , {
		text : '发起付款申请【H】',
		columns : [ {
			text : '到达提成付款申请',
			width : 200,
			dataIndex : 'ptpIprAcpa',
			align:'right'
		}, {
			text : '委托派费代付申请',
			width : 200,
			dataIndex : 'ptpIprPafc',
			align:'right'
		}, {
			text : '到付运费代付申请',
			width : 200,
			dataIndex : 'ptpIprPfpa',
			align:'right'
		} ]
	},{
		text : '签收运单【H】',
		columns : [ {
			text : '签收后非月结部分始发提成应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpQfyjOcrfyOe',
				align:'right'
			}]
		},{
			text : '签收后非月结部分到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyFrt',
				align:'right'
			},{
				text : '接货费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyPup',
				align:'right'
			},{
				text : '送货费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyDel',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyPkg',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyCod',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpQfyjArbfyDv',
				align:'right'
			},{
				text : '其它费用',
				width : 200,
				dataIndex : 'ptpQfyjArbfyOt',
				align:'right'
			}]
		},{
			text : '签收后非月结部分委托派费应收已核销金额',
			columns : [ {
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpQfyjWtpfyDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpQfyjWtpfyDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpQfyjWtpfyLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpQfyjWtpfyUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpQfyjWtpfySr',
				align:'right'
			}]
		},{
			text : '签收时月结部分始发提成应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpQyjSftcyPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpQyjSftcyBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpQyjSftcyPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpQyjSftcyTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpQyjSftcyCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpQyjSftcyDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpQyjSftcyBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpQyjSftcyOe',
				align:'right'
			}]
		},{
			text : '签收时月结部分委托派费应收已核销金额',
			columns : [ {
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpQyjWtpfyDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpQyjWtpfyDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpQyjWtpfyLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpQyjWtpfyUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpQyjWtpfySr',
				align:'right'
			}]
		},{
			text : '签收时月结部分始发提成应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpQyjSftcwPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpQyjSftcwBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpQyjSftcwPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpQyjSftcwTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpQyjSftcwCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpQyjSftcwDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpQyjSftcwBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpQyjSftcwOe',
				align:'right'
			}]
		},{
			text : '签收时月结部分委托派费应收未核销金额',
			columns : [ {
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpQyjWtpfwDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpQyjWtpfwDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpQyjWtpfwLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpQyjWtpfwUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpQyjWtpfwSr',
				align:'right'
			}]
		},{
			text : '反签收后非月结部分始发提成应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpFqfyjSftcyOe',
				align:'right'
			}]
		},{
			text : '反签收后非月结部分到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyFrt',
				align:'right'
			},{
				text : '接货费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyPup',
				align:'right'
			},{
				text : '送货费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyDel',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyPkg',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyCod',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyDv',
				align:'right'
			},{
				text : '其它费用',
				width : 200,
				dataIndex : 'ptpFqfyjDdysyOt',
				align:'right'
			}]
		},{
			text : '反签收后非月结部分委托派费应收已核销金额',
			columns : [ {
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpFqfyjWtpfyDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpFqfyjWtpfyDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpFqfyjWtpfyLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpFqfyjWtpfyUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpFqfyjWtpfySr',
				align:'right'
			}]
		},{
			text : '反签收时月结部分始发提成应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpFqyjSftcyDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpFqyjSftcyBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpFqyjSftcyOe',
				align:'right'
			}]
		},{
			text : '反签收时月结部分委托派费应收已核销金额',
			columns : [ {
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfyDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfyDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfyLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfyUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpFqyjWtpfySr',
				align:'right'
			}]
		},{
			text : '反签收时月结部分始发提成应收未核销金额',
			columns : [{
				text : '公布价运费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwPpf',
				align:'right'
			},{
				text : '开单操作费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwBof',
				align:'right'
			},{
				text : '包装费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwPc',
				align:'right'
			},{
				text : '保价费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwTif',
				align:'right'
			},{
				text : '代收货款手续费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwCf',
				align:'right'
			},{
				text : '送货费（不含上楼）',
				width : 200,
				dataIndex : 'ptpFqyjSftcwDf',
				align:'right'
			},{
				text : '基础送货费',
				width : 200,
				dataIndex : 'ptpFqyjSftcwBdc',
				align:'right'
			},{
				text : '其他费用',
				width : 200,
				dataIndex : 'ptpFqyjSftcwOe',
				align:'right'
			}]
		},{
			text : '反签收时月结部分委托派费应收未核销金额',
			columns : [{
				text : '送货上楼费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfwDf',
				align:'right'
			},{
				text : '送货进仓费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfwDc',
				align:'right'
			},{
				text : '大件上楼费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfwLuf',
				align:'right'
			},{
				text : '超远派送费',
				width : 200,
				dataIndex : 'ptpFqyjWtpfwUld',
				align:'right'
			},{
				text : '签收单返回',
				width : 200,
				dataIndex : 'ptpFqyjWtpfwSr',
				align:'right'
			}]
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrPartnerComStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrPartnerCom_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrPartnerComQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrPartnerComQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrPartnerCom_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrPartnerCom-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});