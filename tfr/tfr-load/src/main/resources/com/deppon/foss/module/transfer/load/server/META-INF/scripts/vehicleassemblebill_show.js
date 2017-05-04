//奖罚协议 奖励model
Ext.define('Foss.load.vehicleassemblebillshow.RewardOrPunishModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id',			type:'string'},
	        {	name:'rewardOrPunishType',  type:'String',
	        	convert : function(value){
	        		if(value == 'REWARD'){
	        			return '奖励'
	        		}else if(value=='FINE'){
	        			return '惩罚'
	        		}else{
	        			return '';
	        		}
	        	}
	        },
	        {	name:'timeLimit',	type:'string',
	        	convert : function(value){
	    			if(value == "0"){
	    			 	return "0小时以上";
	    			}else if (value == "0-1"){
	    				return "0-1小时";
	    			}else if(value == "1"){
	    				return "1小时以上";			
	    			}else if(value =="1-2" ){
	    				return "1-2小时";
	    			}else if(value == "2"){
	    				return "2小时以上";
	    			}else{
	    				return '';
	    			}
	    		}
	        },//时间段
	        {name:'agreementMoney',type:'string'},//奖励金额
	        {name:'rewardOrPunishMaxMoney' , type:'string'}]//最高上限金额
});
Ext.define('Foss.load.vehicleassemblebillshow.RewardOrPunishStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.vehicleassemblebillshow.RewardOrPunishModel',
	proxy: {
        type : 'ajax',
        reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vehicleAssembleBillVo.rewardOrPunishDto.rewardOrPunishAgreementEntity'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {}
	},
    exception : function(response) {}
});
//奖罚协议录入 奖励grid
Ext.define('Foss.load.vehicleassemblebillshow.RewardOrPunishGrid',{
	extend: 'Ext.grid.Panel',
	id:'Foss_load_vehicleassemblebillshow_RewardOrPunish_ID',
	store : Ext.create('Foss.load.vehicleassemblebillshow.RewardOrPunishStore'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//width:600,
	//增加表格列的分割线
	columnLines: true,
	stripeRows: false,
	collapsible : true,
	hidden : true,
	closeAction: 'destroy',
    frame: true,
    title: load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.gridTitle'),//时效条款录入
    columns:[{
    	header: "id",//id
		dataIndex:'id',
		hidden:true	
    },{
    	header:load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardType'),//条款类型
    	dataIndex:'rewardOrPunishType',
		width:350
    },{
    	header:load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.timelimit'),//时间段
    	dataIndex:'timeLimit',   	
    	width:300

    } ,{
    	header: load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.agreementMoney'),//奖励金额
		dataIndex:'agreementMoney',
		width:300
    }],
 dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
	    	margin:'0 0 0 0',
			xtype: 'textfield',
			readOnly:true,
			anchor: '100%',
			labelWidth:80
		},
		items: [{
			id : 'Foss_load_vehicleassemblebillshow_FineMaxMoney',
			fieldLabel : load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.finMaxMoney'),//本次惩罚封顶费用
			columnWidth : 1/2,
			labelWidth : 120,
			xtype : 'numberfield',
			value: 0
		} , {
			id : 'Foss_load_vehicleassemblebillshow_RewardMaxMoney',
			fieldLabel : load.vehicleassemblebillshow.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardMaxMoney'),//本次奖励封顶费用
			columnWidth : 1/2,
			labelWidth : 120,
			xtype : 'numberfield',
			value: 0
		}]
	}]
   
});
// 配载单基本信息Model
Ext.define('Foss.load.vehicleassemblebillshow.BillBasicInfoModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'assembleType',
		type : 'string',
		convert : function(value){
			if(value == 'CAR_LOAD'){
				return '整车';
			}else if(value == 'OWNER_LINE'){
				return '专线';
			}else{
				return '';
			}
		}
	},{
		type : 'string',
		name : 'destOrgName'
	}, {
		type : 'date',
		name : 'leaveTime',
		convert : function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		type : 'string',
		name : 'vehicleAssembleNo'
	}, {
		type : 'string',
		name : 'vehicleNo'
	},  {
		type : 'string',
		name : 'driverName'
	}, {
		type : 'string',
		name : 'vehicleOwnerShip'
	}, {
		type : 'string',
		name : 'frequencyNo'
	}, {
		type : 'string',
		name : 'transProperty',
		convert : function(value){
			if(value == 'TRUCK_LIKE_PLAN'){
				return '精准卡航';
			}else if(value == 'LONG_DISTANCE'){
				return '精准汽运(长)';
			}else if (value== 'TRUCK_LIKE_PCP'){
			    return '精准包裹';
			}else{
				return '';
			}
		}
	}, {
		type : 'string',
		name : 'driverMobilePhone'
	}, {
		type : 'string',
		name : 'runHours'
	}, {
		type : 'string',
		name : 'ratedLoad'
	}, {
		type : 'string',
		name : 'vehicleModel'
	}, {
		type : 'string',
		name : 'containerNo'
	},{
		type : 'string',
		name : 'examineVolume'
	}, {
		type : 'string',
		name : 'ratedClearance'
	}, {
		type : 'string',
		name : 'loadingRate'
	}, {
		type : 'string',
		name : 'createUser'
	}, {
		type : 'string',
		name : 'modifyUser'
	}, {
		type : 'string',
		name : 'goodsType',
		convert : function(value){
			if(value == 'A_TYPE'){
				return 'A类';
			}else if(value == 'B_TYPE'){
				return 'B类';
			}else{
				return '全部';
			}
		}
	}, {
		type : 'boolean',
		name : 'beMidWayLoad'
	}, {
		type : 'boolean',
		name : 'beFinallyArrive'
	},{
		type : 'boolean',
		name :　'beInLTLCar'
	},{
		tyep : 'boolean',
		name : 'beMidWayOnlyLoad'
	},{
		type : 'string',
		name : 'onTheWayDestOrgCode'
	},{
		type : 'string',
		name : 'midWayLoadType',
		convert : function(value){
			if(value == 'DISTANCE_UNLOAD'){
				return '在途装载类型：两地卸';
			}else if(value == 'DISTANCE_LOAD'){
				return '在途装载类型：两地装';
			}else if(value == 'MIDWAY_PICKUP'){
				return '在途装载类型：中途带货';
			}else{
				return '';
			}
		}
	},{
		type : 'string',
		name : 'vehicleMessage'
	},{
		type : 'string',
		name : 'note'
	},{
		type : 'string',
		name : 'paymentType',
		convert : function(value){
			if(value == 'CH'){
				return '现金';
			}else if(value == 'CT'){
				return '月结';
			}else{
				return value;
			}
		}
	},{
		type : 'string',
		name : 'feeTotal'
	},{
		type : 'number',
		name : 'prePaidFeeTotal',
		convert : function(value){
			if(value == null){
				return null;
			}else{
				return value;
			}
		}
	},{
		type : 'number',
		name : 'arriveFeeTotal',
		convert : function(value){
			if(value == null){
				return null;
			}else{
				return value;
			}
		}
	},{
		type : 'number',
		name : 'loadFeeTotal'
	},{
		type : 'number',
		name : 'collectionFeeTotal'
	}, {
		type : 'boolean',
		name : 'beReturnReceipt'
	}, {
		type : 'boolean',
		name : 'beTimeLiness'
	},{
		type : 'string',
		name : 'feeTotalChangeNotes'
	},{
		type : 'number',
		name : 'driverOfWeight'
	},{
		type : 'number',
		name : 'driverOfVolumn'
	},{
		type : 'number',
		name : 'applicationRatedLoad'
	},{
		type : 'number',
		name : 'applicationRatedClearance'
	},{
		type : 'boolean',
		name : 'beCarSmallUsed'
	}]
});

// 配载单基本信息form
Ext.define('Foss.load.vehicleassemblebillshow.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.formTitle')/*'配载单基本信息'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100,
		columnWidth : 1 / 4,
		xtype : 'textfield',
		readOnly : true
	},
	layout : 'column',
	items : [{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.assembleType')/*'配载类型'*/,
		name : 'assembleType'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/,
		name : 'destOrgName'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.leaveTime')/*'出发日期'*/,
		name : 'leaveTime'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleAssembleNo')/*'配载车次号'*/,
		name : 'vehicleAssembleNo'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/,
		name : 'vehicleNo'
	},  {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverName')/*'主驾驶姓名'*/,
		name : 'driverName'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverMobilePhone')/*'司机电话'*/,
		name : 'driverMobilePhone'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleModel')/*'车型'*/,
		name : 'vehicleModel'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.containerNo')/*'货柜编号'*/,
		name : 'containerNo'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.frequencyNo')/*'卡车班次'*/,
		name : 'frequencyNo'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.transProperty')/*'运输性质'*/,
		name : 'transProperty'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedLoad')/*'额定载重'*/,
		name : 'ratedLoad'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedClearance')/*'额定净空'*/,
		name : 'ratedClearance'
	},{
		fieldLabel :load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverOfGoods')/*'司机自带货量'*/,
		xtype : 'numberfield',
		value:0,
		margin:'5 0 5 10',
		columnWidth:.20,
		name : 'driverOfWeight'
	},{
		fieldLabel :'kg',
		xtype : 'numberfield',
		labelSeparator : '',
		value:0,
		margin:'5 0 5 0',
		columnWidth:.13,
		labelWidth : 20,
		name : 'driverOfVolumn'
	},{
		xtype: 'container',
		columnWidth:.05,
		id:'Foss_load_vehicleassemblebillshow_m³',
		margin:'10 0 5 0',
		html: 'm³'
	},{
		fieldLabel :load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedLoad')/*适用载重*/,
		xtype : 'numberfield',
		labelWidth : 60,
		readOnly : true,
		margin:'5 10 5 130',
		columnWidth :.30,
		name : 'applicationRatedLoad'
		
	},{
		fieldLabel :load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedClearance')/*'适用净空'*/,
		xtype : 'numberfield',
		readOnly : true,
		margin:'5 10 5 75',
		columnWidth : .30,
		name : 'applicationRatedClearance'
		
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.examineVolume')/*'考核体积'*/,
		name : 'examineVolume'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadingRate')/*'装载率'*/,
		name : 'loadingRate'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleOwnerShip')/*'车辆所有权类别'*/,
		name : 'vehicleOwnerShip',
		readOnly : true,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"Company", "value":"公司车"},
	            {"key":"Leased", "value":"外请车"}
	        ]
	    })
	}, {
		fieldLabel : '运行时数(小时)',
		name : 'runHours'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.createUser')/*'制单人'*/,
		name : 'createUser'
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.modifyUser')/*'修改人'*/,
		name : 'modifyUser'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleMessage')/*'车辆信息'*/,
		name : 'vehicleMessage',
		columnWidth : .5
	}, {
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/,
		name : 'note',
		columnWidth : .5,
		margin : '5 10 0 10'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.goodsType')/*'货物类型'*/,
		name : 'goodsType',
		margin : '5 10 0 10'
	}, {
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayLoad')/*'是否在途装卸'*/,
		name : 'beMidWayLoad',
		xtype : 'checkbox',
		columnWidth : .12,
		margin : '5 10 0 10'
	}, {
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beFinallyArrive')/*'是否最终到达'*/,
		name : 'beFinallyArrive',
		xtype : 'checkbox',
		columnWidth : .13,
		margin : '5 10 0 10'
	},{
		xtype : 'container',
		columnWidth : 1
	},{
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beInLTLCar')/*'是否与零担合车'*/,
		name : 'beInLTLCar',
		xtype : 'checkbox',
		margin : '5 10 0 10'
	},{
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayOnlyLoad')/*'是否在途只装不卸'*/,
		name : 'beMidWayOnlyLoad',
		xtype : 'checkbox',
		margin : '5 10 0 10'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.onTheWayDestOrgCode')/*'中途到达部门'*/,
		name : 'onTheWayDestOrgCode',
		margin : '5 10 0 10'
	},{
		boxLabel : '在途装载类型'/*'load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beFinallyArrive')'*/,
		name : 'midWayLoadType',
		xtype : 'combobox',
		margin : '5 10 0 10'
	},{
		boxLabel :load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beCarSmallUsed')/*'是否大车小用'*/,
		name :'beCarSmallUsed',
		xtype : 'checkbox',
		margin : '5 10 0 10'
	},{
		xtype : 'container',
		html : '<hr/>',
		columnWidth : 1
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.paymentType')/*'付款方式'*/,
		name : 'paymentType',
		margin : '0 10 5 10'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.feeTotal')/*'总运费'*/,
		margin : '0 10 5 10',
		name : 'feeTotal'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotal')/*'预付运费总额'*/,
		margin : '0 10 5 10',
		name : 'prePaidFeeTotal'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.arriveFeeTotal')/*'到付运费总额'*/,
		margin : '0 10 5 10',
		name : 'arriveFeeTotal'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadFeeTotal')/*'装车总金额'*/,
		name : 'loadFeeTotal',
		xtype : 'numberfield'
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.collectionFeeTotal')/*'代收货款'*/,
		name : 'collectionFeeTotal',
		xtype : 'numberfield'
	}, {
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beReturnReceipt')/*'是否押回单'*/,
		name : 'beReturnReceipt',
		xtype : 'checkbox',
		columnWidth : .15
	}, {
		boxLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beTimeLiness')/*'是否签署时效条款'*/,
		name : 'beTimeLiness',
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var selectResultPanel = load.vehicleassemblebillshow.RewardOrPunishGrid;
				if(newValue){
					//显示奖罚协议
					selectResultPanel.setVisible(true);
				}else{
					selectResultPanel.setVisible(false);
				}
			}
		}
		
	},{
		fieldLabel : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.feeTotalChangeNotes')/*'总运费修改原因'*/,
		name : 'feeTotalChangeNotes',
		columnWidth : .85,
		hidden : true
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单列表Model
Ext.define('Foss.load.vehicleassemblebillshow.HandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	}, {
		name : 'handOverTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'arriveDept',
		type : 'string'
	}, {
		name : 'arriveDeptCode',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	} ,{
		name : 'volumeTotal',
		type : 'number'
	},{
		name : 'goodsQtyTotal',
		type : 'number'
	},{
		name : 'moneyTotal',	
		type : 'number'
	},{
		name : 'codAmountTotal',
		type : 'number'
	},{
		name : 'waybillQtyTotal',
		type : 'number'
	},{
		name : 'weightTotal',
		type : 'number'
	},{
		name : 'note',
		type : 'string'
	}]
});

//定义主页交接单store
Ext.define('Foss.load.vehicleassemblebillshow.HandOverBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillshow.HandOverBillModel',
	autoLoad : false,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillListByVNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vehicleAssembleBillVo.handOverBillList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			Ext.apply(operation, {
				params : {
					'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillshow.vehicleAssembleNo
				}
			});	
		},
		'load' : function(store,records,successful,eOpts){
			var record;
			//装车总金额、代收货款总额
			var totalMoneyCmp = load.vehicleassemblebillshow.basicInfoForm.getForm().findField('loadFeeTotal');
			var totalCodCmp = load.vehicleassemblebillshow.basicInfoForm.getForm().findField('collectionFeeTotal');
			//总重量、体积、件数、票数
			var totalWeightCmp = Ext.getCmp('Foss_load_vehicleassemblebillshow_TotalWeight');
			var totalVolumeCmp = Ext.getCmp('Foss_load_vehicleassemblebillshow_TotalVolume');
			var totalPiecesCmp = Ext.getCmp('Foss_load_vehicleassemblebillshow_TotalPieces');
			var totalWaybillCmp = Ext.getCmp('Foss_load_vehicleassemblebillshow_TotalWaybill');
			for(var i in records){
				record = records[i];
				//统计总重量、体积、件数、票数
				totalWeightCmp.setValue(totalWeightCmp.getValue() + record.get('weightTotal'));
				totalVolumeCmp.setValue(totalVolumeCmp.getValue() + record.get('volumeTotal'));
				totalPiecesCmp.setValue(totalPiecesCmp.getValue() + record.get('goodsQtyTotal'));
				totalWaybillCmp.setValue(totalWaybillCmp.getValue() + record.get('waybillQtyTotal'));
				//统计装车总金额、代收货款总额
				totalMoneyCmp.setValue(totalMoneyCmp.getValue() + record.get('moneyTotal'));
				totalCodCmp.setValue(totalCodCmp.getValue() + record.get('codAmountTotal'));
			}
		}
	}
});

//定义主页交接单列表
Ext.define('Foss.load.vehicleassemblebillshow.HandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.gridTitle')/*'交接单列表'*/,
//	bodyCls : 'autoHeight',
	height : 350,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.vehicleassemblebillshow.HandOverBillStore'),
	columns : [{
		dataIndex : 'arriveDept',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		flex : 1,
		text : '重量</br>(千克)'
	}, {
		dataIndex : 'volumeTotal',
		align : 'center',
		flex : 1,
		text : '体积</br>(方)'
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.goodsQtyTotal')/*'件数'*/
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.waybillQtyTotal')/*'票数'*/
	}, {
		dataIndex : 'note',
		align : 'center',
		width : 300,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/
	}, {
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/,
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(value!=null){
				return '<a href="javascript:load.vehicleassemblebillshow.showHandOverBillDetail('+"'" + value + "'"+
				",'"+null+"'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	}],
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_vehicleassemblebillshow_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
	    	margin:'0 0 0 0',
			xtype: 'textfield',
			readOnly:true,
			anchor: '100%',
			labelWidth:80
		},
		items: [{
			id : 'Foss_load_vehicleassemblebillshow_TotalWeight',
			fieldLabel: '总重量(千克)',
			columnWidth : 1/4,
			labelWidth : 120,
			xtype : 'numberfield',
			value : 0
			},{
			id : 'Foss_load_vehicleassemblebillshow_TotalVolume',
			fieldLabel: '总体积(方)',
			labelWidth : 120,
			columnWidth : 1/4,
			xtype : 'numberfield',
			value : 0
			},{
			id : 'Foss_load_vehicleassemblebillshow_TotalPieces',
			fieldLabel: load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalPieces')/*'总件数'*/,
			columnWidth : 1/5,
			xtype : 'numberfield',
			value : 0
			},{
			id : 'Foss_load_vehicleassemblebillshow_TotalWaybill',
			fieldLabel: load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalWaybill')/*'总票数'*/,
			columnWidth : 1/5,
			xtype : 'numberfield',
			value : 0
			}]
	  }]
});

//点击“交接单编号”打开详情界面方法
load.vehicleassemblebillshow.showHandOverBillDetail = function(handOverBillNo,handOverType){
	load.addTab('T_load-handoverbillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.showHandOverBillTabTitle')/*'交接单详情'*/,//打开的Tab页的标题
			//'"&handOverType="'+handOverType+
			load.realPath('handoverbillshowindex.action') + '?handOverBillNo="' + handOverBillNo + '"&handOverType="'+handOverType+'"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//定义交接单日志Model
Ext.define('Foss.load.vehicleassemblebillshow.OptLogModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'operatorName',
		type : 'string'
	},{
		name : 'optTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'optType',
		type : 'string'
	},{
		name : 'optContent',
		type : 'string'
	}]
});

//定义配载单日志store
Ext.define('Foss.load.vehicleassemblebillshow.OptLogStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillshow.OptLogModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryVehicleAssembleBillOptLogByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vehicleAssembleBillVo.optLogList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			Ext.apply(operation, {
				params : {
					'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillshow.vehicleAssembleNo
				}
			});	
		}
	}
});

//定义交接单修改日志列表
Ext.define('Foss.load.vehicleassemblebillshow.OptLogGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.gridTitle')/*'修改日志'*/,
//	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	collapsed : true,//页面初始化时不展开该grid，不加载数据
	animCollapse : true,
	store : Ext.create('Foss.load.vehicleassemblebillshow.OptLogStore'),
	columns : [{
		dataIndex : 'operatorName',
		align : 'center',
		width : 70,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.operatorName')/*'操作人'*/
	}, {
		dataIndex : 'optTime',
		align : 'center',
		width : 135,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optTime')/*'操作时间'*/,
		renderer : function(value){
			if(value!=null){
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');									
		}else{
				return null;
			}
		}
	}, {
		dataIndex : 'optType',
		align : 'center',
		width : 100,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optType')/*'操作类别'*/
	}, {
		dataIndex : 'optContent',
		flex : 1,
		text : load.vehicleassemblebillshow.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optContent')/*'操作内容'*/,
		xtype : 'linebreakcolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
	});
	load.vehicleassemblebillshow.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	listeners : {
		'expand' : function(panel, eOpts){
			this.store.load();	//展开表格后加载日志数据
		}
	}
});

//基本信息Form
load.vehicleassemblebillshow.basicInfoForm = Ext.create('Foss.load.vehicleassemblebillshow.BasicInfoForm');
//交接单列表grid
load.vehicleassemblebillshow.mainPageGrid = Ext.create('Foss.load.vehicleassemblebillshow.HandOverBillGrid');
//奖罚协议gird
load.vehicleassemblebillshow.RewardOrPunishGrid = Ext.create('Foss.load.vehicleassemblebillshow.RewardOrPunishGrid');
//onReady
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-vehicleassemblebillshowindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [load.vehicleassemblebillshow.basicInfoForm,
		         load.vehicleassemblebillshow.RewardOrPunishGrid,
		         load.vehicleassemblebillshow.mainPageGrid,
		         Ext.create('Foss.load.vehicleassemblebillshow.OptLogGrid')],
		renderTo : 'T_load-vehicleassemblebillshowindex-body'
	});
	//后台读取配载单基本信息
	Ext.Ajax.request({
		url : load.realPath('queryVehicleAssembleBillByNo.action'),
		params : {'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillshow.vehicleAssembleNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.vehicleAssembleBillVo.baseEntity;
			var form = load.vehicleassemblebillshow.basicInfoForm.getForm();
			
			if(basicInfo.beInLTLCar=='Y'){
				basicInfo.beInLTLCar=true;
			}
			
			//是否在途装卸、是否最终到达、是否押回单
			if(basicInfo.beMidWayLoad == 'Y'){
				basicInfo.beMidWayLoad = true;
				form.findField('beFinallyArrive').setVisible(true);
				form.findField('midWayLoadType').setVisible(true);
				form.findField('beMidWayOnlyLoad').setVisible(true);
			}else{
				form.findField('midWayLoadType').setVisible(false);
				form.findField('beFinallyArrive').setVisible(false);
				form.findField('beMidWayOnlyLoad').setVisible(false);
			}
			
			if(basicInfo.beFinallyArrive == 'Y'){
				basicInfo.beFinallyArrive = true;
			}
			if(basicInfo.beReturnReceipt == 'Y'){
				basicInfo.beReturnReceipt = true;
			}
			if(basicInfo.beMidWayOnlyLoad =='Y'){
				basicInfo.beMidWayOnlyLoad= true;
				form.findField('onTheWayDestOrgCode').setVisible(true);
				
			}else{
				form.findField('onTheWayDestOrgCode').setVisible(false);
			}
			if(basicInfo.beCarSmallUsed=='Y'){
				basicInfo.beCarSmallUsed=true;
			}else{
				basicInfo.beCarSmallUsed=false;
			}
			//是否时效条款
			if(basicInfo.beTimeLiness == 'Y'){
				basicInfo.beTimeLiness =true;
				var rewardOrpunishgird = Ext.getCmp('Foss_load_vehicleassemblebillshow_RewardOrPunish_ID');
				var data = result.vehicleAssembleBillVo.rewardOrPunishDto.rewardOrPunishAgreementEntity,
				rewardMoney = result.vehicleAssembleBillVo.rewardOrPunishDto.rewardMaxMoney,
				fineMaxMoney = result.vehicleAssembleBillVo.rewardOrPunishDto.fineMaxMoney;
				//加载奖罚信息
				rewardOrpunishgird.getStore().loadData(data,null);
				if(rewardMoney==0){
					Ext.getCmp('Foss_load_vehicleassemblebillshow_RewardMaxMoney').setVisible(false);
				}
				//设置奖励最高金额
				Ext.getCmp('Foss_load_vehicleassemblebillshow_RewardMaxMoney').setValue(rewardMoney);
				//设置惩罚最高金额
				Ext.getCmp('Foss_load_vehicleassemblebillshow_FineMaxMoney').setValue(fineMaxMoney);
				
			}
			var basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.load.vehicleassemblebillshow.BillBasicInfoModel'),
				changeCmp = form.findField('feeTotalChangeNotes');
			form.loadRecord(basicInfoRecord);
			if(!Ext.isEmpty(form.findField('feeTotalChangeNotes').getValue())){
				changeCmp.setVisible(true);
			}
			load.vehicleassemblebillshow.mainPageGrid.store.load();
			form.findField('onTheWayDestOrgCode').setValue(basicInfo.onTheWayDestOrgName);
			//如果为外请车，显示车型，隐藏货柜，反之则反之
			if(basicInfo.vehicleOwnerShip == 'Leased'){
				form.findField('containerNo').setVisible(false);
				form.findField('vehicleModel').setVisible(true);
				//外请车则显示适用载重和适用净空 司机自带货量
				form.findField('driverOfWeight').setVisible(true);
				form.findField('driverOfVolumn').setVisible(true);
				form.findField('applicationRatedLoad').setVisible(true);
				form.findField('applicationRatedClearance').setVisible(true);
				form.findField('beCarSmallUsed').setVisible(true);
				Ext.getCmp('Foss_load_vehicleassemblebillshow_m³').setVisible(true);
			}else{
				//如果为公司车，则获取车辆类型来控制货柜号和车型的显示或者隐藏
				var vehicleType = result.vehicleAssembleBillVo.ownerVehicleType
				if(vehicleType == 'vehicletype_tractors'){
					form.findField('vehicleModel').setVisible(false);
					form.findField('containerNo').setVisible(true);
				}else{//如果为厢式车或挂车
					form.findField('vehicleModel').setVisible(true);
					form.findField('containerNo').setVisible(false);
				}
				//公司车则影藏适用载重和适用净空 司机自带货量
				form.findField('driverOfWeight').setVisible(false);
				form.findField('driverOfVolumn').setVisible(false);
				form.findField('applicationRatedLoad').setVisible(false);
				form.findField('applicationRatedClearance').setVisible(false);
				form.findField('beCarSmallUsed').setVisible(false);
				Ext.getCmp('Foss_load_vehicleassemblebillshow_m³').setVisible(false);
			}
		}
	});
});