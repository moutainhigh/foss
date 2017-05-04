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

(function(){
	predeliver.exceptionProcess.exceptionStore =FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE', null);
	predeliver.exceptionProcess.exceptionStore.removeAt(predeliver.exceptionProcess.exceptionStore.find('valueCode','DELIVER'));
	Ext.Ajax.request({
		url:predeliver.realPath('queryCity.action'),
		async : false,
		success:function(response){
			var json = Ext.decode(response.responseText);
			Ext.apply(predeliver.exceptionProcess,{
				city : json.vo.city
			});
		}
	});
})();

/**
 * 为对象属性添加前缀
 * @param obj 传入对象
 * @param prevName 前缀名称
 * @returns
 */
function addPrev(obj, prevName){
  if (Ext.isObject(obj)){
    for (var attr in obj){
      var keyName = prevName + '.' + attr;
      obj[keyName] = obj[attr];
      delete obj[attr];
    }
  } 
}


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
				{ name : 'receiveBigCustomer'}, /** 收货人大客户标示 */
				{ name: 'receiveCustomerMobilephone'},  //收货人手机
				{ name: 'receiveCustomerPhone'},  //收货人电话
				{ name: 'exceptionType'},  //异常类型
				{ name: 'exceptionLink'},  //异常环节
				{ name: 'serialNo'},  //流水号
				{ name: 'receiveOrgCode'},  //收货部门
				{ name: 'receiveOrgName'},  //收货部门
				{ name: 'receiveOrgTel'},  //收货部门电话
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
				{ name: 'modifyTime',type:'date',
						 convert:function(value){
							 if(value!=null){
								 var date = new Date(value);
								 return date;
							 }else{
								 return null;
							 }
						 }}, //更新时间
				{ name: 'exceptionReason'},  //异常原因
				{ name: 'storageDay'},  //库存天数
				{ name: 'arrivesheetId'},  //到达联id
				{ name: 'exceptionOperate'},  //异常操作
				{ name: 'noticeContext'},  //通知内容
				{ name: 'countyName'}, //弃货类型
				{ name: 'deliverDate',type:'date', //预计送货日期
					 convert:function(value){
						 if(value!=null){
							 var date = new Date(value);
							 return date;
						 }else{
							 return null;
						 }
					 }},
				{ name: 'signRecord'} //签收记录
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
				{ name: 'operateOrgName'},//处理人部门
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
				{ name: 'deliveryCustomerContact'},//发货人
				{ name: 'deliveryCustomerMobilephone'}, //发货人手机
				{ name: 'goodsVolumeTotal'}, //体积(方)
				{ name: 'inStockTime',type:'date',
				convert : dateConvert}, //入库时间
				{ name: 'storageDay'},  //仓储时长(天)
				{ name: 'operator'},  //操作人
				{ name: 'exceptionId'}, //异常_ID
			//	{ name: 'customerCooperateStatus'}, //客户配合
				{ name: 'notes'}, //弃货事由
				{ name: 'abandonedgoodsType'} //弃货类型
			 ]
});
//创建处理异常枚举store
Ext.define('Foss.predeliver.exceptionProcess.ExceptionTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'serialNo',  type: 'string'}
	],
	data: {
		'items':[]
	},
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
			
			var form = Ext.getCmp('Foss_Exception_Form_ExceptionProcessForm_ID').getForm();
			// 验证运单号输入的行数
			if (!Ext.isEmpty(queryParams.waybillNo)) {
				var arrayWaybillNo = queryParams.waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.valitation'), 'error', 3000); // 运单号为8到10位数字，以回车输入，最多输入50个运单号。
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.valitation'), 'error', 3000); // 运单号为8到10位数字，以回车输入，最多输入50个运单号。
						return false;	
					}
				}
			}
			if (!form.isValid()){
				return false;
			}
					
			Ext.apply(operation, {
				params : {					
						'vo.exceptionProcessConditionDto.storageDayBegin': queryParams.storageDayBegin,
				    	'vo.exceptionProcessConditionDto.storageDayEnd': queryParams.storageDayEnd,
				    	'vo.exceptionProcessConditionDto.exceptionTimeBegin': queryParams.exceptionTimeBegin,
				    	'vo.exceptionProcessConditionDto.exceptionTimeEnd': queryParams.exceptionTimeEnd,
				    	'vo.exceptionProcessConditionDto.modifyTimeBegin': queryParams.modifyTimeBegin,
				    	'vo.exceptionProcessConditionDto.modifyTimeEnd': queryParams.modifyTimeEnd,
				    	'vo.exceptionProcessConditionDto.exceptionType': queryParams.exceptionType,
				    	'vo.exceptionProcessConditionDto.status': queryParams.status,
				    	'vo.exceptionProcessConditionDto.exceptionLink': queryParams.exceptionLink,
				    	'vo.exceptionProcessConditionDto.waybillNo': queryParams.waybillNo,
				    	'vo.exceptionProcessConditionDto.countyCode': queryParams.district,
				    	'vo.exceptionProcessConditionDto.exceptionOperate': queryParams.exceptionOperate,
              'vo.exceptionProcessConditionDto.signRecord': queryParams.signRecord
				    	//TODO
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
        type: 'ajax',
        url: predeliver.realPath('findExceptionProcessDetailList.action'),
        reader: {
            type: 'json',
            root: 'exceptionProcessDetailList'
        }
	}
});

//查询条件
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessForm',{
	extend:'Ext.form.Panel',
	title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.queryCondition'),//查询条件,
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
		name:'waybillNo',
		xtype : 'textarea',
		fieldLabel:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo'),//运单号
		columnWidth : 0.25,
		labelWidth: 65,
		height: 90,
		emptyText : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.valitation')
	},{
		xtype:'numberfield',
		hideTrigger: true,
		allowDecimals : false,// 不允许有小数点
		name:'storageDayBegin', 
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.storageDayBegin'),//库存时间(天数),
		minValue: 1,
		width: 20,
		margin: '10 18 0 0',
		maxLength:20,//最大位数20
		columnWidth: .15
	},{
		xtype:'numberfield',
		hideTrigger: true,
		labelAlign: 'center',
		allowDecimals : false,// 不允许有小数点
		name:'storageDayEnd', 
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.to'),//至,
		labelWidth: 20,
		margin: '10 108 0 -20',
		minValue: 1,
		length: 50,
		maxLength:20,//最大位数20
		columnWidth: .14
	},{//签收记录
			xtype: 'combobox',
			name: 'signRecord',
			fieldLabel: '签收记录',
			displayField:'name',
			margin: '10 0 0 -107',
			value:'',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			editable: false,
			columnWidth: .05,
			labelWidth: 65,
			width: 70,
			store: Ext.create('Ext.data.Store',{
				fields: ['code','name'],
				data: [{'code':'','name':'全部'},
								{'code':'hr', 'name':'有'},{'code':'nr', 'name':'无'}]
			})
		},{
		xtype: 'rangeDateField',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionTime'),//登记时间,
		fieldId: 'FOSS_ExceptionTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'exceptionTimeBegin',
		fieldId:'Foss_exceptionProcess_exceptionTime',
		toName: 'exceptionTimeEnd',
		disallowBlank: true,
		editable:false,
		labelWidth: 65,
		margin: '10 0 0 20',
		dateRange : 7,
		fromValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate(new Date(),-3),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .4
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'status',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionStatus'),//异常状态,
		columnWidth: 0.17,
		labelWidth: 65,
		value:'HANDLING',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_STATE', null, {
			'valueCode': 'ALL',
            'valueName': predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.all')
		})	
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'exceptionType',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionType'),//异常类型,
		columnWidth: 0.18,
		labelWidth: 65,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE', null, {
			'valueCode': 'ALL',
            'valueName': predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.all')
		})	
	},{
		xtype: 'rangeDateField',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.modifyTime'),//更新时间,
		fieldId: 'FOSS_ModifyTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'modifyTimeBegin',
		fieldId:'Foss_exceptionProcess_modifyTime',
		toName: 'modifyTimeEnd',
		//disallowBlank: true,
		editable:false,
		labelWidth: 65,
		dateRange : 7,
		//fromValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate(new Date(),-3),'Y-m-d H:i:s'),
		//toValue: Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .4
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		labelWidth: 65,
		name: 'exceptionOperate',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionOperate'),//异常操作
		columnWidth: 0.17,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_OPERATE', null, {
			'valueCode': 'ALL',
            'valueName': predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.all')
		})	
	},{
	    xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'exceptionLink',
		fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionLink'),//异常环节,
		columnWidth: 0.18,
		labelWidth: 65,
		value:'ALL',
		store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE', null, {
			'valueCode': 'ALL',
            'valueName': predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.all')
		})	
	},{
        //fieldLabel : '行政区域',
        columnWidth: .4,
        provinceLabelWidth:65,
        provinceWidth : 190,
        allowBlank:true,
        cityWidth : 87,
        cityLabel : '',
        cityName : 'cityCode',//名称
        provinceLabel : '行政区域',
        provinceName:'privateCode',//省名称
        areaLabel : '',
        areaName : 'district',// 县名称
        areaWidth : 87,
		areaIsBlank : true,
		cityIsBlank : true,
		provinceIsBlank : true,
        type : 'P-C-C',
        xtype : 'linkregincombselector'
    },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.reset'),//重置,
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.reset();
//				form.findField('storageDayBegin').setValue('');
//				form.findField('storageDayEnd').setValue('');
				form.findField('exceptionTimeBegin').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate(new Date(),-3),'Y-m-d H:i:s'));
				form.findField('exceptionTimeEnd').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
//				form.findField('modifyTimeBegin').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate(null,-3),'Y-m-d H:i:s'));
//				form.findField('modifyTimeEnd').setValue(Ext.Date.format(predeliver.exceptionProcess.getTargetDate1(null,0),'Y-m-d H:i:s'));
				form.findField('exceptionType').setValue('ALL');
				form.findField('status').setValue('HANDLING');
				form.findField('exceptionLink').setValue('ALL');
//				form.findField('privateCode').setValue('');
//				form.findField('cityCode').setValue('');
//				form.findField('district').setValue('');
//				form.findField('waybillNo').setValue('');
				form.findField('exceptionOperate').setValue('ALL');
				
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.query'),//查询,
			disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexquerybutton'),
			hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function()
			{	
				predeliver.pagingBar.moveFirst();
			}
		}]
	}]
});

var addWin = null;
var noticeWin = null;
var addExceptionWin = null;
//获得当前部门是否营业部
var dept = FossUserContext. getCurrentDept().code;

//异常信息
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id',
	emptyText:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.emptyText'),//查询结果为空！,
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
	//title:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionRecords'),//异常记录,
	collapsible: true,
	animCollapse: true,
	selModel : null,
  tbar : [{
  xtype: 'label',
  text: '异常记录 ',
  width : 110,
  margin : '10 0 0 0',
  style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:5px solid #3d74b7;'
  },{
      xtype : 'image',
      imgCls : 'foss_icons_pkp_waitToBeDisposed'
    },
    {
      xtype : 'label',
      text : '有签收记录'
    },'->'
  ],
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
			//如果有签收记录，则标记为绿色
			if(!Ext.isEmpty(record.get('signRecord'))) {
				return 'foss_icons_pkp_exception_hasSignRecord';
			}
	 	}
	},
	//定义表格列信息
	columns: [Ext.create('Ext.grid.RowNumberer'),{
			xtype:'actioncolumn',
			width:100,
			text: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operate'),//操作,
			align: 'center',
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.edit'),//编辑,
				disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexeditbutton'),
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
					
					var addExceptionProcessWindow = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionProcessWindow');
					//FORM匹配
					//.store.loadData(result.vo.exceptionProcessDetailDto.exceptionProcessDetailEntityList);
					addExceptionProcessWindow.down('#addExceptionProcessGridItemId').getStore().load({
						params : {
							'exceptionProcessId': tSrvExceptionId
						}
					});
					
					Ext.Ajax.request({
					    url:predeliver.realPath('queryExceptionProcessDetailInfo.action'),
					    params: {
					    	'vo.exceptionProcessConditionDto.tSrvExceptionId':tSrvExceptionId
					    },
					    success: function(response){
					        var result = Ext.decode(response.responseText);
					        var exceptionProcessVoModel = Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessVoModel', result.vo.exceptionProcessDetailDto) ;
					   		var noticeCustomerModel = Ext.create('Foss.predeliver.exceptionProcess.WaybillInfoModel', result.vo.exceptionProcessDetailDto.notifyCustomerDto);
					   		var addExceptionProcessForm = addExceptionProcessWindow.down('#addExceptionProcessFormItemId');
					   		addExceptionProcessForm.loadRecord(exceptionProcessVoModel);
					   		var actualAddress = addExceptionProcessForm.down('linkregincombselector');
					   		actualAddress.setReginValue(exceptionProcessVoModel.get('actualProvN'), exceptionProcessVoModel.get('actualProvCode'), '1');
					   		actualAddress.setReginValue(exceptionProcessVoModel.get('actualCityN'), exceptionProcessVoModel.get('actualCityCode'), '2');
					   		actualAddress.setReginValue(exceptionProcessVoModel.get('actualDistrictN'), exceptionProcessVoModel.get('actualDistrictCode'), '3');
					   	
					    	addExceptionProcessWindow.down('#noticeCustomerWaybillInfoFormItemId').loadRecord(noticeCustomerModel);
					    	var temp;
					    	if (result.vo.exceptionProcessDetailDto.notifyCustomerDto.isOrPayStatus) {
					    		temp = result.vo.exceptionProcessDetailDto.toPayAmount + "【" + result.vo.exceptionProcessDetailDto.notifyCustomerDto.isOrPayStatus + "】";
					    	} else {
					    		temp = result.vo.exceptionProcessDetailDto.toPayAmount;
					    	}
					    	
					    	if ('LABELEDGOOD_EXCEPTION' == result.vo.exceptionProcessDetailDto.exceptionType) {
					    		addExceptionProcessForm.down('combobox[name=exceptionType]').setDisabled(true);
					    	}
					    	
					    	addExceptionProcessWindow.down('#addExceptionProcessFormItemId').down('textfield[name=toPayAmount]').setValue(temp);
					    }
					});
					
					addExceptionProcessWindow.show();
				}
			},{
				iconCls: 'deppon_icons_showdetail',
				//tooltip标准的快捷提示实现，用于悬浮在目标元素之上时出现的提示信息。
				tooltip: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.showdetail'),//查看,
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
							
							predeliver.exceptionProcess.queryInfoGrid.getStore().load({
								params : {
									'exceptionProcessId': tSrvExceptionId
								}
							});
							var queryWin = Ext.create('Foss.predeliver.exceptionProcess.QueryExceptionWindow').show();
						}
					});
					
				}
			},{
				iconCls: 'deppon_icons_notice',
				tooltip: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.notice'),//通知,
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
					//选中那一行的信息
					var selection = grid.getStore().getAt(rowIndex);
					var	receiveOrgName = selection.get('receiveOrgName');
					var receiveOrgCode = selection.get('receiveOrgCode');
					var waybillNo = selection.get('waybillNo');
					var notes = null;
					var exceptionType = null;
					if(selection.get('exceptionType') == 'WAYBILL_EXCEPTION')
					{
						exceptionType = predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillException');
					}else{
						exceptionType = predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.goodsException');
					}
					 
					if(selection.get('notes')!=null)
					{
						notes = selection.get('notes');
					}else{
						notes = exceptionType;
					}	
					
					var noticeContext = receiveOrgName +predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeWaybillNo')+ waybillNo+predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeReason')+ notes +predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeErrorInfo')+ exceptionType;
					predeliver.exceptionProcess.noticeContext=new Object();
					predeliver.exceptionProcess.noticeContext.receiveOrgName=receiveOrgName;
					predeliver.exceptionProcess.noticeContext.waybillNo=waybillNo;
					predeliver.exceptionProcess.noticeContext.notes=notes;
					predeliver.exceptionProcess.noticeContext.exceptionType=exceptionType;
					var form = predeliver.exceptionProcess.addNoticeForm.getForm();
					//设置表单的文本内容
					form.findField('receiveOrgName').setValue(receiveOrgName);
					form.findField('receiveOrgCode').setValue(receiveOrgCode);
					form.findField('noticeContext').setValue(noticeContext);
					noticeWin = Ext.create('Foss.predeliver.exceptionProcess.AddNoticeWindow').show();
				}
			}]
		},{
			header:'id',//id,
			dateIndex:'exceptionProcessId',
			hidden: true,
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				return record.get('exceptionProcessId');
			},
			align: 'center'
		},{
			header:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.customerPickupOrgCode'),//到达部门,
			dateIndex:'customerPickupOrgCode',
			hidden: true,
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				return record.get('customerPickupOrgCode');
			},
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo'),//运单号, 
			dataIndex: 'waybillNo',
			width:80,
			align: 'center'
		},{ 
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionStatus'),//异常状态, 
			dataIndex: 'status',
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerName'),//收货人, 
			dataIndex: 'receiveCustomerName', 
			width:80,
			align: 'center',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				//标示大客户
			  	if(record.data.receiveBigCustomer == 'Y'){
			  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
			  	}
			  	return value;
			}
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerMobilephone'),//收货人手机, 
			dataIndex: 'receiveCustomerMobilephone', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerPhone'),//收货人电话, 
			dataIndex: 'receiveCustomerPhone', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionType'),//异常类型, 
			dataIndex: 'exceptionType', 
			width:80,
			align: 'center',
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(
						value, 'PKP_EXCEPTION_TYPE');
			}
		},
		{ 
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionOperate'),//异常操作, 
			dataIndex: 'exceptionOperate',
			width:80,
			align: 'center',
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(
						value, 'PKP_EXCEPTION_OPERATE');
			
				
			}
		},
		{ 
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeContext'),//通知内容, 
			dataIndex: 'noticeContext',
			xtype : 'ellipsiscolumn',
			width:80,
			align: 'center'
		},
		{
		header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionReason'),//异常原因, 
		dataIndex: 'exceptionReason', 
		xtype : 'ellipsiscolumn',
		width:80,
		align: 'center'
	},
		
//		,{ 
//			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionReason'),//异常原因, 
//			dataIndex: 'selectExceptionOperate',
//			xtype : 'ellipsiscolumn',
//			width:80,
//			align: 'center'
//		},
		{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionLink'),//异常环节, 
			dataIndex: 'exceptionLink', 
			width:80,
			align: 'center',
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(
						value, 'PKP_EXCEPTION_PHASE');
			}
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.serialNo'),//流水号, 
			dataIndex: 'serialNo', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveOrgName'),//收货部门, 
			dataIndex: 'receiveOrgName', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveOrgName'),//收货部门, 
			dataIndex: 'receiveOrgCode', 
			hidden: true,
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.district'),//行政区域, 
			dataIndex: 'countyName', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.registerUserName'),//登记人, 
			dataIndex: 'createUserName', 
			width:80,
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateTime'),//登记时间, 
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
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.modifyTime'),//更新时间, 
			dataIndex: 'modifyTime', 
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
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receivepick'),//出发/到达, 
			dataIndex: 'receivepick', 
			width:80,
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('receiveOrgCode')==dept){
					return predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.departure');
				}else if(record.get('customerPickupOrgCode')==dept){ 
					return predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.arrive');
				}else{
					return null;
				}
			},
			align: 'center'
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.storageDays'),//库存天数, 
			dataIndex: 'storageDay', 
			width:80,
			align: 'center'
		}],
	    listeners: {
	        itemclick:function(dv, record, item, index, e){
	        	// 异常状态
				var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_EXCEPTION_STATE');
				if(status != 'HANDLING'){
					Ext.getCmp('Foss_predeliver_exception_abandonGoods_Id').setDisabled(true);
				}else{
					Ext.getCmp('Foss_predeliver_exception_abandonGoods_Id').setDisabled(false);
				}
	        }
		},
		abandonedGoodsWin: null,
		getAbandonedGoodsWin: function(){
			if(Ext.isEmpty(this.abandonedGoodsWin)){
				this.abandonedGoodsWin = Ext.create('Foss.HandlingExceptions.Window.AbandonedGoods');
				predeliver.exceptionProcess.abandonedGoodsWin = this.abandonedGoodsWin;
			}
			return this.abandonedGoodsWin;
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				listeners:{
					'beforerenderer':function(record, store) {
						var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_EXCEPTION_STATE');
				        if(status == 'HANDLING'){
				            return false;  //不能进行选择
				        }else{   
				           return true;
				        }
					}
				}
			});
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
					name:'batchProcess',
					columnWidth:.1,
					text: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.batchProcess'), // 批量处理
					handler: function(grid, rowIndex, colIndex) {
						var records = predeliver.exceptionProcess.exceptionGrid.getSelectionModel().getSelection();
						var isReject = false;//异常原因是否包含拒收
			            var arrayObj = new Array();
			            var returnMsg = '';
			            for (var i = 0; i < records.length; i++){
			              if(!Ext.isEmpty(records[i].get('exceptionReason'))&&records[i].get('exceptionReason').indexOf('拒收')!=-1){
			            	isReject = true;
			                arrayObj.push(records[i].get('waybillNo'));
			              }
			            }
			            if(isReject){
			              if(arrayObj.length>2){
			                returnMsg = predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.batchRejectRecordBegin')+arrayObj[0]+'、'+arrayObj[1]+'...'+
			                predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.batchRejectRecordEnd');
			              }else{
			                returnMsg = predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.batchRejectRecordBegin')+arrayObj.join('、')+
			                predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.batchRejectRecordEnd');
			              }
			              Ext.Msg.confirm(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'),returnMsg,function(o){
			                if(o=='yes'){
			                  if(records.length < 1){
			                    Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.choseOperateData'), 'error', 3000);
			                    return;
			                  }
			                  Ext.create('Foss.predeliver.exceptionProcess.BatchProcessWindow',{chooseRecords : records}).show();
			                }else{
			                  return false;
			                }
			              });			                  
			            }else{
			              if(records.length < 1){
			                Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.choseOperateData'), 'error', 3000);
			                return;
			              }
			              Ext.create('Foss.predeliver.exceptionProcess.BatchProcessWindow',{chooseRecords : records}).show();
			            }
			            
					}
				},{
					xtype:'button',
					allowBlank:true,
					name:'addException',
					columnWidth:.1,
					text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.addException'),//新增异常,
					handler: function(grid, rowIndex, colIndex) {
						addExceptionWin = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionWindow').show();
						predeliver.exceptionProcess.addExceptionForm.getForm().reset();
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
					text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.abandonGoodsApplication'),//转弃货申请,
					disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexabandonbutton'),
					hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexabandonbutton'),
						handler: function() {
							var gridRow = predeliver.exceptionProcess.exceptionGrid.getSelectionModel().getSelection()[0];
							
							if(typeof gridRow == "undefined"){
								Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.choseOperateData'), 'error', 3000);
								return;
							}
							if(gridRow.get('exceptionType')== "LABELEDGOOD_EXCEPTION"){
								Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.labeledgoodExceptionCantAbandon'), 'error', 3000);
								return;
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
									 	var result = Ext.decode(response.responseText),
									 		model = Ext.ModelManager.create(result.vo.abandonGoodsApplicationDto,'Foss.predeliver.exceptionProcess.AbandonGoodsDetailModel'),
									 		window = me.getAbandonedGoodsWin(),
									 		form = window.getAbandonGoodsDetailViewForm();
										form.loadRecord(model);
										window.show();
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
					text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.export'),//导出,
					disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexexportbutton'),
					hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexexportbutton'),
					handler : function(){
						var queryParams = predeliver.exceptionProcess.exceptionForm.getValues();
						if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}	
						//获取查询出来的异常信息
						var exceptionGridStore = predeliver.exceptionProcess.exceptionGrid.getStore();
						//若异常信息不为空
						if(exceptionGridStore.getCount()!=0){
							Ext.Ajax.request({
								url:predeliver.realPath('exportExcel.action'),
								form: Ext.fly('downloadAttachFileForm'),
								method : 'POST',
								params : {					
									'vo.exceptionProcessConditionDto.storageDayBegin': queryParams.storageDayBegin,
							    	'vo.exceptionProcessConditionDto.storageDayEnd': queryParams.storageDayEnd,
							    	'vo.exceptionProcessConditionDto.exceptionTimeBegin': queryParams.exceptionTimeBegin,
							    	'vo.exceptionProcessConditionDto.exceptionTimeEnd': queryParams.exceptionTimeEnd,
							    	'vo.exceptionProcessConditionDto.modifyTimeBegin': queryParams.modifyTimeBegin,
							    	'vo.exceptionProcessConditionDto.modifyTimeEnd': queryParams.modifyTimeEnd,
							    	'vo.exceptionProcessConditionDto.exceptionType': queryParams.exceptionType,
							    	'vo.exceptionProcessConditionDto.status': queryParams.status,
							    	'vo.exceptionProcessConditionDto.exceptionLink': queryParams.exceptionLink,
							    	'vo.exceptionProcessConditionDto.waybillNo': queryParams.waybillNo,
							    	'vo.exceptionProcessConditionDto.countyCode': queryParams.countyCode,
							    	'vo.exceptionProcessConditionDto.exceptionOperate': queryParams.exceptionOperate,
//							    	'vo.exceptionProcessConditionDto.notificationContent': queryParams.notificationContent,
							    	'vo.exceptionProcessConditionDto.exceptionReason': queryParams.exceptionReason
								},
								isUpload: true
							/*	success:function(response){
									alert(1);
								},
								exception : function(response) {
									alert(2);
								}*/
							});
						}else{
							//或者提示不能导出
							Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'),predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
						}
						
					}
				}]
			}],

			//添加分页工具条
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
			});
			predeliver.pagingBar = me.bbar;
			predeliver.store = me.store;
			me.callParent([cfg]);
		}
	});
// 上传付款文件的panel
Ext.define('Foss.predeliver.Grid.FileUploadGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			modulePath : 'pkp-predeliver',
			title : predeliver.exceptionProcess.i18n('pkp.predeliver.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/predeliver/uploadFiles.action',
//			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/predeliver/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/predeliver/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/predeliver/reviewImg.action'
		});
//新增弃货信息表单
Ext.define('Foss.predeliver.exceptionProcess.AbandonGoodsDetailForm', {
	extend : 'Ext.form.Panel',
//	cls:'autoHeight',
//	bodyCls:'autoHeight',
	style:{border:'0px'},
	defaultType: 'textfield',
	defaults: {
			margin:'5 5 5 5',
			labelWidth:90
		},
	layout:'column',
	frame:true,
	collapsible: true,
	animCollapse: true,
	title : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.addAbandonedGoods'),//新增预弃货信息,
	fileUploadGrid : null,
	getFileUploadGrid : function() {
		if (this.fileUploadGrid == null) {
			this.fileUploadGrid = Ext.create(
					'Foss.predeliver.Grid.FileUploadGrid', {
						columnWidth : 1
					});
		}
		return this.fileUploadGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						xtype : 'hiddenfield',
						name : 'exceptionId' // 异常_ID
					}, {
						xtype : 'hiddenfield',
						name : 'abandonedgoodsType' // 弃货类型
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.waybillNo'),// 运单号,
						name : 'waybillNo',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.origOrgName'),// 始发部门,
						name : 'origOrgName',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.deliveryCustomerName'),// 发货人,
						name : 'deliveryCustomerContact',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.deliveryCustomerMobilephone'),// 发货人手机,
						name : 'deliveryCustomerMobilephone',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.goodsVolumeTotal'),// 体积(方),
						name : 'goodsVolumeTotal',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.inStockTime'),// 入库时间,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						name : 'inStockTime',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.storageDay'),// 仓储时长(天),
						name : 'storageDay',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.exceptionProcess
								.i18n('pkp.predeliver.exceptionProcess.operator'),// 操作人,
						name : 'operator',
						readOnly : true,
						columnWidth : .5
					},{
						fieldLabel : '弃货事由',
						name : 'notes',
						xtype: 'textarea',
						height : 80,
						width : 600,
						allowBlank:false,
						colspan:3,
						maxLength:300,
						columnWidth : 1
					}, me.getFileUploadGrid()],
			buttons: [{
					text : predeliver.exceptionProcess
							.i18n('pkp.predeliver.exceptionProcess.close'),// 关闭,
					handler : function() {
						predeliver.exceptionProcess.abandonedGoodsWin.close();
					}
				},'->', {
					cls : 'yellow_button',
					text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submit'),// 提交,
					disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexsubmitbutton'),
					hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexsubmitbutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					handler : function() {
						var serachParms = this.up('form').getForm()
								.getValues();
						var fileGrid = me.getFileUploadGrid();
						var filearray = new Array();
						fileGrid.getStore().each(function(record) {
									filearray.push({
												'id' : record.get('id')
											});
								});
						if(!this.up('form').getForm().isValid()){
							return;
						}
						if(filearray.length==0){
							Ext.ux.Toast
							.msg(predeliver.exceptionProcess
									.i18n('pkp.predeliver.exceptionProcess.tip'),
									'请上传附件。', 3000);
							return;
						}
						Ext.Ajax.request({
							url : predeliver
									.realPath('createAbandonGoodsApplication.action'),
							method : 'POST',
							jsonData : {
								'vo' : {
									'abandonGoodsApplicationEntity' : {
										'waybillNo' : serachParms.waybillNo,
										'tSrvExceptionId' : serachParms.exceptionId,
										'abandonedgoodsType' : serachParms.abandonedgoodsType,
										'notes' : serachParms.notes
									},
									'attachementFiles' : filearray
								}
							},
							success : function(response) {
								var json = Ext
										.decode(response.responseText);
								Ext.ux.Toast
										.msg(
												predeliver.exceptionProcess
														.i18n('pkp.predeliver.exceptionProcess.tip'),
												predeliver.exceptionProcess
														.i18n('pkp.predeliver.exceptionProcess.saveSuccess'),
												'ok', 1000);
								me.getFileUploadGrid().getStore().removeAll();
								predeliver.exceptionProcess.abandonedGoodsWin.close();
								//刷新结果数据
								predeliver.pagingBar.moveFirst();
							},
							exception : function(response) {
								var result = Ext
										.decode(response.responseText);
								Ext.ux.Toast
										.msg(
												predeliver.exceptionProcess
														.i18n('pkp.predeliver.exceptionProcess.tip'),
												result.message,
												'error', 3000);
							}
						});

					}
				}]
		});

		me.callParent(arguments);
	}
});

//新增弃货信息窗口
Ext.define('Foss.HandlingExceptions.Window.AbandonedGoods', {
	extend:'Ext.window.Window',
	closeAction:'hide',
	width: 550,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	bodyCls: 'autoHeight',
	layout: 'auto',
	abandonGoodsDetailViewForm: null,
	getAbandonGoodsDetailViewForm: function(){
		if(this.abandonGoodsDetailViewForm==null){
			this.abandonGoodsDetailViewForm = Ext.create('Foss.predeliver.exceptionProcess.AbandonGoodsDetailForm');
		}
		return this.abandonGoodsDetailViewForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getAbandonGoodsDetailViewForm()];
		me.callParent([cfg]);
	}
});
//处理详情
Ext.define('Foss.predeliver.exceptionProcess.QueryInfoForm',{
	extend:'Ext.form.Panel',	
	title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.processingDetails'),//处理详情,
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
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo'),//运单号,
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
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionType'),//异常类型,
			columnWidth: 0.5,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE')
		},{
			name: 'receiveCustomerName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerName'),//收货人,
			labelWidth: 85,
			columnWidth: .5
		},{	
			name: 'receiveCustomerPhone',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerPhone'),//收货人电话,
			labelWidth: 85,
			columnWidth: .5
		},{			
			name: 'receiveCustomerMobilephone',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerMobilephone'),//收货人手机,
			labelWidth: 85,
			columnWidth: .5
		},{		
			name: 'receiveOrgName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveOrgName'),//收货部门,
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
	emptyText:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.emptyText'),//查询结果为空！,
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//定义表格的标题
	title:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.processingRecords'),//处理历史,
	collapsible: true,
	animCollapse: true,
	store: Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessStore'),	 
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
		{
			
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateTime'),//登记时间, 
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
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operaterName'),//处理人, 
			//关联model中的字段名
			dataIndex: 'operator',
			align: 'center',
			width:160
		},{
			header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionReason'),//异常原因, 
			//查看-处理历史-异常原因
			//关联model中的字段名
			dataIndex: 'notes', 
			align: 'center',
			width:180
		}]
	});	
	
//获取当前操作人
var userName = FossUserContext. getCurrentUserEmp().empName;

//新增异常form
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionForm',{
	extend:'Ext.form.Panel',	
	title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.addException'),//新增异常,
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	serialNoWindow : null,
	getSerialNoWindow : function(){
		if(this.serialNoWindow == null){
			this.serialNoWindow = Ext.create('Foss.predeliver.exceptionProcess.SerialNoWindow');
		}
		return this.serialNoWindow;
	},
	initComponent : function(){
		var me = this;
		me.items = [{
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
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionType'),//异常类型,
			columnWidth: .5,
			allowBlank:false,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE')	,
			 listeners : 
		 	{
		    	'select' : function(combo, records, eOpts)
		    	{
		    		var form = this.up('form').getForm()
		    			form1 = this.up('form');
					if (records[0].get('valueCode') == 'LABELEDGOOD_EXCEPTION') 
					{
						form.findField('serialNo').setValue('');
						form1.query('button[name="choose"]')[0].setDisabled(false);
						form1.query('button[name="cancle"]')[0].setDisabled(false);
						form1.query('button[name="save"]')[0].setDisabled(true);
					}
					else
					{
						form.findField('serialNo').setValue('');
						form1.query('button[name="choose"]')[0].setDisabled(true);
						form1.query('button[name="cancle"]')[0].setDisabled(true);
						form1.query('button[name="save"]')[0].setDisabled(false);
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
			name: 'exceptionLink',
			editable:false,
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionLink'),//异常环节,
			columnWidth: 0.5,
			allowBlank:false,
			store:predeliver.exceptionProcess.exceptionStore	
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},
		{
	        //fieldLabel : '行政区域',
	        columnWidth: .7,
	        provinceLabelWidth:100,
	        provinceWidth : 180,
			allowBlank:true,
	        cityWidth : 80,
	        cityLabel : '',
	        cityName : 'cityCode',//名称
	        provinceLabel : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.district'),//行政区域
	        provinceName:'privateCode',//省名称
	        areaLabel : '',
	        areaName : 'district',// 县名称
	        areaWidth : 95,
			areaIsBlank : true,
			cityIsBlank : true,
			provinceIsBlank : true,
	        type : 'P-C-C',
	        xtype : 'linkregincombselector'
	    },{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
			name: 'waybillNo',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo'),//运单号,
			labelWidth: 100,
			vtype: 'waybill',
			allowBlank:false,
			columnWidth: .5,
			listeners : {
				blur : function(field, event, eOpts) {
					var form = this.up('form').getForm();
					var form1 = this.up('form');
					if (field.value != '') {
						if(!form.findField('waybillNo').isValid()){
							Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.notExist'), 'error', 3000);
						}else {
							if(form.findField('exceptionType').getValue()=='LABELEDGOOD_EXCEPTION')
							{
								form.findField('serialNo').setValue('');
								if(!Ext.isEmpty(form.findField('exceptionType').getValue())){
									form1.query('button[name="save"]')[0].setDisabled(false);//设置提交按钮可编辑
								} else {
									form1.query('button[name="save"]')[0].setDisabled(true);//设置提交按钮可编辑
								}
							}else{
								form1.query('button[name="save"]')[0].setDisabled(false);//设置提交按钮可编辑
							}
						}
					}else
					{
						form.findField('serialNo').setValue('');
					}
					Ext.apply(form1.getSerialNoWindow().getSerialNoGird().getStore(),{chooseRecords : []});
				}
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		}/*,{		
			name: 'serialNo',
		    xtype:'combobox',
			displayField:'serialNo',
			valueField:'serialNo',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.goodsSerialNo'),//货物流水号,
			labelWidth: 100,
			listConfig:{maxHeight:80},
			columnWidth: .5,
			store:	Ext.create('Foss.predeliver.exceptionProcess.ExceptionTypeStore')
		}*/,{
			name: 'serialNo',
			width:300,
			columnWidth : .5,
			readOnly:true,
			fieldLabel : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.goodsSerialNo'),//货物流水号,
			xtype : 'textareafield',
			listeners : {
				change : function(field, newValue, oldValue, eOpts) {
					if(!Ext.isEmpty(oldValue) && !Ext.isEmpty(newValue)
							&& newValue != oldValue){
						this.up('form').query('button[name="save"]')[0].setDisabled(false);
					}
				}
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{
	        text: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.choose'),
	        name : 'choose',
	        xtype : 'button',
	        columnWidth : .15,
	        handler: function() {
	        	var form = this.up('form').getForm();
	        	if(!form.findField('waybillNo').isValid()){
					Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.notExist'), 'error', 3000);
					return;
				}
	        	me.getSerialNoWindow().getSerialNoGird().getStore().load({
	    			params:{
	    				'vo.exceptionProcessConditionDto.waybillNo': me.getForm().findField('waybillNo').getValue()
	    			}
	    		});
	        	Ext.apply(me.getSerialNoWindow().getSerialNoGird().getStore(),{grid : me.getSerialNoWindow().getSerialNoGird()});
	            me.getSerialNoWindow().show();
	        }
		},{
	        text: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.cancle'),
	        name : 'cancle',
	        xtype : 'button',
	        columnWidth : .15,
	        handler: function() {
	        	me.getForm().findField('serialNo').setValue('');
	        	Ext.apply(me.getSerialNoWindow().getSerialNoGird().getStore(),{chooseRecords : []});
	        }
		},{
			xtype: 'container',
			border : false,
			columnWidth:.44,
			height : 30,
			html: '&nbsp;'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.26,
			html: '&nbsp;'
		},{		
			name: 'createUserName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.createUserName'),//上报人,
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
				text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.reset'),//重置,
				columnWidth:.15,
				handler:function(){
					var form = this.up('form').getForm();
					var form1 = this.up('form');
					form.findField('exceptionType').setValue('');
					form.findField('exceptionLink').setValue('');
					form.findField('waybillNo').setValue('');
					form.findField('serialNo').setValue('');
					form.findField('privateCode').setValue('');
					form.findField('cityCode').setValue('');
					form.findField('district').setValue('');
					form1.query('button')[1].setDisabled(true);//设置提交按钮不可编辑
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.7,
				html: '&nbsp;'
			},{
				text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.save'),//保存,
				name  : 'save',
				disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexsavebutton'),
				hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexsavebutton'),
				cls:'yellow_button',
				columnWidth:.15,
				disabled:true,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
				}),
				handler:function()
				{
					var form = this.up('form').getForm(),
						form1 = this.up('form'),
						iexceptionType = form.findField('exceptionType').getValue(),
						iexceptionLink = form.findField('exceptionLink').getValue(),
						iwaybillNo = form.findField('waybillNo').getValue(),
						iserialNo = form.findField('serialNo').getValue()
						district = form.findField('district').getValue();
					
					if(iexceptionType=='LABELEDGOOD_EXCEPTION'&&iserialNo=='')
					{
						Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.serialNoIsNotNull'),'error', 3000);
						return;
					}	
					if(form.isValid()){
						Ext.Ajax.request({
						    url:predeliver.realPath('addExceptionProcessInfo.action'),
						    params:{
						    	'vo.exceptionProcessConditionDto.exceptionType':iexceptionType,
						    	'vo.exceptionProcessConditionDto.exceptionLink':iexceptionLink,
						    	'vo.exceptionProcessConditionDto.waybillNo':iwaybillNo,
						    	'vo.exceptionProcessConditionDto.serialNo':iserialNo,
						    	'vo.exceptionProcessConditionDto.countyCode': district
						    },
					    	success:function(response){
						    	Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.saveSuccess'), 'ok', 1000);
						    	form.findField('exceptionType').setValue('');
								form.findField('exceptionLink').setValue('');
								form.findField('waybillNo').setValue('');
								form.findField('serialNo').setValue('');
								form1.query('button')[1].setDisabled(true);//设置提交按钮不可编辑
						    	addExceptionWin.close();
				    		},			    		
				    		exception: function(response){
								var json = Ext.decode(response.responseText);
		              			Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.saveError'), json.message, 'error', 2000);
							}
						});
					}else{
						Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionTypeError'), 'error', 3000);
					}
				}
			}]
		}];
		me.callParent();
	}
});	

//签收信息--签收件 流水号Model
Ext.define('Foss.predeliver.exceptionProcess.SerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'}// 流水号
	]
});

//签收信息---查询签收件下的流水号Store
Ext.define('Foss.predeliver.exceptionProcess.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.predeliver.exceptionProcess.SerialNoSignOutStorageModel',
	grid : null,
	chooseRecords : [],
	proxy : {
		type : 'ajax',
		async : false,
		url: predeliver.realPath('querySerialNos.action'),
		actionMethods : 'post',//方式
		reader : {
			type : 'json',
			root : 'vo.stockDtoList'
		}
	}
});


//签收流水号GridPanel
Ext.define('Foss.predeliver.exceptionProcess.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:predeliver.exceptionProcess.i18n('pkp.sign.sign.emptyText'),//查询结果为空
	columnLines: true,
	height: 300,//高度
	frame: true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns: [
        {header: predeliver.exceptionProcess.i18n('pkp.sign.sign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center' }//流水号
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.exceptionProcess.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
		me.getView().on('refresh',function(){
			var me = this,
				store = me.getStore(),
				records = store.getRange()
				choose = [];
			for(var j = 0; j < store.chooseRecords.length; j ++){
				for(var i = 0; i < records.length; i ++){
					if(store.chooseRecords[j].get('serialNo') == records[i].get('serialNo')){
						choose.push(records[i]);
						break;
					}
				}
			}
			me.getSelectionModel().select(choose);
	        return ;
		});
	}
});

Ext.define('Foss.predeliver.exceptionProcess.SerialNoWindow', {
	extend : 'Ext.window.Window',
	modal : 'true',
	closeAction : 'hide',
	layout: 'auto',	
	width : 550,
	chooseRecord : null,
	serialNoGrid : null,
	getSerialNoGird : function(){
		if(this.serialNoGrid == null){
			this.serialNoGrid = Ext.create('Foss.predeliver.exceptionProcess.SerialNoOutStorageGridPanel');
		}
		return this.serialNoGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getSerialNoGird(),{
			xtype: 'container',
			border : false,
			layout : 'column',
			items : [{
				xtype : 'container',
				columnWidth : .8,
				border : false,
				html: '&nbsp;'
			},{
				xtype : 'button',
				text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.confirm'),
				columnWidth : .2,
				handler : function(){
					var records = me.getSerialNoGird().getSelectionModel().getSelection(),
						result = [];
					Ext.apply(me.getSerialNoGird().getStore(),{chooseRecords : records});
//					if(records.length > 500){
//						Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.autoChoose'), 'error', 3000);
//						records = records.slice(0,500);
//					}
					for(var i = 0; i < records.length; i ++){
						result.push(records[i].get('serialNo'));
					}
					predeliver.exceptionProcess.addExceptionForm.getForm().findField('serialNo').setValue(result.join());
					me.close();
				}
			}]
		}];
		me.callParent([cfg]);
	}
});


//通知部门
Ext.define('Foss.predeliver.exceptionProcess.AddNoticeForm',{
	extend:'Ext.form.Panel',	
	id:'Foss_predeliver_exceptionProcess_AddNoticeForm_Id',
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[{
			name: 'receiveOrgCode',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveOrgCode'),//通知接收部门code,
			hidden: true,
			columnWidth: 1
		},{
			name: 'receiveOrgName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeReceiveOrgName'),//通知接收部门,
			disabled:true,
			labelWidth: 100,
			allowBlank:false,
			columnWidth: 1
		},{
			name:'noticeContext',
	        xtype: 'textareafield',
	        grow: true,
	        maxLength:200,
	        fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeContext'),//通知内容,
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
			text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.close'),//关闭,
			columnWidth:.2,
			handler:function()
			{
				noticeWin.close();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.6,
			html: '&nbsp;'
		},{
																	
			text:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeOnline'),//在线通知,
			disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexnoticebutton'),
			hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexnoticebutton'),
			cls:'yellow_button',
			columnWidth:.2,
			handler:function(){
				var form = this.up('form').getForm();
				var receiveOrgCode = form.findField('receiveOrgCode').getValue();
				//var noticeContext = form.findField('noticeContext').getValue();
				predeliver.exceptionProcess.noticeContext.receiveOrgCode=receiveOrgCode;
				if(form.isValid()){
					Ext.Ajax.request({
					    url:predeliver.realPath('addNotice.action'),
					    method : 'POST',
					    jsonData : {
							'vo' : {
								'exceptionProcessDetailDto' :  predeliver.exceptionProcess.noticeContext
							}
						},
				    	success:function(response){
					    	Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeSuccess'), 'ok', 1000);
					    	noticeWin.close();
			    		},			    		
			    		exception: function(response){
							var json = Ext.decode(response.responseText);
	              			Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeError'), json.message, 'error', 2000);
						}
					});
				}else{
					Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeContextNotNull'), 'error', 3000);
				}
			}
		}]
	}]
});	

//定义查看异常表单
predeliver.exceptionProcess.queryInfoForm = Ext.create('Foss.predeliver.exceptionProcess.QueryInfoForm');
//定义查看异常表格
predeliver.exceptionProcess.queryInfoGrid = Ext.create('Foss.predeliver.exceptionProcess.QueryInfoGrid');
//定义新增异常主体表单
predeliver.exceptionProcess.addExceptionForm = Ext.create('Foss.predeliver.exceptionProcess.AddExceptionForm');
//定义通知表单
predeliver.exceptionProcess.addNoticeForm = Ext.create('Foss.predeliver.exceptionProcess.AddNoticeForm');

//定义查询异常window
Ext.define('Foss.predeliver.exceptionProcess.QueryExceptionWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	resizable : false,
	width : 600,
	height : 550,
	items : [predeliver.exceptionProcess.queryInfoForm,predeliver.exceptionProcess.queryInfoGrid]
});

// 批量处理form
Ext.define('Foss.predeliver.exceptionProcess.BatchProcessForm',{
	extend:'Ext.form.Panel',	
	title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.addExceptionProcess'),//登记处理异常,
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
			name:'notes',
	        xtype: 'textareafield',
	        maxLength:200,
	        fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.notes'),//处理结果,
	        rowspan: 3,
	        columnWidth: 1,
	        allowBlank:false
	}]
});	

Ext.define('Foss.predeliver.exceptionProcess.BatchProcessWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	resizable : false,
	width : 600,
	height : 300,
	batchProcessForm : null,
	chooseRecords : [],
	getBatchProcessForm : function(){
		if(this.batchProcessForm == null){
			this.batchProcessForm = Ext.create('Foss.predeliver.exceptionProcess.BatchProcessForm');
		}
		return this.batchProcessForm;
	},
	items :  [Ext.create('Foss.predeliver.exceptionProcess.BatchProcessForm')],
	buttons : [{
		cls : 'yellow_button',
		text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.save'),//保存,
		handler : function() {
			var form = this.up('window').getBatchProcessForm(),
				chooseRecords = this.up('window').chooseRecords,
				length = this.up('window').chooseRecords.length,
				param = [],
				list,
				exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
			var notes = form.getForm().findField('notes').getValue();
			if ((notes==null || notes=="" || notes.length > 200)) {
				Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateResultIsError'), 'error', 3000);
				return;
			}
				for(var i = 0 ;i < length; i ++){
					list = {
						'exceptionProcessId' : chooseRecords[i].get('exceptionProcessId'),
						'exceptionType' : chooseRecords[i].get('exceptionType'),
						'exceptionLink' : chooseRecords[i].get('exceptionLink'),
						'notes' : notes
					};
					param.push(list);
				}
				Ext.Ajax.request({
				    url:predeliver.realPath('batchProcess.action'),
				    scope : this,
				    jsonData : {
		    			 'vo':{
		    				 'exceptionProcessDtoList' : param
			    		}
	    			},
			    	success:function(response){
			    		Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitSuccess'), 'ok', 1000);
			    		exceptionGrid.store.load();
			    		this.up('window').close();
		    		},
		    		exception: function(response){
		    			Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
		    			exceptionGrid.store.load();
		    			this.up('window').close();
					}
				});
			
		}
	},{
		xtype: 'container',
		border : false,
		columnWidth:.6,
		html: '&nbsp;'
	},{
		cls : 'yellow_button',
		text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.disposed'),//处理完毕,
		disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexcompletebutton'),
		hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexcompletebutton'),
		handler : function() {
			var form = this.up('window').getBatchProcessForm(),
				chooseRecords = this.up('window').chooseRecords,
				length = this.up('window').chooseRecords.length,
				param = [],
				list,
				exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
			var notes = form.getForm().findField('notes').getValue();
			if (notes==null || notes=="" || notes.length > 200) {
				Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateResultIsError'), 'error', 3000);
				return;
			}
				for(var i = 0 ;i < length; i ++){
					list = {
						'exceptionProcessId' : chooseRecords[i].get('exceptionProcessId'),
						'exceptionType' : chooseRecords[i].get('exceptionType'),
						'exceptionLink' : chooseRecords[i].get('exceptionLink'),
						'notes' : notes,
						'waybillNo' : chooseRecords[i].get('waybillNo'),
						'arrivesheetId' : chooseRecords[i].get('arrivesheetId')
					};
					param.push(list);
				}
				Ext.Ajax.request({
				    url:predeliver.realPath('batchComplete.action'),
				    scope : this,
				    jsonData : {
		    			 'vo':{
		    				 'exceptionProcessDtoList' : param
			    		}
	    			},
			    	success:function(response){
			    		Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.disposed'), 'ok', 3000);
			    		exceptionGrid.store.load();
			    		this.up('window').close();
		    		},
		    		exception: function(response){
		    			Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
		    			exceptionGrid.store.load();
		    			this.up('window').close();
					}
				});
			
		}
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getBatchProcessForm()];
		me.callParent([cfg]);
	}
});

/**
 * 异常值对象Model
 */
Ext.define('Foss.predeliver.exceptionProcess.ExceptionProcessVoModel', {
	extend : 'Ext.data.Model',
	fields : [
		'waybillNo','receiveCustomerName','exceptionType','exceptionLink','receiveCustomerCode',
		'receiveCustomerPhone','receiveCustomerMobilephone','receiveOrgName','receiveCustomerContact',
		'receiveOrgTel','serialNo','createUserName','exceptionProcessId','exceptionReason','arrivesheetId',
		'paymentType','toPayAmount','actualCityCode','actualProvCode','actualDistrictCode','actualAddressDetail',
		'actualProvN','actualCityN','actualDistrictN','actualStreetn','notes',{name: 'deliverDate',  //预计送货日期
					 convert:function(value){
						 if (value != null && value > 0) {
							 var date = new Date(value);
							 return date;
						 } else {
							 return null;
						 }
					 }},'deliveryTimeInterval','deliveryTimeStart',
		'deliveryTimeOver','isExhibition','isEmptyCar','invoiceType', 'invoiceDetail', 'isSentRequired'
	]
});

/**
 * 历史收货地址model
 */
Ext.define('Foss.predeliver.exceptionProcess.ReceiptAddressModel',{
	extend : 'Ext.data.Model',
	fields : ['customerCode', 'customerContactName', 'customerMobilePhone',
	      	'customerName', 'customerPhone', 'id', 'receiveAddressDetails',
	      	'receiveCityCode', 'receiveDistCode', 'receiveProvCode', 'receiveStreet',
	      	'receiveProvName', 'receiveCityName', 'receiveDistName']
});

/**
 * 历史收货地址store
 */
Ext.define('Foss.predeliver.exceptionProcess.ReceiptAddressStore', {
	model : 'Foss.predeliver.exceptionProcess.ReceiptAddressModel',
	proxy: {
        type : 'ajax',
		url : predeliver.realPath("findReceiptAddress.action"),
		actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'customerReceiptAddresses'
        }
    }
});

/**
 * 历史收货地址Window
 */
Ext.define('Foss.predeliver.exceptionProcess.ReceiptAddressWindow', {
	extend : 'Ext.window.Window',
	title : '收货人地址历史信息',
	modal : true,
	width : 800,
	upForm: null,
	resizable : false,
	items : [ {
		xtype : 'grid',
		store : Ext.create('Foss.predeliver.exceptionProcess.ReceiptAddressStore'),
		hideHeaders : true,
		multiSelect : false,
		columns : [ {
			xtype : 'actioncolumn',
			align: 'center',
			width :60,
			items : [ {
				iconCls: 'deppon_icons_delete',
				tooltip :  '删除',
				handler: function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					var id = "";
					if(rec.data.id) {
						id = rec.data.id;
					}
					Ext.Msg.show({
					     title:'提示信息',
					     msg: '确定要作废该客户收货地址吗？', 
					     buttons: Ext.Msg.OKCANCEL,
					     icon: Ext.Msg.QUESTION,
					     fn : function (btnText) {
					    	 if (btnText == 'ok'){
					    	 	addressGrid = new Array();
					    		Ext.Ajax.request({
					    			url : predeliver.realPath("deleteReceiptAddress.action"),
									params : {
										id : id
									},
									success: function(response, opts) {
										var obj = Ext.decode(response.responseText);
									    if(obj) {
									    	if(obj.success) {
									    		grid.getStore().load();
									        	Ext.ux.Toast.msg('提示信息','删除成功', 'success', 3000);/*删除成功 */
									        } else {
									        	Ext.ux.Toast.msg('提示信息', obj.message, 'error', 3000);
									        }
									     }
									 },
									 exception: function(response){
									 	var json = Ext.decode(response.responseText);
					              		Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
									}
								});
					    	 }
					     }
					});
				}
			}]
		}, {
			xtype : 'templatecolumn',
			width :18,
			tpl : '<input type="radio" name="select" id="{id}" />'
		}, {
			xtype : 'gridcolumn',
			flex : 1,
			renderer: function(value, metaData, model) {
				var obj = model.data;
				var str = "";
				str += "客户编码:" + obj.customerCode;
				str += "&nbsp;&nbsp;联系人:" + obj.customerContactName;
				str += "&nbsp;&nbsp;手机:" + obj.customerMobilePhone;
				str += "&nbsp;&nbsp;电话:" + obj.customerPhone;
				str += "&nbsp;&nbsp;地址:" + obj.receiveProvName + obj.receiveCityName + obj.receiveDistName + obj.receiveAddressDetails + (obj.receiveStreet == null ? "": obj.receiveStreet);
							
	            if(str.length > 65){
	            	var strAddress = obj.receiveProvName + obj.receiveCityName + obj.receiveDistName + obj.receiveAddressDetails + (obj.receiveStreet == null ? "": obj.receiveStreet);
	                metaData.tdAttr = 'data-qtip="'+strAddress+'"';
	            }
				return str;
	      }
		} ],
		listeners : {
			'select' : function (rowModel,record,index) {
				Ext.getDom(record.data.id).checked = 'checked';
			}
		}
	} ],
	buttons : [ {
		text : '返回',
		handler : function (btn) {
			var win = btn.up('window');
			win.close();
		}
	},  '->' , {
		text : '确定', 
		handler : function (btn) {
			
			var win = btn.up('window');
			
			var grid = win.down('grid');
			
			var selectionModel = grid.getSelectionModel();
			
			if (!selectionModel.hasSelection()) {
				Ext.Msg.alert('提示', '请选择收货人地址信息!');
				return;
			}
			
			var record = selectionModel.getLastSelected();
			var upForm = win.upForm;
			var actualAddress = upForm.down('linkregincombselector');
			actualAddress.setReginValue(record.get('receiveProvName'), record.get('receiveProvCode'), '1');
			actualAddress.setReginValue(record.get('receiveCityName'), record.get('receiveCityCode'), '2');
			actualAddress.setReginValue(record.get('receiveDistName'), record.get('receiveDistCode'), '3');
			upForm.down('textfield[name=actualAddressDetail]').setValue(record.get('receiveAddressDetails'));
			upForm.down('textfield[name=actualStreetn]').setValue(record.get('receiveStreet'));
			win.close();
		}
	} ]
});

Ext.define('Foss.predeliver.exceptionProcess.WaybillInfoModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'storageCharge'},/**保管费*/
		{name: 'stockStatus'},/**库存状态*/
		{name: 'storageDay'},/**库存天数*/
		{name : 'receiveMethod',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
			}
		}, /** 派送方式 */
		{name : 'receiveCustomerAddress'}, /** 送货地址 */
		{name : 'goodsQtyTotal', type : 'int'}, /** 开单件数 */
		{name : 'arriveGoodsQty', type : 'int'}, /** 到达件数 */
		{name : 'handoverGoodsQty', type : 'int'}, /** 交接件数 */
		{name : 'handoverNo'}, /** 交接单号 */
		{name : 'deliveryCustomerName'}, /** 发货人 */
		{name : 'deliveryCustomerContact'}, /** 发货人联系人 */
		{name : 'goodsName'}, /** 货物名称 */
		{name : 'insuranceAmount', type : 'float'}, /** 货物价值 */
		{name : 'paidMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
			}
		}, /** 付款方式 */
		{name:'paidMethodVir'},
		{name : 'receiveOrgName'}, /** 始发部门 */
		{name : 'productCode',
			convert:function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, /** 运输性质 */
		{name : 'stockQty', type : 'int'}, /** 库存件数 */
		{name : 'goodsWeightTotal', type : 'float'}, /** 总量 */
		{name : 'goodsVolumeTotal', type : 'float'}, /** 体积 */
		{name : 'goodsPackage'}, /** 包装 */
		{name : 'goodsSize'}, /** 尺寸 */
		{name : 'transportFee', type : 'float'}, /** 运费 */
		{name : 'deliveryGoodsFee', type : 'float'}, /** 送货费 */
		{name : 'otherFee', type : 'float'}, /** 其他费用 */
		{name : 'insuranceFee', type : 'float'}, /** 保价费 */
		{name : 'codAmount', type : 'float'}, /** 代收费 */
		{name : 'toPayAmount', type : 'float'}, /** 到付费 */
		{name : 'billTime', type : 'date'}, /** 出发日期 */
		{name : 'notificationTime', type : 'date', 
			convert: function(value) {
				if (value != null && value > 0) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
			} 
		}, /** 上次通知日期 */
		{name : 'receivingHabits'}, /** 收货习惯 */
		{name : 'selectType'}, /** 查询方式 */
		{name : 'receiveOrgName'}, /** 始发部门*/
		{name : 'customerQulification'}, /** 客户资质*/
		{name: 'deliverDate',
			convert: function(value) {
				if (value != null && value > 0) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
			}
		},
		{name : 'packageFee'}, // 包装手续费
		{name : 'transportationRemark'}, // 储运事项
		{name : 'deliveryCustomerMobilephone'}, //发货客户手机
		{name : 'deliveryCustomerPhone'}, // 发货客户电话
		{name : 'isOrPayStatus'},  // 到付的是否网上支付
		{name : 'isExhibitCargo'} //是否会展货
	]
});

Ext.define('predeliver.exceptionProcess.tipPanel', {
	extend: 'Ext.panel.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	//回调方法
	bindData : function(value){
		this.update(value);
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//新增异常处理结果window
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionProcessWindow', {
	extend : 'Ext.window.Window',
	modal : 'true',
	resizable : false,
	autoScroll:true,
	width : 600,
	height : 700,
	items : [ {
		xtype: 'form',
		title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.addExceptionProcess'),//登记处理异常,
		frame:true,
		itemId: 'addExceptionProcessFormItemId',
		defaults: {
			margin: '5 10 5 10',
			labelWidth: 85
		},
		defaultType: 'textfield',
		layout: 'column',
		items:[ {
			name: 'waybillNo',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo'),//运单号,
			columnWidth: .5,
			disabled:true
		}, {
			name: 'receiveCustomerName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerName'),//收货人,
			disabled:true,
			columnWidth: .5
		}, {
			xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			disabled:true,
			name: 'exceptionType',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionType'),//异常类型,
			columnWidth: .5,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_TYPE'),
			listeners: {
				change : function(combox, newValue, oldValue, eOpts ) {
					var win = combox.up('window');
					var form = win.down('#addExceptionProcessFormItemId');
					var flag;
					if (newValue == 'LABELEDGOOD_EXCEPTION') {
						flag = true;
					} else {
						flag = false;
					}
					// 实际收货地址-省市区
					form.down('linkregincombselector').setDisabled(flag);
					// 实际收货地址-明细
					form.down('textfield[name=actualAddressDetail]').setDisabled(flag);
					// 实际收货地址-备注
					form.down('textfield[name=actualStreetn]').setDisabled(flag);
					// 实际收货地址-查询按钮
					form.down('button[name=btnQueryAddress]').setDisabled(flag);
					// 送货时间段
					form.down('combobox[name=deliveryTimeInterval]').setDisabled(flag);
					// 送货时间点-开始
					form.down('timefield[name=deliveryTimeStart]').setDisabled(flag);
					// 送货时间点-结束
					form.down('timefield[name=deliveryTimeOver]').setDisabled(flag);
					// 会展货
					form.down('checkbox[name=isExhibition]').setDisabled(flag);
					// 发票类型
					form.down('combobox[name=invoiceType]').setDisabled(flag);
					// 发票备注
					form.down('textfield[name=invoiceDetail]').setDisabled(flag);
					// 通知并处理完毕按钮
					win.down('#notifyProcessedBtnItemId').setDisabled(flag);
				}
			}
		}, {
			xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			name: 'exceptionLink',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionLink'),//异常环节,
			columnWidth: 0.5,
			disabled:true,
			store:FossDataDictionary.getDataDictionaryStore('PKP_EXCEPTION_PHASE'),
			listeners : {
				change : function(combo, newValue) {
					var form = combo.up('form');
					if (newValue == 'CUSTOMER_NOTICE' || newValue == 'DELIVER' || newValue == 'SEND_ROW_OF_SINGLE'){
						form.query('textfield[name="deliverDate"]')[0].show();
					} else {
						form.query('textfield[name="deliverDate"]')[0].hide();
					}
				}
			}
		}, {
			name: 'receiveCustomerPhone',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerPhone'),//收货人电话,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'receiveCustomerMobilephone',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveCustomerMobilephone'),//收货人手机,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'receiveOrgName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.receiveOrgName'),//收货部门,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'receiveOrgTel',
			fieldLabel: '收货部门电话',//收货部门,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'serialNo',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.serialNo'),//流水号,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'createUserName',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.createUserName'),//上报人,
			disabled:true,
			columnWidth: .5
		}, {
			name: 'exceptionProcessId',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionProcessId'),//异常ID,
			hidden: true,
			columnWidth: .2
		} ,{
			name: 'exceptionReason',
			fieldLabel: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.exceptionReason'),//异常原因,
			hidden: true,
			columnWidth: .2
		}, {
			name:'arrivesheetId',
		    xtype: 'textareafield',
			hidden: true,
			columnWidth: .3
		}, {
			xtype : 'radiogroup',
			vertical : true,
			columnWidth : 0.5,
			name : 'paymentTypeName',
			fieldLabel : '付款方式',
			labelStype : {
				width : '70px'
			},
			items : [ {
				boxLabel : '到付',
				name : 'paymentType',//'到付'
				inputValue : 'CH',
				itemId:'CH', 
				checked : true,
				style: {
					marginTop: '5px'
				}
			}, {
				boxLabel : '月结',
				name : 'paymentType',//'月结'
				inputValue : 'CT', 
				itemId:'CT',  
				disabled : true,
				style: {
					marginTop: '5px'
				}
			}, {
				boxLabel : '临欠',
				name : 'paymentType',//'临欠'
				inputValue : 'DT', 
				itemId:'DT', 
				disabled : true,
				style: {
					marginTop: '5px'
				}
			} ],
			listeners: {
				change : function(radioGroup, newValue, oldValue) {
					radioGroup.down('#' + newValue.paymentType).setDisabled(false);
					radioGroup.down('#' + oldValue.paymentType).setDisabled(true);
				}
			}
		}, {
			name : 'toPayAmount' ,  
			fieldStyle:'color:red;',
			labelWidth:60,
			columnWidth : 0.5,// 到付总额
			fieldLabel : '到付总额', 
			readOnly : true
		}, {
			xtype : 'panel',
			columnWidth: 1,
			collapsed : true,
			collapsible : true,
			title : '实际收货地址(如需修改地址，请点击右边箭头展开)',
			height : 75,
			layout:'column',
			items : [ {
				xtype : 'container',
				columnWidth: 0.9,
				layout:'column',
				items : [ {
					fieldLabel : ' ',
					columnWidth: 1,
					labelSeparator : '',
					labelWidth:85,
					provinceWidth : 119,
					cityWidth : 119,
					cityLabel : '',
					cityName : 'actualCityCode',//市  
					provinceLabel : '',
					provinceName:'actualProvCode',//省
					areaLabel : '',
					areaName : 'actualDistrictCode',// 区(县)
					areaWidth : 119,
					areaIsBlank : true,
					cityIsBlank : true,
					provinceIsBlank : true,
					type : 'P-C-C',
					xtype : 'linkregincombselector'
				}, {
					xtype : 'textfield',
					name: 'actualAddressDetail',//实际收货地址
					columnWidth: .55,
					labelWidth:0,
					maxLength:166,
					margin: '0 0 0 90',
					fieldLabel : '',
					labelSeparator:''
				}, {
					xtype : 'textfield',
					name: 'actualStreetn',//实际收货详细地址
					columnWidth: .445,
					labelWidth:0,
					margin: '0 0 0 0',
					fieldLabel : '',
					labelWidth:100,
					labelSeparator:'',
					maxLength:166,
					emptyText : '地址备注(XX旁边,XX路口等)非必填'
				} ]
			}, {
				xtype : 'button',
				text : '查询',
				name: 'btnQueryAddress',
				height : 45,
				margin: '2 0 0 0',
				columnWidth : 0.1,
				handler: function(btn) {
					var obj = {};
					var form = btn.up('form');
					if (form.down('hidden[name=receiveCustomerContact]').getValue()) {
						obj['customerContactName'] = form.down('hidden[name=receiveCustomerContact]').getValue();
						obj['customerCode'] = form.down('hidden[name=receiveCustomerCode]').getValue();
						obj['customerName'] = form.down('textfield[name=receiveCustomerName]').getValue();
						obj['customerPhone'] = form.down('textfield[name=receiveCustomerPhone]').getValue();
						obj['customerMobilePhone'] = form.down('textfield[name=receiveCustomerMobilephone]').getValue();
						addPrev(obj,'customerReceiptAddressEntity');
						var receiptAddressWindow = Ext.create('Foss.predeliver.exceptionProcess.ReceiptAddressWindow', {
							upForm : form
						});
						var store = receiptAddressWindow.down('grid').getStore();
						store.proxy.extraParams = obj;
						store.load();
						receiptAddressWindow.show();
					}
				}
				
			} ]
		}, {
			name:'notes',
		    xtype: 'textareafield',
		    maxLength:200,
		    fieldLabel: '处理结果<br>(送货要求)',//处理结果,
		    rowspan: 3,
		    columnWidth: 1,
		    allowBlank:false
		}, {
			name : 'deliverDate',
			xtype: 'datefield',
			allowBlank:false,
			columnWidth : .5,
			fieldStyle: 'color:red;font-weight:bold;',
			format : 'Y-m-d',
			fieldLabel : predeliver.exceptionProcess.i18n('pkp.predeliver.notifyDetailsAction.query.planToSendTime')//预计送货日期, 
		}, {
			xtype : 'combobox',
			fieldLabel : '送货时间段',//送货时间段
			name: 'deliveryTimeInterval',
			columnWidth: .5,
			displayField: 'name',
			valueField: 'name',
			value: '全天',
			triggerAction: 'all',
			store: Ext.create('Ext.data.Store', {
				fields : ['name'],
				data : [ {
					name : '全天'
				}, {
					name : '上午'
				}, {
					name : '下午'
				} ]
			}),
			editable: false,
			mode: 'local'
		}, {
			xtype : 'container',
			columnWidth : .5,
			layout : 'hbox',
			items : [ {
				xtype: 'timefield',
				name: 'deliveryTimeStart',
				labelWidth : 85,
				width : 152,
				editable:true,
				format : 'H:i',
				fieldLabel: '送货时间点',
				increment: 30,
				submitFormat: 'H:i',
				listeners : {
					blur : function (timefield) {
						var val = timefield.getValue();
						if (val) {
							timefield.nextSibling().allowBlank = false;
						} else if (!val && !timefield.nextSibling().getValue()) {
							timefield.reset();
							timefield.nextSibling().reset();
							timefield.allowBlank = true;
							timefield.nextSibling().allowBlank = true;
						} else {
							timefield.nextSibling().allowBlank = true;
						}
					},
			        select : function(combo, records, eOpts) {
			        	var val = combo.getValue() ;
			        	if (val) {
			        		combo.nextSibling().setMinValue(val);
			        	}
			        }
				}
			}, {
				xtype: 'timefield',
				name: 'deliveryTimeOver',
				format : 'H:i',
				fieldLabel: '至',
				labelWidth : 20,
				width : 87,
				editable:true,
				labelSeparator: '',
				increment: 30,
				submitFormat: 'H:i',
				listeners : {
					blur : function (timefield) {
						var val = timefield.getValue();
						if (val) {
							timefield.previousSibling().allowBlank = false;
						} else if (!val && !timefield.previousSibling().getValue()) {
							timefield.reset();
							timefield.previousSibling().reset();
							timefield.allowBlank = true;
							timefield.previousSibling().allowBlank = true;
						} else {
							timefield.previousSibling().allowBlank = true;
						}
					},
			       	select : function(combo, records, eOpts) {
			       		var val = combo.getValue() ;
			        	if (val) {
			        		combo.previousSibling().setMaxValue(val);
			        	}
			        }
				}
			} ]
		}, {
			xtype : 'checkbox',
			name: 'isExhibition',
			boxLabel : '会展货', //会展货
			inputValue : 'Y',
			margin: '5px 0 0 20px',
			columnWidth : 0.15,
			listeners : {
				change : function(checkbox, newValue, oldValue, eOpts) {
					var form = checkbox.up('form').getForm();
					if (newValue) {
						form.findField('isEmptyCar').setDisabled(false);
					} else {
						form.findField('isEmptyCar').reset();
						form.findField('isEmptyCar').setDisabled(true);
					}
				}
			}
		}, {
			xtype : 'checkbox',
			name: 'isEmptyCar',
			boxLabel : '空车出',
			disabled: true,
			inputValue : 'Y',
			columnWidth : 0.2,
			margin: '5px 0 0 30px'
		}, {
			xtype : 'container',
			columnWidth: 1,
			layout : 'column',
			items : [ {
				xtype : 'combobox',
				fieldLabel : '发票类型',//发票类型
				name: 'invoiceType',
				labelWidth : 85,
				columnWidth: 0.48,
				displayField: 'valueName',
				valueField: 'valueCode',
				store: FossDataDictionary.getDataDictionaryStore('PKP_RECEIPT_INVOICE_TYPE', null, {
						'valueCode': '', 'valueName':'无'
				}),
				editable: false,
				listeners : {
					blur : function(combobox) {
						var formPanel = combobox.up('form');
						var noticeContentField = formPanel.down('textarea[name=notes]');
						var noticeContentValue = noticeContentField.getValue();
						if (combobox.getRawValue()) {
							noticeContentField.setValue(noticeContentValue + '【' + combobox.getRawValue() + '】');
						} else {
							noticeContentField.setValue(noticeContentValue);
						}
					}
				}
			}, {
				fieldLabel : '发票备注',
				labelWidth : 60,
				xtype : 'textfield',
				margin: '0 0 0 20px',
				name: 'invoiceDetail',
				columnWidth: 0.52,
				maxLength: 30,
				maxLengthText: '已超出字数最大限制!',
				listeners : {
					blur : function(text, newValue, oldValue, eOpts ) {
						if (!text.isValid()) {
							return ;
						}
						var formPanel = text.up('form');
						var noticeContentField = formPanel.down('textarea[name=notes]');
						var noticeContentValue = noticeContentField.getValue();
						if (text.getValue()) {
							noticeContentField.setValue(noticeContentValue + '【' + text.getValue() + '】');
						}
					}
				}
			} ]
		}, {
			xtype: 'hidden',
			columnWidth: .5,
			name: 'receiveCustomerCode'
		}, {
			xtype: 'hidden',
			columnWidth: .5,
			name: 'receiveCustomerContact'
		} ]
	}, {
		xtype: 'tabpanel',
		minTabWidth: 100,
		items:[ {
			xtype: 'grid',
			//表格对象增加一个边框
			frame: true,
			itemId: 'addExceptionProcessGridItemId',
			//增加表格列的分割线
			columnLines: true,
			emptyText:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.emptyText'),//查询结果为空！,
			//定义表格的标题
			title:predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.processingRecords'),//处理历史,
			store: Ext.create('Foss.predeliver.exceptionProcess.ExceptionProcessStore'),	 
			viewConfig: {
				enableTextSelection: true
			},
			//定义表格列信息
			columns: [ {
				header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateTime'),//登记时间, 
				dataIndex: 'operateTime',
				align: 'center',
				width:130,
				renderer:function(value){
					if (value!=null) {
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					} else {
						return null;
					}
				} 
			}, {
				header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operaterName'),//处理人, 
				dataIndex: 'operator', 
				align: 'center',
				width:100
			}, {
				header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.notes'),//处理结果, 
				dataIndex: 'notes', 
				align: 'center',
				flex:1
			}, {
				header: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateOrgName'),//处理部门, 
				dataIndex: 'operateOrgName', 
				align: 'center',
				flex:1
			} ]
		}, {
			itemId: 'noticeCustomerWaybillInfoFormItemId',
			title: '运单信息',
			xtype: 'form',
			frame: true,
			layout: {
				type: 'table',
				columns: 3
			},
			defaults: {
				labelWidth: 60,
				readOnly: true
			},
			defaultType: 'textfield',
			items: [ {
				xtype: 'textarea',
				fieldLabel: '送货地址',
				name: 'receiveCustomerAddress',
				height: 105,
				rowspan: 4,
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '始发部门',
				name: 'receiveOrgName',
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '运输性质',
				name: 'productCode',
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '货物名称',
				name: 'goodsName',
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '派送方式',
				name: 'receiveMethod'
			}, {
				fieldLabel: '库存状态',
				name: 'stockStatus'
			}, {
				fieldLabel: '运费',
				name: 'transportFee'
			}, {
				fieldLabel: '开单件数',
				fieldStyle: 'color:red;',
				name: 'goodsQtyTotal'
			}, {
				fieldLabel: '运货费',
				name: 'deliveryGoodsFee'
			}, {
				fieldLabel: '发货人',
				name: 'deliveryCustomerContact',
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '到达件数',
				name: 'arriveGoodsQty'
			}, {
				fieldLabel: '其他费用',
				name: 'otherFee'
			}, {
				fieldLabel: '发货客户电话',
				name: 'deliveryCustomerPhone',
				labelWidth: 84,
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '库存件数',
				fieldStyle: 'color:red;',
				name: 'stockQty'
			}, {
				fieldLabel: '货物价值',
				name: 'insuranceAmount'
			}, {
				fieldLabel: '发货客户手机',
				name: 'deliveryCustomerMobilephone',
				labelWidth: 84,
				tipConfig : {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.exceptionProcess.tipPanel'
				}
			}, {
				fieldLabel: '库存天数',
				name: 'storageDay'
			}, {
				fieldLabel: '保价费',
				name: 'insuranceFee'
			}, {
				fieldLabel: '储运事项',
				xtype: 'textarea',
				name: 'transportationRemark',
				height: 105,
				rowspan: 4
			}, {
				fieldLabel: '重量(千克)',
				fieldStyle: 'color:red;',
				name: 'goodsWeightTotal',
				labelWidth: 85
			}, {
				fieldLabel: '代收货款',
				name: 'codAmount'
			}, {
				fieldLabel: '体积(立方米)',
				fieldStyle: 'color:red;',
				name: 'goodsVolumeTotal',
				labelWidth: 85
			}, {
				fieldLabel: '包装费',
				name: 'packageFee'
			}, {
				fieldLabel: '包装',
				name: 'goodsPackage'
			}, {
				fieldLabel: '保管费',
				name: 'storageCharge'
			}, {
				fieldLabel: '尺寸(厘米)',
				name: 'goodsSize',
				labelWidth: 85
			}, {
				fieldLabel: '上次通知日期',
				xtype: 'datefield',
				name: 'notificationTime',
				hideTrigger: true,
				format: 'Y-m-d',
				width: 170,
				labelWidth: 85
			} ]
		}]
	} ],
	buttons : [{
		cls : 'yellow_button',
		text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.save'),//保存,
		handler : function(btn) {
			var win = btn.up('window');
			var form = win.down('#addExceptionProcessFormItemId').getForm();
			var exceptionProcessId = form.findField('exceptionProcessId').getValue();
			var notes = form.findField('notes').getValue();
			var waybillNo = form.findField('waybillNo').getValue();
			var deliverDate = form.findField('deliverDate').getValue();
			var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
			if (!form.findField('notes').isValid()) {
				Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateResultIsError'), 'error', 3000);
				return;
			}
			Ext.Ajax.request({
				url:predeliver.realPath('addExceptionProcessDetailInfo.action'),
				params:{
					'vo.exceptionProcessConditionDto.tSrvExceptionId':exceptionProcessId,	//异常id
					'vo.exceptionProcessConditionDto.notes':notes,	//处理结果
					'vo.exceptionProcessConditionDto.waybillNo':waybillNo,
					'vo.exceptionProcessConditionDto.deliverDate':deliverDate	//预计送货日期
				},
				success:function(response){
					Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitSuccess'), 'ok', 1000);
					exceptionGrid.store.load();
					win.close();
				},
				exception: function(response){
					var json = Ext.decode(response.responseText);
			        Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
			        exceptionGrid.store.load();
			        win.close();
				}
			});
						
		}
	}, {
		cls : 'yellow_button',
		text : predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.disposed'),//处理完毕,
		disabled:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexcompletebutton'),
		hidden:!predeliver.exceptionProcess.isPermission('exceptionprocessindex/exceptionprocessindexcompletebutton'),
		handler : function(btn) {
			var win = btn.up('window');
			var form = win.down('#addExceptionProcessFormItemId').getForm();
			var exceptionReason = form.findField('exceptionReason').getValue();
			var exceptionProcessId = form.findField('exceptionProcessId').getValue();
			var notes = form.findField('notes').getValue();
			var	exceptionType = form.findField('exceptionType').getValue();
			var exceptionLink = form.findField('exceptionLink').getValue();
			var waybillNo = form.findField('waybillNo').getValue();
			var arrivesheetId = form.findField('arrivesheetId').getValue();
			var deliverDate = form.findField('deliverDate').getValue();
			var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
			if(!Ext.isEmpty(exceptionReason)&&exceptionReason.indexOf('拒收')!=-1){
				Ext.Msg.confirm(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'),predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.rejectRecord'),function(o){
					if(o=='yes'){
						if (!form.findField('notes').isValid()) {
							Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateResultIsError'), 'error', 3000);
							return;
						}
						Ext.Ajax.request({
							url:predeliver.realPath('operateExceptionProcessDetailInfo.action'),
							params:{
								'vo.exceptionProcessConditionDto.tSrvExceptionId':exceptionProcessId,
								'vo.exceptionProcessConditionDto.notes':notes,
								'vo.exceptionProcessConditionDto.exceptionLink':exceptionLink,
								'vo.exceptionProcessConditionDto.exceptionType':exceptionType,
								'vo.exceptionProcessConditionDto.waybillNo':waybillNo,
								'vo.exceptionProcessConditionDto.arrivesheetId':arrivesheetId,
								'vo.exceptionProcessConditionDto.deliverDate':deliverDate
							},
							success: function(response) {
								Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.disposed'), 'ok', 3000);
								exceptionGrid.store.load();
								win.close();
							},
							exception: function(response){
								var json = Ext.decode(response.responseText);
						        Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
						        exceptionGrid.store.load();
						        win.close();
							}
						});
					}else{
						return false;
					}
				});
			}else{
				if (!form.findField('notes').isValid()) {
					Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.operateResultIsError'), 'error', 3000);
					return;
				}
				Ext.Ajax.request({
					url:predeliver.realPath('operateExceptionProcessDetailInfo.action'),
					params:{
						'vo.exceptionProcessConditionDto.tSrvExceptionId':exceptionProcessId,
						'vo.exceptionProcessConditionDto.notes':notes,
						'vo.exceptionProcessConditionDto.exceptionLink':exceptionLink,
						'vo.exceptionProcessConditionDto.exceptionType':exceptionType,
						'vo.exceptionProcessConditionDto.waybillNo':waybillNo,
						'vo.exceptionProcessConditionDto.arrivesheetId':arrivesheetId,
						'vo.exceptionProcessConditionDto.deliverDate':deliverDate
					},
					success: function(response) {
						Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'), predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.disposed'), 'ok', 3000);
						exceptionGrid.store.load();
						win.close();
					},
					exception: function(response){
						var json = Ext.decode(response.responseText);
				        Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
				        exceptionGrid.store.load();
				        win.close();
					}
				});
			}
		}
	}, {
		cls : 'yellow_button',
		xtype : 'button',
		itemId: 'notifyProcessedBtnItemId',
		text : '通知并处理完毕',
		handler : function(btn) {
			var win = btn.up('window');
			var form = win.down('#addExceptionProcessFormItemId').getForm();
			var exceptionReason = form.findField('exceptionReason').getValue();
			if(!Ext.isEmpty(exceptionReason)&&exceptionReason.indexOf('拒收')!=-1){
				Ext.Msg.confirm(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.tip'),predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.waybillNo.rejectRecord'),function(o){
					if(o=='yes'){
						var form = win.down('#addExceptionProcessFormItemId');
						if (!form.getForm().isValid()) {
							return ;
						}
						
						var record = form.getRecord();
						var values = form.getValues();
						for (var key in values) {
							if (key == 'toPayAmount') {
								values[key] = parseFloat(values[key]);
							}
							record.set(key, values[key]);
						}
						
						// 会展货
						var isExhibition = form.down('checkbox[name=isExhibition]').getValue();
						// 空车出
						var isEmptyCar = form.down('checkbox[name=isEmptyCar]').getValue();
						if (!isExhibition) {
							isExhibition = '';
						} else {
							isExhibition = 'Y';
						}
						if (!isEmptyCar) {
							isEmptyCar = '';
						} else {
							isEmptyCar = 'Y';
						}
						record.set('isExhibition', isExhibition);
						record.set('isEmptyCar', isEmptyCar);
						var paramData = record.getData(true);
						
						addPrev(paramData, 'vo.exceptionProcessDetailDto')
						
						Ext.Ajax.request({
						    url: predeliver.realPath('operateExceptionAndNotifyCustomer.action'),
						    params: paramData,
						    success: function(response){
						        var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
						        if (exceptionGrid) {
						        	exceptionGrid.getStore().load();
						        }
						        win.close();
						    },
						    exception: function(response){
								var json = Ext.decode(response.responseText);
						        Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
						        var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
						        if (exceptionGrid) {
						        	exceptionGrid.getStore().load();
						        }
						        win.close();
							}
						});
					}else{
						return false;
					}
				});
			}else{
				var form = win.down('#addExceptionProcessFormItemId');
				if (!form.getForm().isValid()) {
					return ;
				}
				
				var record = form.getRecord();
				var values = form.getValues();
				for (var key in values) {
					if (key == 'toPayAmount') {
						values[key] = parseFloat(values[key]);
					}
					record.set(key, values[key]);
				}
				
				// 会展货
				var isExhibition = form.down('checkbox[name=isExhibition]').getValue();
				// 空车出
				var isEmptyCar = form.down('checkbox[name=isEmptyCar]').getValue();
				if (!isExhibition) {
					isExhibition = '';
				} else {
					isExhibition = 'Y';
				}
				if (!isEmptyCar) {
					isEmptyCar = '';
				} else {
					isEmptyCar = 'Y';
				}
				record.set('isExhibition', isExhibition);
				record.set('isEmptyCar', isEmptyCar);
				var paramData = record.getData(true);
				
				addPrev(paramData, 'vo.exceptionProcessDetailDto')
				
				Ext.Ajax.request({
				    url: predeliver.realPath('operateExceptionAndNotifyCustomer.action'),
				    params: paramData,
				    success: function(response){
				        var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
				        if (exceptionGrid) {
				        	exceptionGrid.getStore().load();
				        }
				        win.close();
				    },
				    exception: function(response){
						var json = Ext.decode(response.responseText);
				        Ext.ux.Toast.msg(predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.submitError'), json.message, 'error', 3000);
				        var exceptionGrid = Ext.getCmp('Foss_predeliver_exceptionProcess_ExceptionProcessGrid_Id');
				        if (exceptionGrid) {
				        	exceptionGrid.getStore().load();
				        }
				        win.close();
					}
				});
			}					
		}
	}]
});	

//新增异常
Ext.define('Foss.predeliver.exceptionProcess.AddExceptionWindow', {
	extend : 'Ext.window.Window',
	resizable : false,
	closeAction : 'hide',
	//modal : 'true',
	width : 600,
	height : 470,
	items : [predeliver.exceptionProcess.addExceptionForm]
});	

//在线通知
Ext.define('Foss.predeliver.exceptionProcess.AddNoticeWindow', {
	extend : 'Ext.window.Window',
	resizable : false,
	title: predeliver.exceptionProcess.i18n('pkp.predeliver.exceptionProcess.noticeOnline'),//在线通知,
	closeAction : 'hide',
	modal : 'true',
	width : 400,
	height : 220,
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
