pay.rentCarForPayReportDetail.QUERY_BY_DATE = "TD";//按日期查询
pay.rentCarForPayReportDetail.QUERY_BY_RENTCAR_NO = "RCB";//按租车编号
pay.rentCarForPayReportDetail.QUERY_BY_BUSINESS_NO = "BN";//按业务单号
pay.rentCarForPayReportDetail.QUERY_BY_WORKFLOW_NO = "WO";//按工作流号
pay.rentCarForPayReportDetail.QUERY_MAXDATE = 10;//报表查询时间范围
pay.rentCarForPayReportDetail.RADIO_QUERY_BY_USE_TIME = "UT";//按用车日期查询
pay.rentCarForPayReportDetail.RADIO_QUERY_BY_CREATE_TIME = "CT";//按单据生成日期查询


/**
 * 导出报表
 */
pay.rentCarForPayReportDetail.payForBillExport = function() {

	// 获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_pay-rentCarForPayReportDetail_content');
	var queryGrid = mainPane.getGrid();
	/**
	 * 导出
	 */
	if (queryGrid.store.data.length == 0) {
		Ext.Msg.alert('温馨提示', '表格没有数据，不能进行导出操作！');
		return false;
	}

	// 提示是否导出
	Ext.Msg.confirm('温馨提示', '确定要导出报表?', function(btn, text) {
		if ('yes' == btn) {
			//转化列头和列明
				columns = queryGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//声明单据父类型、单据子类型
				var billParentType,billType;
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if(columns[i].text!=pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
			var params = pay.rentCarForPayReportDetail.setQueryParams();
			var vo = new Object();
			for(var p in params){
				var aa = 'vo.rentCarReportDetailDto.'+p;
				vo[aa]=params[p];
			}

			// 创建一个form
			if (!Ext.fly('exportMvrRfiForm')) {
				var frm = document.createElement('form');
				frm.id = 'exportMvrRfiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}

			// 导出Ajax请求
			Ext.Ajax.request({
				url : pay.realPath('exportQueryRentCarReportDetail.action'),
				form : Ext.fly('exportMvrRfiForm'),
			    params : {
	    	                 'vo.rentCarReportDetailDto.queryType': vo['vo.rentCarReportDetailDto.queryType'],
	    	                 'vo.rentCarReportDetailDto.billNos': vo['vo.rentCarReportDetailDto.billNos'],
	    	                 'vo.rentCarReportDetailDto.payWorkFlowNo': vo['vo.rentCarReportDetailDto.payWorkFlowNo'],
	    	                 'vo.rentCarReportDetailDto.vehicleNo': vo['vo.rentCarReportDetailDto.vehicleNo'],
	    	                 'vo.rentCarReportDetailDto.useCarDate': vo['vo.rentCarReportDetailDto.useCarDate'],
	    	                 'vo.rentCarReportDetailDto.createTime': vo['vo.rentCarReportDetailDto.createTime'],
	    	                 'vo.rentCarReportDetailDto.queryDate': vo['vo.rentCarReportDetailDto.queryDate'],
	    	                 'vo.rentCarReportDetailDto.queryDateType': vo['vo.rentCarReportDetailDto.queryDateType'],
	    	                 'vo.arrayColumnNames':arrayColumnNames,
	    	                 'vo.arrayColumns':arrayColumns
	            },
				method : 'post',
				isUpload : true,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					// 如果异常信息有值，则弹出提示
					if (!Ext.isEmpty(result.errorMessage)) {
						Ext.Msg.alert('温馨提示', result.errorMessage);
						return false;
					}
					Ext.ux.Toast.msg('温馨提示', '导出成功！', 'success', 1000);
				},
				failure : function(response) {
					Ext.ux.Toast.msg('温馨提示', '导出失败！', 'error', 5000);
				}
			});

		}
	});
}
/**
 * 设置查询参数
 */
pay.rentCarForPayReportDetail.setQueryParams = function(){
	//判断是否进行过查询
	if(Ext.isEmpty(pay.rentCarForPayReportDetail.queryType)){
		return false;
	}
	//copy一份值，不然此处分页会有问题
	var searchParams = Ext.clone(pay.rentCarForPayReportDetail.searchParams);
	searchParams.queryType = pay.rentCarForPayReportDetail.queryType;
	return searchParams;
}

/**
 * 查询
 */
pay.rentCarForPayReportDetail.query = function(me){
	var form  = me.up('form').getForm();
	if(form.isValid()){
		//获取查询参数
		pay.rentCarForPayReportDetail.searchParams = form.getValues();
		//按日期查询
		if (!(pay.rentCarForPayReportDetail.QUERY_BY_DATE ==pay.rentCarForPayReportDetail.queryType)){
			var billNos = pay.rentCarForPayReportDetail.searchParams.billNos;
			var billNosArray_tmp = stl.splitToArray(billNos);
			var billNosArray=new Array();
			for(var i=0;i<billNosArray_tmp.length;i++){
				if(billNosArray_tmp[i].trim()!=''){
					billNosArray.push(billNosArray_tmp[i].trim());
				} 
			}
			 
			if(billNosArray.length==0){
				Ext.Msg.alert(pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayment.billNosNotInputAlert'));
			 	return false;
			}
			if(billNosArray.length>10){
				Ext.Msg.alert(pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayment.billNosNotInputAlert'));
			 	return false;
			}
			pay.rentCarForPayReportDetail.searchParams.billNos = billNosArray;
		}
		var grid = pay.rentCarForPayReportDetail.reportGrid;
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.alert'),rawData.message);
				me.enable(true);
				return false;
			}  
	    	//正常返回
	    	if(success){
	    		var result = Ext.decode(operation.response.responseText);
	    		Ext.getCmp('totalCount').setValue(result.vo.rentCarReportDetailDto.totalCount);
	    	 }
	       }
		}); 
	}else{
		Ext.Msg.alert(pay.rentCarForPayReport.i18n('foss.stl.pay.common.alert'),pay.rentCarForPayReport.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 重置
 */
pay.rentCarForPayReportDetail.reset = function(){
	this.up('form').getForm().reset();
}

/**
 * 临时租车报表model
 */
Ext.define('Foss.rentCarForPayReportDetail.GridModel',{
	extend:'Ext.data.Model',
	fields:[
	    {name:'waybillNo',type:'string'},
		{name:'billNo',type:'string'},
		{name:'rentCarNo',type:'string'},
		{name:'payableNo',type:'string'},
		{name:'payWorkFlowNo',type:'string'},
		{name:'weight',type:'double'},
		{name:'volume',type:'double'},
		{name:'createTime',type:'Date',
		  convert:function(value) {
				return stl.longToDateConvert(value);
		   }
		},
		{name:'billType',type:'string'},	
		{name:'vehicleNo',type:'string'},
		{name:'driverName',type:'string'},
		{name:'useCarDate',type:'Date',
		  convert:function(value) {
				return stl.longToDateConvert(value);
		   }
		},
		{name:'rentCarUseType',type:'string'}
		]
	});
	
/**
 * 付款报表查询界面
 */
Ext.define('Foss.rentCarForPayReportDetail.GridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.rentCarForPayReportDetail.GridModel',
	proxy : {
		type : 'ajax',
		url : pay.realPath('queryRentCarReportDetail.action'),
		reader : {
			type : 'json',
			root : 'vo.rentCarReportDetailDtoList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeLoad':function(store, operation, eOpts){
			var params = pay.rentCarForPayReportDetail.setQueryParams();
			var vo = new Object();
			for(var p in params){
				var aa = 'vo.rentCarReportDetailDto.'+p;
				vo[aa]=params[p];
			}
			Ext.apply(operation, {
				params: vo
			});
		}
	}
});

/**
 * 临时租车查询
 */
Ext.define('Foss.rentCarForPayReportDetail.QueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,// 默认显示按单号制作
	height : 210,
	items : [{
		title : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig : {
			width : 120
		},
		layout : 'hbox',
		items : [{
			xtype : 'form',
			width : 850,
			margin : '10,5,5,5',
			defaults : {
				labelWidth : 85,
				margin : '5,5,5,5'
			},
			layout : 'column',
			items : [{
				xtype : 'fieldcontainer',
				defaultType : 'radiofield',
				columnWidth : 1,
				layout : 'hbox',
				items : [{
						boxLabel : pay.rentCarForPayReportDetail
								.i18n('foss.stl.pay.rentCarForPayReport.queryByUseCarDate'),
						checked : true,
						name : 'queryDateType',
						inputValue :pay.rentCarForPayReportDetail.RADIO_QUERY_BY_USE_TIME,// 表单的参数值
						width : 120
					},{
						boxLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.queryByBillTime'),
						name : 'queryDateType',
						inputValue :pay.rentCarForPayReportDetail.RADIO_QUERY_BY_CREATE_TIME,// 表单的参数值
						width : 130
					}]
				},{
					xtype : 'datefield',
					allowBlank : false,
					fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.queryDate'),
					labelWidth : 85,
					name : 'queryDate',
					allowBlank:false,
					columnWidth : .3,
					format : 'Y-m-d',
					value : new Date()
				},{
					xtype:'commonleasedvehicleselector',
					name : 'vehicleNo',
					isPaging : true,// 分页
					fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
					columnWidth : .3
				},{
			    	xtype: 'dynamicorgcombselector',
					name:'division',
					division:'Y',
			        fieldLabel: pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.division'),
					listWidth:300,//设置下拉框宽度
					isPaging:true, //分页
					columnWidth : .3
		    	},{			
					xtype : 'linkagecomboselector',
					eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					itemId : 'Foss_baseinfo_BigRegion_ID',
					store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
					fieldLabel :pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.largeRegion'),
					value:'',
					minChars : 0,
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					name : 'bigArea',
					allowBlank : true,
					listWidth : 300,// 设置下拉框宽度
					isPaging : true,
					columnWidth : .3,
					queryParam : 'commonOrgVo.name'
			    },{	    	
			    	xtype : 'linkagecomboselector',
					itemId : 'Foss_baseinfo_SmallRegion_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
					name : 'smallArea',
					fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.smallRegion'),
					parentParamsAndItemIds : {
						'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
					},// 此处城市不需要传入
					minChars : 0,
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					allowBlank : true,
					listWidth : 300,// 设置下拉框宽度
					isPaging : true,
					columnWidth : .3,
					queryParam : 'commonOrgVo.name'
		    	},{
			    	xtype: 'dynamicorgcombselector',
					name:'rentCarDeptCode',
			        fieldLabel: pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.dept'),
			        allowblank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true, //分页
		    		columnWidth : .3
		    	},{
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [{
						text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.reset'),
						columnWidth : .1,
						handler : pay.rentCarForPayReportDetail.reset
					}, {
						xtype : 'container',
						border : false,
						html : '&nbsp;',
						columnWidth : .7
					}, {
						text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.query'),
						cls : 'yellow_button',
						columnWidth : .1,
						handler : function() {
							var form = this.up('form').getForm();
							var me = this;
							pay.rentCarForPayReportDetail.queryType = pay.rentCarForPayReportDetail.QUERY_BY_DATE;
							pay.rentCarForPayReportDetail.query(me);
						}
					}]
			}]
		}]
	}, {
		title : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.queryByCarNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 115
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReportDetail.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReportDetail.queryType = pay.rentCarForPayReportDetail.QUERY_BY_RENTCAR_NO;
						pay.rentCarForPayReportDetail.query(me);
					}
				}]
			}]
		}]
	},{
		title : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.queryByBusinessNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 115
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReportDetail.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReportDetail.queryType = pay.rentCarForPayReportDetail.QUERY_BY_BUSINESS_NO;
						pay.rentCarForPayReportDetail.query(me);
					}
				}]
			}]
		}]
	},{
		title : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.queryByWorkflowNo'),
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '5 0 5 5'
			},
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNo'),
				emptyText : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
				name : 'billNos',
				allowBlank : false,
				columnWidth : .7,
				labelWidth : 70,
				width : 600,
				height : 115
			}, {
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaultType : 'button',
				defaults : {
					width : 80
				},
				items : [{
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.reset'),
					columnWidth : .08,
					handler : pay.rentCarForPayReportDetail.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .54
				}, {
					text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						pay.rentCarForPayReportDetail.queryType = pay.rentCarForPayReportDetail.QUERY_BY_WORKFLOW_NO;
						pay.rentCarForPayReportDetail.query(me);
					}
				}]
			}]
		}]
	}]
});
Ext.define('Foss.rentCarForPayReportDetail.FormDetail',{
		extend:'Ext.form.Panel',	
		frame:true,
	    layout:'column',
	    defaultType:'textfield',
	    layout:'column',
	    defaults:{
		labelWidth:60,
		margin:'5 10 5 30',
		readOnly:true
	},
	items:[{
		        xtype : 'textfield',
				readOnly : true,
				name : 'billNo',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.waybillNo')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'rentCarNo',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.rentCarNo')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'payableNo',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.payableNo')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'payWorkFlowNo',
				columnWidth : .5,
				labelWidth : 120,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.payWorkFlowNo')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'weight',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.weight')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'volume',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.volume')
		},{
		        xtype : 'datefield',
				readOnly : true,
				name : 'createTime',
				format:'Y-m-d H:i:s',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.createTime')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'billType',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.billType')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'vehicleNo',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.ofvpaymentRpt.vehicleNo')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'driverName',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.driverName')
		},{
		        xtype : 'datefield',
				readOnly : true,
				name : 'useCarDate',
				format:'Y-m-d H:i:s',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.useCarDate')
		},{
		        xtype : 'textfield',
				readOnly : true,
				name : 'rentCarUseType',
				columnWidth : .5,
				labelWidth : 90,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType')
		}]
	});

/**
 * 定义一个租车明细的窗口
 */
Ext.define('Foss.rentCarForPayReportDetail.EntryWindow',{
	title:"租车明细的窗口",
	extend:'Ext.window.Window',
	width:600,
    height:270,
	modal:true,
	layout: 'fit',
	constrainHeader: true,
	closeAction:'hide',
	items:[Ext.create('Foss.rentCarForPayReportDetail.FormDetail')]
});
/**
 * 临时租车付款报表查询结果列表
 */
Ext.define('Foss.rentCarForPayReportDetail.reportGrid', {
	extend : 'Ext.grid.Panel',
	title : pay.rentCarForPayReportDetail
			.i18n('foss.stl.pay.rentCarForPayReportDetail.detailList'),
	frame : true,
	height : 500,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	store : Ext.create('Foss.rentCarForPayReportDetail.GridStore'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	defaults : {
		align : 'center',
		margin : '5 0 5 0'
	},
	
	viewConfig : {
		enableTextSelection : true
	},
	listeners:{
		'itemdblclick' :function(){
		   //初始化window
		   if (Ext.isEmpty(Foss.rentCarForPayReportDetail.RentCarForPayReportDetailWindow)){
		   		Foss.rentCarForPayReportDetail.RentCarForPayReportDetailWindow = Ext.create('Foss.rentCarForPayReportDetail.EntryWindow');
		   }
		   
		   var form = Foss.rentCarForPayReportDetail.RentCarForPayReportDetailWindow.down('form');
		   var selection=Ext.getCmp('T_pay-rentCarForPayReportDetail_content').getGrid().getSelectionModel().getSelection();
		   form.loadRecord(selection[0]);
		   var rentCarUseType = form.getForm().findField('rentCarUseType');
		   rentCarUseType.setValue(FossDataDictionary.rendererSubmitToDisplay(rentCarUseType.getValue(), 'RENTCAR_USE_TYPE'));
		   
		   Foss.rentCarForPayReportDetail.RentCarForPayReportDetailWindow.show();
		}
	},
	columns : [{
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.billNo'),
		dataIndex : 'billNo'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.rentCarNo'),
		dataIndex : 'rentCarNo'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.payableNo'),
		dataIndex : 'payableNo'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.payWorkFlowNo'),
		dataIndex : 'payWorkFlowNo'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.weight'),
		dataIndex : 'weight'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.volume'),
		dataIndex : 'volume'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.createTime'),
		dataIndex : 'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.billType'),
		dataIndex : 'billType'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.vehicleNo'),
		dataIndex : 'vehicleNo'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReportDetail.driverName'),
		dataIndex : 'driverName'
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.useCarDate'),
		dataIndex : 'useCarDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	}, {
		header : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.rentCarUseType'),
		dataIndex : 'rentCarUseType',
		renderer:function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RENTCAR_USE_TYPE');
			return displayField;
		}
	}],
	initComponent : function() {
		var me = this;
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'container',
				html : '&nbsp;',
				columnWidth : .91
			}, {
				xtype : 'button',
				text : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.common.export'),
				columnWidth : .09,
				handler : pay.rentCarForPayReportDetail.payForBillExport
			}]
		}, {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'textfield',
				readOnly : true,
				name : 'total',
				id:'total',
				columnWidth : .1,
				labelWidth : 40,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.queryBillPayable.total')
			}, {
				xtype : 'textfield',
				readOnly : true,
				name : 'totalCount',
				id:'totalCount',
				columnWidth : .2,
				labelWidth : 100,
				fieldLabel : pay.rentCarForPayReportDetail.i18n('foss.stl.pay.rentCarForPayReport.totalCount')
			}, {
				xtype : 'standardpaging',
				store : me.store,
				columnWidth : 1,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					// 设置分页记录最大值，防止输入过大的数值
					maximumSize : 1000,
					sizeList : [['20', 20], ['50', 50], ['100', 100]]
				}) 
			}]
		}];
		me.callParent();
	}
});

// 初始化界面
Ext.onReady(function() {
	pay.rentCarForPayReportDetail.queryTab= Ext.create('Foss.rentCarForPayReportDetail.QueryInfoTab');
	pay.rentCarForPayReportDetail.reportGrid = Ext.create('Foss.rentCarForPayReportDetail.reportGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-rentCarForPayReportDetail_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getGrid : function() {
			return pay.rentCarForPayReportDetail.reportGrid;
		},
		getForm : function() {
			return pay.rentCarForPayReportDetail.queryTab;
		},
		layout: 'auto',
		items:[pay.rentCarForPayReportDetail.queryTab,pay.rentCarForPayReportDetail.reportGrid],
		renderTo: 'T_pay-rentCarForPayReportDetail-body'
	});
});
