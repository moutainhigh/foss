
//定义查询条件的panel

Ext.define('Foss.platform.fieldStockMovtion.fieldStockMovtionForm', {
	extend : 'Ext.form.Panel',
	title: platform.fieldStockMovtion.i18n('Foss.platform.queryPDAOnlineUsing.title'),//查询条件
	id : 'Foss_baseinfo_ownVehicle_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 1
	},
	defaults : {
		labelWidth : 120,
		width : 316,
		margin : '8 10 5 10',
		anchor : '100%'
	},
	//height : 245,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		    id:'Foss.platform.fieldStockMovtion.transferCenterCode.id',
		    xtype:'commontransfercenterselector',
		    name:'transferCenterCode',
			fieldLabel:platform.fieldStockMovtion.i18n('Foss.platform.queryPDAOnlineUsing.label.transferCenterCode'), //外场
			displayField:'name', 
			valueField:'orgCode',
			columnWidth:.50,
			listeners: {
	            /*select: {
			       fn: function(src, records, eOpts ) {
			    	   platform.queryPDAOnlineUsing.transferCenterCode=records[0].data.orgCode;
			    	}
		   		}*/
	       }
		  },{
			  id:'Foss.platform.fieldStockMovtion.queryDate.id',
			  xtype:'datefield',
		      fieldLabel:platform.fieldStockMovtion.i18n('Foss.platform.queryPDAOnlineUsing.label.queryDate'),  //查询日期
		      allowBlank:false,
			  name:'queryDate',
			  editable:false,
			  value: login.currentServerTime,
			  format:'Y-m-d H:i:s'
		 }],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [ {
				xtype : 'button',
				width : 70,
				text:platform.fieldStockMovtion.i18n('Foss.platform.queryPDAOnlineUsing.button.search'),  //查询
				cls : 'yellow_button',
				handler : function () {
					if (me.getForm().isValid()) {
						var form = this.up('form').getForm();
						var grid=platform.fieldStockMovtion.queryResult;
						if(!form.isValid()){
							return;
						}
						var store=grid.getStore( );
						//清除掉之前的数据
						store.removeAll(false);
						//重新加载数据
						store.load();
						Ext.getCmp('Foss.platform.fieldStockMovtion.queryDate.id').setValue(new Date());
						
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});


//定义查询pda在线使用的model

Ext.define('Foss.platform.fieldStockMovtion.fieldStockMovtionModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'transferCenterCode',//外场code
				type : 'string'
			}, {
				name : 'transferCenterName',//外场name
				type : 'string'
			}, {
				name : 'arrivedVotes',//到达未卸票数
				type : 'string'
			},{
				name : 'arrivedWeight',//到达未卸重量
				type : 'string'
			}, {
				name : 'arrivedVolume',//到达未卸体积
				type : 'string'
			}, {
				name : 'unloadingVotes',//卸车中票数
				type : 'string'
			}, {
				name : 'unloadingWeight',//卸车中重量
				type : 'string'
			},{
				name : 'unloadingVolume',//卸车中体积
				type : 'string'
			}, {
				name : 'trayVotes',//待叉区库存票数
				type : 'string'
			},{
				name : 'trayWeight',//待叉区库存重量
				type : 'string'
			},{
				name : 'trayVolume',//待叉区库存体积
				type : 'string'
			}, {
				name : 'packagingVotes',//包装库区库存票数
				type : 'string'
			}, {
				name : 'packagingWeight',//包装库区库存重量
				type : 'string'
			}, {
				name : 'packagingVolume',//包装库区库存体积
				type : 'string'
			},{
				name : 'stockVotes',//零担中转库区库存票数
				type : 'string'
			}, {
				name : 'stockWeight',//零担中转库区库存重量
				type : 'string'
			},{
				name : 'stockVolume',//零担中转库区库存体积
				type : 'string'
			}, {
				name : 'deliverStockVotes',//零担派送库区库存票数
				type : 'string'
			},{
				name : 'deliverStockWeight',//零担派送库区库存重量
				type : 'string'
			}, {
				name : 'deliverStockVolume',//零担派送库区库存体积
				type : 'string'
			},{
				name : 'expressStockVotes',//快递中转库区库存票数
				type : 'string'
			},{
				name : 'expressStockWeight',//快递中转库区库存重量
				type : 'string'
			}, {
				name : 'expressStockVolume',//快递中转库区库存体积
				type : 'string'
			}, {
				name : 'expressDeliverStockVotes',//快递派送库区库存票数
				type : 'string'
			}, {
				name : 'expressDeliverStockWeight',//快递派送库区库存重量
				type : 'string'
			},{
				name : 'expressDeliverStockVolume',//快递派送库区库存体积
				type : 'string'
			}, {
				name : 'loadedVotes',//已装车票数
				type : 'string'
			},{
				name : 'loadedWeight',//已装车重量
				type : 'string'
			}, {
				name : 'loadedVolume',//已装车体积
				type : 'string'
			}
		 ]
});
//查询库区库存store
Ext.define('Foss.platform.fieldStockMovtion.fieldStockMovtionStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	// 绑定一个模型
	model : 'Foss.platform.fieldStockMovtion.fieldStockMovtionModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		//Ext.BLANK_IMAGE_URL='sendRateDayQuery.action',
		//Ext.Ajax.timeout=300000,
		url : platform.realPath('queryFieldStockMovtion.action'),
		timeout:3000000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'fieldStockMovtionVo.fieldStockMovtionEntity',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			
				Ext.apply(operation, {
					params : {
						'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode':platform.fieldStockMovtion.transferCenterCode
					}
				});	
		}
	},
			
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日派送率 列表
Ext.define('Foss.platform.fieldStockMovtion.fieldStockMovtionGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	//columnLines: true,
	title : '场内流动库存查询',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	//height : 600,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.fieldStockMovtion.fieldStockMovtionStore');
		/*me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 10,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['30', 30],['50', 50], ['100', 100]]
			})
		});*/
		/*me.tbar = [{
			xtype : 'button',
			text : '导出'
			handler : function() {
			
				// 获取查询参数
				params = { 'queryPDAOnlineUsingVo.orgCode':platform.fieldStockMovtion.transferCenterCode,
						   'queryPDAOnlineUsingVo.queryDate':platform.fieldStockMovtion.queryDate};
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportPDAUsingDetail.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败' '导出失败' ,
								result.message);
					}
				});
			}
		}]*/
	//platform.fieldStockMovtion.pagingBar=me.bbar;
	me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'transferCenterName',
		align : 'center',
		text:'转运场',
		width:150
	}, {
		text : '到达未卸',	
		columns : [{
				text : '票数',
				dataIndex : 'arrivedVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=1;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'arrivedWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'arrivedVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '卸车中',	
		columns : [{
				text : '票数',
				dataIndex : 'unloadingVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=2;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'unloadingWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'unloadingVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '待叉区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'trayVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=3;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'trayWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'trayVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '包装库区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'packagingVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=4;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'packagingWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'packagingVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '零担中转库区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'stockVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=5;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'stockWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'stockVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '零担派送库区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'deliverStockVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=6;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'deliverStockWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'deliverStockVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '快递中转库区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'expressStockVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=7;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'expressStockWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'expressStockVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '快递派送库区库存',	
		columns : [{
				text : '票数',
				dataIndex : 'expressDeliverStockVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=8;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'expressDeliverStockWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'expressDeliverStockVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65
			}
		 ]
		
	},{
		text : '已装车',	
		columns : [{
				text : '票数',
				dataIndex : 'loadedVotes',
				align : 'center',
				xtype : 'ellipsiscolumn',
				width:65,
				renderer : function(value){
					var colIndex=9;
					if(value!=null){
						return '<a href="javascript:platform.fieldStockMovtion.showfieldStockMovtionDetail('+"'" + colIndex + "'"+')">' + value + '</a>';				
					}else{
						return null;
					}
				
				}
		   },{
			    dataIndex : 'loadedWeight',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '重量',
				width:65
			},{
			    dataIndex : 'loadedVolume',
				align : 'center',
				xtype : 'ellipsiscolumn',
				text : '体积',
				width:65

			}
		 ]
		
	}]
});
//定义跳转
platform.fieldStockMovtion.showfieldStockMovtionDetail = function(colIndex){
	platform.addTab('T_platform-fieldStockMovtionDetailIndex','场内流动库存明细',
			platform.realPath('fieldStockMovtionDetailIndex.action') + '?colIndex="' + colIndex + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

// 创建查询form
platform.fieldStockMovtion.fieldStockMovtionForm = Ext.create('Foss.platform.fieldStockMovtion.fieldStockMovtionForm');
// 创建查询grid
platform.fieldStockMovtion.queryResult = Ext.create('Foss.platform.fieldStockMovtion.fieldStockMovtionGrid');
//入口函数
Ext.onReady(function() {
	
		  //通过当前登录部门编码去找顶级外场
          currentOrgCode=FossUserContext.getCurrentDept().code;
          if(currentOrgCode==null||currentOrgCode==''){
             return;
          	  
          }
		 platform.fieldStockMovtion.transferCenterCode='';

	        //去获取外场编码名称
	        Ext.Ajax.request({
				url : platform.realPath('queryTfrNameByCode.action'),
				params: {
						'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode':currentOrgCode
					},
				success : function(response) {
					var json = Ext.decode(response.responseText);
				    var transferCenterName  =json.fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgName;//外场名称
					platform.fieldStockMovtion.transferCenterCode=json.fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode;//外场code					 
					Ext.getCmp('Foss.platform.fieldStockMovtion.transferCenterCode.id').setReadOnly(true);
					Ext.getCmp('Foss.platform.fieldStockMovtion.transferCenterCode.id').setValue(transferCenterName);
					Ext.getCmp('Foss.platform.fieldStockMovtion.queryDate.id').setReadOnly(true);
			        Ext.create('Ext.panel.Panel', {
			        	id : 'T_platform-fieldStockMovtionIndex_content',
			        	cls : "panelContentNToolbar",
			        	bodyCls : 'panelContent-body',
			        	layout : 'auto',
			        	items : [platform.fieldStockMovtion.fieldStockMovtionForm,platform.fieldStockMovtion.queryResult],
			        	renderTo : 'T_platform-fieldStockMovtionIndex-body'
			        });
				},
				exception : function(response) {
				   	 var result = Ext.decode(response.responseText);
					  Ext.MessageBox.alert('提示',result.message);
					
				}
			});

			
			
		});


