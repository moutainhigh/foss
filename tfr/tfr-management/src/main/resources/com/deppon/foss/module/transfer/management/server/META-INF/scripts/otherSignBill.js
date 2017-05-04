
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
    pageSize:10,
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
							top.Ext.MessageBox.alert("失败",json.message);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert("失败",json.message);
					}
				});
			}
	
			
		
		},			
		load: function(store,records,successful,epots){
		 	if(store.getCount() == 0){
				 Ext.ux.Toast.msg('提示', '没有查询到数据!', 'ok', 1000);
			}
		}
				
				
		
	}
});

//其他签单查询条件
Ext.define('Foss.management.OtherSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame: false,
	border: false,	
	title : '查询条件',
	layout:'column',
	defaults:{
		xtype: 'textfield',
		labelWidth: 85,		
		margin: '5 10 5 10'
	},
	items:[{
		xtype:'commonsaledepartmentselector',
		name: 'useTruckOrgCode',
		fieldLabel: '用车部门',
		columnWidth: .3
	},{
		xtype:'commonowndriverselector',
		name: 'driverCode',		
		fieldLabel: '司机姓名',
		columnWidth: .3
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',
		fieldLabel: '车牌号码',
		columnWidth: .3
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',
		fieldLabel: '车型',
		columnWidth: .3
	},{
		xtype: 'rangeDateField',
		fieldLabel:'日期',
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_queryOtherSignBillForm_createTime',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		dateRange : 31,
		disallowBlank:true,
		fromName: 'beginSignBillDate',
		toName: 'endSignBillDate',
		format:'Y-m-d H:i:s',
		fromValue:Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',
		allowBlank: false,
		columnWidth: .40
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
				//重新初始化交接时间
				this.up('form').getForm().findField('beginSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endSignBillDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));

			}
		},{
			xtype: 'container',
			columnWidth:.680,
			html: '&nbsp;'
		},{
			text:'查询',
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.OtherSignBillGrid.store.load();
				}
				
			}
		},{
			text:'导 出',
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
	title:'其他签单列表',
	layout:'column',
	frame:true,
	defaults:{
		sortable: true,
		flex: 1
	},
	columns: [{
		xtype:'actioncolumn',
		width:60,
		text: '操作',
		items: [{
			iconCls: 'deppon_icons_delete',
			tooltip: '删除',
			//删除事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Msg.confirm('提示','是否删除选中项',function(btn,text){
					//询问是否删除，是则发送请求
					if(btn == 'yes'){
						Ext.Ajax.request({
							params: {
								'vo.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteOtherSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg('提示', '删除成功!', 'ok', 1000);
								 management.OtherSignBillGrid.store.load();				        
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert('failure','删除失败!' + result);
							},
							exception : function(response) {
	        					var json = Ext.decode(response.responseText);
	        					Ext.Msg.alert('exception',json.message);
	        				}
						});
					}
				});
			}
		},{
			iconCls: 'deppon_icons_edit',
			tooltip: '编辑',
			//编辑事件
			handler: function(grid, rowIndex, colIndex){
				management.editOtherSignBillWindow = Ext.create('Foss.management.editOtherSignBillWindow');			 
				management.editOtherSignBillWindow.show();  
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
				
				
				var cmbUseTruckOrgCode = editOtherForm.findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.useTruckOrgCode);
				
				//司机选择器
				var cmbDriverCode = editOtherForm.findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.driverCode}});
				cmbDriverCode.setValue(record.driverCode);
				//车牌号				
				var cmbVehicleNo = editOtherForm.findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.vehicleNo}});
				cmbVehicleNo.setValue(record.vehicleNo);
				
				//车型选择器
				var cmbVehicleTypeLength=editOtherForm.findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.vehicleTypeLength);
				
				management.editOtherFeeInfoForm.getForm().reset();
				management.editOtherFeeInfoForm.getForm().loadRecord(record);	
			}
		}]
	},{
		header: '签单编号',
		dataIndex: 'signBillNo',
		flex:0.7
	},{
		header: '用车部门',
		dataIndex: 'useTruckOrgName',
		flex:0.7
	},{
		header: '司机姓名',
		dataIndex: 'driverName',
		flex:0.7
	},{
		header: '日期',
		dataIndex: 'signBillDate',
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
		header: '签单类型',
		dataIndex: 'signBillType',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value=='INTERIOR_SIGN_BILL'){
					return '内部签单';
				}else if(value=='SPECIAL_SIGN_BILL'){
					return '专车签单';
				} else{
					return '其他签单';
				}				
			}
		},
		flex:0.7
	},{
		header: ' 目的地',
		dataIndex: 'arrvRegionName',
		flex:0.7
	},{
		header: '线路里程',
		dataIndex: 'lineDistance',
		flex:0.7
	},{
		header: '票数',
		dataIndex: 'billQty',
		flex:0.7
	},{
		header: '体积',
		dataIndex: 'volume',
		flex:0.6
	},{
		header: '重量',
		dataIndex: 'weight',
		flex:0.6
	},{
		header: '用车时间',
		dataIndex: 'useTruckDuration',
		flex:0.9
	},{
		header: '其它金额',
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
			   fieldLabel: '司机数',	
			   labelWidth:60,
			   width:110,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: '线路里程合计',	
			   labelWidth:100,
			   width:160,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:'体积合计',	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: '重量合计',	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel: '票数合计',	
			   labelWidth:70,	
			   width:130,
			   dataIndex: 'billQtyCount'
		  },{
			   fieldLabel: '用车时间合计',
			   labelWidth:100,
			   width:150,
			   dataIndex: 'useTruckDurationTotal'
		   },{
			   fieldLabel: '其它金额合计',
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
			text: '新增',
			handler: function() {
			   management.AddOtherSignBillWindow = Ext.create('Foss.management.AddOtherSignBillWindow');			 
			   management.AddOtherSignBillWindow.center().show();  
			   management.addOtherSignBillForm.getForm().reset();
			   management.addOtherFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store
		});	
		management.OtherSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});

//新增界面其他签单
Ext.define('Foss.management.addOtherSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title:'录入其他签单签单',
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			margin:'10 10 5 15'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: '签单编号',
		allowBlank: false,	
		maxLength:50,
		columnWidth:.333
	},{
		xtype: 'combo',
		fieldLabel: '用车类型',
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
		emptyText : '全部',		
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('USE_TRUCK_TYPE')		
	},{
		xtype: 'combo',
		name: 'signBillType',							
		fieldLabel: '签单类型',
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
		emptyText : '全部',		
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('SIGN_BILL_TYPE')		
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: '司机姓名',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
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
		fieldLabel: '目的地',
		allowBlank: false,
		maxLength : 100,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33		
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: '日期',
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: '输入的日期格式不对',  		
  		columnWidth: .34
	},{
		xtype:'commonsaledepartmentselector',
		name:'useTruckOrgCode',
		fieldLabel: '用车部门',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
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
		fieldLabel: '车牌号',
		allowBlank: false,
		labelWidth: 60,	
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
			       Ext.Msg.alert('failure','保存失败！');
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
		fieldLabel: '车型',
		allowBlank: false,
		labelWidth: 50,	
		columnWidth:.33
	},{
		name: 'volume',							
		fieldLabel: '体积',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"格式输入有误！例如1或者1.122",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'weight',							
		fieldLabel: '重量',
		allowBlank: false,	
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 8,
		value:'0.00',
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'billQty',							
		fieldLabel: '票数',
		allowBlank: false,
		regex: /^\d{0,30}$/,		
		regexText:"票数格式输入有误！",		
		maxLength : 9,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'lineDistance',							
		fieldLabel: '线路里程',		
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"线路里程格式输入有误！例如1或者1.12",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'useTruckDuration',							
		fieldLabel: '用车时间',
		labelWidth:80,
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,1}$/,
		regexText:"用车时间格式输入有误！例如1或者1.1",
		maxLength : 5,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.0',
		columnWidth:.33
	},{
		name: 'otherFee',							
		fieldLabel: '其它金额',
		allowBlank: false,
		regex: /^\d{0,30}$/,	
		regexText:"其它金额格式输入有误！为整数",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth:80,
		columnWidth:.33
	},{
		name: 'notes',							
		fieldLabel: '备注',	
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth:80,
		columnWidth:.33
	}]
});
//其他签单费用信息form
Ext.define('Foss.management.addOtherFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:670,	  
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 10',
			anchor: '90%'					
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: '司机提成',				
				name: 'driverRoyalty',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"司机提成格式输入有误！为整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.33					
			},{
				labelAlign: 'top',
				name: 'useTruckFee',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"司机提成格式输入有误！为整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				fieldLabel: '用车费用',							
				labelWidth:60,
				columnWidth:.33	
			}]
});
//编辑界面其他签单
Ext.define('Foss.management.editOtherSignBillForm',{
	 extend: 'Ext.form.Panel',
	 layout: 'column',
	 frame: false,
	 title:'编辑其他签单签单',
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
		fieldLabel: '签单编号',
		allowBlank: false,	
		maxLength:50,
		columnWidth:.33
	},{
		xtype: 'combo',
		fieldLabel: '用车类型',
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
		emptyText : '全部',		
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('USE_TRUCK_TYPE')		
	},{
		xtype: 'combo',
		name: 'signBillType',							
		fieldLabel: '签单类型',
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
		emptyText : '全部',		
		editable:false,	
		store:FossDataDictionary.getDataDictionaryStore('SIGN_BILL_TYPE')		
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: '司机姓名',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
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
		fieldLabel: '目的地',
		allowBlank: false,
		maxLength : 100,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33		
	},{
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: '日期',
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: '输入的日期格式不对',  		
  		columnWidth: .33
	},{
		xtype:'commonsaledepartmentselector',
		name:'useTruckOrgCode',
		fieldLabel: '用车部门',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
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
		fieldLabel: '车牌号',
		allowBlank: false,
		labelWidth: 60,	
		columnWidth:.33		
	},{
		xtype: 'commonvehicletypeselector',
		name: 'vehicleTypeLength',							
		fieldLabel: '车型',
		allowBlank: false,
		labelWidth: 50,	
		columnWidth:.33
	},{
		name: 'volume',							
		fieldLabel: '体积',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"格式输入有误！例如1或者1.122",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'weight',							
		fieldLabel: '重量',
		allowBlank: false,	
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 8,
		value:'0.00',
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'billQty',							
		fieldLabel: '票数',
		allowBlank: false,
		regex: /^\d{0,30}$/,		
		regexText:"票数格式输入有误！",		
		maxLength : 9,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'lineDistance',							
		fieldLabel: '线路里程',		
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"线路里程格式输入有误！例如1或者1.12",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.33
	},{
		name: 'useTruckDuration',							
		fieldLabel: '用车时间',
		labelWidth:80,
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,1}$/,
		regexText:"用车时间格式输入有误！例如1或者1.1",
		maxLength : 5,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.0',
		columnWidth:.33
	},{
		name: 'otherFee',							
		fieldLabel: '其它金额',
		allowBlank: false,
		regex: /^\d{0,30}$/,	
		regexText:"其它金额格式输入有误！为整数",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth:80,
		columnWidth:.33
	},{
		name: 'notes',							
		fieldLabel: '备注',			
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth:80,
		columnWidth:.33
	}]
});
//编辑其他签单费用信息form
Ext.define('Foss.management.editOtherFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:670,	   
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 10',
			anchor: '90%'					
		},
		items:[{
			labelAlign: 'top',
			fieldLabel: '司机提成',				
			name: 'driverRoyalty',
			allowBlank: false,
			regex: /^\d{0,30}$/,	
			regexText:"司机提成格式输入有误！为整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			labelWidth:50,
			columnWidth:.33					
		},{
			labelAlign: 'top',
			name: 'useTruckFee',
			allowBlank: false,
			regex: /^\d{0,30}$/,	
			regexText:"司机提成格式输入有误！为整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			fieldLabel: '用车费用',							
			labelWidth:60,
			columnWidth:.33	
		}]
});

//新增界面
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
	      text: '保存',
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
		     Ext.MessageBox.confirm("提示","已保存，确认提交吗", function(button,text){   
					if(button=='yes'){					
						if(form.isValid()){							
							var params={vo:{otherSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('addOtherSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
						    	form.reset();						    	
						       Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);						     
						       management.AddOtherSignBillWindow.center().close(); 
						       management.OtherSignBillGrid.store.load();
					       	  },
					       	  failure: function(response, opts) {
						       Ext.Msg.alert('failure','保存失败！');
					       	  }
					         });
				    	}
					 }
					});

	      }
	  },{
	      text: '取消',	     
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
	      text: '修改',
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
		     Ext.MessageBox.confirm("提示","以修改，确认提交吗", function(button,text){   
					if(button=='yes'){					
						if(form.isValid()){							
							var params={vo:{otherSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('updateOtherSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
					       	   form.reset();						    	
						       Ext.ux.Toast.msg('提示', '修改成功!', 'ok', 1000);
						       management.OtherSignBillGrid.store.load();
						       management.editOtherSignBillWindow.center().close(); 
					       	  },
					       	  failure: function(response, opts) {
						       Ext.Msg.alert('failure','修改失败！');
					       	  }
					         });
				    	}
					 }
					});

	      }
	  },{
	      text: '取消',	     
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


//定义弹出的新增窗口
Ext.define('Foss.management.AddOtherSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:700,		
	closable:true,
	closeAction:'hide',
	modal: true,
	addOtherSignBillForm : null,
	getAddOtherSignBillForm: function(){
		if(this.addOtherSignBillForm==null){
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