/**
 * 定义常量
 */
pay.queryPdaPayInReport.DATELIMIT_MAX = 30;//日期差距
pay.queryPdaPayInReport.pageSize = 20;//分页参数
pay.queryPdaPayInReport.maximumSize = 100;//最大分页条数限制

/**
 * 查询pda报表数据
 */
pay.queryPdaPayInReport.queryPDAPayInPort = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//校验日期是否正确
		var reportEndDate = form.findField('reportEndDate').getValue();
		var reportBeginDate = form.findField('reportBeginDate').getValue();
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(reportBeginDate,reportEndDate);
		//校验起始日期和结束日期
		if(compareTwoDate>pay.queryPdaPayInReport.DATELIMIT_MAX ){
			Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//设置查询条件
		pay.queryPdaPayInReport.reportBeginDate = form.findField('reportBeginDate').getValue();
		pay.queryPdaPayInReport.reportEndDate = form.findField('reportEndDate').getValue();	
		pay.queryPdaPayInReport.driverCode  = form.findField('driverCode').getValue();		
		//pay.queryPdaPayInReport.vehicleNo  = form.findField('vehicleNo').getValue();		
		pay.queryPdaPayInReport.reportNo  = form.findField('reportNo').getValue();
		pay.queryPdaPayInReport.rptType = form.findField('rptType').getValue();
		var grid = Ext.getCmp('T_pay-queryPdaPayInReport_content').getGrid();
		//获取遮罩
		var loadMask = Ext.getCmp('T_pay-queryPdaPayInReport_content').getLoadMask();
		//显示遮罩
		loadMask.show();
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				//隐藏遮罩
				loadMask.hide();
				var rawData = grid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
				if(!success && ! rawData.isException){
					Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),rawData.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.vo.dto.rptList) ||result.vo.dto.rptList.length==0){
						Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.common.noResult'));
						return false;	
					}
					//显示表格
					grid.show();
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 查看明细
 */
pay.queryPdaPayInReport.viewDetail = function(me,record){
	//获取报表编号
	var reportNo = record.get('reportNo');
	var businessDate = record.get('businessDate');
	//获取弹出窗口
	var win = me.detailWin;
	//获取接货列表
	var receiveGrid = win.getDetailReceivedGrid();
	//获取送货列表
	var deliverGrid = win.getDetailDeliverGrid();
	//获取总计panel
	var totalForm = win.getParentForm();
	
	//重置明细信息
	receiveGrid.store.removeAll();
	deliverGrid.store.removeAll();
	//反写接货总金额
	receiveGrid.down('toolbar').items.items[1].setValue(0);
	//反写接货总票数
	receiveGrid.down('toolbar').items.items[2].setValue(0);
	//反写接货签收单数
	receiveGrid.down('toolbar').items.items[3].setValue(0);
	
	//反写送货总金额
	deliverGrid.down('toolbar').items.items[1].setValue(0);
	//反写送货总票数
	deliverGrid.down('toolbar').items.items[2].setValue(0);
	//反写送货反单数
	deliverGrid.down('toolbar').items.items[3].setValue(0);
	totalForm.getForm().reset();
	
	
	//判断报表编号是否为空
	if(Ext.isEmpty(reportNo)){
		Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.reportIsNullWarning'));
		return false;
	}
	//发送请求
	Ext.Ajax.request({
		url:pay.realPath('queryReportDetailByDate.action'),
		actionMethods:'post',
		params:{
			'vo.dto.reportNo':reportNo,
			'vo.dto.reportBeginDate':businessDate
		},
		success:function(response){
			var result = Ext.decode(response.responseText);
			receiveGrid.store.loadData(result.vo.dto.receivedList);
			deliverGrid.store.loadData(result.vo.dto.deliverList);
			if(!Ext.isEmpty(result.vo.dto.receivedList) && result.vo.dto.receivedList.length>0){
				//反写接货总金额
				receiveGrid.down('toolbar').items.items[1].setValue(result.vo.dto.receivedTotalAmount);
				//反写接货总票数
				receiveGrid.down('toolbar').items.items[2].setValue(result.vo.dto.receivedTotalCount);
				//反写接货签收单数
				receiveGrid.down('toolbar').items.items[3].setValue(result.vo.dto.signwaybillTotal);
			}else{
				receiveGrid.hide();
			}
			
			//获取送货信息
			if(!Ext.isEmpty(result.vo.dto.deliverList)&& result.vo.dto.deliverList.length>0){
				//反写送货总金额
				deliverGrid.down('toolbar').items.items[1].setValue(result.vo.dto.deliverTotalAmount);
				//反写送货总票数
				deliverGrid.down('toolbar').items.items[2].setValue(result.vo.dto.deliverTotalCount);
				//反写送货反单数
				deliverGrid.down('toolbar').items.items[3].setValue(result.vo.dto.returnTicketTotal);
			}else{
				deliverGrid.hide();
			}
			
			totalForm.loadRecord(record);
			me.detailWin.show();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//弹出异常提示
			Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),result.message);
		}
	});
}

/**
 * 打印
 */
pay.queryPdaPayInReport.print = function(){
	//获取表格
	var grid = Ext.getCmp('T_pay-queryPdaPayInReport_content').getGrid();
	//查询选择行
	var selections = grid.getSelectionModel().getSelection();
	//判断是否选择
	if(selections.length==0){
		Ext.Msg.alert(pay.queryPdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.unSelectedToPrintyWarning'));
		return false;
	}
	//报表编号
	var reportNoStr ="";
	//循环获取要打印的表格数据
	for(var i=0;i<selections.length;i++){
		if(i!=selections.length-1){
			reportNoStr = reportNoStr+selections[i].get('reportNo')+",";
		}else{
			reportNoStr = reportNoStr+selections[i].get('reportNo');
		}
	}
	//打印
	do_printpreview('pdapayinreport',{reportNos:reportNoStr},ContextPath.STL_WEB);
	
}

/**
 * 表格模型
 */
Ext.define('Foss.queryPdaPayInReport.GridModel',{
	extend:'Ext.data.Model',
	fields:[{
		//报表编号
		name:'reportNo'
	},{
		//司机姓名
		name:'driverName'
	},{
		//司机工号
		name:'driverCode'
	},{
		//总票数
		name:'waybillQtyTotal',
		type:'int'
	},{
		//总体积
		name:'volumeTotal',
		type:'double'
	},{
		//总件数
		name:'piecesTotal',
		type:'int'
	},{
		//总重量
		name:'weightTotal',
		type:'double'
	},{
		//现金总额
		name:'receiveAmountTotal',
		type:'double'
	},{
		//实收总额
		name:'receivedAmountTotal',
		type:'double'
	},{
		//办理人
		name:'createUserName'
	},{
		//办理时间
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//交接异常备注
		name:'notes'
	},{
		//签收单总数
		name:'signwaybillTotal',
		type:'int'
	},{
		//返单总数
		name:'returnTicketTotal',
		type:'int'
	},{
		//报表类型
		name:'rptType'
	}]
});

/**
 * 表格store
 */
Ext.define('Foss.queryPdaPayInReport.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.queryPdaPayInReport.GridModel',
	proxy:{
		type:'ajax',
		url:pay.realPath('queryReceiveReportBillByDate.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.dto.rptList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			//声明查询条件
			var searchParams = {
				'vo.dto.reportBeginDate':pay.queryPdaPayInReport.reportBeginDate,	
				'vo.dto.reportEndDate':pay.queryPdaPayInReport.reportEndDate,	
				'vo.dto.driverCode':pay.queryPdaPayInReport.driverCode,	
				//'vo.dto.vehicleNo':pay.queryPdaPayInReport.vehicleNo,	
				'vo.dto.reportNo':pay.queryPdaPayInReport.reportNo,
				'vo.dto.rptType':pay.queryPdaPayInReport.rptType
			};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

/**
 * 司机收款报表表格model
 */
Ext.define('Foss.queryPdaPayInReport.DetailModel',{
	extend:'Ext.data.Model',
	fields:[{
		//类型
		name:'type'
	},{
		//运单号
		name:'waybillNo'
	},{
		//重量
		name:'weight',
		type:'double'
	},{
		//体积
		name:'volume',
		type:'double'
	},{
		//件数
		name:'qty',
		type:'int'
	},{
		//金额
		name:'amount',
		type:'double'
	},{
		//是否有签收单
		name:'isSignwaybill'
	},{
		//是否有签收单
		name:'vehicleNo'
	},{
		//是否有返单
		name:'isReturnTicket'
	},{
		//创建时间
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'id'
	}]
});

/**
 * 接货列表store
 */
Ext.define('Foss.queryPdaPayInReport.DetailReceiveGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.queryPdaPayInReport.DetailModel'
});

/**
 * 送货列表store
 */
Ext.define('Foss.queryPdaPayInReport.DetailDeliverGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.queryPdaPayInReport.DetailModel'
});


/**
 * 接货表格
 */
Ext.define('Foss.queryPdaPayInReport.DetailReceiveGrid',{
	extend:'Ext.grid.Panel',
    title:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.receiveEntry'),
    height:220,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.queryPdaPayInReport.DetailReceiveGridStore'),
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	defaults:{
  		align:'center'
  	},
  	columns:[{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.amount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.isSignwaybill'),
		dataIndex:'isSignwaybill',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
		},
		flex:1
	}],
    initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.04,
				labelWidth:40,
				labelSeparator:'',
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				columnWidth:.25,
				labelWidth:85,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.cashAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.2,
				labelWidth:75,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.2,
				labelWidth:80,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.singCount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 送货表格
 */
Ext.define('Foss.queryPdaPayInReport.DetailDeliverGrid',{
	extend:'Ext.grid.Panel',
    height:220,
    title:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.deliverEntry'),
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.queryPdaPayInReport.DetailDeliverGridStore'),
  	defaults:{
  		align:'center'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns:[{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.arriveAmount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.isReturnTicket'),
		dataIndex:'isReturnTicket',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
		},
		flex:1
	}],
  	 initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.04,
				labelWidth:40,
				labelSeparator:'',
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				columnWidth:.25,
				labelWidth:85,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.arriveAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.2,
				labelWidth:75,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.2,
				labelWidth:80,
				fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.returnTicketCount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 明细区域
 */
Ext.define('Foss.queryPdaPayInReport.DetailPanel',{
	extend:'Ext.panel.Panel',
	//frame:true,
	items:[
		Ext.create('Foss.queryPdaPayInReport.DetailReceiveGrid'),
		Ext.create('Foss.queryPdaPayInReport.DetailDeliverGrid')
	]
});

/**
 * 新增页面查询form
 */
Ext.define('Foss.queryPdaPayInReport.TotalFormForAdd',{
	extend:'Ext.form.Panel',
	title:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalAll'),
	frame:true,
	layout:'column',
	defaultType:'textfield',
	defaults:{
		labelWidth:75,
		readOnly:true
	},
	items:[{
		xtype:'numberfield',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCount'),
		name:'waybillQtyTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalPieces'),
		name:'piecesTotal',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalQty'),
		xtype:'numberfield',
		name:'volumeTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalWeight'),
		xtype:'numberfield',
		name:'weightTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.returnTicketCount'),
		name:'returnTicketTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalSignwaybill'),
		xtype:'numberfield',
		name:'signwaybillTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCashAmount'),
		xtype:'numberfield',
		name:'receiveAmountTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.createUserName'),
		name:'createUserName',
		columnWidth:.3
	},{
		xtype:'datefield',
		name:'businessDate',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.createTime'),
		columnWidth:.3,
		format:'Y-m-d H:i:s'
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.receivedAmountTotal'),
		name:'receivedAmountTotal',
		xtype:'numberfield',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.reportNo'),
		name:'reportNo',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.driverCode'),
		name:'driverCode',
		columnWidth:.3
	},{
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.driverName'),
		name:'driverName',
		columnWidth:.3
	},{
		xtype:'textarea',
		name:'notes',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.notes'),
		columnWidth:.9
	}]
});

/**
 * 明细信息
 */
Ext.define('Foss.queryPdaPayInReport.DetailWindow',{
	extend:'Ext.window.Window',
	getParentForm:function(){
		return this.items.items[0];
	},
	getDetailReceivedGrid:function(){
		return this.items.items[1].items.items[0];
	},
	getDetailDeliverGrid:function(){
		return this.items.items[1].items.items[1];
	},
	width:800,
	modal:true,
	constrainHeader: true,
	closeAction:'hidden',
	items:[
		Ext.create('Foss.queryPdaPayInReport.TotalFormForAdd'),
		Ext.create('Foss.queryPdaPayInReport.DetailPanel')
	]
});


/**
 * 新增页面查询form
 */
Ext.define('Foss.queryPdaPayInReport.QueryForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.report'),
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{
		xtype:'textfield',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.reportNo'),
		name:'reportNo',
		columnWidth:.3
	},{
		xtype:'commonowndriverselector',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.driverCode'),
		name:'driverCode',
		columnWidth:.3
	},{
		xtype:'combobox',
		fieldLabel:'类型',
		name:'rptType',
		editable : false,
		columnWidth:.3,
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.DRIVER_COLLECTION_RPT_D__TYPE,null, {
			'valueCode' : '',
			'valueName' : '全部'
		}),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		value:''
	},{
		xtype:'datefield',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.startDate'),
		name:'reportBeginDate',
		columnWidth:.3,
		format:'Y-m-d',
		allowBlank:false,
		value:stl.getTargetDate(new Date(),-stl.DATE_THREE_DAYS_WEEK)
	},{
		xtype:'datefield',
		name:'reportEndDate',
		fieldLabel:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.endDate'),
		allowBlank:false,
		columnWidth:.3,
		format:'Y-m-d',
		value:new Date()
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.7
	},{
		xtype:'button',
		text:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.query'),
		columnWidth:.1,
		handler:pay.queryPdaPayInReport.queryPDAPayInPort
	},{
		xtype:'button',
		text:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.print'),
		columnWidth:.1,
		handler:pay.queryPdaPayInReport.print
	}]
});

/**
 * 接货表格
 */
Ext.define('Foss.queryPdaPayInReport.Grid',{
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.queryPdaPayInReport.i18n('foss.stl.pay.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.queryPdaPayInReport.GridStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
  		xtype:'rownumberer',
  		width:20
  	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.reportNo'),
		dataIndex:'reportNo'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.driverName'),
		dataIndex:'driverName'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCount'),
		dataIndex:'waybillQtyTotal',
		width:80
	},{
		header:'总体积(立方米)',
		width:120,
		dataIndex:'volumeTotal'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.totalCashAmount'),
		dataIndex:'receiveAmountTotal'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.receivedAmountTotal'),
		dataIndex:'receivedAmountTotal'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.createUserName'),
		dataIndex:'createUserName'
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.createTime'),
		dataIndex:'businessDate',
		width:80,
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.rptType'),
		dataIndex:'rptType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
			return displayField;
		}
	},{
		header:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.exceptionDesc'),
		dataIndex:'notes'
	}],
	listeners:{
		'itemdblclick':function(th,record){
			var me = this;
			pay.queryPdaPayInReport.viewDetail(me,record);
		}
	},
    initComponent:function(){
		var me = this;
		//创建明细窗口
		if(Ext.isEmpty(me.detailWin)){
			me.detailWin = Ext.create('Foss.queryPdaPayInReport.DetailWindow');
		}
		//定义分页
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:1,
				pageSize:pay.queryPdaPayInReport.pageSize,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: pay.queryPdaPayInReport.maximumSize,
					sizeList: [['20', 20], ['30', 30], ['50', 50], ['100', 100]]
				})
			 }]
   		 }];
   		 me.callParent();
    }
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询表单
	var queryForm = Ext.create('Foss.queryPdaPayInReport.QueryForm');
	//查询结果列表
	var grid = Ext.create('Foss.queryPdaPayInReport.Grid',{hidden:true});

	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-queryPdaPayInReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		loadMask:null,//遮罩
		getLoadMask:function(){
			var me = this;
			//获取遮罩效果
			if(Ext.isEmpty(me.loadMask)){
				me.loadMask = new Ext.LoadMask(me, {
					msg:pay.queryPdaPayInReport.i18n('foss.stl.pay.queryPdaPayInReport.saveMask'),
				    removeMask : true// 完成后移除
				});
			}
			return me.loadMask;
		},
		getGrid:function(){
			return grid;
		},
		getForm:function(){
			return queryForm;
		},
		items: [queryForm,grid],
		renderTo: 'T_pay-queryPdaPayInReport-body'
	});
});