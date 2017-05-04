/**
 * 获取上个月期间
 */
closing.mvrNafi.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrNafi.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNafi.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNafi.PeriodModel',
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

closing.mvrNafi.queryMvrNafiByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrNafi_content');
	var grid = m.getQueryGrid();
	
	closing.mvrNafi.period = form.findField('mvrNafiVo.period').getValue();
	//closing.mvrNafi.customerCode = form.findField('mvrNafiVo.customerCode').getValue();
	closing.mvrNafi.customer = form.findField('mvrNafiVo.customerCode').getValue();
	closing.mvrNafi.customerDetail = form.findField('mvrNafiVo.customerDetail').getValue();
	closing.mvrNafi.agencyDetail = form.findField('mvrNafiVo.agencyDetail').getValue();
	
	closing.mvrNafi.orgType = form.findField('mvrNafiVo.orgType').getValue();
	closing.mvrNafi.orgCode = form.findField('mvrNafiVo.orgCode').getValue();
	
	var customerCode = "";
	if(closing.mvrNafi.customer != null && closing.mvrNafi.customer != ""){
		customerCode = closing.mvrNafi.customer;
	}else if(closing.mvrNafi.customerDetail != null && closing.mvrNafi.customerDetail != ""){
		customerCode = closing.mvrNafi.customerDetail;
	}else if(closing.mvrNafi.agencyDetail != null && closing.mvrNafi.agencyDetail != ""){
		customerCode = closing.mvrNafi.agencyDetail;
	}
	
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'mvrNafiVo.period':closing.mvrNafi.period,
				'mvrNafiVo.customerCode':customerCode,
				'mvrNafiVo.orgType':closing.mvrNafi.orgType,
				'mvrNafiVo.orgCode':closing.mvrNafi.orgCode
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

closing.mvrNafi.exportMvrNafi = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrNafi_content');
	var queryGrid = mainPane.getQueryGrid();
	
	var customerCode = "";
	if(closing.mvrNafi.customer != null && closing.mvrNafi.customer != ""){
		customerCode = closing.mvrNafi.customer;
	}else if(closing.mvrNafi.customerDetail != null && closing.mvrNafi.customerDetail != ""){
		customerCode = closing.mvrNafi.customerDetail;
	}else if(closing.mvrNafi.agencyDetail != null && closing.mvrNafi.agencyDetail != ""){
		customerCode = closing.mvrNafi.agencyDetail;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出空运往来月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = {
					'mvrNafiVo.period':closing.mvrNafi.period,
					'mvrNafiVo.customerCode':customerCode,
					'mvrNafiVo.orgType':closing.mvrNafi.orgType,
					'mvrNafiVo.orgCode':closing.mvrNafi.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportMvrNafiForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNafiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrNafiExport.action'), 
				form: Ext.fly('exportMvrNafiForm'),
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
closing.mvrNafi.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrNafiComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNafi.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNafiComboModel', {
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
Ext.define('Foss.closing.mvrNafiModel', {
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
			          {name:'uroaDestChNpod',           type:'decimal'},   
			          {name:'uroaDestCdPod',            type:'decimal'},
			          {name:'urtaDestCdNpod',           type:'decimal'},
			          {name:'apwrOthPayWoDestRcvoPod',  type:'decimal'},
			          {name:'apwrCostWoDestRcvtPod',    type:'decimal'},
			          {name:'apwrOthPayWoDestRcvtNpod', type:'decimal'},
			          {name:'airCodCdUrPod',            type:'decimal'},
			          {name:'airCodOthPayWoCodRcvPod',  type:'decimal'},
			          {name:'airDrWoDestRcvoNpod',      type:'decimal'},
			          {name:'uroaDestCdNpod',           type:'decimal'},
			          {name:'uroaDestChPod',            type:'decimal'},
			          {name:'urtaDestChNpod',           type:'decimal'},
			          {name:'urtaDestChPod',            type:'decimal'},
			          {name:'urtaDestCdPod',            type:'decimal'},
			          {name:'apwrCostWoDestRcvoPod',    type:'decimal'},
			          {name:'apwrCostWoDestRcvoNpod',   type:'decimal'},
			          {name:'apwrOthPayWoDestRcvoNpod', type:'decimal'},
			          {name:'apwrCostWoDestRcvtNpod',   type:'decimal'},
			          {name:'apwrOthPayWoDestRcvtPod',  type:'decimal'},
			          {name:'airCodPodNwo',             type:'decimal'},
			          {name:'airCodUpdNwo',             type:'decimal'},
			          {name:'airCodChUrPod',            type:'decimal'},
			          {name:'airCodPodWo',              type:'decimal'},
			          {name:'airCodUpdWo',              type:'decimal'},
			          {name:'airCodDpayWoCodRcvPod',    type:'decimal'},
			          {name:'airDrWoDestRcvoPod',       type:'decimal'},
			          {name:'airDrWoDestRcvtPod',       type:'decimal'},
			          {name:'airDrWoDestRcvtNpod',      type:'decimal'},
			          {name:'airDrWoCodRcvPod',         type:'decimal'}]
		})

//Store
Ext.define('Foss.closing.mvrNafiStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNafiModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNafi.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNafiVo.mvrNafiEntityList',
			totalProperty : 'totalCount'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
        			var searchParams;
        			var form = Ext.getCmp('T_closing-mvrNafi_content').getQueryTab().getForm();
        			closing.mvrNafi.period = form.findField('mvrNafiVo.period').getValue();
        			//closing.mvrNafi.customerCode = form.findField('mvrNafiVo.customerCode').getValue();
        			closing.mvrNafi.customer = form.findField('mvrNafiVo.customerCode').getValue();
        			closing.mvrNafi.customerDetail = form.findField('mvrNafiVo.customerDetail').getValue();
        			closing.mvrNafi.agencyDetail = form.findField('mvrNafiVo.agencyDetail').getValue();
        			closing.mvrNafi.orgType = form.findField('mvrNafiVo.orgType').getValue();
        			closing.mvrNafi.orgCode = form.findField('mvrNafiVo.orgCode').getValue();
        			var customerCode = "";
        			if(closing.mvrNafi.customer != null && closing.mvrNafi.customer != ""){
        				customerCode = closing.mvrNafi.customer;
        			}else if(closing.mvrNafi.customerDetail != null && closing.mvrNafi.customerDetail != ""){
        				customerCode = closing.mvrNafi.customerDetail;
        			}else if(closing.mvrNafi.agencyDetail != null && closing.mvrNafi.agencyDetail != ""){
        				customerCode = closing.mvrNafi.agencyDetail;
        			}
    				//获取查询条件
    				searchParams = {
    						'mvrNafiVo.period':closing.mvrNafi.period,
    						'mvrNafiVo.customerCode':customerCode,
    						'mvrNafiVo.orgType':closing.mvrNafi.orgType,
    						'mvrNafiVo.orgCode':closing.mvrNafi.orgCode
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
		
/*// 定义查询Form
Ext.define('Foss.closing.mvrNafiQueryForm', {
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
				name : 'mvrNafiVo.period',
		    	fieldLabel:'查询期间',
		    	editable:false,
				store:Ext.create('Foss.common.PeriodStore'),
			    queryMode: 'local', 	
				displayField:'name',
				valueField:'value',
				value:closing.mvrNafi.getLastMonthPeriod(),
		    	columnWidth:.33
		    	queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNafi.PeriodStore'),
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
				name : 'mvrNafiVo.customerCode',
				columnWidth : .33
			}{
		    	xtype:'commoncustomerselector',
		    	name:'mvrNafiVo.customerCode',
		    	fieldLabel:'客户信息',
		    	singlePeopleFlag : 'Y',
	    		listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
		 	},{
				xtype : 'commonairlinesselector',
				fieldLabel : '航空公司',
				name : 'mvrNafiVo.customerDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			}, {
				xtype : 'commonallagentselector',
				fieldLabel : '空运代理',
				name : 'mvrNafiVo.agencyDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
				xtype : 'combo',
				name : 'mvrNafiVo.orgType',
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
				name : 'mvrNafiVo.orgCode',
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
									closing.mvrNafi.querymvrNafiByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}			
						}]
			}]
		})*/

//定义查询Form
Ext.define('Foss.closing.mvrNafiQueryForm', {
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
				name : 'mvrNafiVo.period',
				fieldLabel:'查询期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNafi.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .3
			},{
		    	xtype: 'combobox',
				fieldLabel:'客户类型',
				name:'mvrNafiVo.customerType',
				labelWidth:85,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
						[closing.mvrNafi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
						 closing.mvrNafi.SETTLEMENT__CUSTOMER_TYPE__AIR,
						 closing.mvrNafi.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:closing.mvrNafi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
		    	columnWidth:.3,
		    	listeners:{
        		    	'change':function(th,newValue,oldValue){
						//获取表单等控件
						var form,//表单
							customerCode,
							customerDetail,
							agencyDetial;
						//获取表单	
						form= this.up('form').getForm();
						//获取下面组件
						customerCode = form.findField('mvrNafiVo.customerCode');
						customerDetail = form.findField('mvrNafiVo.customerDetail');
						agencyDetail = form.findField('mvrNafiVo.agencyDetail');
						if( newValue=='LC'){
							customerCode.show();
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetail.hide();
							agencyDetail.setValue("");
						}else if(newValue=='A'){
							customerCode.hide();
							customerCode.setValue("");
							customerDetail.show();
							agencyDetail.hide();
							agencyDetail.setValue("");
						}else if(newValue=='AA'){
							customerCode.hide();
							customerCode.setValue("");
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetail.show();
						}
					}
				}
		    },{
		    	xtype:'commoncustomerselector',
		    	name:'mvrNafiVo.customerCode',
		    	fieldLabel:'客户信息',
		    	singlePeopleFlag : 'Y',
	    		listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
		 	},{
				xtype : 'commonairlinesselector',
				fieldLabel : '航空公司',
				name : 'mvrNafiVo.customerDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			}, {
				xtype : 'commonallagentselector',
				fieldLabel : '空运代理',
				name : 'mvrNafiVo.agencyDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			},{
				xtype : 'combo',
				name : 'mvrNafiVo.orgType',
				fieldLabel:'始发/到达',
		    	store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .3
			},{
				xtype : 'dynamicorgcombselector',
				name : 'mvrNafiVo.orgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
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
							handler :function(){
								this.up('form').getForm().reset();
							}
						}, {
							xtype : 'container',
							border : false,
							html : '&nbsp;',
							columnWidth : .7
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.mvrNafi.queryMvrNafiByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrNafiQueryGrid', {
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
				handler:closing.mvrNafi.exportMvrNafi,
				disabled:!closing.mvrNafi.isPermission('/stl-web/closing/mvrNafiExport.action'),
				hidden:!closing.mvrNafi.isPermission('/stl-web/closing/mvrNafiExport.action')
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
		}, {
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
		text : '空运应付冲应收',
		columns : [ {
			text : '应付到达代理成本冲01应收到付运费已签收',
			width:150,
			dataIndex : 'apwrCostWoDestRcvoPod'
		}, {
			text : '应付到达代理成本冲01应收到付运费未签收',
			width:150,
			dataIndex : 'apwrCostWoDestRcvoNpod'
		},{
			text : '其他应付冲01应收到付运费已签收',
			width:150,
			dataIndex : 'apwrOthPayWoDestRcvoPod'
		}, {
			text : '其他应付冲01应收到付运费未签收',
			width:150,
			dataIndex : 'apwrOthPayWoDestRcvoNpod'
		}, {
			text : '应付到达代理成本冲02应收到付运费已签收',
			width:150,
			dataIndex : 'apwrCostWoDestRcvtPod'
		}, {
			text : '应付到达代理成本冲02应收到付运费未签收',
			width:150,
			dataIndex : 'apwrCostWoDestRcvtNpod'
		},{
			text : '其他应付冲02应收到付运费已签收',
			width:150,
			dataIndex : 'apwrOthPayWoDestRcvtPod'
		}, {
			text : '其他应付冲02应收到付运费未签收',
			width:150,
			dataIndex : 'apwrOthPayWoDestRcvtNpod'
		}]
	},{
		text : '空运代收货款',
		columns : [ {
			text : '空运签收时未核销代收货款',
			width:150,
			dataIndex : 'airCodPodNwo'
		}, {
			text : '空运反签收时未核销代收货款',
			width:150,
			dataIndex : 'airCodUpdNwo'
		}, {
			text : '空运还款代收货款现金已签收',
			width:150,
			dataIndex : 'airCodChUrPod'
		}, {
			text : '空运还款代收货款银行已签收',
			width:150,
			dataIndex : 'airCodCdUrPod'
		},{
			text : '空运签收时已核销代收货款',
			width:150,
			dataIndex : 'airCodPodWo'
		}, {
			text : '空运反签收时已核销代收货款',
			width:150,
			dataIndex : 'airCodUpdWo'
		}, {
			text : '空运到达代理应付冲应收代收货款已签收',
			width:150,
			dataIndex : 'airCodDpayWoCodRcvPod'
		}, {
			text : '空运其他应付冲应收代收货款已签收',
			width:150,
			dataIndex : 'airCodOthPayWoCodRcvPod'
		}]
	},{
		text : '预收空运代理',
		columns : [ {
			text : '预收空运代理冲02应收到付运费已签收',
			width:150,
			dataIndex : 'airDrWoDestRcvtPod'
		}, {
			text : '预收空运代理冲02应收到付运费未签收',
			width:150,
			dataIndex : 'airDrWoDestRcvtNpod'
		}, {
			text : '预收空运代理冲01应收到付运费已签收',
			width:150,
			dataIndex : 'airDrWoDestRcvoPod'
		}, {
			text : '预收空运代理冲01应收到付运费未签收',
			width:150,
			dataIndex : 'airDrWoDestRcvoNpod'
		}, {
			text : '预收空运代理冲应收代收货款已签收',
			width:150,
			dataIndex : 'airDrWoCodRcvPod'
		}]
	},{
		text : '还款运单总运费（到付）【01】',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'uroaDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'uroaDestCdNpod'
		},{
			text : '还款现金已签收',
			width:150,
			dataIndex : 'uroaDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'uroaDestCdPod'
		}]
	},{
		text : '还款运单总运费（到付）【02】',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'urtaDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'urtaDestCdNpod'
		},{
			text : '还款现金已签收',
			width:150,
			dataIndex : 'urtaDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'urtaDestCdPod'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrNafiStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrNafi_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNafiQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNafiQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNafi_content',
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
						renderTo : 'T_closing-mvrNafi-body'
					});
		});