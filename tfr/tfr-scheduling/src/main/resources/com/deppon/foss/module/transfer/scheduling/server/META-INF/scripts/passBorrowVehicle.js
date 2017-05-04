/** 借车申请grid */
//定义一个Model模型
Ext.define('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [{
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
		convert: function(value) {
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
	}]
});

//定义约车数据源
Ext.define('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryModel'
});

//左边 借车信息表格
Ext.define('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryGrid', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	frame: true,
	animCollapse: false,
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.borrowVehicleApplyQueryGrid.title'),    //借车申请列表
	//表格绑定store
	store: null,
	//设置选择模式为复选框选择
	selModel: null,
	height: 500,
	autoScroll:true,
	emptyText: scheduling.i18n('foss.scheduling.borrowvehicle.tip.queryIsNull'),    //查询结果为空
	columns: [{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowNo'),    //借车单号
		flex: 1, 
		sortable: true, 
		dataIndex: 'borrowNo'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.status'),    //状态
		flex: 1, 
		sortable: true, 
		dataIndex: 'status'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门
		flex: 3, 
		sortable: true, 
		dataIndex: 'applyOrgName'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型
		flex: 1, 
		sortable: true, 
		dataIndex: 'orderVehicleModel'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
	},
	listeners: {
			select: function(model, record) {
                var borrowNo = record.get('borrowNo');
                scheduling.borrowVehicleApplyDetailForm.getForm().loadRecord(record);
                scheduling.queryBorrowVehicleAuditLog(borrowNo);
             }
	}
});

//左面板 审核结果
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInputForm', {
	extend: 'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	height: 180,
	title:scheduling.i18n('foss.scheduling.borrowvehicle.label.acceptedResultStatus'),    //受理结果
	frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		labelWidth:100
	},
	items: [{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.approverNotes'),    //审核结果备注
		name: 'notes',
		xtype:'textarea',
		maxLength : 330,
		maxLengthText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.maxLength'),    //长度已超过最大限制
		columnWidth:.9
	}],
	// 不同意
	disagree:function(url) {
		// 拒绝必须有备注信息
		var borrowVehicleApplyQueryGrid = scheduling.borrowVehicleApplyQueryGrid;
	    if(!borrowVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
	    	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseBorrowVehicleInfo'));   //请选择您要操作的借车申请信息
	 	   	return false;
	    }
        //备注信息
        var values = scheduling.passBorrowVehicleVehicleInputForm.getForm().getValues();
        var notes  = values.notes;
        if(Ext.isEmpty(notes)) {
        	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.notesIsRequired'));    //备注信息不能为空
	 	   	return false;
        }
        // 要提交的信息
        var auditBorrowApplyList = new Array();
	    var dataList = borrowVehicleApplyQueryGrid.getSelectionModel().getSelection();
	    for(var i = 0; i < dataList.length; i++) {
	    	var record = dataList[i];
	    	var borrowNo = record.get('borrowNo');
	    	var auditBorrowApplyEntity = {
	    			'borrowNo':borrowNo,
	    			'notes':notes
	    		};
	    	auditBorrowApplyList.push(auditBorrowApplyEntity);
	    }
		var passBorrowApplyVo = {passBorrowApplyVo:{auditBorrowApplyList:auditBorrowApplyList}}
		this.ajaxRequest(passBorrowApplyVo, url, true, true);
	},
	ajaxRequest: function(passBorrowApplyVo, url, isDelete, isResetLog) {
		Ext.Ajax.request({
			url:url,
			jsonData: passBorrowApplyVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var auditBorrowApplyEntity = passBorrowApplyVo.passBorrowApplyVo.auditBorrowApplyEntity;
				var borrowNoList = new Array();
				// 拒绝  退回时 可以多选 auditBorrowApplyEntity 为undefined 
				// 如果auditBorrowApplyEntity 不存在  认为是不同意操作. 
				if(!auditBorrowApplyEntity) {
					borrowNoList = passBorrowApplyVo.passBorrowApplyVo.auditBorrowApplyList;
				} else {
					// 审核通过操作.
					borrowNoList.push(auditBorrowApplyEntity.borrowNo);
				}
				if(!isDelete) {
					borrowNoList = undefined; 
				}
				// reload
				scheduling.acceptedBorrowVehicleApplyWindow.initData(borrowNoList);
				if(isResetLog) {
					// 拒绝 退回
					scheduling.queryBorrowVehicleAuditLog(undefined);
				} else {
					// 审核通过
					scheduling.queryBorrowVehicleAuditLog(auditBorrowApplyEntity.borrowNo);
				}
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
			}
		});		
	},
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',
		items: [{
			xtype: 'button',			
			text: scheduling.i18n('foss.scheduling.borrowvehicle.label.through'),    //通过
			margin:'0 0 0 120',
			handler: function() {
				// 选中的借车申请
				var borrowVehicleApplyQueryGrid = scheduling.borrowVehicleApplyQueryGrid;
			    if(!borrowVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseBorrowVehicleInfo'));   //请选择您要操作的借车申请信息
			 	   	return false;
			    }
			    var dataList = borrowVehicleApplyQueryGrid.getSelectionModel().getSelection();
			    if(dataList && dataList.length > 1) {
			    	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.onlyChoiseOne'));   //请选择单条记录进行操作!
			 	   	return false;
			    }
		        var borrowNo = dataList[0].get('borrowNo');
		        // 车辆信息
				var vehicleGrid = scheduling.passBorrowVehicleVehicleInfoQueryGrid;
				if(!vehicleGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseNeedVehicle'));    //请选择要安排的车辆信息!
			 	   	return false;
			    }
				var values = scheduling.passBorrowVehicleVehicleInputForm.getForm().getValues();
				var vehicleDataList = vehicleGrid.getSelectionModel().getSelection();
		        var vehicleRecord = vehicleDataList[0];
		        var vehicleNo = vehicleRecord.get('vehicleNo');
		        var url = scheduling.realPath('doAcceptedBorrowVehicleApply.action');
		        var passBorrowApplyEntity = {
		        	"borrowNo":	borrowNo,
		        	"vehicleNo":vehicleNo
		        };
		        var auditBorrowApplyEntity = {
		        	"notes":values.notes
		        };
		        var passBorrowApplyVo = {passBorrowApplyVo:{auditBorrowApplyEntity:auditBorrowApplyEntity, passBorrowApplyEntity:passBorrowApplyEntity}}
				// 提交请求
				this.ownerCt.ownerCt.ajaxRequest(passBorrowApplyVo, url);
			}
		},{
			xtype: 'button',			
			text: scheduling.i18n('foss.scheduling.borrowvehicle.button.refuse'),    //拒绝
			margin:'0 0 0 80',
			handler: function() {
				var url = scheduling.realPath('doDismissBorrowVehicleApply.action');
				this.ownerCt.ownerCt.disagree(url);
			}
		},{
			xtype: 'button',			
			text: scheduling.i18n('foss.scheduling.borrowvehicle.button.return'),    //退回
			margin:'0 0 0 80',
			handler: function() {
				var url = scheduling.realPath('doReturnBorrowVehicleApply.action');
				this.ownerCt.ownerCt.disagree(url);
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

//==============右面板构建
//右面板-申请信息表单
Ext.define('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyDetailForm', {
	extend: 'Ext.form.Panel',
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyDetailForm.title'),    //申请信息
	layout:'column',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaultType: 'textfield',
	columnWidth:1,
	defaults: {
		margin:'5 5 0 5',
		labelWidth:85
	},
	items: [{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowNo'),    //借车单号
		name: 'borrowNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.operatorStatus'),    //操作状态
		name: 'status',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyTime'),    //申请时间
		name: 'applyTime',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.useType'),    //使用类型
		name: 'useType',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型
		name: 'orderVehicleModel',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowPurpose'),    //借车用途
		name: 'borrowPurpose',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.volume'),    //货物体积
		name: 'volume',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.weight'),    //货物重量
		name: 'weight',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.notes'),    //备注
		name: 'notes',
		allowBlank:true,
		columnWidth:.99,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
		name: 'auditOrgName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门
		name: 'applyOrgName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyEmpName'),    //申请人
		name: 'applyEmpName',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.telephoneNo'),    //固定电话
		name: 'telephoneNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.mobilephoneNo'),    //手机
		name: 'mobilephoneNo',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowBeginTime'),    //借车起始时间
		name: 'borrowBeginTime',
		allowBlank:true,
		columnWidth:.5,
		readOnly:true
	},{
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowEndTime'),    //借车结束时间
		name: 'borrowEndTime',
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

//定义一个审核受理记录的模型
Ext.define('Foss.scheduling.passborrowvehicle.AuditOrderLogModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [{
	    	// 约车编号
			name:'orderNo',  	         
			type:'string',
		},{
			// 受理时间
			name:'auditTime',
			type:'date',
			convert: dateConvert
		},{
			// 序号
			name:'auditNo',
			type:'string',
		},{
			// 备注
			name:'notes',
			type:'string',
		},{
			// 审核车队名称
			name:'auditOrgName',
			type:'string',
		},{
			// 审核车队编码
			name:'auditOrgCode',
			type:'string',
		},{
			// 审核人员名称
			name:'auditEmpName',
			type:'string',
		},{
			// 审核人员编码
			name:'auditEmpCode',
			type:'string',
		},{
			name:'id',
			type:'string',
		},{
			name:'perdictArriveTime',
			type:'date',
			convert: dateConvert	
		},{
			name:'ifNeedReleaseBill',
			type:'string',
			convert:function(value) {
				return value == 'Y' ? scheduling.i18n('foss.scheduling.borrowvehicle.label.yes') :scheduling.i18n('foss.scheduling.borrowvehicle.label.no');
			}
		},{
			name:'status',
			type:'string'
		}
	]
});

//定义审核受理记录数据源
Ext.define('Foss.scheduling.passborrowvehicle.AuditOrderLogStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passborrowvehicle.AuditOrderLogModel'
});

Ext.define('Foss.scheduling.passborrowvehicle.AuditBorrowLogGrid', {
	extend: 'Ext.grid.Panel',
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.AuditBorrowLogGrid.title'),    //受理记录
	//表格对象增加一个边框
	frame: true,
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	//指定表格的高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//表格绑定store
	store: null,
	height: 150,
	autoScroll:true,
	emptyText: scheduling.i18n('foss.scheduling.borrowvehicle.tip.queryIsNull'),    //查询结果为空	
	columnWidth:1,
	columns: [{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditEmpName'),    //受理人
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditEmpName'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditTime'),    //受理时间
		flex: 1, 
		sortable: true, 
		dataIndex: 'auditTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s' 
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.acceptedResultStatus'),    //受理结果
		flex: 1, 
		sortable: true, 
		dataIndex: 'status',
		renderer:function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value, 'BORROWVEHICLE_STATUS');
		}
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.acceptedResultNotes'),    //受理结果备注
		flex: 1, 
		sortable: true, 
		dataIndex: 'notes'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passborrowvehicle.AuditOrderLogStore');
		me.callParent([cfg]);
	}
});

//审核log
scheduling.queryBorrowVehicleAuditLog = function(borrowNo) {
	if(!borrowNo) {
		scheduling.passBorrowVehicleAuditBorrowLogGrid.store.loadData([]);
		return false;
	}
	var passBorrowApplyVo = {
				'passBorrowApplyVo.passBorrowApplyEntity.borrowNo':borrowNo
			};
	Ext.Ajax.request({
		url:scheduling.realPath('queryAuditBorrowApplyLog.action'),
		params: passBorrowApplyVo,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var list = result.passBorrowApplyVo.auditBorrowApplyList;
			if(!list) {
				list = {};
			}
			scheduling.passBorrowVehicleAuditBorrowLogGrid.store.loadData(list);
		},
		exception: function(response) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
		}
	});
}

//车辆信息表单
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInfoForm', {
	extend: 'Ext.form.Panel',	
	layout:'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		labelWidth:60
	},
	items: [{
			fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.vehcleLengthCode'),    //车型
			name:'vehcleLengthCode',
			xtype:'commonvehicletypeselector',
			columnWidth:.43
		}, {
			columnWidth:.43,
			xtype: 'combobox',
			mode:'local',
			queryMode: 'local',		
			triggerAction:'all',
			fieldLabel:     scheduling.i18n('foss.scheduling.borrowvehicle.label.transTeam'),    //所属组别
			name:           'transTeam',
			displayField:   'name',
			valueField:     'code',		
			store: null
		},{
			xtype: 'button',			
			text: scheduling.i18n('foss.scheduling.borrowvehicle.button.search'),    //查询
			columnWidth:.14,
			handler: function() {
				// 拒绝必须有备注信息
				var borrowVehicleApplyQueryGrid = scheduling.borrowVehicleApplyQueryGrid;
			    if(!borrowVehicleApplyQueryGrid.getSelectionModel().hasSelection()) {
			    	Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseBorrowVehicleInfo'));
			 	   	return false;
			    }
				// 车型
                var values = scheduling.passBorrowVehicleVehicleInfoForm.getValues();
                var vehcleLengthCode = values.vehcleLengthCode;
                // 组别
                var orgId = values.transTeam;
                if(!orgId) {
                	orgId = FossUserContext.getCurrentDept().code;
                }
                var ownTruck = {
                		"vehcleLengthCode":vehcleLengthCode,
                		"orgId":orgId
                		};
                scheduling.passBorrowVehicleVehicleInfoQueryGrid.queryCompanyVehicleByBorrowVehicle(ownTruck);
			}
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	},
	//车辆组别
	queryTransTeamListByBorrowVehicle:function(transDepartment) {
		var passBorrowApplyVo = {
					'passBorrowApplyVo.transDepartment':transDepartment
				};
		Ext.Ajax.request({
			url:scheduling.realPath('queryTransTeamListForBorrow.action'),
			params: passBorrowApplyVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passBorrowApplyVo.transTeamList;
				if(!list) {
					list = [];
				}
				var json= {
						fields:['name','code'],
					    data : list
					};
				var store = Ext.create('Ext.data.Store', json);
				var transTeam = scheduling.passBorrowVehicleVehicleInfoForm.getForm().findField('transTeam');
				transTeam.store = store;
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
			}
		});
	}
});


/** 车辆信息grid */
//定义一个车辆信息的模型
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInfoModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
 		{name: 'vehicleNo',type:'string'},
		{name: 'vehcleLengthCode',type:'string'},
		{name: 'vehicleLengthName',type:'string'},
		{name: 'orgId', type: 'string'},
		{name: 'vehicleMotorcadeName', type: 'string'}
	]
});

scheduling.vehicleInfoTipView = function (value, metaData, record, rowIndex, colIndex, store, view) {
	metaData.tip = Ext.create('Ext.tip.ToolTip', {
		target: view.el,
		title: scheduling.i18n('foss.scheduling.borrowvehicle.vehicleInfoTipView.title'),    //车辆详情信息
		titleAlign: 'center',
		bodyStyle: 'background:white; padding:10px; border-style: ridge; border-width: 2px;',
		height: 230,
		width: 420,
		trackMouse: true,
		hideDelay: 2000,
		delegate: view.itemSelector,
		tpl: new Ext.XTemplate(
			'<tpl for=".">',
				'<p>{field}:{content}</p></br>',
			'</tpl></p>'
		),
		listeners: {
			beforeshow: function(tip) {
				var vehicleRecord = view.getRecord(tip.triggerElement);
				tip.update([
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.vehicleNo')+'</b>',  content: vehicleRecord.get('vehicleNo') },  //车牌号
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.useStatus')+'</b>', content: vehicleRecord.get('vehicleNo') },  //使用状态
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowOrgName')+'</b>', content: vehicleRecord.get('vehicleNo') },  //约车部门
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.goodsInfo')+'</b>', content: vehicleRecord.get('vehicleNo') },  //所装货物
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.useTime')+'</b>', content: vehicleRecord.get('vehicleNo') },  //用车时间
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.applyEmpName')+'</b>',  content: vehicleRecord.get('vehicleNo') },  //申请人
					{ field: '<b>'+scheduling.i18n('foss.scheduling.borrowvehicle.label.auditEmpName')+'</b>',  content: vehicleRecord.get('vehicleNo') }  //受理人
				]);
			}
		}
	});
	return value;
}

//创建车辆信息表格
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInfoQueryGrid', {
	extend: 'Ext.grid.Panel',
	//表格对象增加一个边框
	frame: false,
	collapsible: false,
	animCollapse: false,
	title:'',
	//表格绑定store
	store: null,
	//设置选择模式为复选框选择
	selModel: null,
	height: 100,
	autoScroll:true,
	emptyText: scheduling.i18n('foss.scheduling.borrowvehicle.tip.queryIsNull'),    //查询结果为空
	columns: [{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.vehicleNo'),    //车牌号
		flex: 1, 
		sortable: true, 
//		renderer : scheduling.vehicleInfoTipView,
		dataIndex: 'vehicleNo'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.vehcleLengthCode'),    //车型
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleLengthName'
	},{
		text: scheduling.i18n('foss.scheduling.borrowvehicle.label.vehicleMotorcadeName'),    //车辆组别
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleMotorcadeName'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.passborrowvehicle.VehicleInfoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{'mode':'SINGLE','showHeaderCheckbox':false});
		me.callParent([cfg]);
	},
	listeners: {
		select: function(model, record) {
			var passBorrowVehicleVehicleInputForm = scheduling.passBorrowVehicleVehicleInputForm.getForm();
         }
	},
	// 查询公司车
	queryCompanyVehicleByBorrowVehicle: function(ownTruck) {
		var passBorrowApplyVo = {passBorrowApplyVo:{ownTruckEntity:ownTruck}}
		Ext.Ajax.request({
			url:scheduling.realPath('queryCompanyVehicle.action'),
			jsonData: passBorrowApplyVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passBorrowApplyVo.ownTruckList;
				if(!list) {
					list = [];
				}
				scheduling.passBorrowVehicleVehicleInfoQueryGrid.store.loadData(list);
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
			}
		});
	}
});

//定义约车数据源
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInfoStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.scheduling.passborrowvehicle.VehicleInfoModel',
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//左边面板
Ext.define('Foss.scheduling.passborrowvehicle.LeftPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 5 0 0',
		columnWidth:.5,
		height:732,
		frame:true,
		constructor: function(config){
			var borrowVehicleApplyQueryGrid = Ext.create('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryGrid');
			var passBorrowVehicleVehicleInputForm = Ext.create('Foss.scheduling.passborrowvehicle.VehicleInputForm');
			scheduling.borrowVehicleApplyQueryGrid = borrowVehicleApplyQueryGrid;
			scheduling.passBorrowVehicleVehicleInputForm = passBorrowVehicleVehicleInputForm;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      borrowVehicleApplyQueryGrid
		      ,
		      passBorrowVehicleVehicleInputForm
		    ]; 
			me.callParent([cfg]);
		}
});

//右边面板车辆信息模块
Ext.define('Foss.scheduling.passborrowvehicle.VehicleInfoPanel',{
		extend:'Ext.panel.Panel',
		title:scheduling.i18n('foss.scheduling.passborrowvehicle.VehicleInfoPanel.title'),    //车辆信息
		margin:'0 0 0 0',
		columnWidth:1,
		frame:true,
		constructor: function(config){
			var vehicleInfoForm = Ext.create('Foss.scheduling.passborrowvehicle.VehicleInfoForm');
			var passBorrowVehicleVehicleInfoQueryGrid = Ext.create('Foss.scheduling.passborrowvehicle.VehicleInfoQueryGrid');
			scheduling.passBorrowVehicleVehicleInfoForm = vehicleInfoForm;
			scheduling.passBorrowVehicleVehicleInfoQueryGrid = passBorrowVehicleVehicleInfoQueryGrid;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      vehicleInfoForm,
		      passBorrowVehicleVehicleInfoQueryGrid
		    ]; 
			me.callParent([cfg]);
		}
});

//右边面板
Ext.define('Foss.scheduling.passborrowvehicle.RightPanel',{
		extend:'Ext.panel.Panel',
		margin:'0 5 0 0',
		columnWidth:.5,
		frame:true,
		constructor: function(config){
			var detailForm = Ext.create('Foss.scheduling.passborrowvehicle.BorrowVehicleApplyDetailForm');
			var auditBorrowLogGrid = Ext.create('Foss.scheduling.passborrowvehicle.AuditBorrowLogGrid');
			var vehicleInfoPanel = Ext.create('Foss.scheduling.passborrowvehicle.VehicleInfoPanel');
			scheduling.passBorrowVehicleAuditBorrowLogGrid = auditBorrowLogGrid;
			scheduling.borrowVehicleApplyDetailForm = detailForm;
			var me = this,
			cfg = Ext.apply({}, config);
		    me.items = [
		      detailForm,
		      auditBorrowLogGrid,
		      vehicleInfoPanel
		    ]; 
			me.callParent([cfg]);
		}
});

//定义整体列布局
//将约车信息、车辆信息、申请信息、货物信息、审核记录组合显示
Ext.define('Foss.scheduling.passborrowvehicle.PassBorrowVehicleContainer',{
	  extend: 'Ext.container.Container',
	  layout: 'column',
	  frame: false,
	  cls: 'autoHeight',
	  bodyCls: 'autoHeight',	  
	  lefttPanel: null,
	  getLefttPanel: function(){
	    if(this.lefttPanel==null){
	      this.lefttPanel = Ext.create('Foss.scheduling.passborrowvehicle.LeftPanel');
	    }
	    return this.lefttPanel;
	  },
	  rightPanel: null,
	  getRightPanel: function(){
	    if(this.rightPanel==null){
	      this.rightPanel = Ext.create('Foss.scheduling.passborrowvehicle.RightPanel');
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

scheduling.convertMap = function (list) {
	var map = new Ext.util.HashMap();
	if(!list || list.length == 0){
		return map;
	}
	for(var index in list) {
		map.add(list[index].valueCode, list[index].valueName);
	}
	return map;
}


/**
 * 借车审核window
 */
Ext.define('Foss.scheduling.borrowvehicle.PassBorrowVehicleWindow',{
	extend:'Ext.window.Window',
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.PassBorrowVehicleWindow.title'),    //借车审核
	width:1100,
	height:800,
	resizable:false,
	modal:true,
	closable: true,
	closeAction: 'hide',
	borrowNoList:null,
	passBorrowVehicleContainer:null,
	resetView: function() {
		scheduling.passBorrowVehicleVehicleInputForm.getForm().reset();
		scheduling.borrowVehicleApplyDetailForm.getForm().reset();
		scheduling.passBorrowVehicleAuditBorrowLogGrid.store.loadData([]);
		scheduling.passBorrowVehicleVehicleInfoForm.getForm().reset();
	},
	initData: function(borrowNoListParams){
		//重置 
		this.resetView();
        //车辆组别
		var orgCode = FossUserContext.getCurrentDept().code;;
		scheduling.passBorrowVehicleVehicleInfoForm.queryTransTeamListByBorrowVehicle(orgCode);
		var borrowNoList = this.borrowNoList;
		if(borrowNoListParams) {
			var len = borrowNoListParams.length; 
			for(var i = 0 ;i < len; i++) {
				Ext.Array.remove(borrowNoList, borrowNoListParams[i].borrowNo); 
			}
		}
		var passBorrowApplyVo = {
				'passBorrowApplyVo.borrowNoList':borrowNoList
			};
		Ext.Ajax.request({
			url:scheduling.realPath('queryNeedAcceptBorrowVehicleApply.action'),
			params:passBorrowApplyVo,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.passBorrowApplyVo.borrowVehicleList;
				scheduling.borrowVehicleApplyQueryGrid.store.loadData(list);
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
				scheduling.acceptedBorrowVehicleApplyWindow.close();
			}
		});
	},
	createPassBorrowVehicleContainer: function() {
		if(this.passBorrowVehicleContainer) {
			return this.passBorrowVehicleContainer;
		}
		this.passBorrowVehicleContainer = Ext.create('Foss.scheduling.passborrowvehicle.PassBorrowVehicleContainer');
		return this.passBorrowVehicleContainer;
	},
	constructor: function(config){
		this.createPassBorrowVehicleContainer();
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.add([this.passBorrowVehicleContainer]);
	}
});
