/**
 * 查询tab页常量
 */
consumer.queryPodInfo.QUERY_BY_DATE = 'TD';
consumer.queryPodInfo.QUERY_BY_WAYBILL = 'WB';


//签收类型的状态Renderer
consumer.queryPodInfo.podTypeRenderer = function(value){
		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'POD_ENTITY__POD_TYPE');
		return displayField;	
	}

consumer.queryPodInfo.isInitRenderer = function(value){
		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'FOSS_BOOLEAN');
		return displayField;	
	}

/**
 * Form重置方法
 */
consumer.queryPodInfo.reset = function() {
	this.up('form').getForm().reset();
}

/**
 * 设置查询参数
 */
consumer.queryPodInfo.setQueryParams = function(){
	//判断是否进行过查询
	if(Ext.isEmpty(consumer.queryPodInfo.queryType)){
		return false;
	}
	//copy一份值，不然此处分页会有问题
	var searchParams = Ext.clone(consumer.queryPodInfo.searchParams);
	searchParams.queryType = consumer.queryPodInfo.queryType;
	return searchParams;
}
/**
 * 查询方法
 */
consumer.queryPodInfo.query = function(me){
	var form  = me.up('form').getForm();
	if(form.isValid()){
		//获取查询参数
		consumer.queryPodInfo.searchParams = form.getValues();
		//按日期查询
		if (consumer.queryPodInfo.QUERY_BY_DATE ==consumer.queryPodInfo.queryType){
		     var startTime = form.findField('podStartDate').getValue();
	         var endTime = form.findField('podEndDate').getValue();
	    //结束时间
      	if(Ext.isEmpty(endTime)){
		     Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.alertMessage'),consumer.queryPodInfo.i18n('foss.stl.consumer.selectpodEndDate'));
		     return false;
	    }
	    //开始时间
	    if(Ext.isEmpty(startTime)){
		     Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.alertMessage'),consumer.queryPodInfo.i18n('foss.stl.consumer.selectpodStartDate'));
		     return false;
	    }
	    //开始和结束时间
	    if(startTime > endTime){
		     Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.alertMessage'),consumer.queryPodInfo.i18n('foss.stl.consumer.beginDateBeforeEndDate'));
		     return false;
	    }
        //开始时间和结束时间的跨度设置
	    if(stl.compareTwoDate(startTime,endTime)>stl.DATELIMITDAYS_MONTH){
			     Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.alertMessage'),consumer.queryPodInfo.i18n('foss.stl.consumer.betweenStartDateAndEndDate')+consumer.queryPodInfo.i18n('foss.stl.consumer.day'));
			 return false;
	    } 
     }
		//按运单号查询
		if (!(consumer.queryPodInfo.QUERY_BY_DATE ==consumer.queryPodInfo.queryType)){
			var billNos = consumer.queryPodInfo.searchParams.waybillNos;
			var billNosArray_tmp = stl.selectWaybillNosArray(billNos);
			var billNosArray=new Array();
			for(var i=0;i<billNosArray_tmp.length;i++){
					billNosArray.push(billNosArray_tmp[i]);
			}	 
			if(billNosArray.length==0){
				Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.warmlyReminder'),consumer.queryPodInfo.i18n('foss.stl.consumer.inputWaybillNo'));
			 	return false;
			}
			if(billNosArray.length>10){
				Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.warmlyReminder'),consumer.queryPodInfo.i18n('foss.stl.consumer.inputWaybillNo'));
			 	return false;
			}
			consumer.queryPodInfo.searchParams.waybillNos = billNosArray;
		}
		var grid = Ext.getCmp('T_consumer-queryPodInfo_content').getAreaGrid();
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.warmlyReminder'),rawData.message);
				me.enable(true);
				return false;
			}  
	    	//正常返回
	    	if(success){
	    		var result = Ext.decode(operation.response.responseText);
	    		Ext.getCmp('totalCount').setValue(result.vo.dto.totalCount);
	    	 }
	       }
		}); 
	}else{
		Ext.Msg.alert(consumer.queryPodInfo.i18n('foss.stl.consumer.warmlyReminder'),consumer.queryPodInfo.i18n('foss.stl.consumer.checkInputParameters'));
		return false;
	}
}
// 签收记录 Model
Ext.define('Foss.consumer.PodInfoReportGridStoreModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'waybillNo'// 运单号
					, 'podType'// 签收类型
					, 'podUserCode'// 签收用户编号
					, 'podUserName'// 签收用户姓名
					, 'podOrgCode'// 签收部门编码
					, 'podOrgName'// 签收部门名称
					, 'isInit'// 是否初始化
					, {
						name : 'podDate',// 签收日期
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createTime',// 创建时间
						type : 'date',
						convert : dateConvert
					}]
		});

// 签收记录 Store
Ext.define('Foss.consumer.PodInfoReportGridStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.consumer.PodInfoReportGridStoreModel',
			sorters : [{
						property : 'createTime',
						direction : 'DESC'
					}],
			pageSize : 100,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : consumer.realPath('podInfo.action'),
				reader : {
					type : 'json',
					root : 'vo.dtoList',
					totalProperty : 'totalCount'
				}
			},
	     	listeners : {
		           'beforeLoad':function(store, operation, eOpts){
			        var params = consumer.queryPodInfo.setQueryParams();
			        var vo = new Object();
			        for(var p in params){
				    var aa = 'vo.dto.'+p;
				    vo[aa]=params[p];
			}
			Ext.apply(operation, {
				params: vo
			});
		}
	}
});

// 签收记录 Grid
Ext.define('Foss.consumer.PodInfoReportGrid', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_PodInfo_Id',
			title : consumer.queryPodInfo
					.i18n('foss.stl.consumer.podInfoReportGrid.title'),
			columnWidth : 1,
			stripeRows : true,
			columnLines : true,
			collapsible : false,
			bodyCls : 'autoHeight',
			emptyText : consumer.queryPodInfo
					.i18n('foss.stl.consumer.emptyResult.title'),
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			sortableColumns : false,
			frame : true,
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			// 增加表格列的分割线
			cls : 'autoHeight',
			store : Ext.create('Foss.consumer.PodInfoReportGridStore'),
			autoScroll : true,
			height : 420,
			// 分页
			columns : [{
						text : 'ID',
						dataIndex : 'id',
						width : 100,
						hidden : true
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.waybillNo'),
						dataIndex : 'waybillNo',
						width : 90
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podDate'),
						dataIndex : 'podDate',
						width : 100,
						renderer : function(value) {
							if (value != null) {
								return Ext.Date
										.format(new Date(value), 'Y-m-d H:i:s');
							} else {
								return value;
							}
						}
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podType'),
						dataIndex : 'podType',
						width : 110,
						renderer:consumer.queryPodInfo.podTypeRenderer
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podUserCode'),
						dataIndex : 'podUserCode',
						width : 110
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podUserName'),
						dataIndex : 'podUserName',
						width : 110
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podOrgCode'),
						dataIndex : 'podOrgCode',
						width : 110
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.podOrgName'),
						dataIndex : 'podOrgName',
						width : 110
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.createTime'),
						dataIndex : 'createTime',
						width : 110,
						renderer : function(value) {
							if (value != null) {
								return Ext.Date
										.format(new Date(value), 'Y-m-d H:i:s');
							} else {
								return value;
							}
						}
					}, {
						text : consumer.queryPodInfo
								.i18n('foss.stl.consumer.isInit'),
						dataIndex : 'isInit',
						width : 110,
						renderer:consumer.queryPodInfo.isInitRenderer
					}],
	        initComponent : function() {
		                var me = this;
		                me.dockedItems = [{
			                  xtype : 'toolbar',
			                  dock : 'bottom',
			                  layout : 'column',
			                  defaults : {
				                  margin : '0 0 5 3'
			                  },
			                  items : [{
				                  xtype : 'textfield',
				                  readOnly : true,
				                  name : 'total',
				                  id:'total',
				                  columnWidth : .1,
				                  labelWidth : 40,
				                  fieldLabel : consumer.queryPodInfo.i18n('foss.stl.consumer.total')
			                  }, {
				                  xtype : 'textfield',
				                  readOnly : true,
				                  name : 'totalCount',
				                  id:'totalCount',
				                  columnWidth : .2,
				                  labelWidth : 100,
				                  fieldLabel : consumer.queryPodInfo.i18n('foss.stl.consumer.totalCount')
			                  }, {
				                  xtype : 'standardpaging',
				                  store : me.store,
				                  columnWidth : 1,
				                  plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					              // 设置分页记录最大值，防止输入过大的数值
					                maximumSize : 1000,
					                sizeList : [['20', 20], ['50', 50], ['100', 100]]
				              })
			             }]
		           }];
		   me.callParent();
	       }
		});

// 按日期查询签收记录信息 FORM
Ext.define('Foss.consumer.queryPodInfoDateForm', {
	extend : 'Ext.form.Panel',
	bodyPadding : 10,
	defaults : {
		margin : 5,
		labelWidth : 85,
		columnWidth : .33
	},
	height : 160,
	layout : 'column',
	items : [{
		xtype : 'datefield',
		name : 'podStartDate',
		fieldLabel : consumer.queryPodInfo
				.i18n('foss.stl.consumer.podStartDate'),
		format : 'Y-m-d',
		value : new Date(),
		editable : false,
		allowBlank : false,
		maxValue : new Date()
	}, {
		xtype : 'datefield',
		name : 'podEndDate',
		fieldLabel : consumer.queryPodInfo.i18n('foss.stl.consumer.podEndDate'),
		format : 'Y-m-d',
		value : new Date(),
		editable : false,
		allowBlank : false,
		maxValue : new Date()
	}, {
		xtype : 'dynamicorgcombselector',
		name : 'PodOrgCode',
		fieldLabel : consumer.queryPodInfo.i18n('foss.stl.consumer.podOrgCode'),
		listWidth : 300,// 设置下拉框宽度
		isPaging : true, // 分页
		disabled : false,
		columnWidth : .34
	}, {
		xtype : 'combo',
		name : 'podType',
		fieldLabel : consumer.queryPodInfo.i18n('foss.stl.consumer.podType'),
		value : '',
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store : FossDataDictionary.getDataDictionaryStore(
				'POD_ENTITY__POD_TYPE', null, {
					'valueCode' : '',
					'valueName' : consumer.queryPodInfo
							.i18n('foss.stl.consumer.all')
				})
	}, {
		xtype : 'container',
		border : false,
		html : '&nbsp;',
		height : 4,
		columnWidth : 1
	}, {
		xtype : 'button',
		text : consumer.queryPodInfo.i18n('foss.stl.consumer.reset'),
		columnWidth : .1,
		handler : consumer.queryPodInfo.reset
	}, {
		xtype : 'container',
		border : false,
		html : '&nbsp;',
		columnWidth : .55
	}, {
		xtype : 'button',
		text : consumer.queryPodInfo.i18n('foss.stl.consumer.query'),
		columnWidth : .1,
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var me = this;
			consumer.queryPodInfo.queryType = consumer.queryPodInfo.QUERY_BY_DATE;
			consumer.queryPodInfo.query(me);
		}
	}]
});

// 按运单号查询签收记录 FORM
Ext.define('Foss.consumer.queryPodInfoWaybillNosForm', {
			extend : 'Ext.form.Panel',
			bodyPadding : 10,
			defaults : {
				margin : 5,
				labelWidth : 85
			},
			height : 160,
			layout : 'column',
			items : [{
				xtype : 'textareafield',
				height : 62,
				name : 'waybillNos',
				fieldLabel : consumer.queryPodInfo
						.i18n('foss.stl.consumer.waybillNo'),
				emptyText : consumer.queryPodInfo
						.i18n('foss.stl.consumer.inputWaybillNo'),
				regex : /^\s*\d{7,12}([\s,.\.、\r\n，。\t]+\d{7,12}){0,9}\s*$/,
				regexText : consumer.queryPodInfo
						.i18n('foss.stl.consumer.validateWaybillNo'),
				columnWidth : .75,
				allowBlank : false
			}, {
				xtype : 'container',
				border : false,
				columnWidth : .25,
				height : 72
			}, {
				xtype : 'button',
				text : consumer.queryPodInfo.i18n('foss.stl.consumer.reset'),
				handler : consumer.queryPodInfo.reset,
				columnWidth : .1

			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				height : 24,
				columnWidth : .55

			}, {
				xtype : 'button',
				text : consumer.queryPodInfo.i18n('foss.stl.consumer.query'),
				cls : 'yellow_button',
				columnWidth : .1,
				handler : function() {
					var form = this.up('form').getForm();
			        var me = this;
			        consumer.queryPodInfo.queryType = consumer.queryPodInfo.QUERY_BY_WAYBILL;
			        consumer.queryPodInfo.query(me);
				}	
			}]
		});

/**
 * 签收记录查询tab
 */
Ext.define('Foss.consumer.queryPodInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth : 1,
	height : 175,
	layout : 'fit',
	items : [{
				title : consumer.queryPodInfo
						.i18n('foss.stl.consumer.searchByDate'),
				tabConfig : {
					width : 120
				},
				items : [Ext.create('Foss.consumer.queryPodInfoDateForm')]
			}, {
				title : consumer.queryPodInfo
						.i18n('foss.stl.consumer.searchByWaybillNos'),
				tabConfig : {
					width : 120
				},
				items : [Ext.create('Foss.consumer.queryPodInfoWaybillNosForm')]
			}]
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryTab = Ext.create('Foss.consumer.queryPodInfoTab');// 查询FORM
			var areaGrid = Ext.create('Foss.consumer.PodInfoReportGrid');// 查询结果GRID
			Ext.create('Ext.panel.Panel', {
						id : 'T_consumer-queryPodInfo_content',
						cls : "panelContentNToolbar", // 必须添加,内容定位用。
						bodyCls : 'panelContentNToolbar-body', // 必须添加,内容定位用。
						layout : 'auto',
						// 获得查询FORM
						getQueryTab : function() {
							return queryTab;
						},
						// 获得查询结果GRID
						getAreaGrid : function() {
							return areaGrid;
						},
						items : [queryTab, areaGrid],
						renderTo : 'T_consumer-queryPodInfo-body'
					});
		});