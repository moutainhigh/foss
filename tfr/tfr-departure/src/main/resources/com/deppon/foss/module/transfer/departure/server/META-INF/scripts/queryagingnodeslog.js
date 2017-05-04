//修改出发到达时间form
Ext.define('Foss.departure.queryagingnodeslog.modifyForm',{
	extend : 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:130
	},
	items : [{
		fieldLabel : departure.queryagingnodeslog.i18n('tfr.departure.modifyForm.form.billNo.label'),//'配载车次号/交接单号',
		name : 'billNo',
		allowBlank:false,
		listeners:{
			'blur':function(cmp, object, eOpts ){
				var billNo=cmp.getValue();
				var form = this.up('form').getForm();
				if(!Ext.isEmpty(billNo)&&billNo!=''){
					Ext.Ajax.request({
						url: departure.realPath('queryRelationbillNos.action'),
						
						jsonData:  {'agingNodesLogVo':{'billNo':billNo}} ,
						
						success: function(response){
						    var result = Ext.decode(response.responseText);
							 departure.queryagingnodeslog.relationbillNos = result.agingNodesLogVo.billNo;
							
							if(!Ext.isEmpty(departure.queryagingnodeslog.relationbillNos)
											&&departure.queryagingnodeslog.relationbillNos!=''){
								//Ext.getCmp('relationbillNos').setText("关联单号:"+departure.queryagingnodeslog.relationbillNos);												
								form.findField('relationbillNos').setValue(departure.queryagingnodeslog.relationbillNos);
							}else{
								Ext.ux.Toast.msg('错误提示',"不存在该单号:"+billNo+"的车辆任务！");
							
							}
							
						  },
						  
						exception : function(response){
						 	var result = Ext.decode(response.responseText);
						 	Ext.ux.Toast.msg('错误提示',result.message);
						 	//设置关联单号为空
						 	form.findField('relationbillNos').setValue('');
						 	cmp.setValue('');
						}
					});
				}
				
			}
		},
		columnWidth:.99	
	},{
		//xtype: 'label',
		fieldLabel : "关联单号",//'关联单号',
		labelWidth:80,
		name : 'relationbillNos',
		readOnly:true,
		//enable:false,
		columnWidth:.99
	},{
		xtype : 'combobox',
		name : 'modifyType',
		fieldLabel : departure.queryagingnodeslog.i18n('tfr.departure.modifyForm.form.modifyType.label'),//'修改类型',
		queryMode: 'local',
    	displayField: 'value',
    	valueField : 'key',
    	editable : false,
    	allowBlank:false,
    	store : Ext.create('Ext.data.Store',{
    		fields : ['key','value'],
    		data : [{"key":"DEPART_TIME" ,"value":"出发时间"},{"key":"ARRID_TIME" ,"value":"到达时间"}]
    	}),
    	columnWidth:.99	
	},{
		xtype: 'datetimefield_date97',
		fieldLabel: departure.queryagingnodeslog.i18n('tfr.departure.modifyForm.form.afterModifyTime.label'),//'修改后时间',
		name: 'afterModifyTime',
		editable : true,
		allowBlank:false,
		time : true,
		//设置为可编辑
		allowBlank:false,
		id : 'Foss_departure_queryagingnodeslog_afterModifyTime_ID',			
		dateConfig : {
			el : 'Foss_departure_queryagingnodeslog_afterModifyTime_ID-inputEl'
		},
		columnWidth:.99	
	},{
	
        xtype: 'container',
        columnWidth:1,
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
        items: [{
			xtype : 'button',
			columnWidth:.15,
			text: departure.queryagingnodeslog.i18n('tfr.departure.modifyForm.form.cancelButton.label'),//'取消'
			handler: function() {	
				//this.up('form').getForm().findField('vo.planDto.destOrgCode').reset();
				this.up('window').close();
			}
		},{
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			columnWidth:.15,
			xtype : 'button',
			text: departure.queryagingnodeslog.i18n('tfr.departure.modifyForm.form.submitButton.label'),//'提交',
			handler: function() {
				var form=this.up('form');
				if(form.getForm().isValid()){
					var param =form.getForm().getValues();
			
					//设置为不可编辑
					this.disable(true);	
					var the=this;
					//影藏窗口
					form.up('window').close();
					//提示窗口
					Ext.Msg.confirm('提示', "修改的单号有:"+departure.queryagingnodeslog.relationbillNos+"请再次确认是否修改！", function(optional){
    					if(optional != 'no'){
    							var jsonParam = {agingNodesLogVo:{billNo:param.billNo,modifyType:param.modifyType,
    							afterModifyTime:param.afterModifyTime,relationbillNos:param.relationbillNos}};
								var myMask = new Ext.LoadMask(Ext.getCmp('T_departure-queryagingnodeslogindex_content'), {
									msg : '数据提交中，请稍候....'
								});
								myMask.show();
								Ext.Ajax.request({
									url : departure.realPath('saveAgingNodesLog.action'),
									timeout: 300000,
					    			jsonData:jsonParam,
									success : function(response){
										myMask.hide();
										if(the){
											the.enable(true);
										}
										if(!Ext.isEmpty(form)){
											form.getForm().findField('billNo').setValue('');
											form.getForm().findField('modifyType').setValue('');
											form.getForm().findField('afterModifyTime').setValue('');
											form.getForm().findField('relationbillNos').setValue('');
											form.up('window').close();
										}
										//初始化查询条件
										var queryForm = departure.queryagingnodeslog.queryform;
										queryForm.getForm().findField('billNo').setValue(param.billNo);
										//刷新查询界面
										departure.queryagingnodeslog.querygrid.store.load({
											params : { 'agingNodesLogVo.billNo':param.billNo}
										});
										Ext.ux.Toast.msg('提示','修改成功！');
									},
									
									exception : function(response){
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg('错误提示',result.message);
										//top.Ext.MessageBox.alert("错误提示",result.message);
										if(the){
											the.enable(true);
										}
										//弹出新增发车计划窗口
										if(departure.queryagingnodeslog.ModifyWindow==null){
											departure.queryagingnodeslog.ModifyWindow=Ext.create('Foss.departure.queryagingnodeslog.ModifyWindow');
										}
										departure.queryagingnodeslog.ModifyWindow.show();
									},
									
									failure : function(){
										myMask.hide();
									}
								})	  
    					}else{
    						//弹出新增发车计划窗口
							if(departure.queryagingnodeslog.ModifyWindow==null){
								departure.queryagingnodeslog.ModifyWindow=Ext.create('Foss.departure.queryagingnodeslog.ModifyWindow');
							}
							departure.queryagingnodeslog.ModifyWindow.show();
	    					//设置提交按钮可编辑
    						if(the){
								the.enable(true);
							}
    					}
					});
					
						
					}
				
				
			}
		}]
				    
	}],
	constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
});
//修改出发/到达时间window
Ext.define('Foss.departure.queryagingnodeslog.ModifyWindow',{
	extend : 'Ext.window.Window',
	title : departure.queryagingnodeslog.i18n('tfr.departure.ModifyWindow.window.title'),//'修改出发/到达时间',
	modal:true,
	closeAction:'hide',
	width: 330,
	bodyCls: 'autoHeight',
	layout: 'auto',
	items :[departure.queryagingnodeslog.ModifyForm=Ext.create('Foss.departure.queryagingnodeslog.modifyForm')]
});



Ext.define('Foss.departure.queryagingnodeslog.agingNodesQueryFrom',{
	extend : 'Ext.form.Panel',
	title : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesQueryFrom.form.title'),//'查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 140,
		cloumnWidth :　1/2,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesQueryFrom.form.billNo.label'),//'配载车次号/交接单号',
		allowBlank:false,
		name : 'billNo'
	}, {
		border : false,
		xtype : 'container',
		layout : 'column',
		columnWidth : 1,
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{ 
			xtype : 'button',
			text :departure.queryagingnodeslog.i18n('tfr.departure.agingNodesQueryFrom.form.resetbutton.label'),// '重置',
			columnWidth : .08,
			handler :function(){
				this.up('form').getForm().reset();
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			xtype : 'button',
			columnWidth : .08,
			cls : 'yellow_button',
			disabled : !departure.queryagingnodeslog.isPermission('departure/queryModifyLog'),
			hidden : !departure.queryagingnodeslog.isPermission('departure/queryModifyLog'),
			text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesQueryFrom.form.querybutton.label'),//'查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					var param = form.getValues();
					departure.queryagingnodeslog.querygrid.store.load({
						params : { 
						   'agingNodesLogVo.billNo':param.billNo
							
						}
						
					});
//					Ext.getCmp('Foss_departure_QueryagingNodesLog_agingNodesGrid_id').store.load({
//						params : { 
//						   'agingNodesLogVo.billNo':param.billNo
//							
//						}
//						
//					})
				}
			}
		}]
	}]
	
});

//日志列表model
Ext.define('Foss.departure.queryagingnodeslog.agingNodesModel',{
	extend : 'Ext.data.Model',
	fields :[{
		name : 'modifyName',
		type : 'string'
	}, {
		name : 'modifyCode',
		type : 'string'
	},{
		name : 'modifyType',
		type : 'string'
	},{
	   name : 'modifyTime',
	   type : 'date',
	   convert : dateConvert
	},{
		name : 'beforeModifyTime',
		type : 'date',
		convert : dateConvert
	},{
		name : 'afterModifyTime',
		type : 'date',
		convert : dateConvert
	}]
});

//日志列表store
Ext.define('Foss.departure.queryagingnodeslog.agingNodesStore',{
	extend : 'Ext.data.Store',
	autuo:true,
	autoLoad: false,
	pageSize : 20,
	model : 'Foss.departure.queryagingnodeslog.agingNodesModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : departure.realPath('queryAgingNodesLog.action'),
		reader : {
			type : 'json',
			root : 'agingNodesLogVo.agingNodesLogs',
			successProperty : 'success'
		}
	}
//	},listeners: {
//		'load' : function( store, records, successful, eOpts){
//			if(successful){
//				if(records.length==0){
//					Ext.ux.Toast.msg("haha", "eeee", 'success', 5000);
//				}
//			}
//		}
//	}
	
	
});

//日志列表grid
Ext.define('Foss.departure.queryagingnodeslog.agingNodesGrid',{
	extend : 'Ext.grid.Panel',
	title : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.title'),//'日志列表',
	emptyText : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.emptyText'),//'查询结果为空',
	frame : true,
	//border : true,
	autoScroll : true,
	collapsible: true,
	animCollapse: false,
	bodyCls:'autoHeight',
	cls:'autoHeight',
	//store : Ext.create('Foss.departure.queryagingnodeslog.agingNodesStore'),
//	tbar : [{
//			xtype : 'button',
//			hidden : !departure.queryagingnodeslog.isPermission('departure/modifyagingnodes'),
//			text : '修改出发/到达时间',
//			handler: function() {
//				//弹出新增发车计划窗口
//				if(departure.queryagingnodeslog.ModifyWindow==null){
//					departure.queryagingnodeslog.ModifyWindow=Ext.create('Foss.departure.queryagingnodeslog.ModifyWindow');
//				}
//				departure.queryagingnodeslog.ModifyWindow.show();
//		}
//		}],
	columns : [{
		text :  departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.modifyName.label'),//'修改人姓名',
		width : 130,
		align : 'center',
		dataIndex : 'modifyName'
		
	},{
		text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.modifyCode.label'),//'修改人工号',
		width : 130,
		align : 'center',
		dataIndex : 'modifyCode'
	},{
		text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.modifyType.label'),//'修改类别',
		width : 130,
		align : 'center',
		dataIndex : 'modifyType',
		renderer:function(value){
			if(value=="DEPART_TIME"){
				return "出发时间"; 
			}else if(value=="ARRID_TIME"){
				return "到达时间" ;
			}else{
				return "";
			}
		}
	},{
		text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.modifyTime.label'),//'操作时间',
		width : 150,
		align : 'center',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		dataIndex : 'modifyTime'
	},{
		text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.beforeModifyTime.label'),//'修改前时间',
		width : 150,
		align : 'center',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		dataIndex : 'beforeModifyTime'
	},{
		text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.afterModifyTime.label'),//'修改后时间',
		align : 'center',
		width : 150,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		dataIndex : 'afterModifyTime'
	}]
	,
	constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.departure.queryagingnodeslog.agingNodesStore');
		me.tbar= [{
			xtype : 'button',
			disabled : !departure.queryagingnodeslog.isPermission('departure/modifyagingnodes'),
			hidden : !departure.queryagingnodeslog.isPermission('departure/modifyagingnodes'),
			text : departure.queryagingnodeslog.i18n('tfr.departure.agingNodesGrid.grid.modifybutton.label'),//'修改出发/到达时间',
			handler: function() {
				//弹出新增发车计划窗口
				if(departure.queryagingnodeslog.ModifyWindow==null){
					departure.queryagingnodeslog.ModifyWindow=Ext.create('Foss.departure.queryagingnodeslog.ModifyWindow');
				}
				departure.queryagingnodeslog.ModifyWindow.show();
		}
		}];
		me.callParent([cfg]);
	}
});



departure.queryagingnodeslog.queryform = Ext.create('Foss.departure.queryagingnodeslog.agingNodesQueryFrom');
departure.queryagingnodeslog.querygrid = Ext.create('Foss.departure.queryagingnodeslog.agingNodesGrid',{id:'Foss_departure_QueryagingNodesLog_agingNodesGrid_id'});

Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id : 'T_departure-queryagingnodeslogindex_content',
		layout : 'auto',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [departure.queryagingnodeslog.queryform,departure.queryagingnodeslog.querygrid],
		renderTo : 'T_departure-queryagingnodeslogindex-body'
				
	});
});