// 查询条件
var goodAreaCodeTemp = '';
Ext.define('Foss.adjustTransportationPath.QueryForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryTerm'),
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	id : 'Foss_adjustTransportationPath_QueryForm_Id',
	items : [{
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				name : 'orgCode',
				id : 'Foss_adjustTransportationPath_QueryForm_orgCode_Id',
				fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.transferCenter'),
				xtype: 'dynamicorgcombselector',
				type : 'ORG',
				transferCenter: 'Y',
				labelWidth: 50,
				columnWidth : .30,
				disabled:true,
				listeners: {
		            select: {
				       fn: function(src, records, eOpts ) {
				            scheduling.adjustTransportationPath.queryForm.getForm().findField('goodsAreaCode').deptCode = records[0].get('code');
				    	}
			   		}
		       }
			}, {
				name : 'goodsAreaCode',
				id : 'Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id',
				fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.goodsAreaCode'),
				////modify by liangfuxiang 2013-05-29 begin BUG-19817
				//xtype: 'commongoodsareaselector',
				xtype: 'commongoodsareamultiselector',
				//modify by liangfuxiang 2013-05-29 begin BUG-12600 调整走货路径界面，库区查询结果不对
				//deptCode : null,
				deptCode : scheduling.adjustTransportationPath.outDeptCode,
				//modify by liangfuxiang 2013-05-29 end
				labelWidth: 50,
				listeners: {
		            select: {
				       fn: function(src, records, eOpts ) {
				          var tempPackName = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue;
							//包含了快递库区
				        //提示 不能进行快递货走货路径调整，请到快递走货路径调整界面操作!
				          if(tempPackName.indexOf("快递库区")>=0)
				          {
				        	  Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), '不能进行快递货走货路径调整，请到快递走货路径调整界面操作!');
				              return;
				          }
							/*var packageObj=Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id");
							if(tempPackName.indexOf("快递库区")>=0){
								//packageObj.setDisabled(false);
								
								var bk=0;
								Ext.each(Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue.split(','),function(item){
									if(item.trim()=='快递库区'){
										goodAreaCodeTemp = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").getValue()[bk];
									}
									bk++;
								});
								
							}else{
								packageObj.setDisabled(true);
								packageObj.setValue('');
							}*/
				    	}
			   		},
					blur:{
						fn: function(src, records, eOpts ) {
				          var tempPackName = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue;
							//包含了快递库区
				          //提示 不能进行快递货走货路径调整，请到快递走货路径调整界面操作!
				          if(tempPackName.indexOf("快递库区")>=0)
				          {
				        	  Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), '不能进行快递货走货路径调整，请到快递走货路径调整界面操作!');
				              return;
				          }
							//var packageObj=Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id");
							/*if(tempPackName.indexOf("快递库区")>=0){
								packageObj.setDisabled(false);
								
								var bk=0;
								Ext.each(Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue.split(','),function(item){
									if(item.trim()=='快递库区'){
										goodAreaCodeTemp = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").getValue()[bk];
									}
									bk++;
								});
								
							}else{
								packageObj.setDisabled(true);
								packageObj.setValue('');
							}*/
				    	}
					}
		       }, 
				columnWidth : .30
			}/*, {
				name : 'goodsAreaCodeforpackage',
				id : 'Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id',
				//fieldLabel : '快递库区目的站',
				fieldLabel : '调整运作部门',
				xtype: 'commongoodsareaForPackageselector',
				//deptCode : null,
				deptCode : scheduling.adjustTransportationPath.outDeptCode,
				labelWidth: 100,
				listeners: {
		            focus: {
				       fn: function(src, records, eOpts ) {
				          var tempPackName = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue;
							//包含了快递库区
							if(tempPackName.indexOf("快递库区")>=0){
								var bk=0;
								Ext.each(Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue.split(','),function(item){
									if(item.trim()=='快递库区'){
										goodAreaCodeTemp = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").getValue()[bk];
									}
									bk++;
								});
								
							}
				    	}
			   		},
					blur:{
						fn: function(src, records, eOpts ) {
				           var tempPackName = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue;
							//包含了快递库区
							if(tempPackName.indexOf("快递库区")>=0){
								var bk=0;
								Ext.each(Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue.split(','),function(item){
									if(item.trim()=='快递库区'){
										goodAreaCodeTemp = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").getValue()[bk];
									}
									bk++;
								});
								
							}
				    	}
					}
		       }, 
				columnWidth : .30
			}*/, {
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
					text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.reset'),
					columnWidth : .08,
					handler : function() {
						var setValue = function(id, value) {
							Ext.getCmp(id).setValue(value);
						};
						setValue(
								'Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id',
								'');
						//modify by liangfuxiang 2013-04-18 BUG-7549
						setValue(
								'Foss_adjustTransportationPath_QueryForm_orgCode_Id',
								FossUserContext.getCurrentDept().name);
					}
				}, {
					xtype : 'container',
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.query'),
					disabled: !scheduling.adjustTransportationPath.isPermission('scheduling/querytTransportationPathButton'),
					hidden: !scheduling.adjustTransportationPath.isPermission('scheduling/querytTransportationPathButton'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						// var panel = this.ownerCt.ownerCt;
						// panel.querygoodsArea();
						scheduling.adjustTransportationPath.pagingBar
								.moveFirst();
						scheduling.adjustTransportationPath.queryResult.show();
					}
				}]
			}]
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
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : scheduling.realPath('queryNextOrg.action'),
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
/** ************快递目的站的定义***************  end */
//调整运作部门


// model 第三层 流水号 货物信息
Ext.define('Foss.adjustTransportationPath.SerialNoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'serialNo',
						type : 'string'
					}]
		});

// 流水号信息 第三层
Ext.define('Foss.adjustTransportationPath.QuerySerialNoResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adjustTransportationPath.SerialNoModel',
			proxy : {
				type : 'ajax',
				url : scheduling.realPath('queryLevel3.action'),
				reader : {
					type : 'json',
					root : 'schedulingVO.adjustList'
				}
			}
		});

Ext.define('Foss.adjustTransportationPath.SerialNoResultGridPanel', {
	frame : true,
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	sortableColumns: false,
	cls : 'autoHeight',
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	animCollapse : true,
	emptyText: scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	baseCls : 'adjustTransportationPath-serialNoGap',
	selModel : null,
	store : null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.adjustTransportationPath.QuerySerialNoResultStore');
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
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.serialNo'),
				// 关联model中的字段名
				dataIndex : 'serialNo',
				width : 200,
				renderer : function(v, m) {
					return v;
				}
			}]
});

// model 第二层 运单信息
Ext.define('Foss.adjustTransportationPath.WayBillModel', {
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

Ext.define('Foss.adjustTransportationPath.QueryWayBillResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adjustTransportationPath.WayBillModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : scheduling.realPath('queryLevel2.action'),
				reader : {
					type : 'json',
					root : 'schedulingVO.adjustList'
				},			
listeners:{
	exception : function(dataProxy, response, action, options) {
		var result = Ext.decode(response.responseText);
		Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'),  result.message);
    }
}
			}
		});

Ext.define('Foss.adjustTransportationPath.WayBillResultGridPanel', {
	frame : true,
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	sortableColumns: false,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	animCollapse : true,
	emptyText: scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	baseCls : 'adjustTransportationPath-wayBillGap',
	selModel : null,
	// store: null,
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : true,
		rowBodyElement : 'Foss.adjustTransportationPath.SerialNoResultGridPanel'
	}],
	viewConfig : {
		// 处理勾选事件
		getRowClass : function(record, rowIndex, rowParams, store) {
			// var superRecord = this.up('grid').record.get('goodsAreaCode');
			// var goodsAreaCode = record.get('goodsAreaCode');
			if (record.get('ifDisable') == 1) {
				return 'disabledrow';
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.adjustTransportationPath.QueryWayBillResultStore');
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
		//var nextPackageCode = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id").getValue();
		// store =
		// Ext.create('Foss.adjustTransportationPath.QueryWayBillResultStore');
		rowBodyElement.getStore().load({
					params : {
						'schedulingVO.adjustEntity.orgCode' : orgCode,
						'schedulingVO.adjustEntity.goodsAreaCode' : goodsAreaCode
						//'schedulingVO.adjustEntity.packageNextOrgCode' :nextPackageCode
					},
					callback : function(records, operation, success) {
					}
				});
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.waybillNo'),
				// 关联model中的字段名
				dataIndex : 'waybillNo',
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsWeightTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'goodsWeightTotal',
				width : 100,
				flex : 1
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsVolumeTotal'),
				xtype : 'numbercolumn',
				// 关联model中的字段名
				dataIndex : 'goodsVolumeTotal',
				width : 100
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.beforeLine'),
				// 关联model中的字段名
				dataIndex : 'beforeLine',
				xtype : 'ellipsiscolumn',
				width : 250
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.afterLine'),
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

// model 第一层 库区信息
Ext.define('Foss.adjustTransportationPath.goodsAreaModel', {
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
Ext.define('Foss.adjustTransportationPath.QueryResultStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.adjustTransportationPath.goodsAreaModel',
	autoLoad : true,
	pageSize : 10,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryLevel1.action'),
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
        		Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'),  result.message);
            }
        }
	},
	listeners : {
		beforeload : function(store, operation) {
			var queryForm = scheduling.adjustTransportationPath.queryForm
					.getValues();
			if(null==queryForm.orgCode||queryForm.orgCode==""){
				queryForm.orgCode = FossUserContext.getCurrentDeptCode();
			}
			
			var packageValueSelected = '';
			var tempPackName = Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCode_Id").rawValue;
			//包含了快递库区
			var packageObj=Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id");
			if(tempPackName.indexOf("快递库区")<0){
				packageValueSelected = '';
			}
			
			Ext.apply(operation, {
				params : {
					'schedulingVO.adjustEntity.goodsAreaCode' : queryForm.goodsAreaCode,
					'schedulingVO.adjustEntity.orgCode' : queryForm.orgCode,
					'schedulingVO.adjustEntity.packageNextOrgCode' :queryForm.goodsAreaCodeforpackage,
					'schedulingVO.adjustEntity.packageGoodsAreaCode' : goodAreaCodeTemp
				}
			});
		},
		load: function(store, records, successful, epots){
			if(successful == false){
				 Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.goodsAreaError'),'error');
			}else{
				//去调快递货区的货
				for(var i=0;i<records.length;i++)
				{
					var goodsAreaName=records[i].data.goodsAreaName;
					if(goodsAreaName=="快递库区")
					{
						store.remove(records[i]);
					}
					
					
				}
				if(store.getCount() == 0){
					 Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),'error');
				}	
			}
		}
	}
});

Ext.define('Foss.adjustTransportationPath.QueryResult', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	// 定义表格的标题
	title : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryResult'),
	animCollapse : true,
	emptyText: scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.queryEmpty'),
	sortableColumns: false,
	// selModel:null,
	// store: null,
	plugins : [{
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : true,
				pluginId : 'Foss_WayBillResultGridPanel_Id',
				// 行体内容
				rowBodyElement : 'Foss.adjustTransportationPath.WayBillResultGridPanel'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.adjustTransportationPath.QueryResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
					checkOnly : true
				});
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					plugins: Ext.create('Deppon.ux.PageSizePlugin', {
		            limitWarning: 200})
				});
		scheduling.adjustTransportationPath.pagingBar = me.bbar;
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
			text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.pathChange'),
			columnWidth : .1,
			index : 0,
			// 走货路径界面
			adjustTransportPathForm : function(schedulingVO) {
				scheduling.adjustTransportationPath.adjustTransportPathDomList = new Array();
				scheduling.adjustTransportationPath.nowWhichCode = schedulingVO.adjustEntity.orgCode;
				scheduling.adjustTransportationPath.nowWhichName = schedulingVO.adjustEntity.orgName;
				var adjustFormTop = Ext
						.create('Foss.adjustTransportationPath.AdjustFormTop');
				var adjustFormInput = Ext
						.create('Foss.adjustTransportationPath.AdjustFormInput');
				scheduling.adjustTransportationPath.adjustTransportPathDomList
						.push(adjustFormInput);
				var addAreaContainer = Ext
						.create('Foss.adjustTransportationPath.AddAreaContainer');

				var tipMessageAreaContainer = Ext
						.create(
								'Foss.adjustTransportationPath.TipMessageAreaContainer',
								{
									hidden : true
								});
				scheduling.adjustTransportationPath.tipMessageAreaContainer = tipMessageAreaContainer;
				var adjustTransportPath = Ext.create("Ext.Window", {
					title : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.pathChange'),
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
								.setValue(scheduling.adjustTransportationPath.nowWhichName);
						// 默认下一站
						var areaLine = this.schedulingVO.adjustEntity.areaLine;
						var nextOrg = areaLine.substring(areaLine.indexOf("-")
								+ 1);
						var defaultNextStationObject = this.adjustFormInput
								.getForm().findField('defaultNextStation');
						defaultNextStationObject.setValue(nextOrg);
						defaultNextStationObject.setVisible(true);
						scheduling.adjustTransportationPath.defaultNextStation = nextOrg;
						adjustFormInput
								.findObjectiveOrgCode(this.schedulingVO.adjustEntity.orgCode);
					},
					items : [
							tipMessageAreaContainer,
							adjustFormTop,
							adjustFormInput,
							addAreaContainer,
							Ext
									.create('Foss.adjustTransportationPath.BtnAreaPanel')]
				});
				scheduling.adjustTransportationPath.adjustTransportPath = adjustTransportPath;
				adjustTransportPath.show();
				scheduling.adjustTransportationPath.newTransportPath = scheduling.adjustTransportationPath.nowWhichName;
				// 最上面 原路径
				scheduling.adjustTransportationPath.adjustTransportPathList = new Array();
				var originalPathObject = adjustFormTop.getForm()
						.findField('originalPath');
				originalPathObject.setValue(schedulingVO.adjustEntity.areaLine);
				adjustTransportPath.loadData();

			},
			JoinCarForm : function(schedulingVO) {
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
			},
			handler : function() {
				var grid = this.ownerCt.ownerCt;
				var SelectionModel = grid.getSelectionModel();

				var SelectionRecord = SelectionModel.getSelection();
				//校验快递货是否被选
				for(var v=0;v<SelectionRecord.length;v++)
				{
					var val=SelectionRecord[v].data.goodsAreaName;
					if(val=='快递库区')
					{
						Ext.ux.Toast.msg(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), '不能调整快递货走货路径!','error');
						return;
					}
					
				}
				
				
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
					Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.selectPath'),'error');
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
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.goodsAreaName'),
				dataIndex : 'goodsAreaName',
				tdCls : 'adjustTransportationPathQuery',
				width : 150
			}, {
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.areaWaybillQty'),
				xtype : 'numbercolumn',
				format : '0',
				dataIndex : 'areaWaybillQty',
				width : 300
			}, {
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaWeightTotal'),
				dataIndex : 'areaWeightTotal',
				xtype : 'numbercolumn',
				width : 150,
				flex : 1
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaVolumeTotal'),
				// 关联model中的字段名
				dataIndex : 'areaVolumeTotal',
				xtype : 'numbercolumn',
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.areaLine'),
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
			var queryForm = Ext
					.create('Foss.adjustTransportationPath.QueryForm');
			var queryResult = Ext.create(
					'Foss.adjustTransportationPath.QueryResult', {
						id : 'Foss_adjustTransportationPath_QueryGrid_Id'
					});
			scheduling.adjustTransportationPath.queryForm = queryForm;	
			queryForm.getForm().findField('orgCode').setValue(FossUserContext.getCurrentDept().name);
			scheduling.adjustTransportationPath.queryResult = queryResult, Ext
					.create('Ext.panel.Panel', {
								id : 'T_scheduling-adjustTransportationPath_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								items : [queryForm, queryResult],
								renderTo : 'T_scheduling-adjustTransportationPath-body'
							});
			//Ext.getCmp("Foss_adjustTransportationPath_QueryForm_goodsAreaCodeforpackage_Id").setDisabled(true);
		});
