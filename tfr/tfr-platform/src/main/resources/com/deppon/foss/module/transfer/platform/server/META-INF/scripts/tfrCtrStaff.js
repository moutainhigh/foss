Ext.define('Foss.platform.tfrCtrStaff.queryForm', {
			id : 'Foss_platform_tfrCtrStaff_queryForm_id',
			extend : 'Ext.form.Panel',
			title : '查询条件',
			frame : true,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4
			},
			layout : 'column',
			items : [{
						fieldLabel : '外场',
						name : 'transferCenter',
						xtype : 'dynamicorgcombselector',
						types : 'ORG',
						transferCenter : 'Y',
						readOnly : !Ext
								.isEmpty(platform.tfrCtrStaff.parentTfrCtrCode)
					}, {
						xtype : 'datefield',
						fieldLabel : '日期',
						name : 'queryDate',
						format : 'Y-m-d',
						value : new Date(new Date().getFullYear(), new Date()
										.getMonth(), new Date().getDate() - 1)
					}],
			buttons : [{
				xtype : 'button',
				columnWidth : .08,
				text : '重置',
				handler : function() {
					var form = this.up('form').getForm();
					form.reset();
					if (platform.tfrCtrStaff.parentTfrCtrCode) {
						form.findField('transferCenter').setCombValue(
								platform.tfrCtrStaff.parentTfrCtrName,
								platform.tfrCtrStaff.parentTfrCtrCode);
					}
				}
			}, '->', {
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : '查询',
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						platform.tfrCtrStaff.resultGrid.store.load();
					}
				}
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.platform.tfrCtrStaff.gridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'statisticDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'transferCenterCode',
						type : 'string'
					}, {
						name : 'transferCenterName',
						type : 'String'
					}, {
						name : 'totalQtyBudeget',
						type : 'string'
					}, {
						name : 'loaderBudget',
						type : 'string'
					}, {
						name : 'driverBudget',
						type : 'string'
					}, {
						name : 'totalQtyActual',
						type : 'string'
					}, {
						name : 'loaderActual',
						type : 'string'
					}, {
						name : 'driverActual',
						type : 'string'
					}, {
						name : 'totalRemainder',
						type : 'string'
					}, {
						name : 'loaderRemainder',
						type : 'string'
					}, {
						name : 'driverRemainder',
						type : 'string'
					}, {
						name : 'absenteeDayCnt',
						type : 'string'
					}, {
						name : 'absenteeAccumulatedCnt',
						type : 'string'
					}, {
						name : 'absentRate',
						type : 'string'
					}, {
						name : 'dimission',
						type : 'string'
					}, {
						name : 'dimissionAccumulated',
						type : 'string'
					}, {
						name : 'dimissionRate',
						type : 'string'
					}, {
						name : 'onDutyCnt',
						type : 'string'
					}, {
						name : 'noDutyCnt',
						type : 'string'
					}, {
						name : 'noDuty3DayCnt',
						type : 'string'
					}]
		});

Ext.define('Foss.platform.tfrCtrStaff.gridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.tfrCtrStaff.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryTfrCtrStaffDtos.action'),
		reader : {
			type : 'json',
			root : 'tfrCtrStaffVo.tfrCtrStaffDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = Ext
					.getCmp('Foss_platform_tfrCtrStaff_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'tfrCtrStaffVo.tfrCtrStaffQcDto.transferCenterCode' : queryForm
								.findField('transferCenter').getValue(),
						'tfrCtrStaffVo.tfrCtrStaffQcDto.queryDate' : queryParams.queryDate
					}
				});
			}
		}
	}
});

Ext.define('Foss.platform.tfrCtrStaff.resultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '外场人员情况',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : false,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.tfrCtrStaff.gridStore');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
				var form = Ext.getCmp('Foss_platform_tfrCtrStaff_queryForm_id')
						.getForm(), queryParams = form.getValues();

				if (!form.isValid()) {
					Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
					return;
				}

				var params = {
					'tfrCtrStaffVo.tfrCtrStaffQcDto.transferCenterCode' : form
							.findField('transferCenter').getValue(),
					'tfrCtrStaffVo.tfrCtrStaffQcDto.queryDate' : queryParams.queryDate
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
							url : platform
									.realPath('exportTfrCtrStaffDtos.action'),
							form : Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : params,
							isUpload : true,
							success : function(response) {

							},
							exception : function(response) {
								top.Ext.MessageBox
										.alert('导出失败', result.message);
							}
						});
			}
		}];
		me.columns = [{
					dataIndex : 'statisticDate',
					align : 'center',
					width : 110,
					text : '日期',
					xtype : 'datecolumn',
					format : 'Y-m-d'
				}, {
					dataIndex : 'transferCenterName',
					align : 'center',
					width : 120,
					text : '外场'
				}, {
					text : '预算人员',
					columns : [{
								dataIndex : 'totalQtyBudeget',
								align : 'center',
								text : '总人数',
								width : 50
							}, {
								dataIndex : 'loaderBudget',
								align : 'center',
								text : '理货员',
								width : 50
							}, {
								dataIndex : 'driverBudget',
								align : 'center',
								text : '叉车司机',
								width : 60
							}]
				}, {
					text : '现有人员',
					columns : [{
								dataIndex : 'totalQtyActual',
								align : 'center',
								text : '总人数',
								width : 50
							}, {
								dataIndex : 'loaderActual',
								align : 'center',
								text : '理货员',
								width : 50
							}, {
								dataIndex : 'driverActual',
								align : 'center',
								text : '叉车司机',
								width : 60
							}]
				}, {
					text : '缺口',
					columns : [{
								dataIndex : 'totalRemainder',
								align : 'center',
								text : '总人数',
								width : 50
							}, {
								dataIndex : 'loaderRemainder',
								align : 'center',
								text : '理货员',
								width : 50
							}, {
								dataIndex : 'driverRemainder',
								align : 'center',
								text : '叉车司机',
								width : 60
							}]
				}, {
					text : '异常人员',
					columns : [{
								dataIndex : 'absenteeDayCnt',
								align : 'center',
								text : '当日异常数',
								width : 80
							}, {
								dataIndex : 'absenteeAccumulatedCnt',
								align : 'center',
								text : '月累计异常数',
								width : 90
							}, {
								dataIndex : 'absentRate',
								align : 'center',
								text : '异常率',
								width : 50
							}]
				}, {
					text : '离职人员',
					columns : [{
								dataIndex : 'dimission',
								align : 'center',
								text : '当日离职数',
								width : 80
							}, {
								dataIndex : 'dimissionAccumulated',
								align : 'center',
								text : '月累计离职数',
								width : 90
							}, {
								dataIndex : 'dimissionRate',
								align : 'center',
								text : '离职率',
								width : 90
							}]
				}, {
					dataIndex : 'onDutyCnt',
					align : 'center',
					text : '当日出勤人数',
					width : 90
				}, {
					dataIndex : 'noDutyCnt',
					align : 'center',
					text : '当日未出勤人数',
					width : 110
				}, {
					dataIndex : 'noDuty3DayCnt',
					align : 'center',
					text : '月累计连续3日未出勤人数',
					width : 180,
					renderer : function(value, meta, record, rowIndex, celIndex) {

						var transferCenterCode = record
								.get('transferCenterCode'), statisticDate = record
								.get('statisticDate');

						var params = "'" + value + "','" + transferCenterCode
								+ "','" + statisticDate + "'";

						return '<a href="javascript:platform.tfrCtrStaff.noDuty3DayWindowShow('
								+ params + ')">' + value + '</a>';
					}
				}];
		me.callParent([cfg]);
		platform.tfrCtrStaff.resultGrid = me;
	}
});

platform.tfrCtrStaff.noDuty3DayWindowShow = function(value, transferCenterCode,
		queryDateStr) {

	var queryDate = new Date(queryDateStr)

	platform.tfrCtrStaff.transferCenterCode = transferCenterCode;
	platform.tfrCtrStaff.queryDate = queryDate;

	if (Ext.isEmpty(value) || value === '0') {
		Ext.ux.Toast.msg('提示', '无月累计连续3日未出勤人员！', 'error', 2000);
		return;
	}

	Ext.getCmp('T_platform-tfrCtrStaffIndex_content').getNoDuty3DayWindow(
			transferCenterCode, queryDate).show();
};

Ext.define('Foss.platform.tfrCtrStaff.noDuty3DayGridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'transferCenterCode',
						type : 'string'
					}, {
						name : 'transferCenterName',
						type : 'string'
					}, {
						name : 'orgName',
						type : 'string'
					}, {
						name : 'empCode',
						type : 'string'
					}, {
						name : 'empName',
						type : 'string'
					}, {
						name : 'postName',
						type : 'string'
					}, {
						name : 'statisticDate',
						type : 'date',
						convert : dateConvert
					}]
		});

Ext.define('Foss.platform.tfrCtrStaff.noDuty3DayGridStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.platform.tfrCtrStaff.noDuty3DayGridModel'
		});

Ext.define('Foss.platform.tfrCtrStaff.noDuty3DayGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '月累计连续3日未出勤人员名单',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : true,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	height : 560,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.tfrCtrStaff.noDuty3DayGridStore');
		me.tbar = ['->', {
			xtype : 'button',
			text : '导出',
			handler : function() {

				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '无月累计连续3日未出勤人员！', 'error', 2000);
					return;
				}

				var params = {
					'tfrCtrStaffVo.transferCenterCode' : platform.tfrCtrStaff.transferCenterCode,
					'tfrCtrStaffVo.queryDate' : platform.tfrCtrStaff.queryDate
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
							url : platform
									.realPath('exportTfrCtrStaff3DayNoDuty.action'),
							form : Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : params,
							isUpload : true,
							success : function(response) {

							},
							exception : function(response) {
								top.Ext.MessageBox
										.alert('导出失败', result.message);
							}
						});
			}
		}];
		me.columns = [{
					xtype : 'rownumberer',
					width : 50,
					sortable : false
				}, {
					dataIndex : 'transferCenterName',
					align : 'center',
					width : 120,
					text : '外场'
				}, {
					dataIndex : 'orgName',
					align : 'center',
					width : 120,
					text : '部门'
				}, {
					dataIndex : 'empCode',
					align : 'center',
					text : '工号',
					width : 85
				}, {
					dataIndex : 'empName',
					align : 'center',
					text : '姓名',
					width : 85
				}, {
					dataIndex : 'postName',
					align : 'center',
					text : '岗位',
					width : 120
				}, {
					dataIndex : 'statisticDate',
					align : 'center',
					width : 110,
					text : '新增时间',
					xtype : 'datecolumn',
					format : 'Y-m-d'
				}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrStaff.noDuty3DayWindow', {
			extend : 'Ext.window.Window',
			closable : true,
			modal : true,
			resizable : false,
			closeAction : 'hide',
			width : 800,
			height : 650,
			noDuty3DayGrid : null,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.noDuty3DayGrid = Ext
						.create('Foss.platform.tfrCtrStaff.noDuty3DayGrid');
				me.items = [me.noDuty3DayGrid];
				me.callParent([cfg]);
			}
		});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.platform.tfrCtrStaff.queryForm');
	var resultGrid = Ext.create('Foss.platform.tfrCtrStaff.resultGrid');

	var transferCenterField = queryForm.getForm().findField('transferCenter');
	transferCenterField.setCombValue(platform.tfrCtrStaff.parentTfrCtrName,
			platform.tfrCtrStaff.parentTfrCtrCode);

	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-tfrCtrStaffIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		noDuty3DayWindow : null,
		getNoDuty3DayWindow : function(transferCenterCode, queryDate) {
			var me = this;
			if (Ext.isEmpty(me.noDuty3DayWindow)) {
				me.noDuty3DayWindow = Ext
						.create('Foss.platform.tfrCtrStaff.noDuty3DayWindow');
			}
			platform.tfrCtrStaff.noDuty3DayWindow = me.noDuty3DayWindow;

			var noDuty3DayGrid = platform.tfrCtrStaff.noDuty3DayWindow.noDuty3DayGrid
			var params = {
				'tfrCtrStaffVo.transferCenterCode' : transferCenterCode,
				'tfrCtrStaffVo.queryDate' : queryDate
			};

			Ext.Ajax.request({
				url : platform.realPath('queryTfrCtrStaff3DayNoDuty.action'),
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					noDuty3DayGrid.store
							.loadData(result.tfrCtrStaffVo.tfrCtrStaffNoDutyEntities);
				}
			});
			return platform.tfrCtrStaff.noDuty3DayWindow;
		},
		items : [queryForm, resultGrid],
		renderTo : 'T_platform-tfrCtrStaffIndex-body'
	});
});