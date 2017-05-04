/***********************START      集中接货签单******************************/
function getUUIDAddNew() {
	return Ext.Date.format(new Date(), 'Y-m-d H:i:s');
}

//集中接货签单MODEL
Ext.define('Foss.management.FocusSignBillModel',{
	extend: 'Ext.data.Model',
	fields: [{name:'id', type: 'string'},
	        {name:'signBillNo', type:'string'},			//签单编号
			{name:'useTruckOrgName', type:'string'}, 	//用车部门
			{name:'driverCode', type:'string'}, 		//司机
			{name:'driverName', type:'string'}, 		//司机姓名
			{name:'receiverName', type:'string'},		//接货员
			{name:'signBillDate', type:'date',
				convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}
			}},											//日期
			{name:'vehicleNo', type:'string'},			//车牌号码
			{name:'vehicleTypeLength', type:'string'},	//车型
			{name:'runKm', type:'float'},				//行驶公里 
			{name:'vacancyKm', type:'float'},			//空驶公里 
			{name:'waybillQtyTotal', type:'int'},	//接货票数
			{name:'volume', type:'float'},				//体积
			{name:'weight', type:'float'},				//重量
			{name:'upstairsBillQty', type:'int'},	//上楼票数 
			{name:'singleReceiveBillQty', type:'int'}	//单独接货票数 
	]
});

//集中接货签单费用MODEL
Ext.define('Foss.management.FocusSignBillCostModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id', type: 'string'},				//明细id
	        {name:'wayBillNo',type:'string'},  			//运单号
	        {name:'customerName',type:'string'},		//客户名称
	        {name:'goodsQty',type:'string'},			//件数
	        {name:'goodsPackage',type:'string'},		//包装
	        {name:'isCashPayment',type:'bool',
	        	convert: function(v){
	        		//2013-06-04_modifybyzyx
	        		if(v == true) {
	        			return true;
	        		}
	        		//end
	        		
	        		if(v=='CH' || v=='Y'){
	        			return true;
	        		}else{
	        			return false;
	        		}
	        	}
	        },		//是否现付
	        {name:'useTruckOrgCode',	type:'string'}, //用车部门Code
	        {name:'useTruckOrgName',	type:'string'}, //用车部门Name
	        {name:'weight',type:'string'},			//重量
	        {name:'volume',type:'string'},			//体积
	        {name:'totalFee',type:'string'},			//开单金额
	        {name:'useTruckFee',type:'string'},			//用车费用
	        {name:'driverRoyalty',type:'string'},		//司机提成
	        {name:'isUpstairs',type:'bool'},			//是否上楼
	        {name:'isSingleReceive',type:'bool'},    //是否单独接货
	        {name:'notes',type:'string'}				//备注
	]
});

//集中接货签单store
Ext.define('Foss.management.FocusSignBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.management.FocusSignBillModel',
//	pageSize: 20,
	proxy: {
        type: 'ajax',
        actionMethods: {read: 'POST'},
        url: management.realPath('queryFocusSignBill.action'),
        reader: {
            type: 'json',
            totalProperty : 'totalCount',
            root: 'vo.focusSignBillDtoList'
        },
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result.message);
            }
        }
    },
    listeners:{
    	//store加载前调用，用于传入查询参数
		beforeload : function(store, operation, eOpts) {
			var queryParams = management.focusSignBillQueryForm.getForm().getValues();
			Ext.apply(operation,{
				params :{
    				'vo.useTruckOrgCode': queryParams.useTruckOrgCode, //用车部门
    				'vo.driverCode': queryParams.driverCode, //司机
    				'vo.vehicleNo': queryParams.vehicleNo, //车牌号
    				'vo.vehicleTypeLength': queryParams.vehicleTypeLength, //车型
    				'vo.signBillDateFrom': queryParams.signBillDateFrom, //起始日期
    				'vo.signBillDateTo': queryParams.signBillDateTo //结束日期
				}
			});
		},
		datachanged: function(store, operation, epots){
			var queryParams = management.focusSignBillQueryForm.getForm().getValues();
			Ext.Ajax.request({
				params:{
    				'vo.useTruckOrgCode': queryParams.useTruckOrgCode, //用车部门
    				'vo.driverCode': queryParams.driverCode, //司机
    				'vo.vehicleNo': queryParams.vehicleNo, //车牌号
    				'vo.vehicleTypeLength': queryParams.vehicleTypeLength, //车型
    				'vo.signBillDateFrom': queryParams.signBillDateFrom, //起始日期
    				'vo.signBillDateTo': queryParams.signBillDateTo //结束日期
				},
				url: management.realPath('queryFocusSignBillTotal.action'),
				success: function(response){
					var result = Ext.decode(response.responseText);
					var totalDto = result.vo.totalDto;
					//获取合计信息的容器
					var totalInfo = management.focusSignBillGrid.getDockedItems('toolbar[dock="bottom"]')[0].query('displayfield');
					Ext.each(totalInfo, function(item) {
						 if(item.name == 'totalDrivers'){
							 item.setValue(totalDto.totalDrivers);
						 }else if(item.name == 'totalBills'){
							 item.setValue(totalDto.totalBills);
						 }else if(item.name == 'totalVolume'){
							 item.setValue(totalDto.totalVolume);
						 }else if(item.name == 'totalWeight'){
							 item.setValue(totalDto.totalWeight);
						 }else if(item.name == 'totalWaybillQty'){
							 item.setValue(totalDto.totalWaybillQty);
						 }else if(item.name == 'totalupstairsBillQty'){
							 item.setValue(totalDto.totalupstairsBillQty);
						 }else if(item.name == 'totalSingleReceiveBillQty'){
							 item.setValue(totalDto.totalSingleReceiveBillQty);
						 }
					});
				},
				exception: function(response ){
					
				},
				failure: function(){
					
				}
			});
		}
    }
});

//集中接货签单费用store
Ext.define('Foss.management.FocusSignBillCostStore',{
	extend:'Ext.data.Store',
	model:'Foss.management.FocusSignBillCostModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.feeDetailList'
		}
	},
	listeners:{
		datachanged: function(store, operation, epots){
			var count = store.getCount();
			var totalPieces = 0;
			var totalRoyalty = 0;
			var totalUpstairs = 0;
			var totalSingleReceive = 0;
			for(var i=0; i<count; i++){
				var rec = store.getAt(i);
				totalPieces += Ext.Number.from(rec.data.goodsQty, 0); 
				totalRoyalty += Ext.Number.from(rec.data.driverRoyalty, 0); 
				//判断是否上楼
				if(rec.data.isUpstairs){
					totalUpstairs += 1;
				}else{
					//空操作
				}
				//判断是否单独接货
				if(rec.data.isSingleReceive){
					totalSingleReceive += 1;
				}else{
					//空操作
				}
			}
			var grid = Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
			if(grid != null){
				var totalInfo = grid.getDockedItems('toolbar[dock="bottom"]')[0].query('displayfield');
				Ext.each(totalInfo, function(item) {
					if(item.name == 'totalBills'){
						 item.setValue(count);
					 }else if(item.name == 'totalDrivers'){
						 item.setValue(totalDrivers);
					 }else if(item.name == 'totalPieces'){
						 item.setValue(totalPieces);
					 }else if(item.name == 'totalRoyalty'){
						 item.setValue(totalRoyalty);
					 }else if(item.name == 'totalUpstairs'){
						 item.setValue(totalUpstairs);
					 }else if(item.name == 'totalSingleReceive'){
						 item.setValue(totalSingleReceive);
					 }
				});
			}
		}
	}
});

//用车类型store
Ext.define('Foss.management.IsFirstTransferStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//集中接货查询条件
Ext.define('Foss.management.FocusSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth: 1,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	defaults:{
		labelWidth: 80,
		msgTarget: 'side',
		margin: '0 5 0 5'
	},
	items:[{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name: 'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		columnWidth: .25
	},{
		xtype:'commonowndriverselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		name: 'driverCode',
		forceSelection: true,
		columnWidth:.25
	},{
		xtype: 'commonowntruckselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		name: 'vehicleNo',
		forceSelection: true,
		columnWidth:.25,
		listeners: {
			change: function(field, newValue, oldValue){
				console.log(newValue);
			}
		}
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		name: 'vehicleTypeLength',
		forceSelection: true,
		columnWidth:.25
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		xtype: 'rangeDateField',	
		fieldId: 'Foss_management_queryFocusSignBillForm_createTime',
		dateType: 'datetimefield_date97',
		disallowBlank:true,
		time:false,
		dateRange : 31,
		format:'Y-m-d',
		fromName: 'signBillDateFrom',		
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),	
		toName: 'signBillDateTo',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),	
		allowBlank: false,
		columnWidth: .50
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: management.signBill.i18n('foss.management.button.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
				//重新初始化交接时间
				this.up('form').getForm().findField('signBillDateFrom')						
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
				this.up('form').getForm().findField('signBillDateTo')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
			
			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text: management.signBill.i18n('foss.management.button.search'),//查询
			disabled: !management.signBill.isPermission('management/queryFocusSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryFocusSignBillButton'),
			cls:'yellow_button',
			columnWidth:.08,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			  //设定间隔秒数,如果不设置，默认为2秒
			  seconds: 1
			}),
			handler:function(){
				if(management.focusSignBillQueryForm.getForm().isValid()){
					management.focusSignBillPagingBar.moveFirst();
				}else{
					Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.airfreight.checkoutMessage.queryFormInfo'));
				}
			}
		},{
			text:management.signBill.i18n('foss.management.button.export'),//导 出
			disabled: !management.signBill.isPermission('management/queryExportFocusSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryExportFocusSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){					
					var queryParams = management.focusSignBillQueryForm.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
		    			url: management.realPath('queryExportFocusSignBill.action'),	 
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params : {
		    				'vo.useTruckOrgCode': queryParams.useTruckOrgCode, //用车部门
		    				'vo.driverCode': queryParams.driverCode, //司机
		    				'vo.vehicleNo': queryParams.vehicleNo, //车牌号
		    				'vo.vehicleTypeLength': queryParams.vehicleTypeLength, //车型
		    				'vo.signBillDateFrom': queryParams.signBillDateFrom, //起始日期
		    				'vo.signBillDateTo': queryParams.signBillDateTo //结束日期
		    			},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),result.message);//导
		    			}
	    			});					
				}				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		//加入全局变量中
		management.focusSignBillQueryForm = me;  
	}
});

//集中接货签单grid
Ext.define('Foss.management.FocusSignBillGrid',{
	extend: 'Ext.grid.Panel',
	title:management.signBill.i18n('foss.management.FocusSignBillGrid.title'),
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	columns:[{
		xtype:'actioncolumn',
		width:60,
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !management.signBill.isPermission('management/updateFocusSignBillButton'),
			hidden: !management.signBill.isPermission('management/updateFocusSignBillButton'),
			tooltip: management.signBill.i18n('foss.management.button.operate'),//编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				Ext.Ajax.request({
					params: {
						'vo.id': record.get('id'), 
						'vo.signBillNo': record.get('signBillNo')
					},
					url: management.realPath('queryFocusSignBillById.action'),
					success:function(response){
						var result = Ext.decode(response.responseText);
						var window= Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID')==null ? Ext.create('Foss.management.FocusSignBillCostWindow'): Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID');
						var form = Ext.getCmp('Foss_management_FocusSignBillCostForm_ID');
						form.isAddNew = false;
						var baseForm = form.getForm();
						var list =  result.vo.feeDetailList;
						Ext.each(list, function(item) {
							if(item.isSingleReceive == 'Y'){
								item.isSingleReceive = true;
							}
							if(item.isUpstairs == 'Y'){
								item.isUpstairs = true
							}
						});
						var content = result.vo.focusSignBillEntity;
						//id
						baseForm.findField('id').setValue(content.id);
						//日期
						baseForm.findField('signBillNo').setValue(content.signBillNo);
						//签单日期
						baseForm.findField('signBillDate').setValue(new Date(content.signBillDate));
						//空驶公里
						baseForm.findField('vacancyKm').setValue(content.vacancyKm);
						//行驶公里
						baseForm.findField('runKm').setValue(content.runKm);
						//接送货员
						baseForm.findField('receiverCode').setValue(content.receiverCode);
						//司机选择器
						var cmbDriverCode = baseForm.findField('driverCode');
						cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : content.driverCode}});
						cmbDriverCode.setValue(content.driverCode);
						//车牌号选择器
						var cmbVehicleNo = baseForm.findField('vehicleNo');
						cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : content.vehicleNo}});
						cmbVehicleNo.setValue(content.vehicleNo);
						//取得明细grid
						var grid = Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
						var data = result.vo.feeDetailList;
						grid.getStore().loadData(data,false);
						
						var total = grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');
						window.center().show();
						
						//车型选择器
						var cmbVehicleTypeLength = baseForm.findField('vehicleTypeLength');
						cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthName' : content.vehicleLengthName}});		
						cmbVehicleTypeLength.setValue(content.vehicleTypeLength);
					},
					failure: function(response){
						 var result = Ext.decode(response.responseText);
						 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result);
					}
				});
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.isDeleteSignInfo'),function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.signBillNo': grid.getStore().getAt(rowIndex).get('signBillNo')
							},
							url: management.realPath('deleteFocusSignBill.action'),
							success: function(response){
								 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.deleteSuccess'), 'ok', 1000);
								 management.focusSignBillGrid.store.load(); 
							},
							failure: function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteFailure') + result);
							},
							exception: function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.alert'),json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: 'id',
		dataIndex: 'id',
		hidden : true
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverCode'),//司机
		dataIndex: 'driverCode',
		editor: {
			xtype: 'commonowndriverselector',
			readOnly: true,
			fieldLabel: ''
		},
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		dataIndex: 'driverName',		
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		dataIndex: 'signBillDate',
		renderer: Ext.util.Format.dateRenderer('Y-m-d'),
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		dataIndex: 'vehicleTypeLength',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.runKm'),//行驶公里<br/>(公里)
		dataIndex: 'runKm',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.waybillQtyTotal'),//接货票数
		dataIndex: 'waybillQtyTotal',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.volume'),//体积<br/>(方)
		dataIndex: 'volume',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.weight'),//重量<br/>(公斤)
		dataIndex: 'weight',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.upstairsBillQty'),//上楼接货票数
		dataIndex: 'upstairsBillQty',
		flex:0.9
	},{
		header: management.signBill.i18n('foss.management.signBill.label.singleReceiveBillQty'),//单独接货票数
		dataIndex: 'singleReceiveBillQty',
		flex:0.9
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.FocusSignBillStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins: 'pagesizeplugin'
		});
		management.focusSignBillPagingBar = me.bbar;
		management.focusSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	},
	dockedItems:[{
	   xtype:'toolbar',
	   dock:'top',
	   layout:'column',
	   defaultType:'button',
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.01
	   },{
		   text:  management.signBill.i18n('foss.management.button.add'),//新增
		   disabled: !management.signBill.isPermission('management/addFocusSignBillButton'),
		   hidden: !management.signBill.isPermission('management/addFocusSignBillButton'),
		   columnWidth:.08,
		   handler:function(){
			   var window= Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID')==null ? Ext.create('Foss.management.FocusSignBillCostWindow'): Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID');
			   var form = Ext.getCmp('Foss_management_FocusSignBillCostForm_ID');
			   var grid = Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
			   form.getForm().reset();
			   grid.getStore().removeAll();
			   management.focusSignBillGrid.store.load();
			   var total = grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');
			   window.center().show();
			   Ext.getCmp('Foss_management_FocusSignBillCostForm_ID').isAddNew = true;
		   }
	   }]
	},{
	   xtype:'toolbar',
	   dock:'bottom',
	   layout:'column',
	   items:[{
		   xtype:'container',
		   columnWidth:1,
		   layout:'column',
		   style: 'background-color:#DDDDDD;',
		   defaults: {
			    xtype: 'displayfield',
			    allowBlank: true,
			    fieldStyle:'padding:0px 10px;',
		        style: 'font-weight:bold;'
		    },
		   items:[{
			   labelWidth: 55,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalDrivers'),//司机数
		       name: 'totalDrivers',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalBills'),//票数合计
		       name: 'totalBills',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalVolume'),//体积合计
		       name: 'totalVolume',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWeight'),//重量合计
		       name: 'totalWeight',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWaybillQty'),//接货票数合计
		       name: 'totalWaybillQty',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalupstairsBillQty'),//上楼票数合计
		       name: 'totalupstairsBillQty',
		       value: '0'
		   },{
			   labelWidth: 120,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalSingleReceiveBillQty'),//单独接货票数合计
		       name: 'totalSingleReceiveBillQty',
		       value: '0'
		   }]
	   }]
	}]
});

//集中接货签单费用录入表单FORM
Ext.define('Foss.management.FocusSignBillCostForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss_management_FocusSignBillCostForm_ID',
	defaultType: 'textfield',
	columnWidth:1,
	layout:'column',
	isAddNew: null,
	defaults:{
		labelWidth: 85,
		margin: '0 5 0 5'
	},
	items:[{
		fieldLabel: 'id',
		name: 'id',
		hidden: true
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		name: 'signBillNo',
		maxLength : 20,
		allowBlank: false,
		columnWidth:.25
	},{
		xtype:'commonowndriverselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		name: 'driverCode',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		xtype: 'datefield',
		name: 'signBillDate',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		allowBlank: false,
		format: 'Y-m-d',
		altFormats: 'Y,m,d,|Y.m.d',
		columnWidth:.25
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.receiverCode'),	
		name: 'receiverCode',
		maxLength:30,
		columnWidth:.25
	},{
		xtype: 'commonowntruckselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		name: 'vehicleNo',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25,
		listeners:{
			'select':function(text,op){			
			if (text.getValue().trim()!=null&&text.getValue().trim()!='') {
				 var params={vo:{sendSignBillDto:{vehicleNo:text.getValue()}}};	//车牌号码			
			     Ext.Ajax.request({
		       	  url: management.realPath('querySendSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		//车型选择器
		       		
					var cmbVehicleTypeLength = Ext.getCmp('Foss_management_FocusSignBillCostForm_ID').getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.sendSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.sendSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
			       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });
				
				}	
			
	 		}
		}
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		name: 'vehicleTypeLength',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		allowNegative: false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vacancyKm'),//空驶公里
		name: 'vacancyKm',
		maxLength : 8,
		regex:/^-?\d+\.?\d{0,1}$/,
        regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
        validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			}
			return true;
		},
		columnWidth:.25
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		allowNegative: false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.runKmInfo'),//行驶公里
		name: 'runKm',
		maxLength : 8,
		regex:/^-?\d+\.?\d{0,1}$/,
        regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
        allowBlank: false,
        validator : function(value) {
			if(value != '' && value <= 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			}
			return true;
		},
		columnWidth:.25
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		//加入全局变量
		//management.FocusSignBillCostForm = me;
	}
	
});

//表单按钮区
Ext.define('Foss.management.FocusSignBillCostBtnArea',{
	extend: 'Ext.panel.Panel',
	defaultType:'button',
	defaults:{
		margin: '5 7 5 7'
	},
	layout:'column',
	items:[{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.4
	},{
		text:  management.signBill.i18n('foss.management.button.save'),
		disabled: !management.signBill.isPermission('management/saveFocusSignBillButton'),
		hidden: !management.signBill.isPermission('management/saveFocusSignBillButton'),
		cls:'yellow_button',
		columnWidth:.1,
		handler: function(){
			var window = this.up('window');
			var baseForm = window.down('form').getForm();
			//如果签单信息验证无效,则退出
			if(!baseForm.isValid()){
				Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'),'集中接货签单信息填写不正确！');
				return;
			}
			//获取费用列表表格
			var grid= Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
			//获取费用列表表格store
			var store=grid.getStore();
			//如果签单明细不为空
			if(store != null && store.getCount() != 0){
				//获取表单值
				var formValues = baseForm.getValues();
				//获取所有数据
				var records = store.getRange();
				for(var i = 0; i < records.length; i++){
					var record = records[i];
					driverRoyalty = record.get('driverRoyalty');
					if(driverRoyalty < 0 || driverRoyalty > 9999999) {
						Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'), management.signBill.i18n('foss.management.signBill.exception.signDetailuseTruckFeeError'));
						return;
					}
				}
				//获取删除数据
				var deletes = store.getRemovedRecords();
				//获取新增数据
				var adds = store.getNewRecords();
				//获取更新数据
				var updates = store.getUpdatedRecords();
				//定义删除数组
				var deleteArray=new Array();
				//遍历删除数据，只需要加入对应即可
				Ext.each(deletes, function(item) {
					deleteArray.push(item.data['id']);
				});
				//定义新增数组
				var addArray=new Array();
				//新增验证
				var addValidate = true;
				Ext.each(adds, function(item) {
					//判断运单号是否为空，如果为空则剔除掉
					if(Ext.isEmpty(item.data['wayBillNo'])){
						 adds.pop();
						 store.remove(item);
						 return;
					}else if(Ext.isEmpty(item.data['useTruckFee'])){
						//不为空则验证必输项
						addValidate = false;
						return;
					}else if(Ext.isEmpty(item.data['driverRoyalty'])){
						//不为空则验证必输项
						addValidate = false;
						return;
					}else{
						//判断是否上楼
						if(item.data['isUpstairs']){
							item.data['isUpstairs']= 'Y';
						}else{
							item.data['isUpstairs']= 'N';
						}
						//判断是否单独接货
						if(item.data['isSingleReceive']){
							item.data['isSingleReceive']= 'Y';
						}else{
							item.data['isSingleReceive']= 'N';
						}
						//是否现付
						if(item.data['isCashPayment']){
							item.data['isCashPayment']= 'Y';
						}else{
							item.data['isCashPayment']= 'N';
						}
						//加入到增加数组中
						addArray.push(item.data);
					}
				});
				//判断列表中是否有记录
				if(store.getCount() == 0){
					Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'),management.signBill.i18n('foss.management.signBill.exception.enterOneSignDetil'));
					return;
				}
				//如果新增的校验不通过，能提示
				if(!addValidate){
					Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'), management.signBill.i18n('foss.management.signBill.exception.enterAddSignDetailuseTruckFee'));
					return;
				}
				//定义更新数组
				var updateArray=new Array();
				//修改验证
				var updateValidate = true;
				//遍历更新数据，设置boolean类型的值为:Y或N
				Ext.each(updates, function(item) {
					if(Ext.isEmpty(item.data['useTruckFee'])){
						updateValidate = false;
						return;
					}else if(Ext.isEmpty(item.data['driverRoyalty'])){
						updateValidate = false;
						return;
					}else{
						//是否上楼，转换为'Y'/'N'
						if(item.data['isUpstairs']){
							item.data['isUpstairs']= 'Y';
						}else{
							item.data['isUpstairs']= 'N';
						}
						//是否单独接货，转换为'Y'/'N'
						if(item.data['isSingleReceive']){
							item.data['isSingleReceive']= 'Y';
						}else{
							item.data['isSingleReceive']= 'N';
						}
						//是否现付
						if(item.data['isCashPayment']){
							item.data['isCashPayment']= 'Y';
						}else{
							item.data['isCashPayment']= 'N';
						}
						updateArray.push(item.data);
					}
				});
				//如果修改的校验不通过，能提示
				if(!updateValidate){
					Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'),management.signBill.i18n('foss.management.signBill.exception.enterUpdateSignDetailuseTruckFee'));
					return;
				}
				//获取操作类型：是否新增
				var operType = Ext.getCmp('Foss_management_FocusSignBillCostForm_ID').isAddNew;
				//拼装成jsonData
				var jsonData={vo:{
					'operType': operType,
					focusSignBillEntity:formValues,
					addList:addArray,
					updateList:updateArray,
					deleteIdList:deleteArray}
				};
				Ext.Ajax.request({
				    url: management.realPath('saveFocusSignBill.action'),
				    method: 'POST',
				    jsonData: jsonData,
				    success: function(response){
				    	Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);
				    	//保存成功后关闭窗口
				    	window.close();
				    },
				    exception: function(response){
				    	console.log('failure');
				    }
				});
			}else{
				//如果签单费用明细列表中无数据，不允许保存
				Ext.Msg.alert(management.signBill.i18n('foss.management.signBill.exception.saveFailure'),management.signBill.i18n('foss.management.signBill.exception.enterOneSignDetil'));
			}
		}
	},{
		text:management.signBill.i18n('foss.management.button.cancel'),
		columnWidth:.1,
		handler: function(){
			Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID').hide();
		}
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.4
	}]
});

//集中接货签单运单费用列表
Ext.define('Foss.management.FocusSignBillCostGrid',{
	extend: 'Ext.grid.Panel',
	id:'Foss_management_focusSignBillCostGrid_ID',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//增加表格列的分割线
	columnLines: true,
	stripeRows: false,
	closeAction: 'destroy',
    frame: true,
    title: management.signBill.i18n('foss.management.FocusSignBillCostGrid.title'),//运单费用列表      定义表格的标题
	columns:[{
		xtype:'actioncolumn',
		width: 40,		
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			handler: function(grid, rowIndex, colIndex) {
				//定义按钮为中文
			
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteSelect'),function(btn,text){
					if(btn == 'yes'){
						grid.getStore().removeAt(rowIndex);
					}
				});
			}
		}]
	},{
		header: 'id',
		dataIndex: 'id',
		hidden : true
	},{
		header: management.signBill.i18n('foss.management.signBill.label.wayBillNo'),//运单号
		dataIndex:'wayBillNo',
		allowBlank: false,
		editor: {
			xtype: 'textfield',
			ignoreNoChange : true,
			minLength : 8,
			maxLength : 9,
			vtype : 'waybill'
		},
		width: 80	
	},{
		header:  management.signBill.i18n('foss.management.signBill.label.customerName'),//客户名称
		dataIndex:'customerName',
		width: 80	
	},{
		header:  management.signBill.i18n('foss.management.signBill.label.goodsQty'),//件数
		dataIndex:'goodsQty',
		width: 60	
	},{
		header: management.signBill.i18n('foss.management.signBill.label.goodsPackage'),//包装
		dataIndex:'goodsPackage',
		width: 80	
	},{
		xtype: 'checkcolumn',
		header:management.signBill.i18n('foss.management.signBill.label.isCashPayment'),//是否<br/>现付
		dataIndex:'isCashPayment',
		stopSelection: false,
		//接收所有的事件,不做处理,达到readOnly的目的
//		processEvent:function(){},
		width: 40
	},{
		header:management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex:'useTruckOrgName',
		width: 60	
	},{
		header:management.signBill.i18n('foss.management.signBill.label.volume'),//体积<br/>(方)
		dataIndex:'volume',
		width: 60	
	},{
		header: management.signBill.i18n('foss.management.signBill.label.totalFee'),//开单金额<br/>(元)
		dataIndex:'totalFee',
		width: 60	
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckFee'),//用车费用<br/>(元)
		dataIndex:'useTruckFee',
		editor: {
             xtype: 'textfield',
             regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
             regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
             allowBlank: false
        },
        width: 60
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverRoyalty'),//司机提成<br/>(元)
		dataIndex:'driverRoyalty',
		editor: {
            xtype: 'textfield',
            regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
            regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
            allowBlank: false,
            validator : function(value) {
				if(value != '' && value < 0) {
					return management.signBill.i18n('foss.management.signBill.label.regexText');
				}
				if(value > 9999999) {
					return management.signBill.i18n('foss.management.signBill.label.regexText');
				}
				return true;
			}
		},	
		width: 60	
	},{
		header: management.signBill.i18n('foss.management.signBill.label.notes'),//备注
		dataIndex:'notes',
		editor: {
            xtype: 'textfield'
		},	
		width: 120	
	},{
		xtype: 'checkcolumn',
	    header: management.signBill.i18n('foss.management.signBill.label.isUpstairs'),//是否<br/>上楼
	    dataIndex: 'isUpstairs',
	    stopSelection: false,
	    width: 40	
	},{
		xtype: 'checkcolumn',
        header: management.signBill.i18n('foss.management.signBill.label.isSingleReceive'),//单独<br/>接货
        dataIndex: 'isSingleReceive',
        /*renderer: function(v){
	    	if (v == 'Y') {
	    		this = true;
	    	}else{
	    		console.log(v);
	    		this = false;
	    	}
	    },*/
        stopSelection: false,
        width: 40	
	}],
	plugins: [
	          Ext.create('Ext.grid.plugin.CellEditing', {
	        	  clicksToEdit: 1,
	        	  //编辑完成时，触发该事件
	        	  listeners: {
	        		  edit: function(editor, e){
	        			  //只有当运单编辑完成时，加载运单相关数据
	        			  if(e.field == 'wayBillNo'){
	        				  //当运单为空,值复原
	        				  if(Ext.isEmpty(e.value)){
	        					  Ext.ux.Toast.msg(management.signBill.i18n('foss.management.signBill.alert'), management.signBill.i18n('foss.management.signBill.exception.waybillNoNotNull'), 'error', 1000);
	        					  e.record.data[e.field] = e.originalValue;
	        					  e.grid.getView().refresh();
	        					  return;
	        				  }else if(e.originalValue == e.value){
	        					  //如果值未做改变，直接返回
	        					  return;
	        				  }else{
	        					  //遍历是否存在重复的运单号
	        					  var flag = false; 
	        					  e.grid.getStore().each(function(item) {
	        						  //如果不是同一个record且运单号相同，则认为是相同的
	        						  if (item.data[e.field] == e.value && item.id != e.record.id) {
	        							  flag = true;
	        							  return;
	        						  }
	        					  });
	        					  //如果存在重复的则不发送请求
	        					  if(flag){
	        						  e.record.data[e.field] = e.originalValue;
	        						  e.grid.getView().refresh();
	        						  Ext.ux.Toast.msg(management.signBill.i18n('foss.management.signBill.alert'), management.signBill.i18n('foss.management.signBill.exception.gridexistwaybillNo'), 'error', 1000);
	        						  return;
	        					  }else{
	        						  Ext.Ajax.request({
	        							  url: management.realPath('queryWayBill.action'),
	        							  params: {
	        								  'vo.wayBillNo': e.value
	        							  },
	        							  success: function(response){
		      	        				      var result = Ext.decode(response.responseText);
		      	        				      if(result.vo.waybillInfo != null){
		      	        				    	  e.record.data['customerName'] = result.vo.waybillInfo.deliveryCustomerName; //客户名称
		      	        				    	  e.record.data['goodsQty'] = result.vo.waybillInfo.goodsQtyTotal; //件数
		      	        				    	  e.record.data['goodsPackage'] = result.vo.waybillInfo.goodsPackage; //包装
		      	        				    	  e.record.data['isCashPayment'] = result.vo.waybillInfo.paidMethod; //是否现付
		      	        				    	  e.record.data['weight'] = result.vo.waybillInfo.goodsWeightTotal;   // 货物总重量
		      	        				    	  e.record.data['volume'] = result.vo.waybillInfo.goodsVolumeTotal;// 货物总体积
		      	        				    	  e.record.data['totalFee'] = result.vo.waybillInfo.totalFee;// 总费用
		      	        				    	  e.record.data['useTruckOrgCode'] = result.vo.waybillInfo.receiveOrgCode; //用车部门Code
		      	        				    	  e.record.data['useTruckOrgName'] = result.vo.useTruckOrgName; //用车部门Name
		      	        				    	  e.grid.getView().refresh();
		      	        				      }else{
		      	        				    	  Ext.ux.Toast.msg(management.signBill.i18n('foss.management.signBill.alert'), management.signBill.i18n('foss.management.signBill.exception.notwaybillInfo'), 'error', 1000);
		      	        				    	  e.record.data[e.field] = e.originalValue;
		      	        	        			  e.grid.getView().refresh();
		      	        				      }
	        							  },
	        							  failure: function(response){
	        								  var text = response.responseText;
	        								  Ext.ux.Toast.msg(management.signBill.i18n('foss.management.signBill.alert'), management.signBill.i18n('foss.management.signBill.exception.notwaybillInfo'), 'error', 1000);
	        								  e.record.data[e.field] = e.originalValue;
	        								  e.grid.getView().refresh();
	        							  },
	        							  exception : function(response) {
	        								  var json = Ext.decode(response.responseText);
	        								  Ext.ux.Toast.msg(management.signBill.i18n('foss.management.signBill.alert'), management.signBill.i18n('foss.management.signBill.exception.notwaybillInfo'), 'error', 1000);
	        								  e.record.data[e.field] = e.originalValue;
	        								  e.grid.getView().refresh();
	        							  }
	        						  });
	        					  }
	        				  }
	        			  }else{
	        				  //如果非编辑运单单元格不做处理
	        				  return ;
	        			  }
	        		  }
	        	  }
	          })
	],
	dockedItems:[{
	   xtype:'toolbar',
	   dock:'top',
	   layout:'column',
	   defaultType:'button',
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.01
	   },{
		   text:management.signBill.i18n('foss.management.button.add'),//新增
		   columnWidth:.08,
		   handler:function(){
			   var record = Ext.create('Foss.management.FocusSignBillCostModel', {
				   isUpstairs: false,
				   isSingleReceive: false,
				   isCashPayment: false
               });
			  var store = this.up('grid').getStore();
			  store.insert(store.getCount(), record);
		   }
	   }]
	},{
	   xtype:'toolbar',
	   dock:'bottom',
	   layout:'column',
	   items:[{
		   xtype:'container',
		   columnWidth:1,
		   layout:'column',
		   style: 'background-color:#DDDDDD;',
		   defaults: {
			    xtype: 'displayfield',
			    allowBlank: true,
			    fieldStyle:'padding:0px 10px;',
		        style: 'font-weight:bold;'
		    },
		   items:[{
			   labelWidth: 75,
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalbillQty'),//合计(票数)			   
		       name: 'totalBills',
		       value: '0'
		   },{
			   labelWidth: 75,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalPieces'),//合计(件数)
		       name: 'totalPieces',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalRoyalty'),//司机提成总额
		       name: 'totalRoyalty',
		       value: '0'
		   },{
			   labelWidth: 100,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalUpstairs'),//合计(上楼票数)
		       name: 'totalUpstairs',
		       value: '0'
		   },{
			   labelWidth: 100,
		       fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalSingleReceive'),//合计(单独接货)
		       name: 'totalSingleReceive',
		       value: '0'
		   }]
	   }]
	}],	
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.FocusSignBillCostStore');
		me.callParent([cfg]);
	}
});

//集中接货签单录入窗口window
Ext.define('Foss.management.FocusSignBillCostWindow',{
	extend:'Ext.window.Window',
	id: 'Foss_management_FocusSignBillCostWindow_ID',
	title: management.signBill.i18n('foss.management.signBill.FocusSignBillCostWindow.title'),//录入集中接货签单	
	width:950,
	modal: true,
	recording: null,
	buttonArea: null,
	costInfoGrid: null,
	//创建表单form
	createRecordingPanel: function() {
		if(this.recording) {
			return this.recording;
		}
		this.recording = Ext.create('Foss.management.FocusSignBillCostForm');
		return this.recording;
	},
	//创建费用列表
	createCostGrid: function() {
		if(this.costInfoGrid) {
			return this.costInfoGrid;
		}
		this.costInfoGrid = Ext.getCmp('Foss_management_focusSignBillCostGrid_ID')==null?Ext.create('Foss.management.FocusSignBillCostGrid'):Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
		return this.costInfoGrid;
	},
	//创建按钮
	createButtonArea: function() {
		if(this.buttonArea) {
			return this.buttonArea;
		}
		this.buttonArea = Ext.create('Foss.management.FocusSignBillCostBtnArea');
		return this.buttonArea;
	},
	//初始化创建控件
	initAllComponent: function() {
		this.createRecordingPanel();
		this.createButtonArea();
		this.createCostGrid();
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.initAllComponent();
		me.add([me.recording, me.costInfoGrid, me.buttonArea]);
	}
});

//集中接货页签
Ext.define('Foss.management.FocusSignBillPanel',{
	extend: 'Ext.panel.Panel',
	cls: 'panelContentNToolbar',
	bodyCls:'panelContentNToolbar-body',
	margin:'10 5 10 10',
	layout: 'auto',
	items:[
        Ext.create('Foss.management.FocusSignBillQueryForm'),
        Ext.create('Foss.management.FocusSignBillGrid')
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*********************************END    集中接货签单*******************************************/


/*********************************BEGIN   派送签单*******************************************/
Ext.define('Foss.management.SendSignBillModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	        {name:'id', type:'string'},
	        {name:'signBillNo', type:'string'},//签单编号
	        {name:'handoverNo', type:'string'},//交接单号        
		    {name:'useTruckOrgCode', type:'string'},//用车部门
		    {name:'useTruckOrgName', type:'string'},//用车部门名称
		    {name:'signBillDate', type:'string',
		    	convert: function(value) {
					if (value != null) {
						var date = new Date(value);						
						return Ext.Date.format(date,'Y-m-d');
					} else {
						return null;
					}
				}
		    },//日期			  
		    {name:'driverCode', type:'string'},//司机			    
		    {name:'driverName', type:'string'},//司机姓名			  
		    {name:'vehicleNo', type:'string'},//车牌号
		    {name:'vehicleTypeLength', type:'string'},//车型
		    {name:'vehicleTypeLengthName', type:'string'},//车型名称
		    {name:'transferDistance'},// 转货里程	
		    {name:'sendBillQty'},//派送票数	
		    {name:'inStockBillQty'},// 进仓票数
			{name:'upstairsBillQty'},// 上楼票数
			{name:'distance'},// 里程
			{name:'isSingleSend', type:'string'},// 单独配送
			{name:'upstairsFee'},// 上楼费
			{name:'noInStockBillFee'},// 非进仓票数费用
			{name:'inStockBillFee'},// 进仓票数费用
			{name:'driverRoyaltyAmount'},// 司机实际提成总额	
			{name:'otherFee', type:'string'},//其它金额	、
			{name:'volume'},//体积
			{name:'weight'},//重量
			{name:'useTruckFee'},// 用车费用划分
			{name:'sendNo'},//派送进仓编号
			{name:'receiverCode'},//接货员姓
			{name:'receiverName'},//接货员姓名
			{name:'upstairsFeeRoyalty'},//上楼费提成
			{name:'haulBackBillQty'}//拉回票数
		 
	]
});
/**
 * 派送签单store
 */
Ext.define('Foss.management.SendSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.SendSignBillModel',
//    pageSize:10,
    autoLoad: false,
    proxy: {
      	type: 'ajax',
        url: management.realPath('querySendSignBill.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.sendSignBillList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        },
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result.message);
            }
        }
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {				
			var queryForm = management.sendSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.sendSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
						'vo.sendSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
						'vo.sendSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
						'vo.sendSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
						'vo.sendSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
						'vo.sendSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
					}
				});	
			}
		},
		datachanged: function(store, operation, epots){
			var queryForm = management.sendSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();		
				var useTruckOrgCode = queryParams.useTruckOrgCode;
				var driverCode = queryParams.driverCode;
				var vehicleNo = queryParams.vehicleNo;
				var vehicleTypeLength = queryParams.vehicleTypeLength;					
				var beginSignBillDate = queryParams.beginSignBillDate;
				var endSignBillDate = queryParams.endSignBillDate;	

				var array = {
						vo:{
							sendSignBillDto:{
								useTruckOrgCode : useTruckOrgCode,
								driverCode : driverCode,
								vehicleNo : vehicleNo,
								vehicleTypeLength : vehicleTypeLength,
								beginSignBillDate : beginSignBillDate,
								endSignBillDate : endSignBillDate
							}
						}
				};
				Ext.Ajax.request({
					url : management.realPath('querySendSignBillByFee.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);							
						//在json中取出对象
						var sendSignBillDto = json.vo.sendSignBillDto;						
						if(sendSignBillDto != null){
						  						
						   var driverCount=0;//司机个数
						   var  sendBillQtyTotal=0;//总派送票数
						   var  volumeTotal=0;//总体积
						   var weightTotal=0;//总重量	
						   var inStockBillQtyTotal=0;// 总进仓票数
						   var upstairsFeeTotal=0;// 总上楼费
						   var  distanceTotal=0;// 总里程
						   if(sendSignBillDto.sendBillQtyTotal!=null){
							   sendBillQtyTotal=sendSignBillDto.sendBillQtyTotal;	
						   }
						   if(sendSignBillDto.weightTotal!=null){
							   weightTotal=sendSignBillDto.weightTotal;//总重量		
						   }
						   if(sendSignBillDto.volumeTotal!=null){
							   volumeTotal=sendSignBillDto.volumeTotal;//总体积
						   }
						   if(sendSignBillDto.inStockBillQtyTotal!=null){
							   inStockBillQtyTotal=sendSignBillDto.inStockBillQtyTotal;	//总票数
						   }
						   if(sendSignBillDto.upstairsFeeTotal!=null){
							   upstairsFeeTotal=sendSignBillDto.upstairsFeeTotal;//总上楼费		
						   }
						   if(sendSignBillDto.distanceTotal!=null){
							   distanceTotal=sendSignBillDto.distanceTotal;//总里程
						   }
						   driverCount=sendSignBillDto.driverCount;//司机个数
						   
							var count = 0;								
							var toolbarArray = management.sendSignBillGrid.getDockedItems('toolbar[dock="bottom"]')[0].items.items;
							for(var j=0;j<toolbarArray.length;j++){
								if(count==0){
									toolbarArray[j].setValue(driverCount);
								}else if(count==1){
									toolbarArray[j].setValue(sendBillQtyTotal);
								}else if(count==2){
									toolbarArray[j].setValue(volumeTotal);
								}else if(count==3){
									toolbarArray[j].setValue(weightTotal);
								}else if(count==4){
									toolbarArray[j].setValue(inStockBillQtyTotal);
								}else if(count==5){
									toolbarArray[j].setValue(upstairsFeeTotal);
								}else if(count==6){
									toolbarArray[j].setValue(distanceTotal);
								}								
								count ++;
							}
							 				    	
						}else{
							top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
					}
				});
			}
	
			
		
		},			
		load: function(store,records,successful,epots){
		 	if(store.getCount() == 0){
				 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.notFindData'), 'ok', 1000);
			}
		}		
		
	}
});



Ext.define('Foss.management.SendSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth: 1,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	defaults:{
		labelWidth: 80,
		margin: '0 10 0 10'
	},
	items:[{
//		xtype:'commonsaledepartmentselector',
//		name: 'useTruckOrgCode',
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		name:'useTruckOrgCode',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		columnWidth: .33
	},{
		name: 'driverCode',
		xtype:'commonowndriverselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		columnWidth: .33
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',			
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		columnWidth: .33
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		name: 'vehicleTypeLength',
		columnWidth: .33
	},{
		fieldLabel:management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		xtype: 'rangeDateField',		
		dateType: 'datetimefield_date97',
		time:false,
		dateRange : 31,
		format:'Y-m-d',
		fieldId: 'Foss_management_querySendSignBillForm_createTime',
		disallowBlank:true,
		fromName: 'beginSignBillDate',		
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),	
		toName: 'endSignBillDate',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),		
		allowBlank: false,
		columnWidth: .50
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:management.signBill.i18n('foss.management.button.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();				
				//重新初始化交接时间
				this.up('form').getForm().findField('beginSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
				this.up('form').getForm().findField('endSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text:management.signBill.i18n('foss.management.button.search'),//查询
			disabled: !management.signBill.isPermission('management/querySendSignBillButton'),
			hidden: !management.signBill.isPermission('management/querySendSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.sendSignBillPagingBar.moveFirst();
				}					
			}
		},{
			text:management.signBill.i18n('foss.management.button.export'),//导 出
			disabled: !management.signBill.isPermission('management/queryExportSendSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryExportSendSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					var queryParams = management.sendSignBillQueryForm.getValues();		
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
		    			url: management.realPath('queryExportSendSignBill.action'),	 
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params : {
		    				'vo.sendSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
							'vo.sendSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
							'vo.sendSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
							'vo.sendSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
							'vo.sendSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
							'vo.sendSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
		    			},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('导出失败',result.message);
		    			}
	    			});
				}
				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		management.sendSignBillQueryForm = me;  //加入全局变量中
	}
});


//派送签单grid
Ext.define('Foss.management.SendSignBillQueryGrid',{
	extend:'Ext.grid.Panel',
	title: management.signBill.i18n('foss.management.signBill.SendSignBillQueryGrid.title'),//派送签单列表
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	columns: [{
		xtype:'actioncolumn',
		width:60,
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !management.signBill.isPermission('management/updateSendSignBillButton'),
			hidden: !management.signBill.isPermission('management/updateSendSignBillButton'),
			tooltip: management.signBill.i18n('foss.management.button.operate'),//编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				management.editSendSignBillWindow = Ext.create('Foss.management.editSendSignBillWindow');
				var record = grid.getStore().getAt(rowIndex);
				management.editSendSignBillForm.getForm().reset();				
				var editSendForm=management.editSendSignBillForm.getForm();
				editSendForm.findField('id').setValue(record.data.id);//id
				editSendForm.findField('handoverNo').setValue(record.data.handoverNo);//交接单号
				editSendForm.findField('signBillNo').setValue(record.data.signBillNo);//签单编号	
				editSendForm.findField('useTruckOrgName').setValue(record.data.useTruckOrgName);//用车部门名称				
				editSendForm.findField('driverName').setValue(record.data.driverName);//司机姓名
				editSendForm.findField('signBillDate').setValue(new Date(record.data.signBillDate));//日期
				editSendForm.findField('sendBillQty').setValue(record.data.sendBillQty);//派送票数
				editSendForm.findField('volume').setValue(record.data.volume);//体积
				editSendForm.findField('weight').setValue(record.data.weight);//'重量				
				editSendForm.findField('inStockBillQty').setValue(record.data.inStockBillQty);//进仓票数
				editSendForm.findField('upstairsBillQty').setValue(record.data.upstairsBillQty);//上楼票数
				editSendForm.findField('distance').setValue(record.data.distance);//里程
				editSendForm.findField('upstairsFee').setValue(record.data.upstairsFee);//上楼费				
				editSendForm.findField('isSingleSend').setValue(record.data.isSingleSend);//单独配送
				editSendForm.findField('sendNo').setValue(record.data.sendNo);//派送进仓编号			
				//editSendForm.findField('receiverCode').setValue(record.data.receiverCode);//接货员
				editSendForm.findField('receiverName').setValue(record.data.receiverName);//接货员
				editSendForm.findField('haulBackBillQty').setValue(record.data.haulBackBillQty);//拉回票数
				//部门公共选择器
				var cmbUseTruckOrgCode = management.editSendSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'commonOrgVo.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);
				
				//司机选择器
				var cmbDriverCode = management.editSendSignBillForm.getForm().findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driverCode}});
				cmbDriverCode.setValue(record.data.driverCode);
				
				//Z车牌号				
				var cmbVehicleNo = management.editSendSignBillForm.getForm().findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.data.vehicleNo}});
				cmbVehicleNo.setValue(record.data.vehicleNo);
				
				//车型选择器
				var cmbVehicleTypeLength = management.editSendSignBillForm.getForm().findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.data.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.data.vehicleTypeLength);
				
				//接货员
				var cmbReceiverCode=management.editSendSignBillForm.getForm().findField('receiverCode');
				cmbReceiverCode.getStore().load({params:{'employeeVo.employeeDetail.department.unifiedCode' : record.data.receiverCode}});		
				cmbReceiverCode.setValue(record.data.receiverCode);
				
				management.editSendFeeInfoForm.getForm().reset();
				management.editSendFeeInfoForm.loadRecord(record);
				management.editSendSignBillWindow.show(); 
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteSelect'),function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.sendSignBillEntity.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteSendSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.deleteSuccess'), 'ok', 1000);
								 management.sendSignBillPagingBar.moveFirst();			        
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteFailure') + result);
							},
							exception : function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('exception',json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverCode'),//司机
		dataIndex: 'driverCode',		
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		dataIndex: 'driverName',		
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		dataIndex: 'signBillDate',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		dataIndex: 'vehicleTypeLengthName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.sendBillQty'),//派送票数
		dataIndex: 'sendBillQty',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		dataIndex: 'volume',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		dataIndex: 'weight',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.inStockBillQty'),//进仓票数
		dataIndex: 'inStockBillQty',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.upstairsBillQtyInfo'),//上楼票数
		dataIndex: 'upstairsBillQty',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.upstairsFeeInfo'),//上楼费
		dataIndex: 'upstairsFee',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.distance'),//里程
		dataIndex: 'distance',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.haulBackBillQty'),//拉回票数
		dataIndex: 'haulBackBillQty',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.isSingleSend'),//是否单独配送
		dataIndex: 'isSingleSend',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value=='Y'){
					return management.signBill.i18n('foss.management.signBill.label.yes');//是
				}else{
					return management.signBill.i18n('foss.management.signBill.label.no');//否
				}
			}
		},
		flex:0.7
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,			 
		     style: 'font-weight:bold;'
		   },
		   items:[{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalDrivers'),//司机数	
			   labelWidth:60,
			   width:90,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineDistanceTotal'),//派送票数合计
			   labelWidth:100,
			   width:160,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:management.signBill.i18n('foss.management.signBill.label.totalVolume'),//体积合计	
			   labelWidth:70,	
			   width:140,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWeight'),//重量合计	
			   labelWidth:70,	
			   width:140,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.billQtyCount'),//进仓票数合计
			   labelWidth:100,	
			   width:160,
			   dataIndex: 'billQtyCount'
		  },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDurationTotal'),//上楼费合计
			   labelWidth:90,
			   width:140,
			   dataIndex: 'useTruckDurationTotal'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineTotal'),//里程合计
			   labelWidth:70,
			   width:130,
			   dataIndex: 'otherFeeTotal'
		   }]
		}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.SendSignBillStore');
		me.tbar=[{
			xtype: 'button', 
			disabled: !management.signBill.isPermission('management/addSendSignBillButton'),
			hidden: !management.signBill.isPermission('management/addSendSignBillButton'),
			text: management.signBill.i18n('foss.management.button.add'),//新增
			handler: function() {
				 management.sendSignBillEditWindow = Ext.create('Foss.management.AddSendSignBillEditWindow');
				 management.sendSignBillEditWindow.show();  
				 management.addSendSignBillForm.getForm().reset();
			      management.addSendFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins: 'pagesizeplugin'
		});	
		management.sendSignBillPagingBar=me.bbar;
		management.sendSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});


Ext.define('Foss.management.addSendSignBillForm',{
	   extend: 'Ext.form.Panel',
	   title: management.signBill.i18n('foss.management.signBill.addSendSignBillForm.title'),//录入派送签单 
	   layout:'column',	
	   width:760,	  
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 5 10',	
			labelWidth: 80,	
			anchor: '90%'					
		},
		items:[{			
			name:'handoverNo',
			fieldLabel:  management.signBill.i18n('foss.management.signBill.label.handoverNo'),//交接单编号
			allowBlank:true,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		},{
			name:'signBillNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners:{
				'blur':function(text,op){				
				if (text.getValue()!=null&&text.getValue()!='') {
					
					 var params={vo:{sendSignBillDto:{signBillNo:text.getValue()}}};	//配载车次号			
				     Ext.Ajax.request({
			       	  url: management.realPath('querySendSignBillBySignBillNo.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) {
			       		var result = Ext.decode(response.responseText);	//查询的结果
			       		var record=result.vo.sendSignBillEntity;		       		
			       		if(record!=null){
			       			var addSendForm=management.addSendSignBillForm.getForm();
				       		addSendForm.findField('useTruckOrgName').setValue(record.useTruckOrgName);//用车部门名称				
							addSendForm.findField('driverName').setValue(record.driverName);//司机姓名
							if(record.signBillDate!=null){
								addSendForm.findField('signBillDate').setValue(new Date(record.signBillDate));//日期
							}	
							addSendForm.findField('volume').setValue(record.volume);//体积
							addSendForm.findField('weight').setValue(record.weight);//'重量
							addSendForm.findField('sendBillQty').setValue(record.sendBillQty);//派送票数
							addSendForm.findField('haulBackBillQty').setValue(record.haulBackBillQty);//拉回票数
							//部门公共选择器
							var cmbUseTruckOrgCode = addSendForm.findField('useTruckOrgCode');
							cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.useTruckOrgCode}});
							cmbUseTruckOrgCode.setValue(record.useTruckOrgCode);
							
							//司机选择器
							var cmbDriverCode = addSendForm.findField('driverCode');
							cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.driverCode}});
							cmbDriverCode.setValue(record.driverCode);
							//车牌号				
							var cmbVehicleNo = addSendForm.findField('vehicleNo');
							cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.vehicleNo}});
							cmbVehicleNo.setValue(record.vehicleNo);
							
							//车型选择器
							var cmbVehicleTypeLength=addSendForm.findField('vehicleTypeLength');
							cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.vehicleTypeLength}});		
							cmbVehicleTypeLength.setValue(record.vehicleTypeLength);
			       		}			       		
			       		},
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			       	  },
			       	  exception : function(response) {
	    					var json = Ext.decode(response.responseText);
	    					Ext.Msg.alert('exception',json.message);
	    				}
			         });
					
					}	
				
		 		}
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.33,
			minHeight:25
		},{
			xtype:'dynamicorgcombselector',
			type : 'ORG',
			name:'useTruckOrgCode',
			salesDepartment : 'Y',
			transferCenter : 'Y',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		    blankText: '部门不能为空!',
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('name');
					this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
					
				}
			}
		},{
			name: 'useTruckOrgName',
			xtype: 'hiddenfield'
		},{
			xtype:'commonowndriverselector',
			name:'driverCode',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('name');
					this.up('form').form.findField('driverName').setValue(field.rawValue);
					
				}
			}
		},{
			name: 'driverName',
			xtype: 'hiddenfield'
		},{
	  		xtype: 'datefield',
	  		name: 'signBillDate',
	  		labelWidth: 60,	
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		allowBlank: false,
	  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
	  		columnWidth: .3
		},{
			xtype: 'commonowntruckselector',
			allowBlank:false,
			name:'vehicleNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
			allowBlank:false,
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners:{
				'select':function(text,op){			
				if (text.getValue().trim()!=null&&text.getValue().trim()!='') {
					
					 var params={vo:{sendSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
				     Ext.Ajax.request({
			       	  url: management.realPath('querySendSignBillByVehicleNo.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) {
			       		var result = Ext.decode(response.responseText);	//查询的结果
			       		//车型选择器
						var cmbVehicleTypeLength=management.addSendSignBillForm.getForm().findField('vehicleTypeLength');									
						cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.sendSignBillEntity.vehicleTypeLength}});		
						cmbVehicleTypeLength.setValue(result.vo.sendSignBillEntity.vehicleTypeLength);
			       	  },
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			       	  },
			       	  exception : function(response) {
	    					var json = Ext.decode(response.responseText);
	    					Ext.Msg.alert('exception',json.message);
	    				}
			         });
					
					}	
				
		 		}
			}
		},{
			xtype: 'commonvehicletypeselector',
			name: 'vehicleTypeLength',							
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
			allowBlank:false,
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			//labelWidth: 50,	
			columnWidth:.33
		},{
			xtype: 'numberfield',
			name:'sendBillQty',
			decimalPrecision: 0, 
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.sendBillQty'),//派送票数
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:  management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			
		},{	
			xtype: 'numberfield',
			name:'volume',
			allowDecimals: true, // 是否与许小数 
			decimalPrecision: 2, // 小数位精度 
			value:'0.00',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)	
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			allowBlank:true,
			columnWidth:.33,
			maxValue:99999.99,
//			parseValue : function(value) {
//		        value = parseFloat(Ext.Number.toFixed(parseFloat(value), 2));
//		        value = parseFloat(String(value).replace(this.decimalSeparator, '.'));
//		        return isNaN(value) ? null : value;
//		    },
			validator : function(value) {
				if(value != '' && value < 0) {
					return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
				}
				return true;
			}
		},{
			xtype: 'numberfield',
			name:'weight',
			allowDecimals: true, // 是否与许小数 
			decimalPrecision: 2, // 小数位精度 
			value:'0.00',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	 
			columnWidth:.33,
			maxValue:99999.99,
			validator : function(value) {
		           if(value != '' && value < 0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			name:'inStockBillQty',
			value:'0',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.inStockBillQty'),//进仓票数
			regex: /^\d{0,30}$/,	
			decimalPrecision: 0, 
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!			
			columnWidth:.33,
			xtype: 'numberfield',
			validator : function(value) {
		           if(value != '' && value < 0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			xtype: 'numberfield',
			decimalPrecision: 0, 
			name:'upstairsBillQty',
			value:'0',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsBillQtyInfo'),//上楼票数	
			maxValue:99999.99,
			regex: /^\d{0,30}$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 9,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,			
			validator : function(value) {
		           if(value != '' && value < 0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			xtype: 'numberfield',
			name:'distance',
			allowDecimals: true, // 是否与许小数 
			decimalPrecision: 2, // 小数位精度 
			value:'0.00',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.distanceToKM'),//里程(km)
			allowBlank: false,
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!		    
			columnWidth:.33
		},{
			name:  'upstairsFee',
			allowDecimals: true, // 是否与许小数 
			decimalPrecision: 2, // 小数位精度 
			value:'0.00',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsFeeInfo'),//上楼费			
//			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			xtype: 'numberfield',
			validator : function(value) {
		           if(value != '' && value < 0) {
		            return management.signBill.i18n('foss.management.signBill.exception.upstairsFeeNotBeNegative');
		           } 
		           return true;
		      }
		},{
			xtype: 'radiogroup',
	        fieldLabel: management.signBill.i18n('foss.management.signBill.label.singleSend'),//单独配送  
	        labelWidth:100,
	        allowBlank: false,
	        vertical: true, 	
	        columnWidth:.33,
	        items: [
	            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isSingleSend', inputValue: 'Y',checked:true},
	            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isSingleSend', inputValue: 'N'}				           
	        ]
		},{
			name:  'sendNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.sendNo'),//派送进仓编号
			labelWidth: 90,	
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		},{
			xtype: 'commonemployeeselector',			
			name:  'receiverCode',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.receiverCode'),//接货员
			allowBlank:true,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('receiverName');
					this.up('form').form.findField('receiverName').setValue(field.rawValue);					
				}
			}
		},{
			name: 'receiverName',
			xtype: 'hiddenfield'
		},{
			xtype: 'numberfield',
			name:  'haulBackBillQty',
			value:'0',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.haulBackBillQty'),//拉回票数
			decimalPrecision: 0, 
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 9,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		}]
		
});


Ext.define('Foss.management.addSendFeeInfoForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title: management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	width:760,
	layout:'column',
	defaults:{
		xtype: 'textfield',
		margin:'5 0 0 5',
		anchor: '90%'					
	},
	items:[{
		xtype: 'numberfield',
		labelAlign: 'top',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.noInStockBillFee'),//非进仓票数费用
		name: 'noInStockBillFee',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		allowBlank:false,			
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:50,
		columnWidth:.17,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}			
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'inStockBillFee',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.inStockBillFee'),//进仓票数费用	
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'driverRoyaltyAmount',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverRoyaltyAmount'),//司机实际提成总额
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.18	,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'useTruckFee',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckFeeTotal'),//用车划分总费用
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.17,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'otherFee',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.otherFeeInfo'),//其它费用	
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		allowBlank:false,								
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'upstairsFeeRoyalty',
		allowDecimals: true, // 是否与许小数 
		decimalPrecision: 2, // 小数位精度 
		value:'0.00',
		allowBlank:false,			
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsFeeRoyalty'),//上楼费提成							
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	}]
});



Ext.define('Foss.management.editSendSignBillForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',	 
	   title:  management.signBill.i18n('foss.management.signBill.editSendSignBillForm.title'),//编辑派送签单
	   width:760,	  
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 5 10',	
			anchor: '90%'					
		},
		items:[{
			name: 'id',
			xtype: 'hiddenfield'
		},{			
			name:'handoverNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.handoverNo'),//交接单编号
			allowBlank:true,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		},{
			name:'signBillNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners:{
				'blur':function(text,op){				
				if (text.getValue().trim()!=null&&text.getValue().trim()!='') {					
					 var params={vo:{sendSignBillDto:{signBillNo:text.getValue()}}};	//配载车次号			
				     Ext.Ajax.request({
			       	  url: management.realPath('querySendSignBillBySignBillNo.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) {
			       		var result = Ext.decode(response.responseText);	//查询的结果
			       		var record=result.vo.sendSignBillEntity;		       		
			       		if(record!=null){
			       			var editSendForm=management.editSendSignBillForm.getForm();
				       		editSendForm.findField('useTruckOrgName').setValue(record.useTruckOrgName);//用车部门名称				
							editSendForm.findField('driverName').setValue(record.driverName);//司机姓名
							if(record.signBillDate!=null){
								editSendForm.findField('signBillDate').setValue(new Date(record.signBillDate));//日期
							}
							editSendForm.findField('volume').setValue(record.volume);//体积
							editSendForm.findField('weight').setValue(record.weight);//'重量
							editSendForm.findField('sendBillQty').setValue(record.sendBillQty);//派送票数
							editSendForm.findField('haulBackBillQty').setValue(record.haulBackBillQty);//拉回票数
							//部门公共选择器
							var cmbUseTruckOrgCode = editSendForm.findField('useTruckOrgCode');
							cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.useTruckOrgCode}});
							cmbUseTruckOrgCode.setValue(record.useTruckOrgCode);
							
							//司机选择器
							var cmbDriverCode = editSendForm.findField('driverCode');
							cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.driverCode}});
							cmbDriverCode.setValue(record.driverCode);
							//车牌号				
							var cmbVehicleNo = editSendForm.findField('vehicleNo');
							cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.vehicleNo}});
							cmbVehicleNo.setValue(record.vehicleNo);
							
							//车型选择器
							var cmbVehicleTypeLength=editSendForm.findField('vehicleTypeLength');
							cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.vehicleTypeLength}});		
							cmbVehicleTypeLength.setValue(record.vehicleTypeLength);
			       		}			       		
			       		},
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			       	  },
			       	  exception : function(response) {
	    					var json = Ext.decode(response.responseText);
	    					Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
	    				}
			         });					
					}				
		 		}
			}		
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.33,
			minHeight:25
		},{
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			transferCenter:'Y',
			salesDepartment:'Y',
			airDispatch:'Y',
			name:'useTruckOrgCode',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!		   
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('name');
					this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
					
				}
			}
		},{
			name: 'useTruckOrgName',
			xtype: 'hiddenfield'
		},{
			xtype:'commonowndriverselector',
			name:'driverCode',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
			allowBlank:false,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('name');
					this.up('form').form.findField('driverName').setValue(field.rawValue);
					
				}
			}
		},{
			name: 'driverName',
			xtype: 'hiddenfield'
		},{
	  		xtype: 'datefield',
	  		name: 'signBillDate',
	  		labelWidth: 60,	
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		allowBlank: false,
	  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对		
	  		columnWidth: .3
		},{
			xtype: 'commonowntruckselector',
			allowBlank:false,
			name:'vehicleNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
			allowBlank:false,
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners:{
				'select':function(text,op){			
				if (text.getValue()!=null&&text.getValue()!='') {					
					 var params={vo:{sendSignBillDto:{vehicleNo:text.getValue()}}};	//车牌号码			
				     Ext.Ajax.request({
			       	  url: management.realPath('querySendSignBillByVehicleNo.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) {
			       		var result = Ext.decode(response.responseText);	//查询的结果
			       		//车型选择器
						var cmbVehicleTypeLength=management.editSendSignBillForm.getForm().findField('vehicleTypeLength');
						cmbVehicleTypeLength.setValue('');						
						cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.sendSignBillEntity.vehicleTypeLength}});		
						cmbVehicleTypeLength.setValue(result.vo.sendSignBillEntity.vehicleTypeLength);
			       	  },
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			       	  },
			       	  exception : function(response) {
	    					var json = Ext.decode(response.responseText);
	    					Ext.Msg.alert('exception',json.message);
	    				}
			         });
					
					}	
				
		 		}
			}
		},{
			xtype: 'commonvehicletypeselector',
			name: 'vehicleTypeLength',							
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
			allowBlank:false,
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!			
			columnWidth:.33
		},{
			xtype: 'numberfield',
			decimalPrecision: 0,
			name:'sendBillQty',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.sendBillQty'),//派送票数
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		},{
			xtype: 'numberfield',
			name:'volume',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
		    maxValue:99999.99,
			allowBlank:true,
			columnWidth:.33,			
			validator : function(value) {
		           if(value!=''&&value<=0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			xtype: 'numberfield',			
			name:'weight',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!		    
			allowBlank:true,
			columnWidth:.33,
			maxValue:99999.99,
			validator : function(value) {
		           if(value!=''&&value<=0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			name:'inStockBillQty',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.inStockBillQty'),//进仓票数
			regex: /^\d{0,30}$/,	
			decimalPrecision: 0,
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 9,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			allowBlank:true,
			columnWidth:.33,
			xtype: 'numberfield',
			validator : function(value) {
		           if(value!=''&&value<=0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			name:'upstairsBillQty',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsBillQtyInfo'),//上楼票数
			allowBlank:true,
			regex: /^\d{0,30}$/,
			decimalPrecision: 0,
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 9,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			xtype: 'numberfield',
			validator : function(value) {
		           if(value!=''&&value<=0) {
		            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
		           } 
		           return true;
		      }
		},{
			xtype: 'numberfield',
			name:'distance',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.distanceToKM'),//里程(km)
			allowBlank: false,
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!		    
			columnWidth:.33
		},{
			name:  'upstairsFee',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsFeeInfo'),//上楼费
			allowBlank:true,
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			xtype: 'numberfield',
			validator : function(value) {
		           if(value!='' && value < 0) {
		            return management.signBill.i18n('foss.management.signBill.exception.upstairsFeeNotBeNegative');
		           } 
		           return true;
		      }
		},{
			xtype: 'radiogroup',
	        fieldLabel: management.signBill.i18n('foss.management.signBill.label.singleSend'),//单独配送   
	        labelWidth:100,
	        allowBlank: false,
	        vertical: true, 	
	        columnWidth:.33,
	        items: [
	            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isSingleSend', inputValue: 'Y',checked:true},
	            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isSingleSend', inputValue: 'N'}				           
	        ]
		},{
			name:  'sendNo',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.sendNo'),//派送进仓编号
			maxLength : 10,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		},{
			xtype: 'commonemployeeselector',			
			name:  'receiverCode',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.receiverCode'),//接货员
			allowBlank:true,
			maxLength : 20,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('receiverName');
					this.up('form').form.findField('receiverName').setValue(field.rawValue);					
				}
			}
		},{
			name: 'receiverName',
			xtype: 'hiddenfield'
		},{
			xtype: 'numberfield',
			name:  'haulBackBillQty',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.haulBackBillQty'),//拉回票数
			decimalPrecision: 0,
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			maxLength : 9,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			columnWidth:.33
		}]
});


Ext.define('Foss.management.editSendFeeInfoForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	width:760,
	layout:'column',
	defaults:{
		xtype: 'textfield',
		margin:'5 0 0 5',
		anchor: '90%'					
	},
	items:[{
		xtype: 'numberfield',
		labelAlign: 'top',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.noInStockBillFee'),//非进仓票数费用
		name: 'noInStockBillFee',
		allowBlank:false,			
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:50,
		columnWidth:.17,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'inStockBillFee',			
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.inStockBillFee'),//进仓票数费用	
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'driverRoyaltyAmount',			
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverRoyaltyAmount'),//司机实际提成总额
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.18	,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'useTruckFee',			
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckFeeTotal'),//用车划分总费用
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.17,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.otherFeeInfo'),//其它费用	
		labelAlign: 'top',
		name: 'otherFee',
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		allowBlank:false,									
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'upstairsFeeRoyalty',
		allowBlank:false,			
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.upstairsFeeRoyalty'),//上楼费提成								
		labelWidth:60,
		columnWidth:.15,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}
	}]
});


Ext.define('Foss.management.editSendSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	    management.editSendSignBillForm= Ext.create('Foss.management.editSendSignBillForm'),
		management.editSendFeeInfoForm= Ext.create('Foss.management.editSendFeeInfoForm')   
	],	
	buttons: [{
		text: management.signBill.i18n('foss.management.button.save'),
		disabled: !management.signBill.isPermission('management/updateSendSignBillButton'),
		hidden: !management.signBill.isPermission('management/updateSendSignBillButton'),
	    cls:'yellow_button',
	    handler: function() {
	    	var form = this.up('form').getForm();
	      	var vals = this.up('form').getForm().getValues(); 
	      	vals.signBillDate=new Date(vals.signBillDate);
	      	var isBol=false;
	      	if(vals.isSingleSend=='Y'){//是否单独送票
	      		isBol=true;//如果是,则接货员可以不输入
	      	}else{
	      		if(vals.receiverCode!= undefined&&vals.receiverCode!= null){//如果接货员输入不为空
	      			isBol=true;//可以通过
	      		}else {
	      			Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.enterReceiverCode'), 'ok', 1000); 
	      		}
	      	}
		    if(isBol){
		    	if(form.isValid()){							
		    		var params={vo:{sendSignBillEntity:vals}};
				    Ext.Ajax.request({
				    	url: management.realPath('updateSendSignBill.action'),
				    	jsonData: params,
			       	  	success: function(response, opts) { 
			       	  		form.reset();						    	
			       	  		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.updateSuccess'), 'ok', 1000);
			       	  		management.sendSignBillPagingBar.moveFirst();
			       	  		management.editSendSignBillWindow.close();
			       	  	},
			       	  	failure: function(response, opts) {
			       	  		Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.updateFailure') );
			       	  	}
				    });
		    	}
		    }
	    }
	},{
		text: management.signBill.i18n('foss.management.button.cancel'),	     
	    handler: function() {
	    	management.editSendSignBillWindow.close();
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);			
		me.callParent([cfg]);			
		me.editSendSignBillForm=management.editSendSignBillForm;
		me.editSendFeeInfoForm=management.editSendFeeInfoForm;
	}
});


Ext.define('Foss.management.AddSendSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	frame: false,
	buttonAlign :'center',
	defaultType: 'textfield',	
	items:[	       
	       management.addSendSignBillForm= Ext.create('Foss.management.addSendSignBillForm'),
	      management.addSendFeeInfoForm= Ext.create('Foss.management.addSendFeeInfoForm')   
	 ],	
	 buttons: [{
	      text: management.signBill.i18n('foss.management.button.save'),
	      disabled: !management.signBill.isPermission('management/updateSendSignBillButton'),
	      hidden: !management.signBill.isPermission('management/updateSendSignBillButton'),
	      align: 'center',
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
	      	var isBol=false;
	      	  
	      	  if(vals.isSingleSend=='Y'){//是否单独送票
	      		isBol=true;//如果是,则接货员可以不输入
	      	  }else{
	      		  if(vals.receiverCode!= undefined&&vals.receiverCode!= null){//如果接货员输入不为空
	      			isBol=true;//可以通过
	      		  }else {
	      			Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.enterReceiverCode'), 'ok', 1000); 
	      		  }
	      		  
	      	  }
		     if(isBol){
	  			if(form.isValid()){							
						var params={vo:{sendSignBillEntity:vals}};
					     Ext.Ajax.request({
				       	  url: management.realPath('addSendSignBill.action'),
				       	  jsonData: params,
				       	  success: function(response, opts) { 
				       	   form.reset();						    	
					       Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);
					       management.sendSignBillPagingBar.moveFirst();
					       management.sendSignBillEditWindow.close();
				       	  },
				       	  failure: function(response, opts) {
					       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
				       	  }
				         });
			    	}					
	      	  }
	      }
	  },{
	      text: management.signBill.i18n('foss.management.button.cancel'),	     
	      handler: function() {
	    	  management.sendSignBillEditWindow.close();

	      }
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({},config);			
			me.callParent([cfg]);			
			me.addSendSignBillForm=management.addSendSignBillForm;
			me.addSendFeeInfoForm=management.addSendFeeInfoForm;
		}
});



//定义弹出的新增签单窗口 
Ext.define('Foss.management.AddSendSignBillEditWindow',{
	extend: 'Ext.window.Window',	
	width:800,	
	closable:true,
	closeAction:'hide',
	modal: true,
	addSendSignBillPanelForm : null,
	getAddSendSignBillPanelForm: function(){
		if(this.addSendSignBillPanelForm == null){
			this.addSendSignBillPanelForm = Ext.create('Foss.management.AddSendSignBillPanel');
		}
		return this.addSendSignBillPanelForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getAddSendSignBillPanelForm()
				];
		me.callParent([cfg]);
	}
});

//定义弹出的编辑其他签单窗口 
Ext.define('Foss.management.editSendSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:800,	
	closable:true,
	closeAction:'hide',
	modal: true,
	editSendSignBillPanelForm : null,
	getEditSendSignBillPanelForm: function(){
		if(this.editSendSignBillPanelForm == null){
			this.editSendSignBillPanelForm = Ext.create('Foss.management.editSendSignBillPanel');
		}
		return this.editSendSignBillPanelForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditSendSignBillPanelForm()
				];
		me.callParent([cfg]);
	}
});

//派送页签
Ext.define('Foss.management.SendSignbillPanel',{
	extend:'Ext.panel.Panel',
	cls:"panelContentNToolbar",
	bodyCls:'panelContentNToolbar-body',
	margin:'10 5 10 10',
	layout: 'auto',
	items:[
	       Ext.create('Foss.management.SendSignBillQueryForm'),
		   Ext.create('Foss.management.SendSignBillQueryGrid')
		   ]
});
/*********************************END   派送签单*******************************************/

/*********************************BEGIN  转货车签单*******************************************/
/**
 * 转货车Model
 */
Ext.define('Foss.management.TransferSignBillModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	{name:'id', type:'string'},
	{name:'signBillNo', type:'string'},//签单编号
	{name:'useTruckOrgCode', type:'string'},//用车部门
	{name:'useTruckOrgName', type:'string'},//用车部门名称
	{name:'signBillDate', type:'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
					return Ext.Date.format(date, 'Y-m-d');
				} else {
					return null;
				}
			}
		},//日期			  
	{name:'driverCode', type:'string'},//司机
	{name:'driverName', type:'string'},//司机姓名
	{name:'isFirstTransfer', type:'string'},//是否第一个部门转货
	{name:'vehicleNo', type:'string'},//车牌号
	{name:'vehicleTypeLength', type:'string'},//车型
	{name:'vehicleTypeLengthName', type:'string'},//车型名称
	{name:'transferDistance'},//转货里程
	{name:'volume'},//体积
	{name:'weight'},//重量
	{name:'useTruckDuration'},//用车时间
	{name:'transferRoyalty'},//转货提成
	{name:'useTruckFee'},//用车费用划分
	{name:'transferType', type:'string'},//单据类型
	{name:'handOverNo', type:'string'},//交接单编号
	{name:'origOrgCode', type:'string'},//出发部门code
	{name:'origOrgName', type:'string'},//出发部门name
	{name:'destOrgCode', type:'string'},//到达部门code
	{name:'destOrgName', type:'string'},//到达部门name
	{name:'motorcadeCode', type:'string'},//所属车队code
	{name:'motorcadeName', type:'string'},//所属车队name
	{name:'storeSign', type:'string'},//营业部签字
	{name:'arrangeUseTruckDate', type:'string',//约定用车时间
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return value;
				}
			}
		},
	{name:'unloadEndDate', type:'string',//卸车结束时间
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return value;
			}
		}
	},
	{name:'origDate', type:'string',//出发时间
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return value;
			}
		}
	},
	{name:'destDate', type:'string',//到达时间
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return value;
			}
		}
	}
	]
});
/**
 * 转货车store
 */
Ext.define('Foss.management.TransferSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.TransferSignBillModel',
//    pageSize:10,
    proxy: {
    	type: 'ajax',
    	url: management.realPath('queryTransferSignBill.action'),
    	actionMethods: {read: 'POST'},
    	reader: {
    		type: 'json',
        	root: 'vo.transferSignBillList',
        	totalProperty : 'totalCount',
        	successProperty: 'success'
    	},
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result.message);
            }
        }
    },
	listeners: {
		datachanged: function(store, operation, epots){
			var queryForm = management.transferSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();		
				var useTruckOrgCode = queryParams.useTruckOrgCode;
				var destOrgCode = queryParams.destOrgCode;
				var isFirstTransfer = queryParams.isFirstTransfer;
				var motorcadeCode = queryParams.motorcadeCode;
				var driverCode = queryParams.driverCode;
				var vehicleNo = queryParams.vehicleNo;
				var vehicleTypeLength = queryParams.vehicleTypeLength;					
				var beginSignBillDate = queryParams.beginSignBillDate;
				var endSignBillDate = queryParams.endSignBillDate;	
				var array={
					vo: {
						transferSignBillDto: {
							useTruckOrgCode : useTruckOrgCode,
							destOrgCode : destOrgCode,
							isFirstTransfer : isFirstTransfer,
							motorcadeCode : motorcadeCode,
							driverCode : driverCode,
							vehicleNo : vehicleNo,
							vehicleTypeLength : vehicleTypeLength,
							beginSignBillDate : beginSignBillDate,
							endSignBillDate : endSignBillDate
						}
					}
				};
				Ext.Ajax.request({
					url : management.realPath('queryTransferSignBillByFee.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);							
						//在json中取出对象
						var transferSignBillDto = json.vo.transferSignBillDto;						
						if(transferSignBillDto != null){
							var weightTotal=0;//总重量							
							var driverCount=0;//司机个数
							var transferDistanceTotal=0;//总的转货里程
							var volumeTotal=0;//总体积
							var useTruckDurationTotal=0;//总的用车时间
							if(transferSignBillDto.weightTotal!=null){
								weightTotal=transferSignBillDto.weightTotal;//总重量
							}
							if (transferSignBillDto.driverCount!=null) {
								driverCount=transferSignBillDto.driverCount;//司机个数
							}						
							if (transferSignBillDto.transferDistanceTotal!=null) {
								transferDistanceTotal=transferSignBillDto.transferDistanceTotal;//总的转货里程
							}
							if (transferSignBillDto.volumeTotal!=null) {
								volumeTotal=transferSignBillDto.volumeTotal;//总体积
							}
							if (transferSignBillDto.useTruckDurationTotal!=null) {
								useTruckDurationTotal=transferSignBillDto.useTruckDurationTotal;//总的用车时间		
							}					  
							var count = 0;								
							var toolbarArray = management.TransferSignBillGrid.getDockedItems('toolbar[dock="bottom"]')[0].items.items;
							for(var j=0;j<toolbarArray.length;j++){
								if(count==0){
									toolbarArray[j].setValue(driverCount);
								}else if(count==1){
									toolbarArray[j].setValue(transferDistanceTotal);
								}else if(count==2){
									toolbarArray[j].setValue(volumeTotal);
								}else if(count==3){
									toolbarArray[j].setValue(weightTotal);
								}else if(count==4){
									toolbarArray[j].setValue(useTruckDurationTotal);
								}
								count ++;
							}
						}else{
							top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
					}
				});
			}
		},
		beforeload : function(store, operation, eOpts) {				
			var queryForm = management.transferSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.transferSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,		//用车部门
						'vo.transferSignBillDto.destOrgCode' : queryParams.destOrgCode,				//到达部门
						'vo.transferSignBillDto.isFirstTransfer' : queryParams.isFirstTransfer,		//用车类型
						'vo.transferSignBillDto.motorcadeCode' : queryParams.motorcadeCode,			//所属车队
						'vo.transferSignBillDto.driverCode' : queryParams.driverCode,  				//司机姓名
						'vo.transferSignBillDto.vehicleNo' : queryParams.vehicleNo,  				//车牌号
						'vo.transferSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength, //车型
						'vo.transferSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate, //开始时间
						'vo.transferSignBillDto.endSignBillDate' :queryParams.endSignBillDate 		//结束时间
					}
				});	
			}
		},
		load: function(store,records,successful,epots){
			if(store.getCount() == 0){
				Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.notFindData'), 'ok', 1000);
			}
		}
	}
});

//转货车查询条件
Ext.define('Foss.management.TransferSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame:false,
	layout:'column',
	defaults:{
		xtype: 'textfield',
		labelWidth: 85,		
		margin: '5 10 5 10'
	},
	items:[{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		transDepartment : 'Y',
		name: 'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		columnWidth: .3
	},{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		transDepartment : 'Y',
		name: 'destOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destOrgCode'),//目的部门
		columnWidth: .3
	},{
		xtype: 'combobox',
		name:'isFirstTransfer',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferType'),//用车类型
		columnWidth:.3,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.management.IsFirstTransferStore',{
			data: {
				'items':[
					{'code':'ALL','name':management.signBill.i18n('foss.management.signBill.label.ALL')},
					{'code':'Y','name':management.signBill.i18n('foss.management.signBill.label.BRINGGOODS')},//带货
					{'code':'N','name':management.signBill.i18n('foss.management.signBill.label.TRANSFER')},//转货
				]
			}
		})
	},{
		xtype:'commonowndriverselector',
		name: 'driverCode',		
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		columnWidth: .3
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .3
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		columnWidth: .3
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		columnWidth: .3
	},{
		fieldLabel:management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		xtype: 'rangeDateField',		
		dateType: 'datetimefield_date97',
		dateRange : 31,
		time:false,
		format:'Y-m-d',
		fieldId: 'Foss_management_TransferSignBillQueryForm_createTime',
		disallowBlank:true,
		fromName: 'beginSignBillDate',		
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),	
		toName: 'endSignBillDate',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),		
		allowBlank: false,
		columnWidth: .50
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:management.signBill.i18n('foss.management.button.reset'),//重置
			columnWidth:.08,
			handler:function(){				
				this.up('form').getForm().reset();				
				//重新初始化交接时间
				this.up('form').getForm().findField('beginSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
				this.up('form').getForm().findField('endSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text:management.signBill.i18n('foss.management.button.search'),//查询
			disabled: !management.signBill.isPermission('management/queryTransferSignBillByFeeButton'),
			hidden: !management.signBill.isPermission('management/queryTransferSignBillByFeeButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.TransferSignBillGrid.store.load();
				}
			}
		},{
			text:management.signBill.i18n('foss.management.button.export'),//导 出
			disabled: !management.signBill.isPermission('management/queryExportTransferSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryExportTransferSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){				
				var form = this.up('form').getForm();
				if(form.isValid()){
					var queryParams = management.transferSignBillQueryForm.getValues();		
					if(!Ext.fly('downloadAttachFileForm')){
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					Ext.Ajax.request({
		    			url: management.realPath('queryExportTransferSignBill.action'),	 
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params : {
		    				'vo.transferSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
							'vo.transferSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
							'vo.transferSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
							'vo.transferSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
							'vo.transferSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
							'vo.transferSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
		    			},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('导出失败',result.message);
		    			}
	    			});
				}
			}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		management.transferSignBillQueryForm = me;  //加入全局变量中
	}
});

//转货车签单grid
Ext.define('Foss.management.TransferSignBillGrid',{
	extend:'Ext.grid.Panel',
	title: management.signBill.i18n('foss.management.signBill.TransferSignBillGrid.title'),//转货车签单列表
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	columns: [{
		xtype:'actioncolumn',
		width:60,
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !management.signBill.isPermission('management/updateTransferSignBillButton'),
			hidden: !management.signBill.isPermission('management/updateTransferSignBillButton'),
			tooltip: management.signBill.i18n('foss.management.button.operate'),//编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				management.editTransferSignBillWindow = Ext.create('Foss.management.editTransferSignBillWindow');
				var tab = management.editTransferSignBillWindow.items.items[0].items.items[0];
				var record = grid.getStore().getAt(rowIndex)
					transferType = record.data.transferType;
				
				//重置界面
				management.editTransferSignBillForm.getForm().reset();
				management.editFeeInfoForm.getForm().reset();
				management.editTransferSignBillMotorcadeForm.getForm().reset();
				management.editFeeInfoMotorcadeForm.getForm().reset();
				
				//management.editTransferSignBillForm.getForm().loadRecord(record);
				var editForm = management.editTransferSignBillForm.getForm();
				
				
				if(transferType == 'STORE') {
					tab.setActiveTab('TransferSignBill');//激活营业部tab
					tab.items.items[0].setDisabled(false);
					tab.items.items[1].setDisabled(true);//disable车队tab
					
					//数据为营业部数据
					editForm = management.editTransferSignBillForm.getForm();
					editForm.findField('storeSign').setValue(record.data.storeSign);//营业部签字
					editForm.findField('arrangeUseTruckDate').setValue(Ext.Date.format(new Date(record.data.arrangeUseTruckDate),'Y-m-d H:i:s'));//约定用车时间
					editForm.findField('unloadEndDate').setValue(Ext.Date.format(new Date(record.data.unloadEndDate),'Y-m-d H:i:s'));//卸车结束时间
					editForm.findField('origDate').setValue(Ext.Date.format(new Date(record.data.origDate),'Y-m-d H:i:s'));//出发时间
					editForm.findField('destDate').setValue(Ext.Date.format(new Date(record.data.destDate),'Y-m-d H:i:s'));//到达时间
					
					//load下方费用信息
					management.editFeeInfoForm.getForm().loadRecord(record);
				} else {
					tab.setActiveTab('TransferSignBillMotorcade');//激活车队tab
					tab.items.items[0].setDisabled(true);//disable营业部tab
					tab.items.items[1].setDisabled(false);
					
					//数据为车队数据
					editForm = management.editTransferSignBillMotorcadeForm.getForm();
					
					//load下方费用信息
					management.editFeeInfoMotorcadeForm.getForm().loadRecord(record);
				}
				editForm.findField('id').setValue(record.data.id);//id
				editForm.findField('handOverNo').setValue(record.data.handOverNo);//交接单号
				editForm.findField('signBillNo').setValue(record.data.signBillNo);//签单编号	
				editForm.findField('useTruckOrgName').setValue(record.data.useTruckOrgName);//用车部门名称				
				editForm.findField('driverName').setValue(record.data.driverName);//司机姓名
				editForm.findField('signBillDate').setValue(new Date(record.data.signBillDate));//日期
				editForm.findField('isFirstTransfer').setValue(record.data.isFirstTransfer);//是否第一个部门				
				editForm.findField('transferDistance').setValue(record.data.transferDistance);//转货里程
				editForm.findField('volume').setValue(record.data.volume);//体积
				editForm.findField('weight').setValue(record.data.weight);//'重量						
				editForm.findField('useTruckDuration').setValue(record.data.useTruckDuration);//用车时间
				//用车部门公共选择器
				var cmbUseTruckOrgCode = editForm.findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);

				//出发部门公共选择器
				var cmbOrigOrgCode = editForm.findField('origOrgCode');
				cmbOrigOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.origOrgCode}});
				cmbOrigOrgCode.setValue(record.data.origOrgCode);
				editForm.findField('origOrgName').setValue(record.data.origOrgName);

				//目的部门公共选择器
				var cmbDestOrgCode = editForm.findField('destOrgCode');
				cmbDestOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.destOrgCode}});
				cmbDestOrgCode.setValue(record.data.destOrgCode);
				editForm.findField('destOrgName').setValue(record.data.destOrgName);

				//所属车队公共选择器
				var cmbMotorcadeCode = editForm.findField('motorcadeCode');
//				cmbMotorcadeCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.motorcadeCode}});
//				cmbMotorcadeCode.setValue(record.data.motorcadeCode);
				
				cmbMotorcadeCode.getStore().load({params:{'commonOrgVo.name' : record.data.motorcadeName}});
				cmbMotorcadeCode.setValue(record.data.motorcadeCode);
				editForm.findField('motorcadeName').setValue(record.data.motorcadeName);
				
				//司机选择器
				var cmbDriverCode = editForm.findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driverCode}});
				cmbDriverCode.setValue(record.data.driverCode);
				//车牌号选择器			
				var cmbVehicleNo = editForm.findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.data.vehicleNo}});
				cmbVehicleNo.setValue(record.data.vehicleNo);
				//车型选择器
				var cmbVehicleTypeLength = editForm.findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.data.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.data.vehicleTypeLength);
				
				management.editTransferSignBillWindow.show();
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteSelect'),function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteTransferSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.deleteSuccess'), 'ok', 1000);
								 management.TransferSignBillGrid.store.load();				        
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteFailure') + result);
							},
							exception : function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('exception',json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverCode'),//司机
		dataIndex: 'driverCode',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		dataIndex: 'driverName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		dataIndex: 'motorcadeName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		dataIndex: 'signBillDate',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		dataIndex: 'vehicleTypeLengthName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.transferDistance'),//转货里程
		dataIndex: 'transferDistance',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		dataIndex: 'volume',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		dataIndex: 'weight',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		dataIndex: 'useTruckDuration',
		flex:0.9
	},{
		header: management.signBill.i18n('foss.management.signBill.label.isFirstTransfer'),//是否带货
		dataIndex: 'isFirstTransfer',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value == 'Y') {
					return management.signBill.i18n('foss.management.signBill.label.yes');
				} else {
					return management.signBill.i18n('foss.management.signBill.label.no');
				}
			}
		},
		flex:0.9
	},{
		header:  management.signBill.i18n('foss.management.signBill.label.transferRoyalty'),//转货提成
		dataIndex: 'transferRoyalty',
		flex:0.9
	},{
		header:   management.signBill.i18n('foss.management.signBill.label.useTruckFeeInfo'),//用车费用划分
		dataIndex: 'useTruckFee',
		flex:0.9
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,			
		     style: 'font-weight:bold;'	,
		     width:20
		    	 
		   },
		   items:[{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalDrivers'),//司机数	
			   labelWidth:60,
			   width:90,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel:  management.signBill.i18n('foss.management.signBill.label.billingWeightTotal'),//转货里程合计
			   labelWidth:110,
			   width:170,
			   dataIndex: 'billingWeightTotal'
		   },{
			   fieldLabel:management.signBill.i18n('foss.management.signBill.label.totalVolume'),//体积合计	
			   labelWidth:70,
			   width:140,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWeight'),//重量合计	
			   labelWidth:70,	
			   width:140,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckTimeTotal'),//用车时间合计
			   labelWidth:100,
			   width:160,
			   dataIndex: 'useTruckTimeTotal'
		   }]
		}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.TransferSignBillStore');
		me.tbar=[{
			xtype: 'button',
			disabled: !management.signBill.isPermission('management/addTransferSignBillButton'),
			hidden: !management.signBill.isPermission('management/addTransferSignBillButton'),
			text: management.signBill.i18n('foss.management.button.add'),//新增
			handler: function() {
				management.AddTransferSignBillWindow = Ext.create('Foss.management.AddTransferSignBillWindow');			 
				management.AddTransferSignBillWindow.center().show();  
				management.addTransferSignBillForm.getForm().reset();
    			management.addFeeInfoForm.getForm().reset();
    			management.addTransferSignBillMotorcadeForm.getForm().reset();
    			management.addFeeInfoMotorcadeForm.getForm().reset();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins: 'pagesizeplugin'			
		});
		management.TransferSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});

//转货签单新增界面-营业部
Ext.define('Foss.management.addTransferSignBillForm',{
	 extend: 'Ext.form.Panel',
	 layout: 'column',
	 frame: false,
	 width:670,
	 layout:'column',	
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,
			margin:'10 5 5 10'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handOverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.handOverNo'),//交接号
		allowBlank: false,
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'origOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origOrgCode'),//出发部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('origOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'origOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name: 'destOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destOrgCode'),//目的部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('destOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'destOrgName',
		xtype: 'hiddenfield'
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
			if (text.getValue()!=null&&text.getValue()!='') {
				
				 var params={vo:{transferSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
			     Ext.Ajax.request({
		       	  url: management.realPath('queryTransferSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		//车型选择器
					var cmbVehicleTypeLength=management.addTransferSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.transferSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.transferSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
		       		  Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });
				}	
	 		}
		}		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',								
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 50,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'transferDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferDistance'),//转货里程
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,				
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 5,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    labelWidth: 100,
		columnWidth:.33,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.useTruckDurationNotBeNegative');
			} 
		    return true;
		}
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrangeUseTruckDate'),//约定用车时间
  		name: 'arrangeUseTruckDate',
  		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_arrangeUseTruckDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_arrangeUseTruckDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destDate'),//到达时间
		name: 'destDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_destDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_destDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
  		fieldLabel: management.signBill.i18n('foss.management.signBill.label.unloadEndDate'),//卸货结束时间
  		name: 'unloadEndDate',
  		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_unloadEndDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_unloadEndDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
  		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origDate'),//出发时间
		name : 'origDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_origDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_origDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
		xtype: 'radiogroup',
        fieldLabel: management.signBill.i18n('foss.management.signBill.label.firstTransfer'),//是否带货
        labelWidth:100,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.33,
        items: [
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isFirstTransfer', inputValue: 'Y',checked:true},
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isFirstTransfer', inputValue: 'N'}				           
        ]
	},{
		name: 'storeSign',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.storeSign'),//营业部签字
		allowBlank: false,
		maxLength : 80,
		labelWidth: 120,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.60
	}]
});

//转货签单新增界面-车队
Ext.define('Foss.management.addTransferSignBillMotorcadeForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	width:670,
	layout:'column',	
	defaults:{
		xtype: 'textfield',
		labelWidth: 60,		
		margin:'10 5 5 10'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handOverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.handOverNo'),//交接号
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'origOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origOrgCode'),//出发部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('origOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'origOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name: 'destOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destOrgCode'),//目的部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('destOrgName').setValue(field.rawValue);
			}
		}
	},{
		name: 'destOrgName',
		xtype: 'hiddenfield'
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
				if (text.getValue()!=null&&text.getValue()!='') {
					 var params={vo:{transferSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
				     Ext.Ajax.request({
				    	 url: management.realPath('queryTransferSignBillByVehicleNo.action'),
				    	 jsonData: params,
				    	 success: function(response, opts) {
				    		 var result = Ext.decode(response.responseText);	//查询的结果
				    		 //车型选择器
				    		 var cmbVehicleTypeLength = management.addTransferSignBillMotorcadeForm.getForm().findField('vehicleTypeLength');
				    		 cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.transferSignBillEntity.vehicleTypeLength}});		
				    		 cmbVehicleTypeLength.setValue(result.vo.transferSignBillEntity.vehicleTypeLength);
				    	 },
				    	 failure: function(response, opts) {
				    		 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
				    	 },
				    	 exception : function(response) {
				    		 var json = Ext.decode(response.responseText);
				    		 Ext.Msg.alert('exception',json.message);
				    	 }
				     });
				}
			}
		}		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',								
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 50,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'transferDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferDistance'),//转货里程
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 60,
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 80,
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,				
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 5,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
	    labelWidth: 100,
		columnWidth:.33,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.useTruckDurationNotBeNegative');
			} 
		    return true;
		}
	},{
		xtype: 'radiogroup',
        fieldLabel: management.signBill.i18n('foss.management.signBill.label.firstTransfer'),//是否带货
        labelWidth:100,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.33,
        items: [
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isFirstTransfer', inputValue: 'Y',checked:true},
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isFirstTransfer', inputValue: 'N'}				           
        ]
	}]
});

//修改转货车信息界面-营业部
Ext.define('Foss.management.editTransferSignBillForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',	  
	width:670,
	layout:'column',	
	defaults:{
		xtype: 'textfield',
		labelWidth: 60,		
		margin:'10 5 5 10'		
	},	
	items:[{
		name: 'id',
		xtype: 'hiddenfield'
	},{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handOverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.handOverNo'),//交接号
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'origOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origOrgCode'),//出发部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('origOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'origOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'destOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destOrgCode'),//目的部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('destOrgName').setValue(field.rawValue);
			}
		}
	},{
		name: 'destOrgName',
		xtype: 'hiddenfield'
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
				name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
			if (text.getValue()!=null&&text.getValue()!='') {
				
				 var params={vo:{transferSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
			     Ext.Ajax.request({
		       	  url: management.realPath('queryTransferSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		//车型选择器
					var cmbVehicleTypeLength=management.addTransferSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.transferSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.transferSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
			       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });
				
				}	
			
	 		}
		}		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',								
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 50,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'transferDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferDistance'),//转货里程
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 5,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		labelWidth: 100,	
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.useTruckDurationNotBeNegative');
			} 
		    return true;
		}
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrangeUseTruckDate'),//约定用车时间
  		name: 'arrangeUseTruckDate',
  		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_edit_arrangeUseTruckDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_edit_arrangeUseTruckDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destDate'),//到达时间
		name: 'destDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_edit_destDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_edit_destDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
  		fieldLabel: management.signBill.i18n('foss.management.signBill.label.unloadEndDate'),//卸货结束时间
  		name: 'unloadEndDate',
  		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_edit_unloadEndDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_edit_unloadEndDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
  		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origDate'),//出发时间
		name : 'origDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_management_edit_origDate_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_management_edit_origDate_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		labelWidth: 90,
		columnWidth: .50
	},{
		xtype: 'radiogroup',
        fieldLabel: management.signBill.i18n('foss.management.signBill.label.firstTransfer'),//是否带货
        labelWidth:100,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.33,
        items: [
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isFirstTransfer', inputValue: 'Y',checked:true},
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isFirstTransfer', inputValue: 'N'}				           
        ]
	},{
		name: 'storeSign',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.storeSign'),//营业部签字
		allowBlank: false,
		maxLength : 90,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	}]
});

//修改转货车信息-车队
Ext.define('Foss.management.editTransferSignBillMotorcadeForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',	  
	title: management.signBill.i18n('foss.management.signBill.editTransferSignBillForm.title'),//编辑转货车签单
	width:670,
	layout:'column',	
	defaults:{
		xtype: 'textfield',
		labelWidth: 60,		
		margin:'10 5 5 10'		
	},	
	items:[{
		name: 'id',
		xtype: 'hiddenfield'
	},{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,		
		maxLength : 50,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handOverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.handOverNo'),//交接号
		allowBlank: false,		
		maxLength : 50,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
				name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'origOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.origOrgCode'),//出发部门
		allowBlank:false,
		maxLength : 20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('origOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'origOrgName',
		xtype: 'hiddenfield'
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'destOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.destOrgCode'),//目的部门
		allowBlank:false,
		maxLength : 20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
				name = record.get('name');
				this.up('form').form.findField('destOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'destOrgName',
		xtype: 'hiddenfield'
	},{
		xtype: 'datefield',
		name: 'signBillDate',
		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		altFormats: 'Y,m,d|Y.m.d',
		format: 'Y-m-d',
		allowBlank: false,
		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
		columnWidth: .33
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
				name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
				name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
				if (text.getValue()!=null&&text.getValue()!='') {
					
					var params={vo:{transferSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
					Ext.Ajax.request({
						url: management.realPath('queryTransferSignBillByVehicleNo.action'),
						jsonData: params,
						success: function(response, opts) {
							var result = Ext.decode(response.responseText);	//查询的结果
							//车型选择器
							var cmbVehicleTypeLength=management.addTransferSignBillForm.getForm().findField('vehicleTypeLength');
							cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.transferSignBillEntity.vehicleTypeLength}});		
							cmbVehicleTypeLength.setValue(result.vo.transferSignBillEntity.vehicleTypeLength);
						},
						failure: function(response, opts) {
							Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert('exception',json.message);
						}
					});
					
				}	
				
			}
		}		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',								
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'transferDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferDistance'),//转货里程
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeTosquare'),//体积(方)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
		maxValue:99999.99,
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightToKG'),//重量(公斤)
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,			
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
		maxValue:99999.99,
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,
//		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 5,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33,
		labelWidth: 100,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.useTruckDurationNotBeNegative');
			} 
		    return true;
		}
	},{
		xtype: 'radiogroup',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.firstTransfer'),//是否带货
		labelWidth:100,
		allowBlank: false,
		vertical: true, 	
		columnWidth:.33,
		items: [
		        { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isFirstTransfer', inputValue: 'Y',checked:true},
		        { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isFirstTransfer', inputValue: 'N'}				           
		        ]
	}]
});

//转货车费用信息form-营业部
Ext.define('Foss.management.addFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10',
			anchor: '90%'					
		},
		items:[{
				xtype: 'numberfield',
				labelAlign: 'top',
				fieldLabel:  management.signBill.i18n('foss.management.signBill.label.transferRoyalty'),//转货提成
				name: 'transferRoyalty',	
				allowBlank: false,
				maxLength : 10,
				maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:50,
				columnWidth:.3,
				validator : function(value) {
					if(value != '' && value < 0) {
						return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
					} 
				    return true;
				}
			},{
				xtype: 'numberfield',
				labelAlign: 'top',
				name: 'useTruckFee',							
				fieldLabel:  management.signBill.i18n('foss.management.signBill.label.useTruckFeeInfo'),//用车费用划分
				allowBlank: false,
				maxLength : 10,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:60,
				columnWidth:.3,
				validator : function(value) {
			           if(value != '' && value < 0) {
				            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			           } 
				       return true;
				  }	
			}]
});

//转货车费用信息form-车队
Ext.define('Foss.management.addFeeInfoMotorcadeForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	width:670,
	layout:'column',
	defaults:{
		xtype: 'textfield',
		margin:'10 5 3 10',
		anchor: '90%'					
	},
	items:[{
		xtype: 'numberfield',
		labelAlign: 'top',
		fieldLabel:  management.signBill.i18n('foss.management.signBill.label.transferRoyalty'),//转货提成
		name: 'transferRoyalty',	
		allowBlank: false,
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:50,
		columnWidth:.3,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
			return true;
		}	
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'useTruckFee',							
		fieldLabel:  management.signBill.i18n('foss.management.signBill.label.useTruckFeeInfo'),//用车费用划分
		allowBlank: false,
		maxLength : 10,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.3,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			}
			return true;
		}	
	}]
});
//新增界面
Ext.define('Foss.management.addTransferSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',
	title:  management.signBill.i18n('foss.management.signBill.addTransferSignBillForm.title'),//录入转货车签单
	items:[{
		xtype : 'tabpanel',
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		activeTab : 0,
		items : [{
					title : '营业部',
					tabConfig : {
						width : 120
					},
					itemId : 'TransferSignBill',
					items : [
					    management.addTransferSignBillForm = Ext.create('Foss.management.addTransferSignBillForm'),
						management.addFeeInfoForm = Ext.create('Foss.management.addFeeInfoForm')
					]
				}, {
					title : '车队',
					tabConfig : {
						width : 120
					},
					itemId : 'TransferSignBillMotorcade',
					items : [
					         management.addTransferSignBillMotorcadeForm = Ext.create('Foss.management.addTransferSignBillMotorcadeForm'),
					         management.addFeeInfoMotorcadeForm = Ext.create('Foss.management.addFeeInfoMotorcadeForm')
					]
				}]
	}],
	buttons: [{
		text: management.signBill.i18n('foss.management.button.save'),
		hidden: !management.signBill.isPermission('management/saveTransferSignBillButton'),
		disabled: !management.signBill.isPermission('management/saveTransferSignBillButton'),
	    cls:'yellow_button',
	    handler: function() {
	    	var tabpanelItemId = this.up('form').items.items[0].getActiveTab().itemId;
	    	var headerValue, footerValue;
	    	//营业部
	    	if(tabpanelItemId == 'TransferSignBill') {
	    		headerValue = management.addTransferSignBillForm.getForm().getValues();
	    		headerValue.transferType = 'STORE';
	    		footerValue = management.addFeeInfoForm.getForm().getValues();
	    	} else {
	    		//车队
	    		headerValue = management.addTransferSignBillMotorcadeForm.getForm().getValues();
	    		headerValue.transferType = 'MOTORCADE';
	    		footerValue = management.addFeeInfoMotorcadeForm.getForm().getValues();
	    	}
	    	var vals = Ext.merge(headerValue, footerValue);
	      	vals.signBillDate = new Date(vals.signBillDate);
	      	
	      	if(tabpanelItemId == 'TransferSignBill') {
	      		if(!management.addTransferSignBillForm.getForm().isValid()) {
	      			return;
	      		}
	      		if(!management.addFeeInfoForm.getForm().isValid()) {
	      			return;
	      		}
	      		vals.arrangeUseTruckDate = new Date(vals.arrangeUseTruckDate);
	      		vals.unloadEndDate = new Date(vals.unloadEndDate);
	      		vals.origDate = new Date(vals.origDate);
	      		vals.destDate = new Date(vals.destDate);
	      	} else {
	      		if(!management.addTransferSignBillMotorcadeForm.getForm().isValid()) {
	      			return;
	      		}
	      		if(!management.addFeeInfoMotorcadeForm.getForm().isValid()) {
	      			return;
	      		}
	      	}
	      	
	    	var params={vo:{transferSignBillEntity:vals}};
	    	Ext.Ajax.request({
	    		url: management.realPath('addTransferSignBill.action'),
	    		jsonData: params,
	    		success: function(response, opts) { 
	    			management.addTransferSignBillForm.getForm().reset();
	    			management.addFeeInfoForm.getForm().reset();
	    			management.addTransferSignBillMotorcadeForm.getForm().reset();
	    			management.addFeeInfoMotorcadeForm.getForm().reset();
	    			Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);						     
	    			management.AddTransferSignBillWindow.center().close(); 
	    			management.TransferSignBillGrid.store.load();
	    		},
		       	failure: function(response, opts) {
		       		Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
		       	}
	    	});
	    }
	  },{
	      text: management.signBill.i18n('foss.management.button.cancel'),	     
	      handler: function() {
	    	  management.AddTransferSignBillWindow.center().close(); 
	      }
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({},config);
			me.callParent([cfg]);
			me.addTransferSignBillForm = management.addTransferSignBillForm;
			me.addFeeInfoForm =  management.addFeeInfoForm;
		}
});


//编辑转货车费用信息form
Ext.define('Foss.management.editFeeInfoForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	width:670,
	layout:'column',
	defaults:{
		xtype: 'textfield',
		margin:'10 5 3 10',
		anchor: '90%'					
	},
	items:[{
		xtype: 'numberfield',
		labelAlign: 'top',
		fieldLabel:  management.signBill.i18n('foss.management.signBill.label.transferRoyalty'),//转货提成
		name: 'transferRoyalty',	
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:50,
		columnWidth:.3,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value!=''&&value<=0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
		    return true;
		}
	},{
		xtype: 'numberfield',
		labelAlign: 'top',
		name: 'useTruckFee',							
		fieldLabel:  management.signBill.i18n('foss.management.signBill.label.useTruckFeeInfo'),//用车费用划分
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.3,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
		validator : function(value) {
			if(value!=''&&value<=0) {
				return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			} 
		    return true;
		}
	}]
});
//编辑界面
Ext.define('Foss.management.editTransferSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	title: management.signBill.i18n('foss.management.signBill.editTransferSignBillForm.title'),//编辑转货车签单
	items : [{
		xtype : 'tabpanel',
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		activeTab : 0,
		items : [{
			title : '营业部',
			tabConfig : {
				width : 120
			},
			itemId : 'TransferSignBill',
			items : [
			         management.editTransferSignBillForm = Ext.create('Foss.management.editTransferSignBillForm'),
			         management.editFeeInfoForm = Ext.create('Foss.management.editFeeInfoForm')
			]
		}, {
			title : '车队',
			tabConfig : {
				width : 120
			},
			itemId : 'TransferSignBillMotorcade',
			items : [
			         management.editTransferSignBillMotorcadeForm = Ext.create('Foss.management.editTransferSignBillMotorcadeForm'),
			         management.editFeeInfoMotorcadeForm = Ext.create('Foss.management.addFeeInfoMotorcadeForm')
			]
		}],
	}],
	buttons: [{
		text: management.signBill.i18n('foss.management.button.save'),
		disabled: !management.signBill.isPermission('management/saveTransferSignBillButton'),
		hidden: !management.signBill.isPermission('management/saveTransferSignBillButton'),
		cls:'yellow_button',
		handler: function() {
			var tabpanelItemId = this.up('form').items.items[0].getActiveTab().itemId;
//			var headerValue, footerValue;
//			//营业部
//	    	if(tabpanelItemId == 'TransferSignBill') {
//	    		headerValue = management.addTransferSignBillForm.getForm().getValues();
//	    		headerValue.transferType = 'STORE';
//	    		footerValue = management.addFeeInfoForm.getForm().getValues();
//	    	} else {
//	    		//车队
//	    		headerValue = management.addTransferSignBillMotorcadeForm.getForm().getValues();
//	    		headerValue.transferType = 'MOTORCADE';
//	    		footerValue = management.addFeeInfoMotorcadeForm.getForm().getValues();
//	    	}
//	    	var vals = Ext.merge(headerValue, footerValue);
//	      	vals.signBillDate = new Date(vals.signBillDate);
//	      		if(!management.addTransferSignBillForm.getForm().isValid()) {
//	      			return;
//	      		}
//	      		if(!management.addFeeInfoForm.getForm().isValid()) {
//	      			return;
//	      		}
//	      		vals.arrangeUseTruckDate = new Date(vals.arrangeUseTruckDate);
//	      		vals.unloadEndDate = new Date(vals.unloadEndDate);
//	      		vals.origDate = new Date(vals.origDate);
//	      		vals.destDate = new Date(vals.destDate);
//	      		if(!management.addTransferSignBillMotorcadeForm.getForm()) {
//	      			return;
//	      		}
//	      		if(!management.addFeeInfoMotorcadeForm.getForm()) {
//	      			return;
//	      		}
			var form = this.up('form').getForm();
				vals = form.getValues();
			vals.signBillDate = new Date(vals.signBillDate);
			if(tabpanelItemId == 'TransferSignBill') {
				vals.arrangeUseTruckDate = new Date(vals.arrangeUseTruckDate);
				vals.unloadEndDate = new Date(vals.unloadEndDate);
				vals.origDate = new Date(vals.origDate);
				vals.destDate = new Date(vals.destDate);
			} else {
	      	}
			if(form.isValid()){
				var params={vo:{transferSignBillEntity:vals}};
				Ext.Ajax.request({
					url: management.realPath('updateTransferSignBill.action'),
					jsonData: params,
			       	success: function(response, opts) { 
				       	form.reset();						    	
					    Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.updateSuccess'), 'ok', 1000);
					    management.TransferSignBillGrid.store.load();
					    management.editTransferSignBillWindow.center().close(); 
			       	},
			       	failure: function(response, opts) {
			       		Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.updateFailure') );
			       	}
				});
			}
		}
	},{
		text: management.signBill.i18n('foss.management.button.cancel'),	     
	    handler: function() {
	    	management.editTransferSignBillWindow.center().close(); 
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		me.editTransferSignBillForm = management.editTransferSignBillForm;
		me.editFeeInfoForm =  management.editFeeInfoForm;
	}
});


//定义弹出的编辑转货车窗口 
Ext.define('Foss.management.editTransferSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:730,	
	closable:true,
	closeAction:'hide',
	modal: true,
	editTransferSignBillForm : null,
	getEditTransferSignBillForm: function(){
		if(this.editTransferSignBillForm == null){
			this.editTransferSignBillForm = Ext.create('Foss.management.editTransferSignBillPanel');
		}
		return this.editTransferSignBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getEditTransferSignBillForm()];
		me.callParent([cfg]);
	}
});


//定义弹出的新增窗口
Ext.define('Foss.management.AddTransferSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:730,	
	closable:true,
	closeAction:'hide',
	modal: true,
	addTransferSignBillForm : null,
	getAddTransferSignBillForm: function(){
		if(this.addTransferSignBillForm == null){
			this.addTransferSignBillForm = Ext.create('Foss.management.addTransferSignBillPanel');
		}
		return this.addTransferSignBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getAddTransferSignBillForm()
				];
		me.callParent([cfg]);
	}
});
//转货车签单
Ext.define('Foss.management.TransferSignBillPanel',{
	extend: 'Ext.panel.Panel',
	cls: 'panelContentNToolbar',
	bodyCls:'panelContentNToolbar-body',
	margin:'10 5 10 10',
	layout: 'auto',
	items:[
        Ext.create('Foss.management.TransferSignBillQueryForm'),
        Ext.create('Foss.management.TransferSignBillGrid')
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/*********************************END   转货车签单*******************************************/

/*********************************BEGIN  专线对发签单*******************************************/
Ext.define('Foss.management.RegularTruckSignBillModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	        {name:'id', type:'string'},
	        {name:'signBillNo', type:'string'},//签单编号
	        {name:'handoverNo', type:'string'},//交接单号        
		    {name:'useTruckOrgCode', type:'string'},//用车部门
		    {name:'useTruckOrgName', type:'string'},//用车部门名称
		    {name:'motorcadeCode', type:'string'},//所属车队Code
		    {name:'motorcadeName', type:'string'},//所属车队name
		    {name:'signBillDate', type:'string',
		    	convert: function(value) {
					if (value != null) {
						var date = new Date(value);						
						return Ext.Date.format(date,'Y-m-d');
					} else {
						return null;
					}
				}
		    },//日期			  
		    {name:'driverCode', type:'string'},//司机   
		    {name:'isEmpty', type:'string'},//司机   
		    {name:'driverName', type:'string'},//司机姓名			  
		    {name:'vehicleNo', type:'string'},//车牌号
		    {name:'vehicleTypeLength', type:'string'},//车型
		    {name:'vehicleTypeLengthName', type:'string'},//车型名称
		    {name:'lineDistance'},// 线路里程
			{name:'arrvRegionName', type:'string'},// 目的地
		    {name:'volume'},//体积
			{name:'weight'},//重量
			{name:'schedulingSign', type:'string'},//调度签字
			{name:'isEmpty', type:'string'},//是否空车
			{name:'lineCode'},// 线路
			{name:'lineName'},// 线路名称
			{name:'msldRoyalty'},// 对发单程线路里程提成
			{name:'emsldRoyalty'},// 空车对发单程里程提成
			{name:'driverRoyaltyAmount'}// 司机提成总额
			
	]
});
/**
 * 专线对发签单store
 */
Ext.define('Foss.management.RegularTruckSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.RegularTruckSignBillModel',
//    pageSize:10,
    autoLoad: false,
    proxy: {
      	type: 'ajax',
        url: management.realPath('queryRegularTruckSignBill.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.regularTruckSignBillList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        },
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result.message);
            }
        }
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {				
			var queryForm = management.regularTruckSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.regularTruckSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
						'vo.regularTruckSignBillDto.motorcadeCode' : queryParams.motorcadeCode,  //所属车队							
						'vo.regularTruckSignBillDto.isEmpty' : queryParams.isEmpty,  //用车类型						
						'vo.regularTruckSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
						'vo.regularTruckSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
						'vo.regularTruckSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
						'vo.regularTruckSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
						'vo.regularTruckSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
					}
				});	
			}
		},
		datachanged: function(store, operation, epots){
			var queryForm = management.regularTruckSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();		
				var useTruckOrgCode= queryParams.useTruckOrgCode;
				var motorcadeCode= queryParams.motorcadeCode;
				var isEmpty= queryParams.isEmpty;
				var driverCode=queryParams.driverCode;
				var vehicleNo=queryParams.vehicleNo;
				var vehicleTypeLength=queryParams.vehicleTypeLength;					
				var beginSignBillDate=queryParams.beginSignBillDate;
				var endSignBillDate=queryParams.endSignBillDate;	
		
				var array={
						vo : {
							regularTruckSignBillDto : {
								useTruckOrgCode : useTruckOrgCode,
								motorcadeCode : motorcadeCode,
								isEmpty : isEmpty,
								driverCode : driverCode,
								vehicleNo : vehicleNo,
								vehicleTypeLength : vehicleTypeLength,
								beginSignBillDate : beginSignBillDate,
								endSignBillDate : endSignBillDate
							}
						}
				};
				Ext.Ajax.request({
					url : management.realPath('queryRegularTruckSignBillByFee.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);							
						//在json中取出对象
						var regularTruckSignBillDto = json.vo.regularTruckSignBillDto;						
						if(regularTruckSignBillDto != null){
						  						
						   var driverCount=0;//司机个数
						   var  lineDistanceTotal=0;//总派送票数
						   var  volumeTotal=0;//总体积
						   var weightTotal=0;//总重量	
						   var inStockBillQtyTotal=0;// 总进仓票数
						   var upstairsFeeTotal=0;// 总上楼费
						   var  distanceTotal=0;// 总里程
						  					  
						   if(regularTruckSignBillDto.weightTotal!=null){
							   weightTotal=regularTruckSignBillDto.weightTotal;//总重量		
						   }
						   if(regularTruckSignBillDto.volumeTotal!=null){
							   volumeTotal=regularTruckSignBillDto.volumeTotal;//		
						   }
						   if(regularTruckSignBillDto.lineDistanceTotal!=null){
							   lineDistanceTotal=regularTruckSignBillDto.lineDistanceTotal;	
						   }
						  
						   driverCount=regularTruckSignBillDto.driverCount;//司机个数
						   
							var count = 0;								
							var toolbarArray = management.regularTruckSignBillGrid.getDockedItems('toolbar[dock="bottom"]')[0].items.items;
							for(var j=0;j<toolbarArray.length;j++){
								if(count==1){
									toolbarArray[j].setValue(driverCount);
								}else if(count==2){
									toolbarArray[j].setValue(lineDistanceTotal);
								}else if(count==3){
									toolbarArray[j].setValue(volumeTotal);
								}else if(count==4){
									toolbarArray[j].setValue(weightTotal);
								}								
								count ++;
							}
							 				    	
						}else{
							top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
					}
				});
			}
			
		},			
		load: function(store,records,successful,epots){
		 	if(store.getCount() == 0){
				 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.notFindData'), 'ok', 1000);
			}
		}		
		
	}
});


//专线对发签单grid
Ext.define('Foss.management.RegularTruckQueryGrid',{
	extend:'Ext.grid.Panel',
	title:  management.signBill.i18n('foss.management.signBill.RegularTruckQueryGrid.title'),//专线对发签单列表
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	columns: [{
		xtype:'actioncolumn',
		width:60,
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
			hidden: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
			tooltip: management.signBill.i18n('foss.management.button.operate'),//编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				management.editRegularTruckSignBillWindow = Ext.create('Foss.management.EditRegularTruckSignBillEditWindow');	
				var record = grid.getStore().getAt(rowIndex);
				management.editRegularTruckSignBillForm.getForm().reset();				
				editForm = management.editRegularTruckSignBillForm.getForm();
				editForm.findField('id').setValue(record.data.id);//id
				editForm.findField('handoverNo').setValue(record.data.handoverNo);//交接单号
				editForm.findField('signBillNo').setValue(record.data.signBillNo);//签单编号	
				editForm.findField('useTruckOrgName').setValue(record.data.useTruckOrgName);//用车部门名称				
				editForm.findField('driverName').setValue(record.data.driverName);//司机姓名
				editForm.findField('signBillDate').setValue(new Date(record.data.signBillDate));//日期
				editForm.findField('volume').setValue(record.data.volume);//体积
				editForm.findField('weight').setValue(record.data.weight);//'重量	
				editForm.findField('lineName').setValue(record.data.lineName);//线路名称
				editForm.findField('lineDistance').setValue(record.data.lineDistance);//线路里程
				editForm.findField('arrvRegionName').setValue(record.data.arrvRegionName);//目的地	
				editForm.findField('schedulingSign').setValue(record.data.schedulingSign);//调度签字
				editForm.findField('isEmpty').setValue(record.data.isEmpty);//调度签字
				//用车部门公共选择器
				var cmbUseTruckOrgCode = management.editRegularTruckSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);
				
				//所属车队公共选择器
				var cmbMotorcadeCode = editForm.findField('motorcadeCode');
				cmbMotorcadeCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.motorcadeCode}});
				cmbMotorcadeCode.setValue(record.data.motorcadeCode);
				editForm.findField('motorcadeName').setValue(record.data.motorcadeName);
				
				//司机选择器
				var cmbDriverCode = management.editRegularTruckSignBillForm.getForm().findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driverCode}});
				cmbDriverCode.setValue(record.data.driverCode);
				//Z车牌号				
				var cmbVehicleNo = management.editRegularTruckSignBillForm.getForm().findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.data.vehicleNo}});
				cmbVehicleNo.setValue(record.data.vehicleNo);
				
				//车型选择器
				var cmbVehicleTypeLength=management.editRegularTruckSignBillForm.getForm().findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.data.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.data.vehicleTypeLength);
				
				//线路选择器
				var cmbLineCode=management.editRegularTruckSignBillForm.getForm().findField('lineCode');
				cmbLineCode.getStore().load({params:{'lineVo.lineEntity.organizationCode' : record.data.lineCode}});		
				cmbLineCode.setValue(record.data.lineCode);
				
				management.editRegularTruckFeeInfoForm.getForm().reset();
				management.editRegularTruckFeeInfoForm.loadRecord(record);				
				management.editRegularTruckSignBillWindow.show(); 
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteSelect'),function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.regularTruckSignBillEntity.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteRegularTruckSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.deleteSuccess'), 'ok', 1000);
								 management.regularTruckSignBillPagingBar.moveFirst();			        
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteFailure') + result);
							},
							exception : function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('exception',json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverCode'),//司机
		dataIndex: 'driverCode',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		dataIndex: 'driverName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		dataIndex: 'motorcadeName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		dataIndex: 'signBillDate',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		dataIndex: 'vehicleTypeLengthName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.lineName'),//线路
		dataIndex: 'lineName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程
		dataIndex: 'lineDistance',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		dataIndex: 'volume',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		dataIndex: 'weight',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.isEmpty'),//重量
		dataIndex: 'isEmpty',
		flex:0.6,
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value == 'Y') {
					return management.signBill.i18n('foss.management.signBill.label.yes');
				} else {
					return management.signBill.i18n('foss.management.signBill.label.no');
				}
			}
		}
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,			 
		     style: 'font-weight:bold;'
		   },
		   items:[{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.33,
				minHeight:25
			},{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalDrivers'),//司机数	
			   labelWidth:60,
			   width:120,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totallineDistance'),//线路里程合计
			   labelWidth:110,
			   width:180,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:management.signBill.i18n('foss.management.signBill.label.totalVolume'),//体积合计	
			   labelWidth:70,	
			   width:160,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWeight'),//重量合计	
			   labelWidth:70,	
			   width:160,
			   dataIndex: 'weightTotal'
		   }]
		}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.RegularTruckSignBillStore');
		me.tbar=[{
			xtype: 'button', 
			disabled: !management.signBill.isPermission('management/addRegularTruckSignBillButton'),
			hidden: !management.signBill.isPermission('management/addRegularTruckSignBillButton'),
			text: management.signBill.i18n('foss.management.button.add'),//新增
			handler: function() {
				 management.regularTruckSignBillEditWindow = Ext.create('Foss.management.AddRegularTruckSignBillEditWindow');
				 management.regularTruckSignBillEditWindow.show();  
				 management.addRegularTruckSignBillForm.getForm().reset();
				 management.addRegularTruckFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins: 'pagesizeplugin'
		});	
		management.regularTruckSignBillPagingBar=me.bbar;
		management.regularTruckSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});



Ext.define('Foss.management.RegularTruckQueryForm',{
	extend:'Ext.form.Panel',	
	frame:false,
	columnWidth: 1,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	defaults:{
		labelWidth: 80,
		margin: '0 10 0 10'
	},
	items:[{		
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name: 'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		columnWidth: .33
	},{
		xtype: 'combobox',
		name:'isEmpty',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.transferType'),//用车类型
		columnWidth:.33,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.management.IsFirstTransferStore',{
			data: {
				'items':[
					{'code':'ALL','name':management.signBill.i18n('foss.management.signBill.label.ALL')},
					{'code':'Y','name':management.signBill.i18n('foss.management.signBill.label.empty')},//空车
					{'code':'N','name':management.signBill.i18n('foss.management.signBill.label.notEmpty')},//非空车
				]
			}
		})
	},{
		name: 'driverCode',
		xtype:'commonowndriverselector',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		columnWidth: .33
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',			
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		columnWidth: .33
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel:     management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		name: 'vehicleTypeLength',
		columnWidth: .33
	},{
		xtype: 'rangeDateField',
		fieldLabel:management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_queryRegularTrukSignBillForm_createTime',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		dateRange : 31,
		disallowBlank:true,
		fromName: 'beginSignBillDate',
		toName: 'endSignBillDate',
		time:false,
		format:'Y-m-d',
		fromValue:Ext.Date.format(new Date(),'Y-m-d'),
		toValue:Ext.Date.format(new Date(),'Y-m-d'),
		allowBlank: false,
		columnWidth: .50
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:management.signBill.i18n('foss.management.button.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
				//重新初始化交接时间
				this.up('form').getForm().findField('beginSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
				this.up('form').getForm().findField('endSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));

			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text:management.signBill.i18n('foss.management.button.search'),//查询
			disabled: !management.signBill.isPermission('management/queryRegularTruckSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryRegularTruckSignBillButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.regularTruckSignBillPagingBar.moveFirst();
				}					
			}
		},{
			text:management.signBill.i18n('foss.management.button.export'),//导 出
			disabled: !management.signBill.isPermission('management/queryExportRegularTruckSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryExportRegularTruckSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){					
					var queryParams = management.regularTruckSignBillQueryForm.getValues();		
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
		    			url: management.realPath('queryExportRegularTruckSignBill.action'),	 
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params : {
		    				'vo.regularTruckSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
							'vo.regularTruckSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
							'vo.regularTruckSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
							'vo.regularTruckSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
							'vo.regularTruckSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
							'vo.regularTruckSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
		    			},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('导出失败',result.message);
		    			}
	    			});					
				}				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		management.regularTruckSignBillQueryForm = me;  //加入全局变量中
	}
});



//录入专线对发签单
Ext.define('Foss.management.addRegularTruckSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title:  management.signBill.i18n('foss.management.signBill.addRegularTruckSignBillForm.title'),//录入专线对发签单
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			allowBlank: false,
			margin:'5 5 5 5'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,	
		maxLength:20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handoverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleAssembleNo'),//配载单编号
		allowBlank: false,
		maxLength:20,
		maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
		name: 'arrvRegionName',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrvRegionName'),//目的地
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
	   	columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		labelWidth: 60,	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
			if (text.getValue()!=null&&text.getValue()!='') {				
				 var params={vo:{regularTruckSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
			     Ext.Ajax.request({
		       	  url: management.realPath('queryRegularTruckSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		//车型选择器
					var cmbVehicleTypeLength=management.addRegularTruckSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.setValue('');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.regularTruckSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.regularTruckSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
			       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.selectFailure') );
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });				
				}
	 		}
		}		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype : 'commonlineselector',
		name: 'lineCode',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineName'),//线路
		allowBlank: false,		
		labelWidth: 60,	
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('lineName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'lineName',
		xtype: 'hiddenfield'
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		allowBlank: false,	
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,		
		maxValue:99999.99,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'lineDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程		
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'radiogroup',
        fieldLabel: management.signBill.i18n('foss.management.signBill.label.isEmpty'),//是否空车
        labelWidth:60,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.33,
        items: [
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isEmpty', inputValue: 'Y', checked:true},
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isEmpty', inputValue: 'N'}				           
        ]
	},{
		name: 'schedulingSign',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.schedulingSign'),//调度签字
		allowBlank: false,
		maxLength : 80,
		labelWidth: 60,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	}]
});
//录入专线对发签单
Ext.define('Foss.management.addRegularTruckFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10'			
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: management.signBill.i18n('foss.management.signBill.label.msldRoyalty'),//对发单程线路里程提成
				name: 'msldRoyalty',
				allowBlank: false,
				maxLength : 8,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:50,
				columnWidth:.33,
				xtype: 'numberfield',
				regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
				regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
				validator : function(value) {
					if(value != '' && value <= 0) {
						return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
					} 
				    return true;
				}						
			},{					
				labelAlign: 'top',
				name: 'emsldRoyalty',							
				fieldLabel: management.signBill.i18n('foss.management.signBill.label.emsldRoyalty'),//用车费用划分
				allowBlank: false,				
				maxLength : 8,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:60,
				columnWidth:.33,
				xtype: 'numberfield',
				regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
				regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
				validator : function(value) {
					if(value!=''&&value<=0) {
						return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
					} 
				    return true;
				}		
			},{
				labelAlign: 'top',
				fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalRoyalty'),//司机提成总额
				name: 'driverRoyaltyAmount',
				allowBlank: false,
				maxLength : 8,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:50,
				columnWidth:.33,
				xtype: 'numberfield',
				regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
				regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
				validator : function(value) {
					if(value!=''&&value<=0) {
						return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
					} 
				    return true;
				}						
			}]
});


//修改界面-专线对发签单
Ext.define('Foss.management.EditRegularTruckSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title: management.signBill.i18n('foss.management.signBill.EditRegularTruckSignBillForm.title'),//编辑专线对发签单
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			allowBlank: false,
			margin:'5 5 5 5'	
	},	
	items:[{
		name: 'id',
		xtype: 'hiddenfield'
	},{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		name: 'handoverNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleAssembleNo'),//配载单编号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
		name: 'arrvRegionName',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrvRegionName'),//目的地
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
	   	columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype:'dynamicorgcombselector',
		name: 'motorcadeCode',
		dispatchTeam : 'Y',
		allowBlank:false,
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.motorcade'),//所属车队
		columnWidth: .33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('motorcadeName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'motorcadeName',
		xtype: 'hiddenfield'
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33	,
		listeners:{
			'select':function(text,op){			
			if (text.getValue()!=null&&text.getValue()!='') {				
				 var params={vo:{regularTruckSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
			     Ext.Ajax.request({
		       	  url: management.realPath('queryRegularTruckSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		//车型选择器
					var cmbVehicleTypeLength=management.editRegularTruckSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.setValue('');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.regularTruckSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.regularTruckSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
			       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.selectFailure') );
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });				
				}
	 		}
		}	
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype : 'commonlineselector',
		name: 'lineCode',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineName'),//线路
		allowBlank: false,		
		labelWidth: 60,	
		maxLength : 30,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('lineName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'lineName',
		xtype: 'hiddenfield'
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		allowBlank: false,	
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,		
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
	    maxValue:99999.99,
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'lineDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程		
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	
	    maxValue:99999.99,   
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'radiogroup',
        fieldLabel: management.signBill.i18n('foss.management.signBill.label.isEmpty'),//是否空车
        labelWidth:60,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.33,
        items: [
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.yes'), name: 'isEmpty', inputValue: 'Y', checked:true},
            { boxLabel: management.signBill.i18n('foss.management.signBill.label.no'), name: 'isEmpty', inputValue: 'N'}				           
        ]
	},{
		name: 'schedulingSign',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.schedulingSign'),//调度签字
		allowBlank: false,
		maxLength : 80,
		labelWidth: 60,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	}]
});
//修改其他签单费用信息form
Ext.define('Foss.management.EditRegularTruckFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10'			
		},
		items:[{
			labelAlign: 'top',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.msldRoyalty'),//对发单程线路里程提成
			name: 'msldRoyalty',
			allowBlank: false,
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			labelWidth:50,
			columnWidth:.33,
			xtype: 'numberfield',
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			validator : function(value) {
		           if(value!=''&&value<=0) {
			            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			           } 
			           return true;
			  }						
		},{					
			labelAlign: 'top',
			name: 'emsldRoyalty',							
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.emsldRoyalty'),//用车费用划分
			allowBlank: false,				
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			labelWidth:60,
			columnWidth:.33,
			xtype: 'numberfield',
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			validator : function(value) {
		           if(value!=''&&value<=0) {
			            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			           } 
			           return true;
			  }		
		},{
			labelAlign: 'top',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalRoyalty'),//司机提成总额
			name: 'driverRoyaltyAmount',
			allowBlank: false,
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			labelWidth:50,
			columnWidth:.33,
			xtype: 'numberfield',
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			validator : function(value) {
				if(value != '' && value <= 0) {
					return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
				} 
				return true;
			}
		}]
});

//编辑面板
Ext.define('Foss.management.EditRegularTruckSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	frame: false,
	buttonAlign :'center',
	defaultType: 'textfield',	
	items:[	       
	      management.editRegularTruckSignBillForm = Ext.create('Foss.management.EditRegularTruckSignBillForm'),
	      management.editRegularTruckFeeInfoForm = Ext.create('Foss.management.EditRegularTruckFeeInfoForm')   
	 ],	
	 buttons: [{
		 text: management.signBill.i18n('foss.management.button.save'),	
		 disabled: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
		 hidden: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
	     align: 'center',
	     cls:'yellow_button',
	     handler: function() {
	    	 var form = this.up('form').getForm();
	      	 var vals = this.up('form').getForm().getValues(); 
	      	 vals.signBillDate=new Date(vals.signBillDate);
		     if(form.isValid()){							
		    	 var params={vo:{regularTruckSignBillEntity:vals}};
			     Ext.Ajax.request({
			    	 url: management.realPath('updateRegularTruckSignBill.action'),
			    	 jsonData: params,
			    	 success: function(response, opts) { 
			    		 form.reset();						    	
			    		 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);
			    		 management.regularTruckSignBillPagingBar.moveFirst();
			    		 management.editRegularTruckSignBillWindow.close();
			    	 },
			    	 failure: function(response, opts) {
			    		 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			    	 }
			     });
		     }
	      }
	  },{
	      text: management.signBill.i18n('foss.management.button.cancel'),	     
	      handler: function() {
		       management.editRegularTruckSignBillWindow.close();
	      }
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({},config);			
			me.callParent([cfg]);			
			me.editRegularTruckSignBillForm=management.editRegularTruckSignBillForm;
			me.editRegularTruckFeeInfoForm=management.editRegularTruckFeeInfoForm;
		}
});
//新增的面板
Ext.define('Foss.management.AddRegularTruckSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	frame: false,
	buttonAlign :'center',
	defaultType: 'textfield',	
	items:[	       
		management.addRegularTruckSignBillForm= Ext.create('Foss.management.addRegularTruckSignBillForm'),
		management.addRegularTruckFeeInfoForm= Ext.create('Foss.management.addRegularTruckFeeInfoForm')   
	],	
	buttons: [{
		text: management.signBill.i18n('foss.management.button.save'),
		disabled: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
		hidden: !management.signBill.isPermission('management/updateRegularTruckSignBillButton'),
		align: 'center',
		cls:'yellow_button',
		handler: function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues(); 
			vals.signBillDate=new Date(vals.signBillDate);
			if(form.isValid()){	
				this.disable(true);
				var params={vo:{regularTruckSignBillEntity:vals}};
				Ext.Ajax.request({
					url: management.realPath('addRegularTruckSignBill.action'),
					jsonData: params,
					success: function(response, opts) { 
						form.reset();						    	
						Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);
						management.regularTruckSignBillPagingBar.moveFirst();
						management.regularTruckSignBillEditWindow.close();
					},
					failure: function(response, opts) {
						Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
					}
				});
			}
		}
	},{
		text: management.signBill.i18n('foss.management.button.cancel'),	     
		handler: function() {
			management.regularTruckSignBillEditWindow.close();
		}
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);			
		me.callParent([cfg]);	
		//Foss.management.EditRegularTruckSignBillEditWindow
		me.addRegularTruckSignBillForm=management.addRegularTruckSignBillForm;
		me.addRegularTruckFeeInfoForm=management.addRegularTruckFeeInfoForm;
	}
});


//定义弹出的编辑窗口
Ext.define('Foss.management.EditRegularTruckSignBillEditWindow',{
	extend: 'Ext.window.Window',	
	width:700,		
	closable:true,
	closeAction:'hide',
	modal: true,
	editRegularTruckSignBillPanelForm : null,
	geteditRegularTruckSignBillPanelForm: function(){
		if(this.editRegularTruckSignBillPanelForm == null) {
			this.editRegularTruckSignBillPanelForm = Ext.create('Foss.management.EditRegularTruckSignBillPanel');
		}
		return this.editRegularTruckSignBillPanelForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.geteditRegularTruckSignBillPanelForm()
				];
		me.callParent([cfg]);
	}
});
//定义弹出的新增窗口
Ext.define('Foss.management.AddRegularTruckSignBillEditWindow',{
	extend: 'Ext.window.Window',	
	width:700,		
	closable:true,
	closeAction:'hide',
	modal: true,
	addRegularTruckSignBillPanelForm : null,
	getAddRegularTruckSignBillPanelForm: function(){
		if(this.addRegularTruckSignBillPanelForm == null){
			this.addRegularTruckSignBillPanelForm = Ext.create('Foss.management.AddRegularTruckSignBillPanel');
		}
		return this.addRegularTruckSignBillPanelForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getAddRegularTruckSignBillPanelForm()
				];
		me.callParent([cfg]);
	}
});

//专线对发页签单
Ext.define('Foss.management.regularTruckSignBillPanel',{
		extend:'Ext.panel.Panel',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		layout: 'auto',
		items:[
		        Ext.create('Foss.management.RegularTruckQueryForm'),
		        Ext.create('Foss.management.RegularTruckQueryGrid')
			 ]	
});
/*********************************END   专线对发签单*******************************************/


/*********************************BEGIN  其他签单*******************************************/

/**
 * 其他签单Model
 */
Ext.define('Foss.management.OtherSignBillModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	         {name:'id', type:'string'},
		        {name:'signBillNo', type:'string'},//签单编号
			    {name:'useTruckOrgCode', type:'string'},//用车部门
			    {name:'useTruckOrgName', type:'string'},//用车部门名称
			    {name:'signBillDate', type:'string',
			    	convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d');
						} else {
							return null;
						}
					}
			    },//日期			  
			    {name:'driverCode', type:'string'},//司机			    
			    {name:'driverName', type:'string'},//司机姓名			  
			    {name:'vehicleNo', type:'string'},//车牌号
			    {name:'vehicleTypeLength', type:'string'},//车型
			    {name:'vehicleTypeLengthName', type:'string'},//车型名称
			    {name:'arrvRegionName', type:'string'},// 目的地			  
			    {name:'useTruckType', type:'string'},//用车类型	
				{name:'signBillType', type:'string'},// 签单类型	
			    {name:'otherFee', type:'string'},//其它金额
			    {name:'billQty'},// 票数
			    {name:'lineDistance'},// 线路里程				   
			    {name:'volume'},//体积
			    {name:'weight'},//重量
			    {name:'useTruckDuration'},//用车时间			    
			    {name:'driverRoyalty'},// 司机提成
			    {name:'notes'},//备注
			    {name:'useTruckFee'}//用车费用划分
	]
});


/**
 * 其他签单store
 */
Ext.define('Foss.management.OtherSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.OtherSignBillModel',
//    pageSize:10,
    autoLoad: false,
    proxy: {
      	type: 'ajax',
        url: management.realPath('queryOtherSignBill.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.otherSignBillList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        },
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),  result.message);
            }
        }
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {				
			var queryForm = management.OtherSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.otherSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
						'vo.otherSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
						'vo.otherSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
						'vo.otherSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
						'vo.otherSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
						'vo.otherSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
					}
				});	
			}
		},
		datachanged: function(store, operation, epots){
			var queryForm = management.OtherSignBillQueryForm;
			if (queryForm != null) {					
				var queryParams = queryForm.getValues();		
				var useTruckOrgCode= queryParams.useTruckOrgCode;
				var driverCode=queryParams.driverCode;
				var  vehicleNo=queryParams.vehicleNo;
				var vehicleTypeLength=queryParams.vehicleTypeLength;					
				var beginSignBillDate=queryParams.beginSignBillDate;
				var endSignBillDate=queryParams.endSignBillDate;	
		
				var array={vo:{otherSignBillDto:{useTruckOrgCode:useTruckOrgCode,driverCode:driverCode,vehicleNo:vehicleNo,
					vehicleTypeLength:vehicleTypeLength,beginSignBillDate:beginSignBillDate,endSignBillDate:endSignBillDate}}};
				Ext.Ajax.request({
					url : management.realPath('queryOtherSignBillByFee.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);							
						//在json中取出对象
						var otherSignBillDto = json.vo.otherSignBillDto;						
						if(otherSignBillDto != null){
						   var weightTotal=0;//总重量							
						   var driverCount=0;//司机个数
						   var  lineDistanceTotal=0;//总线路里程
						   var  volumeTotal=0;//总体积
						   var  useTruckDurationTotal=0;//总的用车时间
						   var billQtyCount=0;// 总票数
						   var otherFeeTotal=0;// 其它金额合计
						   
						   if(otherSignBillDto.weightTotal!=null){
							    weightTotal=otherSignBillDto.weightTotal;//总重量	 
						   }
						   if(otherSignBillDto.driverCount!=null){
							    driverCount=otherSignBillDto.driverCount;//司机个数 
						   }
						   if(otherSignBillDto.lineDistanceTotal!=null){
							   lineDistanceTotal=otherSignBillDto.lineDistanceTotal;//总线路里程 
						   }
						   if(otherSignBillDto.volumeTotal!=null){
							   volumeTotal=otherSignBillDto.volumeTotal;//总体积	 
						   }
						   if(otherSignBillDto.useTruckDurationTotal!=null){
							   useTruckDurationTotal=otherSignBillDto.useTruckDurationTotal;//总的用车时间	 
						   }
						   if(otherSignBillDto.billQtyCount!=null){
							   billQtyCount=otherSignBillDto.billQtyCount;// 总票数 
						   }
						   if(otherSignBillDto.otherFeeTotal!=null){
							   otherFeeTotal=otherSignBillDto.otherFeeTotal;// 其它金额合计	 
						   }
						     
						   
							var count = 0;								
							var toolbarArray = management.OtherSignBillGrid.getDockedItems('toolbar[dock="bottom"]')[0].items.items;
							for(var j=0;j<toolbarArray.length;j++){
								if(count==0){
									toolbarArray[j].setValue(driverCount);
								}else if(count==1){
									toolbarArray[j].setValue(lineDistanceTotal);
								}else if(count==2){
									toolbarArray[j].setValue(volumeTotal);
								}else if(count==3){
									toolbarArray[j].setValue(weightTotal);
								}else if(count==4){
									toolbarArray[j].setValue(billQtyCount);
								}else if(count==5){
									toolbarArray[j].setValue(useTruckDurationTotal);
								}else if(count==6){
									toolbarArray[j].setValue(otherFeeTotal);
								}								
								count ++;
							}
							 				    	
						}else{
							top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),json.message);
					}
				});
			}
	
			
		
		},			
		load: function(store,records,successful,epots){
		 	if(store.getCount() == 0){
				 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.notFindData'), 'ok', 1000);
			}
		}
				
				
		
	}
});

//其他签单查询条件
Ext.define('Foss.management.OtherSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame: false,
	border: false,		
	layout:'column',
	defaults:{
		xtype: 'textfield',
		labelWidth: 85,		
		margin: '5 10 5 10'
	},
	items:[{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name: 'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		columnWidth: .3
	},{
		xtype:'commonowndriverselector',
		name: 'driverCode',		
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		columnWidth: .3
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		columnWidth: .3
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		columnWidth: .3
	},{
		xtype: 'rangeDateField',
		fieldLabel:management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_queryOtherSignBillForm_createTime',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		time:false,
		dateRange : 31,
		format:'Y-m-d',
		disallowBlank:true,
		fromName: 'beginSignBillDate',
		toName: 'endSignBillDate',		
		fromValue:Ext.Date.format(new Date(),'Y-m-d'),
		toValue:Ext.Date.format(new Date(),'Y-m-d'),
		allowBlank: false,
		columnWidth: .50
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:management.signBill.i18n('foss.management.button.reset'),//重置
			columnWidth:.08,
			handler:function(){		
				this.up('form').getForm().reset();				
				//重新初始化交接时间
				this.up('form').getForm().findField('beginSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
				this.up('form').getForm().findField('endSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));

			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text:management.signBill.i18n('foss.management.button.search'),//查询
			disabled: !management.signBill.isPermission('management/queryOtherSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryOtherSignBillButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.OtherSignBillGrid.store.load();
				}
				
			}
		},{
			text:management.signBill.i18n('foss.management.button.export'),//导 出
			disabled: !management.signBill.isPermission('management/queryExportOtherSignBillButton'),
			hidden: !management.signBill.isPermission('management/queryExportOtherSignBillButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){				
				var form = this.up('form').getForm();
				if(form.isValid()){
					var queryParams = management.OtherSignBillQueryForm.getValues();		
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
		    			url: management.realPath('queryExportOtherSignBill.action'),	 
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params : {
		    				'vo.otherSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
							'vo.otherSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
							'vo.otherSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
							'vo.otherSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
							'vo.otherSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
							'vo.otherSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
		    			},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('导出失败',result.message);
		    			}
	    			});
				}				
				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		management.OtherSignBillQueryForm = me;  //加入全局变量中
	}
});


//其他签单签单grid
Ext.define('Foss.management.OtherSignBillGrid',{
	extend:'Ext.grid.Panel',
	title:  management.signBill.i18n('foss.management.OtherSignBillGrid.title'),//其他签单列表
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	columns: [{
		xtype:'actioncolumn',
		width:60,
		text: management.signBill.i18n('foss.management.button.operate'),//操作
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !management.signBill.isPermission('management/updateOtherSignBillButton'),
			hidden: !management.signBill.isPermission('management/updateOtherSignBillButton'),
			tooltip: management.signBill.i18n('foss.management.button.operate'),//编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				management.editOtherSignBillWindow = Ext.create('Foss.management.editOtherSignBillWindow');		
				var record = grid.getStore().getAt(rowIndex);
				management.editOtherSignBillForm.getForm().reset();		
				var editOtherForm=management.editOtherSignBillForm.getForm();
				editOtherForm.findField('id').setValue(record.data.id);//id
				editOtherForm.findField('signBillNo').setValue(record.data.signBillNo);//签单编号	
				editOtherForm.findField('useTruckType').setValue(record.data.useTruckType);//用车类型
				editOtherForm.findField('signBillType').setValue(record.data.signBillType);//签单类型
				editOtherForm.findField('arrvRegionName').setValue(record.data.arrvRegionName);//目的地
				editOtherForm.findField('useTruckOrgName').setValue(record.data.useTruckOrgName);//用车部门名称				
				editOtherForm.findField('driverName').setValue(record.data.driverName);//司机姓名
				editOtherForm.findField('signBillDate').setValue(new Date(record.data.signBillDate));//日期
				editOtherForm.findField('volume').setValue(record.data.volume);//体积
				editOtherForm.findField('weight').setValue(record.data.weight);//'重量	
				editOtherForm.findField('billQty').setValue(record.data.billQty);//票数
				editOtherForm.findField('lineDistance').setValue(record.data.lineDistance);//线路里程				
				editOtherForm.findField('useTruckDuration').setValue(record.data.useTruckDuration);//用车时间				
				editOtherForm.findField('otherFee').setValue(record.data.otherFee);//其它金额				
				editOtherForm.findField('notes').setValue(record.data.notes);//备注	
				
				
				var cmbUseTruckOrgCode = management.editOtherSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'commonOrgVo.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);
				
				//司机选择器
				var cmbDriverCode = management.editOtherSignBillForm.getForm().findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driverCode}});
				cmbDriverCode.setValue(record.data.driverCode);
				//车牌号				
				var cmbVehicleNo = management.editOtherSignBillForm.getForm().findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.data.vehicleNo}});
				cmbVehicleNo.setValue(record.data.vehicleNo);
				
				//车型选择器
				var cmbVehicleTypeLength=management.editOtherSignBillForm.getForm().findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.data.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.data.vehicleTypeLength);
				
				management.editOtherFeeInfoForm.getForm().reset();
				management.editOtherFeeInfoForm.getForm().loadRecord(record);					
				management.editOtherSignBillWindow.show();
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: management.signBill.i18n('foss.management.button.delete'),//删除
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Msg.confirm(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteSelect'),function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteOtherSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.deleteSuccess'), 'ok', 1000);
								 management.OtherSignBillGrid.store.load();				        
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.deleteFailure') + result);
							},
							exception : function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('exception',json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverCode'),//司机
		dataIndex: 'driverCode',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		dataIndex: 'driverName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
		dataIndex: 'signBillDate',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号码
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		dataIndex: 'vehicleTypeLengthName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.signBillType'),//签单类型
		dataIndex: 'signBillType',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value=='INTERIOR_SIGN_BILL'){
					return management.signBill.i18n('foss.management.signBill.label.interiorsignbill');
				}else if(value=='SPECIAL_SIGN_BILL'){
					return management.signBill.i18n('foss.management.signBill.label.specialsignbill');//专车签单
				} else{
					return management.signBill.i18n('foss.management.signBill.label.othersignbill');//其他签单
				}				
			}
		},
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.arrvRegionName'),//目的地
		dataIndex: 'arrvRegionName',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程
		dataIndex: 'lineDistance',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.billQty'),//票数
		dataIndex: 'billQty',
		flex:0.7
	},{
		header: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		dataIndex: 'volume',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		dataIndex: 'weight',
		flex:0.6
	},{
		header: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		dataIndex: 'useTruckDuration',
		flex:0.9
	},{
		header: management.signBill.i18n('foss.management.signBill.label.otherFee'),//其它金额
		dataIndex: 'otherFee',
		flex:0.9
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,			 
		     style: 'font-weight:bold;'
		   },
		   items:[{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalDrivers'),//司机数	
			   labelWidth:60,
			   width:90,			  
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel:  management.signBill.i18n('foss.management.signBill.label.totallineDistance'),//线路里程合计
			   labelWidth:100,
			   width:160,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:management.signBill.i18n('foss.management.signBill.label.totalVolume'),//体积合计	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalWeight'),//重量合计	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.totalBills'),//票数合计	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'billQtyCount'
		  },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckTimeTotal'),//用车时间合计
			   labelWidth:100,
			   width:150,
			   dataIndex: 'useTruckDurationTotal'
		   },{
			   fieldLabel: management.signBill.i18n('foss.management.signBill.label.otherFeeTotal'),//其它金额合计
			   labelWidth:100,
			   width:150,
			   dataIndex: 'otherFeeTotal'
		   }]
		}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.OtherSignBillStore');
		me.tbar=[{
			xtype: 'button',
			disabled: !management.signBill.isPermission('management/addOtherSignBillButton'),
			hidden: !management.signBill.isPermission('management/addOtherSignBillButton'),
			text: management.signBill.i18n('foss.management.button.add'),//新增
			handler: function() {
			   management.AddOtherSignBillWindow = Ext.create('Foss.management.AddOtherSignBillWindow');			 
			   management.AddOtherSignBillWindow.center().show();  
			   management.addOtherSignBillForm.getForm().reset();
			   management.addOtherFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins: 'pagesizeplugin'
		});	
		management.OtherSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});

//其他签单-新增界面
Ext.define('Foss.management.addOtherSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title: management.signBill.i18n('foss.management.signBill.addOtherSignBillForm.title'),//录入其他签单
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			margin:'5 5 5 5'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.333
	},{
		xtype: 'combo',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckType'),//用车类型
		allowBlank: false,
		name: 'useTruckType',
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.333,
		//因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		//如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode:'local',
		//默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction:'all',
		//延时查询，与下面的参数配合
		//typeAhead:true,
		//默认250
		//typeAheadDelay:3000,
		//false则不可编辑，默认为 true
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('USE_TRUCK_TYPE')		
	},{
		xtype: 'combo',
		name: 'signBillType',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillType'),//签单类型
		allowBlank: false,
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.333,
		//因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		//如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode:'local',
		//默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction:'all',
		//延时查询，与下面的参数配合
		//typeAhead:true,
		//默认250
		//typeAheadDelay:3000,
		//false则不可编辑，默认为 true		
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('SIGN_BILL_TYPE')		
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}	
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		name: 'arrvRegionName',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrvRegionName'),//目的地
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .34
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
	    blankText: '部门不能为空!',
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		labelWidth: 60,	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners:{
			'select':function(text,op){			
			if (text.getValue()!=null&&text.getValue()!='') {				
				 var params={vo:{otherSignBillDto:{vehicleNo:text.getValue()}}};	//配载车次号			
			     Ext.Ajax.request({
		       	  url: management.realPath('queryOtherSignBillByVehicleNo.action'),
		       	  jsonData: params,
		       	  success: function(response, opts) {
		       		var result = Ext.decode(response.responseText);	//查询的结果
		       		 //车型选择器
					var cmbVehicleTypeLength=management.addOtherSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.otherSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.otherSignBillEntity.vehicleTypeLength);
		       	  },
		       	  failure: function(response, opts) {
			       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
		       	  },
		       	  exception : function(response) {
    					var json = Ext.decode(response.responseText);
    					Ext.Msg.alert('exception',json.message);
    				}
		         });
				
				}	
			
	 		}
		}
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 50,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		allowBlank: false,			
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,		
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 0, 
		name: 'billQty',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.billQty'),//票数
		allowBlank: false,
		regex: /^[1-9]\d*$/ ,	
		regexText:"格式输入有误！请输入大于0的整数",	
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'lineDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程		
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:"线路里程格式输入有误！",
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		decimalPrecision: 1, 
		labelWidth:100,
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,1})?$/,	
		regexText:"用车时间格式输入有误！",
		maxLength : 5,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33
	},{
		xtype: 'numberfield',			
		name: 'otherFee',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.otherFee'),//其它金额
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
//		regexText:"格式输入有误！请输入大于0的整数",
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:80,
		columnWidth:.33,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.feeNotBeNegative');
			}
			return true;
		}
	},{		
		name: 'notes',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.notes'),//备注	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.33
	}]
});
//其他签单费用信息form
Ext.define('Foss.management.addOtherFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	   width:670,	  
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 10',
			anchor: '90%'					
		},
		items:[{
				xtype: 'numberfield',
				labelAlign: 'top',
				fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverRoyaltyTo'),//司机提成				
				name: 'driverRoyalty',
				allowBlank: false,
				maxLength : 8,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				labelWidth:50,
				columnWidth:.33,
				regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
				regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
				validator : function(value) {
			           if(value!=''&&value<=0) {
				            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
				           } 
				           return true;
				  }						
			},{
				xtype: 'numberfield',
				labelAlign: 'top',
				name: 'useTruckFee',
				allowBlank: false,
				maxLength : 8,
			    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
				fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckFeeTo'),//用车费用						
				labelWidth:60,
				columnWidth:.33,
				regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
				regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
				validator : function(value) {
			           if(value!=''&&value<=0) {
				            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
				           } 
				           return true;
				  }		
			}]
});
//编辑界面其他签单
Ext.define('Foss.management.editOtherSignBillForm',{
	 extend: 'Ext.form.Panel',
	 layout: 'column',
	 frame: false,
	 title:  management.signBill.i18n('foss.management.signBill.editOtherSignBillForm.title'),//编辑其他签单签单
	 width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			margin:'10 5 5 10'		
	},	
	items:[{
		name: 'id',
		xtype: 'hiddenfield'
	},{
		name: 'signBillNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillNo'),//签单编号
		allowBlank: false,	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33
	},{
		xtype: 'combo',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckType'),//用车类型
		allowBlank: false,
		name: 'useTruckType',
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.33,
		//因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		//如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode:'local',
		//默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction:'all',
		//延时查询，与下面的参数配合
		//typeAhead:true,
		//默认250
		//typeAheadDelay:3000,
		//false则不可编辑，默认为 true				
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('USE_TRUCK_TYPE')		
	},{
		xtype: 'combo',
		name: 'signBillType',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillType'),//签单类型
		allowBlank: false,
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.33,
		//因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		//如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode:'local',
		//默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction:'all',
		//延时查询，与下面的参数配合
		//typeAhead:true,
		//默认250
		//typeAheadDelay:3000,
		//false则不可编辑，默认为 true			
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('SIGN_BILL_TYPE')		
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverName'),//司机姓名
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('driverName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'driverName',
		xtype: 'hiddenfield'
	},{
		name: 'arrvRegionName',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.arrvRegionName'),//目的地
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.signBillDate'),//日期
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: management.signBill.i18n('foss.management.signBill.exception.signDateFailure'),//输入的日期格式不对  		
  		columnWidth: .33
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		name:'useTruckOrgCode',
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckOrgCode'),//用车部门
		allowBlank:false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	   
		columnWidth:.33,
		listeners: {
			'select': function(field, records, eOpts) {					
				var record = records[0],
					name = record.get('name');
				this.up('form').form.findField('useTruckOrgName').setValue(field.rawValue);
				
			}
		}
	},{
		name: 'useTruckOrgName',
		xtype: 'hiddenfield'
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleNo'),//车牌号
		allowBlank: false,
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.vehicleTypeLength'),//车型
		allowBlank: false,
		maxLength : 10,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 50,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'volume',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.volumeInfo'),//体积
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'weight',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.weightInfo'),//重量
		allowBlank: false,			
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText:management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误
		maxLength : 8,		
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 80,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 0, 
		name: 'billQty',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.billQty'),//票数
		allowBlank: false,
		regex: /^[1-9]\d*$/ ,	
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 9,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'lineDistance',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.lineDistance'),//线路里程		
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		labelWidth: 60,	
		columnWidth:.33
	},{
		xtype: 'numberfield',
		decimalPrecision: 1,
		name: 'useTruckDuration',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.useTruckDuration'),//用车时间
		labelWidth:100,
		allowBlank: false,
		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,1})?$/,	
		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 5,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!	    
		columnWidth:.33
	},{
		xtype: 'numberfield',
		name: 'otherFee',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.otherFee'),//其它金额
		allowBlank: false,
//		regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,		
//		regexText: management.signBill.i18n('foss.management.signBill.label.regexText'),//格式输入有误！
		maxLength : 8,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:80,
		columnWidth:.33,
		validator : function(value) {
			if(value != '' && value < 0) {
				return management.signBill.i18n('foss.management.signBill.exception.feeNotBeNegative');
			}
			return true;
		}
	},{
		name: 'notes',							
		fieldLabel: management.signBill.i18n('foss.management.signBill.label.notes'),//备注	
		maxLength : 20,
	    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
		labelWidth:60,
		columnWidth:.33
	}]
});
//编辑其他签单费用信息form
Ext.define('Foss.management.editOtherFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:management.signBill.i18n('foss.management.signBill.signFeeInfo.title'),//费用信息	
	   width:670,	   
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 10',
			anchor: '90%'					
		},
		items:[{
			xtype: 'numberfield',
			labelAlign: 'top',
			fieldLabel: management.signBill.i18n('foss.management.signBill.label.driverRoyaltyTo'),//司机提成			
			name: 'driverRoyalty',
			allowBlank: false,
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			labelWidth:50,
			columnWidth:.33,
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			validator : function(value) {
		           if(value!=''&&value<=0) {
			            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			           } 
			           return true;
			  }						
		},{
			xtype: 'numberfield',
			labelAlign: 'top',
			name: 'useTruckFee',
			allowBlank: false,
			maxLength : 8,
		    maxLengthText: management.signBill.i18n('foss.management.signBill.label.maxLengthText'),//长度已超过最大限制!
			fieldLabel:  management.signBill.i18n('foss.management.signBill.label.useTruckFeeTo'),//用车费用							
			labelWidth:60,
			columnWidth:.33,
			regex: /^[+]?(([1-9]\d*[.]?)|(0.))(\d{0,2})?$/,	
			regexText:management.signBill.i18n('foss.management.signBill.label.regexTextToNum'),//格式输入有误！请输入整数
			validator : function(value) {
		           if(value!=''&&value<=0) {
			            return management.signBill.i18n('foss.management.signBill.exception.enterEcOneNum');
			           } 
			           return true;
			  }		
		}]
});

//其他签单-新增界面
Ext.define('Foss.management.AddOtherSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.addOtherSignBillForm= Ext.create('Foss.management.addOtherSignBillForm'),
	       management.addOtherFeeInfoForm= Ext.create('Foss.management.addOtherFeeInfoForm')   
	 ],
	  buttons: [{
	      text: management.signBill.i18n('foss.management.button.save'),
	      disabled: !management.signBill.isPermission('management/updateOtherSignBillButton'),
	      hidden: !management.signBill.isPermission('management/updateOtherSignBillButton'),
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
	 		  if(form.isValid()){							
					var params={vo:{otherSignBillEntity:vals}};
				     Ext.Ajax.request({
			       	  url: management.realPath('addOtherSignBill.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) { 
				    	form.reset();						    	
				       Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.saveSuccess'), 'ok', 1000);						     
				       management.AddOtherSignBillWindow.center().close(); 
				       management.OtherSignBillGrid.store.load();
			       	  },
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.saveFailure'));
			       	  }
			         });
		    	}

	      }
	  },{
	      text: management.signBill.i18n('foss.management.button.cancel'),	     
	      handler: function() {
	    	  management.AddOtherSignBillWindow.close();

	      }
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({},config);
			me.callParent([cfg]);
			me.addOtherSignBillForm = management.addOtherSignBillForm;
			me.addOtherFeeInfoForm =  management.addOtherFeeInfoForm;
		}
});



//编辑界面
Ext.define('Foss.management.editOtherSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.editOtherSignBillForm= Ext.create('Foss.management.editOtherSignBillForm'),
	       management.editOtherFeeInfoForm= Ext.create('Foss.management.editOtherFeeInfoForm')   
	 ],
	  buttons: [{
	      text: management.signBill.i18n('foss.management.button.save'),
	      disabled: !management.signBill.isPermission('management/updateOtherSignBillButton'),
	      hidden: !management.signBill.isPermission('management/updateOtherSignBillButton'),
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
		      if(form.isValid()){							
					var params={vo:{otherSignBillEntity:vals}};
				     Ext.Ajax.request({
			       	  url: management.realPath('updateOtherSignBill.action'),
			       	  jsonData: params,
			       	  success: function(response, opts) { 
			       	   form.reset();						    	
				       Ext.ux.Toast.msg(management.signBill.i18n('foss.management.messageBox.alert.tip.title'), management.signBill.i18n('foss.management.signBill.exception.updateSuccess'), 'ok', 1000);
				       management.OtherSignBillGrid.store.load();
				       management.editOtherSignBillWindow.center().close(); 
			       	  },
			       	  failure: function(response, opts) {
				       Ext.Msg.alert(management.signBill.i18n('foss.management.messageBox.alert.tip.title'),management.signBill.i18n('foss.management.signBill.exception.updateFailure') );
			       	  }
			         });
		    	}
					

	      }
	  },{
	      text: management.signBill.i18n('foss.management.button.cancel'),	     
	      handler: function() {
	    	  management.editOtherSignBillWindow.center().close(); 

	      }
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({},config);
			me.callParent([cfg]);
			me.editOtherSignBillForm = management.editOtherSignBillForm;
			me.editOtherFeeInfoForm =  management.editOtherFeeInfoForm;
		}
});


//定义弹出的编辑其他签单窗口 
Ext.define('Foss.management.editOtherSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:700,		
	closable:true,
	closeAction:'hide',
	modal: true,
	editOtherSignBillForm : null,
	getEditOtherSignBillForm: function(){
		if(this.editOtherSignBillForm==null){
			this.editOtherSignBillForm = Ext.create('Foss.management.editOtherSignBillPanel');
		}
		return this.editOtherSignBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditOtherSignBillForm()
				];
		me.callParent([cfg]);
	}
});


//其他签单-新增窗口
Ext.define('Foss.management.AddOtherSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:700,		
	closable:true,
	closeAction:'hide',
	modal: true,
	addOtherSignBillForm : null,
	getAddOtherSignBillForm: function(){
		if(this.addOtherSignBillForm == null){
			this.addOtherSignBillForm = Ext.create('Foss.management.AddOtherSignBillPanel');
		}
		return this.addOtherSignBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getAddOtherSignBillForm()
				];
		me.callParent([cfg]);
	}
});
//其他签单签单
Ext.define('Foss.management.OtherSignBillPanel',{
	extend: 'Ext.panel.Panel',
	cls: 'panelContentNToolbar',
	bodyCls:'panelContentNToolbar-body',
	margin:'10 5 10 10',
	layout: 'auto',
	items:[
        Ext.create('Foss.management.OtherSignBillQueryForm'),
        Ext.create('Foss.management.OtherSignBillGrid')
        
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/*********************************END   其他签单*******************************************/

/*****************************************主框架**********************************************/

Ext.define('Foss.management.SignbillTabPanel',{
	extend:'Ext.tab.Panel',
	cls:"innerTabPanel",
	bodyCls:"overrideChildLeft",
	activeTab:0,
	autoScroll:false,
	frame: false,
	constructor: function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				title: management.signBill.i18n('foss.management.signBill.SendSignbillPanel.title'),//派送
				tabConfig:{width:100},
				items: Ext.create('Foss.management.SendSignbillPanel')
			 },{
				title: management.signBill.i18n('foss.management.signBill.FocusSignBillPanel.title'),//集中接货
				tabConfig:{width:100},
				items: Ext.create('Foss.management.FocusSignBillPanel')
			},{
				title: management.signBill.i18n('foss.management.signBill.TransferSignBillPanel.title'),//转货车
				tabConfig:{width:100},				
				items: Ext.create('Foss.management.TransferSignBillPanel')
			},{
				title: management.signBill.i18n('foss.management.signBill.regularTruckSignBillPanel.title'),//专线对发
				tabConfig:{width:100},				
				items: Ext.create('Foss.management.regularTruckSignBillPanel')
			},{
				title: management.signBill.i18n('foss.management.signBill.OtherSignBillPanel.title'),//其他
				tabConfig:{width:80},				
				items: Ext.create('Foss.management.OtherSignBillPanel')
			}
		];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var tabPanel = Ext.create('Foss.management.SignbillTabPanel'); 
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-signBillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items: [tabPanel],
		renderTo: 'T_management-signBillIndex-body'
	});
});