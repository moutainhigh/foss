
//定义查询条件的panel

Ext.define('Foss.platform.onLinePDADetail.onLinePDADetailForm', {
	extend : 'Ext.form.Panel',
	title: platform.onLinePDADetail.i18n('Foss.platform.queryPDAOnlineUsing.title'),//查询条件
	id : 'Foss.platform.onLinePDADetail.onLinePDADetailForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 1
	},
	defaults : {
		labelWidth : 120,
		width : 316,
		margin : '8 10 5 10',
		anchor : '100%'
	},
	//height : 245,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		    id:'Foss.platform.onLinePDADetail.transferCenterCode.id',
		    xtype:'commontransfercenterselector',
			fieldLabel:platform.onLinePDADetail.i18n('Foss.platform.queryPDAOnlineUsing.label.transferCenterCode'), //外场
			name:'orgCode'
		  },{
			  id:'Foss.platform.onLinePDADetail.queryDate.id',
			  xtype:'datefield',
		      fieldLabel:platform.onLinePDADetail.i18n('Foss.platform.queryPDAOnlineUsing.label.queryDate'),  //查询日期
		      allowBlank:false,
			  name:'queryDate',
			  editable:false,
			  value: login.currentServerTime,
			  maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -0),
			  format:'Y-m-d'
		 }],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				xtype : 'button',
				width : 70,
				text:platform.onLinePDADetail.i18n('Foss.platform.queryPDAOnlineUsing.button.reset'),  //重置
				//hidden:!baseinfo.ownVehicle.isPermission('platform/queryPDAOnlineUsingRestButton'),
				margin : '8 800 5 0',
				handler : function () {
					var form = this.up('form').getForm();
				    form.findField('queryDate').setValue(Ext.Date.format(login.currentServerTime,'Y-m-d'));
					
				}
			}, {
				xtype : 'button',
				width : 70,
				text:platform.onLinePDADetail.i18n('Foss.platform.queryPDAOnlineUsing.button.search'),  //查询
				//hidden:!platform.onLinePDADetail.isPermission('platform/queryPDAOnlineUsingQueryButton'),
				cls : 'yellow_button',
				handler : function () {
					if (me.getForm().isValid()) {
						
						var form = this.up('form').getForm();
						var grid=platform.onLinePDADetail.queryResult;
						if(!form.isValid()){
							return;
						}
						var store=grid.getStore( );
						//清除掉之前的数据
						store.removeAll(false);
					    platform.onLinePDADetail.queryDate=Ext.getCmp('Foss.platform.onLinePDADetail.queryDate.id').getValue();
						platform.onLinePDADetail.pagingBar.moveFirst();
						
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});


//定义查询pda在线使用的model

Ext.define('Foss.platform.onLinePDADetail.queryPDAOnlineMothModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'hqCode',//本部编码
				type : 'string'
			}, {
				name : 'hqName',//本部名称
				type : 'string'
			}, {
				name : 'transferCenterCode',//外场code
				type : 'string'
			}, {
				name : 'transferCenterName',//外场name
				type : 'string'
			}, {
				name : 'staDate',//统计日期
				type : 'string'
			},{
				name : 'clerkUesdPDACountDay',//日理货员使用PDA台数
				type : 'string'
			}, {
				name : 'clerkUesdPDAOlineTimeDay',//日理货员使用PDA在线最高峰时间
				type : 'string'
			}, {
				name : 'forkUesdPDACountDay',//日电叉车使用PDA台数
				type : 'string'
			}, {
				name : 'forkUesdPDAOlineTimeDay',//日电叉车使用PDA在线最高峰时间
				type : 'string'
			},{
				name : 'allUesdPDACountDay',//日所有人员使用PDA台数
				type : 'string'
			}, {
				name : 'allUesdPDAOlineTimeDay',//日所有人员使用PDA最高峰时间
				type : 'string'
			}
		 ]
});
//查询pda在线使用的store
Ext.define('Foss.platform.onLinePDADetail.onLinePDADetailStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	// 绑定一个模型
	model : 'Foss.platform.onLinePDADetail.queryPDAOnlineMothModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		//Ext.BLANK_IMAGE_URL='sendRateDayQuery.action',
		//Ext.Ajax.timeout=300000,
		url : platform.realPath('queryPDAUsingDetail.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryPDAOnlineUsingVo.pdaOnlineUsingDtoList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			
				Ext.apply(operation, {
					params : {
						'queryPDAOnlineUsingVo.orgCode':platform.onLinePDADetail.transferCenterCode,
						'queryPDAOnlineUsingVo.queryDate':platform.onLinePDADetail.queryDate
					}
				});	
		}
	},
			
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日派送率 列表
Ext.define('Foss.platform.onLinePDADetail.onLinePDADetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	//columnLines: true,
	title : 'PDA在线使用情况明细',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.onLinePDADetail.onLinePDADetailStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 10,
			maximumSize : 30,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['30', 30]]
			})
		});
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
			
				// 获取查询参数
				params = { 'queryPDAOnlineUsingVo.orgCode':platform.onLinePDADetail.transferCenterCode,
						   'queryPDAOnlineUsingVo.queryDate':platform.onLinePDADetail.queryDate};
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportPDAUsingDetail.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败'/* '导出失败' */,
								result.message);
					}
				});
			}
		}]
	platform.onLinePDADetail.pagingBar=me.bbar;
	me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'transferCenterName',
		align : 'center',
		text:'转运场',
		width:150
	},{
		dataIndex : 'staDate',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期',
		width:150
	}, {
		text : '当日使用最高峰',
		columns : [{
			text : '理货员',
			columns:[{
					dataIndex : 'clerkUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:100
			},{
			
			        dataIndex : 'clerkUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:120
			}]
			
		},{
			text : '电叉司机',
			columns:[{
					dataIndex : 'forkUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:100
			},{
			
			        dataIndex : 'forkUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:120
			}]
		},{
			text : '所有人员',
			columns:[{
					dataIndex : 'allUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:100
					
			},{
			
			        dataIndex : 'allUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:120
			}]
		}]
	}]
});

// 创建查询form
platform.onLinePDADetail.queryPDAForm = Ext.create('Foss.platform.onLinePDADetail.onLinePDADetailForm');
// 创建查询grid
platform.onLinePDADetail.queryResult = Ext.create('Foss.platform.onLinePDADetail.onLinePDADetailGrid');
//入口函数
Ext.onReady(function() {
	
		platform.onLinePDADetail.transferCenterCode=platform.queryPDAOnlineUsing.transferCenterCode;//外场编码

	
	        //去获取外场编码名称
	        Ext.Ajax.request({
				url : platform.realPath('queryTransferCenterNameByCode.action'),
				params: {
						'queryPDAOnlineUsingVo.orgCode':platform.onLinePDADetail.transferCenterCode
					},
				success : function(response) {
					var json = Ext.decode(response.responseText);
				    var transferCenterName  =json.queryPDAOnlineUsingVo.orgName;//外场名称

					 platform.onLinePDADetail.queryDate=platform.queryPDAOnlineUsing.queryDate; 
					 
					 Ext.getCmp('Foss.platform.onLinePDADetail.queryDate.id').setValue(platform.onLinePDADetail.queryDate);
					 
					 Ext.getCmp('Foss.platform.onLinePDADetail.transferCenterCode.id').setReadOnly(true);
					 
					 platform.onLinePDADetail.pagingBar.moveFirst();
					 Ext.getCmp('Foss.platform.onLinePDADetail.transferCenterCode.id').setValue(transferCenterName);
					 Ext.create('Ext.panel.Panel', {
									id : 'T_platform-onLinePDADetailIndex_content',
									cls : "panelContentNToolbar",
									bodyCls : 'panelContent-body',
									layout : 'auto',
									items : [platform.onLinePDADetail.queryPDAForm,platform.onLinePDADetail.queryResult],
									renderTo : 'T_platform-onLinePDADetailIndex-body'
								});
				},
				exception : function(response) {
					
				}
			});

			 
			
			
		});


