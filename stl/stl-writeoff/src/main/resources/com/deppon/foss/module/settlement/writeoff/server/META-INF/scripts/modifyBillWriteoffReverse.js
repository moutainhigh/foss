// 更改单查询日期限制
// 时间间隔最大不超过90天
//时间相差一月 、默认最大显示条数 、每页显示的条数
writeoff.modifyBill.STLBILLREPAYMENT_MAX = 90;
writeoff.modifyBill.STLBILLREPAYMENT_ONEMONTH = 10; 
writeoff.modifyBill.MAXSHOWNUM = 1000; 
writeoff.modifyBill.STLREVERSEWRITEOFF_ONEPAGESIZE = 50; 


//未核销、核销不通过、核销通过
writeoff.modifyBill.NO_WRITE_OFF='NO_WRITE_OFF';
writeoff.modifyBill.WRITE_OFF_FAILURE='WRITE_OFF_FAILURE';
writeoff.modifyBill.WRITE_OFF_SUCCESS='WRITE_OFF_SUCCESS';

//更改单的类型(转运单更改单、返货单更改单、更改单)
writeoff.modifyBill.TRANSFER='TRANSFER';
writeoff.modifyBill.RETURN='RETURN';


//更改单获取部门、用户、权限
writeoff.modifyBill.dept = FossUserContext. getCurrentUserDept();//当前登录部门
writeoff.modifyBill.user = FossUserContext.getCurrentUser();//当前登录用户
writeoff.modifyBill.roles = FossUserContext. getCurrentUserRoleCodes();
writeoff.modifyBill.emp=FossUserContext. getCurrentUserEmp();//获得当前用户对应的职员信息
writeoff.modifyBill.currentDeptCode=FossUserContext. getCurrentDeptCode();
writeoff.modifyBill.currentDept=FossUserContext. getCurrentDept();// 获取当前用户设置的当前部门

writeoff.modifyBill.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for ( var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

writeoff.modifyBill.deptInfo=FossUserContext. getCurrentDept();
//更改单查询
writeoff.modifyBill.queryByParam=function(form,me){
		var form,
		//运单号
		waybayNos,
		//运单号集合
		waybayNosArray,
		//受理部门
		acceptStartDate,
		//受理结束日期
		acceptEndDate,
		//大区
		largeArea,
		//小区
		smallArea,
		//起草部门编码				
		darftOrgCode,
		//起草部门
		darftOrgName,
		//审核状态
		writeoffStatus
		//输入框表单
	
	//运单号、运单号集合、受理开始日期和结束日期	
	waybayNos = form.findField('waybayNos').getValue();
	waybayNosArray = stl.splitToArray(waybayNos);
	acceptStartDate=form.findField('acceptStartDate').getValue();
	acceptEndDate=form.findField('acceptEndDate').getValue();
	
	//大区、小区、起草部门  、状态
	largeArea=form.findField('largeArea').getValue();
	smallArea=form.findField('smallArea').getValue();
	darftOrgCode=form.findField('darftOrgName').getValue();
	writeoffStatus=form.findField('writeoffStatus').getValue();
	productTypeList=form.findField('productTypeList').getValue();	
	
	
	if(waybayNos!=null&waybayNos!=''){
		for(var i=0;i<waybayNosArray.length;i++){
			if(waybayNosArray[i]==''){
				waybayNosArray.pop(waybayNosArray[i]);
			}
		}
		if(waybayNosArray.length>10){
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.maxInputChangeWaybillWarning'));
		 	return false;
		}
	}
	
	
	//将数据copy到隐藏域中
	var hiddenfield = Ext.getCmp('T_writeoff-modifyBillWriteoffReverse_content').getHiddenField().getForm();
	hiddenfield.findField('waybayNosParam').setValue(waybayNos);
	hiddenfield.findField('acceptStartDateParam').setValue(acceptStartDate);
	hiddenfield.findField('acceptEndDateParam').setValue(acceptEndDate);
	hiddenfield.findField('largeAreaParam').setValue(largeArea);
	hiddenfield.findField('smallAreaParam').setValue(smallArea);
	hiddenfield.findField('darftOrgNameParam').setValue(darftOrgCode);
	hiddenfield.findField('writeoffStatusParam').setValue(writeoffStatus);
	hiddenfield.findField('productTypeListParam').setValue(productTypeList);
	

	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {

				var rawData = Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.proxy.reader.rawData;

				if(!success && ! rawData.isException){
					Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.pay.common.alert'),rawData.message);
					me.enable(true);
					return false;
				}
				
				if(success){
				var result = Ext.decode(operation.response.responseText); 
					//判断是否有数据提示
				var waybillRfcListLength = 0;
				
				if(!Ext.isEmpty(result.modifyBillWriteoffResultVo.waybillRfcList)){
					waybillRfcListLength = result.modifyBillWriteoffResultVo.waybillRfcList.length;
				}
				 
				if(waybillRfcListLength==0){
						Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.notFoundWaybillWarning'),function(){
						    Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').hide();
						});
				}else{
					//查询后显示隐藏组件
					var queryGrid=Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id');
					//更改单的条数							
					queryGrid.getDockedItems('toolbar[dock="bottom"]')[0].items.items[1].setValue(rawData.totalCount);
					//grid显示							
					Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').show();
					
				}
				me.enable(true);
			  }
			}
		});	
	}else{
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

//查询时，重置
writeoff.modifyBill.reset=function(){
	this.up('form').getForm().reset();
	var form =this.up('form').getForm().reset();
	var darftOrgCodeCombSelector=form.findField('darftOrgName');
	if(!Ext.isEmpty(stl.currentDept.code)){

			var displayText = stl.currentDept.name;
			var valueText = stl.currentDept.code;
			
			var store = darftOrgCodeCombSelector.store;
			var  key = darftOrgCodeCombSelector.displayField + '';
			var value = darftOrgCodeCombSelector.valueField+ '';
			
			var m = Ext.create(store.model.modelName);
			m.set(key, displayText);
			m.set(value, valueText);
			
			store.loadRecords([m]);
			darftOrgCodeCombSelector.setValue(valueText);
	}
}


//核销通过
writeoff.modifyBill.writeOffPass=function(){
	var 
	//运单号
	waybayNos,
	//运单号集合
	waybayNosArray,
	//受理开始日期
	acceptStartDate,
	//受理结束日期
	acceptEndDate,
	//大区
	largeArea,
	//小区
	smallArea,
	//起草部门
	darftOrgName,
	//核销状态
	writeoffStatus,
	//界面按钮，点击核销通过
	writeoffSuccess;
	
	var selectionWaybill = Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').getSelectionModel().getSelection();
	
	var notes=Ext.getCmp('Foss_wrieteoffReverseModifyBill_notes');
	
	if(!notes.isValid()){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onWriteoffNoteIsNull'));
		return false;
	}
	if(selectionWaybill.length==0){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onSelectRecordInwriteoff'));
		return false;
	}
	
	//获取界面grid应收单选中的记录
	var entity = new Object();
	var jsonDataWaybill = new Array();
	var waybillNumber='';
	var errorNumber='';
	for(var i=0;i<selectionWaybill.length;i++){
		//判断核销状态是否等于未核销，只有未核销的才能点击核销通过
		waybillNumber+=selectionWaybill[i].data.waybillNumber+",";
		if(selectionWaybill[i].data.writeoffStatus==writeoff.modifyBill.NO_WRITE_OFF){
			jsonDataWaybill.push(selectionWaybill[i].data.waybillChangeId);
			jsonDataWaybill.push(selectionWaybill[i].data.changeAmount);
		}else{
			errorNumber = errorNumber+selectionWaybill[i].data.waybillNumber;
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+errorNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatusPassErrorWarning'));
			return false;
		}
		
		//变更金额
		if(Math.abs(selectionWaybill[i].data.changeAmount)>200){
			if(notes.rawValue==''){
				Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+selectionWaybill[i].data.waybillNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatusNoteErrorWarning'));
				return false;
			}
		}
	}
		
		if(jsonDataWaybill.length==0){
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.selectRecordHasWriteoffWarning'));
			return false;
		}
							
		var modifyBillWriteoffVo   = new Object();
		var modifyBillWriteOffDto = new Object();
		modifyBillWriteOffDto.selectWaybillChangeNos = jsonDataWaybill;
		//设置核销状态为核销通过
		modifyBillWriteOffDto.writeoffSuccess =writeoff.modifyBill.WRITE_OFF_SUCCESS;
		modifyBillWriteOffDto.waybillNumber=waybillNumber;
		modifyBillWriteOffDto.notes=notes.getValue();
		modifyBillWriteoffVo.modifyBillWriteOffDto = modifyBillWriteOffDto;
		
		Ext.Ajax.request({
			url:writeoff.realPath('writeoffModifyBill.action'),
			jsonData:{
				'modifyBillWriteoffVo':modifyBillWriteoffVo
			},
			actionMethods:'post',
			submitEmptyText:false,
			success:function(response){
				Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.load();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
		notes.setValue='';
}


//核销不通过
writeoff.modifyBill.writeoffIsNotPass=function(){
	var 
	//运单号
	waybayNos,
	//运单号集合
	waybayNosArray,
	//受理开始日期
	acceptStartDate,
	//受理结束日期
	acceptEndDate,
	//大区
	largeArea,
	//小区
	smallArea,
	//起草部门
	darftOrgName,
	//核销状态
	writeoffStatus,
	//界面按钮，点击核销不通过
	writeoffFail;
	//核销不通过时选中的记录
	var selectionWaybill = Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').getSelectionModel().getSelection();	
	var notes=Ext.getCmp('Foss_wrieteoffReverseModifyBill_notes');
	if(!notes.isValid()){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onWriteoffNoteIsNull'));
		return false;
	}
	
	if(selectionWaybill.length==0){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onSelectRecordInwriteoff'));
		return false;
	}
	
	//获取界面grid应收单选中的记录
	var entity = new Object();
	var jsonDataWaybill = new Array();
	var errorNumber='';
	var changeAmount=0;
	var waybillNumber='';
	for(var i=0;i<selectionWaybill.length;i++){
		waybillNumber+=selectionWaybill[i].data.waybillNumber+",";
		//未核销时，核销状态必须为未核销
		if(selectionWaybill[i].data.writeoffStatus==writeoff.modifyBill.NO_WRITE_OFF){
			jsonDataWaybill.push(selectionWaybill[i].data.waybillChangeId);
			jsonDataWaybill.push(selectionWaybill[i].data.changeAmount);
		}else{
			errorNumber = errorNumber+selectionWaybill[i].data.waybillNumber
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+errorNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatusPassErrorWarning'));
			return false;
		}
		
		
		changeAmount=selectionWaybill[i].data.changeAmount;
		//变更金额
		if(Math.abs(selectionWaybill[i].data.changeAmount)>200){
			if(notes.rawValue==''){
				Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+selectionWaybill[i].data.waybillNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatusNoteErrorWarning'));
				return false;
			}
		}
	}

	if(jsonDataWaybill.length==0){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.selectRecordHasWriteoff'));
		return false;
	}
	var modifyBillWriteoffVo = new Object();
	var modifyBillWriteOffDto = new Object();
	modifyBillWriteOffDto.selectWaybillChangeNos = jsonDataWaybill;
	modifyBillWriteOffDto.waybillNumber=waybillNumber;
	//设置核销状态为：核销不通过
	modifyBillWriteOffDto.writeoffFail = writeoff.modifyBill.WRITE_OFF_FAILURE;
	modifyBillWriteOffDto.notes=notes.getValue();
	modifyBillWriteoffVo.modifyBillWriteOffDto = modifyBillWriteOffDto;

	Ext.Ajax.request({
		url:writeoff.realPath('writeoffModifyBill.action'),
		jsonData:{
			'modifyBillWriteoffVo':modifyBillWriteoffVo
		},
		actionMethods:'post',
		submitEmptyText:false,
		success:function(response){
			
			Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.load();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),result.message);
		}
	});
	
	notes.setValue='';
}


//反核销
writeoff.modifyBill.revWriteoff=function(){
	var 
	//运单号
	waybayNos,
	//运单号集合
	waybayNosArray,
	//受理开始日期
	acceptStartDate,
	//受理结束日期
	acceptEndDate,
	//大区
	largeArea,
	//小区
	smallArea,
	//起草部门
	darftOrgName,
	//核销状态
	writeoffStatus,
	//界面按钮，点击反核销
	reverse;
	var selectionWaybill = Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').getSelectionModel().getSelection();
	var notes=Ext.getCmp('Foss_wrieteoffReverseModifyBill_notes');
	if(selectionWaybill.length==0){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.revWriteoffNoChooseChangWaybill'));
		return false;
	}
	if(!notes.isValid()){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onWriteoffNoteIsNull'));
		return false;
	}
	
	//获取界面grid应收单选中的记录
	var entity = new Object();
	var jsonDataWaybill = new Array();
	
	var errorNumber='';
	
	for(var i=0;i<selectionWaybill.length;i++){
		//过滤掉核销状态等于未核销的 记录
		if(selectionWaybill[i].data.writeoffStatus==writeoff.modifyBill.WRITE_OFF_FAILURE||selectionWaybill[i].data.writeoffStatus==writeoff.modifyBill.WRITE_OFF_SUCCESS){
			jsonDataWaybill.push(selectionWaybill[i].data.waybillChangeId);
			jsonDataWaybill.push(selectionWaybill[i].data.changeAmount);
		}else{
			errorNumber = errorNumber+selectionWaybill[i].data.waybillNumber;
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+errorNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatusRevPassErrorWarning'));
			return false;
		}
		
		//反核销时，更改单不能为空
		if(notes.rawValue==''){
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo')+selectionWaybill[i].data.waybillNumber+writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.noChooseRevRecordOrRevWarning'));
			return false;
		}
	}
	
		if(jsonDataWaybill.length==0){
			Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.noChooseRecordOrRevWarning'));
			return false;
		}
		var modifyBillWriteoffVo   = new Object();
		var modifyBillWriteOffDto = new Object();
		modifyBillWriteOffDto.selectWaybillChangeNos = jsonDataWaybill;
		
		//反核销将：核销状态(变为未核销)
		modifyBillWriteOffDto.reverse = writeoff.modifyBill.NO_WRITE_OFF;
		modifyBillWriteOffDto.changeAmount=writeoff.modifyBill.changeAmount;
		modifyBillWriteOffDto.notes=notes.getValue();
		modifyBillWriteoffVo.modifyBillWriteOffDto = modifyBillWriteOffDto;
		Ext.Ajax.request({
			url:writeoff.realPath('reverseModifyBill.action'),
			jsonData:{
				'modifyBillWriteoffVo':modifyBillWriteoffVo
			},
			actionMethods:'post',
			submitEmptyText:false,
			success:function(response){
				Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.load();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
		
		notes.setValue='';
}






//打印更改单
writeoff.modifyBill.printModifyBill=function(grid,rowIndex,colIndex){
var params =  grid.getStore().getAt(rowIndex).data;

//职员编码、职员名称
params.empCode=writeoff.modifyBill.emp.empCode;
params.empName=writeoff.modifyBill.emp.empName;
params.user=writeoff.modifyBill.user;
params.deptNo=writeoff.modifyBill.deptInfo.code;
params.deptName=writeoff.modifyBill.deptInfo.name;
//修改用户编号
params.modifyUser=writeoff.modifyBill.deptInfo.modifyUser;
params.createUser=writeoff.modifyBill.deptInfo.createUser;
//更改单编号
var waybillChangeId=waybillChangeId;
//更改单类型
var rfcType=params.rfcType;

	//更改单，转货的打印	
	if(writeoff.modifyBill.TRANSFER==rfcType){
		do_printpreview('modifybillchangetransportwaybill',params,ContextPath.STL_WEB);
	//更改单退货返货的打印
	}else if(writeoff.modifyBill.RETURN==rfcType){
		do_printpreview('modifybillreturngoodswaybill',params,ContextPath.STL_WEB);
	}else{
		//打印更改单
		do_printpreview('modifybillwriteoffprt',params,ContextPath.STL_WEB);
	}
	Ext.getCmp('Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id').store.load();
}



/**
 * 更改单导出excel
 */
writeoff.modifyBill.writeoffReverseExport = function(){
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid;
	
	var me =this;
	//获取表格
	grid = Ext.getCmp('T_writeoff-modifyBillWriteoffReverse_content').getWillbayChangeGrid();
	var form= grid.ownerCt.down('form').getForm();
	
	
	var acceptStartDate=form.findField("acceptStartDate").getValue();
	var acceptEndDate=form.findField("acceptEndDate").getValue();
	var largeArea=form.findField("largeArea").getValue();
	var smallArea=form.findField("smallArea").getValue();
	var writeoffStatus=form.findField("writeoffStatus").getValue();
	var darftOrgCode=form.findField("darftOrgName").getValue();
	var productTypeList=form.findField("productTypeList").getValue();

	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),writeoff.modifyBill.i18n('foss.stl.writeoff.common.noDataToExport'));
		return false;
	}
	//进行导出提示
	Ext.MessageBox.show({
        title: writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.isExportbillChange'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: writeoff.modifyBill.i18n('foss.stl.writeoff.common.defaultColumnExport'), 
            no: writeoff.modifyBill.i18n('foss.stl.writeoff.common.customColumnExport'),
            cancel:writeoff.modifyBill.i18n('foss.stl.writeoff.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['waybillNumber','productType','changeItems','changeReason','oldToPayAmount',
								'newToPayAmount','oldPrePayAmount','newPrePayAmount','changeAmount','writeoffStatus',
								'writeOffTime','writeOffEmpName','darftOrgName','darfter','writeOffNote','printCounts'];
				//默认显示列名称
				arrayColumnNames = [
				                    writeoff.modifyBill.i18n('foss.stl.writeoff.common.waybillNo'),
				                    writeoff.modifyBill.i18n('foss.stl.writeoff.common.productCode'),
				                    writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeItems'),
				                    writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeReason'),
				                    writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.oldToPayAmount'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.newToPayAmount'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.oldPrePayAmount'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.newPrePayAmount'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeAmount'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatus'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.reverseWriteoff.writeoffTime'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.reverseWriteoff.writeoffEmpName'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darftOrgName'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darfter'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.common.notes'),
									writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.printCounts')
									];									
        	}else if(e=='no'){
        		//转化列头和列明
				columns = grid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if(columns[i].text!=writeoff.modifyBill.i18n('foss.stl.writeoff.common.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
        	}else{
        		return false;
        	}
   			
        	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
        	 //设置导出更改单参数
        	searchParams = {
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptStartDate':acceptStartDate,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptEndDate':acceptEndDate,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.largeArea':largeArea,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.smallArea':smallArea,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.darftOrgCode':darftOrgCode,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.writeoffStatus':writeoffStatus,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.arrayColumns':arrayColumns,
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.productTypeList':writeoff.modifyBill.convertProductCode(productTypeList),
        	'modifyBillWriteoffVo.modifyBillWriteOffDto.arrayColumnNames':arrayColumnNames
        	}; 
        	//设置该按钮灰掉
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 10000);
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url: writeoff.realPath('exportBillWillbayChange.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	//如果异常信息有值，则弹出提示
			    	if(!Ext.isEmpty(result.errorMessage)){
			    		Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),result.errorMessage);
			    		return false;
			    	}
					Ext.ux.Toast.msg(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'), writeoff.modifyBill.i18n('foss.stl.writeoff.common.exportSuccess'), 'success', 1000);
			    },
			    exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(writeoff.modifyBill.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
			});
			
        }
    });
}



//更改单model
Ext.define('Foss.writeoff.modifyBillWriteoffReverseModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	fields:[
	{
		//额外的用于生成的EXT使用的列 
		name: 'extid'
	},{
		name:'waybillChangeId'
	},
	{
		//运单号
		name:'waybillNumber'
	},{
		//运输性质
		name:'productType'
	},{
		//更改内容
		name:'changeItems'
	},{
		//更改依据
		name:/*'changeReason'*/'rfcSource'
	},{
		//更改前到付
		name:'oldToPayAmount',
		type:'long'
		
	},{
		//更改后到付
		name:'newToPayAmount',
		type:'long'
	},{
		//更改前预付
		name:'oldPrePayAmount',
		type:'long'
	},{
		//更改后预付
		name:'newPrePayAmount',
		type:'long'
	},{
		//申请部门
		name:'darftOrgName'
	},{
		//申请人(起草人)
		name: 'darfter'
	},{
		name:'writeoffStatus'
	},{
		name:'writeOffNote'
	},{
		//变更金额
		name:'changeAmount',
		type:'long'
	},{
		//开单时间
		name:'createTime',
		type:'date',
		convert : dateConvert
	},{
		//更改单起草时间
		name:'draftTime',
		type:'date',
		convert : dateConvert
	},{
		//更改单受理时间
		name:'rfcOperateTime',
		type:'date',
		convert : dateConvert
	},{
		//提走出库的时间
		name:'signTime',
		type:'date',
		convert : dateConvert
	},{
		//打印次数
		name:'printCounts'
	},{
		//更改单类型		
		name:'rfcType'
	},{
		//核销人姓名
		name:'writeOffEmpName'
	},{
		//核销时间
		name:'writeOffTime',
		type:'date',
		convert : dateConvert
	}]
});


//更改单Store
//核销单Store
Ext.define('Foss.writeoff.modifyBillWriteoffReverseStore',{
	extend:'Ext.data.Store',
	model:'Foss.writeoff.modifyBillWriteoffReverseModel',
	pageSize:writeoff.modifyBill.STLREVERSEWRITEOFF_ONEPAGESIZE,
	//autoLoad:true,
//	sorters: [{
//	     property: 'beginNo',
//	     direction: 'ASC'
//	 }],
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryModifyBill.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'modifyBillWriteoffResultVo.waybillRfcList',
			totalProperty:'totalCount'
			
		}
	},
	listeners:{
		beforeLoad:function(store, operation, eOpts){
			var 
			//运单号
			waybayNos,
			//运单号集合
			waybayNosArray,
			//受理开始日期
			acceptStartDate,
			//受理结束日期
			acceptEndDate,
			//大区
			largeArea,
			//小区
			smallArea,
			//起草部门
			darftOrgName,
			//核销状态
			writeoffStatus,
			//界面按钮，点击核销通过
			writeoffSuccess;		
			var hiddenfield = Ext.getCmp('T_writeoff-modifyBillWriteoffReverse_content').getHiddenField().getForm();
			waybayNos = hiddenfield.findField('waybayNosParam').getValue();
			if(Ext.String.trim(waybayNos)!=''){
				waybayNosArray = stl.splitToArray(waybayNos);
			}
			
			acceptStartDate = hiddenfield.findField('acceptStartDateParam').getValue();
			acceptEndDate =hiddenfield.findField('acceptEndDateParam').getValue();
			largeArea =hiddenfield.findField('largeAreaParam').getValue();
			smallArea =hiddenfield.findField('smallAreaParam').getValue();
			darftOrgCode =hiddenfield.findField('darftOrgNameParam').getValue();
			writeoffStatus =hiddenfield.findField('writeoffStatusParam').getValue();
			productTypeList =hiddenfield.findField('productTypeListParam').getValue();
			
			if(Ext.String.trim(waybayNos)!=''){
				var params = {
					'modifyBillWriteoffVo.modifyBillWriteOffDto.waybillNos':waybayNosArray,//核销时勾选的更改单运单;
					'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptStartDate':acceptStartDate,
					'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptEndDate':acceptEndDate,
					'modifyBillWriteoffVo.modifyBillWriteOffDto.largeArea':largeArea,
					'modifyBillWriteoffVo.modifyBillWriteOffDto.smallArea':smallArea,
					'modifyBillWriteoffVo.modifyBillWriteOffDto.darftOrgCode':darftOrgCode,
					'modifyBillWriteoffVo.modifyBillWriteOffDto.productTypeList':writeoff.modifyBill.convertProductCode(productTypeList),
					'modifyBillWriteoffVo.modifyBillWriteOffDto.writeoffStatus':writeoffStatus
					
				};
			}else{
				var params = {
						'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptStartDate':acceptStartDate,
						'modifyBillWriteoffVo.modifyBillWriteOffDto.acceptEndDate':acceptEndDate,
						'modifyBillWriteoffVo.modifyBillWriteOffDto.largeArea':largeArea,
						'modifyBillWriteoffVo.modifyBillWriteOffDto.smallArea':smallArea,
						'modifyBillWriteoffVo.modifyBillWriteOffDto.darftOrgCode':darftOrgCode,
						'modifyBillWriteoffVo.modifyBillWriteOffDto.productTypeList':writeoff.modifyBill.convertProductCode(productTypeList),
						'modifyBillWriteoffVo.modifyBillWriteOffDto.writeoffStatus':writeoffStatus
				};
			}
			
			Ext.apply(operation,{
				params : params
			});	
		}
	}
});




//表单的定义
Ext.define('Foss.writeoff.StlwriteoffReverseWillbayChangeFrom',{
	extend:'Ext.form.Panel',
	id:'Foss_writeoff_StlwriteoffReverseWillbayChangeFrom',
//	title : '出发更改单财务审核反审核',
	frame : true,
	height : 190,
	columnWidth:1,
	defaults:{
    	labelWidth:90,
    	margin :'5 5 5 5'
	},
	layout:'column',
    items:[
    {
    	xtype:'datefield',
    	name:'acceptStartDate',
    	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.acceptStartDate'),
    	value:stl.getTargetDate(new Date(),-writeoff.modifyBill.STLBILLREPAYMENT_ONEMONTH),
    	maxValue:new Date(),
    	editable:false,
    	format:'Y-m-d',
    	allowBlank:false,
    	columnWidth:.25,
    	invalidText : writeoff.modifyBill.i18n('foss.stl.writeoff.common.dateFormatError')
    },
  {
    	xtype:'datefield',
   	 	name:'acceptEndDate',
    	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.acceptEndDate'),
    	format:'Y-m-d',
    	maxValue:new Date(),
    	id:'Foss_modifyBillWriteoffReverse_accptEndDate_Id',
    	editable:false,
    	allowBlank:false,
    	value:new Date(),
    	columnWidth:.25,
    	invalidText : writeoff.modifyBill.i18n('foss.stl.writeoff.common.dateFormatError'),
    	format:'Y-m-d'
    },{
    	xtype:'combobox',
    	name:'writeoffStatus',
    	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatus'),
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore('BILL_WAYLLBAY_WRITEOFF_STATUS',null/*,{
			 'valueCode':'',
      		 'valueName':writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.all')
		}*/),
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		value:'NO_WRITE_OFF',
    	columnWidth:.25
    },
    {
		xtype: 'container',
		border : false,
		columnWidth:.25,
		height:24,
		html: '&nbsp;'
	},
    {
		//xtype:'linkagecomboselector',
        xtype: 'dynamicorgcombselector',
		eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		itemId : 'Foss_baseinfo_BigRegion_ID',
		store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
		columnWidth:.25,
		fieldLabel : writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.largeAreaName'),
		name : 'largeArea',
		isPaging: true,
		allowBlank : false,
		value:'',
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},
	{
		xtype:'linkagecomboselector',
		itemId : 'Foss_baseinfo_SmallRegion_ID',
		eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth:.25,
		fieldLabel : writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.smallAreaName'),
		name : 'smallArea',
		allowBlank : true,
		isPaging: true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
		},// 此处城市不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},
	{
		xtype:'linkagecomboselector',
		eventType : ['callparent'],
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth:.25,
		fieldLabel : writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darftOrgName'),
		name : 'darftOrgName',
		allowBlank : true,
		isPaging: true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
		},// 此处区域不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},
	{
		xtype: 'container',
		border : false,
		columnWidth:.25,
		height:24,
		html: '&nbsp;'
	},
	{
	     xtype: 'textarea',
		 autoScroll:true,
		 emptyText:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.inputBillErrorWarning'),
         fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.common.waybillNo'),
         name: 'waybayNos',
         height : 50,
         columnWidth:.5
	 },
		{
	    	xtype: 'combobox',
			name:'productTypeList',
	        fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.common.productCode'),
			store:Ext.create('Foss.pkp.ProductStore'),
			queryMode:'local',
			multiSelect:true,
	    	editable:false,
			displayField:'name',
			valueField:'code',
			height:24,
			columnWidth:.25,
			value:''
	    },
	 {
		 border: 1,
		 xtype:'container',
		 columnWidth:1,
		 defaultType:'button',
		 layout:'column',
		items:[{
			text:writeoff.modifyBill.i18n('foss.stl.writeoff.common.reset'),
			columnWidth:.08,
			handler:writeoff.modifyBill.reset
		},{
			xtype: 'container',
			border : false,
			columnWidth:.59,
			height:24,
			html: '&nbsp;'
		},{
			text:writeoff.modifyBill.i18n('foss.stl.writeoff.common.query'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
    			var form = this.up('form').getForm();
    			var me = this;
    			writeoff.modifyBill.queryByParam(form,me)
    		}
	}]
  }]
});	


//更改单更改内容明细表单
Ext.define('Foss.writeoff.modifyBillDetailForm', {
	extend:'Ext.form.Panel',
    title: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billChangeDetail'),
    bodyPadding: 5,
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },
    items: [{
        fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeItems'),
        xtype:'textareafield',
        height:200,
        name: 'changeItems',
        readOnly:true,
        regex:/^\S{1,}$/,
        regexText:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.onChangeItemCannotNull')
    }]
  });



/**
 * 声明更改单弹出修改界面
 */
Ext.define('Foss.writeoff.DetailWindow',{
	extend:'Ext.window.Window',
	width:600,
	modal:false,
	constrainHeader: true,
	closeAction:'hidden',
	items:[Ext.create('Foss.writeoff.modifyBillDetailForm')]
});



//更改单查询列表
Ext.define('Foss.writeoff.StlmodifyBillWriteoffReverseGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	height:500,
	columnWidth:1,
	detailWindow:null,
	title: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeList'),
	frame:true,
	totalRows:null,//总条数
	getTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRows)){
			me.totalRows=0;
		}
		return me.totalRows;
	},
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.writeoff.modifyBillWriteoffReverseStore'),
    listeners:{
    	'itemdblclick':function(th,record){
    		this.detailWindow.items.items[0].loadRecord(record)
    		this.detailWindow.show();
    	}
    },
	columns:[{
			xtype:'actioncolumn',
			width:100,
			text: writeoff.modifyBill.i18n('foss.stl.writeoff.common.actionColumn'),//操作列
			align: 'center',
			items: [{
	                tooltip: writeoff.modifyBill.i18n('foss.stl.writeoff.common.print'),//打印
					iconCls:'deppon_icons_print',
					width:42,
	                handler: function(grid, rowIndex, colIndex) {	
	                	writeoff.modifyBill.printModifyBill(grid,rowIndex,colIndex);
	                }
	            }]
	}, {
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.waybillNo'), 
		dataIndex: 'waybillNumber'
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.productCode'), 
		dataIndex: 'productType',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.createTime'), 
		dataIndex: 'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.signTime'), 
		dataIndex: 'signTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.draftTime'), 
		dataIndex: 'draftTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.common.rfcOperateTime'), 
		dataIndex: 'rfcOperateTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeItems'), 
		dataIndex: 'changeItems',
		xtype: 'ellipsiscolumn'
		
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.rfcSource'),
		xtype: 'ellipsiscolumn',
		//dataIndex: 'changeReason'
        dataIndex: 'rfcSource',
        renderer : function(value){
            if(value==null){
                return null;
            }
            else if(value=='INSIDE_REQUIRE'){
                return writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.insideRequire')//内部要求;
            }
            else if(value=='CUSTOMER_REQUIRE'){
                return writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.customerRequire')//客户要求;
            }
        }
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.oldToPayAmount'), 
		dataIndex:'oldToPayAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.newToPayAmount'), 
		dataIndex:'newToPayAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.oldPrePayAmount'),
		dataIndex:'oldPrePayAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.newPrePayAmount'), 
		dataIndex:'newPrePayAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.changeAmount'), 
		dataIndex:'changeAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatus'), 
		dataIndex:'writeoffStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_WAYLLBAY_WRITEOFF_STATUS');
    		return displayField;
    	}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.reverseWriteoff.writeoffTime'), 
		dataIndex:'writeOffTime',
		width:120,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.reverseWriteoff.writeoffEmpName'), 
		dataIndex:'writeOffEmpName'
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darftOrgName'), 
		dataIndex:'darftOrgName'
	},{
		header: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darfter'), 
		dataIndex:'darfter'
	},{
		header:writeoff.modifyBill.i18n('foss.stl.writeoff.common.notes'),
		dataIndex:'writeOffNote',
		xtype: 'ellipsiscolumn'
	},{
		header:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.printCounts'),
		dataIndex:'printCounts',
		align: 'right'
	}],
	viewConfig: {
		enableTextSelection: true
	},
	initComponent:function(config){
		var me = this;
		
		me.detailWindow = Ext.create('Foss.writeoff.DetailWindow');
		me.dockedItems = [{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			items:[{
				xtype:'button',
				columnWidth:.1,
				hidden:!writeoff.modifyBill.isPermission('/stl-web/writeoff/writeoffModifyBill.action'),
				text:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffIsPass'),
				handler:writeoff.modifyBill.writeOffPass
		},{
			xtype:'button',
			columnWidth:.1,
			hidden:!writeoff.modifyBill.isPermission('/stl-web/writeoff/writeoffModifyBill.action'),
			text:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffIsNotPass'),
			handler:writeoff.modifyBill.writeoffIsNotPass
		},{
			xtype:'button',
			columnWidth:.1,
			hidden:!writeoff.modifyBill.isPermission('/stl-web/writeoff/reverseModifyBill.action'),
			text:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.revWriteoff'),
			handler:writeoff.modifyBill.revWriteoff
		
		},{
			xtype:'container',
	    	html:'&nbsp;',
	    	columnWidth:.5
		},{
			xtype:'button',
			columnWidth:.1,
			text:writeoff.modifyBill.i18n('foss.stl.writeoff.common.export'),
			handler:writeoff.modifyBill.writeoffReverseExport
		}]
	},{
		xtype: 'toolbar',
		dock: 'bottom',
		layout:'column',
		items: [{
				xtype:'textarea',
				fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.common.notes'),
				labelWidth:70,
				id:'Foss_wrieteoffReverseModifyBill_notes',
				autoScroll:true,
				name:'notes',
				height:50,
//				allowBlank:false,
//				emptyText:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.notesNullWarning'),
				columnWidth:1
		},{			
			xtype:'displayfield',
			allowBlank:true,
			columnWidth:.1,
			name:'totalRows',
			labelWidth:80,
			width:185,
			height:24,
			fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.receivableWriteOffPayble.totalRow'),
			value:me.getTotalRows()
		},{
			xtype:'container',
		    html:'&nbsp;',
		    columnWidth:.2
	    },{	
			xtype:'standardpaging',
			store:me.store,
			columnWidth:.6,
			plugins: Ext.create('Deppon.ux.PageSizePlugin', {
				//设置分页记录最大值，防止输入过大的数值
				maximumSize: 200
			})
		 }]
	}];
		me.callParent(config);
	}
});

/**
 * 查询条件统一丢到一个隐藏panel中，为后续使用
 */
Ext.define('Foss.statementbill.QueryParamsHiddenfield',{
	extend:'Ext.form.Panel',
	hidden:true,
	layout:'column',
	defaults:{
		readOnly:true
	},
	items:[
	       {
	       	xtype:'datefield',
	       	name:'acceptStartDateParam',
	       	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.acceptStartDate'),
	       	format:'Y-m-d',
	       	columnWidth:.3
	       },
	       {
	       	xtype:'combobox',
	       	name:'writeoffStatusParam',
	       	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.writeoffStatus'),
	       	columnWidth:.3
	       }
	       ,{	
	       	xtype: 'dynamicorgcombselector',
	   		fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.darftOrgName'),
	   		name:'darftOrgNameParam',
	   		columnWidth:.3
	       }
	       ,{
	       	xtype:'datefield',
	      	name:'acceptEndDateParam',
	       	fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.acceptEndDate'),
	       	allowBlank:false,
	       	columnWidth:.3,
	       	format:'Y-m-d'
	       }
	       ,{
	       	xtype:'textfield',
	   		fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.largeAreaName'),
	   		name:'largeAreaParam',
	   		columnWidth:.3
	       }
	       ,
	       {
	       	xtype:'textfield',
	   		fieldLabel:writeoff.modifyBill.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.smallAreaName'),
	   		name:'smallAreaParam',
	   		columnWidth:.3
	       }
	   	,{
	   	    xtype: 'textarea',
	   	    autoScroll:true,
            fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.common.waybillNo'),
            name: 'waybayNosParam',	          
            height : 80,
            columnWidth:.6
	   	 },
			{
		    	xtype: 'combobox',
				name:'productTypeListParam',
		        fieldLabel: writeoff.modifyBill.i18n('foss.stl.writeoff.common.productCode'),
				store:Ext.create('Foss.pkp.ProductStore'),
				queryMode:'local',
				multiSelect:true,
		    	editable:false,
				displayField:'name',
				valueField:'code',
				height:24,
				columnWidth:.3,
				value:''
		    }]
});


	
//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var StlwriteoffReverseWillbayChangeFrom=Ext.create('Foss.writeoff.StlwriteoffReverseWillbayChangeFrom');

	//页面grid显示结果 和更改依据输入框，加分页
	var StlwriteoffReverseWillbayChangeGrid=Ext.create('Foss.writeoff.StlmodifyBillWriteoffReverseGrid',{
		id:'Foss_writeoff_StlmodifyBillWriteoffReverseGrid_Id',
		hidden:true
	});
	
	var queryParamsHiddenfield = Ext.create('Foss.statementbill.QueryParamsHiddenfield');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-modifyBillWriteoffReverse_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getHiddenField:function(){
			return queryParamsHiddenfield;
		},
		getWillbayChangeGrid:function(){
			return StlwriteoffReverseWillbayChangeGrid;
		},
		items: [StlwriteoffReverseWillbayChangeFrom,queryParamsHiddenfield,StlwriteoffReverseWillbayChangeGrid],
		listeners:{
			'boxready':function(th){
			//获取当前用户权限
			var roles = stl.currentUserDepts;
			//费用承担部门				
			var darftOrgCodeCombSelector=StlwriteoffReverseWillbayChangeFrom.form.findField('darftOrgName');
			
			/**
			 * 获取用户角色，判断部门是否可编辑 
			 */
			for(var i=0;i<roles.length;i++){
				//此处需要判断是否为收银员
				if(roles[i]=='1'){
					if(!Ext.isEmpty(stl.currentDept.code)){
	
						var displayText = stl.currentDept.name;
						var valueText = stl.currentDept.code;
						
						var store = darftOrgCodeCombSelector.store;
						var  key = darftOrgCodeCombSelector.displayField + '';
						var value = darftOrgCodeCombSelector.valueField+ '';
						
						var m = Ext.create(store.model.modelName);
						m.set(key, displayText);
						m.set(value, valueText);
						
						store.loadRecords([m]);
						darftOrgCodeCombSelector.setValue(valueText);
					}
				}	
			} 
		 }
		},
		renderTo: 'T_writeoff-modifyBillWriteoffReverse-body'
	});
});

