/**-----------------------------------------------差异报告状态store------------------------------**/
Ext.define('Foss.Load.TaskStateStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"GENERATED", "name":load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.GENERATED')},//"已生成"
        {"value":"BACK", "name":load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.BACK')},//已打回
        {"value":"AFFIRM", "name":load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.AFFIRM')},//已确认
        {"value":"ALL", "name":load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.ALL')}//全部
    ]
});
/**-----------------------------------------------queryForm--------------------------------------------------**/
Ext.define('Foss.Load.GaprepQueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.queryCondition'),//'查询条件',
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 100
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.deliverBillNo'),//'派送单号',
		name: 'report.deliverBillNo',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.reportNo'),//'差异报告编号',
		//labelWidth: 60,
		name: 'report.reportNo',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadTaskNo'),//'装车任务编号',
		//labelWidth: 60,
		name: 'report.taskNo',
		columnWidth:.25
	},{
		xtype: 'commontruckselector',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.vehicleNo'),//'车牌号',
		//labelWidth: 60,
		name: 'report.vehicleNo',
		columnWidth:.25
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.createTime'),//'报告生成时间',
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
			,00,00,00),'Y-m-d H:i:s'),
		//labelWidth: 80,
		columnWidth: .27,
		name: 'queryTimeBegin',
		id:'foss.load.gaprep.queryTimeBegin',
		time : true,
		allowBlank:false,
		dateConfig: {
			el : 'foss.load.gaprep.queryTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		labelWidth:50,
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.to'),//'至',
		allowBlank:false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .23,
		name: 'queryTimeEnd',
		id:'foss.load.gaprep.queryTimeEnd',
		time : true,
		dateConfig: {
			el : 'foss.load.gaprep.queryTimeEnd-inputEl'
		}
	},{
		xtype: 'combo',
		editable :false,
		store: Ext.create('Foss.Load.TaskStateStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.state'),//'差异报告状态',
		//labelWidth: 60,
		name: 'report.state',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.waybillNo'),//'运单号',
		//labelWidth: 60,
		name: 'report.waybillNo',
		columnWidth:.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.reset'),//'重置',
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.8,
			html: '&nbsp;'
		},{
			text:load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.query'),//'查询',
			disabled : !load.deliverloadgapreport.isPermission('load/queryDeliverLoadGapReportButton'),
			hidden : !load.deliverloadgapreport.isPermission('load/queryDeliverLoadGapReportButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				if(this.up('form').getForm().isValid()){
					var time1 = this.up('form').getForm().getValues().queryTimeBegin,
					time2 = this.up('form').getForm().getValues().queryTimeEnd,
					quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19)),
					queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
					if(queryTimeEnd>quertTimeBegin){
						if( queryTimeEnd.getTime() -quertTimeBegin.getTime() <= 31*24*60*60*1000){
							load.deliverloadgapreport.pagingBar.moveFirst();
						}else{
							//Ext.ux.Toast.msg('提示', '查询时间跨度不能超过31天!', 'error', 3000);
							Ext.ux.Toast.msg(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.prompt'), load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.queryTimeTooLong'), 'error', 3000);
						}
					}else{
						//Ext.ux.Toast.msg('提示', '查询开始时间不能大于查询截止时间!', 'error', 3000);
						Ext.ux.Toast.msg(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.prompt'), load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.wrongQueryTime'), 'error', 3000);
					}
				}else{
					//Ext.ux.Toast.msg('提示', '请补全查询条件!', 'error', 3000);
					Ext.ux.Toast.msg(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.prompt'), load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.wrongQueryCondition'), 'error', 3000);
				}	
			}
			}]
	}]
});
Ext.define('Foss.Load.GaprepModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id',type:'string'},
	        {name:'taskNo',type:'string'},
	        {name:'reportNo',type:'string'},
	        {name:'deliverBillNo',type:'string'},
	        {name:'createTime',type:'string'},
	        {name:'state',type:'string',
	        	convert: function(value) {
					if (value == 'GENERATED') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.GENERATED');//'已生成';
					} else if (value == 'BACK') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.BACK');//'已打回';
					}else if (value == 'AFFIRM') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.AFFIRM');//'已确认';
					}
				}
	        },
	        {name:'planLoadGoodsQty',type:'int'},
	        {name:'actualLoadGoodsQty',type:'int'},
	        {name:'lackGoodsQty',type:'int'},
	        {name:'vehicleNo',type:'string'},
	        {name:'beValid',type:'string'}
	       ]
});
Ext.define('Foss.Load.GaprepStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.GaprepModel',
	pageSize: 10,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryDeliverLoadGapReport.action'),//'../load/queryDeliverLoadGapReport.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.reports',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
Ext.define('Foss.Load.GaprepGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.dataNotFind'),//'查询结果为空',
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	frame: true,
	title:load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.gapReport'),//'派送装车差异报告',
	columns:[
	         	{
			xtype : 'actioncolumn',
			flex: 0.3,
			text : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.operate'),//'操作',//操作
			align : 'center',
			items : [ {
				tooltip : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.watch'),//'查看',//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					Ext.getCmp('Foss_Load_GaprepWayBillGrid_Id').store.removeAll();
					load.deliverloadgapreport.reportWindow.restore();
					var record = grid.store.getAt(rowIndex);
					Ext.getCmp('Foss_Load_GaprepWayBillGrid_Id').store.load({
						params : {
							'vo.report.id':record.get('id')
						},
						callback : function(records, operation, success) {
							if (success == false) {
								var errorMessage = operation.request.proxy.reader.jsonData.message;
								//Ext.Msg.alert('提示',errorMessage);
								//Ext.ux.Toast.msg('提示', errorMessage, 'error', 3000);
								Ext.ux.Toast.msg(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.prompt'), errorMessage, 'error', 3000);
							}else{
								Ext.getCmp('Foss_Load_DeliverLoadGapRep_WindowBottomForm_Id').getForm().loadRecord(record);
								Ext.getCmp('Foss_Load_DeliverLoadGapRep_WindowForm_Id').getForm().loadRecord(record);
								load.deliverloadgapreport.reportWindow.show();
							}
						}
					});
					}
				} ]
		},
		{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.reportNo'),//'差异报告编号',
			dataIndex : 'reportNo',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadTaskNo'),//'装车任务编号',
			dataIndex : 'taskNo',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.deliverBillNo'),//'派送单号',
			dataIndex : 'deliverBillNo',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.state'),//'差异报告状态',
			dataIndex : 'state',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.planLoadQty'),//'预计装车总件数',
			dataIndex : 'planLoadGoodsQty',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadQty'),//'实际装车总件数',
			dataIndex : 'actualLoadGoodsQty',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.lackQty'),//'少货总件数',
			dataIndex : 'lackGoodsQty',
			flex: 0.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.vehicleNo'),//'车牌号',
			dataIndex : 'vehicleNo',
			flex: 0.3
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.createTime'),//'报告生成时间',
			dataIndex : 'createTime',
			flex: 0.5
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.GaprepStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					var values = Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.report.deliverBillNo':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.deliverBillNo').getValue(),
							'vo.report.reportNo':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.reportNo').getValue(),
							'vo.report.taskNo':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.taskNo').getValue(),
							'vo.report.vehicleNo':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.vehicleNo').getValue(),
							'vo.queryTimeBegin':values.queryTimeBegin,
							'vo.queryTimeEnd':values.queryTimeEnd,
							'vo.report.state':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.state').getValue(),
							'vo.report.waybillNo':Ext.getCmp('Foss_Load_GaprepQueryForm_Id').getForm().findField('report.waybillNo').getValue()
						}
					});	
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		load.deliverloadgapreport.pagingBar = me.bbar;
		me.callParent([cfg]);	
	}
});
/**-----------------------------------------------明细窗口--------------------------------------------------**/
Ext.define('Foss.Load.DeliverLoadGapRep.WindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 100
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.deliverBillNo'),//'派送单号',
		name: 'deliverBillNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.reportNo'),//'差异报告编号',
		//labelWidth: 60,
		name: 'reportNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadTaskNo'),//'装车任务编号',
		//labelWidth: 60,
		name: 'taskNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.state'),//'差异报告状态',
		//labelWidth: 60,
		name: 'state',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.vehicleNo'),//'车牌号',
		//labelWidth: 60,
		name: 'vehicleNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.createTime'),//'报告生成时间',
		columnWidth: .33,
		name: 'createTime',
		readOnly:true
	}]
});
Ext.define('Foss.Load.DeliverLoadGapRep.WindowBottomForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin: '2 3 3 3',
		labelWidth: 100
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.planLoadQty'),//'预计装车总件数',
		name: 'planLoadGoodsQty',
		columnWidth:.25,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadQty'),//'实际装车总件数',
		//labelWidth: 60,
		name: 'actualLoadGoodsQty',
		columnWidth:.25,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.lackQty'),//'少货总件数',
		//labelWidth: 60,
		name: 'lackGoodsQty',
		columnWidth:.25,
		readOnly:true
	},{
		xtype: 'button',
		text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadTaskDetail'),//'装车任务明细',
		columnWidth:.16,
		handler:function(){
			var taskNo = Ext.getCmp('Foss_Load_DeliverLoadGapRep_WindowForm_Id').getForm().findField('taskNo').getValue();
			if(taskNo!=null){
				addTab('T_load-loadtaskwaybilldetailIndex',//对应打开的目标页面js的onReady里定义的renderTo
						load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadDetail'),//'装车明细',//打开的Tab页的标题
						load.realPath('loadtaskwaybilldetailIndex.action')+'?taskNo=' + taskNo);//对应的页面URL，可以在url后使用?x=123这种形式传参
				load.deliverloadgapreport.reportWindow.hide();
			}
		}
	},{
		xtype : 'button',
		text : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.expertExcelButtonText'),//'导出'
		columnWidth:.08,
		handler : function(){
			if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
			}
			//获取查询参数
			var taskNo = Ext.getCmp('Foss_Load_DeliverLoadGapRep_WindowForm_Id').getForm().findField('taskNo').getValue();
			Ext.Ajax.request({
				url : load.realPath('exportDeliverLoadGapDetailExcel.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {
					'vo.report.taskNo' : taskNo
				},
				isUpload: true,
				success:function(response){
					
				},
				exception : function(response) {
					top.Ext.MessageBox.alert(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.expertFailureAlertInfo')/*'导出失败'*/,result.message);
				}
			});
		}
	}]
});
Ext.define('Foss.Load.DeliverLoadGapRep.SerialModel',{
	extend:'Ext.data.Model',
	fields:[{name:'wayBillNo',type:'string'},
	        {name:'serialNo',type:'string'},
	        {name:'scanState',type:'string',
				convert: function(value) {
					if (value == 'SCANED') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.scan');//'已扫描';
					} else if (value == 'UNSCANED') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.unscan');//'未扫描';
					}else if (value == 'BY_HAND') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.byHand');//'手输';
					}
				}
	        },
	        {name:'goodsState',type:'string',
	        	convert: function(value) {
					if (value == 'NOT_LOADING') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.unload');//'未装车';
					} else if (value == 'EXTRA_UNPACKAGE') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.unpack');//'强装-未打包装';
					}else if (value == 'EXTRA_MODIFY') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.modify');//'强装-有更改';
					}else if (value == 'EXTRA_PACKAGE_UNOUT_STORAGE') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.packArea');//'强装-代打包装未出库';
					}else if (value == 'EXTRA_VALUABLES_UNOUT_STORAGE') {					
						return load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.valueArea');//'强装-贵重物品未出库';
					}
				}
	        },
	        {name:'scanTime',type:'string'}
	       ]
});
Ext.define('Foss.Load.DeliverLoadGapRep.SerialStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.DeliverLoadGapRep.SerialModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'vo.reportSerials'
		}
	}
});

Ext.define('Foss.Load.DeliverLoadGapRep.SerialGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.valueArea'),//'查询结果为空',
		frame: false,
		border : false,
		autoScroll:true,
		margin: '0 0 0 50',
		columns:[     
			{text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.serialNo'),dataIndex : 'serialNo'},//流水号
			{text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.scanState'),dataIndex : 'scanState'},//扫描状态
			{text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.scanTime'),dataIndex : 'scanTime'},//扫描时间
			{text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.goodsState'),dataIndex : 'goodsState'}//货物状态
		],
		viewConfig: {
	        stripeRows: false,
			getRowClass: function(record, rowParams, rp, store) {
				var goodsState = record.get('goodsState');
				if(goodsState != load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.unload')) {//未装车
					return 'deliver_load_gapreport';
				}
			}
		},
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.Load.DeliverLoadGapRep.SerialStore').load();
			me.callParent([cfg]);
		},
		bindData :function(record){
			var grid = this;
			var params = {
					'vo.reportWayBill.reportId':record.get('reportId'),
					'vo.reportWayBill.wayBillNo':record.get('wayBillNo')
			};
			Ext.Ajax.request({
				url : load.realPath('queryDeliverLoadGapReportSerials.action'),//'../load/queryDeliverLoadGapReportSerials.action',
				params :params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					grid.store.loadData(result.vo.reportSerials);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					//Ext.Msg.alert('提示',result.message);
					Ext.ux.Toast.msg(load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.prompt'), result.message, 'error', 3000);//提示
				}
			});
		}
});
Ext.define('Foss.Load.DeliverLoadGapRep.WayBillModel',{
	extend:'Ext.data.Model',
	fields:[{name:'wayBillNo',type:'string'},
	        {name:'gapType',type:'string'},
	        {name:'planLoadQty',type:'int'},
	        {name:'actualLoadQty',type:'int'},
	        {name:'lackGoodsQty',type:'int'},
	        {name:'notes',type:'string'},
	        {name:'transportType',type:'string'},
	        {name:'reportId',type:'string'}
	       ]
});
Ext.define('Foss.Load.DeliverLoadGapRep.WayBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.DeliverLoadGapRep.WayBillModel',
	proxy : {
		type : 'ajax',
		url : load.realPath('queryDeliverLoadGapReportWayBills.action'),//'../load/queryDeliverLoadGapReportWayBills.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.reportWayBills',
			successProperty: 'success'
		}
	}
});
Ext.define('Foss.Load.GaprepWayBillGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.dataNotFind'),//'查询结果为空',
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	height:400,
	width:690,
	frame: true,
	/*plugins: [{
		pluginId : 'deliverloadgapreport_rowexpander_plugin_Id',
		ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement: 'Foss.Load.DeliverLoadGapRep.SerialGrid'
	}],*/
	columns:[
		{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.wayBillNo'),//'运单号',
			dataIndex : 'wayBillNo',
			flex: 1.2
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.gapType'),//'差异类型',
			dataIndex : 'gapType',
			flex: 1.2
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.planLoadQty1'),//'预计装车件数',
			dataIndex : 'planLoadQty',
			flex: 1.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.loadQty1'),//'实际装车件数',
			dataIndex : 'actualLoadQty',
			flex: 1.4
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.lackQty1'),//'少货件数',
			dataIndex : 'lackGoodsQty',
			flex: 1.2
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.transportType'),//'运输性质',
			dataIndex : 'transportType',
			flex: 1.2
		},{
			text: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.notes'),//'备注',
			dataIndex : 'notes',
			flex: 1.2
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.DeliverLoadGapRep.WayBillStore');
		me.callParent([cfg]);	
	}
});
load.deliverloadgapreport.reportWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 780,
	height : 590,
	modal:true,
    layout: 'column',
    //id: 'windForm',
	title: load.deliverloadgapreport.i18n('foss.load.deliverLoadGapReport.gapReportDetail'),//'派送装车差异报告明细',//详细信息
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85,
	},
	items:[
	       Ext.create('Foss.Load.DeliverLoadGapRep.WindowForm',{id:'Foss_Load_DeliverLoadGapRep_WindowForm_Id'}),
	       Ext.create('Foss.Load.GaprepWayBillGrid',{id:'Foss_Load_GaprepWayBillGrid_Id'}),
	       Ext.create('Foss.Load.DeliverLoadGapRep.WindowBottomForm',{id:'Foss_Load_DeliverLoadGapRep_WindowBottomForm_Id'})
	]
});
/**-----------------------------------------------viewpanel--------------------------------------------------**/
Ext.onReady(function(){
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-deliverloadgapreportindex_content',
		frame:false,
		style:'padding-top:10px',
		items: [Ext.create('Foss.Load.GaprepQueryForm',{id:'Foss_Load_GaprepQueryForm_Id'}),Ext.create('Foss.Load.GaprepGrid',{id:'Foss_Load_GaprepGrid_Id'})],
		renderTo: 'T_load-deliverloadgapreportindex-body'
	});
});