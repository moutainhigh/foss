//定义第一层配载单Model
Ext.define('Foss.unload.unloadtaskconfirmlong.billInfoModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [ {
		name: ' extid', 
		type: 'string'
	},{
		name : 'billNo',
		type : 'string'
	},{
		name : 'expressOrLingDan',
		type : 'string'
	},{
		name : 'isBindData',//用来标记该行记录是否被展开
		type : 'string',
		defaultValue : 'N'
	},{
		name : 'isSelectAll',//用来标记子表是否被全选
		type : 'string',
		defaultValue : 'N'
	}]
});

//定义第一层列表store
Ext.define('Foss.unload.unloadtaskconfirmlong.billInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskconfirmlong.billInfoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义第一层列表grid
Ext.define('Foss.unload.unloadtaskconfirmlong.billInfoGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	cls:'autoHeight',
	title : '卸车任务明细	<font color="red" size="1px" style="font-weight : lighter">（注：请勾选少货的流水号，没有少货异常请不要勾选！）</font>',
	bodyCls:'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor : function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskconfirmlong.billInfoStore'),
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		pluginId : 'unloadtaskconfirmlong_handOverBillGrid',
		// 行体内容
		rowBodyElement : 'Foss.unload.unloadtaskconfirmlong.handOverBillInfoGrid'
	}],
	hideHeaders : true,
	columns: [{
		align : 'left',
		dataIndex : 'billNo',
		flex : 1,
		renderer : function(value,md,record){
			if(value != null){
				
				var checkIsExpress=function(billNo){
					billNo=Ext.util.Format.uppercase(billNo);
					if((billNo.indexOf('L')!=-1
							&&!isNaN(billNo.replace('L','')))
							||(billNo.indexOf('S')!=-1
							&&!isNaN(billNo.replace('S','')))  
						    ||(billNo.indexOf('T')!=-1
						    &&!isNaN(billNo.replace('T','')))){
						return true;
					}else if((billNo.indexOf('L')!=-1
							&&billNo.lastIndexOf('P')&&
							!isNaN(billNo.replace('L','').replace('P','')))
							||(billNo.indexOf('S')!=-1
									&&billNo.lastIndexOf('P')
									&&!isNaN(billNo.replace('S','').replace('P','')))
							||(billNo.indexOf('T')!=-1
									&&billNo.lastIndexOf('P')
									&&!isNaN(billNo.replace('T','').replace('P','')))){
						return true;
					}
					return false;
				}
				
				if(checkIsExpress(record.get('billNo'))){
					return '交接单	' + value;	
				}else{
					return '配载单	' + value;	
				}
			}
		}
	}],
	listeners : {
	'select' : function(rowModel,record,index,eOpts ){
			record.set('isSelectAll','Y');
			var grid = this;
			var vehicleAssembleNo = record.get('billNo');
			var plugin = grid.getPlugin('unloadtaskconfirmlong_handOverBillGrid');
			//如果子表展开，则全选子表
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var store = items[i].store;
						var superNo = store.getAt(0).get('vehicleAssembleNo');
						if(superNo == vehicleAssembleNo){
							items[i].getSelectionModel().selectAll(false);
						}
					}
			}
		},
		'deselect' : function(rowModel,record,index,eOpts){
			record.set('isSelectAll','N');
			var grid = this;
			var vehicleAssembleNo = record.get('billNo');
			var plugin = grid.getPlugin('unloadtaskconfirmlong_handOverBillGrid');
			//如果
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var store = items[i].store;
						var superNo = store.getAt(0).get('vehicleAssembleNo');
						if(superNo == vehicleAssembleNo){
							items[i].getSelectionModel().deselectAll(false);
						}
					}
			}
		}
	}
});

//定义第二层交接单Model
Ext.define('Foss.unload.unloadtaskconfirmlong.handOverBillInfoModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [ {
		name: ' extid', 
		type: 'string'
	},{
		name : 'vehicleAssembleNo',
		type : 'string'
	},{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'cargoNo',
		type : 'string'
	},{
		name : 'cargoType',
		type : 'string'
	},{
		name : 'isBindData',//用来标记该行记录是否被展开
		type : 'string',
		defaultValue : 'N'
	},{
		name : 'isSelectAll',//用来标记子表是否被全选
		type : 'string',
		defaultValue : 'N'
	}]
});

//定义第二层列表store
Ext.define('Foss.unload.unloadtaskconfirmlong.handOverBillInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskconfirmlong.handOverBillInfoModel',
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		url : unload.realPath('queryHandOverBillListByVehicleAssembleNo.action'),
		// 定义一个读取器
		reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'unloadTaskVo.handOverBillList'
			}
		},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义第二层列表grid
Ext.define('Foss.unload.unloadtaskconfirmlong.handOverBillInfoGrid', {
	extend : 'Ext.grid.Panel',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	baseCls : 'unload_unloadconfirm_waybillGap',
	constructor : function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskconfirmlong.handOverBillInfoStore'),
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		pluginId : 'unloadtaskconfirmlong_waybillGrid',
		// 行体内容
		rowBodyElement : 'Foss.unload.unloadtaskconfirmlong.waybillGrid'
	}],
	hideHeaders : true,
	columns: [{
		align : 'left',
		dataIndex : 'handOverBillNo',
		flex : 1,
		renderer : function(value,md,record){
			if(value != null){
				if(record.get('cargoType')=='CAGE'){
					return '笼	' + value;
				}else if(record.get('cargoType')=='PACKAGE'){
					return '包	' + value;
				}else if(record.get('cargoType')=='WAYBILL'){
					return '运单号	' + value;
				}
				return '交接单	' + value;
			}
		}
	}],
	bindData : function(record,grid,rowBody){
		var isBindData = record.get('isBindData');
		var isSelectAll = record.get('isSelectAll');
		//获取配载单列表
		var superGrid = rowBody.superGrid;
		var vehicleAssembleNo = record.get('billNo');
		//看该配载单是否被反选
		var isDeselect = superGrid.getSelectionModel().isSelected(record);
		if(isBindData == 'N'){
			this.store.load({
				params : {
					'unloadTaskVo.vehicleAssembleNo' : vehicleAssembleNo,
					'unloadTaskVo.unloadTaskNo':unload.unloadtaskconfirmlong.unloadTaskNo,
					'unloadTaskVo.unloadType':unload.unloadtaskconfirmlong.unloadType
				},
				callback : function(records, operation, success){
					record.set('isBindData','Y');
					//如果被全选，选中所有交接单
					if(isSelectAll == 'Y'){
						rowBody.getSelectionModel().selectAll(false);//触发运单号的全选
					}
					//如果配载单没被选中，反选所有交接单
					if(!isDeselect){
						rowBody.getSelectionModel().deselectAll(false);//触发交接单号的反选
					}
					if(!Ext.isEmpty(grid.openChild)){
						grid.openChild(rowBody);
						grid.openChildEnable = false;
					}
				}
			});
		}else{
			if(isSelectAll == 'Y'){
				rowBody.getSelectionModel().selectAll(false);//触发运单号的全选
			}
			if(grid.openChildEnable){
				grid.openChild(rowBody);
				grid.openChildEnable = false;
			}
		}
		//如果配载单被反选，则将交接单列反选，同时触发交接单列的反选事件
		if(!isDeselect){
			rowBody.getSelectionModel().deselectAll(false);//触发交接单的反选
		}
	},
	listeners : {
	'select' : function(rowModel,record,index,eOpts ){
			record.set('isSelectAll','Y');
			var grid = this;
			var superGrid = this.superGrid;
			var handOverBillNo = record.get('handOverBillNo');
			var vehicleAssembleNo = record.get('vehicleAssembleNo');
			var superRecord = superGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
			var plugin = grid.getPlugin('unloadtaskconfirmlong_waybillGrid');
			//勾选配载单
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//反选第一层表格中的行：第三个参数为false，表示触发该条记录的反选事件
			//如果子表展开，则全选子表
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var store = items[i].store;
						if(store.getAt(0)==undefined){
							return;
						}
						var superNo = store.getAt(0).get('handOverBillNo');
						if(superNo == handOverBillNo){
							items[i].getSelectionModel().selectAll(false);
						}
					}
			}
		},
		'deselect' : function(rowModel,record,index,eOpts){
			record.set('isSelectAll','N');
			var grid = this;
			var superGrid = this.superGrid;
			var handOverBillNo = record.get('handOverBillNo');
			var vehicleAssembleNo = record.get('vehicleAssembleNo');
			var superRecord = superGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
			superRecord.set('isSelectAll','N');
			var plugin = grid.getPlugin('unloadtaskconfirmlong_waybillGrid');
			//如果交接单全部被反选，则将配载单列反选
			var isDeselectedAll = grid.getSelectionModel().hasSelection();//如果为true，则未全部反选
			if(!isDeselectedAll){
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord,false);//反选第一层表格中的行：第三个参数为false，表示触发该条记录的反选事件
			}
			//如果运单列表被展开，则反选全部运单
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var store = items[i].store;
						if(store.getAt(0)==undefined){
							return;
						}
						var superNo = store.getAt(0).get('handOverBillNo');
						if(superNo == handOverBillNo){
							items[i].getSelectionModel().deselectAll(false);
						}
					}
			}
		}
	}
});

//第三层运单model
Ext.define('Foss.unload.unloadtaskconfirmlong.waybillModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [ {
		name: ' extid', 
		type: 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'vehicleAssembleNo',
		type : 'string'
	},{
		name : 'isBindData',//用来标记该行记录是否被展开
		type : 'string',
		defaultValue : 'N'
	},{
		name : 'isSelectAll',//用来标记子表是否被全选
		type : 'string',
		defaultValue : 'N'
	}]
});

//定义第三层列表store
Ext.define('Foss.unload.unloadtaskconfirmlong.waybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskconfirmlong.waybillModel',
	proxy : {
	// 代理的类型为内存代理
	type : 'ajax',
	url : unload.realPath('queryWaybillListByHandOverBillNo.action'),
	// 定义一个读取器
	reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadTaskVo.waybillList'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义第三层列表grid
Ext.define('Foss.unload.unloadtaskconfirmlong.waybillGrid', {
	extend : 'Ext.grid.Panel',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	baseCls : 'unload_unloadconfirm_waybillGap',
	animCollapse : true,
	constructor : function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskconfirmlong.waybillStore'),
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			allowDeselect : true,
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	hideHeaders : true,
	columns: [{
		align : 'left',
		flex : 1,
		dataIndex : 'waybillNo',
		renderer : function(value,md,record){
			if(value != null){
				if(record.get('cargoType')=='CAGE'){
					return '笼	' + value;
				}else if(value != null&&record.get('cargoType')=='PACKAGE'){
					return '包	' + value;
				}else{
					return '运单号	' + value;
				}
			}
		}
	}],
	bindData : function(record,grid,rowBody){
		var isBindData = record.get('isBindData');
		var isSelectAll = record.get('isSelectAll');
		//获取交接单列表
		var superGrid = rowBody.superGrid;
		//看该配载单是否被反选
		var isDeselect = superGrid.getSelectionModel().isSelected(record);
		if(isBindData == 'N'){
			//获取交接单号
			var handOverBillNo = record.get('handOverBillNo');
			//此处获取零担快递区分标识
			var expressOrLingDan=record.get('expressOrLingDan');
			//获取配载单号
			var vehicleAssembleNo = record.get('vehicleAssembleNo');
			this.store.load({
				params : {
					'unloadTaskVo.handOverBillNo' : handOverBillNo,
					'unloadTaskVo.unloadType':unload.unloadtaskconfirmlong.unloadType,
					'unloadTaskVo.expressOrLingdan':expressOrLingDan,
					'unloadTaskVo.unloadTaskNo':unload.unloadtaskconfirmlong.unloadTaskNo
				},
				callback : function(records, operation, success){
					if(records.length==0){
						 Ext.get(Ext.query('.x-grid-empty')).hide();
					}
					
					record.set('isBindData','Y');
					//如果被全选，选中所有运单号
					if(isSelectAll == 'Y'){
						rowBody.getSelectionModel().selectAll(true);//触发运单号的全选
					}
					//如果交接单没被选中，反选所有运单号
					if(!isDeselect){
						rowBody.getSelectionModel().deselectAll(false);//触发运单号的反选
					}
					//给运单列里的vehicleAssembleNo赋值
					for(var i in records){
						records[i].set('vehicleAssembleNo',vehicleAssembleNo);
					}
					if(!Ext.isEmpty(grid.openChild)){
						grid.openChild(rowBody);
						grid.openChildEnable = false;
					}
				}
			});
		}else{
			if(isSelectAll == 'Y'){
				rowBody.getSelectionModel().selectAll(false);//触发运单号的全选
			}
			if(grid.openChildEnable){
				grid.openChild(rowBody);
				grid.openChildEnable = false;
			}
		}
		//如果交接单被反选，则将运单列反选，同时触发运单列的反选事件
		if(!isDeselect){
			rowBody.getSelectionModel().deselectAll(false);//触发运单号的反选
		}
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		pluginId : 'unloadtaskconfirmlong_serialNoPanel',
		// 行体内容
		rowBodyElement : 'Foss.unload.unloadtaskconfirmlong.serialNoPanel'
	}],
	listeners : {
		'select' : function(rowModel,record,index,eOpts ){
			var grid = this;
			var plugin = grid.getPlugin('unloadtaskconfirmlong_serialNoPanel');
			record.set('isSelectAll','Y');
			var handOverBillNo = record.get('handOverBillNo');
			var vehicleAssembleNo = record.get('vehicleAssembleNo');
			var waybillNo = record.get('waybillNo');
			var superGrid = this.superGrid;
			var superRecord = superGrid.store.findRecord('handOverBillNo',handOverBillNo,0,false,true,true);
			//将父表格的行选中
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//反选第一层表格中的行：第三个参数为true，表示不触发该条记录的选择事件
			//将配载单选中
			var firstGrid = unload.unloadtaskconfirmlong.tGrid;
			var firstRecord = firstGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
			var firstSm = firstGrid.getSelectionModel();
			firstSm.select(firstRecord,true,true);
			//如果四级checkboxgroup被展开，则勾选所有checkbox
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var panel = items[i];
						var form = panel.getForm();
						var superBillNo = form.findField('handOverBillNo').getValue();
						var superWaybillNo = form.findField('waybillNo').getValue();
						if(superBillNo == handOverBillNo && superWaybillNo == waybillNo){
							var checkBoxes = panel.getCheckBoxGroup().items.items;
							for(var j in checkBoxes){
								checkBoxes[j].setValue(true);
							}
						}
					}
			}
		},
		'deselect' : function(rowModel,record,index,eOpts ){
			record.set('isSelectAll','N');
			var grid = this;
			var plugin = grid.getPlugin('unloadtaskconfirmlong_serialNoPanel');
			var handOverBillNo = record.get('handOverBillNo');
			var vehicleAssembleNo = record.get('vehicleAssembleNo');
			//获取第一层的配载单表格
			var firstGrid = unload.unloadtaskconfirmlong.tGrid;
			var firstRecord = firstGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
			var waybillNo = record.get('waybillNo');
			var superGrid = this.superGrid;
			var superRecord = superGrid.store.findRecord('handOverBillNo',handOverBillNo,0,false,true,true);
			//将交接单列的“是否全选”设置为0
			superRecord.set('isSelectAll','N');
			//将配载单列的“是否全选”设置为N
			firstRecord.set('isSelectAll','N');
			//如果运单全部被反选，则将交接单列反选
			var isDeselectedAll = grid.getSelectionModel().hasSelection();//如果为true，则未全部反选
			if(!isDeselectedAll){
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord,false);
			}
			//如果四级checkboxgroup被展开，则反选所有checkbox
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var panel = items[i];
						var form = panel.getForm();
						var superBillNo = form.findField('handOverBillNo').getValue();
						var superWaybillNo = form.findField('waybillNo').getValue();
						if(superBillNo == handOverBillNo && superWaybillNo == waybillNo){
							var checkBoxes = panel.getCheckBoxGroup().items.items;
							for(var j in checkBoxes){
								checkBoxes[j].setValue(false);
							}
						}
					}
			}
		}
	}
});

//第四层流水号panel
Ext.define('Foss.unload.unloadtaskconfirmlong.serialNoPanel', {
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	baseCls : 'unload_unloadconfirm_waybillGap',
	fieldDefaults: {
		labelWidth: 110
	},
	margin:'0 0 0 20',
	bindData : function(record,grid,rowBody){
		//获取上级运单表
		var superGrid = rowBody.superGrid;
		//判断运单是否被选中
		var isSelect = superGrid.getSelectionModel().isSelected(record);
		//判断是否以前被展开过
		var isBindData = record.get('isBindData');
		//动态生成CheckBoxGroup的item
		var checkGroup=rowBody.getCheckBoxGroup();
		var tmpForm=rowBody;
		var waybillNo = record.data.waybillNo;
		var handOverBillNo = record.data.handOverBillNo;
		var vehicleAssembleNo = record.data.vehicleAssembleNo;
		//获取上级表格中的运单列记录
		var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
		//上级表格中记录的运单流水号是否被全选
		var isSelectAll = superRecord.get('isSelectAll');
		//如果该流水号list曾展开过，
		if(isBindData == 'Y'){
			//如果流水号被全选
			if(isSelectAll == 'Y'){
				for(var i in checkGroup.items.items){
					checkGroup.items.items[i].setValue(true);
				}
			}
			//如果上级运单被反选
			if(!isSelect){
				for(var i in checkGroup.items.items){
					checkGroup.items.items[i].setValue(false);
				}
			}
		}else{//如果未展开过，则重新加载数据，渲染checkboxgroup
			record.set('isBindData','Y');
			var params = {'unloadTaskVo.waybillNo' : waybillNo,
				'unloadTaskVo.handOverBillNo' : handOverBillNo,
				'unloadTaskVo.unloadTaskNo':unload.unloadtaskconfirmlong.unloadTaskNo,
			    'unloadTaskVo.unloadType':unload.unloadtaskconfirmlong.unloadType
			};
			var myMask = new Ext.LoadMask(tmpForm, {msg : "正在查询，请稍等..."});
	 		myMask.show();
			Ext.Ajax.request({
				url : unload.realPath('querySerialNoListByHandOverBillNoAndWaybillNo.action'),
				params : params,
				method : 'post',
				success : function(response){
					var result = Ext.decode(response.responseText);
					var serialNoList = result.unloadTaskVo.serialNoList;
					if(serialNoList !=null){
						if(tmpForm.checkBoxGroup==null){
							//添加运单号
							tmpForm.add({
								xtype : 'textfield',
								readOnly : true,
								hidden : true,
								name : 'waybillNo',
								value : waybillNo
							});
							//添加交接单号
							tmpForm.add({
								xtype : 'textfield',
								readOnly : true,
								hidden : true,
								name : 'handOverBillNo',
								value : handOverBillNo
							});
							//添加配载单号
							tmpForm.add({
								xtype : 'textfield',
								readOnly : true,
								hidden : true,
								name : 'vehicleAssembleNo',
								value : vehicleAssembleNo
							});
							//添加checkboxgroup
							var checkboxitems="[";
							//构建选项
							for(var i = 0 ;i<serialNoList.length; i++){
								if(checkboxitems != "")
				        			checkboxitems += ",";
								var checkboxSingleItem = "{boxLabel:'流水号 " + serialNoList[i].serialNo + "', margin:'5 0 10 0',serialNo:'" + serialNoList[i].serialNo +"'}";
								checkboxitems = checkboxitems+ checkboxSingleItem;
							}
							checkboxitems += "]";
							//重新实例化所有的checkbox
							var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
								fieldLabel : '',
								labelSeparator : '',		
								margin : '0 0 0 6',
								defaults : { 
								    columnWidth : .2,
								    margin : '5 0 10 0'
								},
								layout : 'column',
								items : eval(checkboxitems)
							});
							var checkBoxes = checkBoxGroup.items.items;
							var boxesCount = checkBoxes.length;
							//如果上级运单被勾选，则选中所有checkbox，否则不做操作
							if(isSelect){
								for(var i in checkBoxes){
									checkBoxes[i].setValue(true);
								}
							}
							//给每个checkbox加监听，监听勾选和反选
							for(var i in checkBoxes){
								var checkBox = checkBoxes[i];
								//添加值改变事件
								checkBox.addListener('change',function(field,newValue,oldValue,eOpts){
									if(newValue){
										//如果是第一个被勾选，则勾选上级运单号，
										if(checkBoxGroup.getChecked().length == 1){
											//将父表格的行选中
											var sm = superGrid.getSelectionModel();
											sm.select(superRecord,true,true);//第三个参数为true，表示不触发该条记录的选择事件
											//勾选第第一层配载单列
											var firstGrid = unload.unloadtaskconfirmlong.tGrid;
											var firstRecord = firstGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
											var firstSm = firstGrid.getSelectionModel();
											firstSm.select(firstRecord,true,true);//第三个参数为true，表示不触发该条记录的选择事件
											
											//勾选第二层交接单列
											var firstPlugin = firstGrid.getPlugin('unloadtaskconfirmlong_handOverBillGrid'),
//											if(!Ext.isEmpty(firstPlugin.getExpendRow())) {
//											var items = firstPlugin.getExpendRowBody();
//												for(var i in items){
//													var no = items[i].store.getAt(0).get('vehicleAssembleNo');
//													if(no == vehicleAssembleNo){
//															var sm = items[i].getSelectionModel();
//															sm.select(items[i].store.findRecord('handOverBillNo',handOverBillNo,0,false,true,true),true);//反选运单，但不触发运单deselect事件
//														}
//													}
//												}
												firstElMap = firstPlugin.elementIdMap,
												firstId  = firstRecord.internalId+'-rowbody-element',
												secondGrid = firstElMap.get(firstId),
												secondRecord = secondGrid.store.findRecord('handOverBillNo',handOverBillNo,0,false,true,true),
												secondSm = secondGrid.getSelectionModel();
												secondSm.select(secondRecord,true,true);
										}
										//如果全被勾选，则将运单号的全选字段置为1
										if(checkBoxGroup.getChecked().length == boxesCount){
											superRecord.set('isSelectAll','Y');
										}
									}else{
										 //将运单号的全选字段置为0
										superRecord.set('isSelectAll','N');
										//将配载单是否全选字段设置为N
										var firstGrid = unload.unloadtaskconfirmlong.tGrid;
										var firstRecord = firstGrid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
										firstRecord.set('isSelectAll','N');
										//如果全部反选，则将运单号反选
										if(checkBoxGroup.getChecked().length == 0){
											var sm = superGrid.getSelectionModel();
											sm.deselect(superRecord,false);
										}
									}
								});
							}
							//添加重新渲染
							tmpForm.add(checkBoxGroup);
							tmpForm.checkBoxGroup=checkBoxGroup;
							tmpForm.doLayout();
						}							
					}			
					myMask.hide();
				},
				failure : function(response){
					var result = Ext.decode(response.responseText);
					myMask.hide();
				}
	       	});
		}
	},
	checkBoxGroup : null,
	getCheckBoxGroup : function(){
		return this.checkBoxGroup;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**————————————————————————以下为添加多货部分——————————————————————**/

//定义多货运单model
Ext.define('Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'action',
		type : 'string'
	},{	
		name : 'cargoNo',
	    type : 'string'
    },{
	   name : 'cargoType',
	   type : 'string'
    },{
		name : 'isSelectAll',//用来标记子表是否被全选
		type : 'string',
		defaultValue : 'N'
	}]
});

//定义多货运单store
Ext.define('Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义多货运单grid
Ext.define('Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillGrid', {
	extend : 'Ext.grid.Panel',
	title : '多货明细',
	frame: true,
	cls : 'autoHeight',
	bodyCls:'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	tbar : ['->',{
		xtype : 'button',
		text : '添加卸车多货',
		handler : function(){
			//显示窗口
			if(unload.unloadtaskconfirmlong.addMoreGoodsWindow == null){
				unload.unloadtaskconfirmlong.addMoreGoodsWindow = Ext.create('Foss.unload.unloadtaskconfirmlong.addMoreGoodsWindow');
			}
			unload.unloadtaskconfirmlong.addMoreGoodsWindow.show();
		}
	}],
	bbar : [{
		xtype : 'button',
		text : '删除',
		handler : function(){
			//获取多货运单选中的行
			var grid = unload.unloadtaskconfirmlong.bGrid;
			var store = grid.store;
			if(store.getCount() == 0){
				Ext.ux.Toast.msg('提示', '未添加任何多货明细！', 'error', 1000);
				return;
			}
			var selectedList = grid.getSelectionModel().getSelection();
			if(selectedList.length == 0){
				Ext.ux.Toast.msg('提示', '没有选择任何多货明细！', 'error', 1000);
				return;
			}
			//获取展开组件
			var plugin = grid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid');
			//遍历选中的运单列
			for(var i in selectedList){
				var record = selectedList[i];
				//获取选中行的运单号
				var waybillNo = record.get('waybillNo');
				//如果该运单下的流水号被全选，则直接移除运单
				if(record.get('isSelectAll') == 'Y'){
					store.remove(record);
				}else{//如果未被全选，则移除选中的流水号
					//展开运单
					var node = Ext.get(grid.getView().getNode(record));
					if(	node.hasCls(plugin.rowCollapsedCls)){
						plugin.toggleRow(grid.getView().getNode(record));							
					}
					//移除流水号
					if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var panel = items[i];
						var form = panel.getForm();
						var superWaybillNo = form.findField('waybillNo').getValue();
						if(superWaybillNo == waybillNo){
							var checkBoxes = panel.getCheckBoxGroup().items.items;
							for(var j in checkBoxes){
									//如果流水号被选中，则移除流水号
									if(checkBoxes[j].getValue()){
										panel.getCheckBoxGroup().remove(checkBoxes[j]);
									}
								}
								//选中的流水号被移除后，反选运单行
								var sm = grid.getSelectionModel();
								sm.deselect(record,true);//反选运单，但不触发运单deselect事件
								panel.doLayout();
							}
						}
					}
				}
			}
		}
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillStore'),
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	hideHeaders : true,
	columns: [{
		align : 'left',
		flex : .5,
		dataIndex : 'waybillNo',
		renderer : function(value){
			if(value != null){
				return '运单号	' + value;
			}
		}
	},{
		align : 'right',
		flex : .5,
		dataIndex : 'action',
		renderer : function(value,md,record){
			var waybillNo = record.get('waybillNo');
			return '<a href="javascript:unload.unloadtaskconfirmlong.addMoreGoodsFromColumn('+"'" + waybillNo + "'"+')">' + '添加卸车多货' + '</a>';
		}
	}],
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		pluginId : 'unloadtaskconfirmlong_addGoodsWaybillGrid',
		// 行体内容
		rowBodyElement : 'Foss.unload.unloadtaskconfirmlong.addGoodsSerialNoPanel'
	}],
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			//将运单列isSelectAll置为Y
			record.set('isSelectAll','Y');
			//获取运单号
			var waybillNo = record.get('waybillNo');
			var grid = this;
			var plugin = grid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid');
			//如果运单下panel被展开，则将所有流水号选中
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var panel = items[i];
						var form = panel.getForm();
						var superWaybillNo = form.findField('waybillNo').getValue();
						if(superWaybillNo == waybillNo){
							var checkBoxes = panel.getCheckBoxGroup().items.items;
							for(var j in checkBoxes){
								checkBoxes[j].setValue(true);
							}
						}
					}
			}
		},
		'deselect' : function(rowModel,record,index,eOpts){
			//将运单列isSelectAll置为N
			record.set('isSelectAll','N');
			//获取运单号
			var waybillNo = record.get('waybillNo');
			var grid = this;
			var plugin = grid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid');
			//如果运单下panel被展开，则将所有流水号反选
			if(!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for(var i in items){
						var panel = items[i];
						var form = panel.getForm();
						var superWaybillNo = form.findField('waybillNo').getValue();
						if(superWaybillNo == waybillNo){
							var checkBoxes = panel.getCheckBoxGroup().items.items;
							for(var j in checkBoxes){
								checkBoxes[j].setValue(false);
							}
						}
					}
			}
		}
	}
});

//点击多货列表中的“添加卸车多货”链接时触发
unload.unloadtaskconfirmlong.addMoreGoodsFromColumn = function(waybillNo){
	//打开窗口，并且把运单号填写好
	var window = unload.unloadtaskconfirmlong.addMoreGoodsWindow;
	//获取窗口中的运单号控件
	var waybillNoCmp = window.items.items[0].items.items[0];
	waybillNoCmp.setValue(waybillNo);
	window.show();
}

Ext.apply(Ext.form.VTypes,     
		{    
	      serialNo: function(val, field)     
		  {    
	    	  if(!isNaN(val)){
	    		  return true;
	    	  }
		        return false;    
		  },    
		  serialNoText: '流水号只能为数字！',    
		});  

//新增多货表单
 Ext.define('Foss.unload.unloadtaskconfirmlong.addMoreGoodsForm', {
	extend : 'Ext.form.Panel',
	bodyStyle : 'padding:5px 10px 0 10px',
	defaults : {
		xtype : 'textfield'
	},
	items : [{
		fieldLabel: '运单号/包/笼',
		name: 'waybillNo',
		allowBlank : false,
		enableKeyEvents : true,
		vtype : 'alphanum',
		listeners : {
			'keypress' : function(text,e,eOpts){
				//如果敲击回车键，则流水号获取焦点
				if(e.getKey() == e.ENTER){
					var form = this.up('form').getForm();
					form.findField('serialNo').focus();
				}
			}
		}
	},{
		fieldLabel : '流水号',
		name : 'serialNo',
		allowBlank : false,
		enableKeyEvents : true,
		vtype : 'serialNo',
		listeners : {
			'keypress' : function(text,e,eOpts){
				//如果敲击回车键，则触发添加按钮事件
				if(e.getKey() == e.ENTER){
					var addButton = unload.unloadtaskconfirmlong.addMoreGoodsWindow.dockedItems.items[1].items.items[0];
					addButton.handler();
				}
			}
		}
	},{
		boxLabel : '连续新增',
		name : 'isQuickAdd',
		xtype : 'checkbox',
		margin : '5 0  0 0',
		checked : true
	}]
});
	
//新增多货窗口
Ext.define('Foss.unload.unloadtaskconfirmlong.addMoreGoodsWindow', {
		extend : 'Ext.window.Window',
		title : '多货信息',
		modal : true,
		closeAction : 'hide',
		width : 300,
		height : 180,
		layout : 'auto',	
		items : [
			Ext.create('Foss.unload.unloadtaskconfirmlong.addMoreGoodsForm')
		],
		buttons : [{
			text : '添加',
			margin : '0 12 0 0',
			handler : function(){
				var form = this.ownerCt.ownerCt.items.items[0].getForm();
				if(form.isValid()){
					//获取多货列表的grid和store
					var grid = unload.unloadtaskconfirmlong.bGrid;
					var store = grid.store;
					var waybillNo = form.findField('waybillNo').getValue();
					var serialNo = form.findField('serialNo').getValue();
					var isQuickAdd = form.findField('isQuickAdd').getValue();
					//判断输入的运单号、流水号是否合法
					var myMask = new Ext.LoadMask(this.ownerCt.ownerCt,{
								msg:"添加中，请稍候..."
					});
					myMask.show();
					Ext.Ajax.request({
						url : unload.realPath('validateWaybillNoAndSerialNo.action'),
						params : {
							'unloadTaskVo.waybillNo' : waybillNo,
							'unloadTaskVo.serialNo' : serialNo,
							'unloadTaskVo.unloadTaskId' : unload.unloadtaskconfirmlong.unloadTaskId,
							'unloadTaskVo.unloadTaskNo'  :unload.unloadtaskconfirmlong.unloadTaskNo,
							'unloadTaskVo.unloadType' : 'LONG_DISTANCE'
						},
						success : function(response){
							var unloadTaskVo = Ext.decode(response.responseText).unloadTaskVo;
							var isAddedNosRight = unloadTaskVo.isAddedNosRight;
							
							 var msgData=null;
							 if(Ext.decode(response.responseText).message!=""
								 &&Ext.decode(response.responseText).message!=null
								 &&Ext.decode(response.responseText).message.indexOf('exMsg')!=-1){
								 msgData=Ext.decode(Ext.decode(response.responseText).message); 
								 Ext.ux.Toast.msg('提示',msgData.exMsg, 'error', 2000);
								 myMask.hide();
								 return;
							 }else if(Ext.decode(response.responseText).message!=""
								 &&Ext.decode(response.responseText).message!=null
								 &&Ext.decode(response.responseText).message.indexOf('cargoNo')!=-1){
								 msgData=Ext.decode(Ext.decode(response.responseText).message); 
							 }else{
								 //nothing todo here
							 }
			
							if(isAddedNosRight == 0){
								Ext.ux.Toast.msg('提示', '添加失败，运单号或流水号不存在！', 'error', 2000);
								myMask.hide();
								return;
							}else if(isAddedNosRight == 1){
								Ext.ux.Toast.msg('提示', '添加失败，本次卸车任务包括该件货物！', 'error', 2000);
								myMask.hide();
								return;
							}else{
								//定义checkboxgroup
								var checkBoxGroup;
								//定义待添加的checkbox
								var serialNoBox;
								//如果待添加的运单号已经在列表中存在
								var waybillRec = store.findRecord('waybillNo',waybillNo,0,false,true,true);
								if(waybillRec != null){
									//如果运单号已经添加过，则获取运单列的扩展组件，再checkboxgroup中添加一个checkbox
									var plugin = grid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid');
									//展开运单
									var node = Ext.get(grid.getView().getNode(waybillRec));
									if(	node.hasCls(plugin.rowCollapsedCls)){
										plugin.toggleRow(grid.getView().getNode(waybillRec));							
									}
									//获取该运单下的流水号panel
									var items = plugin.getExpendRowBody();
									var serialNoPanel;
									for(var i in items){
										var panel = items[i];
										if(panel.getForm().findField('waybillNo').getValue() == waybillNo){
											serialNoPanel = panel;
										}
									}
									//获取流水号panel中的checkboxgroup
									checkBoxGroup = serialNoPanel.getCheckBoxGroup();
									var checkBoxes = checkBoxGroup.items.items;
									//看流水号是否已被添加
									for(var i in checkBoxes){
										if(checkBoxes[i].serialNo == serialNo){
											Ext.ux.Toast.msg('提示', '操作失败，该流水号已经被添加！', 'error', 1000);
											myMask.hide();
											return false;
										}
									}
									//构建新的流水号checkbox
									serialNoBox = new Ext.form.field.Checkbox({
										boxLabel : '流水号 ' + serialNo,
										serialNo : serialNo,
										margin : '5 0 10 0'
									});
									checkBoxGroup.add(serialNoBox);
									serialNoPanel.doLayout();
									//将上级运单的isSelectAll设置为N
									waybillRec.set('isSelectAll','N');
								}else{
									//如果运单号从未被添加过，则直接展开该运单列，获取运单列组件，添加checkboxgroup
									var o=null; 
									if(msgData!=null){
									   o = {'waybillNo' : waybillNo,'cargoNo':msgData.cargoNo,'cargoType':msgData.cargoType}; 
									 }else{
									   o = {'waybillNo' : waybillNo}; 
									 }
									
									var record = Ext.ModelManager.create(o,'Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillModel');
									store.insert(store.getCount(),record);
									waybillRec = record;
									var plugin = grid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid');
									//展开该行运单
									plugin.toggleRow(grid.getView().getNode(record));
									//因为是新增，所以肯定是最后一个，直接取到
									var items = plugin.getExpendRowBody();
									var serialNoPanel = items[items.length - 1];
									checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
												fieldLabel : '',
												labelSeparator : '',		
												margin : '0 0 0 6',
												defaults : { 
												    columnWidth : .2,
												    margin : '5 0 10 0'
												},
												layout : 'column'
									});
									var waybillCmp = {
										xtype : 'textfield',
										name : 'waybillNo',
										value : waybillNo,
										hidden : true
									};
									serialNoBox = new Ext.form.field.Checkbox({
										boxLabel : '流水号 ' + serialNo,
										serialNo : serialNo,
										margin : '5 0 10 0'
									});
									checkBoxGroup.add(serialNoBox);
									serialNoPanel.add(waybillCmp);
									serialNoPanel.add(checkBoxGroup);
									serialNoPanel.checkBoxGroup = checkBoxGroup;
									serialNoPanel.doLayout();
								}
								//为新增的checkbox添加change事件
								serialNoBox.addListener('change',function(field,newValue,oldValue,eOpts){
										var boxesCount = checkBoxGroup.items.items.length;
										if(newValue){
												//如果是第一个被勾选，则勾选上级运单号，
												if(checkBoxGroup.getChecked().length == 1){
													//将父表格的行选中
													var sm = grid.getSelectionModel();
													sm.select(waybillRec,true,true);//选中第一层表格中的行：第三个参数为true，表示不触发该条记录的选择事件
												}
												//如果全被勾选，则将运单号的全选字段置为1
												if(checkBoxGroup.getChecked().length == boxesCount){
													waybillRec.set('isSelectAll','Y');
												}
											}else{
												 //将运单号的全选字段置为0
												waybillRec.set('isSelectAll','N');
												//如果全部反选，则将运单号反选
												if(checkBoxGroup.getChecked().length == 0){
													var sm = grid.getSelectionModel();
													sm.deselect(waybillRec,false);//反选第一层表格中的行：第二个参数为true，表示触发该条记录的反选事件
												}
											}
									});
								//若勾选了“连续新增”，则不关闭窗口，但清空“流水号”控件
								if(!isQuickAdd){
									unload.unloadtaskconfirmlong.addMoreGoodsWindow.close();
								}else{
									//重置流水号控件并获取焦点，方便输入
									form.findField('serialNo').reset();
									form.findField('serialNo').focus();
								}
								Ext.ux.Toast.msg('提示', '添加成功！', 'info', 1000);
							}
							myMask.hide();
						}
					});
				}
			}
		}],
		listeners : {
			'beforehide' : function(cmp,eOpts){
				var form = cmp.items.items[0].getForm();
				form.reset();
			}
		}
	});

//第三层流水号panel
Ext.define('Foss.unload.unloadtaskconfirmlong.addGoodsSerialNoPanel', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	height : 200,
	bodyCls :'autoHeight',
	baseCls : 'unload_unloadconfirm_waybillGap',
	fieldDefaults : {
		labelWidth: 110
	},
	items : [this.getCheckBoxGroup],
	margin : '0 0 0 20',
	bindData : function(record,grid,rowBody){
		var isSelectAll = record.get('isSelectAll');
		//上级运单是否被选择
		var isSelect =  rowBody.superGrid.getSelectionModel().isSelected(record);
		if(isSelectAll == 'Y'){
			//如果被全选，则勾选全部checkbox
			var checkBoxes = rowBody.getCheckBoxGroup().items.items;
			for(var i in checkBoxes){
				checkBoxes[i].setValue(true);
			}
		}
		//如果运单是反选状态，则将所有流水号反选
		if(!isSelect){
			//如果被全选，则勾选全部checkbox
			if(rowBody.getCheckBoxGroup() != null){
				var checkBoxes = rowBody.getCheckBoxGroup().items.items;
					for(var i in checkBoxes){
						checkBoxes[i].setValue(false);
					}
			}
		}
	},
	checkBoxGroup : null,
	getCheckBoxGroup : function(){
		return this.checkBoxGroup;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	//上方grid
	unload.unloadtaskconfirmlong.tGrid = Ext.create('Foss.unload.unloadtaskconfirmlong.billInfoGrid');
	//下方grid
	unload.unloadtaskconfirmlong.bGrid = Ext.create('Foss.unload.unloadtaskconfirmlong.moreGoodsWaybillGrid');
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-unloadtaskconfirmlongindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [{
			layout : 'column',
			columnWidth : 1,
			defaults : {
				margin : '5 5 10 5'	
			},
			items : [{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : .27,
					name : 'unloadTaskNo',
					fieldLabel : '卸车任务编号',
					id : 'Foss_unload_unloadtaskconfirmlong_unloadTaskNo_ID'
				},{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : .2,
					name : 'vehicleNo',
					fieldLabel : '车牌号',
					id : 'Foss_unload_unloadtaskconfirmlong_vehicleNo_ID'
				},{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : .2,
					name : 'platformNo',
					fieldLabel : '月台号',
					id : 'Foss_unload_unloadtaskconfirmlong_platformNo_ID'
				},{
					xtype : 'textfield',
					columnWidth : .22,
					name : 'waybillNo',
					vtype : 'alphanum',
					fieldLabel : '运单号',
					id : 'Foss_unload_unloadtaskconfirmlong_waybillNo_ID'
				},{
					xtype : 'button',
					columnWidth : .08,
					cls : 'btnAfterTextfield',
					plugins : [{
						seconds: 5,
						ptype : 'buttonlimitingplugin'
					}],
					text : '快速定位',
					handler : function(){
						//获取运单号控件
						var waybillNoCmp = Ext.getCmp('Foss_unload_unloadtaskconfirmlong_waybillNo_ID');
						//运单号非空
						if(Ext.isEmpty(waybillNoCmp.getValue())){
							Ext.ux.Toast.msg('提示', '请输入运单号！', 'error', 1000);
							return;
						}else{
							//请求后台，获取配载单、交接单号list
							var grid = unload.unloadtaskconfirmlong.tGrid;
							var waybillNo = waybillNoCmp.getValue();
							var plugin  = grid.getPlugin('unloadtaskconfirmlong_handOverBillGrid');
							//弹出数据加载，禁止操作
							var myMask = new Ext.LoadMask(grid,{
								msg:"定位中，请稍候..."
							});
							myMask.show();
							Ext.Ajax.request({
							url : unload.realPath('queryBillNosListByWaybillNo.action'),
							params : {'unloadTaskVo.unloadTaskId' : unload.unloadtaskconfirmlong.unloadTaskId,
								      'unloadTaskVo.waybillNo' : waybillNo,
								      'unloadTaskVo.unloadType':unload.unloadtaskconfirmlong.unloadType,
								      'unloadTaskVo.unloadTaskNo':unload.unloadtaskconfirmlong.unloadTaskNo},
							success : function(response){
								var unloadTaskVo = Ext.decode(response.responseText).unloadTaskVo;
								//配载单、交接单号list
								var billNosList = unloadTaskVo.billNosList;
								if(billNosList.length == 0){
									Ext.ux.Toast.msg('提示', '本次卸车任务没有该运单！', 'error', 2000);
									myMask.hide();
								}else{
									//遍历返回的list
									for(var i in billNosList){
										//配载单号
										var vehicleAssembleNo = billNosList[i].vehicleAssembleNo;
										var handOverBillNo = billNosList[i].handOverBillNo;
										//从一级grid中获取该行配载单记录，展开
										var record = grid.store.findRecord('billNo',vehicleAssembleNo,0,false,true,true);
										var node = Ext.get(grid.getView().getNode(record));
										Ext.Object.merge(grid, {
											openChildEnable: false,
											openChild: function(handOverGrid){
												//获取列，展开
												var handOverRecord = handOverGrid.store.findRecord('handOverBillNo',handOverBillNo,0,false,true,true),
												handOverNode = Ext.get(handOverGrid.getView().getNode(handOverRecord)),
												handOverGridPlugin = handOverGrid.getPlugin('unloadtaskconfirmlong_waybillGrid');
												Ext.Object.merge(handOverGrid, {
													openChildEnable: false,
													openChild: function(waybillGrid){
														//获取运单列，展开，着色
														var waybillRecord = waybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
														if(waybillRecord!=null){
															waybillNode = Ext.get(waybillGrid.getView().getNode(waybillRecord));
															waybillPlugin = waybillGrid.getPlugin('unloadtaskconfirmlong_serialNoPanel');
															//展开该运单列
															if(waybillNode.hasCls(waybillPlugin.rowCollapsedCls)){
																waybillPlugin.toggleRow(waybillNode);
															}
															waybillNode.highlight("ff00ff", { attr: 'color', duration: 5000 });
														}else{
															var expressWaybillRecord = handOverGrid.store.findRecord('handOverBillNo',waybillNo,0,false,true,true);
															
															expresswaybillNode = Ext.get(handOverGrid.getView().getNode(expressWaybillRecord));
															expressWaybillPlugin = handOverGrid.getPlugin('unloadtaskconfirmlong_waybillGrid');
															//展开该运单列
															if(expresswaybillNode.hasCls(expressWaybillPlugin.rowCollapsedCls)){
																expressWaybillPlugin.toggleRow(expresswaybillNode);
															}
															expresswaybillNode.highlight("ff00ff", { attr: 'color', duration: 5000 });
														}
														
													}
												});
												//展开该运单列
												if(handOverNode.hasCls(handOverGridPlugin.rowCollapsedCls)){
													handOverGrid.openChildEnable = true;
													handOverGridPlugin.toggleRow(handOverNode);
												}else{
													var waybillGrid = handOverGridPlugin.getRowBodyElement(handOverRecord);
													handOverGrid.openChild(waybillGrid);
												}
											}
										});
										if(node==null){
											myMask.hide();
											continue;
										}
										//展开该配载单列
										if(node.hasCls(plugin.rowCollapsedCls)){
											grid.openChildEnable = true;
											plugin.toggleRow(node);
										}else{
											//获取列，展开
											var handOverGrid = plugin.getRowBodyElement(record);
											grid.openChild(handOverGrid);
										}
								   	}
								}
								myMask.hide();
							}
						});
					}
				}
			}]
		},
		unload.unloadtaskconfirmlong.tGrid,
		unload.unloadtaskconfirmlong.bGrid,{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ { 
					xtype : 'container',
					columnWidth : .9,
					html : '&nbsp'
			}, {
				columnWidth : .1,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				id : 'Foss_unload_unloadtaskconfirmlong__confirmButton_ID',
				text : '确认卸车结束',
				handler : function() {
					//获取少货明细grid和多货明细grid
					var tGrid = unload.unloadtaskconfirmlong.tGrid,
						bGrid = unload.unloadtaskconfirmlong.bGrid;
					//获取少货明细中勾选的record
					var selectedFirstBillList = tGrid.getSelectionModel().getSelection();
					//定义被全选的少货的配载单、交接单、运单list、流水号list；多货的流水号list
					var seVehicleAssembleBillList = new Array(),
						seHandOverBillList = new Array(),
						seWaybillList = new Array(),
						seSerialNoList = new Array();
						moSerialNoList = new Array();
					/**
					 * 少货数据收集思路
					 * 1、获取所有被勾选的配载单list；
					 * 2、遍历第一步的list，从配载单grid的plugin的elementIdMap中获取该条记录id对应的value，
					 * 		 2.1若返回为空，表明该配载单从未被打开，即直接全选，将配载单放入seVehicleAssembleBillList中；
					 * 		 2.2若返回不为空，则表明配载单被打开过，获取配载单下所有被选中的交接单list；
					 * 		 2.3遍历上一步获取到的list，从交接单grid的plugin的elementIdMap中获取该条记录id对应的value，
					 * 			 2.3.1若返回为空，表明该交接单从未被打开，即直接全选，将交接单放入seHandOverBillList中；
					 * 			 2.3.2若返回不为空，则表明交接单被打开过，获取交接单下所有被选中的运单list；
					 * 			  		2.3.2.1若运单记录的isAllSelect为Y，则直接将该运单放入seWaybillList中
					 * 			  		2.3.2.2若运单记录的isAllSelect为N，则获取流水号panel，将选中的流水号放入seSerialNoList中
					 */
					var firstPlugin = tGrid.getPlugin('unloadtaskconfirmlong_handOverBillGrid'),
									   firstMap = firstPlugin.elementIdMap;
					for(var i in selectedFirstBillList){
						var vehicleAssembleBill = selectedFirstBillList[i];
						var firstId = vehicleAssembleBill.internalId+'-rowbody-element';
						//说明该条配载单未被展开过，直接全选的
						if(firstMap.get(firstId) == null){
							var firstBillEntity = vehicleAssembleBill.data;
							Ext.Object.merge(firstBillEntity, {
												vehicleAssembleNo : vehicleAssembleBill.get('billNo')
											});
							seVehicleAssembleBillList.push(firstBillEntity);
						}else{
							//如果被展开过，获取展开后的交接单grid
							var secondGrid = firstMap.get(firstId),
								secondPlugin = secondGrid.getPlugin('unloadtaskconfirmlong_waybillGrid'),
								secondMap = secondPlugin.elementIdMap;
							//获取交接单列表中被勾选的列
							var selectedSecondList = secondGrid.getSelectionModel().getSelection();
							//遍历被勾选的交接单list
							for(var j in selectedSecondList){
								var handOverBill = selectedSecondList[j],
									secondId = handOverBill.internalId+'-rowbody-element';
								//如果plugin的idMap中获取不到记录，则表明交接单被全选 
								if(secondMap.get(secondId) == null||secondMap.get(secondId).getSelectionModel().getSelection().length==0){
									var secondBillEntity = handOverBill.data;
									seHandOverBillList.push(secondBillEntity);
								}else{
									var thirdGrid = secondMap.get(secondId);
									//获取所有被勾选的运单列
									var selectedThirdList = thirdGrid.getSelectionModel().getSelection();
									//遍历勾选的运单列
									for(var k in selectedThirdList){
										var waybill = selectedThirdList[k];
										//如果运单被全选，将运单放入seWaybillList
										if(waybill.get('isSelectAll') == 'Y'){
											seWaybillList.push(waybill.data);
										}else{
											//获取该条运单展开后的serialNoPanel
											var waybillId = waybill.internalId + '-rowbody-element';
											var waybillPlugin = thirdGrid.getPlugin('unloadtaskconfirmlong_serialNoPanel');
											var serialNoPanel = waybillPlugin.elementIdMap.get(waybillId);
											//获取所有被勾选的serialNo
											var checkBoxes = serialNoPanel.getCheckBoxGroup().items.items;
											for(var k in checkBoxes){
												var checkBox = checkBoxes[k];
												//如果被勾选，则构造对象，将流水号放入seSerialNoList中
												if(checkBox.getValue()){
													var serialNo = {
														'vehicleAssembleNo' : vehicleAssembleBill.get('billNo'),
														'handOverBillNo' : handOverBill.get('handOverBillNo'),
														'waybillNo' : waybill.get('waybillNo'),
														'serialNo' : checkBox.serialNo
													};
													seSerialNoList.push(serialNo);
												}
											}
										}
									}
								}
							}
						}
					}
					//收集多货数据
					var bStore = bGrid.store,
						bPlugin = bGrid.getPlugin('unloadtaskconfirmlong_addGoodsWaybillGrid'),
						bMap = bPlugin.elementIdMap;
					bStore.each(function(record){
						var id = record.internalId+'-rowbody-element';
						var serialNoPanel = bMap.get(id);
						var checkBoxes = serialNoPanel.getCheckBoxGroup().items.items;
						//将流水号放置于moSerialNoList
						for(var i in checkBoxes){
							var serialNo = {
								'serialNo' : checkBoxes[i].serialNo,
								'waybillNo' : record.get('waybillNo'),
								'cargoNo' : record.get('cargoNo'),
								'cargoType' : record.get('cargoType')
							};
							moSerialNoList.push(serialNo);
						}
					});
					var alertInfo;
					if(selectedFirstBillList.length == 0 && bStore.getCount() == 0){
						alertInfo = '本次卸车没有任何异常';
					}else if(selectedFirstBillList.length == 0 && bStore.getCount() != 0){
						alertInfo = '本次卸车存在有多货异常';
					}else if(selectedFirstBillList.length != 0 && bStore.getCount() == 0){
						alertInfo = '本次卸车存在有少货异常';
					}else{
						alertInfo = '本次卸车存在有少货、多货异常';
					}
					//对话框
					Ext.MessageBox.confirm('提示', alertInfo + '，确定结束卸车任务吗？',function(btn){
						if(btn == 'yes'){
							//构造jsonData
							var jsonData = {
								'unloadTaskVo' : {
									'confirmUnloadTaskDto' : {
										'unloadTaskId' : unload.unloadtaskconfirmlong.unloadTaskId,
										'unloadTaskNo' : Ext.getCmp('Foss_unload_unloadtaskconfirmlong_unloadTaskNo_ID').getValue(),
										'lackVehicleAssembleBillList' : seVehicleAssembleBillList,
										'lackHandOverBillList' : seHandOverBillList,
										'lackWaybillList' : seWaybillList,
										'lackSerialNoList' : seSerialNoList,
										'moreSerialNoList' : moSerialNoList
									}
								}
							};
							//mask
							var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskconfirmlongindex_content'),{
								msg:"数据提交中，请稍候..."
							});
							myMask.show();
							Ext.Ajax.request({
								url : unload.realPath('confirmUnloadTask.action'),
								jsonData : jsonData,
								timeout : 300000,
								success : function(response){
									Ext.Msg.show({
										    title : '提示',
										    msg : '操作成功，卸车任务已结束！',
										    buttons : Ext.Msg.OK,
										    icon : Ext.Msg.INFO
										});
										myMask.hide();
										//隐藏确认卸车结束按钮
										Ext.getCmp('Foss_unload_unloadtaskconfirmlong__confirmButton_ID').setVisible(false);
									},
									exception : function(response){
										var result = Ext.decode(response.responseText);
								    	top.Ext.MessageBox.alert('提示','操作失败，' + result.message);
								    	myMask.hide();
									},
									failure : function(){
										myMask.hide();
										console.log('确认卸车任务服务端异常！');
									}
							});
						}
					});
				}
			} ]
		}],
		renderTo : 'T_unload-unloadtaskconfirmlongindex-body'
	});
	
	//加载卸车任务数据
	//弹出数据加载，禁止操作
	var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskconfirmlongindex_content'),{
		msg:"加载中，请稍候..."
			});
	myMask.show();
	Ext.Ajax.request({
		url : unload.realPath('loadUnloadTaskInfo.action'),
		params : {
			'unloadTaskVo.unloadTaskId' : unload.unloadtaskconfirmlong.unloadTaskId
			},
		success : function(response){
			var unloadTaskVo = Ext.decode(response.responseText).unloadTaskVo;
			//获取基本信息
			var baseEntity = unloadTaskVo.baseEntity;
			
			unload.unloadtaskconfirmlong.unloadTaskNo=baseEntity.unloadTaskNo;
			unload.unloadtaskconfirmlong.unloadType=baseEntity.unloadType;
			//若卸车任务已被取消或者已完成
			if(baseEntity == null){
				Ext.ux.Toast.msg('提示', '卸车任务不存在或者已经被取消！', 'error', 4000);
				Ext.getCmp('mainAreaPanel').remove('T_unload-unloadtaskconfirmlongindex',true);
				myMask.hide();
				return;
			}
			if(baseEntity.taskState == 'FINISHED'){
				Ext.ux.Toast.msg('提示', '卸车任务已经结束，无法再次确认！', 'error', 4000);
				Ext.getCmp('mainAreaPanel').remove('T_unload-unloadtaskconfirmlongindex',true);
				myMask.hide();
				return;
			}
			//获取单据列表
			var billDetailList = unloadTaskVo.billDetailList;
			//给车牌号、月台号、卸车任务编号赋值
			Ext.getCmp('Foss_unload_unloadtaskconfirmlong_unloadTaskNo_ID').setValue(baseEntity.unloadTaskNo);
			Ext.getCmp('Foss_unload_unloadtaskconfirmlong_vehicleNo_ID').setValue(baseEntity.vehicleNo);
			Ext.getCmp('Foss_unload_unloadtaskconfirmlong_platformNo_ID').setValue(baseEntity.platformNo);
			//单据列表赋值
			unload.unloadtaskconfirmlong.tGrid.store.loadData(billDetailList);
			myMask.hide();
		}
	});
});