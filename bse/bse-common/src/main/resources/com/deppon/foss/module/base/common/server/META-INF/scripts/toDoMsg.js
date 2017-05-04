

common.toDoMsg.FORMAT_DATE = 'Y-m-d'; //格式化日期字符串
common.toDoMsg.FORMAT_TIME = 'Y-m-d H:i:s'; //格式化时间字符串
common.toDoMsg.STATUS_PROCESSING= 'G'; //未处理
common.toDoMsg.STATUS_PROCESSED= 'D'; //已处理
common.toDoMsg.URL_TYPE= {WEB:'WEB',GUI:'GUI'}; //Web端菜单

//Grid查询用URL
common.toDoMsg.GridUrl='queryToDoMsg.action';
//外部链接用查询URL
common.toDoMsg.AutoUrl='queryToDoMsgByBisType.action?msgVo.bisType='+login.msg.bisType;
//查询链接
common.toDoMsg.QueryUrl=Ext.isEmpty(login.msg.bisType) ? common.toDoMsg.GridUrl : common.toDoMsg.AutoUrl; 
/**
  * 时间格式化
  *@param value 要格式化的时间
  *@format 格式化方式如  Y-m-d;Y-m-d H:i:s
  */
common.toDoMsg.dateFormat=function(value,format){
	if(value!=null && format!=null){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}
}
/**
 * 待办处理
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
common.toDoMsg.toDodeal=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var status=record.get('status');
	if(status==common.toDoMsg.STATUS_PROCESSED){
		Ext.MessageBox.alert(common.toDoMsg.i18n('foss.common.share.alertInfo'), common.toDoMsg.i18n('foss.common.toDoMsg.plsChoiceNoDealMsg')); 
	}else{
		var dealUrls=record.get('dealUrl').split('|');
		var pageIndex=dealUrls[0];
		var menuName=dealUrls[1];
		var url=dealUrls[2];
		var param='?serialNumber='+record.get('serialNumber');
		openDealMenu(pageIndex,menuName,url); 
	}
}

/**
 * 待办查询
 */
common.toDoMsg.search=function(){
	var form = common.toDoMsg.msgForm.getForm();
	if(form.isValid()){
		var createStartTime=form.findField("msgVo.toDoDto.createStartTime").getValue();
		var createEndTime=form.findField("msgVo.toDoDto.createEndTime").getValue(); 
		
		if(Ext.isEmpty(createStartTime)){
			 Ext.MessageBox.alert(common.toDoMsg.i18n('foss.common.share.alertInfo'), common.toDoMsg.i18n('foss.common.toDoMsg.plsChoiceTodoStartDate')); 
			 return false;
		}
		if(Ext.isEmpty(createEndTime)){
			 Ext.MessageBox.alert(common.toDoMsg.i18n('foss.common.share.alertInfo'), common.toDoMsg.i18n('foss.common.toDoMsg.plsChoiceTodoEndDate')); 
			return false;
		} 
		if(createStartTime>createEndTime){
			 Ext.MessageBox.alert(common.toDoMsg.i18n('foss.common.share.alertInfo'), common.toDoMsg.i18n('foss.common.toDoMsg.startTimeGreaterThanEndTime')); 
			return false;
		} 
		var grid=common.toDoMsg.msgGrid;
		//设置查询参数
		var params=form.getValues();
		grid.store.setSubmitParams(params);
		var bisType=login.msg.bisType;
		var queryStoreUrl=common.toDoMsg.GridUrl;
		if(!Ext.isEmpty(bisType)){
			queryStoreUrl=common.toDoMsg.AutoUrl
		}
		grid.store.proxy.url=common.realPath(queryStoreUrl);
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) { 
			login.msg.bisType=null; 
	       }
	    });
	}else{
		Ext.Msg.alert(common.toDoMsg.i18n('foss.common.share.alertInfo'), common.toDoMsg.i18n('foss.common.instationMsg.plsCheckInputCondition'));
		return false;
	}
}
//弹出新窗口
function openDealMenu(id,title,url){ 
	addTab(id,title,url);
}
//待办事项 Model
Ext.define('Foss.Messages.toDoMsgModel', {
			extend : 'Ext.data.Model', 
			fields : [ {
				name : 'id'
			}, {
				name : 'title', //待办标题
				type : 'string'
			}, {
				name : 'receiveOrgCode', //接收方组织编码
				type : 'string'
			}, {
				name : 'receiveSubOrgCode', //接收方下级组织编码
				type : 'string'
			}, {
				name : 'receiveSubOrgName', //接收方下级组织名称
				type : 'string'
			}, {
				name : 'receiveRoleCode', // 接收方角色编码
				type : 'string'
			}, {
				name : 'businessType', //业务类型
				type : 'string'
			}, {
				name : 'businessNo', //业务单号
				type : 'string'
			}, {
				name : 'dealUrl', //处理的址
				type : 'string'
			}, {
				name : 'urlType', //待办类型
				type : 'string'
			}, {
				name : 'serialNumber', //待办流水号
				type : 'string'
			}, {
				name : 'status', //待办状态
				type : 'string'
			}, {
				name : 'createUserName', //创建人名称
				type : 'string'
			}, {
				name : 'createUserCode', //创建人名称
				type : 'string'
			}, {
				name : 'createTime', //创建时间
				type : 'date',
				convert:dateConvert
			}]
		});

//站内消息数据 
Ext.define('Foss.Messages.toDoMsgStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.toDoMsgModel', 
	sorters: [{
        property: 'createTime',
        direction: 'DESC'
    }],
    submitParams:{},
    setSubmitParams:function(submitParams){
    	this.submitParams=submitParams;
    },
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url :common.realPath(common.toDoMsg.QueryUrl),
		reader : {
			type : 'json',
			root : 'msgVo.toDoMsgList',
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
 
//系统消息表格
Ext.define('Foss.Messages.toDoMsgGrid',{
	extend: 'Ext.grid.Panel', 
	title: common.toDoMsg.i18n('foss.common.share.queryResult'),
	columnWidth: 1,
	stripeRows: true,
    columnLines: true,
	collapsible: false,
    bodyCls: 'autoHeight',
    frame: true,
    //增加表格列的分割线
	cls: 'autoHeight',
	store :  Ext.create('Foss.Messages.toDoMsgStore'),
	autoScroll : true,
	height: 'autoHeight',
	emptyText: common.toDoMsg.i18n('foss.common.share.queryResultIsEmpty'),
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
	columns : [{
		xtype:'actioncolumn',
		width:100,
		text: common.toDoMsg.i18n('foss.common.toDoMsg.domain'),
		align: 'center',
		items: [{
                tooltip: common.toDoMsg.i18n('foss.common.toDoMsg.deal'),
				iconCls:'deppon_icons_dispose',
				width:42,
				disabled:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgDisposeButton'),
				hidden:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgDisposeButton'),
                handler: function(grid, rowIndex, colIndex) {
                		common.toDoMsg.toDodeal(grid, rowIndex, colIndex);  
                	}
            }]
		},   
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.title'),dataIndex : 'title' ,hidden:true},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.receiveOrgCode'), dataIndex : 'receiveOrgCode' ,hidden:true},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.receiveSubOrgCode'), dataIndex : 'receiveSubOrgCode' ,hidden:true},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.receiveSubOrgName'), dataIndex : 'receiveSubOrgName' ,width:140}, 
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.busType'),  dataIndex: 'businessType' ,width:140,
			renderer:function(value){
				var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'TODOMSG__BUSINESS_TYPE');
				return displayField;
			}
		},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.busNo'),  dataIndex : 'businessNo' ,width:120},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.serialNumber'), dataIndex : 'serialNumber' ,width:120}, 
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.toDoStatus'),  dataIndex: 'status' ,width:120,
			renderer:function(value){
				var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'TODOMSG__STATUS_TYPE');
				return displayField;
			}},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.createUserName'),  dataIndex: 'createUserName' ,width:120},
		{ text: common.toDoMsg.i18n('foss.common.toDoMsg.createTime'),flex:1, dataIndex: 'createTime',width:140,
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

//查询待办消息表单
Ext.define('Foss.Messages.toDoMsgForm',{
	extend:'Ext.form.Panel',
	title:common.toDoMsg.i18n('foss.common.share.queryCondition'),
	frame:true,
	collapsible:true,
	animcollapse:true,
	columnWidth:1,
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
	    id:'beginDate',
		xtype:'datefield',
		name:'msgVo.toDoDto.createStartTime', 
		fieldLabel:common.toDoMsg.i18n('foss.common.toDoMsg.toDoStartDate'),
		format:'Y-m-d',
		allowBlank:true,
		//默认值为空
		value:'',
		//最大时间为当天时间
		maxValue:common.toDoMsg.dateFormat(new Date(),common.toDoMsg.FORMAT_DATE),
//		dateRange: {begin: 'beginDate', end: 'endDate' },  
//        vtype: 'dateRange'  
		 
	},{
	 	id:'endDate',
		xtype:'datefield',
		name:'msgVo.toDoDto.createEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:common.toDoMsg.i18n('foss.common.instationMsg.arrive'),
		format:'Y-m-d',
		allowBlank:false,
		//默认值为空
		value:'',
		//最大时间为当天时间
		maxValue:common.toDoMsg.dateFormat(new Date(),common.toDoMsg.FORMAT_DATE) ,
//		dateRange: {begin: 'beginDate', end: 'endDate' },  
//        vtype: 'dateRange'  
        
	},{  
		name:'msgVo.toDoDto.urlType',
		value:common.toDoMsg.URL_TYPE.WEB,
		hidden:true 
	},FossDataDictionary.getDataDictionaryCombo(
			'TODOMSG__BUSINESS_TYPE',
			{
				name:"msgVo.toDoDto.businessType",
				fieldLabel:common.toDoMsg.i18n('foss.common.toDoMsg.busType'),
				colspan:3,
				editable:false,
				value: '',
				labelWidth :85 
			},
			{
	            'valueCode': '',
	            'valueName': common.toDoMsg.i18n('foss.common.instationMsg.all')
			}
	),FossDataDictionary.getDataDictionaryCombo(
			'TODOMSG__STATUS_TYPE',
			{
				name:"msgVo.toDoDto.status",
				fieldLabel:common.toDoMsg.i18n('foss.common.toDoMsg.toDoStatus'),
				colspan:3,
				editable:false,
				value: '',
				labelWidth :85 
			},
			{
	            'valueCode': '',
	            'valueName': common.toDoMsg.i18n('foss.common.instationMsg.all')
			}
	 ),{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:common.toDoMsg.i18n('foss.common.share.reset'),   
				  columnWidth:.12,
				  disabled:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgQueryButton'),
				  hidden:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgQueryButton'),
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
				  text:common.toDoMsg.i18n('foss.common.share.query'),
				  columnWidth:.12,
				  cls:'yellow_button',  
				  disabled:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgQueryButton'),
				  hidden:!common.toDoMsg.isPermission('toDoMsgInit/toDoMsgQueryButton'),
				  handler:function(){ 
					    login.msg.bisType=null;  
					    common.toDoMsg.search();
				  }
			  	}]
		}]  
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_common-toDoMsgInit_content')) {
		return;
	} 
	common.toDoMsg.msgForm = Ext.create('Foss.Messages.toDoMsgForm');//查询FORM
	common.toDoMsg.msgGrid = Ext.create('Foss.Messages.toDoMsgGrid');//查询结果GRID
	
	Ext.getCmp('T_common-toDoMsgInit').add(Ext.create('Ext.panel.Panel', {
		id : 'T_common-toDoMsgInit_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return common.toDoMsg.msgForm;
		},
		//获得查询结果GRID
		getMsgGridGrid : function() {
			return common.toDoMsg.msgGrid;
		},
		items: [ common.toDoMsg.msgForm,common.toDoMsg.msgGrid],
	   }));
});



