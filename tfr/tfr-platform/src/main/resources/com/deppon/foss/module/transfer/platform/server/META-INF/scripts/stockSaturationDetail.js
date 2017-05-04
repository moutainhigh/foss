var stockSaturationDatePage = new Date();
if(stockSaturationDatePage.getHours()>=12){
	stockSaturationDatePage = new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() +1);
}else{
	stockSaturationDatePage = new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate())
}

//仓库饱和度明细(日) form
Ext.define('Foss.platform.stockSaturationDay.stockSaturationDetailQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'outfieldCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.stockSaturationDetail.currentCode),
		allowBlank : false
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '日期',
		columnWidth : 1/2,
		dateType: 'datefield',
		dateRange : 31,
		fromName : 'stockSaturationBeginTime',
		toName : 'stockSaturationEndTime',
		fromValue : new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 29),
		toValue : stockSaturationDatePage,
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			hidden: !Ext.isEmpty(platform.stockSaturationDetail.currentCode),
			columnWidth : .08,
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				//如果部门为外场，则外场重新赋值
				if(!Ext.isEmpty(platform.stockSaturationDetail.currentCode)){
					form.findField('outfieldCode').setCombValue(
							platform.stockSaturationDetail.outfieldName,
							platform.stockSaturationDetail.outfieldCode
					);
				}
				//填写日期
				form.findField('stockSaturationBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 30), 'Y-m-d'));
				form.findField('stockSaturationEndTime').setValue(Ext.Date.format(stockSaturationDatePage, 'Y-m-d'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				platform.stockSaturationDetail.resultDayPanel.setVisible(true);
				platform.stockSaturationDetail.resultDayPanel.items.items[0].store.load();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//仓库饱和度明细(日) model
Ext.define('Foss.platform.stockSaturationDay.stockSaturationDetailModel', {
    extend: 'Ext.data.Model',
    fields : [{
		name : 'saturationDay',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'dangerrange',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'warnrange',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'timeStatisticsGroup',
		type : 'string'
	}]
});
//仓库饱和度明细(日) store
Ext.define('Foss.platform.stockSaturationDay.stockSaturationDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.platform.stockSaturationDay.stockSaturationDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :  platform.realPath('queryStockSaturationDetailDay.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'stockSaturationVo.stockSaturationList',
			successProperty: 'success'
		},
		listeners:{
            exception:function(theproxy, response, operation, options ){
            	Ext.MessageBox.alert('提示',Ext.decode(response.responseText).message);
            }
        }
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.stockSaturationDetail.queryDayForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.beginDate' : queryParams.stockSaturationBeginTime,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.stockSaturationEndTime
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//仓库饱和度明细(日) 曲线图
Ext.define('Foss.platform.stockSaturationDay.stockSaturationDetailChart',{
    extend :  'Ext.chart.Chart',
    animate: false,
    store : Ext.create('Foss.platform.stockSaturationDay.stockSaturationDetailStore'),
    width : 950,
    height : 400,
    insetPadding: 30,
    legend: {
        position: 'top',
        boxZIndex:200
    },
    axes: [{
               type: 'Numeric',
               position: 'left',
               fields: ['saturationDay','dangerrange','warnrange'],
               minimum: 0,
			   title:'日仓库饱和度',
               grid: true,
			   label: {  
                    renderer: Ext.util.Format.numberRenderer('0,0')  
               },  
               label: {
                   renderer: function(saturationDay) {
                	   return saturationDay + '%';
                   }
               }
           },{
               type: 'Category',
               position: 'bottom',
			   title:'日期',
               fields: ['timeStatisticsGroup'],
			   rotate: {
					degrees: 315  
				}
           }
       ],
       series: [{
            type: 'line',
			axis: ['left'],
            xField: 'timeStatisticsGroup',
            yField: 'saturationDay',
            title:'实际值',
            tips: {
                trackMouse: true,
                width: 100,
                height: 40,
                renderer: function(storeItem, item) {
                	this.setTitle(storeItem.get('timeStatisticsGroup'));
                	this.update((storeItem.get('saturationDay')) + '%');
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
            xField: 'timeStatisticsGroup',
            yField: 'warnrange',
            title:'预警值',
            tips: {
                trackMouse: true,
                width: 100,
                height: 40,
                renderer: function(storeItem, item) {
                	this.setTitle(storeItem.get('timeStatisticsGroup'));
                	this.update((storeItem.get('warnrange')) + '%');
                }
            }
        },{
            type: 'line',
            axis: ['left'],
			xField: 'timeStatisticsGroup',
            yField: 'dangerrange',
            title:'预警危险值',
            tips: {
                trackMouse: true,
                width: 100,
                height: 40,
                renderer: function(storeItem, item) {
                	this.setTitle(storeItem.get('timeStatisticsGroup'));
                	this.update((storeItem.get('dangerrange')) + '%');
                }
            }
        }]
});

//仓库饱和度明细(日) 曲线图 面板
Ext.define('Foss.platform.stockSaturationDay.stockSaturationDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : '日仓库饱和度信息',
	frame : true,
	collapsible : true,
	hidden : true,
	animCollapse : true,
	items : [Ext.create('Foss.platform.stockSaturationDay.stockSaturationDetailChart')],
	tbar : [
	   /*{
        text: '导出图片',
        handler: function(){
        	var chart = platform.stockSaturationDetail.resultDayChart.items.items[0];
        	if(chart.store.getCount() > 0){
        		chart.save({
                    type: 'image/png'
                });
        	}else{
        		Ext.ux.Toast.msg('提示', '折线图中没有数据！', 'error', 1500);
				return;
        	}
        }
    },*/{
    	text: '导出数据',
        handler: function(){
        	var actionUrl=platform.realPath('stockSaturationDetailDayExport.action');
			var queryForm = platform.stockSaturationDetail.queryDayForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				var exportParams = {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.beginDate' : queryParams.stockSaturationBeginTime,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.stockSaturationEndTime
				}; 
				if(!Ext.fly('downloadStockSaturationDayFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadStockSaturationDayFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downloadStockSaturationDayFileForm'),
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
//---------------------
Ext.define('Ext.ux.form.MonthField', {
    extend: 'Ext.form.field.Picker',
    alias: 'widget.monthfield',
    //requires: ['Ext.picker.Date'],
    //alternateClassName: ['Ext.form.DateField', 'Ext.form.Date'],


    format: "Y-m",

    altFormats: "m/y|m/Y|m-y|m-Y|my|mY|y/m|Y/m|y-m|Y-m|ym|Ym",

    //disabledDaysText: "Disabled",

    //disabledDatesText: "Disabled",

    //minText: "The date in this field must be equal to or after {0}",

    //maxText: "The date in this field must be equal to or before {0}",

    //invalidText: "{0} is not a valid date - it must be in the format {1}",

    triggerCls: Ext.baseCSSPrefix + 'form-date-trigger',

    //showToday: true,

    //initTime: '12',

    //initTimeFormat: 'H',

    matchFieldWidth: false,

    startDay: new Date(),

    initComponent: function () {
        var me = this;


        me.disabledDatesRE = null;

        me.callParent();
    },

    initValue: function () {
        var me = this,
            value = me.value;

        if (Ext.isString(value)) {
            me.value = Ext.Date.parse(value, this.format);
        }
        if (me.value)
            me.startDay = me.value;
        me.callParent();
    },

    rawToValue: function (rawValue) {
        return Ext.Date.parse(rawValue, this.format) || rawValue || null;
    },

    valueToRaw: function (value) {
        return this.formatDate(value);
    },



    formatDate: function (date) {
        return Ext.isDate(date) ? Ext.Date.dateFormat(date, this.format) : date;
    },
    createPicker: function () {
        var me = this,
            format = Ext.String.format;

        return Ext.create('Ext.picker.Month', {
            //renderTo: me.el,
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            shadow: false,
            focusOnShow: true,
            listeners: {
                scope: me,
                cancelclick: me.onCancelClick,
                okclick: me.onOkClick,
                yeardblclick: me.onOkClick,
                monthdblclick: me.onOkClick
            }
        });
    },

    onExpand: function () {
        //this.picker.show();
        this.picker.setValue(this.startDay);
        //
        
    },

    //    onCollapse: function () {
    //        this.focus(false, 60);
    //    },

    onOkClick: function (picker, value) {
        var me = this,
            month = value[0],
            year = value[1],
            date = new Date(year, month, 1);
        me.startDay = date;
        me.setValue(date);
        this.picker.hide();
        //this.blur();
    },

    onCancelClick: function () {
        this.picker.hide();
        //this.blur();
    }

});

//----------------------------------------
//仓库饱和度明细(月) form
Ext.define('Foss.platform.stockSaturationMonth.stockSaturationDetailQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'outfieldCode',
		xtype : 'dynamicorgcombselector',
		readOnly : !Ext.isEmpty(platform.stockSaturationDetail.currentCode),
		type : 'ORG',
		transferCenter : 'Y',
		allowBlank : false
	}, {
			xtype : 'panel',
			layout : 'column',
			columnWidth : 1/2,
			items : [ {
				name:'stockSaturationBeginTime',
				xtype : 'monthfield',
				fieldLabel : '日期',
				labelAlign : 'right',
				value:new Date(new Date().getFullYear(),new Date().getMonth()-11),
				allowBlank : false,
				disallowBlank : true
			}, {
				html : '-',
				frame : false,
				border : false,
				style : {
					marginLeft : '12px',
					marginTop : '5px'
				}
			}, {
				xtype : 'monthfield',
				editable : false,
				value:new Date(new Date().getFullYear(),new Date().getMonth()),
				name : 'stockSaturationEndTime'
			} ]
		},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			hidden:!Ext.isEmpty(platform.stockSaturationDetail.currentCode),
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				//如果部门为外场，则外场重新赋值
				if(!Ext.isEmpty(platform.stockSaturationDetail.currentCode)){
					form.findField('outfieldCode').setCombValue(
							platform.stockSaturation.outfieldName,
							platform.stockSaturation.outfieldCode
					);
				}
				//填写日期
				form.findField('stockSaturationBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()-12), 'Y-m'));
				form.findField('stockSaturationEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()), 'Y-m'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				platform.stockSaturationDetail.resultMonthPanel.setVisible(true);
				platform.stockSaturationDetail.resultMonthPanel.items.items[0].store.load();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//仓库饱和度明细(月) model
Ext.define('Foss.platform.stockSaturationMonth.stockSaturationDetailModel', {
    extend: 'Ext.data.Model',
    fields : [{
		name : 'saturationMonth',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'dangerrange',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'warnrange',
		type : 'string',
		convert : function(value){
			if(value==null || value==''){
				return 0;
			}else{
				return parseFloat(value);
			}
		}
	},{
		name : 'timeStatisticsGroup',
		type : 'string'
	}]
});
//仓库饱和度明细(月) store
Ext.define('Foss.platform.stockSaturationMonth.stockSaturationDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.platform.stockSaturationMonth.stockSaturationDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryStockSaturationDetailMonth.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'stockSaturationVo.stockSaturationList',
			successProperty: 'success'
		},
		listeners:{
		    exception:function(theproxy, response, operation, options ){
		    	Ext.MessageBox.alert('提示',Ext.decode(response.responseText).message);
		    }
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.stockSaturationDetail.queryMonthForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.beginDate' : queryParams.stockSaturationBeginTime,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.stockSaturationEndTime
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//仓库饱和度明细(月) 曲线图
Ext.define('Foss.platform.stockSaturationMonth.stockSaturationDetailChart',{
    extend :  'Ext.chart.Chart',
    animate: false,
    store : Ext.create('Foss.platform.stockSaturationMonth.stockSaturationDetailStore'),
    width : 950,
    height : 400,
    insetPadding: 30,
    axes: [{
	        type: 'Numeric',
	        position: 'left',
	        fields: ['saturationMonth','warnrange','dangerrange'],
	        minimum: 0,
			title:'月仓库饱和度',
	        grid: true,
	        label: {
	            renderer: function(saturationMonth) {
	         	   return saturationMonth + '%';
	            }
	        }
	    },{
	        type: 'Category',
	        position: 'bottom',
			title:'月份',
	        fields: ['timeStatisticsGroup']
	    }
	],
	series: [{
	     type: 'line',
			axis: ['left','bottom'],
	     xField: 'timeStatisticsGroup',
	     yField: 'saturationMonth',
	     tips: {
	         trackMouse: true,
	         width: 100,
	         height: 40,
	         title:'实际值',
	         renderer: function(storeItem, item) {
	         	this.setTitle(storeItem.get('timeStatisticsGroup'));
	         	this.update((storeItem.get('saturationMonth')) + '%');
	         }
	     }
	 },{
	     type: 'line',
			axis: ['left','bottom'],
	     xField: 'timeStatisticsGroup',
	     yField: 'warnrange',
	     title:'预警值',
	     tips: {
	         trackMouse: true,
	         width: 100,
	         height: 40,
	         renderer: function(storeItem, item) {
	         	this.setTitle(storeItem.get('timeStatisticsGroup'));
	         	this.update((storeItem.get('warnrange')) + '%');
	         }
	     }
	 },{
	     type: 'line',
	     axis: ['left','bottom'],
			xField: 'timeStatisticsGroup',
	     yField: 'dangerrange',
	     title:'预警危险值',
	     tips: {
	         trackMouse: true,
	         width: 100,
	         height: 40,
	         renderer: function(storeItem, item) {
	         	this.setTitle(storeItem.get('timeStatisticsGroup'));
	         	this.update((storeItem.get('dangerrange')) + '%');
	         }
	     }
	 }]
});

//仓库饱和度明细(月) 曲线图 面板
Ext.define('Foss.platform.stockSaturationMonth.stockSaturationDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : '月仓库饱和度信息',
	frame : true,
	collapsible : true,
	hidden : true,
	animCollapse : true,
	items : [Ext.create('Foss.platform.stockSaturationMonth.stockSaturationDetailChart')],
	tbar : [
	   /*{
        text: '导出图片',
        handler: function(){
        	var chart = platform.stockSaturationDetail.resultDayChart.items.items[0];
        	if(chart.store.getCount() > 0){
        		chart.save({
                    type: 'image/png'
                });
        	}else{
        		Ext.ux.Toast.msg('提示', '折线图中没有数据！', 'error', 1500);
				return;
        	}
        }
    },*/{
    	text: '导出数据',
        handler: function(){
        	var actionUrl=platform.realPath('stockSaturationDetailMonthExport.action');
			var queryForm = platform.stockSaturationDetail.queryMonthForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				var exportParams = {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.beginDate' : queryParams.stockSaturationBeginTime,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.stockSaturationEndTime
				}; 
				if(!Ext.fly('downloadStockSaturationMonthFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadStockSaturationMonthFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downloadStockSaturationMonthFileForm'),
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


//主页面
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout=300000;
	//明细(日)
	var queryDayCreateForm = Ext.create('Foss.platform.stockSaturationDay.stockSaturationDetailQueryForm');
	var resultDayCreateChart = Ext.create('Foss.platform.stockSaturationDay.stockSaturationDetailChart');
	var resultDayCreatePanel = Ext.create('Foss.platform.stockSaturationDay.stockSaturationDetailPanel');
	
	platform.stockSaturationDetail.queryDayForm = queryDayCreateForm;
	platform.stockSaturationDetail.resultDayChart = resultDayCreateChart;
	platform.stockSaturationDetail.resultDayPanel = resultDayCreatePanel;
	
	//明细(月)
	
	var queryMonthCreateForm = Ext.create('Foss.platform.stockSaturationMonth.stockSaturationDetailQueryForm');
	var resultMonthCreateChart = Ext.create('Foss.platform.stockSaturationMonth.stockSaturationDetailChart');
	var resultMonthCreatePanel = Ext.create('Foss.platform.stockSaturationMonth.stockSaturationDetailPanel');
	
	platform.stockSaturationDetail.queryMonthForm = queryMonthCreateForm;
	platform.stockSaturationDetail.resultMonthChart = resultMonthCreateChart;
	platform.stockSaturationDetail.resultMonthPanel = resultMonthCreatePanel;
	
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-stockSaturationDetailQueryIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '日仓库饱和度',
				tabConfig : {
					width : 120
				},
				items : [queryDayCreateForm,resultDayCreatePanel]
			}
			,{
				title : '月历史仓库饱和度',
				tabConfig : {
					width : 120
				},
				items : [queryMonthCreateForm,resultMonthCreatePanel]
			}
			]
		}],
		renderTo: 'T_platform-stockSaturationDetailQueryIndex-body'
	});
	
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.stockSaturationDetail.outfieldCode)){
		platform.stockSaturationDetail.queryDayForm.getForm().findField('outfieldCode').setCombValue(
				platform.stockSaturationDetail.outfieldName,
				platform.stockSaturationDetail.outfieldCode
		);
		
		platform.stockSaturationDetail.queryMonthForm.getForm().findField('outfieldCode').setCombValue(
				platform.stockSaturationDetail.outfieldName,
				platform.stockSaturationDetail.outfieldCode
		);
	}
});
