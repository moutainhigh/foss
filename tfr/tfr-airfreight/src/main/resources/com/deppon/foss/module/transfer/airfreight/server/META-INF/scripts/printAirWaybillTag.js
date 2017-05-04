//定义打印标签的model
Ext.define('Foss.airfreight.printAirWaybillTag.printAirWaybillModel',{
	extend:'Ext.data.Model',
	fields:[{name:'airWaybillNo', type:'string'},
	        {name:'goodsQty', type:'string'},
	        {name:'billingWeight', type:'string'},
	        {name:'deptRegionName', type:'string'},
	        {name:'arrvRegionName', type:'string'},
	        {name:'grossWeight', type:'string'}
	        ]
});

//定义航空公司信息的model
Ext.define('Foss.airfreight.printAirWaybillTag.queryAirlinesModel',{
	extend:'Ext.data.Model',
	fields:[{name:'code', type:'string'},
	        {name:'simpleName', type:'string'},
	        {name:'name', type:'string'},
	        {name:'logo', type:'string'},
	        {name:'prifixName', type:'string'}
	        ]
});

//定义查询航空公司信息的store
Ext.define('Foss.airfreight.printAirWaybillTag.queryAirlinesStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.airfreight.printAirWaybillTag.queryAirlinesModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAllAirlines.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'printAirWaybillTagVO.airlinesEntityList'
		}
	}
});
//打印信息panel
Ext.define('Foss.airfreight.printAirWaybillTag.printAirWaybill',{
	extend:'Ext.form.Panel',
	height:235,
	width:400,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		readOnly:true,
		margin: '5 5 5 5'
	},
	layout:'column',
	items:[{
		name:'name',
		displayField:'code',
		readOnly:false,
		valueField:'name',
		editable:false,
		store:Ext.create('Foss.airfreight.printAirWaybillTag.queryAirlinesStore'),
		xtype:'combobox',
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.airWaybillNo'),     //正单号
		labelWidth:60,
		columnWidth:.40,
		listeners:{
			change:function(newValue,oldValue,eOpts){
				var record = this.findRecordByDisplay(this.getRawValue());
				if(record != null){
					airfreight.printAirWaybill.items.items[1].setValue(record.data.prifixName);
					airfreight.printAirWaybill.items.items[2].setValue(record.data.code);
					airfreight.printAirWaybillLogo.items.items[0].setSrc(record.data.logo);
					var form = this.up('form').getForm();
					var waybillNo = airfreight.printAirWaybill.getForm().findField('airWaybillNo').getValue(); 
					var airlineTwoletter = newValue.rawValue;
					if(waybillNo == null || waybillNo == "" || airlineTwoletter == null || airlineTwoletter == ""){
						var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
						form.loadRecord(record);
						return;
					}else{
						var array = {printAirWaybillTagVO : {airWayBillDto : {airWaybillNo : waybillNo,airlineTwoletter:airlineTwoletter}}};
						Ext.Ajax.request({
							url : airfreight.realPath('queryWaybillInfo.action'),
							jsonData:array,
							success : function(response) {
								var json = Ext.decode(response.responseText);
			    				var data = new Ext.data.reader.Json(json.printAirWaybillTagVO.airWaybillEntity);
			    				var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
			    				record.data = data;
			    				airfreight.printAirWaybill.getForm().findField('printSize').setValue(record.data.goodsQty);
			    				form.loadRecord(record);
							},
							exception : function(response) {
			    				var json = Ext.decode(response.responseText);
			    				var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
								form.loadRecord(record);
								form.findField('airWaybillNo').setValue(waybillNo);
			    				airfreight.printAirWaybill.getForm().findField('printSize').setValue(0);
			    				Ext.Msg.alert("失败",json.message);
			    			} 
						});
					}
				}
			}
		}
	},{
		name:'prifixName',
		columnWidth:.20
	},{
		name:'airCode',
		hidden:true
	},{
		readOnly:true,
		value:'——',
		columnWidth:.10
	},{
		name:'grossWeight',
		hidden:true,
	},{
		name:'airWaybillNo',
		columnWidth:.30,
		readOnly:false,
		listeners:{
			blur:function(text,op){
				var form = this.up('form').getForm();
				var waybillNo = text.getValue(); 
				var airlineTwoletter = airfreight.printAirWaybill.getForm().findField('name').getRawValue();
				if(waybillNo == null || waybillNo == "" || airlineTwoletter == null || airlineTwoletter == ""){
					var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
					form.loadRecord(record);
					form.findField('airWaybillNo').setValue(waybillNo);
					return;
				}else{
					var array = {printAirWaybillTagVO : {airWayBillDto : {airWaybillNo : waybillNo,airlineTwoletter:airlineTwoletter}}};
					Ext.Ajax.request({
						url : airfreight.realPath('queryWaybillInfo.action'),
						jsonData:array,
						success : function(response) {
							var json = Ext.decode(response.responseText);
		    				var data = new Ext.data.reader.Json(json.printAirWaybillTagVO.airWaybillEntity);
		    				var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
		    				record.data = data;
		    				airfreight.printAirWaybill.getForm().findField('printSize').setValue(record.data.goodsQty);
		    				form.loadRecord(record);
						},
						exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var record = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillModel');
							form.loadRecord(record);
							form.findField('airWaybillNo').setValue(waybillNo);
		    				airfreight.printAirWaybill.getForm().findField('printSize').setValue(0);
		    				Ext.Msg.alert("失败",json.message);
		    			} 
					});
				}
			}
		}
	},{
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.goodsQty'),     //件数
		name:'goodsQty',
		labelWidth:60,
		columnWidth:.5
	},{
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.billingWeight'),     //重量
		name:'billingWeight',
		labelWidth:60,
		columnWidth:.5
	},{
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.deptRegionName'),     //始发站
		name:'deptRegionName',
		labelWidth:60,
		columnWidth:.5
	},{
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.arrvRegionName'),     //目的站
		name:'arrvRegionName',
		labelWidth:60,
		columnWidth:.5
	},{
		name:'printSize',
		xtype:'numberfield',
		fieldLabel:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.label.printSize'),     //打印数量
		labelWidth:60,
		readOnly:false,
		allowDecimals: false,
		minValue: 1,
		maxValue: 999,
		columnWidth:1
	},{
		xtype:'button',
		text:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.button.print'),     //打印
		width:80,
		columnWidth:.30,
		handler: function() {
			var form = this.up('form').getForm();
			if(form.isValid()){
				var values = form.getValues();
				if(values.goodsQty != values.printSize){
					Ext.Msg.show({
	            		title:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.msg.message'),     //提示
						msg:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.msg.confirmPrint'),     //件数与打印数量不一致，确认打印吗？
						buttons:Ext.Msg.YESNO,
						icon: Ext.Msg.QUESTION, 
						fn : function(btn){
							if(btn == 'no'){
								return;
							}else{
								Ext.data.JsonP.request({
							        url: 'http://localhost:8077/print',
							        callbackKey: 'callback',
								    params: {
								    	lblprtworker:"AirWayBillLabelPrintWorker",
										cnpycode:values.airCode,
										cnpyname:values.name,
								    	awbno : values.airWaybillNo,
								    	pieces : values.goodsQty,
								    	goodsweight : values.billingWeight,
								    	pcsweight : '   ',
								    	from : values.deptRegionName,
								    	to : values.arrvRegionName,
								    	size : values.printSize
								    },
									callback: function() {
							 			//回调函数，不管请求成功与否都执行
							 			//alert("callback");
									},   	    
								    success: function(result, request) {
								        //var text = response.responseText;
										//alert(text);
										var ret_code = result.data.code;
										alert(result.data.msg);
								    },
								    failure : function (result, request) {
								    	//var text = response.responseText;
								    	//alert(text);
								    	var ret_code = result.data.code;
										alert(result.data.msg);
								    }
								});
							}
						}
	            	});
				}else{
					Ext.data.JsonP.request({
				        url: 'http://localhost:8077/print',
				        callbackKey: 'callback',
					    params: {
					    	lblprtworker:"AirWayBillLabelPrintWorker",
							cnpycode:values.airCode,
							cnpyname:values.name,
					    	awbno : values.airWaybillNo,
					    	pieces : values.goodsQty,
					    	goodsweight : values.billingWeight,
					    	pcsweight : '   ',
					    	from : values.deptRegionName,
					    	to : values.arrvRegionName,
					    	size : values.printSize
					    },
						callback: function() {
				 			//回调函数，不管请求成功与否都执行
				 			//alert("callback");
						},   	    
					    success: function(result, request) {
					        //var text = response.responseText;
							//alert(text);
							var ret_code = result.data.code;
							alert(result.data.msg);
					    },
					    failure : function (result, request) {
					    	//var text = response.responseText;
					    	//alert(text);
					    	var ret_code = result.data.code;
							alert(result.data.msg);
					    }
					});
				}
			}
		}
	}],
	listeners : {
		render : function(panel,opt){
			var currentDept = FossUserContext.getCurrentDept();
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			//打印
			if(!airfreight.printAirWaybillTag.isPermission('airfreight/printAirwaybillLabelButton')){
				btns[btns.length-1].disable();
			}
		}
	}
});

//展示logo
Ext.define("Foss.airfreight.printAirWaybillTag.printAirWaybillLogo",{
	extend:'Ext.form.Panel',
	layout:'column',
	height:140,
	width:120,
	bodyStyle:'padding:5px 5px 0',
	defaults: {
		margin: '5 5 5 5'
	},
	items:[{
		xtype:'image',
		width:120,
		height:140,
		src:''
	}]
});

airfreight.printAirWaybill = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybill');
airfreight.printAirWaybillLogo = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillLogo');

//主panel
Ext.define('Foss.airfreight.printAirWaybillTag.printAirWaybillAll',{
	extend:'Ext.form.Panel',
	width:570,
	height:250,
	frame: true,
	layout:'column',
	title:airfreight.printAirWaybillTag.i18n('foss.airfreight.printAirWaybill.title'),     //航空正单标签
	items:[
		airfreight.printAirWaybill,
		airfreight.printAirWaybillLogo
	]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var printPanel = Ext.create('Foss.airfreight.printAirWaybillTag.printAirWaybillAll');
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-printAirWaybillTag_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout:'auto',
		items: [printPanel],
		renderTo: 'T_airfreight-printAirWaybillTag-body'
	});
});