//定义
Ext.define('Foss.management.jobprocesslog.listModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [
	          {name : 'id',type : 'string'},
	          {name : 'bizName',type : 'string'},
	          {name : 'bizCode',type : 'string'},
	          {name : 'execBizId',type : 'string'},
	          {name : 'execTableName',type : 'string'},
	          {name : 'remark',type : 'string'},
	          {name : 'exceptionInfo',type : 'string'},
	          {name : 'createTime',type : 'string',
	  			convert: function(value) {
					if (value != null) {
						var date = new Date(value);						
						return Ext.Date.format(date,'Y-m-d H:i:s');
					} else {
						return value;
					}
				}
	          }
	         ]
});


Ext.define('Foss.management.jobprocesslog.jobProcessLogsListStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.management.jobprocesslog.listModel',
	pageSize:10,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// 请求的url
		url : management.realPath('jobProcessLogsList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.jobProcessLogEList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = management.jobprocesslog.queryform;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'vo.jobProcessLogDto.createTimeFrom' : queryParams.createTimeFrom,
						'vo.jobProcessLogDto.createTimeTo' : queryParams.createTimeTo
					}
				});	
			}
		}
	}
});

//查询条件-页面
Ext.define('Foss.management.jobprocesslog.QueryForm', {
	extend:'Ext.form.Panel',
	title: management.jobprocesslog.i18n('foss.management.title'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	//id: 'Foss_Queryloadtask_QueryForm_Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[{
		xtype: 'rangeDateField',
		fieldLabel: management.jobprocesslog.i18n('foss.management.label.takeOffDate'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		dateType: 'datetimefield_date97',
		fromName: 'createTimeFrom',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'createTimeTo',
		allowBlank: true,
		columnWidth: .50
	},{
		xtype: 'container',
		columnWidth:.50,
		html: '&nbsp;',
		height:30,
	},{
		text: management.jobprocesslog.i18n('foss.management.button.reset'),
		xtype:"button",
		columnWidth:.10,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('createTimeFrom')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('createTimeTo')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
	},{
		text: management.jobprocesslog.i18n('foss.management.button.search'), //查询 按钮
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			management.jobprocesslog.pagingBar.moveFirst();
		}
	}]
});

//展示查询的结果
Ext.define('Foss.management.jobprocesslog.QueryResult',{
	extend:'Ext.grid.Panel',
    title:management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.title'),   //JOB异常列表
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns: [{
    	xtype:'actioncolumn',
		header: management.jobprocesslog.i18n('foss.management.button.operate'),   //操作
        flex : 0.3,
        items: [{
        	iconCls: 'deppon_icons_showdetail',
            tooltip: management.jobprocesslog.i18n('foss.management.button.info'),   //查看
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	var detailInfoWindow = management.jobprocesslog.readDetailInfoWindows;
				var readForm = detailInfoWindow.getReadJobProcessLogForm();
				readForm.getForm().reset();
				readForm.getForm().loadRecord(record);
				detailInfoWindow.show();
            	
            	/*var array = {vo : {id:record.get('id')}};
            	Ext.Ajax.request({
    				url:management.realPath('jobProcessLogById.action'),
    				jsonData:array,
    				success : function(response) {
        				var json = Ext.decode(response.responseText);
        				
        				record.date = json.vo.jobProcessLogE;
        				readForm.getForm().loadRecord(record);
        				
        				detailInfoWindow.show();
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert(management.jobprocesslog.i18n('foss.management.jobprocesslog.msg.message'),json.message);
        			}
    			});*/
            }
        }]
    },{
		dataIndex: 'id' ,
		hidden:true
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.bizName'),   //业务名称
		dataIndex: 'bizName' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.bizCode'),   //业务编码
		dataIndex: 'bizCode' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.execBizId'),   //业务任务ID
		dataIndex: 'execBizId' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.execTableName'),   //业务任务名称
		dataIndex: 'execTableName' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.exceptionInfo'),   //异常描述
		dataIndex: 'exceptionInfo' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.createTime'),   //创建时间
		dataIndex: 'createTime' ,
		flex: 1
	}],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.jobprocesslog.jobProcessLogsListStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.jobprocesslog.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


//定义整个展示的panel集合
Ext.define('Foss.management.jobprocesslog.jobProcessLogReadAll',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	title:management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.info'),   //JOB异常信息
	cls: "panelContentNToolbar",
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		allowBlank:false,
		readOnly:true
	},
	layout:'column',
	items:[{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.bizName'),   //业务名称
		name: 'bizName' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.bizCode'),   //业务编码
		name: 'bizCode' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.execBizId'),   //业务任务ID
		name: 'execBizId' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.execTableName'),   //业务任务名称
		name: 'execTableName' ,
		columnWidth:.25,
		allowBlank:true
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.createTime'),   //创建时间
		name: 'createTime' ,
		columnWidth:.25
	},{
		columnWidth:.75,
		value : ''
	},{
		xtype : 'textareafield',
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.exceptionInfo'),   //异常描述
		name: 'exceptionInfo',
		columnWidth:1,
		height: 500
	}]
});
//定义弹出的展示窗口 - 查看
Ext.define('Foss.management.jobprocesslog.readDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	readJobProcessLogForm : null,
	getReadJobProcessLogForm: function(){
		if(this.readJobProcessLogForm==null){
			this.readJobProcessLogForm = management.jobprocesslog.jobProcessLogReadAll;
		}
		management.readJobProcessLogForm = this.readJobProcessLogForm;
		return this.readJobProcessLogForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadJobProcessLogForm()
				];
		me.callParent([cfg]);
	}
});

/********************job process**********************/

//定义
Ext.define('Foss.management.jobprocess.listModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [
	          {name : 'id',type : 'string'},
	          {name : 'bizName',type : 'string'},
	          {name : 'bizCode',type : 'string'},
	          {name : 'bizStartTime',type : 'string',
		  			convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d H:i:s');
						} else {
							return value;
						}
					}
		          },
	          {name : 'bizEndTime',type : 'string',
		  			convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d H:i:s');
						} else {
							return value;
						}
					}
		          },
	          {name : 'status',type : 'string'},
	          {name : 'threadNo',type : 'string'},
	          {name : 'threadCount',type : 'string'},
	          {name : 'jobStartTime',type : 'string',
		  			convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d H:i:s');
						} else {
							return value;
						}
					}
		          },
	          {name : 'jobEndTime',type : 'string',
	  			convert: function(value) {
					if (value != null) {
						var date = new Date(value);						
						return Ext.Date.format(date,'Y-m-d H:i:s');
					} else {
						return value;
					}
				}
	          }
		    ]
});


Ext.define('Foss.management.jobprocess.jobProcessListStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.management.jobprocess.listModel',
	pageSize:10,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// 请求的url
		url : management.realPath('jobProcessList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.jobProcessEList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = management.jobprocess.queryform;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'vo.jobProcessDto.bizStartTimeFrom' : queryParams.bizStartTimeFrom,
						'vo.jobProcessDto.bizStartTimeTo' : queryParams.bizStartTimeTo,
						'vo.jobProcessDto.bizEndTimeFrom' : queryParams.bizEndTimeFrom,
						'vo.jobProcessDto.bizEndTimeTo' : queryParams.bizEndTimeTo,
						'vo.jobProcessDto.jobStartTimeFrom' : queryParams.jobStartTimeFrom,
						'vo.jobProcessDto.jobStartTimeTo' : queryParams.jobStartTimeTo,
						'vo.jobProcessDto.jobEndTimeFrom' : queryParams.jobEndTimeFrom,
						'vo.jobProcessDto.jobEndTimeTo' : queryParams.jobEndTimeTo
					}
				});	
			}
		}
	}
});

//查询条件-页面
Ext.define('Foss.management.jobprocess.QueryForm', {
	extend:'Ext.form.Panel',
	title: management.jobprocesslog.i18n('foss.management.title'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	//id: 'Foss_Queryloadtask_QueryForm_Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[{
		xtype: 'rangeDateField',
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizStartTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_jobProcess_bizStartTime_Id_' + Ext.Date.format(new Date(),'YmdHis'),
		dateType: 'datetimefield_date97',
		fromName: 'bizStartTimeFrom',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'bizStartTimeTo',
		allowBlank: true,
		columnWidth: .50
	},{
		xtype: 'rangeDateField',
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizEndTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_jobProcess_bizEndTime_Id_' + Ext.Date.format(new Date(),'YmdHis'),
		dateType: 'datetimefield_date97',
		fromName: 'bizEndTimeFrom',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'bizEndTimeTo',
		allowBlank: true,
		columnWidth: .50
	},{
		xtype: 'rangeDateField',
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobStartTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_jobProcess_jobStartTime_Id_' + Ext.Date.format(new Date(),'YmdHis'),
		dateType: 'datetimefield_date97',
		fromName: 'jobStartTimeFrom',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'jobStartTimeTo',
		allowBlank: true,
		columnWidth: .50
	},{
		xtype: 'rangeDateField',
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobEndTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_jobProcess_jobEndTime_Id_' + Ext.Date.format(new Date(),'YmdHis'),
		dateType: 'datetimefield_date97',
		fromName: 'jobEndTimeFrom',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'jobEndTimeTo',
		allowBlank: true,
		columnWidth: .50
	},{
		text: management.jobprocesslog.i18n('foss.management.button.reset'),
		xtype:"button",
		columnWidth:.10,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('bizStartTimeFrom')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('bizStartTimeTo')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('bizEndTimeFrom')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('bizEndTimeTo')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('jobStartTimeFrom')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('jobStartTimeTo')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('jobEndTimeFrom')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('jobEndTimeTo')
			.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
	},{
		text: management.jobprocesslog.i18n('foss.management.button.search'), //查询 按钮
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			management.jobprocess.pagingBar.moveFirst();
		}
	}]
});

//展示查询的结果
Ext.define('Foss.management.jobprocess.QueryResult',{
	extend:'Ext.grid.Panel',
    title:management.jobprocesslog.i18n('Foss.management.jobprocess.label.title'),   //JOB执行列表
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns: [{
    	xtype:'actioncolumn',
		header: management.jobprocesslog.i18n('foss.management.button.operate'),   //操作
        flex : 0.3,
        items: [{
        	iconCls: 'deppon_icons_showdetail',
            tooltip: management.jobprocesslog.i18n('foss.management.button.info'),   //查看
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	var detailInfoWindow = management.jobprocess.readDetailInfoWindows;
				var readForm = detailInfoWindow.getReadJobProcessForm();
				readForm.getForm().reset();
				readForm.getForm().loadRecord(record);
				detailInfoWindow.show();
            	
            	/*var array = {vo : {id:record.get('id')}};
            	Ext.Ajax.request({
    				url:management.realPath('jobProcessLogById.action'),
    				jsonData:array,
    				success : function(response) {
        				var json = Ext.decode(response.responseText);
        				
        				record.date = json.vo.jobProcessLogE;
        				readForm.getForm().loadRecord(record);
        				
        				detailInfoWindow.show();
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert(management.jobprocesslog.i18n('foss.management.jobprocess.msg.message'),json.message);
        			}
    			});*/
            }
        }]
    },{
		dataIndex: 'id' ,
		hidden:true
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizName'),   //业务名称
		dataIndex: 'bizName' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizCode'),   //业务编码
		dataIndex: 'bizCode' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizStartTime'),   //业务开始时间
		dataIndex: 'bizStartTime' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizEndTime'),   //业务结束时间
		dataIndex: 'bizEndTime' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.status'),   //执行状态
		dataIndex: 'status' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.threadNo'),   //线程ID
		dataIndex: 'threadNo' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.threadCount'),   //线程数量
		dataIndex: 'threadCount' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobStartTime'),   //业务开始时间
		dataIndex: 'jobStartTime' ,
		flex: 1 
	},{
		header: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobEndTime'),   //业务结束时间
		dataIndex: 'jobEndTime' ,
		flex: 1 
	}],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.jobprocess.jobProcessListStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.jobprocess.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


//定义整个展示的panel集合
Ext.define('Foss.management.jobprocess.jobProcessReadAll',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	title:management.jobprocesslog.i18n('Foss.management.jobprocess.label.info'),   //JOB执行信息
	cls: "panelContentNToolbar",
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		allowBlank:false,
		readOnly:true
	},
	layout:'column',
	items:[{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizName'),   //业务名称
		name: 'bizName' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizCode'),   //业务编码
		name: 'bizCode' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizStartTime'),   //业务开始时间
		name: 'bizStartTime' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.bizEndTime'),   //业务结束时间
		name: 'bizEndTime' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.status'),   //执行状态
		name: 'status' ,
		columnWidth:.25 
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.threadNo'),   //线程ID
		name: 'threadNo' ,
		columnWidth:.25 
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.threadCount'),   //线程数量
		name: 'threadCount' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobStartTime'),   //业务开始时间
		name: 'jobStartTime' ,
		columnWidth:.25
	},{
		fieldLabel: management.jobprocesslog.i18n('Foss.management.jobprocess.label.jobEndTime'),   //业务结束时间
		name: 'jobEndTime' ,
		columnWidth:.25
	}]
});
//定义弹出的展示窗口 - 查看
Ext.define('Foss.management.jobprocess.readDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	readJobProcessForm : null,
	getReadJobProcessForm: function(){
		if(this.readJobProcessForm==null){
			this.readJobProcessForm = management.jobprocess.jobProcessReadAll;
		}
		management.readProcessForm = this.readJobProcessForm;
		return this.readJobProcessForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadJobProcessForm()
				];
		me.callParent([cfg]);
	}
});

if(management.jobprocess == null || management.jobprocess == undefined){
	management.jobprocess={};
}
Foss.management.job={};

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Loader.setConfig({enabled:true});
	
	//job process log
	management.jobprocesslog.queryform = Ext.create('Foss.management.jobprocesslog.QueryForm');
	management.jobprocesslog.queryResult = Ext.create('Foss.management.jobprocesslog.QueryResult');
	management.jobprocesslog.jobProcessLogReadAll = Ext.create('Foss.management.jobprocesslog.jobProcessLogReadAll');
	management.jobprocesslog.readDetailInfoWindows=Ext.create('Foss.management.jobprocesslog.readDetailInfoWindows');
	//job process
	management.jobprocess.queryform = Ext.create('Foss.management.jobprocess.QueryForm');
	management.jobprocess.queryResult = Ext.create('Foss.management.jobprocess.QueryResult');
	management.jobprocess.jobProcessReadAll = Ext.create('Foss.management.jobprocess.jobProcessReadAll');
	management.jobprocess.readDetailInfoWindows=Ext.create('Foss.management.jobprocess.readDetailInfoWindows');

	//TAB
	Foss.management.job.jobprocesslogPanel = Ext.create('Ext.panel.Panel',{items:[management.jobprocesslog.queryform,management.jobprocesslog.queryResult]});
	Foss.management.job.jobprocessPanel = Ext.create('Ext.panel.Panel',{items:[management.jobprocess.queryform,management.jobprocess.queryResult]});
	Foss.management.job.queryTabPanel = Ext.create('Ext.tab.Panel',{
		bodyCls: 'autoHeight',
		cls: 'innerTabPanel',
		items: [{
			title: management.jobprocesslog.i18n('Foss.management.jobprocesslog.label.info'),
			layout: 'fit',
			tabConfig: {
				width: 120
			},
			items:[Foss.management.job.jobprocesslogPanel]
		},{
			title: management.jobprocesslog.i18n('Foss.management.jobprocess.label.info'),
			tabConfig: {
				width: 120
			},
			layout: 'fit',
			items:[Foss.management.job.jobprocessPanel]
		}]
	});
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-jobProcessLogIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [Foss.management.job.queryTabPanel],
		renderTo: 'T_management-jobProcessLogIndex-body'
	});	
});
