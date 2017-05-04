Ext.define('Foss.management.RegularTruckSignBillModel', {
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
		    {name:'lineDistance'},// 线路里程
			{name:'arrvRegionName', type:'string'},// 目的地
		    {name:'volume'},//体积
			{name:'weight'},//重量
			 {name:'lineCode'},// 线路
			 {name:'lineName'},// 线路名称
			 {name:'msldRoyalty'},// 对发单程线路里程提成
			 {name:'emsldRoyalty'},// 空车对发单程里程提成
			 {name:'driverRoyaltyAmount'}// 司机提成总额
			
	]
});
/**
 * 其他签单store
 */
Ext.define('Foss.management.RegularTruckSignBillStore', {
	extend: 'Ext.data.Store',
    model: 'Foss.management.RegularTruckSignBillModel',
    pageSize:10,
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
				var driverCode=queryParams.driverCode;
				var  vehicleNo=queryParams.vehicleNo;
				var vehicleTypeLength=queryParams.vehicleTypeLength;					
				var beginSignBillDate=queryParams.beginSignBillDate;
				var endSignBillDate=queryParams.endSignBillDate;	
		
				var array={vo:{regularTruckSignBillDto:{useTruckOrgCode:useTruckOrgCode,driverCode:driverCode,vehicleNo:vehicleNo,vehicleTypeLength:vehicleTypeLength,beginSignBillDate:beginSignBillDate,endSignBillDate:endSignBillDate}}};
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


//专线对发签单grid
Ext.define('Foss.management.RegularTruckQueryGrid',{	
	extend:'Ext.grid.Panel',
	title:'专线对发签单列表',
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
								'vo.regularTruckSignBillEntity.id': grid.getStore().getAt(rowIndex).get('id')
							},
							url: management.realPath('deleteRegularTruckSignBill.action'),
							success:function(response){
								 Ext.ux.Toast.msg('提示', '删除成功!', 'ok', 1000);
								 management.regularTruckSignBillPagingBar.moveFirst();			        
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
				management.editRegularTruckSignBillWindow = Ext.create('Foss.management.EditRegularTruckSignBillEditWindow');	
				var record = grid.getStore().getAt(rowIndex);
				management.editRegularTruckSignBillForm.getForm().reset();
				management.editRegularTruckSignBillForm.getForm().loadRecord(record);	
				editForm=management.editRegularTruckSignBillForm.getForm();
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
				//部门公共选择器
				var cmbUseTruckOrgCode = management.editRegularTruckSignBillForm.getForm().findField('useTruckOrgCode');
				cmbUseTruckOrgCode.getStore().load({params:{'saleDepartmentVo.departmentEntity.code' : record.data.useTruckOrgCode}});
				cmbUseTruckOrgCode.setValue(record.data.useTruckOrgCode);
				
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
		header: '线路',
		dataIndex: 'lineName',
		flex:0.7
	},{
		header: '线路里程',
		dataIndex: 'lineDistance',
		flex:0.7
	},{
		header: '体积',
		dataIndex: 'volume',
		flex:0.6
	},{
		header: '重量',
		dataIndex: 'weight',
		flex:0.6
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
			   fieldLabel: '司机数',	
			   labelWidth:60,
			   width:120,
			   dataIndex: 'driverCount'
		   },{
			   fieldLabel: '线路里程合计',	
			   labelWidth:110,
			   width:180,
			   dataIndex: 'lineDistanceTotal'
		   },{
			   fieldLabel:'体积合计',	
			   labelWidth:70,	
			   width:160,
			   dataIndex: 'volumeTotal'
		   },{
			    fieldLabel: '重量合计',	
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
			text: '新增',
			handler: function() {
				 management.regularTruckSignBillEditWindow = Ext.create('Foss.management.AddRegularTruckSignBillEditWindow');
				 management.regularTruckSignBillEditWindow.show();  
				 management.addRegularTruckSignBillForm.getForm().reset();
			     management.addSendFeeInfoForm.getForm().reset();
			}
		}];
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store
		});	
		management.regularTruckSignBillPagingBar=me.bbar;
		management.regularTruckSignBillGrid = me; //加入全局变量中
		me.callParent([cfg]);
	}
});



Ext.define('Foss.management.RegularTruckQueryForm',{
	extend:'Ext.form.Panel',
	title: '查询条件',
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
		fieldLabel: '车牌号码',
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
		fieldId: 'Foss_management_queryRegularTrukSignBillForm_createTime',
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
					management.regularTruckSignBillPagingBar.moveFirst();
				}					
			}
		},{
			text:'导 出',
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



//新增界面其他签单
Ext.define('Foss.management.addRegularTruckSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title:'录入专线对发签单',
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			allowBlank: false,
			margin:'10 10 5 15'		
	},	
	items:[{
		name: 'signBillNo',							
		fieldLabel: '签单编号',
		allowBlank: false,	
		maxLength:50,
		columnWidth:.33
	},{
		name: 'handoverNo',							
		fieldLabel: '交接单号',
		allowBlank: false,
		maxLength:5,
		labelWidth: 60,	
		columnWidth:.33		
	},{
		name: 'arrvRegionName',							
		fieldLabel: '目的地',
		allowBlank: false,
		maxLength : 100,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33		
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
		name: 'vehicleNo',							
		fieldLabel: '车牌号',
		allowBlank: false,
		labelWidth: 60,	
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
			       Ext.Msg.alert('failure','选择错误！');
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
		xtype : 'commonlineselector',
		name: 'lineCode',							
		fieldLabel: '线路',
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
	}]
});

//其他签单费用信息form
Ext.define('Foss.management.addRegularTruckFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10'			
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: '对发单程线路里程提成',
				name: 'msldRoyalty',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.33					
			},{				
				labelAlign: 'top',
				name: 'emsldRoyalty',							
				fieldLabel: ' 空车对发单程里程提成',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:60,
				columnWidth:.33	
			},{
				labelAlign: 'top',
				fieldLabel: '司机提成总额',
				name: 'driverRoyaltyAmount',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.33					
			}]
});


//修改界面其他签单
Ext.define('Foss.management.EditRegularTruckSignBillForm',{
	 extend: 'Ext.form.Panel',
	   layout: 'column',	  
	   title:'编辑专线对发签单',
	   width:670,	   
	 defaults:{
			xtype: 'textfield',
			labelWidth: 60,		
			allowBlank: false,
			margin:'10 10 5 15'		
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
		name: 'handoverNo',							
		fieldLabel: '交接单号',
		allowBlank: false,
		maxLength:5,
		labelWidth: 60,	
		columnWidth:.33		
	},{
		name: 'arrvRegionName',							
		fieldLabel: '目的地',
		allowBlank: false,
		maxLength : 100,
	    maxLengthText:'长度已超过最大限制!',
		labelWidth: 60,	
		columnWidth:.33		
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
		name: 'vehicleNo',							
		fieldLabel: '车牌号',
		allowBlank: false,
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
			       Ext.Msg.alert('failure','选择错误！');
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
		xtype : 'commonlineselector',
		name: 'lineCode',							
		fieldLabel: '线路',
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
	}]
});
//修改其他签单费用信息form
Ext.define('Foss.management.EditRegularTruckFeeInfoForm',{
	   extend: 'Ext.form.Panel',
	   layout: 'column',
	   frame: true,
	   title:'费用信息',	
	   width:670,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10'			
		},
		items:[{
				labelAlign: 'top',
				fieldLabel: '对发单程线路里程提成',
				name: 'msldRoyalty',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.33					
			},{				
				labelAlign: 'top',
				name: 'emsldRoyalty',							
				fieldLabel: ' 空车对发单程里程提成',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:60,
				columnWidth:.33	
			},{
				labelAlign: 'top',
				fieldLabel: '司机提成总额',
				name: 'driverRoyaltyAmount',
				allowBlank: false,
				regex: /^\d{0,30}$/,	
				regexText:"格式输入有误！请输入整数",
				maxLength : 10,
			    maxLengthText:'长度已超过最大限制!',
				labelWidth:50,
				columnWidth:.33					
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
	       management.editRegularTruckSignBillForm= Ext.create('Foss.management.EditRegularTruckSignBillForm'),
	      management.editRegularTruckFeeInfoForm= Ext.create('Foss.management.EditRegularTruckFeeInfoForm')   
	 ],	
	 buttons: [{
	      text: '修改',	
	      align: 'center',
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
		     Ext.MessageBox.confirm("提示","数据已修改，确认提交吗", function(button,text){   
					if(button=='yes'){					
						if(form.isValid()){							
							var params={vo:{regularTruckSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('updateRegularTruckSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
					       	   form.reset();						    	
						       Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);
						       management.regularTruckSignBillPagingBar.moveFirst();
						       management.editRegularTruckSignBillWindow.close();
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
	      text: '保存',	
	      align: 'center',
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues(); 
	      	  vals.signBillDate=new Date(vals.signBillDate);
		     Ext.MessageBox.confirm("提示","数据已添加，确认提交吗", function(button,text){   
					if(button=='yes'){					
						if(form.isValid()){							
							var params={vo:{regularTruckSignBillEntity:vals}};
						     Ext.Ajax.request({
					       	  url: management.realPath('addRegularTruckSignBill.action'),
					       	  jsonData: params,
					       	  success: function(response, opts) { 
					       	   form.reset();						    	
						       Ext.ux.Toast.msg('提示', '保存成功!', 'ok', 1000);
						       management.regularTruckSignBillPagingBar.moveFirst();
						       management.regularTruckSignBillEditWindow.close();
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
		if(this.editRegularTruckSignBillPanelForm==null){
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
		if(this.addRegularTruckSignBillPanelForm==null){
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