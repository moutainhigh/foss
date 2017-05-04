/**
 * 查询tab页常量
 */
agency.agencySystem.QUERY_BY_DATE='TD';
agency.agencySystem.QUERY_BY_WAYBILL='WB';

/**
 * 校验日期
 */
agency.agencySystem.validateDateDiff = function(curDate, endDate, dateLimitDays,alertInfo) {
	var diffDays = stl.compareTwoDate(curDate, endDate);
	if (diffDays > dateLimitDays) {
		Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), alertInfo+agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.betweenBeginDateAndEndDate') + dateLimitDays + agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.day'));
		return true;
	} else if (diffDays < 1) {
		Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), alertInfo+agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.beginDateBeforeEndDate'));
		return true;
	}
	return false;
};

/**
 * 设置查询参数
 */
agency.agencySystem.setParams=function(form,queryType){
	//定义查询参数
	var params={};
	//按日期查询
	if(agency.agencySystem.QUERY_BY_DATE==queryType){

		var billStartDate=form.findField("vo.dto.startBusinessDate").getValue();
		var billEndDate=form.findField("vo.dto.endBusinessDate").getValue();
		var handOverStartTime=form.findField("vo.dto.handOverStartTime").getValue();
		var handOverEndTime=form.findField("vo.dto.handOverEndTime").getValue();
		
		
		var arriveOrgCodeLast = form.findField('vo.dto.arriveOrgCode').lastValue;
		var arriveOrgCode = "";
		if(arriveOrgCodeLast == stl.currentDept.name){
			arriveOrgCode = stl.currentDept.code;
		}else{
			arriveOrgCode = arriveOrgCodeLast;
		}

		
		
		if(Ext.isEmpty(billStartDate) && !Ext.isEmpty(billEndDate)){
			 Ext.MessageBox.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.selectBeginBusinessDate')); 
			 return false;
		}else if(!Ext.isEmpty(billStartDate) && Ext.isEmpty(billEndDate)){
			 Ext.MessageBox.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.selectEndBusinessDate')); 
			return false;
		}
    	 
		var result1=agency.agencySystem.validateDateDiff(billStartDate,billEndDate,stl.DATELIMITDAYS_MONTH,agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.business'));
    	if(result1){
    		return false;
    	}
		
		//获取FORM所有值
		params = {
				'vo.dto.startBusinessDate':billStartDate,
				'vo.dto.endBusinessDate':billEndDate,
				'vo.dto.handOverStartTime':handOverStartTime,
				'vo.dto.handOverEndTime':handOverEndTime,
				'vo.dto.arriveOrgCode':arriveOrgCode,
				'vo.dto.registerFlag':form.findField('vo.dto.registerFlag').getValue()
		}
		
	 
	//按账单号查询
	}else if(agency.agencySystem.QUERY_BY_WAYBILL==queryType){//按运单号查询
		var waybillNos= form.findField('vo.dto.waybillNo').getValue();
		var waybillNosArray_tmp = stl.splitToArray(waybillNos);
		var waybillNosArray=new Array();
		
		for(var i=0;i<waybillNosArray_tmp.length;i++){
			if(waybillNosArray_tmp[i].trim()!=''){
				waybillNosArray.push(waybillNosArray_tmp[i]);
			} 
		}
	 
		if(waybillNosArray.length==0){
			Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.alertMessage'),consumer.cashIncomeStatements.PROMPT);
		 	return null;
		}
		if(waybillNosArray.length>1){
			Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.alertMessage'),agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.wayBillNoLimit'));
		 	return null;
		}
		Ext.apply(params,{ 
							'vo.dto.waybillNo' : waybillNosArray
							});
	}
	//设置查询类型
	Ext.apply(params,{
					'vo.dto.queryType':queryType
			});
	return params;
}

/**
 * Form查询方法
 */
agency.agencySystem.query=function(me,queryType){
	var form=me.getForm();
	var grid = Ext.getCmp('T_agency-queryAgencySystemReport_content').getAreaGrid();
	if(form.isValid()){
		var params=agency.agencySystem.setParams(form,queryType);
		if(null==params){
			return;
		}
		//设置查询参数
		grid.store.setSubmitParams(params);
		
		
		grid.getPagingToolbar().moveFirst();
	}else {
		Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.alertMessage'), agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.checkInputParameters'));
	}
} 

/**
 * Form重置方法
 */
agency.agencySystem.reset=function(){
	this.up('form').getForm().reset();
}
/**
 * 刷新Grid列表
 */
agency.agencySystem.refReshGrid=function(){
	var grid = Ext.getCmp('T_agency-queryAgencySystemReport_content').getAreaGrid();
	grid.getPagingToolbar().moveFirst();
}

/**
 *导出 
 */
agency.agencySystem.exportReport = function(){
		var	columns,
		grid;
	//获取表格
	grid =  Ext.getCmp('T_agency-queryAgencySystemReport_content').getAreaGrid();
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'),agency.agencySystem.i18n('foss.stl.agency.common.noDataToExport'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	
	//获取查询参数		
	var searchParams = grid.store.submitParams;
	
	//拼接vo，注入到后台
	Ext.Ajax.request({
	    url: agency.realPath('exportAgencySystemReport.action'),
	    form: Ext.fly('downloadAttachFileForm'),
	    method : 'post',
	    params :searchParams,
	    isUpload: true,
	    success : function(response){
	    	var result = Ext.decode(response.responseText);
	    	//如果异常信息有值，则弹出提示
	    	if(!Ext.isEmpty(result.errorMessage)){
	    		Ext.Msg.alert(agency.agencySystem.i18n('foss.stl.agency.common.alert'),result.errorMessage);
	    		return false;
	    	}
			Ext.ux.Toast.msg(agency.agencySystem.i18n('foss.stl.agency.common.alert'),agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.exportSuccess'), 'success', 1000);
	    },
	    failure : function(response){
			Ext.ux.Toast.msg(agency.agencySystem.i18n('foss.stl.agency.common.alert'),agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.exportFailed'), 'error', 1000);
	    }
	});
}

//偏线全盘报表 Model
Ext.define('Foss.agencySystem.AgencySystemReportModel', {
	extend :'Ext.data.Model', 
	fields :
	[  
	  'id'
	 ,'waybillNo'//运单号, 
	 ,'receiveOrgCode'//收货部门
	 ,'paymentMethod'//付款方式 
	 ,'agencyCompanyName'//代理名称
	 ,'amount'//件数 
	 ,'totalWeight'//重量（KG）
	 ,'totalVolume'//体积（立方米）
	 ,'targetOrgName'//目的站 
	 ,'registerFlag'//是否外发 
	 ,'auditStatus'//外发单审核状态
	 ,{
		name : 'billDate',//收货日期  
		type : 'date',
		convert : dateConvert
	  }
	 ,{
		name : 'handOverTime',//配载时间 
		type : 'date',
		convert : dateConvert
	  }
	 ,{
		name : 'arriveTime',//到达时间
		type : 'date',
		convert : dateConvert
	  } 
	 ]
 });
Ext.define('Foss.agencySystem.RegisterFlagStore', {
	extend : 'Ext.data.Store',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ],
	data : {
		'items' : [{
			code : '',
			name : agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.all')
		}, {
			code : 'Y',
			name : agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.yes')
		}, {
			code : 'N',
			name : agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.no')
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

//偏线全盘报表 Store
Ext.define('Foss.agencySystem.AgencySystemReportStore',{
	extend:'Ext.data.Store',
	model:'Foss.agencySystem.AgencySystemReportModel', 
	sorters: [{
        property: 'arriveTime',
        direction: 'DESC'
    }],
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : agency.realPath('queryAgencySystemReportAction.action'),
		reader : {
			type : 'json',
			root : 'vo.list',
			totalProperty : 'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
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

//偏线全盘报表 Grid
Ext.define('Foss.agencySystem.AgencySystemReportGrid',{
	extend:'Ext.grid.Panel',
	id:'Foss_AgencySystemReportGrid_Id',
	title:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.title'),
	columnWidth:1,
	stripeRows:true,
    columnLines:true,
	collapsible:false,
    bodyCls:'autoHeight',
    emptyText: agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.emptyResult'),
    viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
    },
    sortableColumns: false,
    frame:true,
    //增加表格列的分割线
	cls:'autoHeight', 
	store :null,
	autoScroll :true,
	height: 420,
    //分页
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:100,
				maximumSize:200,
				plugins:'pagesizeplugin'
			});
		}
       return me.pagingToolbar;
	},
	columns :[
		{ text:'ID', dataIndex :'id' ,width:100,hidden:true},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.waybillNo'), dataIndex :'waybillNo',width:90},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.receiveOrgCode'),dataIndex :'receiveOrgCode' ,width:100},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.billDate'), dataIndex :'billDate' ,width:110,
			renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return value;
    		}
    	} 
		},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.paymentMethod'), dataIndex :'paymentMethod' ,width:100,
			renderer: function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value,'SETTLEMENT__PAYMENT_TYPE');
			}
		},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.handOverTime'),dataIndex:'handOverTime' ,width:110,
			renderer:function(value){
				return stl.dateFormat(value,stl.FORMAT_TIME);
			} 
		},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.agencyCompanyName'), dataIndex:'agencyCompanyName' ,width:100},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.amount'),  dataIndex:'amount' ,width:70},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.totalWeight'),   dataIndex:'totalWeight' ,width:80},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.totalVolume'),  dataIndex:'totalVolume' ,width:100},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.targetOrgName'), dataIndex:'targetOrgName',width:100},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.arriveTime'), dataIndex:'arriveTime',width:110, 
			renderer:function(value){
				return stl.dateFormat(value,stl.FORMAT_TIME);
			} 
		},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.registerFlag'), dataIndex:'registerFlag',width:90,
			renderer:function(value){
			if(value!=null){
				return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.yes');
			}else{
				return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.no');
			}
		}	
		},
		{ text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.auditStatus'), dataIndex:'auditStatus',width:100,
			renderer:function(value){
				if (value == 'WAITINGAUDIT'){
					return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.waitingAudit');
				}else if(value == 'HASAUDITED'){
					return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.hasAudited');}
				else if(value == 'BACKAUDIT'){
					return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.backAudit');}
				else if(value == 'INVALID'){
					return agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.invalid');}
				else{
					return value;
				}
			}
		}
	],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store=Ext.create('Foss.agencySystem.AgencySystemReportStore');
		me.dockedItems =[{
			xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
			items:[{
		    	xtype:'container',
		    	html:'&nbsp;',
		    	columnWidth:.9
		    },{
				xtype:'button',
				text:agency.agencySystem.i18n('foss.stl.agency.common.export'),
				columnWidth:.09,
				handler:agency.agencySystem.exportReport
			}]
		}];
		
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
}); 

//查询偏线全盘报表  FORM
Ext.define('Foss.agencySystem.AgencySystemReportDateForm',{
	extend:'Ext.form.Panel', 
	defaults:{
		margin :'10 5 5 0',
		labelWidth :85,
		colspan :1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	{
		xtype:'datefield',
		name:'vo.dto.startBusinessDate',
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.beginBusinessDate'),
		format:'Y-m-d',
		value: new Date(),
		editable:false,
		allowBlank:false,
		maxValue : new Date()
	},{
		xtype:'datefield',
		name:'vo.dto.endBusinessDate', 
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.endBusinessDate'),
		format:'Y-m-d',
		value: new Date(),
		editable:false,
		allowBlank:false,
		maxValue : new Date()
	},{
		xtype:'dynamicorgcombselector',
		name:'vo.dto.arriveOrgCode',
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.arriveOrgCode'),
        allowBlank: false,
		listWidth:300,//设置下拉框宽度
		isPaging:true, //分页
		value:stl.currentDept.name,
		disabled:false
	},{
		xtype:'datefield',
		name:'vo.dto.handOverStartTime',
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.handOverStartTime'),
		format:'Y-m-d',
		maxValue : new Date() 
	},{
		xtype:'datefield',
		name:'vo.dto.handOverEndTime', 
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.handOverEndTime'),
		format:'Y-m-d',
		maxValue : new Date()
	},{
		xtype:'combo',
		colspan:3,
		name:'vo.dto.registerFlag',
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.registerFlag'),
		value:'',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.agencySystem.RegisterFlagStore')
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3, 
		defaultType:'button',
		layout:'column',
		items:[{
			  text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.reset'),   
			  columnWidth:.12,
			  handler:agency.agencySystem.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
			  text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.search'),
			  columnWidth:.12,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  agency.agencySystem.query(form,agency.agencySystem.QUERY_BY_DATE)
			  }
		  	}]
		}
	]  
});

//查询偏线全盘报表  FORM
Ext.define('Foss.agencySystem.AgencySystemReportWaybillForm',{
	extend:'Ext.form.Panel', 
	columnWidth:1,
	defaults:{
		margin :'10 5 5 0',
		labelWidth :85,
		colspan :1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	{
		name:'vo.dto.waybillNo',
		colspan:3,
		fieldLabel:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.waybillNo'),
		emptyText:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.inputOneWayBillNo'),
		regex:/^([0-9]{7,10})$/i,
		regexText:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.validateWayBillNo'),
		allowBlank:false
	},{
		border: 1,
		xtype:'container',
		width:243,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.reset'),   
			  columnWidth:.4,
			  handler:agency.agencySystem.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.2
			},
		  	{
			  text:agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.search'),
			  columnWidth:.4,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  agency.agencySystem.query(form,agency.agencySystem.QUERY_BY_WAYBILL)
			  }
		  	}]
		}
	]  
});

/**
 * 现金收入查询tab
 */
Ext.define('Foss.agencySystem.AgencySystemReportTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	//columnHeight: 'autoHeight',
	height:170, 
	items : [
	 {
		title : agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.searchByDate'),
		tabConfig : {
			width : 120
			},
		items : [
				 Ext.create('Foss.agencySystem.AgencySystemReportDateForm')
		       ]
	},
	{
		title : agency.agencySystem.i18n('foss.stl.agency.queryagencysystemreport.searchByWayBillNo'),
		tabConfig : {
			width : 120
			},
		items : [ 
		         Ext.create('Foss.agencySystem.AgencySystemReportWaybillForm')
			    ]
	} 
   ] 
});
 

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_agency-queryAgencySystemReport_content')) {
		return;
	} 
	var queryTab = Ext.create('Foss.agencySystem.AgencySystemReportTab');//查询FORM
	var areaGrid = Ext.create('Foss.agencySystem.AgencySystemReportGrid');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id :'T_agency-queryAgencySystemReport_content',
		cls:"panelContentNToolbar", //必须添加,内容定位用。
		bodyCls:'panelContentNToolbar-body', //必须添加,内容定位用。
		layout :'auto',
		//获得查询FORM
		getQueryTab : function() {
			return queryTab;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return areaGrid;
		},
		items:[ queryTab,areaGrid],
		renderTo :'T_agency-queryAgencySystemReport-body'
	}); 
});