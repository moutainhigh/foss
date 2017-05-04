/**
  * 查询成功后，执行的公用方法
  */ 
writeoff.partnerPayStatementAdd.statementQuerySuccess = function(result){
	var baseInfoAdd,
		statementEntryAddGrid,
		partnerPayStatementDList,
		partnerPayStatementEntity,
		model;
		
	baseInfoAdd = Ext.getCmp('Foss_partnerPayStatementAdd_BaseInfoAdd_ID');
	statementEntryAddGrid = Ext.getCmp('Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID');
	
	//获取表单后台数据
	partnerPayStatementAddDto = result.partnerPayStatementVo.partnerPayStatementDto;
	partnerPayStatementEntity = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementEntity;
	partnerPayStatementDList = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList;
	model = new Foss.partnerPayStatementAdd.StatementFormModel(partnerPayStatementEntity);
	
	if(Ext.isEmpty(partnerPayStatementDList)){
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
		return false;
	}

	//判断如果本期为空，则不显示
	if(partnerPayStatementDList.length==0){
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
		return false;
	}	
	//判断银行账号信息异常
	var accountException = result.partnerPayStatementVo.partnerPayStatementDto.accountException;
	if(!Ext.isEmpty(accountException)){
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),accountException);
	}
	//load后台明细数据
	statementEntryAddGrid.store.loadData(partnerPayStatementDList);
	
	//form表单load后台数据
	baseInfoAdd.loadRecord(model);
	//给总条数复制
	statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(result.partnerPayStatementVo.partnerPayStatementDto.count||0);
	//查询后显示隐藏组件
	baseInfoAdd.show();
	statementEntryAddGrid.show();
} 

/**
 * 按合伙人制作查询对账单
 * 
 * 
 */
writeoff.partnerPayStatementAdd.statementQueryByCustomerInfo = function(){
	var form  = this.up('form').getForm();
	//声明合伙人编码和合伙人名称
	var customerCode;
	if(form.isValid()){
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		
		writeoff.partnerPayStatementAdd.customerCode = form.getValues().customerCode;
		writeoff.partnerPayStatementAdd.periodBeginDate = form.findField('periodBeginDate').getValue();
		writeoff.partnerPayStatementAdd.periodEndDate = form.findField('periodEndDate').getValue();
		writeoff.partnerPayStatementAdd.billType = form.getValues().billType;
		writeoff.partnerPayStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		
		//声明日期类型
		var dateType = form.getValues().queryDateType;
		//校验合伙人信息
		
		//合伙人编码
		customerCode = form.getValues().customerCode;
		//客户名称
		customerName = form.findField('customerCode').getRawValue();
		//合伙人编码不能为空
		if(Ext.isEmpty(customerCode)){
			//合伙人信息不能为空!
			Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.receivableStatementAdd.hehuorenMessage'));
			return false;
		}
		
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		
		if(compareTwoDate>stl.DATELIMITMAXDAYS_WEEK){
			Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		
		//选择单据子类型
		var selectBillTypes = form.getValues().billType;
		if(selectBillTypes==undefined){
			Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
			return false;
		}
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('Foss_partnerPayStatementAdd_BaseInfoAdd_ID');
		var statementEntryAddGrid = Ext.getCmp('Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID');
		
		baseInfoAdd.getForm().reset();
		statementEntryAddGrid.store.removeAll();
		statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);

		var grid = Ext.getCmp('T_writeoff-partnerPayStatementAdd_content').getGrid();
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					//var result = Ext.decode(response.responseText);	
					//writeoff.partnerPayStatementAdd.statementQuerySuccess(result);
					Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),rawData.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//调用执行成功后的方法
					writeoff.partnerPayStatementAdd.statementQuerySuccess(result);
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

/**
 * 根据单号查询对账单
 */
writeoff.partnerPayStatementAdd.statementQueryByNumbers = function(){
	var form  = this.up('form').getForm();	
	var billDetailNoArray =[];

	var billDetailNos = form.findField('billDetailNos').getValue();
	if(Ext.String.trim(billDetailNos)!=null && Ext.String.trim(billDetailNos)!=''){
		 array = stl.splitToArray(billDetailNos);
		 for(var i=0;i<array.length;i++){
		 	if(Ext.String.trim(array[i])!=''){
		 		billDetailNoArray.push(array[i]);	
		 	}
		 }
		 if(billDetailNoArray.length>10){
		 	Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	
	//选择单据子类型
	var selectBillTypes = form.getValues().billType;
	if(selectBillTypes==undefined){
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
		return false;
	}

	if(form.isValid()){
		
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('Foss_partnerPayStatementAdd_BaseInfoAdd_ID');
		var statementEntryAddGrid = Ext.getCmp('Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID');

		writeoff.partnerPayStatementAdd.billType = selectBillTypes;
		writeoff.partnerPayStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER;
		writeoff.partnerPayStatementAdd.billDetailNos = billDetailNos;
		
		baseInfoAdd.getForm().reset();
		statementEntryAddGrid.store.removeAll();
		statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
		
		var grid = Ext.getCmp('T_writeoff-partnerPayStatementAdd_content').getGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && !rawData.isException){
					Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),rawData.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText); 
					//调用执行成功后的方法
					writeoff.partnerPayStatementAdd.statementQuerySuccess(result);
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

/**
 * 生成对账单
 */
writeoff.partnerPayStatementAdd.statementSave = function(){
	//声明变量
	var baseInfoAdd,
		statementEntryAddGrid,
		win,
		partnerPayStatementVo,
		partnerPayStatementDto,
		partnerPayStatementEntity,	
		entryGridLength;
		
	baseInfoAdd = Ext.getCmp('Foss_partnerPayStatementAdd_BaseInfoAdd_ID');
	statementEntryAddGrid = Ext.getCmp('Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID');
	//对账单明细表格数据量
	entryGridLength = statementEntryAddGrid.store.data.items.length;
	
	if(entryGridLength==0){
		Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.currentDataIsNullWarning'));
		return false;
	}
	
	myMask =Ext.getCmp('Foss.partnerPayStatementAdd.LoadMaskAdd_ID');
	if(myMask==null){
		myMask = new Ext.LoadMask(Ext.getCmp('T_writeoff-partnerPayStatementAdd_content'), {
    		id:'Foss.partnerPayStatementAdd.LoadMaskAdd_ID',
			msg:'正在生成对账单中...',
		    removeMask : true// 完成后移除
		});
	}
	
	Ext.Msg.confirm(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.isSaveStatement'), function(btn){
		if(btn=='yes'){
			myMask.show();
			//将查询出来的全局变量DTO传入后台
			partnerPayStatementDto = partnerPayStatementAddDto;
			//将dto注入到后台中
			partnerPayStatementVo = new Object();
			partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url: writeoff.realPath('addPartnerPayStatement.action'),
				jsonData:{'partnerPayStatementVo':partnerPayStatementVo},
			    method : 'POST',
			    isUpload : true,
			    success : function(response,options){
			    	myMask.hide();
			    	var result = Ext.decode(response.responseText);
			    	var statementBillNo = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementEntity.statementBillNo;
			    	Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			    		'合伙人付款对账单保存成功，对账单单号：'+ statementBillNo);

					//制作完后清空对账单界面数据
					baseInfoAdd.getForm().reset();
					statementEntryAddGrid.store.removeAll();
					statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
					//对账单明细来源 -- 新增
					writeoff.showEntryType = writeoff.PAGEFROM_STATEMENTADD;
			    },
				exception:function(response){
					myMask.hide();
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				},
				failure:function(response){
					myMask.hide();
				}
			});
		}
	});
};

/** 
 * 添加明细到对账单中
 * @param {} grid 表单
 * @param {} rowIndex 行标
 * @param {} colIndex
 */
writeoff.partnerPayStatementAdd.statementAdd = function(grid, rowIndex, colIndex){
	var statementEntryAddGrid = Ext.getCmp('Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID');
	var baseInfo = Ext.getCmp('Foss_partnerPayStatementAdd_BaseInfoAdd_ID');
	var statementEntryStore = statementEntryAddGrid.store;
	var selection = grid.getStore().getAt(rowIndex);
	
	//调用edit.js中的操作方法
	writeoff.statementOperate(baseInfo,beginBillsForm,selection,writeoff.STATEMENT_OPERATE_ADD);	
	statementPreEntryStore.remove(selection);
	statementEntryStore.insert(0,selection);
	//给总条数复制
	statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(statementEntryStore.data.length);		
}

/**
 * 对账单form表单model
 */

Ext.define('Foss.partnerPayStatementAdd.StatementFormModel', {
    extend: 'Ext.data.Model',
    fields: [{
        //合伙人编码
        name: 'customerCode'
    }, {
        //单据类型
        name: 'billType'
    }, {
        name: 'createTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'confirmStatus'
    }, {
        name: 'periodBeginDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'companyCode'
    }, {
        name: 'companyName'
    }, {
        name: 'customerName'
    }, {
        name: 'confirmTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'periodEndDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                //此处一定要这样写，不能转化成字符串，如果转化成字符串，那么你getRecord拿到的也是字符串。
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'createOrgCode'
    }, {
        name: 'createUserName'
    }, {
        name: 'createOrgName'
    }, {
        name: 'unifiedCode'
    }, {
        name: 'statementBillNo'
    },{
        name: 'payableNo'
    }, {
        name: 'companyAccountBankNo'
    }, {
        name: 'accountUserName'
    }, {
        name: 'bankBranchName'
    }, {
        name: 'periodUnverifyPayAmount',
        type: 'double'
    }, {
        name: 'settleNum',
        type: 'int'
    }, {
        name: 'periodAmount',
        type: 'double'
    }, {
        name: 'periodPayAmount',
        type: 'double'
    }, {
        name: 'notes'
    }, {
        name: 'id'
    }, {
        name: 'versionNo',
        type: 'short'
    }, {
        name: 'invoiceStatus'
    }, {
        name: 'applyInvoice'
    }, {
        //统一结算
        name: 'unifiedSettlement'
    }]
});

/**
 * 对账单明细表格model
 */
Ext.define('Foss.partnerPayStatementAdd.StatementEntryModel', {
    extend: 'Ext.data.Model',
    fields: [{
		//应付单号
		name:'payableNo'
	},{
		//运单号
		name:'waybillNo'
	},{
		//单据子类型
		name:'billType'
	},{
		//审核状态
		name:'auditStatus'
	},{
		//金额
		name:'amount',
        type: 'double'
	},{
		//已核销金额
		name:'verifyAmount',
        type: 'double'
	},{
		//未核销金额
		name:'unverifyAmount',
        type: 'double'
	},{
		//部门编码
		name:'orgCode'
	},{
		//部门名称
		name:'orgName'	
	},{
		//始发网点编码
		name:'origOrgCode'
	},{
		//始发网点名称
		name:'origOrgName'
	},{
		//到达网点编码
		name:'destOrgCode'
	},{
		//到达网点名称
		name:'destOrgName'
	},{
		//客户编码
		name:'customerCode'
	},{
		//客户名称
		name:'customerName'
	},{
		//始发地
		name:'deptRegionCode'
	},{
		//目的站
		name:'arrvRegionCode'
	},{
		//提货网点
		name:'customerPickupOrgName'	
	},{
		//货物名称
		name:'goodsName'
	},{
		//发货客户编码
		name:'deliveryCustomerCode'
	},{
		//发货客户名称
		name:'deliveryCustomerName'
	},{
		//付款方式
		name:'paymentType'
	},{
		//提货方式
		name:'receiveMethod'
	},{
		//运输性质
		name:'productCode'
	},{
		//删除时间
		name:'disableTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
	},{
		//业务日期
		name:'businessDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
	},{
		//记账日期
		name:'accountDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }	
	},{
		//运单签收日期
		name:'signDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
	},{
		//创建时间
		name:'createTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
	},{
		//备注
		name:'notes'
	},{
		//运单开单时间
		name:'waybillCreateDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
	},{
		//支线转运提成
		name:'regionalTransportFee',
        type: 'double'
	},{
		//送货费
		name:'deliveryFee',
        type: 'double'
	},{
		//派送费
		name:'dispatchFee',
        type: 'double'
	},{
		//送货进仓费
		name:'deliveryWarehouseFee',
        type: 'double'
	},{
		//签收单返单费
		name:'signReturnFee',
        type: 'double'
	},{
		//送货上楼费
		name:'deliveryUpstairsFee',
        type: 'double'
	},{
		//大件上楼费
		name:'bigUpstairsFee',
        type: 'double'
	},{
		//超远派送费
		name:'farDeliveryFee',
        type: 'double'
	}]
});

/**
 * 对账单明细store
 */
Ext.define('Foss.partnerPayStatementAdd.StatementEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.partnerPayStatementAdd.StatementEntryModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryForPartnerPayStatementMake.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.partnerPayStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
						'partnerPayStatementVo.partnerPayStatementDto.customerCode':writeoff.partnerPayStatementAdd.customerCode,
						'partnerPayStatementVo.partnerPayStatementDto.customerName':writeoff.partnerPayStatementAdd.customerName,
						'partnerPayStatementVo.partnerPayStatementDto.periodBeginDate':writeoff.partnerPayStatementAdd.periodBeginDate,
						'partnerPayStatementVo.partnerPayStatementDto.periodEndDate':writeoff.partnerPayStatementAdd.periodEndDate,
						'partnerPayStatementVo.partnerPayStatementDto.billType':writeoff.partnerPayStatementAdd.billType,
						'partnerPayStatementVo.partnerPayStatementDto.queryTabType':writeoff.STATEMENTQUERYTAB_BYCUSTOMER
				};
			}else{
				var billDetailNos = stl.splitToArray(writeoff.partnerPayStatementAdd.billDetailNos);
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.billDetailNos':billDetailNos,
					'partnerPayStatementVo.partnerPayStatementDto.billType':writeoff.partnerPayStatementAdd.billType,
					'partnerPayStatementVo.partnerPayStatementDto.queryTabType':writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}

});

/**
 * 查询tab
 */
Ext.define('Foss.partnerPayStatementAdd.QueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab:0,//默认显示按单号制作
	height:210,
    items: [{
      	title: writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardAdd.queryByPartner'),//按合伙人制作
		tabConfig: {
			width: 120
		},
		layout: 'hbox',
        items:[{
	    	xtype:'form',
	    	width:920,
	    	defaults:{
        		labelWidth:75,
        		labelAlign:'left',
        		margin:'5,5,5,5'
        	},
			layout:'column',
		    items:[{
	        	xtype:'commonsaledepartmentselector',
	        	listWidth:300,
	        	all:'true',//查询所有(包含作废)
	        	name:'customerCode',
	        	emptyText:'合伙人编码和手机均可查询',//
	        	contcatFlag :'Y',//增加按手机号查询
	        	singlePeopleFlag:'Y',
	        	allowBlank: false,
	        	isPaging:true, //分页
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardAdd.partnerInfo'),
	        	columnWidth:.35,
	        	labelWidth:90,
	        	readOnly:true,
	        	value:stl.currentDept.code
	        },{
	        	xtype:'datefield',
	        	allowBlank:false,
	        	fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.accountPeriod'),
	        	name:'periodBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-stl.DATELIMITDAYS_MONTH)
	        },{
	        	xtype:'datefield',
	        	allowBlank:false,
	        	fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.to'),
	        	name:'periodEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	labelWidth:50,
	        	value:new Date()
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:180
				},
				items:[{
		       	 	xtype: 'fieldcontainer',
		            fieldLabel: writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.billType'),//对账单类型
		            columnWidth:.5,
		            labelWidth:90,
		            defaultType: 'radiofield',
		            layout:'column',
		            /*layout:'hbox',*/
		            items: [{
		                boxLabel: writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryFee'),//到达提成
		                name: 'billType',
		                inputValue: 'PDFP',
		                checked:true
		            }]
	       	 	}]
        	},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.44
				},{
					text:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.partnerPayStatementAdd.statementQueryByCustomerInfo
				}]
        	}]
	 	}]
	},{										
        title: writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.makeByNumber'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
	    	defaults:{
	        	margin:'5,5,5,5'
	   		 },
	    	layout:'column',
		    items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
	    		emptyText:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
	    		name:'billDetailNos',
	    		allowBlank:false,
	    		columnWidth:.65,
	    		labelWidth:70,
	    		width: 600,
	    		height:110
	        },{
				xtype:'container',
				columnWidth:.35,
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+'(备注包括：运单、应付单等)'+'</span>'
					}
				}]
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
		       	 	xtype: 'fieldcontainer',
		            fieldLabel: writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.billType'),//对账单类型
		            columnWidth:.5,
		            labelWidth:90,
		            defaultType: 'radiofield',
		            layout:'column',
		            /*layout:'hbox',*/
		            items: [{
		                boxLabel:  writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryFee'),//到达提成
		                name: 'billType',
		                inputValue: 'PDFP',
		                checked:true
		            }]
	       	 	},{
					text:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.partnerPayStatementAdd.statementQueryByNumbers
				}]
        	}]
	    }]
    }]
});

/**
 * 基本对账单信息
 */
Ext.define('Foss.partnerPayStatementAdd.BaseInfo',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	/*hidden:true,*/
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:65,
		margin:'5 5 5 10',
		readOnly:true
	},
	items:[{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.billType'),
		name:'billType',
		xtype:'combo',
		labelWidth:80,
		columnWidth:.3,
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode'
	},{
		xtype:'commoncustomerselector',
		listWidth:300,
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),//合伙人编码
		singlePeopleFlag:'Y',
		labelWidth:95,
		contcatFlag :'Y',//增加按手机号查询
		name:'customerCode',
		columnWidth:.3
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),//合伙人名称
		name:'customerName',
		labelWidth:95,
		columnWidth:.3
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		name:'periodBeginDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.3
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyName'),
		name:'companyName',
		columnWidth:.5
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		name:'periodEndDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.3
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
		name:'createOrgName',
		columnWidth:.5
	},{
		 xtype: 'component',
		 border:true,
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		labelWidth:85,
		name:'unifiedCode',
		columnWidth:.20
	},{
		xtype:'container',
		columnWidth:.30,
		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 0',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCodeNotes')+'</span>'
			}
		}]
    },{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),
		name:'companyAccountBankNo',
		columnWidth:.22
	},{
		xtype:'container',
		columnWidth:.28,
		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 0',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNoNotes')+'</span>'
			}
		}]
    },{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		name:'accountUserName',
		columnWidth:.5
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
		name:'bankBranchName',
		columnWidth:.48
	},{
		 xtype: 'component',
		 border:true,
	   autoEl: {
	       tag: 'hr'
	   },	
		 columnWidth:1
	},{
		fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementAdd.periodUnverifyPayAmount'),
		labelWidth:110,
		name:'periodUnverifyPayAmount',
		xtype:'numberfield',
		decimalPrecision:2,
		columnWidth:.5,
		value:0
	}]
});

/**
* 格式化分录的单据子类型
*/
writeoff.partnerPayStatementAdd.statementFormatBillType = function(value,record){
	var displayField = value;
	//如果为应收单，则读取应收单的单据子类型
	if(record.get('billParentType')==writeoff.STATEMENTDETAIL_RECEIVABLE){
		displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__BILL_TYPE);
	}else if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE){
		displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_PAYABLE__BILL_TYPE);
	}else{
		displayField = value;
	}
	return displayField;
}

/**
 * 对账单明细记录
 */
Ext.define('Foss.partnerPayStatementAdd.StatementEntryAddGrid', {
	extend:'Ext.grid.Panel',
	title:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementCommon.statementEntry'),
    /*hidden:true,*/
    frame:true,
    height:600,
    store:Ext.create('Foss.partnerPayStatementAdd.StatementEntryStore',{id:'Foss_partnerPayStatementAdd_StatementEntryStore_ID'}),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
  	defaults:{
  		align:'center',
  		margin:'5 0 5 0'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns: [{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.payableNo'),//应付单号
		dataIndex:'payableNo'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
		dataIndex:'waybillNo'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.billType'),//单据子类型
		dataIndex:'billType',
		renderer:function(value){
			if(value!=null){
				return FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_PAYABLE__BILL_TYPE);
			}else{
				return value;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.approveStatus'),//审核状态
		dataIndex:'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
			return displayField;
		}
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.amount'),//金额
		dataIndex:'amount'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.verifyAmount'),//已核销金额
		dataIndex:'verifyAmount'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.unverifyAmount'),//未核销金额
		dataIndex:'unverifyAmount'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.orgCode'),//部门编码
		dataIndex:'orgCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.orgName'),//部门名称
		dataIndex:'orgName'	
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),//始发网点编码
		dataIndex:'origOrgCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgName'),//始发网点名称
		dataIndex:'origOrgName'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),//到达网点编码
		dataIndex:'destOrgCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgName'),//到达网点名称
		dataIndex:'destOrgName'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.customerCode'),//客户编码
		dataIndex:'customerCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.customerName'),//客户名称
		dataIndex:'customerName'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),//始发地
		dataIndex:'deptRegionCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),//目的站
		dataIndex:'arrvRegionCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),//提货网点
		dataIndex:'customerPickupOrgName'	
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.goodsName'),//货物名称
		dataIndex:'goodsName'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),//发货客户编码
		dataIndex:'deliveryCustomerCode'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),//发货客户名称
		dataIndex:'deliveryCustomerName'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.woodenStatementEdit.paymentType'),//付款方式
		dataIndex:'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
			return displayField;
		}
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),//提货方式
		dataIndex:'receiveMethod',
		renderer:function(value){
			//先去汽运提货方式词条中拿
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS );
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(value==displayField){
				displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS_AIR);
			}
			return displayField;
		}
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.productCode'),//运输性质
		dataIndex:'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.receivableStatementAdd.shangchushijian'),//删除时间
		dataIndex:'disableTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.businessDate'),//业务日期
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.accountDate'),//记账日期
		dataIndex:'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),//运单签收日期
		dataIndex:'signDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.createTime'),//创建时间
		dataIndex:'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.notes'),//备注
		dataIndex:'notes'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.common.createTime'),//运单开单时间
		dataIndex:'waybillCreateDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.regionalTransportFee'),//支线转运提成
		dataIndex:'regionalTransportFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliverFee'),//送货费
		dataIndex:'deliveryFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.dispatchFee'),//派送费
		dataIndex:'dispatchFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryWarehouseFee'),//送货进仓费
		dataIndex:'deliveryWarehouseFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.signReturnFee'),//签收单返单费
		dataIndex:'signReturnFee'	
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryUpstairsFee'),//送货上楼费
		dataIndex:'deliveryUpstairsFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.bigUpstairsFee'),//大件上楼费
		dataIndex:'bigUpstairsFee'
	},{
		header:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.farDeliveryFee'),//超远派送费
		dataIndex:'farDeliveryFee'
	}],
	initComponent:function(){ 
		this.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},
		    items: [{
				xtype:'textfield',
				readOnly:true,
				name:'totalRows',
				columnWidth:.1,
				labelWidth:60,
				fieldLabel:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.totalCount')
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.35
			},{
				xtype:'standardpaging',
				store:this.store,
				columnWidth:.45,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 },{
				xtype:'button',
				text:writeoff.partnerPayStatementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),
				columnWidth:.1,
				handler:writeoff.partnerPayStatementAdd.statementSave,
				hidden:!writeoff.partnerPayStatementAdd.isPermission('/stl-web/writeoff/addPartnerPayStatement.action')
			}]
		}];
  		this.callParent();
	}
});

/**
 * 对账单页面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建查询页签面板
	var queryInfoTab = Ext.create('Foss.partnerPayStatementAdd.QueryInfoTab');
	//创建基本信息面板
	var baseInfo = Ext.create('Foss.partnerPayStatementAdd.BaseInfo',{id:'Foss_partnerPayStatementAdd_BaseInfoAdd_ID'});
	//创建明细信息面板
 	var statementEntryAddGrid =  Ext.create('Foss.partnerPayStatementAdd.StatementEntryAddGrid',{
									id:'Foss_partnerPayStatementAdd_StatementEntryAddGrid_ID'
								 });
	//创建整个对账单面板，将上面几块包含在一起
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-partnerPayStatementAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getGrid:function(){
			return statementEntryAddGrid;
		},
		getTab:function(){
			return queryInfoTab;
		},
		items: [queryInfoTab,baseInfo,statementEntryAddGrid],
		renderTo: 'T_writeoff-partnerPayStatementAdd-body',
		listeners: {
            'boxready': function(th) {
                var form = queryInfoTab.down('form');
                console.info(form);
                var dept = form.getForm().findField('customerCode');
                if (!Ext.isEmpty(stl.currentDept.code)) {
                    var displayText = stl.currentDept.name
                    var valueText = stl.currentDept.code;
                    var store = dept.store;
                    var key = dept.displayField + '';
                    var value = dept.valueField + '';
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