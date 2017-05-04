//合伙人单据子类型
writeoff.partnerStatementOfAwardAdd.billType=function(val){
	    switch (val) {
	    case  "PB":
	        return writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardPayAward');
	    case  "PLE":
	        return writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardPayLand');
	    case  "PP":
	        return writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecDeduct');
	    case  "PER":
	        return writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecError');
	    case  "PTF":
	        return writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecTrain');
	}
};
//store显示时所用model
Ext.define('Foss.partnerStatementOfAwardAdd.GridModel', {
	extend : 'Ext.data.Model',
	fields : [{
        //id
        name: 'id'
    }, {
        //原始来源单据编号
        name: 'origSourceBillNo'
    }, {
    	
    	
        //总金额
        name: 'amount',
        type: 'double'
    }, {
        //已核销金额
        name: 'verifyAmount',
        type: 'double'
    }, {
        //未核销金额
        name: 'unverifyAmount',
        type: 'double'
    }, {
        //单据子类型
        name: 'billType'
    }, {
        //来源单号
        name: 'sourceBillNo'
    }, {
        //对账单编号
        name: 'statementBillNo'
    }, {
        //业务日期
        name: 'businessDate',
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
        //会计日期
        name: 'accountDate',
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
        //客户编码
        name: 'customerCode'
    }, {
        //客户名称
        name: 'customerName'
    },{
        //审核状态
        name: 'auditStatus'
    }, {
        //备注
        name: 'notes'
    }, {
        //是否删除
        name: 'isDelete',
        defaultValue: 'N'
    }, {
        //删除时间
        name: 'disableTime',
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
        //单据父类型
        name: 'billParentType'
    }, {
        //创建时间
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
        //应收应付部门编码
        name: 'orgCode'
    }, {
        //应收应付部门名称
        name: 'orgName'
    }]
});

writeoff.partnerStatementOfAwardAdd.statementFormatBillType = function (value, record) {
    var displayField = value;
    //如果为应收单，则读取应收单的单据子类型
    if (record.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
    } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE) {
    	displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__BILL_TYPE);
    } else {
        displayField = value;
    }
    return displayField;
}

//生成对账单
writeoff.partnerStatementOfAwardAdd.statementSave = function(){
	var	partnerStatementOfAwardAddGrid;
	partnerStatementOfAwardAddGrid = Ext.getCmp('T_writeoff-partnerStatementOfAwardAdd_content').getGrid();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.customerStatementAdd.statementSave'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(partnerStatementOfAwardAddGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementCommon.noDataToExport'));
				return false;
		  	}
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				if(writeoff.partnerStatementOfAwardAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					//应收应付单号集合
					var numbers = [];
					//添加对账单明细列表
					var data = partnerStatementOfAwardAddGrid.store.data;
					//添加对账单明细条数
					var length = data.items.length;
					//循环编译单号集合
					for(var i=0;i<length;i++){
						numbers.push(data.items[i].get('waybillNo'));
					}
					if(Ext.isEmpty(writeoff.partnerStatementOfAwardAdd.customerCode)){
						Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.validateFailAlertByNoCustomer'));
					 	return false;
					}
					searchParams = {
							//封装应收单的来源单号，用于判断运单是否存在未受理的更改单
							'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate':writeoff.partnerStatementOfAwardAdd.periodBeginDate,	
							'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate':writeoff.partnerStatementOfAwardAdd.periodEndDate,	
							'partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode':writeoff.partnerStatementOfAwardAdd.customerCode,
							'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':writeoff.partnerStatementOfAwardAdd.queryTabType,
//							'partnerStatementOfAwardVo.partnerStatementOfAwardDto.billDetailTypes':writeoff.partnerStatementOfAwardAdd.billDetailTypes
						};
				}else{
					var numbers = stl.splitToArray(writeoff.partnerStatementOfAwardAdd.numbers);
					searchParams = {
						'partnerStatementOfAwardVo.partnerStatementOfAwardDto.numbers':numbers,
						'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':writeoff.partnerStatementOfAwardAdd.queryTabType,
//						'partnerStatementOfAwardVo.partnerStatementOfAwardDto.billDetailTypes':writeoff.partnerStatementOfAwardAdd.billDetailTypes
					};
				}
				//拼接vo，注入到后台   运用AJAX+javascript调用后台数据库的数据，将后台数据库的数据调用前台页面
				Ext.Ajax.request({
				    url: writeoff.realPath('partnerStatementOfAwardSave.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var statementBillNo = result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.statementBillNo;
				    	Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
							'合伙人奖罚对账单保存成功,对账单单号:'+ statementBillNo);
				    	//清空列表
				    	partnerStatementOfAwardAddGrid.store.removeAll();
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

//根据账期时间和客户编码查询
writeoff.partnerStatementOfAwardAdd.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		writeoff.partnerStatementOfAwardAdd.periodBeginDate = form.findField('periodBeginDate').getValue();
		writeoff.partnerStatementOfAwardAdd.periodEndDate = form.findField('periodEndDate').getValue();
		writeoff.partnerStatementOfAwardAdd.customerCode = form.findField('customerCode').getValue();
		writeoff.partnerStatementOfAwardAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		//获取选择的单据子类型
		writeoff.partnerStatementOfAwardAdd.billDetailTypes = form.getValues().billType;
		var grid = Ext.getCmp('T_writeoff-partnerStatementOfAwardAdd_content').getGrid();
		if(Ext.isEmpty(writeoff.partnerStatementOfAwardAdd.customerCode)){
			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.customerIsNullWarning'));
		 	return false;
		}
		//开始日期
		var periodBeginDate = writeoff.partnerStatementOfAwardAdd.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.partnerStatementOfAwardAdd.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//校验单据子类型
//		var selectBillTypes = writeoff.partnerStatementOfAwardAdd.billDetailTypes;
//		if(selectBillTypes==undefined){
//			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
//					writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
//			return false;
//		}
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList) 
							||result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList.length==0){
						Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

//根据单号查询
writeoff.partnerStatementOfAwardAdd.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(numbers)!=null && Ext.String.trim(numbers)!=''){
		var billNumberArray = stl.splitToArray(numbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.partnerStatementOfAwardAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
		writeoff.partnerStatementOfAwardAdd.numbers = numbers;
		//获取选择的单据子类型
		writeoff.partnerStatementOfAwardAdd.billDetailTypes = form.getValues().billType;
		//校验单据子类型
//		var selectBillTypes = writeoff.partnerStatementOfAwardAdd.billDetailTypes;
//		if(selectBillTypes==undefined){
//			Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
//					writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
//			return false;
//		}
		var grid = Ext.getCmp('T_writeoff-partnerStatementOfAwardAdd_content').getGrid();
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList) 
							||result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList.length==0){
						Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

//查询时store
Ext.define('Foss.partnerStatementOfAwardAdd.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.partnerStatementOfAwardAdd.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryPartnerStatementOfAwardD.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.partnerStatementOfAwardAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER) {
			    searchParams = {
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate':writeoff.partnerStatementOfAwardAdd.periodBeginDate,	
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate':writeoff.partnerStatementOfAwardAdd.periodEndDate,	
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode':writeoff.partnerStatementOfAwardAdd.customerCode,
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':writeoff.partnerStatementOfAwardAdd.queryTabType,
//					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.billDetailTypes':writeoff.partnerStatementOfAwardAdd.billDetailTypes
				};
			} else {
				var numbers = stl.splitToArray(writeoff.partnerStatementOfAwardAdd.numbers);
				searchParams = {
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.numbers':numbers,
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':writeoff.partnerStatementOfAwardAdd.queryTabType,
					'partnerStatementOfAwardVo.partnerStatementOfAwardDto.receivableUnified':writeoff.partnerStatementOfAwardAdd.receivableUnified
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		},
		'load':function(store, operation, eOpts) {
			var data = store.getProxy().getReader().rawData;
	        if(data==undefined){
	            Ext.MessageBox.alert(writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.alert'),
						writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.noResult'));
	            return ;
	        }
	       if(data.success==false){
	         Ext.MessageBox.alert("温馨提示",data.message);
	           return ;
	        }
	       if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto != null ) {
	    	   //显示基本信息的表单
	    	   var baseInfo = Ext.getCmp('T_writeoff-partnerStatementOfAwardAdd_content').getBaseInfo();
	    	   baseInfo.show();
	    	   //设置对账单类型
	    	   Ext.getCmp('billType').setValue("");
	    	   //合伙人编码
	    	   Ext.getCmp('customerCode').setValue("");
	    	   //合伙人名称
	    	   Ext.getCmp('customerName').setValue("");
	    	   //账期开始日期
	    	   Ext.getCmp('periodBeginDate').setValue("");
	    	   //账期结束日期
	    	   Ext.getCmp('periodEndDate').setValue("");
	    	   //所属公司编码
	    	   Ext.getCmp('companyCode').setValue("");
	    	   //所属公司名称
	    	   Ext.getCmp('companyName').setValue("");
	    	   //所属部门编码
	    	   Ext.getCmp('createOrgCode').setValue("");
	    	   //所属部门名称
	    	   Ext.getCmp('createOrgName').setValue("");
	    	   //部门标杆
	    	   Ext.getCmp('unifiedCode').setValue("");
	    	   //账号
	    	   Ext.getCmp('companyAccountBankNo').setValue("");
	    	   //开户名
	    	   Ext.getCmp('accountUserName').setValue("");
	    	   //省市支行
	    	   Ext.getCmp('bankBranchName').setValue("");
	    	   //本期未还金额
	    	   Ext.getCmp('unpaidAmount').setValue("");
	    	   //设置对账单类型
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.billType != null) {
	    		   //Ext.getCmp('billType').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.billType);
	    		   Ext.getCmp('billType').setValue('合伙人奖罚对账单');
	    	   }
	    	   //设置合伙人编码
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode != null) {
	    		   Ext.getCmp('customerCode').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode);
	    	   }
	    	   //合伙人名称
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerName != null) {
	    		   Ext.getCmp('customerName').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerName);
	    	   }
	    	   //账期开始日期
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate != null) {
	    		   Ext.getCmp('periodBeginDate').setValue(new Date(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate));
	    	   }
	    	   ///账期结束日期
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate != null) {
	    		   Ext.getCmp('periodEndDate').setValue(new Date(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate));
	    	   }
	    	   //所属公司编码
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyCode != null) {
	    		   Ext.getCmp('companyCode').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyCode);
	    	   }
	    	   //所属公司名称
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyName != null) {
	    		   Ext.getCmp('companyName').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyName);
	    	   }
	    	   //所属部门编码
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.createOrgCode != null) {
	    		   Ext.getCmp('createOrgCode').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.createOrgCode);
	    	   }
	    	   //所属部门名称
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.createOrgName != null) {
	    		   Ext.getCmp('createOrgName').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.createOrgName);
	    	   }
	    	   //部门标杆
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unifiedCode != null) {
	    		   Ext.getCmp('unifiedCode').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unifiedCode);
	    	   }
	    	   //账号
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyAccountBankNo != null) {
	    		   Ext.getCmp('companyAccountBankNo').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.companyAccountBankNo);
	    	   }
	    	   //开户名
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.accountUserName != null) {
	    		   Ext.getCmp('accountUserName').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.accountUserName);
	    	   }
	    	   //省市支行
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.bankBranchName != null) {
	    		   Ext.getCmp('bankBranchName').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.bankBranchName);
	    	   }
	    	   //本期未还金额
	    	   if(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unpaidAmount != null) {
	    		   Ext.getCmp('unpaidAmount').setValue(data.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unpaidAmount);
	    	   }
	    	   
	       }
		}
	}
});

Ext.define('Foss.partnerStatementOfAwardAdd.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementCommon.statementEntry'),//对账单明细
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//最多输入10个单号，以半角的,或;隔开
	emptyText:writeoff.partnerStatementOfAwardAdd.i18n('查询结果为空！'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.partnerStatementOfAwardAdd.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
		dataIndex:'unverifyAmount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.totalAmount'),
		dataIndex: 'amount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.number'),
		dataIndex:'sourceBillNo'
	},{
		header: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.origSourceBillNo'),
		dataIndex: 'origSourceBillNo'
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.billParentType'),
		dataIndex:'billParentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
    		return displayField;
		}
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.billType'),
		dataIndex:'billType',
		renderer:writeoff.partnerStatementOfAwardAdd.billType
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex:'customerName'
	},{
		header: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex:'verifyAmount',
		hidden:true,
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex:'orgCode'
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex:'orgName'
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex:'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
			return displayField;
		}
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.notes'),
		dataIndex:'notes',
		hidden:true
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.isDelete'),
		dataIndex:'isDelete',
		hidden:true
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		dataIndex:'statementBillNo',
		hidden:true
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.disableTime'),
		dataIndex:'disableTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex:'createTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:'id',
		dataIndex:'id',
		hidden:true
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
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
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.9,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 },{
				xtype:'button',
				text:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),
				columnWidth:.1,
				hidden:!writeoff.partnerStatementOfAwardAdd.isPermission('/stl-web/writeoff/partnerStatementOfAwardSave.action'),
				handler:writeoff.partnerStatementOfAwardAdd.statementSave
			}]
		}];
		me.callParent();
	}
});

/**
 * 基本对账单信息--baseInfo
 */
Ext.define('Foss.partnerStatementOfAwardAdd.BaseInfo',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	hidden:true,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:65,
		margin:'5 5 5 10',
		readOnly:true
	},
	items:[{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.billType'),
		name:'billType',
		id:'billType',
		xtype:'combo',
		labelWidth:75,
		columnWidth:.23,
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode'
	},{
		xtype:'datefield',
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.createTime'),
		name:'createTime',
		id:'createTime',
		format:'Y-m-d H:i:s',
		columnWidth:.28
	},{
		xtype:'commoncustomerselector',
		listWidth:300,
		labelWidth:80,
		fieldLabel:'合伙人编码',
		singlePeopleFlag:'Y',
		contcatFlag :'Y',//增加按手机号查询
		name:'customerCode',
		id:'customerCode',
		columnWidth:.28
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		name:'periodBeginDate',
		id:'periodBeginDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.22
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.companyName'),
		name:'companyName',
		id:'companyName',
		columnWidth:.28
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.companyCode'),
		name:'companyCode',
		id:'companyCode',
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:'合伙人名称',
		name:'customerName',
		id:'customerName',
		labelWidth:95,
		columnWidth:.24
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		name:'confirmTime',
		id:'confirmTime',
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		name:'periodEndDate',
		id:'periodEndDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.22
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
		name:'createOrgName',
		id:'createOrgName',
		columnWidth:.28
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.createOrgCode'),
		name:'createOrgCode',
		id:'createOrgCode',
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		name:'statementBillNo',
		id:'statementBillNo',
		columnWidth:.22
	},{
		fieldLabel:'id',
		name:'id',
		id:'id',
		columnWidth:.22,
		hidden:true
	},{
		xtype:'numberfield',
		fieldLabel:'versionNo',
		name:'versionNo',
		id:'versionNo',
		columnWidth:.22,
		hidden:true
	},{
		 xtype: 'component',
		 border:true,
	     autoEl: {
	       tag: 'hr'
	     },	
	      /* xtype:'box',
		 autoEl:{ tag:'div',style:'line-height:1px;border:1px solid;color:gray;'},*/
		 columnWidth:1
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		labelWidth:85,
		name:'unifiedCode',
		id:'unifiedCode',
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
				//html:'<span style="color:red;">'+writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCodeNotes')+'</span>'
			}
		}]
    },{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),
		name:'companyAccountBankNo',
		id:'companyAccountBankNo',
		columnWidth:.25
	},/*{
		xtype:'container',
		columnWidth:.28,
		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 0',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNoNotes')+'</span>'
			}
		}]
    },*/{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		name:'accountUserName',
		id:'accountUserName',
		columnWidth:.5
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
		name:'bankBranchName',
		id:'bankBranchName',
		columnWidth:.48
	},{
		 xtype: 'component',
		 border:true,
	   autoEl: {
	       tag: 'hr'
	   },	
		 columnWidth:1
	},{
		fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.unpaidAmount'),
		labelWidth:110,
		name:'unpaidAmount',
		id:'unpaidAmount',
		xtype:'numberfield',
		decimalPrecision:2,
		columnWidth:.5,
		value:0
	}]
});

//查询tab
Ext.define('Foss.partnerStatementOfAwardAdd.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardAdd.queryByPartner'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:920,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		    	labelWidth:80,
		    	name:'periodEndDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),+1)
		    },{
	        	//xtype:'commoncustomerselector',
	        	//合伙人查询公共选择器
	        	xtype: 'commonsaledepartmentselector',
	        	listWidth:300,
	        	all:'true',//查询所有(包含作废)
	        	name:'customerCode',
	        	emptyText:'客户编码和手机均可查询',
	        	contcatFlag :'Y',//增加按手机号查询
	        	singlePeopleFlag:'Y',
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardAdd.partnerInfo'),
	        	columnWidth:.3
	        },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.74,
					html: '&nbsp;'
				},{
					text:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.partnerStatementOfAwardAdd.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.queryByNo'),
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.number'),
				//emptyText:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
				columnWidth:.65,
				//regex:/^((YS)?[0-9]{7,10}[;,])*((YS)?[0-9]{7,10}[;,]?)$/i,
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:104
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+writeoff.partnerStatementOfAwardAdd.i18n('（备注：应收单号、应付单号、来源单号）')+'</span>'
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
					text:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.partnerStatementOfAwardAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.partnerStatementOfAwardAdd.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

//页面入口
Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	Ext.QuickTips.init();
	//查询表单
	var queryTab = Ext.create('Foss.partnerStatementOfAwardAdd.QueryTab');
	//创建基本信息面板
	var baseInfo = Ext.create('Foss.partnerStatementOfAwardAdd.BaseInfo');
	//查询结果列表
	var grid = Ext.create('Foss.partnerStatementOfAwardAdd.Grid');

	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-partnerStatementOfAwardAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getGrid:function(){
			return grid;
		},
		getBaseInfo:function(){
			return baseInfo;
		},
		getTab:function(){
			return queryTab;
		},
		items: [queryTab,baseInfo,grid],
		renderTo: 'T_writeoff-partnerStatementOfAwardAdd-body'
	});
});