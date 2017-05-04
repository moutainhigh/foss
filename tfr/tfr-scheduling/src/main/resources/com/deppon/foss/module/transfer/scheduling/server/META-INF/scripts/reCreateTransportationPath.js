Ext.define('Foss.reCreateTransportationPath.QueryForm', {
			extend : 'Ext.form.Panel',
			id : 'Foss_reCreateTransportationPath_QueryForm_ID',
			layout : 'column',
			frame : true,
			border : false,
			title : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.reCreateTransportationPath'),
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
						xtype : 'textfield',
						fieldLabel : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.waybillNo'),
						name : 'waybillNo',
						columnWidth : .3
					}, {
						xtype : 'container',
						columnWidth : .05,
						html : '&nbsp;'
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.reset'),
									columnWidth : .08,
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									columnWidth : .84,
									html : '&nbsp;'
								}, {
									text : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.query'),
									disabled: !scheduling.reCreateTransportationPath.isPermission('scheduling/queryReCreateTransportPathButton'),
									hidden: !scheduling.reCreateTransportationPath.isPermission('scheduling/queryReCreateTransportPathButton'),
									columnWidth : .08,
									handler : function() {
										scheduling.pagingBar.moveFirst();
									}
								}]
					}],

			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
// TransportationPath model
Ext.define('Foss.reCreateTransportationPath.TransportationPathModel', {
			extend : 'Ext.data.Model',
			// idgen: 'uuid',
			idProperty : 'transportPathId',
			fields : [{
						name : 'transportPathId',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'billingTime',
						type : 'date',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'billingOrgCode',
						type : 'string'
					}, {
						name : 'billingOrgCodeName',
						type : 'string'
					}, {
						name : 'destOrgCode',
						type : 'string'
					}, {
						name : 'destOrgCodeName',
						type : 'string'
					}, {
						name : 'currentOrgCode',
						type : 'string'
					}, {
						name : 'currentOrgCodeName',
						type : 'string'
					}, {
						name : 'totalWeight',
						type : 'float'
					}, {
						name : 'totalVolume',
						type : 'float'
					}, {
						name : 'goodsQtyTotal',
						type : 'string'
					}, {
						name : 'transportModel',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'transportPath',
						type : 'string'
					}, {
						name : 'action',
						type : 'string'
					}, {
						name : 'beforeAction',
						type : 'string'
					}, {
						name : 'packingOrgCode',
						type : 'string'
					}, {
						name : 'packingOrgCodeName',
						type : 'string'
					}, {
						name : 'nextOrgCode',
						type : 'string'
					}, {
						name : 'nextOrgCodeName',
						type : 'string'
					}, {
						name : 'planStartTime',
						type : 'date',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'planArriveTime',
						type : 'date',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'ifPartialStowage',
						type : 'String' //modify by liangfuxiang 2013-05-28 将num->string
					}, {
						name : 'packedNum',
						type : 'number'
					}]
		});

// TransportationPath Store
Ext.define('Foss.reCreateTransportationPath.TransportationPathStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.reCreateTransportationPath.TransportationPathModel',
	pageSize : 10,
	autoLoad : true,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryReCreateTransportPath.action'),
		reader : {
			type : 'json',
			root : 'schedulingVO.transportPathList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = scheduling.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'schedulingVO.transportPathEntity.waybillNo' : queryParams.waybillNo,
					'schedulingVO.transportPathEntity.currentOrgCode' : FossUserContext.getCurrentDeptCode()
				}
			});
		}
	}
});

Ext.define('Foss.scheduling.reCreateTransportationPath.GoodsStockModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type:'string'},
	    {name: 'waybillNO',type:'string'},
	    {name: 'serialNO',type:'string'},
	    {name: 'inStockTime',type:'date', convert: dateConvert},
		{name: 'operatorName', type: 'string'}
	]
});

Ext.define('Foss.scheduling.reCreateTransportationPath.GoodsStockStore',{
	extend:'Ext.data.Store',
	model:'Foss.scheduling.reCreateTransportationPath.GoodsStockModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'schedulingVO.stockEntityList'
		}
	}
});


Ext.define('Foss.scheduling.reCreateTransportationPath.pathDetailModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type:'string'},
	    {name: 'routeNo',type:'string'},
	    {name: 'origOrgName',type:'string'},
	    {name: 'objectiveOrgName',type:'string'}	
	]
});

Ext.define('Foss.scheduling.reCreateTransportationPath.pathDetailStore',{
	extend:'Ext.data.Store',
	model:'Foss.scheduling.reCreateTransportationPath.pathDetailModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'schedulingVO.pathDetailList'
		}
	}
});

// Waybill Grid
Ext.define('Foss.reCreateTransportationPath.QueryGrid', {
	extend : 'Ext.grid.Panel',
	// 指定grid对象在DOM树中的唯一值
	id : 'Foss_reCreateTransportationPath_QueryGrid_ID',
	emptyText: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.queryEmpty'),
	// 指定表格的高度
	height : 300,
	// 指定表格的宽度
	// width:780,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	// plugins: [{
	// ptype: 'rowexpander',
	// rowsExpander: false,
	// rowBodyElement : 'Foss.reCreateTransportationPath.GoodsGrid'
	// }],
	plugins: [{
		pluginId : 'stockTransportPathQuery_rowexpander_plugin_Id',
		ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement: 'Foss.scheduling.reCreateTransportationPath.GoodsStockGrid'
	}],
	// 定义表格列信息
	columns : [
				// add by liangfuxiang 2013-05-28 begin没有此参数，导致重新计算走货路径，全部变为了非分批配载了。
				{
					name:'ifPartialStowage',//是否分批配载
					hidden:true
				},
				//add by liangfuxiang 2013-05-28 end 
	           {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.waybillNo'),
				// 关联model中的字段名
				dataIndex : 'waybillNo'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.billingTime'),
				xtype : 'ellipsiscolumn',
				// 关联model中的字段名
				dataIndex : 'billingTime'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.billingOrgCodeName'),
				xtype : 'ellipsiscolumn',
				// 关联model中的字段名
				dataIndex : 'billingOrgCodeName'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.destOrgCodeName'),
				xtype : 'ellipsiscolumn',
				// 关联model中的字段名
				dataIndex : 'destOrgCodeName'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.currentOrgCodeName'),
				xtype : 'ellipsiscolumn',
				// 关联model中的字段名
				dataIndex : 'currentOrgCodeName'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.transportModel'),
				// 关联model中的字段名
				dataIndex : 'transportModel'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.totalWeight'),
				// 关联model中的字段名
				dataIndex : 'totalWeight'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.totalVolume'),
				// 关联model中的字段名
				dataIndex : 'totalVolume'
			}, {
				width : 80,
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.goodsQtyTotal'),
				// 关联model中的字段名
				dataIndex : 'goodsQtyTotal'
			}, {
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.action'),
				// 关联model中的字段名
				dataIndex : 'action'
			}, {
				width : 500,
				// 字段标题
				header : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.transportPath'),
				xtype : 'ellipsiscolumn',
				// 关联model中的字段名
				dataIndex : 'transportPath'
			}],
	//add by liangfuxiang 2013-04-16 BUG-7278 begin
	loadMask:null,
	getLoadMask:function(){
			var me = this;
			me.loadMask = Ext.getCmp('FOSS_reCreateTransportationPath_QueryGrid_LoadMask_ID');
			if(Ext.isEmpty(me.loadMask)){
				me.loadMask = new Ext.LoadMask(me.up('panel'),{
					id:'FOSS_reCreateTransportationPath_QueryGrid_LoadMask_ID',
					msg:scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.maskMsg'),
					autoShow:false
				});
			}
			return me.loadMask;
	},
	//add by liangfuxiang 2013-04-16 BUG-7278 end
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.reCreateTransportationPath.TransportationPathStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');

		me.tbar = [{
			xtype : 'button',
			text : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.reCreate'),
			disabled: !scheduling.reCreateTransportationPath.isPermission('scheduling/ReCreateTransportPathButton'),
			hidden: !scheduling.reCreateTransportationPath.isPermission('scheduling/ReCreateTransportPathButton'),
			gridContainer : this,
			handler : function() {
				var selectedList = Ext
						.getCmp('Foss_reCreateTransportationPath_QueryGrid_ID')
						.getSelectionModel().selected.items
				var transportPathList = new Array();
				Ext.each(selectedList, function(record) {
							transportPathList.push(record.data);
						});
				if(transportPathList==null||transportPathList.length==0){
					Ext.MessageBox.alert(scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.hint'),scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.noSelect'));
					return false;
				}
				var params = {
					schedulingVO : {
						transportPathList : transportPathList
					}
				};
				//modify by liangfuxiang 2013-04-16 BUG-7278 begin
				var grid=Ext.getCmp('Foss_reCreateTransportationPath_QueryGrid_ID');
				grid.getLoadMask().show();
				//modify by liangfuxiang 2013-04-16 end
				Ext.Ajax.request({
							url : scheduling.realPath('reCreateTransportPath.action'),
							jsonData : params,
							success : function(response) {
								//modify by liangfuxiang 2013-04-16 BUG-7278 begin
								grid.getLoadMask().hide();
								//modify by liangfuxiang 2013-04-16 end
								Ext.ux.Toast.msg(scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.hint'),scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.success'));
								scheduling.pagingBar.moveFirst();
							},
							exception : function(response) {
								//modify by liangfuxiang 2013-04-16 BUG-7278 begin
								grid.getLoadMask().hide(); 
								//modify by liangfuxiang 2013-04-16 end
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.hint'), result.message,'error');
							}
						});
			}
		}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});
		scheduling.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.reCreateTransportationPath.GoodsStockGrid',{
	extend: 'Ext.grid.Panel',
	autoScroll:true,
	frame: true,
	border: false,
	bodyCls: 'autoHeight',
	layout: 'column',
	margin: '10 0 0 0',
	columns:[
	    {
	    	xtype : 'actioncolumn',
			width: 38,
			text : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.view'),	//查看
			align : 'center',
			items : [ {
				tooltip : scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.pathDetail'),	//路径明细
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					var record = grid.store.getAt(rowIndex);
					Ext.getCmp('Foss_scheduling_reCreateTransportationPath_pathDetailGrid_Id').store.removeAll();
					Ext.Ajax.request({
						url : scheduling.realPath('queryPathDetail.action'),
						params :{
							'schedulingVO.pathDetailEntity.waybillNo' : record.get('waybillNO'),
							'schedulingVO.pathDetailEntity.goodsNo':record.get('serialNO')
						},
						success:function(response){
							var result = Ext.decode(response.responseText);
							Ext.getCmp('Foss_scheduling_reCreateTransportationPath_pathDetailGrid_Id').store.loadData(result.schedulingVO.pathDetailList);
							scheduling.reCreateTransportationPath.pathDetailWindow.restore();
							scheduling.reCreateTransportationPath.pathDetailWindow.show();
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.getPathDetailFailure'), result.message, 'error', 3000); //获取路径明细失败
						}
					});
					}
				} ]
	    },{
			text: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.waybillNo'), //运单号
			dataIndex : 'waybillNo',
			hidden: true
		},{
			text: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.serailNo'), //流水号
			dataIndex : 'serialNO'
		},{
			text: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.inStoreTime'), //入库时间
			dataIndex : 'inStockTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s'
		},{
			text: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.operater'), //操作人
			dataIndex : 'operatorName'
		}
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.reCreateTransportationPath.GoodsStockStore');
		me.callParent([cfg]);
	},
	bindData :function(record){
		var grid = this;
		var params = {
				'schedulingVO.transportPathEntity.waybillNo' : record.get('waybillNo'),
				'schedulingVO.transportPathEntity.currentOrgCode' : FossUserContext.getCurrentDeptCode()
		};
		Ext.Ajax.request({
			url : scheduling.realPath('queryStock.action'),
			params :params,
			success:function(response){
				var result = Ext.decode(response.responseText);
				grid.store.loadData(result.schedulingVO.stockEntityList);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.getStockDetailFailure'), result.message, 'error', 3000); //获取库存明细失败
			}
		});
	}
});


//路径明细
Ext.define('Foss.scheduling.reCreateTransportationPath.pathDetailGrid',{
	extend: 'Ext.grid.Panel',
	title: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.pathDetail'),
	stripeRows: true,
	frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
			header: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.routeNo'), //线路段号
			dataIndex: 'routeNo',
			flex : 0.8
		},{
			header: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.origOrgName'), //出发部门
			dataIndex: 'origOrgName',
			flex : 1
			
		},{
			header: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.objectiveOrgName'), //到达部门 
			dataIndex: 'objectiveOrgName',
			flex : 1
		}],
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.reCreateTransportationPath.pathDetailStore');
		me.callParent([cfg]);
	}
});

scheduling.reCreateTransportationPath.pathDetailWindow = Ext.create('Ext.window.Window', {
    title: scheduling.reCreateTransportationPath.i18n('foss.scheduling.reCreateTransportationPath.serailPathDetail'), //流水号路径明细
    height:400,
    width:600,
    autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
    modal: true,
    defaults: {
		margin:'5 5 5 5',
		labelWidth:85,
	},
    items: [
            Ext.create('Foss.scheduling.reCreateTransportationPath.pathDetailGrid',{id:'Foss_scheduling_reCreateTransportationPath_pathDetailGrid_Id'})
    ]
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryform = Ext
					.create('Foss.reCreateTransportationPath.QueryForm');
			scheduling.queryform = queryform;
			var querygrid = Ext
					.create('Foss.reCreateTransportationPath.QueryGrid');
			Ext.create('Ext.panel.Panel', {
						id : 'T_scheduling-reCreateTransportationPath_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						items : [queryform, querygrid],
						renderTo : 'T_scheduling-reCreateTransportationPath-body'
					});
		});
