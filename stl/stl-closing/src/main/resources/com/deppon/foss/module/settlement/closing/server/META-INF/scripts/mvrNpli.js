/**
 * 获取上个月期间
 */
closing.mvrNpli.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrNpli.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNpli.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNpli.PeriodModel',
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

closing.mvrNpli.querymvrNpliByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrNpli_content');
	var grid = m.getQueryGrid();
	
	closing.mvrNpli.period = form.findField('mvrNpliVo.period').getValue();
	closing.mvrNpli.customerCode = form.findField('mvrNpliVo.customerCode').getValue();
	closing.mvrNpli.orgType = form.findField('mvrNpliVo.orgType').getValue();
	closing.mvrNpli.orgCode = form.findField('mvrNpliVo.orgCode').getValue();
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'mvrNpliVo.period':closing.mvrNpli.period,
				'mvrNpliVo.customerCode':closing.mvrNpli.customerCode,
				'mvrNpliVo.orgType':closing.mvrNpli.orgType,
				'mvrNpliVo.orgCode':closing.mvrNpli.orgCode
			}
		});	
	});
	//设置该按钮灰掉
	me.disable(false);
	//30秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 30000);
	grid.store.loadPage(1,{
		callback: function(records, operation, success) {
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

closing.mvrNpli.exportMvrNpli = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrNpli_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发偏线往来月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = {
					'mvrNpliVo.period':closing.mvrNpli.period,
					'mvrNpliVo.customerCode':closing.mvrNpli.customerCode,
					'mvrNpliVo.orgType':closing.mvrNpli.orgType,
					'mvrNpliVo.orgCode':closing.mvrNpli.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportMvrNpliForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNpliForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNpliExport.action'), 
				form: Ext.fly('exportMvrNpliForm'),
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
closing.mvrNpli.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrNpliComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNpli.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNpliComboModel', {
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

// 空运月报表数据模型
Ext.define('Foss.closing.mvrNpliModel', {
			extend : 'Ext.data.Model',
			fields : [
			          {name:'id',                   type:'string'},
			          {name:'period',               type:	'string'},                                           
			          {name:'productCode',          type:	'string'},                            
			          {name:'customerCode',         type:	'string'},                             
			          {name:'customerName',         type:	'string'},                             
			          {name:'orgCode',              type:	'string'},                        
			          {name:'orgName',              type:	'string'},                        
			          {name:'orgType',              type:	'string'},                        
			          {name:'orgUnifiedCode',       type:	'string'},                                                                    
			          {name:'customerType',         type:	'string'},                             
			          {name:'invoiceMark',          type:	'string'},                            
			          {name:'uropDestChNpod',       type:	'decimal'},                           
			          {name:'uropDestChPod',        type:	'decimal'},                          
			          {name:'urtpDestCdNpod',       type:	'decimal'},    
			          {name:'urtpDestCdPod',        type:	'decimal'},    
			          {name:'plCostWoDestRcvtNpod', type:	'decimal'},    
			          {name:'plDrWoDestRcvtPod',    type:	'decimal'},    
			          {name:'uropDestCdNpod',       type:	'decimal'},    
			          {name:'uropDestCdPod',        type:	'decimal'},    
			          {name:'urtpDestChNpod',       type:	'decimal'},    
			          {name:'urtpDestChPod',        type:	'decimal'},    
			          {name:'plCostWoDestRcvoPod',  type:	'decimal'},    
			          {name:'plCostWoDestRcvoNpod', type:	'decimal'},    
			          {name:'plCostWoDestRcvtPod',  type:	'decimal'},    
			          {name:'plDrWoDestRcvoPod',    type:	'decimal'},    
			          {name:'plDrWoDestRcvoNpod',   type:	'decimal'},    
			          {name:'plDrWoDestRcvtNpod',   type:	'decimal'},
			          {name:'popWoDroPod',   type:	'decimal'},
			          {name:'popWoDroNpod',   type:	'decimal'},
			          {name:'popWoDrtPod',   type:	'decimal'},
			          {name:'popWoDrtNpod',   type:	'decimal'}]
		})

//Store
Ext.define('Foss.closing.mvrNpliStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNpliModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNpli.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNpliVo.mvrNpliEntityList',
			totalProperty : 'totalCount'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
        			var searchParams;
        			var form = Ext.getCmp('T_closing-mvrNpli_content').getQueryTab().getForm();
        			closing.mvrNpli.period = form.findField('mvrNpliVo.period').getValue();
        			closing.mvrNpli.customerCode = form.findField('mvrNpliVo.customerCode').getValue();
        			closing.mvrNpli.orgType = form.findField('mvrNpliVo.orgType').getValue();
        			closing.mvrNpli.orgCode = form.findField('mvrNpliVo.orgCode').getValue();
    				//获取查询条件
    				searchParams = {
    						'mvrNpliVo.period':closing.mvrNpli.period,
    						'mvrNpliVo.customerCode':closing.mvrNpli.customerCode,
    						'mvrNpliVo.orgType':closing.mvrNpli.orgType,
    						'mvrNpliVo.orgCode':closing.mvrNpli.orgCode
    				}
    	   		    Ext.apply(me.submitParams, {
    		          "limit":operation.limit,
    		          "page":operation.page,
    		          "start":operation.start
    		        }); 
    	   			Ext.apply(operation, {
    	   				params : searchParams 
    	   			});
    	   		} 
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 定义查询Form
Ext.define('Foss.closing.mvrNpliQueryForm', {
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
				xtype:'combobox',
				name : 'mvrNpliVo.period',
		    	fieldLabel:'查询期间',
		    	/*editable:false,
				store:Ext.create('Foss.common.PeriodStore'),
			    queryMode: 'local', 	
				displayField:'name',
				valueField:'value',
				value:closing.mvrNpli.getLastMonthPeriod(),
		    	columnWidth:.33*/
		    	queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNpli.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.04
			},{
				xtype : 'commonvehagencycompselector',
				fieldLabel : '偏线代理公司',
				name : 'mvrNpliVo.customerCode',
				columnWidth : .33
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
				xtype : 'combo',
				name : 'mvrNpliVo.orgType',
		    	fieldLabel:'始发/到达',
		    	store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .33
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.04
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNpliVo.orgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
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
							columnWidth : .5
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.mvrNpli.querymvrNpliByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}			
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrNpliQueryGrid', {
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
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrNpli.exportMvrNpli,
				disabled:!closing.mvrNpli.isPermission('/stl-web/closing/mvrNpliExport.action'),
				hidden:!closing.mvrNpli.isPermission('/stl-web/closing/mvrNpliExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		columns : [ {
			text:'序号',
			hidden:true,
			xtype:'rownumberer',
			width:40
		},{
			text : 'ID',
			hidden:true,
			dataIndex : 'id'
		}, {
			text : '期间',
			dataIndex : 'period'
		}, {
			text : '发票标记',
			dataIndex : 'invoiceMark',
			renderer:function(value){
				if(value=='INVOICE_TYPE_01')
				{
					return '01-运输专票11%';
				}else if(value=='INVOICE_TYPE_02')
				{
					return '02-非运输专票';
				}
    			return value;
    		}
		}/*, {
			text : '产品类型',
			dataIndex : 'productCode',
			renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);

			}
		}*/, {
			text : '客户编码',
			dataIndex : 'customerCode'
		}, {
			text : '客户名称',
			dataIndex : 'customerName'
		}, {
			text : '部门类型',
			dataIndex : 'orgType',
			/*renderer:function(value){
    			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'VOUCHER__ORG_TYPE');
    			return displayField;
    		}*/
			renderer:function(value){
    			var display = null;
    			if(!Ext.isEmpty(value) &&value=='D'){
    				return '到达';
    			}else if(!Ext.isEmpty(value) &&value=='O'){
    				return '始发';
    			}
    			return value;
    		}
		}, {
			text : '部门编码',
			dataIndex : 'orgCode'
		}, {
			text : '部门名称',
			dataIndex : 'orgName'
		}]
	},{
		text : '偏线代理成本',
		columns : [ {
			text : '应付偏线代理成本冲01应收到付运费已签收',
			width:150,
			dataIndex : 'plCostWoDestRcvoPod'
		}, {
			text : '应付偏线代理成本冲02应收到付运费已签收',
			width:150,
			dataIndex : 'plCostWoDestRcvtPod'
		},{
			text : '应付偏线代理成本冲01应收到付运费未签收',
			width:150,
			dataIndex : 'plCostWoDestRcvoNpod'
		}, {
			text : '应付偏线代理成本冲02应收到付运费未签收',
			width:150,
			dataIndex : 'plCostWoDestRcvtNpod'
		}]
	},{
		text : '还款运单总运费（到付）【01】',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'uropDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'uropDestCdNpod'
		}, {
			text : '还款现金已签收',
			width:150,
			dataIndex : 'uropDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'uropDestCdPod'
		}]
	},{
		text : '还款运单总运费（到付）【02】',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'urtpDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'urtpDestCdNpod'
		}, {
			text : '还款现金已签收',
			width:150,
			dataIndex : 'urtpDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'urtpDestCdPod'
		}]
	},{
		text : '预收偏线代理',
		columns : [ {
			text : '预收偏线代理冲01应收到付运费已签收',
			width:150,
			dataIndex : 'plDrWoDestRcvoPod'
		}, {
			text : '预收偏线代理冲01应收到付运费未签收',
			width:150,
			dataIndex : 'plDrWoDestRcvoNpod'
		},{
			text : '预收偏线代理冲02应收到付运费已签收',
			width:150,
			dataIndex : 'plDrWoDestRcvtPod'
		}, {
			text : '预收偏线代理冲02应收到付运费未签收',
			width:150,
			dataIndex : 'plDrWoDestRcvtNpod'
		}]
	},{
		text : '偏线应收冲应付',
		columns : [ {
			text : '其他应付冲01应收到付运费已签收',
			width:150,
			dataIndex : 'popWoDroPod'
		}, {
			text : '其他应付冲01应收到付运费未签收',
			width:150,
			dataIndex : 'popWoDroNpod'
		},{
			text : '其他应付冲02应收到付运费已签收',
			width:150,
			dataIndex : 'popWoDrtPod'
		}, {
			text : '其他应付冲02应收到付运费未签收',
			width:150,
			dataIndex : 'popWoDrtNpod'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrNpliStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrNpli_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNpliQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNpliQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNpli_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						getQueryTab : function() {
							return queryForm;
						},
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-mvrNpli-body'
					});
		});