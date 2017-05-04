pay.posCardManagement.dept = FossUserContext.getCurrentUserDept();// 当前登录部门
pay.posCardManagement.user = FossUserContext.getCurrentUser();// 当前登录用户
pay.posCardManagement.roles = FossUserContext.getCurrentUserRoleCodes();


//银行卡明细报表model
Ext.define('Foss.posCardManagement.posCardManagementGridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'tradeSerialNo'
	},{
		name:'serialAmount'
	},{	
		name:'cardDeptName'
	},{
		name:'cardDeptCode'
	},{
		name:'usedAmount'
	},{
		name:'unUsedAmount'		
	},{
		name:'cardTime'		
	},{
		name:'belongModule'		
	},{
		name:'businessAreaName'		
	},{
		name:'belongRegionName'		
	}/*,{
		name:'financeDeptName'		
	}*/,{
		name:'invoiceType'
	},{
		name:'invoiceNo'		
	},{
		name:'amount'		
	},{
		name:'occupateAmount'
	}/*,{
		name:'unVerifyAmount'		
	}*/
	,{
		//冻结状态
		name:'frozenStatus'  
	},{
		//冻结时间
		name:'frozenTime'
	},{
		//冻结金额
		name:'frozenAmount'
	}
	]
});
/**
 * Form重置方法
 */
pay.posCardManagement.posCardManagementQueryReset=function(){
	this.up('form').getForm().reset();
	var dept = this.up('form').getForm().findField('orgCode');
	// 重置部门为当前登录部门
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
/**
 * 按单号查询POS刷卡
 */
pay.posCardManagement.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//查询类型
	pay.posCardManagement.queryTabType = "NO";
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
		 	Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		 		pay.posCardManagement.i18n('foss.stl.pay.common.queryNosLimit'));//输入单号不能超过10个!
		 	return false;
		 }
	}else{
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.billNosIsNullWarning'));//输入单号不能为空！
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		pay.posCardManagement.numbers = numbers;
		var grid = pay.posCardManagement.panel.getPosCardGrid();//
		grid.getStore().loadPage(1);
	}else{
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}	
};

/**
 * 按交易流水号查询POS刷卡
 */
pay.posCardManagement.statementSelectByTradeSerialNos= function(){	
	var form  = this.up('form').getForm();	
	//输入交易流水号集合
	var tradeSerialNos = form.findField('tradeSerialNos').getValue();
	//查询类型
	pay.posCardManagement.queryTabType = "SN";
	if(Ext.String.trim(tradeSerialNos)!=null && Ext.String.trim(tradeSerialNos)!=''){
		 var tradeSerialNoArray = stl.splitToArray(tradeSerialNos);
		 for(var i=0;i<tradeSerialNoArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(tradeSerialNoArray[i])==''){
		 		billNumberArray.pop(tradeSerialNoArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(tradeSerialNoArray.length>10){
		 	Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		 		pay.posCardManagement.i18n('foss.stl.pay.common.querytradeSerialNosLimit'));//输入单号不能超过10个!
		 	return false;
		 }
	}else{
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNosIsNullWarning'));//输入单号不能为空！
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		pay.posCardManagement.tradeSerialNos = tradeSerialNos;
		var grid = pay.posCardManagement.panel.getPosCardGrid();//
		grid.getStore().loadPage(1);
	}else{
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}	
};

/**
 * 处理查询按钮
 */

pay.posCardManagement.settlementByDate = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//开始时间
		pay.posCardManagement.periodBeginDate = form.findField('periodBeginDate').getValue();
		//结束时间
		pay.posCardManagement.periodEndDate = form.findField('periodEndDate').getValue();
		
		//所属模块
		pay.posCardManagement.belongMoudleCode = form.findField('belongMoudleCode').getValue();
		//大区
		pay.posCardManagement.largeAreaCode = form.findField('largeAreaCode').getValue();
		//小区
		pay.posCardManagement.smallAreaCode = form.findField('smallAreaCode').getValue();
		//部门
		pay.posCardManagement.orgCode = form.findField('orgCode').getValue();
		
		//流水号
		pay.posCardManagement.tradeSerialNo = form.findField('tradeSerialNo').getValue();
		//单据号
		//pay.posCardManagement.invoiceNo = form.findField('invoiceNo').getValue();
		//未使用金额
		pay.posCardManagement.unUsedAmount = form.findField('unUsedAmount').getValue();
		//500
		
		//开始日期
		var periodBeginDate = pay.posCardManagement.periodBeginDate;
		//结束日期
		var periodEndDate = pay.posCardManagement.periodEndDate;
		
		pay.posCardManagement.queryTabType = "TD";
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.quryDateIsNullWarning'));//起始日期或结束日期间为空，不能进行查询!
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryDateMaxLimit'));//起始日期和结束日期间隔不能超过31天!
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
					pay.posCardManagement.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));//结束日期不能早于起始日期!
			return false;
		}
		/**
		 * 大区、小区、部门至少选择一样！
		 */
		if(!(!!form.findField('largeAreaCode').getValue()||!!form.findField('smallAreaCode').getValue()||!!form.findField('orgCode').getValue())){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),
				pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.validateRegionFailAlert'));//大区、小区、部门至少选择一样！
			return false;
		}
		//获取grid
		var grid = Ext.getCmp("T_pay-posCardManagement_content").getPosCardGrid();
		grid.getStore().loadPage(1);
	}else{
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}
}

//取消冻结交易流水金额
pay.posCardManagement.posCardListThaw = function(){
	//获取所有选中行
	var rows = pay.posCardManagement.panel.getPosCardGrid().getSelectionModel().getSelection();
	var posCardEntitys = new Array();
	var posCardEntity = null;
	//假如有选中数据
	if(rows.length != 0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].data.frozenStatus != 1 && rows[i].data.frozenStatus != 2 ){
				//只有冻结状态为未冻结跟部分冻结的才允许执行冻结操作
				Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.thawStatusError'));
			    return;
			} 
			if(rows[i].data.unUsedAmount <= 0 ){
		    	//只有未使用金额大于0的交易流水才可以冻结
		    	Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.frozenUnUsedAmountError'));
			    return;
		    }
			 if(rows[i].data.serialAmount >= rows[i].data.usedAmount 
			    		&& rows[i].data.frozenAmount > 0 
			    		&& rows[i].data.frozenAmount <= rows[i].data.unUsedAmount
			    		&& rows[i].data.unUsedAmount <= rows[i].data.serialAmount){
				//只有冻结状态全额冻结或部分冻结且冻结金额大于0的且存在未使用金额才可以取消冻结
				posCardEntity = {
						'tradeSerialNo':rows[i].data.tradeSerialNo
					};
				posCardEntitys.push(posCardEntity);
			}else{
				//只有冻结金额小于等于未使用的金额才可以取消冻结操作!
				Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.thawAmountError'));
				return;
			}
		}
		Ext.Ajax.request({
			url : pay.realPath('thawPostCard.action'),
			form : Ext.fly('downloadAttachFileForm'),
			method : 'post',
			jsonData : {
				'vo':{'posCardManageDto':{'posCardEntitys':posCardEntitys}}
			},
			isUpload : true,
			success : function(response) {
				Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
						.i18n('foss.stl.pay.common.thawSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				// 如果异常信息有值，则弹出提示
				if (!Ext.isEmpty(result.errorMessage)) {
					Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
					return false;
				}
			}
		});
	}
}

	//冻结交易流水金额
pay.posCardManagement.posCardListFrozen = function(){
		//获取所有选中行
		var rows = pay.posCardManagement.panel.getPosCardGrid().getSelectionModel().getSelection();
		var posCardEntitys = new Array();
		var posCardEntity = null;
		//假如有选中数据
		if(rows.length != 0){
			for(var i=0;i<rows.length;i++){
				if(rows[i].data.frozenStatus != 0 && rows[i].data.frozenStatus != 2 && rows[i].data.frozenStatus !=null){
					//只有冻结状态为未冻结跟部分冻结的才允许执行冻结操作
					Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.frozenStatusError'));
				    return;
				} 
			    if(rows[i].data.unUsedAmount <= 0 ){
			    	//只有未使用金额大于0的交易流水才可以冻结
			    	Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.frozenUnUsedAmountError'));
				    return;
			    }
			    if(rows[i].data.serialAmount > rows[i].data.usedAmount 
			    		&& rows[i].data.frozenAmount >= 0 
			    		&& rows[i].data.frozenAmount < rows[i].data.unUsedAmount
			    		&& rows[i].data.unUsedAmount <= rows[i].data.serialAmount){
					    //未使用金额小于
				    	posCardEntity = {
				    			'tradeSerialNo':rows[i].data.tradeSerialNo
				    	};
				    	posCardEntitys.push(posCardEntity);
					} else {
						//只有冻结金额小于未使用的金额才可以冻结操作!
				    	Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),pay.posCardManagement.i18n('foss.stl.pay.queryDisableBillRepayment.frozenAmountError'));
					    return;
					}
				}
			}
			Ext.Ajax.request({
				url : pay.realPath('frozenPostCard.action'),
				form : Ext.fly('downloadAttachFileForm'),
				method : 'post',
				jsonData : {
					'vo':{'posCardManageDto':{'posCardEntitys':posCardEntitys}}
				},
				isUpload : true,
				success : function(response) {
					Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
							.i18n('foss.stl.pay.common.frozenSuccess'));
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					// 如果异常信息有值，则弹出提示
					if (!Ext.isEmpty(result.errorMessage)) {
						Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
						return false;
					}
				}
			});
		}

//导出报表明细
pay.posCardManagement.posCardManagementListExport = function(){
	var columns, searchParams, arrayColumns, arrayColumnNames, grid;
	// 获取表格
	var grid = pay.posCardManagement.panel.getPosCardGrid();
	//grid = Ext.getCmp('T_pay-posCardManagement_content').getPosCardGrid();
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement.i18n('foss.stl.pay.queryBillPayable.noDataToExport'));
		return false;
	}
	// 进行导出提示
	Ext.MessageBox.show({
				title : pay.posCardManagement.i18n('foss.stl.pay.common.alert'),
				msg : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.isExport'),
				buttons : Ext.MessageBox.YESCANCEL,
				buttonText : {
					yes : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.defaultColumnExport'),
					/*no : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.customColumnExport'),*/
					cancel : pay.posCardManagement.i18n('foss.stl.pay.common.cancel')
				},
				 
				fn : function(e) {
					if (e == 'yes') {
						arrayColumns = ['tradeSerialNo', 'serialAmount', 'cardDeptName', 'usedAmount', 'unUsedAmount','cardTime',
								'belongModule','businessAreaName', 'belongRegionName','businessDeptName'/*,'financeDeptName'*/, 'invoiceType', 'invoiceNo',
								'amount'/*,'unVerifyAmount'*/,'occupateAmount'];
						// 默认显示列名称
						arrayColumnNames = [
							    pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNo'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.flowAmout'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardDeptName'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.usedAmount'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unUsedAmount'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardTime'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongModule'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessRegion'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.bigRegion'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessDeptName'),
								/*pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.financeDept'),*/
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceType'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceNo'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.amount'),
								/*pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unVerifyAmount'),*/
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.occupateAmount')];
					} else if (e == 'no') {
						// 转化列头和列明
						columns = grid.columns;
						arrayColumns = [];
						arrayColumnNames = [];
						// 将前台对应列头传入到后台去
						for (var i = 1; i < columns.length; i++) {
							if (columns[i].isHidden() == false) {
								var hederName = columns[i].text;
								var dataIndex = columns[i].dataIndex;
								if (columns[i].text != pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.actionColumn')) {
									arrayColumns.push(dataIndex);
									arrayColumnNames.push(hederName);
								}
							}
						}
					} else {
						return false;
					}

					if (!Ext.fly('downloadAttachFileForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					
					//获取选中的行
					var selectionModel = grid.getSelectionModel();
					var rows= selectionModel.getSelection(); //获取所有选中行
					
					var jsonData = new Array();
					var serialNos = null;
					//假如有选中数据
					if(rows.length != 0){
						for(var i=0;i<rows.length;i++){
							jsonData.push(rows[i].data.tradeSerialNo);
						}
						//设置选中的交易流水号
						serialNos = jsonData;
					}
					if(pay.posCardManagement.queryTabType == 'TD'){
						searchParams = {
							'vo.posCardManageDto.periodBeginDate':pay.posCardManagement.periodBeginDate,//开始时间
							'vo.posCardManageDto.periodEndDate':pay.posCardManagement.periodEndDate,
							'vo.posCardManageDto.belongMoudleCode':pay.posCardManagement.belongMoudleCode,
							'vo.posCardManageDto.largeAreaCode':pay.posCardManagement.largeAreaCode,
							'vo.posCardManageDto.smallAreaCode':pay.posCardManagement.smallAreaCode,
							'vo.posCardManageDto.orgCode':pay.posCardManagement.orgCode,
							'vo.posCardManageDto.tradeSerialNo':pay.posCardManagement.tradeSerialNo,
							//'vo.posCardManageDto.invoiceNo':pay.posCardManagement.invoiceNo,
							'vo.posCardManageDto.unUsedAmount':pay.posCardManagement.unUsedAmount,//未使用金额
							'vo.posCardManageDto.arrayColumns' : arrayColumns,
							'vo.posCardManageDto.arrayColumnNames' : arrayColumnNames,
							'vo.posCardManageDto.serialNos' : serialNos,
							'vo.posCardManageDto.queryTabType' : pay.posCardManagement.queryTabType,
							'vo.posCardManageDto.invoices': null
						};
					}else{
						var invoices = stl.splitToArray(pay.posCardManagement.numbers);
						searchParams = {
								'vo.posCardManageDto.arrayColumns' : arrayColumns,
								'vo.posCardManageDto.arrayColumnNames' : arrayColumnNames,
								'vo.posCardManageDto.serialNos' : serialNos,
								'vo.posCardManageDto.invoices':invoices
						};
					}
					var operation= grid.store.submitParams;
					var s = pay.posCardManagement.periodBeginDate;//开始时间
					//设置查询条件
					Ext.apply(operation,{
						params :searchParams
					});

					// 拼接vo，注入到后台
					Ext.Ajax.request({
								url : pay.realPath('exportPostCardDetail.action'),
								form : Ext.fly('downloadAttachFileForm'),
								method : 'post',
								params : searchParams,
								isUpload : true,
								/*success : function(response) {
									var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}
									Ext.ux.Toast.msg(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportSuccess'), 'success', 1000);
								},
								failure : function(response) {
									Ext.ux.Toast.msg(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportFailed'), 'error', 1000);
								}*/
								success : function(response) {
									/*var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}*/
									Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
											.i18n('foss.stl.pay.posCardManagement.exportSuccess'));
									/*Ext.ux.Toast.msg(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportSuccess'), 'success', 1000);
							*/	},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}
									/*Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportFailed'), 'error', 1000);*/
								}
							});
				}
			});
}

//资金部导出
pay.posCardManagement.posCardListExport = function(){
	var columns, searchParams, arrayColumns, arrayColumnNames, grid;
	// 获取表格
	var grid = pay.posCardManagement.panel.getPosCardGrid();
	//grid = Ext.getCmp('T_pay-posCardManagement_content').getPosCardGrid();
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement.i18n('foss.stl.pay.queryBillPayable.noDataToExport'));
		return false;
	}
	if(pay.posCardManagement.queryTabType == 'TD'){
		//判断时间
		//开始时间
		var beginDate = pay.posCardManagement.periodBeginDate;
		//结束时间
		var endDate = pay.posCardManagement.periodEndDate;
		var compareTwoDate = stl.compareTwoDate(beginDate,endDate);
		if(compareTwoDate>10){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryDateMax'));//资金部导出时间不能超过10天！
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
					pay.posCardManagement.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));//结束日期不能早于起始日期!
			return false;
		}
	}
		
	// 进行导出提示
	Ext.MessageBox.show({
				title : pay.posCardManagement.i18n('foss.stl.pay.common.alert'),
				msg : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.isExport'),
				buttons : Ext.MessageBox.YESCANCEL,
				buttonText : {
					yes : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.defaultColumnExport'),
					/*no : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.customColumnExport'),*/
					cancel : pay.posCardManagement.i18n('foss.stl.pay.common.cancel')
				},
				 
				fn : function(e) {
					if (e == 'yes') {
						arrayColumns = ['tradeSerialNo', 'serialAmount', 'cardDeptName', 'usedAmount', 'unUsedAmount','cardTime',
								'belongModule','businessAreaName', 'belongRegionName','businessDeptName'/*,'financeDeptName'*/, 'invoiceType', 'invoiceNo',
								'amount'/*,'unVerifyAmount'*/,'occupateAmount'];
						// 默认显示列名称
						arrayColumnNames = [
							    pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNo'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.flowAmout'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardDeptName'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.usedAmount'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unUsedAmount'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardTime'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongModule'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessRegion'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.bigRegion'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessDeptName'),
								/*pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.financeDept'),*/
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceType'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceNo'),
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.amount'),
								/*pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unVerifyAmount'),*/
								pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.occupateAmount')];
					} else if (e == 'no') {
						// 转化列头和列明
						columns = grid.columns;
						arrayColumns = [];
						arrayColumnNames = [];
						// 将前台对应列头传入到后台去
						for (var i = 1; i < columns.length; i++) {
							if (columns[i].isHidden() == false) {
								var hederName = columns[i].text;
								var dataIndex = columns[i].dataIndex;
								if (columns[i].text != pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.actionColumn')) {
									arrayColumns.push(dataIndex);
									arrayColumnNames.push(hederName);
								}
							}
						}
					} else {
						return false;
					}

					if (!Ext.fly('downloadAttachFileForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					
					//获取选中的行
					var selectionModel = grid.getSelectionModel();
					var rows= selectionModel.getSelection(); //获取所有选中行
					
					var jsonData = new Array();
					var serialNos = null;
					//假如有选中数据
					if(rows.length != 0){
						for(var i=0;i<rows.length;i++){
							jsonData.push(rows[i].data.tradeSerialNo);
						}
						//设置选中的交易流水号
						serialNos = jsonData;
					}
					if(pay.posCardManagement.queryTabType == 'TD'){
						searchParams = {
							'vo.posCardManageDto.periodBeginDate':pay.posCardManagement.periodBeginDate,//开始时间
							'vo.posCardManageDto.periodEndDate':pay.posCardManagement.periodEndDate,
							'vo.posCardManageDto.belongMoudleCode':pay.posCardManagement.belongMoudleCode,
							'vo.posCardManageDto.largeAreaCode':pay.posCardManagement.largeAreaCode,
							'vo.posCardManageDto.smallAreaCode':pay.posCardManagement.smallAreaCode,
							'vo.posCardManageDto.orgCode':pay.posCardManagement.orgCode,
							'vo.posCardManageDto.tradeSerialNo':pay.posCardManagement.tradeSerialNo,
							//'vo.posCardManageDto.invoiceNo':pay.posCardManagement.invoiceNo,
							'vo.posCardManageDto.isExport':'true',
							'vo.posCardManageDto.arrayColumns' : arrayColumns,
							'vo.posCardManageDto.arrayColumnNames' : arrayColumnNames,
							'vo.posCardManageDto.serialNos' : serialNos,
							'vo.posCardManageDto.queryTabType':pay.posCardManagement.queryTabType,
							'vo.posCardManageDto.invoices': null
						};
					}else{
						var invoices = stl.splitToArray(pay.posCardManagement.numbers);
						searchParams = {
								'vo.posCardManageDto.isExport':'true',
								'vo.posCardManageDto.arrayColumns' : arrayColumns,
								'vo.posCardManageDto.arrayColumnNames' : arrayColumnNames,
								'vo.posCardManageDto.serialNos' : serialNos,
								'vo.posCardManageDto.invoices':invoices
						};
					}
					var operation= grid.store.submitParams;
					//设置查询条件
					Ext.apply(operation,{
						params :searchParams
					});

					// 拼接vo，注入到后台
					Ext.Ajax.request({
								url : pay.realPath('exportPostCardDetail.action'),
								form : Ext.fly('downloadAttachFileForm'),
								method : 'post',
								params : searchParams,
								isUpload : true,
								success : function(response) {
									/*var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}*/
									Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
											.i18n('foss.stl.pay.posCardManagement.exportSuccess'));
									/*Ext.ux.Toast.msg(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportSuccess'), 'success', 1000);
							*/	},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}
									/*Ext.Msg.alert(pay.posCardManagement.i18n('foss.stl.pay.common.alert'), pay.posCardManagement
													.i18n('foss.stl.pay.posCardManagement.exportFailed'), 'error', 1000);*/
								}
							});
				}
			});
}
//按日期查询 Form
Ext.define('Foss.posCardManagement.PaymentQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	height :200,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
    	colspan : 1, 
    	columnWidth:.10
	},
	layout:{
		type :'column'
	},
	items : [/*{
		xtype : 'datefield',
		name : 'periodBeginDate',
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.startPaymentDate'),
		value : stl.getTargetDate(new Date(),-3),
		validator : function(value) {
			if (value == null || value == '') {
				return pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.startPaymentDateIsNotNull');
			}
			return true;
		},
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
    	maxValue:new Date(),
    	columnWidth:.30*/
		{
	    	xtype:'datetimefield_date97',
	    	fieldLabel:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.startPaymentDate'),
	    	id:'FOSS_stl_posCardManagement_ID',
			time:true,
			name:'periodBeginDate',
			columnWidth:.30,
			editable:false,
			allowBlank : false,
			value: Ext.Date.format(stl.getTargetDate(new Date(),-3), 'Y-m-d')+' 00:00:00',
			validator : function(value) {
				if (value == null || value == '') {
					return pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.startPaymentDateIsNotNull');
				}
				return true;
			},
			dateConfig: {
				el: 'FOSS_stl_posCardManagement_ID-inputEl',
				dateFmt: 'yyyy-MM-dd',
				//最大选择日期
				maxDate: Ext.Date.format(stl.getTargetDate(new Date(),1), 'Y-m-d')+' 00:00:00'
			}
	},
	{
	    	xtype:'datetimefield_date97',
	    	fieldLabel:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.endPaymentDate'),
	    	id:'FOSS_stl_posCardManagement1_ID',
			time:true,
			name:'periodEndDate',
			columnWidth:.30,
			editable:false,
			allowBlank : false,
			value: Ext.Date.format(stl.getTargetDate(new Date(),1), 'Y-m-d')+' 00:00:00',
			validator : function(value) {
				if (value == null || value == '') {
					return pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.endPaymentDateIsNotNull');
				}
				return true;
			},
			dateConfig: {
				el: 'FOSS_stl_posCardManagement1_ID-inputEl',
				dateFmt: 'yyyy-MM-dd',
				//最大选择日期
				maxDate: Ext.Date.format(stl.getTargetDate(new Date(),1), 'Y-m-d')+' 00:00:00'
			}
	}/*{
		xtype : 'datefield',
		name : 'periodEndDate',
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.endPaymentDate'),
		value : new Date(),
		validator : function(value) {
			if (value == null || value == '') {
				return pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.endPaymentDateIsNotNull');
			}
			return true;
		},
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
    	maxValue:new Date(),
    	columnWidth:.30
	}*/,{
    	xtype: 'combobox',
		name:'belongMoudleCode',//所属模块
        fieldLabel: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongMoudle'),
		editable : false,
		labelWidth : 85,
		store:Ext.create('Ext.data.Store', {
    	    fields: ['valueName', 'valueCode'],
    	    data : [{
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.statement'),
						valueCode : 'ST'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.settle'),
						valueCode : 'SE'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.deposit'),
						valueCode : 'DE'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.waybill'),
						valueCode : 'WA'
					},{
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.KD'),
						valueCode : 'KD'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.queryBillPayable.all'),
						valueCode : 'ALL'
					}]
    	}),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		value : 'ALL',
		columnWidth : .3
    },{
		xtype : 'linkagecomboselector',
		eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		itemId : 'Foss_baseinfo_BigRegion_ID',
		store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
		columnWidth : .30,
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongRegion'),
		name : 'largeAreaCode',
		isPaging : true,
		allowBlank : true,
		value : '',
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	}, {
		xtype : 'linkagecomboselector',
		itemId : 'Foss_baseinfo_SmallRegion_ID',
		eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth : .30,
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessArea'),
		name : 'smallAreaCode',
		allowBlank : true,
		isPaging : true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
		},// 此处城市不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	}, {
		xtype : 'linkagecomboselector',
		eventType : ['callparent'],
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth : .30,
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessDeptName'),
		name : 'orgCode',
		allowBlank : true,
		isPaging : true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
		},// 此处区域不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},{
		xtype : 'textareafield',
		name : 'tradeSerialNo',//流水号
		fieldLabel : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNo'),
    	regex:/^([0-9]{12})$/i,
    	height:22,
    	editable:false,
		value:'',
		columnWidth:.30
	}/*,{
    	xtype: 'textareafield',
		name:'invoiceNo',//单据号
        fieldLabel: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceNo'),
		allowBlank: true,
		height:21,
		columnWidth:.30
    }*/,{
    	xtype: 'combobox',
		name:'unUsedAmount',//未使用金额
        fieldLabel: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unUsedAmount'),
		editable : false,
		labelWidth : 85,
		store:Ext.create('Ext.data.Store', {
    	    fields: ['valueName', 'valueCode'],
    	    data : [{
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.equals'),
						valueCode : '1'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.notequlas'),
						valueCode : '0'
					}, {
						valueName : pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.all'),
						valueCode : 'ALL'
					}]
    	}),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		value : 'ALL',
		columnWidth : .3
    },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[
		    {
		    	text:pay.posCardManagement.i18n('foss.stl.pay.common.reset'),
		  		columnWidth:.1,
		  		handler:pay.posCardManagement.posCardManagementQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.58
			},
		  	{
				  text:pay.posCardManagement.i18n('foss.stl.pay.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler: pay.posCardManagement.settlementByDate
		  	}]
		  	
	}]			
});
///stl-web/pay/isExport
//查询tab
Ext.define('Foss.posCardManagement.bankReportQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	columnWidth:1,
	height :300,
	items : [ {
		title: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryCondition'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.posCardManagement.PaymentQueryInfoByDate')
               ]
	},{
        title: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryByNumber'),//按单号查询
        tabConfig:{
   			width:150
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
				fieldLabel:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.number'),//单号
				allowBlank:false,
				columnWidth:.65,
				regex:/^([DUSZ0-9]{7,14}[;,])*([DUSZ0-9]{7,14})$/i,//329757-放开限制到14位运单号
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',//单号集合
				autoScroll:true,
				height:80
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryWaybillNo')+'</span>'
					}//请输入单据号
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
					text:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.reset'),//重置
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
					text:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.posCardManagement.statementSelectByNumber
				}]
        	}]
        }]
    
	},{
		//按流水号查询
        title: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryByTradeSerialNo'),
        tabConfig:{
   			width:150
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
				fieldLabel:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNo'),//交易流水号
				allowBlank:false,
				columnWidth:.65,
				regex:/^([0-9]{12}[;,])*([0-9]{12})$/i,
				labelWidth:70,
				labelAlign:'right',
				name:'tradeSerialNos',//交易流水号集合
				autoScroll:true,
				height:80
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.queryByTradeSerialNos')+'</span>'
					}//请输入单据号
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
					text:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.reset'),//重置
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
					text:pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.posCardManagement.statementSelectByTradeSerialNos
				}]
        	}]
        }]
	}]
});




/**
 * 查询后台数据
 */
Ext.define('Foss.posCardManagement.posCardManagementGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.posCardManagement.posCardManagementGridModel',
	pageSize: pay.posCardManagement.ONEPAGESIZE,
	proxy:{
		type:'ajax',
		timeout: 420000,
		url:pay.realPath('queryPostCard.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vo.posCardManageDto.posCardEntitys',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(pay.posCardManagement.queryTabType == 'TD'){
				searchParams = {
						'vo.posCardManageDto.periodBeginDate':pay.posCardManagement.periodBeginDate,//开始时间
						'vo.posCardManageDto.periodEndDate':pay.posCardManagement.periodEndDate,
						'vo.posCardManageDto.belongMoudleCode':pay.posCardManagement.belongMoudleCode,
						'vo.posCardManageDto.largeAreaCode':pay.posCardManagement.largeAreaCode,
						'vo.posCardManageDto.smallAreaCode':pay.posCardManagement.smallAreaCode,
						'vo.posCardManageDto.orgCode':pay.posCardManagement.orgCode,
						'vo.posCardManageDto.tradeSerialNo':pay.posCardManagement.tradeSerialNo,
						'vo.posCardManageDto.queryTabType':'TD',
						//'vo.posCardManageDto.invoiceNo':pay.posCardManagement.invoiceNo,
						'vo.posCardManageDto.unUsedAmount':pay.posCardManagement.unUsedAmount//未使用金额
				};
			}else if(pay.posCardManagement.queryTabType == "NO"){
				var invoices = stl.splitToArray(pay.posCardManagement.numbers);
				searchParams = {
						'vo.posCardManageDto.invoices':invoices,
						'vo.posCardManageDto.queryTabType':'NO'
				};
			}else if(pay.posCardManagement.queryTabType == "SN"){
				var tradeSerialNos = stl.splitToArray(pay.posCardManagement.tradeSerialNos);
				searchParams = {
						'vo.posCardManageDto.serialNos':tradeSerialNos,
						'vo.posCardManageDto.queryTabType':'SN'
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			});
		}
	}
});

//报表列表
Ext.define('Foss.posCardManagement.bankReportGrid',{
	extend:'Ext.grid.Panel',
	/*id:'Foss.posCardManagement.bankReportGrid_Id',*/
	title: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.PaymentReportGridTitle'),
    frame:true,
	height:420,
	/*sortableColumns: false,*/
	store:Ext.create('Foss.posCardManagement.posCardManagementGridStore'),
	emptyText: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.PaymentReportGridEmptyText'),
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	viewConfig: {
		enableTextSelection: true
	},
	/*点击显示明细*/
	plugins : [ {   
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.posCardManagement.posManageDetailGrid'
	}],
	columns:[{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.tradeSerialNo'),
		dataIndex: 'tradeSerialNo'
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.flowAmout'),
		dataIndex: 'serialAmount'
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardDeptName'),
		dataIndex: 'cardDeptName',
		width:130
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.usedAmount'),
		dataIndex: 'usedAmount',
		width:130
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unUsedAmount'),
		dataIndex: 'unUsedAmount'
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.cardTime'),
		dataIndex: 'cardTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		},
		width:130
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongModule'),
		dataIndex: 'belongModule'		
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.businessArea'),
		dataIndex: 'businessAreaName'						
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.belongRegion'),
		dataIndex: 'belongRegionName'		
	}/*,{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.financeDept'),
		dataIndex: 'financeDeptName'				
	}*/
	,{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.frozenStatus'),
		dataIndex: 'frozenStatus',
		renderer:function(value){
			if(value == 2  || value == 1){
				return "冻结";
			}else{
				return "未冻结";
			}
		}
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.frozenTime'),
		dataIndex: 'frozenTime',
		renderer:function(value){
			if(value!=null && value != ""){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}	
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.frozenAmount'),
		dataIndex: 'frozenAmount'		
	}
	],
	constructor:function(config){
		var me = this
		me.dockedItems =[{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items: [{
				xtype:'button',
				text :pay.posCardManagement.i18n('foss.stl.pay.common.export'),
				columnWidth:.1,
				handler:pay.posCardManagement.posCardManagementListExport,
				disabled: pay.posCardManagement.isPermission('/stl-web/pay/exportButton'),
				hidden: pay.posCardManagement.isPermission('/stl-web/pay/exportButton')
			},{
				xtype:'button',
				text : pay.posCardManagement.i18n('foss.stl.pay.common.export'),////pay.posCardManagement.i18n('foss.stl.pay.common.export'),
				columnWidth:.1,
				handler:pay.posCardManagement.posCardListExport,//资金部导出
				disabled: !pay.posCardManagement.isPermission('/stl-web/pay/exportButton'),
				hidden: !pay.posCardManagement.isPermission('/stl-web/pay/exportButton')
			},{
				xtype:'button',
				text : pay.posCardManagement.i18n('foss.stl.pay.common.frozen'),
				columnWidth:.1,
				handler:pay.posCardManagement.posCardListFrozen,//冻结
//				disabled: !pay.posCardManagement.isPermission('/stl-web/pay/frozenButton'),
				hidden: !pay.posCardManagement.isPermission('/stl-web/pay/frozenButton')
			},{
				xtype:'button',
				text : pay.posCardManagement.i18n('foss.stl.pay.common.thaw'),
				columnWidth:.1,
				handler:pay.posCardManagement.posCardListThaw,//取消冻结
//				disabled: !pay.posCardManagement.isPermission('/stl-web/pay/thawButton'),
				hidden: !pay.posCardManagement.isPermission('/stl-web/pay/thawButton')
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',	
			defaults:{
					margin:'0 0 5 3'
				},		
			items: [{
						xtype:'standardpaging',
						store:me.store,
						columnWidth:1,
						plugins: Ext.create('Deppon.ux.PageSizePlugin',{
							maximumSize:200,
							sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200]]
						})
			}]
		}];	
		me.callParent();
	}
});

//明细
Ext.define('Foss.posCardManagement.posDetailGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.posCardManagement.posCardManagementGridModel',
	proxy:{
		type:'ajax',
		timeout: 420000,
		url:pay.realPath('queryPostCardDetail.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vo.posCardManageDto.posCardDetailEntitys',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			searchParams = {
					'vo.posCardManageDto.tradeSerialNo':pay.posCardManagement.tradeSerialNo
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			});
		}
	}
});

//报表列表明细
Ext.define('Foss.posCardManagement.posManageDetailGrid',{
	extend:'Ext.grid.Panel',
	/*id:'T_pay-posCardManagement.bankReportDetailGrid_Id',*/
    columnLines: true,
    //frame:true,
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	//height:220,
	width:530,
	store: null,//Ext.create('Foss.posCardManagement.posDetailGridStore'),
	//emptyText: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.PaymentReportGridEmptyText'),
	columns:[{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceType'),
		dataIndex: 'invoiceType',
		width:120,
		renderer:function(value){
			if(value!=null){
				if(value == "W1" || value == "W2" || value == "W3"){
					return "运单";
				}else if(value == "DZ"){
					return "对账单";
				}else if(value == "US"){
					return "预收单";
				}else if(value == "XP"){
					return "小票";
				}
			}else{
				return null;
			}
		}
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.invoiceNo'),
		dataIndex: 'invoiceNo',
		width:120
	},{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.amount'),
		dataIndex: 'amount',
		width:120
	}/*,{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.unVerifyAmount'),
		dataIndex: 'unVerifyAmount',
		width:120
	}*/,{
		header: pay.posCardManagement.i18n('foss.stl.pay.posCardManagement.occupateAmount'),
		dataIndex: 'occupateAmount',
		width:120
	}],
	constructor:function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.posCardManagement.posDetailGridStore');
		me.callParent([cfg]);
	},
	//绑定数据
	bindData : function(record,grid,rowBodyElement) {
		//获取交易流水号
		var serials = record.get('tradeSerialNo')
		//获取表格
		//var store = pay.posCardManagement.panel.getPosCardDetailGrid().getStore();
		store = rowBodyElement.getStore();
		//设置交易流水号
		pay.posCardManagement.tradeSerialNo = serials
		store.removeAll();
		//加载数据
		store.loadPage(1);
		//清空交易流水号
		pay.posCardManagement.tradeSerialNo = '';
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询条件tab
	var posCardManagementQueryInfoTab = Ext.create('Foss.posCardManagement.bankReportQueryInfoTab');
	//报表Grid
	var posCardGrid = Ext.create('Foss.posCardManagement.bankReportGrid');
	//定义一个panel
	pay.posCardManagement.panel = 
		Ext.create('Ext.panel.Panel',{
		id: 'T_pay-posCardManagement_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getPosCardGrid:function(){
			return posCardGrid;
		},
		// 获得查询FORM
		getposCardManagementQueryInfoTab : function() {
			return posCardManagementQueryInfoTab;
		},
		// 明细store
		/*getPosCardDetailGrid:function(){
			return posCardDetailGrid;
		},*/
		items: [posCardManagementQueryInfoTab,posCardGrid],
		renderTo: 'T_pay-posCardManagement-body',
		// 角色权限问题
		listeners : {
			'boxready' : function(th) {
				var roles = pay.posCardManagement.roles;
				var queryByDateTab = th.getposCardManagementQueryInfoTab().items.items[0].items.items[0];
				var orgName = queryByDateTab.getForm().findField('orgCode');
				// 设置部门为默认当前登录部门
				if (!Ext.isEmpty(stl.currentDept.code)) {
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = orgName.store;
					var key = orgName.displayField + '';
					var value = orgName.valueField + '';
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					store.loadRecords([m]);
					orgName.setValue(valueText);
				}
			}
		}
	});
});
