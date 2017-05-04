/**
 * @项目：合伙人项目
 * @功能：“发票对账单管理”的页面
 * @author:218371-foss-zhaoyanjun
 * @date;2016-01-28上午09:11
 */


/**
 * 对账单本期明细form表单model
 */
Ext.define('Foss.invoiceStatementManage.StatementFormModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'statementBillNo'
    }, {
        name: 'customerCode'
    }, {
        name: 'customerName'
    }, {
        name: 'periodAmount'
    }, {
        name: 'periodBeginDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'periodEndDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'departmentCode'
    }, {
        name: 'createOrgName'
    }, {
        name: 'sectorBenchmarkingEncoding'
    }, {
        name: 'invoiceStatus'
    },{
    	name:'statementBillNo'
    },{
    	name:'totleFee'
    },{
    	name:'createTime',
    	type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },{
    	name:'invoiceHeadCode'
    },{
    	name:'taxId'
    },{
    	name:'registeredAddress'
    },{
    	name:'registeredTelephone'
    },{
    	name:'bank'
    },{
    	name:'accountBank'
    },{
    	name:'isGeneralTaxpayer'
    },{
    	name:'invoiceMark'
    }]
});

/**
 * 对账单表格store
 */
Ext.define('Foss.invoiceStatementManage.StatementQueryGridStore', {
	extend:'Ext.data.Store',
    model:'Foss.invoiceStatementManage.StatementFormModel',
    pageSize:50,
    proxy:{
		type:'ajax',
		url:consumer.realPath('queryStatement.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'invoiceManagementAllVo.list',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			var hidden = Ext.getCmp('T_consumer-invoiceStatementManage_content').getHiddenField().getForm();
			//获取隐藏域的值
			var queryTabType=hidden.findField('queryTypeParam').getValue();
			
			var currentDeptCode=FossUserContext.getCurrentDeptCode();
			
			if(currentDeptCode==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.currentDeptNull'));
				return false;
			}
			
			//判断是按那种查询进行
			if(queryTabType=='TD'){
//				var queryInfoTab = Ext.getCmp('customerFrom').getForm();
				var queryInfoTab = Ext.getCmp('T_consumer-invoiceStatementManage_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
				var customerCode = queryInfoTab.findField('customerCode').getValue();
				var periodBeginDate = queryInfoTab.findField('periodBeginDate').getValue();
				var periodEndDate = queryInfoTab.findField('periodEndDate').getValue();
				var invoiceStatus = queryInfoTab.findField('invoiceStatus').getValue();
				searchParams = {
					'invoiceManagementAllVo.invoiceManagementAddDto.customerCode':customerCode,
					'invoiceManagementAllVo.invoiceManagementAddDto.periodBeginDate':periodBeginDate,
					'invoiceManagementAllVo.invoiceManagementAddDto.periodEndDate':periodEndDate,
					'invoiceManagementAllVo.invoiceManagementAddDto.departmentCode':consumer.invoiceStatementManage.departmentCode,
					'invoiceManagementAllVo.invoiceManagementAddDto.bigRegion':consumer.invoiceStatementManage.largeRegion,
					'invoiceManagementAllVo.invoiceManagementAddDto.smallRegion':consumer.invoiceStatementManage.smallRegion,
					'invoiceManagementAllVo.invoiceManagementAddDto.invoiceStatus':consumer.invoiceStatementManage.invoiceStatus,
					'invoiceManagementAllVo.invoiceManagementAddDto.queryPage':'TD',
					'invoiceManagementAllVo.invoiceManagementAddDto.currentDeptNo':currentDeptCode
				};
			}else if(queryTabType=='TDZ'){
//				var queryInfoTab = Ext.getCmp('billForm').getForm();
				var queryInfoTab = Ext.getCmp('T_consumer-invoiceStatementManage_content').getQueryInfoTab().items.items[2].items.items[0].getForm();
				var statementBillNumbers = queryInfoTab.findField('statementBillNumbers').getValue();
				//获取对账单号
				var billNumberArray = stl.splitToArray(statementBillNumbers);
				searchParams = {
					'invoiceManagementAllVo.invoiceManagementAddDto.statementNos':billNumberArray,
					'invoiceManagementAllVo.invoiceManagementAddDto.queryPage': 'TDZ',
					'invoiceManagementAllVo.invoiceManagementAddDto.currentDeptNo':currentDeptCode
				};
			}else if(queryTabType=='TB'){
//				var queryInfoTab = Ext.getCmp('waybillForm').getForm();
				var queryInfoTab = Ext.getCmp('T_consumer-invoiceStatementManage_content').getQueryInfoTab().items.items[1].items.items[0].getForm();
				var numbers = queryInfoTab.findField('numbers').getValue();
				//获取运单号、应收单号等
				var billNumberArray = stl.splitToArray(numbers);
				searchParams = {
					'invoiceManagementAllVo.invoiceManagementAddDto.billDetailNos':billNumberArray,
					'invoiceManagementAllVo.invoiceManagementAddDto.queryPage': 'TB',
					'invoiceManagementAllVo.invoiceManagementAddDto.currentDeptNo':currentDeptCode
				};
			}else{
				searchParams = {
						'invoiceManagementAllVo.invoiceManagementAddDto.departmentCode':consumer.invoiceStatementManage.departmentCode,
						'invoiceManagementAllVo.invoiceManagementAddDto.queryPage':''																			
					};
			}
			
			Ext.apply(operation,{
				params :searchParams
			});  
		}
	}
});

/**
 * 修改当前对账单条目
 */
consumer.invoiceStatementManage.statementDelete = function(){
	//声明变量
	var invoiceManagementAllVo,
		invoiceManagementAddDto,
		deleteStoreLength,
		myMask
		;
	//对账单明细表格数据量
	deleteStoreLength = Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().getSelectionModel().getSelection();
	
	if(deleteStoreLength==0){
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.currentDataIsNullWarning'));
		return false;
	}
	
	//未申请和已退回可以删除，其余都不行
	var invoiceStatus;
	for(var i=0;i<deleteStoreLength.length;i++){
		invoiceStatus=deleteStoreLength[i].data.invoiceStatus;
		if(invoiceStatus!='notApply'&&invoiceStatus!='reback'){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.invoiceStateValid'));
			return false;
		}
	}
	
	Ext.Msg.confirm(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'), consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.isSaveStatement'), function(btn){
		if(btn=='yes'){		
			var jsonData = new Array();
			var waybillNumber;
			for(var i=0;i<deleteStoreLength.length;i++){
				waybillNumber=deleteStoreLength[i].data.statementBillNo;
				jsonData.push(waybillNumber);
			}
			invoiceManagementAddDto=new Object();
			invoiceManagementAddDto.statementNos=jsonData;
			invoiceManagementAllVo=new Object();
			invoiceManagementAllVo.invoiceManagementAddDto = invoiceManagementAddDto;
			
        	myMask =Ext.getCmp('Foss.statementbill.LoadMaskAdd_ID');
        	if(myMask==null){
        		myMask = new Ext.LoadMask(Ext.getCmp('T_consumer-invoiceStatementManage_content'), {
	        		id:'Foss.statementbill.LoadMaskAdd_ID',
	    			msg:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.saveStatementMask'),
    			    removeMask : true// 完成后移除
        		});
        	}
			myMask.show();
	        
			Ext.Ajax.request({
				url :consumer.realPath('deleteStatement.action'), 
				jsonData:{'invoiceManagementAllVo':invoiceManagementAllVo},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);	
					myMask.hide();
					Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().hide();
				},
				failure:function(form,action){
					myMask.hide();
					Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().hide();
				},
				unknownException:function(form,action){
					myMask.hide();
					Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().hide();
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					myMask.hide();
					Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().hide();
					Ext.Msg.alert(consumer.statementAdd.i18n('foss.stl.consumer.common.alert'),'删除对账单出错，请联系系统管理员');
				}
			});
		}
	});
}

/**
 * 根据对账单号查询
 */
consumer.invoiceStatementManage.statementSelectByStatementNumber = function(){		
	var form  = this.up('form').getForm();	
	
	var statementBillNumbers = form.findField('statementBillNumbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(statementBillNumbers)!=null && Ext.String.trim(statementBillNumbers)!=''){
		var billNumberArray = stl.splitToArray(statementBillNumbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var grid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid();
		var receivableGrid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getReceivableDetailGrid();
		//查询后台
		var hidden = Ext.getCmp('T_consumer-invoiceStatementManage_content').getHiddenField();
		hidden.getForm().findField('queryTypeParam').setValue('TDZ');
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),rawData.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					consumer.invoiceStatementManage.statementSelectSuccess(result);	
					grid.show();
					receivableGrid.store.removeAll();
					receivableGrid.show();
				}
		    }
		});
	}else{
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.validateFailAlert'));
		return false;
	}	
};

/**
 * 按单号查询
 */
consumer.invoiceStatementManage.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(numbers)!=null && Ext.String.trim(numbers)!=''){
		var billNumberArray = stl.splitToArray(numbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var grid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid();
		var receivableGrid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getReceivableDetailGrid();
		var hidden = Ext.getCmp('T_consumer-invoiceStatementManage_content').getHiddenField();
		hidden.getForm().findField('queryTypeParam').setValue('TB');
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){	
					Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),rawData.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					consumer.invoiceStatementManage.statementSelectSuccess(result);	
					grid.show();
					receivableGrid.store.removeAll();
					receivableGrid.show();
				}
		    }
		});
	}else{
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.validateFailAlert'));
		return false;
	}	
};

/**
 * 查询成功后，后续处理方法
 * @param {} action
 */
consumer.invoiceStatementManage.statementSelectSuccess = function(result){
	var statementQueryGridStore;	
	statementQueryGridStore = Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid().store;
	//加载后台查询到的数据到grid的store中
	var statementOfAccountList = result.invoiceManagementAllVo.list;
	if(!Ext.isEmpty(statementOfAccountList)){
		statementQueryGridStore.loadData(statementOfAccountList);
	}else{
		statementQueryGridStore.removeAll();
		Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.noResult'));
	}
		
};

/**
 * 根据客户查询对账单
 */
consumer.invoiceStatementManage.statementSelectByCust = function(){
	var form  = this.up('form').getForm();	
	//声明客户编码和客户名称
	var customerCode,customerName;
	
	if(form.isValid()){
		//邓大伟
		consumer.invoiceStatementManage.departmentCode = form.findField('departmentCode').getValue();
		consumer.invoiceStatementManage.largeRegion = form.findField('bigRegion').getValue();
		consumer.invoiceStatementManage.smallRegion = form.findField('smallRegion').getValue();
		consumer.invoiceStatementManage.invoiceStatus = form.findField('invoiceStatus').getValue();
		consumer.invoiceStatementManage.customerCode=form.findField('customerCode').getValue();
		if(Ext.isEmpty(consumer.invoiceStatementManage.departmentCode)
				&&Ext.isEmpty(consumer.invoiceStatementManage.largeRegion)
				&&Ext.isEmpty(consumer.invoiceStatementManage.smallRegion)){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.orgCodeNull'));
			return false;
		}
		if(Ext.isEmpty(consumer.invoiceStatementManage.customerCode)){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerIsNullWarning'));
			return false;
		}
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		var grid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getStatementQueryGrid();
		var receivableGrid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getReceivableDetailGrid();
		var hidden = Ext.getCmp('T_consumer-invoiceStatementManage_content').getHiddenField();
		hidden.getForm().findField('queryTypeParam').setValue('TD');
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.alert'),rawData.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					consumer.invoiceStatementManage.statementSelectSuccess(result);	
					grid.show();
					receivableGrid.store.removeAll();
					receivableGrid.show();
				}
		    }
		});
	}
};

/**
* 单据子类型的model
*/
Ext.define('Foss.invoiceStatementManage.queryComboModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

Ext.define('Foss.invoiceStatementManage.InvoiceStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.invoiceStatementManage.queryComboModel',
	data:{
		'items':[
				{
					name:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.all'),
					value:'all'
				},
				{
					name:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.notApply'),
					value:'notApply'
				},
				{
					name:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.issued'),
					value:'issued'
				},
				{
					name:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.reback'),
					value:'reback'
				},
				{
					name:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.inIssue'),
					value:'inIssue'
				}
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

/**
 * 查询tab
 */
Ext.define('Foss.invoiceStatementManage.QueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab:0,// 默认显示按单号制作
	height:210,
    items: [{
      	title: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.makeByCustomer'),
		tabConfig: {
			width: 120
		},
		layout: 'hbox',
		items:[{
	    	xtype:'form',
//	    	id:'customerFrom',
	    	width:920,
	    	defaults:{
	    		margin:'5 5 5 5',
	        	labelWidth:80
        	},
			layout:'column',
		    items:[{
		    	xtype:'linkagecomboselector',
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				itemId : 'Foss_baseinfo_BigRegion_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
				columnWidth:.3,
				fieldLabel : consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.largeRegion'),
				name : 'bigRegion',
				isPaging: true,
				allowBlank : true,
				value:'',
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam : 'commonOrgVo.name'
		    },{
		    	xtype:'linkagecomboselector',
				itemId : 'Foss_baseinfo_SmallRegion_ID',
				eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
				columnWidth:.3,
				fieldLabel : consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.smallRegion'),
				name : 'smallRegion',
				allowBlank : true,
				isPaging: true,
				parentParamsAndItemIds : {
					'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
				},// 此处城市不需要传入
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam : 'commonOrgVo.name'
	        },{
	        	xtype : 'dynamicorgcombselector',
				fieldLabel : consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.statementOrgName'),
				name : 'departmentCode',
				value : '',
				columnWidth:.3,
				labelWidth : 80,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
	        },{
	        	xtype:'datefield',
	        	fieldLabel:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.periodBeginDate'),
	        	allowBlank:false,
	        	name:'periodBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-1)
	        },{
	        	xtype:'datefield',
	        	fieldLabel:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.to'),
	        	labelWidth:80,
	        	name:'periodEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	value:new Date()
	        },{
	        	xtype:'commonsaledepartmentselector',
	        	all:'true',// 查询所有(包含作废)
	        	labelWidth : 80,
	        	name:'customerCode',
	        	displayField : 'name',// 显示名称
				valueField : 'code',
	        	emptyText:'客户编码和手机均可查询',
	        	contcatFlag :'Y',// 增加按手机号查询
	        	singlePeopleFlag:'Y',
	        	fieldLabel:'<span style="color:red;">*</span>'+consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.partnerMessage'),
	        	columnWidth:.3
	        },{
	        	xtype: 'combobox',
				name:'invoiceStatus',
		        fieldLabel: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.statementStatus'),
				store:Ext.create('Foss.invoiceStatementManage.InvoiceStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				labelWidth : 80,
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
	        },{
	        	xtype:'displayfield',
		    	columnWidth:.3,
		    	value:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerType')
		    	+"&nbsp;:&nbsp;"+consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.businessPartner')
	        },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.44,
					html: '&nbsp;'
				},{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:consumer.invoiceStatementManage.statementSelectByCust
				}]
		    }]
		}]
	},{										
        title: consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.makeByWaybillNumber'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
//	    	id:'waybillForm',
	    	defaults:{
	        	margin:'5,5,5,5'
	   		 },
	    	layout:'column',
		    items:[{       		
		    	xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.number'),
				columnWidth:.65,
				regex:/^((HHR)?[0-9]{7,10}[;,])*((HHR)?[0-9]{7,10}[;,]?)$/i,
				regexText:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billNosQueryRegexText'),
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:104
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.075,
					handler:function() {
						// 重置
						this.up('form').getForm().reset();
					}
				},{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:consumer.invoiceStatementManage.statementSelectByNumber
				}]
        	}]
	    }]       
    },{
    	title: consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.makeByBillNumber'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
//	    	id:'billForm',
	    	defaults:{
	        	margin:'5,5,5,5'
	   		 },
	    	layout:'column',
		    items:[{       		
		    	xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.number'),
				columnWidth:.65,
				regex:/^((HH)?[0-9]{7,10}[;,])*((HH)?[0-9]{7,10}[;,]?)$/i,
				regexText:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billNosQueryRegexText'),
				labelWidth:70,
				labelAlign:'right',
				name:'statementBillNumbers',
				autoScroll:true,
				height:104
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.075,
					handler:function() {
						// 重置
						this.up('form').getForm().reset();
					}
				},{
					text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:consumer.invoiceStatementManage.statementSelectByStatementNumber
				}]
        	}]
	    }]
    }]
});

/**
 * 对账单本期明细form表单model
 */
Ext.define('Foss.invoiceStatementManage.receivableFormModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'businessDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'waybillNumber'
    }, {
    	name: 'totleFee'
    }, {
    	name: 'transportProperties'
    }, {
    	name: 'subTypesOfDocuments'
    }, {
    	name: 'customerName'
    }, {
    	name: 'customerCode'
    },{
        name: 'departmentName'
    }, {
        name: 'departmentCode'
    }, {
        name: 'customerName'
    }, {
        name: 'dateOfEntry',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
    	name: 'dateOfReceipt',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'destinationStation'
    }]
});

/**
 * 对账单表格store
 */
Ext.define('Foss.invoiceStatementManage.ReceivableQueryGridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.invoiceStatementManage.receivableFormModel',
	pageSize : 50,
	sorters : [ {
		property : 'businessDate',
		direction : 'ASC'
	} ],
	proxy : {
		type : 'ajax',
		url : null,
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'invoiceManagementAllVo.invoiceManagementAddDto.list'
		}
	},
	listeners : {
		'beforeLoad' : function(store, operation, eOpts) {
			var params = consumer.invoiceStatementManage.detailParams;
			Ext.apply(operation, {
				params : params
			});
		}
	}
});

/**
 * 对账单本期明细
 */
Ext.define('Foss.invoiceStatementManage.ReceivableDetailGrid', {
	extend:'Ext.grid.Panel',
    title: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billDetails'),
	frame:true,
	height:300,
//	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.invoiceStatementManage.ReceivableQueryGridStore'),
//    viewConfig:{
//  		enableTextSelection : true//设置行可以选择，进而可以复制
//  	},
    columns: [{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.businessDate'), 
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.waybillNo'),
		dataIndex: 'waybillNumber' 
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.totleFee'), 
		dataIndex: 'totleFee'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.productType'),
		dataIndex: 'transportProperties',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.documentType'),
		dataIndex: 'subTypesOfDocuments',
		renderer:function(value){
			var temp=FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
			if(temp==null||''==temp){
				return value;
			}else{
				return temp;
			}
		}
		
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerName'),
		dataIndex: 'customerName' 
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerCode'),
		dataIndex: 'customerCode'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.deptName'),
		dataIndex: 'departmentName'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.deptCode'),
		dataIndex: 'departmentCode'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billingDate'),
		dataIndex: 'dateOfEntry',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.signDate'),
		dataIndex: 'dateOfReceipt',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.aimAddress'),
		dataIndex: 'destinationStation'
	}]
});

//按应收单查询应收单明细
consumer.invoiceStatementManage.queryDetailByReceivableNo = function(grid, rowIndex, colIndex){
	//获取表头数据
	var selection = grid.getStore().getAt(rowIndex);
	//获取grid							   
	var receivableDetailGrid = Ext.getCmp('T_consumer-invoiceStatementManage_content').getReceivableDetailGrid();
	//封装参数
	consumer.invoiceStatementManage.detailParams = {
			"invoiceManagementAllVo.invoiceManagementAddDto.statementBillNo" : selection.data.statementBillNo
		};
	//获取URL
	var queryReceivableDetailUrl = consumer.realPath('queryReceivableDetailByStatementNo.action');
	//获取明细store
	var receivableDetailGridStore = receivableDetailGrid.store;
	//设置url
	receivableDetailGridStore.getProxy().url = queryReceivableDetailUrl;
	//查询
	receivableDetailGridStore.loadPage(1, {
		callback: function(records, operation, success) {
			var rawData = receivableDetailGridStore.proxy.reader.rawData;
			if(!success){
				Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.alertMessage'),'查询应收单明细报错，请联系系统管理员');
				return false;
			}
			//如果成功显示
			if(success){
				//对结果进行转化
				var result = Ext.decode(operation.response.responseText);
				//判断查询结果
				if(Ext.isEmpty(result.invoiceManagementAllVo.invoiceManagementAddDto.list) || result.invoiceManagementAllVo.invoiceManagementAddDto.list.length==0){
					Ext.Msg.alert(consumer.invoiceStatementManage.i18n('foss.stl.consumer.alertMessage'),
							consumer.invoiceStatementManage.i18n('foss.stl.consumer.emptyResult.title'));
					return false;
				}
			}
	    }
	});
}

/**
 * 对账单本期明细
 */
Ext.define('Foss.invoiceStatementManage.BillDetails', {
	extend:'Ext.grid.Panel',
    title: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billDetails'),
	frame:true,
	height:600,
	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.invoiceStatementManage.StatementQueryGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{
		xtype : 'actioncolumn',
		width : 73,
		text : consumer.invoiceStatementManage.i18n('foss.stl.consumer.common.actionColumn'),
		align : 'center',
		items : [{
    			iconCls : 'deppon_icons_showdetail',
    			tooltip : consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.QueryInfoGridTooltip'),
    			handler : function(grid, rowIndex, colIndex){
    				consumer.invoiceStatementManage.queryDetailByReceivableNo(grid, rowIndex, colIndex)
    			}
			}]
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.billNumber'), 
		dataIndex: 'statementBillNo'
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerCode'),
		dataIndex: 'customerCode' 
	},{
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.customerName'), 
		dataIndex: 'customerName'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.periodAmount'),
		dataIndex: 'totleFee'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementAdd.createTime'),
		dataIndex: 'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.periodStartDate'),
		dataIndex: 'periodBeginDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.periodEndAmount'),
		dataIndex: 'periodEndDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.deptCode'),
		dataIndex: 'departmentCode'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.deptName'),
		dataIndex: 'createOrgName'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.deptBenchMarkingCode'),
		dataIndex: 'sectorBenchmarkingEncoding'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.invoiceHeadCode'),
		dataIndex: 'invoiceHeadCode'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.taxId'),
		dataIndex: 'taxId'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.registeredAddress'),
		dataIndex: 'registeredAddress'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.registeredTelephone'),
		dataIndex: 'registeredTelephone'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.bank'),
		dataIndex: 'bank'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.accountBank'),
		dataIndex: 'accountBank'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.isGeneralTaxpayer'),
		dataIndex: 'isGeneralTaxpayer'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.invoiceMark'),
		dataIndex: 'invoiceMark'
	},{ 
		header: consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.invoiceStatus'),
		dataIndex: 'invoiceStatus',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "notApply") {
					displayField = "未申请";
				} else if (value == "issued") {
					displayField = "已开具";
				} else if (value == "reback") {
					displayField = "已退回";
				} else if (value == "inIssue") {
					displayField = "开具中";
				} else if (value == "notAccept") {
					displayField = "未受理";
				}
			}
			return displayField;
		}
	}],
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout:'column',
	    items: [{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.9
		},{
			xtype:'button',
			disabled:false,
			text:consumer.invoiceStatementManage.i18n('foss.stl.consumer.invoiceStatementManage.confirm'),
			columnWidth:.1,
			handler:consumer.invoiceStatementManage.statementDelete
		}]
	}]
});

//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', { 
  	clicksToEdit : 1,
  	isObservable : false
});

Ext.define('Foss.invoiceStatementManage.QueryEditParamsHiddenfield',{
	extend:'Ext.form.Panel',
	layout:'column',
	defaults:{
		readOnly:true
	},
	items:[{
    	xtype:'textfield',
    	fieldLabel:'queryTypeParam',
    	columnWidth:.2,
    	name:'queryTypeParam'
    }]
});



/**
 * 修改对账单页面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建对账单查询tab
	var statementQueryInfoTab = Ext.create('Foss.invoiceStatementManage.QueryInfoTab');
	//创建查询隐藏域
	var queryEditHiddenfield =Ext.create('Foss.invoiceStatementManage.QueryEditParamsHiddenfield',{hidden:true});
	//创建对账单查询表格
	var statementQueryGrid = Ext.create('Foss.invoiceStatementManage.BillDetails',{hidden:true});
	var receivableDetailGrid = Ext.create('Foss.invoiceStatementManage.ReceivableDetailGrid',{hidden:true});
	//创建对账单查询panel	
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-invoiceStatementManage_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
			return statementQueryInfoTab;
		},
		getStatementQueryGrid:function(){
			return statementQueryGrid;
		},
		getHiddenField:function(){
			return queryEditHiddenfield;
		},
		getReceivableDetailGrid : function() {
			return receivableDetailGrid;
		},
		items: [statementQueryInfoTab,statementQueryGrid,receivableDetailGrid],
		renderTo: 'T_consumer-invoiceStatementManage-body'
	});
});





