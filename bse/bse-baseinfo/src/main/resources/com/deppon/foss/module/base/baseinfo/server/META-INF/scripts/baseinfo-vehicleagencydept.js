/**
 * 偏线代理网点查询Form								Foss.baseinfo.QueryVehicleAgencyDeptForm
 * 偏线代理网点查询结果grid							Foss.baseinfo.QueryVehicleAgencyDeptGrid
 * 偏线代理网点新增Win								Foss.baseinfo.VehicleAgencyDeptWin
 * 偏线代理网点界面form								Foss.baseinfo.VehicleAgencyDeptWinForm
 * 偏线代理网点界面grid								Foss.baseinfo.VehicleAgencyDeptWinGrid
 */
/**
 * 空运代理公司、偏线代理公司model									Foss.baseinfo.commonSelector.AgencyCompanyModel
 * 空运代理网点、偏线代理网点model									Foss.baseinfo.commonSelector.AgencyDeptModel
 * 偏线代理公司store													Foss.baseinfo.VehicleAgencyCompanyStore
 * 偏线代理网点store													Foss.baseinfo.VehicleAgencyDeptStore
 * 空运代理公司store													Foss.baseinfo.AirAgencyCompanyStore
 * 空运代理网点store													Foss.baseinfo.AirAgencyDeptStore
 */
//------------------------------------常量和公用方法----------------------------------
baseinfo.operatorCount = {defaultV:0,successV:1,failureV:-1};	//偏线代理 操作返回值 1为成功，-1为失败
baseinfo.delAgencyType = 'MANY';								//偏线代理 删除的类型，批量或非批量
//偏线代理公司 查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.viewAgencyState = {add:'ADD',update:'UPDATE',view:'VIEW'};
//提货网点编码
baseinfo.regLimit = {stationNumber:/^\d{4}$/};
baseinfo.readOnly = false;										//readOnly属性（编辑窗体）
baseinfo.booleanType = {yes:'Y',no:'N'};						//booleanType  对应后台常量 "布尔类型"
baseinfo.booleanStr = {yes:'true',no:'false'};					//booleanStr   从复选框中得到值
baseinfo.effectiveState = {active:'Y',inactive:'N'};			//booleanType  对应后台常量 "生效/未生效"
baseinfo.actioncolumnDisabled;									//弹出界面上 按钮图标的 是否可用状态
baseinfo.tempValue = {init:''};									//标识 承运业务 到达 是初始还是界面操作的 change事件
baseinfo.init = {init:'INIT'};
baseinfo.operateType = {save:'SAVE'};							//标识 是否 保存操作
baseinfo.agencyType = {ky:'KY',px:'PX'};						//标识 是空运KY还是偏线PX
baseinfo.regLimit= {
		w:/^[A-Za-z0-9]{6,7}$/
};
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
baseinfo.agencyDeptIsExist = function(field,fieldValue,fieldLabel,fieldNmae,agencyType){
	var url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('vehicleAgencyDeptIsExist.action'):baseinfo.realPath('airAgencyDeptIsExist.action'),
		param={},objectVo = {},entytyRecord = Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.outerBranchEntity = entytyRecord.data;
	param.objectVo = objectVo;
//successFn(result),exceptionFn(result)
baseinfo.agencyIsExist(url,param,function(result){
		if(Ext.isEmpty(result.objectVo.outerBranchEntityList)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		//fieldLabel+result.objectVo.businessPartnerEntity
		field.markInvalid(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};
//校验 代理 公司是否重复
baseinfo.agencyCompanyIsExist = function(field,fieldValue,fieldLabel,fieldNmae,agencyType){
	var url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('vehicleAgencyCompanyIsExist.action'):baseinfo.realPath('airAgencyCompanyIsExist.action'),
		param={},objectVo = {},entytyRecord = Ext.create('Foss.baseinfo.commonSelector.AgencyCompanyModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.businessPartnerEntity = entytyRecord.data;
	param.objectVo = objectVo;
	//successFn(result),exceptionFn(result)
	baseinfo.agencyIsExist(url,param,function(result){
		if(Ext.isEmpty(result.objectVo.businessPartnerEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		//fieldLabel+result.objectVo.businessPartnerEntity
		field.markInvalid(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.vehicleAgencyDeptIndex.i18n('i18n.baseinfo-util.fossAlert'),
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

//通过查询条件导出数据
baseinfo.vehicleAgencyDeptIndex.exportVehicleAgencyDepts = function(queryForm){
	var queryForm = queryForm.getForm();//得到查询的FORM表单
	queryForm.updateRecord(queryForm.record);
    if (queryForm != null) {
		Ext.MessageBox.buttonText.yes = "确定";  
		Ext.MessageBox.buttonText.no = "取消"; 
		if(!Ext.fly('downloadAgencyDept')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAgencyDept';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm( baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.tipInfo'), '确定要导出查询结果吗?', function(btn,text){
			if(btn == 'yes'){
				var outerBranchEntity = queryForm.record.getData();
				var params = {
					//代理网点编码
					'objectVo.outerBranchEntity.agentDeptCode':outerBranchEntity.agentDeptCode,
					//代理网点名称
					'objectVo.outerBranchEntity.agentDeptName':outerBranchEntity.agentDeptName,
					//代理公司
					'objectVo.outerBranchEntity.agentCompany':outerBranchEntity.agentCompany,
					//管理部门
					'objectVo.outerBranchEntity.management':outerBranchEntity.management,
					//城市编码
					'objectVo.outerBranchEntity.cityCode':outerBranchEntity.cityCode,
					//增值服务
					'objectVo.valueAddedServices':queryForm.findField('valueAddedServices').getValue(),
					//承运业务
					'objectVo.carrierBusiness':queryForm.findField('carrierBusiness').getValue()
				};
		
				Ext.Ajax.request({
					url:baseinfo.realPath('exportVehicleAgencyDepts.action'),
					form: Ext.fly('downloadAgencyDept'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.exportFailed'),result.message);
					}
				});
			}
		});
	}
};
//偏线代理网点 弹出界面上 数据渲染
baseinfo.initVehicleAgencyDeptWin = function(win,viewState,formRecord,gridData){
	win.editForm.getForm().findField('isAirports').reset();//取消选中
	//标识为 初始窗体数据
	baseinfo.tempValue.init = baseinfo.init.init;
	
	//加载数据
	win.editForm.loadRecord(baseinfo.dealOuterBranchDis2Boolean(formRecord));
	//TODO 公共组件
	win.editForm.getForm().findField('agentCompany').setCombValue(formRecord.get('agentCompanyName'),formRecord.get('agentCompany'));//代理公司 
	win.editForm.getForm().findField('management').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//市
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//区
	
	
	var form  = win.editForm.getForm();
	//查看状态下 只有 取消按钮可用 [取消]按钮占 0
	if(baseinfo.viewAgencyState.view === viewState){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 0);
		}
		baseinfo.formFieldSetReadOnly(true,win.editForm);
		//在查看状态下，编辑地图为不可编辑，查看可用
		if(!Ext.isEmpty(win.editForm.items)){
			win.editForm.items.items[17].setDisabled(true);
			win.editForm.items.items[18].setDisabled(false);
			win.editForm.items.items[20].setDisabled(true);
			win.editForm.items.items[21].setDisabled(false);
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
		//判断只有勾选‘派送’，才可用派送电子地图
		baseinfo.initDeliveryMapCanDisabled(form.findField('pickupToDoor').getValue(),win.editForm);
		if(!Ext.isEmpty(formRecord)){
			if(!Ext.isEmpty(formRecord.get('deliveryCoordinate'))){
				win.editForm.getForm().findField('deliveryCoordinate').setValue(formRecord.get('deliveryCoordinate'));
			}
		}
		
	}
	baseinfo.tempValue.init = null;
	return win;
};
//偏线代理网点 弹出界面上 数据渲染
//是 到达 isArrive,是自提 isPickupSelf,是送货上门 isPickupToDoor,承载form
baseinfo.initAgencyDeptCheckbox = function(isArrive,isPickupSelf,isPickupToDoor,form){
	// 只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
	if(!Ext.isEmpty(isArrive)){
		baseinfo.arriveDealReadOnly(!(baseinfo.booleanStr.yes === isArrive || isArrive),form);
	}
	// 只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
	if(!Ext.isEmpty(isPickupSelf)){
		form.findField('pickupArea').setReadOnly(!(baseinfo.booleanStr.yes === isPickupSelf || isPickupSelf));
	}
	// 只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
	if(!Ext.isEmpty(isPickupToDoor)){
		form.findField('deliverArea').setReadOnly(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
	}
	return form;
};
/**
 * 根据用户是否派送 初始化派送电子地图
 */
baseinfo.initDeliveryMapCanDisabled =function(isPickupToDoor,editForm){
	// 只有勾选“标准服务”中的“送货上门”后，“派送区域地图”方可使用；
	if(!Ext.isEmpty(isPickupToDoor)){
		editForm.items.items[20].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		editForm.items.items[21].setDisabled(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		editForm.getForm().findField('deliveryCoordinate').setValue('');
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
	return form;
};
//空运代理网点 弹出界面上 数据渲染
baseinfo.initAirAgencyDeptWin = function(win,viewState,formRecord,gridData){
	return win;
};
//提交偏线代理网点oprateSource:操作来源 象征是 偏线代理网点还是空运代理网点
baseinfo.submitAgencyDept = function(win,viewState,outerBranchEntity,grid,agencyType){
	var url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('addVehicleAgencyDept.action'):baseinfo.realPath('addAirAgencyDept.action'),
		m_success = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.saveSuccess'),								//保存成功！
		m_failure = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.saveFail'),								//保存失败！
		m_dateError = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataError'),								//数据异常！
		objectVo = {};
	objectVo.outerBranchEntity = outerBranchEntity;
	if(baseinfo.viewAgencyState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewAgencyState.update === viewState){
		//修改URL
		url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('updateVehicleAgencyDept.action'):baseinfo.realPath('updateAirAgencyDept.action');
	}

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
					baseinfo.showInfoMsg(m_success);
					win.hide();
				}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
					baseinfo.showInfoMsg(Ext.isEmpty(result.message)?m_failure:result.message);
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showInfoMsg(m_dateError);
			}else{
				baseinfo.showInfoMsg(result.message);
			}
		}
	});
};
//作废偏线代理网点
baseinfo.deleteAgencyDeptByCode = function(agencyType,delAgencyDeptType,operatRecord,grid){
//	var grid = Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryGrid();
	var gisWindow =Ext.create('Foss.baseinfo.vehicleagencydept.MainGisWindow');
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		var codeStr = '';
		//作废电子地图Attay
		var	deptCoordinateArr =new Array();
		//作废偏线代理网点Array
		var deliveryCoordinateArr =new Array();
		if(!Ext.isEmpty(delAgencyDeptType)&&baseinfo.delAgencyType===delAgencyDeptType){
			//批量作废
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
				if(!Ext.isEmpty(selection[j].get('deptCoordinate'))){
					deptCoordinateArr.push(selection[j].get('deptCoordinate'));
				}
				if(!Ext.isEmpty(selection[j].get('deliveryCoordinate'))){
					deliveryCoordinateArr.push(selection[j].get('deliveryCoordinate'));
				}
			}
		}else{
			codeStr = operatRecord.get('virtualCode');
		}
		var objectVo = {},
		url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('deleteVehicleAgencyDeptByCode.action'):baseinfo.realPath('deleteAirAgencyDeptByCode.action');
		objectVo.codeStr = codeStr;

		Ext.MessageBox.buttonText.yes = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.tipInfo'),baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.isDeleteCompanyInfo'),function(btn,text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:url,
					async:false,
					jsonData:{'objectVo':objectVo},
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataError'));
						}else{				//操作返回值
							if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
								grid.store.loadPage(1);
								baseinfo.showInfoMsg(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.deleteSuccess'));
							}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
							    //TODO 作废失败原因后台是否详细描述
								baseinfo.showInfoMsg(Ext.isEmpty(result.message)?baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.deleteFailure'):result.message);
							}
							//删除GIS范围ID
							if(deliveryCoordinateArr.length>0){
								//删除偏线代理网点后,删除代理偏线范围ID
								for(var i= 0;i < deliveryCoordinateArr.length; i++){
									DpMap.base.deletePolygonByCode(deliveryCoordinateArr[i]);
								}
							}
							//删除GIS点ID
							if(deptCoordinateArr.length>0){
								//删除偏线代理网点后,删除代理网点ID
								for(var i= 0;i < deptCoordinateArr.length; i++){
									DpMap.base.deletePointByCode(deptCoordinateArr[i]);
								}
							}
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.dataError'));
						}else{
							baseinfo.showInfoMsg(result.message);
						}
					}
				});
			}
		});
	}
};
/**.
 * <p>
 * 公共方法，承运代理，标准服务，增值服务 前后台数据转换<br/>
 * 			数据库true转换成"Y"，false转换成"N"<br/>
 * <p>
 * @param  outerBranchModel  空运代理网点、偏线代理网点 model
 * @returns outerBranchModel 空运代理网点、偏线代理网点 model
 * @author 李学兴
 * @时间 2012-11-30
 */
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
	return outerBranchModel;
};
/**.
 * <p>
 * 公共方法，承运代理，标准服务，增值服务 前后台数据转换<br/>
 * 			数据库"Y"转换成true，"N"转换成false<br/>
 * <p>
 * @param  outerBranchModel  空运代理网点、偏线代理网点 model
 * @returns outerBranchModel 空运代理网点、偏线代理网点 model
 * @author 李学兴
 * @时间 2012-11-30
 */
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
	return outerBranchModel;
};
//------------------------------------STORE----------------------------------
//偏线代理公司STORE
Ext.define('Foss.baseinfo.VehicleAgencyCompanyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryVehicleAgencyCompany.action'),
		reader : {
			type : 'json',
			root : 'objectVo.businessPartnerEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//偏线代理公司 辖内 偏线代理网点STORE
Ext.define('Foss.baseinfo.VehicleAgencyCompanyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryOuterBranchsByComCode.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//偏线代理网点STORE
Ext.define('Foss.baseinfo.VehicleAgencyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryVehicleAgencyDept.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * ---------GIS：网点电子地图窗口-----------------------------------------------
 */
Ext.define('Foss.baseinfo.vehicleagencydept.MainGisWindow',{
	extend:'Ext.window.Window',
	closeAction:'hide',
	width:750,
	collBackFun:function(){},//回调函数
	parent:null,//Foss.baseinfo.VehicleAgencyDeptWinForm
	 constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			height:750
		}];
		me.callParent([cfg]);
	},
	height:750
});

//------------------------------------FORM----------------------------------
//偏线代理网点 编辑窗体 form
Ext.define('Foss.baseinfo.VehicleAgencyDeptWinForm', {
	extend : 'Ext.form.Panel',
	title:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyDeptInfo'),
	frame: true,
	defaultType : 'textfield',
	height :430,
	layout:{
      type: 'table',
      columns: 6
  },
  //编辑网点电子地图
  mainGisWindow: null,
  getMainGisWindow:function(){
	  var me =this;
	  if(Ext.isEmpty(this.mainGisWindow)){
		  this.mainGisWindow =Ext.create('Foss.baseinfo.vehicleagencydept.MainGisWindow',{
			  'parent':this, //Foss.baseinfo.VehicleAgencyDeptWinForm
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
						  if(!Ext.isEmpty(me.items.items[3])){
							  //获取城市信息
							  var cityLocation = me.items.items[3].city.rawValue;
							  //获取区县信息
							  var countryLocation =me.items.items[3].county.rawValue;
							  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
	    							mapLocation = cityLocation + countryLocation;
	    						}else if(Ext.isEmpty(cityLocation)){
	    							mapLocation = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.shanghaishi');
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
  //查看网点电子地图
   mainGisShowWindow:null,
   getMainGisShowWindow:function(){
	   var me =this;
	   if(Ext.isEmpty(this.mainGisShowWindow)){
		   this.mainGisShowWindow =Ext.create('Foss.baseinfo.vehicleagencydept.MainGisWindow',{
			   'parent':this,
			   listeners:{
				   //显示之前的事件
				   beforeshow:function(window){
					   Ext.defer(function(){
						   var mapLoaction;
						   if(!Ext.isEmpty(me.items.items[3])){
								  //获取城市信息
								  var cityLocation = me.items.items[3].city.rawValue;
								  //获取区县信息
								  var countryLocation =me.items.items[3].county.rawValue;
								  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
		    							mapLocation = cityLocation + countryLocation;
		    						}else if(Ext.isEmpty(cityLocation)){
		    							mapLocation = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.shanghaishi');
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
  //编辑派送区域电子地图
  deliveryGisWindow:null,
  getDeliveryGisWindow:function(){
	  var me =this;
	if(Ext.isEmpty(this.deliveryGisWindow)){
		this.deliveryGisWindow =Ext.create('Foss.baseinfo.vehicleagencydept.MainGisWindow',{
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
						  if(!Ext.isEmpty(me.items.items[3])){
							  //获取城市信息
							  var cityLocation = me.items.items[3].city.rawValue;
							  //获取区县信息
							  var countryLocation =me.items.items[3].county.rawValue;
							  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
	    							mapLocation = cityLocation + countryLocation;
	    						}else if(Ext.isEmpty(cityLocation)){
	    							mapLocation = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.shanghaishi');
	    					}
						  }
						  /*new DPMap.MyMap('DRAW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
	    						var locateFeature =  DMap.LocateFeature(map);
	    			   		     var fun = function(data){
	    			   		    	window.collBackFun(data);
	    			   			 }
	    			   		     var ployfeature = DMap.PolygonFeature(map,{isAddable:true, callBackFun:fun, foregroundType:'PIANXIAN_REGIONS', backgroundType:'CITY',manipulable:1}); 
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
    						 var ployfeature = new DpMap.service.DpMap_polygon(map, window.items.items[0].getId(),{isAddable:true, callBackFun:fun, foregroundType:'PIANXIAN_REGIONS', backgroundType:'CITY',closeToolCallback:callFun});    						
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
				},
			}
		});
	}
	return this.deliveryGisWindow;
  },
  //查看派送区域电子地图
  deliveryGisShowWindow:null,
  getDeliveryGisShowWindow:function(){
	var me =this;
	if(Ext.isEmpty(this.deliveryGisShowWindow)){
		this.deliveryGisShowWindow =Ext.create('Foss.baseinfo.vehicleagencydept.MainGisWindow',{
			'parent':this,
			listeners:{
				beforeshow:function(window){
					Ext.defer(function(){
						var mapLocation;
						  if(!Ext.isEmpty(me.items.items[3])){
							  //获取城市信息
							  var cityLocation = me.items.items[3].city.rawValue;
							  //获取区县信息
							  var countryLocation =me.items.items[3].county.rawValue;
							  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
	    							mapLocation = cityLocation + countryLocation;
	    						}else if(Ext.isEmpty(cityLocation)){
	    							mapLocation = baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.shanghaishi');
	    					}
						  }
						  new DPMap.MyMap('VIEW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
	    						var locateFeature =  DMap.LocateFeature(map);
	    			   		     var ployfeature = DMap.PolygonFeature(map,{isAddable:true, callBackFun:function(){}, foregroundType:'PIANXIAN_REGIONS', backgroundType:'CITY'}); 
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
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
			allowBlank:false,
			labelWidth:100,
			readOnly:baseinfo.readOnly,
			width:250
	    };
	},
	//获得表单元素
	getItems:function(config){
		var me = this;
		return [{
			colspan:2,
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyNum'),							//代理编码
			name:'agentDeptCode',
			//allowBlank:true,//DEFECT-4664	
			regex:baseinfo.regLimit.w,
			listeners:{
				blur:function(field){
	        		//偏线代理编码 唯一
      			if(!Ext.isEmpty(field.getValue())
      				&&(baseinfo.viewAgencyState.view != config.viewState)
      				&& config.formRecord.get('agentDeptCode') != field.getValue()){
      				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.px);
      			}
      		}
      	}
		},{
			colspan:2,
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyName'),							//管理部门
			name:'agentDeptName',
			listeners:{
				blur:function(field){
	        		//偏线代理名称 唯一
      			if(!Ext.isEmpty(field.getValue())
          			&&(baseinfo.viewAgencyState.view != config.viewState)
      				&& config.formRecord.get('agentDeptName') != field.getValue()){
      				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.px);
      			}
      		}
      	}
		},{
			colspan:2,
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencydept.vehicleAgencySimple'),							//偏线代理简称
			name:'simplename',
			listeners:{
				blur:function(field){
	        		//偏线代理简称 唯一
      			if(!Ext.isEmpty(field.getValue())
          			&&(baseinfo.viewAgencyState.view != config.viewState)
      				&& config.formRecord.get('simplename') != field.getValue()){
      				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.px);
      			}
      		}
      	}
		},
		{
			colspan:6,
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			provinceLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.province'),
			cityLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.city'),
			areaLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.area'),
			width:768,
			hideLabel:true,
			nationIsBlank:true,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:true,         //应BUG-56726 要求，将县级必填项条件去除
			provinceWidth : 250,
			cityWidth : 250,
			areaWidth : 250,
			labelWid : 100,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
			colspan:2,
			xtype:'commonvehagencycompselector',
			maxLength: 65,
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyCompany'),							//代理简称
			name:'agentCompany',
			valueField: 'virtualCode'
		},{
			colspan:2,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.manageDept'),							//代理名称
			name: 'management',
			type:'ORG'
		},{
			colspan:2,
//			width:523,
			xtype :'textfield',
			maxLength: 100,
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.tel'),							//联系地址
			name:'contactPhone'
		},{
			colspan:4,
			width:523,
//			width:796,
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.remark'),								//联系人
			name:'notes',
			allowBlank:true
		},{
			colspan:2,
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.deptNum'),						//提货网点编码
			name:'stationNumber',
			regex:baseinfo.regLimit.stationNumber,
			regexText:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.deptNumBe4Count')
		},{
			colspan:3,
			width:390,
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyAddress'),							//联系人电话
			name: 'address'
		},{
			colspan:3,
			width:390,
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.carrierBusiness'),
			allowBlank:true,
			defaults:{readOnly:baseinfo.readOnly},
			items: [{ boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.leave'), name: 'leave'},
			        { boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.arrive'), name: 'arrive',listeners:{
			        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
			        		change:function(field,newV,oldV ){
			        			if(baseinfo.tempValue.init != baseinfo.init.init){
				        			baseinfo.initAgencyDeptCheckbox(newV,null,null,me.getForm())
					        		baseinfo.arriveChangeReadOnly(true,me.getForm());
			        			}
			        		}
			        	}
			        },
			        { boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.transfers'), name: 'transfer'}]
		},{
			colspan:3,
			width:390,
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.standardServices'),
			allowBlank:true,
			defaults:{readOnly:baseinfo.readOnly},
			items: [{ boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.fromMentioning'), name: 'pickupSelf',listeners:{
			        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
			        		change:function(field,newV,oldV ){
			        			if(baseinfo.tempValue.init != baseinfo.init.init){
				        			baseinfo.initAgencyDeptCheckbox(null,newV,null,me.getForm());
				        			me.getForm().findField('pickupArea').setValue();
				        		}
			        		}
			        	}
					},{ boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.homeDelivery'), name: 'pickupToDoor',listeners:{
			        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
			        		change:function(field,newV,oldV ){
			        			if(baseinfo.tempValue.init != baseinfo.init.init){
				        			baseinfo.initAgencyDeptCheckbox(null,null,newV,me.getForm());
				        			baseinfo.initDeliveryMapCanDisabled(newV,me);
				        			me.getForm().findField('deliverArea').setValue();
				        		}
			        		}
			        	}
					}]
		},{
			colspan:3,
			width:390,
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.addedServices'),
			allowBlank:true,
			defaults:{readOnly:baseinfo.readOnly},
			items: [{ boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.returnSigning'), name: 'returnBill'},
			        { boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.paymentCollection'), name: 'helpCharge'},
			        { boxLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.cashOnDelivery'), name: 'arriveCharge'}]
		},{
			colspan:2,
			fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencydept.isAirport'),//是否机场
			allowBlank:false,
	        xtype : 'radiogroup',
	        layout:'column',
	        name: 'isAirports',
			defaultType: 'radio',
			defaults:{
				width:150,
			},
			items: [{ 
				boxLabel  : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.yes'), 
				columnWidth:.5,
				name      : 'isAirport',
				//设置填入后台的值为Y
				inputValue: baseinfo.booleanType.yes,
			}, { 
				boxLabel  : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.no'), 
				columnWidth:.5,
				name      : 'isAirport',
				//设置填入后台的值为N
				inputValue: baseinfo.booleanType.no,
			}]
		},{
			colspan:4,
			xtype :'label',//占空间
		},{
			colspan:3,
			width:387,
			height:100,
			xtype:'textareafield',
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.fromMentioningArea'),								//自提区域描述
			name:'pickupArea',
			allowBlank:true
		},{
			colspan:3,
			width:387,
			height:100,
			xtype:'textareafield',
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.deliveryArea'),								//派送区域描述
			name:'deliverArea',
			allowBlank:true
		},{
			width:120,
			margin : '5 5 5 5',
			xtype:'button',
			text:'编辑网点电子地图',
			handler:function(){
				me.getMainGisWindow().show();
			}
		},{
			width:120,
			margin : '5 5 5 5',
			xtype : 'button',
			text:'查看网点电子地图',
			handler:function(){
				me.getMainGisShowWindow().show();
			}
		},{
			xtype :'label',
			colspan:2
		},{
			width:120,
			margin : '5 5 5 5',
			xtype:'button',
			text:'编辑派送区域地图',
			handler:function(){
				me.getDeliveryGisWindow().show();
			}
		},{
			width:120,
			margin : '5 5 5 5',
			xtype : 'button',
			text:'查看派送区域地图',
			handler:function(){
				me.getDeliveryGisShowWindow().show();
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
		}
		/*********隐藏区域****************
		,{
			xtype:'hiddenfield',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.province'),								//省份
			name:'provName'
		},{
			xtype:'hiddenfield',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.city'),								//城市
			name: 'cityName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.area'),							//联系电话
			name:'countyName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.agencyCompanyName'),			//代理公司名称（扩展）
			name:'agentCompanyName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.manageName'),			//管理部门名称（扩展）
			name:'managementName'
		}*/
		];
	}
});
//------------------------------------WINDOW--------------------------------
/**
 * 偏线代理网点界面win
 */
Ext.define('Foss.baseinfo.VehicleAgencyDeptWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.addVehicleAgencyDept'),								//新增偏线代理网点   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :880,
	height :620,	
    layout:'fit',
	listeners:{
		beforehide:function(me){
			//还原 全局变量值
			baseinfo.tempValue.init = baseinfo.init.init;
		}
	},
	sourceGrid:null,										//创建船体的 来源渠道 需要刷新的 grid
	editForm:null,											//偏线代理网点表单Form
	editGrid:null,											//偏线代理网点表格Grid
	formRecord:null,										//偏线代理网点实体 Foss.baseinfo.commonSelector.AgencyCompanyModel
	gridDate:null,											//偏线代理网点 信息数组  [Foss.baseinfo.commonSelector.AgencyDeptModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.VehicleAgencyDeptWinForm',{'height':260,'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.editForm];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.reset'),
			handler :function(){
				// 重置
				var gridData = [];
				baseinfo.initVehicleAgencyDeptWin(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord),gridData);
			} 
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.save'),
			cls:'yellow_button',
			handler :function(){
		    	var editForm = me.editForm.getForm(),
		    	beforeReturn = baseinfo.vehicleAgencyDeptIndex.validateBeforeClose(editForm);
		    	if(!Ext.isEmpty(beforeReturn)){
		    		baseinfo.showInfoMsg(beforeReturn);
		    		return;
		    	}
		    	
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.findField('agentDeptCode').hasActiveError()
		    			||editForm.findField('agentDeptName').hasActiveError()||!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
				baseinfo.submitAgencyDept(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord).data,me.sourceGrid,baseinfo.agencyType.px);
			}
		}];
	}
});


//------------------------------------业务方法----------------------------------
//修改偏线代理网点
baseinfo.vehicleAgencyDeptIndex.readOnly;					//readOnly属性（新增）
//grid上的 超链接事件 方法
baseinfo.vehicleAgencyDeptIndex.winShow = function(rowIndex){
	var param = {},
		grid = Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryGrid(), 
		record = grid.store.getAt(rowIndex);				//选中的计费规则数据
	param.formRecord = record;
	grid.viewVehicleAgencyDept(param).show();
};

//保存前校验 必填项是否填写完整【出发，到达，中转，自提，送货上门，自提区域描述，派送区域描述】
baseinfo.vehicleAgencyDeptIndex.validateBeforeClose = function(form) {
	var leave = form.findField('leave').getValue(),arrive = form.findField('arrive').getValue(),transfer = form.findField('transfer').getValue(),
	pickupSelf = form.findField('pickupSelf').getValue(),pickupToDoor = form.findField('pickupToDoor').getValue(),
	pickupArea = form.findField('pickupArea').getValue(),deliverArea = form.findField('deliverArea').getValue();

	var radioSum=0;
	
	//承运业务至少选择一个
	if(!leave && !arrive && !transfer){
		return baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencydept.operatingServiceBeLeastOne');
	}
	//承运业务 选择 到达 必须选择 一个 标准服务
	if(arrive && !pickupSelf && !pickupToDoor){
		return '承运业务 选择 到达时， 标准服务至少选择一个！ ';
	}
	// 标准服务 自提  必须选择 自提区域
	if(pickupSelf && Ext.isEmpty(pickupArea)){
		return '标准服务为自提时，自提区域描述不能为空！ ';
	}
	// 标准服务 送货上门 必须填写 送货区域
	if(pickupToDoor && Ext.isEmpty(deliverArea)){
		return '标准服务为送货上门时，送货区域描述不能为空！ ';
	}
	var isAirports=form.findField('isAirports').getValue();
	if(Ext.isEmpty(isAirports.isAirport)){
		return '是否飞机场为必选项,不可为空';
	}
	return null;
};
//信息
baseinfo.vehicleAgencyDeptIndex.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.vehicleAgencyDeptIndex.i18n('i18n.baseinfo-util.fossAlert'),
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

//------------------------------------STORE----------------------------------
//偏线代理公司STORE
Ext.define('Foss.baseinfo.VehicleAgencyCompanyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryVehicleAgencyCompany.action'),
		reader : {
			type : 'json',
			root : 'objectVo.businessPartnerEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//偏线代理公司 辖内 偏线代理网点STORE
Ext.define('Foss.baseinfo.VehicleAgencyCompanyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryOuterBranchsByComCode.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//偏线代理网点STORE
Ext.define('Foss.baseinfo.VehicleAgencyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryVehicleAgencyDept.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//------------------------------------FORM----------------------------------
//偏线代理网点 查询条件
Ext.define('Foss.baseinfo.QueryVehicleAgencyDeptForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 8 5 12',
    	//labelSeparator:'',
    	labelWidth:110
    },
    height :210,
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
	        fieldLabel: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyDeptNum'),						//偏线代理网点编码
			name:'agentDeptCode'
		},{
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyDept'),							//偏线代理网点
			name:'agentDeptName'
		},{
			colspan:2,
			xtype:'commonvehagencycompselector',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencydept.belongVehicleAgency'),							//所属偏线代理
			name:'agentCompany',
			valueField: 'virtualCode'
		},{
			forceSelection : true,
			xtype : 'commoncityselector',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.city'),									//所属偏线代理
			name: 'cityCode'
		},FossDataDictionary.getDataDictionaryCombo('VALUE_ADDED_SERVICES',{
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.addedServices'),								//增值服务
			name: 'valueAddedServices',
	    	//labelSeparator:'',
	    	labelWidth:110
		}),FossDataDictionary.getDataDictionaryCombo('OPERATING_SERVICES',{
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.carrierBusiness'),								//承运业务
			name: 'carrierBusiness',
	    	//labelSeparator:'',
	    	labelWidth:110
		}),{
			colspan: 3,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.manageDept'),								//所属偏线代理
			name: 'management',
			type:'ORG'
		},{
			xtype : 'container',colspan: 3,
			defaultType:'button',
			layout:'column',
			disabled:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptQueryButton'),
			hidden:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptQueryButton'),
			items : [{
				width: 75,
				text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.reset'),
				handler : function() {
					this.up('form').getForm().reset();
					var management = this.up('form').getForm().findField('management');
					management.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
				text :  baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptQueryButton'),
				hidden:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptQueryButton'),
				cls:'yellow_button',
				handler : function() {
				    //查询条件是否全部可为空
					var grid = Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryGrid();
					grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				}
			}]
		},{
			xtype:'hiddenfield',
			fieldLabel:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencydept.cityName'),									//城市名称
			name: 'cityName'
		}];
	}
});
//------------------------------------GRID----------------------------------
/**
 * 偏线代理网点查询grid
 */
Ext.define('Foss.baseinfo.QueryVehicleAgencyDeptGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
		return this.pagingToolbar;
	},
	//得到偏线代理网点编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.VehicleAgencyDeptWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		return baseinfo.initVehicleAgencyDeptWin(win,viewState,formRecord,gridData);
	},
	//得到偏线代理网点编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getVehicleAgencyDeptWin:function(viewState,param){
		if(baseinfo.viewAgencyState.add === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.addVehicleAgencyDeptWin,baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.addVehicleAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.update === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.updateVehicleAgencyDeptWin,baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.alertVehicleAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.view === viewState){
			baseinfo.readOnly = true;
			return this.getAgencyDeptWin(this.viewVehicleAgencyDeptWin,baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.viewVehicleAgency'),viewState,param);
		}
	},
	addVehicleAgencyDeptWin:null,						//新增基偏线代理网点
	addVehicleAgencyDept:function(param){
		return this.getVehicleAgencyDeptWin(baseinfo.viewAgencyState.add,param);
	},
	updateVehicleAgencyDeptWin:null,						//修改基偏线代理网点
	updateVehicleAgencyDept:function(param){
		return this.getVehicleAgencyDeptWin(baseinfo.viewAgencyState.update,param);
	},
	viewVehicleAgencyDeptWin:null,						//查看基偏线代理网点
	viewVehicleAgencyDept:function(param){
		return this.getVehicleAgencyDeptWin(baseinfo.viewAgencyState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns();
		me.store = me.getStore();
		me.listeners = me.getMyListeners();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
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
	    	//查看 偏线代理网点
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewVehicleAgencyDept(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.VehicleAgencyDeptStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var outerBranchEntity = queryForm.record.getData();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//代理网点编码
								'objectVo.outerBranchEntity.agentDeptCode':outerBranchEntity.agentDeptCode,
								//代理网点名称
								'objectVo.outerBranchEntity.agentDeptName':outerBranchEntity.agentDeptName,
								//代理公司
								'objectVo.outerBranchEntity.agentCompany':outerBranchEntity.agentCompany,
								//管理部门
								'objectVo.outerBranchEntity.management':outerBranchEntity.management,
								//城市编码
								'objectVo.outerBranchEntity.cityCode':outerBranchEntity.cityCode,
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
	getTbar:function(config){
		var me = this;
		return [{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptAddButton'),
			hidden:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptAddButton'),
			handler :function(){
				var win = me.addVehicleAgencyDept({});
				win.show();
				var dynamicorgcombselector = win.down('form').down('dynamicorgcombselector');
				dynamicorgcombselector.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
			} 
		},'-', {
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptVoidButton'),
			hidden:!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptVoidButton'),
			handler :function(){
				baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.px,baseinfo.delAgencyType,null,me);
			} 
		},'-', {
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.export'),								//导出
//			hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneExportButton'),
			handler :function(){
				baseinfo.vehicleAgencyDeptIndex.exportVehicleAgencyDepts(config.queryForm);
			} 
		}];
	},
	getColumns:function(){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.update'),
                disabled :!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptEditButton'),
                width:100,
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateVehicleAgencyDept(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.void'),
                disabled :!baseinfo.vehicleAgencyDeptIndex.isPermission('vehicleAgencyDeptIndex/vehicleAgencyDeptVoidButton'),
                handler: function(grid, rowIndex, colIndex) {
					baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.px,null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			//TODO 偏线代理网点编码  超链接
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencycompany.vehicleAgencyDeptNum'),							//代理编码
			width:120,
			dataIndex : 'agentDeptCode',
			renderer:function(value,meta,record,rowIndex,celIndex){
				var v = "'"+rowIndex+"'";
				return Ext.isEmpty(value)?'':'<a href="javascript:baseinfo.vehicleAgencyDeptIndex.winShow('+v+')">'+value+'</a>';
			}
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencydept.vehicleAgencyDeptName'),							//偏线代理网点名称
			width:145,
			dataIndex : 'agentDeptName'
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.vehicleagencydept.belongVehicleAgencyCompany'),							//所属偏线代理公司
			width:145,
			dataIndex : 'agentCompanyName'
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.manageDept'),									//管理部门
			width:120,
			dataIndex : 'managementName'
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.province'),										//省份
			width:80,
			dataIndex : 'provName'
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.city'),										//城市
			width:80,
			dataIndex : 'cityName'
		},{
			text : baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.area'),										//区/县
			width:80,
			dataIndex : 'countyName'
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QueryVehicleAgencyDeptForm',{'record':Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.QueryVehicleAgencyDeptGrid',{'queryForm':queryForm});//查询结果显示列表
	var management = queryForm.getForm().findField('management');
	management.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
	//TODO 根据角色判断是否管理员,如果不是管理员 则 设置为 不可编辑
	//management.setReadOnly();
	Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-vehicleAgencyDeptIndex_content',
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
			text:baseinfo.vehicleAgencyDeptIndex.i18n('foss.baseinfo.void'),								//作废
			hidden:true,
			handler :function(){
				baseinfo.deleteAgencyDeptByCode(baseinfo.agencyType.px,baseinfo.delAgencyType,null,Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryGrid());
			}
		}]
	}));
});
//------------------------------------WINDOW----------------------------------

