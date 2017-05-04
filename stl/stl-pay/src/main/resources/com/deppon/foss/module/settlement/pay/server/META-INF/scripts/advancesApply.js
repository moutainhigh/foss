/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.billAdvancesApplyConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(pay.addAdvance.i18n('foss.stl.pay.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


/**
 * 新增空运预付款信息
 * @returns {Boolean}
 */
pay.advancesApply = function(){

	var form  = this.up('form').getForm();
	var bt=this;

	var yesFn=function(){
		
		
		
		bt.disable(false);
		
		setTimeout(function() {
			bt.enable(true);
		}, 10000);
		
		//费用承担部门就是付款部门
		var paymentOrgCode = form.findField('paymentOrgCode').getValue();
		//费用承担部门就是付款部门名称
		var paymentOrgName = form.findField('paymentOrgCode').getRawValue();
		
		//金额
		var amount = form.findField('amount').getValue();
		
		//发票抬头
		var invoiceTitle = form.findField('invoiceTitle').getValue();
		
		//收款客户名称
		var customerCode  = form.findField('customerCode').getValue();
		
		//收款客户名称
		var customerName= form.findField('customerCode').getRawValue();
		
		// 开户名
		var payeeCode=form.findField('payeeCode').getValue();
		
		var payeeName  = form.findField('payeeName').getValue();
		
		//银行账号
		var accountNo   = form.findField('accountNo').getValue();
		
		//省份
		var provinceName  = form.findField('provinceName').getValue();
		
		var provinceCode = form.findField('provinceCode').getValue();
		
		//城市 
		var cityCode=form.findField('cityCode').getValue();
		var cityName= form.findField('cityName').getValue();
		
		var bankHqCode=form.findField('bankHqCode').getValue();
		
		//开户银行
		var bankHqName= form.findField('bankHqName').getValue();
		
		//开户支行
		var bankBranchName= form.findField('bankBranchName').getValue();
		
		//开户支行
		var bankBranchCode= form.findField('bankBranchCode').getValue();
		
		//收款人手机
		var mobilePhone= form.findField('mobilePhone').getValue();
		
		//最迟汇款日期
		var lastpaymentTime= form.findField('lastpaymentTime').getValue();
		
		//账户性质,1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.
		var accountType=form.findField('accountType').getValue();
		
		if(form.isValid()){
			if(amount<=0){
				Ext.Msg.alert(pay.addAdvance.i18n('foss.stl.pay.common.alert'),pay.addAdvance.i18n('foss.stl.pay.advancesApply.receivableAmountErrorWarning'));
				return false;
			}

			var params = {
					'billAdvanceVo.billAdvanDto.paymentOrgCode':paymentOrgCode,
					'billAdvanceVo.billAdvanDto.paymentOrgName':paymentOrgName,
					'billAdvanceVo.billAdvanDto.invoiceTitle':invoiceTitle,
					'billAdvanceVo.billAdvanDto.amount':amount,
					'billAdvanceVo.billAdvanDto.bankHqName':bankHqName,
					'billAdvanceVo.billAdvanDto.bankHqCode':bankHqCode,
					'billAdvanceVo.billAdvanDto.accountNo':accountNo,
					'billAdvanceVo.billAdvanDto.provinceName':provinceName,
					'billAdvanceVo.billAdvanDto.provinceCode':provinceCode,
					'billAdvanceVo.billAdvanDto.cityName':cityName,
					'billAdvanceVo.billAdvanDto.cityCode':cityCode,
					'billAdvanceVo.billAdvanDto.payeeName':payeeName,
					'billAdvanceVo.billAdvanDto.payeeCode':payeeCode,
					'billAdvanceVo.billAdvanDto.bankBranchName':bankBranchName,
					'billAdvanceVo.billAdvanDto.bankBranchCode':bankBranchCode,
					'billAdvanceVo.billAdvanDto.mobilePhone':mobilePhone,
					'billAdvanceVo.billAdvanDto.lastpaymentTime':lastpaymentTime,
					'billAdvanceVo.billAdvanDto.customerCode':customerCode,
					'billAdvanceVo.billAdvanDto.customerName':customerName,
					'billAdvanceVo.billAdvanDto.accountType':accountType
			}
			
		
		//锁定预付单
		myMask =Ext.getCmp('Foss.billAdvanceApplys.LoadMaskAdd_ID');
		if(myMask==null){
			myMask = new Ext.LoadMask(Ext.getCmp('T_pay-billAdvanceApplys_content'), {
	    		id:'Foss.billAdvanceApplys.LoadMaskAdd_ID',
				msg:pay.addAdvance.i18n('foss.stl.pay.advancesApply.saveAdvancesApplyMask'),
			    removeMask : true// 完成后移除
			});
		}
		myMask.show();

		 Ext.Ajax.request({
		 	url : pay.realPath('addBillAdvanceApplys.action'),
			params:params,
			actionMethods:'post',
			submitEmptyText:false,
			success:function(response){
				var json=Ext.decode(response.responseText);
				Ext.MessageBox.alert(pay.addAdvance.i18n('foss.stl.pay.common.alert'), json.message);
				
				form.reset();//提交成功之后，清空界面
				var applyCodeCombSelector=form.findField('paymentOrgCode');
				var recInvoiceTitleSelector=form.findField('invoiceTitle');
				
				if(!Ext.isEmpty(stl.currentDept.code)){
					applyCodeCombSelector.setCombValue(stl.currentDept.name,stl.currentDept.code);
				}
				
				if(!Ext.isEmpty(FossUserContext. getCurrentDept().subsidiaryCode)){
					recInvoiceTitleSelector.setCombValue(FossUserContext.getCurrentDept().subsidiaryName,FossUserContext. getCurrentDept().subsidiaryCode);
				}
				myMask.hide();
				bt.enable(true);
				
			},
			exception:function(response){
				myMask.hide();
				bt.enable(true);
				var json=Ext.decode(response.responseText);
				Ext.MessageBox.alert(pay.addAdvance.i18n('foss.stl.pay.common.alert'), json.message);
			}
		 });
		}else{			
			Ext.MessageBox.alert(pay.addAdvance.i18n('foss.stl.pay.common.alert'),pay.addAdvance.i18n('foss.stl.pay.common.validateFailAlert'));
			return false;
		}
	};
		
		var noFn=function(){
		 	return false;
		};
		
		
		pay.billAdvancesApplyConfirmAlert(pay.addAdvance.i18n('foss.stl.pay.advancesApply.isAddAdvancesApply'),yesFn,noFn);
}

//新增预付款重置
pay.advanceReset=function(){
	
	var form  = this.up('form').getForm();
	this.up('form').getForm().reset();
	
	var applyCodeCombSelector=form.findField('paymentOrgCode');
	var recInvoiceTitleSelector=form.findField('invoiceTitle');
	
	if(!Ext.isEmpty(stl.currentDept.code)){
		applyCodeCombSelector.setCombValue(stl.currentDept.name,stl.currentDept.code);
	}
	
	if(!Ext.isEmpty(FossUserContext. getCurrentDept().subsidiaryCode)){
		recInvoiceTitleSelector.setCombValue(FossUserContext.getCurrentDept().subsidiaryName,FossUserContext. getCurrentDept().subsidiaryCode);
	}
}



Ext.define('Foss.stlbilladvancesApplys.stlbilladvancesApplysPanel',{
	extend:'Ext.form.Panel',
	frame:true,
	title: pay.addAdvance.i18n('foss.stl.pay.advancesApply.airAdvancesApplys'),
	bodyCls: 'autoHeight',
	cls : 'innerTabPanel',
	layout:'column',
	    items:[{	
	    	xtype:'dynamicorgcombselector',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.paymentOrgCode'),
			name:'paymentOrgCode',
			allowBlank:false,
			columnWidth:.3
	    },{	
	    	xtype:'numberfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.amount'),
			name:'amount',
			decimalPrecision:0,
			allowBlank:false,
			columnWidth:.3,
			minValue:1,
			negativeText:pay.addAdvance.i18n('foss.stl.pay.advancesApply.amountErrorWarning'),
			maxValue:9999999,
			maxText:pay.addAdvance.i18n('foss.stl.pay.advancesApply.amountMaxWarning')
	    }
	   , {
	 		xtype:'commonsubsidiaryselector',
	 		fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.invoiceTitle'),
	 		readOnly:false,
	 		name:'invoiceTitle',
	 		allowBlank:false
	 	}
	    ,{
	    	xtype: 'commonairlinesselector',
	    	fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.customerCode'),
	    	name:'customerCode',
	    	allowBlank:false,
	    	columnWidth:.3
	    }
	    ,{
	    	xtype:'commonpayeeinfoselector',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.accountNo'),
			name:'accountNo',
			operatorId:FossUserContext.getCurrentUserEmp().empCode,
			allowBlank:false,
			exactQuery:'Y', //精确查找
			readOnly:false,
			disabled:false,
			columnWidth:.3,
			listeners:{
				'change':function(th,newValue,oldValue){
					//获取record
					var record = th.findRecordByValue(newValue);
					//获取form表单
					var form = this.up('form').getForm();
					if(!record){
						form.findField('provinceName').setValue(null);
						form.findField('provinceCode').setValue(null);
						form.findField('cityName').setValue(null);
						form.findField('cityCode').setValue(null);
						form.findField('bankHqCode').setValue(null);
						form.findField('bankHqName').setValue(null);
						form.findField('bankBranchName').setValue(null);
						form.findField('bankBranchCode').setValue(null);
						form.findField('accountType').setValue(null);
						form.findField('payeeName').setValue(null);//开户名称
//						form.findField('mobilePhone').setValue(null);
//						form.findField('customerCode').setValue(null);//收款人名称
						
					}
				},
				'select':function(th,records){
					//获取选中记录
					var record = records[0];
					//获取form表单
					var form = this.up('form').getForm();
					//判断是否选中记录为空
					if(!Ext.isEmpty(record)){
						
						//省份
						if(!Ext.isEmpty(record.get('accountbankstateName'))){
							form.findField('provinceName').setValue(record.get('accountbankstateName'));
						}else{
							form.findField('provinceName').setValue('上海');
						}
						//省份名称
						if(!Ext.isEmpty(record.get('accountbankstateCode'))){
							form.findField('provinceCode').setValue(record.get('accountbankstateCode'));
						}else{
							form.findField('provinceCode').setValue('上海');
						}
						
						//城市编码
						if(!Ext.isEmpty(record.get('accountbankcityCode'))){
							form.findField('cityCode').setValue(record.get('accountbankcityCode'));
						}else{
							form.findField('cityCode').setValue('上海');
						}
						
						
						//城市名称
						if(!Ext.isEmpty(record.get('accountbankcityName'))){
							form.findField('cityName').setValue(record.get('accountbankcityName'));
						}else{
							form.findField('cityName').setValue('上海');
						}
						
						//开户行名称
						if(!Ext.isEmpty(record.get('accountbankName'))){
							form.findField('bankHqName').setValue(record.get('accountbankName'));
						}else{
							form.findField('bankHqName').setValue('中国银行');
						}
						
						
							//开户行编码
							if(!Ext.isEmpty(record.get('accountbankCode'))){
								form.findField('bankHqCode').setValue(record.get('accountbankCode'));
							}else{
								form.findField('bankHqCode').setValue(null);
							}
						
						
						
						//开户支行名称
						if(!Ext.isEmpty(record.get('accountbranchbankName'))){
							form.findField('bankBranchName').setValue(record.get('accountbranchbankName'));
						}else{
							form.findField('bankBranchName').setValue("徐泾支行");
						}
						
						
						
						//开户支行编码
						if(!Ext.isEmpty(record.get('accountbranchbankCode'))){
							form.findField('bankBranchCode').setValue(record.get('accountbranchbankCode'));
						}else{
							form.findField('bankBranchCode').setValue(null);
						}
						
						
						//账户类型、对公、对私
						if(!Ext.isEmpty(record.get('accountType'))){
							form.findField('accountType').setValue(record.get('accountType'));
						}else{
							form.findField('accountType').setValue(null);
						}

						//开户名
						if(!Ext.isEmpty(record.get('beneficiaryName'))){
							form.findField('payeeName').setValue(record.get('beneficiaryName'));
						}
						else{
							form.findField('payeeName').setValue(null);
						}
						
//						//联系方式
						if(!Ext.isEmpty(record.get('payeeNo'))){
								form.findField('payeeCode').setValue(record.get('payeeNo'))
						}
						else{
							form.findField('payeeCode').setValue(null);
						}
					}
				}
			}
	    }
	    ,{
	    	xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.provinceCode'),
			name:'provinceCode',
			columnWidth:.3,
			hidden:true
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.provinceName'),
			name:'provinceName',
			columnWidth:.3
			
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.cityCode'),
			name:'cityCode',
			columnWidth:.3,
			hidden:true,
			allowBlank:false
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.cityName'),
			name:'cityName',
			columnWidth:.3
		}
		,{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.bankHqCode'),
			columnWidth:.3,
			name:'bankHqCode',
			hidden:true
		}
		,
		{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.bankHqName'),
			columnWidth:.3,
			name:'bankHqName'
			
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.bankBranchName'),
			columnWidth:.3,
			name:'bankBranchName'
			
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.bankBranchCode'),
			columnWidth:.3,
			name:'bankBranchCode',
			hidden:true
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.accountType'),
			columnWidth:.3,
			name:'accountType',
			hidden:true
		},{
			xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.payeeName'),
			columnWidth:.3,
			name:'payeeName'
			
		}
		,{
	    	xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.payeeCode'),
			name:'payeeCode',
			columnWidth:.3,
			hidden:true
	    },
		
		,{
	    	xtype:'textfield',
			fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.payeeMobilePhone'),
			name:'mobilePhone',
			allowBlank:false,
			regex:/^1[3|4|5|8][0-9]\d{8}$/,
			regexText:pay.addAdvance.i18n('foss.stl.pay.advancesApply.mobilePhoneWarning'),
			columnWidth:.3
	    }
		,
	    {
	    	xtype:'datefield',
	    	name:'lastpaymentTime',
	    	fieldLabel:pay.addAdvance.i18n('foss.stl.pay.advancesApply.lastpaymentTime'),
	    	value:new Date(),
	    	minValue:new Date(),
	    	minText:pay.addAdvance.i18n('foss.stl.pay.advancesApply.dateCannotLessThanCurrentDate'),
	    	format:'Y-m-d',
	    	allowBlank:false,
	    	columnWidth:.3
	    },
	      {
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.addAdvance.i18n('foss.stl.pay.common.reset'),
//				cls:'yellow_button',
				columnWidth:.08,
				handler:pay.advanceReset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.74,
				html: '&nbsp;'
			},{
				text:pay.addAdvance.i18n('foss.stl.pay.common.commit'),
				disabled:!pay.addAdvance.isPermission('/stl-web/pay/addBillAdvanceApplys.action'),
				hidden:!pay.addAdvance.isPermission('/stl-web/pay/addBillAdvanceApplys.action'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:pay.advancesApply
			}
	    ]
	   }]
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var StladvancesApplysPanel = Ext.create('Foss.stlbilladvancesApplys.stlbilladvancesApplysPanel');
	
	
	Ext.create('Ext.panel.Panel',{
		
		id: 'T_pay-billAdvanceApplys_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StladvancesApplysPanel],
		renderTo: 'T_pay-billAdvanceApplys-body'
		,listeners:{
			'boxready':function(th){
				//获取当前用户权限
				var roles = stl.currentUserDepts;
				//费用承担部门				
				var applyCodeCombSelector=StladvancesApplysPanel.items.items[0];
				//发票抬头				
				var recInvoiceTitleSelector=StladvancesApplysPanel.items.items[2];
				/**
				 * 获取用户角色，判断部门是否可编辑 
				 */
				for(var i=0;i<roles.length;i++){
					//此处需要判断是否为收银员
					if(roles[i]=='1'){
						if(!Ext.isEmpty(stl.currentDept.code)){
							applyCodeCombSelector.setCombValue(stl.currentDept.name,stl.currentDept.code);
						}
						
						if(!Ext.isEmpty(FossUserContext. getCurrentDept().subsidiaryCode)){
							recInvoiceTitleSelector.setCombValue(FossUserContext.getCurrentDept().subsidiaryName,FossUserContext. getCurrentDept().subsidiaryCode);
						}
					}	
				} 
			}
		}
	});
});