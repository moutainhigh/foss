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
 * 其他签单store
 */
Ext.define('Foss.management.SendSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.SendSignBillModel',
    pageSize:10,
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
				var useTruckOrgCode= queryParams.useTruckOrgCode;
				var driverCode=queryParams.driverCode;
				var  vehicleNo=queryParams.vehicleNo;
				var vehicleTypeLength=queryParams.vehicleTypeLength;					
				var beginSignBillDate=queryParams.beginSignBillDate;
				var endSignBillDate=queryParams.endSignBillDate;	
		
				var array={vo:{sendSignBillDto:{useTruckOrgCode:useTruckOrgCode,driverCode:driverCode,vehicleNo:vehicleNo,
					vehicleTypeLength:vehicleTypeLength,beginSignBillDate:beginSignBillDate,endSignBillDate:endSignBillDate}}};
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
						   if(sendSignBillDto.weightTotal!=null){
							   weightTotal=sendSignBillDto.weightTotal;//总重量		
						   }
						   if(sendSignBillDto.sendBillQtyTotal!=null){
							   sendBillQtyTotal=sendSignBillDto.sendBillQtyTotal;	
						   }
						   if(sendSignBillDto.weightTotal!=null){
							   weightTotal=sendSignBillDto.weightTotal;//总重量		
						   }
						   if(sendSignBillDto.volumeTotal!=null){
							   volumeTotal=sendSignBillDto.volumeTotal;//		
						   }
						   if(sendSignBillDto.inStockBillQtyTotal!=null){
							   inStockBillQtyTotal=sendSignBillDto.inStockBillQtyTotal;	
						   }
						   if(sendSignBillDto.upstairsFeeTotal!=null){
							   upstairsFeeTotal=sendSignBillDto.upstairsFeeTotal;//总重量		
						   }
						   if(sendSignBillDto.distanceTotal!=null){
							   distanceTotal=sendSignBillDto.distanceTotal;//总重量		
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
		xtype:'commonsaledepartmentselector',
		name: 'useTruckOrgCode',
		fieldLabel: '用车部门',
		columnWidth: .33
	},{
		name: 'driverCode',
		xtype:'commonowndriverselector',
		fieldLabel: '司机姓名',
		columnWidth: .33
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',			
		fieldLabel: '车牌号',
		columnWidth: .33
	},{
		xtype: 'commonvehicletypeselector',
		fieldLabel:     '车型',
		name: 'vehicleTypeLength',
		columnWidth: .33
	},{
		xtype: 'rangeDateField',
		fieldLabel:'日期',
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_querySendSignBillForm_createTime',
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
			columnWidth:.1,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					management.sendSignBillPagingBar.moveFirst();
				}					
			}
		},{
			text:'导 出',
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


//其他签单签单grid
Ext.define('Foss.management.SendSignBillQueryGrid',{	
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
								'vo.sendSignBillEntity.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteSendSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg('提示', '删除成功!', 'ok', 1000);
								 management.sendSignBillPagingBar.moveFirst();			        
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
				editSendForm.findField('sendBillQty').setValue(record.data.sendBillQty);//里程
				editSendForm.findField('upstairsFee').setValue(record.data.upstairsFee);//上楼费				
				editSendForm.findField('isSingleSend').setValue(record.data.isSingleSend);//单独配送
				editSendForm.findField('sendNo').setValue(record.data.sendNo);//派送进仓编号			
				//editSendForm.findField('receiverCode').setValue(record.data.receiverCode);//接货员
				editSendForm.findField('receiverName').setValue(record.data.receiverName);//接货员
				editSendForm.findField('haulBackBillQty').setValue(record.data.haulBackBillQty);//拉回票数
				//部门公共选择器
				var cmbUseTruckOrgCode = management.editSendSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.useTruckOrgCode}});
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
				var cmbVehicleTypeLength=management.editSendSignBillForm.getForm().findField('vehicleTypeLength');
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
		header: '车牌号',
		dataIndex: 'vehicleNo',
		flex:0.7
	},{
		header: '车型',
		dataIndex: 'vehicleTypeLength',
		flex:0.7
	},{
		header: '派送票数',
		dataIndex: 'sendBillQty',
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
		header: '进仓票数',
		dataIndex: 'inStockBillQty',
		flex:0.7
	},{
		header: '上楼票数',
		dataIndex: 'upstairsBillQty',
		flex:0.7
	},{
		header: '里程',
		dataIndex: 'distance',
		flex:0.7
	},{
		header: '拉回票数',
		dataIndex: 'haulBackBillQty',
		flex:0.7
	},{
		header: '是否单独配送',
		dataIndex: 'isSingleSend',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value=='Y'){
					return '是';
				}else{
					return '否';
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
			   fieldLabel: '司机数',	
			   labelWidth:60,
			   width:110,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: '派送票数合计',	
			   labelWidth:100,
			   width:160,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:'体积合计',	
			   labelWidth:70,	
			   width:140,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: '重量合计',	
			   labelWidth:70,	
			   width:140,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel: '进仓票数合计',	
			   labelWidth:100,	
			   width:160,
			   dataIndex: 'billQtyCount'
		  },{
			   fieldLabel: '上楼费合计',
			   labelWidth:90,
			   width:140,
			   dataIndex: 'useTruckDurationTotal'
		   },{
			   fieldLabel: '里程合计',
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
			text: '新增',
			handler: function() {
				 management.sendSignBillEditWindow = Ext.create('Foss.management.AddSendSignBillEditWindow');
				 management.sendSignBillEditWindow.show();  
				 management.addSendSignBillForm.getForm().reset();
			      management.addSendFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store
		});	
		management.sendSignBillPagingBar=me.bbar;
		management.sendSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});


Ext.define('Foss.management.addSendSignBillForm',{
	   extend: 'Ext.form.Panel',
	   title:'录入派送签单', 
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
			fieldLabel: '交接单编号',
			allowBlank:true,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'signBillNo',
			fieldLabel: '签单编号',
			allowBlank:false,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
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
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.33,
			minHeight:25
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
	  		xtype: 'datefield',
	  		name: 'signBillDate',
	  		labelWidth: 60,	
			fieldLabel: '日期',
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		allowBlank: false,
	  		invalidText: '输入的日期格式不对',  		
	  		columnWidth: .3
		},{
			xtype: 'commonowntruckselector',
			allowBlank:false,
			name:'vehicleNo',
			fieldLabel: '车牌号',
			allowBlank:false,
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33,
			listeners:{
				'select':function(text,op){			
				if (text.getValue()!=null&&text.getValue()!='') {
					
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
			allowBlank:false,
			maxLength : 5,
		    maxLengthText:'长度已超过最大限制!',
			//labelWidth: 50,	
			columnWidth:.33
		},{
			name:'sendBillQty',
			fieldLabel: '派送票数',
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'volume',
			fieldLabel: '体积(方)',
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"格式输入有误！例如1或者1.122",
			maxLength : 8,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'weight',
			fieldLabel: '重量(公斤)',
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"格式输入有误！例如1或者1.122",
			maxLength : 8,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'inStockBillQty',
			fieldLabel: '进仓票数',
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'upstairsBillQty',
			fieldLabel: '上楼票数',
			allowBlank:true,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'distance',
			fieldLabel: '里程(km)',
			allowBlank: false,
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"里程格式输入有误！例如1或者1.12",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			columnWidth:.33
		},{
			name:  'upstairsFee',
			fieldLabel: '上楼费',
			allowBlank:true,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			xtype: 'radiogroup',
	        fieldLabel: '单独配送',  
	        labelWidth:100,
	        allowBlank: false,
	        vertical: true, 	
	        columnWidth:.33,
	        items: [
	            { boxLabel: '是', name: 'isSingleSend', inputValue: 'Y',checked:true},
	            { boxLabel: '否', name: 'isSingleSend', inputValue: 'N'}				           
	        ]
		},{
			name:  'sendNo',
			fieldLabel: '派送进仓编号',
			allowBlank:false,
			labelWidth: 90,	
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			xtype: 'commonemployeeselector',			
			name:  'receiverCode',
			fieldLabel: '接货员',
			allowBlank:true,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
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
			name:  'haulBackBillQty',
			fieldLabel: '拉回票数',
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		}]
		
});


Ext.define('Foss.management.addSendFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:760,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 5',
			anchor: '90%'					
		},
		items:[{
			labelAlign: 'top',
			fieldLabel: '非进仓票数费用',
			name: 'noInStockBillFee',
			allowBlank:false,
			regex:/^-?\d+\.?\d{0,2}$/,
			labelWidth:50,
			columnWidth:.17				
		},{
			labelAlign: 'top',
			name: 'inStockBillFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			allowBlank:false,
			fieldLabel: '进仓票数费用',							
			labelWidth:60,
			columnWidth:.15	
		},{
			labelAlign: 'top',
			name: 'driverRoyaltyAmount',
			regex:/^-?\d+\.?\d{0,2}$/,
			allowBlank:false,
			fieldLabel: '司机实际提成总额	',							
			labelWidth:60,
			columnWidth:.18	
		},{
			labelAlign: 'top',
			name: 'useTruckFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			allowBlank:false,
			fieldLabel: '用车划分总费用',							
			labelWidth:60,
			columnWidth:.17
		},{
			labelAlign: 'top',
			name: 'otherFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			allowBlank:false,
			fieldLabel: '其它费用',							
			labelWidth:60,
			columnWidth:.15	
		},{
			labelAlign: 'top',
			name: 'upstairsFeeRoyalty',
			allowBlank:false,
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '上楼费提成',							
			labelWidth:60,
			columnWidth:.15	
		}]
});



Ext.define('Foss.management.editSendSignBillForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',	 
	   title:'编辑派送签单',
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
			fieldLabel: '交接单编号',
			allowBlank:true,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'signBillNo',
			fieldLabel: '签单编号',
			allowBlank:false,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
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
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.33,
			minHeight:25
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
	  		xtype: 'datefield',
	  		name: 'signBillDate',
	  		labelWidth: 60,	
			fieldLabel: '日期',
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		allowBlank: false,
	  		invalidText: '输入的日期格式不对',  		
	  		columnWidth: .3
		},{
			xtype: 'commonowntruckselector',
			allowBlank:false,
			name:'vehicleNo',
			fieldLabel: '车牌号码',
			allowBlank:false,
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
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
			allowBlank:false,
			maxLength : 5,
		    maxLengthText:'长度已超过最大限制!',			
			columnWidth:.33
		},{
			name:'sendBillQty',
			fieldLabel: '派送票数',
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'volume',
			fieldLabel: '体积(方)',
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"格式输入有误！例如1或者1.122",
			maxLength : 8,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'weight',
			fieldLabel: '重量(公斤)',
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"格式输入有误！例如1或者1.122",
			maxLength : 8,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'inStockBillQty',
			fieldLabel: '进仓票数',
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			allowBlank:true,
			columnWidth:.33
		},{
			name:'upstairsBillQty',
			fieldLabel: '上楼票数',
			allowBlank:true,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			name:'distance',
			fieldLabel: '里程(km)',
			allowBlank: false,
			regex:/^-?\d+\.?\d{0,2}$/,
			regexText:"里程格式输入有误！例如1或者1.12",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
		    value:'0.00',
			columnWidth:.33
		},{
			name:  'upstairsFee',
			fieldLabel: '上楼费',
			allowBlank:true,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			xtype: 'radiogroup',
	        fieldLabel: '单独配送',  
	        labelWidth:100,
	        allowBlank: false,
	        vertical: true, 	
	        columnWidth:.33,
	        items: [
	            { boxLabel: '是', name: 'isSingleSend', inputValue: 'Y',checked:true},
	            { boxLabel: '否', name: 'isSingleSend', inputValue: 'N'}				           
	        ]
		},{
			name:  'sendNo',
			fieldLabel: '派送进仓编号',
			allowBlank:false,
			maxLength : 10,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		},{
			xtype: 'commonemployeeselector',			
			name:  'receiverCode',
			fieldLabel: '接货员',
			allowBlank:true,
			maxLength : 20,
		    maxLengthText:'长度已超过最大限制!',
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
			name:  'haulBackBillQty',
			fieldLabel: '拉回票数',
			allowBlank:false,
			regex: /^\d{0,30}$/,	
			regexText:"格式输入有误！请输入整数",
			maxLength : 9,
		    maxLengthText:'长度已超过最大限制!',
			columnWidth:.33
		}]
});


Ext.define('Foss.management.editSendFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:760,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'5 0 0 5',
			anchor: '90%'					
		},
		items:[{
			labelAlign: 'top',
			fieldLabel: '非进仓票数费用',
			name: 'noInStockBillFee',	
			regex:/^-?\d+\.?\d{0,2}$/,
			labelWidth:50,
			columnWidth:.17				
		},{
			labelAlign: 'top',
			name: 'inStockBillFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '进仓票数费用',							
			labelWidth:60,
			columnWidth:.15	
		},{
			labelAlign: 'top',
			name: 'driverRoyaltyAmount',
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '司机实际提成总额	',							
			labelWidth:60,
			columnWidth:.18	
		},{
			labelAlign: 'top',
			name: 'useTruckFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '用车划分总费用',							
			labelWidth:60,
			columnWidth:.17
		},{
			labelAlign: 'top',
			name: 'otherFee',
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '其它费用',							
			labelWidth:60,
			columnWidth:.15	
		},{
			labelAlign: 'top',
			name: 'upstairsFeeRoyalty',
			regex:/^-?\d+\.?\d{0,2}$/,
			fieldLabel: '上楼费提成',							
			labelWidth:60,
			columnWidth:.15	
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
	      text: '修改',	      
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
	      			Ext.ux.Toast.msg('提示', '请输入接货员!', 'ok', 1000); 
	      		  }
	      		  
	      	  }
		     if(isBol){
		    		Ext.MessageBox.confirm("提示","数据已修改，确认提交吗", function(button,text){   
						if(button=='yes'){					
							if(form.isValid()){							
								var params={vo:{sendSignBillEntity:vals}};
							     Ext.Ajax.request({
						       	  url: management.realPath('updateSendSignBill.action'),
						       	  jsonData: params,
						       	  success: function(response, opts) { 
						       	   form.reset();						    	
							       Ext.ux.Toast.msg('提示', '修改成功!', 'ok', 1000);
							       management.sendSignBillPagingBar.moveFirst();
							       management.editSendSignBillWindow.close();
						       	  },
						       	  failure: function(response, opts) {
							       Ext.Msg.alert('failure','修改失败！');
						       	  }
						         });
					    	}
						 }
						}); 
		     }
	      

	      }
	  },{
	      text: '取消',	     
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
	      text: '保存',	
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
	      			Ext.ux.Toast.msg('提示', '请输入接货员!', 'ok', 1000); 
	      		  }
	      		  
	      	  }
		     if(isBol){
	      		Ext.MessageBox.confirm("提示","数据已添加，确认提交吗", function(button,text){   
					if(button=='yes'){					
						if(form.isValid()){							
							var params={vo:{sendSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('addSendSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
					       	   form.reset();						    	
						       Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);
						       management.sendSignBillPagingBar.moveFirst();
						       management.sendSignBillEditWindow.close();
					       	  },
					       	  failure: function(response, opts) {
						       Ext.Msg.alert('failure','保存失败！');
					       	  }
					         });
				    	}
					 }
					});
	      	  }
	      }
	  },{
	      text: '取消',	     
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



//定义弹出的编辑其他签单窗口 
Ext.define('Foss.management.AddSendSignBillEditWindow',{
	extend: 'Ext.window.Window',	
	width:800,	
	closable:true,
	closeAction:'hide',
	modal: true,
	addSendSignBillPanelForm : null,
	getAddSendSignBillPanelForm: function(){
		if(this.addSendSignBillPanelForm==null){
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
		if(this.editSendSignBillPanelForm==null){
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