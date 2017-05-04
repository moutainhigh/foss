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
							return Ext.Date.format(date,'Y-m-d');
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
			    {name:'transferDistance'},//转货里程
			    {name:'volume'},//体积
			    {name:'weight'},//重量
			    {name:'useTruckDuration'},//用车时间
			    {name:'transferRoyalty'},//转货提成
			    {name:'useTruckFee'}//用车费用划分
	]
});
/**
 * 转货车store
 */
Ext.define('Foss.management.TransferSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.TransferSignBillModel',
    pageSize:10,
    proxy: {
      	type: 'ajax',
        url: management.realPath('queryTransferSignBill.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.transferSignBillList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        }
    },
	listeners: {	
				datachanged: function(store, operation, epots){
				var queryForm = management.transferSignBillQueryForm;
				if (queryForm != null) {					
					var queryParams = queryForm.getValues();		
					var useTruckOrgCode= queryParams.useTruckOrgCode;
					var driverCode=queryParams.driverCode;
					var  vehicleNo=queryParams.vehicleNo;
					var vehicleTypeLength=queryParams.vehicleTypeLength;					
					var beginSignBillDate=queryParams.beginSignBillDate;
					var endSignBillDate=queryParams.endSignBillDate;	
			
					var array={vo:{transferSignBillDto:{useTruckOrgCode:useTruckOrgCode,driverCode:driverCode,vehicleNo:vehicleNo,vehicleTypeLength:vehicleTypeLength,beginSignBillDate:beginSignBillDate,endSignBillDate:endSignBillDate}}};
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
							   var  transferDistanceTotal=0;//总的转货里程
							   var  volumeTotal=0;//总体积
							   var  useTruckDurationTotal=0;//总的用车时间
								   
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
			beforeload : function(store, operation, eOpts) {				
				var queryForm = management.transferSignBillQueryForm;
				if (queryForm != null) {					
					var queryParams = queryForm.getValues();
					Ext.apply(operation, {
						params : {
							'vo.transferSignBillDto.useTruckOrgCode' : queryParams.useTruckOrgCode,  //用车部门								
							'vo.transferSignBillDto.driverCode' : queryParams.driverCode,  //司机姓名	
							'vo.transferSignBillDto.vehicleNo' : queryParams.vehicleNo,  //车牌号
							'vo.transferSignBillDto.vehicleTypeLength' : queryParams.vehicleTypeLength,  //车型
							'vo.transferSignBillDto.beginSignBillDate' : queryParams.beginSignBillDate,  //开始时间	
							'vo.transferSignBillDto.endSignBillDate' :queryParams.endSignBillDate //结束时间
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






//转货车查询条件
Ext.define('Foss.management.TransferSignBillQueryForm',{
	extend:'Ext.form.Panel',
	frame: true,
	border: false,	
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
		fieldId: 'Foss_management_queryTransfeiSignBillForm_createTime',
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
					management.TransferSignBillGrid.store.load();
				}
				
			}
		},{
			text:'导 出',
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
	title:'转货车签单列表',
	layout:'column',
	frame:true,
	border:true,
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
							url: management.realPath('deleteTransferSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg('提示', '删除成功!', 'ok', 1000);
								 management.TransferSignBillGrid.store.load();				        
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
				management.editTransferSignBillWindow = Ext.create('Foss.management.editTransferSignBillWindow');
				var record = grid.getStore().getAt(rowIndex);
				management.editTransferSignBillForm.getForm().reset();
				//management.editTransferSignBillForm.getForm().loadRecord(record);
				var editForm= management.editTransferSignBillForm.getForm();
				editForm.findField('id').setValue(record.data.id);//id
				editForm.findField('signBillNo').setValue(record.data.signBillNo);//签单编号	
				editForm.findField('useTruckOrgName').setValue(record.data.useTruckOrgName);//用车部门名称				
				editForm.findField('driverName').setValue(record.data.driverName);//司机姓名
				editForm.findField('signBillDate').setValue(new Date(record.data.signBillDate));//日期
				editForm.findField('isFirstTransfer').setValue(record.data.isFirstTransfer);//是否第一个部门				
				editForm.findField('transferDistance').setValue(record.data.transferDistance);//转货里程
				editForm.findField('volume').setValue(record.data.volume);//体积
				editForm.findField('weight').setValue(record.data.weight);//'重量						
				editForm.findField('useTruckDuration').setValue(record.data.useTruckDuration);//用车时间
				//部门公共选择器
				var cmbUseTruckOrgCode = management.editTransferSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);
				
				//司机选择器
				var cmbDriverCode = management.editTransferSignBillForm.getForm().findField('driverCode');
				cmbDriverCode.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driverCode}});
				cmbDriverCode.setValue(record.data.driverCode);
				//车牌号选择器			
				var cmbVehicleNo = management.editTransferSignBillForm.getForm().findField('vehicleNo');
				cmbVehicleNo.getStore().load({params:{'truckVo.truck.vehicleNo' : record.data.vehicleNo}});
				cmbVehicleNo.setValue(record.data.vehicleNo);
				//车型选择器
				var cmbVehicleTypeLength=management.editTransferSignBillForm.getForm().findField('vehicleTypeLength');
				cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : record.data.vehicleTypeLength}});		
				cmbVehicleTypeLength.setValue(record.data.vehicleTypeLength);
								
				management.editFeeInfoForm.getForm().reset();
				management.editFeeInfoForm.getForm().loadRecord(record);
				management.editTransferSignBillWindow.show();

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
		header: '转货里程',
		dataIndex: 'transferDistance',
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
		header: '是否第一个部门转货',
		dataIndex: 'isFirstTransfer',
		renderer:function(value, metadata, record){
			if(!Ext.isEmpty(value)){
				if(value=='Y'){
					return '是';
				}else{
					return '否';
				}
			}
		},
		flex:0.9
	},{
		header: '转货提成',
		dataIndex: 'transferRoyalty',
		flex:0.9
	},{
		header: '用车费用划分',
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
			   fieldLabel: '司机数',	
			   labelWidth:60,
			   width:110,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: '转货里程合计',	
			   labelWidth:110,
			   width:170,
			   dataIndex: 'billingWeightTotal'
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
			   fieldLabel: '用车时间合计',
			   labelWidth:100,
			   width:160,
			   dataIndex: 'useTruckDurationTotal'
		   }]
		}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.TransferSignBillStore');
		me.tbar=[{
			xtype: 'button', 
			text: '新增',
			handler: function() {
				   management.AddTransferSignBillWindow = Ext.create('Foss.management.AddTransferSignBillWindow');			 
				   management.AddTransferSignBillWindow.center().show();  
				   management.addTransferSignBillForm.getForm().reset();
				   management.addFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store: me.store			
		});
		management.TransferSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});

//新增界面
Ext.define('Foss.management.addTransferSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: false,
	   title:'录入转货车签单',
	   width:670,
	   layout:'column',	
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			margin:'10 5 5 10'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: '签单编号',
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText:'长度已超过最大限制!',
		columnWidth:.3
	},{
		xtype:'commonsaledepartmentselector',
		name:'useTruckOrgCode',
		fieldLabel: '用车部门',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
	    blankText: '部门不能为空!',
		columnWidth:.3,
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
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: '日期',
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: '输入的日期格式不对',  		
  		columnWidth: .4
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: '司机姓名',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
		columnWidth:.3,
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
		xtype: 'radiogroup',
        fieldLabel: '第一个转货部门',  
        labelWidth:100,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.4,
        items: [
            { boxLabel: '是', name: 'isFirstTransfer', inputValue: 'Y',checked:true},
            { boxLabel: '否', name: 'isFirstTransfer', inputValue: 'N'}				           
        ]
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: '车牌号',
		allowBlank: false,
		labelWidth: 60,	
		columnWidth:.3,
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
		columnWidth:.3
	},{
		name: 'transferDistance',							
		fieldLabel: '转货里程',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"转货里程格式输入有误！例如1或者1.12",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.3
	},{
		name: 'volume',							
		fieldLabel: '体积(方)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,3}$/,
		regexText:"体积格式输入有误！例如1或者1.122",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.4
	},{
		name: 'weight',							
		fieldLabel: '重量(公斤)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,3}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 80,	
		columnWidth:.3
	},{
		name: 'useTruckDuration',							
		fieldLabel: '用车时间(小时)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,1}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 5,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.0',
		labelWidth:100,
		columnWidth:.4
	}]
});

//新增界面转货车
Ext.define('Foss.management.editTransferSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title:'编辑转货车签单',
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
		fieldLabel: '签单编号',
		allowBlank: false,		
		maxLength : 50,
	    maxLengthText:'长度已超过最大限制!',
		columnWidth:.3
	},{
		xtype:'commonsaledepartmentselector',
		name:'useTruckOrgCode',
		fieldLabel: '用车部门',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
	    blankText: '部门不能为空!',
		columnWidth:.3,
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
  		xtype: 'datefield',
  		name: 'signBillDate',
  		labelWidth: 60,	
		fieldLabel: '日期',
  		altFormats: 'Y,m,d|Y.m.d',
  		format: 'Y-m-d',
  		allowBlank: false,
  		invalidText: '输入的日期格式不对',  		
  		columnWidth: .4
	},{
		xtype:'commonowndriverselector',
		name:'driverCode',
		fieldLabel: '司机姓名',
		allowBlank:false,
		maxLength : 20,
	    maxLengthText:'长度已超过最大限制!',
		columnWidth:.3,
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
		xtype: 'radiogroup',
        fieldLabel: '第一个转货部门',  
        labelWidth:100,
        allowBlank: false,
        vertical: true, 	
        columnWidth:.4,
        items: [
            { boxLabel: '是', name: 'isFirstTransfer', inputValue: 'Y',checked:true},
            { boxLabel: '否', name: 'isFirstTransfer', inputValue: 'N'}				           
        ]
	},{
		xtype: 'commonowntruckselector',
		name: 'vehicleNo',							
		fieldLabel: '车牌号',
		allowBlank: false,
		labelWidth: 60,	
		columnWidth:.3,
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
					var cmbVehicleTypeLength=management.editTransferSignBillForm.getForm().findField('vehicleTypeLength');
					cmbVehicleTypeLength.getStore().load({params:{'vehicleTypeEntity.vehicleLengthCode' : result.vo.transferSignBillEntity.vehicleTypeLength}});		
					cmbVehicleTypeLength.setValue(result.vo.transferSignBillEntity.vehicleTypeLength);
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
		columnWidth:.3
	},{
		name: 'transferDistance',							
		fieldLabel: '转货里程',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,2}$/,
		regexText:"转货里程格式输入有误！例如1或者1.12",
		maxLength : 10,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.3
	},{
		name: 'volume',							
		fieldLabel: '体积(方)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,3}$/,
		regexText:"体积格式输入有误！例如1或者1.122",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 60,	
		columnWidth:.4
	},{
		name: 'weight',							
		fieldLabel: '重量(公斤)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,3}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 8,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.00',
		labelWidth: 80,	
		columnWidth:.3
	},{
		name: 'useTruckDuration',							
		fieldLabel: '用车时间(小时)',
		allowBlank: false,
		regex:/^-?\d+\.?\d{0,1}$/,
		regexText:"格式输入有误！例如1或者1.12",
		maxLength : 5,
	    maxLengthText:'长度已超过最大限制!',
	    value:'0.0',
		labelWidth:100,
		columnWidth:.4
	}]
});

//转货车费用信息form
Ext.define('Foss.management.addFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10',
			anchor: '90%'					
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: '转货提成',
				name: 'transferRoyalty',	
				allowBlank: false,
				regex: /^\d{0,30}$/,
				regexText:"格式输入有误！",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.3					
			},{
				labelAlign: 'top',
				name: 'useTruckFee',							
				fieldLabel: '用车费用划分',
				allowBlank: false,
				regex: /^\d{0,30}$/,
				regexText:"格式输入有误！",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:60,
				columnWidth:.3	
			}]
});
//新增界面
Ext.define('Foss.management.addTransferSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.addTransferSignBillForm= Ext.create('Foss.management.addTransferSignBillForm'),
	       management.addFeeInfoForm= Ext.create('Foss.management.addFeeInfoForm')   
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
							var params={vo:{transferSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('addTransferSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
						    	form.reset();						    	
						       Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);						     
						       management.AddTransferSignBillWindow.center().close(); 
						       management.TransferSignBillGrid.store.load();
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
	   title:'费用信息',	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10',
			anchor: '90%'					
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: '转货提成',
				name: 'transferRoyalty',	
				allowBlank: false,
				regex: /^\d{0,30}$/,
				regexText:"格式输入有误！",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.3					
			},{
				labelAlign: 'top',
				name: 'useTruckFee',							
				fieldLabel: '用车费用划分',	
				allowBlank: false,
				regex: /^\d{0,30}$/,
				regexText:"格式输入有误！",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:60,
				columnWidth:.3	
			}]
});
//编辑界面
Ext.define('Foss.management.editTransferSignBillPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',
	buttonAlign :'center',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.editTransferSignBillForm= Ext.create('Foss.management.editTransferSignBillForm'),
	       management.editFeeInfoForm= Ext.create('Foss.management.editFeeInfoForm')   
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
							var params={vo:{transferSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('updateTransferSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
					       	   form.reset();						    	
						       Ext.ux.Toast.msg('提示', '修改成功!', 'ok', 1000);
						       management.TransferSignBillGrid.store.load();
						       management.editTransferSignBillWindow.center().close(); 
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
	width:700,	
	closable:true,
	closeAction:'hide',
	modal: true,
	editTransferSignBillForm : null,
	getEditTransferSignBillForm: function(){
		if(this.editTransferSignBillForm==null){
			this.editTransferSignBillForm = Ext.create('Foss.management.editTransferSignBillPanel');
		}
		return this.editTransferSignBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditTransferSignBillForm()
				];
		me.callParent([cfg]);
	}
});


//定义弹出的新增窗口
Ext.define('Foss.management.AddTransferSignBillWindow',{
	extend: 'Ext.window.Window',	
	width:700,	
	closable:true,
	closeAction:'hide',
	modal: true,
	addTransferSignBillForm : null,
	getAddTransferSignBillForm: function(){
		if(this.addTransferSignBillForm==null){
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