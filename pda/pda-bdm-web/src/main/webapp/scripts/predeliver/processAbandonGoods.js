predeliver.PKP_ABANDONGOODS_STATUS='PKP_ABANDONGOODS_STATUS';
predeliver.PKP_ABANDONGOODS_TYPE='PKP_ABANDONGOODS_TYPE';	
/**

 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
predeliver.getTargetDateEnd = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};



/**
 * 获得当天的指定时间
 */
predeliver.getTargetDateTime = function(date, hour, minute, second, millisecond) {
	var t2 = date;//date
	t2.setHours(hour);//hour
	t2.setMinutes(minute);//minute
	t2.setSeconds(second);//second
	t2.setMilliseconds(millisecond);//millisecond	
	return t2;
};


// 定义model
Ext.define('Foss.ProcessAbandonGoods', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',//关联的名称
						type : 'string'
					},{// 重量--运单
						name : 'goodsWeightTotal',//关联的名称
						type : 'Number'
					},{// 体积--运单
						name : 'goodsVolumeTotal',//关联的名称
						type : 'Number'
					},{// 总件数--运单
						name : 'goodsQtyTotal',//关联的名称
						type : 'int'
					},{// 运单号
						name : 'waybillNo',//关联的名称
						type : 'string'
					},{
						name : 'status',//关联的名称
						type : 'string'
					},// 处理状态
					{
						name : 'createUserName',//关联的名称
						type : 'string'
					},// 预弃货人
					{
						name : 'receiveCustomerName',//关联的名称
						type : 'string'
					},// 收货人姓名
					{
						name : 'receiveCustomerPhone',//关联的名称
						type : 'string'
					},// 收货人电话
					{
						name : 'draftTime',
						type : 'date',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								var date = new Date(value);
								return date;
							} else {
								return null;
							}
						}
					}// 预弃货时间
			]
		});
// 定义弃货详细信息模型
Ext.define('Foss.predeliver.AbandonGoodsDetailModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'waybillNo',//关联的名称
						type : 'string'
					},// 单号
					{
						name : 'goodsName',//关联的名称
						type : 'string'
					}, // 货物名称
					{
						name : 'goodsWeightTotal',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(千克)';
							} else {
								return null;
							}
						}
					}, // 重量
					{
						name : 'darftOrgName',//关联的名称
						type : 'string'
					}, // 起草变更部门
					{
						name : 'goodsVolumeTotal',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(立方米)';
							} else {
								return null;
							}
						}
					}, // 体积
					{
						name : 'deliveryCustomerContact',//关联的名称
						type : 'string'
					}, // 发货人
					{
						name : 'deliveryCustomerPhone',//关联的名称
						type : 'string'
					}, // 发货人电话
					{
						name : 'receiveOrgCode',//关联的名称
						type : 'string'
					}, // 发货部门
					{
						name : 'receiveOrgName',//关联的名称
						type : 'string'
					}, // 发货部门
					{
						name : 'respectiveRegional',//关联的名称
						type : 'string'
					}, // 所属区域
					{
						name : 'respectiveRegionalName',//关联的名称
						type : 'string'
					}, // 所属区域
					{
						name : 'receiveCustomerContact',//关联的名称
						type : 'string'
					}, // 收货人
					{
						name : 'receiveCustomerPhone',//关联的名称
						type : 'string'
					}, // 收货人电话
					{
						name : 'lastLoadOrgCode',//关联的名称
						type : 'string'
					}, // 到达部门
					{
						name : 'lastLoadOrgName',//关联的名称
						type : 'string'
					},
					{
						name : 'lastStorageName',//关联的名称
						type : 'string'
					},
					{
						name : 'codAmount',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 代收金额
					{
						name : 'insuranceAmount',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 保险金额
					{
						name : 'prePayAmount',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 预付金额
					{
						name : 'toPayAmount',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(元)';
							} else {
								return null;
							}
						}
					}, // 到付金额
					{
						name : 'abandonedgoodsType',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PKP_ABANDONGOODS_TYPE);
						
						}
					}, // 弃货类型
					{
						name : 'status',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PKP_ABANDONGOODS_STATUS);
						
						}
						
					}, // 处理状态
					{
						name : 'preabandonedgoodsTime',//关联的名称
						convert : function (value){
							if (value != null) {//convert类型转换器
								return Ext.Date.format(new Date(value), 'Y-m-d');
							} else {
								return null;
							}
							
						},
						type : 'string'
						
					},// 入库时间
					{
						name : 'storageDay',//关联的名称
						type : 'string',
						convert : function(value) {//convert类型转换器
							if (value != null) {
								return value + '(天)';
							} else {
								return null;
							}
						}
					}, // 仓储时长
					{
						name : 'createUserName',//关联的名称
						type : 'string'
					}, // 预弃货人
					{
						name : 'notes',//关联的名称
						type : 'string'
					} // 弃货事由
			]
		});
var detailWin = null;
// 定义签收变更数据store
Ext.define('Foss.ProcessAbandonGoodsStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.ProcessAbandonGoods',
			proxy : {
				// 代理的类型为内存代理
				type : 'ajax',
				url : predeliver.realPath('searchAbandonGoodsList.action'),//老的url格式：
				//'../predeliver/searchAbandonGoodsList.action',
				headers: {//URL Encode
					'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'//UTF-8   
				},
				actionMethods:'POST',//post is necessary 如果不用post会造成中文乱码
				// 定义一个读取器
				reader : {
					// 以JSON的方式读取
					type : 'json',
					// 定义读取JSON数据的根对象
					root : 'abandonedGoodsSearchVo.abandonGoodsResultDtoList'

				}
			},
			listeners: { 
				//load event 当选择中的时候被触发
				load :function( store, records,successful,operation,eOpts ){//查询结果为空
					if(Ext.isEmpty(records)){
						Ext.ux.Toast.msg('提示信息', '查询结果为空!');
					}
				}
			}
		});

//定义数据model
Ext.define('Status', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'code', //code
						type : 'string'
					}, {
						name : 'name', // name
						type : 'string'
					}]
		});
// 定义处理状态store

// 定义弃货类型store

// 查询条件
Ext.define('Foss.ProcessAbandonGoods.queryForm', {
	extend : 'Ext.form.Panel',
	id: 'Foss_ProcessAbandonGoods_queryForm_ids',
	frame:true,
	collapsible: true,
	animCollapse: true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : '查询条件',//标题
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
						fieldLabel : '运单号',//字段标题
						name : 'waybillNo'//关联的名称
					}, {
						xtype : 'combobox',
						columnWidth : 0.25,
						fieldLabel : '处理状态',//字段标题
						value : '',
						valueField : 'valueCode',
						displayField : 'valueName',
						store : 
							FossDataDictionary.getDataDictionaryStore(predeliver.PKP_ABANDONGOODS_STATUS, null, {
								'valueCode': '',
					            'valueName': '全部'
							}),
						name : 'status'//关联的名称
					}, {
						xtype : 'combobox',
						columnWidth : 0.25,
						fieldLabel : '弃货类型',//字段标题
						value : '',
						valueField : 'valueCode',
						displayField : 'valueName',
						store : 
							FossDataDictionary.getDataDictionaryStore(predeliver.PKP_ABANDONGOODS_TYPE, null, {
								'valueCode': '',
					            'valueName': '全部'
							}),
							
						name : 'abandonedgoodsType'//关联的名称
					},{
						xtype : 'dynamicorgcombselector',//部门选择器
						id:'Foss_ProcessAbandonGoods_receiveOrgCode',
						columnWidth : 0.25,
						name : 'receiveOrgCode',
						
						fieldLabel : '发货部门',//字段标题
					},{
					
						xtype : 'textfield',
						columnWidth : 0.25,
						fieldLabel : '发货人',//字段标题
						name : 'deliveryCustomerName'//关联的名称
					}, {
						xtype : 'textfield',
						columnWidth : 0.25,
						fieldLabel : '预弃货人',//字段标题
						id:'Foss_ProcessAbandonGoods_createUserName',
						name : 'createUserName'//关联的名称
					}, {
						xtype : 'rangeDateField',//type
						columnWidth : 0.5,
						fieldLabel : '预弃货处理时间',//字段标题
						format: 'Y-m-d H:i:s' ,
						dateType: 'datetimefield_date97',
						fieldId:'Foss_ProcessAbandonGoods_preabandonedgoodsTime',
						allowFromBlank : true,
						allowToBlank : true,
						editable:false,
						fromName : 'preabandonedgoodsTimeBegin',//关联的名称
						toName : 'preabandonedgoodsTimeEnd',//关联的名称
						fromValue: Ext.Date.format(predeliver.getTargetDate(new Date(),0),'Y-m-d H:i:s'),
						toValue: Ext.Date.format(predeliver.getTargetDateEnd(new Date(),0),'Y-m-d H:i:s')
							
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : '重置',//字段标题
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form');
										myform.getForm().reset();//reset page form
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .84,
									html : '&nbsp;'
								}, {
									text : '查询',//字段标题
									cls : 'yellow_button',
									columnWidth : .08,
									handler : function() {
										//得到form values
										var serachParms = this.up('form')
												.getForm().getValues();
										
										//选择时间 null
										if(serachParms.preabandonedgoodsTimeBegin == '' 
												|| serachParms.preabandonedgoodsTimeEnd == ''){
											Ext.ux.Toast.msg('提示', '选择时间', 'error', 1000);//选择时间 null
											return;
										}
										
										var timeBegin = serachParms.preabandonedgoodsTimeBegin;//开始时间
										var timeEnd   = serachParms.preabandonedgoodsTimeEnd;//结束时间
										
										var   startDate   =   new Date(Date.parse(timeBegin.replace(/-/g, "/")));  //解析开始时间
										var   endDate   =     new Date(Date.parse(timeEnd.replace(/-/g,   "/")));   //解析结束时间
										
										if(startDate < predeliver.getTargetDate(endDate, -30)){//结束时间要在开始时间30天以内
											
											//结束时间要在开始时间30天以内
											Ext.ux.Toast.msg('警告', '对不起，查询时间段不能超过30天！', 'error', 2000);
											
											return;
										}
										
										var resultGridStore = predeliver.processAbandonGoodsGrid.store;//读取数据
										resultGridStore.load({
											params : {
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.waybillNo' : serachParms.waybillNo,//运单号
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.status' : serachParms.status,//状态
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.abandonedgoodsType' : serachParms.abandonedgoodsType,//弃货类型
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.receiveOrgCode' : serachParms.receiveOrgCode,//接受部门
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.deliveryCustomerName' : serachParms.deliveryCustomerName,//发送客户
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.createUserName' : serachParms.createUserName,//创建人
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeBegin' : serachParms.preabandonedgoodsTimeBegin,//开始时间
												'abandonedGoodsSearchVo.abandonedGoodsSearchDto.preabandonedgoodsTimeEnd' : serachParms.preabandonedgoodsTimeEnd  //结束时间
											}
										});
									}
								}]
					}]
		});
		me.callParent(arguments);
	}
});

// 查询结果
Ext.define('Foss.ProcessAbandonGoods.GridPanel', {
	extend : 'Ext.grid.Panel',
	title : '处理弃货',
	emptyText: "查询结果为空",
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	foolBar: null,
	getFoolBar: function(){
		if(this.foolBar==null){
			this.foolBar = Ext.create('Ext.Toolbar',{
			    dock: 'bottom',
			    id:'viewId',
			    items: [{
                    xtype: 'textfield',
                    width: 208,
                    readOnly: true,
                    fieldLabel: '总重量',//字段标题
                    name:'goodsWeightTotal'//关联的名称
                },{
                    xtype: 'textfield',
                    width: 195,
                    readOnly: true,
                    fieldLabel: '总体积',//字段标题
                    name:'goodsVolumeTotal'//关联的名称
                },{
                    xtype: 'textfield',
                    width: 195,
                    readOnly: true,
                    fieldLabel: '总件数',//字段标题
                    name:'goodsQtyTotal'//关联的名称
                },{
					xtype : 'textfield',
					readOnly: true,
					width : 460,
					name:'forPosition' //占jsp位子用的一个样式textfield没有业务价值
				},{
                    xtype: 'button',
                    text: '导入内部带货',//字段标题
                    //2秒内不能重新点plugin
                    plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
        				seconds: 2   //2秒内不能重新点
        			}),
                    handler:function(){
        				
                    	//选择行
                    	var selectWaybill = predeliver.processAbandonGoodsGrid.getSelectionModel().getSelection();
		        		var selectSheetNos = '';
		        		if(selectWaybill.length==0){
		        			Ext.ux.Toast.msg("提示信息","请选择导入行！");
		        		}else{
		        			for(var i = 0;i<selectWaybill.length;i++){
		        				
		        				if(selectSheetNos.length == 0) {//拼接选择的id
		        					selectSheetNos = selectWaybill[i].data.id;
		        				} else {
		        					selectSheetNos = selectSheetNos + "," + selectWaybill[i].data.id;
		        				}
		        			}
		        			//ajax  请求发送
		        			Ext.Ajax.request({
		    					url: predeliver.realPath('createAbandonGoodsImport.action'),//老的url格式：
		    					//'../predeliver/createAbandonGoodsImport.action',
		    					params: {
		    						'vo.id': selectSheetNos
		    					},
		    					//导入成功!
		    					success: function(response){
		    						Ext.ux.Toast.msg('提示', '导入成功!', 'ok', 1000);
		    						//predeliver.pagingBar.moveFirst();
		    					},
		    					//导入失败！
		    				    exception: function(response) {
		    		                var json = Ext.decode(response.responseText);
		    		                Ext.ux.Toast.msg('失败！', '保存失败！', 'error', 2000);//失败
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
					//对于选择事件 进行过滤
					'beforeselect' : function(SelectionModel, record, rowIndex,
							eOpts) {
						//只有审批通过 可导入 其他状态不可以
						if (record.data.status == 'ABANDGOODS_STATUS_NEW'
							||record.data.status == 'ABANDGOODS_STATUS_APPROVAL'
							||record.data.status == 'ABANDGOODS_STATUS_REFUSE'
							||record.data.status == 'ABANDGOODS_STATUS_DEALED'
							||record.data.status == 'ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE') {
							return false;
						}
						
						//审批通过 可导入
						return true;
					}
				}
			}),
	
	columns : [{
				header : 'ID',
				align : 'center',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			},{
				header : 'ID',
				align : 'center',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			},{
				header : 'ID',
				align : 'center',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			},{
				header : 'ID',
				align : 'center',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			}, {
				xtype : 'actioncolumn',
				width : 80,
				text : '工作流',//字段标题
				align : 'center',
				items : [{
							tooltip : '起草',//tip
							iconCls:'deppon_icons_notice',
							getClass : function(value,metadata,record,rowIndex,colIndex,store){
								
								
								//审批通过  审批中 不能起草
								if(record.get('status') =='ABANDGOODS_STATUS_PASS'
									|| record.get('status') =='ABANDGOODS_STATUS_APPROVAL'
										|| record.get('status') =='ABANDGOODS_STATUS_DEALED'
											|| record.get('status') =='ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE'  ){
									return 'deppon_icons_notice_hidden';
								}else {//只有审批不通过  没有审批可起草
									return 'deppon_icons_notice';
								}
							},
							xtype : 'button',
							//3秒内不能重新点plugin
							plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
								seconds: 3 //3秒内不能重新点
							}),
							handler : function(grid, rowIndex, colIndex) {
								//Ext.MessageBox.progress('请等待');
								
								var selection = grid.getStore().getAt(rowIndex);
								var waybillNoSelected = selection.get("waybillNo") ;//运单号
								var waybillNoStatus = selection.get("status") ;//审批状态
								
								//该弃货运单目前正在审批流程中或者审批流程已经通过
								if(waybillNoStatus=='ABANDGOODS_STATUS_PASS' 
									|| waybillNoStatus=='ABANDGOODS_STATUS_APPROVAL' 
										|| waybillNoStatus =='ABANDGOODS_STATUS_DEALED' ){
									Ext.ux.Toast.msg('起草失败！', '该弃货运单目前正在审批流程中或者审批流程已经通过,不能再次起草申请流程',
											'error', 2000);
									return;//NO NEED TO CALL Ajax
								}
								
								// CALL Ajax
								Ext.Ajax.request({
									url: predeliver.realPath('startDiscardWorkflow.action'),//老的url格式：
									//'../predeliver/startDiscardWorkflow.action',
									params: {
			    						'vo.waybillNos': waybillNoSelected //运单号
			    					},
									
									success: function(response){
										//ajax reposne 
										var result = Ext.decode(response.responseText);
										
										//是否成功标记
										var successResult = result.vo.resultDto.code;
										
										//错误信息
										var errormsg = result.vo.resultDto.msg;
										
										if(successResult=="1"){//起草成功
											Ext.ux.Toast.msg('提示', '起草成功!', 'ok', 1000);
											
											//predeliver.pagingBar.moveFirst();//重新翻页
											
										}else{//起草失败！
											Ext.ux.Toast.msg('起草失败！', errormsg, 'error', 2000);
										}
									},
								    exception: function(response) {//起草失败！
						                var json = Ext.decode(response.responseText);
						                Ext.ux.Toast.msg('起草失败！', json.message, 'error', 2000);
					                }
								});
								
							}
						}]
			}, {
				xtype : 'actioncolumn',
				width : 80,
				text : '弃货信息',//字段标题
				align : 'center',
				items : [{
					iconCls: 'deppon_icons_showdetail',
					tooltip: '查看',//字段标题
					handler : function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var id = selection.get('id');//得到id

						//call ajax
						Ext.Ajax.request({
							url : predeliver.realPath('searchAbandonGoodsDetail.action'),//老的url格式：
							//'../predeliver/searchAbandonGoodsDetail.action',
							params : {
								'abandonedGoodsSearchVo.id' : id
							},
							//请求成功
							success : function(response) {
								//get ajax response
								var result = Ext.decode(response.responseText);

								//得到数据模型
								var formModel = new Foss.predeliver.AbandonGoodsDetailModel(result.abandonedGoodsSearchVo.abandonedGoodsDetailDto);
								//读数据
								predeliver.abandonGoodsDetailForm.loadRecord(formModel);
								
								//展现窗体
								detailWin = Ext.create('Foss.processAbandonGoods.AbandonGoodsDetailWindow').show();
							}
						});

						
					}
				}]
			}, {
				header : '运单号',//字段标题
				align : 'center',
				dataIndex : 'waybillNo',//关联的名称
				flex : 1
			}, {
				header : '处理状态',//字段标题
				align : 'center',
				dataIndex : 'status',//关联的名称
				flex : 1,
				renderer : function(value, metadata, record, rowIndex, columnIndex, store){
					return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PKP_ABANDONGOODS_STATUS);
				
				}
			}, {
				header : '预弃货人',//字段标题
				align : 'center',
				dataIndex : 'createUserName',   //关联的名称
				flex : 1
			}, {
				header : '收货人姓名',//字段标题
				align : 'center',
				dataIndex : 'receiveCustomerName',  //关联的名称
				flex : 1
			}, {
				header : '收货人电话',//字段标题
				align : 'center',
				dataIndex : 'receiveCustomerPhone',  //关联的名称
				flex : 1.2
			}, {
				header : '预弃货处理时间',//字段标题
				align : 'center',
				dataIndex : 'preabandonedgoodsTime', //关联的名称
				flex : 1.8,
				renderer : function(value) {//convert 转换器
					if (value != null) {
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					} else {
						return null;
					}
				}
			}],
	listeners: { 
		//select event 当选择中的时候被触发
		'select': function(rowModel, record, index, eOpts ){
			var selectRecords = rowModel.getSelection(),
				goodsWeightTotal = 0,//总重量
				goodsVolumeTotal = 0,//总体积
				goodsQtyTotal = 0,//总数量
				items = this.getFoolBar().items.items;//总记录数
			
			
			for(var i=0;i<selectRecords.length;i++){//计算记录
				goodsWeightTotal += selectRecords[i].get('goodsWeightTotal');//计算总重量
				goodsVolumeTotal += selectRecords[i].get('goodsVolumeTotal');//计算总体积
				goodsQtyTotal += selectRecords[i].get('goodsQtyTotal');//计算总数量
			}
			
			items[0].setValue(goodsWeightTotal +'(千克)');//set 总重量
			items[1].setValue(goodsVolumeTotal +'(立方米)');//set 总体积
			items[2].setValue(goodsQtyTotal +'(件)');   //set 总数量
		}
		
	},
	constructor : function(config) {//初始化组件
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.ProcessAbandonGoodsStore');
		me.dockedItems = [me.getFoolBar()];
//		me.getFoolBar();
		me.callParent([cfg]);
		
		//分页组件  不需要分页 但是我们用这个组件来刷新页面
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		
		predeliver.pagingBar = me.bbar;
	}
});
// 弃货详细信息
Ext.define('Foss.ProcessAbandonGoods.AbandonGoodsDetailForm', {
			extend : 'Ext.form.Panel',
			frame:true,
			collapsible: true,
			animCollapse: true,
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			layout : {
				type : 'column'
			},
			bodyPadding : 10,
			title : '查询弃货信息',//查询弃货信息
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 80
			},
			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							items : [{
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '单号',//字段标题
										name : 'waybillNo'  //关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '货物名称',//字段标题
										name : 'goodsName'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '重量',//字段标题
										format: '0.00',
										name : 'goodsWeightTotal'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '体积',//字段标题
										format: '0.00',
										name : 'goodsVolumeTotal'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '发货人',//字段标题
										name : 'deliveryCustomerContact'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '发货人电话',//字段标题
										name : 'deliveryCustomerPhone'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '发货部门',//字段标题
										name : 'receiveOrgName'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '所属区域',//字段标题
										name : 'respectiveRegionalName'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '收货人',//字段标题
										name : 'receiveCustomerContact'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '收货人电话',//字段标题
										name : 'receiveCustomerPhone'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '到达部门',//字段标题
										name : 'lastLoadOrgName'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '仓储部门',//字段标题
										name : 'lastStorageName'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '代收金额',//字段标题
										name : 'codAmount'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '保险金额',//字段标题
										name : 'insuranceAmount'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '预付金额',//字段标题
										name : 'prePayAmount'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '到付金额',//字段标题
										name : 'toPayAmount'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '弃货类型',//字段标题
										name : 'abandonedgoodsType'//关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '处理状态',//字段标题
										name : 'status'//关联的名称
									}, {
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '入库时间',//字段标题
										xtype: 'textfield',
										name : 'preabandonedgoodsTime'////关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1 / 4,//默认长度 一行可以显示4个
										fieldLabel : '仓储时长',//字段标题
										name : 'storageDay'////关联的名称
									}, {
										xtype : 'textfield',
										readOnly: true,
										columnWidth : 1,
										fieldLabel : '预弃货人',//字段标题
										name : 'createUserName'////关联的名称
									}, {
										xtype : 'textareafield',
										readOnly: true,
										columnWidth : 1,
										fieldLabel : '弃货事由',//字段标题
										name : 'notes'////关联的名称
									}, {
										border : 1,
										xtype : 'container',
										columnWidth : 1,
										defaultType : 'button',
										layout : 'column',
										items : [ {xtype: 'container',
											border : false,
											columnWidth:.45,
											html: '&nbsp;'
										},{
													text : '确定',//字段标题
													cls : 'yellow_button',
													align: 'center',
													columnWidth : .10,
													handler : function() {
														detailWin.close();//close window
													}
												},{
													xtype: 'container',
													border : false,
													columnWidth:.45,
													html: '&nbsp;'
												}]
									}]
						});
				me.callParent(arguments);
			}
		});

//定义变量，在事件里面使用
predeliver.abandonGoodsDetailForm = Ext
		.create('Foss.ProcessAbandonGoods.AbandonGoodsDetailForm');

//定义打开的windows
Ext.define('Foss.processAbandonGoods.AbandonGoodsDetailWindow', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			modal : 'true',
			width : 850,
			x:300,
		    y:150,
			items : [predeliver.abandonGoodsDetailForm] //明细展现的form
		});

//查询 结果表格
predeliver.processAbandonGoodsGrid	= Ext.create(
		"Foss.ProcessAbandonGoods.GridPanel", {
			id : "Foss_ProcessAbandonGoods_GridPanel_Id"
		});


// extjs初始化
Ext.onReady(function() {
			Ext.QuickTips.init();//初始化
			
			//查询条件form
			var queryForm = Ext.create("Foss.ProcessAbandonGoods.queryForm");
			
			Ext.create('Ext.panel.Panel', {
						id : 'T_predeliver-processAbandonGoodsIndex-content',//id
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryForm : function() {
							return queryForm;
						},
						items : [queryForm, predeliver.processAbandonGoodsGrid],//查询条件form, //查询结果grid
						renderTo : 'T_predeliver-processAbandonGoodsIndex-body'
					});
		});