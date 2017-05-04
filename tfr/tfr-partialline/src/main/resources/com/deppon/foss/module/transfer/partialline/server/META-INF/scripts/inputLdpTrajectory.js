var inputLdpTrajectoryData=new Array();

/**状态**/
Ext.define('Foss.partialline.InputLdpTrajectory.status.StatusStore', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : 'entered',
			name : '已录入'
		}, {
			code : 'unentered',
			name : '未录入'//接货
		}, {
			code : '',
			name : '全部'//送货
		}
		]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

/**操作类型**/
Ext.define('Foss.partialline.InputLdpTrajectory.OperateType', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '3',
			name : '派送'
		}, {
			code : '2',
			name : '出发'
		}, {
			code : '1',
			name : '到达'
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

/**
 *快件轨迹列模型 
 ***/
Ext.define('Foss.partialline.InputLdpTrajectory.Model.Grid.DataModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'wayBillNo',//运单号
		type : 'string'
	},{
		name : 'status',//状态
		type : 'string'
	},{
		name : 'lastOperateTime',//最后操作时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'inputedDays',//已录入天数
		type : 'string'
	},{
		name : 'lastOperator',//最后一次操作人
		type : 'string'
	},{
		name : 'agentOrgCode',//快递代理网点编码
		type : 'string'
	},{
		name : 'agentOrgName',//快递代理网点名称
		type : 'string'
	},{
		name : 'agentCompanyCode',//快递代理公司编码
		type : 'string'
	},{
		name : 'agentCompanyName',//快递代理公司名称
		type : 'string'
	}]

});

/**
 *快件轨迹操作记录列模型 
 ***/
Ext.define('Foss.partialline.InputLdpTrajectory.Model.Grid.OperationRecordsModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',//ID
		type : 'string'
	},{
		name : 'operationCity',//操作城市
		type : 'string'
	},{
		name : 'operationType',//操作类型
		type : 'string'
	},{
		name : 'updateTime',//轨迹更新时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'manualOperationTime',//人工操作时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'notes',//最后一次操作人
		type : 'string'
	}]

});


/**
 * 
 * 查询store
 * */
Ext.define('Foss.partialline.InputLdpTrajectory.Grid.OperationRecordsStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	// 绑定一个模型
	model : 'Foss.partialline.InputLdpTrajectory.Model.Grid.OperationRecordsModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		timeout:300000,
//		请求的url
		url :partialline.realPath('queryOperationRecords.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryLdpTrajectoryVo.expressOpreateRecordEntityList',
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
				var wayBillNo=inputLdpTrajectoryData.wayBillNo;
			
				Ext.apply(operation, {
					params : {
						'queryLdpTrajectoryVo.wayBillNo' : wayBillNo//运单号
					}
				});	
			
		}
	}
});

/**
 * 
 * 查询store
 * */
Ext.define('Foss.partialline.InputLdpTrajectory.Grid.DataStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	// 绑定一个模型
	model : 'Foss.partialline.InputLdpTrajectory.Model.Grid.DataModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		timeout:300000,
//		请求的url
url :partialline.realPath('queryLdpTrajectory.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryLdpTrajectoryVo.ldpTrajectoryEntitylist',
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
				var queryParams=Ext.getCmp('Foss_Partialline_InputLdpTrajectory_QueryForm_Id').getForm().getValues();
			
				Ext.apply(operation, {
					params : {
						'queryLdpTrajectoryVo.billGenerationBeginTime' : Ext.Date.parse(queryParams.billGenerationBeginTime,'Y-m-d H:i:s'),//单据生成开始时间
						'queryLdpTrajectoryVo.billGenerationEndTime' : Ext.Date.parse(queryParams.billGenerationEndTime,'Y-m-d H:i:s'),//单据生成结束时间
						'queryLdpTrajectoryVo.wayBillNo' : queryParams.wayBillNo,//运单号
						'queryLdpTrajectoryVo.status' : queryParams.status,//状态
						'queryLdpTrajectoryVo.agentCompanyCode':queryParams.agentCompanyCode//是否运单
						
					}
				});	
			
		}
	}
});


/**录入外发轨迹查询form**/
Ext.define('Foss.Partialline.InputLdpTrajectory.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_Partialline_InputLdpTrajectory_QueryForm_Id',
	layout: 'column',
	frame: true,
	border: false,
    title :partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.queryCondition'),//'查询条件',// 
	defaults: {
		margin: '5 3 7 3',
		labelWidth: 100
	},
	items:[{
		xtype: 'textfield',
		maxLength: 10,
		fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.wayBillNo'),// '运单号',
		name: 'wayBillNo',
		columnWidth:.33
	},{
	   xtype : 'rangeDateField',
	   dateType : 'datetimefield_date97',
	   fromName:'billGenerationBeginTime',
	   fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
			   new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
	   toName: 'billGenerationEndTime',
	   toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
			   new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
	   dateRange:7,
	   editable: false,
	   labelWidth :100,
	   fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.createTime'),//单据生成时间
	   editable:false,
	   allowBlank:false,
	   columnWidth: .45
	},{
		columnWidth:.25,
		xtype: 'combobox',
		fieldLabel: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.status'),
		name:  'status',
		id:'status',
		displayField:   'name',
		valueField:     'code',		
		store: Ext.create('Foss.partialline.InputLdpTrajectory.status.StatusStore'),
		value:''
	},{
		xtype : 'commonLdpAgencyCompanySelector',
		fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.agentOrgName'),
		name:'agentCompanyCode',
		columnWidth:.33
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.reset'),//'重置',
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.8,
			html: '&nbsp;'
		},{
		text:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.query'),//'查询',
		cls:'yellow_button',
		columnWidth:.08,
		handler:function(){
			var form = this.up('form').getForm();
			if(form.isValid()){
				partialline.inputLdpTrajectory.pagingBar.moveFirst();
			   }
		}
	}]}
  ]
})

/**点击编辑时弹出二级界面，可以同步外发轨迹**/
function  inputLdpEdit(rec){
	if(partialline.inputLdpTrajectory.window==null){
		partialline.inputLdpTrajectory.window=Ext.create('Foss.partialline.window.LdpTrajectoryDetail');
	}
	inputLdpTrajectoryData=rec.data;
	Ext.getCmp('Foss_partialline_viewLdpTrajectory_ID').getForm().findField('wayBillNo').setValue(rec.data.wayBillNo);
	partialline.inputLdpTrajectory.DetailGrid.store.load();
	partialline.inputLdpTrajectory.window.show();
}


Ext.define('Foss.Partialline.inputLdpTrajectory.QueryFormGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.list'),
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : '暂无数据',
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.partialline.InputLdpTrajectory.Grid.DataStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 10,
			maximumSize : 20,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['20', 20], ['50', 50],['100', 100]]
			})
		});	
		
		partialline.inputLdpTrajectory.pagingBar = me.bbar;
		me.callParent([ cfg ]);
	},
	columns:[{
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '编辑',
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				var rec = grid.store.getAt(rowIndex);
				inputLdpEdit(rec);
				}
		}]
	},
	{   //运单号
		xtype:'ellipsiscolumn',
		text :partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.wayBillNo'),
		flex : 1,
		dataIndex: 'wayBillNo'
	},{ //状态
		text :partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.status'),
		flex:0.6,
		dataIndex:'status',
		renderer:function(value){
			if(value=='entered'){
				return "已录入";
			}else if(value=='unentered'){
				return "未录入";
			}else{
				return value;
			}
		}
	},{ //最后一次操作时间
		xtype:'ellipsiscolumn',
		text :partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.lastOperateTime'),
		flex :0.8,
		dataIndex:'lastOperateTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),
						value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');

			}
		}
		
	},{ //已录入天数
		xtype:'ellipsiscolumn',
		text :partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.inputedDays'),
		flex :0.8,
		dataIndex:'inputedDays'
	},{ //最后一次操作人
		xtype: 'ellipsiscolumn',
		text : partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.lastOperator'),
		flex : 0.8,
		dataIndex: 'lastOperator'
	},{ //快递代理公司
		xtype:'ellipsiscolumn',
		text :'快递代理网点名称',
		flex : 1,
		hidden:true,
		dataIndex: 'agentOrgName'
	},{ //快递代理公司
		xtype:'ellipsiscolumn',
		text :'快递代理网点编码',
		hidden:true,
		flex : 1,
		dataIndex: 'agentOrgCode'
	},{ //快递代理公司
		xtype:'ellipsiscolumn',
		text :'快递代理公司编码',
		hidden:true,
		flex : 1,
		dataIndex: 'agentCompanyCode'
	},{ //快递代理公司
		xtype:'ellipsiscolumn',
		text :'快递代理公司名称',
		hidden:false,
		flex : 1,
		dataIndex: 'agentCompanyName'
	}]
})

Ext.define('Foss.partialline.InputLdpTrajectory.Form.Express',{
	extend: 'Ext.form.Panel',	
	bodyStyle:'padding:5px 5px 0',
	id:'Foss_partialline_viewLdpTrajectory_ID',
	frame: true,
	layout: 'column',
	fieldDefaults: {
			msgTarget: 'side',
			margin:'5 5 5 0'
		},
	defaults: {
		 xtype:'textfield',
		anchor: '97%'
	},
	items:[
		      {   //运单号
            	   fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.waybillNo'),
            	   name:'wayBillNo',
            	   readOnly:true,
				   columnWidth:.9
		       },{ //代理轨迹更新时间
		    	   xtype:'datefield',
                   format: 'Y-m-d H:i:s',
                   allowBlank:false,
		   		   altFormats: 'Y,m,d H:i:s|Y.m.d H:i:s',
		    	   fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.updateTime'), 
                   name:'updateTime',
                   labelWidth: 120,
                   columnWidth:.4	
		       },{ //操作城市
		    	   allowBlank:false,
		    	   fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.cityName'),
		    	   name:'operationCity',
		    	   labelWidth: 70,
                   columnWidth:.3	
		       },{
		    	   fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.type'),
					xtype: 'radiogroup',
					readOnly:false,
					allowBlank:false,
			        vertical: true,
					columnWidth:.3,
					labelWidth: 50,
					name: 'transferType',
					items: [
			            { id:'Foss.partialline.form.InputLdpTrajectory.fieldLabel.transferType.transferCenter', boxLabel: '中转场', name: 'transferType', inputValue: 'transferCenter' },
			            { id:'Foss.partialline.form.InputLdpTrajectory.fieldLabel.transferType.branch', boxLabel: '网点', name: 'transferType', inputValue: 'branch'}
			        ]
		       },{
		    	   xtype:'container',
		    	   columnWidth:1,
		    	   colspan:3,
		    	   layout:'column',
		    	   items:[{ //操作类型
			    	   labelWidth: 70,
			    	   allowBlank:false,
			    	   columnWidth:.25,
				   		xtype: 'combobox',
				   		fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.operationType'),
				   		name:  'operationType',
				   		id:'operationType',
				   		displayField:   'name',
				   		valueField:     'code',	
			   		   store: Ext.create('Foss.partialline.InputLdpTrajectory.OperateType')
			       },{
			    	   //备注
						xtype:'textfield',
						labelWidth:40,
						allowBlank:true,
						hidden: true, 
						fieldLabel:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.notes'),
						name: 'notes',
						maxLength:300,
						columnWidth:.3
			       }]
		       },{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					xtype:'button',
					text:partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.synchron'),//'同步轨迹',
					cls:'yellow_button',
					columnWidth:.2,
					handler:function(){
						
						var form=this.up('form').getForm()
						var insertParms=form.getValues();
						if(!form.isValid()){
							return;
						}
						var agentCompanyCode=inputLdpTrajectoryData.agentCompanyCode;
						var agentCompanyName=inputLdpTrajectoryData.agentCompanyName;
						var agentOrgCode=inputLdpTrajectoryData.agentOrgCode;
						var agentOrgName=inputLdpTrajectoryData.agentOrgName;
						
						Ext.Ajax.request({
							url : partialline.realPath('insertLdpTrajectory.action'),
							params : {
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.wayBillNo':insertParms.wayBillNo,
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.updateTime':Ext.Date.parse(insertParms.updateTime,'Y-m-d H:i:s'),
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.operationCity':insertParms.operationCity,
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.transferType':insertParms.transferType,
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.operationType':insertParms.operationType,
									'queryLdpTrajectoryVo.expressOpreateRecordEntity.notes':insertParms.notes,
									
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.waybillNo':insertParms.wayBillNo,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.agentCompanyCode':agentCompanyCode,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.agentCompanyName':agentCompanyName,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.agentOrgCode':agentOrgCode,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.agentOrgName':agentOrgName,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.operationcity':insertParms.operationCity,
									'queryLdpTrajectoryVo.ldpExternalBillTrackEntity.operationtype':insertParms.operationType,
							
							},
							success : function(response) {
								partialline.inputLdpTrajectory.DetailGrid.store.load();
								Ext.Msg.alert("提示","同步轨迹成功");//("同步成功")
							},
							exception:function (response){
									var result = Ext.decode(response.responseText);
									Ext.Msg.alert("提示",result.message);
							}
						});
						}
			}]
})

Ext.define('Foss.partialline.InputLdpTrajectory.Detail.OperationRecords',{
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.OperationRecords'),//操作记录
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : '暂无操作记录数据',
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.partialline.InputLdpTrajectory.Grid.OperationRecordsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 10,
			maximumSize : 20,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['20', 20]]
			})
		});	
		
		partialline.inputLdpTrajectory.pagingBar2 = me.bbar;
		me.callParent([ cfg ]);
	},
	columns:[{
		align : 'center',
		xtype : 'actioncolumn',
		text : '操作',//操作
		items: [{
			iconCls: 'deppon_icons_delete',
            tooltip: '删除',//作废
            //disabled:!partialline.inputLdpTrajectory.isPermission('queryDataDictionaryValueByEntity/dataDictionaryDisableButton'),
			width:12,
            handler: function(grid, rowIndex, colIndex) {
            	//获取选中的数据
				var record=grid.getStore().getAt(rowIndex);
				Ext.MessageBox.confirm('提示','是否要删除这条记录？',
				function(e){//是否要作废这条值？
        			if(e=='yes'){//询问是否删除，是则发送请求
        				var id = record.get('id');
        				var params = {'queryLdpTrajectoryVo.expressOpreateRecordEntity.id':id};
        				var url = partialline.realPath('deleteValue.action');
        				Ext.Ajax.request({
        					url : url,
        					params:params,
        					//动态创建表单，显示任务信息
        					success : function(response) {
        						partialline.inputLdpTrajectory.DetailGrid.store.load();
        						Ext.ux.Toast.msg('提示', '删除成功!');			
        					},
        					exception : function(response) {
        						var json = Ext.decode(response.responseText);
        						Ext.MessageBox.alert('提示',json.message);
        					}		
        				});		
        			}
        		})
            	
            }
		}]

	},{
	   xtype : 'ellipsiscolumn',
	   text: 'ID',//ID
	   hidden: true,
	   dataIndex: 'id'		
	},{
	   xtype : 'ellipsiscolumn',
	   text: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.operationCity'),//操作城市
	   flex: 0.8,
	   maxLength:20,
	   dataIndex: 'operationCity'
	},{
	   xtype : 'ellipsiscolumn',
	   text: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.operationType1'),//操作类型
	   flex: 0.8,
	   dataIndex: 'operationType',
	   renderer:function(value){
		   if(value=="3"){
				return  "派送";
			}else if(value=="2"){
				return  "出发";
			}else if(value=="1"){
				return  "到达";
			} else{
				return value;
			}
		  }
	},{
	   xtype : 'ellipsiscolumn',
	   text: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.updateTime1'),//代理轨迹更新时间
	   flex: 1,
	   dataIndex: 'updateTime',//代理轨迹更新时间
	   renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),
						value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');

			}
		}
	},{
	   xtype : 'ellipsiscolumn',
	   text: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.manualOperationTime'),//人工操作时间
	   flex: 1,
	   dataIndex: 'manualOperationTime'	,//人工操作时间
	   renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),
						value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');

			}
		}
	},{
	   xtype : 'ellipsiscolumn',
	   text: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Detail.notes1'),//备注
	   flex: 0.8,
	   dataIndex: 'notes'		
	}]
})

Ext.define('Foss.partialline.window.LdpTrajectoryDetail', {
	extend:'Ext.window.Window',
	title: partialline.inputLdpTrajectory.i18n('Foss.partialline.InputLdpTrajectory.Grid.Express'),//录入快件轨迹,
	modal:true,
	closeAction:'hide',
	width: 800,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[partialline.inputLdpTrajectory.DetailForm=Ext.create('Foss.partialline.InputLdpTrajectory.Form.Express'),
	       partialline.inputLdpTrajectory.DetailGrid=Ext.create('Foss.partialline.InputLdpTrajectory.Detail.OperationRecords'),
	      ],
   listeners:{
	   'hide':function(window,eOpts){
		   window=null;
	   }	
   }
}); 

Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-inputLdpTrajectoryIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [Ext.create('Foss.Partialline.InputLdpTrajectory.QueryForm'),
		         Ext.create('Foss.Partialline.inputLdpTrajectory.QueryFormGrid')],//,{id:'Foss_Partialline_InputLdpTrajectory_QueryFormGrid_Id'})
		        
		renderTo: 'T_partialline-inputLdpTrajectoryIndex-body'
	});	
});

