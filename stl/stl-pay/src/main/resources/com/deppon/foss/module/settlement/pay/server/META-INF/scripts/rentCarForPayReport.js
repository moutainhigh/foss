pay.rentCarForPayReport.QUERY_BY_DATE = "TD";//按日期查询
pay.rentCarForPayReport.QUERY_BY_RENTCAR_NO = "RCB";//按租车编号
pay.rentCarForPayReport.QUERY_BY_PAYABLE_NO = "TP";//按应付单号
pay.rentCarForPayReport.QUERY_BY_BUSINESS_NO = "BN";//按业务单号
pay.rentCarForPayReport.QUERY_BY_WORKFLOW_NO = "WO";//按工作流号
pay.rentCarForPayReport.QUERY_MAXDATE = 31;//报表查询时间范围
pay.rentCarForPayReport.QUERY_DEFAULTDATE = 10;//报表查询时间范围
pay.rentCarForPayReport.RADIO_QUERY_BY_USE_TIME = "UT";//按用车日期查询
pay.rentCarForPayReport.RADIO_QUERY_BY_RENT_TIME = "RT";//按租车日期查询

pay.rentCarForPayReport.costType_SALE = "JY";//经营
 

/**
 * 获取费用承担部门
 */
pay.rentCarForPayReport.costTypeDatas = FossDataDictionary.getDataByTermsCode('RENTCAR_COST_TYPE');
//剔除费用类型中付款类型的数据
if(!Ext.isEmpty(pay.rentCarForPayReport.costTypeDatas) 
		&& !Ext.isEmpty(pay.rentCarForPayReport.costTypeDatas.length)){
	for(var i=0;i<pay.rentCarForPayReport.costTypeDatas.length;i++){
		if(pay.rentCarForPayReport.costTypeDatas[i].extAttribute1=='PAY'){
			Ext.Array.remove(pay.rentCarForPayReport.costTypeDatas,pay.rentCarForPayReport.costTypeDatas[i]);
		}
	}
}

/**
 * 获取费用类型
 */
pay.rentCarForPayReport.getCostTypeDatas = function(deptType){
	var costTypeDataClone =  Ext.clone(pay.rentCarForPayReport.costTypeDatas);
	//如果部门性质为空，则返回空
	if(!Ext.isEmpty(deptType) && deptType==pay.rentCarForPayReport.costType_SALE){
		if(!Ext.isEmpty(costTypeDataClone) && !Ext.isEmpty(costTypeDataClone.length)){
			for(var i=0;i<costTypeDataClone.length;i++){
				if(costTypeDataClone[i].extAttribute2=='UNSALE'){
					Ext.Array.remove(costTypeDataClone,costTypeDataClone[i]);
				}
			}
		}
	}
	for(var i=0;i<costTypeDataClone.length;i++){
		if(costTypeDataClone[i].valueCode=='13'){
			Ext.Array.remove(costTypeDataClone,costTypeDataClone[i]);
		}
	}
	return costTypeDataClone;
}

/**
 * 设置查询参数
 */
pay.rentCarForPayReport.setQueryParams=function(){
	//判断是否进行过查询
	if(Ext.isEmpty(pay.rentCarForPayReport.queryTab)){
		return false;
	}
	//copy一份值，不然此处分页会有问题
	var searchParams = Ext.clone(pay.rentCarForPayReport.searchParams);
	searchParams.queryType = pay.rentCarForPayReport.queryTab;
	
	var vo = new Object();
	for(var p in searchParams){
		var aa = 'vo.dto.'+p;
		vo[aa]=searchParams[p];
	}
	return vo;
}

/**
 * 校验重复项
 */
pay.rentCarForPayReport.validateRepeat = function(rentCarNo){
	//获取重复项车次号
	pay.rentCarForPayReport.validateRepeatNo = rentCarNo;
	
	//判断是否要查看重复运单
	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isWatchRepeatWaybill'),function(o){
		if(o=='yes'){
			var repeatWin;
			if(Ext.isEmpty(repeatWin)){
				repeatWin = Ext.create('Foss.rentCarForPayReport.RepeatWindow');
			}
			repeatWin.down('grid').store.load();
			repeatWin.show();
		}
	});
}

/**
 * 重置
 */
pay.rentCarForPayReport.reset = function(){
	this.up('form').getForm().reset();
	//重置部门名称
	var dept = this.up('form').getForm().findField('rentCarDeptCode');
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
 * 查询
 */
pay.rentCarForPayReport.query = function(me){
	var form  = me.up('form').getForm();
	//判断查询条件是否合法
	if(form.isValid()){
		//按日期查询
		if(pay.rentCarForPayReport.QUERY_BY_DATE ==pay.rentCarForPayReport.queryTab){
			var startDate = form.findField('startDate').getValue();//获得开始日期
			var endDate = form.findField('endDate').getValue();//获得结束日期
			//比较开始和结束日期差额
			var compareTwoDate = stl.compareTwoDate(startDate,endDate);
			if(compareTwoDate>pay.rentCarForPayReport.QUERY_MAXDATE){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.startToEndAccountDateIsNotPaymentMaxDay'));
				return false;
			}else if(compareTwoDate<1){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayment.endAccountDateIsNotBeforeStartAccountDate'));
				return false;
			}
		}
		//获取查询参数
		pay.rentCarForPayReport.searchParams = form.getValues();
		//按日期查询
		if (!(pay.rentCarForPayReport.QUERY_BY_DATE ==pay.rentCarForPayReport.queryTab)){
			var billNos = pay.rentCarForPayReport.searchParams.billNos;
			var billNosArray_tmp = stl.splitToArray(billNos);
			var billNosArray=new Array();
			for(var i=0;i<billNosArray_tmp.length;i++){
				if(billNosArray_tmp[i].trim()!=''){
					billNosArray.push(billNosArray_tmp[i].trim());
				} 
			}
			 
			if(billNosArray.length==0){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayment.billNosNotInputAlert'));
			 	return false;
			}
			if(billNosArray.length>10){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayment.billNosInputToMore'));
			 	return false;
			}
			pay.rentCarForPayReport.searchParams.billNos = billNosArray;
		}
		var grid = pay.rentCarForPayReport.reportGrid;
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),rawData.message);
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				toolBars = grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');;
				toolBars[1].setValue(result.vo.dto.totalCount);
				toolBars[2].setValue(result.vo.dto.totalAmount);
				toolBars[3].setValue(result.vo.dto.totalTakeGoodsQyt);
	    	}
	       }
    	}); 
	}else{
		Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 预提
 */
pay.rentCarForPayReport.withholding = function(me){
	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isWithholding'),function(o){
 		//提示是否要删除
 		if(o=='yes'){
 			var grid = me.up('grid');
			var selections = grid.getSelectionModel().getSelection();
			//如果数组值为0表示没有选中
			if(selections.length==0){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.unSelectedTowithholding'));
				return false;
			}
			//声明要付款的单号集合
			var withholdingNos = [];
				//循环获取单号集合
			for(var i=0;i<selections.length;i++){
				//获取预提状态
				var withholdingStatus = selections[i].get('withholdingStatus');
				if(!Ext.isEmpty(withholdingStatus) && withholdingStatus!=pay.rentCarForPayReport.WITHHOLDING_STATUS_NOT_TRANSFER){
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),selections[i].get('rentCarNo')+pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.errorWithholdingStatus'));
					return false;
				}
				//校验报销状态
				if(!Ext.isEmpty(selections[i].get('reimbursement'))&& selections[i].get('reimbursement')=='Y'){
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),selections[i].get('rentCarNo')+pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.errorReimbursementToWithholding'));
					return false;
				}
				//获取租车编号
				withholdingNos.push(selections[i].get('rentCarNo'));
			}
			//获取支付状态
			pay.rentCarForPayReport.payablePayStatus = selections[0].get('payablePayStatus');
			
			Ext.Ajax.request({
				url:pay.realPath('withholding.action'),
				params:{
					'vo.dto.billNos':withholdingNos,
					'vo.dto.queryType':pay.rentCarForPayReport.QUERY_BY_RENTCAR_NO
				},
				timeout:60000*10,
				method:'POST',
				success:function(response){
					var result = Ext.decode(response.responseText);
					//获取预提实体
					var entity = result.vo.withholdingEntity;
					var list = result.vo.repeatList;
					var deptType = entity.costDeptType;
					var deptCode = entity.costDeptCode;var deptName = entity.costDeptName;
					//初始化预提界面
					if(Ext.isEmpty(pay.rentCarForPayReport.withholdingWin)){
						pay.rentCarForPayReport.withholdingWin = Ext.create('Foss.rentCarForPayReport.withholdingWindow');
					}
					var win = pay.rentCarForPayReport.withholdingWin;
					//获取预提界面的表单和grid
					var form = win.down('form');
					var grid = win.down('grid');
					//
					//重新加载数据到界面
					form.loadRecord(new Foss.rentCarForPayReport.WithholdingModel(entity));
					var costTypeStore = form.getForm().findField('costType').store;
					costTypeStore.removeAll(); costTypeStore.loadData(pay.rentCarForPayReport.getCostTypeDatas(deptType));
					if(!Ext.isEmpty(list)&& list.length>0){
						grid.store.loadData(list);
					}else{
						grid.store.removeAll();
					}
					//如果
					if((!Ext.isEmpty(pay.rentCarForPayReport.payablePayStatus) && pay.rentCarForPayReport.payablePayStatus=='Y')
						||!Ext.isEmpty(deptCode)){
						form.getForm().findField('costDate').setReadOnly(true);
						var costDeptCode = form.getForm().findField('costDeptCode');
						if(pay.rentCarForPayReport.payablePayStatus!='Y'){
							costDeptCode.setReadOnly(false);
						}else{
							costDeptCode.setReadOnly(true);
						}
						costDeptCode.setCombValue(deptName,deptCode);
					}else{
						form.getForm().findField('costDate').setReadOnly(false);
						var costDeptCode = form.getForm().findField('costDeptCode');
						costDeptCode.setReadOnly(false);
					}
					pay.rentCarForPayReport.withholdingWin.show();
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
 		}
 	});
}

/**
  * 删除预提明细
  * @param {} grid 表单
  * @param {} rowIndex 行标
  * @param {} colIndex 
  */
 pay.rentCarForPayReport.withholdingRemove = function(grid, rowIndex, colIndex){
 	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.isDelete'),function(o){
 		//提示是否要删除
 		if(o=='yes'){
 			//获取表单
 			var form = grid.up('window').items.items[0].getForm();
 			//获取金额组件
 			var amount = form.findField('amount');
 			//获取要删除行
 			var selection = grid.getStore().getAt(rowIndex);
 			//获取要删除行的本次付款金额
 			var currentAmount = selection.get('rentCarAmount');
 			//删除该条记录
 			grid.store.remove(selection);
 			//重置金额组件的金额
 			amount.setValue(stl.amountSub(amount.getValue(),currentAmount));
 		}
 	});
 }
/**
 * 取消租车标记
 */
pay.rentCarForPayReport.cancelMarketing = function(me){
	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.iscancelMarketing'),function(o){
 		//提示是否要删除
 		if(o=='yes'){
 			var grid = me.up('grid');
			var selections = grid.getSelectionModel().getSelection();
			//如果数组值为0表示没有选中
			if(selections.length==0){
				Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.unSelectedToCancelMarketing'));
				return false;
			}
			//声明要付款的单号集合
			var deleteNos = [];
				//循环获取单号集合
			for(var i=0;i<selections.length;i++){
				//校验应付单支付状态
				if(Ext.isEmpty(selections[i].get('payablePayStatus'))||selections[i].get('payablePayStatus')=='Y'){
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),selections[i].get('rentCarNo')+pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.errorPayStatusToCancelMarketing'));
					return false;
				}
				//获取预提状态
				var withholdingStatus = selections[i].get('withholdingStatus');
				if(!Ext.isEmpty(withholdingStatus) && withholdingStatus!=pay.rentCarForPayReport.WITHHOLDING_STATUS_NOT_TRANSFER){
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),selections[i].get('rentCarNo')+pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.errorWithholdingStatusToCMarketing'));
					return false;
				}
				//获取租车编号
				deleteNos.push(selections[i].get('rentCarNo'));
			}
			
			Ext.Ajax.request({
				url:pay.realPath('disableRentCar.action'),
				params:{
						'vo.dto.billNos':deleteNos
					},
				method:'POST',
				success:function(response){
					grid.store.load();
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.disableSuccess'));
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
 		}
 	});
}
/**
 * 保存预提
 */
pay.rentCarForPayReport.withholdingCommit = function(){
	var win = this.up('window'); var grid = win.down('grid'); var form = this.up('form');
	var loadMask = win.getLoadMask();
	var amount=form.getForm().findField('amount').getValue(); var costDeptName = form.getForm().findField('costDeptCode').getRawValue(); 
	//判断是否合法
	if(form.getForm().isValid()){
		Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isCommitwithholding',[amount,costDeptName]),function(o){
 			//提示是否要删除
	 		if(o=='yes'){
				if(grid.store.data.length==0){
					Ext.ux.Toast.msg(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.noDataToWithholding'), 'error', 3000);
					return false;
				}
				//如果为已付款，日期是默认的，不判断时间。如果手动选择，则需要判断日期必须在本月或上月
				if(pay.rentCarForPayReport.payablePayStatus!='Y'){
	 				//获取费用日期
	 				var costDate = form.getForm('form').findField('costDate').getValue();
	 				//获取本月最后一天
					var endDate = Ext.Date.getLastDateOfMonth(new Date());
					var startDate = Ext.Date.getFirstDateOfMonth(new Date());
					//获取上月第一天
					var targetDate  = Ext.Date.add(startDate,Ext.Date.MONTH,-1);
					//判断日期
					if(!Ext.Date.between(costDate,targetDate,endDate)){
	 					Ext.ux.Toast.msg(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.costDateWarning'), 'error', 3000);
						return false;
					}
				}
				
				//声明传入action参数
				var vo = new Object();
				var withholdingEntity = new Object();
				form.getForm().updateRecord(form.getForm().getRecord());
				//获取付款单头信息即界面form表单
			 	withholdingEntity = form.getForm().getRecord().data; 
			 	withholdingEntity.costDeptName = form.getForm().findField('costDeptCode').getRawValue();
			 	withholdingEntity.invoiceName = form.getForm().findField('invoiceCode').getRawValue();
			 	vo.withholdingEntity = withholdingEntity;
			 	//循环校验明细和总金额的值是否相等
			 	var jsonData = new Array(); var billNos = new Array();
			 	var dto = new Object();
			 	grid.store.each(function(record){
			 		jsonData.push(record.data);
			 		billNos.push(record.data.rentCarNo);
			 	});
			 	vo.repeatList = jsonData;
			 	vo.dto = dto; 
			 	//声明查询参数
			 	dto.billNos = billNos; dto.queryType = pay.rentCarForPayReport.QUERY_BY_RENTCAR_NO;
			 	dto.payablePayStatus = pay.rentCarForPayReport.payablePayStatus;
			 	loadMask.show();
			 	//保存预提
			 	Ext.Ajax.request({
			 		url:pay.realPath('saveWithholding.action'),
			 		actionMethods:'post',
			 		jsonData:{
			 			'vo':vo
			 		},
			 		success:function(response){
			 			loadMask.hide();
			 			//获取返回结果
			 			var result = Ext.decode(response.responseText);	
			 			//获取预提工作流号
			 			var workFlowNo = result.vo.dto.withholdingWorkFlowNo;
			 			//遮罩窗口
			 			Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),
			 						  pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.saveWithholdingSuccess')+workFlowNo);
			 			//关闭窗口
			 			win.close();
			 		},
			 		exception:function(response){
			 			loadMask.hide();
			 			var result = Ext.decode(response.responseText);
			 			//弹出异常提示
			 			Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),result.message);
			 		},
			 		unknownException:function(form,action){
			 			//隐藏掉遮罩
			 			loadMask.hide();
			 		},
			 		failure:function(form,action){
			 			//隐藏掉遮罩
			 			loadMask.hide();
			 		}
			 	});
	 		}
 		});
	}else{
		Ext.ux.Toast.msg(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), pay.rentCarForPayReport.i18n('foss.stl.pay.common.validateFailAlert'), 'error', 3000);
		return false;
	}
}
/**
 * 取消预提
 */
pay.rentCarForPayReport.withholdingCancel = function(){
	var win = this.up('window');
	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isExistwithholding'),function(o){
 		//提示是否要删除
 		if(o=='yes'){
 			if(!Ext.isEmpty(win)){
 				win.close();
 			}
 		}
 	});
}
/**
 * 导出临时租车
 */
pay.rentCarForPayReport.rentCarExport = function(){
	var columns, searchParams, arrayColumns, arrayColumnNames, grid;
	// 获取表格
	grid = pay.rentCarForPayReport.reportGrid;
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), pay.rentCarForPayReport
						.i18n('foss.stl.pay.queryBillPayable.noDataToExport'));
		return false;
	}
	// 进行导出提示
	Ext.Msg.confirm(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isExport'),function(o){
		if(o=='yes'){
			if (!Ext.fly('downloadAttachFileForm')) {
				var frm = document.createElement('form');
				frm.id = 'downloadAttachFileForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}

			// 拼接vo，注入到后台
			Ext.Ajax.request({
				url : pay.realPath('exportRentCar.action'),
				form : Ext.fly('downloadAttachFileForm'),
				method : 'post',
				params : pay.rentCarForPayReport.setQueryParams(),
				isUpload : true,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					// 如果异常信息有值，则弹出提示
					if (!Ext.isEmpty(result.errorMessage)) {
						Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), result.errorMessage);
						return false;
					}
					Ext.ux.Toast.msg(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), 
									 pay.rentCarForPayReport.i18n('foss.stl.pay.common.exportSuccess'), 'success', 1000);
				},
				failure : function(response) {
					Ext.ux.Toast.msg(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'), 
									 pay.rentCarForPayReport.i18n('foss.stl.pay.common.exportFailed'), 'error', 1000);
				}
			});	
		}
	});
}
/**
 * 预提和重复租车明细model
 */
Ext.define('Foss.rentCarForPayReport.ValidateRepeatModel',{
	extend:'Ext.data.Model',
	fields:[{ name:'payableNo',type:'String'},
			{ name:'rentCarNo',type:'String'},
			{ name:'rentCarUseType',type:'String'},
			{ name:'vehicleNo',type:'String'},
			{ name:'driverName',type:'String'},
			{ name:'driverCode',type:'String'},
			{ name:'driverPhone',type:'String'},
			{ name:'repeatTag',type:'String'},
			{ name:'rentCarAmount',type:'double'},
			{ 
			   name:'useCarDate',
			   type:'Date',
			   convert:function(value) {
					return stl.longToDateConvert(value);
			   }
			},
			{ name:'notes',type:'String'},
			{ name:'billNo',type:'String'},
			{ name:'version',type:'String'}]
});
/**
 * 运单号重复标记
 */
Ext.define('Foss.rentCarForPayReport.RepeatStore',{
	extend:'Ext.data.Store',
	model:'Foss.rentCarForPayReport.ValidateRepeatModel',
 	groupField: 'billNo',
	proxy:{
		type:'ajax',
		url: pay.realPath('validateRepeatModel.action'),
		actionMethods:'POST',
		reader : {
			type : 'json',
			root : 'vo.repeatList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeLoad':function(store, operation, eOpts){
			Ext.apply(operation, {
				params:{
					'vo.dto.rentCarNo':pay.rentCarForPayReport.validateRepeatNo
				}
			});
		}
	}
});

/**
 * 重复运单列表
 */
Ext.define('Foss.rentCarForPayReport.RepeatGrid',{
	extend:'Ext.grid.Panel',
	title:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.repeatWaybill'),
	frame:true,
 	bodyCls: 'autoHeight',
 	cls: 'autoHeight',
	store:Ext.create('Foss.rentCarForPayReport.RepeatStore'),
	features:Ext.create('Ext.grid.feature.Grouping',{
			       groupHeaderTpl: pay.rentCarForPayReport.i18n('foss.stl.pay.common.waybillNo')+': {name}',
			       hideGroupedHeader: true,
			       startCollapsed:true
			 }),
	defaults:{
	   align:'center'
	},
	columns: [{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.common.payableNo'),
     	dataIndex:'payableNo'
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarNo'),
     	dataIndex:'rentCarNo',
     	width:120
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType'),
     	dataIndex:'rentCarUseType',
		renderer:function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RENTCAR_USE_TYPE');
			return displayField;
		}
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
     	dataIndex:'vehicleNo',
     	width:100
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarAmount'),
     	dataIndex:'rentCarAmount',
     	width:80
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.notes'),
     	dataIndex:'notes'
     }]
});
/**
 * 多次标记window
 */
Ext.define('Foss.rentCarForPayReport.RepeatWindow',{
 		extend:'Ext.window.Window',
 		width:stl.SCREENWIDTH*0.7,
 		height:600,
 		modal:true,
 		constrainHeader: true,
 		closeAction:'hide',
 		layout:'fit',
 		items:[Ext.create('Foss.rentCarForPayReport.RepeatGrid')]
 	});

/**
 * 临时租车报表model
 */
Ext.define('Foss.rentCarForPayReport.GridModel',{
	extend:'Ext.data.Model',
	fields:[{ name:'payableNo',type:'String'},
			{ name:'rentCarNo',type:'String'},
			{ name:'payStatus',type:'String'},
			{ name:'payWorkFlowNo',type:'String'},
			{ name:'reimbursement',type:'String'},
			{ name:'vehicleNo',type:'String'},
			{ name:'rentCarUseType',type:'String'},
			{ name:'rentCarAmount',type:'double'},
			{ name:'costDept',type:'String'},
			{ name:'costDeptName',type:'String'},
			{ name:'costType',type:'String'},
			{ name:'businessDate',type:'Date',
			  convert:function(value) {
				  return stl.longToDateConvert(value);
			  }
			},
			{ name:'withholdingStatus',type:'String'},
			{ name:'withholdingWorkFlowNo',type:'String'},
			{ name:'withholdingResult',type:'String'},
			{ name:'costDate',type:'Date',
			  convert:function(value) {
				  return stl.longToDateConvert(value);
			  }
			},
			{ name:'division',type:'String'},
			{ name:'bigArea',type:'String'},
			{ name:'smallArea',type:'String'},
			{ name:'totalWeight',type:'double'},
			{ name:'totalVolume',type:'double'},
			{ name:'kilometre',type:'double'},
			{ name:'destination',type:'String'},
			{ name:'departure',type:'String'},
			{ 
			   name:'useCarDate',
			   type:'Date',
			   convert:function(value) {
					return stl.longToDateConvert(value);
			   }
			},
			{ name:'useCarReasion',type:'String'},
			{ name:'shallTakegoodsQyt',type:'double'},
			{ name:'actualTakegoodsQyt',type:'double'},
			{ name:'carType',type:'String'},
			{ name:'driverName',type:'String'},
			{ name:'rentCarTime',type:'Date',
			  convert:function(value) {
					return stl.longToDateConvert(value);
			   }
			},
			{ name:'rentCarDeptName',type:'String'},
			{ name:'rentCarDeptCode',type:'String'},
			{ name:'rentMarkUserInfo',type:'String'},
			{ name:'isRepeateMark',type:'String'},
			{ name:'payablePayStatus',type:'String'},
			{ name:'notes',type:'String'},
			{ name:'withholdingCostDeptName',type:'String'}]
			});
/**
 * 付款报表查询界面
 */
Ext.define('Foss.rentCarForPayReport.GridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.rentCarForPayReport.GridModel',
	pageSize:100,
	proxy : {
		type : 'ajax',
		url : pay.realPath('queryRentCarReport.action'),
		actionMethods:'POST',
		reader : {
			type : 'json',
			root : 'vo.resultList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeLoad':function(store, operation, eOpts){
			var vo = pay.rentCarForPayReport.setQueryParams();
			Ext.apply(operation, {
				params: vo
			});
		}
	}
});

/**
 * 预提entity
 */
Ext.define('Foss.rentCarForPayReport.WithholdingModel',{
	extend:'Ext.data.Model',
	fields:[{ name:'costDeptCode',type:'String'},
			{ name:'costDeptName',type:'String'},
			{ name:'createOrgCode',type:'String'},
			{ name:'costType',type:'String'},
			{ name:'invoiceCode',type:'String'},
			{ name:'amount',type:'double'},
			{ 
			   name:'costDate',
			   type:'Date',
			   convert:function(value) {
					return stl.longToDateConvert(value);
			   }
			},
			{ name:'notes',type:'String'}
		]
});

/**
 * 运单号重复标记
 */
Ext.define('Foss.rentCarForPayReport.WithholdingStore',{
	extend:'Ext.data.Store',
	model:'Foss.rentCarForPayReport.ValidateRepeatModel'
});
/**
  * 预提form
  */
 Ext.define('Foss.rentCarForPayReport.WithholdingForm',{
 	extend:'Ext.form.Panel',
 	title:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholding'),
 	frame:true,
 	bodyCls: 'autoHeight',
 	cls: 'autoHeight',
 	defaults:{
 		labelWidth:100,
 		columnWidth:.3,
 		labelAlign:'right'
 	},
 	padding:'5,5,5,5',
 	layout:'column',
 	items:[{
    	xtype:'commoncostcenterdeptSelector',
		name:'costDeptCode',
        fieldLabel: pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.costDept'),
        allowblank:true,
		listWidth:300,//设置下拉框宽度
		allowBlank:false,
		isPaging:true,
		listeners:{
			'change':function(th,newValue,oldValue,opts){
				if(!Ext.isEmpty(th.getValue()) && !Ext.isEmpty(th.valueModels)){
					//复制一份费用类型
					var costType = th.up('form').getForm().findField('costType');
					//获取费用类型
					var costTypeStore = th.up('form').getForm().findField('costType').store;
					costTypeStore.removeAll();costType.setValue(null);
					//部门类型
					var deptType = th.valueModels[0].get('typeCode');
					var targetSore = pay.rentCarForPayReport.getCostTypeDatas(deptType);
					//如果为经营，则需要删除
					costTypeStore.loadData(targetSore);
				}
			}
		}
	},{
		xtype : 'combobox',
		name : 'costType',
		editable : false,
		allowBlank:false,
		queryMode:'local',
		fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.costType'),
		store : FossDataDictionary.getDataDictionaryStore('RENTCAR_COST_TYPE',null,null,['001']),//
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		xtype : 'datefield',
		allowBlank : false,
		fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.costDate'),
		labelWidth : 85,
		name : 'costDate',
		format :'Y-m'
	},{
		xtype:'commonsubsidiaryselector',
		allowBlank:false,
 		fieldLabel:pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.invoiceHeadCode'),
 		readOnly:false,
 		name:'invoiceCode'
	},{
		xtype:'numberfield',
 		fieldLabel:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingAmount'),
 		readOnly:true,
 		name:'amount'
	},{
 		xtype:'textareafield',
 		columnWidth:.9,
 		allowBlank:false,
 		labelWidth:100,
 		height:60,
 		autoScroll:true,
 		fieldLabel:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.notes'),
 		name:'notes'
 	},{
 		border: 1,
 		xtype:'container',
 		columnWidth:1,
 		disabled:false,
 		readOnly:false,
 		defaultType:'button',
 		layout:'column',
 		items:[{
 			xtype:'container',
 			border:false,
 			html:'&nbsp;',
 			columnWidth:.7
 		},{
 			text:pay.rentCarForPayReport.i18n('foss.stl.pay.common.commit'),
 			columnWidth:.1,
 			handler:pay.rentCarForPayReport.withholdingCommit
 		},{
 			text:pay.rentCarForPayReport.i18n('foss.stl.pay.common.cancel'),
 			columnWidth:.1,
 			handler:pay.rentCarForPayReport.withholdingCancel
 		}]
 	
 	}]
 });
 
 /**
  * 预提明细
  */
 Ext.define('Foss.rentCarForPayReport.WithholdingDetail', {
 	extend:'Ext.grid.Panel',
    frame:true,
    title:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingDetail'),
    height:400,
    bodyCls: 'autoHeight',
 	cls: 'autoHeight',
    store:Ext.create('Foss.rentCarForPayReport.WithholdingStore'),
	defaults:{
	   align:'center'
	},
   	viewConfig : {  
   		enableTextSelection : true,
         stripeRows: false,
         getRowClass : function(record,rowIndex,rowParams,store){}  
     },
     columns: [{
     	xtype:'actioncolumn',
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.actionColumn'),
     	width:70,
     	align: 'center',
     	items:[{
 			iconCls : 'deppon_icons_delete',
 			tooltip:pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.delete'),
 			handler:function(grid, rowIndex, colIndex){
 				pay.rentCarForPayReport.withholdingRemove(grid, rowIndex, colIndex);
 			}
     	}]
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarNo'),
     	dataIndex:'rentCarNo'
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.common.payableNo'),
     	dataIndex:'payableNo'
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType'),
     	dataIndex:'rentCarUseType',
		renderer:function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RENTCAR_USE_TYPE');
			return displayField;
		}
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
     	dataIndex:'vehicleNo'
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.driverName'),
     	dataIndex:'driverName',
     	width:80
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarAmount'),
     	dataIndex:'rentCarAmount',
     	width:80
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.useCarDate'),
     	dataIndex:'useCarDate',
     	renderer:function(value){
     		return stl.dateFormat(value, 'Y-m-d');
     	}
     },{
     	header:pay.rentCarForPayReport.i18n('foss.stl.pay.addPayment.notes'),
     	dataIndex:'notes'
     }]
 });
 
 //初始化预提表单
 if(Ext.isEmpty(pay.rentCarForPayReport.withholdingForm)){
 	pay.rentCarForPayReport.withholdingForm = Ext.create('Foss.rentCarForPayReport.WithholdingForm');
 }
 if(Ext.isEmpty(pay.rentCarForPayReport.withholdingDetail)){
 	pay.rentCarForPayReport.withholdingDetail = Ext.create('Foss.rentCarForPayReport.WithholdingDetail');
 }
 
/**
 * 预提界面
 */		
Ext.define('Foss.rentCarForPayReport.withholdingWindow',{
	extend:'Ext.window.Window',
	title:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarWithholding'),
	width:stl.SCREENWIDTH*0.7,
	bodyCls: 'autoHeight',
 	cls: 'autoHeight',
	modal:true,
	constrainHeader: true,
	closeAction:'hide',
	loadMask:null,//遮罩
	getLoadMask:function(){
		var me = this;
		//获取遮罩效果
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me, {
				msg:pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.saveWithholding'),
			    removeMask : true// 完成后移除
			});
		}
		return me.loadMask;
	},
	items:[pay.rentCarForPayReport.withholdingForm,pay.rentCarForPayReport.withholdingDetail]
});			
			
/**
 * 临时租车查询
 */
Ext.define('Foss.rentCarForPayReport.QueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,// 默认显示按单号制作
	height : 270,
	items : [{
		title : pay.rentCarForPayReport.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig : {
			width : 120
		},
		layout : 'hbox',
		items : [{
			xtype : 'form',
			width : 850,
			margin : '10,5,5,5',
			defaults : {
				labelWidth : 85,
				margin : '5,5,5,5'
			},
			layout : 'column',
			items : [{
				xtype : 'fieldcontainer',
				defaultType : 'radiofield',
				name:'dateType',
				columnWidth : 1,
				layout : 'hbox',
				items : [{
						boxLabel : pay.rentCarForPayReport
								.i18n('foss.stl.pay.rentCarForPayReport.queryByUseCarDate'),
						checked : true,
						name : 'queryDateType',
						inputValue :pay.rentCarForPayReport.RADIO_QUERY_BY_USE_TIME,// 表单的参数值
						width : 120
					},{
						boxLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.queryByRentCarDate'),
						name : 'queryDateType',
						inputValue :pay.rentCarForPayReport.RADIO_QUERY_BY_RENT_TIME,// 表单的参数值
						width : 130
					}]
				},{
					xtype : 'datefield',
					allowBlank : false,
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.startDate'),
					labelWidth : 85,
					name : 'startDate',
					columnWidth : .3,
					format : 'Y-m-d',
					value : stl.getTargetDate(new Date(), -pay.rentCarForPayReport.QUERY_DEFAULTDATE)
				},{
					xtype : 'datefield',
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.endDate'),
					allowBlank : false,
					labelWidth : 85,
					name : 'endDate',
					format : 'Y-m-d',
					columnWidth : .3,
					value : new Date()
				},{
					xtype:'commonleasedvehicleselector',
					name : 'vehicleNo',
					isPaging : true,// 分页
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
					columnWidth : .3
				},{
					xtype : 'combobox',
					name : 'rentCarUseType',
					editable : false,
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType'),
					//RENTCAR_USE_TYPE
					store : FossDataDictionary.getDataDictionaryStore('RENTCAR_USE_TYPE', null, {
								'valueCode' : '',
								'valueName' : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.all')
							}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value : '',
					columnWidth : .3
				},{
					xtype : 'combobox',
					name : 'payStatus',
					editable : false,
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payStatus'),
					store : FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYMENT__REMIT_STATUS,null,{
						 'valueCode': '',
	               		 'valueName': pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayment.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value : '',
					columnWidth : .3
				},{
					xtype : 'combobox',
					name : 'withholdingStatus',
					editable : false,
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingStatus'),
					store : FossDataDictionary.getDataDictionaryStore('WITHHOLDING_STATUS', null, {
								'valueCode' : '',
								'valueName' : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.all')
							}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value : '',
					columnWidth : .3
				},{
			    	xtype: 'dynamicorgcombselector',
					name:'division',
					division:'Y',
			        fieldLabel: pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.division'),
					listWidth:300,//设置下拉框宽度
					isPaging:true, //分页
					columnWidth : .3
		    	},{			
					xtype : 'linkagecomboselector',
					eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					itemId : 'Foss_baseinfo_BigRegion_ID',
					store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
					fieldLabel :pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.largeRegion'),
					value:'',
					minChars : 0,
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					name : 'bigArea',
					allowBlank : true,
					listWidth : 300,// 设置下拉框宽度
					isPaging : true,
					queryParam : 'commonOrgVo.name',
					columnWidth : .3	
			    },{	    	
			    	xtype : 'linkagecomboselector',
					itemId : 'Foss_baseinfo_SmallRegion_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
					name : 'smallArea',
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.smallRegion'),
					parentParamsAndItemIds : {
						'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
					},// 此处城市不需要传入
					minChars : 0,
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					allowBlank : true,
					queryParam : 'commonOrgVo.name',
					listWidth : 300,// 设置下拉框宽度
					isPaging : true,
					columnWidth : .3	
		    	},{
			    	xtype: 'dynamicorgcombselector',
					name:'rentCarDeptCode',
			        fieldLabel: pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.dept'),
			        allowblank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true, //分页
		    		columnWidth : .3
		    	},{
					xtype : 'combobox',
					name : 'reimbursement',
					fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.reimbursement'),
					labelWidth : 120,
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN', null,
							{
								'valueCode' : '',
								'valueName' : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.all')
							}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value : '',
					columnWidth : .3
				}, {
					xtype:'commonleaseddriverselector',
				    listWidth:300,
				    name:'driverCode',
				    fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.common.leaseddriver'),
				    columnWidth : .3,
				    active:'Y'
			 	},{
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [{
						text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.reset'),
						columnWidth : .1,
						handler :pay.rentCarForPayReport.reset
					}, {
						xtype : 'container',
						border : false,
						html : '&nbsp;',
						columnWidth : .7
					}, {
						text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.query'),
						cls : 'yellow_button',
						columnWidth : .1,
						handler : function() {
							var me = this;
							pay.rentCarForPayReport.queryTab = pay.rentCarForPayReport.QUERY_BY_DATE;
							pay.rentCarForPayReport.query(me);
						}
					}]
			}]
		}]
	}, {
		title : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.queryByCarNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 175
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReport.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReport.queryTab = pay.rentCarForPayReport.QUERY_BY_RENTCAR_NO;
						pay.rentCarForPayReport.query(me);
					}
				}]
			}]
		}]
	},{
		title : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.queryByPayableNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 175
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReport.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReport.queryTab = pay.rentCarForPayReport.QUERY_BY_PAYABLE_NO;
						pay.rentCarForPayReport.query(me);					
					}
				}]
			}]
		}]
	},{
		title : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.queryByBusinessNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 175
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReport.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReport.queryTab = pay.rentCarForPayReport.QUERY_BY_BUSINESS_NO;
						pay.rentCarForPayReport.query(me);	
					}
				}]
			}]
		}]
	},{
		title : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.queryByWorkflowNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 175
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReport.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReport.queryTab = pay.rentCarForPayReport.QUERY_BY_WORKFLOW_NO;
						pay.rentCarForPayReport.query(me);	
					}
				}]
			}]
		}]
	}]
});

/**
 * 临时租车付款报表查询结果列表
 */
Ext.define('Foss.rentCarForPayReport.reportGrid', {
	extend : 'Ext.grid.Panel',
	title : pay.rentCarForPayReport
			.i18n('foss.stl.pay.rentCarForPayReport.rentCarGrid'),
	frame : true,
	height : 500,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	store : Ext.create('Foss.rentCarForPayReport.GridStore'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	defaults : {
		align : 'center',
		margin : '5 0 5 0'
	},
	viewConfig : {
		enableTextSelection : true
	},
	columns : [{
		header : pay.rentCarForPayReport
				.i18n('foss.stl.pay.common.actionColumn'),
		dataIndex : 'validateRepeat',
		renderer : function(data, metadata, record, rowIndex, columnIndex,
				store) {
			//获取是否重复标记
			var isRepeateMark = record.get("isRepeateMark");
			//重复则显示校验项
			if(!Ext.isEmpty(isRepeateMark) && isRepeateMark=='Y'){
				var carNo = record.get("rentCarNo");
				var href = "javascript: pay.rentCarForPayReport.validateRepeat('"
						+ carNo + "')";
				return '<a href="'
						+ href
						+ '">'
						+ pay.rentCarForPayReport
								.i18n('foss.stl.pay.rentCarForPayReport.validateRepeat')
						+ '</a>';
			}
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.common.payableNo'),
		dataIndex : 'payableNo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarNo'),
		dataIndex : 'rentCarNo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payStatus'),
		dataIndex : 'payStatus',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYMENT__REMIT_STATUS');
			return displayField;
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payWorkFlowNo'),
		dataIndex : 'payWorkFlowNo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.reimbursement'),
		dataIndex : 'reimbursement',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'FOSS_BOOLEAN');
			return displayField;
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
		dataIndex : 'vehicleNo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType'),
		dataIndex : 'rentCarUseType',
		renderer:function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RENTCAR_USE_TYPE');
			return displayField;
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarAmount'),
		dataIndex : 'rentCarAmount'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payCostDeptCode'),
		dataIndex : 'costDept',
		hidden:true
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payCostDept'),
		dataIndex : 'costDeptName'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.payCostType'),
		dataIndex : 'costType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RENTCAR_COST_TYPE');
			return displayField;
		}
		
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.businessDate'),
		dataIndex : 'businessDate',
		renderer : function(value) {
			return stl.dateFormat(value, 'Y-m-d  H:i:s');
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingStatus'),
		dataIndex : 'withholdingStatus',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'WITHHOLDING_STATUS');
			return displayField;
		}
	},{
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingCostDeptName'),
		dataIndex : 'withholdingCostDeptName'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingWorkFlowNo'),
		dataIndex : 'withholdingWorkFlowNo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholdingResult'),
		dataIndex : 'withholdingResult'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.costDate'),
		dataIndex : 'costDate',
		renderer : function(value) {
			return stl.dateFormat(value, 'Y-m');
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.division'),
		dataIndex : 'division'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.bigArea'),
		dataIndex : 'bigArea'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.smallArea'),
		dataIndex : 'smallArea'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.totalWeight'),
		dataIndex : 'totalWeight'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.totalVolume'),
		dataIndex : 'totalVolume'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.kilometre'),
		dataIndex : 'kilometre'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.destination'),
		dataIndex : 'destination'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.departure'),
		dataIndex : 'departure'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.useCarDate'),
		dataIndex : 'useCarDate',
		renderer : function(value) {
			return stl.dateFormat(value, 'Y-m-d  H:i:s');
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.useCarReasion'),
		dataIndex : 'useCarReasion'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.shallTakegoodsQyt'),
		dataIndex : 'shallTakegoodsQyt'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.actualTakegoodsQyt'),
		dataIndex : 'actualTakegoodsQyt'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.carType'),
		dataIndex : 'carType'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.driverName'),
		dataIndex : 'driverName'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarTime'),
		dataIndex : 'rentCarTime',
		renderer : function(value) {
			return stl.dateFormat(value, 'Y-m-d  H:i:s');
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentCarDeptName'),
		dataIndex : 'rentCarDeptName'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.rentMarkUserInfo'),
		dataIndex : 'rentMarkUserInfo'
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.isRepeateMark'),
		dataIndex : 'isRepeateMark',
		renderer:function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'FOSS_BOOLEAN');
			return displayField;
		}
	}, {
		header : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.notes'),
		dataIndex : 'notes'
	}],
	initComponent : function() {
		var me = this;
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'button',
				text : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.withholding'),
				columnWidth : .09,
				disabled:!pay.rentCarForPayReport.isPermission('/stl-web/pay/withholding.action'),
				hidden:!pay.rentCarForPayReport.isPermission('/stl-web/pay/withholding.action'),
				handler : pay.rentCarForPayReport.withholding
			},{
				xtype : 'button',
				text : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.cancelMarketing'),
				columnWidth : .1,
				disabled:!pay.rentCarForPayReport.isPermission('/stl-web/pay/disableRentCar.action'),
				hidden:!pay.rentCarForPayReport.isPermission('/stl-web/pay/disableRentCar.action'),
				handler : pay.rentCarForPayReport.cancelMarketing
			},{
				xtype : 'container',
				html : '&nbsp;',
				columnWidth : .71
			}, {
				xtype : 'button',
				text : pay.rentCarForPayReport.i18n('foss.stl.pay.common.export'),
				columnWidth : .09,
				handler : pay.rentCarForPayReport.rentCarExport
			}]
		}, {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'textfield',
				readOnly : true,
				name : 'total',
				columnWidth : .1,
				labelWidth : 40,
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.queryBillPayable.total')
			}, {
				xtype : 'textfield',
				readOnly : true,
				name : 'totalCount',
				columnWidth : .2,
				labelWidth : 75,
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.totalCount')
			}, {
				xtype : 'textfield',
				readOnly : true,
				name : 'totalAmount',
				columnWidth : .2,
				labelWidth : 75,
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.totalAmount')
			}, {
				xtype : 'textfield',
				readOnly : true,
				name : 'totalTakeGoodsQyt',
				columnWidth : .2,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReport.i18n('foss.stl.pay.rentCarForPayReport.trueTotalCount')
			}, {
				xtype : 'standardpaging',
				store : me.store,
				columnWidth : 1,
				pageSize:100,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					// 设置分页记录最大值，防止输入过大的数值
					maximumSize : 1000,
					sizeList : [['20', 20], ['50', 50],	['100', 100],['200', 200],['500', 500],['1000', 1000]]
				})
			}]
		}];
		me.callParent(); 
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.Ajax.timeout = 180000;
	var queryTab = Ext.create('Foss.rentCarForPayReport.QueryInfoTab');
	pay.rentCarForPayReport.reportGrid = Ext.create('Foss.rentCarForPayReport.reportGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-rentCarForPayReport_content', 
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items:[queryTab,pay.rentCarForPayReport.reportGrid],
		getQueryInfoTab:function(){
			return queryTab;
		},
		renderTo: 'T_pay-rentCarForPayReport-body',
		listeners : {
			'boxready' : function(th) {
				var roles = stl.currentUserDepts;
				var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
				var dept = queryByDateTab.getForm().findField('rentCarDeptCode');
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
