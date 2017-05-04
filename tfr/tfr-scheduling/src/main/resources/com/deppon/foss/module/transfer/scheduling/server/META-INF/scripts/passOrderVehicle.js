/********************************************************************************************************************
约车受理页面
1、panel → T_scheduling-passOrderVehicleIndex_content 初始化
+-1.1、Container → Foss.scheduling.passOrderVehicle.PassOrderVehicleContainer 整个页面的container
--+-1.1.1、Panel → Foss.scheduling.passOrderVehicle.LeftPanel   左侧布局panel
------1.1.1.1、Grid → Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryGrid 左上的约车信息(未审核、已受理)
----+-1.1.1.2、TabPanel → Foss.scheduling.passOrderVehicle.VehicleInfoPanel 选择车辆的tab panel 分为(自有车、外请车)
------+-1.1.1.2.1、panel → Foss.scheduling.passOrderVehicle.ownVehiclePanel 自有车
--------1.1.1.2.1.1、Form → Foss.scheduling.passOrderVehicle.ownVehicleInfoForm 查询条件
--------1.1.1.2.1.2、Grid → Foss.scheduling.passOrderVehicle.ownVehicleInfoQueryGrid 查询结果
--------1.1.1.2.2、panel → Foss.scheduling.passOrderVehicle.inviteVehiclePanel 外请车
--------1.1.1.2.2.1、Form → Foss.scheduling.passOrderVehicle.inviteVehicleInfoForm 查询条件
------+-1.1.1.2.2.2、Grid → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid 查询结果
--------1.1.1.2.2.2.1、Store → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.store
--------1.1.1.2.2.2.2、Model → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.model
------+-1.1.1.2.2.2.3、每行的Tip → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid 外请车的详细提示框
--------1.1.1.2.2.2.3.1、Store → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.store
--------1.1.1.2.2.2.3.2、Model → Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.model
------1.1.1.3、FormPanel → Foss.scheduling.passOrderVehicle.VehicleInputForm 受理的提交表单
--+-1.1.2、Panel → Foss.scheduling.passOrderVehicle.RightPanel  右侧布局panel
------1.1.2.1、Form → Foss.scheduling.passOrderVehicle.OrderVehicleApplyDetailForm 约车申请详细信息
------1.1.2.2、Grid → Foss.scheduling.passOrderVehicle.AuditOrderLogGrid 审批日志列表
**********************************************************************************************************************/
//定义一个约车的模型
Ext.define('Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [{
			 // 约车单号
			name: 'orderNo',
			type:'string'
		},{
			 // 状态
			name: 'status',
			type:'string'
		},{
			// 申请时间
			name: 'applyTime', 
			type: 'string',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}
		},{
			// 用车类型
			name: 'orderType', 
			type: 'string',
			convert: function(value) {
				return scheduling.passOrderVehicle.passOrderTypeMap.get(value);
			}
		},{
			// 车型
			name: 'orderVehicleModel', 
			type: 'string'
		},{
			// 用车部门 code
			name:'useVehicleOrgCode',
			type:'string'
		},{
			// 用车部门 名称
			name:'useVehicleOrgName',
			type:'string'
		},{
			// 申请人部门
			name: 'applyOrgName', 
			type: 'string'
		},{
			// 派车车队
			name: 'dispatchTransDept', 
			type: 'string'
		},{
			// 是否尾板车
			name: 'isTailboard', 
			type: 'string',
			convert: function(value) {
				return value == 'Y' ? scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.YES.lable') : scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.NO.lable');
			}
		},{
			// 用车地址
			name: 'useAddress', 
			type: 'string'
		},{
			// 用户姓名  , 申请人员名称
			name: 'applyEmpName', 
			type: 'string'
		},{
			// ab货
			name: 'goodsType', 
			type: 'string'
		},{
			// 货物名称
			name: 'goodsName', 
			type: 'string'
		},{
			// 预计用车时间
			name: 'predictUseTime', 
			type: 'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}
		},
		// 体积
		{name: 'volume', type: 'string'},
		// 重量
		{name: 'weight', type: 'string'},
		// 件数
		{name: 'goodsQty', type: 'string'},
		// 备注
		{name: 'notes', type: 'string'},
		// 派车车队名称
		{name:'dispatchTransDeptName', type:'string'}
	]
});

//定义约车数据源
Ext.define('Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryModel'
});

//创建约车信息表格
Ext.define('Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryGrid', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	frame: true,
	collapsible: true,
    animCollapse: true,
	title:scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.BookingCar.title'),//'约车信息',
	//表格绑定store
	store: null,
	//设置选择模式为复选框选择
	selModel: null,
	height: 250,
	autoScroll:true,
	emptyText: scheduling.passOrderVehicle.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.origOrgName.title'),//'查询结果为空',
	columns: [{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo'),//'约车编号',
		flex: 1, 
		sortable: true, 
		dataIndex: 'orderNo'
	},{
		text: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.status'),//'操作状态',
		flex: 1, 
		sortable: true, 
		dataIndex: 'status',
		dataIndex: 'status',
		renderer:function(value){
			return scheduling.passOrderVehicle.orderVehicle_Status.get(value);
		}
	},{
		text: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType'),//用车类型',
		flex: 1, 
		sortable: true, 
		dataIndex: 'orderType'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{'mode':'SINGLE','showHeaderCheckbox':false});
		me.callParent([cfg]);
	},
	queryCompanyVehicleAndBorrowVehicle: function(ownTruck) {
		var passOrderVehicleVo = {passOrderVehicleVo:{vehicleDriverWithDto:ownTruck}}
		Ext.Ajax.request({
			url:scheduling.realPath('queryCompanyVehicleAndBorrowVehicle.action'),
			jsonData: passOrderVehicleVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passOrderVehicleVo.vehicleDriverWithDtoList;
				if(!list) {
					list = [];
				}
				scheduling.passOrderVehicle.ownVehicleInfoQueryGrid.store.loadData(list);
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				
			}
		});
	},
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
			   text:scheduling.passOrderVehicle.i18n('Foss.scheduling.button.refresh'),//'刷新',
			   columnWidth:.12,
			   handler:function() {
				   scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.queryOrderVehicleApply();
			   }
		   }]
	}],
	listeners: {
			select: function(model, record) {
				scheduling.passOrderVehicle.passOrderVehicleDetailForm.getForm().loadRecord(record);
				var status = scheduling.passOrderVehicle.passOrderVehicleDetailForm.getForm().findField('status').getValue();
				var statusDesc = scheduling.passOrderVehicle.orderVehicle_Status.get(status);
				scheduling.passOrderVehicle.passOrderVehicleDetailForm.getForm().findField('status').setValue(statusDesc);
                var orderNo = record.get('orderNo');
                scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid.queryAuditOrderApplyLog(orderNo);
                if(!record.raw) {
                	record.raw = {};
                }
                var orderType = record.raw.orderType;
                var ifNeedReleaseBillValue = 'N';
                if(!orderType) {
                	orderType = '';
                }
                if('SEND_GOODS' == orderType) {
                	ifNeedReleaseBillValue = 'Y';
                }
                // 控制生成放行任务按钮
                var ifNeedReleaseBill = scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('ifNeedReleaseBill');
                ifNeedReleaseBill.setValue(ifNeedReleaseBillValue);
             }
	},
	// 删除拒绝， 退回的主键id
	removeOrderId: function(orderId) {
		if(!orderId) {
			return false;
		}
		if(!scheduling.passOrderVehicle.paramOrderIdList) {
			return false;
		}
		scheduling.passOrderVehicle.paramOrderIdList = scheduling.passOrderVehicle.paramOrderIdList.replace(orderId +',', '').replace(orderId, '');
	},
	queryOrderVehicleApply: function(removeOrderId) {
		this.removeOrderId(removeOrderId);
		var orderIdList = [];
		if(!Ext.isEmpty(scheduling.passOrderVehicle.paramOrderIdList)) {
			orderIdList = scheduling.passOrderVehicle.paramOrderIdList;
		}
		var passOrderVehicleVo = {
				'passOrderVehicleVo.orderIdList':orderIdList,
				'passOrderVehicleVo.isLoadAll':scheduling.passOrderVehicle.orderVehicleIsLoadAll
			};
		Ext.Ajax.request({
			url:scheduling.realPath('queryPassOrderVehicleApply.action'),
			params: passOrderVehicleVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passOrderVehicleVo.orderVehicleResultList;
				if(!list) {
					list = {};
				}
				scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.store.loadData(list);
				if(removeOrderId) {
					// 拒绝， 退回操作
					// log重置
					scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid.queryAuditOrderApplyLog();
					// 右边form重置
					scheduling.passOrderVehicle.passOrderVehicleDetailForm.getForm().reset();
				}
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
			}
		});
	}
});

//左面板-车辆信息表单
Ext.define('Foss.scheduling.passOrderVehicle.ownVehicleInfoForm', {
	extend: 'Ext.form.Panel',	
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	items: [{
			fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehicleNo'),//'车牌号',
			name: 'vehicleNo',
			xtype : 'commonowntruckselector',
			allowBlank:true,
			columnWidth:.5
		}, {
			fieldLabel: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
			name: 'vehcleLengthCode',
			xtype: 'commonvehicletypeselector',
			columnWidth: .5
		}, {
			xtype: 'combobox',
			mode:'local',
			queryMode: 'local',		
			triggerAction:'all',
			fieldLabel:     scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehicleMotorcadeName'),//'车辆组别',
			name:           'transTeam',
			displayField:   'name',
			valueField:     'code',
			editable : false,
			store: null,
			columnWidth:.5
		}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		
		items: [
		{
			xtype: 'button',			
			text: scheduling.passOrderVehicle.i18n('Foss.scheduling.button.reset'),//'重置',
			margin:'0 0 0 100',
			handler: function() {	
				scheduling.passOrderVehicle.ownVehicleInfoForm.getForm().reset();
			}
		},
		{
			xtype: 'button',	
			text: scheduling.passOrderVehicle.i18n('Foss.scheduling.button.search'),//'查询',
			margin:'0 0 0 190',
			handler: function() {
               scheduling.passOrderVehicle.orderModuleOwnerVehiclePagingBar.moveFirst();
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

/** 车辆信息grid */
//定义一个车辆信息的模型
Ext.define('Foss.scheduling.passOrderVehicle.VehicleInfoModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'vehicleNo',type:'string'},
		{name: 'vehcleLengthCode',type:'string'},
		{name: 'vehicleLengthName',type:'string'},
		{name: 'driverName', type: 'string'},
		{name: 'driverCode', type: 'string'},
		{name: 'driverPhone', type: 'string'},
		{name: 'vehcleGroupName', type: 'string'},
		{name: 'vehcleGroupCode', type: 'string'}
	]
});

//定义约车数据源
Ext.define('Foss.scheduling.passOrderVehicle.VehicleInfoStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passOrderVehicle.VehicleInfoModel',
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义自有车store
Ext.define('Foss.scheduling.passOrderVehicle.orderVehicleInfoQueryGrid.store',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passOrderVehicle.VehicleInfoModel',
	pageSize:10,
	proxy: {
        //以JSON的方式加载数据
      type : 'ajax',
      actionMethods:'POST',
      url: scheduling.realPath('queryOrderVehicleInfoList.action'),
			reader : {
				type : 'json',
				root : 'orderVehicleVo.vehicleDriverWithList',
				totalProperty : 'totalCount',
				successProperty: 'success'
			}
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = scheduling.passOrderVehicle.ownVehicleInfoForm.getForm();
			var vehicleNo = queryParams.findField('vehicleNo').getValue();
			var vehcleLengthCode = queryParams.findField('vehcleLengthCode').getValue();
			var transTeam = queryParams.findField('transTeam').getValue();
			Ext.apply(operation, {
				params : {
				   'orderVehicleVo.vehicleDriverWithDto.vehicleNo' : vehicleNo,
				   'orderVehicleVo.vehicleDriverWithDto.vehcleLengthCode' : vehcleLengthCode,
				   'orderVehicleVo.vehicleDriverWithDto.vehcleGroupCode' : transTeam
				}
			});	
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindResult'));//没有查询到数据!
			}
		}
	}
});

//创建车辆信息表格
Ext.define('Foss.scheduling.passOrderVehicle.ownVehicleInfoQueryGrid', {
	extend: 'Ext.grid.Panel',
	frame: false,
	preventHeader:true,
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	height: 200,
	store: null,
	selModel: null,
	autoScroll:true,
	columns: [{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehicleNo'),//'车牌号',
		flex: 1, 
		sortable: true, 
//		renderer : scheduling.vehicleInfoTipView,
		dataIndex: 'vehicleNo'
	},{
		text: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	},{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehicleMotorcadeName'),//'车辆组别',
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehcleGroupName'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passOrderVehicle.orderVehicleInfoQueryGrid.store');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{'mode':'SINGLE','showHeaderCheckbox':false});
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		scheduling.passOrderVehicle.orderModuleOwnerVehiclePagingBar = me.bbar;
		
		me.callParent([cfg]);
	},
	listeners: {
		select: function(model, record) {
			var vehicleInputForm = scheduling.passOrderVehicle.vehicleInputForm.getForm();
			var setValue = function(name, value) {
				var obj = vehicleInputForm.findField(name);
				obj.setValue(value);
			}
			
//			在选中自有车的车辆时，需去掉外请车已选的车辆
			scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.getSelectionModel().deselectAll(true);
			
			scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverCode').setCombValue('', '');
			scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverPhone').setValue('');
			
			//尝试获取短途排班表中的司机信息
			var vehicleNo = record.data.vehicleNo;
		    Ext.Ajax.request({
				actionMethods:'POST',
				async:true,
				url: scheduling.realPath('queryDriverInfoInShortPlan.action'),
				params:{
					'orderVehicleVo.vehicleNo': vehicleNo
				},
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if(!Ext.isEmpty(result.orderVehicleVo.simpleTruckScheduleDto)){
					  var driverInfoRecord = result.orderVehicleVo.simpleTruckScheduleDto;
					  scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverCode').setCombValue(driverInfoRecord.driverName, driverInfoRecord.driverCode);
					  scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverPhone').setValue(driverInfoRecord.driverPhone);
					}
				},
				//异常message
				exception: function(response) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				}
		   });
		}
	}
});

//重写RadioGroup setValue方法
Ext.override(Ext.form.RadioGroup, {   
    setValue: function(v){   
        if (this.rendered)    
        	   this.items.each(function(item) { 
                item.setValue(item.inputValue == v);   
            });   
        else {   
            for (var k in this.items) {   
                this.items[k].checked = this.items[k].inputValue == v;   
            }   
        }   
    }   
});

//左面板-车辆信息-价格信息表单
Ext.define('Foss.scheduling.passOrderVehicle.VehicleInputForm', {
	extend: 'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.ifNeedReleaseBill'),//'是否生成放行需求',
		xtype: 'radiogroup',
        vertical: true,
		columnWidth:.5,
		labelWidth:120,
		name:'ifNeedReleaseBill',
		allowBlank:true,
		items: [
           { boxLabel: scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.YES.lable'), name: 'ifNeedReleaseBill', inputValue: 'Y' },
           { boxLabel: scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.NO.lable'), name: 'ifNeedReleaseBill', inputValue: 'N', checked: true}
       ]
	},{
		columnWidth: .5,
	    fieldLabel : scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'),//'预计到达时间',
	    editable : false,
		name : 'perdictArriveTime',
		xtype : 'datetimefield_date97',
		format: 'Y-m-d',
		time : true,
		id : 'Foss_scheduling_passOrdervehicle_perdictArriveTime_ID',
		allowBlank: true,
		dateConfig: {
			el : 'Foss_scheduling_passOrdervehicle_perdictArriveTime_ID-inputEl'
		}
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		xtype:'commondriverselector',
		name: 'driverCode',		
		columnWidth:.5,
		allowBlank: true,
		listeners : {
			'change' : function(ths) {
				if (Ext.isEmpty(ths.getRawValue())) {
					ths.setValue(null);
					scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverPhone').setValue(null);
				}
			},
			'select' : function(combo, records, eOpts) {
				var record = records[0].data;
				var empPhone = record.empPhone;
				scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverPhone').setValue(empPhone);
			}
		}
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.driverPhone'),//'联系方式',
		name: 'driverPhone',
		maxLength: 15,
		maxLengthText: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText'),//'长度超过最大限制!',
		allowBlank:true,
		columnWidth:.5
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.approverNotes'),//'审核结果备注',
		name: 'notes',
		maxLength: 300,
		maxLengthText: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText'),//'长度超过最大限制!',
		allowBlank: true,
		columnWidth:.9
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		items: [{
			xtype: 'button',			
			text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.button.return'),//'退回',
			disabled : !scheduling.passOrderVehicle.isPermission('scheduling/doReturnOrderVehicleApplyButton'),
			hidden : !scheduling.passOrderVehicle.isPermission('scheduling/doReturnOrderVehicleApplyButton'),
			margin:'0 0 0 100',
			handler: function() {
				// 必须有备注信息
				var form = scheduling.passOrderVehicle.vehicleInputForm.getForm();
				var orderVehicleApplyQueryGrid = scheduling.passOrderVehicle.orderVehicleApplyQueryGrid;
			    if(!orderVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.hasSelection'));//'请选择您要操作的约车申请信息!');
			 	   	return false;
			    }
			    var dataList = orderVehicleApplyQueryGrid.getSelectionModel().getSelection();
                var orderNo = dataList[0].get('orderNo');
                var orderId = dataList[0].get('id');
                //备注信息
                var values = scheduling.passOrderVehicle.vehicleInputForm.getForm().getValues();
                var notes  = values.notes;
                if(Ext.isEmpty(notes)) {
                	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.msg.notesIsRequired'));//'备注信息不能为空!');
			 	   	return false;
                }
       			var passOrderVehicleVo = {
       				'passOrderVehicleVo.auditOrderApplyEntity.orderNo':orderNo,
       				'passOrderVehicleVo.auditOrderApplyEntity.notes':notes,
       				'passOrderVehicleVo.orderId':orderId
       			};
       			
       			scheduling.passOrderVehicle.vehicleInputForm.getEl().mask(scheduling.passOrderVehicle.i18n('Foss.scheduling.saving'));
       			Ext.Ajax.request({
       				url:scheduling.realPath('doReturnOrderVehicleApply.action'),
       				params: passOrderVehicleVo,
       				success:function(response){
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
       					
       					Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.vehicleInputForm.orderNo') + orderNo);
       					var result = Ext.decode(response.responseText);
       					scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.queryOrderVehicleApply(orderId);
       				},
       				exception: function(response) {
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
					    var result = Ext.decode(response.responseText);
					    Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				    }
       			});
			}
		},{
			xtype: 'button',			
			text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.through'),//'通过',
			disabled : !scheduling.passOrderVehicle.isPermission('scheduling/doAcceptedOrderVehicleApplyButton'),
			hidden : !scheduling.passOrderVehicle.isPermission('scheduling/doAcceptedOrderVehicleApplyButton'),
			margin:'0 0 0 90',
			handler: function() {
				var form = scheduling.passOrderVehicle.vehicleInputForm.getForm();
				// 约车申请grid
				var orderVehicleApplyQueryGrid = scheduling.passOrderVehicle.orderVehicleApplyQueryGrid;
			    if(!orderVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.hasSelection'));
			 	   	return false;
			    }
			    // 车辆grid
				var ownVehicleInfoQueryGrid = scheduling.passOrderVehicle.ownVehicleInfoQueryGrid;
				var inviteVehicleInfoQueryGrid = scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid;
				var vehicleNo = "";
			    if(!ownVehicleInfoQueryGrid.getSelectionModel().hasSelection() && !inviteVehicleInfoQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.hasSelection.car'));//请选择车辆信息!
			 	   	return false;
			    }else{
//			    	获取车牌号
			    	if(ownVehicleInfoQueryGrid.getSelectionModel().hasSelection()){
			    		vehicleNo = ownVehicleInfoQueryGrid.getSelectionModel().getSelection()[0].get('vehicleNo');
			    	}
				    if(inviteVehicleInfoQueryGrid.getSelectionModel().hasSelection()){
			    		vehicleNo = inviteVehicleInfoQueryGrid.getSelectionModel().getSelection()[0].get('vehicleNo');
			    	}
			    }
			    
				if(!form.isValid()) {
				
					return false;
				}
				
				var recordBookingCar = orderVehicleApplyQueryGrid.getSelectionModel().getSelection();
				//只有未审核状态的约车记录可以执行受理业务
				if(recordBookingCar[0].data.status != 'UNAPPROVED'){
					Ext.Msg.alert(scheduling.passOrderVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.statu.unapproved'));
					
					return false;
				}
				
			    // 输入表单信息
			    var values = scheduling.passOrderVehicle.vehicleInputForm.getForm().getValues();
			    if(Ext.isEmpty(values.driverCode)) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.driverCode.not.null'));//'司机姓名不能为空!');
			 	   	return false;
			    }
			    if(Ext.isEmpty(values.driverPhone)) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.driverPhone.not.null'));//'司机联系方式不能为空!');
			 	   	return false;
			    }
			    if(Ext.isEmpty(values.perdictArriveTime)) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.perdictArriveTime.not.null'));//'请选择预计到达时间!');
			 	   	return false;
			    }
			    var nowDate = new Date();
				var perdictArriveTimeDate = Ext.Date.parse(values.perdictArriveTime, "Y-m-d H:i:s", true);
				var result = nowDate.getTime() >= perdictArriveTimeDate.getTime();
				if(result) {
					Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.perdictArriveTimeDate.not.null'));//'预计到达时间必须大于当前时间!');
					return false;
				}
			    // 约车申请
			    var orderVehicleApplyQueryGridDataList = orderVehicleApplyQueryGrid.getSelectionModel().getSelection();
			    var orderVehicleApplyRecord = orderVehicleApplyQueryGridDataList[0];
			    
			    // 约车单号
                var orderNo = orderVehicleApplyRecord.get('orderNo');
				// 预计到达时间
				var perdictArriveTime = values.perdictArriveTime;
				// 是否生成放行任务
				var ifNeedReleaseBill = values.ifNeedReleaseBill;
			
                // 司机姓名
                var driverName = scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverCode').getRawValue();
                // 司机联系方式
                var driverPhone = values.driverPhone; 
                // 司机编号
                var driverCode = values.driverCode;
                // 约车申请主键id
                var orderId = orderVehicleApplyRecord.get('id');
                // 备注信息
                var notes = values.notes;
                
                var sms=orderVehicleApplyRecord.get('orderType') + '：' + orderVehicleApplyRecord.get('useVehicleOrgName')
                + '，'+scheduling.passOrderVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo')+'：'
                + orderVehicleApplyRecord.get('weight') + scheduling.passOrderVehicle.i18n('foss.scheduling.forecastQuantity.weightUnit') 
                + '/' + orderVehicleApplyRecord.get('volume') + scheduling.passOrderVehicle.i18n('foss.scheduling.forecastQuantity.volumeUnit')
                + '，'+scheduling.passOrderVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime')+'：' 
                +perdictArriveTime
                + '，'+scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo')+'：' 
                + orderNo;
                
                //如果有备注则添加备注
                if(notes){
                	sms+='\n'+scheduling.passOrderVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable')+'：'+ notes;
                }
                
                scheduling.passOrderVehicle.auditOrderSmsPanal.getForm().findField("sms").setValue(sms);
                
       			var passOrderVehicleVo = {
       				'passOrderVehicleVo.orderId':orderId,
       				 // 约车编号
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.orderNo': orderNo,
       				// 预计到达时间
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.perdictArriveTime': Ext.Date.parse(perdictArriveTime, "Y-m-d H:i:s"),
       				// 是否生成放行任务
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.ifNeedReleaseBill': ifNeedReleaseBill,
       				// 车牌号
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.vehicleNo': vehicleNo,
       				// 司机姓名
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.driverName': driverName,
       				// 备注信息
       				'passOrderVehicleVo.passOrderApplyDto.notes':notes,
       				//司机联系方式
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.driverPhone': driverPhone,
       				//司机编号
       				'passOrderVehicleVo.passOrderApplyDto.passOrderApplyEntity.driverCode': driverCode
       			};
       			
       			scheduling.passOrderVehicle.vehicleInputForm.getEl().mask(scheduling.passOrderVehicle.i18n('Foss.scheduling.saving'));
       			Ext.Ajax.request({
       				url:scheduling.realPath('doAcceptedOrderVehicleApply.action'),
       				params: passOrderVehicleVo,
       				success:function(response){
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
       					var result = Ext.decode(response.responseText);
       					
       					Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.vehicleInputForm.orderNo') + orderNo);
       					
       					scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.queryOrderVehicleApply();
       					scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid.queryAuditOrderApplyLog(orderNo);
       				},
       				exception: function(response) {
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
       				    var result = Ext.decode(response.responseText);
					    Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				    }
       			});

			}
		},{
			xtype: 'button',		
			text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.button.refuse'),//'拒绝',
			disabled : !scheduling.passOrderVehicle.isPermission('scheduling/doDismissOrderVehicleApplyButton'),
			hidden : !scheduling.passOrderVehicle.isPermission('scheduling/doDismissOrderVehicleApplyButton'),
			margin:'0 0 0 90',
			handler: function() {
				var form = scheduling.passOrderVehicle.vehicleInputForm.getForm();
				// 拒绝必须有备注信息
				// 必须有备注信息
				var orderVehicleApplyQueryGrid = scheduling.passOrderVehicle.orderVehicleApplyQueryGrid;
			    if(!orderVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.hasSelection'));//'请选择您要操作的约车申请信息!');
			 	   	return false;
			    }
			    var dataList = orderVehicleApplyQueryGrid.getSelectionModel().getSelection();
                var orderNo = dataList[0].get('orderNo');
                var orderId = dataList[0].get('id');
                //备注信息
                var values = scheduling.passOrderVehicle.vehicleInputForm.getForm().getValues();
                var notes  = values.notes;
                if(Ext.isEmpty(notes)) {
                	Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.msg.notesIsRequired'));//'备注信息不能为空!');
			 	   	return false;
                }
       			var passOrderVehicleVo = {
       				'passOrderVehicleVo.auditOrderApplyEntity.orderNo':orderNo,
       				'passOrderVehicleVo.auditOrderApplyEntity.notes':notes,
       				'passOrderVehicleVo.orderId':orderId
       			};
       			scheduling.passOrderVehicle.vehicleInputForm.getEl().mask(scheduling.passOrderVehicle.i18n('Foss.scheduling.saving'));
       			Ext.Ajax.request({
       				url:scheduling.realPath('doDismissOrderVehicleApply.action'),
       				params: passOrderVehicleVo,
       				success:function(response){
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
       					Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.vehicleInputForm.orderNo') + orderNo);
       					var result = Ext.decode(response.responseText);
       					scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.queryOrderVehicleApply(orderId);
       				},
       				exception: function(response) {
       					scheduling.passOrderVehicle.vehicleInputForm.getEl().unmask();
					    var result = Ext.decode(response.responseText);
					    Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
				    }
       			});		
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

//左边面板自有车辆信息模块
Ext.define('Foss.scheduling.passOrderVehicle.ownVehiclePanel',{
		extend:'Ext.panel.Panel',
		margin:'0 0 0 0',
		frame:true,
		constructor: function(config){
			var ownVehicleInfoForm = Ext.create('Foss.scheduling.passOrderVehicle.ownVehicleInfoForm');
			var ownVehicleInfoQueryGrid = Ext.create('Foss.scheduling.passOrderVehicle.ownVehicleInfoQueryGrid');
			scheduling.passOrderVehicle.ownVehicleInfoForm = ownVehicleInfoForm;
			scheduling.passOrderVehicle.ownVehicleInfoQueryGrid = ownVehicleInfoQueryGrid;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      ownVehicleInfoForm,
		      ownVehicleInfoQueryGrid
		    ]; 
			me.callParent([cfg]);
		}
});

//左边面板
Ext.define('Foss.scheduling.passOrderVehicle.LeftPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 5 0 0',
		columnWidth:.5,
		frame:true,
		constructor: function(config){
			var orderVehicleApplyQueryGrid = Ext.create('Foss.scheduling.passOrderVehicle.OrderVehicleApplyQueryGrid');
			var vehicleInfoPanel = Ext.create('Foss.scheduling.passOrderVehicle.VehicleInfoPanel');
			var vehicleInputForm = Ext.create('Foss.scheduling.passOrderVehicle.VehicleInputForm');
			scheduling.passOrderVehicle.orderVehicleApplyQueryGrid = orderVehicleApplyQueryGrid;
			scheduling.passOrderVehicle.vehicleInfoPanel = vehicleInfoPanel;
			scheduling.passOrderVehicle.vehicleInputForm = vehicleInputForm;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      orderVehicleApplyQueryGrid,
		      vehicleInfoPanel,
		      vehicleInputForm
		    ]; 
			me.callParent([cfg]);
		}
});

//车辆信息的TAB (自有车、外请车)
Ext.define('Foss.scheduling.passOrderVehicle.VehicleInfoPanel',{
	extend:'Ext.tab.Panel',
	cls:"innerTabPanel",
	bodyCls:"overrideChildLeft",
	activeTab:0,
	autoScroll:false,
	frame: false,
	constructor: function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
		title: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.VehicleInfo.have'),//'自有车',
		tabConfig:{width:100},
		frame:false,
		itemId: 'p1',
		items: Ext.create('Foss.scheduling.passOrderVehicle.ownVehiclePanel',{id:'Foss.scheduling.passOrderVehicle.ownVehiclePanel.id'})
		 },{
		title: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.VehicleInfo.not.have'),//'外请车',
		itemId: 'p2',
		frame:false,
		tabConfig:{width:100},
		items:  Ext.create('Foss.scheduling.passOrderVehicle.inviteVehiclePanel',{id:'Foss.scheduling.passOrderVehicle.inviteVehiclePanel.id'}) 
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoForm', {
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
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.forecastQuantity.forecastQuantity.vehicleNo'),//'车牌号',
		name: 'vehicleNo',
		allowBlank:true,
		columnWidth:.5
		
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		name: 'vehicleLengthCode',
		allowBlank:true,
		xtype:'commonvehicletypeselector',
		columnWidth:.5
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		name: 'driverName',
		allowBlank:true,
		xtype:'commonleaseddriverselector',
		columnWidth:.5,
		listeners: {
			select: function(combo, record, index) {
				var driverPhone = record[0].data.driverPhone;
				var vehicleNo = record[0].data.vehicleNo;
				scheduling.passOrderVehicle.inviteVehicleInfoForm.getForm().findField('driverPhone').setValue(driverPhone);
				scheduling.passOrderVehicle.inviteVehicleInfoForm.getForm().findField('vehicleNo').setValue(vehicleNo);
			}
		}
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.driverPhone'),//'司机手机',
		name: 'driverPhone',
		allowBlank:true,
		columnWidth:.5
	},{
		fieldLabel : scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isOpenVehicle.is'),//'是否开蓬',
		allowBlank : true,
		name: 'isOpenVehicle',
		columnWidth:.5,
		xtype : 'combo',
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		store : [['ALL', scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all')], ['Y', scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.YES.lable')], ['N', scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.NO.lable')]],
		value : 'ALL'
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		
		items: [
		{
			xtype: 'button',			
			text: scheduling.passOrderVehicle.i18n('Foss.scheduling.button.reset'),//'重置',
			margin:'0 0 0 100',
			handler: function() {	
				scheduling.passOrderVehicle.inviteVehicleInfoForm.getForm().reset();
			}
		},
		{
			xtype: 'button',			
			text: scheduling.passOrderVehicle.i18n('Foss.scheduling.button.search'),//'查询',
			margin:'0 0 0 190',
			handler: function() {	
				scheduling.passOrderVehicle.orderModuleInviteVehiclePagingBar.moveFirst();
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

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.model', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'vehicleNo',type:'string'},
		{name: 'status',type:'string',
			convert:function(value){
				if(value=='USING'){
					return scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.using'); //已使用
				}else{
					return scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused'); //未使用
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
Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.store',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.model',
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

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid',{
	extend: 'Ext.grid.Panel',
	title: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.title'), //外请车详细信息
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:210,
	columns: [{
			header: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号
			flex: 1,
			sortable: true,
			dataIndex: 'vehicleNo'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.status'), //使用状态
			flex: 1,
			sortable: true,
			dataIndex: 'status'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.weight'), //重量(吨)
			flex: 1,
			sortable: true,
			dataIndex: 'weight'
		},{
			header: scheduling.passOrderVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsVolumeTotal'),//'体积(方)',
			flex: 1,
			sortable: true,
			dataIndex: 'volume'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.goodsName'),//'所装货物',
			flex: 1,
			sortable: true,
			dataIndex: 'goodsName'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.predictUseTime'),//'用车时间',
			flex: 1,
			sortable: true,
			dataIndex: 'predictUseTime'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.applyEmpName'),//'申请人',
			flex: 1,
			sortable: true,
			dataIndex: 'applyEmpName'
		},{
			header: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.acceptEmpName'),//'受理人',
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
					Ext.Msg.alert(scheduling.passOrderVehicle.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),result.message);
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
		me.store = Ext.create('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid.store');
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.model', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'vehicleNo',type:'string'},
		{name: 'vehicleLengthName',type:'string'},
		{name: 'vehcleLengthCode',type:'string'},
		{name: 'driverName', type: 'string'},
		{name: 'driverCode', type: 'string'},
		{name: 'driverPhone', type: 'string'},
		{name: 'driverIdCard', type: 'string'},
		{name: 'isOpenVehicle', type: 'string',
			convert:function(value){
				if(value=='Y'){
					return scheduling.passOrderVehicle.i18n('Foss.scheduling.common.yes'); //是
				}else if(value=='N'){
					return scheduling.passOrderVehicle.i18n('Foss.scheduling.common.no'); //否
				}else{
					return scheduling.passOrderVehicle.i18n('Foss.scheduling.common.all'); //全部
				}
			}},
		{name: 'useStaus', type: 'string'},
		{name: 'bookingDepart', type: 'string'},
		{name: 'goods', type: 'string'},
		{name: 'useTime', type: 'string'},
		{name: 'aplyer', type: 'string'},
		{name: 'assignees', type: 'string'},
		{name: 'vehicleScrapDate', type: 'string'}
		
	]
});

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.store',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.model',
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
			var queryParams = scheduling.passOrderVehicle.inviteVehicleInfoForm.getForm();
			var vehicleNo = queryParams.findField('vehicleNo').getValue();
			var driverName = queryParams.findField('driverName').getValue();
			var driverPhone = queryParams.findField('driverPhone').getValue();
			var isOpenVehicle = queryParams.findField('isOpenVehicle').getValue();
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
				 Ext.ux.Toast.msg(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), scheduling.passOrderVehicle.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindResult'));
			}
		}
	}
});

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid', {
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
	columns: [{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehicleNo'),//'车牌号',
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
		tipBodyElement:'Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.tipGrid'
		//自动填列
	},{
		text: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	},{
		text: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverName'
	},{
		text: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.driverPhone'),//'司机手机',
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverPhone'
	},{
		text: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isOpenVehicle.is'),//'是否开蓬',
		flex: 1, 
		sortable: true, 
		dataIndex: 'isOpenVehicle'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid.store');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'SINGLE'});
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		scheduling.passOrderVehicle.orderModuleInviteVehiclePagingBar = me.bbar;
		
		me.callParent([cfg]);
	},
	listeners: {
		select: function(model, record) {
//			在选中外请车的车辆时，需去掉自有车已选的车辆
			scheduling.passOrderVehicle.ownVehicleInfoQueryGrid.getSelectionModel().deselectAll(true);
			//如果司机编号不为空，则需要设置下方表单中的司机姓名下拉框公共选择器
			if(!Ext.isEmpty(record.data.driverCode)){
			  scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverCode').setCombValue(record.data.driverName, record.data.driverCode);
			  scheduling.passOrderVehicle.vehicleInputForm.getForm().findField('driverPhone').setValue(record.data.driverPhone);
			}
		}
	}
});

Ext.define('Foss.scheduling.passOrderVehicle.inviteVehiclePanel',{
	extend:'Ext.panel.Panel',
	margin:'0 0 0 0',
	frame:true,
	items:[
		scheduling.passOrderVehicle.inviteVehicleInfoForm = Ext.create('Foss.scheduling.passOrderVehicle.inviteVehicleInfoForm'),
		scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid = Ext.create('Foss.scheduling.passOrderVehicle.inviteVehicleInfoQueryGrid')					
	]
});

//==============右面板构建
//右面板-申请信息表单
 Ext.define('Foss.scheduling.passOrderVehicle.OrderVehicleApplyDetailForm', {
	extend: 'Ext.form.Panel',
	title:scheduling.passOrderVehicle.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyDetailForm.title'),//'申请信息',
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
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo'),//'约车编号',
		name: 'orderNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
		
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.status'),//'操作状态',
		name: 'status',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'),//'申请时间',
		name: 'applyTime',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderType'),//'预约类型',
		name: 'orderType',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
		name: 'orderVehicleModel',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'),//'用车部门',
		name: 'useVehicleOrgName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.dispatchTransDept'),//'派车车队',
		name: 'dispatchTransDeptName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.isTailboard'),//'是否尾板车',
		name: 'isTailboard',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress'),//'用车地址',
		name: 'useAddress',
		allowBlank:true,
		columnWidth:.99,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.applyEmpName'),//'用户姓名',
		name: 'applyEmpName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsType'),//'A货B货',
		name: 'goodsType',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'),//'货物名称',
		name: 'goodsName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.predictUseTime'),//'用车时间',
		name: 'predictUseTime',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.volume'),//'体积',
		name: 'volume',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal'),//'重量',
		name: 'weight',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsQty'),//'件数',
		name: 'goodsQty',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.passOrderVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable'),//'备注',
		name: 'notes',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

//右面板-审核受理记录表格
//定义一个审核受理记录的模型
 Ext.define('Foss.scheduling.passOrderVehicle.AuditOrderLogModel', {
 	extend: 'Ext.data.Model',
 	//定义字段
 	fields: [{
 	    	// 约车编号
			name:'orderNo',  	         
			type:'string'
		},{
			// 受理时间
			name:'auditTime',
			type:'date',
			convert: dateConvert
		},{
			// 序号
			name:'auditNo',
			type:'string'
		},{
			// 备注
			name:'notes',
			type:'string'
		},{
			// 审核车队名称
			name:'auditOrgName',
			type:'string'
		},{
			// 审核车队编码
			name:'auditOrgCode',
			type:'string'
		},{
			// 审核人员名称
			name:'auditEmpName',
			type:'string'
		},{
			// 审核人员编码
			name:'auditEmpCode',
			type:'string'
		},{
			name:'id',
			type:'string'
		},{
			name:'perdictArriveTime',
			type:'date',
			convert: dateConvert	
		},{
			name:'ifNeedReleaseBill',
			type:'string',
			convert:function(value) {
				return value == 'Y' ? scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.YES.lable'):scheduling.passOrderVehicle.i18n('foss.shortdepartureplan.grid.plansearchresult.isIssue.NO.lable');
			}
		}
 	]
 });
 
//定义审核受理记录数据源
 Ext.define('Foss.scheduling.passOrderVehicle.AuditOrderLogStore',{
 	extend: 'Ext.data.Store',
 	//绑定一个模型
 	model: 'Foss.scheduling.passOrderVehicle.AuditOrderLogModel'
 });
 
Ext.define('Foss.scheduling.passOrderVehicle.AuditOrderLogGrid', {
	extend: 'Ext.grid.Panel',
	title:scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.AuditLogInfo'),//'审核&受理记录',
	//表格对象增加一个边框
	frame: true,
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	//指定表格的高度
	//表格绑定store
	store: null,
	height: 300,
	autoScroll:true,
	emptyText: scheduling.passOrderVehicle.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.origOrgName.title'),//'查询结果为空',	
	columnWidth:1,
	columns: [{
		text: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.acceptEmpName'),//'受理人',
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditEmpName'
	},{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.auditTime'),//'受理时间',
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s', 
		width:140
	},{
		text: scheduling.passOrderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'),//'预计到达时间',
		flex: 1, 
		sortable: true, 
		dataIndex: 'perdictArriveTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s', 
		width:140
	},{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.needReleaseBill'),//'生成放行需求',
		flex: 1,
		dataIndex: 'ifNeedReleaseBill',
		sortable: true
	},{
		text: scheduling.passOrderVehicle.i18n('foss.scheduling.borrowvehicle.label.approverNotes'),//'审核结果备注',
		flex: 1, 
		sortable: true, 
		dataIndex: 'notes'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passOrderVehicle.AuditOrderLogStore');
		me.callParent([cfg]);
	},
	//约车审核log
	queryAuditOrderApplyLog: function(orderNo) {
		if(!orderNo) {
			scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid.store.loadData([]);
		}
		var passOrderVehicleVo = {
				'passOrderVehicleVo.orderVehicleEntity.orderNo':orderNo
			};
		Ext.Ajax.request({
			url:scheduling.realPath('queryAuditOrderApplyLog.action'),
			params: passOrderVehicleVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passOrderVehicleVo.passOrderApplyDto.auditOrderApplyDtoList;
				if(!list) {
					list = {};
				}
				scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid.store.loadData(list);
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
			}
		});
	}
});

Ext.define('Foss.scheduling.passOrderVehicle.AuditOrderSmsPanal', {
	extend: 'Ext.form.Panel',
	title:scheduling.passOrderVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),//'短信信息',
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
Ext.define('Foss.scheduling.passOrderVehicle.RightPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 0 0 5',
		columnWidth:.5,
		constructor: function(config){
			var detailForm = Ext.create('Foss.scheduling.passOrderVehicle.OrderVehicleApplyDetailForm');
			var auditOrderLogGrid = Ext.create('Foss.scheduling.passOrderVehicle.AuditOrderLogGrid');
			var auditOrderSmsPanal = Ext.create('Foss.scheduling.passOrderVehicle.AuditOrderSmsPanal');
			scheduling.passOrderVehicle.passOrderVehicleAuditOrderLogGrid = auditOrderLogGrid;
			scheduling.passOrderVehicle.passOrderVehicleDetailForm = detailForm;
			scheduling.passOrderVehicle.auditOrderSmsPanal = auditOrderSmsPanal;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      detailForm,
		      auditOrderLogGrid,
		      auditOrderSmsPanal
		    ]; 
			me.callParent([cfg]);
		}
});

//定义整体列布局
//将约车信息、车辆信息、申请信息、货物信息、审核记录组合显示
Ext.define('Foss.scheduling.passOrderVehicle.PassOrderVehicleContainer',{
	  extend: 'Ext.container.Container',
	  layout: 'column',
	  frame: false,
	  cls: 'autoHeight',
	  bodyCls: 'autoHeight',	  
	  lefttPanel: null,
	  getLefttPanel: function(){
	    if(this.lefttPanel==null){
	      this.lefttPanel = Ext.create('Foss.scheduling.passOrderVehicle.LeftPanel');
	    }
	    return this.lefttPanel;
	  },
	  rightPanel: null,
	  getRightPanel: function(){
	    if(this.rightPanel==null){
	      this.rightPanel = Ext.create('Foss.scheduling.passOrderVehicle.RightPanel');
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

scheduling.passOrderVehicle.convertMap = function (list) {
	var map = new Ext.util.HashMap();
	if(!list || list.length == 0){
		return map;
	}
	for(var index in list) {
		map.add(list[index].valueCode, list[index].valueName);
	}
	return map;
}

Ext.onReady(function() {
	var container = Ext.create('Foss.scheduling.passOrderVehicle.PassOrderVehicleContainer');
	Ext.create('Ext.panel.Panel',{
	id: 'T_scheduling-passOrderVehicleIndex_content',
	cls:"panelContent",
	bodyCls:'panelContent-body',
	layout:'auto',
	margin:'0 0 0 0',
	items : [container],
	renderTo: 'T_scheduling-passOrderVehicleIndex-body'
	});
	
	// 加载数据
	scheduling.passOrderVehicle.orderVehicleApplyQueryGrid.queryOrderVehicleApply();
	//通过当前部门获取其下的车队列表
	var dispatchTransDept = FossUserContext.getCurrentDept().code;
    var passOrderVehicleVo = {
		'passOrderVehicleVo.transDepartment': dispatchTransDept
	};
	Ext.Ajax.request({
		url:scheduling.realPath('queryTransTeamList.action'),
		params: passOrderVehicleVo,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var list = result.passOrderVehicleVo.transTeamList;
			if(!list) {
				list = [];
			}
			var json= {
					fields:['name','code'],
				    data : list
				};
			var store = Ext.create('Ext.data.Store', json);
			var transTeam = scheduling.passOrderVehicle.ownVehicleInfoForm.getForm().findField('transTeam');
			transTeam.store = store;
		},
		exception: function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.passOrderVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
		}
	});

    var orderType = FossDataDictionary.getDataByTermsCode('ORDERVEHICLE_ORDERTYPE');
	var orderVehicle_Status = FossDataDictionary.getDataByTermsCode('ORDERVEHICLE_STATUS');
	scheduling.passOrderVehicle.passOrderTypeMap = scheduling.passOrderVehicle.convertMap(orderType);
	scheduling.passOrderVehicle.orderVehicle_Status = scheduling.passOrderVehicle.convertMap(orderVehicle_Status);
});