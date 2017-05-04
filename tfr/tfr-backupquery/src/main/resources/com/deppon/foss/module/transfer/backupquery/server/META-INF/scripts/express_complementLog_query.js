//326027
//审核状态
Ext.define('Foss.load.complementLog.Store',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
      	{"valueName": "全部", "valueCode": ""},
	    {"valueName": "基础资料匹配", "valueCode": "10"},
	    {"valueName": "地址库匹配", "valueCode": "9"},
	    {"valueName": "行政区域匹配", "valueCode": "1"},
	    {"valueName": "历史经验", "valueCode": "3"},
	    {"valueName": "百度经纬度匹配", "valueCode": "2"},
	    {"valueName": "人工补码", "valueCode": "5"}
		]
});
Ext.define('Foss.load.complementLog.queryForm', {
			id : 'Foss_load_complementLog_queryForm_id',
			extend : 'Ext.form.Panel',
			title : '查询条件',
			frame : true,
			collapsible : true,
			animCollapse : true,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 0.25,
				xtype : 'textfield'
			},
			layout : 'column',
			items : [{
						fieldLabel : '运单号',
						vtype : 'waybill',
						name : 'waybillNo'
					}, {
						fieldLabel : '补码前部门',
						name : 'beforeOrgCode',
						//labelWidth : 110,
						//width : 300,
						xtype : 'commonLdpAndExpressAndOrgSelector'
					}, {
						fieldLabel : '补码后部门',
						name : 'afterOrgCode',
						//labelWidth : 110,
						//width : 300,
						xtype : 'commonExpressAndOrgSelector',
						active : 'Y',
						typeParam : 'ORG,LDP'
					}, {//326027
						fieldLabel : '操作人工号',
						name : 'operatorCode'
						//labelWidth : 110,
						//width : 300
					},{
						//326027
						fieldLabel : '出发城市',
						name : 'departCityCode',
						//labelWidth : 110,
						//width : 300,
						colspan : 4,
						//allowBlank : false,
						xtype : 'commoncityselector',
						forceSelection : true	
					},{
						xtype : 'rangeDateField',
						fieldLabel : '补码时间',
						columnWidth :0.5,
						fieldId : 'Foss_complementLog_QueryForm_operateTime_ID',
						dateType : 'datetimefield_date97',
						dateRange :7,
						fromName : 'beginOperateTime',
						fromValue : Ext.Date.format(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate()-6, 0, 0, 0),
								'Y-m-d H:i:s'),
						toValue : Ext.Date.format(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate(), 23, 59, 59),
								'Y-m-d H:i:s'),
						toName : 'endOperateTime',
						allowBlank : false,
						disallowBlank : true
					}, {//326027
						fieldLabel : '匹配模式',
						name : 'matchType',
						xtype : 'combo',
						displayField: 'valueName',
						valueField:'valueCode',
						columnWidth:.25,
						value : '',
						queryMode:'local',
						triggerAction:'all',
						editable:false,
						store:Ext.create('Foss.load.complementLog.Store')
					}],
					
			buttons : [{
				xtype : 'button',
				columnWidth : .08,
				text : '重置',
				handler : function() {
					var form = this.up('form').getForm();
					form.reset();
					this.up('form').getForm().findField('beginOperateTime')
							.setValue(Ext.Date.format(new Date(new Date()
															.getFullYear(),
													new Date().getMonth(),
													new Date().getDate()-6, 0, 0,
													0), 'Y-m-d H:i:s'));
					this.up('form').getForm().findField('endOperateTime')
							.setValue(Ext.Date.format(new Date(new Date()
															.getFullYear(),
													new Date().getMonth(),
													new Date().getDate(), 23,
													59, 59), 'Y-m-d H:i:s'));
				}
			}, '->', {
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : '查询',
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						backupquery.complementLog.pagingBar.moveFirst();
					}
				}
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.load.complementLog.complementLogModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'beforeOrgCode',
						type : 'string'
					}, {
						name : 'beforeOrgName',
						type : 'string'
					}, {
						name : 'afterOrgCode',
						type : 'string'
					}, {
						name : 'afterOrgName',
						type : 'string'
					}, {
						name : 'operatorName',
						type : 'string'
					}, {
						name : 'operatorCode',
						type : 'string'
					}, {
						name : 'address',
						type : 'string'
					}, {
						name : 'operationOrgName',
						type : 'string'
					}, {
						name : 'operationOrgCode',
						type : 'string'
					}, {
						name : 'operationTime',
						type : 'date',
						convert : dateConvert
					},{//326027
						name : 'departCityName',
						type : 'string'
					},{
						name : 'departCityCode',
						type : 'string'
					},{//匹配模式
						name : 'matchType',
						type : 'string',
						convert : function(value) {
								if (value==1) {
									return '行政区域匹配';
								}else if(value==2) {
									return '百度经纬度匹配';
								}else if(value==3) {
									return '历史经验';
								}else if(value==5) {
									return '人工补码';
								}else if(value==9) {
									return '地址库匹配';
								}else if(value==10) {
									return '基础资料匹配';
								}
						}
						
					}]
		});

Ext.define('Foss.load.complementLog.complementLogStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.load.complementLog.complementLogModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : backupquery.realPath('queryComplementLogList.action'),
		reader : {
			type : 'json',
			root : 'complementVo.complementLogEntityList',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_load_complementLog_queryForm_id')
					.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'complementVo.complementLogDto.waybillNo' : queryParams.waybillNo,
						'complementVo.complementLogDto.operationOrgCode' : queryParams.operationOrgCode,
						'complementVo.complementLogDto.beforeOrgCode' : queryParams.beforeOrgCode,
						'complementVo.complementLogDto.afterOrgCode' : queryParams.afterOrgCode,
						'complementVo.complementLogDto.beginOperateTime' : queryParams.beginOperateTime,
						'complementVo.complementLogDto.endOperateTime' : queryParams.endOperateTime,
						'complementVo.complementLogDto.operatorCode' : queryParams.operatorCode,
						//326027
						'complementVo.complementLogDto.departCityCode' : queryParams.departCityCode,
						'complementVo.complementLogDto.matchType' : queryParams.matchType,
					}
				});
			}
		}
	}
});

Ext.define('Foss.load.complementLog.complementLogGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			columnLines : true,
			title : '补码列表',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			emptyText : '查询结果为空',
			autoScroll : true,
			collapsible : true,
			animCollapse : true,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config)
				me.store = Ext
						.create('Foss.load.complementLog.complementLogStore');
				me.bbar = Ext.create('Deppon.StandardPaging', {
							store : me.store,
							pageSize : 20,
							maximumSize : 50,
							plugins : Ext.create('Deppon.ux.PageSizePlugin', {
										sizeList : [['20', 20], ['30', 30],
												['40', 40], ['50', 50]]
									})
						});
				backupquery.complementLog.pagingBar = me.bbar;
				me.callParent([cfg]);
			},
			columns : [{
						dataIndex : 'waybillNo',
						align : 'center',
						width : 100,
						xtype : 'ellipsiscolumn',
						text : '运单号'
					},{//326027
						text : '出发城市',
						dataIndex : 'departCityName',
						width : 180,
						align : 'center'
					}, {
						dataIndex : 'beforeOrgName',
						align : 'center',
						width : 180,
						text : '补码前部门'
					}, {
						dataIndex : 'afterOrgName',
						align : 'center',
						width : 180,
						text : '补码后部门'
					}, {
						dataIndex : 'operatorName',
						align : 'center',
						width : 85,
						text : '操作人名称'
					}, {
						dataIndex : 'address',
						align : 'center',
						width : 200,
						text : '目的地址'
					},{//326027
						text : '匹配模式',
						dataIndex : 'matchType',
						width : 180,
						align : 'center'
					}, {
						dataIndex : 'operatorCode',
						align : 'center',
						width : 85,
						text : '操作人工号'
					}, {
						dataIndex : 'operationOrgName',
						align : 'center',
						width : 180,
						text : '操作部门名称'
					}, {
						dataIndex : 'operationTime',
						align : 'center',
						width : 150,
						text : '补码时间',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s'
					}]
		});

Ext.onReady(function() {
			var queryForm = Ext.create('Foss.load.complementLog.queryForm');
			var queryResult = Ext
					.create('Foss.load.complementLog.complementLogGrid');
			Ext.create('Ext.panel.Panel', {
						id : 'T_backupquery-complementLogIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						layout : 'auto',
						items : [queryForm, queryResult],
						renderTo : 'T_backupquery-complementLogIndex-body'
					});
		});