/**
 * 汇总信息Model全局变量
 */
closing.mvrRfd.mvrRfdTotalRecord = null;

/**
 * 汇总信息Model的ID全局常量
 */
closing.mvrRfd.mvrRfdTotalRecord_ID = 'closing_mvrRfd_mvrRfdSum_ID';


/**
 * 声明账期model
 */
Ext.define('Foss.mvrRfd.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrRfd.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrRfd.PeriodModel',
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
closing.mvrRfd.queryMvrRfd=function(form,me){
	var m=Ext.getCmp('T_closing-mvrRfd_content');
	// 清空汇总数据ID
	closing.mvrRfd.mvrRfdTotalRecord.data.id = null;
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
							//var result =   Ext.decode(operation.response.responseText); 
							if(result.mvrRfdVo.mvrRfdDto.count > 0){
								// 把汇总Dto赋值到汇总Mode上
								closing.mvrRfd.mvrRfdTotalRecord.data = result.mvrRfdVo.mvrRfdDto;
								// 设置汇总标识信息
								closing.mvrRfd.mvrRfdTotalRecord.data.id = closing.mvrRfd.mvrRfdTotalRecord_ID;
								closing.mvrRfd.mvrRfdTotalRecord.data.period = '&nbsp;&nbsp;&nbsp;&nbsp;<b>汇总</b>';
								closing.mvrRfd.mvrRfdTotalRecord.data.productCode = '<b>总条数：</b>' + closing.mvrRfd.mvrRfdTotalRecord.data.count;
								closing.mvrRfd.mvrRfdTotalRecord.data.destOrgName = '&nbsp;&nbsp;&nbsp;&nbsp;<b>总金额：</b>';
								// 把汇总mode插到store的最后一行
								store.insert(store.count(),closing.mvrRfd.mvrRfdTotalRecord);
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
closing.mvrRfd.exportMvrRfd = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrRfd_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出专线到达月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = mainPane.getQueryForm().getValues();
			
			Ext.apply(params,{ 
				'mvrRfdVo.productCodeList':stl.convertProductCode(mainPane.getQueryForm().getForm().findField('mvrRfdVo.productCodeList').getValue())
			});
			
			//创建一个form
			if(!Ext.fly('exportMvrRfdForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrRfdForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrRfdExport.action'), 
				form: Ext.fly('exportMvrRfdForm'),
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
closing.mvrRfd.getComboPeriodStore = function() {
	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrRfd.getComboProductTypeStore = function() {
	var productStore = Ext.create('Foss.pkp.ProductStore');
	
	for(var i=0;i<productStore.data.length;i++){
		var record = productStore.data.items[i];
		if(record.get('code') == 'AF' || record.get('code') == 'PLF'){ //去掉精准空运，汽运偏线
			productStore.remove(record);
		}
	}
	
	
	return productStore;
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.MvrRfdComboModel', {
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
Ext.define('Foss.closing.MvrRfdModel', {
			extend : 'Ext.data.Model',
			fields : [
				{	  
					/** ID */
					name:	'id',            
					type:  'string'
				},{	
				/** 期间 */
				name:	'period',        
					type:  'string'             
				},{	
				/** 业务类型 */
				name:		'productCode',   
					type:	 'string'              
				},{	
				/** 客户编码 */
				name:		'customerCode',  
					type:  'string'             
				},{	
				/** 客户名称 */
				name:		'customerName' , 
				  type:  'string'           
				},{	
				/** 始发部门编码 */      
				name:	'origOrgCode',   
					type:  'string'             
				},{	
				/** 始发部门名称 */          
				name:		'origOrgName',   
				  type:  'string'           
				},{	
				/** 到达部门编码 */  
				name:		'destOrgCode',   
				  type: 'string'            
				},{	
				/** 到达部门名称 */ 
				name:	'destOrgName',   
					type: 'string'              
				},{	
				/** 统计时间 */       
				name:	'atisticalTime',
				  type : 'date',                             
					convert : stl.longToDateConvert 
				}, {	/** 还款运单总运费（到付）-还款现金未签收. */
				    name:'urDestChNpod',
				    type : 'decimal'
				},{    /** 还款运单总运费（到付）-还款银行未签收. */
				    name:'urDestCdNpod'                                                                                  
				,
				  type : 'decimal'
				},{    /** 还款运单总运费（到付）-还款现金已签收. */
				    name:'urDestChPod'                                                                                  
				,
				  type : 'decimal'
				},{     /** 还款运单总运费（到付）-还款银行已签收. */
				    name:'urDestCdPod'                                                                                  
				,
				  type : 'decimal'
				},{         /** 理赔-理赔冲收入. */
				    name:'claimDestWoIncome'                                                                                   
				,
				  type : 'decimal'
				},{         /** 理赔-理赔入成本. */
				    name:'claimDestCost'                                                                                  
				,
				  type : 'decimal'
				},{     /** 理赔-理赔付款申请. */
				    name:'claimDestPayApply'                                                                                   
				,
				  type : 'decimal'
				},{    /** 理赔-理赔冲到达应收已签收. */
				    name:'claimWoDestRcvPod'                                                                                  
				,
				  type : 'decimal'
				},{      /** 理赔-理赔冲到达应收未签收. */
				    name:'claimWoDestRcvNpod'                                                                                  
				,
				  type : 'decimal'
				},{        /** 代收货款-还款代收货款现金未签收. */
				    name:'codUrChNpod'                                                                                  
				,
				  type : 'decimal'
				},{   /** 代收货款-还款代收货款银行未签收. */
				    name:'codUrCdNpod'                                                                                  
				,
				  type : 'decimal'
				},{     /** 退运费-退运费冲收入. */
				    name:'rdDestWoIncome'                                                                                   
				,
				  type : 'decimal'
				},{    /** 退运费-退运费入成本. */
				    name:'rdDestCost'                                                                                  
				,
				  type : 'decimal'
				},{    /** 退运费-退运费付款申请. */
				    name:'rdDestPayApply'                                                                                   
				,
				  type : 'decimal'
				},{    /** 服务补救-到达服务补救付款申请. */
				    name:'cnDestPayApply'                                                                                  
				,
				  type : 'decimal'
				},
				/** 预收客户冲应收到付运费未签收 **/
				{
				name:'custDrDestRcvNpod',
				type:'decimal'
				},
				/**预收客户冲应收到付运费已签收**/
				{
				name:'custDrDestRcvPod',
				type:'decimal'
				},
				/**退运费冲到达应收已签收**/
				{
				name:'rdWoDestRcvPod',
				type:'decimal'
				},
				/**退运费冲到达应收未签收**/
				{
				name:'rdWoDestRcvNpod',
				type:'decimal'
				}
				]
		})

//Store
Ext.define('Foss.closing.MvrRfdStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.MvrRfdModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrRfd.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrRfdVo.mvrRfdEntityList',
			totalProperty : 'mvrRfdVo.mvrRfdDto.count'
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
	   		var queryForm = Ext.getCmp('T_closing-mvrRfd_content').getQueryForm();
			if (queryForm) {
				var form = queryForm.getForm();
				var params = form.getValues();
				
				Ext.apply(params,{ 
					'mvrRfdVo.productCodeList':stl.convertProductCode(form.findField('mvrRfdVo.productCodeList').getValue())
				});
				
				Ext.apply(operation, {
					params:params
	   			});				
			}
	   			
	   		},
	   		'load': function(store, operation, eOpts){
				//加载后把已有汇总信息插入到store
	   			if(closing.mvrRfd.mvrRfdTotalRecord.data.id == closing.mvrRfd.mvrRfdTotalRecord_ID){
					store.insert(store.count(),closing.mvrRfd.mvrRfdTotalRecord);
	   			}	   			
				
	   		}
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 定义查询Form
Ext.define('Foss.closing.MvrRfdQueryForm', {
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
				name : 'mvrRfdVo.period',
				fieldLabel : '期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrRfd.getComboPeriodStore().getAt(1)
						.get('value'),
				store : closing.mvrRfd.getComboPeriodStore(),*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrRfd.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			}, {
				xtype : 'combo',
				name : 'mvrRfdVo.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrRfd.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrRfd.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'commoncustomerselector',
				fieldLabel : '客户信息',
				singlePeopleFlag : 'Y',
				name : 'mvrRfdVo.customerCode',
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrRfdVo.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrRfdVo.destOrgCode',
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
									closing.mvrRfd.queryMvrRfd(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.MvrRfdQueryGrid', {
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
	            if(record.data.id == closing.mvrRfd.mvrRfdTotalRecord_ID){ // 汇总的样式
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
				handler:closing.mvrRfd.exportMvrRfd,
				disabled:!closing.mvrRfd.isPermission('/stl-web/closing/mvrRfdExport.action'),
				hidden:!closing.mvrRfd.isPermission('/stl-web/closing/mvrRfdExport.action')
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
			text : '期<br/>间',
			width:95,
			dataIndex : 'period'
		}, {
			text : '运<br/>输<br/>性<br/>质',
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
			text : '客<br/>户<br/>编<br/>码',
			width:95,
			dataIndex : 'customerCode',
		}, {
			text : '客<br/>户<br/>名<br/>称',
			width:95,
			dataIndex : 'customerName'
		}, {
			text : '始发<br/>部门<br/>编码',
			width:95,
			dataIndex : 'origOrgCode'
		}, {
			text : '始发<br/>部门<br/>名称',
			width:95,
			dataIndex : 'origOrgName'
		}, {
			text : '到达<br/>部门<br/>编码',
			width:95,
			dataIndex : 'destOrgCode'
		}, {
			text : '到达<br/>部门<br/>名称',
			width:95,
			dataIndex : 'destOrgName'
		} ]
	},
	
	 {
		text : '还款运单总运费（到付）',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'还款现金未签收',
			width:95,
			dataIndex:'urDestChNpod',
			width:95
		}, {
			text : '还款银行未签收',
			width:95,
			dataIndex : 'urDestCdNpod',
		}, {
			text : '还款现金已签收',
			width:95,
			dataIndex : 'urDestChPod'
		}, {
			text : '还款银行已签收',
			width:95,
			dataIndex : 'urDestCdPod'
		} ]
	}, {
		text : '理赔',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'理赔冲收入',
			width:95,
			dataIndex :'claimDestWoIncome',
			width:95
		}, {
			text : '理赔入成本',
			width:95,
			dataIndex : 'claimDestCost',
		}, {
			text : '理赔冲到达应收已签收',
			width:95,
			dataIndex : 'claimWoDestRcvPod'
		}, {
			text : '理赔冲到达应收未签收',
			width:95,
			dataIndex : 'claimWoDestRcvNpod'
		}, {
			text : '理赔付款申请',
			width:95,
			dataIndex : 'claimDestPayApply'
		} ]
	},   {
		text : '代收货款',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'还款代收货款现金未签收',
			width:95,
			dataIndex :'codUrChNpod',
		}, {
			text : '还款代收货款银行未签收',
			width:95,
			dataIndex : 'codUrCdNpod',
		} ]
	},{
		text : '营业部预收客户',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'预收客户冲应收到付运费未签收',
			width:95,
			dataIndex :'custDrDestRcvNpod',
		}, {
			text : '预收客户冲应收到付运费已签收',
			width:95,
			dataIndex : 'custDrDestRcvPod',
		} ]
	}, 
	 {
		text : '退运费',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'退运费冲收入',
			dataIndex:'rdDestWoIncome',
			width:95
		}, {
			text : '退运费入成本',
			width:95,
			dataIndex : 'rdDestCost',
		}, {
			text : '退运费付款申请',
			width:95,
			dataIndex : 'rdDestPayApply',		
		},{
			text : '退运费冲到达应收已签收',
			width:95,
			dataIndex : 'rdWoDestRcvPod'
		}, {
			text : '退运费冲到达应收未签收',
			width:95,
			dataIndex : 'rdWoDestRcvNpod'
		} ]
	},   {
		text : '服务补救',
		defaults : {
			style :"text-align:center"
		},
		columns : [ {
			text:'到达服务补救付款申请',
			dataIndex:'cnDestPayApply',
			width:95
		}  ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.MvrRfdStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			// 汇总model
			closing.mvrRfd.mvrRfdTotalRecord = Ext.create('Foss.closing.MvrRfdComboModel');
			
			if (Ext.getCmp('T_closing-mvrRfd_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.MvrRfdQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.MvrRfdQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrRfd_content',
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
						renderTo : 'T_closing-mvrRfd-body'
					});
		});