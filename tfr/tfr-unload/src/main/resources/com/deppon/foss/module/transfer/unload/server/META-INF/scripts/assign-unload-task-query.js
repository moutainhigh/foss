/**-----------------------------------------------QueryForm------------------------------**/
Ext.define('Foss.UnLoad.AssignUnloadTaskQuery.TaskStateStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"UNSTART", "name":"未开始"},
        {"value":"PROCESSING", "name":"进行中"},
        {"value":"FINISHED", "name":"已完成"},
        {"value":"CANCELED", "name":"已取消"},
        {"value":"ALL", "name":"全部"}
    ]
});
/*Ext.define('Foss.UnLoad.AssignUnloadTaskQuery.UnloadTypeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"DELIVER", "name":"集中卸车"},
        //{"value":"PARTIALLINE", "name":"偏线卸车"},
        {"value":"LONG_DISTANCE", "name":"长途卸车"},
        {"value":"SHORT_DISTANCE", "name":"短途卸车"},
        {"value":"ALL", "name":"全部"}
    ]
});*/
Ext.define('Foss.Unload.AssignUnloadTaskQuery.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 490,
	frame: true,
	border: false,
	title : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.queryCondition'),//'查询条件',
	defaults: {
		margin: '5 3 7 3',
		labelWidth: 100
	},
	items: [{
		xtype: 'commontruckselector',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.vehicleNo'),//'车牌号',
		name: 'vehicleNo',
		columnWidth:.25
	},{
		xtype: 'commonflightselector',
		fieldLabel: '航班号',
		name: 'flightNo',
		hidden:true,
		columnWidth:.25,
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.billNo'),//'单据编号',
		name: 'billNo',
		columnWidth:.25
	},{
		xtype: 'datetimefield_date97',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.assignTime'),//'分配时间',
		columnWidth: .25,
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,00,00,00),'Y-m-d H:i:s'),
		name: 'assignTimeBegin',
		id:'Foss_unload_assignUnloadTaskQuery_assignTimeBegin',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTaskQuery_assignTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.to'),//'至',
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .25,
		name: 'assignTimeEnd',
		id:'Foss_unload_assignUnloadTaskQuery_assignTimeEnd',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTaskQuery_assignTimeEnd-inputEl'
		}
	},{
		xtype: 'commonemployeeselector',
		//xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loader'),//'理货员',
		name: 'loader',
		columnWidth:.25
	},{
		xtype: 'dynamicorgcombselector',
		//xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loaderOrg'),//'理货员部门',
		name: 'loaderOrg',
		columnWidth:.25
	},
	FossDataDictionary.getDataDictionaryCombo('ASSIGNUNLOADTASK_UNLOADTYPE',{
		fieldLabel : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadType'),//'卸车类型',
		allowBlank : false,
		name : 'unloadType',
		value : 'ALL',
		editable : false,
		columnWidth:.25,
		listeners:{
			beforerender:{
				fn: function(src, newValue, oldValue, eOpts){
					this.up('form').getForm().findField('flightNo').setVisible(false);
				 } 
			},
				change:{
				fn : function(src, newValue, oldValue, eOpts){
					if(newValue == 'BUSINESS_AIR'){
						this.up('form').getForm().findField('flightNo').setVisible(true);
						this.up('form').getForm().findField('vehicleNo').setVisible(false);
						
					}
					else{
						this.up('form').getForm().findField('vehicleNo').setVisible(true);
						this.up('form').getForm().findField('flightNo').setVisible(false);
					}
				}
			}}
		
		
	})/*{
		xtype: 'combo',
		name: 'unloadType',
		editable :false,
		fieldLabel: '卸车类型',
		store: Ext.create('Foss.UnLoad.AssignUnloadTaskQuery.UnloadTypeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',
		columnWidth:.25
	}*/,/*FossDataDictionary.getDataDictionaryCombo('ASSIGNUNLOADTASK_TASKSTATE',{
		fieldLabel : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadState'),//'卸车状态',
		allowBlank : false,
		name : 'taskState',
		value : 'ALL',
		editable : false,
		columnWidth:.25
	})*/{
		xtype: 'combo',
		name: 'taskState',
		editable :false,
		fieldLabel:  unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadState'),//'卸车状态',
		store: Ext.create('Foss.UnLoad.AssignUnloadTaskQuery.TaskStateStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',
		columnWidth:.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.reset'),//'重置',
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.query'),//'查询',
			cls:'yellow_button',
			columnWidth:.08,
			disabled : !unload.assignunloadtaskquery.isPermission('unload/queryAssignUnloadTaskButton'),
			hidden : !unload.assignunloadtaskquery.isPermission('unload/queryAssignUnloadTaskButton'),
			handler:function(){
				if(this.up('form').getForm().isValid()){
					/*var params = this.up('form').getForm().getValues();
					Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_AssignUnloadTaskGrid_Id').store.load({
						params : {
							'vo.task.vehicle.vehicleNo':params.vehicleNo,
							'vo.task.vehicle.unloadType':params.unloadType,
							'vo.task.bill.billNo':params.billNo,
							'vo.task.loader.loaderCode':params.loader,
							'vo.task.loader.loaderCode':params.loaderOrg,
							'vo.task.state':params.taskState,
							'vo.task.queryTimeBegin':params.assignTimeBegin,
							'vo.task.queryTimeEnd':params.assignTimeEnd
						},
						callback : function(records, operation, success) {
							if (success == false) {
								var errorMessage = operation.request.proxy.reader.jsonData.message;
								Ext.Msg.alert('提示',errorMessage);
								Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_AssignUnloadTaskGrid_Id').store.removeAll();
							}
						}
					});*/
					var time1 = this.up('form').getForm().getValues().assignTimeBegin,
					time2 = this.up('form').getForm().getValues().assignTimeEnd,
					quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19)),
					queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
					if(queryTimeEnd>quertTimeBegin){
						if( queryTimeEnd.getTime() -quertTimeBegin.getTime() <= 31*24*60*60*1000){
							unload.assignunloadtaskquery.pagingBar.moveFirst();
						}else{
							//Ext.ux.Toast.msg('提示', '查询时间跨度不能超过31天!', 'error', 3000);
							Ext.ux.Toast.msg(unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.prompt'), unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.queryConditionError'), 'error', 3000);
						}
					}else{
						//Ext.ux.Toast.msg('提示', '查询开始时间不能大于查询截止时间!', 'error', 3000);
						Ext.ux.Toast.msg(unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.prompt'), unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.queryConditionError1'), 'error', 3000);
					}
				}else{
					//Ext.Msg.alert('提示','请补全查询条件!');
					//Ext.ux.Toast.msg('提示', '请补全查询条件!', 'error', 3000);
					Ext.ux.Toast.msg(unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.prompt'), unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.queryConditionError2'), 'error', 3000);
				}
			}
			},{
				text:unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.exportQuery'),//'导出'
				disabled : !unload.assignunloadtaskquery.isPermission('unload/queryAssignUnloadTaskButton'),
				hidden : !unload.assignunloadtaskquery.isPermission('unload/queryAssignUnloadTaskButton'),
				columnWidth:.08,
				handler:function(){
					var queryParams = this.up('form').getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
						url:unload.realPath('exportAssignunloadedtask.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'vo.task.vehicle.vehicleNo':queryParams.vehicleNo,
							'vo.task.vehicle.unloadType':queryParams.unloadType,
							'vo.task.bill.billNo':queryParams.billNo,
							'vo.task.loader.loaderCode':queryParams.loader,
							'vo.task.loader.loaderCode':queryParams.loaderOrg,
							'vo.task.state':queryParams.taskState,
							'vo.task.queryTimeBegin':queryParams.assignTimeBegin,
							'vo.task.queryTimeEnd':queryParams.assignTimeEnd
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('提示',result.message);
		    			}
					});
				}
			}]
	}]
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type:'string'},
		{name: 'vehicleNo',type:'string'},
		{name: 'vehicleType',type:'string'},
		{name: 'line',type:'string'},
		{name: 'unloadType',type:'string',
			convert: function(value) {
				if (value == 'DELIVER') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.deliver');//'派送';
				} else if (value == 'PARTIALLINE') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.partialLine');//'偏线';
				}else if (value == 'LONG_DISTANCE') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.long');//'长途';
				}else if (value == 'SHORT_DISTANCE') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.short');//'短途';
				}else if (value == 'PACKAGE_AIR'){
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air');//快递空运
				}else if(value == 'EXPRESS_PICK'){
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick');//快递集中卸车
				}else if(value == 'BUSINESS_AIR'){
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.BusinessAir');//商务专递卸车
				}else if(value == 'ELECTRANSPORT'){// 322610
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.ElecTransport');//零担电子运单卸车
				}else{
					return value;
				}
			}
		},
		{name: 'taskState',type:'string',
			convert: function(value) {
				if (value == 'UNSTART') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unstart');//'未开始';
				} else if (value == 'PROCESSING') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loading');//'进行中';
				}else if (value == 'FINISHED') {					
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.finished');//'已完成';
				}else if(value == 'CANCELED'){
					return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.canceled');//'已取消';
				}else{
					return value;
				}
			}
		},
		{name: 'arriveTime',type:'string'},
		{name: 'weightTotal',type:'number'},
		{name: 'volumeTotal',type:'number'},
		{name: 'goodsQtyTotal',type:'number'},
		{name: 'billQtyTotal',type:'number'},
		{name: 'platformNo',type:'string'},
		{name: 'modifyUserName',type:'string'},
		{name: 'fastWayBillQtyTotal',type:'number'},
		/**快递票数*/
		{name: 'expressWayBillQty',type:'number'},
		{name: 'createTime',type:'string'},
		{name: 'loaderName',type:'string'},
		{name: 'loaderCode',type:'string'},
		{name: 'loaderOrgName',type:'string'}
	]
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskModel',
	pageSize:10,
	proxy : {
		type : 'ajax',
		url : unload.realPath('queryAssignUnloadTask.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.totalTasks',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.dataNotFind'),//'查询结果为空',
	autoScroll:true,
	frame: true,
	border: false,
	//bodyCls: 'autoHeight',
	height:720,
	margin: '10 0 0 0',
	plugins: [{
		pluginId : 'assignUnloadTaskQuery_rowexpander_plugin_Id',
		ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement: 'Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailGrid'
	}],
	columns:[
			{
			xtype : 'actioncolumn',
			flex: 1,
			text : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.operation'),//'操作',//操作
			align : 'center',
			items : [ {
				tooltip : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.watch'),//'查看',//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					var record = grid.store.getAt(rowIndex);
					
					var unloadType;
					if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.deliver')){
						unloadType = 'DELIVER';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.long')){
						unloadType = 'LONG_DISTANCE';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.short')){
						unloadType = 'SHORT_DISTANCE';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air')){
						unloadType = 'PACKAGE_AIR';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick')){
						unloadType = 'EXPRESS_PICK';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.BusinessAir')){
						unloadType = 'BUSINESS_AIR';
					}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.ElecTransport')){
						unloadType = 'ELECTRANSPORT';// 322610
					}else{
						unloadType = 'PARTIALLINE';
					}
					
					Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_WindowGrid_Id').store.removeAll();
					unload.assignunloadtaskquery.mainMask.show();
					Ext.Ajax.request({
						url : unload.realPath('queryAssignUnloadTaskDetail.action'),
						params :{
							'vo.task.vehicle.vehicleNo':record.get('vehicleNo'),
							'vo.task.vehicle.unloadType':unloadType,
							'vo.task.createTime':record.get('createTime')
						},
						success:function(response){
							var result = Ext.decode(response.responseText);
							Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_WindowGrid_Id').store.loadData(result.vo.bills);
							unload.assignunloadtaskquery.assignUnloadTaskWindow.restore();
							Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_WindowForm_Id').getForm().loadRecord(record);
							Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_StatisticsWindowForm_Id').getForm().loadRecord(record);
							unload.assignunloadtaskquery.mainMask.hide();
							unload.assignunloadtaskquery.assignUnloadTaskWindow.show();
						},
						exception:function(response){
							unload.assignunloadtaskquery.mainMask.hide();
							var result = Ext.decode(response.responseText);
							//Ext.Msg.alert('提示',result.message);
							Ext.ux.Toast.msg(unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.finished'), result.message, 'error', 3000);
						}
					});
					}
				},{

					tooltip : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.cancel'),//取消
					iconCls : 'deppon_icons_delete',
					handler : function(grid, rowIndex, colIndex) {
						var record = grid.store.getAt(rowIndex);
						var unloadType;
						if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.deliver')){
							unloadType = 'DELIVER';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.long')){
							unloadType = 'LONG_DISTANCE';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.short')){
							unloadType = 'SHORT_DISTANCE';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air')){
							unloadType = 'PACKAGE_AIR';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick')){
							unloadType = 'EXPRESS_PICK';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.BusinessAir')){
							unloadType = 'BUSINESS_AIR';
						}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.ElecTransport')){
							unloadType = 'ELECTRANSPORT';// 322610
						}else{
							unloadType = 'PARTIALLINE';
						}
						Ext.MessageBox.confirm(unload.assignunloadtaskquery.i18n('foss.unload.assignunloadtask.prompt'),
						unload.assignunloadtaskquery.i18n('foss.unload.assignunloadtask.beConfirmCancel'),
						function(btn){
							if(btn == 'yes'){
								var myMask = new Ext.LoadMask(grid, {
									msg : unload.assignunloadtaskquery.i18n('任务取消中，请稍候...')
								});
								myMask.show();
								Ext.Ajax.request({
									url : unload.realPath('cancelAssignedUnloadTask.action'),
									params :{
										'vo.task.vehicle.vehicleNo':record.get('vehicleNo'),
										'vo.task.vehicle.unloadType':unloadType,
										'vo.task.id':record.get('id'),
										'vo.task.createTime':record.get('createTime')
									},
									timeout : 1800000,
									success:function(response){
										unload.assignunloadtaskquery.pagingBar.moveFirst();
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert(unload.assignunloadtaskquery.i18n('foss.unload.assignunloadtask.prompt'),
												'取消成功');
										myMask.hide();
									},
									exception : function(response) {
					    				var result = Ext.decode(response.responseText);
					    				top.Ext.MessageBox.alert(unload.assignunloadtaskquery.i18n('foss.unload.assignunloadtask.prompt'),result.message);
					    				myMask.hide();
					    			}
								});
							}
						});
				}
				} ]
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.vehicleNo'),//'车牌号',
			dataIndex : 'vehicleNo', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.vechileType'),//'车型',
			dataIndex : 'vehicleType', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.line'),//'线路',
			dataIndex : 'line', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadType'),//'卸车类型',
			dataIndex : 'unloadType', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadState'),//'卸车状态',
			dataIndex : 'taskState', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.arriveTime'),//'到达时间',
			dataIndex : 'arriveTime', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.weightTotal'),//'总重量',
			dataIndex : 'weightTotal', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.volumeTotal'),//'总体积',
			dataIndex : 'volumeTotal', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.goodsQtyTotal'),//'总件数',
			dataIndex : 'goodsQtyTotal', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.billQty'),//'单据数',
			dataIndex : 'billQtyTotal', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.platformNo'),//'月台号',
			dataIndex : 'platformNo', flex: 0.8},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.assignUnloader'),//'分配人',
			dataIndex : 'modifyUserName', flex: 0.8},	
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.fastWayBillQty'),//'卡货票数',
			dataIndex : 'fastWayBillQtyTotal', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressWayBillQty'),//'快递票数',
				dataIndex : 'expressWayBillQty', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.assignTime'),//'分配时间',
			dataIndex : 'createTime', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loader'),//'理货员',
			dataIndex : 'loaderName', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loaderCode'),//'工号',
			dataIndex : 'loaderCode', flex: 1},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.org'),//'部门',
			dataIndex : 'loaderOrgName', flex: 1}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					unload.assignunloadtaskquery.queryButtonMask.show();
					var queryParams = Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_QueryForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.task.vehicle.vehicleNo':queryParams.vehicleNo,
							'vo.task.vehicle.flightNo':queryParams.flightNo,
							'vo.task.vehicle.unloadType':queryParams.unloadType,
							'vo.task.bill.billNo':queryParams.billNo,
							'vo.task.loader.loaderCode':queryParams.loader,
							'vo.task.loader.orgCode':queryParams.loaderOrg,
							'vo.task.state':queryParams.taskState,
							'vo.task.queryTimeBegin':queryParams.assignTimeBegin,
							'vo.task.queryTimeEnd':queryParams.assignTimeEnd
						}
					});	
				},
				'load' : function(store, operation, eOpts){
					unload.assignunloadtaskquery.queryButtonMask.hide();
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
		});
		unload.assignunloadtaskquery.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type:'string'},
	    {name: 'billNo',type:'string'},
	    {name: 'origOrgName',type:'string'},
	    {name: 'weightTotal',type:'string'},
	    {name: 'goodsQtyTotal',type:'string'},
	    {name: 'fastWayBillQtyTotal',type:'string'},
	    {name: 'volumeTotal',type:'string'},
	    {name: 'expressWayBillQty',type:'string'},
		{name: 'unloadEndTime',type:'string'},
		{name: 'unloadBeginTime',type:'string'}
	]
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailStore',{
	extend:'Ext.data.Store',
	model:'Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'vo.bills'
		}
	}
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailGrid',{
	extend: 'Ext.grid.Panel',
	autoScroll:true,
	frame: true,
	border: false,
	bodyCls: 'autoHeight',
	//height:720,
	layout: 'column',
	margin: '10 0 0 0',
	columns:[
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.billNo'),//'单据编号',
			dataIndex : 'billNo'
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.origOrg'),//'出发部门',
			dataIndex : 'origOrgName',renderer: function(value){
				if(value){
					if (value == 'RECEIVE') {					
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.receive');//'接货';
					} else if(value == 'GOODS_BACK'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.goodsBack');//'拉回货';
					}else if(value =='PACKAGE_AIR'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air');//快递空运
					}else if(value == 'EXPRESS_PICK'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick1');//快递接货
					}else{
						return value;
					}
				}else{
					return '';
				}
			}
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.taskBeginTime'),//'任务开始时间',
			dataIndex : 'unloadBeginTime'},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.taskEndTime'),//'任务结束时间',
			dataIndex : 'unloadEndTime'}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailStore');
		me.callParent([cfg]);
	},
	bindData :function(record){
		var grid = this;
		var unloadType;
		if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.deliver')){
			unloadType = 'DELIVER';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.long')){
			unloadType = 'LONG_DISTANCE';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.short')){
			unloadType = 'SHORT_DISTANCE';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air')){
			unloadType = 'PACKAGE_AIR';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick')){
			unloadType = 'EXPRESS_PICK';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.BusinessAir')){
			unloadType = 'BUSINESS_AIR';
		}else if(record.get('unloadType') == unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.ElecTransport')){
			unloadType = 'ELECTRANSPORT';//322610
		}else{
			unloadType = 'PARTIALLINE';
		}
		var params = {
				'vo.task.vehicle.vehicleNo':record.get('vehicleNo'),
				'vo.task.vehicle.unloadType':unloadType,
				'vo.task.createTime':record.get('createTime')
		};
		Ext.Ajax.request({
			url : unload.realPath('queryAssignUnloadTaskDetail.action'),
			params :params,
			success:function(response){
				var result = Ext.decode(response.responseText);
				grid.store.loadData(result.vo.bills);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				//Ext.Msg.alert('提示',result.message);
				Ext.ux.Toast.msg(unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.prompt'), result.message, 'error', 3000);
			}
		});
	}
});

/**-----------------------------------------------明细窗口--------------------------------------------------**/
Ext.define('Foss.Unload.AssignUnloadTaskQuery.WindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 490,
	frame: true,
	border: false,
	//title : '查询条件',
	defaults: {
		margin: '5 3 7 3',
		labelWidth: 90
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.vehicleNo'),//'车牌号',
		readOnly:true,
		name: 'vehicleNo',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.vechileType'),//'车型',
		readOnly:true,
		name: 'vehicleType',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.arriveTime'),//'车辆到达时间',
		labelWidth: 90,
		readOnly:true,
		name: 'arriveTime',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.assignTime'),//'分配时间',
		readOnly:true,
		name: 'createTime',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.line'),//'线路',
		readOnly:true,
		name: 'line',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loaderCode'),//'工号',
		readOnly:true,
		name: 'loaderCode',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.loader'),//'理货员',
		readOnly:true,
		name: 'loaderName',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.org'),//'部门',
		readOnly:true,
		name: 'loaderOrgName',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadType'),//'卸车类型',
		readOnly:true,
		name: 'unloadType',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.unloadState'),//'卸车状态',
		readOnly:true,
		name: 'taskState',
		columnWidth:.33
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.platformNo'),//'月台号',
		readOnly:true,
		name: 'platformNo',
		columnWidth:.33
	}]
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.StatisticsWindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 490,
	frame: true,
	border: false,
	//title : '查询条件',
	defaults: {
		margin: '5 3 5 3',
		labelWidth: 90
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.volumeTotal'),//'总体积',
		readOnly:true,
		name: 'volumeTotal',
		columnWidth:.21
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.weightTotal'),//'总重量',
		readOnly:true,
		name: 'weightTotal',
		columnWidth:.21
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.goodsQtyTotal'),//'总件数',
		readOnly:true,
		name: 'goodsQtyTotal',
		columnWidth:.21
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.fastWayBillQtyTotal'),//'卡货总票数',
		labelWidth: 90,
		readOnly:true,
		name: 'fastWayBillQtyTotal',
		columnWidth:.22
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressWayBillTotalQty'),//'快递总票数',
		labelWidth: 90,
		readOnly:true,
		name: 'expressWayBillQty',
		columnWidth:.22
	},{
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.billQty'),//'单据数',
		readOnly:true,
		name: 'billQtyTotal',
		columnWidth:.18
	}]
});
Ext.define('Foss.Unload.AssignUnloadTaskQuery.WindowGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.dataNotFind'),//'查询结果为空',
	autoScroll:true,
	frame: true,
	border: false,
	height:300,
	width: 720,
	columns:[
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.billNo'),//'单据编号',
			dataIndex : 'billNo',flex:1.8
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.origOrg'),//'出发部门',
			dataIndex : 'origOrgName',flex:1.8,
			sortable :false,
			renderer: function(value){
				if(value){
					if (value == 'RECEIVE') {					
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.receive');//'接货';
					} else if(value == 'GOODS_BACK'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.goodsBack');//'拉回货';
					}else if(value =='PACKAGE_AIR'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.air');//快递空运
					}else if(value == 'EXPRESS_PICK'){
						return unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressPick1');//快递接货
					}else{
						return value;
					}
				}else{
					return '';
				}
			}
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.taskBeginTime'),//'任务开始时间',
			dataIndex : 'unloadBeginTime',flex:1.8,},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.taskEndTime'),//'任务结束时间',
			dataIndex : 'unloadEndTime',flex:1.8,},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.weightTotal1'),//'总重量(公斤)',
			dataIndex : 'weightTotal',flex:1.8
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.volumeTotal1'),//'总体积(方)',
			dataIndex : 'volumeTotal',flex:1.8
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.goodsQtyTotal'),//'总件数',
			dataIndex : 'goodsQtyTotal',flex:1.8
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.fastWayBillQtyTotal'),//'卡货总票数',
			dataIndex : 'fastWayBillQtyTotal',flex:1.8
		},
		{text: unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.expressWayBillTotalQty'),//'快递总票数',
			dataIndex : 'expressWayBillQty',flex:1.8
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskDetailStore');
		me.callParent([cfg]);
	}
});
unload.assignunloadtaskquery.assignUnloadTaskWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 750,
	//height : 390,
	modal:true,
    layout: 'column',
	title:unload.assignunloadtaskquery.i18n('foss.unload.AssignUnloadTaskQuery.assignedTaskDetail'),//'已分配卸车任务明细',//详细信息
	defaults: {
		margin:'5 5 5 5',
		labelWidth:85,
	},
	items:[
	       Ext.create('Foss.Unload.AssignUnloadTaskQuery.WindowForm',{id:'Foss_Unload_AssignUnloadTaskQuery_WindowForm_Id'}),
	       Ext.create('Foss.Unload.AssignUnloadTaskQuery.WindowGrid',{id:'Foss_Unload_AssignUnloadTaskQuery_WindowGrid_Id'}),
	       Ext.create('Foss.Unload.AssignUnloadTaskQuery.StatisticsWindowForm',{id:'Foss_Unload_AssignUnloadTaskQuery_StatisticsWindowForm_Id'})
	]
});
/**-----------------------------------------------view panel--------------------------------------------------**/
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_unload-assignunloadtaskqueryindex_content',
		frame:false,
		//style:'padding-top:10px',
		items : [Ext.create('Foss.Unload.AssignUnloadTaskQuery.QueryForm',{id:'Foss_Unload_AssignUnloadTaskQuery_QueryForm_Id'}),
		         Ext.create('Foss.Unload.AssignUnloadTaskQuery.AssignUnloadTaskGrid',{id:'Foss_Unload_AssignUnloadTaskQuery_AssignUnloadTaskGrid_Id'})],
		listeners: {
 		    'boxready': function(){
 		    	unload.assignunloadtaskquery.queryButtonMask = new Ext.LoadMask(Ext.getCmp('Foss_Unload_AssignUnloadTaskQuery_QueryForm_Id').items.items[9].items.items[3],{
 		    		msg:""
 		    	});
 		    	unload.assignunloadtaskquery.mainMask = new Ext.LoadMask(Ext.getCmp('T_unload-assignunloadtaskqueryindex_content'),{
 		    		msg:""
 		    	});
 		    }
 		},	
		renderTo: 'T_unload-assignunloadtaskqueryindex-body'
	});
});
