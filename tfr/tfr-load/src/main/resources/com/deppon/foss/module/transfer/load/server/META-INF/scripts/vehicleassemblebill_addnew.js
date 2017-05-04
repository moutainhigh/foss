//定义变量，存储总运费
load.vehicleassemblebilladdnew.tempTotalFee = 0;
//车辆载重
load.vehicleassemblebilladdnew.tempVehicleLoadWeight = 0;
//车辆净空
load.vehicleassemblebilladdnew.tempVehicleLoadVolume = 0;
//奖罚类型
load.vehicleassemblebilladdnew.returnSubType = 'AWARD_TYPE';
//奖罚时间段之间的互斥关系
load.vehicleassemblebilladdnew.timeMap = new Ext.util.HashMap();
load.vehicleassemblebilladdnew.timeMap.add("0",['0','1','0-1','1-2','2']);
load.vehicleassemblebilladdnew.timeMap.add("1",['0','1','1-2','2']);
load.vehicleassemblebilladdnew.timeMap.add("2",['0','1','2']);
load.vehicleassemblebilladdnew.timeMap.add("0-1",['0','0-1']);
load.vehicleassemblebilladdnew.timeMap.add("1-2",['0','1','1-2']);
//奖罚时间段的组合关系
load.vehicleassemblebilladdnew.timeCombinationMap = new Ext.util.HashMap();
load.vehicleassemblebilladdnew.timeCombinationMap.add("0",['0']);
load.vehicleassemblebilladdnew.timeCombinationMap.add("1",['0-1','1']);
load.vehicleassemblebilladdnew.timeCombinationMap.add("2",['0-1','1-2','2']);


//自定义方法，判断是否为合法数字
load.vehicleassemblebilladdnew.isNumber = function(value){
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

load.changeCodeToNameStore = function(store, code) {
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
Ext.define('Foss.load.vehicleassemblebilladdnew.RewardOrPunishModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id',			type:'string'},
	        {name:'rewardOrPunishType',  type:'String'},
	        {name:'timeLimit',	type:'string'},//时间段
	        {name:'agreementMoney',type:'string'},//奖励金额
	        {name:'rewardOrPunishMaxMoney' , type:'string'}]//最高上限金额
});
Ext.define('Foss.load.vehicleassemblebilladdnew.RewardOrPunishStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.vehicleassemblebilladdnew.RewardOrPunishModel',
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
Ext.define('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid',{
	extend: 'Ext.grid.Panel',
	//id:'Foss_load_vehicleassemblebilladdnew_Reward_ID',
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
    title: load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.gridTitle'),//时效条款录入
    columns:[{
    	xtype:'actioncolumn',
    	width:40,
    	text:load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.action'),//操作
    	items: [{
			iconCls: 'deppon_icons_delete',
			tooltip:load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.delete'),//删除
			handler: function(grid, rowIndex, colIndex) {	
				//记录删除行的数据
				var rowValue = grid.getStore().getAt(rowIndex).data;
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
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setValue(temp);
					
				}else if(rowValue.rewardOrPunishType =='FINE'){
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').setValue(temp);
				}
				
				if(Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').getValue()==0){
    				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(false);
    			}else{
    				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(true);
    			}
						
			}
		}]
    },{
    	header: "id",//id
		dataIndex:'id',
		hidden:true	
    },{
    	header:load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardType'),//条款类型
    	dataIndex:'rewardOrPunishType',
    	allowBlank:false,
    	renderer : function(value){
			var store = FossDataDictionary.getDataDictionaryStore(load.vehicleassemblebilladdnew.returnSubType);
			return load.changeCodeToNameStore(store,value);
		},
    	editor:{
    		xtype : 'combo',
    		queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,	
			store : FossDataDictionary.getDataDictionaryStore(load.vehicleassemblebilladdnew.returnSubType),
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
	    							var array = load.vehicleassemblebilladdnew.timeMap.get(rowValue.data.timeLimit);
	    							for(var j=0;j<array.length;j++){
	    								if(items[i].data.timeLimit == array[j]){    									
	    									Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '该时间段不能选择该奖罚类型或存在重复的协议！', 'error', 3000);
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
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setValue(temp);
		    					
		    			}else if(newValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').setValue(temp);
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
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setValue(0);
		    					
		    			}else if(oldValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').setValue(0);
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
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setValue(tempb);
		    					
		    			}else if(oldValue =='FINE'){
		    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').setValue(tempb);
		    			}
					}
    					
					if(Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').getValue()==0){
	    				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(false);
					}else{
						Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(true);
					}
    			}

    	   }
    	},
		width:350
    },{
    	header:load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.timelimit'),//时间段
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
    							var array = load.vehicleassemblebilladdnew.timeMap.get(newValue);
    							for(var j=0;j<array.length;j++){
    								if(items[i].data.timeLimit == array[j]){    									
    									Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '不能选择该时间段！', 'error', 3000);
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
    	header:load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.agreementMoney'),//奖励金额
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
	    				load.vehicleassemblebilladdnew.money = newValue;
    				}else{
    					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '不能输入超过8位的条款金额！', 'error', 3000);
    					field.setValue(load.vehicleassemblebilladdnew.money);
    					temp = load.vehicleassemblebilladdnew.money;
    					for(var i=0;i<items.length;i++){
	    					if(parseInt(items[i].data.agreementMoney)>temp&&
	    						(items[i].internalId != rowValue.internalId )&&
	    						rowValue.data.rewardOrPunishType == items[i].data.rewardOrPunishType){
	    							
	    						temp=parseInt(items[i].data.agreementMoney);
	    					}
	    				}
    				
    				}
    				if(rowValue.data.rewardOrPunishType =='REWARD'){
	    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setValue(temp);
	    					
	    			}else if(rowValue.data.rewardOrPunishType =='FINE'){
	    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').setValue(temp);
	    			}
	    				
    				if(Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').getValue()==0){
    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(false);
    				}else{
    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(true);
    				}
    			}
    		}
		},
		width:300
    }],
	tbar : [{
			   xtype : 'button',
			   text : load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.add'),//新增
			   handler:function(){
				   var record = Ext.create('Foss.load.vehicleassemblebilladdnew.RewardOrPunishModel', {
					   
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
			id : 'Foss_load_vehicleassemblebilladdnew_FineMaxMoney',
			fieldLabel :load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.finMaxMoney'),//本次惩罚封顶费用
			columnWidth : 1/2,
			labelWidth : 120,
			xtype : 'numberfield',
			value: 0
		} , {
			id : 'Foss_load_vehicleassemblebilladdnew_RewardMaxMoney',
			fieldLabel :load.vehicleassemblebilladdnew.i18n('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid.rewardMaxMoney'),//本次奖励封顶费用
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
			me.store = Ext.create('Foss.load.vehicleassemblebilladdnew.RewardOrPunishStore');
			me.plugins=[
				me.getEditor()
			];
			me.callParent([cfg]);
    }
   
});

// 配载单基本信息form
Ext.define('Foss.load.vehicleassemblebilladdnew.AddNewForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.formTitle')/*'配载单基本信息'*/,
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
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.assembleType')/*'配载类型'*/,
		name : 'assembleType',
		allowBlank : false,
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
	    }),
	    listeners : {
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
	    		basicInfoForm.findField('vehicleNo').reset();
	    		var destOrgCmp = basicInfoForm.findField('destOrgCode');
	    		destOrgCmp.reset();
	    		if(newValue == 'OWNER_LINE'){
	    			basicInfoForm.findField('driverName').setReadOnly(true);
	    			basicInfoForm.findField('frequencyNo').setReadOnly(true);//200968
	    			destOrgCmp.store.removeAll();
        			destOrgCmp.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
								params : searchParams
							});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.salesDepartment'] = null;
					});
					destOrgCmp.store.loadPage(1);
	    		}else{
	    			basicInfoForm.findField('driverName').setReadOnly(false);
	    			//269701--LLN--2015-07-08
	    			//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
	    			basicInfoForm.findField('feeTotal').setReadOnly(true);
	    			basicInfoForm.findField('frequencyNo').setReadOnly(false);//200968
	    			destOrgCmp.store.removeAll();
        			destOrgCmp.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
								params : searchParams
							});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.salesDepartment'] = 'Y';
					});
					destOrgCmp.store.loadPage(1);
	    		}
	    	}
	    }
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/,
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		salesDepartment : 'Y',
		allowBlank : false,
		listeners : {
			'blur' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
				//获取输入的到达部门编码
				var outfieldCode = cmp.getValue();
				//获取是否为整车
				var assembleTypeCmp = basicInfoForm.findField('assembleType');
				var isCarLoad;
				if(assembleTypeCmp.getValue() == 'CAR_LOAD'){
					isCarLoad = 'Y';
				}else if(assembleTypeCmp.getValue() == 'OWNER_LINE'){
					isCarLoad = 'N';
				}else{
					isCarLoad = null;
				}
				if(!Ext.isEmpty(outfieldCode)){
					//该请求首先获取到达部门是否支持自动分拣，以此来控制“货物种类”、净空载重等
					//若出发日期、配载类型不为空，则同时后台生成车次号返回前台
					Ext.Ajax.request({
						url : load.realPath('someCmpLostFocus.action'),
						jsonData : {
							'vehicleAssembleBillVo' : {
								'outfieldCode' : outfieldCode,
								'leaveTime' : basicInfoForm.findField('leaveTime').getValue(),
								'isCarLoad' : isCarLoad,
								'assembleType' : assembleTypeCmp.getValue()
							}
						},
						success : function(response){
							var result = Ext.decode(response.responseText);
							var outfield = result.vehicleAssembleBillVo.outfield;
							var vehicleAssembleNo = result.vehicleAssembleBillVo.vehicleAssembleNo;
							if(vehicleAssembleNo != null){
								//如果已添加了包交接单，则添加“KD-”前缀
								var billGrid = load.vehicleassemblebilladdnew.mainPageGrid,
									billStore = billGrid.store;
							    if(billStore.getCount() != 0 &&( billStore.getAt(0).data.handOverBillNo.substring(0,2)==("ky")||billStore.getAt(0).data.handOverBillNo.substring(0,3)==("KYB"))){
							    	vehicleAssembleNo = 'KDY-' + vehicleAssembleNo;
							    }else{
								if(billStore.getCount() != 0 && billStore.getAt(0).data.bePackage == 'Y'){
									vehicleAssembleNo = 'KD-' + vehicleAssembleNo;
								}
							    }
								//填充配载车次号
								basicInfoForm.findField('vehicleAssembleNo').setValue(vehicleAssembleNo);
							}
							//根据返回的外场是否支持自动分拣来控制“货物类型”
							if(outfield != null){
								//如果支持自动分拣
								if(outfield.sortingMachine == 'Y'){
									basicInfoForm.findField('goodsType').setReadOnly(false);
									//额定载重、额定净空可编辑
									basicInfoForm.findField('ratedLoad').setReadOnly(false);
									basicInfoForm.findField('ratedClearance').setReadOnly(false);
								}else{
									basicInfoForm.findField('goodsType').setReadOnly(true);
									basicInfoForm.findField('goodsType').setValue('ALL');
									//额定载重、额定净空不可编辑
									basicInfoForm.findField('ratedLoad').setReadOnly(true);
									basicInfoForm.findField('ratedClearance').setReadOnly(true);
								}
							}else{
								basicInfoForm.findField('goodsType').setReadOnly(true);
								basicInfoForm.findField('goodsType').setValue('ALL');
								//额定载重、额定净空不可编辑
								basicInfoForm.findField('ratedLoad').setReadOnly(true);
								basicInfoForm.findField('ratedClearance').setReadOnly(true);
							}
						},
						exception : function(response){
							var result = Ext.decode(response.responseText);
			    			top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,result.message);
			    			basicInfoForm.findField('vehicleAssembleNo').reset();
			    			cmp.reset();
			    			cmp.focus();
						}
					});
					if(load.vehicleassemblebilladdnew.destOrgCode){
						if(outfieldCode != load.vehicleassemblebilladdnew.destOrgCode){
							//触发“车牌号“失去焦点事件
							var vehicleNoCmp = basicInfoForm.findField('vehicleNo');
							vehicleNoCmp.fireEvent('blur',vehicleNoCmp);
							load.vehicleassemblebilladdnew.destOrgCode = outfieldCode;
						}
					}
					if(outfieldCode==basicInfoForm.findField('onTheWayDestOrgCode').getValue()){
						top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
								"中途到达部门不能与最终到达部门一致！");
						basicInfoForm.findField('onTheWayDestOrgCode').reset();
						basicInfoForm.findField('onTheWayDestOrgCode').focus();
					}
				}else{
					basicInfoForm.findField('vehicleAssembleNo').setValue(null);
				}
			}
		}
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.leaveTime')/*'出发日期'*/,
		name : 'leaveTime',
		xtype : 'datefield',
		allowBlank : false,
		format : 'Y-m-d',
		editable : false,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
	    		var destOrgCmp = basicInfoForm.findField('destOrgCode');
	    		//触发”到达部门“控件的焦点失去事件，重新生成配载车次号
	    		destOrgCmp.fireEvent('blur',destOrgCmp);
			}
		}
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleAssembleNo')/*'配载车次号'*/,
		name : 'vehicleAssembleNo',
		allowBlank : false,
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/,
		allowBlank : false,
		xtype : 'commontruckselector',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		isOwnTruck:'Y',
		//vehicleTypes:'vehicletype_rqsvc,vehicletype_tractors,vehicletype_van',
		vehicleTypes:'vehicletype_rqsvc,vehicletype_tractors,vehicletype_van,vehicletype_trailer',//200968
		name : 'vehicleNo',
		listeners : {//监听车牌号控件失去焦点事件
			'blur' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
				var destOrgCmp = basicInfoForm.findField('destOrgCode');
				var leaveTimeCmp = basicInfoForm.findField('leaveTime');
				var assembleTypeCmp = basicInfoForm.findField('assembleType');
				//必须先输入到达部门
				if(!destOrgCmp.isValid() || Ext.isEmpty(destOrgCmp.getValue())){
					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '请先输入到达部门！', 'error', 3000);
					cmp.reset();
					destOrgCmp.focus();
					return;
				}
				//必须输入发车日期
				if(!leaveTimeCmp.isValid() || Ext.isEmpty(leaveTimeCmp.getValue())){
					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '请先输入发车日期！', 'error', 3000);
					cmp.reset();
					leaveTimeCmp.focus();
					return;
				}
				var vehicleNo = cmp.getValue();
				if(!Ext.isEmpty(vehicleNo)){
					var store = cmp.store;
					var vehicleRecord = store.findRecord('vehicleNo',vehicleNo,0,false,true,true);
					if(vehicleRecord==null){
						cmp.reset();
						return;
					}
					var assembleType = assembleTypeCmp.getValue();
					load.vehicleassemblebilladdnew.truckType = vehicleRecord.get('truckType');
					if(assembleType == 'CAR_LOAD'
						&& vehicleRecord.get('truckType') != '外请车'){
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.mustLeasedVehicleAlert'), 'error', 3000);
						cmp.setValue('');//清空车牌号
						cmp.focus(false,100);//获取输入焦点
						return;
					}
					var myMask = new Ext.LoadMask(load.vehicleassemblebilladdnew.addNewForm, {
						msg : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadingFeeAndVehicle')
					});
					myMask.show();
					Ext.Ajax.request({
						url : load.realPath('queryVehicleInfoByVehicleNo.action'),
						jsonData : {
							'vehicleAssembleBillVo' : {
								'vehicleNo' : vehicleNo,
								'leaveTime' : leaveTimeCmp.getValue(),
								'destDeptCode' : destOrgCmp.getValue(),
								'assembleType' : basicInfoForm.findField('assembleType').getValue(),
								'transProperty' : basicInfoForm.findField('transProperty').getValue()
							}
						},
						success : function(response){
							var result = Ext.decode(response.responseText);
							var vehicleInfo = result.vehicleAssembleBillVo.vehicleInfo;
							if(vehicleInfo == null){
								Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '车牌号不存在，请重新输入!', 'error', 3000);
								cmp.setValue('');//清空车牌号
								cmp.focus(false,100);//获取输入焦点
								return;
							}else{
								var vehicleAssembleNo =result.vehicleAssembleBillVo.vehicleAssembleNo;
								if(!Ext.isEmpty(vehicleAssembleNo)){
									Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"车牌号"+vehicleNo+",配载单"+vehicleAssembleNo+"，已经勾选只装不卸且未发车，是否确认再次配载！", function(optional){
		    								if(optional != 'no'){
		    									load.LoadVehicleInfo(result,basicInfoForm,vehicleInfo);
		    								}else{
		    									cmp.setValue('');//清空车牌号
		    									cmp.focus(false,100);//获取输入焦点
		    								}
										});
									
								}else{
									load.LoadVehicleInfo(result,basicInfoForm,vehicleInfo);
								}
							}
							myMask.hide();
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				//设置默认挂牌号
		    				load.vehicleassemblebilladdnew.trailerVehicleNo=null;
		    				top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.queryVehicleInfoFailureAlertPrefix')/*'读取车辆信息失败，'*/ + result.message + '请重新选择车辆！');
		    				cmp.reset();
		    				cmp.focus();
		    				myMask.hide();
			    		},
			    		failure : function(){
			    			//设置默认挂牌号
			    			load.vehicleassemblebilladdnew.trailerVehicleNo=null;
			    			console.log('读取车辆信息及运费信息时服务端异常！');
			    			myMask.hide();
			    		}
					});
				}else{
					//初始化挂牌号
					load.vehicleassemblebilladdnew.trailerVehicleNo=null;
				}
			},
			'focus' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
				var destOrgCmp = basicInfoForm.findField('destOrgCode');
				var leaveTimeCmp = basicInfoForm.findField('leaveTime');
				//必须先输入到达部门
				if(!destOrgCmp.isValid() || Ext.isEmpty(destOrgCmp.getValue())){
					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '请先输入到达部门！', 'error', 3000);
					cmp.setValue(null);
					destOrgCmp.focus();
					return;
				}
				//必须输入发车日期
				if(!leaveTimeCmp.isValid() || Ext.isEmpty(leaveTimeCmp.getValue())){
					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '请先输入发车日期！', 'error', 3000);
					cmp.setValue(null);
					leaveTimeCmp.focus();
					return;
				}
			}
		}
	},  {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverName')/*'主驾驶姓名'*/,
		allowBlank : false,
		name : 'driverName',
		xtype : 'commondriverselector',
		readOnly : true,
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
					if(load.vehicleassemblebilladdnew.truckType == '外请车') {
						if(record.get('driverType') == '公司司机') {
							Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '外请车必须用外请司机!', 'error', 3000);
							basicInfoForm.findField('driverCode').reset();
							cmp.reset();
							return;
						}
					} else if (load.vehicleassemblebilladdnew.truckType == '公司车') {
						if(record.get('driverType') == '外请司机') {
							Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '公司车必须用公司司机!', 'error', 3000);
							basicInfoForm.findField('driverCode').reset();
							cmp.reset();
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
		readOnly : true,
		hidden : true
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverMobilePhone')/*'司机电话'*/,
		name : 'driverMobilePhone',
		maxLength:13,
		allowBlank : false
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleModel')/*'车型'*/,
		name : 'vehicleModel',
		readOnly : true
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.containerNo')/*'货柜编号'*/,
		xtype : 'commonowntruckselector',
		name : 'containerNo',
		displayField : 'containerCode',
		valueField : 'containerCode',
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		name : 'containerNo',
		hidden : true,
		allowBlank : false,
		listeners : {
			'blur' : function(cmp,eo,eOpts){
				var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
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
								Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '货柜编号不存在，请重新输入!', 'error', 3000);
								cmp.reset();//清空车牌号
								cmp.focus(false,100);//获取输入焦点
							}else{
								//额定载重、额定净空读取车辆载重、车辆净空
								basicInfoForm.findField('ratedClearance').setValue(containerInfo.selfVolume);
								//填充车辆信息
								var vehicleMessage = load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
									+ containerInfo.deadLoad 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
									+ '，' 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
									+ containerInfo.selfVolume 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
									+ '，' 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
									+ containerInfo.vehicleHeight/100 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
									+ '，' 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
									+ containerInfo.vehicleWidth/100 
									+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
								basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
								//车辆净空
								load.vehicleassemblebilladdnew.tempVehicleLoadVolume = containerInfo.selfVolume;
							}
						}
					});
				}
			}
		}
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.frequencyNo')/*'卡车班次'*/,
		allowBlank : false,
		readOnly : true, //200968
		name : 'frequencyNo'
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.transProperty')/*'运输性质'*/,
		allowBlank : false,
		xtype : 'combobox',
		name : 'transProperty',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
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
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var form = this.up('form').getForm();
	    		//当“到达部门”、“发车日期”、“车牌号”均不为空、且“配载类型”不为整车时，访问后台，获取“运行时数”
	    		var assembleType = form.findField('assembleType').getValue();
	    		if(assembleType != 'CAR_LOAD'){
	    			var destOrgCode = form.findField('destOrgCode').getValue(),
		    		leaveTime = form.findField('leaveTime').getValue(),
		    		vehicleNo = form.findField('vehicleNo').getValue();
		    		if(!Ext.isEmpty(destOrgCode) && !Ext.isEmpty(leaveTime) && !Ext.isEmpty(vehicleNo)){
		    			Ext.Ajax.request({
							url : load.realPath('queryRunHoursByTransProperty.action'),
							jsonData : {
								'vehicleAssembleBillVo' : {
									'destDeptCode' : destOrgCode,
									'leaveTime' : leaveTime,
									'vehicleNo' : vehicleNo,
									'transProperty' : newValue
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
								form.findField('vehicleNo').reset();
								top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,result.message);
							}
						});
		    		}
	    		}
	    	}
	    }
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedLoad')/*'额定载重'*/,
		xtype : 'numberfield',
		allowBlank : false,
		readOnly : true,
		minValue:0,
		name : 'ratedLoad',
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
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ratedClearance')/*'额定净空'*/,
		xtype : 'numberfield',
		allowBlank : false,
		readOnly : true,
		minValue:0,
		name : 'ratedClearance',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				form.findField('examineVolume').setValue(newValue);
				var driverOfVolumn =form.findField('driverOfVolumn').getValue();
				if(driverOfVolumn==null){
					driverOfVolumn=0;
				}
				if(newValue==null){
					form.findField('applicationRatedClearance').setValue(0);
				}else{
					form.findField('applicationRatedClearance').setValue(newValue-driverOfVolumn);
				}
				
			}
		}
	},{
		fieldLabel :load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.driverOfGoods')/*'司机自带货量'*/,
		xtype : 'numberfield',
		hidden:true,
		value:0,
		minValue:0,
		maxValue:9999999.99,
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
		hidden:true,
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
		hidden:true,
		id:'Foss_load_vehicleassemblebilladdnew_m³',
		margin:'10 0 5 0',
		html: 'm³'
	},{
		fieldLabel :load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedLoad')/*'适用载重'*/,
		xtype : 'numberfield',
		labelWidth : 60,
		allowBlank : false,
		hidden:true,
		readOnly : true,
		margin:'5 10 5 60',
		name : 'applicationRatedLoad'
		
	},{
		fieldLabel :load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.applicationRatedClearance')/*'适用净空'*/,
		xtype : 'numberfield',
		labelWidth : 60,
		allowBlank : false,
		hidden:true,
		readOnly : true,
		margin:'5 10 5 60',
		name : 'applicationRatedClearance'
		
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.examineVolume')/*'考核体积'*/,
		name : 'examineVolume',
		readOnly : true,
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm();
				if(!load.vehicleassemblebilladdnew.isNumber(newValue)){
					form.findField('loadingRate').setValue(0.00);
					return;
				}
				//获取总体积
				var totalVolume = Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalVolume').getValue();
				//计算装载率
				var loadingRate = totalVolume/newValue;
				if(!load.vehicleassemblebilladdnew.isNumber(loadingRate)){
					form.findField('loadingRate').setValue(0.00);
				}else{
					form.findField('loadingRate').setValue(loadingRate.toFixed(2));
				}
			}
		}
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadingRate')/*'装载率'*/,
		name : 'loadingRate',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleOwnerShip')/*'车辆所有权类别'*/,
		name : 'vehicleOwnerShip',
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
	    }),
		readOnly : true
	},  {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.runHours')/*'运行时数(小时)'*/,
		name : 'runHours',
		readOnly : true
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.createUser')/*'制单人'*/,
		name : 'createUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.modifyUser')/*'修改人'*/,
		name : 'modifyUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleMessage')/*'车辆信息'*/,
		name : 'vehicleMessage',
		readOnly : true,
		columnWidth : .5,
		fieldStyle : 'font-weight:bold'
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/,
		name : 'note',
		columnWidth : .5,
		margin : '5 10 0 10',
		maxLength : 300
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.goodsType')/*'货物类型'*/,	//此控件是否可编辑需要根据到达部门是否自动分拣来判断，
		allowBlank : false,
		margin : '5 10 0 10',
		xtype : 'combobox',
		name : 'goodsType',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    readOnly : true,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"A_TYPE", "value":"A类"},
	            {"key":"B_TYPE", "value":"B类"}
	        ]
	    })
	}, {
		boxLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayLoad')/*'是否在途装卸'*/,
		name : 'beMidWayLoad',
		margin : '5 10 0 10',
		xtype : 'checkbox',
		columnWidth : .12,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = load.vehicleassemblebilladdnew.addNewForm.getForm();
				
				//重置总运费修改原因
				var reasonCmp = form.findField('feeTotalChangeNotes');
				reasonCmp.setDisabled(true);
				reasonCmp.reset();
				reasonCmp.setVisible(false);
				
				//如果勾选在途装卸，则显示“是否最终到达”，并且“额定载重”“额定净空”可修改 midWayLoadType
				if(newValue){
					
					form.findField('beInLTLCar').setValue(false);
					
					form.findField('midWayLoadType').setVisible(true);
					form.findField('beFinallyArrive').setVisible(true);
					form.findField('ratedLoad').setReadOnly(false);
					form.findField('ratedClearance').setReadOnly(false);
					form.findField('beMidWayOnlyLoad').setVisible(true);
					if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
						form.findField('feeTotal').setValue(0);
						form.findField('prePaidFeeTotal').setValue(0);
						form.findField('arriveFeeTotal').setValue(0);
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
						Ext.Msg.show({
								     title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
								     msg : "若此次配载为首次配载，不能勾选在途装卸，否则将无法生成整车或者外请车应付单，无法与司机结款！",
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.WARNING
									});
					}else{
						form.findField('feeTotal').setValue(0);
						form.findField('prePaidFeeTotal').reset();
						form.findField('arriveFeeTotal').reset();
						form.findField('feeTotal').setReadOnly(true);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setReadOnly(true);
				}
				if(form.findField('beTimeLiness').getValue() && !form.findField('beFinallyArrive').getValue() ){
					Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"签署了时效条款不能选择在途卸且非最终到达!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
											'error', 
											2000
											);
						return form.findField('beMidWayLoad').setValue(false);
				}
			}else{
				form.findField('midWayLoadType').setVisible(false);
				form.findField('midWayLoadType').setValue();
				
				form.findField('beMidWayOnlyLoad').setVisible(false);
				form.findField('beMidWayOnlyLoad').setValue(false);
				
				
				form.findField('beFinallyArrive').setVisible(false);
				form.findField('beFinallyArrive').setValue(false);
				form.findField('ratedLoad').setReadOnly(true);
				form.findField('ratedClearance').setReadOnly(true);
				//获取车辆所有权的值
				if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
					//如果为现金付款 
					if(form.findField('paymentType').getValue() == 'CH'
						|| form.findField('paymentType').getValue() == 'TT'){
						var totalFee = load.vehicleassemblebilladdnew.tempTotalFee;
						//总运费
						form.findField('feeTotal').setValue(totalFee);
						// 269701--LLN--2015--07--08 begin
						//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
						if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
							form.findField('feeTotal').setReadOnly(true);
						}else{
							form.findField('feeTotal').setReadOnly(false);
						}
						// 269701--LLN--2015--07--08 end
						//预付运费总额
						//var prePaidFeeTotal = Math.round(totalFee/300)*100;
						//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*						var prePaidFeeTotal = Math.floor(totalFee/300)*100;
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
					var totalFee = load.vehicleassemblebilladdnew.tempTotalFee;
					//总运费
					form.findField('feeTotal').setValue(totalFee);
					form.findField('feeTotal').setReadOnly(true);
					//预付运费总额
					form.findField('prePaidFeeTotal').reset();
					//到付运费总额
					form.findField('arriveFeeTotal').reset();
				}
			}
			//重新设置载重、净空
			form.findField('ratedLoad').setValue(load.vehicleassemblebilladdnew.tempVehicleLoadWeight);
			form.findField('ratedClearance').setValue(load.vehicleassemblebilladdnew.tempVehicleLoadVolume);
			}
		}
	}, {
		boxLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beFinallyArrive')/*'是否最终到达'*/,
		name : 'beFinallyArrive',
		margin : '5 10 0 10',
		xtype : 'checkbox',
		columnWidth : .13,
		hidden : true,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				
				//重置总运费修改原因
				var reasonCmp = form.findField('feeTotalChangeNotes');
				reasonCmp.setDisabled(true);
				reasonCmp.reset();
				reasonCmp.setVisible(false);
				var totalFee = load.vehicleassemblebilladdnew.tempTotalFee;
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
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							//总运费
							form.findField('feeTotal').setValue(totalFee);
							// 269701--LLN--2015--07--08 begin
							//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								form.findField('feeTotal').setReadOnly(true);
							}else{
								form.findField('feeTotal').setReadOnly(false);
							}
							// 269701--LLN--2015--07--08 end
							//预付运费总额
							// var prePaidFeeTotal = Math.round(totalFee/300)*100;
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.round(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//重置总运费
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
					//如果取消最终到达则取消在途只装不卸
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
						Ext.Msg.show({
								     title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
								     msg : "若此次配载为首次配载，不能勾选在途装卸，否则将无法生成整车或者外请车应付单，无法与司机结款！",
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.WARNING
									});
//						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
//											"若此次配载为首次配载，不能勾选在途装卸，否则将无法生成整车或者外请车应付单，无法与司机结款！", 
//											'error', 
//											2000
//											);
					}
					if(form.findField('beTimeLiness').getValue() && form.findField('beMidWayLoad').getValue() ){
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
		boxLabel :'是否与零担合车',//load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beInLTLCar')/*'是否与零担合车'*/,
		name : 'beInLTLCar',
		margin : '5 10 0 10',
		xtype : 'checkbox',
		//columnWidth : .13,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
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
					var totalFee = load.vehicleassemblebilladdnew.tempTotalFee;
					//获取车辆所有权的值
					if(form.findField('vehicleOwnerShip').getValue() == 'Leased'){
						//如果为现金付款
						if(form.findField('paymentType').getValue() == 'CH'
							|| form.findField('paymentType').getValue() == 'TT'){
							//总运费ddd
							form.findField('feeTotal').setValue(totalFee);
							//269701---lln--2015-07-08
							//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								form.findField('feeTotal').setReadOnly(true);
							}else{
								form.findField('feeTotal').setReadOnly(false);
							}
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.round(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//与零担和车否
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
							form.findField('prePaidFeeTotal').setReadOnly(false);//ddddd
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
		boxLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beMidWayOnlyLoad')/*'是否在途只装不卸'*/,
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
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.onTheWayDestOrgCode')/*'中途到达部门'*/,
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
					top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
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
		boxLabel :load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beCarSmallUsed')/*'是否大车小用'*/,
		name : 'beCarSmallUsed',
		xtype : 'checkbox',
		hidden : true
	},{
		xtype : 'container',
		html : '<hr/>',
		columnWidth : 1
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.paymentType')/*'付款方式'*/,
		name : 'paymentType',
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
	            {"key":"TT", "value":"电汇"}
	        ]
	    }),
		margin : '0 10 5 10',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts) {
				var form = this.up('form').getForm();
				//重置“总运费修改原因”
				var reasonCmp = form.findField('feeTotalChangeNotes');
				reasonCmp.setDisabled(true);
				reasonCmp.reset();
				reasonCmp.setVisible(false);
				if (form.findField('vehicleOwnerShip').getValue() == 'Leased') {
					// 如果为月结，则将首款、尾款全部置为0，而且不可编辑
					if (newValue == 'CT') {
						form.findField('feeTotal').setValue(load.vehicleassemblebilladdnew.tempTotalFee);
						form.findField('prePaidFeeTotal').setValue(0);
						form.findField('prePaidFeeTotal').setReadOnly(true);
						form.findField('arriveFeeTotal').setValue(0);
					} else {
						if (!form.findField('beMidWayLoad').getValue()
								|| (form.findField('beMidWayLoad').getValue() && form.findField('beFinallyArrive').getValue())) {
							var totalFee = load.vehicleassemblebilladdnew.tempTotalFee;
							// 总运费
							form.findField('feeTotal').setValue(totalFee);
							// 269701--LLN--2015--07--08 begin
							//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
							if(form.findField('assembleType').getValue() == 'CAR_LOAD'){
								form.findField('feeTotal').setReadOnly(true);
							}else{
								form.findField('feeTotal').setReadOnly(false);
							}
							// 269701--LLN--2015--07--08 end
							//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*							var prePaidFeeTotal = Math.round(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
							
							// 276198--duh--2015--09--06
							//重置总运费
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
							form.findField('arriveFeeTotal').setValue(totalFee - prePaidFeeTotal);
							form.findField('prePaidFeeTotal').setReadOnly(false);
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
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.feeTotal')/*'总运费'*/,
		margin : '0 10 5 10',
		name : 'feeTotal',
		xtype : 'numberfield',
		maxLength : 8,
		step : 100,
		readOnly : true,
		minValue : 0,
		listeners : {
			'blur' : function(field){
				var form = this.up('form').getForm(),
					totalFee = field.getValue();
				if(field.isValid() && form.findField('vehicleOwnerShip').getValue() == 'Leased'
					&& (form.findField('paymentType').getValue() == 'CH'
						|| form.findField('paymentType').getValue() == 'TT')
					&&(!form.findField('beMidWayLoad').getValue()
						|| (form.findField('beMidWayLoad').getValue()
								&& form.findField('beFinallyArrive').getValue()))){
					//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能ddd（不了解）
/*					var prePaidFeeTotal = Math.round(totalFee/300)*100;
							form.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);*/
					
					// 276198--duh--2015--09--06
					//重置总运费不了解
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
					
					form.findField('arriveFeeTotal').setValue(totalFee - prePaidFeeTotal);
					var reasonCmp = form.findField('feeTotalChangeNotes');
					//如果此处总运费不等于请车价格，则必须输入修改原因；
					if(totalFee !== load.vehicleassemblebilladdnew.tempTotalFee){
						reasonCmp.setDisabled(false);
						reasonCmp.reset();
						reasonCmp.setVisible(true);
					}else{
						reasonCmp.setDisabled(true);
						reasonCmp.reset();
						reasonCmp.setVisible(false);
					}
				}
			}
		}
	},{
		name : 'inviteNo',
		hidden : true,
		fieldLabel : '约车编号'
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotal')/*'预付运费总额'*/,
		margin : '0 10 5 10',
		name : 'prePaidFeeTotal',
		xtype : 'numberfield',
		step : 100,
		minValue : 0,
		validator : function(value){
			var form = this.up('form').getForm();
			var assembleType = form.findField('assembleType').getValue();
			//预付运费总额必须大于0
			if(value < 0){
				return load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip1')/*'预付运费总额必须大于0'*/;
			}
			//必须为100的整数倍
/*			if(value%100 != 0){
				return load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip2')'预付运费总额必须为100的整数倍';
			}*/
			// 必须小于等于总运费2/3
			if(assembleType=='CAR_LOAD'&&(value > Math.floor(form.findField('feeTotal').getValue()*2/3))){
				return load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip3')/*'预付运费总额必须小于等于总运费2/3'*/;
			}
			//必须小于等于总运费1/3  duh
			if(assembleType=='OWNER_LINE'&&(value > Math.floor(form.findField('feeTotal').getValue()*1/3))){
				return load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.prePaidFeeTotalToolTip4')/*'预付运费总额必须小于等于总运费1/3'*/;
			}if(assembleType=='OWNER_LINE'&&(value > form.findField('feeTotal').getValue())){
				return load.vehicleassemblebilladdnew.i18n('预付运费总额必须小于等于总运费')/*'预付运费总额必须小于等于总运费'*/;
			}
			return true;
		},
		listeners : {
			'blur' : function(cmp,eO,eOpts){
				var form = this.up('form').getForm();
				if(cmp.isValid() && form.findField('vehicleOwnerShip').getValue() == 'Leased'
					&& (form.findField('paymentType').getValue() == 'CH' || form.findField('paymentType').getValue() == 'TT')){
					form.findField('arriveFeeTotal').setValue(form.findField('feeTotal').getValue() - cmp.getValue());
				}
			}
		}
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.arriveFeeTotal')/*'到付运费总额'*/,
		margin : '0 10 5 10',
		name : 'arriveFeeTotal',
		xtype : 'numberfield',
		step : 100,
		readOnly : true,
		minValue : 0
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.loadFeeTotal')/*'装车总金额'*/,
		name : 'loadFeeTotal',
		xtype : 'numberfield',
		value : 0,
		readOnly : true
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.collectionFeeTotal')/*'代收货款'*/,
		name : 'collectionFeeTotal',
		xtype : 'numberfield',
		value : 0,
		readOnly : true
	}, {
		boxLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beReturnReceipt')/*'是否押回单'*/,
		name : 'beReturnReceipt',
		xtype : 'checkbox',
		columnWidth : .15
	}, {
		boxLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.beTimeLiness')/*'是否签署效条款'*/,
		name : 'beTimeLiness',
		xtype : 'checkbox',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var selectResultPanel = load.vehicleassemblebilladdnew.RewardOrPunishGrid;
				if(newValue){
					var form = this.up('form').getForm();
					var destOrgCode = form.findField('destOrgCode').getValue();
					var vehicleType = form.findField('vehicleOwnerShip').getValue();
					var assembleType = form.findField('assembleType').getValue();
					var beFinallyArrive = form.findField('beFinallyArrive').getValue();
					var beMidWayLoad = form.findField('beMidWayLoad').getValue();
					var beInLTLCar = form.findField('beInLTLCar').getValue();
					
					if(vehicleType=='' || assembleType=='' || vehicleType==null||assembleType==null){
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
											"请先输入配载基本信息!",//'请先输入配载基本信息!', 
											'error', 
											2000
											);
						return field.setValue(false);
						
					}
					if(!((vehicleType=='Leased' && assembleType=='OWNER_LINE')&& ((beFinallyArrive && beMidWayLoad)||(!beFinallyArrive && !beMidWayLoad)))){
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
					//显示奖罚协议grid
					selectResultPanel.setVisible(true);
					if(Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').getValue()==0){
    					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').setVisible(false);
    				}
				}else{
					//隐藏奖罚协议grid
					selectResultPanel.setVisible(false);
				}
			}
		}
		
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.feeTotalChangeNotes')/*'总运费修改原因'*/,
		name : 'feeTotalChangeNotes',
		columnWidth : .85,
		hidden : true,
		disabled : true,
		maxLength : 300,
		allowBlank : false
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//车牌号变动 调用方法
load.LoadVehicleInfo=function(result,basicInfoForm,vehicleInfo){

	//填充司机、司机电话、班次
	var driverCode = result.vehicleAssembleBillVo.driverCode,
	driverName = result.vehicleAssembleBillVo.driverName,
	driverTel = result.vehicleAssembleBillVo.driverTel,
	frequencyNo = result.vehicleAssembleBillVo.frequencyNo,
	runHours = result.vehicleAssembleBillVo.runHours;
	//获取挂牌号
	load.vehicleassemblebilladdnew.trailerVehicleNo=result.vehicleAssembleBillVo.trailerVehicleNo;
	basicInfoForm.findField('driverCode').setValue(driverCode);
	basicInfoForm.findField('driverName').setValue(driverName);
	basicInfoForm.findField('driverMobilePhone').setValue(driverTel);
	basicInfoForm.findField('frequencyNo').setValue(frequencyNo);
	//运行时数
	if(!Ext.isEmpty(runHours)){
		basicInfoForm.findField('runHours').setValue(runHours);
	}
	//填充货柜号
	var containerNo =  result.vehicleAssembleBillVo.containerNo;
	var containerNoCmp = basicInfoForm.findField('containerNo');
	containerNoCmp.setValue(containerNo);
	containerNoCmp.fireEvent('blur',containerNoCmp);
	console.log(vehicleInfo);
	//填充车辆所有权类别
	basicInfoForm.findField('vehicleOwnerShip').setValue(vehicleInfo.vehicleOwnershipType);
	//车辆载重
	load.vehicleassemblebilladdnew.tempVehicleLoadWeight = vehicleInfo.vehicleDeadLoad;
	basicInfoForm.findField('ratedLoad').setValue(vehicleInfo.vehicleDeadLoad);
	//如果是公司车，则显示货柜、隐藏车型、清空车辆信息
	if(vehicleInfo.vehicleOwnershipType == 'Company'){
		//公司车则影藏适用载重和适用净空 司机自带货量
		basicInfoForm.findField('driverOfWeight').setVisible(false);
		basicInfoForm.findField('driverOfVolumn').setVisible(false);
		basicInfoForm.findField('applicationRatedLoad').setVisible(false);
		basicInfoForm.findField('applicationRatedClearance').setVisible(false);
		basicInfoForm.findField('beCarSmallUsed').setVisible(false);
		Ext.getCmp('Foss_load_vehicleassemblebilladdnew_m³').setVisible(false);
		
		basicInfoForm.findField('feeTotal').setReadOnly(true);
		basicInfoForm.findField('feeTotal').doComponentLayout();
		//判断公司车是车头还是厢式车
		var vehicleType = vehicleInfo.vehicleType;
		//如果为车头
		if(vehicleType == 'vehicletype_tractors'){
			basicInfoForm.findField('vehicleModel').setVisible(false);
			basicInfoForm.findField('vehicleModel').setDisabled(true);
			basicInfoForm.findField('vehicleModel').reset();
			basicInfoForm.findField('containerNo').setVisible(true);
			basicInfoForm.findField('containerNo').setDisabled(false);
			basicInfoForm.findField('containerNo').setReadOnly(false);
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
			load.vehicleassemblebilladdnew.tempVehicleLoadVolume = vehicleInfo.vehicleSelfVolume;
			basicInfoForm.findField('ratedClearance').setValue(vehicleInfo.vehicleSelfVolume);
			//读取车型,填充
			basicInfoForm.findField('vehicleModel').setValue(vehicleInfo.vehicleLengthName);
			//填充车辆信息
			var vehicleMessage = load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
				+ vehicleInfo.vehicleDeadLoad 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
				+'，' 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
				+ vehicleInfo.vehicleSelfVolume 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
				+'，' 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
				+ vehicleInfo.vehicleHeight/100 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
				+ '，' 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
				+ vehicleInfo.vehicleWidth/100 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
			basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
		}
		//限制付款方式为月结，不可更改
		basicInfoForm.findField('paymentType').setValue('CT');
		basicInfoForm.findField('paymentType').setReadOnly(true);
		//填充总运费
		var vehicleFee = result.vehicleAssembleBillVo.vehicleFee;
		load.vehicleassemblebilladdnew.tempTotalFee = vehicleFee;
		//如果不是在途装卸、或者在途装卸且最终到达，则算总运费
		if((!basicInfoForm.findField('beMidWayLoad').getValue()&& !basicInfoForm.findField('beInLTLCar').getValue())
					|| (basicInfoForm.findField('beMidWayLoad').getValue() && basicInfoForm.findField('beFinallyArrive').getValue())){
			basicInfoForm.findField('feeTotal').setValue(vehicleFee);	
			
		}else{
			basicInfoForm.findField('feeTotal').setValue(0);	
		}
		basicInfoForm.findField('prePaidFeeTotal').setReadOnly(true);
		basicInfoForm.findField('prePaidFeeTotal').reset();
		basicInfoForm.findField('arriveFeeTotal').setReadOnly(true);
		basicInfoForm.findField('arriveFeeTotal').reset();
		//如果是外请车，则显示车型，隐藏货柜，读取车辆长宽载重信息、填充各控件
	}else if(vehicleInfo.vehicleOwnershipType == 'Leased'){
		// 269701--LLN--2015--07--08 begin
		//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
		if(basicInfoForm.findField('assembleType').getValue() == 'CAR_LOAD'){
			//总运费不可修改
			basicInfoForm.findField('feeTotal').setReadOnly(true);
		}else{
			//总运费可修改
			basicInfoForm.findField('feeTotal').setReadOnly(false);
		}
		// 269701--LLN--2015--07--08 end
		basicInfoForm.findField('feeTotal').doComponentLayout();
		basicInfoForm.findField('vehicleModel').setVisible(true);
		basicInfoForm.findField('vehicleModel').setDisabled(false);
		basicInfoForm.findField('containerNo').setVisible(false);
		basicInfoForm.findField('containerNo').setDisabled(true);
		basicInfoForm.findField('containerNo').setReadOnly(true);
		basicInfoForm.findField('containerNo').reset();
		//外请车则显示适用载重和适用净空 司机自带货量
		basicInfoForm.findField('driverOfWeight').setVisible(true);
		basicInfoForm.findField('driverOfVolumn').setVisible(true);
		basicInfoForm.findField('applicationRatedLoad').setVisible(true);
		basicInfoForm.findField('applicationRatedClearance').setVisible(true);
		basicInfoForm.findField('beCarSmallUsed').setVisible(true);
		Ext.getCmp('Foss_load_vehicleassemblebilladdnew_m³').setVisible(true);
		
		//根据是否合同车来显示付款方式
		if(vehicleInfo.bargainVehicle == 'Y'){//合同车可以选择现金或者月结、电汇
			basicInfoForm.findField('paymentType').setReadOnly(false);
		}else{//非合同车只能是现金
			basicInfoForm.findField('paymentType').setReadOnly(true);
			basicInfoForm.findField('paymentType').setValue('CH');
		}
		//车辆净空
		load.vehicleassemblebilladdnew.tempVehicleLoadVolume = vehicleInfo.vehicleSelfVolume;
		//额定载重、额定净空读取车辆载重、车辆净空
		basicInfoForm.findField('ratedClearance').setValue(vehicleInfo.vehicleSelfVolume);
		//读取车型,填充
		basicInfoForm.findField('vehicleModel').setValue(vehicleInfo.vehicleLengthName);
		//填充车辆信息
		var vehicleMessage = load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.deadLoadPrefix') 
			+ vehicleInfo.vehicleDeadLoad 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.ton') 
			+ '，' 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.volumePrefix') 
			+ vehicleInfo.vehicleSelfVolume 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.litre') 
			+ '，' 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleHeightPrefix') 
			+ vehicleInfo.vehicleHeight 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter') 
			+ '，' 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.vehicleWidthPrefix') 
			+ vehicleInfo.vehicleWidth 
			+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.basicInfoForm.meter');
		basicInfoForm.findField('vehicleMessage').setValue(vehicleMessage);
		//填充总运费
		var vehicleFee = result.vehicleAssembleBillVo.vehicleFee;
		load.vehicleassemblebilladdnew.tempTotalFee = vehicleFee;
		//填充外请车约车编号
		var inviteNo = result.vehicleAssembleBillVo.inviteNo;
		basicInfoForm.findField('inviteNo').setValue(inviteNo);
		//如果为非月结付款方式，则将总运费填充dddd
		if(basicInfoForm.findField('paymentType').getValue() == 'CH'
			|| basicInfoForm.findField('paymentType').getValue() == 'TT'){
			if((!basicInfoForm.findField('beMidWayLoad').getValue()&&!basicInfoForm.findField('beInLTLCar').getValue())
					|| (basicInfoForm.findField('beMidWayLoad').getValue() && basicInfoForm.findField('beFinallyArrive').getValue())){
				basicInfoForm.findField('feeTotal').setValue(vehicleFee);	
				//预付运费总额  新增配载单界面的默认预付运费总额为小于或等于总运费的1/3的功能
/*				var prePaidFeeTotal = Math.floor(vehicleFee/300)*100;
				basicInfoForm.findField('prePaidFeeTotal').setValue(prePaidFeeTotal);
				//到付运费总额
				basicInfoForm.findField('arriveFeeTotal').setValue(vehicleFee - prePaidFeeTotal);*/
				// 269701--LLN--2015--07--08 begin
				//配载类型是整车，总运费为约车中的总费用， 此时总运费不可修改；配载类型是专线时，总运费可修改；
				if(basicInfoForm.findField('assembleType').getValue() == 'CAR_LOAD'){
					basicInfoForm.findField('feeTotal').setReadOnly(true);
				}else{
					basicInfoForm.findField('feeTotal').setReadOnly(false);
				}
				
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
				
				//到付运费总额
				basicInfoForm.findField('arriveFeeTotal').setValue(vehicleFee - prePaidFeeTotal);
				
				
				// 269701--LLN--2015--07--08 end
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
//定义交接单列表Model(主页和弹出窗口共用)
Ext.define('Foss.load.vehicleassemblebilladdnew.HandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	}, {
		name : 'driver',
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
		name : 'waybillQtyTotal',
		type : 'number'
	},{
		name : 'weightTotal',
		type : 'number'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'codAmountTotal',
		type : 'number'
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'bePackage',
		type : 'string'
	}]
});

//定义主页交接单store
Ext.define('Foss.load.vehicleassemblebilladdnew.MainPageHandOverBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebilladdnew.HandOverBillModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'add' : function(store,records,index,eOpts){
			//增加后重新计算装车总金额
			var totalFeeCmp = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('loadFeeTotal');
			//增加后重新计算代收货款总额
			var codCmp = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('collectionFeeTotal');
			//增加后统计值更新
			for(var i in records){
				var record = records[i];
				//如果添加的是包交接单，但车次号不是以“KD-”打头，则重新填充配载车次号
				var vehicleAssembleNoCmp = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('vehicleAssembleNo'),
					vehicleAssembleNo = vehicleAssembleNoCmp.getValue();
				//如果添加的是商务专递接单，但车次号不是以“KDY-”打头，则重新填充配载车次号
				if(record.data.handOverBillNo.substring(0,2)==("ky")||record.data.handOverBillNo.substring(0,3)==("KYB")){
					if(!Ext.isEmpty(vehicleAssembleNo)
							&& vehicleAssembleNo.substr(0,4) != 'KDY-'){
					 vehicleAssembleNoCmp.setValue('KDY-' + vehicleAssembleNo);
					}
				}else{
					//如果添加的是非包交接单，但车次号以“KD-”打头，则需要还原车次号
					if(!Ext.isEmpty(vehicleAssembleNo)
								&& vehicleAssembleNo.substr(0,4) == 'KDY-'){
						vehicleAssembleNoCmp.setValue(vehicleAssembleNo.substring(4,vehicleAssembleNo.length + 1));
						vehicleAssembleNo=vehicleAssembleNo.substring(4,vehicleAssembleNo.length + 1);
					}
					
					if(record.data.bePackage == 'Y'){
						if(!Ext.isEmpty(vehicleAssembleNo)
								&& vehicleAssembleNo.substr(0,3) != 'KD-'){
							vehicleAssembleNoCmp.setValue('KD-' + vehicleAssembleNo);
						}
					}else{
						//如果添加的是非包交接单，但车次号以“KD-”打头，则需要还原车次号
						if(!Ext.isEmpty(vehicleAssembleNo)
								&& vehicleAssembleNo.substr(0,3) == 'KD-'){
							vehicleAssembleNoCmp.setValue(vehicleAssembleNo.substring(3,vehicleAssembleNo.length + 1));
						}
					}
				}
				
				//更新装车总金额
				totalFeeCmp.setValue(totalFeeCmp.getValue() + record.get('moneyTotal') - record.get('codAmountTotal'));
				//更新代收货款总额
				codCmp.setValue(codCmp.getValue() + record.get('codAmountTotal'));
				//更新总重量
				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWeight').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWeight').getValue() + record.get('weightTotal'));
				//更新总体积
				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalVolume').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalVolume').getValue() + record.get('volumeTotal'));
				//更新总件数
				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalPieces').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalPieces').getValue() + record.get('goodsQtyTotal'));
				//更新总票数
				Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWaybill').setValue(
						Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWaybill').getValue() + record.get('waybillQtyTotal'));
			}
		},
		'remove' : function(store,record,index,eOpts){
			//增加后重新计算装车总金额
			var totalFeeCmp = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('loadFeeTotal');
			//更新装车总金额
			totalFeeCmp.setValue(totalFeeCmp.getValue() - record.get('moneyTotal') + record.get('codAmountTotal'));
			//增加后重新计算代收货款总额
			var codCmp = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('collectionFeeTotal');
			//更新代收货款总额
			codCmp.setValue(codCmp.getValue() - record.get('codAmountTotal'));
			//更新总重量
			Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWeight').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWeight').getValue() - record.get('weightTotal'));
			//更新总体积
			Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalVolume').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalVolume').getValue() - record.get('volumeTotal'));
			//更新总件数
			Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalPieces').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalPieces').getValue() - record.get('goodsQtyTotal'));
			//更新总票数
			Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWaybill').setValue(
					Ext.getCmp('Foss_load_vehicleassemblebilladdnew_TotalWaybill').getValue() - record.get('waybillQtyTotal'));
		}
	}
});

//定义主页交接单列表
Ext.define('Foss.load.vehicleassemblebilladdnew.MainPageHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	//load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.gridTitle')
	frame : true,
	title :'交接单列表<font style="margin-left:200px;color:red;font-size:13px;">提示：快递与零担合车，配载时，必须先配载快递货，再配载零担货！</font>' /*'交接单列表'*/,
	height : 350,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.vehicleassemblebilladdnew.MainPageHandOverBillStore'),
	tbar : [{
		xtype : 'button',
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.addBillButton')/*'添加交接单'*/,
		handler : function() {
			load.vehicleassemblebilladdnew.queryWindow.show();
			var addNewForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
			var queryForm = load.vehicleassemblebilladdnew.queryForm.getForm();
			var destOrgCmp = addNewForm.findField('destOrgCode');
			var destOrgStore = destOrgCmp.store;
			if(destOrgStore != null){
				var destOrgName = destOrgStore.findRecord('code',destOrgCmp.getValue(),0,false,true,true);
				queryForm.findField('arriveDept').setValue(destOrgName);
				queryForm.findField('arriveDeptCode').setValue(destOrgCmp.getValue());
			}
			var vehicleNo = addNewForm.findField('vehicleNo').getValue();
			queryForm.findField('vehicleNo').setValue(vehicleNo);
		}
	},'->',{
		xtype : 'button',
		disabled: !load.vehicleassemblebilladdnew.isPermission('load/printvehicleassemblebillButton'),
		hidden: !load.vehicleassemblebilladdnew.isPermission('load/printvehicleassemblebillButton'),
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.printButton')/*'打印'*/,
		handler : function() {
			if(load.vehicleassemblebilladdnew.prtvehicleAssembleNo == undefined) {
				Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						"请先保存配载单!", 
						'error');
				return;
			}
			var records = this.up('grid').getStore().getRange();
			if(records.length == 0){
				Ext.MessageBox.show({
					title:"提示",
					msg:"请先选择您要操作的行!"
				});
				return;
			}
			//如果选择的配载单属于多个不同的车牌则不能打印
			var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm(),
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
						Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
		disabled: !load.vehicleassemblebilladdnew.isPermission('load/printTransportProtocolButton'),
		hidden: !load.vehicleassemblebilladdnew.isPermission('load/printTransportProtocolButton'),
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.printBarginBillButton')/*'打印运输合同'*/,
		handler : function(){
			if(load.vehicleassemblebilladdnew.prtvehicleAssembleNo == undefined) {
				Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						"请先保存配载单!", 
						'error');
				return;
			}
			var records = this.up('grid').getStore().getRange();
			if(records.length == 0){
				Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						"没有交接单数据, 请确认!", 
						'error');
				return;
			}
			
			//1:必须是外请车
			//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
			//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
			var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm(),
				vehicleAssembleNo = basicInfoForm.findField('vehicleAssembleNo').getValue();
			if(basicInfoForm.findField('vehicleOwnerShip').getValue() != 'Leased') {
				Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非外请车, 不能打印!', 'error');
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
	    										Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
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
										Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
										return;
									}
								}
								//4：与零担合车不能打印配载单
								if(basicInfo.beInLTLCar=='Y'){
									Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, '与零担合车, 不能打印!', 'error');
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
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.action')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.delete')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.weightTotal')//'重量</br>(千克)'
	}, {
		dataIndex : 'volumeTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.volumeTotal')//'体积</br>(方)'
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.goodsQtyTotal')/*'件数'*/
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.waybillQtyTotal')/*'票数'*/
	}, {
		dataIndex : 'note',
		align : 'center',
		width : 300,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.note')/*'备注'*/
	}, {
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/,
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:load.vehicleassemblebilladdnew.showHandOverBillDetail('+"'" + value + "'"+
				",'"+null+"'"+')">' + value + '</a>';
			}else{
				return null;
			}
		}
	}],
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_vehicleassemblebilladdnew_toobar',
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
				id : 'Foss_load_vehicleassemblebilladdnew_TotalWeight',
				fieldLabel: load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalWeight'),//'总重量(千克)',
				columnWidth : 1/4,
				labelWidth : 120,
				xtype : 'numberfield',
				value : 0
			},{
				id : 'Foss_load_vehicleassemblebilladdnew_TotalVolume',
				fieldLabel: load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalVolume'),//'总体积(方)',
				labelWidth : 120,
				columnWidth : 1/4,
				xtype : 'numberfield',
				value : 0,
				listeners : {
					'change' : function(field, newValue, oldValue, eOpts){
						var form = load.vehicleassemblebilladdnew.addNewForm.getForm();
						//获取考核体积
						var examineVolume = form.findField('examineVolume').getValue();
						if(!load.vehicleassemblebilladdnew.isNumber(examineVolume)){
							form.findField('loadingRate').setValue(0.00);
							return;
						}
						//计算装载率
						var loadingRate = newValue/examineVolume;
						if(!load.vehicleassemblebilladdnew.isNumber(loadingRate)){
							form.findField('loadingRate').setValue(0.00);
						}else{
							form.findField('loadingRate').setValue(loadingRate.toFixed(2));
						}
					}
				}
			},{
				id : 'Foss_load_vehicleassemblebilladdnew_TotalPieces',
				fieldLabel: load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalPieces')/*'总件数'*/,
				columnWidth : 1/5,
				xtype : 'numberfield',
				value : 0
			},{
				id : 'Foss_load_vehicleassemblebilladdnew_TotalWaybill',
				fieldLabel: load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.totalWaybill')/*'总票数'*/,
				columnWidth : 1/5,
				xtype : 'numberfield',
				value : 0
			}]
	  }]
});

//定义方法，生成弹出窗口的查询条件中“交接时间”的起始和结束时间
load.vehicleassemblebilladdnew.getHandOverTime4QueryForm = function(isBegin){
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
Ext.define('Foss.load.vehicleassemblebilladdnew.QueryForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.formTitle')/*'查询条件'*/,
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
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/,
		name : 'handOverBillNo'
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		salesDepartment : 'Y',
		name : 'arriveDept',
		listeners : {
			'blur' : function(cmp, eo,eOpts){
				var destOrgCode = cmp.getValue();
				var destOrgCodeCmp = this.up('form').getForm().findField('arriveDeptCode');
				if(!Ext.isEmpty(destOrgCode)){
					destOrgCodeCmp.setValue(destOrgCode);
				}else{
					destOrgCodeCmp.reset();
				}
			}
		}
	},{
		xtype : 'textfield',
		name : 'arriveDeptCode',
		hidden : true
	},{
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/,
		xtype : 'commontruckselector',
		name : 'vehicleNo'
	},{
		allowBlank : false,
		disallowBlank : true,
		xtype : 'rangeDateField',
		fieldLabel : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.handOverTime')/*'交接时间'*/,
		columnWidth : 4/7,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_vehicleassemblebilladdnew_QueryForm_HandOverTime_fieldID',
		id : 'Foss_vehicleassemblebilladdnew_QueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateRange : 20,
		fromValue : Ext.Date.format(load.vehicleassemblebilladdnew.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.vehicleassemblebilladdnew.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'),
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
			text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.resetButton')/*'重置'*/,
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('beginHandOverTime').setValue(Ext.Date.format(load.vehicleassemblebilladdnew.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'));
				form.findField('endHandOverTime').setValue(Ext.Date.format(load.vehicleassemblebilladdnew.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.queryButton')/*'查询'*/,
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.vehicleassemblebilladdnew.queryGridPanel.store.load();
				}
			}
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单查询窗口中交接单store
Ext.define('Foss.load.vehicleassemblebilladdnew.QueryPageHandOverBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebilladdnew.HandOverBillModel',
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
			var queryParams = load.vehicleassemblebilladdnew.queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.handOverBillNo' : queryParams.handOverBillNo,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.arriveDept' : queryParams.arriveDeptCode,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.vehicleNo' : queryParams.vehicleNo,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.beginHandOverTime' : queryParams.beginHandOverTime,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.endHandOverTime' : queryParams.endHandOverTime,
					'vehicleAssembleBillVo.queryHandOverBillConditionDto.trailerVehicleNo':load.vehicleassemblebilladdnew.trailerVehicleNo
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
Ext.define('Foss.load.vehicleassemblebilladdnew.QueryPageHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.gridTitle')/*'交接单列表'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	emptyText : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.emptyText')/*'查询结果为空'*/,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.load.vehicleassemblebilladdnew.QueryPageHandOverBillStore');
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
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverBillNo')/*'交接单编号'*/
	},{
		dataIndex : 'handOverTime',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.handOverTime'),//'交接日期',
		renderer : function(value){
			if(value!=null){
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');									
		}else{
				return null;
			}
		}
	},{
		dataIndex : 'bePackage',
		align : 'center',
		width : 200,
		hidden:true,
		xtype : 'ellipsiscolumn',
		text : '是否快递'/*'到达部门'*/
	},{
		dataIndex : 'arriveDept',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.arriveDept')/*'到达部门'*/
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 100,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.queryForm.vehicleNo')/*'车牌号'*/
	},{
		dataIndex : 'weightTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.weightTotal')
	}, {
		dataIndex : 'volumeTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.volumeTotal')//'体积</br>(方)'
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.goodsQtyTotal')/*'件数'*/
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		flex : 1,
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.waybillQtyTotal')/*'票数'*/
	}]
});

//定义查询交接单弹出窗口
Ext.define('Foss.load.vehicleassemblebilladdnew.QueryHandOverBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	width : 850,
	title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.windowTitle')/*'查询待配载交接单'*/,
	queryForm: null,
	getQueryForm: function(){
		if(this.queryForm==null){
			this.queryForm = Ext.create('Foss.load.vehicleassemblebilladdnew.QueryForm');
			load.vehicleassemblebilladdnew.queryForm = this.queryForm;
		}
		return this.queryForm;
	},
	queryGridPanel : null,
	getQueryGridPanel : function(){
		if(this.queryGridPanel==null){
			this.queryGridPanel = Ext.create('Foss.load.vehicleassemblebilladdnew.QueryPageHandOverBillGrid');
			load.vehicleassemblebilladdnew.queryGridPanel = this.queryGridPanel;
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
		text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			var selectedList =load.vehicleassemblebilladdnew.queryGridPanel.getSelectionModel().selected.items;
			if(selectedList.length == 0){
				Ext.ux.Toast.msg(
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.noHandOverBillChecked'),//'未选中任何交接单!', 
					'error', 
					2000
				);
				return;
			}
			//对待确认的交接单进行校验，校验通过后插入主页的列表中
			if(load.vehicleassemblebilladdnew.validateUnAddHandOverBillList(selectedList)){
				//关闭窗口
				this.ownerCt.ownerCt.close();
			}
		}
	}]
});

/**
 * 方法，对从“查询交接单”界面传入的交接单列表进行校验；
 * 对从“查询待配载交接单”窗口传入的交接单列表进行校验，逻辑一致
 * 校验通过后插入主页面的列表中
 */
load.vehicleassemblebilladdnew.validateUnAddHandOverBillList = function(handOverBillList){
	//获取主页面列表store
	var mainStore = load.vehicleassemblebilladdnew.mainPageGrid.store;
	//获取主页面中输入的车牌号
	var vehicleNo = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('vehicleNo').getValue();
	//获取主页面中输入的到达部门
	var arriveDept = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('destOrgCode').getValue();
	//获取主页面中输入的“配载类型”
	var assembleType = load.vehicleassemblebilladdnew.addNewForm.getForm().findField('assembleType').getValue();
	//从选中的记录中随意取出一个车牌号和到达部门
	var queriedVehicleNo = handOverBillList[0].get('vehicleNo'),
		queriedArrivedDept = handOverBillList[0].get('arriveDeptCode'),
		queriedDriverCode = handOverBillList[0].get('driver'),
		queriedIsCarLoad = handOverBillList[0].get('isCarLoad'),
		queriedBePackage = handOverBillList[0].get('bePackage');
   var  handOverNo=handOverBillList[0].get('handOverBillNo');
	//定义已经添加到主页面的车牌号和到达部门；
	var addedVehicleNo,addedArriveDeptCode,addedIsCarLoad,addedBePackage,addhandOverBillNo;
	if(mainStore.getCount() != 0){
		var record = load.vehicleassemblebilladdnew.mainPageGrid.store.getAt(0);
		addedVehicleNo = record.get('vehicleNo');
		addedArriveDeptCode = record.get('arriveDeptCode');
		addedIsCarLoad = record.get('isCarLoad');
		addedBePackage = record.get('bePackage');
		addhandOverBillNo = record.get('handOverBillNo');
		//判断主页面的第一个交接单号是否为空
	if(!Ext.isEmpty(addhandOverBillNo)){
	  //第一个交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
	  if(addhandOverBillNo.substring(0,2)==("ky")||addhandOverBillNo.substring(0,3)==("KYB")){
		//
		if(!Ext.isEmpty(handOverNo)){
		 //选择的交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
		 if(handOverNo.substring(0,2)!=("ky")&&handOverNo.substring(0,3)!=("KYB")){
		   Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
		   '操作失败，已经添加了商务专递的交接单不可添加非商务专递的交接单', 'error', 3000);
			return;
			}
		}
	  }else{
	      if(handOverNo.substring(0,2)=="ky"||handOverNo.substring(0,3)=="KYB"){
		   Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
		   '操作失败，已经添加了非商务专递的交接单不可添加商务专递的交接单', 'error', 3000);
			return;
		   }
		   if(!Ext.isEmpty(addedBePackage) && addedBePackage != queriedBePackage){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				'操作失败，添加的交接单必须全为快递交接单或全为普通交接单！', 'error', 2000 );
			  return;
		    }
	  }
	}
	}
	//循环选中的交接单，进行各种校验
	for(var i in handOverBillList){
		var record = handOverBillList[i],
			vehicleNoI = record.get('vehicleNo'),
			arriveDeptI = record.get('arriveDeptCode'),
			driverCodeI = record.get('driver'),
			isCarLoadI = record.get('isCarLoad'),
			handOverBillNoI=record.get('handOverBillNo'),
			bePackageI = record.get('bePackage');
		if(vehicleNoI != queriedVehicleNo){
			if( !Ext.isEmpty(load.vehicleassemblebilladdnew.trailerVehicleNo)){
				if( vehicleNoI!=load.vehicleassemblebilladdnew.trailerVehicleNo && vehicleNoI !=vehicleNo){
					Ext.ux.Toast.msg(
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						//'操作失败，所选交接单车牌号不同!', 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSame'),
						'error', 
						2000
					);
					return;
				}
			}else{
				Ext.ux.Toast.msg(
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSame'),//'操作失败，所选交接单车牌号不同!', 
						'error', 
						2000
					);
				return;
			}
			
		}
		//第一个交接单号是否为空
	if(!Ext.isEmpty(handOverNo)){
	  //第一个交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
	  if(handOverNo.substring(0,2)==("ky")||handOverNo.substring(0,3)==("KYB")){
		//
		if(!Ext.isEmpty(handOverBillNoI)){
		 //选择的交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
		 if(handOverBillNoI.substring(0,2)!=("ky")&&handOverBillNoI.substring(0,3)!=("KYB")){
		   Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
		   '操作失败，不全部是商务专递的交接单不可生成配载单', 'error', 3000);
			return;
			}
		}
	  }else{
	      if(handOverBillNoI.substring(0,2)=="ky"||handOverBillNoI.substring(0,3)=="KYB"){
		   Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
		   '操作失败，不全部是商务专递的交接单不可生成配载单', 'error', 3000);
			return;
		   }
		   if(!Ext.isEmpty(addedBePackage) && addedBePackage != bePackageI){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				'操作失败，添加的交接单必须全为快递交接单或全为普通交接单！', 'error', 2000 );
			  return;
		    }
	  }
	}
		if(isCarLoadI != queriedIsCarLoad){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，所选交接单必须全是整车或者非整车交接单!',
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.carloadNotTheSame'), 
				'error', 
				2000
			);
			return;
		}
		if(!Ext.isEmpty(assembleType)
				&& assembleType == 'OWNER_LINE'
					&& isCarLoadI == 'Y'){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，“专线”配载时不能添加整车交接单!',
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.assembleTypeNotTheSame1'), 
				'error', 
				2000
			);
			return;
		}
		if(!Ext.isEmpty(assembleType)
				&& assembleType == 'CAR_LOAD'
					&& isCarLoadI == 'N'){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，“整车”配载时只能添加整车交接单!', 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.assembleTypeNotTheSame2'),
				'error', 
				2000
			);
			return;
		}
		if(arriveDeptI != queriedArrivedDept){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，所选交接单到达部门不同!', 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSame'),
				'error', 
				2000
			);
			return;
		}
		if(driverCodeI != queriedDriverCode){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，所选交接单司机不同!', 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.driverNotTheSame'),
				'error', 
				2000
			);
			return;
		}
		
		
		//如果车辆是挂车则 比较挂牌号 前提条件是专线
		if( !Ext.isEmpty(vehicleNo) && vehicleNoI != vehicleNo){
			if( !Ext.isEmpty(load.vehicleassemblebilladdnew.trailerVehicleNo)){
					if( vehicleNoI!=load.vehicleassemblebilladdnew.trailerVehicleNo){
						Ext.ux.Toast.msg(
							load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
							//'操作失败，所选交接单车牌号和配载单车牌号不同!',
							load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsMain'), 
							'error', 
							2000
						);
						return;
					}		
			}else{
				Ext.ux.Toast.msg(
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
						//'操作失败，所选交接单车牌号和配载单车牌号不同!', 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsMain'),
						'error', 
						2000
					);
					return;
			}
		} 
		
		
		if(!Ext.isEmpty(arriveDept) && arriveDeptI != arriveDept){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，所选交接单到达部门和配载单到达部门不同!', 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSameAsMain'),
				'error', 
				2000
			);
			return;
		}
		if(!Ext.isEmpty(addedVehicleNo) && addedVehicleNo != vehicleNoI){
			if( !Ext.isEmpty(load.vehicleassemblebilladdnew.trailerVehicleNo)){
				if(addedVehicleNo!=load.vehicleassemblebilladdnew.trailerVehicleNo && addedVehicleNo != vehicleNo){
					Ext.ux.Toast.msg(
							load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
							//'操作失败，所选交接单车牌号和已经添加的交接单车牌号不同!', 
							load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsThatHaveAdded'),
							'error', 
							2000
					);
					return;
				}
				
				
			}else{
				Ext.ux.Toast.msg(
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						//'操作失败，所选交接单车牌号和已经添加的交接单车牌号不同!', 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.vehicleNoNotTheSameAsThatHaveAdded'),
						'error', 
						2000
				);
				return;
			}
		}
		if(!Ext.isEmpty(addedArriveDeptCode) && addedArriveDeptCode != arriveDeptI){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				//'操作失败，所选交接单到达部门和已经添加的交接单到达部门不同!', 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.destOrgNotTheSameAsThatHaveAdded'),
				'error', 
				2000
			);
			return;
		}
		if(!Ext.isEmpty(addedIsCarLoad) && addedIsCarLoad != isCarLoadI){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				'操作失败，所选交接单必须全为快递交接单和包交接单或全为零担交接单！',
				'error', 
				2000
			);
			return;
		}
		//获取改行记录的交接单号
		var handOverBillNo = record.get('handOverBillNo');
		if(mainStore.getCount() != 0){
			//如果该交接单号已经被添加至主页面，则跳出循环
			if(mainStore.findRecord('handOverBillNo',handOverBillNo,0,false,false,false) != null){
				Ext.ux.Toast.msg(
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
					//'操作失败，交接单【' 
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.billAlreadyAddedPrefix')
					+ handOverBillNo 
					//'】已经添加至配载单',
					+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.billAlreadyAddedPostfix'), 
					'error', 
					3000
				);
				return;
			}
		}
	}
	//给主页的车牌号、到达部门赋值
	var basicInfoForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
	if(Ext.isEmpty(arriveDept)){
		basicInfoForm.findField('destOrgCode').setValue(queriedArrivedDept);
		basicInfoForm.findField('destOrgCode').store.load({
			params : {'commonOrgVo.code' : queriedArrivedDept}
		});
		//触发到达部门失去焦点事件
		basicInfoForm.findField('destOrgCode').fireEvent('blur',basicInfoForm.findField('destOrgCode'));
	}
	//如果“发车日期”不为空，则填入车牌号，因为要根据发车日期来校验发车计划
	if(!Ext.isEmpty(basicInfoForm.findField('leaveTime').getValue())){
		if(Ext.isEmpty(vehicleNo)){
			basicInfoForm.findField('vehicleNo').setValue(queriedVehicleNo);
			//触发车牌号失去焦点事件
			basicInfoForm.findField('vehicleNo').fireEvent('blur',basicInfoForm.findField('vehicleNo'));
		}
	}
	//将该条记录插入主页面grid中
	mainStore.insert(mainStore.getCount(),handOverBillList);	
	load.vehicleassemblebilladdnew.mainPageGrid.getView().refresh();
	//如果选择的是快递交接单则 与零担合车默认勾选
	if(queriedBePackage=='Y'){
		basicInfoForm.findField('beInLTLCar').setValue(true);
	}else if(handOverNo!=null&&(handOverNo.substring(0,1)=='k'||handOverNo.substring(0,1)=='B')){
		basicInfoForm.findField('beInLTLCar').setValue(true);
	}
	return true;
}

//点击“交接单编号”打开详情界面方法
load.vehicleassemblebilladdnew.showHandOverBillDetail = function(handOverBillNo,handOverType){
	load.addTab('T_load-handoverbillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
	       /*'交接单详情'*///打开的Tab页的标题
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.resultGrid.showHandOverBillTabTitle'),
			//对应的页面URL，可以在url后使用?x=123这种形式传参
			load.realPath('handoverbillshowindex.action') + '?handOverBillNo="' + handOverBillNo + '"&handOverType="'+handOverType+'"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//基本信息Form
load.vehicleassemblebilladdnew.addNewForm = Ext.create('Foss.load.vehicleassemblebilladdnew.AddNewForm');
//交接单列表grid
load.vehicleassemblebilladdnew.mainPageGrid = Ext.create('Foss.load.vehicleassemblebilladdnew.MainPageHandOverBillGrid');
//定义弹出窗口
load.vehicleassemblebilladdnew.queryWindow = Ext.create('Foss.load.vehicleassemblebilladdnew.QueryHandOverBillWindow');
//奖罚协议gird
load.vehicleassemblebilladdnew.RewardOrPunishGrid= Ext.create('Foss.load.vehicleassemblebilladdnew.RewardOrPunishGrid');
//onReady
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-vehicleassemblebilladdnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [load.vehicleassemblebilladdnew.addNewForm,
		         load.vehicleassemblebilladdnew.RewardOrPunishGrid,
		         load.vehicleassemblebilladdnew.mainPageGrid,{
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
						id : 'Foss_load_vehicleassemblebilladdnew_mainPage_saveButton_ID',
						disabled : !load.vehicleassemblebilladdnew.isPermission('load/saveVehicleAssembleBillButton'),
						hidden : !load.vehicleassemblebilladdnew.isPermission('load/saveVehicleAssembleBillButton'),
						text : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.saveButton')/*'保存'*/,
						handler : function(){
							//配载单基本信息form
							var basicForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
							//获取配载类型
							var assembleType = basicForm.findField('assembleType').getValue();
							var vehicleOwnerShip=basicForm.findField('vehicleOwnerShip').getValue();
							//获取基本信息
							var baseEntity = basicForm.getValues();
							//#################################################
							//配载单中交接单
							var detailStore = load.vehicleassemblebilladdnew.mainPageGrid.store;
							//获取bePackage
							var bePackItme=load.vehicleassemblebilladdnew.mainPageGrid.store.data.items[0];
							var beP=null;
							if(bePackItme!=null){
								//是否快递
								beP = bePackItme.data.bePackage;
							}
							//出发部门code
							var currentDeptCode = FossUserContext.getCurrentDept().code;
							//出发部门名称
							var currentDeptName = FossUserContext.getCurrentDept().name;
							//零担快递判断标示
							var flag=false;
							
							if(beP!=null && beP=='N'){
								//零担配载单发送ajax请求
								Ext.Ajax.request({
									url : load.realPath('isNOrYVehicleAssembleBill.action'),
									 params : {
										'vehicleAssembleBillVo.queryHandOverBillConditionDto.arriveDept' : baseEntity.destOrgCode,
										'vehicleAssembleBillVo.queryHandOverBillConditionDto.vehicleNo' : baseEntity.vehicleNo,
										'vehicleAssembleBillVo.queryHandOverBillConditionDto.departDept' : currentDeptCode 
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										var list=result.vehicleAssembleBillVo.handOverBillList;
										for(var i=0;i<list.length;i++){
											if(list[i].bePackage=='Y'){
												//包含快递货
												flag=true;
												break;
											}else{
												flag=false;
											}
										};
										//################################################# 
										if(assembleType == 'CAR_LOAD'&& baseEntity.beReturnReceipt != 'on'){
											Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
												"该配载单没有勾选押回单，请再次确认是否保存！", function(optional){
			    								if(optional != 'no'){
			    								  load.confirmAction();
			    								}
											});
										}else{
											//如果是专线且费用为0提示
											if(assembleType == 'OWNER_LINE'&& baseEntity.feeTotal==0 && vehicleOwnerShip=='Leased'){
												Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
														"是否确认车价为零总费用为零！", function(optional){
					    								if(optional != 'no'){
					    									if(flag){
					    										Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('提示')/*'提示'*/, 
					    												"此车牌号有包含快递和零担货，需要先配载快递货，请确认是否要配载！", function(optional){
					    											if(optional != 'no'){
					    												load.confirmAction();
					    											}
					    										});
					    										
					    									}else{
					    										load.confirmAction();
					    									}
					    									
					    								}
													});
												
												
											}if(assembleType == 'OWNER_LINE'&& vehicleOwnerShip=='Leased'){
			    									if(flag){
			    										Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('提示')/*'提示'*/, 
			    												"此车牌号有包含快递和零担货，需要先配载快递货，请确认是否要配载！", function(optional){
			    											if(optional == 'yes'){
			    												load.confirmAction();
			    											}
			    										});
			    										
			    									}else{
			    										load.confirmAction();
			    									}
												
												
											}else{
												load.confirmAction();
											}
											
										}
									},
									exception : function(response){
										var result = Ext.decode(response.responseText);
									}
								});
							} else{
								
								//################################################# 
								if(assembleType == 'CAR_LOAD'&& baseEntity.beReturnReceipt != 'on'){
									Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
										"该配载单没有勾选押回单，请再次确认是否保存！", function(optional){
	    								if(optional != 'no'){
	    								  load.confirmAction();
	    								}
									});
								}else{
									//如果是专线且费用为0提示
									if(assembleType == 'OWNER_LINE'&& baseEntity.feeTotal==0&& vehicleOwnerShip=='Leased'){
										Ext.Msg.confirm(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
												"是否确认车价为零总费用为零！", function(optional){
			    								if(optional != 'no'){
			    									load.confirmAction();
			    								}
											});
									}else{
										load.confirmAction();
									}
									
								}
							}
							
						}
					}]
		}],
		renderTo : 'T_load-vehicleassemblebilladdnewindex-body'
	});
	//如果是从“查询交接单”界面的“生成配载单”按钮进入本界面，则对该界面提交来的交接单进行校验、插入
	if(load.vehicleassemblebilladdnew.comeFromHandOverBillQuery == '1'){
		var selectedRecord = load.handoverbillquery.queryHandOverBillGrid.getSelectionModel().getSelection();
		//对待确认的交接单进行校验，校验通过后插入主页的列表中
		load.vehicleassemblebilladdnew.validateUnAddHandOverBillList(selectedRecord);
	}
});
load.confirmAction = function(){
	//配载单基本信息form
	var basicForm = load.vehicleassemblebilladdnew.addNewForm.getForm();
	//获取配载类型
	var assembleType = basicForm.findField('assembleType').getValue();
	//配载单中交接单
	var detailStore = load.vehicleassemblebilladdnew.mainPageGrid.store;
	//奖罚协议
	var reWardStore = load.vehicleassemblebilladdnew.RewardOrPunishGrid.store;
	//存放奖罚信息的数组
	var vehicleRewardProtEntity= new Array();
	//获取基本信息
	var baseEntity = basicForm.getValues();
	///*'是否签署效条款'*/
	if(baseEntity.beTimeLiness=='on'){
		//处理基本信息
		baseEntity.beTimeLiness='Y';
		if(!baseEntity.beFinallyArrive && baseEntity.beMidWayLoad){
				Ext.ux.Toast.msg(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"只有专线外请车且非在途的配载才能签署时效条款!",//'只有专线外请车且非在途的配载才能签署时效条款!', 
				'error', 
				2000
				);
				field.setValue(false);
				return;
		}
		//定义数组用于存放连续的区间
		var timeRewardArray=[];
		var timepunishArray=[];
		var rewardcount=0;
		var finecount=0;
		if(reWardStore.data.items.length==0){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"没有选择奖罚类型!",//'没有选择奖罚类型!', 
				'error', 
				2000
				);
				return;
			}
			
			var timeLimit = record.get('timeLimit');
			if(Ext.isEmpty(timeLimit)){
				Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"时间段不能为空!",//'时间上限不能小于时间下限!', 
				'error', 
				2000
				);
				return;
			}
			var agreementMoney =record.get('agreementMoney');
			if(Ext.isEmpty(agreementMoney)){
				Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				"奖罚金额不能为空!",//'奖罚金额不能为空!', 
				'error', 
				2000
				);
				return;
			}
			//获取连续区间
			if(rewardOrPunishType =='REWARD'){
				//没有种类型都有时间上限，根据时间上限找到对应的连续区间，用于判断选择的时间段是否连续
				if(load.vehicleassemblebilladdnew.timeCombinationMap.get(timeLimit)!=null){
					 timeRewardArray =load.vehicleassemblebilladdnew.timeCombinationMap.get(timeLimit)
				}
				rewardcount=rewardcount+1;
			}else{
				if(load.vehicleassemblebilladdnew.timeCombinationMap.get(timeLimit)!=null){
					 timepunishArray =load.vehicleassemblebilladdnew.timeCombinationMap.get(timeLimit)
				}
				finecount=finecount+1;
			}
		
			vehicleRewardProtEntity.push(record.data);
		}
		//如果存在奖励和罚款则判断奖励和罚款区间是否保持一致
//								if(timeRewardArray!=null && timepunishArray!=null && (rewardcount>=1 && finecount>=1)
//									&& (timeRewardArray.length>=1 ||timepunishArray.length>=1 )&& timeRewardArray!=timepunishArray){
//									//奖励和罚款时间区间不一致
//										Ext.ux.Toast.msg(
//										load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
//										"奖励和惩罚时间区间不一致!",//'奖励和惩罚时间区间不一致', 
//										'error', 
//										2000
//										);
//										return;
//									}
		
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
								load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
								load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
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
	//坑爹的LMS车辆基础资料，此处校验车辆的额定净空和额定载重
	if(Ext.isEmpty(basicForm.findField('ratedClearance').getValue())){
		Ext.ux.Toast.msg(
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.ratedClearanceCanNotBeNull'),//'车辆的“额定净空”为空，请换用其他车辆！', 
			'error', 
			2000
		);
		return;
	}
	if(Ext.isEmpty(basicForm.findField('ratedLoad').getValue())){
		Ext.ux.Toast.msg(
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.ratedLoadCanNotBeNull'),//'车辆的“额定载重”为空，请换用其他车辆！', 
			'error', 
			2000
		);
		return;
	}
	if(!basicForm.isValid()){
		return;
	}
	if(detailStore.getCount() == 0){
		Ext.ux.Toast.msg(
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
			load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.noBillHasAdded'),//'未添加任何交接单!', 
			'error', 
			2000
		);
		return;
	}
	//选择了在途只装不卸，必须选择中途到达部门
	if(basicForm.findField('beMidWayOnlyLoad').getValue()&&Ext.isEmpty(basicForm.findField('onTheWayDestOrgCode').getValue())){
		Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				'选择了在途只装不卸，必须选择中途到达部门！',//'选择了在途只装不卸，必须选择中途到达部门!', 
				'error', 
				2000
			);
			return;
	}

	//获取基本信息中的车牌号
	var vehicleNo = baseEntity.vehicleNo;
	//获取基本信息中的到达部门
	var arriveDeptCode = baseEntity.destOrgCode;
	//车辆所有权类别
	var vehicleOwnerShip =baseEntity.vehicleOwnerShip; 
	//定义里面交接单数组
	var billDetailEntityList = new Array();
	for(var i in detailStore.data.items){
		var record = detailStore.data.items[i];
		var isCarLoad = record.get('isCarLoad');
		if(assembleType == 'OWNER_LINE' && isCarLoad == 'Y'){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.assembleTypeNotTheSameAsMainPrefix1'),//'专线配载时不可添加整车交接单！' 
				+ record.get('handOverBillNo') 
				+ load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.assembleTypeNotTheSameAsMainPostfix1')/*'为整车交接单'*/, 
				'error', 
				4000
			);
			return;
		}
		if(assembleType == 'CAR_LOAD' && isCarLoad == 'N'){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.assembleTypeNotTheSameAsMainPrefix2'),//'整车配载时只能添加整车交接单！' 
				+ record.get('handOverBillNo') + 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.assembleTypeNotTheSameAsMainPostfix2')/*'不是整车交接单'*/, 
				'error', 
				4000
			);
			return;
		}
		if(assembleType == 'OWNER_LINE'&& vehicleOwnerShip=='Company'){
			//如果车辆是挂车则 比较挂牌号 前提条件是专线
			if( record.get('vehicleNo') != vehicleNo && record.get('vehicleNo') !=load.vehicleassemblebilladdnew.trailerVehicleNo ){
				Ext.ux.Toast.msg(
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
						load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.vehicleNoNotTheSameAsMain'),//'列表中交接单车牌号和配载单车牌号不同!', 
						'error', 
						2000
				);
				return;
				
			}
		}else if(record.get('vehicleNo') != vehicleNo){
			
			Ext.ux.Toast.msg(
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
					load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.vehicleNoNotTheSameAsMain'),//'列表中交接单车牌号和配载单车牌号不同!', 
					'error', 
					2000
			);
			return;
		}
		if(record.get('arriveDeptCode') != arriveDeptCode){
			Ext.ux.Toast.msg(
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/, 
				load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.destOrgNotTheSameAsMain'),//'列表中交接单到达部门和配载单到达部门不同!', 
				'error', 
				2000
			);
			return;
		}
		billDetailEntityList.push(record.data);
	}
	//处理基本信息中的出发日期
	baseEntity.leaveTime = new Date(baseEntity.leaveTime);
	//处理基本信息中的“是否在途装卸”
	if(baseEntity.beMidWayLoad == 'on'){
		baseEntity.beMidWayLoad = 'Y';
		//处理基本信息中的“是否最终到达”
		if(baseEntity.beFinallyArrive == 'on'){
			baseEntity.beFinallyArrive = 'Y';
		}else{
			baseEntity.beFinallyArrive = 'N';
		}
	}else{
		baseEntity.beMidWayLoad = 'N';
	}
	
	//midWayLoadType
	if(Ext.isEmpty(baseEntity.midWayLoadType) && baseEntity.beMidWayLoad== 'Y'){
		Ext.ux.Toast.msg('提示', 
		"勾选了是否在途装卸时就必须选择在途配载类型!",
		'error', 
		2000
		);
		return;
	}
	if(vehicleOwnerShip!='Company'){
		if(Ext.isEmpty(baseEntity.driverOfWeight) || Ext.isEmpty(baseEntity.driverOfVolumn) ){
			Ext.ux.Toast.msg('提示', 
					"请填写司机自带货量!",
					'error', 
					2000
					);
					return;
		}
	}
	//处理基本信息中的“是否押回单”
	if(baseEntity.beReturnReceipt == 'on'){
		baseEntity.beReturnReceipt = 'Y';
	}else{
		baseEntity.beReturnReceipt = 'N';
	}
	//处理是否与零担合车
	if(baseEntity.beInLTLCar =='on'){
		baseEntity.beInLTLCar = 'Y';
	}else{
		baseEntity.beInLTLCar = 'N';
	}
	//处理基本信息中的“是否在途只装不卸”
	if(baseEntity.beMidWayOnlyLoad=='on'){
		baseEntity.beMidWayOnlyLoad='Y';
	}else{
		baseEntity.beMidWayOnlyLoad='N';
	}
	//处理是否大车小用
	if(baseEntity.beCarSmallUsed=='on'){
		baseEntity.beCarSmallUsed='Y';
	}else{
		baseEntity.beCarSmallUsed='N'
	}
	var data = {'vehicleAssembleBillVo' : {
		'baseEntity' : baseEntity,
		'billDetailEntityList' : billDetailEntityList,
		'rewardOrPunishDto':{
			'rewardOrPunishAgreementEntity':vehicleRewardProtEntity,
			'rewardMaxMoney' : Ext.getCmp('Foss_load_vehicleassemblebilladdnew_RewardMaxMoney').getValue(),
			'fineMaxMoney' :  Ext.getCmp('Foss_load_vehicleassemblebilladdnew_FineMaxMoney').getValue()
		}
	}};
	//获取到达部门名称
	var arriveDeptName;
	var arriveDeptCmp = basicForm.findField('destOrgCode');
	if(!Ext.isEmpty(arriveDeptCmp.getValue())){
		arriveDeptName = arriveDeptCmp.store.findRecord('code',arriveDeptCmp.getValue(),0,false,true,true).get('name');
		//在对象中加入到达部门code
		Ext.Object.merge(baseEntity,{
			'destOrgName' : arriveDeptName
		});
	}
	var myMask = new Ext.LoadMask(Ext.getCmp('T_load-vehicleassemblebilladdnewindex_content'), {
		msg : '数据提交中，请稍候....'
	});
	myMask.show();
	//保存配载单数据
	Ext.Ajax.request({
		url : load.realPath('saveVehicleAssembleBill.action'),
		jsonData : data,
		timeout : 300000,
		success : function(response){
			myMask.hide();
			//设置form所有控件为只读
			var formCmps = basicForm.getFields().getRange(0,basicForm.getFields().getCount());
			for(var i in formCmps){
				formCmps[i].setReadOnly(true);
			}
			var result = Ext.decode(response.responseText);
			var vehicleAssembleNo = result.vehicleAssembleBillVo.vehicleAssembleNo;
			//隐藏保存按钮
			Ext.getCmp('Foss_load_vehicleassemblebilladdnew_mainPage_saveButton_ID').setVisible(false);
			//隐藏交接单列表前的操作列
			load.vehicleassemblebilladdnew.mainPageGrid.columns[0].setVisible(false);
			//将配载车次号反写至界面
			load.vehicleassemblebilladdnew.addNewForm.getForm().findField('vehicleAssembleNo').setValue(vehicleAssembleNo);
			
			load.vehicleassemblebilladdnew.prtvehicleAssembleNo = vehicleAssembleNo;
			Ext.Msg.show({
		     title : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,
		     msg : load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.saveSuccessInfoPrefix')/*'保存成功，配载车次号为：'*/ + vehicleAssembleNo,
		     buttons : Ext.Msg.OK,
		     icon: Ext.Msg.WARNING
			});
		},
		exception : function(response){
			myMask.hide();
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert(load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.billGrid.alertTitle')/*'提示'*/,load.vehicleassemblebilladdnew.i18n('foss.load.vehicleassemblebilladdnew.mainPanel.saveFailureInfoPrefix')/*'保存失败，'*/ + result.message);
			
			//还原处理 “是否在途装卸”
			if(baseEntity.beMidWayLoad = 'Y'){
				baseEntity.beMidWayLoad='on';
			}
			//还原处理“是否最终到达”
			if(baseEntity.beFinallyArrive =='Y'){
				baseEntity.beFinallyArrive='on';
			}
			//还原处理“是否押回单”
			if(baseEntity.beReturnReceipt =='Y'){
				baseEntity.beReturnReceipt='on';
			}
			//还原处理“是否时效条款”
			if(baseEntity.beTimeLiness=='Y'){
				baseEntity.beTimeLiness='on';
			}
			//还原是否与零担合车
			if(baseEntity.beInLTLCar =='Y'){
				baseEntity.beInLTLCar = 'on';
			}
			//还原是否中途只装不卸
			if(baseEntity.beMidWayOnlyLoad =='Y'){
				baseEntity.beMidWayOnlyLoad = 'on';
			}
			//还原是否打车小用
			if(baseEntity.beCarSmallUsed=='Y'){
				baseEntity.beCarSmallUsed='on';
			}
		},
		failure : function(){
			myMask.hide();
		}
	});
}