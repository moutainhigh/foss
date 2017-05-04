/**
 * author:foss-yuting
 */
sign.pickUpInventory.PKP_INADVANCE_GOODS_STATE='PKP_INADVANCE_GOODS_STATE';//提前找货状态
var arr=new Array(10);
(function(){
	sign.pickUpInventory.pickUpInventoryStore =FossDataDictionary.getDataDictionaryStore(sign.pickUpInventory.PKP_INADVANCE_GOODS_STATE, null);
})();
//待处理  ---查询Model
Ext.define('Foss.sign.pickUpInventoryOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
	    {name: 'id',type: 'string'},// 运单id
	    {name: 'serialNumber', type: 'int'},// 序列号
		{name: 'waybillNo',type: 'string'},// 运单编号
		{name: 'goodPackage', type: 'string'},// 货物的包装
		{name: 'billingGoodsQty', type: 'int'},// 货物的开单件数
		{name: 'goodWeight', type: 'string'},// 货物重量
		{name: 'goodVolume', type: 'string'},// 货物体积
		{name: 'goodSize', type: 'string'},// 货物尺寸
		{name: 'state', type: 'string'},// 操作的状态
		{name: 'operTime', type: 'date',
		 convert:function(value){
			 if(value!=null){
				 var date = new Date(value);
				 return date;
			 }else{
				 return null;
			 }
		  }
		}// 操作时间
	]
});
 
// 待处理---Store
Ext.define('Foss.sign.pickUpInventoryOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.pickUpInventoryOutStorageModel',
	pageSize: 10,
	proxy : {
		type : 'ajax',
		url:sign.realPath('queryPickupList.action'),
		actionMethods : 'post',
		timeout: 100000,
		reader : {
			type : 'json', 
			root : 'pickupResultVo.pickupResultDtoList',
			totalProperty : 'totalCount' //总数
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			var form = Ext.getCmp('T_sign-pickUpInventoryIndex_content').getQueryForm().getForm();
			// 操作时间验证
			var operTimeStart = form.getValues().operTimeStart, operTimeEnd = form.getValues().operTimeEnd;
			if (!Ext.isEmpty(operTimeStart) && !Ext.isEmpty(operTimeEnd)) {	
				var result = Ext.Date.parse(operTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(operTimeStart,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 3){	
					Ext.ux.Toast.msg(sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.tip'), sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.the.date.range.cannot.be.more.than.3.days'), 'error', 3000); // '起止日期相隔不能超过3天！'
					return false;	
				}	
			}else {//如果操作时间为空
				Ext.ux.Toast.msg(sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.tip'), sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.operTimeNotNull'), 'error', 3000);
					return false;
			}
			if (!form.isValid()){
				return false;
			}
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-pickUpInventoryIndex_content').getQueryForm().getValues();
			Ext.apply(operation,{
				params:{
					'pickupVo.pickupDto.waybillNo':queryParams.waybillNo,// 运单号
					'pickupVo.pickupDto.state':queryParams.state,// 提货状态
					'pickupVo.pickupDto.operTimeStart':queryParams.operTimeStart,// 操作时间起
					'pickupVo.pickupDto.operTimeEnd':queryParams.operTimeEnd// 操作时间止
				}
			});
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.tip'), data.message, 'error', 2000);	//提示			
			}
		}
	}
});


// 查询条件----第一层
Ext.define('Foss.pickUp.QuerypickUpInventoryFormPanel',{
	extend: 'Ext.form.Panel',
	title:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.queryBuilder'),// 查询条件
    defaultType : 'textfield',
	id:'Foss.sign.QuerypickUpInventoryFormPanel_ID',
	collapsible: true,
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	items: [{
		fieldLabel:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.waybillNo'),//运单号
		name: 'waybillNo',//运单号
		vtype: 'waybill',
		columnWidth:.20,
		labelWidth:40
	},{
		xtype: 'rangeDateField',
		fieldLabel:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.settleTime'),//操作时间
		fieldId:'Foss_sign_PickUpInventoryFormPanel_rangeDateField_ID',
		fromEditable:false,
		toEditable :false,
		disallowBlank:true,
		labelWidth:65,
		dateType: 'datetimefield_date97',
		fromName: 'operTimeStart',
		toName: 'operTimeEnd',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,0,0,0),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .45
	},{
		xtype:'combo',
		name: 'state',
		fieldLabel:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.state'),//状态
		labelWidth: 40, 
		forceSelection: true, 
		editable:false, 
		forceSelection:true,
		valueField:'valueCode',  
		displayField: 'valueName', 
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .15,
		store : FossDataDictionary.getDataDictionaryStore(
				'PKP_INADVANCE_GOODS_STATE', null, {
					'valueCode' : 'GOOD_STATE_ALL',
					'valueName' : '全部'
				}),
		listeners : {
			'select' : function(combo, records, eOpts){
			
	    		
			}
		}
	},{
		xtype : 'button',
		text :sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.search'),//'查询'
		columnWidth : .08,
		disabled:!sign.pickUpInventory.isPermission('sign/pickUpInventoryIndexquerybutton'),
		hidden:!sign.pickUpInventory.isPermission('sign/pickUpInventoryIndexquerybutton'),
		cls:'yellow_button',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
		handler : function() {
			Ext.getCmp('T_sign-pickUpInventoryIndex_content').getArriveGrid().getPagingToolbar().moveFirst();		
		}
	}
	],
	listeners : {
		beforerender:function(){
				var form=Ext.getCmp('Foss.sign.QuerypickUpInventoryFormPanel_ID').getForm();
				form.findField('state').setValue('GOOD_STATE_HASINFORM');				
			}
		}
});

	
// 待处理-GridPanel---第一层
Ext.define('Foss.pickUp.QuerypickUpInventoryGridPanel',{
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    frame: true,
	id: 'QuerypickUpInventory_GridPanel_Id',
    stripeRows: true,
    title:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.pending'),//'待处理'
    emptyText:sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.emptyText'),//查询结果为空'
	collapsible: true,
	animCollapse: true,
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
	store: null,
	viewConfig: {
        enableTextSelection: true
    },
	columns: [
		/*{text: sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.operate'), dataIndex: 'operate', xtype:'checkcolumn',flex: 1,align: 'center', stopSelection:true,listeners:{
						'checkchange': function(model,index,state){
								if(state==true){
									arr[index]=index;
								}else{
									arr[index]=null;
								}	
					}
				}},*///操作
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.no'),align: 'center',flex: 1,dataIndex : 'serialNumber'},//序号
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.waybillNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'waybillNo' }, //单号
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.package'),align: 'center',flex: 1,dataIndex : 'goodPackage' },//包装
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.goodsQty'),align: 'center',flex: 1,dataIndex : 'billingGoodsQty' },//件数
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.weight'),align: 'center',flex: 1,dataIndex : 'goodWeight' },//重量
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.volume'),align: 'center',flex: 1,dataIndex : 'goodVolume' },//体积
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.size'),align: 'center',width : 150,dataIndex : 'goodSize'} ,//尺寸
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.state'),align: 'center',width : 100,dataIndex : 'state'},//状态
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.settleTime'),align: 'center',width : 200,dataIndex : 'operTime', 
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		},//操作时间
		{ text : sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.id'),align: 'center',width : 150,dataIndex : 'id',hidden:true,hideable:false} //隐藏数据id
    ],
    pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
	  dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
				{
					xtype : 'button',
					columnWidth:.08,
					margin : '0 0 0 5',
					text :sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.Revocation'),//撤销
					disabled:!sign.pickUpInventory.isPermission('sign/updatePickupStatebutton'),
					hidden:!sign.pickUpInventory.isPermission('sign/updatePickupStatebutton'),
					handler:function(grid, rowIndex, colIndex){
						me = this.up('toolbar').up('grid');
						store = me.getStore();
						var data=store.data;
						var pickUprowObjs = me.getSelectionModel().getSelection();
						var pickUpList = new Array();
						if(pickUprowObjs!=null){
							for (var i = 0; i < pickUprowObjs.length; i++) {
								if(pickUprowObjs[i].data.state==sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.goodStateRevocation')){
									continue;
								}
								pickUpList.push({
											'id':pickUprowObjs[i].data.id,//主键id
											'waybillNo':pickUprowObjs[i].data.waybillNo,//运单号
											'state':pickUprowObjs[i].data.state//提货状态
										});
							}
						}
						
						if(pickUpList==null||pickUpList.length<=0){
							Ext.ux.Toast.msg(sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.tip'), sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.waybillNoRevoke'), 'error', 2000);	//提示
							return ;
						}
						Ext.Ajax.request({
							url:sign.realPath('updatePickupState.action'),
							method: 'POST',
							timeout: 300000,
							jsonData: {
								'pickupVo':{
									'pickupList':pickUpList//流水号信息集合
								}
							},
							success: function(response){
								Ext.getCmp('T_sign-pickUpInventoryIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
							},exception: function(response){
								Ext.ux.Toast.msg(sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.tip'), sign.pickUpInventory.i18n('pkp.sign.pickUpInventory.exception'), 'error', 3000);
							}
						});
					}
				}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.pickUpInventoryOutStorageStore');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm =  Ext.create('Foss.pickUp.QuerypickUpInventoryFormPanel');
	var queryResultGrid = Ext.create('Foss.pickUp.QuerypickUpInventoryGridPanel');
	Ext.create('Ext.panel.Panel',{
		id:'T_sign-pickUpInventoryIndex_content',
		cls:"panelContent",
		bodyCls:'panelContent-body',
		layout:'auto',
		getArriveGrid: function(){
			return queryResultGrid;
		},
		getQueryForm: function(){
			return queryForm;
		},
		margin:'0 0 0 0',
		items : [queryForm,queryResultGrid],
		renderTo: 'T_sign-pickUpInventoryIndex-body'
	});
	
});

 