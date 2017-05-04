//卸车类型
Ext.define('Foss.Queryloadtask.QueryForm.taskType.Store', {
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//卸车状态
Ext.define('Foss.Queryloadtask.QueryForm.State.Store', {
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.queryunloadtask.UnloadTaskModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'unloadTaskNo' , type: 'string'},
		{name: 'gaprepNo' , type: 'string'},
		{name: 'unloadWay' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'line' , type: 'string'},
		{name: 'loaderName' , type: 'string'},
		{name: 'platformNo' , type: 'string'},
		{name: 'unloadType' , type: 'string'},
		{name: 'taskState' , type: 'string'},
		{name: 'arriveTime' , convert : dateConvert, type: 'date'},
		{name: 'unloadStartTime' , convert : dateConvert, type: 'date'},
		{name: 'unloadEndTime' , convert : dateConvert, type: 'date'}
	]
});

//装卸车人员Mode
Ext.define('Foss.unload.queryunloadtask.LoaderModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'loaderCode' , type: 'string'},
		{name: 'loaderName' , type: 'string'},
		{name: 'joinTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name: 'leaveTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}
	]
});

//LoadTaskStore
Ext.define('Foss.unload.queryunloadtask.UnloadTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.unload.queryunloadtask.UnloadTaskModel',
//	pageSize: 25,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url : unload.realPath('queryUnloadTask.action'),
		reader : {
			type : 'json',
			root : 'unloadTaskVo.unloadTaskDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = unload.unloadtaskquery.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'unloadTaskVo.unloadTaskDto.unloadTaskNo' : queryParams.unloadTaskNo,
					'unloadTaskVo.unloadTaskDto.vehicleNo' : queryParams.vehicleNo,
					'unloadTaskVo.unloadTaskDto.flightNo' : queryParams.flightNo,
					'unloadTaskVo.unloadTaskDto.loaderName' :queryParams.loaderName ,
					'unloadTaskVo.unloadTaskDto.unloadType' : queryParams.unloadType,
					'unloadTaskVo.unloadTaskDto.taskState' : queryParams.taskState,
					'unloadTaskVo.unloadTaskDto.beginDate' :queryParams.beginDate ,
					'unloadTaskVo.unloadTaskDto.endDate' : queryParams.endDate,
					'unloadTaskVo.unloadTaskDto.unloadWay' : queryParams.unloadWay,
					'unloadTaskVo.unloadTaskDto.billNo' : queryParams.billNo,
					'unloadTaskVo.unloadTaskDto.platformNo' : queryParams.platformNo
				}
			});
		}
	}
});

//查询条件
Ext.define('Foss.unload.queryunloadtask.QueryForm', {
	extend:'Ext.form.Panel',
	title: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.queryParam'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	id: 'Foss.unload.queryunloadtask.QueryForm.Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[{
		name: 'unloadTaskNo',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadTaskNo'),
		columnWidth: .25
	},{
		name: 'vehicleNo',
		xtype : 'commontruckselector',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.vehicleNo'),
		columnWidth: .25
	},{
		xtype: 'commonflightselector',
		fieldLabel: '航班号',
		name: 'flightNo',
		hidden:true,
		columnWidth:.25,
	},{
		name: 'loaderName',
		xtype: 'commonemployeeselector',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.loaderName'),
		columnWidth: .25
	},{
		xtype: 'combobox',
		name:'unloadType',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.Queryloadtask.QueryForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.ALL')},
					{'code':'LONG_DISTANCE','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.LONGDISTANCE')},
					{'code':'SHORT_DISTANCE','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.SHORTDISTANCE')},
					{'code':'DELIVER','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.DELIVER')},
					{'code':'DIVISION','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.DIVISION')},
					/*{'code':'PACKAGE_AIR','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.PACKAGEAIR')},*/
					{'code':'EXPRESS_PICK','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.EXPRESSDELIVER')},
					{'code':'BUSINESS_AIR','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.BUSINESSAIR')},
					{'code':'ELECTRANSPORT','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.ELECTRANSPORT')}//322610
				]
			}
		}),
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
		
	}, {
		xtype: 'combobox',
		name:'taskState',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.taskState'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.Queryloadtask.QueryForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.ALL')},
					{'code':'UNLOADING','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.UNLOADING')},
					{'code':'FINISHED','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.FINISHED')},
					{'code':'CANCELED','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.CANCELED')}
				]
			}
		})
	},{
		xtype: 'rangeDateField',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadStartTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_unload_queryunloadtask_LoadStartTime_Id',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true,
		columnWidth: .50
	},{
		xtype: 'combobox',
		name:'unloadWay',
		fieldLabel: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadWay'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.Queryloadtask.QueryForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.ALL')},
					{'code':'PDA','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.PDA')},
					{'code':'NO_PDA','name':unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.NOPDA')}
				]
			}
		})
	},{
		name: 'billNo',
		fieldLabel: '车次号',
		columnWidth: .25
	},{
		xtype: 'commonplatformselector',
		name:'platformNo',
		allowBlank: true,
		orgCode : unload.unloadtaskquery.outfieldCode,
		columnWidth: .25,
		fieldLabel:unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.platformNo')//月台号
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		}
	},{
		text: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.reset'),
		xtype:"button",
		columnWidth:.10,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
	},{
		text: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.query'),
		disabled: !unload.unloadtaskquery.isPermission('unload/queryUnloadTaskButton'),
		hidden: !unload.unloadtaskquery.isPermission('unload/queryUnloadTaskButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			if(!this.up('form').getForm().isValid()) {
				return;
			}
			unload.unloadtaskquery.pagingBar.moveFirst();
		}
	}]
});

//表格panel
Ext.define('Foss.unload.queryunloadtask.QueryResult', {
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	id: 'Foss_Queryunloadtask_QueryResult_id',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height: 500,
	store: null,
	emptyText: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.emptyResult'),
	//定义表格列信息
	columns: [{
			xtype:'actioncolumn',
			align : 'center',
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.operate'),
			width : 80,
	        items: [{
	            iconCls: 'deppon_icons_affirm',
	            tooltip: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.confirmUnloadTask'),
	            handler: function(grid, rowIndex, colIndex) {
	            	//unload.changelabelgoods.changelabelgoodsPrintAgain(record);
	            	var record = grid.getStore().getAt(rowIndex);
	            	if(record.get('unloadWay') == 'PDA'){
	            		Ext.MessageBox.confirm('提示', '提交卸车任务后将不能继续卸车,确定提交此卸车任务吗？',function(btn){
							if(btn == 'yes'){
								var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskqueryindex_content'),{
									msg:"数据提交中，请稍后..."
								});
								myMask.show();
								unloadTaskNo = record.get('unloadTaskNo');
								Ext.Ajax.request({
									url : unload.realPath('confirmPDAUnloadTask.action'),
									params : {'unloadTaskVo.unloadTaskNo' : unloadTaskNo},
									success : function(response){
										Ext.Msg.show({
										     title : '提示'/*'提示'*/,
										     msg : '操作成功，卸车任务已提交！',
										     buttons : Ext.Msg.OK,
										     icon : Ext.Msg.INFO
										});
										myMask.hide();
										//dudu
										record.set('taskState','FINISHED');
										record.set('unloadEndTime',new Date(Ext.decode(response.responseText).unloadTaskVo.unloadEndDate));
									},
									exception : function(response){
										var result = Ext.decode(response.responseText);
								    	top.Ext.MessageBox.alert('提示'/*'提示'*/,'操作失败，' + result.message);
								    	myMask.hide();
									}
								});
							}
						});
	            	}else{
	            		//卸车任务ID
		            	var unloadTaskId = record.get('id');
		            	//unload.changelabelgoods.changelabelgoodsPrintAgain(record);
		            	//卸车任务类型
		            	var unloadType = record.get('unloadType');
		            	if(unloadType == 'SHORT_DISTANCE'
		            		|| unloadType == 'PARTIALLINE'){
		            		//短途卸车确认界面
			            	unload.addTab('T_unload-unloadtaskconfirmindex',
			            			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else if(unloadType == 'LONG_DISTANCE'){
		            		unload.addTab('T_unload-unloadtaskconfirmlongindex',
		            				unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmlongindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else if(unloadType == 'BUSINESS_AIR'||unloadType=='PACKAGE_AIR'){
		            		unload.addTab('T_unload-unloadtaskconfirmindex',
		            				unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else if(unloadType == 'EXPRESS_LONG_DISTANCE'){
		            		unload.addTab('T_unload-unloadtaskconfirmlongindex',
		            				unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmlongindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else if(unloadType == 'EXPRESS_SHORT_DISTANCE'){
		            		unload.addTab('T_unload-unloadtaskconfirmindex',
			            			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else if(unloadType == 'EXPRESS_AIR_DISTANCE'){
		            		unload.addTab('T_unload-unloadtaskconfirmindex',
			            			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.manualConfirmUnloadTask'),
			            			unload.realPath('unloadtaskconfirmindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
		            	}else{
		            		alert(unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadTaskTypeError'));
		            	}
	            	}
	            }
	        },{
	            iconCls: 'deppon_icons_edit',
	            tooltip: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.modifyUnloadTask'),
	            handler: function(grid, rowIndex, colIndex) {
	            	var record = grid.getStore().getAt(rowIndex);
	            	var unloadTaskId = record.get('id');
	            	//unload.changelabelgoods.changelabelgoodsPrintAgain(record);
	            	unload.addTab('T_unload-unloadtaskmodifyindex',
	            			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.modifyUnloadTask'),
	            			unload.realPath('unloadtaskmodifyindex.action') + '?unloadTaskId="' + unloadTaskId + '"');
	            }
	        }, {
	        	iconCls: 'deppon_icons_cancel',
	        	tooltip : '取消任务',
	        	handler : function(grid, rowIndex, colIndex){
	        		//取消任务
					Ext.MessageBox.confirm('提示', '确定要取消此卸车任务吗？',function(btn){
						if(btn == 'yes'){
							var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskqueryindex_content'),{
								msg:"数据提交中，请稍后..."
							});
							myMask.show();
							var record = grid.store.getAt(rowIndex),
							unloadTaskId = record.get('id');
							unloadTaskNo=record.get('unloadTaskNo');
							Ext.Ajax.request({
								url : unload.realPath('cancelUnloadTask.action'),
								params : {
									'unloadTaskVo.unloadTaskId' : unloadTaskId,
									'unloadTaskVo.unloadTaskNo' : unloadTaskNo
									},
								success : function(response){
									Ext.Msg.show({
									     title : '提示'/*'提示'*/,
									     msg : '操作成功，卸车任务已取消！',
									     buttons : Ext.Msg.OK,
									     icon : Ext.Msg.INFO
									});
									grid.store.removeAt(rowIndex);
									myMask.hide();
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
							    	top.Ext.MessageBox.alert('提示'/*'提示'*/,'操作失败，' + result.message);
							    	myMask.hide();
								}
							});
						}
					});
	        	}
	        }],
            renderer:function(value, metadata, record){
            	if(record.data.taskState == 'UNLOADING'){
        			//当非PDA卸车状态为"进行中", 可以确认, 修改，取消
            		//当PDA卸车卸车状态为"进行中", 不可修改，不可取消,可以确认
        			this.items[0].iconCls = 'deppon_icons_affirm';
        			if(record.data.unloadWay == 'PDA'){
        				this.items[1].iconCls = '';
            			this.items[2].iconCls = '';
        			}else{
        				this.items[1].iconCls = 'deppon_icons_edit';
	        			this.items[2].iconCls = 'deppon_icons_cancel';
        			}
        		} else {
        			this.items[0].iconCls = '';
        			this.items[1].iconCls = '';
        			this.items[2].iconCls = '';
        		}
            }
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadTaskNo2'), 
			dataIndex: 'unloadTaskNo',
			flex : 1,
			renderer : function(value,metadata,record){
				var state=record.data.taskState;
				if(value != null){
					return '<a href="javascript:unload.unloadtaskquery.showUnloadWayBillDetail('+"'" + value+"'"+",'"+state+ "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			}
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.gaprepNo'), 
			dataIndex: 'gaprepNo',
			flex : 1,
			renderer : function(value){
				if(value != null){
					return '<a href="javascript:unload.unloadtaskquery.showUnloadDiffenences('+"'" + value + "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			}
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadWay'), 
			dataIndex: 'unloadWay',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'PDA':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.PDA');
					case 'NO_PDA':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.NOPDA');
					default: return value;
				}
			}
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.vehicleNo'), 
			dataIndex: 'vehicleNo',
			flex : 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.line'), 
			dataIndex: 'line',
			flex : 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.createName'),
			dataIndex: 'loaderName',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value != null){
					var id = record.get('id'),
						unloadWay = record.get('unloadWay');
					var param = id + "','" + unloadWay;
					return '<a href="javascript:unload.unloadtaskquery.showLoader('+"'" + param + "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			},
//			xtype: 'tipcolumn',
//			tipConfig: {
//		        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
//				maxWidth: 425,
//				width: 425,
//				height: 210,
//		        //Tip的Body是否随鼠标移动
//				trackMouse: true,
//				//Tip的隐藏延迟时间(单位:ms)
//				hideDelay: 2000
//			},
			//配置tip内引用的自定义组件类名
//			tipBodyElement:'Foss.unload.queryunloadtask.LoaderGrid',
			//自动填列
		    flex: 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.platformNo'),
			dataIndex: 'platformNo',
			flex : 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadType'),
			dataIndex: 'unloadType',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'LONG_DISTANCE':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.LONGDISTANCE');
					case 'SHORT_DISTANCE':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.SHORTDISTANCE');
					case 'DELIVER':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.DELIVER');
					case 'DIVISION':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.DIVISION');
					/*case 'PACKAGE_AIR':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.PACKAGEAIR');*/
					case 'EXPRESS_PICK':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.EXPRESSDELIVER');
					case 'BUSINESS_AIR':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.BUSINESSAIR');
					case 'ELECTRANSPORT'://322610
					return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.ELECTRANSPORT');
					case 'EXPRESS_LONG_DISTANCE':
					    return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.EXPRESSLONGDISTANCE');
					case 'EXPRESS_SHORT_DISTANCE':
				        return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.EXPRESSSHORTDISTANCE');
					case 'PACKAGE_AIR':
				    return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.BUSINESSAIR');
					default: return value;
				}
			}
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.taskState'), 
			dataIndex: 'taskState',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'UNLOADING':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.UNLOADING');
					case 'FINISHED':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.FINISHED');
					case 'CANCELED':
						return unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.CANCELED');
					default: return value;
				}
			}
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.arriveTime'), 
			dataIndex: 'arriveTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 1.8
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadStartTime'), 
			dataIndex: 'unloadStartTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 1.8
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadEndTime'), 
			dataIndex: 'unloadEndTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 1.8
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.unload.queryunloadtask.UnloadTaskStore');
//			me.selModel = Ext.create('Ext.selection.CheckboxModel');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			me.tbar = [{
				xtype : 'button',
				disabled: !unload.unloadtaskquery.isPermission('unload/unloadtaskaddnewButton'),
				hidden: !unload.unloadtaskquery.isPermission('unload/unloadtaskaddnewButton'),
				text : unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.addUnloadTask'),
				handler : function(){
					//新增卸车任务
					unload.addTab('T_unload-unloadtaskaddnewindex',
								unload.unloadtaskquery.i18n('foss.unload.unloadtaskaddnew.mainTab.title'),
								unload.realPath('unloadtaskaddnewindex.action'));
				}
			}, {
				xtype : 'button',
				text : unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.export'),
				disabled: !unload.unloadtaskquery.isPermission('unload/exportUnloadWayBillByTaskNoButton'),
				hidden: !unload.unloadtaskquery.isPermission('unload/exportUnloadWayBillByTaskNoButton'),
				name : 'export',
				handler : function(){
					var queryParams = unload.unloadtaskquery.queryform.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					Ext.Ajax.request({
						url:unload.realPath('exportUnloadTask.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'unloadTaskVo.unloadTaskDto.unloadTaskNo' : queryParams.unloadTaskNo,
							'unloadTaskVo.unloadTaskDto.vehicleNo' : queryParams.vehicleNo,
							'unloadTaskVo.unloadTaskDto.loaderName' :queryParams.loaderName ,
							'unloadTaskVo.unloadTaskDto.unloadType' : queryParams.unloadType,
							'unloadTaskVo.unloadTaskDto.taskState' : queryParams.taskState,
							'unloadTaskVo.unloadTaskDto.beginDate' :queryParams.beginDate ,
							'unloadTaskVo.unloadTaskDto.endDate' : queryParams.endDate,
							'unloadTaskVo.unloadTaskDto.unloadWay' : queryParams.unloadWay
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.exportFail'),result.message);
		    			}
					});
				}
			}],
			unload.unloadtaskquery.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});

//理货员信息
Ext.define('Foss.unload.queryunloadtask.LoaderGrid',{
	extend: 'Ext.grid.Panel',
	title: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.loaderName'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.loaderCode'), 
			dataIndex: 'loaderCode',
			flex : 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.loaderName'), 
			dataIndex: 'loaderName',
			flex : 1
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.joinTime'), 
			dataIndex: 'joinTime',
			flex : 1.8
		},{
			header: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.leaveTime'), 
			dataIndex: 'leaveTime',
			flex : 1.8
		}],
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//点击“卸车任务编号”打开详情界面方法
unload.unloadtaskquery.showUnloadWayBillDetail = function(unloadTaskNo,state){
	unload.addTab('T_unload-unloadtaskdetailqueryIndex',//对应打开的目标页面js的onReady里定义的renderTo
			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloadDetail'),//打开的Tab页的标题
			unload.realPath('unloadtaskdetailqueryindex.action?unloadTaskNo='+ unloadTaskNo+'&state='+state));//对应的页面URL，可以在url后使用?x=123这种形式传参
}
//点击“卸车差异编号”打开详情界面方法
javascript:unload.unloadtaskquery.showUnloadDiffenences = function(gaprepNo){
	unload.addTab('T_unload-unloaddiffenencesreportshowindex',
			unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.unloaDdiffenences'),
			unload.realPath('unloaddiffenencesreportshowindex.action') + '?diffReportNo="' + gaprepNo + '"');
}
//点击“理货员列”打开理货员窗口
javascript:unload.unloadtaskquery.showLoader = function(id, unloadWay){
	var grid = Ext.create('Foss.unload.queryunloadtask.LoaderGrid'),
		window = Ext.create('Ext.window.Window', {
		    title: unload.unloadtaskquery.i18n('foss.unload.unloadtaskquery.loaderName'),
		    height:400,
		    width:600,
		    closable:true,
			closeAction:'hide',
		    modal: true,
		    items: [
		            grid
	        ]
		});
	var params = {
		'unloadTaskVo.unloadTaskId' : id,
		'unloadTaskVo.unloadWay' : unloadWay
	};
	Ext.Ajax.request({
		url : unload.realPath('queryLoaderByTaskId.action'),
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText),
				store = grid.store;
			store.model = 'Foss.unload.queryunloadtask.LoaderModel';
			store.remoteSort = 'false';
			store.loadData(result.unloadTaskVo.loaderParticipationList);
			window.show()
		}
	});
}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.unload.queryunloadtask.QueryForm');
	unload.unloadtaskquery.queryform = queryform;
	var queryResult = Ext.create('Foss.unload.queryunloadtask.QueryResult');
	Ext.create('Ext.panel.Panel',{
		id: 'T_unload-unloadtaskqueryindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [queryform, queryResult],
		renderTo: 'T_unload-unloadtaskqueryindex-body'
	});
});