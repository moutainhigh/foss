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
		platform.adjustTransportationPath.joinCarFormLeftLinesGrid = this.joinCarFormLeftLinesGrid;
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
				var grid = platform.adjustTransportationPath.joinCarFormLeftLinesGrid;
				var selectRecords = grid.getSelectionModel().getSelection();
				if (selectRecords < 1) {
					Ext.MessageBox.alert(platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.hint'), platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.noSelect'));
					return;
				}
				if (selectRecords > 1) {
					Ext.MessageBox.alert(platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.hint'), platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.mutiSelect'));
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
				text : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.objectPath'),
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
					text : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.joinToPath'),
					style : 'font-weight:bold'
				},{
					xtype : 'textfield',
					readOnly : true,
					columnWidth:0.6,
					id : 'labJoinLine',
					style : 'font-weight:bold'
				}]
			}, {
				fieldLabel : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.changeEndTime'),
				id : 'Foss_adjustTransportPath_datetimefieldForm_ID',
				time : true,
				format : 'Y-m-d H:i:s',
				xtype : 'datetimefield_date97',
				dateConfig : {
					el : 'Foss_adjustTransportPath_datetimefieldForm_ID-inputEl'
				}
			}, {
				xtype : 'label',
				text : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.notes'),
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
					text : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.save'),
					cls : 'yellow_button',
					width : 70,
					handler : function() {
						// grid object
						var grid = platform.adjustTransportationPath.joinCarFormLeftLinesGrid;
						// platformVO
						var platformVO = platform.adjustTransportationPath.adjustTransportPathWindow.platformVO;
						// 选中的行
						var selectionRecords = grid.getSelectionModel()
								.getSelection();
						// 如果没有选中则提示
						if (selectionRecords.length == 0) {
							alert(platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.selectNewPath'));
						} else if (Ext.isEmpty(Ext.getCmp('labJoinLine')
								.getValue())) {
							alert(platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.addToRight'));
						} else {
							var adjustEntity = selectionRecords[0];
							platformVO.adjustEntity = adjustEntity.data;
							var winForm = this.ownerCt.ownerCt.ownerCt;
							// 时间
							var time = Ext
									.getCmp('Foss_adjustTransportPath_datetimefieldForm_ID').rawValue;
							if (time != null && time != "") {
								platformVO.changePathEntity = {};
								platformVO.changePathEntity.effectEndTime = time;
							}

							var params = {
								platformVO : platformVO
							};
							this.setDisabled(true);
							// platform.i18n('foss.adjustTransportationpath.alterTitle');
							var title = platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.hint');
							Ext.Ajax.request({
								url : platform.realPath('joinVehicle.action'),
								jsonData : params,
								success : function(response) {
									// var message =
									// platform.i18n('foss.adjustTransportationpath.adjustPathSuccessMessage');
									Ext.ux.Toast.msg(platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.hint'), platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.success'));
									winForm.close();
									platform.adjustTransportationPath.pagingBar
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
					text : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.cancle'),
					cls : 'yellow_button',
					width : 70,
					handler : function() {
						if (!platform.adjustTransportationPath.adjustTransportPathWindow)
							return false;
						platform.adjustTransportationPath.adjustTransportPathWindow
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
	title : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.selectNeedChange'),
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
				header : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.goodsAreaCode'),
				// 关联model中的字段名
				dataIndex : 'goodsAreaCode',
				width : 70
			}, {
				// 字段标题
				header : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.areaLine'),
				// 关联model中的字段名
				dataIndex : 'areaLine',
				width : 300
			}, {
				// 字段标题
				header : platform.adjustTransportationPath.i18n('foss.platform.adjustTransportationPath.joinCar.areaWeightTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'areaWeightTotal',
				width : 70
			}]

});
/** **************2.合车调整界面 end************* */
