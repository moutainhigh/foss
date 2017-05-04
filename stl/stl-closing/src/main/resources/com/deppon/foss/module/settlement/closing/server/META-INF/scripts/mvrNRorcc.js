/**
 * 获取上个月期间
 */
closing.mvrNRorcc.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrNRorcc.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNRorcc.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNRorcc.PeriodModel',
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

closing.mvrNRorcc.setParams=function(form){
	// 定义查询参数
	var params={};
	
	var period = form.findField('vo.dto.period').getValue();
	if(period==null||period==''){
		// 期间不能为空
		Ext.Msg.alert('温馨提示', '期间不能为空');
		return null;	
	}
		
	// 获取FORM所有值
	params = form.getValues();		
	
	
	Ext.apply(params,{ 
		'vo.dto.productCodeList':stl.convertProductCode(form.findField('vo.dto.productCodeList').getValue())
	});
	
	
	return params;
}

/**
 * 重置
 */
closing.mvrNRorcc.reset = function(){
	this.up('form').getForm().reset();
	Ext.getCmp('Foss.closing.period').show();
	Ext.getCmp('Foss.closing.destOrgCode').show();
	Ext.getCmp('Foss.closing.origOrgCode').show();
	Ext.getCmp('Foss.closing.productCode').show();
}

/**
 * Form查询方法
 */
closing.mvrNRorcc.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNRorcc_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrNRorcc.setParams(form);
		if(null==params){
			return;
		}
		// 设置查询参数
		grid.store.setSubmitParams(params);
		// 设置该按钮灰掉
		me.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
//		grid.store.removeAll();
		// 设置统计值
		grid.store.loadPage(1,{
			callback	 : function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && rawData.isException){
					Ext.Msg.alert("提示", rawData.message);
					me.enable(true);
					return false;
				}				
				me.enable(true);
			}
		});
	}else {
		
		// 请检查输入条件是否合法
		Ext.Msg.alert('温馨提示', '请检查输入条件合法性');
	}
}

/**
 * 导出始发应收月报表
 */
closing.mvrNRorcc.exportmvrNRorcc = function(){
	// 获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrNRorcc_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	// 提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发外请车月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			// 创建一个form
			if(!Ext.fly('exportmvrNRorccForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrNRorccForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			// 导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNRorccExport.action'), 
				form: Ext.fly('exportmvrNRorccForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					// var json = Ext.decode(response.responseText);
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
closing.mvrNRorcc.getComboPeriodStore = function() {
	var now = new Date();
	var nowMonth = now.getMonth();
	var format = 'Ym';

	var periods = [];
	period = Ext.Date.format(now, format);
	periods.push({
				'name' : period,
				'value' : period
			});

	for (var i = 0; i < 12; i++) {
		nowMonth = now.getMonth();
		now.setMonth(nowMonth - 1);
		period = Ext.Date.format(now, format);
		periods.push({
					'name' : period,
					'value' : period
				});
	}

	return Ext.create('Ext.data.Store', {
				model : 'Foss.closing.mvrNRorccComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNRorcc.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNRorccComboModel', {
			extend : 'Ext.data.Model',
			fields : [{
						/* 显示名 */
						name : 'name',
						type : 'string'
					}, {
						/* 实际值 */
						name : 'value',
						type : 'string'
					}]
		})

// 始发应收月报表数据模型 model 
Ext.define('Foss.closing.mvrNRorccModel', {
			extend : 'Ext.data.Model',
			fields : [
			    'id', 
			    'period', 
			    'productCode', 
			    'customerCode', 
			    'customerName',
			    'origOrgCode', 
			    'origOrgName', 
			    'destOrgCode', 
			    'destOrgName',
			    'origUnifiedCode',
			    'destUnifiedCode',
			    {name : 'origPay',/* 出发付款（预付>0) */type : 'decimal'},
			    {name : 'destCfmOcost',/* 到达时确认首款成本（预付>0） */type : 'decimal'},
			    {name : 'destCfmDcost',/* 到达时确认尾款成本（预付>0） */type : 'decimal'},
			    {name : 'udestCfmOcost',/* 反到达时红冲首款成本 */type : 'decimal'},
			    {name : 'udestCrmDcost',/* 反到达时红冲尾款成本 */type : 'decimal'},
			    {name : 'origPayTail',/* 付款申请（始发付尾款） */type : 'decimal'},
			    {name : 'destPayTail',/* 付款申请（到达付尾款）（预付>0） */type : 'decimal'}]
		});

// 始发应收Store
Ext.define('Foss.closing.mvrNRorccStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNRorccModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('mvrNRorccQuery.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'vo.orccEntitys',
			totalProperty :'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   		 Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		}
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 始发应收月报查询Form
Ext.define('Foss.closing.mvrNRorccQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '查询条件',
			bodyCls : 'autoHeight',
			defaults : {
				margin : '10 5 10 5',
				labelWidth : 85,
				colspan : 1
				
			},
			defaultType : 'textfield',
			layout : {
				type : 'column',
				columns : 3
			},
			items : [{
				xtype : 'combo',
				id:'Foss.closing.period',
				name : 'vo.dto.period',
				fieldLabel : '期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNRorcc.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			},{
				xtype : 'combo',
				id:'Foss.closing.productCode',
				name : 'vo.dto.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNRorcc.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNRorcc.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				id:'Foss.closing.origOrgCode',
				name : 'vo.dto.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				id:'Foss.closing.destOrgCode',
				name : 'vo.dto.destOrgCode',
				fieldLabel : '到达部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				colspan : 3,
				defaultType : 'button',
				layout : 'column',
				items : [{
							text : '重置',
							columnWidth : .1,
							handler : closing.mvrNRorcc.reset
						}, {
							xtype : 'container',
							border : false,
							html : '&nbsp;',
							columnWidth : .8
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								  var form=this.up('form');
								  var me = this;
								  closing.mvrNRorcc.query(form,me)
							  }
						}]
			}]
		})


// 始发外请车表查询Grid
Ext.define('Foss.closing.mvrNRorccQueryGrid', {
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
	height : 850,
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
				handler:closing.mvrNRorcc.exportmvrNRorcc,
				disabled:!closing.mvrNRorcc.isPermission('/stl-web/closing/exportmvrNRorcc.action'),
				hidden:!closing.mvrNRorcc.isPermission('/stl-web/closing/exportmvrNRorcc.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		defaults:{
			style:"text-align:center"
		},
		columns : [ {
			xtype:'rownumberer',
			width:40
		}, {
			text : '期间',
			width:110,
			dataIndex : 'period'
		}, {
			text : '运输性质',
			width:110,
			dataIndex : 'productCode',
			renderer:function(value){
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, {
			text : '客户名称',
			width:110,
			dataIndex : 'customerName'
		}, {
			text : '客户编码',
			width:110,
			dataIndex : 'customerCode'		
		}, {
			text : '始发部门编</br>码',
			width:110,
			dataIndex : 'origOrgCode'
		}, {
			text : '始发部门名称',
			width:110,
			dataIndex : 'origOrgName'
		}, {
			text : '始发部门标杆编码',
			width:110,
			dataIndex : 'origUnifiedCode',
			hidden : true
		}, {
			text : '到达部门编码',
			width:110,
			dataIndex : 'destOrgCode',
		}, {
			text : '到达部门名称',
			width:110,
			dataIndex : 'destOrgName'
		}, {
			text : '到达部门标杆编码',
			width:110,
			dataIndex : 'destUnifiedCode',
			hidden:true
		}]
	}, {
		text : '外请车成本',
		columns : [ {
			text : '出发付款（预付>0)',
			width : 180,
			align : 'right',
			dataIndex : 'origPay'
		}, {
			text : '到达时确认首款成本(预付>0)',
			width : 180,
			align : 'right',
			dataIndex : 'destCfmOcost'
		}, {
			text : '到达时确认尾款成本(预付>0)',
			width : 180,
			align : 'right',
			dataIndex : 'destCfmDcost'
		}, {
			text : '反到达时红冲首款成本',
			width : 180,
			align : 'right',
			dataIndex : 'udestCfmOcost'
		}, {
			text : '反到达时红冲尾款成本',
			width : 180,
			align : 'right',
			dataIndex : 'udestCrmDcost'
		}, {
			text : '付款申请(始发付尾款)',
			width : 180,
			align : 'right',
			dataIndex : 'origPayTail'
		} , {
			text : '付款申请(到达付尾款)(预付>0)',
			width : 180,
			align : 'right',
			dataIndex : 'destPayTail'
		}    ]
	} ],	
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrNRorccStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrNRorcc_content')) {
				return;
			}

			closing.mvrNRorcc.mvrNRorccTotalRecord = Ext.create('Foss.closing.mvrNRorccModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNRorccQueryForm');
			
			// 显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNRorccQueryGrid');
			
			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNRorcc_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获得查询FORM
						getQueryFrom : function() {
							return queryForm;
						},
						// 获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						items : [queryForm,queryGrid],
						renderTo : 'T_closing-mvrNRorcc-body'
					});
		});