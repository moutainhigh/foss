//发车任务model
Ext.define('Foss.load.vehiclEmptyBillModel', {
			extend : 'Ext.data.Model',
					fields : [{
						name : 'vehiclEmptyBillNo', //空驶单号
						type : 'string'
					}, {
						name : 'vehicleNo',//车牌号
						type : 'string'
					}, {
						name : 'state',//状态
						type : 'string'
					}, {
						name : 'driverName',//司机
						type : 'string'
					},  {
						name : 'driverCode',//司机编号
						type : 'string'
					}, {
						name : 'origOrgName',//出发部门
						type : 'string'
					},{
						name : 'destOrgName',//到达部门
						type : 'string'
					},{
					   name : 'destOrgCode',//到达部门编号
						type : 'string'
					},{
						name : 'origTime',//出发时间
						type : 'date',
						convert: dateConvert
					}, {
						name : 'destTime',//到达时间
						type : 'date',
						convert: dateConvert
					},{
						name : 'createTime',//制单时间
						type : 'date',
						convert: dateConvert
					},{
						name : 'createrUserName',//制单人
						type : 'string'
					}

			]
		});

///////////////////////空驶单Store///////////////////////////////////////////////////////////////////////
Ext.define('Foss.load.vehiclEmptyBillStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.vehiclEmptyBillModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryvehiclEmptyBill.action'),
		reader : {
			type : 'json',
			root : 'vehiclEmptyBillVo.queryvehiclEmptyBill',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = load.vehiclEmptyBillForm.getValues();
			Ext.apply(operation, {
				params : {
					//空驶单号
					'vehiclEmptyBillVo.vehiclEmptyBillDto.vehiclEmptyBillNo' : queryParams.vehiclEmptyBillNo,
					//出发部门
					'vehiclEmptyBillVo.vehiclEmptyBillDto.origOrgName': queryParams.origOrgName,
					//出发编码
					'vehiclEmptyBillVo.vehiclEmptyBillDto.origOrgCode': queryParams.origOrgCode,
					//到达部门
					'vehiclEmptyBillVo.vehiclEmptyBillDto.destOrgName' : queryParams.destOrgName,
					//到达编码
					'vehiclEmptyBillVo.vehiclEmptyBillDto.destOrgCode' : queryParams.destOrgCode,
					//司机
					'vehiclEmptyBillVo.vehiclEmptyBillDto.driverCode' : queryParams.driverCode,
					//车牌号
					'vehiclEmptyBillVo.vehiclEmptyBillDto.vehicleNo' : queryParams.vehicleNo,
					//出发时间
					'vehiclEmptyBillVo.vehiclEmptyBillDto.startTime' : queryParams.startTime,
					//截止时间
					'vehiclEmptyBillVo.vehiclEmptyBillDto.endTime' : queryParams.endTime,
					//制单人
					'vehiclEmptyBillVo.vehiclEmptyBillDto.createrUserName' : queryParams.createrUserName
				}
			});
		}
	}
});			

/**********************************Grid*****************************************/
Ext.define('Foss.load.vehiclEmptyBillGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	emptyText : load.i18n('foss.load.deliverLoadGapReport.dataNotFind'),// 查询结果为空
	id : 'Foss.load.vehiclEmptyBillGrid',
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	height : 500,
	selModel : null,
	store : null,
	title : load.i18n('foss.load.seal.vehiclEmptyBill.vehiclEmptyBillnolist'),// 空驶单列表
	columns : [{
		xtype : 'actioncolumn',
		align : 'center',
		text : load.i18n('foss.load.vehicleassemblebillquery.resultGrid.actionColumn'),/*'操作'*/
	    dataIndex : 'action',
	    width : 50,
	    items : [ /*
	    
	                  友情提示以下部分是修改部分因为考虑到仅有三个字段就做修改操作实在是没有什么意义 经过和需求人员讨论后
	                  决定暂停此项功能，为了防止后面业务区需要特注释掉
	        {
			tooltip : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.modify'),//修改
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex){
				var newInfoWindow = Ext.create('Foss.load.ModifyVehiclEmptyBillInfoWindows');
				var newForm = newInfoWindow.getModifyvehiclEmptyBillForm().getForm();
					var rec = grid.store.getAt(rowIndex);
					driverName = rec.get('driverName');
					driverCode = rec.get('driverCode');
					vehicleNo =rec.get('vehicleNo');
					destOrgName = rec.get('destOrgName');
					destOrgCode = rec.get('destOrgCode');
					newForm.loadRecord(rec);
			*//**
			 *205109 zhangpeng 修改 
			 *2015/10/27
			 ***//*
				if(!Ext.isEmpty(vehicleNo)){
				var array_json = {vehiclEmptyBillVo : {vehiclEmptyBillDto:{
					      vehicleNo:rec.get('vehicleNo')
						          }
						       }};
					              }
					Ext.Ajax.request({
						url : load.realPath('CheckVehicleStatusIsDepart.action'),
						jsonData:array_json,
						success : function(response) {
							//newForm.findField('driverName').value=driverName;
							newForm.findField('driverName').setCombValue(driverName,driverCode);
							newForm.findField('destOrgName').setCombValue(destOrgName,destOrgCode);
							newInfoWindow.show();
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							'提示'
							top.Ext.MessageBox.alert(load.i18n
									('foss.load.assignLoadTask.prompt'),
									json.message);			
						}
					});	
				
		 }
				
	},*/{
		tooltip : load.i18n('foss.load.handoverbillquery.resultGrid.cancelToolTipText'),//删除
		iconCls : 'deppon_icons_delete',
		handler : function(grid, rowIndex, colIndex) {
			Ext.MessageBox.confirm(load.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
			load.i18n('foss.load.vehiclEmptyBill.alertInfo.confirmCancel'),
			function(btn){
				if(btn == 'yes'){
					var record = grid.store.getAt(rowIndex);
					var state = record.get('state');
					var vehiclEmptyBillNo=record.get('vehiclEmptyBillNo');
					/**
					 * zhagnpeng
					 *2015/10/29
					 ***/
					var myMask = new Ext.LoadMask(grid, {
						msg : load.i18n('foss.load.vehiclEmptyBill.alertInfo.waitingForCancel')
					});
					
					myMask.show();
					if(state!='UNDEPART'){
				       Ext.Msg.alert("提示","该空驶单不可删除");//("提示","不可删除")
						myMask.hide();
					}else{
						Ext.Ajax.request({
							url : load.realPath('cancelVehiclEmptyBillByVehicleNo.action'),
							params:{
								//空驶单号
								'vehiclEmptyBillVo.vehiclEmptyBillDto.vehiclEmptyBillNo' : record.get('vehiclEmptyBillNo')},
							timeout : 1800000,
							success:function(response){
								grid.store.removeAt(rowIndex);
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(load.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),'操作成功，空驶单【' + vehiclEmptyBillNo + '】已作废');
								myMask.hide();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
			    				result.message);
			    				myMask.hide();
			    			}
						});
					}
					
					
				}
			});
	} 
} ]
	    
	}, {
		header : '空驶单号',
		width : 86,
		dataIndex : 'vehiclEmptyBillNo'
	},{
		header : '车牌号',
		dataIndex : 'vehicleNo'
	}, {
		header :load.i18n('foss.load.handoverbillquery.resultGrid.stateColumn'),// '状态',
		width : 86,
		dataIndex : 'state',
		renderer : function(value) {
			 if(value=="UNDEPART"){
				 return "未出发";
			 }else if(value=="ONTHEWAY"){
				 return "在途";
			 }else{
				 return "已到达";
			 }
		}
		
	}, {
		header :load.i18n('foss.load.handoverbillquery.resultGrid.driverColumn'),// '司机',
		width : 86,
		dataIndex : 'driverName',
	
	}, {
		hidden:true,
		header :load.i18n('foss.load.handoverbillquery.resultGrid.driverCodeColumn'),// '司机工号',
		width : 86,
		dataIndex : 'driverCode',
	
	},{
		header : load.i18n('foss.load.handoverbillshow.basicInfoForm.departDeptLabel'),//'出发部门',
		width : 86,
		dataIndex : 'origOrgName'
		
		
	},{
		header : load.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		dataIndex : 'destOrgName',
		width : 86
     },{
    	hidden:true,
		header : load.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		dataIndex : 'destOrgCode',
		width : 86
     },
	{
		header : load.i18n('foss.load.handoverbillquery.resultGrid.departTimeColumn'),//'出发时间',
		width : 86,
		dataIndex : 'origTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
		
	},{header :load.i18n('foss.load.handoverbillquery.resultGrid.arriveTimeColumn'),// '到达时间',
		width : 86,
		dataIndex : 'destTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},
	{
		header : load.i18n('foss.load.vehicleassemblebillquery.queryForm.createTimeLabel'),//'制单时间',
		width : 86,
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	
	},{header :load.i18n('foss.load.vehicleassemblebillquery.resultGrid.createUserNameColumn'),// '制单人',
		width : 86,
		dataIndex : 'createrUserName'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.store = Ext.create('Foss.load.vehiclEmptyBillStore');
		me.tbar = [{
			xtype:'button',
			text:'新增',   // 新增
			//hidden: !management.billListCheckIndex.isPermission('management/billListCheckAddButton'), // 对账单																							// - 新增
			handler: function(){
				var newInfoWindow = Ext.create('Foss.load.vehiclEmptyBillInfoWindows');
				var newForm = newInfoWindow.getReadvehiclEmptyBillForm().getForm();
				newInfoWindow.show();
			}
		}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					maximumSize : 800,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
						sizeList : [['10', 10], ['50', 50], ['100', 100], ['800', 800]]
					})
				});
		load.vehiclEmptyBillStorePagingBar= me.bbar;
		me.callParent([cfg]);
	}
});
///////////////////////修改车辆空驶单///////////////////////////////
/*Ext.define('Foss.load.ModifyvehiclEmptyBillPanel',{
	extend: 'Ext.form.Panel',
	title: '修改车辆空驶单',
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	height :300,
	width:850,
	defaultType: 'textfield',
	defaults : {
		margin : '5 10 5 10',
		labelWidth :60,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout:'column',
	items:[{
		xtype : 'commontruckselector',
		fieldLabel : load.i18n('foss.load.assignLoadTask.vehicleNo'),//'车牌号',
		name : 'vehicleNo',
		listeners:{
			blur:function(txtField,eOpts ){
				   var newForm =this.up('form').getForm();
					txtField.setValue(Ext.String.trim(txtField.getValue()));
					var vehicleNo=txtField.value;
						if(!Ext.isEmpty(vehicleNo)){
							 var array_json = {vehiclEmptyBillVo : {vehiclEmptyBillDto:{
								 vehicleNo:newForm.findField('vehicleNo').value
								}
								}};
						}
						Ext.Ajax.request({
							url : load.realPath('CheckVehicleStatusIsArrive.action'),
							jsonData:array_json,
							success : function(response) {	
							},
							exception : function(response) {
								var json = Ext.decode(response.responseText);
								'提示'
								top.Ext.MessageBox.alert(load.i18n
										('foss.load.assignLoadTask.prompt'),
										json.message);
//								var json = Ext.decode(response.responseText);
//								Ext.Msg.alert('提示',json.message);
							}
						});
					}
				}
			},{
			xtype:'commondriverselector',
			fieldLabel: load.i18n('foss.load.assignLoadTask.driver'),//'司机',
			name: 'driverName',
			forceSelection: true,
			listeners : {
				'change' : function(field, newValue,oldValue,eOpts){
					var form = this.up('form').getForm();
					if(field.isValid()){
						//如果司机不为空，则更新司机电话
						if(!Ext.isEmpty(newValue)){
							var store = field.store;
							var driverRecord = store.findRecord('empCode',newValue,0,false,true,true);
							if(driverRecord == null){
								form.findField('driverTel').reset();
							}else{
								form.findField('driverTel').setValue(driverRecord.get('empPhone'));	
								form.findField('driverCode').setValue(driverRecord.get('empCode'));
							}
						}
					}else{
						form.findField('driverTel').reset();
					}
				}
			}
		}, {
			xtype : 'dynamicorgcombselector',
			fieldLabel : load.i18n('foss.load.queryloadtask.reachOrgName'),//'到达部门',
			type : 'ORG',
			name : 'destOrgName',
			listeners : {
				'change' : function(field, newValue,oldValue,eOpts){
					var form = this.up('form').getForm();
					if(field.isValid()){
						//如果到达部门不为空，则更新司机电话
						if(!Ext.isEmpty(newValue)){
							var store = field.store;
							var driverRecord = store.findRecord('code',newValue,0,false,true,true);
							if(driverRecord == null){
								form.findField('destOrgName').reset();
							}else{
								form.findField('orgCode').setValue(driverRecord.get('code'));	
							}
						}
					}else{
						form.findField('driverTel').reset();
					}
				}
			}	
	  },{   hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')'司机姓名',
			name : 'driverName',
			readOnly : true
      },{  
			fieldLabel : load.i18n('foss.load.seal.vehiclEmptyBill.vehiclEmptyBillno')'空驶单号',
			name : 'vehiclEmptyBillNo',
			readOnly : true
      },{   hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')'司机电话',
			name : 'driverTel',
			readOnly : true
	  },
	   {    hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.orgName')'到达部门编码',
			name : 'orgCode',
			readOnly : true
	  },{
		   xtype:'container',
		   border:false,
		   html:'&nbsp;',
		   columnWidth:.75
	   },{
		xtype:'button',
		text:'取消',   // 取消 关闭
		columnWidth: .1,
		handler: function(){
			this.up('window').close();
         }
	},{
		xtype:'button',
		text:'保存',   // 保存
		cls:'yellow_button',
		hidden:false,
		columnWidth: .1,
		handler: function(){
			var detailInfoWindow = Ext.create('Foss.load.ModifyVehiclEmptyBillInfoWindows');
//			var newForm = detailInfoWindow.getReadvehiclEmptyBillForm().getForm();
			var newForm =this.up('form').getForm();
			if(Ext.isEmpty(newForm.findField('vehicleNo').getValue())){
				Ext.Msg.alert('提示','车牌号不能为空');
				return;
			}
			if(Ext.isEmpty(newForm.findField('driverCode').getValue())){
				Ext.Msg.alert('提示','司机不能为空');
				return;  
			}
			if(Ext.isEmpty(newForm.findField('destOrgCode').getValue())){
				Ext.Msg.alert('提示','到达部门不能为空');
				return;
			}
			 var array_json = {vehiclEmptyBillVo : {vehiclEmptyBillDto:{
				 vehicleNo:newForm.findField('vehicleNo').value,
				 driverName:newForm.findField('driverName').value,
				 destOrgCode:newForm.findField('destOrgCode').value,
				 destOrgName:newForm.findField('orgName').value,
				 driverCode:newForm.findField('driverCode').value,
				 driverTel:newForm.findField('driverTel').value
				}
				}};
			 Ext.Ajax.request({
					url : load.realPath('ModifyVehiclEmptyBill.action'),
					jsonData:array_json,
					success : function(response) {
						newForm.reset();
						departuregrid.store.load();
						Ext.Msg.alert('提示','修改成功');
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert('提示','
失败');
					}
				});
		}

	}]
});
*/

///////////////////////新增车辆空驶单panel////////////////////////////////////
Ext.define('Foss.load.vehiclEmptyBillPanel',{
	extend: 'Ext.form.Panel',
	title: '新增车辆空驶单',
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	height :300,
	width:850,
	defaultType: 'textfield',
	defaults : {
		margin : '5 10 5 10',
		labelWidth :60,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout:'column',
	items:[{
		xtype : 'commontruckselector',
		fieldLabel : load.i18n('foss.load.assignLoadTask.vehicleNo'),//'车牌号',
		name : 'vehicleNo',
		listeners:{
			blur:function(txtField,eOpts ){
				   var newForm =this.up('form').getForm();
					txtField.setValue(Ext.String.trim(txtField.getValue()));
					var vehicleNo=txtField.value;
						if(!Ext.isEmpty(vehicleNo)){
							 var array_json = {vehiclEmptyBillVo : {vehiclEmptyBillDto:{
								 vehicleNo:newForm.findField('vehicleNo').value
								}
								}};
						}
						Ext.Ajax.request({
							url : load.realPath('CheckVehicleStatusIsArrive.action'),
							jsonData:array_json,
							success : function(response) {	
							},
							exception : function(response) {
								var json = Ext.decode(response.responseText);
								/*'提示'*/
								top.Ext.MessageBox.alert(load.i18n
										('foss.load.assignLoadTask.prompt'),
										json.message);
//								var json = Ext.decode(response.responseText);
//								Ext.Msg.alert('提示',json.message);
							}
						});
					}
				}
			},{
			xtype:'commonowndriverselector',
			fieldLabel: load.i18n('foss.load.assignLoadTask.driver'),//'司机',
			name: 'driverCode',
			forceSelection: true,
			listeners: {
				'select': function(field, records, eOpts) {					
					var record = records[0],
						name = record.get('name');
					var form = this.up('form').getForm();
					form.findField('driverTel').setValue(record.get('empPhone'));	
					form.findField('driverName').setValue(record.get('empName'));
				}
			}
			
		}, {
			xtype : 'dynamicorgcombselector',
			forceSelection : true,
			types:'ORG',
			salesDepartment:'Y',//自己定义参数就OK  --定义查询营业部
			transferCenter:'Y',//--或者查询外场
			fieldLabel : '到达部门',
			name : 'destOrgCode'
	  },{   hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机姓名'*/,
			name : 'driverName',
			readOnly : true
      },{   hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
			name : 'driverTel',
			readOnly : true
	  },
	   {    hidden:true,
			fieldLabel : load.i18n('foss.load.handoverbilladdnew.basicInfoForm.orgName')/*'到达部门名称'*/,
			name : 'orgName',
			readOnly : true
	  },{
		   xtype:'container',
		   border:false,
		   html:'&nbsp;',
		   columnWidth:.75
	   },{
		xtype:'button',
		text:'取消',   // 取消 关闭
		columnWidth: .1,
		handler: function(){
			this.up('window').close();
         }
	},{
		xtype:'button',
		text:'保存',   // 保存
		cls:'yellow_button',
		hidden:false,
		columnWidth: .1,
		handler: function(){
			var detailInfoWindow = Ext.create('Foss.load.vehiclEmptyBillInfoWindows');
//			var newForm = detailInfoWindow.getReadvehiclEmptyBillForm().getForm();
			var newForm =this.up('form').getForm();
			if(Ext.isEmpty(newForm.findField('vehicleNo').getValue())){
				Ext.Msg.alert('提示','车牌号不能为空');
				return;
			}
			if(Ext.isEmpty(newForm.findField('driverCode').getValue())){
				Ext.Msg.alert('提示','司机不能为空');
				return;  
			}
			if(Ext.isEmpty(newForm.findField('destOrgCode').getValue())){
				Ext.Msg.alert('提示','到达部门不能为空');
				return;
			}
			 var array_json = {vehiclEmptyBillVo : {vehiclEmptyBillDto:{
				 vehicleNo:newForm.findField('vehicleNo').value,
				 driverName:newForm.findField('driverName').value,
				 destOrgCode:newForm.findField('destOrgCode').value,
				 destOrgName: newForm.findField('destOrgCode').getRawValue(),
				 driverCode:newForm.findField('driverCode').value,
				 driverTel:newForm.findField('driverTel').value
				}
				}};
			 Ext.Ajax.request({
					url : load.realPath('addvehiclEmptyBill.action'),
					jsonData:array_json,
					success : function(response) {
						newForm.reset();
						load.vehiclEmptyDeparturegrid.store.load();
						Ext.Msg.alert('提示','新增成功');
					},
					exception : function(response) {
						Ext.Msg.alert('提示','新增失败');
					}
				});
		}
		//});
		//management.billListCheckIndex.pagingBar.moveFirst();
			
			//////////////
		
	
	
	
	}]
});

//////////////////////////////////////新增车辆空驶单//////////////////
Ext.define('Foss.load.vehiclEmptyBillInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:900,
	readvehiclEmptyBillForm : null,
	getReadvehiclEmptyBillForm: function(){
		if(this.readvehiclEmptyBillForm==null){
			this.readvehiclEmptyBillForm = Ext.create('Foss.load.vehiclEmptyBillPanel');
		}
		//management.billListCheckRead = this.readBillListCheckForm;
		return this.readvehiclEmptyBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadvehiclEmptyBillForm()
				];
		me.callParent([cfg]);
	}
});
///////////////////修改弹出框//////////////////////////////////////////////////
//Ext.define('Foss.load.ModifyVehiclEmptyBillInfoWindows',{
//	extend: 'Ext.window.Window',
//	modal:true,
//	closeAction: 'hide',
//	width:900,
//	modifyvehiclEmptyBillForm : null,
//	getModifyvehiclEmptyBillForm: function(){
//		if(this.modifyvehiclEmptyBillForm==null){
//			this.modifyvehiclEmptyBillForm = Ext.create('Foss.load.ModifyvehiclEmptyBillPanel');
//		}
//		//management.billListCheckRead = this.readBillListCheckForm;
//		return this.modifyvehiclEmptyBillForm;
//	},
//	constructor: function(config){
//		var me = this,
//			cfg = Ext.apply({}, config);
//		me.items = [
//				    me.getModifyvehiclEmptyBillForm()
//				];
//		me.callParent([cfg]);
//	}
//});

////////////////////////////////////form//////////////////////////////////////
Ext.define('Foss.load.vehiclEmptyBillForm', {
			extend : 'Ext.form.Panel',
			title :load.i18n('foss.load.queryLoadingProgress.queryCondition'),//查询条件
			collapsible : true,//允许展开收缩
			animCollapse : true,//显示动画效果
			layout : 'column',
			frame : true,
			border : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4,
				xtype : 'textfield'
			},
			items : [{
					xtype : 'textfield',
					fieldLabel :load.i18n('foss.load.seal.vehiclEmptyBill.vehiclEmptyBillno'),// '空驶单号',
					name : 'vehiclEmptyBillNo',
					regex:/[A-Za-z0-9]+-?[A-Za-z0-9]+/
				},{
					xtype : 'dynamicorgcombselector',//
					fieldLabel : load.i18n('foss.load.queryloadtask.origOrgName'),//'出发部门',
					type : 'ORG',
					name : 'origOrgCode'	
				},{
					xtype : 'dynamicorgcombselector',
					forceSelection : true,
					types:'ORG',
					salesDepartment:'Y',//自己定义参数就OK  --定义查询营业部
					transferCenter:'Y',//--或者查询外场
					fieldLabel : '到达部门',
					name : 'destOrgCode',
				},
				{
					xtype:'commonowndriverselector',
					fieldLabel: load.i18n('foss.load.assignLoadTask.driver'),//'司机',
					name: 'driverCode',
					forceSelection: true,
						
				},{
					xtype : 'commontruckselector',
					fieldLabel : load.i18n('foss.load.assignLoadTask.vehicleNo'),//'车牌号',
					name : 'vehicleNo',
					listeners: {

				}
			},
			{
				xtype : 'rangeDateField',
				fieldLabel : load.i18n('foss.load.vehicleassemblebillquery.queryForm.createTimeLabel'),//'制单时间',
				columnWidth : 1/2,
				fieldId:'Foss_load_QuerySecondCarDepartureForm_createTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'startTime',
				allowBlank:false,
				dateRange:30,
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			},{
				name : 'createrUserName',
				xtype : 'textfield',
				fieldLabel : load.i18n('foss.load.vehicleassemblebillquery.resultGrid.createUserNameColumn'),//制单人
				columnWidth : 0.25,
				labelWidth : 80},
				{
		    		   xtype:'container',
		    		   border:false,
		    		   html:'&nbsp;',
		    		   columnWidth:.25
		    	   },
		    	   {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
							text : load.i18n('foss.load.assignLoadTask.reset'),//'重置',
							columnWidth : .2,
							handler : function() {
								this.up('form').getForm().reset();
								this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'00', '00', '00'), 'Y-m-d H:i:s'));
								this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
												new Date().getMonth(), new Date().getDate(),
												'23', '59', '59'), 'Y-m-d H:i:s')); 
								var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
								Ext.Ajax.request({
									
								});
							}
						}, {
							xtype : 'container',
							columnWidth : .6,
							html : '&nbsp;'
						}, {
							text : load.i18n('foss.load.deliverLoadGapReport.query'),//'查询',
							columnWidth : .2,
							cls:'yellow_button',
							handler : function() {
								   var form = load.vehiclEmptyBillForm.getForm();
									if (!form.isValid())
									return false;
									load.vehiclEmptyBillStorePagingBar.moveFirst();
							}
							}]
					}],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([cfg]);
					}
				});
/****************************************** 到达本部门的数据end**************************************/
Ext.onReady(function() {
	Ext.QuickTips.init();
	var departureform = Ext.create('Foss.load.vehiclEmptyBillForm');
	var departuregrid = Ext.create('Foss.load.vehiclEmptyBillGrid');
	load.vehiclEmptyBillForm=departureform;
	load.vehiclEmptyDeparturegrid=departuregrid;
//	load.departureform=departureform;
//	load.arriveform=arriveform; 
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-loadSecondCarIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//boder:true,
		//frame:true,
		/*items : [{
			xtype : 'panel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
		    items :[{
						title : "查询条件",//'临时任务',
						tabConfig : {
							width : 120
						},
						itemId : 'departureTab',
						items : [departureform, departuregrid]
					}]
			}],*/
			items : [ departureform,departuregrid],
		    renderTo: 'T_load-vehiclEmptyBill-body'
	});

});