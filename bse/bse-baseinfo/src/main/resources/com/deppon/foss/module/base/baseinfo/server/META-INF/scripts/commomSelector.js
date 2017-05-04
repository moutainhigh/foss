﻿﻿//下拉单选框
Ext.define('Foss.commonSelector.CommonCombSelector', {
	extend : 'Deppon.commonselector.DynamicComboSelector',
	minChars : 0,
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	listWidth : 200,// 设置下拉框宽度
	active : null,
	myActive : 'Y',
	record : null,
	displayField : null,
	valueField : null, 
	displayField : null,// 显示名称
	valueField : null,// 值
	queryParam : null,// 查询参数
	setCombValue : function(displayText, valueText) {
		var me = this, key = me.displayField + '', value = me.valueField
				+ '';
		var m = Ext.create(me.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		me.store.loadRecords([m]);
		me.setValue(valueText);
	},
	getBlur:function(ths, the, eOpts){
		if (Ext.isEmpty(ths.value)) {
			ths.setRawValue(null);
		}
	},
	getBeforeLoad : function(st, operation, e) {
		var me = this;
		var me = this, searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
				params : searchParams
			});
		}
		var prefix = me.queryParam.substring(0, me.queryParam
						.lastIndexOf('.'))
				+ '.';
		var param = prefix + me.myQueryParam;
		if (!Ext.isEmpty(me.myQueryParam)) {
			searchParams[param] = me.getRawValue();
			searchParams[me.queryParam] = null;
		} else {
			searchParams[me.queryParam] = me.rawValue;
			if(!Ext.isEmpty(me.myQueryParam)){
				searchParams[param] = null;
			}
		}
		var activeParam = prefix + 'active';
		var act = Ext.isEmpty(me.active) ? me.myActive : me.active;
		searchParams[activeParam] = act;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.active = config.active; 
		me.store.addListener('select', function(comb, records, obs) {
			me.record = records[0];
		});
		me.callParent([cfg]);
		me.on('blur',me.getBlur,me); 
	},
	getRecord : function() {
		var me = this;
		return me.record;
	}
});
//下拉多选框
Ext.define('Foss.commonSelector.CommonMultiCombSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	minChars : 0,
	isPaging : true,// 分页 
	listWidth : 200,// 设置下拉框宽度
	active : null,
	myActive : 'Y',
	record : null,
	displayField : null,
	valueField : null,
	displayField : null,// 显示名称
	valueField : null,// 值
	queryParam : null,// 查询参数
	triggerAction: 'query',
	setCombValue : function(displayText, valueText) {
		var me = this, key = me.displayField + '', value = me.valueField + '';
		var m = Ext.create(me.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		me.store.loadRecords([m]);
		me.setValue(valueText);
	},
	getBeforeLoad : function(st, operation, e) {
		var me = this;
		var me = this, searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
				params : searchParams
			});
		}
		var prefix = me.queryParam.substring(0, me.queryParam
						.lastIndexOf('.'))
				+ '.';
		if (!Ext.isEmpty(me.myQueryParam)) {
			var param = prefix + me.myQueryParam;
			searchParams[param] = me.getRawValue();
		} else {
			searchParams[me.queryParam] = me.lastQuery;
		}
		var activeParam = prefix + 'active';
		var act = Ext.isEmpty(me.active) ? me.myActive : me.active;
		searchParams[activeParam] = act;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.active = config.active;
		me.mon(me.store,'beforeLoad',me.getBeforeLoad,me); 
		me.store.addListener('select', function(comb, records, obs) {
			me.record = records[0];
		});
		me.callParent([cfg]);
	},
	getRecord : function() {
		var me = this;
		return me.record;
	}
});
Ext.define('Foss.baseinfo.commonSelector.OrgModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id'
					}, {
						name : 'code'
					}, {
						name : 'name'
					}, {
						name : 'simpleName'
					}, {
						name : 'pinYin'
					}, {
						name : 'cityCode'
					}, {
						name : 'countyCode'
					}, {
						name : 'type'
					}, {
						name : 'provinceCode'
					}, {
						name : 'active'
					}, {
						name : 'division'
					}, {
						name : 'bigRegion'
					}, {
						name : 'smallRegion'
					}, {
						name : 'salesDepartment'
					}, {
						name : 'transferCenter'
					}, {
						name : 'doAirDispatch'
					}, {
						name : 'transDepartment'
					}, {
						name : 'dispatchTeam'
					}, {
						name : 'billingGroup'
					}, {
						name : 'transTeam'
					}, {
						name : 'isDeliverSchedule'
					}, {
						name : 'isArrangeGoods'
					}, {
						name : 'airDispatch'
					}, {
						name : 'isEntityFinance'
					}, {
						name : 'unifiedCode'
					},{
						name : 'expressSalesDepartment'
					},{
						name : 'expressPart'
					},{
						name : 'expressBigRegion'
					}]
		});

Ext.define('Foss.commonOrgSelector.OrgStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchCommonOrg.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonOrgSelector.OrgCombStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/seacrhOrgByParam.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
//org组织表选择器
Ext.define('Foss.commonOrgSelector.OrgTableCombStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryOrgByParam.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
		
//虚拟营业部
Ext.define('Foss.commonOrgSelector.VirtualDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.OrgModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryVirtualSalesDeptInfos.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'orgVo.orgAdministrativeInfoEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//虚拟营业部多选
Ext.define('Foss.commonOrgSelector.DynamicVirtualDeptSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.dynamicVirtualDeptcombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : null, //配置是否有效
	transCenterCodes : null,//配置外场编码
	expressSalesDepartment : null,//配置是否虚拟营业部
	code : null,//配置虚拟 营业部编码
	pinYin : null,//配置营业部拼音
	simpleName : null,//配置营业部简称
	queryParam : 'orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Foss.commonOrgSelector.VirtualDeptStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			// 传递的一个或者多个快递大区code
			var virtualDeptCodes = null;
			if (!Ext.isEmpty(config.transCenterCodes)) {
				virtualDeptCodes = config.transCenterCodes.split(',');
				searchParams['orgVo.codes'] = virtualDeptCodes;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.active'] = config.active;
			}
			if (!Ext.isEmpty(config.expressSalesDepartment)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.expressSalesDepartment'] 
				= config.expressSalesDepartment;
			}
			if (!Ext.isEmpty(config.code)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.code'] 
				= config.code;
			}
			if (!Ext.isEmpty(config.pinYin)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.pinyin'] 
				= config.pinYin;
			}
			if (!Ext.isEmpty(config.simpleName)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.orgSimpleName'] 
				= config.simpleName;
			}
		})
		me.callParent([cfg]);
	}
});

//虚拟营业部单选
Ext.define('Foss.commonOrgSelector.DynamicVirtualDepartmentSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicVirtualDepartmentcombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : null, //配置是否有效
	transCenterCodes : null,//配置外场编码
	expressSalesDepartment : null,//配置是否虚拟营业部
	code : null,//配置虚拟 营业部编码
	pinYin : null,//配置营业部拼音
	simpleName : null,//配置营业部简称
	queryParam : 'orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Foss.commonOrgSelector.VirtualDeptStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			// 传递的一个或者多个快递大区code
			var virtualDeptCodes = null;
			if (!Ext.isEmpty(config.transCenterCodes)) {
				virtualDeptCodes = config.transCenterCodes.split(',');
				searchParams['orgVo.codes'] = virtualDeptCodes;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.active'] = config.active;
			}
			if (!Ext.isEmpty(config.expressSalesDepartment)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.expressSalesDepartment'] 
				= config.expressSalesDepartment;
			}
			if (!Ext.isEmpty(config.code)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.code'] 
				= config.code;
			}
			if (!Ext.isEmpty(config.pinYin)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.pinyin'] 
				= config.pinYin;
			}
			if (!Ext.isEmpty(config.simpleName)) {
				searchParams['orgVo.commonQueryVirtualSalesDeptDto.orgAdministrativeInfoEntity.orgSimpleName'] 
				= config.simpleName;
			}
		})
		me.callParent([cfg]);
	}
});
//org组织表选择器-232607
Ext.define('Foss.commonOrgSelector.orgCombSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.orgCombSelector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : 'Y', //配置是否有效
	division : null,// 查询事业部 配置此值
	bigRegion : null,// 查询大区 配置此值
	smallRegion : null,// 查询小区 配置此值
	salesDepartment : null,// 查询营业部 配置此值
	transferCenter : null,// 查询外场 配置此值
	doAirDispatch : null,// 查询空运配载 配置此值
	transDepartment : null,// 查询车队 配置此值
	dispatchTeam : null,// 查询车队调度组 配置此值
	billingGroup : null,// 查询集中开单组 配置此值
	transTeam : null,// 查询车队组 配置此值
	isDeliverSchedule : null,// 查询派送排单 配置此值
	isArrangeGoods : null,// 查询理货 配置此值
	airDispatch : null,// 查询空运总调 配置此值     
	isEntityFinance : null,// 查询实体财务部 配置此值	
	expressSalesDepartment : null,//是否虚拟营业部
	expressBigRegion : null,//是否快递大区
	expressPart : null,//是否快递点部
	issecurity : null,//是否保安组
	expressSorting:null,//是否快递分拣
	expressBranch:null,//是否快递分部
	isManageDepartment:null,//是否经营本部
	queryParam : 'commonOrgVo.dto.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Foss.commonOrgSelector.OrgTableCombStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}

			if (!Ext.isEmpty(config.division)) {
				searchParams['commonOrgVo.dto.division'] = config.division;
			}
			if (!Ext.isEmpty(config.bigRegion)) {
				searchParams['commonOrgVo.dto.bigRegion'] = config.bigRegion;
			}
			if (!Ext.isEmpty(config.smallRegion)) {
				searchParams['commonOrgVo.dto.smallRegion'] = config.smallRegion;
			}
			if (!Ext.isEmpty(config.salesDepartment)) {
				searchParams['commonOrgVo.dto.salesDepartment'] = config.salesDepartment;
			}
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['commonOrgVo.dto.transferCenter'] = config.transferCenter;
			}
			if (!Ext.isEmpty(config.doAirDispatch)) {
				searchParams['commonOrgVo.dto.doAirDispatch'] = config.doAirDispatch;
			}
			if (!Ext.isEmpty(config.transDepartment)) {
				searchParams['commonOrgVo.dto.transDepartment'] = config.transDepartment;
			}
			if (!Ext.isEmpty(config.dispatchTeam)) {
				searchParams['commonOrgVo.dto.dispatchTeam'] = config.dispatchTeam;
			}
			if (!Ext.isEmpty(config.billingGroup)) {
				searchParams['commonOrgVo.dto.billingGroup'] = config.billingGroup;
			}
			if (!Ext.isEmpty(config.transTeam)) {
				searchParams['commonOrgVo.dto.transTeam'] = config.transTeam;
			}
			if (!Ext.isEmpty(config.isDeliverSchedule)) {
				searchParams['commonOrgVo.dto.isDeliverSchedule'] = config.isDeliverSchedule;
			}
			if (!Ext.isEmpty(config.isArrangeGoods)) {
				searchParams['commonOrgVo.dto.isArrangeGoods'] = config.isArrangeGoods;
			}
			if (!Ext.isEmpty(config.airDispatch)) {
				searchParams['commonOrgVo.dto.airDispatch'] = config.airDispatch;
			}
			if (!Ext.isEmpty(config.isEntityFinance)) {
				searchParams['commonOrgVo.dto.isEntityFinance'] = config.isEntityFinance;
			}
			if (!Ext.isEmpty(config.expressSalesDepartment)) {
				searchParams['commonOrgVo.dto.expressSalesDepartment'] = config.expressSalesDepartment;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOrgVo.dto.active'] = config.active
			}
			if (!Ext.isEmpty(config.expressBigRegion)) {
				searchParams['commonOrgVo.dto.expressBigRegion'] = config.expressBigRegion
			}
			if (!Ext.isEmpty(config.expressPart)) {
				searchParams['commonOrgVo.dto.expressPart'] = config.expressPart;
			}
			if (!Ext.isEmpty(config.issecurity)) {
				searchParams['commonOrgVo.dto.issecurity'] = config.issecurity;
			}
			if (!Ext.isEmpty(config.expressSorting)) {
				searchParams['commonOrgVo.dto.expressSorting'] = config.expressSorting;
			}			
			if (!Ext.isEmpty(config.expressBranch)) {
				searchParams['commonOrgVo.dto.expressBranch'] = config.expressBranch;
			}
			if (!Ext.isEmpty(config.isManageDepartment)) {
				searchParams['commonOrgVo.dto.isManageDepartment'] = config.isManageDepartment;
			}
		})
		me.callParent([cfg]);
	}
});
//组织单选
Ext.define('Foss.commonOrgSelector.DynamicOrgSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicorgcombselector',
//	fieldLabel : '动态部门单选',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	type : 'ORG',// 部门类型 一种部门类型，传递此值
	types : null,// 部门类型 多种部门类型传递次值 两个类型的值之间用","隔开
	division : null,// 查询事业部 配置此值
	bigRegion : null,// 查询大区 配置此值
	smallRegion : null,// 查询小区 配置此值
	salesDepartment : null,// 查询营业部 配置此值
	transferCenter : null,// 查询外场 配置此值
	doAirDispatch : null,// 查询空运配载 配置此值
	transDepartment : null,// 查询车队 配置此值
	dispatchTeam : null,// 查询车队调度组 配置此值
	billingGroup : null,// 查询集中开单组 配置此值
	transTeam : null,// 查询车队组 配置此值
	isDeliverSchedule : null,// 查询派送排单 配置此值
	isArrangeGoods : null,// 查询理货 配置此值
	airDispatch : null,// 查询空运总调 配置此值     
	isEntityFinance : null,// 查询实体财务部 配置此值	
	deptCode : null,//组织编码	
	expressSalesDepartment : null,//查询虚拟营业部配置此值
	expressBigRegion : null,//是否快递大区
	expressPart : null,//是否快递点部
	arrive:null,//是否可到达
	queryParam : 'commonOrgVo.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Foss.commonOrgSelector.OrgCombStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			// 传递的部门类型是多种
			var types = null;
			if (!Ext.isEmpty(config.types)) {
				types = config.types.split(',');
				searchParams['commonOrgVo.types'] = types;
			}
			
			if (!Ext.isEmpty(config.deptCode)) {
				searchParams['commonOrgVo.deptCode'] = config.deptCode;
			}						
			if (!Ext.isEmpty(config.type)) {
				searchParams['commonOrgVo.type'] = config.type;
			}
			if (!Ext.isEmpty(config.division)) {
				searchParams['commonOrgVo.division'] = config.division;
			}
			if (!Ext.isEmpty(config.bigRegion)) {
				searchParams['commonOrgVo.bigRegion'] = config.bigRegion;
			}
			if (!Ext.isEmpty(config.smallRegion)) {
				searchParams['commonOrgVo.smallRegion'] = config.smallRegion;
			}
			if (!Ext.isEmpty(config.salesDepartment)) {
				searchParams['commonOrgVo.salesDepartment'] = config.salesDepartment;
			}
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['commonOrgVo.transferCenter'] = config.transferCenter;
			}
			if (!Ext.isEmpty(config.doAirDispatch)) {
				searchParams['commonOrgVo.doAirDispatch'] = config.doAirDispatch;
			}
			if (!Ext.isEmpty(config.transDepartment)) {
				searchParams['commonOrgVo.transDepartment'] = config.transDepartment;
			}
			if (!Ext.isEmpty(config.dispatchTeam)) {
				searchParams['commonOrgVo.dispatchTeam'] = config.dispatchTeam;
			}
			if (!Ext.isEmpty(config.billingGroup)) {
				searchParams['commonOrgVo.billingGroup'] = config.billingGroup;
			}
			if (!Ext.isEmpty(config.transTeam)) {
				searchParams['commonOrgVo.transTeam'] = config.transTeam;
			}
			if (!Ext.isEmpty(config.isDeliverSchedule)) {
				searchParams['commonOrgVo.isDeliverSchedule'] = config.isDeliverSchedule;
			}
			if (!Ext.isEmpty(config.isArrangeGoods)) {
				searchParams['commonOrgVo.isArrangeGoods'] = config.isArrangeGoods;
			}
			if (!Ext.isEmpty(config.airDispatch)) {
				searchParams['commonOrgVo.airDispatch'] = config.airDispatch;
			}
			if (!Ext.isEmpty(config.isEntityFinance)) {
				searchParams['commonOrgVo.isEntityFinance'] = config.isEntityFinance;
			}
			if (!Ext.isEmpty(config.expressSalesDepartment)) {
				searchParams['commonOrgVo.expressSalesDepartment'] = config.expressSalesDepartment;
			}
			if (!Ext.isEmpty(config.expressPart)) {
				searchParams['commonOrgVo.expressPart'] = config.expressPart;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOrgVo.active'] = config.active
			}
			if (!Ext.isEmpty(config.arrive)) {
				searchParams['commonOrgVo.arrive'] = config.arrive
			}
			if (!Ext.isEmpty(config.expressBigRegion)) {
				searchParams['commonOrgVo.expressBigRegion'] = config.expressBigRegion
			}
		})
		me.callParent([cfg]);
	}
});
//组织多选
Ext.define('Foss.commonOrgSelector.DynamicOrgMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.dynamicorgmulticombselector',
	//fieldLabel : '动态部门单选',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	type : 'ORG',// 部门类型 一种部门类型，传递此值
	types : null,// 部门类型 多种部门类型传递次值 两个类型的值之间用","隔开
	division : null,// 查询事业部 配置此值
	bigRegion : null,// 查询大区 配置此值
	smallRegion : null,// 查询小区 配置此值
	salesDepartment : null,// 查询营业部 配置此值
	transferCenter : null,// 查询外场 配置此值
	doAirDispatch : null,// 查询空运配载 配置此值
	transDepartment : null,// 查询车队 配置此值
	dispatchTeam : null,// 查询车队调度组 配置此值
	billingGroup : null,// 查询集中开单组 配置此值
	transTeam : null,// 查询车队组 配置此值
	isDeliverSchedule : null,// 查询派送排单 配置此值
	isArrangeGoods : null,// 查询理货 配置此值
	airDispatch : null,// 查询空运总调 配置此值
	isEntityFinance : null,// 查询实体财务部 配置此值
	expressPart:null,
	queryParam : 'commonOrgVo.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Foss.commonOrgSelector.OrgCombStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			// 传递的部门类型是多种
			var types = null;
			if (!Ext.isEmpty(config.types)) {
				types = config.types.split(',');
				searchParams['commonOrgVo.types'] = types;
			}
			if (!Ext.isEmpty(config.type)) {
				searchParams['commonOrgVo.type'] = config.type;
			}
			if (!Ext.isEmpty(config.division)) {
				searchParams['commonOrgVo.division'] = config.division;
			}
			if (!Ext.isEmpty(config.bigRegion)) {
				searchParams['commonOrgVo.bigRegion'] = config.bigRegion;
			}
			if (!Ext.isEmpty(config.smallRegion)) {
				searchParams['commonOrgVo.smallRegion'] = config.smallRegion;
			}
			if (!Ext.isEmpty(config.salesDepartment)) {
				searchParams['commonOrgVo.salesDepartment'] = config.salesDepartment;
			}
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['commonOrgVo.transferCenter'] = config.transferCenter;
			}
			if (!Ext.isEmpty(config.doAirDispatch)) {
				searchParams['commonOrgVo.doAirDispatch'] = config.doAirDispatch;
			}
			if (!Ext.isEmpty(config.transDepartment)) {
				searchParams['commonOrgVo.transDepartment'] = config.transDepartment;
			}
			if (!Ext.isEmpty(config.dispatchTeam)) {
				searchParams['commonOrgVo.dispatchTeam'] = config.dispatchTeam;
			}
			if (!Ext.isEmpty(config.billingGroup)) {
				searchParams['commonOrgVo.billingGroup'] = config.billingGroup;
			}
			if (!Ext.isEmpty(config.transTeam)) {
				searchParams['commonOrgVo.transTeam'] = config.transTeam;
			}
			if (!Ext.isEmpty(config.isDeliverSchedule)) {
				searchParams['commonOrgVo.isDeliverSchedule'] = config.isDeliverSchedule;
			}
			if (!Ext.isEmpty(config.isArrangeGoods)) {
				searchParams['commonOrgVo.isArrangeGoods'] = config.isArrangeGoods;
			}
			if (!Ext.isEmpty(config.airDispatch)) {
				searchParams['commonOrgVo.airDispatch'] = config.airDispatch;
			}
			if (!Ext.isEmpty(config.isEntityFinance)) {
				searchParams['commonOrgVo.isEntityFinance'] = config.isEntityFinance;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOrgVo.active'] = config.active;
			}
			if (!Ext.isEmpty(config.expressPart)) {
				searchParams['commonOrgVo.expressPart'] = config.expressPart;
			}
		})
		me.callParent([cfg]);
	}
});
// 大区store
Ext.define('Foss.baseinfo.commonSelector.BigRegionStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchAllBigRegion.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					//searchParams['commonOrgVo.division'] = 'Y';
					searchParams['commonOrgVo.bigRegion'] = 'Y';
					searchParams['commonOrgVo.active'] = 'Y';
				}
			}
		});
// 大区store
Ext.define('Foss.baseinfo.commonSelector.SmallRegionStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchOrgDeptByParentCode.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['commonOrgVo.active'] = 'Y';
				}
			}
		});
//ying
Ext.define('Foss.baseinfo.commonSelector.SaleDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.OrgModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchAllBigRegion.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['commonOrgVo.division'] = 'Y';
			searchParams['commonOrgVo.bigRegion'] = 'Y';
			searchParams['commonOrgVo.active'] = 'Y';
		}
	}
});

//大小区/营业部联动
Ext.define('Foss.commonSelector.linkOrgDeptCombSelector', {
	extend : 'Deppon.commonselector.LinkageContainer',
	alias : 'widget.dynamiclinkorgdeptcombselector',
// fieldLabel : '组织联动',
	labelSeparator:'',
	labelWidth:0,
	type : 'B-S-D',// type ：B-S-D 大小区，营业部 B-S :大小区S-D:小区，营业部
	width : 800,
	bigRegionWidth : 150,// 大区长度
	bigRegionLabel : '',// 省份label
	bigRegionName : '',// 省份名称—对应name
	bigRegionIsBlank : true,
	bigRegionLabelWidth: null,
	smallRegionWidth : 150,// 城市长度
	smallRegionLabel : '',// 城市label
	smallRegionName : '',// 城市name
	smallRegionIsBlank : true,
	smallRegionLabelWidth: null,
	saleDeptWidth : 150,// 县长度
	saleDeptLabel : '',// 县label
	saleDeptName : '',// 县name
	saleDeptIsBlank : true,
	saleDeptLabelWidth: null,
	layout : 'column',
	labelWid : 20,
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			minChars : 0,
			labelSeparator : '',
			displayField : 'name',//显示名称
			valueField : 'code',
			minChars : 0,
			queryParam : 'commonOrgVo.name'
		}
	},
	setReginValue : function(displayText, valueText, order) {// 0：大区值，1：小区的值，2：营业部的值
		var me = this;
		var regionObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				regionObj=me.bigRegion;
			}else if(order == '1'){
				regionObj=me.smallRegion;
			}else if(order == '2'){
				regionObj=me.saleDept;
			} 
		}
		var  key = regionObj.displayField + '', value =regionObj.valueField
				+ '';
		var m = Ext.create(regionObj.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		regionObj.store.loadRecords([m]);
		regionObj.setValue(valueText);
	}, 
	bigRegion:null,
	getBigRegion:function(bigRegionWidth,bigRegionLabel,bigRegionName,bigRegionIsBlank,configType,bigRegionLabelWidth,smallRegionObj){
		if(Ext.isEmpty(this.bigRegion)){
			this.bigRegion=Ext.widget('linkagecomboselector',{
					configType :configType,
					smallRegionObj : smallRegionObj,
					editable:false,
					eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					itemId : 'Foss_baseinfo_BigRegion_ID',
					store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入				
					listeners : {
						'blur' : function(ths, the, eOpts) {
							if (Ext.isEmpty(ths.value)) {
								ths.setRawValue(null);
							}
						},
						'change' : function(ths, the, eOpts){
								ths.smallRegionObj.setReadOnly(false);
								var smallObj=ths.smallRegionObj.getEl();
								if(!Ext.isEmpty(smallObj)){
									smallObj.query('input')[0].readOnly = 'readOnly';	
								}							
						}
					},					
					fieldLabel : bigRegionLabel,
					displayField : 'name',// 显示名称
					valueField : 'code',
					name : bigRegionName,
					width : bigRegionWidth,
					labelWidth:bigRegionLabelWidth,
					isPaging: true,
					allowBlank : bigRegionIsBlank,
					queryParam:'commonOrgVo.name'
			});
		}
		return this.bigRegion;
	},			
	smallRegion:null,
	getSmallRegion:function(smallRegionWidth,smallRegionLabel,smallRegionName,smallRegionIsBlank,smallRegionLabelWidth,configType,saleDeptObj){
		if(Ext.isEmpty(this.smallRegion)){
			this.smallRegion=Ext.widget('linkagecomboselector',{
					configType :configType,
					saleDeptObj : saleDeptObj,
					editable:false,
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					itemId : 'Foss_baseinfo_SmallRegion_ID',
					store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入				
					listeners : {
						'blur' : function(ths, the, eOpts) {
							if (Ext.isEmpty(ths.value)) {
								ths.setRawValue(null);
							}
						},
						'change' : function(ths, the, eOpts){							
								ths.saleDeptObj.setReadOnly(false);
								var saleObj=ths.saleDeptObj.getEl();
								if(!Ext.isEmpty(saleObj)){
									saleObj.query('input')[0].readOnly = 'readOnly';	
								}							 
						}
					},
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					width : smallRegionWidth,
					fieldLabel : smallRegionWidth,
					name : smallRegionLabel,
					labelWidth:smallRegionLabelWidth,
					allowBlank:smallRegionIsBlank,
					isPaging: true,									
					parentParamsAndItemIds : {
						'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
					},// 此处城市不需要传入
					queryParam:'commonOrgVo.name'
			});
		}
		return this.smallRegion;
	},	
	saleDept:null,
	getSaleDept:function(saleDeptWidth,saleDeptLabel,saleDeptNames,saleDeptLabelWidth,saleDeptIsBlank){
		if(Ext.isEmpty(this.saleDept)){
		this.saleDept=Ext.widget('linkagecomboselector',{
					eventType : ['callparent'],
					store : Ext
							.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
					listeners : {
						'blur' : function(ths, the, eOpts) {
							if (Ext.isEmpty(ths.value)) {
								ths.setRawValue(null);
							}
						}						
					},
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					editable:false,
					width : saleDeptWidth,
					fieldLabel : saleDeptLabel,
					name : saleDeptNames,					
					allowBlank : saleDeptIsBlank,					
					labelWidth:saleDeptLabelWidth,
					isPaging: true,
					parentParamsAndItemIds : {
						'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
					},// 此处区域不需要传入
					queryParam:'commonOrgVo.name'
					});
		}
		return  this.saleDept;
	},	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var saleDept = me.getSaleDept(config.saleDeptWidth,config.saleDeptLabel,config.saleDeptNames,config.saleDeptIsBlank,config.saleDeptLabelWidth);
		var smallRegion = me.getSmallRegion(config.smallRegionWidth,config.smallRegionLabel,config.smallRegionName,config.smallRegionIsBlank,config.type,config.smallRegionLabelWidth,saleDept);
		var bigRegion = me.getBigRegion(config.bigRegionWidth,config.bigRegionLabel,config.bigRegionName,config.bigRegionIsBlank,config.type,config.bigRegionLabelWidth,smallRegion);
		me.items=[bigRegion,smallRegion,saleDept];
		if (config.type == 'B-S-D') {
			smallRegion.setReadOnly(true);
			saleDept.setReadOnly(true); 
			smallRegion.cls='readonlyhaveborder';
			saleDept.cls='readonlyhaveborder';
			me.items=[bigRegion,smallRegion,saleDept];
		} else if (config.type == 'S-D') {
			saleDept.setReadOnly(true);
			saleDept.cls='readonlyhaveborder';
			smallRegion.parentParamsAndItemIds = null;
			smallRegion.eventType=['focus'];
			me.items=[smallRegion,saleDept];
		} else if (config.type == 'B-S') {
			smallRegion.setReadOnly(true);
			smallRegion.cls='readonlyhaveborder';
			bigRegion.eventType=['focus'];
			me.items=[bigRegion,smallRegion];
		}
		me.callParent([cfg]);
	}
});

// 定义treeStore
Ext.define('Foss.baseinfo.commonSelector.resource.TreeStore', {
			extend : 'Ext.data.TreeStore',
			storeId : 'Foss_baseinfo_resource_TreeStore_Id',
			root : {
				// 根的文本信息
				text : '权限列表',
				// 设置根节点的ID
				id : '01',
				// 根节点是否展开
				expanded : false
			},

			// 设置一个代理，通过读取内存数据
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../baseinfo/queryCommonResource.action',
				reader : {
					type : 'json',
					root : 'nodes'
				}
			}
		});

Ext.define('Foss.baseinfo.commonSelector.resource.Tree', {
			alias : 'widget.staticresourcetreeselector',
			extend : 'Ext.tree.Panel',
			title : '权限',
			margin : false,
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			width : 300,
			autoScroll : true,
			animate : false,
			useArrows : true,
			frame : false,
			rootVisible : true,
			columnWidth : 1,
			store : null,
			// administrativeRegionsTreeRightMenu :
			// Ext.create('Foss.baseinfo.commonSelector.administrativeRegions.DistrictTreeRightMenu'),
			defaults : {
				margin : '5 5 5 5'
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.resource.TreeStore');
				// var administrativeRegionsTreeRightMenu =
				// Ext.getCmp('Foss_baseinfo_administrativeRegions_DistrictTreeMenu_Id');
				// 监听鼠标事件
				me.listeners = {
					// 左键单击
					// itemclick:function(node,record,item,index,e){
					// //阻止浏览器默认行为处理事件
					// e.preventDefault();
					// //me.bindData(me,record);
					// },
					// 右键单击
					itemcontextmenu : function(node, record, item, index, e) {
						e.preventDefault();
					}
				};
				me.callParent([cfg]);
			}
		});
/** ************货区定义*************** */
Ext.define('Foss.commonSelector.GoodsAreaModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'goodsAreaCode'
					}, {
						name : 'goodsAreaName'
					}, {
						name : 'virtualCode'
					}, {
						name : 'organizationCode'
					}, {
						name : 'goodsAreaName'
					}, {
						name : 'organizationName'
					}]
		});

Ext.define('Foss.commonSelector.GoodsAreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.GoodsAreaModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonGoodsArea.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'goodsAreaVo.goodsAreaEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
//库区单选
Ext.define('Foss.commonSelector.GoodsAreaSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commongoodsareaselector',
//	fieldLabel : '库区',
	displayField : 'goodsAreaName',// 显示名称
	valueField : 'goodsAreaCode',// 值
	deptCode : null,
	goodsAreaType:null,//库区类型
	queryParam : 'goodsAreaVo.goodsAreaEntity.goodsAreaName',// 查询参数
	showContent : '{goodsAreaName}&nbsp;&nbsp;&nbsp;{goodsAreaCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.commonSelector.GoodsAreaStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = me.deptCode;
			searchParams['goodsAreaVo.goodsAreaEntity.goodsAreaType'] = me.goodsAreaType;
		})
		me.callParent([cfg]);
	}
});
Ext.define('Foss.commonSelector.GoodsAreaStoreBydeptCode', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.GoodsAreaModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonGoodsAreaBydeptCode.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'goodsAreaVo.goodsAreaEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
//库区单选根据code
Ext.define('Foss.commonSelector.GoodsAreaSelectorBycode', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commongoodsareaselectorBycode',
//	fieldLabel : '库区',
	displayField :'goodsAreaCode',// 值 //'goodsAreaName',// 显示名称
	valueField : 'goodsAreaName',// 显示名称//'goodsAreaCode',// 值
	deptCode : null,
	goodsAreaType:null,//库区类型
	queryParam : 'goodsAreaVo.goodsAreaEntity.goodsAreaCode',// 查询参数
	showContent : '{goodsAreaName}&nbsp;&nbsp;&nbsp;{goodsAreaCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.commonSelector.GoodsAreaStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = me.deptCode;
			searchParams['goodsAreaVo.goodsAreaEntity.goodsAreaType'] = me.goodsAreaType;
		})
		me.callParent([cfg]);
	}
});
//库区多选
Ext.define('Foss.commonSelector.GoodsAreaMultiSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	alias : 'widget.commongoodsareamultiselector',
	//fieldLabel : '库区',
	displayField : 'goodsAreaName',// 显示名称
	valueField : 'goodsAreaCode',// 值
	deptCode : null,
	isPaging: true, 
	queryParam : 'goodsAreaVo.goodsAreaEntity.goodsAreaName',// 查询参数
	showContent : '{goodsAreaName}&nbsp;&nbsp;&nbsp;{goodsAreaCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.commonSelector.GoodsAreaStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = me.deptCode;
		})
		me.callParent([cfg]);
	}
});

/** ************银行*************** */
Ext.define('Foss.commonSelector.BankModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code'
					}, {
						name : 'name'
					}, {
						name : 'parentBank'
					}, {
						name : 'provId'
					}, {
						name : 'provName'
					}, {
						name : 'cityCode'
					}, {
						name : 'cityName'
					}, {
						name : 'headOffice'
					}, {
						name : 'intraDayType'
					}]
		});

Ext.define('Foss.commonSelector.BankStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.BankModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonBank.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'bankVo.bankList',
					totalProperty : 'totalCount'
				}
			}
		});
Ext.define('Foss.commonSelector.BankMultiSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	alias : 'widget.commonbankmultiselector',
//	fieldLabel : '库区',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	headOffice:null,
	queryParam : 'bankVo.bankEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.commonSelector.BankStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			searchParams['bankVo.bankEntity.headOffice'] = me.headOffice;
		})
		me.callParent([cfg]);
	}
});

/** ************外请司机定义*************** */
Ext.define('Foss.commonSelector.LeasedDriverModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'driverName'
					}, {
						name : 'idCard'
					}, {
						name : 'address'
					}, {
						name : 'driverPhone'
					}, {
						name : 'vehicleNo'
					}]
		});

Ext.define('Foss.commonSelector.LeasedDriverStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.LeasedDriverModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonLeasedDriver.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'leasedDriverVo.leasedDriverList',
					totalProperty : 'totalCount'
				}
			}
		});
Ext.define('Foss.commonSelector.LeasedDriverSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonleaseddriverselector',
			//fieldLabel : '外请司机',
			displayField : 'driverName',// 显示名称
			valueField : 'idCard',// 值
			status : 'Y',//Y可用，N不可用
			statues:null,//
			listWidth:350,
			queryParam : 'leasedDriverVo.leasedDriver.driverName',// 查询参数
			showContent : '{driverName}&nbsp;&nbsp;&nbsp;{idCard}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.commonSelector.LeasedDriverStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if (!Ext.isEmpty(config.statues)) {
						var statues = config.statues.split(',');
						searchParams['leasedDriverVo.leasedDriver.statues'] = statues;
					}else{
						if(!Ext.isEmpty(config.status)){
							searchParams['leasedDriverVo.leasedDriver.status'] = config.status;
						}else{
							searchParams['leasedDriverVo.leasedDriver.status'] = me.status;
						}
					}
				});
				me.callParent([cfg]);
			}
		});
/** ************月台定义************* */
Ext.define('Foss.commonSelector.PlatformModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'platformCode'
					}, {
						name : 'virtualCode'
					}, {
						name : 'organizationCode'
					}, {
						name : 'organizationName'
					}, {
						name : 'vehicleNameInfo'
					}, {
						name : 'chasLift'
					}, {
						name : 'ordinate'
					}, {
						name : 'abscissa'
					}
					]
		});

Ext.define('Foss.commonSelector.PlatformStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.PlatformModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPlatform.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'platformVo.platformEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
//月台
Ext.define('Foss.commonSelector.PlatformSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonplatformselector',
//			fieldLabel : '月台',
			displayField : 'platformCode',// 显示名称
			valueField : 'platformCode',// 值
			orgCode:null,
			hasLift:null,//是否有升降
			vehicleName:null,//车辆类型名称
			queryParam : 'platformVo.platformEntity.platformCode',// 查询参数
			showContent : '{platformCode}&nbsp;&nbsp;&nbsp;{chasLift}&nbsp;&nbsp;&nbsp;{vehicleNameInfo}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.commonSelector.PlatformStore');
				
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {}; 
					}
					if(!Ext.isEmpty(config.orgCode)){ 
						searchParams['platformVo.platformEntity.organizationCode'] = config.orgCode;
					}
					if(!Ext.isEmpty(config.hasLift)){ 
						searchParams['platformVo.platformEntity.hasLift'] = config.hasLift;
					}
					if(!Ext.isEmpty(config.vehicleName)){ 
						searchParams['platformVo.platformEntity.vehicleName'] = config.vehicleName;
					}
					Ext.apply(operation, {
						params : searchParams
					});
				}); 
				me.callParent([cfg]);
			}
		});

/** ************外请车定义*************** */
Ext.define('Foss.commonSelector.LeasedVehicleModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'vehicleNo'
					}, {
						name : 'vehicleFrameNo'
					}, {
						name : 'engineNo'
					}, {
						name : 'bridge'
					}, {
						name : 'deadLoad'
					}, {
						name : 'selfWeight'
					}, {
						name : 'endTime'
					}, {
						name : 'plat'
					}, {
						name : 'tailBoard'
					}, {
						name : 'vehicleLength'
					}, {
						name : 'vehicleWidth'
					}, {
						name : 'vehicleHeight'
					}, {
						name : 'openVehicle'
					}, {
						name : 'railVehicle'
					}, {
						name : 'gps'
					}, {
						name : 'bargainVehicle'
					}, {
						name : 'operatingLic'
					}, {
						name : 'contact'
					}, {
						name : 'contactPhone'
					}, {
						name : 'contactAddress'
					}, {
						name : 'vehicleType'
					}, {
						name : 'vehcleLengthCode'
					}, {
						name : 'brand'
					}, {
						name : 'status'
					}]
		});

Ext.define('Foss.commonSelector.LeasedVehicleStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.LeasedVehicleModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonLeasedVehicle.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'leasedVehicleVo.leasedVehicleList',
					totalProperty : 'totalCount'
				}
			}
		});
Ext.define('Foss.commonSelector.LeasedVehicleSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonleasedvehicleselector',
//			fieldLabel : '外请车辆',
			displayField : 'vehicleNo',// 显示名称
			valueField : 'vehicleNo',// 值
			status : 'Y',//Y or N
			queryParam : 'leasedVehicleVo.leasedVehicle.vehicleNo',// 查询参数
			showContent : '{contact}&nbsp;&nbsp;&nbsp;{vehicleNo}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config); 
				me.store = Ext.create('Foss.commonSelector.LeasedVehicleStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(Ext.isEmpty(config.status)){
						searchParams['leasedVehicleVo.leasedVehicle.status'] = me.status;
					}else{
						searchParams['leasedVehicleVo.leasedVehicle.status'] = config.status;
					} 
				});
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.baseinfo.commonSelector.ReginModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'degree',
						type : 'string'
					}, {
						name : 'degreeName',
						type : 'string'
					}, {
						name : 'parentDistrictName',
						type : 'string'
					}					
					]
		});

Ext.define('Foss.baseinfo.commonSelector.DynamicProvinceStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			pageSize : 500,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryProvinceByName.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.provinceList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.baseinfo.commonSelector.DynamicCityStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCityByName.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.cityList',
					totalProperty : 'totalCount'
				}
			}
		});
Ext.define('Foss.baseinfo.commonSelector.DynamicAreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryAreaByName.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.areaList',
					totalProperty : 'totalCount'
				}
			}
		});
Ext.define('Foss.baseinfo.commonSelector.ReginStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryReginByName.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.cityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.ReginSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonreginselector',
//			fieldLabel : '行政区域查询',
			eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			displayField : 'name',// 显示名称
			valueField : 'code',
			queryParam : 'cityVo.name',// 查询参数
			degree:null,//省市县类别
			showContent : '{name}&nbsp;&nbsp;&nbsp;{degreeName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.ReginStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.degree)){
						searchParams['cityVo.degree'] = config.degree;
					} 
				});
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'code',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}, {
				name : 'degree',
				type : 'string'
			}, {
				name : 'degreeName',
				type : 'string'
			}, {
				name : 'parentDistrictName',
				type : 'string'
			}					
			]
});

//快递派送区域

Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonExpressDeliveryRegions.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressDeliveryRegionsVo.expressDeliveryRegionsList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.commonrexpressdeliveryregionelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonrexpressdeliveryregionelector',
//	fieldLabel : '行政区域查询',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	queryParam : 'expressDeliveryRegionsVo.expressDeliveryRegionsEntity.name',// 查询参数
	degree:null,//省市县类别
	showContent : '{name}&nbsp;&nbsp;&nbsp;{degreeName}&nbsp;&nbsp;&nbsp;{parentDistrictName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressDeliveryRegionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.degree)){
				searchParams['expressDeliveryRegionsVo.expressDeliveryRegionsEntity.degree'] = config.degree;
			} 
		});
		me.callParent([cfg]);
	}
});



Ext.define('Foss.baseinfo.commonSelector.ReginByConditionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ReginModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonReginByCondition.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'cityVo.reginList',
			totalProperty : 'totalCount'
		}
	}
});
//行政区域（多选）
Ext.define('Foss.commonSelector.commonreginbyconditionselector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonreginbyconditionselector',
//	fieldLabel : '行政区域查询',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	queryParam : 'cityVo.name',// 查询参数
	degree:null,//省市县类别
	parentId:null,//上级区域code
	showContent : '{name}&nbsp;&nbsp;&nbsp;{degreeName}',// 显示表格列
	setReginValue : function(displayText, valueText) {
		var me = this;
		var  key = me.displayField + '', value =me.valueField
				+ '';
		var m = Ext.create(me.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		me.store.loadRecords([m]);
		me.setValue(valueText);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.ReginByConditionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.degree)){
				searchParams['cityVo.degree'] = config.degree;
			}if(!Ext.isEmpty(config.parentId)){
				searchParams['cityVo.parentId'] = config.parentId;
			}
		});
		me.callParent([cfg]);
	}
});



Ext.define('Foss.commonSelector.ProvinceSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonprovinceselector',
//			fieldLabel : '省',
			eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			displayField : 'name',// 显示名称
			valueField : 'code',
			queryParam : 'cityVo.name',// 查询参数
			isPaging : false,// 分页
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.DynamicProvinceStore');
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.commonSelector.CitySelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commoncityselector',
//	fieldLabel : '市',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	queryParam : 'cityVo.name',// 查询参数
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.DynamicCityStore');
		me.callParent([cfg]);
	}
});
Ext.define('Foss.commonSelector.AreaSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	alias : 'widget.commonareaselector',
//	fieldLabel : '县',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	parentId:null,
	queryParam : 'cityVo.name',// 查询参数
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.DynamicAreaStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}					
			if(!Ext.isEmpty(config.parentId)){
				searchParams['cityVo.parentId'] = config.parentId;
			}										
		});
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.commonSelector.ProvinceStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			pageSize : 500,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryProvince.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.provinceList',
					totalProperty : 'totalCount'
				}
			}
		});

// 下拉框的store,因为下拉框是根据名称去查询具体城市的
Ext.define('Foss.baseinfo.commonSelector.CityStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCity.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.cityList',
					totalProperty : 'totalCount'
				}
			}
		});

// 区域面板的store
Ext.define('Foss.baseinfo.commonSelector.AreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryArea.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.areaList',
					totalProperty : 'totalCount'
				}
			}
		});
// 区域面板的store
Ext.define('Foss.baseinfo.commonSelector.NationStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ReginModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchAllNationList.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'cityVo.nationList',
					totalProperty : 'totalCount'
				}
			}
		});
//行政区域省市区街道START
//省
Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionProvinceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryRegionsByParentCode.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressDeliveryRegionsVo.expressDeliveryRegionsList'
		}
	}
});
//市
Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionCityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryRegionsByParentCode.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressDeliveryRegionsVo.expressDeliveryRegionsList'
		}
	}
});
//区
Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionCountyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryRegionsByParentCode.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressDeliveryRegionsVo.expressDeliveryRegionsList'
		}
	}
});
//街
Ext.define('Foss.baseinfo.commonSelector.ExpressDeliveryRegionStreetStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ExpressDeliveryRegionModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryRegionsByParentCode.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressDeliveryRegionsVo.expressDeliveryRegionsList'
		}
	}
});
Ext.define('Foss.commonSelector.linkExpressDeliveryReginCombSelector', {
	extend : 'Deppon.commonselector.LinkageContainer',
	alias : 'widget.linkexpressdeliveryregincombselector',
	type : 'P-C-C-S',// type ：P-C-C-S 省市县街
	width : 800,
	provinceWidth : 150,// 省份长度
	provinceLabel : '',// 省份label
	provinceLabelWidth:null,
	provinceName : '',// 省份名称—对应name
	provinceIsBlank : true,
	cityWidth : 150,// 城市长度
	cityLabel : '',// 城市label
	cityName : '',// 城市name
	cityIsBlank : true,
	cityLabelWidth:null,
	countyWidth : 150,// 县长度
	countyLabel : '',// 县label
	countyName : '',// 县name
	countyIsBlank : true,
	countyLabelWidth:null,
	streetWidth : 150,// 街道长度
	streetLabel : '',// 街道label
	streetName : '',// 街道名称--对应name
	streetIsBlank : true,
	streetLabelWidth:null,
	layout : {
        type: 'table',
        columns: 4
    },
	labelWid : 20,
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			minChars : 0,
			labelSeparator : ''
		}
	},
	setReginValue : function(displayText, valueText, order) {// 0：省的值，1：市的值，2：县的值，3：街的值
		var me = this;
		var regionObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				regionObj=me.province;
			}else if(order == '1'){
				regionObj=me.city;
			}else if(order == '2'){
				regionObj=me.county;
			}else if(order == '3'){
				regionObj=me.street;
			}
		}
		var  key = regionObj.displayField + '', value =regionObj.valueField
				+ '';
		var m = Ext.create(regionObj.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		regionObj.store.loadRecords([m]);
		regionObj.setValue(valueText);
	},
	province:null,
	getProvince:function(provinceWidth,provinceLabel,provinceName,provinceIsBlank,provinceLabelWidth,configType,cityObj){
		if(Ext.isEmpty(this.province)){
			this.province=Ext.widget('linkagecomboselector',{
				configType:configType,
				cityObj : cityObj,
				editable:false,
				eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				itemId : 'Foss_baseinfo_ExpressDeliveryProvince_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.ExpressDeliveryRegionProvinceStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change' : function(ths, the, eOpts){
						ths.cityObj.setReadOnly(false); 
						var cityObj=ths.cityObj.getEl();
						if(!Ext.isEmpty(cityObj)){
							cityObj.query('input')[0].readOnly = 'readOnly';	
						}
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				width : provinceWidth,
				fieldLabel : provinceLabel,
				name : provinceName,
				labelWidth:provinceLabelWidth,
				allowBlank : provinceIsBlank,
				isPaging: false, 
				queryParam : 'expressDeliveryRegionsVo.name'
			});
		}
		return this.province;
	},
	city:null,
	getCity:function(cityWidth,cityLabel,cityName,cityIsBlank,cityLabelWidth,configType,countyObj){
		if(Ext.isEmpty(this.city)){
				this.city=Ext.widget('linkagecomboselector',{
					configType:configType,
					countyObj : countyObj,
					editable:false,
					itemId : 'Foss_baseinfo_ExpressDeliveryCity_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Foss.baseinfo.commonSelector.ExpressDeliveryRegionCityStore'),// 从外面传入
					listeners : {
						'blur' : function(ths, the, eOpts) {
							if (Ext.isEmpty(ths.value)) {
								ths.setRawValue(null);
							}
						},
						'change' : function(ths, the, eOpts){
							ths.countyObj.setReadOnly(false);
							var countyObj=ths.countyObj.getEl();
							if(!Ext.isEmpty(countyObj)){
								countyObj.query('input')[0].readOnly = 'readOnly';	
							}
						}
					},
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					width : cityWidth,
					fieldLabel : cityLabel,
					name : cityName,
					labelWidth:cityLabelWidth,
					allowBlank : cityIsBlank,
					isPaging: false,  
					parentParamsAndItemIds : {
						'expressDeliveryRegionsVo.parentDistrictCode' : 'Foss_baseinfo_ExpressDeliveryProvince_ID'
					},
					queryParam : 'expressDeliveryRegionsVo.name'
			});
		}
		return this.city;
	},
	county:null,
	getCounty:function(countyWidth,countyLabel,countyName,countyIsBlank,countyLabelWidth,configType,streetObj){
		if(Ext.isEmpty(this.county)){
			this.county=Ext.widget('linkagecomboselector',{
				store : Ext.create('Foss.baseinfo.commonSelector.ExpressDeliveryRegionCountyStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change' : function(ths, the, eOpts){
						ths.streetObj.setReadOnly(false);
						var streetObj=ths.streetObj.getEl();
						if(!Ext.isEmpty(streetObj)){
							streetObj.query('input')[0].readOnly = 'readOnly';	
						}
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				editable:false,
				eventType : ['callparent'],
				configType:configType,
				streetObj : streetObj,
				itemId : 'Foss_baseinfo_ExpressDeliveryCounty_ID',
				width : countyWidth,
				fieldLabel : countyLabel,
				name : countyName,
				allowBlank : countyIsBlank,
				labelWidth:countyLabelWidth,
				isPaging: false, 
				parentParamsAndItemIds : {
					'expressDeliveryRegionsVo.parentDistrictCode' : 'Foss_baseinfo_ExpressDeliveryCity_ID'
				},
				queryParam : 'expressDeliveryRegionsVo.name'
			});
		}
		return  this.county;
	},
	street:null,
	getStreet:function(streetWidth,streetLabel,streetName,streetIsBlank,streetLabelWidth){
		if(Ext.isEmpty(this.street)){
			this.street=Ext.widget('linkagecomboselector',{
				editable:false,
				itemId : 'Foss_baseinfo_ExpressDeliveryStreet_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.ExpressDeliveryRegionStreetStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				width : streetWidth,
				minChars : 0,
				fieldLabel : streetLabel,
				name : streetName,
				labelWidth:streetLabelWidth,
				allowBlank : streetIsBlank,
				isPaging: false, 
				parentParamsAndItemIds : {
					'expressDeliveryRegionsVo.parentDistrictCode' : 'Foss_baseinfo_ExpressDeliveryCounty_ID'
				},
				queryParam : 'expressDeliveryRegionsVo.name'
			});
		}
		return this.street;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var street = me.getStreet(config.streetWidth,config.streetLabel,config.streetName,config.streetIsBlank,config.streetLabelWidth);
		var county = me.getCounty(config.countyWidth,config.countyLabel,config.countyName,config.countyIsBlank,config.countyLabelWidth,config.type,street);
		var city = me.getCity(config.cityWidth,config.cityLabel,config.cityName,config.cityIsBlank,config.cityLabelWidth,config.type,county);
		var province = me.getProvince(config.provinceWidth,config.provinceLabel,config.provinceName,config.provinceIsBlank,config.provinceLabelWidth,config.type,city);
		me.items=[province,city,county,street];
		me.callParent([cfg]);
	}
});
//行政区域省市区街道END
Ext.define('Foss.commonSelector.linkReginCombSelector', {
	extend : 'Deppon.commonselector.LinkageContainer',
	alias : 'widget.linkregincombselector',
//	fieldLabel : '联动选择',
	type : 'N-P-C-C',// type ：N-P-C-C 国省市县 P-C-C :省市县 C-C:市县 P-C:省市
	width : 800,
	nationWidth : 150,// 国家长度
	nationLabel : '',// 国家label
	nationName : '',// 国家名称--对应name
	nationIsBlank : true,
	nationLabelWidth:null,
	provinceWidth : 150,// 省份长度
	provinceLabel : '',// 省份label
	provinceLabelWidth:null,
	provinceName : '',// 省份名称—对应name
	provinceIsBlank : true,
	cityWidth : 150,// 城市长度
	cityLabel : '',// 城市label
	cityName : '',// 城市name
	cityIsBlank : true,
	cityLabelWidth:null,
	areaWidth : 150,// 县长度
	areaLabel : '',// 县label
	areaName : '',// 县name
	areaIsBlank : true,
	areaLabelWidth:null,
	layout : 'column',
	labelWid : 20,
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			minChars : 0,
			labelSeparator : ''
		}
	},
	setReginValue : function(displayText, valueText, order) {// 0：国家的值，1：省的值，2：市的值，3：县的值
		var me = this;
		var regionObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				regionObj=me.nation;
			}else if(order == '1'){
				regionObj=me.province;
			}else if(order == '2'){
				regionObj=me.city;
			}else if(order == '3'){
				regionObj=me.county;
			}
		}
		var  key = regionObj.displayField + '', value =regionObj.valueField
				+ '';
		var m = Ext.create(regionObj.store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		regionObj.store.loadRecords([m]);
		regionObj.setValue(valueText);
	},
	nation:null,
	getNation:function(nationWidth,nationLabel,nationName,nationIsBlank,nationLabelWidth,configType,provinceObj){
		if(Ext.isEmpty(this.nation)){
			this.nation=Ext.widget('linkagecomboselector',{
				configType :configType,
				provinceObj : provinceObj,
				editable:false,
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				name : 'province',
				itemId : 'Foss_baseinfo_Nation_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.NationStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change' : function(ths, the, eOpts){
						if(ths.configType == 'N-P-C-C'){
							ths.provinceObj.setReadOnly(false);
							var provObj=ths.provinceObj.getEl();
							if(!Ext.isEmpty(provObj)){
								provObj.query('input')[0].readOnly = 'readOnly';	
							}
						} 
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				width : nationWidth,
				fieldLabel : nationLabel,
				name : nationName,
				labelWidth:nationLabelWidth,
				allowBlank : nationIsBlank,
				isPaging: true, 
				queryParam : 'cityVo.name'
			});
		}
		return this.nation;
	},
	province:null,
	getProvince:function(provinceWidth,provinceLabel,provinceName,provinceIsBlank,provinceLabelWidth,configType,cityObj){
		if(Ext.isEmpty(this.province)){
			this.province=Ext.widget('linkagecomboselector',{
				configType:configType,
				cityObj : cityObj,
				editable:false,
				itemId : 'Foss_baseinfo_Province_ID',
				eventType : ['callparent'],
				store : Ext.create('Foss.baseinfo.commonSelector.ProvinceStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change' : function(ths, the, eOpts){
						ths.cityObj.setReadOnly(false); 
						var cityObj=ths.cityObj.getEl();
						if(!Ext.isEmpty(cityObj)){
							cityObj.query('input')[0].readOnly = 'readOnly';	
						}
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				width : provinceWidth,
				fieldLabel : provinceLabel,
				name : provinceName,
				labelWidth:provinceLabelWidth,
				allowBlank : provinceIsBlank,
				isPaging: false, 
				parentParamsAndItemIds : {
					'cityVo.parentId' : 'Foss_baseinfo_Nation_ID'
				},// 此处城市不需要传入
				queryParam : 'cityVo.name'
			
			});
		}
		return this.province;
	},
	city:null,
	getCity:function(cityWidth,cityLabel,cityName,cityIsBlank,cityLabelWidth,configType,countyObj){
		if(Ext.isEmpty(this.city)){
				this.city=Ext.widget('linkagecomboselector',{
					configType:configType,
					countyObj : countyObj,
					editable:false,
					itemId : 'Foss_baseinfo_City_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Foss.baseinfo.commonSelector.CityStore'),// 从外面传入
					listeners : {
						'blur' : function(ths, the, eOpts) {
							if (Ext.isEmpty(ths.value)) {
								ths.setRawValue(null);
							}
						},
						'change' : function(ths, the, eOpts){
							ths.countyObj.setReadOnly(false);
							var countyObj=ths.countyObj.getEl();
							if(!Ext.isEmpty(countyObj)){
								countyObj.query('input')[0].readOnly = 'readOnly';	
							}
						}
					},
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					width : cityWidth,
					fieldLabel : cityLabel,
					name : cityName,
					labelWidth:cityLabelWidth,
					allowBlank : cityIsBlank,
					isPaging: false,  
					parentParamsAndItemIds : {
						'cityVo.parentId' : 'Foss_baseinfo_Province_ID'
					},// 此处城市不需要传入
					queryParam : 'cityVo.name'
			});
		}
		return this.city;
	},
	county:null,
	getCounty:function(areaWidth,areaLabel,areaNames,areaIsBlank,areaLabelWidth){
		if(Ext.isEmpty(this.county)){
			this.county=Ext.widget('linkagecomboselector',{
				store : Ext.create('Foss.baseinfo.commonSelector.AreaStore'),// 从外面传入
				listeners : {
					'blur' : function(ths, the, eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				editable:false,
				width : areaWidth,
				fieldLabel : areaLabel,
				name : areaNames,
				allowBlank : areaIsBlank,
				labelWidth:areaLabelWidth,
				isPaging: false, 
				parentParamsAndItemIds : {
					'cityVo.parentId' : 'Foss_baseinfo_City_ID'
				},// 此处区域不需要传入
				queryParam : 'cityVo.name'
				});
			}
		return  this.county;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var county = me.getCounty(config.areaWidth,config.areaLabel,config.areaName,config.areaIsBlank,config.areaLabelWidth);
		var city = me.getCity(config.cityWidth,config.cityLabel,config.cityName,config.cityIsBlank,config.cityLabelWidth,config.type,county);
		var province = me.getProvince(config.provinceWidth,config.provinceLabel,config.provinceName,config.provinceIsBlank,config.provinceLabelWidth,config.type,city);
		var nation = me.getNation(config.nationWidth,config.nationLabel,config.nationName,config.nationIsBlank,config.nationLabelWidth,config.type,province);
		me.items=[nation,province,city,county];
		if (config.type == 'N-P-C-C') {
			province.setReadOnly(true);   
			city.setReadOnly(true);   
			county.setReadOnly(true);  
			province.cls='readonlyhaveborder'; 
			city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';			
			me.items=[nation,province,city,county];
		} else if (config.type == 'P-C-C') {
			city.setReadOnly(true);   
			county.setReadOnly(true);    
			city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city,county];
		} else if (config.type == 'P-C') {
			city.setReadOnly(true);   
			city.cls='readonlyhaveborder';
			
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city];
		} else if (config.type == 'C-C') {
			county.setReadOnly(true);   
			county.cls='readonlyhaveborder';
			
			city.eventType=['focus'];
			city.parentParamsAndItemIds = null;
			me.items=[city,county];
		}
		me.callParent([cfg]);
	}
});

// 人员
Ext.define('Foss.baseinfo.commonSelector.EmployeeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'empCode',
						type : 'string'
					}, {
						name : 'empName',
						type : 'string'
					}, {
						name : 'orgCode',
						type : 'string'
					}, {
						name : 'orgName',
						type : 'string'
					}, {
						name : 'title',
						type : 'string'
					},{
						name : 'titleName',
						type : 'string'
					}, {
						name : 'enterDate',
						type : 'string'
					}, {
						name : 'department'
					},{
						name : 'yingYeName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.EmployeeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.EmployeeModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonEmp.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'employeeVo.employeeList',
					totalProperty : 'totalCount'
				}
			}
		});
//人员单选
Ext.define('Foss.commonSelector.EmployeeSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonemployeeselector',
//	fieldLabel : '人员',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'empName',// 显示名称
	valueField : 'empCode',// 值
	deptCode : null,
	parentOrgCode:null,//递归该网点下属所有网点
	title : null,//职位 
	queryParam : 'employeeVo.employeeDetail.queryParam',// 查询参数
	//showContent : '{empName}&nbsp;&nbsp;&nbsp;{department.name}',// 显示表格列
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode
		me.store = Ext.create('Foss.baseinfo.commonSelector.EmployeeStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.deptCode)){
				searchParams['employeeVo.employeeDetail.orgCode'] = config.deptCode;	
			}else if(!Ext.isEmpty(config.parentOrgCode)) {
				searchParams['employeeVo.employeeDetail.parentOrgCode'] = config.parentOrgCode;	
			}
			if(!Ext.isEmpty(config.title)){
				searchParams['employeeVo.employeeDetail.title'] = config.title;	
			}
		})
		me.callParent([cfg]);
	}
});

//人员多选
Ext.define('Foss.commonSelector.EmployeeMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonemployeemultiselector',
//	fieldLabel : '人员',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'empName',// 显示名称
	valueField : 'empCode',// 值
	deptCode : null,
	parentOrgCode:null,//递归该网点下属所有网点
	title : null,//职位
	queryParam : 'employeeVo.employeeDetail.queryParam',// 查询参数
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}',// 显示表格列 
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode
		me.store = Ext.create('Foss.baseinfo.commonSelector.EmployeeStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.deptCode)){
				searchParams['employeeVo.employeeDetail.orgCode'] = config.deptCode;	
			}else if(!Ext.isEmpty(config.parentOrgCode)) {
				searchParams['employeeVo.employeeDetail.parentOrgCode'] = config.parentOrgCode;	
			}
			if(!Ext.isEmpty(config.title)){
				searchParams['employeeVo.employeeDetail.title'] = config.title;	
			}
		})
		me.callParent([cfg]);
	}
});

// 大区Model
Ext.define('Foss.baseinfo.commonSelector.BigZoneModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'transDepartmentCode',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					}, {
						name : 'type',
						type : 'string'
					}
				    ]
		});
//大区Store
Ext.define('Foss.baseinfo.commonSelector.BigZoneStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.BigZoneModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonBigZone.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonZoneVo.commonBigZoneList',
					totalProperty : 'totalCount'
				}
			}
		});
//大区
Ext.define('Foss.commonSelector.BigZoneSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonbigzoneselector',
//	fieldLabel : '大区',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	regionType:null,//区域类型 
	management : null,//管理部门
	transDepartmentCode : null,//所属部门
	queryParam : 'commonZoneVo.commonBigZoneDto.queryParam',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.BigZoneStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.type)){
				searchParams['commonZoneVo.commonBigZoneDto.type'] = config.regionType;
			} 
			if(!Ext.isEmpty(config.management)){
				searchParams['commonZoneVo.commonBigZoneDto.management'] = config.management;
			} 
			if(!Ext.isEmpty(config.transDepartmentCode)){
				searchParams['commonZoneVo.commonBigZoneDto.transDepartmentCode'] = config.transDepartmentCode;
			} 
		});
		me.callParent([cfg]);
	}
});

// 小区Model
Ext.define('Foss.baseinfo.commonSelector.SmallZoneModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'transDepartmentCode',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					}, {
						name : 'zoneType',
						type : 'string'
					}, {
						name : 'regionType',
						type : 'string'
					}, {
						name : 'id',
						type : 'string'
					}]
		});
//小区Store
Ext.define('Foss.baseinfo.commonSelector.SmallZoneStore', {
			extend : 'Ext.data.Store',
			pageSize : 10,
			model : 'Foss.baseinfo.commonSelector.SmallZoneModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonSmallZone.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonZoneVo.commonSmallZoneList',
					totalProperty : 'totalCount'
				}
			}
		});
//小区
Ext.define('Foss.commonSelector.SmallZoneSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonsmallzoneselector',
//			fieldLabel : '小区',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionType:null,//区域类型 
			management : null,//管理部门
			parentOrgCode:null,//递归该网点下属所有网点
			queryParam : 'commonZoneVo.commonSmallZoneDto.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.SmallZoneStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.regionType)){
						searchParams['commonZoneVo.commonSmallZoneDto.regionType'] = config.regionType;
					} 
					if(!Ext.isEmpty(config.management)){
						searchParams['commonZoneVo.commonSmallZoneDto.management'] = config.management;
					} 
					if(!Ext.isEmpty(config.parentOrgCode)){
						searchParams['commonZoneVo.commonSmallZoneDto.parentOrgCode'] = config.parentOrgCode;
					} 
				});
				me.callParent([cfg]);
			}
		});
//快递收派小区Model
Ext.define('Foss.baseinfo.commonSelector.ExpressSmallZoneModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'bigzonecode',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					},  {
						name : 'regionType',
						type : 'string'
					}, {
						name : 'id',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
//快递收派小区Store
Ext.define('Foss.baseinfo.commonSelector.ExpressSmallZoneStore', {
			extend : 'Ext.data.Store',
			pageSize : 10,
			model : 'Foss.baseinfo.commonSelector.ExpressSmallZoneModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonExpressSmallZone.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonExpressZoneVo.commonExpressSmallZoneList',
					totalProperty : 'totalCount'
				}
			}
		});
//快递收派小区（快递员排班专用）
Ext.define('Foss.commonSelector.ExpressSmallZoneSelector', {
			extend:'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.expressSmallZoneSelector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			regionType:null,
			bigzonecode:null,
			queryParam : 'commonExpressZoneVo.commonExpressSmallZoneEntity.regionName',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);    
				me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressSmallZoneStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					// 传递的部门类型是多种
					var types = null;
					if (!Ext.isEmpty(config.types)) {
						types = config.types.split(',');
						searchParams['commonExpressZoneVo.commonExpressSmallZoneEntity.types'] = types;
					}
					if (!Ext.isEmpty(config.regionCode)) {
						searchParams['commonExpressZoneVo.commonExpressSmallZoneEntity.regionCode'] = config.regionCode;
					}
					if (!Ext.isEmpty(config.regionName)) {
						searchParams['commonExpressZoneVo.commonExpressSmallZoneEntity.regionName'] = config.regionName;
					}
					if (!Ext.isEmpty(config.management)) {
						searchParams['commonExpressZoneVo.commonExpressSmallZoneEntity.management'] = config.management;
					}
					if (!Ext.isEmpty(config.active)) {
						searchParams['commonExpressZoneVo.commonExpressSmallZoneEntity.active'] = config.active;
					}
				})
				me.callParent([cfg]);
			}
});
//大小区 store
Ext.define('Foss.baseinfo.commonSelector.ServiceZoneStore', {
			extend : 'Ext.data.Store',
			pageSize : 10,
			model : 'Foss.baseinfo.commonSelector.SmallZoneModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonAllZone.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonZoneVo.commonAllZoneList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.SerivceZoneSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonservicezoneselector',
//			fieldLabel : '大小区',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionType:null,//区域类型 
			management : null,//管理部门
			queryParam : 'commonZoneVo.commonAllZoneDto.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}&nbsp;&nbsp;&nbsp;{zoneType}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.ServiceZoneStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.regionType)){
						searchParams['commonZoneVo.commonAllZoneDto.regionType'] = config.regionType;
					} 
					if(!Ext.isEmpty(config.management)){
						searchParams['commonZoneVo.commonAllZoneDto.management'] = config.management;
					} 
				});
				me.callParent([cfg]);
			}
		});		
		
// 公司司机
Ext.define('Foss.baseinfo.commonSelector.OwnDriverModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'empCode',
						type : 'string'
					}, {
						name : 'empName',
						type : 'string'
					}, {
						name : 'empPhone',
						type : 'string'
					}, {
						name : 'licenseType',
						type : 'string'
					}, {
						name : 'driverType',
						type : 'string'
					}, {
						name : 'orgName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.OwnDriverStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OwnDriverModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonOwnDriver.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'driverVo.driverEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.OwnDriverSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonowndriverselector',
//	fieldLabel : '公司司机',
	displayField : 'empName',// 显示名称
	valueField : 'empCode',// 值
	deptCode : null,
	parentOrgCode:null,//上级车队组织
	waybillFlag:null,//是否是集中接送区域 如果设置值为Y,N 
	fleetType:null,//车队组类型
	queryParam : 'driverVo.driverEntity.empName',// 查询参数
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}&nbsp;&nbsp;&nbsp;{orgName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.baseinfo.commonSelector.OwnDriverStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.parentOrgCode)){
				searchParams['driverVo.driverEntity.orgId'] = me.deptCode;
			}
			if(!Ext.isEmpty(config.parentOrgCode)){
				searchParams['driverVo.driverEntity.parentOrgCode'] = me.parentOrgCode;
			}
			if(!Ext.isEmpty(config.waybillFlag)){
				searchParams['driverVo.driverEntity.waybillFlag'] = me.waybillFlag;
			}
			if(!Ext.isEmpty(config.fleetType)){
			   var fleetTypes = config.fleetType.split(',');
			   searchParams['driverVo.driverEntity.fleetType'] = fleetTypes;
			}
		});
		me.callParent([cfg]);
	}
});

//司机
Ext.define('Foss.baseinfo.commonSelector.DriverStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OwnDriverModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAllOwnDriver.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'driverVo.driverDtos',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.DriverSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commondriverselector',
//	fieldLabel : '公司司机',
	displayField : 'empName',// 显示名称
	valueField : 'empCode',// 值
	parentOrgCode:null,//上级车队组织
	waybillFlag:null,//是否是集中接送区域  如果设置值为Y,N
	fleetType:null,//车队组类型
	deptCode : null,//组织编码
	status:'Y',//外请车审核状态 "Y", "N"
	queryParam : 'driverVo.driverEntity.empName',// 查询参数
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}&nbsp;&nbsp;&nbsp;{orgName}&nbsp;&nbsp;&nbsp;{driverType}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.baseinfo.commonSelector.DriverStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			if(!Ext.isEmpty(config.status)){
				searchParams['driverVo.driverEntity.status'] = config.status;
			}else{
				searchParams['driverVo.driverEntity.status'] = me.status;
			}
			
			if(!Ext.isEmpty(config.parentOrgCode)){
				searchParams['driverVo.driverEntity.parentOrgCode'] = config.parentOrgCode;
			}
			if(!Ext.isEmpty(config.waybillFlag)){
				searchParams['driverVo.driverEntity.waybillFlag'] = config.waybillFlag;
			}
			if(!Ext.isEmpty(config.fleetType)){
			   var fleetTypes = config.fleetType.split(',');
			   searchParams['driverVo.driverEntity.fleetType'] = fleetTypes;
			}
			searchParams['driverVo.driverEntity.orgId'] = me.deptCode;
		});
		me.callParent([cfg]);
	}
});

// 公司车
Ext.define('Foss.baseinfo.commonSelector.OwnTruckModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'orgId',
						type : 'string'
					},{
						name : 'orgName',
						type : 'string'
					},{
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'brand',
						type : 'string'
					}, {
						name : 'tailBoard',
						type : 'string'
					}, {
						name : 'deadLoad',
						type : 'string'
					}, {
						name : 'containerCode',
						type : 'string'  
					}, {
						name : 'truckType',
						type : 'string'
					}, {
						name : 'reginName',
						type : 'string'
					}, {
						name : 'isTrailer',
						type : 'string'
					}, {
						name : 'isOwnTruck',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.OwnTruckStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OwnTruckModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonOwnTractors.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'truckVo.ownTrucks',
					totalProperty : 'totalCount'
				}
			}
		});
//公司车 或 货柜号
Ext.define('Foss.commonSelector.OwnTruckSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonowntruckselector',
//			fieldLabel : '公司车 或 货柜号',
			displayField : 'vehicleNo',// 显示名称
			valueField : 'vehicleNo',// 值
			deptCode : null,
			deptCodes : null,
			parentOrgCode:null,//递归该网点下属所有网点
			vehicleTypes:null,//车辆类型多个以“,”隔开
			queryParam : 'truckVo.truck.vehicleNo',// 查询参数
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{brand}',// 显示表格列
			queryAllFlag:false,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.OwnTruckStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					// 传递多个部门
					var deptCodes = config.deptCodes;
					if (!Ext.isEmpty(deptCodes)) {
						var deptCodeLst = deptCodes.split(',');
						searchParams['truckVo.truck.orgIds'] = deptCodeLst;
					}
					if(!Ext.isEmpty(config.deptCode)){
						searchParams['truckVo.truck.orgId'] = config.deptCode;
					}
					if(!Ext.isEmpty(config.parentOrgCode)){
						searchParams['truckVo.truck.parentOrgCode'] = config.parentOrgCode;
					}
					if (!Ext.isEmpty(config.vehicleTypes)) {
						var vehicleTypes = config.vehicleTypes.split(',');
						searchParams['truckVo.truck.vehicleTypes'] = vehicleTypes;
					}
				});
				me.callParent([cfg]);
			}
		});

//车辆store
Ext.define('Foss.baseinfo.commonSelector.TruckStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OwnTruckModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAllOwnTractors.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'truckVo.truckDtos',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.TruckSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commontruckselector',
//			fieldLabel : '公司车',
			displayField : 'vehicleNo',// 显示名称
			valueField : 'vehicleNo',// 值
			deptCode : null,
			//expressTruck:null,//查询快递车配置“Y”
			status : 'Y',//外请车审核状态 “Y” , "N"
			loopOrgCode : null,//配置查询组织下所有公司车以“,”隔开
			reginName:null,//小区名称
			isOwnTruck:null,//是否只限制公司车
			queryParam : 'truckVo.truck.vehicleNo',// 查询参数
			topFleetOrgCode : null,//查询该组织下关联的顶级车队
			vehicleTypes:null,//车辆类型多个以“,”隔开
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{brand}&nbsp;&nbsp;&nbsp;{truckType}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				}
				me.deptCode = config.deptCode;
				me.loopOrgCode = config.loopOrgCode;
				me.topFleetOrgCode = config.topFleetOrgCode;
				me.reginName = config.reginName;
				me.store = Ext.create('Foss.baseinfo.commonSelector.TruckStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if (!Ext.isEmpty(config.vehicleTypes)) {
						var vehicleTypes = config.vehicleTypes.split(',');
						searchParams['truckVo.truck.vehicleTypes'] = vehicleTypes;
					}
					if (!Ext.isEmpty(config.status)) {
						searchParams['truckVo.truck.status'] = config.status;
					}else{
						searchParams['truckVo.truck.status'] = me.status;
					}
					searchParams['truckVo.truck.orgId'] = me.deptCode;
					if (!Ext.isEmpty(config.loopOrgCode)) {
						var loopOrgCodes = config.loopOrgCode.split(',');		
						searchParams['truckVo.truck.loopOrgCode'] = loopOrgCodes;
					}
					if (!Ext.isEmpty(config.topFleetOrgCode)) {
						searchParams['truckVo.truck.topFleetOrgCode'] = me.topFleetOrgCode;
					}
					if (!Ext.isEmpty(config.reginName)) {
						searchParams['truckVo.truck.reginName'] = me.reginName;
					}
					if (!Ext.isEmpty(config.isOwnTruck)) {
						searchParams['truckVo.truck.isOwnTruck'] = me.isOwnTruck;
					}
//					if (!Ext.isEmpty(config.expressTruck)) {
//						searchParams['truckVo.truck.expressTruck'] = me.expressTruck;
//					}
				});
				me.callParent([cfg]);
			}
		});		
		
// 价格时效
Ext.define('Foss.baseinfo.commonSelector.PriceRegionModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'regionType',
						type : 'string'
					}, {
						name : 'proCode',
						type : 'string'
					}, {
						name : 'proName',
						type : 'string'
					}, {
						name : 'proCode',
						type : 'string'
					}, {
						name : 'cityCode',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'countyCode',
						type : 'string'
					}, {
						name : 'countyName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.PriceRegionStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPriceRegion.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'priceRegionEntityVo.priceRegionEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.PriceRegionSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonpriceregionselector',
			//fieldLabel : '价格区域',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
			airPriceFlag :null,//默认不配为汽运价格，配置Y为空运价格
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.PriceRegionStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if (!Ext.isEmpty(config.airPriceFlag)) {
						searchParams['priceRegionEntityVo.priceRegionEntity.airPriceFlag'] = config.airPriceFlag;
					} 
				});
				me.callParent([cfg]);
			}
		});
/**
 * 大票价格区域store（实体共用价格时效的）
 */
Ext.define('Foss.baseinfo.commonSelector.PriceRegionBigTicketStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPriceRegionBigTicket.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 大票价格区域 始发区域
 */
Ext.define('Foss.commonSelector.PriceRegionBigTicketSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonpriceregionbigticketselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	airPriceFlag :null,//默认不配为汽运价格，配置Y为空运价格
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.PriceRegionBigTicketStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.airPriceFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.airPriceFlag'] = config.airPriceFlag;
			} 
		});
		me.callParent([cfg]);
	}
});

/**
 * 首续重价格出发区域（实体共用价格时效的）
 */
Ext.define('Foss.baseinfo.commonSelector.PriceRegionEcGoodsStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPriceRegionEcGoods.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 首续重价格始发区域
 */
Ext.define('Foss.commonSelector.PriceRegionEcGoodsSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonpriceregionecgoodsselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	airPriceFlag :null,//默认不配为汽运价格，配置Y为空运价格
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.PriceRegionEcGoodsStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.airPriceFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.airPriceFlag'] = config.airPriceFlag;
			} 
		});
		me.callParent([cfg]);
	}
});

/**
 * 大票目的区域store
 */
Ext.define('Foss.baseinfo.commonSelector.AllPriceRegionBigTicketStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPriceAllRegionBigTicket.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 公共组件-大票目的区域
 */
Ext.define('Foss.commonSelector.AllPriceRegionBigTicketSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonallpriceregionBigTicketselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	priceRegionFlag :null,//输入价格区域类型
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.AllPriceRegionBigTicketStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.priceRegionFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.priceRegionFlag'] = config.priceRegionFlag;
			} 
		});
		me.callParent([cfg]);
	}
});
/**
 * 首续重目的区域store
 */
Ext.define('Foss.baseinfo.commonSelector.AllPriceRegionEcGoodsStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPriceAllRegionEcGoods.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 公共组件-首续重目的区域
 */
Ext.define('Foss.commonSelector.AllPriceRegionEcGoodsSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonallpriceregionEcGoodsselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	priceRegionFlag :null,//输入价格区域类型
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.AllPriceRegionEcGoodsStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.priceRegionFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.priceRegionFlag'] = config.priceRegionFlag;
			} 
		});
		me.callParent([cfg]);
	}
});

/**
 * 快递价格区Store
 */
Ext.define('Foss.baseinfo.commonSelector.ExpressPriceRegionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonExpressPriceRegion.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 快递价格区域始发区域
 */
Ext.define('Foss.commonSelector.ExpressPriceRegionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonexpresspriceregionselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	airPriceFlag :null,//默认不配为汽运价格，配置Y为空运价格
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressPriceRegionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.airPriceFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.airPriceFlag'] = config.airPriceFlag;
			} 
		});
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.commonSelector.PerscriptionRegionExpressStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPerscriptionRegionExpress.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.PerscriptionRegionExpressSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonperscriptionregionexpressselector',
//	fieldLabel : '快递时效区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.regionName',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.PerscriptionRegionExpressStore');
		me.callParent([cfg]);
	}
});
//查询全部价格区域
Ext.define('Foss.baseinfo.commonSelector.AllPriceRegionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonPriceAllRegion.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'priceRegionEntityVo.priceRegionEntities',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.AllPriceRegionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonallpriceregionselector',
	//fieldLabel : '价格区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.queryParam',// 查询参数
	priceRegionFlag :null,//输入价格区域类型
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.AllPriceRegionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.priceRegionFlag)) {
				searchParams['priceRegionEntityVo.priceRegionEntity.priceRegionFlag'] = config.priceRegionFlag;
			} 
		});
		me.callParent([cfg]);
	}
});


Ext.define('Foss.baseinfo.commonSelector.PerscriptionRegionStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.PriceRegionModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPerscriptionRegion.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'priceRegionEntityVo.priceRegionEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.PerscriptionRegionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonperscriptionregionselector',
//	fieldLabel : '时效区域',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	queryParam : 'priceRegionEntityVo.priceRegionEntity.regionName',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.PerscriptionRegionStore');
		me.callParent([cfg]);
	}
});

// 产品条目
Ext.define('Foss.baseinfo.commonSelector.ProductItemModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'tSrvProductCode',
						type : 'string'
					}, {
						name : 'tSrvGoodstypeCode',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'code',
						type : 'string'
					}, {
						name : 'tSrvGoodstypeId',
						type : 'string'
					}, {
						name : 'proName',
						type : 'string'
					}, {
						name : 'tSrvProductId',
						type : 'string'
					}, {
						name : 'description',
						type : 'string'
					}, {
						name : 'feeBottom',
						type : 'string'
					}, {
						name : 'beginTime',
						type : 'date'
					}, {
						name : 'endTime',
						type : 'date'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.ProductItemStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ProductItemModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonProductItem.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'productItemVo.productItemEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.ProductItemSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonproductitemselector',
//			fieldLabel : '产品条目',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			queryParam : 'productItemVo.productItemEntity.name',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.ProductItemStore');
				me.callParent([cfg]);
			}
		});

// 公司所有客户
Ext.define('Foss.baseinfo.commonSelector.CustomerModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					}, {
						name : 'cusCode',
						type : 'string'
					}, {
						name : 'creditAmount',
						type : 'int'
					}, {
						name : 'type',
						type : 'string'
					}, {
						name : 'unifiedCode',
						type : 'string'
					}, {
						name : 'degree',
						type : 'string'
					}, {
						name : 'provName',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'simpleName',
						type : 'date'
					}, {
						name : 'isDelete',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
//公司客户Store
Ext.define('Foss.baseinfo.commonSelector.CustomerStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CustomerModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonCustomerInfo.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'customerVo.customerList',
					totalProperty : 'totalCount'
				}
			}
		});
//公司客户
Ext.define('Foss.commonSelector.CustomerSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commoncustomerselector',
//	fieldLabel : '公司客户',
	displayField : 'name',// 显示名称
	valueField : 'cusCode',// 值
	isDelete:null,//是否删除
	active:null,//是否启用
	queryParam : 'customerVo.customerEntity.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{cusCode}',// 显示表格列
	contcatFlag :null,//是否要按手机号查询,有就设”Y“无就不设属性
	singlePeopleFlag : null,//是否同时也要查散客信息，有就设”Y“无不就设该属性 
	queryAllFlag:false,
	all:null,//如果为true则查全部。
	validValueLength:'2',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.deptCode = config.deptCode;
		me.store = Ext.create('Foss.baseinfo.commonSelector.CustomerStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.contcatFlag)) {
				searchParams['customerVo.customerEntity.contcatFlag'] = config.contcatFlag;
			}
			if (!Ext.isEmpty(config.singlePeopleFlag)) {
				searchParams['customerVo.customerEntity.singlePeopleFlag'] = config.singlePeopleFlag;
			}
			if (!Ext.isEmpty(config.all)) {
				searchParams['customerVo.customerEntity.all'] = config.all;
			}
			
			searchParams['customerVo.customerEntity.unifiedCode'] = me.deptCode;
		});
		
		me.callParent([cfg]);
	}
});

// 营业部Model
Ext.define('Foss.baseinfo.commonSelector.SaleDepartmentModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					}, {
						name : 'code',
						type : 'string'
					}, {
						name : 'pinyin',
						type : 'int'
					}, {
						name : 'leave',
						type : 'string'
					}, {
						name : 'arrive',
						type : 'string'
					}, {
						name : 'station',
						type : 'string'
					}]
		});

//营业部(过滤虚拟营业部和快递点部)Store
Ext.define('Foss.baseinfo.commonSelector.SaleDepartFilterStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.SaleDepartmentModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonSaleDepartFilter.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'saleDepartmentVo.departmentEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//营业部(过滤虚拟营业部和快递点部)公共选择器
Ext.define('Foss.commonSelector.SaleDepartFilterselector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonsaleDepartFilterselector',
			//fieldLabel : '营业部',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			queryParam : 'saleDepartmentVo.departmentEntity.name',// 查询参数
			leave : null,//可出发
			arrive : null,//可到达
			satelliteDept:null,//是否卫星点部
			canExpressPickupToDoor : null,//可快递接货
			canExpressDelivery : null, // 可快递送货
			isLeagueSaleDept: null,//是否加盟网点
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.SaleDepartFilterStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.leave)){
						searchParams['saleDepartmentVo.departmentEntity.leave'] = config.leave;
					}
					if(!Ext.isEmpty(config.arrive)){
						searchParams['saleDepartmentVo.departmentEntity.arrive'] = config.arrive;
					}
					if(!Ext.isEmpty(config.canExpressPickupToDoor)){
						searchParams['saleDepartmentVo.departmentEntity.canExpressPickupToDoor'] = config.canExpressPickupToDoor;
					}
					if(!Ext.isEmpty(config.canExpressDelivery)){
						searchParams['saleDepartmentVo.departmentEntity.canExpressDelivery'] = config.canExpressDelivery;
					}
					if(!Ext.isEmpty(config.satelliteDept)){
						searchParams['saleDepartmentVo.departmentEntity.satelliteDept'] = config.satelliteDept;
					}
					if(!Ext.isEmpty(config.isLeagueSaleDept)){
						searchParams['saleDepartmentVo.departmentEntity.isLeagueSaleDept'] = config.isLeagueSaleDept;
					}
				});
				me.callParent([cfg]);
			}
		});


//营业部Store
Ext.define('Foss.baseinfo.commonSelector.SaleDepartmentStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.SaleDepartmentModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonSaleDepartment.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'saleDepartmentVo.departmentEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//营业部公共选择器
Ext.define('Foss.commonSelector.SaleDepartmentSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonsaledepartmentselector',
			//fieldLabel : '营业部',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			queryParam : 'saleDepartmentVo.departmentEntity.name',// 查询参数
			leave : null,//可出发
			arrive : null,//可到达
			satelliteDept:null,//是否卫星点部
			canExpressPickupToDoor : null,//可快递接货
			canExpressDelivery : null, // 可快递送货
			isLeagueSaleDept: null,//是否加盟网点
			delivery:null, // 可派送
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.SaleDepartmentStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.leave)){
						searchParams['saleDepartmentVo.departmentEntity.leave'] = config.leave;
					}
					if(!Ext.isEmpty(config.arrive)){
						searchParams['saleDepartmentVo.departmentEntity.arrive'] = config.arrive;
					}
					if(!Ext.isEmpty(config.canExpressPickupToDoor)){
						searchParams['saleDepartmentVo.departmentEntity.canExpressPickupToDoor'] = config.canExpressPickupToDoor;
					}
					if(!Ext.isEmpty(config.canExpressDelivery)){
						searchParams['saleDepartmentVo.departmentEntity.canExpressDelivery'] = config.canExpressDelivery;
					}
					if(!Ext.isEmpty(config.satelliteDept)){
						searchParams['saleDepartmentVo.departmentEntity.satelliteDept'] = config.satelliteDept;
					}
					if(!Ext.isEmpty(config.isLeagueSaleDept)){
						searchParams['saleDepartmentVo.departmentEntity.isLeagueSaleDept'] = config.isLeagueSaleDept;
					}
					if(!Ext.isEmpty(config.delivery)){
						searchParams['saleDepartmentVo.departmentEntity.delivery'] = config.delivery;
					}
				});
				me.callParent([cfg]);
			}
		});

// 车队对应营业部
Ext.define('Foss.baseinfo.commonSelector.MotorcadeSaleDeptModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'salesdeptCode',
						type : 'string'
					}, {
						name : 'motorcadeCode',
						type : 'int'
					}, {
						name : 'salesdeptName',
						type : 'string'
					}, {
						name : 'motorcadeName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.MotorcadeSaleDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.MotorcadeSaleDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonMotorcadeSaleDept.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'salesMotorcadeEntities',
					totalProperty : 'totalCount'
				}
			}
		});

//车队营业部
Ext.define('Foss.commonSelector.MotorcadeSaleDeptSelector', {
			extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
			alias : 'widget.commonmotorcadesaledeptselector',
//			fieldLabel : '车队营业部',
			delimiter : ' ',
//			width : 300,
			listWidth : 200,// 设置下拉框宽度
			motorcadeCode : null,
			store : null,
			minChars : 0,
			displayField : 'salesdeptName',// 显示名称
			valueField : 'salesdeptCode',// 值
			queryParam : 'salesMotorcadeEntity.salesdeptName',// 查询参数
			showContent : '{salesdeptName}&nbsp;&nbsp;&nbsp;{salesdeptCode}',// 显示表格列
			isPaging : true,// 分页
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.MotorcadeSaleDeptStore');
				me.motorcadeCode = config.motorcadeCode;
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					searchParams['salesMotorcadeEntity.motorcadeCode'] = me.motorcadeCode;
				});
				me.callParent([cfg]);
			}
		});


// 车队对应营业区
Ext.define('Foss.baseinfo.commonSelector.MotorcadeSalesAreaModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'motorcadeCode',
						type : 'string'
					}, {
						name : 'motorcadeName',
						type : 'int'
					}, {
						name : 'salesareaCode',
						type : 'string'
					}, {
						name : 'salesareaName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.MotorcadeSalesAreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.MotorcadeSalesAreaModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonMotorcadeSalesArea.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'areaEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.MotorcadeSalesAreaSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	alias : 'widget.commonmotorcadesalesareaselector',
//	fieldLabel : '车队营业区',
	delimiter : ' ',
//	width : 300,
	minChars : 0,
	listWidth : 200,// 设置下拉框宽度
	motorcadeCode : null,
	store : null,
	displayField : 'salesareaName',// 显示名称
	valueField : 'salesareaCode',// 值
	queryParam : 'areaEntity.salesareaName',// 查询参数
	showContent : '{salesareaName}&nbsp;&nbsp;&nbsp;{salesareaCode}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.MotorcadeSalesAreaStore');
				me.motorcadeCode = config.motorcadeCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['areaEntity.motorcadeCode'] = me.motorcadeCode;
				});
		me.callParent([cfg]);
	}
});






// 车队对应行政区域
Ext.define('Foss.baseinfo.commonSelector.MotorcadeDistrictModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'motorcadeCode',
						type : 'string'
					}, {
						name : 'motorcadeName',
						type : 'string'
					}, {
						name : 'districtName',
						type : 'string'
					}, {
						name : 'districtCode',
						type : 'string'
					}, {
						name : 'salesareaName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'parentDistrictName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.MotorcadeDistrictStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.MotorcadeDistrictModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonMotorcadeDistrict.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'motorcadeDistrictVo.motorcadeServeDistrictEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.MotorcadeDistrictSelector', {
	extend : 'Deppon.commonselector.DynamicMultiSelectComboSelector',
	alias : 'widget.commonmotorcadedistrictselector',
//	fieldLabel : '车队行政区域',
	delimiter : ' ',
	listWidth : 200,// 设置下拉框宽度
	width : 300,
	minChars : 0,
	motorcadeCode : null,
	store : null,
	displayField : 'districtName',// 显示名称
	valueField : 'districtCode',// 值
	queryParam : 'motorcadeDistrictVo.motorcadeServeDistrictEntity.districtName',// 查询参数
	showContent : '{districtName}&nbsp;&nbsp;&nbsp;{districtCode}&nbsp;&nbsp;&nbsp;{motorcadeName}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.MotorcadeDistrictStore');
				me.motorcadeCode = config.motorcadeCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['motorcadeDistrictVo.motorcadeServeDistrictEntity.motorcadeCode'] = me.motorcadeCode;
		});
		me.callParent([cfg]);
	}
});

// 代理公司
Ext.define('Foss.baseinfo.commonSelector.AgencyCompanyModel', {
	extend : 'Ext.data.Model',
	fields : [// 代理公司编码
	  {name:'agentCompanyCode',type:'string'},
	  // 管理部门
	  {name:'management',type:'string'},
	   // 管理部门名称（扩展）
	  {name:'managementName',type:'string'},
	  // 代理公司名称
	  {name:'agentCompanyName',type:'string'},
	  // 代理简称
	  {name:'simplename',type:'string'},
	  // 省份
	  {name:'provCode',type:'string'},
	  // 省份名称
	  {name:'provName',type:'string'},
	  // 城市
	  {name:'cityCode',type:'string'},
	  // 城市名称
	  {name:'cityName',type:'string'},
	  // 联系地址
	  {name:'contactAddress',type:'string'},
	  // 联系电话
	  {name:'contactPhone',type:'string'},
	  // 联系人
	  {name:'contact',type:'string'},
	  // 联系人电话
	  {name:'mobilePhone',type:'string'},
	  // 代理公司类别
	  {name:'agentCompanySort',type:'string'},
	  // 备注
	  {name:'notes',type:'string'},
	  // 是否启用
	  {name:'active',type:'string'},
	  // 虚拟编码
	  {name:'virtualCode',type:'string'}
	 ]
});
Ext.define('Foss.baseinfo.commonSelector.AirAgencyCompanyStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirAgencyCompany.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'businessPartnerEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirAgencyCompanySelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairagencycompanyselector',
//			fieldLabel : '空运代理公司',
			displayField : 'agentCompanyName',// 显示名称
			valueField : 'agentCompanyCode',// 值
			queryParam : 'businessPartnerEntity.agentCompanyName',// 查询参数
			showContent : '{agentCompanyName}&nbsp;&nbsp;&nbsp;{agentCompanyCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirAgencyCompanyStore');
				me.callParent([cfg]);
			}
		});

// 偏线代理公司
Ext.define('Foss.baseinfo.commonSelector.VehAgencyCompStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AgencyCompanyModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonVehAgencyComp.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'businessPartnerEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.VehAgencyCompSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonvehagencycompselector',
//			fieldLabel : '偏线代理公司',
			displayField : 'agentCompanyName',// 显示名称
			valueField : 'agentCompanyCode',// 值
			all:null,//如果为true则查询全部
			queryParam : 'businessPartnerEntity.agentCompanyName',// 查询参数
			showContent : '{agentCompanyName}&nbsp;&nbsp;&nbsp;{agentCompanyCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.VehAgencyCompStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}										
					if (!Ext.isEmpty(config.all)) {
						searchParams['businessPartnerEntity.all'] = config.all;
					}
		});
				me.callParent([cfg]);
			}
		});
// 代理网点
Ext.define('Foss.baseinfo.commonSelector.AgencyDeptModel', {
	extend : 'Ext.data.Model',
	fields : [// 代理网点编码
      {name:'agentDeptCode',type:'string'},
      // 代理网点名称
      {name:'agentDeptName',type:'string'},
      // 代理公司
      {name:'agentCompany',type:'string'},
      // 代理公司名称（扩展）
      {name:'agentCompanyName',type:'string'},
      // 管理部门
      {name:'management',type:'string'},
      // 管理部门名称（扩展）
      {name:'managementName',type:'string'},
      // 代理简称
      {name:'simplename',type:'string'},
      // 省份编码
      {name:'provCode',type:'string'},
      // 省份名称（扩展）
      {name:'provName',type:'string'},
      // 正单联系电话
      {name:'airWaybillTel',type:'string'},
      // 正单开单名称
      {name:'airWaybillName',type:'string'},
      // 城市编码
      {name:'cityCode',type:'string'},
      // 城市名称（扩展）
      {name:'cityName',type:'string'},
      // 联系人
      {name:'contact',type:'string'},
      // 区县编码
      {name:'countyCode',type:'string'},
      // 区县名称（扩展）
      {name:'countyName',type:'string'},
      // 网点地址
      {name:'address',type:'string'},
      // 是否可自提
      {name:'pickupSelf',type:'string'},
      // 是否送货上门
      {name:'pickupToDoor',type:'string'},
      // 是否支持返回签单
      {name:'returnBill',type:'string'},
      // 是否支持货到付款
      {name:'arriveCharge',type:'string'},
      // 是否支持代收货款
      {name:'helpCharge',type:'string'},
      // 自提区域
      {name:'pickupArea',type:'string'},
      // 派送区域
      {name:'deliverArea',type:'string'},
      // 可出发
      {name:'leave',type:'string'},
      // 可中转
      {name:'transfer',type:'string'},
      // 可到达
      {name:'arrive',type:'string'},
      // 联系电话
      {name:'contactPhone',type:'string'},
      // 联系人电话
      {name:'mobilePhone',type:'string'},
      // 备注
      {name:'notes',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
      // 虚拟编码
      {name:'virtualCode',type:'string'},
      // 网点类型：空运代理：KY 偏线代理：PX
      {name:'branchtype',type:'string'},
      // 提货网点编码【打印使用】
      {name:'stationNumber',type:'string'},
      //是否是机场
      {name:'isAirport',type:'string'},
      //网点服务坐标编号
      {name:'deptCoordinate',type:'string'},
      //派送区域服务坐标
      {name:'deliveryCoordinate',type:'string'}]
});
Ext.define('Foss.baseinfo.commonSelector.AirAgencyDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirAgencyDept.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'outerBranchEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirAgencyDeptSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairagencydeptselector',
//			fieldLabel : '空运代理网点',
			displayField : 'agentDeptName',// 显示名称
			valueField : 'agentDeptCode',// 值
			queryParam : 'outerBranchEntity.agentDeptName',// 查询参数
			showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirAgencyDeptStore');
				me.callParent([cfg]);
			}
		});

// 偏线代理网点
Ext.define('Foss.baseinfo.commonSelector.VehAgencyDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AgencyDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonVehAgencyDept.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'outerBranchEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.VehAgencyDeptSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonvehagencydeptselector',
//			fieldLabel : '偏线代理网点',
			displayField : 'agentDeptName',// 显示名称
			valueField : 'agentDeptCode',// 值
			queryParam : 'outerBranchEntity.agentDeptName',// 查询参数
			showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.VehAgencyDeptStore');
				me.callParent([cfg]);
			}
		});

//代理人
Ext.define('Foss.baseinfo.commonSelector.CommonAgentDeptModel', {
	extend : 'Ext.data.Model',
	fields : [
      // 配载部门.
      {name:'assemblyDeptId',type:'string'},
      // 航空公司代码
      {name:'airlinesCode',type:'string'},
      // 代理人编码.
      {name:'agentCode',type:'string'},
      // 代理人名称
      {name:'agentName',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
	{name:'active',type:'string'}
 
      ]
});
Ext.define('Foss.baseinfo.commonSelector.AgentDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonAgentDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAgentCondition.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'agentVo.agentEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AgentDeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonagentselector',
	agentCode : null,
	assemblyDeptId : null,
	airlinesCode : null,
	displayField : 'agentName',// 显示名称
	valueField : 'agentCode',// 值
	queryParam : 'agentVo.agentEntity.agentName',// 查询参数
	showContent : '{agentName}&nbsp;&nbsp;&nbsp;{agentCode}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AgentDeptStore');
				me.agentCode = config.agentCode;
				me.assemblyDeptId = config.assemblyDeptId;
				me.airlinesCode = config.airlinesCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['agentVo.agentEntity.agentCode'] = me.agentCode;
					searchParams['agentVo.agentEntity.assemblyDeptId'] = me.assemblyDeptId;
					searchParams['agentVo.agentEntity.airlinesCode'] = me.airlinesCode;
				});
		me.callParent([cfg]);
	}
});



//代理
Ext.define('Foss.baseinfo.commonSelector.CommonAllAgentDeptModel', {
	extend : 'Ext.data.Model',
	fields : [
      // 代理人名称
      {name:'agentName',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
      // 代理编码
      {name:'agentCode',type:'string'},
	{name:'agentCode',type:'string'}
 
      ]
});
Ext.define('Foss.baseinfo.commonSelector.AllAgentDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonAllAgentDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAgent.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonAllAgentVo.allAgentEntitys',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AllAgentDeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonallagentselector',
	displayField : 'agentName',// 显示名称
	valueField : 'agentCode',// 值
	agentCode : null,
	active : null,
	all:null,//如果为true则查全部。
	queryParam : 'commonAllAgentVo.allAgentEntity.agentName',// 查询参数
	showContent : '{agentName}&nbsp;&nbsp;&nbsp;{agentCode}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AllAgentDeptStore');
		me.agentCode = config.agentCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['commonAllAgentVo.allAgentEntity.agentCode'] = me.agentCode;
					if (!Ext.isEmpty(config.active)) {
						searchParams['commonAllAgentVo.allAgentEntity.active'] = config.active
					}
					if (!Ext.isEmpty(config.all)) {
						searchParams['commonAllAgentVo.allAgentEntity.all'] = config.all;
					}
		});
		me.callParent([cfg]);
	}
});


//代理
Ext.define('Foss.baseinfo.commonSelector.CommonAirAgentModel', {
	extend : 'Ext.data.Model',
	fields : [
      // 代理人名称
      {name:'agentName',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
      // 代理编码
      {name:'agentCode',type:'string'},
	 {name:'agentCode',type:'string'}
 
      ]
});
Ext.define('Foss.baseinfo.commonSelector.AirAgentStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonAirAgentModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirAgent.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'airAgentVo.airAgentEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirAgentSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonairagentselector',
	displayField : 'agentName',// 显示名称
	valueField : 'agentCode',// 值
	agentCode : null,
	active : null,
	all:null,//如果为true则查全部。
	queryParam : 'airAgentVo.airAgentEntity.agentName',// 查询参数
	showContent : '{agentName}&nbsp;&nbsp;&nbsp;{agentCode}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AirAgentStore');
		me.agentCode = config.agentCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['airAgentVo.airAgentEntity.agentCode'] = me.agentCode;
					if (!Ext.isEmpty(config.active)) {
						searchParams['airAgentVo.airAgentEntity.active'] = config.active
					}
					
		});
		me.callParent([cfg]);
	}
});



//角色
Ext.define('Foss.baseinfo.commonSelector.CommonRoleModel', {
	extend : 'Ext.data.Model',
	fields : [
      // 数据版本号.
      {name:'versionNo',type:'string'},
      // 角色名称
      {name:'name',type:'string'},
      // 角色编码
      {name:'code',type:'string'},
      // 角色描述
      {name:'notes',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
	{name:'active',type:'string'}
 
      ]
});
Ext.define('Foss.baseinfo.commonSelector.RoleStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonRoleModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonRole.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonRoleVo.commonRoleEntitys',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.RoleSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonroleselector',
	code : null,
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'commonRoleVo.commonRoleEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.RoleStore');
				me.code = config.code;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					searchParams['commonRoleVo.commonRoleEntity.name'] = me.name;
				});
		me.callParent([cfg]);
	}
});






// 出发站点
Ext.define('Foss.baseinfo.commonSelector.OgirFreightRouteLineStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonFreightRouteLine.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'orgs',
					totalProperty : 'totalCount'
				}
			}
		});
//始发站点
Ext.define('Foss.commonSelector.OgirFreightRouteLineSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonogirfreightroutelineselector',
//	fieldLabel : '始发站点',
	active : 'Y',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'freightRouteLine.ogirName',// 查询参数
	destCode : null,//到达站网点
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.OgirFreightRouteLineStore');
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.destCode)) {
				searchParams['freightRouteLine.destCode'] = config.destCode
			}
			searchParams['freightRouteLine.active'] = me.active;
		});
		me.callParent([cfg]);
	}
});

Ext.define('Foss.commonSelector.DestFreightRouteLineSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commondestfreightroutelineselector',
//	fieldLabel : '到达站点',
	active : 'Y',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'freightRouteLine.destName',// 查询参数
	orgCode : null,//始发站网点
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.OgirFreightRouteLineStore');
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.orgCode)) {
				searchParams['freightRouteLine.orgCode'] = config.orgCode
			}
			searchParams['freightRouteLine.active'] = me.active;
		 });
		me.callParent([cfg]);
	}
});

// 航空公司
Ext.define('Foss.baseinfo.commonSelector.AirlinesModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					}, {
						name : 'code',
						type : 'string'
					}, {
						name : 'simpleName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'logo',
						type : 'string'
					}, {
						name : 'prifixName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.AirlinesStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AirlinesModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirlines.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'airlinesEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirlinesSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairlinesselector',
//			fieldLabel : '航空公司',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			all:null,//如果为true则查询全部
			queryParam : 'airlinesEntity.queryParam',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirlinesStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}										
					if (!Ext.isEmpty(config.all)) {
						searchParams['airlinesEntity.all'] = config.all;
					}
		});
				me.callParent([cfg]);
			}
		});

// 机型
Ext.define('Foss.baseinfo.commonSelector.AircraftTypeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'aircraftSort',
						type : 'string'
					}, {
						name : 'volumn',
						type : 'number'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.AircraftTypeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AircraftTypeModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAircraftType.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'aircraftTypeEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AircraftTypeSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonaircrafttypeselector',
//	fieldLabel : '机型',
	displayField : 'code',// 显示名称
	valueField : 'code',// 值
	queryParam : 'aircraftTypeEntity.code',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{aircraftSort}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.AircraftTypeStore');
		me.callParent([cfg]);
	}
});

// 车型
Ext.define('Foss.baseinfo.commonSelector.VehicleTypeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'seq',
						type : 'string'
					},{
						name : 'vehicleType',
						type : 'string'
					}, {
						name : 'vehicleLengthCode',
						type : 'string'
					}, {
						name : 'eachKilometersFees',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'vehicleLength',
						type : 'number'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.VehicleTypeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.VehicleTypeModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonVehicleType.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'vehicleTypeEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.VehicleTypeSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonvehicletypeselector',
			//fieldLabel : '车型',
			displayField : 'vehicleLengthName',// 显示名称
			vehicleSort : 'ownership_company',//外请车(ownership_leased)或公司车(ownership_company)
			vehicleType : null, 
			valueField : 'vehicleLengthCode',// 值
			queryParam : 'vehicleTypeEntity.queryParam',// 查询参数
			showContent : '{seq}&nbsp;&nbsp;{vehicleLength}&nbsp;&nbsp;{vehicleLengthCode}&nbsp;&nbsp;{vehicleLengthName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.VehicleTypeStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					/*if (!Ext.isEmpty(config.vehicleSort)) {
						searchParams['vehicleTypeEntity.vehicleSort'] = config.vehicleSort;
					}else{
						searchParams['vehicleTypeEntity.vehicleSort'] = me.vehicleSort;
					}*/
					if (!Ext.isEmpty(config.vehicleType)) {
						searchParams['vehicleTypeEntity.vehicleType'] = config.vehicleType;
					} 
				})
				me.callParent([cfg]);
			}
		});

// 机场
Ext.define('Foss.baseinfo.commonSelector.AirportModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'airportName',
						type : 'string'
					}, {
						name : 'airportCode',
						type : 'string'
					}, {
						name : 'cityCode',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'countyCode',
						type : 'string'
					}, {
						name : 'countyName',
						type : 'string'
					}, {
						name : 'contact',
						type : 'string'
					}, {
						name : 'contactPhone',
						type : 'string'
					}, {
						name : 'pickupAddress',
						type : 'string'
					}, {
						name : 'notes',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.AirportStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.AirportModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirport.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'airportEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirportSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairportselector',
			//fieldLabel : '机场',
			displayField : 'airportName',// 显示名称
			valueField : 'airportCode',// 值
			queryParam : 'airportEntity.queryParam',// 查询参数
			showContent : '{airportName}&nbsp;&nbsp;&nbsp;{airportCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.AirportStore');
				me.callParent([cfg]);
			}
		});
//显示机场市县名称机场		
Ext.define('Foss.commonSelector.AirportWithCityNameSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairporwithcitynametselector',
//			fieldLabel : '机场',
			displayField : 'airportName',// 显示名称
			valueField : 'airportCode',// 值
			queryParam : 'airportEntity.queryDistrictParam',// 查询参数
			showContent : '{cityName}&nbsp;&nbsp;&nbsp;{countyName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirportStore');
				me.callParent([cfg]);
			}
		});		
// 线路
Ext.define('Foss.baseinfo.commonSelector.LineModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'simpleCode',
						type : 'string'
					}, {
						name : 'lineName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'orginalCityCode',
						type : 'string'
					}, {
						name : 'destinationCityCode',
						type : 'string'
					}, {
						name : 'orginalOrganizationCode',
						type : 'string'
					}, {
						name : 'destinationOrganizationCode',
						type : 'string'
					}, {
						name : 'transType',
						type : 'string'
					}, {
						name : 'orginalCityName',
						type : 'string'
					}, {
						name : 'destinationCityName',
						type : 'string'
					}, {
						name : 'notes',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.LineStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.LineModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonLine.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'lineVo.lineEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.LineSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonlineselector',
//	fieldLabel : '线路',
	displayField : 'lineName',// 显示名称
	valueField : 'simpleCode',// 值
	deptCode : null,
	orginalOrganizationCode:null,//出发部门编码
	destinationOrganizationCode:null,//到达部门
	lineSort:null,//线路类型
	queryParam : 'lineVo.lineEntity.lineName',// 查询参数
	showContent : '{lineName}&nbsp;&nbsp;&nbsp;{simpleCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.LineStore');
		me.deptCode =config.deptCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			if (!Ext.isEmpty(config.lineSort)) {
				searchParams['lineVo.lineEntity.lineSort'] = config.lineSort;
			}
			if (!Ext.isEmpty(config.orginalOrganizationCode)) {
				searchParams['lineVo.lineEntity.orginalOrganizationCode'] = config.orginalOrganizationCode;
			}
			if (!Ext.isEmpty(config.destinationOrganizationCode)) {
				searchParams['lineVo.lineEntity.destinationOrganizationCode'] = config.destinationOrganizationCode;
			}
			searchParams['lineVo.lineEntity.organizationCode'] = me.deptCode;   
		})
		me.callParent([cfg]);
	}
});


//航班号信息
Ext.define('Foss.baseinfo.commonSelector.FlightModel', {
			extend : 'Ext.data.Model',
			fields : ['flightSort','flightNo','airlines',
			          'departureAirport','destinationAirport','aircraftType',
			          'origCode','flyOnMonday','flyOnTuesday',
			          'flyOnWednesday','flyOnThursday','flyOnFriday',
			          'flyOnSaturday','flyOnSunday','active',
			          {
						 name:'isAgreementFlight',
		        	     type:'string'
			          },{
			        	  name:'dailyAgreementVolume',
			        	  type:'number'
			          },{
			        	  name:'belongsAirFreightDispatch',
				          type:'string'
			          },
			          'targetCode',{	
							name :'planLeaveTime', //计划起飞时间 
							type :'date',
							convert:dateConvert
						},{	
							name :'planArriveTime',  //计划到达时间
							type :'date',
							convert:dateConvert
						}
			          ]
		});
//航班信息Store
Ext.define('Foss.baseinfo.commonSelector.FlightStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.FlightModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonFlight.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'flightVo.flightList',
			totalProperty : 'totalCount'
		}
	}
});
//航班号
Ext.define('Foss.commonSelector.FlightSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonflightselector',
//	fieldLabel : '航班号',
	displayField : 'flightNo',// 显示名称
	valueField : 'flightNo',// 值
	active : 'Y',
	flightSort : null,//航班类别
	airLines : null,//航空公司
	origCode : null,//始发站三字码
	targetCode:null,//目的站三字码
	origCodes : null,//始发站三字码列表
	targetCodes:null,//目的站三字码列表
	isAgreementFlight:null,//是否协议航班
	airDispatchCodes:null,//空运总调编码
	queryParam : 'flightVo.flightDto.flightNo',// 查询参数
	showContent : '{flightNo}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.FlightStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.flightSort)) {
				searchParams['flightVo.flightDto.flightSort'] = config.flightSort;
			}
			if (!Ext.isEmpty(config.airLines)) {
				searchParams['flightVo.flightDto.airLines'] = config.airLines;
			}
			if (!Ext.isEmpty(config.origCode)) {
				searchParams['flightVo.flightDto.origCode'] = config.origCode;
			}
			if (!Ext.isEmpty(config.targetCode)) {
				searchParams['flightVo.flightDto.targetCode'] = config.targetCode;
			} 
			if (!Ext.isEmpty(config.origCodes)) {
				var origCodes = config.origCodes.split(',');
				searchParams['flightVo.flightDto.origCodes'] = origCodes;
			}
			if (!Ext.isEmpty(config.targetCodes)) {
				var targetCodes = config.targetCodes.split(',');
				searchParams['flightVo.flightDto.targetCodes'] = targetCodes;
			} 
			if (!Ext.isEmpty(config.airDispatchCodes)) {
				var airDispatchCodes = config.airDispatchCodes.split(',');
				searchParams['flightVo.flightDto.airDispatchCodes'] = airDispatchCodes;
			}
			if (!Ext.isEmpty(config.isAgreementFlight)) {
				searchParams['flightVo.flightDto.isAgreementFlight'] = config.isAgreementFlight;
			} 
			searchParams['flightVo.flightDto.active'] = me.active;
		})
		me.callParent([cfg]);
	}
});

// 车队
Ext.define('Foss.baseinfo.commonSelector.MotorcadeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'pinyin',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'service',
						type : 'string'
					}, {
						name : 'serviceCode',
						type : 'string'
					}, {
						name : 'serviceTeam',
						type : 'string'
					}, {
						name : 'parentOrgCode',
						type : 'string'
					}, {
						name : 'transferCenter',
						type : 'string'
					}, {
						name : 'dispatchTeam',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.MotorcadeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.MotorcadeModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonMotorcade.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonMotorcadeVo.motorcadeEntityList',
					totalProperty : 'totalCount'
				}
			}
		});
//车队
Ext.define('Foss.commonSelector.MotorcadeSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonmotorcadeselector',
//	fieldLabel : '车队', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	deptCode:null,//部门编码
	name:null,//部门名称
	parentOrgCode:null,//所属车队
	transferCenter:null,//所服务外场
	topFleetOrgCode:null,//查询该组织下包含的顶级车队
	isFullFleetOrgFlag:null,//查询全网顶级车队标识 ,如配置则为：Y；否则不配置（null）；
	loopOrgCodes:null,//递归该组织及下属所有组织（多个用‘,’给开）
	fleetTypes:null,//车队类型集合（多个用‘,’给开）
	queryParam : 'commonMotorcadeVo.commonMotorcadeDto.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.MotorcadeStore'); 
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(me.fleetTypes)) {							
				var fleetTypes = me.fleetTypes.split(',');
				searchParams['commonMotorcadeVo.commonMotorcadeDto.fleetTypes'] = fleetTypes;
			}
			if (!Ext.isEmpty(me.loopOrgCodes)) {							
				var loopOrgCodes = me.loopOrgCodes.split(',');
				searchParams['commonMotorcadeVo.commonMotorcadeDto.loopOrgCodes'] = loopOrgCodes;
			}
			if (!Ext.isEmpty(config.deptCode)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.code'] = config.deptCode;
			} 
			if (!Ext.isEmpty(config.name)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.name'] = config.name;
			} 
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.active'] = config.active;
			} 
			if (!Ext.isEmpty(config.parentOrgCode)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.parentOrgCode'] = config.parentOrgCode;
			} 
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.transferCenter'] = config.transferCenter;
			} 
			if (!Ext.isEmpty(config.topFleetOrgCode)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.topFleetOrgCode'] = config.topFleetOrgCode;
			} 
			if (!Ext.isEmpty(config.isFullFleetOrgFlag)) {
				searchParams['commonMotorcadeVo.commonMotorcadeDto.isFullFleetOrgFlag'] = config.isFullFleetOrgFlag;
			} 
//			if (!Ext.isEmpty(config.loopOrgCode)) {
//				searchParams['commonMotorcadeVo.commonMotorcadeDto.loopOrgCode'] = config.loopOrgCode;
//			} 
		});
		me.callParent([cfg]);
	}
});

//权限
Ext.define('Foss.baseinfo.commonSelector.ResourceModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'entryUri',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'parentRes',
						type : 'string'
					}, {
						name : 'displayOrder',
						type : 'string'
					}, {
						name : 'checked',
						type : 'string'
					}, {
						name : 'resType',
						type : 'string'
					}, {
						name : 'leafFlag',
						type : 'string'
					}, {
						name : 'iconCls',
						type : 'string'
					}, {
						name : 'cls',
						type : 'string'
					}, {
						name : 'notes',
						type : 'string'
					}, {
						name : 'resourceTypes',
						type : 'string'
					}, {
						name : 'systemTypes',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.ResourceStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ResourceModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonResourceByName.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'resourceList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.ResourceSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonresourceselector',
//			fieldLabel : '权限',
			active : 'Y',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			resourceTypes:'4',//权限类型集合，多个以","分隔
			systemTypes:'WEB',//权限所属系统类型
			queryParam : 'resourceEntity.name',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.ResourceStore');
						me.active =config.active;				
				me.store.addListener('beforeload', function(store, operation,
								eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
											params : searchParams
										});
							}
							searchParams['resourceEntity.active'] = me.active;
							if (!Ext.isEmpty(me.resourceTypes)) {							
								var resourceTypes = me.resourceTypes.split(',');
								searchParams['resourceEntity.resourceTypes'] = resourceTypes;
							}							
							if(!Ext.isEmpty(me.systemTypes)){
							     var systemTypes = me.systemTypes.split(',');
							     searchParams['resourceEntity.systemTypes'] = systemTypes;
								}
						});
				me.callParent([cfg]);
			}
		});


// 客户账户
Ext.define('Foss.baseinfo.commonSelector.CustomerModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'otherBranchBankName',
						type : 'string'
					}, {
						name : 'accountNo',
						type : 'string'
					}, {
						name : 'accountName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'cityCode',
						type : 'string'
					}, {
						name : 'provCode',
						type : 'string'
					}, {
						name : 'bankCode',
						type : 'string'
					}, {
						name : 'customer',
						type : 'string'
					}, {
						name : 'branchBankCode',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'provinceName',
						type : 'string'
					}, {
						name : 'openingBankName',
						type : 'string'
					}, {
						name : 'branchBankName',
						type : 'string'
					}, {
						name : 'accountNature',
						type : 'string'
					}, {
						name : 'accountNatureName',
						type : 'string'
					}, {
						name : 'financeLinkman',
						type : 'string'
					}, {
						name : 'accountUse',
						type : 'string'
					}, {
						name : 'mobilePhone',
						type : 'string'
					}, {
						name : 'address',
						type : 'string'
					}, {
						name : 'type',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'license',
						type : 'string'
					}, {
						name : 'cusCode',
						type : 'string'
					}, {
						name : 'creditAmount',
						type : 'string'
					}]
		});


Ext.define('Foss.baseinfo.commonSelector.orgSelectorWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.commonorgselectorwindow',
			width : 900,
			closeAction : 'hide',
			type : null,//数据类型
			active:null,
			division : null,//事业部
			bigRegion : null,//大区
			smallRegion : null,//小区
			salesDepartment : null,//营业部
			transferCenter : null,//外场
			doAirDispatch : null,//空运配载
			transDepartment : null,//车队
			dispatchTeam : null,//车队调度组
			billingGroup : null,//集中开单组
			transTeam : null,//车队组
			isDeliverSchedule : null,//派送派单
			isArrangeGoods : null,//派送理货
			airDispatch : null,//空运总调
			isEntityFinance : null,//实体财务部 
			regionIsBlank:null,//省市县是否必输
			layout : {
				type : 'vbox',
				align : 'stretch'
			},
			getGridRecord : function() {
				var me = this;
				var grid = me.down('grid');
				var records = grid.getSelectionModel().getSelection();
				Ext.Array.each(records, function(var2, index2, self2) {
							grid.orgSelectorGridValue.push(var2)
						});
				return grid.orgSelectorGridValue;
			},
			commitFun : function() {
			},
			getFbar : function() {
				var me = this;
				return [{
							xtype : 'button',
							text : '确定',
							handler : function() {
								me.down('grid').orgSelectorGridValue = [];
								me.commitFun();
							}
						}];
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = me.getFbar();
				me.items = [{
							xtype : 'commonselectororgselectorpanel',
							regionIsBlank:config.regionIsBlank,
							type:config.type,//数据类型
							active:config.active,
							division : config.division,//事业部
							bigRegion : config.bigRegion,//大区
							smallRegion : config.smallRegion,//小区
							salesDepartment : config.salesDepartment,//营业部
							transferCenter : config.transferCenter,//外场
							doAirDispatch : config.doAirDispatch,//空运配载
							transDepartment : config.transDepartment,//车队
							dispatchTeam : config.dispatchTeam,//车队调度组
							billingGroup : config.billingGroup,//集中开单组
							transTeam : config.transTeam,//车队组
							isDeliverSchedule : config.isDeliverSchedule,//派送派单
							isArrangeGoods : config.isArrangeGoods,//派送理货
							airDispatch : config.airDispatch,//空运总调
							isEntityFinance : config.isEntityFinance//实体财务部 
						}, {
							xtype : 'commonselectororgselectorgrid'
						}];
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.baseinfo.commonSelector.orgSelectorPanel', {
			extend : 'Ext.form.Panel',
			alias : 'widget.commonselectororgselectorpanel',
			// 为表单对象增加一个框
			frame : true,
			title : '组织查询条件',
			// 表示向内从四个方向同时偏移5px
			bodyStyle : 'padding:5px 5px 0',
			// 指定表单的宽度
			width : 900,
			// 指定表单中的所有字段属性
			fieldDefaults : {
				// 设置字段值输入有误时，错误信息的显示位置
				msgTarget : 'side',
				// 字段标签的长度
				labelWidth : 60 
			},
			// 指定表单中的所有字段类型
			defaultType : 'textfield',
			type : null,
			regionIsBlank:null,//省市县是否必输
			active:null,
			division : null,//事业部
			bigRegion : null,//大区
			smallRegion : null,//小区
			salesDepartment : null,//营业部
			transferCenter : null,//外场
			doAirDispatch : null,//空运配载
			transDepartment : null,//车队
			dispatchTeam : null,//车队调度组
			billingGroup : null,//集中开单组
			transTeam : null,//车队组
			isDeliverSchedule : null,//派送派单
			isArrangeGoods : null,//派送理货
			airDispatch : null,//空运总调
			isEntityFinance : null,//实体财务部 
			// 指定表单容器中项的属性
			layout : {
				type : 'table',
				columns : 3
			},
			// 表单项
			// 指定表单容器的按钮
			buttons : [{
						text : '查询',
						handler : function() {
							var me = this;
							me.up('form').up('window').down('grid').getStore()
									.load();
						}
					}, {
						text : '重置',
						handler : function() {
							var me = this;
							me.up('form').getForm().reset();
						}
					}], 
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);   
				me.items = me.getItems(config);
				me.callParent([cfg]);
			},
			getItems:function(config){
				return [{
					fieldLabel : '名称',
					name : 'name' 
				}, {
					fieldLabel : '编码',
					name : 'code' 
				}, {
					fieldLabel : '拼音',
					name : 'pinYin'  
				},{
					//fieldLabel : '省市区',
					type : 'P-C-C',
					xtype : 'linkregincombselector',
					cityIsBlank:Ext.isEmpty(config.regionIsBlank)?true:config.regionIsBlank,
					areaIsBlank:Ext.isEmpty(config.regionIsBlank)?true:config.regionIsBlank,
					provinceIsBlank:Ext.isEmpty(config.regionIsBlank)?true:config.regionIsBlank,
					nationIsBlank:Ext.isEmpty(config.regionIsBlank)?true:config.regionIsBlank,
					colspan:3,
					labelWid : 60,
					provinceLabel : '省份',
					provinceName:'provinceCode',//省名称
					//provinceLabelWidth : 60,
					//provinceWidth : 160,
					cityLabel : '城市',
					cityName : 'cityCode',//名称
					//cityLabelWidth : 60,
					//cityWidth : 160,
					areaLabel : '地区',
					//areaLabelWidth : 60,
					//areaWidth : 160,
					areaName : 'countyCode' // 县名称
					
				}, {
					fieldLabel : '数据类型',
					name : 'type',
					xtype : 'combobox',
					labelWid : 60,
					queryMode : 'local',
					displayField : 'name',
					value : Ext.isEmpty(config.type)?"":config.type,
					valueField : 'code',
					editable:false,
					store : Ext.create('Ext.data.Store', {
								// 定义字段
								fields : [{
											name : 'code',
											type : 'string'
										}, {
											name : 'name',
											type : 'string'
										}],
								data : {
									'items' : [{
												"code" : "",
												"name" : "全部"
											}, {
												"code" : "ORG",
												"name" : "内部组织"
											}, {
												"code" : "AIRPORT",
												"name" : "机场"
											}, {
												"code" : "PX",
												"name" : "偏线代理网点"
											}, {
												"code" : "KY",
												"name" : "空运代理网点"
											}]
								},
								proxy : {
									type : 'memory',
									reader : {
										type : 'json',
										root : 'items'
									}
								}
							}),
					listeners : {
						select : function(combo, records, eOpts) {
							if (!Ext.isEmpty(records) && 0 < records.length) {
								if (records[0].get('code') == 'A') {
									Ext.Array
											.each(
													combo
															.up('form')
															.query('checkbox'),
													function(value, index,
															comself) {
														value
																.setDisabled(true);
													})
								}
							}
						}
					}
				}, {
					fieldLabel : '是否有效',
					name : 'active',
					xtype : 'combobox',
					queryMode : 'local',
					editable:false,
					labelWid : 60,
					displayField : 'name',
					value : Ext.isEmpty(config.active)?"Y":config.active,
					valueField : 'code',
					colspan : 2,
					store : Ext.create('Ext.data.Store', {
								// 定义字段
								fields : [{
											name : 'code',
											type : 'string'
										}, {
											name : 'name',
											type : 'string'
										}],
								data : {
									'items' : [{
												"code" : "Y",
												"name" : "是"
											}, {
												"code" : "N",
												"name" : "否"
											}]
								},
								proxy : {
									type : 'memory',
									reader : {
										type : 'json',
										root : 'items'
									}
								}
							})
				}, {
					// 指定表单的标签
					colspan : 3,
					// 指定表单中的内容的CSS样式
					// padding就是向内收缩格式为(左 上 右 下)，例如：padding:5px 5px 5px 5px
					// 表示向内从四个方向同时偏移5px
					bodyStyle : 'padding:5px 5px 0',
					// 指定表单的宽度
					// 指定表单中的所有字段属性
					fieldDefaults : {
						// 字段标签的长度
						labelWidth : 60
					},
					// 指定类型为字段容器类型
					xtype: 'checkboxgroup',
					layout : {
						type : 'table',
						columns : 7
					},
					// 指定字段标签
					fieldLabel : '组织属性',
					defaults : {
						margin : '0 15 0 0'
					},
					items : [{
								boxLabel : '事业部',
								name : 'division', // 表单的参数名
								inputValue : 'Y',
								checked:Ext.isEmpty(config.division) ? false : true 
							}, {
								boxLabel : '大区',
								name : 'bigRegion', // 表单的参数名
								inputValue : 'Y' ,
								checked:Ext.isEmpty(config.bigRegion) ? false : true 
						}	, {
								boxLabel : '小区',
								name : 'smallRegion', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.smallRegion) ? false : true  
							}, {
								boxLabel : '营业部',
								name : 'salesDepartment', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.salesDepartment) ? false : true 
							}, {
								boxLabel : '外场',
								name : 'transferCenter', // 表单的参数名 
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.transferCenter) ? false : true 
							}, {
								boxLabel : '空运配载',
								name : 'doAirDispatch', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.doAirDispatch) ? false : true 
							}, {
								boxLabel : '车队',
								name : 'transDepartment', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.transDepartment) ? false : true 
							}, {
								boxLabel : '车队调度组',
								name : 'dispatchTeam', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.dispatchTeam) ? false : true 
							}, {
								boxLabel : '集中开单组',
								name : 'billingGroup', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.billingGroup) ? false : true 

							}, {
								boxLabel : '车队组',
								name : 'transTeam', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.transTeam) ? false : true 
							}, {
								boxLabel : '派送派单',
								name : 'isDeliverSchedule', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.isDeliverSchedule) ? false : true 
							}, {
								boxLabel : '派送理货', 
								name : 'isArrangeGoods', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.isArrangeGoods) ? false : true 
							}, {
								boxLabel : '空运总调',
								name : 'airDispatch', // 表单的参数名 
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.airDispatch) ? false : true 
							}, {
								boxLabel : '实体财务部',
								name : 'isEntityFinance', // 表单的参数名
								inputValue : 'Y', 
								checked:Ext.isEmpty(config.isEntityFinance) ? false : true 
							}]
				},{
					/**
					 * 功能：增加组织查询 提示信息
					 * 修改人：FOSS-073586-LIXUEXING
					 * 修改时间：2013-03-27
					 */
					colspan : 3,
					xtype:'label',
					html:'<span style="color:red;font-weight:bold">提示：如果要配置全国部门的参数请在名称里面输入 “办公门户机构人员” 进行查询</span>'
				}];
			}
		});

Ext.define('Foss.commonOrgSelector.conditionSelectorOrgStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.OrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchCommonOrg.action',
				actionMethods : 'POST',
				reader : {
					type : 'json',
					root : 'commonOrgVo.commonOrgEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

//组织列表信息 创建一个带复选框选择列的表格
Ext.define('Foss.baseinfo.commonSelector.orgSelectorGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.commonselectororgselectorgrid',
	// 表格绑定store
	store : null,
	// 设置选择模式为复选框选择
	selModel : null,
	// 增加表格列的分割线
	columnLines : true,
	// 指定表格的宽度
	width : 750,
	orgSelectorGridValue : new Array(),
	// 指定表格的高度
	height : 280,
	// 表格对象增加一个边框
	frame : true,
	// 定义表格的标题
	title : '组织列表信息',
	// 定义表格列信息
	columns : [{
				text : "名称",
				width : 200,
				dataIndex : 'name'
			}, {
				text : "简称",
				width : 200,
				dataIndex : 'simpleName'
			}, {
				text : "编码",
				width : 200,
				dataIndex : 'code'
			}, {
				text : "类型",
				width : 200,
				dataIndex : 'type',
				renderer:function(value){
					if(value=='ORG'){
						return '内部组织';	
					}else if(value=='AIRPORT'){
						return '机场';
					}else if(value=='PX'){
						return '偏线代理网点';
					}else if(value=='KY'){
						return '空运代理网点';
					}else if(value=='CPPX'){
						return '偏线代理公司';
					}else if(value=='CPKY'){
						return '空运代理公司';
					}else{
						return value;
					}
				} 
			}],
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : me.store,
				pageSize : 10,
				listeners : {
					beforechange : function(th, page, eOpts) {
						var recs = me.getSelectionModel().getSelection();
						var isExists = false;
						if (Ext.isEmpty(me.orgSelectorGridValue)
								|| 0 >= me.orgSelectorGridValue.length) {
							if (Ext.isEmpty(recs) || 0 >= recs.length) {
								return;
							} else {
								Ext.Array.each(recs, function(value, index,
												self) {
											me.orgSelectorGridValue.push(value);
										});
							}
						}
						Ext.Array.each(recs, function(var2, index2, self2) {
									Ext.Array.each(me.orgSelectorGridValue,
											function(var1, index1, self1) {
												if (var1.get('id') == var2
														.get('id')) {
													isExists = true;
												}
											})
									if (!isExists) {
										me.orgSelectorGridValue.push(var2);
									}
								})
					}
				}
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.commonOrgSelector.conditionSelectorOrgStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var queryForm = me.up('window').down('form');
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'commonOrgVo.name' : queryParams.name,
						'commonOrgVo.code' : queryParams.code,
						'commonOrgVo.pinYin' : queryParams.pinYin, 
						'commonOrgVo.type' : queryParams.type,
						'commonOrgVo.provinceCode' : queryParams.provinceCode,
						'commonOrgVo.cityCode' : queryParams.cityCode,
						'commonOrgVo.countyCode' : queryParams.countyCode, 
						'commonOrgVo.division' : queryParams.division,
						'commonOrgVo.bigRegion' : queryParams.bigRegion,
						'commonOrgVo.smallRegion' : queryParams.smallRegion,
						'commonOrgVo.salesDepartment' : queryParams.salesDepartment,
						'commonOrgVo.transferCenter' : queryParams.transferCenter,
						'commonOrgVo.doAirDispatch' : queryParams.doAirDispatch,
						'commonOrgVo.transDepartment' : queryParams.transDepartment,
						'commonOrgVo.dispatchTeam' : queryParams.dispatchTeam,
						'commonOrgVo.billingGroup' : queryParams.billingGroup,
						'commonOrgVo.transTeam' : queryParams.transTeam,
						'commonOrgVo.isDeliverSchedule' : queryParams.isDeliverSchedule,
						'commonOrgVo.isArrangeGoods' : queryParams.isArrangeGoods,
						'commonOrgVo.airDispatch' : queryParams.airDispatch,
						'commonOrgVo.isEntityFinance' : queryParams.isEntityFinance,
						'commonOrgVo.active' : queryParams.active
					}
				});
			}
		});
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
	}
});
//客户账号查询Store
Ext.define('Foss.baseinfo.commonSelector.CustomerAccountStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CustomerModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/commonCusAccountInfo.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'customerVo.cusAccountList',
					totalProperty : 'totalCount'
				}
			}
		});
// 创建一个带复选框选择列的表格
Ext.define('Foss.baseinfo.commonSelector.customerSelectorGrid', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.commoncustomerselectorgrid',
			// 表格绑定store
			store : null,
			// 设置选择模式为复选框选择
			selModel : null,
			// 增加表格列的分割线
			columnLines : true,
			// 指定表格的宽度
			width : 750,
			// 指定表格的高度
			height : 280,
			// 表格对象增加一个边框
			orgSelectorGridValue : new Array(),
			frame : true,
			// 定义表格的标题
			title : '客户账户列表信息',
			// 定义表格列信息
			columns : [{
						text : "税号",
						width : 200,
						dataIndex : 'license'
					}, {
						text : "公司名称",
						dataIndex : 'name'
					}, {
						text : "电话",
						dataIndex : 'mobilePhone'
					}, {
						text : "账号",
						dataIndex : 'accountNo'
					}, {
						text : "银行编码",
						dataIndex : 'bankCode'
					}, {
						text : "账户关系",
						dataIndex : 'customer'
					}, {
						text : "省份",
						dataIndex : 'provinceName'
					}, {
						text : "城市",
						dataIndex : 'cityName'
					}, {
						text : "账户性质",
						dataIndex : 'accountNature',
						hidden:true
					}, {
						text : "账户性质",
						dataIndex : 'accountNatureName'
					}, {
						text : "银行行号",
						dataIndex : 'branchBankCode'
					}, {
						text : "卡户行",
						dataIndex : 'openingBankName'
					}, {
						text : "地址",
						dataIndex : 'address'
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.CustomerAccountStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var customerCode = me.up('window').customerCode;
					var active = me.up('window').active;
					Ext.apply(operation, {
						params : {
							'customerVo.customerCondDto.custCode' : customerCode,
							'customerVo.customerCondDto.active' : active
						}
					});
				});
				me.selModel = Ext.create('Ext.selection.RadioModel');
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.baseinfo.commonSelector.CustomerWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.commoncustomerselectorwindow',
			width : 800,
			closeAction : 'hide',
			customerCode : null,
			active : null,
			orgSelectorGridValue:null,
			layout : {
				type : 'vbox',
				align : 'stretch'
			},
			getGridRecord : function() {
				var me =this;
				return me.orgSelectorGridValue;
			},
			commitFun : function() {
				var me = this;
			},
			getFbar : function() {
				var me = this;
				return [{
					xtype : 'button',
					text : '确定',
					handler : function() {
						var records = me.down('grid').getSelectionModel()
								.getSelection();
						me.orgSelectorGridValue = records;
						me.commitFun();
						me.close();
					}
				}];
			},
			listeners : {
				beforeshow : function(ths, eOpts) {
					ths.down('grid').orgSelectorGridValue = [];
					ths.down('grid').store.load();
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = me.getFbar();
				me.items = [{
							xtype : 'commoncustomerselectorgrid'
						}];
				me.callParent([cfg]);
			}
		});
		
// 客户账户model
Ext.define('Foss.commonSelector.CusAccountModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'otherBranchBankName',
						type : 'string'
					}, {
						name : 'accountNo',
						type : 'string'
					}, {
						name : 'accountName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'cityCode',
						type : 'string'
					}, {
						name : 'provCode',
						type : 'string'
					}, {
						name : 'bankCode',
						type : 'string'
					}, {
						name : 'customer',
						type : 'string'
					}, {
						name : 'branchBankCode',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'provinceName',
						type : 'string'
					}, {
						name : 'openingBankName',
						type : 'string'
					}, {
						name : 'branchBankName',
						type : 'string'
					}, {
						name : 'accountNature',
						type : 'string'
					}, {
						name : 'accountNatureName',
						type : 'string'
					}, {
						name : 'financeLinkman',
						type : 'string'
					}, {
						name : 'accountUse',
						type : 'string'
					}, {
						name : 'mobilePhone',
						type : 'string'
					}, {
						name : 'address',
						type : 'string'
					}, {
						name : 'type',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'license',
						type : 'string'
					}, {
						name : 'cusCode',
						type : 'string'
					}, {
						name : 'creditAmount',
						type : 'string'
					}]
});		
//客户账户或客户信息查询Store
Ext.define('Foss.commonSelector.CusAccountJoinCusStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.CusAccountModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/cusAccountJoinCus.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'customerVo.cusAccountList',
					totalProperty : 'totalCount'
				}
			}
});		
//开户人姓名（数据源于客户账户表）单选选择器——232607
Ext.define('Foss.commonSelector.CusAccountNameSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.cusAccountNameSelector',
	displayField : 'accountName',// 显示开户人姓名
	valueField : 'accountName',// 值域为开户人姓名
	unifiedCode:null, // 客户所属部门标杆编码
	queryParam : 'customerVo.cusAccountDto.accountName',// 查询参数
	showContent : '{accountName}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.CusAccountJoinCusStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.unifiedCode)) {
			searchParams['customerVo.cusAccountDto.unifiedCode'] = config.unifiedCode;
		}
	});
	me.callParent([cfg]);
	}
});
//供应商选择器model
Ext.define('Foss.commonSelector.SupplierModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'simpleName',
						type : 'string'
					}, {
						name : 'contactPerson',
						type : 'string'
					}, {
						name : 'contactPhone',
						type : 'string'
					}, {
						name : 'contactAddress',
						type : 'string'
					}, {
						name : 'furnitureFlag',
						type : 'string'
					}, {
						name : 'householdFlag',
						type : 'string'
					}, {
						name : 'constructionFlag',
						type : 'string'
					}, {
						name : 'remark',
						type : 'string'
					}, {
						name : 'createTime',
						type : 'string'
					}, {
						name : 'updateTime',
						type : 'string'
					}, {
						name : 'activeTime',
						type : 'string'
					}, {
						name : 'isUsing',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
});
//供应商选择器store
Ext.define('Foss.commonSelector.SupplierStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.commonSelector.SupplierModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/comboQuerySupplier.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'supplierVo.supplierEntitys',
					totalProperty : 'totalCount'
				}
			}
});		
//供应商选择器（单选）——232607
Ext.define('Foss.commonSelector.SupplierSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.supplierSelector',
	displayField : 'name',// 显示供应商名称
	valueField : 'code',// 值域为供应商编码
	queryParam : 'supplierVo.supplierEntity.name',// 查询参数
	showContent : '{name}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);
	me.store = Ext.create('Foss.commonSelector.SupplierStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
	});
	me.callParent([cfg]);
	}
});

// 职位
Ext.define('Foss.baseinfo.commonSelector.PositionModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'title',//职位
						type : 'string'
					}, {
						name : 'titleCode',//职位code
						type : 'string'
					}
					]
		});
Ext.define('Foss.baseinfo.commonSelector.PositionStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.PositionModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPosition.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'positionVo.positionList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.PositionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonpositionselector',
//	fieldLabel : '职位',
	displayField : 'title',// 显示名称
	valueField : 'titleCode',// 值
	queryParam : 'positionVo.positionEntity.queryParam',// 查询参数
	showContent : '{title}&nbsp;&nbsp;&nbsp;{titleCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.PositionStore');
		me.active = config.active; 
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});
		me.callParent([cfg]);
	}
});

// 付款方
Ext.define('Foss.baseinfo.commonSelector.PayeeInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'payeeNo',
						type : 'string'
					}, {
						name : 'operatorId',
						type : 'string'
					}, {
						name : 'accountType',
						type : 'string'
					}, {
						name : 'beneficiaryName',
						type : 'string'
					}, {
						name : 'accountNo',
						type : 'string'
					}, {
						name : 'accountbankCode',
						type : 'string'
					}, {
						name : 'accountbankName',
						type : 'string'
					}, {
						name : 'accountbranchbankCode',
						type : 'string'
					}, {
						name : 'accountbranchbankName',
						type : 'string'
					}, {
						name : 'accountbankcityCode',
						type : 'string'
					}, {
						name : 'accountbankcityName',
						type : 'string'
					}, {
						name : 'accountbankstateCode',
						type : 'string'
					}, {
						name : 'accountbankstateName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'accSort',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.PayeeInfoStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.PayeeInfoModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPayeeInfo.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'payeeInfoEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.PayeeInfoSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonpayeeinfoselector',
//			fieldLabel : '收款方',
			displayField : 'beneficiaryName',// 显示名称
			valueField : 'accountNo',// 值
			accSort : null,
			accountTypes : null,//账户类型集合，多个以","分隔
			isOnlyPartnerAccount:null,//如果未配置则只查收款方账户表，'Y'只查询合伙人账号表，'N'则两个表全部查询
			operatorId : null,//录入人工号
			exactQuery : null,//配置‘Y’则精确查询
			queryParam : 'payeeInfoEntity.beneficiaryName',// 查询参数
			showContent : '{beneficiaryName}&nbsp;&nbsp;&nbsp;{accountNo}',// 显示表格列			
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.commonSelector.PayeeInfoStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if (!Ext.isEmpty(config.accountTypes)) {
						accountTypes = config.accountTypes.split(',');
						searchParams['payeeInfoEntity.accountTypes'] = accountTypes;
					}
					if (!Ext.isEmpty(config.operatorId)) {
						searchParams['payeeInfoEntity.operatorId'] = config.operatorId;
					}
					if (!Ext.isEmpty(config.exactQuery)) {
						searchParams['payeeInfoEntity.exactQuery'] = config.exactQuery;
					}
					if (!Ext.isEmpty(config.isOnlyPartnerAccount)) {
						searchParams['payeeInfoEntity.isOnlyPartnerAccount'] = config.isOnlyPartnerAccount;
					}
					if (!Ext.isEmpty(config.accSort)) {
						searchParams['payeeInfoEntity.accSort'] = config.accSort;
					}
				});
				me.callParent([cfg]);
			}
		});

// 对公转户
Ext.define('Foss.baseinfo.commonSelector.PublicBankAccountModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'bankAcc',
						type : 'string'
					}, {
						name : 'bankAccName',
						type : 'string'
					}, {
						name : 'deptCd',
						type : 'string'
					}, {
						name : 'bankCd',
						type : 'string'
					}, {
						name : 'bankName',
						type : 'string'
					}, {
						name : 'subbranchCd',
						type : 'string'
					}, {
						name : 'subbranchName',
						type : 'string'
					}, {
						name : 'provCd',
						type : 'string'
					}, {
						name : 'provName',
						type : 'string'
					}, {
						name : 'cityCd',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.PublicBankAccountStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.PublicBankAccountModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonPublicBankAccount.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonPublicBankAccountVo.publicBankAccountEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.PublicBankAccountSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonpublicbankaccountselector',
	//fieldLabel : '对公账户',
	displayField : 'bankAccName',// 显示名称
	valueField : 'bankAcc',// 值
	deptCode : null,//部门编码 
	accountStatus:null,
	queryParam : 'commonPublicBankAccountVo.publicBankAccountDto.bankAccName',// 查询参数
	showContent : '{bankAccName}&nbsp;&nbsp;&nbsp;{bankAcc}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config); 
		me.store = Ext.create('Foss.baseinfo.commonSelector.PublicBankAccountStore');
		me.store.addListener('beforeload', function(store, operation,eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonPublicBankAccountVo.publicBankAccountDto.active'] = config.active;
			}
			if (!Ext.isEmpty(config.deptCode)) {
				searchParams['commonPublicBankAccountVo.publicBankAccountDto.deptCode'] = config.deptCode;
			}
			if (!Ext.isEmpty(config.accountStatus)) {
				searchParams['commonPublicBankAccountVo.publicBankAccountDto.accountStatus'] = config.accountStatus;
			}
		});
		me.callParent([cfg]);
	}
});






//全部账户
Ext.define('Foss.baseinfo.commonSelector.BankAccountModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'bankAcc',
						type : 'string'
					}, {
						name : 'bankAccName',
						type : 'string'
					}, {
						name : 'deptCd',
						type : 'string'
					}, {
						name : 'bankCd',
						type : 'string'
					}, {
						name : 'bankName',
						type : 'string'
					}, {
						name : 'subbranchCd',
						type : 'string'
					}, {
						name : 'subbranchName',
						type : 'string'
					}, {
						name : 'provCd',
						type : 'string'
					}, {
						name : 'provName',
						type : 'string'
					}, {
						name : 'cityCd',
						type : 'string'
					}, {
						name : 'cityName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'accountType',
						type : 'string'
					}, {
						name : 'payeeNo',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.BankAccountStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.BankAccountModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonBankAccount.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'commonBankAccountVo.commonBankAccountEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.BankAccountSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonbankaccountselector',
	//fieldLabel : '对公账户',
	displayField : 'bankAccName',// 显示名称
	valueField : 'bankAcc',// 值
	deptCode : null,//部门编码 
	operatorId : null,//录入人工号
	accountStatus:null,
	accountTypes : null,//账户类型集合，多个以","分隔
	queryParam : 'commonBankAccountVo.commonBankAccountEntity.bankAccName',// 查询参数
	showContent : '{bankAccName}&nbsp;&nbsp;&nbsp;{bankAcc}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.BankAccountStore');
		me.store.addListener('beforeload', function(store, operation,eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.accountTypes)) {
				accountTypes = config.accountTypes.split(',');
				searchParams['commonBankAccountVo.commonBankAccountEntity.accountTypes'] = accountTypes;
			}
			if (!Ext.isEmpty(me.operatorId)) {
				searchParams['commonBankAccountVo.commonBankAccountEntity.operatorId'] = me.operatorId;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['ommonBankAccountVo.commonBankAccountEntity.active'] = config.active;
			}
		});
		me.callParent([cfg]);
	}
});






//小区多选
Ext.define('Foss.commonSelector.CommonMultiSmallZoneSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonmultismallzoneselector',
	//fieldLabel : '小区多选',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	regionType:null,//区域类型 
	management : null,//管理部门
	parentOrgCodes:null,//递归该网点下属所有网点多个用','隔开
	queryParam : 'commonZoneVo.commonSmallZoneDto.queryParam',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.SmallZoneStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.regionType)){
				searchParams['commonZoneVo.commonSmallZoneDto.regionType'] = config.regionType;
			} 
			if(!Ext.isEmpty(config.management)){
				searchParams['commonZoneVo.commonSmallZoneDto.management'] = config.management;
			}
			if (!Ext.isEmpty(config.parentOrgCodes)) {
				parentOrgCodes = config.parentOrgCodes.split(',');
				searchParams['commonZoneVo.commonSmallZoneDto.parentOrgCodes'] = parentOrgCodes;
			}		
		});
		me.callParent([cfg]);
	}
});


//营业部和偏线代理信息Model
Ext.define('Foss.baseinfo.commonSelector.SaleDeptAndOuterBranchModel', {
			extend : 'Ext.data.Model',
			fields : [
			          'id','code','name','type','simpleName','pinYin'
			          ]
		});
//营业部和偏线代理信息Store
Ext.define('Foss.baseinfo.commonSelector.SaleDeptAndOuterBranchStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.SaleDeptAndOuterBranchModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonSaleDeptAndOuterBranch.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'vo.saleDeptAndOuterBranchList',
			totalProperty : 'totalCount'
		}
	}
});
//营业部和偏线代理信息
Ext.define('Foss.commonSelector.SaleDeptAndOuterBranchSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonsaledeptandouterbranchselector',
//	fieldLabel : '航班号',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	types : 'ORG',//默认查询营业部
	active : 'Y', 
	leave : null,//可出发 Y,N
	arrive : null,//可到达 Y,N
	station : null, //是否驻地部门 Y,N
	billingGroup:null,//所属集中开单组 
	transferCenter:null,// 驻地营业部所属外场
	pickupSelf :null,//可自提 Y,N
	delivery :null,//可派送 Y,N
	airArrive :null,//可空运到达 Y,N
	truckArrive :null,//可汽运到达 Y,N
	saleDeptCode:null,//传入code查找当前对应的营业部
	//wp_078816_20140521
	productCode:null,
	queryParam : 'vo.dto.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.SaleDeptAndOuterBranchStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			// 传递的部门类型是多种
			var types = null;
			if (!Ext.isEmpty(config.types)) {
				types = config.types.split(',');
				searchParams['vo.dto.types'] = types;
			}else{
				types = me.types.split(',');
				searchParams['vo.dto.types'] = types;
			}
			if (!Ext.isEmpty(config.type)) {
				searchParams['vo.dto.type'] = config.type;
			}
			if (!Ext.isEmpty(config.leave)) {
				searchParams['vo.dto.leave'] = config.leave;
			}
			if (!Ext.isEmpty(config.arrive)) {
				searchParams['vo.dto.arrive'] = config.arrive;
			}
			if (!Ext.isEmpty(config.billingGroup)) {
				searchParams['vo.dto.billingGroup'] = config.billingGroup;
			}
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['vo.dto.transferCenter'] = config.transferCenter;
			} 
			if (!Ext.isEmpty(config.pickupSelf)) {
				searchParams['vo.dto.pickupSelf'] = config.pickupSelf;
			}
			if (!Ext.isEmpty(config.delivery)) {
				searchParams['vo.dto.delivery'] = config.delivery;
			}
			if (!Ext.isEmpty(config.airArrive)) {
				searchParams['vo.dto.airArrive'] = config.airArrive;
			} 
			if (!Ext.isEmpty(config.truckArrive)) {
				searchParams['vo.dto.truckArrive'] = config.truckArrive;
			} 
			if (!Ext.isEmpty(config.saleDeptCode)) {
				searchParams['vo.dto.saleDeptCode'] = config.saleDeptCode;
			} 
			searchParams['vo.dto.active'] = me.active; 	
			
			if (!Ext.isEmpty(config.productCode)) {
				searchParams['vo.dto.productCode'] = config.productCode;
			} 
			})
		me.callParent([cfg]);
	}
});


Ext.define('Foss.baseinfo.commonSelector.OrgExtendsModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'code'
			}, {
				name : 'name'
			}, {
				name : 'simpleName'
			}, {
				name : 'pinYin'
			}, {
				name : 'cityCode'
			}, {
				name : 'countyCode'
			}, {
				name : 'type'
			}, {
				name : 'provinceCode'
			}, {
				name : 'active'
			}, {
				name : 'division'
			}, {
				name : 'bigRegion'
			}, {
				name : 'smallRegion'
			}, {
				name : 'salesDepartment'
			}, {
				name : 'transferCenter'
			}, {
				name : 'doAirDispatch'
			}, {
				name : 'transDepartment'
			}, {
				name : 'dispatchTeam'
			}, {
				name : 'billingGroup'
			}, {
				name : 'transTeam'
			}, {
				name : 'isDeliverSchedule'
			}, {
				name : 'isArrangeGoods'
			}, {
				name : 'airDispatch'
			}, {
				name : 'isEntityFinance'
			}, {
				name : 'unifiedCode'
			},
			
			 {
				name : 'leave'
			}, {
				name : 'arrive'
			}, {
				name : 'station'
			}, {
				name : 'billingGroup'
			}, {
				name : 'transferCenter'
			}, {
				name : 'pickupSelf'
			}, {
				name : 'delivery'
			},
			 {
				name : 'airArrive'
			}, {
				name : 'truckArrive'
			}, {
				name : 'inCentralizedShuttle'
			}, {
				name : 'canPayServiceFee'
			}, {
				name : 'canReturnSignBill'
			}, {
				name : 'canCashOnDelivery'
			}, {
				name : 'canAgentCollected'
			}, {
				name : 'transferGoodDept'
			}, {
				name : 'stationNumber'
			}]
 });

Ext.define('Foss.baseinfo.commonSelector.OrgExtendsStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.OrgExtendsModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonOrgExtends.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonOrgVo.commonDtoList',
			totalProperty : 'totalCount'
		}
	}
});

//组织信息扩展添加营业部信息
Ext.define('Foss.commonSelector.CommonOrgExtendsSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonorgextendsselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	type : 'ORG',// 部门类型 一种部门类型，传递此值
	types : null,// 部门类型 多种部门类型传递次值 两个类型的值之间用","隔开
	doAirDispatch : null,// 查询空运配载 配置此值
	airDispatch : null,// 查询空运总调 配置此值
	active : 'Y', 
	leave : null,//可出发 Y,N
	arrive : null,//可到达 Y,N
	station : null, //是否驻地部门 Y,N
	billingGroup:null,//所属集中开单组 
	transferCenter:null,// 驻地营业部所属外场
	pickupSelf :null,//可自提 Y,N
	delivery :null,//可派送 Y,N
	airArrive :null,//可空运到达 Y,N
	truckArrive :null,//可汽运到达 Y,N
	queryParam : 'commonOrgVo.dto.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.OrgExtendsStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.type)) {
				searchParams['commonOrgVo.dto.type'] = config.type;
			}
			// 传递的部门类型是多种
			var types = null;
			if (!Ext.isEmpty(config.types)) {
				types = config.types.split(',');
				searchParams['commonOrgVo.dto.types'] = types;
			}
			if (!Ext.isEmpty(config.doAirDispatch)) {
				searchParams['commonOrgVo.dto.doAirDispatch'] = config.doAirDispatch;
			}
			if (!Ext.isEmpty(config.airDispatch)) {
				searchParams['commonOrgVo.dto.airDispatch'] = config.airDispatch;
			}
			
			if (!Ext.isEmpty(config.leave)) {
				searchParams['commonOrgVo.dto.leave'] = config.leave;
			}
			if (!Ext.isEmpty(config.arrive)) {
				searchParams['commonOrgVo.dto.arrive'] = config.arrive;
			}
			if (!Ext.isEmpty(config.station)) {
				searchParams['commonOrgVo.dto.station'] = config.station;
			}
			if (!Ext.isEmpty(config.billingGroup)) {
				searchParams['commonOrgVo.dto.billingGroup'] = config.billingGroup;
			}
			if (!Ext.isEmpty(config.transferCenter)) {
				searchParams['commonOrgVo.dto.transferCenter'] = config.transferCenter;
			} 
			if (!Ext.isEmpty(config.pickupSelf)) {
				searchParams['commonOrgVo.dto.pickupSelf'] = config.pickupSelf;
			}
			if (!Ext.isEmpty(config.delivery)) {
				searchParams['commonOrgVo.dto.delivery'] = config.delivery;
			}
			if (!Ext.isEmpty(config.airArrive)) {
				searchParams['commonOrgVo.dto.airArrive'] = config.airArrive;
			} 
			if (!Ext.isEmpty(config.truckArrive)) {
				searchParams['commonOrgVo.dto.truckArrive'] = config.truckArrive;
			} 
			searchParams['commonOrgVo.dto.active'] = me.active; 		
			})
		me.callParent([cfg]);
	}
});


//产品Model
Ext.define('Foss.baseinfo.commonSelector.ProductModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'code'
			}, {
				name : 'name'
			}, {
				name : 'active'
			}, {
				name : 'description'
			}, {
				name : 'transportType'
			}, {
				name : 'levels'
			}, {
				name : 'parentCode'
			}, {
				name : 'parentName'
			}, {
				name : 'refId'
			}, {
				name : 'shortName'
			}, {
				name : 'priority'
			}, {
				name : 'destNetType'
			}]
 });

Ext.define('Foss.baseinfo.commonSelector.ProductStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ProductModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../pricing/queryProductCommonToLevelByCondition.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'productVo.productEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//产品类型公共选择器信息
Ext.define('Foss.commonSelector.CommonProductSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonproductselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : 'Y', 
	transportType : null,//产品性质(空运/汽运)
	levelses : '2',//多个产品层级以","分隔
	parentCode : null, //父级产品编号
	queryParam : 'productVo.productEntity.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.ProductStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.transportType)) {
				searchParams['productVo.productEntity.transportType'] = config.transportType;
			}
			var levelses = null; 
			if (!Ext.isEmpty(config.levelses)) {
				levelses = config.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] = levelses;
			}else{
				levelses = me.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] =levelses;
			}
			if (!Ext.isEmpty(config.parentCode)) {
				searchParams['productVo.productEntity.parentCode'] = config.parentCode;
			}
			searchParams['productVo.productEntity.active'] = me.active; 		
			})
		me.callParent([cfg]);
	}
});
//产品类型多选选择器-232607
Ext.define('Foss.commonSelector.CommonProductMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonproductmultiselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : 'Y', 
	transportType : null,//产品性质(空运/汽运)
	levelses : '1,2,3',//多个产品层级以","分隔
	parentCode : null, //父级产品编号
	queryParam : 'productVo.productEntity.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.ProductStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.transportType)) {
				searchParams['productVo.productEntity.transportType'] = config.transportType;
			}
			var levelses = null; 
			if (!Ext.isEmpty(config.levelses)) {
				levelses = config.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] = levelses;
			}else{
				levelses = me.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] =levelses;
			}
			if (!Ext.isEmpty(config.parentCode)) {
				searchParams['productVo.productEntity.parentCode'] = config.parentCode;
			}
			searchParams['productVo.productEntity.active'] = me.active; 		
			})
		me.callParent([cfg]);
	}
});

//外场信息Model
Ext.define('Foss.baseinfo.commonSelector.TransferCenterModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'orgCode'
			}, {
				name : 'code'
			}, {
				name : 'name'
			}, {
				name : 'pinYin'
			}, {
				name : 'simpleCode'
			}, {
				name : 'vehicleAssemble'
			}, {
				name : 'outAssemble'
			}, {
				name : 'packingWood'
			}, {
				name : 'transfer'
			}, {
				name : 'sortingMachine'
			}, {
				name : 'goodsArea'
			}, {
				name : 'platArea'
			}, {
				name : 'platType'
			}, {
				name : 'parentOrgId'
			}]
});

Ext.define('Foss.baseinfo.commonSelector.TransferCenterStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.TransferCenterModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonTransferCenter.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'transferCenterVo.transferCenterList',
			totalProperty : 'totalCount'
		}
	}
});

//外场公共选择器信息
Ext.define('Foss.commonSelector.CommonTransferCenterSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commontransfercenterselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : 'Y', 
	orgCode : null,//部门编码
	vehicleAssemble : null,//可汽运配载
	outAssemble : null,//可外发配载
	packingWood : null, //可打木架
	transfer : null, //可中转
	sortingMachine : null, //是否有自动分拣机
	platType : null, //库型
	parentOrgId : null, //所属外场
	userCode: null,//权限用当前用户编码
	currentOrgCode: null,//权限用当前部门编码 
	queryParam : 'transferCenterVo.dto.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.TransferCenterStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.orgCode)) {
				searchParams['transferCenterVo.dto.orgCode'] = config.orgCode;
			}
			if (!Ext.isEmpty(config.vehicleAssemble)) {
				searchParams['transferCenterVo.dto.vehicleAssemble'] = config.vehicleAssemble;
			}
			if (!Ext.isEmpty(config.outAssemble)) {
				searchParams['transferCenterVo.dto.outAssemble'] = config.outAssemble;
			}
			if (!Ext.isEmpty(config.packingWood)) {
				searchParams['transferCenterVo.dto.packingWood'] = config.packingWood;
			}
			if (!Ext.isEmpty(config.transfer)) {
				searchParams['transferCenterVo.dto.transfer'] = config.transfer;
			}
			if (!Ext.isEmpty(config.sortingMachine)) {
				searchParams['transferCenterVo.dto.sortingMachine'] = config.sortingMachine;
			}
			if (!Ext.isEmpty(config.platType)) {
				searchParams['transferCenterVo.dto.platType'] = config.platType;
			}
			if (!Ext.isEmpty(config.parentOrgId)) {
				searchParams['transferCenterVo.dto.parentOrgId'] = config.parentOrgId;
			}
			if (!Ext.isEmpty(config.userCode)) {
				searchParams['transferCenterVo.dto.userCode'] = config.userCode;
			}
			if (!Ext.isEmpty(config.currentOrgCode)) {
				searchParams['transferCenterVo.dto.currentOrgCode'] = config.currentOrgCode;
			}
			searchParams['transferCenterVo.dto.active'] = me.active; 		
		})
		me.callParent([cfg]);
	}
});

//子公司信息Model
Ext.define('Foss.baseinfo.commonSelector.SubSidiaryModel',{
	extend : 'Ext.data.Model',
	fields : ['id','code','name','fullName']
});

Ext.define('Foss.baseinfo.commonSelector.SubSidiaryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.SubSidiaryModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonSubSidiary.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonSubSidiaryVo.subSidiaryList',
			totalProperty : 'totalCount'
		}
	}
});

//子公司选择器信息
Ext.define('Foss.commonSelector.CommonSubSidiarySelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonsubsidiaryselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'commonSubSidiaryVo.dto.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.SubSidiaryStore');  
		me.callParent([cfg]);
	}
});
//快递代理公司
Ext.define('Foss.baseinfo.commonSelector.LdpAgencyCompanyModel', {
	extend : 'Ext.data.Model',
	fields : [
	// 快递代理公司编码
	{
		name : 'agentCompanyCode',
		type : 'string'
	},
	// 管理部门
	{
		name : 'management',
		type : 'string'
	},
	// 管理部门名称（扩展）
	{
		name : 'managementName',
		type : 'string'
	},
	// 快递代理公司名称
	{
		name : 'agentCompanyName',
		type : 'string'
	},
	// 快递代理简称
	{
		name : 'simplename',
		type : 'string'
	},
	// 省份
	{
		name : 'provCode',
		type : 'string'
	},
	// 省份名称
	{
		name : 'provName',
		type : 'string'
	},
	// 城市
	{
		name : 'cityCode',
		type : 'string'
	},
	// 城市名称
	{
		name : 'cityName',
		type : 'string'
	},
	// 联系地址
	{
		name : 'contactAddress',
		type : 'string'
	},
	// 联系电话
	{
		name : 'contactPhone',
		type : 'string'
	},
	// 联系人
	{
		name : 'contact',
		type : 'string'
	},
	// 联系人电话
	{
		name : 'mobilePhone',
		type : 'string'
	},
	// 公司类别
	{
		name : 'agentCompanySort',
		type : 'string'
	},
	// 备注
	{
		name : 'notes',
		type : 'string'
	},
	// 是否启用
	{
		name : 'active',
		type : 'string'
	},
	// 虚拟编码
	{
		name : 'virtualCode',
		type : 'string'
	}, 
	// 接口服务编码
	{
		name : 'interFaceServiceCode',
		type : 'string'
	}, 
	// 核销方式
	{
		name : 'verificationMethods',
		type : 'string'
	}, 
	// 核销方式名称
	{
		name : 'verificationMethodsName',
		type : 'string'
	}]
});
Ext.define('Foss.baseinfo.commonSelector.LdpAgencyCompanyStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.LdpAgencyCompanyModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryAllInfos.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'expressCompanyVo.businessPartnerExpressEntityList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.CommonLdpAgencyCompanySelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonLdpAgencyCompanySelector',
	displayField : 'agentCompanyName',// 显示名称
	valueField : 'agentCompanyCode',// 值
	active:null,
	management:null,
	managementName:null,
	agentCompanyCode:null,
	queryParam : 'expressCompanyVo.businessPartnerExpressEntity.agentCompanyName',// 查询参数
	showContent : '{agentCompanyName}&nbsp;&nbsp;&nbsp;{agentCompanyCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.LdpAgencyCompanyStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}	
			if (!Ext.isEmpty(config.active)) {
				searchParams['expressCompanyVo.businessPartnerExpressEntity.active'] = config.active;
			}
			if (!Ext.isEmpty(config.agentCompanyCode)) {
				searchParams['expressCompanyVo.businessPartnerExpressEntity.agentCompanyCode'] = config.agentCompanyCode;
			}
			if (!Ext.isEmpty(config.managementName)) {
				searchParams['expressCompanyVo.businessPartnerExpressEntity.managementName'] = config.managementName;
			}
			if (!Ext.isEmpty(config.management)) {
				searchParams['expressCompanyVo.businessPartnerExpressEntity.management'] = config.management;
			}
});
		me.callParent([cfg]);
	}
});

//快递代理公司网点
Ext.define('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel', {
	extend : 'Ext.data.Model',
	fields : [// 代理网点编码
      {name:'agentDeptCode',type:'string'},
      // 快递代理网点名称
      {name:'agentDeptName',type:'string'},
      // 快递代理公司
      {name:'agentCompany',type:'string'},
      // 快递代理公司名称（扩展）
      {name:'agentCompanyName',type:'string'},
      // 管理部门
      {name:'management',type:'string'},
      // 管理部门名称（扩展）
      {name:'managementName',type:'string'},
      // 快递代理网点简称
      {name:'simplename',type:'string'},
      // 省份编码
      {name:'provCode',type:'string'},
      // 省份名称（扩展）
      {name:'provName',type:'string'},
      // 正单联系电话
      {name:'airWaybillTel',type:'string'},
      // 正单开单名称
      {name:'airWaybillName',type:'string'},
      // 城市编码
      {name:'cityCode',type:'string'},
      // 城市名称（扩展）
      {name:'cityName',type:'string'},
      // 联系人
      {name:'contact',type:'string'},
      // 区县编码
      {name:'countyCode',type:'string'},
      // 区县名称（扩展）
      {name:'countyName',type:'string'},
      // 网点地址
      {name:'address',type:'string'},
      // 是否可自提
      {name:'pickupSelf',type:'string'},
      // 是否送货上门
      {name:'pickupToDoor',type:'string'},
      // 是否支持返回签单
      {name:'returnBill',type:'string'},
      // 是否支持货到付款
      {name:'arriveCharge',type:'string'},
      // 是否支持代收货款
      {name:'helpCharge',type:'string'},
      // 是否保价
      {name:'insured',type:'string'},
      // 自提区域
      {name:'pickupArea',type:'string'},
      // 派送区域
      {name:'deliverArea',type:'string'},
      // 可出发
      {name:'leave',type:'string'},
      // 可中转
      {name:'transfer',type:'string'},
      // 可到达
      {name:'arrive',type:'string'},
      // 联系电话
      {name:'contactPhone',type:'string'},
      // 联系人电话
      {name:'mobilePhone',type:'string'},
      // 备注
      {name:'notes',type:'string'},
      // 是否启用
      {name:'active',type:'string'},
      // 虚拟编码
      {name:'virtualCode',type:'string'},
      // 网点类型：快递代理：LD
      {name:'branchtype',type:'string'},
      // 提货网点编码【打印使用】
      {name:'stationNumber',type:'string'},
      //是否是机场
      {name:'isAirport',type:'string'},
      //网点服务坐标编号
      {name:'deptCoordinate',type:'string'},
      //派送区域服务坐标
      {name:'deliveryCoordinate',type:'string'},
      //代理公司简称Code
      {name:'agentCompanyAbbreviationCode',type:'string'},
      //代收上限
      {name:'agentCollectedUpperLimit',type:'string'}]
});
Ext.define('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonLdpAgencyDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryDeptInfos.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'expressDeptVo.outerBranchExpressEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.CommonLdpAgencyDeptSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonldpagencydeptselector',
			fieldLabel : '快递代理公司网点',
			displayField : 'agentDeptName',// 显示名称
			valueField : 'agentDeptCode',// 值
			active:null,
			management:null,
			managementName:null,
			agentCompanyCode:null,
			agentDeptCode:null,
			queryParam : 'expressDeptVo.outerBranchExpressEntity.agentDeptName',// 查询参数
			showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.CommonLdpAgencyDeptStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}	
					if (!Ext.isEmpty(config.active)) {
						searchParams['expressDeptVo.outerBranchExpressEntity.active'] = config.active;
					}
					if (!Ext.isEmpty(config.agentCompanyCode)) {
						searchParams['expressDeptVo.outerBranchExpressEntity.agentCompany'] = config.agentCompanyCode;
					}
					if (!Ext.isEmpty(config.managementName)) {
						searchParams['expressDeptVo.outerBranchExpressEntity.managementName'] = config.managementName;
					}
					if (!Ext.isEmpty(config.management)) {
						searchParams['expressDeptVo.outerBranchExpressEntity.management'] = config.management;
					}
					if (!Ext.isEmpty(config.management)) {
						searchParams['expressDeptVo.outerBranchExpressEntity.agentDeptCode'] = config.agentDeptCode;
					}
		});
				me.callParent([cfg]);
			}
		});

//****************************经营大区***********************
Ext.define('Foss.baseinfo.commonSelector.SecondCarBigRegionModel', {
	extend : 'Ext.data.Model',
	fields : [
	  //大区名称 
      {name:'name',type:'string'},
      //大区编码
      {name:'code',type:'string'}]
});

Ext.define('Foss.baseinfo.commonSelector.SecondCarBigRegionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.SecondCarBigRegionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryOrgAdministrativeInfoByEntity.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'orgAdministrativeInfoVo.orgAdministrativeInfoEntityList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.SecondCarBigRegionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.bigregionselector',
	fieldLabel : '经营大区',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'orgAdministrativeInfoVo.orgAdministrativeInfoEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.SecondCarBigRegionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['orgAdministrativeInfoVo.orgAdministrativeInfoEntity.bigRegion'] = 'Y';
		});
		me.callParent([cfg]);
	}
});
//***************************经营大区完****************************************

Ext.define('Foss.commonSelector.SecondCarTransferCenterSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.transfercenterselector',
	fieldLabel : '出发中转场',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'orgAdministrativeInfoVo.orgAdministrativeInfoEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.SecondCarBigRegionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['orgAdministrativeInfoVo.orgAdministrativeInfoEntity.transferCenter'] = 'Y';
		});
		me.callParent([cfg]);
	}
});
//*********************************中转场完*************************************

//*********************************接驳点开始************************************
Ext.define('Foss.baseinfo.commonSelector.AccessPointModel', {
	extend : 'Ext.data.Model',
	fields : [
	  //接驳点名称 
      {name:'name',type:'string'},
      //接驳点编码
      {name:'code',type:'string'}]
});

Ext.define('Foss.baseinfo.commonSelector.AccessPointStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.AccessPointModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryAccessPoints.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'accessPointVo.accessPointEntityList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.AccessPointSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.accesspointselector',
	fieldLabel : '接驳点名称',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'accessPointVo.accessPointEntityCondition.name',// 查询参数
	showContent : '{name}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AccessPointStore');
		/*me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});*/
		me.callParent([cfg]);
	}
});
//*******************************************接驳点完*****************************************


//快递车
Ext.define('Foss.baseinfo.commonSelector.ExpressVehicleModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'vehicleNo',
						type : 'string'
					},{
						name : 'vehicleLengthCode',
						type : 'string'
					},{
						name : 'vehicleLengthName',
						type : 'string'
					},{
						name : 'empCode',
						type : 'string'
					},{
						name : 'empName',
						type : 'string'
					},{
						name : 'orgCode',
						type : 'string'
					},{
						name : 'orgName',
						type : 'string'
					},{
						name : 'ownDeptCode',
						type : 'string'
					},{
						name:'active',
					    type:'string'
					},{
						name : 'ownDeptName',
						type : 'string'
					},{
						name : 'mobilePhone',
						type : 'string'
					},{
						name : 'description',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.ExpressVehicleStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ExpressVehicleModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonVehicleNo.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'expressVehicleVo.expressVehicleList',
					totalProperty : 'totalCount'
				}
			}
		});
//快递车车牌
Ext.define('Foss.commonSelector.ExpressVehicleSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonExpressVehicleselector',
			displayField : 'vehicleNo',// 显示名称
			valueField : 'vehicleNo',// 值
			orgCode : null,
			orgName : null,
			active : null,
			queryParam : 'expressVehicleVo.expressVehicle.vehicleNo',// 查询参数
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{empCode}&nbsp;&nbsp;&nbsp;{orgName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressVehicleStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.orgCode)){
						searchParams['expressVehicleVo.expressVehicle.orgCode'] = config.orgCode;
					}
					if(!Ext.isEmpty(config.orgName)){
						searchParams['expressVehicleVo.expressVehicle.orgName'] = config.orgName;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['expressVehicleVo.expressVehicle.active'] = config.active;
					}
				});
				me.callParent([cfg]);
			}
		});

//快递员
Ext.define('Foss.commonSelector.OldExpressEmployeeSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonOldExpressEmpselector',
			displayField : 'vehicleNo',// 显示名称
			valueField : 'vehicleNo',// 值
			empCode : null,
			empName : null,
			mobilePhone : null,
			active : null,
			districtCodes : null,
			orgCode : null,
			orgName : null,
			vehicleNo : null,
			queryParam : 'expressVehicleVo.expressVehicle.empCode',// 查询参数
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{empName}&nbsp;&nbsp;&nbsp;{orgName}&nbsp;&nbsp;&nbsp;{mobilePhone}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressVehicleStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.empName)){
						searchParams['expressVehicleVo.expressVehicle.empName'] = config.empName;
					}
					if(!Ext.isEmpty(config.mobilePhone)){
						searchParams['expressVehicleVo.expressVehicle.mobilePhone'] = config.mobilePhone;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['expressVehicleVo.expressVehicle.active'] = config.active;
					}
					if(!Ext.isEmpty(config.orgCode)){
						searchParams['expressVehicleVo.expressVehicle.orgCode'] = config.orgCode;
					}
					if(!Ext.isEmpty(config.orgName)){
						searchParams['expressVehicleVo.expressVehicle.orgName'] = config.orgName;
					}
					if(!Ext.isEmpty(config.districtCodes)){
						areaCodes = config.districtCodes.split(',');
						searchParams['expressVehicleVo.expressVehicle.districtCodes'] = areaCodes;
					}
					if(!Ext.isEmpty(config.vehicleNo)){
						searchParams['expressVehicleVo.expressVehicle.vehicleNo'] = config.vehicleNo;
					}
				});
				me.callParent([cfg]);
			}
		});


//快递代理网点+快递点部
Ext.define('Foss.baseinfo.commonSelector.ExpressAndOrgModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'deptName',
						type : 'string'
					},{
						name : 'deptCode',
						type : 'string'
					},{
						name : 'typeName',
						type : 'string'
					},{
						name : 'proCode',
						type : 'string'
					},{
						name : 'proName',
						type : 'string'
					},{
						name : 'cityCode',
						type : 'string'
					},{
						name : 'cityName',
						type : 'string'
					},{
						name : 'countyName',
						type : 'string'
					},{
						name :'countyCode',
					    type :'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.ExpressAndOrgStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ExpressAndOrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryExpressAndOrgInfos.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'vo.ldpOuterBranchsAndOrgList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.ExpressAndOrgSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonExpressAndOrgSelector',
			displayField : 'deptName',// 显示名称
			valueField : 'deptCode',// 值
			active : null,
			deptCode : null,
			cityCode : null,
			cityName : null,
			typeParam : null,
			queryParam : 'vo.ldpOuterBranchsAndOrginfo.deptName',// 查询参数
			showContent : '{deptName}&nbsp;&nbsp;&nbsp;{deptCode}&nbsp;&nbsp;&nbsp;{typeName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressAndOrgStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.deptCode)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.deptCode'] = config.deptCode;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.active'] = config.active;
					}
					if(!Ext.isEmpty(config.cityCode)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.cityCode'] = config.cityCode;
					}
					if(!Ext.isEmpty(config.cityName)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.cityName'] = config.cityName;
					}
					if(!Ext.isEmpty(config.typeParam)){
						searchParams['vo.typeParam'] = config.typeParam;
					}
				});
				me.callParent([cfg]);
			}
		});

//快递代理核销方式
Ext.define('Foss.baseinfo.commonSelector.writeOffTypeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'termsCode',
						type : 'string'
					}, {
						name : 'valueSeq',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'valueName',
						type : 'string'
					}, {
						name : 'valueCode',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.writeOffTypeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.writeOffTypeModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonWriteOffType.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'dataDictionaryValueEntities',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.writeOffTypeSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonwriteOffTypeselector',
//	fieldLabel : '核销方式（快递代理）',
	active : 'Y',
	termsCode : 'LDP_WRITE_OFF_TYPE',
	displayField : 'valueName',// 显示名称
	valueField : 'valueCode',// 值
	queryParam : 'dataDictionaryValueEntity.valueName',// 查询参数
	showContent : '{valueName}&nbsp;&nbsp;&nbsp;{valueCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.writeOffTypeStore');
		me.active = config.active; 
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			if(Ext.isEmpty(config.termsCode)){
				searchParams['dataDictionaryValueEntity.termsCode'] = me.termsCode;
			}else{
				searchParams['dataDictionaryValueEntity.termsCode'] = config.termsCode;
			}
			searchParams['dataDictionaryValueEntity.active'] = me.active;
		});
		me.callParent([cfg]);
	}
});

//快递员单选（new）
Ext.define('Foss.baseinfo.commonSelector.ExpressEmployeeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.EmployeeModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryExpressEmp.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'employeeVo.employeeList',
			totalProperty : 'totalCount'
		}
	}
});

//快递员排班需求 zhangyongxue 218392
Ext.define('Foss.commonSelector.ExpressEmployeeSelectorYingYeName', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonExpressemployeeselectorYingYeName',
	//fieldLabel : '快递人员',
	listWidth : 300,// 设置下拉框宽度
	displayField : 'empName',// 显示名称
	valueField : 'empCode',// 值
	deptCode : null,
	empCode : null,
	yingYeName : null,
	queryParam : 'employeeVo.employeeDetail.queryParam',// 查询参数
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}&nbsp;&nbsp;&nbsp;{yingYeName}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);
	me.deptCode = config.deptCode
	me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressEmployeeStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
				params : searchParams
			});
		}
		if(!Ext.isEmpty(config.deptCode)){
			searchParams['employeeVo.employeeDetail.orgCode'] = config.deptCode;	
		}else if(!Ext.isEmpty(config.empCode)) {
			searchParams['employeeVo.employeeDetail.empCode'] = config.empCode;	
		}else if(!Ext.isEmpty(config.yingYeName)) {
			searchParams['employeeVo.employeeDetail.yingYeName'] = config.yingYeName;	
		}
	})
	me.callParent([cfg]);
	}
	});

Ext.define('Foss.commonSelector.ExpressEmployeeSelector', {
extend : 'Foss.commonSelector.CommonCombSelector',
alias : 'widget.commonExpressemployeeselector',
//fieldLabel : '快递人员',
listWidth : 300,// 设置下拉框宽度
displayField : 'empName',// 显示名称
valueField : 'empCode',// 值
deptCode : null,
empCode : null,
yingYeName : null,
queryParam : 'employeeVo.employeeDetail.queryParam',// 查询参数
showContent : '{empName}&nbsp;&nbsp;&nbsp;{empCode}',// 显示表格列
constructor : function(config) {
var me = this, cfg = Ext.apply({}, config);
me.deptCode = config.deptCode
me.store = Ext.create('Foss.baseinfo.commonSelector.ExpressEmployeeStore');
me.store.addListener('beforeload', function(store, operation, eOpts) {
	var searchParams = operation.params;
	if (Ext.isEmpty(searchParams)) {
		searchParams = {};
		Ext.apply(operation, {
			params : searchParams
		});
	}
	if(!Ext.isEmpty(config.deptCode)){
		searchParams['employeeVo.employeeDetail.orgCode'] = config.deptCode;	
	}else if(!Ext.isEmpty(config.empCode)) {
		searchParams['employeeVo.employeeDetail.empCode'] = config.empCode;	
	}
})
me.callParent([cfg]);
}
});


//快递代理网点+虚拟营业部+自有网点
Ext.define('Foss.baseinfo.commonSelector.ldpAndExpressAndOrgModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'deptName',
						type : 'string'
					},{
						name : 'deptCode',
						type : 'string'
					},{
						name : 'typeName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.ldpAndExpressAndOrgStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ldpAndExpressAndOrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryLdpAndExpressAndOrgInfos.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'vo.ldpOuterBranchsAndOrgList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.ldpAndExpressAndOrgSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonLdpAndExpressAndOrgSelector',
			displayField : 'deptName',// 显示名称
			valueField : 'deptCode',// 值
			active : 'Y',
			deptCode : null,
			queryParam : 'vo.ldpOuterBranchsAndOrginfo.deptName',// 查询参数
			showContent : '{deptName}&nbsp;&nbsp;&nbsp;{deptCode}&nbsp;&nbsp;&nbsp;{typeName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.ldpAndExpressAndOrgStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.deptCode)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.deptCode'] = config.deptCode;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['vo.ldpOuterBranchsAndOrginfo.active'] = config.active;
					}
				});
				me.callParent([cfg]);
			}
		});


//财务组织
Ext.define('Foss.baseinfo.commonSelector.financialOrgModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					},{
						name : 'code',
						type : 'string'
					},{
						name : 'parentOrgCode',
						type : 'string'
					},{
						name : 'parentOrgName',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.financialOrgStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.financialOrgModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonFinancialOrganizationsByEntity.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'financialOrgs',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.commonfinancialOrgSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonfinancialOrgSelector',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			active : 'Y',
			queryParam : 'entity.name',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.financialOrgStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.deptCode)){
						searchParams['entity.code'] = config.code;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['entity.active'] = config.active;
					}
				});
				me.callParent([cfg]);
			}
		});


//信息部查询
Ext.define('Foss.baseinfo.commonSelector.infoDeptModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					},{
						name : 'code',
						type : 'string'
					}]
		});
Ext.define('Foss.baseinfo.commonSelector.infoDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.infoDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonInfoDept.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'infoDeptVo.infoDeptEntityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.commoninfodeptSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commoninfodeptSelector',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			active : 'Y',
			queryParam : 'infoDeptVo.infoDeptEntity.name',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				if (!Ext.isEmpty(config.showContent)) {
					me.showContent = config.showContent;
				} 
				me.store = Ext.create('Foss.baseinfo.commonSelector.infoDeptStore');
				me.store.addListener('beforeload', function(store, operation,eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if(!Ext.isEmpty(config.deptCode)){
						searchParams['infoDeptVo.infoDeptEntity.code'] = config.code;
					}
					if(!Ext.isEmpty(config.active)){
						searchParams['infoDeptVo.infoDeptEntity.active'] = config.active;
					}
				});
				me.callParent([cfg]);
			}
		});


Ext.define('Foss.baseinfo.commonSelector.DataDictionaryModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'valueCode',
				type : 'string'
			}, {
				name : 'valueName',
				type : 'string'
			}, {
				name : 'termsName',
				type : 'string'
			}, {
				name : 'termsCode',
				type : 'string'
			}					
			]
});

//数据字典

Ext.define('Foss.baseinfo.commonSelector.DataDictionaryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.DataDictionaryModel',
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonDataDictionaryValueExact.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'dataDictionaryVo.dataDictionaryValueEntityList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.commonrdatadictionarymodelelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonrdatadictionarymodelelector',
//	fieldLabel : '数据字典查询',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'valueName',// 显示名称
	valueField : 'valueCode',
	queryParam : 'dataDictionaryVo.dataDictionaryValueEntity.valueName',// 查询参数
	termsCode:'UUMS_POSITION',//上级词条
	showContent : '{valueName}&nbsp;&nbsp;&nbsp;{valueCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
//		me.termsCode = config.termsCode;
		me.store = Ext.create('Foss.baseinfo.commonSelector.DataDictionaryStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
						
			if(!Ext.isEmpty(config.termsCode)){
				searchParams['dataDictionaryVo.dataDictionaryValueEntity.termsCode'] = config.termsCode;
			}else{
				searchParams['dataDictionaryVo.dataDictionaryValueEntity.termsCode'] = me.termsCode;
			}
//			if(!Ext.isEmpty(config.termsCode)){
//				searchParams['dataDictionaryVo.dataDictionaryValueEntity.termsCode'] = me.termsCode;
//			} 
	 });
		me.callParent([cfg]);
    }
});

//小区

Ext.define('Foss.baseinfo.commonSelector.pickupAndDeliverySmallZoneModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'regionCode',
				type : 'string'
			}, {
				name : 'regionName',
				type : 'string'
			}, {
				name : 'management',
				type : 'string'
			}, {
				name : 'managementName',
				type : 'string'
			}, {
				name : 'regionType',
				type : 'string'
			},{
				name : 'gisid',
				type : 'string'
			},{
				name : 'gisArea',
				type : 'string'
			},{
				name : 'bigzonecode',
				type : 'string'
			},{
				name : 'bigzoneName',
				type : 'string'
			},{
				name : 'active',
				type : 'string'
			}, {
				name : 'notes',
				type : 'string'
			}, {
				name : 'virtualCode',
				type : 'string'
			}, {
				name : 'provCode',
				type : 'string'
			}, {
				name : 'provName',
				type : 'string'
			}, {
				name : 'cityCode',
				type : 'string'
			}, {
				name : 'cityName',
				type : 'string'
			}, {
				name : 'countyCode',
				type : 'string'
			}, {
				name : 'countyName',
				type : 'string'
			}]
});

Ext.define('Foss.commonOrgSelector.pickupAndDeliverySmallZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.pickupAndDeliverySmallZoneModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/querySmallZone.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});


//小区公共选择器
Ext.define('Foss.commonOrgSelector.DynamicPickupAndDeliverySmallZoneSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicPickupAndDeliverySmallzonecombselector',
	//fieldLabel : '动态部门单选',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	regionCode:null,
	regionName:null,
	management:null,
	active:'Y',
	notes:null,
	regionType:null,
	type : 'ORG',
	gisid:null,
	gisArea:null,
	bigzonecode:null,
	virtualCode:null,
	managementName:null,
	bigzoneName:null,
	provCode:null,
	provName:null,
	cityCode:null,
	cityName:null,
	countyCode:null,
	countyName:null,
	queryParam : 'objectVo.queryParam',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonOrgSelector.pickupAndDeliverySmallZoneStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		// 传递的部门类型是多种
		var types = null;
		if (!Ext.isEmpty(config.types)) {
			types = config.types.split(',');
			searchParams['objectVo.smallZoneEntity.types'] = types;
		}
		if (!Ext.isEmpty(config.regionCode)) {
			searchParams['objectVo.smallZoneEntity.regionCode'] = config.regionCode;
		}
		if (!Ext.isEmpty(config.regionName)) {
			searchParams['objectVo.smallZoneEntity.regionName'] = config.regionName;
		}
		if (!Ext.isEmpty(config.management)) {
			searchParams['objectVo.smallZoneEntity.management'] = config.management;
		}
		if (!Ext.isEmpty(config.managementName)) {
			searchParams['objectVo.smallZoneEntity.managementName'] = config.managementName;
		}
		if (!Ext.isEmpty(config.active)) {
			searchParams['objectVo.smallZoneEntity.active'] = config.active;
		}
		if (!Ext.isEmpty(config.notes)) {
			searchParams['objectVo.smallZoneEntity.notes'] = config.notes;
		}
		if (!Ext.isEmpty(config.virtualCode)) {
			searchParams['objectVo.smallZoneEntity.virtualCode'] = config.virtualCode;
		}
		if (!Ext.isEmpty(config.provCode)) {
			searchParams['objectVo.smallZoneEntity.provCode'] = config.provCode;
		}
		if (!Ext.isEmpty(config.provName)) {
			searchParams['objectVo.smallZoneEntity.provName'] = config.provName;
		}
		if (!Ext.isEmpty(config.cityCode)) {
			searchParams['objectVo.smallZoneEntity.cityCode'] = config.cityCode;
		}
		if (!Ext.isEmpty(config.cityName)) {
			searchParams['objectVo.smallZoneEntity.cityName'] = config.cityName;
		}
		if (!Ext.isEmpty(config.countyCode)) {
			searchParams['objectVo.smallZoneEntity.countyCode'] = config.countyCode;
		}
		if (!Ext.isEmpty(config.countyName)) {
			searchParams['objectVo.smallZoneEntity.countyName'] = config.countyName;
		}
		
	})
	me.callParent([cfg]);
	}
});

//-------------------------------------------------------------------------------------
//接送货大小区
Ext.define('Foss.baseinfo.commonSelector.pickupAndDeliveryBigZoneModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',type : 'string'
			}, {
				name : 'regionCode',type : 'string'
			}, {
				name : 'regionName',type : 'string'
			}, {
				name : 'management',type : 'string'
			}, {
				name : 'managementName',type : 'string'
			}, {
				name : 'active',type : 'string'
			}, {
				name : 'notes',type : 'string'
			}, {
				name : 'virtualCode',type : 'string'
			}, {
				name : 'provCode',type : 'string'
			}, {
				name : 'provName',type : 'string'
			}, {
				name : 'cityCode',type : 'string'
			}, {
				name : 'cityName',type : 'string'
			}, {
				name : 'countyCode',type : 'string'
			}, {
				name : 'countyName',type : 'string'
			}]
});


Ext.define('Foss.commonOrgSelector.pickupAndDeliveryBigZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.pickupAndDeliveryBigZoneModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryBigZone.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.bigZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//大区
Ext.define('Foss.commonOrgSelector.DynamicPickupAndDeliveryBigZoneSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicPickupAndDeliveryBigcombselector',
	//fieldLabel : '动态部门单选',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	regionCode:null,
	regionName:null,
	management:null,
	active:'Y',
	notes:null,
	regionType:null,
	type : 'ORG',
	gisid:null,
	gisArea:null,
	bigzonecode:null,
	virtualCode:null,
	managementName:null,
	bigzoneName:null,
	provCode:null,
	provName:null,
	cityCode:null,
	cityName:null,
	countyCode:null,
	countyName:null,
	queryParam : 'objectVo.queryParam',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonOrgSelector.pickupAndDeliveryBigZoneStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		// 传递的部门类型是多种
		var types = null;
		if (!Ext.isEmpty(config.types)) {
			types = config.types.split(',');
			searchParams['objectVo.bigZoneEntity.types'] = types;
		}
		if (!Ext.isEmpty(config.regionCode)) {
			searchParams['objectVo.bigZoneEntity.regionCode'] = config.regionCode;
		}
		if (!Ext.isEmpty(config.regionName)) {
			searchParams['objectVo.bigZoneEntity.regionName'] = config.regionName;
		}
		if (!Ext.isEmpty(config.management)) {
			searchParams['objectVo.bigZoneEntity.management'] = config.management;
		}
		if (!Ext.isEmpty(config.managementName)) {
			searchParams['objectVo.bigZoneEntity.managementName'] = config.managementName;
		}
		if (!Ext.isEmpty(config.active)) {
			searchParams['objectVo.bigZoneEntity.active'] = config.active;
		}
		if (!Ext.isEmpty(config.notes)) {
			searchParams['objectVo.bigZoneEntity.notes'] = config.notes;
		}
		if (!Ext.isEmpty(config.virtualCode)) {
			searchParams['objectVo.bigZoneEntity.virtualCode'] = config.virtualCode;
		}
		if (!Ext.isEmpty(config.provCode)) {
			searchParams['objectVo.bigZoneEntity.provCode'] = config.provCode;
		}
		if (!Ext.isEmpty(config.provName)) {
			searchParams['objectVo.bigZoneEntity.provName'] = config.provName;
		}
		if (!Ext.isEmpty(config.cityCode)) {
			searchParams['objectVo.bigZoneEntity.cityCode'] = config.cityCode;
		}
		if (!Ext.isEmpty(config.cityName)) {
			searchParams['objectVo.bigZoneEntity.cityName'] = config.cityName;
		}
		if (!Ext.isEmpty(config.countyCode)) {
			searchParams['objectVo.bigZoneEntity.countyCode'] = config.countyCode;
		}
		if (!Ext.isEmpty(config.countyName)) {
			searchParams['objectVo.bigZoneEntity.countyName'] = config.countyName;
		}
	})
	me.callParent([cfg]);
	}
});
//快递收派大区多选公共选择器
Ext.define('Foss.baseinfo.commonSelector.expressDeliveryBigZoneModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'regionCode'
			}, {
				name : 'regionName'
			}, {
				name : 'management'
			}, {
				name : 'managementName'
			}, {
				name : 'active'
			}, {
				name : 'notes'
			}, {
				name : 'virtualCode'
			}, {
				name : 'provCode'
			}, {
				name : 'provName'
			}, {
				name : 'cityCode'
			}, {
				name : 'cityName'
			}, {
				name : 'countyCode'
			}, {
				name : 'countyName'
			}]
});

Ext.define('Foss.commonOrgSelector.expressDeliveryBigZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.expressDeliveryBigZoneModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryExpressDeliver.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.bigZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//快递大区多选expressDeliveryBigZoneModel
Ext.define('Foss.commonOrgSelector.DynamicExpressBigZoneMultiSelector', {
			extend : 'Foss.commonSelector.CommonMultiCombSelector',
			alias : 'widget.dynamicexpressbigzonemulticombselector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			active:'Y',
			notes:null,
			type : 'ORG',
			virtualCode:null,
			managementName:null,
			provCode:null,
			provName:null,
			cityCode:null,
			cityName:null,
			countyCode:null,
			countyName:null,
			queryParam : 'objectVo.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);    
			me.store = Ext.create('Foss.commonOrgSelector.expressDeliveryBigZoneStore');
			me.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
								params : searchParams
							});
				}
				// 传递的部门类型是多种
				var types = null;
				if (!Ext.isEmpty(config.types)) {
					types = config.types.split(',');
					searchParams['objectVo.expressDeliveryBigZoneEntity.types'] = types;
				}
				if (!Ext.isEmpty(config.regionCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.regionCode'] = config.regionCode;
				}
				if (!Ext.isEmpty(config.regionName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.regionName'] = config.regionName;
				}
				if (!Ext.isEmpty(config.management)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.management'] = config.management;
				}
				if (!Ext.isEmpty(config.managementName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.managementName'] = config.managementName;
				}
				if (!Ext.isEmpty(config.active)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.active'] = config.active;
				}
				if (!Ext.isEmpty(config.notes)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.notes'] = config.notes;
				}
				if (!Ext.isEmpty(config.virtualCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.virtualCode'] = config.virtualCode;
				}
				if (!Ext.isEmpty(config.provCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.provCode'] = config.provCode;
				}
				if (!Ext.isEmpty(config.provName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.provName'] = config.provName;
				}
				if (!Ext.isEmpty(config.cityCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.cityCode'] = config.cityCode;
				}
				if (!Ext.isEmpty(config.cityName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.cityName'] = config.cityName;
				}
				if (!Ext.isEmpty(config.countyCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.countyCode'] = config.countyCode;
				}
				if (!Ext.isEmpty(config.countyName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.countyName'] = config.countyName;
				}
				
			})
			me.callParent([cfg]);
			}
});
//快递收派大区多选公共选择器
Ext.define('Foss.baseinfo.commonSelector.expressDeliveryBigZoneModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'regionCode'
			}, {
				name : 'regionName'
			}, {
				name : 'management'
			}, {
				name : 'managementName'
			}, {
				name : 'active'
			}, {
				name : 'notes'
			}, {
				name : 'virtualCode'
			}, {
				name : 'provCode'
			}, {
				name : 'provName'
			}, {
				name : 'cityCode'
			}, {
				name : 'cityName'
			}, {
				name : 'countyCode'
			}, {
				name : 'countyName'
			}]
});

Ext.define('Foss.commonOrgSelector.expressDeliveryBigZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.expressDeliveryBigZoneModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryExpressDeliver.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.bigZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//快递大区多选expressDeliveryBigZoneModel
Ext.define('Foss.commonOrgSelector.DynamicExpressBigZoneMultiSelector', {
			extend : 'Foss.commonSelector.CommonMultiCombSelector',
			alias : 'widget.dynamicexpressbigzonemulticombselector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			active:'Y',
			notes:null,
			type : 'ORG',
			virtualCode:null,
			managementName:null,
			provCode:null,
			provName:null,
			cityCode:null,
			cityName:null,
			countyCode:null,
			countyName:null,
			queryParam : 'objectVo.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);    
			me.store = Ext.create('Foss.commonOrgSelector.expressDeliveryBigZoneStore');
			me.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
								params : searchParams
							});
				}
				// 传递的部门类型是多种
				var types = null;
				if (!Ext.isEmpty(config.types)) {
					types = config.types.split(',');
					searchParams['objectVo.expressDeliveryBigZoneEntity.types'] = types;
				}
				if (!Ext.isEmpty(config.regionCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.regionCode'] = config.regionCode;
				}
				if (!Ext.isEmpty(config.regionName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.regionName'] = config.regionName;
				}
				if (!Ext.isEmpty(config.management)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.management'] = config.management;
				}
				if (!Ext.isEmpty(config.managementName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.managementName'] = config.managementName;
				}
				if (!Ext.isEmpty(config.active)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.active'] = config.active;
				}
				if (!Ext.isEmpty(config.notes)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.notes'] = config.notes;
				}
				if (!Ext.isEmpty(config.virtualCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.virtualCode'] = config.virtualCode;
				}
				if (!Ext.isEmpty(config.provCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.provCode'] = config.provCode;
				}
				if (!Ext.isEmpty(config.provName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.provName'] = config.provName;
				}
				if (!Ext.isEmpty(config.cityCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.cityCode'] = config.cityCode;
				}
				if (!Ext.isEmpty(config.cityName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.cityName'] = config.cityName;
				}
				if (!Ext.isEmpty(config.countyCode)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.countyCode'] = config.countyCode;
				}
				if (!Ext.isEmpty(config.countyName)) {
					searchParams['objectVo.expressDeliveryBigZoneEntity.countyName'] = config.countyName;
				}
				
			});
			me.callParent([cfg]);
			}
});

//快递收派小区多选公共选择器
Ext.define('Foss.baseinfo.commonSelector.expressDeliverySmallZoneModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'regionCode'
			}, {
				name : 'regionName'
			}, {
				name : 'management'
			}, {
				name : 'managementName'
			}, {
				name : 'regionType'
			},{
				name : 'gisid'
			},{
				name : 'gisArea'
			},{
				name : 'bigzonecode'
			},{
				name : 'bigzoneName'
			},{
				name : 'active'
			}, {
				name : 'notes'
			}, {
				name : 'virtualCode'
			}, {
				name : 'provCode'
			}, {
				name : 'provName'
			}, {
				name : 'cityCode'
			}, {
				name : 'cityName'
			}, {
				name : 'countyCode'
			}, {
				name : 'countyName'
			}]
});

Ext.define('Foss.commonOrgSelector.expressDeliverySmallZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.expressDeliverySmallZoneModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/querySmallRegion.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//快递小区多选expressDeliveryBigZoneModel
Ext.define('Foss.commonOrgSelector.DynamicExpressSmallZoneMultiSelector', {
			extend : 'Foss.commonSelector.CommonMultiCombSelector',
			alias : 'widget.dynamicexpressSmallzonemulticombselector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			active:'Y',
			notes:null,
			regionType:null,
			type : 'ORG',
			gisid:null,
			gisArea:null,
			bigzonecode:null,
			virtualCode:null,
			managementName:null,
			bigzoneName:null,
			provCode:null,
			provName:null,
			cityCode:null,
			cityName:null,
			countyCode:null,
			countyName:null,
			queryParam : 'objectVo.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);    
			me.store = Ext.create('Foss.commonOrgSelector.expressDeliverySmallZoneStore');
			me.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
								params : searchParams
							});
				}
				// 传递的部门类型是多种
				var types = null;
				if (!Ext.isEmpty(config.types)) {
					types = config.types.split(',');
					searchParams['objectVo.expressDeliverySmallZoneEntity.types'] = types;
				}
				if (!Ext.isEmpty(config.regionCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionCode'] = config.regionCode;
				}
				if (!Ext.isEmpty(config.regionName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionName'] = config.regionName;
				}
				if (!Ext.isEmpty(config.management)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.management'] = config.management;
				}
				if (!Ext.isEmpty(config.managementName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.managementName'] = config.managementName;
				}
				if (!Ext.isEmpty(config.active)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.active'] = config.active;
				}
				if (!Ext.isEmpty(config.notes)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.notes'] = config.notes;
				}
				if (!Ext.isEmpty(config.virtualCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.virtualCode'] = config.virtualCode;
				}
				if (!Ext.isEmpty(config.provCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provCode'] = config.provCode;
				}
				if (!Ext.isEmpty(config.provName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provName'] = config.provName;
				}
				if (!Ext.isEmpty(config.cityCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityCode'] = config.cityCode;
				}
				if (!Ext.isEmpty(config.cityName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityName'] = config.cityName;
				}
				if (!Ext.isEmpty(config.countyCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyCode'] = config.countyCode;
				}
				if (!Ext.isEmpty(config.countyName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyName'] = config.countyName;
				}
			});
			me.callParent([cfg]);
			}
});
//快递收派小区单选公共选择器

Ext.define('Foss.commonOrgSelector.DynamicExpressSmallZoneSelector', {
			extend:'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.dynamicexpressSmallzonecombselector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			active:'Y',
			notes:null,
			regionType:null,
			type : 'ORG',
			gisid:null,
			gisArea:null,
			bigzonecode:null,
			virtualCode:null,
			managementName:null,
			bigzoneName:null,
			provCode:null,
			provName:null,
			cityCode:null,
			cityName:null,
			countyCode:null,
			countyName:null,
			queryParam : 'objectVo.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);    
			me.store = Ext.create('Foss.commonOrgSelector.expressDeliverySmallZoneStore');
			me.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
								params : searchParams
							});
				}
				// 传递的部门类型是多种
				var types = null;
				if (!Ext.isEmpty(config.types)) {
					types = config.types.split(',');
					searchParams['objectVo.expressDeliverySmallZoneEntity.types'] = types;
				}
				if (!Ext.isEmpty(config.regionCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionCode'] = config.regionCode;
				}
				if (!Ext.isEmpty(config.regionName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionName'] = config.regionName;
				}
				if (!Ext.isEmpty(config.management)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.management'] = config.management;
				}
				if (!Ext.isEmpty(config.managementName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.managementName'] = config.managementName;
				}
				if (!Ext.isEmpty(config.active)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.active'] = config.active;
				}
				if (!Ext.isEmpty(config.notes)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.notes'] = config.notes;
				}
				if (!Ext.isEmpty(config.virtualCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.virtualCode'] = config.virtualCode;
				}
				if (!Ext.isEmpty(config.provCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provCode'] = config.provCode;
				}
				if (!Ext.isEmpty(config.provName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provName'] = config.provName;
				}
				if (!Ext.isEmpty(config.cityCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityCode'] = config.cityCode;
				}
				if (!Ext.isEmpty(config.cityName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityName'] = config.cityName;
				}
				if (!Ext.isEmpty(config.countyCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyCode'] = config.countyCode;
				}
				if (!Ext.isEmpty(config.countyName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyName'] = config.countyName;
				}
				
			})
			me.callParent([cfg]);
			}
});

//部门职位公共选择器（主要根据部门CODE查询该部门下的所有职位名称及代码）
Ext.define('Foss.baseinfo.commonSelector.employeeTitleModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'empCode'   //员工工号
			}, {
				name : 'empName'   //员工姓名
			}, {
				name : 'title'     //员工职位编码（员工表）
			}, {
				name : 'orgCode'   //部门编码
			}, {
				name : 'orgName'   //部门名称
			}, {
				name : 'titleValueCode'     //员工职位编码（职位表）
			}, {
				name : 'titleValueName'     //员工职位名称
			}, {
				name : 'active'     //有效性
			}]
});
Ext.define('Foss.commonSelector.employeeTitleStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.employeeTitleModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchTitle.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.titleEntityList',
			totalProperty : 'totalCount'
		}
	}
});
Ext.define('Foss.commonSelector.CommonDynamicTitleSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicTitleSelector',
	//fieldLabel : '部门职位单选',
	displayField : 'titleValueName',// 显示职位名称
	valueField : 'titleValueCode',// 职位编码
	titleValueCode:null,
	titleValueName:null,
	empName:null,
	empCode:null,
	orgName:null,
	orgCode:null,
	title:null,
	active:null,
	queryParam : 'objectVo.entity.titleValueName',// 查询参数
	showContent : '{titleValueName}&nbsp;&nbsp;&nbsp;{orgCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.employeeTitleStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.titleValueCode)) {
			searchParams['objectVo.entity.titleValueCode'] = config.titleValueCode;
		}
		if (!Ext.isEmpty(config.titleValueName)) {
			searchParams['objectVo.entity.titleValueName'] = config.titleValueName;
		}
		if (!Ext.isEmpty(config.empName)) {
			searchParams['objectVo.entity.empName'] = config.empName;
		}
		if (!Ext.isEmpty(config.empCode)) {
			searchParams['objectVo.entity.empCode'] = config.empCode;
		}
		if (!Ext.isEmpty(config.orgName)) {
			searchParams['objectVo.entity.orgName'] = config.orgName;
		}
		if (!Ext.isEmpty(config.orgCode)) {
			searchParams['objectVo.entity.orgCode'] = config.orgCode;
		}
		if (!Ext.isEmpty(config.title)) {
			searchParams['objectVo.entity.title'] = config.title;
		}
		if (!Ext.isEmpty(config.active)) {
			searchParams['objectVo.entity.active'] = config.active;
		}
	})
	me.callParent([cfg]);
	}
});

//快递收派小区单选公共选择器
Ext.define('Foss.commonOrgSelector.DynamicExpressSmallZoneSelector', {
			extend:'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.dynamicexpressSmallzonecombselector',
			//fieldLabel : '动态部门单选',
			displayField : 'regionName',// 显示名称
			valueField : 'regionCode',// 值
			regionCode:null,
			regionName:null,
			management:null,
			active:'Y',
			notes:null,
			regionType:null,
			type : 'ORG',
			gisid:null,
			gisArea:null,
			bigzonecode:null,
			virtualCode:null,
			managementName:null,
			bigzoneName:null,
			provCode:null,
			provName:null,
			cityCode:null,
			cityName:null,
			countyCode:null,
			countyName:null,
			queryParam : 'objectVo.queryParam',// 查询参数
			showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
			constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);    
			me.store = Ext.create('Foss.commonOrgSelector.expressDeliverySmallZoneStore');
			me.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
								params : searchParams
							});
				}
				// 传递的部门类型是多种
				var types = null;
				if (!Ext.isEmpty(config.types)) {
					types = config.types.split(',');
					searchParams['objectVo.expressDeliverySmallZoneEntity.types'] = types;
				}
				if (!Ext.isEmpty(config.regionCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionCode'] = config.regionCode;
				}
				if (!Ext.isEmpty(config.regionName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.regionName'] = config.regionName;
				}
				if (!Ext.isEmpty(config.management)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.management'] = config.management;
				}
				if (!Ext.isEmpty(config.managementName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.managementName'] = config.managementName;
				}
				if (!Ext.isEmpty(config.active)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.active'] = config.active;
				}
				if (!Ext.isEmpty(config.notes)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.notes'] = config.notes;
				}
				if (!Ext.isEmpty(config.virtualCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.virtualCode'] = config.virtualCode;
				}
				if (!Ext.isEmpty(config.provCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provCode'] = config.provCode;
				}
				if (!Ext.isEmpty(config.provName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.provName'] = config.provName;
				}
				if (!Ext.isEmpty(config.cityCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityCode'] = config.cityCode;
				}
				if (!Ext.isEmpty(config.cityName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.cityName'] = config.cityName;
				}
				if (!Ext.isEmpty(config.countyCode)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyCode'] = config.countyCode;
				}
				if (!Ext.isEmpty(config.countyName)) {
					searchParams['objectVo.expressDeliverySmallZoneEntity.countyName'] = config.countyName;
				}
				
			});
			me.callParent([cfg]);
			}
});			
//包装供应商公共选择器
Ext.define('Foss.baseinfo.commonSelector.packagingSupplierEntityModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'packagingSupplierCode',//供应商编码
				type :  'String'
			}, {
				name : 'packagingSupplier',//供应商
				type : 'String'
			}, {
				name : 'packagingSupplierPhone',//供应商电话
				type :  'String'
			}, {
				name : 'woodenFrame',//打木架单价（方）
				type :  'String'
			}, {
				name : 'woodPallet',//打木托单价（个）
				type :  'String'
			},{
				name : 'orgCode',//部门编码
				type :  'String'
			},{
				name : 'orgName',
				type :  'String'
			},{
				name : 'orgCodeCode',
				type :  'String'
			},{
				name : 'bagLine',//打包带单价（跟）
				type :  'String'
			},{
				name : 'wood',//木条单价（米）
				type :  'String'
			},{
				name : 'bubblefilm',//气泡膜单价（方）
				type :  'String'
			}, {
				name : 'wrappingFilm',//缠绕膜单价（方）
				type :  'String'
			}, {
				name : 'woodBox',//打木箱（个）
				type :  'String'
			}, {
				name : 'breakageRate',//破损率参数
				type :  'String'
			}, {
				name : 'woodenFrameStartVolume',//打木架起步体积
				type :  'String'
			}, {
				name : 'woodenFrameMin',//打木箱最低一票
				type :  'String'		
			}, {
				name : 'woodBoxStartVolume',//打木箱起步体积
				type :  'String'
			},  {
				name : 'active'
			}]
});
//包装供应商
Ext.define('Foss.commonOrgSelector.packagingSupplierEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.packagingSupplierEntityModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchPackagingSupplier.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.packagingSupplierEntities',
			totalProperty : 'totalCount'
		}
	}
});
//包装供应商公共选择器
Ext.define('Foss.commonOrgSelector.DynamicPackagingSupplierSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicPackagingSupplierSelector',
	displayField : 'packagingSupplier',// 显示名称
	valueField : 'packagingSupplierCode',// 值
	packagingSupplierCode:null,
	packagingSupplier:null,
	orgCode:null,
	orgName:null,
	orgCodeCode:null,
	active:null,
	queryParam : 'objectVo.packagingSupplierEntity.packagingSupplier',// 查询参数
	showContent : '{packagingSupplier}&nbsp;&nbsp;&nbsp;{packagingSupplierCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonOrgSelector.packagingSupplierEntityStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.active)) {
			searchParams['objectVo.packagingSupplierEntity.active'] = config.active;
		}
		if (!Ext.isEmpty(config.orgCode)) {
			searchParams['objectVo.packagingSupplierEntity.orgCode'] = config.orgCode;
		}
		if (!Ext.isEmpty(config.orgName)) {
			searchParams['objectVo.packagingSupplierEntity.orgName'] = config.orgName;
		}
		if (!Ext.isEmpty(config.orgCodeCode)) {
			searchParams['objectVo.packagingSupplierEntity.orgCodeCode'] = config.orgCodeCode;
		}
	})
	me.callParent([cfg]);
	}
});

//部门编码下属车队组公共选择器（主要根据部门CODE查询该部门下的所有车队组名称及代码）
Ext.define('Foss.baseinfo.commonSelector.transTeamModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'orgCode'   //查询部门编码
			}, {
				name : 'orgName'   //查询部门名称
			}, {
				name : 'transTeamCode'     //查询车队组编码
			}, {
				name : 'transTeamName'     //查询车队组名称
			}, {
				name : 'code'     //结果车队组编码
			}, {
				name : 'name'     //结果车队组名称
			}]
});
Ext.define('Foss.commonSelector.transTeamStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.transTeamModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchTransTeam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.entityList',
			totalProperty : 'totalCount'
		}
	}
});
Ext.define('Foss.commonSelector.CommonDynamicTransTeamSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicTransTeamSelector',
	displayField : 'name',// 显示车队组名称
	valueField : 'code',// 车队组编码
	transTeamCode:null,
	transTeamName:null,
	orgName:null,
	orgCode:null,
	queryParam : 'objectVo.entity.transTeamName',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.transTeamStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.transTeamCode)) {
			searchParams['objectVo.entity.transTeamCode'] = config.transTeamCode;
		}
		if (!Ext.isEmpty(config.transTeamName)) {
			searchParams['objectVo.entity.transTeamName'] = config.transTeamName;
		}
		if (!Ext.isEmpty(config.orgName)) {
			searchParams['objectVo.entity.orgName'] = config.orgName;
		}
		if (!Ext.isEmpty(config.orgCode)) {
			searchParams['objectVo.entity.orgCode'] = config.orgCode;
		}
	})
	me.callParent([cfg]);
	}
});

	
//成本中心部门信息表Model
Ext.define('Foss.baseinfo.commonSelector.costCenterDeptModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'deptName',
						type : 'string'
					},{
						name : 'deptCode',
						type : 'string'
					},{
						name : 'typeCode',
						type : 'string'
					},{
						name : 'status',
						type : 'string'
					},{
						name : 'isFreeze',
						type : 'string'
					},{
						name : 'isCostOrgUnit',
						type : 'string'
					}]
		});

//成本中心部门信息表Store
Ext.define('Foss.baseinfo.commonSelector.costCenterDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.costCenterDeptModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonCostDept.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'commonCostDeptVo.commonCostDeptList',
			totalProperty : 'totalCount'
		}
	}
});

//成本中心部门信息表
Ext.define('Foss.commonSelector.commoncostcenterdeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commoncostcenterdeptSelector',
	displayField : 'deptName',// 显示名称
	valueField : 'deptCode',// 值
	deptCode : null,//部门编码 
	deptName:null,//部门名称
	typeCode:null,//部门性质代码
	status:'0',//正常0，已封存1，已撤销2，待撤销3，待清理4
	isFreeze:'0',// 是否冻结 1是0否
	isCostOrgUnit:'1',//是否成本中心 1是 0否
	queryParam : 'commonCostDeptVo.costCenterDeptEntity.deptName',// 查询参数
	showContent : '{deptName}&nbsp;&nbsp;&nbsp;{deptCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config); 
		me.store = Ext.create('Foss.baseinfo.commonSelector.costCenterDeptStore');
		me.store.addListener('beforeload', function(store, operation,eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.active'] = config.active;
			}
			if (!Ext.isEmpty(config.deptCode)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.deptCode'] = config.deptCode;
			}
			if (!Ext.isEmpty(config.deptName)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.deptName'] = config.deptName;
			}
			if (!Ext.isEmpty(config.deptName)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.status'] = config.status;
			}else{
				searchParams['commonCostDeptVo.costCenterDeptEntity.status'] = me.status;
			}
			if (!Ext.isEmpty(config.deptName)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.isFreeze'] = config.isFreeze;
			}else{
				searchParams['commonCostDeptVo.costCenterDeptEntity.isFreeze'] = me.isFreeze;
			}
			if (!Ext.isEmpty(config.deptName)) {
				searchParams['commonCostDeptVo.costCenterDeptEntity.isCostOrgUnit'] = config.isCostOrgUnit;
			}else{
				searchParams['commonCostDeptVo.costCenterDeptEntity.isCostOrgUnit'] = me.isCostOrgUnit;
			}
		});
		me.callParent([cfg]);
	}

});


//--------------------------------------------------------------------------------------------------------------------------

//城市单选公共选择器
Ext.define('Foss.baseinfo.commonSelector.DynamicCityByProvinceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ReginModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchCityByProvince.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'cityVo.cityList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.SearchCityByProvince', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonCityByProvinceselector',
//	fieldLabel : '市',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	queryParam : 'cityVo.name',// 查询参数
	parentId:null,
	isPaging : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.DynamicCityByProvinceStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}					
			if(!Ext.isEmpty(config.parentId)){
				searchParams['cityVo.parentId'] = config.parentId;
			}										
		});
		me.callParent([cfg]);
	}
});
//区县单选公共选择器
Ext.define('Foss.baseinfo.commonSelector.DynamicAreaByCityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.ReginModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchAreaByCity.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'cityVo.areaList',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.SearchAreaByCity', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonAreaByCityselector',
//	fieldLabel : '市',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'name',// 显示名称
	valueField : 'code',
	queryParam : 'cityVo.name',// 查询参数
	parentId:null,
	isPaging : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.DynamicAreaByCityStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}					
			if(!Ext.isEmpty(config.parentId)){
				searchParams['cityVo.parentId'] = config.parentId;
			}										
		});
		me.callParent([cfg]);
	}
});
//三级产品Model
Ext.define('Foss.baseinfo.commonSelector.ThirdLevelProductModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name',
						type : 'string'
					}, {
						name : 'code',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}]
		});
//三级产品Store
Ext.define('Foss.baseinfo.commonSelector.ThirdLevelProductStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.ThirdLevelProductModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonThirdLevelProduct.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'thirdLevelProductVo.thirdLevelProductEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//三级产品单选公共选择器
Ext.define('Foss.commonSelector.ThirdLevelProductSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonthirdlevelproductselector',
//			fieldLabel : '三级产品',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			queryParam : 'thirdLevelProductVo.thirdLevelProductEntity.name',// 查询参数
			showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.ThirdLevelProductStore');
				me.callParent([cfg]);
			}
		});
//三级产品多选公共选择器
Ext.define('Foss.commonSelector.ProductItemMultiSelector', {
			extend : 'Foss.commonSelector.CommonMultiCombSelector',
			alias : 'widget.commonthirdlevelproductmultiselector',
			displayField : 'name',// 显示名称
			valueField : 'code',// 值
			active:'Y',//是否激活
			queryParam : 'thirdLevelProductVo.thirdLevelProductEntity.name',// 查询参数
			showContent : '{name}',// 显示表格列
			isPaging : true,// 分页 
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.ThirdLevelProductStore');
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					if (!Ext.isEmpty(config.active)) {
						searchParams['thirdLevelProductVo.thirdLevelProductEntity.active'] = config.active;
					}
				});
				me.callParent([cfg]);
			}
		});
//订单来源公共选择器
Ext.define('Foss.baseinfo.commonSelector.orderSourceModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'sourceCode',   //订单来源编码
				type : 'string'
			}, {
				name : 'sourceName',   //订单来源名称
				type : 'string'
			}, {
				name : 'active',     //有效性
				type : 'string'
			}]
});
Ext.define('Foss.commonSelector.orderSourceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.orderSourceModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchOrderSource.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.orderSourceEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//订单来源单选公共选择器
Ext.define('Foss.commonSelector.CommonDynamicOrderSourceSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicOrderSourceSelector',
	//fieldLabel : '订单来源单选',
	displayField : 'sourceName',// 显示订单来源名称
	valueField : 'sourceCode',// 订单来源编码
	sourceCode:null,
	sourceName:null,
	active:null,
	queryParam : 'objectVo.entity.sourceName',// 查询参数
	showContent : '{sourceName}&nbsp;&nbsp;&nbsp;{sourceCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.orderSourceStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.sourceCode)) {
			searchParams['objectVo.entity.sourceCode'] = config.sourceCode;
		}
		if (!Ext.isEmpty(config.sourceName)) {
			searchParams['objectVo.entity.sourceName'] = config.sourceName;
		}
		if (!Ext.isEmpty(config.active)) {
			searchParams['objectVo.entity.active'] = config.active;
		}
	});
	me.callParent([cfg]);
	}
});
//订单来源多选公共选择器
Ext.define('Foss.commonSelector.OrderSourceMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonordersourcemultiselector',
	displayField : 'sourceName',// 显示名称
	valueField : 'sourceCode',// 值
	queryParam : 'objectVo.entity.sourceName',// 查询参数
	showContent : '{sourceName}',// 显示表格列
	isPaging : true,// 分页 
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.commonSelector.orderSourceStore');
		me.callParent([cfg]);
	}
});

//客户等级公共选择器
Ext.define('Foss.baseinfo.commonSelector.customerDegreeModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'degreeCode',   //客户等级编码
				type : 'string'
			}, {
				name : 'degreeName',   //客户等级名称
				type : 'string'
			}]
});
Ext.define('Foss.commonSelector.customerDegreeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.customerDegreeModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchCustomerDegree.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.customerDegreeEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//客户等级单选公共选择器
Ext.define('Foss.commonSelector.CommonDynamiccustomerDegreeSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicCustomerDegreeSelector',
	displayField : 'degreeName',// 显示客户等级名称
	valueField : 'degreeCode',// 客户等级编码
	degreeCode:null,
	degreeName:null,
	queryParam : 'objectVo.entity.degreeName',// 查询参数
	showContent : '{degreeName}&nbsp;&nbsp;&nbsp;{degreeCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.customerDegreeStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.degreeCode)) {
			searchParams['objectVo.entity.degreeCode'] = config.degreeCode;
		}
		if (!Ext.isEmpty(config.degreeName)) {
			searchParams['objectVo.entity.degreeName'] = config.degreeName;
		}
	});
	me.callParent([cfg]);
	}
});
//客户等级多选公共选择器
Ext.define('Foss.commonSelector.CustomerDegreeMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commoncustomerdegreemultiselector',
	displayField : 'degreeName',// 显示名称
	valueField : 'degreeCode',// 值
	queryParam : 'objectVo.entity.degreeName',// 查询参数
	showContent : '{degreeName}',// 显示表格列
	isPaging : true,// 分页 
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.commonSelector.customerDegreeStore');
		me.callParent([cfg]);
	}
});

//客户行业公共选择器
Ext.define('Foss.baseinfo.commonSelector.customerProfessionModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'professionCode',   //客户行业编码（一级行业）
				type : 'string'
			}, {
				name : 'professionName',   //客户行业名称（一级行业）
				type : 'string'
			}]
});
Ext.define('Foss.commonSelector.customerProfessionStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.customerProfessionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchCustomerProfession.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.customerProfessionEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//客户行业单选公共选择器（一级）
Ext.define('Foss.commonSelector.CommonDynamiccustomerProfessionSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicCustomerProfessionSelector',
	displayField : 'professionName',// 显示客户行业名称
	valueField : 'professionCode',// 客户行业编码
	professionCode:null,
	professionName:null,
	queryParam : 'objectVo.entity.professionName',// 查询参数
	showContent : '{professionName}&nbsp;&nbsp;&nbsp;{professionCode}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.customerProfessionStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.professionCode)) {
			searchParams['objectVo.entity.professionCode'] = config.professionCode;
		}
		if (!Ext.isEmpty(config.professionName)) {
			searchParams['objectVo.entity.professionName'] = config.professionName;
		}
	});
	me.callParent([cfg]);
	}
});
//客户行业多选公共选择器(一级行业)
Ext.define('Foss.commonSelector.CustomerProfessionMultiSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commoncustomerprofessionmultiselector',
	displayField : 'professionName',// 显示名称
	valueField : 'professionCode',// 值
	queryParam : 'objectVo.entity.professionName',// 查询参数
	showContent : '{professionName}',// 显示表格列
	isPaging : true,// 分页 
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.commonSelector.customerProfessionStore');
		me.callParent([cfg]);
	}
});



Ext.define('Foss.baseinfo.commonSelector.sendDistrictMapModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'districtCode',
				type : 'string'
			},{
				name : 'zoneCode',
				type : 'string'
			},{
				name : 'zoneName',
				type : 'string'
			}]
});

Ext.define('Foss.baseinfo.commonSelector.sendDistrictMapStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.sendDistrictMapModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchZoneName.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'objectVo.sendDistrictMapEntities',
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.commonSelector.commonsendDistrictMapSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonSendDistrictMapSelector',
	displayField : 'zoneName',// 显示名称
	valueField : 'zoneCode',// 值
	districtCode : null,//部门编码 
	zoneName:null,
	zoneCode:null,
	transferCenterCode:null, //外场编码
	queryParam : 'objectVo.sendDistrictMapEntity.zoneName',// 查询参数
	showContent : '{zoneName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config); 
		me.store = Ext.create('Foss.baseinfo.commonSelector.sendDistrictMapStore');
		me.store.addListener('beforeload', function(store, operation,eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['objectVo.sendDistrictMapEntity.active'] = config.active;
			}
			if (!Ext.isEmpty(config.districtCode)) {
				searchParams['objectVo.sendDistrictMapEntity.districtCode'] = config.districtCode;
			}
			if (!Ext.isEmpty(config.zoneName)) {
				searchParams['objectVo.sendDistrictMapEntity.zoneName'] = config.zoneName;
			}
			if (!Ext.isEmpty(config.transferCenterCode)) {
				searchParams['objectVo.sendDistrictMapEntity.transferCenterCode'] = config.transferCenterCode;
			}
		});
		me.callParent([cfg]);
	}
});

//部门编码下属车队组公共选择器（主要根据部门CODE查询该部门下的所有车队组名称及代码）
Ext.define('Foss.baseinfo.commonSelector.transTeamModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'orgCode'   //查询部门编码
			}, {
				name : 'orgName'   //查询部门名称
			}, {
				name : 'transTeamCode'     //查询车队组编码
			}, {
				name : 'transTeamName'     //查询车队组名称
			}, {
				name : 'code'     //结果车队组编码
			}, {
				name : 'name'     //结果车队组名称
			}]
});
Ext.define('Foss.commonSelector.transTeamStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.transTeamModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/searchTransTeam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'objectVo.entityList',
			totalProperty : 'totalCount'
		}
	}
});
Ext.define('Foss.commonSelector.CommonDynamicTransTeamSelector', {
	extend:'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonDynamicTransTeamSelector',
	displayField : 'name',// 显示车队组名称
	valueField : 'code',// 车队组编码
	transTeamCode:null,
	transTeamName:null,
	orgName:null,
	orgCode:null,
	queryParam : 'objectVo.entity.transTeamName',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);    
	me.store = Ext.create('Foss.commonSelector.transTeamStore');
	me.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
			Ext.apply(operation, {
						params : searchParams
					});
		}
		if (!Ext.isEmpty(config.transTeamCode)) {
			searchParams['objectVo.entity.transTeamCode'] = config.transTeamCode;
		}
		if (!Ext.isEmpty(config.transTeamName)) {
			searchParams['objectVo.entity.transTeamName'] = config.transTeamName;
		}
		if (!Ext.isEmpty(config.orgName)) {
			searchParams['objectVo.entity.orgName'] = config.orgName;
		}
		if (!Ext.isEmpty(config.orgCode)) {
			searchParams['objectVo.entity.orgCode'] = config.orgCode;
		}
	})
	me.callParent([cfg]);
	}
});
//空运代理网点（包括外场）
Ext.define('Foss.baseinfo.commonSelector.CommonAirAgentAndDeptModel', {
	extend : 'Ext.data.Model',
	fields : [
      // id.
      {name:'id',type:'string'},
      // 名称
      {name:'agentDeptName',type:'string'},
      // 编码.
      {name:'agentDeptCode',type:'string'},
      //联系电话
      {name:'contactPhone',type:'string'},
      //城市名称
      {name:'cityName',type:'string'},
      //城市编码
      {name:'cityCode',type:'string'},
      // 是否启用
      {name:'active',type:'string'}
      ]
});
Ext.define('Foss.baseinfo.commonSelector.AirAgentAndDeptStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.CommonAirAgentAndDeptModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryCommonAirAgentCondition.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'vo.entityList',
					totalProperty : 'totalCount'
				}
			}
		});

Ext.define('Foss.commonSelector.AirAgentDeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonAiragentAndDeptselector',
	agentDeptCode : null,
	displayField : 'agentDeptName',// 显示名称
	valueField : 'agentDeptCode',// 值
	queryParam : 'vo.entity.agentDeptName',// 查询参数
	showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
	isPaging : true,// 分页
	destCityCode:null,//目的站城市代码
	isEnterQuery : true,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.AirAgentAndDeptStore');
		me.agentDeptCode = config.agentDeptCode;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					//目的站城市代码
			       if (!Ext.isEmpty(me.destCityCode)) {
			            searchParams['vo.entity.cityCode'] = me.destCityCode;
			       }
					    searchParams['vo.entity.agentDeptCode'] = me.agentDeptCode;
				});
		me.callParent([cfg]);
	}
});

//接货大区Model
Ext.define('Foss.baseinfo.commonSelector.BigRegionsModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					}
				    ]
		});
//接货大区Store
Ext.define('Foss.baseinfo.commonSelector.BigRegionsStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.BigRegionsModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/search.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'bigZoneVo.bigZoneEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//接货大区
Ext.define('Foss.commonSelector.BigRegionsSelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonbigregionsselector',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	management : null,//管理部门
	queryParam : 'bigZoneVo.entity.regionName',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.BigRegionsStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.management)){
				searchParams['bigZoneVo.entity.management'] = config.management;
			} 
		});
		me.callParent([cfg]);
	}
});

//送货大区Model
Ext.define('Foss.baseinfo.commonSelector.BigRegionsDEModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					}
				    ]
		});
//送货大区Store
Ext.define('Foss.baseinfo.commonSelector.BigRegionsDEStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.commonSelector.BigRegionsDEModel',
			pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/searchDE.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'bigZoneVo.bigZoneEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//送货大区
Ext.define('Foss.commonSelector.BigRegionsDESelector', {
	extend : 'Foss.commonSelector.CommonMultiCombSelector',
	alias : 'widget.commonbigregionsdeselector',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	management : null,//管理部门
	queryParam : 'bigZoneVo.entity.regionName',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.BigRegionsDEStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.management)){
				searchParams['bigZoneVo.entity.management'] = config.management;
			} 
		});
		me.callParent([cfg]);
	}
});
//接货小区Model
Ext.define('Foss.baseinfo.commonSelector.SmallRegionsModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'management',
						type : 'string'
					}]
		});
//接货小区Store
Ext.define('Foss.baseinfo.commonSelector.SmallRegionsStore', {
			extend : 'Ext.data.Store',
			pageSize : 10,
			model : 'Foss.baseinfo.commonSelector.SmallRegionsModel',
			proxy : {
				type : 'ajax',
				url : '../baseinfo/find.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'smallZoneVo.smallZoneEntities',
					totalProperty : 'totalCount'
				}
			}
		});
//接货小区
Ext.define('Foss.commonSelector.SmallRegionsSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonsmallregionsselector',
	displayField : 'regionName',// 显示名称
	valueField : 'regionCode',// 值
	management : null,//管理部门
	queryParam : 'smallZoneVo.entity.regionName',// 查询参数
	showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.SmallRegionsStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(config.management)){
						searchParams['smallZoneVo.entity.management'] = config.management;
			}
			});
		    me.callParent([cfg]);
	  }
});

//一般纳税人Model
Ext.define('Foss.baseinfo.commonSelector.generaltaxpayerModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'taxId',
				type : 'string'
			},{
				name : 'billTitle',
				type : 'string'
			},{
				name : 'bankName',
				type : 'string'
			},{
				name : 'bankNumber',
				type : 'string'
			},{
				name : 'isTaxpayer',
				type : 'string'
			},{
				name : 'regTel',
				type : 'string'
			},{
				name : 'regAddress',
				type : 'string'
			},{
				name : 'createDepartment',
				type : 'string'
			}]
});

//一般纳税人Store
Ext.define('Foss.baseinfo.commonSelector.generaltaxpayerStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.generaltaxpayerModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonTaxpayerCondition.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'taxpayerVo.taxpayerEntitys',
			totalProperty : 'totalCount'
		}
	}
});

//一般纳税人信息
Ext.define('Foss.commonSelector.Commongeneraltaxpayerselector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commongeneraltaxpayerselector',
	displayField : 'billTitle',// 显示名称
	valueField : 'bankNumber',// 值
	queryParam : 'taxpayerVo.taxpayerEntity.billTitle',// 查询参数
	showContent : '{billTitle}&nbsp;&nbsp;&nbsp;{bankNumber}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : false,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.generaltaxpayerStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
				});
		me.callParent([cfg]);
	}
});

//合伙人网点Model
Ext.define('Foss.baseinfo.commonSelector.twoLevelNetworkModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			},{
				name : 'code',
				type : 'string'
			},{
				name : 'name',
				type : 'string'
			}]
});

//二级合伙人网点名称Store
Ext.define('Foss.baseinfo.commonSelector.twoLevelNetworkStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.twoLevelNetworkModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommontwoLevelNetworkCondition.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'departmentVo.depEntitys',
			totalProperty : 'totalCount'
		}
	}
});

//二级合伙人网点名称
Ext.define('Foss.commonSelector.CommonTwoLevelNetworkselector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonTwoLevelNetworkselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'departmentVo.depEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : false,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.twoLevelNetworkStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
				});
		me.callParent([cfg]);
	}
});
//一级合伙人网点名称Store
Ext.define('Foss.baseinfo.commonSelector.leagueSaleDeptStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.commonSelector.twoLevelNetworkModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonLeagueSaleDeptCondition.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'departmentVo.depEntitys',
			totalProperty : 'totalCount'
		}
	}
});

//一级合伙人网点名称
Ext.define('Foss.commonSelector.CommonLeagueSaleDeptselector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonLeagueSaleDeptselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'departmentVo.depEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
	isPaging : true,// 分页
	isEnterQuery : false,// 回车查询
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.leagueSaleDeptStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
				});
		me.callParent([cfg]);
	}
});

/**
 * **********************定义公共多选选择器  mjq ********************
 */
Ext.define('Foss.commonSelector.CommonMutiSelectorModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'valueCode'
	},{
		name:'valueName'
	}]
});
//定义多选择器的表格Store
Ext.define('Foss.commonSelector.CommonMutiSelectorStore',{
	extend:'Ext.data.Store',
	model:'Foss.commonSelector.CommonMutiSelectorModel'
	
});
//定义多选择器的表格Form
Ext.define('Ext.commonSelector.CommonMutiSelectorForm',{
	extend:'Ext.form.Panel',
	frame:true,
	title:'查询条件',
	autoScroll:true,
 	selector:null,
 	height:120,
 	defaults:{
 		labelWidth:75,
 		columnWidth:.7
 	},
 	layout:'column',
 	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [config.selector,{
	 		xtype:'button',
	 		text:'新增',
	 		height:24,
	 		columnWidth:.1,
	 		handler:function(){
	 			var selector = this.up('form').items.items[0];
	 			var window  = this.up('window');
	 			if(Ext.isEmpty(selector.getValue())){
	 				return false;
	 			}else{
	 				var gird = this.up('window').items.items[1];
		 			var gridStore = gird.store;
		 			var storeDataArray = gridStore.collect('valueCode');
		 			if(Ext.typeOf(selector.getValue())=='array'){
		 				var rawValueList;
		 				if(!Ext.isEmpty(selector.getRawValue())){
		 					rawValueList = selector.getRawValue().split(',');
		 				}
		 				for(var i=0;i<selector.getValue().length;i++){
		 					if(!Ext.isEmpty(storeDataArray) && storeDataArray.indexOf(selector.getValue()[i])>=0){
		 						continue;
		 					}
		 					var model = new Foss.commonSelector.CommonMutiSelectorModel();
		 					if(!Ext.isEmpty(rawValueList[i])){
		 						model.set('valueName',rawValueList[i]);
		 					}
				 			model.set('valueCode',selector.getValue()[i])
				 			gridStore.add(model);
		 				}
		 			}else{
		 				if(Ext.isEmpty(storeDataArray) || storeDataArray.indexOf(selector.getValue())==-1){
		 					var model = new Foss.commonSelector.CommonMutiSelectorModel();
				 			model.set('valueName',selector.getRawValue());
				 			model.set('valueCode',selector.getValue())
				 			gridStore.add(model);
	 					}
		 			}
		 			selector.reset();
	 			}
	 		}
	 	},{
	 		xtype:'button',
	 		text:'清空选择',
	 		height:24,
	 		columnWidth:.13,
	 		handler:function(){
	 			var gird = this.up('window').items.items[1];
	 			var gridStore = gird.store;
	 			gridStore.removeAll();
	 		}
	 	}];
		me.callParent([cfg]);
	}
});

Ext.define('Ext.commonSelector.CommonMutiSelectorGrid', {
	extend : 'Ext.grid.Panel',
	title :'选择内容',
	frame : true,
	height : 350,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	maxRecords:null,
	store : Ext.create('Foss.commonSelector.CommonMutiSelectorStore'),
	defaults : {
		align : 'center',
		margin : '5 0 5 0'
	},
	viewConfig : {
		enableTextSelection : true
	},
	columns : [{
		xtype : 'actioncolumn',
		header : '操作列',
		width : 110,
		align : 'center',
		items : [{
					iconCls : 'deppon_icons_delete',
					tooltip : '删除',
					handler : function(grid, rowIndex, colIndex) {
						Ext.Msg.confirm('温馨提示','是否要删除？',function(o){
					 		//提示是否要删除
					 		if(o=='yes'){
					 			//获取要删除行
					 			var selection = grid.getStore().getAt(rowIndex);
					 			//删除该条记录
					 			grid.store.remove(selection);
					 		}
					 	});
					}
				}]
	}, {
		header : '名称',
		dataIndex : 'valueName',
		width:window.screen.width*0.15
	},{
		header : '编码',
		dataIndex:'valueCode',
		width:window.screen.width*0.15
	}],
	initComponent : function() {
		var me = this;
		me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'bottom',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
						xtype : 'container',
						border : false,
						html : '&nbsp;',
						columnWidth : .9
					}, {
						xtype : 'button',
						text : '保存',
						columnWidth : .09,
						handler : function(){
							var me =this; var grid = me.up('grid');var parentSelector = me.up('window').parentSelector;
							parentSelector.setRawValue(null); parentSelector.setValue(null);parentSelector.valueList = [];
							if(!Ext.isEmpty(grid.store) &&grid.store.data.length>0){
								var valueName =""; var valueCodeList = new Array();
								grid.store.each(function(e){
									if(Ext.isEmpty(valueName)){
										valueName =e.get("valueName");
									}else{
										valueName = valueName+","+e.get("valueName");
									}
									valueCodeList.push(e.get("valueCode"));
								});
								parentSelector.setRawValue(valueName);
								parentSelector.valueList = valueCodeList;
							}
							me.up('window').close();
						}
					}]
				}];
		me.callParent();
	}
});


//声明录入付款单window
Ext.define('Foss.commonSelector.CommonMutiSelectorWindow',{
	extend:'Ext.window.Window',
	title:'选择界面',
	width:window.screen.width*0.5,
	modal:true,
	selector:null,
	maxRecords:50,
	parentSelector:null,
	constrainHeader: true,
	closeAction:'hide',
	closable:false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items =[Ext.create('Ext.commonSelector.CommonMutiSelectorForm',{
						'selector':config.selector}),
					Ext.create('Ext.commonSelector.CommonMutiSelectorGrid')];
		me.callParent([cfg]);
	}
	
});

//*************************多选选择器*******************
Ext.define('Foss.commonSelector.CommonMutiSelector',{
	extend:'Ext.form.field.ComboBox',
	alias :'widget.commonMutiSelector',
	triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
	queryMode:'local',
	selector:null,
	editable:false,
	queryWindow:null,
	valueList:[],
	maxRecords:20,//最大选择查询条数
	store:null,
	listWidth:300,
	onTriggerClick:function(){
		var me = this;var window = me.queryWindow;
		if(!Ext.isEmpty(window)){
			window.show();
		}
	},
	listeners:{
		specialkey: function(field, e){
			var me = this;var window = me.queryWindow;
			if (e.getKey() == e.ENTER) {
				if(!Ext.isEmpty(window)){
					window.show();
				}
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		if(Ext.isEmpty(me.queryWindow)){
			me.queryWindow = Ext.create('Foss.commonSelector.CommonMutiSelectorWindow',{
				'selector':config.selector,'maxRecords':config.maxRecords,'parentSelector':me
			});
		}
		me.callParent([cfg]);
	}
});
//车队
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'longHaulFleetName',//长途车队名称
		type : 'string'
	},{
		name:'longHaulFleetCode',//长途车队编码
		type : 'string'
	},{
		name:'departmentName',//下辖部门名称
		type : 'string'
	},{
		name:'departmentCode',//下辖部门编码
		type : 'string'
	},{
		name:'trafficCode',//行车编码简称
		type : 'string'
	}]
});
Ext.define('Foss.baseinfo.commonSelector.vehicledrivingStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.vehicledriving.VehicleDrivingModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryCommonVehicleDriving.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'vehicleDrivingVo.vehicleDrivingEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//所属车队
Ext.define('Foss.commonSelector.VehicleDrivingSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonvehicledrivingselector',
	displayField : 'longHaulFleetName',// 显示名称
	valueField : 'longHaulFleetCode',// 值
	queryParam : 'vehicleDrivingVo.vehicleDrivingEntity.longHaulFleetName',// 查询参数
	showContent : '{longHaulFleetName}&nbsp;&nbsp;&nbsp;{longHaulFleetCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.vehicledrivingStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});
		me.callParent([cfg]);
	}
});


//合伙人加收方案 目的站model--twoLevelNetworkModel

Ext.define('Foss.baseinfo.commonSelector.twoLevelSaleDepartmentModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			},{
				name : 'code',
				type : 'string'
			},{
				name : 'name',
				type : 'string'
			},{
				name : 'isTwoLevelNetwork',
				type : 'string'
			},{
				name : 'networkModel',
				type : 'string'
			}]
});

/*合伙人到达加收方案 ： 目的站点名称*/
Ext.define('Foss.baseinfo.commonSelector.twolevelVehAgencyDeptStore', {
      extend : 'Ext.data.Store',
      model : 'Foss.baseinfo.commonSelector.twoLevelSaleDepartmentModel',
      pageSize : 10,
      proxy : {
        type : 'ajax',
        url : '../baseinfo/queryCommonTwolevelVehAgencyDept.action',
        actionMethods : 'POST',// 否则可能会出现中文乱码
        reader : {
          type : 'json',
          root : 'saleDepartmentEntitys',
          totalProperty : 'totalCount'
        }
      }
    });

Ext.define('Foss.commonSelector.twolevelVehAgencyDeptSelector', {
      extend : 'Foss.commonSelector.CommonCombSelector',
      alias : 'widget.commonTwolevelvehagencydeptselector',
//      fieldLabel : '',
      displayField : 'name',// 显示名称
      valueField : 'code',// 值
      queryParam : 'saleDepartmentEntity.name',// 查询参数
      showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
      constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext
            .create('Foss.baseinfo.commonSelector.twolevelVehAgencyDeptStore');
        me.callParent([cfg]);
      }
    });



/*合伙人model*/

Ext.define('Foss.baseinfo.commonSelector.partnerDepartmentModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			},{
				name : 'code',
				type : 'string'
			},{
				name : 'name',
				type : 'string'
			}]
});

/*合伙人汽运价格查询 ： 出发站点*/
Ext.define('Foss.baseinfo.commonSelector.partnerDeptStore', {
      extend : 'Ext.data.Store',
      model : 'Foss.baseinfo.commonSelector.partnerDepartmentModel',
      pageSize : 10,
      proxy : {
        type : 'ajax',
        url : '../baseinfo/queryCommonPartnerDeptCondition.action',
        actionMethods : 'POST',// 否则可能会出现中文乱码
        reader : {
          type : 'json',
          root : 'saleDepartmentEntitys',
          totalProperty : 'totalCount'
        }
      }
    });

Ext.define('Foss.commonSelector.partnerDeptSelector', {
    extend : 'Foss.commonSelector.CommonCombSelector',
    alias : 'widget.partnercombselector',
    displayField : 'name',// 显示名称
  	valueField : 'code',// 值
  	queryParam : 'saleDepartmentEntity.name',// 查询参数
  	showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
  	isPaging : true,// 分页
  	isEnterQuery : false,// 回车查询
  	constructor : function(config) {
  		var me = this, cfg = Ext.apply({}, config);
  		me.store = Ext.create('Foss.baseinfo.commonSelector.partnerDeptStore');
  		me.store.addListener('beforeload', function(store, operation, eOpts) {
  					var searchParams = operation.params;
  					if (Ext.isEmpty(searchParams)) {
  						searchParams = {};
  						Ext.apply(operation, {
  									params : searchParams
  								});
  					}
  				});
  		me.callParent([cfg]);
  	}
  });

//事业部
Ext.define('Foss.baseinfo.indexFossVehicleCost.businessDivisionModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'businessCode',//事业部编码
		type : 'string'
	},{
		name:'businessName',//事业部名称
		type : 'string'
	},{
		name:'regionalCode',//大区编码
		type : 'string'
	},{
		name:'regionalName',//大区名称
		type : 'string'
	}]
});
Ext.define('Foss.baseinfo.commonSelector.businessDivisionStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.indexFossVehicleCost.businessDivisionModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryBusinessDivision.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'inviteFossVehicleCostVo.inviteFossVehicleCostEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//所属事业部
Ext.define('Foss.commonSelector.businessDivisionSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonbusinessdivisionselector',
	displayField : 'businessName',// 显示名称
	valueField : 'businessCode',// 值
	queryParam : 'inviteFossVehicleCostVo.inviteFossVehicleCostEntity.businessName',// 查询参数
	showContent : '{businessName}&nbsp;&nbsp;&nbsp;{businessCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.businessDivisionStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});
		me.callParent([cfg]);
	}
});

//大区
Ext.define('Foss.baseinfo.indexFossVehicleCost.regionalModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'businessCode',//事业部编码
		type : 'string'
	},{
		name:'businessName',//事业部名称
		type : 'string'
	},{
		name:'regionalCode',//大区编码
		type : 'string'
	},{
		name:'regionalName',//大区名称
		type : 'string'
	}]
});
Ext.define('Foss.baseinfo.commonSelector.regionalStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.indexFossVehicleCost.regionalModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryRegional.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'inviteFossVehicleCostVo.inviteFossVehicleCostEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//所属大区
Ext.define('Foss.commonSelector.regionalSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonregionalselector',
	displayField : 'regionalName',// 显示名称
	valueField : 'regionalCode',// 值
	queryParam : 'inviteFossVehicleCostVo.inviteFossVehicleCostEntity.regionalName',// 查询参数
	showContent : '{regionalName}&nbsp;&nbsp;&nbsp;{regionalCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.regionalStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});
		me.callParent([cfg]);
	}
});

//快递分部和点部
Ext.define('Foss.baseinfo.indexFossVehicleCost.expressageModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'expressageCode',//快递部门编码
		type : 'string'
	},{
		name:'expressageName',//快递部门名称
		type : 'string'
	}]
});
Ext.define('Foss.baseinfo.commonSelector.expressageStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.indexFossVehicleCost.expressageModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../baseinfo/queryExpressage.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'inviteFossVehicleCostVo.inviteCommonExpressageEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//所属快递点部和分部
Ext.define('Foss.commonSelector.expressageSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonexpressageselector',
	displayField : 'expressageName',// 显示名称
	valueField : 'expressageCode',// 值
	queryParam : 'inviteFossVehicleCostVo.inviteCommonExpressageEntity.expressageName',// 查询参数
	showContent : '{expressageName}&nbsp;&nbsp;&nbsp;{expressageCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.expressageStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * **********************定义公共多选选择器  mjq  结束********************
 */
