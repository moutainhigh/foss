//默认时间跨度
pay.onlineMonitorReport.PAYABLE_TWODAYS = 2;
pay.onlineMonitorReport.PAYABLE_ONEDAYS = 1;
pay.onlineMonitorReport.pageSize = 20;//分页参数
pay.onlineMonitorReport.maximumSize = 100;//最大分页条数限制

/**
 * 重置
 */
pay.onlineMonitorReport.reset = function(){
	this.up('form').getForm().reset();
}

/** 
 * @param {} billNos  要分割的单号集合
 * @return [] 分割后的数组
 */
pay.onlineMonitorReport.splitBillNos = function(billNos,limit){
	//声明array
	var array = [];
	//判断传入数据是否为空
	if(Ext.String.trim(billNos)!=null && Ext.String.trim(billNos)!=''){
		//继续分割
		array = stl.splitToArray(billNos);
		//踢出空字符串
		 for(var i=0;i<array.length;i++){
		 	if(Ext.String.trim(array[i])==''){
		 		array.pop(array[i]);
		 	}
		 }
		 //判断传入单号个数
		if(array.length>limit){
			return false;
		}	 	 
	}else{
		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.nosNotNull'));
		return false;
	}
	//返回分割数组
	return array;
}

/**
 * 查询成功
 */
pay.onlineMonitorReport.querySuccess = function(){
	//获取grid
	var grid = Ext.getCmp('T_pay-onlineMonitorReport_content').getGrid();
	//查询后台
	grid.store.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = grid.store.proxy.reader.rawData;
			if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),rawData.message);
				return false;
			}
			if(success){
				var result = Ext.decode(operation.response.responseText);  
				if(!Ext.isEmpty(result.vo.resultDto.list)&& result.vo.resultDto.list.length>0){
		      		var tbars = grid.dockedItems.items[3].query('textfield');
		      		tbars[1].setValue(result.vo.resultDto.totalCount);
					tbars[2].setValue(result.vo.resultDto.totalAmount);
					tbars[3].setValue(result.vo.resultDto.onlionPayTotalAmount);
					grid.show();		      	
		      	}
			}
	    }
	});
}

/**
 * 按日期查询 
 */
pay.onlineMonitorReport.queryByDate = function(){
	//获取按日期查询form
	var form = this.up('form').getForm();
	
	//判断是否合法
	if(form.isValid()){
		
		//校验日期是否正确
		var businessEndDate = form.findField('businessEndDate').getValue();
		var businessBeginDate = form.findField('businessBeginDate').getValue();
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(businessBeginDate,businessEndDate);
		//校验日期
		if(compareTwoDate>pay.onlineMonitorReport.PAYABLE_TWODAYS){
			Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//设置查询条件
		pay.onlineMonitorReport.businessBeginDate = form.findField('businessBeginDate').getValue();
		pay.onlineMonitorReport.businessEndDate = form.findField('businessEndDate').getValue();
		pay.onlineMonitorReport.deptCode = form.findField('deptCode').getValue();
		pay.onlineMonitorReport.billtype = form.findField('billtype').getValue();
		pay.onlineMonitorReport.payState = form.findField('payState').getValue();
		pay.onlineMonitorReport.refundState = form.findField('refundState').getValue();
		//设置查询页面方式
		pay.onlineMonitorReport.searchType = form.getValues().queryDateType;
		//去后台查询
		pay.onlineMonitorReport.querySuccess();
	}else{
		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 按运单号查询 
 */
pay.onlineMonitorReport.queryByWaybillNo = function(){
	//获取按单号查询界面
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		var billNosStr= form.findField('billNos').getValue();
		var billNos = pay.onlineMonitorReport.splitBillNos(billNosStr,stl.BILL_NOS_MAX);
		if(!billNos){
			Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.queryNosLimit'));
			return false;
		}
		//将分割数组付给变量
		pay.onlineMonitorReport.billNumber = billNos;
		//设置查询页面方式
		pay.onlineMonitorReport.searchType ='3'

		//去后台查询
		pay.onlineMonitorReport.querySuccess();
	}else{
		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 按对账单号查询  
 */
pay.onlineMonitorReport.queryByStatementNo = function(){
	//获取按日期查询form
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		var billNosStr= form.findField('billNos').getValue();
		var billNos = pay.onlineMonitorReport.splitBillNos(billNosStr,stl.BILL_NOS_MAX);
		//如果错误，则提示
		if(!billNos){
			Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.queryNosLimit'));
			return false;
		}
		//将分割数组付给变量
		pay.onlineMonitorReport.accoutNumber = billNos;
		//设置查询页面方式
		pay.onlineMonitorReport.searchType = '4';
		//去后台查询
		pay.onlineMonitorReport.querySuccess();
	}else{
		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 导出
 */
pay.onlineMonitorReport.exportReport = function(){
	var	columns,
		searchParams,
		grid;
	//获取表格
	grid = Ext.getCmp('T_pay-onlineMonitorReport_content').getGrid();
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.noDataToExport'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
			
	//按客户查询
	if(pay.onlineMonitorReport.searchType=='1'||pay.onlineMonitorReport.searchType=='2'){
		searchParams = {
			'vo.dto.startDate':pay.onlineMonitorReport.businessBeginDate,	
			'vo.dto.endDate':pay.onlineMonitorReport.businessEndDate,	
			'vo.dto.deptCode':pay.onlineMonitorReport.deptCode,	
			'vo.dto.billtype':pay.onlineMonitorReport.billtype	,	
			'vo.dto.payState':pay.onlineMonitorReport.payState,	
			'vo.dto.refundState':pay.onlineMonitorReport.refundState,
			'vo.dto.searchType':pay.onlineMonitorReport.searchType
		};
	}else if(pay.onlineMonitorReport.searchType=='3'){
		searchParams = {
			'vo.dto.billNumber':pay.onlineMonitorReport.billNumber,	
			'vo.dto.searchType':pay.onlineMonitorReport.searchType
		}
	}else{
		searchParams = {
			'vo.dto.accoutNumber':pay.onlineMonitorReport.accoutNumber,	
			'vo.dto.searchType':pay.onlineMonitorReport.searchType
		}
	}
	
	//拼接vo，注入到后台
	Ext.Ajax.request({
	    url: pay.realPath('exportOnlionMonitorReport.action'),
	    form: Ext.fly('downloadAttachFileForm'),
	    method : 'post',
	    params :searchParams,
	    isUpload: true,
	    success : function(response){
	    	var result = Ext.decode(response.responseText);
	    	//如果异常信息有值，则弹出提示
	    	if(!Ext.isEmpty(result.errorMessage)){
	    		Ext.Msg.alert(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),result.errorMessage);
	    		return false;
	    	}
			Ext.ux.Toast.msg(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.exportSuccess'), 'success', 1000);
	    },
	    failure : function(response){
			Ext.ux.Toast.msg(pay.onlineMonitorReport.i18n('foss.stl.pay.common.alert'),pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.exportFailed'), 'error', 1000);
	    }
	});
}

/**
 *	支付类型
 */
Ext.define('Foss.onlineMonitorReport.PayType',{
	extend:'Ext.data.Store',
	fields:['valueCode','valueName'],
	data:[
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.all'),valueCode:'2'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.receivebill'),valueCode:'0'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.statementbill'),valueCode:'1'}
	]
});

/**
 *	支付状态
 */
Ext.define('Foss.onlineMonitorReport.PayStatus',{
	extend:'Ext.data.Store',
	fields:['valueCode','valueName'],
	data:[
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.all'),valueCode:'4'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.paySuccess'),valueCode:'2'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.payFailed'),valueCode:'0'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.unPay'),valueCode:'1'}
	]
});

/**
 *	核销状态
 */
Ext.define('Foss.onlineMonitorReport.VerifyStatus',{
	extend:'Ext.data.Store',
	fields:['valueCode','valueName'],
	data:[
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.all'),valueCode:'3'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffed'),valueCode:'1'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffFailed'),valueCode:'2'},
		{valueName:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.unWriteoff'),valueCode:'0'}
	]
});

/**
 * form的model
 */
Ext.define('Foss.onlineMonitorReport.GridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'number'
	},{
		name:'payNumber'
	},{
		name:'productCode'
	},{
		name:'businessDivision'
	},{
		name:'largeArea'
	},{
		name:'smallArea'
	},{
		name:'deptName'
	},{
		name:'custNumber'
	},{
		name:'custName'
	},{
		name:'userName'
	},{
		name:'totalAmount',
		type:'double'
	},{
		name:'onlinePayAmount',
		type:'double'
	},{
		name:'payType'
	},{
		name:'payStatus'
	},{
		name:'onlinePayTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'verifyStatus'
	},{
		name:'verifyTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'paymentType'
	},{
		name:'isOnway'
	}]
});

/**
 * 表格store
 */
Ext.define('Foss.onlineMonitorReport.GridStroe',{
	extend:'Ext.data.Store',
	model:'Foss.onlineMonitorReport.GridModel',
	proxy:{
		type:'ajax',
		url:pay.realPath('queryOnlionMointorList.action'),
		actionMethods:'post',
		pageSize:pay.onlineMonitorReport.pageSize,
		reader:{
		  	type:'json',
			root:'vo.resultDto.list',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;			
			//如果查询类型为空，则表示用户直接选择了分页查询，没有点击查询按钮。此处提示用户点击查询按钮进行查询
			if(Ext.isEmpty(pay.onlineMonitorReport.searchType)){
				//获取查询条件
				var form = Ext.getCmp('T_pay-onlineMonitorReport_content').getQueryInfoTab().getQueryByDateForm().getForm();
				//设置查询条件
				pay.onlineMonitorReport.businessBeginDate = form.findField('businessBeginDate').getValue();
				pay.onlineMonitorReport.businessEndDate = form.findField('businessEndDate').getValue();
				pay.onlineMonitorReport.deptCode = form.findField('deptCode').getValue();
				pay.onlineMonitorReport.billtype = form.findField('billtype').getValue();
				pay.onlineMonitorReport.payState = form.findField('payState').getValue();
				pay.onlineMonitorReport.refundState = form.findField('refundState').getValue();
				//设置查询页面方式
				pay.onlineMonitorReport.searchType = form.getValues().queryDateType;
			}
			//按支付日期、核销日期查询
			if(pay.onlineMonitorReport.searchType=='1'||pay.onlineMonitorReport.searchType=='2'){
				searchParams = {
					'vo.dto.startDate':pay.onlineMonitorReport.businessBeginDate,	
					'vo.dto.endDate':pay.onlineMonitorReport.businessEndDate,	
					'vo.dto.deptCode':pay.onlineMonitorReport.deptCode,	
					'vo.dto.billtype':pay.onlineMonitorReport.billtype	,	
					'vo.dto.payState':pay.onlineMonitorReport.payState,	
					'vo.dto.refundState':pay.onlineMonitorReport.refundState,
					'vo.dto.searchType':pay.onlineMonitorReport.searchType
				};
			//按运单查询
			}else if(pay.onlineMonitorReport.searchType=='3'){
				searchParams = {
					'vo.dto.billNumber':pay.onlineMonitorReport.billNumber,	
					'vo.dto.searchType':pay.onlineMonitorReport.searchType
				}
				//按对账单查询
			}else{
				searchParams = {
					'vo.dto.accoutNumber':pay.onlineMonitorReport.accoutNumber,	
					'vo.dto.searchType':pay.onlineMonitorReport.searchType
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

/**
 * 查询tab
 */
Ext.define('Foss.onlineMonitorReport.QueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab:0,//默认显示按单号制作
	height:180,
    items: [{
      	title: pay.onlineMonitorReport.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig: {
			width: 120
		},
		layout: 'hbox',
        items:[{
	    	xtype:'form',
	    	width:850,
	    	defaults:{
        		labelWidth:75,
        		margin:'5,5,5,5'
        	},
			layout:'column',
		    items:[{
	            xtype:'fieldcontainer',
	            columnWidth:1,
	            defaultType: 'radiofield',
	            layout: 'column',
	            items:[{
                    boxLabel  : pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.payDate'),
                    name      : 'queryDateType',
                    checked:true,
                    inputValue: '1',
                    columnWidth: .2
                },{
                    boxLabel  : pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.verifyDate'),
                    name      : 'queryDateType',
                    inputValue: '2',
                    columnWidth: .2
                }]
	        },{
	        	xtype:'datefield',
	        	allowBlank:false,
	        	fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.startDate'),
	        	name:'businessBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-pay.onlineMonitorReport.PAYABLE_ONEDAYS)
	        },{
	        	xtype:'dynamicorgcombselector',
	        	name:'deptCode',
	        	valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
	        	fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.common.payableOrgName'),
	        	columnWidth:.3
	        },{
	        	xtype:'combo',
	        	name:'billtype',
	        	fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.billtype'),
	        	columnWidth:.3,
	        	editable:false,
				store:Ext.create('Foss.onlineMonitorReport.PayType'),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'2'					
	        },{
	        	xtype:'datefield',
	        	fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.endDate'),
	        	allowBlank:false,
	        	name:'businessEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	value:new Date()
	        },{
	        	xtype:'combo',
	        	name:'payState',
	        	fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.common.payStatus'),
	        	columnWidth:.3,
	       		editable:false,
				store:Ext.create('Foss.onlineMonitorReport.PayStatus'),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'4'	
        	},{
	        	xtype:'combo',
	        	name:'refundState',
				fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffStatus'),
	        	columnWidth:.3,
	        	editable:false,
				store:Ext.create('Foss.onlineMonitorReport.VerifyStatus'),
				queryModel:'local', 
				displayField:'valueName',
				valueField:'valueCode',
				value:'3'
	        },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.reset'),
					columnWidth:.1,
					handler:pay.onlineMonitorReport.reset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:pay.onlineMonitorReport.queryByDate
				}]
        	}]
	 	}]
	},{
        title: pay.onlineMonitorReport.i18n('foss.stl.pay.common.queryByNo'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
	    	defaults:{
	        	margin:'5 0 5 5'
	   		 },
	    	layout:'column',
		    items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.common.waybillNo'),
	    		emptyText:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.billNoEmptyText'),
	    		name:'billNos',
	    		allowBlank:false,
	    		columnWidth:.7,
	    		labelWidth:70,
	    		width: 600,
	    		height:81,
	    		regex:/^([0-9]{7,14}[;,])*([0-9]{7,14}[;,]?)$/i,
				regexText:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.waybillNoRegexTextWarning')
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.reset'),
					columnWidth:.08,
					handler:pay.onlineMonitorReport.reset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.54
				},{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:pay.onlineMonitorReport.queryByWaybillNo
				}]
        	}]
	    }]
    },{
        title: pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.queryByStatementNo'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
	    	defaults:{
	        	margin:'5 0 5 5'
	   		 },
	    	layout:'column',
		    items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.statementNo'),
	    		emptyText:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.billNoEmptyText'),
	    		name:'billNos',
	    		allowBlank:false,
	    		columnWidth:.7,
	    		labelWidth:70,
	    		width: 600,
	    		height:81,
	    		regex:/^(DZ[0-9]{7,10}[;,])*(DZ[0-9]{7,10}[;,]?)$/i,
				regexText:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.statementNoRegexTextWarning')
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.reset'),
					columnWidth:.08,
					handler:pay.onlineMonitorReport.reset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.54
				},{
					text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:pay.onlineMonitorReport.queryByStatementNo
				}]
        	}]
	    }]
    }]
});

/**
 * 在线支付明细列表
 */
Ext.define('Foss.onlineMonitorReport.Grid', {
	extend:'Ext.grid.Panel',
	title:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.onlinePayEntry'),
   // hidden:true,
    frame:true,
    height:500,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    store:Ext.create('Foss.onlineMonitorReport.GridStroe'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    emptyText:pay.onlineMonitorReport.i18n('foss.stl.pay.common.noResult'),
  	defaults:{
  		align:'center',
  		margin:'5 0 5 0'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.number'),
    	dataIndex:'number'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.queryBillPayable.productCode'),
    	dataIndex:'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.payNumber'),
    	dataIndex:'payNumber'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.businessDivision'),
    	dataIndex:'businessDivision'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.largeArea'),
    	dataIndex:'largeArea'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.smallArea'),
    	dataIndex:'smallArea'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.deptName'),
    	dataIndex:'deptName'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.common.customerCode'),
    	dataIndex:'custNumber'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.common.customerName'),
    	dataIndex:'custName'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.userName'),
    	dataIndex:'userName'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.totalAmount'),
    	dataIndex:'totalAmount'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.onlinePayAmount'),
    	dataIndex:'onlinePayAmount'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.billtype'),
    	dataIndex:'payType',
    	renderer:function(value){
    		if(value=='0'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.receivebill');
    		}else if(value =='1'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.statementbill');
    		}
    	}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.common.payStatus'),
    	dataIndex:'payStatus',
    	renderer:function(value){
    		if(value=='0'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.payFailed');
    		}else if(value =='1'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.unPay');
    		}else if(value=='2'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.paySuccess');
    		}
    	}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.onlinePayTime'),
    	dataIndex:'onlinePayTime',
    	renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffStatus'),
    	dataIndex:'verifyStatus',
    	renderer:function(value){
    		if(value=='0'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.unWriteoff');
    		}else if(value =='2'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffFailed');
    		}else if(value=='1'){
    			return pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.writeoffed');
    		}
    	}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.verifyTime'),
    	dataIndex:'verifyTime',
    	renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.paymentType'),
    	dataIndex:'paymentType'
    },{
    	header:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.isOnway'),
    	dataIndex:'isOnway'
    }],
    initComponent:function(){
		var me = this;
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
				text:pay.onlineMonitorReport.i18n('foss.stl.pay.common.export'),
				columnWidth:.09,
				handler:pay.onlineMonitorReport.exportReport
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'textfield',
				readOnly:true,
				name:'total',
				labelSeparator:'',
				columnWidth:.1,
				labelWidth:40,
				fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'total',
				columnWidth:.2,
				labelWidth:60,
				fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				name:'totalAmount',
				columnWidth:.2,
				labelWidth:80,
				fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.totalAmount')
			},{
				xtype:'textfield',
				readOnly:true,
				name:'totalUnVerifyAmount',
				columnWidth:.2,
				labelWidth:90,
				fieldLabel:pay.onlineMonitorReport.i18n('foss.stl.pay.onlineMonitorReport.onlinePayAmount')
			},{
				xtype:'standardpaging',
				store:me.store,
				pageSize:pay.onlineMonitorReport.pageSize,
				columnWidth:1,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					maximumSize:pay.onlineMonitorReport.maximumSize,
					sizeList: [['5',5],['20', 20], ['30', 30], ['50', 50], ['100', 100]]
				})
			 }]
   		 }];
   		 me.callParent();
    }
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建查询tab
	var queryInfoTab = Ext.create('Foss.onlineMonitorReport.QueryInfoTab',{
		//获取按日期查询form
		getQueryByDateForm:function(){
			return this.items.items[0].items.items[0];
		},
		//获取按运单号查询form
		getQueryByWayBillNosForm:function(){
			return this.items.items[1].items.items[0];
		},
		//获取按对账单号查询form
		getQueryByStatementNosForm:function(){
			return this.items.items[2].items.items[0];
		}
	});
	//创建分页grid
	var grid = Ext.create('Foss.onlineMonitorReport.Grid');
	//创建整个页面
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-onlineMonitorReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//获取查询tab方法
		getQueryInfoTab:function(){
			return queryInfoTab;
		},
		//获取grid方法
		getGrid:function(){
			return grid;
		},
		items: [queryInfoTab,grid],
		renderTo: 'T_pay-onlineMonitorReport-body'
	});
});