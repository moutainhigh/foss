//TODO 暂时分开，交付时合并到signBill.js

//集中接货签单MODEL
Ext.define('Foss.management.FocusSignBillModel',{
	extend: 'Ext.data.Model',
	fields: [{name:'id', type: 'string'},
	        {name:'signBillNo', type:'string'},			//签单编号
			{name:'useTruckOrgCode',	type:'string'}, //用车部门
			{name:'driverCode', type:'string'}, 		//司机姓名
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
	        		if(v=='CH' || v=='Y'){
	        			return true;
	        		}else{
	        			return false;
	        		}
	        	}
	        },		//是否现付
	        {name:'useTruckOrgCode',	type:'string'}, //用车部门
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
	pageSize: 20,
	proxy: {
        type: 'ajax',
        actionMethods: {read: 'POST'},
        url: management.realPath('queryFocusSignBill.action'),
        reader: {
            type: 'json',
            totalProperty : 'totalCount',
            root: 'vo.focusSignBillDtoList'
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
		xtype:'commonsaledepartmentselector',
		name: 'useTruckOrgCode',
		fieldLabel: '用车部门',
		columnWidth: .25
	},{
		xtype:'commonowndriverselector',
		fieldLabel: '司机姓名',
		name: 'driverCode',
		forceSelection: true,
		columnWidth:.25
	},{
		xtype: 'commonowntruckselector',
		fieldLabel: '车牌号码',
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
		fieldLabel:     '车型',
		name: 'vehicleTypeLength',
		forceSelection: true,
		columnWidth:.25
	},{
		xtype:'rangeDateField',
		fieldLabel: '日期',
		fromName: 'signBillDateFrom',
		toName: 'signBillDateTo',
		allowBlank: false,
		disallowBlank: true,
		dateRange: '31',
		columnWidth: .5
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
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			columnWidth:.835,
			html: '&nbsp;'
		},{
			text:'查询',
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
					Ext.Msg.alert('提示','请输入必填的查询项');
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
	frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	title:'集中接货签单列表',
	columns:[{
		xtype:'actioncolumn',
		width:60,
		text: '操作',
		items: [{
			iconCls: 'deppon_icons_edit',
			tooltip: '编辑',
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				Ext.Ajax.request({
					params: {'vo.signBillNo': record.get('signBillNo')},
					url: management.realPath('queryFocusSignBillByNo.action'),
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
						//车型选择器
						var cmbVehicleTypeLength = baseForm.findField('vehicleTypeLength');
						cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : content.vehicleTypeLength}});		
						cmbVehicleTypeLength.setValue(content.vehicleTypeLength);
						//取得明细grid
						var grid = Ext.getCmp('Foss_management_focusSignBillCostGrid_ID');
						var data = result.vo.feeDetailList;
						grid.getStore().loadData(data,false);
						
						var total = grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');
						window.center().show();
					},
					failure: function(response){
						 var result = Ext.decode(response.responseText);
						 Ext.Msg.alert('failure','编辑失败！' + result);
					}
				});
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: '删除',
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				Ext.MessageBox.buttonText.yes = "是";  
				Ext.MessageBox.buttonText.no = "否"; 
				Ext.Msg.confirm('提示','是否删除整个签单信息?',function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.signBillNo': grid.getStore().getAt(rowIndex).get('signBillNo')
							},
							url: management.realPath('deleteFocusSignBill.action'),
							success: function(response){
								 Ext.ux.Toast.msg('提示', '删除成功!', 'ok', 1000);
								 management.focusSignBillGrid.store.load(); 
							},
							failure: function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert('failure','删除失败!' + result);
							},
							exception: function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('警告',json.message);
	        				}
						});
					}
				});
			}
		}]
	},{
		header: '签单id',
		dataIndex: 'id',
		hidden : true
	},{
		header: '签单编号',
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: '用车部门',
		dataIndex: 'useTruckOrgCode',
		editor: {
			xtype: 'commonsaledepartmentselector',
			readOnly: true,
			fieldLabel: ''
		},
		flex:0.7
	},{
		header: '司机姓名',
		dataIndex: 'driverCode',
		editor: {
			xtype: 'commonowndriverselector',
			readOnly: true,
			fieldLabel: ''
		},
		flex:0.7
	},{
		header: '日期',
		dataIndex: 'signBillDate',
		renderer: Ext.util.Format.dateRenderer('Y-m-d'),
		flex:0.7
	},{
		header: '车牌号码',
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: '车型',
		dataIndex: 'vehicleTypeLength',
		flex:0.7
	},{
		header: '行驶公里<br/>(公里)',
		dataIndex: 'runKm',
		flex:0.7
	},{
		header: '接货票数',
		dataIndex: 'waybillQtyTotal',
		flex:0.7
	},{
		header: '体积<br/>(方)',
		dataIndex: 'volume',
		flex:0.6
	},{
		header: '重量<br/>(公斤)',
		dataIndex: 'weight',
		flex:0.6
	},{
		header: '上楼接货票数',
		dataIndex: 'upstairsBillQty',
		flex:0.9
	},{
		header: '单独接货票数',
		dataIndex: 'singleReceiveBillQty',
		flex:0.9
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.FocusSignBillStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store: me.store,
			pageSize: 50
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
		   text:'新增',
		   columnWidth:.08,
		   handler:function(){
			   Ext.create('Foss.management.FocusSignBillCostWindow').center().show();
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
		       fieldLabel: '司机数',
		       name: 'totalDrivers',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: '票数合计',
		       name: 'totalBills',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: '体积合计',
		       name: 'totalVolume',
		       value: '0'
		   },{
			   labelWidth: 65,
		       fieldLabel: '重量合计',
		       name: 'totalWeight',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: '接货票数合计',
		       name: 'totalWaybillQty',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: '上楼票数合计',
		       name: 'totalupstairsBillQty',
		       value: '0'
		   },{
			   labelWidth: 120,
		       fieldLabel: '单独接货票数合计',
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
		fieldLabel: '签单id',
		name: 'id',
		hidden: true
	},{
		fieldLabel: '签单编号',
		name: 'signBillNo',
		allowBlank: false,
		columnWidth:.25
	},{
		xtype:'commonowndriverselector',
		fieldLabel: '司机姓名',
		name: 'driverCode',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25
	},{
		xtype: 'datefield',
		name: 'signBillDate',
		fieldLabel: '日期',
		allowBlank: false,
		format: 'Y-m-d',
		altFormats: 'Y,m,d,|Y.m.d',
		columnWidth:.25
	},{
		fieldLabel: '接货员姓名',	
		name: 'receiverCode',
		columnWidth:.25
	},{
		xtype: 'commonowntruckselector',
		fieldLabel: '车牌号码',
		name: 'vehicleNo',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25,
		listeners: {
			change: function(field,newValue,oldValue){
			}
		}
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel:     '车型',
		name: 'vehicleTypeLength',
		allowBlank: false,
		forceSelection: true,
		columnWidth:.25
	},{
		fieldLabel: '空驶公里',
		name: 'vacancyKm',
		regex:/^-?\d+\.?\d{0,1}$/,
        regexText:"公里输入有误！",
		columnWidth:.25
	},{
		fieldLabel: '行驶公里',
		name: 'runKm',
		regex:/^-?\d+\.?\d{0,1}$/,
        regexText:"公里输入有误！",
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
		text:'保存',
		cls:'yellow_button',
		columnWidth:.1,
		handler: function(){
			var window = this.up('window');
			var baseForm = window.down('form').getForm();
			//如果签单信息验证无效,则退出
			if(!baseForm.isValid()){
				Ext.Msg.alert('保存失败','集中接货签单信息填写不正确！');
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
					Ext.Msg.alert('保存失败','请输入至少一条签单明细！');
					return;
				}
				//如果新增的校验不通过，能提示
				if(!addValidate){
					Ext.Msg.alert('保存失败','请输入新增签单明细中的用车费用与司机提成！');
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
					Ext.Msg.alert('保存失败','请输入修改签单明细中的用车费用与司机提成！');
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
				    	Ext.ux.Toast.msg('提示', '保存成功', 'ok', 1000);
				    	//保存成功后关闭窗口
				    	window.close();
				    },
				    exception: function(response){
				    	console.log('failure');
				    }
				});
			}else{
				//如果签单费用明细列表中无数据，不允许保存
				Ext.Msg.alert('保存失败','请输入至少一条签单明细！');
			}
		}
	},{
		text:'取消',
		columnWidth:.1,
		handler: function(){
			Ext.getCmp('Foss_management_FocusSignBillCostWindow_ID').close();
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
    title: '运单费用列表',  //定义表格的标题
	columns:[{
		xtype:'actioncolumn',
		width: 40,		
		text: '操作',
		items: [{
			iconCls: 'deppon_icons_delete',
			tooltip: '删除',
			handler: function(grid, rowIndex, colIndex) {
				//定义按钮为中文
				Ext.MessageBox.buttonText.yes = "是";  
				Ext.MessageBox.buttonText.no = "否"; 
				Ext.Msg.confirm('提示','是否删除选中项',function(btn,text){
					if(btn == 'yes'){
						grid.getStore().removeAt(rowIndex);
					}
				});
			}
		}]
	},{
		header: '明细id',
		dataIndex: 'id',
		hidden : true
	},{
		header:'运单号',
		dataIndex:'wayBillNo',
		allowBlank: false,
		editor: {
			xtype: 'textfield',
			ignoreNoChange : true,
			minLength : 8,
			maxLength : 9
		},
		width: 80	
	},{
		header:'客户名称',
		dataIndex:'customerName',
		width: 80	
	},{
		header:'件数',
		dataIndex:'goodsQty',
		width: 60	
	},{
		header:'包装',
		dataIndex:'goodsPackage',
		width: 80	
	},{
		xtype: 'checkcolumn',
		header:'是否<br/>现付',
		dataIndex:'isCashPayment',
		//接收所有的事件,不做处理,达到readOnly的目的
		processEvent:function(){},
		width: 40	
	},{
		header:'用车部门',
		dataIndex:'useTruckOrgCode',
		editor: {
			xtype: 'commonsaledepartmentselector',
			readOnly: true,
			fieldLabel: ''
		},
		width: 80	
	},{
		header:'重量<br/>(公斤)',
		dataIndex:'weight',
		width: 60	
	},{
		header:'体积<br/>(方)',
		dataIndex:'volume',
		width: 60	
	},{
		header:'开单金额<br/>(元)',
		dataIndex:'totalFee',
		width: 60	
	},{
		header:'用车费用<br/>(元)',
		dataIndex:'useTruckFee',
		editor: {
             xtype: 'textfield',
             regex:/^-?\d+\.?\d{0,1}$/,
             regexText:"提成格式输入有误！",
             allowBlank: false
        },
        width: 60	
	},{
		header:'司机提成<br/>(元)',
		dataIndex:'driverRoyalty',
		editor: {
            xtype: 'textfield',
            regex:/^-?\d+\.?\d{0,1}$/,
            regexText:"提成格式输入有误！",
            allowBlank: false
		},	
		width: 60	
	},{
		header:'备注',
		dataIndex:'notes',
		editor: {
            xtype: 'textfield'
		},	
		width: 120	
	},{
		xtype: 'checkcolumn',
	    header: '是否<br/>上楼',
	    dataIndex: 'isUpstairs',
	    stopSelection: false,
	    width: 40	
	},{
		xtype: 'checkcolumn',
        header: '单独<br/>接货',
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
        	edit: function(editor,e){
        		//只有当运单编辑完成时，加载运单相关数据
        		if(e.field == 'wayBillNo'){
        			//当运单为空,值复原
        			if(Ext.isEmpty(e.value)){
        				Ext.ux.Toast.msg('警告', '运单号不能为空', 'error', 1000);
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
							Ext.ux.Toast.msg('警告', '列表中已存在该单号', 'error', 1000);
							return ;
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
	        				        	e.record.data['useTruckOrgCode'] = result.vo.waybillInfo.receiveOrgCode; //收货部门
	        				        	e.record.data['weight'] = result.vo.waybillInfo.goodsWeightTotal;   // 货物总重量
	        				        	e.record.data['volume'] = result.vo.waybillInfo.goodsVolumeTotal;// 货物总体积
	        				        	e.record.data['totalFee'] = result.vo.waybillInfo.totalFee;// 总费用
	        				        	e.grid.getView().refresh();
	        				        }else{
	        				        	Ext.ux.Toast.msg('警告', '无该运单信息', 'error', 1000);
	        				        	e.record.data[e.field] = e.originalValue;
	        	        			    e.grid.getView().refresh();
	        				        }
	        				    },
	        				    failure: function(response){
	        				    	var text = response.responseText;
	        				    	Ext.ux.Toast.msg('警告', '无该运单信息', 'error', 1000);
	        				    	e.record.data[e.field] = e.originalValue;
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
		   text:'新增',
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
			   fieldLabel: '合计(票数)',
			   
		       name: 'totalBills',
		       value: '0'
		   },{
			   labelWidth: 75,
		       fieldLabel: '合计(件数)',
		       name: 'totalPieces',
		       value: '0'
		   },{
			   labelWidth: 90,
		       fieldLabel: '司机提成总额',
		       name: 'totalRoyalty',
		       value: '0'
		   },{
			   labelWidth: 100,
		       fieldLabel: '合计(上楼票数)',
		       name: 'totalUpstairs',
		       value: '0'
		   },{
			   labelWidth: 100,
		       fieldLabel: '合计(单独接货)',
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
	title:'录入集中接货签单',
	width:950,
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
