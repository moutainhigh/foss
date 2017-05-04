/**
 * 刷新界面
 */
pay.pdaPayInReport.refreshPage = function(){
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	var deliverGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaPayInReport_content').getTotalFormForAdd();
	
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
	
	receiveGrid.store.removeAll();
	deliverGrid.store.removeAll();
	totalForm.getForm().reset();
}


/**
 * 查询
 */
pay.pdaPayInReport.query = function(){
	//获取form表单
	var form = this.up('form').getForm();
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	var deliverGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaPayInReport_content').getTotalFormForAdd();
	//刷新界面
	pay.pdaPayInReport.refreshPage();
	
	//判断界面输入条件是否都合法
	if(form.isValid()){
		//获取当前页面
		var loadMask = Ext.getCmp('T_pay-pdaPayInReport_content').getLoadMask();
		//获取遮罩,进行遮罩
		loadMask.show();
		//发送请求
		Ext.Ajax.request({
			url:pay.realPath('queryReceiptSendGoodsInfoByDate.action'),
			actionMethods:'post',
			params:{
				'vo.dto.driverCode':form.findField('driverCode').getValue(),
				'vo.dto.driverName':form.findField('driverCode').getRawValue(),
				'vo.dto.rptType':form.findField('rptType').getValue(),
				'vo.dto.queryReportDate':form.findField('queryReportDate').getValue()
			},
			success:function(response){
				//遮罩窗口
				loadMask.hide();
				//获取返回结果
				var result = Ext.decode(response.responseText);	
				//如果dto为空，则提示
				if(Ext.isEmpty(result.vo.dto)){
					Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport.i18n('foss.stl.pay.common.noResult'));
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
					return false;
				}
				var receivedList = result.vo.dto.receivedList;
				var deliverList = result.vo.dto.deliverList;
				var entity = result.vo.dto.entity;
				
				//如果接货和送货都为空，则提示
				if(Ext.isEmpty(receivedList) && Ext.isEmpty(deliverList)){
					Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport.i18n('foss.stl.pay.common.noResult'));
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
				}
				//声明总计实体
				var formModel = new Foss.pdaPayInReport.TotalFormModel(entity);
				//加载数据
				totalForm.loadRecord(formModel);
				//显示隐藏列表		
				var detailPanel = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailPanel();
				detailPanel.show();
				totalForm.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				//隐藏掉遮罩
				loadMask.hide();
				//弹出异常提示
				Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),result.message);
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
		Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.noDriverWarning'));
		return false;
	}
}

/**
 * 新增pda报表
 */
pay.pdaPayInReport.addReceiveReportBill = function(){
	//声明要用变量
	var vo,dto,receiveGrid,deliverGrid,
		totalForm,receivedList,deliverList,entity;
	//获取接货列表
	receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailReceiveGrid();
	//获取送货列表
	deliverGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailDeliverGrid();
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaPayInReport_content').getTotalFormForAdd();
	
	if(!totalForm.getForm().isValid()){
		Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
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
	var loadMask = Ext.getCmp('T_pay-pdaPayInReport_content').getLoadMask();
	//获取遮罩,进行遮罩
	loadMask.show();
	
	//调用后台方法继续保存操作
	Ext.Ajax.request({
		url:pay.realPath('addReceiveReportBillByDate.action'),
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
			pay.pdaPayInReport.refreshPage();
			
			//获得打印编号
			totalForm.getForm().findField('reportNo').setValue(result.vo.dto.entity.reportNo);
			
			Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'), pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.saveSuccess')+result.vo.dto.entity.reportNo);
			
			Ext.MessageBox.show({
			      title: pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),
			      msg: pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.saveSuccess')+result.vo.dto.entity.reportNo+pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.isPrint'),
			      buttons: Ext.MessageBox.YESNO,
			      buttonText:{ 
			          yes: pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.yes'), 
			          no:  pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.no'),
			      },
			      fn: function(e){
			      	if(e=='yes'){
			      		pay.pdaPayInReport.print();
			      	}else if(e=='no'){
			      		return false;
			      	}
			      }
			  });
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//隐藏掉遮罩
			loadMask.hide();
			//弹出异常提示
			Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),result.message);
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
}
/**
 * 打印
 */
pay.pdaPayInReport.print = function(){
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaPayInReport_content').getTotalFormForAdd();
	//获取报表编号 
	var reportNo = totalForm.getForm().findField('reportNo').getValue();
	//如果报表编号为空，表示没有生产
	if(Ext.isEmpty(reportNo)){
		Ext.Msg.alert(pay.pdaPayInReport.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.unSaveToPrintWarning'));
		return false;
	}
	//打印
	do_printpreview('pdapayinreport',{reportNos:reportNo},ContextPath.STL_WEB);
	
}

/**
 * 司机收款报表表格model
 */
Ext.define('Foss.pdaPayInReport.DetailModel',{
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
 * 总计表单
 */
Ext.define('Foss.pdaPayInReport.TotalFormModel',{
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
	},{
		//异常备注
		name:'rptType'
	}]
});

/**
 * 接货列表store
 */
Ext.define('Foss.pdaPayInReport.DetailReceiveGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaPayInReport.DetailModel'
});

/**
 * 送货列表store
 */
Ext.define('Foss.pdaPayInReport.DetailDeliverGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaPayInReport.DetailModel'
});

/**
 * 新增页面查询form
 */
Ext.define('Foss.pdaPayInReport.QueryFormForAdd',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.pdaPayReport'),
	layout:'column',
	defaults:{
		labelWidth:75,
		labelAlign:'right'
	},
	items:[{
		xtype:'commonowndriverselector',
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.driverName'),
		name:'driverCode',
		columnWidth:.3,
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
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.driverCode'),
		readOnly:true,
		name:'driverName',
		columnWidth:.3
	},{
		xtype:'datefield',
		fieldLabel:'接送货日期',
		name:'queryReportDate',
		columnWidth:.25,
		allowBlank:false,
		editable:false,
		format:'Y-m-d',
		value:new Date()
	},{
		xtype:'combobox',
		fieldLabel:'类型',
		name:'rptType',
		columnWidth:.3,
		allowBlank:false,
		editable : false,
		store : FossDataDictionary.getDataDictionaryStore(settlementDict.DRIVER_COLLECTION_RPT_D__TYPE),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		value:pay.pdaPayInReport.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP,
		listeners:{
			'change':function(th,newValue,oldValue,opt){
				//获取接货列表
				var receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailReceiveGrid();
				//获取送货列表
				var deliverGrid = Ext.getCmp('T_pay-pdaPayInReport_content').getDetailDeliverGrid();
				//如果为接货，则显示接货列表，隐藏送货列表。反之隐藏接货列表，显示送货列表
				if(newValue==pay.pdaPayInReport.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP){
					receiveGrid.show(); deliverGrid.hide();
				}else if(newValue==pay.pdaPayInReport.DRIVER_COLLECTION_RPT_D__TYPE__DELIVERY){
					receiveGrid.hide(); deliverGrid.show();
				}
			}
		}
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.8
	},{
		xtype:'button',
		text:pay.pdaPayInReport.i18n('foss.stl.pay.common.query'),
		columnWidth:.1,
		handler:pay.pdaPayInReport.query
	}]
	
});

/**
 * 接货表格
 */
Ext.define('Foss.pdaPayInReport.DetailReceiveGrid',{
	extend:'Ext.grid.Panel',
    height:160,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.pdaPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.pdaPayInReport.DetailReceiveGridStore'),
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	defaults:{
  		align:'center'
  	},
  	columns:[{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.amount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.isSignwaybill'),
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
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'totalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.cashAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:75,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:80,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.singCount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 送货表格
 */
Ext.define('Foss.pdaPayInReport.DetailDeliverGrid',{
	extend:'Ext.grid.Panel',
    height:160,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.pdaPayInReport.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.pdaPayInReport.DetailDeliverGridStore'),
  	defaults:{
  		align:'center'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns:[{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.arriveAmount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.isReturnTicket'),
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
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'totalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.arriveAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:75,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:80,
				fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.returnTicketCount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 明细区域
 */
Ext.define('Foss.pdaPayInReport.DetailPanel',{
	extend:'Ext.panel.Panel',
	//frame:true,
	items:[
		Ext.create('Foss.pdaPayInReport.DetailReceiveGrid'),
		Ext.create('Foss.pdaPayInReport.DetailDeliverGrid',{hidden:true})
	]
});


/**
 * 新增页面查询form
 */
Ext.define('Foss.pdaPayInReport.TotalFormForAdd',{
	extend:'Ext.form.Panel',
	title:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalAll'),
	frame:true,
	layout:'column',
	defaultType:'textfield',
	defaults:{
		labelWidth:75,
		readOnly:true
	},
	items:[{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCount'),
		name:'waybillQtyTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalPieces'),
		name:'piecesTotal',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalQty'),
		xtype:'numberfield',
		name:'volumeTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalWeight'),
		xtype:'numberfield',
		name:'weightTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.returnTicketCount'),
		name:'returnTicketTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalSignwaybill'),
		xtype:'numberfield',
		name:'signwaybillTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.totalCashAmount'),
		xtype:'numberfield',
		name:'receiveAmountTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.createUserName'),
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
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.createTime'),
		columnWidth:.3,
		format:'Y-m-d H:i:s'
	},{
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.receivedAmountTotal'),
		name:'receivedAmountTotal',
		xtype:'numberfield',
		maxValue:99999999.99,
		maxText:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.maxValue'),
		allowBlank:false,
		columnWidth:.3,
		readOnly:false,
		listeners:{
			'blur':function(){
				var receivedAmountTotal=this.getValue();
				if(receivedAmountTotal<=0)
					{
					    Ext.Msg.alert(pay.pdaLandPayInReport.i18n('foss.stl.pay.common.alert'),"实收金额不能为负数！");
						this.setValue(null);
						return false;
					}
			}
		}
	},{
		xtype:'textarea',
		name:'notes',
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.notes'),
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
		fieldLabel:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
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
		fieldLabel:'rptType',
		name:'rptType',
		hidden:true,
		columnWidth:.3
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.7
	},{
		xtype:'button',
		text:pay.pdaPayInReport.i18n('foss.stl.pay.common.commit'),
		columnWidth:.1,
		handler:pay.pdaPayInReport.addReceiveReportBill,
		disabled:!pay.pdaPayInReport.isPermission('/stl-web/pay/addReceiveReportBill.action'),
		hidden:!pay.pdaPayInReport.isPermission('/stl-web/pay/addReceiveReportBill.action')
	},{
		xtype:'button',
		text:pay.pdaPayInReport.i18n('foss.stl.pay.common.print'),
		columnWidth:.1,
		handler:pay.pdaPayInReport.print
	}]
	
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询表单
	var queryFormForAdd = Ext.create('Foss.pdaPayInReport.QueryFormForAdd');
	//明细区域
	var detailPanel = Ext.create('Foss.pdaPayInReport.DetailPanel',{hidden:true});
	//汇总表单
	var totalFormForAdd = Ext.create('Foss.pdaPayInReport.TotalFormForAdd',{hidden:true});
	
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-pdaPayInReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		loadMask:null,//遮罩
		getLoadMask:function(){
			var me = this;
			//获取遮罩效果
			if(Ext.isEmpty(me.loadMask)){
				me.loadMask = new Ext.LoadMask(me, {
					msg:pay.pdaPayInReport.i18n('foss.stl.pay.pdaPayInReport.saveMask'),
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
		renderTo: 'T_pay-pdaPayInReport-body'
	});
});