querying.requestJsonAjax = function(url, params, successFn, failFn) {
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
querying.acceptNetMsgOnline.active ='Y';
querying.acceptNetMsgOnline.unActive ='N';
querying.acceptNetMsgOnline.processing ='G';
querying.acceptNetMsgOnline.baseinfoRealPath = ContextPath.BSE_BASEINFO+"/baseinfo/";
querying.acceptNetMsgOnline.query =function(){
	var form = Ext.getCmp('T_querying-acceptNetMsgOnline_content').getSendMsgQueryForm().form;
	var billNo = form.findField('msgOnlineVo.msgOnlineDto.billNo').getValue();
	var grid = Ext.getCmp('T_querying-acceptNetMsgOnline_content').getAcceptMsgOpeDataGrid();
		
		//定义查询参数
		var params={'msgOnlineVo.msgOnlineEntity.waybillNo':billNo,
				'msgOnlineVo.msgOnlineEntity.receiveOrgCode':FossUserContext.getCurrentDeptCode(),
				'msgOnlineVo.msgOnlineEntity.acceptStatus':querying.acceptNetMsgOnline.processing};
		//设置账单号参数到DTO中
		Ext.apply(grid.store.proxy.extraParams,params);
		//grid.store.setSubmitParams(params);
		grid.store.load(); 
	
		
}

//航空单类型：model
Ext.define('Foss.Messages.AirBillTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'airBillTypeCode'
	},{
		name:'airBillTypeName'
	}]
	
});
//航空单类型：store
Ext.define('Foss.Messages.AirBillTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.AirBillTypeModel',
	data:{
		'items':[
			{airBillTypeCode:'11',airBillTypeName:'空运正单类型A'},
			{airBillTypeCode:'22',airBillTypeName:'空运正单类型B'},
			{airBillTypeCode:'33',airBillTypeName:'空运正单类型C'},
			{airBillTypeCode:'44',airBillTypeName:'空运正单类型D'}
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

//全网消息发送表单查询FORM
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
				name: 'msgOnlineVo.msgOnlineDto.billNo',
			    fieldLabel:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.billNo'),
			},{
	 		  	xtype:'button',
	 		  	text:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.query'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		querying.acceptNetMsgOnline.query();
	 		  	
	 		  	}
	 	  },{
	 		  	xtype:'button',
	 		  	text:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.agree'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		var grid = Ext.getCmp('T_querying-acceptNetMsgOnline_content').getAcceptMsgOpeDataGrid();
	 		  		var remarks = Ext.getCmp('Foss.Messages.SendMsgOpeDataGrid_id').getForm().findField('msgVo.msgJobEntityDto.msgDetial').getValue()           
	 		  		var entitys =new Array();
	 		  		var selections=grid.getSelectionModel().getSelection();
	 		  		if(selections.length<1){
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoOne'));
	 		  			return;
	 		  		}else if(selections.length>1){
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoTwo'));
	 		  			return;
	 		  		}
	 		  		
	 		  		if(remarks.length>330){  //判断受理备注
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.remarksToLength'));
	 		  			return;
	 		  		}
	 		  		selections[0].set('acceptStatus',querying.acceptNetMsgOnline.active);
	 		  		selections[0].set('remarks',remarks);
	 		  			
	 		  		var params = {'msgOnlineVo':{'msgOnlineEntity':selections[0].data}};
	    		 	var successFun = function(json){
	    		 		Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),json.message);
	    		 		querying.acceptNetMsgOnline.query();
	    			};
	    			var failureFun =function(json){
	    				if(Ext.isEmpty(json)){
	    					
	    				}else{
	    					
	    				}
	    			};
	    			var url = querying.acceptNetMsgOnline.baseinfoRealPath+'updateMsgByBillNo.action';
	    			//var url = querying.realPath('updateMsgByBillNo.action');
	    			querying.requestJsonAjax(url,params,successFun,failureFun);
	    		
	    			//grid.store.load();
	 		  		}
	 	  	
	 	  },{
	 		  	xtype:'button',
	 		  	text:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.refuse'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){

	 		  		var grid = Ext.getCmp('T_querying-acceptNetMsgOnline_content').getAcceptMsgOpeDataGrid();
	 		  		var remarks = Ext.getCmp('Foss.Messages.SendMsgOpeDataGrid_id').getForm().findField('msgVo.msgJobEntityDto.msgDetial').getValue()           
	 		  		var entitys =new Array();
	 		  		var selections=grid.getSelectionModel().getSelection();
	 		  		if(selections.length<1){
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoOne'));
	 		  			//querying.showInfoMes(querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoOne'));
	 		  			return;
	 		  		}else if(selections.length>1){
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoTwo'));
	 		  			//querying.showInfoMes(querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoTwo'));
	 		  			return;
	 		  		}
	 		  		if(remarks.length>330){  //判断受理备注
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.remarksToLength'));
	 		  			return;
	 		  		}
	 		  		if(Ext.isEmpty(remarks)){  //拒绝时请填写备注
	 		  			Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.refuseremarks'));
	 		  			return;
	 		  		}
	 		  			selections[0].set('acceptStatus',querying.acceptNetMsgOnline.unActive);
	 		  			selections[0].set('remarks',remarks);
	 		  			
	 		  		var params = {'msgOnlineVo':{'msgOnlineEntity':selections[0].data}};
	    		 	var successFun = function(json){
	    		 		Ext.Msg.alert(querying.acceptNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),json.message);
	    		 		querying.acceptNetMsgOnline.query();
	    			};
	    			var failureFun =function(json){
	    				if(Ext.isEmpty(json)){
	    					
	    				}else{
	    					
	    				}
	    			};
	    			var url = querying.acceptNetMsgOnline.baseinfoRealPath+'updateMsgByBillNo.action';	
	    		//  var url = querying.realPath('updateMsgByBillNo.action');
	    			querying.requestJsonAjax(url,params,successFun,failureFun);
	    			//grid.store.load();
	    			
	 		  	}
	 	  },
	 	  {
	 		  	xtype:'button',
	 		  	text:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.print'),
	 		  	columnWidth:.06,
	 		  	hidden:true,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
				  
	 		  	}
	 	  }
	 	  ]  
});

Ext.define('Foss.querying.SendMsgOpeDataGridMedel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'id',type:'string'},
       {name: 'receiveOrgCode',type: 'string'},
       {name: 'receiveOrgName',type: 'string'},
       {name: 'sendUserCode',type: 'string'},
       {name: 'sendUserName',type: 'string'},
       {name: 'sendOrgCode',type: 'string'},
       {name: 'sendOrgName',type: 'string'},
       {name: 'receiveUserCode',type: 'string'},
       {name: 'receiveUserName',type: 'string'},
       {name: 'receiveOrgCode',type: 'string'},
       {name: 'receiveOrgName',type: 'string'},
       {name: 'context',type: 'string'},
       {name: 'acceptStatus',type: 'string'},
       {name: 'remarks',type: 'string'},
       {name: 'waybillNo',type: 'string'},
       {name: 'modifyTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'modifyUserCode',type: 'string'},
       {name: 'createTime',type: 'date',convert: dateConvert,defaultValue:null},
       {name: 'modifyUserName',type: 'string'}
    ],
    idProperty: 'billNo'
});

//var myData = [
//              ['111111','Name1','Code1'],
//              ['222222','Name2','Code2'],
//              ['333333','Name3','Code3'],
//              ['444444','Name4','Code4']
//          ];
//var myBillStore = Ext.create('Ext.data.Store', {
//    model: 'Foss.Messages.SendMsgOpeDataGridMedel',
//    data: 
//});

//定义公告的Store
Ext.define('Foss.querying.SendMsgOpeDataGridStore', {
	extend : 'Ext.data.Store',
	autoLoad: true,
	model : 'Foss.querying.SendMsgOpeDataGridMedel',// 绑定公告MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : querying.realPath('queryMsgByBillNo.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'msgOnlineVo.msgOnlineEntitys',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			Ext.apply(operation, {
				params : {					
		  			'msgOnlineVo.msgOnlineEntity.receiveOrgCode':FossUserContext.getCurrentDeptCode(),
		  			'msgOnlineVo.msgOnlineEntity.acceptStatus':querying.acceptNetMsgOnline.processing
					}
			});	
		}
	}
});

//单据列表
Ext.define('Foss.Messages.SendMsgOpeDataGrid',{
	id:'Foss.Messages.SendMsgOpeDataGrid_id',
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
		html:'<font size="3" style="font-weight:bold">受理备注</font>',
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
		name:'msgVo.msgJobEntityDto.msgDetial',
		columnWidth:1
	}]			
});


//单据列表
Ext.define('Foss.Messages.AcceptMsgOpeDataGrid',{
	extend:'Ext.grid.Panel',
    title: '',
    frame:true,
    height : 200,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	store: Ext.create('Foss.querying.SendMsgOpeDataGridStore'),
	columns:[{
		xtype : 'rownumberer',
		width : 40,
		text : querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.rownumberer') //序号
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.waybillNumber'),
		dataIndex: 'waybillNo'
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.draftingOrg'),
		dataIndex: 'sendOrgName'
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.drafters'),
		dataIndex: 'sendUserName'
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.draftingTime'),
		dataIndex: 'createTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s',
	},{
		header:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.MsgDetail'),
		dataIndex: 'context'
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.callStatus'),
		dataIndex: 'acceptStatus',
		renderer:function(value){
				if(!Ext.isEmpty(value)){
					if(value == querying.acceptNetMsgOnline.active){
						return value ='接受';
					}if(value == querying.acceptNetMsgOnline.unActive){
						return value ='拒绝';
					}if(value == querying.acceptNetMsgOnline.processing){
						return value = '未处理';
					}
				}
			
			}
	},{
		header:querying.acceptNetMsgOnline.i18n('foss.querying.acceptingRemarks'),
		dataIndex: 'remarks'
	},{
		header:querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.receiveOrgCode'),
		dataIndex: 'receiveOrgName'
	},{
		header: querying.acceptNetMsgOnline.i18n('foss.querying.processingTime'),
		xtype: 'datecolumn', 
		width:200,
		dataIndex: 'modifyTime',
		format:'Y-m-d H:i:s',
	}],
	constructor:function(config){
		var me = this;
		//me.store=Ext.create('Foss.payment.PaymentGridStore');
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
				var context =record.raw.context;
				var sendMsgOpeMsgForm =Ext.getCmp('Foss.Messages.acceptNetMsg_id');

				sendMsgOpeMsgForm.getForm().findField('msgVo.msgJobEntityDto.msgDetial').setValue(context);
				}
		};
		me.callParent();
	}
});


Ext.define('Foss.Messages.SendMsgOpeMsgForm',{
	extend:'Ext.form.Panel',
	id:'Foss.Messages.acceptNetMsg_id',
	frame:false,
	height : 205,
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'container',
		border:false,
		html:'<font size="3" style="font-weight:bold">'+querying.acceptNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.MsgDetail')+'</font>',
		//html:'<font size="3">通知内容</font>',
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
		name:'msgVo.msgJobEntityDto.msgDetial',
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
        items:[
               Ext.create('Foss.Messages.SendMsgOpeMsgForm')
               ]
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		columnWidth:.02
	},{
		title: '',
        columnWidth:.35,
        items:[
               Ext.create('Foss.Messages.SendMsgOpeDataGrid')
               ]
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_querying-acceptNetMsgOnline_content')) {
		return;
	} 
	
	var sendMsgQueryForm = Ext.create('Foss.Messages.SendMsgQueryForm');//查询FORM
	var sendMsgOpePanel = Ext.create('Foss.Messages.SendMsgOpePanel');//操作Panel
	var acceptMsgOpeDataGrid = Ext.create('Foss.Messages.AcceptMsgOpeDataGrid');//操作Panel
	
	
	
	Ext.getCmp('T_querying-acceptNetMsgOnline').add(Ext.create('Ext.panel.Panel', {
		id : 'T_querying-acceptNetMsgOnline_content',
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
		items : [acceptMsgOpeDataGrid,sendMsgQueryForm,sendMsgOpePanel],
	}));
	
	
	
//	Ext.create('Ext.panel.Panel', {
//		id : 'T_querying-acceptNetMsgOnline_content',
//		cls:"panelContentNToolbar", //必须添加，内容定位用。
//		bodyCls:'panelContentNToolbar-body', //必须添加，内容定位用。
//		layout: 'auto',
//		//获得查询FORM
//		getSendMsgQueryForm : function() {
//			return sendMsgQueryForm; 
//		}, 
//		//获得操作模版
//		getSendMsgOpePanel : function() {
//			return sendMsgOpePanel; 
//		}, 
//		//获得操作模版
//		getAcceptMsgOpeDataGrid : function() {
//			return acceptMsgOpeDataGrid; 
//		}, 
//		items: [acceptMsgOpeDataGrid,sendMsgQueryForm,sendMsgOpePanel],
//		renderTo: 'T_querying-acceptNetMsgOnline-body'
//	   });

});

