predeliver.processAbandonGoods.PKP_ABANDONGOODS_STATUS = 'PKP_ABANDONGOODS_STATUS';
predeliver.processAbandonGoods.PKP_ABANDONGOODS_TYPE = 'PKP_ABANDONGOODS_TYPE';
/**
 * 
 * @param {}
 *            date--比较日期 day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.processAbandonGoods.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);// 天数增加
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);
	return t2;
};

/**
 * @param {}
 *            date--比较日期 day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.processAbandonGoods.getTargetDateEnd = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);// 天数增加
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);
	return t2;
};

predeliver.processAbandonGoods.abandonId = null;
/**
 * 获得当天的指定时间
 */
predeliver.processAbandonGoods.getTargetDateTime = function(date, hour, minute, second, millisecond) {
	var t2 = date;// date
	t2.setHours(hour);// hour
	t2.setMinutes(minute);// minute
	t2.setSeconds(second);// second
	t2.setMilliseconds(millisecond);// millisecond
	return t2;
};

// 定义model
Ext.define('Foss.predeliver.processAbandonGoods.GoodsModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',// 关联的名称
						type : 'string'
					}, {// 重量--运单
						name : 'goodsWeightTotal',// 关联的名称
						type : 'Number'
					}, {// 体积--运单
						name : 'goodsVolumeTotal',// 关联的名称
						type : 'Number'
					}, {// 总件数--运单
						name : 'goodsQtyTotal',// 关联的名称
						type : 'int'
					}, {// 运单号
						name : 'waybillNo',// 关联的名称
						type : 'string'
					},{// 流水号
						name : 'serialNumber',// 关联的名称
						type : 'String'
					}, {
						name : 'status',// 关联的名称
						type : 'string'
					},// 处理状态
					{
						name : 'createUserName',// 关联的名称
						type : 'string'
					},// 预弃货人
					{
						name : 'receiveCustomerName',// 关联的名称
						type : 'string'
					},// 收货人姓名
					{
						name : 'receiveCustomerPhone',// 关联的名称
						type : 'string'
					},// 收货人电话
					{
						name : 'preabandonedgoodsTime',convert:dateConvert
					}, // 预弃货时间
					{
						name : 'operateTime',convert:dateConvert
					}, // 操作时间
					{
						name : 'processId',
						type : 'string'
					},// 工作流号
					{
						name : 'startDraft',
						type : 'string'
					}
			]
		});
// 定义弃货详细信息模型
Ext.define('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'waybillNo',// 关联的名称
						type : 'string'
					},// 单号
					{
						name : 'goodsName',// 关联的名称
						type : 'string'
					}, // 货物名称
					{
						name : 'goodsWeightTotal',// 关联的名称
						type : 'float',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(千克)';
							} else {
								return null;
							}
						}
					}, // 重量
					{
						name : 'darftOrgName',// 关联的名称
						type : 'string'
					}, // 起草变更部门
					{
						name : 'goodsVolumeTotal',// 关联的名称
						type : 'float',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(立方米)';
							} else {
								return null;
							}
						}
					}, // 体积
					{
						name : 'deliveryCustomerContact',// 关联的名称
						type : 'string'
					}, // 发货人
					{
						name : 'deliveryCustomerPhone',// 关联的名称
						type : 'string'
					}, // 发货人电话
					{
						name : 'receiveOrgCode',// 关联的名称
						type : 'string'
					}, // 发货部门
					{
						name : 'receiveOrgName',// 关联的名称
						type : 'string'
					}, // 发货部门
					{
						name : 'respectiveRegional',// 关联的名称
						type : 'string'
					}, // 所属区域
					{
						name : 'respectiveRegionalName',// 关联的名称
						type : 'string'
					}, // 所属区域
					{
						name : 'receiveCustomerContact',// 关联的名称
						type : 'string'
					}, // 收货人
					{
						name : 'receiveCustomerPhone',// 关联的名称
						type : 'string'
					}, // 收货人电话
					{
						name : 'lastLoadOrgCode',// 关联的名称
						type : 'string'
					}, // 到达部门
					{
						name : 'lastLoadOrgName',// 关联的名称
						type : 'string'
					}, {
						name : 'lastStorageName',// 关联的名称
						type : 'string'
					}, {
						name : 'codAmount',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 代收金额
					{
						name : 'insuranceAmount',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 保险金额
					{
						name : 'prePayAmount',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 预付金额
					{
						name : 'toPayAmount',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 到付金额
					{
						name : 'abandonedgoodsType',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							return FossDataDictionary.rendererSubmitToDisplay(
									value, predeliver.processAbandonGoods.PKP_ABANDONGOODS_TYPE);

						}
					}, // 弃货类型
					{
						name : 'status',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							return FossDataDictionary.rendererSubmitToDisplay(
									value, predeliver.processAbandonGoods.PKP_ABANDONGOODS_STATUS);

						}

					}, // 处理状态
					{
						name : 'preabandonedgoodsTime',// 关联的名称
						type:'date', 
						convert:function(value){
							 if(value!=null){
								 var date = new Date(value);
								 return date;
							 }else{
								 return null;
							 }
						} 
					},// 入库时间
					{
						name : 'storageDay',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value != null) {
								return value + '(天)';
							} else {
								return null;
							}
						}
					}, // 仓储时长
					{
						name : 'createUserName',// 关联的名称
						type : 'string'
					}, // 预弃货人
					{
						name : 'notes',// 关联的名称
						type : 'string'
					}, // 弃货事由 
					{
						name : 'serialNumber',// 关联的名称
						type : 'String'
					}, // 流水号
					{
						name : 'errorNumber',// 关联的名称
						type : 'String'
					},// OA差错编号
					{
						name : 'customerCooperateStatus',// 关联的名称
						type : 'string',
						convert : function(value) {// convert类型转换器
							if (value == 'Y') {
								return '不配合';
							} else {
								return '配合';
							}
						}
					}
			]
		});
var detailWin = null;
// 定义签收变更数据store
Ext.define('Foss.predeliver.processAbandonGoods.ProcessAbandonGoodsStore', {
			extend : 'Ext.data.Store',
			autoLoad: predeliver.processAbandonGoods.byMsg==0?false:true,
			//autoLoad:true,
			model : 'Foss.predeliver.processAbandonGoods.GoodsModel',
			proxy : {
				// 代理的类型为内存代理
				type : 'ajax',
				url : predeliver.realPath('searchAbandonGoodsList.action'),// 老的url格式：
				// '../predeliver/searchAbandonGoodsList.action',
				headers : {// URL Encode
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'// UTF-8
				},
				actionMethods : 'POST',// post is necessary 如果不用post会造成中文乱码
				// 定义一个读取器
				reader : {
					// 以JSON的方式读取
					type : 'json',
					// 定义读取JSON数据的根对象
					root : 'abandonedGoodsSearchVo.abandonGoodsResultDtoList'

				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					if(predeliver.processAbandonGoods.byMsg>0){
						Ext.apply(operation, {	
							params:{
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.abandonedgoodsType' : 'ABANDGOODS_TYPE_AUTO',// 弃货类型
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.isByMsg' : 'Y' //通过在线提醒跳转查询的
								}
							});
					}
				},
				// load event 当选择中的时候被触发
				load : function(store, records, successful, operation, eOpts) {// 查询结果为空
					if (Ext.isEmpty(records)) {
						Ext.ux.Toast.msg('提示信息', '查询结果为空!');
					}
					predeliver.processAbandonGoods.byMsg=0;
				 	// 清除合计数据
					var items = predeliver.processAbandonGoods.processAbandonGoodsGrid.foolBar.items.items;
					// 总记录数
					items[0].setValue('');
					// 总重量
					items[1].setValue('');
					// 总体积
					items[2].setValue('');
				}
			}
		});

// 定义处理状态store

// 定义弃货类型store

// 查询条件
Ext.define('Foss.predeliver.processAbandonGoods.QueryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : predeliver.processAbandonGoods
			.i18n('pkp.predeliver.processAbandonGoods.searchCondition'),// 标题
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'textfield',
				columnWidth : 0.25,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.waybillNo'),// 字段标题
				name : 'waybillNo',// 关联的名称
//				vtype: 'waybill' // 无标签弃货的运单号不是纯数字的
				regex : /^([a-zA-Z0-9]{8,10}\n?)+$/i,
				regexText : '运单号为8-10位'
			}, {
				xtype : 'combobox',
				columnWidth : 0.25,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.status'),// 字段标题
				value : '',
				valueField : 'valueCode',
				displayField : 'valueName',
				store : FossDataDictionary.getDataDictionaryStore(
						predeliver.processAbandonGoods.PKP_ABANDONGOODS_STATUS, null, {
							'valueCode' : '',
							'valueName' : '全部'
						}),
				name : 'status'// 关联的名称
			}, {
				xtype : 'combobox',
				columnWidth : 0.25,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.abandonedgoodsType'),// 字段标题
				value : '',
				valueField : 'valueCode',
				displayField : 'valueName',
				store : FossDataDictionary.getDataDictionaryStore(
						predeliver.processAbandonGoods.PKP_ABANDONGOODS_TYPE, null, {
							'valueCode' : '',
							'valueName' : '全部'
						}),

				name : 'abandonedgoodsType'// 关联的名称
			}, {
				xtype : 'dynamicorgcombselector',// 部门选择器
				columnWidth : 0.25,
				name : 'receiveOrgCode',
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveOrgCode')
					// 字段标题
			}, {

				xtype : 'textfield',
				columnWidth : 0.25,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.deliveryCustomerContact'),// 字段标题
				maxLength:100,
				name : 'deliveryCustomerName'// 关联的名称
			}, {
				xtype : 'textfield',
				columnWidth : 0.25,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.createUserName'),// 字段标题
				name : 'createUserName'// 关联的名称
			}, {
				xtype : 'rangeDateField',// type
				columnWidth : 0.5,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.preabandonedgoodsTime2'),// 字段标题
				format : 'Y-m-d H:i:s',
				dateType : 'datetimefield_date97',
				fieldId:'Foss_predeliver_preabandonedgoodsTime_Id',
				allowFromBlank : true,
				allowToBlank : true,
				editable : false,
				fromName : 'preabandonedgoodsTimeBegin',// 关联的名称
				toName : 'preabandonedgoodsTimeEnd',// 关联的名称
				fromValue : Ext.Date.format(predeliver.processAbandonGoods.getTargetDate(
								new Date(), -7), 'Y-m-d H:i:s'),
				toValue : Ext.Date.format(predeliver.processAbandonGoods.getTargetDateEnd(
								new Date(), 0), 'Y-m-d H:i:s')

			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.reset'),// 字段标题
					columnWidth : .08,
					handler : function() {
						var myform = this.up('form');
						myform.getForm().reset();// reset
						myform.getForm().findField('preabandonedgoodsTimeBegin').setValue(Ext.Date.format(predeliver.processAbandonGoods.getTargetDate(
								new Date(), -7), 'Y-m-d H:i:s'));
				        myform.getForm().findField('preabandonedgoodsTimeEnd').setValue(Ext.Date.format(predeliver.processAbandonGoods.getTargetDateEnd(
								new Date(), 0), 'Y-m-d H:i:s'));
						// page
						// form
					}
				}, {
					xtype : 'container',
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					text : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.search'),// 字段标题
					disabled:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexquerybutton'),
					hidden:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexquerybutton'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						// 得到form
						// values
						var serachParms = this.up('form').getForm().getValues();

						// 选择时间
						// null
						if (serachParms.preabandonedgoodsTimeBegin == ''
								|| serachParms.preabandonedgoodsTimeEnd == '') {
							Ext.ux.Toast.msg('提示', '选择时间', 'error', 1000);// 选择时间
							// null
							return;
						}

						var timeBegin = serachParms.preabandonedgoodsTimeBegin;// 开始时间
						var timeEnd = serachParms.preabandonedgoodsTimeEnd;// 结束时间

						var startDate = new Date(Date.parse(timeBegin.replace(
										/-/g, "/"))); // 解析开始时间
						var endDate = new Date(Date.parse(timeEnd.replace(/-/g,
										"/"))); // 解析结束时间

						if (startDate < predeliver.processAbandonGoods.getTargetDate(endDate, -30)) {// 结束时间要在开始时间30天以内

							// 结束时间要在开始时间30天以内
							Ext.ux.Toast.msg('警告', '对不起，查询时间段不能超过30天！',
									'error', 2000);

							return;
						}

						var resultGridStore = predeliver.processAbandonGoods.processAbandonGoodsGrid.store;// 读取数据
						resultGridStore.load({
							params : {
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.waybillNo' : serachParms.waybillNo,// 运单号
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.status' : serachParms.status,// 状态
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.abandonedgoodsType' : serachParms.abandonedgoodsType,// 弃货类型
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.receiveOrgCode' : serachParms.receiveOrgCode,// 接受部门
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.deliveryCustomerName' : serachParms.deliveryCustomerName,// 发送客户
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.createUserName' : serachParms.createUserName,// 创建人
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeBegin' : serachParms.preabandonedgoodsTimeBegin,// 开始时间
								'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeEnd' : serachParms.preabandonedgoodsTimeEnd
								// 结束时间
							}
						});
					}
				}]
			}]
		});
		me.callParent(arguments);
	}
});
Ext.define('Foss.predeliver.processAbandonGoods.EditAbandonGoodsModel', {
	extend: 'Ext.data.Model',
	fields: [
			    { name: 'waybillNo'}, //运单号
				{ name: 'receiveOrgName'}, //始发部门
				{ name: 'deliveryCustomerName'}, //发货人
				{ name: 'deliveryCustomerContact'},//发货人
				{ name: 'deliveryCustomerMobilephone'}, //发货人手机
				{ name: 'goodsVolumeTotal'}, //体积(方)
				{ name: 'preabandonedgoodsTime',type:'date',
					convert : dateConvert}, //入库时间
				{ name : 'storageDay',// 关联的名称
				  type : 'string',
				  convert : function(value) {// convert类型转换器
					if (value != null) {
						return value + '(天)';
					} else {
						return null;
					}
				  }
				},
				{ name: 'createUserName'},  //操作人
				{ name: 'exceptionId'}, //异常_ID
				{ name: 'customerCooperateStatus'}, //客户配合
				{ name: 'notes'}, //弃货事由
				{ name: 'abandonedgoodsType'} //弃货类型
			 ]
});
//上传付款文件的panel
Ext.define('Foss.predeliver.Grid.FileUploadGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			modulePath : 'pkp-predeliver',
			title : predeliver.processAbandonGoods.i18n('pkp.predeliver.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/predeliver/uploadFiles.action',
//			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/predeliver/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/predeliver/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/predeliver/reviewImg.action'
		});
//编辑弃货信息表单
Ext.define('Foss.predeliver.processAbandonGoods.EditAbandonGoodsForm', {
	extend : 'Ext.form.Panel',
//	cls:'autoHeight',
//	bodyCls:'autoHeight',
	style:{border:'0px'},
	defaultType: 'textfield',
	defaults: {
			margin:'5 5 5 5',
			labelWidth:90
		},
	layout:'column',
	frame:true,
	collapsible: true,
	animCollapse: true,
	title : predeliver.processAbandonGoods.i18n('pkp.predeliver.processAbandonGoods.editAbandonGoods'),//编辑预弃货信息,
	fileUploadForAbdonGrid : null,
	getFileUploadForAbdonGrid : function() {
		if (this.fileUploadForAbdonGrid == null) {
			this.fileUploadForAbdonGrid = Ext.create(
					'Foss.predeliver.Grid.FileUploadForAbdonGrid', {
						columnWidth : 1
					});
		}
		return this.fileUploadForAbdonGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						xtype : 'hiddenfield',
						name : 'exceptionId' // 异常_ID
					}, {
						xtype : 'hiddenfield',
						name : 'abandonedgoodsType' // 弃货类型
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.waybillNo'),// 运单号,
						name : 'waybillNo',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.origOrgName'),// 始发部门,
						name : 'receiveOrgName',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.deliveryCustomerName'),// 发货人,
						name : 'deliveryCustomerContact',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.deliveryCustomerMobilephone'),// 发货人手机,
						name : 'deliveryCustomerMobilephone',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.goodsVolumeTotal'),// 体积(方),
						name : 'goodsVolumeTotal',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.inStockTime'),// 入库时间,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						name : 'preabandonedgoodsTime',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.storageDay'),// 仓储时长(天),
						name : 'storageDay',
						readOnly : true,
						columnWidth : .5
					}, {
						fieldLabel : predeliver.processAbandonGoods
								.i18n('pkp.predeliver.exceptionProcess.operator'),// 操作人,
						name : 'createUserName',
						readOnly : true,
						columnWidth : .5
					}/*, {
						boxLabel : '客户不予配合',
						name : 'customerCooperateStatus',
						inputValue : 'Y',
						xtype : 'checkbox',
						columnWidth : .5 ,
					}*/,{
						fieldLabel : '弃货事由',
						name : 'notes',
						xtype: 'textarea',
						height : 80,
						width : 600,
						allowBlank:false,
						colspan:3,
						maxLength:300,
						columnWidth : 1
					},me.getFileUploadForAbdonGrid()],
			buttons: [{
					text : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.exceptionProcess.close'),// 关闭,
					handler : function() {
						editAbandonedGoodsWin.close();
					}
				},'->', {
					cls : 'yellow_button',
					text : predeliver.processAbandonGoods.i18n('pkp.predeliver.exceptionProcess.submit'),// 提交,
					disabled:!predeliver.processAbandonGoods.isPermission('exceptionprocessindex/exceptionprocessindexsubmitbutton'),
					hidden:!predeliver.processAbandonGoods.isPermission('exceptionprocessindex/exceptionprocessindexsubmitbutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					handler : function() {
						var serachParms = this.up('form').getForm()
								.getValues();
						var filearray = new Array();
						var fileGrid = me.getFileUploadForAbdonGrid();
						fileGrid.getStore().each(function(record) {
							filearray.push({
										'id' : record.get('id')
									});
						});
						if(!this.up('form').getForm().isValid()){
							return;
						}
						Ext.Ajax.request({
							url : predeliver
									.realPath('editAbandonGoodsApplication.action'),
							method : 'POST',
							jsonData : {
								'vo' : {
									'abandonGoodsApplicationEntity' : {
										'waybillNo' : serachParms.waybillNo,
										'notes' : serachParms.notes,
										'id' : predeliver.processAbandonGoods.abandonId,
									},
									'attachementFiles' : filearray
								}
							},
							success : function(response) {
								var json = Ext
										.decode(response.responseText);
								Ext.ux.Toast
										.msg(
												predeliver.processAbandonGoods
														.i18n('pkp.predeliver.exceptionProcess.tip'),
												predeliver.processAbandonGoods
														.i18n('pkp.predeliver.exceptionProcess.saveSuccess'),
												'ok', 1000);
								me.getFileUploadForAbdonGrid().getStore().removeAll();
								predeliver.processAbandonGoods.abandonId =null;
								editAbandonedGoodsWin.close();
							},
							exception : function(response) {
								var result = Ext
										.decode(response.responseText);
								Ext.ux.Toast
										.msg(
												predeliver.processAbandonGoods
														.i18n('pkp.predeliver.exceptionProcess.tip'),
												result.message,
												'error', 3000);
							}
						});

					}
				}]
		});

		me.callParent(arguments);
	}
});

Ext.define('Foss.predeliver.Grid.FileUploadForAbdonGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	modulePath : 'pkp-predeliver',
	title : predeliver.processAbandonGoods.i18n('pkp.predeliver.fileUpload'), // 上传凭证附件,
	uploadUrl : ContextPath.PKP_DELIVER + '/predeliver/uploadFiles.action',
//	fileTypes: ['jpg','jpeg','gif','bmp','png'],
	downLoadUrl : ContextPath.PKP_DELIVER + '/predeliver/downLoadFiles.action',
	deleteUrl : ContextPath.PKP_DELIVER + '/predeliver/deleteFile.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/predeliver/reviewImg.action'
});
predeliver.processAbandonGoods.editAbandonGoodsForm = Ext.create('Foss.predeliver.processAbandonGoods.EditAbandonGoodsForm');
//新增弃货信息窗口
Ext.define('Foss.HandlingExceptions.Window.eidtAbandonedGoods', {
	extend:'Ext.window.Window',
	closeAction:'hide',
	width: 550,
//	height : 450,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout: 'auto',	
	items:[predeliver.processAbandonGoods.editAbandonGoodsForm]
});
var editAbandonedGoodsWin = null;
// 查询结果
Ext.define('Foss.predeliver.processAbandonGoods.GridPanel', {
	extend : 'Ext.grid.Panel',
	title : predeliver.processAbandonGoods
			.i18n('pkp.predeliver.processAbandonGoods.handleAbandonedgoods'),
	emptyText : '查询结果为空',
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	foolBar : null,
	viewConfig : {
		// 单元格可复制
		enableTextSelection: true
	},
	getFoolBar : function() {
		if (this.foolBar == null) {
			this.foolBar = Ext.create('Ext.Toolbar', {
				dock : 'bottom',
				items : [{
					xtype : 'textfield',
					width : 208,
					readOnly : true,
					fieldLabel : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.zgoodsWeightTotal'),// 字段标题
					name : 'goodsWeightTotal'// 关联的名称
				}, {
					xtype : 'textfield',
					width : 195,
					readOnly : true,
					fieldLabel : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.goodsVolumeTotal2'),// 字段标题
					name : 'goodsVolumeTotal'// 关联的名称
				}, {
					xtype : 'textfield',
					width : 195,
					readOnly : true,
					fieldLabel : '总件数',// 字段标题
					name : 'goodsQtyTotal'// 关联的名称
				}, {
					xtype : 'textfield',
					readOnly : true,
					width : 195,
					name : 'forPosition' // 占jsp位子用的一个样式textfield没有业务价值
				}, {
					xtype : 'button',
					text : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.inputInternalGoods'),// 字段标题
					disabled:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindeximportbutton'),
					hidden:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindeximportbutton'),
					// 2秒内不能重新点plugin
					plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds : 2
							// 2秒内不能重新点
						}),
					handler : function() {
						// 选择行
						var selectWaybill = predeliver.processAbandonGoods.processAbandonGoodsGrid
								.getSelectionModel().getSelection();
						var selectSheetNos = '';
						if (selectWaybill.length == 0) {
							Ext.ux.Toast.msg("提示信息", "请选择导入行！");
						} else {
							for (var i = 0; i < selectWaybill.length; i++) {

								if (selectSheetNos.length == 0) {// 拼接选择的id
									selectSheetNos = selectWaybill[i].data.id;
								} else {
									selectSheetNos = selectSheetNos + ","
											+ selectWaybill[i].data.id;
								}
							}
							// ajax 请求发送
							Ext.Ajax.request({
								url : predeliver
										.realPath('createAbandonGoodsImport.action'),// 老的url格式：
								// '../predeliver/createAbandonGoodsImport.action',
								params : {
									'vo.id' : selectSheetNos
								},
								// 导入成功!
								success : function(response) {
									Ext.ux.Toast.msg('提示', '导入成功!', 'ok', 1000);
									var serachParms = predeliver.processAbandonGoods.queryForm.getForm().getValues();
									var resultGridStore = predeliver.processAbandonGoods.processAbandonGoodsGrid.store;// 读取数据
									resultGridStore.load({
										params : {
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.waybillNo' : serachParms.waybillNo,// 运单号
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.status' : serachParms.status,// 状态
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.abandonedgoodsType' : serachParms.abandonedgoodsType,// 弃货类型
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.receiveOrgCode' : serachParms.receiveOrgCode,// 接受部门
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.deliveryCustomerName' : serachParms.deliveryCustomerName,// 发送客户
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.createUserName' : serachParms.createUserName,// 创建人
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeBegin' : serachParms.preabandonedgoodsTimeBegin,// 开始时间
											'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeEnd' : serachParms.preabandonedgoodsTimeEnd
											// 结束时间
										}
									});
									// 设置体积、总量、件数为空
									
								},
								// 导入失败！
								exception : function(response) {
									var json = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg('失败！', '保存失败！', 'error',
											2000);// 失败
								}
							});
						}

					}

				}]
			});
		}
		return this.foolBar;
	},
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		listeners : {
			// 对于选择事件 进行过滤
			'beforeselect' : function(SelectionModel, record, rowIndex, eOpts) {
				// 只有审批通过 可导入 其他状态不可以
				if (record.data.status == 'ABANDGOODS_STATUS_NEW'
						|| record.data.status == 'ABANDGOODS_STATUS_APPROVAL'
						|| record.data.status == 'ABANDGOODS_STATUS_REFUSE'
						|| record.data.status == 'ABANDGOODS_STATUS_DEALED'
						|| record.data.status == 'ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE') {
					return false;
				}

				// 审批通过 可导入
				return true;
			}
		}
	}),

	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.workflow'),// 字段标题
				align : 'center',
				dataIndex : 'startDraft', // 第一次起草
				items : [{
					tooltip : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.draft'),// tip
					iconCls : 'deppon_icons_notice',
					getClass : function(value, metadata, record, rowIndex,
							colIndex, store) {

						// 审批通过 审批中 不能起草
						if (record.get('status') == 'ABANDGOODS_STATUS_PASS'
								|| record.get('status') == 'ABANDGOODS_STATUS_APPROVAL'
								|| record.get('status') == 'ABANDGOODS_STATUS_DEALED'||record.get("startDraft")=='Y') {
							return 'deppon_icons_notice_hidden';
						} else {// 只有审批不通过 没有审批可起草
							return 'deppon_icons_notice';
						}
					},
					xtype : 'button',
					disabled:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexapplicationbutton'),
					hidden:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexapplicationbutton'),
					// 3秒内不能重新点plugin
					plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds : 3
							// 3秒内不能重新点
						}),
					handler : function(grid, rowIndex, colIndex) {
						// Ext.MessageBox.progress('请等待');

						var selection = grid.getStore().getAt(rowIndex);
						var waybillNoSelected = selection.get("waybillNo");// 运单号
						var waybillNoStatus = selection.get("status");// 审批状态

						// 该弃货运单目前正在审批流程中或者审批流程已经通过
						if (waybillNoStatus == 'ABANDGOODS_STATUS_PASS'
								|| waybillNoStatus == 'ABANDGOODS_STATUS_APPROVAL'
								|| waybillNoStatus == 'ABANDGOODS_STATUS_DEALED') {
							Ext.ux.Toast.msg('起草失败！',
									'该弃货运单目前正在审批流程中或者审批流程已经通过,不能再次起草申请流程',
									'error', 2000);
							return;// NO NEED TO CALL Ajax
						}

						// CALL Ajax
						Ext.Ajax.request({
							url : predeliver
									.realPath('startDiscardWorkflow.action'),// 老的url格式：
							// '../predeliver/startDiscardWorkflow.action',
							params : {
								'vo.waybillNos' : waybillNoSelected,
								'vo.id' : selection.get("id")
								// 运单号
							},

							success : function(response) {
								// ajax reposne
								var result = Ext.decode(response.responseText);

								// 是否成功标记
								var successResult = result.vo.resultDto.code;

								// 错误信息
								var errormsg = result.vo.resultDto.msg;
								var processId = result.vo.resultDto.processId;
								if (successResult == "1") {// 起草成功
									Ext.ux.Toast.msg('提示', '起草成功!', 'ok', 1000);
									if(!Ext.isEmpty(processId)){
										selection.set("processId",result.vo.resultDto.processId);
										selection.set("status","ABANDGOODS_STATUS_APPROVAL");
										selection.set('startDraft','Y');
									}
									// predeliver.pagingBar.moveFirst();//重新翻页

								} else {// 起草失败！
									Ext.ux.Toast.msg('起草失败！', errormsg,
											'error', 2000);
								}
							},
							exception : function(response) {// 起草失败！
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg('起草失败！', json.message,
										'error', 2000);
							}
						});

					}
				},{
					tooltip : predeliver.processAbandonGoods.i18n('pkp.predeliver.processAbandonGoods.draft'),// tip
					iconCls : 'deppon_icons_notice',
					getClass : function(value, metadata, record, rowIndex,
							colIndex, store) {
						var result = Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s') 
										- Ext.Date.parse(Ext.Date.format(record.get('operateTime'),'Y-m-d H:i:s'),'Y-m-d H:i:s');
						// 审批通过 审批中 没有工作流ID，可以重新起草工作流
						if (record.get('status') == 'ABANDGOODS_STATUS_APPROVAL'
								&& record.get('processId') == '' && result >= 86400000 && record.get('startDraft')!='Y') {
							return 'deppon_icons_notice';
						} else {
							return 'deppon_icons_notice_hidden';
						}
					},
					xtype : 'button',
					disabled:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexapplicationbutton'),
					hidden:!predeliver.processAbandonGoods.isPermission('processabandongoodsindex/processabandongoodsindexapplicationbutton'),
					// 3秒内不能重新点plugin
					plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds : 3
							// 3秒内不能重新点
						}),
					handler : function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var waybillNoSelected = selection.get("waybillNo");// 运单号
						var waybillNoStatus = selection.get("status");// 审批状态
						var processId = selection.get("processId");
						var operateTime = selection.get("operateTime");
		
						// 该弃货运单目前正在审批流程中或者审批流程已经通过
						var result = Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s') 
						- Ext.Date.parse(Ext.Date.format(operateTime,'Y-m-d H:i:s'),'Y-m-d H:i:s');
						
						if (waybillNoStatus == 'ABANDGOODS_STATUS_APPROVAL'
								&& processId == '' && result >= 86400000) {
							// 审批通过 审批中 没有工作流ID，可以重新起草工作流
						} else {
							Ext.ux.Toast.msg('起草失败！',
									'该弃货运单目前正在审批流程中或者审批流程已经通过,不能再次起草申请流程',
									'error', 2000);
							return;
						}
		
						// CALL Ajax
						Ext.Ajax.request({
							url : predeliver
									.realPath('startDiscardWorkflow.action'),// 老的url格式：
							// '../predeliver/startDiscardWorkflow.action',
							params : {
								'vo.waybillNos' : waybillNoSelected,
								'vo.id' : selection.get("id")
								// 运单号
							},
		
							success : function(response) {
								// ajax reposne
								var result = Ext.decode(response.responseText);
		
								// 是否成功标记
								var successResult = result.vo.resultDto.code;
		
								// 错误信息
								var errormsg = result.vo.resultDto.msg;
								var processId = result.vo.resultDto.processId;
								if (successResult == "1") {// 起草成功
									Ext.ux.Toast.msg('提示', '起草成功!', 'ok', 1000);
									if(!Ext.isEmpty(processId)){
										selection.set("processId",result.vo.resultDto.processId);
										selection.set("status","ABANDGOODS_STATUS_APPROVAL");
										selection.set("operateTime",new Date());
										selection.set('startDraft','Y');
									}
									// predeliver.pagingBar.moveFirst();//重新翻页
		
								} else {// 起草失败！
									Ext.ux.Toast.msg('起草失败！', errormsg,
											'error', 2000);
								}
							},
							exception : function(response) {// 起草失败！
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg('起草失败！', json.message,
										'error', 2000);
							}
						});
		
					}
				}]
			}, {
				xtype : 'actioncolumn',
				width : 80,
				text : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.abandonedGoodsInfo'),// 字段标题
				align : 'center',
				items : [{
					iconCls : 'deppon_icons_showdetail',
					tooltip : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.look'),// 字段标题
					handler : function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var id = selection.get('id');// 得到id

						// call ajax
						Ext.Ajax.request({
							url : predeliver
									.realPath('searchAbandonGoodsDetail.action'),// 老的url格式：
							// '../predeliver/searchAbandonGoodsDetail.action',
							params : {
								'abandonedGoodsSearchVo.id' : id
							},
							// 请求成功
							success : function(response) {
								// get ajax response
								var result = Ext.decode(response.responseText);

								// 得到数据模型
								var formModel = new Foss.predeliver.processAbandonGoods.AbandonGoodsDetailModel(result.abandonedGoodsSearchVo.abandonedGoodsDetailDto);
								// 读数据
								predeliver.processAbandonGoods.abandonGoodsDetailForm
										.loadRecord(formModel);
								predeliver.processAbandonGoods.abandonGoodsDetailForm.fileUploadResultGrid.getStore()
										.loadData(result.abandonedGoodsSearchVo.abandonedGoodsDetailDto.abandonedGoodsFiles);
								// 展现窗体
								detailWin = Ext
										.create('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailWindow')
										.show();
							}
						});

					}
				},{
					iconCls : 'deppon_icons_edit',
					tooltip : predeliver.processAbandonGoods
							.i18n('pkp.predeliver.processAbandonGoods.edit'),// 编辑
					handler: function(grid, rowIndex, colIndex) {
						//页面显示数据
						var selection=grid.getStore().getAt(rowIndex);
						//页面保数据
						var waybillNoSelected = selection.get("waybillNo");// 运单号
						var waybillNoStatus = selection.get("status");// 审批状态
						var id = selection.get('id');// 得到id
						// 该弃货运单目前正在审批流程中或者审批流程已经通过
						if (waybillNoStatus == 'ABANDGOODS_STATUS_PASS'
								|| waybillNoStatus == 'ABANDGOODS_STATUS_APPROVAL'
								|| waybillNoStatus == 'ABANDGOODS_STATUS_DEALED') {
							Ext.ux.Toast.msg('编辑失败！',
									'该弃货运单目前正在审批流程中或者审批流程已经通过,不能修改.',
									'error', 2000);
							return;// NO NEED TO CALL Ajax
						}
						Ext.Ajax.request({
							url:predeliver.realPath('searchAbandonGoodsDetail.action'),
							params : {
								'abandonedGoodsSearchVo.id' : id
							},
							success : function(response) {
							 	var result = Ext.decode(response.responseText);
								var model = Ext.ModelManager.create(result.abandonedGoodsSearchVo.abandonedGoodsDetailDto,'Foss.predeliver.processAbandonGoods.EditAbandonGoodsModel');
								predeliver.processAbandonGoods.abandonId=result.abandonedGoodsSearchVo.id;
								predeliver.processAbandonGoods.editAbandonGoodsForm.loadRecord(model);
								predeliver.processAbandonGoods.editAbandonGoodsForm.fileUploadForAbdonGrid.getStore()
								.loadData(result.abandonedGoodsSearchVo.abandonedGoodsDetailDto.abandonedGoodsFiles);
								editAbandonedGoodsWin = Ext.create('Foss.HandlingExceptions.Window.eidtAbandonedGoods').show();
							}
					    });
					}
					
				}]
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.waybillNo'),// 字段标题
				align : 'center',
				dataIndex : 'waybillNo',// 关联的名称
				flex : 1
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.serialNumber'),// 字段标题
				align : 'center',
				dataIndex : 'serialNumber',// 关联的名称
				flex : 1			
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.status'),// 字段标题
				align : 'center',
				dataIndex : 'status',// 关联的名称
				flex : 1,
				renderer : function(value, metadata, record, rowIndex,
						columnIndex, store) {
					return FossDataDictionary.rendererSubmitToDisplay(value,
							predeliver.processAbandonGoods.PKP_ABANDONGOODS_STATUS);

				}
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.createUserName'),// 字段标题
				align : 'center',
				dataIndex : 'createUserName', // 关联的名称
				flex : 1
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveCustomerName'),// 字段标题
				align : 'center',
				dataIndex : 'receiveCustomerName', // 关联的名称
				flex : 1
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveCustomerPhone'),// 字段标题
				align : 'center',
				dataIndex : 'receiveCustomerPhone', // 关联的名称
				flex : 1.2
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.preabandonedgoodsTime2'),// 字段标题
				align : 'center',
				dataIndex : 'preabandonedgoodsTime', // 关联的名称
				flex : 1.8,
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
						return date;
					} else {
						return null;
					}
				} 
			}, {
				header : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.processId'),// 字段标题
				align : 'center',
				dataIndex : 'processId', // 关联的名称
				flex : 1.2
			}],
	listeners : {
		// select event 当选择中的时候被触发
		'select' : function(rowModel, record, index, eOpts) {
			var selectRecords = rowModel.getSelection(), goodsWeightTotal = 0, // 总重量
			goodsVolumeTotal = 0, // 总体积
			goodsQtyTotal = 0, // 总数量
			items = this.getFoolBar().items.items;// 总记录数

			for (var i = 0; i < selectRecords.length; i++) {// 计算记录
				goodsWeightTotal += selectRecords[i].get('goodsWeightTotal');// 计算总重量
				goodsVolumeTotal += selectRecords[i].get('goodsVolumeTotal');// 计算总体积
				goodsQtyTotal += selectRecords[i].get('goodsQtyTotal');// 计算总数量
			}

			items[0].setValue(goodsWeightTotal + '(千克)');// set
			// 总重量
			items[1].setValue(goodsVolumeTotal + '(立方米)');// set
			// 总体积
			items[2].setValue(goodsQtyTotal + '(件)'); // set
			// 总数量
		}

	},
	constructor : function(config) {// 初始化组件
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.processAbandonGoods.ProcessAbandonGoodsStore');
		me.dockedItems = [me.getFoolBar()];
		// me.getFoolBar();
		me.callParent([cfg]);

		// 分页组件 不需要分页 但是我们用这个组件来刷新页面
		/*me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});*/

		predeliver.processAbandonGoods.pagingBar = me.bbar;
	}
});
// 附件详情
Ext.define('Foss.predeliver.processAbandonGoods.FileUploadResultGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	reviewFlag : true,
//	fileTypes: ['jpg','jpeg','gif','bmp','png'],
	title : '上传凭证', // 上传凭证,
	downLoadUrl : ContextPath.PKP_DELIVER + '/sign/downLoadFiles.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
});
// 弃货详细信息
Ext.define('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : predeliver.processAbandonGoods
			.i18n('pkp.predeliver.processAbandonGoods.searchAbandonedgoodsInfo'),// 查询弃货信息
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 80
	},
	fileUploadResultGrid : null,
	getFileUploadResultGrid : function() {
		if (this.fileUploadResultGrid == null) {
			this.fileUploadResultGrid = Ext.create('Foss.predeliver.processAbandonGoods.FileUploadResultGrid',
			{
				columnWidth : 1,
				autoScroll : true
			});
		}
		return this.fileUploadResultGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.waybillNo2'),// 字段标题
				name : 'waybillNo' // 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.goodsName'),// 字段标题
				name : 'goodsName'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.goodsWeightTotal'),// 字段标题
				format : '0.00',
				name : 'goodsWeightTotal'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.goodsVolumeTotal'),// 字段标题
				format : '0.00',
				name : 'goodsVolumeTotal'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.deliveryCustomerContact'),// 字段标题
				name : 'deliveryCustomerContact'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.deliveryCustomerPhone'),// 字段标题
				name : 'deliveryCustomerPhone'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveOrgCode'),// 字段标题
				name : 'receiveOrgName'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.respectiveRegionalName'),// 字段标题
				name : 'respectiveRegionalName'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveCustomerContact'),// 字段标题
				name : 'receiveCustomerContact'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.receiveCustomerPhone'),// 字段标题
				name : 'receiveCustomerPhone'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.lastLoadOrgName'),// 字段标题
				name : 'lastLoadOrgName'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.lastStorageName'),// 字段标题
				name : 'lastStorageName'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,// 默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.codAmount'),// 字段标题
				name : 'codAmount'// 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.insuranceAmount'),//字段标题
				name : 'insuranceAmount'//关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.prePayAmount'),//字段标题
				name : 'prePayAmount'//关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.toPayAmount'),//字段标题
				name : 'toPayAmount'//关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.abandonedgoodsType'),//字段标题
				name : 'abandonedgoodsType'//关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.status'),//字段标题
				name : 'status'//关联的名称
			}, {
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.preabandonedgoodsTime'),//字段标题
				xtype:'datefield',
				format : 'Y-m-d H:i:s',
				name : 'preabandonedgoodsTime'////关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1 / 4,//默认长度 一行可以显示4个
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.storageDay'),//字段标题
				name : 'storageDay'////关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/4,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.createUserName'),//字段标题
				name : 'createUserName'////关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 3/4,
				fieldLabel : '客户配合',
				name : 'customerCooperateStatus'////关联的名称
			}, {
				xtype : 'textareafield',
				readOnly : true,
				columnWidth : 1/4,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.notes'),//字段标题
				name : 'notes'////关联的名称
			}, {
				xtype : 'textareafield',
				readOnly : true,
				columnWidth : 1/4,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.serialNumber'),//字段标题
				name : 'serialNumber'////关联的名称
			}, {
				xtype : 'textareafield',
				readOnly : true,
				columnWidth : 1/2,
				fieldLabel : predeliver.processAbandonGoods
						.i18n('pkp.predeliver.processAbandonGoods.errorNumber'),//字段标题
				name : 'errorNumber'////关联的名称
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [
					{
						xtype : 'label',
						columnWidth : 1 / 5,
						text : '处理凭证'
					},
					me.getFileUploadResultGrid()]
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : .45,
							html : '&nbsp;'
						}, {
							text : predeliver.processAbandonGoods
									.i18n('pkp.predeliver.processAbandonGoods.confirm'),//字段标题
							cls : 'yellow_button',
							align : 'center',
							columnWidth : .10,
							handler : function() {
								detailWin.close();//close window
							}
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .45,
							html : '&nbsp;'
						}]
			}]
		});
		me.callParent(arguments);
	}
});

//定义变量，在事件里面使用
predeliver.processAbandonGoods.abandonGoodsDetailForm = Ext
		.create('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailForm');

//定义打开的windows
Ext.define('Foss.predeliver.processAbandonGoods.AbandonGoodsDetailWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	width : 850,
	x : 300,
	y : 150,
	items : [predeliver.processAbandonGoods.abandonGoodsDetailForm]
		//明细展现的form
	});
//查询 结果表格
// extjs初始化
Ext.onReady(function() {
	Ext.Loader.setConfig({enabled:true});
	Ext.QuickTips.init();//初始化
	predeliver.processAbandonGoods.processAbandonGoodsGrid = Ext.create("Foss.predeliver.processAbandonGoods.GridPanel");
	predeliver.processAbandonGoods.queryForm = Ext.create("Foss.predeliver.processAbandonGoods.QueryForm"),
	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-processAbandonGoodsIndex_content',//id
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ predeliver.processAbandonGoods.queryForm,predeliver.processAbandonGoods.processAbandonGoodsGrid],//查询条件form, //查询结果grid
		renderTo : 'T_predeliver-processAbandonGoodsIndex-body'
	});
});
