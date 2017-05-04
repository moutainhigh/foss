Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-selector_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
					xtype : 'container',
					labelWidth : 60,
					width :1000,
					layout : 'column',
					items : [{
								xtype : 'dynamicorgcombselector',
								id : 'dynamicorgselector',
								fieldLabel : '组织单选(dynamicorgcombselector)',
								transferCenter : 'Y',
								labelWidth : 160 
							}, {
								xtype : 'dynamicorgcombselector',
								// id : 'vardynamicorgselector',
								labelWidth : 160,
								name : 'abc',
								valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
								forceSelection : true,
								// active:'N',
								fieldLabel : '自定义value是标杆编码部门(dynamicorgcombselector)'
							/*	listeners : {
									select : function(name, index, obj) {
										alert('sfsdf');
									}
								}*/
							}, {
								xtype : 'dynamicorgcombselector',
								displayField : 'simpleName',// 这个参数，只需与实体中的某个字段对应即可
								forceSelection : true,
								labelWidth : 160,
								fieldLabel : '自定义显示值是简称部门'
							}, {
								xtype : 'dynamicorgcombselector',
								myQueryParam : 'simpleName',// 这个参数，只需与实体中的某个字段对应即可
								forceSelection : true,
								labelWidth : 210,
								fieldLabel : '自定义查询参数是组织简称编码部门'
							}, {
								xtype : 'dynamicorgcombselector',
								forceSelection : true,
								types:'ORG',
								salesDepartment:'Y',//自己定义参数就OK  --定义查询营业部
								transferCenter:'Y',//--或者查询外场
								labelWidth : 210,
								fieldLabel : '查询营业部和外场部门'
							}]
				}, {
					fieldLabel : '国省市区(xtype:linkregincombselector,type:N-P-C-C)',
					labelWidth:320,
					type : 'N-P-C-C',
					xtype : 'linkregincombselector',
					id : 'npcclinkregincombselector'
				}, {
					fieldLabel : '省市区(xtype:linkregincombselector,type:P-C-C)',
					id:'test_ssq',
					labelWidth:300,
					provinceWidth : 160,
					cityWidth : 160,
					cityLabel : '市',
					cityName : 'cityName',//名称
					provinceLabel : '省',
					provinceName:'privateName',//省名称
					areaLabel : '县',
					areaName : 'areaName',// 县名称
					areaWidth : 160,
					type : 'P-C-C',
					xtype : 'linkregincombselector'
				}, {
					fieldLabel : '省市(xtype:linkregincombselector,type:P-C)',
					provinceWidth : 160,
					labelWidth:300,
					cityWidth : 160,
					cityLabel : '市',
					provinceLabel : '省',
					labelWid : 30,
					type : 'P-C',
					xtype : 'linkregincombselector'
				}, {
					fieldLabel : '市区(xtype:linkregincombselector,type:C-C)',
					type : 'C-C',
					labelWidth:300,
					cityWidth : 180,
					areaWidth : 180,
					xtype : 'linkregincombselector'
				}, {
					xtype : 'dynamiclinkorgdeptcombselector',
					labelWidth:300,
					fieldLabel : '大小区,营业部(xtype:dynamiclinkorgdeptcombselector,type:B-S-D)',
					type:'B-S-D'
				}, {
					xtype : 'container',
					labelWidth:300,
					labelWidth : 60,
					width : 900,
					layout : 'column',
					items : [{ 
								xtype : 'commonreginselector',
								fieldLabel : '行政区域(commonreginselector)',
								labelWidth:300,
								degree : 'CITY'
							}, { 
								xtype : 'commonprovinceselector',
								fieldLabel : '省(commonprovinceselector)',
								labelWidth:300
							}, { 
								forceSelection : true,
								xtype : 'commoncityselector',
								fieldLabel : '市(commoncityselector)',
								labelWidth:300
							}, {
								xtype : 'commonareaselector',
								fieldLabel : '县(commonareaselector)',
								labelWidth:300
							}, {
								xtype : 'commonemployeeselector',
								fieldLabel : '人员(commonemployeeselector)', 
								parentOrgCode : 'W01140602',
								labelWidth:300 
							}]
				}, {
					xtype : 'staticresourcetreeselector',
					fieldLabel : '权限(staticresourcetreeselector)',
								labelWidth:300
				}, {
					xtype : 'linkagecontainer',
					labelWidth : 60,
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonplatformselector',
								fieldLabel : '月台(commonplatformselector)',
								labelWidth:300
							}, {
								xtype : 'commongoodsareaselector',
								fieldLabel : '库区(commongoodsareaselector)',
								labelWidth:300,
								deptCode:'W040002060401'
							}, {
								xtype : 'commonleaseddriverselector',
								fieldLabel : '外请车司机(commonleaseddriverselector)',
								labelWidth:300 
							}, {
								xtype : 'commonleasedvehicleselector',
								fieldLabel : '外请车(commonleasedvehicleselector)',
								labelWidth:300 
							}]
				}, {
					fieldLabel : '2012-12-03',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 900,
					layout : 'column',
					items : [{
								xtype : 'commonbigzoneselector',
								fieldLabel : '大区(commonbigzoneselector)',
								//deptCode:'ssss',
								labelWidth:300
							}, {
								xtype : 'commonsmallzoneselector',
								fieldLabel : '小区(commonsmallzoneselector)',
								labelWidth:300
							}, {
								xtype : 'commonbigregionsselector',
								fieldLabel : '接货大区(commonbigregionsselector)',
								labelWidth:300,
								width:450
							},{			
								xtype : 'commonbigregionsdeselector',
								fieldLabel : '送货大区(commonbigregionsdeselector)',
								//management:'W0114050408',
								labelWidth:300,
								width:450
							},{
								xtype : 'commonsmallregionsselector',
								fieldLabel : '接货小区(commonsmallregionsselector)',
								labelWidth:300
							}, {
								xtype : 'commonowndriverselector',
								fieldLabel : '公司司机(commonowndriverselector)',
								labelWidth:300
							}, {
								xtype : 'commonowntruckselector',
								fieldLabel : '公司车(commonowntruckselector)',
								labelWidth:300
							}, {
								xtype : 'commonowntruckselector', 
								displayField : 'containerCode',// 显示名称
								valueField : 'containerCode',
								myQueryParam : 'containerCode',
								showContent : '{containerCode}',  
								fieldLabel : '货柜号(commonowntruckselector)',
								vehicleTypes : 'vehicletype_trailer',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-04',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonperscriptionregionselector',
								fieldLabel : '时效区域(commonperscriptionregionselector)',
								labelWidth:300
							}, {
								xtype : 'commonpriceregionselector',
								fieldLabel : '价格区域(commonpriceregionselector)',
								airPriceFlag :'Y',
								labelWidth:300
							}, {
								xtype : 'commonproductitemselector',
								fieldLabel : '产品条目(commonproductitemselector)',
								labelWidth:300
							}, {
								xtype : 'commoncustomerselector',
								fieldLabel : '客户(commoncustomerselector)',
								contcatFlag :'Y',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-05',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonsaledepartmentselector' ,
								fieldLabel : '营业部(commonsaledepartmentselector)',
								labelWidth:300
								
							}, {
								motorcadeCode : '34534534',
								xtype : 'commonmotorcadesaledeptselector',
								fieldLabel : '车队对应营业部(commonmotorcadesaledeptselector)',
								width:500,
								labelWidth:300
							}, {
								xtype : 'commonmotorcadesalesareaselector',
								fieldLabel : '车队对应营业区(commonmotorcadesalesareaselector)',
								width:500,
								labelWidth:300
							}, {
								xtype : 'commonmotorcadedistrictselector',
								fieldLabel : '车队对应行政区域(commonmotorcadedistrictselector)',
								width:500,
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-06',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonairagencycompanyselector',
								fieldLabel : '空运代理公司(commonairagencycompanyselector)',
								labelWidth:300
							}, {
								xtype : 'commonvehagencycompselector',
								fieldLabel : '偏线代理公司(commonvehagencycompselector)',
								labelWidth:300
							}, {
								xtype : 'commonairagencydeptselector',
								fieldLabel : '空运代理网点(commonairagencydeptselector)',
								labelWidth:300
							}, {
								xtype : 'commonvehagencydeptselector',
								fieldLabel : '偏线代理网点(commonvehagencydeptselector)',
								labelWidth:300
							}, {
								xtype : 'commonpositionselector', 
								fieldLabel : '职位(commonpositionselector)',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-07',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonogirfreightroutelineselector',
								fieldLabel : '始发站(commonogirfreightroutelineselector)',
								labelWidth:300

							}, {
								xtype : 'commondestfreightroutelineselector',
								fieldLabel : '目的站(commondestfreightroutelineselector)',
								labelWidth:300
							},{
								xtype : 'commonairlinesselector',
								forceSelection : true,
								fieldLabel : '航空公司(commonairlinesselector)',
								displayField : 'code',// 显示名称
								valueField : 'code',// 值 
								labelWidth:300
							}, {
								xtype : 'commonairlinesselector',
								fieldLabel : '机场(commonairlinesselector)',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-10',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonvehicletypeselector',
								fieldLabel : '车型(commonvehicletypeselector)',
								labelWidth:300/*,
								vehicleSort : 'ownership_leased'*/
							}, {
								xtype : 'commonaircrafttypeselector',
								fieldLabel : '机型(commonaircrafttypeselector)',
								labelWidth:300
							}, {
								xtype : 'commonairportselector', 
								fieldLabel : '机场(commonairportselector)',
								labelWidth:300
							}, {
								xtype : 'commonairporwithcitynametselector', 
								fieldLabel : '机场市县名称机场(commonairporwithcitynametselector)',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-11',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonlineselector',
								fieldLabel : '线路(commonlineselector)',
								lineSort:'BSE_LINE_SORT_SOURCE',
								labelWidth:300
							}, { 
								xtype : 'commonmotorcadeselector',
								fieldLabel : '车队(commonmotorcadeselector)',
								labelWidth:300 
							}, {
								active : 'Y',
								xtype : 'commonresourceselector',
								fieldLabel : '权限(commonresourceselector)',
								labelWidth:300
							}]
				}, {
					fieldLabel : '2012-12-15',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
								xtype : 'commonpayeeinfoselector',
								fieldLabel : '收款方信息(commonpayeeinfoselector)',
								isOnlyPartnerAccount:'N',
								accountTypes : '2,1',
								labelWidth:200
							},{
								xtype : 'commonpayeeinfoselector',
								fieldLabel : '合伙人账户信息(commonpayeeinfoselector)',
								isOnlyPartnerAccount:'Y',
								accountTypes : '2,1',
								labelWidth:200
							},{
								xtype : 'commonpayeeinfoselector',
								fieldLabel : '账户信息(commonpayeeinfoselector)',
								accountTypes : '2,1',
								labelWidth:200
							},{
								active : 'Y',
								fieldLabel : '组织对公账号(commonpublicbankaccountselector)',
								xtype : 'commonpublicbankaccountselector',
								labelWidth:200
							}, {
								active : 'Y',
								fieldLabel : '银行多选(commonbankmultiselector)',
								xtype : 'commonbankmultiselector',
								width:450,
								labelWidth:200
							}]
				}, {
					fieldLabel : '2012-12-28',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'commonservicezoneselector',
						labelWidth:300,
						fieldLabel:'集中接送货大小区(commonservicezoneselector)'
					}, {
						xtype : 'commontruckselector',
						labelWidth:300,
						fieldLabel:'车辆(commontruckselector)' 
					}, {
						xtype : 'commondriverselector',
						labelWidth:300,
						fieldLabel:'司机(commondriverselector)',
						parentOrgCode : 'W01140601',
						waybillFlag:'Y',					 
					}, {
						xtype : 'commonemployeemultiselector',
						fieldLabel:'人员多选(commonemployeemultiselector)',
						labelWidth:300, 
						parentOrgCode : 'W01140602',
						width:450 
					}, {
						xtype : 'commongoodsareamultiselector',
						labelWidth:300,
						width:450,
						fieldLabel:'库区多选(commongoodsareamultiselector)' 
					}]
				}, {
					fieldLabel : '2012-12-10',
					xtype : 'linkagecontainer',
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'button',
						text : '组织查询',
						handler : function() {
							var win = Ext
									.getCmp('Foss_baseinfo_commonSelector_orgSelectorWindow');
							if (Ext.isEmpty(win)) {
								win = Ext
										.create(
												'Foss.baseinfo.commonSelector.orgSelectorWindow',
												{
													'id' : 'Foss_baseinfo_commonSelector_orgSelectorWindow',
													'bigRegion' : 'Y',
													'smallRegion' :'Y',
													'type':'ORG' 
												});
							}
							win.down('grid').store.removeAll();
							win.down('form').getForm().reset();
							win.show();
						}
					}, {
						xtype : 'button',
						text : '客户银行账号',
						handler : function() {
							var win = Ext
									.getCmp('Foss_baseinfo_commonSelector_customerWindow');
							if (Ext.isEmpty(win)) {
								win = Ext
										.create(
												'Foss.baseinfo.commonSelector.CustomerWindow',
												{
													'id' : 'Foss_baseinfo_commonSelector_customerWindow' 
												});
							}
							win.down('grid').store.removeAll();
							win.show();
						}
					}, {
						xtype : 'button',
						text : '组织设值',
						handler : function() {
							Ext.getCmp('dynamicorgselector').setCombValue(
									'德邦物流', '0332233');
							alert(Ext.getCmp('dynamicorgselector').getValue());
						}
					}, {
						xtype : 'button',
						text : '国家设值',
						handler : function() {
							Ext.getCmp('npcclinkregincombselector')
									.setReginValue('英国', '200000', 0);
							alert(Ext.getCmp('npcclinkregincombselector').items.items[0]
									.getValue());
						}
					}, {
						xtype : 'button',
						text : '省设值',
						handler : function() {
							Ext.getCmp('npcclinkregincombselector')
									.setReginValue('伦敦', '201000', 1);
							alert(Ext.getCmp('npcclinkregincombselector').items.items[1]
									.getValue());
						}
					}, {
						xtype : 'button',
						text : '市设值',
						handler : function() {
							Ext.getCmp('npcclinkregincombselector')
									.setReginValue('不列颠', '201010', 2);
							alert(Ext.getCmp('npcclinkregincombselector').items.items[2]
									.getValue());
						}
					}, {
						xtype : 'button',
						text : '区县设值',
						handler : function() {
							Ext.getCmp('npcclinkregincombselector')
									.setReginValue('乡下', '201101', 3);
							alert(Ext.getCmp('npcclinkregincombselector').items.items[3]
									.getValue());
						}
					}, {
						xtype : 'button',
						text : '获取编码的值',
						handler : function() {
							alert(Ext.getCmp('vardynamicorgselector')
									.getValue());
						}
					}]
					}, {
						fieldLabel : '2013-01-08',
						xtype : 'linkagecontainer', 
						labelWidth : 80,
						labelSeparator : '',
						width : 1000,
						layout : 'column',
						items : [{
							xtype : 'commonflightselector',
							id:'commonflightselector', 
							isAgreementFlight:'Y',
							airDispatchCodes:'W011406020203,W31000206090611,W0114040401',
							labelWidth:160,
							fieldLabel:'航班号信息(commonflightselector)'
						},{
							xtype : 'dynamicorgmulticombselector',
							id : 'dynamicorgmultiselector',
							fieldLabel : '组织多选(dynamicorgmulticombselector)',
							type : 'ORG' ,
							labelWidth : 160,
							width:350
						}]
				}, {
					fieldLabel : '2013-01-12',
					xtype : 'linkagecontainer', 
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'commonmultismallzoneselector',
						id : 'commonmultismallzoneselector',
						fieldLabel : '小区多选(commonmultismallzoneselector)', 
						labelWidth : 160,
						width:350
					}]
			}, {
				fieldLabel : '2013-01-28',
				xtype : 'linkagecontainer', 
				labelWidth : 80,
				labelSeparator : '',
				width : 1000,
				layout : 'column',
				items : [{
					xtype : 'commonsaledeptandouterbranchselector', 
					fieldLabel : '营业部和偏线代理(commonsaledeptandouterbranchselector)', 
					labelWidth : 160,
					types:'ORG,KY',
					active:'Y',
					leave :'Y',
					airArrive : 'Y',
					productCode:'WVH',
					width:350
				}]
		}, {
			fieldLabel : '2013-01-29',
			xtype : 'linkagecontainer', 
			labelWidth : 80,
			labelSeparator : '',
			width : 1000,
			layout : 'column',
			items : [{
				xtype : 'commonorgextendsselector', 
				fieldLabel : '网点信息扩展添加营业部查询(commonorgextendsselector)', 
				labelWidth : 160,
				types:'ORG,KY',
				active:'Y',
				leave :'Y',
				airArrive : 'Y',
				width:350
			}]
	},{
		fieldLabel : '2013-01-29',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonproductselector', 
			fieldLabel : '产品类型公共选择器(commonproductselector)', 
			labelWidth : 160, 
			active:'Y', 
			width:350
		 }]
		},{
			fieldLabel : '2013-01-30',
			xtype : 'linkagecontainer', 
			labelWidth : 80,
			labelSeparator : '',
			width : 1000,
			layout : 'column',
			items : [{
				xtype : 'commontransfercenterselector', 
				fieldLabel : '查询外场公共选择器(commontransfercenterselector)', 
				labelWidth : 160, 
				active:'Y',  
				width:350
			 }]
			},{
				fieldLabel : '2013-02-02',
				xtype : 'linkagecontainer', 
				labelWidth : 80,
				labelSeparator : '',
				width : 1000,
				layout : 'column',
				items : [{
					xtype : 'commonsubsidiaryselector', 
					fieldLabel : '查询子公司选择器(commonsubsidiaryselector)', 
					labelWidth : 160,  
					width:350
				 }]
				},{
					fieldLabel : '2013-04-26',
					xtype : 'linkagecontainer', 
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'commonroleselector', 
						fieldLabel : '角色选择器(commonroleselector)', 
						labelWidth : 160,  
						width:350
					 }]
					},{
						fieldLabel : '2013-04-26',
						xtype : 'linkagecontainer', 
						labelWidth : 80,
						labelSeparator : '',
						width : 1000,
						layout : 'column',
						items : [{
							xtype : 'commonallagentselector', 
							fieldLabel : '代理选择器(commonallagentselector)', 
							labelWidth : 160,  
							width:350
					}]
					},{
						fieldLabel : '2013-08-02',
						xtype : 'linkagecontainer', 
						labelWidth : 80,
						labelSeparator : '',
						width : 1000,
						layout : 'column',
						items : [{
							xtype : 'commonExpressVehicleselector', 
							fieldLabel : '快递车牌公共选择器(commonExpressVehicleselector)', 
							active:'Y',
							labelWidth : 160,  
							width:500
				}]
				},{
					fieldLabel : '2013-08-02',
					xtype : 'linkagecontainer', 
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'commonOldExpressEmpselector', 
						fieldLabel : '快递员公共选择器(commonOldExpressEmpselector)', 
						active:'Y',
						districtCodes : '440104,310118,310112,110105',
						labelWidth : 160,  
						width:500
				}]
				},{
							fieldLabel : '2013-07-23',
							xtype : 'linkagecontainer', 
							labelWidth : 80,
							labelSeparator : '',
							width : 1000,
							layout : 'column',
							items : [{
								xtype : 'commonLdpAgencyCompanySelector', 
								fieldLabel : '快递代理公司选择器(commonLdpAgencyCompanySelector)', 
								active:'Y',
								labelWidth : 160,  
								width:350
					}]
					},{
						fieldLabel : '2013-07-25',
						xtype : 'linkagecontainer', 
						labelWidth : 80,
						labelSeparator : '',
						width : 1000,
						layout : 'column',
						items : [{
							xtype : 'commonldpagencydeptselector', 
							fieldLabel : '快递代理网点选择器(commonldpagencydeptselector)', 
							active:'Y',
							labelWidth : 160,  
							width:350
				}]
				},{
					fieldLabel : '2013-07-26',
					xtype : 'linkagecontainer', 
					labelWidth : 80,
					labelSeparator : '',
					width : 1000,
					layout : 'column',
					items : [{
						xtype : 'dynamicorgcombselector', 
						fieldLabel : '虚拟营业部公共选择器(dynamicorgcombselector)', 
						active:'Y',
						expressSalesDepartment:'Y',
						expressBigRegion:'Y',
						type : 'ORG',
						labelWidth : 160,  
						width:350
			}]
			},{
				fieldLabel : '2013-07-26',
				xtype : 'linkagecontainer', 
				labelWidth : 80,
				labelSeparator : '',
				width : 1000,
				layout : 'column',
				items : [{
					xtype : 'dynamicorgcombselector', 
					fieldLabel : '快递点部公共选择器(dynamicorgcombselector)', 
					active:'Y',
					expressPart:'Y',
					type : 'ORG',
					labelWidth : 160,  
					width:350
		}]
		},{
			fieldLabel : '2013-07-26',
			xtype : 'linkagecontainer', 
			labelWidth : 80,
			labelSeparator : '',
			width : 1000,
			layout : 'column',
			items : [{
				xtype : 'commonExpressAndOrgSelector', 
				fieldLabel : '快递代理网点+快递点部公共选择器(dynamicorgcombselector)', 
				active:'Y'
//				typeParam:'ORG,LDP',
//				cityName ：'北京',
			}]
	},{
		fieldLabel : '2013-08-07',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'dynamicVirtualDeptcombselector', 
			fieldLabel : '虚拟营业部公共选择器多选(dynamicVirtualDeptcombselector)', 
			active:'Y',
			expressSalesDepartment:'Y',
			labelWidth : 160,  
			width:350,
			transCenterCodes :'W31000206090611'
		}]
	},{
		fieldLabel : '2013-08-27',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'dynamicVirtualDepartmentcombselector', 
			fieldLabel : '虚拟营业部公共选择器单选(dynamicVirtualDepartmentcombselector)', 
			active:'Y',
			expressSalesDepartment:'Y',
			labelWidth : 160,  
			width:350,
			transCenterCodes :'W31000206090611'
		}]
	},{
		fieldLabel : '2013-08-12',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonexpresspriceregionselector',
			fieldLabel : '快递价格区域(commonexpresspriceregionselector)',
			airPriceFlag :'Y',
			labelWidth:300
		}]
	},{
		fieldLabel : '2013-08-28',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonExpressemployeeselector',
			fieldLabel : '快递员new(commonExpressemployeeselector)',
			airPriceFlag :'Y',
			labelWidth:300
		}]
	},{
		fieldLabel : '2013-11-05',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonLdpAndExpressAndOrgSelector',
			fieldLabel : '快递代理网点—自有网点—虚拟网点commonLdpAndExpressAndOrgSelector)',
			airPriceFlag :'Y',
			labelWidth:300
		}]
	},{
		fieldLabel : '2013-11-05',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commoninfodeptSelector',
			fieldLabel : '信息部—commoninfodeptSelector)',
			airPriceFlag :'Y',
			labelWidth:300
		}]
	},{
		fieldLabel : '2013-11-05',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'dynamicPackagingSupplierSelector',
			fieldLabel : '包装供应商—dynamicPackagingSupplierSelector)',
			active:'Y',
			//airPriceFlag :'Y',
			labelWidth:300
		}]
	},{
		fieldLabel : '2014-07-29',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commoncostcenterdeptSelector',
			fieldLabel : '成本中心部门—commoncostcenterdeptSelector)',
			labelWidth:300
		}]
	},{
		fieldLabel : '2014-07-04',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonpriceregionbigticketselector',
			fieldLabel : '大票价格始发区域(commonpriceregionbigticketselector)',
			airPriceFlag :'N',
			labelWidth:300
		},{
			xtype : 'commonallpriceregionBigTicketselector',
			fieldLabel : '大票价格目的区域(commonallpriceregionBigTicketselector)',
			airPriceFlag :'N',
			labelWidth:300
		}]
	},{
		fieldLabel : '2014-10-29',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonAiragentAndDeptselector',
			fieldLabel : '新空运代理—commonAiragentAndDeptselector)',
			labelWidth:300
		}]
	},{
		fieldLabel : '2016-02-29',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commongeneraltaxpayerselector',
			fieldLabel : '(一般纳税人信息—commongeneraltaxpayerselector)',
			labelWidth:300
		}]
	},{
		fieldLabel : '2016-08-22',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonTwoLevelNetworkselector',
			fieldLabel : '(二级合伙人网点—commonTwoLevelNetworkselector)',
			labelWidth:300
		}]
	},{
		fieldLabel : '2016-08-22',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonLeagueSaleDeptselector',
			fieldLabel : '(一级合伙人网点—commonLeagueSaleDeptselector)',
			labelWidth:300
		}]
	},{
		fieldLabel : '2016-07-14',
		xtype : 'linkagecontainer', 
		labelWidth : 80,
		labelSeparator : '',
		width : 1000,
		layout : 'column',
		items : [{
			xtype : 'commonpriceregionecgoodsselector',
			fieldLabel : '首续重价格始发区域(commonpriceregionecgoodsselector)',
			airPriceFlag :'N',
			labelWidth:300
		},{
			xtype : 'commonallpriceregionEcGoodsselector',
			fieldLabel : '首续重价格目的区域(commonallpriceregionEcGoodsselector)',
			airPriceFlag :'N',
			labelWidth:300
		}]
	}],
		renderTo : 'T_baseinfo-selector-body'
	});
});