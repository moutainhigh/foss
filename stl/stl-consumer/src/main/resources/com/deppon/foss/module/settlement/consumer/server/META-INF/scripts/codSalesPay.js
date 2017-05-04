//由于平台正则BUG，为了加载message
consumer.codSalesPay.i18n('foss.stl.consumer.cod.WaybillNoCodCustomerCodeEmptyCannotChangeAccount');
/**
 * 
 * @param {日期}
 *            date
 * @param {小时}
 *            hours
 * @param {分钟}
 *            minutes
 * @param {秒}
 *            seconds
 * @param {微秒}
 *            milliseconds
 * @return {}
 */
consumer.codSalesPay.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

/**
 * 查询
 * @param {} thisForm
 */
consumer.codSalesPay.queryCodSalesPay=function(thisForm){
	// 得到Store
	var grid = Ext.getCmp('Foss_codSalesPay_CodSalesPayGrid_ID');
	var store = grid.getStore();
	if(store){
		if(grid.isHidden()){
			grid.show();
		}
		grid.getLoadMask().show();
		
		var CODPaidQueryForm = Ext.getCmp('Foss_codSalesPay_CodSalesPayQueryInfoTab_ID').getActiveTab().down('form');
		if (CODPaidQueryForm) {
			var form = CODPaidQueryForm.getForm();
			// 设置查询参数
			grid.setSubmitParams(form.getValues());
		}
		
		// 加载第一页数据
		store.loadPage(1,{
					callback : function(records, operation, success) {
						var rawData = store.proxy.reader.rawData;
						if (!success && !rawData.isException) {
							Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
							return false;
						}
					}
				});
		grid.getLoadMask().hide();
	}
}

/**
 * 出纳审核Action
 */
consumer.codSalesPay.cashierAuditAccountsAction = function(grid,jsonData){
	grid.getLoadMask().show();
	//审核
	Ext.Ajax.request({
		url:consumer.realPath('cashierAuditAccounts.action'),
		method:'post',
		params:{
			'salesPayCODVO.entityIds':jsonData
		},
		success:function(response){
			Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), consumer.codSalesPay.i18n('foss.stl.consumer.cod.auditSuccess'),'ok',1000);
			var store = grid.getStore();
			var selectionModel = grid.getSelectionModel();
			var rows= selectionModel.getSelection(); //获取所有选中行，
			// 审核成功，移除Gird中已审核数据
			for(var i=0;i<rows.length;i++){
				store.remove(rows[i]);
			}
			store.load();
			grid.getLoadMask().hide();
		},
		exception : function(response) {
		  var json = Ext.decode(response.responseText);
		  Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
		  grid.getLoadMask().hide();
		},
		failure:function(form,action){
			grid.getLoadMask().hide();
		},
		unknownException:function(form,action){
			grid.getLoadMask().hide();
		}				
	}); 
}

/**
 * 把查询的银行信息复制到 开户行窗口的Form上。
 */
consumer.codSalesPay.bankRecordCopyForm = function(me,record){
		
		// 先置空，置空需修改的信息
		me.getForm().findField('bankCode').setValue(null); // 银行行号
		me.getForm().findField('bank').setValue(null); // 开户银行
		me.getForm().findField('publicPrivateFlag').setValue(null); // 公私标志
		me.getForm().findField('payeeName').setValue(null); // 开户姓名
		me.getForm().findField('payeePhone').setValue(null); // 手机号码
		me.getForm().findField('payeeAccount').setValue(null); // 银行账号
		me.getForm().findField('provinceCode').setValue(null); // 开户行省份编码
		me.getForm().findField('cityCode').setValue(null); // 开户行城市编码
		me.getForm().findField('province').setValue(null); // 开户行省份
		me.getForm().findField('city').setValue(null); // 开户行城市
		me.getForm().findField('bankSubbranchCode').setValue(null); // 开户行支行编码
		me.getForm().findField('bankSubbranch').setValue(null); // 开户行支行
		me.getForm().findField('payeeAndConsignor').setValue(null); // 客户收款人关系
		
		//再赋值
		me.getForm().findField('bankCode').setValue(record.get('bankCode')); // 银行行号
		me.getForm().findField('bank').setValue(record.get('openingBankName')); // 开户银行
		var flag = record.get('accountNature');// 公私标志
		if(flag == consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__PUBLIC ){ //crm标志与结算转化 
			flag = consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__COMPANY;
		}else if(flag == consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__PRIVATE){
			flag = consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__RESIDENT;
		}
		me.getForm().findField('publicPrivateFlag').setValue(flag); // 公私标志
		me.getForm().findField('payeeName').setValue(record.get('accountName')); // 开户姓名
		me.getForm().findField('payeePhone').setValue(record.get('mobilePhone')); // 手机号码
		me.getForm().findField('payeeAccount').setValue(record.get('accountNo')); // 银行账号
		me.getForm().findField('provinceCode').setValue(record.get('provCode')); // 开户行省份编码
		me.getForm().findField('cityCode').setValue(record.get('cityCode')); // 开户行城市编码
		me.getForm().findField('province').setValue(record.get('provinceName')); // 开户行省份
		me.getForm().findField('city').setValue(record.get('cityName')); // 开户行城市
		me.getForm().findField('bankSubbranchCode').setValue(record.get('branchBankCode')); // 开户行支行编码
		me.getForm().findField('bankSubbranch').setValue(record.get('branchBankName')); // 开户行支行
		me.getForm().findField('payeeAndConsignor').setValue(record.get('customer')); // 客户收款人关系
}

/**
 * 代收货款状态model
 */
Ext.define('Foss.codSalesPay.RefundTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * 退款类型store
 */
Ext.define('Foss.codSalesPay.RefundTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.codSalesPay.RefundTypeModel',
	data:{
		'items':[
			{name:consumer.codSalesPay.i18n('foss.stl.consumer.cod.auditFailureOrRefund'),value:'AG,RF,SF'},
			{name:consumer.codSalesPay.i18n('foss.stl.consumer.cod.notRefund'),value:'NR'}
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
 * 账户性质store
 */
Ext.define('Foss.codSalesPay.AccountPropertiesStore',{
	extend:'Ext.data.Store',
	model:'Foss.codSalesPay.RefundTypeModel',
	data:{
		'items':[
			{name:'对公',value:consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__COMPANY},
			{name:'对私',value:consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__RESIDENT}
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
 * 代收货款信息model
 */
Ext.define('Foss.codSalesPay.CodSalesPayModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	/**
	 * extid,id,运单号,应付单编号,总金额,冲应收金额,应退金额,代收货款类型,代收货款状态
	 * 发货客户名称,收款人,应付单客户编码,应付单客户名称,应付部门,账号
	 * 开户行,发货客户与收款人关系,对公对私标志,省编码,市编码,省,市,支行,收款人电话
	 * 银行行号,业务日期,密码(用于页面控制修改账号成功红色提示)
	 */
	fields : ['extid', 'id', 'waybillNo', 'payableNo', 'amount',
					'verReceivableAmount', 'returnAmount', 'codType', 'status',
					'customerName', 'payeeName', 'payableCustomerCode',
					'payableCustomerName', 'payableOrgName', 'payeeAccount',
					'bank', 'payeeAndConsignor', 'publicPrivateFlag',
					'provinceCode', 'cityCode','province', 'city', 'bankSubbranchCode','bankSubbranch', 'payeePhone',
					'bankCode', {
						name : 'businessDate',
						type : 'date',
						convert : stl.longToDateConvert
					}, 'notes', {
						name : 'password',
						type : 'string',
						defaultValue : '0'
					}]
});

/**
 * 代收货款信息数据Store
 */
Ext.define('Foss.codSalesPay.CodSalesPayStore',{
	extend:'Ext.data.Store',
	model:'Foss.codSalesPay.CodSalesPayModel',
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods : 'post',
		url:consumer.realPath('querySalesPayCOD.action'),
		reader:{
			type:'json',
			root:'salesPayCODVO.cods',
			totalProperty:'salesPayCODVO.totalRecords'
		}
	}
});

/**
 * 代收货款出发申请Form
 */
Ext.define('Foss.codSalesPay.CodSalesPayCodStatusForm',{
	extend:'Ext.form.Panel',
	//title:'代收货款账号管理',
	frame:false,
	//collapsible: true,
	//animCollapse: true,
	height:180,
	layout : {
		type : 'column'
	},
	defaults : {
		//msgTarget : 'under',
		margin :'10 10 10 10',
		labelWidth :85,
		allowBlank : true
	},
	items:[{
		xtype:'container',
		columnWidth:1,
		layout:'column',
		items:[{
			xtype: 'combobox',
			name:'salesPayCODVO.status',
	        fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.codStatus'),//代收货款状态
			store:Ext.create('Foss.codSalesPay.RefundTypeStore'),
			queryModel:'local',
			value:'AG,RF,SF', // 默认类型
			displayField:'name',
			valueField:'value',
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.codStatus')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),
			editable:false,
	        columnWidth:.3
		},{
			xtype:'container',
			border:false,
			columnWidth:.2,
			height:40,
			html : '<span style="color:red;font-weight:bold">&nbsp;&nbsp;'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.modifyAccountSteps')//修改账号步骤
				+ ':<br/>&nbsp;&nbsp;1、'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.firstToCrmMaintenance')//先去CRM维护
				+ '<br/>&nbsp;&nbsp;2、'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.inTheInterfaceModificationAccount')//
				+ '</span>'
		},{//@author 218392 zhangyongxue 2015-11-02 10:25:36 DP-FOSS-代收货款账号查询优化
		   //在代收货款账号管理界面新增“收款方”和“业务日期”查询方式，可查询自己部门录入了代收货款账号的客户名称。
				xtype : 'fieldcontainer',
				labelWidth : 130,
				fieldLabel : '是否按业务日期查询',
				columnWidth : .50,
				layout : 'column',
				items:[{
						xtype : 'radio',
						name : 'salesPayCODVO.timeType',
						inputValue : 'yes',
						id:'querying_consumer_queryOtherRevenue_yes',
						checked : true,
						boxLabel : '是',
				        listeners: {
				            'change': function (me, newValue, oldValue, eOpts) {
			    				var bussinessTime_id = Ext.getCmp('consumer_queryConsumer_bussinessTime');
								if(newValue){
									bussinessTime_id.enable();
								}else{
									bussinessTime_id.disable();
								}
		                        bussinessTime_id.doComponentLayout();
			    			}
				        }
					},{
						xtype : 'radio',
						name : 'salesPayCODVO.timeType',
						inputValue : 'no',
						id:'querying_consumer_queryOtherRevenue_no',
						boxLabel : '否'
				}]
		},{ //@author 218392 zhangyongxue 2015-11-03 16:12:18
			xtype : 'rangeDateField',
			//name : 'salesPayCODVO.bussinessDate',
			id : 'consumer_queryConsumer_bussinessTime',
			fieldLabel :'业务日期(起)',   // 预计到达时间
			dateType : 'datetimefield_date97',
			fromName : 'salesPayCODVO.timeBegin',
			toName : 'salesPayCODVO.timeEnd',
			fromValue : Ext.Date.format(consumer.codSalesPay.getDate(
							new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
			toValue : Ext.Date.format(consumer.codSalesPay.getDate(
							new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
			columnWidth : .5,
			disallowBlank : false,
			editable : false
		}]
	},{
		//@author 218392 zhangyongxue 2015-11-02 11:14:19 收款方 引用FOSS综合开发组的公共选择器 在
		//bse-baseinfo 下面commomSelector.js中
		unifiedCode:FossUserContext.getCurrentUserDept().unifiedCode,
		xtype : 'cusAccountNameSelector',
		forceSelection : true,
		fieldLabel : '收款方',
		name : 'salesPayCODVO.cusAccountName',
		showContent : '{accountName}',//显示表格列
		labelWidth : 80,
		columnWidth : .3
	},{
		xtype:'container',
		columnWidth:1,
		border : false,
		layout:'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items:[{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.reset'),
			width:80,
			handler:function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().setValues({
					'salesPayCODVO.timeBegin' : Ext.Date.format(consumer.codSalesPay.getDate(
							new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
					'salesPayCODVO.timeEnd' : Ext.Date.format(consumer.codSalesPay.getDate(
							new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s')
				});
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:0.82
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.query'),
			width:118,
			cls:'yellow_button',
			handler:function(){
				if(this.up('form').getForm().isValid()){
					consumer.codSalesPay.queryCodSalesPay(this);
				}else{
					Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
			}
		}]
	}]
	
	
});

/**
 * 代收货款出发申请Form
 */
Ext.define('Foss.codSalesPay.CodSalesPayWaybillNoForm',{
	extend:'Ext.form.Panel',
	//title:'代收货款账号管理',
	frame:false,
	//collapsible: true,
	//animCollapse: true,
	height:180,
	layout : {
		type : 'column'
	},
	defaults : {
		//msgTarget : 'under',
		margin :'10 10 10 10',
		labelWidth :85,
		allowBlank : false
	},
	items:[{
        xtype:'textarea',
		name:'salesPayCODVO.waybillNos',
		fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.No'),
		emptyText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
		height:60,
		//regex:/^([0-9]{8,10},?){0,10}$/i,
		//354658-校验至14位运单号
		regex:/^([0-9]{8,14})(,[0-9]{8,14}){0,9},?$/i,
		regexText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
		blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.NoCannotEmpty'),
		columnWidth:.5
	},{ 
		xtype:'container',
		border:false,
		columnWidth:.2,
		height:80,
		html : '<span style="color:red;font-weight:bold">&nbsp;&nbsp;'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.modifyAccountSteps')
				+ ':<br/>&nbsp;&nbsp;1、'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.firstToCrmMaintenance')
				+ '<br/>&nbsp;&nbsp;2、'
				+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.inTheInterfaceModificationAccount')
				+ '</span>'
	},{
		xtype:'container',
		columnWidth:1,
		layout:'column',
		items:[{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.reset'),
			width:80,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:0.82
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.query'),
			width:118,
			cls:'yellow_button',
			handler:function(){
				if(this.up('form').getForm().isValid()){
					consumer.codSalesPay.queryCodSalesPay(this);
				}else{
					Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
			}
		}]
	}]
	
});

/**
 * 显示代收货款单据
 */
Ext.define('Foss.codSalesPay.CodSalesPayGrid',{
	extend:'Ext.grid.Panel',
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	frame:true,
	title: consumer.codSalesPay.i18n('foss.stl.consumer.cod.codMessage'),
	height:600,
	columns : {
				defaults:{
					draggable:false
				},
				items:[{
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.wayBillNo'),
					dataIndex : 'waybillNo'
						
				},{
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.payableNo'),
					dataIndex : 'payableNo'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.billAmount'),
					dataIndex : 'amount'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.verReceivableAmount'),
					dataIndex : 'verReceivableAmount'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.returnAmount'),
					dataIndex : 'returnAmount'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.codReturnType'),
					dataIndex : 'codType',
					renderer:function(value){
						/*if(value == consumer.codSalesPay.COD__COD_TYPE__RETURN_1_DAY){
							return '即日退';
						}
						else if(value == 'R3'){
							return '三日退';
						}
						else if(value == 'RA'){
							return '审核退';
						}else{
							return value;
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__COD_TYPE);
			    		return displayField;
					}
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.codStatus'),
					dataIndex : 'status',
					renderer:function(value){
						/*if(value == consumer.codSalesPay.COD__STATUS__APPROVING){
							return '待审核';
						}
						else if(value == consumer.codSalesPay.COD__STATUS__RETURN_FAILURE){
							return '退款失败';
						}
						else if(value == consumer.codSalesPay.COD__STATUS__NOT_RETURN){
							return '未退款';
						}else if(value == consumer.codSalesPay.COD__STATUS__SHIPPER_FREEZE){
							return '营业部冻结';
						}else{
							return null;
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__STATUS);
			    		return displayField;
					}
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.customerName'),
					dataIndex : 'payableCustomerName'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeeName'),
					dataIndex : 'payeeName'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeeAccount'),
					dataIndex : 'payeeAccount'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank'),
					dataIndex : 'bank'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeeAndConsignor'),
					dataIndex : 'payeeAndConsignor'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.publicPrivateFlag'),
					dataIndex : 'publicPrivateFlag',
					renderer:function(value){
						/*if(value == consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__COMPANY){
							return '对公';
						}
						else if(value == consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__RESIDENT){
							return '对私';
						}else{
							return '未知';
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
			    		return displayField;
					},
					hidden: true
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.province'),
					dataIndex : 'province',
					hidden: true
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.city'),
					dataIndex : 'city',
					hidden: true
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.bankSubbranch'),
					dataIndex : 'bankSubbranch',
					hidden: true
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeePhone'),
					dataIndex : 'payeePhone',
					hidden: true
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.businessDate'),
					dataIndex : 'businessDate',
					format : 'Y-m-d',
					xtype : 'datecolumn'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.orgName'),
					dataIndex : 'payableOrgName'
				}, {
					header : consumer.codSalesPay.i18n('foss.stl.consumer.cod.notes'),
					dataIndex : 'notes'
				}]}	,
	store:null,
	bottomBar:null,
	getBottomBar:function(){
		var me = this;
		if(Ext.isEmpty(me.bottomBar)){

			me.bottomBar = Ext.create('Ext.panel.Panel',{
				border:0,
				items:[Ext.create('Deppon.StandardPaging', {
						store: me.store,
						pageSize: 20,
			    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
							//设置分页记录最大值，防止输入过大的数值
							maximumSize: 100
						})
				}),Foss.codSalesPay.footBottomPanel]
			});
		}
		return me.bottomBar
	},
	loadMask:null,
	getLoadMask:function(){
		var me = this;
		// 加载效果
		me.loadMask = Ext.getCmp('FOSS_consumer_CodSalesPayGrid_LoadMask_ID');
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me.up('panel'),{
				id:'FOSS_consumer_CodSalesPayGrid_LoadMask_ID',
				msg:consumer.codSalesPay.i18n('foss.stl.consumer.cod.dataLoading'),
				autoShow:false
			});
		}
		return me.loadMask;
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this;
		var cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.codSalesPay.CodSalesPayStore',{
			listeners : {
				// 数据加载前，form表单值 设置
				beforeload : function(store, operation, eOpts) {
					Ext.apply(me.submitParams, {
						  "limit":operation.limit,
				          "page":operation.page,
				          "start":operation.start
				          }); 
					
					Ext.apply(operation, {
		   				params : me.submitParams
		   			});
					
				}
			}
		});
		me.bbar = me.getBottomBar();
		me.callParent([cfg]);
	},
    viewConfig: {
    	enableTextSelection: true
    }
});

/**
 * Gird表格footer功能区
 */
Foss.codSalesPay.footBottomPanel = Ext.create('Ext.panel.Panel',{
	border:0,
	dockedItems:[{
   		xtype: 'toolbar',
	    dock: 'bottom',
	    layout:'column',		    	
	    defaults:{
		margin:'0 0 5 0'
		},		
	    items: [{
				xtype:'container',
				columnWidth:0.05,
				cls:'orangerow',
				height:20,
				html:'&nbsp;'
			},{
		    	xtype:'tbtext',
				readOnly:true,
				text : '&nbsp;&nbsp;'+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.salesDepartment')
							+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.doFreeze'),
				height:20,
				columnWidth:0.1
			},{
				xtype:'container',
				columnWidth:0.05,
				cls:'redrow',
				height:20,
				html:'&nbsp;'
			},{
		    	xtype:'tbtext',
				readOnly:true,
				text:'&nbsp;&nbsp;'+consumer.codSalesPay.i18n('foss.stl.consumer.cod.accountHasModified'),
				height:20,
				columnWidth:0.1
			},{
		    	xtype:'tbtext',
				readOnly:true,
				columnWidth:0.33
			},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.cod.salesDepartment')
					+ consumer.codSalesPay.i18n('foss.stl.consumer.cod.doFreeze'),
			disabled:!consumer.codSalesPay.isPermission('/stl-web/consumer/freezePayCOD.action'),		
			hidden:!consumer.codSalesPay.isPermission('/stl-web/consumer/freezePayCOD.action'),		
			columnWidth:0.1,
			width:54,
			handler: function() {
				var grid = Ext.getCmp('Foss_codSalesPay_CodSalesPayGrid_ID');
				grid.getLoadMask().show();
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductFreezingOperation'));
						grid.getLoadMask().hide();
						return false;
				}
				jsonData =[];
				for(var i=0;i<rows.length;i++){
					jsonData.push(rows[i].get('id'));
				}
				//营业部冻结
				Ext.Ajax.request({
					url:consumer.realPath('freezePayCOD.action'),
					method:'post',
					params:{
						'salesPayCODVO.entityIds':jsonData
					},
					success:function(response){
						Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.salesDepartmentFreezeSuccess'),'ok',1000);
						var store = grid.getStore();
						var index = 0;
						//冻结成功，设置冻结标识
						store.each(function(record){
							for(var i=0;i<jsonData.length;i++){
								var id = jsonData[i];
								if(record.get('id') == id){
									record.set('status',consumer.codSalesPay.COD__STATUS__SHIPPER_FREEZE);
									record.commit();
								}
							}
							index++;
						});
						store.load();
						grid.getLoadMask().hide();
					},
					exception : function(response) {
					  var json = Ext.decode(response.responseText);
					  Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
					  grid.getLoadMask().hide();
					},
					failure:function(form,action){
						grid.getLoadMask().hide();
					},
					unknownException:function(form,action){
						grid.getLoadMask().hide();
					}				
				}); 
				  
	        }
		},{
			xtype:'container',
			columnWidth:0.02,
			html:'&nbsp;',
			border:0
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.cod.modifyAccount'),
			columnWidth:0.1,
			width:54,
			handler: function() {
				var grid = Ext.getCmp('Foss_codSalesPay_CodSalesPayGrid_ID');
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length!=1){
						Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseSelectADataModifiedAccountOperationCanOnlyChoose'));
						return false;
				}
				var model = null;
				for(var i=0;i<rows.length;i++){
					model = rows[i];
				}
				if(model.get('status')!=consumer.codSalesPay.COD__STATUS__SHIPPER_FREEZE){
					Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.mustGoToTheSalesFrozenCanModifyTheAccount'));
					return false;
				}
				
				// 客户编码
				var customerCodeV = model.get('payableCustomerCode');
				if(customerCodeV == null || customerCodeV == ''){
					var waybillNoV = model.get('waybillNo');
					Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.WaybillNoCodCustomerCodeEmptyCannotChangeAccount',[waybillNoV]));
					return false;
				}
				
				// 创建修改窗口
				var modifyAccountWindow = Ext.getCmp('Foss_codSalesPay_ModifyAccountWindow_ID');
				if(!modifyAccountWindow){
						modifyAccountWindow=Ext.create('Foss.codSalesPay.ModifyAccountWindow',{
						id:'Foss_codSalesPay_ModifyAccountWindow_ID',
						customerCode:customerCodeV
					});
				}
				// 设置客户编码
				modifyAccountWindow.customerCode = customerCodeV;
				
				modifyAccountWindow.show();
				// 将选择model数据加载到修改窗口的form中
				Ext.getCmp('Foss_codSalesPay_ModifyAccountForm_ID').getForm().reset(); // 清空form表单数据
				Ext.getCmp('Foss_codSalesPay_ModifyAccountForm_ID').getForm().loadRecord(model);
	        }
		},{
			xtype:'container',
			columnWidth:0.02,
			html:'&nbsp;',
			border:0
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.audit'),
			disabled:!consumer.codSalesPay.isPermission('/stl-web/consumer/cashierAuditAccounts.action'),
			hidden:!consumer.codSalesPay.isPermission('/stl-web/consumer/cashierAuditAccounts.action'),
			columnWidth:0.1,
			width:54,
			handler: function() {
				var grid = Ext.getCmp('Foss_codSalesPay_CodSalesPayGrid_ID');
				grid.getLoadMask().show();
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneAuditData'));
						grid.getLoadMask().hide();
						return false;
				}
				var salesPayCODVO = new Object();
				codsData = new Array();
				jsonData = new Array();
				for(var i=0;i<rows.length;i++){
					jsonData.push(rows[i].get('id'));
					
					var cod = new Object();
					cod.waybillNo = rows[i].get('waybillNo');
					cod.id = rows[i].get('id');
					cod.payableCustomerCode = rows[i].get('payableCustomerCode');
					cod.payableCustomerName = rows[i].get('payableCustomerName');
					codsData.push(cod);
				}
				salesPayCODVO.cods = codsData;
				
				// 客户存在应收账款，对应的代收货款是否冲销应收款
				Ext.Ajax.request({
					url:consumer.realPath('queryCODDtoCheckReceivableUnAmount.action'),
					method:'post',
					jsonData:{
						'salesPayCODVO':salesPayCODVO
					},
					success:function(response){
						
						var result = Ext.decode(response.responseText);
						var codList = result.salesPayCODVO.cods;
						var codListLen = codList.length;
						if(codListLen > 0){ // 存在应收账款
							var startMsg = "";
							// 循环拼接错误信息
							for(var j=0;j<codListLen;j++){
								var cod = codList[j];
								startMsg += "[运单："+cod.waybillNo+ ";客户："+cod.payableCustomerCode +","+cod.payableCustomerName +"],<br/>"
							} 
							
							//提示是否导出
							Ext.Msg.confirm(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), startMsg+'存在应收账款，对应的代收货款是否冲销应收款?(确认:需要冲销，则通过核销界面冲销，<br/>取消:不需要，则直接审批)',function(btn,text){
								if('no' == btn){
									consumer.codSalesPay.cashierAuditAccountsAction(grid,jsonData); // 出纳审核
								}
							});	
							
						}else{
							consumer.codSalesPay.cashierAuditAccountsAction(grid,jsonData); // 出纳审核
						}
						
						grid.getLoadMask().hide();
					},
					exception : function(response) {
					  var json = Ext.decode(response.responseText);
					  Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
					  grid.getLoadMask().hide();
					},
					failure:function(form,action){
						grid.getLoadMask().hide();
					},
					unknownException:function(form,action){
						grid.getLoadMask().hide();
					}				
				}); 
				
				
	        }
		}]
	}]
});

/**
 * 修改账号Form
 */
Ext.define('Foss.codSalesPay.ModifyAccountForm',{
	extend:'Ext.form.Panel',
	frame:true,
	collapsible:false,
	animcollapse:true,
	store:Ext.create('Foss.codSalesPay.CodSalesPayStore'),
	defaultType:'textfield',
	layout:{
		type : 'column'
	},	
    bodyStyle: 'padding: 5px;',
	constructor: function(config){
  		var me = this,cfg = Ext.apply({}, config);
  		me.items=[{
  			xtype: 'textfield',
  		   	name: 'id',
	   		fieldLabel: 'ID',
	   		hidden:true
	   	},{
	   		xtype: 'textfield',
  		   	name: 'waybillNo',
	   		fieldLabel: 'waybillNo',
	   		hidden:true
	   	},{
	   		xtype: 'textfield',
  		   	name: 'bankCode',
	   		fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.bankCode'),
	   		hidden:true
	   	},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.depositBank'),
			name: 'bank',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.depositBank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),
			cls:'readonlygraybackground',
			columnWidth:.5
		},{
			xtype: 'combobox',
			name:'publicPrivateFlag',
			cls:'readonlygraybackground',
	        fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.publicPrivateMark'),
	        store : FossDataDictionary.getDataDictionaryStore(
	        		settlementDict.COD__PUBLIC_PRIVATE_FLAG, null,null ),
			/*store:Ext.create('Foss.codSalesPay.AccountPropertiesStore'),*/
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.publicPrivateMark')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
	        columnWidth:.5
		},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.openAccount'),
			name: 'payeeName',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.openAccount')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),			
			cls:'readonlygraybackground',
			columnWidth:.5
		},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.phoneNumber'),
			name: 'payeePhone',
			readOnly:true,
			//allowBlank:false,
			//blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.phoneNumber')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),			
			cls:'readonlygraybackground',
			columnWidth:.5
		},
		{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.bankAccount'),
			name: 'payeeAccount',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.bankAccount')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),			
			cls:'readonlygraybackground',
			columnWidth:.86
		},
		{
			xtype : 'button',
			text : '<span style="color:#FFFFFF">'+ consumer.codSalesPay.i18n('foss.stl.consumer.common.query')+'</span>',
			style: {
				/* 去掉按钮样式 */
				border: 'none',
				padding: '0',
				margin: '2px 0 0 1px',
		    	textDecoration: 'none',
				fontSize:'8px',
				background: '#373C64',
				fontWeight: 'lighter'
	        },
			height : 22,
			columnWidth:.13,
			handler : function() {
				var customerCodeV = this.up('window').customerCode;
				var win = Ext.getCmp('Foss_baseinfo_commonSelector_customerWindow');
				if (Ext.isEmpty(win)) {
					win = Ext.create('Foss.baseinfo.commonSelector.CustomerWindow',{
							'id' : 'Foss_baseinfo_commonSelector_customerWindow',
							commitFun : function() {
								var formPanel = Ext.getCmp('Foss_codSalesPay_ModifyAccountForm_ID');
								formPanel.getLoadMask().show();
								
								var rows = win.getGridRecord();
									if(rows == null ){
										Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.customerNotHaveTheBankInfo'));
										return false;
									}
									// 选择该银行
									if(rows.length == 1){
										var record = rows[0];
										
										var codType = me.getForm().getRecord().get('codType'); // 代收货款类型
										var flag = record.get('accountNature'); // 所选账号 公私标识
										// 代收货款即日退
										if(consumer.codSalesPay.COD__COD_TYPE__RETURN_1_DAY == codType){ 
											// 判断所选账号的银行暂不支持即日退
											var intraDayType = '';
											Ext.Ajax.request({
												url:consumer.realPath('queryBankIntraDayTypeByBankCode.action'),
												method : 'post',
												params : {
													"salesPayCODVO.bankCode":record.get("bankCode")
												},
												async: false,
												success:function(response){
													
													 var result = Ext.decode(response.responseText);
													 intraDayType = result.salesPayCODVO.intraDayType;
													 if(intraDayType == 'N' ){
														Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.selectedAccountBanktemporaryNotSupportThisRetreat'));
														return false;
													 }else if(intraDayType != 'Y'&&intraDayType != 'N'){
														Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.inquiresBankEntityEmpty'));
														return false;
													 }
													 
													 // 判断即日退不能选择对公账号
													if(consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__COMPANY == flag
														||consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__PUBLIC == flag){ // 对公标志
														Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.backCannotChooseSaysToTheAccount'));
														return false;
													}
													consumer.codSalesPay.bankRecordCopyForm(me,record);
												},
												exception : function(response) {
												  var json = Ext.decode(response.responseText);
												  Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
												  formPanel.getLoadMask().hide();
												},
												failure:function(form,action){
													formPanel.getLoadMask().hide();
												},
												unknownException:function(form,action){
													formPanel.getLoadMask().hide();
												}
											});
											
										} else {
											consumer.codSalesPay.bankRecordCopyForm(me,record);
										}
										
									}
									formPanel.getLoadMask().hide();
							},
							'customerCode' : customerCodeV/*,
							'active':'N'*/
						});	
				
				}else{
					win.customerCode = customerCodeV;
					/*win.active = 'N';*/
				}
				
				win.down('grid').store.removeAll();
				win.show();
			}
		},{
	   		xtype: 'textfield',
  		   	name: 'provinceCode',
	   		fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.province')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.code'),
	   		hidden:true
	   	},{
	   		xtype: 'textfield',
  		   	name: 'cityCode',
	   		fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.city')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.code'),
	   		hidden:true
	   	},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.province'),
			name: 'province',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.province')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),			
			cls:'readonlygraybackground',
			columnWidth:.5
		},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.city'),
			name: 'city',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.city')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),				
			cls:'readonlygraybackground',
			columnWidth:.5
		},{
			xtype: 'textfield',
			name:'bankSubbranchCode',
			fieldLabel: 'bankSubbranchCode',
	   		hidden:true
		},{
			xtype:'textarea',
			name:'bankSubbranch',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.subbranch'),
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.bank')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.subbranch')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),				
			cls:'readonlygraybackground',
			width:300,
			height:140,
			columnWidth:1
		},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeeAndConsignor'),
			name: 'payeeAndConsignor',
			readOnly:true,
			allowBlank:false,
			blankText:consumer.codSalesPay.i18n('foss.stl.consumer.cod.payeeAndConsignor')+consumer.codSalesPay.i18n('foss.stl.consumer.cod.cannotEmpty'),				
			cls:'readonlygraybackground',
			columnWidth:1
		},{
			xtype:'container',
			html : '&nbsp;',
			columnWidth:.6
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.commit'),
			disabled:!consumer.codSalesPay.isPermission('/stl-web/consumer/changeBankAccounts.action'),
			hidden:!consumer.codSalesPay.isPermission('/stl-web/consumer/changeBankAccounts.action'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var grid = Ext.getCmp('Foss_codSalesPay_CodSalesPayGrid_ID');
				var formPanel = Ext.getCmp('Foss_codSalesPay_ModifyAccountForm_ID');
				var form = formPanel.getForm(); 
				formPanel.getLoadMask().show();
				if(form.isValid()){
					//修改银行帐号
					Ext.Ajax.request({
						url:consumer.realPath('changeBankAccounts.action'),
						params : {
									"salesPayCODVO.id":form.findField("id").getValue(),
									"salesPayCODVO.waybillNo":form.findField("waybillNo").getValue(),
									"salesPayCODVO.payeeAccount":form.findField("payeeAccount").getValue(),
									"salesPayCODVO.bankCode":form.findField("bankCode").getValue(),
									"salesPayCODVO.bank":form.findField("bank").getValue(),
									"salesPayCODVO.publicPrivateFlag":form.findField("publicPrivateFlag").getValue(),
									"salesPayCODVO.payeePhone":form.findField("payeePhone").getValue(),
									"salesPayCODVO.provinceCode":form.findField("provinceCode").getValue(),
									"salesPayCODVO.cityCode":form.findField("cityCode").getValue(),
									"salesPayCODVO.province":form.findField("province").getValue(),
									"salesPayCODVO.city":form.findField("city").getValue(),
									"salesPayCODVO.bankSubbranchCode":form.findField("bankSubbranchCode").getValue(),
									"salesPayCODVO.bankSubbranch":form.findField("bankSubbranch").getValue(),
									"salesPayCODVO.payeeName":form.findField("payeeName").getValue(),
									"salesPayCODVO.payeeAndConsignor":form.findField("payeeAndConsignor").getValue()
								},
						success:function(response){
							Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), consumer.codSalesPay.i18n('foss.stl.consumer.cod.modifyAccountSuccess'),'ok',1000);
							var selectionModel = grid.getSelectionModel();
							var rows= selectionModel.getSelection(); //获取所有选中行，
							jsonData = new Array();
							for(var i=0;i<rows.length;i++){
								jsonData.push(rows[i].get('id'));
							}
							var index = 0;
							// 修改成功后，对修改成功数据加红色标识
							var store = grid.getStore();
							store.load(function(records, operation, success) {
								grid.getStore().each(function(record){
										for(var i=0;i<jsonData.length;i++){
											var id = jsonData[i];
											if(record.get('id') == id){
												record.set('password','1');
												record.commit();
												//grid.getView().addRowCls(index, 'redrow');
											}
										}
										index++;
								});
								formPanel.getLoadMask().hide();
								var modifyAccountWindow = Ext.getCmp('Foss_codSalesPay_ModifyAccountWindow_ID');
								modifyAccountWindow.hide(); 
								
							});
						},
						exception : function(response) {
						  var json = Ext.decode(response.responseText);
						  Ext.ux.Toast.msg(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
						  formPanel.getLoadMask().hide();
						},
						failure:function(form,action){
							formPanel.getLoadMask().hide();
						},
						unknownException:function(form,action){
							formPanel.getLoadMask().hide();
						}				
					}); 
				}else{
					formPanel.getLoadMask().hide();
					Ext.Msg.alert(consumer.codSalesPay.i18n('foss.stl.consumer.common.warmTips'),consumer.codSalesPay.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
			}
		},{
			xtype:'button',
			text:consumer.codSalesPay.i18n('foss.stl.consumer.common.cancel'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var modifyAccountWindow = Ext.getCmp('Foss_codSalesPay_ModifyAccountWindow_ID');
				modifyAccountWindow.hide(); 
			}
		}];
	me.callParent([cfg]);
  },
  loadMask:null,
  getLoadMask:function(){
	var me = this;
	me.loadMask = Ext.getCmp('FOSS_consumer_ModifyAccountForm_LoadMask_ID');
	if(Ext.isEmpty(me.loadMask)){
		me.loadMask = new Ext.LoadMask(me.up('panel'),{
			id:'FOSS_consumer_ModifyAccountForm_LoadMask_ID',
			msg:consumer.codSalesPay.i18n('foss.stl.consumer.cod.dataLoading'),
			autoShow:false
		});
	}
	return me.loadMask;
  }
});

/**
 * 修改账号界面  Window
 */
Ext.define('Foss.codSalesPay.ModifyAccountWindow',{
  extend: 'Ext.Window', 
	closeAction: 'close',
	store:Ext.create('Foss.codSalesPay.CodSalesPayStore'),
	title:consumer.codSalesPay.i18n('foss.stl.consumer.cod.depositBank'),
	x:(stl.SCREENWIDTH-500)/2,
	y:150,
	modal:true,
	width:500,
	height:400,
	customerCode:null,
  resizable:false,
  //layout: 'fit',
  layout : 'column',
  codSalesPayModifyAccountForm:null,
  getCodSalesPayModifyAccountForm:function(){  
  	if(this.writeoffNoteForm==null){
  		this.writeoffNoteForm=Ext.create('Foss.codSalesPay.ModifyAccountForm',{id:'Foss_codSalesPay_ModifyAccountForm_ID'});
  	}
  	return this.writeoffNoteForm;
  }, 
  constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getCodSalesPayModifyAccountForm()];
		me.callParent([cfg]);
  }
});

//查询tab
Ext.define('Foss.codSalesPay.CodSalesPayQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 1,//为1的时候，默认浏览器打开的时候，激活的页签是第二个，第一个应该是0
	height : 260,
	items : [ {
		title: consumer.codSalesPay.i18n('foss.stl.consumer.cod.queryByCodType'),//按代收货款状态查询
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.codSalesPay.CodSalesPayCodStatusForm')
               ]
	}, {
		title: consumer.codSalesPay.i18n('foss.stl.consumer.common.queryByNo'),//按单号查询
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.codSalesPay.CodSalesPayWaybillNoForm')
               ]
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//创建Tab
	var codSalesPayQueryInfoTab = Ext.getCmp('Foss_codSalesPay_CodSalesPayQueryInfoTab_ID');
	if(!codSalesPayQueryInfoTab){
		codSalesPayQueryInfoTab = Ext.create('Foss.codSalesPay.CodSalesPayQueryInfoTab',{
			id: 'Foss_codSalesPay_CodSalesPayQueryInfoTab_ID'
		});
	}
	//创建显示表格
	var codSalesPayGrid = Ext.create('Foss.codSalesPay.CodSalesPayGrid',{
		id : 'Foss_codSalesPay_CodSalesPayGrid_ID',
		hidden:true,
		//enableColumnHide: false,      //取消列头菜单
      	//sortableColumns: false,          //取消列头排序功能
		viewConfig : {   
			enableTextSelection: true,         
	        forceFit : true,
	        stripeRows: false,//显示重复样式，不用隔行显示
	        emptyText : consumer.codSalesPay.i18n('foss.stl.consumer.common.emptyText'),
	        getRowClass : function(record,rowIndex,rowParams,store){
	            if(record.data.password=="1"){ // 账号修改成功返回的样式
	            	return 'redrow';
	            }else if(record.data.status==consumer.codSalesPay.COD__STATUS__SHIPPER_FREEZE){ // 审核成功后返回的样式
	            	return 'orangerow';
	            }else{
	            	return ''; 
	            }
         }
    }   			
	});

	//显示到JSP页面
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-codSalesPay_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [codSalesPayQueryInfoTab,codSalesPayGrid],
		renderTo: 'T_consumer-codSalesPay-body'
	});

	//如果为站内消息通知则自动跳转到第一个页签
	if(!Ext.isEmpty(consumer.codSalesPay.activeTab)){
		codSalesPayQueryInfoTab.setActiveTab(consumer.codSalesPay.activeTab);
	}


});