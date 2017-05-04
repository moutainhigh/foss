/**
*
* 转运场货量流动分布查询界面
*
*/
Ext.define('Foss.platform.goodsDistributionIndex.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'staDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');
			} else {
				return null;
			}
		}
	},{
		name : 'staTime',
		type : 'string'
	},{
		name : 'transferCenterCode',
		type : 'string'
	},{
		name : 'transferCenterName',
		type : 'string'
	},{
		name : 'arriveCargo',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'departCargo',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'actualInCargo',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'actualOutCargo',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'goodsStockWeight',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	}]
});



Ext.define('Foss.platform.goodsDistributionIndex.storeByDay',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.goodsDistributionIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryGoodsDistributionByDay.action'),
		reader : {
			type : 'json',
			root : 'goodsDistributionVo.goodsDistributionList'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.goodsDistributionIndex.queryFormByDay;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var goodsDistributionVo = store.proxy.reader.rawData.goodsDistributionVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});


platform.goodsDistributionIndex.getCurrentDept = function(item,operationCode,transferCode,str){
	if(!Ext.isEmpty(transferCode) && Ext.isEmpty(operationCode)){
		if(str == 'transferCode'){
			item.readOnly = true;
		}
	}else if(Ext.isEmpty(transferCode) && !Ext.isEmpty(operationCode)){
		if(str == 'operationCode'){
			item.readOnly = true;
		}
	}
}


Ext.define('Foss.platform.goodsDistributionIndex.formByDay', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'goodsDistributionVo.operationDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.goodsDistributionIndex.getCurrentDept(_this,platform.goodsDistributionIndex.operationDeptCode,platform.goodsDistributionIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'goodsDistributionVo.transferCenterCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.goodsDistributionIndex.getCurrentDept(_this,platform.goodsDistributionIndex.operationDeptCode,platform.goodsDistributionIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'goodsDistributionVo.staDate',
		editable:false,
		value: new Date(),
		format:'Y-m-d',
		columnWidth :.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
	    xtype:'checkboxgroup',
  	    columnWidth:1,
  	    colspan:6,
  	   // defaultType:'checkboxgroup',
  	    name:'staTypeGroup',
  	    layout:'column',
  	    text: '状态类型 :',
  	    items:[{
  	        xtype: 'label',
  	        text: '状态类型 :',
  	        margin: '12 30 0 8'
  	      },{
	     	   //xtype : 'checkboxfield',
	    	   name : 'isArrive',
	    	   id: 'isArrive',
	    	   inputValue : 'Y',
	    	   checked:true,
	    	   uncheckedValue: 'N',
	    	   columnWidth : 0.15,
	    	   boxLabel : '到达货量',
			   margin:'12 0 0 0'
	       },{
	    	  // xtype : 'checkboxfield',
	    	   name : 'isDepart',
	    	   id: 'isDepart',
	    	   checked:true,
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   columnWidth : 0.15,
	    	   boxLabel : '出发货量',
			   margin:'12 0 0 0'
	       },{
	    	   //xtype : 'checkboxfield',
	    	   id:'isActualIn',             
	    	   name : 'isActualIn',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
	    	   boxLabel : '实际流入量',
			   margin:'12 0 0 0'
	       },{
	    	 //  xtype : 'checkboxfield',
	    	   id:'isActualOut',             
	    	   name : 'isActualOut',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
	    	   boxLabel : '实际流出量',
			   margin:'12 0 0 0'
	       },{
	    	  // xtype : 'checkboxfield',
	    	   id:'isGoodsStock',
	    	   name : 'isGoodsStock',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
	    	   boxLabel :'货台库存',
			   margin:'12 0 0 0'
	     }]
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.goodsDistributionIndex.queryFormByDay.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				Ext.getCmp('Foss_Platform_goodsDistributionIndex_Chart_Id').store.removeAll();
				var params = this.up('form').getForm().getValues();
				var isArrive = params.isArrive;
				if(isArrive == 'N'){
					platform.goodsDistributionIndex.chartByDay.series.items[0].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByDay.series.items[0].showAll();
				}
				if(params.isDepart == 'N'){
					platform.goodsDistributionIndex.chartByDay.series.items[1].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByDay.series.items[1].showAll();
				}
				if(params.isActualIn == 'N'){
					platform.goodsDistributionIndex.chartByDay.series.items[2].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByDay.series.items[2].showAll();
				}
				if(params.isActualOut == 'N'){
					platform.goodsDistributionIndex.chartByDay.series.items[3].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByDay.series.items[3].showAll();
				}
				if(params.isGoodsStock == 'N'){
					platform.goodsDistributionIndex.chartByDay.series.items[4].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByDay.series.items[4].showAll();
				}
				var staTypeGroupValue = form.findField('staTypeGroup').getValue();
				if(staTypeGroupValue.isArrive==null&&
						staTypeGroupValue.isDepart==null&&
						staTypeGroupValue.isActualIn==null&&
						staTypeGroupValue.isActualOut==null&&
						staTypeGroupValue.isGoodsStock==null)
				{
					Ext.Msg.alert('提示','状态类型，至少选择一种!');
					return false;
				}
				Ext.getCmp('Foss_Platform_goodsDistributionIndex_Chart_Id').store.load({
					params : params
				});
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


platform.goodsDistributionIndex.queryFormByDay = Ext.create('Foss.platform.goodsDistributionIndex.formByDay');


Ext.define('Foss.platform.goodsDistributionIndex.chartByDay', {
	extend : 'Ext.chart.Chart',
	  animate: false,
	    store : Ext.create('Foss.platform.goodsDistributionIndex.storeByDay'),
	    width : 920,
	    height : 400,
	    insetPadding: 35,
	    legend : {
			position : 'top'/*,
			itemSpacing:5,
			padding:5*/
		},
	    axes: [{
	               type: 'Numeric',
	               position: 'left',
	               fields: ['arriveCargo','departCargo','actualInCargo','actualOutCargo','goodsStockWeight'],
	               minimum: 0,
				   title:'货量(吨)',
	               grid: true,
				   label: {  
	                    renderer: Ext.util.Format.numberRenderer('0,00.00')  
	               }  
//	               label: {
//	                   renderer: function(saturationDay) {
//	                	   return saturationDay + '%';
//	                   }
//	               }
	           },{
	               type: 'Category',
	               position: 'bottom',
				   title:'时间',
	               fields: ['staTime'],
				   rotate: {
						degrees: 315  
					}
	           }
	       ],
	       series: [{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'arriveCargo',
	            title: '到达货量',
	            tips: {
	                trackMouse: true,
	                width: 80,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('arriveCargo')));
	                }
	            }
				//,label: { 
					//field: 'saturationDay',//设置为name则legend就会显示name
					 //display: 'rotate',  
					 //contrast: true,  
					 //font: '18px Arial'		
				//}
				//,
				//label: {  
	             //       display: 'rotate',  
	              //      'text-anchor': 'middle',  
	               //     field: 'saturationDay',  
	                //    renderer: Ext.util.Format.numberRenderer('0'),  
	                  //  orientation: 'vertical',  
	                   // color: '#333'  
	                //}
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'departCargo',
	            title:'出发货量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('departCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'actualInCargo',
	            title: '实际流入货量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('actualInCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'actualOutCargo',
	            title:'实际流出量',
	            tips: {
	                trackMouse: true,
	                width: 80,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('actualOutCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'goodsStockWeight',
	            title:'货台库存',
	            tips: {
	                trackMouse: true,
	                width: 80,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('goodsStockWeight')));
	                }
	            }
	        }]
});

platform.goodsDistributionIndex.chartByDay =Ext.create('Foss.platform.goodsDistributionIndex.chartByDay',{id:'Foss_Platform_goodsDistributionIndex_Chart_Id'}) ;



Ext.define('Foss.platform.goodsDistributionIndex.PanelByDay', {
	extend : 'Ext.panel.Panel',
	title : '查询结果',
	frame : true,
	collapsible : true,
//	hidden : true,
	animCollapse : true,
	items : [platform.goodsDistributionIndex.chartByDay],
	tbar : [{
    	text: '导出数据',
        handler: function(){
        	var actionUrl=platform.realPath('goodsDistributionByDayExport.action');
			var queryForm = platform.goodsDistributionIndex.queryFormByDay.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
//				var exportParams = {
//						'goodsDistributionVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
//						'goodsDistributionVo.stockSaturationEntity.beginDate' : queryParams.stockSaturationBeginTime,
//						'goodsDistributionVo.stockSaturationEntity.endDate' : queryParams.stockSaturationEndTime
//				}; 
				if(!Ext.fly('downloadGoodsDistributionByDayFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadGoodsDistributionByDayFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				var staTypeGroupValue = queryForm.findField('staTypeGroup').getValue();
				if(staTypeGroupValue.isArrive==null&&
						staTypeGroupValue.isDepart==null&&
						staTypeGroupValue.isActualIn==null&&
						staTypeGroupValue.isActualOut==null&&
						staTypeGroupValue.isGoodsStock==null)
				{
					Ext.Msg.alert('提示','状态类型，至少选择一种!');
					return false;
				}
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downloadGoodsDistributionByDayFileForm'),
				method : 'POST',
				params : queryParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示',result.message);
					//myMask.hide();
				}	
				});
			}
		}
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
})

platform.goodsDistributionIndex.panelByDay =Ext.create('Foss.platform.goodsDistributionIndex.PanelByDay');


/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!上面是   日均装卸车效率  store  grid  panel
 * ！！！！！！！！！！！！！！！！！下面 是    月均装卸车效率  store grid panel
 */
Ext.define('Foss.platform.goodsDistributionIndex.storeByMonth',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.goodsDistributionIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryGoodsDistributionByMonth.action'),
		reader : {
			type : 'json',
			root : 'goodsDistributionVo.goodsDistributionList'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.goodsDistributionIndex.queryFormByMonth;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				var tempDate = queryParams['goodsDistributionVo.staDate'];
				if (tempDate != null) {
					var date = new Date(tempDate);
					queryParams['goodsDistributionVo.staDate'] = date;
				}
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var goodsDistributionVo = store.proxy.reader.rawData.goodsDistributionVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

Ext.define('Foss.platform.goodsDistributionIndex.formByMonth', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'goodsDistributionVo.operationDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.goodsDistributionIndex.getCurrentDept(_this,platform.goodsDistributionIndex.operationDeptCode,platform.goodsDistributionIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'goodsDistributionVo.transferCenterCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.goodsDistributionIndex.getCurrentDept(_this,platform.goodsDistributionIndex.operationDeptCode,platform.goodsDistributionIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'goodsDistributionVo.staDate',
		editable:false,
		value: new Date(),
		format:'Y-m',
		columnWidth :.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
	    xtype:'checkboxgroup',
  	    columnWidth:1,
  	    colspan:6,
  	    //defaultType:'checkboxgroup',
  	    layout:'column',
  	    name:'staTypeGroupMonth',
  	    items:[{
  	        xtype: 'label',
  	        text: '状态类型 :',
  	        margin: '12 30 0 8'
  	      },{
	     	   //xtype : 'checkboxfield',
	    	   name : 'isArrive',
	    	   id: 'isArrive2',
	    	   inputValue : 'Y',
	    	   checked:true,
	    	   uncheckedValue: 'N',
	    	   columnWidth : 0.15,
				   margin:'12 0 0 0',
	    	   boxLabel : '到达货量'
	       },{
	    	  // xtype : 'checkboxfield',
	    	   name : 'isDepart',
	    	   id: 'isDepart2',
	    	   checked:true,
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   columnWidth : 0.15,
				   margin:'12 0 0 0',
	    	   boxLabel : '出发货量'
	       },{
	    	   //xtype : 'checkboxfield',
	    	   id:'isActualIn2',             
	    	   name : 'isActualIn',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
				   margin:'12 0 0 0',
	    	   boxLabel : '实际流入量'
	       },{
	    	  // xtype : 'checkboxfield',
	    	   id:'isActualOut2',             
	    	   name : 'isActualOut',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
				   margin:'12 0 0 0',
	    	   boxLabel : '实际流出量'
	       },{
	    	  // xtype : 'checkboxfield',
	    	   id:'isGoodsStock2',
	    	   name : 'isGoodsStock',
	    	   inputValue : 'Y',
	    	   uncheckedValue: 'N',
	    	   checked:true,
	    	   columnWidth : 0.15,
				   margin:'12 0 0 0',
	    	   boxLabel :'货台库存'
	     }]
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.goodsDistributionIndex.queryFormByMonth.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				var params = this.up('form').getForm().getValues();
				
				var isArrive = params.isArrive;
				if(isArrive == 'N'){
					platform.goodsDistributionIndex.chartByMonth.series.items[0].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByMonth.series.items[0].showAll();
				}
				if(params.isDepart == 'N'){
					platform.goodsDistributionIndex.chartByMonth.series.items[1].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByMonth.series.items[1].showAll();
				}
				if(params.isActualIn == 'N'){
					platform.goodsDistributionIndex.chartByMonth.series.items[2].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByMonth.series.items[2].showAll();
				}
				if(params.isActualOut == 'N'){
					platform.goodsDistributionIndex.chartByMonth.series.items[3].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByMonth.series.items[3].showAll();
				}
				if(params.isGoodsStock == 'N'){
					platform.goodsDistributionIndex.chartByMonth.series.items[4].hideAll();
				}else{
					platform.goodsDistributionIndex.chartByMonth.series.items[4].showAll();
				}
				var staTypeGroupValue = form.findField('staTypeGroupMonth').getValue();
				if(staTypeGroupValue.isArrive==null&&
						staTypeGroupValue.isDepart==null&&
						staTypeGroupValue.isActualIn==null&&
						staTypeGroupValue.isActualOut==null&&
						staTypeGroupValue.isGoodsStock==null)
				{
					Ext.Msg.alert('提示','状态类型，至少选择一种!');
					return false;
				}
				Ext.getCmp('Foss_Platform_goodsDistributionIndex_Chart2_Id').store.load({
					params : params
				});
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}});

platform.goodsDistributionIndex.queryFormByMonth = Ext.create('Foss.platform.goodsDistributionIndex.formByMonth');


Ext.define('Foss.platform.goodsDistributionIndex.chartByMonth', {
	  extend : 'Ext.chart.Chart',
	  animate: false,
	    store : Ext.create('Foss.platform.goodsDistributionIndex.storeByMonth'),
	    width : 920,
	    height : 400,
	    insetPadding: 35,
	    legend : {
			position : 'right',
			itemSpacing:5,
			padding:5
		},
	    axes: [{
	               type: 'Numeric',
	               position: 'left',
	               fields: ['arriveCargo','departCargo','actualInCargo','actualOutCargo','goodsStockWeight'],
	               minimum: 0,
				   title:'货量(吨)',
	               grid: true,
	               adjustEnd:true,
				   label: {  
	                    renderer: Ext.util.Format.numberRenderer('0,00.00')  
	               }  
//	               label: {
//	                   renderer: function(saturationDay) {
//	                	   return saturationDay + '%';
//	                   }
//	               }
	           },{
	               type: 'Category',
	               position: 'bottom',
				   title:'时间',
	               fields: ['staTime'],
				   rotate: {
						degrees: 315  
					}
	           }
	       ],
	       series: [{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'arriveCargo',
	            title: '到达货量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('arriveCargo')));
	                }
	            }
				//,label: { 
					//field: 'saturationDay',//设置为name则legend就会显示name
					 //display: 'rotate',  
					 //contrast: true,  
					 //font: '18px Arial'		
				//}
				//,
				//label: {  
	             //       display: 'rotate',  
	              //      'text-anchor': 'middle',  
	               //     field: 'saturationDay',  
	                //    renderer: Ext.util.Format.numberRenderer('0'),  
	                  //  orientation: 'vertical',  
	                   // color: '#333'  
	                //}
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'departCargo',
	            title: '出发货量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('departCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'actualInCargo',
	            title: '实际流入量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('actualInCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'actualOutCargo',
	            title:'实际流出量',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('actualOutCargo')));
	                }
	            }
	        },{
	            type: 'line',
	            axis: ['left'],
	            xField: 'staTime',
	            yField: 'goodsStockWeight',
	            title:'货台库存',
	            tips: {
	                trackMouse: true,
	                width: 100,
	                height: 40,
	                renderer: function(storeItem, item) {
	                	this.setTitle(storeItem.get('staTime'));
	                	this.update((storeItem.get('goodsStockWeight')));
	                }
	            }
	        }]
});


platform.goodsDistributionIndex.chartByMonth =Ext.create('Foss.platform.goodsDistributionIndex.chartByMonth',{id:'Foss_Platform_goodsDistributionIndex_Chart2_Id'}) ;


Ext.define('Foss.platform.goodsDistributionIndex.PanelByMonth', {
	extend : 'Ext.panel.Panel',
	title : '查询结果',
	frame : true,
	collapsible : true,
//	hidden : true,
	animCollapse : true,
	items : [platform.goodsDistributionIndex.chartByMonth],
	tbar : [{
    	text: '导出数据',
        handler: function(){
        	var actionUrl=platform.realPath('goodsDistributionByMonthExport.action');
			var queryForm = platform.goodsDistributionIndex.queryFormByMonth.getForm();
			var queryFormValues = queryForm.getValues();
			var t = queryFormValues['goodsDistributionVo.staDate'];
			var date;
			if (t != null) {
				date = new Date(t);
			}
			var exportParams = {
					'goodsDistributionVo.transferCenterCode' : queryFormValues['goodsDistributionVo.transferCenterCode'],
					'goodsDistributionVo.operationDeptCode' : queryFormValues['goodsDistributionVo.operationDeptCode'],
					'goodsDistributionVo.staDate' : date
			}; 
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				if(!Ext.fly('downloadGoodsDistributionByMonthFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadGoodsDistributionByMonthFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				var staTypeGroupValue = queryForm.findField('staTypeGroupMonth').getValue();
				if(staTypeGroupValue.isArrive==null&&
						staTypeGroupValue.isDepart==null&&
						staTypeGroupValue.isActualIn==null&&
						staTypeGroupValue.isActualOut==null&&
						staTypeGroupValue.isGoodsStock==null)
				{
					Ext.Msg.alert('提示','状态类型，至少选择一种!');
					return false;
				}
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downloadGoodsDistributionByMonthFileForm'),
				method : 'POST',
				params : exportParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示',result.message);
					//myMask.hide();
				}	
				});
			}
		}
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

platform.goodsDistributionIndex.panelByMonth =Ext.create('Foss.platform.goodsDistributionIndex.PanelByMonth');

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!      mainPanel
 */
Ext.define('Foss.platform.goodsDistributionIndex.MainTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
				tabConfig : {
					width : 180
				},
				title : '外场每日货物流动分布情况',
				items : [platform.goodsDistributionIndex.queryFormByDay,platform.goodsDistributionIndex.panelByDay]
			}, {
				tabConfig : {
					width : 180
				},
				title : '外场月均货物流动分布情况',
				items : [platform.goodsDistributionIndex.queryFormByMonth,platform.goodsDistributionIndex.panelByMonth]
			}]
});

/** -----------------------------------------------viewpanel--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	platform.goodsDistributionIndex.MainTabPanel = Ext
			.create('Foss.platform.goodsDistributionIndex.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-goodsDistributionIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				items : [platform.goodsDistributionIndex.MainTabPanel],
				renderTo : 'T_platform-goodsDistributionIndex-body'
			});

	//如果当前部门找不到外场，但是找到了经营本部，则自动填写经营本部
	if(!Ext.isEmpty(platform.goodsDistributionIndex.operationDeptCode)){
		platform.goodsDistributionIndex.queryFormByDay.getForm().findField('goodsDistributionVo.operationDeptCode').setCombValue(
			platform.goodsDistributionIndex.operationDeptName,		
			platform.goodsDistributionIndex.operationDeptCode
		);
		platform.goodsDistributionIndex.queryFormByMonth.getForm().findField('goodsDistributionVo.operationDeptCode').setCombValue(
		platform.goodsDistributionIndex.operationDeptName,		
		platform.goodsDistributionIndex.operationDeptCode
		);
	};
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.goodsDistributionIndex.outfieldCode)){
		platform.goodsDistributionIndex.queryFormByDay.getForm().findField('goodsDistributionVo.transferCenterCode').setCombValue(
			platform.goodsDistributionIndex.outfieldName,
			platform.goodsDistributionIndex.outfieldCode
			);
		platform.goodsDistributionIndex.queryFormByMonth.getForm().findField('goodsDistributionVo.transferCenterCode').setCombValue(
			platform.goodsDistributionIndex.outfieldName,
			platform.goodsDistributionIndex.outfieldCode
			);
	}
});



