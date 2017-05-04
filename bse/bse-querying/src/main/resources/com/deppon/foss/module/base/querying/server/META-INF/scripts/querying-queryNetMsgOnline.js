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
querying.queryNetMsgOnline.active ='Y';
querying.queryNetMsgOnline.unActive ='N';
querying.queryNetMsgOnline.processing ='G';
querying.queryNetMsgOnline.baseinfoRealPath = ContextPath.BSE_BASEINFO+"/baseinfo/";
/**
 * 时间格式化
 *@param value 要格式化的时间
 *@format 格式化方式如  Y-m-d;Y-m-d H:i:s
 */
querying.queryNetMsgOnline.dateFormat=function(value,format){
	if(value!=null && format!=null){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}
}
/**
 * 查询方法
 */
querying.queryNetMsgOnline.query=function(value,format){
		var form = Ext.getCmp('T_querying-queryNetMsgOnline_content').getSendMsgQueryForm().form;
	  		var grid = Ext.getCmp('T_querying-queryNetMsgOnline_content').getAcceptMsgOpeDataGrid();
	  		
	  		var send = form.findField('msgOnlineVo.msgOnlineDto.send').getValue();
	  		var receive = form.findField('msgOnlineVo.msgOnlineDto.receive').getValue();
	  		var createStartTime=form.findField("msgOnlineVo.msgOnlineEntity.createStartTime").getValue();
			var createEndTime=form.findField("msgOnlineVo.msgOnlineEntity.createEndTime").getValue(); 
	  		var params ={};
	  		if(Ext.isEmpty(createStartTime)){
				 Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfo'),
						 querying.queryNetMsgOnline.i18n('foss.querying.plsChoiceMsgSendStartTime')); 
				 return false;
			}
			if(Ext.isEmpty(createEndTime)){
				 Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfo'),
						 querying.queryNetMsgOnline.i18n('foss.querying.plsChoiceMsgSendEndTime')); 
				return false;
			}
			if(createStartTime>createEndTime){
				Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfo'),
						querying.queryNetMsgOnline.i18n('foss.querying.startTimeGreaterThanEndTime')); 
				return false;
			}
	  		if(send){
	  			 params={'msgOnlineVo.msgOnlineEntity.receiveOrgCode':'',
		  				'msgOnlineVo.msgOnlineEntity.sendOrgCode':FossUserContext.getCurrentDeptCode(),
		  				'msgOnlineVo.msgOnlineEntity.createStartTime':createStartTime,
		  				'msgOnlineVo.msgOnlineEntity.createEndTime':createEndTime
		  				};
	  		}else{
	  		//定义查询参数
		  		 params={'msgOnlineVo.msgOnlineEntity.sendOrgCode':'',
		  				'msgOnlineVo.msgOnlineEntity.createStartTime':createStartTime,
		  				'msgOnlineVo.msgOnlineEntity.createEndTime':createEndTime,
		  				'msgOnlineVo.msgOnlineEntity.receiveOrgCode':FossUserContext.getCurrentDeptCode()
		  				};
	  		}
	  		//设置账单号参数到DTO中
	  		Ext.apply(grid.store.proxy.extraParams,params);
			//grid.store.setSubmitParams(params);
			grid.store.load(); 
	  	
	  	}


/**
 * 全网消息发送
 * 
 */
querying.queryNetMsgOnline.submit=function(){
	var form = this.up('form').getForm();
	if(form.isValid()){
		var orgCodeObj=form.findField('msgVo.msgJobEntityDto.receiveOrgCode');
		form.findField('msgVo.msgJobEntityDto.receiveOrgName').setValue(orgCodeObj.rawValue);
		var availableDate = form.findField('msgVo.msgJobEntityDto.expireTime').getValue();
		var beginDate = Ext.Date.format(new Date(), 'Y-m-d H:i:s');
		if(!Ext.isEmpty(availableDate)){
			if(beginDate > availableDate){
			Ext.Msg.alert(querying.queryNetMsgOnline.i18n('foss.querying.sendAllNetMsg.warmTips'),querying.sendAllNetMsg.i18n('foss.querying.sendAllNetMsg.availableDateCanNotGreaterThanBeginDate'));
			return false;
		}
		}
		Ext.Ajax.request({
			url:querying.realPath('msgSend.action'),
			params:form.getValues(),
			success : function(response) { 
				var json = Ext.decode(response.responseText); 
				Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.share.alertInfo'), json.message); 
				form.reset();
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.share.alertInfo'), json.message);
			},
			failure:function(response){
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.share.alertInfo'), json.message);
			}
		}); 
	}
}      


//全网消息发送表单查询FORM
Ext.define('Foss.Messages.SendMsgQueryForm',{
	extend:'Ext.form.Panel',
	frame:false,
	//collapsible:true,
	//animcollapse:true,
	defaults:{
		margin : '10 10 10 0',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'column',
		columns : 8
	},
	items:[
	       {
	   	id:'queryingbeginDate1',
		xtype:'datefield',
		name:'msgOnlineVo.msgOnlineEntity.createStartTime', 
		fieldLabel:querying.queryNetMsgOnline.i18n('foss.querying.msgBeginTime'),
		format:'Y-m-d',
		allowBlank:false,
		//默认值为空
		value:new Date(),
		//最大时间为当天时间
		maxValue:querying.queryNetMsgOnline.dateFormat(new Date(),querying.queryNetMsgOnline.FORMAT_DATE),
//		dateRange: {begin: 'queryingbeginDate1', end: 'queryingEndDate1' },  
//        vtype: 'dateRange'   
	},{
		id:'queryingendDate1',
		xtype:'datefield',
		name:'msgOnlineVo.msgOnlineEntity.createEndTime',
		labelWidth:80,
	    labelSeparator:'',
		fieldLabel:querying.queryNetMsgOnline.i18n('foss.querying.msgEndTime'),
		format:'Y-m-d',
		allowBlank:false,
		//默认值为空
		value:new Date(),
		//最大时间为当天时间
		maxValue:querying.queryNetMsgOnline.dateFormat(new Date(),querying.queryNetMsgOnline.FORMAT_DATE),
//		dateRange: {begin: 'queryingbeginDate1', end: 'queryingEndDate1' },  
//        vtype: 'dateRange'  
	},{
		xtype: 'fieldcontainer',
		defaultType: 'radiofield',
		columnWidth :.1,
        layout: 'table',
        name:'msgOnlineVo.msgOnlineDto.sendRadio',
        items: [{ 
        	boxLabel: querying.queryNetMsgOnline.i18n('foss.querying.send'),
        	name: 'msgOnlineVo.msgOnlineDto.send',
        	checked:true,
            inputValue: '1',
        	handler:function(val){
            		if(val.value == true){
            			var form =Ext.getCmp('T_querying-queryNetMsgOnline_content').getSendMsgQueryForm().form;
            			form.findField('msgOnlineVo.msgOnlineDto.receive').reset();
            			form.findField('msgOnlineVo.msgOnlineDto.receive').setValue(false);
            		}
     		}
		}]
  	},{
		xtype: 'fieldcontainer',
		defaultType: 'radiofield',
		columnWidth :.1,
        layout: 'table',
       // checked : true,
        name:'msgOnlineVo.msgOnlineDto.receiveRadio',
        items: [{ 
			boxLabel: querying.queryNetMsgOnline.i18n('foss.querying.receive'), 
			name: 'msgOnlineVo.msgOnlineDto.receive',
            inputValue: '2',
            checked:false,
            handler:function(val){
            	if(val.value == true){
            	var form =Ext.getCmp('T_querying-queryNetMsgOnline_content').getSendMsgQueryForm().form;
      			form.findField('msgOnlineVo.msgOnlineDto.send').reset();
      			form.findField('msgOnlineVo.msgOnlineDto.send').setValue(false);
 				
            	}
            }
		}]
	  },{
	 		  	xtype:'button',
	 		  	text:querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.query'),
	 		  	columnWidth:.2,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		querying.queryNetMsgOnline.query();
	 		  	
	 		  	}
	 	  },{
				xtype : 'button',
				text : querying.queryNetMsgOnline.i18n('foss.querying.export'),//导出
				//hidden:!baseinfo.originatingLine.isPermission('originatingLine/originatingLineExportButton'),
				width : 80,
				handler : function() {
				    //获取Form表单
					var queryForm = Ext.getCmp('T_querying-queryNetMsgOnline_content').getSendMsgQueryForm().form;
					var createStartTime=queryForm.findField("msgOnlineVo.msgOnlineEntity.createStartTime").getValue();
					var createEndTime=queryForm.findField("msgOnlineVo.msgOnlineEntity.createEndTime").getValue(); 
					var send = queryForm.findField('msgOnlineVo.msgOnlineDto.send').getValue();
			  		var receive = queryForm.findField('msgOnlineVo.msgOnlineDto.receive').getValue();
			  		
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						
						Ext.MessageBox.buttonText.yes = querying.queryNetMsgOnline.i18n('foss.querying.ensur'); //确定 
						Ext.MessageBox.buttonText.no = querying.queryNetMsgOnline.i18n('foss.querying.cancel'); //取消
						if(!Ext.fly('downloadOriginatingForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadOriginatingForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}
						
						Ext.Msg.confirm( querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfo'), querying.queryNetMsgOnline.i18n('foss.querying.ensurExportList'), function(btn,text){
							if(btn == 'yes'){
								if(send){
						  			 params={'msgOnlineVo.msgOnlineEntity.receiveOrgCode':'',
							  				'msgOnlineVo.msgOnlineEntity.sendOrgCode':FossUserContext.getCurrentDeptCode(),
							  				'msgOnlineVo.msgOnlineEntity.createStartTime':createStartTime,
							  				'msgOnlineVo.msgOnlineEntity.createEndTime':createEndTime
							  				};
						  		}else{
						  		//定义查询参数
							  		 params={'msgOnlineVo.msgOnlineEntity.sendOrgCode':'',
							  				'msgOnlineVo.msgOnlineEntity.createStartTime':createStartTime,
							  				'msgOnlineVo.msgOnlineEntity.createEndTime':createEndTime,
							  				'msgOnlineVo.msgOnlineEntity.receiveOrgCode':FossUserContext.getCurrentDeptCode()
							  				};
						  		}
								
						
								Ext.Ajax.request({
									url:querying.queryNetMsgOnline.baseinfoRealPath+'exportOnLineMsg.action',
									//url:querying.realPath('exportOnLineMsg.action'),
									form: Ext.fly('downloadOriginatingForm'),
									params:params,
									method:'post',
									isUpload: true,
									success:function(response){
										var result = Ext.decode(response.responseText);
									},
									failure:function(response){
										var result = Ext.decode(response.responseText);
										top.Ext.MessageBox.alert(querying.queryNetMsgOnline.i18n('foss.querying.exportFail'),result.message);
									}
								});
							}
						});
					}
				}
			}]  
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


//定义公告的Store
Ext.define('Foss.querying.SendMsgOpeDataGridStore', {
	extend : 'Ext.data.Store',
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
	}
});

//单据列表
Ext.define('Foss.Messages.SendMsgOpeDataGrid',{
	id:'Foss.Messages.MsgOpeDataGrid_id',
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
	store:null,
	store: Ext.create('Foss.querying.SendMsgOpeDataGridStore'),
	columns:[{
		xtype : 'rownumberer',
		width : 40,
		text : querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.rownumberer') //序号
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.waybillNumber'),
		dataIndex: 'waybillNo'
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.draftingOrg'),
		dataIndex: 'sendOrgName'
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.drafters'),
		dataIndex: 'sendUserName'
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.draftingTime'),
		dataIndex: 'createTime',
		xtype: 'datecolumn',
		format:'Y-m-d H:i:s',
	},{
		header:querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.MsgDetail'),
		dataIndex: 'context'
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.callStatus'),
		dataIndex: 'acceptStatus',
		renderer:function(value){
				if(!Ext.isEmpty(value)){
					if(value == querying.queryNetMsgOnline.active){
						return value ='接受';
					}if(value == querying.queryNetMsgOnline.unActive){
						return value ='拒绝';
					}if(value == querying.queryNetMsgOnline.processing){
						return value = '未处理';
					}
				}
			
			}
	},{
		header:querying.queryNetMsgOnline.i18n('foss.querying.acceptingRemarks'),
		dataIndex: 'remarks'
	},{
		header:querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.receiveOrgCode'),
		dataIndex: 'receiveOrgName'
	},{
		header:querying.queryNetMsgOnline.i18n('foss.querying.auditor'),
		dataIndex: 'modifyUserName'
	},{
		header: querying.queryNetMsgOnline.i18n('foss.querying.processingTime'),
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
				var remarks =record.raw.remarks;
				var sendMsgOpeMsgForm =Ext.getCmp('Foss.Messages.queryNetMsg_id');
				var SendMsgOpeDataGrid =Ext.getCmp('Foss.Messages.MsgOpeDataGrid_id')
				
				sendMsgOpeMsgForm.getForm().findField('msgVo.msgJobEntityDto.msgDetial').setValue(context);
				SendMsgOpeDataGrid.getForm().findField('msgVo.msgJobEntityDto.msgDetial').setValue(remarks);
				}
		};
		me.callParent();
	}
});





//消息发送 Form
Ext.define('Foss.Messages.SendMsgOpeMsgForm',{
	extend:'Ext.form.Panel',
	id:'Foss.Messages.queryNetMsg_id',
	frame:false,
	height : 205,
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'container',
		border:false,
		html:'<font size="3" style="font-weight:bold">'+querying.queryNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.MsgDetail')+'</font>',
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
		columnWidth:1,
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
	if (Ext.getCmp('T_querying-queryNetMsgOnline_content')) {
		return;
	} 
	
	var sendMsgQueryForm = Ext.create('Foss.Messages.SendMsgQueryForm');//查询FORM
	var sendMsgOpePanel = Ext.create('Foss.Messages.SendMsgOpePanel');//操作Panel
	var acceptMsgOpeDataGrid = Ext.create('Foss.Messages.AcceptMsgOpeDataGrid');//操作Panel
	
	
	
	Ext.getCmp('T_querying-queryNetMsgOnline').add(Ext.create('Ext.panel.Panel', {
		id : 'T_querying-queryNetMsgOnline_content',
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
		items : [sendMsgQueryForm,acceptMsgOpeDataGrid,sendMsgOpePanel],
	}));
	querying.queryNetMsgOnline.query();

});

