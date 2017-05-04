/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义"外请车白名单"列表查询条件窗口 ***************************************************/
baseinfo.leasedVehicleWhitelists.DEFLAULT_TIME_INTEVAL= 31;
/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
baseinfo.leasedVehicleWhitelists.getTargetDate=function(date, day) {
	var d, s, t, t2;
  	var MinMilli = 1000 * 60;
  	var HrMilli = MinMilli * 60;
  	var DyMilli = HrMilli * 24;
  	t = Date.parse(date);
  	t2 =  new Date(t+day*DyMilli);
  	return t2;
};


/**
 * 时间格式化
 *@param value 要格式化的时间
 *@format 格式化方式如  Y-m-d;Y-m-d H:i:s
 */
baseinfo.leasedVehicleWhitelists.dateFormat=function(value,format){
	if(!Ext.isEmpty(value)){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}
	
}

/**
 * @param {} 小的日期
 * @param {} 大的日期
 * 比较两个日期差额
 */
baseinfo.leasedVehicleWhitelists.compareTwoDate = function(startDate,endDate){
  var startDateClone = Ext.Date.clone(new Date(startDate));
  var endDateClone =  Ext.Date.clone(new Date(endDate));
  startDateClone.setHours(0);
  startDateClone.setMinutes(0);
  startDateClone.setSeconds(0);
  startDateClone.setMilliseconds(0);
  
  endDateClone.setHours(0);
  endDateClone.setMinutes(0);
  endDateClone.setSeconds(0);
  endDateClone.setMilliseconds(0);
  
  var d, s, t, t2;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  
  d = new Date();
  t = Date.parse(startDateClone);
  t2= Date.parse(endDateClone); 
  s = Math.round((t2-t)/ DyMilli)+1;
  
  return s;
};

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.queryCondition'),
	id : 'Foss_baseinfo_leasedVehicleWhitelists_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'column',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 280,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			xtype : 'datetimefield_date97',
			id:'FOSS_Baseinfo_LeasedVehicleWhitelists_StartDate_ID',
			name : 'beginDate',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.startDate'),
			value : baseinfo.leasedVehicleWhitelists.dateFormat(
						baseinfo.leasedVehicleWhitelists.getTargetDate(
								new Date(),-baseinfo.leasedVehicleWhitelists.DEFLAULT_TIME_INTEVAL),'Y-m-d'),
			time:false,
			dateConfig: {
				el: 'FOSS_Baseinfo_LeasedVehicleWhitelists_StartDate_ID-inputEl',
				dateFmt: 'yyyy-MM-dd'
			},
			editable:false,
			labelWidth:85,
			columnWidth : .33
		},{
			xtype : 'datetimefield_date97',
			id:'FOSS_Baseinfo_LeasedVehicleWhitelists_EndDate_ID',
			name : 'endDate',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.endDate'),
			value :baseinfo.leasedVehicleWhitelists.dateFormat(new Date(),'Y-m-d'),
			time:false,
			dateConfig: {
				el: 'FOSS_Baseinfo_LeasedVehicleWhitelists_EndDate_ID-inputEl',
				dateFmt: 'yyyy-MM-dd'
			},
			editable:false,
			labelWidth:85,
			columnWidth : .33
		},{
			xtype : 'commonemployeeselector',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.applyUser'),
			name : 'applyUser',
			labelWidth:85,
			columnWidth : .33	
		},{
			xtype : 'dynamicorgcombselector',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.applyOrg'),
			name : 'applyOrg',
			labelWidth:85,
			columnWidth : .33	
		},{
			xtype : 'combobox',
			name : 'currentApplication',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.currentApply'),
			columnWidth : .33,
			labelWidth:85,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : 'all',
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', [{
				'valueCode' : 'all',
				'valueName' : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.selectAll')
			}, {
				'valueCode' : 'null',
				'valueName' : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.selectNull')
			}])
		},{
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.writeListStatus'),
			columnWidth : .33,
			labelWidth:85,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : '',
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.selectAll')
			})
		},{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleNo'),
			labelWidth:85,
			columnWidth : .33
		}, {
			border : false,
			xtype : 'container',
			columnWidth : 1,
			html : '&nbsp;'
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.resetBtn'),
			disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsQueryButton'),
			hidden:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedVehicleWhitelists_QueryForm_Id').getForm().reset();
			}
		}, {
			border : false,
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.queryBtn'),
			disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsQueryButton'),
			hidden:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedVehicleWhitelists_LeasedVehicleWhitelistsGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"车白名单"列表结果窗口 *****************************************************/
/*
 * 定义：一个"车白名单"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen: 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id',//ID标识
			type : 'string'
		}, {
			name : 'vehicleNo', //车牌号
			type : 'string'
		}, {
			name : 'contact', //所属人/组织
			type : 'string'
		},{
			name : 'vehicleType', //车辆类型
			type : 'string'
		}, {
			name : 'drivingLicense', //行驶证
			type : 'string'
		}, {
			name : 'operatingLic', //营运证
			type : 'string'
		}, {
			name : 'endTimeDrivingLic', //行驶证到期日期
			type : 'date',
			convert : function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);
					return date;
				}
			}
		}, {
			name : 'endTimeOperatingLic', //营运证到期日期
			type : 'date',
			convert : function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);
					return date;
				}
			}
		}, {
			name : 'insureCard', //保险卡
			type : 'string'
		}, {
			name : 'contact', //车主姓名
			type : 'string'
		}, {
			name : 'contactPhone', //车主联系方式
			type : 'string'
		}, {
			name : 'status', //白名单状态
			type : 'string'
		}, {
			name : 'currentApplication', //当前申请
			type : 'string'
		}, {
			name : 'applyUser', //申请人工号
			type : 'string'
		}, {
			name : 'applyUserName', //申请人名称
			type : 'string'
		}, {
			name : 'applyTime', //申请时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'approveUser', //审核人
			type : 'string'
		}, {
			name : 'approveTime', //审核时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'applyNotes', //申请备注
			type : 'string'
		}, {
			name : 'auditNotes', //审核备注
			type : 'string'
		}
	]
});

/*
 * 定义：一个"车白名单"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedVehicleWhitelistsList.action'),
		reader : {
			type : 'json',
			root : 'leasedWhitelistVo.whitelistAuditList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedVehicleWhitelists_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				
				//时间判断
				if(queryParams.beginDate!=null 
						&& queryParams.beginDate!='' 
						&& queryParams.endDate!=null	
						&& queryParams.endDate!='' 	
					){
					if(baseinfo.leasedVehicleWhitelists.compareTwoDate(queryParams.beginDate,queryParams.endDate)<1){
//						Ext.Msg.alert(baseinfo.leasedVehicleWhitelists.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.EndDateNotBeforeBeginDate'));
//						return false;
						Ext.MessageBox.show({
							title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
							msg : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.EndDateNotBeforeBeginDate'),
							width : 300,
							buttons : Ext.Msg.OK,
							callback : function () {
								me.getApplyForm().up('window').hide();
							},
							icon : Ext.MessageBox.WARNING
						});
						return false;
					}	
				}
				
				Ext.apply(operation, {
					params : {
						'leasedWhitelistVo.whitelistAuditQueryDto.vehicleNo' : queryParams.vehicleNo,
						'leasedWhitelistVo.whitelistAuditQueryDto.currentApplication' : queryParams.currentApplication,
						'leasedWhitelistVo.whitelistAuditQueryDto.status' : queryParams.status,
						'leasedWhitelistVo.whitelistAuditQueryDto.beginDate' : queryParams.beginDate,
						'leasedWhitelistVo.whitelistAuditQueryDto.endDate' : queryParams.endDate,
						'leasedWhitelistVo.whitelistAuditQueryDto.applyUser' : queryParams.applyUser,
						'leasedWhitelistVo.whitelistAuditQueryDto.applyOrg' : queryParams.applyOrg
					}
				});
			}
		}
	}
});

/*
 * 定义："车白名单"内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsDetailForm', {
	extend : 'Ext.form.Panel',
	height : 305,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleNo'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleType'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_leasedVehicleWhitelists_VehicleTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.drivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.operatingLic'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeDrivingLic'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeOperatingLic'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'insureCard',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.insureCard'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			name : 'contact',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.contact'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.contactPhone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.writeListStatus'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			readOnly : true,
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.currentApply'),
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			name : 'applyUser',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyUser'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'applyTime',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyTime'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'approveUser',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.approveUser'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'approveTime',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.approveTime'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
			
		}, {
			name : 'auditNotes',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.auditNotes'),
			readOnly : true,
			columnWidth : 0.5,
			colspan : 2,
			width : 550
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.leasedVehicleWhitelistsDetail'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请车白名单详细信息
	 * @return {Object} LeasedVehicleWhitelistsDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedVehicleWhitelists_LeasedVehicleWhitelistsGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyVehicleInfo'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsDetailPanel' // 行体内容
		}
	],
	applyWindow : null,
	/**
	 * 获取"申请入库、申请可用、不可用"的窗口的对象
	 * @param {String} availableTitle 窗口的标题
	 * @param {Boolean} isNewApply true：申请入库，false：申请可用/不可用
	 * @param {String} actionType apply/available/unavailable
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getApplyWindow : function (availableTitle, isNewApply, actionType) {
		var me = this;
		if (this.applyWindow == null) {
			this.applyWindow = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsApplyWindow');
		}
		this.applyWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'apply') {
					operationUrl = baseinfo.realPath('applyLeasedVehicleWhitelists.action');
				} else if (actionType === 'available') {
					operationUrl = baseinfo.realPath('availableLeasedVehicleWhitelists.action');
				} else if (actionType === 'unavailable') {
					operationUrl = baseinfo.realPath('unAvailableLeasedVehicleWhitelists.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.applyWindow.getApplyForm().setTitle(availableTitle);
		if (Ext.isBoolean(isNewApply) && isNewApply) {
			this.applyWindow.getApplyForm().getForm().reset();
			this.applyWindow.getApplyForm().getForm().findField('vehicleNo').setReadOnly(!isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('status').hide().setDisabled(isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('currentApplication').hide().setDisabled(isNewApply);
			this.applyWindow.getButtons(1).setVisible(isNewApply); //重置按钮可用
			this.applyWindow.getButtons(0).setPosition(0);
			this.applyWindow.getButtons(2).setPosition(0);
		} else {
			this.applyWindow.getApplyForm().getForm().findField('vehicleNo').setReadOnly(!isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('status').show().setDisabled(isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('currentApplication').show().setDisabled(isNewApply);
			this.applyWindow.getButtons(1).setVisible(isNewApply); //重置按钮不可用
			this.applyWindow.getButtons(0).setPosition(50);
			this.applyWindow.getButtons(2).setPosition(50);
		}
		return this.applyWindow;
	},
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.operator'),
			align : 'center',
			items : [{
					iconCls : 'foss_icons_bse_applyReturn',
					tooltip : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyReturn'),//撤回
					disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsCancelButton'),
					getClass : function (v, m, r, rowIndex) {
						if (r.get('currentApplication') === '') {
							return 'statementBill_hide';
						} else {
							return 'foss_icons_bse_applyReturn';
						}
					},
					/**
					 * "撤回"响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.Msg.show({
							title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.confirmPrompt'),
							msg : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyReturnLeaseWhiteListAfterNotResum'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('withdrawLeasedVehicleWhitelists.action'),
										jsonData : {
											'leasedWhitelistVo' : {
												'whitelistAudit' : record.data
											}
										},
										//"撤回"成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//"撤回"失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
												msg : json.message,
												width : 450,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									});
								}
							}
						});
					}
				}, {
					iconCls : 'foss_icons_bse_applyUsable',
					tooltip : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyUsable'),//申请可用
					disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsAvailableButton'),
					getClass : function (v, m, r, rowIndex) {
						if (Ext.isEmpty(r.get('currentApplication')) && r.get('status') === 'audit_unavailable') {
							return 'foss_icons_bse_applyUsable';
						} else {
							return 'statementBill_hide';
						}
					},
					// "申请可用"响应事件
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var model = new Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsModel(record.data);
						model.set('applyNotes', '');
						model.set('currentApplication', FossDataDictionary.rendererSubmitToDisplay('apply_available', 'BES_WHITELISTS_APPLY'));
						var applyWindow = grid.up().getApplyWindow(item.tooltip, false, 'available');
						applyWindow.getApplyForm().getForm().loadRecord(model);
						applyWindow.show();
						model = null;
					}
				}, {
					iconCls : 'foss_icons_bse_applyDisabled',
					tooltip : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyDisabled'),//申请不可用
					disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsUnavailableButton'),
					getClass : function (v, m, r, rowIndex) {
						if (Ext.isEmpty(r.get('currentApplication')) && r.get('status') === 'audit_available') {
							return 'foss_icons_bse_applyDisabled';
						} else {
							return 'statementBill_hide';
						}
					},
					// "申请不可用"响应事件
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var model = new Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsModel(record.data);
						model.set('applyNotes', '');
						model.set('currentApplication', FossDataDictionary.rendererSubmitToDisplay('apply_unavailable', 'BES_WHITELISTS_APPLY'));
						var applyWindow = grid.up().getApplyWindow(item.tooltip, false, 'unavailable');
						applyWindow.getApplyForm().getForm().loadRecord(model);
						applyWindow.show();
						model = null;
					}
				}
			]
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleNo'),
			dataIndex : 'vehicleNo',
			width : 110
		},{
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.contact'),
			dataIndex : 'contact',
			width : 110
		},
		{
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleType'),
			dataIndex : 'vehicleType',
			flex : 1,
			width : 110,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_VEHICLE_TYPE');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.writeListStatus'),
			dataIndex : 'status',
			width : 90,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_AUDIT');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.currentApply'),
			dataIndex : 'currentApplication',
			width : 85,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_APPLY');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyNotes'),
			dataIndex : 'applyNotes',
			width : 170
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyUser'),
			dataIndex : 'applyUserName',
			flex : 1,
			width : 110
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyTime'),
			dataIndex : 'applyTime',
			width : 115,
			renderer : function (value) {
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeOperatingLic'),
			dataIndex : 'endTimeOperatingLic',
			width : 115,
			renderer : function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d');
				}else{
					return null;
				}
			}
		}, {
			header : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeDrivingLic'),
			dataIndex : 'endTimeDrivingLic',
			width : 115,
			renderer : function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d');
				}else{
					return null;
				}
			}
		}
	],
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : {
					xtype : 'button',
					text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyStore'),//申请入库
					disabled:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsAddButton'),
					hidden:!baseinfo.leasedVehicleWhitelists.isPermission('leasedVehicleWhitelists/carWhitelistsAddButton'),
					width : 100,
					//弹出申请入库新的外请车的窗口
					handler : function () {
						me.getApplyWindow(this.text, true, 'apply').show();
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义"车白名单"申请窗口 *****************************************************/
/*
 * 定义："车白名单"申请入库、申请可用、申请不可用的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsApplyForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
			xtype : 'commonleasedvehicleselector',
			status : 'N',
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleNo'),
			allowBlank : false,
			blankText : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.vehicleNoIsNotNull'),
			readOnly : false,
			columnWidth : 0.5,
			validateOnBlur : true,
			queryAllFlag:false,
			validateOnChange : true,
			validator : function (val) {
				var me = this;
				if (Ext.isEmpty(val)) {
					me.validatorMessage.message = me.blankText;
					return true;
				} else {
					if (!Ext.isEmpty(me.validatorMessage.validity) && me.validatorMessage.validity) {
						return me.validatorMessage.validity;
					} else {
						return me.validatorMessage.message;
					}
				}
			},
			validatorMessage : {
				validity : true,
				message : ''
			},
			listeners : {
				/**
				 * 模拟选择器：失去焦点获取外请车信息
				 * @param {Ext.Component} me 当前对象
				 * @param {Ext.EventObject} event 当前触发事件对象
				 */
				select : function (me, event) {
					if (Ext.isEmpty(me.getValue())) {
						return;
					}
					Ext.Ajax.request({
						url : baseinfo.realPath('queryLeasedVehicleWhitelists.action'),
						jsonData : {
							'leasedWhitelistVo' : {
								'whitelistAudit' : {
									'vehicleNo' : me.getValue()
								}
							}
						},
						success : function (response) {
							//检测"外请车白名单"是否已经存在
							var whitelistAudit = Ext.decode(response.responseText).leasedWhitelistVo.whitelistAudit;
							if (Ext.isEmpty(whitelistAudit)) {
								if (Ext.isEmpty(me.getValue())) {
									return;
								}
								Ext.Ajax.request({
									url : baseinfo.realPath('queryLeasedVehicle.action'),
									jsonData : {
										'leasedVehicleVo' : {
											'leasedVehicle' : {
												'vehicleNo' : me.getValue()
											}
										}
									},
									success : function (response) {
										//刷新"外请车"信息到申请入库窗口
										var leasedVehicle = Ext.decode(response.responseText).leasedVehicleVo.leasedVehicle;
										var model = new Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsModel(leasedVehicle);
										model.set('vehicleNo', me.getValue());
										model.set('applyTime', null);
										model.set('approveTime', null);
										me.up().getForm().loadRecord(model);
										if (Ext.isEmpty(leasedVehicle)) {
											me.validatorMessage.validity = false;
											me.validatorMessage.message = baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.leaseVehicleNotExist');
											me.markInvalid(me.validatorMessage.message);
											//me.setActiveError(me.validatorMessage.message);
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
												msg : me.validatorMessage.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										} else {
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
											//me.unsetActiveError();
											me.clearInvalid();
										}
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										me.validatorMessage.validity = false;
										me.validatorMessage.message = json.message;
										//me.setActiveError(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
											msg : me.validatorMessage.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
								});
							} else {
								var currentApplication = null;
								//当前申请
								if(whitelistAudit.currentApplication === 'apply_apply'){
									currentApplication = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.curApplyInStore')+'</li>';
								}else if(whitelistAudit.currentApplication === 'apply_available'){
									currentApplication = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.curApplyCanUse')+'</li>';
								}else if(whitelistAudit.currentApplication === 'apply_unavailable'){
									currentApplication = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.curApplyNotUse')+'</li>';
								}else{
									currentApplication = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.curApplyNone')+'</li>';
								}
								var status = null;
								//白名单状态
								if(whitelistAudit.status === 'audit_apply'){
									status = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.whiteListStatusNoInStore')+'</li>';
								}else if(whitelistAudit.status === 'audit_available'){
									status = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.whiteListStatusCanUse')+'</li>';
								}else if(whitelistAudit.status === 'audit_unavailable'){
									status = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.whiteListStatusNoUse')+'</li>';
								}else{
									status = '<li>' + baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.whiteListStatusNone')+'</li>';
								}
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.easeVehicleWhiteListExist') + "<ui>" + currentApplication + status + "</ui>";
								me.markInvalid(me.validatorMessage.message);
								currentApplication = null;
								status = null;
								//me.setActiveError(me.validatorMessage.message);
								Ext.MessageBox.show({
									title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
									msg : me.validatorMessage.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							me.validatorMessage.validity = false;
							me.validatorMessage.message = json.message;
							//me.setActiveError(me.validatorMessage.message);
							Ext.MessageBox.show({
								title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
								msg : me.validatorMessage.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
				},
				blur : function (me, eOpts) {
					if(Ext.isEmpty(me.getValue())){
						me.setValue(null);
					}else{
						var value = me.getValue();
						me.up('window').getApplyForm().getForm().reset();
						me.store.removeAll(false);
						me.setRawValue(value);
						me.setValue(value);
						me.store.load({
							scope : me,
							params : {
								start : 0,
								limit : 1,
								'leasedVehicleVo.leasedVehicle.vehicleNo' : value
							},
							callback : function (rs) {
								me.fireEvent('select', me, eOpts);
							}
						});
					}
				}
			}
		}, {
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.vehicleType'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_leasedVehicleWhitelists_VehicleTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.drivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.operatingLic'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeDrivingLic'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d H:i:s',
		
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.endTimeOperatingLic'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'insureCard',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.insureCard'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			name : 'contact',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.contact'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.contactPhone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.writeListStatus'),
			hidden : true,
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			hidden : true,
			readOnly : true,
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.currentApply'),
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'textareafield',
			name : 'applyNotes',
			readOnly : false,
			fieldLabel : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.applyNotes'),
			colspan : 2,
			maxlength : 1000,
			width : 560
		}, {
			name : 'id',
			fieldLabel : 'ID',
			readOnly : true,
			hidden : true,
			columnWidth : 0.5,
			colspan : 2
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义"车白名单"申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsApplyWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVehicleWhitelists.easeVehicleWhiteList'),
	width : 650,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	applyForm : null,
	getApplyForm : function () {
		if (null == this.applyForm) {
			this.applyForm = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsApplyForm');
		}
		return this.applyForm;
	},
	buttons : null,
	getButtons : function (index) {
		this.buttons = new Array();
		if (Ext.isEmpty(this.buttons)) {
			Ext.Array.include(this.buttons, this.items.items[1].items.items[0]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[2]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[4]);
		}
		if (arguments.length === 0) {
			return this.buttons;
		} else {
			return this.buttons[index];
		}
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getApplyForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 0 155',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.cancel'),
						handler : function () {
							me.hide();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.vehicle.resetBtn'),
						handler : function () {
							me.getApplyForm().getForm().reset();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.save'),
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var applyForm = me.getApplyForm().getForm();
								if (applyForm.isValid()) { //校验FORM是否通过校验
									var applyNotes = applyForm.findField('applyNotes').getValue();
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'leasedWhitelistVo' : {
												'whitelistAudit' : applyForm.getRecord().data,
												'comment' : applyNotes
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-leasedVehicleWhitelists_content').getLeasedVehicleWhitelistsGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getApplyForm().up('window').hide();
												},
												icon : Ext.MessageBox.INFO
											});
											applyForm = null;
										},
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getApplyForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											applyForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedVehicleWhitelists.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : applyForm.findField('vehicleNo').validatorMessage.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									applyForm = null;
								}
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeshow : function(me, eOpts){
			var fielsds = me.getApplyForm().getForm().getFields();
			if(!Ext.isEmpty(fielsds)){
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.QueryForm');
	/*
	 * 创建查询"车白名单"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedVehicleWhitelists.LeasedVehicleWhitelistsGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedVehicleWhitelists').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedVehicleWhitelists_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"车白名单"结果列表结果窗口
		getLeasedVehicleWhitelistsGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedVehicleWhitelists-body'
	}));
});

