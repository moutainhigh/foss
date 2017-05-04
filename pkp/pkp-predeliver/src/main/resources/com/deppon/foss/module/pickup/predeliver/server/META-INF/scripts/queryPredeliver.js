/**
 * 
 * @param {日期}
 *            date
 * @param {小时}
 *            hours
 * @param {分钟}
 *            minutes
 * @param {秒}
 *            seconds
 * @param {微秒}
 *            milliseconds
 * @return {}
 */
predeliver.queryPredeliver.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

//定义派送信息查询模型
Ext.define('Foss.QueryPredeliver.Model.PredeliverInfoModel', {
	extend: 'Ext.data.Model',
	fields: [
	    { name: 'vehicleNo',type:'string' }, //车牌号       
		{ name: 'vehicleassembleNo',type:'string' }, //配载车次号
		{ name: 'handoverNo',type:'string' }, //交接单号
		{ name: 'deliverQty',type:'string' },  //派送（票数）      
		{ name: 'deliverWeight',type:'string' },  //派送（重量）
		{ name: 'deliverVolume',type:'string' },  //派送（体积）
		{ name: 'origOrgName',type:'string' },  //出发部门
		{ name: 'destOrgName',type:'string' },  //到达部门
		{ name: 'leaveTime',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},  //出发时间        
		{ name: 'arriveTime',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},  //到达时间
		{ name: 'preArriveTime',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			} 
		},  //预计到达时间
		
		
		{ name: 'waybillNo',type:'string' },    //运单号        
		{ name: 'goodsQty' ,type:'string'},  //开单件数
		{ name: 'assembleQty',type:'string' }, //配载件数
		{ name: 'productCode',type:'string' }, // 运输性质
		{ name: 'receiveMethod',type:'string' }, // 提货方式
		{ name: 'distName',type:'string' } //  行政区
		]
});

// 查询条件Form
Ext.define('Foss.predeliver.queryPredeliver.QueryPredeliverForm', {
			extend : 'Ext.form.Panel',
			title :'查询条件',   // 查询条件
			frame : true,
			id:'Foss_predeliver_queryPredeliver_QueryPredeliverForm_Id',
			collapsible : true,
			animCollapse : true,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			defaultType : 'textfield',
			layout : 'column',
			items : [{
				name : 'vehicleNo',
				fieldLabel : '车牌号',   // 车牌号
				//labelWidth : 70,
				columnWidth : .25
			}, {
				name : 'handoverNo',
				fieldLabel : '交接单号',   // 交接单号
				//labelWidth : 70,
				columnWidth : .25
			},{
				name : 'vehicleassembleNo',
				fieldLabel : '车次号',   // 车次号
				//labelWidth : 70,
				columnWidth : .25
			}, {
				fieldLabel : '出发部门',
				name : 'departDeptCode',  
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				salesDepartment : 'Y',
				transferCenter : 'Y',
				airDispatch : 'Y',
				doAirDispatch : 'Y',
				labelWidth : 70,
				columnWidth : .25
			}, {
				xtype : 'rangeDateField',
				fieldId : 'predeliver_queryPredeliver_preArriveTime',
				fieldLabel :'预计到达时间',   // 预计到达时间
				dateType : 'datetimefield_date97',
				fromName : 'preArriveTimeBegin',
				toName : 'preArriveTimeEnd',
				fromValue : Ext.Date.format(predeliver.queryPredeliver.getDate(
								new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
				toValue : Ext.Date.format(predeliver.queryPredeliver.getDate(
								new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : false,
				editable : false
			}, {
				xtype : 'rangeDateField',
				fieldId : 'predeliver_queryPredeliver_arriveTime',
				fieldLabel : '到达时间', //到达时间
				dateType : 'datetimefield_date97',
				fromName : 'arriveTimeBegin',
				toName : 'arriveTimeEnd',
				//fromValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
				//toValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : false,
				editable : false
			},{
				xtype : 'rangeDateField',
				fieldId : 'predeliver_queryPredeliver_leaveTime',
				fieldLabel : '出发时间', //出发时间
				dateType : 'datetimefield_date97',
				fromName : 'leaveTimeBegin',
				toName : 'leaveTimeEnd',
				//fromValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
				//toValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : false,
				editable : false
			},{
				xtype : 'rangeDateField',
				fieldId : 'predeliver_queryPredeliver_inStockTime',
				fieldLabel : '入库时间', //入库时间IN_STOCK_TIME
				dateType : 'datetimefield_date97',
				fromName : 'inStockTimeBegin',
				toName : 'inStockTimeEnd',
				//fromValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
				//toValue : Ext.Date.format(predeliver.deliverbill.getDate(
						//new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : false,
				editable : false
			}/***隐藏域***/
			,{
				fieldLabel:'清单类型',	//清单类型
				hidden:true,
				name:'billType'
			},{
				border : false,
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaults : {
					margin : '5 0 5 0'
				},
				items : [{
					xtype : 'button',
					columnWidth : .08,
					text :'重置',  // 重置
					handler : function() {
						this.up('form').getForm().reset();

						//predeliver.deliverbill.queryDeliverbillForm.getForm()
								//.findField('status').select('');

						this.up('form').getForm().setValues({
							'preArriveTimeBegin' : Ext.Date.format(
									predeliver.queryPredeliver.getDate(new Date(),
											0, 0, 0, 0), 'Y-m-d H:i:s'),
							'preArriveTimeEnd' : Ext.Date.format(
									predeliver.queryPredeliver.getDate(new Date(),
											23, 59, 59, 999), 'Y-m-d H:i:s')
						});
					}
				}, {
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : '查询',  // 查询
					handler : function() {
						if (predeliver.queryPredeliver.mainTabPanel.getActiveTab().itemId == "temporary") {
							predeliver.queryPredeliver.pagingBar.moveFirst();
						}else {
							predeliver.queryPredeliver.pagingBarWaybill.moveFirst();
						}
					}
				}]
			}]
		});


//创建一个查询货量store
Ext.define('Foss.PredeliverInfo.Store.PredeliverInfoStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.QueryPredeliver.Model.PredeliverInfoModel',
	//默认每页数据大小
	pageSize:20,
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
			url:predeliver.realPath('queryPredeliver.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.sendHandoverInfoDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			
			
				var queryParams = predeliver.queryPredeliver.queryPredeliverForm.getValues();

				var form = predeliver.queryPredeliver.queryPredeliverForm.getForm();
				var handoverNo = form.getValues().handoverNo;
				var vehicleassembleNo = form.getValues().vehicleassembleNo;
				//交接单为空再进行判断
				if(Ext.isEmpty(handoverNo)&&Ext.isEmpty(vehicleassembleNo)) {
					var preArriveTimeBegin = form.getValues().preArriveTimeBegin;
					var preArriveTimeEnd = form.getValues().preArriveTimeEnd;
					var result = Ext.Date.parse(preArriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(preArriveTimeBegin,'Y-m-d H:i:s');
					if(result / (24 * 60 * 60 * 1000) >= 3){
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
					if ((!Ext.isEmpty(preArriveTimeBegin) && Ext.isEmpty(preArriveTimeEnd))||(Ext.isEmpty(preArriveTimeBegin) && !Ext.isEmpty(preArriveTimeEnd))) {
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
					var arriveTimeBegin = form.getValues().arriveTimeBegin;
					var arriveTimeEnd = form.getValues().arriveTimeEnd;
					if (!Ext.isEmpty(arriveTimeBegin) && !Ext.isEmpty(arriveTimeEnd)) {
						var result1 = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');
						if(result1 / (24 * 60 * 60 * 1000) >= 3){
							Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
							return false;
						}
					}
					if ((!Ext.isEmpty(arriveTimeBegin) && Ext.isEmpty(arriveTimeEnd))||(Ext.isEmpty(arriveTimeBegin) && !Ext.isEmpty(arriveTimeEnd))) {
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
					var leaveTimeBegin = form.getValues().leaveTimeBegin;
					var leaveTimeEnd = form.getValues().leaveTimeEnd;
					if (!Ext.isEmpty(leaveTimeBegin) && !Ext.isEmpty(leaveTimeEnd)) {
						var result2 = Ext.Date.parse(leaveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(leaveTimeBegin,'Y-m-d H:i:s');
						if(result2 / (24 * 60 * 60 * 1000) >= 3){
							Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
							return false;
						}
					}
					if ((!Ext.isEmpty(leaveTimeBegin) && Ext.isEmpty(leaveTimeEnd))||(Ext.isEmpty(leaveTimeBegin) && !Ext.isEmpty(leaveTimeEnd))) {
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
					var inStockTimeBegin = form.getValues().inStockTimeBegin;
					var inStockTimeEnd = form.getValues().inStockTimeEnd;
					if (!Ext.isEmpty(inStockTimeBegin) && !Ext.isEmpty(inStockTimeEnd)) {
						var result3 = Ext.Date.parse(inStockTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(inStockTimeBegin,'Y-m-d H:i:s');
						if(result3 / (24 * 60 * 60 * 1000) >= 3){
							Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
							return false;
						}
					}
					if ((!Ext.isEmpty(inStockTimeBegin) && Ext.isEmpty(inStockTimeEnd))||(Ext.isEmpty(inStockTimeBegin) && !Ext.isEmpty(inStockTimeEnd))) {
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
					var vehicleNo = form.getValues().vehicleNo;
					var departDeptCode = form.getValues().departDeptCode;
					if (!Ext.isEmpty(vehicleNo)) {
						if(Ext.isEmpty(preArriveTimeBegin)&&Ext.isEmpty(preArriveTimeEnd)&&Ext.isEmpty(arriveTimeBegin)&&Ext.isEmpty(arriveTimeEnd)&&Ext.isEmpty(leaveTimeBegin)&&Ext.isEmpty(leaveTimeEnd)){
							Ext.ux.Toast.msg('提示', '"车牌号"至少需与"预计到达时间"、"出发时间"、"到达时间"其中一个组合查询！', 'error', 3000);
							return false;
						}
					}
					if (!Ext.isEmpty(departDeptCode)) {
						if(Ext.isEmpty(preArriveTimeBegin)&&Ext.isEmpty(preArriveTimeEnd)&&Ext.isEmpty(arriveTimeBegin)&&Ext.isEmpty(arriveTimeEnd)&&Ext.isEmpty(leaveTimeBegin)&&Ext.isEmpty(leaveTimeEnd)){
							Ext.ux.Toast.msg('提示', '"出发部门"至少需与"预计到达时间"、"出发时间"、"到达时间"其中一个组合查询！', 'error', 3000);
							return false;
						}
					}
				}
				if(Ext.isEmpty(handoverNo)&&Ext.isEmpty(vehicleNo)&&
						Ext.isEmpty(departDeptCode)&&Ext.isEmpty(preArriveTimeBegin)&&
						Ext.isEmpty(arriveTimeBegin)&&Ext.isEmpty(leaveTimeBegin)&&
						Ext.isEmpty(inStockTimeBegin)&&Ext.isEmpty(vehicleassembleNo)) {
					Ext.ux.Toast.msg('提示', '查询条件不能全为空！', 'error', 3000);
					return false;
				}
				
				
				
				if (!form.isValid()){
					return false;
				}
				var queryType;
				if (predeliver.queryPredeliver.mainTabPanel.getActiveTab().itemId == "temporary") {
					//form.findField('billType').setValue('HANDOVER_NO');
					queryType = 'HANDOVER_NO';
				}else {
					//form.findField('billType').setValue('WAY_BILL');
					queryType = 'WAY_BILL';
				}
			Ext.apply(operation, {
				params : {					
						'vo.sendInfoSearchDto.vehicleNo': queryParams.vehicleNo,
				    	'vo.sendInfoSearchDto.handoverNo': queryParams.handoverNo,
				    	'vo.sendInfoSearchDto.vehicleassembleNo': queryParams.vehicleassembleNo,
				    	'vo.sendInfoSearchDto.departDeptCode': queryParams.departDeptCode,
				    	'vo.sendInfoSearchDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
				    	'vo.sendInfoSearchDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
				    	'vo.sendInfoSearchDto.arriveTimeBegin': queryParams.arriveTimeBegin,
				    	'vo.sendInfoSearchDto.arriveTimeEnd': queryParams.arriveTimeEnd,
				    	'vo.sendInfoSearchDto.leaveTimeBegin': queryParams.leaveTimeBegin,
				    	'vo.sendInfoSearchDto.leaveTimeEnd': queryParams.leaveTimeEnd,
				    	'vo.sendInfoSearchDto.inStockTimeBegin': queryParams.inStockTimeBegin,
				    	'vo.sendInfoSearchDto.inStockTimeEnd': queryParams.inStockTimeEnd,
				    	'vo.sendInfoSearchDto.billType':queryType
					}
			});	
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if (predeliver.queryPredeliver.mainTabPanel.getActiveTab().itemId == "temporary") {
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotal_ID').setValue(data.vo.sendInfoSearchDto.deliverQtyTotal);
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotal_ID').setValue(data.vo.sendInfoSearchDto.deliverWeightTotal);
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotal_ID').setValue(data.vo.sendInfoSearchDto.deliverVolumeTotal);
			}else {
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotalW_ID').setValue(data.vo.sendInfoSearchDto.deliverQtyTotal);
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotalW_ID').setValue(data.vo.sendInfoSearchDto.deliverWeightTotal);
				Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotalW_ID').setValue(data.vo.sendInfoSearchDto.deliverVolumeTotal);
			}
		}
	}
});


//交接单
Ext.define('Foss.predeliver.queryPredeliver.QueryPredeliverGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_queryPredeliver_QueryPredeliverGrid_Id',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:'查询结果为空',//查询结果为空
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	collapsible: true,
	animCollapse: true,
	store: null,
	
	
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
	     //Ext.create('Ext.grid.RowNumberer'),
	    {
			//字段标题
			header: '车牌号',//车牌号
			//关联model中的字段名
			dataIndex: 'vehicleNo',
			width:80				
		},{ 
			//字段标题
			header: '配载车次号',//配载车次号
			//关联model中的字段名
			dataIndex: 'vehicleassembleNo',
			width:130
		},{
			//字段标题
			header: '交接单号',//交接单号
			//关联model中的字段名
			dataIndex: 'handoverNo', 
			width:90
		},{
			//字段标题
			header: '派送（票数）',//派送（票数）   
			//关联model中的字段名
			dataIndex: 'deliverQty', 
			width:90
		},{
			//字段标题
			header: '派送（重量）',//派送（重量）  
			//关联model中的字段名
			dataIndex: 'deliverWeight', 
			width:90
		},{
			//字段标题
			header: '派送（体积）',//派送（体积）
			//关联model中的字段名
			dataIndex: 'deliverVolume', 
			width:90
		},{
			//字段标题
			header: '出发部门',//出发部门 
			//关联model中的字段名
			dataIndex: 'origOrgName', 
			width:100
		},{
			//字段标题
			header: '到达部门',//到达部门
			//关联model中的字段名
			dataIndex: 'destOrgName', 
			width:100
		},{
			//字段标题
			header: '出发时间',//出发时间 
			//关联model中的字段名
			dataIndex: 'leaveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '到达时间',//到达时间
			//关联model中的字段名
			dataIndex: 'arriveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '预计到达时间',//预计到达时间 
			//关联model中的字段名
			dataIndex: 'preArriveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.PredeliverInfo.Store.PredeliverInfoStore');
			//添加列表按钮及分页工具条
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',		    	
				defaults:{
				margin:'0 0 5 3'
				},		
				items: [{
					xtype : 'container',
					border : false,
					columnWidth : .9,
					html : '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					name:'predeliverInfo',
					columnWidth:.1,
					text:'导出',//导出,
					handler : function(){
						var queryPredeliverForm = predeliver.queryPredeliver.queryPredeliverForm;
    					var form = predeliver.queryPredeliver.queryPredeliverForm.getForm();	
						if (queryPredeliverForm != null) {
							var queryParams = queryPredeliverForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}	
							//获取查询出来的异常信息
							var predeliverGridStore = Ext.getCmp('Foss_queryPredeliver_QueryPredeliverGrid_Id').store;	
							//若异常信息不为空
							if(predeliverGridStore.getCount()!=0){
								Ext.Ajax.request({
									url:predeliver.realPath('exportPredeliverInfo.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : {
										'sendInfoQueryVo.sendInfoSearchDto.vehicleNo': queryParams.vehicleNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.handoverNo': queryParams.handoverNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.vehicleassembleNo': queryParams.vehicleassembleNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.departDeptCode': queryParams.departDeptCode,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.arriveTimeBegin': queryParams.arriveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.arriveTimeEnd': queryParams.arriveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeBegin': queryParams.leaveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeEnd': queryParams.leaveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.inStockTimeBegin': queryParams.inStockTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.inStockTimeEnd': queryParams.inStockTimeEnd
									},
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg('提示信息','查询结果为空，不能导出！', 'error', 3000);
							}
						}
					}
				}]
				},{
					xtype: 'toolbar',
					dock: 'bottom',
					layout:'column',
					defaults:{
						margin:'0 0 5 3',
						allowBlank:true
					},		
					items: [{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverQtyTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotal_ID',
						columnWidth:.2,
						labelWidth:140,
						fieldLabel: '合计：派送（票数）'//派送（票数）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverWeightTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotal_ID',
						columnWidth:.2,
						fieldLabel:'派送（重量）'//派送（重量）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverVolumeTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotal_ID',
						columnWidth:.2,
						fieldLabel: '派送（体积）'//总件数
					}]
			}],
			//自定义分页控件
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 20,
				maximumSize : 100,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['20', 20], ['50', 50],  ['100', 100]]
				})
			});
			predeliver.queryPredeliver.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
	});

//运单
Ext.define('Foss.predeliver.queryPredeliver.QueryWaybillGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	id:'Foss_queryPredeliver_QueryWaybillGrid_Id',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:'查询结果为空',//查询结果为空
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	hideTitle:true,
	collapsible: true,
	animCollapse: true,
	store: null,
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
	     //Ext.create('Ext.grid.RowNumberer'),
	    {
			//字段标题
			header: '运单号',//运单号   
			//关联model中的字段名
			dataIndex: 'waybillNo',
			width:90				
		},{ 
			//字段标题
			header: '配载车次号',//配载车次号
			//关联model中的字段名
			dataIndex: 'vehicleassembleNo',
			width:130
		},{
			//字段标题
			header: '交接单号',//交接单号
			//关联model中的字段名
			dataIndex: 'handoverNo', 
			width:90
		},{
			//字段标题
			header: '开单件数',//开单件数
			//关联model中的字段名
			dataIndex: 'goodsQty', 
			width:90
		},{
			//字段标题
			header: '配载件数',//配载件数
			//关联model中的字段名
			dataIndex: 'assembleQty', 
			width:90
		},{
			//字段标题
			header: '派送（重量）',//派送（重量）
			//关联model中的字段名
			dataIndex: 'deliverWeight', 
			width:90
		},{
			//字段标题
			header: '派送（体积）',//派送（体积）
			//关联model中的字段名
			dataIndex: 'deliverVolume', 
			width:90
		},{
			//字段标题
			header: '运输性质',//运输性质 
			//关联model中的字段名
			dataIndex: 'productCode', 
			width:90
		},{
			//字段标题
			header: '提货方式',//提货方式
			//关联model中的字段名
			dataIndex: 'receiveMethod', 
			width:90,
			renderer : function(value){
				if(value == 'DELIVER'){
					return '免费送货';
				}else if(value == 'DELIVER_INGA'){
					return '送货进仓';
				}else if(value == 'DELIVER_NOUP'){
					return '送货(不含上楼)';
				}else if(value == 'DELIVER_UP'){
					return '送货上楼';
				}else if(value=='INNER_PICKUP'){
					return '内部带货自提';
				}else if(value=='LARGE_DELIVER_UP'){
					return '大件上楼';
				}else if(value=='SELF_PICKUP'){
					return '自提';
				}
				return value;
			}
		},{
			//字段标题
			header: '行政区',//行政区
			//关联model中的字段名
			dataIndex: 'distName', 
			width:90
		},{
			//字段标题
			header: '出发时间',//出发时间 
			//关联model中的字段名
			dataIndex: 'leaveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '到达时间',//到达时间
			//关联model中的字段名
			dataIndex: 'arriveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '预计到达时间',//预计到达时间 
			//关联model中的字段名
			dataIndex: 'preArriveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.PredeliverInfo.Store.PredeliverInfoStore');
			//添加列表按钮及分页工具条
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',		    	
				defaults:{
				margin:'0 0 5 3'
				},		
				items: [{
					xtype : 'container',
					border : false,
					columnWidth : .9,
					html : '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					name:'predeliverInfo',
					columnWidth:.1,
					text:'导出',//导出,
					handler : function(){
						var queryPredeliverForm = predeliver.queryPredeliver.queryPredeliverForm;
    					var form = predeliver.queryPredeliver.queryPredeliverForm.getForm();	
						if (queryPredeliverForm != null) {
							var queryParams = queryPredeliverForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}	
							//获取查询出来的异常信息
							var waybillGridStore = Ext.getCmp('Foss_queryPredeliver_QueryWaybillGrid_Id').store;	
							//若异常信息不为空
							if(waybillGridStore.getCount()!=0){
								Ext.Ajax.request({
									url:predeliver.realPath('exportPredeliverWaybillInfo.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : {
										'sendInfoQueryVo.sendInfoSearchDto.vehicleNo': queryParams.vehicleNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.handoverNo': queryParams.handoverNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.vehicleassembleNo': queryParams.vehicleassembleNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.departDeptCode': queryParams.departDeptCode,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.arriveTimeBegin': queryParams.arriveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.arriveTimeEnd': queryParams.arriveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeBegin': queryParams.leaveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeEnd': queryParams.leaveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.inStockTimeBegin': queryParams.inStockTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.inStockTimeEnd': queryParams.inStockTimeEnd
									},
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg('提示信息','查询结果为空，不能导出！', 'error', 3000);
							}
						}
					}
				}]
				},{
					xtype: 'toolbar',
					dock: 'bottom',
					layout:'column',
					defaults:{
						margin:'0 0 5 3',
						allowBlank:true
					},		
					items: [{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverQtyTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotalW_ID',
						columnWidth:.2,
						labelWidth:140,
						fieldLabel: '合计：派送（票数）'//派送（票数）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverWeightTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotalW_ID',
						columnWidth:.2,
						fieldLabel:'派送（重量）'//派送（重量）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverVolumeTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotalW_ID',
						columnWidth:.2,
						fieldLabel: '派送（体积）'//总件数
					}]
			}],
			//自定义分页控件
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 20,
				maximumSize : 100,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['20', 20], ['50', 50],  ['100', 100]]
				})
			});
			predeliver.queryPredeliver.pagingBarWaybill = me.bbar;
			me.callParent([cfg]);
		}
	});

//快递form
Ext.define('Foss.predeliver.queryPredeliver.QueryPredeliverExpressForm', {
	extend : 'Ext.form.Panel',
	title :'查询条件',   // 查询条件
	frame : true,
	id:'Foss_predeliver_queryPredeliver_QueryPredeliverExpressForm_ID',
	collapsible : true,
	animCollapse : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 90
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name : 'vehicleNo',
		fieldLabel : '车牌号',   // 车牌号
		labelWidth : 70,
		columnWidth : .25
	}, {
		name : 'handoverNo',
		fieldLabel : '交接单号',   // 交接单号
		//labelWidth : 70,
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		fieldId : 'predeliver_queryPredeliver_preArriveTime_express',
		fieldLabel :'预计到达时间',   // 预计到达时间
		dateType : 'datetimefield_date97',
		fromName : 'preArriveTimeBegin',
		toName : 'preArriveTimeEnd',
		fromValue : Ext.Date.format(predeliver.queryPredeliver.getDate(
						new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(predeliver.queryPredeliver.getDate(
						new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
		columnWidth : .5,
		disallowBlank : false,
		editable : false
	}, {
		fieldLabel : '出发部门',
		name : 'departDeptCode',  
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		labelWidth : 70,
		listWidth:.5,
		columnWidth : .5
	},{
		xtype : 'rangeDateField',
		fieldId : 'predeliver_queryPredeliver_leaveTime_express',
		fieldLabel : '出发时间', //出发时间
		dateType : 'datetimefield_date97',
		fromName : 'leaveTimeBegin',
		toName : 'leaveTimeEnd',
		//fromValue : Ext.Date.format(predeliver.deliverbill.getDate(
				//new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
		//toValue : Ext.Date.format(predeliver.deliverbill.getDate(
				//new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
		columnWidth : .5,
		disallowBlank : false,
		editable : false
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth : .08,
			text :'重置',  // 重置
			handler : function() {
				this.up('form').getForm().reset();

				//predeliver.deliverbill.queryDeliverbillForm.getForm()
						//.findField('status').select('');

				this.up('form').getForm().setValues({
					'preArriveTimeBegin' : Ext.Date.format(
							predeliver.queryPredeliver.getDate(new Date(),
									0, 0, 0, 0), 'Y-m-d H:i:s'),
					'preArriveTimeEnd' : Ext.Date.format(
							predeliver.queryPredeliver.getDate(new Date(),
									23, 59, 59, 999), 'Y-m-d H:i:s')
				});
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',  // 查询
			handler : function() {
				predeliver.queryPredeliver.QueryWaybillExpressGrid.store.load();
			}
		}]
	}]
});

//定义派送信息查询模型
Ext.define('Foss.QueryPredeliver.Model.ExpressWaybillInfoModel', {
	extend: 'Ext.data.Model',
	fields: [
	    { name: 'waybillNo',type:'string' },    //运单号      
		{ name: 'handoverBillNo',type:'string' }, //交接单号
		{ name: 'billing',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},  // 开单时间
		{ name: 'waybillQty',type:'string' },  //票数
		{ name: 'weight',type:'string' },  //重量
		{ name: 'volume',type:'string' },  //体积
		{ name: 'transportType',type:'string' },  // 产品类型 
		{ name: 'arriveDistName',type:'string' },  //到达区名称
		{ name: 'departTime',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},  //出发时间
		{ name: 'arriveTime',type:'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			} 
		} //预计到达时间
		]
});
//快递运单store
Ext.define('Foss.PredeliverInfo.Store.ExpressWaybillInfoStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.QueryPredeliver.Model.ExpressWaybillInfoModel',
	//默认每页数据大小
	pageSize:20,
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
			url:predeliver.realPath('queryPredeliver.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.billInfoResponses',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
				var queryParams = predeliver.queryPredeliver.queryPredeliverExpressForm.getValues();
				var form =        predeliver.queryPredeliver.queryPredeliverExpressForm.getForm();
				var handoverNo = form.getValues().handoverNo;
				var preArriveTimeBegin = form.getValues().preArriveTimeBegin;
				var preArriveTimeEnd = form.getValues().preArriveTimeEnd;
				var result = Ext.Date.parse(preArriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(preArriveTimeBegin,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >= 3){
					Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
					return false;
				}
				if ((!Ext.isEmpty(preArriveTimeBegin) && Ext.isEmpty(preArriveTimeEnd))||(Ext.isEmpty(preArriveTimeBegin) && !Ext.isEmpty(preArriveTimeEnd))) {
					Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
					return false;
				}
				var leaveTimeBegin = form.getValues().leaveTimeBegin;
				var leaveTimeEnd = form.getValues().leaveTimeEnd;
				if (!Ext.isEmpty(leaveTimeBegin) && !Ext.isEmpty(leaveTimeEnd)) {
					var result2 = Ext.Date.parse(leaveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(leaveTimeBegin,'Y-m-d H:i:s');
					if(result2 / (24 * 60 * 60 * 1000) >= 3){
						Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
						return false;
					}
				}
				if ((!Ext.isEmpty(leaveTimeBegin) && Ext.isEmpty(leaveTimeEnd))||(Ext.isEmpty(leaveTimeBegin) && !Ext.isEmpty(leaveTimeEnd))) {
					Ext.ux.Toast.msg('提示', '起止日期相隔不能超过3天！', 'error', 3000);
					return false;
				}
				var vehicleNo = form.getValues().vehicleNo;
				var departDeptCode = form.getValues().departDeptCode;
				if (!Ext.isEmpty(vehicleNo)) {
					if(Ext.isEmpty(preArriveTimeBegin)&&Ext.isEmpty(preArriveTimeEnd)&&Ext.isEmpty(leaveTimeBegin)&&Ext.isEmpty(leaveTimeEnd)){
						Ext.ux.Toast.msg('提示', '"车牌号"至少需与"预计到达时间"、"出发时间"其中一个组合查询！', 'error', 3000);
						return false;
					}
				}
				if (!Ext.isEmpty(departDeptCode)) {
					if(Ext.isEmpty(preArriveTimeBegin)&&Ext.isEmpty(preArriveTimeEnd)&&Ext.isEmpty(leaveTimeBegin)&&Ext.isEmpty(leaveTimeEnd)){
						Ext.ux.Toast.msg('提示', '"出发部门"至少需与"预计到达时间"、"出发时间"其中一个组合查询！', 'error', 3000);
						return false;
					}
				}
				if(Ext.isEmpty(handoverNo)&&Ext.isEmpty(vehicleNo)&&
						Ext.isEmpty(departDeptCode)&&Ext.isEmpty(preArriveTimeBegin)&&
						Ext.isEmpty(leaveTimeBegin)) {
					Ext.ux.Toast.msg('提示', '查询条件不能全为空！', 'error', 3000);
					return false;
				}
				if (!form.isValid()){
					return false;
				}
			Ext.apply(operation, {
				params : {					
						'vo.sendInfoSearchDto.vehicleNo': queryParams.vehicleNo,
				    	'vo.sendInfoSearchDto.handoverNo': queryParams.handoverNo,
				    	'vo.sendInfoSearchDto.departDeptCode': queryParams.departDeptCode,
				    	'vo.sendInfoSearchDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
				    	'vo.sendInfoSearchDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
				    	'vo.sendInfoSearchDto.leaveTimeBegin': queryParams.leaveTimeBegin,
				    	'vo.sendInfoSearchDto.leaveTimeEnd': queryParams.leaveTimeEnd,
				    	'vo.sendInfoSearchDto.billType':'EXPRESS_WAY_BILL'
					}
			});	
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotalW_Express_ID').setValue(data.vo.sendInfoSearchDto.deliverQtyTotal);
			Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotalW_Express_ID').setValue(data.vo.sendInfoSearchDto.deliverWeightTotal);
			Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotalW_Express_ID').setValue(data.vo.sendInfoSearchDto.deliverVolumeTotal);
		}
	}
});
//快递运单
Ext.define('Foss.predeliver.queryPredeliver.QueryWaybillExpressGrid', {
	extend:'Ext.grid.Panel',
	title : '运单',
	//增加表格列的分割线
	columnLines: true,
	id:'Foss_queryPredeliver_QueryWaybillGrid_Express_Id',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:'查询结果为空',//查询结果为空
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	hideTitle:true,
	collapsible: true,
	animCollapse: true,
	store: null,
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
	     //Ext.create('Ext.grid.RowNumberer'),
	    {
			//字段标题
			header: '运单号',//运单号   
			//关联model中的字段名
			dataIndex: 'waybillNo',
			width:90				
		},{
			//字段标题
			header: '交接单号',//交接单号
			//关联model中的字段名
			dataIndex: 'handoverBillNo', 
			width:90
		},{ 
			//字段标题
			header: '开单时间',//开单时间
			//关联model中的字段名
			dataIndex: 'billing',
			width:130,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '开单件数',//开单件数
			//关联model中的字段名
			dataIndex: 'waybillQty', 
			width:90
		},{
			//字段标题
			header: '运单（重量）',//运单（重量）
			//关联model中的字段名
			dataIndex: 'weight', 
			width:90
		},{
			//字段标题
			header: '运单（体积）',//运单（体积）
			//关联model中的字段名
			dataIndex: 'volume', 
			width:90
		},{
			//字段标题
			header: '产品类型',//产品类型 
			//关联model中的字段名
			dataIndex: 'transportType', 
			width:90
		},{
			//字段标题
			header: '行政区',//行政区
			//关联model中的字段名
			dataIndex: 'arriveDistName', 
			width:90
		},{
			//字段标题
			header: '出发时间',//出发时间 
			//关联model中的字段名
			dataIndex: 'departTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			//字段标题
			header: '预计到达时间',//预计到达时间 
			//关联model中的字段名
			dataIndex: 'arriveTime', 
			width:135,
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.PredeliverInfo.Store.ExpressWaybillInfoStore');
			//添加列表按钮及分页工具条
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',		    	
				defaults:{
				margin:'0 0 5 3'
				},		
				items: [{
					xtype : 'container',
					border : false,
					columnWidth : .9,
					html : '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					name:'predeliverInfo',
					columnWidth:.1,
					text:'导出',//导出,
					handler : function(){
						var queryPredeliverForm = predeliver.queryPredeliver.queryPredeliverExpressForm;
    					var form = queryPredeliverForm.getForm();	
						if (queryPredeliverForm != null) {
							var queryParams = queryPredeliverForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}	
							//获取查询出来的异常信息
							var waybillGridStore = Ext.getCmp('Foss_queryPredeliver_QueryWaybillGrid_Express_Id').store;	
							//若异常信息不为空
							if(waybillGridStore.getCount()!=0){
								Ext.Ajax.request({
									url:predeliver.realPath('exportExpressWaybillInfo.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : {
										'sendInfoQueryVo.sendInfoSearchDto.vehicleNo': queryParams.vehicleNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.handoverNo': queryParams.handoverNo,
			    						'sendInfoQueryVo.sendInfoSearchDto.departDeptCode': queryParams.departDeptCode,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeBegin': queryParams.preArriveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.preArriveTimeEnd': queryParams.preArriveTimeEnd,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeBegin': queryParams.leaveTimeBegin,
			    						'sendInfoQueryVo.sendInfoSearchDto.leaveTimeEnd': queryParams.leaveTimeEnd
									},
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg('提示信息','查询结果为空，不能导出！', 'error', 3000);
							}
						}
					}
				}]
				},{
					xtype: 'toolbar',
					dock: 'bottom',
					layout:'column',
					defaults:{
						margin:'0 0 5 3',
						allowBlank:true
					},		
					items: [{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverQtyTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotalW_Express_ID',
						columnWidth:.2,
						labelWidth:140,
						fieldLabel: '合计：派送（票数）'//派送（票数）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverWeightTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotalW_Express_ID',
						columnWidth:.2,
						fieldLabel:'派送（重量）'//派送（重量）
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						name:'deliverVolumeTotal',
						id:'Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotalW_Express_ID',
						columnWidth:.2,
						fieldLabel: '派送（体积）'//总件数
					}]
			}],
			//自定义分页控件
		/*	me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 20,
				maximumSize : 100,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['20', 20], ['50', 50],  ['100', 100]]
				})
			});*/
			me.bbar=[];
			predeliver.queryPredeliver.pagingBarExpressWaybillInfo = me.bbar;
			me.callParent([cfg]);
		}
	});

Ext.define('Foss.predeliver.queryPredeliver.QueryTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
				tabConfig : {
					width : 100
				},
				itemId : 'ltl',
				title : '零担',//零担
				items : Ext.create('Foss.predeliver.queryPredeliver.QueryPredeliverForm')
			}, {
				tabConfig : {
					width : 100
				},
				itemId : 'express',
				title : '快递',//快递
				items :Ext.create('Foss.predeliver.queryPredeliver.QueryPredeliverExpressForm')
			}],
			listeners : {
				'tabchange' : function(tabPanel,newCard,oldCard,eOpts ){
					var itemId=newCard.itemId;
					var mainTabPanel=Ext.getCmp('Foss_predeliver_queryPredeliver_MainTabPanel_ID');
					if("ltl"==itemId){
						predeliver.queryPredeliver.QueryWaybillExpressGrid.hide();
						predeliver.queryPredeliver.mainTabPanel.show();
					}else if("express"==itemId){
						predeliver.queryPredeliver.QueryWaybillExpressGrid.show();
						predeliver.queryPredeliver.mainTabPanel.hide();
					}
				}
			}
});

Ext.define('Foss.predeliver.queryPredeliver.MainTabPanel', {
	extend : 'Ext.tab.Panel',
	id:'Foss_predeliver_queryPredeliver_MainTabPanel_ID',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
				tabConfig : {
					width : 100
				},
				itemId : 'temporary',
				title : '车次/交接单',// 车次/交接单页签
				items : Ext.create('Foss.predeliver.queryPredeliver.QueryPredeliverGrid')
			}, {
				tabConfig : {
					width : 100
				},
				itemId : 'forever',
				title : '运单',// 运单页签
				items : Ext.create('Foss.predeliver.queryPredeliver.QueryWaybillGrid')
			}],
			listeners : {
				'tabchange' : function(tabPanel,newCard,oldCard,eOpts ){
					Ext.getCmp('Foss_queryPredeliver_QueryWaybillGrid_Id').store.removeAll();
					Ext.getCmp('Foss_queryPredeliver_QueryPredeliverGrid_Id').store.removeAll();
					predeliver.queryPredeliver.pagingBar.onLoad();
					predeliver.queryPredeliver.pagingBarWaybill.onLoad();
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotal_ID').setValue('');
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotal_ID').setValue('');
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotal_ID').setValue('');
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverQtyTotalW_ID').setValue('');
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverWeightTotalW_ID').setValue('');
					Ext.getCmp('Foss_QueryPredeliver_QueryPredeliverGrid_deliverVolumeTotalW_ID').setValue('');
				}
			}
});

//predeliver.queryPredeliver.queryResultGrid= Ext.create('Foss.predeliver.queryPredeliver.QueryPredeliverGrid');
//predeliver.queryPredeliver.queryResultWaybillGrid= Ext.create('Foss.predeliver.queryPredeliver.QueryWaybillGrid');

Ext.onReady(function() {
	Ext.QuickTips.init();
	//var queryResultGrid =predeliver.queryPredeliver.queryResultGrid;
	//var queryResultWaybillGrid =predeliver.queryPredeliver.queryResultWaybillGrid;
	
/**2016年12月9日10:45:16 注释
 * 	predeliver.queryPredeliver.queryPredeliverForm = Ext
			.create('Foss.predeliver.queryPredeliver.QueryPredeliverForm');*/
	Foss.predeliver.queryPredeliver.QueryTabPanel = Ext
	.create('Foss.predeliver.queryPredeliver.QueryTabPanel');
	/*****************/
	predeliver.queryPredeliver.mainTabPanel = Ext
	.create('Foss.predeliver.queryPredeliver.MainTabPanel');
	predeliver.queryPredeliver.QueryWaybillExpressGrid = Ext
	.create('Foss.predeliver.queryPredeliver.QueryWaybillExpressGrid');
	predeliver.queryPredeliver.QueryWaybillExpressGrid.hide();
	predeliver.queryPredeliver.queryPredeliverForm =Ext.getCmp('Foss_predeliver_queryPredeliver_QueryPredeliverForm_Id');
	predeliver.queryPredeliver.queryPredeliverExpressForm =Ext.getCmp('Foss_predeliver_queryPredeliver_QueryPredeliverExpressForm_ID');
	Ext.create('Ext.panel.Panel',{
		id: 'T_predeliver-queryPredeliverIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
//		getQueryResultGrid: function(){
//			return queryResultGrid;
//		},
//		getQueryResultWaybillGrid: function(){
//			return queryResultWaybillGrid;
//		},
		/**2016年12月9日10:45:31 注释
		 * items: [predeliver.queryPredeliver.queryPredeliverForm,predeliver.queryPredeliver.mainTabPanel],*/
		items: [Foss.predeliver.queryPredeliver.QueryTabPanel,predeliver.queryPredeliver.mainTabPanel,predeliver.queryPredeliver.QueryWaybillExpressGrid],
		/*****************/
		renderTo: 'T_predeliver-queryPredeliverIndex-body'
	})
});