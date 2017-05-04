/**
 * 查询外请约车申请明细
 */
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyDetailFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'status',
		type:'string',
		convert: function(value) {
			if(!value) {
				return ''
			}
			return FossDataDictionary.rendererSubmitToDisplay(value, 'INVITEVEHICLE_STATUS');
		}
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
		name:'predictArriveTime',
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
		convert: function(value) {
			return scheduling.inviteVehicle.usePurposeMap.get(value);
		}
	},{
		name:'useType',
		type:'string',
		convert: function(value) {
			return scheduling.inviteVehicle.useTypeMap.get(value);
		}
	},{
		name:'orderVehicleModel',
		type:'string'
	},{
		name:'predictUseTime',
		type:'string',
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
		name:'dispatchTransDept',
		type:'string'
	},{
		name:'dispatchTransDeptName',
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
	},{//310248
		name:'businessType',
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
		type:'string'
	},{
		name:'vehicleLengthName',
		type:'string'
	},{
		name:'applyEmpOrgName',
		type:'string'
	},{
		name:'loadGoodsTime',
		type:'string', convert : function(value){
			if(value != null){
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}else{
				return null;
			}
		}
	},{
		name:'goodsName',
		type:'string'
	},{
		name : 'isScaneSeeGoods',
		type : 'string',
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
		name : 'isGoodsOver',
		type : 'string',
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
		name:'isPassByArrivedDept',
		type:'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}
			return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
		}
	},{
		name:'arrivedDeptName',
		type:'string'
	},{
		name:'arrivedAddress',
		type:'string'
	},{
		name:'isReturn',
		type:'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}
			return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
		}
	},{
		name:'returnCost',
		type:'string'
	},{
		name:'isContractVehicle',
		type:'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}
			return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
		}
	},{
		name:'contractLineCode',
		type:'string'
	},{
		name:'contractLine',
		type:'string'
	},{
		name:'useStatus',
		type:'string',
		convert : function(value){
			if (value == 'USING') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.using');//'已使用'
			} else if (value == 'UNUSED') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused');//'未使用'
			}
			return value;
		}
	},{
		name:'vehicleassembleNo',
		type:'string'
	}]
});


Ext.define('Foss.scheduling.inviteVehicle.MatchesCarInfoFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'vehicleNo',
		type:'string'
	},{
		name:'driverName',
		type:'string'
	},{
		name:'driverPhone',
		type:'string'
	},{
		name:'vehcleLengthName',
		type:'string'
	},{
		name:'inviteCost',
		type:'string'
	},{
		name:'predictArriveTime',
		type:'string',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name : 'carpoolingType',
		type : 'string',
		convert: function(value) {
			//269701--2016/03/28--拼车类型追加--精准拼车和精准专车
			if(value == 'ACCURATE_CARPOOL'){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.accurateCarpool'); // 精准拼车
			}else if(value == 'ACCURATE_LINE_CARPOOL'){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.accurateLineCarpool'); // 精准专车
			}else if(value == 'EXTERNAL_CARPOOL') {
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.externalCarpool'); // 外部拼车
			}else if(value == 'INTERNAL_WHOLECAR_CARPOOL') {
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.internalWholecarCarpool'); // 内部整车拼车
			}else if(value == 'INTERNAL_WHOLECAR_BREAKBULK_CARPOOL') {
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.internalWholecarBreakbulkCarpool'); // 内部整车零担拼车
			}else if(value == 'NO_CARPOOL') {
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType.noCarpool'); // 无
			}else {
				return value;
			}
		}
	},{
		name : 'isSaleDepartmentCompany',
		type : 'string',
		convert: function(value) {
			if(value == 'Y') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.yes'); //是
			}else if(value == 'N') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.common.no'); //否
			}else {
				return value;
			}
		}
	}]
});

// 外请约车明细window
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow',{
	extend:'Ext.window.Window',
	title: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.title'), //约车明细查询
	width:700,
	resizable:false,
	buttonArea: null,
	operateForm:null,
	acceptsGrid:null,
	modal:true,
	closable: true,
	closeAction: 'hide',
	inviteNo:null,
	matchesCarInfoPanel: null,
	customerAndGoodsInfo: null,
	inviteVehiclepplyInfo : null,
	matchesCarInfoForm: null,
	bindData:function(record) {
		this.inviteNo = record.get('inviteNo');
	},
	createInviteVehiclepplyInfo: function() {
		if(this.inviteVehiclepplyInfo) {
			return this.inviteVehiclepplyInfo;
		}
		this.inviteVehiclepplyInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.title'),  //申请信息
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 105,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'inviteNo',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.inviteNo'), //外请车单号
			    	columnWidth: .5
			    },{ 
			    	name:'status',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.status'),  //操作状态
			    	columnWidth: .5
			    },{ 
			    	name:'applyTime',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'), //申请时间
			    	columnWidth: .5
			    },{
			    	name: 'dispatchTransDeptName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.dispatchTransDeptName'), //派车车队
			    	columnWidth: .5
				},{ 
			    	name:'usePurpose',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.usePurpose'), //业务类型
			    	columnWidth: .5
			    },{
			    	name:'useType',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.useType'), //用车类型
		            columnWidth: .5
		         },{
			    	name:'vehicleLengthName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.vehicleLengthName'), //用车车型
		            columnWidth: .5
		         },{
			    	name:'applyOrgName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyOrgName'), //用车部门
		            columnWidth: .5
			     },{
			    	name:'useAddress',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.useAddress'), //用车地址
		            columnWidth: 1
			     },{
			    	name:'applyEmpName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyEmpName'), //申请人姓名
		            columnWidth: .5
			     },{
			    	name:'applyEmpOrgName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyEmpOrgName'), //所在部门
		            columnWidth: .5
			     },{
			    	name:'predictUseTime',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.predictUseTime'), //用车时间
		            columnWidth: .5
				  },{
			    	name:'telephoneNo',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.telephoneNo'), //固定电话
		            columnWidth: .5
				  },{//310248
					name: 'applyFees',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyFees'),//'外请车费用',
					columnWidth:.5,
				  },{//310248
					name: 'bearFeesDeptName',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bearFeesDept'),//'费用承担部门',
					columnWidth:.5,
				  },{//310248
					name: 'businessType',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.businessType'),//'业务类型',
					columnWidth:.5,
				  },{
			    	name:'mobilephoneNo',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.mobilephoneNo'), //手机
		            columnWidth: .5
				  },{
			    	fieldLabel: '', 
			    	columnWidth: .5
				 },{
					 name:'isPassByArrivedDept',
				     fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.isPassByArrivedDept'), //是否经过到达外场
				     labelWidth: 120,
			         columnWidth: .6 
				 },{
					 name:'arrivedDeptName',
				     fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.arrivedDeptName'), //到达部门
			         columnWidth: .4 
				 },{
					 name:'arrivedAddress',
				     fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedAddress'), //到达地址
			         columnWidth: 1 
				 },{
			    	name:'isReturn',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn'), //是否回程
		            columnWidth: .5
				  },{
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginReturnCostStr'),  //回程价格
			    	name:'returnCost',
			    	columnWidth: .5
				 },{
			    	name:'isContractVehicle',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isContractVehicle'), //是否合同车
		            columnWidth: .5
				  },{
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.contractLineCode'), //合同线路
			    	name:'contractLine',
			    	columnWidth: .5
				  },{
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useStatus'), //使用状态
					name:'useStatus',
					columnWidth: .5
				  },{
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.vehicleassembleNo'), //配载单号
					name:'vehicleassembleNo',
					columnWidth: .5,
					hidden : false
				  }]
			}]
		})
		return this.inviteVehiclepplyInfo;
	},
	// 客户和货物信息
	createCustomerAndGoodsInfo: function() {
		if(this.customerAndGoodsInfo) {
			return this.customerAndGoodsInfo;
		}
		this.customerAndGoodsInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.title'), //客户和货物信息
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 105,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'goodsName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'), //货物名称
			    	columnWidth: .5
			    },{
			    	name:'weight',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight'), //货物重量
			    	columnWidth: .5
			    },{ 
			    	name:'goodsPackege',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsPackage'), //货物包装
			    	columnWidth: .5
			    },{ 
			    	name:'goodsQty',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty'), //货物件数
			    	columnWidth: .5
			    },{
			    	name:'volume',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume'), //货物体积
		            columnWidth: .5
			    },{
			    	name:'loadGoodsTime',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.loadGoodsTime'), //装货时间
		            columnWidth: .5
			    },{
					name : 'isScaneSeeGoods',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isScaneSeeGoods'), // 是否现场看货
					columnWidth: .5
			    },{
					name : 'isGoodsOver',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isGoodsOver'), // 货物是否可叠加
					columnWidth: .5
			    },{ 
			    	name:'requirment',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.remark'), //备注
			    	columnWidth: 1
			    },{
			    	name:'clientName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientName'), //发货人
		            columnWidth: .5
		         },{
			    	name:'clientContactPhone',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientContactPhone'), //发货人电话
		            columnWidth: .5
		         }]
			}]
		})
		return this.customerAndGoodsInfo;
	},
	//匹配车辆信息
	createMatchesCarInfoPanel: function() {
		if(this.matchesCarInfoPanel) {
			return this.matchesCarInfoPanel;
		}
		this.matchesCarInfoPanel = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.title'), //匹配车辆信息
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 110,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'vehicleNo',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehicleNo'), //车牌号 
			    	columnWidth: .5
			    },{ 
			    	name:'vehcleLengthName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.vehcleLengthName'),  //车辆型号
			    	columnWidth: .5
			    },{
			    	name:'driverName',
			    	fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.driverName'), //驾驶人员姓名
		            columnWidth: .5
		         },{
				    name:'driverPhone',
				    fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.driverPhone'), //驾驶人员电话
			        columnWidth: .5
		         },{
		        	name:'inviteCost',
				    fieldLabel: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.matchesCarInfoPanel.inviteCost'), //请车价格
			        columnWidth: .5
		         },{
		        	name:'predictArriveTime',
				    fieldLabel: scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'), //'预计到达时间',
			        columnWidth: .5
		         },{
		        	name:'isSaleDepartmentCompany',
		        	fieldLabel: scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.isSaleDepartmentCompany'), // 是否营业部自请车
		        	columnWidth: .5
				 },{
					name:'carpoolingType',
					fieldLabel: scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.carpoolingType'), // 拼车类型
					columnWidth: .5
				 }]
			}]
		})
		return this.matchesCarInfoPanel;
	},
	//按钮区域
	createButtonArea: function(ordertruckApplyWindow) {
		if(this.buttonArea) {
			return this.buttonArea;
		}
		this.buttonArea = Ext.create('Ext.container.Container',{
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
				text: scheduling.inviteVehicle.i18n('Foss.scheduling.button.return'), //返回
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
		return this.buttonArea;
	},
	createOperateForm: function() {
		if(this.operateForm) {
			return this.operateForm;
		}
		this.operateForm  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		return this.operateForm;
	},
	createMatchesCarInfoForm: function() {
		if(this.matchesCarInfoForm) {
			return this.matchesCarInfoForm;
		}
		this.matchesCarInfoForm  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		return this.matchesCarInfoForm;
	},
	// 受理 log grid
	createAcceptsGrid: function() {
		Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleDetailAcceptsModel',{
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
					return FossDataDictionary.rendererSubmitToDisplay(value, 'INVITEVEHICLE_STATUS');
				}
			},{
				name:'notes',
				type:'string'
			}]
		})
		var dataStore = Ext.create('Ext.data.Store',{
			model:'Foss.scheduling.inviteVehicle.InviteVehicleDetailAcceptsModel',
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		this.acceptsGrid = Ext.create('Ext.grid.Panel',{
			frame:true,
			emptyText: scheduling.inviteVehicle.i18n('Foss.scheduling.operation.tip.resultNull'), //查询结果为空
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
				header: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.auditEmpName'),  //审核人
				dataIndex:'auditEmpName',
				flex:2.5
			},{
				header: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.auditTime'), //审核时间
				dataIndex:'auditTime',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s',
				flex:2.5
			},{
				header: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.status'), //审核结果
				dataIndex:'status',
				flex:2.5
			},{
				header: scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.acceptsGrid.notes'), //备注
				dataIndex:'notes',
				flex:2.5
			}]
		});
		return this.acceptsGrid;
	},
	//初始化所有组件
	initAllComponent: function() {
		this.createInviteVehiclepplyInfo();
		this.createCustomerAndGoodsInfo();
		this.createAcceptsGrid();
		this.createMatchesCarInfoPanel();
		this.createButtonArea();
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.initAllComponent();
		var inviteVehiclepplyInfo = this.createInviteVehiclepplyInfo();
		var operateForm = this.createOperateForm();
		var acceptsGrid = this.createAcceptsGrid();
		operateForm.add([me.inviteVehiclepplyInfo, me.customerAndGoodsInfo]);
		setFormEditAble(operateForm, false);
		
		var matchesCarInfoForm = this.createMatchesCarInfoForm();
		matchesCarInfoForm.add([me.matchesCarInfoPanel]);
		setFormEditAble(matchesCarInfoForm, false);
		
		me.add([operateForm, acceptsGrid, matchesCarInfoForm, me.buttonArea]);
	},
	//   加载数据
	initData: function(){
		var inviteNo = this.inviteNo;
		var params = {"inviteVehicleVo.inviteVehicleDto.inviteNo":inviteNo};
		var form = this.operateForm;
		var inviteVehiclepplyInfoFrom = this.inviteVehiclepplyInfo;
		var acceptsGrid = this.acceptsGrid;
		var matchesCarInfoForm = this.matchesCarInfoForm;
		Ext.Ajax.request({
			url:scheduling.realPath('queryInviteVehicleApplyDetail.action'),
			params:params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var inviteVehicleVo = result.inviteVehicleVo;
				var record = Ext.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyDetailFormModel', inviteVehicleVo.inviteVehicleDto);
				form.loadRecord(record);
				
				//当使用状态是未使用时 隐藏 配载单号否则显示
				if(inviteVehiclepplyInfoFrom.up().getForm().findField('useStatus').getValue() == scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused')){
					inviteVehiclepplyInfoFrom.up().getForm().findField('vehicleassembleNo').hidden = true;
					document.getElementById(inviteVehiclepplyInfoFrom.up().getForm().findField('vehicleassembleNo').getId()).style.display = 'none';
				}else{
					inviteVehiclepplyInfoFrom.up().getForm().findField('vehicleassembleNo').hidden = false;
				    document.getElementById(inviteVehiclepplyInfoFrom.up().getForm().findField('vehicleassembleNo').getId()).style.display = '';
				}
				//日志信息
				var auditInviteVehicleList = inviteVehicleVo.inviteVehicleDto.auditInviteVehicleList;
				if(!auditInviteVehicleList){
					auditInviteVehicleList = {};
				}
				acceptsGrid.store.loadData(auditInviteVehicleList);
				// 车辆信息
				var vehicleDriverWithDto = inviteVehicleVo.inviteVehicleDto.vehicleDriverWithDto;
				if(vehicleDriverWithDto!=null && auditInviteVehicleList.length!=0) {
					vehicleDriverWithDto.isSaleDepartmentCompany=auditInviteVehicleList[0].isSaleDepartmentCompany;
					vehicleDriverWithDto.carpoolingType=auditInviteVehicleList[0].carpoolingType;
				}
				var vehicleDriverWithRecord = Ext.create('Foss.scheduling.inviteVehicle.MatchesCarInfoFormModel', vehicleDriverWithDto);
				matchesCarInfoForm.loadRecord(vehicleDriverWithRecord);
			},
			exception:function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'), result.message);
			}
		});
	},
	listeners: {
		beforeshow: function() {
			this.initData();
		}
	 }
});
