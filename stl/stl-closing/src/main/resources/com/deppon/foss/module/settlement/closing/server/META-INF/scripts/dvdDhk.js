
closing.dvdDhk.checkQuery = function(form){
	//校验日期是否正确
	var compareTwoDate = stl.compareTwoDate(form.findField('dvdDhkVo.startDate').getValue(),
			form.findField('dvdDhkVo.endDate').getValue());
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert('温馨提示','查询日期范围不能超过31天！');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert('温馨提示','起始日期不能晚于结束日期！');
		return false;
	}
	
	if (Ext.isEmpty(form.findField('dvdDhkVo.remitOrgCode').getValue())
	  &&Ext.isEmpty(form.findField('dvdDhkVo.byremitOrgCode').getValue())) {
		Ext.Msg.alert('温馨提示','代汇款部门、被代汇款部门，两者必须选择一项！');
		return false;
	}
	return true;
}

/**
 * 查询方法
 */
closing.dvdDhk.queryDvdDhk=function(form,me){
	var m=Ext.getCmp('T_closing-dvdDhk_content');
	if(m){
		
		var isCheck = closing.dvdDhk.checkQuery(form);
		if(!isCheck){
			return;
		}
		
		var grid = m.getQueryGrid();
		var store=grid.getStore();
		if(store){
			if(grid.isHidden()){
				grid.show();
			}
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			// 加载第一页数据
			store.loadPage(1,{
						callback	 : function(records, operation, success) {
							var rawData = store.proxy.reader.rawData;
							if(!success && !rawData.isException){
								Ext.Msg.alert("提示", rawData.message);
								me.enable(true);
								return false;
							}
							
							me.enable(true);
						}
					});
		}
	}

}

/**
 * 导出代汇款明细报表
 */
closing.dvdDhk.exportDvdDhk = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-dvdDhk_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出代汇款明细报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = mainPane.getQueryForm().getValues();
			
			//创建一个form
			if(!Ext.fly('exportDvdDhkForm')){
				var frm = document.createElement('form');
				frm.id = 'exportDvdDhkForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('dvdDhkExport.action'), 
				form: Ext.fly('exportDvdDhkForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//获取响应的json字符串
					var jsonText = Ext.decode(response.responseText.trim());
                   	//导出失败
                   	if(jsonText.message!=null&&jsonText.message!=''){
                     	Ext.Msg.alert('温馨提示',jsonText.message);
                     }
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				}
		    });
			
		}
	});	
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.DvdDhkComboModel', {
			extend : 'Ext.data.Model',
			fields : [{
						/* 显示名 */
						name : 'name',
						type : 'string'
					}, {
						/* 实际值 */
						name : 'value',
						type : 'string'
					}]
		})

// 代汇款明细报表数据模型
Ext.define('Foss.closing.DvdDhkModel', {
			extend : 'Ext.data.Model',
			fields : [
			    {name:'id',               type:'string'},     
				{name:'remitDate',       type:'Date', convert : stl.longToDateConvert},
				{name:'period',           type:'string'},
				{name:'remitOrgCode',   type:'string'},
				{name:'remitOrgName',   type:'string'},
				{name:'byremitOrgCode', type:'string'},
				{name:'byremitOrgName', type:'string'},
				{name:'billNo',          type:'string'},
				{name:'sourceBillNo',   type:'string'},
				{name:'billType',        type:'string'},
				{name:'amount',           type:'decimal'},
				{name:'paymentType',      type:'string'}
				]
		})

//Store
Ext.define('Foss.closing.DvdDhkStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.DvdDhkModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryDvdDhk.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'dvdDhkVo.dvdDhkEntityList',
			totalProperty : 'dvdDhkVo.dvdDhkDto.count'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   		/* Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); */ 		
	   		var queryForm = Ext.getCmp('T_closing-dvdDhk_content').getQueryForm();
			if (queryForm) {
				var form = queryForm.getForm();
				var params = form.getValues();
				
				Ext.apply(operation, {
					params:params
	   			});				
			}
	   			
	   		}
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 定义查询Form
Ext.define('Foss.closing.DvdDhkQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '查询条件',
			bodyCls : 'autoHeight',
			defaults : {
				margin : '10 5 10 5',
				labelWidth : 85,
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'column',
				columns : 3
			},
			items : [{
		    	xtype:'datefield',
		    	name : 'dvdDhkVo.startDate',
		    	fieldLabel:'开始时间',
		    	value: stl.getTargetDate(new Date(),-1),
		    	format:'Y-m-d',
		    	//minValue:new Date(2014,0,1),
		    	allowBlank:false,
		    	columnWidth:.3
			}, {
		    	xtype:'datefield',
		    	name : 'dvdDhkVo.endDate',
		    	fieldLabel:'结束时间',
		    	value: new Date(),
		    	format:'Y-m-d',
		    	//minValue:new Date(2014,0,1),
		    	allowBlank:false,
		    	columnWidth:.3
			},{
				xtype: 'combobox',
				name: 'dvdDhkVo.paymentType',
				fieldLabel: '付款方式',
				store: FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE', null, 
						{'valueCode': '','valueName': '全部'},
						 [closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__CASH, 
						  closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__CARD, 
						  closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER, 
						  closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__NOTE, 
						  closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__ONLINE]),
				queryMode: 'local',
				editable: false,
				displayField: 'valueName',
				valueField: 'valueCode',
				value: '',
				columnWidth : .3
				
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'dvdDhkVo.remitOrgCode',
				fieldLabel : '代汇款部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'dvdDhkVo.byremitOrgCode',
				fieldLabel : '被代汇款部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
			}, {
				xtype : 'combo',
				name : 'dvdDhkVo.billType',
				fieldLabel : '单据类别',
				forceSelection : true,
				store: FossDataDictionary.getDataDictionaryStore('BILL_PARENT_TYPE', null, 
						{'valueCode': '','valueName': '全部'},
						[closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__HK,
						 closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__XS]),
				queryMode: 'local',
				editable: false,
				displayField: 'valueName',
				valueField: 'valueCode',
				value: '',
				columnWidth : .3
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				colspan : 3,
				defaultType : 'button',
				layout : 'column',
				items : [{
							text : '重置',
							columnWidth : .1,
							handler : function(){
								this.up('form').getForm().reset();
							}
						}, {
							xtype : 'container',
							border : false,
							html : '&nbsp;',
							columnWidth : .8
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.dvdDhk.queryDvdDhk(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.DvdDhkQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	columnWidth : 1,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	bodyCls : 'autoHeight',
	frame : true,
	cls : 'autoHeight',
	store : null,
	autoScroll : true,
	height : 650,
	emptyText : '查询结果为空',
	viewConfig : {
		enableTextSelection : true, // 设置行可以选择，进而可以复制
		getRowClass : function(record,rowIndex,rowParams,store){
	            if(record.data.period == '汇总'){ // 汇总的样式
	            	return 'closing-totalBgColor';
	            }else{
	            	return ''; 
	            }
      }
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : me.store,
						pageSize : 100,                                                                       
						maximumSize : 500,
						plugins : 'pagesizeplugin',
						items:[me.getExportButton()]
					});
		}
		return me.pagingToolbar;12
	},
	exportButton:null,
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.dvdDhk.exportDvdDhk,
				disabled:!closing.dvdDhk.isPermission('/stl-web/closing/dvdDhkExport.action'),
				hidden:!closing.dvdDhk.isPermission('/stl-web/closing/dvdDhkExport.action')
			});
		}
		return me.exportButton;
	},
		columns : [ {
			text:'序号',
			xtype:'rownumberer',
			width:40
		},{
			text : 'ID',
			width:10,
			dataIndex: 'id',
			sortable : false,
			hidden: true
		}, {
			text : '代汇款日期',
			width:120,
			sortable : false,
			format : 'Y-m-d H:i:s',
			xtype : 'datecolumn',
			dataIndex : 'remitDate'
		}, {
			text : '代汇款所属期间',
			width:95,
			sortable : false,
			dataIndex : 'period',
			renderer:function(value){
				
				if(value == "汇总"){
					return "汇总";
				}
				
				var ymStr = value.substring(0,6);
				var dd = parseInt(value.substring(6,8));
				
				if(dd < 11){
					return ymStr + '-01';
				}else if(dd > 20){
					return ymStr + '-03';
				}else{
					return ymStr + '-02';
				}
				
	    	}
		}, {
			text : '代汇款部门名称',
			width:95,
			sortable : false,
			dataIndex : 'remitOrgName'
		},  {
			text : '代汇款部门编码',
			width:95,
			sortable : false,
			dataIndex : 'remitOrgCode',
		},{
			text : '被代汇款部门名称（快递代理点部名称）',
			width:95,
			sortable : false,
			dataIndex : 'byremitOrgName'
		}, {
			text : '被代汇款部门编码（快递代理点部编码）',
			width:95,
			sortable : false,
			dataIndex : 'byremitOrgCode'
		}, {
			text : '单据编号',
			width:95,
			sortable : false,
			dataIndex : 'billNo'
		}, {
			text : '来源单号',
			width:95,
			sortable : false,
			dataIndex : 'sourceBillNo'
		}, {
			text : '单据类型',
			width:95,
			sortable : false,
			dataIndex : 'billType',
			renderer:function(value){
				var displayField  = "";
				if(value == closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__HK){
					displayField = "还款单";
				}else if(value == closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__XS){
					displayField = "现金收款单";
				}
	    		return displayField;
	    	}
		}, {
			text : '代汇金额',
			width:95,
			sortable : false,
			dataIndex : 'amount'
		}, {
			text : '付款方式',
			width:95,
			sortable : false,
			dataIndex : 'paymentType',
			renderer:function(value){
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'SETTLEMENT__PAYMENT_TYPE');
		    	return displayField;
			}
		}]
	,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.DvdDhkStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-dvdDhk_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.DvdDhkQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.DvdDhkQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-dvdDhk_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						//获得查询FORM
						getQueryForm : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-dvdDhk-body'
					});
		});