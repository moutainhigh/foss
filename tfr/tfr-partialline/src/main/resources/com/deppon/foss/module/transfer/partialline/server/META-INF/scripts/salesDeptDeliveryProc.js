//Model
Ext.define('Foss.partialline.salesDeptDeliveryPrc.salesDeptDeliveryPrcModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},// ID
	{
		name : 'wayBillNo',
		type : 'string'
	},// 运单号
	{
		name : 'waystaus',
		type : 'string'
	}, // 运单状态
	{
		name : 'stock',
		type : 'string'
	},// 库存数量
	{
		name : 'billing',
		type : 'string'
	},// 开单数量
	{
		name : 'suppliers',
		type : 'string'
	},// 供应商
	{
		name : 'supplierOrder',
		type : 'string'
	},// 供应商订单号
	{
		name : 'deliveryMethod',
		type : 'string'
	},// 提货方式
	{
		name : 'goodsName',
		type : 'string'
	},// 货物名称
	{
		name : 'weight',
		type : 'string'
	}, // 重量
	{
		name : 'volume',
		type : 'string'
	},// 体积
	{
		name : 'confirmationTime',
		type : 'string',
		convert:function(v){
			if(null!=v){
				return Ext.Date.format(new Date(v), 'Y-m-d');
			}
			return null;
		}
	},// 确认时间
	{
		name : 'isbroken',
		type : 'string'
	},// 是否破损
	{
		name : 'brokennote',
		type : 'string'
	},// 破损备注
	{
		name : 'goodsDetails',
		type : 'string'
	},// 货物明细
	{
		name : 'picPersoncard',
		type : 'string'
	},// 提货人身份证
	{
		name : 'createtime',
		type : 'date'
	},// 创建时间
	{
		name : 'createuser',
		type : 'string'
	},// 创建人
	{
		name : 'modifytime',
		type : 'date'
	},// 修改时间
	{
		name : 'modifyuser',
		type : 'string'
	} ]
// 修改人
});
// ------------------------------------STORE----------------------------------
//  
Ext.define('Foss.partialline.salesDeptDeliveryPrc.salesDeptDeliveryPrcStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.partialline.salesDeptDeliveryPrc.salesDeptDeliveryPrcModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : partialline.realPath('salesDeptDeliveryQuery.action'),
		reader : {
			type : 'json',
			root : 'objectVo.salesdeptDeliveryEntitys',
			totalProperty : 'totalCount'
		}
	}
});

// 弹出界面上 数据渲染
Foss.partialline.salesDeptDeliveryPrc.initParentWin = function(win, viewState,
		formRecord, gridData) {
	if ('ADD' === viewState) {
		// 新增时 必填项不用
		win.editForm.getForm().reset();
	} else {

	}

	return win;
};

//运单确认
Foss.partialline.salesDeptDeliveryPrc.confirm=function(the){
	
	var grid=the.getSalesDeptDeliveryPrcChild();
	var store=grid.getStore();
	var data=[];
	var flag=true;
	//运单号交接字段验证
	store.each(function(model){
		var wayBillNo=model.get('wayBillNo');
		data.push(model.data);
		if(Ext.isEmpty(model.get('isbroken'))){
			 Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
					 '运单号'+wayBillNo+'是否破损不能为空！');
			 flag=false;
			 return;
		}
		if(model.get('isbroken')=='Y'){
			if(Ext.isEmpty(model.get('brokennote'))){
				Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
						 '运单号'+wayBillNo+'破损备注不能为空！'); 
				 flag=false;
				return;
			}
			if(model.get('brokennote').length>50){
				Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
						 '运单号'+wayBillNo+'破损备注长度不大于50！'); 
				 flag=false;
				return;
			}
		}
	 
		if(Ext.isEmpty(model.get('picPersoncard'))){
			 Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
					 '运单号'+wayBillNo+'提过人身份证号不能为空！'); 
			 flag=false;
			 return;
		}
	/*	//身份证正则表达式(15位)
		isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		//身份证正则表达式(18位)
		isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		身份证正则合并：(^\d{15}$)|(^\d{17}([0-9]|X)$)*/
		/*var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		var isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;*/
		var isIDCard=/(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		if(!isIDCard.test(model.get('picPersoncard'))){
			Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
					 '运单号'+wayBillNo+'的提货人身份证号格式不对！'); 
			 flag=false;
			 return;
		}
	})
	 
	if(!flag){
		return;
	}
	/**
	 * 运单货物交接请求
	 */
	var param=new Object();
	var objectVo=new Object();
	objectVo.salesdeptDeliveryEntitys=data;
	param.objectVo=objectVo;
	Ext.Ajax.request({
		url:partialline.realPath('goodsHandOver.action'),
		jsonData:param,
		method:'post',
		success : function(response) { 
			 var json = Ext.decode(response.responseText); 
			 var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();//得到grid
			 var win=grid.addSalesDeptDeliveryPrcWin();
			     win.hide();
				 grid.store.loadPage(1);
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoHandOverOK')); 
			
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoHandOverFail'));
		},
		failure:function(response){
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),json.message);
			}
		});
}

//通过查询条件导出数据
Foss.partialline.salesDeptDeliveryPrc.exportSalesDeptDeliveryPrc = function(queryForm,codes){
	var entity = queryForm.getForm().getValues();//得到查询的FORM表单
	if(codes.length<=0){
		Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
				partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoChoiceImportInfo'));
		return;
	}
		Ext.MessageBox.buttonText.yes =partialline.salesDeptDeliveryProc.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no =partialline.salesDeptDeliveryProc.i18n('foss.baseinfo.leasedVan.cancel');
		if(!Ext.fly('salesDeptDeliveryProc')){
			    var frm = document.createElement('form');
			    frm.id = 'salesDeptDeliveryProc';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm(partialline.salesDeptDeliveryProc.i18n('foss.baseinfo.tipInfo'),partialline.salesDeptDeliveryProc.i18n('foss.baseinfo.exportMsg'), function(btn,text){
			if(btn == 'yes'){
				var params ={
						'objectVo.salesdeptDeliveryEntity.wayNos':codes,
						// //运单号
						'objectVo.waynoStr' : entity.waynos,
						// //运单状态
						'objectVo.status' : entity.waystaus,
				};
				Ext.Ajax.request({
					url:partialline.realPath('exportWayNoDetail.action'),
					form: Ext.fly('salesDeptDeliveryProc'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.baseinfo.exportFailed'),result.message);
					}
				});
			}
		});
};

//取消交货确认
Foss.partialline.salesDeptDeliveryPrc.canlSalesDeptDeliveryPrc=function(me){
	 var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();
	 var selections = grid.getSelectionModel().getSelection();
		
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
				partialline.salesDeptDeliveryProc.i18n('foss.partialline.atLeastOneSelect'));
		return false;
	}
	var data=[];
	for(var i in selections){
		if(Ext.isEmpty(selections[i].data.waystaus)||selections[i].data.waystaus=='NON_DE_CONFIRM'){
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
					partialline.salesDeptDeliveryProc.i18n('foss.partialline.onlyHandOverOKWayBillNoOperate'));
			return;
		}
		data.push(selections[i].data);
	}
	Ext.Msg.wait(partialline.salesDeptDeliveryProc.i18n('foss.partialline.canleHanding'),partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou')); 
//	me.setDisabled(true);
	//先禁用	掉EXT的重绘
	 Ext.suspendLayouts();
	 //先禁用store的事件，等处理完毕之后在恢复，在将store重新指定给grid
	 grid.getStore().suspendEvents();
	 grid.getStore().remove(selections);
	 //恢复store事件
	 grid.getStore().resumeEvents();
	 grid.reconfigure(grid.getStore());
	 //恢复重绘
	 Ext.resumeLayouts(true);
	 
	var param=new Object();
	var objectVo=new Object();
	objectVo.salesdeptDeliveryEntitys=data;
	param.objectVo=objectVo;
	//--取消运单交货
	Ext.Ajax.request({
		url:partialline.realPath('canlGoodsHandOver.action'),
		jsonData:param,
		method:'post',
		success : function(response) { 
			me.setDisabled(false);
			var json = Ext.decode(response.responseText); 
			 var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();//得到grid
				grid.store.loadPage(1);
				Ext.Msg.hide();
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),partialline.salesDeptDeliveryProc.i18n('foss.partialline.canleHandSucces')); 
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),json.message);
		},
		failure:function(response){
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),partialline.salesDeptDeliveryProc.i18n('foss.partialline.canleHandFail'));
			}
		});
	
	
}

/**
 * queryForm
 */
Ext.define('Foss.partialline.salesDeptDeliveryPrc.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件', // 查询条件
	frame : true,
	collapsible : true,
	defaults : {
		margin : '8 200 5 10',
	// columnWidth : 0.5
	},
	defaultType : 'textfield',
	layout : {
		type : 'column',
		columns : 2
	},
	record : null, // 绑定的model
	constructor : function(config) { // 构造器
		var me = this, cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([ cfg ]);
	},
	getItems : function() {
		var me = this;
		return [ {
			fieldLabel :partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNo'), 
			xtype : 'textareafield',
			allowBlank:false,
			regex:/^(\d{8,9}\n{0,1}){0,5}$/,
			regexText:partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoValid'),
			name : 'waynos',
			emptyText :partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoEmptyText'),
			height:83
		},
		FossDataDictionary.getDataDictionaryCombo('SALESDEPT_DELIVERY_PROC', {
			fieldLabel : partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoStatus'),
//			columnWidth : 0.3,
			name : 'waystaus',
			value : ''
		}, [{
		'valueCode' : '',
		'valueName' : partialline.salesDeptDeliveryProc.i18n('foss.partialline.all')
	}])];
	},
	buttons : [ {
		text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.reset'),// 重置
		margin : '0 790 0 0',
		handler : function() {
			var queryForm = this.up('form').getForm();
			queryForm.reset();
		}
	}, {
		text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.query'),// 查询
		cls : 'yellow_button',
		margin : '0 30 0 0',
		handler : function(btn) {
          var form=this.up('form').getForm();	
          
          if(form.isValid()){
	          var waynos=form.findField('waynos').getValue();
	          if(Ext.isEmpty(waynos)){
	      		Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
	      				partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoNotNull'));
	        	return;
	          }
				console.log(form);
			 var waynoCodes=waynos.split('\n');
			 if(waynoCodes.length<0||waynoCodes.length>5){
			   Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
					   partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoMaxNumberFive'));
			   return;
			 }
			 
			 for(var i in waynoCodes){
				 if(Ext.isEmpty(waynoCodes[i])){
					 continue;
				 }
				 var regex=/^\d{8,9}$/;
				 if(waynoCodes[i].length>9||waynoCodes[i].length<8||!regex.test(waynoCodes[i].trim())){
					 Ext.Msg.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
							 partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoEightOrNineAndEntry')); 
					 return;
				 }
			 }
			 
			 var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();//得到grid
			    grid.getStore().removeAll();
				grid.store.loadPage(1);
	          }
		 },
		plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds : 3
		})
	} ]
});

// grid
// 查询结果grid
Ext.define('Foss.partialline.salesDeptDeliveryPrc.QueryResultGrid',{
		extend : 'Ext.grid.Panel',
		title : partialline.salesDeptDeliveryProc.i18n('foss.partialline.queryResults'),
		sortableColumns : false,
		enableColumnHide : false,
		enableColumnMove : false,
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		stripeRows : true, // 交替行效果
		selType : "rowmodel", // 选择类型设置为：行选择
		emptyText : partialline.salesDeptDeliveryProc.i18n('foss.partialline.searchListNull'), // 查询结果为空
		queryForm : null,
		frame : true,
		// 得到BBAR（分页）
		pagingToolbar : null,
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.columns = me.getColumns(config);
			me.store = me.getStore();
			me.listeners = me.getMyListeners(config);
			// 添加多选框
			me.selModel = Ext.create('Ext.selection.CheckboxModel',
					{
						mode : 'MULTI',
						checkOnly : true
					});
			// 添加头部按钮
			me.tbar = me.getTbar(config);
			// 添加分页控件
			me.bbar = me.getPagingToolbar(config);
			// 设置分页控件的store属性
			me.getPagingToolbar().store = me.store;
			me.callParent([ cfg ]);
		},
		getTbar : function(config) {
			var me = this;
			return [ {
				text :partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoOpreate') ,
				handler : function() {
					var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();
					 var selections = grid.getSelectionModel().getSelection();
						for(var i in selections){
							if(selections[i].get('waystaus')=='DE_CONFIRM'){
								Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
										partialline.salesDeptDeliveryProc.i18n('foss.partialline.only.wayBillNoOkHandingOperate'));
								return;
							}
						}
					// 如果未选中数据，提示至少选择一条记录进行操作
					if(selections.length==0){
						Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
								partialline.salesDeptDeliveryProc.i18n('foss.partialline.atLeastOneSelect'));
						return false;
					}
					var win=me.addSalesDeptDeliveryPrcWin();
					var childGird=win.getSalesDeptDeliveryPrcChild();
					childGird.getStore().loadData(selections);
					win.show();
				}
			}, '-', {
				text :partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoOpreateCanle'),
				handler : function() {
				  Foss.partialline.salesDeptDeliveryPrc.canlSalesDeptDeliveryPrc(this);	
				}
			}, '-', {
				text :partialline.salesDeptDeliveryProc.i18n('foss.partialline.importWayBillNoInfo') ,
				handler : function() {
					var grid  = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryGrid();
					var selections = grid.getSelectionModel().getSelection();
					if(null==selections){
						Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
								partialline.salesDeptDeliveryProc.i18n('foss.partialline.pleaseChoiceRecord'));
						return;
					}
					var codes=[];
					for(var i in selections){
					   codes.push(selections[i].get('wayBillNo')); 
						if(Ext.isEmpty(selections[i].get('waystaus'))||
								selections[i].get('waystaus')=='NON_DE_CONFIRM'){
							Ext.MessageBox.alert(partialline.salesDeptDeliveryProc.i18n('foss.partialline.FOSSRemindYou'),
									partialline.salesDeptDeliveryProc.i18n('foss.partialline.existNonOperateWayBillNoInfo'));
							return;
						}
				}
					Foss.partialline.salesDeptDeliveryPrc.exportSalesDeptDeliveryPrc(me.queryForm,codes);
				}
			} ];
		},
		getPagingToolbar : function(config) {
			if (Ext.isEmpty(this.pagingToolbar)) {
				this.pagingToolbar = Ext.create(
						'Deppon.StandardPaging', {
							store : this.store,
							pageSize : 20
						});
			}
			return this.pagingToolbar;
		},
		salesDeptDeliveryPrc : null,
		addSalesDeptDeliveryPrcWin : function(param) {
			if (null==this.salesDeptDeliveryPrc) {
				this.salesDeptDeliveryPrc = Ext.create('Foss.partialline.salesDeptDeliveryPrc.SalesDeptDeliveryPrcWin');
			}
			return this.salesDeptDeliveryPrc;
		},
		getMyListeners : function() {
			var me = this;
			return {
				// 增加滚动条事件，防止出现滚动条后却不能用
				scrollershow : function(scroller) {

				},
				 
				itemdblclick : function(view, record) {

				}
			};
		},
		getStore : function() {
			return Ext.create('Foss.partialline.salesDeptDeliveryPrc.salesDeptDeliveryPrcStore',{
					autoLoad : false,
					pageSize : 20,
					listeners : {
						beforeload : function(store,operation, eOpts) {
							var queryForm = Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex_content').getQueryForm()
									.getForm();
							var entity=queryForm.getValues();
							if (queryForm != null){
								Ext.apply(operation,{
										params : {
											// //运单号
											'objectVo.waynoStr' : entity.waynos,
											// //运单状态
											'objectVo.status' : entity.waystaus,
										//								
										}
									});
							}
						}
					}
				});
		},
		getColumns : function(config) {
			var me = this;
			return [ {
				text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNo'),
				dataIndex : 'wayBillNo',
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.waystaus'),
				dataIndex : 'waystaus',
				renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'SALESDEPT_DELIVERY_PROC');
				}
			},
		/*	{
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.stock'),
				dataIndex : 'stock',
			},*/ 
			{
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.billing'),
				dataIndex : 'billing'
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.suppliers'),
				dataIndex : 'suppliers'
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.supplierOrder'),
				dataIndex : 'supplierOrder',
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.deliveryMethod'),
				dataIndex : 'deliveryMethod',
				renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.goodsName'),
				dataIndex : 'goodsName',
				renderer:function formatQtip(value,metadata){   
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qtip="' + value + '"';
					}
				    return value;    
				}
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.weight'),
				dataIndex : 'weight'
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.volume'),
				dataIndex : 'volume'
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.confirmationTime'),
				dataIndex : 'confirmationTime'
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.isbroken'),
				dataIndex : 'isbroken',
				renderer:function(v){
					if(v=='Y'){
						return partialline.salesDeptDeliveryProc.i18n('foss.partialline.yes');
					}else if(v=='N'){
						return partialline.salesDeptDeliveryProc.i18n('foss.partialline.no');
					}
					return null;
				}
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.brokennote'),
				dataIndex : 'brokennote',
				renderer:function formatQtip(value,metadata){   
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qtip="' + value + '"';
					}
				    return value;    
				} 
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.goodsDetails'),
				dataIndex : 'goodsDetails',
				renderer:function formatQtip(value,metadata){   
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qtip="' + value + '"';
					}
				    return value;    
				} 
			}, {
				text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.picPersoncard'),
				dataIndex : 'picPersoncard',
				renderer:function formatQtip(value,metadata){   
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qtip="' + value + '"';
					}
				    return value;    
				}  
			} ];
		}
	});

Ext.onReady(function() {
	// salesDeptDeliveryProcIndex
	var queryForm = Ext.create('Foss.partialline.salesDeptDeliveryPrc.QueryConditionForm');
	var salesDepteDeliveryProcGrid = Ext.create('Foss.partialline.salesDeptDeliveryPrc.QueryResultGrid',{
		'queryForm':queryForm
	});
	Ext.getCmp('T_partialline-salesDeptDeliveryProcIndex').add(
			Ext.create('Ext.panel.Panel', {
				id : 'T_partialline-salesDeptDeliveryProcIndex_content',
				cls : 'panelContentNToolbar',
				bodyCls : 'panelContentNToolbar-body',
				// 获得查询FORM
				getQueryForm : function() {
					return queryForm;
				},
				// 获得查询报表结果GRID
				getQueryGrid : function() {
					return salesDepteDeliveryProcGrid;
				},
				items : [ queryForm, salesDepteDeliveryProcGrid ]
			}));

})

// win
Ext.define('Foss.partialline.salesDeptDeliveryPrc.SalesDeptDeliveryPrcWin', {
	extend : 'Ext.window.Window',
	title : partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoHandOver'),
	closable : false,
	modal : true,
//	resizable : false,
	closeAction : 'destroy',
	width : 850,
//	height : 500,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	salesDeptDeliveryPrcChild:null,
	getSalesDeptDeliveryPrcChild:function(){
		if(null==this.salesDeptDeliveryPrcChild){
			this.salesDeptDeliveryPrcChild=Ext.create('Foss.partialline.salesDeptDeliveryPrcChild.QueryResultChildGrid');
		}
		return this.salesDeptDeliveryPrcChild;
	},
	formRecord : null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getSalesDeptDeliveryPrcChild()]
		me.fbar = me.getFbar(config);
		me.listeners = {
				'beforeshow':function(me){
				 
					 
				},
			};
		me.callParent([ cfg ]);
	},
	initComponent : function() {
		var me = this;
		this.callParent();
	},
	// 操作界面上的按钮
	getFbar : function(config) {
		var me = this;
		return [ {
			text :partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoCanle') ,
			handler : function() {
				var grid=me.getSalesDeptDeliveryPrcChild();
				var store=grid.getStore();
				store.removeAll();
				me.hide();
			}
		} , {
			text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNoOk'),
			handler : function() {
				Foss.partialline.salesDeptDeliveryPrc.confirm(me);
			}
		} ];
	}
});

//交货确认grid

//grid
//查询结果grid
Ext.define('Foss.partialline.salesDeptDeliveryPrcChild.QueryResultChildGrid',{
		extend : 'Ext.grid.Panel',
		title : partialline.salesDeptDeliveryProc.i18n('foss.partialline.queryResults'),
		sortableColumns : false,
		enableColumnHide : false,
		enableColumnMove : false,
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		stripeRows : true, // 交替行效果
		selType : "rowmodel", // 选择类型设置为：行选择
		emptyText : partialline.salesDeptDeliveryProc.i18n('foss.partialline.searchListNull'), // 查询结果为空
		queryForm : null,
		frame : true,
		// 得到BBAR（分页）
		pagingToolbar : null,
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.columns = me.getColumns(config);
			me.store=Ext.create('Foss.partialline.salesDeptDeliveryPrcChild.salesDeptDeliveryPrcStoreChild');
			me.plugins = [ Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 1
			}) ];
			me.callParent([ cfg ]);
		},
		getColumns : function(config) {
			var me = this;
			return [ {
				text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.wayBillNo'),
				dataIndex : 'wayBillNo',
				renderer:function (value,metadata){   
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qtip="' + value + '"';
					}
				    return value;    
				} , 
				flex:1,
				}, {
					text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.isbroken'),
					dataIndex : 'isbroken',
					renderer:function (value,metadata){   
						if('Y'==value){
							return partialline.salesDeptDeliveryProc.i18n('foss.partialline.yes');
						}else if('N'==value){
							return partialline.salesDeptDeliveryProc.i18n('foss.partialline.no');    
						}
						return null;
					} , 
					editor: new Ext.form.ComboBox({  
		                editable: false,  
		                displayField: 'v',  
 		                valueField: 'k',
		                mode: "local",
		                triggerAction: "all",  
		                store: new Ext.data.Store({  
		                    fields: ['v','k'],  
		                    data: [{"k":"Y", "v":"是"},
		                           {"k":"N", "v":"否"}]  
		                })  
		            }) ,
				}, {
					text : partialline.salesDeptDeliveryProc.i18n('foss.partialline.brokennote'),
					dataIndex : 'brokennote',
					flex:1,
					editor: 'textfield',
					renderer:function(value,metadata){   
						if(!Ext.isEmpty(value)){
							metadata.tdAttr = 'data-qtip="' + value + '"';
						}
					    return value;    
					}
				}, {
					text :  partialline.salesDeptDeliveryProc.i18n('foss.partialline.picPersoncard'),
					dataIndex : 'picPersoncard',
					flex:1,
					editor: 'textfield',
					renderer:function(value,metadata){   
						if(!Ext.isEmpty(value)){
							metadata.tdAttr = 'data-qtip="' + value + '"';
						}
					    return value;    
					} , 
				}];
		}
	});

//Model
Ext.define('Foss.partialline.salesDeptDeliveryPrcChild.salesDeptDeliveryPrcChildModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},// ID
	{
		name : 'wayBillNo',
		type : 'string'
	},// 运单号
	{
		name : 'isbroken',
		type : 'string'
	}, // 货物是否有破损
	{
		name : 'brokennote',
		type : 'string'
	},// 破损备注
	{
		name : 'picPersoncard',
		type : 'string'
	}//提货人身份证号
	 ]

});
// ------------------------------------STORE----------------------------------
//  
Ext.define('Foss.partialline.salesDeptDeliveryPrcChild.salesDeptDeliveryPrcStoreChild', {
	extend : 'Ext.data.Store',
//	model : 'Foss.partialline.salesDeptDeliveryPrcChild.salesDeptDeliveryPrcChildModel',
	model : 'Foss.partialline.salesDeptDeliveryPrc.salesDeptDeliveryPrcModel',
	pageSize : 20,
	/*data:{'items':[
	               { 'wayno': '1111'},
	               { 'wayno': '4444'},
	               { 'wayno': '2222'},
	               { 'wayno': '3333'}
	           ]},*/
   proxy: {
           type: 'memory',
           reader: {
               type: 'json',
               root: 'items'
           }
	           }
});

