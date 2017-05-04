/**
 *author : 043258-foss-zhaobin
 *page : 处理异常 
*/

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.exceptionProcess.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

predeliver.exceptionProcess.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

//定义处理异常模型
Ext.define('Foss.predeliver.exceptionProcess.ExceptionModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'exceptionProcessId'},//异常ID
			    { name: 'waybillNo'}, //运单号
				{ name: 'status',
			    	convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_EXCEPTION_STATE');
				}}, //异常状态
				{ name: 'receiveCustomerName'}, //收货人
				{ name: 'receiveCustomerMobilephone'},  //收货人手机
				{ name: 'receiveCustomerPhone'},  //收货人电话
				{ name: 'exceptionType',				
					convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_EXCEPTION_TYPE');
				}},  //异常类型
				{ name: 'exceptionLink',
					convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_EXCEPTION_PHASE');
				}},  //异常环节
				{ name: 'serialNo'},  //流水号
				{ name: 'receiveOrgCode'},  //收货部门
				{ name: 'receiveOrgName'},  //收货部门
				{ name: 'customerPickupOrgCode'},  //到达部门
				{ name: 'createUserName'},  //上报人
				{ name: 'exceptionTime',type:'date',
					 convert:function(value){
						 if(value!=null){
							 var date = new Date(value);
							 return date;
						 }else{
							 return null;
						 }
					 }}, //登记时间
				{ name: 'notes'},  //异常原因
				{ name: 'storageDay'}  //库存天数
			 ]
});

//定义处理异常详情模型
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'operateTime',type:'date',
					convert:function(value){
					 if(value!=null){
						 var date = new Date(value);
						 return date;
					 }else{
						 return null;
					 }
				 }}, //登记时间
				{ name: 'exceptionType',				
					convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_EXCEPTION_TYPE');
				}},  //异常类型
				{ name: 'operator'},//处理人
				{ name: 'operateOrgName'},//处理人
			    { name: 'notes'}//异常原因
			 ]
});
//定义异常传弃货申请MODEl
Ext.define('Foss.predeliver.exceptionProcess.AbandonGoodsDetailModel', {
	extend: 'Ext.data.Model',
	fields: [
			    { name: 'waybillNo'}, //运单号
				{ name: 'origOrgName'}, //始发部门
				{ name: 'deliveryCustomerName'}, //发货人
				{ name: 'deliveryCustomerMobilephone'}, //发货人手机
				{ name: 'goodsVolumeTotal'}, //体积(方)
				{ name: 'inStockTime',type:'date',
					 convert:function(value){
						 if(value!=null){
							 var date = new Date(value);
							 return date;
						 }else{
							 return null;
						 }
					 }}, //入库时间
				{ name: 'storageDay'},  //仓储时长(天)
				{ name: 'operator'},  //操作人
				{ name: 'exceptionId'}, //异常_ID
				{ name: 'abandonedgoodsType'} //弃货类型
			 ]
});
//创建处理异常枚举store
Ext.define('Foss.predeliver.exceptionProcess.ExceptionTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
		
//创建处理异常store
Ext.define('Foss.predeliver.exceptionProcess.ExceptionStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.predeliver.exceptionProcess.ExceptionModel',
	//是否自动查询
	autoLoad: false,
	//默认每页数据大小
	pageSize:10,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		url:predeliver.realPath('queryExceptionProcessInfo.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.exceptionProcessDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var queryParams = predeliver.exceptionProcess.exceptionForm.getValues();
			Ext.apply(operation, {
				params : {					
						'vo.exceptionProcessConditionDto.storageDayBegin': queryParams.storageDayBegin,
				    	'vo.exceptionProcessConditionDto.storageDayEnd': queryParams.storageDayEnd,
				    	'vo.exceptionProcessConditionDto.exceptionTimeBegin': queryParams.exceptionTimeBegin,
				    	'vo.exceptionProcessConditionDto.exceptionTimeEnd': queryParams.exceptionTimeEnd,
				    	'vo.exceptionProcessConditionDto.exceptionType': queryParams.exceptionType,
				    	'vo.exceptionProcessConditionDto.status': queryParams.status,
				    	'vo.exceptionProcessConditionDto.exceptionLink': queryParams.exceptionLink,
				    	'vo.exceptionProcessConditionDto.waybillNo': queryParams.waybillNo
					}
			});	
		}
	}
});

//创建一个变更内容store
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.predeliver.exceptionProcess.ExceptionProcessModel',
	//是否自动查询
	autoLoad: false,
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
	}
});

//查询条件
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessForm',{
	extend:'Ext.form.Panel',
	title: '查询条件',
	frame:true,
	id:'Foss_Exception_Form_ExceptionProcessForm_ID',
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		xtype:'numberfield',
		hideTrigger: true,
		allowDecimals : false,// 不允许有小数点
		name:'storageDayBegin', 
		fieldLabel: '库存时间(天数)',
		minValue: 1,
		maxLength:20,//最大位数20
		columnWidth: .25
	},{
		xtype: 'container',
		border : false,
		columnWidth:.035,
		html: '&nbsp;'
	},{
		xtype:'numberfield',
		hideTrigger: true,
		allowDecimals : false,// 不允许有小数点
		name:'storageDayEnd', 
		fieldLabel: '至',
		labelWidth: 65,
		minValue: 1,
		maxLength:20,//最大位数20
		columnWidth: .215
	},{
		xtype: 'rangeDateField',
		fieldLabel: '异常时间',
		fieldId: 'FOSS_ExceptionTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'exceptionTimeBegin',
		fieldId:'Foss_exceptionProcess_exceptionTime',
		toName: 'exceptionTimeEnd',
		disallowBlank: true,
		editable:false,
		fromValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate(new Date(),-10),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .5
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'exceptionType',
		fieldLabel: '异常类型',
		columnWidth: 0.25,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE', null, {
			'valueCode': 'ALL',
            'valueName': '全部'
		})	
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'status',
		fieldLabel: '异常状态',
		columnWidth: 0.25,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_STATE', null, {
			'valueCode': 'ALL',
            'valueName': '全部'
		})	
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'exceptionLink',
		fieldLabel: '异常环节',
		columnWidth: 0.25,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE', null, {
			'valueCode': 'ALL',
            'valueName': '全部'
		})	
	},{
		name:'waybillNo', 
		fieldLabel:'运单号',
		regex:/^[0-9]{8,9}$/i,
		regexText:'输入8-9位纯数字单号',
		columnWidth: 0.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:'重置',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.findField('storageDayBegin').setValue('');
				form.findField('storageDayEnd').setValue('');
				form.findField('exceptionTimeBegin').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate(new Date(),-10),'Y-m-d H:i:s'));
				form.findField('exceptionTimeEnd').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
				form.findField('exceptionType').setValue('ALL');
				form.findField('status').setValue('ALL');
				form.findField('exceptionLink').setValue('ALL');
				form.findField('waybillNo').setValue('');
				
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:'查询',
			cls:'yellow_button',
			columnWidth:.08,
			handler:function()
			{	
				var form = this.up('form').getForm();
				//通过校验之后进行分页查询
				if(form.isValid()){
					predeliver.pagingBar.moveFirst();
				}else{
					Ext.ux.Toast.msg('提示', '库存天数或运单号查询条件有误，请检查!', 'error', 3000);
				}
			}
		}]
	}]
});

var addWin = null;
var noticeWin = null;
var abandonedGoodsWin = null;
var addExceptionWin = null;
//获得当前部门是否营业部
var dept = FossUserContext. getCurrentUserDept().code;

//异常信息
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id',
	emptyText:'查询结果为空！',
	//增加表格列的分割线
	columnLines: true,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	//定义表格的标题
	title:'异常记录',
	collapsible: true,
	animCollapse: true,
	store: null,	 
	viewConfig :{
		//显示重复样式，不用隔行显示
	 	stripeRows: false,
	 	enableTextSelection: true,
		getRowClass:function(record, rowIndex, p, ds){
		//如果库存天数大于90天，标记为粉红色
		if(record.get('storageDay') > '90') {
			return 'exceptionProcess_mark_color';
	 		} 
	 	}
	},
	//定义表格列信息
	columns: [
            Ext.create('Ext.grid.RowNumberer'),
		{
			xtype:'actioncolumn',
			width:100,
			text: '操作',
			align: 'center',
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip: '编辑',
				getClass : function(value,metadata,record,rowIndex,colIndex,store)
				{
					// 异常状态
					var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_EXCEPTION_STATE');
	    			//当异常状态为处理中时才显示 
					//异常状态待修改
					if(status == 'HANDLING'){
						return 'deppon_icons_edit';
					}else {
						return 'deppon_icons_edit_hide';
					}
				},
				handler: function(grid, rowIndex, colIndex) {
					var selection = grid.getStore().getAt(rowIndex);
					var	tSrvExceptionId = selection.get('exceptionProcessId');
					Ext.Ajax.request({
						url:predeliver.realPath('queryExceptionProcessDetailInfo.action'),
						params:{'vo.exceptionProcessConditionDto.tSrvExceptionId':tSrvExceptionId},
						success:function(response){
							var result = Ext.decode(response.responseText);	
							//装载成model数据然后返回
							var formModel = new Foss.predeliver.exceptionProcess.ExceptionModel(result.vo.exceptionProcessDetailDto) ;
							//FORM匹配
							predeliver.exceptionProcess.addExceptionProcessForm.loadRecord(formModel);
							//Grid匹配
							predeliver.exceptionProcess.addExceptionProcessGrid.store.loadData(result.vo.exceptionProcessDetailDto.exceptionProcessDetailEntityList);
							addWin = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionProcessWindow').show();
						}
					});
				}
			},{
				iconCls: 'deppon_icons_showdetail',
				tooltip: '查看',
				handler: function(grid, rowIndex, colIndex) {
					var selection = grid.getStore().getAt(rowIndex);
					var	tSrvExceptionId = selection.get('exceptionProcessId');
					Ext.Ajax.request({
						url:predeliver.realPath('queryExceptionProcessDetailInfo.action'),
						params:{'vo.exceptionProcessConditionDto.tSrvExceptionId':tSrvExceptionId},
						success:function(response){
							var result = Ext.decode(response.responseText);	
							var formModel = new Foss.predeliver.exceptionProcess.ExceptionModel(result.vo.exceptionProcessDetailDto) ;
							predeliver.exceptionProcess.queryInfoForm.loadRecord(formModel);
							predeliver.exceptionProcess.queryInfoGrid.store.loadData(result.vo.exceptionProcessDetailDto.exceptionProcessDetailEntityList);
							var queryWin = Ext.create('Foss.predeliver.exceptionProcess.QueryExceptionWindow').show();
						}
					});
					
				}
			},{
				iconCls: 'deppon_icons_notice',
				tooltip: '通知',
				getClass : function(value,metadata,record,rowIndex,colIndex,store){
					// 异常状态
					var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_EXCEPTION_STATE');
					//当异常状态为处理中时才显示 
					//异常状态待修改
					if(status == 'HANDLING'){
						return 'deppon_icons_notice';
					}else {
						return 'deppon_icons_notice_hide';
					}
				},
				handler: function(grid, rowIndex, colIndex) {
					var selection = grid.getStore().getAt(rowIndex);
					var	receiveOrgName = selection.get('receiveOrgName');
					var waybillNo = selection.get('waybillNo');
					var notes = selection.get('notes');
					var exceptionType = null;
					if(selection.get('exceptionType') == 'WAYBILL_EXCEPTION')
					{
						exceptionType = '运单异常';
					}else{
						exceptionType = '货物异常';
					}
					 
					var noticeContext = receiveOrgName +',单号：'+ waybillNo+',由于'+ notes +'原因导致无法正常派送，请协助处理！异常类型：'+ exceptionType;
					
					var form = predeliver.exceptionProcess.addNoticeForm.getForm();
					form.findField('receiveOrgName').setValue(receiveOrgName);
					form.findField('noticeContext').setValue(noticeContext);
					noticeWin = Ext.create('Foss.predeliver.exceptionProcess.AddNoticeWindow').show();
				}
			}]
		},{
			header:'id',
			dateIndex:'exceptionProcessId',
			hidden: true,
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				return record.get('exceptionProcessId');
			},
			align: 'center'
		},{
			header:'到达部门',
			dateIndex:'customerPickupOrgCode',
			hidden: true,
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				return record.get('customerPickupOrgCode');
			},
			align: 'center'
		},{
			header: '运单号', 
			dataIndex: 'waybillNo',
			width:80,
			align: 'center'
		},{ 
			header: '异常状态', 
			dataIndex: 'status',
			width:80,
			align: 'center'
		},{
			header: '收货人', 
			dataIndex: 'receiveCustomerName', 
			width:80,
			align: 'center'
		},{
			header: '收货人手机', 
			dataIndex: 'receiveCustomerMobilephone', 
			width:80,
			align: 'center'
		},{
			header: '收货人电话', 
			dataIndex: 'receiveCustomerPhone', 
			width:80,
			align: 'center'
		},{
			header: '异常类型', 
			dataIndex: 'exceptionType', 
			width:80,
			align: 'center'
		},{
			header: '异常环节', 
			dataIndex: 'exceptionLink', 
			width:80,
			align: 'center'
		},{
			header: '流水号', 
			dataIndex: 'serialNo', 
			width:80,
			align: 'center'
		},{
			header: '收货部门', 
			dataIndex: 'receiveOrgName', 
			width:80,
			align: 'center'
		},{
			header: '收货部门', 
			dataIndex: 'receiveOrgCode', 
			hidden: true,
			width:80,
			align: 'center'
		},{
			header: '登记人', 
			dataIndex: 'createUserName', 
			width:80,
			align: 'center'
		},{
			header: '登记时间', 
			dataIndex: 'exceptionTime', 
			width:100,
			align: 'center',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			header: '异常原因', 
			dataIndex: 'notes', 
			width:80,
			align: 'center'
		},{
			header: '出发/到达', 
			dataIndex: 'receivepick', 
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('receiveOrgCode')==dept){
					return '出发';
				}else if(record.get('customerPickupOrgCode')==dept){ 
					return '到达';
				}else{
					return null;
				}
			},
			align: 'center'
		},{
			header: '库存天数', 
			dataIndex: 'storageDay', 
			width:80,
			align: 'center'
		}],
	    listeners: {
	        itemclick:function(dv, record, item, index, e){
	        	// 异常状态
				var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_EXCEPTION_STATE');
				if(status !='HANDLING'){
					Ext.getCmp('Foss_predeliver_exception_abandonGoods_Id').setDisabled(true);
				}else{
					Ext.getCmp('Foss_predeliver_exception_abandonGoods_Id').setDisabled(false);
				}
	        }
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.predeliver.exceptionProcess.ExceptionStore');
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				defaults:{
					margin:'0 0 5 3',
					allowBlank:true
				},		
				items: [{
					xtype:'button',
					allowBlank:true,
					name:'addException',
					columnWidth:.1,
					text:'新增异常',
					handler: function(grid, rowIndex, colIndex) {
						addExceptionWin = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionWindow').show();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.01,
					html: '&nbsp;'
				},{
					xtype:'button',
					id:'Foss_predeliver_exception_abandonGoods_Id',
					allowBlank:true,
					name:'abandonGoodsApplication2',
					columnWidth:.1,
					text:'转弃货申请',
						handler: function() {
							var gridRow = predeliver.exceptionProcess.exceptionGrid.getSelectionModel().getSelection()[0];
							
							if(typeof gridRow == "undefined"){
								Ext.ux.Toast.msg('提示', '请选择一个处理中的数据', 'error', 3000);
							}
							
							var statusRs =FossDataDictionary.rendererDisplayToSubmit(gridRow.data.status, 'PKP_EXCEPTION_STATE');
						
							if(statusRs =='HANDLING'){
							//页面显示数据
							var waybillNo = gridRow.data.waybillNo;//运单号
							receiveOrgCode = gridRow.data.receiveOrgCode;//始发部门
							createUserName = gridRow.data.createUserName;//操作人
							storageDay = gridRow.data.storageDay;//库存天数
							//页面保数据
							exceptionId = gridRow.data.exceptionProcessId;//异常ID
							Ext.Ajax.request({
							url:predeliver.realPath('searchAbandonGoodsApplicationInfo.action'),
							params : {
								'vo.abandonGoodsApplicationDto.waybillNo' : waybillNo, //运单
								'vo.abandonGoodsApplicationDto.origOrgCode' : receiveOrgCode,//始发部门
								'vo.abandonGoodsApplicationDto.operator' : createUserName,//操作人
								'vo.abandonGoodsApplicationDto.storageDay' : storageDay,//仓储时长
								'vo.abandonGoodsApplicationDto.exceptionId' : exceptionId//异常ID
							},
							success : function(response) {
							 	var result = Ext.decode(response.responseText);
								var model = Ext.ModelManager.create(result.vo.abandonGoodsApplicationDto,'Foss.predeliver.exceptionProcess.AbandonGoodsDetailModel');
								predeliver.exceptionProcess.abandonGoodsDetailViewForm.loadRecord(model);
								abandonedGoodsWin = Ext.create('Foss.HandlingExceptions.Window.AbandonedGoods').show();
							}
						    });
							}
						}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.01,
					html: '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					name:'exportException',
					columnWidth:.1,
					text:'导出',
					handler : function(){
						var queryParams = predeliver.exceptionProcess.exceptionForm.getValues();
						if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}		
						Ext.Ajax.request({
							url:predeliver.realPath('exportExcel.action'),
							form: Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : {					
								'vo.exceptionProcessConditionDto.storageDayBegin': queryParams.storageDayBegin,
						    	'vo.exceptionProcessConditionDto.storageDayEnd': queryParams.storageDayEnd,
						    	'vo.exceptionProcessConditionDto.exceptionTimeBegin': queryParams.exceptionTimeBegin,
						    	'vo.exceptionProcessConditionDto.exceptionTimeEnd': queryParams.exceptionTimeEnd,
						    	'vo.exceptionProcessConditionDto.exceptionType': queryParams.exceptionType,
						    	'vo.exceptionProcessConditionDto.status': queryParams.status,
						    	'vo.exceptionProcessConditionDto.exceptionLink': queryParams.exceptionLink,
						    	'vo.exceptionProcessConditionDto.waybillNo': queryParams.waybillNo
							},
							isUpload: true
/*							success:function(response){
								alert(1);
							},
							exception : function(response) {
								alert(2);
							}*/
						});
					}
				}]
			}],

			//添加分页工具条
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
			predeliver.pagingBar = me.bbar;
			predeliver.store = me.store;
			me.callParent([cfg]);
		}
	});

//新增弃货信息表单
Ext.define('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailForm', {
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	style:{border:'0px'},
	defaultType: 'textfield',
	layout:'column',
	defaults: {
			margin:'5 5 5 5',
			//anchor: '100%',
			labelWidth:90
		},
	frame:true,
	collapsible: true,
	animCollapse: true,
	title : '新增预弃货信息',
	items : [
	{
		xtype : 'hiddenfield',
		name : 'exceptionId' // 异常_ID
	}, {
		xtype : 'hiddenfield',
		name : 'abandonedgoodsType' //弃货类型
	}, {
		fieldLabel : '运单号',
		name : 'waybillNo',
		readOnly : true,
		columnWidth : .5
	}, {
		fieldLabel: '始发部门',
		name: 'origOrgCode',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '发货人',
		name: 'deliveryCustomerName',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '发货人手机',
		name: 'deliveryCustomerMobilephone',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '体积(方)',
		name: 'goodsVolumeTotal',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '入库时间',
		name: 'inStockTime',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '仓储时长(天)',
		name: 'storageDay',
		readOnly: true,
		columnWidth:.5		
	},{
		fieldLabel: '操作人',
		name: 'operator',
		readOnly:true,
		columnWidth:.5		
	},{
       xtype: 'filefield',
       name: 'photo',
       columnWidth:1,
       fieldLabel: '弃货证明',
       buttonConfig: {
       	text: '浏览'
       }
   },{
			border : false,
			xtype : 'container',
			columnWidth:0.99,
			layout:'column',
			defaults: {
				margin:'5 0 5 0'
			},
			items : [{
				xtype : 'button',
				columnWidth:.2,
				text: '关闭',
				handler: function() {
					abandonedGoodsWin.close();
				}
			},{
				border : false,
				columnWidth:.6,
				html: '&nbsp;'
			},{
				columnWidth:.2,
				xtype : 'button',
				cls:'yellow_button',
				text: '提交',
				handler: function() {
					var serachParms = this.up('form').getForm().getValues();
    	        		Ext.Ajax.request({
							url:predeliver.realPath('createAbandonGoodsApplication.action'),
    	        			method: 'POST',
    	        			jsonData:{'vo':{'abandonGoodsApplicationEntity':{'waybillNo':serachParms.waybillNo,
    	        			'tSrvExceptionId':serachParms.exceptionId,'abandonedgoodsType':serachParms.abandonedgoodsType}}
    	        			},
    	        			success : function(response) {
    	        				var json = Ext.decode(response.responseText);
    	        				Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);
    	        				abandonedGoodsWin.close();
    	        			},
    	        			exception : function(response) {
    	        				var result = Ext.decode(response.responseText);
						          Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
    	        			}
    	        		});
    				
				}
			}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}		
});
//定义变量，在事件里面使用
predeliver.exceptionProcess.abandonGoodsDetailViewForm = Ext.create('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailForm');
//新增弃货信息窗口
Ext.define('Foss.HandlingExceptions.Window.AbandonedGoods', {
	extend:'Ext.window.Window',
	closeAction:'hide',
	width: 550,
	height : 350,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[predeliver.exceptionProcess.abandonGoodsDetailViewForm]
});
//处理详情
Ext.define('Foss.predeliver.exceptionProcess.QueryInfoForm',{
	extend:'Ext.form.Panel',	
	title: '处理详情',
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'displayfield',
	layout: 'column',
	items:[
		{
			name: 'waybillNo',
			fieldLabel: '运单号',
			labelWidth: 85,
			columnWidth: .5
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			disabled:true,
			name: 'exceptionType',
			fieldLabel: '异常类型',
			columnWidth: 0.5,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE')
		},{
			name: 'receiveCustomerName',
			fieldLabel: '收货人',
			labelWidth: 85,
			columnWidth: .5
		},{	
			name: 'receiveCustomerPhone',
			fieldLabel: '收货人电话',
			labelWidth: 85,
			columnWidth: .5
		},{			
			name: 'receiveCustomerMobilephone',
			fieldLabel: '收货人手机',
			labelWidth: 85,
			columnWidth: .5
		},{		
			name: 'receiveOrgName',
			fieldLabel: '收货部门',
			labelWidth: 85,
			columnWidth: .5
		}]
});	

//处理历史
Ext.define('Foss.predeliver.exceptionProcess.QueryInfoGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	height : 250,
	emptyText:'查询结果为空！',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//定义表格的标题
	title:'处理历史',
	collapsible: true,
	animCollapse: true,
	store: Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessStore'),	 
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
		{
			
			header: '登记时间', 
			//关联model中的字段名
			dataIndex: 'operateTime',
			align: 'center',
			width:180,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{ 
			header: '处理人', 
			//关联model中的字段名
			dataIndex: 'operator',
			align: 'center',
			width:160
		},{
			header: '异常原因', 
			//关联model中的字段名
			dataIndex: 'notes', 
			align: 'center',
			width:180
		}]
	});	
		
//登记处理异常
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionProcessForm',{
	extend:'Ext.form.Panel',	
	id:'Foss_predeliver_exceptionProcess_AddExceptionProcessForm_Id',
	title: '登记处理异常',
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[
		{
			name: 'waybillNo',
			fieldLabel: '运单号',
			labelWidth: 85,
			columnWidth: .5,
			disabled:true
		},{
			name: 'receiveCustomerName',
			fieldLabel: '收货人',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			disabled:true,
			name: 'exceptionType',
			fieldLabel: '异常类型',
			columnWidth: .5,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE')	
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			name: 'exceptionLink',
			fieldLabel: '异常环节',
			columnWidth: 0.5,
			disabled:true,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE')	
		},{	
			name: 'receiveCustomerPhone',
			fieldLabel: '收货人电话',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{			
			name: 'receiveCustomerMobilephone',
			fieldLabel: '收货人手机',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{		
			name: 'receiveOrgName',
			fieldLabel: '收货部门',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{		
			name: 'serialNo',
			fieldLabel: '流水号',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{		
			name: 'createUserName',
			fieldLabel: '上报人',
			labelWidth: 85,
			disabled:true,
			columnWidth: .5
		},{		
			name: 'exceptionProcessId',
			fieldLabel: '异常ID',
			labelWidth: 85,
			hidden: true,
			columnWidth: .5
		},{
			name:'notes',
	        xtype: 'textareafield',
	        maxLength:200,
	        fieldLabel: '处理结果',
	        rowspan: 3,
	        columnWidth: 1,
	        allowBlank:false
		}]
});	

//处理历史
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionProcessGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	height : 250,
	emptyText:'查询结果为空！',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//定义表格的标题
	title:'处理历史',
	collapsible: true,
	animCollapse: true,
	store: Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessStore'),	 
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
		{
			
			header: '登记时间', 
			dataIndex: 'operateTime',
			align: 'center',
			width:130,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			header: '处理人', 
			dataIndex: 'operator', 
			align: 'center',
			width:100
		},{
			header: '处理结果', 
			dataIndex: 'notes', 
			align: 'center',
			flex:1
		},{
			header: '处理部门', 
			dataIndex: 'operateOrgName', 
			align: 'center',
			flex:1
		}]
	});	
	

//获取当前操作人
var userName = FossUserContext. getCurrentUserEmp().empName;

//新增异常form
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionForm',{
	extend:'Ext.form.Panel',	
	id:'Foss_predeliver_exceptionProcess_AddExceptionForm_Id',
	title: '新增异常',
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			name: 'exceptionType',
			fieldLabel: '异常类型',
			columnWidth: .5,
			allowBlank:false,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE')	,
			 listeners : 
		 	{
		    	'select' : function(combo, records, eOpts)
		    	{
		    		var form = this.up('form').getForm();
					if (records[0].get('valueCode') == 'LABELEDGOOD_EXCEPTION') 
					{
						form.findField('serialNo').setDisabled(false);
					}
					else
					{
						form.findField('serialNo').setDisabled(true);
					}
		    	}
			 }	
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			name: 'exceptionLink',
			fieldLabel: '异常环节',
			columnWidth: 0.5,
			allowBlank:false,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE')		
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
			name: 'waybillNo',
			fieldLabel: '运单号',
			labelWidth: 100,
			regex:/^[0-9]{8,9}$/i,
			regexText:'输入8-9位纯数字单号',
			allowBlank:false,
			columnWidth: .5
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{		
			name: 'serialNo',
			fieldLabel: '货物流水号',
			labelWidth: 100,
			columnWidth: .5
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{		
			name: 'createUserName',
			fieldLabel: '上报人',
			labelWidth: 100,
			disabled:true,
			value: userName,
			columnWidth: .5
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:'重置',
			columnWidth:.15,
			handler:function(){
				var form = this.up('form').getForm();
				form.findField('exceptionType').setValue('');
				form.findField('exceptionLink').setValue('');
				form.findField('waybillNo').setValue('');
				form.findField('serialNo').setValue('');
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			text:'保存',
			cls:'yellow_button',
			columnWidth:.15,
			handler:function()
			{
				var form = this.up('form').getForm();
				var iexceptionType = form.findField('exceptionType').getValue();
				var iexceptionLink = form.findField('exceptionLink').getValue();
				var iwaybillNo = form.findField('waybillNo').getValue();
				var iserialNo = form.findField('serialNo').getValue();
				if(form.isValid()){
					Ext.Ajax.request({
					    url:predeliver.realPath('addExceptionProcessInfo.action'),
					    params:{
					    	'vo.exceptionProcessConditionDto.exceptionType':iexceptionType,
					    	'vo.exceptionProcessConditionDto.exceptionLink':iexceptionLink,
					    	'vo.exceptionProcessConditionDto.waybillNo':iwaybillNo,
					    	'vo.exceptionProcessConditionDto.serialNo':iserialNo
					    },
				    	success:function(response){
					    	Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);
					    	form.findField('exceptionType').setValue('');
							form.findField('exceptionLink').setValue('');
							form.findField('waybillNo').setValue('');
							form.findField('serialNo').setValue('');
					    	addExceptionWin.close();
			    		},			    		
			    		exception: function(response){
							var json = Ext.decode(response.responseText);
	              			Ext.ux.Toast.msg('保存失败', json.message, 'error', 2000);
						}
					});
				}else{
					Ext.ux.Toast.msg('提示', '异常类型、异常环节、运单号有误!', 'error', 3000);
				}
			}
		}]
	}]
});	

//通知部门
Ext.define('Foss.predeliver.exceptionProcess.AddNoticeForm',{
	extend:'Ext.form.Panel',	
	id:'Foss_predeliver_exceptionProcess_AddNoticeForm_Id',
	title: '在线通知',
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[{
			name: 'receiveOrgCode',
			fieldLabel: '通知接收部门code',
			hidden: true,
			columnWidth: 1
		},{
			name: 'receiveOrgName',
			fieldLabel: '通知接收部门',
			disabled:true,
			labelWidth: 100,
			allowBlank:false,
			columnWidth: 1
		},{
			name:'noticeContext',
	        xtype: 'textareafield',
	        grow: true,
	        maxLength:200,
	        fieldLabel: '通知内容',
	        anchor: '100%',
	        columnWidth: 1,
	        allowBlank:false
		},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:'在线通知',
			columnWidth:.2,
			handler:function(){
				var form = this.up('form').getForm();
				var receiveOrgCode = form.findField('receiveOrgCode').getValue();
				var noticeContext = form.findField('noticeContext').getValue();
				if(form.isValid()){
					Ext.Ajax.request({
					    url:predeliver.realPath('addNotice.action'),
					    params:{
					    	'vo.exceptionProcessDetailDto.receiveOrgCode':receiveOrgCode,
					    	'vo.exceptionProcessDetailDto.noticeContext':noticeContext
					    },
				    	success:function(response){
					    	Ext.ux.Toast.msg('提示', '通知成功!', 'ok', 1000);
					    	noticeWin.close();
			    		},			    		
			    		exception: function(response){
							var json = Ext.decode(response.responseText);
	              			Ext.ux.Toast.msg('通知失败', json.message, 'error', 2000);
						}
					});
				}else{
					Ext.ux.Toast.msg('提示', '通知内容不可为空!', 'error', 3000);
				}
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.6,
			html: '&nbsp;'
		},{
			text:'关闭',
			cls:'yellow_button',
			columnWidth:.2,
			handler:function()
			{
				noticeWin.close();
			}
		}]
	}]
});	

//定义查看异常表单
predeliver.exceptionProcess.queryInfoForm = Ext.create('Foss.predeliver.exceptionProcess.QueryInfoForm');
//定义查看异常表格
predeliver.exceptionProcess.queryInfoGrid = Ext.create('Foss.predeliver.exceptionProcess.QueryInfoGrid');
//定义新增异常处理详情表单
predeliver.exceptionProcess.addExceptionProcessForm = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionProcessForm');
//定义新增异常处理详情表格
predeliver.exceptionProcess.addExceptionProcessGrid = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionProcessGrid');
//定义新增异常主体表单
predeliver.exceptionProcess.addExceptionForm = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionForm');
//定义通知表单
predeliver.exceptionProcess.addNoticeForm = Ext.create('Foss.predeliver.exceptionProcess.AddNoticeForm');

//定义查询异常window
Ext.define('Foss.predeliver.exceptionProcess.QueryExceptionWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
//	bodyCls: 'autoHeight',
//	cls: 'autoHeight',
	width : 600,
	height : 500,
	items : [predeliver.exceptionProcess.queryInfoForm,predeliver.exceptionProcess.queryInfoGrid]
});

//新增异常处理结果window
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionProcessWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	width : 600,
	height : 650,
	items : [predeliver.exceptionProcess.addExceptionProcessForm,predeliver.exceptionProcess.addExceptionProcessGrid],
	buttons : [{
				cls : 'yellow_button',
				text : '保存',
				style : {
					float : 'left'
				},
				handler : function() {
						var form = predeliver.exceptionProcess.addExceptionProcessForm.getForm();
						var exceptionProcessId = form.findField('exceptionProcessId').getValue();
						var notes = form.findField('notes').getValue();
						var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
						if(form.isValid()){
							Ext.Ajax.request({
							    url:predeliver.realPath('addExceptionProcessDetailInfo.action'),
							    params:{
							    			 'vo.exceptionProcessConditionDto.tSrvExceptionId':exceptionProcessId,
							    			 'vo.exceptionProcessConditionDto.notes':notes
							    			 },
						    	success:function(response){
							    	Ext.ux.Toast.msg('提示', '提交成功!', 'ok', 1000);
							    	exceptionGrid.store.load();
							    	addWin.close();
					    		},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg('提交失败', json.message, 'error', 3000);
			              			exceptionGrid.store.load();
			              			addWin.close();
								}
							});
						}else{
							Ext.ux.Toast.msg('提示', '处理结果有误，不可为空且不能大于200个字符!', 'error', 3000);
						}
					}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.6,
				html: '&nbsp;'
			},{
				cls : 'yellow_button',
				text : '处理完毕',
				style : {
					float : 'right'
				},
				handler : function() {
						var form = predeliver.exceptionProcess.addExceptionProcessForm.getForm();
						var exceptionProcessId = form.findField('exceptionProcessId').getValue();
						var notes = form.findField('notes').getValue();
						var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
						if(form.isValid()){
							Ext.Ajax.request({
						    url:predeliver.realPath('operateExceptionProcessDetailInfo.action'),
						    params:{
				    			 'vo.exceptionProcessConditionDto.tSrvExceptionId':exceptionProcessId,
				    			 'vo.exceptionProcessConditionDto.notes':notes
				    			 },
					    	success:function(response){
						    	Ext.ux.Toast.msg('提示', '处理完毕!', 'ok', 3000);
						    	exceptionGrid.store.load();
						    	addWin.close();
				    		},
				    		exception: function(response){
								var json = Ext.decode(response.responseText);
		              			Ext.ux.Toast.msg('提交失败', json.message, 'error', 3000);
		              			exceptionGrid.store.load();
		              			addWin.close();
							}
						});
						}else{
							Ext.ux.Toast.msg('提示', '处理结果不许为空!', 'error', 3000);
						}
				}
			}]
});	

//新增异常
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
//	bodyCls: 'autoHeight',
//	cls: 'autoHeight',
	width : 600,
	height : 380,
	items : [predeliver.exceptionProcess.addExceptionForm]
});	

//在线通知
Ext.define('Foss.predeliver.exceptionProcess.AddNoticeWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
//	bodyCls: 'autoHeight',
//	cls: 'autoHeight',
	width : 500,
	height : 300,
	items : [predeliver.exceptionProcess.addNoticeForm]
});	

Ext.onReady(function() {
	Ext.QuickTips.init();
	predeliver.exceptionProcess.exceptionForm = Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessForm'); 
	predeliver.exceptionProcess.exceptionGrid = Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessGrid');
	Ext.create('Ext.panel.Panel',{
		id: 'T_predeliver-exceptionProcessIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [predeliver.exceptionProcess.exceptionForm,predeliver.exceptionProcess.exceptionGrid],
		renderTo: 'T_predeliver-exceptionProcessIndex-body'
	});
});


