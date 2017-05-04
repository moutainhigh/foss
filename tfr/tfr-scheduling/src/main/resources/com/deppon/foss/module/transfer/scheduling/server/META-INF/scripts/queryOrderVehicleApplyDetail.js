/**
 * 查询约车申请明细
 */
//model 用于初始化数据绑定
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyDetailFormModel',{
	extend:'Ext.data.Model',
	fields:[{
			// 约车编号
			name:'orderNo',
			type:'string'
		},{
			// 申请时间
			name:'applyTime',
			type:'date',
			convert:function(value) {
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);			
			}
		},{
			// 预约类型
			name:'orderType',
			convert:function(value) {
				return scheduling.orderVehicle.orderTypeValueMap.get(value);
			},
			type:'string'
		},{
			// 车型
			name:'orderVehicleModel',
			type:'string'
		},{
			// 派车车队
			name:'dispatchTransDept',
			type:'string'
		},{
			// 用车地址
			name:'useAddress',
			type:'string'
		},{
			// a b货
			name:'goodsType',
			type:'string'
		},{
			// 是否尾板车
			name:'isTailboard',
			type:'string',
			convert:function(value) {
				return value && value == 'Y' ? scheduling.orderVehicle.i18n('Foss.scheduling.common.yes') : scheduling.orderVehicle.i18n('Foss.scheduling.common.no');
			}
		},{
			// 预计用车时间
			name:'predictUseTime',
			type:'date',
			convert:function(value) {
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);			
			}
		},{
			// 货物名称
			name:'goodsName',
			type:'string'
		},{
			// 货物重量
			name:'weight',
			type:'string'
		},{
			// 货物体积 
			name:'volume',
			type:'string'
		},{
			//货物件数
			name:'goodsQty',
			type:'string'
		},{
			// 备注
			name:'notes',
			type:'string'
		},{
			// 状态
			name:'status',
			type:'string',
			convert:function(value) {
				return scheduling.orderVehicle.statusValueMap.get(value);
			}
		},{
			// 用车部门 名称
			name:'useVehicleOrgName',
			type:'string'
		},{
			// 申请网点编码
			name:'applyOrgCode',
			type:'string'
		},{
			// 申请网点名称 
			name:'applyOrgName',
			type:'string'
		},{
			// 申请网点编码
			name:'applyOrgCode',
			type:'string'
		},{
			//  申请人员名称
			name:'applyEmpName',
			type:'string'
		},{
			// 申请人员编码 
			name:'applyEmpCode',
			type:'string'
		},{
			// 主键id
			name:'id',
			type:'string'
		},{
			// 电话
			name:'telephoneNo',
			type:'string'
		},{
			// 移动电话
			name:'mobilephoneNo',
			type:'string'
		},{
			//订单号
			name:'wayBillNo',
			type:'string'
		},{
			name:'dispatchTransDeptName',
			type:'string'
		}]
});

// 约车明细window
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleDetailWindow',{
	extend:'Ext.window.Window',
	title:scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.title'),//'约车明细查询',
	width:700,
	resizable:false,
	baseInfo:null,
	ordertruckInfo:null,
	goodsInfo: null,
	carInfo:null,
	buttonArea: null,
	operateForm:null,
	acceptsGrid:null,
	modal:true,
	closable: true,
	closeAction: 'hide',
	orderVehicleId:null,
	bindData:function(record) {
		this.orderVehicleId = record.get('id');
	},
	//top
	createOrdertruckBillBaseInfoPanel: function() {
		var tempType = this.baseInfo; 
		if(tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.container.Container',{
			defaultType: 'textfield',
			width:655,
			layout:'column',
			defaults:{
				labelWidth: 60,
				margin: '5 10 5 10'
			},
			items:[{
				xtype: 'container',
			    html:'&nbsp',
			    columnWidth:.02
			},{
				name: 'orderNo',
				fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderNo'),//'约车单号',
				columnWidth:.3
			},{
				name:'status',
				fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.status'),//'操作状态',
				columnWidth:.3
			},{
				name:'applyTime',
				fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'),//'申请时间',
				columnWidth:.35
			}]
		})
		this.baseInfo = tempType;
		return this.baseInfo;
	},
	//约车信息
	createOrdertruckInfoPanel: function() {
		var tempType = this.ordertruckInfo; 
		if(tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			width:655,
			title:scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.BookingCar.title'),//'约车信息',
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 85,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'orderType',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderType'),//'预约类型', 
			    	columnWidth: .5
			    },{ 
			    	name:'wayBillNo',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.wayBillNo'),//'订单号', 
			    	columnWidth: .5
			    },{ 
			    	name:'orderVehicleModel',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.label.vehcleLengthCode'),//'车型', 
			    	columnWidth: .5
			    },{ 
			    	name:'useVehicleOrgName',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'),//'用车部门', 
			    	columnWidth: .5
			    },{ 
			    	name:'useAddress',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress'),//'用车地址', 
			    	columnWidth: 1
			    },{
			    	name:'dispatchTransDeptName',
					fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.dispatchTransDept'),//'派车车队', 
			        columnWidth: .5
				},{
					name:'applyEmpName',
					fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyEmpName'),//'申请人姓名', 
			        columnWidth: .5
				},{
					name:'telephoneNo',
					fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.telephoneNo'),//'固定电话', 
			        columnWidth: .5
				},{
					name:'mobilephoneNo',
					fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.mobilephoneNo'),//'手机', 
			        columnWidth: .5
				},{
					name:'predictUseTime',
					fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.predictUseTime.u'),//'预计用车时间', 
			        columnWidth: .5
				},{
					name:'isTailboard',
					fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.isTailboard'),//'是否尾板车', 
			        columnWidth: .5
				}]
			}]
		})
		this.ordertruckInfo = tempType;
		return this.ordertruckInfo;
	},
	//货物信息
	createGoodsInfoPanel:function() {
		var tempType = this.goodsInfo; 
		if(tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo'),//'货物信息',
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 85,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'goodsType',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsType'),//'A货B货', 
			    	columnWidth: .5
			    },{ 
			    	name:'weight',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.weight'),//'重量(KG)', 
			    	columnWidth: .5
			    },{ 
			    	name:'goodsName',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'),//'货物名称', 
			    	columnWidth: .5
			    },{ 
			    	name:'volume',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsVolumeTotal'),//'体积(方)', 
			    	columnWidth: .5
			    },{ 
			    	name:'goodsQty',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsQty'),//'件数', 
			    	columnWidth: .5
			    },{
			    	name:'notes',
					fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.notes'),//'备注', 
			        columnWidth: .5
				}]
			}]
		})
		this.goodsInfo = tempType;
		return this.goodsInfo;
	},
	//匹配车辆信息
	createCarInfoPanel: function() {
		var tempType = this.carInfo; 
		if(tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.title'),//'匹配车辆信息',
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 85,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'vehicleNo',
			    	fieldLabel: scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.vehicleNo'),//'安排车牌号', 
			    	columnWidth: .5
			    },{ 
			    	name:'orderVehicleModel_1',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.adviseWorkNumber.vehicleType'),//'车辆型号', 
			    	columnWidth: .5
			    },{ 
			    	name:'driverName',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.driverName'),//'驾驶人姓名', 
			    	columnWidth: .5
			    },{ 
			    	name:'driverPhone',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.driverPhone'),//'驾驶人电话', 
			    	columnWidth: .5
			    },{
			    	name:'driverGroup',
			    	fieldLabel: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.driverGroup'),//'所属分组',
		            columnWidth: .5
		         }]
			}]
		})
		this.carInfo = tempType;
		return this.carInfo;
	},
	//按钮区域
	createButtonArea: function(ordertruckApplyWindow) {
		var temp = this.buttonArea;
		if(temp) {
			return temp;
		}
		temp = Ext.create('Ext.container.Container',{
			defaultType:'button',
			defaults:{
				margin: '5 7 5 7'
			},
			layout:'column',
			items:[{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.43
			},{
				text:scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.borrowVehicleDetailWindow.button.return'),//'返回',
				columnWidth:.14,
				handler:function() {
					var window = this.ownerCt.ownerCt;
					window.close();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.43
			}]
		});
		this.buttonArea = temp;
		return this.buttonArea;
	},
	createOperateForm: function() {
		var form = this.operateForm;
		if(form) {
			return form;
		}
		form  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		this.operateForm = form;
		return this.operateForm;
	},
	createAcceptsGrid: function() {
		Ext.define('Foss.scheduling.ordervehicle.OrderVehicleDetailAcceptsModel',{
			extend:'Ext.data.Model',
			fields:[{
				name:'auditEmpName',
				type:'string'
			},{
				name:'auditTime',
				type:'date',
				convert: dateConvert	
			},{
				name:'status',
				type:'string',
				convert:function(value) {
					return scheduling.orderVehicle.statusValueMap.get(value);
				}
			},{
				name:'notes',
				type:'string'
			},{
				name:'perdictArriveTime',
				type:'date',
				convert: dateConvert	
			},{
				name:'ifNeedReleaseBill',
				type:'string',
				convert:function(value) {
					return value == 'Y' ? scheduling.orderVehicle.i18n('Foss.scheduling.common.yes'):scheduling.orderVehicle.i18n('Foss.scheduling.common.no');
				}
			}]
		})
		var dataStore = Ext.create('Ext.data.Store',{
			model:'Foss.scheduling.ordervehicle.OrderVehicleDetailAcceptsModel',
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		this.acceptsGrid = Ext.create('Ext.grid.Panel',{
			frame:true,
			emptyText: scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.origOrgName.title'),//'查询结果为空',
			height: 120,
			autoScroll:true,
			animCollapse: true,
			store:dataStore,
			constructor:function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.store = dataStore;
				me.callParent([cfg]);
			},			
			columns:[{
				header: scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.acceptEmpName'),//'受理人',
				dataIndex:'auditEmpName',
				flex:1.5
			},{
				header: scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.label.auditTime'),//'受理时间',
				dataIndex:'auditTime',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s',
				flex:1.5
			},{
				header: scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.label.acceptedResultStatus'),//'受理结果',
				dataIndex:'status',
				flex:1.5
			},{
				header: scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.ViewManualForm.notes.label'),//'备注',
				dataIndex:'notes',
				flex:2
			},{
				header: scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'),//'预计到达时间',
				dataIndex:'perdictArriveTime',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s',
				flex:1.5
			},{
				header: scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.ifNeedReleaseBill'),//'是否生成放行单',
				dataIndex:'ifNeedReleaseBill',
				flex:1.5
			}]
		});
		return this.acceptsGrid;
	},
	//初始化所有组件
	initAllComponent: function() {
		this.createOrdertruckBillBaseInfoPanel();
		this.createOrdertruckInfoPanel();
		this.createGoodsInfoPanel();
		this.createCarInfoPanel();
		this.createButtonArea();
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.initAllComponent();
		var form = this.createOperateForm();
		var acceptsGrid = this.createAcceptsGrid();
		form.add([me.baseInfo, me.ordertruckInfo, me.goodsInfo, me.carInfo]);
		setFormEditAble(form, false);
		me.add([form,acceptsGrid, me.buttonArea]);
	},
	//   加载数据
	initData: function(){
		var id = this.orderVehicleId;
		var params = {"orderVehicleVo.orderVehicleEntity.id":id};
		var form = this.operateForm;
		var acceptsGrid = this.acceptsGrid;
		Ext.Ajax.request({
			url:scheduling.realPath('queryOrderVehicleApplyDetail.action'),
			params:params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var orderVehicleVo = result.orderVehicleVo;
				var record = Ext.create('Foss.scheduling.ordervehicle.OrderVehicleApplyDetailFormModel', orderVehicleVo.orderVehicleEntity);
				form.loadRecord(record);

				var passOrderApplyDto = orderVehicleVo.passOrderApplyDto;
				var passOrderApplyEntity = passOrderApplyDto.passOrderApplyEntity;
				//车辆
				form.getForm().findField('vehicleNo').setValue(passOrderApplyEntity.vehicleNo)
				//司机姓名
				form.getForm().findField('driverName').setValue(passOrderApplyEntity.driverName)
				//司机联系方式
				form.getForm().findField('driverPhone').setValue(passOrderApplyEntity.driverPhone)
				//所属分组
				form.getForm().findField('driverGroup').setValue(passOrderApplyDto.driverGroup)
				//车型
				form.getForm().findField('orderVehicleModel_1').setValue(passOrderApplyDto.orderVehicleModel);
				//日志信息
				var auditorderapplyList = passOrderApplyDto.auditOrderApplyDtoList
				if(!auditorderapplyList){
					auditorderapplyList = {};
				}
				acceptsGrid.store.loadData(auditorderapplyList)
			},
			exception:function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
			}
		});
		
	},
	listeners: {
		beforeshow: function() {
			this.initData();
		}
	 }
});
