// 定义数据字典model
predeliver.editDeliverbillNew.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);
	return date;
};

/**
 * 分区查看Model
 * @Fields regionalName 区域名称
 * @Fields regionalCode 区域编码
 * @Fields regionalVirtaulCode 区域虚拟编码
 * @Fields handoverBillCount 已交单总票数
 * @Fields goodsWeightSum  重量总和
 * @Fields goodsVolumeSum 体积总和
 * @Fields goodsQtyTotalSum 件数总和
 * @Fields vehicleNo 定区车
 * @Fields truckModelValue 车型名
 * @Fields frequencyNo 班次
 * @Fields expectedBringVolume 预计带货方数（方/F）
 * @Fields deliveryDate 送货日期
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.PartitionViewInfoModel', {
	extend : 'Ext.data.Model',
	fields : [
	      	'regionalName', 'regionalCode', 'regionalVirtaulCode', 
	    	'handoverBillCount', 'goodsWeightSum', 'goodsVolumeSum', 
	    	'goodsQtyTotalSum', 'vehicleNo', 'truckModelValue', 
	    	'frequencyNo', 'expectedBringVolume', 'deliveryDate'
	          ]
});

/**
 * 分区查看Store
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.PartitionViewInfoStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.editDeliverbillNewIndex.PartitionViewInfoModel',
	pageSize : 20,
	proxy: {
        type : 'ajax',
        url :  predeliver.realPath('queryPartitionViewInfoByBigZone.action'), 
        actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'partitionViewInfos',
            totalProperty : 'totalCount'
        }
    }
});


//定义派车类型集合store
Ext.define('Foss.predeliver.store.DeliverTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'NOMAL','valueName':'正常'},// '正常'
			{'valueCode':'SPECIAL','valueName':'专车'},//'专车'
			{'valueCode':'MANNED','valueName':'带人'}//'带人'
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

// 获得当前部门是否营业部
var dept = FossUserContext.getCurrentDept().salesDepartment;
(function(){
		// 获得当前组织对应的车队
		Ext.Ajax.request({
			url : predeliver.realPath('querySuperOrgNew.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					fleetCode : json.fleetCode
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
			}
		});
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

//货物状态集合
Ext.define('Foss.predeliver.store.GoodsStatusStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':' ','valueName':'全部'},// '全部'
			{'valueCode':'ONTHEWAY','valueName':'预计到达'},//'预计到达'
			{'valueCode':'ARRIVED','valueName':'已到达'},//'已到达'
			{'valueCode':'IN_STOCK','valueName':'库存中'}//'库存中'
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

Ext.define('Foss.predeliver.editDeliverbillNew.deliverbillDetailGridModel_P', {
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
        },{
            name : 'consigneeAddress', // 送货地址
            type : 'string'
        }, {
            name : 'regionName', // 预处理建议.送货小区
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
            name : 'actualSmallzoneName', //小区名称
            type : 'string'
        }, {
            name : 'actualSmallzoneCode', //小区代码
            type : 'string'
        }]
});

/*----------------------------------分区查看开始-----------------------------------------------------*/
predeliver.editDeliverbillNew.vehicleNoTruckType_PartitionedView=null;//验证是否外请车
predeliver.editDeliverbillNew.virtualCade=null;//获取大区编码
predeliver.editDeliverbillNew.waybillDetailId_PartitionedView=null;//保存分区查看派送单Id
predeliver.editDeliverbillNew.checkAll=null;
predeliver.editDeliverbillNew.Flag=false;			

// 查询运单form——分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.QueryWaybillForm_PartitionedView',{
	extend : 'Ext.form.Panel',
	title : '查询排单运单',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnWidth : 1,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 80
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		xtype: 'datefield',
		editable : false,
		name: 'deliveryDate',
		format : 'Y-m-d',
		fieldLabel: '送货日期',	//暂时写死，留待i18n处理
		labelWidth : 80,
		value:Ext.Date.format(new Date(),'Y-m-d'),
		columnWidth:.25
	}, {
		xtype:'combo',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
	    name : 'productCode',
		columnWidth : .25,
		store : Ext.create('Foss.pkp.ProductStore'),
		fieldLabel : '运输性质'//暂时写死，留待i18n处理
	}, {
		xtype : 'combobox',
		fieldLabel : '货物状态',//暂时写死，留待i18n处理
		columnWidth : .25,
		displayField : 'valueName',
		name : 'goodStatus',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		value : ' ',
		store : Ext.create('Foss.predeliver.store.GoodsStatusStore')
	}, {
		name : 'vehicleNo',
		xtype : 'commontruckselector', 
		queryAllFlag : true,
		loopOrgCode : predeliver.fleetCode,
		fieldLabel : '车牌号',//暂时写死，留待i18n处理
		columnWidth : .25
	}, {
		xtype : 'commonmotorcadeselector',
		fieldLabel : '所属车队组',//暂时写死，留待i18n处理
		columnWidth : .25,
		name : 'fleetGroup',
		xtype : 'commonmotorcadeselector',
		loopOrgCodes : predeliver.fleetCode
	}, {
		xtype : 'commonbigregionsdeselector',
		fieldLabel : '送货大区',//暂时写死，留待i18n处理
		columnWidth : .25,
		name : 'bigZoneCodes',
		queryAllFlag : true,
		loopOrgCode : predeliver.fleetCode
	}, {
		xtype : 'commonmultismallzoneselector',
		fieldLabel : '送货小区',//暂时写死，留待i18n处理
		name : 'smallZoneCodes',
		columnWidth : .25,
		regionType : 'DE',
		queryAllFlag : true,
		loopOrgCode : predeliver.fleetCode
	}, {
		xtype : 'container',
		columnWidth : .25,
		layout : 'hbox',
		defaults : {
			margin : '5 0 0 5'
		},
		items : [ {
			xtype : 'button',
			width : 80,
			text : '重置',//暂时写死，留待i18n处理
			handler : function(btn) {
				var from=btn.up('form').getForm();
				from.reset();
			}
		}, {
			xtype : 'button',
			cls : 'yellow_button',
			width : 80,
			text : '查询',
			handler : function(btn) {	
				var form = btn.up('form').getForm();
				if (!form.isValid()) {
					return;
				}
				var values = form.getValues();
				
				for (var attr in values){
					var keyName = 'partitionViewInfoVo.' + attr;
					values[keyName] = values[attr];
					delete values[attr];
				}
				
				var grid = Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView');
				var store = grid.getStore();
				store.proxy.extraParams = values;
				store.load({
				    scope: this,
				    callback: function(records, operation, success) {
				    	var handoverBillCounts = 0; 
						var goodsWeightSums = 0;
					    var goodsVolumeSums = 0;
					    Ext.Array.each(records, function(record, index, countriesItSelf) {
					    	handoverBillCounts += record.get('handoverBillCount');
					    	goodsWeightSums +=  record.get('goodsWeightSum');
					    	goodsVolumeSums += record.get('goodsVolumeSum');
					    });
					    grid.down('displayfield[name=totalGoodsQty]').setValue(handoverBillCounts);
					    grid.down('displayfield[name=totalGoodsWeight]').setValue(Ext.Number.toFixed(goodsWeightSums, 1));
					    grid.down('displayfield[name=totalGoodsVolume]').setValue(Ext.Number.toFixed(goodsVolumeSums, 2));
					    Ext.getCmp('PartitionedViewHaveVotes').reset();
						Ext.getCmp('PartitionedViewHaveWeight').reset();
						Ext.getCmp('PartitionedViewHaveVolume').reset();
				    }
				});
				grid.down('standardpaging').bind(store);
			}
		}]
	}]
});


//分区查看 批量右移运单
predeliver.editDeliverbillNew.addWaybillToArrange_PartitionedView =function(waybillNos,id,notWaybillrfcString){
	//调用AJAX
	Ext.Ajax.request({
		url:predeliver.realPath('waybillDetailAddWaybillToArrangeNew.action'),
		params: {
		    'partitionedviewVo.deliverbill.id':  id,
			'partitionedviewVo.waybillNos' : waybillNos,
			'partitionedviewVo.notwaybillNos' : notWaybillrfcString
		},
		success: function(response){	
			//刷新已分区查询结果和已排单运单且清空已选
			predeliver.editDeliverbillNew.waybillsToArrange_BigZone.clear(); 
			predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.clear();
			predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.getStore().load({
				scope: this,
				callback: function(records, operation, success) {
					var handoverBillCounts = 0; 
					var goodsWeightSums = 0;
					var goodsVolumeSums = 0;
					Ext.Array.each(records, function(record, index, countriesItSelf) {
						handoverBillCounts += record.get('handoverBillCount');
						goodsWeightSums +=  record.get('goodsWeightSum');
						goodsVolumeSums += record.get('goodsVolumeSum');
					});
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsQty]').setValue(handoverBillCounts);
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsWeight]').setValue(Ext.Number.toFixed(goodsWeightSums, 1));
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsVolume]').setValue(Ext.Number.toFixed(goodsVolumeSums, 2));
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsQty]').setValue('0');
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsWeight]').setValue('0.0');
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsVolume]').setValue('0.00');
				}
			});
			predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().load();//刷新已排单结果
			
			//给已排单运单汇总信息赋值
			var result = Ext.decode(response.responseText);
			Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue(result.partitionedviewVo.deliverbill.loadingRate);//设置装载率
			Ext.getCmp('predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.waybillQtyTotal);//设置总票数
			Ext.getCmp('predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.goodsQtyTotal);//设置总件数
			Ext.getCmp('predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.weightTotal).toFixed(1));//设置总重量
			Ext.getCmp('predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.volumeTotal).toFixed(2));//设置总体积
			Ext.getCmp('predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.payAmountTotal).toFixed(2));//设置到付金额
			Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'success', 2500);
		},
		exception: function(response){
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示', json.message, 'error', 2500);
		}
	});

	predeliver.editDeliverbillNew.Flag=false;
}

//定义已排运单的Store—分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.DeliverbillStore',{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.editDeliverbillNew.deliverbillDetailGridModel_P',
					pageSize : 20,
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					//定义一个代理对象
					proxy: {
						//代理的类型为内存代理
						type : 'ajax',
						actionMethods : 'POST',
						url:  predeliver.realPath('waybillDetailQueryDetailListNew.action'),
						//定义一个读取器
						reader: {
							//以JSON的方式读取
							type: 'json',
							//定义读取JSON数据的根对象
							root: 'partitionedviewVo.waybillDetailArrageDtos',
							totalProperty : 'totalCount',
							successProperty : 'success'
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryWaybillForm = predeliver.ButtonPanelRole_PartitionedView;
							if (queryWaybillForm != null) {
								var queryParams = queryWaybillForm.getValues();
								var params = {
									'partitionedviewVo.deliverbill.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
								};
								Ext.apply(operation,{
									params : params
								});
							}
							
							
						}
					}
});


//定义已分区查询结果的表格——分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView',{
	extend : 'Ext.grid.Panel',
	title : '已分区查询结果',
	frame :  true,
	columnLines : true,
	id : 'predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView',
	width : (document.body.scrollWidth-201)/2,
	height : 670,
	enableColumnResize : false,
	autoScroll : true,
	store : Ext.create('Foss.predeliver.editDeliverbillNewIndex.PartitionViewInfoStore'),
	selModel: {
        mode: "SIMPLE",     //"SINGLE"/"SIMPLE"/"MULTI"
        checkOnly: true     //只能通过checkbox选择
    },
    selType: "checkboxmodel",
	columns : [ {
		text : '送货区域',
		width : 160,
		dataIndex : 'regionalName'
	}, {
		text : '总票数',
		width : 80,
		dataIndex : 'handoverBillCount'
	}, {
		text : '重量',
		width : 80,
		dataIndex : 'goodsWeightSum'
	}, {
		text : '体积',
		width : 80,
		dataIndex : 'goodsVolumeSum'
	}, {
		text : '件数',
		width : 80,
		dataIndex : 'goodsQtyTotalSum'
	}, {
		text : '定区车',
		dataIndex : 'vehicleNo'
	}, {
		text : '车型',
		width : 80,
		dataIndex : 'truckModelValue'
	}, {
		text : '班次',
		width : 60,
		dataIndex : 'frequencyNo'
	}, {
		text : '带货',
		dataIndex : 'expectedBringVolume'
	} ],
	dockedItems : [ {
		xtype : 'toolbar',
		layout : 'column',
		dock: 'bottom',
		items : [ {
			xtype : 'displayfield',
			fieldLabel : '总票数',
			name : 'totalGoodsQty',
			id : 'PartitionedViewTotalVotes',
			allowBlank:true,
			labelWidth : 60,
			columnWidth : .3
		}, {
			xtype:'displayfield',
			fieldLabel: '总重量(千克)',
			id : 'PartitionedViewTotalWeight',
			name:'totalGoodsWeight',
			allowBlank:true,
			labelWidth : 90,
			columnWidth : .4
		}, {
			xtype:'displayfield',
			fieldLabel: '总体积(方)',
			name:'totalGoodsVolume',
			id : 'PartitionedViewTotalVolume',
			allowBlank:true,
			labelWidth : 90,
			columnWidth : .3
		}, {
			xtype:'displayfield',
			fieldLabel: '已选票数',
			allowBlank:true,
			value : 0,
			id:'PartitionedViewHaveVotes',
			name:'selectGoodsQty',
			labelWidth : 70,
			columnWidth : .3
		}, {
			xtype:'displayfield',
			fieldLabel: '已选重量(千克)',
			allowBlank:true,
			id : 'PartitionedViewHaveWeight',
			name:'selectGoodsWeight',
			labelWidth : 100,
			value : 0,
			columnWidth : .4
		}, {
			xtype:'displayfield',
			fieldLabel: '已选体积(方)',
			allowBlank:true,
			id : 'PartitionedViewHaveVolume',
			name:'selectGoodsVolume',
			labelWidth : 90,
			value : 0,
			columnWidth : .3
		} ]
	} ],
	bbar : Ext.create('Deppon.StandardPaging',{
		displayInfo: true,
		pageSize: 20,
		plugins : Ext.create('Deppon.ux.PageSizePlugin', {
			sizeList : [['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500]]
		})
	}),
	plugins : [ { 
		header : true,
		ptype : 'rowexpander',
		expandOnDblClick : false,
		rowBodyTpl: [  
		    '<div id="{regionalVirtaulCode}">',  
		     '</div>'  
		] 
	} ],
	listeners : {
		afterrender : function (grid) {
			var view  = grid.view;
			view.on('expandBody', function (rowNode, record, expandRow, eOpts) {  
				var store = grid.getStore();
				if (Ext.getCmp(record.get('regionalCode'))) {
					Ext.ComponentManager.unregister(Ext.getCmp(record.get('regionalCode')));
					var parent = document.getElementById(record.get('regionalVirtaulCode'));  
					var child = parent.firstChild;  
					while (child) {  
					   child.parentNode.removeChild(child);  
					   child = child.nextSibling;  
					}  
				}
				var innerGrid = Ext.create('Foss.predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView_SmallZone', {
					id : record.get('regionalCode'),
					store : Ext.create('Foss.predeliver.editDeliverbillNewIndex.PartitionViewInfoStore',{
						singleton : false
					}),
					listeners : {
						select : function(rowModel, innerRecord, index, eOpts ) {
							if (innerGrid.getSelectionModel().getCount() == innerGrid.getStore().getCount()) {
								grid.getSelectionModel().select(record,true,true);
							} 
							predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.add(innerRecord.data.regionalCode,
									innerRecord.data);
							
							Ext.getCmp('PartitionedViewHaveVotes').setValue(Ext.getCmp('PartitionedViewHaveVotes').getValue()*1+innerRecord.get('handoverBillCount')*1);
							
							var selectGoodsWeightSum = (Ext.getCmp('PartitionedViewHaveWeight').getValue()*1+innerRecord.get('goodsWeightSum')*1).toFixed(1);
							
							var selectGoodsVolumeSum = (Ext.getCmp('PartitionedViewHaveVolume').getValue()*1+innerRecord.get('goodsVolumeSum')*1).toFixed(2);
							
							Ext.getCmp('PartitionedViewHaveWeight').setValue((selectGoodsWeightSum < 0 ? 0 : selectGoodsWeightSum));
							Ext.getCmp('PartitionedViewHaveVolume').setValue((selectGoodsVolumeSum < 0 ? 0 : selectGoodsVolumeSum));
						},
						deselect : function (rowModel, innerRecord, index, eOpts) {
							grid.getSelectionModel().deselect(record, true, true);
							predeliver.editDeliverbillNew.waybillsToArrange_SmallZone
									.removeAtKey(innerRecord.data.regionalCode);
							predeliver.editDeliverbillNew.waybillsToArrange_BigZone
									.removeAtKey(record.data.regionalCode);
							var selectionModel = innerGrid.getSelectionModel();
							if (selectionModel.hasSelection()) {
								Ext.Array.each(selectionModel.getSelection(), function(item, index, countriesItSelf){
									predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.add(item.data.regionalCode,
											item.data);
								});
							}							
							Ext.getCmp('PartitionedViewHaveVotes').setValue(Ext.getCmp('PartitionedViewHaveVotes').getValue()*1-innerRecord.get('handoverBillCount')*1);
							
							var selectGoodsWeightSum = (Ext.getCmp('PartitionedViewHaveWeight').getValue()*1-innerRecord.get('goodsWeightSum')*1).toFixed(1);
							
							var selectGoodsVolumeSum = (Ext.getCmp('PartitionedViewHaveVolume').getValue()*1-innerRecord.get('goodsVolumeSum')*1).toFixed(2);
							Ext.getCmp('PartitionedViewHaveWeight').setValue((selectGoodsWeightSum < 0 ? 0 : selectGoodsWeightSum));
							Ext.getCmp('PartitionedViewHaveVolume').setValue((selectGoodsVolumeSum < 0 ? 0 : selectGoodsVolumeSum));
						}
					},
					renderTo: record.get('regionalVirtaulCode')
				});
	
				var innerStore = innerGrid.getStore();
				
				var extraParams = store.proxy.extraParams;
				extraParams['partitionViewInfoVo.regionalVirtaulCode'] = record.get('regionalVirtaulCode');
				innerStore.proxy.extraParams = extraParams;
				innerStore.getProxy().url = predeliver.realPath('queryPartitionViewInfoBySmallZone.action'); 
				innerStore.load({
				    scope: this,
				    callback: function(records, operation, success) {
				    	if (grid.getSelectionModel().isSelected(record)) {
				    		innerGrid.getSelectionModel().selectAll(true);
				    	}
				    }
				});
			 });
			
			view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {  
				Ext.ComponentManager.unregister(Ext.getCmp(record.get('regionalCode')));	
				var parent = document.getElementById(record.get('regionalVirtaulCode'));  
				var child = parent.firstChild;  
				while (child) {  
				   child.parentNode.removeChild(child);  
				   child = child.nextSibling;  
				}  
			 });
		},
		select : function(rowModel, record, index, eOpts) {
			var innerGrid = Ext.getCmp(record.get('regionalCode'));
			var selectGoodsQty = 0,
			selectGoodsWeight = 0,selectGoodsVolume = 0;
			if (innerGrid) {
				var selectionModel = innerGrid.getSelectionModel();
				if (selectionModel.hasSelection()) {
					Ext.Array.each(selectionModel.getSelection(), function(item, index, countriesItSelf){
						selectGoodsQty += item.get('handoverBillCount');
						selectGoodsWeight +=  item.get('goodsWeightSum');
						selectGoodsVolume += item.get('goodsVolumeSum');
					});
				}
				selectionModel.selectAll(true);
			}
			
			predeliver.editDeliverbillNew.waybillsToArrange_BigZone.add(record.data.regionalCode,
					record.data);
			Ext.getCmp('PartitionedViewHaveVotes').setValue(Ext.getCmp('PartitionedViewHaveVotes').getValue()*1+record.get('handoverBillCount')*1 - selectGoodsQty);
			
			var selectGoodsWeightSum = (Ext.getCmp('PartitionedViewHaveWeight').getValue()*1+record.get('goodsWeightSum')*1 - selectGoodsWeight).toFixed(1);
			
			var selectGoodsVolumeSum = (Ext.getCmp('PartitionedViewHaveVolume').getValue()*1+record.get('goodsVolumeSum')*1 - selectGoodsVolume).toFixed(2);
			
			Ext.getCmp('PartitionedViewHaveWeight').setValue((selectGoodsWeightSum < 0 ? 0 : selectGoodsWeightSum));
			Ext.getCmp('PartitionedViewHaveVolume').setValue((selectGoodsVolumeSum < 0 ? 0 : selectGoodsVolumeSum));
		},
		deselect :  function(rowModel, record, index, eOpts) {
			var innerGrid = Ext.getCmp(record.get('regionalCode'));
			if (innerGrid) {
				var selectionModel = innerGrid.getSelectionModel();
				selectionModel.deselectAll(true);
			}
			predeliver.editDeliverbillNew.waybillsToArrange_BigZone
					.removeAtKey(record.data.regionalCode);
			Ext.getCmp('PartitionedViewHaveVotes').setValue(Ext.getCmp('PartitionedViewHaveVotes').getValue()*1-record.get('handoverBillCount')*1 );
			
			var selectGoodsWeightSum = (Ext.getCmp('PartitionedViewHaveWeight').getValue()*1-record.get('goodsWeightSum')*1 ).toFixed(1);
			
			var selectGoodsVolumeSum = (Ext.getCmp('PartitionedViewHaveVolume').getValue()*1-record.get('goodsVolumeSum')*1 ).toFixed(2);
			
			Ext.getCmp('PartitionedViewHaveWeight').setValue((selectGoodsWeightSum < 0 ? 0 : selectGoodsWeightSum));
			Ext.getCmp('PartitionedViewHaveVolume').setValue((selectGoodsVolumeSum < 0 ? 0 : selectGoodsVolumeSum));
		}
	}
});

//已分区查询结果——小区/分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView_SmallZone',{
	extend : 'Ext.grid.Panel',
	hideHeaders : true,
	columnLines : true,
	autoScroll : false,
	padding : '0 0 0 22',
	selModel: {
        mode: "SIMPLE",     //"SINGLE"/"SIMPLE"/"MULTI"
        checkOnly: true     //只能通过checkbox选择
    },
    selType: "checkboxmodel",
	columns : [ {
		text : '送货区域',
		width : 160,
		dataIndex : 'regionalName'
	}, {
		text : '总票数',
		width : 80,
		dataIndex : 'handoverBillCount'
	}, {
		text : '重量',
		width : 80,
		dataIndex : 'goodsWeightSum'
	}, {
		text : '体积',
		width : 80,
		dataIndex : 'goodsVolumeSum'
	}, {
		text : '件数',
		width : 80,
		dataIndex : 'goodsQtyTotalSum'
	}, {
		text : '定区车',
		dataIndex : 'vehicleNo'
	}, {
		text : '车型',
		width : 80,
		dataIndex : 'truckModelValue'
	}, {
		text : '班次',
		width : 60,
		dataIndex : 'frequencyNo'
	}, {
		text : '带货',
		dataIndex : 'expectedBringVolume'
	} ]
});
	
//定义已排单运单的表格——分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView', {
		extend : 'Ext.grid.Panel',
		frame : true,
		//title : '已排单运单',
		id :'predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView',
		sortableColumns : false,
		height : 670,
		width : (document.body.scrollWidth-201)/2,
		autoScroll : true,
		margin : '10 0 5 -2',
		columnLines : true,
		cellEditor : null,		
		selModel: {
			injectCheckbox: 0,
			mode: "SIMPLE"
		},
		selType: 'checkboxmodel',
		tbar : [{
			xtype: 'label',
			text: '已排单运单 ',
			width : 110,
			margin : '10 0 0 0',
			style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:5px solid #3d74b7;'
			},
			{
							xtype: 'button',
							text: '排序',
              width: 35,
							plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
								seconds: 3
							}),
							//cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
							handler: function(button) {
									if (!Ext.isEmpty(predeliver.editDeliverbillNew.waybillDetailId_PartitionedView) && predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().getCount() > 0) {
											Ext.Ajax.request({
													url : predeliver.realPath('visibleIfExistsNoCoord.action'),
													params : {
														'deliverbillVo.deliverbill.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
													},
													success : function(response) {
													  //调用可视化界面
                            Ext.Ajax.request({
                              url : predeliver.realPath('visiblebillInfo.action'),
                              params :{
                                'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
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
                                markWin.down('grid').getStore().setDeliverbillId(predeliver.editDeliverbillNew.waybillDetailId_PartitionedView);
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
			},'->',{xtype:'panel',border: 1,width:25,height:20,cls:'predeliver-beforeNoticeIndex-row-purole'},
			{xtype:'label',text:'无坐标运单'}/*{
			text : '刷新',
				handler : function (btn) {
					predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
				}
		}*/],
		addWaybillToArrange : function(waybillNos,notWaybillrfcString) {
			// 排单前需验证车辆信息
			if (predeliver.editDeliverbillNew.waybillDetailId_PartitionedView == "" || predeliver.editDeliverbillNew.waybillDetailId_PartitionedView == null) {
				var deliverbillBasicInfo = predeliver.ButtonPanelRole_PartitionedView.getValues();
				// 保存派送单
				Ext.Ajax.request({
					url : predeliver.realPath('waybillDetailSaveDeliverbillNew.action'),
					jsonData : {									
						'partitionedviewVo' : {
							'deliverbill' : {
								'deliverbillNo' : deliverbillBasicInfo.delivebillNo,
								'vehicleNo' : deliverbillBasicInfo.vehicleNo,
								'delStatus' :"",
								'driverCode' : deliverbillBasicInfo.driverCode,
								'deliverType' : deliverbillBasicInfo.deliverType,
								'driverName' : predeliver.ButtonPanelRole_PartitionedView.getForm().findField("driverName").rawValue,
								'carTaskinfo': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=carTaskinfo]').getValue(),
								'frequencyno': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=frequencyNo]').getValue(),
								'preCartaskTime': predeliver.ButtonPanelRole_PartitionedView.down('timefield[name=preCartaskTime]').getRawValue(),
								'takeGoodsDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
								'takeGoodsDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
								'expectedbringvolume': predeliver.ButtonPanelRole_PartitionedView.down('textfield[name=expectedBringVolume]').getValue(),
								'transferDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
								'transferDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						predeliver.editDeliverbillNew.waybillDetailId_PartitionedView = result.partitionedviewVo.deliverbill.id;
						predeliver.editDeliverbillNew.addWaybillToArrange_PartitionedView(waybillNos,predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,notWaybillrfcString);
					},
					exception : function(response) {
						predeliver.editDeliverbillNew.Flag=false;
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
					}
				});
			}else {
				predeliver.editDeliverbillNew.addWaybillToArrange_PartitionedView(waybillNos,predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,notWaybillrfcString);
			}
		},
		deleteDeliverbillDetails : function(deliverbillDetailIds) {
			Ext.Ajax.request({
				url : predeliver
						.realPath('deleteDeliverbillDetails.action'),
				params : {
					'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
					'deliverbillVo.deliverbillDetailIds' : deliverbillDetailIds
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.getStore().load({
						scope: this,
						callback: function(records, operation, success) {
							var handoverBillCounts = 0; 
							var goodsWeightSums = 0;
							var goodsVolumeSums = 0;
							Ext.Array.each(records, function(record, index, countriesItSelf) {
								handoverBillCounts += record.get('handoverBillCount');
								goodsWeightSums +=  record.get('goodsWeightSum');
								goodsVolumeSums += record.get('goodsVolumeSum');
							});
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsQty]').setValue(handoverBillCounts);
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsWeight]').setValue(Ext.Number.toFixed(goodsWeightSums, 1));
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsVolume]').setValue(Ext.Number.toFixed(goodsVolumeSums, 2));
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsQty]').setValue('0');
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsWeight]').setValue('0.0');
							predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsVolume]').setValue('0.00');
						}
					});
					predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().load();
					Ext.Ajax.request({
						url : predeliver.realPath('queryDeliverbill.action'),
						jsonData : {									
							'deliverbillVo' : {
								'deliverbillDto' : {
									'id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
								}
							}
						},
						success : function(response) {
							//给已派单运单汇总信息赋值
							var result = Ext.decode(response.responseText);
							Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue(result.deliverbillVo.deliverbill.loadingRate);//设置装载率
							Ext.getCmp('predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView').setValue(result.deliverbillVo.deliverbill.waybillQtyTotal);//设置总票数
							Ext.getCmp('predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView').setValue(result.deliverbillVo.deliverbill.goodsQtyTotal);//设置总件数
							Ext.getCmp('predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView').setValue(result.deliverbillVo.deliverbill.weightTotal);//设置总重量
							Ext.getCmp('predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView').setValue(result.deliverbillVo.deliverbill.volumeTotal);//设置总体积
							Ext.getCmp('predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView').setValue(result.deliverbillVo.deliverbill.payAmountTotal);//设置到付金额
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 2500);
						}
					});
				},
				exception: function(response){
					var json = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
				}
			});
		},
		dockedItems : [{
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
						labelWidth : 60,
						id : 'predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView',
						name:'totalTicket',
						value: '0',
						columnWidth:.3,
						fieldLabel: '总票数'
				 },{
						xtype:'displayfield',
						allowBlank:true,
						name:'totalCount',
						value: '0',
						labelWidth : 60,
						id : 'predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView',
						columnWidth:.3,
						fieldLabel: '总件数'
				},{
						xtype:'displayfield',
						columnWidth:.3,
						labelWidth : 100,
						value: '0',
						id : 'predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView',
						name : 'totalWeight',
						fieldLabel: '总重量(KG)'
				},{
						xtype:'displayfield',
						allowBlank:true,
						labelWidth : 80,
						name:'totalQty',
						value: '0',
						columnWidth:.5,
						id : 'predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView',
						name : 'totalVolumn',
						fieldLabel: '总体积(m³)'
				},{
						xtype:'displayfield',
						allowBlank:true,
						name:'totalWeight',
						value: '0',
						labelWidth : 100,
						columnWidth:.5,
						id : 'predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView',
						name : 'totalPayAmount',
						fieldLabel: '到付金额(元)'
				},{
						xtype:'displayfield',
						columnWidth:.5,
						value: '0/0',
						width: 200,
						labelWidth : 120,
						name: 'nominalRate',
						id : 'predeliver.editDeliverbillNewIndex.nominalRate_PartitionedView',
						fieldLabel: '净空(方)/载重(吨)'
				},{
						xtype:'displayfield',
						columnWidth:.5,
						width: 200,
						value: '0%/0%',
						labelWidth : 125,
						id : 'predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView',
						name: 'loadRate',
						style: 'marginTop:1px',
						fieldLabel: '装载率(体积/重量)'
				}
			]
		}],
		columns : [/*{
			xtype : 'actioncolumn',
			text : '操作',
			width : 48,
			layout : 'column',
			align : 'center',
			items : [
					/*{
						iconCls : 'deppon_icons_up',
						tooltip : '上移',
						columnWidth : .50,
						handler : function(grid, rowIndex,
								colIndex) {
							if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
								Ext.ux.Toast.msg('提示',
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
										'error', 4000);
								return;
							}

							Ext.Ajax.request({
										url : predeliver.realPath('upgradeDeliverbillDetail.action'),
										params : {
											'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
											'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
											'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
										},
										success : function(response) {
											// 刷新当前页
											predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().load();
										}
							});

							if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
								Ext.ux.Toast.msg('提示',
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
										'error', 4000);
								return;
							}
						}
						
						},
						{
							iconCls : 'deppon_icons_down',
							tooltip : '下移',
							handler : function(grid, rowIndex,
									colIndex) {
								if (grid.getStore().getAt(rowIndex).get('serialNo') == grid.getStore().getTotalCount()) {
									Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theEnd'),
											'error', 4000);
									return;
								}
	
								Ext.Ajax.request({
											url : predeliver.realPath('downgradeDeliverbillDetail.action'),
											params : {
												'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
												'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
												'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
											},
											success : function(response) {
												// 刷新当前页
												predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.store.load();
											}
								});
							}
						}]
			},*/
				{
					dataIndex : 'waybillNo',
					align : 'center',
					width : 80,
					header : '运单号'
				},
				{
					dataIndex : 'cashTime',
					align : 'center',
					width : 80,
					xtype : 'ellipsiscolumn',
					header : '规定兑现时间'
				},
				{
					dataIndex : 'consigneeAddress',
					align : 'left',
					width : 100,
					xtype : 'linebreakcolumn',
					header : '送货地址'
				},
				{
					dataIndex : 'preSuggestions',
					align : 'center',
					width : 90,
					xtype: 'templatecolumn',
					header : '预处理建议',
					tpl :  new Ext.XTemplate(
							'<tpl if="this.returnType(regionName,vehicleNo) == \'ALL\' ">',
									'<p>{regionName}<br/>{vehicleNo}</p>',
							'<tpl elseif="this.returnType(regionName,vehicleNo) == \'REGION_NAME\'">',
									'<p>{regionName}</font></p>',
							'<tpl elseif="this.returnType(regionName,vehicleNo) == \'VEHICLENO\'">',
									'<p>{vehicleNo}</p>',
							'</tpl>',
					{
							/*XTemplate 配置：*/
							disableFormats: true,
							/* 成员函数:*/
							returnType: function(regionName,vehicleNo){
								if ((!Ext.isEmpty(regionName))&&(!Ext.isEmpty(vehicleNo))){//全显示
									return 'ALL';
								} else if ((!Ext.isEmpty(regionName))&&(Ext.isEmpty(vehicleNo))) {//只有小区没车牌号
									return 'REGION_NAME';
								} else if((Ext.isEmpty(regionName))&&(!Ext.isEmpty(vehicleNo))) {//只有车牌号没小区
									return 'VEHICLENO';
								}
							}
					}
					)
				},
				{
					dataIndex : 'preArrangeGoodsQty',
					align : 'center',
					width : 60,
					header : '已排件数'
				},
				{
					dataIndex : 'weight',
					align : 'center',
					width : 58,
					header : '排单重量'
				},
				{
					dataIndex : 'goodsVolumeTotal',
					align : 'center',
					width : 58,	
					xtype: 'ellipsiscolumn',
					header : '排单体积'
				},{
					dataIndex : 'dimension',
					align : 'center',
					width : 65,
					xtype: 'ellipsiscolumn',//悬浮提示
					header : '尺寸'
				},{
					dataIndex : 'goodsPackage',
					align : 'center',
					width : 75,
					xtype: 'ellipsiscolumn',
					header : '包装'
				},{
					dataIndex : 'transportType',
					align : 'center',
					width : 80,
					header : '运输性质',
					renderer : function(value) {
						return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
					}
				},{
					dataIndex : 'deliverDate',
					align : 'center',
					width : 120,
					xtype: 'datecolumn',
					header : '预计送货日期',
					format : 'Y-m-d'
				},{
					dataIndex : 'deliveryTimeInterval',
					align : 'center',
					width : 90,
					header : '送货时间段'
				},{
					dataIndex : 'deliverRange',
					align : 'center',
					width : 90,
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
				},{
					dataIndex : 'payAmount',
					align : 'center',
					width : 80,
					header : '到付金额'
				},{
					dataIndex : 'togetherSendCode',
					align : 'center',
					width : 80,
					header : '合送编码'
				}],
		viewConfig: {
						stripeRows : false,
						enableTextSelection : true,
						getRowClass : function(record, rowIndex, rp, ds) {
										if (Ext.isEmpty(record.data.longitude) || Ext.isEmpty(record.data.latitude)) {
														return 'predeliver-beforeNoticeIndex-row-purole';
										}
						},
						plugins: {
							ptype: 'gridviewdragdrop',
							dragText: '拖拽行记录进行排序,点击保存(提交)后生效!'
						}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.predeliver.editDeliverbillNew.DeliverbillStore');
			me.selModel = Ext
						.create('Foss.predeliver.editDeliverbillNew.CheckboxModelAlready');
			me.callParent([cfg]);
		}
	});


// 定义checkbox model---分区查看
Ext.define('Foss.predeliver.editDeliverbillNew.CheckboxModelAlready', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsToAlready.add(record.data.id,
					record.data);
		},
		deselect : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsToAlready
					.removeAtKey(record.data.id);
		}
	}
})

//中间操作——分区查看
Ext.define('Foss.predeliver.ButtonPanelRole_PartitionedView',{
  		extend :'Ext.form.Panel',
  		buttonAlign : 'center',
  		layout : 'column',
		padding : 0,
		margin : '0 4 0 4',
		width : 125,
		flex : 0,
		getDriverName : function(vehicleNo) {
			if (vehicleNo != '' && vehicleNo != null) {
				Ext.Ajax.request({
					url : predeliver.realPath('queryVehicleByVehicleNo.action'),
					params : {
						'deliverbillVo.deliverbillDto.vehicleNo' : vehicleNo
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var driverNameField = predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverName');
						var driverCode = predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverCode');
						var vehicle = result.deliverbillVo.vehicleAssociationDto;
						if (vehicle != null) {
							var driver = result.deliverbillVo.driverDto;
							if (driver != null) {
								// 更新driverName的value和rawValue
								driverNameField.setValue(driver.empCode);
								driverNameField.setRawValue(driver.empName);
								driverCode.setValue(driver.empCode);
							} 
						} else {
							Ext.ux.Toast.msg(
									'提示',
									predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
									'error',
									4000);
							}
					}
				});
			 }
		},
  		items : [{
  				xtype : 'label',
				style : 'padding-top:10px;text-align:center',
				columnWidth: 1,
				text : '派送单号：'
  			},{
  				xtype : 'textfield',
				id : 'PartitionedViewDeliverNo',
				name : 'delivebillNo',
				fieldStyle : 'color:red;text-align:center',
				columnWidth: 1,
				readOnly :true,
				value :  querySequence()
  			},{
  				xtype : 'label',
				columnWidth: 1,				
				style : 'padding-top:5px;text-align:center',
				text : '车辆组别：'
  			},{
  				name : 'vehicleType',
  				fieldLabel : '', // 车辆组别
  				columnWidth : 1,
  				xtype : 'commonmotorcadeselector',
  				loopOrgCodes : predeliver.fleetCode,
  				listeners : {
  					'change' : function(field, event, eOpts) {
  						var form = field.up('form').getForm(),
  							vehicleNo = form.findField('vehicleNo'),
  							driverName = form.findField('driverName'),
  							driverCode = form.findField('driverCode');
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
  							driverCode.setValue('');
  							predeliver.editDeliverbillNew.vehicleNoTruckType_PartitionedView=null;
  		
  						}
  					}
  				}
  			},{
  				name : 'driverCode',
  				xtype: 'textfield',
  				hidden : true
  			},{
  				xtype: 'displayfield',
  				style : 'text-align:center',
  				fieldLabel: '送货车辆',
  				name: 'home_score',
  				columnWidth : 1
  			},{
  				name : 'vehicleNo',
  				fieldLabel : '', // 车辆
  				columnWidth : 1,
  				xtype : 'commontruckselector', 
  				allowBlank : false,
  				queryAllFlag : true,
  				loopOrgCode : predeliver.fleetCode,
  				listeners : {
  					'select': function(field, records, eOpts) {
  						var record = records[0];
  						predeliver.editDeliverbillNew.vehicleNoTruckType_PartitionedView = record.get('truckType');
						
						var driverNameField = predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverName');
						var driverCode = predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverCode');
						driverNameField.setValue(null);
						driverNameField.setRawValue(null);
						driverCode.setValue(null);
						
						var queryParams = predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.getStore().proxy.extraParams;
						//根据车牌号、送货日期带出排班信息
						var deliverTime = queryParams['partitionViewInfoVo.deliveryDate'];
						if(deliverTime==null){
							deliverTime=predeliver.editDeliverbillNew.QueryWaybillForm_PartitionedView.getValues().deliveryDate;
						}
						//更新已排单表格下的信息
						var bottomPanel = predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails;
						var ButtonPanelRole = predeliver.ButtonPanelRole_PartitionedView;
						if (predeliver.editDeliverbillNew.vehicleNoTruckType_PartitionedView == '外请车') {
							//外请车不查询排班表
							Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue(null);
									Ext.getCmp('predeliver.editDeliverbillNewIndex.nominalRate_PartitionedView').setValue(null);
							ButtonPanelRole.down('textfield[name=expectedBringVolume]').setValue("");
							ButtonPanelRole.down('combobox[name=frequencyNo]').setValue("");
							if (field.value != '') {
								predeliver.ButtonPanelRole_PartitionedView.getDriverName(field.value);
							}
              
              //如果是外请车，则设置出车任务"送+接"，预计出车时间为"00:00"。反之，如果已天出车任务、预计出车时间再选择车辆未外请车不考虑
              ButtonPanelRole.getForm().findField('carTaskinfo').setValue('1');
              ButtonPanelRole.getForm().findField('preCartaskTime').setValue('00:00');
						} else {
							Ext.Ajax.request({
								url : predeliver.realPath('queryWaybillDetailRightCount.action'),
								async : false,
								jsonData:{
									'vo' : {
										'waybillDetailNewQueryDto':{
											'deliverDate' : Ext.util.Format.date(deliverTime, 'Y-m-d'),
											'vehicleNo' : field.value
										},
										'deliverbill' : {
											'id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
										}
									}
								},
								success : function(response) {
									var data = Ext.decode(response.responseText);
									if(!data.success &&(!data.isException)){
										Ext.ux.Toast.msg('提示', data.message, 'error', 2000);
										return;
									}
									if (field.value != '') {
										predeliver.ButtonPanelRole_PartitionedView.getDriverName(field.value);
									}
									predeliver.editDeliverbillNew.waybillDetailLoadRate = null;
									predeliver.editDeliverbillNew.waybillDetailNominalRate = null;
									ButtonPanelRole.down('textfield[name=expectedBringVolume]').setValue("");
									ButtonPanelRole.down('combobox[name=frequencyNo]').setValue("");
									if(data.vo.waybillDeliverNewCountDto==null){
										return;
									}
									if (data.vo.waybillDeliverNewCountDto.nominalRate) {//额定净空/额定载重率
										Ext.getCmp('predeliver.editDeliverbillNewIndex.nominalRate_PartitionedView').setValue(data.vo.waybillDeliverNewCountDto.nominalRate);
									}
									if (data.vo.waybillDeliverNewCountDto.expectedBringVolume) {//带货(F)
										ButtonPanelRole.down('textfield[name=expectedBringVolume]').setValue(data.vo.waybillDeliverNewCountDto.expectedBringVolume);
									}
									if (data.vo.waybillDeliverNewCountDto.frequencyNo) {//班次
										ButtonPanelRole.down('combobox[name=frequencyNo]').setValue(data.vo.waybillDeliverNewCountDto.frequencyNo);
									}
									if (data.vo.waybillDeliverNewCountDto.carTaskinfo) {//出车任务
										ButtonPanelRole.getForm().findField('carTaskinfo').setValue(data.vo.waybillDeliverNewCountDto.carTaskinfo);
									}
									if (data.vo.waybillDeliverNewCountDto.preCartaskTime) {//预计出车时间
										ButtonPanelRole.getForm().findField('preCartaskTime').setValue(data.vo.waybillDeliverNewCountDto.preCartaskTime);
									}
									if (data.vo.waybillDeliverNewCountDto.takeGoodsDeptcode) {//带货部门
										ButtonPanelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setValue(data.vo.waybillDeliverNewCountDto.takeGoodsDeptcode);
										ButtonPanelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setRawValue(data.vo.waybillDeliverNewCountDto.takeGoodsDeptname);
									}
									if (data.vo.waybillDeliverNewCountDto.transferDeptcode) {//转货部门
										ButtonPanelRole.down('commonsaledepartmentselector[name=transferDeptcode]').setValue(data.vo.waybillDeliverNewCountDto.transferDeptcode);
										ButtonPanelRole.down('commonsaledepartmentselector[name=transferDeptcode]').setRawValue(data.vo.waybillDeliverNewCountDto.transferDeptname);
									}
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									field.value='';
									Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
								}
							});
						}
  					}
  				}	
  			},{
  				xtype : 'label',
				columnWidth: 1,				
				style : 'padding-top:10px;text-align:center',
				text : '送货司机：'
  			},{
  				name : 'driverName',
				columnWidth : 1,
				xtype : 'commondriverselector',								
				waybillFlag:'N',//是否是集中接送区域  如果设置值为Y,N
				fleetType:'FLEET_TYPE__SHUTTLE,FLEET_TYPE__SHUTTLE_GOODS,FLEET_TYPE__LONG_DISTANCE',
				columnWidth : 1,
				//车队组类型(包含集中接送货和物流班车)
				listeners : {
				blur : function(field, event, eOpts) {
					// 公共选择器的value为driverCode,rawValue为driverName
					// 更新driverCode
					this.up('form').getForm().findField('driverCode').setValue(field.value);
				}
			}
			},{
  				xtype : 'label',
				columnWidth: 1,				
				style : 'padding-top:10px;text-align:center',
				text : '派车类型：'
  			},{
  				xtype : 'combobox',
				name : 'deliverType',
				columnWidth : 1,
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : 'NOMAL',
				store : Ext.create('Foss.predeliver.store.DeliverTypeStore')
			},{
				border : false,
				xtype : 'container',
				columnWidth : 1,
				//frame : true,
				layout : 'column',
				items : [{
						style : 'margin-top:43px;margin-left:2px;cursor:pointer',
						id : 'predeliver.ButtonPanelRole_Button_Left_Left',
						hidden : true,
						xtype : 'button',
						tooltip: '点击缩回',
						maxWidth : 15,
						cls : 'flexleft',
						columnWidth : .15,
						listeners:{
							click: {
								element: 'el', 
								fn: function(){ 
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left').hide();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right').show();//显示下一个按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right').show();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left').hide();//显示上一个按钮
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').setWidth((document.body.scrollWidth)/4*2);
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').doComponentLayout();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').setWidth((document.body.scrollWidth)/4+130);
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').doComponentLayout();
								}
							}
						}
					},{
						style : 'margin-top:43px;margin-left:2px;cursor:pointer',
						id : 'predeliver.ButtonPanelRole_Button_Left_Right',
						xtype : 'button',
						maxWidth : 15,
						tooltip: '点击展开',
						cls : 'flexright',
						columnWidth : .15,
						listeners:{
							click: {
								element: 'el', 
								fn: function(){ 
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right').hide();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left').show();//显示上一个按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right').show();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left').hide();//显示上一个按钮
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').setWidth((document.body.scrollWidth)/3*2+20);
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').doComponentLayout();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').setWidth((document.body.scrollWidth-201)/5);
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').doComponentLayout();
								}
							}
						}
					},{
					xtype : 'button',
					style : 'margin-top:30px;',
					iconCls:'deppon_icons_turnright',
					tooltip: '添加至派送单',
					height:23,
					columnWidth : .70,
					listeners:{
						Click : function(){	
							//排单验证车辆
							if(!predeliver.editDeliverbillNew.SubmitForm_PartitionedView.validateOnArrange()){
								return;
							}
							//排单验证司机
							if(!predeliver.editDeliverbillNew.SubmitForm_PartitionedView.validateOnDriver()){
								return;
							}
							
							if(predeliver.editDeliverbillNew.Flag==true){
								Ext.ux.Toast.msg('提示', '当前派送单正在操作中，请勿重复点击按钮', 'error', 2500);
								return;
							}
							
							predeliver.editDeliverbillNew.Flag=true;
							
							//定义运单集合
							var waybillNos=new Array();
							
							//排单验证是否选择数据
							if(!predeliver.editDeliverbillNew.SubmitForm_PartitionedView.validateOnBigAndSmall()){
								predeliver.editDeliverbillNew.Flag=false;
								return;
							}else{
								var bigZones=null;
								var smallZones=null;
								
								predeliver.editDeliverbillNew.waybillsToArrange_BigZone
									.each(function(item, index,length) {
									if(bigZones==null){
										bigZones=item.regionalCode;
									}else{
										bigZones=bigZones+','+item.regionalCode;
									}
								});
								predeliver.editDeliverbillNew.waybillsToArrange_SmallZone
									.each(function(item, index,length) {
									if(smallZones==null){
										smallZones=item.regionalCode;
									}else{
										smallZones=smallZones+','+item.regionalCode;
									}
								});
								var queryWaybillForm = predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView;
								var queryParams = queryWaybillForm.getStore().proxy.extraParams;
								//Ajax给运单集合赋值
								Ext.Ajax.request({
									url:predeliver.realPath('queryWaybills_BigZoneAndSmallZone.action'),
									params: {
										'partitionedviewVo.partitionedViewDto.deliverTime' : queryParams['partitionViewInfoVo.deliveryDate'],
										'partitionedviewVo.partitionedViewDto.productCode' : queryParams['partitionViewInfoVo.productCode'],
										'partitionedviewVo.partitionedViewDto.teamGroup' : queryParams['partitionViewInfoVo.fleetGroup'],
										'partitionedviewVo.partitionedViewDto.goodStatus' : queryParams['partitionViewInfoVo.goodStatus'],
										'partitionedviewVo.partitionedViewDto.vehicleNo' : queryParams['partitionViewInfoVo.vehicleNo'],
										'partitionedviewVo.partitionedViewDto.smallZoneCodes' : smallZones,
										'partitionedviewVo.partitionedViewDto.bigZoneCodes' : bigZones
									},
									success: function(response){
										var json = Ext.decode(response.responseText);
										//给运单集合赋值
										waybillNos=json.partitionedviewVo.waybills;
										
										//运单集合为Null，不进行下一步操作
										if(waybillNos==null){
											Ext.ux.Toast.msg('提示', '运单数量为0', 'error', 2500);
											return;
										}
										
										//调用方法验证
										predeliver.editDeliverbillNew.queryIsExsitsWayBillRfcs_PartitionedView(predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView,waybillNos)
								    },
									exception: function(response){
										predeliver.editDeliverbillNew.Flag=false;
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg('提示', json.message, 'error', 2500);
									}
								});
							}
						}	
					}
				},{
					xtype : 'button',
					iconCls:'deppon_icons_turnleft',
					tooltip: '移除出派送单',
					height:23,
					columnWidth : .70,
					listeners:{
						Click : function(){							
							var selectRow = predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getSelectionModel().getSelection();
							if (selectRow.length == 0) {
								Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
								return;
							}
							
							var ids = '';
							for ( var i = 0; i < selectRow.length; i++) {
								ids = selectRow[i].data.id+ ","+ ids;
							}
							predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.deleteDeliverbillDetails(ids);
					}							
				}
				},{
						style : 'margin-top:-13px;cursor:pointer', //margin-left:6px;
						id : 'predeliver.ButtonPanelRole_Button_Right_Left',
						hidden : true,
						xtype : 'button',
						tooltip: '点击缩回',
						maxWidth : 15,
						cls : 'flexright',
						columnWidth : .15,
						listeners:{
							click: {
								element: 'el', 
								fn: function(){ 
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left').hide();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right').show();//显示下一个按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right').show();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left').hide();//显示上一个按钮
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').setWidth((document.body.scrollWidth)/4*2);
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').doComponentLayout();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').setWidth((document.body.scrollWidth)/4+130);
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').doComponentLayout();
								}
							}
						}
					},{
						style : 'margin-top:-13px;cursor:pointer',//margin-left:6px;
						id : 'predeliver.ButtonPanelRole_Button_Right_Right',
						xtype : 'button',
						maxWidth : 15,
						tooltip: '点击展开',
						cls : 'flexleft',
						columnWidth : .15,
						listeners:{
							click: {
								element: 'el', 
								fn: function(){ 
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right').hide();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left').show();//显示上一个按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right').show();//点击隐藏自身按钮
									Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left').hide();//显示上一个按钮
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').setWidth((document.body.scrollWidth-201)/5);
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView').doComponentLayout();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').setWidth((document.body.scrollWidth-201)/3*2+155);
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').show();
									Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView').doComponentLayout();
								}
							}
						}
					}]
			},{
				xtype : 'textfield',
				style : 'margin-top:6px',
				emptyText : '输入单号右移',
				id : 'predeliver_editDeliverbillNew_partitionedView_bywaybillNumber_ID',
				columnWidth : 1,
				maxLength : 9,
				listeners : {
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
								//排单验证车辆
								if(!predeliver.editDeliverbillNew.SubmitForm_PartitionedView.validateOnArrange()){
									return;
								}

								if (predeliver.editDeliverbillNew.waybillDetailId_PartitionedView == "" || predeliver.editDeliverbillNew.waybillDetailId_PartitionedView == null) {
									var deliverbillBasicInfo = predeliver.ButtonPanelRole_PartitionedView.getValues();
									// 保存派送单
									Ext.Ajax.request({
										url : predeliver.realPath('waybillDetailSaveDeliverbillNew.action'),
										jsonData : {									
											'partitionedviewVo' : {
												'deliverbill' : {
													'deliverbillNo' : deliverbillBasicInfo.delivebillNo,
													'vehicleNo' : deliverbillBasicInfo.vehicleNo,
													'delStatus' :"",
													'driverCode' : deliverbillBasicInfo.driverCode,
													'deliverType' : deliverbillBasicInfo.deliverType,
													'driverName' : predeliver.ButtonPanelRole_PartitionedView.getForm().findField("driverName").rawValue,
													'carTaskinfo': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=carTaskinfo]').getValue(),
													'frequencyno': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=frequencyNo]').getValue(),
													'preCartaskTime': predeliver.ButtonPanelRole_PartitionedView.down('timefield[name=preCartaskTime]').getRawValue(),
													'takeGoodsDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
													'takeGoodsDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
													'expectedbringvolume': predeliver.ButtonPanelRole_PartitionedView.down('textfield[name=expectedBringVolume]').getValue(),
													'transferDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
													'transferDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
												}
											}
										},
										success : function(response) {
											var result = Ext.decode(response.responseText);
											predeliver.editDeliverbillNew.waybillDetailId_PartitionedView = result.partitionedviewVo.deliverbill.id;
											//调用AJAX
											Ext.Ajax.request({
												url:predeliver.realPath('waybillDetailAddToArrangeByWaybillNoNew.action'),
												params: {
													'partitionedviewVo.deliverbill.id':  predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
													'partitionedviewVo.waybillNo' : field.value
												},
												success: function(response){													
													predeliver.editDeliverbillNew.waybillsToArrange_BigZone.clear();
													predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.clear();
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.getStore().load({
														scope: this,
														callback: function(records, operation, success) {
															var handoverBillCounts = 0; 
															var goodsWeightSums = 0;
															var goodsVolumeSums = 0;
															Ext.Array.each(records, function(record, index, countriesItSelf) {
																handoverBillCounts += record.get('handoverBillCount');
																goodsWeightSums +=  record.get('goodsWeightSum');
																goodsVolumeSums += record.get('goodsVolumeSum');
															});
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsQty]').setValue(handoverBillCounts);
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsWeight]').setValue(Ext.Number.toFixed(goodsWeightSums, 1));
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsVolume]').setValue(Ext.Number.toFixed(goodsVolumeSums, 2));
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsQty]').setValue('0');
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsWeight]').setValue('0.0');
															predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsVolume]').setValue('0.00');
														}
													});
													
													predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().load();//刷新已排单结果
													Ext.getCmp('predeliver_editDeliverbillNew_partitionedView_bywaybillNumber_ID').setValue('');
													
													//给已排单运单汇总信息赋值
													var result = Ext.decode(response.responseText);
													Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue(result.partitionedviewVo.deliverbill.loadingRate);//设置装载率
													Ext.getCmp('predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.waybillQtyTotal);//设置总票数
													Ext.getCmp('predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.goodsQtyTotal);//设置总件数
													Ext.getCmp('predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.weightTotal).toFixed(2));//设置总重量
													Ext.getCmp('predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.volumeTotal).toFixed(1));//设置总体积
													Ext.getCmp('predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.payAmountTotal).toFixed(2));//设置到付金额
													Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'success', 2500);
												},
												exception: function(response){
													var json = Ext.decode(response.responseText);
													Ext.ux.Toast.msg('提示', json.message, 'error', 2500);
												}
											});
										},
										exception : function(response) {
											var result = Ext.decode(response.responseText);
											Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
										}
									});
								}else {
									//调用AJAX
									Ext.Ajax.request({
										url:predeliver.realPath('waybillDetailAddToArrangeByWaybillNoNew.action'),
										params: {
											'partitionedviewVo.deliverbill.id':  predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
											'partitionedviewVo.waybillNo' : field.value
										},
										success: function(response){
											predeliver.editDeliverbillNew.waybillsToArrange_BigZone.clear();
											predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.clear();
											predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.getStore().load({
												scope: this,
												callback: function(records, operation, success) {
													var handoverBillCounts = 0; 
													var goodsWeightSums = 0;
													var goodsVolumeSums = 0;
													Ext.Array.each(records, function(record, index, countriesItSelf) {
														handoverBillCounts += record.get('handoverBillCount');
														goodsWeightSums +=  record.get('goodsWeightSum');
														goodsVolumeSums += record.get('goodsVolumeSum');
													});
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsQty]').setValue(handoverBillCounts);
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsWeight]').setValue(Ext.Number.toFixed(goodsWeightSums, 1));
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=totalGoodsVolume]').setValue(Ext.Number.toFixed(goodsVolumeSums, 2));
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsQty]').setValue('0');
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsWeight]').setValue('0.0');
													predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView.down('displayfield[name=selectGoodsVolume]').setValue('0.00');
												}
											});
											
											predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().load();//刷新已排单结果
											Ext.getCmp('predeliver_editDeliverbillNew_partitionedView_bywaybillNumber_ID').setValue('');
											
											//给已排单运单汇总信息赋值
											var result = Ext.decode(response.responseText);
											Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue(result.partitionedviewVo.deliverbill.loadingRate);//设置装载率
											Ext.getCmp('predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.waybillQtyTotal);//设置总票数
											Ext.getCmp('predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView').setValue(result.partitionedviewVo.deliverbill.goodsQtyTotal);//设置总件数
											Ext.getCmp('predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.weightTotal).toFixed(2));//设置总重量
											Ext.getCmp('predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.volumeTotal).toFixed(1));//设置总体积
											Ext.getCmp('predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView').setValue((result.partitionedviewVo.deliverbill.payAmountTotal).toFixed(2));//设置到付金额
											Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'success', 2500);
										},
										exception: function(response){
											var json = Ext.decode(response.responseText);
											Ext.ux.Toast.msg('提示', json.message, 'error', 2500);
										}
									});
								}
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
				style : 'padding-top:5px;text-align:center',
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
				style : 'padding-top:5px;text-align:center',
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
				style : 'padding-top:5px;text-align:center',
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
			{
				xtype : 'button',
				text : '特殊地址标记',
				style : 'margin-top:10px;',
				columnWidth: 1,	
				listeners:{
				   	Click : function(){	
						var record = {};
						var count = 0;
						var waitGrid = predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView;
						var waitRowModel = waitGrid.getSelectionModel();
						count = waitRowModel.getCount();
						if (count == 1) {
							if (waitRowModel.hasSelection()) {
								record = waitRowModel.getLastSelected();
							}
							
							if (record) {
								
								var consigneeAddress = "";
								
								if (record.get('consigneeAddress')) {
									consigneeAddress = record.get('consigneeAddress').replace(new RegExp('-',"gm"), '');
								}
							
								Ext.Ajax.request({
									url : predeliver.realPath('selectSpecialAddress.action'),
									params : {
										deliveryAddress : consigneeAddress
									},
									success : function(response){
										var win = Ext.create('Foss.predeliver.editDeliverbillNewIndex.SpecialAddressTagWindow');
										
										var obj = Ext.decode(response.responseText);
										
										if (obj.success) {
											if (obj.specialDeliveryAddressEntity) {
												if (obj.specialDeliveryAddressEntity.addressType) {
													win.down('combobox[name=addressType]').setValue(obj.specialDeliveryAddressEntity.addressType);
												}
												if (obj.specialDeliveryAddressEntity.vehicleNo) {
													win.down('commontruckselector[name=vehicleNo]').setValue(obj.specialDeliveryAddressEntity.vehicleNo);
												}
												
												if (obj.specialDeliveryAddressEntity.id) {
													win.down('hidden[name=id]').setValue(obj.specialDeliveryAddressEntity.id);
												}
											}
											
											win.down('hidden[name=deliveryAddress]').setValue(consigneeAddress);
											
											if (record.get('actualSmallzoneCode')) {
												win.down('hidden[name=deliveryResidenceCode]').setValue(record.get('actualSmallzoneCode'));
											}
											
											if (record.get('actualSmallzoneName')) {
												win.down('hidden[name=deliveryResidenceName]').setValue(record.get('actualSmallzoneName'));
											}
											if (record.get('waybillNo')) {
												win.down('hidden[name=waybillNo]').setValue(record.get('waybillNo'));
											}
											win.show();
										} else {
											Ext.ux.Toast.msg('提示信息', obj.message, 'success', 3000);
										}
										
										
										
									},
									failure : function(response, opts) {
										var obj = Ext.decode(response.responseText);
										Ext.ux.Toast.msg('提示信息', obj.message, 'success', 3000);
									}
								});	
							}
							
						} else {
							Ext.ux.Toast.msg('提示信息','已排单列表至少选中一行且只能选中一行数据进行特殊地址标记操作', 'success', 3000);
						}				
					}													
				}
			},{
				xtype : 'button',
				text : '地址坐标标记',
				style : 'margin-top:10px;',
				listeners:{
				    Click : function(){
						var selectRow = predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getSelectionModel().getSelection();
						if (selectRow.length == 0) {
							Ext.ux.Toast.msg('提示','已排单列表至少选中一行数据进行地址坐标标记操作', 'error', 3000);
							return;
						}
						var waybillNos = []; 

						Ext.Array.each(selectRow, function (record, index, sortedRowModel) {
							waybillNos.push(record.get('waybillNo'));
						});
						
						//根据条件查询特殊地址历史库的特殊地址类型
						Ext.Ajax.request({
								url : predeliver.realPath('visibleAddressMark.action'),
								async : false,
								jsonData:{
									'vo': {
										'specialWayBillNO': waybillNos.join(',')
									}
								},
								success : function(response) {
									var json = Ext.decode(response.responseText);
									if (json.vo.listAddresMarks) {
											var markWin = Ext.create('Foss.predeliver.editDeliverbillNewIndex.addressCoordinateMark');
											markWin.down('grid').getStore().loadData(json.vo.listAddresMarks);
											markWin.show();
									}
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
								}
						});
				    }
				},
				columnWidth: 1
			}]
});
//页面底部保存提交form
Ext.define('Foss.predeliver.editDeliverbillNew.SubmitForm_PartitionedView', {
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
		if (!predeliver.ButtonPanelRole_PartitionedView.getForm().isValid()) {
			Ext.ux.Toast.msg('提示', '有必填项（页面标识红叉项）未填写，请填写完整！');
			return false;
		}
		return true;
	},
	// 排单前验证
	validateOnBigAndSmall : function() {
		if (predeliver.editDeliverbillNew.waybillsToArrange_SmallZone.length<1 && predeliver.editDeliverbillNew.waybillsToArrange_BigZone.length<1) {
			Ext.ux.Toast.msg('提示', '请选择分区查询结果');
			return false;
		}
		return true;
	},
	// 排单前验证
	validateOnDriver : function() {
		// 11a 12a 排单员点击“保存”或“提交”时，如果外请车没有对应的司机
		if (predeliver.editDeliverbillNew.vehicleNoTruckType_PartitionedView == '外请车'&& predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverName').getRawValue() == '') {
			Ext.ux.Toast.msg('提示', '司机不能为空');
			return false;
		}
		return true;
	},
	// 保存/提交时验证
	validateOnSaveOrSubmit : function() {
		// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
		if (!predeliver.ButtonPanelRole_PartitionedView.getForm().isValid()) {
			Ext.ux.Toast.msg('提示', '有必填项（页面标识红叉项）未填写，请填写完整！');
			return false;
		}
		// 11b 12b
		// 排单员点击"保存"或"提交"时，如果未有选中的运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
		if (predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.store.data.getCount() == 0) {
			Ext.ux.Toast.msg('提示', '该派送单没有排单信息');
			return false;
		}
		return true;
	},
	// 保存/提交派送单
	saveOrSubmit : function(deliverbillStatus, button) {
		//需要验证车牌号/司机是否有效
		if (!predeliver.editDeliverbillNew.SubmitForm_PartitionedView.validateOnSaveOrSubmit()) {
			return;
		}
    
    //拖动排序保存,取当前的集合数据
    var sortGridList = new Array();
    predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.getStore().each(function(item, index, length) {
      var obj = new Object();
      obj.id = item.data.id;
      obj.waybillNo = item.data.waybillNo;
      sortGridList.push(obj);
    });

    
		var deliverbillBasicInfo = predeliver.ButtonPanelRole_PartitionedView.getValues();
		// 更新派送单
		Ext.Ajax.request({
				url : predeliver.realPath('visibleSaveDeliverbill.action'),
				jsonData : {
					'vo' : {
						'deliverbill' : {
							'id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView,
							'deliverbillNo' : deliverbillBasicInfo.delivebillNo,
							'vehicleNo' : deliverbillBasicInfo.vehicleNo,
							'delStatus' : predeliver.editDeliverbillNew.status,
							'driverCode' : deliverbillBasicInfo.driverCode,
							'deliverType' : deliverbillBasicInfo.deliverType,
							'driverName' : predeliver.ButtonPanelRole_PartitionedView.getForm().findField("driverName").rawValue,
							'status' : deliverbillStatus,
							'carTaskinfo': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=carTaskinfo]').getValue(),
							'frequencyno': predeliver.ButtonPanelRole_PartitionedView.down('combobox[name=frequencyNo]').getValue(),
							'preCartaskTime': predeliver.ButtonPanelRole_PartitionedView.down('timefield[name=preCartaskTime]').getRawValue(),
							'takeGoodsDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
							'takeGoodsDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
							'expectedbringvolume': predeliver.ButtonPanelRole_PartitionedView.down('textfield[name=expectedBringVolume]').getValue(),
							'transferDeptcode': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
							'transferDeptname': predeliver.ButtonPanelRole_PartitionedView.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
						},
            'dragStr': sortGridList.length < 1 ? null : Ext.encode(sortGridList)
					}
				},
				success : function(response) {
					var confirmTitle = deliverbillStatus == 'SAVED' ? predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.saveDeliverSuccess')
							: predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.submitDeliverSuccess');
					var confirmMsg = confirmTitle
							+ predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theNumberIs')
							+ deliverbillBasicInfo.delivebillNo
							+ predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.surePrintDeliverBill')
					Ext.Msg.confirm(confirmTitle, confirmMsg, function(btn, text) {
							if (btn == "yes") {
								// 打印预派送单
								var printWin = Ext.create('Foss.predeliver.deliverbill.PrintDeliverbillWindow', {
										'deliverbillId' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
								});

								//获取打印的预排送单基本信息
								Ext.Ajax.request({
										url : predeliver.realPath('queryDeliverbill.action'),
										params : {
											'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetailId_PartitionedView
										},
										success : function(response) {
												var result = Ext.decode(response.responseText), model = Ext.ModelManager
														.create(result.deliverbillVo.deliverbill, 'Foss.predeliver.deliverbill.PrintModel');
														
												 if(!Ext.isEmpty(deliverbillBasicInfo.driverName)){
													  printWin.query('checkbox[name="isNoticeDriver"]')[0].setValue('Y');
													  printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
												  }else{//如果司机姓名为空，通知司机按钮显示、不可操作
													  printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
													  printWin.query('checkbox[name="isNoticeDriver"]')[0].setDisabled (true); 
												  }
												
												printWin.getDeliverPrintHeadInfo().loadRecord(model);
												printWin.getDeliverPrintBottomInfo().loadRecord(model);
												printWin.getDeliverPrintGrid().store.setDeliverbillId(predeliver.editDeliverbillNew.waybillDetailId_PartitionedView);
												printWin.getDeliverPrintGrid().getPagingToolbar().moveFirst();
												printWin.show();
										}
								});
							}
					});

					if (deliverbillStatus == 'SUBMITED') {
						Ext.getCmp('T_predeliver-visibleOrderBillIndex').close();
						if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
								&& predeliver.deliverbill.deliverbillGrid != null) {
							predeliver.deliverbill.deliverbillGrid.store.load();
						}
					}

				},
				exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
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
					text : '保存', //保存,
					handler : function(button, e) {
						button.up('form').saveOrSubmit('SAVED', button);
					}
				},
				{
					columnWidth : .08,
					xtype : 'button',
					id : 'deliverbill_submit_vob',
					disabled : false,
					cls : 'yellow_button',
					text : '提交', //提交,
					handler : function(button, e) {
						button.up('form').saveOrSubmit('SUBMITED', button);
						//刷新派送单号，清空已排单运单
						Ext.getCmp('PartitionedViewDeliverNo').setValue(querySequence());
						predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.store.removeAll();
						//清空数据
						Ext.getCmp('predeliver.editDeliverbillNewIndex.loadingRate_PartitionedView').setValue('0%/0%');//设置装载率
						Ext.getCmp('predeliver.editDeliverbillNewIndex.waybillQtyTotal_PartitionedView').setValue(0);//设置总票数
						Ext.getCmp('predeliver.editDeliverbillNewIndex.goodsQtyTotal_PartitionedView').setValue(0);//设置总件数
						Ext.getCmp('predeliver.editDeliverbillNewIndex.weightTotal_PartitionedView').setValue(0);//设置总重量
						Ext.getCmp('predeliver.editDeliverbillNewIndex.volumeTotal_PartitionedView').setValue(0);//设置总体积
						Ext.getCmp('predeliver.editDeliverbillNewIndex.payAmountTotal_PartitionedView').setValue(0);//设置到付金额								
					}
				}]
	} ]
});

/*分区查看  传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单---*/
predeliver.editDeliverbillNew.queryIsExsitsWayBillRfcs_PartitionedView = function( selectRowGrid, selectWaybillNos) {
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
			var waybillNos = json.deliverbillVo.waybillNos;
			var notWaybillrfcNos = json.deliverbillVo.notWaybillrfcNos;
			var waybillString = '';
			var notWaybillrfcString ='';
			if(notWaybillrfcNos.length>0){
				for(var j = 0 ;j<notWaybillrfcNos.length;j++){
					notWaybillrfcString +=notWaybillrfcNos[j]+'<br/>';
				}
				var confirmMsgBox3 = Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),"运单号:" +notWaybillrfcString+"有更改单未核审 /未受理不能排单，剩余运单是否继续排单？",function(btn1){
					if (btn1 == 'yes') {
						predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.addWaybillToArrange(selectWaybillNos,notWaybillrfcNos);
					}else {
						predeliver.editDeliverbillNew.Flag=false;
						return;
					}
				});
			}else if(waybillNos.length>0){
				for(var j = 0 ;j<waybillNos.length;j++){
					waybillString +=waybillNos[j]+'<br/>';
				}
				var confirmMsgBox3 = Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),"运单号:" +waybillString+"有更改单未核审 /未受理不能排单，剩余运单是否继续排单？",function(btn1){
					if (btn1 == 'yes') {
						predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.addWaybillToArrange(selectWaybillNos,waybillNos);
					}else {
						predeliver.editDeliverbillNew.Flag=false;
						return;
					}
				});
			}else {
				predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView.addWaybillToArrange(selectWaybillNos,null);
			}
			
			
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示信息', result.message, 'error', 2500);
		}
	});
}

/*----------------------------------运单明细开始-----------------------------------------------------*/
//定义运单明细中待选列表的模型
Ext.define('Foss.predeliver.editDeliverbillNew.WaybillDetailModel',
{
	extend : 'Ext.data.Model',
	fields : [
	       {
	    	   name : 'id',						//ID
	    	   type : 'String'
	       },{
	    	   name : 'srvDeliverHandoverbillId',						//交单ID
	    	   type : 'String'
	       },{
	    	   name : 'waybillNo',				//运单号
	    	   type : 'String'
	       },{
	    	   name : 'cashTime',				//规定兑现时间
	    	   type : 'String',
			   convert: function(value) {
	            	if (value != null) {
	            		var date = new Date(value);
	            		return Ext.Date.format(date,'Y-m-d H:i');
	            	} else {
	            		return null;
	            	}
	            } 	    	   
	       },{
	    	   name : 'consigneeAddress',		//送货地址
	    	   type : 'String'
	       },{
	    	   name : 'preArrangeGoodsQty',				//预排件数
	    	   type : 'int'				
	       },{
	    	   name : 'arrangeGoodsQty',				//排单件数
	    	   type : 'int'				
	       },{
	    	   name : 'arrangableGoodsQty',				//可排单件数
	    	   type : 'int'				
	       },{
	    	   name : 'suggestionTreatment', 	//预处理建议（小区/车辆）
	    	   type : 'String'
	       },{
	    	   name : 'arrangedWeight',			//排单重量
	    	   type : 'double',
	    	   convert: function(value) {
					if (value != null && value!='') {
						return Ext.util.Format.round(value,1);
					} else {
						return 0.0;
					}
				}
	       },{
	    	   name : 'arrangedVolume',			//排单体积
	    	   type : 'double',
	    	   convert: function(value) {
					if (value != null && value!='') {
						return Ext.util.Format.round(value,2);
					} else {
						return 0.00;
					}
				}
	       },{
	    	   name : 'dimension',				//尺寸
	    	   type : 'String'
	       },{
	    	   name : 'goodsPackage',			//包装
	    	   type : 'String'
	       },{
	    	   name : 'addressType',			//特殊地址类型
	    	   type : 'String',
				convert : function(value, record) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_SPECIAL_DELIVERYADDRESS_TYPE');
				}
	    	   
	       },{
	    	   name : 'goodsType',				//特殊货物类型
	    	   type : 'String',
			   convert: function(value) {
					if (value != null && value!='') {
						return value.substring(0,value.length-1);
					} else {
						return null;
					}
				}
	       },{
	    	   name : 'prouctCode',				//运输性质
	    	   type : 'String'
	       },{
			
			name : 'receiveMethod',				//提货方式
	    	   type : 'String'
		   },{
	    	   name : 'deliverDate',			//预计送货日期
	    	   type : 'date',
			    convert: function(value) {
					if (value != null) {
						var date = new Date(value);
						return Ext.Date.format(date,'Y-m-d');
					} else {
						return null;
					}
				}
	       },{
	    	   name : 'deliveryTimeInterval',			//送货时间段
	    	   type : 'String'
	       },{
	    	   name : 'deliverTimeStartAndOver',//送货时间点
	    	   type : 'String'
	       },{
	    	   name : 'billQty',			    //交单件数
	    	   type : 'int'
	       },{
	    	   name : 'stockQty',			    //库存件数
	    	   type : 'int'
	       },{
	    	   name : 'goodsQtyTotal',			    //开单件数
	    	   type : 'int'
	       },{
	    	   name : 'payAmount',			    //到付金额
	    	   type : 'String'
	       },{
	    	   name : 'ertuenReason',			//理货员退回原因
	    	   type : 'String'
	       },{
	    	   name : 'deliverRequire',			//送货要求
	    	   type : 'String'
	       },{
	    	   name : 'goodsStatus',			//货物状态
	    	   type : 'String'
	       },{
	    	   name : 'togetherSendCode',					//合送编码
	    	   type : 'String'
	       },{
	    	   name : 'actualVehicleNo',					//车牌号
	    	   type : 'String'
	       },{
	    	   name : 'actualSmallzoneCode',					//送货小区CODE
	    	   type : 'String'
	       },{
	    	   name : 'actualSmallzoneName',					//送货小区名称
	    	   type : 'String'
	       },{
	    	   name : 'receiveBigCustomer',					//是否大客户
	    	   type : 'String'
	       },{
	    	   name : 'longitude',					//
	    	   type : 'String'
	       },{
	    	   name : 'latitude',					//
	    	   type : 'String'
	       },{
	    	   name : 'goodsVolumeTotal',					//开单重量
	    	   type : 'double'
	       },{
	    	   name : 'weight',					//开单体积
	    	   type : 'double'
	       },{
	    	   name : 'isVehicleScheduling',					//车辆是否排班
	    	   type : 'String'
	       },{
	    	   name : 'uitraLongDelivery',					//是否超远派送运单
	    	   type : 'String'
	       },{
	    	   name : 'actualProvN',					//省名称
	    	   type : 'String'
	       },{
	    	   name : 'actualCityN',					//市名称
	    	   type : 'String'
	       },{
	    	   name : 'actualDistrictN',					//区名称
	    	   type : 'String'
	       },{
	    	   name : 'receiveCustomerAddress',					//收货地址
	    	   type : 'String'
	       },{
	    	   name : 'goodsName',					//货物名称
	    	   type : 'String'
	       },{
	    	   name : 'arriveTime',					//到达时间
	    	   type : 'date',
           convert: function(value) {
            if (value != null) {
              var date = new Date(value);
              return Ext.Date.format(date,'Y-m-d H:i:s');
            } else {
              return null;
            }
          }
	       },{
	    	   name : 'inStockTime',					//入库时间
	    	   type : 'date',
           convert: function(value) {
            if (value != null) {
              var date = new Date(value);
              return Ext.Date.format(date,'Y-m-d H:i:s');
            } else {
              return null;
            }
          }
	       }, {
	    	   name: 'receiveCustomerContact',//收货联系人
	    	   type: 'string'
	       }, {
	    	   name: 'returnNumber',//退单次数
	    	   type: 'number'
	       }
	]
});
//已排单运单的表格Model---运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.deliverbillDetailGridModel', {
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
            name : 'regionName', // 预处理建议.送货小区
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
                    return Ext.Date.format(date,'Y-m-d');
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
            name : 'actualSmallzoneName', //小区名称
            type : 'string'
        }, {
            name : 'actualSmallzoneCode', //小区代码
            type : 'string'
        }]
});
//定义运单明细---中间派送单号相关MODEL
Ext.define('Foss.predeliver.editDeliverbillNew.ShippingDetailsModel', {
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
	},{
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
	}]
});
// 定义运单明细待标列表的Store
Ext.define('Foss.predeliver.editDeliverbillNew.StandardList',
{
	extend : 'Ext.data.Store',
	//autoLoad : false,
	 timeout: 600000,
	// 绑定一个模型
	model : 'Foss.predeliver.editDeliverbillNew.WaybillDetailModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type : 'ajax',
		actionMethods : 'POST',
		url:  predeliver.realPath('queryWaybilldetails.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.waybillDetailDtos',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryWaybillForm = predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails;
			var queryParams = queryWaybillForm.getValues();
			var form = queryWaybillForm.getForm(),waybillNos = queryWaybillForm.getValues().waybillNo;
			// 验证运单号输入的行数
			if (!Ext.isEmpty(waybillNos)) {
				var arrayWaybillNo = waybillNos.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg('提示', '运单号为8到9个数字，以回车输入，最多输入50个运单号。', 'error', 2500); 
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg('提示', '运单号为8到9个数字，以回车输入，最多输入50个运单号。', 'error', 2500); 
						return false;	
					}else if(arrayWaybillNo[i].length<8  ||  arrayWaybillNo[i].length>9){
						Ext.ux.Toast.msg('提示', '运单号为8到9个数字，以回车输入，最多输入50个运单号。', 'error', 2500); 
						return false;
					}
				}
			}		
			if(!form.isValid())
			{
				Ext.ux.Toast.msg('提示', '请仔细检查查询条件!', 'error', 2500);
				return;
			}
			Ext.apply(operation,{
				params : {
					'vo.waybillDetailNewQueryDto.waybillNo' : queryParams.waybillNo,
					'vo.waybillDetailNewQueryDto.productCode' : queryParams.productCode,
					'vo.waybillDetailNewQueryDto.preDeliverDate' : queryParams.preDeliverDate,
					'vo.waybillDetailNewQueryDto.goodsStatus' : queryParams.goodsStatus,
					'vo.waybillDetailNewQueryDto.vehicleType' : queryParams.vehicleType,
					'vo.waybillDetailNewQueryDto.specialNoType' : queryParams.specialNoType,
					'vo.waybillDetailNewQueryDto.lateNo' : queryParams.lateNo,
					'vo.waybillDetailNewQueryDto.tallymanReturnReason' : queryParams.tallymanReturnReason,
					'vo.waybillDetailNewQueryDto.noCoordinateWaybill' : queryParams.noCoordinateWaybill,
					'vo.waybillDetailNewQueryDto.noPartitionWaybill' : queryParams.noPartitionWaybill,
					'vo.waybillDetailNewQueryDto.vehicleNo' : queryParams.vehicleNo,
					'vo.waybillDetailNewQueryDto.deliverGrandArea' : queryParams.deliverGrandArea,
					'vo.waybillDetailNewQueryDto.deliverSmallArea' : queryParams.deliverSmallArea,
					'vo.waybillDetailNewQueryDto.defaultOneMonth' : queryParams.defaultOneMonth,
					'vo.waybillDetailNewQueryDto.receiveCustomerCityCode' : queryParams.cityCode,
					'vo.waybillDetailNewQueryDto.receiveCustomerProvCode' : queryParams.privateCode,
					'vo.waybillDetailNewQueryDto.receiveCustomerDistCode' : queryParams.districtCode,
					'vo.waybillDetailNewQueryDto.deliverInga' : queryParams.deliverInga,
					'vo.waybillDetailNewQueryDto.noDeliverInga' : queryParams.noDeliverInga,
					'vo.waybillDetailNewQueryDto.uitraLongDelivery' : queryParams.uitraLongDelivery,
					'vo.waybillDetailNewQueryDto.isExhibition' : queryParams.isExhibition,
					'vo.waybillDetailNewQueryDto.pieceInspection' : queryParams.pieceInspection,
					'vo.waybillDetailNewQueryDto.deliveryTimeInterval' : queryParams.deliveryTimeInterval,
					'vo.waybillDetailNewQueryDto.arrivedDateFrom' : queryParams.arrivedDateFrom,
					'vo.waybillDetailNewQueryDto.arrivedDateTo' : queryParams.arrivedDateTo,
					'vo.waybillDetailNewQueryDto.stockDateFrom' : queryParams.stockDateFrom,
					'vo.waybillDetailNewQueryDto.stockDateTo' : queryParams.stockDateTo,
					'vo.waybillDetailNewQueryDto.isWithPeople' : queryParams.isWithPeople
				}
			});
		},
		load : function(store, records, successful,eOpts)  {
			if(successful==false){
				if(!store.proxy.reader.rawData.success){
					Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
				}
			}
			var data = store.getProxy().getReader().rawData,
			countDto =data.vo.waybillDeliverNewCountDto,
			totalGoodsQty=Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsQty');
			totalGoodsWeight=Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsWeight');
			totalGoodsVolume=Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsVolume');
			if(countDto!=null){
				if(countDto.totalGoodsQty!=null){
					totalGoodsQty.setValue(countDto.totalGoodsQty);
				}else{
					totalGoodsQty.setValue(0);
				}
				if(countDto.totalGoodsWeight!=null){
					totalGoodsWeight.setValue(countDto.totalGoodsWeight.toFixed(1));
				}else{
					totalGoodsWeight.setValue(0);
				}
				if(countDto.totalGoodsVolume!=null){
					totalGoodsVolume.setValue(countDto.totalGoodsVolume.toFixed(2));
				}else{
					totalGoodsVolume.setValue(0);
				}
			}else{
				totalGoodsQty.setValue(0);
				totalGoodsWeight.setValue(0);
				totalGoodsVolume.setValue(0);
			}
			
		}
	}
});
//送货时间段集合
Ext.define('Foss.predeliver.editDeliverbillNew.deliveryTimeIntervalStore',{
    extend: 'Ext.data.Store',
    fields: [
        {name: 'valueCode',  type: 'string'},
        {name: 'valueName',  type: 'string'}
    ],
    data: {
        'items':[
            {'valueCode':'','valueName':'全部'},
            {'valueCode':'全天','valueName':'全天'},
            {'valueCode':'上午','valueName':'上午'},
            {'valueCode':'下午','valueName':'下午'},
            {'valueCode':'上午+下午','valueName':'上午+下午'},
            {'valueCode':'上午+全天','valueName':'上午+全天'},
            {'valueCode':'下午+全天','valueName':'下午+全天'}
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
predeliver.editDeliverbillNew.deptCode = FossUserContext.getCurrentDept().code;
predeliver.editDeliverbillNew.deptName = FossUserContext.getCurrentDept().name;
predeliver.editDeliverbillNew.vehicleNoTruckType = null;
// 查询运单form——运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails',{
	title : '查询排单运单',
	extend : 'Ext.form.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnWidth : 1,
    //收缩
    collapsible: true,
    //动画收缩
    animCollapse: true,
	defaults : {
		margin : '0 5 0 5',
		labelWidth : 65
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name : 'waybillNo',
    		labelWidth : 40,
    		xtype : 'textarea',
    		fieldLabel : '单号', //暂时写死，留待i18n处理
    		columnWidth : .18,
			emptyText : '运单号为8到9个数字，以回车输入，最多输入50个运单号。',
			regexText : '运单号为8到9个数字，以回车输入，最多输入50个运单号。',
    		//grow : true,
    		//growMin : 70,
    		//growMax : 80,
			 height:100,
    		regex : /^([0-9]{8,9}\n?)+$/i
        },{
			xtype: 'checkboxgroup', 
			margin : '7 -10 0 15',
			columnWidth : .02,
			items: [     
				{
					id : 'flag',
					name:'defaultOneMonth',
					inputValue : 'Y',
					handler : function(){
						var flag=Ext.getCmp('flag').getValue();
						var from=this.up('form').getForm();
						if(flag){
							//禁用掉其余的查询条件
							from.findField('preDeliverDate').disable();//送货日期
							from.findField('productCode').disable();//运输性质
							from.findField('vehicleType').disable();//所属车队组
							from.findField('deliverGrandArea').disable();//送货大区
							from.findField('deliverSmallArea').disable();//送货小区
							from.findField('goodsStatus').setValue(' ');
							from.findField('goodsStatus').disable();//货物状态
							from.findField('vehicleNo').disable();//车牌号
							Ext.getCmp('checkGroup_ShippingDetails').disable();//复选框组合
							
							from.findField('deliveryTimeInterval').disable();//货物状态
							from.findField('privateCode').disable();//
							from.findField('cityCode').disable();//
							from.findField('districtCode').disable();//
							Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').disable();//到达时间
							Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').disable();//库存时间
							
							Ext.ux.Toast.msg('提示', '默认查询送货日期为当前日期之前30天的运单！', 'info', 3000);
						
						}else{
							//解除其余条件的禁用
							from.findField('preDeliverDate').enable();//送货日期
							from.findField('productCode').enable();//运输性质
							from.findField('vehicleType').enable();//所属车队组
							from.findField('deliverGrandArea').enable();//送货大区
							from.findField('deliverSmallArea').enable();//送货小区
							from.findField('goodsStatus').enable();//货物状态
							from.findField('vehicleNo').enable();//车牌号
							from.findField('privateCode').enable();//
							from.findField('cityCode').enable();//
							from.findField('districtCode').enable();//
							Ext.getCmp('checkGroup_ShippingDetails').enable();//复选框组合
							from.findField('deliveryTimeInterval').enable();//货物状态
							Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').enable();//到达时间
							Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').enable();//库存时间
						}
					}
				}  
			]  
		},{
			xtype: 'datefield',
			editable : false,
			allowBlank:false,
			name: 'preDeliverDate',
			format : 'Y-m-d',
			fieldLabel: '送货日期',	//暂时写死，留待i18n处理
			value:Ext.Date.format(new Date(),'Y-m-d'),
			columnWidth:.16
		},{
			xtype:'combo',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			value : '',
			editable:false,
			name : 'productCode',
			columnWidth : .16,
			store : Ext.create('Foss.pkp.ProductStore'),
			fieldLabel : '运输性质'//暂时写死，留待i18n处理
		},{
			xtype : 'combobox',
			fieldLabel : '货物状态',//暂时写死，留待i18n处理
			columnWidth : .16,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			name : 'goodsStatus',
			editable : false,
			value : ' ',
			store : Ext.create('Foss.predeliver.store.GoodsStatusStore'),
      listeners: {
        'change': function(goodsStatus) {
          if ('ARRIVED' == goodsStatus.getValue()) {
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').enable();//到达时间
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').disable();//库存时间
          } else if ('IN_STOCK' == goodsStatus.getValue()) {
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').disable();//到达时间
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').enable();//库存时间
          } else if ('ONTHEWAY' == goodsStatus.getValue()) {
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').disable();//到达时间
						Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').disable();//库存时间
          } else {
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_arrivedDate_Id').enable();//到达时间
            Ext.getCmp('Foss_predeliver_editDeliverbill_New_stockDate_Id').enable();//库存时间
          }
        }
      }
		},{
			xtype : 'commonmotorcadeselector',
			fieldLabel : '所属车队组',//暂时写死，留待i18n处理
			columnWidth : .18,
			name : 'vehicleType',
			queryAllFlag : true,
			labelWidth : 80,
			loopOrgCode : predeliver.fleetCode
		},{
			name : 'vehicleNo',
			xtype : 'commontruckselector', 
			queryAllFlag : true,
			labelWidth : 55,
			loopOrgCode : predeliver.fleetCode,
			fieldLabel : '车牌号',//暂时写死，留待i18n处理
			columnWidth : .14
		},{
            //fieldLabel : '行政区域',
            columnWidth: .32,
            margin: '2 2 0 -12',
            provinceLabelWidth:65,
            provinceWidth : 173,
            allowBlank:true,
            cityWidth : 101,
            cityLabel : '',
            cityName : 'cityCode',//名称
            provinceLabel : '行政区域',
            provinceName:'privateCode',//省名称
            areaLabel : '',
            areaName : 'districtCode',// 县名称
            areaWidth : 101,
            areaIsBlank : true,
            cityIsBlank : true,
            provinceIsBlank : true,
            type : 'P-C-C',
            xtype : 'linkregincombselector'
        },{
			xtype : 'commonbigregionsdeselector',
			fieldLabel : '送货大区',//暂时写死，留待i18n处理
			columnWidth : .16,
			//queryAllFlag : true,
			name : 'deliverGrandArea'//,
			//management: predeliver.fleetCode
		},{
			xtype : 'commonmultismallzoneselector',
			fieldLabel : '送货小区',//暂时写死，留待i18n处理
			//margin : '0 0 0 -12',
			columnWidth : .18,
			labelWidth : 80,
			regionType : 'DE',
			name : 'deliverSmallArea',
			queryAllFlag : true,
			loopOrgCode : predeliver.fleetCode
		},{
			xtype:'combo',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			value : '',
			editable:false,
			name : 'deliveryTimeInterval',
			columnWidth : .14,
			labelWidth : 80,
			store : Ext.create('Foss.predeliver.editDeliverbillNew.deliveryTimeIntervalStore'),
			fieldLabel : '送货时间段'
		},{
        xtype : 'rangeDateField',
        fieldLabel : '到达时间',
        fieldId : 'Foss_predeliver_editDeliverbill_New_arrivedDate',
        id:'Foss_predeliver_editDeliverbill_New_arrivedDate_Id',
        dateType : 'datetimefield_date97',
        labelWidth: 65,
        margin: '2 2 2 11',
        fromName : 'arrivedDateFrom',
        toName : 'arrivedDateTo',
        fromValue: null,
        toValue: null,
        editable:false,
        columnWidth : .35
    },{
        xtype : 'rangeDateField',
        fieldLabel : '入库时间',
        fieldId : 'Foss_predeliver_editDeliverbill_New_stockDate',
        id: 'Foss_predeliver_editDeliverbill_New_stockDate_Id',
        dateType : 'datetimefield_date97',
        labelWidth: 65,
        margin: '0 0 0 -6',
        fromName : 'stockDateFrom',
        toName : 'stockDateTo',
        fromValue: null,
        toValue: null,
        editable:false,
        columnWidth : .325
    },{
			xtype: 'fieldcontainer',
			frame : true,
			id : 'checkGroup_ShippingDetails',
			defaultType: 'checkboxfield',
			layout : 'column',
			margin : '6 0 0 20',
			items: [
				{
					boxLabel  : '特殊运单',
					name      : 'specialNoType',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_specialNoType',
					columnWidth : .08
				}, {
					boxLabel  : '进仓货',
					name      : 'deliverInga',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_deliverInga',
					columnWidth : .08
				}, {
					boxLabel  : '非进仓货',
					name      : 'noDeliverInga',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_noDeliverInga',
					columnWidth : .08
				}, {
					boxLabel  : '超远派送',
					name      : 'uitraLongDelivery',  
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_uitraLongDelivery',
					columnWidth : .08
				}, {
					boxLabel  : '会展货',
					name      : 'isExhibition', 
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_isExhibition',
					columnWidth : .08
				}, {
					boxLabel  : '逐件验货',
					name      : 'pieceInspection',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_pieceInspection',
					columnWidth : .1
				}, {
					boxLabel  : '晚交运单',
					name      : 'lateNo',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_lateNo',
					columnWidth : .1
				}, {
					boxLabel  : '无坐标运单',
					name      : 'noCoordinateWaybill',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_noCoordinateWaybill',
					columnWidth : .1
				}, {
					boxLabel  : '理货员退回',
					name      : 'tallymanReturnReason',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_tallymanReturnReason',
					columnWidth : .1
				}, {
					boxLabel  : '未分区运单',
					name      : 'noPartitionWaybill',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_noPartitionWaybill',
					columnWidth : .1
				}, {
					boxLabel  : '带人货',
					name      : 'isWithPeople',
					inputValue : 'Y',
					id        : 'Foss_predeliver_editDeliverbillNew_QueryWaybillForm_ShippingDetails_isWithPeople',
					columnWidth : .1
				}
			],
			columnWidth : .65
		},{
			border : false,
			xtype : 'container',
			columnWidth : .17,
			height: 25,
			layout : 'column',
			defaults : {
				margin : '1 0 0 20'//上右下左
			},
			items : [{
				xtype : 'button',
				columnWidth : .35,
				height: 25,
				text : '重置',//暂时写死，留待i18n处理
				handler : function() {
					var from=this.up('form').getForm().reset();
				}
			},{
				border : false,
				columnWidth : .01,
				height: 25,
				html : '&nbsp;'
			},{
				columnWidth : .35,
				height: 25,
				xtype : 'button',
				cls : 'yellow_button',
				text : '查询',
				handler : function() {											
					
					var pagingToolbar =predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails
						   .getPagingToolbar();
					pagingToolbar.moveFirst();
				}
			}]
		}]
});

/*运单明细  传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单---*/
predeliver.editDeliverbillNew.queryIsExsitsWayBillRfcs = function( selectRowGrid, selectWaybillNos) {
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
			var waybillNos = json.deliverbillVo.waybillNos;
			var notWaybillrfcNos = json.deliverbillVo.notWaybillrfcNos;
			var waybillString = '';
			var notWaybillrfcString ='';
			if(notWaybillrfcNos.length>0){
				for(var j = 0 ;j<notWaybillrfcNos.length;j++){
					notWaybillrfcString +=notWaybillrfcNos[j]+'<br/>';
				}
				var confirmMsgBox3 = Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),"运单号:" +notWaybillrfcString+"有更改单申请未受理不能排单，剩余运单是否继续排单？",function(btn1){
				if (btn1 == 'yes') {
					for ( var i = 0; i < notWaybillrfcNos.length; i++) {
						predeliver.editDeliverbillNew.waybillsDetailToArrange
							.each(function(item, index,length) {
								if (item.waybillNo == notWaybillrfcNos[i]) {
									predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(item.id);
								}
							});
						}
						if(predeliver.editDeliverbillNew.waybillsDetailToArrange.length == 0){
							selectRowGrid.pagingToolbar.moveFirst();
							return;
						}else{
							if(waybillNos.length>0){
								for(var j = 0 ;j<waybillNos.length;j++){
									waybillString +=waybillNos[j]+'<br/>';
								}
								var confirmMsgBox2 = Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),"运单号:" +waybillString+"有更改单未核审 /未受理不能排单，剩余运单是否继续排单？",function(btn1) {
									if (btn1 == 'yes') {
										for ( var i = 0; i < waybillNos.length; i++) {
											predeliver.editDeliverbillNew.waybillsDetailToArrange
												.each(function(item, index,length) {
													if (item.waybillNo == waybillNos[i]) {
														predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(item.id);
													}
												});
											}
											if(predeliver.editDeliverbillNew.waybillsDetailToArrange.length == 0){
												selectRowGrid.pagingToolbar.moveFirst();
												return;
											}else {
												predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.addWaybillToArrange();
											}
											
									}else {
										return;
									}
								});
							}
								predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.addWaybillToArrange();
							}
					}else {
							return;
						}
	
				});
			}
			else if(waybillNos.length>0){
				for(var j = 0 ;j<waybillNos.length;j++){
					waybillString +=waybillNos[j]+'<br/>';
				}
				//
				var confirmMsgBox2 = Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),"运单号:" +waybillString+"有更改单未核审 /未受理不能排单，剩余运单是否继续排单？",function(btn1) {
					if (btn1 == 'yes') {
						for ( var i = 0; i < waybillNos.length; i++) {
							predeliver.editDeliverbillNew.waybillsDetailToArrange
								.each(function(item, index,length) {
									if (item.waybillNo == waybillNos[i]) {
										predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(item.id);
									}
								});
							}
							if(predeliver.editDeliverbillNew.waybillsDetailToArrange.length == 0){
								selectRowGrid.pagingToolbar.moveFirst();
								return;
							}else {
								
							}
							predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.addWaybillToArrange();
					}else {
						return;
					}
				});
			}else {
				predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.addWaybillToArrange();
			}
		}
	});
}

/**
 * 特殊地址标记 window
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.SpecialAddressTagWindow', {
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
			btn.up('window').close();
		}
	} ]
});

/**
 * 定义地址坐标标记的模型
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.coordinateMarkModel', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'id',
			type: 'string'
		},{
			name: 'waybillNo',
			type: 'string'
		},{
			name: 'actualAddress',
			type: 'string'
		},{
			name: 'matchType',
			type: 'string'
		},{
			name: 'actualSmallzoneName',
			type: 'string'
		},{
			name: 'actualSmallzoneCode',
			type: 'string'
		},{
			name: 'receiveCustomerProvCode',
			type: 'string'
		},{
			name: 'receiveCustomerCityCode',
			type: 'string'
		},{
			name: 'receiveCustomerDistCode',
			type: 'string'
		},{
			name: 'receiveCustomerAddress',
			type: 'string'
		},{
			name: 'phone',
			type: 'string'
		},{
			name: 'tel',
			type: 'string'
		},{
			name: 'longitude',
			type: 'string'
		},{
			name: 'latitude',
			type: 'string'
		},{
			name: 'juheAdress',
			type: 'string'
		}
	]
});

/**
 * 定义地址坐标标记对应Store
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.coordinateMarkStore',{
	extend:'Ext.data.Store',
	model:'Foss.predeliver.editDeliverbillNewIndex.coordinateMarkModel'
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
					Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
			}
	});
}


/**
 * 地址坐标标记 window
 */
Ext.define('Foss.predeliver.editDeliverbillNewIndex.addressCoordinateMark', {
	extend : 'Ext.window.Window',
	title : '送货地址坐标标记',
	id : 'Foss_predeliver_editDeliverbillNewIndex_addressCoordinateMark_Id',
	modal : true,
	resizable : false,
	width : window.screen.availWidth * 0.9,
	height : window.screen.availHeight * 0.8,
	layout:'border',
	defaults : {
		margin : '1 1 1 1'
	},
	items : [ {
		xtype : 'grid',
		frame : true,
		region:'west',
		width : 300,
		height:'100%',
		title : '待标列表',
		store : Ext.create('Foss.predeliver.editDeliverbillNewIndex.coordinateMarkStore'),
		autoScroll : true,
		columns : [ {
			xtype : 'templatecolumn',
			width : 30,
			tpl : '<input type="radio" name="select" id="{id}" />'
		}, {
			dataIndex : 'waybillNo',
			align : 'center',
			text : '运单号'
		}, {
			dataIndex : 'actualAddress',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '实际送货地址'
		}, {
			dataIndex : 'actualSmallzoneName',
			align : 'center',
			text : '建议送货小区'
		} ],
		listeners : {
			'select' : function (rowModel,record,index) {
				Ext.getDom(record.data.id).checked = 'checked';
				Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryAddress_ID').setValue(record.data.actualAddress);
				var longitude = '',latitude = '';
			
				if (record.data.longitude) {
					longitude = record.data.longitude;
					latitude = record.data.latitude;
					Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID').setValue(record.data.longitude+','+record.data.latitude);
					var pointGrid = {"lng":longitude,"lat":latitude};
					var addressGIS = record.data.juheAdress;
					if (!Ext.isEmpty(longitude) && !Ext.isEmpty(latitude)) {
						addressGIS = '';
						showMarkY(pointGrid,addressGIS);//描点
					} else {
						pointGrid = '';
						showMarkY(pointGrid,addressGIS);//描点
					}
				} else {
					Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID').setValue('');
					var addressGIS = record.data.juheAdress;
					showMarkY('',addressGIS);//描点
				}

			}
		}
	}, {
		xtype : 'container',
		region:'center',
		layout:'vbox', 
		defaults : {
			margin : '1 1 1 1'
		},
		items : [ {
			xtype : 'form',
			autoScroll : true,
			width : window.screen.availWidth * 0.9 - 320,
			height : 135,
			frame : true,
			title : '录入框',
			layout : {
				 type: 'table',
			     columns: 2
			},
			defaults : {
				margin : '5 5 5 5',
				height: 25
			},
			items : [ {
				xtype : 'textfield',
				fieldLabel : '实际送货地址',
				labelWidth: 100,
				id: 'EditDeliverbillNewIndex_ActualDeliveryAddress_ID',
				width : 700,
				colspan: 2,
				readOnly:true,
				readOnlyCls: '.x-form-field .x-form-text'
			}, {
				xtype : 'textfield',
				fieldLabel : '实际地址坐标',
				labelWidth: 100,
				id: 'EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID',
				width : 400,
				readOnly:true,
				readOnlyCls: '.x-form-field .x-form-text'
			}, {
				xtype : 'button',
				name: 'btnSaveForm',
				width : 100,
				text : '保存',
				handler : function(btnSaveForm) {

					//待标列表选中记录
					var selRow = btnSaveForm.up('window').down('grid').getSelectionModel().getSelection();
					//修改后的运单坐标值
					var xyValues = Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID').getValue();
					
					if(Ext.isEmpty(xyValues)) {
						Ext.ux.Toast.msg('提示信息', '实际地址坐标不能为空！', 'error', 3000);
						return;
					}
					
					if (selRow.length > 0) {
						xyValues = xyValues.split(',');
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
														if (returnResult.success) {
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
														Ext.ux.Toast.msg('提示信息', result.message, 'error', 3000);
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
								Ext.ux.Toast.msg('提示信息', result.message, 'error', 3000);
								//查找小区异常后，保存修改后的坐标
								saveHandoverXY(obj);
							}
							
						});
					}  else {
						Ext.ux.Toast.msg('提示信息', '请选择待标运单！', 'error', 3000);
					}
				
				}
			} ]
		}, {
			xtype : 'container',//地图区域
			id : 'EditDeliverbillNewIndex_editDeliverbillNew_GIS_P',
			width : window.screen.availWidth * 0.9 - 320,
			height : window.screen.availHeight * 0.57,
			listeners: {
				afterrender : function(field) {
					var provCity = FossUserContext.getCurrentDept().provName + FossUserContext.getCurrentDept().cityName;
					var dmapMark =  new DpMap(field.getId(), {center: provCity,zoom: 13}, function(map) {
						var callFun = function(data) {
							if (data.flag == 'return') {
								Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID').setValue(data.point.lng+','+data.point.lat);
							}
						}
						var  ployfeature = new  DpMap.service.DpMap_polygon(map,field.getId(),{foregroundType:'DELIVERY_REGIONS',  backgroundType:'CITY' });
						var pmarker =new DpMap.service.DpMap_marker(map,field.getId(),{isAddable:false, locateToMarker:true,returnCallback:callFun});
						showMarkY = function (point, addressGIS) {
							var allOverlay = map.getOverlays();
							
							for (var i = 0; i < allOverlay.length; i++) {
								map.removeOverlay(allOverlay[i]);
							}
							pmarker.showModifyMarker(point, addressGIS, map,function(data) {
								if (data.flag == 'return') {
									Ext.getCmp('EditDeliverbillNewIndex_ActualDeliveryCoordinate_ID').setValue(data.point.lng+','+data.point.lat);
								}
							});
						}
					});
				}
			}
		} ]
	} ],
	listeners : {
		close : function () {
			Ext.ComponentManager.unregister(Ext.getCmp('Foss_predeliver_editDeliverbillNewIndex_addressCoordinateMark_Id'));
		}
	}
});

//中间操作——运单明细
Ext.define('Foss.predeliver.ButtonPanelRole_ShippingDetails',{
	extend :'Ext.form.Panel',
	buttonAlign : 'center',
	layout : 'column',
	padding : 0,
	margin : '0 0 0 14',
	width : 125,
	flex : 0,
	items : [{
			xtype : 'label',
			style : 'padding-top:30px;text-align:center',
			columnWidth: 1,
			margin: '-15 0 0 0',
			text : '派送单号：'
		},{
			xtype : 'textfield',
			id : 'Foss_predeliver_ediDeliverbillNew_WaybillDetailDeliverNo',
			name : 'delivebillNo',
			fieldStyle : 'padding-top:5px;text-align:center;color:red;',
			columnWidth: 1,
			margin: '-10 0 0 0',
			readOnly :true,
			value :  predeliver.editDeliverbillNew.deliverbillNo
		},{
			xtype : 'label',
			columnWidth: 1,				
			margin: '-10 0 0 0',
			style : 'padding-top:10px;text-align:center',
			text : '车辆组别：'
		},{
			name : 'vehicleType',
			fieldLabel : '', // 车辆组别
			columnWidth : 1,
			margin: '0 0 0 0',
			xtype : 'commonmotorcadeselector',
			loopOrgCodes : predeliver.fleetCode,
			listeners : {
				'change' : function(field, event, eOpts) {
					var form = field.up('form').getForm(),
						vehicleNo = form.findField('vehicleNo'),
						driverName = form.findField('driverName'),
						driverCode = form.findField('driverCode');
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
						driverCode.setValue('');
						predeliver.editDeliverbillNew.vehicleNoTruckType=null;
	
					}
				}
			}
		},{
			name : 'driverCode',
			xtype: 'textfield',
			hidden : true
		},{
			xtype: 'displayfield',
			style : 'text-align:center',
			fieldLabel: '送货车辆',
			name: 'home_score',
			columnWidth : 1
		},{
			name : 'vehicleNo',
			fieldLabel : '', // 车辆
			columnWidth : 1,
			xtype : 'commontruckselector', 
			allowBlank : false,
			queryAllFlag : true,
			loopOrgCode : predeliver.fleetCode,
			listeners : {
				'select': function(field, records, eOpts) {
						var record = records[0];
						predeliver.editDeliverbillNew.vehicleNoTruckType = record.get('truckType');
				},
				'blur' : function(field, event, eOpts) {
						//根据车牌号、送货日期带出排班信息
						var deliverTime = predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails.getForm().findField('preDeliverDate').getValue();					
						//更新已排单表格下的信息
						var bottomPanel = predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails;
						var ButtonPanelRole =predeliver.ButtonPanelRole_ShippingDetails;
            if (predeliver.editDeliverbillNew.vehicleNoTruckType == '外请车') {
              //如果是外请车，则设置出车任务"送+接"，预计出车时间为"00:00"。反之，如果已天出车任务、预计出车时间再选择车辆未外请车不考虑
              ButtonPanelRole.getForm().findField('carTaskinfo').setValue('1');
              ButtonPanelRole.getForm().findField('preCartaskTime').setValue('00:00');
            }
						//if (predeliver.editDeliverbillNew.vehicleNoTruckType == '外请车') {
							//外请车不查询排班表
						//	bottomPanel.down('displayfield[name=loadRate]').setValue(null);
						//	bottomPanel.down('displayfield[name=nominalRate]').setValue(null);
						//	predeliver.editDeliverbillNew.waybillDetailLoadRate = null;
						//	predeliver.editDeliverbillNew.waybillDetailNominalRate = null;
						//	ButtonPanelRole.down('label[name=expectedBringVolume]').setText("");
						//	ButtonPanelRole.down('label[name=frequencyNo]').setText("");
							
						//		predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails.getDriverName(field.value);
						//	}
						//} else {
						if (field.value != '') {
							var rightGridStore=bottomPanel.getStore();
							//如果已排单里有数据且预计送货日期不为空
							if(rightGridStore.getCount()>0){
								for (var i = 0; i < rightGridStore.getCount(); i++) {
									if(rightGridStore.data.items[i].data.deliverDate!=null){
										deliverTime=rightGridStore.data.items[i].data.deliverDate;
										break;
									}
								}
							}
							Ext.Ajax.request({
								url : predeliver.realPath('queryWaybillDetailRightCount.action'),
								async : false,
								jsonData:{
									'vo' : {
										'waybillDetailNewQueryDto':{
											'deliverDate':Ext.util.Format.date(deliverTime, 'Y-m-d'),
											'vehicleNo':field.value
										},
										'deliverbill': {
											'id':predeliver.editDeliverbillNew.waybillDetail_deliverbillId
										},
										'waybillDeliverNewCountDto': {
											'totalWeight':predeliver.editDeliverbillNew.waybillDetailTotalWeight,
											'totalVolumn':predeliver.editDeliverbillNew.waybillDetailTotalVolumn
										}
									}
								},
								success : function(response) {
									var data = Ext.decode(response.responseText);
									if(!data.success &&(!data.isException)){
										Ext.ux.Toast.msg('提示', data.message, 'error', 2000);
										return;
									}
									if (field.value != '') {
									predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails.getDriverName(field.value);
									}
									predeliver.editDeliverbillNew.waybillDetailRightReset(bottomPanel,ButtonPanelRole,data);
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									field.value='';
									Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
								}
						});
					}
				} 
			}
		},{
			xtype : 'label',
			columnWidth: 1,				
			style : 'padding-top:10px;text-align:center',
			text : '送货司机：'
		},{
			name : 'driverName',
			columnWidth : .33,
			xtype : 'commondriverselector',								
			waybillFlag:'N',//是否是集中接送区域  如果设置值为Y,N
			fleetType:'FLEET_TYPE__SHUTTLE,FLEET_TYPE__SHUTTLE_GOODS,FLEET_TYPE__LONG_DISTANCE',
			columnWidth : 1,
			//车队组类型(包含集中接送货和物流班车)
			listeners : {
				blur : function(field, event, eOpts) {
					// 公共选择器的value为driverCode,rawValue为driverName
					// 更新driverCode
					this.up('form').getForm().findField('driverCode').setValue(field.value);
				}
			}
		},{
			xtype : 'label',
			columnWidth: 1,				
			style : 'padding-top:10px;text-align:center',
			text : '派车类型：'
		},{
			xtype : 'combobox',
			name : 'deliverType',
			columnWidth : 1,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : 'NOMAL',
			store : Ext.create('Foss.predeliver.store.DeliverTypeStore')
		} ,{
			border : false,
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [{
					style : 'margin-top:33px;margin-left:2px;cursor:pointer',
					id : 'predeliver.ButtonPanelRole_Button_Left_Left_ShippingDetails',
					xtype : 'button',
					tooltip: '点击缩回',
					hidden : true,
					maxWidth : 15,
					cls : 'flexleft',
					columnWidth : .15,
					listeners:{
						click: {
							element: 'el', 
							fn: function(){ 
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left_ShippingDetails').hide();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right_ShippingDetails').show();//显示下一个按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right_ShippingDetails').show();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left_ShippingDetails').hide();//显示上一个按钮
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').setWidth((document.body.scrollWidth)/4*2)
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').show();
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').doComponentLayout();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').setWidth((document.body.scrollWidth)/4+130);
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').show();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').doComponentLayout();
							}
						}
					}
				},{
					style : 'margin-top:33px;margin-left:2px;cursor:pointer',
					id : 'predeliver.ButtonPanelRole_Button_Left_Right_ShippingDetails',
					xtype : 'button',
					maxWidth : 15,
					tooltip: '点击展开',
					cls : 'flexright',
					columnWidth : .15,
					listeners:{
						click: {
							element: 'el', 
							fn: function(){ 
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right_ShippingDetails').hide();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left_ShippingDetails').show();//显示上一个按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right_ShippingDetails').show();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left_ShippingDetails').hide();//显示上一个按钮
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').setWidth((document.body.scrollWidth)/3*2+20);
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').show();
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').doComponentLayout();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').setWidth((document.body.scrollWidth-201)/5);
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').show();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').doComponentLayout();
							}
						}
					}
				},{
				xtype : 'button',
				style : 'margin-top:20px;',
				id: 'predeliver_editDeliverbillNew_waybillDetail_AddArrange_ID',
				iconCls:'deppon_icons_turnright',
				tooltip: '添加至派送单',
				height:23,
				columnWidth : .70,
				handler: function(button) {
					var selectRowGrid =predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails,
					selectRowModel = selectRowGrid.getSelectionModel(),
				    selectRow = selectRowModel.getSelection(),
					selectWaybillNos =new Array();//存放复选框选中的运单							
					//判断是否选中数据
					if(predeliver.editDeliverbillNew.waybillsDetailToArrange.length<1){
						Ext.ux.Toast.msg('提示', '请选择待排单运单',
							'error', 2500);
							return;
					}
					//验证是否选择送货车辆
					var form = this.up('form').getForm(),
					vehicleNo = form.findField('vehicleNo');
					// 如果外请车没有对应的司机
					if (predeliver.editDeliverbillNew.vehicleNoTruckType == '外请车'&& form.findField('driverName').getRawValue() == '') {
						Ext.ux.Toast.msg('提示', '外请车必须选择司机!');
						return ;
					}
					if(!predeliver.ButtonPanelRole_ShippingDetails.getForm().isValid()){
						Ext.ux.Toast.msg('提示', '有必填项（页面标识红叉项）未填写，请填写完整！',
							'error', 2500);
							return;
					};
					var rightGridStore=predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore();
					var deliverDate=null;
					//如果已排单里有数据且预计送货日期不为空
					if(rightGridStore.getCount()>0){
						for (var i = 0; i < rightGridStore.getCount(); i++) {
							if(rightGridStore.data.items[i].data.deliverDate!=null){
								deliverDate=rightGridStore.data.items[i].data.deliverDate;
								break;
							}
						}
					}else{
						for (var i = 0; i < selectRow.length; i++) {
							if(!Ext.isEmpty(selectRow[i].get("deliverDate"))){
								deliverDate=selectRow[i].get("deliverDate");
								break;
							}
						}
					}
					var uitraLong=false;//是否需要提醒
					var deliverDateDiff=false;
					predeliver.editDeliverbillNew.waybillsDetailToArrange
						.each(function(item, index,length) {
							if(Ext.isEmpty(item.actualSmallzoneCode) && Ext.isEmpty(item.addressType) &&
									(Ext.isEmpty(item.uitraLongDelivery)||'N'==item.uitraLongDelivery)){
								predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(item.id);
								uitraLong=true;
							}else{
									if(deliverDate!=null && item.deliverDate!=deliverDate){
										deliverDateDiff=true;
										predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(item.id);
									}else{
										selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
									}
								}
								
						});
					if(predeliver.editDeliverbillNew.waybillsDetailToArrange.length<1){
						selectRowModel.deselectAll();
						if(uitraLong){
							Ext.ux.Toast.msg('提示', '除特殊地址和超远派送运单外，无送货小区不能排单，请添加小区后再排单！',
									'error', 3000);
							
						}
						if(deliverDateDiff){
							if(rightGridStore.getCount()>0){
								Ext.ux.Toast.msg('提示', '所选的运单的送货日期必须与已排运单的送货日期一致!',
								'info', 3000);
							}else{
								Ext.ux.Toast.msg('提示', '所选的运单的送货日期不完全一致,无法排同一个派送单!',
								'info', 3000);
							}
						}
						return;
					}else{
						if(uitraLong){
							Ext.ux.Toast.msg('提示', '除特殊地址和超远派送运单外，无送货小区不能排单,系统已自动过滤!',
									'info', 3000);
						}
						if(deliverDateDiff){
							if(rightGridStore.getCount()>0){
								Ext.ux.Toast.msg('提示', '所选的运单的送货日期必须与已排运单的送货日期一致!',
								'info', 3000);
							}else{
								Ext.ux.Toast.msg('提示', '所选的运单的送货日期不完全一致,无法排同一个派送单!',
								'info', 3000);
							}
							
						}
					}
					predeliver.editDeliverbillNew.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
				}
			},{
				xtype : 'button',
				iconCls:'deppon_icons_turnleft',
				tooltip: '移除出派送单',
				height:23,
				columnWidth : .70,
				handler: function(button) {
					var selectRow = predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getSelectionModel().getSelection();
					if (selectRow.length == 0) {
						Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
								predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
						return;
					}
					Ext.Msg.confirm(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveDetail'),
						function(btn, text) {
							if (btn == "yes") {
								var ids = '';
								for ( var i = 0; i < selectRow.length; i++) {
									ids = selectRow[i].data.id+ ","+ ids;
								}
								predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.deleteDeliverbillDetails(ids);
						}
					});							
				}
			},{
					style : 'margin-top:-13px;cursor:pointer',
					id : 'predeliver.ButtonPanelRole_Button_Right_Left_ShippingDetails',
					hidden : true,
					xtype : 'button',
					tooltip: '点击缩回',
					maxWidth : 15,
					cls : 'flexright',
					columnWidth : .15,
					listeners:{
						click: {
							element: 'el', 
							fn: function(){ 
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left_ShippingDetails').hide();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right_ShippingDetails').show();//显示下一个按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right_ShippingDetails').show();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left_ShippingDetails').hide();//显示上一个按钮
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').setWidth((document.body.scrollWidth)/4*2);
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').show();
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').doComponentLayout();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').setWidth((document.body.scrollWidth)/4+130);
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').show();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').doComponentLayout();
							}
						}
					}
				},{
					style : 'margin-top:-13px;cursor:pointer',
					id : 'predeliver.ButtonPanelRole_Button_Right_Right_ShippingDetails',
					xtype : 'button',
					maxWidth : 15,
					tooltip: '点击展开',
					cls : 'flexleft',
					columnWidth : .15,
					listeners:{
						click: {
							element: 'el', 
							fn: function(){ 
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Right_ShippingDetails').hide();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Right_Left_ShippingDetails').show();//显示上一个按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Right_ShippingDetails').show();//点击隐藏自身按钮
								Ext.getCmp('predeliver.ButtonPanelRole_Button_Left_Left_ShippingDetails').hide();//显示上一个按钮
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').setWidth((document.body.scrollWidth-201)/5);
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').show();
								Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails').doComponentLayout();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').setWidth((document.body.scrollWidth-201)/3*2+155);
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').show();
								Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails').doComponentLayout();
							}
						}
					}
				}]
		},{
			xtype : 'textfield',
			style : 'margin-top:26px',
			id: 'predeliver_editDeliverbillNew_waybillDetail_bywaybillNumber_ID',
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
						if (predeliver.editDeliverbillNew.waybillDetail_deliverbillId == "") {
							if(!form.isValid()) {
								Ext.ux.Toast.msg('提示', '有必填项（页面标识红叉项）未填写，请填写完整！', 'error', 3000);
								return;
							}
						}
						var deliverbillBasicInfo = predeliver.ButtonPanelRole_ShippingDetails.getValues();
						var rightGridStore=predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore();
						var deliverDate=null;
						//如果已排单里有数据且预计送货日期不为空
						if(rightGridStore.getCount()>0){
							for (var i = 0; i < rightGridStore.getCount(); i++) {
								if(rightGridStore.data.items[i].data.deliverDate!=null){
									deliverDate=rightGridStore.data.items[i].data.deliverDate;
									break;
								}
							}
						}
						var newVo = {
								'vo': {
									'deliverbill' : {
										'id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
										'deliverbillNo' : deliverbillBasicInfo.delivebillNo,
										'vehicleNo' : deliverbillBasicInfo.vehicleNo,
										'delStatus' :"",
										'driverCode' : deliverbillBasicInfo.driverCode,
										'deliverType' : deliverbillBasicInfo.deliverType,
										'driverName' : predeliver.ButtonPanelRole_ShippingDetails.getForm().findField("driverName").rawValue
									},'waybillNo':field.value,
									'deliverDate':deliverDate
								}
							}
						Ext.Ajax.request({
							url:predeliver.realPath('waybillDetailAddToArrangeByWaybillNo.action'),
							jsonData: newVo,
							success: function(response){
								var result = Ext.decode(response.responseText);
								predeliver.editDeliverbillNew.waybillDetail_deliverbillId = result.vo.deliverbill.id;
								predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
								predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
								Ext.getCmp('predeliver_editDeliverbillNew_waybillDetail_bywaybillNumber_ID').setValue('');
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
      editable: false,
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
		{
			xtype : 'label',
			columnWidth: .40,				
			style : 'padding-top:7px;text-align:right',
			text : '班次：'
		},{
			xtype : 'combobox',
			columnWidth: .60,
			queryMode : 'local',
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
		{
			xtype : 'label',
			columnWidth: .55,			
			style : 'padding-top:10px;text-align:right',
			text : '带货(F)：'
		},{
			xtype : 'textfield',
			columnWidth: .45,
			margin: '0 0 0 0',
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
		{
			xtype : 'button',
			text : '特殊地址标记',
			style: {
				margin:'10px 0 0 0'
			},
			columnWidth: 1,	
			listeners : {	
				click : function(btn) {
					var record = {};
					var count = 0;
					var waitGrid = Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails');
					var sortedGrid = Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails');
					var waitRowModel = waitGrid.getSelectionModel();
					var sortedRowModel = sortedGrid.getSelectionModel();
					count = waitRowModel.getCount() + sortedRowModel.getCount();
					if (count == 1) {
						if (waitRowModel.hasSelection()) {
							record = waitRowModel.getLastSelected();
						}
						
						if (sortedRowModel.hasSelection()) {
							record = sortedRowModel.getLastSelected();
						}
						
						if (record) {
							
							var consigneeAddress = "";
							
							if (record.get('consigneeAddress')) {
								consigneeAddress = record.get('consigneeAddress').replace(new RegExp('-',"gm"), '');
							}
						
							Ext.Ajax.request({
							    url : predeliver.realPath('selectSpecialAddress.action'),
							    params : {
							    	deliveryAddress : consigneeAddress
							    },
							    success : function(response){
							    	var win = Ext.create('Foss.predeliver.editDeliverbillNewIndex.SpecialAddressTagWindow');
							    	
							    	var obj = Ext.decode(response.responseText);
							    	
							    	if (obj.success) {
							    		if (obj.specialDeliveryAddressEntity) {
								    		if (obj.specialDeliveryAddressEntity.addressType) {
								    			win.down('combobox[name=addressType]').setValue(obj.specialDeliveryAddressEntity.addressType);
								    		}
								    		if (obj.specialDeliveryAddressEntity.vehicleNo) {
								    			win.down('commontruckselector[name=vehicleNo]').setValue(obj.specialDeliveryAddressEntity.vehicleNo);
								    		}
								    		
								    		if (obj.specialDeliveryAddressEntity.id) {
												win.down('hidden[name=id]').setValue(obj.specialDeliveryAddressEntity.id);
											}
								    	}
								    	
								    	win.down('hidden[name=deliveryAddress]').setValue(consigneeAddress);
										
										if (record.get('actualSmallzoneCode')) {
											win.down('hidden[name=deliveryResidenceCode]').setValue(record.get('actualSmallzoneCode'));
										}
										
										if (record.get('actualSmallzoneName')) {
											win.down('hidden[name=deliveryResidenceName]').setValue(record.get('actualSmallzoneName'));
										}
										if (record.get('waybillNo')) {
											win.down('hidden[name=waybillNo]').setValue(record.get('waybillNo'));
										}
										win.show();
							    	} else {
							    		Ext.ux.Toast.msg('提示信息', obj.message, 'success', 3000);
							    	}
							    	
							    	
							        
							    },
							    failure : function(response, opts) {
							    	var obj = Ext.decode(response.responseText);
							    	Ext.ux.Toast.msg('提示信息', obj.message, 'success', 3000);
							    }
							});	
						}
						
					} else {
						Ext.ux.Toast.msg('提示信息','待选列表或已排单列表至少选中一行且只能选中一行数据进行特殊地址标记操作', 'success', 3000);
					}
				}
			}
		},{
			xtype : 'button',
			text : '地址坐标标记',
			style: {
				margin:'10px 0 0 0'
			},
			listeners:{
				click : function (btn) {
					
					var waitGrid = Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails');
					var sortedGrid = Ext.getCmp('predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails');
					var waitRowModel = waitGrid.getSelectionModel();
					var sortedRowModel = sortedGrid.getSelectionModel();
					
					var waybillNos = []; 
					if (!waitRowModel.hasSelection() && !sortedRowModel.hasSelection()) {
						Ext.ux.Toast.msg('提示信息','待选列表或已排单列表至少选中一行数据进行地址坐标标记操作', 'success', 3000);
						return;
					}
					
					Ext.Array.each(waitRowModel.getSelection(), function (record, index, waitRowModel) {
						waybillNos.push(record.get('waybillNo'));
					});
					
					Ext.Array.each(sortedRowModel.getSelection(), function (record, index, sortedRowModel) {
						waybillNos.push(record.get('waybillNo'));
					});
					
					//根据条件查询特殊地址历史库的特殊地址类型
					Ext.Ajax.request({
							url : predeliver.realPath('visibleAddressMark.action'),
							async : false,
							jsonData:{
								'vo': {
									'specialWayBillNO': waybillNos.join(',')
								}
							},
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.vo.listAddresMarks) {
										var markWin = Ext.create('Foss.predeliver.editDeliverbillNewIndex.addressCoordinateMark');
										markWin.down('grid').getStore().loadData(json.vo.listAddresMarks);
										markWin.show();
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg('提示信息', result.message, 'error', 3000);
							}
					});
				}
			},
			columnWidth: 1
		}]
});

// 定义checkbox model---已选运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.CheckboxModelAlreadyWaybillDetails', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsDetailToAlready.add(record.data.id,
					record.data);
		},
		deselect : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsDetailToAlready.removeAtKey(record.data.id);
		}
	}
});

// 定义已排单运单的表格——运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails',{
	extend : 'Ext.grid.Panel',
	frame : true,
	sortableColumns : false,
	id :'predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails',
	//title : '已排单运单',
	//flex : 0,
	width : 410, //(document.body.scrollWidth)/3+10,
	margin : '10 -2 5 0',
	height : 670,
	autoScroll : true,
	//columnLines : true,
	cellEditor : null,		
	selModel: {
		injectCheckbox: 0,
		mode: "SIMPLE"
	},
	//selType: 'checkboxmodel',
	tbar : [{
		xtype: 'label',
		text: '已排单运单 ',
		width : 80,
		margin : '10 0 0 0',
		style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:5px solid #3d74b7;'
		},
		{
						xtype: 'button',
						text: '排序',
						width:35,
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 3
						}),
						//cls: 'x-btn x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon',
						handler: function(button) {
								if (!Ext.isEmpty(predeliver.editDeliverbillNew.waybillDetail_deliverbillId) && predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().getCount() > 0) {
										Ext.Ajax.request({
												url : predeliver.realPath('visibleIfExistsNoCoord.action'),
												params : {
													'deliverbillVo.deliverbill.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId
												},
												success : function(response) {
													  //调用可视化界面
                            Ext.Ajax.request({
                              url : predeliver.realPath('visiblebillInfo.action'),
                              params :{
                                'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId
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
                                markWin.down('grid').getStore().setDeliverbillId(predeliver.editDeliverbillNew.waybillDetail_deliverbillId);
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
		},{
			text : '刷新',
			width:35,
			handler : function (btn) {
				predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
      }},'->',{xtype:'panel',border: 1,width:35,height:20,cls:'predeliver-beforeNoticeIndex-row-purole'},
		{xtype:'label',text:'无坐标运单'}
	],
	dockedItems : [{
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
				labelWidth : 60,
				name:'totalTicket',
				value: '0',
				columnWidth:.3,
				fieldLabel: '总票数'
		 },{
				xtype:'displayfield',
				allowBlank:true,
				name:'totalCount',
				value: '0',
				labelWidth : 60,
				columnWidth:.3,
				fieldLabel: '总件数'
		},{
				xtype:'displayfield',
				columnWidth:.3,
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
				columnWidth:.5,
				name : 'totalVolumn',
				fieldLabel: '总体积(m³)'
		},{
				xtype:'displayfield',
				allowBlank:true,
				name:'totalWeight',
				value: '0',
				labelWidth : 100,
				columnWidth:.5,
				name : 'totalPayAmount',
				fieldLabel: '到付金额(元)'
		},{
			xtype:'displayfield',
			columnWidth:.5,
			value: '0/0',
			width: 200,
			labelWidth : 120,
			name: 'nominalRate',
			fieldLabel: '净空(方)/载重(吨)'
		},{
			xtype:'displayfield',
			columnWidth:.5,
			width: 200,
			value: '0%/0%',
			labelWidth : 125,
			name: 'loadRate',
			style: 'marginTop:1px',
			fieldLabel: '装载率(体积/重量)'
		}]
	}],
		columns : [/*{
			xtype : 'actioncolumn',
			text : '操作',
			width : 48,
			layout : 'column',
			align : 'center',
			items : [
					{
						iconCls : 'deppon_icons_up',
						tooltip : '上移',
						columnWidth : .50,
						handler : function(grid, rowIndex,
								colIndex) {
							if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
								Ext.ux.Toast.msg('提示',
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
										'error', 4000);
								return;
							}

							Ext.Ajax.request({
										url : predeliver.realPath('upgradeDeliverbillDetail.action'),
										params : {
											'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
											'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
											'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
										},
										success : function(response) {
											// 刷新当前页
											predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
										}
							});

							if (grid.getStore().getAt(rowIndex).get('serialNo') == 1) {
								Ext.ux.Toast.msg('提示',
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
										'error', 4000);
								return;
							}
						}
						
					},
					{
						iconCls : 'deppon_icons_down',
						tooltip : '下移',
						handler : function(grid, rowIndex,
								colIndex) {
							if (grid.getStore().getAt(rowIndex).get('serialNo') == grid.getStore().getTotalCount()) {
								Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
										predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theEnd'),
										'error', 4000);
								return;
							}

							Ext.Ajax.request({
										url : predeliver.realPath('downgradeDeliverbillDetail.action'),
										params : {
											'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
											'deliverbillVo.deliverbillDetail.id' : grid.getStore().getAt(rowIndex).get('id'),
											'deliverbillVo.deliverbillDetail.serialNo' : grid.getStore().getAt(rowIndex).get('serialNo')
										},
										success : function(response) {
											// 刷新当前页
											predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
										}
							});
						}
					}]
		},*/
		{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 80,
			header : '运单号'
		},
		{
			dataIndex : 'cashTime',
			align : 'center',
			width : 80,
            xtype: 'ellipsiscolumn',//悬浮提示
			header : '规定兑现时间'
		},
		{
			dataIndex : 'consigneeAddress',
			align : 'center',
			width : 100,
            xtype: 'ellipsiscolumn',//悬浮提示
			header : '送货地址'
		},
		{
			dataIndex : 'preSuggestions',
			align : 'center',
			width : 90,
			xtype: 'templatecolumn',
			header : '预处理建议',
			tpl :  new Ext.XTemplate(
					'<tpl if="this.returnType(regionName,vehicleNo) == \'ALL\' ">',
							'<p>{regionName}<br/>{vehicleNo}</p>',
					'<tpl elseif="this.returnType(regionName,vehicleNo) == \'REGION_NAME\'">',
							'<p>{regionName}</font></p>',
					'<tpl elseif="this.returnType(regionName,vehicleNo) == \'VEHICLENO\'">',
							'<p>{vehicleNo}</p>',
					'</tpl>',
			{
					/*XTemplate 配置：*/
					disableFormats: true,
					/* 成员函数:*/
					returnType: function(regionName,vehicleNo){
						if ((!Ext.isEmpty(regionName))&&(!Ext.isEmpty(vehicleNo))){//全显示
							return 'ALL';
						} else if ((!Ext.isEmpty(regionName))&&(Ext.isEmpty(vehicleNo))) {//只有小区没车牌号
							return 'REGION_NAME';
						} else if((Ext.isEmpty(regionName))&&(!Ext.isEmpty(vehicleNo))) {//只有车牌号没小区
							return 'VEHICLENO';
						}
					}
			}
			)
		},
		{
			dataIndex : 'preArrangeGoodsQty',
			align : 'center',
			width : 60,
			header : '已排件数'
		},
		{
			dataIndex : 'weight',
			align : 'center',
			width : 58,
			header : '排单重量'
		},
		{
			dataIndex : 'goodsVolumeTotal',
			align : 'center',
			width : 58,	
			xtype: 'ellipsiscolumn',
			header : '排单体积'
		},{
			dataIndex : 'dimension',
			align : 'center',
			width : 65,
			xtype: 'ellipsiscolumn',//悬浮提示
			header : '尺寸'
		},{
			dataIndex : 'goodsPackage',
			align : 'center',
			width : 75,
			xtype: 'ellipsiscolumn',
			header : '包装'
		},{
			dataIndex : 'transportType',
			align : 'center',
			width : 80,
			header : '运输性质',
			renderer : function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		},{
			dataIndex : 'deliverDate',
			align : 'center',
			width : 120,
			xtype: 'datecolumn',
			header : '预计送货日期',
			format : 'Y-m-d'
		},{
			dataIndex : 'deliveryTimeInterval',
			align : 'center',
			width : 90,
			header : '送货时间段'
		},{
			dataIndex : 'deliverRange',
			align : 'center',
			width : 90,
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
		},{
			dataIndex : 'payAmount',
			align : 'center',
			width : 80,
			header : '到付金额'
		},{
			dataIndex : 'togetherSendCode',
			align : 'center',
			width : 80,
			header : '合送编码'
		}],
		viewConfig: {
						stripeRows : false,
						enableTextSelection : true,
						getRowClass : function(record, rowIndex, rp, ds) {
										if (Ext.isEmpty(record.data.longitude) || Ext.isEmpty(record.data.latitude)) {
														return 'predeliver-beforeNoticeIndex-row-purole';
										}
						},
						plugins: {
							ptype: 'gridviewdragdrop',
							dragText: '拖拽行记录进行排序,点击保存(提交)后生效!'
						}

		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.predeliver.editDeliverbillNew.DeliverbillStoreDetail');
			me.selModel = Ext.create('Foss.predeliver.editDeliverbillNew.CheckboxModelAlreadyWaybillDetails');
			me.callParent([cfg]);
		}
});
predeliver.editDeliverbillNew.waybillDetailTotalTicket = 0;
predeliver.editDeliverbillNew.waybillDetailTotalCount = 0;
predeliver.editDeliverbillNew.waybillDetailTotalWeight = 0;
predeliver.editDeliverbillNew.waybillDetailTotalVolumn = 0;
predeliver.editDeliverbillNew.waybillDetailTotalPayAmount = 0;
predeliver.editDeliverbillNew.waybillDetailLoadRate = null;
predeliver.editDeliverbillNew.waybillDetailNominalRate = null;
predeliver.editDeliverbillNew.waybillDetailRightReset=function(bottomPanel,ButtonPanelRole,data){
	bottomPanel.down('displayfield[name=loadRate]').setValue(null);
	bottomPanel.down('displayfield[name=nominalRate]').setValue(null);
	predeliver.editDeliverbillNew.waybillDetailLoadRate = null;
	predeliver.editDeliverbillNew.waybillDetailNominalRate = null;
	ButtonPanelRole.down('textfield[name=expectedBringVolume]').setValue("");
	ButtonPanelRole.down('combobox[name=frequencyNo]').setValue("");
	if(data.vo.waybillDeliverNewCountDto==null){
		return;
	}
	if (data.vo.waybillDeliverNewCountDto.loadRate) {//装载率(容量和体积)
		bottomPanel.down('displayfield[name=loadRate]').setValue(data.vo.waybillDeliverNewCountDto.loadRate);
		predeliver.editDeliverbillNew.waybillDetailLoadRate = data.vo.waybillDeliverNewCountDto.loadRate;
	}
	if (data.vo.waybillDeliverNewCountDto.nominalRate) {//额定净空/额定载重率
		bottomPanel.down('displayfield[name=nominalRate]').setValue(data.vo.waybillDeliverNewCountDto.nominalRate);
		predeliver.editDeliverbillNew.waybillDetailNominalRate = data.vo.waybillDeliverNewCountDto.nominalRate;
	}
	if (data.vo.waybillDeliverNewCountDto.expectedBringVolume) {//带货(F)
		ButtonPanelRole.down('textfield[name=expectedBringVolume]').setValue(data.vo.waybillDeliverNewCountDto.expectedBringVolume);
	}
	if (data.vo.waybillDeliverNewCountDto.frequencyNo) {//班次
		ButtonPanelRole.down('combobox[name=frequencyNo]').setValue(data.vo.waybillDeliverNewCountDto.frequencyNo);
	}
	if (data.vo.waybillDeliverNewCountDto.carTaskinfo) {//带出-出车任务
		ButtonPanelRole.getForm().findField('carTaskinfo').setValue(data.vo.waybillDeliverNewCountDto.carTaskinfo);
	}
	if (data.vo.waybillDeliverNewCountDto.preCartaskTime) {//带出-预计出车时间
		ButtonPanelRole.getForm().findField('preCartaskTime').setValue(data.vo.waybillDeliverNewCountDto.preCartaskTime);
	}
	if (data.vo.waybillDeliverNewCountDto.takeGoodsDeptcode) {//带出-带货部门
		ButtonPanelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setValue(data.vo.waybillDeliverNewCountDto.takeGoodsDeptcode);
		ButtonPanelRole.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').setRawValue(data.vo.waybillDeliverNewCountDto.takeGoodsDeptname);
	}
	if (data.vo.waybillDeliverNewCountDto.transferDeptcode) {//带出-转货部门
		ButtonPanelRole.down('commonsaledepartmentselector[name=transferDeptcode]').setValue(data.vo.waybillDeliverNewCountDto.transferDeptcode);
		ButtonPanelRole.down('commonsaledepartmentselector[name=transferDeptcode]').setRawValue(data.vo.waybillDeliverNewCountDto.transferDeptname);
	}
}
predeliver.editDeliverbillNew.waybillDetailVehicleReset=function(totalCount){
	//中间信息
	var ButtonPanelRole =predeliver.ButtonPanelRole_ShippingDetails;
	if (totalCount>0) {
		//如果已排运单有数据，则设置车牌号,车辆组别为只读
		ButtonPanelRole.down('commontruckselector[name=vehicleNo]').readOnlyCls = '.x-form-readonly input, .x-form-readonly textarea{background:transparent!important;text-align:center}';
		ButtonPanelRole.down('commontruckselector[name=vehicleNo]').setReadOnly(true);
		ButtonPanelRole.down('commonmotorcadeselector[name=vehicleType]').setReadOnly(true);
		ButtonPanelRole.getForm().findField("deliverType").setReadOnly(true);
		
	} else {
		ButtonPanelRole.down('commontruckselector[name=vehicleNo]').setReadOnly(false);
		ButtonPanelRole.down('commonmotorcadeselector[name=vehicleType]').setReadOnly(false);
		ButtonPanelRole.getForm().findField("deliverType").setReadOnly(false);
	}	
}

/*运单明细---已排单列表查询*/	
Ext.define('Foss.predeliver.editDeliverbillNew.DeliverbillStoreDetail',{
	extend : 'Ext.data.Store',
	autoLoad : false,
	// 绑定一个模型
	
	model : 'Foss.predeliver.editDeliverbillNew.deliverbillDetailGridModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type : 'ajax',
		timeout: 300000,
		actionMethods : 'POST',
		url:  predeliver.realPath('waybillDetailQueryDetailList.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.waybillDetailArrageDtos'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryWaybillForm = predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails;
			var rightGridStore=predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore();
			var queryParams = queryWaybillForm.getValues();
			var deliverTime = queryParams.preDeliverDate;
			//如果已排单里有数据且预计送货日期不为空
			if(rightGridStore.getCount()>0){
				for (var i = 0; i < rightGridStore.getCount(); i++) {
					if(rightGridStore.data.items[i].data.deliverDate!=null){
						deliverTime=rightGridStore.data.items[i].data.deliverDate;
						break;
					}
				}
			}
			var deliverbillBasicInfo = predeliver.ButtonPanelRole_ShippingDetails.getValues();
			Ext.apply(operation,{
				params : {
					'vo.deliverbill.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
					'vo.waybillDetailNewQueryDto.deliverDate' : Ext.util.Format.date(deliverTime, 'Y-m-d'),
					'vo.waybillDetailNewQueryDto.vehicleNo' : deliverbillBasicInfo.vehicleNo
				}
			});
		},
		load : function(store, records, successful, oOpts) {
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg('提示', data.message, 'error', 2000);
				return;
			}
			//更新已排单表格下的信息
			var bottomPanel = predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails;
			var ButtonPanelRole =predeliver.ButtonPanelRole_ShippingDetails;
			if (data.vo.waybillDeliverNewCountDto.totalTicket) {//总票数
				bottomPanel.down('displayfield[name=totalTicket]').setValue(data.vo.waybillDeliverNewCountDto.totalTicket);
				predeliver.editDeliverbillNew.waybillDetailTotalTicket = data.vo.waybillDeliverNewCountDto.totalTicket;
				//predeliver.editDeliverbillNew.waybillDetailVehicleReset(1);//设置车辆组别跟送货车辆是否只读
			} else {
				//predeliver.editDeliverbillNew.waybillDetailVehicleReset(0);//设置车辆组别跟送货车辆是否只读
				bottomPanel.down('displayfield[name=totalTicket]').setValue(null);
				bottomPanel.down('displayfield[name=totalCount]').setValue(null);
				bottomPanel.down('displayfield[name=totalWeight]').setValue(null);
				bottomPanel.down('displayfield[name=totalVolumn]').setValue(null);
				bottomPanel.down('displayfield[name=totalPayAmount]').setValue(null);
				predeliver.editDeliverbillNew.waybillDetailTotalTicket = 0;
				predeliver.editDeliverbillNew.waybillDetailTotalCount = 0;
				predeliver.editDeliverbillNew.waybillDetailTotalWeight = 0;
				predeliver.editDeliverbillNew.waybillDetailTotalVolumn = 0;
				predeliver.editDeliverbillNew.waybillDetailTotalPayAmount = 0;
			}
			
			if (data.vo.waybillDeliverNewCountDto.totalCount) {//总件数
				bottomPanel.down('displayfield[name=totalCount]').setValue(data.vo.waybillDeliverNewCountDto.totalCount);
				predeliver.editDeliverbillNew.waybillDetailTotalCount = data.vo.waybillDeliverNewCountDto.totalCount;
			}
			if (data.vo.waybillDeliverNewCountDto.totalWeight) {//总重量
				bottomPanel.down('displayfield[name=totalWeight]').setValue(data.vo.waybillDeliverNewCountDto.totalWeight);
				predeliver.editDeliverbillNew.waybillDetailTotalWeight = data.vo.waybillDeliverNewCountDto.totalWeight;
			}
			if (data.vo.waybillDeliverNewCountDto.totalVolumn) {//总体积
				bottomPanel.down('displayfield[name=totalVolumn]').setValue(data.vo.waybillDeliverNewCountDto.totalVolumn);
				predeliver.editDeliverbillNew.waybillDetailTotalVolumn = data.vo.waybillDeliverNewCountDto.totalVolumn;
			}
			if (data.vo.waybillDeliverNewCountDto.totalPayAmount) {//到付金额
				bottomPanel.down('displayfield[name=totalPayAmount]').setValue(data.vo.waybillDeliverNewCountDto.totalPayAmount);
				predeliver.editDeliverbillNew.waybillDetailTotalPayAmount = data.vo.waybillDeliverNewCountDto.totalPayAmount;
			}
			
			predeliver.editDeliverbillNew.waybillDetailRightReset(bottomPanel,ButtonPanelRole,data);
		}
	}
});

// 定义checkbox model  运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.CheckboxModelArrangeWaybillDetail', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsDetailToArrange.add(record.data.id, record.data);
			
			var grid = Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails');
			var totalQtyField = grid.down('displayfield[name=totalQty]');
			totalQtyField.setValue(row.getCount());
			var totalWeightField = grid.down('displayfield[name=totalWeight]');
			var arrangedWeight = parseFloat(record.get('arrangedWeight'));
			var totalWeightValue = parseFloat(totalWeightField.getValue());
			var totalVolumeField = grid.down('displayfield[name=totalVolume]');
			var totalVolumeValue = parseFloat(totalVolumeField.getValue());
			var arrangedVolume = parseFloat(record.get('arrangedVolume'));
			
			if (!arrangedWeight) {
				arrangedWeight = 0;
			}
			if (!totalWeightValue) {
				totalWeightValue = 0;
			}
			if (!totalVolumeValue) {
				totalVolumeValue = 0;
			}
			
			if (!arrangedVolume) {
				arrangedVolume = 0;
			}
			
			if (row.getCount() > 0) {
				totalWeightField.setValue(Ext.Number.toFixed((arrangedWeight + totalWeightValue), 1));
				totalVolumeField.setValue(Ext.Number.toFixed((totalVolumeValue + arrangedVolume), 2));
			} else {
				totalWeightField.setValue(0);
				totalVolumeField.setValue(0);
			}
		},
		deselect : function(row, record, index, eOpts) {
			predeliver.editDeliverbillNew.waybillsDetailToArrange.removeAtKey(record.data.id);
			var grid = Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails');
			var totalQtyField = grid.down('displayfield[name=totalQty]');
			totalQtyField.setValue(row.getCount());
			var totalWeightField = grid.down('displayfield[name=totalWeight]');
			var arrangedWeight = parseFloat(record.get('arrangedWeight'));
			var totalWeightValue = parseFloat(totalWeightField.getValue());
			var totalVolumeField = grid.down('displayfield[name=totalVolume]');
			var totalVolumeValue = parseFloat(totalVolumeField.getValue());
			var arrangedVolume = parseFloat(record.get('arrangedVolume'));
			
			if (!arrangedWeight) {
				arrangedWeight = 0;
			}
			if (!totalWeightValue) {
				totalWeightValue = 0;
			}
			if (!totalVolumeValue) {
				totalVolumeValue = 0;
			}
			
			if (!arrangedVolume) {
				arrangedVolume = 0;
			}
			
			if (row.getCount() > 0) {
				totalWeightField.setValue(Ext.Number.toFixed((totalWeightValue - arrangedWeight), 1));
				totalVolumeField.setValue(Ext.Number.toFixed((totalVolumeValue - arrangedVolume), 2));
			} else {
				totalWeightField.setValue(0);
				totalVolumeField.setValue(0);
			}
		}
	}
});
/*运单明细里的-运单退回按钮*/
Ext.define('Foss.predeliver.editDeliverbillNew.billReturnWin',{
	extend : 'Ext.window.Window',
	title : ' 退回派送部',
	modal : true,
	height:'autoHeight',
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
				store : FossDataDictionary.getDataDictionaryStore('PKP_VISIBLE_WAYBILL_RETURN', null),
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
				Ext.ux.Toast.msg('提示', '请选择退回派送部的退回原因！', 'success', 3000);
				return;
			}
			var returnBillNos = btnSave.up('window').down('hiddenfield[name=returnBills]').getValue();

			var returnReasonNotes = btnSave.up('window').down('textarea[name=returnReasonNotes]').getValue();
			if (returnReasonNotes.length > 500) {
				Ext.ux.Toast.msg('提示', '退回派送部的退回原因备注超过最大字数(500)', 'success', 3000);
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
							
							Ext.Msg.alert('提示', strWaybill, function(optional) {
								if (optional == 'ok') {
									btnSave.up('window').close();
								}
							});
						} else {
							Ext.ux.Toast.msg('提示', ' 退回操作成功', 'success', 3000);
							btnSave.up('window').close();
						}
						predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
				},
				exception : function(response) {
					Ext.ux.Toast.msg('提示', '退回操作出现异常', 'success', 3000);
				}
			});
		}
	}, {
		text : '取消',
		name: 'btnCel',
		handler: function(btnCel) {
				btnCel.up('window').close();
		}
	} ]
});

// 运单明细里修改预处理建议表单Form
Ext.define('Foss.predeliver.editDeliverbillNew.PreprocessForm', {
	extend : 'Ext.form.Panel',
	id:'Foss_predeliver_editDeliverbillNew_PreprocessForm_id',
	defaults : {
		width : 240
	},
	items : [{
		fieldLabel : '送货小区',
		name : 'actualSmallzoneName',
		xtype : 'commonsmallzoneselector',
		regionType : 'DE',
		allowBlank : true,
		management : predeliver.fleetCode,
		columnWidth : .95,
		listeners : {
			'select' : function(field, event, eOpts) {
				var form = field.up('form').getForm(),
					vehicleNo = form.findField('actualVehicleNo');
				if (field.value != '' && field.value != null) {
					Ext.Ajax.request({
						url : predeliver
								.realPath('queryVehicleNoByRegionCode.action'),
						params : {
							'vo.deliverHandoverbillEntity.actualSmallzoneCode' : field.value
						},
						async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if(result.vo.actualVehicleNo!=null && result.vo.actualVehicleNo!=undefined){
								vehicleNo.setValue(result.vo.actualVehicleNo);
							}else{
								vehicleNo.setValue('');
							}
						},exception: function(response) {
							vehicleNo.setValue('');
						}, scope : this
					});
					
				}
				else
				{
					vehicleNo.setValue('');
				}
			}
		}

	},{
		fieldLabel :'车牌号',//车牌号
		name : 'actualVehicleNo',
		readOnly:true,
		xtype:'textfield',
		//xtype:'commontruckselector',
		queryAllFlag : true
	}],
	fbar : [ {
		text : '保存',
		 plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 4
        }),
		handler : function(){
			var me = this;
			var smallZoneCode = me.up('form').query('combobox[name="actualSmallzoneName"]')[0].getValue();
			var smallZoneName = me.up('form').query('combobox[name="actualSmallzoneName"]')[0].getRawValue();
			var vehicleNo = me.up('form').query('textfield[name="actualVehicleNo"]')[0].getValue();
			var record = me.up('form').getForm().getRecord();
			var array = {
                        'vo': {
                            'deliverHandoverbillEntity' :
                            { 
								'waybillNo':record.data.waybillNo,
								'actualSmallzoneCode':smallZoneCode,
								'actualSmallzoneName':smallZoneName,
								'actualVehicleNo':vehicleNo,
								'preDeliverDate':record.data.deliverDate
							}
						}
					};
			if(Ext.isEmpty(smallZoneCode) ){
				Ext.ux.Toast.msg('提示', '送货小区不能为空!',
						'error', 4000);
				return false;
			}else{
				
				Ext.Ajax.request({
					url:predeliver.realPath('updateSuggestionTreatment.action'),
					jsonData: array,
					success: function(response){  
					var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', '修改成功!', 'info', 2000);
						if((!Ext.isEmpty(record.data.actualSmallzoneCode)) && record.data.actualSmallzoneCode !=smallZoneCode){
							var confirmMsg = Ext.Msg.confirm('提示',
		                            '修改小区之后将作废地址库的采集坐标，请确认是否作废？',function(btn) {
		                                if (btn == 'yes') {
		                                	Ext.Ajax.request({
		                                		url : predeliver.realPath('waybilldetailDeleteAddress.action'),
		                                		jsonData : {
		                          				  'handoverBillVo' : {
		                          						  'waybillNo' : record.data.waybillNo,
		                          						  'receiveCustomerProvName' : record.data.actualProvN,
		                          						  'receiveCustomerCityName' : record.data.actualCityN,
		                          						  'receiveCustomerDistName' :record.data.actualDistrictN,
		                          						  'receiveCustomerAddress' : record.data.receiveCustomerAddress,
		                          						  'longitude' : record.data.longitude,
		                          						  'latitude' : record.data.latitude
		                          					  }
		                          				  
		                          			  	},
		                            			success : function(response) {
		                            				Ext.ux.Toast.msg('提示', '作废成功！', 'info', 3000);
		                            			},
		                            			exception : function(response) {
		                            				var result = Ext.decode(response.responseText);
		                            				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
		                            			}
		                            		});
		                                }
							});
						}
						record.set('actualSmallzoneCode',smallZoneCode);
						record.set('actualSmallzoneName',smallZoneName);
						record.set('actualVehicleNo',vehicleNo);
						me.up('window').close();
					},exception: function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', result.message, 'error', 2000);
					}, scope : this
				});
				
			}
		}
	}, {
		text : '取消',
		handler : function(){
			var me = this;
			me.up('window').close();
		}
	} ]
});

//批量预处理建议修改form
Ext.define('Foss.predeliver.editDeliverbillNew.BatchPreprocessForm',{
	extend : 'Foss.predeliver.editDeliverbillNew.PreprocessForm',
	id : 'Foss_predeliver_editDeliverbillNew_BatchPreprocessForm_id',
	selectRecords : null,
	fbar : [ {
		text : '保存',
		 plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 4
        }),
		handler : function(){
			var me = this;
			var smallZoneCode = me.up('form').query('combobox[name="actualSmallzoneName"]')[0].getValue();
			var smallZoneName = me.up('form').query('combobox[name="actualSmallzoneName"]')[0].getRawValue();
			var vehicleNo = me.up('form').query('textfield[name="actualVehicleNo"]')[0].getValue();
			
			var deliverHandoverbillEntityList = [];
			var records = me.up('form').selectRecords;
			Ext.Array.each(records,function(record,index){
				deliverHandoverbillEntityList.push({
					'waybillNo':record.data.waybillNo,
					'actualSmallzoneCode':smallZoneCode,
					'actualSmallzoneName':smallZoneName,
					'actualVehicleNo':vehicleNo,
					'preDeliverDate':record.data.deliverDate
				});
			});
			
			var array = {
                        'vo': {
                            'deliverHandoverbillEntityList' : deliverHandoverbillEntityList
						}
					};
			
			if(Ext.isEmpty(smallZoneCode) ){
				Ext.ux.Toast.msg('提示', '送货小区不能为空!',
						'error', 4000);
				return false;
			}else{
				
				Ext.Ajax.request({
					url:predeliver.realPath('updateBatchSuggestionTreatment.action'),
					jsonData: array,
					success: function(response){  
					var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', '修改成功!', 'info', 2000);
						//清空form表单中的值
						me.up('form').getForm().reset();
						
						var recordArray = Ext.Array.filter(records, function(record,index){
							if((!Ext.isEmpty(record.data.actualSmallzoneCode)) && record.data.actualSmallzoneCode !=smallZoneCode){
								return true;
							}
						});
						
						if(recordArray && recordArray.length>0){
							var confirmMsg = Ext.Msg.confirm('提示',
		                            '修改小区之后将作废地址库的采集坐标，请确认是否作废？',function(btn) {
		                                if (btn == 'yes') {
		                                	var handoverBillVos = [];
		                                	Ext.each(recordArray,function(record,index){
		                                		handoverBillVos.push({
		                                			  'waybillNo' : record.data.waybillNo,
	                          						  'receiveCustomerProvName' : record.data.actualProvN,
	                          						  'receiveCustomerCityName' : record.data.actualCityN,
	                          						  'receiveCustomerDistName' :record.data.actualDistrictN,
	                          						  'receiveCustomerAddress' : record.data.receiveCustomerAddress,
	                          						  'longitude' : record.data.longitude,
	                          						  'latitude' : record.data.latitude
		                                		});
		                                	});
		                                	Ext.Ajax.request({
		                                		url : predeliver.realPath('waybilldetailBatchDeleteAddress.action'),
		                                		jsonData : {
		                          				  'handoverBillVos' : handoverBillVos
		                          			  	},
		                            			success : function(response) {
		                            				Ext.ux.Toast.msg('提示', '作废成功！', 'info', 3000);
		                            			},
		                            			exception : function(response) {
		                            				var result = Ext.decode(response.responseText);
		                            				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
		                            			}
		                            		});
		                                }
							});
						}
						
						Ext.each(recordArray,function(record,index){
							record.set('actualSmallzoneCode',smallZoneCode);
							record.set('actualSmallzoneName',smallZoneName);
							record.set('actualVehicleNo',vehicleNo);
						});
						me.up('window').close();
					},exception: function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', result.message, 'error', 2000);
					}, scope : this
				});
				
			}
			
		}
	}, {
		text : '取消',
		handler : function(){
			var me = this;
			me.up('window').close();
		}
	} ]
});

//运单明细里---修改预处理建议window
predeliver.editDeliverbillNew.PreprocessWindow = function() {

    if (Ext.getCmp('Foss_predeliver_editDeliverbillNew_window_Preprocess_Id')) {
        Ext.getCmp('Foss_predeliver_editDeliverbillNew_window_Preprocess_Id').show();
        return;
    }
    Ext.create('Ext.window.Window', {
        id : 'Foss_predeliver_editDeliverbillNew_window_Preprocess_Id',
        closeAction : 'hide',
		width : 300,
		height : 200,
        title : '预处理建议修改',
        modal:true,
        resizable : false,
        items : [Ext.create('Foss.predeliver.editDeliverbillNew.PreprocessForm')]
    }).show();
}
// 定义待选列表查询结果的表格——运单明细
Ext.define('Foss.predeliver.editDeliverbillNew.WaybillToListQueryResultsGrid_ShippingDetails',{
	extend : 'Ext.grid.Panel',
	frame : true,
	id : 'predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails',
	tbar : [{
		xtype: 'label',
		text: '待选列表 ',
		width : 110,
		margin : '10 0 0 0',
		style: 'font-weight:900;color:#3d74b7;font-size:15px;border-bottom:5px solid #3d74b7;'
		},'->',{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_waitToBeDisposed'
			},
			{
				xtype : 'label',
				text : '理货员退回'
			},{
				text: '批量修改预处理建议',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{seconds:3}),
				hidden : false,
				handler: function(btn){
					var rowSelectionModel = btn.up('grid').getSelectionModel();
					if(!rowSelectionModel.hasSelection()){
						Ext.ux.Toast.msg('提示信息','至少选中一行数据进行修改操作', 'success', 3000);/*"至少选中一行数据进行修改"*/
			        	return ; 
					}
					var records = rowSelectionModel.getSelection();
					
					if (Ext.getCmp('Foss_predeliver_editDeliverbillNew_window_BatchPreprocess_Id')) {
				        Ext.getCmp('Foss_predeliver_editDeliverbillNew_window_BatchPreprocess_Id').show();
				        var form = Ext.getCmp('Foss_predeliver_editDeliverbillNew_BatchPreprocessForm_id');
				        form.selectRecords = records;
				        return;
				    }
				    
					Ext.create('Ext.window.Window', {
						id : 'Foss_predeliver_editDeliverbillNew_window_BatchPreprocess_Id',
				        closeAction : 'hide',
						width : 300,
						height : 200,
				        title : '预处理建议批量修改',
				        modal:true,
				        resizable : false,
				        items : [Ext.create('Foss.predeliver.editDeliverbillNew.BatchPreprocessForm',{
				        	selectRecords : records
				        })]
				    }).show();
				    
				}
			},{
			text: '运单退回',
			 plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
	             seconds: 3
	         }),
			handler : function (btn) {
				var grid = btn.up('grid');
				var rowSelectionModel = grid.getSelectionModel();
				if (rowSelectionModel.hasSelection()) { 
					var records = rowSelectionModel.getSelection();
					var waybillNos = [];
				    Ext.Array.each(records, function(record, index, countriesItSelf) {
				    	waybillNos.push(record.get('waybillNo'));
				     });
				    var win = Ext.create('Foss.predeliver.editDeliverbillNew.billReturnWin');
				    win.down('hiddenfield[name=returnBills]').setValue(waybillNos.toString());
				    win.show();
				} else {
					Ext.ux.Toast.msg('提示信息','至少选中一行数据进行退回操作', 'success', 3000);/*"至少选中一行数据进行删除"*/
			        return ; 
				}
			}
	}],
	store: null,
	width : (document.body.scrollWidth)/4*2,
	height : 670,
	margin : '10 -2 5 0',
	autoScroll : true,
	//columnLines : true,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	//selModel : Ext.create('Ext.selection.CheckboxModel',{
    //checkOnly: false //限制只有点击checkBox后才能选中行
	//}),
	//selType: 'checkboxmodel',
	selModel : Ext.create('Ext.selection.CellModel',{
			mode: 'SINGLE'
		}),
	getCellEditor : function() {
		if (this.cellEditor == null) {
			// 对单元格级别进行编辑
			this.cellEditor = Ext.create(
					'Ext.grid.plugin.CellEditing', {
						// 设置鼠标点击多少次，触发编辑
						clicksToEdit : 1
					});
		}
		return this.cellEditor;
	},
	dockedItems : [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout:'column',
		defaults:{
			margin:'0 0 0 0',
			allowBlank:true
		},		
		items: [{
			xtype:'displayfield',
			allowBlank:true,
			labelWidth : 65,
			id:'predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsQty',
			name:'totalGoodsQty',
			columnWidth:.25,
			fieldLabel: '总票数'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype:'displayfield',
			allowBlank:true,
			name:'totalGoodsWeight',
			labelWidth : 85,
			id:'predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsWeight',
			columnWidth:.25,
			fieldLabel: '总重量(KG)'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype:'displayfield',
			allowBlank:true,
			name:'totalGoodsVolume',
			columnWidth:.25,
			id:'predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails_totalGoodsVolume',
			labelWidth : 75,
			fieldLabel: '总体积(方)'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype:'displayfield',
			allowBlank:true,
			labelWidth : 75,
			name:'totalQty',
			columnWidth:.25,
			value : 0,
			fieldLabel: '已选票数'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype:'displayfield',
			allowBlank:true,
			name:'totalWeight',
			labelWidth : 95,
			columnWidth:.25,
			value : 0,
			fieldLabel: '已选重量(KG)'
		},{
			xtype: 'container',
			border : false,
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype:'displayfield',
			allowBlank:true,
			name:'totalVolume',
			columnWidth:.25,
			labelWidth : 95,
			value : 0,
			fieldLabel: '已选体积(方)'
		}]
	}],
	addWaybillToArrange : function() {
		var deliverbillBasicInfo = predeliver.ButtonPanelRole_ShippingDetails.getValues();
		if(Ext.isEmpty(deliverbillBasicInfo.delivebillNo)){
			Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), "派送单号为空!不能排单",
					'error', 4000);
			return;
		}
		Ext.Ajax.request({
			url : predeliver.realPath('waybillDetailAddWaybillToArrange.action'),
			timeout: 600000,
			jsonData : {
				'vo' : {
					'waybillToArrangeDtoList' : predeliver.editDeliverbillNew.waybillsDetailToArrange.items,
					'deliverbill' : {
						'id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
						'deliverbillNo' : deliverbillBasicInfo.delivebillNo,
						'vehicleNo' : deliverbillBasicInfo.vehicleNo,
						'delStatus' :"",
						'driverCode' : deliverbillBasicInfo.driverCode,
						'deliverType' : deliverbillBasicInfo.deliverType,
						'driverName' : predeliver.ButtonPanelRole_ShippingDetails.getForm().findField("driverName").rawValue,
						'carTaskinfo': predeliver.ButtonPanelRole_ShippingDetails.down('combobox[name=carTaskinfo]').getValue(),
						'frequencyno': predeliver.ButtonPanelRole_ShippingDetails.down('combobox[name=frequencyNo]').getValue(),
						'preCartaskTime': predeliver.ButtonPanelRole_ShippingDetails.down('timefield[name=preCartaskTime]').getRawValue(),
						'takeGoodsDeptcode': predeliver.ButtonPanelRole_ShippingDetails.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
						'takeGoodsDeptname': predeliver.ButtonPanelRole_ShippingDetails.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
						'expectedbringvolume': predeliver.ButtonPanelRole_ShippingDetails.down('textfield[name=expectedBringVolume]').getValue(),
						'transferDeptcode': predeliver.ButtonPanelRole_ShippingDetails.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
						'transferDeptname': predeliver.ButtonPanelRole_ShippingDetails.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
					}
				}
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var alertMsg = "";
				var failedList = result.vo.waybillToArrangeDtoList;
				for ( var i = 0; i < failedList.length; i++) {
					alertMsg = alertMsg
							+ predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.waybillbr')
							+ "</br>" 
							+ failedList[i].waybillNo
							+ " "
							+ failedList[i].failedToArrangeReason;
				}
				predeliver.editDeliverbillNew.waybillDetail_deliverbillId = result.vo.deliverbill.id;
				predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
				predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
				if (alertMsg == "") {
					Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
							result.message);
				} else {
					Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), alertMsg,
							'error', 4000);
				}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		});
		
	},
	deleteDeliverbillDetails : function(deliverbillDetailIds) {
		Ext.Ajax.request({
			url : predeliver
					.realPath('deleteDeliverbillDetails.action'),
			params : {
				'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
				'deliverbillVo.deliverbillDetailIds' : deliverbillDetailIds
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
				predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
			},
			exception: function(response){
				var json = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
			}
		});
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				displayInfo: true,
				totalCountFlag:true,
				plugins: {
					ptype: 'pagesizeplugin',
					//超出输入最大限制是否提示，true则提示，默认不提示
					alertOperation: true,
					//自定义分页comobo数据
					sizeList: [['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500]],
					//入最大限制，默认为200
					maximumSize: 300
				}
			});
		}
		return this.pagingToolbar;
	},
	viewConfig: {
        stripeRows : false,
        enableTextSelection : true,
        getRowClass : function(record, rowIndex, rp, ds) {
            if ((!Ext.isEmpty(record.data.ertuenReason)) && record.data.ertuenReason!=null) {
                return 'predeliver-editDeliverbillNew-row-blue';
            }
        }

    },
	columns : [
	 { hidden : true,dataIndex: 'id'},
	 { hidden : true,dataIndex: 'arrangeGoodsQty'},
	 { hidden : true,dataIndex: 'actualVehicleNo'},
	 { hidden : true,dataIndex: 'actualSmallzoneCode'},
	 { hidden : true,dataIndex: 'actualSmallzoneName'},
	 { hidden : true,dataIndex: 'receiveBigCustomer'},
	 { hidden : true,dataIndex: 'goodsVolumeTotal'},
	 { hidden : true,dataIndex: 'weight'},
	 { hidden : true,dataIndex: 'arrangableGoodsQty'},
	 { hidden : true,dataIndex: 'latitude'},
	 { hidden : true,dataIndex: 'longitude'},
	{
		dataIndex : 'consigneeAddress',
		align : 'left',
		xtype : 'linebreakcolumn',
		width : 218,
		sortable : true,
		header : '送货地址'
	},
	{
		dataIndex : 'arrangedVolume',
		align : 'center',
		width : 58,	
		xtype: 'ellipsiscolumn',
		header : '排单体积'
	},
	{
		dataIndex : 'arrangedWeight',
		align : 'center',
		width : 58,
		header : '排单重量'
	},
	{
		dataIndex : 'suggestionTreatment',
		align : 'center',
		width : 110,
		header : '预处理建议',
		//windowClassName : 'Foss.predeliver.editDeliverbillNew.PreprocessWindow',
		//xtype : 'openwindowcolumn',
		renderer : function(value, meta, record) {
			var content = '';
			if(!Ext.isEmpty(record.get('actualSmallzoneName'))){
				content += record.get('actualSmallzoneName');
			}
            if((!Ext.isEmpty(record.get('actualVehicleNo')))&&(Ext.isEmpty(record.get('isVehicleScheduling'))||record.get('isVehicleScheduling')!='Y')){
                if(content.length>0){
                    content += '<br/><font color="red">';
                }
                content += record.get('actualVehicleNo');
                content += '</font>';
            }else if(!Ext.isEmpty(record.get('actualVehicleNo'))){
            	if(content.length>0){
                    content += '<br/>';
                }
                content += record.get('actualVehicleNo');
            }
			return Ext.String.format('<a href="javascript:void(0)">{0}</a>',content);
		}
	},
	{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype: 'ellipsiscolumn',//悬浮提示
		header : '单号'
	},
	{
		dataIndex : 'cashTime',
		align : 'center',
		width : 80,
		xtype: 'ellipsiscolumn',//悬浮提示
		header : '规定兑现时间'
	},
	{
		dataIndex : 'deliverRequire',
		align : 'center',
		width : 80,
		xtype: 'ellipsiscolumn',
		header : '送货要求'
	},
	{
		dataIndex : 'dimension',
		align : 'center',
		width : 65,
		xtype: 'ellipsiscolumn',//悬浮提示
		header : '尺寸'
	},
	{
		dataIndex : 'preArrangeGoodsQty',
		align : 'center',
		width : 58,
		header : '排单件数',
		editor : {
			// 定义编辑框的类型，编辑框可以是表单中的所有类型
			xtype : 'numberfield',
			allowBlank : false,
			allowDecimals : false,
			listeners : {
				'afterrender' : function(field, event,
						eOpts) {
					var waybillToArrangeGrid = field.up('gridpanel');
					var editor = waybillToArrangeGrid.getCellEditor().activeEditor;
					var selectRecord = null;
					editor.on('startedit',
							function() {
								var selectRow = waybillToArrangeGrid
										.getSelectionModel()
										.getSelection();
								if (selectRow == null
										|| selectRow.length <= 0)
									return;
								selectRecord = selectRow[0];
							}, this);

					editor.on('beforecomplete',
					function() {
						if (selectRecord == null) {
							return;
						}
						if (field.lastValue > selectRecord
								.get('arrangableGoodsQty')) {
							flag = false;
							Ext.ux.Toast.msg('提示','排单件数不能大于当前交单件数','error',4000);
							editor.cancelEdit();
							return;
						} else {
							flag = true;
						}
						if (field.lastValue <= 0) {
							flag = false;
							Ext.ux.Toast.msg('提示','排单件数必须大于0','error',4000);
							editor.cancelEdit();
							return;
						} else {
							flag = true;
						}
						var grid = Ext.getCmp('predeliver_editDeliverbillNew_WaybillToListQueryResultsGrid_ShippingDetails');
			
						var weight =selectRecord.data.weight* field.lastValue
												/ selectRecord.data.goodsQtyTotal;
						var volumne=selectRecord.data.goodsVolumeTotal* field.lastValue
												/ selectRecord.data.goodsQtyTotal;
					
						grid.down('displayfield[name=totalWeight]').setValue(weight.toFixed(1));;
						grid.down('displayfield[name=totalVolume]').setValue(volumne.toFixed(2));
						selectRecord.set('preArrangeGoodsQty',field.lastValue);
						selectRecord.set('arrangeGoodsQty',field.lastValue);
						
						selectRecord.set('arrangedVolume',volumne.toFixed(2));
						selectRecord.set('arrangedWeight',weight.toFixed(1));
						selectRecord.commit();
					}, this);
				}
			}
		}
	},
	{
		dataIndex : 'deliveryTimeInterval',
		align : 'center',
		width : 90,
		header : '送货时间段'
	},
	{
		dataIndex : 'deliverTimeStartAndOver',
		align : 'center',
		width : 90,
		header : '送货时间点'
	},
	{
		dataIndex : 'goodsPackage',
		align : 'center',
		width : 75,
		xtype: 'ellipsiscolumn',
		header : '包装'
	},
	{
		dataIndex : 'billQty',
		align : 'center',
		width : 80,
		header : '交单件数'
	},
	{
		dataIndex : 'prouctCode',
		align : 'center',
		width : 80,
		header : '运输性质',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},
	{
		dataIndex : 'goodsType',
		align : 'center',
		xtype: 'ellipsiscolumn',
		width : 120,
		header : '特殊货物类型'
	},
	{
		dataIndex : 'addressType',
		align : 'center',
		width : 120,
		header : '特殊地址类型'
	},
	{
		dataIndex : 'stockQty',
		align : 'center',
		width : 80,
		xtype: 'ellipsiscolumn',
		header : '库存件数'
	},
	{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 80,
		header : '开单件数'
	},
	{
		dataIndex : 'goodsStatus',
		align : 'center',
		width : 80,
		header : '货物状态'
	},
	{
		dataIndex : 'goodsName',
		align : 'center',
		width : 58,	
		header : '货物名称'
	},
	{
		dataIndex : 'payAmount',
		align : 'center',
		width : 80,
		header : '到付金额'
	},
	{
		dataIndex : 'receiveMethod',
		align : 'center',
		width : 80,
		header : '提货方式',
		renderer : function(value) {
			var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
			if(Ext.isEmpty(v) || value == v){
				v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
			}
			return v;
		}
	},
	{
		dataIndex : 'ertuenReason',
		align : 'center',
		width : 120,
		header : '退回原因'
	},
	{
		dataIndex : 'returnNumber',
		align : 'center',
		width : 130,
		header : '理货员退单次数'
	},
	{
		dataIndex : 'arriveTime',
		align : 'center',
		width : 130,
		header : '到达时间'
	},
	{
		dataIndex : 'inStockTime',
		align : 'center',
		width : 130,
		header : '入库时间'
	},
	{
		dataIndex : 'deliverDate',
		align : 'center',
		width : 120,
		xtype: 'ellipsiscolumn',
		header : '预计送货日期'
	},
	{
		dataIndex : 'togetherSendCode',
		align : 'center',
		width : 80,
		header : '合送编码'
	}],
	listeners : {
		afterrender : function (grid) {
			var view  = grid.view;
			view.on('expandBody', function (rowNode, record, expandRow, eOpts) {  
				
				if (Ext.getCmp('dpap.User.GridDetail_Id')) {
					Ext.ComponentManager.unregister(Ext.getCmp('dpap.User.GridDetail_Id'));
					var parent = document.getElementById(record.get('waybillNo'));  
					var child = parent.firstChild;  
					while (child) {  
					   child.parentNode.removeChild(child);  
					   child = child.nextSibling;  
					}  
				}
				
				var gridDetail = Ext.create('dpap.User.GridDetail', {
					renderTo : record.get('waybillNo')
				});
				
				if (record.get('waybillNo')) {
					// 获得当前对应的收货联系人
			  		Ext.Ajax.request({
			  			url : predeliver.realPath('queryReceiveCustomerContact.action'),
			  			params :{'waybillNo':record.get('waybillNo')
			  					 //'modifyTime':record.get('modifyTime')
			  					},
			  			success : function(response) {
			  				var result = Ext.decode(response.responseText);
			  				
			  				if (result.vo.receiveCustomerContact) {
			  					gridDetail.loadRecord(record);
			  					gridDetail.down('textfield[name=receiveCustomerContact]').setValue(result.vo.receiveCustomerContact);
			  				} else {
			  					gridDetail.loadRecord(record);
			  					gridDetail.down('textfield[name=receiveCustomerContact]').setValue(result.vo.receiveCustomerContact);
			  				}
			  			},
			  			exception : function(response) {
			  				var result = Ext.decode(response.responseText);
			  				Ext.ux.Toast.msg('提示', result.message, 'error', 2500);
			  			}
			  		});
			  		
				}
			 })
		},
		cellclick:  function(table, td, cellIndex, record, tr, rowIndex, e, eOpts){
			var columns=predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.columns;
				if(columns[cellIndex].dataIndex == 'suggestionTreatment'){
					predeliver.editDeliverbillNew.PreprocessWindow();
					var preprocessForm=Ext.getCmp('Foss_predeliver_editDeliverbillNew_PreprocessForm_id');
	                 preprocessForm.getForm().loadRecord(record);
					preprocessForm.query('combobox[name="actualSmallzoneName"]')[0].setCombValue(record.data.actualSmallzoneName,record.data.actualSmallzoneCode);
					preprocessForm.getForm().findField('actualVehicleNo').setValue(record.data.actualVehicleNo);
				}
			}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.editDeliverbillNew.StandardList');
		me.selModel = Ext.create('Foss.predeliver.editDeliverbillNew.CheckboxModelArrangeWaybillDetail');
		me.bbar = me.getPagingToolbar();
		me.plugins = [
						me.getCellEditor(),
						{
							header : true,
							// 定义行可展开的插件ID
							pluginId : 'rowexpander_plugin_id',
							// 定义插件的类型
							ptype : 'rowexpander',
							// 定义行展开模式（单行与多行），默认是多行展开(值true)
							rowsExpander : false,
							rowBodyTpl: [  
							 		    '<div id="{waybillNo}">',  
							 		     '</div>'  
							 		] 
						}];
		me.callParent([cfg]);
	}
});

//待选列表中，向下展开详细列表
  Ext.define('dpap.User.GridDetail',{
	  extend: 'Ext.form.Panel',
      id: 'dpap.User.GridDetail_Id',
      frame: true,//是否有边框
      hidden : false,//是否隐藏
      defaultType : 'textfield',
      layout:'column',//布局
      align: 'stretch',
      height:'100',
  	  cls : 'autoHeight',
      defaults: {
          anchor: '100%',
          labelWidth: 100
      },
      bindData : function(record){
          this.loadRecord(record);
      },
      initComponent: function(config){

          var me = this,
              cfg = Ext.apply({}, config);
          me.items = [{
              name: 'receiveCustomerContact',
              fieldLabel: '收货联系人',
              readOnly : true,
			  tipConfig: {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.editDeliverbillNewIndex.tipPanel'
				},
              columnWidth : 0.08
          },{
              name: 'returnNumber',
              fieldLabel: '退单次数',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'goodsQtyTotal',
              fieldLabel: '开单件数',
              readOnly : true,
              columnWidth : 0.84
          },{
              name: 'goodsName',
              fieldLabel: '货物名称',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'ertuenReason',
              fieldLabel: '退回原因',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'billQty',
              fieldLabel: '交单件数',
              readOnly : true,
              columnWidth : 0.84
          },{
    	      xtype:'combo',
              displayField: 'name',
              valueField : 'code',
              readOnly : true,
              name: 'prouctCode',
              store: Ext.create('Foss.pkp.ProductStore'),
              columnWidth : 0.08,
              fieldLabel: '运输性质'
          },{
              name: 'deliveryTimeInterval',
              fieldLabel: '送货时间段',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'stockQty',
              fieldLabel: '库存件数',
              readOnly : true,
              columnWidth : 0.84
          },{
              name: 'receiveMethod',
              fieldLabel: '提货方式',
              readOnly : true,
              valueField : 'valueCode',
              displayField: 'valueName',
              xtype:'combo',
              store: FossDataDictionary.getDataDictionaryStore("PICKUPGOODSHIGHWAYS"),
              columnWidth : 0.08
          },{
              name: 'deliverTimeStartAndOver',
              fieldLabel: '送货时间点',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'goodsStatus',
              fieldLabel: '货物状态',
              readOnly : true,
              columnWidth : 0.84
          },{
              name: 'goodsPackage',
              fieldLabel: '包装',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'goodsType',
              fieldLabel: '特殊货物类型',
              readOnly : true,
              columnWidth : 0.08
          },{
              name: 'arriveTime',
              fieldLabel: '到达时间',
              readOnly : true,
              columnWidth : 0.84
          },{
              name: 'dimension',
              fieldLabel: '尺寸',
              readOnly : true,
              tipConfig: {
					cls: 'autoHeight',
					bodyCls: 'autoHeight',
					width: 150,
					//是否随鼠标滑动
					trackMouse: true,
					//普通Form上必须配置tipType(区分普通Form和行展开的Form)
					tipType: 'normal',
					//当值为空的时候，不显示tip
					isShowByData: true,
					tipBodyElement: 'predeliver.editDeliverbillNewIndex.tipPanel'
				},
              columnWidth : 0.08
          },{
              name: 'addressType',
              fieldLabel: '特殊地址类型',
              readOnly : true,
              columnWidth : 0.08
          },{
        	  xtype:'displayfield',
        	  allowBlank:true,
        	  valueField : 'valueCode',
              displayField: 'valueName',
        	  labelWidth : 100,
        	  columnWidth : 0.84,
        	  name : 'payAmount',
        	  fieldLabel: '到付金额'
          }];
          me.callParent([cfg]);

      }
  }); 
  Ext.define('predeliver.editDeliverbillNewIndex.tipPanel', {
		extend: 'Ext.panel.Panel',
		cls: 'autoHeight',
		bodyCls: 'autoHeight',
		//回调方法
		bindData : function(value){
			this.update(value);
		},
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
	});
  
 //运单明细保存、提交按钮
Ext.define('FOSS.predeliver.editDeliverbillNew.ShippingDetails_saveSubmitFrom',{
	 extend: 'Ext.form.Panel',
	 //title : '运单明细',
	// border : false,
	 frame : false,
     layout : 'column',
     defaults : {
        margin : '5 0 20 0'
     },
    items : [{
                border : false,
                columnWidth : .82,
                html : '&nbsp;'
            },{

                xtype : 'button',
                columnWidth : .08,
                cls : 'yellow_button',
                text : '保存',//暂时写死，留待i18n处理
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                handler : function(button, e) {
                    button.up('form').saveOrSubmit('SAVED', button);
                }

            },{
                border : false,
                columnWidth : .02,
                html : '&nbsp;'
            },{
                columnWidth : .08,
                xtype : 'button',
                cls : 'yellow_button',
                text : '提交',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                disabled : true,
                id:'Foss_predeliver_editDeliverbillNew_deliverbill_submit',
                handler : function(button, e) {
                    button.up('form').saveOrSubmit('SUBMITED', button);
                }
        }],
	  // 保存/提交时验证
	  validateOnSaveOrSubmit : function() {
		  // 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
		  if (!predeliver.ButtonPanelRole_ShippingDetails
			  .getForm().isValid()) {
			  Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), '有必填项（页面标识红叉项）未填写，请填写完整！');
			  return false;
		  }

		  // 11b 12b
		  // 排单员点击“保存”或“提交”时，如果未有运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
		  if (predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.store.data
			  .getCount() == 0) {
			  Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.choseTheGoods'));
			  return false;
		  }

		  return true;
	  },
	  // 保存/提交派送单
	  saveOrSubmit : function(deliverbillStatus, button) {
		  //需要验证车牌号/司机是否有效
		  if (!predeliver.editDeliverbillNew.ShippingDetails_saveSubmitFrom.validateOnSaveOrSubmit()) {
			  return;
		  }
      
      //拖动排序保存,取当前的集合数据
      var sortGridList = new Array();
      predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().each(function(item, index, length) {
        var obj = new Object();
        obj.id = item.data.id;
        obj.waybillNo = item.data.waybillNo;
        sortGridList.push(obj);
      });

		  var deliverbillBasicInfo = predeliver.ButtonPanelRole_ShippingDetails;
		  var driverName=predeliver.ButtonPanelRole_ShippingDetails.getForm().findField("driverName");
		  // 更新派送单
		  Ext.Ajax.request({
			  url : predeliver.realPath('waybillDetailSaveDeliverbill.action'),
			  jsonData : {
				  'vo' : {
					  'deliverbill' : {
						  'id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId,
						  'deliverbillNo' : deliverbillBasicInfo.getValues().delivebillNo,
						  'vehicleNo' : deliverbillBasicInfo.getValues().vehicleNo,
						  'delStatus' :predeliver.editDeliverbillNew.status,
						  'driverCode' : deliverbillBasicInfo.getValues().driverCode,
						  'deliverType' : deliverbillBasicInfo.getValues().deliverType,
						  'driverName' : driverName.rawValue,
						  'status' : deliverbillStatus,
						  'carTaskinfo': deliverbillBasicInfo.down('combobox[name=carTaskinfo]').getValue(),
						  'frequencyno': deliverbillBasicInfo.down('combobox[name=frequencyNo]').getValue(),
						  'preCartaskTime': deliverbillBasicInfo.down('timefield[name=preCartaskTime]').getRawValue(),
						  'takeGoodsDeptcode': deliverbillBasicInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').lastValue,
						  'takeGoodsDeptname': deliverbillBasicInfo.down('commonsaledepartmentselector[name=takeGoodsDeptcode]').rawValue,
						  'expectedbringvolume': deliverbillBasicInfo.down('textfield[name=expectedBringVolume]').getValue(),
						  'transferDeptcode': deliverbillBasicInfo.down('commonsaledepartmentselector[name=transferDeptcode]').lastValue,
						  'transferDeptname': deliverbillBasicInfo.down('commonsaledepartmentselector[name=transferDeptcode]').rawValue
					  },
					  'dragStr': sortGridList.length < 1 ? null : Ext.encode(sortGridList)
				  }
			  },
			  success : function(response) {

				  var confirmTitle = deliverbillStatus == 'SAVED' ? predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.saveDeliverSuccess')
					  : predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.submitDeliverSuccess');

				  var confirmMsg = confirmTitle
					  + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.theNumberIs')
					  + deliverbillBasicInfo.getValues().delivebillNo
					  //+ predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.weightTotalbr')
					 // + visibleTotalWeight
					 // + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotalbr')
					 // + visibleTotalVolumn
					 // + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotalbr')
					 // + visibleTotalTicket
					 // + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.piecesTotalbr')
					 // + visibleTotalCount
					 // + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.payAmountTotalbr')
					 // + visibleTotalPayAmount
					 // + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.loadRatebr')
					 // + visibleLoadRate
					  + predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.surePrintDeliverBill')

				  Ext.Msg.confirm(confirmTitle, confirmMsg, function(btn, text) {
					  if (btn == "yes") {
						  // 打印预派送单
						  var printWin = Ext.create('Foss.predeliver.deliverbill.PrintDeliverbillWindow', {
							  'deliverbillId' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId
						  });

						  //获取打印的预排送单基本信息
						  Ext.Ajax.request({
							  url : predeliver.realPath('queryDeliverbill.action'),
							  params : {
								  'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId
							  },
							  success : function(response) {
								  var result = Ext.decode(response.responseText), model = Ext.ModelManager
									  .create(result.deliverbillVo.deliverbill, 'Foss.predeliver.deliverbill.PrintModel');
								  printWin.getDeliverPrintHeadInfo().loadRecord(model);
								  printWin.getDeliverPrintBottomInfo().loadRecord(model);
								  if(!Ext.isEmpty(deliverbillBasicInfo.getValues().driverCode)){
									  printWin.query('checkbox[name="isNoticeDriver"]')[0].setValue('Y');
									  printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
								  }
								  else{//如果司机姓名为空，通知司机按钮显示、不可操作
									  printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
									  printWin.query('checkbox[name="isNoticeDriver"]')[0].setDisabled (true); 
								  }
								  
								  printWin.getDeliverPrintGrid().store.setDeliverbillId(predeliver.editDeliverbillNew.waybillDetail_deliverbillId);
								  printWin.getDeliverPrintGrid().getPagingToolbar().moveFirst();
								  printWin.show();
								  if (deliverbillStatus == 'SUBMITED') {
										 predeliver.ButtonPanelRole_ShippingDetails.getForm().reset();
										 predeliver.editDeliverbillNew.vehicleNoTruckType=null;
										 //刷新派送单
										Ext.getCmp('Foss_predeliver_ediDeliverbillNew_WaybillDetailDeliverNo').setValue(querySequence());
										predeliver.editDeliverbillNew.waybillDetail_deliverbillId="";
										predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
										predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
										 
								}
							  }
						  });
					  }else{
						  if (deliverbillStatus == 'SUBMITED') {
								 predeliver.ButtonPanelRole_ShippingDetails.getForm().reset();
								 predeliver.editDeliverbillNew.vehicleNoTruckType=null;
								 //刷新派送单
								Ext.getCmp('Foss_predeliver_ediDeliverbillNew_WaybillDetailDeliverNo').setValue(querySequence());
								predeliver.editDeliverbillNew.waybillDetail_deliverbillId="";
								predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails.getPagingToolbar().moveFirst();
								predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
								 
							  }
					  }
				  });
			  },
			  exception : function(response) {
				  var result = Ext.decode(response.responseText);
				  Ext.ux.Toast.msg(predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			  }
		  });
        }
	});
/*-----------------------------------运单明细结束-------------------------------------------*/
Ext.onReady(function() {	
	Ext.QuickTips.init();

		  
		  
	predeliver.editDeliverbillNew.waybillsToArrange_BigZone = new Ext.util.MixedCollection(); // 选中的大区
	predeliver.editDeliverbillNew.waybillsToArrange_SmallZone = new Ext.util.MixedCollection(); // 选中的小区
	predeliver.editDeliverbillNew.waybillsToAlready = new Ext.util.MixedCollection(); // 选中的已排运单集合
	
	predeliver.editDeliverbillNew.waybillsDetailToArrange = new Ext.util.MixedCollection(); // 选中的待排运单集合/运单明细
	predeliver.editDeliverbillNew.waybillsDetailToAlready = new Ext.util.MixedCollection(); // 已排列表运单集合/运单明细
	
	/**
		分区查看
	**/
	predeliver.editDeliverbillNew.QueryWaybillForm_PartitionedView = Ext.create('Foss.predeliver.editDeliverbillNew.QueryWaybillForm_PartitionedView');//查询排单运单_分区查看
	predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView = Ext.create('Foss.predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView');//已分区查询结果——分区查看
	predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView = Ext.create('Foss.predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView');//已排单运单——分区查看
	predeliver.ButtonPanelRole_PartitionedView = Ext.create('Foss.predeliver.ButtonPanelRole_PartitionedView');//中间操作——分区查看
	predeliver.editDeliverbillNew.SubmitForm_PartitionedView=Ext.create('Foss.predeliver.editDeliverbillNew.SubmitForm_PartitionedView');//保存和提交

	/**
		运单明细
	**/
	predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails = Ext.create('Foss.predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails');//查询排单运单_运单明细
	predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails = Ext.create('Foss.predeliver.editDeliverbillNew.WaybillToListQueryResultsGrid_ShippingDetails');//待选列表——运单明细
	predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails = Ext.create('Foss.predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails');//已排单运单——运单明细
	predeliver.ButtonPanelRole_ShippingDetails = Ext.create('Foss.predeliver.ButtonPanelRole_ShippingDetails');//中间操作——运单明细
	
	
	
	  
	  
	//分区查看——主界面
	Ext.define('FOSS.predeliver.editDeliverbillNew.ContainerPanel_PartitionedView',{
				  extend: 'Ext.panel.Panel',
				  bodyCls : 'autoHeight',
				  cls : 'autoHeight',
				  anchor : '100%',
				  layout: {
					type: 'hbox',
					align: 'stretch'
				  },
				  //刷新中间panel的车辆相关信息
				  refreshMiddlePanelInfo: function(vehicleNo, driverName, driverCode, deliverType) {
					var midPanel = predeliver.ButtonPanelRole_PartitionedView;
					if (vehicleNo) {
						midPanel.getForm().findField('vehicleNo').setValue(vehicleNo);;
					}
					if (driverName) {
						midPanel.getForm().findField('driverName').setValue(driverName);;
					}
					if (driverCode) {
						midPanel.getForm().findField('driverCode').setValue(driverCode);;
					}
					if (deliverType) {
						midPanel.getForm().findField('deliverType').setValue(deliverType);;
					}
					
					
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
									var driverNameField =  predeliver.ButtonPanelRole_PartitionedView.getForm().findField('driverName');
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
											driverNameField.setRawValue(driver.empName);

											 predeliver.ButtonPanelRole_PartitionedView.getForm()
													.findField('driverCode').setValue(driver.empCode);

											if (driver.pdaSigned == 'Y') {
												// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
												driverNameField.setReadOnly(true);
											} else {
												// 司机设置为可选择
												driverNameField.setReadOnly(false);
											}
										} else {
											// 司机设置为可选择
											driverNameField.setReadOnly(false);
										}

									} else {
										Ext.ux.Toast.msg(
												'提示',
												'出现未知错误',
												'error',
												2500);
										if (typeof (vehicleNoField) != "undefined"
												&& vehicleNoField != null) {
											vehicleNoField
													.setValue(oldVehicleNo);
										}
										driverNameField.setReadOnly(false);
									}
								}
							});
						}						
					},
				  items:[predeliver.editDeliverbillNew.QueryResultsGrid_PartitionedView,
						 predeliver.ButtonPanelRole_PartitionedView,
						 predeliver.editDeliverbillNew.SingleRowWaybillGrid_PartitionedView]
	}); 
	
//运单明细——主界面
Ext.define('FOSS.predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails',{
	 extend: 'Ext.panel.Panel',
	 bodyCls : 'autoHeight',
	 cls : 'autoHeight',
	 anchor : '100%',
	 layout: {
		type: 'hbox',
		align: 'stretch'
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
					var driverNameField = predeliver.ButtonPanelRole_ShippingDetails.getForm().findField('driverName');
					var driverCode = predeliver.ButtonPanelRole_ShippingDetails.getForm().findField('driverCode');
					var vehicle = result.deliverbillVo.vehicleAssociationDto;
					if (vehicle != null) {
						var driver = result.deliverbillVo.driverDto;
						if (driver != null) {
							// 更新driverName的value和rawValue
							driverNameField.setValue(driver.empCode);
							driverNameField.setRawValue(driver.empName);
							driverCode.setValue(driver.empCode);
						} 
					} else {
						Ext.ux.Toast.msg(
								'提示',
								predeliver.editDeliverbillNew.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
								'error',
								4000);
						}
				}
			});
		 }
	},
  items:[predeliver.editDeliverbillNew.QueryResultsGrid_ShippingDetails,
		 predeliver.ButtonPanelRole_ShippingDetails,
		 predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails]
}); 
	
	predeliver.editDeliverbillNew.ContainerPanel_PartitionedView = Ext.create('FOSS.predeliver.editDeliverbillNew.ContainerPanel_PartitionedView');
	predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails = Ext.create('FOSS.predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails');
    predeliver.editDeliverbillNew.ShippingDetails_saveSubmitFrom = Ext.create('FOSS.predeliver.editDeliverbillNew.ShippingDetails_saveSubmitFrom');
	
 //运单明细选项卡
    Ext.define('FOSS.predeliver.editDeliverbillNew.ShippingDetails',{
        extend: 'Ext.panel.Panel',
        title : '运单明细',
        items : [predeliver.editDeliverbillNew.QueryWaybillForm_ShippingDetails,predeliver.editDeliverbillNew.ContainerPanel_ShippingDetails, predeliver.editDeliverbillNew.ShippingDetails_saveSubmitFrom
           ]
    });

	
	//分区查看选项卡
	Ext.define('FOSS.predeliver.editDeliverbillNew.PartitionedView',{
				  extend: 'Ext.panel.Panel',
				  title : '分区查看',
	items : [predeliver.editDeliverbillNew.QueryWaybillForm_PartitionedView,predeliver.editDeliverbillNew.ContainerPanel_PartitionedView,
	predeliver.editDeliverbillNew.SubmitForm_PartitionedView]
	}); 
	
	predeliver.editDeliverbillNew.ShippingDetails=Ext.create('FOSS.predeliver.editDeliverbillNew.ShippingDetails',{
		tabConfig:{
					  width:120
					  }
	});
	predeliver.editDeliverbillNew.PartitionedView=Ext.create('FOSS.predeliver.editDeliverbillNew.PartitionedView',{
		tabConfig:{
					  width:120
					  }
	});
	if(predeliver.editDeliverbillNew.status == 'SAVED' || predeliver.editDeliverbillNew.waybillDetail_deliverbillId == '')
	{
		Ext.getCmp('Foss_predeliver_editDeliverbillNew_deliverbill_submit').setDisabled(false);
	}
	if(predeliver.editDeliverbillNew.status == 'LOADED' || predeliver.editDeliverbillNew.status == 'CONFIRMED')
	{
		predeliver.editDeliverbillNew.waybillDetailVehicleReset(1);//设置车辆组别跟送货车辆是否只读
	}
	Ext.define('FOSS.predeliver.editDeliverbillNew.ContainerPanel',{
		extend: 'Ext.tab.Panel',
		cls : 'innerTabPanel',
		activeTab : 0,
		columnWidth : 1,
		items:[predeliver.editDeliverbillNew.ShippingDetails,predeliver.editDeliverbillNew.PartitionedView]
	}); 
	
	predeliver.editDeliverbillNew.ContainerPanel=Ext.create('FOSS.predeliver.editDeliverbillNew.ContainerPanel');
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-editDeliverbillNewIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		margin : '2 2 2 2',
		items : [predeliver.editDeliverbillNew.ContainerPanel],
		refreshDeliverbillInfoForm : function() {
			if (predeliver.editDeliverbillNew.waybillDetail_deliverbillId != '') {
				// 若派送单已保存，则刷新统计信息和车载信息
				Ext.Ajax.request({
							url : predeliver
									.realPath('queryDeliverbill.action'),
							params : {
								'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbillNew.waybillDetail_deliverbillId
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var deliverbill = Ext.ModelManager.create(result.deliverbillVo.deliverbill,
												'Foss.predeliver.editDeliverbillNew.ShippingDetailsModel');
								Ext.getCmp('Foss_predeliver_ediDeliverbillNew_WaybillDetailDeliverNo').setValue(result.deliverbillVo.deliverbill.deliverbillNo);
								predeliver.ButtonPanelRole_ShippingDetails.loadRecord(deliverbill);
								predeliver.editDeliverbillNew.SingleRowWaybillGrid_ShippingDetails.getStore().load();
								
							}
						});
			}
		},
		renderTo : 'T_predeliver-editDeliverbillNewIndex-body'
	});
	Ext.getCmp('T_predeliver-editDeliverbillNewIndex_content').refreshDeliverbillInfoForm();
});