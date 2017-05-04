// 定义交接单明细列表之Model
Ext.define('Foss.unload.orderDifferReportHandle.orderReportDetailModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
				       name:'reportNo',
				       type:'String'//报告编号
			        },{
				        name : 'handoverNo',// 交接单号
				        type : 'string'
			        }, {
				        name : 'origOrgCode',// 出发部门
				        type : 'string'
			        }, {
				        name : 'origOrgName',// 出发部门
				        type : 'string'
			        }, {
				        name : 'destOrgCode',// 到达部门
				        type : 'string'
			        }, {
				        name : 'destOrgName',// 到达部门
				        type : 'string'
			        }, {
						name : 'waybillNo',// 运单号
						type : 'string'
					}, {
						name : 'transportType',// 运输性质
						type : 'string'
					}, {
						name : 'packing',// 包装
						type : 'string'
					}, {
						name : 'goodsName',// 货物名称
						type : 'string'
					}, {
						name : 'goodsQty',// 开单件数
						type : 'number'
					}, {
						name : 'handoverGoodsQty',// 交单件数
						type : 'number'
					}, {
						name : 'handoverWeight',// 交单重量
						type : 'number'
					}, {
						name : 'handoverVolume',// 交单体积
						type : 'number'
					},{
						name:'createTime',//报告生成时间
					    convert:dateConvert,
						type:'Date'
					},{
					    name:'isHandle',//是否处理完
					    type:'String'
					}]
		});
// 定义交接单明细列表之store
Ext.define('Foss.unload.orderDifferReportHandle.orderReportDetailStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.orderDifferReportHandle.orderReportDetailModel',
			autoLoad : true,
			// 定义一个代理对象
			proxy : {
				type : 'ajax',
				actionMethods:'POST',
				// 请求的url
				url : unload.realPath('queryOrderReportDetailByNo.action'),
				// 定义一个读取器
				reader : {
					// 以JSON的方式读取
					type : 'json',
					// 定义读取JSON数据的根对象
					root : 'orderDifferReportVo.orderReportDetailList',
					successProperty: 'success'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([ cfg ]);
			},
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					Ext.apply(operation, {
						params : {
							'orderDifferReportVo.reportNo' : unload.orderDifferReportHandle.reportNo
						}
					});	
				}
			}
});
//定义运单明细Model
Ext.define('Foss.unload.orderDifferReportHandle.serialNoDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
	    name:'detailId',
	    type:'String'
	},{
	   name:'id',
	   type:'String'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	},{
		name : 'handoverNo',
		type : 'string'
	},{
		name : 'orderReportType',//点单差异类型（NORMAL正常,LOSE少货,MORE多货）
		type : 'string'
	},{
		name : 'reason',//REASON
		type : 'string'
	},{
		name : 'handleTime',//差异处理时间
	    convert:dateConvert,
		type : 'Date'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'createTime',//		创建时间
		convert:dateConvert,
		type : 'Date'
	},{
		name : 'modifyTime',//修改时间
		convert:dateConvert,
		type : 'Date'
	},{
		name : 'modifyUserCode',//修改人代码
		type : 'string'
	},{
		name : 'createUserCode',//创建人
		type : 'string'
	},{
	    name:'isHandle',//是否处理过
	    type:'String'
	}]
});
//定义运单明细store
Ext.define('Foss.unload.orderDifferReportHandle.serialNoDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.orderDifferReportHandle.serialNoDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : unload.realPath('querySerialNoListByDetailId.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'orderDifferReportVo.orderReportSerialNoList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//差异原因store
Ext.define('Foss.unload.orderDifferReportHandle.reasonType',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {'items':[//有货无单Y_GOODS_N_BILL、有单无货N_GOODS_Y_BILL、其他ORTHER
			{'code':'Y_GOODS_N_BILL','name': unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.Y_GOODS_N_BILL')},
			{'code':'N_GOODS_Y_BILL','name': unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.N_GOODS_Y_BILL')},
			{'code':'ORTHER','name': unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.ORTHER')}
	]},
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//差异报告明细处理From	
Ext.define('Foss.unload.orderDifferReportHandle.orderDifferReportHandleForm',{
   extend:'Ext.form.Panel',
   cls:'autoHeight',
   defaults:{
   	 xtype: 'textfield',
     margin:'5 5 5 5',
     anchor:'98%',
     lableWidth:90
   },
   standardSubmit:true,
   items:[{
      name:'detailId',
      hidden:true
   },{
      name:'id',
      hidden:true
   },{
      name:'handoverNo',
      hidden:true
   },{
   	  xtype:'textfield',
	  fieldLabel:'运单号',
	  name:'waybillNo',
	  readOnly:true,
	  allowBlank: false
	},{
	  xtype:'textfield',
	  fieldLabel:'流水号',
	  readOnly:true,
	  name:'serialNo',
	  allowBlank: false
	},{
		xtype: 'combobox',//差异原因
		name:'reason',
		fieldLabel: unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.reasonType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.unload.orderDifferReportHandle.reasonType'),
		allowBlank: false
	},{
	    xtype : 'textarea',
		fieldLabel: '备注',
		height: 150,
	    width: 380,
		autoScroll:true,
		name: 'note',
		allowBlank: false
	}]
});
//差异报告处理WindoW
Ext.define('Foss.unload.orderDifferReportHandle.orderDifferReportHandleWindow',{
	extend:'Ext.window.Window',
	title:unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderDifferReportHandleWindow'),//点单差异报告明细处理
	modal:true,
	width:510,
	height:370,
	closeAction:'hide',
	bodyCls:'autoHeight',
	layout:'auto',
	handleType:null,//all 运单处理， 流水处理one
	orderDifferReportHandleForm : null,
	getOrderDifferReportHandleForm : function() {
		if (this.orderDifferReportHandleForm == null) {
			this.orderDifferReportHandleForm = Ext.create('Foss.unload.orderDifferReportHandle.orderDifferReportHandleForm');
		}
		return this.orderDifferReportHandleForm;
	},
    resetData : function() {
		var me = this, form = this.orderDifferReportHandleForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this;
		var form = me.orderDifferReportHandleForm.getForm();
	},
	listeners : {
		beforehide : function(me) {
			me.resetData();
		},
		beforeshow : function(me) {
			me.initData();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getOrderDifferReportHandleForm()];
		me.fbar = [{
			cls : 'yellow_button',
			text : '确认',
			handler : function() {
				var orderDifferReportHandleWindow=this.up('window');
				var orderDifferReportHandleForm=orderDifferReportHandleWindow.orderDifferReportHandleForm.getForm();
				if(orderDifferReportHandleForm.isValid()){
					var values=orderDifferReportHandleForm.getValues();
					var url='handleOrderDifferReportBySerialNo.action';
					var param = {'orderDifferReportVo' : {'orderReportSerialNoDto':{
								'handoverNo' : values.handoverNo,
								'waybillNo':values.waybillNo,
								'reason':values.reason,
								'note':values.note,
								'detailId':values.detailId,
								'id':values.id
					}}};
					 param.orderDifferReportVo.reportNo=unload.orderDifferReportHandle.reportNo;
					if(orderDifferReportHandleWindow.handleType=='all'){
					  url='handleOrderDifferReportByWaybillNo.action';
					}
					var windowMask = new Ext.LoadMask(orderDifferReportHandleWindow, {
						msg : "保存中，请稍后..."
					});
					windowMask.show();
					Ext.Ajax.request({
						url : unload.realPath(url),
						jsonData : param,
						success : function(response) {
							windowMask.hide();
							me.close();
							Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
							unload.orderReportDetailGrid.getStore().load();
							//是否可以打印   默认为true可以   只要有一条为处理完 就不能打印
							var isPrint=true;
							unload.orderReportDetailGrid.getStore().each(function(record) {
							 if( record.data.isHandle=='N'){
							  isPrint=false;
							 } 
							});
							if(isPrint){
							  var printButton=unload.orderReportDetailGrid.down('button');
		                          printButton.setVisible(true);
							}
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							windowMask.hide();
							Ext.MessageBox.alert('提示', '保存失败，' + result.message);
						}
				   });
				}
			}
		},  {
			cls : 'yellow_button',
			text : '关闭',
			handler : function() {
				me.close();
			}
		}];
		me.callParent([cfg]);
	}
	
});
// 定义下方列表之grid(点单差异报告明细)
Ext.define('Foss.unload.orderDifferReportHandle.orderReportDetailGrid', {
	extend : 'Ext.grid.Panel',
	title:unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.title'),//差异明细
	emptyText : '查询结果为空',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	height:750,
	frame: false,
	store : Ext.create('Foss.unload.orderDifferReportHandle.orderReportDetailStore'),
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners:{
		beforedestroy:function(g){
			var arr= Ext.ComponentQuery.query("[xtype=orderreportdetailserialnogrid]");
			if(arr&&arr.length>0){
				for(var i in arr){
					arr[i].destroy();
				}
			}
		}
	},
	// 定义行展开
	plugins: [{
        ptype: 'rowexpander',
        header: true,
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid',
		pluginId : 'Foss_orderDifferReportHandle_mainPage_orderReportDetailSerialNoGrid_ID'
	}],
	 tbar : [{
    	xtype : 'button',
    	hidden:true,
    	text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.printButtonText')/*'打印'*/,
		handler : function(){
		do_printpreview('orderReport',{
									"reportNo":unload.orderDifferReportHandle.reportNo
									}, ContextPath.TFR_EXECUTION)
		}
	 }],
	columns : [{
		xtype : 'actioncolumn',
		flex: 0.3,
		align : 'center',
		header : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReport.button.operator'),// 操作
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReport.handle'),// 处理
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var handoverNo=record.get('handoverNo');
				var waybillNo=record.get('waybillNo');
				var id=record.get('id');
			    var params = {'orderDifferReportVo' :{ 'id':id}};
				   Ext.Ajax.request({
						url : unload.realPath('querySerialNoListByDetailId.action'),
						jsonData : params,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var orderReportSerialNoList=result.orderDifferReportVo.orderReportSerialNoList;
							if(orderReportSerialNoList.length==0){
								Ext.MessageBox.alert('提示', "对应流水为空，不能处理");
							}else{
							
							var serialNos=orderReportSerialNoList[0].serialNo;
						    for(var i=1;i<orderReportSerialNoList.length;i++){
							 serialNos=serialNos+","+ orderReportSerialNoList[i].serialNo;
							}
							// 处理点差异报告
						    var orderDifferReportHandleWindow=
						    Ext.create('Foss.unload.orderDifferReportHandle.orderDifferReportHandleWindow',{
									 orderReportSerialNoList:orderReportSerialNoList,
									 handleType:'all'
							});
							orderDifferReportHandleWindow.show();
							var orderDifferReportHandleForm=orderDifferReportHandleWindow.orderDifferReportHandleForm.getForm();
							orderDifferReportHandleForm.findField('serialNo').setValue(serialNos);
							orderDifferReportHandleForm.findField('waybillNo').setValue(waybillNo);
							orderDifferReportHandleForm.findField('handoverNo').setValue(handoverNo);
							orderDifferReportHandleForm.findField('detailId').setValue(id);
						 }
						},
					exception : function(response) {
						    var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert('提示', result.message);
					}
				 });
			}
		}],
		renderer : function(value, metadata, record) {
			if (record.data.isHandle == 'N') {
				// 当点单任务状态为"点单中", 可以 修改
				// 当点单任务状态为"点单完毕", 不可修改，可以查看
				this.items[0].iconCls = 'deppon_icons_edit';
			} else{
				this.items[0].iconCls = '';
			}
		}
	
	
	},{
		dataIndex : 'reportNo',//报告编号
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.reportNo')
	},{
	    dataIndex:'handoverNo',
	    hidden:true
	}, {
		dataIndex : 'waybillNo',//运单编号
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.waybillNo')
	}, {
		dataIndex : 'goodsName',//货物名称
		align : 'center',
		flex : 1,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.goodsName')
	},{
		dataIndex : 'transportType',//运输性质
		align : 'center',
		flex : 1,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.transportType')
	},{
		dataIndex : 'packing',//包装
		align : 'center',
		flex : 0.5,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.packing')
	},  {
		dataIndex : 'handoverWeight',
		align : 'center',
		flex : 0.3,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.handoverWeight')
	}, {
		dataIndex : 'handoverVolume',
		align : 'center',
		flex : 0.3,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.handoverVolume')
	},{// 到达部门
		dataIndex : 'destOrgName',
		align : 'center',
		flex : 1,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.destOrgName')
	},{// 报告生成时间
		dataIndex : 'createTime',
		align : 'center',
		flex : 1.2,
		xtype : 'datecolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailGrid.createTime'),
		format : 'Y-m-d H:i:s'
	}]
});
//定义流水之grid（点单差异报告流水）
Ext.define('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid', {
	xtype:'orderreportdetailserialnogrid',
	extend : 'Ext.grid.Panel',
	//enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
    frame: true,
    autoScroll: true,
    cls:'autoHeight',
    bodyCls: 'autoHeight',
    store:Ext.create('Foss.unload.orderDifferReportHandle.serialNoDetailStore'),
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	columns : [{
		xtype : 'actioncolumn',
		align : 'center',
		header : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReport.button.operator'),// 操作
		width : 30,
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReport.handle'),// 处理
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var handoverNo=record.get('handoverNo');
				var waybillNo=record.get('waybillNo');
				var serialNo=record.get('serialNo');
				var id=record.get('id');
				var detailId=record.get('detailId');
									  // 处理点差异报告
				var orderDifferReportHandleWindow=
					Ext.create('Foss.unload.orderDifferReportHandle.orderDifferReportHandleWindow',{
					 handleType:'one'
					});
				     orderDifferReportHandleWindow.show();
				var orderDifferReportHandleForm=orderDifferReportHandleWindow.orderDifferReportHandleForm.getForm();
					orderDifferReportHandleForm.findField('serialNo').setValue(serialNo);
					orderDifferReportHandleForm.findField('waybillNo').setValue(waybillNo);
					orderDifferReportHandleForm.findField('handoverNo').setValue(handoverNo);
					orderDifferReportHandleForm.findField('id').setValue(id);
					orderDifferReportHandleForm.findField('detailId').setValue(detailId);
			}
		}],
		renderer : function(value, metadata, record) {
			if (record.data.isHandle=='N' ) {
				// 当点单任务状态为"点单中", 可以 修改
				// 当点单任务状态为"点单完毕", 不可修改，可以查看
				this.items[0].iconCls = 'deppon_icons_edit';
			} else{
				this.items[0].iconCls = '';
			}
		}
	
	},{
	    dataIndex:'id',
	    hidden:true
	},{
	    dataIndex:'detailId',
	    hidden:true
	},{
	    dataIndex:'handoverNo',
	    hidden:true
	},{
	    dataIndex:'waybillNo',
	    hidden:true
	},{
		dataIndex : 'serialNo',
		align : 'center',
		width : 50,
		xtype : 'ellipsiscolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.serialNo') //流水号
	},{
		dataIndex : 'orderReportType',
		align : 'center',
		width : 80,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.orderReportType') ,//点单差异类型（NORMAL正常,LOSE少货,MORE多货）
		renderer : function(value, metadata, record) {
		 //点单差异类型（NORMAL正常,LOSE少货,MORE多货）
			if(value=="LOSE"){
			    return "少货";
			}else if(value=="MORE"){
				return "多货";
			}else{
				return "正常";
			}
		}
	},{
		dataIndex : 'createTime',
		align : 'center',
		width : 150,
		xtype : 'datecolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.createTime'),//操作时间
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'reason',
		align : 'center',
		width : 120,
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.reason') ,//差异原因
		renderer : function(value, metadata, record) {
		 //有货无单Y_GOODS_N_BILL、有单无货N_GOODS_Y_BILL、其他ORTHER
			if(Ext.isEmpty(value)){
			   return null;
			}else if(value=="Y_GOODS_N_BILL"){
			    return "有货无单";
			}else if(value=="N_GOODS_Y_BILL"){
				return "有单无货";
			}else{
				return "其他";
			}
		}
	},{
		dataIndex : 'handleTime',
		align : 'center',
		width : 150,
		xtype : 'datecolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.handleTime'), //异常处理时间
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'note',
		align : 'center',
		width : 180,
		xtype : 'ellipsiscolumn',
		text : unload.orderDifferReportHandle.i18n('Foss.unload.orderDifferReportHandle.orderReportDetailSerialNoGrid.note') //备注
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo');
		var handOverBillNo = record.get('handoverNo');
		var id=record.get('id');
		this.store.load({
			params : {
				'orderDifferReportVo.id' : id,
				'orderDifferReportVo.waybillNo' : waybillNo,
				'orderDifferReportVo.handoverNo' : handOverBillNo
			}
		});
  }
});
Ext.onReady(function() {
	// 下方grid
unload.orderReportDetailGrid = Ext.create('Foss.unload.orderDifferReportHandle.orderReportDetailGrid');
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-orderDifferReportHandleindex_content',
		cls : "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			items : [unload.orderReportDetailGrid]
		}],
		renderTo : 'T_unload-orderDifferReportHandleindex-body'
	});
	unload.orderReportDetailGrid.printButton=unload.orderReportDetailGrid.down('button');
	if(unload.orderDifferReportHandle.type=="show"){
		unload.orderReportDetailGrid.printButton.setVisible(true);
	}else{
	    unload.orderReportDetailGrid.printButton.setVisible(false);
	}
});