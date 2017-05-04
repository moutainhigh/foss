/***
 * 可视化排单
 */

predeliver.visibleOrderBill.RETURN_TYPE = 'PKP_VISIBLE_WAYBILL_RETURN';
var leftFlag = false; //定义左移动变量
var rightFlag = false; //定义右移动变量
var deptCode = FossUserContext.getCurrentUserDept().code; //登录用户部门代码
var deptName = FossUserContext.getCurrentUserDept().name; //登录用户部门名称
predeliver.visibleOrderBill.vehicleNoTruckType = null;
var gis_AutoSort_URL = 'http://192.168.10.43:80/gis-ws/matchservice/navigationSortAction.action';

// 获得当前部门是否营业部
var dept = FossUserContext.getCurrentDept().salesDepartment;
(function() {
		// 获得当前组织对应的车队
		Ext.Ajax.request({
			url : predeliver.realPath('querySuperOrg.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					fleetCode : json.fleetCode
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		});
})(); 

(function() {
		// 获得派送单sequence
		Ext.Ajax.request({
			url : predeliver.realPath('querySequence.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					sequence : json.sequence
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		})
})();

// 生成派送单号
function querySequence(){
	var sequence=null;
	Ext.Ajax.request({
		url : predeliver.realPath('querySequenceNew.action'),
		async : false,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			sequence = json.sequence;
		},
		exception : function(response) {
			Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
		}
	});
	return sequence;
}

/*//顶级车队->下属车队->营业部
(function() {
	if (FossUserContext.getCurrentDept().transDepartment == 'Y'
			|| FossUserContext.getCurrentDept().dispatchTeam == 'Y'
			|| FossUserContext.getCurrentDept().transTeam == 'Y') {
		// 车队
		predeliver.visibleOrderBill.dept = 'N';
	} else {
		// 营业部
		predeliver.visibleOrderBill.dept = 'Y';
	}
	console.info(predeliver.visibleOrderBill.dept);
	if (predeliver.visibleOrderBill.dept != 'Y') {
		// 获得当前组织对应的车队
		Ext.Ajax.request({
					url : predeliver.realPath('querySuperOrg.action'),
					async : false,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						console.info(json);
						// 如果找不到对应的顶级车队
						if (Ext.isEmpty(json.fleetCode)) {
							// 判定为营业部，权限不放大
							predeliver.visibleOrderBill.dept = 'Y';
						}
						Ext.apply(predeliver, {
									fleetCode : json.fleetCode
								});
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示',result.message, 'error', 3000);
					}
				});
		// 获得车队下的车队组
		Ext.Ajax.request({
			url : predeliver.realPath('querySubOrg.action'),
			async : false,
			jsonData : {
				fleetCode : predeliver.fleetCode
			},
			success : function(response) {
				var json = Ext.decode(response.responseText);
				console.info(json);
				Ext.apply(predeliver, {
					fleet : json.dispatchOrderConditionVo.dispatchOrderConditionDto.serviceFleetList
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
			}
		});
	} else {
		Ext.apply(predeliver, {
					fleetCode : FossUserContext.getCurrentDept().code
				});
	}
})();*/

//派车类型集合
Ext.define('Foss.predeliver.store.DeliverTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'NOMAL','valueName':'正常'},//predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.nomal')},// '正常'
			{'valueCode':'SPECIAL','valueName':'专车'},//predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.special')},// '专车'
			{'valueCode':'MANNED','valueName':'带人'}//predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.manned')}//'带人'
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});

//定义预派送单的模型
Ext.define('Foss.predeliver.visibleOrderBill.DeliverbillModel', {
	extend : 'Ext.data.Model',
	idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
	// 定义字段
	fields : [ {
		name : ' extid ',
		type : 'string'
	},// 额外的用于生成的EXT使用的列
	{
		name : 'id',
		type : 'string'
	}, {
		name : 'deliverbillNo', // 派送单号
		type : 'string'
	}, {
		name : 'vehicleNo', // 车辆
		type : 'string'
	}, {
		name : 'driverCode', // 司机编号
		type : 'string'
	}, {
		name : 'driverName', // 司机姓名
		type : 'string'
	}, {
		name : 'waybillQtyTotal', // 总票数
		type : 'int'
	}, {
		name : 'goodsQtyTotal', // 总件数
		type : 'int'
	}, {
		name : 'payAmountTotal', // 总到付金额
		type : 'number'
	}, {
		name : 'weightTotal', // 总重量
		type : 'number'
	}, {
		name : 'volumeTotal', // 总体积
		type : 'number'
	},{
		name : 'deliverType', //派车类型
		type : 'string'
	}, {
		name : 'createUserName', // 创建人
		type : 'string'
	}, {
		name : 'createUserCode', // 创建人编码
		type : 'string'
	}, {
		name : 'submitTime' // 提交时间
	}, {
		name : 'tOptTruckDepartId', // 车辆放行ID
		type : 'string'
	}, {
		name : 'status', // 状态
		type : 'string'
	}, {
		name : 'createOrgName', // 创建部门
		type : 'string'
	}, {
		name : 'createOrgCode', // 创建部门编码
		type : 'string'
	}, {
		name : 'operator', // 操作人
		type : 'date'
	}, {
		name : 'operatorCode', // 操作人编码
		type : 'string'
	}, {
		name : 'operateOrgName', // 操作人部门
		type : 'string'
	}, {
		name : 'operateOrgCode', // 操作人部门编码
		type : 'string'
	}, {
		name : 'operateTime', // 操作时间
		type : 'string'
	}, {
		name : 'fastWaybillQty', // 卡货票数
		type : 'int'
	}, {
		name : 'carTaskinfo', // 出车任务
		type : 'string'
	}, {
		name : 'frequencyno', // 班次
		type : 'int'
	}, {
		name : 'preCartaskTime', // 预计出车时间
		type : 'string'
	}, {
		name : 'takeGoodsDeptcode', // 带货部门编码
		type : 'string'
	}, {
		name : 'takeGoodsDeptname', // 带货部门
		type : 'string'
	}, {
		name : 'expectedbringvolume', // 带货(F)
		type : 'number'
	}, {
		name : 'transferDeptcode', // 转货部门编码
		type : 'string'
	}, {
		name : 'transferDeptname', // 转货部门
		type : 'string'
	}  ]
});

//定义地址坐标标记的模型
Ext.define('Foss.predeliver.visibleOrderBill.coordinateMarkModel', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'id',
			type: 'string',
		},{
			name: 'waybillNo',
			type: 'string',
		},{
			name: 'actualAddress',
			type: 'string',
		},{
			name: 'matchType',
			type: 'string',
		},{
			name: 'actualSmallzoneName',
			type: 'string',
		},{
			name: 'actualSmallzoneCode',
			type: 'string',
		},{
			name: 'receiveCustomerProvCode',
			type: 'string',
		},{
			name: 'receiveCustomerCityCode',
			type: 'string',
		},{
			name: 'receiveCustomerDistCode',
			type: 'string',
		},{
			name: 'receiveCustomerAddress',
			type: 'string',
		},{
			name: 'phone',
			type: 'string',
		},{
			name: 'tel',
			type: 'string',
		},{
			name: 'longitude',
			type: 'string',
		},{
			name: 'latitude',
			type: 'string',
		},{
			name: 'juheAdress',
			type: 'string',
		}
	]
});

//基础信息Form对应Model
Ext.define('Foss.predeliver.model.ViSibleOrderConBill', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'}, /** 运单号 */
		{name : 'deliverDate', convert:dateConvert}, /**  送货日期 */
		{name: 'productCode'}, /**运输性质*/
		{name: 'goodsStatus'}, /**货物状态*/
		{name: 'vehicleType'}, /**所属车队组*/
		{name: 'deliverGrandArea'}, /**送货大区*/
		{name: 'deliverSmallArea'}, /**送货小区*/
		{name: 'specialNoType'}, /**特殊运单*/
		{name: 'lateNo'}, /** 晚交运单*/
		{name: 'tallymanReturnReason'} /** 理货员退回*/
	]
});

//已排单运单的表格Model
Ext.define('Foss.predeliver.visibleOrderBill.deliverbillDetailGridModel', {
	extend : 'Ext.data.Model',
	idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
	// 定义字段
	fields : [
		{// 额外的用于生成的EXT使用的列
			name : ' extid ',
			type : 'string'
		}, {
			name : 'id',
			type : 'string'
		}, {
			name : 'tSrvDeliverbillId',
			type : 'string'
		}, {
			name : 'serialNo', // 派送单明细序列号
			type : 'int'
		}, {
			name : 'waybillNo', // 运单号
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
		}, {
			name : 'consigneeAddress', // 送货地址
			type : 'string'
		}, {
			name : 'actualSmallzoneName', // 预处理建议.送货小区
			type : 'string'
		}, {
			name : 'vehicleNo', // 预处理建议.车牌号
			type : 'string'
		}, {
			name : 'preArrangeGoodsQty', // 已排单件数
			type : 'int'
		}, {
			name : 'weight', // 已排单重量
			type : 'number'
		}, {
			name : 'goodsVolumeTotal', // 已排单体积
			type : 'number'
		}, {
			name : 'dimension', // 运单尺寸
			type : 'string'
		}, {
			name : 'goodsPackage', // 包装
			type : 'string'
		}, {
			name : 'transportType', // 运输方式
			type : 'string'
		}, {
			name : 'deliverDate', // 送货日期
			type : 'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 var dd =Ext.Date.format(date,'Y-m-d H:i:s');
					 var deliverTime ='1900-01-01 00:00:00';
					 if(dd== deliverTime ){
						return null;
					 }
					 return date;
				 }else{
					 return null;
				 }
			}
		}, {
			name : 'deliveryTimeInterval', // 送货时间段
			type : 'string'
		}, {
			name : 'deliveryTimeStart', // 送货时间范围-开始
			type : 'string'
		}, {
			name : 'deliveryTimeOver', // 送货时间范围-结束
			type : 'string'
		}, {
			name : 'payAmount', // 到付款金额
			type : 'number'
		}, {
			name : 'togetherSendCode', //合送编码
			type : 'string'
		}, {
			name : 'isVehicleScheduling', //车辆是否排班
			type : 'string'
		}, {
			name : 'longitude', //经度
			type : 'string'
		}, {
			name : 'latitude', //纬度
			type : 'string'
		}, {
			name : 'smallzoneName', //小区名称
			type : 'string'
		}, {
			name : 'smallzoneCode', //小区代码
			type : 'string'
		}]
});

function jsArrToJSON(wayBillnosArr) {
	//var waybillstr ='[{"waybillno":"10111287"}]';
	var jsonStr= '';
	if (wayBillnosArr) {
		 if (wayBillnosArr.length > 0) {
				for(var i=0; i < wayBillnosArr.length; i++) {
					jsonStr = jsonStr + '{"waybillno":"' + wayBillnosArr[i] + '"},'
				}
				jsonStr = jsonStr.substring(0, jsonStr.length - 1);
				return '[' + jsonStr + ']'
		 }
	}
	return;
}

//基础信息Form对应Store
Ext.define('Foss.predeliver.store.ViSibleOrderConBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.predeliver.model.ViSibleOrderConBill',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('visibleOrderBill.action')
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var myForm = predeliver.visibleOrderBill.orderBasicInfoForm.getForm();
			console.info(myForm);
			var queryParams = myForm.getValues();

			// 验证运单号输入的行数
			if (!Ext.isEmpty(queryParams.waybillNo)) {
				var arrayWaybillNo = queryParams.waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
						return false;
					}
				}
			}

			Ext.apply(operation, {
				params:{
					'vo.conditionDto.waybillNo':queryParams.waybillNo,  //运单号
					'vo.conditionDto.deliverDate':queryParams.deliverDate,// 送货日期
					'vo.conditionDto.productCode':queryParams.productCode,	//运输性质
					'vo.conditionDto.goodsStatus':queryParams.goodsStatus,		//货物状态
					'vo.conditionDto.vehicleType':queryParams.vehicleType,		//所属车队组
					'vo.conditionDto.deliverGrandArea':queryParams.deliverGrandArea, //送货大区
					'vo.conditionDto.deliverSmallArea':queryParams.deliverSmallArea,	//送货小区
					'vo.conditionDto.specialNoType':queryParams.specialNoType,	//特殊运单
					'vo.conditionDto.lateNo':queryParams.lateNo, 		//晚交运单
					'vo.conditionDto.tallymanReturnReason':queryParams.tallymanReturnReason,	//理货员退回
					'vo.conditionDto.deliverInga':queryParams.deliverInga,	//进仓货
					'vo.conditionDto.noDeliverInga':queryParams.noDeliverInga,	//非进仓货
					'vo.conditionDto.uitraLongDelivery':queryParams.uitraLongDelivery,	////超远派送
					'vo.conditionDto.isExhibition':queryParams.isExhibition,	//会展货	
					'vo.conditionDto.pieceInspection':queryParams.pieceInspection	//逐件验货
					}
			});
		},
		load: function(store) {
			var data = store.getProxy().getReader().rawData;
			jsonInfoGIS = data.vo.paramForGIS;
			if (!Ext.isEmpty(jsonInfoGIS)) {
						//进入地图默认的显示区域
						var mapArea = (JSON.parse(jsonInfoGIS))[0].administrationCity
						var dmap = new DPMap.MyMap('VIEW', 'predeliver_VisibleOrderBill_GIS',{center : mapArea,zoom:"TOWN"},function(map) {

								myMap = DMap.CoverFeature(map,{isAddable:false});

								//返回失败或异常的运单号集合
								var failedWaybillNos = new Array();

								//1.查询运单,并在地图上显示覆盖物标志出来
								var loadMap = myMap.renderingCoordinatePoint(jsonInfoGIS);

								//2.实现选中的运单, 增加排单
								//document.getElementById('predeliver_visibleOrderBill_AddArrange_ID').addEventListener('click', function(){
								//});

								//2.实现选中的运单，移除排单
								document.getElementById('predeliver_visibleOrderBill_moveOut_ID').addEventListener('click', function(){
										var selectRow = predeliver.visibleOrderBill.deliverbillDetailGrid.getSelectionModel().getSelection();
										if (selectRow.length == 0) {
											Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
													predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
											return;
										}

										//删除前检查排单状态是否已取消
										Ext.Ajax.request({
												url : predeliver.realPath('queryDeliverbillInfoById.action'),
												params : {
													'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
												},
												success : function(response) {
													var result = Ext.decode(response.responseText);
													if (result.vo.deliverStatus) {
														//如果派送单状态已经取消，则提示终止
														if (result.vo.deliverStatus == 'CANCELED') {
															Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '当前派送单已取消不能继续操作，请关闭当前页面后再次进入操作', 'error', 3000);
															return;
														}
													}

													var moveArr = new Array();
													Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),  predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveDetail'),
														function(btn, text) {
															if (btn == "yes") {
																var ids = '';
																for ( var i = 0; i < selectRow.length; i++) {
																	ids = selectRow[i].data.id+ ","+ ids;
																	moveArr.push(selectRow[i].data.waybillNo);
																}
																predeliver.visibleOrderBill.deliverbillDetailGrid.deleteDeliverbillDetails(ids);

																//延迟执行
																var dTask = new Ext.util.DelayedTask(function(){
																		var waybillstr = jsArrToJSON(moveArr);
																		myMap.resetwaybillMarkerFromFoss(waybillstr);
																});
																dTask.delay(2000);
															}
														}
													);
												},
												exception: function(response){
													var json = Ext.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
												}
										});
								});

								//3.实现画区域框清除
								document.getElementById('predeliver_visibleOrderBill_ClearSelectMap_ID').addEventListener('click',function(){
									//返回运单号信息给foss系统
									myMap.clearAreaframe();
								});

								//3.3 修改排单件数
								//document.getElementById('predeliver_visibleOrderBill_ModifyCount_ID').addEventListener('click', function(){
								//});

								//4.运单退回
								document.getElementById('predeliver_visibleOrderBill_wayBillReturn_ID').addEventListener('click', function(){
									//运单退回前检查排单状态是否已取消
									Ext.Ajax.request({
											url : predeliver.realPath('queryDeliverbillInfoById.action'),
											params : {
												'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
											},
											success : function(response) {
													var result = Ext.decode(response.responseText);
													if (result.vo.deliverStatus) {
														//如果派送单状态已经取消，则提示终止
														if (result.vo.deliverStatus == 'CANCELED') {
															Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '当前派送单已取消不能继续操作，请关闭当前页面后再次进入操作', 'error', 3000);
															return;
														}
													}

													var wabyInfoArr = new Array(); //定义待排单运单号数组
													var gisArray = new Array(); //定义GIS返回的运单号数组
													gisArray = myMap.chooseInwaybill();
													for(var i = 0; i< gisArray.length; i++) {
														wabyInfoArr.push(gisArray[i]);
														predeliver.visibleOrderBill.waybillsToReturn.push(gisArray[i]);
													}
													console.info(wabyInfoArr);
													if (wabyInfoArr.length > 0) {
														//GIS传递的值
														var returnWin = Ext.create('Foss.predeliver.visibleOrderBill.billReturnWin');
														returnWin.down('hidden[name=returnBills]').setValue(wabyInfoArr.join(','));
														returnWin.show(this);
														returnWin.down('textarea[name=returnReasonNotes]').hide();
													}
											}
									});
								});
						});
			} else {
					 var provCity = FossUserContext.getCurrentDept().provName + FossUserContext.getCurrentDept().cityName;
							 var dmap = new DPMap.MyMap('VIEW', 'predeliver_VisibleOrderBill_GIS',{center : provCity ,zoom:"TOWN"},function(map) {

									myMap = DMap.CoverFeature(map,{isAddable:false});

									var loadMap = myMap.renderingCoordinatePoint(jsonInfoGIS);

									//3.实现画区域框清除
									document.getElementById('predeliver_visibleOrderBill_ClearSelectMap_ID').addEventListener('click',function(){
										//返回运单号信息给foss系统
										myMap.clearAreaframe();
									});

					 });
			}
		}
	}
});

//定义地址坐标标记对应Store
Ext.define('Foss.predeliver.store.coordinateMarkStore',{
	extend:'Ext.data.Store',
	model:'Foss.predeliver.visibleOrderBill.coordinateMarkModel'
});

//合计信息
var visibleTotalTicket;
var visibleTotalCount;
var visibleTotalWeight;
var visibleTotalVolumn;
var visibleTotalPayAmount;
var visibleLoadRate;
var visibleNominalRate;
//已排单运单的表格Store
Ext.define('Foss.predeliver.visibleOrderBill.deliverbillDetailGridStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.predeliver.visibleOrderBill.deliverbillDetailGridModel',
	pageSize : 50,
	proxy: {
        type : 'ajax',
        url : predeliver.realPath('visibleQueryDeliverbillDetailList.action'),
        actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'vo.arrageListDto',
            totalProperty : 'totalCount'
        }
  },
	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.callParent([ cfg ]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var deliverTime = predeliver.visibleOrderBill.orderBasicInfoForm.getForm().findField('deliverDate').getValue();
			var vehilceNo = predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').getValue();
			Ext.apply(operation, {
					params : {
						'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId,
						'vo.deliverDate' : Ext.util.Format.date(deliverTime, 'Y-m-d'),
						'vo.vehilceNo' : vehilceNo
					}
			});
		},
		load : function(store, records, successful, oOpts) {
				var data = store.getProxy().getReader().rawData;
				if(!data.success &&(!data.isException)){
					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), data.message, 'error', 2000);	//提示
					return;
				}
				//更新已排单表格下的信息
				var bottomPanel = predeliver.visibleOrderBill.deliverbillDetailGrid;
				if (data.vo.totalTicket) {//总票数
					bottomPanel.down('displayfield[name=totalTicket]').setValue(data.vo.totalTicket);
					visibleTotalTicket = data.vo.totalTicket;
				} else {
					bottomPanel.down('displayfield[name=totalTicket]').setValue(null);
					bottomPanel.down('displayfield[name=totalCount]').setValue(null);
					bottomPanel.down('displayfield[name=totalWeight]').setValue(null);
					bottomPanel.down('displayfield[name=totalVolumn]').setValue(null);
					bottomPanel.down('displayfield[name=totalPayAmount]').setValue(null);
					bottomPanel.down('displayfield[name=loadRate]').setValue(null);
					bottomPanel.down('displayfield[name=nominalRate]').setValue(null);
					visibleTotalTicket = null;
					visibleTotalCount = null;
					visibleTotalWeight = null;
					visibleTotalVolumn = null;
					visibleTotalPayAmount = null;
					visibleLoadRate = null;
					visibleNominalRate = null;
				}
				if (data.vo.totalCount) {//总件数
					bottomPanel.down('displayfield[name=totalCount]').setValue(data.vo.totalCount);
					visibleTotalCount = data.vo.totalCount;
				}
				if (data.vo.totalWeight) {//总重量
					bottomPanel.down('displayfield[name=totalWeight]').setValue(data.vo.totalWeight);
					visibleTotalWeight = data.vo.totalWeight;
				}
				if (data.vo.totalVolumn) {//总体积
					bottomPanel.down('displayfield[name=totalVolumn]').setValue(data.vo.totalVolumn);
					visibleTotalVolumn = data.vo.totalVolumn;
				}
				if (data.vo.totalPayAmount) {//到付金额
					bottomPanel.down('displayfield[name=totalPayAmount]').setValue(data.vo.totalPayAmount);
					visibleTotalPayAmount = data.vo.totalPayAmount;
				}
				if (data.vo.loadRate) {//装载率(容量和体积)
					bottomPanel.down('displayfield[name=loadRate]').setValue(data.vo.loadRate);
					visibleLoadRate = data.vo.loadRate;
				}
				if (data.vo.nominalRate) {//额定净空/额定载重率
					bottomPanel.down('displayfield[name=nominalRate]').setValue(data.vo.nominalRate);
					visibleNominalRate = data.vo.nominalRate;
				}
				
				var gridStoreLoad = predeliver.visibleOrderBill.deliverbillDetailGrid.getStore();
				if (gridStoreLoad.getCount() > 0) {
					//如果有数据，则设置车牌号为只读
					if (!predeliver.visibleOrderBill.isOrSave) {
						predeliver.visibleOrderBill.isOrSave = true;
					/*	predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;text-align:center}';
						predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').setReadOnly(true);
						predeliver.visibleOrderBill.ButtonPanelRole.down('commonmotorcadeselector[name=vehicleType]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;text-align:center}';
						predeliver.visibleOrderBill.ButtonPanelRole.down('commonmotorcadeselector[name=vehicleType]').setReadOnly(true); */
					}
				} else {
					/*	predeliver.visibleOrderBill.isOrSave = false;
						predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').setReadOnly(false);
						predeliver.visibleOrderBill.ButtonPanelRole.down('commonmotorcadeselector[name=vehicleType]').setReadOnly(false); */
				}
		}
	}
});

//基础信息Form
Ext.define('Foss.predeliver.visibleOrderBill.vsOrderBasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : '查询排单运单',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	padding: '2 15 5 15',
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '1 15 1 15', //上右下左
		labelWidth : 90,
	},
	defaultType : 'textfield',
	layout : {
		type: 'table',
		columns: 4
	},
	items : [
		{
			name : 'waybillNo',
			xtype: 'textarea',
			type : 'string',
			fieldLabel: '运单号',
			emptyText: '运单号为8到9位数字，以回车输入，最多输入50个运单号',
			regex : /^([0-9]{8,10}\n?)+$/i,
			regexText : predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
			height:78,
			rowspan: 4
		}, //运单号
		{//送货日期
			name: 'deliverDate',
			xtype: 'datefield',
			fieldLabel: '送货日期',
			value: new Date(),
			format : 'Y-m-d',
			editable: true,
			listeners: {
				'blur': function(deliverDate, newValue, oldValue, eOpts) {
					//如果未进行排单，更改日期；清空车牌号
					//var middlePanel = predeliver.visibleOrderBill.ButtonPanelRole;
					//middlePanel.down('commontruckselector[name=vehicleNo]').setValue('');
				}
			}
		},//送货日期
		{//运输性质
			xtype:'dynamicmulticomboselector',
			displayField:'name',
			valueField:'code',
			queryMode:'remote',
			triggerAction:'all',
			name : 'productCode',
			fieldLabel : predeliver.visibleOrderBill.i18n('pkp.predeliver.notifyCustomer.productCode'),
			labelWidth: 80,
			width: 260,
			store : Ext.create('Ext.data.Store', {
				fields : ['code','name'],
				proxy : {
					type : 'ajax',
					url :predeliver.realPath('queryThreeLevelProductAll.action'),
					reader: {
							 type: 'json',
							 root: 'vo.productEntitys'
					 }
				}
			}),
			showContent:'{name}',// 显示表格列 {valueName}&nbsp;&nbsp;{valueCode}
			isPaging:false,// 分页
			isEnterQuery:true// 回车查询
		},
		{//货物状态
			xtype: 'combobox',
			name: 'goodsStatus',
			fieldLabel: '货物状态',
			displayField:'name',
			value:'KC',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
      editable: false,
			store: Ext.create('Ext.data.Store',{
				fields: ['code','name'],
				data: [{'code':'30','name':'预计到达'},
								{'code':'40', 'name':'已到达'},{'code':'KC', 'name':'库存中'}]
			})
		}, //货物状态
		{//所属车队组
			name: 'vehicleType',
			fieldLabel: '所属车队组',
			xtype : 'commonmotorcadeselector',
			loopOrgCodes : predeliver.fleetCode
			/*listeners : {
				'change' : function(field, event, eOpts) {
					var form = field.up('form').getForm(),
						vehicleNo = form.findField('vehicleNo');
						driverName = form.findField('driverName');
					if (field.value != '' && field.value != null) {
						vehicleNo.setReadOnly(false);
						vehicleNo.getStore().un('beforeload');
						vehicleNo.getStore().on('beforeload', function(store,operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							searchParams['truckVo.truck.loopOrgCode'] = field.value;
						});
						vehicleNo.getStore().load();
						vehicleNo.expand();
					}
					else
					{
						vehicleNo.setValue('');
						driverName.setValue('');

					}
				}
			} */
		},
		{//送货大区
			xtype: 'commonbigregionsdeselector',
			name: 'deliverGrandArea',
			fieldLabel: '送货大区',
			labelWidth: 80,
			//management: predeliver.fleetCode,
			width: 260
		},
		{//送货小区
			xtype: 'commonmultismallzoneselector',
			name: 'deliverSmallArea',
			fieldLabel: '送货小区',
			queryAllFlag : true,
			regionType : 'DE',
			loopOrgCode : predeliver.fleetCode,
			labelWidth: 90,
			width: 245
		},
		{
			xtype : 'container' ,
			layout: 'column',
			colspan: 2,
			width: 630,
			items:[
				{
					name: 'specialNoType',
					xtype: 'checkbox',
					boxLabel: '特殊运单',
					margin : '5 2 2 10'
				}, //特殊运单
				{
					name: 'deliverInga',
					xtype: 'checkbox',
					boxLabel: '进仓货',
					margin : '5 2 2 10'
				}, //进仓货
				{
					name: 'noDeliverInga',
					xtype: 'checkbox',
					boxLabel: '非进仓货',
					margin : '5 2 2 10'
				}, //非进仓货
				{
					name: 'uitraLongDelivery',
					xtype: 'checkbox',
					boxLabel: '超远派送',
					margin : '5 2 2 10'
				}, //超远派送
				{
					name: 'isExhibition',
					xtype: 'checkbox',
					boxLabel: '会展货',
					margin : '5 2 2 10'
				}, //会展货
				{
					name: 'pieceInspection',
					xtype: 'checkbox',
					boxLabel: '逐件验货',
					margin : '5 2 2 10'
				}, //逐件验货
				{
					name: 'lateNo',
					xtype: 'checkbox',
					boxLabel: '晚交运单',
					margin : '5 2 2 10'
				}, //晚交运单
				{
					name: 'tallymanReturnReason',
					xtype: 'checkbox',
					boxLabel: '理货员退回',//理货员退回
					labelWidth: 100,
					margin : '5 2 0 10'
				}
			]
		},
		{
			border: 1,
			xtype:'container',
			defaultType:'button',
			margin : '0 0 0 0',
			padding: '0 0 5 0',
			layout:'hbox',
			defaults: {
				height: 27,
				width: 80,
				align: 'middle'
			},
			items:[
				{ //重置
					text : '重置',
					handler : function() {
						var myForm = this.up('form').getForm();
						myForm.reset();
					}
				}, {  //查询
					text : '查询',
					disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/condtionQueryButton'),
					hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/condtionQueryButton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
										seconds: 3
									}),
					cls : 'yellow_button',
					name : 'btnQuery',
					handler : function (btn) {
						var form = btn.up('form').getForm();
						var values = form.getValues();
						var store = Ext.create('Foss.predeliver.store.ViSibleOrderConBillStore');
						store.load();
					}
				}
			]
		}
	 ],
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//地图.待排运单.运单退回窗口
Ext.define('Foss.predeliver.visibleOrderBill.billReturnWin',{
		extend : 'Ext.window.Window',
		title : ' 退回派送部',
		modal : true,
		height:'autoHeight',
    closable: false,
		//closeAction:'hide',
		resizable : true,
		items : [{
			xtype : 'form',
			width : 400,
			defaults: {
				margin: '5 20 5 10',
				labelWidth: 90
			},
			layout: {
					type : 'table',
					columns : 2
			},
			items : [
				{
					xtype : 'combobox',
					fieldLabel: '退回原因',
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					allowBlank: false,
					blankText: '必须选择退回原因',
					editable:false,
					colspan: 2,
					name : 'returnReason',
					value : '',
					store : FossDataDictionary.getDataDictionaryStore(predeliver.visibleOrderBill.RETURN_TYPE, null),
					listeners: {
							'change': function(returnReason, newValue) {
								if ('OTHER_REASONS' == newValue.trim()) {
									returnReason.up('form').getForm().findField('returnReasonNotes').show(this);
								} else {
									returnReason.up('form').getForm().setValues({returnReasonNotes: ' '});
									returnReason.up('form').getForm().findField('returnReasonNotes').hide(); ;
								}
							}
					}
				}, {
					xtype : 'textarea',
					width: 360,
					name : 'returnReasonNotes',
					colspan : 2
			  },
				{//退回运单集合
					xtype : 'hiddenfield',
					name : 'returnBills'
			  }
			]
		}],
		buttons : [ {
			text : '保存',
			name : 'btnSave',
			handler: function(btnSave) {
				var returnReason = btnSave.up('window').down('combobox[name=returnReason]').getValue();
				if (!returnReason) {
					Ext.ux.Toast.msg('提示', '请选择退回派送部的退回原因！', 'error', 2500);
					return;
				}
				var returnBillNos = btnSave.up('window').down('hiddenfield[name=returnBills]').getValue();

				var returnReasonNotes = btnSave.up('window').down('textarea[name=returnReasonNotes]').getValue();
				if (returnReasonNotes.length > 500) {
					Ext.ux.Toast.msg('提示', '退回派送部的退回原因备注超过最大字数(500)', 'error', 2500);
					return;
				}
				Ext.Ajax.request({
					url : predeliver.realPath('visibleBillReturn.action'),
					async : false,
					jsonData:{
						'vo' : {
							'handBillReturnEntity': {
								'waybillNo': returnBillNos,
								'returnReason': returnReason,
								'returnReasonNotes': returnReasonNotes
							}
						}
					},
					success : function(response) {
							var result = Ext.decode(response.responseText);
							var strWaybill = "以下运单退回失败：<br/>";
							if (result.vo.handBillReturnEntity.waybillNo !=null && result.vo.handBillReturnEntity.waybillNo !='') {
								//多个运单
								if (result.vo.handBillReturnEntity.waybillNo.indexOf(',') > 0) {
									var waybillNos = result.vo.handBillReturnEntity.waybillNo.split(',');
									for(var i=0; i < waybillNos.length; i++) {
										strWaybill = strWaybill + waybillNos[i] + '<br/>'
									}
								} else { //单个运单
									strWaybill = strWaybill + result.vo.handBillReturnEntity.waybillNo + '<br/>';
								}
								Ext.Msg.alert('提示', strWaybill, function(optional){
									if (optional == 'ok') {
										btnSave.up('window').close();
									}
								});
						 } else {
								Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), ' 退回操作成功', 'success', 3000);
								btnSave.up('window').close();
							}
					},
					exception : function(response) {}
				});
			}
		}, {
			text : '取消',
			id: 'predeliver_visibleOrderBill_wayReturnCancel_ID',
			name: 'btnCel',
			handler: function(btnCel) {
					if (predeliver.visibleOrderBill.waybillsToReturn.length > 0) {
								var waybillstr = jsArrToJSON(predeliver.visibleOrderBill.waybillsToReturn);
								myMap.resetwaybillMarkerFromFoss(waybillstr);
								predeliver.visibleOrderBill.waybillsToReturn.length = 0;
					}
					btnCel.up('window').close();
			}
		} ],
		listeners: {
			'close': function() {
				Ext.ComponentManager.unregister(Ext.getCmp('predeliver_visibleOrderBill_wayReturnCancel_ID'));
			}
		}
});

//地图.待排运单.修改排单件数窗口
Ext.define('Foss.predeliver.visibleOrderBill.modifiyArrangeWin',{
		extend : 'Ext.window.Window',
		title : ' 排单件数修改',
		id: 'predeliver_visibleOrderBill_modifiyArrangeWin_ID',
		modal : true,
    closable: false,
		resizable : false,
		items : [{
			xtype : 'form',
			width : 400,
			height: 260,
			defaults: {
				margin: '5 20 5 10',
				labelWidth: 90
			},
			layout : 'column',
			items : [
				{//运单号
					xtype: 'textfield',
					name: 'wayBillNo',
					fieldLabel:'运单号&nbsp;&nbsp;',
					readOnly: true,
					labelAlign: 'right',
					columnWidth : 1
				},
				{//开单件数
					xtype: 'textfield',
					name: 'waybillGoodsQty',
					fieldLabel:'开单件数',
					readOnly: true,
					labelAlign: 'right',
					columnWidth : 1
				},
				{//交单件数
					xtype: 'textfield',
					name: 'billQty',
					fieldLabel:'交单件数',
					readOnly: true,
					labelAlign: 'right',
					columnWidth : 1
				},
				{//可排件数
					xtype: 'textfield',
					name: 'arrangableGoodsQty',
					fieldLabel:'可排件数',
					readOnly: true,
					labelAlign: 'right',
					columnWidth : 1
				},
				{//排单件数
					xtype: 'textfield',
					name: 'arrangeCount',
					fieldLabel:'排单件数',
					labelAlign: 'right',
					allowBlank: false,
					width: 100,
					columnWidth : .8,
					regex: new RegExp("^[1-9]\\d*"),
					regexText: '排单件数不能以0开头且不能为0!'
				},
				{//保存
					xtype: 'button',
					text : '保存',
					margin: '50 20 0 60',
					name : 'btnSave',
					width: 80,
					columnWidth : .5,
					handler: function(btnSave) {
								var thisForm = btnSave.up('form').getForm();
								//校验表单有效性
								if (!thisForm.isValid()) {
									return;
								}

								//排单件数 <= 可排件数
								if (thisForm.findField('arrangeCount').getValue() > thisForm.findField('arrangableGoodsQty').getValue()) {
									Ext.Msg.alert(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '排单件数只能小于或等于可排件数！');
									return;
								}

								//获取修改后的排单件数
								modifiyArrangeWinCount = thisForm.findField('arrangeCount').getValue();

								//校验车辆必输
								var middPanelForm = predeliver.visibleOrderBill.ButtonPanelRole.getForm();
								if (Ext.isEmpty(middPanelForm.findField('vehicleNo').getValue())) {
									Ext.Msg.alert(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '&nbsp;&nbsp;&nbsp;&nbsp;请先选择车辆!');
									return;
								}

								var flg = false;


								var modfiyWayBillInfo = predeliver.visibleOrderBill.modfiyToArrange;

								//遍历返回结果添加到待排运单列表
								Ext.Array.each(modfiyWayBillInfo, function(item, index, length) {
									  //修改排单件数
										item.arrangableGoodsQty = thisForm.findField('arrangeCount').getValue();
										item.preArrangeGoodsQty = thisForm.findField('arrangeCount').getValue();
										item.arrangeGoodsQty = thisForm.findField('arrangeCount').getValue();
										predeliver.visibleOrderBill.waybillsToArrange.add(item.id, item);
										//是否必送货
										if(item.isSentRequired == 'Y') {
											flg = true;
										}
								});

								var waybills = ''; //是否已联系客户(通知记录)
								var selectWaybillNos =new Array();//存放复选框选中的运单
								var selectWaybillNosWVH =new Array();//存放复选框选中的运输性质为整车的运单
								predeliver.visibleOrderBill.waybillsToArrange.each(function(item, index, length) {
										selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
										//没有通知记录的放入waybills里, 有通知记录的不放入
										if (item.isAlreadyContact == 'N') {
											waybills += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + item.waybillNo
													+ '<br/>';
										}
										if (item.transportType == 'WVH') {//如果运输性质为整车
											selectWaybillNosWVH.push(item.waybillNo);//得到复选框选中的运单号集合
										}
								});

								//如果是必送货
								if (flg == true) {
									Ext.Msg.alert(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
										 if (btn == 'ok') {
												//校验运输性质为整车
												if(selectWaybillNosWVH.length>0){
													var checkResult=predeliver.visibleOrderBill.checkWayBillNosWVH(selectWaybillNosWVH);
													if(!checkResult){
														return;
													}
												}
												//有通知记录,直接校验
												if (waybills == '') {
													//验证单货物状态是否发生了变更
													predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
													btnSave.up('window').close();
												} else {//如果没有有通知记录，给予提示
													/*var confirmMsgBox = Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
														if (btn == 'yes') {
															//验证单货物状态是否发生了变更
															predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
														}
													},this); //predeliver.visibleOrderBill.arrangeDeliverbillWindow */

													Ext.Msg.alert(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '&nbsp;&nbsp;&nbsp;&nbsp;'+predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+'<br/>'+waybills+'&nbsp;&nbsp;&nbsp;&nbsp;无通知记录',function(btn){
														if (btn == 'ok') {
															//验证单货物状态是否发生了变更
															predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
															btnSave.up('window').close();
														} else {
															//TODO 未确认操作
														}
													});
												}
										} else {
											//TODO 未确认操作
										}
									});
								} else {
										//校验运输性质为整车
										if(selectWaybillNosWVH.length>0){
											var checkResult=predeliver.visibleOrderBill.checkWayBillNosWVH(selectWaybillNosWVH);
											if(!checkResult){
												return;
											}
										}
										//有通知记录,直接校验
										if (waybills == '') {
											//验证单货物状态是否发生了变更
											predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
											btnSave.up('window').close();
										} else {//如果没有有通知记录，给予提示
											/*var confirmMsgBox = Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
												if (btn == 'yes') {
													//验证单货物状态是否发生了变更
													predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
												}
											},this); //predeliver.visibleOrderBill.arrangeDeliverbillWindow */

											Ext.Msg.alert(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '&nbsp;&nbsp;&nbsp;&nbsp;'+predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+'<br/>'+waybills+'&nbsp;&nbsp;&nbsp;&nbsp;无通知记录',function(btn){
														if (btn == 'ok') {
															//验证单货物状态是否发生了变更
															predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
															btnSave.up('window').close();
														} else {
															//TODO 未确认操作
														}
											});

										}
							}
					}
				},
				{//取消
					xtype: 'button',
					text : '取消',
					margin: '50 50 0 30',
					name : 'btnCel',
					width: 80,
					columnWidth : .5,
					handler: function(btnCel) {
						if (predeliver.visibleOrderBill.modfiyToArrange.length > 0) {
									//单个运单移动到地图
									var waybillstr = new Array();
									waybillstr.push(predeliver.visibleOrderBill.modfiyToArrange[0].waybillNo);
									var waybillstrJSON = jsArrToJSON(waybillstr);
									myMap.resetwaybillMarkerFromFoss(waybillstrJSON);
									predeliver.visibleOrderBill.modfiyToArrange.length = 0;
						}
						btnCel.up('window').close();
					}
				}
			]
		}]
});

var jsonInfoGIS = null;
//定义地图区域
Ext.define('Foss.predeliver.visibleOrderBill.WaybillToArrangeMap', {
	extend : 'Ext.form.Panel',
	//columnWidth : .6,
	id: 'visibleOrderBill_WaybillToArrangeMap_ID',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	margin : '0 2 0 0',
	//width: 800,
	anchor : '100%',
	flex: 4,
	border: 1,
	layout : 'column',
	style: {borderColor:'#000000', borderStyle:'solid', borderWidth:'1px'},
	height:700,
	tbar:[{
			xtype: 'label',
			text: '地图查看',
			height: 25,
			style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:4px solid #3d74b7;'
		},'->',{
			xtype: 'textfield',
			name: 'tbarAddressCon',
			width: 260,
			fieldLabel:'',
			labelSeparator: '',
			height: 25,
			listeners: {
				'specialkey': function(field, e) {
						if (e.getKey() == e.ENTER) {
							myMap.searchAddressPoint(field.getValue());
						}
				}
			}
		},{
			xtype: 'button',
			name: 'btnAddresQuery',
			text: '地址检索',
			height: 25,
			cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
			handler: function(btnAddresQuery) {
				var conAddressInfo = btnAddresQuery.up('form').getForm().findField('tbarAddressCon').getValue();
				myMap.searchAddressPoint(conAddressInfo);
			}
		},{
			xtype:'button',
			name: 'beginMapBtn',
			id: 'predeliver_visibleOrderBill_BeginSelectMap_ID',
			columnWidth: .08,
			height: 25,
			text: '开启圈选',//暂时写死，留待i18n处理
			cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
			handler: function(beginMapBtn) {
				myMap.startDrawAreaKuang();
			}
		},{
			xtype:'button',
			name: 'clearMapBtn',
			id: 'predeliver_visibleOrderBill_ClearSelectMap_ID',
			columnWidth: .08,
			height: 25,
			text: '清除圈选',//暂时写死，留待i18n处理
			cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon'
		},
		{
			xtype:'button',
			name: 'modBtn',
			id: 'predeliver_visibleOrderBill_ModifyCount_ID',
			columnWidth: .08,
			height: 25,
			text: '修改排单件数',//暂时写死，留待i18n处理
			cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
			handler: function(modBtn) {
					//修改排单件数前检查排单状态是否已取消
					Ext.Ajax.request({
							url : predeliver.realPath('queryDeliverbillInfoById.action'),
							params : {
								'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
							},
							success : function(response) {
									var result = Ext.decode(response.responseText);
									if (result.vo.deliverStatus) {
										//如果派送单状态已经取消，则提示终止
										if (result.vo.deliverStatus == 'CANCELED') {
											Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '当前派送单已取消不能继续操作，请关闭当前页面后再次进入操作', 'error', 3000);
											return;
										}
									}

									var wabyInfoArr = new Array(); //定义待排单运单号数组
									var gisArray = new Array(); //定义GIS返回的运单号数组
									gisArray = myMap.chooseInwaybill();
									for(var i = 0; i< gisArray.length; i++) {
										wabyInfoArr.push(gisArray[i]);
									}
									if (wabyInfoArr.length == 0) {
										return;
									}
									if (wabyInfoArr.length > 1) {
										Ext.Msg.alert('提示', '修改排单件数只能操作单个运单!');
										var waybillstr = jsArrToJSON(wabyInfoArr);
										myMap.resetwaybillMarkerFromFoss(waybillstr);
										return;
									}
									//根据运单号查询待排运单信息
									Ext.Ajax.request({
											url : predeliver.realPath('visibleQueryWaybillToArrange.action'),
											async : false,
											jsonData:{
												'vo': {
													"waybillToArrangeDto": {
														'waybillNo': wabyInfoArr.join(',')
													}
												}
											},
											success : function(response) {
												var json = Ext.decode(response.responseText);
												if (json.vo.waybillToArrangeDtoList) {
															//重新初始化变量值
															predeliver.visibleOrderBill.modfiyToArrange = new Ext.util.MixedCollection();
															var resultArrangeList = json.vo.waybillToArrangeDtoList;

															if (resultArrangeList) {
															} else {
																//Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillcantoperate'), 'error', 3000);
																return;
															}

															predeliver.visibleOrderBill.modfiyToArrange = resultArrangeList;
															//返回结果不为空

															var localWayBillInfo = predeliver.visibleOrderBill.modfiyToArrange[0];
															var modWin = Ext.create('Foss.predeliver.visibleOrderBill.modifiyArrangeWin');
															modWin.down('textfield[name=wayBillNo]').setValue(localWayBillInfo.waybillNo);
															modWin.down('textfield[name=waybillGoodsQty]').setValue(localWayBillInfo.waybillGoodsQty);
															modWin.down('textfield[name=arrangableGoodsQty]').setValue(localWayBillInfo.arrangableGoodsQty);
															modWin.down('textfield[name=billQty]').setValue(localWayBillInfo.billQty);
															modWin.show();
												} else {
														Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillcantoperate'), 'error', 3000);
														var waybillstr = jsArrToJSON(wabyInfoArr);
														myMap.resetwaybillMarkerFromFoss(waybillstr);
												}
											}
								});
							}
					});
			}
		},{
			xtype:'button',
			columnWidth: .08,
			id: 'predeliver_visibleOrderBill_wayBillReturn_ID',
			name: 'btnReturn',
			height: 25,
			text: '运单退回',//暂时写死，留待i18n处理
			disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/waybillReturnButton'),
			hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/waybillReturnButton'),
			cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon'
		}
	],
	items : [{
				columnWidth :1,
				name: 'gisMap',
				id : 'predeliver_VisibleOrderBill_GIS',
				xtype: 'container',
				margin : '0 5 0 0',
				width: 1400,
				height: 630,
				listeners: {
					afterrender: function(gisMap) {
						//延迟执行
						var dTask = new Ext.util.DelayedTask(function(){
								 var provCity = FossUserContext.getCurrentDept().provName + FossUserContext.getCurrentDept().cityName;
								 var dmapp = new DPMap.MyMap('VIEW', 'predeliver_VisibleOrderBill_GIS',{center : provCity ,zoom:"TOWN"},function(map) {

										var myMapp = DMap.CoverFeature(map,{isAddable:false});

										var loadMap = myMapp.renderingCoordinatePoint(jsonInfoGIS);

										//3.实现画区域框清除
										document.getElementById('predeliver_visibleOrderBill_ClearSelectMap_ID').addEventListener('click',function(){
											//返回运单号信息给foss系统
											myMapp.clearAreaframe();
										});

								});
						});
						dTask.delay(300);
					}
				}
			},{
					columnWidth :1,
					width: 700,
					height: 20,
					id: 'totalinfo',
					xtype: 'container'
				},{
					columnWidth :1,
					width: 700,
					height: 20,
					id: 'checkinfo',
					xtype: 'container'
				}]
});


//特殊地址标记
Ext.define('Foss.predeliver.visibleOrderBill.specailAddressMark',{
	extend : 'Ext.window.Window',
	title : '特殊送货地址标记',
	modal:true,
	items : [ {
		xtype : 'form',
		items : [ {
			xtype : 'combobox',
			name : 'addressType',
			fieldLabel : '地址类型',
			queryModel : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			allowBlank: false,
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('PKP_SPECIAL_DELIVERYADDRESS_TYPE')
		}, {
			fieldLabel : '车牌号',
			xtype : 'commontruckselector',
			name : 'vehicleNo',
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
			emptyText : '车牌号+归属部门'
		}, {
			xtype : 'hidden',
			name : 'deliveryResidenceCode'
		}, {
			xtype : 'hidden',
			name : 'deliveryResidenceName'
		}, {
			xtype : 'hidden',
			name : 'deliveryAddress'
		}, {
			xtype : 'hidden',
			name : 'id'
		}, {
			xtype : 'hidden',
			name : 'waybillNo'
		} ]
	} ],
	buttons : [ {
		text : '保存',
		handler : function (btn) {
			var inner = btn.up('window');
			var form = inner.down('form');
			if (!form.getForm().isValid()) {
				return ;
			}
			var obj = form.getForm().getValues();
			var commontruckselector = form.down('commontruckselector');
			if (commontruckselector.valueModels[0]) {
				obj['vehicleDeptName'] = commontruckselector.valueModels[0].data.orgName;
				obj['vehicleDeptCode'] = commontruckselector.valueModels[0].data.orgId;
			}
			for (var attr in obj){
				var keyName = 'specialDeliveryAddressEntity.' + attr;
				obj[keyName] = obj[attr];
				delete obj[attr];
			} 
			obj['waybillNo'] = obj['specialDeliveryAddressEntity.waybillNo'];
			delete obj['specialDeliveryAddressEntity.waybillNo'];
			Ext.Ajax.request({
				url: predeliver.realPath('updateSpecialAddressType.action'),
				params : obj,
				success: function(response, opts) {
					var obj = Ext.decode(response.responseText);
					if (obj) {
						if(obj.success) {
							Ext.ux.Toast.msg('提示', obj.message, 'success', 2500);
							inner.close();
						} else {
							Ext.ux.Toast.msg('提示', obj.message, 'error', 2500);
						}
					}
				},
				exception: function(response){
					var obj = Ext.decode(response.responseText);
					Ext.ux.Toast.msg('提示', obj.message, 'error', 2500);
				}
			 });
		}
	}, {
		text : '取消',
		handler : function (btn) {
			if (specialAdressFlag) {
				 var waybillArr = new Array();
				 waybillArr.push(btn.up('window').down('hidden[name=waybillNo]').getValue());
				 var waybillstr = jsArrToJSON(waybillArr);
				 myMap.resetwaybillMarkerFromFoss(waybillstr);
				 specialAdressFlag = false;
			}
			btn.up('window').close();
		}
	} ]
});

var longitude,latitude;
//地址坐标标记
Ext.define('Foss.predeliver.visibleOrderBill.addressCoordinateMark',{
	extend : 'Ext.window.Window',
	title : '实际送货地址标记坐标',
	id: 'addressCoordinateMark_ID',
	width : window.screen.availWidth * 0.9,
	height : window.screen.availHeight * 0.8,
	layout : 'border',
	draggable : false,
	resizable : false,
	modal:true,
	defaults : {
		margin : '1 1 1 1'
	},
	items : [
	         {//待标列表
						xtype : 'grid',
						frame : true,
						store : Ext.create('Foss.predeliver.store.coordinateMarkStore'),
						title : '待标列表',
						region:'west',
						width : 300,
						autoScroll : true,
						columnLines : true,
						columns : [
									{
										xtype : 'templatecolumn',
										text : '操作',
										width : 33,
										tpl : '<input type="radio" name="select" id="{id}" />'
									},
									{
										dataIndex : 'waybillNo',
										align : 'center',
										width : 80,
										text : '运单号'
									},
									{
										dataIndex : 'actualAddress',
										align : 'center',
										width : 120,
										xtype : 'ellipsiscolumn',
										text : '实际送货地址'
									},
									{
										dataIndex : 'actualSmallzoneName',
										align : 'center',
										width : 80,
										xtype : 'ellipsiscolumn',
										text : '建议送货小区'
									}
						],
						constructor: function(config){
							var me = this,
							cfg = Ext.apply({}, config);
							me.callParent([cfg]);
						},
						listeners: {
							'select' : function (rowModel,record,index) {
									Ext.getDom(record.data.id).checked = 'checked';
									Ext.getCmp('ActualDeliveryAddress_ID').setValue(record.data.actualAddress);
									var longitude = '',latitude = '';
									if (!Ext.isEmpty(record.data.longitude) && !Ext.isEmpty(record.data.latitude)) {
										longitude = record.data.longitude;
										latitude = record.data.latitude;
										Ext.getCmp('ActualDeliveryCoordinate_ID').setValue(record.data.longitude+','+record.data.latitude);
										var pointGrid = {"lng":longitude,"lat":latitude};
										var addressGIS = '';//record.data.juheAdress;//'江苏省徐州市邳州市运河镇';//'江苏省徐州市邳州市运河镇';
										if (!Ext.isEmpty(longitude) && !Ext.isEmpty(latitude)) {
											showMarkY(pointGrid,addressGIS);//描点
										}
									} else {//坐标为空，取四级地址
											Ext.getCmp('ActualDeliveryCoordinate_ID').setValue('');
											pointGrid = '';
											var addressGIS = record.data.juheAdress;
											showMarkY(pointGrid,addressGIS);//描点
									}

							}
						}
					},{
						xtype : 'container',
						region:'center',
						layout:'vbox', 
						defaults : {
							margin : '1 1 1 1'
						},
						items : [{//录入框区域
							title : '录入框',
							xtype: "form",
							autoScroll : true,
							width : window.screen.availWidth * 0.9 - 320,
							height : 135,
							frame : true,
							layout : {
								 type: 'table',
							     columns: 2
							},
							defaults : {
								margin : '5 5 5 5',
								height: 25
							},
							items: [{
								xtype : 'textfield',
								fieldLabel : '实际送货地址',
								labelWidth: 100,
								id: 'ActualDeliveryAddress_ID',
								width : 700,
								colspan: 2,
								readOnly:true,
								readOnlyCls: '.x-form-field .x-form-text'
							},{
								xtype : 'textfield',
								fieldLabel : '实际地址坐标',
								labelWidth: 100,
								id: 'ActualDeliveryCoordinate_ID',
								width : 400,
								readOnly:true,
								readOnlyCls: '.x-form-field .x-form-text'
							
							},{
								xtype : 'button',
								name: 'btnSaveForm',
								width : 100,
								text : '保存',
								handler : function(btnSaveForm) {

									//待标列表选中记录
									var selRow = btnSaveForm.up('window').down('grid').getSelectionModel().getSelection();
									//修改后的运单坐标值
									var xyValues = Ext.getCmp('ActualDeliveryCoordinate_ID').getValue().split(',');
									if (!Ext.isEmpty(xyValues) && selRow.length > 0) {
											var obj = selRow[0].data;
											obj.longitude = xyValues[0];//把修改后的经度赋给对象
											obj.latitude = xyValues[1];//把修改后的纬度赋给对象
											//根据选择的运单和修改后的坐标，查询修改后坐标对应的小区
											Ext.Ajax.request({
													url : predeliver.realPath('visibleFindSmallByCrood.action'),
													async : false,
													jsonData:{
														'vo': {
															'markDto': obj
														}
													},
													success : function(response) {
														var json = Ext.decode(response.responseText);
														if (json.vo.actualSmallzoneName) {
															//送货地址为司机采集库地址且建议小区与修改坐标后返回的小区不一致
															if (obj.matchType.trim() == 'localAddress' && obj.actualSmallzoneName != json.vo.actualSmallzoneName) {
																Ext.Msg.confirm('提示', '实际送货小区与建议小区不一致，是否要司机重新采集?', function(btn){
																	if (btn == 'yes') {
																		//选择是，则作废采集库坐标，【让司机重新采集】
																		Ext.Ajax.request({
																				url : predeliver.realPath('visibleDeleteCrood.action'),
																				async : false,
																				jsonData:{
																					'vo': {
																						'markDto': selRow[0].data
																					}
																				},
																				success: function(response, opts) {
																					 var returnResult = Ext.decode(response.responseText);
																					 //作废成功后、保存修改后的坐标、小区到交单表
																					 if(returnResult.success) {
																						  Ext.ux.Toast.msg('提示', '作废采集库坐标成功!', 'success', 2500);
																							//取获取的新坐标对应的小区代码和名称
																							obj.actualSmallzoneName = json.vo.actualSmallzoneName;
																							obj.actualSmallzoneCode = json.vo.actualSmallzoneCode;
																							//保存修改后的坐标和小区代码、名称
																							saveHandoverXY(obj);
																					 } else {
																							Ext.ux.Toast.msg('提示', obj.message, 'error', 2500);
																					 }																												 
																				},
																				exception : function(response) {
																					var result = Ext.decode(response.responseText);
																					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
																					//作废失败，保存修改后的坐标
																					saveHandoverXY(obj);
																				}
																		});
																	}
																});

															} else {
																	//取获取的新坐标对应的小区代码和名称
																	obj.actualSmallzoneName = json.vo.actualSmallzoneName;
																	obj.actualSmallzoneCode = json.vo.actualSmallzoneCode;
																	//返回的小区名称和建议小区名称相同时，保存修改后的坐标
																	saveHandoverXY(obj);
															}
														} else {
															//返回小的小区名为空的时候，保存修改后的坐标
															saveHandoverXY(obj);
														}
													},
													exception : function(response) {
														var result = Ext.decode(response.responseText);
														Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
														//查找小区异常后，保存修改后的坐标
														saveHandoverXY(obj);
												  }
										});
									} else {
										Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '请选择待标运单！', 'error', 3000);
									}
							
								}
							}
							]
				},
						{//地图区域
							id : 'editDeliverbillNew_GIS_P',
							xtype: 'container',
							width : window.screen.availWidth * 0.9 - 320,
							height : window.screen.availHeight * 0.57,
							listeners: {
								afterrender: function(field) {
											var provCity = FossUserContext.getCurrentDept().provName + FossUserContext.getCurrentDept().cityName;
											dmapMark =  new DpMap(field.getId(), {center: provCity ,zoom: 13}, function(map) {
											var callFun = function(data) {
												if (data.flag == 'return') {
													Ext.getCmp('ActualDeliveryCoordinate_ID').setValue(data.point.lng+', '+data.point.lat);
												}
											}

											var point= {"lng":"121.091371","lat":"31.130676"};
											var  ployfeature = new  DpMap.service.DpMap_polygon(map,field.getId(), {foregroundType:'DELIVERY_REGIONS',  backgroundType:'CITY' });

											pmarker =new DpMap.service.DpMap_marker(map,field.getId(),{isAddable:false, locateToMarker:true,returnCallback:callFun});

	                    showMarkY = function (point, addressGIS) {
												pmarker.showModifyMarker(point, addressGIS, map,function(data) {
												  if (data.flag == 'return') {
														Ext.getCmp('ActualDeliveryCoordinate_ID').setValue(data.point.lng+', '+data.point.lat);
													}
												});
											}

										/*	setTimeout(function (){
												pmarker.showModifyMarker(point,map,function(data) {
													 if (data.flag == 'return') {
														//Ext.getCmp('ActualDeliveryCoordinate').setDisabled(false);//让实际送货地址坐标框呈可用状态
														//Ext.getCmp('ActualDeliveryCoordinate').setValue(data.point.lng+","+data.point.lat);//改变实际送货地址坐标框值
														//Ext.getCmp('ActualDeliveryReset').setDisabled(false);//让重置按钮呈可用状态
														//Ext.getCmp('ActualDeliveryCommit').setDisabled(false);//让保存按钮呈可用状态
													}
												});
											},500); */
										});
								}
							}
				  }]
					}
			 ]
});

//坐标标记.保存修改后的坐标
function saveHandoverXY(obj) {
	//保存修改后的坐标和小区代码、名称
	Ext.Ajax.request({
			url : predeliver.realPath('visibleSaveCrood.action'),
			async : false,
			jsonData:{
				'vo': {
					'markDto': obj
				}
			},
			success: function(response, opts) {
				 var returnResult = Ext.decode(response.responseText);
				 if(returnResult.success) {
						Ext.ux.Toast.msg('提示', '修改后的信息保存成功!', 'success', 2500);
				 } else {
						Ext.ux.Toast.msg('提示', obj.message, 'error', 2500);
				 }
			},
			exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
	});
}


var flag = true;
//区分运单是从地图or已排单进入特殊地址标记，从地图设置为true
var specialAdressFlag = false;
//Panel下中间操作区域-开始
Ext.define('Foss.predeliver.visibleOrderBill.ButtonPanelRole',{
	extend :'Ext.form.Panel',
	id: 'visibleOrderBill_ButtonPanelRole_ID',
	buttonAlign : 'center',
	layout : 'column',
	flex: 0,
	width: 100,
	defaults: {
		height:25
	},
	//columnWidth : .1,
	items : [{
			xtype : 'label',
			style : 'padding-top:30px;text-align:center',
			columnWidth: 1,
			margin: '-10 5 5 5',
			text : '派送单号：'
		},{
			xtype : 'label',
			style : 'padding-top:5px;text-align:center;color:red',
			name: 'deliverbillNo',
			id: 'predeliver_visibleOrderBill_deliverbillNo_ID',
			columnWidth: 1,
			margin: '5 5 5 5',
			text : 'P'+predeliver.sequence //'P2015030105'
		},{
			xtype : 'label',
			columnWidth: 1,
			margin: '-15 5 5 5',
			style : 'padding-top:10px;text-align:center',
			text : '车辆组别：'
		},{
			name : 'vehicleType',
			fieldLabel : '', // 车辆组别
			columnWidth : 1,
			margin: '-3 5 5 5',
			xtype : 'commonmotorcadeselector',
			loopOrgCodes : predeliver.fleetCode,
			listeners : {
				'change' : function(field, event, eOpts) {
					var form = field.up('form').getForm(),
						vehicleNo = form.findField('vehicleNo');
					if (field.value != '' && field.value != null) {
						//vehicleNo.setReadOnly(false);
						vehicleNo.getStore().un('beforeload');
						vehicleNo.getStore().on('beforeload', function(store, operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							searchParams['truckVo.truck.loopOrgCode'] = field.value;
						});
						vehicleNo.getStore().load();
						vehicleNo.expand();
					}
					else {
							vehicleNo.setValue('');
							vehicleNo.getStore().un('beforeload');
							vehicleNo.getStore().on('beforeload', function(store, operation,eOpts) {
								var searchParams = operation.params;
								if (Ext.isEmpty(searchParams)) {
									searchParams = {};
									Ext.apply(operation, {
										params : searchParams
									});
								}
								searchParams['truckVo.truck.loopOrgCode'] = field.value;
							});
							vehicleNo.getStore().load();
							vehicleNo.expand();
					}
				}
			}
		},{
			xtype: 'displayfield',
			style : 'text-align:center',
			fieldLabel: '送货车辆',
			margin: '-5 5 0 5',
			columnWidth : 1
		},{
			name : 'vehicleNo',
			fieldLabel : '', // 车辆
			columnWidth : 1,
			margin: '-3 5 5 5',
			xtype : 'commontruckselector',
			allowBlank : false,
			queryAllFlag : true,
			//loopOrgCode : predeliver.fleetCode,
			listeners : {
				'select': function(field, records, eOpts) {
						var record = records[0];
						predeliver.visibleOrderBill.vehicleNoTruckType = record.get('truckType');
				},
				'blur' : function(field, event, eOpts) {
						if (!Ext.isEmpty(field.value)) {
							Ext.getCmp('T_predeliver-visibleOrderBillIndex_content').getDriverName(field.value);
						} else {
							field.up('form').down('combobox[name=frequencyNo]').setValue('');
							field.up('form').down('textfield[name=expectedBringVolume]').setValue('');
							Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '请选择送货车辆!', 'error', 2500);
							return;
						} 
						//根据车牌号、送货日期带出排班信息
						
						var deliverTime = null;
						//如果有已排，则取已排的日期; 否则取查询条件的日期
						if(predeliver.visibleOrderBill.deliverbillDetailGrid.store.getCount() > 0) {
							deliverTime = predeliver.visibleOrderBill.deliverbillDetailGrid.store.getAt(0).data.deliverDate;
						} else {
							deliverTime = predeliver.visibleOrderBill.orderBasicInfoForm.getForm().findField('deliverDate').getValue();
						}
						if (predeliver.visibleOrderBill.vehicleNoTruckType == '外请车') {
								//外请车不查询排班表
              
								//如果是外请车，则设置出车任务"送+接"，预计出车时间为"00:00"。反之，如果已天出车任务、预计出车时间再选择车辆未外请车不考虑
								field.up('form').getForm().findField('carTaskinfo').setValue('1');
								field.up('form').getForm().findField('preCartaskTime').setValue('00:00');
						} else {
							Ext.Ajax.request({
								url : predeliver.realPath('visibleVehicleSchedue.action'),
								async : false,
								jsonData:{
									'vo' : {
										'vehilceNo': field.value,
										'deliverDate': Ext.util.Format.date(deliverTime, 'Y-m-d')
									}
								},
								success : function(response) {
										var result = Ext.decode(response.responseText);
										if (result.message) {
											field.up('form').down('commontruckselector[name=vehicleNo]').setValue(null);
											field.up('form').getForm().findField('driverCode').setValue(null);
											field.up('form').down('combobox[name=frequencyNo]').setValue('');
											field.up('form').down('textfield[name=expectedBringVolume]').setValue('');
											Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
										} else {
											//提示
											if(result.vo.vehilceNoTips) {
												Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.vo.vehilceNoTips, 'error', 3000);
											}
											//车辆班次
											if (result.vo.frequencyNo) {
												field.up('form').down('combobox[name=frequencyNo]').setValue(result.vo.frequencyNo);
											}
											//车辆带货(方)
											if (result.vo.expectedBringVolume) {
												field.up('form').down('textfield[name=expectedBringVolume]').setValue(result.vo.expectedBringVolume);
											}
											//通过车牌号带出-出车任务
											if (result.vo.carTaskinfo) {
												 field.up('form').down('combobox[name=carTaskinfo]').setValue(result.vo.carTaskinfo);
											}
											//通过车牌号带出-预计出车时间
											if (result.vo.preCartaskTime) {
												 field.up('form').down('timefield[name=preCartaskTime]').setValue(result.vo.preCartaskTime);
											}
											//通过车牌号带出-带货部门
											if (result.vo.takeGoodsDeptcode) {
												 field.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setValue(result.vo.takeGoodsDeptcode);
												 field.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setRawValue(result.vo.takeGoodsDeptname);
											}
											//通过车牌号带出-带货部门
											if (result.vo.transferDeptcode) {
												 field.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').setValue(result.vo.transferDeptcode);
												 field.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').setRawValue(result.vo.transferDeptname);
											}
										}
								},
								exception : function(response) {}
						});
					}
				}
			}
		},{
			xtype : 'label',
			columnWidth: 1,
			margin: '-10 5 5 5',
			style : 'padding-top:10px;text-align:center',
			text : '送货司机：'
		},{
			name : 'driverName',
			fieldLabel : '', // 司机姓名
			columnWidth : 1,
			margin: '-3 5 5 5',
			xtype : 'commondriverselector',
			waybillFlag:'N',//是否是集中接送区域  如果设置值为Y,N
			fleetType:'FLEET_TYPE__SHUTTLE,FLEET_TYPE__SHUTTLE_GOODS,FLEET_TYPE__LONG_DISTANCE',//车队组类型(包含集中接送货和物流班车)
			listeners : {
				blur : function(field, event, eOpts) {
					// 公共选择器的value为driverCode,rawValue为driverName
					// 更新driverCode
					field.up('form').getForm().findField('driverCode').setValue(field.value);
				}
			}
		},{
			name : 'driverCode',
			xtype: 'textfield',
			hidden : true
		},{
			xtype : 'label',
			columnWidth: 1,
			margin: '-10 5 5 5',
			style : 'padding-top:10px;text-align:center',
			text : '派车类型：'
		},{
			xtype : 'combobox',
			name  : 'deliverType',
			width : 80,
			margin: '-5 5 5 5',
			columnWidth : 1,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : 'NOMAL',
			store : Ext.create('Foss.predeliver.store.DeliverTypeStore')
		},
		{//添加至派送单、移除派送单
			border : false,
			xtype : 'container',
			columnWidth : 1,
			height: 70,
			margin: '-28 -10 5 0',
			layout : 'column',
			items : [{
				xtype:'label',
				text:' ',
				margin: '0 0 2 0',
				columnWidth: .08
			},
			{//右移动按钮-放大地图
				style : 'margin-top:36px;cursor:pointer',
				id: 'predeliver_LeftImg_ID',
				xtype : 'button',
				maxWidth : 16,
				cls : 'flexright',
				columnWidth : .15,
				listeners:{
					click: {
						element: 'el',
						fn: function(){
							if (!leftFlag && rightFlag) {
								Ext.getCmp("visibleOrderBill_WaybillToArrangeMap_ID").show();
								var store = Ext.create('Foss.predeliver.store.ViSibleOrderConBillStore');
								store.load();
								Ext.getCmp('visibleOrderBill_deliverbillDetailGrid_ID').doComponentLayout();//刷新布局
								Ext.getCmp("visibleOrderBill_deliverbillDetailGrid_ID").show();
								leftFlag = false;
								rightFlag = false;
								return;
							}
							if (!leftFlag) {
								Ext.getCmp("visibleOrderBill_deliverbillDetailGrid_ID").hide();
								var store = Ext.create('Foss.predeliver.store.ViSibleOrderConBillStore');
								store.load();
								leftFlag = true;
							}
						}
					}
				}
			},
			{//添加至派送单
				xtype : 'button',
				style : 'margin-top:30px;',
				id: 'predeliver_visibleOrderBill_AddArrange_ID',
				iconCls:'deppon_icons_turnright',
				tooltip: '添加至派送单',
				height:23,
				columnWidth : .6,
				handler: function(button) {

					//运单退回前检查排单状态是否已取消
					Ext.Ajax.request({
							url : predeliver.realPath('queryDeliverbillInfoById.action'),
							params : {
								'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
							},
							success : function(response) {
									var result = Ext.decode(response.responseText);
									if (result.vo.deliverStatus) {
										//如果派送单状态已经取消，则提示终止
										if (result.vo.deliverStatus == 'CANCELED') {
											Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '当前派送单已取消不能继续操作，请关闭当前页面后再次进入操作', 'error', 3000);
											return;
										}
									}

									predeliver.visibleOrderBill.waybillsToArrange.clear();
									var wabyInfoArr = new Array(); //定义待排单运单号数组
									var gisArray = new Array(); //定义GIS返回的运单号数组
									gisArray = myMap.chooseInwaybill();
									if (gisArray.length < 1) {
										return;
									}
									for(var i = 0; i< gisArray.length; i++) {
										wabyInfoArr.push(gisArray[i]);
									}
									console.info(wabyInfoArr);

									// 排单前，若还未保存派送单，则需先保存派送单
									if (predeliver.visibleOrderBill.deliverbillId == "") {
											//验证表单是否有效
											if (!predeliver.visibleOrderBill.SubmitForm.validateOnArrange()) {
												var waybillstr = jsArrToJSON(wabyInfoArr);
												myMap.resetwaybillMarkerFromFoss(waybillstr);
												return;
											}
											//验证车辆信息
											if (!predeliver.visibleOrderBill.SubmitForm.validateOnDriver()) {
												var waybillstr = jsArrToJSON(wabyInfoArr);
												myMap.resetwaybillMarkerFromFoss(waybillstr);
												return;
											}
									}

									//通过GIS返回的运单号到后台查询运单相关数据
									//var wabyInfoArr = new Array("A100001", "A100002", "A100003");
									var wayBillInfoArr;//定义根据运单号查询待排运单信息集合
									//根据运单号查询待排运单信息
									Ext.Ajax.request({
											url : predeliver.realPath('visibleQueryWaybillToArrange.action'),
											async : false,
											jsonData:{
												'vo': {
													"waybillToArrangeDto": {
														'waybillNo': wabyInfoArr.join(',')
													}
												}
											},
											success : function(response) {
												var json = Ext.decode(response.responseText);
												wayBillInfoArr = json.vo.waybillToArrangeDtoList;
											}
									});

									var flg = false;
									//查询待排运单信息集合添加到待排运单列表
									Ext.Array.each(wayBillInfoArr, function(item, index, wayBillInfoArr) {
											predeliver.visibleOrderBill.waybillsToArrange.add(item.id, item);
											if(item.isSentRequired == 'Y') {
												flg = true;
											}
									});

									var waybills = ''; //提示没有通知信息的运单
									var selectWaybillNos =new Array();//存放校验后的可排单运单数组
									var selectWaybillNosWVH =new Array();//存放复选框选中的运输性质为整车的运单
									predeliver.visibleOrderBill.waybillsToArrange.each(function(item, index,length) {
											selectWaybillNos.push(item.waybillNo);//得到校验后的可排单运单数组
											if (item.isAlreadyContact == 'N') {
													waybills += item.waybillNo + '<br/>';
											}
											if (item.transportType == 'WVH') {//如果运输性质为整车
													selectWaybillNosWVH.push(item.waybillNo);//得到运输性质为整车的运单数组
											}
									});

									//没有根据运单号没有查询到运单信息
									if (selectWaybillNos.length < 1) {
										Ext.Msg.alert('提示', '没有运单信息');
										return;
									}


									if (flg == true) {//必送货未排单，是否确认
										var confirmMsg = Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
											 if (btn == 'yes') {
													if (flag == false) {
														flag = true;
														return;
													}
													if(selectWaybillNosWVH.length>0){
														var checkResult=predeliver.visibleOrderBill.checkWayBillNosWVH(selectWaybillNosWVH);
														if(!checkResult){
															return;
														}
													}
													if (waybills == '') {
														predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
													} else {
														var confirmMsgBox = Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
															if (btn == 'yes') {
																predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
															}
														},this);
													}
											}
										});
									} else {
											if (flag == false) {
												flag = true;
												return;
											}
											if(selectWaybillNosWVH.length>0){
												var checkResult=predeliver.visibleOrderBill.checkWayBillNosWVH(selectWaybillNosWVH);
												if(!checkResult){
													return;
												}
											}
											if (waybills == '') {
												predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
											} else {
												var confirmMsgBox = Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
													if (btn == 'yes') {
														predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs(selectWaybillNos);
													}
												},this);

											}
										}

							}
					});
				}
			},
			{//移除派送单
				xtype : 'button',
				iconCls:'deppon_icons_turnleft',
				tooltip: '移除出派送单',
				id: 'predeliver_visibleOrderBill_moveOut_ID',
				height:23,
				columnWidth : .6,
				handler: function() {
					/*	var selectRow = predeliver.visibleOrderBill.deliverbillDetailGrid.getSelectionModel().getSelection();
						if (selectRow.length == 0) {
							Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
									predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
							return;
						}
						Ext.Msg.confirm(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),  predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveDetail'),
							function(btn, text) {
								if (btn == "yes") {
									var ids = '';
									for ( var i = 0; i < selectRow.length; i++) {
										ids = selectRow[i].data.id+ ","+ ids;
									}
									predeliver.visibleOrderBill.deliverbillDetailGrid.deleteDeliverbillDetails(ids);
								}
							}
						); */
				}
			},
			{//左移动按钮-放大已派单表格  
				xtype : 'button',
				style : 'margin-top:-20px;cursor:pointer;',
				maxWidth : 15,
				id: 'predeliver_RightImg_ID',
				cls : 'flexleft',
				columnWidth : .1,
				listeners:{
					click: {
						element: 'el',
						fn: function(){
							if (!rightFlag && leftFlag) {
								Ext.getCmp("visibleOrderBill_deliverbillDetailGrid_ID").show();
								Ext.getCmp('visibleOrderBill_deliverbillDetailGrid_ID').doComponentLayout();//刷新布局
								Ext.getCmp("visibleOrderBill_WaybillToArrangeMap_ID").show();
								leftFlag = false;
								rightFlag = false;
								var store = Ext.create('Foss.predeliver.store.ViSibleOrderConBillStore');
								store.load();
								return;
							}
							if (!rightFlag) {
								Ext.getCmp("visibleOrderBill_WaybillToArrangeMap_ID").hide();
								rightFlag = true;
							}
						}
					}
				}
			}]
		},
    {//输入单号右移
			xtype : 'textfield',
			name:'moveEnter',
			margin: '10 0 5 0',
			id: 'predeliver_visibleOrderBill_bywaybillNumber_ID',
			style : 'margin-top:26px',
			emptyText : '输入单号右移',
			columnWidth : 1,
			listeners: {
				'change': function(field,new_v,old_v){
					if(!Ext.isEmpty(new_v)&& new_v.length >=9){
						//先去两端的空格
						var new_value = new_v.trim();
						//再把值设置给文本框
						field.setValue(new_value.substring(0,new_value.length>=9?9:new_value.length));
					}
				},
				'specialkey': function(field, e) {
						if (e.getKey() == e.ENTER) {
							var form = this.up('form').getForm();
							if(predeliver.visibleOrderBill.deliverbillId == "") {
								if(!form.isValid()) {
									Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '有必填项（页面标识红叉项）未填写，请填写完整！', 'error', 3000);
									return;
								}
							}
						
							var deliverbillBasicInfo = predeliver.visibleOrderBill.ButtonPanelRole.getValues();
							var rightGridStore = predeliver.visibleOrderBill.deliverbillDetailGrid.getStore();
							var deliverDate = null;
							//如果已排单里有数据且预计送货日期不为空 239284
							if(rightGridStore.getCount()>0){
								for (var i = 0; i < rightGridStore.getCount(); i++) {
									if(rightGridStore.data.items[i].data.deliverDate!=null){
										deliverDate=rightGridStore.data.items[i].data.deliverDate;
									}
								}
							}
							
							var newVo = {
														'vo': {
																'deliverbill' : {
																	'id' : predeliver.visibleOrderBill.deliverbillId,
																	'deliverbillNo' : Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text,
																	'vehicleNo' : deliverbillBasicInfo.vehicleNo,
																	'delStatus' :"",
																	'driverCode' : deliverbillBasicInfo.driverCode,
																	'deliverType' : deliverbillBasicInfo.deliverType,
																	'driverName' : predeliver.visibleOrderBill.ButtonPanelRole.getForm().findField("driverName").rawValue
																},
																'wayBillNo':field.value,
																'rightDeliverDate':deliverDate
															}
													}
							Ext.Ajax.request({
									url:predeliver.realPath('visibleAddRightToArrangeByWaybillNo.action'),
									jsonData: newVo,
									success: function(response){
										var result = Ext.decode(response.responseText);
										predeliver.visibleOrderBill.deliverbillId = result.vo.deliverbill.id;
										predeliver.visibleOrderBill.deliverbillDetailGrid.getStore().load();
										Ext.getCmp('predeliver_visibleOrderBill_bywaybillNumber_ID').setValue('');
									},
									exception: function(response) {
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg('提示', json.message, 'error', 3000);
									}
							});
						}
				}
			}
		},
		{//出车任务
			xtype : 'label',
			columnWidth: 1,
			style : 'padding-top:5px;text-align:center',
			html: '<font style="color:red;">*</font>出车任务：'
		},{
			xtype : 'combobox',
			columnWidth: 1,
			name : 'carTaskinfo',
			style : 'padding-top:-5px;text-align:center;color:red',
			margin:'0 0 0 0',
			displayField:'valueName',
			valueField:'valueCode',
			store:FossDataDictionary.getDataDictionaryStore("DISPATCH_VEHICLE_TASK"),
			value : '',
			allowBlank: false,
      editable:false,
			listeners: {
				'change': function(src, newValue, oldValue, eOpts) {
					var value = (newValue && true == newValue);
					//选择“带+送+转”或者“送+转”
					if (newValue == 4 || newValue == 2) {
						this.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').setReadOnly(false);
					} else {
						//非"带+送+转"和"送+转",则转货部门字段，不允许录入	
						this.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').setValue('');
						this.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;}';
						this.up('form').down('commonsaledepartmentselector[name=transferDeptcode]').setReadOnly(true);
					} 
					
				  if (newValue==3||newValue==4||newValue==6){ 
						//若选择“带+送+接”/“带+送+转”/“带+二次派送”这三种类型，则带货部门、带货（F）才允许录入且为必填,其他类型,不允许填写
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setReadOnly(false);
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').allowBlank = false;
						this.up('form').down('textfield[name=expectedBringVolume]').setReadOnly(false);
						this.up('form').down('textfield[name=expectedBringVolume]').allowBlank = false;
						if(this.up('form').down('textfield[name=expectedBringVolume]').wasValid != undefined&&!this.up('form').down('textfield[name=expectedBringVolume]').wasValid){
							this.up('form').down('textfield[name=expectedBringVolume]').inputEl.addCls('x-form-invalid-field');
						}
						if(this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').wasValid != undefined&&!this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').wasValid){
							this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').inputEl.addCls('x-form-invalid-field');
						}
					} else {
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').inputEl.removeCls('x-form-invalid-field');
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').allowBlank = true;
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').doComponentLayout();
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;}';
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setReadOnly(true);
						this.up('form').down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setValue('');
						
						this.up('form').down('textfield[name=expectedBringVolume]').inputEl.removeCls('x-form-invalid-field');
						this.up('form').down('textfield[name=expectedBringVolume]').allowBlank = true;
						this.up('form').down('textfield[name=expectedBringVolume]').doComponentLayout();
						this.up('form').down('textfield[name=expectedBringVolume]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;}';
						this.up('form').down('textfield[name=expectedBringVolume]').setReadOnly(true);
						this.up('form').down('textfield[name=expectedBringVolume]').setValue('');

						
					}
				}
			}
		},
		{//班次
			xtype : 'label',
			columnWidth: .40,
			style : 'padding-top:7px;text-align:right',
			text : '班次：'
		},{
			xtype : 'combobox',
			columnWidth: .60,
			queryMode : 'local',
			style : 'padding-top:10px;',
			displayField : 'freNo',
			valueField : 'freNo',
			editable:false,
			name : 'frequencyNo',
			store: Ext.create('Ext.data.Store',{
				fields:[{name:'freNo', type:'int'}],
				data: [
					{'freNo': 1},
					{'freNo': 2},
					{'freNo': 3}
					]
				})
		},
		{//预计出车时间
			xtype : 'label',
			columnWidth: 1,
			style : 'padding-top:10px;text-align:center',
			//text : '预计出车时间：'
			html: '<font style="color:red;">*</font>预计出车时间：'
		},{
			xtype : 'timefield',
			columnWidth: 1,
			labelWidth : 90,
			width : 180,
			editable:true,
			allowBlank: false,
			name : 'preCartaskTime',
			increment: 5,
			submitFormat : 'H:i',
			format : 'H:i',
			//style : 'padding-top:10px;color:red;font-weight:bold',
			value : ''
		},
		{//带货部门
			xtype : 'label',
			columnWidth: 1,
			style : 'padding-top:10px;text-align:center',
			text : '带货部门：'
		},{
			xtype : 'commonsaledepartmentselector',
			salesDepartment:'Y',
			types : 'CPPX,LD,CPLD',
			columnWidth: 1,
			labelWidth : 90,
			width : 180,
			editable:true,
			name : 'takeGoodsDeptcode',
			value : '',
			listeners: {
				blur:function(txtField,eOpts ){
            if (txtField.getValue() == txtField.getRawValue()) {
              txtField.setValue('');
            } else {
              txtField.setValue(Ext.String.trim(txtField.getValue()));
            }
				},
				change:function(  txtField,  newValue,  oldValue,  eOpts ){
					txtField.setValue(Ext.String.trim(txtField.getValue()));
				}
			}
		},
		{//带货
			xtype : 'label',
			columnWidth: .6,
			style : 'padding-top:7px;text-align:right',
			text : '带货(F)：'
		},{
			xtype : 'textfield',
			columnWidth: .4,
			style : 'padding-top:10px;',
			width: 20,
			height: 23,
      regex: /^\d+(\.\d+)?$/,
      regexText: "只能输入数字",
			name : 'expectedBringVolume',
			//style : 'color:red;font-weight:bold;text-align:left;margin-top:10px;',
			value : ''
		},
		{//转货部门
			xtype : 'label',
			columnWidth: 1,
			style : 'padding-top:10px;text-align:center',
			text : '转货部门：'
		},{
			xtype : 'commonsaledepartmentselector',
			salesDepartment:'Y',
			types : 'CPPX,LD,CPLD',
			columnWidth: 1,
			labelWidth : 90,
			width : 180,
			editable:true,
			name : 'transferDeptcode',
			value : '',
			listeners: {
				blur:function(txtField,eOpts ){
            if (txtField.getValue() == txtField.getRawValue()) {
              txtField.setValue('');
            } else {
              txtField.setValue(Ext.String.trim(txtField.getValue()));
            }
				},
				change:function(  txtField,  newValue,  oldValue,  eOpts ){
					txtField.setValue(Ext.String.trim(txtField.getValue()));
				}
			}
		},
		{//特殊地址标记
			xtype : 'button',
			text : '特殊地址标记',
			style : 'margin-top:10px;',
			columnWidth: 1,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', { seconds: 3 }),
			disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/specialAddressMarkButton'),
			hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/specialAddressMarkButton'),
			handler: function() {

					//1-左边地图区域进入
					var wabyInfoArr = new Array(); //定义待排单运单号数组
					var gisArray = new Array(); //定义GIS返回的运单号数组
					gisArray = myMap.chooseInwaybill();
					for(var i = 0; i< gisArray.length; i++) {
						wabyInfoArr.push(gisArray[i]);
					}
					//2-右边已排单区域进入
					var selectRow = predeliver.visibleOrderBill.deliverbillDetailGrid.getSelectionModel().getSelection();

					//校验操作-地图、已排单都未选择
					if (selectRow.length == 0 && wabyInfoArr.length == 0) {
						Ext.ux.Toast.msg('提示', '特殊地址标记必须选择相应的运单','error', 2500);
						return;
					}
					//校验操作-地图/已排单选择大于单个
					if (selectRow.length > 1 || wabyInfoArr.length > 1) {
						var waybillstr = jsArrToJSON(wabyInfoArr);
						myMap.resetwaybillMarkerFromFoss(waybillstr);
						Ext.ux.Toast.msg('提示', '特殊地址标记只能操作单个运单','error', 2500);
						return;
					}
					//校验操作-地图、已排单不能同时操作
					if (selectRow.length == 1 && wabyInfoArr.length == 1) {
						var waybillstr = jsArrToJSON(wabyInfoArr);
						myMap.resetwaybillMarkerFromFoss(waybillstr);
						Ext.ux.Toast.msg('提示', '地图标记和已排单标记不能同时操作','error', 2500);

						return;
					}

					//2.1-从已排区域进入的直接取地址
					var queryAddress = '';
					if (selectRow.length == 1) {
						queryAddress = selectRow[0].data.consigneeAddress;
						specialAdressFlag = false;
					} else {
						specialAdressFlag = true;
					}

					//根据条件查询特殊地址历史库的特殊地址类型
					Ext.Ajax.request({
							url : predeliver.realPath('visibleSpecialAddress.action'),
							async : false,
							jsonData:{
								'vo': {
									'specialWayBillNO':wabyInfoArr[0],
									'specialAddress': queryAddress
								}
							},
							success : function(response) {
								var obj = Ext.decode(response.responseText);

								var specailMarkWin = Ext.create('Foss.predeliver.visibleOrderBill.specailAddressMark');
								//请求成功
								if (obj.success) {
									//=如果特殊地址库有历史特殊地址类型，则带出对话框界面
									if (obj.specialDeliveryAddressEntity) {
											if (obj.specialDeliveryAddressEntity.addressType) {
												specailMarkWin.down('combobox[name=addressType]').setValue(obj.specialDeliveryAddressEntity.addressType);
											}
											if (obj.specialDeliveryAddressEntity.vehicleNo) {
												specailMarkWin.down('commontruckselector[name=vehicleNo]').setValue(obj.specialDeliveryAddressEntity.vehicleNo);
											}
											if (obj.specialDeliveryAddressEntity.id) {
												specailMarkWin.down('hidden[name=id]').setValue(obj.specialDeliveryAddressEntity.id);
											}
									}									 
									//1.1-如果是通过地图传过来的运单号，则传入到标记对话框
									//相应的地址、小区名称、小区代码同时传入到标记对话框中, 在取消对话框时，运单返回到坐标上
									if (wabyInfoArr.length > 0) {
											specailMarkWin.down('hiddenfield[name=waybillNo]').setValue(wabyInfoArr[0]);
										 if (obj.vo.specialAddress) {
												specailMarkWin.down('hidden[name=deliveryAddress]').setValue(obj.vo.specialAddress);
										 }
										 if (obj.vo.specialSmallZoneCode) {
											 specailMarkWin.down('hidden[name=deliveryResidenceCode]').setValue(obj.vo.specialSmallZoneCode);
										 } 
										 if (obj.vo.specialSmallZoneName) {
											 specailMarkWin.down('hidden[name=deliveryResidenceName]').setValue(obj.vo.specialSmallZoneName);
										 }
									}	else {
											//2.2-从已排单进入的标记对话框
											var seRow = selectRow[0].data;
											specailMarkWin.down('hiddenfield[name=waybillNo]').setValue(seRow.waybillNo);
											if (!Ext.isEmpty(seRow.consigneeAddress)) {
												specailMarkWin.down('hidden[name=deliveryAddress]').setValue(seRow.consigneeAddress);
											} 
											if (!Ext.isEmpty(seRow.deliveryResidenceCode)) {
												specailMarkWin.down('hidden[name=deliveryResidenceCode]').setValue(seRow.actualSmallzoneCode);
											} 
											if (!Ext.isEmpty(seRow.actualSmallzoneName)) {
												specailMarkWin.down('hidden[name=deliveryResidenceName]').setValue(seRow.actualSmallzoneName);
											}
									}

								specailMarkWin.show();

							}
						}
					});
				}
		},
		{//地址坐标标记
			xtype : 'button',
			text : '地址坐标标记',
			style : 'margin-top:10px;',
			columnWidth: 1,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', { seconds: 3 }),
			disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/addressCroodMarkButton'),
			hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/addressCroodMarkButton'),
			handler: function() {
					//1-左边地图区域进入
					var wabyInfoArr = new Array(); //定义待排单运单号数组
					var gisArray = new Array(); //定义GIS返回的运单号数组
					gisArray = myMap.chooseInwaybill();
					for(var i = 0; i< gisArray.length; i++) {
						wabyInfoArr.push(gisArray[i]);
					}
					//2-右边已排单区域进入
					var selectRow = predeliver.visibleOrderBill.deliverbillDetailGrid.getSelectionModel().getSelection();

					//校验操作-地图、已排单都未选择
					if (selectRow.length == 0 && wabyInfoArr.length == 0) {
						Ext.ux.Toast.msg('提示', '地址坐标标记必须选择相应的运单','error', 2500);
						return;
					}
					//校验操作-地图、已排单不能同时操作
					if (selectRow.length > 0 && wabyInfoArr.length > 0) {
						var waybillstr = jsArrToJSON(wabyInfoArr);
						myMap.resetwaybillMarkerFromFoss(waybillstr);
						Ext.ux.Toast.msg('提示', '地图地址坐标标记和已排单地址坐标标记不能同时操作','error', 2500);
						return;
					}
					//如果右边已排单选中结果大于0表，表示从右边进入的
					if (selectRow.length > 0) {
							wabyInfoArr = new Array();
							for ( var i = 0; i < selectRow.length; i++) {
								wabyInfoArr.push(selectRow[i].data.waybillNo);
							}
					} 

					//根据条件查询特殊地址历史库的特殊地址类型
					Ext.Ajax.request({
							url : predeliver.realPath('visibleAddressMark.action'),
							async : false,
							jsonData:{
								'vo': {
									'specialWayBillNO': wabyInfoArr.join(',')
								}
							},
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.vo.listAddresMarks) {
										var markWin = Ext.create('Foss.predeliver.visibleOrderBill.addressCoordinateMark');
										markWin.down('grid').getStore().loadData(json.vo.listAddresMarks);
										markWin.show();
								}
							}
				});
		}
  }],
		constructor : function(config) {
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([ cfg ]);
			var vehicleType = me.getForm().findField('vehicleType'),
				vehicleTypeStore = vehicleType.getStore();
			vehicleTypeStore.on('load',function(th,re){
					var model = Ext.ModelManager.create({
					  'code' : deptCode,
					  'name' : deptName
					},'Foss.baseinfo.commonSelector.MotorcadeModel');
					th.insert(0, model);
			});
		}
});
//Panel下中间操作区域-结束


// 定义已排运单deliverbillDetailGrid-checkbox model
Ext.define('Foss.predeliver.visibleOrderBill.CheckboxModel', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.visibleOrderBill.rightToArrange.add(record.data.id,
					record.data);

		},
		deselect : function(row, record, index, eOpts) {
			predeliver.visibleOrderBill.rightToArrange
					.removeAtKey(record.data.id);
		}
	}
})

//已排单运单的表格-开始
Ext.define('Foss.predeliver.visibleOrderBill.deliverbillDetailGrid',{
		extend : 'Ext.grid.Panel',
		frame : true,
		sortableColumns : false,
		id: 'visibleOrderBill_deliverbillDetailGrid_ID',
		padding: '0 0 0 0',
		margin : '0 0 0 2',
		flex: 2,
		height: 700,
		autoScroll : true,
		animCollapse : true,
		tbar:[{ //表头标题与自动排序
				xtype: 'label',
				text: '已排单运单   ',
				style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:4px solid #3d74b7;'
				},'->',{
						xtype: 'button',
						text: '可视化排序',
						height: 25,
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 3
						}),
						cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
						handler: function(button) {
								if (!Ext.isEmpty(predeliver.visibleOrderBill.deliverbillId) && predeliver.visibleOrderBill.deliverbillDetailGrid.getStore().getCount() > 0) {
										Ext.Ajax.request({
												url : predeliver.realPath('visibleIfExistsNoCoord.action'),
												params : {
													'deliverbillVo.deliverbill.id' : predeliver.visibleOrderBill.deliverbillId
												},
												success : function(response) {
													  //调用可视化界面
                            Ext.Ajax.request({
                              url : predeliver.realPath('visiblebillInfo.action'),
                              params :{
                                'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
                              },
                              success :function(response){
                                var result = Ext.decode(response.responseText);
                                var markWin = Ext.create('Foss.predeliver.visibleOrderBill.visibleAutoSortWindow');
                                var temp = Ext.create('Foss.predeliver.visibleOrderBill.deliverbillInfoModel',result.deliverbillNewVo.deliverbillNewDto);
                                markWin.down('form').loadRecord(temp);
                                if (!Ext.isEmpty(result.deliverbillNewVo.deliverbillNewDto.calculateType)) {
                                	//距离排序
                                	if (result.deliverbillNewVo.deliverbillNewDto.calculateType=='DISTANCE_PRIORITY') {
                                		markWin.down('displayfield[name=totalDistance1]').setValue(result.deliverbillNewVo.deliverbillNewDto.totalDistance);
									//时效排序
                                	}else if (result.deliverbillNewVo.deliverbillNewDto.calculateType=='AGING_PRIORITY') {
                                		markWin.down('displayfield[name=totalDistance2]').setValue(result.deliverbillNewVo.deliverbillNewDto.totalDistance);
                                	}
								}
                                markWin.down('grid').getStore().setDeliverbillId(predeliver.visibleOrderBill.deliverbillId);
                                markWin.down('grid').getStore().load();
                                markWin.show();
                              },
                              error : function(response) {
                                var result = Ext.decode(response.responseText);
                                Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
                              },
                              exception : function(response) {
                                var result = Ext.decode(response.responseText);
                                Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
                              }
                            });
												},
												exception : function(response) {
														var result = Ext.decode(response.responseText);
														Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
												}
										});
								} else {
											Ext.ux.Toast.msg('提示', '该派送单未进行任何排单，不能进行可视化排序!', 'error', 2500);
								}
						}
				/*{
				xtype: 'button',
				text: '自动排序',
				height: 25,
				cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
				handler: function(button) {
					//点击自动排序后，禁用其他排单操作，如果GIS超时，则不排序，释放操作
					var addBtn = Ext.getCmp('predeliver_visibleOrderBill_AddArrange_ID');
					var moveBtn = Ext.getCmp('predeliver_visibleOrderBill_moveOut_ID');
					addBtn.setDisabled(true);
					moveBtn.setDisabled(true);
					button.setDisabled(true);
					predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(true);

					//自动排序
					Ext.Ajax.request({
							url : predeliver.realPath('visibleQueryDeliverbillDetailAllList.action'),
							timeout: 7000, //调GIS超时时间
							params : {
								'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
							},
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.vo.sortMap) {
										Ext.Ajax.request({
												url : predeliver.realPath('visibleAutoSort.action'),
												async : false,
												jsonData:{
													'vo': {
														"sortMap": json.vo.sortMap//GIS返回的参数列表
													},
													'deliverbillVo': {
														"deliverbill": {
															'id': predeliver.visibleOrderBill.deliverbillId
														}
													}
												},
												success : function(response) {
														addBtn.setDisabled(false);
														moveBtn.setDisabled(false);
														button.setDisabled(false);
														predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(false);
														predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
												},
												exception: function(response) {
														addBtn.setDisabled(false);
														moveBtn.setDisabled(false);
														button.setDisabled(false);
														predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(false);
												}
										});
								} else {
									addBtn.setDisabled(false);
									moveBtn.setDisabled(false);
									button.setDisabled(false);
									predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(false);
								} 
							},
							exception: function (response, options) {
								addBtn.setDisabled(false);
								moveBtn.setDisabled(false);
								button.setDisabled(false);
								predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(false);
								var json = Ext.decode(response.responseText);
								if(json) {
									Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
								}
              },
							failure:function() {
								addBtn.setDisabled(false);
								moveBtn.setDisabled(false);
								button.setDisabled(false);
								predeliver.visibleOrderBill.deliverbillDetailGrid.setDisabled(false);
							}
					});
					
			  }
				}*/}],
		dockedItems : [
			{//底部统计信息
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				defaults:{
					margin:'0 0 0 0',
					allowBlank:true
				},
				items: [
							{
								xtype:'displayfield',
								allowBlank:true,
								labelWidth : 65,
								name:'selectTicket',
								columnWidth:.21,
								value: '0',
								fieldLabel: '已选票数'
						 },
						 {
								xtype:'displayfield',
								allowBlank:true,
								labelWidth : 90,
								name:'selectWeight',
								columnWidth:.35,
								value: '0',
								fieldLabel: '已选重量(KG)'
						 },
						 {
								xtype:'displayfield',
								allowBlank:true,
								labelWidth : 90,
								name:'selectVolumn',
								columnWidth:.3,
								value: '0',
								fieldLabel: '已选体积(m³)'
						 },
						 {
								xtype:'displayfield',
								allowBlank:true,
								labelWidth : 60,
								name:'totalTicket',
								value: '0',
								columnWidth:.25,
								fieldLabel: '总票数'
						 },{
								xtype:'displayfield',
								allowBlank:true,
								name:'totalCount',
								value: '0',
								labelWidth : 60,
								columnWidth:.25,
								fieldLabel: '总件数'
						},{
								xtype:'displayfield',
								columnWidth:.25,
								labelWidth : 100,
								value: '0',
								name : 'totalWeight',
								fieldLabel: '总重量(KG)'
						},{
								xtype:'displayfield',
								allowBlank:true,
								labelWidth : 80,
								name:'totalQty',
								value: '0',
								columnWidth:.3,
								name : 'totalVolumn',
								fieldLabel: '总体积(m³)'
						},{
								xtype:'displayfield',
								allowBlank:true,
								name:'totalWeight',
								value: '0',
								labelWidth : 100,
								columnWidth:.3,
								name : 'totalPayAmount',
								fieldLabel: '到付金额(元)'
						},{
								xtype:'displayfield',
								columnWidth:.8,
								width: 200,
								value: '0% / 0%',
								labelWidth : 135,
								name: 'loadRate',
								style: 'marginTop:1px',
								fieldLabel: '装载率(容积/体积)'
						},{
							xtype:'displayfield',
							columnWidth:1,
							value: '0 / 0',
							labelWidth : 180,
							name: 'nominalRate',
							fieldLabel: '额定净空(方)/额定载重(吨)'
						}
				]
			}
		],
	/*	bbar : Ext.create('Deppon.StandardPaging',{
			store:null, //Ext.create('Foss.predeliver.store.DeliverTypeStore'),
			displayInfo: true,
			pageSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500]]
			})
		}),   */
		columns : [
			{
				dataIndex : 'id',
				align : 'center',
				flex : 1,
				hidden : true
			},
			/*{//操作
				xtype : 'actioncolumn',
				//flex : 2,
				width : 80,
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.operate'), //操作,
				align : 'center',
				items : [ {
							iconCls : 'deppon_icons_up',
							tooltip : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.moveUp'), //上移,
							handler : function(grid, rowIndex, colIndex) {
								// alert(grid.getStore().getAt(rowIndex).get('id'));
								if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
									Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
											'error', 4000);
									return;
								}

								Ext.Ajax.request({
											url : predeliver.realPath('upgradeDeliverbillDetail.action'),
											params : {
												'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId,
												'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
												'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
											},
											success : function(response) {
												// 刷新当前页
												predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
											}
								});

								if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
									Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
											'error', 4000);
									return;
								}
							}
						},
						{
							iconCls : 'deppon_icons_down',
							tooltip : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.down'), //下移,
							handler : function(grid, rowIndex, colIndex) {
								// alert(grid.getStore().getAt(rowIndex).get('id'));
								if (grid.getStore().getAt(rowIndex).get('serialNo') == grid.getStore().getTotalCount()) {
									Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.theEnd'),
											'error', 4000);
									return;
								}

								Ext.Ajax.request({
											url : predeliver.realPath('downgradeDeliverbillDetail.action'),
											params : {
												'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId,
												'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
												'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
											},
											success : function(response) {
												// 刷新当前页
												predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
											}
								});
							}
						}]
			},*/
			{//单号
				dataIndex : 'waybillNo',
				align : 'center',
				width : 80,
				header : '单号'
			},
			{//规定兑现时间
				dataIndex : 'cashTime',
				align : 'center',
				width : 80,
				xtype : 'ellipsiscolumn',				
				header : '规定兑现时间'
			},			
			{//送货地址
				dataIndex : 'consigneeAddress',
				align : 'center',
				width : 130,
				xtype: 'ellipsiscolumn',
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress')
			},
			{//预处理建议
				dataIndex : 'preSuggestions',
				align : 'center',
				width : 150,
				header : '预处理建议',
				xtype : 'templatecolumn',
				tpl :  new Ext.XTemplate(
									'<tpl if="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'normalcolor\' ">',
											'<p>{actualSmallzoneName}/<br/><font color="red">{vehicleNo}</font></p>',
									'<tpl elseif="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'name\'">',
											'<p>{actualSmallzoneName}</p>',
									'<tpl elseif="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'colorvno\'">',
											'<p><font color="red">{vehicleNo}</font></p>',
									'<tpl elseif="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'vehicno\'">',
											'<p>{vehicleNo}</p>',
									'<tpl elseif="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'other\'">',
											'<p>{actualSmallzoneName}/<br/>{vehicleNo}</p>',
									'<tpl elseif="this.returnType(isVehicleScheduling, actualSmallzoneName, vehicleNo) == \'NULL\'">',
											'<p> </p>',
									'</tpl>',
							{
									/*XTemplate 配置：*/
									disableFormats: true,
									/* 成员函数:*/
									returnType: function(isVehicleScheduling, actualSmallzoneName, vehicleNo){
										if (isVehicleScheduling == 'Y'){//有排班
											if (!Ext.isEmpty(actualSmallzoneName) && !Ext.isEmpty(vehicleNo)) {
												return "normalcolor";
											} else if (!Ext.isEmpty(actualSmallzoneName) && Ext.isEmpty(vehicleNo)){
												return "name";
											} else if (Ext.isEmpty(actualSmallzoneName) && !Ext.isEmpty(vehicleNo)){
												return "colorvno";
											} else {
												return "NULL";
											}
										} else {//没有排班
											if (!Ext.isEmpty(actualSmallzoneName) && !Ext.isEmpty(vehicleNo)) {
												return "other";
											} else if (!Ext.isEmpty(actualSmallzoneName) && Ext.isEmpty(vehicleNo)){
												return "name";
											} else if (Ext.isEmpty(actualSmallzoneName) && !Ext.isEmpty(vehicleNo)){
												return "vehicno";
											} else {
												return "NULL";
											}
										} 
									}
							}
					)
			},
			{//已排件数
				dataIndex : 'preArrangeGoodsQty',
				align : 'center',
				width : 60,
				header : '已排件数'
			},
			{//已排重量
				dataIndex : 'weight',
				align : 'center',
				width : 60,
				header : '已排重量'
			},
			{//已排体积
				dataIndex : 'goodsVolumeTotal',
				align : 'center',
				width : 60,
				header : '已排体积'
			},
			{//尺寸
				dataIndex : 'dimension',
				align : 'center',
				width : 130,
				xtype: 'ellipsiscolumn',
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize')
			},
			{//包装
				dataIndex : 'goodsPackage',
				align : 'center',
				width : 60,
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage')
			},
			{//运输性质
				dataIndex : 'transportType',
				align : 'center',
				width : 100,
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'),
				renderer : function(value) {
					return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
				}
			},
			{//送货日期
				dataIndex : 'deliverDate',
				align : 'center',
				width : 130,
				header : predeliver.visibleOrderBill.i18n('pkp.predeliver.confirmDeliverbill.deliverDate'),
				xtype : 'datecolumn',
				format : 'Y-m-d' // H:i:s
			},
			{//送货时间段
				dataIndex : 'deliveryTimeInterval',
				align : 'center',
				width : 60,
				header : '送货时间段'
			},
			{//送货时间范围
				dataIndex : 'deliverRange',
				align : 'center',
				width : 130,
				header : '送货时间范围',
				xtype : 'templatecolumn',
				tpl :  new Ext.XTemplate(
									'<tpl if="this.isNull(deliveryTimeStart)">',
											'<p>{deliveryTimeStart} - {deliveryTimeOver}</p>',
									'<tpl else>',
											'<p></p>',
									'</tpl>',
							{
									/*XTemplate 配置：*/
									disableFormats: true,
									/* 成员函数:*/
									isNull: function(deliveryTimeStart){
										if (deliveryTimeStart == null || deliveryTimeStart == ''){
											return false;
										} else {
											return true;
										}
									}
							}
					)
			},
			{//到付金额
				dataIndex : 'payAmount',
				align : 'center',
				width : 60,
				header : '到付金额'
			},
			{//合送编码
				dataIndex : 'togetherSendCode',
				align : 'center',
				width : 80,
				header : '合送编码'
			},
		],
		deleteDeliverbillDetails : function(deliverbillDetailIds) {
				Ext.Ajax.request({
							url : predeliver.realPath('deleteDeliverbillDetails.action'),
							params : {
								'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId,
								'deliverbillVo.deliverbillDetailIds' : deliverbillDetailIds
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var deliverbill = Ext.create('Foss.predeliver.visibleOrderBill.DeliverbillModel', result.deliverbillVo.deliverbill);
								predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();

								//Ext.getCmp('T_predeliver-visibleOrderBillIndex_content').refreshMiddlePanelInfo(deliverbill.get('vehicleNo'),
								//deliverbill.get('driverName'),deliverbill.get('driverCode'),deliverbill.get('deliverType'));
								Ext.getCmp('T_predeliver-visibleOrderBillIndex_content').refreshMiddlePanelInfo(deliverbill);
							},
							exception: function(response){
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
							}
				});
		},
		pagingToolbar : null,
		getPagingToolbar : function() {
			if (this.pagingToolbar == null) {
				this.pagingToolbar = Ext.create(
						'Deppon.StandardPaging', {
							pageSize : 50,
							store : this.store,
							plugins: 'pagesizeplugin',
							displayInfo: true
						});
			}
			return this.pagingToolbar;
		},
		listeners: {
			'selectionchange': function (seleMode, redModS, eOpts) {
				var selectTicket = 0;
				var selectWeight = 0;
				var selectVolumn = 0;
				var gridPanel = predeliver.visibleOrderBill.deliverbillDetailGrid;
				Ext.each(redModS, function(item){
					selectTicket = selectTicket + 1;
					selectWeight = selectWeight + item.data.weight;
					selectVolumn = selectVolumn + item.data.goodsVolumeTotal;
				});
				if (redModS.length > 0) {
					gridPanel.down('displayfield[name=selectTicket]').setValue(selectTicket);
					gridPanel.down('displayfield[name=selectWeight]').setValue(selectWeight.toFixed(2));
					gridPanel.down('displayfield[name=selectVolumn]').setValue(selectVolumn.toFixed(2));
				} else {
					gridPanel.down('displayfield[name=selectTicket]').setValue(selectTicket);
					gridPanel.down('displayfield[name=selectWeight]').setValue(selectWeight);
					gridPanel.down('displayfield[name=selectVolumn]').setValue(selectVolumn);
				}

			}
		},
    viewConfig: {
        plugins: {
          ptype: 'gridviewdragdrop',
          dragText: '拖拽行记录进行排序,点击保存(提交)后生效!'
        }
		},
		constructor: function(config){
			var me = this, cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.predeliver.visibleOrderBill.deliverbillDetailGridStore');
			me.selModel = Ext.create('Foss.predeliver.visibleOrderBill.CheckboxModel');
			me.bbar = me.getPagingToolbar();
			me.callParent([ cfg ]);
		}
	});
//已排单运单的表格-结束

// 排单窗口
Ext.define('Foss.predeliver.visibleOrderBill.ArrangeDeliverbillWindow',{
	extend : 'Ext.window.Window',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	closeAction : 'hide',
	width : 1100,
	// 确认运单FORM
	confirmForm : null,
	getConfirmForm : function() {
		if (this.confirmForm == null) {
			this.confirmForm = Ext
					.create('Foss.predeliver.visibleOrderBill.ConfirmForm');
		}
		return this.confirmForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getQueryWaybillForm(),
				me.getConfirmForm() ];
		me.callParent([ cfg ]);
	}
});

//右移动确认运单form
Ext.define('Foss.predeliver.visibleOrderBill.ConfirmForm',{
	extend : 'Ext.form.Panel',
	frame : false,
	height : 80,
	defaults : {
		margin : '0 45 0 25'
	},
	layout : 'column',
	addWaybillToArrange : function() {
		// 排单前需验证车辆信息
		if (predeliver.visibleOrderBill.deliverbillId == "") {
			var buttonPanelInfo = predeliver.visibleOrderBill.ButtonPanelRole.getValues();
			// 保存派送单
			Ext.Ajax.request({
				url : predeliver.realPath('saveDeliverbill.action'),
				jsonData : {
					'vo' : {
						 'deliverbill' : {
								'id' : predeliver.visibleOrderBill.deliverbillId,
								'deliverbillNo' : visibleOrderBill.deliverbillNo,
								'vehicleNo' : buttonPanelInfo.vehicleNo,
								'driverCode' : buttonPanelInfo.driverCode,
								'deliverType' : buttonPanelInfo.deliverType,
								'driverName' : predeliver.visibleOrderBill.ButtonPanelRole.getForm().findField("driverName").rawValue
							}
					}
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					predeliver.visibleOrderBill.deliverbillId = result.vo.deliverbill.id;
					predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
				}
		});
	 } else {
			predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
	 }
	},
		items : [ {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '0 0 0 0'
		},
		items : [
			{
				border : false,
				columnWidth : .92,
				html : '&nbsp;'
			},{
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.confirm'), //确认,
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					// 设定间隔秒数,如果不设置，默认为2秒
					seconds : 2
				}),
				handler : function() {
					if (predeliver.visibleOrderBill.waybillsToArrange.length == 0) {
						Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.choseDeliverDetail'),'ok', 4000);
						return;
					}
					var waybills = '';
					var selectWaybillNos =new Array();//存放复选框选中的运单
					//待排单窗口-替换
					var selectRowGrid =Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getArrangeDeliverbillWindow()
							.getdeliverbillDetailGrid(),
						selectRowModel = selectRowGrid.getSelectionModel(),
							selectRow = selectRowModel.getSelection();
					predeliver.editDeliverbill.waybillsToArrange
							.each(function(item, index,length) {
									selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
								if (item.isAlreadyContact == 'N') {
									waybills += item.waybillNo
											+ '<br/>';
								}
							});

					var deliverStore = Ext.getCmp('Foss_predeliver_editDeliverbill_deliverbillDetailGrid_Id').store;
					var map = new Ext.util.HashMap(), flg = false;
					for (var i = 0; i < selectRow.length; i++) {
						map.add(selectRow[i].get("waybillNo"), selectRow[i].get("waybillNo"));
					}
					deliverStore.each(function(record, index, allRecords){
						if (Ext.isEmpty(map.get(record.get('waybillNo')))) {
							if (record.get('isSentRequired')=='Y') {
								flg = true;
							}
						}
					});
					if (flg == true) {
						var confirmMsg = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
								if (btn == 'yes') {
									if (flag == false) {
										flag = true;
										return;
									}
									if (waybills == '') {
										predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
									} else {
										var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
											if (btn == 'yes') {
												predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
											}
										},predeliver.editDeliverbill.arrangeDeliverbillWindow);
									}
								}
							});
					} else {
						if (flag == false) {
							flag = true;
							return;
						}
						if (waybills == '') {
							predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
						} else {
							var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
								if (btn == 'yes') {
									predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
								}
							},predeliver.editDeliverbill.arrangeDeliverbillWindow);
						}
					}
				}
			} ]
	} ]
});

//页面底部保存提交form
Ext.define('Foss.predeliver.visibleOrderBill.SubmitForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	height : 80,
	defaults : {
		margin : '0 20 0 0'
	},
	layout : 'column',
	// 排单前验证
	validateOnArrange : function() {
		// 11a 12a 排单员点击“保存”或“提交”时，校验表单是否有效
		if (!predeliver.visibleOrderBill.ButtonPanelRole.getForm().isValid()) {
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '有必填项（页面标识红叉项）未填写，请填写完整！');
			return false;
		}
		return true;
	},
	// 排单前验证
	validateOnDriver : function() {
		// 11a 12a 排单员点击“保存”或“提交”时，如果外请车没有对应的司机
		if (predeliver.visibleOrderBill.vehicleNoTruckType == '外请车'&& predeliver.visibleOrderBill.ButtonPanelRole.getForm().findField('driverName').getRawValue() == '') {
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.drivercantblank'));
			return false;
		}
		return true;
	},
	// 保存/提交时验证
	validateOnSaveOrSubmit : function() {
		// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
		if (!predeliver.visibleOrderBill.ButtonPanelRole.getForm().isValid()) {
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '有必填项（页面标识红叉项）未填写，请填写完整！');
			return false;
		}

		// 11b 12b
		// 排单员点击"保存"或"提交"时，如果未有选中的运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
		if (predeliver.visibleOrderBill.deliverbillDetailGrid.store.data.getCount() == 0) {
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheGoods'));
			return false;
		}
		return true;
	},
	// 保存/提交派送单
	saveOrSubmit : function(deliverbillStatus, button) {
		//需要验证车牌号/司机是否有效
		if (!predeliver.visibleOrderBill.SubmitForm.validateOnSaveOrSubmit()) {
			return;
		}
    
    //拖动排序保存,取当前的集合数据
    var sortGridList = new Array();
    predeliver.visibleOrderBill.deliverbillDetailGrid.getStore().each(function(item, index, length) {
      var obj = new Object();
      obj.id = item.data.id;
      obj.waybillNo = item.data.waybillNo;
      sortGridList.push(obj);
    });
    

		var panelRole = predeliver.visibleOrderBill.ButtonPanelRole;
		// 更新派送单
		Ext.Ajax.request({
				url : predeliver.realPath('visibleSaveDeliverbill.action'),
				jsonData : {
					'vo' : {
						'deliverbill' : {
							'id' : predeliver.visibleOrderBill.deliverbillId,
							'deliverbillNo' : Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text,
							'vehicleNo' : panelRole.getValues().vehicleNo,
							'delStatus' :predeliver.visibleOrderBill.status,
							'driverCode' : panelRole.down('textfield[name=driverCode]').getValue(),
							'deliverType' : panelRole.getValues().deliverType,
							'driverName' : panelRole.getForm().findField("driverName").rawValue,
							'status' : deliverbillStatus,
							'carTaskinfo': panelRole.down('combobox[name=carTaskinfo]').getValue(),
							'frequencyno': panelRole.down('combobox[name=frequencyNo]').getValue(),
							'preCartaskTime': panelRole.down('timefield[name=preCartaskTime]').getRawValue(),
							'takeGoodsDeptcode': panelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
							'takeGoodsDeptname': panelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
							'expectedbringvolume': panelRole.down('textfield[name=expectedBringVolume]').getValue(),
							'transferDeptcode': panelRole.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
							'transferDeptname': panelRole.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
						},
						'dragStr': sortGridList.length < 1 ? null : Ext.encode(sortGridList)
					}
				},
				success : function(response) {
					//点击保存后，车辆可以修改
					predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').setReadOnly(false);
					predeliver.visibleOrderBill.isOrSave = false;
					//提交时才弹出打印窗口，重置页面信息
					if (deliverbillStatus == 'SUBMITED') {
								var confirmTitle = deliverbillStatus == 'SAVED' ? predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.saveDeliverSuccess')
										: predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.submitDeliverSuccess');

								var confirmMsg = confirmTitle
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.theNumberIs')
										+ Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.weightTotalbr')
										+ visibleTotalWeight
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotalbr')
										+ visibleTotalVolumn
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotalbr')
										+ visibleTotalTicket
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.piecesTotalbr')
										+ visibleTotalCount
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.payAmountTotalbr')
										+ visibleTotalPayAmount
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.loadRatebr')
										+ visibleLoadRate
										+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.surePrintDeliverBill')

								Ext.Msg.confirm(confirmTitle, confirmMsg, function(btn, text) {
										if (btn == "yes") {
											// 打印预派送单
											var printWin = Ext.create('Foss.predeliver.deliverbill.PrintDeliverbillWindow', {
													'deliverbillId' : predeliver.visibleOrderBill.deliverbillId
											});

											//获取打印的预排送单基本信息
											Ext.Ajax.request({
													url : predeliver.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : predeliver.visibleOrderBill.deliverbillId
													},
													success : function(response) {
															var result = Ext.decode(response.responseText), model = Ext.ModelManager
																	.create(result.deliverbillVo.deliverbill, 'Foss.predeliver.deliverbill.PrintModel');
															printWin.getDeliverPrintHeadInfo().loadRecord(model);
															printWin.getDeliverPrintBottomInfo().loadRecord(model);
															var driverName = result.deliverbillVo.deliverbill.driverName;
															if(driverName!=null && driverName!=""){//如果司机姓名不为空，通知司机按钮显示、可操作
																printWin.query('checkbox[name="isNoticeDriver"]')[0].setValue('Y');
																printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
															}else{//如果司机姓名为空，通知司机按钮显示、不可操作
																printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
																printWin.query('checkbox[name="isNoticeDriver"]')[0].setDisabled (true); 
															}
															printWin.getDeliverPrintGrid().store.setDeliverbillId(predeliver.visibleOrderBill.deliverbillId);
															printWin.getDeliverPrintGrid().getPagingToolbar().moveFirst();
															printWin.show();
															
															predeliver.visibleOrderBill.ButtonPanelRole.getForm().reset();
															predeliver.visibleOrderBill.vehicleNoTruckType = null;
															//刷新派送单
															Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').setText(querySequence());
															predeliver.visibleOrderBill.deliverbillId = "";
															predeliver.visibleOrderBill.deliverbillDetailGrid.store.removeAll(); 
															predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
															predeliver.visibleOrderBill.ButtonPanelRole.down('textfield[name=driverCode]').setValue("");
													}
											});
											
											
										} else {
													predeliver.visibleOrderBill.ButtonPanelRole.getForm().reset();
													predeliver.visibleOrderBill.vehicleNoTruckType = null;
													//刷新派送单
													Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').setText(querySequence());
													predeliver.visibleOrderBill.deliverbillId = "";
													predeliver.visibleOrderBill.deliverbillDetailGrid.store.removeAll(); 
													predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
													predeliver.visibleOrderBill.ButtonPanelRole.down('textfield[name=driverCode]').setValue("");
										}
								});
				} else {
          Ext.ux.Toast.msg('提示', '保存成功!','error', 5000);
        }

					if (deliverbillStatus == 'SUBMITED') {
						//Ext.getCmp('T_predeliver-visibleOrderBillIndex').close();
						if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
								&& predeliver.deliverbill.deliverbillGrid != null) {
							predeliver.deliverbill.deliverbillGrid.store.load();
						}
					}

				},
				exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
				}
		 });
	},
	items : [ {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '0 0 0 0'
		},
		items : [
				{
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				},
				{
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.save'), //保存,
					disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/visibleOrderSaveButton'),
					hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/visibleOrderSaveButton'),
					handler : function(button, e) {
						button.up('form').saveOrSubmit('SAVED', button);
					}
				},
				{
					columnWidth : .08,
					xtype : 'button',
					//id : 'deliverbill_submit_vob',
					disabled : false,
					cls : 'yellow_button',
					text : predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.submit'), //提交,
					disabled:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/visibleOrderSubmitButton'),
					hidden:!predeliver.visibleOrderBill.isPermission('visibleOrderBillIndex/visibleOrderSubmitButton'),
					handler : function(button, e) {
						button.up('form').saveOrSubmit('SUBMITED');
					}
				}]
	} ]
});

//整车校验
predeliver.visibleOrderBill.checkWayBillNosWVH = function(selectWVHWaybillNos) {
	if(selectWVHWaybillNos.length<=0){
		return true;
	}
	var result1 =true;
	//判断传入的整车运单号是否做配载和到达
	Ext.Ajax.request({
		url : predeliver.realPath('checkWayBillNosWVH.action'),
		async : false,
		jsonData:{
			'deliverbillVo' : {
				'waybillNos':selectWVHWaybillNos
			}
		},
		success : function(response) { },
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			result1 =false;
		}
	});
	return 	result1;
}

//添加派送单明细操作
predeliver.visibleOrderBill.addWaybillToArrange =function(id){
	console.info(predeliver.visibleOrderBill.waybillsToArrange.items);
	Ext.Ajax.request({
		url : predeliver.realPath('visibleAddWaybillToArrange.action'),
		timeout: 600000,
		jsonData : {
			'vo' : {
				'waybillToArrangeDtoList' : predeliver.visibleOrderBill.waybillsToArrange.items,
				'deliverbill' : {
					'id' : id
				}
			}
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var alertMsg = "";
			var failedList = result.vo.waybillToArrangeDtoList;
			if(failedList) {
				for ( var i = 0; i < failedList.length; i++) {
					alertMsg = alertMsg
							+ predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.waybillbr')
							+ "</br>"
							+ failedList[i].waybillNo
							+ " "
							+ failedList[i].failedToArrangeReason + "</br>";
							predeliver.visibleOrderBill.exceptionWaybills.push(failedList[i].waybillNo);
				}
			}

			// 刷新派送单信息
			var deliverbill = Ext.create('Foss.predeliver.visibleOrderBill.DeliverbillModel', result.deliverbillVo.deliverbill);

   //Ext.getCmp('T_predeliver-visibleOrderBillIndex_content').refreshMiddlePanelInfo(deliverbill.get('vehicleNo'),
   //deliverbill.get('driverName'),deliverbill.get('driverCode'),deliverbill.get('deliverType'));
			Ext.getCmp('T_predeliver-visibleOrderBillIndex_content').refreshMiddlePanelInfo(deliverbill);

			predeliver.visibleOrderBill.deliverbillDetailGrid.store.load();
			if (alertMsg == "") {
				Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
						result.message);
			} else {
				Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), alertMsg,
						'error', 5000);
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
		}
	});
}

//添加派送单操作
function addWaybillToArrange() {
	// 排单前需验证车辆信息
	if (predeliver.visibleOrderBill.deliverbillId == "") {
		var buttonPanelInfo = predeliver.visibleOrderBill.ButtonPanelRole;
		// 保存派送单
		Ext.Ajax.request({
				url : predeliver.realPath('visibleSaveDeliverbill.action'),
				jsonData : {
					'vo' : {
						'deliverbill' : {
							'id' : predeliver.visibleOrderBill.deliverbillId,
							'deliverbillNo' : Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text,
							'vehicleNo' : buttonPanelInfo.getValues().vehicleNo,
							'delStatus' :"",
							'driverCode' : buttonPanelInfo.down('textfield[name=driverCode]').getValue(),
							'deliverType' : buttonPanelInfo.getValues().deliverType,
							'driverName' : buttonPanelInfo.getForm().findField("driverName").rawValue,
							'carTaskinfo': buttonPanelInfo.down('combobox[name=carTaskinfo]').getValue(),
							'frequencyno': buttonPanelInfo.down('combobox[name=frequencyNo]').getValue(),
							'preCartaskTime': buttonPanelInfo.down('timefield[name=preCartaskTime]').getRawValue(),
							'takeGoodsDeptcode': buttonPanelInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
							'takeGoodsDeptname': buttonPanelInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
							'expectedbringvolume': buttonPanelInfo.down('textfield[name=expectedBringVolume]').getValue(),
							'transferDeptcode': buttonPanelInfo.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
							'transferDeptname': buttonPanelInfo.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
						}
					}
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					predeliver.visibleOrderBill.deliverbillId = result.vo.deliverbill.id;
					predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
					var win = Ext.getCmp('predeliver_visibleOrderBill_modifiyArrangeWin_ID');
					if (win) {
						win.close();
					}
		  	},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			 }
		});
	} else {
		//如果车牌号等可以修改的情况下，更新修改的信息
		if (!predeliver.visibleOrderBill.isOrSave) {
			var buttonPanelInfo = predeliver.visibleOrderBill.ButtonPanelRole;
				// 保存派送单
				Ext.Ajax.request({
						url : predeliver.realPath('visibleSaveDeliverbill.action'),
						jsonData : {
							'vo' : {
								'deliverbill' : {
									'id' : predeliver.visibleOrderBill.deliverbillId,
									'deliverbillNo' : Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text,
									'vehicleNo' : buttonPanelInfo.getValues().vehicleNo,
									'delStatus' : "",
									'driverCode' : predeliver.visibleOrderBill.ButtonPanelRole.down('textfield[name=driverCode]').getValue(),
									'deliverType' : buttonPanelInfo.getValues().deliverType,
									'driverName' : buttonPanelInfo.getForm().findField("driverName").getRawValue(),
									'carTaskinfo': buttonPanelInfo.down('combobox[name=carTaskinfo]').getValue(),
									'frequencyno': buttonPanelInfo.down('combobox[name=frequencyNo]').getValue(),
									'preCartaskTime': buttonPanelInfo.down('timefield[name=preCartaskTime]').getRawValue(),
									'takeGoodsDeptcode': buttonPanelInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
									'takeGoodsDeptname': buttonPanelInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
									'expectedbringvolume': buttonPanelInfo.down('textfield[name=expectedBringVolume]').getValue(),
									'transferDeptcode': buttonPanelInfo.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
									'transferDeptname': buttonPanelInfo.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
								}
							}
						},
						success : function(response) {
							predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
					 }
			});
		} else {
			predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
		}
		/*predeliver.visibleOrderBill.addWaybillToArrange(predeliver.visibleOrderBill.deliverbillId);
		如果派送单已经创建，允许修改车辆，更新车辆到派送单信息
		if (!predeliver.visibleOrderBill.ButtonPanelRole.down('commontruckselector[name=vehicleNo]').readOnly) {
				var buttonPanelInfo = predeliver.visibleOrderBill.ButtonPanelRole.getValues();
				// 保存派送单
				Ext.Ajax.request({
						url : predeliver.realPath('visibleSaveDeliverbill.action'),
						jsonData : {
							'vo' : {
								'deliverbill' : {
									'id' : predeliver.visibleOrderBill.deliverbillId,
									'deliverbillNo' : Ext.getCmp('predeliver_visibleOrderBill_deliverbillNo_ID').text,
									'vehicleNo' : buttonPanelInfo.vehicleNo,
									'delStatus' : "",
									'driverCode' : buttonPanelInfo.driverCode,
									'deliverType' : buttonPanelInfo.deliverType,
									'driverName' : predeliver.visibleOrderBill.ButtonPanelRole.getForm().findField("driverName").rawValue
								}
							}
						},
						success : function(response) {},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
					 }
				});
		}*/
	}
}

//验证单货物状态是否发生了变更
predeliver.visibleOrderBill.queryIsExsitsWayBillRfcs = function(selectWaybillNos) {
	// 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	Ext.Ajax.request({
		url : predeliver.realPath('visibleIsExsitsWayBillRfcs.action'),
		async : false,
		jsonData:{
			'deliverbillVo' : {
				'waybillNos':selectWaybillNos
			}
		},
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var waybillNos = json.deliverbillVo.waybillNos; //更改单申请未处理
			var notWaybillrfcNos = json.deliverbillVo.notWaybillrfcNos; //更改单申请未受理
			var waybillString = '';
			var notWaybillrfcString ='';
			predeliver.visibleOrderBill.exceptionWaybills = new Array();
			if(notWaybillrfcNos.length>0){
				for(var j = 0 ;j<notWaybillrfcNos.length;j++){
					notWaybillrfcString +=notWaybillrfcNos[j]+'<br/>';
				}
					Ext.Msg.confirm('提示', '运单号:' + notWaybillrfcString + '有更改单未核审 /未受理不能排单，剩余运单是否继续排单？', function(btn){
						if (btn == 'yes') {
								//移除无效的运单信息
								for ( var i = 0; i < notWaybillrfcNos.length; i++) {
									predeliver.visibleOrderBill.waybillsToArrange.each(function(item, index,length) {
											if (item.waybillNo == notWaybillrfcNos[i]) {
													predeliver.visibleOrderBill.exceptionWaybills.push(item.waybillNo);
													predeliver.visibleOrderBill.waybillsToArrange.removeAtKey(item.id);
											}
										});
								}
								if(predeliver.visibleOrderBill.waybillsToArrange.length == 0){
									Ext.Msg.alert('提示','排除更改单申请未受理的运单后，没有剩余可排的运单,请重新选择!');
									return;
								} else {
										if(waybillNos.length>0){
												for(var j = 0 ;j<waybillNos.length;j++){
													waybillString +=waybillNos[j]+'<br/>';
												}
												Ext.Msg.confirm('提示', '运单号:' + notWaybillrfcString + '有更改单未核审 /未受理不能排单，剩余运单是否继续排单？',function(btn){
													if (btn == 'yes') {
															for ( var i = 0; i < waybillNos.length; i++) {
																	predeliver.visibleOrderBill.waybillsToArrange.each(function(item, index,length) {
																		if (item.waybillNo == waybillNos[i]) {
																				predeliver.visibleOrderBill.exceptionWaybills.push(item.waybillNo);
																				predeliver.visibleOrderBill.waybillsToArrange.removeAtKey(item.id);
																		}
																	});
															}
															if(predeliver.visibleOrderBill.waybillsToArrange.length == 0){
																Ext.Msg.alert('提示','排除更改单申请未处理的运单后，没有剩余可排的运单,请重新选择!');
																return;
															}
															//排单操作
															addWaybillToArrange();
													} else {
														return;
													}
												});

										} else {
											//排单操作
											addWaybillToArrange();
										}
								}
						} else {
							return;
						}
				});
			}
			else if(waybillNos.length>0){
					for(var j = 0 ;j<waybillNos.length;j++){
						waybillString +=waybillNos[j]+'<br/>';
					}

					Ext.Msg.confirm('提示', '运单号:' + waybillString + '有更改单未核审 /未受理不能排单，剩余运单是否继续排单？', function(btn){
						 if (btn == 'yes') {
								for ( var i = 0; i < waybillNos.length; i++) {
										predeliver.visibleOrderBill.waybillsToArrange.each(function(item, index,length) {
											 if (item.waybillNo == waybillNos[i]) {
													predeliver.visibleOrderBill.waybillsToArrange.removeAtKey(item.id);
													predeliver.visibleOrderBill.exceptionWaybills.push(item.waybillNo);
											 }
										});
								}
								if(predeliver.visibleOrderBill.waybillsToArrange.length == 0){
									Ext.Msg.alert('提示','排除更改单申请未处理的运单后，没有剩余可排的运单,请重新选择!');
									return;
								}
								//排单操作
								addWaybillToArrange();
						 } else {
							 return;
						 }
					});
			} else {
					//排单操作
					addWaybillToArrange();
			}
		}
	});
}

//初始化
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout = 600000;
	//Form查询
	predeliver.visibleOrderBill.orderBasicInfoForm = Ext.create('Foss.predeliver.visibleOrderBill.vsOrderBasicInfoForm');

	//地图区域
	predeliver.visibleOrderBill.WaybillToArrangeMap = Ext.create('Foss.predeliver.visibleOrderBill.WaybillToArrangeMap');

	//中间操作区域
	predeliver.visibleOrderBill.ButtonPanelRole = Ext.create('Foss.predeliver.visibleOrderBill.ButtonPanelRole');

	//已排单区域
	predeliver.visibleOrderBill.deliverbillDetailGrid = Ext.create('Foss.predeliver.visibleOrderBill.deliverbillDetailGrid');

	// ---- 重量/体积精度
	predeliver.visibleOrderBill.numberPrecision = 2;
	predeliver.visibleOrderBill.waybillsToArrange = new Ext.util.MixedCollection(); // 选中的待排运单集合
	predeliver.visibleOrderBill.modfiyToArrange = new Ext.util.MixedCollection(); //修改排单件数运单信息
	predeliver.visibleOrderBill.rightToArrange = new Ext.util.MixedCollection(); //已排移除运单信息
	predeliver.visibleOrderBill.waybillsToReturn = new Array(); //运单退回的集合

	predeliver.visibleOrderBill.exceptionWaybills = new Array(); //异常运单(包含更改单未受理的各种情况)

	predeliver.visibleOrderBill.isOrSave = false;

	//大面板定义-开始
	Ext.define('FOSS.predeliver.visibleOrderBill.ContainerPanel',{
	  extend: 'Ext.panel.Panel',
	  bodyCls : 'autoHeight',
	  cls : 'autoHeight',
		height: 800,
	  layout: 'hbox', // column
	  anchor : '100%',
	  margin : '0 0 0 0',
	  padding: '0 0 0 0',
	  frame: true,
	  items:[predeliver.visibleOrderBill.WaybillToArrangeMap,
			 predeliver.visibleOrderBill.ButtonPanelRole,
			 predeliver.visibleOrderBill.deliverbillDetailGrid
			 ]
	});
	//大面板定义-结束

	//大面板区域
	predeliver.visibleOrderBill.ContainerPanel = Ext.create('FOSS.predeliver.visibleOrderBill.ContainerPanel');

	//保存、提交按钮
	predeliver.visibleOrderBill.SubmitForm = Ext.create('Foss.predeliver.visibleOrderBill.SubmitForm');

	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-visibleOrderBillIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		defaults : {
			margin : '0 0 0 0',
		},
		getDriverName : function(vehicleNo) {
			 if (vehicleNo != '' && vehicleNo != null) {
					Ext.Ajax.request({
						url : predeliver.realPath('queryVehicleByVehicleNo.action'),
						params : {
							'deliverbillVo.deliverbillDto.vehicleNo' : vehicleNo
						},
						success : function(response) {
								var result = Ext.decode(response.responseText);

								var driverNameField = predeliver.visibleOrderBill.ButtonPanelRole.getForm().findField('driverName');

								var vehicle = result.deliverbillVo.vehicleAssociationDto;

								if (vehicle != null) {
						

									/*
									 * SR9 1.
									 * 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
									 * 2.
									 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
									 * 3.
									 * 当排班和PDA绑定同时存在时，以PDA绑定为准)
									 */
									var driver = result.deliverbillVo.driverDto;

									if (driver != null) {
										// 更新driverName的value和rawValue
										driverNameField.setValue(driver.empCode);
										predeliver.visibleOrderBill.ButtonPanelRole.down('textfield[name=driverCode]').setValue(driver.empCode);
										driverNameField.setRawValue(driver.empName);

										if (driver.pdaSigned == 'Y') {
											// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
											//driverNameField.setReadOnly(true);
										} else {
											// 司机设置为可选择
											//driverNameField.setReadOnly(false);
										}

									} else {
										// 司机设置为可选择
										//driverNameField.setReadOnly(false);
									}

								} else {
									Ext.ux.Toast.msg(
											predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											predeliver.visibleOrderBill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
											'error',
											4000);
									}
									//driverNameField.setReadOnly(false);
						}
				});
			 }
		},
		getArrangeDeliverbillWindow : function() {
			if (predeliver.visibleOrderBill.arrangeDeliverbillWindow == null) {
				predeliver.visibleOrderBill.arrangeDeliverbillWindow = Ext
				.create('Foss.predeliver.visibleOrderBill.ArrangeDeliverbillWindow');
			}
			return predeliver.visibleOrderBill.arrangeDeliverbillWindow;
		},
		//刷新中间panel的车辆相关信息
		refreshMiddlePanelInfo: function(deliverbill) {
			var midPanel = predeliver.visibleOrderBill.ButtonPanelRole;
			var vehicleNo = deliverbill.get('vehicleNo');
			if (vehicleNo) {
				midPanel.getForm().findField('vehicleNo').setValue(vehicleNo);
			}
			if (deliverbill.get('driverName')) {
				midPanel.getForm().findField('driverName').setValue(deliverbill.get('driverName'));
			}
			if (deliverbill.get('driverCode')) {
				midPanel.getForm().findField('driverCode').setValue(deliverbill.get('driverCode'));
			}
			if (deliverbill.get('deliverType')) {
				midPanel.getForm().findField('deliverType').setValue(deliverbill.get('deliverType'));
			}
			if (deliverbill.get('carTaskinfo')) {
				midPanel.down('combobox[name=carTaskinfo]').setValue(deliverbill.get('carTaskinfo'));
			}
			if (deliverbill.get('frequencyno')) {
				midPanel.getForm().findField('frequencyNo').setValue(deliverbill.get('frequencyno'));
			}
			if (deliverbill.get('preCartaskTime')) {
				midPanel.down('timefield[name=preCartaskTime]').setValue(deliverbill.get('preCartaskTime'));
			}
			if (deliverbill.get('takeGoodsDeptcode')) {
				midPanel.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setValue(deliverbill.get('takeGoodsDeptcode'));
				midPanel.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setRawValue(deliverbill.get('takeGoodsDeptname'));
			}
			if (deliverbill.get('expectedbringvolume')) {
				midPanel.getForm().findField('expectedBringVolume').setValue(deliverbill.get('expectedbringvolume'));
			}
			if (deliverbill.get('transferDeptcode')) {
				midPanel.down('commonsaledepartmentselector[name=transferDeptcode]').setValue(deliverbill.get('transferDeptcode'));
				midPanel.down('commonsaledepartmentselector[name=transferDeptcode]').setRawValue(deliverbill.get('transferDeptname'));
			}

			//如果已排单里有记录(相同送货日期)，则取一条记录的送货日期，如果没有则取查询条件选择的
			var deliverDate = null;
			var gridStore = predeliver.visibleOrderBill.deliverbillDetailGrid.getStore();
			if (gridStore.getCount() > 0) {
				if (gridStore.getAt(0).data.estimatedDeliverTime) {
					  deliverDate = Ext.util.Format.date(gridStore.getAt(0).data.estimatedDeliverTime,'Y-m-d');
				}
			} else {
				var deliverDateBase = predeliver.visibleOrderBill.orderBasicInfoForm.getForm().findField('deliverDate').getValue();
				if (deliverDateBase) {
						deliverDate = Ext.util.Format.date(deliverDateBase,'Y-m-d');
				}
			}
			//根据车牌号、送货日期重新加载班次、带货信息
			if (predeliver.visibleOrderBill.vehicleNoTruckType == '外请车') {
				//外请车不查询排班表
        //如果是外请车，则设置出车任务"送+接"，预计出车时间为"00:00"。反之，如果已天出车任务、预计出车时间再选择车辆未外请车不考虑
        midPanel.getForm().findField('carTaskinfo').setValue('1');
        midPanel.getForm().findField('preCartaskTime').setValue('00:00');
			} else {
					if (vehicleNo != null && deliverDate != null) {
						Ext.Ajax.request({
								url : predeliver.realPath('visibleVehicleSchedue.action'),
								async : false,
								jsonData:{
									'vo' : {
										'vehilceNo': vehicleNo,
										'deliverDate': deliverDate,
										'deliverbill' : {'id' : predeliver.visibleOrderBill.deliverbillId}
									}
								},
								success : function(response) {
										var result = Ext.decode(response.responseText);
										//车辆班次
										if (result.vo.frequencyNo) {
											midPanel.down('combobox[name=frequencyNo]').setValue(result.vo.frequencyNo);
										}
										//车辆带货(方)
										if (result.vo.expectedBringVolume) {
											midPanel.down('textfield[name=expectedBringVolume]').setValue(result.vo.expectedBringVolume);
										}
								},
								exception : function(response) {}
						 });
					}
			}
		},
		items : [
			predeliver.visibleOrderBill.orderBasicInfoForm,
			predeliver.visibleOrderBill.ContainerPanel,
			predeliver.visibleOrderBill.SubmitForm
			/*{
				border : false,
				columnWidth : 1,
				layout : 'column',
				items : [{
							border : false,
							columnWidth : .82,
							html : '&nbsp;'
						},{

							xtype : 'button',
							columnWidth : .08,
							cls : 'yellow_button',
							text : '保存',//暂时写死，留待i18n处理
						},{
							border : false,
							columnWidth : .02,
							html : '&nbsp;'
						},{
							columnWidth : .08,
							xtype : 'button',
							cls : 'yellow_button',
							text : '提交'
						}]
			} */
		],
		renderTo : 'T_predeliver-visibleOrderBillIndex-body'
	});
});