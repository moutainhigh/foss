/**-----------------------------------------------QueryairfreightbindTrayModel-----------------------------------------------------*/
//协议航班model
Ext.define('Foss.airfreight.airProtocolFlight.QueryProtocolFlightModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'flightNo',//航班号
		type : 'string'
	}, {
		name : 'departCity',//始发站
		type : 'string'
	},{
		name : 'arriveCity',//目的站
		type : 'string'
	}, 
	{
		name : 'currMonthGoodsAmount',//当月货量
		type : 'string'
	}, {
		name : 'currTimeOptGoodsAmount',//当日配载货量
		type : 'string'
	}, {
		name : 'currMonthTotalOptGoodsAmount',//本月累计已配载货量
		type : 'string'
	}, {
		name : 'currMonthTimeschedule',//本月时间进度
		type : 'string'
	}, {
		name : 'currMonthGoodschedule',//本月货量进度
		type : 'string'
	},{
		name : 'reDateOptGoodsAmount',//剩余日需货量
		type : 'string'
	}
	]
	
});

///**-----------------------------------------------QueryTrayScanStore-----------------------------------------------------*/
////协议航班store
Ext.define('foss.airfreight.airProtocolFlight.QueryProtocolFlightStore', {
extend : 'Ext.data.Store',
pageSize : 20,
autoLoad: false,
//绑定托盘扫描模型
model : 'Foss.airfreight.airProtocolFlight.QueryProtocolFlightModel',
// 定义一个代理对象
proxy : {
	type : 'ajax',
	timeout: 600000,
	actionMethods:'POST',
	// 请求的url
	url : airfreight.realPath('queryProtocolFlightList.action'),
	// 定义一个读取器
	reader : {
		// 以JSON的方式读取
		type : 'json',
		// 定义读取JSON数据的根对象
		root : 'airProtocolFlightVo.airProtocolFlightList',
		successProperty: 'success',
		totalProperty : 'totalCount'
	}
	
},
constructor : function(config) {
	var me = this, cfg = Ext.apply({}, config);
	me.callParent([ cfg ]);
},
listeners : {
	'beforeload' : function(store, operation, eOpts){
		var queryParams = airfreight.airProtocolFlight.queryForm.getForm().getValues();
		Ext.apply(operation, {
			params : {
				'airProtocolFlightVo.airProtocolFlightQueryDto.flightNo' :queryParams.flightNo,
				'airProtocolFlightVo.airProtocolFlightQueryDto.departCity' :queryParams.departCity,
				'airProtocolFlightVo.airProtocolFlightQueryDto.arriveCity' :queryParams.arriveCity,
				'airProtocolFlightVo.airProtocolFlightQueryDto.currQueryTime' :queryParams.queryEndTime
			}
		});	
	}
}
});

/**-----------------------------------------------queryForm-----------------------------------------------------*/
//定义协议航班查询界面
Ext.define('foss.airfreight.airProtocolFlight.QueryForm', {
	extend : 'Ext.form.Panel',
	title : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.title'),//查询条件
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/ 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [ {
		fieldLabel : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.flightNo'),//航班号
		name : 'flightNo',
		xtype:'commonflightselector',
		id:'commonflightselector', 
		isAgreementFlight:'Y',
		airDispatchCodes:airfreight.airProtocolFlight.orgCodes//'W31000206090611'
	},{
		fieldLabel : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.departCity'),//始发站
		name : 'departCity',
		xtype:'commonairporwithcitynametselector',
		valueField : 'cityName',// 值
		displayField : 'cityName'// 显示名称
	}, {
		fieldLabel : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.arriveCity'),//目的站
		name : 'arriveCity',
		xtype:'commonairporwithcitynametselector',
		valueField : 'cityName',// 值
		displayField : 'cityName'// 显示名称

	}, {
		xtype : 'datetimefield_date97',
		fieldLabel : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.queryEndTime'),//查询截止时间
		id:Ext.Date.format(new Date(),'YmdHis')+'Foss_airfreight_editProtocolFlightPanel_queryEndTimeId',
		time:true,
		allowBlank:false,
		disabled:false,
		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		name:'queryEndTime',
		maxValue:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_airfreight_editProtocolFlightPanel_queryEndTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .30
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
	items : [{
			xtype : 'button',
			columnWidth:.08,
			text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('queryEndTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!airfreight.airProtocolFlight.isPermission('airfreight/airProtocolFlightButton'),
			hidden:!airfreight.airProtocolFlight.isPermission('airfreight/airProtocolFlightButton'),
			text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.queryButtonText'),//'查询'
     		handler : function(opts){
				var grid=airfreight.airProtocolFlight.showProtocolFlightGird;
				var form = this.up('form').getForm();
				if(!form.isValid()){
					return;
				}
				
				var store=grid.getStore( );
				//清楚掉之前的数据
				store.removeAll(false);
				//重新加载数据
			    airfreight.airProtocolFlight.pagingBar.moveFirst();	
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

/**-----------------------------------------------airProtocolFlightGird-----------------------------------------------------*/
//定义协议航班Gird
Ext.define('foss.airfreight.airProtocolFlight.showProtocolFlightGird',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.title'),/*'货量进度列表'*/
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			//货量进度 
			var goodsSchedulF=record.get('currMonthGoodschedule').substring(0,5);
			var goodsSchedul=parseInt(goodsSchedulF);
			//时间进度
			var timeSchedulF=record.get('currMonthTimeschedule').substring(0,5);
			var timeSchedul=parseInt(timeSchedulF);
			
			if(goodsSchedul<timeSchedul){
    			return 'predeliver_notice_customer_row_yellow';
			}
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('foss.airfreight.airProtocolFlight.QueryProtocolFlightStore');
		me.tbar = [{
			xtype : 'button',
			disabled:!airfreight.airProtocolFlight.isPermission('airfreight/exportairProtocolFlightButton'),
			hidden:!airfreight.airProtocolFlight.isPermission('airfreight/exportairProtocolFlightButton'),
			text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.ExportButtonText'),//'导出'
			handler : function(){
					if(!Ext.fly('downloadAttachFileForm')){
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm',
						frm.style.display = 'none',
						document.body.appendChild(frm)
					}
					var form = airfreight.airProtocolFlight.queryForm .getForm();
					var queryParams = form.getValues();
					if(!form.isValid()){
						Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
						return;
					}
					Ext.Ajax.request({
						url : airfreight.realPath('exportProtocolFlightDetailExcel.action'),
						form: Ext.fly('downloadAttachFileForm'),
						timeout:30000,
						method : 'POST',
						params : {
							'airProtocolFlightVo.airProtocolFlightQueryDto.flightNo' :queryParams.flightNo,
							'airProtocolFlightVo.airProtocolFlightQueryDto.departCity' :queryParams.departCity,
							'airProtocolFlightVo.airProtocolFlightQueryDto.arriveCity' :queryParams.arriveCity,
							'airProtocolFlightVo.airProtocolFlightQueryDto.currQueryTime' :queryParams.queryEndTime
						},
						isUpload: true,
						success:function(response){
						
						},
						exception : function(response) {
							top.Ext.MessageBox.alert(airfreight.airProtocolFlight.i18n('foss.airfreight.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
						}
				});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50], ['100', 100]]
			})
		});
	   airfreight.airProtocolFlight.pagingBar = me.bbar;
	   me.callParent([cfg]);
	},
	columns : [
	{
		dataIndex : 'flightNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.flightNo')/*'航班号'*/
	}, {
		dataIndex : 'departCity',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.departCity')/*'始发站'*/
	}, {
		dataIndex : 'arriveCity',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.arriveCity')/*'目的站'*/
	}, {
		dataIndex : 'currMonthGoodsAmount',
		align : 'center',
		width :150,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.currMonthGoodsAmount')/*'本月协议货量(公斤)'*/
	}, {
		dataIndex : 'currTimeOptGoodsAmount',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.currTimeOptGoodsAmount')/*'当日已配载货量（公斤）'*/
	}, {
		dataIndex : 'currMonthTotalOptGoodsAmount',
		align : 'center',
		width : 180,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.currMonthTotalOptGoodsAmount')/*'本月累计已配载货量（公斤）'*/
	}, {
		dataIndex : 'currMonthTimeschedule',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.currMonthTimeschedule')/*'本月时间进度'*/
	}, {
		dataIndex : 'currMonthGoodschedule',
		align : 'center',
		width : 120,
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.currMonthGoodschedule')/*'本月货量进度'*/
	}, {
		dataIndex : 'reDateOptGoodsAmount',
		align : 'center',
		width : 180,
		xtype : 'ellipsiscolumn',
		text : airfreight.airProtocolFlight.i18n('foss.airfreight.airProtocolFlight.airProtocolFlightGird.reDateOptGoodsAmount')/*'剩余日需货量(公斤)'*/
	}
]
		
});

///**-----------------------------------------------onReady-----------------------------------------------------*/
//协议航班
airfreight.airProtocolFlight.queryForm = Ext.create('foss.airfreight.airProtocolFlight.QueryForm');
airfreight.airProtocolFlight.showProtocolFlightGird = Ext.create('foss.airfreight.airProtocolFlight.showProtocolFlightGird');
Ext.onReady(function() {
	Ext.QuickTips.init();
	airfreight.airProtocolFlight.orgCodes= new Array();
	Ext.Ajax.request({
		url : airfreight.realPath('queryOptAllOrgCodeByUserCode.action'),
		success : function(response){
			var result = Ext.decode(response.responseText);
			airfreight.airProtocolFlight.orgCodes=result.airProtocolFlightVo.airProtocolFlightQueryDto.orgCodeList;
			Ext.create('Ext.panel.Panel',{
				id : 'T_airfreight-airProtocolFlightindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [ airfreight.airProtocolFlight.queryForm,airfreight.airProtocolFlight.showProtocolFlightGird],
				renderTo : 'T_airfreight-airProtocolFlightindex-body'
					
			});
		}
	
	})
	
});