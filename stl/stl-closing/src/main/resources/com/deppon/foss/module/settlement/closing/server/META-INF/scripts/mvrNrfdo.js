/**
 * 声明账期model
 */
Ext.define('Foss.mvrNrfdo.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfdo.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfdo.PeriodModel',
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
closing.mvrNrfdo.queryMvrNrfdo=function(form,me){
	var m=Ext.getCmp('T_closing-mvrNrfdo_content');
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
closing.mvrNrfdo.exportMvrNrfdo = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrNrfdo_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出01到达月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = mainPane.getQueryForm().getValues();
			
			Ext.apply(params,{ 
				'mvrNrfdoVo.productCodeList':stl.convertProductCode(mainPane.getQueryForm().getForm().findField('mvrNrfdoVo.productCodeList').getValue())
			});
			
			//创建一个form
			if(!Ext.fly('exportMvrNrfdoForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNrfdoForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNrfdoExport.action'), 
				form: Ext.fly('exportMvrNrfdoForm'),
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
closing.mvrNrfdo.getComboPeriodStore = function() {
	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNrfdo.getComboProductTypeStore = function() {
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
Ext.define('Foss.closing.MvrNrfdoComboModel', {
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
Ext.define('Foss.closing.MvrNrfdoModel', {
			extend : 'Ext.data.Model',
			fields : [
			    {name:'id',							type:'string'},
				{name:'period',						type:'string'},
				{name:'productCode',				type:'string'},
				{name:'customerCode',				type:'string'},
				{name:'customerName',				type:'string'},
				{name:'origOrgCode',				type:'string'},
				{name:'origOrgName',				type:'string'},
				{name:'destOrgCode',				type:'string'},
				{name:'destOrgName',				type:'string'},
				{name:'origUnifiedCode',			type:'string'},
				{name:'destUnifiedCode',			type:'string'},
				{name:'customerType',				type:'string'},
			    {name:'unifiedSettlementType',		type:'string'},
			    {name:'contractOrgCode',			type:'string'},
			    {name:'contractOrgName',			type:'string'},
				{name:'custDroWoDestRcvoNpod',		type:'decimal'},
				{name:'custDroWoDestRcvoPod',		type:'decimal'},
				{name:'custDroWoDestRcvtNpod',		type:'decimal'},
				{name:'custDroWoDestRcvtPod',		type:'decimal'},
				{name:'claimDestoIncome',			type:'decimal'},
				{name:'claimDestoCost',				type:'decimal'},
				{name:'claimDestoWoDestRcvoPod',	type:'decimal'},
				{name:'claimDestoWoDestRcvoNpod',	type:'decimal'},
				{name:'claimDestoWoDestRcvtPod',	type:'decimal'},
				{name:'claimDestoWoDestRcvtNpod',	type:'decimal'},
				{name:'claimDestoPayApply',			type:'decimal'},
				{name:'rdDestoIncome',				type:'decimal'},
				{name:'rdDestoCost',				type:'decimal'},
				{name:'rdDestoPayApply',			type:'decimal'},
				{name:'rdDestoDestRcvoPod',			type:'decimal'},
				{name:'rdDestoWoDestRcvoNpod',		type:'decimal'},
				{name:'rdDestoDestRcvtPod',			type:'decimal'},
				{name:'rdDestoWoDestRcvtNpod',		type:'decimal'},
				{name:'cpoDestPayApply',			type:'decimal'}
				]
		})

//Store
Ext.define('Foss.closing.MvrNrfdoStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.MvrNrfdoModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNrfdo.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNrfdoVo.mvrNrfdoEntityList',
			totalProperty : 'mvrNrfdoVo.mvrNrfdoDto.count'
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
	   		var queryForm = Ext.getCmp('T_closing-mvrNrfdo_content').getQueryForm();
			if (queryForm) {
				var form = queryForm.getForm();
				var params = form.getValues();
				
				Ext.apply(params,{ 
					'mvrNrfdoVo.productCodeList':stl.convertProductCode(form.findField('mvrNrfdoVo.productCodeList').getValue())
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
Ext.define('Foss.closing.MvrNrfdoQueryForm', {
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
				name : 'mvrNrfdoVo.period',
				fieldLabel : '期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfdo.getComboPeriodStore().getAt(1)
						.get('value'),
				store : closing.mvrNrfdo.getComboPeriodStore(),*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNrfdo.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			}, {
				xtype : 'combo',
				name : 'mvrNrfdoVo.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfdo.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNrfdo.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'commoncustomerselector',
				fieldLabel : '客户信息',
				singlePeopleFlag : 'Y',
				name : 'mvrNrfdoVo.customerCode',
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfdoVo.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfdoVo.destOrgCode',
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
									closing.mvrNrfdo.queryMvrNrfdo(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.MvrNrfdoQueryGrid', {
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
				handler:closing.mvrNrfdo.exportMvrNrfdo,
				disabled:!closing.mvrNrfdo.isPermission('/stl-web/closing/mvrNrfdoExport.action'),
				hidden:!closing.mvrNrfdo.isPermission('/stl-web/closing/mvrNrfdoExport.action')
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
		}, {
			text : 'ID',
			width:10,
			dataIndex: 'id',
			hidden: true
		}, {
			text : '期间',
			width:95,
			dataIndex : 'period'
		}, {
			text : '运输性质',
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
			dataIndex : 'customerCode'
		}, {
			text : '始发部门编码',
			width:95,
			dataIndex : 'origOrgCode'
		}, {
			text : '始发部门名称',
			width:95,
			dataIndex : 'origOrgName'
		}, {
			text : '到达部门编码',
			width:95,
			dataIndex : 'destOrgCode'
		}, {
			text : '到达部门名称',
			width:95,
			dataIndex : 'destOrgName'
		}, {
		    text : '统一结算类型',
			width:110,
			dataIndex : 'unifiedSettlementType',
	        renderer: function (value, metaData, record, rowIndex, colIndex, store) {
	            if (rowIndex != store.data.length - 1) {
	                return value == 'ORIG' ? "始发统一结算" : value == 'DEST' ? "到达统一结算" : "非统一结算";
	            }
	        }
		}, {
			text : '合同部门名称',
			width:110,
			dataIndex : 'contractOrgCode'
		}, {
			text : '合同部门编码',
			width:110,
			dataIndex : 'contractOrgName'
		}]
	},
	
	 {
		text : '预收客户',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'01预收客户冲01应收到付运费未签收',
			width:95,
			dataIndex:'custDroWoDestRcvoNpod',
			width:95
		}, {
			text : '01预收客户冲02应收到付运费未签收',
			width:95,
			dataIndex : 'custDroWoDestRcvtNpod'
		}, {
			text : '01预收客户冲01应收到付运费已签收',
			width:95,
			dataIndex : 'custDroWoDestRcvoPod'
		}, {
			text : '01预收客户冲02应收到付运费已签收',
			width:95,
			dataIndex : 'custDroWoDestRcvtPod'
		} ]
	}, {
		text : '理赔',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'01理赔冲收入',
			width:95,
			dataIndex :'claimDestoIncome',
			width:95
		}, {
			text : '01理赔入成本',
			width:95,
			dataIndex : 'claimDestoCost'
		}, {
			text : '01理赔冲01到达应收已签收',
			width:95,
			dataIndex : 'claimDestoWoDestRcvoPod'
		}, {
			text : '01理赔冲02到达应收已签收',
			width:95,
			dataIndex : 'claimDestoWoDestRcvtPod'
		}, {
			text : '01理赔冲01到达应收未签收',
			width:95,
			dataIndex : 'claimDestoWoDestRcvoNpod'
		}, {
			text : '01理赔冲02到达应收未签收',
			width:95,
			dataIndex : 'claimDestoWoDestRcvtNpod'
		}, {
			text : '01理赔付款申请',
			width:95,
			dataIndex : 'claimDestoPayApply'
		}]
	},
	 {
		text : '退运费',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'01退运费冲收入',
			dataIndex:'rdDestoIncome',
			width:95
		}, {
			text : '01退运费入成本',
			width:95,
			dataIndex : 'rdDestoCost'
		}, {
			text : '01退运费付款申请',
			width:95,
			dataIndex : 'rdDestoPayApply'
		},{
			text : '01退运费冲01到达应收已签收',
			width:95,
			dataIndex : 'rdDestoDestRcvoPod'
		}, {
			text : '01退运费冲02到达应收已签收',
			width:95,
			dataIndex : 'rdDestoDestRcvtPod'
		}, {
			text : '01退运费冲01到达应收未签收',
			width:95,
			dataIndex : 'rdDestoWoDestRcvoNpod'
		}, {
			text : '01退运费冲02到达应收未签收',
			width:95,
			dataIndex : 'rdDestoWoDestRcvtNpod'
		} ]
	},   {
		text : '服务补救',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'01到达服务补救付款申请',
			dataIndex:'cpoDestPayApply',
			width:95
		}  ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.MvrNrfdoStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrNrfdo_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.MvrNrfdoQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.MvrNrfdoQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNrfdo_content',
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
						renderTo : 'T_closing-mvrNrfdo-body'
					});
		});