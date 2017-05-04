/**
 * 获取上个月期间
 */
closing.mvrNrfoso.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

//客户类型：Model
Ext.define('Foss.mvrNrfoso.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.mvrNrfoso.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfoso.CustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'01',customerTypeName:'客户'},
			{customerTypeCode:'02',customerTypeName:'空运代理'},
			{customerTypeCode:'03',customerTypeName:'偏线代理'},
			{customerTypeCode:'04',customerTypeName:'航空公司'},
			{customerTypeCode:'05',customerTypeName:'快递代理'}]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

/**
 * 声明账期model
 */
Ext.define('Foss.mvrNrfoso.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfoso.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfoso.PeriodModel',
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

closing.mvrNrfoso.setParams=function(form){
	//定义查询参数
	var params={};
	
	var period = form.findField('mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.period').getValue();
	if(period==null||period==''){
		//期间不能为空
		Ext.Msg.alert('温馨提示', '期间不能为空');
		return null;	
	}
		
	//获取FORM所有值
	params = form.getValues();
		
	//客户类型
	var custType = form.findField('customerType').getValue();
	//给客户默认值
	var customerCode = null;
	//客户类型
	if(custType=='01'){
		customerCode=Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').getValue();
	}else if(custType=='02'){
		customerCode=Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').getValue();
	}else if(custType=='03'){
		customerCode=Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').getValue();
	}else if(custType=='04'){
		customerCode=Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').getValue();
	}else if(custType=='05'){
		customerCode=Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').getValue();
	}
	
	Ext.apply(params,{ 
		'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.productCodeList':stl.convertProductCode(form.findField('mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.productCodeList').getValue())
	});
	
	
	return params;
}

/**
 * Form重置方法
 */
closing.mvrNrfoso.reset=function(){
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrNrfoso.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNrfoso_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrNrfoso.setParams(form);
		if(null==params){
			return;
		}
		//设置查询参数
		grid.store.setSubmitParams(params);
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		//设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
			var result =   Ext.decode(operation.response.responseText);	
			me.enable(true);
	       }
	    });
	}else {
		
		//请检查输入条件是否合法
		Ext.Msg.alert('温馨提示', '请检查输入条件合法性');
	}
}

/**
 * 导出始发应收月报表
 */
closing.mvrNrfoso.exportMvrNrfoso = function(){
	//获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrNrfoso_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出01特殊业务始发月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportMvrNrfosoForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNrfosoForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrNrfoso.action'), 
				form: Ext.fly('exportMvrNrfosoForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){

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
closing.mvrNrfoso.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrNrfosoComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNrfoso.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNrfosoComboModel', {
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

// 始发应收月报表数据模型
Ext.define('Foss.closing.mvrNrfosoModel', {
			extend : 'Ext.data.Model',
			fields : [
			    'id', 
			    'period', 
			    'productCode', 
			    'customerCode', 
			    'customerName',
			    'customerType',
			    'origOrgCode', 
			    'origOrgName', 
			    'destOrgCode', 
			    'destOrgName',
			    'origUnifiedCode',
			    'destUnifiedCode',
			    'unifiedSettlementType', 
			    'contractOrgCode', 
			    'contractOrgName', 
			    {name : 'claimOrigoIncome',/*01理赔冲收入*/type : 'decimal'},
			    {name : 'claimOrigoCost',/*01理赔入成本*/type : 'decimal'},
			    {name : 'claimOrigoWoOrigRcvoPod',/*01理赔冲01始发应收已签收*/type : 'decimal'},
			    {name : 'claimOrigoWoOrigRcvtPod',/*01理赔冲02始发应收已签收*/type : 'decimal'},
			    {name : 'claimOrigtOrigRcvoPod',/*02理赔冲01始发应收已签收*/type : 'decimal'},
			    {name : 'claimOrigoOrigRcvoNpod',/*01理赔冲01始发应收未签收*/type : 'decimal'},
			    {name : 'claimOrigoWoOrigRcvtNpod',/*01理赔冲02始发应收未签收*/type : 'decimal'},
			    {name : 'claimOrigtWoOrigRcvoNpod',/*02理赔冲01始发应收未签收*/type : 'decimal'},
			    {name : 'claimOrigoPayApply',/*01理赔付款申请*/type : 'decimal'},
			    {name : 'claimDestoIncome',/*01理赔冲收入*/type : 'decimal'},
			    {name : 'claimDestoWoDestRcvoPod',/*01理赔冲到01达应收已签收*/type : 'decimal'},
			    {name : 'claimDesttWoDestRcvoPod',/*02理赔冲01到达应收已签收*/type : 'decimal'},
			    {name : 'claimDestoWoDestRcvoNpod',/*01理赔冲01到达应收未签收*/type : 'decimal'},
			    {name : 'claimDesttWoDestRcvoNpod',/*02理赔冲01到达应收未签收*/type : 'decimal'},
			    {name : 'exOrigRcvoPod',/*01应收始发运费已签收*/type : 'decimal'},
			    {name : 'exDestRcvoPod',/*01应收到付运费已签收*/type : 'decimal'},
			    {name : 'bdWoOrigRcvoPod',/*坏账冲01应收始发运费已签收*/type : 'decimal'},
			    {name : 'bdWoDestRcvoPod',/*坏账冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'orChPbio',/*01小票现金主营业务收入*/type : 'decimal'},
			    {name : 'orCdPbio',/*01小票银行主营业务收入*/type : 'decimal'},
			    {name : 'orRcvoPbi',/*01小票应收主营业务收入*/type : 'decimal'},
			    {name : 'orChUrRcvo',/*还款现金冲01小票应收*/type : 'decimal'},
			    {name : 'orCdUrRcvo',/*还款银行冲01小票应收*/type : 'decimal'},
			    {name : 'orCodPayWoRcvo',/*应付代收货款冲01小票应收*/type : 'decimal'},
			    {name : 'orClaimPayoWoRcvo',/*01应付理赔冲01小票应收*/type : 'decimal'},
			    {name : 'orClaimPayoWoRcvt',/*01应付理赔冲02小票应收*/type : 'decimal'},
			    {name : 'orClaimPaytWoRcvo',/*02应付理赔冲01小票应收*/type : 'decimal'},
			    {name : 'orCustDroWoRcvo',/*01预收客户冲01小票应收*/type : 'decimal'},
			    {name : 'orCustDroWoRcvt',/*01预收客户冲02小票应收*/type : 'decimal'},
			    {name : 'orCustDrtWoRcvo',/*02预收客户冲01小票应收*/type : 'decimal'},
			    {name : 'orExWoRcvo',/*异常冲收入冲01小票应收*/type : 'decimal'},
			    {name : 'orBadWoRcvo',/*坏账损失冲01小票应收*/type : 'decimal'},
			    {name : 'acOrigRcvNwo',/*开单且为月结临时欠款网上支付未核销*/type : 'decimal'},
			    {name : 'acOrigRcvWo',/*开单且为月结临时欠款网上支付已核销*/type : 'decimal'},
			    {name : 'acCash',/*开单且为现金银行卡*/type : 'decimal'},
			    {name : 'rdOrigoIncome',/*01退运费冲收入*/type : 'decimal'},
			    {name : 'rdOrigoCost',/*01退运费入成本*/type : 'decimal'},
			    {name : 'rdOrigoPayApply',/*01退运费付款申请*/type : 'decimal'},
			    {name : 'rdOrigoWoOrigRcvoPod',/*01退运费冲01始发应收已签收*/type : 'decimal'},
			    {name : 'rdOrigoWoOrigRcvtPod',/*01退运费冲02始发应收已签收*/type : 'decimal'},
			    {name : 'rdOrigtWoOrigRcvoPod',/*02退运费冲01始发应收已签收*/type : 'decimal'},
			    {name : 'rdOrigoWoOrigRcvoNpod',/*01退运费冲01始发应收未签收*/type : 'decimal'},
			    {name : 'rdOrigoWoOrigRcvtNpod',/*01退运费冲02始发应收未签收*/type : 'decimal'},
			    {name : 'rdOrigtWoOrigRcvoNpod',/*02退运费冲01始发应收未签收*/type : 'decimal'},
			    {name : 'rdDestoIncome',/*01退运费冲收入*/type : 'decimal'},
			    {name : 'rdDestoDestRcvoPod',/*01退运费冲01到达应收已签收*/type : 'decimal'},
			    {name : 'rdDesttWoDestRcvoPod',/*02退运费冲01到达应收已签收*/type : 'decimal'},
			    {name : 'rdDestoWoDestRcvoNpod',/*01退运费冲01到达应收未签收*/type : 'decimal'},
			    {name : 'rdDesttWoDestRcvoNpod',/*02退运费冲01到达应收未签收*/type : 'decimal'},
			    {name : 'sfoPayApply',/*01装卸费付款申请*/type : 'decimal'},
			    {name : 'cpoOrigPayApply',/*01始发服务补救付款申请*/type : 'decimal'},
			    {name : 'custDrOch',/*预收客户现金*/type : 'decimal'},
			    {name : 'custDrOcd',/*预收客户银行*/type : 'decimal'},
			    {name : 'custDroWoDestRcvoNpod',/*01预收客户冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'custDrtWoDestRcvoNpod',/*02预收客户冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'custDroWoDestRcvoPod',/*01预收客户冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'custDrtWoDestRcvoPod',/*02预收客户冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'custDroWoOrigRcvoNpod',/*01预收客户冲01应收始发运费未签收*/type : 'decimal'},
			    {name : 'custDroWoOrigRcvtNpod',/*01预收客户冲02应收始发运费未签收*/type : 'decimal'},
			    {name : 'custDrtWoOrigRcvoNpod',/*02预收客户冲01应收始发运费未签收*/type : 'decimal'},
			    {name : 'custDroWoOrigRcvoPod',/*01预收客户冲01应收始发运费已签收*/type : 'decimal'},
			    {name : 'custDroWoOrigRcvtPod',/*01预收客户冲02应收始发运费已签收*/type : 'decimal'},
			    {name : 'custDrtWoOrigRcvoPod',/*02预收客户冲01应收始发运费已签收*/type : 'decimal'},
			    {name : 'custDroPayApply',/*01始发退预收付款申请*/type : 'decimal'},
			    {name : 'plCostWoDestRcvoPod',/*应付偏线代理成本冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'plCostWoDestRcvoNpod',/*应付偏线代理成本冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'plDrWoDestRcvoPod',/*预收偏线代理冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'plDrWoDestRcvoNpod',/*预收偏线代理冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'alDrWoDestRcvoPod',/*预收空运/快递代理冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'alDrWoDestRcvoNpod',/*预收空运/快递代理冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'alpwrWoDestRcvoPod',/*应付到达代理/快递代理成本冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'alpwrWoDestRcvoNpod',/*应付到达代理/快递代理成本冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'othPayWoDestRcvoPod',/*其他应付冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'othPayWoDestRcvoNpod',/*其他应付冲01应收到付运费未签收*/type : 'decimal'},
			    {name : 'popWoDroPod',/*其他应付冲01应收到付运费已签收*/type : 'decimal'},
			    {name : 'popWoDroNpod',/*其他应付冲01应收到付运费未签收*/type : 'decimal'}]
		})

//始发应收Store
Ext.define('Foss.closing.mvrNrfosoStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNrfosoModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNrfoso.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNrfosoEntityVo.mvrNrfosoEntityResultDto.mvrNrfosoEntityList',
			totalProperty : 'totalCount'
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
Ext.define('Foss.closing.mvrNrfosoQueryForm', {
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
				name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.period',
				fieldLabel : '期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNrfoso.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			},
		    {
		    	xtype:'combobox',
		    	name:'customerType',
		    	columnWidth:.33,
		    	fieldLabel:'客户类型',
				store:Ext.create('Foss.mvrNrfoso.CustomerTypeStore'),
				queryModel:'local',
				value:'01',
				editable:false,
				displayField:'customerTypeName',
				valueField:'customerTypeCode',
		    	listeners:{
					"select": {
						fn: function(_combo, _r){
							var cusValue=_combo.ownerCt.getForm().findField('customerType').getValue();
							if(cusValue==='01'){
								// 客户信息
								Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').show();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='02'){
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='03'){
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='04'){
								// 航空公司
								Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='05'){
								// 快递代理
								Ext.getCmp('closing.mvrNrfoso.commonLdpAgencyCompanySelector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfoso.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfoso.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfoso.commonairlinesselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfoso.commonairagencycompanyselector').hide();
							}
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :'客户信息',
		    	name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.customerCode',
		    	singlePeopleFlag : 'Y',
		    	id:'closing.mvrNrfoso.commoncustomerselector',
		    	columnWidth:.33,
				isPaging:true // 分页
		    }
		    ,{	
		    	xtype:'commonallagentselector',
		    	fieldLabel : '空运代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfoso.commonairagencycompanyselector',
				isPaging:true ,// 分页
				hidden:true
		    },
		    {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :'偏线代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfoso.commonvehagencycompselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonairlinesselector',
		    	fieldLabel :'航空公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfoso.commonairlinesselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	columnWidth:.33,
		    	name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfoso.commonLdpAgencyCompanySelector',
				isPaging:true ,// 分页
				hidden:true
		    }, {
				xtype : 'combo',
				name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfoso.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNrfoso.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfosoEntityVo.mvrNrfosoEntityQueryDto.destOrgCode',
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
							handler : closing.mvrNrfoso.reset
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
								  closing.mvrNrfoso.query(form,me)
							  }
						}]
			}]
		})

// 始发应收月报表查询Grid
Ext.define('Foss.closing.mvrNrfosoQueryGrid', {
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
				handler:closing.mvrNrfoso.exportMvrNrfoso,
				disabled:!closing.mvrNrfoso.isPermission('/stl-web/closing/exportMvrNrfoso.action'),
				hidden:!closing.mvrNrfoso.isPermission('/stl-web/closing/exportMvrNrfoso.action')
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
			dataIndex : 'origUnifiedCode'
		}, {
			text : '到达部门编码',
			width:110,
			dataIndex : 'destOrgCode'
		}, {
			text : '到达部门名称',
			width:110,
			dataIndex : 'destOrgName'
		}, {
			text : '到达部门标杆编码',
			width:110,
			dataIndex : 'destUnifiedCode'
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
			text : '合同部门编码',
			width:110,
			dataIndex : 'contractOrgCode'
		}, {
			text : '合同部门名称',
			width:110,
			dataIndex : 'contractOrgName'
		}]
	}, {
		text : '预收客户',
		columns : [ {
			text : '预收客户',
			columns : [ {
				text : '预收客户现金',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOch'
			}, {
				text : '预收客户银行',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOcd'
			}]},
			{
			text : '01预收客户冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoDestRcvoNpod'
		}, {
			text : '02预收客户冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoDestRcvoNpod'
		}, {
			text : '01预收客户冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoDestRcvoPod'
		}, {
			text : '02预收客户冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoDestRcvoPod'
		}, {
			text : '01预收客户冲01应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoOrigRcvoNpod'
		}, {
			text : '01预收客户冲02应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoOrigRcvtNpod'
		}, {
			text : '02预收客户冲01应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoOrigRcvoNpod'
		}, {
			text : '01预收客户冲01应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoOrigRcvoPod'
		}, {
			text : '01预收客户冲02应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoOrigRcvtPod'
		}, {
			text : '02预收客户冲01应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoOrigRcvoPod'
		}, {
			text : '01始发退预收付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'custDroPayApply'
		} ]
	}, {
		text : '偏线代理成本',
		columns : [ {
			text : '应付偏线代理成本冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvoPod'
		}, {
			text : '应付偏线代理成本冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvoNpod'
		} ]
	}, {
		text : '预收偏线代理',
		columns : [ {
			text : '预收偏线代理冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvoPod'
		}, {
			text : '预收偏线代理冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvoNpod'
		} ]
	}, {
		text : '预收空运/快递代理',
		columns : [ {
			text : '预收空运/快递代理冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'alDrWoDestRcvoPod'
		}, {
			text : '预收空运/快递代理冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'alDrWoDestRcvoNpod'
		} ]
	}, {
		text : '空运/快递代理应付冲应收',
		columns : [ {
			text : '应付到达代理/快递代理成本冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'alpwrWoDestRcvoPod'
		}, {
			text : '应付到达代理/快递代理成本冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'alpwrWoDestRcvoNpod'
		}, {
			text : '其他应付冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'othPayWoDestRcvoPod'
		}, {
			text : '其他应付冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'othPayWoDestRcvoNpod'
		} ]
	}, {
		text : '理赔',
		columns : [ {
			text : '出发部门申请',
			columns : [ {
				text : '01理赔冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoIncome'
			}, {
				text : '01理赔入成本',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoCost'
			}, {
				text : '01理赔冲01始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoWoOrigRcvoPod'
			}, {
				text : '01理赔冲02始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoWoOrigRcvtPod'
			}, {
				text : '02理赔冲01始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigtOrigRcvoPod'
			}, {
				text : '01理赔冲01始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoOrigRcvoNpod'
			}, {
				text : '01理赔冲02始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoWoOrigRcvtNpod'
			}, {
				text : '02理赔冲01始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigtWoOrigRcvoNpod'
			}, {
				text : '01理赔付款申请',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigoPayApply'
			} ]
		}, {
			text : '到达部门申请',
			columns : [ {
				text : '01理赔冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'claimDestoIncome'
			}, {
				text : '01理赔冲到01达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimDestoWoDestRcvoPod'
			}, {
				text : '02理赔冲01到达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimDesttWoDestRcvoPod'
			}, {
				text : '01理赔冲01到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimDestoWoDestRcvoNpod'
			}, {
				text : '02理赔冲01到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimDesttWoDestRcvoNpod'
			} ]
		} ]
	}, {
		text : '异常冲收入',
		columns : [ {
			text : '01应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'exOrigRcvoPod'
		}, {
			text : '01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'exDestRcvoPod'
		} ]
	}, {
		text : '坏账损失',
		columns : [ {
			text : '坏账冲01应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'bdWoOrigRcvoPod'
		}, {
			text : '坏账冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'bdWoDestRcvoPod'
		} ]
	}, {
		text : '小票',
		columns : [ {
			text : '小票录入现金',
			columns : [ {
				text : '01小票现金主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orChPbio'
			} ]
		}, {
			text : '小票录入银行',
			columns : [ {
				text : '01小票银行主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orCdPbio'
			} ]
		}, {
			text : '小票录入应收',
			columns : [ {
				text : '01小票应收主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvoPbi'
			} ]
		}, {
			text : '小票应收核销',
			columns : [ {
				text : '还款现金冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orChUrRcvo'
			}, {
				text : '还款银行冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orCdUrRcvo'
			}, {
				text : '应付代收货款冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orCodPayWoRcvo'
			}, {
				text : '01应付理赔冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orClaimPayoWoRcvo'
			}, {
				text : '01应付理赔冲02小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orClaimPayoWoRcvt'
			}, {
				text : '02应付理赔冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orClaimPaytWoRcvo'
			}, {
				text : '01预收客户冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orCustDroWoRcvo'
			}, {
				text : '01预收客户冲02小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orCustDroWoRcvt'
			}, {
				text : '02预收客户冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orCustDrtWoRcvo'
			}, {
				text : '异常冲收入冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orExWoRcvo'
			}, {
				text : '坏账损失冲01小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orBadWoRcvo'
			} ]
		} ]
	}, {
		text : '弃货、违禁品、全票丢货',
		columns : [ {
			text : '开单为月结临时欠款网上支付未核销',
			width : 180,
			align : 'right',
			dataIndex : 'acOrigRcvNwo'
		}, {
			text : '开单为月结临时欠款网上支付已核销',
			width : 180,
			align : 'right',
			dataIndex : 'acOrigRcvWo'
		}, {
			text : '开单为现金银行卡',
			width : 180,
			align : 'right',
			dataIndex : 'acCash'
		} ]
	}, {
		text : '退运费',
		columns : [ {
			text : '出发部门申请',
			columns : [ {
				text : '01退运费冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoIncome'
			}, {
				text : '01退运费入成本',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoCost'
			}, {
				text : '01退运费付款申请',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoPayApply'
			}, {
				text : '01退运费冲01始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoWoOrigRcvoPod'
			}, {
				text : '01退运费冲02始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoWoOrigRcvtPod'					
			}, {
				text : '02退运费冲01始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigtWoOrigRcvoPod'					
			}, {
				text : '01退运费冲01始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoWoOrigRcvoNpod'					
			}, {
				text : '01退运费冲02始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigoWoOrigRcvtNpod'					
			}, {
				text : '02退运费冲01始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigtWoOrigRcvoNpod'					
			} ]
		}, {
			text : '到达部门申请',
			columns : [ {
				text : '01退运费冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'rdDestoIncome'
			},{
				text : '01退运费冲01到达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdDestoDestRcvoPod'
			},{
				text : '02退运费冲01到达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdDesttWoDestRcvoPod'
			},{
				text : '01退运费冲01到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdDestoWoDestRcvoNpod'
			},{
				text : '02退运费冲01到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdDesttWoDestRcvoNpod'
			} ]
		} ]
	}, {
		text : '装卸费',
		columns : [ {
			text : '01装卸费付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'sfoPayApply'
		} ]
	}, {
		text : '服务补救',
		columns : [ {
			text : '01始发服务补救付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'cpoOrigPayApply'
		} ]
	}, {
		text : '偏线应收冲应付',
		columns : [ {
			text : '其他应付冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'popWoDroPod'
		}, {
			text : '其他应付冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'popWoDroNpod'
		}]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrNrfosoStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrNrfoso_content')) {
				return;
			}

			closing.mvrNrfoso.mvrNrfosoTotalRecord = Ext.create('Foss.closing.mvrNrfosoModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNrfosoQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNrfosoQueryGrid');
			
			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNrfoso_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						//获得查询FORM
						getQueryFrom : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						items : [queryForm,queryGrid],
						renderTo : 'T_closing-mvrNrfoso-body'
					});
		});