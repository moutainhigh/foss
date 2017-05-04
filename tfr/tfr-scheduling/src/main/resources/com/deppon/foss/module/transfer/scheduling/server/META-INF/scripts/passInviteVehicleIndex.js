﻿/**
 * 外请车约车
 * 
 * 左上面板车辆信息模块
 * Panel → Foss.PassInviteVehicleIndex.LeftPanel.FormGrid
 *  Form → Foss.PassInviteVehicleIndex.Form.CarInfo 
 *  Grid → Foss.PassInviteVehicleIndex.Grid.CarInfo
 * 左下面板
 * Foss.PassInviteVehicleIndex.LeftPanel
 *  Foss.PassInviteVehicleIndex.Grid.BookingCar
 *  Foss.PassInviteVehicleIndex.LeftPanel.FormGrid
 *  Foss.PassInviteVehicleIndex.Form.InputPrice
 * 右面板
 * Foss.PassInviteVehicleIndex.Form.ApplyInfo     申请信息表单
 * Foss.PassInviteVehicleIndex.Form.ClientGoods 客户和货物信息表单
 * Foss.PassInviteVehicleIndex.Grid.AuditLogInfo  审核受理记录表格
 */

//定义一个约车的模型
Ext.define('Foss.PassInviteVehicleIndex.Model.BookingCar', {
	extend: 'Ext.data.Model',
	fields:[{
		name:'status',
		type:'string'
	},{
		name:'inviteNo',
		type:'string'
	},{
		name:'applyTime',
		type:'string',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'usePurpose',
		type:'string',
		convert:function(value){
			if(value=='INVITE_ORDER'){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose.store.inviteorder'); //外请车约车
			}else if(value=='SAMECITY_ORDER') {
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose.store.samecityorder');  //同城外租车
			}else{
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose.store.wholeorder');  //整车约车
			}
		}
	},{
		name:'useType',
		type:'string',
		convert:function(value){
			if(value=='TO_CLIENT'){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.toclient'); //到客户处
			}else if(value=='TO_TRANSIT'){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.totransit'); //到中转场
			}else {
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.tosalesdepart'); //到营业部
			}
		}
	},{
		name:'orderVehicleModel',
		type:'string'
	},{
		name:'predictUseTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'useAddress',
		type:'string'
	},{
		name:'clientName',
		type:'string'
	},{
		name:'clientContactPhone',
		type:'string'
	},{
		name:'goodsPackege',
		type:'string'
	},{
		name:'goodsQty',
		type:'string'
	},{
		name:'weight',
		type:'string'
	},{
		name:'volume',
		type:'string'
	},{
		name:'requirment',
		type:'string'
	},{
		name:'isScaneSeeGoods',
		type:'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}else if(value == 'N') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
			}else {
				return value;
			}
			
		}
	},{
		name:'isGoodsOver',
		type:'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}else if(value == 'N') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
			}else {
				return value;
			}
			
		}
	},{
		name:'dispatchTransDept',
		type:'string'
	},{
		name:'applyOrgName',
		type:'string'
	},{
		name:'applyOrgCode',
		type:'string'
	},{
		name:'applyEmpName',
		type:'string'
	},{
		name:'applyEmpCode',
		type:'string'
	},{
		name:'telephoneNo',
		type:'string'
	},{//310248
		name:'applyFees',
		type:'string'
	},{//310248
		name:'bearFeesDeptName',
		type:'string'
	},{
		name:'mobilephoneNo',
		type:'string'
	},{
		name:'isPassByArrivedDept',
		type:'string',
		convert:function(value){
			if(value=='Y'){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn.store.yes'); //是
			}else if(value=='N'){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn.store.no'); //否
			}else{
				return scheduling.passInviteVehicle.i18n(''); //空
			}
		}
	},{
		name:'arrivedAddress',
		type:'string'
	},{
		name:'arrivedDeptName',
		type:'string'
	},{
		name:'id',
		type:'string'
	},{
		name:'vehicleNo',
		type:'string'
	},{
		name:'inviteCost',
		type:'string'
	},{
		name:'auditTime',
		type:'date',
		convert:dateConvert
	},{
		name:'vehicleLengthName',
		type:'string'
	}]
});
//定义一个车辆信息的模型
Ext.define('Foss.PassInviteVehicleIndex.Model.CarInfo', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'vehicleNo',type:'string'},
		{name: 'vehcleType',type:'string'},
		{name: 'vehicleLengthName',type:'string'},
		{name: 'vehicleLengthCode',type:'string'},
		{name: 'driverName', type: 'string'},
		{name: 'driverPhone', type: 'string'},
		{name: 'isOpenVehicle', type: 'string',
		convert:function(value){
			if(value=='Y' || value==scheduling.passInviteVehicle.i18n('Foss.scheduling.common.yes')){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}else if(value=='N' || value==scheduling.passInviteVehicle.i18n('Foss.scheduling.common.no')){
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.common.no'); //否
			}else{
				return scheduling.passInviteVehicle.i18n('Foss.scheduling.common.all'); //全部
			}
		}},
		{name: 'useStaus', type: 'string'},
		{name: 'bookingDepart', type: 'string'},
		{name: 'goods', type: 'string'},
		{name: 'useTime', type: 'string'},
		{name: 'aplyer', type: 'string'},
		{name: 'assignees', type: 'string'},
		{name: 'vehicleScrapDate', type: 'string'},
		{name: 'ministryinformation', type: 'string'},
		{
			name:'perdictArriveTime',
			type:'date',
			convert:dateConvert
		},{
			name:'inviteCost',
			type:'string'
		}
	]
});

//定义一个审核受理记录的模型
Ext.define('Foss.PassInviteVehicleIndex.Model.AuditLogInfo', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'auditEmpName',type:'string'},
		{name: 'auditTime',type:'string',convert:dateConvert},
		{name: 'notes', type: 'string'},
		{name: 'isSaleDepartmentCompany', type: 'string'},
		{name: 'carpoolingType', type: 'string'},
		{name: 'status', type: 'string'},
		{name: 'inviteCost', type: 'string'}
	]
});

//定义一个审核受理记录的模型
Ext.define('Foss.PassInviteVehicleIndex.Model.CarInfoModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'vehicleNo',type:'string'},
		{name: 'status',type:'string',
			convert:function(value){
				if(value=='USING'){
					return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.using'); //已使用
				}else{
					return scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused'); //未使用
				}
			}},
		{name: 'applyOrgName',type:'string'},
		{name: 'goodsName',type:'string'},
		{name: 'predictUseTime',type:'date',type:'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}},
		{name: 'applyEmpName',type:'string'},
		{name: 'assignees',type:'string'},
		{name: 'weight',type:'number'},
		{name: 'volume',type:'number'},
		{name: 'acceptEmpName',type:'string'}
	]
});


//定义约车数据源
Ext.define('Foss.PassInviteVehicleIndex.Model.CarInfoStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.PassInviteVehicleIndex.Model.CarInfoModel',
	//定义一个代理对象
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



//定义约车数据源
Ext.define('Foss.PassInviteVehicleIndex.Store.BookingCar',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.PassInviteVehicleIndex.Model.BookingCar',
	//定义一个代理对象
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义约车数据源
Ext.define('Foss.PassInviteVehicleIndex.Store.CarInfo',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.PassInviteVehicleIndex.Model.CarInfo',
	pageSize:10,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: scheduling.realPath('queryInviteVehicleInfoList.action'),
		reader : {
			type : 'json',
			root : 'inviteVehicleVo.inviteVehicleList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = scheduling.passInviteVehicle.CarInfo.getForm();
			var vehicleNo = queryParams.findField('vehicleNo').getValue();
			var driverName = queryParams.findField('driverName').getValue();
			var driverPhone = queryParams.findField('driverPhone').getValue();
			var isOpenVehicle = queryParams.findField('isOpenVehicle').getValue();
			//var isOpenVehicle = queryParams.findField('isOpenVehicle').inputValue;
			if('N' == isOpenVehicle){
				isOpenVehicle = 'N';
			}else if('Y' == isOpenVehicle){
				isOpenVehicle = 'Y';
			}else{
				isOpenVehicle = null;
			}
			var vehicleLengthCode = queryParams.findField('vehicleLengthCode').getValue();
			Ext.apply(operation, {
				params : {
				   'inviteVehicleVo.inviteVehicleDto.vehicleNo' : vehicleNo,
				   'inviteVehicleVo.inviteVehicleDto.driverName' : driverName,
				   'inviteVehicleVo.inviteVehicleDto.driverPhone' : driverPhone,
				   'inviteVehicleVo.inviteVehicleDto.isOpenVehicle' : isOpenVehicle,
				   'inviteVehicleVo.inviteVehicleDto.vehicleLengthCode' : vehicleLengthCode
				}
			});	
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(scheduling.passInviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.passInviteVehicle.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindResult'));
			}
		}
	}
});

//定义审核受理记录数据源
Ext.define('Foss.PassInviteVehicleIndex.Store.AuditLogInfo',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.PassInviteVehicleIndex.Model.AuditLogInfo',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//创建约车信息表格
Ext.define('Foss.PassInviteVehicleIndex.Grid.BookingCar', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	frame: true,
	animCollapse: false,
	title: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.BookingCar.title'), //约车信息
	height: 180,
	autoScroll:true,
	store: null,
	selModel: null,	
	collapsible: true,
    animCollapse: true,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'top',
		   layout:'column',
		   defaultType:'button',
		   items:[{
			   xtype:'container',
			   html:'&nbsp;',
			   columnWidth:.87
		   },{
			   text: scheduling.passInviteVehicle.i18n('Foss.scheduling.button.refresh'), //刷新
			   columnWidth:.12,
			   handler:function() {
				   scheduling.passInviteVehicle.bookingCar.queryBookingCar();
			   }
		   }]
	}],
	columns: [{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.inviteNo'), //外请车单号
		flex: 1, 
		sortable: true, 
		dataIndex: 'inviteNo'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.status'), //操作状态
		flex: 1, 
		sortable: true, 
		dataIndex: 'status',
		renderer:function(value){
			return FossDataDictionary.rendererSubmitToDisplay (value,'INVITEVEHICLE_STATUS');
		}
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.usePurpose'), //使用类型 (业务类型)
		flex: 1, 
		sortable: true, 
		dataIndex: 'usePurpose'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.useType'), //用车类型
		flex: 1, 
		sortable: true, 
		dataIndex: 'useType'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.vehicleLengthName'), //用车车型
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	}],
	queryBookingCar : function(){
		var paramInviteNoList = [];
		var isLoadAll = false;
		if(!Ext.isEmpty(scheduling.passInviteVehicle.paramInviteNoList)){
			paramInviteNoList = scheduling.passInviteVehicle.paramInviteNoList;
		}
		//是否加载全部
		if(scheduling.passInviteVehicle.inviteVehicleIsLoadAll){
			isLoadAll = true;
		}
		var passInviteApplyVo = {
				'passInviteApplyVo.inviteNoList':paramInviteNoList,
				'passInviteApplyVo.inviteVehicleIsLoadAll':isLoadAll
			};
		Ext.Ajax.request({
			async: false,
			url:scheduling.realPath('queryPassInviteVehicleInfo.action'),
			params: passInviteApplyVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passInviteApplyVo.inviteVehicleDtoList;
				if(list.length == 0){
					 Ext.ux.Toast.msg(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passInviteVehicle.i18n('Foss.scheduling.operation.tip.queryBookingCarResultNull'));
				}
				scheduling.passInviteVehicle.bookingCar.store.loadData(list);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
			}
		});
	},
	queryAuditInviteLogList :function(inviteNo){
		Ext.Ajax.request({
			actionMethods:'POST',
			async:true,
			url: scheduling.realPath('queryAuditInviteLogListAndVehicleList.action'),
			params:{
				'passInviteApplyVo.inviteNo': inviteNo
			},
			success: function(response) {
				var result = Ext.decode(response.responseText);
				var dto = result.passInviteApplyVo.inviteVehicleDto;
				var auditInviteVehicleList = dto.auditInviteVehicleList;
				var sortArray = new Array();
				for(var i= 0;i<auditInviteVehicleList.length;i++){
					sortArray.push(auditInviteVehicleList[i].auditNo);
				}
				var bigNo = Ext.Array.max(sortArray);
				for(var j =0; j<auditInviteVehicleList.length;j++){
					var record = auditInviteVehicleList[j];
					if(record.auditNo==bigNo && record.status=='COMMITTED'){
						auditInviteVehicleList[j].inviteCost = dto.inviteCost;
					}
				}
				scheduling.passInviteVehicle.auditLogInfo.store.loadData(auditInviteVehicleList);
			},
			//异常message
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
			}
		});	
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PassInviteVehicleIndex.Store.BookingCar');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'SINGLE'});
		me.callParent([cfg]);
	},
	listeners:{
		select:function(model,record){
			scheduling.passInviteVehicle.applyInfo.loadRecord(record);
			var status = scheduling.passInviteVehicle.applyInfo.getForm().findField('status').getValue();
			var statusDesc = FossDataDictionary.rendererSubmitToDisplay (status,'INVITEVEHICLE_STATUS');
			scheduling.passInviteVehicle.applyInfo.getForm().findField('status').setValue(statusDesc);
			//绑定客户货物信息
			scheduling.passInviteVehicle.clientGoods.loadRecord(record);
			Ext.Ajax.request({
				url: scheduling.realPath('queryInviteVehicleApplyDetail.action'),
				params:{
					'inviteVehicleVo.inviteVehicleDto.inviteNo': record.data.inviteNo
				},
				success:function(response) {
					var result = Ext.decode(response.responseText);
					scheduling.passInviteVehicle.clientGoods.getForm().findField('goodsName').setValue(result.inviteVehicleVo.inviteVehicleDto.goodsName);
					scheduling.passInviteVehicle.clientGoods.getForm().findField('loadGoodsTime').setValue(Ext.Date.format(new Date(result.inviteVehicleVo.inviteVehicleDto.loadGoodsTime), 'Y-m-d H:i:s'));
					scheduling.passInviteVehicle.applyInfo.getForm().findField('arrivedAddress').setValue(result.inviteVehicleVo.inviteVehicleDto.arrivedAddress);
					if(result.inviteVehicleVo.inviteVehicleDto.usePurpose=='INVITE_ORDER' ||result.inviteVehicleVo.inviteVehicleDto.usePurpose=='SAMECITY_ORDER'){
						scheduling.passInviteVehicle.applyInfo.getForm().findField('arrivedAddress').setVisible(false);
						scheduling.passInviteVehicle.applyInfo.getForm().findField('arrivedDeptName').setVisible(false);
						scheduling.passInviteVehicle.applyInfo.getForm().findField('isPassByArrivedDept').setVisible(false);
						scheduling.passInviteVehicle.clientGoods.getForm().findField('isScaneSeeGoods').setVisible(false);
						scheduling.passInviteVehicle.clientGoods.getForm().findField('isGoodsOver').setVisible(false);
						scheduling.passInviteVehicle.inputPrice.getForm().findField('isSaleDepartmentCompany').setVisible(false);
						scheduling.passInviteVehicle.inputPrice.getForm().findField('isSaleDepartmentCompany').allowBlank=true;
						scheduling.passInviteVehicle.inputPrice.getForm().findField('carpoolingType').setVisible(false);
						scheduling.passInviteVehicle.inputPrice.getForm().findField('carpoolingType').allowBlank=true
						
					}
				},
				exception:function(response) {
					 var result = Ext.decode(response.responseText);
					 Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				}
			});
			
			var inviteNo = record.data.inviteNo;
			scheduling.passInviteVehicle.bookingCar.queryAuditInviteLogList(inviteNo);
		}
	}
	
});

//被受理的的车辆信息
Ext.define('Foss.PassInviteVehicleIndex.Grid.Passcar', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	frame: true,
	animCollapse: false,
	title: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Passcar.title'), //受理车辆
	height: 180,
	autoScroll:true,
	store: null,
	selModel: null,	
	collapsible: true,
    animCollapse: true,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'top',
		   layout:'column',
		   defaultType:'button',
		   items:[{
			   text: scheduling.passInviteVehicle.i18n('foss.shortdepartureplan.form.addnewplan.button.new.lable'), //新增
			   columnWidth:.12,
			   handler:function() {
			   //获取约车编号
				var recordBookingCar = scheduling.passInviteVehicle.bookingCar.getSelectionModel().getSelection();
				//判定是否有选中约车和要通过的车辆
				if(recordBookingCar.length == 0){
					Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.vehicleMessages'));//'请选择需要通过的车辆!';
					return false;
				}
				
				scheduling.passInviteVehicle.CarInfo.getForm().reset();
				scheduling.passInviteVehicle.InputPriceAndTime.getForm().reset();
				scheduling.passInviteVehicle.inviteVehiclePagingBar.hide();
				scheduling.passInviteVehicle.vehicleCarInfo.getStore().removeAll();
				scheduling.passInviteVehicle.FormGridWindow.show();
			   }
		   },{
			   xtype:'container',
			   html:'&nbsp;',
			   columnWidth:0.74
		   },{
			   text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.delete.lable'), //删除
			   columnWidth:.12,
			   handler:function() {
					 //被受理的的车辆信息
					var recordPasscar = scheduling.passInviteVehicle.Passcar.getSelectionModel().getSelection();
					//删除删除被受理的车辆的信息
					if(recordPasscar.length > 0){
						scheduling.passInviteVehicle.Passcar.getStore().remove(recordPasscar[0]);
					}
				
			   }
		   }]
	}],
	columns: [{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号
		flex: 0.5,
		sortable: true, 
		dataIndex: 'vehicleNo',
		xtype: 'tipcolumn',
		tipConfig: {
	        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
			maxWidth: 600,
			width: 600,
			height: 230,
	        //Tip的Body是否随鼠标移动
			trackMouse: true,
			//Tip的隐藏延迟时间(单位:ms)
			hideDelay: 2000
		},
		//配置tip内引用的自定义组件类名
		tipBodyElement:'Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo'
		//自动填列
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'driverName'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.driverPhone'),//'司机手机',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'driverPhone'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isOpenVehicle'),//'开蓬',
		flex: 0.23, 
		sortable: true, 
		dataIndex: 'isOpenVehicle'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginInviteCost'),//'请车价格',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'inviteCost'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'),//'预计到达时间',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'perdictArriveTime'
	},{
		xtype : 'ellipsiscolumn',
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.ministryinformation'),//'信息部名称',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'ministryinformation'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PassInviteVehicleIndex.Store.BookingCar');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'SIMPLE'});
		me.callParent([cfg]);
	}
	
});

Ext.define('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo',{
	extend: 'Ext.grid.Panel',
	title: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.title'), //外请车详细信息
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:210,
	columns: [{
			header: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号
			flex: 1,
			sortable: true,
			dataIndex: 'vehicleNo'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.status'), //使用状态
			flex: 1,
			sortable: true,
			dataIndex: 'status'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.weight'), //重量(吨)
			flex: 1,
			sortable: true,
			dataIndex: 'weight'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.volume'),//体积(方)
			flex: 1,
			sortable: true,
			dataIndex: 'volume'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.goodsName'),//'所装货物',
			flex: 1,
			sortable: true,
			dataIndex: 'goodsName'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.predictUseTime'),//'用车时间',
			flex: 1,
			sortable: true,
			dataIndex: 'predictUseTime'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.applyEmpName'),//'申请人',
			flex: 1,
			sortable: true,
			dataIndex: 'applyEmpName'
		},{
			header: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.acceptEmpName'),//'受理人',
			flex: 1,
			sortable: true,
			dataIndex: 'acceptEmpName'
		}],
	bindData: function(record){
		var previewPassInviteVehicleDetail = function(record){
			var viewRecord = null;
			Ext.Ajax.request({
				//设置后台返回的合票号
				async: false,
				url:scheduling.realPath('queryInviteUseStatus.action'),
				params :{
					'passInviteApplyVo.vehicleNo' : record.data.vehicleNo
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					viewRecord = result.passInviteApplyVo.inviteVehicleList;
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),result.message);
				}
			});
			return viewRecord;
		}
		var viewRecord = previewPassInviteVehicleDetail(record);
		var viewArrayRecord = new Array();
		for(var i = 0 ; i<viewRecord.length; i++){
			viewRecord[i].vehicleNo = record.data.vehicleNo,
			viewArrayRecord.push(viewRecord[i]);
		}
		this.store.loadData(viewArrayRecord);
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PassInviteVehicleIndex.Model.CarInfoStore');
		me.callParent([cfg]);
	}
});

//创建车辆信息表格
Ext.define('Foss.PassInviteVehicleIndex.Grid.CarInfo', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	frame:true,
	border:true,
	height:240,
	store: null,
	//设置选择模式为复选框选择
	selModel: null,
	autoScroll:true,
	autoDestroy :true, 
	columns: [{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号
		flex: 1,
		sortable: true, 
		dataIndex: 'vehicleNo',
		xtype: 'tipcolumn',
		tipConfig: {
	        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
			maxWidth: 600,
			width: 600,
			height: 230,
	        //Tip的Body是否随鼠标移动
			trackMouse: true,
			//Tip的隐藏延迟时间(单位:ms)
			hideDelay: 2000
		},
		//配置tip内引用的自定义组件类名
		tipBodyElement:'Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo'
		//自动填列
	},{
		text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		flex: 0.8, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	},{
		text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		flex: 0.8, 
		sortable: true, 
		dataIndex: 'driverName'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.driverPhone'),//'司机手机',
		flex: 1.2, 
		sortable: true, 
		dataIndex: 'driverPhone'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isOpenVehicle'),//'开蓬',
		flex: 0.5, 
		sortable: true, 
		dataIndex: 'isOpenVehicle'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PassInviteVehicleIndex.Store.CarInfo');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'SINGLE'});
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		scheduling.passInviteVehicle.inviteVehiclePagingBar = me.bbar;
		
		me.callParent([cfg]);
	}
});
//左面板-车辆信息表单
 Ext.define('Foss.PassInviteVehicleIndex.Form.CarInfo', {
	extend: 'Ext.form.Panel',	
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:80
	},
	items: [{
		xtype:'commonleasedvehicleselector',
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号
		name: 'vehicleNo',
		allowBlank:true,
		columnWidth:.5
		
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		name: 'vehicleLengthCode',
		allowBlank:true,
		xtype:'commonvehicletypeselector',
		columnWidth:.5
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		name: 'driverName',
		allowBlank:true,
		xtype:'commonleaseddriverselector',
		valueField : 'driverName',
		columnWidth:.5,
		listeners: {
			select: function(combo, record, index) {
				var driverPhone = record[0].data.driverPhone;
				var vehicleNo = record[0].data.vehicleNo;
				scheduling.passInviteVehicle.CarInfo.getForm().findField('driverPhone').setValue(driverPhone);
				scheduling.passInviteVehicle.CarInfo.getForm().findField('vehicleNo').setValue(vehicleNo);
			}
		}
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.driverPhone'),//'司机手机',
		name: 'driverPhone',
		allowBlank:true,
		columnWidth:.5,
		validator : function(value) {
			if(value.length > 11){
				return scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText');
			}
			return true;
		}
	},{
		fieldLabel : scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isOpenVehicle.is'),//'是否开蓬',
		allowBlank : true,
		name: 'isOpenVehicle',
		columnWidth:.5,
		xtype : 'combo',
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		store : [['ALL', scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all')], ['Y', scheduling.passInviteVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.YES.lable')], ['N', scheduling.passInviteVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.NO.lable')]],
		value : 'ALL'
	}/*,{
		fieldLabel: '请车价格',
		name: 'inviteCost',
		allowBlank:true,
		columnWidth:.2
	},{
		fieldLabel: '-',
		name: 'inviteCost',
		columnWidth:.1,
		labelSeparator:''
	}*/],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		
		items: [
		{
			xtype: 'button',			
			text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.ShortInnerCar.button.reset.lable'),//'重置',
			margin:'0 0 0 100',
			handler: function() {	
				scheduling.passInviteVehicle.CarInfo.getForm().reset();
			}
		},
		{
			xtype: 'button',	
			text: scheduling.passInviteVehicle.i18n('foss.shortScheduleDesign.form.InnerCarSearch.button.query.lable'),//'查询',
			margin:'0 0 0 190',
			handler: function() {
				scheduling.passInviteVehicle.inviteVehiclePagingBar.moveFirst();
				scheduling.passInviteVehicle.inviteVehiclePagingBar.show();
			}
		},{
			xtype: 'tbspacer',
			flex: 1
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});
 
//左面板-车辆信息-价格信息表单
 Ext.define('Foss.PassInviteVehicleIndex.Form.InputPriceAndTime', {
	extend: 'Ext.form.Panel',
	layout:'column',
	url:'#',	
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginInviteCost'),//'请车价格',
		name: 'inviteCost',
		allowBlank:false,
		regex:/^\d+\.?\d{0,3}$/,
		regexText: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.regexText'),//"请输入正确数字!",
		maxLength: 8,
		maxLengthText: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText'),//'长度超过最大限制!',
		columnWidth: .5
		
	},{
	    fieldLabel :  scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'),//'预计到达时间',
		name : 'perdictArriveTime',
		xtype : 'datetimefield_date97',
		columnWidth: .5,
		editable:false,
		time : true,
		id : 'Foss_scheduling_passInviteVehicleIndex_perdictArriveTime_ID',
		allowBlank:false,
		dateConfig: {
			el : 'Foss_scheduling_passInviteVehicleIndex_perdictArriveTime_ID-inputEl'
		}
	},{
	    fieldLabel :  scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.ministryinformation'),//'信息部名称',
		name : 'ministryinformation',
		xtype : 'commoninfodeptSelector',
		columnWidth: .5,
		editable:true,
		allowBlank:false
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		items: [{
				xtype: 'button',			
				text: scheduling.passInviteVehicle.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
				margin:'0 0 0 90',
				handler: function() {
					scheduling.passInviteVehicle.FormGridWindow.hide();
				}
			},{
			   xtype:'container',
			   html:'&nbsp;',
			   margin:'0 0 0 90',
			   columnWidth:.80
			},{
				xtype: 'button',
				text: scheduling.passInviteVehicle.i18n('foss.queryShortSchedule.form.ModifyShortSchedule.button.ok.lable'),//'确认',
				margin:'0 0 0 90',
				handler: function() {
					//获取约车编号
					var recordBookingCar = scheduling.passInviteVehicle.bookingCar.getSelectionModel().getSelection();
					//获取选中的车辆
					var vehicleRecord = scheduling.passInviteVehicle.vehicleCarInfo.getSelectionModel().getSelection();
					//获取请车价格和派车时间
					var inputPriceAndTimeRecord = scheduling.passInviteVehicle.InputPriceAndTime.getForm().getValues();
					
					if (!scheduling.passInviteVehicle.InputPriceAndTime.getForm().isValid()) {
						Ext.MessageBox
								.alert(
										scheduling.inviteVehicle
												.i18n('Foss.scheduling.validation.alert.title'),
										scheduling.inviteVehicle
												.i18n('Foss.scheduling.validation.tip.input')); // 有必填项未完成!
						return false;
					}
					
					//判定是否有选中约车和要通过的车辆
					if(recordBookingCar.length == 0 || vehicleRecord.length == 0){
						Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.vehicleMessages'));//'请选择需要通过的车辆!';
						return false;
					}
					//usePurpose recordBookingCar[0].get('usePurpose')
					//整车的时候请车价格不能为0,外请车可以为0
					var usePurpose = scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose.store.wholeorder');  //整车约车
					if(usePurpose === recordBookingCar[0].get('usePurpose') && (inputPriceAndTimeRecord.inviteCost == null || inputPriceAndTimeRecord.inviteCost == '' || inputPriceAndTimeRecord.inviteCost == 0)){
						Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.passInviteVehicle.i18n('Foss.scheduling.passInviteVehicle.beginInviteCost'));
						return false;
					}
					//派车时间check
					if(inputPriceAndTimeRecord.perdictArriveTime == null || inputPriceAndTimeRecord.perdictArriveTime == ''){
						Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime')); //'预计到达时间不能为空!'
						return false;
					}

					var nowDate = new Date();
					var perdictArriveTimeDate = Ext.Date.parse(inputPriceAndTimeRecord.perdictArriveTime, "Y-m-d H:i:s", true);
					var result = nowDate.getTime() >= perdictArriveTimeDate.getTime();
					if(result) {
						Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'),scheduling.passInviteVehicle.i18n('foss.scheduling.passOrderVehicle.perdictArriveTimeDate.not.null'));//'预计到达时间必须大于当前时间!');
						return false;
					}
					
					var vehicleScrapDateStr = vehicleRecord[0].data.vehicleScrapDate;
					vehicleNo = vehicleRecord[0].data.vehicleNo;
					var currentDate = new Date();
					if(vehicleScrapDateStr != null || vehicleScrapDateStr.length > 0){
						if(Ext.Date.parse(vehicleScrapDateStr, 'Y-m-d') <= currentDate){
							Ext.MessageBox.confirm(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.vehicleScrapDateStr'), function(button){  //'此外请车已到报超龄日期，是否继续受理?'
							   if(button != 'yes') {
							       return false;
							   }
						   });
						}
					}
					var vehicleData = scheduling.passInviteVehicle.Passcar.getStore();
					//受理约车多次检测
					var vehicleNoBool=false;
					vehicleData.each(function(rec){
						 if(vehicleNo == rec.data.vehicleNo){
							 vehicleNoBool=true;
						}
				      });
					if(vehicleNoBool){
						Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'),scheduling.passInviteVehicle.i18n('foss.scheduling.PassInviteVehicle.license.can.not.repeatedly.accepted'));//'一个车牌号不能同时做多次受理!');
						 return false;
					}
					if(usePurpose == recordBookingCar[0].get('usePurpose') && vehicleData.data.length>=100){
						Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'),scheduling.passInviteVehicle.i18n('foss.scheduling.PassInviteVehicle.Vehicle.maximum.Hundred'));//'整车约车可受理上限为100辆!');
						return false;
					}else if(usePurpose != recordBookingCar[0].get('usePurpose') && vehicleData.data.length >=1){
						Ext.MessageBox.alert(scheduling.passInviteVehicle.i18n('Foss.scheduling.validation.alert.title'),scheduling.passInviteVehicle.i18n('foss.scheduling.PassInviteVehicle.Not.vehicle.cars'));//'不是整车不能同时受理多辆约车!');
						return false;
					}

					var nullNewRow = Ext.create('Foss.PassInviteVehicleIndex.Model.CarInfo',vehicleRecord[0].data);
					nullNewRow.data.perdictArriveTime=inputPriceAndTimeRecord.perdictArriveTime;
					nullNewRow.data.inviteCost=inputPriceAndTimeRecord.inviteCost;
					nullNewRow.data.ministryinformation=scheduling.passInviteVehicle.InputPriceAndTime.getForm().findField("ministryinformation").getRawValue();
					scheduling.passInviteVehicle.Passcar.store.insert(scheduling.passInviteVehicle.Passcar.store.getCount(),nullNewRow);
					
					if(usePurpose != recordBookingCar[0].get('usePurpose') && scheduling.passInviteVehicle.Passcar.store.getCount() >0){
						
					}
					
					scheduling.passInviteVehicle.FormGridWindow.hide();
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});
 

//左面板-车辆信息-价格信息表单
 Ext.define('Foss.PassInviteVehicleIndex.Form.InputPrice', {
	extend: 'Ext.form.Panel',
	layout:'column',
	url:'#',	
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',	
	backAndPassAndRefuseAjax: function(url, type){
		var form = scheduling.passInviteVehicle.inputPrice.getForm();
		var recordBookingCar = scheduling.passInviteVehicle.bookingCar.getSelectionModel().getSelection();
		//TODO
		//这个地方要拿到受理车辆GRID列表里面的所有的对象
		//var vehicleRecord = scheduling.passInviteVehicle.vehicleCarInfo.getSelectionModel().getSelection();
		var vehicleData = scheduling.passInviteVehicle.Passcar.getStore();
		
		  var vehicleRecord = new Array();
		  scheduling.passInviteVehicle.vehicleRecordObj = {};
		  scheduling.passInviteVehicle.vehicleNos = '';
		  vehicleData.each(function(rec){
			  var passInviteApplyVoTemp = {};
			  passInviteApplyVoTemp.vehicleNo=rec.data.vehicleNo;
			  passInviteApplyVoTemp.inviteCost=rec.data.inviteCost;
			  passInviteApplyVoTemp.perdictArriveTime=rec.data.perdictArriveTime;
			  passInviteApplyVoTemp.ministryinformation=rec.data.ministryinformation;
			  if(!scheduling.passInviteVehicle.vehicleRecordDate){
				  scheduling.passInviteVehicle.vehicleRecordDate = rec.data.perdictArriveTime;
			  }else if(Date.parse(scheduling.passInviteVehicle.vehicleRecordDate)<Date.parse(rec.data.perdictArriveTime)){
				  scheduling.passInviteVehicle.vehicleRecordDate = rec.data.perdictArriveTime;
			  }
	    	  
	    	  vehicleRecord.push(passInviteApplyVoTemp);
	    	  
	    	  scheduling.passInviteVehicle.vehicleNos += rec.data.vehicleNo+",";
	      });
		var record = scheduling.passInviteVehicle.inputPrice.getForm().getValues();
		var vehicleMessages = '';
		var bookingCarMessages = '';
		
		var vehicleNo = scheduling.passInviteVehicle.vehicleNos.substring(0,scheduling.passInviteVehicle.vehicleNos.length-1);
		
		if(type == 'pass'){
			
			bookingCarMessages = scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.bookingCarMessages');//'请选择需要通过的约车信息!';
			vehicleMessages  = scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.vehicleMessages.add');//'请添加需要通过的车辆!';
		}
		if(type == 'back'){
			bookingCarMessages = scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.back.bookingCarMessages');//'请选择需要退回的约车信息!';
			vehicleMessages = scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.back.vehicleMessages');//'请选择需要退回的车辆!';
		}
		if(type == 'refuse'){
			bookingCarMessages = scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.refuse.bookingCarMessages');//'请选择需要拒绝的约车信息!';
		}
		if(recordBookingCar.length == 0){
			Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), bookingCarMessages);
			return false;
		}
		if(type == 'pass'){
			if(vehicleRecord.length==0){
				Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),vehicleMessages);
				return false;
			}
		}
		if(type == 'pass'){
			//只有未审核状态的外请车约车记录可以执行受理业务
			if(recordBookingCar[0].data.status != 'UNCOMMITTED'){
				Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.uncommitted'));//只有未审核状态的外请车约车记录可以执行审核受理业务
				return false;
			}
			
			if(!form.isValid()) {
				return false;
			}
			
		}else{
			if(record.notes==''){
				Ext.Msg.alert(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.notes')); //'审核结果备注不能为空!'
				return false;
			}
		}
		var inviteNo = recordBookingCar[0].data.inviteNo;
	
		scheduling.passInviteVehicle.inputPrice.getEl().mask(scheduling.passInviteVehicle.i18n('Foss.scheduling.saving'));
		
		var passInviteApplyVo={};
		passInviteApplyVo.passInviteApplyList = vehicleRecord;
		passInviteApplyVo.applyOrgCode=recordBookingCar[0].data.applyOrgCode;
		passInviteApplyVo.inviteNo=inviteNo;
		passInviteApplyVo.notes=record.notes;
		passInviteApplyVo.isSaleDepartmentCompany=record.isSaleDepartmentCompany;
		passInviteApplyVo.carpoolingType=record.carpoolingType;
		
		//var passInviteApplyVo=JSON.stringify(passInviteApplyVoTemp);
		
		
		Ext.Ajax.request({
	       actionMethods:'POST',
	       async:true,
	       url: scheduling.realPath(url),
	       jsonData:{passInviteApplyVo:passInviteApplyVo},
		   success: function(response) {
			   scheduling.passInviteVehicle.inputPrice.getEl().unmask();
			   var result = Ext.decode(response.responseText);
			   if(type=='pass'){
				   scheduling.passInviteVehicle.bookingCar.queryBookingCar();
					/*//获取拒绝或退回的约车单号
					if(recordBookingCar){
						for(var i = 0 ; i<recordBookingCar.length; i++){
							//约车单号
							inviteNo = recordBookingCar[i].data.inviteNo;
							//移除约车单号
							Ext.Array.remove(scheduling.paramInviteNoList,inviteNo);
						}
						scheduling.bookingCar.queryBookingCar();
					}*/
				   scheduling.passInviteVehicle.bookingCar.queryAuditInviteLogList(inviteNo);
				   Ext.ux.Toast.msg(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.pass.inviteNo1') + vehicleNo + scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.pass.inviteNo2'));
				  
				   var sms=recordBookingCar[0].get('usePurpose') + '：' + recordBookingCar[0].get('useType')
	                + '，'+scheduling.passInviteVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo')+'：'
	                + recordBookingCar[0].get('weight') + scheduling.passInviteVehicle.i18n('foss.scheduling.forecastQuantity.weightUnit') 
	                + '/' + recordBookingCar[0].get('volume') + scheduling.passInviteVehicle.i18n('foss.scheduling.forecastQuantity.volumeUnit')
	                + '，'+scheduling.passInviteVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime')+'：' 
	                +scheduling.passInviteVehicle.vehicleRecordDate
	                + '，'+scheduling.passInviteVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo')+'：' 
	                + recordBookingCar[0].get('inviteNo');
					
					//如果有备注则添加备注
	                if(record.notes){
	                	sms+='\n'+scheduling.passInviteVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable')+'：'+ record.notes;
	                }
	                
	                scheduling.passInviteVehicle.AuditSmsPanal.getForm().findField("sms").setValue(sms);
	                
	                scheduling.passInviteVehicle.vehicleCarInfo.getStore().removeAll();

			   }else if(type == 'back'){
				   Ext.ux.Toast.msg(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.back.inviteNo1') + vehicleNo + scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.back.inviteNo2'));
				   scheduling.passInviteVehicle.bookingCar.queryBookingCar();
					/*if(recordBookingCar){
						for(var i = 0 ; i<recordBookingCar.length; i++){
							//约车单号
							inviteNo = recordBookingCar[i].data.inviteNo;
							//移除约车单号
							Ext.Array.remove(scheduling.paramInviteNoList, inviteNo);
						}
						scheduling.bookingCar.queryBookingCar();
					}*/
				   scheduling.passInviteVehicle.applyInfo.getForm().reset();
				   scheduling.passInviteVehicle.clientGoods.getForm().reset();
				   scheduling.passInviteVehicle.auditLogInfo.store.loadData([]);
				   scheduling.passInviteVehicle.vehicleCarInfo.getStore().removeAll();
			   }else{
				   scheduling.passInviteVehicle.bookingCar.queryBookingCar();
				   scheduling.passInviteVehicle.applyInfo.getForm().reset();
				   scheduling.passInviteVehicle.clientGoods.getForm().reset();
				   scheduling.passInviteVehicle.auditLogInfo.store.loadData([]);
				   scheduling.passInviteVehicle.vehicleCarInfo.getStore().removeAll();
				   Ext.ux.Toast.msg(scheduling.passInviteVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.refuse.inviteNo1') + vehicleNo + scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.refuse.inviteNo2'));
			   }
			   
//			   重置受理表单内容
			   scheduling.passInviteVehicle.inputPrice.getForm().reset();
		   },
		   //异常message
		   exception: function(response) {
			   scheduling.passInviteVehicle.inputPrice.getEl().unmask();
			   var result = Ext.decode(response.responseText);
		   }
		});	
	},
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		name : 'isSaleDepartmentCompany',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isSaleDepartmentCompany'), // 是否营业部自请车
		xtype : 'combobox',
		labelWidth : 110,
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.yes'),
				'valueCode' : 'Y'
			},		// 是
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.no'),
				'valueCode' : 'N'
			}		// 否
			]
		}),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		allowBlank: false,
		columnWidth : .5
	},
	{
		name : 'carpoolingType',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType'), // 拼车类型
		xtype : 'combobox',
		labelWidth : 70,
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [
			        //269701--2016/03/28--拼车类型追加--精准拼车和精准专车
			     {
					'valueName' : scheduling.inviteVehicle
							.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.accurateCarpool'),
				    'valueCode' : 'ACCURATE_CARPOOL'
				},		// 精准拼车
			    {
					'valueName' : scheduling.inviteVehicle
							.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.accurateLineCarpool'),
				    'valueCode' : 'ACCURATE_LINE_CARPOOL'
				},		// 精准专车
				{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.externalCarpool'),
				'valueCode' : 'EXTERNAL_CARPOOL'
			},		// 外部拼车
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.internalWholecarCarpool'),
		        'valueCode' : 'INTERNAL_WHOLECAR_CARPOOL'
			},		// 内部整车拼车
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.internalWholecarBreakbulkCarpool'),
		        'valueCode' : 'INTERNAL_WHOLECAR_BREAKBULK_CARPOOL'
			},		// 内部整车零担拼车
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.noCarpool'),
		        'valueCode' : 'NO_CARPOOL'
			}		// 无
			]
		}),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		//hidden :true,
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		allowBlank: false,
		columnWidth : .5
	},
	{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.notes.lable'),//'审核结果备注',
		name: 'notes',
		allowBlank:true,
		maxLength: 300,
		maxLengthText: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText'),//'长度超过最大限制!',
		columnWidth:.9
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		
		items: [
		{
			xtype: 'button',	
			text: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.button.return'),//'退回',
			disabled : !scheduling.passInviteVehicle.isPermission('scheduling/returnInviteVehicleIndexButton'),
			hidden : !scheduling.passInviteVehicle.isPermission('scheduling/returnInviteVehicleIndexButton'),
			margin:'0 0 0 100',
			handler: function() {
				var url = 'backInviteInviteApplyApply.action'
				scheduling.passInviteVehicle.inputPrice.backAndPassAndRefuseAjax(url,'back');	
			}
		},
		{
			xtype: 'button',			
			text: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.label.through'),//'通过',
			disabled : !scheduling.passInviteVehicle.isPermission('scheduling/passInviteVehicleIndexButton'),
			hidden : !scheduling.passInviteVehicle.isPermission('scheduling/passInviteVehicleIndexButton'),
			margin:'0 0 0 90',
			handler: function() {	
				if(scheduling.passInviteVehicle.applyInfo.getForm().getValues().usePurpose == '整车约车'){
					if(scheduling.passInviteVehicle.inputPrice.getForm().getValues().isSaleDepartmentCompany==null ||
							scheduling.passInviteVehicle.inputPrice.getForm().getValues().carpoolingType==null){
						//  是否营业部自请车,拼车类型不能为空
						Ext.Msg.alert('提示','有必填项未完成!');
							return;
					}
				}
				var url = 'passInviteInviteApplyApply.action';
				scheduling.passInviteVehicle.inputPrice.backAndPassAndRefuseAjax(url,'pass');
			}
		},{
			xtype: 'button',			
			text: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.button.refuse'),//'拒绝',
			disabled : !scheduling.passInviteVehicle.isPermission('scheduling/refusepassInviteVehicleIndexButton'),
			hidden : !scheduling.passInviteVehicle.isPermission('scheduling/refusepassInviteVehicleIndexButton'),
			margin:'0 0 0 90',
			handler: function() {
				var url = 'refusePassInviteVehicleApply.action';
				scheduling.passInviteVehicle.inputPrice.backAndPassAndRefuseAjax(url,'refuse');
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

 

 
scheduling.passInviteVehicle.inputPrice = Ext.create('Foss.PassInviteVehicleIndex.Form.InputPrice');
scheduling.passInviteVehicle.InputPriceAndTime = Ext.create('Foss.PassInviteVehicleIndex.Form.InputPriceAndTime');
scheduling.passInviteVehicle.vehicleCarInfo = Ext.create('Foss.PassInviteVehicleIndex.Grid.CarInfo');
 
//左边面板车辆信息模块
Ext.define('Foss.PassInviteVehicleIndex.LeftPanel.FormGrid',{
		extend:'Ext.panel.Panel',
		title:scheduling.passInviteVehicle.i18n('foss.scheduling.passborrowvehicle.VehicleInfoPanel.title'),//'车辆信息',
		margin:'0 0 0 0',
		frame:true,
		items:[
				scheduling.passInviteVehicle.CarInfo = Ext.create('Foss.PassInviteVehicleIndex.Form.CarInfo'),
				scheduling.passInviteVehicle.vehicleCarInfo,
				scheduling.passInviteVehicle.InputPriceAndTime
			  ]
		
});


//定义弹出的展示窗口
Ext.define('Foss.PassInviteVehicleIndex.FormGridWindow',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:600,
	bodyCls: 'autoHeight',
	resizable:false,
	formGrid : null,
	getFormGrid: function(){
		if(this.formGrid==null){
			this.formGrid = Ext.create('Foss.PassInviteVehicleIndex.LeftPanel.FormGrid');
		}
		 scheduling.passInviteVehicle.FormGrid = this.formGrid;
		return this.formGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getFormGrid()
				];
		me.callParent([cfg]);
	}
});

scheduling.passInviteVehicle.FormGridWindow = Ext.create('Foss.PassInviteVehicleIndex.FormGridWindow');

//左边面板
Ext.define('Foss.PassInviteVehicleIndex.LeftPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 5 0 0',
		frame:true,
		width: 500,
		items:[
				scheduling.passInviteVehicle.bookingCar = Ext.create('Foss.PassInviteVehicleIndex.Grid.BookingCar'),
				scheduling.passInviteVehicle.Passcar = Ext.create('Foss.PassInviteVehicleIndex.Grid.Passcar'),
				scheduling.passInviteVehicle.inputPrice
		]
});

//==============右面板构建
//右面板-申请信息表单
 Ext.define('Foss.PassInviteVehicleIndex.Form.ApplyInfo', {
	extend: 'Ext.form.Panel',
	title:scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.title'),//'申请信息',
	layout:'column',
	url:'#',	
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.inviteNo'),//'外请车单号',
		name: 'inviteNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
		
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.status'),//'操作状态',
		name: 'status',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'),//'申请时间',
		name: 'applyTime',
		allowBlank:true,
		columnWidth:.5,
		width:120,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.useType'),//'用车类型',
		name: 'useType',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.label.useType'),//'使用类型',
		name: 'usePurpose',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'),//'用车部门',
		name: 'applyOrgName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.predictUseTime'),//'用车时间',
		name: 'predictUseTime',
		allowBlank:true,
		columnWidth:.5,
		width:130,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress'),//'用车地址',
		name: 'useAddress',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.vehicleLengthName'),//'用车车型',
		name: 'vehicleLengthName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyEmpName'),//'申请人姓名',
		name: 'applyEmpName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyEmpOrgName'),//'所在部门',
		name: 'applyOrgName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.telephoneNo'),//'固定电话',
		name: 'telephoneNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{//310248
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyFees'),//'外请车费用',
		name: 'applyFees',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{//310248
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bearFeesDept'),//'费用承担部门',
		name: 'bearFeesDeptName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.mobilephoneNo'),//'手机',
		name: 'mobilephoneNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		xtype: 'container',
		border : false,
		columnWidth : .2,
		html : '&nbsp;'
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.isPassByArrivedDept'),//'是否经过到达部门',
		name: 'isPassByArrivedDept',
		labelWidth:110,
		allowBlank:true,
		columnWidth:.8,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode'),//'到达部门',
		name: 'arrivedDeptName',
		allowBlank:true,
		columnWidth:.8,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedAddress'),//'到达地址',
		name: 'arrivedAddress',
		allowBlank:true,
		columnWidth:.8,
		readOnly:true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

//右面板-客户和货物信息表单
 Ext.define('Foss.PassInviteVehicleIndex.Form.ClientGoods', {
	extend: 'Ext.form.Panel',	
	title:scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.title'),//'客户和货物信息',
	layout:'column',
	url:'#',	
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'),//'货物名称',
		name: 'goodsName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
		
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsPackage'),//'货物包装',
		name: 'goodsPackege',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty'),//'货物件数',
		name: 'goodsQty',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.label.weight'),//'货物重量',
		name: 'weight',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('foss.scheduling.borrowvehicle.label.volume'),//'货物体积',
		name: 'volume',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.other'),//'其他要求',
		name: 'requirment',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isScaneSeeGoods'),//'是否现场看货',
		name: 'isScaneSeeGoods',
		allowBlank:true,
		columnWidth:.5,
		
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isGoodsOver'),//'货物是否可叠加',
		name: 'isGoodsOver',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientName'),//'发货人',
		name: 'clientName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientContactPhone'),//'发货人电话',
		name: 'clientContactPhone',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.loadGoodsTime'),//'装货时间',
		name: 'loadGoodsTime',
		allowBlank:true,
		columnWidth:1,
		readOnly:true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

//右面板-审核受理记录表格
Ext.define('Foss.PassInviteVehicleIndex.Grid.AuditLogInfo', {
	extend: 'Ext.grid.Panel',
	title:scheduling.passInviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.AuditLogInfo'),//'审核&受理记录',
	//表格对象增加一个边框
	frame: true,
	collapsible: true,
	animCollapse: false,
	//hideHeaders:true,
	hideCollapseTool:true,
	//指定表格的高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//表格绑定store
	store: null,
	autoScroll:true,
	columns: [{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.auditEmpName'),//'审核人',
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditEmpName'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.auditTime'),//'审核时间',
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditTime',
		format: 'Y-m-d H:i:s',
		xtype:'datecolumn'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.status'),//'审核结果',
		flex: 1, 
		sortable: true, 
		dataIndex: 'status',
		renderer: function(value){
			return FossDataDictionary.rendererSubmitToDisplay (value,'INVITEVEHICLE_STATUS');
		}
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.notes'),//'备注',
		flex: 1, 
		sortable: true, 
		dataIndex: 'notes'
	},{
		text: scheduling.passInviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginInviteCost'),//'请车价格',
		flex: 1, 
		sortable: true, 
		dataIndex: 'inviteCost'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PassInviteVehicleIndex.Store.AuditLogInfo');
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.PassInviteVehicleIndex.AuditSmsPanal', {
	extend: 'Ext.form.Panel',
	title:scheduling.passInviteVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),//'短信信息',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaultType: 'textfield',
	columnWidth:1,
	defaults: {
		margin:'5 5 5 5',
		labelWidth:85
	},
	items: [{
		xtype :'textareafield',
		name: 'sms',
		columnWidth:1
	}]
});


//右边面板
Ext.define('Foss.PassInviteVehicleIndex.RightPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 0 0 5',
		width: 500,
		items:[
		       scheduling.passInviteVehicle.applyInfo = Ext.create('Foss.PassInviteVehicleIndex.Form.ApplyInfo'),
		       scheduling.passInviteVehicle.clientGoods = Ext.create('Foss.PassInviteVehicleIndex.Form.ClientGoods'),
		       scheduling.passInviteVehicle.auditLogInfo = Ext.create('Foss.PassInviteVehicleIndex.Grid.AuditLogInfo'),
		       scheduling.passInviteVehicle.AuditSmsPanal = Ext.create('Foss.scheduling.PassInviteVehicleIndex.AuditSmsPanal')
		]
		
});

//定义整体列布局
//将约车信息、车辆信息、申请信息、货物信息、审核记录组合显示
Ext.define('Foss.PassInviteVehicleIndex.AcceptBookingCarContainer',{
	  extend: 'Ext.container.Container',
	  layout: 'column',
	  frame: false,
	  cls: 'autoHeight',
	  bodyCls: 'autoHeight',	  
	  lefttPanel: null,
	  getLefttPanel: function(){
	    if(this.lefttPanel==null){
	      this.lefttPanel = Ext.create('Foss.PassInviteVehicleIndex.LeftPanel');
	    }
	    return this.lefttPanel;
	  },
	  rightPanel: null,
	  getRightPanel: function(){
	    if(this.rightPanel==null){
	      this.rightPanel = Ext.create('Foss.PassInviteVehicleIndex.RightPanel');
	    }
	    return this.rightPanel;
	  }, 
	  constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
	      me.items = [
	      me.getLefttPanel(),
	      me.getRightPanel()
	    ]; 
			me.callParent([cfg]);
		}
}); 

Ext.onReady(function(){
		var container = Ext.create('Foss.PassInviteVehicleIndex.AcceptBookingCarContainer');
		Ext.create('Ext.panel.Panel',{
			id: 'T_scheduling-passInviteVehicleIndex_content',
			cls:"panelContent",
			bodyCls:'panelContent-body',
			layout:'auto',
			margin:'0 0 0 0',
			items : [container],
			renderTo: 'T_scheduling-passInviteVehicleIndex-body'
		});
		scheduling.passInviteVehicle.bookingCar.queryBookingCar();
		scheduling.passInviteVehicle.bookingCar.getSelectionModel().selectAll();
		
		
});