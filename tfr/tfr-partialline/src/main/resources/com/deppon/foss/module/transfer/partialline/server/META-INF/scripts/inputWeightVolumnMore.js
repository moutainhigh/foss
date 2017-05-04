partialline.getStartDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};
partialline.getEndDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};
//一票多件重量体积model
Ext.define('Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [
		{name : 'waybillNo',type : 'string'},//运单号
		{name : 'serialNo',type : 'string'}, //流水号
		{name : 'goodsQtyTotal',type:'number'},//总件数
		{name : 'weight',type : 'number'},//重量
		{name : 'volumn',type : 'number'}, //体积
		{name : 'modifyUserName',type : 'string'},//修改人
        {name: 'modifyTime', type: 'date', //修改时间
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//跟踪时间
		{name : 'canModify',type:'string'}//是否能修改
	]
});

//定义子母件修改信息基本信息Store
Ext.define('Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoModel',
	autoLoad : true,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : partialline.realPath('queryInputWeightVolumnMoreList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.list',
			//successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.inputWeightVolumnMore.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.handOverBillNos' : queryParams.handoverNo,
						'vo.waybillNos' : queryParams.waybillNo,
						'vo.dto.beginHandOverTime' : queryParams.beginHandOverTime,
						'vo.dto.endHandOverTime' : queryParams.endHandOverTime,
						'vo.dto.beginInStockTime' : queryParams.beginInStockTime,
						'vo.dto.endInStockTime' : queryParams.endInStockTime
					}
				});	
			}
		},
 		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
 		}
	}
});


//一票多件重量体积查询form表单  
Ext.define('Foss.partialline.inputweightvolumnmore.form.searchForm',{
	extend: 'Ext.form.Panel',
	title : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.query'), //查询,
	layout:'column',
	frame: true,
	cls:'autoHeight',
	defaults: {
		margin:'5 10 5 10',
//		anchor: '90%',
		labelWidth:60
	},
	items: [{
		fieldLabel: partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.waybillNo'),//运单号
		columnWidth :.3,
		name : 'waybillNo',
		labelWidth : 60,
		xtype : 'textarea',
		emptyText : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation')
	},{
		fieldLabel: partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.handOverbillNo'),//交接单号
		name: 'handoverNo',
		xtype: 'textarea',
		labelWidth : 60,
		allowBlank:true,
		columnWidth:.3,
		emptyText : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo')
	},{//入库时间
		xtype : 'rangeDateField',
		fieldLabel : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.inStockTime'),//入库时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.inputWeightVolumnMore_inStockTime_RangeDateField_ID',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginInStockTime',
		fromValue : Ext.Date.format(partialline.getStartDate(new Date(),-3),'Y-m-d H:i:s'),
		toValue : Ext.Date.format(partialline.getEndDate(new Date(),0),'Y-m-d H:i:s'),
		toName : 'endInStockTime'
//		allowBlank : false,
//		disallowBlank : true		
	},{
		xtype : 'rangeDateField',
		fieldLabel : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.handOverTime'),//交接时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.inputWeightVolumnMore_handOverTime_RangeDateField_ID',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginHandOverTime',
		fromValue : Ext.Date.format(partialline.getStartDate(new Date(),-3),'Y-m-d H:i:s'),
		toValue : Ext.Date.format(partialline.getEndDate(new Date(),0),'Y-m-d H:i:s'),
		toName : 'endHandOverTime'
//		allowBlank : false,
//		disallowBlank : true	
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.reset'),//重置
			handler: function() {
				var form = this.up('form').getForm();
				form.reset();
			}
		},{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.query'),//查询
			cls:'yellow_button',
			handler: function() {
				var searchParms = this.up('form').getForm().getValues();
				if(!this.up('form').getForm().isValid()){
					return;
				}
			/*	
				if(Ext.isEmpty(searchParms.waybillNo) && Ext.isEmpty(searchParms.handoverNo)) {
					Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.condition'), 'error', 3000); 
					return;
				}
				*/
				// 验证运单号输入的行数
				if (!Ext.isEmpty(searchParms.waybillNo)) {
					var arrayWaybillNo = searchParms.waybillNo.split('\n');
					if (arrayWaybillNo.length > 50) {
						Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'), 'error', 3000); // 
						return;	
					}
					for (var i = 0; i < arrayWaybillNo.length; i++) {
						if (Ext.isEmpty(arrayWaybillNo[i])) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'), 'error', 3000); 
							return;	
						}
					}
				}
				//验证交接单号输入行数
				if (!Ext.isEmpty(searchParms.handoverNo)) {
					var arrayHandOverNo = searchParms.handoverNo.split('\n');
					if (arrayHandOverNo.length > 50) {
						Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo'), 'error', 3000); 
						return;	
					}
					for (var i = 0; i < arrayHandOverNo.length; i++) {
						if (Ext.isEmpty(arrayHandOverNo[i])) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo'), 'error', 3000); // 运单号不能录入空回车
							return;	
						}
					}
				}
				if((!Ext.isEmpty(searchParms.beginInStockTime) && Ext.isEmpty(searchParms.endInStockTime)) 
						|| (Ext.isEmpty(searchParms.beginInStockTime) && !Ext.isEmpty(searchParms.endInStockTime))) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.time.valitation'), 'error', 3000); 
							return;
						}
						
				if((!Ext.isEmpty(searchParms.beginHandOverTime) && Ext.isEmpty(searchParms.endHandOverTime)) 
						|| (Ext.isEmpty(searchParms.beginHandOverTime) && !Ext.isEmpty(searchParms.endHandOverTime))) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.time.valitation'), 'error', 3000); 
							return;
						}
				
				if(this.up('form').getForm().isValid()){
					partialline.inputWeightVolumnMore.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

//一票多件查询结果grid          
Ext.define('Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	autoScroll : true,
	tbar : [{
		xtype : 'button',
		text :  partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.button.label.confirm'),//'确认修改'
		handler : function(){
			var mygrid = this.up('gridpanel');
			var selectWaybill = mygrid.getSelectionModel().getSelection();
			if(!selectWaybill.length > 0){
					Ext.ux.Toast.msg('提示信息', '请选择行！');
					return;
			}
			var list = new Array();
			for (var i = 0; i < selectWaybill.length; i++) {
				var record = selectWaybill[i];
				var waybillNo = record.data.waybillNo;
				var serialNo = record.data.serialNo;
				var weight = record.data.weight;
				var volumn = record.data.volumn;
				if(weight <= 0){
					alert("运单号 "+ waybillNo + "流水号"+serialNo+"的重量小于等于0，请检查！");
					return;
				}
				if(volumn < 0){
					alert("运单号 "+ waybillNo + "流水号"+serialNo+"的体积小于0，请检查！");
					return;
				}
				list.push(record.data);
			}
			var myMask = new Ext.LoadMask(this.up('gridpanel'), {
				msg:"修改中，请稍候..."
			});
			var jsonParam = {vo:{list:list}};
			myMask.show();
			Ext.Ajax.request({
					url:partialline.realPath('saveMPSRecords.action'),
					jsonData: jsonParam,
					success: function(response){
						myMask.hide();
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg("提示", "修改成功");
						partialline.inputWeightVolumnMore.pagingBar.moveFirst();
					},
					exception : function(response) {
						myMask.hide();
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.alert("提示", result.message);
					}
				});		
		
		}
	},
	'->',
	{
		xtype : 'button',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.button.label.import'),//'导入'
		handler : function(){
			var importWindow = Ext.getCmp('Foss_partialline_inputweightvolumnmore_window_importweightvolumnmore_ID');
			if(importWindow == null){
				importWindow=Ext.create('Foss.partialline.inputweightvolumnmore.window.importweightvolumnmore');								
			}
			    importWindow.center().show();
		}
	},
	{
		xtype : 'button',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.button.label.loadTemplate'),//'导入模板下载'
		handler : function(){
			filePath('inputWeightMore.xlsx');
		}
	}],
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.waybillNo')/*'运单号'*/
	},{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumnmore.grid.serialNo')/*'流水号'*/
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumnmore.grid.goodsQtyTotal')/*'总件数'*/
	},{
		header : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.weight'),/*'重量'*/
		dataIndex : 'weight',
		align : 'center',
		width : 120,
		field:{
			xtype: 'textfield',
			editable:true,
			allowBlank:false,
			//regex :/^\d+$/,
			regex :/^\d+(\.\d{1,2})?$/,
			maxLength:30,
			hideTrigger: true
		}
	},{
		header : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.grid.label.volumn'),/*'体积'*/
		dataIndex : 'volumn',
		align : 'center',
		width : 120,
		field:{
			xtype: 'textfield',
			editable:true,
			allowBlank:false,
			//regex :/^\d+$/,
			regex :/^\d+(\.\d{1,2})?$/,
			regexText: '请输入正确的数据类型',
			maxLength:30,
			hideTrigger: true
		}
	},{
		dataIndex : 'modifyUserName',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumnmore.grid.modifyUserName')/*'修改人'*/
	},{
		dataIndex : 'modifyTime',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumnmore.grid.modifyTime')/*'修改时间'*/
	},{
		dataIndex : 'canModify',
		align : 'center',
		width : 120,
		text : 'canModify',
		xtype : 'ellipsiscolumn',
		hidden:true
	}
],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoStore');
		me.plugins = [
	  			me.getEditor()
	  		];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
			sizeList : [['100', 100],['200', 200],['500', 500]]
			})
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			//showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		partialline.inputWeightVolumnMore.pagingBar = me.bbar;
		me.callParent([ cfg ]);
	},
	editor: null,
    getEditor: function(){
    	if(this.editor==null){
    		this.editor = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1,
				listeners:{
					beforeedit : function(editor,  e,  eOpts ) {
						var record = e.record;
						var canModify = record.get('canModify');
						if(canModify == 'Y'){
							return true;
						}else if(canModify == 'N'){
							return false;
						}
					}
				}
			});
    	}
    	return this.editor;
    },
	listeners: {
	}
});
//导入表单         
Ext.define('Foss.partialline.inputweightvolumnmore.form.importWeightVolumnMoreStandard',{
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
			        fieldLabel: '导入文件',//'导入文件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: '浏览',//'浏览',
			      	columnWidth:.85
			    },{
					xtype : 'button',
					columnWidth:.15,
					cls:'cleanBtn',
					text: '清除',//'清除',
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
						text:'取消',//'取消',
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
						text: '导入',//'导入',
						handler: function() {
							var form = this.up('form').getForm();
							var myMask = new Ext.LoadMask(this.up('form'),  {msg:'正在导入，请稍等...'});//"正在导入，请稍等..."
					            if(form.isValid()){
					                Ext.MessageBox.confirm(   
					                        "请确认"  
					                       ,"确定导入修改吗"  
					                       ,function( button,text ){  
					                           if( button == 'yes'){  
				 							    myMask.show();
								                form.submit({
								                    url: partialline.realPath('importMPSRecords.action'),
								                    success: function(form, action) {
								                    	myMask.hide();
								                    	var json =action.result;
							                    		 Ext.MessageBox.alert('导入成功');
								                    },
													exception : function(form, action) {
														myMask.hide();
								        				json=action.result;
								        				var msg=json.message;
								        				while(msg.indexOf(';')>-1){
								        					msg=msg.replace(';', "\r\n");
								        				}
								        				Ext.create('Ext.window.Window', {
								        				    title:partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'),
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
								        				alert(msg);
								        				Ext.create('Ext.window.Window', {
								        				    title: partialline.inputWeightVolumnMore.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'),
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
					                   );  
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
Ext.define('Foss.partialline.inputweightvolumnmore.window.importweightvolumnmore', {
	extend:'Ext.window.Window',
	id:'Foss_partialline_inputweightvolumnmore_window_importweightvolumnmore_ID',
	title: '一票多件重量体积导入',//'重量体积导入',
	modal:true,
	closeAction:'hide',
	width: 550,
	height:150,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	listeners:{
		hide:function(comp, eOpts){
			this.down('form').getForm().findField('uploadFile').reset();
		}
	},
	items:[Ext.create('Foss.partialline.inputweightvolumnmore.form.importWeightVolumnMoreStandard')]
});
Ext.onReady(function() {
	var searchForm=Ext.create('Foss.partialline.inputweightvolumnmore.form.searchForm');
	var searchResult=Ext.create('Foss.partialline.inputweightvolumnmore.InputWeightVolumnMoreInfoGrid');
	partialline.inputWeightVolumnMore.queryForm=searchForm;
	partialline.inputWeightVolumnMore.searchResultGrid=searchResult;
	Ext.create('Ext.panel.Panel', {
	id : 'T_partialline-inputWeightVolumnMoreIndex_content',
	cls : "panelContentNToolbar",
	bodyCls : 'panelContent-body',
	layout : 'auto',
	items : [ searchForm,searchResult],
	renderTo : 'T_partialline-inputWeightVolumnMoreIndex-body'
	});
});