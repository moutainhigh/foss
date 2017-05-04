/**
 * 始发专线到达往来月报表（特殊）
 * 获取上个月期间
 */
closing.mvrSnrfi.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrSnrfi.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrSnrfi.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrSnrfi.PeriodModel',
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

closing.mvrSnrfi.queryMvrSnrfiByConditions = function(me){
	var m =Ext.getCmp('T_closing-mvrSnrfi_content');
	var grid = m.getQueryGrid();
	var form = m.getQueryTab();
	
	var params = form.getForm().getValues();
	Ext.apply(params,{
		'mvrNrfiDto.customerCode':form.getCustomerCode()
	});
	
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :params
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

closing.mvrSnrfi.exportMvrSnrfi = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrSnrfi_content');
	var grid = mainPane.getQueryGrid();
	var form = mainPane.getQueryTab();
	
	var params = form.getForm().getValues();
	Ext.apply(params,{
		'mvrNrfiDto.customerCode':form.getCustomerCode()
	});
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发专线往来月报表吗?',function(btn,text){
		if('yes' == btn){
			
			//创建一个form
			if(!Ext.fly('exportMvrSnrfiForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrSnrfiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrSnrfi.action'), 
				form: Ext.fly('exportMvrSnrfiForm'),
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
closing.mvrSnrfi.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrSnrfiComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrSnrfi.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrSnrfiComboModel', {
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
Ext.define('Foss.closing.mvrSnrfiModel', {
			extend : 'Ext.data.Model',
			fields : [
			          {name:'id',                   type:'string'},
			          {name:'period',       type:'string'},
			          {name:'invoiceMark',       type:'string'},
			          {name:'orgType',       type:'string'},
			          {name:'orgCode',       type:'string'},
			          {name:'orgName',       type:'string'},
			          {name:'deoCh',       type:'decimal'},
			          {name:'deoCd',       type:'decimal'},
			          {name:'uroOrigChNpod',       type:'decimal'},
			          {name:'uroOrigCdNpod',       type:'decimal'},
			          {name:'uroOrigChPod',       type:'decimal'},
			          {name:'uroOrigCdPod',       type:'decimal'},
			          {name:'uroDestChNpod',       type:'decimal'},
			          {name:'uroDestCdNpod',       type:'decimal'},
			          {name:'uroDestChPod',       type:'decimal'},
			          {name:'uroDestCdPod',       type:'decimal'},
			          {name:'urtDestChNpod',       type:'decimal'},
			          {name:'urtDestCdNpod',       type:'decimal'},
			          {name:'urtDestChPod',       type:'decimal'},
			          {name:'urtDestCdPod',       type:'decimal'},
			          {name:'claimOrigoWoOrigRcvtPod',       type:'decimal'},
			          {name:'claimOrigtOrigRcvoPod',       type:'decimal'},
			          {name:'claimOrigoWoOrigRcvtNpod',       type:'decimal'},
			          {name:'claimOrigtWoOrigRcvoNpod',       type:'decimal'},
			          {name:'claimOrigoPayApply',       type:'decimal'},
			          {name:'claimDestoIncome',       type:'decimal'},
			          {name:'claimDesttIncome',       type:'decimal'},
			          {name:'claimDestoPayApply',       type:'decimal'},
			          {name:'claimDestoWoDestRcvoPod',       type:'decimal'},
			          {name:'claimDestoWoDestRcvtPod',       type:'decimal'},
			          {name:'claimDesttWoDestRcvoPod',       type:'decimal'},
			          {name:'claimDesttWoDestRcvtPod',       type:'decimal'},
			          {name:'claimDestoWoDestRcvoNpod',       type:'decimal'},
			          {name:'claimDesttWoDestRcvoNpod',       type:'decimal'},
			          {name:'claimDestoWoDestRcvtNpod',       type:'decimal'},
			          {name:'claimDesttWoDestRcvtNpod',       type:'decimal'},
			          {name:'custDrOch',       type:'decimal'},
			          {name:'custDrOcd',       type:'decimal'},
			          {name:'custDroWoDestRcvoNpod',       type:'decimal'},
			          {name:'custDroWoDestRcvtNpod',       type:'decimal'},
			          {name:'custDrtWoDestRcvoNpod',       type:'decimal'},
			          {name:'custDrtWoDestRcvtNpod',       type:'decimal'},
			          {name:'custDroWoDestRcvoPod',       type:'decimal'},
			          {name:'custDroWoDestRcvtPod',       type:'decimal'},
			          {name:'custDrtWoDestRcvoPod',       type:'decimal'},
			          {name:'custDrtWoDestRcvtPod',       type:'decimal'},
			          {name:'custDroWoOrigRcvtNpod',       type:'decimal'},
			          {name:'custDrtWoOrigRcvoNpod',       type:'decimal'},
			          {name:'custDroWoOrigRcvtPod',       type:'decimal'},
			          {name:'custDrtWoOrigRcvoPod',       type:'decimal'},
			          {name:'custDroPayApply',       type:'decimal'},
			          {name:'orChPbio',       type:'decimal'},
			          {name:'orCdPbio',       type:'decimal'},
			          {name:'orChUrRcvo',       type:'decimal'},
			          {name:'orCdUrRcvo',       type:'decimal'},
			          {name:'orClaimPayoWoRcvt',       type:'decimal'},
			          {name:'orClaimPaytWoRcvo',       type:'decimal'},
			          {name:'orCustDroWoRcvt',       type:'decimal'},
			          {name:'orCustDrtWoRcvo',       type:'decimal'},
			          {name:'codUrChNpod',       type:'decimal'},
			          {name:'codUrCdNpod',       type:'decimal'},
			          {name:'codPayWoDestRcvoPod',       type:'decimal'},
			          {name:'codPayWoDestRcvoNpod',       type:'decimal'},
			          {name:'codPayWoOrigRcvoPod',       type:'decimal'},
			          {name:'codPayWoOrigRcvoNpod',       type:'decimal'},
			          {name:'codPayWoDestRcvtPod',       type:'decimal'},
			          {name:'codPayWoDestRcvtNpod',       type:'decimal'},
			          {name:'codPayWoOrigRcvtPod',       type:'decimal'},
			          {name:'codPayWoOrigRcvtNpod',       type:'decimal'},
			          {name:'codPayWoOthRcvo',       type:'decimal'},
			          {name:'codPayWoOthRcvt',       type:'decimal'},
			          {name:'rdOrigoPayApply',       type:'decimal'},
			          {name:'rdOrigoWoOrigRcvtPod',       type:'decimal'},
			          {name:'rdOrigtWoOrigRcvoPod',       type:'decimal'},
			          {name:'rdOrigoWoOrigRcvtNpod',       type:'decimal'},
			          {name:'rdOrigtWoOrigRcvoNpod',       type:'decimal'},
			          {name:'rdDestoIncome',       type:'decimal'},
			          {name:'rdDesttIncome',       type:'decimal'},
			          {name:'rdDestoPayApply',       type:'decimal'},
			          {name:'rdDestoDestRcvoPod',       type:'decimal'},
			          {name:'rdDesttWoDestRcvoPod',       type:'decimal'},
			          {name:'rdDestoDestRcvtPod',       type:'decimal'},
			          {name:'rdDesttWoDestRcvtPod',       type:'decimal'},
			          {name:'rdDestoWoDestRcvoNpod',       type:'decimal'},
			          {name:'rdDesttWoDestRcvoNpod',       type:'decimal'},
			          {name:'rdDestoWoDestRcvtNpod',       type:'decimal'},
			          {name:'rdDesttWoDestRcvtNpod',       type:'decimal'},
			          {name:'sfoPayApply',       type:'decimal'},
			          {name:'cpoDestPayApply',       type:'decimal'},
			          {name:'cpoOrigPayApply',       type:'decimal'},
			          {name:'customerCode',       type:'string'}]
		})

//Store
Ext.define('Foss.closing.mvrSnrfiStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrSnrfiModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrSnrfi.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNrfiDto.list',
			totalProperty : 'totalCount'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
        			var searchParams;
        			var form = Ext.getCmp('T_closing-mvrSnrfi_content').getQueryTab();
        			
        			var params = form.getForm().getValues();
        			Ext.apply(params,{
        				'mvrNrfiDto.customerCode':form.getCustomerCode()
        			});
    				
    	   			Ext.apply(operation, {
    	   				params : params 
    	   			});
    	   		} 
		};
		me.callParent([ cfg ]);
	} 
}); 
		
//定义查询Form
Ext.define('Foss.closing.mvrSnrfiQueryForm', {
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
			getCustomerCode:function(){
				var me = this;
				var form = me.getForm();
				if(!Ext.isEmpty(form.findField('lineCustomerCode').getValue())){
					return form.findField('lineCustomerCode').getValue();
				}else if(!Ext.isEmpty(form.findField('airCustomerCode').getValue())){
					return form.findField('airCustomerCode').getValue();
				}else if(!Ext.isEmpty(form.findField('agencyCustomerCode').getValue())){
					return form.findField('agencyCustomerCode').getValue();
				}else{
					return '';
				}
			},
			items : [{
				xtype : 'combo',
				name : 'mvrNrfiDto.period',
				fieldLabel:'查询期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrSnrfi.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .3
			},{
		    	xtype: 'combobox',
				fieldLabel:'客户类型',
				name:'customerType',
				labelWidth:85,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
						[closing.mvrSnrfi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
						 closing.mvrSnrfi.SETTLEMENT__CUSTOMER_TYPE__AIR,
						 closing.mvrSnrfi.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:closing.mvrSnrfi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
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
						lineCustomer = form.findField('lineCustomerCode');
						airCustomer = form.findField('airCustomerCode');
						agencyCustomer = form.findField('agencyCustomerCode');
						if( newValue=='LC'){
							lineCustomer.show();
							airCustomer.hide();
							airCustomer.setValue("");
							agencyCustomer.hide();
							agencyCustomer.setValue("");
						}else if(newValue=='A'){
							lineCustomer.hide();
							lineCustomer.setValue("");
							airCustomer.show();
							agencyCustomer.hide();
							agencyCustomer.setValue("");
						}else if(newValue=='AA'){
							lineCustomer.hide();
							lineCustomer.setValue("");
							airCustomer.hide();
							airCustomer.setValue("");
							agencyCustomer.show();
						}
					}
				}
		    },{
		    	xtype:'commoncustomerselector',
		    	name:'lineCustomerCode',
		    	fieldLabel:'客户信息',
		    	singlePeopleFlag : 'Y',
	    		listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
		 	},{
				xtype : 'commonairlinesselector',
				fieldLabel : '航空公司',
				name : 'airCustomerCode',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			}, {
				xtype : 'commonallagentselector',
				fieldLabel : '空运代理',
				name : 'agencyCustomerCode',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			},{
				xtype : 'combo',
				name : 'mvrNrfiDto.orgType',
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
				name : 'mvrNrfiDto.orgCode',
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
								var form = this.up('form');
								var me = this;
								if(form.getForm().isValid()){
									closing.mvrSnrfi.queryMvrSnrfiByConditions(me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrSnrfiQueryGrid', {
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
            if(record.data.customerCode == '汇总'){ // 汇总的样式
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
				handler:closing.mvrSnrfi.exportMvrSnrfi,
				disabled:!closing.mvrSnrfi.isPermission('/stl-web/closing/exportMvrSnrfi.action'),
				hidden:!closing.mvrSnrfi.isPermission('/stl-web/closing/exportMvrSnrfi.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '期间',
			width : 150,
			dataIndex : 'period'
		}, {
			text : '发票标记',
			width : 150,
			dataIndex : 'invoiceMark',
			renderer : function(value) {
				var displayField = FossDataDictionary.rendererSubmitToDisplay(
						value, 'SETTLEMENT_INVOICE_MARK');
				return displayField;
			}
		}, {
			text : '产品类型',
			width : 150,
			dataIndex : 'productCode',
			renderer:Foss.pkp.ProductData.rendererSubmitToDisplay
		}, {
			text : '部门类型',
			width : 150,
			dataIndex : 'orgType',
			renderer:function(value){
    			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'VOUCHER__ORG_TYPE');
    			return displayField;
    		}
		}, {
			text : '部门编码',
			width : 150,
			dataIndex : 'orgCode'
		}, {
			text : '部门名称',
			width : 150,
			dataIndex : 'orgName'
		} ]
	},{
		text : '理赔',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '出发部门申请',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '01理赔冲02始发应收已签收',
				width : 150,
				dataIndex : 'claimOrigoWoOrigRcvtPod'
			}, {
				text : '02理赔冲01始发应收已签收',
				width : 150,
				dataIndex : 'claimOrigtOrigRcvoPod'
			}, {
				text : '01理赔冲02始发应收未签收',
				width : 150,
				dataIndex : 'claimOrigoWoOrigRcvtNpod'
			}, {
				text : '02理赔冲01始发应收未签收',
				width : 150,
				dataIndex : 'claimOrigtWoOrigRcvoNpod'
			}, {
				text : '01理赔付款申请',
				width : 150,
				dataIndex : 'claimOrigoPayApply'
			} ]
		}, {
			text : '到达部门申请',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '01理赔冲收入',
				width : 150,
				dataIndex : 'claimDestoIncome'
			}, {
				text : '01理赔付款申请',
				width : 150,
				dataIndex : 'claimDestoPayApply'
			}, {
				text : '02理赔冲收入',
				width : 150,
				dataIndex : 'claimDesttIncome'
			}, {
				text : '01理赔冲01到达应收已签收',
				width : 150,
				dataIndex : 'claimDestoWoDestRcvoPod'
			}, {
				text : '01理赔冲02到达应收已签收',
				width : 150,
				dataIndex : 'claimDestoWoDestRcvtPod'
			}, {
				text : '02理赔冲01到达应收已签收',
				width : 150,
				dataIndex : 'claimDesttWoDestRcvoPod'
			}, {
				text : '02理赔冲02到达应收已签收',
				width : 150,
				dataIndex : 'claimDesttWoDestRcvtPod'
			}, {
				text : '01理赔冲01到达应收未签收',
				width : 150,
				dataIndex : 'claimDestoWoDestRcvoNpod'
			}, {
				text : '02理赔冲01到达应收未签收',
				width : 150,
				dataIndex : 'claimDesttWoDestRcvoNpod'
			}, {
				text : '01理赔冲02到达应收未签收',
				width : 150,
				dataIndex : 'claimDestoWoDestRcvtNpod'
			}, {
				text : '02理赔冲02到达应收未签收',
				width : 150,
				dataIndex : 'claimDesttWoDestRcvtNpod'
			} ]
		} ]
	}, {
		text : '预收客户',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '预收客户现金',
			width : 150,
			dataIndex : 'custDrOch'
		}, {
			text : '预收客户银行',
			width : 150,
			dataIndex : 'custDrOcd'
		}, {
			text : '01预收客户冲01应收到付运费未签收',
			width : 150,
			dataIndex : 'custDroWoDestRcvoNpod'
		}, {
			text : '01预收客户冲02应收到付运费未签收',
			width : 150,
			dataIndex : 'custDroWoDestRcvtNpod'
		}, {
			text : '02预收客户冲01应收到付运费未签收',
			width : 150,
			dataIndex : 'custDrtWoDestRcvoNpod'
		}, {
			text : '02预收客户冲02应收到付运费未签收',
			width : 150,
			dataIndex : 'custDrtWoDestRcvtNpod'
		}, {
			text : '01预收客户冲01应收到付运费已签收',
			width : 150,
			dataIndex : 'custDroWoDestRcvoPod'
		}, {
			text : '01预收客户冲02应收到付运费已签收',
			width : 150,
			dataIndex : 'custDroWoDestRcvtPod'
		}, {
			text : '02预收客户冲01应收到付运费已签收',
			width : 150,
			dataIndex : 'custDrtWoDestRcvoPod'
		}, {
			text : '02预收客户冲02应收到付运费已签收',
			width : 150,
			dataIndex : 'custDrtWoDestRcvtPod'
		}, {
			text : '01预收客户冲02应收始发运费未签收',
			width : 150,
			dataIndex : 'custDroWoOrigRcvtNpod'
		}, {
			text : '02预收客户冲01应收始发运费未签收',
			width : 150,
			dataIndex : 'custDrtWoOrigRcvoNpod'
		}, {
			text : '01预收客户冲02应收始发运费已签收',
			width : 150,
			dataIndex : 'custDroWoOrigRcvtPod'
		}, {
			text : '02预收客户冲01应收始发运费已签收',
			width : 150,
			dataIndex : 'custDrtWoOrigRcvoPod'
		}, {
			text : '01始发退预收付款申请',
			width : 150,
			dataIndex : 'custDroPayApply'
		} ]
	}, {
		text : '小票',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '01小票现金主营业务收入',
			width : 150,
			dataIndex : 'orChPbio'
		}, {
			text : '01小票银行主营业务收入',
			width : 150,
			dataIndex : 'orCdPbio'
		}, {
			text : '还款现金冲01小票应收',
			width : 150,
			dataIndex : 'orChUrRcvo'
		}, {
			text : '还款银行冲01小票应收',
			width : 150,
			dataIndex : 'orCdUrRcvo'
		}, {
			text : '01应付理赔冲02小票应收',
			width : 150,
			dataIndex : 'orClaimPayoWoRcvt'
		}, {
			text : '02应付理赔冲01小票应收',
			width : 150,
			dataIndex : 'orClaimPaytWoRcvo'
		}, {
			text : '01预收客户冲02小票应收',
			width : 150,
			dataIndex : 'orCustDroWoRcvt'
		}, {
			text : '02预收客户冲01小票应收',
			width : 150,
			dataIndex : 'orCustDrtWoRcvo'
		} ]
	}, {
		text : '退运费',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '出发部门申请',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '01退运费付款申请',
				width : 150,
				dataIndex : 'rdOrigoPayApply'
			}, {
				text : '01退运费冲02始发应收已签收',
				width : 150,
				dataIndex : 'rdOrigoWoOrigRcvtPod'
			}, {
				text : '02退运费冲01始发应收已签收',
				width : 150,
				dataIndex : 'rdOrigtWoOrigRcvoPod'
			}, {
				text : '01退运费冲02始发应收未签收',
				width : 150,
				dataIndex : 'rdOrigoWoOrigRcvtNpod'
			}, {
				text : '02退运费冲01始发应收未签收',
				width : 150,
				dataIndex : 'rdOrigtWoOrigRcvoNpod'
			} ]
		}, {
			text : '到达部门申请',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '01退运费冲收入',
				width : 150,
				dataIndex : 'rdDestoIncome'
			}, {
				text : '02退运费冲收入',
				width : 150,
				dataIndex : 'rdDesttIncome'
			}, {
				text : '01退运费付款申请',
				width : 150,
				dataIndex : 'rdDestoPayApply'
			}, {
				text : '01退运费冲01到达应收已签收',
				width : 150,
				dataIndex : 'rdDestoDestRcvoPod'
			}, {
				text : '02退运费冲01到达应收已签收',
				width : 150,
				dataIndex : 'rdDesttWoDestRcvoPod'
			}, {
				text : '01退运费冲02到达应收已签收',
				width : 150,
				dataIndex : 'rdDestoDestRcvtPod'
			}, {
				text : '02退运费冲02到达应收已签收',
				width : 150,
				dataIndex : 'rdDesttWoDestRcvtPod'
			}, {
				text : '01退运费冲01到达应收未签收',
				width : 150,
				dataIndex : 'rdDestoWoDestRcvoNpod'
			}, {
				text : '02退运费冲01到达应收未签收',
				width : 150,
				dataIndex : 'rdDesttWoDestRcvoNpod'
			}, {
				text : '01退运费冲02到达应收未签收',
				width : 150,
				dataIndex : 'rdDestoWoDestRcvtNpod'
			}, {
				text : '02退运费冲02到达应收未签收',
				width : 150,
				dataIndex : 'rdDesttWoDestRcvtNpod'
			} ]
		} ]
	}, {
		text : '装卸费',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '01装卸费付款申请',
			width : 150,
			dataIndex : 'sfoPayApply'
		} ]
	}, {
		text : '服务补救',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '01到达服务补救付款申请',
			width : 150,
			dataIndex : 'cpoDestPayApply'
		}, {
			text : '01始发服务补救付款申请',
			width : 150,
			dataIndex : 'cpoOrigPayApply'
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrSnrfiStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrSnrfi_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrSnrfiQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrSnrfiQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrSnrfi_content',
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
						renderTo : 'T_closing-mvrSnrfi-body'
					});
		});