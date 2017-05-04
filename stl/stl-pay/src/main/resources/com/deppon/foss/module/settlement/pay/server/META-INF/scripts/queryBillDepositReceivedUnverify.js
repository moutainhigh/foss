/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.depositReceivedUnverify.billDepositReceivedConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};
pay.depositReceivedUnverify.QUERY_BYDATE = 'TD';//按客户查询
pay.depositReceivedUnverify.QUERY_BYNUMBER = 'DERE';//按单号查询

pay.depositReceivedUnverify.getMonthStartDate = function(){
	var currentDate = new Date();
	currentDate.setDate(currentDate.getDate());
	currentDate.setMonth(currentDate.getMonth()-1, currentDate.getDate());
	currentDate.setHours(0);
	currentDate.setMinutes(0);
	currentDate.setSeconds(0);
	currentDate.setMilliseconds(0);
	return currentDate;
};

/**
 * 按日期,客户查询
 */
pay.depositReceivedUnverify.queryBillDepositReceivedUnverifyEntityParams = function(form,me){
	pay.depositReceivedUnverify.generatingOrgCode = form.findField('generatingOrgDetial').getValue();
	pay.depositReceivedUnverify.startBusinessDate = form.findField('startDate').getValue();
	pay.depositReceivedUnverify.endBusinessDate = form.findField('endDate').getValue();
	pay.depositReceivedUnverify.activeStatus = form.findField('activeStatus').getValue();
	pay.depositReceivedUnverify.depositReceivedNoName = form.findField('depositReceivedNoName').getValue();
	pay.depositReceivedUnverify.customerDetialCode = form.findField('pay.depositReceived.commoncustomerselector').getValue();
	pay.depositReceivedUnverify.agencyDetialPACode = form.findField('pay.depositReceived.commonvehagencycompselector').getValue();
	pay.depositReceivedUnverify.agencyDetialAACode = form.findField('pay.depositReceived.commonairagencycompanyselector').getValue();
	pay.depositReceivedUnverify.agencyDetialLSCode = form.findField('pay.depositReceived.commonldpagencycompanyselector').getValue();
	pay.depositReceivedUnverify.isRedBack = form.findField('isRedBackStatus').getValue();
	pay.depositReceivedUnverify.isInit = form.findField('isInit').getValue();
	pay.depositReceivedUnverify.queryByTab = pay.depositReceivedUnverify.QUERY_BYDATE;
	if(pay.depositReceivedUnverify.startBusinessDate==null 
			|| pay.depositReceivedUnverify.startBusinessDate==''){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.dateIsNotNull'));
		return false;
	}

	if(pay.depositReceivedUnverify.endBusinessDate==null 
			|| pay.depositReceivedUnverify.endBusinessDate==''){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.dateIsNotNull'));
		return false;
	}
	
	var compareTwoDate = stl.compareTwoDate(pay.depositReceivedUnverify.startBusinessDate
			,pay.depositReceivedUnverify.endBusinessDate);
	if(compareTwoDate>stl.DATELIMITMAXDAYS_WEEK){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.queryDateMaxLimitText1')+stl.DATELIMITMAXDAYS_WEEK+
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.queryDateMaxLimitText2'));
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
		return false;
	}
	var customerCode = "";
	if(pay.depositReceivedUnverify.customerDetialCode != null && pay.depositReceivedUnverify.customerDetialCode != ""){
		customerCode = pay.depositReceivedUnverify.customerDetialCode;
	}else if(pay.depositReceivedUnverify.agencyDetialPACode != null && pay.depositReceivedUnverify.agencyDetialPACode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialPACode;
	}else if(pay.depositReceivedUnverify.agencyDetialAACode != null && pay.depositReceivedUnverify.agencyDetialAACode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialAACode;
	}else if(pay.depositReceivedUnverify.agencyDetialLSCode != null && pay.depositReceivedUnverify.agencyDetialLSCode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialLSCode;
	}
	Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode':pay.depositReceivedUnverify.generatingOrgCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate':pay.depositReceivedUnverify.startBusinessDate,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate':pay.depositReceivedUnverify.endBusinessDate,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.active':pay.depositReceivedUnverify.activeStatus,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo':pay.depositReceivedUnverify.depositReceivedNoName,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode':customerCode,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.isInit':pay.depositReceivedUnverify.isInit,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack':pay.depositReceivedUnverify.isRedBack,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab':pay.depositReceivedUnverify.queryByTab
			}
		});	
	});
	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
			    var result =   Ext.decode(operation.response.responseText);  
			    Ext.getCmp('depositReceved_totalNum_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalNum);
				Ext.getCmp('depositReceved_totalAmount_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalAmount);
				Ext.getCmp('depositReceved_totalVerify_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalVerifyAmount);
				Ext.getCmp('depositReceved_totalUnverify_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalUnverifyAmount);
				me.enable(true);
			}
		});
	}else{
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}	
}

/**
 * 按预收单号查询
 * @returns {Boolean}
 */
pay.depositReceivedUnverify.queryByDepositReceivedNOs = function(form,me){
	pay.depositReceivedUnverify.depositReceivedNo = form.findField('depositReceivedNos').getValue();
	pay.depositReceivedUnverify.queryByTab = pay.depositReceivedUnverify.QUERY_BYNUMBER;
	//单号不能为空
	if(pay.depositReceivedUnverify.depositReceivedNo == null ||
			pay.depositReceivedUnverify.depositReceivedNo == ''){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.queryNoIsNotNull'));
		return false;
	}
	//单号个数不能超过10个
	var depositReceivedNosStr = new Array();
	depositReceivedNosStr = stl.splitToArray(pay.depositReceivedUnverify.depositReceivedNo);
	if(depositReceivedNosStr.length>10){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.common.queryNosLimit'));
		return false;
	}
	
	Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo':pay.depositReceivedUnverify.depositReceivedNo,
				'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab':pay.depositReceivedUnverify.queryByTab
			}
		});	
	});
	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
			    var result =   Ext.decode(operation.response.responseText);  
			    Ext.getCmp('depositReceved_totalNum_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalNum);
				Ext.getCmp('depositReceved_totalAmount_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalAmount);
				Ext.getCmp('depositReceved_totalVerify_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalVerifyAmount);
				Ext.getCmp('depositReceved_totalUnverify_id').setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalUnverifyAmount);
				me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}	
}

//导出预收单
pay.depositReceivedUnverify.depositReceivedUnverifyListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),
				pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.exportIsNotNull'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	var customerCode = "";
	if(pay.depositReceivedUnverify.customerDetialCode != null && pay.depositReceivedUnverify.customerDetialCode != ""){
		customerCode = pay.depositReceivedUnverify.customerDetialCode;
	}else if(pay.depositReceivedUnverify.agencyDetialPACode != null && pay.depositReceivedUnverify.agencyDetialPACode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialPACode;
	}else if(pay.depositReceivedUnverify.agencyDetialAACode != null && pay.depositReceivedUnverify.agencyDetialAACode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialAACode;
	}else if(pay.depositReceivedUnverify.agencyDetialLSCode != null && pay.depositReceivedUnverify.agencyDetialLSCode != ""){
		customerCode = pay.depositReceivedUnverify.agencyDetialLSCode;
	}
	searchParams = {
			'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode':pay.depositReceivedUnverify.generatingOrgCode,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate':pay.depositReceivedUnverify.startBusinessDate,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate':pay.depositReceivedUnverify.endBusinessDate,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.active':pay.depositReceivedUnverify.activeStatus,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo':pay.depositReceivedUnverify.depositReceivedNoName,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode':customerCode,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack':pay.depositReceivedUnverify.isRedBack,
			'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo':pay.depositReceivedUnverify.depositReceivedNo
	}
	var yesFn=function(){
		Ext.Ajax.request({
  		url:pay.realPath('depositReceivedUnverifyListExport.action'),
  		form: Ext.fly('downloadAttachFileForm'),
  		params :searchParams,
		isUpload: true,
  		success:function(response){
			var result =   Ext.decode(response.responseText);  
  			//如果异常信息有值，则弹出提示
  	    	if(!Ext.isEmpty(result.errorMessage)){
  	    		Ext.Msg.alert(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'),result.errorMessage);
  	    		return false;
  	    	}
  	    	Ext.ux.Toast.msg(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'), 
  	    			pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.exportSuccess'), 'success', 1000);
  		},
  		failure:function(response){
  			Ext.ux.Toast.msg(pay.depositReceivedUnverify.i18n('foss.stl.pay.common.alert'), 
  					pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.exportError'), 'error', 1000);
  		}
  	});
	};
	var noFn=function(){
	 	return false;
	};
	pay.depositReceivedUnverify.billDepositReceivedConfirmAlert(
			pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.isExport'),yesFn,noFn);
}

//预收单model
Ext.define('Foss.PayDepositReceivedUnverify.BillDepositReceivedUnverifyEntityModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'depositReceivedNo'
	},{
		name:'collectionOrgName'
	},{
		name:'collectionOrgCode'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'transportType'
	},{
		name:'businessDate',
		type:'date',
		convert:function(value) {
			return stl.longToDateConvert(value);
		}
	},{
		name:'status'
	},{
		name:'amount',
		type:'long'
	},{
		name:'verifyAmount',
		type:'long'
	},{
		name:'unverifyAmount',
		type:'long'
	},{
		name:'paymentAmount',
		type:'long'
	},{
		name:'disableUserName'
	},{
		name:'disableUserCode'
	},{
		name: 'createUserName'
	},{
		name: 'createUserCode'
	},{
		name: 'paymentType'
	},{
		name:'accountDate',
		type:'date',
		convert:function(value) {
			return stl.longToDateConvert(value);
		}
	},{
		name: 'isRedBack'
	},{
		name: 'isInit'
	},{
		name: 'versionNo'
	},{
		name: 'active'
	}]
});

//预收单Store
Ext.define('Foss.PayDepositReceivedUnverify.BillDepositReceivedUnverifyEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.PayDepositReceivedUnverify.BillDepositReceivedUnverifyEntityModel',
	proxy:{
		type:'ajax',
		url:pay.realPath('queryDepositReceivedUnverifyPage.action'),
		reader:{
			type:'json',
			root:'billDepositReceivedPayVo.billDepositReceivedPayDto.billDepositReceivedEntityList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			var form = Ext.getCmp('T_pay-queryBillDepositReceivedUnverify_content').getQueryTab().items.items[0].items.items[0].getForm();
			pay.depositReceivedUnverify.generatingOrgCode = form.findField('generatingOrgDetial').getValue();
			pay.depositReceivedUnverify.startBusinessDate = form.findField('startDate').getValue();
			pay.depositReceivedUnverify.endBusinessDate = form.findField('endDate').getValue();
			pay.depositReceivedUnverify.activeStatus = form.findField('activeStatus').getValue();
			pay.depositReceivedUnverify.depositReceivedNoName = form.findField('depositReceivedNoName').getValue();
			pay.depositReceivedUnverify.customerDetialCode = form.findField('pay.depositReceived.commoncustomerselector').getValue();
			pay.depositReceivedUnverify.agencyDetialPACode = form.findField('pay.depositReceived.commonvehagencycompselector').getValue();
			pay.depositReceivedUnverify.agencyDetialAACode = form.findField('pay.depositReceived.commonairagencycompanyselector').getValue();
			pay.depositReceivedUnverify.agencyDetialLSCode = form.findField('pay.depositReceived.commonldpagencycompanyselector').getValue();
			pay.depositReceivedUnverify.isRedBack = form.findField('isRedBackStatus').getValue();
			pay.depositReceivedUnverify.isInit = form.findField('isInit').getValue();
			var customerCode = "";
			if(pay.depositReceivedUnverify.customerDetialCode != null && pay.depositReceivedUnverify.customerDetialCode != ""){
				customerCode = pay.depositReceivedUnverify.customerDetialCode;
			}else if(pay.depositReceivedUnverify.agencyDetialPACode != null && pay.depositReceivedUnverify.agencyDetialPACode != ""){
				customerCode = pay.depositReceivedUnverify.agencyDetialPACode;
			}else if(pay.depositReceivedUnverify.agencyDetialAACode != null && pay.depositReceivedUnverify.agencyDetialAACode != ""){
				customerCode = pay.depositReceivedUnverify.agencyDetialAACode;
			}else if(pay.depositReceivedUnverify.agencyDetialLSCode != null && pay.depositReceivedUnverify.agencyDetialLSCode != ""){
				customerCode = pay.depositReceivedUnverify.agencyDetialLSCode;
			}
			//如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if(Ext.isEmpty(pay.depositReceivedUnverify.queryByTab)){
				//获取查询条件
				searchParams = {
					'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate':pay.depositReceivedUnverify.startBusinessDate,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate':pay.depositReceivedUnverify.endBusinessDate,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode':pay.depositReceivedUnverify.generatingOrgCode,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType':pay.depositReceivedUnverify.paymentType,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode':customerCode,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.active':pay.depositReceivedUnverify.activeStatus,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.isInit':pay.depositReceivedUnverify.isInit,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack':pay.depositReceivedUnverify.isRedBack,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab':pay.depositReceivedUnverify.QUERY_BYDATE
				}
			}
			else if(pay.depositReceivedUnverify.queryByTab == pay.depositReceivedUnverify.QUERY_BYNUMBER){
				searchParams = {
					'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo':pay.depositReceivedUnverify.depositReceivedNo,
					'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab':pay.depositReceivedUnverify.queryByTab
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.PayDepositReceivedUnverify.PayDepositReceivedUnverifyQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 200,
	items : [ {
		title: pay.depositReceivedUnverify.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[{
        	xtype:'form',
        	id : 'Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoTab_FormByDate_Id',
        	defaults:{
	        	margin:'5 5 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
		    items:[{	
		    	xtype:'dynamicorgcombselector',
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.orgDetail'),
				name:'generatingOrgDetial',
				columnWidth:.3,
				labelWidth:80,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype:'datefield',
		    	id:'startDate',
		    	name:'startDate',
		    	fieldLabel:'开始日期',
		    	labelWidth:80,
		    	value: pay.depositReceivedUnverify.getMonthStartDate(new Date()),
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	columnWidth:.3
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:'至',
		    	labelWidth:80,
		    	format:'Y-m-d',
		    	value:new Date(),
		    	allowBlank:false,
		    	maxValue:new Date(),
		    	columnWidth:.3
		    },{
		    	xtype:'textfield',
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'),
				labelWidth:80,
				name:'depositReceivedNoName',
				columnWidth:.3
		    },{
		    	xtype: 'combobox',
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.customerType'),
				name:'transportType',
				labelWidth:80,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE,null,null,
						[pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
						 pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY,
						 pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY,
						 pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE]),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
		    	columnWidth:.3,
		    	listeners:{
        		    	'change':function(th,newValue,oldValue){
						//获取表单等控件
						var form,//表单
							customerDetail,
							agencyDetialPA,
							agencyDetialAA,
							agencyDetialLS;
						//获取表单	
						form= this.up('form').getForm();
						//获取下面组件
						customerDetail = form.findField('pay.depositReceived.commoncustomerselector');
						agencyDetialPA = form.findField('pay.depositReceived.commonvehagencycompselector');
						agencyDetialAA = form.findField('pay.depositReceived.commonairagencycompanyselector');
						agencyDetialLS = form.findField('pay.depositReceived.commonldpagencycompanyselector');
						if( newValue=='LC'){
							customerDetail.show();
							agencyDetialPA.hide();
							agencyDetialPA.setValue("");
							agencyDetialAA.hide();
							agencyDetialAA.setValue("");
							agencyDetialLS.hide();
							agencyDetialLS.setValue("");
						}else if(newValue=='PA'){
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetialPA.show();
							agencyDetialAA.hide();
							agencyDetialAA.setValue("");
							agencyDetialLS.hide();
							agencyDetialLS.setValue("");
						}else if(newValue == 'AA'){
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetialPA.hide();
							agencyDetialPA.setValue("");
							agencyDetialAA.show();
							agencyDetialLS.hide();
							agencyDetialLS.setValue("");
						}else if(newValue == 'LS'){
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetialPA.hide();
							agencyDetialPA.setValue("");
							agencyDetialAA.hide();
							agencyDetialAA.setValue("");
							agencyDetialLS.show();
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :"专线客户",
		    	name : 'pay.depositReceived.commoncustomerselector',
		    	all:'true',
		    	columnWidth:.3,
		    	labelWidth:80,
				isPaging:true // 分页
		    },{	
		    	xtype:'commonairagentselector',
		    	fieldLabel : "空运代理",
		    	columnWidth:.3,
		    	labelWidth:80,
		    	name :'pay.depositReceived.commonairagencycompanyselector',
				isPaging:true // 分页
		    }, {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :"偏线代理",
		    	labelWidth:80,
		    	columnWidth:.3,
		    	name :'pay.depositReceived.commonvehagencycompselector',
				isPaging:true // 分页
		    }, {	
		    	xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :"快递代理",
		    	labelWidth:80,
		    	columnWidth:.3,
		    	name :'pay.depositReceived.commonldpagencycompanyselector',
				isPaging:true // 分页
		    },{
		    	xtype: 'combobox',
		    	id: 'active',
				name:'activeStatus',
		        fieldLabel: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.active'),
		    	labelWidth:80,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.FOSS_ACTIVE,null,{
					 'valueCode': '',
               		 'valueName': pay.depositReceivedUnverify.i18n('foss.stl.pay.common.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'',
				columnWidth:.3
		    },{	
		    	xtype: 'combobox',
		    	id: 'isRedBack',
				name:'isRedBackStatus',
		        fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.isRedBack'),
		    	labelWidth:80,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__IS_RED_BACK,null,{
					 'valueCode': '',
               		 'valueName': pay.depositReceivedUnverify.i18n('foss.stl.pay.common.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'',
				columnWidth:.3
		    },{
		    	xtype: 'combobox',
				name:'isInit',
		        fieldLabel: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.isInit'),
		    	labelWidth:80,
		    	editable:false,
		    	columnWidth:.3,
				store:FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN',null,{
					 'valueCode': '',
               		 'valueName': pay.depositReceivedUnverify.i18n('foss.stl.pay.common.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:''
		    },{	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:pay.depositReceivedUnverify.i18n('foss.stl.pay.common.reset'),
					columnWidth:.1,
					handler:function(){
						//获取界面数据
						this.up('form').getForm().reset(); 
						var dept = this.up('form').getForm().findField('generatingOrgDetial');
						//重置部门为当前登录部门
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = dept.store;
							var  key = dept.displayField + '';
							var value = dept.valueField+ '';
							
							var m = Ext.create(store.model.modelName);
							m.set(key, displayText);
							m.set(value, valueText);
							
							store.loadRecords([m]);
							dept.setValue(valueText);
						}
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.693,
					html: '&nbsp;'
				},{
					text:pay.depositReceivedUnverify.i18n('foss.stl.pay.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){
    					var form = this.up('form').getForm();
    					var me = this;
    					pay.depositReceivedUnverify.queryBillDepositReceivedUnverifyEntityParams(form,me)
    				}
				}]
		    }]
        }]
	}, {
		title: pay.depositReceivedUnverify.i18n('foss.stl.pay.common.queryByNo'),
		tabConfig: {
			width: 200
		},
        layout:'fit',
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'5 5 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
        	 items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedNoEmptyText'),
 	            fieldLabel: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'),
 	            name: 'depositReceivedNos',
 	            height : 80,
 	            allowBlank:false,
 	            columnWidth:.4
 	            //regex:/^((YS)[0-9]{7,10}[;,]?)+$/i,
 	            //regexText:'输入YS加上7-10位以内的数字编号' 
 		    },{
 		    	border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:pay.depositReceivedUnverify.i18n('foss.stl.pay.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.693,
					html: '&nbsp;'
				},{
					text:pay.depositReceivedUnverify.i18n('foss.stl.pay.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){
    					var form = this.up('form').getForm();
    					var me = this;
    					pay.depositReceivedUnverify.queryByDepositReceivedNOs(form,me)
    				}
				}]
 		    }]
        }]
	}]
});


//预收单列表
Ext.define('Foss.PayDepositReceivedUnverify.PayDepositReceivedUnverifyQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceived'),
	frame:true,
	height:400,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.PayDepositReceivedUnverify.BillDepositReceivedUnverifyEntityStore'),
	columns:[{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'), 
		dataIndex: 'depositReceivedNo'
	},{
		header:  pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.orgDetail'), 
		dataIndex: 'collectionOrgName'
	},{
		header:  pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.customerCode'), 
		dataIndex: 'customerCode'
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.customerName'), 
		dataIndex: 'customerName'
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.customerType'),
		dataIndex:'transportType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE);
    		return displayField;
    	}
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.status'), 
		dataIndex: 'status',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
    		return displayField;
    	}
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.amount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.verifyAmount'),
		dataIndex: 'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat 
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.unverifyAmount'), 
		dataIndex: 'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat				
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.paymentAmount'), 
		dataIndex: 'paymentAmount',
		align: 'right',  
		renderer:stl.amountFormat				
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.disableUserName'),
		dataIndex: 'disableUserName' 
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.createUserName'), 
		dataIndex: 'createUserName'
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.paymentType'),  
		dataIndex: 'paymentType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
    		return displayField;
    	}
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.isRedBack'), 
		dataIndex: 'isRedBack',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__IS_RED_BACK);
    		return displayField;
    	}
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.isInit'), 
		dataIndex: 'isInit',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'FOSS_BOOLEAN');
    		return displayField;
    	}
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.versionNo'),
		hidden:true,
		dataIndex: 'versionNo'
	},{
		header: pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.active'), 
		dataIndex: 'active',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
    	}
	}],
	viewConfig:{
		enableTextSelection : true//设置行可以选择，进而可以复制
	},
	initComponent:function(){
		var me = this;
		me.dockedItems=[{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			items: [{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:pay.depositReceivedUnverify.i18n('foss.stl.pay.common.export'),
				disabled:!pay.depositReceivedUnverify.isPermission('/stl-web/pay/depositReceivedUnverifyListExport.action'),
				hidden:!pay.depositReceivedUnverify.isPermission('/stl-web/pay/depositReceivedUnverifyListExport.action'),
				handler:pay.depositReceivedUnverify.depositReceivedUnverifyListExport
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'depositReceved_totalNum_id',
				columnWidth:.15,
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedTotal'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'depositReceved_totalAmount_id',
				columnWidth:.15,
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.depositReceivedSum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'depositReceved_totalVerify_id',
				columnWidth:.15,
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'depositReceved_totalUnverify_id',
				columnWidth:.1,
				fieldLabel:pay.depositReceivedUnverify.i18n('foss.stl.pay.billDepositReceived.unverifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				height:24,
				html:'&nbsp;',
				columnWidth:.5
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.2
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.5,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
			 },{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.6
			}]
		}]
		me.callParent();
	}
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var payDepositReceivedUnverifyQueryInfoTab = Ext.create('Foss.PayDepositReceivedUnverify.PayDepositReceivedUnverifyQueryInfoTab');
	
	var payDepositReceivedUnverifyQueryInfoGrid = Ext.create('Foss.PayDepositReceivedUnverify.PayDepositReceivedUnverifyQueryInfoGrid',{
		id:'Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-queryBillDepositReceivedUnverify_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [payDepositReceivedUnverifyQueryInfoTab,payDepositReceivedUnverifyQueryInfoGrid],
		getQueryTab:function(){
    		return payDepositReceivedUnverifyQueryInfoTab;
    	},
		renderTo: 'T_pay-queryBillDepositReceivedUnverify-body',
		listeners:{
			'boxready':function(th){
        		var queryTab = th.getQueryTab();
        		var form = queryTab.down('form');
        		form = form.getForm();
        		//获取下面组件
        		var customerDetail = form.findField('pay.depositReceived.commoncustomerselector');
        		var agencyDetialPA = form.findField('pay.depositReceived.commonvehagencycompselector');
        		var agencyDetialAA = form.findField('pay.depositReceived.commonairagencycompanyselector');
        		var agencyDetialLS = form.findField('pay.depositReceived.commonldpagencycompanyselector');
        		if(agencyDetialPA){
        			agencyDetialPA.hide();
        		}
        		if(agencyDetialAA){
        			agencyDetialAA.hide();
        		}
        		if(agencyDetialLS){
        			agencyDetialLS.hide();
        		}
        		
        		//BUG-18071 设置默认部门
        		var formByDate = Ext.getCmp('Foss_PayDepositReceivedUnverify_PayDepositReceivedUnverifyQueryInfoTab_FormByDate_Id');
				var dept = formByDate.getForm().findField('generatingOrgDetial');
				//dept.setCombValue(stl.currentDept.name,stl.currentDept.code);
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = dept.store;
					var  key = dept.displayField + '';
					var value = dept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					dept.setValue(valueText);
				}
        		
			}
		}
	});
});