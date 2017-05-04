/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期getTargetDate
 */
pickup.driverPickupBillPrint.getStartDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期  23:59:59
 */
pickup.driverPickupBillPrint.getEndDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};
/**
 * 查询方式类型
 * @param queryType
 */
pickup.driverPickupBillPrint.QUERY_BY_DATE='TD';
pickup.driverPickupBillPrint.QUERY_BY_BILL='TDZ';
pickup.driverPickupBillPrint.QUERY_BY_COLLECT='DERE';
pickup.driverPickupBillPrint.QUERY_BY_WAYBILL='WB';
pickup.driverPickupBillPrint.CHOICE_BY_ACCOUNT='AC';
pickup.driverPickupBillPrint.CHOICE_BY_CONFIRM='CO';


pickup.driverPickupBillPrint.setParams=function(form,queryType){
	//定义查询参数
	var params={};
	//按日期查询
	if(pickup.driverPickupBillPrint.QUERY_BY_DATE==queryType){
		var startDate = form.findField('startTime').getValue();
		var driverCode = form.findField('driverCode').getValue();
		var endDate = form.findField('endTime').getValue();
		var waybillNo = form.findField('waybillNo').getValue();
		if(!startDate){
			
			//开始日期不能为空
			Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.startDateCannotBeNull'));
			return null;
		}
		if(!endDate){
			
			//结束日期不能为空
			Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.endDateCannotBeNull'));
			return null;
		}
			
//		var diffDays = stl.compareTwoDate(startDate, endDate);
//		if (diffDays > stl.DATELIMITDAYS_MONTH) {
//			
//			//起始日期和结束日期间隔不能超过{0}天
//			//stl.DATELIMITDAYS_MONTH
//			Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.startAndEndDateDiffCannotExceedSomeDays',[stl.DATELIMITDAYS_MONTH]));
//			return null;
//		} else if (diffDays < 1) {
//			
//			//开始日期不能晚于结束日期
//			Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.startDateCannotGtEndDate'));
//			return null;
//		}

		//获取FORM所有值
		params = form.getValues();

		//设置账单号参数到DTO中
		Ext.apply(params, {
			'vo.driverPickupBillPrintDto.pickupTimeBegin': startDate,//接货时间begin
			'vo.driverPickupBillPrintDto.pickupTimeEnd': endDate,//接货时间End
			'vo.driverPickupBillPrintDto.driverCode': driverCode,//接货司机工号 
			//'vo.driverPickupBillPrintDto.driverName': driverName,//接货司机工号 
			'vo.driverPickupBillPrintDto.waybillNo': waybillNo//运单号
					});
	
	}//按账单号查询
	return params;
}


/**
 * 获取Grid组件信息
 * @returns
 */
pickup.driverPickupBillPrint.getPageObj=function(){
	var me=Ext.getCmp('T_pickup.driverPickupBillPrint_content');
	if(me){
		var grid = me.getQueryGrid();
		var store=grid.getStore();
		if(store.data.length==0){
			
			//查询结果集为空不能进行该操作
			Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'), pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.queryResultNull.cannotOperate'));
			return null;
		}
		return grid;	
	}
	return null;
};
/**
 * Form查询方法
 */
pickup.driverPickupBillPrint.query=function(f,me,queryType){
	var form=f.getForm();
	
	var grid = Ext.getCmp('T_pickup.driverPickupBillPrint_content').getQueryGrid();

	if(form.isValid()){
		var params=pickup.driverPickupBillPrint.setParams(form,queryType);
		if(null==params){
			return;
		}
		//清除上一次查询的结果
		grid.store.removeAll();
		//设置查询参数
		grid.store.setSubmitParams(params);
		
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		//设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	      	if(success == false){
	      		Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'),grid.store.proxy.reader.jsonData.message);
	      		me.enable(true);
	      		return;
	      	}
			var result =   Ext.decode(operation.response.responseText),
			
			toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
			toolBar.getComponent('totalCounts').setValue(result.totalCount);
			me.enable(true);
	       }
	    });
	}else {
		
		//请检查输入条件是否合法
		Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'), pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.pleaseCheckInputConditionisLegal'));
	}
}

/**
 * 导出方法
 */
//consumer.cashIncomeStatements.cashIncomeStatementsExport = function(){
//	var grid = consumer.cashIncomeStatements.getPageObj();
//	if(grid==null){
//		return;
//	}
//	Ext.MessageBox.buttonText.yes = pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.OK');  
//	Ext.MessageBox.buttonText.no = pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.cancel'); 
//	
//	//确定要导出查询结果吗?
//	Ext.Msg.confirm( pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.sureExportQueryResult'), function(btn,text){
//		if(btn == 'yes'){
//			var params=grid.store.submitParams;
//			if(!Ext.fly('downloadCashIncomeForm')){
//				var frm = document.createElement('form');
//				frm.id = 'downloadCashIncomeForm';
//				frm.style.display = 'none';
//				document.body.appendChild(frm);
//			}
//	
//			Ext.Ajax.request({
//				url:consumer.realPath('cashIncomeStatementsExport.action'),
//				form: Ext.fly('downloadCashIncomeForm'),
//				params:params,
//				method:'post',
//				isUpload: true,
//				success:function(response){
//					var result = Ext.decode(response.responseText);
//				},
//				failure:function(response){
//					var result = Ext.decode(response.responseText);
//				}
//			});
//		}
//	});
//}

/**
 * 设置打印公共参数
 * @returns 
 */
pickup.driverPickupBillPrint.getPrintPickupParams=function(grid){  
	//获取Form信息
	var params =grid.store.submitParams;
	var orgCode=FossUserContext. getCurrentUserDept().code;
//	var depositReceivedNos=params["vo.dto.depositReceivedNos"];
//	if(depositReceivedNos){
//		depositReceivedNos=depositReceivedNos.toString();
//	}
	
	var formParams={
			'id' : params["id"],
			'startTime' : params["startTime"],
			'endTime' : params["endTime"],
			'driverCode' : params["driverCode"],
			'driverName' : params["driverName"],
			'waybillNo' : params["waybillNo"],
			'orgCode' : orgCode
	}; 
	
	//转化列头和列明
	var columns = grid.columns;
	var arrayColumns = [];
	var index=0;//列标
	//传入后台打印，与index拼接为后台列明
	var headerStr = "{";
	for(var i=1;i<columns.length;i++){
		if(columns[i].isHidden()==false){
			var hederName = columns[i].text;
			if(i!=columns.length-1){
				if(columns[i].text!=pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.actionColumn')){
					index = index+1;
					headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\',';
					arrayColumns.push(columns[i].dataIndex);
				}
			}else{
				index = index+1;
				arrayColumns.push(columns[i].dataIndex);
				headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\'';
			}
		}
	}
	//控制打印列数
	if(arrayColumns.length>15){
		
		//最多不能超过15列
		Ext.Msg.alert(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'),pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.15columnsMost'));
		return null;
	}
	headerStr = headerStr+'}';
	var headerObject = Ext.decode(headerStr);
    targetObject = Ext.merge(headerObject,formParams);
	targetObject.arrayColumns = arrayColumns; 
	
return targetObject;
}

/**
 * 打印汇总
 */
//consumer.cashIncomeStatements.printCashIncomeTotal
//pickup.driverPickupBillPrint.printPickupTotal=function(){
//	var grid=consumer.cashIncomeStatements.getPageObj();
//	if(grid==null){
//		return;
//	}
//	Ext.MessageBox.buttonText.yes = pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.OK');  
//	Ext.MessageBox.buttonText.no = pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.cancel');
//	
//	//确定要打印吗?
//	Ext.Msg.confirm(pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.warmTips'), pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.surePrint'), function(btn,text){
//		if(btn == 'yes'){
//			//获取打印参数信息
//			targetObject = pickup.driverPickupBillPrint.getPrintPickupParams(grid);
//			do_printpreview('poolcashincomestatements',targetObject,ContextPath.STL_WEB);
//		}
//	});
//}

/**
 * 打印明细
 */
pickup.driverPickupBillPrint.printPickupDetail=function(){
	var grid=pickup.driverPickupBillPrint.getPageObj();
	if(grid==null){
		return;
	}
	Ext.MessageBox.buttonText.yes = pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.OK');  
	Ext.MessageBox.buttonText.no = pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.cancel');
	
	//确定要打印吗?
	Ext.Msg.confirm(pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.warmTips'), pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.surePrint'), function(btn,text){
		if(btn == 'yes'){
			//获取打印参数信息
			targetObject = pickup.driverPickupBillPrint.getPrintPickupParams(grid);
			do_printpreview('driverPickupBillPrint',targetObject,ContextPath.PKP_DELIVER);//ContextPath.PKP_DELIVER 全栈作用域
		}
	});
}


pickup.driverPickupBillPrint.reset=function(){
	this.up('form').getForm().reset();
}
/**
 *司机接货 model 
 */
Ext.define('pickup.driverPickupBillPrint.DriverPickupBillPrintModel',{
	extend:'Ext.data.Model',
	fields :[  
	        {name: 'extid', type: 'string'},//额外的用于生成的EXT使用的列
	 	    { name: 'id',type:'id' },//id
	 	    { name: 'pickupTime',type:'date',convert: dateConvert }, //开单时间
	 	    { name: 'driverCode',type:'string' }, //接货司机工号
	 	    { name: 'driverName',type:'string' }, //接货司机Name
	 	    { name: 'waybillNo',type:'string' }, //运单号
	 	    { name: 'createOrgName',type:'string' }//收货部门Name
	    ]
});


/**
 * 司机接货  store
 */
Ext.define('pickup.driverPickupBillPrint.DriverPickupBillPrintStore',{
	extend:'Ext.data.Store',
	model:'pickup.driverPickupBillPrint.DriverPickupBillPrintModel',  
	pageSize:25,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pickup.realPath('queryDriverPickupBill.action'),
		reader : {
			type : 'json',
			root : 'vo.rtDriverPickupBillPrintDtoList',
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

/**
 * FORM 定义
 */

//司机接货查询 Form
Ext.define('pickup.driverPickupBillPrint.QueryDateForm',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:230,
	defaults:{
		margin :'5 10 5 10',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
//	layout:{
//		type :'table',
//		columns :2
//	},
	layout: 'column',
	items:[
	    {
			xtype: 'rangeDateField',//接货时间
			fieldId: 'FOSS_Time_cashDiffReport_Id_start',
			fieldLabel: pickup.driverPickupBillPrint.i18n('pkp.pickup.cashDiffReport.pickupTime'),//字段标题 -- 接货时间
			allowFromBlank : false,
			allowToBlank : false,
			editable:false,
			dateType: 'datetimefield_date97',
			fromName: 'startTime',
			toName: 'endTime',
			fromValue: Ext.Date.format(pickup.driverPickupBillPrint.getStartDate(new Date(),0),'Y-m-d H:i:s'),
			toValue:   Ext.Date.format(pickup.driverPickupBillPrint.getEndDate(new Date(),0),'Y-m-d H:i:s'),
			format: 'Y-m-d H:i:s' ,
			columnWidth: 0.5
			
		},
		{
			xtype : 'commonemployeeselector', 
			id : 'driverCode',//id
			name:'driverCode',//
			fieldLabel:pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.driverNoInfo'),//字段标题-接货司机信息
			columnWidth: 0.25
		},{
			xtype : 'textfield', 
			id : 'waybillNo',//id
			name:'waybillNo',//
			fieldLabel:pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.waybillNo'),//运单
			columnWidth: 0.25
		},
	    {
			border: 1,
			xtype:'container',
			columnWidth:0.5,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[
//			       {
//				  text:pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.reset'),   //重置
//				  columnWidth:.3,
//				  handler:pickup.driverPickupBillPrint.reset 
//			  	},
			  	{
				  text:pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.search'),//查询
				  columnWidth:0.3,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  pickup.driverPickupBillPrint.query(form,me,pickup.driverPickupBillPrint.QUERY_BY_DATE)
				  }
			  	}]
			}
	  ]
});




//司机接货明细 Grid
Ext.define('pickup.driverPickupBillPrint.PickupGrid',{
	extend: 'Ext.grid.Panel',
	title: pickup.driverPickupBillPrint.i18n('pkp.pickup.cashDiffReport.driverNoInfo'),//司机接货明细
	columnWidth: 1,
	stripeRows: true,
	columnLines: true,
	collapsible: false,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	bodyCls: 'autoHeight',
	frame: true,
	cls: 'autoHeight', 
	store : null,
	autoScroll : true,
	height: 450,
	emptyText: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.emptyText'),
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:25,
				maximumSize:1000,
				plugins:'pagesizeplugin'
			});
		}
     return me.pagingToolbar;
	},
	hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	totalCounts:null,//总行数
	getTotalCounts:function(){
		var me=this;
		if(Ext.isEmpty(me.totalCounts)){
			me.totalCounts=0;
		}
		return me.totalCounts;
	},
	columns : [ 
			 {
	    	//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.id'), //序号
			//关联model中的字段名
			dataIndex: 'id',
			width:50,
			xtype: 'rownumberer'
	    },{
			//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.pickupTime'), //开单时间
			//关联model中的字段名
			dataIndex: 'pickupTime', 
			width:130,
			type: 'columndate',
//            dateFormat: 'Y-m-d h:i:s'
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.waybillNo'), //运单号
			//关联model中的字段名
			dataIndex: 'waybillNo',
			width:100				
		},{
			//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.driverCode'), //接货司机工号
			//关联model中的字段名
			dataIndex: 'driverCode',
			width:80			
		},{
			//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.driverName'), //接货司机姓名
			//关联model中的字段名
			dataIndex: 'driverName',
			width:80				
		},{
			//字段标题
			header: pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.receiveOrgName'), //营业部
			//关联model中的字段名
			dataIndex: 'createOrgName', 
			width:200,
			xtype: 'ellipsiscolumn'
		}
			],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items :[
//			 {
//				xtype :'button',
//				text :pickup.driverPickupBillPrint.i18n('foss.stl.consumer.common.export'),
//				columnWidth :.1,
//				handler :consumer.cashIncomeStatements.cashIncomeStatementsExport,
//				disabled:!consumer.cashIncomeStatements.isPermission('/stl-web/consumer/cashIncomeStatementsExport.action'),
//				hidden:!consumer.cashIncomeStatements.isPermission('/stl-web/consumer/cashIncomeStatementsExport.action')
//			},
//			{
//				xtype :'button',
//				text :pickup.driverPickupBillPrint.i18n('foss.stl.consumer.cashIncome.printGather'),//打印汇总
//				columnWidth :.1,
//				handler :consumer.cashIncomeStatements.printCashIncomeTotal
//			},
			{
				xtype :'button',
				text :pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.print'),//打印明细
				columnWidth :.1,
				handler : pickup.driverPickupBillPrint.printPickupDetail
			}
		  ]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		     defaults:{
				margin:'1 2 1 1'
			},
			/* BUG-30317  添加总条数统计项，修改统计项顺序为：合计总金额、总条数、现金、银行卡、电汇、支票、网上支付
			 * BUG-36770  合计总金额显示不清楚
			 * Yang Shushuo
			 * 2013-06-24
			 */
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},
			{
				xtype:'displayfield',
				itemId: 'totalCounts',
				name:'totalCounts',
				allowBlank:true,
				labelWidth:65,
				columnWidth:.2,
				fieldLabel:pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.allNum'),//总条数
				value:me.getTotalCounts()
			}]
		 }];
		
		me.store=Ext.create('pickup.driverPickupBillPrint.DriverPickupBillPrintStore');
		
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
});

/**
 * 司机接货查询tab
 */
Ext.define('pickup.driverPickupBillPrint.QueryPickupTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	//columnHeight: 'autoHeight',
	height:230, 
	items : [
	 {
		title : pickup.driverPickupBillPrint.i18n('pkp.pickup.driverPickupBillPrint.fromTitle'),//按日期查询
		tabConfig : {
			width : 120
			},
		items : [
				 Ext.create('pickup.driverPickupBillPrint.QueryDateForm')
		       ]
	},
   ] 
});
 
//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_pickup.driverPickupBillPrint_content')) {
		return;
	} 
	var queryTab = Ext.create('pickup.driverPickupBillPrint.QueryPickupTab');//查询TAB
	var queryGrid = Ext.create('pickup.driverPickupBillPrint.PickupGrid');//查询结果GRID
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pickup.driverPickupBillPrint_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//获得查询FORM
		getQueryTab : function() {
			return queryTab;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items: [queryTab,queryGrid],
		renderTo: 'T_pickup-driverPickupBillPrint-body'
	});
});


