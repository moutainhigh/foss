/**
 * 汽运价格表信息管理
 * @author 094463-foss-xieyantao
 * @date 2014-1-14 下午3:08:57
 */


//消息提醒框
pricing.priceReportSearch.showWarningMsg = function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	};
	
//--------------------------------------pricing----------------------------------------
//汽运价格表信息信息
Ext.define('Foss.pricing.PriceTableEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'provinceName',/* 省份 */
        type : 'string'
    },{
        name : 'destinationName',/* 目的城市 */
        type : 'string'
    },{
        name : 'runTime', /* 自提时间 */
        type : 'string'
    },{
        name : 'heavyPrice',/* 元/公斤*/
        type : 'number'
    },{
        name : 'lightPrice',/* 元/方 */
        type : 'number'
    }]
});

//------------------------------------model---------------------------------------------------



/**
 * 汽运价格表信息Store（Foss.pricing.PriceTableEntity）
 */
Ext.define('Foss.pricing.PartbussPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.PriceTableEntity',//汽运价格表信息的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/queryAllPriceTables.action',//请求地址
		reader : {
			type : 'json',
			root : 'vo.priceTableEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//创建一个本地的类型store
Ext.define('Foss.pricing.priceReportSearch.TypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
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

//----------------------------------------store---------------------------------

/**
 * 汽运价格表信息查询条件FORM
 */
Ext.define('Foss.pricing.QueryPartbussPlanForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceReportSearch.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	colspan: 1,
		margin : '8 10 8 10',
		allowBlank:false,
		anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [
			{
			name: 'startDeptCode',
	        fieldLabel: '始发部门',
	        maxLength:200,
	        xtype : 'dynamicorgcombselector',
	        type : 'ORG',
	        salesDepartment : 'Y'// 查询营业部 配置此值
		},{
		    xtype:'combobox',
			name: 'productType',
			fieldLabel: '产品类型',
//			columnWidth: .25,
			maxLength:200,
			displayField: 'name',
			//value值字段
			valueField:'code', 
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			//默认值为全部
			value:'RECISION_PRODUCTS',
			store: Ext.create('Foss.pricing.priceReportSearch.TypeStore',{
				data: {
					'items':[
						{'code':'RECISION_PRODUCTS','name':'精准'},
						{'code':'COMMON_PRODUCTS','name':'普通'}
					]
				}
			})
		},{
			xtype:'datetimefield_date97',
			name: 'effectiveDate',
			id:'pricing.priceReportSearch_endTime_ID',
			time:true,
			editable:'false',
	        fieldLabel: '时间',
	        format:'Y-m-d H:i:s',
	        dateConfig: {
				el: 'pricing.priceReportSearch_endTime_ID-inputEl',
				dateFmt: 'yyyy-MM-dd HH:mi:ss'
//				maxDate: stl.dateFormat(new Date(),stl.FORMAT_DATE)+stl.END_PREFIX
			},
			value:new Date().format('yyyy-MM-dd hh:mm:ss')
		},{//[pop] add 148246-孙建博 start
		    xtype:'combobox',
			name: 'sectionID',
			fieldLabel: '分段数',
			maxLength:10,
			displayField: 'name',
			//value值字段
			valueField:'code', 
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			//默认值为1
			value:'1',
			store: Ext.create('Foss.pricing.priceReportSearch.TypeStore',{
				data: {
					'items':[
						{'code':'1','name':'第1段'},
						{'code':'2','name':'第2段'},
						{'code':'3','name':'第3段'},
						{'code':'4','name':'第4段'},
						{'code':'5','name':'第5段'},
						{'code':'6','name':'第6段'}
					]
				}
			})//[pop] add 148246-孙建博 start
		}],
		me.fbar = [
		           {
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.priceReportSearch.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
				//设置当前部门
				if(FossUserContext.getCurrentDeptCode()=='W0114020901'){
					me.getForm().findField('startDeptCode').setValue(null);
				}else{
					me.getForm().findField('startDeptCode').setCombValue(FossUserContext. getCurrentDept().name,FossUserContext.getCurrentDeptCode());
				}
				me.getForm().findField('effectiveDate').setValue(new Date().format('yyyy-MM-dd hh:mm:ss'));
			}
		},
		{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.priceReportSearch.i18n('i18n.pricingRegion.search'),//查询
//			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-priceReportSearchIndex_content').getFlightPriceGrid();
					grid.getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
		/*me.getForm().getFields( ).each(function(item,index,length){
			
			var roleCodes = FossUserContext.getCurrentUserRoleCodes();

			if(FossUserContext.getCurrentDeptCode()=='W0100020108180504'){
				if(item.getName() == 'startDeptCode'){
					item.setReadOnly(false);
				}
			}else{
				if(item.getName() == 'startDeptCode'){
					item.setReadOnly(true);
				}
			}
			
		});//将FORM设置为不可用*/		
		var startform = me.getForm().findField('startDeptCode');
		var roleCodes = FossUserContext.getCurrentUserRoleCodes();
		startform.setReadOnly(true);
		for(var i=0;i < roleCodes.length; i++){
			if(roleCodes[i] == 'BSE_PRICE_MAINTENANCE'){
// 				if(startform.getValue() == 'startDeptCode'){
					startform.setReadOnly(false);
					break;
// 				}
			}
// 			else{
// 				if(startform.getValue() == 'startDeptCode'){
// 					startform.setReadOnly(true);
// 				}
// 			}
		}
		//设置当前部门
				if(FossUserContext.getCurrentDeptCode()=='W0114020901'){
					me.getForm().findField('startDeptCode').setValue(null);
				}else{
					me.getForm().findField('startDeptCode').setCombValue(FossUserContext. getCurrentDept().name,FossUserContext.getCurrentDeptCode());
				}
	}
});



/**
 * 汽运价格表信息列表
 */
Ext.define('Foss.pricing.PartbussPlanGrid', {
	extend: 'Ext.grid.Panel',
	title : '查询结果',//汽运价格表信息列表
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.priceReportSearch.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	/**
	 * 导出PDF
	 */
	pricingExportPDF: function(){
		var me = this;
		if(!Ext.fly('downloadAttachFileForm')){
		    var frm = document.createElement('form');
		    frm.id = 'downloadAttachFileForm';
		    frm.style.display = 'none';
		    document.body.appendChild(frm);
		}
		setTimeout(function() {
			me.enable(true);
		}, 10000);	
		var searchForm = Ext.getCmp('T_pricing-priceReportSearchIndex_content').getQueryFlightPriceForm().getForm();
		var queryParams = searchForm.getValues();
		var params = {
						'vo.startDeptCode':queryParams.startDeptCode,
						'vo.productType':queryParams.productType,
						'vo.effectiveDate':queryParams.effectiveDate,
						//[pop] add 148246-孙建博 start
						'vo.sectionID':queryParams.sectionID
						//[pop] add 148246-孙建博 end
					};//pricing.priceReportSearch.dateFormat(endTime,'Y-m-d H:i:s')
		//拼接vo，注入到后台
		Ext.Ajax.request({
		    url:pricing.realPath('pricingDetail.action'),
		    form: Ext.fly('downloadAttachFileForm'),
		    method : 'POST',
		    params : params,
		    isUpload: true,
		    success : function(response,options){
					Ext.Msg.alert('导出失败');
		    },
		    failure : function(response,options){
				Ext.Msg.alert('导出失败');
		    }
		});
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [
			{
			text : '省份',//省份
			dataIndex : 'provinceName',
			width:200
		},{
			text : '目的城市',//目的城市
			dataIndex : 'destinationName',
			flex:1
		},{
			text : '自提时间',//自提时间
			dataIndex : 'runTime',
			width:200
		},{
			text : '元/公斤',//元/公斤
			dataIndex : 'heavyPrice',
			width:150
		},{
			text : '元/方',//元/方
			dataIndex : 'lightPrice',
			width:150
		}];
		me.store = Ext.create('Foss.pricing.PartbussPlanStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryFlightPriceForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//汽运价格表信息大查询，查询条件组织
								'vo.startDeptCode':queryForm.getForm().findField('startDeptCode').getValue(),//始发部门
								'vo.productType':queryForm.getForm().findField('productType').getValue(),//产品类型
								'vo.effectiveDate':queryForm.getForm().findField('effectiveDate').getValue(),//有效时间
								//[pop] add 148246-孙建博 start
								'vo.sectionID':queryForm.getForm().findField('sectionID').getValue()//分段数
								//[pop] add 148246-孙建博 end
							}
						});	
					}
				},
				load:function(store,records,successful,operation,opts){  
	                if(successful){
	                	//[pop] add 148246-孙建博 start
	                	var sectionID = store.getProxy().getReader().jsonData.vo.sectionID;
						var sectionScopeMap = store.getProxy().getReader().jsonData.vo.sectionScopeMap;
	                	if(!Ext.isEmpty(sectionScopeMap)){
							Ext.ComponentQuery.query('displayfield[name=sectionScope]')[0].setValue(sectionScopeMap['section'+sectionID]);
						}else{
							Ext.ComponentQuery.query('displayfield[name=sectionScope]')[0].setValue('');
						}
						//[pop] add 148246-孙建博 end
	                }else{  
	                	this.removeAll();
	                    Ext.Msg.alert('提示','数据加载失败!');
	                }          
	           }  
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.tbar = [{
			text : '导出',//导出
			disabled: !pricing.priceReportSearch.isPermission('priceReportSearch/updatebutton'),
			hidden: !pricing.priceReportSearch.isPermission('priceReportSearch/updatebutton'),
			handler :function(){
				me.pricingExportPDF();
			}
		},{//[pop] add 148246-孙建博 start
			name : 'sectionScope',//用于显示价格对应重量体积范围
			margin : '0 0 5 100',
			xtype : 'displayfield',
			fieldStyle : 'color:red;',
			width:400,
			value : ''
			//[pop] add 148246-孙建博 end
		}];
		me.bbar = me.getPagingToolbar();
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------


/**
 * @description 汽运价格表信息管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-priceReportSearchIndex_content')) {
		return;
	};
	var queryFlightPriceForm = Ext.create('Foss.pricing.QueryPartbussPlanForm');//查询FORM
	var flightPriceGrid = Ext.create('Foss.pricing.PartbussPlanGrid');//查询结果GRID
	Ext.getCmp('T_pricing-priceReportSearchIndex').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-priceReportSearchIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryFlightPriceForm : function() {
			return queryFlightPriceForm;
		},
		//获得查询结果GRID
		getFlightPriceGrid : function() {
			return flightPriceGrid;
		},
		items : [queryFlightPriceForm, flightPriceGrid]
	}));
});
