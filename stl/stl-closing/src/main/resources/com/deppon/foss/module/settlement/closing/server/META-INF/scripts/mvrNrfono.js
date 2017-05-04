/**
 * 获取上个月期间
 */
closing.mvrNrfono.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

// 客户类型：Model
Ext.define('Foss.mvrNrfono.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

// 客户类型：store
Ext.define('Foss.mvrNrfono.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfono.CustomerTypeModel',
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
Ext.define('Foss.mvrNrfono.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfono.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfono.PeriodModel',
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

closing.mvrNrfono.setParams=function(form){
	// 定义查询参数
	var params={};
	
	var period = form.findField('mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.period').getValue();
	if(period==null||period==''){
		// 期间不能为空
		Ext.Msg.alert('温馨提示', '期间不能为空');
		return null;	
	}
		
	// 获取FORM所有值
	params = form.getValues();
		
	// 客户类型
	var custType = form.findField('customerType').getValue();
	// 给客户默认值
	var customerCode = null;
	// 客户类型
	if(custType=='01'){
		customerCode=Ext.getCmp('closing.mvrNrfono.commoncustomerselector').getValue();
	}else if(custType=='02'){
		customerCode=Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').getValue();
	}else if(custType=='03'){
		customerCode=Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').getValue();
	}else if(custType=='04'){
		customerCode=Ext.getCmp('closing.mvrNrfono.commonairlinesselector').getValue();
	}else if(custType=='05'){
		customerCode=Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').getValue();
	}
	
	Ext.apply(params,{ 
		'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.productCodeList':stl.convertProductCode(form.findField('mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.productCodeList').getValue())
	});
	
	
	return params;
}

/**
 * Form重置方法
 */
closing.mvrNrfono.reset=function(){
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrNrfono.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrNrfono.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrNrfono.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNrfono_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrNrfono.setParams(form);
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
		// 设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
			var result =   Ext.decode(operation.response.responseText);	
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
closing.mvrNrfono.exportMvrNrfono = function(){
	// 获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrNrfono_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	// 提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出01普通业务始发月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			// 创建一个form
			if(!Ext.fly('exportMvrNrfonoForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrNrfonoForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			// 导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrNrfono.action'), 
				form: Ext.fly('exportMvrNrfonoForm'),
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
closing.mvrNrfono.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrNrfonoComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNrfono.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNrfonoComboModel', {
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
Ext.define('Foss.closing.mvrNrfonoModel', {
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
			    'unifiedSettlementType', 
			    'contractOrgCode', 
			    'contractOrgName', 
			    {name : 'podoCashCollectedFrt',/* 签收时现付已收款金额_公布价运费 */type : 'decimal'},
			    {name : 'podoCashCollectedPup',/* 签收时现付已收款金额_接货费 */type : 'decimal'},
			    {name : 'podoCashCollectedDel',/* 签收时现付已收款金额_送货费 */type : 'decimal'},
			    {name : 'podoCashCollectedPkg',/* 签收时现付已收款金额_包装费 */type : 'decimal'},
			    {name : 'podoCashCollectedCod',/* 签收时现付已收款金额_代收货款手续费 */type : 'decimal'},
			    {name : 'podoCashCollectedDv',/* 签收时现付已收款金额_保价费 */type : 'decimal'},
			    {name : 'podoCashCollectedOt',/* 签收时现付已收款金额_其它费用 */type : 'decimal'},
			    {name : 'podoOrigRcvWoFrt',/* 签收时始发应收已核销金额_公布价运费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoPup',/* 签收时始发应收已核销金额_接货费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoDel',/* 签收时始发应收已核销金额_送货费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoPkg',/* 签收时始发应收已核销金额_包装费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoCod',/* 签收时始发应收已核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoDv',/* 签收时始发应收已核销金额_保价费 */type : 'decimal'},
			    {name : 'podoOrigRcvWoOt',/* 签收时始发应收已核销金额_其它费用 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoFrt',/* 签收时始发应收未核销金额_公布价运费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoPup',/* 签收时始发应收未核销金额_接货费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoDel',/* 签收时始发应收未核销金额_送货费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoPkg',/* 签收时始发应收未核销金额_包装费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoCod',/* 签收时始发应收未核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoDv',/* 签收时始发应收未核销金额_保价费 */type : 'decimal'},
			    {name : 'podoOrigRcvNwoOt',/* 签收时始发应收未核销金额_其它费用 */type : 'decimal'},
			    {name : 'podoDestRcvWoFrt',/* 签收时到达应收已核销金额_公布价运费 */type : 'decimal'},
			    {name : 'podoDestRcvWoPup',/* 签收时到达应收已核销金额_接货费 */type : 'decimal'},
			    {name : 'podoDestRcvWoDel',/* 签收时到达应收已核销金额_送货费 */type : 'decimal'},
			    {name : 'podoDestRcvWoPkg',/* 签收时到达应收已核销金额_包装费 */type : 'decimal'},
			    {name : 'podoDestRcvWoCod',/* 签收时到达应收已核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'podoDestRcvWoDv',/* 签收时到达应收已核销金额_保价费 */type : 'decimal'},
			    {name : 'podoDestRcvWoOt',/* 签收时到达应收已核销金额_其它费用 */type : 'decimal'},
			    {name : 'podoDestRcvNwoFrt',/* 签收时到达应收未核销金额_公布价运费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoPup',/* 签收时到达应收未核销金额_接货费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoDel',/* 签收时到达应收未核销金额_送货费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoPkg',/* 签收时到达应收未核销金额_包装费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoCod',/* 签收时到达应收未核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoDv',/* 签收时到达应收未核销金额_保价费 */type : 'decimal'},
			    {name : 'podoDestRcvNwoOt',/* 签收时到达应收未核销金额_其它费用 */type : 'decimal'},
			    {name : 'updoCashCollectedFrt',/* 反签收时现付已收款金额_公布价运费 */type : 'decimal'},
			    {name : 'updoCashCollectedPup',/* 反签收时现付已收款金额_接货费 */type : 'decimal'},
			    {name : 'updoCashCollectedDel',/* 反签收时现付已收款金额_送货费 */type : 'decimal'},
			    {name : 'updoCashCollectedPkg',/* 反签收时现付已收款金额_包装费 */type : 'decimal'},
			    {name : 'updoCashCollectedCod',/* 反签收时现付已收款金额_代收货款手续费 */type : 'decimal'},
			    {name : 'updoCashCollectedDv',/* 反签收时现付已收款金额_保价费 */type : 'decimal'},
			    {name : 'updoCashCollectedOt',/* 反签收时现付已收款金额_其它费用 */type : 'decimal'},
			    {name : 'updoOrigRcvWoFrt',/* 反签收时始发应收已核销金额_公布价运费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoPup',/* 反签收时始发应收已核销金额_接货费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoDel',/* 反签收时始发应收已核销金额_送货费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoPkg',/* 反签收时始发应收已核销金额_包装费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoCod',/* 反签收时始发应收已核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoDv',/* 反签收时始发应收已核销金额_保价费 */type : 'decimal'},
			    {name : 'updoOrigRcvWoOt',/* 反签收时始发应收已核销金额_其它费用 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoFrt',/* 反签收时始发应收未核销金额_公布价运费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoPup',/* 反签收时始发应收未核销金额_接货费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoDel',/* 反签收时始发应收未核销金额_送货费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoPkg',/* 反签收时始发应收未核销金额_包装费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoCod',/* 反签收时始发应收未核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoDv',/* 反签收时始发应收未核销金额_保价费 */type : 'decimal'},
			    {name : 'updoOrigRcvNwoOt',/* 反签收时始发应收未核销金额_其它费用 */type : 'decimal'},
			    {name : 'updoDestRcvWoFrt',/* 反签收时到达应收已核销金额_公布价运费 */type : 'decimal'},
			    {name : 'updoDestRcvWoPup',/* 反签收时到达应收已核销金额_接货费 */type : 'decimal'},
			    {name : 'updoDestRcvWoDel',/* 反签收时到达应收已核销金额_送货费 */type : 'decimal'},
			    {name : 'updoDestRcvWoPkg',/* 反签收时到达应收已核销金额_包装费 */type : 'decimal'},
			    {name : 'updoDestRcvWoCod',/* 反签收时到达应收已核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'updoDestRcvWoDv',/* 反签收时到达应收已核销金额_保价费 */type : 'decimal'},
			    {name : 'updoDestRcvWoOt',/* 反签收时到达应收已核销金额_其它费用 */type : 'decimal'},
			    {name : 'updoDestRcvNwoFrt',/* 反签收时到达应收未核销金额_公布价运费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoPup',/* 反签收时到达应收未核销金额_接货费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoDel',/* 反签收时到达应收未核销金额_送货费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoPkg',/* 反签收时到达应收未核销金额_包装费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoCod',/* 反签收时到达应收未核销金额_代收货款手续费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoDv',/* 反签收时到达应收未核销金额_保价费 */type : 'decimal'},
			    {name : 'updoDestRcvNwoOt',/* 反签收时到达应收未核销金额_其它费用 */type : 'decimal'},
			    {name : 'deoCh',/* 开单现金 */type : 'decimal'},
			    {name : 'deoCd',/* 开单银行卡 */type : 'decimal'},
			    {name : 'uroOrigChNpod',/* 还款现金未签收 */type : 'decimal'},
			    {name : 'uroOrigCdNpod',/* 还款银行未签收 */type : 'decimal'},
			    {name : 'uroOrigChPod',/* 还款现金已签收 */type : 'decimal'},
			    {name : 'uroOrigCdPod',/* 还款银行已签收 */type : 'decimal'},
			    {name : 'uroDestChNpod',/* 还款现金未签收 */type : 'decimal'},
			    {name : 'uroDestCdNpod',/* 还款银行未签收 */type : 'decimal'},
			    {name : 'uroDestChPod',/* 还款现金已签收 */type : 'decimal'},
			    {name : 'uroDestCdPod',/* 还款银行已签收 */type : 'decimal'},
			    {name : 'custDrOch',/* 预收客户现金 */type : 'decimal'},
			    {name : 'custDrOcd',/* 预收客户银行 */type : 'decimal'},
			    {name : 'custDroWoDestRcvoNpod',/* 01预收客户冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'custDrtWoDestRcvoNpod',/* 02预收客户冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'custDroWoDestRcvoPod',/* 01预收客户冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'custDrtWoDestRcvoPod',/* 02预收客户冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'custDroWoOrigRcvoNpod',/* 01预收客户冲01应收始发运费未签收 */type : 'decimal'},
			    {name : 'custDroWoOrigRcvtNpod',/* 01预收客户冲02应收始发运费未签收 */type : 'decimal'},
			    {name : 'custDrtWoOrigRcvoNpod',/* 02预收客户冲01应收始发运费未签收 */type : 'decimal'},
			    {name : 'custDroWoOrigRcvoPod',/* 01预收客户冲01应收始发运费已签收 */type : 'decimal'},
			    {name : 'custDroWoOrigRcvtPod',/* 01预收客户冲02应收始发运费已签收 */type : 'decimal'},
			    {name : 'custDrtWoOrigRcvoPod',/* 02预收客户冲01应收始发运费已签收 */type : 'decimal'},
			    {name : 'custDroPayApply',/* 01始发退预收付款申请 */type : 'decimal'},
			    {name : 'codPayWoDestRcvoPod',/* 应付代收货款冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'codPayWoDestRcvoNpod',/* 应付代收货款冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'codPayWoOrigRcvoPod',/* 应付代收货款冲01应收始发运费已签收 */type : 'decimal'},
			    {name : 'codPayWoOrigRcvoNpod',/* 应付代收货款冲01应收始发运费未签收 */type : 'decimal'},
			    {name : 'plCostWoDestRcvoPod',/* 应付偏线代理成本冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'plCostWoDestRcvoNpod',/* 应付偏线代理成本冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'plDrWoDestRcvoPod',/* 预收偏线代理冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'plDrWoDestRcvoNpod',/* 预收偏线代理冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'alDrWoDestRcvoPod',/* 预收空运/快递代理冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'alDrWoDestRcvoNpod',/* 预收空运/快递代理冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'alpwrWoDestRcvoPod',/* 应付到达代理/快递代理成本冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'alpwrWoDestRcvoNpod',/* 应付到达代理/快递代理成本冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'othPayWoDestRcvoPod',/* 其他应付冲01应收到付运费已签收 */type : 'decimal'},
			    {name : 'othPayWoDestRcvoNpod',/* 其他应付冲01应收到付运费未签收 */type : 'decimal'},
			    {name : 'popWoDroPod',type : 'decimal'},
			    {name : 'popWoDroNpod',type : 'decimal'}]
		});

// 始发应收Store
Ext.define('Foss.closing.mvrNrfonoStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNrfonoModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNrfono.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNrfonoEntityVo.mvrNrfonoEntityResultDto.mvrNrfonoEntityList',
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
Ext.define('Foss.closing.mvrNrfonoQueryForm', {
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
				name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.period',
				fieldLabel : '期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNrfono.PeriodStore'),
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
				store:Ext.create('Foss.mvrNrfono.CustomerTypeStore'),
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
								Ext.getCmp('closing.mvrNrfono.commoncustomerselector').show();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfono.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='02'){
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfono.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfono.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='03'){
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfono.commoncustomerselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfono.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='04'){
								// 航空公司
								Ext.getCmp('closing.mvrNrfono.commonairlinesselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfono.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='05'){
								// 快递代理
								Ext.getCmp('closing.mvrNrfono.commonLdpAgencyCompanySelector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfono.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfono.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfono.commonairlinesselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfono.commonairagencycompanyselector').hide();
							}
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :'客户信息',
		    	name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.customerCode',
		    	singlePeopleFlag : 'Y',
		    	id:'closing.mvrNrfono.commoncustomerselector',
		    	columnWidth:.33,
				isPaging:true // 分页
		    }
		    ,{	
		    	xtype:'commonallagentselector',
		    	fieldLabel : '空运代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfono.commonairagencycompanyselector',
				isPaging:true ,// 分页
				hidden:true
		    },
		    {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :'偏线代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfono.commonvehagencycompselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonairlinesselector',
		    	fieldLabel :'航空公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfono.commonairlinesselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	columnWidth:.33,
		    	name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfono.commonLdpAgencyCompanySelector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype : 'combo',
				name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfono.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNrfono.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfonoEntityVo.mvrNrfonoEntityQueryDto.destOrgCode',
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
							handler : closing.mvrNrfono.reset
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
								  closing.mvrNrfono.query(form,me)
							  }
						}]
			}]
		})

// 始发应收月报表查询Grid
Ext.define('Foss.closing.mvrNrfonoQueryGrid', {
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
				handler:closing.mvrNrfono.exportMvrNrfono,
				disabled:!closing.mvrNrfono.isPermission('/stl-web/closing/exportMvrNrfono.action'),
				hidden:!closing.mvrNrfono.isPermission('/stl-web/closing/exportMvrNrfono.action')
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
		text : '开单运单',
		columns : [ {
			text : '开单现金',
			width : 180,
			align : 'right',
			dataIndex : 'deoCh'
		}, {
			text : '开单银行卡',
			width : 180,
			align : 'right',
			dataIndex : 'deoCd'
		} ]
	}, {
		text : '还款运单总运费（月结临时欠款网上支付）',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroOrigChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroOrigCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroOrigChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroOrigCdPod'
		} ]
	}, {
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroDestChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroDestCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroDestChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'uroDestCdPod'
		} ]
	}, {
		text : '签收运单',
		columns : [ {
			text : '签收时现付已收款金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podoCashCollectedOt'
			} ]
		}, {
			text : '签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvWoOt'
			} ]
		}, {
			text : '签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podoOrigRcvNwoOt'
			} ]
		}, {
			text : '签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvWoOt'
			} ]
		}, {
			text : '签收时到达应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podoDestRcvNwoOt'
			} ]
		} ]
	}, {
		text : '反签收运单',
		columns : [ {
			text : '反签收时现付已收款金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updoCashCollectedOt'
			} ]
		}, {
			text : '反签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvWoOt'
			} ]
		}, {
			text : '反签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updoOrigRcvNwoOt'
			} ]
		}, {
			text : '反签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvWoOt'
			} ]
		}, {
			text : '反签收时到达应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updoDestRcvNwoOt'
			} ]
		} ]
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
		text : '代收货款',
		columns : [ {
			text : '应付代收货款冲01应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoDestRcvoPod'
		}, {
			text : '应付代收货款冲01应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoDestRcvoNpod'
		}, {
			text : '应付代收货款冲01应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoOrigRcvoPod'
		}, {
			text : '应付代收货款冲01应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoOrigRcvoNpod'
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
	},{
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
		
		me.store = Ext.create('Foss.closing.mvrNrfonoStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrNrfono_content')) {
				return;
			}

			closing.mvrNrfono.mvrNrfonoTotalRecord = Ext.create('Foss.closing.mvrNrfonoModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNrfonoQueryForm');
			
			// 显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNrfonoQueryGrid');
			
			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNrfono_content',
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
						renderTo : 'T_closing-mvrNrfono-body'
					});
		});