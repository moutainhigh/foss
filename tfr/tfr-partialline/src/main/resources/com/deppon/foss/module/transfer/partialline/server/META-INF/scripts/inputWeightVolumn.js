/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
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
//定义子母件修改信息基本信息Model
Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [
		{name : 'parentWaybillNo',type : 'string'},//母件单号
		{name : 'totalNum',type : 'number'}, //总票数
		{name : 'totalWeight',type : 'string'},//重量
		{name : 'totalVolumn',type : 'string'}, //体积
		{name : 'modifyUserName',type : 'string'},//修改人
		{name : 'modifyTime',type : 'string'}, //修改时间
		{name : 'isLoad',type : 'string', defaultValue: '0'} //修改时间
	]
});

//定义子母件修改明细Model
Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnInfoModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [ 
		{name : 'parentWaybillNo',type : 'string'},//母件单号
		{name : 'waybillNo', type : 'string'},
		{name : 'isPicPackage',type : 'string'}, //子母件类型
		{name : 'weight',type : 'number'},//重量
		{name : 'volumn',type : 'number'}, //体积
		{name : 'canModify',type:'string'}//是否能修改
	]
});

// 定义子母件修改信息基本信息Store
Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoModel',
	autoLoad : true,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : partialline.realPath('queryInputWeightVolumnBaseInfo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.list',
			totalProperty : 'totalCount'
			//successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.inputWeightVolumn.queryForm;
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
		//用于支持勾选翻页
		load:function( store, records,successful,operation,eOpts ){
			partialline.inputWeightVolumn.inputWeightWaybillInfoMap.clear();
			var record;
			for(var i in records){
				record = records[i];
				var parentWaybillNo = record.get('parentWaybillNo');
				var mapKey = parentWaybillNo;
			//	partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.add(mapKey,record);
				partialline.inputWeightVolumn.inputWeightWaybillInfoMap.add(mapKey,record);
				if(partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.get(mapKey) != null){
					var selectRecord = partialline.inputWeightVolumn.inputWeightWaybillInfoMap.get(mapKey);
					partialline.inputWeightVolumn.searchResultGrid.getSelectionModel().select(selectRecord,true,true);
				}
			}
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
		}
	}
});

// 定义子母件修改明细store
Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.partialline.inputweightvolumn.InputWeightVolumnInfoModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : partialline.realPath('queryInputWeightVolumnInfo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.list',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
		
	}
});
//查询form表单 
Ext.define('Foss.partialline.inputweightvolumn.form.searchForm',{
	extend: 'Ext.form.Panel',
//	id: 'Foss.express.printCZMTips.form.searchForm.id',
	title : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.query'), //查询,
	layout:'column',
	frame: true,
//	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
//	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
//		anchor: '90%',
		labelWidth:60
	},
	items: [{
		fieldLabel: partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.waybillNo'),//运单号
		columnWidth :.3,
		name : 'waybillNo',
		labelWidth : 60,
		xtype : 'textarea',
		emptyText : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation')
	},{
		fieldLabel: partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.handOverbillNo'),//交接单号
		name: 'handoverNo',
		xtype: 'textarea',
		labelWidth : 60,
		allowBlank:true,
		columnWidth:.3,
		emptyText : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo')
	},{//入库时间
		xtype : 'rangeDateField',
		fieldLabel : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.inStockTime'),//入库时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.inputWeightVolumn_inStockTime_RangeDateField_ID',
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
		fieldLabel : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.handOverTime'),//交接时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.inputWeightVolumn_handOverTime_RangeDateField_ID',
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
			text: partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.reset'),//重置
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
			text: partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.button.label.query'),//查询
			cls:'yellow_button',
			handler: function() {
				var searchParms = this.up('form').getForm().getValues();
				if(!this.up('form').getForm().isValid()){
					return;
				}
				/*if(Ext.isEmpty(searchParms.waybillNo) && Ext.isEmpty(searchParms.handoverNo)) {
					Ext.ux.Toast.msg(partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.condition'), 'error', 3000); 
					return;
				}*/
				// 验证运单号输入的行数
				if (!Ext.isEmpty(searchParms.waybillNo)) {
					var arrayWaybillNo = searchParms.waybillNo.split('\n');
					if (arrayWaybillNo.length > 50) {
						Ext.ux.Toast.msg(partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'), 'error', 3000); // 
						return;	
					}
					for (var i = 0; i < arrayWaybillNo.length; i++) {
						if (Ext.isEmpty(arrayWaybillNo[i])) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.waybillNo.valitation'), 'error', 3000); 
							return;	
						}
					}
				}
				//验证交接单号输入行数
				if (!Ext.isEmpty(searchParms.handoverNo)) {
					var arrayHandOverNo = searchParms.handoverNo.split('\n');
					if (arrayHandOverNo.length > 50) {
						Ext.ux.Toast.msg(partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo'), 'error', 3000); 
						return;	
					}
					for (var i = 0; i < arrayHandOverNo.length; i++) {
						if (Ext.isEmpty(arrayHandOverNo[i])) {
							Ext.ux.Toast.msg(partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'), partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.searchForm.valitation.handOverbillNo'), 'error', 3000); // 运单号不能录入空回车
							return;	
						}
					}
				}
				if(this.up('form').getForm().isValid()){
					partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.clear();
					partialline.inputWeightVolumn.inputWeightVolumnInfoMap.clear();
					partialline.inputWeightVolumn.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

// 定义子母件修改信息grid
Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoGrid', {
	extend : 'Ext.grid.Panel',
	store : Ext.create('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoStore'),
	height: 500,
	autoScroll: true,
	columnLines: true,
	frame: true,
	forceFit: true,
	enableColumnHide: false,
    //sortableColumns: false,
    collapsible: true,
    animCollapse: true,
    emptyText : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.searchWaybill.empty'), //查询结果为空,
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		pluginId: 'Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoGrid_Plugin_ID',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.partialline.inputweightvolumn.InputWeightVolumnInfoGrid'
	}],
	tbar : [{
		xtype : 'button',
		text :  partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.button.label.confirm'),//'确认修改'
		handler : function(){
				var inputWeightVolumnBaseMapList = partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.getValues();
				if(!inputWeightVolumnBaseMapList.length > 0){
					Ext.ux.Toast.msg('提示信息', '请选择行！');
					return;
				}
				var list = new Array();
				for(var i=0;i<inputWeightVolumnBaseMapList.length;i++){
					var totalNum = inputWeightVolumnBaseMapList[i].data.totalNum; //总票数
					var parentWaybillNo = inputWeightVolumnBaseMapList[i].data.parentWaybillNo;
					var inputWeightVolumnInfoMap = partialline.inputWeightVolumn.inputWeightVolumnInfoMap.get(parentWaybillNo);
					var inputWeightVolumnInfoMapList = inputWeightVolumnInfoMap.getValues();
					//每次提交必须为整个子母件一起提交
					if(inputWeightVolumnInfoMapList == null || totalNum != inputWeightVolumnInfoMapList.length){
						Ext.ux.Toast.msg('提示信息', '请选择母件' + parentWaybillNo + '下的所有子件！');
						return;
					}
					//修改后的子母件每一票重量不能小于等于0
					for(var j=0;j<inputWeightVolumnInfoMapList.length;j++){
						var weight = inputWeightVolumnInfoMapList[j].data.weight;
						var volumn = inputWeightVolumnInfoMapList[j].data.volumn;
						if(weight <= 0){
							var waybillNo = inputWeightVolumnInfoMapList[j].data.waybillNo;
							Ext.ux.Toast.msg('提示信息', '运单号' + waybillNo + '的重量小于等于0，请检查！');
							return;
						}
						list.push(inputWeightVolumnInfoMapList[j].data);
					}
				}
				var myMask = new Ext.LoadMask(this.up('gridpanel'), {
					msg:"修改中，请稍候..."
				});
				var jsonParam = {vo:{list:list}};
				myMask.show();
				Ext.Ajax.request({
						url:partialline.realPath('addRecord.action'),
						jsonData: jsonParam,
						success: function(response){
							myMask.hide();
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg("提示", "修改成功");
							partialline.inputWeightVolumn.pagingBar.moveFirst();
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
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.button.label.import'),//'导入'
		handler : function(){
			var importWindow = Ext.getCmp('Foss_partialline_inputweightvolumn_window_importweightvolumn_ID');
			if(importWindow == null){
				importWindow=Ext.create('Foss.partialline.inputweightvolumn.window.importweightvolumn');								
			}
			    importWindow.center().show();
		}
	},
	{
		xtype : 'button',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.button.label.loadTemplate'),//'导入模板下载'
		handler : function(){
			filePath('inputWeight.xls');
		}
	}],
	columns : [{
		dataIndex : 'parentWaybillNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.parentWaybillNo')/*母件*/
	},
	{dataIndex : 'totalNum',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.totalNum')/*总票数*/
	},
	{dataIndex : 'totalWeight',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.weight')/*重量*/
	},
	{dataIndex : 'totalVolumn',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.volumn')/*体积*/
	},{dataIndex : 'modifyUserName',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.modifyUser')/*修改人*/
	},{dataIndex : 'modifyTime',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.modifyTime')/*修改时间*/
	},{
		dataIndex : 'isLoad',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		hidden:true
	}],
/*	bindData : function(record){
		var waybillNo = record.get('parentWaybillNo');
		var recordsMap = this.store.load({
			params : {
				'vo.parentWaybillNo' : parentWaybillNo
			}
		});
	},*/
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
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
		partialline.inputWeightVolumn.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	listeners: {
		select : function(rowModel, record, index, eOpts) {
				var grid = this,
				plugin = grid.getPlugin('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoGrid_Plugin_ID');
				var parentWaybillNo = record.get('parentWaybillNo');
				var mapKey = parentWaybillNo;
				partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.add(mapKey,record);
				var inputWeightVolumnInfoStore = Ext.create('Foss.partialline.inputweightvolumn.InputWeightVolumnInfoStore',{
					listeners: {
						'load': function(store, records, successful, eOpts ){
							var inputWeightVolumnInfoMap = new Ext.util.HashMap();
							for(var i = 0; i<records.length;i++){
								var chrecord = records[i];
								inputWeightVolumnInfoMap.add(chrecord.get('waybillNo'),chrecord);
							}
							partialline.inputWeightVolumn.inputWeightVolumnInfoMap.add(mapKey,inputWeightVolumnInfoMap);
							record.set('isLoad','1');
						}
					}
				});
				if(record.get('isLoad') == '0'){
					inputWeightVolumnInfoStore.load({
						params:{'vo.parentWaybillNo': parentWaybillNo
						}
					});	
				}else if(record.get('isLoad') == '1'){//点击过展开
					var item = plugin.getRowBodyElement(record);
					var store = item.getStore();
					var parentWaybillNo = store.getAt(0).get('parentWaybillNo');
					if(parentWaybillNo == record.get('parentWaybillNo')){
						item.getSelectionModel().selectAll(true);
						var selectedRecord = item.getSelectionModel().selected;
						var inputWeightVolumnInfoMap = new Ext.util.HashMap();
						for(var i = 0; i<selectedRecord.length;i++){
						    var waybillNo = selectedRecord.get(i).get('waybillNo');
						 //   var waybillNo = selectedRecord.get[i].get('waybillNo');
						  inputWeightVolumnInfoMap.add(selectedRecord.get(i).get('waybillNo'),selectedRecord.get(i));
						  // alert(i);
						}
						 partialline.inputWeightVolumn.inputWeightVolumnInfoMap.add(mapKey,inputWeightVolumnInfoMap)
					}
				}
/*				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var store = item.getStore();
					var parentWaybillNo = store.getAt(0).get('parentWaybillNo');
					if(parentWaybillNo == record.get('parentWaybillNo')){
						item.getSelectionModel().selectAll(true);
						var selectedRecord = item.getSelectionModel().selected;
						var inputWeightVolumnInfoMap = new Ext.util.HashMap();
						for(var i = 0; i<selectedRecord.length;i++){
						    var waybillNo = selectedRecord.get(i).get('waybillNo');
						 //   var waybillNo = selectedRecord.get[i].get('waybillNo');
						  inputWeightVolumnInfoMap.add(selectedRecord.get(i).get('waybillNo'),selectedRecord.get(i));
						  // alert(i);
						}
						 partialline.inputWeightVolumn.inputWeightVolumnInfoMap.add(mapKey,inputWeightVolumnInfoMap)
					}
				}*/
				
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this,
				plugin = grid.getPlugin('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoGrid_Plugin_ID');
				var parentWaybillNo = record.get('parentWaybillNo');
				var mapKey = parentWaybillNo;
				partialline.inputWeightVolumn.inputWeightVolumnInfoMap.removeAtKey(mapKey);
				partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.removeAtKey(mapKey);
				var item = plugin.getRowBodyElement(record);
				var store = item.getStore();
				var parentWaybillNo = store.getAt(0).get('parentWaybillNo');
				if(parentWaybillNo == record.get('parentWaybillNo')){
					item.getSelectionModel().deselectAll(true);
				}
/*				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var store = item.getStore();
					var parentWaybillNo = store.getAt(0).get('parentWaybillNo');
					if(parentWaybillNo == record.get('parentWaybillNo')){
						item.getSelectionModel().deselectAll(true);
					}
				}*/
			}
		},
	viewConfig: {
		enableTextSelection: true
    }
});

//导入表单 
Ext.define('Foss.partialline.inputweightvolumn.form.importWeightVolumnStandard',{
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
								                    url: partialline.realPath('importRecords.action'),
								                    success: function(form, action) {
								                    	myMask.hide();
								                    	var json =action.result;
							                    		 Ext.MessageBox.alert('导入成功');
							                     		 var searchResultStore = partialline.inputWeightVolumn.searchResultGrid.getStore();
							                    		 searchResultStore.removeAll();
							                    		 searchResultStore.loadData(json.vo.list)
								                    },
													exception : function(form, action) {
														myMask.hide();
								        				json=action.result;
								        				var msg=json.message;
								        				while(msg.indexOf(';')>-1){
								        					msg=msg.replace(';', "\r\n");
								        				}
								        				Ext.create('Ext.window.Window', {
								        				    title:partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'),
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
								        				    title: partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.form.searchWaybill.notify'),
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
Ext.define('Foss.partialline.inputweightvolumn.window.importweightvolumn', {
	extend:'Ext.window.Window',
	id:'Foss_partialline_inputweightvolumn_window_importweightvolumn_ID',
	title: '重量体积导入',//'重量体积导入',
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
	items:[Ext.create('Foss.partialline.inputweightvolumn.form.importWeightVolumnStandard')]
});


Ext.define('Foss.partialline.inputweightvolumn.InputWeightVolumnInfoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	autoScroll : true,
	unbind : function(record){},
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var parentWaybillNo = record.get('parentWaybillNo');
		var isLoad = record.get('isLoad');
		var grid = this;
		if(isLoad == '1'){
		        var waybillNo;
				var mapKey = parentWaybillNo;
				var inputWeightVolumnInfoMap = partialline.inputWeightVolumn.inputWeightVolumnInfoMap.get(mapKey);
				if(inputWeightVolumnInfoMap != null){
					for(var i=0;i<grid.store.getCount();i++){
						waybillNo = grid.store.getAt(i).get('waybillNo');
						if(inputWeightVolumnInfoMap.get(waybillNo) != null){
							grid.getSelectionModel().select(grid.store.getAt(i),true,true);
						}
					}
				}
			return;
		}
	
		Ext.Ajax.request({
			url: partialline.realPath('queryInputWeightVolumnInfo.action'),
			params:{'vo.parentWaybillNo': parentWaybillNo
					},
			success:function(response){
				var result = Ext.decode(response.responseText);
				/*if(!result.stockVO.stockList || result.stockVO.stockList.length == 0) {
					Ext.MessageBox.alert("提示", "没有数据行!");
					return;
				}*/
				grid.store.loadData(result.vo.list);
				var waybillNo;
				var mapKey = parentWaybillNo;
				var inputWeightVolumnInfoMap = partialline.inputWeightVolumn.inputWeightVolumnInfoMap.get(mapKey);
				if(inputWeightVolumnInfoMap != null){
					for(var i=0;i<grid.store.getCount();i++){
						waybillNo = grid.store.getAt(i).get('waybillNo');
						if(inputWeightVolumnInfoMap.get(waybillNo) != null){
							grid.getSelectionModel().select(grid.store.getAt(i),true,true);
						}
					}
				}
				record.set('isLoad','1');
			}
		})
	},
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.waybillNo')/*'运单号'*/
	},{
		dataIndex : 'isPicPackage',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.isPicPackage'),/*'子母件'*/
		renderer : function(value,metaData,record){
				var isPicPackage = record.get('isPicPackage');
				if(isPicPackage=='Y'){
					value = '母件';
				}else{
					value = '子件';
				}
				return value;
		}
	},{
		header : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.weight'),/*'重量'*/
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
		header : partialline.inputWeightVolumn.i18n('Foss.partialline.inputweightvolumn.grid.label.volumn'),/*'体积'*/
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
		me.plugins = [
	  			me.getEditor()
	  		];
	  	me.store = Ext.create('Foss.partialline.inputweightvolumn.InputWeightVolumnInfoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
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
		select : function(rowModel, record, index, eOpts) {
			var waybillNo = record.get('waybillNo');
			var parentWaybillNo = record.get('parentWaybillNo');
			var mapKey = parentWaybillNo;
			var inputWeightVolumnInfoMap = partialline.inputWeightVolumn.inputWeightVolumnInfoMap.get(mapKey);
			if(inputWeightVolumnInfoMap== null){
				inputWeightVolumnInfoMap = new Ext.util.HashMap();
			}
			inputWeightVolumnInfoMap.add(waybillNo,record);
			partialline.inputWeightVolumn.inputWeightVolumnInfoMap.add(mapKey,inputWeightVolumnInfoMap);
			//勾选上层运单库存
			var selectRecord = partialline.inputWeightVolumn.inputWeightWaybillInfoMap.get(mapKey);
			partialline.inputWeightVolumn.searchResultGrid.getSelectionModel().select(selectRecord,true,true);
			var inputWeightVolumnBaseInfoMap = partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.get(mapKey);
			if(inputWeightVolumnBaseInfoMap == null){
				partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.add(mapKey,selectRecord);
			}
		},
		deselect : function(rowModel, record, index, eOpts) {
			var grid = this;
			var parentWaybillNo = record.get('parentWaybillNo');
			var waybillNo = record.get('waybillNo');
			var mapKey = parentWaybillNo;
			var selectedList = grid.getSelectionModel().selected;
			if(selectedList.length == 0){
				//取消勾选上层运单库存
				var superGrid = this.superGrid;
				var superRecord = partialline.inputWeightVolumn.inputWeightWaybillInfoMap.get(mapKey);
				superGrid.getSelectionModel().deselect(superRecord,true);
				partialline.inputWeightVolumn.inputWeightVolumnInfoMap.removeAtKey(mapKey);
				partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap.removeAtKey(mapKey);
			}else{
				var inputWeightVolumnInfoMap = partialline.inputWeightVolumn.inputWeightVolumnInfoMap.get(mapKey);
				inputWeightVolumnInfoMap.removeAtKey(waybillNo);
				partialline.inputWeightVolumn.inputWeightVolumnInfoMap.add(mapKey,inputWeightVolumnInfoMap);
			}
		}
	}
});

Ext.onReady(function() {
		var searchForm=Ext.create('Foss.partialline.inputweightvolumn.form.searchForm');
		var searchResult=Ext.create('Foss.partialline.inputweightvolumn.InputWeightVolumnBaseInfoGrid');
		partialline.inputWeightVolumn.queryForm=searchForm;
		partialline.inputWeightVolumn.searchResultGrid=searchResult;
		partialline.inputWeightVolumn.inputWeightWaybillInfoMap = new Ext.util.HashMap();
		partialline.inputWeightVolumn.inputWeightVolumnBaseInfoMap = new Ext.util.HashMap(); //上层map 用于界面存放已勾选的父运单
		partialline.inputWeightVolumn.inputWeightVolumnInfoMap = new Ext.util.HashMap();//用于界面存放已勾选的重量体积详情
		Ext.create('Ext.panel.Panel', {
		id : 'T_partialline-inputweightvolumnindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [ searchForm,searchResult],
		renderTo : 'T_partialline-inputweightvolumnindex-body'
	});
});