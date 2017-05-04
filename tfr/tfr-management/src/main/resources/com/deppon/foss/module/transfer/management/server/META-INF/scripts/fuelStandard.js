//定义车辆油耗信息的model
Ext.define('Foss.Management.FuelStandardModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'id', type: 'string'},
	        {name:'fuelTime', type:'date',
				convert: function(v){
					if(v!=null){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d');
					}
			}},
	        {name: 'vehicleNo', type: 'string'},
	        {name: 'fuelStandard', type: 'string'},
	        {name: 'createUser', type: 'string'},
	        {name:'createDate', type:'date',
				convert: function(v){
					if(v!=null){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d H:i:s');
					}
				}}]
});

//定义车辆油耗信息的store
Ext.define('Foss.Management.FuelStandardStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelStandardModel',
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: management.realPath('queryFuelStandard.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'fuelStandardVo.fuelStandardDtoList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = management.fuelStandardInfo;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				if(Ext.isEmpty(queryParams.vehicleNo) || Ext.isEmpty(queryParams.fuelTime)) {
					return;
				}
				queryParams.fuelTime =  new Date(queryParams.fuelTime);
				
				Ext.apply(operation, {
					params : {
						'fuelStandardVo.fuelStandardExcelDto.vehicleNo' : queryParams.vehicleNo,
						'fuelStandardVo.fuelStandardExcelDto.fuelTime' : queryParams.fuelTime
					}
				});	
			}
		}
	}
});

//查看油耗标准
Ext.define('Foss.Management.form.ReadFuelStandard',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.title'),   //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true
	},
	layout:'column',
	items: [{
		name: 'fuelTime',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.month'),   //日期
		columnWidth: .5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		//xtype:'commonowntruckselector',
		name:'vehicleNo',
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		name:'fuelStandard',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.return'),   //返回
		columnWidth:.1,
		handler: function(){
			management.fuelStandardReadWindows.close();
		}
	}]
});
//查看窗口
Ext.define('Foss.Management.window.FuelStandardReadWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	//frame: true,
	closeAction: 'hide',
	width:600,
	bodyCls: 'autoHeight',
	resizable:false,
	readFuelStandardForm : null,
	getReadFuelStandardForm: function(){
		if(this.readFuelStandardForm==null){
			this.readFuelStandardForm = Ext.create('Foss.Management.form.ReadFuelStandard');
		}
		//management.fuelStandardReadWindows = this.readFuelStandardForm;
		return this.readFuelStandardForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadFuelStandardForm()
				];
		me.callParent([cfg]);
	}
});
management.fuelStandardReadWindows = Ext.create('Foss.Management.window.FuelStandardReadWindows');
//编辑界面
Ext.define('Foss.Management.form.EditFuelStandard',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		//allowBlank:false,
		labelWidth: 85,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items: [{
		//xtyp:'textfield',
		name: 'id',
		readOnly:true,
		hidden: true
		//fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.month'),   //日期
		//columnWidth: .5
	},/*{
		readOnly:true,
		columnWidth: .5
	},*/{
		xtype: 'datefield',
		name: 'fuelTime',
		editable: false,
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureDate'),   //发车日期
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth: .5
	},{
		readOnly:true,
		columnWidth: .5
	},{

		xtype:'commonowntruckselector',
		name:'vehicleNo',
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		readOnly:false,
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype: 'numberfield',
		name:'fuelStandard',
		
		minValue:0,
		maxLength:8,
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准
		columnWidth:.5
	},{
		readOnly:true,
		columnWidth: .5
	},{
		readOnly:true,
		columnWidth: .8
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.save'),   //保存
		columnWidth:.1,
		handler: function(){
			var btnSave = this;
			var win = management.editFuelStandardWindow;
			var form = win.getEditFuelStandardForm();
			if(form.getForm().isValid()) {
				var values = form.getValues();
				
				btnSave.setDisabled(true);		
				
				var array = {fuelStandardVo:{'fuelStandardExcelDto':values}};
				Ext.Ajax.request({
					url:management.realPath('saveOrUpdateFuelStandard.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'), management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.saveSuccess'), 'ok', 1000);   //提示保存成功
						
						management.editFuelStandardWindow.close();
						//查询条件都不为空时，做刷新
						if(!Ext.isEmpty(management.fuelStandardInfo.getValues().vehicleNo) && !Ext.isEmpty(management.fuelStandardInfo.getValues().fuelTime)) {
							management.fuelStandardPagingBar.moveFirst();
						}
						btnSave.setDisabled(false);
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
						btnSave.setDisabled(false);
					}
				});
			}
		}
	},,{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.cancel'),   //取消
		columnWidth:.1,
		handler: function(){
			management.editFuelStandardWindow.close();
		}
	}]
});
//编辑窗口
Ext.define('Foss.Management.window.FuelStandardEditWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:600,
	bodyCls: 'autoHeight',
	resizable:false,
	edittFuelStandardForm : null,
	getEditFuelStandardForm: function(){
		if(this.edittFuelStandardForm==null){
			this.edittFuelStandardForm = Ext.create('Foss.Management.form.EditFuelStandard');
		}
		//management.fuelStandardReadWindows = this.readFuelStandardForm;
		return this.edittFuelStandardForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditFuelStandardForm()
				];
		me.callParent([cfg]);
	}
});

management.editFuelStandardWindow = Ext.create('Foss.Management.window.FuelStandardEditWindows');

//导入表单
Ext.define('Foss.Management.form.ImportFuelStandard',{
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
					xtype: 'datefield',
					format:'Y-m',
					fieldLabel: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.month'),//'导入月份',
					name: 'importMonth',
					disabled:true,
					value: new Date(),
					format: 'Y-m',
					allowBlank:false,
					columnWidth:.5
				},{
			        xtype: 'filefield',
			        name: 'uploadFile',
			        readOnly:false,
			        buttonOnly:false,
			        fieldLabel: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.lable.uploadFile'),//'导入文件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.lable.scan'),//'浏览',
			      	columnWidth:.85
			    },{
					xtype : 'button',
					columnWidth:.15,
					cls:'cleanBtn',
					text: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.clear'),//'清除',
					handler: function() {
						this.up('form').getForm().findField('uploadFile').reset();						
					}
				},{
			        xtype: 'container',
			        columnWidth:1,
					layout:'column',
					defaults: {
						margin:'5 0 5 0'
					},
			        items: [{
						xtype : 'button',
						columnWidth:.15,
						text: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.cancel'),//'取消',
						handler: function() {
							
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.button.import'),//'导入',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					            	var myMask = new Ext.LoadMask(this.up('form'),  {msg:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.tip.waiting')});//"正在导入，请稍等..."
		 							    myMask.show();
					                form.submit({
					                    url: management.realPath('importFuelStandardInfo.action'),
					                    success: function(form, action) {
					                    	myMask.hide();
					                    	//查询条件都为空时，不刷新
					                    	if(!Ext.isEmpty(management.fuelStandardInfo.getValues().vehicleNo) 
					                    			&& !Ext.isEmpty(management.fuelStandardInfo.getValues().fuelTime)) {
					                    		management.fuelStandardPagingBar.moveFirst();
					                    	}
					                    	var json =action.result;
					                        Ext.MessageBox.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.tip.title'),
					                        '导入成功'/*+json.fuelStandardVo.importTotalCount+'条'*/);
					                    },
										exception : function(form, action) {
											myMask.hide();
					        				json=action.result;
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}			        				
					        				Ext.create('Ext.window.Window', {
					        				    title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.tip.title'),
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
					        			failure: function(form, action){
					        				myMask.hide();
					        				json=action.result;					        				
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}		
					        				Ext.create('Ext.window.Window', {
					        				    title: management.fuelConsumptionIndex.i18n('foss.scheduling.ShortSchedule.tip.title'),
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
					}]
				    }
				],
		dockedItems: [],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
});
//导入窗口
Ext.define('Foss.Management.window.ImportFuelStandard', {
	extend:'Ext.window.Window',
	id:'Foss_management_window_importFuelStandard_ID',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.label.dataexport'),//'车辆油耗标准导入',
	modal:true,
	closeAction:'hide',
	width: 550,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	listeners:{
		hide:function(comp, eOpts){
			this.down('form').getForm().findField('uploadFile').reset();
			this.down('form').getForm().findField('importMonth').reset();
		}
	},
	items:[Ext.create('Foss.Management.form.ImportFuelStandard')]
});


//油耗标准查询panel
Ext.define('Foss.Management.FuelStandardInfo',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	title:management.fuelConsumptionIndex.i18n('foss.management.queryFuelConsumption.title'),   //油耗查询
	frame: true,
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:false
	},
	items:[{
		xtype:'commonowntruckselector',
		name:'vehicleNo',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		columnWidth:.25
	},{
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.month'),   //月份
		fieldId:'Foss_Management_FuelStandardConsumption_FuelStandardMonthId',
		xtype: 'datefield',
		name: 'fuelTime',
		editable: false,
		allowBlank: false,
		value: Ext.Date.format(new Date(), 'Y-m'),
		format : 'Y-m',
		columnWidth: .25
	},{
		readOnly:true,
		columnWidth: .5
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.reset'),   //重置
		columnWidth: .1,
		handler: function(){
			var form = this.up('form').getForm();
			form.reset();
			form.findField('fuelTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m'));
		}
	},{
		readOnly:true,
		columnWidth: .67
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.button.dataexport'),   //数据导入
		cls:'yellow_button',
		//hidden:management.fuelConsumptionIndex.isPermission('management/fuelConsumptionExportExcelButton')?false:true,
		columnWidth: .13,
		handler: function(){
			//弹出窗口，供用户选择年月
			var importWindow = Ext.getCmp('Foss_management_window_importFuelStandard_ID');
			if(importWindow == null){
				importWindow=Ext.create('Foss.Management.window.ImportFuelStandard');								
			}
			importWindow.down('form').getForm().findField('importMonth').setReadOnly(false);
			importWindow.center().show();
		
		}
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.search'),   //查询
		cls:'yellow_button',
		//hidden:management.fuelConsumptionIndex.isPermission('management/queryFuelConsumptionButton')?false:true,
		columnWidth: .1,
		handler: function(){
			if(this.up('form').getForm().isValid()) {
				management.fuelStandardPagingBar.moveFirst();
			}
		}
	}]
});


//油耗详细信息列表
Ext.define('Foss.Management.FuelStandardDetailInfo',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumption.title'),   //油耗档案列表
	height: 500,
	autoScroll:true,
	columnLines: true,
    frame: true,
    forceFit: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    width:'100%',
    columns:[{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.operator'),   //操作
    	xtype:'actioncolumn',
		flex: 0.3,
		items:[{
            iconCls: 'deppon_icons_delete',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
            handler: function(grid, rowIndex, colIndex){
            	Ext.Msg.show({
            		title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
					msg:management.fuelConsumptionIndex.i18n('foss.management.fuelDetailFuelCard.msg.confirmDelete'),   //确认删除这条记录吗?
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'yes'){
							var id = grid.getStore().getAt(rowIndex).data.id;
							var array = {fuelStandardVo: {fuelStandardExcelDto :{id:id}}};
							Ext.Ajax.request({
								url:management.realPath('deleteFuelStandard.action'),
			        			jsonData:array,
			        			success : function(response) {
									Ext.ux.Toast.msg(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'), management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.deleteSuccess'), 'ok', 1000);  //提示删除成功
									if(!Ext.isEmpty(management.fuelStandardInfo.getValues().vehicleNo) 
			                    			&& !Ext.isEmpty(management.fuelStandardInfo.getValues().fuelTime)) {
										management.fuelStandardPagingBar.moveFirst();
									}
			        			},
			        			exception : function(response) {
			        				var json = Ext.decode(response.responseText);
			        				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.failure'),json.message);
			        			}
			        		});
						}
					}
            	});
            
            }
        },{
        	 iconCls: 'deppon_icons_showdetail',
             tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.info'),   //查看
             handler: function(grid, rowIndex, colIndex){
            	 var readWindow = management.fuelStandardReadWindows;
            	 var readForm = readWindow.getReadFuelStandardForm();
            	 //清空数据
            	 readForm.getForm().reset();
            	 //加载数据
            	 var record = grid.getStore().getAt(rowIndex);
            	 readForm.getForm().loadRecord(record);
            	 readWindow.show();
             }
        },{
        	iconCls: 'deppon_icons_edit',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.edit'),   //'编辑',
         handler: function(grid, rowIndex, colIndex){
        	 var record = grid.getStore().getAt(rowIndex);
        	 var  win = management.editFuelStandardWindow;
        	 var form =	win.getEditFuelStandardForm().getForm();
        	 form.reset();
        	 form.loadRecord(record);
        	 form.findField('fuelTime').setReadOnly(true);
        	 form.findField('vehicleNo').setReadOnly(true);
			 win.show();
         }
        }]
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.month'),   //月份
		dataIndex: 'fuelTime' ,
		eidtable: false,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号 
		dataIndex: 'vehicleNo' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准 
		dataIndex: 'fuelStandard' ,
		flex: 1
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelStandardStore');
		//me.callParent([cfg]);
		me.tbar = [{
			xtype:'button',
			text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.add'),   //新增
			//hidden: !management.fuelConsumptionIndex.isPermission('management/saveOrUpdateFuelConsumptionButton'), //油耗 - 新增
			handler: function(){
				var  win = management.editFuelStandardWindow;
				var form = win.getEditFuelStandardForm().getForm();
				form.reset();
				form.findField('fuelTime').setReadOnly(false);
	        	form.findField('vehicleNo').setReadOnly(false);
				win.show();
			}
		},'->',{
			xtype:'button',
			text:'导入模版下载',
			iconCls:'deppon_icons_downloadOnBtn',
			handler: function(){
				filePath();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.fuelStandardPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	//Ext.QuickTips.init();
	management.fuelStandardInfo = Ext.create('Foss.Management.FuelStandardInfo');
	management.fuelStandardDetailInfo = Ext.create('Foss.Management.FuelStandardDetailInfo');
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-fuelStandardIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [management.fuelStandardInfo,management.fuelStandardDetailInfo],
		renderTo: 'T_management-fuelStandardIndex-body'
	});
});