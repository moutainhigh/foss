/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.billDepositReceivedAdd.billAddBillDepositReceivedConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

pay.billDepositReceivedAdd.DEPOSIT_RECEIVED_ACCOUNTING = "DEA";
pay.billDepositReceivedAdd.DEPOSIT_RECEIVED_CASHIER = "DEC";

/**
 * 新增预收单信息
 * @returns {Boolean}
 */
pay.billDepositReceivedAdd.addBillDepositReceived = function(form,cashierAccounting,me){
	var generatingOrgName = form.findField('generatingOrgDetail').getRawValue();
	var generatingOrgCodeLast = form.findField('generatingOrgDetail').lastValue;
	var customerDetialCode = form.findField('customerDetail').getValue();
	var customerDetialName = form.findField('customerDetail').getRawValue();
	var agencyDetialPACode = form.findField('agencyDetialPA').getValue();
	var agencyDetialPAName = form.findField('agencyDetialPA').getRawValue();
	var agencyDetialAACode = form.findField('agencyDetialAA').getValue();
	var agencyDetialAAName = form.findField('agencyDetialAA').getRawValue();
	var paymentType = form.findField('paymentType').getValue();
	var transportType = form.findField('transportType').getValue();
	var remitNo = form.findField('remitNo').getValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	var customerCode = "";
	if(customerDetialCode != "" && customerDetialCode != null){
		customerCode = customerDetialCode;
	}else if(agencyDetialPACode != "" && agencyDetialPACode != null){
		customerCode = agencyDetialPACode;
	}else if(agencyDetialAACode != "" && agencyDetialAACode != null){
		customerCode = agencyDetialAACode;
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.customerCodeNotNull'));
		return false;
	}
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else if(agencyDetialPAName != "" && agencyDetialPAName != null){
		customerName = agencyDetialPAName;
	}else if(agencyDetialAAName != "" && agencyDetialAAName != null){
		customerName = agencyDetialAAName;
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.customerNameNotNull'));
		return false;
	}
	var generatingOrgCode = "";
	if(generatingOrgCodeLast == stl.currentDept.name){
		generatingOrgCode = stl.currentDept.code;
	}else{
		generatingOrgCode = generatingOrgCodeLast;
	}
	if(paymentType == "" || paymentType == null){
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.paymentTypeText'));
		return false;
	}
	if(paymentType == "TT" || paymentType == "NT"){
		if(remitNo == ""){
			Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
					pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.remitNoNotNull'));
			return false;
		}
    	
    	if(amount > pay.billDepositReceivedAdd.noCancelAmount){
    		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
    				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.amountNotGreaterThanRemainingAmount'));
    		return false;
    	}
	}
	if(paymentType == "CD" || paymentType == "CH" || paymentType == "OL"){
    	if(amount <= 0){
    		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
    				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.amountGreaterThanZero'));
    		return false;
    	}
	}
	if(notes.length >= 300){
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.notesNotGreaterThanZero300'));
		return false;
	}
	if(form.isValid()){
		var params = {
				'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode':generatingOrgCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgName':generatingOrgName,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode':customerCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.customerName':customerName,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType':paymentType,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.transportType':transportType,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.remitNo':remitNo,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.amount':amount,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.notes':notes,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.cashierAccounting':cashierAccounting
		}
		var yesFn=function(){
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url : pay.realPath('addBillDepositReceived.action'),
				params:params,
				success:function(response){
					form.reset();
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
							pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.saveSuccess'));
					me.enable(true);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
					me.enable(true);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
					me.enable(true);
				}
			});
		};
		var noFn=function(){
		 	return false;
		};
		pay.billDepositReceivedAdd.billAddBillDepositReceivedConfirmAlert(
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

pay.billDepositReceivedAdd.addBillDepositReceivedAccount = function(form,cashierAccounting,me){
	var generatingOrgName = form.findField('generatingOrgDetail').getRawValue();
	var generatingOrgCodeLast = form.findField('generatingOrgDetail').lastValue;
	var customerDetialCode = form.findField('customerDetail').getValue();
	var customerDetialName = form.findField('customerDetail').getRawValue();
	var agencyDetialPACode = form.findField('agencyDetialPA').getValue();
	var agencyDetialPAName = form.findField('agencyDetialPA').getRawValue();
	var agencyDetialAACode = form.findField('agencyDetialAA').getValue();
	var agencyDetialAAName = form.findField('agencyDetialAA').getRawValue();
	var paymentType = form.findField('paymentType').getValue();
	var transportType = form.findField('transportType').getValue();
	var remitNo = form.findField('remitNo').getValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	var customerCode = "";
	if(customerDetialCode != "" && customerDetialCode != null){
		customerCode = customerDetialCode;
	}else if(agencyDetialPACode != "" && agencyDetialPACode != null){
		customerCode = agencyDetialPACode;
	}else if(agencyDetialAACode != "" && agencyDetialAACode != null){
		customerCode = agencyDetialAACode;
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.customerCodeNotNull'));
		return false;
	}
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else if(agencyDetialPAName != "" && agencyDetialPAName != null){
		customerName = agencyDetialPAName;
	}else if(agencyDetialAAName != "" && agencyDetialAAName != null){
		customerName = agencyDetialAAName;
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.customerNameNotNull'));
		return false;
	}
	var generatingOrgCode = "";
	if(generatingOrgCodeLast == stl.currentDept.name){
		generatingOrgCode = stl.currentDept.code;
	}else{
		generatingOrgCode = generatingOrgCodeLast;
	}
	if(paymentType == "" || paymentType == null){
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.paymentTypeText'));
		return false;
	}
	if(paymentType == "TT" || paymentType == "NT"){
		if(remitNo == ""){
			Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
					pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.remitNoNotNull'));
			return false;
		}
    	
    	if(amount > pay.billDepositReceivedAdd.noCancelAmount){
    		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
    				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.amountNotGreaterThanRemainingAmount'));
    		return false;
    	}
	}
	if(paymentType == "CD" || paymentType == "CH" || paymentType == "OL"){
    	if(amount <= 0){
    		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
    				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.amountGreaterThanZero'));
    		return false;
    	}
	}
	if(notes.length >= 300){
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.notesNotGreaterThanZero300'));
		return false;
	}
	if(form.isValid()){
		var params = {
				'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode':generatingOrgCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgName':generatingOrgName,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode':customerCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.customerName':customerName,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType':paymentType,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.transportType':transportType,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.remitNo':remitNo,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.amount':amount,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.notes':notes,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.cashierAccounting':cashierAccounting
		}
		var yesFn=function(){
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url : pay.realPath('addBillDepositReceivedAccount.action'),
				params:params,
				success:function(response){
					form.reset();
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
							pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.saveSuccess'));
					me.enable(true);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
					me.enable(true);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
					me.enable(true);
				}
			});
		};
		var noFn=function(){
		 	return false;
		};
		pay.billDepositReceivedAdd.billAddBillDepositReceivedConfirmAlert(
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
				pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 收款方式model
 */
Ext.define('Foss.billDepositReceivedAdd.PaymentTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * 收款方式store
 */
Ext.define('Foss.billDepositReceivedAdd.PaymentTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.billDepositReceivedAdd.PaymentTypeModel',
	data:{
		'items':[
		    {name:"全部",value:""},
			{name:"现金",value:'CH'},
			{name:"银行卡",value:'CD'},
			{name:"电汇",value:'TT'},
			{name:"支票",value:'NT'},
			{name:"网上支付",value:'OL'}
		]
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
 * 客户类型model
 */
Ext.define('Foss.billDepositReceivedAdd.TransportTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * 客户类型store
 */
Ext.define('Foss.billDepositReceivedAdd.TransportTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.billDepositReceivedAdd.TransportTypeModel',
	data:{
		'items':[
			{name:"专线客户",value:'LC'},
			{name:"偏线代理",value:'PA'},
			{name:"空运代理",value:'AA'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});
Ext.define('Foss.StladdBillDepositReceived.StladdBillDepositReceivedInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	width : 1130,
	height : 600,
	items : [ {
		title: '<span class="statementBill_tabHead">&nbsp;&nbsp;'+pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.saveDepositReceivedTitle')+'&nbsp;&nbsp;</span>',
		tabConfig: {
			width: 200
		},
		width: '200',
        layout:'fit',
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'20 10 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
		    items:[{
		    	xtype:'dynamicorgcombselector',
		    	fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.generatingOrgDetail'),
				name:'generatingOrgDetail',
				allowBlank:false,
				disabled:true,
				value:stl.currentDept.name,
				columnWidth:.4,
				labelWidth:85,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
			},{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
		    	xtype: 'combobox',
				fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.transportType'),
				name:'transportType',
				labelWidth:85,
		    	allowBlank:false,
		    	editable:false,
		    	store:Ext.create('Foss.billDepositReceivedAdd.TransportTypeStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:'LC',
		    	columnWidth:.4,
		    	listeners:{
        		    	'change':function(th,newValue,oldValue){
    					//获取表单等控件
    					var form,//表单
    						customerDetail,
    						agencyDetialPA,
    						agencyDetialAA;
    					//获取表单	
    					form= this.up('form').getForm();
    					//获取下面组件
    					customerDetail = form.findField('customerDetail');
    					agencyDetialPA = form.findField('agencyDetialPA');
    					agencyDetialAA = form.findField('agencyDetialAA');
    					//当选择电汇和支票时，隐藏汇款编号控件
    					if( newValue=='LC'){
    						customerDetail.show();
    						customerDetail.labelEl.update('<span style="color:red;">*</span>'+
    								pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.customerDetail'));
    						agencyDetialPA.hide();
    						agencyDetialPA.labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
    						agencyDetialAA.hide();
    						agencyDetialAA.labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
    					}else if(newValue=='PA'){
    						agencyDetialPA.show();
    						agencyDetialPA.labelEl.update('<span style="color:red;">*</span>'+pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
    						agencyDetialAA.hide();
    						agencyDetialAA.labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
    						customerDetail.hide();
    					}else if(newValue == 'AA'){
    						agencyDetialAA.show();
    						agencyDetialAA.labelEl.update('<span style="color:red;">*</span>'+pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
    						customerDetail.hide();
    						customerDetail.labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.customerDetail'));
    						agencyDetialPA.hide();
    						agencyDetialPA.labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
    					}
    				}
    			}
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{	
		    	xtype: 'combobox',
				name:'paymentType',
		        fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.paymentType'),
		    	labelWidth:85,
		    	allowBlank:false,
		    	editable:false,
		    	store:Ext.create('Foss.billDepositReceivedAdd.PaymentTypeStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:'',
		    	columnWidth:.4,
		    	listeners:{
					'change':function(th,newValue,oldValue){
						//获取表单等控件
						var form,//表单
							remitNo,
							amount;
						//获取表单	
						form= this.up('form').getForm();
						//获取下面组件
						remitNo = form.findField('remitNo');
						amount = form.findField('amount');
						//当选择电汇和支票时，隐藏汇款编号控件
						if( newValue=='NT' || newValue == 'TT'){
							remitNo.setDisabled(false);
							form.findField('remitNo').labelEl.update('<span style="color:red;">*</span>'+pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.remitNo')+':');
							amount.setValue("");
						}else if(newValue=='CD' || newValue == 'CH'|| newValue == 'OL'){
							form.findField('remitNo').labelEl.update(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.remitNo'));
							remitNo.setValue("");
							amount.setValue("");
							remitNo.setDisabled(true);
						}else{
							form.findField('remitNo').setFieldLabel(pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.remitNo')+':');
							remitNo.setValue("");
							amount.setValue("");
							remitNo.setDisabled(true);
						}
					}
				}
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
				xtype:'commoncustomerselector',
				singlePeopleFlag : 'Y',
		    	fieldLabel:'<span style="color:red;">*</span>'+pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.customerDetail'),
				name:'customerDetail',
				columnWidth:.4,
				labelWidth:85,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
				xtype:'commonairagentselector',
		    	fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'),
				name:'agencyDetialAA',
				columnWidth:.4,
				hidden:true,
				labelWidth:85,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'),
		    	labelWidth:85,
		    	columnWidth:.4,
		    	name :'agencyDetialPA',
				isPaging:true ,// 分页
				hidden:true
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
		    	xtype:'textfield',
				fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.remitNo'),
				labelWidth:85,
				name:'remitNo',
				columnWidth:.4,
				regex:/^((\s)*[^\u4e00-\u9fa5]+)(\s)*$/,
	 	        regexText:'输入非中文的汇款编号',
				disabled:true,
				listeners:{
					'blur':function(th){
    		    	//获取表单等控件
    				var form,//表单
    					remitNo,
    					amount,
    					paymentType;
            	    	form= this.up('form').getForm();
            			//获取下面组件
            	    	remitNo = form.findField('remitNo');
            			amount = form.findField('amount');
            			paymentType = form.findField('paymentType').getValue();
            			var generatingOrgCodeLast = form.findField('generatingOrgDetail').lastValue;
            			var generatingOrgCode = "";
            			if(generatingOrgCodeLast == stl.currentDept.name){
            				generatingOrgCode = stl.currentDept.code;
            			}else{
            				generatingOrgCode = generatingOrgCodeLast;
            			}
        		    	if(th.getValue() != "" && th.getValue != null){
        		    		var reg = /^[\u4E00-\u9FA5]+$/; 
        		    		if(reg.test(th.getValue())){ 
        		    			Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),
        		    					"请输入非中文的汇款编号!"); 
        		    			remitNo.setValue("");
            		    		return false; 
        		    		} 
        					var billDepositReceivedPayVo,billDepositReceivedPayDto;
        					//当选择电汇和支票时，隐藏汇款编号控件
    						billDepositReceivedPayVo = new Object();
    						billDepositReceivedPayDto = new Object();
    						billDepositReceivedPayDto.remitNo = th.getValue();
    						billDepositReceivedPayDto.paymentType =  paymentType;
    						billDepositReceivedPayDto.generatingOrgCode = generatingOrgCode;
    						billDepositReceivedPayVo.billDepositReceivedPayDto = billDepositReceivedPayDto;
    						//调用后台接口根据输入汇款编号获取汇款人、汇款日期，汇款可用金额、
    						Ext.Ajax.request({
    							url:pay.realPath('queryPayRemittanceDetail.action'),
    							jsonData:{
    								'billDepositReceivedPayVo':billDepositReceivedPayVo
    							},
    							method:'post',
    							success:function(response){
    								var result = Ext.decode(response.responseText);	
    								amount.setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount);
    								pay.billDepositReceivedAdd.noCancelAmount = 
    										result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount;
    							},
    							exception:function(response){
    								var result = Ext.decode(response.responseText);	
    								Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
    								remitNo.setValue("");
    							},
    							failure:function(response){
    								var result = Ext.decode(response.responseText);
    								Ext.Msg.alert(pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.alert'),result.message);
    							}	
    						});
        				}
		    		}
				}
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
		    	xtype:'numberfield',
		    	fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.amount'),
		    	name:'amount',
		    	allowBlank:false,
		    	decimalPrecision : 2,
		    	labelWidth:85,
				html: '&nbsp;',
				columnWidth:.4
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
		    	xtype: 'textarea',
		    	autoScroll:true,
				fieldLabel:pay.billDepositReceivedAdd.i18n('foss.stl.pay.billDepositReceived.notes'),
				labelWidth:85,
				name:'notes',
				columnWidth:.83,
				height : 80
		    },{	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					//text:'重置',
					text:pay.billDepositReceivedAdd.i18n('foss.stl.pay.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('generatingOrgDetail').setValue('');
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.56,
					html: '&nbsp;'
				},{
					text:pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.commitAccount'),
					disabled:!pay.billDepositReceivedAdd.isPermission('/stl-web/pay/addBillDepositReceivedAccount.action'),
					hidden:!pay.billDepositReceivedAdd.isPermission('/stl-web/pay/addBillDepositReceivedAccount.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form  = this.up('form').getForm();	
						var me = this;
						pay.billDepositReceivedAdd.addBillDepositReceivedAccount(form,pay.billDepositReceivedAdd.DEPOSIT_RECEIVED_ACCOUNTING,me);
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.03,
					html: '&nbsp;'
				},{
					text:pay.billDepositReceivedAdd.i18n('foss.stl.pay.addBillDepositReceived.commitCashier'),
					disabled:!pay.billDepositReceivedAdd.isPermission('/stl-web/pay/addBillDepositReceived.action'),
					hidden:!pay.billDepositReceivedAdd.isPermission('/stl-web/pay/addBillDepositReceived.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form  = this.up('form').getForm();	
						var me = this;
						//设置该按钮灰掉
						//me.disable(false);
						//10秒后自动解除灰掉效果
						//setTimeout(function() {
							//me.enable(true);
						//}, 10000);
						pay.billDepositReceivedAdd.addBillDepositReceived(form,pay.billDepositReceivedAdd.DEPOSIT_RECEIVED_CASHIER,me);
					}
				}]
		    }]
        }]
	}]
});

//初始化界面
Ext.onReady(function() {
	Ext.Ajax.timeout=180000;
	Ext.QuickTips.init();

	var StladdBillDepositReceivedQueryInfoTab = Ext.create('Foss.StladdBillDepositReceived.StladdBillDepositReceivedInfoTab');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-billDepositReceivedAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StladdBillDepositReceivedQueryInfoTab],
		getQueryTab:function(){
      		return StladdBillDepositReceivedQueryInfoTab;
      	},
		renderTo: 'T_pay-billDepositReceivedAdd-body',
		listeners:{
    			'boxready':function(th){
          		var roles = stl.currentUserDepts;
          		var queryTab = th.getQueryTab();
          		var form = queryTab.down('form');
          		form = form.getForm();
          		var deptcombselector = form.findField('generatingOrgDetail');
          		//获取下面组件
          		var customerDetail = form.findField('customerDetail');
          		var agencyDetialPA = form.findField('agencyDetialPA');
          		var agencyDetialAA = form.findField('agencyDetialAA');
          		
          		if(agencyDetialPA){
          			agencyDetialPA.hide();
          		}
    				
          		if(agencyDetialAA){
          			agencyDetialAA.hide();
          		}
          		
          		if(pay.billDepositReceivedAdd.isPermission('/stl-web/pay/addBillDepositReceivedAccount.action')){
          			deptcombselector.setDisabled(false);
          			deptcombselector.setValue("");
          		}
    		}
		}
	});
});
