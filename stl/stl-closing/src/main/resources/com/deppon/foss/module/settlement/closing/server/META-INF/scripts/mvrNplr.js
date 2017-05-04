/**
 * 声明账期model
 */
Ext.define('Foss.mvrNplr.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNplr.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNplr.PeriodModel',
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
 * 查询方法
 */
closing.mvrNplr.queryMvrNplr=function(form,me){
	var m=Ext.getCmp('T_closing-mvrNplr_content');
	if(m){
		var grid = m.getQueryGrid();
		var store=grid.getStore();
		if(store){
			if(grid.isHidden()){
				grid.show();
			}
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			// 加载第一页数据
			store.loadPage(1,{
						callback	 : function(records, operation, success) {
							var result =   Ext.decode(operation.response.responseText);  
							if (!success && result.isException) {
								Ext.Msg.alert("提示", result.message);
								me.enable(true);
								return false;
							}
							
							me.enable(true);
						}
					});
		}
	}

}

/**
 * 导出专线到达月报表
 */
closing.mvrNplr.exportMvrNplr = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrNplr_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出偏线月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = mainPane.getQueryForm().getValues();
			
			Ext.apply(params,{ 
				'mvrNplrVo.productCodeList':stl.convertProductCode(mainPane.getQueryForm().getForm().findField('mvrNplrVo.productCodeList').getValue())
			});
			
			//创建一个form
			if(!Ext.fly('exportMvrNplrForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNplrForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNplrExport.action'), 
				form: Ext.fly('exportMvrNplrForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//获取响应的json字符串
					var jsonText = Ext.decode(response.responseText.trim());
                   	//导出失败
                   	if(jsonText.message!=null&&jsonText.message!=''){
                     	Ext.Msg.alert('温馨提示',jsonText.message);
                     }
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				}
		    });
			
		}
	});	
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrNplr.getComboPeriodStore = function() {
	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNplr.getComboProductTypeStore = function() {
	var productStore = Ext.create('Foss.pkp.ProductStore');
	
	/*for(var i=0;i<productStore.data.length;i++){
		var record = productStore.data.items[i];
		if(record.get('code') == 'AF' || record.get('code') == 'PLF'){ //去掉精准空运，汽运偏线
			productStore.remove(record);
		}
	}*/
	
	
	return productStore;
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.MvrNplrComboModel', {
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

// 专线到达月报表数据模型
Ext.define('Foss.closing.MvrNplrModel', {
			extend : 'Ext.data.Model',
			fields : [
					{name:'id',                   type:'string'},
					{name:'period',               type:'string'},
					{name:'productCode',          type:'string'},
					{name:'customerCode',         type:'string'},
					{name:'customerName',         type:'string'},
					{name:'origOrgCode',          type:'string'},
					{name:'origOrgName',          type:'string'},
					{name:'destOrgCode',          type:'string'},
					{name:'destOrgName',          type:'string'},
					{name:'origUnifiedCode',      type:'string'},
					{name:'destUnifiedCode',      type:'string'},
					{name:'customerType',         type:'string'},
					{name:'uropDestChNpod',       type:'decimal'},
					{name:'uropDestCdNpod',       type:'decimal'},
					{name:'uropDestChPod',        type:'decimal'},
					{name:'uropDestCdPod',        type:'decimal'},
					{name:'urtpDestChNpod',       type:'decimal'},
					{name:'urtpDestCdNpod',       type:'decimal'},
					{name:'urtpDestChPod',        type:'decimal'},
					{name:'urtpDestCdPod',        type:'decimal'},
					{name:'plCostWoDestRcvoPod',  type:'decimal'},
					{name:'plCostWoDestRcvoNpod', type:'decimal'},
					{name:'plCostWoDestRcvtPod',  type:'decimal'},
					{name:'plCostWoDestRcvtNpod', type:'decimal'},
					{name:'plCostEntry',          type:'decimal'},
					{name:'plCostConfirm',        type:'decimal'},
					{name:'plCostNconfirm',       type:'decimal'},
					{name:'plCostPayApply',       type:'decimal'},
					{name:'plDrWoDestRcvtPod',    type:'decimal'},
					{name:'plDrCh',               type:'decimal'},
					{name:'plDrWoDestRcvoPod',    type:'decimal'},
					{name:'plDrWoDestRcvoNpod',   type:'decimal'},
					{name:'plDrWoDestRcvtNpod',   type:'decimal'},
					{name:'plDrCd',               type:'decimal'},
					{name:'plDrPayApply',         type:'decimal'},
					{name:'plCostOpCrm',         type:'decimal'},
					{name:'plDrWoOr',         type:'decimal'},
					{name:'porEntry',         type:'decimal'},
					{name:'urPorCh',         type:'decimal'},
					{name:'urPorCd',         type:'decimal'},
					{name:'popWoDroPod',         type:'decimal'},
					{name:'popWoDroNpod',         type:'decimal'},
					{name:'popWoDrtPod',         type:'decimal'},
					{name:'popWoDrtNpod',         type:'decimal'},
					{name:'pocpWoOr',         type:'decimal'}
				]
		})

//Store
Ext.define('Foss.closing.MvrNplrStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.MvrNplrModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNplr.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNplrVo.mvrNplrEntityList',
			totalProperty : 'mvrNplrVo.mvrNplrDto.count'
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
	   		/* Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); */ 		
	   		var queryForm = Ext.getCmp('T_closing-mvrNplr_content').getQueryForm();
			if (queryForm) {
				var form = queryForm.getForm();
				var params = form.getValues();
				
				Ext.apply(params,{ 
					'mvrNplrVo.productCodeList':stl.convertProductCode(form.findField('mvrNplrVo.productCodeList').getValue())
				});
				
				Ext.apply(operation, {
					params:params
	   			});				
			}
	   			
	   		}
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 定义查询Form
Ext.define('Foss.closing.MvrNplrQueryForm', {
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
				name : 'mvrNplrVo.period',
				fieldLabel : '期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNplr.getComboPeriodStore().getAt(1)
						.get('value'),
				store : closing.mvrNplr.getComboPeriodStore(),*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNplr.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			}, {
				xtype : 'combo',
				name : 'mvrNplrVo.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNplr.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNplr.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'commoncustomerselector',
				fieldLabel : '客户信息',
				singlePeopleFlag : 'Y',
				name : 'mvrNplrVo.customerCode',
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNplrVo.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNplrVo.destOrgCode',
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
							handler : function(){
								this.up('form').getForm().reset();
							}
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
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.mvrNplr.queryMvrNplr(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.MvrNplrQueryGrid', {
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
		enableTextSelection : true, // 设置行可以选择，进而可以复制
		getRowClass : function(record,rowIndex,rowParams,store){
	            if(record.data.period == '汇总'){ // 汇总的样式
	            	return 'closing-totalBgColor';
	            }else{
	            	return ''; 
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
				handler:closing.mvrNplr.exportMvrNplr,
				disabled:!closing.mvrNplr.isPermission('/stl-web/closing/mvrNplrExport.action'),
				hidden:!closing.mvrNplr.isPermission('/stl-web/closing/mvrNplrExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'序号',
			xtype:'rownumberer',
			width:40
		},{
			text : 'ID',
			width:10,
			dataIndex: 'id',
			hidden: true
		}, {
			text : '期间',
			width:95,
			dataIndex : 'period'
		}, {
			text : '业务类型',
			width:95,
			dataIndex : 'productCode',
			renderer:function(value){
				/*if(value == 'FSF'){
					return '精准城运';
				}else if(value == 'FLF'){
					return '精准卡运';
				}else if(value == 'AF'){
					return '精准空运';
				}else if(value == 'SRF'){
					return '精准汽运(短途)';
				}else if(value == 'LRF'){
					return '精准汽运(长途)';
				}else if(value == 'PLF'){
					return '汽运偏线';
				}else if(value == 'WVH'){
					return '整车';
				}else{
					return value;
				}*/	
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);

			}
		}, {
			text : '客户名称',
			width:95,
			dataIndex : 'customerName'
		},  {
			text : '客户编码',
			width:95,
			dataIndex : 'customerCode',
		},{
			text : '始发部门编码',
			width:95,
			dataIndex : 'origOrgCode'
		}, {
			text : '始发部门名称',
			width:95,
			dataIndex : 'origOrgName'
		},{
			text : '到达部门编码',
			width:95,
			dataIndex : 'destOrgCode'
		}, {
			text : '到达部门名称',
			width:95,
			dataIndex : 'destOrgName'
		}]
	},
	
	 {
		text : '偏线代理成本',
		defaults : {
			style :"text-align:center"
		},
		columns : [{text:'外发单录入',width:95,dataIndex:'plCostEntry'},
		           {text:'偏线代理成本确认',width:95,dataIndex:'plCostConfirm'},
		           {text:'偏线代理成本反确认',width:95,dataIndex:'plCostNconfirm'},
		           {text:'应付偏线代理成本冲01应收到付运费已签收',width:95,dataIndex:'plCostWoDestRcvoPod'},
		           {text:'应付偏线代理成本冲02应收到付运费已签收',width:95,dataIndex:'plCostWoDestRcvtPod'},
		           {text:'应付偏线代理成本冲01应收到付运费未签收',width:95,dataIndex:'plCostWoDestRcvoNpod'},
		           {text:'应付偏线代理成本冲02应收到付运费未签收',width:95,dataIndex:'plCostWoDestRcvtNpod'},
		           {text:'其它应付成本确认',width:95,dataIndex:'plCostOpCrm'},
		           {text:'偏线代理成本付款申请',width:95,dataIndex:'plCostPayApply'}
		           ]
	}, {
		text : '还款运单总运费（到付）【01】',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {text:'还款现金未签收',width:95,dataIndex : 'uropDestChNpod'},
		            {text:'还款银行未签收',width:95,dataIndex : 'uropDestCdNpod'},
		            {text:'还款现金已签收',width:95,dataIndex : 'uropDestChPod'},
		            {text:'还款银行已签收',width:95,dataIndex : 'uropDestCdPod'}
		            ]
	},{
		text : '还款运单总运费（到付）【02】',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {text:'还款现金未签收',width:95,dataIndex : 'urtpDestChNpod'},
		            {text:'还款银行未签收',width:95,dataIndex : 'urtpDestCdNpod'},
		            {text:'还款现金已签收',width:95,dataIndex : 'urtpDestChPod'},
		            {text:'还款银行已签收',width:95,dataIndex : 'urtpDestCdPod'}
		            ]
	},{
		text : '预收偏线代理',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {text:'预收偏线代理现金',width:95,dataIndex:'plDrCh'},
		            {text:'预收偏线代理银行',width:95,dataIndex:'plDrCd'},
		            {text:'预收偏线代理冲01应收到付运费已签收',width:95,dataIndex:'plDrWoDestRcvoPod'},
		            {text:'预收偏线代理冲01应收到付运费未签收',width:95,dataIndex:'plDrWoDestRcvoNpod'},
		            {text:'预收偏线代理冲02应收到付运费已签收',width:95,dataIndex:'plDrWoDestRcvtPod'},
		            {text:'预收偏线代理冲02应收到付运费未签收',width:95,dataIndex:'plDrWoDestRcvtNpod'},
		            {text:'预收偏线代理冲其他应收',width:95,dataIndex:'plDrWoOr'},
		            {text:'偏线退预收付款申请',width:95,dataIndex:'plDrPayApply'}
		            ]
	},{
		text : '偏线其他应收',
		defaults : {
			style :"text-align:center"
		},
		columns : [ 
		            {text:'偏线其他应收录入',width:95,dataIndex:'porEntry'},
		            {text:'还款偏线其他应收现金',width:95,dataIndex:'urPorCh'},
		            {text:'还款偏线其他应收银行',width:95,dataIndex:'urPorCd'}
		            ]
	},{
		text : '偏线应收冲应付',
		defaults : {
			style :"text-align:center"
		},
		columns : [ 
		            {text:'其他应付冲01应收到付运费已签收',width:95,dataIndex:'popWoDroPod'},
		            {text:'其他应付冲01应收到付运费未签收',width:95,dataIndex:'popWoDroNpod'},
		            {text:'其他应付冲02应收到付运费已签收',width:95,dataIndex:'popWoDrtPod'},
		            {text:'其他应付冲02应收到付运费未签收',width:95,dataIndex:'popWoDrtNpod'},
		            {text:'成本应付其他应付冲其他应收',width:95,dataIndex:'pocpWoOr'}
		            ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.MvrNplrStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrNplr_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.MvrNplrQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.MvrNplrQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNplr_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						//获得查询FORM
						getQueryForm : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-mvrNplr-body'
					});
		});