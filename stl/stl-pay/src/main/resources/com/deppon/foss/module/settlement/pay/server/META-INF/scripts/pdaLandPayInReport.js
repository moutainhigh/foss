/**
 * 刷新界面
 */
pay.pdaLandPayInReport.refreshPage = function(){
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	var deliverGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaLandPayInReport_content').getTotalFormForAdd();
	
	//反写接货总金额
	receiveGrid.down('toolbar').items.items[1].setValue(0);
	//反写接货总票数
	receiveGrid.down('toolbar').items.items[2].setValue(0);
	//反写接货签收单数
	receiveGrid.down('toolbar').items.items[3].setValue(0);
	//反写接货签收单数
	receiveGrid.down('toolbar').items.items[4].setValue(0);
	//反写接货签收单数
	receiveGrid.down('toolbar').items.items[5].setValue(0);
	
	//反写送货总金额
	deliverGrid.down('toolbar').items.items[1].setValue(0);
	//反写送货总票数
	deliverGrid.down('toolbar').items.items[2].setValue(0);
	//反写送货反单数
	deliverGrid.down('toolbar').items.items[3].setValue(0);
	deliverGrid.down('toolbar').items.items[4].setValue(0);
	deliverGrid.down('toolbar').items.items[5].setValue(0);
	
	receiveGrid.store.removeAll();
	deliverGrid.store.removeAll();
	totalForm.getForm().reset();
}


/**
 * 查询
 */
pay.pdaLandPayInReport.query = function(){
	//获取form表单
	var form = this.up('form').getForm();
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	var deliverGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaLandPayInReport_content').getTotalFormForAdd();
	//获取起始和结束时间
	var startTime_str = form.findField('startTime').getValue();
	var endTime_str= form.findField('endTime').getValue();
	var startTime = new Date(startTime_str.replace(/-/g,"/")).getTime();
	var endTime = new Date(endTime_str.replace(/-/g,"/")).getTime();
	
	//开始时间不能晚于结束时间
	if(startTime>endTime){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'), pay.pdaLandPayInReport.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
		return false;
	}else if(stl.compareTwoDate(startTime,endTime) > 3){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'), pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.threeDaysWaring'));
		return false;
	}
	
	//设置查询条件
	pay.pdaLandPayInReport.driverCode = form.findField('driverCode').getValue();
	pay.pdaLandPayInReport.driverName = form.findField('driverCode').getRawValue();
	pay.pdaLandPayInReport.reportBeginDate = startTime_str;
	pay.pdaLandPayInReport.reportEndDate = endTime_str;
	
	//刷新界面
	pay.pdaLandPayInReport.refreshPage();
	
	//判断界面输入条件是否都合法
	if(form.isValid()){
		//获取当前页面
		var loadMask = Ext.getCmp('T_pay-pdaLandPayInReport_content').getLoadMask();
		//获取遮罩,进行遮罩
		loadMask.show();
		//发送请求
		Ext.Ajax.request({
			url:pay.realPath('queryReceiptSendGoodsInfo.action'),
			actionMethods:'post',
			params:{
				'vo.dto.driverCode':pay.pdaLandPayInReport.driverCode,
				'vo.dto.driverName':pay.pdaLandPayInReport.driverName,
				'vo.dto.reportBeginDate':pay.pdaLandPayInReport.reportBeginDate,
				'vo.dto.reportEndDate':pay.pdaLandPayInReport.reportEndDate
			},
			success:function(response){
				//遮罩窗口
				loadMask.hide();
				//获取返回结果
				var result = Ext.decode(response.responseText);	
				//如果dto为空，则提示
				if(Ext.isEmpty(result.vo.dto)){
					Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.common.noResult'));
					pay.pdaLandPayInReport.refreshPage();
					return false;
				}
				var receivedList = result.vo.dto.receivedList;
				var deliverList = result.vo.dto.deliverList;
				var entity = result.vo.dto.entity;
				
				//如果接货和送货都为空，则提示
				if(Ext.isEmpty(receivedList) && Ext.isEmpty(deliverList)){
					Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.common.noResult'));
					pay.pdaLandPayInReport.refreshPage();
					return false;
				}
				//获取接货信息
				if(!Ext.isEmpty(receivedList)){
					//加载数据到表格中
					receiveGrid.store.loadData(receivedList);
					//反写接货总金额
					receiveGrid.down('toolbar').items.items[1].setValue(result.vo.dto.receivedTotalAmount);
					//反写接货总票数
					receiveGrid.down('toolbar').items.items[2].setValue(result.vo.dto.receivedTotalCount);
					//反写接货签收单数
					receiveGrid.down('toolbar').items.items[3].setValue(result.vo.dto.signwaybillTotal);
					//反写接货签收单数
					receiveGrid.down('toolbar').items.items[4].setValue(result.vo.dto.receiveCardIncome);
					//反写接货签收单数
					receiveGrid.down('toolbar').items.items[5].setValue(result.vo.dto.receiveCashIncome);
				}
				//获取送货信息
				if(!Ext.isEmpty(deliverList)){
					//加载数据到表格中
					deliverGrid.store.loadData(deliverList);
					//反写送货总金额
					deliverGrid.down('toolbar').items.items[1].setValue(result.vo.dto.deliverTotalAmount);
					//反写送货总票数
					deliverGrid.down('toolbar').items.items[2].setValue(result.vo.dto.deliverTotalCount);
					//反写送货反单数
					deliverGrid.down('toolbar').items.items[3].setValue(result.vo.dto.returnTicketTotal);
					//反写接货签收单数
					deliverGrid.down('toolbar').items.items[4].setValue(result.vo.dto.deliverCardIncome);
					//反写接货签收单数
					deliverGrid.down('toolbar').items.items[5].setValue(result.vo.dto.deliverCashIncome);
				}
				//声明总计实体
				var formModel = new Foss.pdaLandPayInReport.TotalFormModel(entity);
				//加载数据
				totalForm.loadRecord(formModel);
				//显示隐藏列表		
				var detailPanel = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailPanel();
				detailPanel.show();
				totalForm.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				//隐藏掉遮罩
				loadMask.hide();
				//弹出异常提示
				Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),result.message);
			},
			unknownException:function(form,action){
				//隐藏掉遮罩
				loadMask.hide();
			},
			failure:function(form,action){
				//隐藏掉遮罩
				loadMask.hide();
			}
		});
	}else{
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.noDriverWarning'));
		return false;
	}
}

/**
 * 新增pda报表
 */
/*pay.pdaLandPayInReport.addReceiveReportBill = function(){
	//声明要用变量
	var vo,dto,receiveGrid,deliverGrid,
		totalForm,receivedList,deliverList,entity;
	//获取接货列表
	receiveGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	deliverGrid = Ext.getCmp('T_pay-pdaLandPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaLandPayInReport_content').getTotalFormForAdd();
	//循环遍历接货列表，获取grid数据
	receivedList = new Array();
	//循环获取数据
	receiveGrid.store.each(function(record){
		receivedList.push(record.data);
	});
	//循环获取送货列表
	deliverList = new Array();
	//循环获取数据
	deliverGrid.store.each(function(record){
		deliverList.push(record.data);
	});
	//初始化form表单实体
	entity = totalForm.getRecord();
	totalForm.getForm().updateRecord(entity);
	//初始化vo	
	vo = new Object();
	//初始化dto	
	dto = new Object();
	//建立vo和dto关系
	vo.dto = dto;
	//给dto属性赋值
	dto.receivedList = receivedList;
	dto.deliverList = deliverList;
	dto.entity = entity.data;
	
	//获取当前页面
	var loadMask = Ext.getCmp('T_pay-pdaLandPayInReport_content').getLoadMask();
	//获取遮罩,进行遮罩
	loadMask.show();
	
	//调用后台方法继续保存操作
	Ext.Ajax.request({
		url:pay.realPath('addReceiveReportBill.action'),
		actionMethods:'POST',
		jsonData:{
			'vo':vo
		},
		success:function(response){
			//获取返回结果
			var result = Ext.decode(response.responseText);		
			//遮罩窗口
			
			loadMask.hide();
			//刷新界面
			pay.pdaLandPayInReport.refreshPage();
			
			//获得打印编号
			totalForm.getForm().findField('reportNo').setValue(result.vo.dto.entity.reportNo);
			
			Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'), pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.saveSuccess')+result.vo.dto.entity.reportNo);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//隐藏掉遮罩
			loadMask.hide();
			//弹出异常提示
			Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),result.message);
		},
		unknownException:function(form,action){
			//隐藏掉遮罩
			loadMask.hide();
		},
		failure:function(form,action){
			//隐藏掉遮罩
			loadMask.hide();
		}
	});
}*/
/**
 * 打印
 */
pay.pdaLandPayInReport.print = function(){
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaLandPayInReport_content').getTotalFormForAdd();
	//获取报表编号 
	totalCount =totalForm.getForm().findField("waybillQtyTotal").getValue();
	//实收总额
	receivedAmountTotal = totalForm.getForm().findField("receivedAmountTotal").getValue();
	//现金总额
	receiveAmountTotal = totalForm.getForm().findField("receiveAmountTotal").getValue();
	 
	/**
	 * 返单数 @author 218392 zhangyongxue
	 */
	returnTicketTotal = totalForm.getForm().findField("returnTicketTotal").getValue();
	/**
	 * 实收返单数 @author 218392 zhangyongxue
	 */
	returnSingular = totalForm.getForm().findField("returnSingular").getValue();
	
	notes = totalForm.getForm().findField("notes").getValue();
	//如果报表编号为空，表示没有生产
	if(totalCount==0 || totalCount==undefined || totalCount==null){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.unDataToPrint'));
		return false;
	}
	//实收金额填写不正确
	if(!totalForm.getForm().isValid()){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
	/**
	 * @author 218392	zhangyongxue
	 * @date 2015-07-08 10:56:55
	 */
	if(stl.amountSub(returnSingular,returnTicketTotal)!=0 && Ext.isEmpty(notes)){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.returnSingularNotEqualsWarning'));//实收返单数与返单数不相等，备注信息必须填写！
		return false;
	}
	if(stl.amountSub(receivedAmountTotal,receiveAmountTotal)!=0 && Ext.isEmpty(notes)){
		Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.amountNotEqualsWarning'));
		return false;
		
	}
	var params = new Object();
	params.driverCode = pay.pdaLandPayInReport.driverCode;
	params.driverName = pay.pdaLandPayInReport.driverName;
	params.reportBeginDate = pay.pdaLandPayInReport.reportBeginDate;
	params.reportEndDate = pay.pdaLandPayInReport.reportEndDate;
	params.currentUser = FossUserContext.getCurrentUserEmp().empName;
	params.receivedAmountTotal = receivedAmountTotal;
	params.returnSingular = returnSingular;//@author 218392 zhangyongxue 2015-07-11
	params.notes = notes;
	//打印
	do_printpreview('pdapayinreportforpackage',params,ContextPath.STL_WEB);
}

/**
 * 司机收款报表表格model
 */
Ext.define('Foss.pdaLandPayInReport.DetailModel',{
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
		//付款方式
		name:'payType',
		type:'string'
	},{
		//金额
		name:'amount',
		type:'double'
	},{
		//刷卡收入
		name:'cardAmount',
		type:'double'
	},{
		//现金收入
		name:'cashAmount',
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
 * 总计表单
 */
Ext.define('Foss.pdaLandPayInReport.TotalFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		//司机编号
		name:'driverCode'
	},{
		//司机名称
		name:'driverName'
	}
//	,{
//		//车牌号
//		name:'vehicleNo'
//	}
	,{
		//总票数
		name:'waybillQtyTotal',
		type:'int'
	},{
		//总票数
		name:'piecesTotal',
		type:'int'
	},{
		//总体积
		name:'volumeTotal',
		type:'double'
	},{
		//总重量
		name:'weightTotal',
		type:'double'
	},{
		//返单总数
		name:'returnTicketTotal',
		type:'int'
	},{
		//签收单总数
		name:'signwaybillTotal',
		type:'int'
	},{
		//现金总额
		name:'receiveAmountTotal',
		type:'double'
	},{
		//刷卡总收入
		name:'cardTotalAmount',
		type:'double'
	},{
		//现金总收入
		name:'cashTotalAmount',
		type:'double'
	},{
		//办理人Code
		name:'createUserCode'
	},{
		//报表编号
		name:'reportNo'
	},{
		//办理人名称
		name:'createUserName'
	},{
		//办理时间
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//实收总额
		name:'receivedAmountTotal',
		type:'double'
	},{
		//实收返单数 @author 218392	zhangyongxue 2015-07-07
		name:'returnSingular',
		type:'int'
	},{
		//报表起始日期
		name:'reportBeginDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//报表结束日期
		name:'reportEndDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//异常备注
		name:'notes'
	}]
});

/**
 * 接货列表store
 */
Ext.define('Foss.pdaLandPayInReport.DetailReceiveGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaLandPayInReport.DetailModel'
});

/**
 * 送货列表store
 */
Ext.define('Foss.pdaLandPayInReport.DetailDeliverGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaLandPayInReport.DetailModel'
});

/**
 * 新增页面查询form
 */
Ext.define('Foss.pdaLandPayInReport.QueryFormForAdd',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.pdaPayReport'),
	layout:'column',
	defaults:{
		labelWidth:75,
		labelAlign:'right'
	},
	items:[{
		xtype:'commonExpressemployeeselector',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.landDriverName'),
		name:'driverCode',
		labelWidth:100,
		columnWidth:.25,
		allowBlank:false,
		listeners:{
			'change':function(th,newValue,oldValue){
				//获取司机工号控件
				var driverName = this.up('form').getForm().findField('driverName');
				//如果当前所选值不为空，则将司机工号设置到工号组件中
				if(!Ext.isEmpty(newValue)){
					driverName.setValue(newValue);
				}else{
					driverName.setValue(null);
				}
			}
		}
	},{
		xtype:'textfield',
		labelWidth:100,
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.landDriverCode'),
		readOnly:true,
		name:'driverName',
		columnWidth:.18
	},{
		xtype:'datetimefield_date97',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.startTime'),
		id:'FOSS_Pay_pdaLandPayInReport_StartTime_ID',
		time:true,
		name:'startTime',
		labelWidth:100,
		columnWidth:.26,
		editable:false,
		value: Ext.Date.format(stl.getTargetDate(new Date(),0), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: 'FOSS_Pay_pdaLandPayInReport_StartTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd HH:mi:ss'
		}
	},{
		xtype:'datetimefield_date97',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.endTime'),
		id:'FOSS_Pay_pdaLandPayInReport_EndTime_ID',
		time:true,
		name:'endTime',
		columnWidth:.19,
		labelWidth:25,
		editable:false,
		value: Ext.Date.format(stl.getTargetDate(new Date(),0), 'Y-m-d')+' 23:59:59',
		dateConfig: {
			el: 'FOSS_Pay_pdaLandPayInReport_EndTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd HH:mi:ss'
		}
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.04
	},{
		xtype:'button',
		text:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.query'),
		columnWidth:.08,
		handler:pay.pdaLandPayInReport.query
	}]
	
});

/**
 * 接货表格
 */
Ext.define('Foss.pdaLandPayInReport.DetailReceiveGrid',{
	extend:'Ext.grid.Panel',
    height:160,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	title: pay.pdaLandPayInReport.i18n('foss.stl.pay.common.receiveGoodsTable'),
	emptyText:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.pdaLandPayInReport.DetailReceiveGridStore'),
  	viewConfig:{
  		enableTextSelection : true,//设置行可以选择，进而可以复制
  		/**
  		 * @author 218392	zhangyongxue 
  		 * @date   2015-07-07 09:30:00
  		 * 当付款方式为‘现金CH’或为‘银行卡CD’时，‘现付金额（元）’与‘PDA现金收入（元）’不一致时，标红
  		 */
  		getRowClass : function(record,rowIndex,rowParams,store){ 
        	if((record.data.payType=='CH'||record.data.payType=='CD')&&(record.data.amount != record.data.cashAmount)){
            	return 'myRowRed';
            }                        
         }
  	},
  	defaults:{
  		align:'center'
  	},
  	columns:[{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{//@author 218392 zhangyongxue 2015-07-11 14:07:00
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.payType'),//付款方式 
		dataIndex:'payType',
		flex:1,
		renderer:function(v){
			if('CD'==v){
				return '银行卡';
			}else if('NT'==v){
				return '支票';
			}else if('CH'==v){
				return '现金';
			}else if('TT'==v){
				return '电汇';
			}else if('OL'==v){
				return '网上支付';
			}else if('CT'==v){
				return '月结';
			}else if('DT'==v){
				return '临时欠款';
			}else if('FC'==v){
				return '到付';
			}
		}
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.amount'),//现付金额
		dataIndex:'amount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cardAmount'),//刷卡收入
		dataIndex:'cardAmount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashAmount'),//PDA现金收入
		dataIndex:'cashAmount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.isSignwaybill'),
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
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'totalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashAmountTotal')//现付总金额
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:75,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:80,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.singCount')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'cardTotalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cardTotalAmount')//刷卡总收入
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'cashTotalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashTotalAmount')//PDA现金总收入
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 送货表格
 */
Ext.define('Foss.pdaLandPayInReport.DetailDeliverGrid',{
	extend:'Ext.grid.Panel',
    height:160,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	title: pay.pdaLandPayInReport.i18n('foss.stl.pay.common.sendGoodsTable'),
	emptyText:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.pdaLandPayInReport.DetailDeliverGridStore'),
  	defaults:{
  		align:'center'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns:[{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.arriveAmount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cardAmount'),
		dataIndex:'cardAmount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashAmount'),
		dataIndex:'cashAmount',
		flex:1
	},{
		header:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.isReturnTicket'),
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
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'totalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.arriveAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:75,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:80,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.returnTicketCount')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'cardTotalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cardTotalAmount')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'cashTotalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashTotalAmount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 明细区域
 */
Ext.define('Foss.pdaLandPayInReport.DetailPanel',{
	extend:'Ext.panel.Panel',
	//frame:true,
	items:[
		Ext.create('Foss.pdaLandPayInReport.DetailReceiveGrid'),
		Ext.create('Foss.pdaLandPayInReport.DetailDeliverGrid')
	]
});


/**
 * 总计
 */
Ext.define('Foss.pdaLandPayInReport.TotalFormForAdd',{
	extend:'Ext.form.Panel',
	title:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalAll'),
	frame:true,
	layout:'column',
	defaultType:'textfield',
	defaults:{
		labelWidth:110,
		readOnly:true
	},
	items:[{
		xtype:'numberfield',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount'),//总票数
		name:'waybillQtyTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalPieces'),//总件数
		name:'piecesTotal',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalQty'),//总体积
		xtype:'numberfield',
		name:'volumeTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalWeight'),//总重量
		xtype:'numberfield',
		name:'weightTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.returnTicketCount'),//返单数
		name:'returnTicketTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalSignwaybill'),//签收单总数
		xtype:'numberfield',
		name:'signwaybillTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCashAmount'),//现金总额
		xtype:'numberfield',
		name:'receiveAmountTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cardTotalAmount'),//刷卡总收入
		xtype:'numberfield',
		name:'cardTotalAmount',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashTotalAmount'),//PDA现金总收入
		xtype:'numberfield',
		name:'cashTotalAmount',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.createUserName'),//办理人
		name:'createUserName',
		columnWidth:.3
	},{
		fieldLabel:'办理人编码',
		hidden:true,
		name:'createUserCode',
		columnWidth:.3
	},{
		xtype:'datefield',
		name:'createTime',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.createTime'),//办理时间
		columnWidth:.3,
		format:'Y-m-d H:i:s'
	},{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.receivedAmountTotal'),//实收总额
		name:'receivedAmountTotal',
		xtype:'numberfield',
		allowBlank:false,
		columnWidth:.3,
		readOnly:false,
		listeners:{
			'blur':function(){
				var receivedAmountTotal=this.getValue();
				if(receivedAmountTotal<0)
					{
						Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),"实收金额不能为负数！");
						this.setValue(null);
						return false;
					}
			}
		}
	},{	//@author 218392 zhangyongxue 2015-07-07 17:29:39  
		//增加'实收返单数'字段框,对'实收返单数'进行校验,若输入值不等于'返单数',则提示"实收返单数与返单数不相等,备注信息必须填写"
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.ReturnSingular'),//实收返单数
		name:'returnSingular',
		xtype:'numberfield',
		allowBlank:false,
		columnWidth:.3,
		readOnly:false,
		listeners:{
			'blur':function(){
				var returnSingular=this.getValue();
				if(returnSingular < 0)
					{
						Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),"实收返单数不能为负!");
						this.setValue(null);
						return false;
					}
			}
		}
	},{
		xtype:'textarea',
		name:'notes',
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.notes'),//异常备注
		columnWidth:.9,
		readOnly:false
	},{
		fieldLabel:'报表编号',
		name:'reportNo',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:'司机编号',
		name:'driverCode',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:'司机名称',
		name:'driverName',
		hidden:true,
		columnWidth:.3
	}/*,{
		fieldLabel:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaLandPayInReport.vehicleNo'),
		name:'vehicleNo',
		hidden:true,
		columnWidth:.3
	}*/,{
		fieldLabel:'报表起始日期',
		xtype:'datefield',
		labelWidth:90,
		hidden:true,
		name:'reportBeginDate',
		format:'Y-m-d H:i:s',
		columnWidth:.3
	},{
		fieldLabel:'报表结束日期',
		xtype:'datefield',
		labelWidth:90,
		hidden:true,
		name:'reportEndDate',
		format:'Y-m-d H:i:s',
		columnWidth:.3
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.8
	}/*,{
		xtype:'button',
		text:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.commit'),
		columnWidth:.1,
		handler:pay.pdaLandPayInReport.addReceiveReportBill,
		hidden:!pay.pdaLandPayInReport.isPermission('/stl-web/pay/addLandReceiveReportBill.action')
	}*/,{
		xtype:'button',
		text:pay.pdaLandPayInReport.i18n('foss.stl.pay.common.print'),
		columnWidth:.1,
		handler:pay.pdaLandPayInReport.print
	}]
	
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询表单
	var queryFormForAdd = Ext.create('Foss.pdaLandPayInReport.QueryFormForAdd');
	//明细区域
	var detailPanel = Ext.create('Foss.pdaLandPayInReport.DetailPanel',{hidden:true});
	//汇总表单
	var totalFormForAdd = Ext.create('Foss.pdaLandPayInReport.TotalFormForAdd',{hidden:true});
	
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-pdaLandPayInReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		loadMask:null,//遮罩
		getLoadMask:function(){
			var me = this;
			//获取遮罩效果
			if(Ext.isEmpty(me.loadMask)){
				me.loadMask = new Ext.LoadMask(me, {
					msg:pay.pdaLandPayInReport.i18n('foss.stl.pay.pdaPayInReport.saveMask'),
				    removeMask : true// 完成后移除
				});
			}
			return me.loadMask;
		},
		getDetailPanel:function(){
			return detailPanel;
		},
		getDetailReceiveGrid:function(){
			return detailPanel.items.items[0];
		},
		getDetailDeliverGrid:function(){
			return detailPanel.items.items[1];;
		},
		getTotalFormForAdd:function(){
			return totalFormForAdd;
		},
		items: [queryFormForAdd,detailPanel,totalFormForAdd],
		renderTo: 'T_pay-pdaLandPayInReport-body'
	});
});