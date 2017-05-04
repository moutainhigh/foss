/**
 * 声明账期model
 */
Ext.define('Foss.mvrPartnerRew.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPartnerRew.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPartnerRew.PeriodModel',
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
closing.mvrPartnerRew.querymvrPartnerRew = function(f,me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrPartnerRew_content').getQueryGrid();

	if (form.isValid()) {
		//定义查询参数
		var paramsV= form.getValues();
		
		//closing.mvrPartnerRew.setParamsValue(paramsV,form);
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
/*closing.mvrPartnerRew.setParamsValue = function(paramsV,form){
	var customerCode = form.findField('mvrPartnerRewDto.landStowage').getValue();
	Ext.apply(paramsV,{ 
		'mvrPartnerRewDto.customerCode':customerCode
	});
}*/
/**
 * 导出快递代理月报表
 */
closing.mvrPartnerRew.exportmvrPartnerRew = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrPartnerRew_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出月报表吗?',function(btn,text){
		if('yes' == btn){
			
			if(queryGrid.store.data.length==0){
				Ext.Msg.alert('温馨提示', '报表明细为空，不能进行导出操作！');
				return false;
			}
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrPartnerRewForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrPartnerRewForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrPtpRps.action'),
				form: Ext.fly('exportmvrPartnerRewForm'),
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
closing.mvrPartnerRew.getComboPeriodStore = function() {

	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrPartnerRew.getComboProductCodeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 快递代理月报表数据模型
Ext.define('Foss.closing.mvrPartnerRewModel', {
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
		name : 'ptpErrSspc',
		type : 'String'
	},{
		name : 'ptpErrSsei',
		type : 'String'
	},{
		name : 'ptpErrShwfr',
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
	}]
})

// Store
Ext.define('Foss.closing.mvrPartnerRewStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrPartnerRewModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('queryMvrPtpRps.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrPtpRpsVo.mvrPtpRpsDto.mvrPtpRpsEntityList',
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
Ext.define('Foss.closing.mvrPartnerRewQueryForm', {
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
			name : 'mvrPtpRpsVo.mvrPtpRpsDto.period',
			fieldLabel : '期间',
			queryMode: 'remote', 
	    	store:Ext.create('Foss.mvrPartnerRew.PeriodStore'),
			displayField:'name',
			valueField:'name',
			allowBlank : false,
			columnWidth : .33
		},{
			xtype: 'commonsaledepartmentselector',
			name:'mvrPtpRpsVo.mvrPtpRpsDto.customerCode',
			fieldLabel:'合伙人名称',       //合伙人名称
			allowBlank: true,
			columnWidth : .33,
			listWidth:300,//设置下拉框宽度
			isPaging:true //分页
		},{
			xtype : 'combo',
			name : 'mvrPtpRpsVo.mvrPtpRpsDto.productCode',
			fieldLabel : '运输性质',
			forceSelection : true,
			displayField : 'name',
			valueField : 'code',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : closing.mvrPartnerRew.getComboProductCodeStore().first().get('code'),
			store : closing.mvrPartnerRew.getComboProductCodeStore(),
			columnWidth : .33
		},{
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpRpsVo.mvrPtpRpsDto.origOrgCode',
			fieldLabel : '始发部门',
			allowblank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			columnWidth : .33
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'mvrPtpRpsVo.mvrPtpRpsDto.destOrgCode',
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
					closing.mvrPartnerRew.querymvrPartnerRew(form,me);
				}
			} ]
		} ]
	}]
})

// 快递代理月报表查询Grid
Ext.define('Foss.closing.mvrPartnerRewQueryGrid', {
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
				plugins : 'pagesizeplugin'/*,
				items:[me.getExportButton()]*/
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
				handler:closing.mvrPartnerRew.exportmvrPartnerRew,
				disabled:!closing.mvrPartnerRew.isPermission('/stl-web/closing/exportMvrPtpRps.action'),
				hidden:!closing.mvrPartnerRew.isPermission('/stl-web/closing/exportMvrPtpRps.action')
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
		}/*, {
			text : '始发部门标杆编码',
			width : 100,
			dataIndex : 'destOrgName',
			align:'center'
		}*/ ,{
			text : '到达部门编码',
			width : 100,
			dataIndex : 'destOrgCode',
			align:'center'
		}/*, {
			text : '到达部门名称',
			width : 100,
			dataIndex : 'destOrgName',
			align:'center'
		}*/ , {
			text : '到达部门标杆编码',
			width : 100,
			dataIndex : 'destOrgName',
			align:'center'
		}]
	}, {
		text : '收取合伙人款项',
		columns : [ {
			text : '理赔冲始发成本',
			width : 200,
			dataIndex : 'ptpErrSspc',
			align:'right'
		}, {
			text : '理赔冲到达成本',
			width : 200,
			dataIndex : 'ptpErrSsei',
			align:'right'
		}, {
			text : '代打木架应收',
			width : 200,
			dataIndex : 'ptpErrShwfr',
			align:'right'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrPartnerRewStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrPartnerRew_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrPartnerRewQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrPartnerRewQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrPartnerRew_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		renderTo : 'T_closing-mvrPartnerRew-body',
		getQueryGrid : function() {
			return queryGrid;
		},
		getQueryForm:function(){
			return queryForm;
		}
	});
});