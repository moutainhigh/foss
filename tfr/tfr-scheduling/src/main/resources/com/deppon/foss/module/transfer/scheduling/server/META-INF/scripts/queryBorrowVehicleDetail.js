/**
 * 查询借车申请明细
 */
//model 用于初始化数据绑定
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyDetailFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'borrowNo',
		type:'string',
	},{
		name:'applyTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'borrowPurpose',
		type:'string',
		convert:function(value) {
			if(!value) {
				return ''
			}
			return scheduling.borrowPurposeMap.get(value);
		}
		
	},{
		name:'useType',
		type:'string',
		convert:function(value) {
			if(!value) {
				return ''
			}
			return scheduling.useTypeMap.get(value);
		}
	},{
		name:'orderVehicleModel',
		type:'string',
		convert:function(value) {
			if(!value) {
				return ''
			}
			return scheduling.vehicleTypeMap.get(value);
		}
	},{
		name:'auditOrgCode',
		type:'string',
	},{
		name:'auditOrgName',
		type:'string',
	},{
		name:'notes',
		type:'string',
	},{
		name:'weight',
		type:'string',
	},{
		name:'volume',
		type:'string',
	},{
		name:'borrowBeginTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'borrowEndTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'applyEmpCode',
		type:'string',
	},{
		name:'applyEmpName',
		type:'string',
	},{
		name:'telephoneNo',
		type:'string',
	},{
		name:'mobilephoneNo',
		type:'string',
	},{
		name:'applyOrgCode',
		type:'string',
	},{
		name:'applyOrgName',
		type:'string',
	},{
		name:'status',
		type:'string',
		convert:function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value, 'BORROWVEHICLE_STATUS');
		}
	},{
		name:'id',
		type:'string',
	},{
		name:'vehicleNo',
		type:'string',
	},{
		name:'vehicleOrganizationName',
		type:'string',
	},{
		// 实际借到的车型
		name:'orderVehicleModelName',
		type:'string',
	}]
});

// 借车明细window
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleDetailWindow',{
	extend:'Ext.window.Window',
	title:scheduling.i18n('foss.scheduling.borrowvehicle.borrowVehicleDetailWindow.title'),    //借车明细查询
	width:700,
	resizable:false,
	baseInfo:null,
	carInfo:null,
	buttonArea: null,
	operateForm:null,
	acceptsGrid:null,
	modal:true,
	closable: true,
	closeAction: 'hide',
	borrowNo:null,
	borrowVehicleInfo:null,
	matchesCarInfoPanel: null,
	bindData:function(record) {
		this.borrowNo = record.get('borrowNo');
	},
	//top
	createOrdertruckBillBaseInfoPanel: function() {
		if(this.baseInfo) {
			return this.baseInfo;
		}
		this.baseInfo = Ext.create('Ext.container.Container',{
			defaultType: 'textfield',
			width:655,
			layout:'column',
			defaults:{
				labelWidth: 85,
				margin: '5 10 5 10'
			},
			items:[{
				xtype: 'container',
			    html:'&nbsp',
			    columnWidth:.02
			},{
				name: 'borrowNo',
				fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowNo'),    //借车单号
				columnWidth:.3
			},{
				name:'status',
				fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.operatorStatus'),    //操作状态
				columnWidth:.3
			},{
				name:'applyTime',
				fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyTime'),    //申请时间
				columnWidth:.35
			}]
		})
		return this.baseInfo;
	},
	//车辆信息
	createCarInfoPanel: function() {
		if(this.carInfo) {
			return this.carInfo;
		}
		this.carInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:scheduling.i18n('foss.scheduling.passborrowvehicle.VehicleInfoPanel.title'),    //车辆信息
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
			    	name:'useType',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.useType'),    //使用类型 
			    	columnWidth: .5
			    },{ 
			    	name:'borrowPurpose',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowPurpose'),    //使用用途 
			    	columnWidth: .5
			    },{ 
			    	name:'orderVehicleModel',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型 
			    	columnWidth: .5
			    },{ 
			    	name:'weight',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.weight'),    //货物重量 
			    	columnWidth: .5
			    },{
			    	name:'volume',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.volume'),    //货物体积
		            columnWidth: .5
		         },{
			    	name:'notes',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.notes'),    //备注
		            columnWidth: .5
		         }]
			}]
		})
		return this.carInfo;
	},
	//借车信息
	createBorrowVehiclePanel: function() {
		if(this.borrowVehicleInfo) {
			return this.borrowVehicleInfo;
		}
		this.borrowVehicleInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:scheduling.i18n('foss.scheduling.borrowvehicle.borrowVehicleDetailWindow.title.borrowVehiclePanel'),    //借车信息
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
			    	name:'auditOrgName',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门 
			    	columnWidth: .5
			    },{ 
			    	name:'applyOrgName',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门 
			    	columnWidth: .5
			    },{
					xtype:'rangeDateField',
					dateType:'datetimefield_date97',
					fieldId:'Foss_borrowvehicle_borrowVehicleApplyDetailWindow_borrowTime_ID',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowTime'),    //借车时间
					fromName: 'borrowBeginTime',
					toName: 'borrowEndTime',
					columnWidth: 1
				},{ 
			    	name:'applyEmpName',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyEmpName'),    //申请人 
			    	columnWidth: .5
			    },{
			    	name:'telephoneNo',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.telephoneNo'),    //固定电话
		            columnWidth: .5
		         },{
			    	name:'mobilephoneNo',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.mobilephoneNo'),    //手机
		            columnWidth: .5
		         }]
			}]
		})
		return this.borrowVehicleInfo;
	},
	//匹配车辆信息
	createMatchesCarInfoPanel: function() {
		if(this.matchesCarInfoPanel) {
			return this.matchesCarInfoPanel;
		}
		this.matchesCarInfoPanel = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:scheduling.i18n('foss.scheduling.borrowvehicle.borrowVehicleDetailWindow.title.matchesCarInfoPanel'),    //匹配车辆信息
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
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.planVehicleNo'),    //安排车牌号 
			    	columnWidth: .5
			    },{ 
			    	name:'orderVehicleModelName',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModelName'),    //车辆型号 
			    	columnWidth: .5
			    },{
			    	name:'vehicleOrganizationName',
			    	fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.transTeam'),    //所属组别
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
				text:scheduling.i18n('foss.scheduling.borrowvehicle.borrowVehicleDetailWindow.button.return'),    //返回
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
	// 受理 log grid
	createAcceptsGrid: function() {
		Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleDetailAcceptsModel',{
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
					return FossDataDictionary.rendererSubmitToDisplay(value, 'BORROWVEHICLE_STATUS');
				}
			},{
				name:'notes',
				type:'string'
			}]
		})
		var dataStore = Ext.create('Ext.data.Store',{
			model:'Foss.scheduling.borrowvehicle.BorrowVehicleDetailAcceptsModel',
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		this.acceptsGrid = Ext.create('Ext.grid.Panel',{
			frame:true,
			emptyText: scheduling.i18n('foss.scheduling.borrowvehicle.tip.queryIsNull'),    //查询结果为空
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
				header:scheduling.i18n('foss.scheduling.borrowvehicle.label.auditEmpName'),    //受理人
				dataIndex:'auditEmpName',
				flex:2.5
			},{
				header:scheduling.i18n('foss.scheduling.borrowvehicle.label.auditTime'),    //受理时间
				dataIndex:'auditTime',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s',
				flex:2.5
			},{
				header:scheduling.i18n('foss.scheduling.borrowvehicle.label.acceptedResultStatus'),    //受理结果
				dataIndex:'status',
				flex:2.5
			},{
				header:scheduling.i18n('foss.scheduling.borrowvehicle.label.notes'),    //备注
				dataIndex:'notes',
				flex:2.5
			}]
		});
		return this.acceptsGrid;
	},
	//初始化所有组件
	initAllComponent: function() {
		this.createOrdertruckBillBaseInfoPanel();
		this.createCarInfoPanel();
		this.createBorrowVehiclePanel();
		this.createMatchesCarInfoPanel();
		this.createButtonArea();
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.initAllComponent();
		var form = this.createOperateForm();
		var acceptsGrid = this.createAcceptsGrid();
		
		form.add([ me.baseInfo, me.carInfo, me.borrowVehicleInfo, me.matchesCarInfoPanel]);
		setFormEditAble(form, false);
		me.add([form,acceptsGrid, me.buttonArea]);
	},
	//   加载数据
	initData: function(){
		var borrowNo = this.borrowNo;
		var params = {"borrowVehicleVo.borrowVehicleDto.borrowNo":borrowNo};
		var form = this.operateForm;
		var acceptsGrid = this.acceptsGrid;
		Ext.Ajax.request({
			url:scheduling.realPath('queryBorrowVehicleApplyDetail.action'),
			params:params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var borrowVehicleVo = result.borrowVehicleVo;
				var record = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyDetailFormModel', borrowVehicleVo.borrowVehicleDto);
				form.loadRecord(record);
				//日志信息
				var auditBorrowApplyList = borrowVehicleVo.borrowVehicleDto.auditBorrowApplyList;
				if(!auditBorrowApplyList){
					auditBorrowApplyList = {};
				}
				acceptsGrid.store.loadData(auditBorrowApplyList)
			},
			exception:function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
			}
		});
	},
	listeners: {
		beforeshow: function() {
			this.initData();
		}
	 }
});
