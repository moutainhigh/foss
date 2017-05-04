/**
 * 获取上个月期间
 */
closing.mvrNrfont.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

//客户类型：Model
Ext.define('Foss.mvrNrfont.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.mvrNrfont.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfont.CustomerTypeModel',
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
Ext.define('Foss.mvrNrfont.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfont.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrNrfont.PeriodModel',
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

closing.mvrNrfont.setParams=function(form){
	//定义查询参数
	var params={};
	
	var period = form.findField('mvrNrfontEntityVo.mvrNrfontEntityQueryDto.period').getValue();
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
		customerCode=Ext.getCmp('closing.mvrNrfont.commoncustomerselector').getValue();
	}else if(custType=='02'){
		customerCode=Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').getValue();
	}else if(custType=='03'){
		customerCode=Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').getValue();
	}else if(custType=='04'){
		customerCode=Ext.getCmp('closing.mvrNrfont.commonairlinesselector').getValue();
	}else if(custType=='05'){
		customerCode=Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').getValue();
	}
	
	Ext.apply(params,{ 
		'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.productCodeList':stl.convertProductCode(form.findField('mvrNrfontEntityVo.mvrNrfontEntityQueryDto.productCodeList').getValue())
	});
	
	
	return params;
}

/**
 * Form重置方法
 */
closing.mvrNrfont.reset=function(){
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrNrfont.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrNrfont.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrNrfont.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNrfont_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrNrfont.setParams(form);
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
 * 导出02普通业务始发月报表
 */
closing.mvrNrfont.exportmvrNrfont = function(){
	//获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrNrfont_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出02普通业务始发月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrNrfontForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrNrfontForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrNrfont.action'), 
				form: Ext.fly('exportmvrNrfontForm'),
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
closing.mvrNrfont.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrNrfontComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNrfont.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNrfontComboModel', {
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
		});

// 02普通业务始发月报表数据模型
Ext.define('Foss.closing.mvrNrfontModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'period',
						type : 'string'
					}, /* 期间 */
					{
						name : 'productCode',
						type : 'string'
					},/* 产品编码 */
					{
						name : 'customerCode',
						type : 'string'
					},/* 客户编码 */
					{
						name : 'customerName',
						type : 'string'
					},/* 客户名称 */
					{
						name : 'origOrgCode',
						type : 'string'
					},/* 始发部门编码 */
					{
						name : 'origOrgName',
						type : 'string'
					},/* 始发部门名称 */
					{
						name : 'destOrgCode',
						type : 'string'
					},/* 到达部门编码 */
					{
						name : 'destOrgName',
						type : 'string'
					},/* 到达部门名称 */
					{
						name : 'origUnifiedCode',
						type : 'string'
					},/* 始发部门标杆编码 */
					{
						name : 'destUnifiedCode',
						type : 'string'
					},/* 到达部门标杆编码 */
					{
						name : 'voucherTime',
						type : 'date'
					},/* 凭证生成时间 */
					{
						name : 'customerType',
						type : 'string'
					},/* 客户类型 */
					{
						name : 'unifiedSettlementType',
						type : 'string'
					},/* 统一结算类型*/
					{
						name : 'contractOrgCode',
						type : 'string'
					},/* 合同部门编码 */
					{
						name : 'contractOrgName',
						type : 'string'
					},/* 合同部门名称 */
					{
						name : 'deoCh',
						type : 'double'
					},/* 开单现金 */
					{
						name : 'deoCd',
						type : 'double'
					},/* 开单银行卡 */
					{
						name : 'detCh',
						type : 'double'
					},/* 开单现金 */
					{
						name : 'detCd',
						type : 'double'
					},/* 开单银行卡 */
					{
						name : 'uroOrigChNpod',
						type : 'double'
					},/* 还款现金未签收 */
					{
						name : 'uroOrigCdNpod',
						type : 'double'
					},/* 还款银行未签收 */
					{
						name : 'uroOrigChPod',
						type : 'double'
					},/* 还款现金已签收 */
					{
						name : 'uroOrigCdPod',
						type : 'double'
					},/* 还款银行已签收 */
					{
						name : 'urtOrigChNpod',
						type : 'double'
					},/* 还款现金未签收 */
					{
						name : 'urtOrigCdNpod',
						type : 'double'
					},/* 还款银行未签收 */
					{
						name : 'urtOrigChPod',
						type : 'double'
					},/* 还款现金已签收 */
					{
						name : 'urtOrigCdPod',
						type : 'double'
					},/* 还款银行已签收 */
					{
						name : 'urtDestChNpod',
						type : 'double'
					},/* 还款现金未签收 */
					{
						name : 'urtDestCdNpod',
						type : 'double'
					},/* 还款银行未签收 */
					{
						name : 'urtDestChPod',
						type : 'double'
					},/* 还款现金已签收 */
					{
						name : 'urtDestCdPod',
						type : 'double'
					},/* 还款银行已签收 */
					{
						name : 'custDrOch',
						type : 'double'
					},/* yin现金 */
					{
						name : 'custDrOcd',
						type : 'double'
					},/* 预收客户银行 */
					{
						name : 'custDrTch',
						type : 'double'
					},/* 预收客户现金 */
					{
						name : 'custDrTcd',
						type : 'double'
					},/* 预收客户银行 */
					{
						name : 'custDroWoOrigRcvtNpod',
						type : 'double'
					},/* 01预收客户冲02应收始发运费未签收 */
					{
						name : 'custDrtWoOrigRcvoNpod',
						type : 'double'
					},/* 02预收客户冲01应收始发运费未签收 */
					{
						name : 'custDroWoOrigRcvtPod',
						type : 'double'
					},/* 01预收客户冲02应收始发运费已签收 */
					{
						name : 'custDrtWoOrigRcvoPod',
						type : 'double'
					},/* 02预收客户冲01应收始发运费已签收 */
					{
						name : 'custDroPayApply',
						type : 'double'
					},/* 01始发退预收付款申请 */
					{
						name : 'custDrtPayApply',
						type : 'double'
					},/* 02始发退预收付款申请 */
					{
						name : 'custDroWoDestRcvtNpod',
						type : 'double'
					},/* 01预收客户冲02应收到付运费未签收 */
					{
						name : 'custDrtWoDestRcvtNpod',
						type : 'double'
					},/* 02预收客户冲02应收到付运费未签收 */
					{
						name : 'custDroWoDestRcvtPod',
						type : 'double'
					},/* 01预收客户冲02应收到付运费已签收 */
					{
						name : 'custDrtWoDestRcvtPod',
						type : 'double'
					},/* 02预收客户冲02应收到付运费已签收 */
					{
						name : 'custDrtWoOrigRcvtNpod',
						type : 'double'
					},/* 02预收客户冲02应收始发运费未签收 */
					{
						name : 'custDrtWoOrigRcvtPod',
						type : 'double'
					},/* 02预收客户冲02应收始发运费已签收 */
					{
						name : 'codPayWoDestRcvtPod',
						type : 'double'
					},/* 应付代收货款冲02应收到付运费已签收 */
					{
						name : 'codPayWoDestRcvtNpod',
						type : 'double'
					},/* 应付代收货款冲02应收到付运费未签收 */
					{
						name : 'codPayWoOrigRcvtPod',
						type : 'double'
					},/* 应付代收货款冲02应收始发运费已签收 */
					{
						name : 'codPayWoOrigRcvtNpod',
						type : 'double'
					},/* 应付代收货款冲02应收始发运费未签收 */
					{
						name : 'plCostWoDestRcvtPod',
						type : 'double'
					},/* 应付偏线代理成本冲02应收到付运费已签收 */
					{
						name : 'plCostWoDestRcvtNpod',
						type : 'double'
					},/* 应付偏线代理成本冲02应收到付运费未签收 */
					{
						name : 'plDrWoDestRcvtPod',
						type : 'double'
					},/* 预收偏线代理冲02应收到付运费已签收 */
					{
						name : 'plDrWoDestRcvtNpod',
						type : 'double'
					},/* 预收偏线代理冲02应收到付运费未签收 */
					{
						name : 'alDrWoDestRcvtPod',
						type : 'double'
					},/* 预收空运/快递代理冲02应收到付运费已签收 */
					{
						name : 'alDrWoDestRcvtNpod',
						type : 'double'
					},/* 预收空运/快递代理冲02应收到付运费未签收 */
					{
						name : 'alpwrWoDestRcvtPod',
						type : 'double'
					},/* 应付到达代理/快递代理成本冲02应收到付运费已签收 */
					{
						name : 'alpwrWoDestRcvtNpod',
						type : 'double'
					},/* 应付到达代理/快递代理成本冲02应收到付运费未签收 */
					{
						name : 'othPayWoDestRcvtPod',
						type : 'double'
					},/* 其他应付冲02应收到付运费已签收 */
					{
						name : 'othPayWoDestRcvtNpod',
						type : 'double'
					},/* 其他应付冲02应收到付运费未签收 */
					{
						name : 'podtCashCollectedFrt',
						type : 'double'
					},/* 签收时现付已收款金额_公布价运费 */
					{
						name : 'podtCashCollectedPup',
						type : 'double'
					},/* 签收时现付已收款金额_接货费 */
					{
						name : 'podtCashCollectedDel',
						type : 'double'
					},/* 签收时现付已收款金额_送货费 */
					{
						name : 'podtCashCollectedPkg',
						type : 'double'
					},/* 签收时现付已收款金额_包装费 */
					{
						name : 'podtCashCollectedCod',
						type : 'double'
					},/* 签收时现付已收款金额_代收货款手续费 */
					{
						name : 'podtCashCollectedDv',
						type : 'double'
					},/* 签收时现付已收款金额_保价费 */
					{
						name : 'podtCashCollectedOt',
						type : 'double'
					},/* 签收时现付已收款金额_其它费用 */
					{
						name : 'podtOrigRcvWoFrt',
						type : 'double'
					},/* 签收时始发应收已核销金额_公布价运费 */
					{
						name : 'podtOrigRcvWoPup',
						type : 'double'
					},/* 签收时始发应收已核销金额_接货费 */
					{
						name : 'podtOrigRcvWoDel',
						type : 'double'
					},/* 签收时始发应收已核销金额_送货费 */
					{
						name : 'podtOrigRcvWoPkg',
						type : 'double'
					},/* 签收时始发应收已核销金额_包装费 */
					{
						name : 'podtOrigRcvWoCod',
						type : 'double'
					},/* 签收时始发应收已核销金额_代收货款手续费 */
					{
						name : 'podtOrigRcvWoDv',
						type : 'double'
					},/* 签收时始发应收已核销金额_保价费 */
					{
						name : 'podtOrigRcvWoOt',
						type : 'double'
					},/* 签收时始发应收已核销金额_其它费用 */
					{
						name : 'podtOrigRcvNwoFrt',
						type : 'double'
					},/* 签收时始发应收未核销金额_公布价运费 */
					{
						name : 'podtOrigRcvNwoPup',
						type : 'double'
					},/* 签收时始发应收未核销金额_接货费 */
					{
						name : 'podtOrigRcvNwoDel',
						type : 'double'
					},/* 签收时始发应收未核销金额_送货费 */
					{
						name : 'podtOrigRcvNwoPkg',
						type : 'double'
					},/* 签收时始发应收未核销金额_包装费 */
					{
						name : 'podtOrigRcvNwoCod',
						type : 'double'
					},/* 签收时始发应收未核销金额_代收货款手续费 */
					{
						name : 'podtOrigRcvNwoDv',
						type : 'double'
					},/* 签收时始发应收未核销金额_保价费 */
					{
						name : 'podtOrigRcvNwoOt',
						type : 'double'
					},/* 签收时始发应收未核销金额_其它费用 */
					{
						name : 'podtDestRcvWoFrt',
						type : 'double'
					},/* 签收时到达应收已核销金额_公布价运费 */
					{
						name : 'podtDestRcvWoPup',
						type : 'double'
					},/* 签收时到达应收已核销金额_接货费 */
					{
						name : 'podtDestRcvWoDel',
						type : 'double'
					},/* 签收时到达应收已核销金额_送货费 */
					{
						name : 'podtDestRcvWoPkg',
						type : 'double'
					},/* 签收时到达应收已核销金额_包装费 */
					{
						name : 'podtDestRcvWoCod',
						type : 'double'
					},/* 签收时到达应收已核销金额_代收货款手续费 */
					{
						name : 'podtDestRcvWoDv',
						type : 'double'
					},/* 签收时到达应收已核销金额_保价费 */
					{
						name : 'podtDestRcvWoOt',
						type : 'double'
					},/*签收时到达应收已核销金额_其它费用*/
					{
						name : 'podtDestRcvNwoFrt',
						type : 'double'
					},/*签收时到达应收未核销金额_公布价运费*/
					{
						name : 'podtDestRcvNwoPup',
						type : 'double'
					},/*签收时到达应收未核销金额_接货费*/
					{
						name : 'podtDestRcvNwoDel',
						type : 'double'
					},/*签收时到达应收未核销金额_送货费*/
					{
						name : 'podtDestRcvNwoPkg',
						type : 'double'
					},/*签收时到达应收未核销金额_包装费*/
					{
						name : 'podtDestRcvNwoCod',
						type : 'double'
					},/*签收时到达应收未核销金额_代收货款手续费*/
					{
						name : 'podtDestRcvNwoDv',
						type : 'double'
					},/*签收时到达应收未核销金额_保价费*/
					{
						name : 'podtDestRcvNwoOt',
						type : 'double'
					},/*签收时到达应收未核销金额_其它费用*/
					{
						name : 'updtCashCollectedFrt',
						type : 'double'
					},/*反签收时现付已收款金额_公布价运费*/
					{
						name : 'updtCashCollectedPup',
						type : 'double'
					},/*反签收时现付已收款金额_接货费*/
					{
						name : 'updtCashCollectedDel',
						type : 'double'
					},/*反签收时现付已收款金额_送货费*/
					{
						name : 'updtCashCollectedPkg',
						type : 'double'
					},/*反签收时现付已收款金额_包装费*/
					{
						name : 'updtCashCollectedCod',
						type : 'double'
					},/*反签收时现付已收款金额_代收货款手续费*/
					{
						name : 'updtCashCollectedDv',
						type : 'double'
					},/*反签收时现付已收款金额_保价费*/
					{
						name : 'updtCashCollectedOt',
						type : 'double'
					},/*反签收时现付已收款金额_其它费用*/
					{
						name : 'updtOrigRcvWoFrt',
						type : 'double'
					},/*反签收时始发应收已核销金额_公布价运费*/
					{
						name : 'updtOrigRcvWoPup',
						type : 'double'
					},/*反签收时始发应收已核销金额_接货费*/
					{
						name : 'updtOrigRcvWoDel',
						type : 'double'
					},/*反签收时始发应收已核销金额_送货费*/
					{
						name : 'updtOrigRcvWoPkg',
						type : 'double'
					},/*反签收时始发应收已核销金额_包装费*/
					{
						name : 'updtOrigRcvWoCod',
						type : 'double'
					},/*反签收时始发应收已核销金额_代收货款手续费*/
					{
						name : 'updtOrigRcvWoDv',
						type : 'double'
					},/*反签收时始发应收已核销金额_保价费*/
					{
						name : 'updtOrigRcvWoOt',
						type : 'double'
					},/*反签收时始发应收已核销金额_其它费用*/
					{
						name : 'updtOrigRcvNwoFrt',
						type : 'double'
					},/*反签收时始发应收未核销金额_公布价运费*/
					{
						name : 'updtOrigRcvNwoPup',
						type : 'double'
					},/*反签收时始发应收未核销金额_接货费*/
					{
						name : 'updtOrigRcvNwoDel',
						type : 'double'
					},/*反签收时始发应收未核销金额_送货费*/
					{
						name : 'updtOrigRcvNwoPkg',
						type : 'double'
					},/*反签收时始发应收未核销金额_包装费*/
					{
						name : 'updtOrigRcvNwoCod',
						type : 'double'
					},/*反签收时始发应收未核销金额_代收货款手续费*/
					{
						name : 'updtOrigRcvNwoDv',
						type : 'double'
					},/*反签收时始发应收未核销金额_保价费*/
					{
						name : 'updtOrigRcvNwoOt',
						type : 'double'
					},/*反签收时始发应收未核销金额_其它费用*/
					{
						name : 'updtDestRcvWoFrt',
						type : 'double'
					},/*反签收时到达应收已核销金额_公布价运费*/
					{
						name : 'updtDestRcvWoPup',
						type : 'double'
					},/*反签收时到达应收已核销金额_接货费*/
					{
						name : 'updtDestRcvWoDel',
						type : 'double'
					},/*反签收时到达应收已核销金额_送货费*/
					{
						name : 'updtDestRcvWoPkg',
						type : 'double'
					},/*反签收时到达应收已核销金额_包装费*/
					{
						name : 'updtDestRcvWoCod',
						type : 'double'
					},/*反签收时到达应收已核销金额_代收货款手续费*/
					{
						name : 'updtDestRcvWoDv',
						type : 'double'
					},/*反签收时到达应收已核销金额_保价费*/
					{
						name : 'updtDestRcvWoOt',
						type : 'double'
					},/*反签收时到达应收已核销金额_其它费用*/
					{
						name : 'updtDestRcvNwoFrt',
						type : 'double'
					},/*反签收时到达应收未核销金额_公布价运费*/
					{
						name : 'updtDestRcvNwoPup',
						type : 'double'
					},/*反签收时到达应收未核销金额_接货费*/
					{
						name : 'updtDestRcvNwoDel',
						type : 'double'
					},/*反签收时到达应收未核销金额_送货费*/
					{
						name : 'updtDestRcvNwoPkg',
						type : 'double'
					},/*反签收时到达应收未核销金额_包装费*/
					{
						name : 'updtDestRcvNwoCod',
						type : 'double'
					},/*反签收时到达应收未核销金额_代收货款手续费*/
					{
						name : 'updtDestRcvNwoDv',
						type : 'double'
					},/*反签收时到达应收未核销金额_保价费*/
					{
						name : 'updtDestRcvNwoOt',
						type : 'double'
					},
					{name : 'popWoDrtPod',type : 'double'},
				    {name : 'popWoDrtNpod',type : 'double'}
			]
		});

//始发应收Store
Ext.define('Foss.closing.mvrNrfontStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrNrfontModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrNrfont.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrNrfontEntityVo.mvrNrfontEntityResultDto.mvrNrfontEntityList',
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
Ext.define('Foss.closing.mvrNrfontQueryForm', {
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
				name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.period',
				fieldLabel : '期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfont.getLastMonthPeriod(),
				store : closing.mvrNrfont.getComboPeriodStore(),
				columnWidth : .33*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrNrfont.PeriodStore'),
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
				store:Ext.create('Foss.mvrNrfont.CustomerTypeStore'),
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
								Ext.getCmp('closing.mvrNrfont.commoncustomerselector').show();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfont.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='02'){
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfont.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfont.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='03'){
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfont.commoncustomerselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfont.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='04'){
								// 航空公司
								Ext.getCmp('closing.mvrNrfont.commonairlinesselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfont.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='05'){
								// 快递代理
								Ext.getCmp('closing.mvrNrfont.commonLdpAgencyCompanySelector').show();
								// 客户信息
								Ext.getCmp('closing.mvrNrfont.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrNrfont.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrNrfont.commonairlinesselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrNrfont.commonairagencycompanyselector').hide();
							}
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :'客户信息',
		    	name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
		    	singlePeopleFlag : 'Y',
		    	id:'closing.mvrNrfont.commoncustomerselector',
		    	columnWidth:.33,
				isPaging:true // 分页
		    }
		    ,{	
		    	xtype:'commonallagentselector',
		    	fieldLabel : '空运代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfont.commonairagencycompanyselector',
				isPaging:true ,// 分页
				hidden:true
		    },
		    {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :'偏线代理公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfont.commonvehagencycompselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonairlinesselector',
		    	fieldLabel :'航空公司',
		    	columnWidth:.33,
		    	name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfont.commonairlinesselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	columnWidth:.33,
		    	name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
		    	id:'closing.mvrNrfont.commonLdpAgencyCompanySelector',
				isPaging:true ,// 分页
				hidden:true
		    }/*, {
				xtype : 'commoncustomerselector',
				fieldLabel : '客户',
				name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.customerCode',
				columnWidth : .33
			}*/, {
				xtype : 'combo',
				name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.productCodeList',
				fieldLabel : '运输性质',
				forceSelection : true,
				multiSelect:true,
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrNrfont.getComboProductTypeStore().first()
						.get('code'),
				store : closing.mvrNrfont.getComboProductTypeStore(),
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrNrfontEntityVo.mvrNrfontEntityQueryDto.destOrgCode',
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
							handler : closing.mvrNrfont.reset
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
								  closing.mvrNrfont.query(form,me)
							  }
						}]
			}]
		})

// 02普通业务始发月报表查询Grid
Ext.define('Foss.closing.mvrNrfontQueryGrid', {
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
				handler:closing.mvrNrfont.exportmvrNrfont,
				disabled:!closing.mvrNrfont.isPermission('/stl-web/closing/exportMvrNrfont.action'),
				hidden:!closing.mvrNrfont.isPermission('/stl-web/closing/exportMvrNrfont.action')
			});
		}
		return me.exportButton;
	},
	columns : [{
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
			text : '到达部门编码',
			width:110,
			dataIndex : 'destOrgCode'
		}, {
			text : '到达部门名称',
			width:110,
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
			text : '合同部门编码',
			width:110,
			dataIndex : 'contractOrgCode'
		}, {
			text : '合同部门名称',
			width:110,
			dataIndex : 'contractOrgName'
		} ]
	}, {
		text : '开单运单【01】',
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
		text : '开单运单【02】',
		columns : [ {
			text : '开单现金',
			width : 180,
			align : 'right',
			dataIndex : 'detCh'
		}, {
			text : '开单银行卡',
			width : 180,
			align : 'right',
			dataIndex : 'detCd'
		} ]
	}, {
		text : '还款运单总运费（月结临时欠款网上支付）【01】',
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
		text : '还款运单总运费（月结临时欠款网上支付）【02】',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtOrigChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtOrigCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtOrigChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtOrigCdPod'
		} ]
	}, {
		text : '还款运单总运费（到付）【02】',
		columns : [ {
			text : '还款现金未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtDestChNpod'
		}, {
			text : '还款银行未签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtDestCdNpod'
		}, {
			text : '还款现金已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtDestChPod'
		}, {
			text : '还款银行已签收',
			width : 180,
			align : 'right',
			dataIndex : 'urtDestCdPod'
		} ]
	}, {
		text : '签收运单【02】',
		columns : [ {
			text : '签收时现付已收款金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podtCashCollectedOt'
			} ]
		}, {
			text : '签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvWoOt'
			} ]
		}, {
			text : '签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podtOrigRcvNwoOt'
			} ]
		}, {
			text : '签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvWoOt'
			} ]
		}, {
			text : '签收时到达应收未核销金额',
			columns : [{
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'podtDestRcvNwoOt'
			} ]
		} ]
	}, {
		text : '反签收运单【02】',
		columns : [ {
			text : '反签收时现付已收款金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updtCashCollectedOt'
			} ]
		}, {
			text : '反签收时始发应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvWoOt'
			} ]
		}, {
			text : '反签收时始发应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updtOrigRcvNwoOt'
			} ]
		}, {
			text : '反签收时到达应收已核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvWoOt'
			} ]
		}, {
			text : '反签收时到达应收未核销金额',
			columns : [ {
				text : '公布价运费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoFrt'
			}, {
				text : '接货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoPup'
			}, {
				text : '送货费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoDel'
			}, {
				text : '包装费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoPkg'
			}, {
				text : '保价费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoDv'
			}, {
				text : '代收货款手续费',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoCod'
			}, {
				text : '其他费用',
				width : 180,
				align : 'right',
				dataIndex : 'updtDestRcvNwoOt'
			} ]
		} ]
	}, {
		text : '预收客户',
		columns : [{
			text:'预收客户【01】',
			columns:[{
				text : '预收客户现金',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOch'
			}, {
				text : '预收客户银行',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOcd'
			}]
		},{
			text:'预收客户【02】',
			columns:[{
				text : '预收客户现金',
				width : 180,
				align : 'right',
				dataIndex : 'custDrTch'
			}, {
				text : '预收客户银行',
				width : 180,
				align : 'right',
				dataIndex : 'custDrTcd'
			}]
		}, {
			text : '01预收客户冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoDestRcvtNpod'
		}, {
			text : '02预收客户冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoDestRcvtNpod'
		}, {
			text : '01预收客户冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDroWoDestRcvtPod'
		}, {
			text : '02预收客户冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoDestRcvtPod'
		}, {
			text : '02预收客户冲02应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoOrigRcvtNpod'
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
			text : '02预收客户冲02应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtWoOrigRcvtPod'
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
		}, {
			text : '02始发退预收付款申请',
			width : 180,
			align : 'right',
			dataIndex : 'custDrtPayApply'
		} ]
	}, {
		text : '代收货款',
		columns : [ {
			text : '应付代收货款冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoDestRcvtPod'
		}, {
			text : '应付代收货款冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoDestRcvtNpod'
		}, {
			text : '应付代收货款冲02应收始发运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoOrigRcvtPod'
		}, {
			text : '应付代收货款冲02应收始发运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'codPayWoOrigRcvtNpod'
		} ]
	},{
		text : '偏线代理成本',
		columns : [ {
			text : '应付偏线代理成本冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvtPod'
		}, {
			text : '应付偏线代理成本冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plCostWoDestRcvtNpod'
		} ]
	}, {
		text : '预收偏线代理',
		columns : [ {
			text : '预收偏线代理冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvtPod'
		}, {
			text : '预收偏线代理冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'plDrWoDestRcvtNpod'
		} ]
	}, {
		text : '预收空运/快递代理',
		columns : [ {
			text : '预收空运/快递代理冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'alDrWoDestRcvtPod'
		}, {
			text : '预收空运/快递代理冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'alDrWoDestRcvtNpod'
		} ]
	}, {
		text : '空运/快递代理应付冲应收',
		columns : [ {
			text : '应付到达代理/快递代理成本冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'alpwrWoDestRcvtPod'
		}, {
			text : '应付到达代理/快递代理成本冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'alpwrWoDestRcvtNpod'
		}, {
			text : '其他应付冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'othPayWoDestRcvtPod'
		}, {
			text : '其他应付冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'othPayWoDestRcvtNpod'
		} ]
	}, {
		text : '偏线应收冲应付',
		columns : [ {
			text : '其他应付冲02应收到付运费已签收',
			width : 180,
			align : 'right',
			dataIndex : 'popWoDrtPod'
		}, {
			text : '其他应付冲02应收到付运费未签收',
			width : 180,
			align : 'right',
			dataIndex : 'popWoDrtNpod'
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrNrfontStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout = 600000;

	if (Ext.getCmp('T_closing-mvrNrfont_content')) {
		return;
	}

	closing.mvrNrfont.mvrNrfontTotalRecord = Ext.create('Foss.closing.mvrNrfontModel');
	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrNrfontQueryForm');
	
	//显示grid
	var queryGrid = Ext.create('Foss.closing.mvrNrfontQueryGrid');
	
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
				id : 'T_closing-mvrNrfont_content',
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
				renderTo : 'T_closing-mvrNrfont-body'
			});
})