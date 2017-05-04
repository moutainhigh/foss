consumer.queryFreightToCollectBill.DATELIMITDAYS_YEAR=365;


//最大显示条数：model
Ext.define('Foss.consumer.queryFreightToCollectBill.StockStatusModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'valueName'
	},{
		name:'valueCode'
	}]
});

consumer.queryFreightToCollectBill.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for ( var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

//最大显示条数：store
Ext.define('Foss.consumer.queryFreightToCollectBill.StockStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.consumer.queryFreightToCollectBill.StockStatusModel',
	data:{
		'items':[
		    {valueName:'全部',valueCode:''},
			{valueName:'库存少货',valueCode:'LOSE_STOCK'},
			{valueName:'正常签收',valueCode:'NORMAL_SIGN_STOCK'},
			{valueName:'异常签收',valueCode:'UNNORMAL_SIGN'},
			{valueName:'未入库',valueCode:'NO_STOCK'},
			{valueName:'库存中',valueCode:'IN_STOCK'},
			{valueName:'库存异常',valueCode:'EXCEPTION_STOCK'}
			
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


//通用时间校验
consumer.dateValidation = function(form){
	var startTime = form.findField('startDate').getValue();
	var endTime = form.findField('endDate').getValue();
	var smallArea=form.findField('smallArea');
	var dept = form.findField('department').getValue();
	//收入部门编码
	var smallAreaCode=smallArea.getValue();
	//收入部门名称
	var smallAreaName=smallArea.getRawValue();
	//		
	var bigArea=form.findField('bigArea');
	//收款部门编码
	var bigAreaCode=bigArea.getValue();
	//收款部门名称
	var bigAreaName=bigArea.getRawValue();	
	//结束时间
	if(Ext.isEmpty(endTime)){
		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.endTimeCannotNull'));
		return false;
	}
	//开始时间
	if(Ext.isEmpty(startTime)){
		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.startTimeCannotNull'));
		return false;
	}
	//开始和结束时间
	if(startTime > endTime){
		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.startTimeCannotGreaterThanEndTime'));
		return false;
	}

	if(stl.compareTwoDate(startTime,endTime)>consumer.queryFreightToCollectBill.DATELIMITDAYS_YEAR){
		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.startTimeAndEndTimePassMax')+ consumer.queryFreightToCollectBill.DATELIMITDAYS_YEAR +consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.date'));
		return false;
	} 

	if(stl.compareTwoDate(startTime,endTime)>stl.DATELIMITDAYS_MONTH){
		 if(Ext.isEmpty(smallAreaCode) && Ext.isEmpty(bigAreaCode) && Ext.isEmpty(dept)){
			Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),'查询超过一个月的的数据，大区和小区和部门不能同时为空');
			return false;
		}
	} 
	 if(Ext.isEmpty(bigAreaCode) && Ext.isEmpty(dept)){
		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),
	    consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.validateRegionFailAlert'));
		return false;
	} 
	return true;
};

//到付清查查询model
Ext.define('Foss.Stlyuscyings.FreightToCollectBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'businessDate'
	},{
		name:'receivableNo'
	},{
		name:'origOrgName'
	},{
		name:'destOrgName'
	},{
		name:'waybillNo'
	},{
		name:'billType'
	},{
		name:'stockStatus'
	},{
		name:'goodsQtyTotal',
		type:'long'
	},{
		name:'productCode'
	},{
		name:'conrevenDate'
	},{
		name:'amount',
		type:'long'
	},{
		name:'verifyAmount',   
		type:'long'
	},{
		name:'unverifyAmount',
		type:'long'
	}]
});

//日期查询列表Store
Ext.define('Foss.Stlyuscyings.FreightToCollectBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.FreightToCollectBillModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		timeout:600000,
		url:consumer.realPath('queryFreightToCollectBillByDate.action'),
		reader:{
			type:'json',
			root:'billFreightToCollectVO.billFreightToCollectList',
			totalProperty:'totalCount'
		}
	}
});


//库存状态查询

//第一个Tab:按日期查询
Ext.define('Foss.Stlyuscyings.FreightToCollectBillQueryInfoTab', {
	extend:'Ext.form.Panel',
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	title: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.queryOnDate'),
	tabConfig: {
		width: 120
	},
	columnWidth:1,
	height :230,
	items : [{
       	name:'queryByDate',
        items:[{
        	xtype:'form',
        	id:'Foss_Stlyuscyings_FreightToCollectBillQueryInfoTab_QueryByDate_ID',
        	defaults:{
	        	margin:'10 0 0 5',
	        	labelWidth:85,
	        	colspan:1
       		 },
        		layout:{
        			type :'table',
        			columns :3
        		},
		    items:[{
		    	xtype      : 'fieldcontainer', 
				defaultType: 'radiofield',
				fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.dateType'),
				layout:'column',
				colspan:1,
				items: [{ 
						boxLabel  : consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.businessDate'),
						checked   : true,
						name      : 'selectDateType', 
						inputValue: '1',  //表单的参数值
						id:'queryFreightToCollectBill_BusinessData_Id'
					},{ 
						boxLabel  : consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.accountDate'),
						name      : 'selectDateType', 
						inputValue: '2', //表单的参数值
						id:'queryFreightToCollectBill_AccountData_Id'
				   }]
		    },{
		    	xtype      : 'fieldcontainer', 
				defaultType: 'radiofield',
				fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.deptType'),
				layout:'column',
				colspan:2,
				items: [{ 
						boxLabel  : consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.departFrom'),
						name      : 'deptType', 
						inputValue: consumer.queryFreightToCollectBill.DEPTTYPE_FROM//表单的参数值
					},{ 
						boxLabel  : consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.departTo'),
						name      : 'deptType', 
						checked   : true,
						inputValue: consumer.queryFreightToCollectBill.DEPTTYPE_TO //表单的参数值
				   }]
		    },{
		    	xtype:'datefield',
		    	name:'startDate',
		    	fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.startDate'),
		    	value: new Date(),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	maxValue:new Date()
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.endDate'),
		    	value: new Date(),
		    	format:'Y-m-d',
		    	editable:false,
		    	allowBlank:false,
		    	maxValue:new Date()
		    },{
                xtype: 'combobox',
                name:'billType',
                fieldLabel: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.billClass'),
                store:FossDataDictionary.getDataDictionaryStore('BILL_RECEIVABLE__BILL_TYPE',null,{
                     'valueCode': '',
                     'valueName': consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.all')
                },[
                    consumer.queryFreightToCollectBill.COD_RECEIVABLE , // 代收货款应收
                    consumer.queryFreightToCollectBill.DESTINATION_RECEIVABLE,//到达应收
                    consumer.queryFreightToCollectBill.AIR_RECEIVABLE,//空运其他应收
                    consumer.queryFreightToCollectBill.DESTINATION_PARTIAL_LINE,//到达偏线代理应收单
                    consumer.queryFreightToCollectBill.AIR_AGENCY,//空运到达代理应收
                    consumer.queryFreightToCollectBill.AIR_AGENCY_COD,//空运代理代收货款应收
                    consumer.queryFreightToCollectBill.DESTINATION_LAND_STOWAGE,//快递代理应收
                    consumer.queryFreightToCollectBill.LAND_STOWAGE_AGENCY_COD//快递代理代收货款应收单
                ]),
                queryMode:'local',
                editable:false,
                displayField:'valueName',
                valueField:'valueCode',
                value:''
            },  {
				xtype : 'linkagecomboselector',
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				itemId : 'Foss_baseinfo_BigRegion_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
				fieldLabel :consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.bigArea'),
				value:'',
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam: 'commonOrgVo.name',
				name : 'bigArea',
				allowBlank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3	
		    },{	    	
		    	xtype : 'linkagecomboselector',
				itemId : 'Foss_baseinfo_SmallRegion_ID',
				eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
				name : 'smallArea',
				fieldLabel : consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.smallArea'),
				parentParamsAndItemIds : {
					'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
				},// 此处城市不需要传入
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam: 'commonOrgVo.name',
				allowBlank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3	
				// 分页
		    },{
		        xtype : 'linkagecomboselector',
                name : 'department',
                eventType : ['callparent'],
                store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
                fieldLabel: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.deptNo'),
                parentParamsAndItemIds : {
                    'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
                },// 此处区域不需要传入
                minChars : 0,
                displayField : 'name',// 显示名称
                valueField : 'code',
                queryParam : 'commonOrgVo.name',
                allowBlank : true,
                columnWidth : .3,
                listWidth : 300,// 设置下拉框宽度
                isPaging : true// 分页
            },{
                xtype: 'combobox',
                name:'productCodeList',
                fieldLabel: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),
                store:null,//Ext.create('Foss.pkp.ProductStore'),
                queryMode:'local',
                multiSelect:true,
                editable:false,
                displayField:'name',
                valueField:'code',
                value:''
		    },{
		    	xtype: 'combobox',
				name:'stockStatus',
		        fieldLabel: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.stockStatus'),
				queryMode:'local',
		    	editable:false,
		    	store:Ext.create('Foss.consumer.queryFreightToCollectBill.StockStatusStore'),
				displayField:'valueName',
				valueField:'valueCode',
				value:''
		    },{
				xtype: 'container',
				border : false,
				columnWidth:.8,
				html: '&nbsp;'
			},{	
				border: 1,
				xtype:'container',
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					text:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
						var dept = this.up('form').getForm().findField("department");
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = dept.store;
							var  key = dept.displayField + '';
							var value = dept.valueField+ '';
							
							var m = Ext.create(store.model.modelName);
							m.set(key, displayText);
							m.set(value, valueText);
							
							store.loadRecords([m]);
							dept.setValue(valueText);
						}		
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					text:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){						
						var form = this.up('form').getForm();
						if (form.isValid()) {
							if(consumer.dateValidation(form)){
							var freightToCollectBillStore = Ext.getCmp('Foss_Stlyuscyings_FreightToCollectBillQueryInfoGrid_Id').store;
							
							freightToCollectBillStore.removeAll();
							
							freightToCollectBillStore.loadPage(1,{
								callback:function(records, operation, success) {
									var result = Ext.decode(operation.response.responseText);
									Ext.getCmp('Foss_FreightToCollectBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billFreightToCollectVO.totalRecordsInDB);
									Ext.getCmp('Foss_FreightToCollectBillQueryInfoGrid_TotalAmount_Id').setValue(result.billFreightToCollectVO.totalAmount);
									Ext.getCmp('Foss_FreightToCollectBillQueryInfoGrid_TotalVerifyAmount_Id').setValue(result.billFreightToCollectVO.totalVerifyAmount);
									Ext.getCmp('Foss_FreightToCollectBillQueryInfoGrid_TotalUnverifyAmount_Id').setValue(result.billFreightToCollectVO.totalUnverifyAmount);
								}
								});}
						}else{
							Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
						}
						
					}
				}
				]
		    }]
        }]
	}]
});

//应收单查询列表
Ext.define('Foss.Stlyuscyings.FreightToCollectBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
    emptyText: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.emptyText'),
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.businessDate'), 
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return value;
			}
		}
	},{
		header:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.receivableNo'), 
		dataIndex: 'receivableNo'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.originatingDept'), 
		dataIndex: 'origOrgName'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.toDept'), 
		dataIndex: 'destOrgName'		
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.waybillNo'), 
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.billClass'), 
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__BILL_TYPE');
    		return displayField;
		}
	},{
		header:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.stockStatus'),
		dataIndex:'stockStatus',
		renderer:function(value){
			if(value == 'LOSE_STOCK'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.loseStockStatus');
			}
			else if(value == 'NORMAL_SIGN_STOCK'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.normalSignStockStatus');
			}
			else if(value == 'UNNORMAL_SIGN'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.unnormalSignStockStatus');
			}
			else if(value == 'NO_STOCK'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.noStockStatus');
			}
			else if(value == 'IN_STOCK'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.inStockStatus');
			}
			else if(value == 'EXCEPTION_STOCK'){
				return consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.exceptionStockStatus');
			}
			else{
				return value;
			}
		}	
	},{
		header:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.goodsQtyTotal'),
		dataIndex:'goodsQtyTotal'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.productType'), 
		dataIndex: 'productCode',
		renderer:function(value){
			return '';//Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.receivableAmount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.verifyAmount'),
		dataIndex: 'verifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.unverifyAmount'), 
		dataIndex: 'unverifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	}]
});

/**
 * 主启动方法
 */	
Ext.onReady(function() {
		Ext.QuickTips.init();
		
	var freightToCollectBillQueryInfoTab = Ext.create('Foss.Stlyuscyings.FreightToCollectBillQueryInfoTab');

	//创建按日期查询到付清查单列表Store
	var FreightToCollectBillStore = Ext.create('Foss.Stlyuscyings.FreightToCollectBillStore',{
		listeners : {
				beforeload : function(store, operation, eOpts) {
					var form = Ext.getCmp('Foss_Stlyuscyings_FreightToCollectBillQueryInfoTab_QueryByDate_ID');
					if (form) {
						var billFreightToCollectVO = new Object();
						var selectBusinessDate = Ext.getCmp('queryFreightToCollectBill_BusinessData_Id').getValue();
						var selectAccountDate = Ext.getCmp('queryFreightToCollectBill_AccountData_Id').getValue();
						var startDateTemp;
						var endDateTemp;
						var selectDateType;
						//业务日期
						if(selectBusinessDate && !selectAccountDate){
							selectDateType = 'BU';
						}else if(!selectBusinessDate && selectAccountDate){
							selectDateType = 'AC';
						}
						startDateTemp = form.getForm().findField('startDate').getValue();
						endDateTemp = form.getForm().findField('endDate').getValue();
						
						//是否有效		
						var isActive=form.getValues().isActive;
						
						var active;
						//用户选择查询有效或者无效
						if('Y'==isActive){
							active='Y';
						}else if('N'==isActive){
							active='N';
						}
						
						var params = {
								'billFreightToCollectVO.dto.selectDateType':selectDateType,
								'billFreightToCollectVO.dto.startDate':startDateTemp,
								'billFreightToCollectVO.dto.endDate':endDateTemp,
								'billFreightToCollectVO.dto.stockStatus':form.getForm().findField('stockStatus').getValue(),
								'billFreightToCollectVO.dto.department':form.getForm().findField('department').getValue(),
								'billFreightToCollectVO.dto.bigArea':form.getForm().findField('bigArea').getValue(),
								'billFreightToCollectVO.dto.smallArea':form.getForm().findField('smallArea').getValue(),
								'billFreightToCollectVO.dto.billType':form.getForm().findField('billType').getValue(),
								'billFreightToCollectVO.dto.productCodeList':consumer.queryFreightToCollectBill.convertProductCode(form.getForm().findField('productCodeList').getValue()),
								'billFreightToCollectVO.dto.deptType':form.getForm().getValues().deptType
						};
						
						Ext.apply(operation,{
							params:params
						});
					}
				}
			}
	});	
	
	//按日期查询到付清查列表Grid
	var freightToCollectBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.FreightToCollectBillQueryInfoGrid',{
		id:'Foss_Stlyuscyings_FreightToCollectBillQueryInfoGrid_Id',
		store:FreightToCollectBillStore,
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items: [{
				xtype:'button',
				text:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.export'),
				columnWidth:.1,
				handler: function() {
					var freightToCollectBillStore = Ext.getCmp('Foss_Stlyuscyings_FreightToCollectBillQueryInfoGrid_Id').store
					//数据为空，则不允许导出
					if(freightToCollectBillStore.data.length==0){
                 		Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryCashCollectionBill.noDataToExportWarning'));
						return false;
					}
					var form = Ext.getCmp('Foss_Stlyuscyings_FreightToCollectBillQueryInfoTab_QueryByDate_ID');
					if (form) {
						var billFreightToCollectVO = new Object();
						var selectBusinessDate = Ext.getCmp('queryFreightToCollectBill_BusinessData_Id').getValue();
						var selectAccountDate = Ext.getCmp('queryFreightToCollectBill_AccountData_Id').getValue();
						var startDateTemp;
						var endDateTemp;
						var selectDateType;
						if(selectBusinessDate && !selectAccountDate){
							selectDateType = 'BU';
						}else if(!selectBusinessDate && selectAccountDate){
							selectDateType = 'AC';
						}
						startDateTemp = form.getForm().findField('startDate').getValue();
						endDateTemp = form.getForm().findField('endDate').getValue();
						
						//是否有效		
						var isActive=form.getValues().isActive;
						var active;
						//用户选择查询有效或者无效
						if('Y'==isActive){
							active='Y';
						}else if('N'==isActive){
							active='N';
						}
						
						var paramsData = {
								'billFreightToCollectVO.dto.selectDateType':selectDateType,
								'billFreightToCollectVO.dto.startDate':startDateTemp,
								'billFreightToCollectVO.dto.endDate':endDateTemp,
								'billFreightToCollectVO.dto.stockStatus':form.getForm().findField('stockStatus').getValue(),
								'billFreightToCollectVO.dto.department':form.getForm().findField('department').getValue(),
								'billFreightToCollectVO.dto.bigArea':form.getForm().findField('bigArea').getValue(),
								'billFreightToCollectVO.dto.smallArea':form.getForm().findField('smallArea').getValue(),
								'billFreightToCollectVO.dto.billType':form.getForm().findField('billType').getValue(),
								'billFreightToCollectVO.dto.productCodeList':consumer.queryFreightToCollectBill.convertProductCode(form.getForm().findField('productCodeList').getValue()),
								'billFreightToCollectVO.dto.deptType':form.getForm().getValues().deptType
						};
						
						
						
						if(!Ext.fly('downloadAttachFileForm')){
							var frm = document.createElement('form');
							frm.id = 'downloadAttachFileForm';
							frm.style.display = 'none';
							document.body.appendChild(frm);
						}
						// AJAX请求导出EXECL
						var exportSuccessBool = true;
						Ext.Ajax.request({
							url:consumer.realPath('queryFreightToCollectBillExportExecl.action'),
							form: Ext.fly('downloadAttachFileForm'),
							method : 'post',
							params:paramsData,
							async: false,
							isUpload: true,
							success:function(response){
								//获取响应的json字符串
								var jsonText = Ext.decode(response.responseText.trim());
	                           	//导出失败
	                           	if(jsonText.resultMark=='error'){
	                             	exportSuccessBool = false;   
	                             	Ext.Msg.alert(consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryCashCollectionBill.exportFailure')+":" + jsonText.message );
	                             }
							},exception : function(response) {
								exportSuccessBool = false;   
							},failure : function(form, action) {
								exportSuccessBool = false;     
	                        },
							unknownException:function(form,action){
								exportSuccessBool = false;
							}	
						});
					}	
		        }
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'vbox',
			items: [{
					height:5,
					width:1600
			},{
				xtype:'panel',
				layout:'hbox',
				items:[{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'totalAmount',
			    		columnWidth:.1,
			    		id:'Foss_FreightToCollectBillQueryInfoGrid_TotalRecordsInDB_Id',
			    		labelWidth:40,
			    		fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.total')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'totalAmount',
			    		columnWidth:.15,
			    		id:'Foss_FreightToCollectBillQueryInfoGrid_TotalAmount_Id',
			    		labelWidth:56,
			    		fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.totalAmount')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'verifyAmount',
			    		columnWidth:.15,
			    		id:'Foss_FreightToCollectBillQueryInfoGrid_TotalVerifyAmount_Id',
			    		labelWidth:88,
			    		fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.verifyTotalAmount')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'unverifyAmount',
			    		columnWidth:.15,
			    		id:'Foss_FreightToCollectBillQueryInfoGrid_TotalUnverifyAmount_Id',
			    		labelWidth:88,
			    		fieldLabel:consumer.queryFreightToCollectBill.i18n('foss.stl.consumer.queryFreightToCollectBill.unverifyTotalAmount')
			    	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.01
					}]
			},Ext.create('Deppon.StandardPaging', {
	    		store: FreightToCollectBillStore,
	    		pageSize: 50,
	    		columnWidth:.4,
	    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})

	    })]
		}]
	});
		
		Ext.create('Ext.panel.Panel',{
			id: 'T_consumer-queryFreightToCollectBill_content',
			cls: "panelContentNToolbar",
			bodyCls: 'panelContentNToolbar-body',
			layout: 'auto',
			items: [freightToCollectBillQueryInfoTab,freightToCollectBillQueryInfoGrid],
			getQueryInfoTab:function(){
				return freightToCollectBillQueryInfoTab;
			},
			renderTo: 'T_consumer-queryFreightToCollectBill-body',
			listeners:{
				'boxready':function(th){
					var roles = stl.currentUserDepts;
					var queryByDateTab = th.getQueryInfoTab();
					var dept = queryByDateTab.getForm().findField("department");
					if(!Ext.isEmpty(stl.currentDept.code)){
						var displayText = stl.currentDept.name
						var valueText = stl.currentDept.code;
						var store = dept.store;
						var  key = dept.displayField + '';
						var value = dept.valueField+ '';
						
						var m = Ext.create(store.model.modelName);
						m.set(key, displayText);
						m.set(value, valueText);
						
						store.loadRecords([m]);
						dept.setValue(valueText);
					}
				}
		}
		});
	});
