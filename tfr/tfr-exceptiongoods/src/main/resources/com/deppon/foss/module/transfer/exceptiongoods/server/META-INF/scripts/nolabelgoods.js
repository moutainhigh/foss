/**
 * 查询无标签多货界面
 * 		Foss.exceptiongoods.nolabelgoods.QueryForm
 * 		Foss.exceptiongoods.nolabelgoods.NolabelgoodsGrid
 */

//无标签多货查询条件表单
Ext.define('Foss.exceptiongoods.nolabelgoods.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	//title : '查询无标签多货',
	title : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.query.nolabelgoods'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		//fieldLabel: '无标签运单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
		name: 'noLabelBillNo',
		columnWidth:.25
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_DISCOVER_STATE',
		{
			//fieldLabel : '无标签发现类型',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabel.discover.type'),
			name : 'findType',
			value : 'ALL',
			editable : false,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_STOCK_STATE',
		{
			//fieldLabel : '库存状态',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.stock.status'),
			name : 'stockStatus',
			value : 'ALL',
			editable : false,
			hidden: true,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),
	FossDataDictionary.getDataDictionaryCombo('REPORT_OA_STATUS',
		{
			//fieldLabel : 'OA上报状态',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa.status'),
			name : 'isReportOa',
			value : 'ALL',
			editable : false,
			hidden: true,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),{
		xtype: 'textfield',
		//fieldLabel: 'OA差错编号',无标签多货编号
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.oa.error.no'),
		name: 'oaErrorNo',
		columnWidth:.25
	},{
		xtype: 'commongoodsareaselector',
		//fieldLabel: '清仓发现货区',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goodsarea'),
		name: 'goodsAreaCode',
		labelWidth: 100,
		columnWidth:.25
	},{
		xtype: 'commonemployeeselector',
		//fieldLabel: '发现人',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.person'),
		name: 'findUserCode',
		labelWidth: 100,
		columnWidth:.25
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_FIND_STATE',
		{
			//fieldLabel : '找货状态',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goods.status'),
			name : 'findGoodsStatus',
			value : 'ALL',
			editable : false,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),{
		xtype: 'textfield',
		//fieldLabel: '交接单号',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handoverno'),
		name: 'handoverNo',
		columnWidth:.25,
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '车次号',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.vehicleno'),
		name: 'vehicleNo',
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		//fieldLabel: '起止时间',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.begin.end.time'),
		fieldId: 'Foss.exceptiongoods.nolabelgoods_QueryForm_BeginEndTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'beginTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'),		
		toName: 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		dateRange: 30,
		columnWidth: .5
	},{
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '上一环节部门',
		fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.previous.org'),
		name: 'previousOrgCode',
		type : 'ORG',
		transferCenter: 'Y',
		labelWidth: 100,
		columnWidth:.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			//text: '重置',
			text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
			columnWidth:.08,
			handler: function(){
				exceptiongoods.nolabelgoods.queryform.getForm().reset();
				exceptiongoods.nolabelgoods.queryform.getForm().findField('beginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'));
				exceptiongoods.nolabelgoods.queryform.getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			//text: '查询',
			text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.query'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				exceptiongoods.nolabelgoods.pagingBar.moveFirst();
			}
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.exceptiongoods.nolabelgoods.NoLabelGoodsModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id' , type: 'string'},
		{name: 'noLabelBillNo' , type: 'string'},
		{name: 'noLabelSerialNo' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'goodsAreaCode' , type: 'string'},
		{name: 'findUserCode', type: 'string'},
		{name: 'previousOrgCode', type: 'string'},  
		{name: 'stockStatus', type: 'string'},
		{name: 'isPrintNoLabel', type: 'string'},
		{name: 'oaErrorNo', type: 'string'},
		{name: 'originalWaybillNo', type: 'string'},
		{name: 'originalSerialNo', type: 'string'},
		{name: 'isPrintOriginalLabel', type: 'string'},
		{name: 'weight', type: 'string'},
		{name: 'volume', type: 'string'},
		{name: 'volumeLWH', type: 'string'},
		{name: 'goodsProperty', type: 'string'},
		{name: 'goodsType', type: 'string'},
		{name: 'goodsBrand', type: 'string'},
		{name: 'previousOrgName', type: 'string'},
		{name: 'eventProcess', type: 'string'},
		{name: 'goodsAreaName', type: 'string'},
		{name: 'findTime', type: 'date',convert: dateConvert},
		{name: 'goodsName', type: 'string'},
		{name: 'packageType', type: 'string'},
		{name: 'handwritingKeyword', type: 'string'},
		{name: 'frontPhoto', type: 'string'},
		{name: 'entiretyPhoto', type: 'string'},
		{name: 'goodsPhoto', type: 'string'},
		
		{name: 'frontPhotoName', type: 'string'},
		{name: 'entiretyPhotoName', type: 'string'},
		{name: 'goodsPhotoName', type: 'string'},
		{name: 'goodsPhotoAName', type: 'string'},
		{name: 'goodsPhotoBName', type: 'string'},
		
		{name: 'lossGoodsNotes', type: 'string'},
		{name: 'noteNotifyPerson', type: 'string'},
		{name: 'packageKeyword', type: 'string'},
		{name: 'handoverNo', type: 'string'},
		{name: 'userCode', type: 'string'},
		{name: 'findType', type: 'string'},
		{name: 'createUserName', type: 'string'},
		{name: 'findOrgCode', type: 'string'},
		
		{name: 'createDate', type: 'date', convert: dateConvert},
		/*{name: 'createDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}	
		},*/
		
		{name: 'goodsQty', type: 'string'},
		{name: 'reportOAUserName', type: 'string'},
		{name: 'findUserName', type: 'string'},
		{name: 'reportOATime', type: 'date', convert: dateConvert},
		{name: 'isReportOa', type: 'string'},
		{name: 'findGoodsStatus', type: 'string'},
		{name: 'printUserName', type: 'string'},
		{name: 'printTime', type: 'date', convert: dateConvert},
		{name: 'printOriginalLabelUserName', type: 'string'},
		{name: 'printOriginalLabelTime', type: 'date', convert: dateConvert},
		{name: 'inStockTime', type: 'string'},
		{name: 'inStockDeviceType', type: 'string'},
		{name: 'inStockUserName', type: 'string'},
		{name: 'leakGoods',type: 'String'},
		{name: 'expressGoods',type: 'String'},
		{name: 'goodsStatus',type: 'String'}
		
	]
});

//查询无标签多货表格Store
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLabelGoodsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.exceptiongoods.nolabelgoods.NoLabelGoodsModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'post',
        url: exceptiongoods.realPath('queryNoLabelGoods.action'),
		reader : {
			type : 'json',
			root : 'noLabelGoodsVo.noLabelGoodsList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				
				var queryParams = exceptiongoods.nolabelgoods.queryform.getValues();
				
				if(queryParams.findType == 'ALL'){
					queryParams.findType = '';
				}
				if(queryParams.stockStatus == 'ALL'){
					queryParams.stockStatus = '';
				}
				if(queryParams.isReportOa == 'ALL'){
					queryParams.isReportOa = '';
				}
				if(queryParams.findGoodsStatus == 'ALL'){
					queryParams.findGoodsStatus = '';
				}
				
				Ext.apply(operation, {
					params : {
						'noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo' : queryParams.noLabelBillNo,
						'noLabelGoodsVo.noLabelGoodsEntity.findType' : queryParams.findType,
						'noLabelGoodsVo.noLabelGoodsEntity.stockStatus' : queryParams.stockStatus,
						'noLabelGoodsVo.noLabelGoodsEntity.isReportOa' : queryParams.isReportOa,
						'noLabelGoodsVo.noLabelGoodsEntity.oaErrorNo' : queryParams.oaErrorNo,
						'noLabelGoodsVo.noLabelGoodsEntity.goodsAreaCode' : queryParams.goodsAreaCode,
						'noLabelGoodsVo.noLabelGoodsEntity.findUserCode' : queryParams.findUserCode,
						'noLabelGoodsVo.noLabelGoodsEntity.findGoodsStatus' : queryParams.findGoodsStatus,
						'noLabelGoodsVo.noLabelGoodsEntity.handoverNo' : queryParams.handoverNo,
						'noLabelGoodsVo.noLabelGoodsEntity.vehicleNo' : queryParams.vehicleNo,
						'noLabelGoodsVo.noLabelGoodsEntity.beginTime' : queryParams.beginTime,
						'noLabelGoodsVo.noLabelGoodsEntity.endTime' : queryParams.endTime,
						'noLabelGoodsVo.noLabelGoodsEntity.previousOrgCode' : queryParams.previousOrgCode,
						'noLabelGoodsVo.noLabelGoodsEntity.findOrgCode' : exceptiongoods.nolabelgoods.bigOrgCode
					}
				});	
		}
	}
	
});

//是否泄漏货
Ext.define('Foss.exceptiongoods.nolabelgoods.LeakGoodsStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'Y','name':exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelgoods.yes')},//是
			{'code':'N','name':exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelgoods.no')}//否
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
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//是否快递货
Ext.define('Foss.exceptiongoods.nolabelgoods.ExpressGoodsStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			//{'code':'','name':''},//空
			{'code':'Y','name':exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelgoods.yes')},//是
			{'code':'N','name':exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelgoods.no')}//否
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
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询无标签多货表格
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLabelGoodsGrid', {
	extend:'Ext.grid.Panel',
	//指定表格的高度
	height: 500,
	//指定表格的宽度
    //width:780,
	autoScroll:true,
	//增加表格列的分割线
	columnLines: true,
	//表格对象增加一个边框
    frame: true,
    
	columns: [{
			xtype : 'actioncolumn',
			//text : '操作',
			text : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operate'),
			width : 60,
			align : 'center',
			items : [ {
				//tooltip : '修改',
				tooltip : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.edit'),
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					 var rec = grid.getStore().getAt(rowIndex);
					 
					/* if((rec.get('stockStatus == 'TO_ABANDON')){
				 			Ext.MessageBox.alert("提示", "货物已转弃货");
							return;
				 		}*/
					 if((rec.get('findGoodsStatus') == 'TO_ABANDON')){
				 			Ext.MessageBox.alert("提示", "货物已转弃货");
							return;
				 		}
					 
					 if('N' == rec.get('isReportOa')){
					 	 var editWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getEditWindow();
						 var editForm = exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm();
						 editForm.loadRecord(rec);
						 
						 exceptiongoods.nolabelgoods.editNoLabelBillNo = rec.get('noLabelBillNo');
						 
						 exceptiongoods.nolabelgoods.editRec = rec;
						 
						 editForm.findField('findTime').setValue(Ext.Date.format(rec.get('findTime'),'Y-m-d H:i:s'));
						
						 var previousOrgSelector = editForm.findField('previousOrgCode');
						 previousOrgSelector.getStore().load({params:{'commonOrgVo.name' : rec.get('previousOrgName')}});
						 
						 var goodsAreaSelector = editForm.findField('goodsAreaCode');
						 goodsAreaSelector.getStore().load({params:{'goodsAreaVo.goodsAreaEntity.goodsAreaName' : rec.get('goodsAreaName')}});
						 
						 var findUserSelector = editForm.findField('findUserCode');
						 findUserSelector.getStore().load({params:{'employeeVo.employeeDetail.empName' : rec.get('findUserName')}});
						 
						 editWindow.show();
					 }else{
					 	//Ext.MessageBox.alert("提示", "已上报OA的数据不可修改");
					 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.no.edit'));
					 	
					 }
					 
				}
			},{
				//tooltip : '删除',
				tooltip : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.delete'),
				iconCls : 'deppon_icons_delete',
				handler : function(grid, rowIndex, colIndex) {
					 var rec = grid.getStore().getAt(rowIndex);
					 
					 if((rec.get('findGoodsStatus') == 'TO_ABANDON')){
				 			Ext.MessageBox.alert("提示", "货物已转弃货");
							return;
				 		}
					 
					 if('N' == rec.get('isReportOa')){
						 //Ext.MessageBox.confirm('提示', '确认删除该无标签货物吗？',function(btn){
					 	 Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.confirm.delete'),function(btn){
							if(btn == 'yes'){	
								Ext.Ajax.request({
					    			url: exceptiongoods.realPath('deleteNoLabelGoods.action'),
									params:{
											'noLabelGoodsVo.noLabelGoodsEntity.id' : rec.get('id'),
											'noLabelGoodsVo.noLabelGoodsEntity.goodsQty' :  rec.get('goodsQty'), 
											'noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo' : rec.get('noLabelBillNo')
										   },
					    			success:function(response){
					    				var result = Ext.decode(response.responseText);
					    				//Ext.ux.Toast.msg('提示', '删除成功！', 'ok', 3000);
					    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.delete.success'), 'ok', 3000);
					    				exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
					    			},
					    			exception : function(response) {
					    				var result = Ext.decode(response.responseText);
					    				//Ext.ux.Toast.msg('删除失败', result.message, 'error', 3000);
					    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.delete.failure'), result.message, 'error', 3000);
					    			}
				    			});	
							}
						});
					 }else{
					 	//Ext.MessageBox.alert("提示", "已上报OA的数据不可删除");
					 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.no.delete'));
					 }
				}
			  }
			]
		},{
			//header: '无标签运单号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
			dataIndex: 'noLabelBillNo',
			windowClassName : 'Foss.exceptiongoods.nolabelgoods.DetailWindow',
			// 定义列类型为扩展的openwindowcolumn类型
			xtype : 'openwindowcolumn'

			/*renderer : function(value){
				if(value!=null){
					return '<a href="javascript:exceptiongoods.nolabelgoods.showNoLabelGoodsDetail('+"'" + value + "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			}*/
		},{ 
			//header: '无标签流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelserialno'),
			dataIndex: 'noLabelSerialNo'
		},{ 
			//header: '车次号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.vehicleno'),
			dataIndex: 'vehicleNo'
		},{ 
			//header: '清仓发现货区', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.stocktaking.find.goodsarea'),
			dataIndex: 'goodsAreaName'
		},{ 
			//header: '发现人', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.person'),
			dataIndex: 'findUserName'
		},{ 
			//header: '上一环节部门', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.previous.org'),
			dataIndex: 'previousOrgName'
		},{ 
			//header: '库存状态', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.stock.status'),
			dataIndex: 'stockStatus',
			hidden: true,
			renderer : function(value){
				if(value!=null){
					var stockStatusName = FossDataDictionary.rendererSubmitToDisplay (value,'NO_LABEL_GOODS_STOCK_STATE');
					return stockStatusName;
				}
			}
			
		},{ 
			//header: '是否已打印无标签多货', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.isprint.nolabel'),
			dataIndex: 'isPrintNoLabel',
			renderer : function(value){
				return value == 'Y'?'是':'否';
			}
		},{ 
			//header: 'OA差错编号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.oa.error.no'),
			dataIndex: 'oaErrorNo'
		},{ 
			//header: '原运单号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.waybillno'),
			dataIndex: 'originalWaybillNo'
		},{ 
			//header: '原流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.serialno'),
			dataIndex: 'originalSerialNo'
		},{ 
			//header: '是否已重打标签', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.isrepeatprint'),
			dataIndex: 'isPrintOriginalLabel',
			renderer : function(value){
				return value == 'Y'?'是':'否';
			}
		},{ 
			//header: '是否为泄漏货', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.leakgoods'),
			dataIndex: 'leakGoods',
			renderer : function(value){
				if(Ext.isEmpty(value)) {
					return '';
				} else {
					return value == 'Y'?'是':'否';
				}
			}
		},{ 
			//header: '是否为快递货', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.expressgoods'),
			dataIndex: 'expressGoods',
			renderer : function(value){
				if(Ext.isEmpty(value)) {
					return '';
				} else {
					return value == 'Y'?'是':'否';
				}
			}
		},{
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsStatus'),
			dataIndex: 'goodsStatus',
			renderer : function(value){
				if(Ext.isEmpty(value)) {
					return '';
				} else {
					if(value == 'UNCONFIRM') {
						return '未确认';
					} else if(value == 'CONFIRM') {
						return '已确认';
					} else if(value == 'EXCEPTIONCONFIRM') {
						return '异常货已处理确认';
					} else {
						return '';
					}
				}
			}
		}],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			
			me.store = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLabelGoodsStore');
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
			exceptiongoods.nolabelgoods.pagingBar = me.bbar;
			
			me.tbar = [{
					xtype: 'button',
					//text: '新增',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.add'),
					disabled: !exceptiongoods.nolabelgoods.isPermission('/tfr-stock-web/exceptiongoods/generateNoLabelGoodsBillNo.action'),
					hidden: !exceptiongoods.nolabelgoods.isPermission('/tfr-stock-web/exceptiongoods/generateNoLabelGoodsBillNo.action'),
					gridContainer: this,
					handler: function() {
						 Ext.Ajax.request({
			    			url: exceptiongoods.realPath('generateNoLabelGoodsBillNo.action'),
			    			success:function(response){
			    				var result = Ext.decode(response.responseText);
			    				exceptiongoods.nolabelgoods.noLabelBillNo = result.noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo;
			    				exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().
			    				findField('noLabelBillNo').setValue(exceptiongoods.nolabelgoods.noLabelBillNo);
			    			},
			    			exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('生成无标签运单号失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.generate.nolabelbillno.failure'), result.message, 'error', 3000);
			    			}
			    			});	
			    			
						var addWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getAddWindow();
						exceptiongoods.nolabelgoods.setAllowBlank(false);
						addWindow.show();
					}
				},{
					xtype: 'button',
					//text: '打印',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print'),
					disabled: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/printNolabelgoodsButton'),
					hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/printNolabelgoodsButton'),
					gridContainer: this,
					handler: function() {
						
						var selectedRecords = me.getSelectionModel().getSelection();
						if(selectedRecords.length < 1){
						 	//Ext.MessageBox.alert("提示", "请先勾选需要打印标签的数据");
						 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.print.data'));
							return;
						}
					 	var noLabelGoodsList = new Array();
					 	var noLabelBillNo = selectedRecords[0].data.noLabelBillNo;
					 	
					 	var noLabelBillNos = '';
					 	var serialNos = '';
					 	var goodsQtys = '';
					 	var packageTypes = '';
					 	var goodsAreaNames = '';
					 	var handoverNos = '';
					 	var goodsNames = '';
					 	var weights = '';
					 	var volumes ='';
					 	for(var i in selectedRecords){
							noLabelGoodsList.push(selectedRecords[i].data); 
							noLabelBillNos += selectedRecords[i].data.noLabelBillNo + ",";
							serialNos += selectedRecords[i].data.noLabelSerialNo + ",";
							goodsQtys += selectedRecords[i].data.goodsQty + ",";
							packageTypes += FossDataDictionary.rendererSubmitToDisplay(selectedRecords[i].data.packageType,'NO_LABEL_GOODS_PACKAGE_TYPE') + ",";
							goodsAreaNames += selectedRecords[i].data.goodsAreaName + ",";
							handoverNos += selectedRecords[i].data.handoverNo + ",";
							goodsNames += selectedRecords[i].data.goodsName + ",";
							weights += selectedRecords[i].data.weight + ",";
							volumes += selectedRecords[i].data.volume +'('+ selectedRecords[i].data.volumeLWH +')' + ",";
						}
						noLabelBillNos = noLabelBillNos.substring(0, noLabelBillNos.length-1);
						serialNos = serialNos.substring(0, serialNos.length-1);
						goodsQtys = goodsQtys.substring(0, goodsQtys.length-1);
						packageTypes = packageTypes.substring(0, packageTypes.length-1);
						goodsAreaNames = goodsAreaNames.substring(0, goodsAreaNames.length-1);
						handoverNos = handoverNos.substring(0, handoverNos.length-1);
						goodsNames = goodsNames.substring(0, goodsNames.length-1);
						weights = weights.substring(0, weights.length-1);
						volumes = volumes.substring(0, volumes.length-1);
						
						var currentDept = FossUserContext.getCurrentDept();
						var currentOrgName = currentDept.name;
						var emp = FossUserContext. getCurrentUserEmp();
						var userCode = emp.empCode;
						
						var jsonParam = {noLabelGoodsVo: {noLabelGoodsList:noLabelGoodsList}};
						
						var vurl = "http://localhost:8077/print";
						Ext.data.JsonP.request({
					        url: vurl,
					        callbackKey: 'callback',
						    params: {
						  	 lblprtworker:"NoLabelGoodsLabelPrintWorker",
						     optusernum: userCode, //工号  1
							 printdate: new Date(),  //打印时间  1
							 number: noLabelBillNos,    //无标签单号  n
							 serialnos: serialNos,     //流水号  n
							 totalpieces: goodsQtys,     //总件数 n
							 packing: packageTypes,     //包装   n
							 currentOrgName: currentOrgName,  //当前部门名称 1
							 goodsAreaName: goodsAreaNames,   //货区 n
							 handoverNo: handoverNos,        //交接单号 n
							 goodsName: goodsNames,     //货物名称 n
							 weight: weights,        //重量 n
							 volume: volumes         //体积 n
						    },
							callback: function() {
					 			//回调函数，不管请求成功与否都执行
					 			//alert("callback");
							},   	    
						    success: function(result, request) {
						    	var ret_code = result.data.code;
						    	if(ret_code == '1'){
						    		Ext.Ajax.request({
					    			url: exceptiongoods.realPath('updateNoLabelGoodsPrintInfo.action'),
					    			jsonData:jsonParam,
					    			success:function(response){
					    				//Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
					    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
					    				exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
					    			},
					    			exception:function(response){
					    				var result = Ext.decode(response.responseText);
    									//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
    									Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
					    			}
						         })	
						    	}else{
						    		//Ext.ux.Toast.msg('提示', result.data.msg, 'error', 3000);
						    		Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.data.msg, 'error', 3000);
						    	}
								
						    },
						    failure : function (result, request) {
								//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
								Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.failure'), 'error', 3000);
						    }
						});
					}
				},{
					xtype: 'button',
					//text: '上报OA',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa'),
					//hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/reportOANoLabelGoodsButton'),
					hidden: true,
					gridContainer: this,
					handler: function() {
						
						var selectedRecords = me.getSelectionModel().getSelection();
						if(selectedRecords.length < 1){
						 	//Ext.MessageBox.alert("提示", "请先勾选需要上报OA的数据");
						 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.report.oa.data'));
							return;
						}
						
					 	var noLabelSerialNoList = new Array();
					 	for(var i in selectedRecords){
					 		if(!Ext.isEmpty(selectedRecords[i].data.oaErrorNo)){
					 			//Ext.MessageBox.alert("提示", "已上报过的数据不可重复上报");
					 			Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.not.repeat.report'));
					 			return;
					 		}
					 		if((selectedRecords[i].data.findGoodsStatus == 'TO_ABANDON')){
					 			Ext.MessageBox.alert("提示", "货物已转弃货");
					 			item.enable();
								return;
					 		}
							noLabelSerialNoList.push(selectedRecords[i].data); 
						}
					 	var jsonParam = {noLabelGoodsVo: {noLabelGoodsList:noLabelSerialNoList}};
					 	
					 	Ext.Msg.wait('处理中，请稍候...', '提示'); //进度条等待  
						Ext.Ajax.request({
			    			url: exceptiongoods.realPath('reportOANoLabelGoods.action'),
			    			jsonData:jsonParam,
			    			timeout: 300000,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				//Ext.ux.Toast.msg('提示', '上报OA成功', 'ok', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa.success'), 'ok', 3000);
			    				exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
			    			},
			    			exception:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
			    			}
				         })	
					}
				},{
					xtype: 'button',
					//text: '登入',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.login'),
					//hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/loginExceptionStockButton'),
					hidden: true,
					gridContainer: this,
					handler: function(item) {
						item.disable();
						var selectedRecords = me.getSelectionModel().getSelection();
						if(selectedRecords.length < 1){
						 	//Ext.MessageBox.alert("提示", "请先勾选需要登入 的数据");
						 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.login.data'));
						 	item.enable();
						 	return;
						}
					 	var noLabelSerialNoList = new Array();
					 	for(var i in selectedRecords){
					 		if(!(selectedRecords[i].data.stockStatus == 'NO_STOCK')){
					 			//Ext.MessageBox.alert("提示", "只可登入库存状态为'未登入'的货物");
					 			Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.nologin.can.login'));
					 			item.enable();
					 			return;
					 		}
					 		if((selectedRecords[i].data.findGoodsStatus == 'TO_ABANDON')){
					 			Ext.MessageBox.alert("提示", "货物已转弃货");
					 			item.enable();
								return;
					 		}
							noLabelSerialNoList.push(selectedRecords[i].data); 
						}
					 	var jsonParam = {noLabelGoodsVo: {noLabelGoodsList:noLabelSerialNoList}};
						
						Ext.Ajax.request({
			    			url: exceptiongoods.realPath('loginExceptionStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				//Ext.ux.Toast.msg('提示', '登入成功', 'ok', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.login.success'), 'ok', 3000);
			    				exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
			    				item.enable();
			    			},
			    			exception:function(response){
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('登入失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.login.failure'), result.message, 'error', 3000);
			    				item.enable();
			    			}
				         })	
					}
				}, {
					xtype: 'button',
					//text: '登出',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.logout'),
					//hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/logoutExceptionStockButton'),
					hidden: true,
					gridContainer: this,
					handler: function() {
						var selectedRecords = me.getSelectionModel().getSelection();
						if(selectedRecords.length < 1){
						 	//Ext.MessageBox.alert("提示", "请先勾选需要登出 的数据");
						 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.logout.data'));
							return;
						}
					 	var noLabelSerialNoList = new Array();
					 	for(var i in selectedRecords){
					 		if(!(selectedRecords[i].data.stockStatus == 'IN_STOCK') || Ext.isEmpty(selectedRecords[i].data.originalWaybillNo)){
					 			//Ext.MessageBox.alert("提示", "货物的找货状态'为已找'且库存状态为'已登入'才可登出");
					 			Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.and.instock.can.logout'));
								return;
					 		}
					 		if((selectedRecords[i].data.findGoodsStatus == 'TO_ABANDON')){
					 			Ext.MessageBox.alert("提示", "货物已转弃货");
					 			item.enable();
								return;
					 		}
							noLabelSerialNoList.push(selectedRecords[i].data); 
						}
					 	var jsonParam = {noLabelGoodsVo: {noLabelGoodsList:noLabelSerialNoList}};
						
						Ext.Ajax.request({
			    			url: exceptiongoods.realPath('logoutExceptionStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				//Ext.ux.Toast.msg('提示', '登出成功', 'ok', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.logout.success'), 'ok', 3000);
			    				exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
			    			},
			    			exception:function(response){
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('登出失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.logout.failure'), result.message, 'error', 3000);
			    			}
				         })	
					}
				}, {
					xtype: 'button',
					//text: '打印指定标签',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.label'),
					disabled: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/queryPrintLabelInfoButton'),
					hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/queryPrintLabelInfoButton'),
					gridContainer: this,
					handler: function() {
						
						var printAppointedWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getPrintAppointedWindow();
						exceptiongoods.nolabelgoods.printAppointedForm.getForm().findField('waybillNo').allowBlank = false;
						printAppointedWindow.show();
					}
			    }, {
					xtype: 'button',
					text: '无标签转弃货',
					//text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.label'),
					//hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/nolabelToAbandonButton'),
					hidden: true,
					gridContainer: this,
					handler: function(item) {
						item.disable();
						var selectedRecords = me.getSelectionModel().getSelection();
						if(selectedRecords.length < 1){
						 	Ext.MessageBox.alert("提示", "请先勾选需要转弃货的数据");
						 	item.enable();
						 	return;
						}else if(selectedRecords.length > 1){
							Ext.MessageBox.alert("提示", "请先勾选一条需要转弃货的数据");
						 	item.enable();
						}else{
							
							if((selectedRecords[0].data.findGoodsStatus == 'TO_ABANDON')){
					 			Ext.MessageBox.alert("提示", "货物已转弃货");
					 			item.enable();
								return;
					 		}
							
							var editWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getNoLabelToAbandonWindow();
							
							var jsonParam = {noLabelGoodsVo: {noLabelGoodsEntity:{
								noLabelBillNo:selectedRecords[0].data.noLabelBillNo,
								noLabelSerialNo:selectedRecords[0].data.noLabelSerialNo
							}}};
							
							
							Ext.Ajax.request({
				    			url: exceptiongoods.realPath('queryNoLabelGoodsOneById.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
				    				var result = Ext.decode(response.responseText);
				    				
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('noLabelBillNo').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('noLabelSerialNo').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.noLabelSerialNo);
				    				
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('userName').setValue(result.noLabelGoodsVo.userName);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('bigOrgName').setValue(result.noLabelGoodsVo.bigOrgName);
				    				
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('goodsName').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.goodsName);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('findOrgName').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.findOrgName);

				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('weight').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.weight);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('volume').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.volume);

				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('goodsQty').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.goodsQty);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('toOaTime').setValue(result.noLabelGoodsVo.toOaTime);

				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('oaErrorNo').setValue(result.noLabelGoodsVo.noLabelGoodsEntity.oaErrorNo);
				    				exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('handleType').setValue("无标签货物转弃货");
				    				
									
									exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm().
				    				findField('toAbandonNotes').setValue("");
									
									var tempStatus = result.noLabelGoodsVo.noLabelGoodsEntity.findGoodsStatus;
									if(tempStatus){
										if('FIND'==tempStatus){
											Ext.MessageBox.alert("提示", "该货物已被认领");
								 			item.enable();
								 			return ;
										}
									}
									
									
									var tempTime = result.noLabelGoodsVo.toOaTime;
									if(tempTime){
										if(parseInt(tempTime)<90){
											Ext.MessageBox.alert("提示", "该货物登记时间不足90天");
								 			item.enable();
								 			return ;
										}
									}
									
				    				 editWindow.show()
				    				item.enable();
				    			},
				    			exception:function(response){
				    				Ext.Msg.hide();
				    				item.enable();
				    			}
					         })	
							
								//exceptiongoods.nolabelgoods.setAllowBlank(false);
							
						}
						 
						
					}
			    }, {
					xtype: 'button',
					text: '补打电子运单',
					disabled: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/queryPrintLabelInfoButton'),
					hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/queryPrintLabelInfoButton'),
					gridContainer: this,
					handler: function() {
						
						var wallbillElectronWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getWallbillElectronToAbandonWindow();
						exceptiongoods.nolabelgoods.wallbillElectronForm.getForm().findField('waybillNo').allowBlank = false;
						wallbillElectronWindow.show();
					}
			    }];
			
			me.callParent([cfg]);
		}
});
/**
 * 新增无标签多货窗口*********************************************************************************
 */

//定义录入无标签货物表单
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLableGoodsForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		name: 'id',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '无标签运单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
		name: 'noLabelBillNo',
		readOnly: true,
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '品名',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'goodsName',
		columnWidth:.3,
		maxLength: 50,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')/*,
		listeners: {
			blur: function(field,epts) {
				if(!Ext.isEmpty(field.getValue())){
					//TODO 判断品类请求
					Ext.Ajax.request({
						url: exceptiongoods.realPath('queryTypeByGoodsName.action'),
						params:{'noLabelGoodsVo.noLabelGoodsEntity.goodsName': field.getValue()},
						success:function(response){
							var result = Ext.decode(response.responseText);
							var goodsType = result.noLabelGoodsVo.noLabelGoodsEntity.goodsType;
							exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('goodsType').setValue(goodsType);	
	                    },
	                    exception:function(response){
	                    	var result = Ext.decode(response.responseText);
		    				Ext.ux.Toast.msg('提示', '查询品类失败', 'error', 3000);
		    			}
	                });
					
				} else {
					//清空品类
					exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('goodsType').setValue(null);
				}
			}
		}*/
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '品牌',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.brand'),
		maxLength: 50,
		name: 'goodsBrand',
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_CATEGORY',
		{
			//fieldLabel : '品类',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstype'),
			name : 'goodsType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			//readOnly: true,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_PACKAGE_TYPE',
		{
			//fieldLabel : '包装',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.package'),
			name : 'packageType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '车次号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.vehicleno'),
		name: 'vehicleNo',
		//regex: /[A-Za-z0-9]+-?[A-Za-z0-9]+/,
		//regexText: '只能输入数字或字母',
		//regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.numbers.letters'),
		maxLength: 50,
		columnWidth:.3
	},{
		xtype: 'textfield',
		//fieldLabel: '总件数',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstotal'),
		name: 'goodsQty',
		columnWidth:.3,
		//regex: /^\+?[1-9][0-9]$/,
		regex: /^[1-9]*[1-9][0-9]*$/,
		//regexText: '字段只能是正整数',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.positive.integer'),
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		//fieldLabel: '包装关键字',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.package.keyword'),
		name: 'packageKeyword',
		hidden: true,
		maxLength: 50,
		columnWidth:.3/*,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')*/
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_MATERIAL_PROPERTY',
		{
			//fieldLabel : '内物属性',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goods.property'),
			name : 'goodsProperty',
			editable : false,
			hidden: true,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false/*,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')*/
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '手写关键字',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handwriting.keyword'),
		name: 'handwritingKeyword',
		maxLength: 50,
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '重量(公斤)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.weight'),
		name: 'weight',
		columnWidth:.3,
		regex: /^[0-9]+(.[0-9]{1,2})?$/,
		//regexText: '最多两位小数',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.two.decimal'),
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		//fieldLabel: '长*宽*高(CM)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.volume.lwh'),
		name: 'volumeLWH',
		columnWidth:.3,
		regex: /^[0-9]+\*[0-9]+\*+[0-9]+$/,
		//regexText: '输入格式为： 长*宽*高',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.format.lwh'),
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		listeners: {
			change:function(field,value){
				if(field.isValid()){
					var volumeLWH = value.split('*');
					var volume = (volumeLWH[0]*volumeLWH[1]*volumeLWH[2])/1000000;
					exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('volume').setValue(volume);
				}
			}
		}
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '体积(方)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.volume'),
		name: 'volume',
		readOnly: true,
		columnWidth:.3,
		cls:'readonlyhaveborder'
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		hidden: true,
		//fieldLabel: '同车少货备注',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.lossgoods.notes'),
		name: 'lossGoodsNotes',
		maxLength: 300,
		columnWidth:.3
	},{
		
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '上一环节部门',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.previous.org'),
		labelWidth: 100,
		id: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsForm_previousOrgCode_ID',
		name: 'previousOrgCode',
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '上一环节部门名称',
		name: 'previousOrgName',
		hidden: true
	},/*{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},*/{
		xtype: 'commonemployeeselector',
		//fieldLabel: '发现人',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.person'),
		labelWidth: 100,
		name: 'findUserCode',
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		fieldLabel: '发现人姓名',
		name: 'findUserName',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commongoodsareaselector',
		//fieldLabel: '发现货区',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goodsarea'),
		labelWidth: 100,
		name: 'goodsAreaCode',
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '发现货区名称',
		name: 'goodsAreaName',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'datetimefield_date97',
	    //fieldLabel: '发现时间',
	    fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.findtime'),
		id: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsForm_findTime_ID',
	    name: 'findTime',
		time: true,
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		dateConfig: {
		//显示所选择日期的input对象的id属性，默认为此组件id + '-inputEl'
		el: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsForm_findTime_ID-inputEl',
		//显示日期的格式，默认为'yyyy-MM-dd'，如果time设置为true,则默认为'yyyy-MM-dd HH:mm:ss'
		dateFmt: 'yyyy-MM-dd',
		//最小选择日期
		minDate: '1970-01-01',
		//最大选择日期
		maxDate: '2020-01-01',
		//起始选择日期
		startDate: '1980-01-01'
		}
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_DISCOVER_STATE',
		{
			//fieldLabel : '无标签发现类型',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabel.discover.type'),
			name : 'findType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		hidden: true,
		//fieldLabel: '交接单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handoverno'),
		name: 'handoverNo',
		regex: /^[A-Za-z0-9]+$/,
		//regexText: '只能输入数字或字母',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.numbers.letters'),
		maxLength: 10,
		columnWidth:.3
	},/*{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},*/{
		xtype: 'commonemployeemultiselector',
		hidden: true,
		//fieldLabel: '短信通知对象',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.note.notify.person'),
		labelWidth: 100,
		name: 'noteNotifyPerson',
		columnWidth:.3
	},/*{
		xtype: 'commonemployeeselector',
		fieldLabel: '短信通知对象',
		name: 'noLabelGoodsVo.noLabelGoodsEntity.noteNotifyPerson',
		columnWidth:.9
	},{
        xtype:'button',
        text: '选择对象',
        cls: 'btnAfterTextfield',
        columnWidth:.1
    },*/{
		xtype:'combo',
		name:'leakGoods',
		fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.leakgoods'),//是否泄漏货
		editable : false,
		value : '',
		columnWidth: .3,
		allowBlank: false,
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.exceptiongoods.nolabelgoods.LeakGoodsStore')
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype:'combo',
		name:'expressGoods',
		fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.expressgoods'),//是否快递货
		editable : false,
		value : '',
		columnWidth: .3,
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.exceptiongoods.nolabelgoods.ExpressGoodsStore'),
		tpl:'<tpl for=".">' +  
        '<div class="x-boundlist-item" style="height:18px;">' +  
        '{name}'+ 
        '</div>'+
        '</tpl>'
	},{
		xtype: 'textarea',
		//fieldLabel: '事件经过(小于1000字)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.eventprocess'),
		autoScroll:true,
		name: 'eventProcess',
		maxLength: 1000,
		columnWidth:1,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		fieldLabel: '发现部门编号',
		name: 'findOrgCode',
		hidden: true
	},{
		xtype: 'textfield',
		fieldLabel: '发现部门名称',
		name: 'findOrgName',
		hidden: true
	},{
        xtype:'filefield',
        //fieldLabel: '<span style="color:red;">*</span>正面照',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.frontphoto'),
        name: 'frontPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File1_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder', 
		//定义选择文件按钮的文本信息
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.9,
        allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File1_ID').reset();
        }
    },{
        xtype:'filefield',
        //fieldLabel: '<span style="color:red;">*</span>整体照',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.entiretyphoto'),
        name: 'entiretyPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File2_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.9,
        allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File2_ID').reset();
        }
    },{
        xtype:'filefield',
        //fieldLabel: '<span style="color:red;">*</span>内物照',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsphoto'),
        name: 'goodsPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File3_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.9,
        allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File3_ID').reset();
        }
    },{
        xtype:'filefield',
        //fieldLabel: '附加照1',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.photoa'),
        name: 'photoA',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File4_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.9
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File4_ID').reset();
        }
    },{
        xtype:'filefield',
        //fieldLabel: '附加照2',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.photob'),
        name: 'photoB',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File5_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.9
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File5_ID').reset();
        }
    },{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.08,
				handler: function(){
					exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().reset();
					exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().
			    				findField('noLabelBillNo').setValue(exceptiongoods.nolabelgoods.noLabelBillNo);
			 	}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			//text: '保存',
			text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save'),
			disabled: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/addNoLabelGoodsButton'),
			hidden: !exceptiongoods.nolabelgoods.isPermission('exceptiongoods/addNoLabelGoodsButton'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				
				/*Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File1_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File2_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File3_ID').allowBlank = blankFlag;*/
				
				var form = exceptiongoods.nolabelgoods.noLableGoodsForm.getForm();
				
				//校验货区和交接单号
				var handoverNo = form.findField('handoverNo').getValue();
				var goodsAreaCode = form.findField('goodsAreaCode').getValue();
				/*if(Ext.isEmpty(handoverNo) && Ext.isEmpty(goodsAreaCode)){
					//Ext.MessageBox.alert("提示", "交接单号和货区不能全部为空！");
					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handoverno.goodsarea.notnull'));
					return;
				}*/
				//设置上一部门名称
				var previousOrgCode = form.findField('previousOrgCode').getValue();
				if(!Ext.isEmpty(previousOrgCode)){
					var previousOrgNameRecord = form.findField('previousOrgCode').findRecordByValue(previousOrgCode);
					form.findField('previousOrgName').setValue(previousOrgNameRecord.data.name);
				}
				
				//设置货区名称
				if(!Ext.isEmpty(goodsAreaCode)){
					var goodsAreaNameRecord = form.findField('goodsAreaCode').findRecordByValue(goodsAreaCode);
					form.findField('goodsAreaName').setValue(goodsAreaNameRecord.data.goodsAreaName);
				}
				
				//设置发现人
				var findUserCode = form.findField('findUserCode').getValue();
				if(!Ext.isEmpty(findUserCode)){
					var findUserNameRecord = form.findField('findUserCode').findRecordByValue(findUserCode);
					form.findField('findUserName').setValue(findUserNameRecord.data.empName);
				}
				
				//设置当前用户大部门
				form.findField('findOrgCode').setValue(exceptiongoods.nolabelgoods.bigOrgCode);
				form.findField('findOrgName').setValue(exceptiongoods.nolabelgoods.bigOrgName);
				//设置保存文件大小
				var allowFileSize = 5;
				var photos = ['frontPhoto','entiretyPhoto','goodsPhoto','photoA','photoB'];
				var tips = validateFileSize(photos,allowFileSize);
				if(tips != '' && tips.length != 0){
					alert(tips);
					return;
				}				
				var formParams = form.getValues();
				
				if(form.isValid()){
					Ext.Msg.wait( '处理中，请稍候...' , '提示' ); //进度条等待  
	                form.submit({
	                	url: exceptiongoods.realPath('addNoLabelGoods.action'),
	                    params: {
	                    		'noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo' : formParams.noLabelBillNo,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsName' : formParams.goodsName,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsBrand' : formParams.goodsBrand,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsType' : formParams.goodsType,
								'noLabelGoodsVo.noLabelGoodsEntity.packageType' : formParams.packageType,
								'noLabelGoodsVo.noLabelGoodsEntity.packageKeyword' : formParams.packageKeyword,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsProperty' : formParams.goodsProperty,
								'noLabelGoodsVo.noLabelGoodsEntity.handwritingKeyword' : formParams.handwritingKeyword,
								'noLabelGoodsVo.noLabelGoodsEntity.weight' : formParams.weight,
								'noLabelGoodsVo.noLabelGoodsEntity.volumeLWH' : formParams.volumeLWH,
								'noLabelGoodsVo.noLabelGoodsEntity.volume' : formParams.volume,
								'noLabelGoodsVo.noLabelGoodsEntity.lossGoodsNotes' : formParams.lossGoodsNotes,
								'noLabelGoodsVo.noLabelGoodsEntity.previousOrgCode' : formParams.previousOrgCode,
								'noLabelGoodsVo.noLabelGoodsEntity.previousOrgName' : formParams.previousOrgName,                       
								'noLabelGoodsVo.noLabelGoodsEntity.goodsAreaCode' : formParams.goodsAreaCode,                  
								'noLabelGoodsVo.noLabelGoodsEntity.goodsAreaName' : formParams.goodsAreaName,                        
								'noLabelGoodsVo.noLabelGoodsEntity.findTime' : formParams.findTime,                           
								'noLabelGoodsVo.noLabelGoodsEntity.findType' : formParams.findType,                           
								'noLabelGoodsVo.noLabelGoodsEntity.findUserCode' : formParams.findUserCode,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findUserName' : formParams.findUserName,                          
								'noLabelGoodsVo.noLabelGoodsEntity.vehicleNo' : formParams.vehicleNo,                           
								'noLabelGoodsVo.noLabelGoodsEntity.goodsQty' :  formParams.goodsQty,                        
								'noLabelGoodsVo.noLabelGoodsEntity.handoverNo' : formParams.handoverNo,                           
								'noLabelGoodsVo.noLabelGoodsEntity.noteNotifyPerson' : formParams.noteNotifyPerson, 
								'noLabelGoodsVo.noLabelGoodsEntity.leakGoods'　: formParams.leakGoods,
								'noLabelGoodsVo.noLabelGoodsEntity.expressGoods'　: formParams.expressGoods,
								'noLabelGoodsVo.noLabelGoodsEntity.eventProcess' : formParams.eventProcess,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findOrgCode' : formParams.findOrgCode,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findOrgName' : formParams.findOrgName 
						    },
	                	success: function(form, action) {
	                		Ext.Msg.hide(); 
	                    	//Ext.ux.Toast.msg('提示', '保存成功！', 'ok', 3000);
	                        Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'),  exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save.success'), 'ok', 3000);
	                       
	                        exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
	                        var addWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getAddWindow();
	                        addWindow.close();
	                    },
	                    failure: function(form, action) {
	                    	Ext.Msg.hide();
					        var result = Ext.decode(response.responseText);
		    				//Ext.ux.Toast.msg('保存失败', result.message, 'error', 3000);
		    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save.failure'), result.message, 'error', 3000);
					    }
	                });
	            }
				
			}
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//上传文件大小限制
function validateFileSize(names,allowSize){
	var tips = '';
	for(var i=0 ;i<names.length;i++){
		var files = document.getElementsByName(names[i])[0].files;
		if(files !=null && files.length > 0){
			if(files[0].size/1024/1024 > allowSize){
				tips = tips + files[0].name + ',';
			}
		}
	}
	if(tips !='' && tips.length != 0){
		tips = tips.substring(0,tips.length-1) +'文件大于'+allowSize+'M，请重新选择！';
	}
	return tips;
}
//修改无标签货物表单
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLableGoodsEditForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		name: 'id',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '无标签运单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
		name: 'noLabelBillNo',
		readOnly: true,
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '品名',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'goodsName',
		columnWidth:.3,
		maxLength: 50,
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')/*,
        listeners: {
			blur: function(field,epts) {
				if(!Ext.isEmpty(field.getValue())){
					//TODO 判断品类请求
					Ext.Ajax.request({
						url: exceptiongoods.realPath('queryTypeByGoodsName.action'),
						params:{'noLabelGoodsVo.noLabelGoodsEntity.goodsName': field.getValue()},
						success:function(response){
							var result = Ext.decode(response.responseText);
							var goodsType = result.noLabelGoodsVo.noLabelGoodsEntity.goodsType;
							exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm().findField('goodsType').setValue(goodsType);	
	                    },
	                    exception:function(response){
	                    	var result = Ext.decode(response.responseText);
		    				Ext.ux.Toast.msg('提示', '查询品类失败', 'error', 3000);
		    			}
	                });
					
				} else {
					//清空品类
					exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm().findField('goodsType').setValue(null);
				}
			}
		}*/
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '品牌',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.brand'),
		maxLength: 50,
		name: 'goodsBrand',
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_CATEGORY',
		{
			//fieldLabel : '品类',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstype'),
			name : 'goodsType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			//readOnly: true,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_PACKAGE_TYPE',
		{
			//fieldLabel : '包装',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.package'),
			name : 'packageType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),/*{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},*/{
		xtype: 'textfield',
		//fieldLabel: '包装关键字',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.package.keyword'),
		name: 'packageKeyword',
		hidden: true,
		maxLength: 50,
		columnWidth:.3/*,
		allowBlank: false,
		blankText:'字段不能为空'*/
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_MATERIAL_PROPERTY',
		{
			//fieldLabel : '内物属性',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goods.property'),
			name : 'goodsProperty',
			editable : false,
			hidden: true,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false/*,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')*/
		}
	),{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '车次号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.vehicleno'),
		name: 'vehicleNo',
		//regex: /[A-Za-z0-9]+-?[A-Za-z0-9]+/,
		//regexText: '只能输入数字或字母',
		//regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.numbers.letters'),
		maxLength: 50,
		columnWidth:.3
	},{
		xtype: 'textfield',
		//fieldLabel: '总件数',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstotal'),
		name: 'goodsQty',
		columnWidth:.3,
		readOnly: true,
		cls:'readonlyhaveborder',
		//regex: /^\+?[1-9][0-9]$/,
		regex: /^[1-9]*[1-9][0-9]*$/,
		//regexText: '字段只能是正整数',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.positive.integer'),
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '手写关键字',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handwriting.keyword'),
		name: 'handwritingKeyword',
		maxLength: 50,
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '重量(公斤)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.weight'),
		name: 'weight',
		columnWidth:.3,
		regex: /^[0-9]+(.[0-9]{1,2})?$/,
		regexText: '最多两位小数',
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		//fieldLabel: '长*宽*高(CM)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.volume.lwh'),
		name: 'volumeLWH',
		columnWidth:.3,
		regex: /^[0-9]+\*[0-9]+\*+[0-9]+$/,
		regexText: '输入格式为： 长*宽*高',
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		listeners: {
			change:function(field,value){
				if(field.isValid()){
					var volumeLWH = value.split('*');
					var volume = (volumeLWH[0]*volumeLWH[1]*volumeLWH[2])/1000000;
					exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm().findField('volume').setValue(volume);
				}
			}
		}
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '体积(方)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.volume'),
		name: 'volume',
		readOnly: true,
		columnWidth:.3,
		cls:'readonlyhaveborder'
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		hidden: true,
		//fieldLabel: '同车少货备注',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.lossgoods.notes'),
		name: 'lossGoodsNotes',
		maxLength: 300,
		columnWidth:.3
	},{
		
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '上一环节部门',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.previous.org'),
		labelWidth: 100,
		id: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsEditForm_previousOrgCode_ID',
		name: 'previousOrgCode',
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '上一环节部门名称',
		name: 'previousOrgName',
		hidden: true
	},/*{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},*/{
		xtype: 'commonemployeeselector',
		//fieldLabel: '发现人',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.person'),
		labelWidth: 100,
		name: 'findUserCode',
		columnWidth:.3,
		allowBlank: false,
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		fieldLabel: '发现人姓名',
		name: 'findUserName',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commongoodsareaselector',
		//fieldLabel: '发现货区',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goodsarea'),
		labelWidth: 100,
		name: 'goodsAreaCode',
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '发现货区名称',
		name: 'goodsAreaName',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'datetimefield_date97',
	    //fieldLabel: '发现时间',
	    fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.findtime'),
		id: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsEditForm_findTime_ID',
	    name: 'findTime',
		time: true,
		columnWidth:.3,
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		dateConfig: {
		//显示所选择日期的input对象的id属性，默认为此组件id + '-inputEl'
		el: 'Foss_exceptiongoods_nolabelgoods_NoLableGoodsEditForm_findTime_ID-inputEl',
		//显示日期的格式，默认为'yyyy-MM-dd'，如果time设置为true,则默认为'yyyy-MM-dd HH:mm:ss'
		dateFmt: 'yyyy-MM-dd',
		//最小选择日期
		minDate: '1970-01-01',
		//最大选择日期
		maxDate: '2020-01-01',
		//起始选择日期
		startDate: '1980-01-01'
		}
	},
	FossDataDictionary.getDataDictionaryCombo('NO_LABEL_GOODS_DISCOVER_STATE',
		{
			//fieldLabel : '无标签发现类型',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabel.discover.type'),
			name : 'findType',
			editable : false,
			columnWidth:.3,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		}
	),/*{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},*/{
		xtype: 'textfield',
		hidden: true,
		//fieldLabel: '交接单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handoverno'),
		name: 'handoverNo',
		regex: /^[A-Za-z0-9]+$/,
		//regexText: '只能输入数字或字母',
		regexText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.only.numbers.letters'),
		maxLength: 10,
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commonemployeemultiselector',
		hidden: true,
		//fieldLabel: '短信通知对象',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.note.notify.person'),
		labelWidth: 100,
		name: 'noteNotifyPerson',
		columnWidth:.3
	},{
		xtype:'combo',
		name:'leakGoods',
		fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.leakgoods'),//是否泄漏货
		editable : false,
		value : '',
		columnWidth: .3,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.exceptiongoods.nolabelgoods.LeakGoodsStore')
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype:'combo',
		name:'expressGoods',
		fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.expressgoods'),//是否快递货
		editable : false,
		value : '',
		columnWidth: .3,
		allowBlank: false,
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull'),
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.exceptiongoods.nolabelgoods.ExpressGoodsStore'),
		tpl:'<tpl for=".">' +  
        '<div class="x-boundlist-item" style="height:18px;">' +  
        '{name}'+ 
        '</div>'+
        '</tpl>'
	},{
		xtype: 'textarea',
		//fieldLabel: '事件经过(小于1000字)',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.eventprocess'),
		autoScroll:true,
		name: 'eventProcess',
		maxLength: 1000,
		columnWidth:1,
		allowBlank: false,
		//blankText:'字段不能为空'
        blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		fieldLabel: '发现部门编号',
		name: 'findOrgCode',
		hidden: true
	},{
		xtype: 'textfield',
		fieldLabel: '发现部门名称',
		name: 'findOrgName',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '正面照',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.frontphoto'),
		name: 'frontPhotoName',
		readOnly: true,
		columnWidth:.4
	},{
        xtype:'filefield',
       // fieldLabel: '<span style="color:red;">*</span>正面照',
        name: 'frontPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File1_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder', 
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.5
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File1_ID').reset();
        }
    },{
		xtype: 'textfield',
		//fieldLabel: '整体照',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.entiretyphoto'),
		name: 'entiretyPhotoName',
		readOnly: true,
		columnWidth:.4
	},{
        xtype:'filefield',
        //fieldLabel: '<span style="color:red;">*</span>整体照',
        name: 'entiretyPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File2_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
		//buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.5
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File2_ID').reset();
        }
    },{
		xtype: 'textfield',
		//fieldLabel: '内物照',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsphoto'),
		name: 'goodsPhotoName',
		readOnly: true,
		columnWidth:.4
	},{
        xtype:'filefield',
        //fieldLabel: '<span style="color:red;">*</span>内物照',
        name: 'goodsPhoto',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File3_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
		//buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.5
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File3_ID').reset();
        }
    },{
		xtype: 'textfield',
		//fieldLabel: '附加照1',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.photoa'),
		name: 'goodsPhotoAName',
		readOnly: true,
		columnWidth:.4
	},{
        xtype:'filefield',
        name: 'photoA',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File4_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.5
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File4_ID').reset();
        }
    },{
		xtype: 'textfield',
		//fieldLabel: '附加照2',
        fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.photob'),
		name: 'goodsPhotoBName',
		readOnly: true,
		columnWidth:.4
	},{
        xtype:'filefield',
        name: 'photoB',
        id: 'Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File5_ID',
        cls: 'uploadfile',
        cls:'readonlyhaveborder',
        //buttonText: '浏览',
        buttonText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.browse'),
        columnWidth:.5
    },{
        xtype:'button',
        //text: '清除',
        text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.clear'),
        cls: 'cleanBtn',
        columnWidth:.1,
        handler: function() {
        	Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsEditForm_File5_ID').reset();
        }
    },{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.08,
				handler: function(){
					exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm().reset();
			    	
			    	var editForm = exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm();
					 
					editForm.loadRecord(exceptiongoods.nolabelgoods.editRec);
					 
					editForm.findField('findTime').setValue(Ext.Date.format(exceptiongoods.nolabelgoods.editRec.get('findTime'),'Y-m-d H:i:s'));
					
					var previousOrgSelector = editForm.findField('previousOrgCode');
					previousOrgSelector.getStore().load({params:{'commonOrgVo.name' : exceptiongoods.nolabelgoods.editRec.get('previousOrgName')}});
					 
					var goodsAreaSelector = editForm.findField('goodsAreaCode');
					goodsAreaSelector.getStore().load({params:{'goodsAreaVo.goodsAreaEntity.goodsAreaName' : exceptiongoods.nolabelgoods.editRec.get('goodsAreaName')}});
					 
					var findUserSelector = editForm.findField('findUserCode');
					findUserSelector.getStore().load({params:{'employeeVo.employeeDetail.empName' : exceptiongoods.nolabelgoods.editRec.get('findUserName')}});
						 
			 	}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			//text: '保存',
			text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				
				/*Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File1_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File2_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File3_ID').allowBlank = blankFlag;*/
				
				var form = exceptiongoods.nolabelgoods.noLableGoodsEditForm.getForm();
				
				//校验货区和交接单号
				var handoverNo = form.findField('handoverNo').getValue();
				var goodsAreaCode = form.findField('goodsAreaCode').getValue();
				/*if(Ext.isEmpty(handoverNo) && Ext.isEmpty(goodsAreaCode)){
					//Ext.MessageBox.alert("提示", "交接单号和货区不能全部为空！");
					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.handoverno.goodsarea.notnull'));
					return;
				}*/
				//设置上一部门名称
				var previousOrgCode = form.findField('previousOrgCode').getValue();
				if(!Ext.isEmpty(previousOrgCode)){
					var previousOrgNameRecord = form.findField('previousOrgCode').findRecordByValue(previousOrgCode);
					form.findField('previousOrgName').setValue(previousOrgNameRecord.data.name);
				}
				
				//设置货区名称
				if(!Ext.isEmpty(goodsAreaCode)){
					var goodsAreaNameRecord = form.findField('goodsAreaCode').findRecordByValue(goodsAreaCode);
					form.findField('goodsAreaName').setValue(goodsAreaNameRecord.data.goodsAreaName);
				}
				
				//设置发现人
				var findUserCode = form.findField('findUserCode').getValue();
				if(!Ext.isEmpty(findUserCode)){
					var findUserNameRecord = form.findField('findUserCode').findRecordByValue(findUserCode);
					form.findField('findUserName').setValue(findUserNameRecord.data.empName);
				}
				
				//设置当前用户大部门
				form.findField('findOrgCode').setValue(exceptiongoods.nolabelgoods.bigOrgCode);
				form.findField('findOrgName').setValue(exceptiongoods.nolabelgoods.bigOrgName);
				
				var formParams = form.getValues();
				
				if(form.isValid()){
	                form.submit({
	                	url: exceptiongoods.realPath('addNoLabelGoods.action'),
	                    params: {
						        'noLabelGoodsVo.noLabelGoodsEntity.id' : formParams.id,
	                    		'noLabelGoodsVo.noLabelGoodsEntity.noLabelBillNo' : formParams.noLabelBillNo,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsName' : formParams.goodsName,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsBrand' : formParams.goodsBrand,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsType' : formParams.goodsType,
								'noLabelGoodsVo.noLabelGoodsEntity.packageType' : formParams.packageType,
								'noLabelGoodsVo.noLabelGoodsEntity.packageKeyword' : formParams.packageKeyword,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsProperty' : formParams.goodsProperty,
								'noLabelGoodsVo.noLabelGoodsEntity.handwritingKeyword' : formParams.handwritingKeyword,
								'noLabelGoodsVo.noLabelGoodsEntity.weight' : formParams.weight,
								'noLabelGoodsVo.noLabelGoodsEntity.volumeLWH' : formParams.volumeLWH,
								'noLabelGoodsVo.noLabelGoodsEntity.volume' : formParams.volume,
								'noLabelGoodsVo.noLabelGoodsEntity.lossGoodsNotes' : formParams.lossGoodsNotes,
								'noLabelGoodsVo.noLabelGoodsEntity.previousOrgCode' : formParams.previousOrgCode,
								'noLabelGoodsVo.noLabelGoodsEntity.previousOrgName' : formParams.previousOrgName,                       
								'noLabelGoodsVo.noLabelGoodsEntity.goodsAreaCode' : formParams.goodsAreaCode,                  
								'noLabelGoodsVo.noLabelGoodsEntity.goodsAreaName' : formParams.goodsAreaName,                        
								'noLabelGoodsVo.noLabelGoodsEntity.findTime' : formParams.findTime,                           
								'noLabelGoodsVo.noLabelGoodsEntity.findType' : formParams.findType,                           
								'noLabelGoodsVo.noLabelGoodsEntity.findUserCode' : formParams.findUserCode,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findUserName' : formParams.findUserName,                          
								'noLabelGoodsVo.noLabelGoodsEntity.vehicleNo' : formParams.vehicleNo,                           
								'noLabelGoodsVo.noLabelGoodsEntity.goodsQty' :  formParams.goodsQty,                        
								'noLabelGoodsVo.noLabelGoodsEntity.handoverNo' : formParams.handoverNo,                           
								'noLabelGoodsVo.noLabelGoodsEntity.noteNotifyPerson' : formParams.noteNotifyPerson, 
								'noLabelGoodsVo.noLabelGoodsEntity.leakGoods' : formParams.leakGoods, 
								'noLabelGoodsVo.noLabelGoodsEntity.expressGoods' : formParams.expressGoods, 
								'noLabelGoodsVo.noLabelGoodsEntity.eventProcess' : formParams.eventProcess,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findOrgCode' : formParams.findOrgCode,                          
								'noLabelGoodsVo.noLabelGoodsEntity.findOrgName' : formParams.findOrgName,
								
								'noLabelGoodsVo.noLabelGoodsEntity.frontPhotoName' : formParams.frontPhotoName,
								'noLabelGoodsVo.noLabelGoodsEntity.entiretyPhotoName' : formParams.entiretyPhotoName,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsPhotoName' : formParams.goodsPhotoName,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsPhotoAName' : formParams.goodsPhotoAName,
								'noLabelGoodsVo.noLabelGoodsEntity.goodsPhotoBName' : formParams.goodsPhotoBName
								
						    },
	                	success: function(form, action) {
	                        //Ext.ux.Toast.msg('提示', '保存成功！', 'ok', 3000);
	                        Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'),  exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save.success'), 'ok', 3000);
	                        exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
	                        var editWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getEditWindow();
	                        editWindow.close();
	                    },
	                    failure: function(form, action) {
					        var result = Ext.decode(response.responseText);
		    				//Ext.ux.Toast.msg('保存失败', result.message, 'error', 3000);
		    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save.failure'), result.message, 'error', 3000);
					    }
	                });
	            }
				
			}
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//无标签货物转弃货窗口
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLableGoodsToAbandonWindow', {
	extend: 'Ext.window.Window',
	title: '无标签转弃货申请',
	//title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.edit.nolabelgoods'),
	modal:true,
	closeAction: 'hide',
	width: 600,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var NoLableGoodsToAbandonForm = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm'); 
		exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm = NoLableGoodsToAbandonForm;
		
		me.items = [
		    NoLableGoodsToAbandonForm
		];
		me.callParent([cfg]);
	}
});


//无标签货物转弃货表单
Ext.define('Foss.exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:2
	},
	items: [{
		xtype: 'textfield',
		name: 'id',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '无标签运单号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
		name: 'noLabelBillNo',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '无标签流水号',
		fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelserialno'),
		name: 'noLabelSerialNo',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textfield',
		fieldLabel: '申请人',		
		name: 'userName',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '所属区域',
		name: 'bigOrgName',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textfield',
		fieldLabel: '品名',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsType'),
		name: 'goodsName',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '仓储部门',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'findOrgName',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textfield',
		fieldLabel: '重量',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsType'),
		name: 'weight',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '体积',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'volume',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textfield',
		fieldLabel: '件数',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsType'),
		name: 'goodsQty',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '上报oa时长(天)',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'toOaTime',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textfield',
		fieldLabel: 'oa差错编号',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsType'),
		name: 'oaErrorNo',
		readOnly: true,
		columnWidth:.4
	},{
		xtype: 'container',
		columnWidth:.04,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '处理类型',
		//fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
		name: 'handleType',
		columnWidth:.4,
		readOnly: true
	},{
		xtype: 'textarea',
		fieldLabel: '申请事由(小于400字)',
		autoScroll:true,
		name: 'toAbandonNotes',
		maxLength: 400,
		columnWidth:1,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
	},{
		xtype: 'textfield',
		fieldLabel: '发现部门编号',
		name: 'findOrgCode',
		hidden: true
	},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: '关闭',
				columnWidth:.08,
				handler: function(){
					 var noLabelToAbandonWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getNoLabelToAbandonWindow();
					 noLabelToAbandonWindow.close();
			 	}
		},{
			xtype: 'container',
			columnWidth:.74,
			html: '&nbsp;'
		},{
			//text: '保存',
			text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				
				/*Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File1_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File2_ID').allowBlank = blankFlag;
				Ext.getCmp('Foss_exceptiongoods_nolabelgoods_noLableGoodsForm_File3_ID').allowBlank = blankFlag;*/
				
				var form = exceptiongoods.nolabelgoods.NoLableGoodsToAbandonForm.getForm();
				
				var formParams = form.getValues();
				
				var tempNotes = formParams.toAbandonNotes;
				if(tempNotes.length ==0){
					Ext.ux.Toast.msg('提示', '申请事由不能为空', 'error', 3000);
					return ;
				}
				if(tempNotes.length>400){
					Ext.ux.Toast.msg('提示', '申请事由不能超过400字', 'error', 3000);
					return ;
				}
				
				Ext.Msg.wait('处理中，请稍候...', '提示'); //进度条等待  
				if(form.isValid()){
					
					var jsonParam = {noLabelGoodsVo: {noLabelGoodsEntity:{
						id:formParams.id,
						noLabelBillNo:formParams.noLabelBillNo,
						noLabelSerialNo:formParams.noLabelSerialNo
					},toAbandonNotes:formParams.toAbandonNotes}};
					
					Ext.Ajax.request({
						url: exceptiongoods.realPath('noLableGoodsToAbandon.action'),
						jsonData:jsonParam,
						success:function(response){
							Ext.Msg.hide()
	                        //Ext.ux.Toast.msg('提示', '保存成功！', 'ok', 3000);
	                        Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'),  '无标签转弃货申请成功', 'ok', 3000);
	                        exceptiongoods.nolabelgoods.nolabelgoodsGrid.store.load();
	                        var editWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getNoLabelToAbandonWindow();
	                        editWindow.close();
	                    },
	                    exception:function(response){
	                    	Ext.Msg.hide()
	                    	var result = Ext.decode(response.responseText);
		    				Ext.ux.Toast.msg('提示', '无标签转弃货申请失败', 'error', 3000);
		    				//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.save.failure'), result.message, 'error', 3000);
		    			}
	                });
	            }
				
			}
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//新增无标签多货窗口
Ext.define('Foss.exceptiongoods.nolabelgoods.AddWindow', {
	extend: 'Ext.window.Window',
	//title: '录入无标签货物',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.input.nolabelgoods'),
	modal:true,
	closeAction: 'hide',
	width: 1000,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var noLableGoodsForm = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLableGoodsForm'); 
		exceptiongoods.nolabelgoods.noLableGoodsForm = noLableGoodsForm;
		exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('findUserCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('goodsAreaCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().findField('noteNotifyPerson').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		
		me.items = [
		    noLableGoodsForm
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			exceptiongoods.nolabelgoods.setAllowBlank(true);
			exceptiongoods.nolabelgoods.noLableGoodsForm.getForm().reset();
		}
	}
});

//编辑无标签多货窗口
Ext.define('Foss.exceptiongoods.nolabelgoods.EditWindow', {
	extend: 'Ext.window.Window',
	//title: '修改无标签货物',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.edit.nolabelgoods'),
	modal:true,
	closeAction: 'hide',
	width: 1000,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var noLableGoodsEditForm = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLableGoodsEditForm'); 
		noLableGoodsEditForm.getForm().findField('findUserCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		noLableGoodsEditForm.getForm().findField('goodsAreaCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		noLableGoodsEditForm.getForm().findField('noteNotifyPerson').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
		exceptiongoods.nolabelgoods.noLableGoodsEditForm = noLableGoodsEditForm;
		
		me.items = [
		    noLableGoodsEditForm
		];
		me.callParent([cfg]);
	}
});

exceptiongoods.nolabelgoods.setAllowBlank = function(blankFlag){
	var form = exceptiongoods.nolabelgoods.noLableGoodsForm.getForm();
	form.findField('goodsName').allowBlank = blankFlag;
	form.findField('goodsType').allowBlank = blankFlag;
	form.findField('goodsBrand').allowBlank = blankFlag;
	form.findField('packageType').allowBlank = blankFlag;
	//form.findField('packageKeyword').allowBlank = blankFlag;
	form.findField('handwritingKeyword').allowBlank = blankFlag;
	//form.findField('goodsProperty').allowBlank = blankFlag;
	form.findField('weight').allowBlank = blankFlag;
	form.findField('volumeLWH').allowBlank = blankFlag;
	form.findField('findTime').allowBlank = blankFlag;
	form.findField('findType').allowBlank = blankFlag;
	form.findField('goodsQty').allowBlank = blankFlag;
	form.findField('leakGoods').allowBlank = blankFlag;
	form.findField('eventProcess').allowBlank = blankFlag;
}

//无标签多货明细表单
Ext.define('Foss.exceptiongoods.nolabelgoods.DetailForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [
	{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		//title: '录入信息',
		title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.input.info'),
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '无标签运单号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelbillno'),
			name: 'noLabelBillNo',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '上一环节部门',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.previous.org'),
			name: 'previousOrgName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '车次号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.vehicleno'),
			name: 'vehicleNo',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			//fieldLabel : '无标签发现类型',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabel.discover.type'),
			name: 'findType',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '发现货区',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goodsarea'),
			name: 'goodsAreaName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '操作人',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operator'),
			name: 'createUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			//fieldLabel: '操作时间',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operatetime'),
			name: 'createDate',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '品名',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsname'),
			name: 'goodsName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel : '包装',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.package'),
			name: 'packageType',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			//fieldLabel: '件数',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodsqty'),
			name: 'goodsQty',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '重量(公斤)',
		    fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.weight'),
			name: 'weight',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '体积(方)',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.volume'),
			name: 'volume',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textarea',
			hidden: true,
			//fieldLabel: '同车少货备注',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.lossgoods.notes'),
			autoScroll:true,
			name: 'lossGoodsNotes',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:1
		} 
		]
	},{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		//title: 'OA上报信息',
		title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa.info'),
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '上报人',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.person'),
			name: 'reportOAUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '发现人',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.person'),
			name: 'findUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '上报时间',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa.time'),
			name: 'reportOATime',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			hidden: true,
			//fieldLabel : 'OA上报状态',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.report.oa.status'),
			name: 'isReportOa',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},/*{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},*/{
			xtype: 'textfield',
			//fieldLabel: 'OA差错编号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.oa.error.no'),
			name: 'oaErrorNo',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel : '找货状态',
			fieldLabel : exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.find.goods.status'),
			name: 'findGoodsStatus',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '关联原运单号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.waybillno'),
			name: 'originalWaybillNo',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},/*{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},*/{
			xtype: 'textfield',
			//fieldLabel: '关联原流水号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.serialno'),
			name: 'originalSerialNo',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.35,
			html: '&nbsp;'
		}
		]
	},{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		//title: '标签打印信息',
		title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.label.print.info'),
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '无标签',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabel'),
			name: 'isPrintNoLabel',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '操作人',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operator'),
			name: 'printUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '打印时间',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.time'),
			name: 'printTime',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			//fieldLabel: '原标签',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.label'),
			name: 'isPrintOriginalLabel',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '操作人',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operator'),
			name: 'printOriginalLabelUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '打印时间',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.time'),
			name: 'printOriginalLabelTime',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		}
		]
	},{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		//title: '异常货区库存信息',
		title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.stock.info'),
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			hidden: true,
			//fieldLabel: '库存状态',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.stock.status'),
			name: 'stockStatus',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},/*{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},*/{
			xtype: 'textfield',
			//fieldLabel: '入库时间',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.instocktime'),
			name: 'inStockTime',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '入库方式',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.instocktype'),
			name: 'inStockDeviceType',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'textfield',
			//fieldLabel: '操作人',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.operator'),
			name: 'inStockUserName',
			readOnly: true,
			cls:'readonlyhaveborder', 
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.7,
			html: '&nbsp;'
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//无标签多货明细
Ext.define('Foss.exceptiongoods.nolabelgoods.DetailWindow', {
	extend: 'Ext.window.Window',
	//title: '无标签货物明细',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.nolabelgoods.detail'),
	modal:true,
	closeAction: 'hide',
	width: 1000,
	bindData : function(record, cellIndex, rowIndex) {
		exceptiongoods.nolabelgoods.detailForm.getForm().loadRecord(record);
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('createDate').setValue(Ext.Date.format(record.data.createDate,'Y-m-d H:i:s'));
	
		var findType = FossDataDictionary.rendererSubmitToDisplay(record.data.findType,'NO_LABEL_GOODS_DISCOVER_STATE');
		var packageType = FossDataDictionary.rendererSubmitToDisplay(record.data.packageType,'NO_LABEL_GOODS_PACKAGE_TYPE');
		var findGoodsStatus = FossDataDictionary.rendererSubmitToDisplay(record.data.findGoodsStatus,'NO_LABEL_GOODS_FIND_STATE');
		var stockStatus = FossDataDictionary.rendererSubmitToDisplay(record.data.stockStatus,'NO_LABEL_GOODS_STOCK_STATE');
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('findType').setValue(findType);
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('packageType').setValue(packageType);
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('findGoodsStatus').setValue(findGoodsStatus);
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('stockStatus').setValue(stockStatus);
		
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('volume').setValue(record.data.volume+"(" + record.data.volumeLWH + ")");
		
		/*exceptiongoods.nolabelgoods.detailForm.getForm().findField('inStockTime').setValue(Ext.Date.format(record.data.inStockTime,'Y-m-d H:i:s'));*/
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('inStockTime').setValue((record.data.inStockTime==null)?'':record.data.inStockTime.substring(0,record.data.inStockTime.lastIndexOf('.')));
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('reportOATime').setValue(Ext.Date.format(record.data.reportOATime,'Y-m-d H:i:s'));
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('printTime').setValue(Ext.Date.format(record.data.printTime,'Y-m-d H:i:s'));
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('printOriginalLabelTime').setValue(Ext.Date.format(record.data.printOriginalLabelTime,'Y-m-d H:i:s'));
		
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('isReportOa').setValue(record.data.isReportOa == 'Y'?'已上报':'未上报');
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('isPrintNoLabel').setValue(record.data.isPrintNoLabel == 'Y'?'已打印':'未打印');
		exceptiongoods.nolabelgoods.detailForm.getForm().findField('isPrintOriginalLabel').setValue(record.data.isPrintOriginalLabel == 'Y'?'已打印':'未打印');
		
		if(record.data.stockStatus == 'IN_STOCK'){
			exceptiongoods.nolabelgoods.detailForm.getForm().findField('inStockDeviceType').setValue(record.data.inStockDeviceType == 'PC'?'手动登入':'PDA扫描登入');
		} 
		
		if(record.data.stockStatus == 'OUT_STOCK'){
			exceptiongoods.nolabelgoods.detailForm.getForm().findField('inStockDeviceType').setValue(record.data.inStockDeviceType == 'PC'?'手动登出':'PDA扫描登出');
		}
		
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var detailForm = Ext.create('Foss.exceptiongoods.nolabelgoods.DetailForm'); 
		exceptiongoods.nolabelgoods.detailForm = detailForm;
		me.items = [
		    detailForm
		];
		me.callParent([cfg]);
	}
});
//打印指定标签表单
Ext.define('Foss.exceptiongoods.nolabelgoods.PrintAppointedForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [
	{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '运单号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.waybill'),
			name: 'waybillNo',
			columnWidth:.3,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '流水号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.serialno'),
			name: 'serialNo',
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.35,
			html: '&nbsp;'
		},{
			xtype:'fieldcontainer',
			fieldLabel: '',
			columnWidth:.3,
			defaultType: 'checkboxfield',
			items:[{
			       boxLabel:'标签显示"德邦物流"',
			       name:'isPrintLogo',
			       inputValue:'Y',
			       checked: true,
			       uncheckedValue:'N'
			}]
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.08,
				handler: function(){
					exceptiongoods.nolabelgoods.printAppointedForm.getForm().reset();
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				//text: '查询',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.query'),
				columnWidth:.08,
				cls : 'yellow_button',
				handler: function() {
					if(exceptiongoods.nolabelgoods.printAppointedForm.getForm().isValid()){
						//var queryParams = exceptiongoods.nolabelgoods.printAppointedForm.getValues();
							//判读是否电子运单
//							Ext.Ajax.request({
//							url: exceptiongoods.realPath('checkWallbillElectron.action'),
//							params:{'noLabelGoodsVo.printLabelDto.waybillNo': queryParams.waybillNo},
//							success:function(response){
//								var result = Ext.decode(response.responseText);
//								if(result.noLabelGoodsVo.wallbillElectron) {
//									//是电子运单
//									if(result.noLabelGoodsVo.wallbillElectron=='Y'){
//										Ext.MessageBox.alert("提示", "电子运单，不能在此打印");
//									}else{
//										exceptiongoods.nolabelgoods.printAppointedGrid.store.load();
//									}
//								}
//							},
//							exception : function(response) {
//			    				var result = Ext.decode(response.responseText);
//			    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
//			    				return false;
//			    			}
//						});
						exceptiongoods.nolabelgoods.printAppointedGrid.store.load();
					}
					
				}
			}]
	}]
	}],	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//打印指定标签Model
Ext.define('Foss.exceptiongoods.nolabelgoods.PrintAppointedModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo' , type: 'string'},
		{name: 'serialNo' , type: 'string'},
		{name: 'originalSerialNo' , type: 'string'},
		{name: 'labelGoodsQty', type: 'string'},
		{name: 'goodsQtyChanged', type: 'string'},  
		{name: 'goodsPosition', type: 'string'},
		{name: 'printCount', type: 'string'}
	]
});

//打印指定标签Store
Ext.define('Foss.exceptiongoods.nolabelgoods.PrintAppointedStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.exceptiongoods.nolabelgoods.PrintAppointedModel',
	autoLoad: false,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'post',
        url: exceptiongoods.realPath('queryPrintLabelInfo.action'),
		reader : {
			type : 'json',
			root : 'noLabelGoodsVo.printLabelDtoList',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				
				var queryParams = exceptiongoods.nolabelgoods.printAppointedForm.getValues();
					
				Ext.apply(operation, {
					params : {
						'noLabelGoodsVo.printLabelDto.waybillNo' : queryParams.waybillNo,
						'noLabelGoodsVo.printLabelDto.serialNo' : queryParams.serialNo
					}
				});	
		},
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//总件数
			var goodsQty = totalArray.length;
			exceptiongoods.nolabelgoods.printAppointedGrid.dockedItems.items[1].items.items[0].setValue(goodsQty);
			//Ext.getCmp('Foss_exceptiongoods_nolabelgoods_PrintAppointedGrid_Toolbar_GoodsQty_ID').setValue(goodsQty);
		}
	}
	
});
//打印指定标签表格
Ext.define('Foss.exceptiongoods.nolabelgoods.PrintAppointedGrid', {
	extend:'Ext.grid.Panel',
	//指定表格的高度
	height: 400,
	//指定表格的宽度
    //width:780,
	autoScroll:true,
	//增加表格列的分割线
	columnLines: true,
	//表格对象增加一个边框
    frame: true,
	columns: [{
			//header: '运单号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.waybill'),
			dataIndex: 'waybillNo'
		},{ 
			//header: '流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.serialno'),
			dataIndex: 'serialNo'
		},{ 
			//header: '关联原流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.serialno'),
			dataIndex: 'originalSerialNo'
		},{ 
			//header: '条码标签显示件数', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.label.goodsqty'),
			dataIndex: 'labelGoodsQty'
		},{ 
			//header: '件数变动事项', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.change.items'), 
			dataIndex: 'goodsQtyChanged'
		},{ 
			//header: '货物位置', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.position'), 
			dataIndex: 'goodsPosition'
		},{ 
			//header: '打印次数', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.count'), 
			dataIndex: 'printCount'
		}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总件数',
			   fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstotal'),
			   //id:'Foss_exceptiongoods_nolabelgoods_PrintAppointedGrid_Toolbar_GoodsQty_ID',
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'waybillTotal'
		   }]
		}],	
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			
			me.store = Ext.create('Foss.exceptiongoods.nolabelgoods.PrintAppointedStore');
			
			me.tbar = [{
					xtype: 'button',
					//text: '打印条码标签',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.bar.label'),
					gridContainer: this,
					handler: function() {
						
						var verifyUserWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getVerifyUserWindow();
						exceptiongoods.nolabelgoods.verifyUserForm.getForm().findField('userCode').allowBlank = false;
						exceptiongoods.nolabelgoods.verifyUserForm.getForm().findField('password').allowBlank = false;
						verifyUserWindow.show();
					}
				}];
			
			me.callParent([cfg]);
		}
});
//打印指定标签
Ext.define('Foss.exceptiongoods.nolabelgoods.PrintAppointedWindow', {
	extend: 'Ext.window.Window',
	//title: '打印指定标签',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.label'),
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var printAppointedForm = Ext.create('Foss.exceptiongoods.nolabelgoods.PrintAppointedForm'); 
		var printAppointedGrid = Ext.create('Foss.exceptiongoods.nolabelgoods.PrintAppointedGrid');
		exceptiongoods.nolabelgoods.printAppointedForm = printAppointedForm;
		exceptiongoods.nolabelgoods.printAppointedGrid = printAppointedGrid;
		me.items = [
		    printAppointedForm,printAppointedGrid
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			exceptiongoods.nolabelgoods.printAppointedForm.getForm().findField('waybillNo').allowBlank = true;
			exceptiongoods.nolabelgoods.printAppointedForm.getForm().reset();
			exceptiongoods.nolabelgoods.printAppointedGrid.store.removeAll();
		}
	}
});

//补打电子运单的表单
Ext.define('Foss.exceptiongoods.nolabelgoods.WallbillElectronForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [
	{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '运单号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.waybill'),
			name: 'waybillNo',
			columnWidth:.3,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '流水号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.serialno'),
			name: 'serialNo',
			columnWidth:.3
		},{
			xtype: 'container',
			columnWidth:.35,
			html: '&nbsp;'
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.08,
				handler: function(){
					exceptiongoods.nolabelgoods.wallbillElectronForm.getForm().reset();
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				//text: '查询',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.query'),
				columnWidth:.08,
				cls : 'yellow_button',
				handler: function() {
					if(exceptiongoods.nolabelgoods.wallbillElectronForm.getForm().isValid()){
						var queryParams = exceptiongoods.nolabelgoods.wallbillElectronForm.getValues();
						//判读是否电子运单
						Ext.Ajax.request({
						url: exceptiongoods.realPath('checkWallbillElectron.action'),
						params:{'noLabelGoodsVo.printLabelDto.waybillNo': queryParams.waybillNo},
						success:function(response){
							var result = Ext.decode(response.responseText);
							if(result.noLabelGoodsVo.wallbillElectron) {
								//是电子运单
								if(result.noLabelGoodsVo.wallbillElectron=='Y'){
									exceptiongoods.nolabelgoods.wallbillElectronGrid.store.load();
								}else{
									Ext.MessageBox.alert("提示", "非电子运单，不能在此打印");
								}
							}
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
		    				return false;
		    			}
					});
						
					}
					
				}
			}]
	}]
	}],	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//补打电子运单Model
Ext.define('Foss.exceptiongoods.nolabelgoods.WallbillElectronModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo' , type: 'string'},
		{name: 'serialNo' , type: 'string'},
		{name: 'originalSerialNo' , type: 'string'},
		{name: 'labelGoodsQty', type: 'string'},
		{name: 'goodsQtyChanged', type: 'string'},  
		{name: 'goodsPosition', type: 'string'},
		{name: 'printCount', type: 'string'}
	]
});

//补打电子运单的Store
Ext.define('Foss.exceptiongoods.nolabelgoods.WallbillElectronStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.exceptiongoods.nolabelgoods.WallbillElectronModel',
	autoLoad: false,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'post',
        url: exceptiongoods.realPath('queryPrintLabelInfo.action'),
		reader : {
			type : 'json',
			root : 'noLabelGoodsVo.printLabelDtoList',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
								  
				var queryParams = exceptiongoods.nolabelgoods.wallbillElectronForm.getValues();
					
				Ext.apply(operation, {
					params : {
						'noLabelGoodsVo.printLabelDto.waybillNo' : queryParams.waybillNo,
						'noLabelGoodsVo.printLabelDto.serialNo' : queryParams.serialNo,
						'noLabelGoodsVo.wallbillElectron' : 'Y'
					}
				});	
				
		},
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//总件数
			var goodsQty = totalArray.length;
			exceptiongoods.nolabelgoods.wallbillElectronGrid.dockedItems.items[1].items.items[0].setValue(goodsQty);
			//Ext.getCmp('Foss_exceptiongoods_nolabelgoods_PrintAppointedGrid_Toolbar_GoodsQty_ID').setValue(goodsQty);
		}
	}
	
});
//补打电子运单表格
Ext.define('Foss.exceptiongoods.nolabelgoods.WallbillElectronGrid', {
	extend:'Ext.grid.Panel',
	//指定表格的高度
	height: 400,
	//指定表格的宽度
    //width:780,
	autoScroll:true,
	//增加表格列的分割线
	columnLines: true,
	//表格对象增加一个边框
    frame: true,
	columns: [{
			//header: '运单号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.waybill'),
			dataIndex: 'waybillNo'
		},{ 
			//header: '流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.serialno'),
			dataIndex: 'serialNo'
		},{ 
			//header: '关联原流水号', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.original.serialno'),
			dataIndex: 'originalSerialNo'
		},{ 
			//header: '条码标签显示件数', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.label.goodsqty'),
			dataIndex: 'labelGoodsQty'
		},{ 
			//header: '件数变动事项', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.change.items'), 
			dataIndex: 'goodsQtyChanged'
		},{ 
			//header: '货物位置', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.position'), 
			dataIndex: 'goodsPosition'
		},{ 
			//header: '打印次数', 
			header: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.count'), 
			dataIndex: 'printCount'
		}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总件数',
			   fieldLabel:exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.goodstotal'),
			   //id:'Foss_exceptiongoods_nolabelgoods_PrintAppointedGrid_Toolbar_GoodsQty_ID',
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'waybillTotal'
		   }]
		}],	
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			
			me.store = Ext.create('Foss.exceptiongoods.nolabelgoods.WallbillElectronStore');
			
			me.tbar = [{
					xtype: 'button',
					//text: '打印条码标签',
					text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.bar.label'),
					gridContainer: this,
					handler: function() {
						
						var verifyUserWindow = Ext.getCmp('T_exceptiongoods-nolabelgoodsindex_content').getCheckUserWindow();
						exceptiongoods.nolabelgoods.checkUserForm.getForm().findField('userCode').allowBlank = false;
						exceptiongoods.nolabelgoods.checkUserForm.getForm().findField('password').allowBlank = false;
						verifyUserWindow.show();
					}
				}];
			
			me.callParent([cfg]);
		}
});
//补打电子运单
Ext.define('Foss.exceptiongoods.nolabelgoods.WallbillElectronWindow', {
	extend: 'Ext.window.Window',
	title: '补打电子运单',
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var WallbillElectronForm = Ext.create('Foss.exceptiongoods.nolabelgoods.WallbillElectronForm'); 
		var WallbillElectronGrid = Ext.create('Foss.exceptiongoods.nolabelgoods.WallbillElectronGrid');
		exceptiongoods.nolabelgoods.wallbillElectronForm = WallbillElectronForm;
		exceptiongoods.nolabelgoods.wallbillElectronGrid = WallbillElectronGrid;
		me.items = [
		    WallbillElectronForm,WallbillElectronGrid
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			exceptiongoods.nolabelgoods.wallbillElectronForm.getForm().findField('waybillNo').allowBlank = true;
			exceptiongoods.nolabelgoods.wallbillElectronForm.getForm().reset();
			exceptiongoods.nolabelgoods.wallbillElectronGrid.store.removeAll();
		}
	}
});

//验证用户名 密码 表单
Ext.define('Foss.exceptiongoods.nolabelgoods.VerifyUserForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [
	{
		border: 1,
		xtype:'container',
		columnWidth:1,
		title: '',
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '用户工号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.user.code'),
			name: 'userCode',
			columnWidth:.45,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:.1,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '密码',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.password'),
			name: 'password',
			inputType: 'password',
			columnWidth:.45,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:1,
			html: '&nbsp;'
		},{
			xtype: 'container',
			columnWidth:1,
			html: '&nbsp;'
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.2,
				handler: function(){
					exceptiongoods.nolabelgoods.verifyUserForm.getForm().reset();
				}
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				//text: '确认',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.confirm'),
				columnWidth:.2,
				cls : 'yellow_button',
				handler: function() {
					if(exceptiongoods.nolabelgoods.verifyUserForm.getForm().isValid()){
						 var formParams = exceptiongoods.nolabelgoods.verifyUserForm.getValues();
						 Ext.Ajax.request({
			    			url: exceptiongoods.realPath('verifyUser.action'),
			    			params : {
								'noLabelGoodsVo.userCode' : formParams.userCode,
								'noLabelGoodsVo.password' : formParams.password
							},
			    			success:function(response){
			    				var result = Ext.decode(response.responseText);
			    				var selectedRecords = exceptiongoods.nolabelgoods.printAppointedGrid.getSelectionModel().getSelection();
								if(selectedRecords.length < 1){
								 	//Ext.MessageBox.alert("提示", "请先勾选需要打印标签的数据");
								 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.print.label.data'));
									return;
								}
			    				if(result.noLabelGoodsVo.isVerify == 'Y'){
									 var serialNoList = new Array();
									 var waybillNo = selectedRecords[0].data.waybillNo;
									 for(var i in selectedRecords){
										serialNoList.push(selectedRecords[i].data.serialNo); 
									 }
									 var qty = 0;
									 var successQty = 0;
					    			 var failureQty = 0;
					    			 Ext.Msg.wait( '处理中，请稍候...' , '提示' ); //进度条等待  
//									 Ext.Array.each(serialNoList, function(item) {071677881
					    			 var itemNumber = serialNoList.length;
					    			 var pageSize = 200;
					    			 var page_size_index =200;
					    			 var pageCurrent = 1;
					    			 var pageNumber = 0;
					    			 if(itemNumber % pageSize == 0){
					    				 pageNumber = itemNumber/pageSize;
					    			 }else{
					    				 pageNumber = Math.floor(itemNumber/pageSize) +1;
					    			 }
					    			 
					    			 var serialNoOneList = new Array();
					    			 for(var k in serialNoList){
					    				 var kItemY = itemNumber-k;//剩余的记录数
					    				 page_size_index --;
					    				 serialNoOneList.push(serialNoList[k]);
					    				 if(kItemY<=pageSize){
					    					 //不足page_size_index的个数
					    					 if((kItemY-1)==0){
					    						//向后台请求一页的数据
												 var jsonParam = {noLabelGoodsVo: {serialNoList:serialNoOneList,printLabelDto: {waybillNo:waybillNo},userCode:formParams.userCode}};
												 var vurl = "http://localhost:8077/print";
												 Ext.Ajax.request({
									    			url: exceptiongoods.realPath('printAppointedLabel.action'),
									    			jsonData:jsonParam,
									    			timeout:300000*10,
									    			success:function(response){
									    				var result = Ext.decode(response.responseText);
									    				var barcodePrintDtoList = result.noLabelGoodsVo.barcodePrintDtoList;
														for(var i=0;i<barcodePrintDtoList.length;i++){
														var printData = barcodePrintDtoList[i];
														//根据运单类型判断打印类型
														var printType = "CommLabelPrintWorker";
														if(
															null != printData.waybillBean.productCode 
															&& (printData.waybillBean.productCode == 'PACKAGE' 
																|| printData.waybillBean.productCode == 'RCP'
																||printData.waybillBean.productCode == 'EPEP'
																||printData.waybillBean.productCode == 'DEAP'
																	)
															
														){
															//快递打印模板
															printType = "ExpCommLabelPrintWorker";
														}
									    				Ext.data.JsonP.request({
													        url: vurl,
													        callbackKey: 'callback',
														    params: {
														     lblprtworker:printType,
														  	 addr1: printData.addr1,
															 addr2: printData.addr2,
															 addr3: printData.addr3,
															 addr4: printData.addr4,
															 location1: printData.location1,
															 location2: printData.location2,
															 location3: printData.location3,
															 location4: printData.location4,
															 countyRegion:printData.countyRegion,
															 optusernum: formParams.userCode,
															 number: printData.waybillNumber,
															 serialnos: printData.printSerialnos,
															 leavecity: printData.leavecity,
															 destination: printData.destination,
															 isagent: printData.isAgent,
															 stationnumber: printData.destinationCode,
															 deptno: printData.lastTransCenterNo,
															 finaloutfieldid: printData.finaloutfieldid,
															 finaloutname: printData.lastTransCenterCity,
															 weight: printData.weight,
															 totalpieces: printData.totalPieces,
															 packing: printData.packing,
															 unusual: printData.unusual,
															 transtype: printData.transtype,
															 printdate: printData.printDate,
															 deliver: printData.deliverToDoor,
															 goodstype: printData.goodstype,
															 preassembly: printData.preassembly,
															 signFlag: printData.isStarFlag,
															 deliveryBigCustomer:printData.deliveryBigCustomer,
															 isNoStop:printData.isNoStop,
															 isExhibitCargo :printData.isExhibitCargo,
															 isPrintLogo:exceptiongoods.nolabelgoods.printAppointedForm.getForm().findField('isPrintLogo').getValue()==true?'Y':'N',
															 secLoadOrgName:printData.secLoadOrgName,
															 isExhibitCargo :printData.isExhibitCargo,
															 productCode:printData.productCode,
															 isElecLtlWaybill: printData.isElecLtlWaybill,
															 otherPackage: printData.otherPackage,
															 lastFirstFinalOutName:printData.lastFirstTransCenterCity,
															 lastSecondFinalOutName:printData.lastSecondTransCenterCity,
															 lastThirdFinalOutName:printData.lastThirdTransCenterCity,
															 lastSecondFinaloutFieldId:printData.lastSecondFinaloutFieldId,
															 lastThirdFinaloutFieldId:printData.lastThirdFinaloutFieldId,
															 simpleLeaveCity:printData.simpleLeaveCity,
															 sortingResult:printData.sortingResult
														    },
															callback: function() {
																
															},   	    
														    success: function(result, request) {
														    	qty++;
														    	successQty ++;
														    	
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个' ,function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    				}
														    },
														    failure : function (result, request) {
														    	qty++;
														    	failureQty ++;
														    	//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
														    	//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.failure'), 'error', 3000);
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个',function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
											    				}
														    }
														    
														});
									    				//Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
									    				//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
														}
									    			},
									    			exception : function(response) {
									    				Ext.Msg.hide();
									    				var result = Ext.decode(response.responseText);
									    				//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
									    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
									    				return false;
									    			}
								    			});
												serialNoOneList = new Array();
												page_size_index =pageSize;
					    					 }
					    				 }else{
					    					 if(page_size_index==0){
					    						//向后台请求一页的数据
												 var jsonParam = {noLabelGoodsVo: {serialNoList:serialNoOneList,printLabelDto: {waybillNo:waybillNo},userCode:formParams.userCode}};
												 var vurl = "http://localhost:8077/print";
												 Ext.Ajax.request({
									    			url: exceptiongoods.realPath('printAppointedLabel.action'),
									    			jsonData:jsonParam,
									    			timeout:300000*10,
									    			success:function(response){
									    				var result = Ext.decode(response.responseText);
									    				var barcodePrintDtoList = result.noLabelGoodsVo.barcodePrintDtoList;
									    				for(var i=0;i<barcodePrintDtoList.length;i++){
														var printData = barcodePrintDtoList[i];
														var printType = "CommLabelPrintWorker";
														if(null != printData.waybillBean.productCode && (printData.waybillBean.productCode == 'PACKAGE' 
															|| printData.waybillBean.productCode == 'RCP'
														    ||printData.waybillBean.productCode == 'EPEP'
														    ||printData.waybillBean.productCode == 'DEAP') ){
															//快递打印模板
															printType = "ExpCommLabelPrintWorker";
														}
									    				Ext.data.JsonP.request({
													        url: vurl,
													        callbackKey: 'callback',
														    params: {
														     lblprtworker:printType,	
														  	 addr1: printData.addr1,
															 addr2: printData.addr2,
															 addr3: printData.addr3,
															 addr4: printData.addr4,
															 location1: printData.location1,
															 location2: printData.location2,
															 location3: printData.location3,
															 location4: printData.location4,
															 countyRegion:printData.countyRegion,
															 optusernum: formParams.userCode,
															 number: printData.waybillNumber,
															 serialnos: printData.printSerialnos,
															 leavecity: printData.leavecity,
															 destination: printData.destination,
															 isagent: printData.isAgent,
															 stationnumber: printData.destinationCode,
															 deptno: printData.lastTransCenterNo,
															 finaloutfieldid: printData.finaloutfieldid,
															 finaloutname: printData.lastTransCenterCity,
															 weight: printData.weight,
															 totalpieces: printData.totalPieces,
															 packing: printData.packing,
															 unusual: printData.unusual,
															 transtype: printData.transtype,
															 printdate: printData.printDate,
															 deliver: printData.deliverToDoor,
															 goodstype: printData.goodstype,
															 preassembly: printData.preassembly,
															 signFlag: printData.isStarFlag,
															 deliveryBigCustomer:printData.deliveryBigCustomer,
															 receiveBigCustomer:printData.receiveBigCustomer,
															 isNoStop:printData.isNoStop,
															 isPrintLogo:exceptiongoods.nolabelgoods.printAppointedForm.getForm().findField('isPrintLogo').getValue()==true?'Y':'N',
															 secLoadOrgName:printData.secLoadOrgName,
															 productCode:printData.productCode,
															 lastFirstFinalOutName:printData.lastFirstTransCenterCity,
															 lastSecondFinalOutName:printData.lastSecondTransCenterCity,
															 lastThirdFinalOutName:printData.lastThirdTransCenterCity,
															 lastSecondFinaloutFieldId:printData.lastSecondFinaloutFieldId,
															 lastThirdFinaloutFieldId:printData.lastThirdFinaloutFieldId,
															 simpleLeaveCity:printData.simpleLeaveCity,
															 sortingResult:printData.sortingResult
														    },
															callback: function() {
															},   	    
														    success: function(result, request) {
														    	qty++;
														    	successQty ++;
														    	
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个' ,function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    				}
														    },
														    failure : function (result, request) {
														    	qty++;
														    	failureQty ++;
														    	//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
														    	//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.failure'), 'error', 3000);
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个',function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
											    				}
														    }
														    
														});
									    				//Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
									    				//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
														}
									    			},
									    			exception : function(response) {
									    				Ext.Msg.hide();
									    				var result = Ext.decode(response.responseText);
									    				//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
									    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
									    				return false;
									    			}
								    			});
												serialNoOneList = new Array();
												page_size_index =pageSize;
					    					 }
					    				 }
					    			 }
									// });//循环
			    				}else{
			    					//Ext.ux.Toast.msg('错误', '用户验证失败', 'error', 3000);
			    					Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.verify.user.failure'), 'error', 3000);
			    				}
			    			}
						 })	
					}
					
				}
			}]
	}]
	}],	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//验证用户名 密码
Ext.define('Foss.exceptiongoods.nolabelgoods.VerifyUserWindow', {
	extend: 'Ext.window.Window',
	//title: '验证用户',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.verify.user'),
	modal:true,
	closeAction: 'hide',
	width: 500,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var verifyUserForm = Ext.create('Foss.exceptiongoods.nolabelgoods.VerifyUserForm'); 
		exceptiongoods.nolabelgoods.verifyUserForm = verifyUserForm;
		me.items = [
		    verifyUserForm
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			exceptiongoods.nolabelgoods.verifyUserForm.getForm().findField('userCode').allowBlank = true;
			exceptiongoods.nolabelgoods.verifyUserForm.getForm().findField('password').allowBlank = true;
			exceptiongoods.nolabelgoods.verifyUserForm.getForm().reset();
		}
	}
});


//验证用户名 密码 表单  补打电子运单
Ext.define('Foss.exceptiongoods.nolabelgoods.CheckUserForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [
	{
		border: 1,
		xtype:'container',
		columnWidth:1,
		title: '',
		layout:'column',
		items:[
		{
			xtype: 'textfield',
			//fieldLabel: '用户工号',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.user.code'),
			name: 'userCode',
			columnWidth:.45,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:.1,
			html: '&nbsp;'
		},{
			xtype: 'textfield',
			//fieldLabel: '密码',
			fieldLabel: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.password'),
			name: 'password',
			inputType: 'password',
			columnWidth:.45,
			allowBlank: false,
			//blankText:'字段不能为空'
        	blankText: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.notnull')
		},{
			xtype: 'container',
			columnWidth:1,
			html: '&nbsp;'
		},{
			xtype: 'container',
			columnWidth:1,
			html: '&nbsp;'
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				//text: '重置',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.reset'),
				columnWidth:.2,
				handler: function(){
					exceptiongoods.nolabelgoods.checkUserForm.getForm().reset();
				}
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				//text: '确认',
				text: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.confirm'),
				columnWidth:.2,
				cls : 'yellow_button',
				handler: function() {
					if(exceptiongoods.nolabelgoods.checkUserForm.getForm().isValid()){
						 var formParams = exceptiongoods.nolabelgoods.checkUserForm.getValues();
						 Ext.Ajax.request({
			    			url: exceptiongoods.realPath('verifyUser.action'),
			    			params : {
								'noLabelGoodsVo.userCode' : formParams.userCode,
								'noLabelGoodsVo.password' : formParams.password
							},
			    			success:function(response){
			    				var result = Ext.decode(response.responseText);
			    				var selectedRecords = exceptiongoods.nolabelgoods.wallbillElectronGrid.getSelectionModel().getSelection();
								if(selectedRecords.length < 1){
								 	//Ext.MessageBox.alert("提示", "请先勾选需要打印标签的数据");
								 	Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.select.print.label.data'));
									return;
								}
			    				if(result.noLabelGoodsVo.isVerify == 'Y'){
									 var serialNoList = new Array();
									 var waybillNo = selectedRecords[0].data.waybillNo;
									 for(var i in selectedRecords){
										serialNoList.push(selectedRecords[i].data.serialNo); 
									 }
									 var qty = 0;
									 var successQty = 0;
					    			 var failureQty = 0;
					    			 Ext.Msg.wait( '处理中，请稍候...' , '提示' ); //进度条等待  
//									 Ext.Array.each(serialNoList, function(item) {071677881
					    			 var itemNumber = serialNoList.length;
					    			 var pageSize = 200;
					    			 var page_size_index =200;
					    			 var pageCurrent = 1;
					    			 var pageNumber = 0;
					    			 if(itemNumber % pageSize == 0){
					    				 pageNumber = itemNumber/pageSize;
					    			 }else{
					    				 pageNumber = Math.floor(itemNumber/pageSize) +1;
					    			 }
					    			 
					    			 var serialNoOneList = new Array();
					    			 for(var k in serialNoList){
					    				 var kItemY = itemNumber-k;//剩余的记录数
					    				 page_size_index --;
					    				 serialNoOneList.push(serialNoList[k]);
					    				 if(kItemY<=pageSize){
					    					 //不足page_size_index的个数
					    					 if((kItemY-1)==0){
					    						//向后台请求一页的数据
												 var jsonParam = {noLabelGoodsVo: {serialNoList:serialNoOneList,printLabelDto: {waybillNo:waybillNo},userCode:formParams.userCode}};
												 var vurl = "http://localhost:8077/print";
												 Ext.Ajax.request({
									    			url: exceptiongoods.realPath('printWallbillElectron.action'),
									    			jsonData:jsonParam,
									    			timeout:300000*10,
									    			success:function(response){
									    				var result = Ext.decode(response.responseText);
									    				var wallbillElectronDtoList = result.noLabelGoodsVo.wallbillElectronList;
									    				if(!wallbillElectronDtoList){
									    					alert('打印查询内容为空');
									    				}
														for(var i=0;i<wallbillElectronDtoList.length;i++){
														var printData = wallbillElectronDtoList[i];
														//打印模板
														var printType = "";
														//打印流水号
														var printSerialNos = printData.printSerialNos;
														
														if(!Ext.isEmpty(printSerialNos)){
															printSerialNos = Ext.util.Format.number(printSerialNos,'0');
														}else{
															printSerialNos = 0;
														}
														//流水号是1的
														if(printSerialNos==1){
															printType = "EWaybillOwnStubCopyPrintWorker";
														}else{
															//流水号不是1的
															printType = "EWaybillReceiveStubCopyPrintWorker";
														}
									    				Ext.data.JsonP.request({
													        url: vurl,
													        callbackKey: 'callback',
														    params: {
														     optusernum: formParams.userCode,
														     lblprtworker:printType,
														     waybillNo: printData.waybillNo,
														     orderNo: printData.orderNo,
														     orderPaidMethod: printData.orderPaidMethod,
														     deliveryCustomerName: printData.deliveryCustomerName,
														     deliveryCustomerMobilephone: printData.deliveryCustomerMobilephone,
														     deliveryCustomerPhone: printData.deliveryCustomerPhone,
														     deliveryCustomerContact: printData.deliveryCustomerContact,
														     deliveryCustomerAddress: printData.deliveryCustomerAddress,
														     receiveCustomerName: printData.receiveCustomerName,
														     receiveCustomerMobilephone: printData.receiveCustomerMobilephone,
														     receiveCustomerPhone: printData.receiveCustomerPhone,
														     receiveCustomerContact: printData.receiveCustomerContact,
														     receiveCustomerAddress: printData.receiveCustomerAddress,
														     receiveOrgCode: printData.receiveOrgCode,
														     receiveOrgName: printData.receiveOrgName,
														     createOrgCode: printData.createOrgCode,
														     createOrgName: printData.createOrgName,
														     customerPickupOrgCode: printData.customerPickupOrgCode,
														     customerPickupOrgName: printData.customerPickupOrgName,
														     goodsName: printData.goodsName,
														     goodsQtyTotal: printData.goodsQtyTotal,
														     goodsVolumeTotal: printData.goodsVolumeTotal,
														     goodsWeightTotal: printData.goodsWeightTotal,
														     billWeight: printData.billWeight,
														     productCode: printData.productCode,
														     productName: printData.productName,
														     receiveMethod: printData.receiveMethod,
														     paidMethod: printData.paidMethod,
														     totalFee: printData.totalFee,
														     transportFee: printData.transportFee,
														     goodsTypeCode: printData.goodsTypeCode,
														     goodsTypeName: printData.goodsTypeName,
														     preciousGoods: printData.preciousGoods,
														     goodsPackage: printData.goodsPackage,
														     insuranceAmount: printData.insuranceAmount,
														     packageFee: printData.packageFee,
														     returnBillType: printData.returnBillType,
														     codAmount: printData.codAmount,
														     accountNo: printData.accountNo,
														     deliverMan: printData.deliverMan,
														     receiveDate: printData.receiveDate,
														     receiverMan: printData.receiverMan,
														     deliverDate: printData.deliverDate,
														     secondOutfieldCode: printData.secondOutfieldCode,
														     secondOutfieldName: printData.secondOutfieldName,
														     lastLoadOrgCode: printData.lastLoadOrgCode,
														     lastLoadOrgName: printData.lastLoadOrgName,
														     leavecity: printData.leavecity,
														     outerField1: printData.outerField1,
														     outerField2: printData.outerField2,
														     outerField3: printData.outerField3,
														     outerField4: printData.outerField4,
														     outerField5: printData.outerField5,
														     outerField6: printData.outerField6,
														     location1: printData.location1,
														     location2: printData.location2,
														     location3: printData.location3,
														     location4: printData.location4,
														     location5: printData.location5,
														     location6: printData.location6,
														     isPrintStar: printData.isPrintStar,
														     printSerialNos: printData.printSerialNos,
														     isPrintAt: printData.isPrintAt,
														     stationnumber: printData.stationnumber,
														     receiveBigCustomer: printData.receiveBigCustomer,
														     deliveryBigCustomer: printData.deliveryBigCustomer,
														     weixinAddr: printData.weixinAddr,
														     isDeliver:printData.isDeliver
														    },
															callback: function() {
																
															},   	    
														    success: function(result, request) {
														    	qty++;
														    	successQty ++;
														    	
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个' ,function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    					}
														    	
														    },
														    failure : function (result, request) {
														    	qty++;
														    	failureQty ++;
														    	//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
														    	//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.failure'), 'error', 3000);
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个',function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
											    				}
														    }
														    
														});
									    				//Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
									    				//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
														}
									    			},
									    			exception : function(response) {
									    				Ext.Msg.hide();
									    				var result = Ext.decode(response.responseText);
									    				//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
									    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
									    				return false;
									    			}
								    			});
												serialNoOneList = new Array();
												page_size_index =pageSize;
					    					 }
					    				 }else{
					    					 if(page_size_index==0){
					    						//向后台请求一页的数据
												 var jsonParam = {noLabelGoodsVo: {serialNoList:serialNoOneList,printLabelDto: {waybillNo:waybillNo},userCode:formParams.userCode}};
												 var vurl = "http://localhost:8077/print";
												 Ext.Ajax.request({
									    			url: exceptiongoods.realPath('printWallbillElectron.action'),
									    			jsonData:jsonParam,
									    			timeout:300000*10,
									    			success:function(response){
									    				var result = Ext.decode(response.responseText);
									    				var wallbillElectronDtoList = result.noLabelGoodsVo.wallbillElectronList;
									    				if(!wallbillElectronDtoList){
									    					alert('打印查询内容为空');
									    				}
									    				for(var i=0;i<wallbillElectronDtoList.length;i++){
														var printData = wallbillElectronDtoList[i];
														//打印模板
														var printType = "";
														//打印流水号
														var printSerialNos = printData.printSerialNos;
														if(!Ext.isEmpty(printSerialNos)){
															printSerialNos = Ext.util.Format.number(printSerialNos,'0');
														}else{
															printSerialNos = 0;
														}
														//流水号是1的
														if(printSerialNos==1){
															printType = "EWaybillOwnStubCopyPrintWorker";
														}else{
															//流水号不是1的
															printType = "EWaybillReceiveStubCopyPrintWorker";
														}
									    				Ext.data.JsonP.request({
													        url: vurl,
													        callbackKey: 'callback',
														    params: {
														    	optusernum: formParams.userCode,
															     lblprtworker:printType,
															     waybillNo: printData.waybillNo,
															     orderNo: printData.orderNo,
															     orderPaidMethod: printData.orderPaidMethod,
															     deliveryCustomerName: printData.deliveryCustomerName,
															     deliveryCustomerMobilephone: printData.deliveryCustomerMobilephone,
															     deliveryCustomerPhone: printData.deliveryCustomerPhone,
															     deliveryCustomerContact: printData.deliveryCustomerContact,
															     deliveryCustomerAddress: printData.deliveryCustomerAddress,
															     receiveCustomerName: printData.receiveCustomerName,
															     receiveCustomerMobilephone: printData.receiveCustomerMobilephone,
															     receiveCustomerPhone: printData.receiveCustomerPhone,
															     receiveCustomerContact: printData.receiveCustomerContact,
															     receiveCustomerAddress: printData.receiveCustomerAddress,
															     receiveOrgCode: printData.receiveOrgCode,
															     receiveOrgName: printData.receiveOrgName,
															     createOrgCode: printData.createOrgCode,
															     createOrgName: printData.createOrgName,
															     customerPickupOrgCode: printData.customerPickupOrgCode,
															     customerPickupOrgName: printData.customerPickupOrgName,
															     goodsName: printData.goodsName,
															     goodsQtyTotal: printData.goodsQtyTotal,
															     goodsVolumeTotal: printData.goodsVolumeTotal,
															     goodsWeightTotal: printData.goodsWeightTotal,
															     billWeight: printData.billWeight,
															     productCode: printData.productCode,
															     productName: printData.productName,
															     receiveMethod: printData.receiveMethod,
															     paidMethod: printData.paidMethod,
															     totalFee: printData.totalFee,
															     transportFee: printData.transportFee,
															     goodsTypeCode: printData.goodsTypeCode,
															     goodsTypeName: printData.goodsTypeName,
															     preciousGoods: printData.preciousGoods,
															     goodsPackage: printData.goodsPackage,
															     insuranceAmount: printData.insuranceAmount,
															     packageFee: printData.packageFee,
															     returnBillType: printData.returnBillType,
															     codAmount: printData.codAmount,
															     accountNo: printData.accountNo,
															     deliverMan: printData.deliverMan,
															     receiveDate: printData.receiveDate,
															     receiverMan: printData.receiverMan,
															     deliverDate: printData.deliverDate,
															     secondOutfieldCode: printData.secondOutfieldCode,
															     secondOutfieldName: printData.secondOutfieldName,
															     lastLoadOrgCode: printData.lastLoadOrgCode,
															     lastLoadOrgName: printData.lastLoadOrgName,
															     leavecity: printData.leavecity,
															     outerField1: printData.outerField1,
															     outerField2: printData.outerField2,
															     outerField3: printData.outerField3,
															     outerField4: printData.outerField4,
															     outerField5: printData.outerField5,
															     outerField6: printData.outerField6,
															     location1: printData.location1,
															     location2: printData.location2,
															     location3: printData.location3,
															     location4: printData.location4,
															     location5: printData.location5,
															     location6: printData.location6,
															     isPrintStar: printData.isPrintStar,
															     printSerialNos: printData.printSerialNos,
															     isPrintAt: printData.isPrintAt,
															     stationnumber: printData.stationnumber,
															     receiveBigCustomer: printData.receiveBigCustomer,
															     deliveryBigCustomer: printData.deliveryBigCustomer,
															     weixinAddr: printData.weixinAddr,
															     isDeliver:printData.isDeliver
														    },
															callback: function() {
															},   	    
														    success: function(result, request) {
														    	
														    		qty++;
															    	successQty ++;
															    	
															    	var qty = successQty+failureQty;
												    				if(qty == serialNoList.length){
												    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
												    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个' ,function(btn){
													    					if(btn == 'yes'){
													    						Ext.Msg.hide();
																			}else{
																				Ext.Msg.hide();
																			}
																		});
//												    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//																    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
												    				}
														    	
														    	
														    },
														    failure : function (result, request) {
														    	qty++;
														    	failureQty ++;
														    	//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
														    	//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.failure'), 'error', 3000);
														    	var qty = successQty+failureQty;
											    				if(qty == serialNoList.length){
											    					//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
//											    					Ext.MessageBox.alert(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), 
//															    			'打印成功：'+successQty+'个，失败：' + failureQty + '个');
											    					Ext.MessageBox.confirm(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), '打印成功：'+successQty+'个，失败：' + failureQty + '个',function(btn){
												    					if(btn == 'yes'){
												    						Ext.Msg.hide();
																		}else{
																			Ext.Msg.hide();
																		}
																	});
											    				}
														    }
														    
														});
									    				//Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
									    				//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.print.success'), 'ok', 3000);
														}
									    			},
									    			exception : function(response) {
									    				Ext.Msg.hide();
									    				var result = Ext.decode(response.responseText);
									    				//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
									    				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
									    				return false;
									    			}
								    			});
												serialNoOneList = new Array();
												page_size_index =pageSize;
					    					 }
					    				 }
					    			 }
									// });//循环
			    				}else{
			    					//Ext.ux.Toast.msg('错误', '用户验证失败', 'error', 3000);
			    					Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.verify.user.failure'), 'error', 3000);
			    				}
			    			}
						 })	
					}
					
				}
			}]
	}]
	}],	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//验证用户名 密码  补打电子运单
Ext.define('Foss.exceptiongoods.nolabelgoods.CheckUserWindow', {
	extend: 'Ext.window.Window',
	//title: '验证用户',
	title: exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.verify.user'),
	modal:true,
	closeAction: 'hide',
	width: 500,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var verifyUserForm = Ext.create('Foss.exceptiongoods.nolabelgoods.CheckUserForm'); 
		exceptiongoods.nolabelgoods.checkUserForm = verifyUserForm;
		me.items = [
		    verifyUserForm
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			exceptiongoods.nolabelgoods.checkUserForm.getForm().findField('userCode').allowBlank = true;
			exceptiongoods.nolabelgoods.checkUserForm.getForm().findField('password').allowBlank = true;
			exceptiongoods.nolabelgoods.checkUserForm.getForm().reset();
		}
	}
});


Ext.onReady(function() {
	
	Ext.Ajax.request({
		url: exceptiongoods.realPath('queryBigOrg.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			exceptiongoods.nolabelgoods.bigOrgCode = result.noLabelGoodsVo.bigOrgCode;
			exceptiongoods.nolabelgoods.bigOrgName = result.noLabelGoodsVo.bigOrgName;
			
			var queryform = Ext.create('Foss.exceptiongoods.nolabelgoods.QueryForm');
			exceptiongoods.nolabelgoods.queryform = queryform;
			
			exceptiongoods.nolabelgoods.queryform.getForm().findField('goodsAreaCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
			
			exceptiongoods.nolabelgoods.queryform.getForm().findField('findUserCode').deptCode = exceptiongoods.nolabelgoods.bigOrgCode;
			
			var nolabelgoodsGrid = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLabelGoodsGrid');
			exceptiongoods.nolabelgoods.nolabelgoodsGrid = nolabelgoodsGrid;
			Ext.create('Ext.panel.Panel',{
				id:'T_exceptiongoods-nolabelgoodsindex_content',
				cls:"panelContentNToolbar",
				bodyCls:'panelContent-body',
				addWindow:null,
				printAppointedWindow:null,
				verifyUserWindow:null,
				noLabelToAbandonWindow:null,
				wallbillElectronWindow:null,
				getAddWindow: function() {
					if(this.addWindow == null) {
						this.addWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.AddWindow');
					}
					return this.addWindow;
				},
				getEditWindow: function() {
					if(this.editWindow == null) {
						this.editWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.EditWindow');
					}
					return this.editWindow;
				},
				getPrintAppointedWindow: function() {
					if(this.printAppointedWindow == null) {
						this.printAppointedWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.PrintAppointedWindow');
					}
					return this.printAppointedWindow;
				}, 
				getVerifyUserWindow: function() {
					if(this.verifyUserWindow == null) {
						this.verifyUserWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.VerifyUserWindow');
					}
					return this.verifyUserWindow;
				},
				getCheckUserWindow: function() {
					if(this.verifyUserWindow == null) {
						this.verifyUserWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.CheckUserWindow');
					}
					return this.verifyUserWindow;
				},
				getNoLabelToAbandonWindow: function() {
					if(this.noLabelToAbandonWindow == null) {
						this.noLabelToAbandonWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.NoLableGoodsToAbandonWindow');
					}
					return this.noLabelToAbandonWindow;
				},
				getWallbillElectronToAbandonWindow: function() {
					if(this.wallbillElectronWindow == null) {
						this.wallbillElectronWindow = Ext.create('Foss.exceptiongoods.nolabelgoods.WallbillElectronWindow');
					}
					return this.wallbillElectronWindow;
				},
				items : [queryform,nolabelgoodsGrid],
				renderTo: 'T_exceptiongoods-nolabelgoodsindex-body'
			});
			
			
		},
		exception : function(response) {
				var result = Ext.decode(response.responseText);
				//Ext.ux.Toast.msg('错误', result.message, 'error', 3000);
				Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
			}
	});
	
	
	
	
});