/**
 * 获取上个月期间
 */
closing.mvrRfo.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

//客户类型：Model
Ext.define('Foss.mvrRfo.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.mvrRfo.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrRfo.CustomerTypeModel',
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
Ext.define('Foss.mvrRfo.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrRfo.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrRfo.PeriodModel',
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

closing.mvrRfo.setParams=function(form){
	//定义查询参数
	var params={};
	
	var period = form.findField('mvrRfoEntityVo.mvrRfoEntityQueryDto.period').getValue();
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
		customerCode=Ext.getCmp('closing.mvrRfo.commoncustomerselector').getValue();
	}else if(custType=='02'){
		customerCode=Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').getValue();
	}else if(custType=='03'){
		customerCode=Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').getValue();
	}else if(custType=='04'){
		customerCode=Ext.getCmp('closing.mvrRfo.commonairlinesselector').getValue();
	}else if(custType=='05'){
		customerCode=Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').getValue();
	}
	
	Ext.apply(params,{ 
		'mvrRfoEntityVo.mvrRfoEntityQueryDto.productCodeList':stl.convertProductCode(form.findField('mvrRfoEntityVo.mvrRfoEntityQueryDto.productCodeList').getValue())
	});
	
	
	return params;
}

/**
 * Form重置方法
 */
closing.mvrRfo.reset=function(){
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrRfo.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrRfo.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrRfo.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrRfo_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrRfo.setParams(form);
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
closing.mvrRfo.exportMvrRfo = function(){
	//获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrRfo_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportMvrRfoForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrRfoForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrRfo.action'), 
				form: Ext.fly('exportMvrRfoForm'),
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
closing.mvrRfo.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrRfoComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrRfo.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrRfoComboModel', {
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
Ext.define('Foss.closing.mvrRfoModel', {
			extend : 'Ext.data.Model',
			fields : [
			    'id', 'period', 'productCode', 'customerCode', 'customerName','customerType','origOrgCode', 'origOrgName', 'destOrgCode', 'destOrgName'
			    ,{
				/** 其他费用 */
				name : 'podDestRcvWoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'podDestRcvNwoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'podDestRcvNwoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'podDestRcvNwoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'podDestRcvNwoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'podDestRcvNwoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'podDestRcvNwoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'podDestRcvNwoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'updCashCollectedFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'updCashCollectedPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'updCashCollectedDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'updCashCollectedPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'updCashCollectedDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'updCashCollectedCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'updCashCollectedOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'updOrigRcvWoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'updOrigRcvWoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'updOrigRcvWoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'updOrigRcvWoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'updOrigRcvWoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'updOrigRcvWoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'updOrigRcvWoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'updOrigRcvNwoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'updOrigRcvNwoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'updOrigRcvNwoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'updOrigRcvNwoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'updOrigRcvNwoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'updOrigRcvNwoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'updOrigRcvNwoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'updDestRcvWoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'updDestRcvWoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'updDestRcvWoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'updDestRcvWoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'updDestRcvWoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'updDestRcvWoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'updDestRcvWoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'updDestRcvNwoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'updDestRcvNwoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'updDestRcvNwoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'updDestRcvNwoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'updDestRcvNwoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'updDestRcvNwoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'updDestRcvNwoOt',
				type : 'decimal'
				},
				{
				/** 理赔冲收入 */
				name : 'claimOrigWoIncome',
				type : 'decimal'
				},
				{
				/** 理赔入成本 */
				name : 'claimOrigCost',
				type : 'decimal'
				},
				{
				/** 理赔冲始发应收已签收 */
				name : 'claimWoOrigRcvPod',
				type : 'decimal'
				},
				{
				/** 理赔冲始发应收未签收 */
				name : 'claimWoOrigRcvNpod',
				type : 'decimal'
				},
				{
				/** 理赔付款申请 */
				name : 'claimOrigPayApply',
				type : 'decimal'
				},
				{
				/** 理赔冲收入 */
				name : 'claimDestWoIncome',
				type : 'decimal'
				},
				{
				/** 理赔冲到达应收已签收 */
				name : 'claimWoDestRcvPod',
				type : 'decimal'
				},
				{
				/** 理赔冲到达应收未签收 */
				name : 'claimWoDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 装卸费付款申请 */
				name : 'sfPayApply',
				type : 'decimal'
				},
				{
				/** 应付代收货款冲应收到付运费已签收 */
				name : 'codDestRcvPod',
				type : 'decimal'
				},
				{
				/** 应付代收货款冲应收到付运费未签收 */
				name : 'codDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 应付代收货款冲应收始发运费已签收 */
				name : 'codOrigRcvPod',
				type : 'decimal'
				},
				{
				/** 应付代收货款冲应收始发运费未签收 */
				name : 'codOrigRcvNpod',
				type : 'decimal'
				},
				{
				/** 预收客户现金 */
				name : 'custDrCh',
				type : 'decimal'
				},
				{
				/** 预收客户银行 */
				name : 'custDrCd',
				type : 'decimal'
				},
				{
				/** 预收客户冲应收到付运费未签收 */
				name : 'custDrDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 预收客户冲应收到付运费已签收 */
				name : 'custDrDestRcvPod',
				type : 'decimal'
				},
				{
				/** 预收客户冲应收始发运费未签收 */
				name : 'custDrOrigRcvNpod',
				type : 'decimal'
				},
				{
				/** 预收客户冲应收始发运费已签收 */
				name : 'custDrOrigRcvPod',
				type : 'decimal'
				},
				{
				/** 始发退预收付款申请 */
				name : 'custDrOrigPayApply',
				type : 'decimal'
				},
				{
				/** 应收始发运费已签收 */
				name : 'exOrigRcvPod',
				type : 'decimal'
				},
				{
				/** 应收到付运费已签收 */
				name : 'exDestRcvPod',
				type : 'decimal'
				},
				{
				/** 坏账冲应收始发运费已签收 */
				name : 'bdOrigRcvPod',
				type : 'decimal'
				},
				{
				/** 坏账冲应收到付运费已签收 */
				name : 'bdDestRcvPod',
				type : 'decimal'
				},
				{
				/** 小票现金之事故赔款  */
				name : 'orChAc',
				type : 'decimal'
				},
				{
				/** 小票现金之变卖废品收入 */
				name : 'orChSi',
				type : 'decimal'
				},
				{
				/** 小票现金之客户多付运费或盘点长款金额  */
				name : 'orChOpay',
				type : 'decimal'
				},
				{
				/** 小票现金之其他 */
				name : 'orChOther',
				type : 'decimal'
				},
				{
				/** 小票现金主营业务收入 */
				name : 'orChMbi',
				type : 'decimal'
				},
				{
				/** 小票银行之事故赔款  */
				name : 'orCdAc',
				type : 'decimal'
				},
				{
				/** 小票银行之收银员卡利息 */
				name : 'orCdBankIt',
				type : 'decimal'
				},
				{
				/** 小票银行之客户多付运费或盘点长款金额  */
				name : 'orCdOpay',
				type : 'decimal'
				},
				{
				/** 小票银行之其他 */
				name : 'orCdOther',
				type : 'decimal'
				},
				{
				/** 小票银行主营业务收入 */
				name : 'orCdMbi',
				type : 'decimal'
				},
				{
				/** 小票应收主营业务收入 */
				name : 'orRcvMbi',
				type : 'decimal'
				},
				{
				/** 还款现金冲小票应收 */
				name : 'orRcvWoUrCh',
				type : 'decimal'
				},
				{
				/** 还款银行冲小票应收 */
				name : 'orRcvWoUrCd',
				type : 'decimal'
				},
				{
				/** 应付代收货款冲小票应收 */
				name : 'orRcvWoCodPay',
				type : 'decimal'
				},
				{
				/** 应付理赔冲小票应收 */
				name : 'orRcvWoClaimPay',
				type : 'decimal'
				},
				{
				/** 预收客户冲小票应收 */
				name : 'orRcvWoCustDr',
				type : 'decimal'
				},
				{
				/** 坏账之保险理赔冲小票应收 */
				name : 'orRcvWoBdDebt',
				type : 'decimal'
				},
				{
				/** 坏账之坏账损失冲小票应收 */
				name : 'orRcvWoBdIncome',
				type : 'decimal'
				},
				{
				/** 开单为月结临时欠款网上支付未核销 */
				name : 'acCtdtolNwo',
				type : 'decimal'
				},
				{
				/** 开单为月结临时欠款网上支付已核销 */
				name : 'acCtdtolWo',
				type : 'decimal'
				},
				{
				/** 开单为现金银行卡 */
				name : 'acChcd',
				type : 'decimal'
				},
				{
				/** 退运费冲收入 */
				name : 'rdOrigWoIncome',
				type : 'decimal'
				},
				{
				/** 退运费入成本 */
				name : 'rdOrigCost',
				type : 'decimal'
				},
				{
				/** 退运费付款申请 */
				name : 'rdOrigPayApply',
				type : 'decimal'
				},
				{
				/** 退运费冲收入 */
				name : 'rdDestWoIncome',
				type : 'decimal'
				},
				{
				/** 始发服务补救付款申请 */
				name : 'cnOrigPayApply',
				type : 'decimal'
				},
				{
				/** 应付偏线代理成本冲应收到付运费已签收 */
				name : 'plCostWoDestRcvPod',
				type : 'decimal'
				},
				{
				/** 应付偏线代理成本冲应收到付运费未签收 */
				name : 'plCostWoDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 预收偏线代理冲应收到付运费已签收 */
				name : 'plDrWoDestRcvPod',
				type : 'decimal'
				},
				{
				/** 预收偏线代理冲应收到付运费未签收 */
				name : 'plDrWoDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 预收空运代理冲应收到付运费已签收 */
				name : 'airDrDestRcvPod',
				type : 'decimal'
				},
				{
				/** 预收空运代理冲应收到付运费未签收 */
				name : 'airDrDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 应付到达代理成本冲应收到付运费已签收 */
				name : 'airPrAgencyWoDestRcvPod',
				type : 'decimal'
				},
				{
				/** 应付到达代理成本冲应收到付运费未签收 */
				name : 'airPrAgencyWoDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 其他应付冲应收到付运费已签收 */
				name : 'airPrOtWoDestRcvPod',
				type : 'decimal'
				},
				{
				/** 其他应付冲应收到付运费未签收 */
				name : 'airPrOthWoDestRcvNpod',
				type : 'decimal'
				},
				{
				/** 开单现金 */
				name : 'deCh',
				type : 'decimal'
				},
				{
				/** 开单银行卡 */
				name : 'deCd',
				type : 'decimal'
				},
				{
				/** 还款现金未签收 */
				name : 'urOrigChNpod',
				type : 'decimal'
				},
				{
				/** 还款银行未签收 */
				name : 'urOrigCdNpod',
				type : 'decimal'
				},
				{
				/** 还款现金已签收 */
				name : 'urOrigChPod',
				type : 'decimal'
				},
				{
				/** 还款银行已签收 */
				name : 'urOrigCdPod',
				type : 'decimal'
				},
				{
				/** 还款现金未签收 */
				name : 'urDestChNpod',
				type : 'decimal'
				},
				{
				/** 还款银行未签收 */
				name : 'urDestCdNpod',
				type : 'decimal'
				},
				{
				/** 还款现金已签收 */
				name : 'urDestChPod',
				type : 'decimal'
				},
				{
				/** 还款银行已签收 */
				name : 'urDestCdPod',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'podCashCollectedFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'podCashCollectedPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'podCashCollectedDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'podCashCollectedPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'podCashCollectedDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'podCashCollectedCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'podCashCollectedOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'podOrigRcvWoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'podOrigRcvWoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'podOrigRcvWoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'podOrigRcvWoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'podOrigRcvWoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'podOrigRcvWoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'podOrigRcvWoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'podOrigRcvNwoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'podOrigRcvNwoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'podOrigRcvNwoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'podOrigRcvNwoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'podOrigRcvNwoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'podOrigRcvNwoCod',
				type : 'decimal'
				},
				{
				/** 其他费用 */
				name : 'podOrigRcvNwoOt',
				type : 'decimal'
				},
				{
				/** 公布价运费 */
				name : 'podDestRcvWoFrt',
				type : 'decimal'
				},
				{
				/** 接货费 */
				name : 'podDestRcvWoPup',
				type : 'decimal'
				},
				{
				/** 送货费 */
				name : 'podDestRcvWoDel',
				type : 'decimal'
				},
				{
				/** 包装费 */
				name : 'podDestRcvWoPkg',
				type : 'decimal'
				},
				{
				/** 保价费 */
				name : 'podDestRcvWoDv',
				type : 'decimal'
				},
				{
				/** 代收货款手续费 */
				name : 'podDestRcvWoCod',
				type : 'decimal'
				},
				/** 退运费冲始发应收已签收 **/
				{
				name:'rdWoOrigRcvPod',
				type:'decimal'
				},
				/**退运费冲始发应收未签收**/
				{
				name:'rdWoOrigRcvNpod',
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
				}]
		})

//始发应收Store
Ext.define('Foss.closing.mvrRfoStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrRfoModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrRfo.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrRfoEntityVo.mvrRfoEntityResultDto.mvrRfoEntityList',
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
Ext.define('Foss.closing.mvrRfoQueryForm', {
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
				name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.period',
				fieldLabel : '期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrRfo.getLastMonthPeriod(),
				store : closing.mvrRfo.getComboPeriodStore(),
				columnWidth : .33*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrRfo.PeriodStore'),
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
				store:Ext.create('Foss.mvrRfo.CustomerTypeStore'),
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
								Ext.getCmp('closing.mvrRfo.commoncustomerselector').show();
								// 空运代理公司
								Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrRfo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='02'){
								// 空运代理公司
								Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrRfo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrRfo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='03'){
								// 偏线代理公司
								Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrRfo.commoncustomerselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrRfo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='04'){
								// 航空公司
								Ext.getCmp('closing.mvrRfo.commonairlinesselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrRfo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='05'){
								// 快递代理
								Ext.getCmp('closing.mvrRfo.commonLdpAgencyCompanySelector').show();
								// 客户信息
								Ext.getCmp('closing.mvrRfo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrRfo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrRfo.commonairlinesselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrRfo.commonairagencycompanyselector').hide();
							}
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :'客户信息',
		    	name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
		    	singlePeopleFlag : 'Y',
		    	id:'closing.mvrRfo.commoncustomerselector',
		    	columnWidth:.33,
				isPaging:true // 分页
		    }
		    ,{	
		    	xtype:'commonallagentselector',
		    	fieldLabel : '空运代理公司',
		    	columnWidth:.33,
		    	name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
		    	id:'closing.mvrRfo.commonairagencycompanyselector',
				isPaging:true ,// 分页
				hidden:true
		    },
		    {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :'偏线代理公司',
		    	columnWidth:.33,
		    	name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
		    	id:'closing.mvrRfo.commonvehagencycompselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonairlinesselector',
		    	fieldLabel :'航空公司',
		    	columnWidth:.33,
		    	name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
		    	id:'closing.mvrRfo.commonairlinesselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	columnWidth:.33,
		    	name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
		    	id:'closing.mvrRfo.commonLdpAgencyCompanySelector',
				isPaging:true ,// 分页
				hidden:true
		    }/*, {
				xtype : 'commoncustomerselector',
				fieldLabel : '客户',
				name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.customerCode',
				columnWidth : .33
			}*/, {
				xtype : 'combo',
				name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrRfo.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrRfo.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrRfoEntityVo.mvrRfoEntityQueryDto.destOrgCode',
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
							handler : closing.mvrRfo.reset
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
								  closing.mvrRfo.query(form,me)
							  }
						}]
			}]
		})

// 始发应收月报表查询Grid
Ext.define('Foss.closing.mvrRfoQueryGrid', {
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
				handler:closing.mvrRfo.exportMvrRfo,
				disabled:!closing.mvrRfo.isPermission('/stl-web/closing/exportMvrRfo.action'),
				hidden:!closing.mvrRfo.isPermission('/stl-web/closing/exportMvrRfo.action')
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
		},{
			text : '始发部门编</br>码',
			width:110,
			dataIndex : 'origOrgCode'
		}, {
			text : '始发部门名称',
			width:110,
			dataIndex : 'origOrgName'
		}, {
			text : '到达部门编码',
			width:110,
			dataIndex : 'destOrgCode'
		},{
			text : '到达部门名称',
			width:110,
			dataIndex : 'destOrgName'
		} ]
	}, {
		text : '开单运单',
		columns : [ {
			text : '开单现金',
			width : 180,
			align : 'right',
			dataIndex : 'deCh'
		}, {
			text : '开单银行卡',
			width : 180,
			align : 'right',
			dataIndex : 'deCd'
		} ]
	}, {
		text : '还款运单总运费（月结临时欠款网上支付）',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urOrigChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urOrigCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urOrigChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urOrigCdPod'
		} ]
	}, {
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urDestChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urDestCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urDestChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urDestCdPod'
		} ]
	}, {
		text : '签收运单',
		columns : [ {
			text : '签收时现付已收款金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podCashCollectedOt'
			} ]
		}, {
			text : '签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvWoOt'
			} ]
		}, {
			text : '签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podOrigRcvNwoOt'
			} ]
		}, {
			text : '签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvWoOt'
			} ]
		}, {
			text : '签收时到达应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podDestRcvNwoOt'
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
				dataIndex : 'updCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updCashCollectedOt'
			} ]
		}, {
			text : '反签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvWoOt'
			} ]
		}, {
			text : '反签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updOrigRcvNwoOt'
			} ]
		}, {
			text : '反签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvWoOt'
			} ]
		}, {
			text : '反签收时到达应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updDestRcvNwoOt'
			} ]
		} ]
	}, {
		text : '理赔',
		columns : [ {
			text : '出发部门申请',
			columns : [ {
				text : '理赔冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigWoIncome'
			}, {
				text : '理赔入成本',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigCost'
			}, {
				text : '理赔冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoOrigRcvPod'
			}, {
				text : '理赔冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoOrigRcvNpod'
			}, {
				text : '理赔付款申请',
				width : 180,
				align : 'right',
				dataIndex : 'claimOrigPayApply'
			} ]
		}, {
			text : '到达部门申请',
			columns : [ {
				text : '理赔冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'claimDestWoIncome'
			}, {
				text : '理赔冲到达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoDestRcvPod'
			}, {
				text : '理赔冲到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoDestRcvNpod'
			} ]
		} ]
	}, {
		text : '装卸费',
		columns : [ {
			text : '装卸费付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'sfPayApply'
		} ]
	}, {
		text : '代收货款',
		columns : [ {
			text : '应付代收货款冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codDestRcvPod'
		}, {
			text : '应付代收货款冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codDestRcvNpod'
		}, {
			text : '应付代收货款冲应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codOrigRcvPod'
		}, {
			text : '应付代收货款冲应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codOrigRcvNpod'
		} ]
	}, {
		text : '营业部预收客户',
		columns : [ {
			text : '预收客户现金',
			width : 180,
			align : 'right',
			dataIndex : 'custDrCh'
		}, {
			text : '预收客户银行',
			width : 180,
			align : 'right',
			dataIndex : 'custDrCd'
		}, {
			text : '预收客户冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrDestRcvNpod'
		}, {
			text : '预收客户冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrDestRcvPod'
		}, {
			text : '预收客户冲应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrOrigRcvNpod'
		}, {
			text : '预收客户冲应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrOrigRcvPod'
		}, {
			text : '始发退预收付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'custDrOrigPayApply'
		} ]
	}, {
		text : '异常冲收入',
		columns : [ {
			text : '应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'exOrigRcvPod'
		}, {
			text : '应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'exDestRcvPod'
		} ]
	}, {
		text : '坏账损失',
		columns : [ {
			text : '坏账冲应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'bdOrigRcvPod'
		}, {
			text : '坏账冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'bdDestRcvPod'
		} ]
	}, {
		text : '小票',
		columns : [ {
			text : '小票录入现金',
			columns : [ {
				text : '小票现金之事故赔款',
				width : 180,
				align : 'right',
				dataIndex : 'orChAc'
			}, {
				text : '小票现金之变卖废品收入',
				width : 180,
				align : 'right',
				dataIndex : 'orChSi'
			}, {
				text : '小票现金之客户多付运费或盘点长款金额',
				width : 180,
				align : 'right',
				dataIndex : 'orChOpay'
			}, {
				text : '小票现金之其他',
				width : 180,
				align : 'right',
				dataIndex : 'orChOther'
			}, {
				text : '小票现金主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orChMbi'
			} ]
		}, {
			text : '小票录入银行',
			columns : [ {
				text : '小票银行之事故赔款',
				width : 180,
				align : 'right',
				dataIndex : 'orCdAc'
			}, {
				text : '小票银行之收银员卡利息',
				width : 180,
				align : 'right',
				dataIndex : 'orCdBankIt'
			}, {
				text : '小票银行之客户多付运费或盘点长款金额',
				width : 180,
				align : 'right',
				dataIndex : 'orCdOpay'
			}, {
				text : '小票银行之其他',
				width : 180,
				align : 'right',
				dataIndex : 'orCdOther'
			}, {
				text : '小票银行主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orCdMbi'
			} ]
		}, {
			text : '小票录入应收',
			columns : [ {
				text : '小票应收主营业务收入',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvMbi'
			} ]
		}, {
			text : '小票应收核销',
			columns : [ {
				text : '还款现金冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoUrCh'
			}, {
				text : '还款银行冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoUrCd'
			}, {
				text : '应付代收货款冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoCodPay'
			}, {
				text : '应付理赔冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoClaimPay'
			}, {
				text : '预收客户冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoCustDr'
			}, {
				text : '坏账之保险理赔冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoBdDebt'
			}, {
				text : '坏账之坏账损失冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoBdIncome'
			} ]
		} ]
	}, {
		text : '弃货、违禁品、全票丢货',
		columns : [ {
			text : '开单为月结临时欠款网上支付未核销',
			width : 180,
			align : 'right',
			dataIndex : 'acCtdtolNwo'
		}, {
			text : '开单为月结临时欠款网上支付已核销',
			width : 180,
			align : 'right',
			dataIndex : 'acCtdtolWo'
		}, {
			text : '开单为现金银行卡',
			width : 180,
			align : 'right',
			dataIndex : 'acChcd'
		} ]
	}, {
		text : '退运费',
		columns : [ {
			text : '出发部门申请',
			columns : [ {
				text : '退运费冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigWoIncome'
			}, {
				text : '退运费入成本',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigCost'
			}, {
				text : '退运费付款申请',
				width : 180,
				align : 'right',
				dataIndex : 'rdOrigPayApply'
			}, {
				text : '退运费冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdWoOrigRcvPod'
			}, {
				text : '退运费冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdWoOrigRcvNpod'					
			} ]
		}, {
			text : '到达部门申请',
			columns : [ {
				text : '退运费冲收入',
				width : 180,
				align : 'right',
				dataIndex : 'rdDestWoIncome'
			},{
				text : '退运费冲到达应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdWoDestRcvPod'
			},{
				text : '退运费冲到达应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'rdWoDestRcvNpod'
			} ]
		} ]
	}, {
		text : '服务补救',
		columns : [ {
			text : '始发服务补救付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'cnOrigPayApply'
		} ]
	}, {
		text : '偏线代理成本',
		columns : [ {
			text : '应付偏线代理成本冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvPod'
		}, {
			text : '应付偏线代理成本冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvNpod'
		} ]
	}, {
		text : '预收偏线代理',
		columns : [ {
			text : '预收偏线代理冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvPod'
		}, {
			text : '预收偏线代理冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvNpod'
		} ]
	}, {
		text : '预收空运/快递代理',
		columns : [ {
			text : '预收空运/快递代理冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'airDrDestRcvPod'
		}, {
			text : '预收空运/快递代理冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'airDrDestRcvNpod'
		} ]
	}, {
		text : '空运/快递代理应付冲应收',
		columns : [ {
			text : '应付到达代理/快递代理成本冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'airPrAgencyWoDestRcvPod'
		}, {
			text : '应付到达代理/快递代理成本冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'airPrAgencyWoDestRcvNpod'
		}, {
			text : '其他应付冲应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'airPrOtWoDestRcvPod'
		}, {
			text : '其他应付冲应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'airPrOthWoDestRcvNpod'
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrRfoStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrRfo_content')) {
				return;
			}

			closing.mvrRfo.mvrRfoTotalRecord = Ext.create('Foss.closing.mvrRfoModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrRfoQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrRfoQueryGrid');
			
			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrRfo_content',
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
						renderTo : 'T_closing-mvrRfo-body'
					});
		});