//转换long类型为日期(在model中会用到)
pricing.minFeePlan.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

pricing.minFeePlan.productEntityList = [];// 基础产品列表（页面中叫做运输性质）
pricing.minFeePlan.channel = 'PKP_PRICE_CHANNEL';// 订单渠道
pricing.minFeePlan.tomorrowTime = null;
// --------------------------------------pricing----------------------------------------

// 查询3级产品列表（页面中叫做运输性质）
pricing.searchProductEntityList = function() {
	Ext.Ajax.request({
				url : pricing.realPath('getLevel3Product.action'),// 查询基础产品
				async : false,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					pricing.minFeePlan.productEntityList = [];
					pricing.minFeePlan.productEntityList = result.minFeePlanVo.productEntityList;
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					if (Ext.isEmpty(result)) {
						pricing.showErrorMes(pricing.minFeePlan
								.i18n('i18n.pricingRegion.requestTimeOut'));
					} else {
						pricing.showErrorMes(result.message);
					}
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					if (Ext.isEmpty(result)) {
						pricing.showErrorMes(pricing.minFeePlan
								.i18n('i18n.pricingRegion.requestTimeOut'));
					} else {
						pricing.showErrorMes(result.message);
					}
				}
			});
};
// 获取服务当前时间
pricing.haveServerNowTime = function() {
	Ext.Ajax.request({
		url : pricing.realPath('haveServerNowTime.action'),
		async : false,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);// 获取服务当前时间
			pricing.minFeePlan.tomorrowTime = today.setDate(today.getDate() + 1);
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			if (Ext.isEmpty(result)) {
				pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			} else {
				pricing.showErrorMes(result.message);
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
// 基础产品明细MODEL（页面叫做运输性质）
Ext.define('Foss.pricing.minFeePlan.ProductEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',// id
		type : 'string'
	}, {
		name : 'code',// code
		type : 'string'
	}, {
		name : 'name',// 名称
		type : 'string'
	}, {
		name : 'active',// 是否激活
		type : 'string'
	}, {
		name : 'description',// 描述
		type : 'string'
	}, {
		name : 'transportType',// 运输类型
		type : 'string'
	}, {
		name : 'levels'// 产品等级
	}, {
		name : 'parentCode',// 父产品CODE
		type : 'string'
	}, {
		name : 'refId',
		type : 'string'
	}, {
		name : 'shortName',// 简称
		type : 'string'
	}, {
		name : 'priority',
		type : 'string'
	} ]
});
// 增值服务折扣方案主信息
Ext.define('Foss.pricing.minFeePlan.MarketingEventEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'createDate',// 创建时间
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'createUser',// 创建人工号
		type : 'string'
	}, {
		name : 'modifyDate',// 修改时间
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'modifyUser',// 修改人工号
		type : 'string'
	}, {
		name : 'code',// code
		type : 'string'
	}, {
		name : 'name',// 名称
		type : 'string'
	}, {
		name : 'beginTime',// 开始日期
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'endTime',// 截止日期
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'description',// 备注
		type : 'string'
	}, {
		name : 'createTime',// 创建时间
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'createOrgCode',// 创建
		type : 'string'
	}, {
		name : 'modifyTime',// 修改时间
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'modifyOrgCode',// 修改人所在部门
		type : 'string'
	}, {
		name : 'priceRegionCode',// 价格区域CODE
		type : 'string'
	}, {
		name : 'priceRegionId',// 价格区域ID
		type : 'string'
	}, {
		name : 'active',// 状态
		type : 'string'
	}, {
		name : 'type',// 类型
		type : 'string'
	}, {
		name : 'pricingEntryName',// 计价条目NAME
		type : 'string'
	}, {
		name : 'pricingEntryCode',// 计价条目CODE
		type : 'string'
	}, {
		name : 'pricingEntryId',// 计价条目ID
		type : 'string'
	}, {
		name : 'createUserName',// 创建人姓名
		type : 'string'
	} ]
});
// 自提件最低一票方案MODEL-----
Ext.define('Foss.pricing.minFeePlan.MinFeePlanEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'productCode',
		type : 'string'
	}, {
		name : 'productId',
		type : 'string'
	}, {
		name : 'productName',
		type : 'string'
	}, {
		name : 'channelCode',
		type : 'string'
	}, {
		name : 'channelId',
		type : 'string'
	}, {
		name : 'channelName',
		type : 'string'
	}, {
		name : 'beginTime',
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'endTime',
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'minFee',
		type : 'long',
	}, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'createUserCode',
		type : 'string'
	},{
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'createOrgCode',
		type : 'string'
	}, {
		name : 'modifyUserCode',
		type : 'string'
	},{
		name : 'modifyUserName',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		defaultValue : null,
		convert : pricing.minFeePlan.changeLongToDate
	}, {
		name : 'modifyOrgCode',
		type : 'string'
	}, {
		name : 'versionNo',
		type : 'long'
	}, {
		name : 'active',
		type : 'string'
	} ]
});
// 订单渠道Entity-----
Ext.define('Foss.pricing.minFeePlan.ChannelEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'valueCode',
		type : 'string'
	}, {
		name : 'valueName',
		type : 'string'
	} ]
});
// ------------------------------------model---------------------------------------------------
/**
 * 自提件最低一票方案Store by lufeifei
 */
Ext.define('Foss.pricing.minFeePlan.minFeePlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.minFeePlan.MinFeePlanEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('selectMinFeePlanList.action'),
		reader : {
			type : 'json',
			root : 'minFeePlanVo.minFeePlanEntityList',
			totalProperty : 'totalCount'
		}
	}
});
// ----------------------------------------store---------------------------------
/**
 * 自提件最低一票方案查询表单
 */
Ext.define('Foss.pricing.minFeePlan.QueryMinFeePlanForm',{
	extend : 'Ext.form.Panel',
	title : pricing.minFeePlan.i18n('i18n.pricingRegion.searchCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults : {
		columnWidth : .3,
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 140,
	defaultType : 'textfield',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
				{
					name : 'name',
					labelWidth : 120,
					fieldLabel : pricing.minFeePlan
							.i18n('foss.pricing.mentionLowestVotePlan'),// 自提最低一票方案
					xtype : 'textfield'
				},{
					name : 'active',
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					editable : false,
					value : 'ALL',
					store : pricing.getStore(
									null,
									null,
									[ 'valueCode', 'valueName' ],
									[
											{
												'valueCode' : 'Y',
												'valueName' : pricing.minFeePlan
														.i18n('i18n.pricingRegion.active')
											},
											{
												'valueCode' : 'N',
												'valueName' : pricing.minFeePlan
														.i18n('i18n.pricingRegion.unActive')
											},
											{
												'valueCode' : 'ALL',
												'valueName' : pricing.minFeePlan
														.i18n('i18n.pricingRegion.all')
											} ]),
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.status'),// 状态
					xtype : 'combo'
				} ];
		me.fbar = [
				{
					xtype : 'button',
					width : 70,
					text : pricing.minFeePlan
							.i18n('foss.pricing.reset'),// 重置
					handler : function() {
						me.getForm().reset();
					}
				},
				{
					xtype : 'button',
					width : 70,
					cls : 'yellow_button',
					margin : '0 0 0 820',
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.search'),
					disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanQuerybutton'),
					hidden : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanQuerybutton'),
					handler : function() {
						if (me.getForm().isValid()) {
							var grid = Ext.getCmp('T_pricing-indexMinFeePlan_content').getAreaGrid();// 获取大查询GRID
							grid.getStore().load();
						}
					}
				} ]
		me.callParent([ cfg ]);
	}
});
/**
 * 自提件最低一票方案查询列表Form
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanGridPanel',{
	extend : 'Ext.grid.Panel',
	title : pricing.minFeePlan.i18n('i18n.pricingRegion.searchResults'),// 查询结果
	frame : true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : pricing.minFeePlan.i18n('foss.pricing.theQueryResultIsEmpty'),// 查询结果为空
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create(
					'Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
	// 删除最低一票方案
	deleteMinFeePlan : function() {
		var me = this;
		var minFeePlanIds = new Array();
		// 获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if (selections.length < 1) {
			pricing.showErrorMes("请选择要删除的最低一票方案！");
			return;
		}
		for ( var i = 0; i < selections.length; i++) {
			if (selections[i].get('active') == 'Y') {// 只有未激活的增值优惠才可以删除
				pricing.showErrorMes("只有未激活的最低一票方案才可以删除！");
				return;
			} else if (selections[i].get('active') == 'N') {
				minFeePlanIds.push(selections[i].get('id'));
			} else {
				minFeePlanIds.push(selections[i].get('id'));
			}
		}
		if (minFeePlanIds.length < 1) {
			pricing.showErrorMes("请至少选择一条未激活的最低一票方案！");
			return;
		}
		pricing.showQuestionMes('是否要删除这些未激活的方案？', function(e) {
			if (e == 'yes') {// 询问是否删除，是则发送请求
				var params = {
					'minFeePlanVo' : {
						'minFeePlanIds' : minFeePlanIds
					}
				};
				var successFun = function(json) {
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json) {
					if (Ext.isEmpty(json)) {
						pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
					} else {
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteMinFeePlan.action');
				pricing.requestJsonAjax(url,params, successFun,failureFun);
			}
		});

	},
	// 自提件最低一票新增Window
	minFeePlanAddWindow : null,
	getMinFeePlanAddWindow : function() {
		if (Ext.isEmpty(this.minFeePlanAddWindow)) {
			this.minFeePlanAddWindow = Ext.create('Foss.pricing.minFeePlan.MinFeePlanAddWindow');
			this.minFeePlanAddWindow.parent = this;
		}
		return this.minFeePlanAddWindow;
	},
	// 自提件最低一票修改Window
	minFeePlanUpdateWindow : null,
	getMinFeePlanUpdateWindow : function() {		
		if (Ext.isEmpty(this.minFeePlanUpdateWindow)) {
			this.minFeePlanUpdateWindow = Ext.create('Foss.pricing.minFeePlan.MinFeePlanUpdateWindow');
			this.minFeePlanUpdateWindow.parent = this;
		}
		return this.minFeePlanUpdateWindow;
	},
	// 查看详情window
	minFeePlanDeatilShowWindow : null,
	getMinFeePlanDeatilShowWindow : function() {
		if (Ext.isEmpty(this.minFeePlanDeatilShowWindow)) {
			this.minFeePlanDeatilShowWindow = Ext.create('Foss.pricing.minFeePlan.MinFeePlanDeatilShowWindow');
			this.minFeePlanDeatilShowWindow.parent = this;
		}
		this.minFeePlanDeatilShowWindow.minFeePlanShowTab.items.items[0].items.items[4]
				.setDisabled(true);
		return this.minFeePlanDeatilShowWindow;
	},
//	/**
//	 * 
//	 * 立即生效
//	 * 
//	 * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
//	 * 
//	 * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
//	 * 
//	 * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
//	 * 
//	 */
	immediatelyActiveWindow : null,
	getImmediatelyActiveWindow : function() {
		if (Ext.isEmpty(this.immediatelyActiveWindow)) {
			this.immediatelyActiveWindow = Ext
					.create('Foss.pricing.minFeePlan.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	/**
	 * 立即中止 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
	 * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
	 * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
	 * 
	 */
	immediatelyStopWindow : null,
	getImmediatelyStopWindow : function() {
		if (Ext.isEmpty(this.immediatelyStopWindow)) {
			this.immediatelyStopWindow = Ext.create('Foss.pricing.minFeePlan.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	/**
	 * 立即中止
	 */
	immediatelyStop : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length < 1) {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneRecordToOp'));
			return;
		}
		if (selections.length > 1) {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneRecordToOp'));
			return;
		}
		if (selections[0].get('active') != 'Y') {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneActiveRecordToOp'));
			return;
		} else {
			var minFeePlanEntity = selections[0].data;
			me.getImmediatelyStopWindow().minFeePlanEntity = minFeePlanEntity;
			me.getImmediatelyStopWindow().show();
		}
	},
	/**
	 * 立即激活
	 */
	immediatelyActive : function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length < 1) {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneRecordToOp'));
			return;
		}
		if (selections.length > 1) {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneRecordToOp'));
			return;
		}
		if (selections[0].get('active') == 'Y') {
			pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.selectOneUnActiveRecordToOp'));
			return;
		} else {
			var minFeePlanEntity = selections[0].data;
			me.getImmediatelyActiveWindow().minFeePlanEntity = minFeePlanEntity;
			me.getImmediatelyActiveWindow().show();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [
				{
					xtype : 'rownumberer',
					width : 40,
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.num')// 序号
				},
				{
					align : 'center',
					xtype : 'actioncolumn',
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.opra'),// 操作
					items : [
							{
								iconCls : 'deppon_icons_edit',
								tooltip : pricing.minFeePlan.i18n('foss.pricing.update'),// 修改
								disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanUpdatebutton'),
								width : 42,
								getClass : function(v, m, r,rowIndex) {
									if (r.get('active') === 'N') {
										return 'deppon_icons_edit';
									} else {
										return 'statementBill_hide';
									}
								},
								handler : function(grid, rowIndex, colIndex) {
									var record = grid.getStore().getAt(rowIndex);
									var params = {'minFeePlanVo' : {'minFeePlanEntity' : {'id' : record.get('id')}}};
									var successFun = function(json) {
										var minFeePlanEntity = json.minFeePlanVo.minFeePlanEntity;
										grid.up().getMinFeePlanUpdateWindow().minFeePlanEntity = minFeePlanEntity;// 设置最低一票主信息
										grid.up().getMinFeePlanUpdateWindow().show();// 显示window
									};
									var failureFun = function(json) {
										if (Ext.isEmpty(json)) {
											pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
										} else {
											pricing.showErrorMes(json.message);
										}
									};
									var url = pricing.realPath('selectMinFeePlanByPrimaryKey.action');
									pricing.requestJsonAjax(url, params,successFun,failureFun);
								}
							},
							/*
							 * 暂时没有升级
							{
								iconCls : 'deppon_icons_softwareUpgrade',
								tooltip : pricing.minFeePlan.i18n('foss.pricing.upgradedVersion'),// 升级版本
								disabled : !pricing.minFeePlan.isPermission('minFeePlan/valueAddDiscountUpgradebutton'),
								width : 42,
								getClass : function(v, m, r,rowIndex) {
									if (r.get('active') === 'Y') {
										return 'deppon_icons_softwareUpgrade';
									} else {
										return 'statementBill_hide';
									}
								},
								handler : function(grid,rowIndex, colIndex) {
									var record = grid.getStore().getAt(rowIndex);
									var params = {'priceDiscountVo' : {'marketingEventEntity' : {'id' : record.get('id')}}};
									var successFun = function(json) {
										pricing.showInfoMes(json.message);
										grid.up().getPagingToolbar().moveFirst();
									};
									var failureFun = function(json) {
										if (Ext.isEmpty(json)) {
											pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
										} else {
											pricing.showErrorMes(json.message);
										}
									};
									var url = pricing.realPath('copyValueAddDiscountProgram.action');
									pricing.requestJsonAjax(url, params,successFun,failureFun);
								}
							},*/
							{
								iconCls : 'deppon_icons_showdetail',
								tooltip : pricing.minFeePlan.i18n('foss.pricing.details'),// 查看详情
								disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanDetailbutton'),
								width : 42,
								handler : function(grid,rowIndex, colIndex) {
									var record = grid.getStore().getAt(rowIndex);
									var params = {'minFeePlanVo' : {'minFeePlanEntity' : {'id' : record.get('id')}}};
									var successFun = function(json) {
										var minFeePlanEntity = json.minFeePlanVo.minFeePlanEntity;
										grid.up().getMinFeePlanDeatilShowWindow().minFeePlanEntity = minFeePlanEntity;
										grid.up().getMinFeePlanDeatilShowWindow().show();// 显示window
									};
									var failureFun = function(json) {
										if (Ext.isEmpty(json)) {
											pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
										} else {
											pricing.showErrorMes(json.message);
										}
									};
									var url = pricing.realPath('selectMinFeePlanByPrimaryKey.action');
									pricing.requestJsonAjax(url, params,successFun,failureFun);
								}
							} ]
				},{
					text : pricing.minFeePlan
							.i18n('foss.pricing.coding'),// 编码
					dataIndex : 'code'
				},{
					text : pricing.minFeePlan
							.i18n('foss.pricing.scenarioName'),// 方案名称
					dataIndex : 'name'
				},
//				{
//					text : pricing.minFeePlan
//							.i18n('foss.pricing.orderChannels'),// 渠道name
//					dataIndex : 'channelName'
//				},
//				{
//					text : pricing.minFeePlan
//							.i18n('foss.pricing.productName'),// 产品name
//					dataIndex : 'productName'
//				},
				{
					text : pricing.minFeePlan
							.i18n('foss.pricing.mentionLowestVoteFee'),// 最低一 票运费
					dataIndex : 'minFee'
				},{
					text : pricing.minFeePlan
							.i18n('foss.pricing.status'),// 状态
					dataIndex : 'active',
					width : 50,
					renderer : function(value) {
						if (value == 'Y') {// 'Y'表示激活
							return pricing.minFeePlan.i18n('i18n.pricingRegion.active');
						} else if (value == 'N') {// 'N'表示未激活
							return pricing.minFeePlan.i18n('i18n.pricingRegion.unActive');
						} else {
							return '';
						}
					}
				},{
					text : pricing.minFeePlan
							.i18n('foss.pricing.qishiTime'),// 生效时间
					dataIndex : 'beginTime',
					renderer : function(value) {
						return Ext.Date.format(new Date(value),
								'Y-m-d H:i:s');
					}
				},{
					text : pricing.minFeePlan
							.i18n('foss.pricing.theCutOffTime'),// 失效时间
					dataIndex : 'endTime',
					renderer : function(value) {
						return Ext.Date.format(new Date(value),
								'Y-m-d H:i:s');
					}
				},{
					text : pricing.minFeePlan.i18n('foss.pricing.createUser'),//创建人
					dataIndex : 'createUserName'
				},{ 
					text:pricing.minFeePlan.i18n('foss.pricing.createTime'),//创建时间
					dataIndex : 'createTime',
					renderer:function(value){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s'); 
					} 					
				},{ 
					text : pricing.minFeePlan.i18n('foss.pricing.modifyUser'),//修改人
					dataIndex : 'modifyUserName'
				},{
					text :pricing.minFeePlan.i18n('foss.pricing.updateTime'),//修改时间
					dataIndex : 'modifyTime',
					renderer:function(value){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					}
				}];
		me.store = Ext.create('Foss.pricing.minFeePlan.minFeePlanStore',{
			autoLoad : false,
			pageSize : 20,
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = me.up().getQueryMinFeePlanForm();
					if (queryForm != null) {
						Ext.apply(operation, {
								params : {
									'minFeePlanVo.minFeePlanEntity.active' : queryForm.getForm().findField('active').getValue(),// 状态
									'minFeePlanVo.minFeePlanEntity.name' : queryForm.getForm().findField('name').getValue()	// 方案名称
								}
						});
					}
				}
			}
		});
		me.listeners = {// 消除出现滚动条之后，却不能用的BUG
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create(
				'Ext.selection.CheckboxModel', {// 带选择框
					mode : 'MULTI',
					checkOnly : true
				});
		// 添加头部按钮
		me.tbar = [
				{
					// 新增
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.add'),
					disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanAddbutton'),
					hidden : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanAddbutton'),
					handler : function() {
						me.getMinFeePlanAddWindow().show();
					}
				},
				'-',
				{
					// 删除
					text : pricing.minFeePlan.i18n('foss.pricing.delete'),
					disabled : !pricing.minFeePlan
					.isPermission('minFeePlan/minFeePlanDeletebutton'),
					hidden : !pricing.minFeePlan
							.isPermission('minFeePlan/minFeePlanDeletebutton'),
					handler : function() {
						me.deleteMinFeePlan();
					}
				},
				'-',
				{
					// '立即激活',
					text : pricing.minFeePlan.i18n('foss.pricing.immediatelyActivationProgram'),
					disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanImmediatelyActivebutton'),
					hidden : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanImmediatelyActivebutton'),
					handler : function() {
						me.immediatelyActive();
					}
				},
				'-',
				{
					// '立即中止',
					text : pricing.minFeePlan.i18n('foss.pricing.immediatelyStopProgram'),
					disabled : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanImmediatelyStopbutton'),
					hidden : !pricing.minFeePlan.isPermission('minFeePlan/minFeePlanImmediatelyStopbutton'),
					handler : function() {
						me.immediatelyStop();
					}
				} ];
		me.bbar = me.getPagingToolbar();
		me.callParent([ cfg ]);
	}
});

/**
 * @description 自提价最低一票方案主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-indexMinFeePlan_content')) {
		return;
	}
	pricing.searchProductEntityList();// 产品类型 or 运输性质
	pricing.haveServerNowTime();
	var queryMinFeePlanForm = Ext.create('Foss.pricing.minFeePlan.QueryMinFeePlanForm');// 查询条件
	var minFeePlanGridPanel = Ext.create('Foss.pricing.minFeePlan.MinFeePlanGridPanel');// 查询结果
	Ext.getCmp('T_pricing-indexMinFeePlan').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-indexMinFeePlan_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		// 获得查询FORM
		getQueryMinFeePlanForm : function() {
			return queryMinFeePlanForm;
		},
		// 获得查询结果GRID
		getAreaGrid : function() {
			return minFeePlanGridPanel;
		},
		items : [ queryMinFeePlanForm, minFeePlanGridPanel ]
	}));
});
/**
 * 自提件最低一票方案-新增窗口
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanAddWindow',{
	extend : 'Ext.window.Window',
	title : '自提件最低一票方案定义',// pricing.minFeePlan.i18n('foss.pricing.definitionOfPriceAppreciationDiscountProgram'),//价格增值服务折扣方案定义
	closable : true,
	modal : true,
	resizable : false,
	parent : null,
	closeAction : 'hide',
	width : 590,
	height : 480,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) {
			me.getValueAddDiscountEditFormPanel().getForm()
					.reset();
		},
		beforeshow : function(me) {

		}
	},
	// 最低一票主信息FORM
	valueAddDiscountEditFormPanel : null,
	getValueAddDiscountEditFormPanel : function() {
		if (Ext.isEmpty(this.valueAddDiscountEditFormPanel)) {
			this.valueAddDiscountEditFormPanel = Ext
					.create(
							'Foss.pricing.minFeePlan.MinFeePlanEditFormPanel',
							{
								isShow : false
							});
		}
		return this.valueAddDiscountEditFormPanel;
	},
	// 提交最低一票方案主信息
	commintValueAddDiscount : function() {
		var me = this;
		var form = me.getValueAddDiscountEditFormPanel();
		if (form.getForm().isValid()) {// 校验form是否通过校验
			var minFeePlanEntity = new Foss.pricing.minFeePlan.MinFeePlanEntity();// 自提件最低一票方案主信息
			form.getForm().updateRecord(minFeePlanEntity);// 将FORM中数据设置到MODEL里面
			if (minFeePlanEntity.get('beginTime').getTime() >= minFeePlanEntity
					.get('endTime')) {
				pricing
						.showWoringMessage(pricing.minFeePlan
								.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));// 终止日期需大于起始日期！
				return;
			}
			var params = {
				'minFeePlanVo' : {
					'minFeePlanEntity' : minFeePlanEntity.data
				}
			};// 自提件最低一票新增数据
			var successFun = function(json) {
				pricing.showInfoMes(json.message);// 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					pricing
							.showErrorMes(pricing.minFeePlan
									.i18n('foss.pricing.requestTimedOut'));// 请求超时
				} else {
					pricing.showErrorMes(json.message);// 提示失败原因
				}
			};
			var url = pricing.realPath('addMinFeePlan.action');// 请求折扣新增
			pricing.requestJsonAjax(url, params, successFun,
					failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getValueAddDiscountEditFormPanel() ];
		me.fbar = [
				{
					text : pricing.minFeePlan
							.i18n('i18n.pricingRegion.returnGrid'),
					handler : function() {
						me.close();
					}
				},
				{
					text : pricing.minFeePlan
							.i18n('i18n.pricingRegion.commit'),
					cls : 'yellow_button',
					margin : '0 0 0 360',
					handler : function() {
						me.commintValueAddDiscount();
					}
				} ];
		me.callParent([ cfg ]);
	}
});
/**
 * 最低一票方案-修改
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '最低一票方案定义',
	closable : true,
	modal : true,
	resizable : false,
	minFeePlanEntity : null,
	parent : null,
	closeAction : 'hide',
	width : 590,
	height : 480,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) {
			me.getMinFeePlanEditFormPanel().getForm().reset();
		},
		beforeshow : function(me) {
			var form = me.getMinFeePlanEditFormPanel();
			if (!Ext.isEmpty(me.minFeePlanEntity)) {
				var minFeePlanEntityModel = new Foss.pricing.minFeePlan.MinFeePlanEntity(me.minFeePlanEntity);
				form.getForm().loadRecord(minFeePlanEntityModel);
			} else {
				pricing.showErrorMes('没有最低一票方案主信息！');
				return;
			}
		}
	},
	// 最低一票方案修改信息FORM
	minFeePlanEditFormPanel : null,
	getMinFeePlanEditFormPanel : function() {
		if (Ext.isEmpty(this.minFeePlanEditFormPanel)) {
			this.minFeePlanEditFormPanel = Ext.create('Foss.pricing.minFeePlan.MinFeePlanEditFormPanel',{
								isShow : false
							});
		}
		return this.minFeePlanEditFormPanel;
	},
	// 提交最低一票修改后信息
	commintMinFeePlan : function() {
		var me = this;
		var form = me.getMinFeePlanEditFormPanel();
		if (form.getForm().isValid()) {// 校验form是否通过校验		
			// 增值服务折扣方案主信息
			var minFeePlanModel = new Foss.pricing.minFeePlan.MinFeePlanEntity(me.minFeePlanEntity);// 最低一票方案主信息
			form.getForm().updateRecord(minFeePlanModel);// 将FORM中数据设置到MODEL里面
			if (minFeePlanModel.get('beginTime').getTime() >= minFeePlanModel.get('endTime')) {
				pricing.showWoringMessage(pricing.minFeePlan.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));// 终止日期需大于起始日期！
				return;
			}			
			var params = {
				'minFeePlanVo' : {
					'minFeePlanEntity' : minFeePlanModel.data
				}
			};
			var successFun = function(json) {
				pricing.showInfoMes(json.message);// 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					pricing.showErrorMes(pricing.minFeePlan.i18n('foss.pricing.requestTimedOut'));// 请求超时
				} else {
					pricing.showErrorMes(json.message);// 提示失败原因
				}
			};
			var url = pricing.realPath('updateMinFeePlan.action');// 请求折扣修改
			pricing.requestJsonAjax(url, params, successFun,failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getMinFeePlanEditFormPanel() ];
		me.fbar = [
				{
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.returnGrid'),// 返回
					handler : function() {
						me.close();
					}
				},
				{
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.commit'),// 提交
					cls : 'yellow_button',
					margin : '0 0 0 285',
					handler : function() {
						me.commintMinFeePlan();
					}
				} ];
		me.callParent([ cfg ]);
	}
});
/**
 * 最低一票方案明细-查看
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanDeatilShowWindow', {
	extend : 'Ext.window.Window',
//	title : pricing.minFeePlan.i18n('foss.pricing.valueAddedServicesDiscountProgram'),
	title : '最低一票方案明细',
	resizable : false,
	minFeePlanEntity : null,
	parent : null,
	closeAction : 'hide',
	width : 650,
	height : 500,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function(me) {
			me.getMinFeePlanShowTab().getMinFeePlanEditFormPanel().getForm().reset();
		},
		beforeshow : function(me) {
			var form = me.getMinFeePlanShowTab().getMinFeePlanEditFormPanel();
			if (!Ext.isEmpty(me.minFeePlanEntity)) {
				form.getForm().loadRecord(new Foss.pricing.minFeePlan.MinFeePlanEntity(me.minFeePlanEntity));// 加载数据
			} else {
				pricing.showErrorMes('没有最低一票方案信息');
				return;
			}
		}
	},
	// 最低一票方案明细新增
	minFeePlanShowTab : null,
	getMinFeePlanShowTab : function() {
		if (Ext.isEmpty(this.minFeePlanShowTab)) {
			this.minFeePlanShowTab = Ext.create('Foss.pricing.minFeePlan.MinFeePlanShowTab');
		}
		return this.minFeePlanShowTab;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getMinFeePlanShowTab() ];
		me.fbar = [ {
			text : pricing.minFeePlan.i18n('i18n.pricingRegion.returnGrid'),
			handler : function() {
				me.close();
			}
		} ];
		me.callParent([ cfg ]);
	}
});

/**
 * 查看最低一票方案-TAB
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanShowTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
	flex : 1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	// 最低一票方案主信息FORM
	minFeePlanEditFormPanel : null,
	getMinFeePlanEditFormPanel : function() {
		if (Ext.isEmpty(this.minFeePlanEditFormPanel)) {
			this.minFeePlanEditFormPanel = Ext.create('Foss.pricing.minFeePlan.MinFeePlanEditFormPanel', {
						isShow : true
					});
			this.minFeePlanEditFormPanel.getForm().getFields().each(
					function(item) {
						item.setReadOnly(true);
					});
		}
		return this.minFeePlanEditFormPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getMinFeePlanEditFormPanel() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 自提件最低一票方案主信息
 */
Ext.define('Foss.pricing.minFeePlan.MinFeePlanEditFormPanel',{
	extend : 'Ext.form.Panel',
	frame : true,
	title : ' 最低一票方案',
	flex : 1,
	collapsible : true,
	defaults : {
		colspan : 2,
		labelWidth : 110,
		allowBlank : false,
		margin : '5 5 5 5'
	},
	layout : {
		type : 'table',
		columns : 2
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.minFeePlan.channel);
		var minValueDate = null;
		if (!config.isShow) {
			minValueDate = new Date(pricing.minFeePlan.tomorrowTime);
		}
		me.items = [
				{
					name : 'code',
					fieldLabel : '<span style="color:red">*</span>编码',// 编码
					readOnly : true,
					allowBlank : true,
					emptyText : pricing.minFeePlan.i18n('foss.pricing.automaticallyGeneratedCoding'),// 自动生成编码
					xtype : 'textfield'
				},
				{
					name : 'name',
					maxLength : 20,
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.scenarioName'),// 方案名称
					xtype : 'textfield'
				},
				{
					name : 'channelCode',
					queryMode : 'local',
					displayField : 'valueName',
					allowBlank : false,
					valueField : 'valueCode',
					editable : false,
					channelRecord : null,// 渠道实体
					store : channel,
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.orderChannels'),// 订单渠道
					xtype : 'combo',
					listeners : {
						change : function(comb, newValue,oldvalue) {
							if (!Ext.isEmpty(newValue)) {
								comb.channelRecord = comb.getStore().getById(newValue);
							} else {
								comb.channelRecord = null;
							}

						}
					}
				},
				{
					name : 'productId',
					queryMode : 'local',
					displayField : 'name',
					allowBlank : false,
					valueField : 'id',
					editable : false,
					productRecord : null,// 基础产品实体
					store : pricing.getStore(
									null,
									'Foss.pricing.minFeePlan.ProductEntity',
									null,
									pricing.minFeePlan.productEntityList),
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.productDefinition'),// 产品定义
					xtype : 'combo',
					listeners : {
						change : function(comb, newValue,oldvalue) {
							if (!Ext.isEmpty(newValue)) {
								comb.productRecord = comb.getStore().getById(newValue);
							} else {
								comb.productRecord = null;
							}

						}
					}
				},
				{
					name : 'beginTime',
					colspan : 1,
					minValue : minValueDate,
					format : 'Y-m-d H:i:s',
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.effectiveTime'),// 生效时间
					xtype : 'datefield'
				},
				{
					name : 'endTime',
					colspan : 1,
					labelWidth : 20,
					minValue : minValueDate,
					format : 'Y-m-d H:i:s',
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.to'),// 至
					xtype : 'datefield',
					value : '01/01/2999'
				},
				{
					name : 'minFee',
					fieldLabel : '自提件最低一票费用',// 自提件最低一票费用
					xtype : 'numberfield',
					allowBlank:false,
					decimalPrecision:0,
					step:1,
				    maxValue: 99999999,
				    minValue:0,
				},
				{
					name : 'remark',
					width : 400,
					maxLength : 200,
					allowBlank : true,
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.mentionLowestVotePlanDescription'),// 自提最低一票方案说明
					xtype : 'textareafield'
				} ];
		me.callParent([ cfg ]);
	}
});
/**
 * 立即中止最低一票方案 Window
 */
Ext.define('Foss.pricing.minFeePlan.ImmediatelyStopEndTimeWindow', {
	extend : 'Ext.window.Window',
	title : pricing.minFeePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),// "立即中止方案",
	width : 380,
	height : 152,
	closeAction : 'hide',
	minFeePlanEntity : null,
	stopFormPanel : null,
	parent : null,
	getStopFormPanel : function() {
		if (Ext.isEmpty(this.stopFormPanel)) {
			this.stopFormPanel = Ext.create('Foss.pricing.minFeePlan.ImmediatelyStopFormPanel');
		}
		return this.stopFormPanel;
	},
	listeners : {
		beforeshow : function(me) {
			var showbeginTime = Ext.Date.format(new Date(me.minFeePlanEntity.beginTime), 'Y-m-d');
			var showendTime = Ext.Date.format(new Date(me.minFeePlanEntity.endTime), 'Y-m-d');
			var value = pricing.minFeePlan.i18n('foss.pricing.showleftTimeInfo')
					+ showbeginTime
					+ pricing.minFeePlan.i18n('foss.pricing.showmiddleTimeInfo')
					+ showendTime
					+ pricing.minFeePlan.i18n('foss.pricing.showrightEndTimeInfo');
			me.getStopFormPanel().down('displayfield').setValue(value);
		},
		beforehide : function(me) {
			me.getStopFormPanel().getForm().reset();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getStopFormPanel() ];
		me.callParent(cfg);
	}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.minFeePlan.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout : 'column',
	stop : function() {
		var me = this;
		var form = me.getForm();
		if (form.isValid()) {
			var minFeePlanEntity = new Foss.pricing.minFeePlan.MinFeePlanEntity();
			form.updateRecord(minFeePlanEntity);
			minFeePlanEntity.set('endTime', Ext.Date.parse(form.findField('endTime').getValue(),'Y-m-d H:i:s'));
			var id = me.up('window').minFeePlanEntity.id;
			minFeePlanEntity.set('id', id);
			var params = {'minFeePlanVo' : {'minFeePlanEntity' : minFeePlanEntity.data}};
			var url = pricing.realPath('stopImmediatelyMinFeePlan.action');
			var successFun = function(json) {
				pricing.showInfoMes(json.message);
				me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
				} else {
					pricing.showErrorMes(json.message);
				}
			};
			pricing.requestJsonAjax(url, params, successFun,failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
				{
					width : 280,
					xtype : 'displayfield',
					columnWidth : .9,
					value : ''
				},
				{
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.suspendTime'),// '中止日期',
					name : 'endTime',
					xtype : 'datetimefield_date97',
					editable : false,
					time : true,
					id : 'Foss_valueAddDiscount_stopEndTime_ID',
					dateConfig : {el : 'Foss_valueAddDiscount_stopEndTime_ID-inputEl'},
					allowBlank : false,
					columnWidth : .9
				},
				{
					xtype : 'container',
					columnWidth : .6,
					html : '&nbsp;'
				},
				{
					xtype : 'button',
					width : 70,
					columnWidth : .15,
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.determine'),// "确认",
					handler : function() {
						me.stop();
					}
				},
				{
					xtype : 'button',
					width : 70,
					columnWidth : .15,
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.cancel'),// "取消",
					handler : function() {
						me.up('window').hide();
					}
				} ];
		me.callParent(cfg);
	}
});

/**
 * 立即激活最低一票方案Window
 */
Ext.define('Foss.pricing.minFeePlan.ImmediatelyActiveTimeWindow',{
	extend : 'Ext.window.Window',
	title : pricing.minFeePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),// "立即激活方案",
	width : 380,
	height : 152,
	minFeePlanEntity : null,
	closeAction : 'hide',
	immediatelyActiveFormPanel : null,
	getImmediatelyActiveFormPanel : function() {
		if (Ext.isEmpty(this.immediatelyActiveFormPanel)) {
			this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.minFeePlan.ImmediatelyActiveFormPanel');
		}
		return this.immediatelyActiveFormPanel;
	},
	listeners : {
		beforeshow : function(me) {
			var showbeginTime = Ext.Date.format(new Date(me.minFeePlanEntity.beginTime), 'Y-m-d');
			var showendTime = Ext.Date.format(new Date(me.minFeePlanEntity.endTime), 'Y-m-d');
			var value = pricing.minFeePlan.i18n('foss.pricing.showleftTimeInfo')
					+ showbeginTime
					+ pricing.minFeePlan.i18n('foss.pricing.showmiddleTimeInfo')
					+ showendTime
					+ pricing.minFeePlan.i18n('foss.pricing.showrightEndTimeInfo');
			me.getImmediatelyActiveFormPanel().down('displayfield').setValue(value);
		},
		beforehide : function(me) {
			me.getImmediatelyActiveFormPanel().getForm().reset();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getImmediatelyActiveFormPanel() ];
		me.callParent(cfg);
	}
});

/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.minFeePlan.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout : 'column',
	activetion : function() {
		var me = this;
		var form = me.getForm();
		if (form.isValid()) {
			var minFeePlanEntity = new Foss.pricing.minFeePlan.MinFeePlanEntity();
			form.updateRecord(minFeePlanEntity);
			var id = me.up('window').minFeePlanEntity.id;
			minFeePlanEntity.set('id', id);
			minFeePlanEntity.set('beginTime', Ext.Date.parse(form.findField('beginTime').getValue(),'Y-m-d H:i:s'));
			var params = {'minFeePlanVo' : {'minFeePlanEntity' : minFeePlanEntity.data}};
			var url = pricing.realPath('activateImmediatelyMinFeePlan.action');
			var successFun = function(json) {
				pricing.showInfoMes(json.message);
				me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					pricing.showErrorMes(pricing.minFeePlan.i18n('i18n.pricingRegion.requestTimeOut'));
				} else {
					pricing.showErrorMes(json.message);
				}
			};
			pricing.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
				{
					width : 280,
					xtype : 'displayfield',
					columnWidth : .9,
					value : ''
				},
				{
					fieldLabel : pricing.minFeePlan.i18n('foss.pricing.availabilityDate'),// '生效日期',
					name : 'beginTime',
					xtype : 'datetimefield_date97',
					editable : false,
					time : true,
					allowBlank : false,
					id : 'Foss_valueAddDiscount_activeEndTime_ID',
					dateConfig : {
						el : 'Foss_valueAddDiscount_activeEndTime_ID-inputEl'
					},
					columnWidth : .9
				},
				{
					xtype : 'container',
					columnWidth : .6,
					html : '&nbsp;'
				},
				{
					xtype : 'button',
					width : 70,
					columnWidth : .15,
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.determine'),// ,"确认",
					handler : function() {
						me.activetion();
					}
				},
				{
					xtype : 'button',
					width : 70,
					columnWidth : .15,
					text : pricing.minFeePlan.i18n('i18n.pricingRegion.cancel'),// "取消",
					handler : function() {
						me.up('window').hide();
					}
				} ];
		this.callParent(cfg);
	}
});