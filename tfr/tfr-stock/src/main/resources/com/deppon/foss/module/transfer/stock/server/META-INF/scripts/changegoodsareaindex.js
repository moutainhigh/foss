Ext.define('Foss.stock.changegoodsareaindex.Store',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
      	{"valueName": "全部", "valueCode": "0"},
	    {"valueName": "未修改", "valueCode": "1"},
	    {"valueName": "已修改", "valueCode": "2"},
	    {"valueName": "已作废", "valueCode": "3"}
		]
});


//库区编号修改页面查询条件
Ext.define('Foss.stock.changegoodsareaindex.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '查询条件',
//	title : stock.changegoodsareaindex.i18n('foss.stock.query.changegoodsareaindex'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: '部门',
//		fieldLabel: stock.prioritygoods.i18n('foss.stock.org'),
		name: 'org_name',
		hidden: true
	},{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'org_code',
		hidden: true
	},{
		xtype: 'rangeDateField',
		fieldLabel: '申请时间',
//		fieldLabel: stock.changegoodsareaindex.i18n('foss.stock.applicationtime'),
		fieldId: 'Foss_stock_changegoodsareaindex_QueryForm_ApplicationTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'createtime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'),		
		toName: 'finishtime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		blankText:'字段不能为空',
		//blankText: stock.changegoodsareaindex.i18n('foss.stock.notnull'),
		columnWidth: .5
	},{
		xtype: 'combo',
		fieldLabel: '操作状态',
//		fieldLabel: stock.changegoodsareaindex.i18n('foss.stock.changegoodsareaindexstate'),
		name: 'state',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: '0',
		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.changegoodsareaindex.Store')
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: '重置',
//			text: stock.changegoodsareaindex.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){
				stock.changegoodsareaindex.queryform.getForm().reset();
				stock.changegoodsareaindex.queryform.getForm().findField('createtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'));
				stock.changegoodsareaindex.queryform.getForm().findField('finishtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
		//		stock.prioritygoods.queryform.getForm().findField('org_code').setValue(stock.prioritygoods.orgCode);
		//		stock.prioritygoods.queryform.getForm().findField('org_name').setValue(stock.prioritygoods.orgName);
				
			}
		},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
		},{
			text: '查询',        
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				var startTime = stock.changegoodsareaindex.queryform.getValues().createtime;
				var endTime = stock.changegoodsareaindex.queryform.getValues().finishtime;
				var difTime = 0;
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 31){
				Ext.MessageBox.alert('警告', '查询时间跨度不能超过31天'); //查询时间跨度不能超过31天
				//	Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.span.limit')); //“任务创建时间”跨度不能超过7天
					return;
				}
				if(stock.changegoodsareaindex.queryform.getForm().isValid()){
					stock.changegoodsareaindex.pagingBar.moveFirst();       //pagingBar   分页用的
				}
				
			}
		}]
	}
	],
	
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询结果
Ext.define('Foss.stock.changegoodsareaindex.WaybillGrid', {
	extend:'Ext.grid.Panel',
	height: 450,
	autoScroll:true,
	columnLines: true,
    frame: true,
//    plugins: [{
//        ptype: 'rowexpander',
//        pluginId: 'Foss.stock.changegoodsareaindex.WaybillGrid_Plugin_ID',
//		rowsExpander: false,
//		rowBodyElement : 'Foss.stock.changegoodsareaindex.GoodsGrid'
//	}],
	columns: [{
			header: 'ID', 
	//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'id',
			width : 100,
			align: 'center',
			hidden: true
			},{
		header: '操作', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'operate',
			width : 160,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
  			tooltip:'修改',
            iconCls:'deppon_icons_edit',
            handler: function(grid, rowIndex, colIndex) {
            	//var oldCode = grid.store.data.items[rowIndex].data.oldCode;
            	var id = grid.store.data.items[rowIndex].data.id;
            	//org_code和id 要传过去,因为点击确定的时候要插入到数据库中去
            	var org_code = grid.store.data.items[rowIndex].data.org_code;
            	var org_name = grid.store.data.items[rowIndex].data.org_name;
            	var remarks = grid.store.data.items[rowIndex].data.remarks;
            	var state = grid.store.data.items[rowIndex].data.state;
            	if(state != '未修改'){
            		Ext.ux.Toast.msg('提示', '只能对未修改的数据进行修改！', 'error', 2000);
            		return;
            	}
            	//去创建第二层页面         转运场名称 只读,备注带出 可修改,  下面的grid用新增的那个   注意查询action 和新增的那个页面的查询action不一样
            	stock.changegoodsareaindex.modifyWindow = Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaModifyWindow();
				stock.changegoodsareaindex.modifyWindow.show();	
				stock.changegoodsareaindex.modifyOrgForm.getForm().findField('org_name').setValue(org_name);
				stock.changegoodsareaindex.modifyOrgForm.getForm().findField('change_goodsarea_area_id').setValue(id);
				stock.changegoodsareaindex.modifyOrgForm.getForm().findField('org_code').setValue(org_code);
				stock.changegoodsareaindex.modifyOrgForm.getForm().findField('remarks').setValue(remarks);
            }
			},{
  			tooltip:'查看',
            iconCls:'deppon_icons_showdetail',
            handler: function(grid, rowIndex, colIndex) {
            	var id = grid.store.data.items[rowIndex].data.id;
            	var org_code = grid.store.data.items[rowIndex].data.org_code;
            	
            	var org_name = grid.store.data.items[rowIndex].data.org_name;
            	var remarks = grid.store.data.items[rowIndex].data.remarks;
            	//去创建第二层页面         转运场名称 只读,备注带出 可修改,  下面的grid用新增的那个   注意查询action 和新增的那个页面的查询action不一样
            	stock.changegoodsareaindex.LookWindow = Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaLookWindow();
				stock.changegoodsareaindex.LookWindow.show();
				stock.changegoodsareaindex.lookOrgForm.getForm().findField('org_name').setValue(org_name);
				stock.changegoodsareaindex.lookOrgForm.getForm().findField('change_goodsarea_area_id').setValue(id);
				stock.changegoodsareaindex.lookOrgForm.getForm().findField('org_code').setValue(org_code);
				stock.changegoodsareaindex.lookOrgForm.getForm().findField('remarks').setValue(remarks);
				stock.changegoodsareaindex.querygoodsareacodelook.store.load();
            }
		}],
		},{ 
			header: '申请时间', 
//			header: stock.prioritygoods.i18n('foss.stock.applicationTime'), 
			dataIndex: 'applicant_time',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 160,
			align: 'center'
		},{ 
			header: '转运场名称', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'org_name',
			width : 160,
			align: 'center'
		},{ 
			header: '转运场code', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'org_code',
			width : 160,
			align: 'center',
			hidden: true
		},{ 
			header: '操作状态', 
//			header: stock.prioritygoods.i18n('foss.stock.checkState'),
			dataIndex: 'state',
			width : 160,
			align: 'center'
		},{ 
			header: '申请人',
//			header: stock.prioritygoods.i18n('foss.stock.applicant'),
			dataIndex: 'applicant_name',
			width : 160,
			align: 'center'
		},{ 
			header: '申请工号',
//			header: stock.prioritygoods.i18n('foss.stock.applicant'),
			dataIndex: 'applicant_code',
			width : 160,
			align: 'center',
			hidden: true
		},{ 
			header: '备注', 
//			header: stock.prioritygoods.i18n('foss.stock.comment'),
			dataIndex: 'remarks',
			width : 160,
			align: 'center'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.changegoodsareaindex.WaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '新增',
				gridContainer: this,
				handler: function() {
					stock.changegoodsareaindex.addWindow = Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaAddWindow();
					stock.changegoodsareaindex.addWindow.show();
					//将页面上的数据获取到
				}
			},{
				xtype: 'button',
				text: '作废',
				gridContainer: this,
				handler: function() {
					var goodsMapList = stock.changegoodsareaindex.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要作废的申请！', 'error', 2000);
						return;
					}else if(goodsMapList.length>=2){
						Ext.ux.Toast.msg('提示', '一次只能作废一条申请！', 'error', 2000);
						return;
					}else if(goodsMapList[0].getValues()[0].data.state != '未修改'){
						Ext.ux.Toast.msg('提示', '只能作废未修改的申请！', 'error', 2000);
						return;
					}
					Ext.MessageBox.confirm('提示', '确认将选中的数据作废吗？',function(btn){
						if(btn == 'yes'){
							var jsonParam = {stockVO: {changeGoodsAreaEntityId:stock.changegoodsareaindex.waybillGoodsMap.getValues()[0].getValues()[0].data.id}};
	//						Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								//作废action   invalidate
			    			url: stock.realPath('invalidateChangeGoodsArea.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    			//	var result = Ext.decode(response.responseText);
			    				stock.changegoodsareaindex.waybillGoodsMap.clear();
			    				stock.changegoodsareaindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '作废成功！', 'ok', 3000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('作废失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});	
				}
			},{
				xtype: 'container',
				columnWidth:.80,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '确认修改',
				gridContainer: this,
				handler: function(item) {
					var goodsMapList = stock.changegoodsareaindex.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要确认修改的申请！', 'error', 2000);
						return;
					}else if(goodsMapList.length>=2){
						Ext.ux.Toast.msg('提示', '一次只能确认修改一条申请！', 'error', 2000);
						return;
					}else if(goodsMapList[0].getValues()[0].data.state != '未修改'){
						Ext.ux.Toast.msg('提示', '只能对未修改的申请点击确认修改！', 'error', 2000);
						return;
					}
					
					Ext.MessageBox.confirm('提示', '确认将选中的数据确认修改吗？',function(btn){
						if(btn == 'yes'){
							//{stockVO: {changeGoodsAreaEntityId:stock.changegoodsareaindex.waybillGoodsMap.getValues()[0].getValues()[0].data.id}};
							var id = stock.changegoodsareaindex.waybillGoodsMap.getValues()[0].getValues()[0].data.id;
							var orgCode = stock.changegoodsareaindex.waybillGoodsMap.getValues()[0].getValues()[0].data.org_code;
							var jsonParam = {'stockVO.changeGoodsAreaEntity.id' : id,
											'stockVO.changeGoodsAreaOrgCode' : orgCode};
	//						Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								url: stock.realPath('confirmModify.action'),
								params:jsonParam,
								success:function(response){
	//			    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				stock.changegoodsareaindex.waybillGoodsMap.clear();
				    				stock.changegoodsareaindex.waybillGrid.store.load();
				    				Ext.ux.Toast.msg('提示', '确认修改成功！', 'ok', 3000);
				    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
								},
				    			exception : function(response) {
	//			    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				Ext.ux.Toast.msg('确认修改失败', result.message, 'error', 3000);
				    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
				    			}
			    			});	
						}
					});	
				
				
				}
			}];
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.changegoodsareaindex.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		//select监听
		listeners: {
			select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				var mapKey = id;
				var goodsMap = stock.changegoodsareaindex.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				//这一行是添加数据的吧?
				stock.changegoodsareaindex.waybillGoodsMap.add(mapKey,goodsMap);	
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				var mapKey = id;
				var selectedList = grid.getSelectionModel().selected;
				stock.changegoodsareaindex.waybillGoodsMap.removeAtKey(mapKey);
			}
		}
});

//运单库存 model
Ext.define('Foss.stock.changegoodsareaindex.WaybillModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',type:'string'},
		{name: 'applicant_time' , type: 'date',convert: dateConvert},
		{name: 'org_name' , type: 'string'},
		{name: 'org_code' , type: 'string'},
		{name: 'state', type: 'string',
			convert: function(value) {
				if (value == '1') {					
					return '未修改';
				}else if(value == '2'){
					return '已修改';
				}else if(value == '3'){
					return '已作废';
				}else if(value == '0'){
					return '全部';
				}
			}
		},
		{name: 'applicant_name' , type: 'string'},
		{name: 'applicant_code' , type: 'string'},
		{name: 'remarks', type: 'string'}

	]
});
//运单库存 Store
Ext.define('Foss.stock.changegoodsareaindex.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.changegoodsareaindex.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
      type : 'ajax',
      actionMethods:'post',
      url: stock.realPath('queryChangeGoodsArea.action'),
		reader : {
			type : 'json',
			root : 'stockVO.changeGoodsAreaEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示', '查询失败！', 'error', 3000);
		}
  },
  listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = stock.changegoodsareaindex.queryform.getValues();
				if(queryParams.state == '0'){
					queryParams.state = '';
				}
				Ext.apply(operation, {
					params : {
						'stockVO.changeGoodsAreaQueryDto.state' : queryParams.state,
						'stockVO.changeGoodsAreaQueryDto.beginInStockTime' : queryParams.createtime,
						'stockVO.changeGoodsAreaQueryDto.endInStockTime' : queryParams.finishtime

					}
				});	
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示', '查询失败！', 'error', 3000);
		}
	}
});



//新增页面↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
Ext.define('Foss.stock.changegoodsareaindex.changeWindow', {
	extend: 'Ext.window.Window',
	title: '库区编号修改',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var changePanel = stock.changegoodsareaindex.changegoodsareaindexPanel; 
		me.items = [
		    changePanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.changegoodsareaindex.changegoodsareaindexForm.form.reset();
			//将grid的数据也重置   
			stock.changegoodsareaindex.querygoodsareacode.store.removeAll();
		}
	}
});
//将两个页面合在一起(2)
//新增页面
Ext.define('Foss.stock.changegoodsareaindex.changegoodsareaindexPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.changegoodsareaindex.changegoodsareaindexForm = Ext.create('Foss.stock.changegoodsareaindex.OrgForm');
			stock.changegoodsareaindex.querygoodsareacode = Ext.create('Foss.stock.changegoodsareaindex.GoodsAreaCodeGrid');
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.changegoodsareaindex.changegoodsareaindexForm,
			    stock.changegoodsareaindex.querygoodsareacode
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [{
			xtype: 'button',
			text: '取消',
//			text: stock.changegoodsareaindex.i18n('foss.stock.reset'),
			handler: function(){  
				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaAddWindow().close();
			}	
			},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
			},{ 
				xtype: 'button',
		  	text: '确认',
						
		  	cls : 'yellow_button',
		  	columnWidth:.08,
		  	
				
						
			gridContainer: this,
		    handler: function(){
		    	//将页面的信息插入到数据库表中,一部分数据插入到主表,新旧库区号的对应关系插入到副表中
		    	//主表需要的信息
		    	orgCode = stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('org_code').getValue();
		    	orgName = stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('org_name').getRawValue();
		    	remarks = stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('remarks').getValue();
		    	if(orgCode == null || orgCode == ''){
		    			Ext.ux.Toast.msg('提示', '请填写转运场名称', 'error', 2000);
		    			return;
		    	}
		    	if(remarks.length > 100){
		    		Ext.ux.Toast.msg('提示', '备注太长！', 'error', 2000);
					return;
		    	}
		    	//副表需要的数据是一个map集合,每条记录都是有 orgCode,oldCode,newCode的list
		    	count = stock.changegoodsareaindex.querygoodsareacode.store.totalCount;
		    	var area_codes = new Array();
		    	 var goods_area_code_new = new Array();
		    	for(var i = 0;i<count;i++){
		    		var goods_area_code_i = new Array();
		    		//获取原库区编号和新库区编号
		    		oldCode = stock.changegoodsareaindex.querygoodsareacode.store.getAt(i).data.old_goods_area_code;
		    		newCode = stock.changegoodsareaindex.querygoodsareacode.store.getAt(i).data.new_goods_area_code;
		    		goods_area_code_i.push(oldCode);
		    		goods_area_code_i.push(newCode);
		    		if(newCode.trim() != null && newCode.trim() != ''){
		    			goods_area_code_new.push(newCode);
		    			}
		    		
		    		area_codes.push(goods_area_code_i);
		    	}
		    	//如果没有改任何库区,不让提交
		    	if(goods_area_code_new.length == 0){
		    			Ext.ux.Toast.msg('提示', '没有要变更的新库区数据', 'error', 2000);
		    			return;
		    		}
			//	var jsonParam = {stockVO: {newAndOldGoodsAreaEntityList:area_codes},
			//	{changeGoodsAreaEntity :{org_code : orgCode,org_name : orgName,remarks : remarks}}};
					var jsonParam = {
								'stockVO.stringList' : area_codes,
								'stockVO.changeGoodsAreaEntity.org_code' : orgCode,
								'stockVO.changeGoodsAreaEntity.org_name' : orgName,
								'stockVO.changeGoodsAreaEntity.remarks' : remarks
						};
						
				var myMask = new Ext.LoadMask(stock.changegoodsareaindex.changegoodsareaindexPanel, {msg : "数据提交中，请稍等..."});
 				myMask.show();
						
		    	Ext.Ajax.request({						
					//将页面的信息写入两个数据库表中
    			url: stock.realPath('changeGoodsAreaInStock.action'),
    			params:jsonParam,
    			success:function(response){
    				
    				Ext.ux.Toast.msg('提示', '申请成功！', 'ok', 3000);
    				myMask.hide();
    				//关闭页面
    				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaAddWindow().close();
    				//去加载主表数据
    				stock.changegoodsareaindex.waybillGrid.store.load();
    				
    			},
    			
    			exception : function(response) {
    				var result = Ext.decode(response.responseText);
    				Ext.ux.Toast.msg('提示', '申请失败！', 'error', 3000);
    				myMask.hide();
    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
    				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaAddWindow().close();
    			}
    				
    			});	
    			stock.changegoodsareaindex.waybillGoodsMap.clear();
		    }
		    
		   
		}]
		//,
	//	closable : function(){
	//		 alert('aaaaaa');
	//		}
});
//库区编号修改表单
Ext.define('Foss.stock.changegoodsareaindex.OrgForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物库区编号修改',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		fieldLabel: '转运场code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'org_code',
		hidden : true,
		columnWidth:.4
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'转运场名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'org_name',
		readOnly : false,
		allowBlank: false,
		blankText:'字段不能为空',
//	    blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
		listeners : {
		     select : function() {
		    	 var orgCode = stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('org_name').getValue();
		    	 stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('org_code').setValue(orgCode);
		     }
		}      
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		readOnly : false,
		fieldLabel: '备注',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'remarks',
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{ 
		xtype: 'button',
	  	text: '查询',
//	  	text: stock.stockmanage.i18n('foss.stock.confirminstock'),
	  	cls : 'yellow_button',
	  	columnWidth:.08,
//	  	hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
		gridContainer: this,
	    handler: function(){
	   	 	if(stock.changegoodsareaindex.changegoodsareaindexForm.getForm().isValid()){
	   	 		stock.changegoodsareaindex.pagingBar1.moveFirst();       //pagingBar   分页用的
			 }
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


Ext.define('Foss.stock.changegoodsareaindex.GoodsAreaCodeGrid', {
	extend:'Ext.grid.Panel',
	height: 300,
	autoScroll:true,
	columnLines: true,
    frame: true,
	columns: [{
			header: 'ID', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'id',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: 'orgCode', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'org_code',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '操作', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'operate',
			width : 160,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
	  			tooltip:'修改',
	            iconCls:'deppon_icons_edit',
	            handler: function(grid, rowIndex, colIndex) {
						stock.changegoodsareaindex.changeWindow = Ext.getCmp('T_stock-changegoodsareaindex_content').getChangeGoodsModifyCodeWindow();
						stock.changegoodsareaindex.changeWindow.show();	
						//将rowIndex设置为全局变量
						stock.changegoodsareaindex.rowindex = rowIndex;
						//原库区code
						var oldCode = grid.store.data.items[rowIndex].data.old_goods_area_code;
						//获取修改页面  然后将旧库区code设置为oldCode  
						stock.changegoodsareaindex.changeGoodsModifyCodeForm.getForm().findField('oldCode').setValue(oldCode);
						stock.changegoodsareaindex.changeGoodsModifyCodeForm.getForm().findField('newCode').setValue('');
						//stock.changegoodsareaindex.querygoodsareacode.store.totalCount 总记录数
						//stock.changegoodsareaindex.querygoodsareacode.store.getAt(0).set('new_goods_area_code','88');
	            }
				}],
			
		},{ 
			header: '原库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'old_goods_area_code',
			width : 260,
			align: 'center'
		},{ 
			header: '新库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.checkState'),
			dataIndex: 'new_goods_area_code',
			width : 260,
			align: 'center'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.changegoodsareaindex.AddGoodsAreaCodeStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			}];
			me.bbar1 = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.changegoodsareaindex.pagingBar1 = me.bbar1;
			me.callParent([cfg]);
		},
		//select监听
		listeners: {
			select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;
	
				var id = record.get('id');
				var goodsMap = stock.changegoodsareaindex.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				//这一行是添加数据的吧?
				stock.changegoodsareaindex.waybillGoodsMap.add(mapKey,goodsMap);	
			},
		    deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;
				var selectedList = grid.getSelectionModel().selected;
				stock.changegoodsareaindex.waybillGoodsMap.removeAtKey(mapKey);
			}

		}
	
});

//库区编号model
Ext.define('Foss.stock.changegoodsareaindex.OldAndNewGoodsArea', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'org_code', type: 'string'},
        {name: 'old_goods_area_code', type: 'string'},
        {name: 'new_goods_area_code', type: 'string'}
    ]
});

//库区号 Store
Ext.define('Foss.stock.changegoodsareaindex.AddGoodsAreaCodeStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.changegoodsareaindex.OldAndNewGoodsArea',
	pageSize:10,
	autoLoad: false,
	proxy: {
	type : 'ajax',
    actionMethods:'post',
    url: stock.realPath('lookGoodsAreaByOrgcode.action'),
		reader : {
			type : 'json',
			root : 'stockVO.newAndOldGoodsAreaEntityList',
		//	totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var orgCode = stock.changegoodsareaindex.changegoodsareaindexForm.form.findField('org_name').getValue();
				Ext.apply(operation, {
					params : {
						'stockVO.changeGoodsAreaOrgCode' : orgCode

					}
				});	
		},
	}
});


//第二层页面修改带出的页面(修改2)
Ext.define('Foss.stock.changegoodsareaindex.changeGoodsModifyCodeWindow', {
	extend: 'Ext.window.Window',
	title: '库区编号修改',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 600,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		stock.changegoodsareaindex.changeGoodsModifyCodeForm = Ext.create('Foss.stock.changegoodsareaindex.changeGoodsModifyCodeForm'); 	
		me.items = [
		     stock.changegoodsareaindex.changeGoodsModifyCodeForm  
		];
		me.callParent([cfg]);
	},
	buttons: [{
		text: '取消',
//		text: stock.changegoodsareaindex.i18n('foss.stock.reset'),
		handler: function(){  
			Ext.getCmp('T_stock-changegoodsareaindex_content').getChangeGoodsModifyCodeWindow().close();
		}	
		},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
		},{ 
	  	text: '确认',
	  	cls : 'yellow_button',
	  	columnWidth:.08,
		gridContainer: this,
	    handler: function(){
	    	//改掉新库区code,然后关掉页面
	    	newCode = stock.changegoodsareaindex.changeGoodsModifyCodeForm.getValues().newCode;
	    	if(newCode.length >20){
	    		Ext.ux.Toast.msg('提示', '新库区编号长度过长!', 'error', 2000);
	    		return;
	    	}
	    	//获取一个rowindex的变量,弄成全局变量吧,一点确定的时候就把这个全局变量给销毁掉
	    	stock.changegoodsareaindex.querygoodsareacode.store.getAt(stock.changegoodsareaindex.rowindex).set('new_goods_area_code',newCode);
	    	stock.changegoodsareaindex.rowindex == null;
	    	Ext.getCmp('T_stock-changegoodsareaindex_content').getChangeGoodsModifyCodeWindow().close();
	    }
		}],
	listeners : {
		beforeclose : function(){
//			stock.movegoodsindex.moveGoodsForm.form.reset();
//			Ext.getCmp('T_stock-movegoods_MoveOutStockForm').allowBlank = true;
		}
	}
});

//库区编号修改_修改页面(新旧库区号的页面)  (修改2) 
Ext.define('Foss.stock.changegoodsareaindex.changeGoodsModifyCodeForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物库区编号修改',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.3
	},
	items: [{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{
		xtype: 'textfield',  //textfield
		maxLength : 100,
		readOnly : true,
		fieldLabel: '原库区编号',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'oldCode',
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{
		xtype: 'textfield',  //textfield
		readOnly : false,
		fieldLabel: '新库区编号',
		maxLength: 20,
		maxLengthText : '输入长度过长',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'newCode',
		columnWidth:.4
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//新增页面结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑




//修改页面↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//修改窗口
Ext.define('Foss.stock.changegoodsareaindex.changeModifyWindow', {
	extend: 'Ext.window.Window',
	title: '库区编号修改',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var changePanel = stock.changegoodsareaindex.changeModifyPanel; 	
		me.items = [
		    changePanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.changegoodsareaindex.querygoodsareacodemodify.store.removeAll();
		}
	}
});
//将两个页面合在一起(2)
Ext.define('Foss.stock.changegoodsareaindex.changeModifyPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.changegoodsareaindex.modifyOrgForm = Ext.create('Foss.stock.changegoodsareaindex.ModifyOrgForm');
			stock.changegoodsareaindex.querygoodsareacodemodify = Ext.create('Foss.stock.changegoodsareaindex.GoodsAreaCodeModifyGrid');
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.changegoodsareaindex.modifyOrgForm,
			    stock.changegoodsareaindex.querygoodsareacodemodify
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [{
			text: '取消',
//			text: stock.changegoodsareaindex.i18n('foss.stock.reset'),
			handler: function(){  
				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaModifyWindow().close();
				
			}	
			},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
			},{ 
		  	text: '确认',
//		  	text: stock.stockmanage.i18n('foss.stock.confirminstock'),
		  	cls : 'yellow_button',
		  	columnWidth:.08,
//		  	hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
			gridContainer: this,
		    handler: function(){
		    	//要获取修改的数据的id,原库区号,新库区号,还有orgCode
		    	var count = stock.changegoodsareaindex.querygoodsareacodemodify.store.totalCount;
		    	var area_codes = new Array();
		    	var goods_area_code_new = new Array();
		    	for(var i = 0;i<count;i++){
		    		var goods_area_code_i = new Array();
		    		//获取id   根据id来修改新库区编号  oldCode 不用要
		    		id = stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(i).data.id;
		    		//获取原库区编号和新库区编号
		    		//oldCode = stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(i).data.old_goods_area_code;
		    		newCode = stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(i).data.new_goods_area_code;
		    		if(newCode.trim() != null && newCode.trim() != ''){
		    			goods_area_code_i.push(id);
			    		//goods_area_code_i.push(oldCode);
			    		goods_area_code_i.push(newCode);
			    		goods_area_code_new.push(newCode);
			    		area_codes.push(goods_area_code_i);
		    		}
		    		
		    	};
		    	//如果没有改任何库区,不让提交
		    	if(goods_area_code_new.length == 0){
		    			Ext.ux.Toast.msg('提示', '没有要变更的新库区数据', 'error', 2000);
		    			return;
		    		}
		    	var remarks = stock.changegoodsareaindex.modifyOrgForm.form.findField('remarks').getValue();
		    	if(remarks.length > 100){
		    		Ext.ux.Toast.msg('提示', '备注太长！', 'error', 2000);
					return;
		    	}
		    	var id = stock.changegoodsareaindex.modifyOrgForm.form.findField('change_goodsarea_area_id').getValue();
		    	var jsonParam = { 'stockVO.stringList' : area_codes,
						'stockVO.changeGoodsAreaEntity.remarks' : remarks,
						'stockVO.changeGoodsAreaEntity.id' : id
						};
		    	var myMask = new Ext.LoadMask(stock.changegoodsareaindex.changeModifyPanel, {msg : "数据修改中，请稍等..."});
 				myMask.show();
		    	Ext.Ajax.request({						
					//将页面的信息写入两个数据库表中
    			url: stock.realPath('modifyGoodsAreaInStock.action'),
    			params:jsonParam,
    			
    			success:function(response){
    				Ext.ux.Toast.msg('提示', '修改成功！', 'ok', 3000);
    				myMask.hide();
    				//关闭页面
    				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaModifyWindow().close();
    				//去加载主表数据
    				stock.changegoodsareaindex.waybillGrid.store.load();
    			},
    			exception : function(response) {
    				Ext.Msg.hide();
    				var result = Ext.decode(response.responseText);
    				Ext.ux.Toast.msg('修改失败', result.message, 'error', 3000);
    				myMask.hide();
    				Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaModifyWindow().close();
    			}
    			});	
		    	stock.changegoodsareaindex.waybillGoodsMap.clear();
		    	//stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(1).data.old_goods_area_code;
		    	//stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(1).data.new_goods_area_code;
		    }
		}]
		
});

Ext.define('Foss.stock.changegoodsareaindex.ModifyOrgForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物库区编号修改',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		fieldLabel: 'change_goodsarea_area_id',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'change_goodsarea_area_id',
		hidden : true,
		columnWidth:.4
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		fieldLabel: '转运场code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'org_code',
		hidden : true,
		columnWidth:.4
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'转运场名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'org_name',
		readOnly : true,
		allowBlank: false,
		blankText:'字段不能为空',
//	    blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
		listeners : {
		     select : function() {
		    	 
		     }
		}      
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		readOnly : false,
		fieldLabel: '备注',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'remarks',
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{ 
		xtype: 'button',
	  	text: '查询',
//	  	text: stock.stockmanage.i18n('foss.stock.confirminstock'),
	  	cls : 'yellow_button',
	  	columnWidth:.08,
//	  	hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
		gridContainer: this,
	    handler: function(){
	    	if(stock.changegoodsareaindex.modifyOrgForm.getForm().isValid()){
	    		stock.changegoodsareaindex.pagingBar2.moveFirst();       //pagingBar   分页用的
			 }
	    
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//第二层页面修改带出的页面(修改2)
Ext.define('Foss.stock.changegoodsareaindex.modifyGoodsModifyCodeWindow', {
	extend: 'Ext.window.Window',
	title: '库区编号修改',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 600,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		stock.changegoodsareaindex.modifyGoodsModifyCodeForm = Ext.create('Foss.stock.changegoodsareaindex.modifyGoodsModifyCodeForm'); 	
		me.items = [
		     stock.changegoodsareaindex.modifyGoodsModifyCodeForm  
		];
		me.callParent([cfg]);
	},
	buttons: [{
		text: '取消',
//		text: stock.changegoodsareaindex.i18n('foss.stock.reset'),
		handler: function(){  
			Ext.getCmp('T_stock-changegoodsareaindex_content').getModifyGoodsModifyCodeWindow().close();
		}	
		},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
		},{ 
	  	text: '确认',
	  	cls : 'yellow_button',
	  	columnWidth:.08,
		gridContainer: this,
	    handler: function(){
	    	//改掉新库区code,然后关掉页面
	    	newCode = stock.changegoodsareaindex.modifyGoodsModifyCodeForm.getValues().newCode;
	    	if(newCode.length >20){
	    		Ext.ux.Toast.msg('提示', '新库区编号长度过长!', 'error', 2000);
	    		return;
	    	}
	    	//获取一个rowindex的变量,弄成全局变量吧,一点确定的时候就把这个全局变量给销毁掉
	    	stock.changegoodsareaindex.querygoodsareacodemodify.store.getAt(stock.changegoodsareaindex.modifyrowindex).set('new_goods_area_code',newCode);
	    	stock.changegoodsareaindex.modifyrowindex == null;
	    	Ext.getCmp('T_stock-changegoodsareaindex_content').getModifyGoodsModifyCodeWindow().close();
	    }
		}],
	listeners : {
		beforeclose : function(){
//			stock.movegoodsindex.moveGoodsForm.form.reset();
//			Ext.getCmp('T_stock-movegoods_MoveOutStockForm').allowBlank = true;
		}
	}
});

//库区编号修改_修改页面(新旧库区号的页面)  (修改2) 
Ext.define('Foss.stock.changegoodsareaindex.modifyGoodsModifyCodeForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物库区编号修改',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.3
	},
	items: [{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{
		xtype: 'textfield',  //textfield
		maxLength : 100,
		readOnly : true,
		fieldLabel: '原库区编号',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'oldCode',
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{
		xtype: 'textfield',  //textfield
		readOnly : false,
		fieldLabel: '新库区编号',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'newCode',
		maxLength: 20,
		maxLengthText : '输入长度过长',
		columnWidth:.4
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


Ext.define('Foss.stock.changegoodsareaindex.GoodsAreaCodeModifyGrid', {
	extend:'Ext.grid.Panel',
	height: 300,
	autoScroll:true,
	columnLines: true,
    frame: true,
	columns: [{
			header: 'ID', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'id',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: 'orgCode', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'org_code',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '操作', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'operate',
			width : 160,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
	  			tooltip:'修改',
	            iconCls:'deppon_icons_edit',
	            handler: function(grid, rowIndex, colIndex) {
	            		stock.changegoodsareaindex.modifyWindow = Ext.getCmp('T_stock-changegoodsareaindex_content').getModifyGoodsModifyCodeWindow();
	            		stock.changegoodsareaindex.modifyWindow.show();	
						//将rowIndex设置为全局变量   ----------------------------------------------------------------
						stock.changegoodsareaindex.modifyrowindex = rowIndex;
						//原库区code
						var oldCode = grid.store.data.items[rowIndex].data.old_goods_area_code;
						//获取修改页面  然后将旧库区code设置为oldCode
						stock.changegoodsareaindex.modifyGoodsModifyCodeForm.getForm().findField('oldCode').setValue(oldCode);
						stock.changegoodsareaindex.modifyGoodsModifyCodeForm.getForm().findField('newCode').setValue('');
						
						
	            }
				}
			],
			
		},{ 
			header: '原库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'old_goods_area_code',
			width : 260,
			align: 'center'
		},{ 
			header: '新库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.checkState'),
			dataIndex: 'new_goods_area_code',
			width : 260,
			align: 'center'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.changegoodsareaindex.ModifyGoodsAreaCodeStore');

//			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
////				showHeaderCheckbox : false,
//				mode : 'SIMPLE',
//				checkOnly : true//限制只有点击checkBox后才能选中行
//			});
			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			}];
			me.bbar2 = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.changegoodsareaindex.pagingBar2 = me.bbar2;
			me.callParent([cfg]);
		},
		//select监听
		listeners: {
			select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;
	
				var id = record.get('id');
				var goodsMap = stock.changegoodsareaindex.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				//这一行是添加数据的吧?
				stock.changegoodsareaindex.waybillGoodsMap.add(mapKey,goodsMap);	
			},
		    deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;
				var selectedList = grid.getSelectionModel().selected;
				stock.changegoodsareaindex.waybillGoodsMap.removeAtKey(mapKey);
			}

		}
	
});


//库区编号model
Ext.define('Foss.stock.changegoodsareaindex.ModifyOldAndNewGoodsArea', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id',type: 'string'},
        {name:'change_goodsarea_area_id',type:'string'}, 
        {name: 'org_code', type: 'string'},
        {name: 'new_goods_area_code', type: 'string'},
        {name: 'old_goods_area_code', type: 'string'}
    ]
});

//库区号 Store
Ext.define('Foss.stock.changegoodsareaindex.ModifyGoodsAreaCodeStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.changegoodsareaindex.ModifyOldAndNewGoodsArea',
	pageSize:10,
	autoLoad: false,
	proxy: {
	type : 'ajax',
    actionMethods:'post',
    url: stock.realPath('lookModifyGoodsAreaByOrgcode.action'),
		reader : {
			type : 'json',
			root : 'stockVO.newAndOldGoodsAreaEntityList',
		//	totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var orgCode = stock.changegoodsareaindex.modifyOrgForm.getForm().findField('org_code').getValue();
				var id = stock.changegoodsareaindex.modifyOrgForm.getForm().findField('change_goodsarea_area_id').getValue();
				Ext.apply(operation, {
					params : {
						'stockVO.changeGoodsAreaOrgCode' : orgCode,
						'stockVO.changeGoodsAreaEntityId' : id
					}
				});	
		},
	}
});




//修改页面结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


//查看页面↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//查看窗口
Ext.define('Foss.stock.changegoodsareaindex.changeLookWindow', {
	extend: 'Ext.window.Window',
	title: '库区编号修改',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 610,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var changePanel = Ext.create('Foss.stock.changegoodsareaindex.changeLookPanel'); 	
		me.items = [
		    changePanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.changegoodsareaindex.querygoodsareacodelook.store.removeAll();
		}
	}
});
//将两个页面合在一起(2)
Ext.define('Foss.stock.changegoodsareaindex.changeLookPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.changegoodsareaindex.lookOrgForm = Ext.create('Foss.stock.changegoodsareaindex.LookOrgForm');
			stock.changegoodsareaindex.querygoodsareacodelook = Ext.create('Foss.stock.changegoodsareaindex.GoodsAreaCodeLookGrid');
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.changegoodsareaindex.lookOrgForm,
			    stock.changegoodsareaindex.querygoodsareacodelook
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [
			{ 
		  	text: '确认',
		  	cls : 'yellow_button',
		  	columnWidth:.08,
			gridContainer: this,
		    handler: function(){
		    	Ext.getCmp('T_stock-changegoodsareaindex_content').getChangegoodsareaLookWindow().close();
		    }
		}]
		
});


Ext.define('Foss.stock.changegoodsareaindex.LookOrgForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物库区编号修改',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		fieldLabel: 'change_goodsarea_area_id',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'change_goodsarea_area_id',
		hidden : true,
		columnWidth:.4
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		fieldLabel: '转运场code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'org_code',
		hidden : true,
		columnWidth:.4
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'转运场名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'org_name',
		readOnly : true,
		allowBlank: false,
		blankText:'字段不能为空',
//	    blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
		listeners : {
		     select : function() {
		    	 
		     }
		}      
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		readOnly : true,
		fieldLabel: '备注',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'remarks',
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.32,
		html: '&nbsp;'
	},{ 
		xtype: 'button',
	  	text: '查询',
//	  	text: stock.stockmanage.i18n('foss.stock.confirminstock'),
	  	cls : 'yellow_button',
	  	columnWidth:.12,
//	  	hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
		gridContainer: this,
	    handler: function(){
	    	if(stock.changegoodsareaindex.lookOrgForm.getForm().isValid()){
	    		stock.changegoodsareaindex.pagingBar3.moveFirst();       //pagingBar   分页用的
			 }
	    
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


Ext.define('Foss.stock.changegoodsareaindex.GoodsAreaCodeLookGrid', {
	extend:'Ext.grid.Panel',
	height: 300,
	autoScroll:true,
	columnLines: true,
    frame: true,
	columns: [{ 
			header: '原库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'old_goods_area_code',
			width : 260,
			align: 'center'
		},{ 
			header: '新库区编号', 
//			header: stock.prioritygoods.i18n('foss.stock.checkState'),
			dataIndex: 'new_goods_area_code',
			width : 260,
			align: 'center'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.changegoodsareaindex.LookGoodsAreaCodeStore');

			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			}];
			me.bbar3 = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.changegoodsareaindex.pagingBar3 = me.bbar3;
			me.callParent([cfg]);
		},
	
});


//库区号 Store
Ext.define('Foss.stock.changegoodsareaindex.LookGoodsAreaCodeStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.changegoodsareaindex.ModifyOldAndNewGoodsArea',
	pageSize:10,
	autoLoad: true,
	proxy: {
	type : 'ajax',
    actionMethods:'post',
    url: stock.realPath('lookLookGoodsAreaByOrgcode.action'),
		reader : {
			type : 'json',
			root : 'stockVO.newAndOldGoodsAreaEntityList',
		//	totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var orgCode = stock.changegoodsareaindex.lookOrgForm.getForm().findField('org_code').getValue();
				var id = stock.changegoodsareaindex.lookOrgForm.getForm().findField('change_goodsarea_area_id').getValue();
				Ext.apply(operation, {
					params : {
						'stockVO.changeGoodsAreaOrgCode' : orgCode,
						'stockVO.changeGoodsAreaEntityId' : id
					}
				});	
		},
	}
});



//查看页面结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


Ext.onReady(function(){
	Ext.Ajax.request({
		url: stock.realPath('queryIsTransferCenter.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			stock.changegoodsareaindex.org_code = result.stockVO.stockOrgCode;
			stock.changegoodsareaindex.org_name = result.stockVO.stockOrgName;
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.stock.changegoodsareaindex.QueryForm');
			stock.changegoodsareaindex.queryform = queryform;
			var waybillGrid = Ext.create('Foss.stock.changegoodsareaindex.WaybillGrid');
			stock.changegoodsareaindex.waybillGrid = waybillGrid;
			var changegoodsareaindexPanel = Ext.create('Foss.stock.changegoodsareaindex.changegoodsareaindexPanel');
			stock.changegoodsareaindex.changegoodsareaindexPanel = changegoodsareaindexPanel;
			var changeModifyindexPanel = Ext.create('Foss.stock.changegoodsareaindex.changeModifyPanel');
			stock.changegoodsareaindex.changeModifyPanel = changeModifyindexPanel;
			
			stock.changegoodsareaindex.waybillGoodsMap = new Ext.util.HashMap();
			
			Ext.create('Ext.panel.Panel',{
				id:'T_stock-changegoodsareaindex_content',
				cls:"panelContentNToolbar",
			  	bodyCls:'panelContent-body',
			  	//新增
			  	changegoodsareaAddWindow:null,
			  	getChangegoodsareaAddWindow: function() {
					if(this.changegoodsareaAddWindow == null) {
						this.changegoodsareaAddWindow = Ext.create('Foss.stock.changegoodsareaindex.changeWindow');
					}
					return this.changegoodsareaAddWindow;
				},
				//修改,	带出的转运场名称 为只读的   查询的SQL跟新增不一样
				changegoodsareaModifyWindow:null,
			  	getChangegoodsareaModifyWindow: function() {
					if(this.changegoodsareaModifyWindow == null) {
						this.changegoodsareaModifyWindow = Ext.create('Foss.stock.changegoodsareaindex.changeModifyWindow');
					}
					return this.changegoodsareaModifyWindow;
				},
				//查看,带出的转运场名称 为只读的  同时grid里面没有'操作'这一项  
				changegoodsareaLookWindow:null,
			  	getChangegoodsareaLookWindow: function() {
					if(this.changegoodsareaLookWindow == null) {
						this.changegoodsareaLookWindow = Ext.create('Foss.stock.changegoodsareaindex.changeLookWindow');
					}
					return this.changegoodsareaLookWindow;
				},
				
				//新增的修改带出库区号的页面(修改2)
				changeGoodsModifyCodeWindow:null,
				getChangeGoodsModifyCodeWindow: function() {
					if(this.changeGoodsModifyCodeWindow == null) {
						this.changeGoodsModifyCodeWindow = Ext.create('Foss.stock.changegoodsareaindex.changeGoodsModifyCodeWindow');
					}
					return this.changeGoodsModifyCodeWindow;
				},
				//修改的修改带出库区号的页面(修改页面的修改2)
				modifyGoodsModifyCodeWindow:null,
				getModifyGoodsModifyCodeWindow: function() {
					if(this.modifyGoodsModifyCodeWindow == null) {
						this.modifyGoodsModifyCodeWindow = Ext.create('Foss.stock.changegoodsareaindex.modifyGoodsModifyCodeWindow');
					}
					return this.modifyGoodsModifyCodeWindow;
				},
				
				items : [queryform,waybillGrid],
				renderTo: 'T_stock-changegoodsareaindex-body'
				});
		
			stock.changegoodsareaindex.queryform.getForm().findField('org_code').setValue(stock.changegoodsareaindex.org_code);
			stock.changegoodsareaindex.queryform.getForm().findField('org_name').setValue(stock.changegoodsareaindex.org_name);
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('获取外场部门失败', result.message, 'error', 3000);
		//	Ext.ux.Toast.msg(stock.changegoodsareaindex.i18n('error.query.transfer.center.org'), result.message, 'error', 3000);
		}
	});
	
});

