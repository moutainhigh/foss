// 制保留2位小数，如：2，会在2后面补上00.即2.00
baseinfo.toDecimal = function(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x * 100) / 100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}
	while (s.length <= rs + 2) {
		s += '0';
	}
	return s;
}
// 制保留0位小数，如：2.00，会在2
baseinfo.toParseInt = function(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x * 100) / 100;
	var s = f.toString();
	var rs = s.indexOf('.');
	return s;
}

// 转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
// Ajax请求--json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.sendDistrictMap.QueryFormParameterStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'valueCode',
						type : 'string'
					}, {
						name : 'valueName',
						type : 'string'
					}],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items' // 定义读取JSON数据的根对象
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
baseinfo.sendDistrictMap.deleteIds = new Array();
baseinfo.sendDistrictMap.oldIds = new Array();
//件区删除list
baseinfo.sendDistrictMap.delteItemAreas=new Array();
/**
 * 派送货区行政区域映射表单
 */
Ext.define('Foss.baseinfo.sendDistrictMap.QuerysendDistrictMapForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.fromTitle'), // 查询
	frame : true,
	defaults : {
		margin : '5 15 5 5'
		// labelWidth: 60
		// columnWidth: 1/3
	},
	bodyStyle : 'overflow-y:auto;',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name : 'organizationName', // 外场名称
			columnWidth : .3,
			labelWidth : 40,
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			transferCenter : 'Y',// --或者查询外场
			fieldLabel : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.organizationName'),// 外场
			listeners : {
				// select: function (text, records, eops) {
				// me.getForm().findField('organizationCode').setValue(records[0].get('orgCode'));
				// me.getForm().findField('transferCode').setValue(records[0].get('code'));
				// },
				added : function(container, component, number, eOpts) {
					var roles = FossUserContext.getCurrentUserRoleCodes();
					var falge = false;
					// if(roles!=null&&roles.length>0){
					// roles.each(function (item, index, length) {
					// if("FOSS_OPERATION_ANALYSIS_QUERY"==item){
					// falge=true;
					// }
					// });
					// }
					var flage = 1;
					var i = roles.length;
					while (i--) {// 营运分析查询员 Code
						if (roles[i] == "FOSS_OPERATION_ANALYSIS_QUERY") {
							flage = 2;
						}
					}
					/**
					 * 业务要求 查询的时候 营运分析查询员 角色 可以查询所有，外场人员 只能查询自己的顶级外场
					 */
					if (flage != 2) {
						var params = {
							'sendDistrictMapVo' : {
								'sendDistrictMapEntity' : {
									'transferCenterCode' : FossUserContext
											.getCurrentDeptCode()
								}
							}
						};
						var successFun = function(json) {
							me
									.getForm()
									.findField('organizationName')
									.setCombValue(
											json.sendDistrictMapVo.sendDistrictMapEntity.transferCenterName,
											json.sendDistrictMapVo.sendDistrictMapEntity.transferCenterCode);
							me.getForm().findField('organizationName')
									.setReadOnly(true)
						};
						var failureFun = function(json) {
							if (Ext.isEmpty(json)) {
								baseinfo.showErrorMes(baseinfo.sendDistrictMap
										.i18n('foss.baseinfo.requestTimeout'));// 请求超时
							} else {
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo
								.realPath('qureyTransferCenter.action');
						baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);

					}
				}
			}
		}, {
			fieldLabel : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.cityProvArea'),// '行政区域',
			columnWidth : .7,
			// id:'test_ssq',
			labelWidth : 100,
			provinceWidth : 160,
			cityWidth : 160,
			cityLabel : '市',
			cityName : 'cityCode',// 名称
			provinceLabel : '省',
			provinceName : 'provCode',// 省名称
			areaLabel : '县',
			areaName : 'districtCode',// 县名称
			areaWidth : 160,
			type : 'P-C-C',
			xtype : 'linkregincombselector',
			provinceIsBlank : true,
			cityIsBlank : true,
			areaIsBlank : true,
			areaLabelWidth : null
		}], me.fbar = [{
			xtype : 'button',
			width : 70,
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.reset'),// 重置
			disabled : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapQueryButton'),
			hidden : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapQueryButton'),
			margin : '0 800 0 0',
			handler : function() {
				var roles = FossUserContext.getCurrentUserRoleCodes();
				var falge = false;
				var flage = 1;
				var i = roles.length;
				while (i--) {// 营运分析查询员 Code
					if (roles[i] == "FOSS_OPERATION_ANALYSIS_QUERY") {
						flage = 2;
					}
				}
				/**
				 * 业务要求 查询的时候 营运分析查询员 角色 可以查询所有，外场人员 只能查询自己的顶级外场
				 */
				if (flage != 2) {
					me.down('linkregincombselector').setReginValue(null, null,
							'1');// 省份
					me.down('linkregincombselector').setReginValue(null, null,
							'2');// 市
					me.down('linkregincombselector').setReginValue(null, null,
							'3');// formRecord.get('countyName'),formRecord.get('countyCode'),'3');//区
				} else {
					me.getForm().reset();
				}

			}
		}, {
			xtype : 'button',
			width : 70,
			cls : 'yellow_button',
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.query'),// 查询
			disabled : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapQueryButton'),
			hidden : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapQueryButton'),
			handler : function() {
				var form = this.up('form');
				// baseinfo.expressRegionDistr.expressCityListQuery(form)
				// 获取form及其参数值
				var form = form.getForm();
				var districtCode = form.findField('districtCode').getValue();
				// var orgCode = form.findField('orgCode').getValue();
				var cityCode = form.findField('cityCode').getValue();
				var provCode = form.findField('provCode').getValue();

				// 如果选择了省份，就一定要选择到市
				if (provCode != null && provCode != '') {
					if (cityCode == null || cityCode == ''
							|| districtCode == null || districtCode == '') {
						Ext.MessageBox.show({
							title : baseinfo.sendDistrictMap
									.i18n('foss.baseinfo.sendDistrictMap.MsgTitle'),// '温馨提示',
							msg : baseinfo.sendDistrictMap
									.i18n('foss.baseinfo.sendDistrictMap.MsgQurey'),// '通过行政区域查询必须选择到市',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						return false;
					}
				}
				me.up().getSendDistrictMapGrid().getPagingToolbar().moveFirst();
			}
		}]
		me.callParent([cfg]);
	}

});
/**
 * 派送货区行政区域映射 MODEL
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',// 主键Id
						type : 'String'
					}, {
						name : 'transferCenterCode',// 外场编码 CODE
						type : 'String'
					}, {
						name : 'transferCenterName',// 外场名称
						type : 'String'
					}, {
						name : 'goodsAreaCode',// 库区编码 CODE ：
						type : 'String'
					}, {
						name : 'goodsAreaName',// 库区名称 ：
						type : 'String'
					}, {
						name : 'districtCode',// 行政区域编码
						type : 'String'
					}, {
						name : 'districtName',// 行政区域名称
						type : 'String'
					}, {
						name : 'zoneName', // 分区名称
						type : 'String'
					}, {
						name : 'zoneCode',// 分区编码CODE
						type : 'String'
					}, {
						name : 'createTime', // 创建时间
						type : 'date',
						defaultValue : null,
						convert : baseinfo.changeLongToDate
					}, {
						name : 'modifyTime', // 修改时间
						type : 'date',
						defaultValue : null,
						convert : baseinfo.changeLongToDate
					}, {
						name : 'active',// 是否启用
						type : 'String'
					}, {
						name : 'createUserCode',// 创建人工号
						type : 'String'
					}, {
						name : 'modifyUserCode',// 更新人工号
						type : 'String'
					}, {
						name : 'goodsType',// 货区类型
						type : 'String'
					},{
						name:'sendDistrictItemAreaEntitys',//件区List
						type:'List'
					}]
		});
/**
 * 件区model
 */
Ext.define('Foss.baseinfo.sendDistrictMap.itemAreaModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'scopeStart',// 范围（起点）
						type : 'String'
					}, {
						name : 'scopeEnd',// 范围（终点）
						type : 'String'
					}, {
						name : 'length',// 件区长度（米）
						type : 'String'
					}, {
						name : 'width',// 件区宽度（米）
						type : 'String'
					}, {
						name : 'height',// 件区高度（米）
						type : 'String'
					}, {
						name : 'abscissa',// 横坐标
						type : 'String'
					}, {
						name : 'ordinate',// 纵坐标
						type : 'String'
					}, {
						name : 'volume',// 件区体积
						type : 'String'
					}, {
						name : 'itemAreaName',// 件区名称
						type : 'String',
						convert:function(v, record){
						 if(!Ext.isEmpty(record.data.scopeStart)&&!Ext.isEmpty(record.data.scopeEnd)){
						 	return record.data.scopeStart+'件-'+record.data.scopeEnd+'件区';
						 }
						 return null;
						}
					},{
					 name:'actionType',//对件区表格的操作类型 add新增 update修改 delete删除
					 type:'String'
					}]

		});
/**
 * 派送货区行政区域映射 Store
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.sendDistrictMap.SendDistrictMapModel',// 派送货区行政区域MODEL
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo
						.realPath('querySendDistrictMapByCondition.action'),// 请求地址
				reader : {
					type : 'json',
					root : 'sendDistrictMapVo.sendDistrictMapEntities',// 获取的数据
					totalProperty : 'totalCount'// 总个数
				}
			}
		});
Ext.define('Foss.baseinfo.sendDistrictMap.ItemAreaStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.sendDistrictMap.itemAreaModel',// 件区的MODEL
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : null,// 请求地址
				reader : {
					type : 'json'
				}
			}
		});
/**
 * 派送货区行政区域映射 行政区域面板
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapArea', {
	extend : 'Ext.grid.Panel',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	frame : true,
	width : 260,
	height : 180,
	columns : [{
		dataIndex : 'districtName',
		text : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.cityProvArea'),// 行政区域
		flex : 1
	}, {
		dataIndex : 'id',
		text : 'id',
		hidden : true
	}, {
		dataIndex : 'districtCode',
		text : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.cityProvArea'),// 行政区域
		hidden : true
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		};
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.store = baseinfo.getStore(null,
				'Foss.baseinfo.sendDistrictMap.SendDistrictMapModel', null, []);
		me.tbar = [{
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.voidDistrictCode'), // 移除行政区域
			disabled : false,
			handler : function() {
				var selections = me.getSelectionModel().getSelection();
				if (selections.length == 0) {
					baseinfo.showErrorMes('请选择要作废记录');
					return;
				}
				if (me.up('panel').up('panel').isUpdate) {// 如果是更新窗口时,移除行政区域的时候就在后台把数据删除掉,根据ID
					for (var x = 0; x < selections.length; x++) {
						if (selections[x].data.id == null
								|| selections[x].data.id != "") {
							baseinfo.sendDistrictMap.deleteIds
									.push(selections[x].data.id);
							baseinfo.sendDistrictMap.deleteDistictCodeIds
									.push(selections[x].data.districtCode)
						}
					}
					me.getStore().remove(selections);
				} else {
					me.getStore().remove(selections);
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 派送货区行政区域映射 新增修改 外场信息 和库区信息 from panel
 */
Ext.define('Foss.baseinfo.sendDistrictMap.AddUpdateTransferOrAreaForm', {
			extend : 'Ext.form.Panel',
			frame : false,
			height : 30,
			// collapsible: true,设置为 true 则允许 fieldset 可以收缩
			defaults : {
				// colspan : 1,
				margin : '5 200 0 5',
				maxLength : 40,
				// allowBlank:false,
				labelWidth : 100
				// anchor : '100%'
			},
			defaultType : 'textfield',
			layout : {
				type : 'table',
				columns : 2
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [{
							xtype : 'textfield',
							fieldLabel : '外场名称',
							name : 'transferCenterName',
							width : 280,
							labelWidth : 80
						}, {
							xtype : 'textfield',
							hidden : true,
							fieldLabel : '外场Code',
							name : 'transferCenterCode',
							width : 30
						}, {
							xtype : 'textfield',
							name : 'goodsAreaName',
							fieldLabel : '库区名称',
							width : 280,
							labelWidth : 80
						}, {
							xtype : 'textfield',
							name : 'goodsAreaCode',
							hidden : true,
							fieldLabel : '库区Code',
							width : 30
						}];
				me.callParent([cfg]);
			}

		});
/**
 * 派送货区行政区域映射 新增修改 分区和行政区域 from panel
 */
Ext.define('Foss.baseinfo.sendDistrictMap.AddUpdateValueForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	height : 300,
	collapsible : true,
	defaults : {
		colspan : 1,
		margin : '5 50 5 5',
		maxLength : 50,
		allowBlank : false,
		labelWidth : 100,
		anchor : '100%'
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	// layout:'column',
	addsendDistrictMapList : null, // 新增派送货区行政区域映射的集合
	deletesendDistrictMapList : null,// 作废派送货区行政区域映射的集合
	sendDistrictMapArea : null,//
	getsendDistrictMapArea : function() {
		if (this.sendDistrictMapArea == null) {
			this.sendDistrictMapArea = Ext
					.create('Foss.baseinfo.sendDistrictMap.SendDistrictMapArea');
		}
		return this.sendDistrictMapArea;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items=[
		  
	   {
			xtype : 'combobox',
			name : 'goodsType',
			fieldLabel : baseinfo.sendDistrictMap.i18n('foss.baseinfo.sendDistrictMap.goodsType'),//'货物类型',
			width : 280,
			colspan : 1,
			labelWidth : 80,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.sendDistrictMap.QueryFormParameterStore', {
						data : {
							'items' : [{
								'valueCode' : 'send',
								'valueName' : baseinfo.sendDistrictMap
										.i18n('foss.baseinfo.sendDistrictMap.goodsType.send')//派送区
							}, {
								'valueCode' : 'personally',
								'valueName' : baseinfo.sendDistrictMap
										.i18n('foss.baseinfo.sendDistrictMap.goodsType.personally')//自提区
							}]
						}
					}),
			 listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var panel=this.up().up();
						var addUpdateItemAreaGrid =panel.getAddUpdateItemAreaGrid();
						if(oldValue[0].data.valueCode==='personally'){
							var container=form.query('container');
							form.query('label')[0].setVisible(false);
							container[1].setVisible(false);
							container[2].setVisible(false);
							form.setHeight(120);
							form.getForm().findField('zoneName').setValue(baseinfo.sendDistrictMap
										.i18n('foss.baseinfo.sendDistrictMap.goodsType.personally'));
							form.getForm().findField('zoneName').setReadOnly(true);
							addUpdateItemAreaGrid.setHeight(380);
						}else{
						  var container=form.query('container');
							form.query('label')[0].setVisible(true);
							container[1].setVisible(true);
							container[2].setVisible(true);
							form.setHeight(280);
							form.getForm().findField('zoneName').setValue(null);
							form.getForm().findField('zoneName').setReadOnly(false);
							addUpdateItemAreaGrid.setHeight(380);
						}
						
//						var city = this.up()
//								.query('commonCityByProvinceselector')[0];
//						if (null != city) {
//							city.parentId = oldValue[0].data.code;
//						}

					}
				},			
			 value : 'send'
	     },{
			name : 'zoneName',
			fieldLabel : baseinfo.sendDistrictMap.i18n('foss.baseinfo.sendDistrictMap.zoneName'), // 分区名称
			width : 260,
			labelWidth : 60,
			maxLength : 60,
			maxLengthText : baseinfo.sendDistrictMap.i18n('foss.baseinfo.sendDistrictMap.zoneName.maxLengthText'),//'只能输入60个字符',
			colspan : 2,
			allowBlank : false
		},{
			name : 'zoneCode',
			hidden : true,
			colspan : 3,
			fieldLabel : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.zoneCode'), // 分区Code
			width : 280
		}, {
			xtype : 'container',
			labelWidth : 60,
			width : 250,
			layout : {
				type : 'table',
				columns : 1,
				margin : '5 5 30 5'
			},
			fieldLabel : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.cityProvArea'),// '行政区域',
			items : [{
				xtype : 'commonprovinceselector',
				name : 'provinceCode',
				fieldLabel : '省',
				labelWidth : 60,
				listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var city = this.up()
								.query('commonCityByProvinceselector')[0];
						if (null != city) {
							city.parentId = oldValue[0].data.code;
						}

					}
				}
			}, {
				fieldLabel : '市',
				forceSelection : true,
				xtype : 'commonCityByProvinceselector',
				name : 'cityCode',
				queryMode : 'local',
				labelWidth : 60,
				listeners : {
					expand : function(field, eOpts) {
						var province = field.up()
								.query('commonprovinceselector')[0];
						if (null == province.getValue()
								|| "" == province.getValue()) {
							Ext.MessageBox.alert('提示', '省份不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
					},
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var county = this.up()
								.query('commonAreaByCityselector')[0];
						if (null != county) {
							county.parentId = oldValue[0].data.code;
						}

					}
				}

			}, {
				fieldLabel : '县',

				xtype : 'commonAreaByCityselector',
				labelWidth : 60,
				queryMode : 'local',
				name : 'districtCode',
				listeners : {
					expand : function(field, eOpts) {
						var city = field.up()
								.query('commonCityByProvinceselector')[0];
						if (null == city.getValue() || "" == city.getValue()) {
							Ext.MessageBox.alert('提示', '城市不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);
									}
								});
					},
					select : function(comb, records, obj) {
						var districtCode = me.getForm()
								.findField('districtCode').getValue();
						var districtName = me.getForm()
								.findField('districtCode').getRawValue();
						// 分区编码
						var zoneCode = me.getForm().findField('zoneCode')
								.getValue();
						// 分区Name
						var zoneName = me.getForm().findField('zoneName')
								.getValue();
						if (zoneName == null || '' == zoneName) {
							Ext.MessageBox.alert('FOSS提醒您', '分区名称不能为空！');
							return;
						}
						var transferCenterCode = this.up('panel').up('panel')
								.down('form').getForm()
								.findField('transferCenterCode').getValue();
						var sendDistrictMapModel = new Foss.baseinfo.sendDistrictMap.SendDistrictMapModel();
						var params = {
							'sendDistrictMapVo' : {
								'sendDistrictMapEntity' : {
									'zoneName' : zoneName,
									'districtCode' : districtCode,
									'transferCenterCode' : transferCenterCode
								}
							}
						};
						if (!Ext.isEmpty(zoneCode)) {
							params.sendDistrictMapVo.sendDistrictMapEntity.zoneCode = zoneCode;
						}
						var successFun = function(response) {
							var sendDistrictMap = response.sendDistrictMapVo.sendDistrictMapEntity;
							// 若库中已经存在该映射派送货区关系
							if (!Ext.isEmpty(sendDistrictMap)) {
								if (Ext.isEmpty(zoneCode)) {
									if (zoneName == sendDistrictMap.zoneName) {
										baseinfo.showErrorMes('已经存在分区名字为：'
												+ zoneName + '的映射派送货区关系！请勿再添加');
										return;
									}
									if (districtCode == sendDistrictMap.districtCode) {
										baseinfo.showErrorMes('已经存在：'
												+ districtName
												+ '的映射派送货区关系！请勿再添加');
										return;
									}
								} else {
									if (districtCode == sendDistrictMap.districtCode) {
										baseinfo.showErrorMes('已经存在：'
												+ districtName
												+ '的映射派送货区关系！请勿再添加');
										return;
									}
								}

							}
							// 把分区编码添加到model中
							sendDistrictMapModel.set('zoneCode', zoneCode);
							// 把分区名称添加到model中
							sendDistrictMapModel.set('districtName',
									districtName);
							var boolean = false;
							me.getsendDistrictMapArea().getStore().each(
									function(record) {
										// 若库中已展示的grid中已经存在该卫星点，请勿重复添加
										if (record.get('districtCode') == records[0]
												.get('code')) {
											boolean = true;
										}
									});
							if (boolean) {
								Ext.MessageBox.alert('FOSS提醒您', '已经存在'
												+ records[0].get('name')
												+ '的映射派送货区关系,请勿再添加！');
								return;
							}
							// //行政区域编码
							sendDistrictMapModel.set('districtCode',
									districtCode);
							// 分区name
							sendDistrictMapModel.set('zoneName', zoneName);
							// 分区code
							sendDistrictMapModel.set('zoneCode', zoneCode);
							// 把选中的实体添加给库中
							me.getsendDistrictMapArea().getStore()
									.add(sendDistrictMapModel);
						}
						var failureFun = function(response) {
							// var json =Ext.decode(response.responseText);
							baseinfo.showErrorMes(response.message);
						}
						var tmpArr = baseinfo.sendDistrictMap.deleteDistictCodeIds;
						for (var d in tmpArr) {
							if (tmpArr[d] == districtCode) {
								// 把分区编码添加到model中
								sendDistrictMapModel.set('zoneCode', zoneCode);
								// 把分区名称添加到model中
								sendDistrictMapModel.set('districtName',
										districtName);
								var boolean = false;
								me.getsendDistrictMapArea().getStore().each(
										function(record) {
											// 若库中已展示的grid中已经存在该卫星点，请勿重复添加
											if (record.get('districtCode') == records[0]
													.get('code')) {
												boolean = true;
											}
										});
								if (boolean) {
									Ext.MessageBox.alert('FOSS提醒您', '已经存在'
													+ records[0].get('name')
													+ '的映射派送货区关系,请勿再添加！');
									return;
								}
								// //行政区域编码
								sendDistrictMapModel.set('districtCode',
										districtCode);
								// 分区name
								sendDistrictMapModel.set('zoneName', zoneName);
								// 分区code
								sendDistrictMapModel.set('zoneCode', zoneCode);
								// 把选中的实体添加给库中
								me.getsendDistrictMapArea().getStore()
										.add(sendDistrictMapModel);
								return;
							}
						}

						var url = baseinfo
								.realPath('querySendDistrictMapBydistrictCodeOrdistrictName.action');
						baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);
					}
				}

			}]
		}, {
			text : '---->',
			xtype : 'label',
			width : 50
		}, me.getsendDistrictMapArea()
	
		];
		me.callParent([cfg]);
	}
});
/**
 * 派送货区行政区域映射 新增修改 件区window 2015-3-1 189284
 */
Ext.define('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.addUpdateItemArea'),// 新增（修改）件区
	width : 670,
	height : 350,
	itemAreaGrid : null,// 件区grid
	modal : true,
	closeAction : 'hidden',
	addUpdateItemAreaFrom : null,
	itemAreaModel:null,//件区model
	actionType:null,//对件区表格的操作类型 add新增 update修改 delete删除(默认add)
	// 监听器
	listeners : {
		beforehide : function(me) {// 隐藏WINDOW的时候清除数据
			me.getAddUpdateItemAreaFrom().getForm().reset();// 表格重置
		},
		beforeshow : function(me) {
		}
	},
	getAddUpdateItemAreaFrom : function() {
		if (this.addUpdateItemAreaFrom == null) {
			this.addUpdateItemAreaFrom = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaFrom');
			this.addUpdateItemAreaFrom.isUpdate = this.isUpdate;
		}
		return this.addUpdateItemAreaFrom;
	},
	// 构造函数
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateItemAreaFrom()];
		me.callParent([cfg]);
	}
});
/**
 * 派送货区行政区域映射 新增修改 件区panel 2015-3-1 189284
 */
Ext.define('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaFrom', {
	extend : 'Ext.form.Panel',
	frame : true,
	height : 300,
	collapsible : true,
	defaults : {
		margin : '15 5 5 5',
		labelWidth : 100,
		windth : 120
	},
	isUpdate : false,// 是否为修改，默认false
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
	         name:'id',
	         hidden : true
	         },{
	         name:'actionType',
	         hidden : true
	         },{
	         name:'itemAreaName',
	         hidden : true
	         },{
	          name:'volume',
	          hidden:true
	         },{
		name : 'scopeStart',
		// decimalPrecision:0,
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeStart'),// 范围（起点）
		step : 1,
		allowDecimals : false,
		allowBlank : false,
		maxValue : 999999,
		minValue : 0,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toParseInt(field.getValue()));
			}
		},
		xtype : 'numberfield'
	}, {
		name : 'scopeEnd',
		// decimalPrecision:0,
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeEnd'),// 范围（终点）
		step : 1,
		allowDecimals : false,
		allowBlank : false,
		maxValue : 999999,
		minValue : 0,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toParseInt(field.getValue()));
			}
		},
		xtype : 'numberfield'
	}, {
		name : 'length',
		// decimalPrecision:2,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.length'),// 件区长度（米）
		step : 1.00,
		allowBlank : false,
		maxValue : 999999,
		minValue : 0,
		xtype : 'numberfield'
	}, {
		name : 'width',
		// decimalPrecision:2,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		step : 1.00,
		allowBlank : false,
		maxValue : 999999,
		xtype : 'numberfield',
		minValue : 0,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.width')
			// 件区宽度（米）
	}, {
		name : 'height',
		// decimalPrecision:2,
		step : 1.00,
		maxValue : 999999,
		allowBlank : false,
		minValue : 0,
		xtype : 'numberfield',
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.height')
			// 件区高度（米）
	}, {
		name : 'abscissa',
		// decimalPrecision:2,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		allowBlank : false,
		step : 1.00,
		maxValue : 999999,
		minValue : -999999,
		xtype : 'numberfield',
		isteners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.abscissa')
			// 横坐标
	}, {
		name : 'ordinate',
		allowBlank : false,
		// decimalPrecision:2,
		xtype : 'numberfield',
		step : 1.00,
		maxValue : 999999,
		minValue : -999999,
		listeners : {
			blur : function(field) {
				// value = parseInt(value, 10);
				field.setValue(baseinfo.toDecimal(field.getValue()));
			}
		},
		fieldLabel : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.ordinate')
			// 纵坐标
	}],
	commitItemAreaInfo : function() {
		var me = this;
		// 获取件区新增修改表单
		var itemAreaForm = me.getForm();
		if (itemAreaForm.isValid()) {
			var itemAreaWindow = me.up('window');
			 itemAreaFormValue = itemAreaForm.getValues();
				/**
				 * 针对于同一个分区，新增件区范围（起点）、范围（终点）时， 系统需要校验不能有重复、包含或交叉的情况（
				 * 例如：重复：如果系统中已经存在一个1-2件区，第二条记录又写了1-2件区，即为包含包含：
				 * 如果系统中已经存在一个1-5件区，第二条记录写了一个2-3件区，即为包含；
				 * 交叉：如果系统中已经存在一个1-5件区，第二条记录写了一个3-6件区，即为交叉）；
				 */
				var itemAreaStore = itemAreaWindow.itemAreaGrid.getStore();
				var itemAreafalge = true;// 判断是否重复标记，默认不重复
				var msg = null;// 错误提示信息（重复）
				// if(itemAreaStore.data.length>0){
				if ((itemAreaFormValue.scopeEnd)*1 < (itemAreaFormValue.scopeStart)*1) {
					baseinfo.showInfoMsg("范围终点不能小于范围起点！");
					return;
				}
				//判断是否重复
				if (itemAreaStore.count() > 0) {
					itemAreaStore.each(function(record,index){
					 if(itemAreaFormValue.actionType=='update'&&index==itemAreaFormValue.itemAreaName){
					   itemAreafalge = true;
					 }else{
						var scopeStart = record.get('scopeStart');
						var scopeEnd = record.get('scopeEnd');
						
						if ((itemAreaFormValue.scopeStart*1 < scopeStart*1 &&itemAreaFormValue.scopeEnd*1 < scopeStart*1)
								|| (itemAreaFormValue.scopeStart*1 > scopeEnd*1 && itemAreaFormValue.scopeEnd*1 > scopeEnd*1)) {
							itemAreafalge = true;
						} else {
							itemAreafalge = false;
							msg = "已经存在" + scopeStart + "-" + scopeEnd
									+ "件区,不能重复添加"
									+ itemAreaFormValue.scopeStart + "-"
									+ itemAreaFormValue.scopeEnd + "件区";
									return false;
						}
					
					 }
					});
				}
				if (itemAreafalge) {
                    if(itemAreaFormValue.actionType=='update'){
						itemAreaStore.removeAt(itemAreaFormValue.itemAreaName);
					}					itemAreaStore
							.add(new Foss.baseinfo.sendDistrictMap.itemAreaModel(itemAreaFormValue));
					itemAreaWindow.close();
				} else {
					baseinfo.showInfoMsg(msg);
				}
			
		}
	},
	// 构造函数
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.cancel'),// 取消
					handler : function() {
						me.up().close();
					}
				}, {
					text : baseinfo.sendDistrictMap.i18n('foss.baseinfo.reset'),// 重置
					handler : function() {
						var addUpdateItemAreaWindow=me.up('window');
						itemAreaModel=addUpdateItemAreaWindow.itemAreaModel
						if (itemAreaModel.data.actionType=='update') {// 如果是修改，加载上一次修改的
							var form=addUpdateItemAreaWindow.addUpdateItemAreaFrom.getForm();
							form.findField('id').setValue(itemAreaModel.data.id);
							form.findField('scopeStart').setValue(itemAreaModel.data.scopeStart);// 范围（起点）					
							form.findField('scopeEnd').setValue(itemAreaModel.data.scopeEnd);// 范围（终点）				
							form.findField('length').setValue(itemAreaModel.data.length);// 件区长度（米）					
							form.findField('width').setValue(itemAreaModel.data.width);// 件区宽度（米）					
							form.findField('height').setValue(itemAreaModel.data.height);// 件区高度（米）					
							form.findField('abscissa').setValue(itemAreaModel.data.abscissa);// 横坐标					
							form.findField('ordinate').setValue(itemAreaModel.data.ordinate);// 纵坐标					
							form.findField('volume').setValue(itemAreaModel.data.volume);// 件区体积					
							form.findField('itemAreaName').setValue(itemAreaModel.data.itemAreaName);// 件区名称
							form.findField('actionType').setValue(itemAreaModel.data.actionType);//对件区表格的操作类型 add新增 update修改 delete删除
									} else {// 如果是新增，直接reset
							me.getForm().reset();// 表格重置
						}
					}
				}, {
					text : baseinfo.sendDistrictMap.i18n('foss.baseinfo.save'),// 保存
					cls : 'yellow_button',
					handler : function() {
						me.commitItemAreaInfo();
					}
				}];
		me.callParent([cfg]);
	}

});
/**
 * 派送货区行政区域映射 新增修改 件区表格 2015-2-6 189284
 */
Ext.define('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	height : 230,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.itemArea'),// 件区
	collapsible : true,
	animCollapse : true,
	store : null,
	autoScroll : true,
	addUpdateItemAreaWindow : null,// 件区From（新增修改）
	// 定义表格列信息
	columns : [{
		xtype : 'actioncolumn',
		width : 80,
		text : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.operate'),// 操作
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.edit'),// =编辑
			// 编辑事件
			handler : function(itemAreaGrid, rowIndex, colIndex) {
				var record = itemAreaGrid.getStore().getAt(rowIndex);
				var  itemAreaModel= new Foss.baseinfo.sendDistrictMap.itemAreaModel(record.data);
				 itemAreaModel.data.actionType='update';
				 itemAreaModel.data.itemAreaName=rowIndex;
				itemAreaGrid= this.up('grid');
				itemAreaGrid.addUpdateItemAreaWindow = itemAreaGrid.getAddUpdateItemAreaWindow(itemAreaGrid,itemAreaModel);
				itemAreaGrid.addUpdateItemAreaWindow.show();
			var itemAreaFrom=itemAreaGrid.addUpdateItemAreaWindow.getAddUpdateItemAreaFrom().getForm();
			    itemAreaFrom.findField('id').setValue(itemAreaModel.data.id);
				itemAreaFrom.findField('scopeStart').setValue(itemAreaModel.data.scopeStart);// 范围（起点）					
				itemAreaFrom.findField('scopeEnd').setValue(itemAreaModel.data.scopeEnd);// 范围（终点）				
				itemAreaFrom.findField('length').setValue(itemAreaModel.data.length);// 件区长度（米）					
				itemAreaFrom.findField('width').setValue(itemAreaModel.data.width);// 件区宽度（米）					
				itemAreaFrom.findField('height').setValue(itemAreaModel.data.height);// 件区高度（米）					
				itemAreaFrom.findField('abscissa').setValue(itemAreaModel.data.abscissa);// 横坐标					
				itemAreaFrom.findField('ordinate').setValue(itemAreaModel.data.ordinate);// 纵坐标					
				itemAreaFrom.findField('volume').setValue(itemAreaModel.data.volume);// 件区体积					
				itemAreaFrom.findField('itemAreaName').setValue(itemAreaModel.data.itemAreaName);// 件区名称
				itemAreaFrom.findField('actionType').setValue(itemAreaModel.data.actionType);//对件区表格的操作类型 add新增 update修改 delete删除
			}
		}, {
			iconCls : 'deppon_icons_delete',
			tooltip : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.add'),// 作废
			handler : function(itemAreaGrid, rowIndex, colIndex) {
				var record = itemAreaGrid.getStore().getAt(rowIndex);
				itemAreaGrid.getStore().remove(record);
				record.actionType='delete';
				if(!Ext.isEmpty(record.data.id)){
				   baseinfo.sendDistrictMap.delteItemAreas.push(record.data.id);
			     }
				}
				
		}]
	}, {
		// 字段标题
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeStart'),// 范围（起点）',
		// 关联model中的字段名
		dataIndex : 'scopeStart',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeEnd'),// 范围（终点）',
		dataIndex : 'scopeEnd',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.length'),// 件区长度（米）',
		dataIndex : 'length',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.width'),// 件区宽度（米）',
		dataIndex : 'width',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.height'),// 件区高度（米）',
		dataIndex : 'height',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.abscissa'),// 横坐标',
		dataIndex : 'abscissa',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.ordinate'),// 纵坐标',
		dataIndex : 'ordinate',
		flex : 0.1
	}],
	// 构造函数
	getAddUpdateItemAreaWindow : function(itemAreaGrid,itemAreaModel) {
		if (itemAreaGrid.addUpdateItemAreaWindow == null) {
			itemAreaGrid.addUpdateItemAreaWindow = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaWindow');
		}
		itemAreaGrid.addUpdateItemAreaWindow.itemAreaGrid = itemAreaGrid;
		//对件区表格的操作类型 add新增 update修改 delete删除
		itemAreaGrid.addUpdateItemAreaWindow.itemAreaModel=itemAreaModel;
		return itemAreaGrid.addUpdateItemAreaWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		//me.store = Ext
				//.create('Foss.baseinfo.sendDistrictMap.SendDistrictMapStore');
		me.tbar = [{
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.add'),// 新增
			// disabled:true,
			handler : function(butn) {
				var itemAreaGrid = butn.up('grid');
				var  itemAreaModel= new Foss.baseinfo.sendDistrictMap.itemAreaModel();
				 itemAreaModel.data.actionType='add';
				itemAreaGrid.addUpdateItemAreaWindow = itemAreaGrid.getAddUpdateItemAreaWindow(itemAreaGrid,itemAreaModel);
				itemAreaGrid.addUpdateItemAreaWindow.show();
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 派送货区行政区域映射 新增Window
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapAddWindow', {
	extend : 'Ext.window.Window',
	//'新增 派送货区行政区域映射',
	title : baseinfo.sendDistrictMap.i18n('foss.baseinfo.sendDistrictMap.SendDistrictMapAddWindow.title'),
	closable : true,
	modal : true,
	resizable : false,
	parent : null,
	// 关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 800,
	height : 700,
	isUpdate : false,
	listeners : {
		beforeshow : function(me) {
			var params = {
				'sendDistrictMapVo' : {
					'sendDistrictMapEntity' : {
						'transferCenterCode' : FossUserContext
								.getCurrentDeptCode()
					}
				}
			};
			var successFun = function(json) {
				var transferOrAreaForm = me.getTransferOrAreaForm().getForm();
				// 顶级外场名称
				transferOrAreaForm
						.findField('transferCenterName')
						.setValue(json.sendDistrictMapVo.sendDistrictMapEntity.transferCenterName);
				transferOrAreaForm.findField('transferCenterName')
						.setReadOnly(true);
				// 顶级外场Code
				transferOrAreaForm
						.findField('transferCenterCode')
						.setValue(json.sendDistrictMapVo.sendDistrictMapEntity.transferCenterCode);
				// 库区名称
				transferOrAreaForm
						.findField('goodsAreaName')
						.setValue(json.sendDistrictMapVo.sendDistrictMapEntity.goodsAreaName);
				transferOrAreaForm.findField('goodsAreaName').setReadOnly(true);
				// 库区code
				transferOrAreaForm
						.findField('goodsAreaCode')
						.setValue(json.sendDistrictMapVo.sendDistrictMapEntity.goodsAreaCode);
			};
			var failureFun = function(json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				} else {
					baseinfo.showErrorMes(json.message);
				}
			};
			var url = baseinfo.realPath('qureyTransferCenter.action');
			baseinfo.requestJsonAjax(url, params, successFun, failureFun);
		},
		beforehide : function(me) {
				me.getAddValueForm().getForm().reset();
			var form=me.getAddValueForm();
				form.getsendDistrictMapArea().getStore().removeAll();
				me.getAddUpdateItemAreaGrid().getStore().removeAll();			
			var container=form.query('container');
				form.query('label')[0].setVisible(true);
				container[1].setVisible(true);
				container[2].setVisible(true);
				form.setHeight(280);
				form.getForm().findField('goodsType').setValue('send');
				form.getForm().findField('zoneName').setValue(null);
				form.getForm().findField('zoneName').setReadOnly(false);
				me.getAddUpdateItemAreaGrid().setHeight(230);
			  baseinfo.sendDistrictMap.delteItemAreas=new Array();
		}
	},
	addValueForm : null,
	getAddValueForm : function() {
		if (this.addValueForm == null) {
			this.addValueForm = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateValueForm');
		}
		return this.addValueForm;
	},
	addUpdateItemAreaGrid : null,
	/**
	 * 2015-2-6 189284 新增件区表格
	 */
	getAddUpdateItemAreaGrid : function() {
		if (this.addUpdateItemAreaGrid == null) {
			this.addUpdateItemAreaGrid = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaGrid');
		}
		return this.addUpdateItemAreaGrid;
	},
	getTransferOrAreaForm : function() {
		if (this.TransferOrAreaForm == null) {
			this.TransferOrAreaForm = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateTransferOrAreaForm');
		}
		return this.TransferOrAreaForm;
	},
	// 保存新增信息
	saveAddValue : function() {
		var me = this;
		var transferOrAreaForm = me.getTransferOrAreaForm().getForm();
		// 顶级外场code
		var transferCenterCode = transferOrAreaForm
				.findField('transferCenterCode').getValue();
		// 对应库区code 驻地派送货区
		var goodsAreaCode = transferOrAreaForm.findField('goodsAreaCode')
				.getValue();
		var valueForm = me.getAddValueForm().getForm();
		var zoneName = valueForm.findField('zoneName').getValue();
		var zoneCode = valueForm.findField('zoneCode').getValue();
		var goodsType=valueForm.findField('goodsType').getValue();
		var params = {
			'sendDistrictMapVo' : {
				'sendDistrictMapEntity' : {
					'transferCenterCode' : transferCenterCode,
					'goodsAreaCode' : goodsAreaCode,
					'zoneCode' : zoneCode,
					'zoneName' : zoneName,
					'goodsType':goodsType
				}
			}
		};
		// 件区grid
		var itemAreaGrid = me.getAddUpdateItemAreaGrid().getStore();
		// 件区List
		var itemAreaGridList = new Array();
		
		if (itemAreaGrid.getNewRecords().length == 0) {
			baseinfo.showErrorMes("请添加对应的件区信息！");
			return;
		}
		if(goodsType==='send'){
		    var addStore = me.getAddValueForm().getsendDistrictMapArea().getStore();
			var addRecordModelList = addStore.getNewRecords();
			var addsendDistrictMapList = new Array();
			// 若没有添加行政区域，不让提交
			if (addRecordModelList.length == 0) {
				baseinfo.showErrorMes("请添加对应的行政区域！");
				return;
			}
			for (var i = 0; i < addRecordModelList.length; i++) {
				addRecordModelList[i].data.sendDistrictItemAreaEntitys=null;
				addsendDistrictMapList.push(addRecordModelList[i].data);
			}
			params.sendDistrictMapVo.sendDistrictMapEntities = addsendDistrictMapList;
		}
		
		var itemAreaGridStore = itemAreaGrid.getNewRecords();
		
		for (var i = 0; i < itemAreaGridStore.length; i++) {
			itemAreaGridList.push(itemAreaGridStore[i].data);
		}
		
		params.sendDistrictMapVo.sendDistrictItemAreaEntitys = itemAreaGridList;
		params.sendDistrictMapVo.sendDistrictMapEntity.sendDistrictItemAreaEntitys = itemAreaGridList;
		var successFun = function(json) {
			// 隐藏页面
			me.hide();
			// 新增成功
			Ext.ux.Toast.msg('FOSS提醒您', '保存成功!');
			// 重新加载
			Ext.getCmp('T_baseinfo-sendDistrictMap_content')
					.getSendDistrictMapGrid().getPagingToolbar().moveFirst();

		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.sendDistrictMap
						.i18n('foss.baseinfo.requestTimeout'));// 请求超时
			} else {
				baseinfo.showErrorMes(json.message);
			}
		};
		var url = baseinfo.realPath('addsendDistrictMap.action');
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTransferOrAreaForm(), me.getAddValueForm(),
				me.getAddUpdateItemAreaGrid()];
		me.fbar = [{
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.cancel'),// 取消
					handler : function() {
						me.close();
					}
				}, {
					text : baseinfo.sendDistrictMap.i18n('foss.baseinfo.reset'),// 重置
					handler : function() {
						me.getAddValueForm().getForm().reset();
						me.getAddValueForm().getsendDistrictMapArea()
								.getStore().removeAll();
						me.getAddUpdateItemAreaGrid().getStore().removeAll();
					}
				}, {
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.confirm'),// 确定
					cls : 'yellow_button',
					margin : '0 0 0 235',
					handler : function() {
						me.saveAddValue();
					}
				}];
		me.callParent([cfg]);
	}
});

/**
 * 派送货区行政区域映射 修改Window
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapUpdateWindow', {
	extend : 'Ext.window.Window',
	//'修改 派送货区行政区域映射',
	title : baseinfo.sendDistrictMap.i18n('foss.baseinfo.sendDistrictMap.SendDistrictMapUpdateWindow.title'),//
	closable : true,
	modal : true,
	resizable : false,
	parent : null,
	// 关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 800,
	height : 700,
	sendDistrictMapModel : null,
	isUpdate : true,
	listeners : {
		beforeshow : function(me) {
			var model = me.sendDistrictMapModel;
			me.getTransferOrAreaForm().getForm()
					.findField('transferCenterName').setValue(model
							.get('transferCenterName'));
			me.getTransferOrAreaForm().getForm()
					.findField('transferCenterName').setReadOnly(true);
			me.getTransferOrAreaForm().getForm()
					.findField('transferCenterCode').setValue(model
							.get('transferCenterCode'));

			me.getTransferOrAreaForm().getForm().findField('goodsAreaName')
					.setValue(model.get('goodsAreaName'));
			me.getTransferOrAreaForm().getForm().findField('goodsAreaName')
					.setReadOnly(true);
			me.getTransferOrAreaForm().getForm().findField('goodsAreaCode')
					.setValue(model.get('goodsAreaCode'));

			me.getUpdateValueForm().getForm().findField('zoneName')
					.setValue(model.get('zoneName'));
			me.getUpdateValueForm().getForm().findField('zoneName')
					.setReadOnly(true);
			me.getUpdateValueForm().getForm().findField('zoneCode')
					.setValue(model.get('zoneCode'));
			/**
			 * 默认为派送货
			 */
			if(Ext.isEmpty(model.get('goodsType'))){
				model.data.goodsType='send';
			}
			me.getUpdateValueForm().getForm().findField('goodsType')
					.setValue(model.get('goodsType'));
			me.getUpdateValueForm().getForm().findField('goodsType').setReadOnly(true);
			
					/**
					 * 货物类型为拍送货
					 */
			if(model.get('goodsType')=='send'){
				 var districtNameStr = model.data.districtName;
				var districtNameArr = districtNameStr.split(",");
				var districtCodeStr = model.data.districtCode;
				var districtCodeArr = districtCodeStr.split(",");
				var idStr = model.data.id;
				var idArr = idStr.split(",");
				baseinfo.sendDistrictMap.oldIds = idArr;// 备份未修改时的数据ID
				for (var x = 0; x < districtNameArr.length; x++) {
					var sendDistrictMapModelTmp = new Foss.baseinfo.sendDistrictMap.SendDistrictMapModel();
					sendDistrictMapModelTmp.set('districtName', districtNameArr[x]);
					sendDistrictMapModelTmp.set('districtCode', districtCodeArr[x]);
					sendDistrictMapModelTmp.set('id', idArr[x]);
					me.getUpdateValueForm().getsendDistrictMapArea().getStore()
							.add(sendDistrictMapModelTmp);
				}
					 me.getUpdateValueForm().getsendDistrictMapArea().getDockedItems()[0].items.items[0]
							.setDisabled(false);
					 var sendDistrictItemAreaEntitys=model.get('sendDistrictItemAreaEntitys');
					Ext.Array.forEach(sendDistrictItemAreaEntitys,function(items){
						  var d=new Foss.baseinfo.sendDistrictMap.itemAreaModel(items);
						  me.getAddUpdateItemAreaGrid().getStore().add(d)
					});
		   }else{
				  var form =me.getUpdateValueForm();
			     var container=form.query('container');
				  form.query('label')[0].setVisible(false);
				  container[0].setVisible(false);
				  container[1].setVisible(false);
				  container[2].setVisible(false);
				  form.setHeight(120);
				  me.getAddUpdateItemAreaGrid().setHeight(380);
				  var sendDistrictItemAreaEntitys=model.get('sendDistrictItemAreaEntitys');
			      Ext.Array.forEach(sendDistrictItemAreaEntitys,function(items){
					  var d=new Foss.baseinfo.sendDistrictMap.itemAreaModel(items);
					  me.getAddUpdateItemAreaGrid().getStore().add(d)
			      });
		  }

			
		},
		beforehide : function(me) {
			me.getUpdateValueForm().getForm().reset();
			me.getUpdateValueForm().getsendDistrictMapArea().getStore().removeAll();
			me.getAddUpdateItemAreaGrid().getStore().removeAll();
			var form =me.getUpdateValueForm();
			  var container=form.query('container');
							form.query('label')[0].setVisible(true);
							container[1].setVisible(true);
							container[2].setVisible(true);
							form.setHeight(280);
							form.getForm().findField('goodsType').setValue('send');
							form.getForm().findField('zoneName').setValue(null);
							form.getForm().findField('zoneName').setReadOnly(false);
							me.getAddUpdateItemAreaGrid().setHeight(230);
					
		}
	},
	/**
	 * 2015-2-6 189284 新增件区表格
	 */
	addUpdateItemAreaGrid:null,
	getAddUpdateItemAreaGrid : function() {
		if (this.addUpdateItemAreaGrid == null) {
			this.addUpdateItemAreaGrid = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateItemAreaGrid');
		}
		return this.addUpdateItemAreaGrid;
	},
	updateValueForm : null,
	getUpdateValueForm : function() {
		if (this.updateValueForm == null) {
			this.updateValueForm = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateValueForm');
		}
		return this.updateValueForm;
	},
	transferOrAreaUpdateForm : null,
	getTransferOrAreaForm : function() {
		if (this.transferOrAreaUpdateForm == null) {
			this.transferOrAreaUpdateForm = Ext
					.create('Foss.baseinfo.sendDistrictMap.AddUpdateTransferOrAreaForm');
		}
		return this.transferOrAreaUpdateForm;
	},
	// 保存新增信息
	saveUpdateValue : function() {
		var me = this;
		var transferOrAreaForm = me.getTransferOrAreaForm().getForm();
		// 顶级外场code
		var transferCenterCode = transferOrAreaForm
				.findField('transferCenterCode').getValue();
		var transferCenterName = transferOrAreaForm
				.findField('transferCenterName').getValue();
		// 对应库区code 驻地派送货区
		var goodsAreaCode = transferOrAreaForm.findField('goodsAreaCode')
				.getValue();
		var goodsAreaName = transferOrAreaForm.findField('goodsAreaName')
				.getValue();

		var valueForm = me.getUpdateValueForm().getForm();
		var zoneName = valueForm.findField('zoneName').getValue();
		var zoneCode = valueForm.findField('zoneCode').getValue();
		var goodsType = valueForm.findField('goodsType').getValue();
		var sendDistrictMapArea=me.getUpdateValueForm().getsendDistrictMapArea();
		var updateStore =sendDistrictMapArea.getStore();
		var params = {
			'sendDistrictMapVo' : {
				'sendDistrictMapEntity' : {
					'transferCenterCode' : transferCenterCode,
					'transferCenterName' : transferCenterName,
					'goodsAreaCode' : goodsAreaCode,
					'goodsAreaName' : goodsAreaName,
					'zoneCode' : zoneCode,
					'zoneName' : zoneName,
					'goodsType':goodsType
				}
			}
		};
		// 件区grid
		var itemAreaGrid = me.getAddUpdateItemAreaGrid().getStore();
		// 件区List
		var itemAreaGridList = new Array();
		
		if (itemAreaGrid.getCount() == 0) {
			baseinfo.showErrorMes("请添加对应的件区信息！");
			return;
		}
	
		/**
		 * 默认为派送货
		 */
		if(Ext.isEmpty(goodsType)){
			goodsType='send';
		}
			/**
		 * goodsType=send表示为派送货需要添加行政区域
		 *  =personally 为自提区 不需要添加行政区域
		 */
		if(goodsType==='send'){
			var updateRecordModelList = updateStore.getNewRecords();
			var updateSendDistrictMapList = new Array();
			// 若没有添加行政区域，不让提交
			if (updateRecordModelList.length == 0) {
				baseinfo.showErrorMes("没有添加对应的行政区域！");
				return;
			}
			for (var i = 0; i < updateRecordModelList.length; i++) {
				updateRecordModelList[i].data.sendDistrictItemAreaEntitys=null;
				updateSendDistrictMapList.push(updateRecordModelList[i].data);
			}
            params.sendDistrictMapVo.sendDistrictMapEntities = updateSendDistrictMapList;
		    params.sendDistrictMapVo.deleteids = baseinfo.sendDistrictMap.deleteIds;
		}		
		
		
		//var itemAreaGridStore = itemAreaGrid.getNewRecords();		
		for (var i = 0; i < itemAreaGrid.getCount(); i++) {
			itemAreaGridList.push(itemAreaGrid.getRange()[i].data);
		}
		params.sendDistrictMapVo.sendDistrictItemAreaEntitys = itemAreaGridList;
		params.sendDistrictMapVo.sendDistrictMapEntity.sendDistrictItemAreaEntitys = itemAreaGridList;
		params.sendDistrictMapVo.delteItemAreas =baseinfo.sendDistrictMap.delteItemAreas;
		Ext.Ajax.request({
					jsonData : params,
					url : baseinfo.realPath('updateSendDistrictMap.action'),
					success : function(response) {
						var json = Ext.decode(response.responseText);
						// 隐藏页面
						me.hide();
						// 更新成功
						baseinfo.showInfoMes(json.message);
						// 重新加载
						Ext.getCmp('T_baseinfo-sendDistrictMap_content')
								.getSendDistrictMapGrid().getPagingToolbar()
								.moveFirst();
						// 隐藏页面
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						baseinfo.showErrorMes(json.message);
					}
				});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTransferOrAreaForm(), me.getUpdateValueForm(),me.getAddUpdateItemAreaGrid()];
		me.fbar = [{
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.cancel'),// 取消
					handler : function() {
						baseinfo.sendDistrictMap.deleteIds = new Array();
						baseinfo.sendDistrictMap.deleteDistictCodeIds = new Array();
						baseinfo.sendDistrictMap.delteItemAreas=new Array();
						me.close();
					}
				}, {
					text : baseinfo.sendDistrictMap.i18n('foss.baseinfo.reset'),// 重置
					handler : function() {
						baseinfo.sendDistrictMap.deleteIds = new Array();
						baseinfo.sendDistrictMap.deleteDistictCodeIds = new Array();
						baseinfo.sendDistrictMap.delteItemAreas=new Array();
						var model = me.sendDistrictMapModel;
						me.getUpdateValueForm().getsendDistrictMapArea().getStore().removeAll();
					    me.getAddUpdateItemAreaGrid().getStore().removeAll();
						/**
						 * 默认为派送货
						 */
						if(Ext.isEmpty(model.get('goodsType'))){
							model.data.goodsType='send';
						}					
						
								/**
								 * 货物类型为拍送货
								 */
						if(model.get('goodsType')=='send'){
							 var districtNameStr = model.data.districtName;
							var districtNameArr = districtNameStr.split(",");
							var districtCodeStr = model.data.districtCode;
							var districtCodeArr = districtCodeStr.split(",");
							var idStr = model.data.id;
							var idArr = idStr.split(",");
							baseinfo.sendDistrictMap.oldIds = idArr;// 备份未修改时的数据ID
							for (var x = 0; x < districtNameArr.length; x++) {
								var sendDistrictMapModelTmp = new Foss.baseinfo.sendDistrictMap.SendDistrictMapModel();
								sendDistrictMapModelTmp.set('districtName', districtNameArr[x]);
								sendDistrictMapModelTmp.set('districtCode', districtCodeArr[x]);
								sendDistrictMapModelTmp.set('id', idArr[x]);
								me.getUpdateValueForm().getsendDistrictMapArea().getStore()
										.add(sendDistrictMapModelTmp);
							}
								 me.getUpdateValueForm().getsendDistrictMapArea().getDockedItems()[0].items.items[0]
										.setDisabled(false);
								 var sendDistrictItemAreaEntitys=model.get('sendDistrictItemAreaEntitys');
								Ext.Array.forEach(sendDistrictItemAreaEntitys,function(items){
									  var d=new Foss.baseinfo.sendDistrictMap.itemAreaModel(items);
									  me.getAddUpdateItemAreaGrid().getStore().add(d)
								});
					   }else{
							  var form =me.getUpdateValueForm();
						     var container=form.query('container');
							  form.query('label')[0].setVisible(false);
							  container[0].setVisible(false);
							  container[1].setVisible(false);
							  container[2].setVisible(false);
							  form.setHeight(120);
							  me.getAddUpdateItemAreaGrid().setHeight(380);
							  var sendDistrictItemAreaEntitys=model.get('sendDistrictItemAreaEntitys');
						      Ext.Array.forEach(sendDistrictItemAreaEntitys,function(items){
								  var d=new Foss.baseinfo.sendDistrictMap.itemAreaModel(items);
								  me.getAddUpdateItemAreaGrid().getStore().add(d)
						      });
					  }						
					}
				}, {
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.confirm'),// 确定
					cls : 'yellow_button',
					margin : '0 0 0 235',
					handler : function() {
						me.saveUpdateValue();
					}
				}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.baseinfo.sendDistrictMap.ItemAreaGridDEtailInfo',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	height : 230,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.itemArea'),// 件区
	collapsible : true,
	animCollapse : true,
	store : null,
	autoScroll : true,
	addUpdateItemAreaWindow : null,// 件区From（新增修改）
	// 定义表格列信息
	columns : [ {
		// 字段标题
		header :baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.itemAreaName'),//'名称',
		// 关联model中的字段名
		dataIndex : 'itemAreaName',
		flex : 0.1
	}, {
		// 字段标题
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeStart'),// 范围（起点）',
		// 关联model中的字段名
		dataIndex : 'scopeStart',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.scopeEnd'),// 范围（终点）',
		dataIndex : 'scopeEnd',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.length'),// 件区长度（米）',
		dataIndex : 'length',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.width'),// 件区宽度（米）',
		dataIndex : 'width',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.height'),// 件区高度（米）',
		dataIndex : 'height',
		flex : 0.1
	},{
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.volume'),// 件区容积（方）',
		dataIndex : 'volume',
		flex : 0.1
	} ,{
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.abscissa'),// 横坐标',
		dataIndex : 'abscissa',
		flex : 0.1
	}, {
		header : baseinfo.sendDistrictMap
				.i18n('foss.baseinfo.sendDistrictMap.ordinate'),// 纵坐标',
		dataIndex : 'ordinate',
		flex : 0.1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.baseinfo.sendDistrictMap.ItemAreaStore');
		me.callParent([cfg]);
	}

});
/**
 * 定义详细信息panel
 */
Ext.define('Foss.baseinfo.sendDistrictMap.sendDistrictMapInfoPanel',{
   extend:'Ext.panel.Panel',
   title:baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.sendDistrictMapInfoDetail'),//件区详情
   frame:true,
   /**
	 * 2015-2-6 189284 新增件区表格
	 */
	detailInfoItemAreaGrid:null,
	getDetailInfoItemAreaGrid : function() {
		if (this.detailInfoItemAreaGrid == null) {
			this.detailInfoItemAreaGrid = Ext
					.create('Foss.baseinfo.sendDistrictMap.ItemAreaGridDEtailInfo');
		}
		return this.detailInfoItemAreaGrid;
	},
   constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		this.items = [this.getDetailInfoItemAreaGrid()];
		me.callParent([cfg]);
	},
	bindData:function(record){
	  var me=this;
	  var sendDistrictItemAreaEntitys=record.get('sendDistrictItemAreaEntitys');
	  me.getDetailInfoItemAreaGrid().getStore().removeAll()
		  Ext.Array.forEach(sendDistrictItemAreaEntitys,function(items){
			 var d=new Foss.baseinfo.sendDistrictMap.itemAreaModel(items);
				 me.getDetailInfoItemAreaGrid().getStore().add(d)
		 });
	  //this.addUpdateItemAreaGrid.getColumnModel().setHidden(1,true); 
	  
	}
});
/**
 * 派送货区行政区域映射 grid 列表
 */
Ext.define('Foss.baseinfo.sendDistrictMap.SendDistrictMapGrid', {
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	title : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.GridTitle'),// '派送货区行政区域映射列表
	// '
	frame : true,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.sendDistrictMap
			.i18n('foss.baseinfo.sendDistrictMap.queryResultIsNull'),// 查询结果为空
	// 得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
		// 表格行可展开的插件
	plugins : [{
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : false,
				// 行体内容
				rowBodyElement : 'Foss.baseinfo.sendDistrictMap.sendDistrictMapInfoPanel'
			}],
	// 派送货区行政区域映射新增WINDOW
	sendDistrictMapAddWindow : null,
	getSendDistrictMapAddWindow : function() {
		if (this.sendDistrictMapAddWindow == null) {
			this.sendDistrictMapAddWindow = Ext
					.create('Foss.baseinfo.sendDistrictMap.SendDistrictMapAddWindow');
			this.sendDistrictMapAddWindow.parent = this;// 父元素
		}
		return this.sendDistrictMapAddWindow;
	},
	// 修改派送货区行政区域映射WINDOW
	sendDistrictMapUpdateWindow : null,
	getSendDistrictMapUpdateWindow : function() {
		if (this.sendDistrictMapUpdateWindow == null) {
			this.sendDistrictMapUpdateWindow = Ext
					.create('Foss.baseinfo.sendDistrictMap.SendDistrictMapUpdateWindow');
			this.sendDistrictMapUpdateWindow.parent = this;// 父元素
		}
		baseinfo.sendDistrictMap.deleteIds = new Array();
		baseinfo.sendDistrictMap.deleteDistictCodeIds = new Array();
		baseinfo.sendDistrictMap.delteItemAreas=new Array();
		baseinfo.sendDistrictMap.oldIds = new Array();
		return this.sendDistrictMapUpdateWindow;
	},
	// 作废派送货区行政区域映射
	toVoidsendDistrictMap : function(btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection();// 获取选中的数据
		if (selections.length < 1) {// 判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');// 请选择一条进行作废操作！
			return;// 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.sendDistrictMap
						.i18n('foss.baseinfo.sendDistrictMap.deleteZoneName'),
				function(e) {// 作废数据后不可恢复，确定要作废么？
					if (e == 'yes') {// 询问是否删除，是则发送请求
						baseinfo.sendDistrictMap.deleteIds = new Array();// 库位ID数组
						for (var x = 0; x < selections.length; x++) {
							if (selections[x].data.id == null
									|| selections[x].data.id != "") {
								var ids = selections[x].data.id.split(',');
								if (ids.length > 0) {
									for (var i = 0; i < ids.length; i++) {
										baseinfo.sendDistrictMap.deleteIds
												.push(ids[i]);
									}
								}
							}
						}

						// for(var i = 0 ; i<selections.length ; i++){
						// sendDistrictMapList.push(selections[i].data);,,
						// }
						var params = {
							'sendDistrictMapVo' : {
								'deleteids' : baseinfo.sendDistrictMap.deleteIds
							}
						};
						var successFun = function(json) {
							baseinfo.showInfoMes('作废成功！');
							me.getPagingToolbar().moveFirst();
						};
						var failureFun = function(json) {
							if (Ext.isEmpty(json)) {
								baseinfo.showErrorMes(baseinfo.sendDistrictMap
										.i18n('foss.baseinfo.requestTimeout'));// 请求超时
							} else {
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo
								.realPath('deleteSendDistrictMapList.action');
						baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);
					}
				})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					xtype : 'rownumberer',
					width : 40,
					text : '序号'// 序号
				}, {
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.sendDistrictMap.operate'),// 操作
					// dataIndex : 'id',
					xtype : 'actioncolumn',
					align : 'center',
					width : 80,
					items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.sendDistrictMap
								.i18n('foss.baseinfo.sendDistrictMap.update'),// 修改
						disabled : !baseinfo.sendDistrictMap
								.isPermission('sendDistrictMap/sendDistrictMapUpdateButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// //获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var updateWindow = me.getSendDistrictMapUpdateWindow();// 获得修改窗口
							updateWindow.sendDistrictMapModel = new Foss.baseinfo.sendDistrictMap.SendDistrictMapModel(record.data);
							updateWindow.show();// 显示修改窗口
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.sendDistrictMap
								.i18n('foss.baseinfo.sendDistrictMap.void'),// 作废
						disabled : !baseinfo.sendDistrictMap
								.isPermission('sendDistrictMap/sendDistrictMapDeleteButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							var record = grid.getStore().getAt(rowIndex);
							baseinfo
									.showQuestionMes(
											baseinfo.sendDistrictMap
													.i18n('foss.baseinfo.sendDistrictMap.deleteZoneName'),
											function(e) {// 作废数据后不可恢复，确定要作废么？
												if (e == 'yes') {// 询问是否删除，是则发送请求
													var params = {
														'sendDistrictMapVo' : {
															'sendDistrictMapEntity' : {
																'zoneCode' : record.data.zoneCode
															}
														}
													};
													var successFun = function(
															json) {
														baseinfo
																.showInfoMes("删除成功");
														me.getPagingToolbar()
																.moveFirst();
													};
													var failureFun = function(
															json) {
														if (Ext.isEmpty(json)) {
															baseinfo
																	.showErrorMes(baseinfo.sendDistrictMap
																			.i18n('foss.baseinfo.requestTimeout'));// 请求超时
														} else {
															baseinfo
																	.showErrorMes(json.message);
														}
													};
													var url = baseinfo
															.realPath('deleteByZoneName.action');
													baseinfo.requestJsonAjax(
															url, params,
															successFun,
															failureFun);
												}
											})
						}
					}]
				}, {
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.sendDistrictMap.transferCenterCode'),// 外场编号
					hidden : true,
					dataIndex : 'transferCenterCode'
				}, {
					text : baseinfo.sendDistrictMap
							.i18n('foss.baseinfo.sendDistrictMap.transferCenterName'),// 外场名称
					xtype : 'ellipsiscolumn',
					dataIndex : 'transferCenterName'
				}, {
					text : '库区编码',
					hidden : true,
					dataIndex : 'goodsAreaCode'
				}, {
					text : '库区名称',
					xtype : 'ellipsiscolumn',
					dataIndex : 'goodsAreaName'
				}, {
					text : '行政区域编码 ',
					hidden : true,
					dataIndex : 'districtCode'
				}, {
					text : '分区名称',
					xtype : 'ellipsiscolumn',
					width : 200,
					dataIndex : 'zoneName'
				}, {
					text : '行政区域名称 ',
					xtype : 'ellipsiscolumn',
					flex : 1,
					dataIndex : 'districtName'
				}, {
					text : '分区编码',
					hidden : true,
					dataIndex : 'zoneCode'
				}, {
					text : 'ID',
					hidden : true,
					dataIndex : 'id'
				}, {
					text : '创建时间',
					hidden : true,
					dataIndex : 'createTime'
				}, {
					text : '修改时间',
					hidden : true,
					dataIndex : 'modifyTime'
				}, {
					text : '是否启用',
					hidden : true,
					dataIndex : 'active'
				}, {
					text : '创建人工号',
					hidden : true,
					dataIndex : 'createUserCode'
				}, {
					text : '更新人工号',
					hidden : true,
					dataIndex : 'modifyUserCode'
				}];
		me.store = Ext.create(
				'Foss.baseinfo.sendDistrictMap.SendDistrictMapStore', {
					autoLoad : false,// 不自动加载
					pageSize : 20,
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = me.up()
									.getQuerysendDistrictMapForm();
							if (queryForm != null) {
								Ext.apply(operation, {
									params : {// 查询库位大查询，查询条件组织
										'sendDistrictMapVo.sendDistrictMapEntity.districtCode' : queryForm
												.getForm()
												.findField('districtCode')
												.getValue(),
										'sendDistrictMapVo.sendDistrictMapEntity.transferCenterCode' : queryForm
												.getForm()
												.findField('organizationName')
												.getValue()
									}
								});
							}
						}
					}
				});
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.add'),// 新增
			disabled : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapAddButton'),
			hidden : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapAddButton'),
			handler : function() {
				me.getSendDistrictMapAddWindow().show();
			}
		}, '-', {
			text : baseinfo.sendDistrictMap
					.i18n('foss.baseinfo.sendDistrictMap.void'),// 作废
			hidden : !baseinfo.sendDistrictMap
					.isPermission('sendDistrictMap/sendDistrictMapDeleteButton'),
			handler : function() {
				me.toVoidsendDistrictMap();
			}
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}

});
/**
 * @description 派送货区行政区域映射主页
 */
Ext.onReady(function() {
			Ext.QuickTips.init();
			if (Ext.getCmp('T_baseinfo-sendDistrictMap_content')) {
				return;
			};
			var querysendDistrictMapForm = Ext
					.create('Foss.baseinfo.sendDistrictMap.QuerysendDistrictMapForm'); // 查询FORM
			var sendDistrictMapGrid = Ext
					.create('Foss.baseinfo.sendDistrictMap.SendDistrictMapGrid'); // 查询结果GRID
			Ext.getCmp('T_baseinfo-sendDistrictMap').add(Ext.create(
					'Ext.panel.Panel', {
						id : 'T_baseinfo-sendDistrictMap_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						// 获得查询FORM
						getQuerysendDistrictMapForm : function() {
							return querysendDistrictMapForm;
						},
						// 获得查询结果GRID
						getSendDistrictMapGrid : function() {
							return sendDistrictMapGrid;
						},
						items : [querysendDistrictMapForm, sendDistrictMapGrid]
					}));
		});