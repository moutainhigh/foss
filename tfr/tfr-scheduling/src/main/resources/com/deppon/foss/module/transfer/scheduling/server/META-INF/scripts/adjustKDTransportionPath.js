
// 查询条件
var goodAreaCodeTemp = '';
Ext.define('Foss.adjustKDTransportionPath.QueryForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryTerm'),
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	id : 'Foss_adjustKDTransportionPath_QueryForm_Id',
	items : [{
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				name : 'orgCode',
				id : 'Foss_adjustKDTransportionPath_QueryForm_orgCode_Id',
				fieldLabel : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.transferCenter'),
				xtype: 'dynamicorgcombselector',
				type : 'ORG',
				transferCenter: 'Y',
				labelWidth: 50,
				columnWidth : .30,
				disabled:true,
				listeners: {
		            select: {
				       fn: function(src, records, eOpts ) {
				            scheduling.adjustKDTransportionPath.queryForm.getForm().findField('goodsAreaCode').deptCode = records[0].get('code');
				    	}
			   		}
		       }
			}, {
			
			    name : 'goodsAreaCode',//货区
				id : 'Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id',
				fieldLabel : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.goodsAreaCode'),
				xtype: 'textfield',
				labelWidth: 50,
				columnWidth : .30,
				disabled:true,
				hidden:true
			}, {
				name : 'goodsAreaCodeforpackage',
				id : 'Foss_adjustKDTransportionPath_QueryForm_goodsAreaCodeforpackage_Id',
				fieldLabel : '调整运作部门',
				xtype: 'commontransfercenterselector',
				displayField:'name', 
				valueField:'orgCode',
				deptCode : scheduling.adjustKDTransportionPath.outDeptCode,
				labelWidth: 100,
				columnWidth : .30
				/*listeners: {
		            focus: {
				       fn: function(src, records, eOpts ) {
								goodAreaCodeTemp =  Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id").getValue();
									
				    	}
			   		},
					blur:{
						fn: function(src, records, eOpts ) {

							goodAreaCodeTemp =  Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id").getValue();
							
				    	}
					}
		       }*/
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			},
			 {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.reset'),
					columnWidth : .08,
					handler : function() {
						var setValue = function(id, value) {
							Ext.getCmp(id).setValue(value);
						};
						/*setValue(
								'Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id',
								'');*/
						//modify by liangfuxiang 2013-04-18 BUG-7549
						setValue(
								'Foss_adjustKDTransportionPath_QueryForm_orgCode_Id',
								FossUserContext.getCurrentDept().name);
					}
				}, {
					xtype : 'container',
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					text : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.query'),
					disabled: !scheduling.adjustKDTransportionPath.isPermission('scheduling/queryKDTransportionPathButton'),
					hidden: !scheduling.adjustKDTransportionPath.isPermission('scheduling/queryKDTransportionPathButton'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						// var panel = this.ownerCt.ownerCt;
						// panel.querygoodsArea();
						scheduling.adjustKDTransportionPath.pagingBar
								.moveFirst();
						scheduling.adjustKDTransportionPath.queryResult.show();
					}
				}]
			}]
      });


      

// model 第三层 流水号 货物信息
Ext.define('Foss.adjustKDTransportionPath.SerialNoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'serialNo',
						type : 'string'
					}]
		});

// 流水号信息 第三层
Ext.define('Foss.adjustKDTransportionPath.QuerySerialNoResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adjustKDTransportionPath.SerialNoModel',
			proxy : {
				type : 'ajax',
				url : scheduling.realPath('queryLevel3.action'),
				reader : {
					type : 'json',
					root : 'schedulingVO.adjustList'
				}
			}
		});

Ext.define('Foss.adjustKDTransportionPath.SerialNoResultGridPanel', {
	frame : true,
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	sortableColumns: false,
	cls : 'autoHeight',
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	animCollapse : true,
	emptyText: scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	baseCls : 'adjustKDTransportionPath-serialNoGap',
	selModel : null,
	store : null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.adjustKDTransportionPath.QuerySerialNoResultStore');
		// me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
	},
	bindData : function(record, grid, rowBodyElement) {
		var orgCode = record.get("orgCode");
		var goodsAreaCode = record.get("goodsAreaCode");
		var waybillNo = record.get("waybillNo");
		rowBodyElement.getStore().load({
					params : {
						'schedulingVO.adjustEntity.orgCode' : orgCode,
						'schedulingVO.adjustEntity.goodsAreaCode' : goodsAreaCode,
						'schedulingVO.adjustEntity.waybillNo' : waybillNo
					},
					callback : function(records, operation, success) {
					}
				});
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.serialNo'),
				// 关联model中的字段名
				dataIndex : 'serialNo',
				width : 200,
				renderer : function(v, m) {
					return v;
				}
			}]
});

// model 第二层 运单信息
Ext.define('Foss.adjustKDTransportionPath.WayBillModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'orgCode',
						type : 'string'
					}, {
						name : 'orgName',
						type : 'string'
					}, {
						name : 'goodsAreaCode',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'goodsWeightTotal',
						type : 'number'
					}, {
						name : 'goodsVolumeTotal',
						type : 'number'
					}, {
						name : 'beforeLine',
						type : 'string'
					}, {
						name : 'afterLine',
						type : 'string'
					}, {
						name : 'ifDisable',
						type : 'number'
					}]
		});


  Ext.define('Foss.adjustKDTransportionPath.QueryWayBillResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adjustKDTransportionPath.WayBillModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : scheduling.realPath('queryExpressWaybillList.action'),
				reader : {
					type : 'json',
					root : 'schedulingVO.adjustList'
				},			
listeners:{
	exception : function(dataProxy, response, action, options) {
		var result = Ext.decode(response.responseText);
		Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'),  result.message);
    }
}
			}
		});

Ext.define('Foss.adjustKDTransportionPath.WayBillResultGridPanel', {
	frame : true,
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	sortableColumns: false,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	animCollapse : true,
	emptyText: scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	baseCls : 'adjustKDTransportionPath-wayBillGap',
	selModel : null,
	// store: null,
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : true,
		rowBodyElement : 'Foss.adjustKDTransportionPath.SerialNoResultGridPanel'
	}],
	viewConfig : {
		// 处理勾选事件
		getRowClass : function(record, rowIndex, rowParams, store) {
			 var superRecord = this.up('grid').record.get('goodsAreaCode');
			 var goodsAreaCode = record.get('goodsAreaCode');
			if (record.get('ifDisable') == 1) {
				return 'disabledrow';
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.adjustKDTransportionPath.QueryWayBillResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
					showHeaderCheckbox : false
				}, {
					checkOnly : true
				});
		me.callParent([cfg]);
	},
	bindData : function(record, grid, rowBodyElement) {
		var goodsAreaCode = record.get('goodsAreaCode');
		var orgCode = record.get('orgCode');
		var nextPackageCode = Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCodeforpackage_Id").getValue();
		// store =
		// Ext.create('Foss.adjustTransportationPath.QueryWayBillResultStore');
		rowBodyElement.getStore().load({
					params : {
						'schedulingVO.adjustEntity.orgCode' : orgCode,
						'schedulingVO.adjustEntity.goodsAreaCode' : goodsAreaCode,
						'schedulingVO.adjustEntity.packageNextOrgCode' :nextPackageCode
					},
					callback : function(records, operation, success) {
					}
				});
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.waybillNo'),
				// 关联model中的字段名
				dataIndex : 'waybillNo',
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsWeightTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'goodsWeightTotal',
				width : 100,
				flex : 1
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsVolumeTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'goodsVolumeTotal',
				width : 100
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.beforeLine'),
				// 关联model中的字段名
				dataIndex : 'beforeLine',
				xtype : 'ellipsiscolumn',
				width : 250
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.afterLine'),
				// 关联model中的字段名
				dataIndex : 'afterLine',
				xtype : 'ellipsiscolumn',
				width : 200
			}],
	listeners : {
		'select' : function(rowModel, record, index, eOpts) {
			var superGrid = this.superGrid;
			var goodsAreaCode = record.get('goodsAreaCode');
			var superRecord = superGrid.store.findRecord('goodsAreaCode',
					goodsAreaCode, 0, false, false, false);
			// 如果第二级条目的货区和第一级不相等则不能被选择
			if (record.get('ifDisable') == 1) {
				return false;
			}
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord, true, true);
		},
		'deselect' : function(rowModel, record, index, eOpts) {
			var selectedList = this.getSelectionModel().selected;
			if (selectedList.length == 0) {
				var superGrid = this.superGrid;
				var goodsAreaCode = record.get('goodsAreaCode');
				var superRecord = superGrid.store.findRecord('goodsAreaCode',
						goodsAreaCode, 0, false, false, false);
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord, true);
			}
		}
	}
   });
		

//调整运作部门
/** ************快递目的站的定义*************** begin */
Ext.define('Foss.commonSelector.GoodsAreaForPackageModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'valueName'
					}, {
						name : 'valueCode'
					}]
		});

Ext.define('Foss.commonSelector.GoodsAreaForPackageStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.GoodsAreaForPackageModel',
			pageSize : 15,
			proxy : {
				type : 'ajax',
				url : scheduling.realPath('queryTransferCenter.action'),
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'schedulingVO.packageOrgList',
					totalProperty : 'totalCount'
				}
			}
		});
//调整运作部门
//快递目的站单选
Ext.define('Foss.commonSelector.GoodsAreaForPackageSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commongoodsareaForPackageselector',
//	fieldLabel : '快递目的站',
	displayField : 'valueName',// 显示名称
	valueField : 'valueCode',// 值
	deptCode : null,
	queryParam : 'schedulingVO.adjustEntity.orgName',// 查询参数
	showContent : '{valueName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		
		me.store = Ext.create('Foss.commonSelector.GoodsAreaForPackageStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['schedulingVO.adjustEntity.orgCode'] = me.deptCode;
			searchParams['schedulingVO.adjustEntity.goodsAreaCode'] = goodAreaCodeTemp;
		})
		me.callParent([cfg]);
	}
});

//model 第一层 库区信息
Ext.define('Foss.adjustKDTransportionPath.goodsAreaModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'orgCode',
						type : 'string'
					}, {
						name : 'orgName',
						type : 'string'
					}, {
						name : 'goodsAreaCode',
						type : 'string'
					}, {
						name : 'goodsAreaName',
						type : 'string'
					}, {
						name : 'areaWaybillQty',
						type : 'number'
					}, {
						name : 'areaWeightTotal',
						type : 'number'
					}, {
						name : 'areaVolumeTotal',
						type : 'number'
					}, {
						name : 'areaLine',
						type : 'string'
					}, {
						name : 'ifDisable',
						type : 'number'
					}]
		});

// 库区信息 第一层
Ext.define('Foss.adjustKDTransportionPath.QueryResultStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.adjustKDTransportionPath.goodsAreaModel',
	autoLoad : true,
	pageSize : 10,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryExpressWaybills.action'),
		reader : {
			type : 'json',
			root : 'schedulingVO.adjustList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		},
		//add by liangfuxiang 2013-05-24 抛出正确的异常信息 begin
        listeners:{
        	exception : function(dataProxy, response, action, options) {
        		var result = Ext.decode(response.responseText);
        		Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'),  result.message);
            }
        }
	},
	listeners : {
		beforeload : function(store, operation) {
			var queryForm = scheduling.adjustKDTransportionPath.queryForm
					.getValues();
			if(null==queryForm.orgCode||queryForm.orgCode==""){
				queryForm.orgCode = FossUserContext.getCurrentDeptCode();
			}
			
			var goodsAreaCode = Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id").getValue();
			var packageNextOrgCode = Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCodeforpackage_Id").getValue();

			if(packageNextOrgCode==queryForm.orgCode){
			    Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'), '不能查询【本部门】->【本部门】的快递货啊！','error');
			    return ;
			}
			
			
			Ext.apply(operation, {
				params : {
					'schedulingVO.adjustEntity.goodsAreaCode' : goodsAreaCode,
					'schedulingVO.adjustEntity.orgCode' : queryForm.orgCode,
					'schedulingVO.adjustEntity.packageNextOrgCode' :packageNextOrgCode,
					'schedulingVO.adjustEntity.packageGoodsAreaCode' : goodAreaCodeTemp
				}
			});
		},
		load: function(store, records, successful, epots){
			if(successful == false){
				 Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsAreaError'),'error');
			}else{
				if(store.getCount() == 0){
					 Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),'error');
				}	
			}
		}
	}
});

Ext.define('Foss.adjustKDTransportionPath.QueryResult', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	// 定义表格的标题
	title : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryResult'),
	animCollapse : true,
	emptyText: scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	sortableColumns: false,
	// selModel:null,
	// store: null,
	plugins : [{
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : true,
				pluginId : 'Foss_WayBillResultGridPanel_Id',
				// 行体内容
				rowBodyElement : 'Foss.adjustKDTransportionPath.WayBillResultGridPanel'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.adjustKDTransportionPath.QueryResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
					checkOnly : true
				});
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					plugins: Ext.create('Deppon.ux.PageSizePlugin', {
		            limitWarning: 200})
				});
		scheduling.adjustKDTransportionPath.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		defaults : {
			margin : '0 0 5 3'
		},
		items : {
			xtype : 'button',
			disabled: !scheduling.adjustKDTransportionPath.isPermission('scheduling/adjustKDTransportionPathButton'),
			hidden: !scheduling.adjustKDTransportionPath.isPermission('scheduling/adjustKDTransportionPathButton'),
			text : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.pathChange'),
			columnWidth : .1,
			index : 0,
			// 走货路径界面
			adjustTransportPathForm : function(schedulingVO) {
				scheduling.adjustKDTransportionPath.adjustTransportPathDomList = new Array();
				scheduling.adjustKDTransportionPath.nowWhichCode = schedulingVO.adjustEntity.orgCode;
				scheduling.adjustKDTransportionPath.nowWhichName = schedulingVO.adjustEntity.orgName;
				var adjustFormTop = Ext
						.create('Foss.adjustKDTransportionPath.AdjustFormTop');
				var adjustFormInput = Ext
						.create('Foss.adjustKDTransportionPath.AdjustFormInput');
				scheduling.adjustKDTransportionPath.adjustTransportPathDomList
						.push(adjustFormInput);
				var addAreaContainer = Ext
						.create('Foss.adjustKDTransportionPath.AddAreaContainer');

				var tipMessageAreaContainer = Ext
						.create(
								'Foss.adjustKDTransportionPath.TipMessageAreaContainer',
								{
									hidden : true
								});
				scheduling.adjustKDTransportionPath.tipMessageAreaContainer = tipMessageAreaContainer;
				var adjustKDTransportionPath = Ext.create("Ext.Window", {
					title : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.pathChange'),
					width : 700,
					// 指定表格的高度
					height : 600,
					// 自动增加滚动条
					autoScroll : true,
					resizable : true,
					schedulingVO : schedulingVO,
					layout : 'auto',
					modal : true,
					adjustFormInput : adjustFormInput,
					adjustFormTop : adjustFormTop,
					addAreaContainer : addAreaContainer,
					loadData : function() {
						// 现在所处
						var nowWhichObject = this.adjustFormInput.getForm()
								.findField('nowWhich');
						nowWhichObject
								.setValue(scheduling.adjustKDTransportionPath.nowWhichName);
						// 默认下一站
						var areaLine = this.schedulingVO.adjustEntity.areaLine;
						var nextOrg = areaLine.substring(areaLine.indexOf("-")
								+ 1);
						var defaultNextStationObject = this.adjustFormInput
								.getForm().findField('defaultNextStation');
						defaultNextStationObject.setValue(nextOrg);
						defaultNextStationObject.setVisible(true);
						scheduling.adjustKDTransportionPath.defaultNextStation = nextOrg;
						adjustFormInput
								.findObjectiveOrgCode(this.schedulingVO.adjustEntity.orgCode);
					},
					items : [
							tipMessageAreaContainer,
							adjustFormTop,
							adjustFormInput,
							addAreaContainer,
							Ext.create('Foss.adjustKDTransportionPath.BtnAreaPanel')]
				});
				scheduling.adjustKDTransportionPath.adjustTransportPath = adjustKDTransportionPath;
				adjustKDTransportionPath.show();
				scheduling.adjustKDTransportionPath.newTransportPath = scheduling.adjustKDTransportionPath.nowWhichName;
				// 最上面 原路径
				scheduling.adjustKDTransportionPath.adjustTransportPathList = new Array();
				var originalPathObject = adjustFormTop.getForm()
						.findField('originalPath');
				originalPathObject.setValue(schedulingVO.adjustEntity.areaLine);
				adjustKDTransportionPath.loadData();

			},
			/*JoinCarForm : function(schedulingVO) {
				var joinCarFormLeftLinesPanel = Ext
						.create('Foss.adjustTransportationPath.JoinCarFormLeftLinesPanel');
				var adjustTransportPath = Ext.create("Ext.Window", {
					title : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.joinCarChange'),
					height : 400,
					resizable : false,
					width : 1000,
					layout : 'column',
					modal : true,
					schedulingVO : schedulingVO,
					items : [
							joinCarFormLeftLinesPanel,
							Ext
									.create('Foss.adjustTransportationPath.JoinCarFormOperationPanel'),
							Ext
									.create('Foss.adjustTransportationPath.JoinCarRightForm')]
				});
				scheduling.adjustTransportationPath.adjustTransportPathWindow = adjustTransportPath;
				adjustTransportPath.show();
				joinCarFormLeftLinesPanel.getJoinCarFormLeftLinesGrid().store
						.loadData(schedulingVO.adjustList);
			},*/
			handler : function() {
				var grid = this.ownerCt.ownerCt;
				var SelectionModel = grid.getSelectionModel();

				var SelectionRecord = SelectionModel.getSelection();
				var adjustList = new Array();
				Ext.each(SelectionRecord, function(record) {
							// 如果不可调整货区 不添加
							if (record.get('ifDisable') != 1) {
								adjustList.push(record.data);
							}
						});
				// 设置有效条目
				var countLength = adjustList.length;
				if (countLength == 0) {
					Ext.MessageBox.alert(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.selectPath'),'error');
					return;
				} else if (countLength > 1) {
					var openForm = this.JoinCarForm;
					var url = scheduling.realPath('joinVehiclePrepare.action');
				} else {
					var openForm = this.adjustTransportPathForm;
					var url = scheduling.realPath('modifyPathPrepare.action');
				}
				plugin = grid.getPlugin('Foss_WayBillResultGridPanel_Id');
				if (!Ext.isEmpty(plugin.getExpendRow())) {
					var items = plugin.getExpendRowBody();
					for (var i in items) {
						var item = items[i];
						if (!item.getSelectionModel().hasSelection()) {
							continue;
						}
						var wayBillSelectionRecord = item.getSelectionModel()
								.getSelection();
						Ext.each(wayBillSelectionRecord, function(record) {
									// 如果第二级条目的货区和第一级不相等则不能被添加
									if (record.get('ifDisable') == 1) {
										return false;
									}
									adjustList.push(record.data);
								});
					}
				}
				var params = {
					schedulingVO : {
						adjustList : adjustList
					}
				};

				Ext.Ajax.request({
							url : url,
							actionMethod : 'POST',
							jsonData : params,
							success : function(response) {
								var result = Ext.decode(response.responseText);
								openForm(result.schedulingVO);
							}
						});
			}
		}
	}],
	// 定义表格列信息
	columns : [{
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.goodsAreaName'),
				dataIndex : 'goodsAreaName',
				tdCls : 'adjustTransportationPathQuery',
				width : 150
			}, {
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.areaWaybillQty'),
				xtype : 'numbercolumn',
				format : '0',
				dataIndex : 'areaWaybillQty',
				width : 300
			}, {
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaWeightTotal'),
				dataIndex : 'areaWeightTotal',
				xtype : 'numbercolumn',
				width : 150,
				flex : 1
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaVolumeTotal'),
				// 关联model中的字段名
				dataIndex : 'areaVolumeTotal',
				xtype : 'numbercolumn',
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaLine'),
				// 关联model中的字段名
				dataIndex : 'areaLine',
				xtype : 'ellipsiscolumn',
				width : 250
			}],
	viewConfig : {
		// 处理勾选事件
		getRowClass : function(record, rowIndex, rowParams, store) {
			if (record.get('ifDisable') == 1) {
				return 'disabledrow';
			}
		}
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts) {
			var grid = this, plugin = grid
					.getPlugin('Foss_WayBillResultGridPanel_Id');
			// 贵重物品货区不可勾选
			if (record.get('ifDisable') == 1) {
				return false;
			}
			if (!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				// var store = item[].store;
				// var subGoodsAreaCode = store.getAt(0).get('goodsAreaCode');
				for (var i in item) {
					if (item[i].store.getAt(0).get('goodsAreaCode') == record
							.get('goodsAreaCode')) {
						item[i].getSelectionModel().selectAll(true);
						break;
					}
				}
			}
		},
		'deselect' : function(rowModel, record, index, eOpts) {
			var grid = this, plugin = grid
					.getPlugin('Foss_WayBillResultGridPanel_Id');
			if (!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				// var store = item[index].store;
				// var subGoodsAreaCode = store.getAt(0).get('goodsAreaCode');
				for (var i in item) {
					if (item[i].store.getAt(0).get('goodsAreaCode') == record
							.get('goodsAreaCode')) {
						item[i].getSelectionModel().deselectAll(true);
						break;
					}
				}
			}
		}
	}
});		
		

Ext.onReady(function() {
			Ext.QuickTips.init();
			
			//找快递库区编码
			Ext.Ajax.request({
				url:scheduling.realPath('queryGoodsAreaByType.action'),
				async: false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					if(result.success){
						scheduling.adjustKDTransportionPath.goodsAreaCode=result.schedulingVO.goodsAreaCode;
					}
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					if(Ext.isEmpty(json)){
						Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportionPath.hint'),  result.message);
					}else{
						Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportionPath.hint'),  result.message);
					}
				}
			});
			
			var queryForm = Ext.create('Foss.adjustKDTransportionPath.QueryForm');
			var queryResult = Ext.create(
					'Foss.adjustKDTransportionPath.QueryResult', {
						id : 'Foss_adjustKDTransportionPath_QueryGrid_Id'
					});
			scheduling.adjustKDTransportionPath.queryForm = queryForm;	
			queryForm.getForm().findField('orgCode').setValue(FossUserContext.getCurrentDept().name);
			Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id").setValue(scheduling.adjustKDTransportionPath.goodsAreaCode);
			
			scheduling.adjustKDTransportionPath.queryResult = queryResult;
			Ext.create('Ext.panel.Panel', {
								id : 'T_scheduling-adjustKDTransportionPath_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								items : [queryForm,queryResult],
								renderTo : 'T_scheduling-adjustKDTransportionPath-body'
							});
			Ext.getCmp("Foss_adjustKDTransportionPath_QueryForm_goodsAreaCode_Id").setDisabled(true);
		});
