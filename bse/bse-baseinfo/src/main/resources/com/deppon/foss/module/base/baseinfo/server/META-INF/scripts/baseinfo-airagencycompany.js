/**
 * 空运代理公司查询Form								Foss.baseinfo.QueryAirAgencyCompanyForm
 * 空运代理公司查询结果grid							Foss.baseinfo.QueryAirAgencyCompanyGrid
 * 空运代理公司新增Win								Foss.baseinfo.AirAgencyCompanyWin
 * 空运代理公司界面form								Foss.baseinfo.AirAgencyCompanyWinForm
 * 空运代理公司界面grid								Foss.baseinfo.AirAgencyCompanyWinGrid
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
			field.markInvalid(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		//fieldLabel+result.objectVo.businessPartnerEntity
		field.markInvalid(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
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
			field.markInvalid(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		//fieldLabel+result.objectVo.businessPartnerEntity
		field.markInvalid(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.airAgencyCompany.i18n('i18n.baseinfo-util.fossAlert'),
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
//偏线代理网点 弹出界面上 数据渲染
baseinfo.initVehicleAgencyDeptWin = function(win,viewState,formRecord,gridData){
	//标识为 初始窗体数据
	baseinfo.tempValue.init = baseinfo.init.init;
	//加载数据
	win.editForm.loadRecord(baseinfo.dealOuterBranchDis2Boolean(formRecord));
	//TODO 公共组件
	win.editForm.getForm().findField('agentCompany').setCombValue(formRecord.get('agentCompanyName'),formRecord.get('agentCompany'));//代理公司 
	win.editForm.getForm().findField('management').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//省份
	
	var form  = win.editForm.getForm();
	//查看状态下 只有 取消按钮可用 [取消]按钮占 0
	if(baseinfo.viewAgencyState.view === viewState){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 0);
		}
		baseinfo.formFieldSetReadOnly(true,win.editForm);
	}else{
		// 只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
		// 只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
		// 只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
		baseinfo.initAgencyDeptCheckbox(
				form.findField('arrive').getValue(),
				form.findField('pickupSelf').getValue(),
				form.findField('pickupToDoor').getValue(),
				form);
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
		//form.findField('pickupArea').allowBlank=false;
	}
	// 只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
	if(!Ext.isEmpty(isPickupToDoor)){
		form.findField('deliverArea').setReadOnly(!(baseinfo.booleanStr.yes === isPickupToDoor || isPickupToDoor));
		//form.findField('deliverArea').allowBlank =false;
	}
	return form;
};
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
		m_success = baseinfo.airAgencyCompany.i18n('foss.baseinfo.saveSuccess'),								//保存成功！
		m_failure = baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.saveFail'),								//保存失败！
		m_dateError = baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError'),								//数据异常！
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
baseinfo.deleteAgencyDeptByCode = function(agencyType,operatRecord,grid,delAgencyDeptType){
//	var grid = Ext.getCmp('T_baseinfo-vehicleAgencyDeptIndex_content').getQueryGrid();
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		var codeStr = '';
		if(!Ext.isEmpty(delAgencyDeptType)&&baseinfo.delAgencyType===delAgencyDeptType){
			//批量作废
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
			}
		}else{
			codeStr = operatRecord.get('virtualCode');
		}
		var objectVo = {},
		url = (baseinfo.agencyType.px === agencyType)?baseinfo.realPath('deleteVehicleAgencyDeptByCode.action'):baseinfo.realPath('deleteAirAgencyDeptByCode.action');
		objectVo.codeStr = codeStr;

		Ext.MessageBox.buttonText.yes = baseinfo.airAgencyCompany.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.airAgencyCompany.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.airAgencyCompany.i18n('foss.baseinfo.tipInfo'),baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.isDeleteCompanyInfo'),function(btn,text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:url,
					async:false,
					jsonData:{'objectVo':objectVo},
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError'));
						}else{				//操作返回值
							if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
								grid.store.loadPage(1);
								baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.deleteSuccess'));
							}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
							    //TODO 作废失败原因后台是否详细描述
								baseinfo.showInfoMsg(Ext.isEmpty(result.message)?baseinfo.airAgencyCompany.i18n('foss.baseinfo.deleteFailure'):result.message);
							}
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError'));
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
//空运代理公司STORE
Ext.define('Foss.baseinfo.AirAgencyCompanyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirAgencyCompany.action'),
		reader : {
			type : 'json',
			root : 'objectVo.businessPartnerEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//空运代理公司 辖内 偏线代理网点STORE
Ext.define('Foss.baseinfo.AirAgencyCompanyDeptStore', {
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
//空运代理网点STORE
Ext.define('Foss.baseinfo.AirAgencyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirAgencyDept.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//空运代理网点 编辑窗体 form
Ext.define('Foss.baseinfo.AirAgencyDeptWinForm', {
	extend : 'Ext.form.Panel',
	title:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airDeptInfo'),
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 6
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
		//TODO 通过不同的查看状态 拼接 界面
		return [{
			colspan:2,
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airCompanyCode'),							//代理编码
			name:'agentDeptCode',
			allowBlank:true,
			listeners:{
				blur:function(field){
	        		//偏线代理编码 唯一
        			if(!Ext.isEmpty(field.getValue())
        				&&(baseinfo.viewAgencyState.view != config.viewState)
        				&& config.formRecord.get('agentDeptCode') != field.getValue()){
        				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.kx);
        			}
        		}
        	}
		},{
			colspan:2,
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airCompanyName'),							//管理部门
			name:'agentDeptName',
			listeners:{
				blur:function(field){
	        		//偏线代理名称 唯一
        			if(!Ext.isEmpty(field.getValue())
            			&&(baseinfo.viewAgencyState.view != config.viewState)
        				&& config.formRecord.get('agentDeptName') != field.getValue()){
        				baseinfo.agencyDeptIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.kx);
        			}
        		}
        	}
		},{
			colspan:2,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.manageDept'),							//代理名称
			name: 'management',
			type:'ORG'
		},
		{
			colspan:6,
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			provinceLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),
			cityLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),
			areaLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.area'),
			width:768,
			hideLabel:true,
			allowBlank:true,
			provinceWidth : 250,
			cityWidth : 250,
			areaWidth : 250,
			nationIsBlank:true,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:true,
			labelWid : 100,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		}
//		{
//			colspan:2,
//			//TODO 共用选择框，省份
//			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),								//省份
//			name:'provCode'
//		},{
//			colspan:2,
//			//TODO 共用选择框，城市
//			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),								//城市
//			name: 'cityCode'
//		},{
//			colspan:2,
//			//TODO 共用选择框，区/县
//	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.area'),								//联系电话
//			name:'countyCode'
//		}
		,{
			colspan:2,
			xtype:'commonairagencycompanyselector',
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.companyInfo'),							//代理简称
			name:'agentCompany',
			valueField: 'virtualCode'
		},{
			colspan:2,
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.tel'),							//联系地址
			name:'contactPhone'
		},{
			colspan:2,
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.orderTel'),						//正单联系电话
			name:'airWaybillTel'
		},{
			colspan:2,
//			width:523,
//			width:796,
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.remark'),								//联系人
			name:'notes',
			allowBlank:true
		},{
			colspan:2,
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.deptNum'),						//提货网点编码
			name:'stationNumber',
			regex:baseinfo.regLimit.stationNumber,
			regexText:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.deptNumBe4Count')
		},{
			colspan:2,
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.orderName'),						//正单开单名称
			name:'airWaybillName'
		},{
			colspan:3,
			width:390,
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.companyAddress'),							//联系人电话
			name: 'address'
		},{
			colspan:3,
			width:390,
			name:'carrierBusiness',
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.carrierBusiness'),
			allowBlank:true,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.leave'), name: 'leave'},
			        { boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.arrive'), name: 'arrive',listeners:{
		        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
		        		change:function(field,newV,oldV ){
		        			if(baseinfo.tempValue.init != baseinfo.init.init){
			        			baseinfo.initAgencyDeptCheckbox(newV,null,null,me.getForm())
				        		baseinfo.arriveChangeReadOnly(true,me.getForm());
		        			}
		        		}
		        	}
		        },{ boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.transfers'), name: 'transfer'}]
		},{
			colspan:3,
			width:390,
			name:'standardServices',
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.standardServices'),
			allowBlank:true,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.fromMentioning'), name: 'pickupSelf',listeners:{
	        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
	        		change:function(field,newV,oldV ){
	        			if(baseinfo.tempValue.init != baseinfo.init.init){
		        			baseinfo.initAgencyDeptCheckbox(null,newV,null,me.getForm());
		        			me.getForm().findField('pickupArea').setValue();
		        		}
	        		}
	        	}
			},{ boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.homeDelivery'), name: 'pickupToDoor',listeners:{
	        		//只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”
	        		change:function(field,newV,oldV ){
	        			if(baseinfo.tempValue.init != baseinfo.init.init){
		        			baseinfo.initAgencyDeptCheckbox(null,null,newV,me.getForm());
		        			me.getForm().findField('deliverArea').setValue();
		        		}
	        		}
	        	}
			}]
		},{
			colspan:3,
			width:390,
			xtype:'checkboxgroup',
			fieldLabel : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addedServices'),
			allowBlank:true,
			defaults:{readOnly:(baseinfo.viewAgencyState.view == config.viewState)},
			items: [{ boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.returnSigning'), name: 'returnBill'},
			        { boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.paymentCollection'), name: 'helpCharge'},
			        { boxLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.cashOnDelivery'), name: 'arriveCharge'}]
		},{
			colspan:2,
			fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencydept.isAirport'),//是否机场
			allowBlank:true,
			name      : 'isAirports',
	        xtype : 'radiogroup',
	        layout:'column',
			defaultType: 'radio',
			defaults:{
				width:150,
			},
			items: [{ 
				boxLabel  :  baseinfo.airAgencyCompany.i18n('foss.baseinfo.yes'), 
				columnWidth:.5,
				name      : 'isAirport',
				//设置填入后台的值为Y
				inputValue: baseinfo.booleanType.yes,
			}, { 
				boxLabel  :  baseinfo.airAgencyCompany.i18n('foss.baseinfo.no'), 
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
			height:360,//师锦涛-将自提区描述高度从40修改为360
			xtype:'textareafield',
	        fieldLabel: baseinfo.airAgencyDeptIndex.i18n('foss.baseinfo.airagencycompany.fromMentioningArea'),								//自提区域描述
			name:'pickupArea',
			allowBlank:true
		},{
			colspan:3,
			width:387,
			height:360,//师锦涛-将派送区描述高度从40修改为360
			xtype:'textareafield',
			allowBlank:true,
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.deliveryArea'),								//派送区域描述
			name:'deliverArea'
		}
		/*********隐藏区域****************
		,{
			xtype:'hiddenfield',
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),								//省份
			name:'provName'
		},{
			xtype:'hiddenfield',
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),								//城市
			name: 'cityName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.area'),							//联系电话
			name:'countyName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyCompanyName'),			//代理公司名称（扩展）
			name:'agentCompanyName'
		},{
			xtype:'hiddenfield',
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.manageName'),			//管理部门名称（扩展）
			name:'managementName'
		}*/
		];
	}
});
//------------------------------------GRID----------------------------------

/**
 * 空运代理网点界面win
 */
Ext.define('Foss.baseinfo.AirAgencyDeptWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addAgencyCompanyDept'),								//新增空运代理网点   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :880,
	height :500,	
    layout:'fit',
	listeners:{
		beforehide:function(me){
			//TODO 清空数据
		}
	},
	sourceGrid:null,										//创建船体的 来源渠道 需要刷新的 grid
	editForm:null,											//空运代理网点表单Form
	editGrid:null,											//空运代理网点表格Grid
	formRecord:null,										//空运代理网点实体 Foss.baseinfo.commonSelector.AgencyCompanyModel
	gridDate:null,											//空运代理网点 信息数组  [Foss.baseinfo.commonSelector.AgencyDeptModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.AirAgencyDeptWinForm',{'height':260,'viewState':config.viewState,formRecord:config.formRecord});
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.reset'),
			handler :function(){
				var gridData = [];
				baseinfo.initVehicleAgencyDeptWin(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord),gridData);
			} 
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.save'),
			cls:'yellow_button',
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.findField('agentDeptCode').hasActiveError()
		    			||editForm.findField('agentDeptName').hasActiveError()||!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
		    	if((editForm.findField('carrierBusiness').getValue() == baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.arrive')) && editForm.findField('carrierBusiness').getValue() == null){
		    		baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.checkCarrierBusiness'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
				baseinfo.submitAgencyDept(me,me.viewState,baseinfo.dealOuterBranchBoolean2Dis(me.formRecord).data,me.sourceGrid,baseinfo.agencyType.ky);
			}
		}];
	}
});














//------------------------------------常量和公用方法----------------------------------
baseinfo.airAgencyCompany.readOnly;					//readOnly属性（编辑窗体）
//------------------------------------业务方法----------------------------------
//弹出界面上 数据渲染
baseinfo.airAgencyCompany.initAirAgencyCompanyWin = function(win,viewState,formRecord,gridData){
	//加载数据
	win.editForm.loadRecord(formRecord);
	// 公共组件 
	win.editForm.getForm().findField('management').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//省份
//	win.editForm.getForm().findField('provCode').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),1);//省份
//	win.editForm.getForm().findField('cityCode').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),2);//城市
	if(baseinfo.viewAgencyState.add != viewState){
		win.editGrid.store.load();
	}else{
		win.editGrid.store.loadData([]);
		// 公共组件 
		win.editForm.getForm().findField('management').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//部门
	}
	if(baseinfo.viewAgencyState.update === viewState){
		win.down('button').setDisabled(false);
	}else{
		//新增 查看 状态下 添加网点按钮均不可用
		win.down('button').setDisabled(true);
		//查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
		baseinfo.viewAgencyState.operateWinBtn(win,viewState);
	}
	if(baseinfo.viewAgencyState.view === viewState){
		baseinfo.formFieldSetReadOnly(true,win.editForm);
	}
	return win;
};
//grid上的 超链接事件 方法  代理公司
baseinfo.airAgencyCompany.winShow = function(rowIndex){
	var param = {},
		grid = Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content').getQueryGrid(), 
		record = grid.store.getAt(rowIndex);				//选中的计费规则数据
	param.formRecord = record;
	grid.viewAirAgencyCompany(param).show();
};
//grid上的 超链接事件 方法  代理网点
baseinfo.airAgencyCompany.winDeptShow = function(rowIndex){
	var param = {},
		grid = Ext.getCmp('Foss_baseinfo_AirAgencyCompanyWinGrid_ID'), 
		record = grid.store.getAt(rowIndex);				//选中的计费规则数据
	param.formRecord = record;
	grid.viewAirAgencyDept(param).show();
};
//修改空运代理公司
baseinfo.airAgencyCompany.submitAirAgencyCompany = function(win,viewState,businessPartnerEntity){
	var grid = Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addAirAgencyCompany.action')
		,m_success = baseinfo.airAgencyCompany.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {};
	objectVo.businessPartnerEntity = businessPartnerEntity;
	if(baseinfo.viewAgencyState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewAgencyState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateAirAgencyCompany.action');
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
					if(baseinfo.viewAgencyState.add === viewState){
						//只有保存空运代理公司信息后，才能点击“添加网点”按钮为该公司添加空运代理网点；
						win.down('button').setDisabled(false);
						//TODO 把虚拟编码 写到 win表单上
						win.formRecord.set('virtualCode',result.objectVo.businessPartnerEntity.virtualCode);
					}
					//查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
					baseinfo.viewAgencyState.operateWinBtn(win,viewState,baseinfo.operateType.save);
					//form表单元素都设置成只读
					baseinfo.formFieldSetReadOnly(true,win.down('form'));

					baseinfo.showInfoMsg(m_success);
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
//获得空运代理公司 辖内 空运代理网点 businessPartnerEntity OBJ
baseinfo.airAgencyCompany.queryOuterBranchListByComCode = function(businessPartnerEntity){
	var url = baseinfo.realPath('queryOuterBranchsByComCode.action')
		,objectVo = {},outerBranchs = [],m_dateError = baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError');
	objectVo.businessPartnerEntity = businessPartnerEntity;
	Ext.Ajax.request({
		url:url,
		async:false,
		jsonData:{'objectVo':objectVo},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showInfoMsg(m_dateError);
			}else{
				outerBranchs = result.objectVo.outerBranchEntityList;
			}
			return outerBranchs;
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
	return outerBranchs;
};
//作废空运代理公司
baseinfo.airAgencyCompany.deleteAirAgencyCompanyByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content').getQueryGrid();
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		var codeStr = '';
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			//批量作废
			codeStr = selection[0].get('virtualCode');
			for (var j = 1; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
			}
			operatRecord = selection[0];
		}else{
			codeStr = operatRecord.get('virtualCode');
		}
		var objectVo = {},hasDept = baseinfo.airAgencyCompany.queryOuterBranchListByComCode(operatRecord.data);
		objectVo.codeStr = codeStr;
		if(!Ext.isEmpty(hasDept)){
			baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.operatorError'));
			return;
		}
		Ext.MessageBox.buttonText.yes = baseinfo.airAgencyCompany.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.airAgencyCompany.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.airAgencyCompany.i18n('foss.baseinfo.tipInfo'),baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.confirmDeleteCompanyInfo'),function(btn,text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url:baseinfo.realPath('deleteAirAgencyCompanyByCode.action'),
					async:false,
					jsonData:{'objectVo':objectVo},
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError'));
						}else{				//操作返回值
							if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
								grid.store.loadPage(1);
//								grid.getPagingToolbar().moveFirst();
								baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.deleteSuccess'));
							}else if(baseinfo.operatorCount.failureV === result.objectVo.returnInt){
							    //TODO 作废失败原因后台是否详细描述
								baseinfo.showInfoMsg(Ext.isEmpty(result.message)?baseinfo.airAgencyCompany.i18n('foss.baseinfo.deleteFailure'):result.message);
							}
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.dataError'));
						}else{
							baseinfo.showInfoMsg(result.message);
						}
					}
				});
			}
		});
	}
};

//------------------------------------STORE----------------------------------
//空运代理公司STORE
Ext.define('Foss.baseinfo.AirAgencyCompanyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirAgencyCompany.action'),
		reader : {
			type : 'json',
			root : 'objectVo.businessPartnerEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//空运代理公司 辖内 偏线代理网点STORE
Ext.define('Foss.baseinfo.AirAgencyCompanyDeptStore', {
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
//空运代理网点STORE
Ext.define('Foss.baseinfo.AirAgencyDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirAgencyDept.action'),
		reader : {
			type : 'json',
			root : 'objectVo.outerBranchEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//------------------------------------FORM----------------------------------
//空运代理公司 查询条件
Ext.define('Foss.baseinfo.QueryAirAgencyCompanyForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.airAgencyCompany.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelSeparator:''
    },
    height :150,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
	constructor : function(config) {						//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyName'),							//代理名称
			name:'agentCompanyName'
		},{
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyCode'),							//代理编码
			name:'agentCompanyCode'
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.manageDept'),							//管理部门
			name: 'management',
			type:'ORG'
		},{
			xtype : 'container',colspan:3,
			defaultType:'button',
			layout:'column',
			disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyQueryButton'),
			hidden:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyQueryButton'),
			items : [{
				width: 75,
				text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.reset'),
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
				text :  baseinfo.airAgencyCompany.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyQueryButton'),
				hidden:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyQueryButton'),
				handler : function() {
				    //查询条件是否全部可为空
					var grid  = Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content').getQueryGrid();//得大grid
					grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
//空运代理公司 编辑窗体 form
Ext.define('Foss.baseinfo.AirAgencyCompanyWinForm', {
	extend : 'Ext.form.Panel',
	title:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airAgencyCompanyInfo'),
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    //viewState:null,											//查看状态
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
			readOnly:baseinfo.airAgencyCompany.readOnly
	    };
	},
	//获得表单元素
	getItems:function(config){
		var me = this;
		return [{
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyCode'),							//代理编码
			name:'agentCompanyCode',
			listeners:{
				blur:function(field){
	        		//代理编码 唯一
        			if(!Ext.isEmpty(field.getValue())
        				&&!baseinfo.airAgencyCompany.readOnly
        				&& config.formRecord.get('agentCompanyCode') != field.getValue()){
        				baseinfo.agencyCompanyIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.kx);
        			}
        		}
        	}
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.manageDept'),							//管理部门
			name:'management',
			allowBlank:true,
			type:'ORG'
		},{
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyName'),							//代理名称
			name: 'agentCompanyName',
			listeners:{
				blur:function(field){
	        		//代理名称 唯一
        			if(!Ext.isEmpty(field.getValue())
        				&&!baseinfo.airAgencyCompany.readOnly
        				&& config.formRecord.get('agentCompanyName') != field.getValue()){
        				baseinfo.agencyCompanyIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.kx);
        			}
        		}
        	}
		},{
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencySimpleName'),							//代理简称
			name:'simplename',
			listeners:{
				blur:function(field){
	        		//代理简称 唯一
        			if(!Ext.isEmpty(field.getValue())
        				&&!baseinfo.airAgencyCompany.readOnly
        				&& config.formRecord.get('simplename') != field.getValue()){
        				baseinfo.agencyCompanyIsExist(field,field.getValue(),field.fieldLabel,field.name,baseinfo.agencyType.kx);
        			}
        		}
        	}
		},{
			colspan:2,
			hideLabel:true,
			provinceWidth:250,// 省份长度
			provinceLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),// 省份label
			provinceName:'provCode',// 省份名称—对应name
			cityWidth:250,// 城市长度
			cityLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),// 城市label
			cityName:'cityCode',// 城市name
			labelWid : 100,
			width:570,
			type : 'P-C',
			xtype : 'linkregincombselector'
		}
//		,{
//			//TODO 共用选择框，省份
//			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),								//省份
//			name:'provCode'
//		},{
//			//TODO 共用选择框，城市
//			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),								//城市
//			name: 'cityCode'
//		}
		,{
			colspan:3,
			width:768,
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.linkAddress'),							//联系地址
			name:'contactAddress'
		},{
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.tel'),							//联系电话
			name:'contactPhone'
		},{
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.contact'),								//联系人
			name:'contact',
			allowBlank:true
		},{
			fieldLabel:baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.contactTel'),							//联系人电话
			name: 'mobilePhone',
			allowBlank:true
		},{
			colspan:3,
			width:768,
			height:40,
			xtype:'textareafield',
	        fieldLabel: baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.remark'),								//备注
			name:'notes',
			allowBlank:true
		}];
	}
});
//------------------------------------GRID----------------------------------
/**
 * 空运代理公司查询grid
 */
Ext.define('Foss.baseinfo.QueryAirAgencyCompanyGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.airAgencyCompany.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//得到空运代理公司编辑窗体
	getAgencyCompanyWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.commonSelector.AgencyCompanyModel'):param.formRecord
				,gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		baseinfo.airAgencyCompany.readOnly = (baseinfo.viewAgencyState.view === viewState);
		//弹出框 grid中  操作按钮图标 是否可用控制
		baseinfo.actioncolumnDisabled = baseinfo.airAgencyCompany.readOnly;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.AirAgencyCompanyWin',{
				'title':title,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.airAgencyCompany.initAirAgencyCompanyWin(win,viewState,formRecord,gridData);
	},
	//得到空运代理公司编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getAirAgencyCompanyWin:function(viewState,param){
		if(baseinfo.viewAgencyState.add === viewState){
			return this.getAgencyCompanyWin(this.addAirAgencyCompanyWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addAirAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.update === viewState){
			return this.getAgencyCompanyWin(this.updateAirAgencyCompanyWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.alterAirAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.view === viewState){
			return this.getAgencyCompanyWin(this.viewAirAgencyCompanyWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.viewAirAgency'),viewState,param);
		}
	},
	addAirAgencyCompanyWin:null,						//新增基空运代理公司
	addAirAgencyCompany:function(param){
		return this.getAirAgencyCompanyWin(baseinfo.viewAgencyState.add,param);
	},
	updateAirAgencyCompanyWin:null,						//修改基空运代理公司
	updateAirAgencyCompany:function(param){
		return this.getAirAgencyCompanyWin(baseinfo.viewAgencyState.update,param);
	},
	viewAirAgencyCompanyWin:null,						//查看基空运代理公司
	viewAirAgencyCompany:function(param){
		return this.getAirAgencyCompanyWin(baseinfo.viewAgencyState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			align : 'center',
			xtype : 'actioncolumn',
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.airAgencyCompany.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyEditButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateAirAgencyCompany(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.airAgencyCompany.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyVoidButton'),
                handler: function(grid, rowIndex, colIndex) {
					baseinfo.airAgencyCompany.deleteAirAgencyCompanyByCode(null,grid.getStore().getAt(rowIndex));
                }
            }]
		},{
			//TODO 空运代理网点编码  超链接
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyCode'),							//代理编码
			dataIndex : 'agentCompanyCode',
			renderer:function(value,meta,record,rowIndex,celIndex){
				var v = "'"+rowIndex+"'";
				return Ext.isEmpty(value)?'':'<a href="javascript:baseinfo.airAgencyCompany.winShow('+v+')">'+value+'</a>';
			},
			flex : 0.7
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.agencyName'),							//代理名称
			dataIndex : 'agentCompanyName',
			flex : 1.7
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.manageDept'),							//管理部门
			dataIndex : 'managementName',
			flex : 1
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),								//省份
			dataIndex : 'provName',
			flex : 0.8
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),								//城市
			dataIndex : 'cityName',
			flex : 0.8
		}];
		me.store = Ext.create('Foss.baseinfo.AirAgencyCompanyStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//代理名称
								'objectVo.businessPartnerEntity.agentCompanyName':queryForm.findField('agentCompanyName').getValue(),
								//代理编码
								'objectVo.businessPartnerEntity.agentCompanyCode':queryForm.findField('agentCompanyCode').getValue(),
								//管理部门
								'objectVo.businessPartnerEntity.management':queryForm.findField('management').getValue()
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 空运代理公司
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewAirAgencyCompany(param).show();
	    	}
	    },
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{checkOnly:true});//mode:'SINGLE',
		//添加头部按钮
		me.tbar = [{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyAddButton'),
			hidden:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyAddButton'),
			handler :function(){
				me.addAirAgencyCompany({}).show();
			} 
		},'-', {
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyVoidButton'),
			hidden:!baseinfo.airAgencyCompany.isPermission('airAgencyCompanyIndex/airAgencyCompanyVoidButton'),
			handler :function(){
				baseinfo.airAgencyCompany.deleteAirAgencyCompanyByCode(baseinfo.delAgencyType);
			} 
		} ];
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/**
 * 网点列表
 */
Ext.define('Foss.baseinfo.AirAgencyCompanyWinGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_baseinfo_AirAgencyCompanyWinGrid_ID',
	title : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.deptList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.airAgencyCompany.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	viewState:null,										//查看状态
	frame: true,
	//得到空运代理网点编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.AirAgencyDeptWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.initVehicleAgencyDeptWin(win,viewState,formRecord,gridData);
	},
	//得到空运代理网点编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getAirAgencyDeptWin:function(viewState,param){
		if(baseinfo.viewAgencyState.add === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.addAirAgencyDeptWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addAirAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.update === viewState){
			baseinfo.readOnly = false;
			return this.getAgencyDeptWin(this.updateAirAgencyDeptWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.alterAirAgency'),viewState,param);
		}else if(baseinfo.viewAgencyState.view === viewState){
			baseinfo.readOnly = true;
			return this.getAgencyDeptWin(this.viewAirAgencyDeptWin,baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.viewAirAgency'),viewState,param);
		}
	},
	addAirAgencyDeptWin:null,						//新增基空运代理网点
	addAirAgencyDept:function(param){
		return this.getAirAgencyDeptWin(baseinfo.viewAgencyState.add,param);
	},
	updateAirAgencyDeptWin:null,						//修改基空运代理网点
	updateAirAgencyDept:function(param){
		return this.getAirAgencyDeptWin(baseinfo.viewAgencyState.update,param);
	},
	viewAirAgencyDeptWin:null,						//查看基空运代理网点
	viewAirAgencyDept:function(param){
		return this.getAirAgencyDeptWin(baseinfo.viewAgencyState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getGridStore(config);
		me.listeners = me.getMyListeners(config);
		//添加头部按钮
		me.tbar = me.getTbar();
		me.callParent([cfg]);
	},
	//监听事件
	getMyListeners:function(config){
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
				me.viewAirAgencyDept(param).show();
	    	}
	    };
	},
	getTbar:function(config){
		var me = this;
		return [{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addDept'),							//添加网点
			name:'grid_addDeptBtn_name',
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				var param = {},formRecord = Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel');
				formRecord.set('agentCompany',me.up('window').formRecord.get('virtualCode'));
				formRecord.set('agentCompanyName',me.up('window').formRecord.get('agentCompanyName'));
				param.formRecord = formRecord;
				var win = me.addAirAgencyDept(param);
				win.show();				
				var dynamicorgcombselector = win.down('form').down('dynamicorgcombselector');
				dynamicorgcombselector.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
				win.down('form').getForm().findField('agentCompany').setReadOnly(true);
			} 
		}];
	},
	//表格数据源
	getGridStore:function(config){
		var me = this;
		return Ext.create('Foss.baseinfo.AirAgencyCompanyDeptStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(operation,{
						params : {
							//代理公司 虚拟code
							'objectVo.businessPartnerEntity.virtualCode':me.up('window').formRecord.get('virtualCode')
						}
					});	
				}
		    }
		});
	},
	//表格数据列
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			hidden:(baseinfo.viewAgencyState.view === config.viewState),
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.airAgencyCompany.i18n('foss.baseinfo.update'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				var win = me.updateAirAgencyDept(param);
    				win.show();
    				win.down('form').getForm().findField('agentCompany').setReadOnly(true);
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.airAgencyCompany.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
                	baseinfo.deleteAgencyDeptByCode(null,grid.getStore().getAt(rowIndex),grid,baseinfo.agencyType.kx);
                }
            }]
		},{
			//TODO 空运代理网点编码  超链接
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airAgencyDeptCode'),					//空运代理网点编码
			dataIndex : 'agentDeptCode',
			renderer:function(value,meta,record,rowIndex,celIndex){
				var v = "'"+rowIndex+"'";
				return Ext.isEmpty(value)?'':'<a href="javascript:baseinfo.airAgencyCompany.winDeptShow('+v+')">'+value+'</a>';
			},
			flex : 1
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.airAgencyDept'),						//空运代理网点
			dataIndex : 'agentDeptName',
			flex : 1
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.province'),								//省份
			dataIndex : 'provName',
			flex : 1
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.city'),								//城市
			dataIndex : 'cityName',
			flex : 1
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.area'),								//管理部门
			dataIndex : 'countyName',
			flex : 1
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-airAgencyCompanyIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QueryAirAgencyCompanyForm');//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.QueryAirAgencyCompanyGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-airAgencyCompanyIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-airAgencyCompanyIndex_content',
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
			text:baseinfo.airAgencyCompany.i18n('foss.baseinfo.void'),								//作废
			hidden:true,
			handler :function(){
				baseinfo.airAgencyCompany.deleteAirAgencyCompanyByCode(baseinfo.delAgencyType);
			}
		}]
	}));
});
//------------------------------------WINDOW----------------------------------
/**
 * 空运代理公司界面win
 */
Ext.define('Foss.baseinfo.AirAgencyCompanyWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.addairAgencyCompany'),								//新增空运代理公司   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :850,
	height :620,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
			var winGrid = Ext.getCmp('Foss_baseinfo_AirAgencyCompanyWinGrid_ID');
			if(!Ext.isEmpty(winGrid)){
				winGrid.destroy();
			}
		}
	},
	//viewState:baseinfo.viewAgencyState.add,				//查看状态,默认为新增
	editForm:null,											//空运代理公司表单Form
	editGrid:null,											//空运代理公司表格Grid
	formRecord:null,										//空运代理公司实体 Foss.baseinfo.commonSelector.AgencyCompanyModel
	gridDate:null,											//空运代理公司 网点信息数组  [Foss.baseinfo.commonSelector.AgencyDeptModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.AirAgencyCompanyWinForm',{'height':260,'viewState':me.viewState,formRecord:config.formRecord});
		me.editGrid = Ext.create('Foss.baseinfo.AirAgencyCompanyWinGrid',{'height':210,'viewState':me.viewState});
		me.items = [me.editForm, me.editGrid];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.reset'),
			handler :function(){
				var gridData = [];
				baseinfo.airAgencyCompany.initAirAgencyCompanyWin(me,me.viewState,me.formRecord,gridData)
			} 
		},{
			text : baseinfo.airAgencyCompany.i18n('foss.baseinfo.save'),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.findField('agentCompanyCode').hasActiveError()||editForm.findField('agentCompanyName').hasActiveError()
		    			||editForm.findField('simplename').hasActiveError()||!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.airAgencyCompany.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
				baseinfo.airAgencyCompany.submitAirAgencyCompany(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

