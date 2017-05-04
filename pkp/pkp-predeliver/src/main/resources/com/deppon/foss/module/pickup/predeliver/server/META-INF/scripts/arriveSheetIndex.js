predeliver.arriveSheet.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);
	return t2;
};

predeliver.arriveSheet.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);
	return t2;
};
//定义状态model
Ext.define('Foss.predeliver.arriveSheet.ArriveSheet', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},//额外的用于生成的EXT使用的列
					{
						name : 'id',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'arrivesheetNo',
						type : 'string'
					}, {
						name : 'printtimes',
						type : 'int'
					}, {
						name : 'status',
						type : 'string'
					}, {
						name : 'arriveSheetGoodsQty',
						type : 'int'
					}, {
						name : 'goodsName',
						type : 'string'
					}, {
						name : 'goodsQtyTotal',
						type : 'int'
					}, {
						name : 'arrangeGoodsGty',
						type : 'int'
					}, {
						name : 'arriveNotoutGoodsQty',
						type : 'int'
					}, {
						name : 'stockGoodsQty',
						type : 'string'
					}, {
						name : 'receiveCustomerName',
						type : 'string'
					}, {
						name : 'receiveCustomerMobilephone',
						type : 'string'
					}, {
						name : 'destroyed',
						type : 'string'
					},{
						name : 'printUserName',
						type : 'string'
					},{
						name : 'printTime',type:'date',
						convert: function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date,'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					},{
						name : 'createTime',type:'date',
						convert: function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date,'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					},{
						name : 'printOrgName',
						type : 'string'
					},{
						name : 'receiveBigCustomer',
						type : 'string'
					}]
		});

//定义到达联数据store
Ext.define('Foss.predeliver.arriveSheet.ArriveStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.arriveSheet.ArriveSheet',
	pageSize : 10,
	proxy : {
		//代理的类型为内存代理
		type : 'ajax',
		//提交方式
		actionMethods : 'POST',
		url : predeliver.realPath('queryArriveSheet.action'),
		//定义一个读取器
		reader : {
			//以JSON的方式读取
			type : 'json',
			//定义读取JSON数据的根对象f
			root : 'vo.arriveDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},//事件监听
	listeners : {
		//查询事件
		beforeload : function(s, operation, eOpts) {
			var queryParamsForm= Ext.getCmp('T_predeliver-arriveSheetIndex_content').getQueryForm();
			var myForm =queryParamsForm.getForm();
			var createBeginTime = myForm.getValues().createBeginTime, 
			createEndTime = myForm.getValues().createEndTime;	
		// 到达联生成时间验证
		if (!Ext.isEmpty(createBeginTime) && !Ext.isEmpty(createEndTime)) {	
			var result = Ext.Date.parse(createEndTime,'Y-m-d H:i:s') - Ext.Date.parse(createBeginTime,'Y-m-d H:i:s');	
			if(result / (24 * 60 * 60 * 1000) >= 3){	
				Ext.ux.Toast.msg('提示信息', '起止日期间隔不要超过三天', 'error', 3000); // '起止日期相隔不能超过30天！'
				return false;	
			}	
		}
		if (!myForm.isValid()) {
			return false;
		}
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var queryParams =queryParamsForm.getValues();
			var waybillNo = queryParams.waybillNo;
			Ext.apply(operation, {
				params : {
					'vo.arriveSheetDto.waybillNo' : queryParams.waybillNo,
					'vo.arriveSheetDto.arrivesheetNo' : queryParams.arrivesheetNo,
					'vo.arriveSheetDto.status' : queryParams.status,
					'vo.arriveSheetDto.isPrinted' : queryParams.isPrinted,
					'vo.arriveSheetDto.goodsName' : queryParams.goodsName,
					'vo.arriveSheetDto.receiveCustomerName' : queryParams.receiveCustomerName,
					'vo.arriveSheetDto.receiveCustomerMobilephone' : queryParams.receiveCustomerMobilephone,
					'vo.arriveSheetDto.createUserName' : queryParams.createUserName,
					'vo.arriveSheetDto.createBeginTime' : queryParams.createBeginTime,
					'vo.arriveSheetDto.createEndTime' : queryParams.createEndTime,
					'vo.arriveSheetDto.destroyed' : queryParams.destroyed
				}
			});
		}
	}
		// autoLoad: true
});

//查询条件
Ext.define('Foss.predeliver.arriveSheet.queryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	//收缩
	collapsible : true,
	//动画收缩
	animCollapse : true,
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.searchCondition'), //查询条件,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100
	},
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
						xtype : 'textfield',
						name : 'waybillNo',
						vtype: 'waybill',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.waybillNo') //运单号
					}, {
						xtype : 'textfield',
						name : 'arrivesheetNo',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrivesheetNo') //到达联编号
					}, {
						xtype : 'combobox',
						name : 'status',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrivesheetStatus'), //到达联状态,
						value : 'NEW',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						editable : false,
						store : FossDataDictionary.getDataDictionaryStore(
								'PKP_ARRIVESHEET_STATUS', null, {
									'valueCode' : '',
									'valueName' : '全部'
								})
					}, {
						xtype : 'checkboxfield',
						name : 'isPrinted',
						inputValue : 'Y',
						//uncheckedValue: 'N',
						columnWidth : 0.1,
						boxLabel : '是否打印'
					}, {
						xtype : 'textfield',
						name : 'goodsName',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.goodsName') //货物名称
					}, {
						xtype : 'textfield',
						name : 'receiveCustomerName',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.receiveCustomerName') //收货人/公司
					}, {
						xtype : 'textfield',
						name : 'receiveCustomerMobilephone',
						columnWidth : 0.3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.receiveCustomerMobilephone') //收货人手机
					}, {
						xtype : 'checkboxfield',
						name : 'destroyed',
						inputValue : 'N',
						//uncheckedValue: 'Y',
						columnWidth : 0.1,
						boxLabel : '是否有效',
						checked : true
					}, {
						xtype : 'rangeDateField',
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.createTime'), //到达联生成时间,
						fieldId : 'FOSS_PredeliverArriveSheetTime_Id',
						dateType : 'datetimefield_date97',
						allowBlank : false,
						allowFromBlank : false,
						allowToBlank : false,
						fromName : 'createBeginTime',
						toName : 'createEndTime',
						fromValue : Ext.Date.format(predeliver.arriveSheet
										.getTargetDate(new Date(), 0),
								'Y-m-d H:i:s'),
						toValue : Ext.Date.format(predeliver.arriveSheet
										.getTargetDate1(new Date(), 0),
								'Y-m-d H:i:s'),
						columnWidth : .6
					}, {
						xtype : 'textfield',
						name : 'createUserName',
						columnWidth : .3,
						fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.createUserName') //创建人
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.reset'), //重置,
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form');
										myform.getForm().reset();
										myform.getForm().findField('createBeginTime').setValue(Ext.Date.format(predeliver.arriveSheet.getTargetDate(new Date(),0),'Y-m-d H:i:s'));	
										myform.getForm().findField('createEndTime').setValue(Ext.Date.format(predeliver.arriveSheet.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));	
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .84,
									html : '&nbsp;'
								}, {
									text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.search'), //查询,
									disabled:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexquerybutton'),
									hidden:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexquerybutton'),
									cls : 'yellow_button',
									columnWidth : .08,
									plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
										seconds: 3
									}),
									handler : function() {
										Ext.getCmp('T_predeliver-arriveSheetIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
									}
								}]
					}]
		});

		me.callParent(arguments);
	}
});

Ext.define('Foss.predeliver.arriveSheet.warn', {
			extend : 'Ext.panel.Panel',
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			layout : 'column',
			// hidden: true,
			initComponent : function() {
				var me = this;

				Ext.applyIf(me, {
							items : [{
										xtype : 'label',
										margin : '0 0 0 10',
										text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.pzcdsl') //排单、装车、库存、到达联件数不一致：
									}, {
										xtype : 'image',
										imgCls : 'predeliver-arriveSheetIndex-row-pink',
										width : 116,
										height : 18
									}]
						});

				me.callParent(arguments);
			}

		});
Ext.define('Foss.predeliver.arriveSheet.editPanel', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaults : {
		margin : '5 10 5 10',
		xtype : 'textfield',
		labelWidth : 90
	},
	items : [{
		fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arriveSheetGoodsQty'), //到达联件数,
		columnWidth : 1,
		xtype : 'numberfield',
		allowDecimals : false,//不允许输入小数  
        allowNegative : false,//不允许输入负数
		minValue : 1,
		maxValue: 9999,
		hideTrigger : true,
		name : 'arriveSheetGoodsQty',
		enableKeyEvents : true,
		listeners : {
			keyup : function(e, t, eOpts) {
				var nowValue = e.getValue();
				var arriveNotoutGoodsQty = predeliver.arriveSheet.editPanel
						.getForm().getRecord().data.arriveNotoutGoodsQty;
				var arriveSheetGoodsQty = predeliver.arriveSheet.editPanel
						.getForm().getRecord().data.arriveSheetGoodsQty;
				if (isNaN(nowValue)) {
					e.setValue(parseInt(arriveSheetGoodsQty));
				}
				if (parseInt(nowValue) > parseInt(arriveNotoutGoodsQty)) {
					e.setValue(parseInt(arriveSheetGoodsQty));
				}
			},
			blur : function(e, t, eOpts) {
				var nowValue = e.getValue();
				var arriveNotoutGoodsQty = predeliver.arriveSheet.editPanel
						.getForm().getRecord().data.arriveNotoutGoodsQty;
				var arriveSheetGoodsQty = predeliver.arriveSheet.editPanel
						.getForm().getRecord().data.arriveSheetGoodsQty;
				if (isNaN(nowValue)) {
					e.setValue(parseInt(arriveSheetGoodsQty));
				}
				if (parseInt(nowValue) > parseInt(arriveNotoutGoodsQty)) {
					e.setValue(parseInt(arriveSheetGoodsQty));
				}
			}
		}
	}, {
		fieldLabel : 'id',
		columnWidth : 1,
		name : 'id',
		hidden : true
	}, {
		fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.waybillNo'), //运单号,
		columnWidth : 1,
		name : 'waybillNo',
		hidden : true
	}, {
		fieldLabel : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arriveNotoutGoodsQty'), //库存件数,
		columnWidth : 1,
		name : 'arriveNotoutGoodsQty',
		hidden : true
	}, {
		xtype : 'button',
		text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.confirm'), //确认,
		columnWidth : .4,
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
		handler : function() {
			var win = this.up('window')
			var arriveValue = this.up('form').getForm().getValues();
			if (this.up('form').getForm().isValid()) {
			Ext.Ajax.request({
				url : predeliver.realPath('modifyArriveSheet.action'),
				jsonData : {
					"vo" : {
						"arriveSheet" : arriveValue
					}
				},
				success : function(response) {
					var text = response.responseText;
					var result = Ext.decode(response.responseText);
					var arriveGridStore = Ext.getCmp('Foss_predeliver_arriveSheet_GridPanel_Id').store;
					var arriveGrid = Ext.getCmp('T_predeliver-arriveSheetIndex_content').getArriveGrid();
					var selectRecord = arriveGrid.getSelectionModel().getSelection()[0];
					win.close();
					arriveGridStore.load({
					    scope: this,
					    callback: function(records, operation, success) {
					    	Ext.Array.each(records, function(record, index, records) {
							    if(record.get('arrivesheetNo')==selectRecord.get('arrivesheetNo')){
							    	arriveGrid.getSelectionModel().select(record);
							    }
							});
					    }
					});
					Ext.MessageBox.confirm('确认框', '到达联更改，是否重新打印？',
						function(btn) {
							if (btn == 'yes') {
								var selectRow = arriveGrid
										.getSelectionModel().getSelection();
								arriveGrid.getSelectionModel()
										.select(selectRow);
								arriveGrid.getPrintWindow().show();
							}
						});

				}
			});
		}
		}
	}, {
		xtype : 'container',
		html : '&nbsp;',
		columnWidth : .2
	}, {
		xtype : 'button',
		text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.cancel'), //取消,
		columnWidth : .4,
		handler : function() {
			this.up('window').close();
		}
	}]
});

predeliver.arriveSheet.editPanel = Ext
		.create('Foss.predeliver.arriveSheet.editPanel');
//编辑任务订单状态window
Ext.define('Foss.predeliver.arriveSheet.editWindow', {
			extend : 'Ext.window.Window',
			width : 250,
			height : 140,
			title : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.updateArrivesheet'), //修改到达联,
			layout : 'column',
			border : false,
			//将window的关闭事件close 设成 hide
			closeAction : 'close',
			items : [predeliver.arriveSheet.editPanel]
		});

function lessCargoDetailsWin() {
	var arriveGrid = Ext.getCmp('T_predeliver-arriveSheetIndex_content')
			.getArriveGrid();
	var selectRow = arriveGrid.getSelectionModel().getSelection();
	var arriveSheet = Ext.ModelManager.create(selectRow[0].data,
			'Foss.predeliver.arriveSheet.ArriveSheet');
	predeliver.arriveSheet.editPanel.loadRecord(arriveSheet);
	var win = Ext.create('Foss.predeliver.arriveSheet.editWindow').show();

	/*Ext.Msg.prompt('修改到达联', '到达联件数:', function(btn, text){
		var me = this;
	    if (btn == 'ok'){
			var id = selectRow[0].data.id;
			var waybillNo = selectRow[0].data.waybillNo;
			if(parseInt(text)> parseInt(arriveNotoutGoodsQty)){
				Ext.Msg.alert("提示信息","修改失败，到达联件数不能大于库存件数！");
				return;
			}
	    	Ext.Ajax.request({
			    url: predeliver.realPath('modifyArriveSheet.action'),
		    	params: {
		    		'vo.arriveSheet.arriveSheetGoodsQty': text,
		    		'vo.arriveSheet.id': id,
		    		'vo.arriveSheet.waybillNo': waybillNo
			    },
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.decode(response.responseText);
					var arriveGridStore = Ext.getCmp('Foss_predeliver_arriveSheet_GridPanel_Id').store;
					arriveGridStore.load();
			    }
			});
	    }
	},this,false,arriveSheetGoodsQty);*/
}

Ext.define('Foss.predeliver.arriveSheet.GridPanel', {
	extend : 'Ext.grid.Panel',
	title : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrivesheetInfo'), //到达联信息,
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	//收缩
	collapsible : true,
	//动画收缩
	animCollapse : true,
	emptyText : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.empty'), //查询结果为空,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
				xtype : 'actioncolumn',
				width : 50,
				text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.print'), //打印,
				align : 'center',
				items : [{
							iconCls : 'deppon_icons_print',
							tooltip : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.print'), //打印,
							handler : function(grid, rowIndex, colIndex) {
								var selection = grid.getStore().getAt(rowIndex);
								if (selection.data.destroyed == 'Y') {
									Ext.ux.Toast.msg("提示信息", "您选择的到达联已经作废，无法打印。", 'error', 3000);
									return;
								}
								var mygrid = this.up('gridpanel');
								mygrid.getSelectionModel().select(selection);
								mygrid.getPrintWindow().show();
							}
						}]
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.waybillNo'), //运单号,
				dataIndex : 'waybillNo',
				width : 100
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrivesheetNo'), //到达联编号,
				dataIndex : 'arrivesheetNo',
				width : 107
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.printtimes'), //打印次数,
				dataIndex : 'printtimes',
				width : 62
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrivesheetStatus'), //到达联状态,
				dataIndex : 'status',
				width : 70,
				renderer : function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value,
							'PKP_ARRIVESHEET_STATUS');
				}
			}, {
				header : '是否有效',
				dataIndex : 'destroyed',
				width : 62,
				renderer : function(value) {
					if (value == 'N') {
						return predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.activation');//激活
					} else {
						return predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.invalid');// 作废
					}
				}
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arriveSheetGoodsQty'), //到达联件数,
				dataIndex : 'arriveSheetGoodsQty',
				width : 105,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var goodsQtyTotal = record.data["goodsQtyTotal"];
					var status = record.data["status"];
					var destroyed = record.data["destroyed"];
					var arriveNotoutGoodsQty = record.data["arriveNotoutGoodsQty"];
					if (status == 'NEW' && destroyed == 'N') {
						return '<div style="float:left">'
								+ value
								+ '/'
								+ goodsQtyTotal
								+ '</div>'
								+ '<div style="float:right"><a href="javascript:lessCargoDetailsWin();">'
								+ '修改' + '</a></div>';
					} else {
						return '<div style="float:left">' + value + '/'
								+ goodsQtyTotal + '</div>'
								+ '<div style="float:right">' + '修改'
								+ '</a></div>';
					}
				}
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arrangeGoodsGty'), //排单件数,
				dataIndex : 'arrangeGoodsGty',
				width : 65
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.arriveNotoutGoodsQty'), //库存件数,
				dataIndex : 'arriveNotoutGoodsQty',
				width : 65
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.goodsName'), //货物名称,
				xtype: 'ellipsiscolumn',
				dataIndex : 'goodsName',
				width : 100
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.receiveCustomerName'), //收货人公司,
				xtype: 'ellipsiscolumn',
				dataIndex : 'receiveCustomerName',
				width : 100,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var bigcustomer = record.data["receiveBigCustomer"];
					if(bigcustomer == 'Y'){ //
						return "<div class='big_Customer_pic_common'></div>" +value ;
					}else return value;
				}
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.printArriveSheet.printUserName'), //打印人
				xtype: 'ellipsiscolumn',
				dataIndex : 'printUserName',
				width : 65
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.printArriveSheet.printTime'), //打印时间
				xtype: 'ellipsiscolumn',
				dataIndex : 'printTime',
				width : 140
			}, {
				header : predeliver.arriveSheet.i18n('pkp.predeliver.printArriveSheet.printOrgName'), //打印部门
				xtype: 'ellipsiscolumn',
				dataIndex : 'printOrgName',
				width : 100
			},{
				header : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.createTime'), //到达联生成时间
				xtype: 'ellipsiscolumn',
				dataIndex : 'createTime',
				width : 140
			}],
	//给表格行涂层
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			var arriveSheetGoodsQty = record.get('arriveSheetGoodsQty');
			var arriveNotoutGoodsQty = record.get('arriveNotoutGoodsQty');
			var arrangeGoodsGty = record.get('arrangeGoodsGty');
			if (parseInt(arriveSheetGoodsQty) != parseInt(arriveNotoutGoodsQty) != parseInt(arrangeGoodsGty)) {
				return 'predeliver-arriveSheetIndex-row-pink';
			}
		}
	},
	printWindow : null,
	getPrintWindow : function() {
		var me = this;
		if (this.printWindow == null) {
			me.printWindow = Ext.create('Foss.printArriveSheet.printOneWindow',
					me);
		}
		return me.printWindow;
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
		  				plugins: 'pagesizeplugin',
						displayInfo: true
					});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.arriveSheet.ArriveStore');
		Ext.MessageBox.buttonText.yes = "确定";
		Ext.MessageBox.buttonText.no = "取消";
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'button',
				text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.batchPrinting'), //批量打印,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				disabled:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexbatchprintbutton'),
				hidden:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexbatchprintbutton'),
				handler : function() {
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel()
							.getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择打印行！");
					} else {
						var mygrid = this.up('gridpanel'),printtimeArriveSheetNos = '',destroyedArriveSheetNos = '',destroyedCount = 0;
						if(selectWaybill.length>50){
					        Ext.ux.Toast.msg('提示信息', "批量打印到达联时，勾选的条数不能大于50条！", 'error', 4000);
					        return;
					    }
						for (var i = 0; i < selectWaybill.length; i++) {
							if (parseInt(selectWaybill[i].data.printtimes) > 0 && selectWaybill[i].data.destroyed == 'N') {
								// 判断已打印的到达联
								if (printtimeArriveSheetNos.length == 0) {
									printtimeArriveSheetNos = selectWaybill[i].data.arrivesheetNo;
								} else {
									printtimeArriveSheetNos = printtimeArriveSheetNos + "," + selectWaybill[i].data.arrivesheetNo;
								}
							}
							if (selectWaybill[i].data.destroyed == 'Y') {
								destroyedCount ++;
								// 判断已作废的到达联
								if (destroyedArriveSheetNos.length == 0) {
									destroyedArriveSheetNos = selectWaybill[i].data.arrivesheetNo;
								} else {
									destroyedArriveSheetNos = destroyedArriveSheetNos + "," + selectWaybill[i].data.arrivesheetNo;
								}
							}
						}
						if (selectWaybill.length == destroyedCount) {
							Ext.ux.Toast.msg('提示信息', '您选择的到达联全部是已作废的到达联，无法打印。', 'error', 3000); 
							return;
						}
						var desMsg = '',printMsg = '',showMsg = '',divHtml = '<div style="white-space: normal; overflow: visible; word-break: break-all;">';
						if (destroyedArriveSheetNos.length > 0) {
							desMsg = '到达联{' + destroyedArriveSheetNos + '}已作废，无法打印。'
						}
						if (printtimeArriveSheetNos.length > 0) {
							printMsg = '到达联{' + printtimeArriveSheetNos + '}已打印，确定要继续打印到达联吗？'
						}
						if (desMsg.length > 0 || printMsg.length > 0) {
							Ext.Msg.confirm('提示信息', divHtml + desMsg + printMsg+'</div>',
								function(btn, text) {
									if (btn == "yes") {
										mygrid.getPrintWindow().show();
									}
								});
						} else {
							mygrid.getPrintWindow().show();
						}
						//do_printpreview('arriveSheet1',{arriveSheetNos:arriveSheetNos});
					}
				}
			}, {
				xtype : 'button',
				text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.invalid'), //作废,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				disabled:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexcancelbutton'),
				hidden:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexcancelbutton'),
				handler : function() {
					var selectRow = me.getSelectionModel().getSelection();
					if (selectRow.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选中一行");
						return;
					}
					Ext.Msg.confirm('提示信息', '确定要作废此到达联？', function(btn, text) {
						if (btn == "yes") {
							var ids = '';
							var waybillNos = '';
							var arriveSheetGoodsQtys = '';
							for (var i = 0; i < selectRow.length; i++) {
								var arriveSheetNos = selectRow[i].data.arrivesheetNo;
								if (selectRow[i].data.status == 'SIGN'
										|| selectRow[i].data.status == 'DELIVER' || selectRow[i].data.status == 'REFUSE') {
									Ext.ux.Toast.msg("提示信息", arriveSheetNos
													+ "只能作废已生成状态的到达联，其他状态的到达联不能作废！",
											'error', 3000);
									return;
									break;
								}
								if (selectRow[i].data.destroyed == 'Y') {
									Ext.ux.Toast.msg("提示信息", arriveSheetNos
													+ "到达联已作废，无需再作废", 'error',
											3000);
									return;
									break;
								}
								if (ids.length == 0) {
									ids = selectRow[i].data.id;
									waybillNos = selectRow[i].data.waybillNo;
									arriveSheetGoodsQtys = selectRow[i].data.arriveSheetGoodsQty;
								} else {
									ids = ids + "," + selectRow[i].data.id;
									waybillNos = waybillNos + ","
											+ selectRow[i].data.waybillNo;
									arriveSheetGoodsQtys = arriveSheetGoodsQtys
											+ ","
											+ selectRow[i].data.arriveSheetGoodsQty;
								}
							}
							Ext.Ajax.request({
								url : predeliver
										.realPath('cancelArriveSheet.action'),
								params : {
									'vo.arriveSheet.id' : ids,
									'vo.arriveSheet.waybillNo' : waybillNos,
									'vo.arriveSheetGoodsQtys' : arriveSheetGoodsQtys
								},
								success : function(response) {
									var json = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg("提示信息", json.message);
									me.store.load();
								},
								exception : function(response) {
									var json = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg('提示信息', json.message,
											'error', 3000);
								}
							});
						}
					});
				}
			}, {
				xtype : 'button',
				text : predeliver.arriveSheet.i18n('pkp.predeliver.arriveSheet.activation'), //激活,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				disabled:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexactivationbutton'),
				hidden:!predeliver.arriveSheet.isPermission('arrivesheetindex/arrivesheetindexactivationbutton'),
				handler : function() {
					var selectRow = me.getSelectionModel().getSelection();
					if (selectRow.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选中一行");
						return;
					}
					Ext.Msg.confirm('提示信息', '确定要激活此到达联？', function(btn, text) {
						var ids = '';
						var waybillNos = '';
						var arriveSheetGoodsQtys = '';
						if (btn == "yes") {
							var ids = '';
							for (var i = 0; i < selectRow.length; i++) {
								var arriveSheetNos = selectRow[i].data.arrivesheetNo;
								if (selectRow[i].data.destroyed == 'N') {
									Ext.ux.Toast.msg("提示信息", arriveSheetNos
													+ "到达联已是激活状态！", 'error',
											3000);
									return;
									break;
								}
								if (ids.length == 0) {
									ids = selectRow[i].data.id;
									waybillNos = selectRow[i].data.waybillNo;
									arriveSheetGoodsQtys = selectRow[i].data.arriveSheetGoodsQty;
								} else {
									ids = ids + "," + selectRow[i].data.id;
									waybillNos = waybillNos + ","
											+ selectRow[i].data.waybillNo;
									arriveSheetGoodsQtys = arriveSheetGoodsQtys
											+ ","
											+ selectRow[i].data.arriveSheetGoodsQty;
								}
							}
							Ext.Ajax.request({
								url : predeliver
										.realPath('activateArriveSheet.action'),
								params : {
									'vo.arriveSheet.id' : ids,
									'vo.arriveSheet.waybillNo' : waybillNos,
									'vo.arriveSheetGoodsQtys' : arriveSheetGoodsQtys
								},
								success : function(response) {
									var json = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg("提示信息", json.message);
									me.store.load();
								},
								exception : function(response) {
									var json = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg('提示信息', json.message,
											'error', 3000);
								}
							});
						}
					});
				}
			}]
		}]
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryForm = Ext.create("Foss.predeliver.arriveSheet.queryForm");
			var warnPanel = Ext.create("Foss.predeliver.arriveSheet.warn");
			var arriveGrid = Ext.create(
					"Foss.predeliver.arriveSheet.GridPanel", {
						id : "Foss_predeliver_arriveSheet_GridPanel_Id"
					});

			Ext.create('Ext.panel.Panel', {
						id : 'T_predeliver-arriveSheetIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getArriveGrid : function() {
							return arriveGrid;
						},
						getQueryForm : function() {
							return queryForm;
						},
						items : [queryForm, warnPanel, arriveGrid],
						renderTo : 'T_predeliver-arriveSheetIndex-body'
					});
		});
