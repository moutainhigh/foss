//奖罚类型
load.vehicleassemblebillmodify.returnSubType = 'AWARD_TYPE';
//奖罚时间段之间的互斥关系
load.vehicleassemblebillmodify.timeMap = new Ext.util.HashMap();
load.vehicleassemblebillmodify.timeMap.add("0",['0','1','0-1','1-2','2']);
load.vehicleassemblebillmodify.timeMap.add("1",['0','1','1-2','2']);
load.vehicleassemblebillmodify.timeMap.add("2",['0','1','2']);
load.vehicleassemblebillmodify.timeMap.add("0-1",['0','0-1']);
load.vehicleassemblebillmodify.timeMap.add("1-2",['0','1','1-2']);
//奖罚时间段的组合关系
load.vehicleassemblebillmodify.timeCombinationMap = new Ext.util.HashMap();
load.vehicleassemblebillmodify.timeCombinationMap.add("0",['0']);
load.vehicleassemblebillmodify.timeCombinationMap.add("1",['0-1','1']);
load.vehicleassemblebillmodify.timeCombinationMap.add("2",['0-1','1-2','2']);
//初始化挂牌号为空
load.vehicleassemblebillmodify.trailerVehicleNo=null;
//自定义方法，判断是否为合法数字
load.vehicleassemblebillmodify.isNumber = function(value){
	if(value === ''
		|| value === null
		|| value === undefined
		|| value === 0
		|| value === '0'
		|| value === NaN
		|| value === Infinity
		|| isNaN(value)){
		return false;
	}
	return true;
}
load.vehicleassemblebillmodify.changeCodeToNameStore = function(store, code) {
	var name = '';
	if (!Ext.isEmpty(store)) {
		store.each(function(record) {
			if (record.get('valueCode') == code) {
				name = record.get('valueName');
			}
		});
	}
	return name;
};
//奖罚协议 奖励model
Ext.define('Foss.load.vehicleassemblebillmodify.RewardOrPunishModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id',			type:'string'},
	        {name:'rewardOrPunishType',  type:'String'},
	        {name:'timeLimit',	type:'string'},//时间段
	        {name:'agreementMoney',type:'string'},//奖励金额
	        {name:'rewardOrPunishMaxMoney' , type:'string'}]//最高上限金额
});
Ext.define('Foss.load.vehicleassemblebillmodify.RewardOrPunishStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.vehicleassemblebillmodify.RewardOrPunishModel',
	proxy: {
        type : 'ajax',
        reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vehicleAssembleBillVo.vehicleRewardProtEntity'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {}
	},
    exception : function(response) {}
});
//奖罚协议录入 奖励grid
Ext.define('Foss.load.vehicleassemblebillmodify.RewardOrPunishGrid',{
	extend: 'Ext.grid.Panel',
	id:'Foss_load_vehicleassemblebillmodify_RewardOrPunish_ID',
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
    title: load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.gridTitle'),//时效条款录入
    listeners : {
		'cellclick' : function(me, td, cellIndex, record, tr,rowIndex, e, eOpts) {
				if(load.vehicleassemblebillmodify.state!=10||load.vehicleassemblebillmodify.checkCarriageContractPrinted==1){
					return false;
				}else{
					return true;
				}
			},
		'blur' : function( me, the,  eOpts ){
			alert('asdasd');
		}
    },

    columns:[{
    	xtype:'actioncolumn',
    	width:40,
    	text:load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.action'),//'操作'
    	items: [{
			iconCls: 'deppon_icons_delete',
			tooltip:load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.delete'),//删除
			handler: function(grid, rowIndex, colIndex) {
				//记录删除行的数据
				var rowValue = grid.getStore().getAt(rowIndex).data;
				//删除选中行
				grid.getStore().removeAt(rowIndex);	
				
				var items=this.up('grid').getSelectionModel().store.data.items;
				var temp=0;
				var rowid =0;
				for(var i=0;i<items.length;i++){
					if(rowValue.rewardOrPunishType==items[i].data.rewardOrPunishType){
						temp=items[i].data.agreementMoney;
						rowid=items[i].internalId;
						break;
					}
				}
	 
				for(var i=0;i<items.length;i++){
					if(parseInt(items[i].data.agreementMoney)>temp&&
						(items[i].internalId != rowid )&&
						rowValue.rewardOrPunishType == items[i].data.rewardOrPunishType){
							
						temp=parseInt(items[i].data.agreementMoney);
					}
				}
				if(rowValue.rewardOrPunishType =='REWARD'){
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(temp);
					
				}else if(rowValue.rewardOrPunishType =='FINE'){
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(temp);
				}
				
				if(Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').getValue()==0){
    				Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(false);
    			}else{
    				Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(true);
    			}
			}
		}],
		renderer:function(value, metadata, record){
			if(load.vehicleassemblebillmodify.state!=10||load.vehicleassemblebillmodify.checkCarriageContractPrinted==1){
				this.items[0].iconCls=''
			}else{
				this.items[0].iconCls='deppon_icons_delete'
			}
			
		}
    },{
    	header: "id",//id
		dataIndex:'id',
		hidden:true	
    },{
    	header:load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardType'),//"条款类型"
    	dataIndex:'rewardOrPunishType',
    	allowBlank:false,
    	renderer : function(value){
			var store = FossDataDictionary.getDataDictionaryStore(load.vehicleassemblebillmodify.returnSubType);
			
			return load.vehicleassemblebillmodify.changeCodeToNameStore(store,value);
		},
    	editor:{
    		xtype : 'combo',
    		queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,	
			store : FossDataDictionary.getDataDictionaryStore(load.vehicleassemblebillmodify.returnSubType),
			listeners : {
    			'change' : function(field,  newValue,  oldValue,  eOpts){
    				var rowValue = this.up('grid').getSelectionModel().selected.items[0];

    					var items=this.up('grid').getSelectionModel().store.data.items;
    					//如过已经选择了奖罚区间
    					if(!Ext.isEmpty(rowValue.data.timeLimit)){
    						
    						for(var i=0;i<items.length;i++){
	    						if(items[i].internalId != rowValue.internalId 
		    						&& !Ext.isEmpty(items[i].data.timeLimit)
		    						&& newValue == items[i].data.rewardOrPunishType){   							
	    							//map的value 存的是互斥数组
	    							var array = load.vehicleassemblebillmodify.timeMap.get(rowValue.data.timeLimit);
	    							for(var j=0;j<array.length;j++){
	    								if(items[i].data.timeLimit == array[j]){    									
	    									Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '该时间段不能选择该奖罚类型或存在重复的协议！', 'error', 3000);
	    									field.value='';
	    									return;
	    								}
	    							}  							
	    						}
	
    					 	}
    					}
    				//设置改动后的类型对于的上限金额
    				var rowValue = this.up('grid').getSelectionModel().selected.items[0];
    				var temp = rowValue.data.agreementMoney;
    				var items=this.up('grid').getSelectionModel().store.data.items;
    				if(!Ext.isEmpty(temp)){
	    				for(var i=0;i<items.length;i++){
	    					if(parseInt(items[i].data.agreementMoney)>temp&&
	    						(items[i].internalId != rowValue.internalId )&&
	    						newValue == items[i].data.rewardOrPunishType){
	    							
	    						temp=parseInt(items[i].data.agreementMoney);
	    					}
	    				}
		    				
	    				if(newValue =='REWARD'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(temp);
		    					
		    			}else if(newValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(temp);
		    			}
		    				
    				}
	    			//设置改动前对于类型的上限金额
    				var tempa;
    				var tempb=0;
					for(var i=0;i<items.length;i++){
						if(oldValue == items[i].data.rewardOrPunishType && items[i].internalId != rowValue.internalId ){
							tempa = items[i];
							break;
						}
					}
					if(Ext.isEmpty(tempa)){
						if(oldValue =='REWARD'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(0);
		    					
		    			}else if(oldValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(0);
		    			}
					}else{
						tempb = tempa.data.agreementMoney
						for(var i=0;i<items.length;i++){
	    					if(parseInt(items[i].data.agreementMoney)>tempb&&
	    						(items[i].internalId != tempa.internalId )&&(items[i].internalId !=rowValue.internalId)
	    						&& oldValue == items[i].data.rewardOrPunishType){	
	    						tempb=parseInt(items[i].data.agreementMoney);
	    					}
	    				}
	    				
	    				if(oldValue =='REWARD'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(tempb);
		    					
		    			}else if(oldValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(tempb);
		    			}
					}
    					
					if(Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').getValue()==0){
	    				Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(false);
					}else{
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(true);
					}
    			}

    	   }
    	},
		width:350
    },{
    	header:load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.timelimit'),//"时间段"
    	dataIndex:'timeLimit',
    	allowBlank:false,
    	renderer : function(value){
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
			}
		},
    	editor:{
    		xtype : 'combo',
    		queryMode : 'local',
			displayField : 'value',
			valueField : 'key',
			editable : false,	
			store :Ext.create('Ext.data.Store',{
				fields:['key','value'],
				data:[{"key":"0","value":"0小时以上"},
					{"key":"0-1","value":"0-1小时"},
					{"key":"1","value":"1小时以上"},
					{"key":"1-2","value":"1-2小时"},
					{"key":"2","value":"2小时以上"}]
			}),
			listeners : {
    		'change' : function(field,  newValue,  oldValue,  eOpts){
    				var rowValue = this.up('grid').getSelectionModel().selected.items[0];

    					var items=this.up('grid').getSelectionModel().store.data.items;
    					
    					for(var i=0;i<items.length;i++){
    						if(items[i].internalId != rowValue.internalId 
	    						&& !Ext.isEmpty(items[i].data.timeLimit)
	    						&& rowValue.data.rewardOrPunishType == items[i].data.rewardOrPunishType){   							
    							//map的value 存的是互斥数组
    							var array = load.vehicleassemblebillmodify.timeMap.get(newValue);
    							for(var j=0;j<array.length;j++){
    								if(items[i].data.timeLimit == array[j]){    									
    									Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '不能选择该时间段！', 'error', 3000);
    									field.value='';
    									return;
    								}
    							}  							
    						}
	
    					}
    			}
    		}
    	
    	},
    	width:300
    	

    } ,{
    	header: load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.agreementMoney'),//奖励金额
		dataIndex:'agreementMoney',
		allowBlank:false,
		editor: {
			xtype: 'numberfield',
			minValue: 0,
			allowDecimals : false,
			validator : function(value){
				if(value>100000000){
					return false;
				}
				return  true;
			},
            listeners : {
    			'change' : function(field,  newValue,  oldValue,  eOpts){
    				var reg = /^[0-9]\d*$/;
    				if(Ext.isEmpty(newValue)){
    					newValue = 0;
    				}
    				var temp = parseInt(newValue);
    				var rowValue = this.up('grid').getSelectionModel().selected.items[0];
    				var items=this.up('grid').getSelectionModel().store.data.items;
    				//true/*temp<parseInt("100000000") && reg.test(newValue) && field.isValid() && temp>=parseInt("0") */
    				if(field.isValid()){
	    				for(var i=0;i<items.length;i++){
	    					if(parseInt(items[i].data.agreementMoney)>temp&&
	    						(items[i].internalId != rowValue.internalId )&&
	    						rowValue.data.rewardOrPunishType == items[i].data.rewardOrPunishType){
	    							
	    						temp=parseInt(items[i].data.agreementMoney);
	    					}
	    				}
	    				load.vehicleassemblebillmodify.money = newValue;
    				}else{
    					Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '不能输入超过8位的条款金额！', 'error', 3000);
    					field.setValue(load.vehicleassemblebillmodify.money);
    					temp = load.vehicleassemblebillmodify.money;
    					for(var i=0;i<items.length;i++){
	    					if(parseInt(items[i].data.agreementMoney)>temp&&
	    						(items[i].internalId != rowValue.internalId )&&
	    						rowValue.data.rewardOrPunishType == items[i].data.rewardOrPunishType){
	    							
	    						temp=parseInt(items[i].data.agreementMoney);
	    					}
	    				}
    				
    				}
    				if(rowValue.data.rewardOrPunishType =='REWARD'){
	    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(temp);
	    					
	    			}else if(rowValue.data.rewardOrPunishType =='FINE'){
	    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(temp);
	    			}
	    				
    				if(Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').getValue()==0){
    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(false);
    				}else{
    					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(true);
    				}
    			}
    		}
		},
		width:300
    }],
	tbar : [{
			   xtype : 'button',
			   text : load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.add'),//新增
			   id:'Foss_load_vehicleassemblebillmodify_rewardOrpunish_addButton_ID',
			   handler:function(){
				   var record = Ext.create('Foss.load.vehicleassemblebillmodify.RewardOrPunishModel', {
					   
	               });
	               
				  var grid = this.up('grid');
				  var store = grid.getStore(),
				  edit = grid.getEditor();
				  
				  edit.cancelEdit();
				  store.insert(store.getCount(), record);	  
				  edit.startEditByPosition({
					row: 0,
					column: 0
					});
			   }
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
			id : 'Foss_load_vehicleassemblebillmodify_FineMaxMoney',
			fieldLabel :load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.finMaxMoney'),//本次惩罚封顶费用,
			columnWidth : 1/2,
			labelWidth : 120,
			xtype : 'numberfield',
			value: 0
		} , {
			id : 'Foss_load_vehicleassemblebillmodify_RewardMaxMoney',
			fieldLabel : load.vehicleassemblebillmodify.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardMaxMoney'),//本次奖励封顶费用
			columnWidth : 1/2,
			labelWidth : 120,
			xtype : 'numberfield',
			value: 0
		}]
	}],
	editor:null,
	getEditor:function(){
		if(this.editor==null){
			this.editor= Ext.create('Ext.grid.plugin.CellEditing', {
				       clicksToEdit:1   
		          })
		}
		return this.editor;
	},
	 constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.vehicleassemblebillmodify.RewardOrPunishStore');
			me.plugins=[
				me.getEditor()
			];
			me.callParent([cfg]);
    }
   
});
// 配载单基本信息Model
Ext.define('Foss.load.vehicleassemblebillmodify.BillBasicInfoModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'truckDepartPlanDetailId',
		type : 'string'
	},{
		name : 'assembleType',
		type : 'string'
	},{
		type : 'string',
		name : 'destOrgCode'
	}, {
		type : 'string',
		name : 'destOrgName'
	}, {
		type : 'date',
		name : 'leaveTime'
	}, {
		type : 'string',
		name : 'vehicleAssembleNo'
	}, {
		type : 'string',
		name : 'vehicleNo'
	},  {
		type : 'string',
		name : 'driverCode'
	},  {
		type : 'string',
		name : 'driverName'
	},  {
		type : 'string',
		name : 'vehicleOwnerShip'
	}, {
		type : 'string',
		name : 'frequencyNo'
	}, {
		type : 'string',
		name : 'transProperty'
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
		name : 'goodsType'
	}, {
		type : 'boolean',
		name : 'beMidWayLoad'     
	}, {
		type : 'boolean',
		name : 'beFinallyArrive'
	},{
		type : 'boolean',
		name : 'beMidWayOnlyLoad'
	},{
		type : 'string',
		name : 'onTheWayDestOrgCode'
	},{
		type : 'string',
		name : 'midWayLoadType'
	},{
		type : 'string',
		name : 'vehicleMessage'
	},{
		type : 'string',
		name : 'note'
	},{
		type : 'string',
		name : 'paymentType'
	},{
		type : 'number',
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
	},{
		type : 'number',
		name : 'totalWaybill'
	},{
		type : 'number',
		name : 'totalPieces'
	},{
		type : 'number',
		name : 'totalVolume'
	},{
		type : 'number',
		name : 'totalWeight'
	},{
		type : 'date',
		name : 'createDate'
	},{
		type : 'string',
		name : 'inviteNo'
	},{
		type : 'boolean',
		name : 'beTimeLiness'
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
Ext.define('Foss.load.vehicleassemblebillmodify.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.formTitle')/*'配载单基本信息'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.assembleType')/*'配载类型'*/,
		name : 'assembleType',
		readOnly : true,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
		store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"OWNER_LINE", "value":"专线"},
	            {"key":"CAR_LOAD", "value":"整车"}
	        ]
	    })
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/,
		name : 'destOrgName',
		readOnly : true
	},{
		name : 'destOrgCode',
		hidden : true,
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.leaveTime')/*'出发日期'*/,
		name : 'leaveTime',
		xtype : 'datefield',
		format : 'Y-m-d',
		readOnly : true,
		editable : false
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleAssembleNo')/*'配载车次号'*/,
		name : 'vehicleAssembleNo',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/,
		xtype : 'commontruckselector',
		name : 'vehicleNo',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		isOwnTruck:'Y',
		vehicleTypes:'vehicletype_rqsvc,vehicletype_tractors,vehicletype_van',
		allowBlank : false,
		listeners : {//监听车牌号控件失去焦点事件
			'blur' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
				var destOrgCmp = basicInfoForm.findField('destOrgCode');
				var leaveTimeCmp = basicInfoForm.findField('leaveTime');
				var assembleTypeCmp = basicInfoForm.findField('assembleType');
				//获取车牌号
				var vehicleNo = cmp.getValue();
				if(!Ext.isEmpty(vehicleNo)){
					var store = cmp.store;
					var vehicleRecord = store.findRecord('vehicleNo',vehicleNo,0,false,true,true);
					if(vehicleRecord == null){
						cmp.setValue('');//清空车牌号
						cmp.focus(false,100);//获取输入焦点
						return;

					}
					var assembleType = assembleTypeCmp.getValue();
					load.vehicleassemblebillmodify.truckType = vehicleRecord.get('truckType');
					if(vehicleRecord != null){
						if(assembleType == 'CAR_LOAD'
							&& vehicleRecord.get('truckType') != '外请车'){
							Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '“整车”配载时只能选择外请车！请重新输入!', 'error', 3000);
							cmp.setValue('');//清空车牌号
							cmp.focus(false,100);//获取输入焦点
							return;
						}				
					}
					
					Ext.Ajax.request({
						url : load.realPath('queryVehicleInfoByVehicleNo.action'),
						jsonData : {
							'vehicleAssembleBillVo' : {
								'vehicleNo' : vehicleNo,
								'leaveTime' : leaveTimeCmp.getValue(),
								'destDeptCode' : destOrgCmp.getValue(),
								'assembleType' : assembleTypeCmp.getValue()
							}
						},
						success : function(response){
							var result = Ext.decode(response.responseText);
							var vehicleInfo = result.vehicleAssembleBillVo.vehicleInfo;
							if(vehicleInfo == null){
								Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '车牌号不存在，请重新输入!', 'error', 3000);
								cmp.setValue('');//清空车牌号
								cmp.focus(false,100);//获取输入焦点
								return;
							}else{
								if(load.vehicleassemblebillmodify.oldVehicleNo != cmp.getValue()){
									//获取交接单grid中新添加的交接单
									var store = load.vehicleassemblebillmodify.mainPageGrid.store;
									var newHandOverBillList = new Array();
									store.each(function(record){
										if(record.get('isAddNew') == 'Y'){
											newHandOverBillList.push(record);
										}
									});
									if(newHandOverBillList.length > 0){
										Ext.MessageBox.confirm(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
										'确定要更改车牌号吗？</br>这将导致新添加的交接单被移除！</br>保存后将更新本配载单中所有交接单的车牌号为此处修改的车牌号！',function(btn) {
											if (btn == 'yes') {
												store.remove(newHandOverBillList);
												load.vehicleassemblebillmodify.oldVehicleNo = cmp.getValue();
											} else {
												cmp.setValue(load.vehicleassemblebillmodify.oldVehicleNo);
												return;
											}
										});
									}
								}
								load.vehicleassemblebillmodify.oldVehicleNo = cmp.getValue();
								//填充司机、司机电话、班次
								var driverCode = result.vehicleAssembleBillVo.driverCode;
								var driverTel = result.vehicleAssembleBillVo.driverTel;
								var driverName = result.vehicleAssembleBillVo.driverName;
								var frequencyNo = result.vehicleAssembleBillVo.frequencyNo;
								basicInfoForm.findField('driverMobilePhone').setValue(driverTel);
								basicInfoForm.findField('frequencyNo').setValue(frequencyNo);
								basicInfoForm.findField('driverName').setValue(driverName);
								//车牌号对应发车计划中的挂牌号
								load.vehicleassemblebillmodify.trailerVehicleNo=result.vehicleAssembleBillVo.trailerVehicleNo;
								//填充货柜号
								var containerNo =  result.vehicleAssembleBillVo.containerNo;
								var containerNoCmp = basicInfoForm.findField('containerNo');
								containerNoCmp.setValue(containerNo);
								containerNoCmp.fireEvent('blur',containerNoCmp);
								
								//更改原先记录中的车牌号
								load.vehicleassemblebillmodify.basicInfoRecord.set('vehicleNo',cmp.getValue());
								console.log(vehicleInfo);
								//填充车辆所有权类别
								basicInfoForm.findField('vehicleOwnerShip').setValue(vehicleInfo.vehicleOwnershipType);
								//车辆载重
								load.vehicleassemblebillmodify.tempVehicleLoadWeight = vehicleInfo.vehicleDeadLoad;
								basicInfoForm.findField('ratedLoad').setValue(vehicleInfo.vehicleDeadLoad);
								//如果是公司车，则显示货柜、隐藏车型、清空车辆信息
								if(vehicleInfo.vehicleOwnershipType == 'Company'){
									//公司车则影藏适用载重和适用净空 司机自带货量
									basicInfoForm.findField('driverOfWeight').setVisible(false);
									basicInfoForm.findField('driverOfVolumn').setVisible(false);
									basicInfoForm.findField('applicationRatedLoad').setVisible(false);
									basicInfoForm.findField('applicationRatedClearance').setVisible(false);
									basicInfoForm.findField('beCarSmallUsed').setVisible(false);
									Ext.getCmp('Foss_load_vehicleassemblebillmodify_m³').setVisible(false);
									//判断公司车是车头还是厢式车
									var vehicleType = vehicleInfo.vehicleType;
									//如果为车头
									if(vehicleType == 'vehicletype_tractors'){
										basicInfoForm.findField('vehicleModel').setVisible(false);
										basicInfoForm.findField('vehicleModel').setValue(null);
										basicInfoForm.findField('vehicleModel').setDisabled(true);
										basicInfoForm.findField('containerNo').setVisible(true);
										basicInfoForm.findField('containerNo').setReadOnly(false);
										basicInfoForm.findField('containerNo').setDisabled(false);
										if(Ext.isEmpty(basicInfoForm.findField('containerNo').getValue())){
											basicInfoForm.findField('vehicleMessage').setValue('');//清空“车辆信息”，此时车辆信息应读取货柜属性
										}
									}else{//如果为厢式车或挂车
										basicInfoForm.findField('vehicleModel').setVisible(true);
										basicInfoForm.findField('vehicleModel').setDisabled(false);
										basicInfoForm.findField('containerNo').setVisible(false);
										basicInfoForm.findField('containerNo').setDisabled(true);
										basicInfoForm.findField('containerNo').setReadOnly(true);
										basicInfoForm.findField('containerNo').reset();
										
										//车辆净空
										load.vehicleassemblebillmodify.tempVehicleLoadVolume = vehicleInfo.vehicleSelfVolume;
										basicInfoForm.findField('ratedClearance').setValue(vehicleInfo.vehicleSelfVolume);
										//读取车型,填充
										basicInfoForm.findField('vehicleModel').setValue(vehicleInfo.vehicleLengthName);
										//填充车辆信息
										var vehicleMessage = load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
											+ vehicleInfo.vehicleDeadLoad 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
											+'，' 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
											+ vehicleInfo.vehicleSelfVolume 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
											+'，' 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
											+ vehicleInfo.vehicleHeight/100 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
											+ '，' 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
											+ vehicleInfo.vehicleWidth/100 
											+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
										basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
									}
									
									//填充总运费
									var vehicleFee = result.vehicleAssembleBillVo.vehicleFee;
									
									//限制付款方式为月结，不可更改
									basicInfoForm.findField('paymentType').setValue('CT');
									basicInfoForm.findField('paymentType').setReadOnly(true);
									
									if((!basicInfoForm.findField('beMidWayLoad').getValue()&& !basicInfoForm.findField('beInLTLCar').getValue())
												|| (basicInfoForm.findField('beMidWayLoad').getValue() && basicInfoForm.findField('beFinallyArrive').getValue())){
										load.vehicleassemblebillmodify.tempTotalFee = vehicleFee;
										basicInfoForm.findField('feeTotal').setValue(vehicleFee);	
										basicInfoForm.findField('feeTotal').setReadOnly(true);	
										basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
										basicInfoForm.findField('prePaidFeeTotal').reset();
										basicInfoForm.findField('arriveFeeTotal').setReadOnly(true);
										basicInfoForm.findField('arriveFeeTotal').reset();
									}else{
										load.vehicleassemblebillmodify.tempTotalFee = vehicleFee;
										basicInfoForm.findField('feeTotal').setValue(0);	
										basicInfoForm.findField('feeTotal').setReadOnly(true);	
										basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
										basicInfoForm.findField('prePaidFeeTotal').reset();
										basicInfoForm.findField('arriveFeeTotal').setReadOnly(true);
										basicInfoForm.findField('arriveFeeTotal').reset();
									}
									//如果是外请车，则显示车型，隐藏货柜，读取车辆长宽载重信息、填充各控件
								}else if(vehicleInfo.vehicleOwnershipType == 'Leased'){
									basicInfoForm.findField('vehicleModel').setVisible(true);
									basicInfoForm.findField('vehicleModel').setDisabled(false);
									basicInfoForm.findField('containerNo').setVisible(false);
									basicInfoForm.findField('containerNo').setReadOnly(true);
									basicInfoForm.findField('containerNo').setDisabled(true);
									basicInfoForm.findField('containerNo').setValue(null);
									//外请车则显示适用载重和适用净空 司机自带货量
									basicInfoForm.findField('driverOfWeight').setVisible(true);
									basicInfoForm.findField('driverOfVolumn').setVisible(true);
									basicInfoForm.findField('applicationRatedLoad').setVisible(true);
									basicInfoForm.findField('applicationRatedClearance').setVisible(true);
									basicInfoForm.findField('beCarSmallUsed').setVisible(true);
									Ext.getCmp('Foss_load_vehicleassemblebillmodify_m³').setVisible(true);
									//根据是否合同车来显示付款方式
									if(vehicleInfo.bargainVehicle == 'Y'){//合同车可以选择现金或者月结、电汇
										basicInfoForm.findField('paymentType').setReadOnly(false);
									}else{//非合同车只能是现金
										basicInfoForm.findField('paymentType').setReadOnly(true);
										basicInfoForm.findField('paymentType').setValue('CH');
									}
									//车辆净空
									load.vehicleassemblebillmodify.tempVehicleLoadVolume = vehicleInfo.vehicleSelfVolume;
									//额定载重、额定净空读取车辆载重、车辆净空
									basicInfoForm.findField('ratedClearance').setValue(vehicleInfo.vehicleSelfVolume);
									//读取车型,填充
									basicInfoForm.findField('vehicleModel').setValue(vehicleInfo.vehicleLengthName);
									//填充车辆信息
									var vehicleMessage = load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
										+ vehicleInfo.vehicleDeadLoad 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
										+ '，' 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
										+ vehicleInfo.vehicleSelfVolume 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
										+ '，' 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
										+ vehicleInfo.vehicleHeight 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
										+ '，' 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
										+ vehicleInfo.vehicleWidth 
										+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
									basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
									//填充总运费
									var vehicleFee = result.vehicleAssembleBillVo.vehicleFee;
									load.vehicleassemblebillmodify.tempTotalFee = vehicleFee;
									var inviteNo = result.vehicleAssembleBillVo.inviteNo;
									basicInfoForm.findField('inviteNo').setValue(inviteNo);
									//如果为非月结付款方式，则将总运费填充
									if(basicInfoForm.findField('paymentType').getValue() == 'CH'
										|| basicInfoForm.findField('paymentType').getValue() == 'TT'){
										if((!basicInfoForm.findField('beMidWayLoad').getValue()&& !basicInfoForm.findField('beInLTLCar').getValue())
												|| (basicInfoForm.findField('beMidWayLoad').getValue() && basicInfoForm.findField('beFinallyArrive').getValue())){
										basicInfoForm.findField('feeTotal').setValue(vehicleFee);	
										// 预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*										var prePaidFeeTotal = Math.floor(vehicleFee/300)*100;
										basicInfoForm.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
										
										// 276198--duh--2015--09--06
										//如果是整车外请车则预付运费总额默认金额小于等于总运费2/3
										//专线外清车预付运费默认金额小于等于总运费的1/3
										
									
										if(basicInfoForm.findField('assembleType').getValue() == 'CAR_LOAD'){
											
											//var prePaidFeeTotal = Math.floor(vehicleFee/300)*200;
											var prePaidFeeTotal = Math.floor(vehicleFee*2/300)*100;
											basicInfoForm.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
											
										}
										else{
											
											var prePaidFeeTotal = Math.floor(vehicleFee/300)*100;
											basicInfoForm.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);	
											
										}
										
										//个人认为修改界面的默认值就应该是修改之前配载单传入的值不需要计算规则
										
										
										
										//到付运费总额
										basicInfoForm.findField('arriveFeeTotal').setValue(vehicleFee - prePaidFeeTotal);
										}else{
											basicInfoForm.findField('feeTotal').setValue(0);
											basicInfoForm.findField('prePaidFeeTotal').setValue(0);
											basicInfoForm.findField('arriveFeeTotal').setValue(0);
											basicInfoForm.findField('feeTotal').setReadOnly(true);
											basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
											basicInfoForm.findField('arriveFeeTotal').setReadOnly(true);
										}
									}
								}
							}
						},
						exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.queryVehicleInfoFailureAlertPrefix')/*'读取车辆信息失败，'*/ + result.message + '请重新选择车辆！');
			    				cmp.setValue(null);
			    				//初始化挂牌号字段
			    				load.vehicleassemblebillmodify.trailerVehicleNo=null;
			    				cmp.focus();
			    		}
					});
				
				}else{
					//初始化挂牌号字段
    				load.vehicleassemblebillmodify.trailerVehicleNo=null;
				}
			}
		}
	},  {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverName')/*'主驾驶姓名'*/,
		name : 'driverName',
		allowBlank : false,
		readOnly : true,
		xtype : 'commondriverselector',
		listeners : {
			'blur' : function(cmp,eo,eOpts){
				basicInfoForm = this.up('form').getForm();
				if(cmp.isValid()){
					var store = cmp.store,
					record = store.findRecord('empCode',cmp.getValue(),0,false,true,true);
					if(record == null){
						return;
					}
					//如果为外请车, 公司司机, 则给出提示.
					if(load.vehicleassemblebillmodify.truckType == '外请车') {
						if(record.get('driverType') == '公司司机') {
							Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '外请车必须用外请司机!', 'error', 3000);
							return;
						}
					} else if (load.vehicleassemblebillmodify.truckType == '公司车') {
						if(record.get('driverType') == '外请司机') {
							Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '公司车必须用公司司机!', 'error', 3000);
							return;
						}
					}
					cmp.setValue(record.get('empName'));
					basicInfoForm.findField('driverCode').setValue(record.get('empCode'));
					basicInfoForm.findField('driverMobilePhone').setValue(record.get('empPhone'));
				}else{
					basicInfoForm.findField('driverCode').reset();
				}
			}
		}
	}, {
		name : 'driverCode',
		hidden : true
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverMobilePhone')/*'司机电话'*/,
		name : 'driverMobilePhone',
		maxLength:13,
		allowBlank : false
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleModel')/*'车型'*/,
		name : 'vehicleModel',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.containerNo')/*'货柜编号'*/,
		xtype : 'commonowntruckselector',
		name : 'containerNo',
		displayField : 'containerCode',
		valueField : 'containerCode',
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		allowBlank : false,
		listeners : {
			'blur' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
				//获取输入的货柜号
				var containerNo = cmp.getValue();
				if(!Ext.isEmpty(containerNo)){
					Ext.Ajax.request({
						url : load.realPath('queryContainerInfoByContainerNo.action'),
						jsonData : {
							'vehicleAssembleBillVo' : {
								'containerNo' : containerNo
							}
						},
						success : function(response){
							var result = Ext.decode(response.responseText);
							var containerInfo = result.vehicleAssembleBillVo.containerInfo;
							console.log(containerInfo);
							if(null == containerInfo){
								Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '货柜编号不存在，请重新输入!', 'error', 3000);
								cmp.setValue(null);//清空车牌号
								cmp.focus(false,100);//获取输入焦点
							}else{
								load.vehicleassemblebillmodify.basicInfoRecord.set('containerNo',cmp.getValue());
								//额定载重、额定净空读取车辆载重、车辆净空
								basicInfoForm.findField('ratedClearance').setValue(containerInfo.selfVolume);
								//填充车辆信息
								var vehicleMessage = load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
									+ containerInfo.deadLoad 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
									+ '，' 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
									+ containerInfo.selfVolume 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
									+ '，' 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
									+ containerInfo.vehicleHeight/100 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
									+ '，' 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
									+ containerInfo.vehicleWidth/100 
									+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
								basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
								//车辆净空
								load.vehicleassemblebillmodify.tempVehicleLoadVolume = containerInfo.selfVolume;
							}
						}
					});
				}
			}
		}
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.frequencyNo')/*'卡车班次'*/,
		name : 'frequencyNo',
		readOnly : true,//200968
		allowBlank : false
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.transProperty')/*'运输性质'*/,
		name : 'transProperty',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    allowBlank : false,
	    editable : false,
		store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"TRUCK_LIKE_PLAN", "value":"精准卡航"},
	            {"key":"TRUCK_LIKE_PCP", "value":"精准包裹"},
	            {"key":"LONG_DISTANCE", "value":"精准汽运(长)"}
	        ]
	    }),
	    listeners : {
	    	'blur' : function(field){
	    		var form = this.up('form').getForm();
	    		//当“配载类型”不为整车时，，访问后台，获取“运行时数”
	    		if(load.vehicleassemblebillmodify.oldBasicInfoRecord.get('assembleType') != 'CAR_LOAD'){
	    			var vehicleAssembleNo = form.findField('vehicleAssembleNo').getValue();
	    			var vehicleNo = form.findField('vehicleNo').getValue();
		    		if(!Ext.isEmpty(vehicleAssembleNo) && !Ext.isEmpty(vehicleNo)){
		    			Ext.Ajax.request({
							url : load.realPath('queryModifyRunHoursByTransProperty.action'),
							jsonData : {
								'vehicleAssembleBillVo' : {
									'vehicleNo' : vehicleNo,
									'vehicleAssembleNo' : vehicleAssembleNo,
									'transProperty' : field.getValue()
								}
							},
							success : function(response){
								var result = Ext.decode(response.responseText);
								var runHours = result.vehicleAssembleBillVo.runHours;
								if(!Ext.isEmpty(runHours)){
									form.findField('runHours').setValue(runHours);
								}
							},
							exception : function(response){
								form.findField('vehicleNo').setValue(null);
								var result = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,result.message);
							}
						});
		    		}
	    		}
	    	}
	    }
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedLoad')/*'额定载重'*/,
		name : 'ratedLoad',
		allowBlank : false,
		readOnly : true,
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				var driverOfWeight=form.findField('driverOfWeight').getValue();
				
				if(driverOfWeight==null){
					driverOfWeight=0;
				}
				if(newValue==null){
					form.findField('applicationRatedLoad').setValue(0);
				}else{
					form.findField('applicationRatedLoad').setValue(newValue*1000-driverOfWeight);
				}
			}
		}
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedClearance')/*'额定净空'*/,
		name : 'ratedClearance',
		allowBlank : false,
		readOnly : true,
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				form.findField('examineVolume').setValue(newValue);
				var driverOfVolumn =form.findField('driverOfVolumn').getValue();
				if(driverOfVolumn==null){
					driverOfVolumn=0;
				}
				if(Ext.isEmpty(newValue)){
					form.findField('applicationRatedClearance').setValue(0);
				}else{
					form.findField('applicationRatedClearance').setValue(newValue-driverOfVolumn);
				}
			}
		}
	},{
		fieldLabel :load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverOfGoods')/*'司机自带货量'*/,
		xtype : 'numberfield',
		value:0,
		maxValue:9999999.99,
		minValue:0,
		margin:'5 0 5 10',
		columnWidth:.25,
		name : 'driverOfWeight',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				var ratedLoad=form.findField('ratedLoad').getValue();
				if(newValue!=null){
					if(ratedLoad==null){
						form.findField('applicationRatedLoad').setValue(0);
					}else{
						form.findField('applicationRatedLoad').setValue(ratedLoad*1000-newValue);
					}
					
					
				}
			}
		}
	},{
		fieldLabel :'kg',
		xtype : 'numberfield',
		labelSeparator : '',
		value:0,
		maxValue:9999999.99,
		margin:'5 0 5 0',
		columnWidth:.15,
		labelWidth : 20,
		minValue:0,
		name : 'driverOfVolumn',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				var ratedClearance =form.findField('ratedClearance').getValue();
				
				if(newValue!=null){
					if(ratedClearance==null){
						form.findField('applicationRatedClearance').setValue(0);
					}else{
						form.findField('applicationRatedClearance').setValue(ratedClearance-newValue);
					}
				}
				
			}
		}
	},{
		xtype: 'container',
		columnWidth:.05,
		id:'Foss_load_vehicleassemblebillmodify_m³',
		margin:'10 0 5 0',
		html: 'm³'
	},{
		fieldLabel :load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedLoad')/*'适用载重'*/,
		xtype : 'numberfield',
		labelWidth : 60,
		allowBlank : false,
		readOnly : true,
		minValue:0,
		margin:'5 10 5 60',
		name : 'applicationRatedLoad'
		
	},{
		fieldLabel :load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedClearance')/*'适用净空'*/,
		xtype : 'numberfield',
		labelWidth : 60,
		allowBlank : false,
		readOnly : true,
		margin:'5 10 5 60',
		minValue:0,
		name : 'applicationRatedClearance'
		
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.examineVolume')/*'考核体积'*/,
		name : 'examineVolume',
		readOnly : true,
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				if(!load.vehicleassemblebillmodify.isNumber(newValue)){
					form.findField('loadingRate').setValue(0.00);
					return;
				}
				//获取总体积
				var totalVolume = Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').getValue();
				//计算装载率
				var loadingRate = totalVolume/newValue;
				if(!load.vehicleassemblebillmodify.isNumber(loadingRate)){
					form.findField('loadingRate').setValue(0.00);
				}else{
					form.findField('loadingRate').setValue(loadingRate.toFixed(2));
				}
			}
		}
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadingRate')/*'装载率'*/,
		name : 'loadingRate',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleOwnerShip')/*'车辆所有权类别'*/,
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
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.runHours')/*'运行时数(小时)'*/,
		name : 'runHours',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.createUser')/*'制单人'*/,
		name : 'createUser',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.modifyUser')/*'修改人'*/,
		name : 'modifyUser',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleMessage')/*'车辆信息'*/,
		name : 'vehicleMessage',
		fieldStyle : 'font-weight:bold',
		columnWidth : .5,
		readOnly : true
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/,
		name : 'note',
		columnWidth : .5,
		margin : '5 10 0 10',
		maxLength : 300
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.goodsType')/*'货物类型'*/,	//此控件是否可编辑需要根据到达部门是否自动分拣来判断
		name : 'goodsType',
		xtype : 'combobox',
		margin : '5 10 0 10',
		queryMode : 'local',
	    displayField : 'value',
	    valueField : 'key',
	    readOnly : true,
	    editable : false,
	    allowBlank : false,
		store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"A_TYPE", "value":"A类"},
	            {"key":"B_TYPE", "value":"B类"}
	        ]
	    })
	}, {
		boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayLoad')/*'是否在途装卸'*/,
		name : 'beMidWayLoad',
		margin : '5 10 0 10',
		columnWidth : .12,
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = load.vehicleassemblebillmodify.basicInfoForm.getForm();
				var totalFee = load.vehicleassemblebillmodify.tempTotalFee;
				var vehicleNo = form.findField('vehicleNo').getValue();
				if(newValue){//如果勾选在途装卸，则显示“是否最终到达”，并且“额定载重”“额定净空”可修改
					form.findField('midWayLoadType').setVisible(true);
					form.findField('beFinallyArrive').setVisible(true);
					
					form.findField('beInLTLCar').setValue(false);
					form.findField('beMidWayOnlyLoad').setVisible(true);
					
					form.findField('ratedLoad').setReadOnly(false);
					form.findField('ratedClearance').setReadOnly(false);
					if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
						form.findField('feeTotal').setValue(0);
						form.findField('prePaidFeeTotal').setValue(0);
						form.findField('arriveFeeTotal').setValue(0);
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
					}else{
						form.findField('feeTotal').setValue(0);
						form.findField('prePaidFeeTotal').reset();
						form.findField('arriveFeeTotal').reset();
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
					}
					if(form.findField('beTimeLiness').getValue() && !form.findField('beFinallyArrive').getValue() ){
						Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
												"签署了时效条款不能选择在途卸且非最终到达!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
												'error', 
												2000
												);
						return form.findField('beMidWayLoad').setValue(false);
					}
				}else{
			//		top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '请重新选择一次车牌号!');
					
					form.findField('midWayLoadType').setVisible(false);
					//form.findField('midWayLoadType').setValue();
					form.findField('beFinallyArrive').setVisible(false);
					form.findField('beFinallyArrive').setValue(false);
					
					form.findField('beMidWayOnlyLoad').setVisible(false);
					form.findField('beMidWayOnlyLoad').setValue(false);
					
					form.findField('ratedLoad').setReadOnly(true);
					form.findField('ratedClearance').setReadOnly(true);
					//获取车辆所有权的值
					if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
						//解决 如果是外请车生成的配载单总运费是0的 ，修改时带不出约车总运费
						if(vehicleNo == load.vehicleassemblebillmodify.staticVehicleNo ){
							if(load.vehicleassemblebillmodify.staticTotalFee==0){
								totalFee= load.vehicleassemblebillmodify.inviteFeetotal;
							}
						}
						
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							//总运费
							form.findField('feeTotal').setValue(totalFee);
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.floor(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//如果是整车外请车则预付运费总额默认金额小于等于总运费2/3
							//专线外清车预付运费默认金额小于等于总运费的1/3
							
							
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								
								
								//var prePaidFeeTotal = Math.floor(totalFee/300)*200;
								var prePaidFeeTotal = Math.floor(totalFee*2/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
								
							}
							else{
								
								var prePaidFeeTotal = Math.floor(totalFee/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);	
								
							}
							
							//到付运费总额
							form.findField('arriveFeeTotal').setValue(totalFee - prePaidFeeTotal);
							form.findField('prePaidFeeTotal').setReadOnly(false);
						}
					}else{
						form.findField('feeTotal').setValue(totalFee);
						form.findField('prePaidFeeTotal').reset();
						form.findField('arriveFeeTotal').reset();
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
					}
				}
				//重新设置载重、净空
				form.findField('ratedLoad').setValue(load.vehicleassemblebillmodify.tempVehicleLoadWeight);
				form.findField('ratedClearance').setValue(load.vehicleassemblebillmodify.tempVehicleLoadVolume);
			}
		}
	}, {
		boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beFinallyArrive')/*'是否最终到达'*/,
		name : 'beFinallyArrive',
		margin : '5 10 0 10',
		hidden : true,
		columnWidth : .13,
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				var totalFee = load.vehicleassemblebillmodify.tempTotalFee;
				var vehicleNo= form.findField('vehicleNo').getValue();
				//如果为现金付款
				if(newValue){
					//如果为公司车
					if(form.findField('vehicleOwnerShip').getValue() == 'Company'){
						//总运费
						form.findField('feeTotal').setValue(totalFee);
						form.findField('feeTotal').setReadOnly(true);
						//预付运费总额
						form.findField('prePaidFeeTotal').reset();
						//到付运费总额
						form.findField('prePaidFeeTotal').reset();
					}else{//外请车
						
						//解决 如果是外请车生成的配载单总运费是0的 ，修改时带不出约车总运费
						if(vehicleNo == load.vehicleassemblebillmodify.staticVehicleNo ){
							if(load.vehicleassemblebillmodify.staticTotalFee==0){
								totalFee= load.vehicleassemblebillmodify.inviteFeetotal;
							}
						}
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							//总运费
							form.findField('feeTotal').setValue(totalFee);
							
							form.findField('feeTotal').setReadOnly(true);
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.floor(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//如果是整车外请车则预付运费总额默认金额小于等于总运费2/3
							//专线外清车预付运费默认金额小于等于总运费的1/3
							
							
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								
								//var prePaidFeeTotal = Math.floor(totalFee/300)*200;
								var prePaidFeeTotal = Math.floor(totalFee*2/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
								
							}
							else{
								
								var prePaidFeeTotal = Math.floor(totalFee/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);	
								
							}
							
							//到付运费总额
							form.findField('arriveFeeTotal').setValue(totalFee - prePaidFeeTotal);
							form.findField('prePaidFeeTotal').setReadOnly(false);	
						}
					}
				}else{
					//
					form.findField('beMidWayOnlyLoad').setValue(false);
					//如果为公司车
					if(form.findField('vehicleOwnerShip').getValue() == 'Company'){
						//总运费
						form.findField('feeTotal').setValue(0);
						form.findField('feeTotal').setReadOnly(true);
						//预付运费总额
						form.findField('prePaidFeeTotal').reset();
						//到付运费总额
						form.findField('prePaidFeeTotal').reset();
					}else{//外请车
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							form.findField('feeTotal').setValue(0);
							form.findField('prePaidFeeTotal').setValue(0);
							form.findField('arriveFeeTotal').setValue(0);
							form.findField('feeTotal').setReadOnly(true);
							form.findField('prePaidFeeTotal').setReadOnly(true);
							form.findField('arriveFeeTotal').setReadOnly(true);
						}
					}
					if(form.findField('beTimeLiness').getValue() && form.findField('beMidWayLoad').getValue() ){
						Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
												"签署了时效条款不能选择在途卸且非最终到达!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
												'error', 
												2000
												);
						return form.findField('beFinallyArrive').setValue(true);
					}
				}
			}
		}
	},{
		xtype : 'container',
		columnWidth : 1
	},{
		boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beInLTLCar')/*'是否与零担合车'*/,
		name : 'beInLTLCar',
		margin : '5 10 0 10',
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				var vehicleNo= form.findField('vehicleNo').getValue();
				//如果选择与零担合车则总运费为0
				if(newValue){
					form.findField('beMidWayLoad').setValue(false);
					
					form.findField('beTimeLiness').setValue(false);
					
					form.findField('feeTotal').setValue(0);
					form.findField('prePaidFeeTotal').setValue(0);
					form.findField('arriveFeeTotal').setValue(0);
					form.findField('feeTotal').setReadOnly(true);
					form.findField('prePaidFeeTotal').setReadOnly(true);
					form.findField('arriveFeeTotal').setReadOnly(true);
				}else{
					var totalFee = load.vehicleassemblebillmodify.tempTotalFee;
					//获取车辆所有权的值
					if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
						//解决 如果是外请车生成的配载单总运费是0的 ，修改时带不出约车总运费
						if(vehicleNo == load.vehicleassemblebillmodify.staticVehicleNo ){
							if(load.vehicleassemblebillmodify.staticTotalFee==0){
								totalFee= load.vehicleassemblebillmodify.inviteFeetotal;
							}
						}
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							//总运费
							form.findField('feeTotal').setValue(totalFee);	
							//预付运费总额
/*							var prePaidFeeTotal = Math.floor(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//如果是整车外请车则预付运费总额默认金额小于等于总运费2/3
							//专线外清车预付运费默认金额小于等于总运费的1/3
							
							
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								
								//var prePaidFeeTotal = Math.floor(totalFee/300)*200;
								var prePaidFeeTotal = Math.floor(totalFee*2/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
								
							}
							else{
								
								var prePaidFeeTotal = Math.floor(totalFee/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);	
								
							}
							
							//到付运费总额
							form.findField('arriveFeeTotal').setValue(totalFee - prePaidFeeTotal);
							form.findField('prePaidFeeTotal').setReadOnly(false);
						}
					}else{
						form.findField('feeTotal').setValue(totalFee);
						form.findField('prePaidFeeTotal').reset();
						form.findField('arriveFeeTotal').reset();
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
				
					}
				}
			}
		}		
	},{
			boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayOnlyLoad')/*'是否在途只装不卸'*/,
			name : 'beMidWayOnlyLoad',
			xtype : 'checkbox',
			hidden : true,
			listeners : {
				'change' : function(field,newValue,oldValue,eOpts){
					var form = this.up('form').getForm();
					if(newValue){
						//如果选择了在途只装不卸则设置最终到达
						form.findField('beFinallyArrive').setValue(true);
						//如果没有选择则在途到达部门可见
						form.findField('onTheWayDestOrgCode').setVisible(true);
					}else{
						//如果没有选择则在途到达部门不可见
						form.findField('onTheWayDestOrgCode').setVisible(false);
					}
				}
			}
		},{
			fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.onTheWayDestOrgCode')/*'中途到达部门'*/,
			name : 'onTheWayDestOrgCode',
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			transferCenter : 'Y',
			hidden :true,
			listeners :　{
				'change' : function(cmp, eo,eOpts){
					var form = this.up('form').getForm();
					if(form.findField('destOrgCode').getValue()!=null 
							&& form.findField('destOrgCode').getValue()==form.findField('onTheWayDestOrgCode').getValue()){
						top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
								"中途到达部门不能与最终到达部门一致！");
		    			cmp.reset();
		    			cmp.focus();
					}
				}
			}
	},{
		fieldLabel : '在途配载类型'/*load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.assembleType')*/,
		name : 'midWayLoadType',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    hidden : true,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'], // 两地卸“”两地装“”中途带货“ distance unload   distance load  midway pickup
	        data : [
	                {"key":"DISTANCE_UNLOAD", "value":"两地卸"},
		            {"key":"DISTANCE_LOAD", "value":"两地装"},
		            {"key":"MIDWAY_PICKUP", "value":"中途带货"}
	        ]
	    })
	 },{
		boxLabel :load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beCarSmallUsed')/*'是否大车小用'*/,
		name : 'beCarSmallUsed',
		xtype : 'checkbox',
		hidden : true
	},{
		xtype : 'container',
		html : '<hr/>',
		columnWidth : 1
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.paymentType')/*'付款方式'*/,
		name : 'paymentType',
		margin : '0 10 5 10',
		xtype : 'combobox',
		allowBlank : false,
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"CH", "value":"现金"},
	            {"key":"CT", "value":"月结"},
	            {"key":"TT","value":"电汇"}
	        ]
	    }),
	    listeners : {
			'change' : function(field, newValue, oldValue, eOpts) {
				var form = this.up('form').getForm();
				if (form.findField('vehicleOwnerShip').getValue() == 'Leased') {
					// 如果为月结，则将三运费全部置为0，而且不可编辑
					if (newValue == 'CT') {
						form.findField('feeTotal').setValue(load.vehicleassemblebillmodify.tempTotalFee);
						form.findField('prePaidFeeTotal').setValue(0);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setValue(0);
					} else {
						if (!form.findField('beMidWayLoad').getValue()
								|| (form.findField('beMidWayLoad').getValue() && form.findField('beFinallyArrive').getValue())) {
							var totalFee = load.vehicleassemblebillmodify.tempTotalFee;
							// 总运费
							form.findField('feeTotal').setValue(totalFee);
							// 预付运费总额
							//var prePaidFeeTotal = Math.round(totalFee / 300)* 100;
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.floor(totalFee/300)*100;
							form.findField('prePaidFeeTotal')
									.setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//如果是整车外请车则预付运费总额默认金额小于等于总运费2/3
							//专线外清车预付运费默认金额小于等于总运费的1/3
							
							
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								
								//var prePaidFeeTotal = Math.floor(totalFee/300)*200;
								var prePaidFeeTotal = Math.floor(totalFee*2/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
								
							}
							else{
								
								var prePaidFeeTotal = Math.floor(totalFee/300)*100;
								form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);	
								
							}
							
							// 到付运费总额
							form.findField('arriveFeeTotal').setValue(totalFee
									- prePaidFeeTotal);
							form.findField('prePaidFeeTotal')
									.setReadOnly(false);
						} else {
							form.findField('feeTotal').setValue(0);
							form.findField('prePaidFeeTotal').setValue(0);
							form.findField('prePaidFeeTotal').setReadOnly(true);
							form.findField('arriveFeeTotal').setValue(0);
						}
					}
				}
			}
		}
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.feeTotal')/*'总运费'*/,
		margin : '0 10 5 10',
		name : 'feeTotal',
		xtype : 'numberfield',
		readOnly : true
	},{
		name : 'inviteNo',
		hidden : true,
		fieldLabel : '约车编号'
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotal')/*'预付运费总额'*/,
		margin : '0 10 5 10',
		name : 'prePaidFeeTotal',
		xtype : 'numberfield',
		step : 100,
		validator : function(value){
			var form = this.up('form').getForm();
			var assembleType = form.findField('assembleType').getValue();
			//预付运费总额必须大于0
			if(value < 0){
				return load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip1')/*'预付运费总额必须大于0'*/;
			}
			//必须为100的整数倍
/*			if(value%100 != 0){
				return load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip2')'预付运费总额必须为100的整数倍';
			}*/
			//必须小于等于总运费
			// 必须小于等于总运费2/3
			if(assembleType=='CAR_LOAD'&&(value > Math.floor(form.findField('feeTotal').getValue()*2/3))){
				return load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip3')/*'预付运费总额必须小于等于总运费2/3'*/;
			}
			//必须小于等于总运费1/3  duh
			if(assembleType=='OWNER_LINE'&&(value > Math.floor(form.findField('feeTotal').getValue()*1/3))){
				return load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip4')/*'预付运费总额必须小于等于总运费1/3'*/;
			}if(assembleType=='OWNER_LINE'&&(value > form.findField('feeTotal').getValue())){
				return load.vehicleassemblebilladdnew.i18n('预付运费总额必须小于等于总运费')/*'预付运费总额必须小于等于总运费'*/;
			}
			return true;
		},
		listeners : {
			'blur' : function(cmp,eO,eOpts){
				var form = this.up('form').getForm();
				if(cmp.isValid() && form.findField('vehicleOwnerShip').getValue() == 'Leased'
					&& (form.findField('paymentType').getValue() == 'CH'
						|| form.findField('paymentType').getValue() == 'TT')){
					form.findField('arriveFeeTotal').setValue(form.findField('feeTotal').getValue() - cmp.getValue());
				}
			}
		}
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.arriveFeeTotal')/*'到付运费总额'*/,
		margin : '0 10 5 10',
		name : 'arriveFeeTotal',
		readOnly : true,
		xtype : 'numberfield'
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadFeeTotal')/*'装车总金额'*/,
		name : 'loadFeeTotal',
		xtype : 'numberfield',
		readOnly : true
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.collectionFeeTotal')/*'代收货款'*/,
		name : 'collectionFeeTotal',
		xtype : 'numberfield',
		readOnly : true
	}, {
		boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beReturnReceipt')/*'是否押回单'*/,
		name : 'beReturnReceipt',
		xtype : 'checkbox',
		columnWidth : .15
	}, {
		boxLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beTimeLiness')/*'是否签署时效条款'*/,
		name : 'beTimeLiness',
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var selectResultPanel = load.vehicleassemblebillmodify.RewardOrPunishGrid;
				if(newValue){
					var form = this.up('form').getForm();
					var destOrgCode = form.findField('destOrgCode').getValue();
					
					var vehicleType = form.findField('vehicleOwnerShip').getValue();
					var assembleType = form.findField('assembleType').getValue();
					var beFinallyArrive = form.findField('beFinallyArrive').getValue();
					var beMidWayLoad = form.findField('beMidWayLoad').getValue();
					var beInLTLCar = form.findField('beInLTLCar').getValue();
					if(vehicleType=='' || assembleType=='' || vehicleType==null||assembleType==null){
						Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"请先输入配载基本信息!",//'请先输入配载基本信息!', 
											'error', 
											2000
											);
						return field.setValue(false);
						
					}
					if(!((vehicleType=='Leased' && assembleType=='OWNER_LINE')&& ((beFinallyArrive && beMidWayLoad)||(!beFinallyArrive && !beMidWayLoad)))){
						Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"只有专线外请车且非在途的配载才能签署时效条款!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
											'error', 
											2000
											);
						return field.setValue(false);
						
					}
					if(beInLTLCar){
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								"选择了与零担合车不能签署时效条款!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
								'error', 
								2000
								);
						return field.setValue(false);
					}
					//显示奖罚协议
					selectResultPanel.setVisible(true);
				}else{
					selectResultPanel.setVisible(false);
				}
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单列表Model，主页和弹出窗口共用
Ext.define('Foss.load.vehicleassemblebillmodify.HandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'isAddNew',
		type : 'string'
	},{
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
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'bePackage',
		type : 'string'
	}]
});

//定义主页交接单store
Ext.define('Foss.load.vehicleassemblebillmodify.MainPageHandOverBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillmodify.HandOverBillModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillListByVNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vehicleAssembleBillVo.handOverBillList',
			successProperty : 'success'
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
					'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillmodify.vehicleAssembleNo
				}
			});	
		},
		'add' : function(store,records,index,eOpts){
			//增加后重新计算装车总金额
			var totalFeeCmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('loadFeeTotal');
			//增加后重新计算代收货款总额
			var codCmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('collectionFeeTotal');
			//增加后统计值更新
			for(var i in records){
				var record = records[i];
				var handOverBillNo = record.get('handOverBillNo');
				//若修改前的map中没有该交接单，则将该交接单置于addedMap中
				if(load.vehicleassemblebillmodify.oldHandOverBillMap.get(handOverBillNo) == null){
					load.vehicleassemblebillmodify.addedHandOverBillMap.add(handOverBillNo,record);
				}
				//更新装车总金额
				totalFeeCmp.setValue(totalFeeCmp.getValue() + record.get('moneyTotal') - record.get('codAmountTotal'));
				//更新代收货款总额
				codCmp.setValue(codCmp.getValue() + record.get('codAmountTotal'));
				//更新总重量
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').getValue() + record.get('weightTotal'));
				//更新总体积
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').getValue() + record.get('volumeTotal'));
				//更新总件数
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').getValue() + record.get('goodsQtyTotal'));
				//更新总票数
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').getValue() + record.get('waybillQtyTotal'));
			}
		},
		'remove' : function(store,record,index,eOpts){
			var handOverBillNo = record.get('handOverBillNo');
			//如果修改前的map中有该记录，则将交接单号置于deletedMap中
			if(load.vehicleassemblebillmodify.oldHandOverBillMap.get(handOverBillNo) != null){
				load.vehicleassemblebillmodify.deletedHandOverBillMap.add(handOverBillNo,record);
			}
			//增加后重新计算装车总金额
			var totalFeeCmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('loadFeeTotal');
			//更新装车总金额
			totalFeeCmp.setValue(totalFeeCmp.getValue() - record.get('moneyTotal') + record.get('codAmountTotal'));
			//增加后重新计算代收货款总额
			var codCmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('collectionFeeTotal');
			//更新代收货款总额
			codCmp.setValue(codCmp.getValue() - record.get('codAmountTotal'));
			//更新总重量
			Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').getValue() - record.get('weightTotal'));
			//更新总体积
			Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').getValue() - record.get('volumeTotal'));
			//更新总件数
			Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').getValue() - record.get('goodsQtyTotal'));
			//更新总票数
			Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').getValue() - record.get('waybillQtyTotal'));
		}
	}
});

//定义打印模版window
Ext.define('Foss.load.vehicleassemblebillquery.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	vehicleAssembleNos : null,
	grid : null,
	items : [{
		fieldLabel : '打印模版',
		name : 'printTemplate',
		columnWidth: 1,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    defaults: {
			margin: '10 5 10 5'
		},
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
                {"key":"配载单", "value":"配载单打印"},
	            {"key":"配载单(流水)", "value":"配载单(流水号)打印"},
	            {"key":"交接单", "value":"交接单打印"},
	            {"key":"交接单(流水)", "value":"交接单(流水号)打印"},
	            {"key":"外发清单", "value":"外发清单打印"},
	            {"key":"外发清单(流水)", "value":"外发清单(流水号)打印"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.printButton')/*'打印'*/,
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				vehicleAssembleNos = upwindow.vehicleAssembleNos;
			var currentDeptCode = FossUserContext.getCurrentDept().code;
			var currentDeptName = FossUserContext.getCurrentDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			
			if(printTemplate == '配载单') {
				do_printpreview('vehicleassemblebill',{
					"vehicleAssembleNos": vehicleAssembleNos, 
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
				return;
			} else if(printTemplate == '配载单(流水)') {
				do_printpreview('vehicleassemblebillsn',{
					"vehicleAssembleNos": vehicleAssembleNos, 
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
				return;
			} 
			Ext.Ajax.request({
				url : load.realPath('queryHandOverBillNosByVehicleAssembleNo.action'),
				params : {'vehicleAssembleBillVo.vehicleAssembleNos' : vehicleAssembleNos},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						handOverBillNos = result.vehicleAssembleBillVo.handOverBillNos;
					
					if (printTemplate == '交接单') {
						do_printpreview('load',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '交接单(流水)') {
						do_printpreview('loadsn',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '外发清单') {
						do_printpreview('partialline',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '外发清单(流水)') {
						do_printpreview('partiallinesn',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					}
				}
			});
		}
	}]
});

//定义主页交接单列表
Ext.define('Foss.load.vehicleassemblebillmodify.MainPageHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.gridTitle')/*'交接单列表'*/,
//	bodyCls : 'autoHeight',
	height : 350,
	enableColumnHide : false,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.vehicleassemblebillmodify.MainPageHandOverBillStore'),
	tbar : [{
		xtype : 'button',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.addBillButton')/*'添加交接单'*/,
		handler : function() {
			load.vehicleassemblebillmodify.queryWindow.show();
			var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
			var queryForm = load.vehicleassemblebillmodify.queryForm.getForm();
			var vehicleNo = basicInfoForm.findField('vehicleNo').getValue();
			queryForm.findField('vehicleNo').setValue(vehicleNo);
		}
	},'->',{
		xtype : 'button',
		disabled: !load.vehicleassemblebillmodify.isPermission('load/printvehicleassemblebillButton'),
		hidden: !load.vehicleassemblebillmodify.isPermission('load/printvehicleassemblebillButton'),
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.printButton')/*'打印'*/,
		handler : function() {
			var records = this.up('grid').getStore().getRange();
			if(records.length == 0){
				Ext.MessageBox.show({
					title:"提示",
					msg:"请先选择您要操作的行!"
				});
				return;
			}
			//如果选择的配载单属于多个不同的车牌则不能打印
			var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm(),
				vehicleAssembleNo = basicInfoForm.findField('vehicleAssembleNo').getValue();
			var vehicleNo = records[0].get('vehicleNo'),
				isdiff = false,
				vehicleAssembleNos = new Array();
			vehicleAssembleNos.push(vehicleAssembleNo);
			
			//如果选择的配载单的车牌号下还有其他的配载单则提示还有其他配载单,请注意
			Ext.Ajax.request({
				url : load.realPath('checkPrintVehicleAssembleBill.action'),
				params : {'vehicleAssembleBillVo.vehicleAssembleNos' : vehicleAssembleNos},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						count = result.vehicleAssembleBillVo.checkPrintVehicleAssembleBillRestlt;
					if(count > 0) {
						//大于0则说明该车牌号下还有其他配载单尚未选择
						Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								"此车牌中还有" + count + "个配载单没有选择打印，请注意!", 
								'error');
					} else {
						
					}
				}
			});
			var vehicleAssembleNo = records[0].get('vehicleAssembleNo');
			Ext.create('Foss.load.vehicleassemblebillquery.PrintWindow', {
				vehicleAssembleNos : vehicleAssembleNos,
				grid : this.up('grid')
			}).show();
		}
	},{
		xtype : 'button',
		disabled: !load.vehicleassemblebillmodify.isPermission('load/printTransportProtocolButton'),
		hidden: !load.vehicleassemblebillmodify.isPermission('load/printTransportProtocolButton'),
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.printBarginBillButton')/*'打印运输合同'*/,
		handler : function(){
			var records = this.up('grid').getStore().getRange();
			if(records.length == 0){
				Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						"没有交接单数据, 请确认!", 
						'error');
				return;
			}
			
			//1:必须是外请车
			//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
			//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
			var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm(),
				vehicleAssembleNo = basicInfoForm.findField('vehicleAssembleNo').getValue();
			if(basicInfoForm.findField('vehicleOwnerShip').getValue() != 'Leased') {
				Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非外请车, 不能打印!', 'error');
				return;
			}
			
			//判断该单据是否打印过运输合同
			Ext.Ajax.request({
				url : load.realPath('checkCarriageContractPrinted.action'),
				params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
				success : function(response){
					var result = Ext.decode(response.responseText),
						checkCarriageContractPrinted = result.vehicleAssembleBillVo.checkCarriageContractPrinted;
					//说明该单据已经被打印过, 询问是否继续打印
					if(checkCarriageContractPrinted == 1) {
						var msg = load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.checkCarriageContractPrinted.msg');
	    				Ext.Msg.confirm(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title'), msg, function(optional){
	    					if(optional == 'yes'){
	    						//后台读取配载单基本信息
	    						Ext.Ajax.request({
	    							url : load.realPath('queryVehicleAssembleBillByNo.action'),
	    							params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
	    							success : function(response){
	    								var result = Ext.decode(response.responseText);
	    								var basicInfo = result.vehicleAssembleBillVo.baseEntity;
	    								//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
	    								if(basicInfo.beMidWayLoad == 'Y'){
	    									//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
	    									if(basicInfo.beFinallyArrive == 'N'){
	    										Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
	    										return;
	    									}
	    								}
	    								var currentDeptCode = FossUserContext.getCurrentDept().code,
	    									currentDeptName = FossUserContext.getCurrentDept().name;
	    									currentUserCode = FossUserContext.getCurrentUser().employee.empCode,
	    									currentUserName = FossUserContext.getCurrentUser().employee.empName;
	    								do_printpreview('carriagecontract',{"vehicleAssembleNo": vehicleAssembleNo, 
	    											"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
	    											"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
	    							}
	    						});
	    					}
	    				});
					} else {
						//后台读取配载单基本信息
						Ext.Ajax.request({
							url : load.realPath('queryVehicleAssembleBillByNo.action'),
							params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
							success : function(response){
								var result = Ext.decode(response.responseText);
								var basicInfo = result.vehicleAssembleBillVo.baseEntity;
								//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
								if(basicInfo.beMidWayLoad == 'Y'){
									//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
									if(basicInfo.beFinallyArrive == 'N'){
										Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
										return;
									}
								}
								//4：与零担合车不能打印配载单
								if(basicInfo.beInLTLCar=='Y'){
									Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '与零担合车, 不能打印!', 'error');
									return;
								}
								var currentDeptCode = FossUserContext.getCurrentDept().code,
									currentDeptName = FossUserContext.getCurrentDept().name;
									currentUserCode = FossUserContext.getCurrentUser().employee.empCode,
									currentUserName = FossUserContext.getCurrentUser().employee.empName;
								do_printpreview('carriagecontract',{"vehicleAssembleNo": vehicleAssembleNo, 
											"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
											"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							}
						});
					}
				}
			});
		}
	} ],
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.action')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.delete')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				grid.store.removeAt(rowIndex);
			}
		} ],
		renderer:function(value, metadata, record){
			//整车配置单不能修改
	    	if(load.vehicleassemblebillmodify.assembleType == 'CAR_LOAD'){
	    		this.items[0].iconCls = '';
	    	} else {
	    		this.items[0].iconCls = 'deppon_icons_remove';
	    	}
	    }
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.weightTotal')//'重量</br>(千克)'
	}, {
		dataIndex : 'volumeTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.volumeTotal')//'体积</br>(方)'
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.goodsQtyTotal')/*'件数'*/
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.waybillQtyTotal')/*'票数'*/
	}, {
		dataIndex : 'note',
		align : 'center',
		width : 300,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/
	}, {
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/,
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:load.vehicleassemblebillmodify.showHandOverBillDetail('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	},{
		dataIndex : 'vehicleNo',
		text : '车牌号',
		hidden : true
	}],
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_vehicleassemblebillmodify_toobar',
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
			id : 'Foss_load_vehicleassemblebillmodify_TotalWeight',
			fieldLabel: load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalWeight'),//'总重量(千克)',
			columnWidth : 1/4,
			labelWidth : 120,
			xtype : 'numberfield',
			value : 0
			},{
			id : 'Foss_load_vehicleassemblebillmodify_TotalVolume',
			fieldLabel: load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalVolume'),//'总体积(方)',
			labelWidth : 120,
			columnWidth : 1/4,
			xtype : 'numberfield',
			value : 0,
			listeners : {
					'change' : function(field, newValue, oldValue, eOpts){
						var form = load.vehicleassemblebillmodify.basicInfoForm.getForm();
						//获取考核体积
						var examineVolume = form.findField('examineVolume').getValue();
						if(!load.vehicleassemblebillmodify.isNumber(examineVolume)){
							form.findField('loadingRate').setValue(0.00);
							return;
						}
						//计算装载率
						var loadingRate = newValue/examineVolume;
						if(!load.vehicleassemblebillmodify.isNumber(loadingRate)){
							form.findField('loadingRate').setValue(0.00);
						}else{
							form.findField('loadingRate').setValue(loadingRate.toFixed(2));
						}
					}
				}
			},{
			id : 'Foss_load_vehicleassemblebillmodify_TotalPieces',
			fieldLabel: load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalPieces')/*'总件数'*/,
			columnWidth : 1/5,
			xtype : 'numberfield',
			value : 0
			},{
			id : 'Foss_load_vehicleassemblebillmodify_TotalWaybill',
			fieldLabel: load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalWaybill')/*'总票数'*/,
			columnWidth : 1/5,
			xtype : 'numberfield',
			value : 0
			}]
	  }]
});

//定义方法，生成弹出窗口的查询条件中“交接时间”的起始和结束时间
load.vehicleassemblebillmodify.getHandOverTime4QueryForm = function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	}else{
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
}

//定义弹出窗口查询条件form
Ext.define('Foss.load.vehicleassemblebillmodify.QueryForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.formTitle')/*'查询条件'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 80,
		columnWidth : 1/3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/
	},{
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/,
		xtype : 'commontruckselector',
		name : 'vehicleNo'
	},{
		allowBlank : false,
		xtype : 'rangeDateField',
		fieldLabel : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.handOverTime')/*'交接时间'*/,
		columnWidth : 4/7,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_vehicleassemblebillmodify_QueryForm_HandOverTime_fieldID',
		id : 'Foss_vehicleassemblebillmodify_QueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateRange : 20,
		fromValue : Ext.Date.format(load.vehicleassemblebillmodify.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.vehicleassemblebillmodify.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'),
		fromName : 'beginHandOverTime',
		toName : 'endHandOverTime'
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.resetButton')/*'重置'*/,
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('beginHandOverTime').setValue(Ext.Date.format(load.vehicleassemblebillmodify.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'));
				form.findField('endHandOverTime').setValue(Ext.Date.format(load.vehicleassemblebillmodify.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.queryButton')/*'查询'*/,
			handler : function(){
				load.vehicleassemblebillmodify.queryGridPanel.store.load();
			}
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单查询窗口中交接单store
Ext.define('Foss.load.vehicleassemblebillmodify.QueryPageHandOverBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillmodify.HandOverBillModel',
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryUnAssembledHandOverBillList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vehicleAssembleBillVo.handOverBillList'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = load.vehicleassemblebillmodify.queryForm.getForm().getValues();
			var destOrgCode = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('destOrgCode').getValue();
			Ext.apply(operation, {
				params : {
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.handOverBillNo' : queryParams.handOverBillNo,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.arriveDept' : destOrgCode,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.vehicleNo' : queryParams.vehicleNo,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.beginHandOverTime' : queryParams.beginHandOverTime,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.endHandOverTime' : queryParams.endHandOverTime,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.truckPlanDetailVehicleNo' :　load.vehicleassemblebillmodify.truckPlanDetailVehicleNo,//发车计划中的挂牌号
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.trailerVehicleNo' : load.vehicleassemblebillmodify.trailerVehicleNo //挂牌号
				}
			});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义弹出窗口中交接单列表Grid
Ext.define('Foss.load.vehicleassemblebillmodify.QueryPageHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.gridTitle')/*'交接单列表'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	emptyText : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.emptyText')/*'查询结果为空'*/,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.load.vehicleassemblebillmodify.QueryPageHandOverBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true
		});
		me.callParent([cfg]);
},
	columns : [{
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/
	},{
		dataIndex : 'handOverTime',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverTime'),//'交接日期',
		renderer : function(value){
			if(value!=null){
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');									
		}else{
				return null;
			}
		}
	},{
		dataIndex : 'arriveDept',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 100,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/
	},{
		dataIndex : 'weightTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.weightTotal')//'重量</br>(千克)'
	}, {
		dataIndex : 'volumeTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.volumeTotal')//'体积</br>(方)'
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.goodsQtyTotal')/*'件数'*/
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.waybillQtyTotal')/*'票数'*/
	}]
});

//定义查询交接单弹出窗口
Ext.define('Foss.load.vehicleassemblebillmodify.QueryHandOverBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	width : 850,
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.windowTitle')/*'查询待配载交接单'*/,
	queryForm: null,
	getQueryForm: function(){
		if(this.queryForm==null){
			this.queryForm = Ext.create('Foss.load.vehicleassemblebillmodify.QueryForm');
			load.vehicleassemblebillmodify.queryForm = this.queryForm;
		}
		return this.queryForm;
	},
	queryGridPanel : null,
	getQueryGridPanel : function(){
		if(this.queryGridPanel==null){
			this.queryGridPanel = Ext.create('Foss.load.vehicleassemblebillmodify.QueryPageHandOverBillGrid');
			load.vehicleassemblebillmodify.queryGridPanel = this.queryGridPanel;
		}
		return this.queryGridPanel;
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getQueryForm(),me.getQueryGridPanel()];
		me.callParent([cfg]);
	},
	listeners : {
		'beforehide' : function(){
			
		}
	},
	buttons : [ {
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			var selectedList =load.vehicleassemblebillmodify.queryGridPanel.getSelectionModel().selected.items;
			if(selectedList.length == 0){
				Ext.ux.Toast.msg(
					load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
					load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.noHandOverBillChecked'),//'未选中任何交接单!', 
					'error', 
					2000
				);
				return;
			}
			//获取主页面列表store
			var mainStore = load.vehicleassemblebillmodify.mainPageGrid.store;
			//获取主页面中输入的车牌号
			var vehicleNo = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('vehicleNo').getValue();
			//获取主页面中输入的到达部门
			var arriveDept = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('destOrgCode').getValue();
			//获取主页面中输入的“配载类型”
			var assembleType = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('assembleType').getValue();
			//获取配载车次号，判断是否为快递配载单
			var vehicleAssembleNo = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('vehicleAssembleNo').getValue(),
				bePackage = vehicleAssembleNo.substr(0,3) == 'KD-' ? 'Y' : 'N';
			//从选中的记录中随意取出一个车牌号和到达部门
			var queriedVehicleNo = selectedList[0].get('vehicleNo');
			var queriedArrivedDept = selectedList[0].get('arriveDeptCode');
			var queriedDriverCode = selectedList[0].get('driver');
			var queriedIsCarLoad = selectedList[0].get('isCarLoad');
			var queriedBePackage = selectedList[0].get('bePackage');
			//定义已经添加到主页面的车牌号和到达部门；
			var addedVehicleNo,addedArriveDeptCode;
			if(mainStore.getCount() != 0){
				var record = load.vehicleassemblebillmodify.mainPageGrid.store.getAt(0);
				addedVehicleNo = record.get('vehicleNo');
				addedArriveDeptCode = record.get('arriveDeptCode');
			}
			//循环选中的交接单，进行各种校验
			for(var i in selectedList){
				var record = selectedList[i];
				var vehicleNoI = record.get('vehicleNo');
				var arriveDeptI = record.get('arriveDeptCode');
				var driverCodeI = record.get('driver');
				var isCarLoadI = record.get('isCarLoad');
				if(vehicleNoI != queriedVehicleNo){
					if( !Ext.isEmpty(load.vehicleassemblebillmodify.trailerVehicleNo)){
						if( vehicleNoI!=load.vehicleassemblebillmodify.trailerVehicleNo && vehicleNoI!=vehicleNo){
							Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSame'),//'操作失败，所选交接单车牌号不同!', 
								'error', 
								2000
							);
							return;
						}
					}else if(!Ext.isEmpty(load.vehicleassemblebillmodify.truckPlanDetailVehicleNo)){
							if( vehicleNoI!=load.vehicleassemblebillmodify.truckPlanDetailVehicleNo && vehicleNoI!=vehicleNo){
								Ext.ux.Toast.msg(
										load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
										load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSame'),//'操作失败，所选交接单车牌号不同!', 
										'error', 
										2000
									);
								return;
							}
					}else{
						Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSame'),//'操作失败，所选交接单车牌号不同!', 
								'error', 
								2000
							);
						return;
					}
					
				}
				if(bePackage == 'Y'){
					if(queriedBePackage == 'N'){
						Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								'操作失败，快递配载单只能添加包交接单！',
								'error', 
								2000
						);
						return;
					}
				}else{
					if(queriedBePackage == 'Y'){
						Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								'操作失败，非快递配载单不能添加包交接单！',
								'error', 
								2000
						);
						return;
					}
				}
				if(isCarLoadI != queriedIsCarLoad){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.carloadNotTheSame'),//'操作失败，所选交接单必须全是整车或者非整车交接单!', 
						'error', 
						2000
					);
					return;
				}
				if(assembleType == 'OWNER_LINE' && isCarLoadI == 'Y'){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.assembleTypeNotTheSame1'),//'操作失败，“专线”配载时不能添加整车交接单!', 
						'error', 
						2000
					);
					return;
				}
				if(assembleType == 'CAR_LOAD' && isCarLoadI == 'N'){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.assembleTypeNotTheSame2'),//'操作失败，“整车”配载时只能添加整车交接单!', 
						'error', 
						2000
					);
					return;
				}
				if(arriveDeptI != queriedArrivedDept){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSame'),//'操作失败，所选交接单到达部门不同!', 
						'error', 
						2000
					);
					return;
				}
				if(driverCodeI != queriedDriverCode){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.driverNotTheSame'),//'操作失败，所选交接单司机不同!', 
						'error', 
						2000
					);
					return;
				}
				
				
				
				if( !Ext.isEmpty(vehicleNo) && vehicleNoI != vehicleNo){
					if( !Ext.isEmpty(load.vehicleassemblebillmodify.trailerVehicleNo)){
							if( vehicleNoI!=load.vehicleassemblebillmodify.trailerVehicleNo){
								Ext.ux.Toast.msg(
									load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
									load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsMain'),//'操作失败，所选交接单车牌号和配载单车牌号不同!', 
									'error', 
									2000
								);
								return;
							}		
					}else if(!Ext.isEmpty(load.vehicleassemblebillmodify.truckPlanDetailVehicleNo)){
						if( vehicleNoI!=load.vehicleassemblebillmodify.truckPlanDetailVehicleNo){
							Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsMain'),//'操作失败，所选交接单车牌号和配载单车牌号不同!', 
								'error', 
								2000
							);
							return;
						}		
						
					}else{
						Ext.ux.Toast.msg(
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
								load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsMain'),//'操作失败，所选交接单车牌号和配载单车牌号不同!', 
								'error', 
								2000
							);
							return;
					}
				} 
				
				if(arriveDept != '' && arriveDeptI != arriveDept){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSameAsMain'),//'操作失败，所选交接单到达部门和配载单到达部门不同!', 
						'error', 
						2000
					);
					return;
				}
				if(addedArriveDeptCode != undefined && addedArriveDeptCode != arriveDeptI){
					Ext.ux.Toast.msg(
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSameAsMain'),//'操作失败，所选交接单到达部门和已经添加的交接单到达部门不同!', 
						'error', 
						2000
					);
					return;
				}
				//获取该行记录的交接单号
				var handOverBillNo = record.get('handOverBillNo');
				if(mainStore.getCount() != 0){
					//如果该交接单号已经被添加至主页面，则跳出循环
					if(mainStore.findRecord('handOverBillNo',handOverBillNo,0,false,false,false) != null){
						Ext.ux.Toast.msg(
							load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
							load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.billAlreadyAddedPrefix')//'操作失败，交接单【' 
							+ handOverBillNo 
							+ load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.billAlreadyAddedPostfix'),//'】已经添加至配载单', 
							'error', 
							3000
						);
						return;
					}
				}
			}
			//将该条记录插入主页面grid中
			record.set('isAddNew','Y');
			mainStore.insert(mainStore.getCount(),selectedList);	
			//关闭窗口
			this.ownerCt.ownerCt.close();
		}
	}]
});

//点击“交接单编号”打开详情界面方法
load.vehicleassemblebillmodify.showHandOverBillDetail = function(handOverBillNo){
	load.addTab('T_load-handoverbillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.showHandOverBillTabTitle')/*'交接单详情'*/,//打开的Tab页的标题
			load.realPath('handoverbillshowindex.action') + '?handOverBillNo="' + handOverBillNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//定义交接单日志Model
Ext.define('Foss.load.vehicleassemblebillmodify.OptLogModel', {
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
Ext.define('Foss.load.vehicleassemblebillmodify.OptLogStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillmodify.OptLogModel',
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
					'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillmodify.vehicleAssembleNo
				}
			});	
		}
	}
});

//定义交接单修改日志列表
Ext.define('Foss.load.vehicleassemblebillmodify.OptLogGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.gridTitle')/*'修改日志'*/,
//	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	collapsed : true,//页面初始化时不展开该grid，不加载数据
	animCollapse : true,
	store : Ext.create('Foss.load.vehicleassemblebillmodify.OptLogStore'),
	columns : [{
		dataIndex : 'operatorName',
		align : 'center',
		width : 70,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.operatorName')/*'操作人'*/
	}, {
		dataIndex : 'optTime',
		align : 'center',
		width : 135,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optTime')/*'操作时间'*/,
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
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optType')/*'操作类别'*/
	}, {
		dataIndex : 'optContent',
		flex : 1,
		text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.optLogGrid.optContent')/*'操作内容'*/,
		xtype : 'linebreakcolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
	});
	load.vehicleassemblebillmodify.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	listeners : {
		'expand' : function(panel, eOpts){
			this.store.load();	//展开表格后加载日志数据
		}
	}
});
//定义Map，存储修改前的交接单集合（key：交接单号，value：交接��信息）
load.vehicleassemblebillmodify.oldHandOverBillMap = new Ext.util.HashMap();
//定义Map，存储新增的交接单集合（key：交接单号，value：交接单信息）
load.vehicleassemblebillmodify.addedHandOverBillMap = new Ext.util.HashMap();
//定义Map，存储新增的交接单集合（key：交接单号，value：交接单信息）
load.vehicleassemblebillmodify.deletedHandOverBillMap = new Ext.util.HashMap();
//基本信息Form
load.vehicleassemblebillmodify.basicInfoForm = Ext.create('Foss.load.vehicleassemblebillmodify.BasicInfoForm');
//交接单列表grid
load.vehicleassemblebillmodify.mainPageGrid = Ext.create('Foss.load.vehicleassemblebillmodify.MainPageHandOverBillGrid');
//定义查询待配载交接单窗口
load.vehicleassemblebillmodify.queryWindow = Ext.create('Foss.load.vehicleassemblebillmodify.QueryHandOverBillWindow');
//奖罚协议grid
load.vehicleassemblebillmodify.RewardOrPunishGrid= Ext.create('Foss.load.vehicleassemblebillmodify.RewardOrPunishGrid');

//onReady
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-vehicleassemblebillmodifyindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [load.vehicleassemblebillmodify.basicInfoForm,
				 load.vehicleassemblebillmodify.RewardOrPunishGrid,
		         load.vehicleassemblebillmodify.mainPageGrid,{
			 			xtype : 'container',
						columnWidth : 1,
						layout : 'column',
						items : [{
							xtype : 'container',
							columnWidth : .98,
							html : '&nbsp'
						},{
							xtype : 'button',
							width : 80,
							cls : 'yellow_button',
							name : 'saveButton',
							id : 'Foss_load_vehicleassemblebillmodify_mainPage_saveButton_ID',
							text : load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.saveButton')/*'保存'*/,
							handler : function(){
								//信息是否完整
								var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
								var baseEntity = basicInfoForm.getValues();
								//如果是专线且费用为0提示
								if(baseEntity.assembleType == 'OWNER_LINE'&& baseEntity.feeTotal==0 ){
									Ext.Msg.confirm(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"是否确认车价为零总费用为零！", function(optional){
		    								if(optional != 'no'){
		    									load.confirmModifyAction();
		    								}
										});
								}else{
									load.confirmModifyAction();
								}
							}
				}]
			},
		         Ext.create('Foss.load.vehicleassemblebillmodify.OptLogGrid')],
		renderTo : 'T_load-vehicleassemblebillmodifyindex-body'
	});
	//mask
	var myMask = new Ext.LoadMask(Ext.getCmp('T_load-vehicleassemblebillmodifyindex_content'), {
		msg:"加载中，请稍候..."
	});
	myMask.show();
	//后台读取配载单基本信息
	Ext.Ajax.request({
		url : load.realPath('queryVehicleAssembleBillByNo.action'),
		params : {'vehicleAssembleBillVo.vehicleAssembleNo' : load.vehicleassemblebillmodify.vehicleAssembleNo,
						'vehicleAssembleBillVo.isModify' : 'Y'},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.vehicleAssembleBillVo.baseEntity;
			//是否被打印过
			load.vehicleassemblebillmodify.checkCarriageContractPrinted = result.vehicleAssembleBillVo.checkCarriageContractPrinted;
			//配载状态
			load.vehicleassemblebillmodify.state = basicInfo.state;
			//发车计划id 
			load.vehicleassemblebillmodify.truckPlanDetailVehicleNo=result.vehicleAssembleBillVo.trailerVehicleNo;
			//记录总费用
			load.vehicleassemblebillmodify.tempTotalFee = basicInfo.feeTotal;
			//记录不变的总运费
			load.vehicleassemblebillmodify.staticTotalFee=basicInfo.feeTotal;
			//原车牌号的约车总运费
			load.vehicleassemblebillmodify.inviteFeetotal = result.vehicleAssembleBillVo.inviteFeetotal;
			//记录车辆总体积、载重
			load.vehicleassemblebillmodify.tempVehicleLoadWeight = basicInfo.ratedLoad;//载重
			load.vehicleassemblebillmodify.tempVehicleLoadVolume = basicInfo.ratedClearance;//净空
			//获取标记变量，看司机车牌号结算信息可否修改；
			var canBeModified = result.vehicleAssembleBillVo.isStlInfoAndDriverVehicleCanBeModified;
			//基本信息表单
			var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
			//如果“配载类型”为整车，则可编辑“主驾驶姓名”
			load.vehicleassemblebillmodify.assembleType = basicInfo.assembleType;
			if(basicInfo.assembleType == 'CAR_LOAD'){
				basicInfoForm.findField('driverName').setReadOnly(false);
			}
			//获取车牌号
			load.vehicleassemblebillmodify.oldVehicleNo = basicInfo.vehicleNo;
			//记录不会改变的车牌号
			load.vehicleassemblebillmodify.staticVehicleNo = basicInfo.vehicleNo;
			//处理发车时间
			basicInfo.leaveTime = new Date(basicInfo.leaveTime);
			//处理制单时间
			basicInfo.createDate = new Date(basicInfo.createDate);
			//处理是否押回单、是否最终到达、是否在途装卸
			if(basicInfo.beMidWayLoad == 'Y'){
				basicInfo.beMidWayLoad = true;
				basicInfoForm.findField('beFinallyArrive').setVisible(true);
			}
			if(basicInfo.beFinallyArrive == 'Y'){
				basicInfo.beFinallyArrive = true;
			}
			if(basicInfo.beReturnReceipt == 'Y'){
				basicInfo.beReturnReceipt = true;
			}
			if(basicInfo.beInLTLCar == 'Y'){
				basicInfo.beInLTLCar = true;
			}
			if(basicInfo.beCarSmallUsed=='Y'){
				basicInfo.beCarSmallUsed=true;
			}
			//是否时效条款
			if(basicInfo.beTimeLiness == 'Y'){
				basicInfo.beTimeLiness =true;
				var rewardOrpunishgird = Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardOrPunish_ID');
				var data = result.vehicleAssembleBillVo.rewardOrPunishDto.rewardOrPunishAgreementEntity,
				rewardMoney = result.vehicleAssembleBillVo.rewardOrPunishDto.rewardMaxMoney,
				fineMaxMoney = result.vehicleAssembleBillVo.rewardOrPunishDto.fineMaxMoney;
				//加载奖罚信息
				rewardOrpunishgird.getStore().loadData(data,null);
				
				//设置奖励最高金额
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setValue(rewardMoney);
				//当奖励金额为0的时候隐藏封顶奖励金额上限
				if(rewardMoney==0){
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').setVisible(false);
				}
				//设置惩罚最高金额
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').setValue(fineMaxMoney);
				
			}
			//如果已出发，则不能修改是否签署实时效条款
			if(basicInfo.state!=10||load.vehicleassemblebillmodify.checkCarriageContractPrinted==1){
				//设置是否签署时效条款不可编辑
				basicInfoForm.findField('beTimeLiness').setReadOnly(true);
				//隐藏新增按钮
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_rewardOrpunish_addButton_ID').setVisible(false);
			}
			
			//如果到达部门支持自动分拣，则“货物类型”可编辑、载重、净空可修改，否则无法修改
			var isSupportAutoSorting = result.vehicleAssembleBillVo.isSupportAutoSorting;
			if(isSupportAutoSorting == 'Y'){
				basicInfoForm.findField('goodsType').setReadOnly(false);
				basicInfoForm.findField('ratedLoad').setReadOnly(false);
				basicInfoForm.findField('ratedClearance').setReadOnly(false);
			}else{
				basicInfoForm.findField('goodsType').setReadOnly(true);
			}
			//如果为外请车，且为合同车，则“付款方式”可编辑，否则其他情况均为只读
			var isBarginCar = result.vehicleAssembleBillVo.isBarginCar;
			if(isBarginCar == 'Y'){
				basicInfoForm.findField('paymentType').setReadOnly(false);
			}else{
				basicInfoForm.findField('paymentType').setReadOnly(true);
				if(basicInfo.vehicleOwnerShip == 'Company'){
					basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
					basicInfoForm.findField('arriveFeeTotal').setReadOnly(true);
				}
			}
			//在途只装不卸
			if(basicInfo.beMidWayOnlyLoad == 'Y'){
				basicInfo.beMidWayOnlyLoad= true;
				basicInfoForm.findField('onTheWayDestOrgCode').setVisible(true);
			}
			var basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.load.vehicleassemblebillmodify.BillBasicInfoModel');
			//设置车牌号
			basicInfoForm.findField('vehicleNo').setCombValue(basicInfo.vehicleNo,basicInfo.vehicleNo);
			//设置中途到达部门
			basicInfoForm.findField('onTheWayDestOrgCode').setCombValue(basicInfo.onTheWayDestOrgName,basicInfo.onTheWayDestOrgCode);
			//加载交接单列表
			var mainStore = load.vehicleassemblebillmodify.mainPageGrid.store;
			mainStore.load({
				callback : function(records, operation, success){
					var record;
					//计算装车总金额、代收货款总额，总重量、总体积、总件数、总票数
					var totalFee = 0,totalCod = 0,totalWaybill = 0,totalPieces = 0,totalVolume = 0,totalWeight = 0;
					for(var i in records){
						record = records[i];
						//将加载出的交接单列表放置于oldMap中
						load.vehicleassemblebillmodify.oldHandOverBillMap.add(record.get('handOverBillNo'),record);
						totalFee += record.get('moneyTotal');
						totalWaybill += record.get('waybillQtyTotal');
						totalPieces += record.get('goodsQtyTotal');
						totalVolume += record.get('volumeTotal');
						totalWeight += record.get('weightTotal');
						totalCod += record.get('codAmountTotal');
					}
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').setValue(totalWeight);
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').setValue(totalVolume);
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').setValue(totalPieces);
					Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').setValue(totalWaybill);
					//更改基本信息model里的总金额、代收货款总额。。。。。。
					basicInfoRecord.set('loadFeeTotal',totalFee);
					basicInfoRecord.set('collectionFeeTotal',totalCod);
					basicInfoRecord.set('totalWeight',totalWeight);
					basicInfoRecord.set('totalVolume',totalVolume);
					basicInfoRecord.set('totalPieces',totalPieces);
					basicInfoRecord.set('totalWaybill',totalWaybill);
					//定义一个基本信息model，随着修改动作而变化值
					load.vehicleassemblebillmodify.basicInfoRecord = Ext.ModelManager.create(basicInfoRecord.data, 'Foss.load.vehicleassemblebillmodify.BillBasicInfoModel');
					//定义一个基本信息model，记录修改前的配载单基本信息，不变化，做生成日志对比之用。
					load.vehicleassemblebillmodify.oldBasicInfoRecord = Ext.ModelManager.create(basicInfoRecord.data, 'Foss.load.vehicleassemblebillmodify.BillBasicInfoModel');
					//为基本信息加载值
					basicInfoForm.loadRecord(load.vehicleassemblebillmodify.basicInfoRecord);
					//如果为外请车，则显示车型，隐藏货柜，反之则反之
					if(basicInfoForm.findField('vehicleOwnerShip').getValue() == 'Leased'){
						basicInfoForm.findField('vehicleModel').setVisible(true);
						basicInfoForm.findField('containerNo').setVisible(false);
						basicInfoForm.findField('containerNo').setReadOnly(true);
						basicInfoForm.findField('containerNo').setDisabled(true);
						//外请车则显示适用载重和适用净空 司机自带货量
						basicInfoForm.findField('driverOfWeight').setVisible(true);
						basicInfoForm.findField('driverOfVolumn').setVisible(true);
						basicInfoForm.findField('applicationRatedLoad').setVisible(true);
						basicInfoForm.findField('applicationRatedClearance').setVisible(true);
						basicInfoForm.findField('beCarSmallUsed').setVisible(true);
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_m³').setVisible(true);
					}else{
						//如果为公司车，则获取车辆类型来控制货柜号和车型的显示或者隐藏
						var vehicleType = result.vehicleAssembleBillVo.ownerVehicleType
						if(vehicleType == 'vehicletype_tractors'){
							basicInfoForm.findField('vehicleModel').setVisible(false);
							basicInfoForm.findField('containerNo').setVisible(true);
							basicInfoForm.findField('containerNo').setDisabled(false);
						}else{//如果为厢式车或挂车
							basicInfoForm.findField('vehicleModel').setVisible(true);
							basicInfoForm.findField('containerNo').setVisible(false);
							basicInfoForm.findField('containerNo').setDisabled(true);
						}
						//公司车则影藏适用载重和适用净空 司机自带货量
						basicInfoForm.findField('driverOfWeight').setVisible(false);
						basicInfoForm.findField('driverOfVolumn').setVisible(false);
						basicInfoForm.findField('applicationRatedLoad').setVisible(false);
						basicInfoForm.findField('applicationRatedClearance').setVisible(false);
						basicInfoForm.findField('beCarSmallUsed').setVisible(false);
						Ext.getCmp('Foss_load_vehicleassemblebillmodify_m³').setVisible(false);
					}
					//如果配载单已出发或已出发付款确认，则财务信息不可修改、且司机、车牌号不能修改；
					if(canBeModified == 'N'){
						basicInfoForm.findField('paymentType').setReadOnly(true);
						basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
						basicInfoForm.findField('beReturnReceipt').setReadOnly(true);
						basicInfoForm.findField('driverName').setReadOnly(true);
						basicInfoForm.findField('vehicleNo').setReadOnly(true);
						basicInfoForm.findField('beMidWayLoad').setReadOnly(true);
						basicInfoForm.findField('beFinallyArrive').setReadOnly(true);
						basicInfoForm.findField('beInLTLCar').setReadOnly(true);
						basicInfoForm.findField('beMidWayOnlyLoad').setReadOnly(true);
						basicInfoForm.findField('onTheWayDestOrgCode').setReadOnly(true);
						
						//不能编辑适用载重和适用净空 司机自带货量
						basicInfoForm.findField('driverOfWeight').setReadOnly(true);
						basicInfoForm.findField('driverOfVolumn').setReadOnly(true);
						basicInfoForm.findField('applicationRatedLoad').setReadOnly(true);
						basicInfoForm.findField('applicationRatedClearance').setReadOnly(true);
						basicInfoForm.findField('beCarSmallUsed').setReadOnly(true);
					}
					console.log(basicInfoForm.findField('vehicleNo').getValue());
					//给基本信息form表单每个控件添加事件监听
					var itemList = basicInfoForm._fields.items;
					for(var i in itemList){
						var cmp = itemList[i];
						cmp.addListener('change',function(field,newValue,oldValue,eOpts){
							var fieldName = field.name;
							if(newValue != load.vehicleassemblebillmodify.basicInfoRecord.get(fieldName)){
								load.vehicleassemblebillmodify.basicInfoRecord.set(fieldName,field.getValue());
							}
						});
					}
					myMask.hide();
				}
			});
		},
		exception : function(response){
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.saveFailureInfoPrefix')/*'保存失败，'*/ + result.message);
			myMask.hide();
		}
	});
});

load.confirmModifyAction = function(){

	//信息是否完整
	var basicInfoForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
	//坑爹的LMS车辆基础资料，此处校验车辆的额定净空和额定载重
	if(Ext.isEmpty(basicInfoForm.findField('ratedClearance').getValue())){
		Ext.ux.Toast.msg(
			load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.ratedClearanceCanNotBeNull'),//'车辆的“额定净空”为空，请换用其他车辆！', 
			'error', 
			2000
		);
		return;
	}
	if(Ext.isEmpty(basicInfoForm.findField('ratedLoad').getValue())){
		Ext.ux.Toast.msg(
			load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.ratedLoadCanNotBeNull'),//'车辆的“额定载重”为空，请换用其他车辆！', 
			'error', 
			2000
		);
		return;
	}
	if(!basicInfoForm.isValid()){
		return;
	}
	var changed = load.vehicleassemblebillmodify.basicInfoRecord.getChanges();
	var isModified = false;
	//判断基本信息是否被修改
	for(var i in changed){
		isModified = true;
		break;
	}
	//---------------------------------TODO
	//修改后的基本信息
	var baseEntity = load.vehicleassemblebillmodify.basicInfoRecord.data;
	//奖罚协议
	var reWardStore = load.vehicleassemblebillmodify.RewardOrPunishGrid.store;
	
	//存放奖罚信息的数组
	var vehicleRewardProtEntity= new Array();
	var baseEntity =basicInfoForm.getValues();
	if(baseEntity.beTimeLiness =='on'){
		//处理基本信息
		baseEntity.beTimeLiness='Y';
		var reMoveRecordes=reWardStore.getRemovedRecords();
		//判断是否修改
		if(reWardStore.getModifiedRecords().length>0 ||reMoveRecordes.length>0){
			isModified = true;
		}
		//midWayLoadType
		/*if(baseEntity.midWayLoadType==null && baseEntity.beMidWayLoad){
			Ext.ux.Toast.msg('提示''提示', 
			"勾选了是否在途装卸时就必须选择在途配载类型!",
			'error', 
			2000
			);
			field.setValue(false);
			return;
		}*/
		
		if(!baseEntity.beFinallyArrive && baseEntity.beMidWayLoad){
			Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			"只有专线外请车且非在途的配载才能签署时效条款!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
			'error', 
			2000
			);
			basicInfoForm.findField('beTimeLiness').setValue(false);
			return;
		}
		//定义数组用于存放连续的区间
		var timeRewardArray=[];
		var timepunishArray=[];
		var rewardcount=0;
		var finecount=0;
		if(reWardStore.data.items.length==0){
			Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"请录入时效条款信息!",//'没有选择奖罚类型!', 
				'error', 
				2000
				);
			return;
		}
		//校验奖罚协议中上下限的问题
		for(var i in reWardStore.data.items){
			var record = reWardStore.data.items[i];
			var rewardOrPunishType = record.get('rewardOrPunishType');
			if(rewardOrPunishType == ''){
				Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"没有选择奖罚类型!",//'没有选择奖罚类型!', 
				'error', 
				2000
				);
				return;
			}
			
			var timeLimit = record.get('timeLimit');
			if(Ext.isEmpty(timeLimit)){
				Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"时间段不能为空!",//'时间上限不能小于时间下限!', 
				'error', 
				2000
				);
				return;
			}
			var agreementMoney =record.get('agreementMoney');
			if(Ext.isEmpty(agreementMoney)){
				Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"奖罚金额不能为空!",//'奖罚金额不能为空!', 
				'error', 
				2000
				);
				return;
			}
			//获取连续区间
			if(rewardOrPunishType =='REWARD'){
				//没有种类型都有时间上限，根据时间上限找到对应的连续区间，用于判断选择的时间段是否连续
				if(load.vehicleassemblebillmodify.timeCombinationMap.get(timeLimit)!=null){
					 timeRewardArray =load.vehicleassemblebillmodify.timeCombinationMap.get(timeLimit)
				}
				rewardcount=rewardcount+1;
			}else{
				if(load.vehicleassemblebillmodify.timeCombinationMap.get(timeLimit)!=null){
					 timepunishArray =load.vehicleassemblebillmodify.timeCombinationMap.get(timeLimit)
				}
				finecount=finecount+1;
			}
		
			vehicleRewardProtEntity.push(record.data);
		}
		//
		//如果存在奖励和罚款则判断奖励和罚款区间是否保持一致
//		if(timeRewardArray!=null && timepunishArray!=null && (rewardcount>=1 && finecount>=1)
//			&& (timeRewardArray.length>=1 ||timepunishArray.length>=1 )&& timeRewardArray!=timepunishArray){
//			//奖励和罚款时间区间不一致
//				Ext.ux.Toast.msg(
//				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
//				"奖励和惩罚时间区间不一致!",//'奖励和惩罚时间区间不一致', 
//				'error', 
//				2000
//				);
//				return;
//			}
//		
		if(timeRewardArray!=null && timepunishArray!=null){
				if(timeRewardArray.length>=1){
						//判断区间是否连续
					for(var n=0; n<timeRewardArray.length;n++){
					
						var isEqual = false;
						for(var m in reWardStore.data.items){
							var record = reWardStore.data.items[m];
							if(timeRewardArray[n]==record.get('timeLimit')&& record.get('rewardOrPunishType') =='REWARD'){
								isEqual=true;
								break;
							}
						}

						if(!isEqual){
							//找不到对于的区间，说明区间不连续
							Ext.ux.Toast.msg(
							load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
							"奖励时间段不连续!",//'奖罚区间不连续', 
							'error', 
							2000
							);
							return;
						}
						
					}
				}
				if(timepunishArray.length>=1){
						//判断区间是否连续
					for(var n=0; n<timepunishArray.length;n++){
					
						var isEqual = false;
						for(var m in reWardStore.data.items){
							var record = reWardStore.data.items[m];
							if(timepunishArray[n]==record.get('timeLimit')&& record.get('rewardOrPunishType') =='FINE'){
								isEqual=true;
								break;
							}
						}

						if(!isEqual){
							//找不到对于的区间，说明区间不连续
							Ext.ux.Toast.msg(
							load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
							"惩罚时间段不连续!",//'奖罚区间不连续', 
							'error', 
							2000
							);
							return;
						}
						
					}

				}
				
				if((timepunishArray.length<1 && finecount>0)|| (timeRewardArray.length<1 && rewardcount > 0)){
					//找不到对于的区间，说明区间不连续
					Ext.ux.Toast.msg(
					load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
					"时间段不连续!",//'奖罚区间不连续', 
					'error', 
					2000
					);
					return;
				}
			
			}
				
		

	}else{
		baseEntity.beTimeLiness='N';
	}
		//---------------------------------TODO
		//midWayLoadType
		if(baseEntity.midWayLoadType=='' && baseEntity.beMidWayLoad){
			Ext.ux.Toast.msg('提示', 
			"勾选了是否在途装卸时就必须选择在途配载类型!",
			'error', 
			2000
			);
			field.setValue(false);
			return;
		}
		if(baseEntity.vehicleOwnerShip!='Company'){
			if(Ext.isEmpty(baseEntity.driverOfWeight) || Ext.isEmpty(baseEntity.driverOfVolumn) ){
				Ext.ux.Toast.msg('提示', 
						"请填写司机自带货量!",
						'error', 
						2000
						);
						return;
			}
		}
		//进行校验
		if(!isModified
				&& load.vehicleassemblebillmodify.addedHandOverBillMap.getCount() == 0
				&& load.vehicleassemblebillmodify.deletedHandOverBillMap.getCount() == 0){
			Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.noDataHasChanged'),//'操作失败，未做任何修改!', 
				'error', 
				2000
			);
			return;
		}
		if(load.vehicleassemblebillmodify.mainPageGrid.store.getCount() == 0){
			Ext.ux.Toast.msg(
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.atleastOneBill'),//'操作失败，至少需要添加一个交接单!', 
				'error', 
				2000
			);
			return;
		}
		//获取配载类型
		var assembleType = cmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField('assembleType').getValue();
		//处理基本信息修改情况，生成日志
		var baseInfoOptLog = '';
		var cmp;
		var fieldLabel;
		var oldValue;
		var newValue;
		//循环changed对象，拼接日志
		for(var fieldName in changed){
			//根据控件name获取lable，拼接日志
			cmp = load.vehicleassemblebillmodify.basicInfoForm.getForm().findField(fieldName);
			fieldLabel = cmp.fieldLabel;
			//转换combobox key value
			if(cmp.xtype == 'combobox'){
			   if(load.vehicleassemblebillmodify.oldBasicInfoRecord.get('midWayLoadType')==''){
				   load.vehicleassemblebillmodify.oldBasicInfoRecord.data.midWayLoadType=baseEntity.midWayLoadType;
			   }
			
				//load.vehicleassemblebillmodify.oldBasicInfoRecord.get('midWayLoadType').setValue()=baseEntity.midWayLoadType;
				oldValue = cmp.store.findRecord('key',load.vehicleassemblebillmodify.oldBasicInfoRecord.get(fieldName),0,false,false,false).get('value');
				newValue = cmp.store.findRecord('key',changed[fieldName],0,false,false,false).get('value');
			}else{
				oldValue = load.vehicleassemblebillmodify.oldBasicInfoRecord.get(fieldName);
				newValue = changed[fieldName];
			}
			//转换checkbox
			if(cmp.xtype == 'checkbox'){
				fieldLabel = cmp.boxLabel;
				if(oldValue){
					oldValue = '是';
				}else{
					oldValue = '否';
				}
				if(newValue){
					newValue = '是';
				}else{
					newValue = '否';
				}
			}
			//拼接基本信息修改日志
			baseInfoOptLog += '“' + fieldLabel + '”'+ '由' + oldValue + '修改为' + newValue + '；';
		}
		//交接单数量的变化必然会导致重量、体积、件数、票数的变化，记录统计信息变化日志
		if(load.vehicleassemblebillmodify.addedHandOverBillMap.getCount() != 0
				|| load.vehicleassemblebillmodify.deletedHandOverBillMap.getCount() != 0){
			//重量变化日志
			var oldWeight = load.vehicleassemblebillmodify.oldBasicInfoRecord.get('totalWeight');
			var newWeight = Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWeight').getValue();
			if(oldWeight != newWeight){
				baseInfoOptLog += '“重量”由' + oldWeight + '变化为' + newWeight + '；';
			}
			//体积变化日志
			var oldVolume = load.vehicleassemblebillmodify.oldBasicInfoRecord.get('totalVolume');
			var newVolume = Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalVolume').getValue();
			if(oldVolume != newVolume){
				baseInfoOptLog += '“体积”由' + oldVolume + '变化为' + newVolume + '；';
			}
			//件数变化日志
			var oldPieces = load.vehicleassemblebillmodify.oldBasicInfoRecord.get('totalPieces');
			var newPieces = Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalPieces').getValue();
			if(oldPieces != newPieces){
				baseInfoOptLog += '“件数”由' + oldPieces + '变化为' + newPieces + '；';
			}
			//票数变化日志
			var oldWaybillCount = load.vehicleassemblebillmodify.oldBasicInfoRecord.get('totalWaybill');
			var newWaybillCount = Ext.getCmp('Foss_load_vehicleassemblebillmodify_TotalWaybill').getValue();
			if(oldWaybillCount != newWaybillCount){
				baseInfoOptLog += '“票数”由' + oldWaybillCount + '变化为' + newWaybillCount + '；';
			}
		}
		//定义新增的交接单list
		var addedHandOverBillList = new Array();
		if(load.vehicleassemblebillmodify.addedHandOverBillMap.getCount() != 0){
			var addedHandOverBillModelList = load.vehicleassemblebillmodify.addedHandOverBillMap.getValues();
			for(var i in addedHandOverBillModelList){
				var isCarLoad = addedHandOverBillModelList[i].data.isCarLoad;
				if(isCarLoad == 'Y' && assembleType == 'OWNER_LINE'){
					Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '“专线”配载不可添加整车交接单！', 'info', 3000);
					return;
				}
				if(isCarLoad == 'N' && assembleType == 'CAR_LOAD'){
					Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '“整车”配载只能添加整车交接单！', 'info', 3000);
					return;
				}
				addedHandOverBillList.push(addedHandOverBillModelList[i].data);
			}
		}
		//所有的交接单List
		var handoverBillList = new Array();
		var records = load.vehicleassemblebillmodify.mainPageGrid.store.getRange();
		for(var i = 0; i < records.length; i++) {
			var record = records[i];
			handoverBillList.push(record.data);
		}
		//定义删除的交接单list
		var deletedHandOverBillList = new Array();
		if(load.vehicleassemblebillmodify.deletedHandOverBillMap.getCount() != 0){
			var deletedHandOverBillModelList = load.vehicleassemblebillmodify.deletedHandOverBillMap.getValues();
			for(var i in deletedHandOverBillModelList){
				deletedHandOverBillList.push(deletedHandOverBillModelList[i].data);
			}
		}
		var baseEntity = load.vehicleassemblebillmodify.basicInfoRecord.data;
		baseEntity.beTimeLiness = baseEntity.beTimeLiness ? 'Y':'N';
		//处理基本信息中的出发日期
		baseEntity.leaveTime = new Date(baseEntity.leaveTime);
		//处理基本信息中的“是否在途装卸”
		if(baseEntity.beMidWayLoad){
			baseEntity.beMidWayLoad = 'Y';
			//处理基本信息中的“是否最终到达”
			if(baseEntity.beFinallyArrive){
				baseEntity.beFinallyArrive = 'Y';
			}else{
				baseEntity.beFinallyArrive = 'N';
			}
		}else{
			baseEntity.beMidWayLoad = 'N';
			baseEntity.beFinallyArrive = null;
		}
		//是否在途只装不卸
		if(baseEntity.beMidWayOnlyLoad){
			baseEntity.beMidWayOnlyLoad = 'Y';
		}else{
			baseEntity.beMidWayOnlyLoad = 'N';
		}
		//是否与零担合车
		if(baseEntity.beInLTLCar){
			baseEntity.beInLTLCar = 'Y';
		}else{
			baseEntity.beInLTLCar = 'N';
		}
		//处理基本信息中的“是否押回单”
		if(baseEntity.beReturnReceipt){
			baseEntity.beReturnReceipt = 'Y';
		}else{
			baseEntity.beReturnReceipt = 'N';
		}
		if(baseEntity.beCarSmallUsed){
			baseEntity.beCarSmallUsed='Y'
		}else{
			baseEntity.beCarSmallUsed='N'
		}
		//定义传到后台的json串
		var data = {'vehicleAssembleBillVo' : {
		'updateVehicleAssembleBillDto' : {
				'baseEntity' : baseEntity,
				'baseInfoOptLog' : baseInfoOptLog,
				'addedHandOverBillList' : addedHandOverBillList,
				'handoverBillList' : handoverBillList,
				'deletedHandOverBillList' : deletedHandOverBillList
			},
		'rewardOrPunishDto' : {
				'rewardOrPunishAgreementEntity' : vehicleRewardProtEntity,
				'rewardMaxMoney' : Ext.getCmp('Foss_load_vehicleassemblebillmodify_RewardMaxMoney').getValue(),
				'fineMaxMoney' :  Ext.getCmp('Foss_load_vehicleassemblebillmodify_FineMaxMoney').getValue()
				 
			  
			}
		}};
		var myMask = new Ext.LoadMask(Ext.getCmp('T_load-vehicleassemblebillmodifyindex_content'), {
			msg : '数据提交中，请稍候....'
		});
		myMask.show();
		//后台保存数据
		Ext.Ajax.request({
			url : load.realPath('updateVehicleAssembleBill.action'),
			jsonData : data,
			success : function(response){
				myMask.hide();
				Ext.ux.Toast.msg(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '修改成功!', 'info', 3000);
				//隐藏“保存”按钮
				Ext.getCmp('Foss_load_vehicleassemblebillmodify_mainPage_saveButton_ID').setVisible(false);
				var basicForm = load.vehicleassemblebillmodify.basicInfoForm.getForm();
				var formCmps = basicForm.getFields().getRange(0,basicForm.getFields().getCount());
				for(var i in formCmps){
					formCmps[i].setReadOnly(true);
				}
				//隐藏交接单列表前的操作列
				load.vehicleassemblebillmodify.mainPageGrid.columns[0].setVisible(false);
			},
			exception : function(response) {
				myMask.hide();
				//还原是否时效协议 如果不还原 再次保存 就会异常
				if(baseEntity.beTimeLiness=='Y'){
					baseEntity.beTimeLiness=true;
				}else{
					baseEntity.beTimeLiness=false;
				}
				//还原在途卸
				if(baseEntity.beMidWayLoad=='Y'){
					baseEntity.beMidWayLoad=true;
				}else{
					baseEntity.beMidWayLoad=false;
				}
				//还原最终到达
				if(baseEntity.beFinallyArrive=='Y'){
					baseEntity.beFinallyArrive=true;
				}else{
					baseEntity.beFinallyArrive=false;
				}
				//还原是否压回单
				if(baseEntity.beReturnReceipt=='Y'){
					baseEntity.beReturnReceipt=true;
				}else{
					baseEntity.beReturnReceipt=false;
				}
				//还原是否与零担合车
				if(baseEntity.beInLTLCar =='Y'){
					baseEntity.beInLTLCar = true;
				}else{
					baseEntity.beInLTLCar = false;
				}
				if(baseEntity.beMidWayOnlyLoad =='Y'){
					baseEntity.beMidWayOnlyLoad=true;
				}else{
					baseEntity.beMidWayOnlyLoad=false;
				}
				if(baseEntity.beCarSmallUsed =='Y'){
					baseEntity.beCarSmallUsed=true;
				}else{
					baseEntity.beCarSmallUsed=false;
				}
				var result = Ext.decode(response.responseText);
				top.Ext.MessageBox.alert(load.vehicleassemblebillmodify.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,result.message);
			},
			failure : function(){
				myMask.hide();
			}
		});

}