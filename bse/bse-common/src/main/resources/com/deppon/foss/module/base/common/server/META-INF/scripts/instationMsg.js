
//格式化日期字符串
common.instationMsg.FORMAT_DATE = 'Y-m-d'; 
//格式化时间字符串
common.instationMsg.FORMAT_TIME = 'Y-m-d H:i:s';
//Grid查询用URL
common.instationMsg.GridUrl='queryInstationMsg.action';
//外部链接用查询URL
common.instationMsg.AutoUrl='queryMsgByMsgType.action?msgVo.msgType='+login.msg.msgType;
//查询链接
common.instationMsg.QueryUrl=Ext.isEmpty(login.msg.msgType) ? common.instationMsg.GridUrl : common.instationMsg.AutoUrl; 

/**
  * 时间格式化
  *@param value 要格式化的时间
  *@format 格式化方式如  Y-m-d;Y-m-d H:i:s
  */
common.instationMsg.dateFormat=function(value,format){
	if(value!=null && format!=null){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}
}

/**
 * 标记信息读取状态
 */
common.instationMsg.markReadOperator=function(me){
	//获取选中的记录
	var rowObjs = me.getSelectionModel().getSelection();
	if(rowObjs.length>0){
		Ext.MessageBox.buttonText.yes = common.instationMsg.i18n('foss.common.share.Ok');  
		Ext.MessageBox.buttonText.no = common.instationMsg.i18n('foss.common.share.Cancel');  
		Ext.Msg.confirm(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.confirmSelectRecord'), function(btn,text){
		if(btn == 'yes'){
			var ids=new Array();
			for(var i = 0; i< rowObjs.length; i++){
				var record=rowObjs[i].data;
				var isReaded=record.isReaded;
				var msgType=record.msgType;
				
				if( msgType =='ALLNET'){
					Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.allNetNotRemark')); 
					return false;
				}
				
				if(isReaded!='N'){
					Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceMsg')); 
					return false;
				}
				ids.push(record.serialNumber);
			} 
			if(ids.length>0){
				Ext.Ajax.request({
					url:common.realPath('updateMsgReadStatus.action'),
					params:{
						'msgVo.ids' : ids
					},
					success : function(response) { 
						var json = Ext.decode(response.responseText); 
						Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'), json.message);

						common.instationMsg.msgGrid.getPagingToolbar().moveFirst(); 
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'), json.message);
					}
				});
			} 
		}
		});
	}else{
		Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceRemarkRecord'));
		return false;
	}
}

/**
 * 双击标记信息读取状态
 */
common.instationMsg.dbMarkReadOperator=function(record){
	//获取选中的记录
	 var id=record.get('serialNumber');
	if(id){
		Ext.MessageBox.buttonText.yes = common.instationMsg.i18n('foss.common.share.Ok');  
		Ext.MessageBox.buttonText.no = common.instationMsg.i18n('foss.common.share.Cancel');  
		Ext.Msg.confirm(common.instationMsg.i18n('foss.common.share.alertInfo'), common.instationMsg.i18n('foss.common.instationMsg.confirmSelectRecord'), function(btn,text){
		if(btn == 'yes'){
			var ids=new Array();
				var isReaded=record.get('isReaded');
				var msgType=record.get('msgType');
				
				if( msgType =='ALLNET'){
					Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.allNetNotRemark')); 
					return false;
				}
				
				if(isReaded!='N'){
					Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceMsg')); 
					return false;
				}
				ids.push(id);
			if(ids.length>0){
				Ext.Ajax.request({
					url:common.realPath('updateMsgReadStatus.action'),
					params:{
						'msgVo.ids' : ids
					},
					success : function(response) { 
						var json = Ext.decode(response.responseText); 
						Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'), json.message);

						common.instationMsg.msgGrid.getPagingToolbar().moveFirst(); 
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'), json.message);
					}
				});
			} 
		}
		});
	}else{
		Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceRemarkRecord'));
		return false;
	}
}
/**
 * 站内消息查询
 */
common.instationMsg.search=function(){
	var form = common.instationMsg.msgForm.getForm();
	if(form.isValid()){
		var createStartTime=form.findField("msgVo.msgDto.createStartTime").getValue();
		var createEndTime=form.findField("msgVo.msgDto.createEndTime").getValue(); 
		
		if(Ext.isEmpty(createStartTime)){
			 Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceMsgSendStartTime')); 
			 return false;
		}
		if(Ext.isEmpty(createEndTime)){
			 Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsChoiceMsgSendEndTime')); 
			return false;
		}
		if(createStartTime>createEndTime){
			Ext.MessageBox.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.toDoMsg.startTimeGreaterThanEndTime')); 
			return false;
		}
		var grid=common.instationMsg.msgGrid;
		//设置查询参数
		var params=form.getValues();
		grid.store.setSubmitParams(params);
		var msgType=login.msg.msgType;
		var queryStoreUrl=common.instationMsg.GridUrl;
		if(!Ext.isEmpty(msgType)){
			queryStoreUrl=common.instationMsg.AutoUrl
		}
		grid.store.proxy.url=common.realPath(queryStoreUrl);
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
			//var result =   Ext.decode(operation.response.responseText);
			login.msg.msgType=null; 
	       }
	    });
	}else{
		Ext.Msg.alert(common.instationMsg.i18n('foss.common.share.alertInfo'),common.instationMsg.i18n('foss.common.instationMsg.plsCheckInputCondition'));
		return false;
	}
}
//站内消息Model
Ext.define('Foss.Messages.InstationMsgModel', {
			extend : 'Ext.data.Model', 
			fields : [ {
				name : 'id'
			}, {
				name : 'serialNumber', //消息标识
				type : 'string'
			}, {
				name : 'sendUserCode', //发送人员编码
				type : 'string'
			}, {
				name : 'sendUserName', //发送人员名称
				type : 'string'
			}, {
				name : 'sendOrgCode', //发送方组织编码
				type : 'string'
			}, {
				name : 'sendOrgName', //发送方组织名称
				type : 'string'
			}, {
				name : 'receiveUserCode', //接收人员编码
				type : 'string'
			}, {
				name : 'receiveUserName', //接收人员名称
				type : 'string'
			}, {
				name : 'context', //消息内容
				type : 'string'
			}, {
				name : 'msgType', //站内消息类型
				type : 'string'
			}, {
				name : 'active', //消息状态
				type : 'string'
			}, {
				name : 'isReaded', //是否已读
				type : 'string'
			}, {
				name : 'createTime', //创建时间
				type : 'date',
				convert:dateConvert
			}]
		});

//站内消息数据 
Ext.define('Foss.Messages.InstationMsgStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.InstationMsgModel', 
	sorters: [{
        property: 'createTime',
        direction: 'DESC'
    }],
    submitParams:{},
    setSubmitParams:function(submitParams){
    	this.submitParams=submitParams;
    },
	pageSize:50,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :common.realPath(common.instationMsg.QueryUrl),
		reader : {
			type : 'json',
			root : 'msgVo.messageList',
			totalProperty : 'totalCount'
		}
	}, 
	autoLoad: true,
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   		 Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
}); 
 
//消息表格
Ext.define('Foss.Messages.InstationMsgGrid',{
	extend: 'Ext.grid.Panel', 
	title: common.instationMsg.i18n('foss.common.share.queryResult'),
	columnWidth: 1,
	stripeRows: true,
    columnLines: true,
	collapsible: false,
    bodyCls: 'autoHeight',
    frame: true,
    //增加表格列的分割线
	cls: 'autoHeight',
	store : Ext.create('Foss.Messages.InstationMsgStore'),
	autoScroll : true,
	height: 'autoHeight',
	emptyText: common.instationMsg.i18n('foss.common.share.queryResultIsEmpty'),
	//分页
	pagingToolbar:null,
	viewConfig:{
	    	enableTextSelection : true//设置行可以选择，进而可以复制    
	},
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:50,
				maximumSize:500,
				plugins:'pagesizeplugin'
			}); 
		}
       return me.pagingToolbar;
	},
	listeners: {
    	'itemdblclick': function(view, record, item, index, e, eOpts){
			msgType = record.get('msgType');
			if(msgType == 'CODPAYFAILD'){
				//设置显示页签
				var activeTab = 0;
				addTab('T_consumer-codSalesPay', common.instationMsg.i18n('foss.common.toDoMsg.accountManage'),
					ContextPath.STL_WEB+'/consumer/codSalesPay.action?activeTab='+activeTab, null);
			}else{
			common.instationMsg.dbMarkReadOperator(record);
			}
    	}
    },
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [ 
	    { text: 'ID', dataIndex : 'id',hidden:true},
	    { text: 'SERIAL_NUMBER', dataIndex : 'serialNumber',hidden:true},
	    { text: common.instationMsg.i18n('foss.common.instationMsg.sendUserCode'), dataIndex : 'sendUserCode',hidden:true},
	    { text: common.instationMsg.i18n('foss.common.instationMsg.sendUserName'),  dataIndex : 'sendUserName' ,width:100},
	    { text: common.instationMsg.i18n('foss.common.instationMsg.sendOrgCode'),  dataIndex : 'sendOrgCode' ,hidden:true},
	    { text: common.instationMsg.i18n('foss.common.instationMsg.sendOrgName'),  dataIndex : 'sendOrgName' ,width:140},
		{ text: common.instationMsg.i18n('foss.common.instationMsg.type'),  dataIndex : 'msgType' ,width:80,
			renderer:function(value){
				var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'MSG_TYPE');
				return displayField;
			}
	    },
		{ text: common.instationMsg.i18n('foss.common.instationMsg.readStatus'),  dataIndex : 'isReaded' ,width:80,
			renderer:function(value){
				var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'MSG__READ_STATUS');
				return displayField;
			}
	    }, 
		{ text: common.instationMsg.i18n('foss.common.instationMsg.content'), 	xtype: 'linebreakcolumn', flex: 1, dataIndex: 'context',sortable:false},
		{ text: common.instationMsg.i18n('foss.common.instationMsg.time'), dataIndex: 'createTime',width:140,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		}
	], 
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config); 
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'button',
				text : common.instationMsg.i18n('foss.common.instationMsg.remarkReaded'),
				disabled:!common.instationMsg.isPermission('instationMsgInit/instationMsgSetReadedButton'),
				hidden:!common.instationMsg.isPermission('instationMsgInit/instationMsgSetReadedButton'),
				columnWidth : .1,
				handler : function() {
					common.instationMsg.markReadOperator(me);
				}
			}]
		}], 
		me.callParent([ cfg ]);
	} 
});

////验证日期
//Ext.apply(Ext.form.VTypes, {  
//         dateRange: function(val, field){  
//             if(field.dateRange){  
//                 var beginId = field.dateRange.begin;  
//                 this.beginField = Ext.getCmp(beginId);  
//                 var endId = field.dateRange.end;  
//                 this.endField = Ext.getCmp(endId);  
//                  beginDate = this.beginField.getValue();  
//                 var endDate = this.endField.getValue();  
//             }  
//             if(beginDate <= endDate){  
//                 return true;  
//             }else{  
//                 return false;  
//             }  
//         },  
//         //验证失败信息  
//         dateRangeText: '开始时间不可以大于结束时间'  
//     }); 
     
//消息信息表单
Ext.define('Foss.Messages.InstationMsgForm',{
	extend:'Ext.form.Panel',
	title:common.instationMsg.i18n('foss.common.share.queryCondition'),
	frame:true,
	collapsible:true,
	animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 3
	},
	items:[
	   {
	   	id:'beginDate1',
		xtype:'datefield',
		name:'msgVo.msgDto.createStartTime', 
		fieldLabel:common.instationMsg.i18n('foss.common.instationMsg.msgSendTime'),
		format:'Y-m-d',
		allowBlank:false,
		//默认值为空
		value:'',
		//最大时间为当天时间
		maxValue:common.instationMsg.dateFormat(new Date(),common.instationMsg.FORMAT_DATE),
//		dateRange: {begin: 'beginDate1', end: 'endDate1' },  
//        vtype: 'dateRange'   
	},{
		id:'endDate1',
		xtype:'datefield',
		name:'msgVo.msgDto.createEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:common.instationMsg.i18n('foss.common.instationMsg.arrive'),
		format:'Y-m-d',
		allowBlank:false,
		//默认值为空
		value:'',
		//最大时间为当天时间
		maxValue:common.instationMsg.dateFormat(new Date(),common.instationMsg.FORMAT_DATE),
//		dateRange: {begin: 'beginDate1', end: 'endDate1' },  
//        vtype: 'dateRange'  
	},FossDataDictionary.getDataDictionaryCombo(
			'MSG_TYPE',
			{
				name:"msgVo.msgDto.msgType",
				fieldLabel:common.instationMsg.i18n('foss.common.instationMsg.msgType'),
				allowBlank:false,
				editable:false,
				value: '',
				labelWidth :85 
			},
			{
	            'valueCode': '',
	            'valueName': common.instationMsg.i18n('foss.common.instationMsg.all')
			}
	),{
		xtype:'radiogroup',
		fieldLabel:common.instationMsg.i18n('foss.common.instationMsg.isNotRead'), 
		colspan:3,
		layout:'table',
		isFormField: true,
		defaults:{
			margin:'5 0 0 0',
			width:50
		},
		items:[{
			boxLabel:common.instationMsg.i18n('foss.common.instationMsg.readed'),
			name:'msgVo.msgDto.isReaded',
			inputValue:'R'
		},{
			boxLabel:common.instationMsg.i18n('foss.common.instationMsg.unread'),
			name:'msgVo.msgDto.isReaded',
			inputValue:'N',
			checked:true
		}]
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:common.instationMsg.i18n('foss.common.share.reset'),
				  columnWidth:.12,
				  disabled:!common.instationMsg.isPermission('instationMsgInit/instationMsgQueryButton'),
				  hidden:!common.instationMsg.isPermission('instationMsgInit/instationMsgQueryButton'),
				  handler:function(){
					 this.up('form').getForm().reset();
				  }
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.76
				},
			  	{
				  text:common.instationMsg.i18n('foss.common.share.query'),
				  columnWidth:.12,
				  disabled:!common.instationMsg.isPermission('instationMsgInit/instationMsgQueryButton'),
				  hidden:!common.instationMsg.isPermission('instationMsgInit/instationMsgQueryButton'),
				  cls:'yellow_button',  
				  handler:function(){ 
					    login.msg.msgType=null;  
						common.instationMsg.search();
				  }
			  	}]
		}]  
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_common-instationMsgInit_content')) {
		return;
	} 
	common.instationMsg.msgForm = Ext.create('Foss.Messages.InstationMsgForm');//查询FORM
	common.instationMsg.msgGrid = Ext.create('Foss.Messages.InstationMsgGrid');//查询结果GRID
 
	
	Ext.getCmp('T_common-instationMsgInit').add(Ext.create('Ext.panel.Panel', {
		id : 'T_common-instationMsgInit_content',
		cls:"panelContentNToolbar", //必须添加，内容定位用。
		bodyCls:'panelContentNToolbar-body', //必须添加，内容定位用。
		//获得查询FORM
		getQueryForm : function() {
			return common.instationMsg.msgForm; 
		},
		//获得查询结果GRID
		getMsgGridGrid : function() {
			return common.instationMsg.msgGrid;
		},
		items: [ common.instationMsg.msgForm,common.instationMsg.msgGrid],
	   }));

	
});