//快递到达货管理页面查询条件表单
Ext.define('Foss.load.expressArrival.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '查询条件',
//	title : stock.movegoods.i18n('foss.stock.query.movegoods'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'numberfield',
		fieldLabel: '单号',
//		fieldLabel: stock.prioritygoods.i18n('foss.stock.org'),
		name: 'waybillNo',
		allowNegative:false,
		allowDecimals:false,
		minLength:10,
		hideTrigger: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'rangeDateField',
		fieldLabel: '预计到达时间',
//		fieldLabel: stock.movegoods.i18n('foss.stock.applicationtime'),
		dateType: 'datetimefield_date97',
		fromName: 'createtime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()+1,
										'00', '00','00'), 'Y-m-d H:i:s'),			
		toName: 'finishtime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()+4,
										'23', '59','59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		blankText:'字段不能为空',
		//blankText: stock.movegoods.i18n('foss.stock.notnull'),
		columnWidth: .6
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: '重置',
//			text: stock.movegoods.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){
				load.expressArrivalindex.queryform.getForm().reset();
				load.expressArrivalindex.queryform.getForm().findField('createtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()+1,
										'00', '00','00'), 'Y-m-d H:i:s'));
				load.expressArrivalindex.queryform.getForm().findField('finishtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()+4,
										'23', '59','59'), 'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
		},{
			text: '查询',
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				var startTime = load.expressArrivalindex.queryform.getValues().createtime;
				var endTime = load.expressArrivalindex.queryform.getValues().finishtime;
				//火狐浏览器不能转换2015-07-15格式的日期,将其转换为2015/07/15格式
				if(startTime != null){
					startTime = startTime.replace(/-/g, "/");
				}
				var difTime = 0;
				//var newTime = new Date().getTime();
				//当前的日期的数字   "2015-07-14" 为  20150714
				newDate = parseInt(Ext.Date.format( new Date(), 'Ymd'));
				//起始日期的数字
				var startDate = parseInt(Ext.util.Format.date(startTime, 'Ymd'));
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y/m/d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 7){
					Ext.MessageBox.alert('警告', '查询时间跨度不能超过7天'); 
					return;
				}
				else if(startDate - 1 < newDate){
					Ext.MessageBox.alert('警告', '起始日期不能小于默认起始日期(系统当前日期+1)');
					return;
				}
				
				if(load.expressArrivalindex.queryform.getForm().isValid()){
					load.expressArrivalindex.pagingBar.moveFirst();       //pagingBar   分页用的
				}
				
			}
		}]
	}
	],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



Ext.define('Foss.load.expressArrival.WaybillModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'receiveCustomerCityCode', type: 'string'},
        {name: 'receiveCustomerCityName', type: 'string'},
        {name: 'receiveCustomerProvCode', type: 'string'},
        {name: 'receiveCustomerProvName', type: 'string'},
        {name: 'waybillNo',  type: 'string'},
        {name: 'receiveCustomerAddress', type: 'string'},
        {name: 'customerPickupOrgName',  type: 'string'},
        {name: 'customerPickupOrgCode',  type: 'string'},
        {name: 'preArriveTime',  type: 'date',convert: dateConvert},
        {name: 'createUserDeptName',  type: 'string'},
        {name: 'createOrgCode',  type: 'string'},
        {name: 'status',  type: 'int'}
    ]
});



//运单库存表格
Ext.define('Foss.load.expressArrival.WaybillGrid', {
	extend:'Ext.grid.Panel',
	height: 450,
	autoScroll:true,
	columnLines: true,
    frame: true,
    plugins: [{
        ptype: 'rowexpander',
        pluginId: 'Foss.load.expressArrival.WaybillGrid_Plugin_ID',
		rowsExpander: false,
		rowBodyElement : 'Foss.load.expressArrival.GoodsGrid'
	}],
	columns: [{
			header: 'ID', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'id',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '收货城市code', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'receiveCustomerCityCode',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '收货城市name', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'receiveCustomerCityName',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '收货省份code', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'receiveCustomerProvCode',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '收货省份name', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'receiveCustomerProvName',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '操作', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'operate',
			width : 100,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
				tooltip : '查看电子地图',
				iconCls : 'foss_icons_pkp_viewOrderlocation',
				handler : function(gridView, rowIndex, colIndex) {
					var grid = gridView.up('grid'),
						rec = grid.store.getAt(rowIndex);
					load.expressArrivalindex.arriveCityName = rec.get('receiveCustomerCityName'); 
					load.expressArrivalindex.address = rec.get('receiveCustomerAddress');
						//rec.get('address').replace(/#/g, '')
										//.replace(/\?/g, '').replace(/&/g, '');
					load.expressArrivalindex.arriveProName = rec.get('receiveCustomerProvName');
					
					
					//请求gis查询网点url+参数
					var param = load.expressArrivalindex.gisPageQuery + 
							'&province=' + load.expressArrivalindex.arriveProName + 
							'&city=' + load.expressArrivalindex.arriveCityName + 
							'&otherAddress=' + load.expressArrivalindex.address + 
							'&expressDeliveryType=expressDeliver';
					
					var url = ContextPath.TFR_EXECUTION + '/scripts/load/complementMap.html?url=' + param;
					
					//打开gis查询网点界面
					load.expressArrivalindex.gidWindow = window.open(url,"_blank","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=1200, height=1000");
				} 
			}],
		},{ 
			header: '运单号', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'waybillNo',
			width : 100,
			align: 'center'
		},{ 
			header: '目的地址', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'receiveCustomerAddress',
			width : 300,
			align: 'center'
		},{ 
			header: '提货网点', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'customerPickupOrgName',
			width : 150,
			align: 'center'
		},{
			header: '提货部门code',
			dataIndex: 'customerPickupOrgCode',
			width : 100,
			align: 'center',
			hidden:true
		},{ 
			header: '预计到达时间', 
//			header: stock.prioritygoods.i18n('foss.stock.applicationTime'), 
			dataIndex: 'preArriveTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			header: '开单营业部', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'createUserDeptName',
			width : 150,
			align: 'center'
		},{
			header: '开单部门code',
			dataIndex: 'createOrgCode',
			width : 100,
			align: 'center',
			hidden:true
		},{
			header: '状态', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'status',
			width : 100,
			align: 'center',
			hidden: true
		}],


		
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.expressArrival.WaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '退回',
//				text: stock.stockmanage.i18n('foss.stock.apply'),
				gridContainer: this,
				handler: function() {

					var goodsMapList = load.expressArrivalindex.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要退回的数据！', 'error', 2000);
//						Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
					}else if(goodsMapList.length>=2){
						Ext.ux.Toast.msg('提示', '一次只能退回一条数据！', 'error', 2000);
//						Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
					}
//					else if(goodsMapList[0].getValues()[0].data.status == 1){
//						//Ext.ux.Toast.msg('提示', ' 确认将已经确认过的数据退回吗?', 'error', 2000);
//						Ext.MessageBox.alert('警告', '确认将已经确认过的数据退回吗?');
//					}
					
					Ext.MessageBox.confirm('提示', '确认该送货地址不属于本营业部的送货范围吗？',function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){					
							var jsonParam = {expressArrivalVo: {expressArrivaId:goodsMapList[0].getValues()[0].data.id,
								waybillNo:goodsMapList[0].getValues()[0].data.waybillNo}};
							Ext.Ajax.request({						
			    			url: load.realPath('expressArrivalBack.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				load.expressArrivalindex.waybillGoodsMap.clear();
			    				load.expressArrivalindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '退回成功！', 'ok', 1000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('退回失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
						
					});
				
					}
			},{
				xtype: 'container',
				columnWidth:.10,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '确认',
				gridContainer: this,
				handler: function() {
					var goodsMapList = load.expressArrivalindex.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要确认的数据！', 'error', 2000);
//						Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
					}else if(goodsMapList.length>=2){
						Ext.ux.Toast.msg('提示', '一次只能确认一条数据！', 'error', 2000);
//						Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
					}else if(goodsMapList[0].getValues()[0].data.status != 0){
						Ext.ux.Toast.msg('提示', ' 此数据已经确认过了！', 'error', 2000);
						retrun;
					}
					
					Ext.MessageBox.confirm('提示', '确认该送货地址属于本营业部的送货范围吗？',function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){					
							var jsonParam = {expressArrivalVo: {expressArrivaId:goodsMapList[0].getValues()[0].data.id,
								waybillNo:goodsMapList[0].getValues()[0].data.waybillNo}};
							Ext.Ajax.request({						
			    			url: load.realPath('expressArrivalConfirm.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				load.expressArrivalindex.waybillGoodsMap.clear();
			    				load.expressArrivalindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '确认操作成功！', 'ok', 1000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('确认操作失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
						
					});
				}
			}];
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			load.expressArrivalindex.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		//select监听
		listeners: {
				select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				var goodsMap = load.expressArrivalindex.waybillGoodsMap.get(id);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				//这一行是添加数据的吧?
				load.expressArrivalindex.waybillGoodsMap.add(id,goodsMap);	
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				load.expressArrivalindex.waybillGoodsMap.removeAtKey(id);
				
			}
				
			
		},
		viewConfig: {
		enableTextSelection: true,
		stripeRows : false,
			getRowClass: function(record, rowParams, rp, store) {
				var returntype = record.get('status');
				if(returntype == 1) {
					return 'result_grid_confirm_back_green';
				}
			}
		}
		
});




//运单库存 Store
Ext.define('Foss.load.expressArrival.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.expressArrival.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: load.realPath('queryExpressArrival.action'),
		reader : {
			type : 'json',
			root : 'expressArrivalVo.expressArrivalDisplayEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = load.expressArrivalindex.queryform.getValues();
				Ext.apply(operation, {
					params : {
						'expressArrivalVo.expressArrivalQueryDto.waybillNo' : queryParams.waybillNo,
						'expressArrivalVo.expressArrivalQueryDto.beginTime' : queryParams.createtime,
						'expressArrivalVo.expressArrivalQueryDto.endTime' : queryParams.finishtime

					}
				});	
		},
	}
	
});






Ext.onReady(function(){
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.load.expressArrival.QueryForm');
	load.expressArrivalindex.queryform = queryform;
	var waybillGrid = Ext.create('Foss.load.expressArrival.WaybillGrid');
	load.expressArrivalindex.waybillGrid = waybillGrid;
	
	load.expressArrivalindex.waybillGoodsMap = new Ext.util.HashMap();
	Ext.create('Ext.panel.Panel',{
		id:'T_load-expressArrivalindex_content',
		cls:"panelContentNToolbar",
	  	bodyCls:'panelContent-body',
	  	
		
		items : [queryform,waybillGrid],
		renderTo: 'T_load-expressArrivalindex-body'
		});
});


