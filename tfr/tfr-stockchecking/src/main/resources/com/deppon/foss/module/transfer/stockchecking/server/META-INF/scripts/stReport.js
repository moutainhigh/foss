/***********************************************************************************************************
清仓差异报告管理

菜单入口： 清仓差异报告查询
1、Panel → id:T_stockchecking-streportindex_content
 1.1、差异报告查询条件
      Form → Foss.stReport.QueryForm
                   差异报告状态
        Store → Foss.StReport.handleStatus.store
                   差异报告异常类型
        Store → Foss.StReport.exType.store
 1.2、差异报告查询结果
      Grid → Foss.stReport.stReportQueryGrid
       Store → Foss.stReport.stReportQueryStore
         Model → Foss.stReport.stReportQueryModel
 1.3、弹出清仓差异报告明细页面
     Window → Foss.stReport.window.editStReportDetail (getEditStReportDetailWindow)  →
处理清仓差异报告
2、Panel → Foss.stReport.panel.editStReportDetail
   column
       差异明细
   2.1、Grid → Foss.stReport.editStReportDetail.grid.detailGrid
          Store → Foss.stReport.editStReportDetail.reportDetail.store	(公用)
           Model → Foss.stReport.editStReportDetail.reportDetail.model  (公用)
       相关联的差异明细
   2.2、Grid → Foss.stReport.editStReportDetail.grid.relativeDetailGrid
          Store → Foss.stReport.editStReportDetail.reportDetail.store   (公用)
           Model → Foss.stReport.editStReportDetail.reportDetail.model  (公用)
       弹出的确认已标记窗口
   2.3、Window → Foss.stReport.editStReportDetail.window.subconfirm (getSubConfirmWindow)  →
        2.3.1、确认已标记表单
         Form → Foss.stReport.editStReportDetail.subconfirm.form
*********************************************************************************************************************/
var deffientTypeLack=0;//少货数
var deffientTypeExpress=0;//多货数
/**当前转运场少货件数**/
var lessGoodsCount=0;
/**当前转运场少货件数多货件数（不包含放错货区）**/
var  exceedDoodsCount=0; 
/**放错货区件数**/
var  errorGoodsAreaCount=0;
/**已签收件数**/
var  signCount=0;
/**已作废件数**/
var  obsoleteCount=0;
/**已中止作废件数**/
var  abortedCount=0;
/**
 *查询统计信息的方法
 ***/
 function goodsCountInfo(){ 
	 Ext.Ajax.request({
			url: stockchecking.realPath('goodsCountInfo.action'),
			  
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.stReportVO!=null){
					lessGoodsCount=result.stReportVO.lessGoodsCount;//当前转运场少货件数
					exceedDoodsCount=result.stReportVO.exceedDoodsCount; //当前转运场少货件数多货件数（不包含放错货区）
					errorGoodsAreaCount=result.stReportVO.errorGoodsAreaCount;	//放错货区件数	
					signCount=result.stReportVO.signCount;//已签收件数
					obsoleteCount=result.stReportVO.obsoleteCount//已作废件数
					abortedCount=result.stReportVO.abortedCount//已中止作废件数
				}
				Ext.getCmp('goodsCountInfo').setValue('今日产生多货（不含放错货区）'+exceedDoodsCount+'件，多货放错货区'+errorGoodsAreaCount+'件，少货'+lessGoodsCount+'件，已签收'+signCount+'件，已作废'+obsoleteCount+'件，已中止作废'+abortedCount+'件。');
			},
			exception : function(response) {
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), result.message);
			}
		});
	 }
	
Ext.define('Foss.StReport.handleStatus.store', {
	extend:'Ext.data.Store',
    fields: ['valueCode', 'valueName'],
    data : [
        {"valueCode":"ALL", "valueName": stockchecking.i18n('Foss.stockchecking.checkbox.defaultvalue')},
        {"valueCode":"DOING", "valueName": stockchecking.i18n('Foss.stockchecking.sttaskreport.status.doing')},
        {"valueCode":"DONE", "valueName": stockchecking.i18n('Foss.stockchecking.sttaskreport.status.done')}
    ]
});

Ext.define('Foss.StReport.exType.store', {
	extend:'Ext.data.Store',
    fields: ['valueCode', 'valueName'],
    data : [
        {"valueCode":"ALL", "valueName": stockchecking.i18n('Foss.stockchecking.checkbox.defaultvalue')},
        {"valueCode":"EXIST", "valueName": stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.allstatus')},
        {"valueCode":"SURPLUS", "valueName": stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.surplus')},
        {"valueCode":"LACK", "valueName": stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.lack')}
    ]
});

Ext.define('Foss.stReport.stReportQueryModel',{
	extend: 'Ext.data.Model',
	idProperty : 'id',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'reportCode' , type: 'string'},			//报告编号
		{name: 'exceedGoodsQty', type: 'string'},		//多货件数
		{name: 'lessGoodsQty' , type: 'string'},		//少货件数
		{name: 'taskStatus', type: 'string'},			//任务状态
		{name: 'goodsAreaName', type: 'string'},		//货区名称
		{name: 'administrativeRegion', type: 'string'},	//行政区
		{name: 'pieceRegion', type: 'string'},		    //件区
		{name: 'operator', type: 'string'},				//清仓人
		{name: 'handleStatus', type: 'string'},			//报告处理状态
		{name: 'createTime', type: 'string', convert : function(value){//清仓差异报告创建时间
			if(value != null){
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}else{
				return null;
			}
		}}				
	]
});

Ext.define('Foss.stReport.editStReportDetail.reportDetail.model',{
	extend: 'Ext.data.Model',
	idProperty : 'id',
	fields: [
	         {name: 'id', type: 'string'}, 				//差异报告明细ID
	         {name: 'handleStatus' , type: 'string'},	//报告明细处理状态
	         {name: 'stDifferReportId' , type: 'string', hidden:true},		//报告ID
	         {name: 'reportCode' , type: 'string'},		//报告编号
	         {name: 'packageNo' , type: 'string'},		//包号
	         {name: 'waybillNo' , type: 'string'},		//运单号
	         {name: 'serialNo' , type: 'string'},		//流水号
	         {name: 'goodsAreaName', type: 'string'},	//货区
	         {name: 'administrativeRegion', type: 'string'},//行政区
	 		 {name: 'pieceRegion', type: 'string'},		//件区
//	         {name: 'oaErrorNo', type: 'string', convert: function(value){
//				if(value == 'N/A'){
//					return '';
//				}else{
//					return value;
//				}
//	         }},		//OA差错编号
	         
	         {name: 'oaErrorNo', type: 'string'},	//OA差错编号
	         
	         
	         {name: 'differenceType' , type: 'string', hidden:true},	//差异类型
	         {name: 'differenceTypeDesc' , type: 'string', convert: function(value){
				if(value == 'LACK'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.lack');
				}else if(value == 'ERROR_GOODSAREA'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.errorgoodsarea'); //放错货区
				}else if(value == 'CARRY'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carry'); //多货-夹带
				}else if(value == 'CARRY_OTHERS'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carryothers'); //多货-异地夹带
				}else if(value == 'SIGN'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carryothers'); //已签收
				}else if(value == 'OBSOLETE'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carryothers'); //已作废
				}else if(value == 'ABORTED'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carryothers'); //中止作废
				}else if(value == 'RFC_DEST'){
					return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.carryothers'); //更改目的站
				}
			}},	
			//差异原因
			 {name: 'differenceReason' , type: 'string', convert: function(value){
					if(value == 'ZCLS'){
						return '装车漏扫';
					}else if(value == 'QSWCK'){
						return '签收未出库'; 
					}else if(value == 'FCHQ'){
						return '放错货区'; 
					}else if(value == 'QT'){
						return '其他'; 
					}else if(value == 'QCLS'){
						return '清仓漏扫'; 
					}else {
						return value;
						} 
				}},	
	         {name: 'operator', type: 'string'},		//清仓员
	         {name: 'reportCreateTime', type: 'string', convert : function(value){
	 			if(value != null){
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return null;
				}
			}},//报告生成时间
	         {name: 'handleTime', type: 'string', convert : function(value){
	 			if(value != null){
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return null;
				}
			}},		//异常处理时间
			{name: 'remark', type: 'string'},		//备注
			{name: 'differDetailList', type: 'object'},//运单号对应的流水号明细
			{name: 'goodsName', type: 'string'}, //货物名称
			{name: 'transProperty',type: 'string'},//运输性质
			{name: 'goodsPackage', type: 'string'}, //包装
			{name: 'goodsWeight', type: 'string'}, //重量
			{name: 'goodsVolume', type: 'string'}, //体积
			{name: 'billTargetOrgName', type: 'string'}, //到达部门(目的站)
			{name: 'operateTime', type: 'string', convert : function(value){
	 			if(value != null){
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return null;
				}
			}} //操作时间
	]
});

Ext.define('Foss.stReport.stReportQueryStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stReport.stReportQueryModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: stockchecking.realPath('queryStReport.action'),
		reader : {
			type : 'json',
			root : 'stReportVO.stReportDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = stockchecking.stReportQueryForm.getValues();
			Ext.apply(operation, {
				params : {
					'stReportVO.stReportDto.startTime': queryParams.startTime,
					'stReportVO.stReportDto.endTime': queryParams.endTime,
					'stReportVO.stReportDto.reportCode': queryParams.reportCode,
					'stReportVO.stReportDto.goodsAreaCode': queryParams.goodsAreaCode,
					'stReportVO.stReportDto.deptcode': queryParams.deptcode,
					'stReportVO.stReportDto.handleStatus': queryParams.handleStatus,
					'stReportVO.stReportDto.operator': queryParams.empCode,
					'stReportVO.stReportDto.waybillNo': queryParams.waybillNo,
					'stReportVO.stReportDto.exType': queryParams.exType
				}
			});	
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.nosearchresult'));
			}
		},
		datachanged:function( store,eOpts){
			if(stockchecking.transferCenter=='Y'){
				goodsCountInfo();
			}
		}
	}
});

Ext.define('Foss.stReport.editStReportDetail.reportDetail.store', {
	extend: 'Ext.data.Store',
	model: 'Foss.stReport.editStReportDetail.reportDetail.model',
	pageSize: 50,
	autoLoad: false,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: stockchecking.realPath('queryReportDetailById.action'),
		reader : {
			type : 'json',
			root : 'stReportVO.stReportDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store,operation) {
			Ext.apply(operation, {
				params : {
					'stReportVO.stReportDto.id': stockchecking.stReportId
				}
			});	
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.nosearchresult'));
			}
		}
	}
	
});

Ext.define('Foss.stReport.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss.stReport.QueryForm.id',
	layout: 'column',
	frame: true,
	border: false,
	title : stockchecking.i18n('Foss.stockchecking.search.title')+ '<font color="red" size="1px" style="font-weight : lighter">   注：清仓任务差异报告会在系统后台触发，请耐心等待，无差异的不需要生成差异报告</font>',
	defaults: {
		margin: '5 5 5 5'
	},
	items: [{
		xtype: 'rangeDateField',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.createtime'),
		fieldId: 'Foss_stReport_QueryForm_createTime_ID',
		dateType: 'datetimefield_date97',
		allowBlank:false,
		fromName: 'startTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(), 
										    new Date().getMonth(), 
										    new Date().getDate() - 7,
										    new Date().getHours() + 1,
										    new Date().getMinutes(),
										    new Date().getSeconds()),
								   'Y-m-d H:i:s'),	//默认查询7天内的数据
		toName: 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(), 
							     new Date().getMonth(), 
							     new Date().getDate(),
							     new Date().getHours() + 1,
							     new Date().getMinutes(),
							     new Date().getSeconds()),
				   				'Y-m-d H:i:s'),
		columnWidth: .5
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.exType'),
		name: 'exType',
		store: Ext.create('Foss.StReport.exType.store'),
		queryMode: 'local',
		displayField: 'valueName',
		valueField:'valueCode', 
		value : 'ALL',
		editable :false,
		columnWidth:.25
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'deptcode',
		columnWidth:.25
	},{
		xtype: 'commongoodsareaselector',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'),
		name: 'goodsAreaCode',
		deptCode: stockchecking.tranferCode,
		columnWidth:.25
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commonemployeeselector',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'),
		name: 'empCode',
		parentOrgCode: FossUserContext.getCurrentDept().code,
		columnWidth:.25
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'),
		name: 'waybillNo',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskNo'),
		name: 'reportCode',
		columnWidth:.25
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleStatus'),
		name: 'handleStatus',
		store: Ext.create('Foss.StReport.handleStatus.store'),
		queryMode: 'local',
		displayField: 'valueName',
		valueField:'valueCode', 
		value : 'ALL',
		editable :false,
		columnWidth:.25
	},{
		xtype: 'container',
		columnWidth:.25,
		html: '&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: stockchecking.i18n('Foss.stockchecking.button.reset'),
			columnWidth:.08,
			handler:function(){
				var theForm = this.up('form').getForm();
				theForm.reset();
				theForm.findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), 
					    new Date().getMonth(), 
					    new Date().getDate() - 7,
					    new Date().getHours() + 1,
					    new Date().getMinutes(),
					    new Date().getSeconds()),
						'Y-m-d H:i:s'));
				theForm.findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), 
					     new Date().getMonth(), 
					     new Date().getDate(),
					     new Date().getHours() + 1,
					     new Date().getMinutes(),
					     new Date().getSeconds()),
		   				'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text: stockchecking.i18n('Foss.stockchecking.button.search'),
			disabled: !stockchecking.isPermission('stockchecking/queryStReportButton'),
			hidden: !stockchecking.isPermission('stockchecking/queryStReportButton'),
			cls: 'yellow_button',
			columnWidth:.08,
			handler: function() {
				var startTime = stockchecking.stReportQueryForm.getValues().startTime;
				var endTime = stockchecking.stReportQueryForm.getValues().endTime;
				var difTime = 0;
				
				if(startTime.length == 0 || endTime.length == 0){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttaskreport.search.validator.createtime'));
					return;
				}
				
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 7){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttaskreport.search.validator.createtime.span.limit'));
					
					return;
				}
				
				stockchecking.reportPagingBar.moveFirst();
			}
		}]
	  }],
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stReport.stReportQueryGrid', {
	extend:'Ext.grid.Panel',
	id: 'Foss.stReport.stReportQueryGrid.id',
	height: 500,
	autoScroll: true,
	columnLines: true,
	frame: true,
	forceFit: true,
	enableColumnHide: false,
    sortableColumns: false,
	columns: [{
		xtype:'actioncolumn',
		width:50,
		flex: 1,
		text: stockchecking.i18n('Foss.stockchecking.button.operator'),
		align: 'center',
		items: [{
            tooltip: stockchecking.i18n('Foss.stockchecking.button.gapDetails'),
            iconCls:'deppon_icons_edit',
            handler: function(grid, rowIndex, colIndex) {
            	var stReportId = grid.getStore().getAt(rowIndex).data.id;
            	stockchecking.stReportId = stReportId;
            	stockchecking.tempStReportId = stReportId;
            	Ext.getCmp('T_stockchecking-streportindex_content').getEditStReportDetailWindow().show();
            }
        }]
	},{
		header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.reportCode.abbreviation'), 
		dataIndex: 'reportCode',
		width : 80,
		flex: 1
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.exceedGoodsQty'), 
		dataIndex: 'exceedGoodsQty',
		width : 50,
		flex: 0.8
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.lessGoodsQty'), 
		dataIndex: 'lessGoodsQty',
		width : 50,
		flex: 0.8
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), 
		dataIndex: 'goodsAreaName',
		width : 80,
		flex: 1
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttask.model.administrativeRegion'), 
		dataIndex: 'administrativeRegion',
		width : 80,
		flex: 1
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttask.model.pieceRegion'), 
		dataIndex: 'pieceRegion',
		width : 80,
		flex: 1
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), 
		dataIndex: 'operator',
		width : 80,
		flex: 0.5
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleStatus'), 
		dataIndex: 'handleStatus',
		renderer:function(value){
			if(!Ext.isEmpty(value)){
				if(value == 'DOING'){
					return stockchecking.i18n('Foss.stockchecking.sttaskreport.status.doing');
				}
				else if(value == 'DONE'){
					return stockchecking.i18n('Foss.stockchecking.sttaskreport.status.done');
				}
			}
		},
		width : 80,
		flex: 0.8
	},{ 
		header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.createtime'), 
		dataIndex: 'createTime',
		fixed: true,
		width : 100,
		flex: 1
	}],
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 80
		},
		items: [{
				fieldLabel : '统计信息',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1,
				value: null,
				id : 'goodsCountInfo'
			}]
	  }],
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stReport.stReportQueryStore');
		/*me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});*/
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 10,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['20', 20], ['30', 30], ['40', 40],['50', 50]]
			})
		});
		stockchecking.reportPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig: {
		enableTextSelection: true
    }
});

//查询关联差异明细方法
stockchecking.queryRelateDetail = function(array) {
	if(array == null) {
		return;
	}
	if(array.lenth == 0) {
		//清空关联明细
		var relativeDetailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id');
		relativeDetailGrid.store.removeAll();
	} else {
		var sm = Ext.getCmp('Foss.stReport.editStReportDetail.grid.detailGrid.id').getSelectionModel();
		var record = sm.getSelection();	
		stockchecking.record = record;
		if(record && record[0]) {
			var stReportId = record[0].data.stDifferReportId;
			var waybillNo = record[0].data.waybillNo;
			var serialNos = array;
			
			Ext.Ajax.request({
				url: stockchecking.realPath('queryReportRelativeDetail.action'),
				params: {
					'stReportVO.stReportDto.id': stReportId,
					'stReportVO.stReportDto.waybillNo': waybillNo,
					'stReportVO.stReportDto.serailNosList': serialNos
				},
				timeout:300000,
				success: function(response){
					result = Ext.decode(response.responseText);
					
					var relativeDetailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id');
					//载入相关联明细
					relativeDetailGrid.store.loadData(result.stReportVO.stReportRelativeDetailDtoList);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
				}
			});
		}
	}
};

//存放选择的差异报告明细
stockchecking.selectedSerialNosMap = new Ext.util.HashMap();
stockchecking.allSerialNos = new Array();
stockchecking.selectSerailNos = new Array();

Ext.define('Foss.stReport.editStReportDetail.grid.detailGrid',{
	extend:'Ext.grid.Panel',
	id:'Foss.stReport.editStReportDetail.grid.detailGrid.id',
	columnLines: true,
	height: 400,
	title: stockchecking.i18n('Foss.stockchecking.panel.editstreportdetail.title'), //差异明细
	frame: false,
	forceFit: true,
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'stReport_editStReportDetailGrid_detailSerialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.stReport.editStReportDetail.grid.detailSerialNoGrid'
	} ],
	
	columns:[
	         /*{
		xtype:'actioncolumn',
		text: stockchecking.i18n('Foss.stockchecking.button.operator'),
		align: 'center',
		flex: 1,
		//默认不可用，只有选中行时才可用
		//disabled: true,
		items: [
		        {
                tooltip: stockchecking.i18n('Foss.stockchecking.button.markdeal'),  //标记已处理
                iconCls:'deppon_icons_affirm',
                handler: function(grid, rowIndex, colIndex) {
                	if(grid.getSelectionModel().isSelected(rowIndex)) {
                		//获取选中的流水号
                		var serailNos = '',
                		waybillNo = grid.getStore().getAt(rowIndex).data.waybillNo,
                		list = stockchecking.selectedSerialNosMap.get(waybillNo).data.serialNoMap.getValues();
                		stockchecking.selectedSerialNosMap.each(function(key, value){
				    		if(stockchecking.selectedSerialNosMap.containsKey(waybillNo)){
				    			serailNos = stockchecking.selectedSerialNosMap.get(waybillNo).data.serialNoMap.getKeys();
				    		}
				    	});
						
                		var editStReportDetailForm = Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.id').getForm();
                		editStReportDetailForm.reset();

						if(deffientTypeLack>0&&deffientTypeExpress>0){
									Ext.Msg.alert('提示', '多货少货不能一起处理');
							}
							if(deffientTypeLack==0&&deffientTypeExpress>0){
								editStReportDetailForm.findField('differenceReason').allowBlank=true;
                				editStReportDetailForm.findField('differenceReason').setVisible(false);
							}
							if(deffientTypeLack>0&&deffientTypeExpress==0){
								editStReportDetailForm.findField('differenceReason').allowBlank=false;
                			editStReportDetailForm.findField('differenceReason').setVisible(true);
                			editStReportDetailForm.findField('differenceReason').setValue(grid.getStore().getAt(rowIndex).data.differenceReason);
							}
						
						
                		
//                	打开确认处理异常差异窗口
                		Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().show();
                		
                		
                		Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.id').setValue(grid.getStore().getAt(rowIndex).data.id);
                		Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.reportId').setValue(grid.getStore().getAt(rowIndex).data.stDifferReportId);
                		editStReportDetailForm.findField('waybillNo').setValue(grid.getStore().getAt(rowIndex).data.waybillNo);
                		editStReportDetailForm.findField('serialNo').setValue(serailNos);
                		editStReportDetailForm.findField('goodsAreaName').setValue(grid.getStore().getAt(rowIndex).data.goodsAreaName);
                		editStReportDetailForm.findField('oaErrorNo').setValue(grid.getStore().getAt(rowIndex).data.oaErrorNo);
                		editStReportDetailForm.findField('isBatchUpdate').setValue('Y');
                	} else {
		        		Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), '请选中该条记录！');
                	}
                }
            }],
            renderer:function(value, metadata, record){
            	if(record.data.handleStatus == 'DOING'){
            		if(record.data.differenceType == '少货') {
            			if(record.data.transProperty == '标准快递' 
            				|| record.data.transProperty == '3.60特惠件'
            				|| record.data.transProperty == '电商尊享'
            				|| record.data.transProperty == '商务专递'){
            				this.items[0].iconCls = '';
            			}else{
            				this.items[0].iconCls = 'deppon_icons_edit';
            			}
            		} else {
            			this.items[0].iconCls = 'deppon_icons_edit';
            		}
            	}else if(record.data.handleStatus == 'DONE'){
            		this.items[0].iconCls = '';
            	}
            }
		},*/
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.reportCode.abbreviation'), dataIndex: 'reportCode', flex: 1,width : 80}, //报告编号
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.packageNo'), dataIndex: 'packageNo', flex: 1,width : 50}, //包号
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), dataIndex: 'waybillNo', flex: 1,width : 50}, //运单号
        //{xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), dataIndex: 'serialNo', flex: 1.2,width : 30}, //流水号
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), dataIndex: 'goodsAreaName', flex: 1,width : 60}, //货区
        /*{
          xtype: 'ellipsiscolumn', 
          header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.oaerrorno'), 
          dataIndex: 'oaErrorNo', 
          flex: 1,
          width : 80,
          renderer : function(value){
			  if(value == 'N/A'){
				 return '';
			  }else{
				 return value;
			  }
			}
        },*/ //OA差错编号
        //{xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.differenceTypeDesc'), dataIndex: 'differenceTypeDesc', flex: 2,width : 40}, //差异类型
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.administrativeRegion'), dataIndex: 'administrativeRegion', flex: 1,width : 60}, //行政区
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.pieceRegion'), dataIndex: 'pieceRegion', flex: 1,width : 60}, //件区
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.goodsName'), dataIndex: 'goodsName', flex: 1,width : 40}, //货物名称
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.transProperty'), dataIndex: 'transProperty', flex: 1,width : 40}, //运输性质
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.goodsPackage'), dataIndex: 'goodsPackage', flex: 1,width : 40}, //包装
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.goodsWeight'), dataIndex: 'goodsWeight', flex: 1,width : 40}, //重量
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.goodsVolume'), dataIndex: 'goodsVolume', flex: 1,width : 40}, //体积
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.billTargetOrgName'), dataIndex: 'billTargetOrgName', flex: 1,width : 60}, //到达部门
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), dataIndex: 'operator', flex: 1, hidden:true,width : 5},  //清仓员
        {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.createtime'), dataIndex: 'reportCreateTime', flex: 1,width : 90} //报告生成时间
        //{xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleTime'), dataIndex: 'handleTime', flex: 2.5,width : 100},  //异常处理时间
        //{xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.remark'), dataIndex: 'remark', flex: 2,width : 40} //备注
	],
//	viewConfig:{
//    	getRowClass: function(record, rowIndex, rowParams, store){
//    		return record.get("handleStatus") == 'DOING' ? "row-valid" : "row-error";
//    	}
//    },
	/*
	listeners: {
	'select' : function(rowModel, record, index, eOpts ){
		var grid = this,
			plugin = grid.getPlugin('stReport_editStReportDetailGrid_detailSerialNoGrid'),
			waybillNo = record.get('waybillNo'),
			store = this.store,
			unsavedWaybill = store.findRecord('waybillNo',waybillNo,0,false,true,true),
			unsubmitedWaybill = store.findRecord('waybillNo',waybillNo,0,false,true,true);
		//取出record中的流水号list，做成map，放到第一层map的record中
		var serialNoStockList = record.get('differDetailList'),
			serialNoMap = new Ext.util.HashMap(),
			totalSerailNoMap = new Ext.util.HashMap();
		//获取DONE的明细数量
		var doneCount = 0;
		//全部流水号
		stockchecking.allSerialNos = [];
		stockchecking.selectSerailNos = [];
		for(var i in serialNoStockList){
			if(!Ext.Array.contains(stockchecking.allSerialNos,serialNoStockList[i].serialNo)) {
				stockchecking.allSerialNos.push(serialNoStockList[i].serialNo);
			}
			if(!Ext.Array.contains(stockchecking.selectSerailNos,serialNoStockList[i].serialNo)) {
				stockchecking.selectSerailNos.push(serialNoStockList[i].serialNo);
			}
		}
		for(var i in serialNoStockList){
			var serialNo = serialNoStockList[i],
				serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.stReport.editStReportDetail.reportDetail.model');
			
			totalSerailNoMap.replace(serialNo.serialNo,serialNoRecord);
			if(serialNo.handleStatus == 'DOING') {
				serialNoMap.replace(serialNo.serialNo,serialNoRecord);
			}
		}
		
		for(var i in serialNoStockList){
			var serialNo = serialNoStockList[i];
			if(serialNo.differenceType == '少货') {
				deffientTypeLack++;
			}else{		
			
				deffientTypeExpress++;
			}
			
			
		}
		
		
		if(totalSerailNoMap.getCount() !== 0){
			//将运单置于已勾选运单map中
			var recCopy = record.copy();
			stockchecking.selectedSerialNosMap.add(waybillNo,recCopy);
			//stockchecking.allSerialNosMap.add(waybillNo,recCopy);
			recCopy.set('serialNoMap',serialNoMap);
			//如果二级表被展开，则勾选
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody(),
				    innerStore = item.getStore(),
				    subWaybillNo = innerStore.getAt(0).get('waybillNo');
				if(subWaybillNo === waybillNo){
					item.getSelectionModel().selectAll(true);
				}
			}
		}else{
			//反选之
			var sm = grid.getSelectionModel();
			sm.deselect(record,true);
			stockchecking.allSerialNos = [];
			stockchecking.selectSerailNos = [];
		}
		//查询相关联的差异报告明细
		stockchecking.queryRelateDetail(stockchecking.allSerialNos);
	},
	'deselect' : function( rowModel, record, index, eOpts){
		stockchecking.allSerialNos = [];
		stockchecking.selectSerailNos = [];
		 deffientTypeLack=0;//少货
 		 deffientTypeExpress=0;//多货

		var grid = this;
		var plugin = grid.getPlugin('stReport_editStReportDetailGrid_detailSerialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var waybillNo = store.getAt(0).get('waybillNo');
			if(waybillNo == record.get('waybillNo')){
				item.getSelectionModel().deselectAll(true);
			}
		}
		var waybillNo = record.get('waybillNo');
		if(stockchecking.selectedSerialNosMap.get(waybillNo) !== undefined){
			// 从map中移除和此运单对应的库存信息
			stockchecking.selectedSerialNosMap.removeAtKey(waybillNo);
		}
		record.set('serialNoMap','');
		//清空关联明细
		var relativeDetailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id');
		relativeDetailGrid.store.removeAll();
		
	}
	
	},*/
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.stReport.editStReportDetail.reportDetail.store');
		/*me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE',
			allowDeselect:true,//允许反选
			checkOnly : true,//限制只有点击checkBox后才能选中行
			showHeaderCheckbox:false
		});*/
		me.tbar = [{
			xtype:'button',
			text: stockchecking.i18n('Foss.stockchecking.button.export'),
			handler: function(){
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
            	}
            	
				var stReportId = stockchecking.tempStReportId;
				
				if(stReportId == null || stReportId == ''){
					return;
				}
				
            	Ext.Ajax.request({
            		url: stockchecking.realPath('exportReportDetailById.action'),
	    			form: Ext.fly('downloadAttachFileForm'),
	    			method : 'POST',
	    			params: {
						'stReportVO.stReportDto.id': stReportId
					},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.export.title'), result.message);
	    			}
	    		});
			}
		},
		{
			xtype:'text',
			text :stockchecking.i18n('Foss.stockchecking.panel.editStReportDetail.remind')
		},
		{
			xtype: 'container',
			//flex:0.5,
			html: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
		},
		{
			xtype : 'textfield',
			fieldLabel : stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'),  //运单号
			emptyText : '输入运单号敲击回车',
			labelWidth : 60,
			vtype : 'waybill',
			id : 'Foss.stockchecking.newStockchecking.form.searchStock.waybillNo.id',
			enableKeyEvents : true,
			listeners : {
				'keypress' : function(text,e,eOpts){
					//如果敲击回车键，则触发添加按钮事件
					if(e.getKey() == e.ENTER){
						
						var detailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.detailGrid.id');
    					var waybillNo = text.value;
    					if(waybillNo != '') {
    						Ext.Ajax.request({
    		    				url: stockchecking.realPath('queryReportDetailByWaybillNo.action'),
    		    				params: {
    								'stReportVO.stReportDto.id': stockchecking.stReportId,
    								'stReportVO.stReportDto.waybillNo': waybillNo
    							},
    		    				success: function(response){
    		    					detailGrid.store.removeAll();
    		    					result = Ext.decode(response.responseText);
    		    					detailGrid.store.loadData(result.stReportVO.stReportDetailDtoList);
    		    				},
    		    				exception : function(response) {
    		    					detailGrid.store.removeAll();
    		        				var result = Ext.decode(response.responseText);
    		        				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
    		        			}
    		    			});
    					} else {
    						detailGrid.store.removeAll();
    						var currentPage = stockchecking.reportDetailPagingBar.store.currentPage;
    	    				stockchecking.reportDetailPagingBar.store.loadPage(currentPage);
    					}
					}
				}
			}
		}
		],
				
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 400,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10',10],['20',20],['30', 30], ['40', 40], ['50', 50], ['60', 60],['70', 70], ['80', 80], ['90', 90], ['100', 100]]
			})
		});
		stockchecking.reportDetailPagingBar = me.bbar;
		
		me.callParent([cfg]);	
	}
});

Ext.define('Foss.stReport.editStReportDetail.grid.relativeDetailGrid',{
	extend:'Ext.grid.Panel',
	id:'Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id',
	columnLines: true,
	height: 400,
	title: stockchecking.i18n('Foss.stockchecking.panel.editStReportDetail.title'), //相关联的差异明细
	frame: false,
	columns:[{
		xtype:'actioncolumn',
		flex: 1,
		text: stockchecking.i18n('Foss.stockchecking.button.operator'),
		align: 'center',
		items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.markdeal'), //标记为已处理
                iconCls:'deppon_icons_edit',
                handler: function(grid, rowIndex, colIndex) {
//                	打开确认处理异常差异窗口
                	Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().show();
                	
                	var editStReportDetailForm = Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.id').getForm();
                	editStReportDetailForm.reset();
                	
                	Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.id').setValue(grid.getStore().getAt(rowIndex).data.id);
                	Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.reportId').setValue(grid.getStore().getAt(rowIndex).data.stDifferReportId);
                	editStReportDetailForm.findField('waybillNo').setValue(grid.getStore().getAt(rowIndex).data.waybillNo);
                	editStReportDetailForm.findField('serialNo').setValue(grid.getStore().getAt(rowIndex).data.serialNo);
                	editStReportDetailForm.findField('goodsAreaName').setValue(grid.getStore().getAt(rowIndex).data.goodsAreaName);
                	editStReportDetailForm.findField('oaErrorNo').setValue(grid.getStore().getAt(rowIndex).data.oaErrorNo);
                	editStReportDetailForm.findField('isBatchUpdate').setValue('N');
                	if(grid.getStore().getAt(rowIndex).data.differenceType=='LACK'){
                		editStReportDetailForm.findField('differenceReason').allowBlank=false;
                		editStReportDetailForm.findField('differenceReason').setVisible(true);
                		editStReportDetailForm.findField('differenceReason').setValue(grid.getStore().getAt(rowIndex).data.differenceReason);
            		}else{
            			editStReportDetailForm.findField('differenceReason').allowBlank=true;
                		editStReportDetailForm.findField('differenceReason').setVisible(false);
            		}
                }
            }],
            renderer:function(value, metadata, record){
            	if(record.data.handleStatus == 'DOING'){
            		if(record.data.differenceType == 'LACK') {
            			if(record.data.transProperty == '标准快递' 
            				|| record.data.transProperty == '3.60特惠件'
            				|| record.data.transProperty == '电商尊享'
            				||record.data.transProperty == '商务专递'){
            				this.items[0].iconCls = '';
            			}else{
            				this.items[0].iconCls = 'deppon_icons_edit';
            			}
            		} else {
            			this.items[0].iconCls = 'deppon_icons_edit';
            		}
            	}else if(record.data.handleStatus == 'DONE'){
            		this.items[0].iconCls = '';
            	}
            }
		},
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.reportCode.abbreviation'), dataIndex: 'reportCode', flex: 2.4}, //报告编号
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.packageNo'), dataIndex: 'packageNo', flex: 1.2}, //包号
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), dataIndex: 'waybillNo', flex: 1.2}, //运单号
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), dataIndex: 'serialNo', flex: 1.2}, //流水号
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.transProperty'), dataIndex: 'transProperty', flex: 1.2}, //运输性质
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), dataIndex: 'goodsAreaName', flex: 2}, //货区
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.administrativeRegion'), dataIndex: 'administrativeRegion', flex: 1.5}, //行政区
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.pieceRegion'), dataIndex: 'pieceRegion', flex: 1.5}, //件区
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.oaerrorno'), dataIndex: 'oaErrorNo', flex: 1}, //OA差错编号
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.differenceTypeDesc'), dataIndex: 'differenceTypeDesc', flex: 1}, //差异类型
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), dataIndex: 'operator', flex: 1}, //清仓员
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.createtime'), dataIndex: 'reportCreateTime', flex: 2.5}, //报告生成时间
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleTime'), dataIndex: 'handleTime', flex: 2.5}, //异常处理时间
	    {xtype: 'ellipsiscolumn', header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.remark'), dataIndex: 'remark', flex: 2} //备注
     ],
     constructor: function(config){
    	 var me = this,
    	 cfg = Ext.apply({}, config);
    	 me.store = Ext.create('Foss.stReport.editStReportDetail.reportDetail.store');
    	 
    	 me.callParent([cfg]);	
     }
});
/**差异原因下拉选择数据model **/
Ext.define('Foss.Stockchecking.sttaskreport.differenceReason.Model',{
	extend: 'Ext.data.Model',
	fields: [
	     {name: 'valueName', type: 'string'},
	     {name: 'valueCode', type: 'string'}
	]
});
/**差异原因下拉选择数据store **/
Ext.define('Foss.Stockchecking.sttaskreport.differenceReason.Store',{
	extend: 'Ext.data.Store',
	model: 'Foss.Stockchecking.sttaskreport.differenceReason.Model',
	autoLoad: true,
	proxy: {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods:'POST',
		url: ContextPath.TFR_STOCK + '/tfrcommon/loadDictDataComboxNoDefault.action',
		reader : {
			type : 'json',
			root : 'tfrCommonVO.baseDataDictDtoList',
			successProperty: 'success'
		}
	},
    listeners: {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'tfrCommonVO.termsCode' : 'TFR_ST_TASK_DIFFERENCEREASONS'  //DictionaryConstants.BSE_GOODSAREA_USAGE
				}
			});	
		}
	}
});

Ext.define('Foss.stReport.editStReportDetail.subconfirm.form',{
	extend: 'Ext.form.Panel',
	id:'Foss.stReport.editStReportDetail.subconfirm.form.id',
	layout: 'column',
	frame: true,
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:2
	},
	items: [
	    {xtype:'displayfield', fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), name:'waybillNo', allowBlank: true, value:'', columnWidth:2},
	    //{xtype: 'container', html: '&nbsp;', columnWidth:.1},
	    //{xtype:'displayfield', fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), name:'serialNo',value:'', allowBlank: true, columnWidth:.4},
	    {
	    	xtype: 'textareafield',
	    	fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'),
	    	name: 'serialNo',
	    	value:'',
	    	disabled: true,
	        grow: true,
	        allowBlank: true,
	        anchor: '90%',
	        height: 30,
	        columnWidth:1
	    },
	    //{xtype: 'container', html: '&nbsp;', columnWidth:.1},
	    {xtype:'displayfield', fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), name:'goodsAreaName', value:'', allowBlank: true, columnWidth:.4},
	    {xtype: 'container', html: '&nbsp;', columnWidth:.1},
	    {xtype:'displayfield', fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.oaerrorno'), name:'oaErrorNo', value:'', allowBlank: true, columnWidth:.4},
	    {xtype: 'container', html: '&nbsp;', columnWidth:.1},
	    {             
	        xtype: 'checkboxgroup',  //复选框组
	        fieldLabel : '', //复选框组的字段标签
			vertical: true, //按照columns中指定的列数来分配复选框组件
			columnWidth:2,
	        items: [{ 
						boxLabel  : stockchecking.i18n('Foss.stockchecking.sttaskreport.model.isInStock')  + '<font color="red" size="1px" style="font-weight : lighter">（注：如货物已经交接出库,请取消勾选！）</font>',
						name      : 'isInStock',
						inputValue: 'Y',
						checked   : true //选中
					}]
		},
		{
			xtype:'combo',
			fieldLabel: '差异原因 ',//stockchecking.i18n('Foss.stockchecking.sttaskreport.model.differenceReason'),
			name:'differenceReason',
			displayField: 'valueName',
			valueField:'valueCode', 
			//value:'', 
			store:Ext.create('Foss.Stockchecking.sttaskreport.differenceReason.Store'),
			editable:false,
			hidden:true,
			allowBlank: false,
			columnWidth:.6
			},
		{xtype:'displayfield',fieldLable:'批处理',name:'isBatchUpdate',value:'',allowBlank:true,hidden:true,columnWidth:.1},
	    {
	    	xtype:'textareafield', 
		    fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.remark'), 
		    rows:3, name:'remark', 
		    allowBlank:false, 
		    columnWidth:1,
		    maxLength: 200,
		    maxLengthText: '200字以内' 
	     },
	    {xtype: 'container', html: '&nbsp;', columnWidth:.3},
	    {xtype: 'textfield', name: 'id', id: 'Foss.stReport.editStReportDetail.subconfirm.form.field.id', hidden:true, hideLabel:true},
	    {xtype: 'textfield', name: 'reportId', id: 'Foss.stReport.editStReportDetail.subconfirm.form.field.reportId', hidden:true, hideLabel:true},
	    {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.confirm'), cls: 'yellow_button', columnWidth:.2, handler:function(){
	    	var thisForm = this.up('form').getForm();
	    	
	    	if(!this.up('form').getForm().isValid()){
	    		return
	    	}
	    	
	    	var id = thisForm.findField('id').getValue();
	    	var reportId = thisForm.findField('reportId').getValue();
	    	var remark = thisForm.findField('remark').getValue();
	    	var isBatchUpdate = thisForm.findField('isBatchUpdate').getValue();
	    	var waybillNo = thisForm.findField('waybillNo').getValue();
	    	var isInStock = thisForm.findField('isInStock').getValue();
	    	var differenceReason=thisForm.findField('differenceReason').getValue();
	    	if(isInStock) {
	    		isInStock = 'Y';
	    	} 
	    	else {
	    		isInStock = 'N';
	    	}
	    	if(isBatchUpdate == 'N') {
//	    	提交数据
	    		Ext.Ajax.request({
	    			url: stockchecking.realPath('updateReportDetail.action'),
	    			params: {
	    				'stReportVO.stDifferDetail.id': id,
	    				'stReportVO.stDifferDetail.stDifferReportId': reportId,
	    				'stReportVO.stDifferDetail.remark': remark,
	    				'stReportVO.stDifferDetail.isInStock':isInStock,
	    				'stReportVO.stDifferDetail.differenceReason':differenceReason
	    			},
	    			success: function(response){
	    				result = Ext.decode(response.responseText);Foss.stockchecking
	    				Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.reportdetailhandlesubmittasksuccessful'));
	    				
	    				//刷新差异明细列表
	    				var currentPage = stockchecking.reportDetailPagingBar.store.currentPage;
	    				stockchecking.reportDetailPagingBar.store.loadPage(currentPage);
	    			},
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
	    			}
	    		});
//		    	关闭窗口
		    	Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().hide();
	    	} else {
	    		//批量处理少货找到
	    		var detailList = [];
	    		var msg = '';
	    		stockchecking.selectedSerialNosMap.get(waybillNo).data.serialNoMap.each(function(key,value,length){
	    			value.data.remark = remark;
	    			value.data.isInStock = isInStock;
	    			value.data.differenceReason=differenceReason;
	    			detailList.push(value.data);
	    			msg = msg + value.data.serialNo + ',';
				});
	    		//处理流水号换行：
	    		var serialNoArray = msg.split(',');
	    		msg = '';
	    		for(var i = 0; i < serialNoArray.length; i++) {
	    			msg = msg + serialNoArray[i] + ','
	    			if((i+1)%10 == 0) {
	    				msg = msg + '<br/>';
	    			}
	    		}
	    		msg = '本次修改的流水号如下：<br/>' + msg.substr(0,msg.length-1) + '<br/>是否确认?'
	    		
	    		var data = {stReportVO:{
	    			'stDifferDetailList':detailList	
	    			}
				}; 
	    		var form = this.up('form');
	    		var secondconfirm = Ext.Msg.confirm(stockchecking.i18n('Foss.stockchecking.alert.title'),msg, 
						function(button, text) {
					if(button == 'yes') {
						form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));
						
						Ext.Ajax.request({
		    			url: stockchecking.realPath('updateBatchReportDetail.action'),
		    			jsonData: data,
		    			success: function(response){
		    				form.getEl().unmask();
		    				result = Ext.decode(response.responseText);Foss.stockchecking
		    				Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.reportdetailhandlesubmittasksuccessful'));
		    				
//		    		    	关闭窗口
		    		    	Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().hide();
		    				//刷新差异明细列表
		    				var currentPage = stockchecking.reportDetailPagingBar.store.currentPage;
		    				stockchecking.reportDetailPagingBar.store.loadPage(currentPage);
		    			},
		    			exception : function(response) {
		    				form.getEl().unmask();
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
		    		});} else {
						return;
					}
				});
	    	}
	    }},
	    {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.close'), cls: 'yellow_button', columnWidth:.2, handler:function(){
	    	Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().hide();
	    }},
	    {xtype: 'container', html: '&nbsp;', columnWidth:.3}
	],
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stReport.editStReportDetail.window.subconfirm', {
	extend:'Ext.window.Window',
	id:'Foss.stReport.editStReportDetail.window.subconfirm.id',
	title: stockchecking.i18n('Foss.stockchecking.window.editstreportdetail.title'),  //标记为已处理
	modal:true,
	align:'center',
	closeAction:'hide',
	width:600,
	height:400,
	autoScroll:false,
	border:false,
	items:[
       Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.id') == null ? Ext.create('Foss.stReport.editStReportDetail.subconfirm.form') : Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.id')
    ]
});

Ext.define('Foss.stReport.panel.editStReportDetail',{
	extend: 'Ext.panel.Panel',
	id:'Foss.stReport.panel.editStReportDetail.id',
	frame:true,
	border:false,
	layout:'column',
	items:[{
//		title:'差异明细',
		columnWidth:1,
		height:400,
		items:[
		    Ext.getCmp('Foss.stReport.editStReportDetail.grid.detailGrid.id') == null ? Ext.create('Foss.stReport.editStReportDetail.grid.detailGrid') : Ext.getCmp('Foss.stReport.editStReportDetail.grid.detailGrid.id')
		]
	},{
//		title:'相关联的差异明细',
		columnWidth:1,
		height:300,
		items:[
		    Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id') == null ? Ext.create('Foss.stReport.editStReportDetail.grid.relativeDetailGrid') : Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id')
		]
	}],
	subConfirmWindow:null,	//标记已处理窗口
	getSubConfirmWindow:function(){
		if(this.subConfirmWindow == null){
			this.subConfirmWindow = Ext.create('Foss.stReport.editStReportDetail.window.subconfirm');
		}
		
		return this.subConfirmWindow;
	}
});

Ext.define('Foss.stReport.window.editStReportDetail', {
	extend:'Ext.window.Window',
	title: stockchecking.i18n('Foss.stockchecking.window.editstreportdetail.title'), //清仓差异报告明细处理
	modal:true,
	align:'center',
	closeAction:'hide',
	width:1000,
	height:700,
	autoScroll:true,
	border:false,
	items:[
       Ext.getCmp('Foss.stReport.panel.editStReportDetail.id') == null ? Ext.create('Foss.stReport.panel.editStReportDetail') : Ext.getCmp('Foss.stReport.panel.editStReportDetail.id')
    ],
    listeners: {
    	beforeclose: function(panel, eOpts){
			
				 deffientTypeLack=0;//少货
				 deffientTypeExpress=0;//多货
			
    		stockchecking.reportPagingBar.moveFirst();
    	},
    	show : function(){
			deffientTypeLack=0;//少货
			deffientTypeExpress=0;//多货
    		stockchecking.reportDetailPagingBar.moveFirst();
    	}
    }
});

//定义查询运单中流水号列表grid
Ext.define('Foss.stReport.editStReportDetail.grid.detailSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 800,
	animCollapse : true,
	viewConfig : {
		getRowClass: function(record, rowIndex, rowParams, store) {
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
		}
	},
	columns:[
	        /* {
		xtype:'actioncolumn',
		text: stockchecking.i18n('Foss.stockchecking.button.operator'),
		align: 'center',
		flex: 1,
		
		items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.markdeal'),  //标记已处理
                iconCls:'deppon_icons_edit',
                handler: function(grid, rowIndex, colIndex) {
                	if(grid.getSelectionModel().isSelected(rowIndex)) {
                		var waybillNo = grid.getStore().getAt(rowIndex).data.waybillNo;
                		var superRecord = grid.up('grid').superGrid.store.findRecord('waybillNo',waybillNo);
                		var goodsAreaName = superRecord.data.goodsAreaName;
//                    	打开确认处理异常差异窗口
                    		Ext.getCmp('Foss.stReport.panel.editStReportDetail.id').getSubConfirmWindow().show();
                    		
                    		var editStReportDetailForm = Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.id').getForm();
                    		editStReportDetailForm.reset();
                    		Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.id').setValue(grid.getStore().getAt(rowIndex).data.id);
                    		Ext.getCmp('Foss.stReport.editStReportDetail.subconfirm.form.field.reportId').setValue(grid.getStore().getAt(rowIndex).data.stDifferReportId);
                    		editStReportDetailForm.findField('waybillNo').setValue(waybillNo);
                    		editStReportDetailForm.findField('serialNo').setValue(grid.getStore().getAt(rowIndex).data.serialNo);
                    		editStReportDetailForm.findField('goodsAreaName').setValue(goodsAreaName);
                    		editStReportDetailForm.findField('oaErrorNo').setValue(grid.getStore().getAt(rowIndex).data.oaErrorNo);
                    		editStReportDetailForm.findField('isBatchUpdate').setValue('N');
                    		if(grid.getStore().getAt(rowIndex).data.differenceType=='少货'){
	                    		editStReportDetailForm.findField('differenceReason').allowBlank=false;
	                    		editStReportDetailForm.findField('differenceReason').setVisible(true);
	                    		editStReportDetailForm.findField('differenceReason').setValue(grid.getStore().getAt(rowIndex).data.differenceReason);
                    		}else{
                    			editStReportDetailForm.findField('differenceReason').allowBlank=true;
	                    		editStReportDetailForm.findField('differenceReason').setVisible(false);
                    		}
                   		    
                    	} else {
    		        		Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), '请选中该条记录！');
                    	}
                	}
            }],
            renderer:function(value, metadata, record){
            	if(record.data.handleStatus == 'DOING'){
            		if(record.data.differenceType == '少货') {
            			if(record.data.transProperty == '标准快递' 
            				|| record.data.transProperty == '3.60特惠件'
            					|| record.data.transProperty == '电商尊享'){
            				this.items[0].iconCls = '';
            			}else{
            				this.items[0].iconCls = 'deppon_icons_edit';
            			}
            		} else {
            			this.items[0].iconCls = 'deppon_icons_edit';
            		}
            	}else if(record.data.handleStatus == 'DONE'){
            		this.items[0].iconCls = '';
            	}
            }
		},*/
		 {
			xtype: 'ellipsiscolumn', 
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), 
			dataIndex: 'serialNo',
			flex: 1.2,
			width : 30
		 }, //流水号 
		 {
	          xtype: 'ellipsiscolumn', 
	          header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.oaerrorno'), 
	          dataIndex: 'oaErrorNo', 
	          flex: 2,
	          width : 100,
	          renderer : function(value){
				  if(value == 'N/A'){
					 return '';
				  }else{
					 return value;
				  }
				}
	        }, //OA差错编号
	        {
	        	xtype: 'ellipsiscolumn', 
	        	header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.differenceTypeDesc'), 
	        	dataIndex: 'differenceType', 
	        	flex: 2,
	        	width : 40
	        }, //差异类型
	        {
	        	xtype: 'ellipsiscolumn', 
	        	header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.operateTime'), 
	        	dataIndex: 'operateTime', 
	        	flex: 2.5,
	        	width : 100
	        }
	        , //差异原因
	        {
	        	xtype: 'ellipsiscolumn', 
	        	header: '差异原因', 
	        	dataIndex: 'differenceReason', 
	        	flex: 2.5,
	        	width : 100
	        },//操作时间
	        {
	        	xtype: 'ellipsiscolumn', 
	        	header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleTime'), 
	        	dataIndex: 'handleTime', 
	        	flex: 2.5,
	        	width : 100
	        },  //异常处理时间
	        {
	        	xtype: 'ellipsiscolumn', 
	        	header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.remark'), 
	        	dataIndex: 'remark', 
	        	flex: 2,
	        	width : 40
	        } //备注
		],
	bindData : function(record){
		var serialNoMap = new Ext.util.HashMap();
		var waybillNo = record.get('waybillNo'),
			grid = this,
			store = grid.store;
		store.loadData(record.get('differDetailList'));
		//如果之前被勾选，则勾选上之前选中的流水号
		var rec = stockchecking.selectedSerialNosMap.get(waybillNo);
		if(rec !== undefined){
			var selectedSerialNoMap = rec.get('serialNoMap'),
				selectedSerialNo = [];
			store.each(function(rec){
				var serialNo = rec.get('serialNo');
				if(selectedSerialNoMap.get(serialNo) !== undefined){
					selectedSerialNo.push(rec);
				}
			});
			var sm = grid.getSelectionModel();
			sm.select(selectedSerialNo,true);
		}
	},
	/*
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid;
			var serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo');
			if(record.get('differenceType')=='少货'){
				 deffientTypeLack++;//少货
			}else{
				 deffientTypeExpress++;//多货
			}

			//选中的流水号
			if(!Ext.Array.contains(stockchecking.selectSerailNos,serialNo)) {
				stockchecking.selectSerailNos.push(serialNo);
			}
			
			var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);//获取一级表格中该运单号对应的行记录
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//勾选第一层表格中的行：第二个参数为true，为保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
			//勾选后，将运单放入map中
			if(stockchecking.selectedSerialNosMap.get(waybillNo) !== undefined){
				superRecord = stockchecking.selectedSerialNosMap.get(waybillNo);
			}else{
				stockchecking.selectedSerialNosMap.add(waybillNo,superRecord);
			}
			
			//获取运单record中的勾选的流水号map
			var selectedSerialNoMap = superRecord.get('serialNoMap');
			if(selectedSerialNoMap !== '' && selectedSerialNoMap !== undefined){
				 if(record.data.handleStatus == 'DOING') {
					 selectedSerialNoMap.add(serialNo,record);
				 } 
			}else{
				if(record.data.handleStatus == 'DOING') {
					selectedSerialNoMap = new Ext.util.HashMap();
					selectedSerialNoMap.add(serialNo,record);
				} 
			}
			superRecord.set('serialNoMap',selectedSerialNoMap);
			//查询关联明细
			stockchecking.queryRelateDetail(stockchecking.selectSerailNos);
		},
		'deselect' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid,
				grid = this,
				selectedList =grid.getSelectionModel().selected,
				serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo');
			if(record.get('differenceType')=='少货'){
				 deffientTypeLack--;//少货
			}else{
				 deffientTypeExpress--;//多货
			}
			Ext.Array.remove(stockchecking.selectSerailNos,serialNo);
			
			if(selectedList.length == 0){//如果第二层表格记录全部被反选，则直接将第一层表格反选，并删除第三层map中的流水号记录
				var superGrid = this.superGrid;
				var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord,true);//反选第一层表格中的行：第二个参数为true，表示反选后不触发deselect事件
				superRecord.set('serialNoMap','');
				stockchecking.selectedSerialNosMap.removeAtKey(waybillNo);
				//清空关联差异明细
				var relativeDetailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.relativeDetailGrid.id');
				relativeDetailGrid.store.removeAll();
			}else{//如果第二层表格记录未全部反选，则从map中的选中行中删除该流水号对应的记录
				var selectedWaybill = stockchecking.selectedSerialNosMap.get(waybillNo);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			
			//查询关联明细
			stockchecking.queryRelateDetail(stockchecking.selectSerailNos);
		}
	},*/
	constructor : function(config) {
		
		 var me = this,
    	 cfg = Ext.apply({}, config);
    	 me.store = Ext.create('Foss.stReport.editStReportDetail.reportDetail.store');
    	 /*me.selModel = Ext.create('Ext.selection.CheckboxModel',{
    		 showHeaderCheckbox : false,
    		 mode : 'SIMPLE',
			checkOnly : true,//限制只有点击checkBox后才能选中行
			listeners : {
				'beforeselect' : function(rowModel, record, index, eOpts){
					//如果不可选，则返回false
					if(me.viewConfig.getRowClass(record) === 'disabledrow'){
						return false;
					}
				}
			}
    	 });*/
    	 
    	 me.callParent([cfg]);	
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();

	var stReportQueryForm = Ext.create('Foss.stReport.QueryForm');
	stockchecking.stReportQueryForm = stReportQueryForm;
	stockchecking.tempStReportId = ""; //临时存放清仓差异报告的ID
	var stReportQueryGrid = Ext.create('Foss.stReport.stReportQueryGrid');
	
	var detailGrid = Ext.getCmp('Foss.stReport.editStReportDetail.grid.detailGrid.id');
	//用于按照单号查询
	stockchecking.detailGridStore = detailGrid.store;
	
	Ext.create('Ext.panel.Panel',{
		id:'T_stockchecking-streportindex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [stReportQueryForm, stReportQueryGrid],
		editStReportDetailWindow: null,	//手工建立清仓任务 窗口
		getEditStReportDetailWindow:function(){
			if(this.editStReportDetailWindow == null){
				this.editStReportDetailWindow = Ext.create('Foss.stReport.window.editStReportDetail');
			}
			
			return this.editStReportDetailWindow;
		},
		renderTo: 'T_stockchecking-streportindex-body'
	});
	if(stockchecking.transferCenter=='Y'){
		goodsCountInfo();
	}else{
		Ext.getCmp('goodsCountInfo').hide();
	}
	
});