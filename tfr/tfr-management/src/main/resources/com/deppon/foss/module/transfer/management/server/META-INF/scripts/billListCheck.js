//定义电子对账单的model
Ext.define('Foss.Management.BillListCheckModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'billDate', type: 'date',
				convert: function(v){
					if(v!=null){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d');
					}
				}},
	        {name: 'fuelFee', type: 'String'},
	        {name: 'fuelFeeSell', type: 'string'},
	        {name: 'roadTollFee', type: 'string'},
	        {name: 'roadTollFeeSell', type: 'string'},
	        {name: 'divisionOrgName', type: 'string'},
			{name: 'divisionOrgCode', type: 'string'},
	        {name: 'transDepartmentName', type: 'string'},
			{name: 'transDepartmentCode', type: 'string'},
	        {name: 'id', type: 'string'}]

});

// 导入表单
Ext.define('Foss.billListCheck.form.ImportBillListCheck',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:90
		},
		standardSubmit: true,
		items : [{
			        xtype: 'filefield',
			        name: 'uploadFile',
			        readOnly:false,
			        buttonOnly:false,
			        fieldLabel: '请选择附件',// '请选择附件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: '浏览',// '浏览',
			      	columnWidth:1
			    },{
					readOnly:true,
					columnWidth: .6
				},{
						columnWidth:.2,
						xtype : 'button',
						text: '导入',// '导入',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					            	var myMask = new Ext.LoadMask(this.up('form'),  {msg:'正在导入，请稍等...'});// "正在导入，请稍等..."
		 							    myMask.show();
										
										 form.submit({
					                    url: management.realPath('importBillListDetail.action'),
					                    success: function(form, action) {
					                    	myMask.hide();
					                    	var json =action.result;
											management.resultBillListCheck.store.load();
					                        Ext.MessageBox.alert('电子对账单：','导入成功');
					                    },
											exception : function(form, action) {
																			myMask.hide();
																			json=action.result;
																			var msg=json.message;
																			while(msg.indexOf(';')>-1){
																				msg=msg.replace(';', "\r\n");
																			}			        				
																			Ext.create('Ext.window.Window', {
																				title: '电子对账单导入失败',
																				height: 200,
																				width: 400,
																				layout: 'fit',
																				items: {  
																					xtype: 'form',
																					border: false,
																					items:[
																						{
																							xtype : 'textarea',
																							fieldLabel: '',
																							height: 130,
																							width: 380,
																							autoScroll:true,
																							readOnly:true,
																							name: 'message',
																							value:msg
																						}
																						  ]
																				}
																			}).show();
											},
											failure: function(form, action) {
																			myMask.hide();
																			json=action.result;
																			var msg=json.message;
																			while(msg.indexOf(';')>-1){
																				msg=msg.replace(';', "\r\n");
																			}			        				
																			Ext.create('Ext.window.Window', {
																				title: '电子对账单导入失败',
																				height: 200,
																				width: 400,
																				layout: 'fit',
																				items: {  
																					xtype: 'form',
																					border: false,
																					items:[
																						{
																							xtype : 'textarea',
																							fieldLabel: '',
																							height: 130,
																							width: 380,
																							autoScroll:true,
																							readOnly:true,
																							name: 'message',
																							value:msg
																						}
																						  ]
																				}
																			}).show();
											}
														 
					            });
						}
					}
				},{
						xtype : 'button',
						columnWidth:.2,
						text: '取消',// '取消',
						handler: function() {
							this.up('window').close();
						}
				}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})

// 定义编辑对账单信息的panel
Ext.define('Foss.Management.opBillListCheckPanel',{
	extend: 'Ext.form.Panel',
	title: '编辑对账单信息',
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5'
	},
	layout:'column',
	items:[{
		fieldLabel:'日期',   // 时间
		xtype: 'datefield',
		name: 'billDate',
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		readOnly:true,
		columnWidth: .5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		division:'Y',
		name:'divisionOrgName',
		fieldLabel:'事业部',   // 事业部
		columnWidth:.7,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var divisionOrg = field.getValue();
				if(divisionOrg == ''){
					form.findField('divisionOrgName').setValue('');
					return;
				}
				form.findField('divisionOrgName').setValue(field.rawValue);
			}
		}
	},{
		xtype: 'textfield',
		name: 'divisionOrgCode',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .3
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		name:'transDepartmentName',
		fieldLabel:'车队',   // 车队
		columnWidth:.7,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var transDepartment = field.getValue();
				if(transDepartment == ''){
					form.findField('transDepartmentName').setValue('');
					return;
				}
				form.findField('transDepartmentName').setValue(field.rawValue);
			}
		}
	},{
		xtype: 'textfield',
		name: 'transDepartmentCode',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .3
	},{
		name:'fuelFee',
		xtype:'numberfield',
		fieldLabel:'燃油费金额',   // 燃油费金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'fuelFeeSell',
		xtype:'numberfield',
		fieldLabel:'燃油费优惠金额',   // 燃油费优惠金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'roadTollFee',
		xtype:'numberfield',
		fieldLabel:'路桥费金额',   // 路桥费金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'roadTollFeeSell',
		xtype:'numberfield',
		fieldLabel:'路桥费优惠金额',   // 路桥费优惠金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'button',
		text:'取消',   // 取消 关闭
		columnWidth: .2,
		handler: function(){
			//var form = this.up('form').getForm();
			//form.reset();
			this.up('window').close();
		}
	},{
		xtype: 'textfield',
		name: 'id',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .6
	},{
		xtype:'button',
		text:'保存',   // 保存
		cls:'yellow_button',
		disabled:management.billListCheckIndex.isPermission('management/billListCheckEditButton')?false:true,
		hidden:management.billListCheckIndex.isPermission('management/billListCheckEditButton')?false:true,
		columnWidth: .2,
		handler: function(){
				var detailInfoWindow = management.readDetailInfoWindow;
				var editForm = detailInfoWindow.getReadBillListCheckForm().getForm();
				
				
				if(Ext.isEmpty(editForm.findField('billDate').value)){
					Ext.Msg.alert('提示','日期不能为空');
					return;
				}
				
				if(Ext.isEmpty(editForm.findField('divisionOrgCode').value)){
					Ext.Msg.alert('提示','所属事业部不能为空');
					return;
				}
				
				if(Ext.isEmpty(editForm.findField('transDepartmentCode').value)){
					Ext.Msg.alert('提示','所属车队不能为空');
					return;
				}
				
				if(Ext.isEmpty(editForm.findField('fuelFee').value)){
					Ext.Msg.alert('提示','燃油费不能为空');
					return;
				}
				
				if(Ext.isEmpty(editForm.findField('fuelFeeSell').value)){
					Ext.Msg.alert('提示','燃油优惠费不能为空');
					return;
				}
				if(Ext.isEmpty(editForm.findField('roadTollFee').value)){
					Ext.Msg.alert('提示','路桥费不能为空');
					return;
				}
				
				if(Ext.isEmpty(editForm.findField('roadTollFeeSell').value)){
					Ext.Msg.alert('提示','路桥优惠费不能为空');
					return;
				}
				
				var jsfuelFee = 0;
				var jsfuelFeeSell = 0;
				var jsroadTollFee = 0;
				var jsroadTollFeeSell = 0;
				
				if(editForm.findField('fuelFee').value) jsfuelFee= editForm.findField('fuelFee').value;
				if(editForm.findField('fuelFeeSell').value) jsfuelFeeSell= editForm.findField('fuelFeeSell').value;
				if(editForm.findField('roadTollFee').value) jsroadTollFee= editForm.findField('roadTollFee').value;
				if(editForm.findField('roadTollFeeSell').value) jsroadTollFeeSell= editForm.findField('roadTollFeeSell').value;
				
				if(!Ext.isNumber(jsfuelFee)) {
					Ext.Msg.alert('提示','燃油费金额必须为数值类型');
					return;
				}else{
					if(jsfuelFee<0){
						Ext.Msg.alert('提示','燃油费金额值不能为负数');
						return;
					}else{
						if(jsfuelFee.toString().length>17){
							Ext.Msg.alert('提示','燃油费金额值超过长度');
							return;
						}
					}
				}
				if(!Ext.isNumber(jsfuelFeeSell)) {
					Ext.Msg.alert('提示','燃油费优惠金额必须为数值类型');
					return;
				}else{
					if(jsfuelFeeSell<0){
						Ext.Msg.alert('提示','燃油费优惠金额值不能为负数');
						return;
					}else{
						if(jsfuelFeeSell.toString().length>17){
							Ext.Msg.alert('提示','燃油费优惠金额值超过长度');
							return;
						}
					}
				}
				if(!Ext.isNumber(jsroadTollFee)) {
					Ext.Msg.alert('提示','路桥费金额必须为数值类型');
					return;
				}else{
					if(jsroadTollFee<0){
						Ext.Msg.alert('提示','路桥费金额值不能为负数');
						return;
					}else{
						if(jsroadTollFee.toString().length>17){
							Ext.Msg.alert('提示','路桥费金额值超过长度');
							return;
						}
					}
				}
				
				if(!Ext.isNumber(jsroadTollFeeSell)) {
					Ext.Msg.alert('提示','路桥费优惠金额为数值类型');
					return;
				}else{
					if(jsroadTollFeeSell<0){
						Ext.Msg.alert('提示','路桥费优惠金额值不能为负数');
						return;
					}else{
						if(jsroadTollFeeSell.toString().length>17){
							Ext.Msg.alert('提示','路桥费优惠金额值超过长度');
							return;
						}
					}
				}
				
				
				var msgStr = '修改数据为：<br/><br/>';
					msgStr += '燃油费金额：'+ jsfuelFee  +'&nbsp;&nbsp;';
					msgStr += '燃油费优惠金额：'+ jsfuelFeeSell+'<br/><br/>';
					msgStr += '路桥费金额：'+ jsroadTollFee +'&nbsp;&nbsp;';
					msgStr += '路桥费优惠金额：'+ jsroadTollFeeSell+'<br/>';
				Ext.MessageBox.confirm('提示', msgStr ,function(btn){
				if(btn == 'yes'){
				var array_json = {billListCheckVo : {billListCheckDto:{
					 id:editForm.findField('id').value,
					 billDate:editForm.findField('billDate').value,
					 divisionOrgCode:editForm.findField('divisionOrgCode').value,
					 divisionOrgName:editForm.findField('divisionOrgName').value,
					 transDepartmentCode:editForm.findField('transDepartmentCode').value,
					 transDepartmentName:editForm.findField('transDepartmentName').value,
					 fuelFee:editForm.findField('fuelFee').value,
					 fuelFeeSell:editForm.findField('fuelFeeSell').value,
					 roadTollFee:editForm.findField('roadTollFee').value,
					 roadTollFeeSell:editForm.findField('roadTollFeeSell').value
					}
				}};
            	Ext.Ajax.request({
    				url:management.realPath('modBillListById.action'),
    				jsonData:array_json,
    				success : function(response) {
						//var json = Ext.decode(response.responseText);
        				management.resultBillListCheck.store.load();
						Ext.Msg.alert('提示','修改成功');
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert('提示',json.message);
        			}
    			});
				}
				});
		
			management.billListCheckIndex.pagingBar.moveFirst();
		}
	}]
});

// 定义新增对账单信息的panel
Ext.define('Foss.Management.newBillListCheckPanel',{
	extend: 'Ext.form.Panel',
	title: '新增对账单信息',
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5'
	},
	layout:'column',
	items:[{
		fieldLabel:'日期',   // 时间
		xtype: 'datefield',
		name: 'billDate',
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth: .5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'textfield',
		name:'divisionOrgName',
		fieldLabel:'事业部',   // 事业部
		readOnly:true,
		columnWidth:.7
	},{
		xtype: 'textfield',
		name: 'divisionOrgCode',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .3
	},{
		xtype:'textfield',
		name:'transDepartmentName',
		fieldLabel:'车队',   // 车队
		readOnly:true,
		columnWidth:.7
	},{
		xtype: 'textfield',
		name: 'transDepartmentCode',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .3
	},{
		name:'fuelFee',
		xtype:'numberfield',
		fieldLabel:'燃油费金额',   // 燃油费金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'fuelFeeSell',
		xtype:'numberfield',
		fieldLabel:'燃油费优惠金额',   // 燃油费优惠金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'roadTollFee',
		xtype:'numberfield',
		fieldLabel:'路桥费金额',   // 路桥费金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'roadTollFeeSell',
		xtype:'numberfield',
		fieldLabel:'路桥费优惠金额',   // 路桥费优惠金额
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'button',
		text:'取消',   // 取消 关闭
		columnWidth: .2,
		handler: function(){
			//var form = this.up('form').getForm();
			//form.reset();
			//form.findField('divisionOrgCode').setValue(management.currentCareerCode);
			//form.findField('divisionOrgName').setValue(management.currentCareerName);
			//form.findField('transDepartmentCode').setValue(management.currentOrgCode);
			//form.findField('transDepartmentName').setValue(management.currentOrgName);
			this.up('window').close();
		}
	},{
		xtype: 'textfield',
		name: 'id',
		hidden: true
	},{
		readOnly:true,
		columnWidth: .6
	},{
		xtype:'button',
		text:'保存',   // 保存
		cls:'yellow_button',
		disabled:management.billListCheckIndex.isPermission('management/billListCheckAddButton')?false:true,
		hidden:management.billListCheckIndex.isPermission('management/billListCheckAddButton')?false:true,
		columnWidth: .2,
		handler: function(){
				var detailInfoWindow = management.newDetailInfoWindow;
				var newForm = detailInfoWindow.getReadBillListCheckForm().getForm();
				
				if(Ext.isEmpty(newForm.findField('billDate').value)){
					Ext.Msg.alert('提示','日期不能为空');
					return;
				}
				
				if(Ext.isEmpty(newForm.findField('divisionOrgCode').value)){
					Ext.Msg.alert('提示','所属事业部不能为空');
					return;
				}
				
				if(Ext.isEmpty(newForm.findField('transDepartmentCode').value)){
					Ext.Msg.alert('提示','所属车队不能为空');
					return;
				}
				
				if(Ext.isEmpty(newForm.findField('fuelFee').value)){
					Ext.Msg.alert('提示','燃油费不能为空');
					return;
				}
				
				if(Ext.isEmpty(newForm.findField('fuelFeeSell').value)){
					Ext.Msg.alert('提示','燃油优惠费不能为空');
					return;
				}
				if(Ext.isEmpty(newForm.findField('roadTollFee').value)){
					Ext.Msg.alert('提示','路桥费不能为空');
					return;
				}
				
				if(Ext.isEmpty(newForm.findField('roadTollFeeSell').value)){
					Ext.Msg.alert('提示','路桥优惠费不能为空');
					return;
				}
				
				var jsfuelFee = 0;
				var jsfuelFeeSell = 0;
				var jsroadTollFee = 0;
				var jsroadTollFeeSell = 0;
				if(newForm.findField('fuelFee').value) jsfuelFee= newForm.findField('fuelFee').value;
				if(newForm.findField('fuelFeeSell').value) jsfuelFeeSell= newForm.findField('fuelFeeSell').value;
				if(newForm.findField('roadTollFee').value) jsroadTollFee= newForm.findField('roadTollFee').value;
				if(newForm.findField('roadTollFeeSell').value) jsroadTollFeeSell= newForm.findField('roadTollFeeSell').value;
				
				if(!Ext.isNumber(jsfuelFee)) {
					Ext.Msg.alert('提示','燃油费金额必须为数值类型');
					return;
				}else{
					if(jsfuelFee<0){
						Ext.Msg.alert('提示','燃油费金额值不能为负数');
						return;
					}else{
						if(jsfuelFee.toString().length>17){
							Ext.Msg.alert('提示','燃油费金额值超过长度');
							return;
						}
					}
				}
				if(!Ext.isNumber(jsfuelFeeSell)) {
					Ext.Msg.alert('提示','燃油费优惠金额必须为数值类型');
					return;
				}else{
					if(jsfuelFeeSell<0){
						Ext.Msg.alert('提示','燃油费优惠金额值不能为负数');
						return;
					}else{
						if(jsfuelFeeSell.toString().length>17){
							Ext.Msg.alert('提示','燃油费优惠金额值超过长度');
							return;
						}
					}
				}
				if(!Ext.isNumber(jsroadTollFee)) {
					Ext.Msg.alert('提示','路桥费金额必须为数值类型');
					return;
				}else{
					if(jsroadTollFee<0){
						Ext.Msg.alert('提示','路桥费金额值不能为负数');
						return;
					}else{
						if(jsroadTollFee.toString().length>17){
							Ext.Msg.alert('提示','路桥费金额值超过长度');
							return;
						}
					}
				}
				
				if(!Ext.isNumber(jsroadTollFeeSell)) {
					Ext.Msg.alert('提示','路桥费优惠金额为数值类型');
					return;
				}else{
					if(jsroadTollFeeSell<0){
						Ext.Msg.alert('提示','路桥费优惠金额值不能为负数');
						return;
					}else{
						if(jsroadTollFeeSell.toString().length>17){
							Ext.Msg.alert('提示','路桥费优惠金额值超过长度');
							return;
						}
					}
				}
				
				
				var msgStr = '新增记录为：<br/><br/>';
					msgStr += '日期：'+ Ext.Date.format(newForm.findField('billDate').value, 'Y-m-d')+'<br/><br/>';
					msgStr += '燃油费金额：'+ jsfuelFee  +'&nbsp;&nbsp;';
					msgStr += '燃油费优惠金额：'+ jsfuelFeeSell+'<br/><br/>';
					msgStr += '路桥费金额：'+ jsroadTollFee +'&nbsp;&nbsp;';
					msgStr += '路桥费优惠金额：'+ jsroadTollFeeSell+'<br/>';
				Ext.MessageBox.confirm('提示', msgStr ,function(btn){
				if(btn == 'yes'){
					var array_json = {billListCheckVo : {billListCheckDto:{
						 id:newForm.findField('id').value,
						 billDate:newForm.findField('billDate').value,
						 divisionOrgCode:newForm.findField('divisionOrgCode').value,
						 divisionOrgName:newForm.findField('divisionOrgName').value,
						 transDepartmentCode:newForm.findField('transDepartmentCode').value,
						 transDepartmentName:newForm.findField('transDepartmentName').value,
						 fuelFee:newForm.findField('fuelFee').value,
						 fuelFeeSell:newForm.findField('fuelFeeSell').value,
						 roadTollFee:newForm.findField('roadTollFee').value,
						 roadTollFeeSell:newForm.findField('roadTollFeeSell').value
						}
					}};
					Ext.Ajax.request({
						url:management.realPath('addBillListDetail.action'),
						jsonData:array_json,
						success : function(response) {
							//var json = Ext.decode(response.responseText);
							newForm.reset();
							management.resultBillListCheck.store.load();
							Ext.Msg.alert('提示','新增成功');
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert('提示',json.message);
						}
					});
			}
			});
			management.billListCheckIndex.pagingBar.moveFirst();
		}
	}]
});

// 导入窗口
Ext.define('Foss.billListCheck.window.ImportBillListCheck', {
	extend:'Ext.window.Window',
	title: '导入电子对账单',// '导入电子对账单',
	modal:true,
	closeAction:'hide',
	width: 550,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	listeners:{
		hide:function(comp, eOpts){
			this.down('form').getForm().findField('uploadFile').reset();
		}
	},
	items:[Ext.create('Foss.billListCheck.form.ImportBillListCheck')]
});

// 定义弹出的编辑窗口
Ext.define('Foss.Management.readBillListCheckInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:500,
	readBillListCheckForm : null,
	getReadBillListCheckForm: function(){
		if(this.readBillListCheckForm==null){
			this.readBillListCheckForm = Ext.create('Foss.Management.opBillListCheckPanel');
		}
		management.billListCheckRead = this.readBillListCheckForm;
		return this.readBillListCheckForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadBillListCheckForm()
				];
		me.callParent([cfg]);
	}
});

// 定义弹出的新增窗口
Ext.define('Foss.Management.newBillListCheckInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:500,
	readBillListCheckForm : null,
	getReadBillListCheckForm: function(){
		if(this.readBillListCheckForm==null){
			this.readBillListCheckForm = Ext.create('Foss.Management.newBillListCheckPanel');
		}
		management.billListCheckRead = this.readBillListCheckForm;
		return this.readBillListCheckForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadBillListCheckForm()
				];
		me.callParent([cfg]);
	}
});

management.readDetailInfoWindow = Ext.create('Foss.Management.readBillListCheckInfoWindows');
management.newDetailInfoWindow = Ext.create('Foss.Management.newBillListCheckInfoWindows');
management.upLoadDetailInfoWindow = Ext.create('Foss.billListCheck.window.ImportBillListCheck');

// 定义电子对账单的store
Ext.define('Foss.Management.BillListCheckStore',{
	extend: 'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.Management.BillListCheckModel',
	pageSize:10,
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url: management.realPath('queryBillList.action'),
		reader : {
			type : 'json',
			root : 'billListCheckVo.billListCheckList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = management.queryBillListCheck.getValues();
				if(queryParams.divisionOrgCode == 'ALL'){
					queryParams.divisionOrgCode = '';
				}
				if(queryParams.transDepartmentCode == 'ALL'){
					queryParams.transDepartmentCode = '';
				}
				Ext.apply(operation, {
					params : {
						'billListCheckVo.beginDate' : queryParams.beginDate,
						'billListCheckVo.endDate' :queryParams.endDate,
						'billListCheckVo.divisionOrgCode' : queryParams.divisionOrgCode,
						'billListCheckVo.transDepartmentCode' :queryParams.transDepartmentCode
					}
				});		
		},
		'load' : function( store, records, successful, eOpts){
			var billListCheckStaDto = store.proxy.reader.rawData.billListCheckVo.billListCheckStaDto;
			if(billListCheckStaDto){
				Ext.getCmp('Foss_management_fuelFee_Total_ID').setValue(billListCheckStaDto.fuelFeeTotal==null?'0':billListCheckStaDto.fuelFeeTotal);
				Ext.getCmp('Foss_management_fuelFeeSell_Total_ID').setValue(billListCheckStaDto.fuelFeeSellTotal==null?'0':billListCheckStaDto.fuelFeeSellTotal);
				Ext.getCmp('Foss_management_fuelFeeSell_After_Total_ID').setValue(billListCheckStaDto.fuelFeeSellAfterTotal==null?'0':billListCheckStaDto.fuelFeeSellAfterTotal);
				Ext.getCmp('Foss_management_roadTollFee_Total_ID').setValue(billListCheckStaDto.roadTollFeeTotal==null?'0':billListCheckStaDto.roadTollFeeTotal);
				Ext.getCmp('Foss_management_roadTollFeeSell_Total_ID').setValue(billListCheckStaDto.roadTollFeeSellTotal==null?'0':billListCheckStaDto.roadTollFeeSellTotal);
				Ext.getCmp('Foss_management_roadTollFeeSell_After_Total_ID').setValue(billListCheckStaDto.roadTollFeeSellAfterTotal==null?'0':billListCheckStaDto.roadTollFeeSellAfterTotal);
			}else{
				Ext.getCmp('Foss_management_fuelFee_Total_ID').setValue('0');
				Ext.getCmp('Foss_management_fuelFeeSell_Total_ID').setValue('0');
				Ext.getCmp('Foss_management_fuelFeeSell_After_Total_ID').setValue('0');
				Ext.getCmp('Foss_management_roadTollFee_Total_ID').setValue('0');
				Ext.getCmp('Foss_management_roadTollFeeSell_Total_ID').setValue('0');
				Ext.getCmp('Foss_management_roadTollFeeSell_After_Total_ID').setValue('0');
			}
			
		}
	}
});



// 查询条件
Ext.define('Foss.Management.QueryBillListCheck',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	title:'对账单查询',   // 对账单查询
	frame: true,
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:false
	},
	items:[{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		division:'Y',
		name:'divisionOrgCode',
		fieldLabel:'事业部',   // 事业部
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var divisionOrg = field.getValue();
				if(divisionOrg == ''){
					form.findField('divisionOrgName').setValue('');
					return;
				}
				form.findField('divisionOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'divisionOrgName',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		name:'transDepartmentCode',
		fieldLabel:'车队',   // 车队
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var transDepartment = field.getValue();
				if(transDepartment == ''){
					form.findField('transDepartmentName').setValue('');
					return;
				}
				form.findField('transDepartmentName').setValue(field.rawValue);
			}
		}
	},{
		name:'transDepartmentName',
		hidden:true
	},{
		fieldLabel:'日期',   // 时间
		fieldId:'Foss_Management_QueryFuelConsumption_FuelCreateDateId',
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		time:false,
		fromName: 'beginDate',
		dateRange:31, // 时间跨度不能大于一个月
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-31), 'Y-m-d'),
		toValue: Ext.Date.format(new Date(), 'Y-m-d'),
		toName: 'endDate',
		columnWidth: .5
	},{
		xtype:'button',
		text:'重置',   // 重置
		columnWidth: .1,
		handler: function(){
			var form = this.up('form').getForm();
			if(management.isTransTeam=='Y'){
			}else{
				form.reset();
			}
			
			form.findField('beginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-31), 'Y-m-d'));
			form.findField('endDate').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
		}
	},{
		readOnly:true,
		columnWidth: .7
	},{
		xtype:'button',
		text:'查询',   // 查询
		cls:'yellow_button',
		disabled:management.billListCheckIndex.isPermission('management/billListCheckQueryButton')?false:true,
		hidden:management.billListCheckIndex.isPermission('management/billListCheckQueryButton')?false:true,
		columnWidth: .1,
		handler: function(){
			var queryParams = management.queryBillListCheck.getValues();
			if(Ext.isEmpty(queryParams.beginDate)){
					Ext.Msg.alert('提示','开始时间不能为空');
					return;
				}
			if(Ext.isEmpty(queryParams.endDate)){
					Ext.Msg.alert('提示','截至时间不能为空');
					return;
				}
	
			management.billListCheckIndex.pagingBar.moveFirst();
		}
	},{
		xtype:'button',
		text:'导出',   // 导出
		cls:'yellow_button',
		disabled:management.billListCheckIndex.isPermission('management/billListCheckExportButton')?false:true,
		hidden:management.billListCheckIndex.isPermission('management/billListCheckExportButton')?false:true,
		columnWidth: .1,
		handler: function(){
			var actionUrl=management.realPath('exportBillList.action');
			var exportValues = management.queryBillListCheck.getValues();
			var exportParams ={
								'billListCheckVo.beginDate' : exportValues.beginDate,
								'billListCheckVo.endDate' : exportValues.endDate,
								'billListCheckVo.divisionOrgCode' : exportValues.divisionOrgCode,
								'billListCheckVo.transDepartmentCode' : exportValues.transDepartmentCode	
							 };
			//var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:'正在导出...'});
				//myMask.show();
			
			if(!Ext.fly('downloadBillListCheckFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadBillListCheckFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadBillListCheckFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}]
});


// 展示查询的结果
Ext.define('Foss.Management.ResultBillListCheck',{
	extend:'Ext.grid.Panel',
	title:'查询结果列表',
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
	store: Ext.create('Foss.Management.BillListCheckStore'),
    columns: [{
    	xtype:'actioncolumn',
		header: '操作',
        flex : 0.5,
        disabled:!management.billListCheckIndex.isPermission('management/billListCheckEditButton'),
		hidden:!management.billListCheckIndex.isPermission('management/billListCheckEditButton'),
        items: [{
            iconCls: 'deppon_icons_edit',
            tooltip: '编辑',
            disabled: !management.billListCheckIndex.isPermission('management/billListCheckEditButton'), // 对账单
            hidden: !management.billListCheckIndex.isPermission('management/billListCheckEditButton'), // 对账单
																										// - 编辑
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
				var detailInfoWindow = management.readDetailInfoWindow;
				var editForm = detailInfoWindow.getReadBillListCheckForm();
				editForm.getForm().reset();
				editForm.getForm().findField('billDate').setValue(record.data.billDate);
				editForm.getForm().findField('divisionOrgCode').setValue(record.data.divisionOrgCode);
				editForm.getForm().findField('divisionOrgName').setValue(record.data.divisionOrgName);
				editForm.getForm().findField('transDepartmentName').setValue(record.data.transDepartmentName);
				editForm.getForm().findField('transDepartmentCode').setValue(record.data.transDepartmentCode);
				editForm.getForm().findField('fuelFee').setValue(record.data.fuelFee);
				editForm.getForm().findField('fuelFeeSell').setValue(record.data.fuelFeeSell);
				editForm.getForm().findField('roadTollFee').setValue(record.data.roadTollFee);
				editForm.getForm().findField('roadTollFeeSell').setValue(record.data.roadTollFeeSell);
				editForm.getForm().findField('id').setValue(record.data.id);
				
				management.readDetailInfoWindow.show();
			
            }
        }]
    },{
		header: '日期',   // 日期
		dataIndex: 'billDate' ,
		flex: 1 
    },{
		header: '所属事业部',// 所属事业部
		dataIndex: 'divisionOrgName' ,
		flex: 1 
    },{
		header: '所属车队',   // 所属车队
		dataIndex: 'transDepartmentName' ,
		flex: 1.5 
    },{
		header: '燃油费金额',   // 燃油费金额
		dataIndex: 'fuelFee' ,
		flex: 1 
    },{
		header: '燃油费优惠金额',   // 燃油费优惠金额
		dataIndex: 'fuelFeeSell' ,
		flex: 1 
    },{
		header: '路桥费金额',   // 路桥费金额
		dataIndex: 'roadTollFee' ,
		flex: 1 
    },{
		header: '路桥费优惠金额',   // 路桥费优惠金额
		dataIndex: 'roadTollFeeSell' ,
		flex: 1 
    }],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   fieldLabel:'燃油费金额',
			   id:'Foss_management_fuelFee_Total_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'fuelFeeTotal'
		   },{
			   fieldLabel:'燃油费优惠金额',
			   id:'Foss_management_fuelFeeSell_Total_ID',
			   columnWidth:.3,
			   labelWidth:120,
			   dataIndex: 'fuelFeeSellTotal'
		   },{
			   fieldLabel:'燃油费优惠后金额',
			   id:'Foss_management_fuelFeeSell_After_Total_ID',
			   columnWidth:.4,
			   labelWidth:140,
			   dataIndex: 'fuelFeeSellAfterTotal'
		   },{
			   fieldLabel:'路桥费金额',
			   id:'Foss_management_roadTollFee_Total_ID',
			   columnWidth:.3,
			   labelWidth:100,
			   dataIndex: 'roadTollFeeTotal'
		   },{
			   fieldLabel:'路桥费优惠金额',
			   id:'Foss_management_roadTollFeeSell_Total_ID',
			   columnWidth:.3,
			   labelWidth:120,
			   dataIndex: 'roadTollFeeSellTotal'
		   },{
			   fieldLabel:'路桥费优惠后金额',
			   id:'Foss_management_roadTollFeeSell_After_Total_ID',
			   columnWidth:.4,
			   labelWidth:140,
			   dataIndex: 'roadTollFeeSellAfterTotal'
		   }]
		}],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		// me.store = Ext.create('Foss.Management.FuelConsumptionStore');
		me.tbar = [{
			xtype:'button',
			text:'新增',   // 新增
			disabled: !management.billListCheckIndex.isPermission('management/billListCheckAddButton'), // 对账单
			hidden: !management.billListCheckIndex.isPermission('management/billListCheckAddButton'), // 对账单
																										// - 新增
			handler: function(){
				var newInfoWindow = management.newDetailInfoWindow;
				var newForm = newInfoWindow.getReadBillListCheckForm();
				newForm.getForm().findField('divisionOrgCode').setValue(management.currentCareerCode);
				newForm.getForm().findField('divisionOrgName').setValue(management.currentCareerName);
				newForm.getForm().findField('transDepartmentCode').setValue(management.currentOrgCode);
				newForm.getForm().findField('transDepartmentName').setValue(management.currentOrgName);
				newForm.getForm().findField('fuelFee').setValue('');
				newForm.getForm().findField('fuelFeeSell').setValue('');
				newForm.getForm().findField('roadTollFee').setValue('');
				newForm.getForm().findField('roadTollFeeSell').setValue('');
				newInfoWindow.show();
			}
		},'->',{
			xtype:'button',
			text:'批量导入',   // 批量导入
			disabled: !management.billListCheckIndex.isPermission('management/billListCheckImportButton'), // 对账单
			hidden: !management.billListCheckIndex.isPermission('management/billListCheckImportButton'), // 对账单
																											// -
																											// 批量导入
			handler: function(){
				management.upLoadDetailInfoWindow.show();
			}
		},{
			xtype:'button',
			iconCls:'deppon_icons_downloadOnBtn',
			text:'模板下载',   // 模板下载
			disabled: !management.billListCheckIndex.isPermission('management/billListCheckTempDownButton'), // 对账单
			hidden: !management.billListCheckIndex.isPermission('management/billListCheckTempDownButton'), // 对账单
																											// -
																											// 模板下载
			handler: function(){
				//billListModeFilePath();
				var actionUrl=management.realPath('downFileModel.action');
				
				if(!Ext.fly('downModleFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downModleFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downModleFileForm'),
				method : 'POST',
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('下载失败',result.message);
					//myMask.hide();
				}
					});
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.billListCheckIndex.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	management.queryBillListCheck = Ext.create('Foss.Management.QueryBillListCheck');
	management.resultBillListCheck = Ext.create('Foss.Management.ResultBillListCheck');
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-billListCheckIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [management.queryBillListCheck,management.resultBillListCheck],
		renderTo: 'T_management-billListCheckIndex-body'
	});
	
	management.isTransTeam = 'N';
	
	Ext.Ajax.request({
		url: management.realPath('queryCurrentInfo.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			management.currentOrgCode = result.billListCheckVo.currentOrgCode;
			management.currentOrgName = result.billListCheckVo.currentOrgName;
			management.currentCareerCode = result.billListCheckVo.currentCareerCode;
			management.currentCareerName = result.billListCheckVo.currentCareerName;
			management.isTransTeam = result.billListCheckVo.isTransTeam;
			
			//车队
			if('Y' == management.isTransTeam || 'Y' == management.isTransTeam){
				var divisionSelector = management.queryBillListCheck.getForm().findField('divisionOrgCode');
				divisionSelector.setValue(management.currentCareerName);
				divisionSelector.getStore().load({params:{'commonOrgVo.name' : management.currentCareerName}});
				divisionSelector.setValue(management.currentCareerCode);
				divisionSelector.addCls('readonlyhaveborder');
				divisionSelector.setReadOnly(true);
				
				var transDepartmentSelector = management.queryBillListCheck.getForm().findField('transDepartmentCode');
				transDepartmentSelector.setValue(management.currentOrgName);
				transDepartmentSelector.getStore().load({params:{'commonOrgVo.name' : management.currentOrgName}});
				transDepartmentSelector.setValue(management.currentOrgCode);
				transDepartmentSelector.addCls('readonlyhaveborder');
				transDepartmentSelector.setReadOnly(true);
			}
			
		},
		exception : function(response) {
			management.currentOrgCode = result.billListCheckVo.currentOrgCode;
			management.currentOrgName = result.billListCheckVo.currentOrgName;
			management.currentCareerCode = result.billListCheckVo.currentCareerCode;
			management.currentCareerName = result.billListCheckVo.currentCareerName;
		}
	});
	
});