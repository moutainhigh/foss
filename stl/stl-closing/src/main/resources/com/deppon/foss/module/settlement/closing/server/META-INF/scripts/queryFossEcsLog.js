closing.DATE = 'date'

/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
closing.queryFossEcsLog.billReceivableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('v',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

// 查看详细双击函数
closing.checkInforByDoubleClick = function(record, type) {

	if (type == 'date') {
		var win = Ext.getCmp('Foss_Ecs_LogQueryInfoGrid_Id').getDetailWindow();
	} else {
		return false;
	}
	var form = win.down('form').getForm();		
	form.findField('id').setValue(record.get('id'));
	form.findField('waybillNo').setValue(record.get('waybillNo'));
	var esbCode=record.get('esbCode');
	if(esbCode=='FOSS_ESB2FOSS_ECS_WAYBILL_BILLING'){
		form.findField('esbCode').setValue('开单生成单据');
	}else if(esbCode=='FOSS_ESB2FOSS_COMPLEMENT_FUNCTION'){
		form.findField('esbCode').setValue('补码生成单据');
	} else if(esbCode=='FOSS_ESB2FOSS_ECS_SYNC_WAYBILL'){
		form.findField('esbCode').setValue('运单信息同步');
	} else if(esbCode=='FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT'){
		form.findField('esbCode').setValue('签收信息同步');
	} else if(esbCode=='FOSS_ESB2FOSS_CHANGE_FINALNCIAL_BILL'){
		form.findField('esbCode').setValue('更改生成单据');
	}
			
	if (record.get('createTime') != null) {
		form.findField('createTime').setValue(stl.dateFormat(new Date(record.get('createTime')),'Y-m-d H:i:s'));
	}

    form.findField('requestMsg').setValue(record.get('requestMsg'));
    form.findField('responseMsg').setValue(record.get('responseMsg'));

	win.show();
}

//重新加载store
closing.checkInforByReload = function(grid){
	var form = Ext.getCmp('Foss_Ecs_EcsQueryInfoTab_QueryByDate_ID');
	if (form) {
		var vo = new Object();
		var statTime;
		var endTime;						
		var waybillNo=form.getForm().findField('waybillNo').getValue();//运单号
		var esbCode =  form.getForm().findField('esbCode').getValue();//编码			
		statTime = form.getForm().findField('statTime').getValue();
		endTime = form.getForm().findField('endTime').getValue();
	}
	Ext.Ajax.request({
		url:closing.realPath('queryEcsFossLog.action'),
		params : {
			'vo.startDate' : statTime,
			'vo.endDate' : endTime,
			'vo.waybillNo' : waybillNo,
			'vo.esbCode' : esbCode
		},
		success:function(response){
			var me = this;
			var dateGridStore = Ext.getCmp('Foss_Ecs_LogQueryInfoGrid_Id').store;
			dateGridStore.removeAll();
			dateGridStore.loadPage(1, {
				callback : function(records, operation,success) {
					var result = Ext.decode(operation.response);

					me.enable(true);
				}
			});
			Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'),'重发成功');
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);	
			Ext.Msg.alert('温馨提示',result.message);
		}
	});
	
}

// 通用时间校验
closing.dateValidation = function(form) {
	var startTime = form.findField('statTime').getValue();
	var endTime = form.findField('endTime').getValue();

	if (Ext.isEmpty(startTime)) {
		Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'), 
			closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.startDateIsNotNull'));
		return false;
	}

	if (Ext.isEmpty(endTime)) {
		Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'), 
			closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.endDateIsNotNull'));
		return false;
	}

	if (startTime > endTime) {
		Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'), 
			closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.endDateIsNotBeforeStart'));
		return false;
	}

	if (stl.compareTwoDate(startTime, endTime) > stl.DATELIMITDAYS_MONTH) {
		Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'), 
			closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.startToEndDateIsNotMaxDay') + stl.DATELIMITDAYS_MONTH+
			closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.day'));
		return false;	
	}
	
	return true;
}

//esb编码：store
Ext.define('Foss.Stl.Closing.EsbCodeStore',{
	extend:'Ext.data.Store',
	fields:['esbCodeCode','esbCodeName'],
	data:{
		'items':[
			{esbCodeCode:'',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.all')},
			{esbCodeCode:'FOSS_ESB2FOSS_ECS_WAYBILL_BILLING',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.coding1')},
			{esbCodeCode:'FOSS_ESB2FOSS_COMPLEMENT_FUNCTION',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.coding2')},
			{esbCodeCode:'FOSS_ESB2FOSS_ECS_SYNC_WAYBILL',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.coding3')},
			{esbCodeCode:'FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.coding4')},
			{esbCodeCode:'FOSS_ESB2FOSS_CHANGE_FINALNCIAL_BILL',esbCodeName:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.coding5')}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

// 条件查询tab
Ext.define('Foss.Ecs.EcsQueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame:false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	//activeTab : 0,
	//columnWidth : 1,
	height : 220,
	items : [{
		title : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.queryFossEcsLog'),
		tabConfig : {
			width : 120
		},
		layout:'hbox',
		//name : 'queryByDate',
		items : [{
			xtype : 'form',
			id : 'Foss_Ecs_EcsQueryInfoTab_QueryByDate_ID',
			width:920,
        	layout:'column',
			defaults : {				
				margin:'10 10 0 10',
				labelWidth : 90
				//padding:5
			},
			items : [{
						xtype : 'rangeDateField',
						dateType : 'datetimefield_date97',
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.createTime'),
						fromName : 'statTime',
						toName : 'endTime',
						allowBlank : false,
						// 设置开始时间默认值
						fromValue : Ext.Date.format(
								new Date(new Date().getFullYear(), new Date().getMonth(),
										new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
						// 设置结束时间默认值
						toValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date()
												.getMonth(), new Date().getDate(), 23, 59, 59),
								'Y-m-d H:i:s'),
						columnWidth : .5
					},{
						xtype : 'combobox',
						name : 'esbCode',
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.esbCode'),
						columnWidth : .3,
						labelWidth : 95,					
						store:Ext.create('Foss.Stl.Closing.EsbCodeStore'),
        				queryModel:'local',
        				value:'',
        				displayField:'esbCodeName',
        				valueField:'esbCodeCode',
        		    	listeners:{}
					},{
						xtype : 'textfield',
						name : 'waybillNo',
						//emptyText : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.billNosEmptyText'),
						//354658-校验至14位运单号
						regex : /^[0-9]\d{6,13}$/,
						regexText : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.billNosRegexText'),
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.waybillNo'),
						columnWidth : .3,
						labelWidth : 90
					},{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : closing.queryFossEcsLog.i18n('stl.closing.common.reset'),
									columnWidth : .1,
									handler : function() {
										var form=this.up('form').getForm();										
										form.reset();													
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .8,
									html : '&nbsp;'
								}, {
									text : closing.queryFossEcsLog.i18n('stl.closing.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : function() {
										var form = this.up('form').getForm();
										var me = this;
										if (form.isValid()) {
											//设置该按钮灰掉
											me.disable(false);
											//2秒后自动解除灰掉效果
											setTimeout(function() {
												me.enable(true);
											}, 2000);
											if (closing.dateValidation(form)) {
												var dateGridStore = Ext.getCmp('Foss_Ecs_LogQueryInfoGrid_Id').store;
												dateGridStore.removeAll();
												dateGridStore.loadPage(1, {
													callback : function(records, operation,success) {
														var result = Ext.decode(operation.response);
														/*Ext.getCmp('Foss_Ecs_DateQueryInfoGrid_TotalRecordsInDB_Id')
															.setValue(2);*///result.vo.logEntityList.length);
														me.enable(true);
													}
												});
											}
										} else {
											Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'),
													closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.validateFailAlert'));
										}				
								}
							}]
					}]
		
		}]
	}]
	
});

// 日期查询model
Ext.define('Foss.Ecs.QueryMqLogEntryModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'waybillNo',
						type:'string'
					},{
						name : 'esbCode',
						type:'string'
					},{
						name : 'createTime',
						type:'long'
					}, {
						name : 'id',
						type:'string'
					}, {
						name : 'requestMsg',
						type:'string'
					},{
                        name:'responseMsg',
                        type:'string'
                    }
            ]
		});

/**
 * 定义查看明细的Form
 */
Ext.define('Foss.QueryFossEcsLog.ShowDetailsInforForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			width : stl.SCREENWIDTH * 0.95,
			frame : false,
			defaultType : 'textfield',
			defaults : {
				margin : '0 0 0 0',
				readOnly : true,
                labelWidth:90
			},
			items : [{
						fieldLabel : 'id',
						name : 'id',
						//hidden : true,
						columnWidth : .5
					},{
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.waybillNo'),
						name : 'waybillNo',
						columnWidth : .5
					},{
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.createTime'),
						name : 'createTime',
						columnWidth : .5
					},{
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.esbCode'),
						name : 'esbCode',
						columnWidth : .5						
					},{
						xtype : 'textarea',
						fieldLabel : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.request'),
						name : 'requestMsg',
						columnWidth : 1,
						grow : true,
						//width: 230,
						anchor : '100%'
					},{
						xtype : 'textarea',
                        fieldLabel: closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.reponse'),
                        name:'responseMsg',
                        columnWidth: 1,
                        grow : true,
						//width: 230,
						anchor : '100%'
            }]
		});

/**
 * 声明查看明细Window
 */
Ext.define('Foss.QueryFossEcsLog.ShowDetailsInforWindow', {
			extend : 'Ext.window.Window',
			width : stl.SCREENWIDTH ,
			height : stl.SCREENHEIGHT * 0.60,
			modal : true,
			constrainHeader : true,
			closeAction : 'hide',
			items : [Ext.create('Foss.QueryFossEcsLog.ShowDetailsInforForm')]
		});


// 日期查询列表Store
Ext.define('Foss.Ecs.DateQueryEntityStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.Ecs.QueryMqLogEntryModel',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				url : closing.realPath('queryEcsFossLog.action'),
				reader : {
					type : 'json',
					root : 'logList',
					totalProperty : 'totalCount'
				}
			}
		});

// 日期查询列表
Ext.define('Foss.Ecs.LogQueryInfoGrid', {
			extend : 'Ext.grid.Panel',
			title : '日期查询列表',
			frame : true,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			emptyText : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.emptyText'),
			height : 468,
			sortableColumns : false,			
			listeners : {
				'itemdblclick' : function(grid, record, item, index) {
					closing.checkInforByDoubleClick(record, closing.DATE);
				}
			},
			detailWindow : null,
			getDetailWindow : function() {
				if (Ext.isEmpty(this.detailWindow)) {
					this.detailWindow = Ext.create('Foss.QueryFossEcsLog.ShowDetailsInforWindow');
				}
				return this.detailWindow;
			},
			selModel:Ext.create('Ext.selection.CheckboxModel'),
			viewConfig : {
				enableTextSelection : true //设置行可以选择，
			},
			defaults:{
		  		align:'center'
		  	},
			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.createTime'),
				dataIndex : 'createTime',
				width : 150,
				renderer:function(value){
					if(value!=null){
						return stl.dateFormat(new Date(value),'Y-m-d H:i:s');
					}else{
						return null;
					}
					
				}
				
			},{
                header:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.waybillNo'),
                width : 110,
                dataIndex:'waybillNo'
            },{
                header:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.esbCode'),
                width : 100,
                dataIndex:'esbCode',
                renderer:function(value){
					if(value=='FOSS_ESB2FOSS_ECS_WAYBILL_BILLING'){
						return '开单生成单据';
					}else if(value=='FOSS_ESB2FOSS_COMPLEMENT_FUNCTION'){
						return '补码生成单据';
					} else if(value=='FOSS_ESB2FOSS_ECS_SYNC_WAYBILL'){
						return '运单信息同步';
					} else if(value=='FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT'){
						return '签收信息同步';
					} else if(value=='FOSS_ESB2FOSS_CHANGE_FINALNCIAL_BILL'){
						return '更改生成单据';
					}
					
				}
            },{
                header:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.request'),
                width : 300,
                dataIndex:'requestMsg'
            },{
                header:closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.reponse'),
                width : 300,
                dataIndex:'responseMsg'
           }]
		});

/**
 * 主启动方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询条件form表单
	var EcsQueryInfoTab = Ext.create('Foss.Ecs.EcsQueryInfoTab');

	// 创建按日期查询应收单列表Store
	var DateQueryInfoGridStore=Ext.create('Foss.Ecs.DateQueryEntityStore',{
		listeners:{
					beforeload : function(store, operation, eOpts) {
						var form = Ext.getCmp('Foss_Ecs_EcsQueryInfoTab_QueryByDate_ID');
						if (form) {
							var vo = new Object();
							var statTime;
							var endTime;						
							var waybillNo=form.getForm().findField('waybillNo').getValue();//运单号
							var esbCode =  form.getForm().findField('esbCode').getValue();//编码			
							statTime = form.getForm().findField('statTime').getValue();
							endTime = form.getForm().findField('endTime').getValue();
							var params = {
								'vo.startDate' : statTime,
								'vo.endDate' : endTime,
								'vo.waybillNo' : waybillNo,
								'vo.esbCode' : esbCode
							};
							Ext.apply(operation, {
										params : params
									});
						}
					}
				}
	});
	// 按日期查询应收单列表Grid
	var DateQueryInfoGrid = Ext.create('Foss.Ecs.LogQueryInfoGrid', {
		id : 'Foss_Ecs_LogQueryInfoGrid_Id',
		store : DateQueryInfoGridStore,
		dockedItems : [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					items : [{
								text : closing.queryFossEcsLog.i18n('foss.stl.closing.queryFossEcsLog.redirect'),//发送
								handler : function() {
									var grid = this.ownerCt.ownerCt;
									// grid 选中的行
									var dataList = grid.getSelectionModel().getSelection();
									if(dataList==null||dataList.length < 1){
										Ext.Msg.alert('温馨提示', '要选择一条数据');
										return false;
									}
									if(dataList.length==1){
										var waybillNo = dataList[0].data.waybillNo;
										var id=dataList[0].data.id;
										var requestMsg=dataList[0].data.requestMsg;
										var	esbCode=dataList[0].data.esbCode;
									}
									
									
									if (dataList.length > 1) {
										Ext.Msg.alert('温馨提示', '不能选择多条');
										return false;
									}
									// 判断当前部门是否为所选约车信息对应的派车顶级车队或者此顶级车队下的调度组
									Ext.Ajax.request({
										actionMethods : 'POST',
										async : true,
										url : closing.realPath('executeLogRequestMsg.action'),
										params : {
											'vo.logEntityList.id':id,
											'vo.logEntityList.waybillNo':waybillNo,
											'vo.logEntityList.requestMsg':requestMsg,
											'vo.logEntityList.esbCode':esbCode
										},
										success : function(response) {
											var result = Ext.decode(response.responseText);
											//重新加载数据
											closing.checkInforByReload(grid);
										},
										// 异常message
										exception : function(response) {
											var result = Ext.decode(response.responseText);
											Ext.Msg.alert(closing.queryFossEcsLog.i18n('stl.closing.common.alert'),
															result.message);
										}
									});									
									
								}							
					}]
				}, {
					xtype : 'toolbar',
					dock : 'bottom',
					layout : 'vbox',
					items : [{
								height : 5,
								width : 1600
							} , Ext.create('Deppon.StandardPaging', {
										store : DateQueryInfoGridStore,
										pageSize : 25,
										columnWidth : 1,
										plugins : Ext.create('Deppon.ux.PageSizePlugin', {
													// 设置分页记录最大值，防止输入过大的数值
													maximumSize : 200
												})
									})]
				}]
	});

	Ext.create('Ext.panel.Panel', {
				id : 'T_closing-queryFossEcsLog_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',				
				getGrid:function(){
					return DateQueryInfoGrid;
				},
				getTab:function(){
					return EcsQueryInfoTab;
				},
				items : [EcsQueryInfoTab,DateQueryInfoGrid],
				renderTo : 'T_closing-queryFossEcsLog-body'
			});
});