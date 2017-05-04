/** **************2.合车调整界面 begin************* */
// 1.左边grid
Ext.define('Foss.adjustTransportationPath.JoinCarFormLeftLinesPanel', {
	extend : 'Ext.panel.Panel',
	title : '',
	columnWidth : 0.65,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : false,
	joinCarFormLeftLinesGrid : null,
	bodyStyle : 'padding:0px 0px 0px 2px',
	getJoinCarFormLeftLinesGrid : function() {
		var me = this;
		if (me.joinCarFormLeftLinesGrid == null) {
			me.joinCarFormLeftLinesGrid = Ext
					.create('Foss.adjustTransportationPath.JoinCarFormLeftLinesGrid');
			me.joinCarFormLeftLinesGrid.on('itemclick', function(view, record,
							item, index, e, eOpts) {
					}, me);
		}
		scheduling.adjustTransportationPath.joinCarFormLeftLinesGrid = this.joinCarFormLeftLinesGrid;
		return this.joinCarFormLeftLinesGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getJoinCarFormLeftLinesGrid()]
		me.callParent([cfg]);
	}
});

// 2.中间操作按钮
Ext.define('Foss.adjustTransportationPath.JoinCarFormOperationPanel', {
	extend : 'Ext.panel.Panel',
	layout : 'column',
	bodyStyle : 'padding:0px 15px 0px 10px',
	moveToRightButton : null,
	getMoveToRightButton : function() {
		if (this.moveToRightButton == null) {
			this.moveToRightButton = Ext.create('Ext.button.Button', {
						columnWidth : 1,
						text : '>>>'
					});
			this.moveToRightButton.on("click", function() {
				var grid = scheduling.adjustTransportationPath.joinCarFormLeftLinesGrid;
				var selectRecords = grid.getSelectionModel().getSelection();
				if (selectRecords < 1) {
					Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.noSelect'));
					return;
				}
				if (selectRecords > 1) {
					Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.mutiSelect'));
					return;
				}
				Ext.getCmp('labJoinLine').setValue(selectRecords[0]
						.get('areaLine'));

			})
		}
		return this.moveToRightButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
					columnWidth : 1,
					xtype : 'container',
					style : 'padding-top:80px;border:none'
				}, me.getMoveToRightButton(), {
					columnWidth : 1,
					xtype : 'container',
					style : 'padding-top:80px;border:none'
				}];
		me.callParent([cfg]);
	}
});

// 2.右边form区域
Ext.define('Foss.adjustTransportationPath.JoinCarRightForm', {
	extend : 'Ext.form.Panel',
	columnWidth : 0.34,
	frame : false,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	bodyStyle : 'padding:25px 0px 0px 5px',
	fieldDefaults : {
		labelAlign : 'top',
		labelWidth : 250,
		labelStyle : 'font-weight:bold'
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'label',
				text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.objectPath'),
				width : 250,
				style : 'font-weight:bold;'
			}, {
				border : 1,
				xtype : 'form',
				width : 250,
				cls : 'autoHeight',
				padding:'10',
				frame : true,
				bodyStyle : 'padding:12px 0px 0px 0px',
				layout : 'column',
				items : [{
					xtype : 'label',
					columnWidth:0.4,
					text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.joinToPath'),
					style : 'font-weight:bold'
				},{
					xtype : 'textfield',
					readOnly : true,
					columnWidth:0.6,
					id : 'labJoinLine',
					style : 'font-weight:bold'
				}]
			}, {
				fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.changeEndTime'),
				id : 'Foss_adjustTransportPath_datetimefieldForm_ID',
				time : true,
				format : 'Y-m-d H:i:s',
				xtype : 'datetimefield_date97',
				dateConfig : {
					el : 'Foss_adjustTransportPath_datetimefieldForm_ID-inputEl'
				}
			}, {
				xtype : 'label',
				text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.notes'),
				style : 'font-weight:bold',
				width : 250
			}, {
				border : 1,
				xtype : 'container',
				width : 250,
				height : 30,
				frame : true
			}, {
				border : 1,
				xtype : 'container',
				width : 250,
				height : 30,
				frame : true,
				defaultType : 'button',
				layout : 'hbox',
				items : [{
					text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.save'),
					cls : 'yellow_button',
					width : 70,
					handler : function() {
						// grid object
						var grid = scheduling.adjustTransportationPath.joinCarFormLeftLinesGrid;
						// schedulingVO
						var schedulingVO = scheduling.adjustTransportationPath.adjustTransportPathWindow.schedulingVO;
						// 选中的行
						var selectionRecords = grid.getSelectionModel()
								.getSelection();
						// 如果没有选中则提示
						if (selectionRecords.length == 0) {
							alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.selectNewPath'));
						} else if (Ext.isEmpty(Ext.getCmp('labJoinLine')
								.getValue())) {
							alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.addToRight'));
						} else {
							var adjustEntity = selectionRecords[0];
							schedulingVO.adjustEntity = adjustEntity.data;
							var winForm = this.ownerCt.ownerCt.ownerCt;
							// 时间
							var time = Ext
									.getCmp('Foss_adjustTransportPath_datetimefieldForm_ID').rawValue;
							if (time != null && time != "") {
								schedulingVO.changePathEntity = {};
								schedulingVO.changePathEntity.effectEndTime = time;
							}

							var params = {
								schedulingVO : schedulingVO
							};
							this.setDisabled(true);
							// scheduling.i18n('foss.adjustTransportationpath.alterTitle');
							var title = scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint');
							Ext.Ajax.request({
								url : scheduling.realPath('joinVehicle.action'),
								jsonData : params,
								success : function(response) {
									// var message =
									// scheduling.i18n('foss.adjustTransportationpath.adjustPathSuccessMessage');
									Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.success'));
									winForm.close();
									scheduling.adjustTransportationPath.pagingBar
											.moveFirst();
								},
								exception : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.MessageBox.alert(title, result.message);
								}
							});
						}
					}
				}, {
					xtype : 'container',
					width : 40,
					html : '&nbsp;'
				}, {
					text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.cancle'),
					cls : 'yellow_button',
					width : 70,
					handler : function() {
						if (!scheduling.adjustTransportationPath.adjustTransportPathWindow)
							return false;
						scheduling.adjustTransportationPath.adjustTransportPathWindow
								.close();
					}
				}]
			}]
		me.callParent([cfg]);
	}
});

Ext.define('Foss.adjustTransportationPath.JionCarGridResultStore', {
			extend : 'Ext.data.Store',
			// 定义字段
			fields : ['orgCode', 'goodsAreaCode', 'areaLine', 'areaWeightTotal']
		});

Ext.define('Foss.adjustTransportationPath.JoinCarFormLeftLinesGrid', {
	extend : 'Ext.grid.Panel',
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	bodyCls : 'autoHeight',
	frame : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.selectNeedChange'),
	animCollapse : true,
	selModel : null,
	store : null,
	autoScroll : true,
	height : 300,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create("Foss.adjustTransportationPath.JionCarGridResultStore");
		me.selModel = Ext.create('Ext.selection.RadioModel');
		me.callParent([cfg]);
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.goodsAreaCode'),
				// 关联model中的字段名
				dataIndex : 'goodsAreaCode',
				width : 70
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaLine'),
				// 关联model中的字段名
				dataIndex : 'areaLine',
				width : 300
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'areaWeightTotal',
				width : 70
			}]

});
/** **************2.合车调整界面 end************* */
