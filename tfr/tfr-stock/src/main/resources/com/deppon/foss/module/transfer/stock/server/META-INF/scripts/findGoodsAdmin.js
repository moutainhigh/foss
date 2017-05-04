//任务状态
Ext.define('Foss.stock.findGoodsAdmin.taskStatusStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
	    {"valueName": "找货中", "valueCode": "find_ing"},
	    {"valueName": "已提交", "valueCode": "submit_end"}
	    ]
});
/**
 * 找货管理  查询
 */
Ext.define('Foss.stock.findGoodsAdmin.QueryForm',{
  extend: 'Ext.form.Panel',
  layout: 'column',
	frame: true,
	border: false,
	title : stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.queryTitle'),//'查询条件',
	defaults: {
		margin: '5 15 15 5',
		columns:3
	},
	items: [{
		//任务编号
		xtype: 'textfield',
        fieldLabel: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskNo'),
        columnWidth:.3,
		name: 'taskNo'
	},{
		//任务状态
		xtype: 'combo',
        fieldLabel: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskStatus'),
		name: 'taskStatus',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: 'submit_end',
		columnWidth:.2,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.findGoodsAdmin.taskStatusStore')
	},{
		//找货人
		xtype: 'commonemployeeselector',
        fieldLabel: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.findGoodsManCode'),
        columnWidth:.3,
        parentOrgCode:stock.findGoodsAdmin.superOrgCode,
        displayField : 'empName',// 显示名称
	    valueField : 'empCode',// 值
		name: 'findGoodsManCode'
	},{
		//任务创建时间
		xtype: 'rangeDateField',
        fieldLabel: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskCreateDate'),
		fieldId: 'Foss_stock_findGoodsAdmin_QueryForm_ApplicationTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'taskCreateDate',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth()-1, new Date().getDate(),
										'00', '00'), 'Y-m-d H:i:s'),		
		toName: 'taskEndDate',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		blankText:'字段不能为空',
		//blankText: stock.findGoodsAdmin.i18n('foss.stock.notnull'),
		columnWidth: .5
	},{
		//货区
		xtype: 'commongoodsareaselector',
        fieldLabel: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.goodsAreaName'),
        displayField : 'goodsAreaName',// 显示名称
	    valueField : 'goodsAreaCode',// 值
	    deptCode: stock.findGoodsAdmin.superOrgCode,
        columnWidth:.3,
		name: 'goodsAreaCode',
		listeners:{
		 expand:function(field, eOpts){
		   field.getStore().addListener('beforeload', function(store, operation, eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
											params : searchParams
										});
							}
							searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = stock.findGoodsAdmin.superOrgCode;
						})
			field.getStore().load();
		 }
		}
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{			
			text:'重置',//findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.button.reset'),	//重置
			columnWidth:.08,
			handler:function(){
				var theForm = this.up('form').getForm();
				theForm.reset();
				theForm.findField('taskCreateDate').setValue( Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth()-1, new Date().getDate(),
										'00', '00'), 'Y-m-d H:i:s'));
				theForm.findField('taskEndDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:'查询',//findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.button.search'),
			disabled: !stock.findGoodsAdmin.isPermission('stockchecking/queryStTaskButton'),
			hidden: !stock.findGoodsAdmin.isPermission('stockchecking/queryStTaskButton'),
			cls: 'yellow_button',
			columnWidth:.08,
			handler: function() {
				var startTime =stock.findGoodsAdmin.findGoodsAdminQueryForm.getForm().getValues().taskCreateDate;
				var endTime = stock.findGoodsAdmin.findGoodsAdminQueryForm.getForm().getValues().taskEndDate;
				var difTime = 0;
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 32){
				Ext.MessageBox.alert(stock.findGoodsAdmin.i18n('Foss.findGoodsAdmin.exception.search.title'), '查询时间跨度不能超过31天'); //查询时间跨度不能超过31天
				//	Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.span.limit')); //“任务创建时间”跨度不能超过7天
					return;
				}
				if(stock.findGoodsAdmin.findGoodsAdminQueryForm.getForm().isValid()){
						stock.findGoodsAdmin.pagingBar.moveFirst();       //pagingBar   分页用的
				}
			}
		}]
	}],
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 * 找货管理Model
 */
Ext.define('Foss.stock.findGoodsAdmin.Model', {
			extend : 'Ext.data.Model',
			fields : [{// 主键ID
				name : 'id',
				type : 'String'
			}, {	// 任务编号
				name : 'taskNo',
				type : 'String'
			}, {// 任务状态 找货中：find_ing 已提交：submit_end
				name : 'taskStatus',
				type : 'String'
			}, {// 货区Code
				name : 'goodsAreaCode',
				type : 'String'
			}, {// 货区所属外场Code
				name : 'orgCode',
				type : 'String'
			}, {// 货区
				name : 'goodsAreaName',
				type : 'String'
			}, {// 找货人Code
				name : 'findGoodsManCode',
				type : 'String'
			}, {// 找货人
				name : 'findGoodsManName',
				type : 'String'
			}, {//任务创建时间
				name : 'taskCreateDate',
				type : 'Date',
				 convert: dateConvert
			}, {//任务创建结束时间
				name : 'taskEndDate',
				type : 'Date',
				 convert: dateConvert
		},{//任务时间
				name : 'taskDate',
				type : 'Date',
				 convert: dateConvert
		},{//任务修改时间（任务结束时间）
		   name:'modifyDate',
		   type : 'Date',
		   convert: dateConvert
		}]
});
/**
 * 找货管理Store
 */
Ext.define('Foss.stock.findGoodsAdmin.Store',{
	extend:'Ext.data.Store',
	model: 'Foss.stock.findGoodsAdmin.Model',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: stock.realPath('queryfindGoodsAdmin.action'),
		reader : {
			type : 'json',
			root : 'findGoodsAdminVo.findGoodsAdminEntitys',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = stock.findGoodsAdmin.findGoodsAdminQueryForm.getValues();	
				Ext.apply(operation, {
					params : {
						'findGoodsAdminVo.findGoodsAdminEntity.taskNo' : queryParams.taskNo,
						'findGoodsAdminVo.findGoodsAdminEntity.taskStatus' : queryParams.taskStatus,
						'findGoodsAdminVo.findGoodsAdminEntity.findGoodsManCode' : queryParams.findGoodsManCode,
						'findGoodsAdminVo.findGoodsAdminEntity.taskCreateDate' : queryParams.taskCreateDate,
						'findGoodsAdminVo.findGoodsAdminEntity.taskEndDate' : queryParams.taskEndDate,
						'findGoodsAdminVo.findGoodsAdminEntity.goodsAreaCode' : queryParams.goodsAreaCode,
						'findGoodsAdminVo.findGoodsAdminEntity.orgCode' : stock.findGoodsAdmin.superOrgCode
					}
				});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
Ext.define('Foss.stock.findGoodsAdmin.Grid',{
  extend:'Ext.grid.Panel',
  autoScroll:true,
	columnLines: true,
    frame: true,
    title :'查询结果',
    height:500,
    bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
//	sortableColumns : false,设置为false则禁用通过单击标题和排序菜单项来进行列排序
//	enableColumnHide : false,设置为false则禁用隐藏表格中的列
//	autoScroll : false,'true'使用溢出：'自动'的组件布局元素，并在必要时自动显示滚动条， 'false'溢出的内容
//	collapsible : false,置为 true 则允许 fieldset 可以收缩,并自动的渲染 收缩/展开 切换按钮到 组头(legend) 元素中, 设置为 false 则让 fieldset 保持静态的大小,也没有收缩按钮. 另一个选择是配置 checkboxToggle. 使用 collapsed 配置项来让 fieldset 初始化为收缩状态.
//	animCollapse : false,true 将会在panel被折叠时使用动画效果，false 将会跳过动画效果
	columns:[{
			//header: '操作', 
     		header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.operate'), 
			dataIndex: 'operate',
			width : 150,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
            tooltip: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.submit'),//'提交',
            disabled: !stock.findGoodsAdmin.isPermission('stock/queryfindGoodsAdminSubmitButton'),
    		hidden: !stock.findGoodsAdmin.isPermission('stock/queryfindGoodsAdminSubmitButton'),
            iconCls:'deppon_icons_edit',
            handler: function(grid, rowIndex, colIndex) {
                	var taskNo = grid.getStore().getAt(rowIndex).data.taskNo;
                	var taskStatus=grid.getStore().getAt(rowIndex).data.taskStatus;
                	if(taskStatus=='submit_end'){
                	  top.Ext.MessageBox.alert(stock.findGoodsAdmin.i18n('Foss.findGoodsAdmin.exception.search.title'),'找货任务已经提交，请勿重复提交');
                	}else{
                		//		由taskNo 提交 找货任务
					Ext.Ajax.request({
						url: stock.realPath('commitFindGoodsAdmin.action'),
						params: {
						'findGoodsAdminVo.taskNo':taskNo
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							stock.findGoodsAdmin.pagingBar.moveFirst();
							Ext.ux.Toast.msg('提示','提交成功！' );
						},
						exception : function(response) {
					    	var result = Ext.decode(response.responseText);
					        top.Ext.MessageBox.alert(stock.findGoodsAdmin.i18n('Foss.findGoodsAdmin.exception.search.title'), result.message);//警告
					    	}
						});
                	}
            }
        },{
            tooltip: stock.findGoodsAdmin.i18n('Foss.stock.findGoodsAdmin.detail'),//'查看',
            iconCls:'deppon_icons_showdetail',
            handler: function(grid, rowIndex, colIndex) {
				//获取taskNo任务编号
                var taskNo = grid.getStore().getAt(rowIndex).data.taskNo;							
				Ext.Ajax.request({
					url: stock.realPath('queryFindGoodsAdminDetail.action'),
					params: {
						'findGoodsAdminVo.taskNo':taskNo
					},
					success: function(response){
						result = Ext.decode(response.responseText);
						var grid = Ext.create('Foss.stock.findGoodsAdminDetailGrid'),
						window = Ext.create('Ext.window.Window', {
						    height:380,
						    width:1200,
						    closable:true,
							closeAction:'hide',
						    modal: true,
						    items: [grid]
						});
					   store = grid.store;
					   store.loadData(result.findGoodsAdminVo.findGoodsAdminDetailEntitys);
					   window.show()
					},
					exception : function(response) {
	    			var result = Ext.decode(response.responseText);
	    			top.Ext.MessageBox.alert(stock.findGoodsAdmin.i18n('Foss.findGoodsAdmin.exception.search.title'), result.message);//警告
	    		}
				});
            }
       
            
        }]
//        //外场经理角色可以看到修改按钮,其余的人不可以看到
//        renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
//        	//如果角色不是外场经理，则隐藏修改按钮
//        	if(stock.findGoodsAdmin.isTransferManager == 'Y'){
//        			this.items[0].iconCls = 'deppon_icons_edit';
//        	}else{
//							this.items[0].iconCls = '';
//					}
//        
//        }	
	},{
    		header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskNo'),//任务编号
			dataIndex: 'taskNo',
			width : 150,
			align: 'center'
        },{
        // '货区', 
        	header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.goodsAreaName'),
			dataIndex: 'goodsAreaName',
			width : 150,
			align: 'center'
        },{
        //  '任务状态', 
			header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskStatus'),
			dataIndex: 'taskStatus',
			width : 130,
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return 'submit_end'==value?'已提交':'找货中';
				}
			},
			align: 'center'
        },{
			//'任务创建时间', 
			header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskCreateDate'), 
			dataIndex: 'taskDate',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 180,
			align: 'center'
        },{
			//'任务完成时间', 
			header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.taskEndDate'), 
			dataIndex: 'modifyDate',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 180,
			align: 'center'
        },{
        	//'找货人',
        	header: stock.findGoodsAdmin.i18n('foss.stock.findGoodsAdmin.findGoodsManCode'),
        	dataIndex:'findGoodsManName',
        	width : 100,
			align: 'center'
        }],
        constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.findGoodsAdmin.Store');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
			//	mode : 'SIMPLE',
				//checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.findGoodsAdmin.pagingBar = me.bbar;
		   me.callParent([cfg]);	
	  }
});
/**
 * 找货管理明细Model
 */
Ext.define('Foss.stock.findGoodsAdminDetail.Model',{
   extend : 'Ext.data.Model',
   fields : [{
            //任务编号      
		  name:"taskNo" ,
		  type:'String'
		},{
		//运单号-->
		  name:"waybillNo" ,
		  type:'String'
		},{
		//流水号   
		  name:"serialNo" ,
		 type:'String'
		},{ 
		//丢货件数 
		  name:"lostGoodsQty" ,
		 type:'String'
		},{
		//总件数      
		  name:"totalQty" ,
		 type:'String'
		},{ 
		//重量
		  name:"weight" ,
		 type:'String'
		},{
		//体积           
		  name:"volume" ,
		 type:'String'
		},{
		//包装     
		  name:"packageType" ,
		 type:'String'
		},{ 
		//提货网点
		  name:"destOrgCode" ,
		 type:'String'
		},{
	    //提货网点名称
		  name:'destOrgName',
		  type:'String'
		},{ 
		 //是否找到(Y是 ,N否)         
		  name:"findType" ,
		 type:'String',
		 convert : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return 'Y'==value?'是':'否';
				}
			}
		},{  
		//上报时间-->    
		  name:"reportDate" ,
		 type:'Date',
		 convert: dateConvert
	}]
});
/**
 * 找货管理明细Store
 */
Ext.define('Foss.stock.findGoodsAdminDetail.Store',{
	extend:'Ext.data.Store',
	model: 'Foss.stock.findGoodsAdminDetail.Model',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
	});
/**
 * 找货管理明细Grid
 */
Ext.define('Foss.stock.findGoodsAdminDetailGrid', {
	extend : 'Ext.grid.Panel',
	autoScroll : true,
	columnLines : true,
	frame : true,
	title : '找货明细',
	height : 300,
	width : 1150,
	emptyText : '找货明细结果为空',
	columns : [ {
			header : "运单号",
			dataIndex : "waybillNo",
			width : 100
		}, {
			header : "流水号",
			dataIndex : "serialNo",
			width : 100
		}, {
			header : "丢货件数",
			dataIndex : "lostGoodsQty",
			width : 80
		}, {
			header : "总件数",
			dataIndex : "totalQty",
			width : 60
		}, {
			header : "重量",
			dataIndex : "weight",
			width : 60
		}, {
			header : "体积",
			dataIndex : "volume",
			width :60
		}, {
			header : "包装",
			dataIndex : "packageType",
			width : 150
		}, {
			header : "提货网点",
			dataIndex : "destOrgName",
			width : 150
		}, {
			header : "是否找到",
			dataIndex : "findType",
			width : 80
		}, {
			header : "上报时间",
			dataIndex : "reportDate",
			width : 150,
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s'
		}],
		 constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.findGoodsAdminDetail.Store');
		   me.callParent([cfg]);	
	  }
});
Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	Ext.Ajax.request({
		url: stock.realPath('querySuperiorOrgByOrgCode.action'),
		async: false,
		success: function(response){
			var result = Ext.decode(response.responseText),
				findGoodsAdminVo = result.findGoodsAdminVo;
			stock.findGoodsAdmin.superOrgCode = findGoodsAdminVo.administrativeInfo.code;
			//是否营业部派送部
			stock.findGoodsAdmin.salesDepartment=findGoodsAdminVo.administrativeInfo.salesDepartment;
		},
		exception: function(response){
	    	var result = Ext.decode(response.responseText);
	    	Ext.ux.Toast.msg(stock.findGoodsAdmin.i18n('foss.stock.prompt'), result.message);
	    }
	});
	var findGoodsAdminQueryForm=Ext.create('Foss.stock.findGoodsAdmin.QueryForm');
	var findGoodsAdminGrid=Ext.create('Foss.stock.findGoodsAdmin.Grid');
	 stock.findGoodsAdmin.findGoodsAdminQueryForm=findGoodsAdminQueryForm;
	 stock.findGoodsAdmin.findGoodsAdminGrid=findGoodsAdminGrid;
	var goodsAreaCode=findGoodsAdminQueryForm.getForm().findField('goodsAreaCode');
	goodsAreaCode.setValue(null);
	//是否营业部派送部  货区查询条件不显示
	if('Y'==stock.findGoodsAdmin.salesDepartment){
	  var goodsAreaCode=findGoodsAdminQueryForm.getForm().findField('goodsAreaCode');
	  goodsAreaCode.setVisible(false);
	}else{
	  goodsAreaCode.setVisible(false);
	}
	
	Ext.create('Ext.panel.Panel',{
	id:'T_stock-findGoodsAdminIndex_content',
	cls:"panelContentNToolbar",
	bodyCls:'panelContent-body',
	items:[findGoodsAdminQueryForm,findGoodsAdminGrid],
	getFindGoodsAdminQueryForm:function(){
	  return this.findGoodsAdminQueryForm;
	},
	getFindGoodsAdminGrid:function(){
	 return this.findGoodsAdminGrid;
	},
	renderTo: 'T_stock-findGoodsAdminIndex-body'
	});
})