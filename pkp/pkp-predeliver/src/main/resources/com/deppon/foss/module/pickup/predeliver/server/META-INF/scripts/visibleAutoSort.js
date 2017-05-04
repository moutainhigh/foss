(function() {
	 var visDistance_Data = '';
	 var visDistance_TotalDistance = '';
	 var visDistance_VisibleMapJSON = '';
	 var visAging_Data = '';
	 var visAging_TotalDistance = '';
	 var visAging_VisibleMapJSON = '';
	/**
	 * 定义自动排序的模型
	 */
	Ext.define('Foss.predeliver.visibleOrderBill.visibleAutoSortModel', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'string'
		},{
			name : 'serialNo',
			type : 'int'
		}, {
			name : 'receiveCustomerAddress', // 送货地址
			type : 'string'
		}, {
			name : 'recommendedDeliveryTime', // 建议送货时间
			type : 'string'
		}, {
			name : 'deliveryTimeInterval',// 送货时间段
			type : 'string'
		}, {
			name : 'deliveryTimeStart',// 送货时间点--开始时间
			type : 'string'
		}, {
			name : 'deliveryTimeOver',// 送货时间点--结束时间
			type : 'string'
		}, {
			name : 'waybillNo',// 运单号
			type : 'string'
		}, {
            name : 'cashTime', // 规定兑现时间
            type : 'string',
			convert: function(value) {
	            if (value != null) {
	            	var date = new Date(value);
	            	return Ext.Date.format(date,'Y-m-d H:i');
	           	} else {
	           		return null;
	           	}
	        }   
		},  {
			name : 'specialAddressType',// 特殊地址类型
			type : 'string'
		}, {
			name : 'goodStatus',// 特殊货物类型
			type : 'string'
		}, {
			name : 'goodsSize',// 尺寸
			type : 'string'
		}, {
			name : 'goodsPackage',// 包装
			type : 'string'
		}, {
			name : 'longitude',// 经度
			type : 'string'
		}, {
			name : 'latitude',// 纬度
			type : 'string'
		}, {
			name : 'serialNo',// 序号
			type : 'string'
		}, {
			name : 'deliverDate',//预计送货时间
			type:'date'
		}, {
			name : 'productCode',	//运输性质
			type : 'string'
		}, {
			name : 'isEmptyCar',	//是否空车出
			name : 'string'
		} ]
	});
	/**
	 * 定义自动排序对应Store
	 */
	Ext.define('Foss.predeliver.visibleOrderBill.visibleAutoSortStore', {
		extend : 'Ext.data.Store',
		model : 'Foss.predeliver.visibleOrderBill.visibleAutoSortModel',
		
		proxy: {
	        type : 'ajax',
	        url : predeliver.realPath('visiblebillInfoList.action'),
	        reader: {
	            type: 'json',
	            root: 'vo.visibleAutoSortDtoList'
	        }
	  },
	  deliverbillId : '',
		setDeliverbillId : function(deliverbillId) {
			this.deliverbillId = deliverbillId;
		},
		getDeliverbillId : function() {
			return this.deliverbillId;
		},
	  listeners : {
			beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'deliverbillVo.deliverbillDto.id' : this.deliverbillId
					}
				});
				}
			}
			
	});
	
	/**
	 * 定义自动排序FORM的MODEL
	 */
	Ext.define('Foss.predeliver.visibleOrderBill.deliverbillInfoModel', {
		extend : 'Ext.data.Model',
		fields : [ 'id','deliverbillNo', 'vehicleNo', 'driverName', 'carTaskInfo',
		           'frequencyNo', 'preCarTaskTime', 'takeGoodsDeptCode','takeGoodsDeptName',
		           'expectedbringVolume','transferDeptCode', 'transferDeptName',
		           'totalDistance','calculateType'
		       ]
	});
	
	
	/**
	 * 可视化自动排序 window
	 */
	Ext
			.define(
					'Foss.predeliver.visibleOrderBill.visibleAutoSortWindow',
					{
						extend : 'Ext.window.Window',
						title : '可视化自动排序',
						id : 'Foss_predeliver_visibleOrderBill_visibleAutoSortWindow',
						modal : true,
						resizable : false,
						width : window.screen.availWidth * 0.95,
						height : window.screen.availHeight * 0.9,
						layout : 'border',
						defaults : {
							margin : '1 1 1 1'
						},
						items : [
								{
									xtype : 'form',
									region : 'north',
									frame : true,
									height : 90,
									layout : 'column',
									defaults : {
										margin : '5 5 5 5',
										height : 25,
										labelWidth : 60,
										readOnly:true,
										readOnlyCls: '.x-form-field .x-form-text'
									},
									defaultType : 'textfield',
									items : [ {
										name : 'deliverbillNo',
										fieldLabel : '派送单号', // 派送单号
										columnWidth : .2

									}, {
										name : 'vehicleNo',
										fieldLabel : '车牌号', // 车牌号
										columnWidth : .2
									}, {
										name : 'driverName',
										fieldLabel : '司机',// 司机
										columnWidth : .2
									}, {
										xtype : 'combobox',
										name : 'carTaskInfo',
										fieldLabel : '出车任务',// 出车任务
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .2,
										store : FossDataDictionary.getDataDictionaryStore('DISPATCH_VEHICLE_TASK')
									}, {
										name : 'frequencyNo',
										fieldLabel : '班次',// 班次
										columnWidth : .2
									}, {
										name : 'preCarTaskTime',
										fieldLabel : '预计出车时间', // 预计出车时间
										format : 'Y-m-d',
										labelWidth : 85,
										columnWidth : .2
									}, {
										name : 'takeGoodsDeptName',
										fieldLabel : '带货部门',
										columnWidth : .2
									}, {
										name : 'expectedbringVolume',
										fieldLabel : '带货(F)', // 带货
										columnWidth : .2
									}, {
										name : 'transferDeptName',
										fieldLabel : '转货部门', // 转货部门
										columnWidth : .2
									} ]
								},
								{
									xtype : 'container',
									region : 'center',
									layout : 'hbox',
									defaults : {
										margin : '1 1 1 1'
									},
									items : [
											{
												xtype : 'container',// 地图区域
												id : 'visibleOrderBill_visibleAutoSort_GIS_P',
												width : window.screen.availWidth * 0.95 - 480,
												height : 730,
												listeners : {
													afterrender : function(field) {
														var provCity = FossUserContext.getCurrentDept().provName
																+ FossUserContext.getCurrentDept().cityName;
														//初始化地图对象 

														
														  var autoMap = new DPMap.MyMap('VIEW',field.id,{center:provCity,zoom:"TOWN"},function(map) { 
														   	cjj = new DMap.SingleTrack(map,{isAddable:false});
													     	//cjj.DistanceSortSingleTrack(json);
														
														/* var myMap = DMap.SingleTrack(map,{isAddable:false});
														   
														   //1.实现距离路线按钮点击事件  
														    document.getElementById('distancebtn').addEventListener('click',function(){
														    	//返回运单号信息给foss系统
														    	myMap.DistanceSortSingleTrack(distancejsonInfo);
														    });
														    //2.实现时效路线按钮点击事件   
														    document.getElementById('AgingRankbtn').addEventListener('click',function(){
														    	//返回运单号信息给foss系统
														    	myMap.AgingRankSingleTrack(timejsonInfo);
														    });
														    //3.实现人工路线按钮点击事件   
														    document.getElementById('ManualSortbtn').addEventListener('click',function(){
														    	//返回运单号信息给foss系统
														    	myMap.ManualSortSingleTrack(manualjsonInfo);
														    });*/
														 });
													}
												}
											},
											{
												xtype : 'panel',
												layout : 'fit',
												style : {
													backgroundColor : 'rgb( 255, 255, 255)'
												},
												width : 450,
												height : window.screen.availHeight * 0.9 - 155,
												padding : 0,
												margin : 1,
												items : [ {
													xtype : 'grid',
													frame : true,
													//title : '已排单运单',
													margin : 0,
													store : Ext.create('Foss.predeliver.visibleOrderBill.visibleAutoSortStore'),
													autoScroll : true,
													sortableColumns : false,
													tbar:[
													      {
														xtype: 'label',
														text: '已排单运单 ',
														width : 80,
														margin : '10 80 0 0',
														style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:5px solid #3d74b7;'
														},
														{xtype:'panel',border: 1,width:35,height:20,cls:'predeliver_row_red'},
														{xtype:'label',text:'建议送货时间不满足客户收货时间'}
													],
													dockedItems : [ {// 底部统计信息
														xtype : 'container',
														dock : 'bottom',
														layout : 'column',
														items : [
																{
																	columnWidth : .55,
																	height : 26,
																	xtype : 'button',
																	cls : 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
																	text : '距离排序', // 距离优先,
																	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
																		seconds: 20
																		}),
																	handler : function(btnCel) {
																		var gridStore =btnCel.up('window').down('grid').getStore();
																		var formModel =btnCel.up('window').down('form').getRecord().data;
																		formModel.calculateType ='DISTANCE_PRIORITY';
																		if (!Ext.isEmpty(visDistance_Data)) {
																			gridStore.loadData(visDistance_Data);
																			var visibleAutoSortDtoList = new Array();
																			var gridNewStore =btnCel.up('window').down('grid').getStore();
																			for (var i=0; i < gridNewStore.data.items.length; i++) {
																				visibleAutoSortDtoList.push(gridNewStore.data.items[i].data);
																			}
																			btnCel.up('window').down('displayfield[name=totalDistance1]').setValue(visDistance_TotalDistance);
																			formModel.totalDistance=visDistance_TotalDistance;
																			cjj.DistanceSortSingleTrack(visDistance_VisibleMapJSON);
																			//将运单详情进行更新
																			Ext.Ajax.request({
																				url : predeliver.realPath('updateVisibleDeliverbillInfo.action'),
																				async : false,
																				jsonData:{
																					'vo' : {
																						'visibleAutoSortDtoList' : visibleAutoSortDtoList
																					},
																					'deliverbillNewVo' : {
																						'deliverbillNewDto' : formModel
																					}
																				},
																				exception: function(response) {
																					var result = Ext.decode(response.responseText);
																					Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																				}
																			});
																		}else{
																			Ext.Ajax.request({
																				url : predeliver.realPath('visibleAutoSortCalculate.action'),
																				async : false,
																				timeout : 25000,
																				jsonData:{
																					'deliverbillNewVo' : {
																						'deliverbillNewDto' : formModel
																					},
																					'deliverbillVo' : {
																						'deliverbillDto' :{
																							'id' : gridStore.getDeliverbillId()
																						}
																					}
																				},
																				success : function(response) {
																					var result = Ext.decode(response.responseText);
																					visDistance_Data = result.vo.visibleAutoSortDtoListBak;
																					visDistance_TotalDistance = result.vo.totalDistance;
																					visDistance_VisibleMapJSON = result.vo.visibleMapJSON;
																					btnCel.up('window').down('displayfield[name=totalDistance1]').setValue(result.vo.totalDistance);
																					gridStore.load();
																					cjj.DistanceSortSingleTrack(result.vo.visibleMapJSON);
																				},
																				exception: function(response) {
																					var result = Ext.decode(response.responseText);
																					Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																				}
																			});
																		}
																	}
																},
																{
																	xtype : 'displayfield',
																	allowBlank : true,
																	labelWidth : 120,
																	name : 'totalDistance1',
																	columnWidth : .45,
																	value : '0',
																	fieldLabel : '里程数(公里)'
																},
																{
																	columnWidth : .55,
																	height : 26,
																	xtype : 'button',
																	cls : 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
																	text : '时效排序', // 时效优先,
																	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
																		seconds: 20
																		}),
																	handler : function(btnCel,e) {
																		var gridStore =btnCel.up('window').down('grid').getStore();
																		var formModel =btnCel.up('window').down('form').getRecord().data;
																		formModel.calculateType ='AGING_PRIORITY';
																		if (!Ext.isEmpty(visAging_Data)) {
																			gridStore.loadData(visAging_Data);
																			var visibleAutoSortDtoList = new Array();
																			var gridNewStore =btnCel.up('window').down('grid').getStore();
																			for (var i=0; i < gridNewStore.data.items.length; i++) {
																				visibleAutoSortDtoList.push(gridNewStore.data.items[i].data);
																			}
																			btnCel.up('window').down('displayfield[name=totalDistance2]').setValue(visAging_TotalDistance);
																			formModel.totalDistance=visAging_TotalDistance;
																			cjj.AgingRankSingleTrack(visAging_VisibleMapJSON);
																			Ext.Ajax.request({
																				url : predeliver.realPath('updateVisibleDeliverbillInfo.action'),
																				async : false,
																				jsonData:{
																					'vo' : {
																						'visibleAutoSortDtoList' : visibleAutoSortDtoList
																					},
																					'deliverbillNewVo' : {
																						'deliverbillNewDto' : formModel
																					}
																				},
																				exception: function(response) {
																					var result = Ext.decode(response.responseText);
																					Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																				}
																			});
																		}else{
																			Ext.Ajax.request({
																				url : predeliver.realPath('visibleAutoSortCalculate.action'),
																				async : false,
																				timeout : 25000,
																				jsonData:{
																					'deliverbillNewVo' : {
																						'deliverbillNewDto' : formModel
																					},
																					'deliverbillVo' : {
																						'deliverbillDto' :{
																							'id' : gridStore.getDeliverbillId()
																						}
																					}
																				},
																				success : function(response) {
																					var result = Ext.decode(response.responseText);
																					visAging_Data = result.vo.visibleAutoSortDtoListBak;
																					visAging_TotalDistance = result.vo.totalDistance;
																					visAging_VisibleMapJSON = result.vo.visibleMapJSON;
																					btnCel.up('window').down('displayfield[name=totalDistance2]').setValue(result.vo.totalDistance);
																					gridStore.load();
																					cjj.AgingRankSingleTrack(result.vo.visibleMapJSON);
																				},
																				exception: function(response) {
																					var result = Ext.decode(response.responseText);
																					Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																				}
																			});
																		}
																	}
																},
																{
																	xtype : 'displayfield',
																	allowBlank : true,
																	labelWidth : 120,
																	name : 'totalDistance2',
																	columnWidth : .45,
																	value : '0',
																	fieldLabel : '里程数(公里)'
																},{
																	columnWidth : .55,
																	height : 26,
																	xtype : 'button',
																	cls : 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
																	text : '人工调整地图展示', // 人工调整地图展示
																	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
																		seconds: 20
																		}),
																	handler : function(btnCel,e) {

																		var gridStore =btnCel.up('window').down('grid').getStore();
																		var formModel =btnCel.up('window').down('form').getRecord().data;
																		formModel.calculateType ='ARTIFICIAL_PRIORITY';
																		Ext.Ajax.request({
																			url : predeliver.realPath('visibleAutoSortCalculate.action'),
																			async : false,
																			timeout : 25000,
																			jsonData:{
																				'deliverbillNewVo' : {
																					'deliverbillNewDto' : formModel
																				},
																				'deliverbillVo' : {
																					'deliverbillDto' :{
																						'id' : gridStore.getDeliverbillId()
																					}
																				}
																			},
																			success : function(response) {
																				var result = Ext.decode(response.responseText);
																				btnCel.up('window').down('displayfield[name=totalDistance3]').setValue(result.vo.totalDistance);
																				gridStore.load();
																				cjj.ManualSortSingleTrack(result.vo.visibleMapJSON);
																			},
																			exception: function(response) {
																				var result = Ext.decode(response.responseText);
																				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																			}
																		});
																		
																	
																	}
																},
																{
																	xtype : 'displayfield',
																	allowBlank : true,
																	labelWidth : 120,
																	name : 'totalDistance3',
																	columnWidth : .45,
																	value : '0',
																	fieldLabel : '里程数(公里)'
																} ]
													} ],
													columns : [
															/*{
																xtype : 'actioncolumn',
																width : 50,
																header : '操作', // 操作,
																align : 'center',
																items : [
																		{
																			iconCls : 'deppon_icons_up',
																			tooltip : '上移', // 上移,
																			handler : function(grid,rowIndex,colIndex) {
																				if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
																					Ext.ux.Toast.msg('提示','已经是第一条记录！','error', 4000);
																					return;
																				}
																				Ext.Ajax.request({
																					url : predeliver.realPath('upgradeDeliverbillDetail.action'),
																					params : {
																						'deliverbillVo.deliverbillDto.id' : grid.getStore().deliverbillId,
																						'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
																						'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
																					},
																					success : function(response) {
																						// 刷新当前页
																						grid.store.load();
																					},
																					exception: function(response) {
																						var result = Ext.decode(response.responseText);
																						Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																					}
																				});
																			}
																		},
																		{
																			iconCls : 'deppon_icons_down',
																			tooltip : '下移', // 下移,
																			handler : function(grid,rowIndex,colIndex) {
																				if (grid.getStore().getAt(rowIndex).get('serialNo') == grid.getStore().getTotalCount()) {
																					Ext.ux.Toast.msg('提示','已经是最后一条记录！','error', 4000);
																					return;
																				}
																				Ext.Ajax.request({
																					url : predeliver.realPath('downgradeDeliverbillDetail.action'),
																					params : {
																						'deliverbillVo.deliverbillDto.id' : grid.getStore().deliverbillId,
																						'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
																						'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
																					},
																					success : function(response) {
																						// 刷新当前页
																						grid.store.load();
																					},
																					exception: function(response) {
																						var result = Ext.decode(response.responseText);
																						Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																					}
																				});
																			}
																		} ]

															},*/
															{
																dataIndex : 'id',
																align : 'center',
																flex : 1,
																hidden : true
															},
															{
																dataIndex : 'waybillNo',
																align : 'center',
																width : 90,
																text : '运单号'
															},
															{
																dataIndex : 'cashTime',
																align : 'center',
																width : 90,
																xtype : 'ellipsiscolumn',
																text : '规定兑现时间'
															},															
															{
																dataIndex : 'receiveCustomerAddress',
																align : 'left',
																width : 180,
																xtype : 'linebreakcolumn',
																text : '送货地址'
															},
															{
																dataIndex : 'recommendedDeliveryTime',
																align : 'center',
																width : 90,
																text : '建议送货时间'
															},
															{
																dataIndex : 'deliveryTimeInterval',
																align : 'center',
																width : 80,
																text : '送货时间段'
															},
															{
																text : '送货时间点',
																align : 'center',
																width : 90,
																xtype:'templatecolumn', 
																tpl:'{deliveryTimeStart} - {deliveryTimeOver}'
															},
															
															{
																dataIndex : 'specialAddressType',
																text : '特殊地址类型',// 特殊地址类型
																renderer : function(value) {
																		return FossDataDictionary.rendererSubmitToDisplay(
																				value, 'PKP_SPECIAL_DELIVERYADDRESS_TYPE');
																	}
															},
															{
																dataIndex : 'goodStatus',
																align : 'center',
																xtype : 'ellipsiscolumn',
																text : '特殊货物类型'
															},
															{
																dataIndex : 'goodsSize',
																align : 'center',
																xtype : 'ellipsiscolumn',
																text : '尺寸'
															},
															{
																dataIndex : 'goodsPackage',
																align : 'center',
																text : '包装'
															} ],
															viewConfig: {
														        stripeRows : false,
														        enableTextSelection : true,
														        getRowClass : function(record, rowIndex, rp, ds) {
														        	var preCarTaskTime = this.up('window').down('form').getRecord().get('preCarTaskTime');
														        	//如果有建议送货时间
														        	if (record.data.recommendedDeliveryTime != null && record.data.recommendedDeliveryTime != '') {
														        		//建议送货时间大于出车时间
														        		if (record.data.recommendedDeliveryTime > preCarTaskTime) {
														        			//预计送货时间点不为空
																			if (record.data.deliveryTimeStart != null && record.data.deliveryTimeStart != '') {
																				//建议送货时间小于或等于预计送货时间(开始) 或者 建议送货时间大于或等于预计送货时间(结束)
																				if (record.data.recommendedDeliveryTime <= record.data.deliveryTimeStart || record.data.recommendedDeliveryTime >= record.data.deliveryTimeOver) {
																					return 'predeliver_row_red';
																				}
																			//没有预计送货时间点
																			}else{
																				//预计送货时间段不为空
																				if (record.data.deliveryTimeInterval !=null && record.data.deliveryTimeInterval != '') {
																					//预计送货时间段为上午
																					if (record.data.deliveryTimeInterval == '上午') {
																						if (record.data.recommendedDeliveryTime > '12:00') {
																							return 'predeliver_row_red';
																						}
																					}
																					//预计送货时间段为下午
																					if (record.data.deliveryTimeInterval == '下午') {
																						if (record.data.recommendedDeliveryTime < '12:00' ) {
																							return 'predeliver_row_red';
																						}
																					}
																				}
																			}
																		}
																	}
																},
																plugins: {
																	ptype: 'gridviewdragdrop',
																	dragText: '拖拽行记录进行排序,点击保存后生效!'
																}
															}
												} ],
												
												buttons : [
														{
															text : '保存',
															cls : 'yellow_button',
															handler : function(btnCel) {
																var formModel =btnCel.up('window').down('form').getRecord().data;
																var gridStore =btnCel.up('window').down('grid').getStore();
																var visibleAutoSortDtoList = new Array();
																for (var i=0; i < gridStore.data.items.length; i++) {
																	visibleAutoSortDtoList.push(gridStore.data.items[i].data);
																}
                                
                                var sortGridList = new Array();
                                gridStore.each(function(item, index, length) {
                                  var obj = new Object();
                                  obj.id = item.data.id;
                                  obj.waybillNo = item.data.waybillNo;
                                  sortGridList.push(obj);
                                });
                                
																Ext.Ajax.request({
																	url : predeliver.realPath('updateVisibleDeliverbillInfo.action'),
																	async : false,
																	jsonData:{
																		'vo' : {
																			'visibleAutoSortDtoList' : visibleAutoSortDtoList,
                                      'dragStr': sortGridList.length < 1 ? null : Ext.encode(sortGridList)
																		},
																		'deliverbillNewVo' : {
																			'deliverbillNewDto' : formModel
																		}
																	},
																	success : function(response) {
																		// 关闭页面
																		//btnCel.up('window').close();
                                    gridStore.load();
                                    Ext.ux.Toast.msg('提示', '保存成功!', 'error', 3000);
																	},
																	exception: function(response) {
																		var result = Ext.decode(response.responseText);
																		Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
																	}
																});
																
															}

														}
														/*,{
															text : '取消',
															cls : 'yellow_button',
															handler : function(btnCel) {
																// 关闭页面
																btnCel.up('window').close();
															}
														} */
														],
											}

									]
								} ],
						listeners : {
							close : function() {
								visDistance_Data = '';
								visDistance_TotalDistance = '';
								visDistance_VisibleMapJSON = '';
								visAging_Data = '';
								visAging_TotalDistance = '';
								visAging_VisibleMapJSON = '';
								Ext.ComponentManager
										.unregister(Ext
												.getCmp('Foss.predeliver.visibleOrderBill.visibleAutoSortWindow'));
							}
						}
					});

})();