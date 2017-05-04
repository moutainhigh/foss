/**
 *author : 043258-foss-zhaobin
 *page : 查询货量
*/

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.queryGoods.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
/*	t2.setHours(6);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);*/	
	return t2;
};

//提货方式集合
Ext.define('Foss.predeliver.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.queryGoods.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
			{'valueCode':'DELIVER','valueName':predeliver.queryGoods.i18n('pkp.predeliver.notifyCustomer.deliver')},// '送货'
			{'valueCode':'PICKUP','valueName':predeliver.queryGoods.i18n('pkp.predeliver.notifyCustomer.pickup')}//'自提'
		]
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
	}
});

//定义查询货量模型
Ext.define('Foss.QueryGoods.Model.GoodsInfoModel', {
	extend: 'Ext.data.Model',
	fields: [
	    { name: 'waybillNo',type:'string' }, //运单号
		{ name: 'goodsName',type:'string' }, //货物名称
		{ name: 'goodsWeight',type:'string' }, //重量
		{ name: 'goodsVolume',type:'string' },  //体积
		{ name: 'goodsQtyTotal',type:'string' },  //件数
		{ name: 'goodsHandoverTotal',type:'string' },  //在途件数
		{ name: 'goodsStoreTotal',type:'string' },  //库存件数
		{ name: 'goodsStoringTotal',type:'string' },  //库存件数
		{ name: 'exceptionTotal',type:'string' },  //异常件数
		{ name: 'toPayAmount',type:'string' },  //到付金额
		{ name: 'receiveCustomerName',type:'string' },  //客户名称
		{ name: 'receiveCustomerMobilephone',type:'string' },    //客户手机
		{ name: 'receiveCustomerPhone' ,type:'string'},  //客户电话
		{ name: 'receiveCustomerAddress',type:'string' }, //客户地址
		{ name: 'transportType',type:'string' }, //运输方式
		{ name: 'preArriveTime',type:'date',
			 convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			  }
		}, //预计到达时间
		{ name: 'notificationResult',type:'string'}, //是否联系客户
		{ name: 'preCustomerPickupTime',type:'date',
		 	convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
		 }}, //预计派送/提货时间
		{ name: 'receiveMethod',
		  convert:function(value,record){
			if(record.get('transportType')=='汽运'){//汽运
				var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;			
			}else{//空运
				return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSAIR');
			}
		}}, //提货方式
		{ name: 'transportType',
			  convert:function(value,record){
				if(value=='TRANS_VEHICLE'){//汽运
					return '汽运';			
				}else if(value=='TRANS_AIRCRAFT'){//空运
					return '空运';
				}else{
					return null;
				}
			}}, //运输方式
		{ name: 'arrangementTotal',type:'string'} //排单状态
		]
});

//创建一个查询货量枚举store
Ext.define('Foss.QueryGoodsInfo.Store.QueryGoodsTypeStore',{
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

		
//创建一个查询货量store
Ext.define('Foss.QueryGoodsInfo.Store.QueryGoodsInfoStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.QueryGoods.Model.GoodsInfoModel',
	//默认每页数据大小
	pageSize:10,
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		url:predeliver.realPath('querygoods.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.goodsInfoDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			
			
			

			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			if(predeliver.queryGoods.queryPanel.getActiveTab().title == predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.creatWaybillPredict'))
			{
				var queryParams = predeliver.queryGoods.queryInfoForPkpForm.getValues();

				var form = Ext.getCmp('Foss_QueryGoodsInfo_Form_QueryInfoForPkpForm_Id').getForm();
				var preArriveTimeBegin = form.getValues().preArriveTimeBegin;
				var preArriveTimeEnd = form.getValues().preArriveTimeEnd;
				var result = Ext.Date.parse(preArriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(preArriveTimeBegin,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >= 7){
					Ext.ux.Toast.msg(predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.tip'), predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.dateintervalError'), 'error', 3000);
					return false;
				}
				if (!form.isValid()){
					return false;
				}
			}
			else
			{
				var queryParams = predeliver.queryGoods.queryInfoForTfrForm.getValues();
				var form1 = Ext.getCmp('Foss_QueryGoodsInfo_Form_QueryInfoForTfrForm_Id').getForm();
				var planArriveTimeBegin = form1.getValues().planArriveTimeBegin;
				var planArriveTimeEnd = form1.getValues().planArriveTimeEnd;
				var result = Ext.Date.parse(planArriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(planArriveTimeBegin,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >= 7){
					Ext.ux.Toast.msg(predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.tip'), predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.dateintervalError'), 'error', 3000);
					return false;
				}
				
				if (!form1.isValid()){
					return false;
				}
			}
						
			Ext.apply(operation, {
				params : {					
						'vo.goodsInfoConditionDto.waybillNo': queryParams.waybillNo,
				    	'vo.goodsInfoConditionDto.receiveCustomerName': queryParams.receiveCustomerName,
				    	'vo.goodsInfoConditionDto.receiveCustomerMobilephone': queryParams.receiveCustomerMobilephone,
				    	'vo.goodsInfoConditionDto.receiveCustomerPhone': queryParams.receiveCustomerPhone,
				    	'vo.goodsInfoConditionDto.receiveMethod': queryParams.receiveMethod,
				    	'vo.goodsInfoConditionDto.productCode': queryParams.productCode,
				    	'vo.goodsInfoConditionDto.regionVehicleCode': queryParams.regionVehicleCode,
				    	'vo.goodsInfoConditionDto.receiveCustomerCountyCode': queryParams.receiveCustomerCountyCode,
				    	'vo.goodsInfoConditionDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
				    	'vo.goodsInfoConditionDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
				    	'vo.goodsInfoConditionDto.planArriveTimeBegin': queryParams.planArriveTimeBegin,
				    	'vo.goodsInfoConditionDto.planArriveTimeEnd': queryParams.planArriveTimeEnd,
				    	'vo.goodsInfoConditionDto.arrangementState': queryParams.arrangementState
					}
			});	
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if(store.currentPage==1)
			{
				Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalWeight_ID').setValue(data.vo.goodsInfoConditionDto.goodsWeightTotal);
				Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalVolume_ID').setValue(data.vo.goodsInfoConditionDto.goodsVolumeTotal);
				Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalPiece_ID').setValue(data.vo.goodsInfoConditionDto.goodsPieceTotal);
				Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalWaybill_ID').setValue(data.vo.goodsInfoConditionDto.goodsWaybillTotal);
				Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_toPayAmountTotal_ID').setValue(data.vo.goodsInfoConditionDto.toPayAmountTotal);
			}
		}
	}
});

//获得当前部门是否营业部
var dept = FossUserContext.getCurrentDept().salesDepartment;

//获得当前部门是否有派送功能
(function() {
		Ext.Ajax.request({
			url : predeliver.realPath('queryDepartment.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					isDeliverDepartment : json.isDeliverDepartment
				});
			}
		})
})();

//开单预计
Ext.define('Foss.QueryGoodsInfo.Form.QueryInfoForPkpForm',{
	extend:'Ext.form.Panel',
	id:'Foss_QueryGoodsInfo_Form_QueryInfoForPkpForm_Id',
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.waybillNo'),//运单号
		vtype: 'waybill',
		columnWidth: 0.25
	},{
		name:'receiveCustomerName', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneeName'),//收货人名称
		columnWidth: 0.25
	},{
		name:'receiveCustomerMobilephone', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneeMobilephone'),//收货人手机
		columnWidth: 0.25
	},{
		name:'receiveCustomerPhone', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneePhone'),//收货人电话
		columnWidth: 0.25
	},{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'receiveMethod',
		fieldLabel : predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveMethod'),//送货方式
		columnWidth : .25,
		store : Ext.create('Foss.predeliver.store.ReceiveMethodStore')
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'productCode',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.productCode'),//运输性质
		columnWidth: 0.25,
		value:'',
		store : Ext.create('Foss.pkp.ProductStore')
	},{		
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'arrangementState',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.arrangementTotal'),//排单状态
		columnWidth: 0.25,
		value: 'NO_DELIVERBILL',
		store:Ext.create('Foss.QueryGoodsInfo.Store.QueryGoodsTypeStore',
		{
		  data:{
		       'items':[
					{'code':'ALL','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.all')},
					{'code':'NO_DELIVERBILL','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.noDeliverbill')},
					{'code':'DELIVERBILLED','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.deliverBilled')}
			   ]
		  }
		})
	},{
		name: 'regionVehicleCode',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.regionVehicleCode'),//定人定区
		xtype : 'commonsmallzoneselector',
		columnWidth: 0.25
	},{
		name: 'receiveCustomerCountyCode',
		xtype : 'commonmotorcadedistrictselector',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerCountyCode'),//行政区域
		columnWidth: 0.25
	},{
		xtype: 'rangeDateField',
		fieldId: 'FOSS_PreArriveTime_Id',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.preArriveTime'),//预计到达时间
		dateType: 'datetimefield_date97',
		fromName: 'preArriveTimeBegin',
		toName: 'preArriveTimeEnd',
		disallowBlank: true,
		editable:false,
		fromValue: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),1),'Y-m-d H:i:s'),
		columnWidth: .49
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.reset'),//重置
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.findField('waybillNo').setValue('');
				form.findField('receiveCustomerName').setValue('');
				form.findField('receiveCustomerMobilephone').setValue('');
				form.findField('receiveCustomerPhone').setValue('');
				form.findField('receiveMethod').setValue('全部');
				form.findField('productCode').setValue('');
				form.findField('arrangementState').setValue('NO_DELIVERBILL');
				form.findField('regionVehicleCode').setValue('');
				form.findField('receiveCustomerCountyCode').setValue('');
				form.findField('preArriveTimeBegin').setValue(Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),0),'Y-m-d H:i:s'));
				form.findField('preArriveTimeEnd').setValue(Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),1),'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.query'),//查询
			disabled:!predeliver.queryGoods.isPermission('querygoodsindex/querygoodsindexquerybutton'),
			hidden:!predeliver.queryGoods.isPermission('querygoodsindex/querygoodsindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				predeliver.queryGoods.pagingBar.moveFirst();
			}
		}]
	}],
	listeners : {
		beforerender:function(){
			var form = Ext.getCmp('Foss_QueryGoodsInfo_Form_QueryInfoForPkpForm_Id').getForm();
			if(predeliver.isDeliverDepartment!='Y')
				{
					form.findField('regionVehicleCode').setDisabled(true);
					form.findField('receiveCustomerCountyCode').setDisabled(true);
				}
			}
		}
});

//车辆预计
Ext.define('Foss.QueryGoodsInfo.Form.QueryInfoForTfrForm',{
	extend:'Ext.form.Panel',
	id:'Foss_QueryGoodsInfo_Form_QueryInfoForTfrForm_Id',
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.waybillNo'),//运单号
		vtype: 'waybill',
		columnWidth: 0.25
	},{
		name:'receiveCustomerName', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneeName'),//收货人名称
		columnWidth: 0.25
	},{
		name:'receiveCustomerMobilephone', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneeMobilephone'),//收货人手机
		columnWidth: 0.25
	},{
		name:'receiveCustomerPhone', 
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.consigneePhone'),//收货人电话
		columnWidth: 0.25
	},{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'receiveMethod',
		fieldLabel : predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveMethod'),//送货方式
		columnWidth : .25,
		store : Ext.create('Foss.predeliver.store.ReceiveMethodStore')
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'productCode',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.productCode'),//运输性质
		columnWidth: 0.25,
		value:'',
		store : Ext.create('Foss.pkp.ProductStore')
	},{		
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'arrangementState',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.arrangementTotal'),//排单状态
		columnWidth: 0.25,
		value: 'NO_DELIVERBILL',
		store:Ext.create('Foss.QueryGoodsInfo.Store.QueryGoodsTypeStore',
		{
		  data:{
		       'items':[
						{'code':'ALL','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.all')},
						{'code':'NO_DELIVERBILL','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.noDeliverbill')},
						{'code':'DELIVERBILLED','name':predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.deliverBilled')}
				   ]
			  }
		})
	},{
		name: 'regionVehicleCode',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.regionVehicleCode'),//定人定区
		xtype : 'commonsmallzoneselector',
		columnWidth: 0.25
	},{
		name: 'receiveCustomerCountyCode',
		xtype : 'commonmotorcadedistrictselector',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerCountyCode'),//行政区域
		columnWidth: 0.25
	},{
		xtype: 'rangeDateField',
		fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.preArriveTime'),//预计到达时间
		fieldId: 'FOSS_TfrArriveTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'planArriveTimeBegin',
		toName: 'planArriveTimeEnd',
		disallowBlank: true,
		editable:false,
		fromValue: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),1),'Y-m-d H:i:s'),
		columnWidth: .49
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.reset'),//重置
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.findField('waybillNo').setValue('');
				form.findField('receiveCustomerName').setValue('');
				form.findField('receiveCustomerMobilephone').setValue('');
				form.findField('receiveCustomerPhone').setValue('');
				form.findField('receiveMethod').setValue('全部');
				form.findField('productCode').setValue('');
				form.findField('arrangementState').setValue('NO_DELIVERBILL');
				form.findField('regionVehicleCode').setValue('');
				form.findField('receiveCustomerCountyCode').setValue('');
				form.findField('planArriveTimeBegin').setValue(Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),0),'Y-m-d H:i:s'));
				form.findField('planArriveTimeEnd').setValue(Ext.Date.format(predeliver.queryGoods.getTargetDate(new Date(),1),'Y-m-d H:i:s'));
			
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.query'),//查询
			disabled:!predeliver.queryGoods.isPermission('querygoodsindex/querygoodsindexquerybutton'),
			hidden:!predeliver.queryGoods.isPermission('querygoodsindex/querygoodsindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){				
					predeliver.queryGoods.pagingBar.moveFirst();
			}
		}]
	}],
	listeners : {
		beforerender:function(){
			var form = Ext.getCmp('Foss_QueryGoodsInfo_Form_QueryInfoForTfrForm_Id').getForm();
			if(predeliver.isDeliverDepartment!='Y')
				{
					form.findField('regionVehicleCode').setDisabled(true);
					form.findField('receiveCustomerCountyCode').setDisabled(true);
				}
			}
		}
});


//货物信息
Ext.define('Foss.QueryGoodsInfo.QueryGoodsInfoGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	id:'Foss_QueryGoodsInfo_QueryGoodsInfoGrid_Id',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.emptyText'),//查询结果为空
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	//定义表格的标题
	title:predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsInfo'),//货物信息
	collapsible: true,
	animCollapse: true,
	store: null,
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
	     Ext.create('Ext.grid.RowNumberer'),
	    {
		xtype:'actioncolumn',
		header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsMark'),//货物标记
		width:100,
		align: 'center',
		items: [{
			iconCls: 'foss_icons_pkp_exceptionSec',
			getClass : function(value,metadata,record,rowIndex,colIndex,store)
			{
				if(record.get('goodsStoreTotal') != record.get('goodsQtyTotal')){
					return 'foss_icons_pkp_exceptionSec';
				}else {
					return 'foss_icons_pkp_exceptionSec_hide';
				}
			}
		},{
			iconCls: 'foss_icons_pkp_exception',
			getClass : function(value,metadata,record,rowIndex,colIndex,store)
			{
				if( record.get('exceptionTotal') > 0){
					return 'foss_icons_pkp_exception';
				}else {
					return 'foss_icons_pkp_exception_hide';
				}
			}
		}]
	},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.waybillNo'),//运单号
			//关联model中的字段名
			dataIndex: 'waybillNo',
			width:80				
		},{ 
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsName'),//货物名称 
			//关联model中的字段名
			dataIndex: 'goodsName',
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsWeight'),//重量 
			//关联model中的字段名
			dataIndex: 'goodsWeight', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsVolume'),//体积 
			//关联model中的字段名
			dataIndex: 'goodsVolume', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.toPayAmount'),//到付金额 
			//关联model中的字段名
			dataIndex: 'toPayAmount', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsQtyTotal'),//开单件数 
			//关联model中的字段名
			dataIndex: 'goodsQtyTotal', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsHandoverTotal'),//在途件数 
			//关联model中的字段名
			dataIndex: 'goodsHandoverTotal', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.goodsStoreTotal'),//库存件数 
			//关联model中的字段名
			dataIndex: 'goodsStoreTotal', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.positionQty'),//库位件数 
			//关联model中的字段名
			dataIndex: 'goodsStoringTotal', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.exceptionTotal'),//异常件数 
			//关联model中的字段名
			hidden: true,
			dataIndex: 'exceptionTotal', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerName'),//客户名称 
			//关联model中的字段名
			dataIndex: 'receiveCustomerName', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerMobilephone'),//客户手机 
			//关联model中的字段名
			dataIndex: 'receiveCustomerMobilephone', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerPhone'),//客户电话 
			//关联model中的字段名
			dataIndex: 'receiveCustomerPhone', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveCustomerAddress'),//客户地址 
			//关联model中的字段名
			dataIndex: 'receiveCustomerAddress', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.preArriveTime'),//预计到达时间 
			//关联model中的字段名
			dataIndex: 'preArriveTime', 
			width:120,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.notificationResult'),//是否联系客户 
			//关联model中的字段名
			dataIndex: 'notificationResult', 
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('notificationResult')=='SUCCESS'){
					return predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.yes');
				}else{
					return predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.no');
				}
			},
			width:80
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.preCustomerPickupTime'),//预计送货时间 
			//关联model中的字段名
			dataIndex: 'preCustomerPickupTime', 
			width:120,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.transportType'),//运输方式
			//关联model中的字段名
			dataIndex: 'transportType', 
			width:70
		},
		{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.receiveMethod'),//送货方式 
			//关联model中的字段名
			dataIndex: 'receiveMethod', 
			width:70
		},{
			//字段标题
			header: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.arrangementTotal'),//排单状态 
			//关联model中的字段名
			dataIndex: 'arrangementTotal', 
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('arrangementTotal')>'0'){
					return predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.deliverBilled');
				}
				else{
					return predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.noDeliverbill');
				}
			},
			width:70
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.QueryGoodsInfo.Store.QueryGoodsInfoStore');
			//添加列表按钮及分页工具条
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',		    	
				defaults:{
				margin:'0 0 5 3'
				},		
				items: [{
                	xtype: 'label',
                    margin:'0 0 0 10',
                    columnWidth : .2,
                    text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.inventoryNotCreateWaybill')//库存件数与开单件数不一致
                },{
	            	xtype: 'image',
	            	columnWidth : .05,
	            	imgCls: 'foss_icons_pkp_exceptionSec'
                },{
                	 xtype: 'label',
                	 columnWidth : .2,
                     margin:'0 0 0 10',
                     text: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.isException')//库存件数与非异常件数不一致
                },{
                	xtype: 'image',
                	columnWidth : .05,
                	imgCls: 'foss_icons_pkp_exception'
                },{
					xtype : 'container',
					border : false,
					columnWidth : .4,
					html : '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					name:'exportGoodsInfo',
					columnWidth:.1,
					text:predeliver.queryGoods.i18n('pkp.predeliver.exceptionProcess.export'),//导出,
					handler : function(){
		
						if(predeliver.queryGoods.queryPanel.getActiveTab().title == predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.creatWaybillPredict'))
						{
							var queryGoodsForm = predeliver.queryGoods.queryInfoForPkpForm;
						}
						else
						{
							var queryGoodsForm = predeliver.queryGoods.queryInfoForTfrForm;
						}
						
						if (queryGoodsForm != null) {
							var queryParams = queryGoodsForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}	
							//获取查询出来的异常信息
							var goodsGridStore = Ext.getCmp('T_predeliver-queryGoodsIndex_content').getQueryResultGrid().getStore();	
							//若异常信息不为空
							if(goodsGridStore.getCount()!=0){
								Ext.Ajax.request({
									url:predeliver.realPath('exportGoodsInfo.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : {
										'queryGoodVo.goodsInfoConditionDto.waybillNo': queryParams.waybillNo,
								    	'queryGoodVo.goodsInfoConditionDto.receiveCustomerName': queryParams.receiveCustomerName,
								    	'queryGoodVo.goodsInfoConditionDto.receiveCustomerMobilephone': queryParams.receiveCustomerMobilephone,
								    	'queryGoodVo.goodsInfoConditionDto.receiveCustomerPhone': queryParams.receiveCustomerPhone,
								    	'queryGoodVo.goodsInfoConditionDto.receiveMethod': queryParams.receiveMethod,
								    	'queryGoodVo.goodsInfoConditionDto.productCode': queryParams.productCode,
								    	'queryGoodVo.goodsInfoConditionDto.regionVehicleCode': queryParams.regionVehicleCode,
								    	'queryGoodVo.goodsInfoConditionDto.receiveCustomerCountyCode': queryParams.receiveCustomerCountyCode,
								    	'queryGoodVo.goodsInfoConditionDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
								    	'queryGoodVo.goodsInfoConditionDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
								    	'queryGoodVo.goodsInfoConditionDto.planArriveTimeBegin': queryParams.planArriveTimeBegin,
								    	'queryGoodVo.goodsInfoConditionDto.planArriveTimeEnd': queryParams.planArriveTimeEnd,
								    	'queryGoodVo.goodsInfoConditionDto.arrangementState': queryParams.arrangementState
									},
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg(predeliver.queryGoods.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),predeliver.queryGoods.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
							}
						}
					}
				}]
				},{
					xtype: 'toolbar',
					dock: 'bottom',
					layout:'column',
					defaults:{
						margin:'0 0 5 3',
						allowBlank:true
					},		
					items: [{
						xtype:'displayfield',
						allowBlank:true,
						name:'totalWeight',
						id:'Foss_QueryGoods_QueryGoodsGrid_totalWeight_ID',
						columnWidth:.1,
						fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.totalWeight')//总重量
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'totalVolume',
						id:'Foss_QueryGoods_QueryGoodsGrid_totalVolume_ID',
						columnWidth:.1,
						fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.totalVolume')//总体积
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'goodsPieceTotal',
						id:'Foss_QueryGoods_QueryGoodsGrid_totalPiece_ID',
						columnWidth:.1,
						fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.confirmDeliverbill.goodsQtyTotal')//总件数
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'goodsWaybillTotal',
						id:'Foss_QueryGoods_QueryGoodsGrid_totalWaybill_ID',
						columnWidth:.1,
						fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.confirmDeliverbill.waybillQtyTotal')//总票数
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'toPayAmountTotal',
						id:'Foss_QueryGoods_QueryGoodsGrid_toPayAmountTotal_ID',
						columnWidth:.1,
						fieldLabel: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.payAmountTotal')//总到付金额
					}]
			}],
			//自定义分页控件
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
			});
			predeliver.queryGoods.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
	});
//查询PKPform
predeliver.queryGoods.queryInfoForPkpForm = Ext.create('Foss.QueryGoodsInfo.Form.QueryInfoForPkpForm');
//查询TFRform
predeliver.queryGoods.queryInfoForTfrForm = Ext.create('Foss.QueryGoodsInfo.Form.QueryInfoForTfrForm');


//查询panel
predeliver.queryGoods.queryPanel = Ext.create('Ext.tab.Panel',{
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	//height:200,
	items: [{
		title: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.creatWaybillPredict'),//开单预计
		tabConfig: {
			width: 120
		},
		layout: 'fit',
		items:[predeliver.queryGoods.queryInfoForPkpForm]
	},{
		title: predeliver.queryGoods.i18n('pkp.predeliver.queryGoods.vehiclePredict'),//车辆预计
		tabConfig: {
			width: 120
		},
		layout: 'fit',
		items:[predeliver.queryGoods.queryInfoForTfrForm]
	}],
	listeners : {
		'tabchange' : function(tabPanel,newCard,oldCard,eOpts ){
			Ext.getCmp('T_predeliver-queryGoodsIndex_content').getQueryResultGrid().store.removeAll();
			predeliver.queryGoods.pagingBar.onLoad();
			Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalWeight_ID').setValue('');
			Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalVolume_ID').setValue('');
			Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalPiece_ID').setValue('');
			Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_totalWaybill_ID').setValue('');
			Ext.getCmp('Foss_QueryGoods_QueryGoodsGrid_toPayAmountTotal_ID').setValue('');
		}
	}
});



Ext.onReady(function() {
	Ext.QuickTips.init();
	
	 var queryResultGrid = Ext.create('Foss.QueryGoodsInfo.QueryGoodsInfoGrid');
	Ext.create('Ext.panel.Panel',{
		id: 'T_predeliver-queryGoodsIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryResultGrid: function(){
			return queryResultGrid;
		},
		items: [predeliver.queryGoods.queryPanel,queryResultGrid],
		renderTo: 'T_predeliver-queryGoodsIndex-body'
	});
});

