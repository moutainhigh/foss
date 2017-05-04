/**
 * Fuck off
 */
//------------------------------------常量和公用方法----------------------------------
baseinfo.operatorCount = {defaultV:0,successV:1,failureV:-1};	//偏线代理 操作返回值 1为成功，-1为失败
baseinfo.delAgencyType = 'MANY';								//偏线代理 删除的类型，批量或非批量
//偏线代理公司 查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.viewAgencyState = {add:'ADD',update:'UPDATE',view:'VIEW'};
//提货网点编码
baseinfo.regLimit = {stationNumber:/^\d{4}$/};
baseinfo.regCodeLimit = {agentDeptCode:/^(LDP)\d{8}$/};
baseinfo.readOnly = false;										//readOnly属性（编辑窗体）
baseinfo.booleanType = {yes:'Y',no:'N'};						//booleanType  对应后台常量 "布尔类型"
baseinfo.booleanStr = {yes:'true',no:'false'};					//booleanStr   从复选框中得到值
baseinfo.effectiveState = {active:'Y',inactive:'N'};			//booleanType  对应后台常量 "生效/未生效"
baseinfo.actioncolumnDisabled;									//弹出界面上 按钮图标的 是否可用状态
baseinfo.tempValue = {init:''};									//标识 承运业务 到达 是初始还是界面操作的 change事件
baseinfo.init = {init:'INIT'};
baseinfo.operateType = {save:'SAVE'};							//标识 是否 保存操作
baseinfo.agencyType = {ldp:'LDP'};								//标识 是快递代理
//校验 特定请求 结果 是否重复
baseinfo.agencyIsExist = function(url,param,successFn,exceptionFn){
	Ext.Ajax.request({
		url:url,
		jsonData:param,
		success:function(response){
			successFn(Ext.decode(response.responseText));
		},
		exception:function(response){
			exceptionFn(Ext.decode(response.responseText));
		}
	});
};
//校验 代理网点 是否重复
baseinfo.agencyDeptIsExist = function(field,fieldValue,fieldLabel,fieldName,agencyType){
	var url = null;
	if(baseinfo.agencyType.ldp === agencyType)
	{
		url = baseinfo.realPath('ldpAgencyDeptIsExist.action');
	}
	var param={},objectVo = {},entytyRecord = Ext.create('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel');
	entytyRecord.set(fieldName+'',fieldValue);
	objectVo.outerBranchExpressEntity = entytyRecord.data;
	param.objectVo = objectVo;
//successFn(result),exceptionFn(result)
baseinfo.agencyIsExist(url,param,function(result){
		if(Ext.isEmpty(result.objectVo.outerBranchExpressEntity)){
			field.clearInvalid();
		}else{
//			field.markInvalid(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataRepeatBegin')+fieldLabel+baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataRepeatEnd'));
			baseinfo.showInfoMsg('数据重复，请修改【'+fieldLabel+'】！');
			field.setValue(null);
			return;
		}
	},function(result){
//		field.markInvalid(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataRepeatBegin')+fieldLabel+baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataRepeatEnd'));
		baseinfo.showInfoMsg('数据重复，请修改【'+fieldLabel+'】！');
		field.setValue(null);
		return;
	});
};
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
//	    title:baseinfo.ldpAgencyCompany.i18n('i18n.baseinfo-util.fossAlert'),
		title:'警告',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
//	setTimeout(function(){
//      Ext.Msg.hide();
//  }, 3000);
};
//查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
baseinfo.viewAgencyState.operateWinBtn = function(win,viewState,operateType){
	//查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
	if(baseinfo.viewAgencyState.view === viewState){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 1);
		}
	}else if(baseinfo.operateType.save === operateType){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i > 1);
		}
	}
};
//快递代理网点 弹出界面上 数据渲染
baseinfo.initLdpAgencyDeptWin = function(win,viewState,formRecord,gridData){
	//标识为 初始窗体数据
	baseinfo.tempValue.init = baseinfo.init.init;
	if(formRecord.get('management')== null || formRecord.get('management') == ''){
		formRecord.set('managementName',FossUserContext.getCurrentDept().name);
		formRecord.set('management',FossUserContext.getCurrentDeptCode());
	}
	//加载数据
	win.editForm.loadRecord(baseinfo.dealOuterBranchDis2Boolean(formRecord));
	//TODO 公共组件
	win.editForm.getForm().findField('agentCompany').setCombValue(formRecord.get('agentCompanyName'),formRecord.get('agentCompany'));//代理公司 
	win.editForm.getForm().findField('management').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//省份
	//当为修改状态时，快递代理公司和快递代理网点编码都要设为不可编辑
	if(baseinfo.viewAgencyState.update === viewState){
		win.editForm.getForm().findField('agentCompany').setDisabled(true);
		win.editForm.getForm().findField('agentDeptCode').setDisabled(true);
	}
	
	var form  = win.editForm.getForm();
	//查看状态下 只有 取消按钮可用 [取消]按钮占 0
	if(baseinfo.viewAgencyState.view === viewState){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 0);
		}
		baseinfo.formFieldSetReadOnly(true,win.editForm);
		if(!Ext.isEmpty(win.editForm.items)){		
			win.editForm.items.items[17].setDisabled(false);
			win.editForm.items.items[18].setDisabled(false);
			win.editForm.items.items[20].setDisabled(true);
			win.editForm.items.items[21].setDisabled(true);
			
		}
	}else{
		// 只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
		// 只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
		// 只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
		baseinfo.initAgencyDeptCheckbox(
				form.findField('arrive').getValue(),
				form.findField('pickupSelf').getValue(),
				form.findField('pickupToDoor').getValue(),
				form);
		//判断只有勾选‘派送’，才可用派送和网点电子地图
		baseinfo.initDeliveryMapCanDisabled(form.findField('pickupToDoor').getValue(),win.editForm);
		if(!Ext.isEmpty(formRecord)){
			if(!Ext.isEmpty(formRecord.get('deliveryCoordinate'))){
				win.editForm.getForm().findField('deliveryCoordinate').setValue(formRecord.get('deliveryCoordinate'));
			}
			if(!Ext.isEmpty(formRecord.get('deptCoordinate'))){
				win.editForm.getForm().findField('deptCoordinate').setValue(formRecord.get('deptCoordinate'));
			}
		}
	}
	baseinfo.tempValue.init = null;
	return win;
};
//快递代理网点 弹出界面上 数据渲染
//是 到达 isArrive,是自提 isPickupSelf,是送货上门 isPickupToDoor,承载form
baseinfo.initAgencyDeptCheckbox = function(isArrive,isPickupSelf,isPickupToDoor,form){
	// 只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
	if(isArrive){
		baseinfo.arriveDealReadOnly(false,form);
		if(isPickupSelf){
			form.findField('pickupArea').setReadOnly(false);
		}else{
			form.findField('pickupArea').setReadOnly(true);
		}
		if(isPickupToDoor){
			form.findField('deliverArea').setReadOnly(false);
		}else{
			form.findField('deliverArea').setReadOnly(true);
		}
	}else{
		baseinfo.arriveChangeReadOnly(true,form);
		form.findField('pickupToDoor').setValue(false);
	}
	//baseinfo.tempValue.init = null;
	return form;
};
/**
 * 根据用户是否派送 初始化派送电子地图
 */
baseinfo.initDeliveryMapCanDisabled =function(isPickupToDoor,editForm){
	// 只有勾选“标准服务”中的“送货上门”后，“派送区域地图”方可使用；
	if(!Ext.isEmpty(isPickupToDoor)){
		editForm.items.items[17].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		editForm.items.items[18].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		editForm.items.items[20].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		editForm.items.items[21].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		
		editForm.getForm().findField('deliveryCoordinate').setValue('');
		editForm.getForm().findField('deptCoordinate').setValue('');
	}
	return editForm;
}
//“标准服务”和“增值服务” 是否可用 true或false 
baseinfo.arriveDealReadOnly = function(value,form){
	form.findField('pickupSelf').setReadOnly(value);
	form.findField('pickupToDoor').setReadOnly(value);
	form.findField('returnBill').setReadOnly(value);
	form.findField('helpCharge').setReadOnly(value);
	form.findField('arriveCharge').setReadOnly(value);
	form.findField('insured').setReadOnly(value);
	return form;
};
baseinfo.arriveChangeReadOnly = function(value,form){
	form.findField('pickupSelf').setValue(!value);
	form.findField('pickupArea').setReadOnly(value);
	form.findField('pickupArea').setValue();
	form.findField('pickupToDoor').setValue(!value);
	form.findField('deliverArea').setReadOnly(value);
	form.findField('deliverArea').setValue();
	form.findField('returnBill').setValue(!value);
	form.findField('helpCharge').setValue(!value);
	form.findField('arriveCharge').setValue(!value);
	form.findField('insured').setValue(!value);
	form.findField('returnBill').setReadOnly(value);
	form.findField('helpCharge').setReadOnly(value);
	form.findField('arriveCharge').setReadOnly(value);
	return form;
};

//提交快递代理网点oprateSource:操作来源 象征是 快递代理网点
baseinfo.submitLdpAgencyDept = function(win,viewState,outerBranchExpressEntity,grid,agencyType){
//	var url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('addVehicleAgencyDept.action'):baseinfo.realPath('addLdpAgencyDept.action'),
		var url = baseinfo.realPath('addLdpAgencyDept.action'),
//		m_success = baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.saveSuccess'),								//保存成功！
//		m_failure = baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.saveFail'),								//保存失败！
//		m_dateError = baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataError'),								//数据异常！
		m_success = '保存成功！',
		m_failure = '保存失败！',
		m_dateError = '数据异常！',
		objectVo = {};
		objectVo.outerBranchExpressEntity = outerBranchExpressEntity;
	
	if(baseinfo.viewAgencyState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewAgencyState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateLdpAgencyDept.action');
	}
	win.getMyMask().show();//显示遮罩
	Ext.Ajax.request({
		url:url,
		jsonData:{'objectVo':objectVo},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showInfoMsg(m_dateError);
			}else{//操作返回值 1：成功；-1：失败
				if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
					grid.store.loadPage(1);
					win.getMyMask().hide();//隐藏遮罩
					baseinfo.showInfoMsg(m_success);
					win.hide();
				}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
					win.getMyMask().hide();
					baseinfo.showInfoMsg(Ext.isEmpty(result.message)?m_failure:result.message);
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				win.getMyMask().hide();
				baseinfo.showInfoMsg(m_dateError);
			}else{
				win.getMyMask().hide();
				baseinfo.showInfoMsg(result.message);
			}
		}
	});
};
//作废快递代理网点
baseinfo.deleteAgencyDeptByCode = function(agencyType,delAgencyDeptType,operatRecord,grid){
	var grid = Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryGrid();
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
//		Ext.MessageBox.alert(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.selectData'));
		Ext.MessageBox.alert('提醒','请至少选择一行数据！');
	}else{	
		var codeStr = '';
		//作废电子地图Array
		var	deptCoordinateArr =new Array();
		//作废快递代理网点Array
		var deliveryCoordinateArr =new Array();
		if(!Ext.isEmpty(delAgencyDeptType)&&baseinfo.delAgencyType===delAgencyDeptType){//!Ext.isEmpty(agencyType)&&
			//批量作废
			codeStr = selection[0].get('virtualCode');
			for (var j = 1; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
				if(!Ext.isEmpty(selection[j].get('deptCoordinate'))){
					deptCoordinateArr.push(selection[j].get('deptCoordinate'));
				}
				if(!Ext.isEmpty(selection[j].get('deliveryCoordinate'))){
					deliveryCoordinateArr.push(selection[j].get('deliveryCoordinate'));
				}
			}
//			operatRecord = selection[0];
		}else{
			codeStr = operatRecord.get('virtualCode');
		}
		var objectVo = {},
//		url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('deleteVehicleAgencyDeptByCode.action'):baseinfo.realPath('deleteLdpAgencyDeptByCode.action');
		url = baseinfo.realPath('deleteLdpAgencyDeptByCode.action');
		objectVo.codeStr = codeStr;

//		Ext.MessageBox.buttonText.yes = baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.yes = '确认';
//		Ext.MessageBox.buttonText.no = baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.cancel');
		Ext.MessageBox.buttonText.no = '取消';
//		Ext.Msg.confirm(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.tipInfo'),baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.isDeleteCompanyInfo'),function(btn,text) {
		Ext.Msg.confirm('提示信息','你确定要作废快递代理网点信息？',function(btn,text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:url,
					jsonData:{'objectVo':objectVo},
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg('数据异常！');
							
						}else{				//操作返回值
							if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
								grid.store.loadPage(1);
//								baseinfo.showInfoMsg(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.deleteSuccess'));
								baseinfo.showInfoMsg('删除成功');
							}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
							    //TODO 作废失败原因后台是否详细描述
//								baseinfo.showInfoMsg(Ext.isEmpty(result.message)?baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.deleteFailure'):result.message);
								baseinfo.showInfoMsg('删除失败');
							}
							//删除GIS范围ID
							if(deliveryCoordinateArr.length>0){
								//删除快递代理网点后,删除快递代理网点范围ID
								for(var i= 0;i < deliveryCoordinateArr.length; i++){
									DpMap.base.deletePolygonByCode(deliveryCoordinateArr[i]);
								}
							}
							//删除GIS点ID
							if(deptCoordinateArr.length>0){
								//删除快递代理网点后,删除快递代理网点ID
								for(var i= 0;i < deptCoordinateArr.length; i++){
									DpMap.base.deletePointByCode(deptCoordinateArr[i]);
								}
							}
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
//							baseinfo.showInfoMsg(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.dataError'));
							baseinfo.showInfoMsg('数据异常！');
						}else{
							baseinfo.showInfoMsg(result.message);
						}
					}
				});
			}
		});
	}
};

baseinfo.dealOuterBranchDis2Boolean = function(outerBranchEntity){
	var outerBranchModel = outerBranchEntity.copy();
	if(baseinfo.booleanType.yes === outerBranchModel.get('pickupSelf')){
		outerBranchModel.set('pickupSelf',true);
	}
	if(baseinfo.booleanType.yes === outerBranchModel.get('pickupToDoor')){
		outerBranchModel.set('pickupToDoor',true);
	}
	if(baseinfo.booleanType.yes === outerBranchModel.get('returnBill')){
		outerBranchModel.set('returnBill',true);
	}
	if(baseinfo.booleanType.yes === outerBranchModel.get('arriveCharge')){
		outerBranchModel.set('arriveCharge',true);
	}
	if(baseinfo.booleanType.yes === outerBranchModel.get('helpCharge')){
		outerBranchModel.set('helpCharge',true);
	}
	if(baseinfo.effectiveState.active === outerBranchModel.get('leave')){
		outerBranchModel.set('leave',true);
	}
	if(baseinfo.effectiveState.active === outerBranchModel.get('transfer')){
		outerBranchModel.set('transfer',true);
	}
	if(baseinfo.effectiveState.active === outerBranchModel.get('arrive')){
		outerBranchModel.set('arrive',true);
	}
	if(baseinfo.effectiveState.active === outerBranchModel.get('insured')){
		outerBranchModel.set('insured',true);
	}

	if(baseinfo.booleanType.no === outerBranchModel.get('pickupSelf')){
		outerBranchModel.set('pickupSelf',false);
	}
	if(baseinfo.booleanType.no === outerBranchModel.get('pickupToDoor')){
		outerBranchModel.set('pickupToDoor',false);
	}
	if(baseinfo.booleanType.no === outerBranchModel.get('returnBill')){
		outerBranchModel.set('returnBill',false);
	}
	if(baseinfo.booleanType.no === outerBranchModel.get('arriveCharge')){
		outerBranchModel.set('arriveCharge',false);
	}
	if(baseinfo.booleanType.no === outerBranchModel.get('helpCharge')){
		outerBranchModel.set('helpCharge',false);
	}
	if(baseinfo.effectiveState.inactive === outerBranchModel.get('leave')){
		outerBranchModel.set('leave',false);
	}
	if(baseinfo.effectiveState.inactive === outerBranchModel.get('transfer')){
		outerBranchModel.set('transfer',false);
	}
	if(baseinfo.effectiveState.inactive === outerBranchModel.get('arrive')){
		outerBranchModel.set('arrive',false);
	}
	if(baseinfo.effectiveState.inactive === outerBranchModel.get('insured')){
		outerBranchModel.set('insured',false);
	}
	if(baseinfo.booleanType.yes === outerBranchModel.get('isAirport')){
		outerBranchModel.set('isAirport','Y');
	}
	if(baseinfo.booleanType.no === outerBranchModel.get('isAirport')){
		outerBranchModel.set('isAirport','N');
	}
	if(outerBranchModel.get('isAirport')== null || outerBranchModel.get('isAirport') == ''){
		outerBranchModel.set('isAirport','N');
	}
	return outerBranchModel;
};

baseinfo.dealOuterBranchBoolean2Dis = function(outerBranchEntity){
	var outerBranchModel = outerBranchEntity.copy();
	if(baseinfo.booleanStr.yes === outerBranchModel.get('pickupSelf')){
		outerBranchModel.set('pickupSelf',baseinfo.booleanType.yes);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('pickupToDoor')){
		outerBranchModel.set('pickupToDoor',baseinfo.booleanType.yes);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('returnBill')){
		outerBranchModel.set('returnBill',baseinfo.booleanType.yes);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('arriveCharge')){
		outerBranchModel.set('arriveCharge',baseinfo.booleanType.yes);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('helpCharge')){
		outerBranchModel.set('helpCharge',baseinfo.booleanType.yes);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('leave')){
		outerBranchModel.set('leave',baseinfo.effectiveState.active);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('transfer')){
		outerBranchModel.set('transfer',baseinfo.effectiveState.active);
	} if(baseinfo.booleanStr.yes === outerBranchModel.get('arrive')){
		outerBranchModel.set('arrive',baseinfo.effectiveState.active);
	}
    if(baseinfo.booleanStr.yes === outerBranchModel.get('insured')){
		outerBranchModel.set('insured',baseinfo.effectiveState.active);
	}
	if(baseinfo.booleanStr.no === outerBranchModel.get('pickupSelf')){
		outerBranchModel.set('pickupSelf',baseinfo.booleanType.no);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('pickupToDoor')){
		outerBranchModel.set('pickupToDoor',baseinfo.booleanType.no);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('returnBill')){
		outerBranchModel.set('returnBill',baseinfo.booleanType.no);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('arriveCharge')){
		outerBranchModel.set('arriveCharge',baseinfo.booleanType.no);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('helpCharge')){
		outerBranchModel.set('helpCharge',baseinfo.booleanType.no);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('leave')){
		outerBranchModel.set('leave',baseinfo.effectiveState.inactive);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('transfer')){
		outerBranchModel.set('transfer',baseinfo.effectiveState.inactive);
	} if(baseinfo.booleanStr.no === outerBranchModel.get('arrive')){
		outerBranchModel.set('arrive',baseinfo.effectiveState.inactive);
	}
	if(baseinfo.booleanStr.yes === outerBranchModel.get('isAirport')){
		outerBranchModel.set('isAirport','Y');
	}
	if(baseinfo.booleanStr.no === outerBranchModel.get('isAirport')){
		outerBranchModel.set('isAirport','N');
	}
 if(baseinfo.booleanStr.no === outerBranchModel.get('insured')){
		outerBranchModel.set('insured',baseinfo.effectiveState.inactive);
	}
	return outerBranchModel;
};

//快递代理网点STORE
Ext.define('Foss.baseinfo.LdpAgencyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel',
	pageSize:10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryLdpAgencyDept.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchExpressEntityList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * ---------GIS：网点电子地图窗口-----------------------------------------------
 */
Ext.define('Foss.baseinfo.ldpAgencyDept.MainGisWindow',{
	extend:'Ext.window.Window',
	closeAction:'hide',
	width:800,
	collBackFun:function(){},//回调函数
	parent:null,//Foss.baseinfo.LdpAgencyDeptWinForm
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			height:600
		}];
		me.callParent([cfg]);
	},
	height:600
});

//-----------------------------------------------------store结束-----------------------------------------------------------

//-----------------------------------------------------快递代理网点-----------------------------------------------------------
//快递代理网点网点 查询条件
Ext.define('Foss.baseinfo.QueryLdpAgencyDeptForm', {
	extend : 'Ext.form.Panel',
//	title: baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.queryCondition'),
	title:'快递代理网点查询',
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	labelWidth:120
    },
    height :225,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.commonSelector.AgencyDeptModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel'):me.record);
	},
	 
	
	getItems:function(){
		var me = this;
		return [{
//	        fieldLabel: baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.ldpAgencyDeptCode'),						//快递代理网点编码
			fieldLabel:'快递代理公司网点编码',
			labelWidth:120,
			name:'agentDeptCode'
		},{
			xtype:'commonldpagencydeptselector',
			displayField : 'agentDeptName',// 显示名称
			valueField : 'agentDeptName',// 值
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.ldpAgencyDept'),							//快递代理网点
			fieldLabel:'快递代理公司网点名称',
			labelWidth:120,
			name:'agentDeptName'
		},{
			xtype:'commonLdpAgencyCompanySelector',
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencydept.belongldpAgencyCompany'),							//所属快递代理
			fieldLabel:'所属快递代理公司',
			name:'agentCompany',
			valueField: 'agentCompanyCode'
		},{
			forceSelection : true,
			xtype : 'commoncityselector',
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.city'),									//所属快递代理
			fieldLabel:'城市',
			name: 'cityCode'
		},FossDataDictionary.getDataDictionaryCombo('VALUE_ADDED_SERVICES',{
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.addedServices'),								//增值服务
			fieldLabel:'增值服务',
			forceSelection:true,
			name: 'valueAddedServices',
	    	labelSeparator:'',
	    	labelWidth:120,
	    	listeners:{
	    		change:function(field,newValue,oldValue){
	    			if(Ext.isEmpty(newValue)){
	    				field.setValue(null);
	    			}
	    		}
	    	}
		}),FossDataDictionary.getDataDictionaryCombo('OPERATING_SERVICES',{
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.carrierBusiness'),								//承运业务
			fieldLabel:'承运业务',
			forceSelection:true,
			name: 'carrierBusiness',
	    	labelSeparator:'',
	    	labelWidth:120,
	    	listeners:{
	    		change:function(field,newValue,oldValue){
	    			if(Ext.isEmpty(newValue)){
	    				field.setValue(null);
	    			}
	    		}
	    	}
		}),{
			colspan: 3,
			xtype:'dynamicorgcombselector',
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.manageDept'),								//管理部门
			fieldLabel:'管理部门',
			name: 'management',
			type:'ORG'
		},{
			xtype : 'container',
			colspan:4,
			defaultType:'button',
			layout:'column',
//			hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptQueryButton'),
			items : [{
				width: 75,
//				text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.reset'),  //重置
				text : '重置',
				handler : function() {
					this.up('form').getForm().reset();
					this.up('form').getForm().findField('management').setCombValue(FossUserContext. getCurrentDept().name,FossUserContext.getCurrentDeptCode());

				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
//				text :  baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.query'),
				text : '查询',
				cls:'yellow_button',
//				hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptQueryButton'),
				handler : function() {
				    //查询条件是否全部可为空
					var grid = Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryGrid();
					grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				}
			}]
		},{
			xtype:'hiddenfield',
//			fieldLabel:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencydept.cityName'),									//城市名称
			fieldLabel: '城市名称',
			name: 'cityName'
		}];
	}
});
//------------------------------------GRID----------------------------------

//grid上的 超链接事件 方法
baseinfo.ldpAgencyDept.winShow = function(rowIndex){
	var param = {},
		grid = Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryGrid(), 
		record = grid.store.getAt(rowIndex);				//选中的计费规则数据
	param.formRecord = record;
	grid.viewLdpAgencyDept(param).show();
};

/**
 * 快递代理网点列表grid
 */
Ext.define('Foss.baseinfo.QueryLdpAgencyDeptGrid', {
	extend: 'Ext.grid.Panel',
//	title : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.resultList'),
	title : '快递代理网点显示',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
//	emptyText: baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10,
				prependButtons : false,
					defaults : {
						margin : '0 0 25 3'
					}
			});
		}
		return this.pagingToolbar;
	},
	
	//得到查看快递代理网点窗体
	getViewAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.LdpExpressDeptWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.initLdpAgencyDeptWin(win,viewState,formRecord,gridData);
	},
	
	//得到快递代理网点编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.LdpExpressDeptWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.initLdpAgencyDeptWin(win,viewState,formRecord,gridData);
	},
	//得到快递代理网点编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getLdpAgencyDeptWin:function(viewState,param){
		if(baseinfo.viewAgencyState.add === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.addLdpAgencyDeptWin,'新增快递代理网点',viewState,param);
		}else if(baseinfo.viewAgencyState.update === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.updateLdpAgencyDeptWin,'修改快递代理网点',viewState,param);
		}
		//			else if(baseinfo.viewAgencyState.view === viewState){
//			baseinfo.readOnly = true;
//			return this.getViewAgencyDeptWin(this.viewLdpAgencyDeptWin,baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.viewldpAgency'),viewState,param);
//		}
	},
	getViewLdpAgencyDeptWin:function(viewState,param){
		if(baseinfo.viewAgencyState.view === viewState){
			baseinfo.readOnly = true;
			return this.getViewAgencyDeptWin(this.viewLdpAgencyDeptWin,'查看快递代理网点',viewState,param);
		}
	},
	addLdpAgencyDeptWin:null,						//新增基快递代理网点
	addLdpAgencyDept:function(param){
		return this.getLdpAgencyDeptWin(baseinfo.viewAgencyState.add,param);
	},
	updateLdpAgencyDeptWin:null,						//修改基快递代理网点
	updateLdpAgencyDept:function(param){
		return this.getLdpAgencyDeptWin(baseinfo.viewAgencyState.update,param);
	},
	viewLdpAgencyDeptWin:null,						//查看基快递代理网点
	viewLdpAgencyDept:function(param){
		return this.getViewLdpAgencyDeptWin(baseinfo.viewAgencyState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns();
		me.store = me.getStore();
		me.listeners = me.getMyListeners();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar();
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 快递代理网点
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewLdpAgencyDept(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.LdpAgencyDeptStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var outerBranchEntity = queryForm.record.getData();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//快递代理网点编码
								'objectVo.outerBranchExpressEntity.agentDeptCode':outerBranchEntity.agentDeptCode,
								//快递代理网点名称
								'objectVo.outerBranchExpressEntity.agentDeptName':outerBranchEntity.agentDeptName,
								//快递代理公司
								'objectVo.outerBranchExpressEntity.agentCompany':outerBranchEntity.agentCompany,
								//管理部门
								'objectVo.outerBranchExpressEntity.management':outerBranchEntity.management,
								//城市编码
								'objectVo.outerBranchExpressEntity.cityCode':outerBranchEntity.cityCode,
								//增值服务
								'objectVo.valueAddedServices':queryForm.findField('valueAddedServices').getValue(),
								//承运业务
								'objectVo.carrierBusiness':queryForm.findField('carrierBusiness').getValue()
							}
						});	
					}
				}
		    }
		});
	},
	getTbar:function(){
		var me = this;
		return [{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.add'),								//新增
			text : '新增',
			//hidden:!pricing.isPermission('../pricing/saveRole.action'),
//			hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptAddButton'),
			disabled:!baseinfo.ldpAgencyDept.isPermission('addLdpAgencyDept/ldpAgencyDeptAddButton'),
			hidden:!baseinfo.ldpAgencyDept.isPermission('addLdpAgencyDept/ldpAgencyDeptAddButton'),
			handler :function(){
				var win = me.addLdpAgencyDept({});
				win.show();
				var dynamicorgcombselector = win.down('form').down('dynamicorgcombselector');
				dynamicorgcombselector.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
				var agentCompanyObject = Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryForm().getForm().
				findField('agentCompany');
				 var dataArray = agentCompanyObject.valueModels[0];
					if(!Ext.isEmpty(dataArray ) ){
						win.down('form').getForm().findField('contactPhone').setValue(dataArray.data.contactPhone);
						win.down('form').getForm().findField('address').setValue(dataArray.data.contactAddress);
//						win.down('form').getForm().findField('agentDeptCode').setValue(dataArray.data.agentCompany);
					}
			} 
		},'-', {
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.void'),								//作废
			text : '作废',
//			hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptVoidButton'),
			disabled:!baseinfo.ldpAgencyDept.isPermission('deleteLdpAgencyDeptByCode/ldpAgencyDeptVoidButton'),
			hidden:!baseinfo.ldpAgencyDept.isPermission('deleteLdpAgencyDeptByCode/ldpAgencyDeptVoidButton'),
			handler :function(){
				baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.ldp,baseinfo.delAgencyType,null,me);
			} 
		}];
	},
	getColumns:function(){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.operate'),//操作
			text : '操作',
			items: [{
            	iconCls:'deppon_icons_edit',
//                tooltip: baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.update'),
            	tooltip: '编辑',
//                disabled:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptEditButton'),
            	disabled:!baseinfo.ldpAgencyDept.isPermission('updateLdpAgencyDept/ldpAgencyDeptEditButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateLdpAgencyDept(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
//                tooltip: baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.void'),
            	tooltip: '作废',
//                disabled:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptVoidButton'),
            	disabled:!baseinfo.ldpAgencyDept.isPermission('deleteLdpAgencyDeptByCode/ldpAgencyDeptVoidButton'),
                handler: function(grid, rowIndex, colIndex) {
					baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.ldp,null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			//TODO 快递代理网点编码  超链接
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.ldpAgencyDeptCode'),							//快递代理编码
			text : '快递代理公司网点编码',
			dataIndex : 'agentDeptCode',
			renderer:function(value,meta,record,rowIndex,celIndex){
				var v = "'"+rowIndex+"'";
				return Ext.isEmpty(value)?'':'<a href="javascript:baseinfo.ldpAgencyDept.winShow('+v+')">'+value+'</a>';
			},
			flex : 1
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencydept.ldpAgencyDeptName'),							//快递代理网点名称
			text:'快递代理公司网点名称',
			dataIndex : 'agentDeptName',
			flex : 1.2
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencydept.belongldpAgencyCompany'),							//所属快递代理公司
			text:'所属快递代理公司',
			dataIndex : 'agentCompanyName',
			flex : 1.75
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.manageDept'),									//管理部门
			text:'管理部门',
			dataIndex : 'managementName',
			flex : 0.75
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.province'),										//省份
			text:'省份',
			dataIndex : 'provName',
			flex : 0.75
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.city'),										//城市
			text:'城市',
			dataIndex : 'cityName',
			flex : 0.75
		},{
//			text : baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.ldpagencycompany.area'),										//区/县
			text:'区/县',
			dataIndex : 'countyName',
			flex : 0.75
		}];
	}
});

//---------------------------------------新增或修改快递代理网点window

//快递代理网点 编辑窗体 form
Ext.define('Foss.baseinfo.LdpExpressDeptWinForm', {
	extend : 'Ext.form.Panel',
//	title:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.LdpDeptInfo'),
	title:'快递代理网点信息',
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'column',
        columns: 1
    },
    viewState:null,											//查看状态
    formRecord:null,										//加载的record
	constructor : function(config){							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			margin : '8 0 5 0',
	    	//labelSeparator:'',
			allowBlank:false,
			labelWidth:80,
			readOnly:baseinfo.readOnly,
			width:250
	    };
	},
	
	 //编辑快递代理网点电子地图
	  mainGisWindow: null,
	  getMainGisWindow:function(){
		  var me =this;
		  if(Ext.isEmpty(this.mainGisWindow)){
			  this.mainGisWindow =Ext.create('Foss.baseinfo.ldpAgencyDept.MainGisWindow',{
				  'parent':this, //Foss.baseinfo.LdpAgencyDeptWinForm
				  'collBackFun':function(data){
					  if(data.flag =='DELETE'){ //若数据类型 为delete 
						  me.getForm().findField('deptCoordinate').setValue('');
						  me.formRecord.set('deptCoordinate','');
					  }else if(data.flag =='ADD'){ //若传过来的数据类型是add
						  me.getForm().findField('deptCoordinate').setValue(data.code);
						  me.formRecord.set('deptCoordinate',data.code);
						  me.mainGisWindow.close();
					  }else if(data.flag =='UPDATE'){
						  //先清空当前
						  me.getForm().findField('deptCoordinate').setValue('');
						  me.formRecord.set('deptCoordinate','');
						  //再设置新的值
						  me.getForm().findField('deptCoordinate').setValue(data.code);
						  me.formRecord.set('deptCoordinate',data.code);
						  me.mainGisWindow.close();
					  }
				  },
				  listeners:{
					  //显示之前的事件
					  beforeshow:function(window){
						  Ext.defer(function(){
							  var mapLocation;
							  if(!Ext.isEmpty(me.items.items[4])){
								  //获取城市信息
								  var cityLocation = me.items.items[4].city.rawValue;
								  //获取区县信息
								  var countryLocation =me.items.items[4].county.rawValue;
								  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
		    							mapLocation = cityLocation + countryLocation;
		    						}else if(Ext.isEmpty(cityLocation)){
		    							mapLocation = baseinfo.ldpAgencyDept.i18n('foss.baseinfo.shanghaishi');
		    					}
							  }
							  /*new DPMap.MyMap('DRAW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
		    						 var locateFeature =  DMap.LocateFeature(map);
		    			   		     var fun = function(data){
		    			   		    	window.collBackFun(data);
		    			   		     }
		    			   		     var pointfeature = DMap.PointFeature(map,{isAddable:true,callBackFun:fun,manipulable:1}); 
		    			   		     var id = me.getForm().findField('deptCoordinate').getValue();
		    			   		     //调用根据id展示地图方法 
		    			   		     pointfeature.showModifiablePointById([id]);
		    		           });*/
							  new DpMap(window.items.items[0].getId(),{center : mapLocation, zoom : 13 },function(map) { 
		    			   		     var fun = function(data){
		    			   		    	window.collBackFun(data);
		    			   		    	if(data.flag =='DELETE'){
		    			   		    		pointfeature.openEditTool();
		    			   		    	};
		    			   			 }
		    			   		     var callFun =function(data){
		    			   		    	 if(data.flag =='QUERY'){
		    			   		    		pointfeature.closeEditTool();
		    			   		    	 }
		    			   		     }
		    			   		var pointfeature = new DpMap.service.DpMap_marker(map,window.items.items[0].getId(),{isAddable:true,callBackFun:fun,closeToolCallback:callFun}); 
		    			   		var id = me.getForm().findField('deptCoordinate').getValue();
		    			   		
		    			   		/*if(!Ext.isEmpty(id)){
		    			   			pointfeature.closeEditTool();
		    			   		}*/
		    			   		 pointfeature.showModifiablePointById([id]);
		    		           });
						  },700,this);
					  },
					  //隐藏之前的事件
					  beforehide:function(window){
						  window.removeAll();
	   				   	  window.add(Ext.create('Ext.container.Container',{
	   					   height:500
	   				      }));
					  }
				  }
			  });
		  }
		  return this.mainGisWindow;
	  },
	  //查看快递代理网点电子地图
	   mainGisShowWindow:null,
	   getMainGisShowWindow:function(){
		   var me =this;
		   if(Ext.isEmpty(this.mainGisShowWindow)){
			   this.mainGisShowWindow =Ext.create('Foss.baseinfo.ldpAgencyDept.MainGisWindow',{
				   'parent':this,
				   listeners:{
					   //显示之前的事件
					   beforeshow:function(window){
						   Ext.defer(function(){
							   var mapLoaction;
							   if(!Ext.isEmpty(me.items.items[4])){
									  //获取城市信息
									  var cityLocation = me.items.items[4].city.rawValue;
									  //获取区县信息
									  var countryLocation =me.items.items[4].county.rawValue;
									  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
			    							mapLocation = cityLocation + countryLocation;
			    						}else if(Ext.isEmpty(cityLocation)){
			    							mapLocation = baseinfo.ldpAgencyDept.i18n('foss.baseinfo.shanghaishi');
			    					}
								}
							   new DPMap.MyMap('VIEW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
		    						var locateFeature =  DMap.LocateFeature(map);
		   						 	var pointF = DMap.PointFeature(map,{isAddable:true,manipulable:1});
		   						 	var id = me.getForm().findField('deptCoordinate').getValue();
		   						 	if(!Ext.isEmpty(id)){
		   						 		pointF.showNon_modifiablePointById(id);
		   						 	}
		       					  });
						   },700,this);
					   },
					   //隐藏之前的事件
					   beforehide:function(window){
						   window.removeAll();
		   				   window.add(Ext.create('Ext.container.Container',{
		   					   height:500
		   				   })); 
					   }
				   }
			   });
		   }
		   return this.mainGisShowWindow;
	   },
	  //编辑快递代理派送区域电子地图
	  deliveryGisWindow:null,
	  getDeliveryGisWindow:function(){
		  var me =this;
		if(Ext.isEmpty(this.deliveryGisWindow)){
			this.deliveryGisWindow =Ext.create('Foss.baseinfo.ldpAgencyDept.MainGisWindow',{
				'parent':this,
				'collBackFun':function(data){
					if(data.type =='DELETE'){
						 me.getForm().findField('deliveryCoordinate').setValue('');
						 me.formRecord.set('deliveryCoordinate','');
					}else if(data.type =='ADD'||data.type=='UPDATE'){
						 me.getForm().findField('deliveryCoordinate').setValue(data.code);
						 me.formRecord.set('deliveryCoordinate',data.code);
						 me.deliveryGisWindow.close();
					}
				},
				listeners:{
					beforeshow:function(window){
						Ext.defer(function(){
							  var mapLocation;
							  if(!Ext.isEmpty(me.items.items[4])){
								  //获取城市信息
								  var cityLocation = me.items.items[4].city.rawValue;
								  //获取区县信息
								  var countryLocation =me.items.items[4].county.rawValue;
								  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
		    							mapLocation = cityLocation + countryLocation;
		    						}else if(Ext.isEmpty(cityLocation)){
		    							mapLocation = baseinfo.ldpAgencyDept.i18n('foss.baseinfo.shanghaishi');
		    					}
							  }
							  /*new DPMap.MyMap('DRAW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
		    						var locateFeature =  DMap.LocateFeature(map);
		    			   		     var fun = function(data){
		    			   		    	window.collBackFun(data);
		    			   			 }
		    			   		     var ployfeature = DMap.PolygonFeature(map,{isAddable:true, callBackFun:fun, foregroundType:'EXPRESSLDP_REGIONS', backgroundType:'CITY',manipulable:1}); 
		    			   		     //调用根据id展示范围方法 
		    			   			ployfeature.showModifiyAblePolygons([me.formRecord.get('deliveryCoordinate')]);
		    			   		     
		    		           });*/
							  new DpMap(window.items.items[0].getId(), {center :mapLocation,zoom : 13}, function (map) { 
	    						  var fun = function(data){
	    							window.collBackFun(data);
	    							if(data.type =='DELETE'){
	    								ployfeature.openEditTool();
	    							}
	    						  }
	    						  var callFun =function(data){
	    							  if(data.type =='QUERY'){
	    								  ployfeature.closeEditTool();
	    							  }
	    						  }
	    						 //实例化一个新类			
	    						 var ployfeature = new DpMap.service.DpMap_polygon(map, window.items.items[0].getId(),{isAddable:true, callBackFun:fun, foregroundType:'EXPRESSLDP_REGIONS', backgroundType:'CITY',closeToolCallback:callFun});    						
	    							//调用根据id展示范围方法
	    						ployfeature.showModifiablePolygons([me.formRecord.get('deliveryCoordinate')]);
	    						/*if(!Ext.isEmpty(me.formRecord.get('deliveryCoordinate'))){
	    							ployfeature.closeEditTool();
	    						}*/

	    					});	
						  },700,this);
					},
					beforehide:function(window){
						 window.removeAll();
		   				   window.add(Ext.create('Ext.container.Container',{
		   					   height:500
		   				   })); 
					}
				}
			});
		}
		return this.deliveryGisWindow;
	  },
	  //查看快递代理派送区域电子地图
	  deliveryGisShowWindow:null,
	  getDeliveryGisShowWindow:function(){
		var me =this;
		if(Ext.isEmpty(this.deliveryGisShowWindow)){
			this.deliveryGisShowWindow =Ext.create('Foss.baseinfo.ldpAgencyDept.MainGisWindow',{
				'parent':this,
				listeners:{
					beforeshow:function(window){
						Ext.defer(function(){
							var mapLocation;
							  if(!Ext.isEmpty(me.items.items[4])){
								  //获取城市信息
								  var cityLocation = me.items.items[4].city.rawValue;
								  //获取区县信息
								  var countryLocation =me.items.items[4].county.rawValue;
								  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
		    							mapLocation = cityLocation + countryLocation;
		    						}else if(Ext.isEmpty(cityLocation)){
		    							mapLocation = baseinfo.ldpAgencyDept.i18n('foss.baseinfo.shanghaishi');
		    					}
							  }
							  new DPMap.MyMap('VIEW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
		    						var locateFeature =  DMap.LocateFeature(map); 
		    			   		    var ployfeature = DMap.PolygonFeature(map,{isAddable:true, callBackFun:function(){}, foregroundType:'EXPRESSLDP_REGIONS', backgroundType:'CITY'}); 
		    			   				 //调用根据id展示范围方法 
		    			   			ployfeature.showModifiyAblePolygons([me.formRecord.get('deliveryCoordinate')]);
		    		           });
						},700,this);
					},
					beforehide:function(window){
						window.removeAll();
		   				   window.add(Ext.create('Ext.container.Container',{
		   					   height:500
		   				   })); 
					}
				}
			});
		}
		return this.deliveryGisShowWindow;
	  },
	
	//获得表单元素
	getItems:function(config){
		var me = this;
		var smsNameStore = FossDataDictionary.getDataDictionaryStore('ABBREVIATION_OF_AGENCY_COMPANY');
		//TODO 通过不同的查看状态 拼接 界面
		return [{
			xtype:'commonLdpAgencyCompanySelector',
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.companyInfo'),							//代理简称
			fieldLabel:'快递代理公司',
			allowBlank:false,
			columnWidth:.33,
			labelWidth:100,
			name:'agentCompany',
			listeners:{
				blur:function(field){
	        		//快递代理公司网点名称 唯一
        			if(!Ext.isEmpty(field.getValue())
            			&&(baseinfo.viewAgencyState.add == config.viewState)
        				&& config.formRecord.get('agentCompany') != field.getValue()){
        				var agenCompanyObject = field.valueModels[0];
        				if(!Ext.isEmpty(agenCompanyObject ) ){
    						me.getForm().findField('contactPhone').setValue(agenCompanyObject.data.contactPhone);
    						me.getForm().findField('address').setValue(agenCompanyObject.data.contactAddress);
    						me.getForm().findField('agentDeptCode').setValue(agenCompanyObject.data.agentCompanyCode);
    					}
        			}
        		}
        	}
//			valueField: 'virtualCode'
		},{
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.LdpCompanyCode'),							//代理编码
			fieldLabel:'快递代理网点编码',
			//margin : '8 0 0 3',
			name:'agentDeptCode',
			regex:baseinfo.regCodeLimit.agentDeptCode,
			regexText:'快递代理网点编码其所属的快递代理公司编码加三位数字组成！',
			columnWidth:.33,
			labelWidth:125,
			width:150,
			allowBlank:false,
			listeners:{
				blur:function(field){
	        		//快递代理网点编码 唯一
        			if(!Ext.isEmpty(field.getValue())
        				&&(baseinfo.viewAgencyState.view != config.viewState)
        				&& config.formRecord.get('agentDeptCode') != field.getValue()){
        				//if(field.getValue().substr(0,8) != this.up().getForm().formRecord.data.agentCompany){
        				if(field.getValue().substr(0,8) != this.up().getForm().findField('agentCompany').getValue()){
        					 baseinfo.showInfoMsg("输入的快递代理网点编码不符合规范，应由其所属的快递代理公司编码加三位数字组成，请重新输入!");
      				         field.setValue(this.up().getForm().formRecord.data.agentCompany);
      				         return;
        				}
        				 if(field.getValue().length < 11){
     				        baseinfo.showInfoMsg("输入长度不足11位，请重新输入!");
     				        field.setValue(this.up().getForm().formRecord.data.agentCompany);
     				        return;
     				    }else if(field.getValue().length > 11){
     				        baseinfo.showInfoMsg("输入长度超过11位，请重新输入!");
     				        field.setValue(this.up().getForm().formRecord.data.agentCompany);
     				        return;
     				    }
        				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.ldp);
        			}
        		}
        	}
		},{
//			fieldLabel:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.LdpCompanyName'),							//管理部门
			fieldLabel:'快递代理公司网点名称',
			name:'agentDeptName',
			//margin : '8 0 0 3',
			columnWidth:.33,
			labelWidth:135,
			listeners:{
				blur:function(field){
	        		//快递代理公司网点名称 唯一
        			if(!Ext.isEmpty(field.getValue())
            			&&(baseinfo.viewAgencyState.view != config.viewState)
        				&& config.formRecord.get('agentDeptName') != field.getValue()){
        				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.ldp);
        			}
        		}
        	}
		},{
//			fieldLabel:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.LdpCompanyName'),							//管理部门
			fieldLabel:'快递代理公司网点简称',
			name:'simplename',
			//margin : '8 0 0 -15',
			labelWidth:135,
			columnWidth:.33
		},{
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
//			provinceLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.province'),
//			cityLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.city'),
//			areaLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.area'),
			provinceLabel :'省份',
			cityLabel :	'城市',
			areaLabel :	'区/县',
			columnWidth:.66,
//			width:600,
//			hideLabel:true,
//			allowBlank:true,
			provinceWidth : 200,
			cityWidth : 200,
			areaWidth : 200,
//			nationIsBlank:true,
			disabled:baseinfo.readOnly,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:false,
			labelWid:50,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.orderTel'),						//正单联系电话
			fieldLabel:'备注',
			//margin:'8 0 0 2',
	        name:'notes',
	        //labelWidth:125,
	        columnWidth:.33,
			allowBlank:true
		},{
			xtype:'dynamicorgcombselector',
//			fieldLabel:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.manageDept'),							//代理名称
			fieldLabel:'管理部门',
			columnWidth:.33,
			//labelWidth:125,
			name: 'management',
			type:'ORG'
		},{
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.tel'),							//联系地址
			fieldLabel:'联系电话',
			columnWidth:.33,
			//labelWidth:50,
			width:30,
			name:'contactPhone'
		},{
			fieldLabel:'代理公司简称',					//代理公司简称
			name:'agentCompanyAbbreviationCode',
			xtype:'combo',
			editable:false,
			queryMode: 'local',
			store:smsNameStore,
			columnWidth:0.33,
			labelWidth:100,
			displayField: 'valueName',
			valueField: 'valueCode'
		},{
//			fieldLabel:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.remark'),								//联系人
			fieldLabel:'快递代理公司网点地址',
			columnWidth:.66,
			labelWidth:135,
			width:500,
			name: 'address'
		},{
//			fieldLabel:baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.deptNum'),						//提货网点编码
			fieldLabel:'提货网点编码',
			//margin:'8 0 0 2',
			name:'stationNumber',
			regex:baseinfo.regLimit.stationNumber,
			columnWidth:.33,
			labelWidth:125,
			regexText:'提货网点编码错误，由四位数字组成！'
		},{
			width:440,
			//margin:'9 0 0 0',
			name:'carrierBusiness',
			xtype:'checkboxgroup',
//			fieldLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.carrierBusiness'),
			fieldLabel :'承运业务',
			height:10,
			allowBlank:true,
			columnWidth:.58,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: '出发', name: 'leave'},
			        { boxLabel: '到达', name: 'arrive',listeners:{
		        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
		        		change:function(field,newV,oldV ){
		        			if(baseinfo.tempValue.init != baseinfo.init.init){
			        			baseinfo.initAgencyDeptCheckbox(newV,
			        					me.getForm().findField('pickupSelf').getValue(),
			        					me.getForm().findField('pickupToDoor').getValue(),
			        					me.getForm())
			        			
		        			}
		        			}
		        		}
		        },{ boxLabel: '中转', name: 'transfer'}]
		},{
			width:390,
			columnWidth:.42,
			name:'standardServices',
			xtype:'checkboxgroup',
//			fieldLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.standardServices'),
			fieldLabel :'标准服务',
			allowBlank:true,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: '自提', name: 'pickupSelf',listeners:{
	        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
	        		change:function(field,newV,oldV ){
	        			if(baseinfo.tempValue.init != baseinfo.init.init){
		        			baseinfo.initAgencyDeptCheckbox(me.getForm().findField('arrive').getValue(),
		        												newV,
		        												me.getForm().findField('pickupToDoor').getValue(),
		        					me.getForm());
		        			me.getForm().findField('pickupArea').setValue();
		        		}
	        		}
	        	}
			},{ boxLabel: '送货上门', name: 'pickupToDoor',listeners:{
	        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
	        		change:function(field,newV,oldV ){
	        			if(baseinfo.tempValue.init != baseinfo.init.init){
		        			baseinfo.initAgencyDeptCheckbox(me.getForm().findField('arrive').getValue(),
		        					me.getForm().findField('pickupSelf').getValue(),
		        					newV,
		        					me.getForm());
		        			var editForm = baseinfo.initDeliveryMapCanDisabled(newV,me);
		        			if(!me.getForm().findField('arrive').getValue()){
		        				editForm.items.items[17].setDisabled(true);
		        				editForm.items.items[18].setDisabled(true);
		        				editForm.items.items[20].setDisabled(true);
		        				editForm.items.items[21].setDisabled(true);
		        			}
		        			me.getForm().findField('deliverArea').setValue();
		        		}
	        		}
	        	}
			}]
		},{
			width:660,
			columnWidth:.75,
			xtype:'checkboxgroup',
//			fieldLabel : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.addedServices'),
			fieldLabel :'增值服务',
			allowBlank:true,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
//			items: [{ boxLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.returnSigning'), name: 'returnBill'},
//			        { boxLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.paymentCollection'), name: 'helpCharge'},
//			        { boxLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.cashOnDelivery'), name: 'arriveCharge'},
//			        { boxLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.cashOnDelivery'), name: 'insured'}]
			items: [{ boxLabel: '返回签单', name: 'returnBill',margin:'6 0 0 0'},
			        { boxLabel: '代收货款', name: 'helpCharge',margin:'6 0 0 0',listeners:{
	        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
		        		change:function(field,newV,oldV ){
		        			if(newV){//勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
						me.getForm().findField('agentCollectedUpperLimit').reset();
            		}else{//未勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(true);
            			me.getForm().findField('agentCollectedUpperLimit').reset();
            		}
		        		}
		        	}
	        	},{name: 'agentCollectedUpperLimit',
				   width:200,
				   decimalPrecision:3,
				   minValue:1,
				   step:1.00,
				   margin:'0 18 0 0',
				   maxValue:99999,
				   labelWidth:90,
				   readOnly:true,
				   allowDecimals:false,//是否允许小数
				   fieldLabel: '代收上限(元)',//baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.agentCollectedUpperLimit'),//代收貨款上限
				   xtype : 'numberfield'},
			     { boxLabel: '货到付款', name: 'arriveCharge',margin:'6 0 0 0'},
			     { boxLabel: '保价',name: 'insured',margin:'6 0 0 0'}]

		},{
			//width:360,
			columnWidth:.24,
			//margin:'0 0 0 30',
			xtype:'radiogroup',
			layout:'column',
			fieldLabel :'是否机场',
			name:'airport',
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: '是', name: 'isAirport',inputValue: 'Y',margin:'6 0 0 0'},
			         {	xtype:'container',
				        border:false,
				        html:'&nbsp;',
				        height:1,
				        columnWidth:.22 },
			        { boxLabel: '否', name: 'isAirport',inputValue: 'N',margin:'6 0 0 0',checked: true}]
		},{
			width:387,
			columnWidth:.5,
			height:120,
			xtype:'textareafield',
			labelWidth:100,
			allowBlank:true,
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.fromMentioningArea'),								//自提区域描述
			fieldLabel:'自提区域描述',
			name:'pickupArea'
		},{
			width:387,
			columnWidth:.5,
			height:120,
			xtype:'textareafield',
			allowBlank:true,
//	        fieldLabel: baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.deliveryArea'),								//派送区域描述
			fieldLabel:'派送区域描述',
			labelWidth:100,
			name:'deliverArea'
		},{	xtype :'label',
			//colspan:2
			columnWidth:.56
		},{
			width:120,
			columnWidth:.22,
			margin : '18 5 5 5',
			xtype : 'button',
			text:'查看快递代理网点电子地图',
			//name :'viewDept',
			handler:function(){
				me.getMainGisShowWindow().show();
			}
		},{
			width:120,
			columnWidth:.22,
			margin : '18 5 5 5',
			xtype : 'button',
			text:'查看快递代理派送区域电子地图',
			//name :'viewArea',
			handler:function(){
				me.getDeliveryGisShowWindow().show();
			}
		},{
			xtype :'label',
			//colspan:2
			columnWidth:.56
		},{
			width:120,
			columnWidth:.22,
			margin : '5 5 5 5',
			xtype:'button',
			text:'编辑快递代理网点电子地图',
			//name :'editDept',
			handler:function(){
				me.getMainGisWindow().show();
			}
		},{
			width:120,
			columnWidth:.22,
			margin : '5 5 5 5',
			xtype:'button',
			text:'编辑快递代理派送区域电子地图',
			//name :'editArea',
			handler:function(){
				me.getDeliveryGisWindow().show();
			}
		},{
			name :'deptCoordinate', //网点服务坐标编码
			fieldLabel:'网点服务坐标编码',
			allowBlank:true,
			hidden:true,
			xtype:'textfield'
		},{
			name :'deliveryCoordinate', //派送区域服务坐标编码
			fieldLabel:'派送区域服务坐标编码',
			hidden:true,
			allowBlank:true,
			xtype:'textfield'
		}];
	}
});
/**
 * 快递代理网点界面win
 */
Ext.define('Foss.baseinfo.LdpExpressDeptWin',{
	extend : 'Ext.window.Window',
//	title : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.addAgencyCompanyDept'),								//新增空运代理网点   默认新增
	title:'新增快递代理网点',
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :1000,
	height :620,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//TODO 清空数据
			baseinfo.tempValue.init = baseinfo.init.init;
		}
	},
	sourceGrid:null,										//创建船体的 来源渠道 需要刷新的 grid
	editForm:null,											//空运代理网点表单Form
	editGrid:null,											//空运代理网点表格Grid
	formRecord:null,										//空运代理网点实体 Foss.baseinfo.commonSelector.AgencyCompanyModel
	gridDate:null,											//空运代理网点 信息数组  [Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.LdpExpressDeptWinForm',{'height':500,'viewState':config.viewState,formRecord:config.formRecord});
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//定义mask遮罩
	myMask:null,
	getMyMask:function(){
		if(Ext.isEmpty(this.myMask)){
			this.myMask = new Ext.LoadMask(this, {msg:"Please wait..."});
		}
		return this.myMask;
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
//			text : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.cancel'),
			text:'取消',
			handler :function(){
				me.hide();
			}
		},{
//			text : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.reset'),
			text:'重置',
			handler :function(){
				var gridData = [];
				baseinfo.initLdpAgencyDeptWin(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord),gridData);
			} 
		},{
//			text : baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.save'),
			text:'保存',
			cls:'yellow_button',
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
//		    		baseinfo.showInfoMsg(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.checkData'));
		    		baseinfo.showInfoMsg("请检测数据必填项【*】是否填写完整且符合规范，有叉号的地方" +
		    				"表示输入有问题，将鼠标移动至有叉号的地方，会有详细提示！");
		    		return;
		    	}
//		    	if((editForm.findField('carrierBusiness').getValue() == baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.arrive')) 
//		    			&& editForm.findField('carrierBusiness').getValue() == null){
//		    		baseinfo.showInfoMsg(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.checkCarrierBusiness'));
//		    		return;
//		    	if((editForm.findField('carrierBusiness').getValue() == baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.arrive')) 
//		    			&& editForm.findField('carrierBusiness').getValue() == null){
//		    		baseinfo.showInfoMsg(baseinfo.ldpAgencyCompany.i18n('foss.baseinfo.ldpAgencyCompany.checkCarrierBusiness'));
//		    		return;
//		    	}
	    		editForm.updateRecord(me.formRecord);
				baseinfo.submitLdpAgencyDept(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord).data,me.sourceGrid,baseinfo.agencyType.ldp);
			}
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QueryLdpAgencyDeptForm',{'record':Ext.create('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.QueryLdpAgencyDeptGrid');//查询结果显示列表
	var management = queryForm.getForm().findField('management');
	management.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
	//TODO 根据角色判断是否管理员,如果不是管理员 则 设置为 不可编辑
	//management.setReadOnly();
	Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-ldpAgencyDeptIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items : [ queryForm, queryGrid,{
			xtype:'button',
//			text:baseinfo.ldpAgencyDeptIndex.i18n('foss.baseinfo.void'),								//作废
			text:'作废',
			hidden:true,
			handler :function(){
				baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.ldp,baseinfo.delAgencyType,null,Ext.getCmp('T_baseinfo-ldpAgencyDeptIndex_content').getQueryGrid());
			}
		}]
	}));
});
