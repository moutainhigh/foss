baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
		url : url,
		jsonData : params,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
baseinfo.callCenterWaybillInfo.status ='Y';
baseinfo.callCenterWaybillInfo.noStatus ='N';
baseinfo.callCenterWaybillInfo.baseinfoRealPath = ContextPath.BSE_BASEINFO+"/baseinfo/";
baseinfo.callCenterWaybillInfo.query =function(){
	var form = Ext.getCmp('T_baseinfo-callCenterWaybillInfo_content').getSendMsgQueryForm().form;
	var billNo = form.findField('waybillNo').getValue();
	var grid = Ext.getCmp('T_baseinfo-callCenterWaybillInfo_content').getAcceptMsgOpeDataGrid();
		//定义查询参数
		var params={'callCenterWaybillInfoEntity.waybillNo':billNo,
				'callCenterWaybillInfoEntity.dealDept':FossUserContext.getCurrentUserDept().unifiedCode};
		//设置催单信息参数到DTO中
		Ext.apply(grid.store.proxy.extraParams,params);
		grid.store.load(); 
}

//催单信息发送表单查询FORM
Ext.define('Foss.Messages.SendMsgQueryForm',{
	extend:'Ext.form.Panel',
	frame:false,
	//collapsible:true,
	//animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth :65,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'column',
		columns : 8
	},
	items:[{
		        xtype : 'textfield',
		        columnWidth :.2,
		        labelWidth :50,
				name: 'waybillNo',
			    fieldLabel:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.billNo'),
			},{
	 		  	xtype:'button',
	 		  	text:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.query'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		baseinfo.callCenterWaybillInfo.query();
	 		  	}
	 	  },{
	 		  	xtype:'button',
	 		  	text:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.resolved'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		var grid = Ext.getCmp('T_baseinfo-callCenterWaybillInfo_content').getAcceptMsgOpeDataGrid();
	 		  		var callBackMsg = Ext.getCmp('Foss.baseinfo.SendMsgOpeCallCenterDataForm_id').getForm().findField('callBackMsg').getValue();           
	 		  		var entitys =new Array();
	 		  		var selections=grid.getSelectionModel().getSelection();
	 		  		if(selections.length<1){
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.alertInfoOne'));
	 		  			return;
	 		  		}else if(selections.length>1){
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.alertInfoTwo'));
	 		  			return;
	 		  		}
	 		  		
	 		  		if(callBackMsg.length>330){  //判断反馈信息
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.callBackMsgToLength'));
	 		  			return;
	 		  		}
	 		  		selections[0].set('hasDone',baseinfo.callCenterWaybillInfo.status);
	 		  		selections[0].set('callBackMsg',callBackMsg);
	 		  		selections[0].set('dealUser',FossUserContext.getCurrentUserEmp().empCode);
 		  			selections[0].set('dealUserName',FossUserContext.getCurrentUserEmp().empName);
 		  			selections[0].set('dealDept',FossUserContext.getCurrentUserDept().unifiedCode);
	 		  		var params = {'callCenterWaybillInfoEntity':selections[0].data};
	    		 	var successFun = function(json){
	    		 		Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),json.message);
	    		 		baseinfo.callCenterWaybillInfo.query();
	    			};
	    			var failureFun =function(json){
	    				if(Ext.isEmpty(json)){
	    					
	    				}else{
	    					
	    				}
	    			};
	    			var url = baseinfo.callCenterWaybillInfo.baseinfoRealPath+'updateCallCenterInfo.action';
	    			baseinfo.requestJsonAjax(url,params,successFun,failureFun);

	 		  	},
	 		  	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
	 				  //设定间隔秒数,如果不设置，默认为2秒
	 				  seconds: 3
	 				})
	 	  	
	 	  },{
	 		  	xtype:'button',
	 		  	text:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.notResolved'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){

	 		  		var grid = Ext.getCmp('T_baseinfo-callCenterWaybillInfo_content').getAcceptMsgOpeDataGrid();
	 		  		var callBackMsg = Ext.getCmp('Foss.baseinfo.SendMsgOpeCallCenterDataForm_id').getForm().findField('callBackMsg').getValue()           
	 		  		var entitys =new Array();
	 		  		var selections=grid.getSelectionModel().getSelection();
	 		  		if(selections.length<1){
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.alertInfoOne'));
	 		  			return;
	 		  		}else if(selections.length>1){
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.alertInfoTwo'));
	 		  			return;
	 		  		}
	 		  		if(callBackMsg.length>330){  //判断反馈信息
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.callBackMsgToLength'));
	 		  			return;
	 		  		}
	 		  		if(Ext.isEmpty(callBackMsg)){  //拒绝时请填写反馈信息
	 		  			Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.refuseremarks'));
	 		  			return;
	 		  		}
	 		  			selections[0].set('hasDone',baseinfo.callCenterWaybillInfo.noStatus);
	 		  			selections[0].set('callBackMsg',callBackMsg); 
	 		  			selections[0].set('dealUser',FossUserContext.getCurrentUserEmp().empCode);
	 		  			selections[0].set('dealUserName',FossUserContext.getCurrentUserEmp().empName);
	 		  			selections[0].set('dealDept',FossUserContext.getCurrentUserDept().unifiedCode);
	 		  		var params = {'callCenterWaybillInfoEntity':selections[0].data};
	    		 	var successFun = function(json){
	    		 		Ext.Msg.alert(baseinfo.callCenterWaybillInfo.i18n('i18n.baseinfo-util.fossAlert'),json.message);
	    		 		baseinfo.callCenterWaybillInfo.query();
	    			};
	    			var failureFun =function(json){
	    				if(Ext.isEmpty(json)){
	    					
	    				}else{
	    					
	    				}
	    			};
	    			var url = baseinfo.callCenterWaybillInfo.baseinfoRealPath+'updateCallCenterInfo.action';	
	    			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	    			
	 		  	},
	 		  	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
	 				  //设定间隔秒数,如果不设置，默认为2秒
	 				  seconds: 3
	 				})
	 	  }] 
});

Ext.define('Foss.baseinfo.SendMsgOpeDataGridMedel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'id',type:'string'},
       {name: 'pressWaybillNo',type: 'string'},
       {name: 'pressMsg',type: 'string'},
       {name: 'pressDeptName',type: 'string', defaultValue:'呼叫中心' },
       {name: 'dealTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'pressTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'pressUser',type: 'string'},
       {name: 'waybillNo',type: 'string'},
       {name: 'dealDept',type: 'string'},
       {name: 'dealDeptName',type: 'string'},
       {name: 'dealUser',type: 'string'},
       {name: 'dealUserName',type: 'string'},
       {name: 'hasDone',type: 'string'},
       {name: 'callBackMsg',type: 'string'},
       {name: 'modifyTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'modifyUserCode',type: 'string'},
       {name: 'createTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'modifyUserName',type: 'string'}
    ]
});

//定义公告的Store
Ext.define('Foss.baseinfo.SendMsgOpeDataGridStore', {
	extend : 'Ext.data.Store',
	autoLoad: true,
	model : 'Foss.baseinfo.SendMsgOpeDataGridMedel',// 绑定公告MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCallCenterWaybillInfos.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'callCenterInfoList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			Ext.apply(operation, {
				params : {					
		  			'callCenterWaybillInfoEntity.dealDept':FossUserContext.getCurrentUserDept().unifiedCode
					}
			});	
		}
	}
});

//单据列表
Ext.define('Foss.baseinfo.SendMsgOpeCallCenterDataForm',{
	extend:'Ext.form.Panel',
	frame:false,
	height : 205,
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'container',
		border:false,
		html:'<font size="3" style="font-weight:bold">'+baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.callBackMsg')+'</font>',
		height : 30,
		columnWidth:.3
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		height : 30,
		columnWidth:.6
	},{
		xtype:'textarea',
		fieldLabel:'',
		height : 170,
		maxLength:330,
		name:'callBackMsg',
		columnWidth:1
	}]			
});


//单据列表
Ext.define('Foss.Messages.AcceptMsgOpeDataGrid',{
	extend:'Ext.grid.Panel',
    title: '',
    frame:true,
    height : 300,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	store: Ext.create('Foss.baseinfo.SendMsgOpeDataGridStore'),
	columns:[{
		xtype : 'rownumberer',
		width : 40,
		text : baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.sequence') //序号
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.waybillNumber'),
		dataIndex: 'waybillNo'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressWaybillNo'),
		dataIndex: 'pressWaybillNo'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressDept'),
		dataIndex: 'pressDeptName'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressUser'),
		dataIndex: 'pressUser'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressTime'),
		dataIndex: 'pressTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s',
	},{
		header:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressMsg'),
		dataIndex: 'pressMsg'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.hasDone'),
		dataIndex: 'hasDone',
		renderer:function(value){
				if(!Ext.isEmpty(value)){
					if(value == baseinfo.callCenterWaybillInfo.status){
						return value ='已解决';
					}else{
						return value ='未解决';
					}
				}
			
			}
	},{
		header:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.callBackMsg'),
		dataIndex: 'callBackMsg'
	},{
		header:baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.dealDept'),
		dataIndex: 'dealDeptName'
	},{
		header: baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.dealTime'),
		xtype: 'datecolumn', 
		width:200,
		dataIndex: 'dealTime',
		format:'Y-m-d H:i:s',
	}],
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:20,
					prependButtons: true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
       return me.pagingToolbar;
	},
	constructor:function(config){
		var me = this;
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
			  	xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.2
			}]
		}];	
		me.listeners ={
				itemclick:function(grid,record,item,index,e){
					//获取选中的行
				var pressMsg =record.raw.pressMsg;
				var sendMsgOpeMsgForm =Ext.getCmp('Foss.baseinfo.SendMsgOpeCallCenterMsgForm_id');
				sendMsgOpeMsgForm.getForm().findField('callCenterWaybillInfoEntity.pressMsg').setValue(pressMsg);
				}
		};
		me.callParent();
	}
});


Ext.define('Foss.baseinfo.SendMsgOpeCallCenterMsgForm',{
	extend:'Ext.form.Panel',
	frame:false,
	height : 205,
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'container',
		border:false,
		html:'<font size="3" style="font-weight:bold">'+baseinfo.callCenterWaybillInfo.i18n('foss.baseinfo.callCenterWaybillInfo.pressMsg')+'</font>',
		height : 30,
		columnWidth:.15
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		height : 30,
		columnWidth:.7
	},{
		xtype:'textarea',
		fieldLabel:'',
		height : 170,
		name:'callCenterWaybillInfoEntity.pressMsg',
		columnWidth:1
	}]			
});

//全网消息发送表单操作panel
Ext.define('Foss.Messages.SendMsgOpePanel', {
	extend:'Ext.panel.Panel',
	frame:false,
	height : 400,
	layout: 'column',
	defaults: {                     //设置没一列的子元素的默认配置
		layout: 'anchor',
	    defaults: {
	    	anchor: '100%'
	    }
	},
	items : [ {
		title: '',
	    columnWidth:.60,
        items:[ Ext.create('Foss.baseinfo.SendMsgOpeCallCenterMsgForm',{
     	   			id:'Foss.baseinfo.SendMsgOpeCallCenterMsgForm_id'
        		}) ]
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		columnWidth:.02
	},{
		title: '',
        columnWidth:.35,
        items:[ Ext.create('Foss.baseinfo.SendMsgOpeCallCenterDataForm',{
     	   			id:'Foss.baseinfo.SendMsgOpeCallCenterDataForm_id'
        		}) ]
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-callCenterWaybillInfo_content')) {
		return;
	} 
	
	var sendMsgQueryForm = Ext.create('Foss.Messages.SendMsgQueryForm');//查询FORM
	var sendMsgOpePanel = Ext.create('Foss.Messages.SendMsgOpePanel');//操作Panel
	var acceptMsgOpeDataGrid = Ext.create('Foss.Messages.AcceptMsgOpeDataGrid');//操作Panel
	
	
	
	Ext.getCmp('T_baseinfo-callCenterWaybillInfo').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-callCenterWaybillInfo_content',
		cls : "panelContent",
		margin:'30 0 0 0',
		bodyCls : 'panelContent-body',
		layout : 'auto',
		//获得查询FORM
		getSendMsgQueryForm : function() {
			return sendMsgQueryForm; 
		}, 
		//获得操作模版
		getSendMsgOpePanel : function() {
			return sendMsgOpePanel; 
		}, 
		//获得操作模版
		getAcceptMsgOpeDataGrid : function() {
			return acceptMsgOpeDataGrid; 
		}, 
		items : [acceptMsgOpeDataGrid,sendMsgQueryForm,sendMsgOpePanel]
	}));
	
});

