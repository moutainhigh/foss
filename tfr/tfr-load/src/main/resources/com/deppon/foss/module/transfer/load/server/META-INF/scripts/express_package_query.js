// 包查询条件form
Ext.define('Foss.load.expresspackagequery.QueryForm', {
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
		fieldLabel : '包号',
		name : 'packageNo',
		validator : function(value){
			if(Ext.isEmpty(value)){
				return true;
			}
			var reg = '^[A-Za-z0-9]+$',
				result = value.match(reg);
			if(result == null){
				return '包号只能输入字母和数字';
			}
			if(value.length>45){
				return '包号过长！';
			}
			return true;
		}
	}, {
		fieldLabel : '到达部门',
		name : 'arriveDeptCode',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	}, {
		fieldLabel : '建包员工号',
		name : 'createUserCode',
		xtype : 'commonemployeeselector'
	},{
		fieldLabel : '包状态',
		name : 'status',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'FINISHED',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	        	{"key":"ALL", "value":"全部"},
	            {"key":"CREATED_BILL", "value":"已生成交接单"},
	            {"key":"FINISHED", "value":"未生成交接单"},
	            {"key":"ALREADY_CANCELED", "value":"已撤销"}
	        ]
	    })
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '建包时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_expresspackage_QueryForm_CreateTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 7,
		fromName : 'beginCreateTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1,0,0,0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'),
		toName : 'endCreateTime',
		allowBlank : false,
		disallowBlank : true
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
			handler : function() {
				this.up('form').getForm().reset();
				//重新初始化建包时间
				this.up('form').getForm().findField('beginCreateTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1,0,0,0), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endCreateTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'));
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
				if(form.isValid()){
					load.expresspackagequery.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义查询包结果列表Model
Ext.define('Foss.load.expresspackagequery.QueryExpressPackageModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'packageNo',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'departOrgCode',
		type : 'string'
	}, {
		name : 'departOrgName',
		type : 'string'
	}, {
		name : 'arriveOrgCode',
		type : 'string'
	}, {
		name : 'arriveOrgName',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	},{
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'weight',
		type : 'number'
	}, {
		name : 'volume',
		type : 'number'
	}, {
		name : 'waybillQty',
		type : 'number'
	}, {
		name : 'goodsQty',
		type : 'number'
	},{
		name : 'endTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'expressPackageType',
		type : 'string'
	}]
});

//定义包列表store
Ext.define('Foss.load.expresspackagequery.QueryExpressPackageStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.expresspackagequery.QueryExpressPackageModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryExpressPackageList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'packageVo.packageList',
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
			var queryParams = load.expresspackagequery.queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'packageVo.queryPackageDto.packageNo' : queryParams.packageNo,
					'packageVo.queryPackageDto.arriveOrgCode' : queryParams.arriveDeptCode,
					'packageVo.queryPackageDto.createUserCode' : queryParams.createUserCode,
					'packageVo.queryPackageDto.status' : queryParams.status,
					'packageVo.queryPackageDto.beginCreateTime' : queryParams.beginCreateTime,
					'packageVo.queryPackageDto.endCreateTime' : queryParams.endCreateTime
				}
			});	
		}
	}
});

//定义撤销结果Model
Ext.define('Foss.load.expresspackagequery.CancelResultModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'packageNo',
		type : 'string'
	}, {
		name : 'beSuccess',
		type : 'string'
	}, {
		name : 'message',
		type : 'string'
	}]
});

//定义撤销结果store
Ext.define('Foss.load.expresspackagequery.CancelResultStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.expresspackagequery.CancelResultModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义撤销结果grid
Ext.define('Foss.load.expresspackagequery.CancelResultGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.load.expresspackagequery.CancelResultStore');
		me.callParent([cfg]);
	},
	columns : [{
			dataIndex : 'packageNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '包号'
	}, {
		dataIndex : 'beSuccess',
		align : 'center',
		width : 80,
		text : '是否成功',
		renderer : function(value){
			if(value === 'Y'){
				return '成功';
			}else if(value === 'N'){
				return '失败';
			}
			return value;
		}
	},{
		dataIndex : 'message',
		xtype : 'linebreakcolumn',
		width : 400,
		text : '失败原因'
	}],
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var beSuccess = record.get('beSuccess');
			if(beSuccess == 'N') {
				return 'result_grid_failure_back_red';
			}
		}
	}
});

//定义撤销结果grid
load.expresspackagequery.cancelResultGrid = Ext.create('Foss.load.expresspackagequery.CancelResultGrid');

//定义撤销结果弹出窗口
Ext.define('Foss.load.expresspackagequery.CancelResultWindow',{
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : true,
	title : '操作结果',
	items : [load.expresspackagequery.cancelResultGrid],
	buttons : [{
		text : '确定',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	}]
});

//修改出发到达时间form
Ext.define('Foss.load.expresspackagequery.ModifyForm',{
	extend : 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:130
	},
	items : [{
		fieldLabel:'包号',
		name:'packageNo',
		columnWidth:.99,
		readOnly:true
	},{
		fieldLabel:'出发站',
		name:'origOrgName',
		columnWidth:.99,
		readOnly:true
	},{
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		fieldLabel:'目的站',
		name:'destOrgName',
		allowBlank : false,
		columnWidth:.99,
		hander:function(){
			
		}
	},{
        xtype: 'container',
        columnWidth:1,
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
        items: [{
			xtype : 'button',
			columnWidth:.15,
			text: '取消',//'取消'
			handler: function() {	
				//this.up('form').getForm().findField('vo.planDto.destOrgCode').reset();
				this.up('window').close();
			}
		},{
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			columnWidth:.15,
			xtype : 'button',
			cls : 'yellow_button',
			text: '提交',//'提交',
			handler: function(){
				var form=this.up('form');
				if(form.getForm().isValid()){
					var destOrgName = form.getForm().findField('destOrgName').getRawValue();
					var param =form.getForm().getValues();
			
					//设置为不可编辑
					this.disable(true);	
					var the=this;
					
					var data = {
								'packageVo.packageNo' : param.packageNo,
								'packageVo.destOrgName' :destOrgName ,
								'packageVo.destOrgCode' :param.destOrgName
							};
						
						
						Ext.Ajax.request({
							url : load.realPath('modifyPackageInfo.action'),
							timeout: 300000,
							params:data,
							success : function(response){
								load.expresspackagequery.queryResultGrid.store.
								getAt(load.expresspackagequery.selectRowIndex).set("arriveOrgName",destOrgName)
								//按钮可编辑
								the.enable();
								//隐藏窗口
								form.up('window').close();
								
								Ext.ux.Toast.msg('提示',"保存成功！");
							},
							exception : function(response){
								//按钮可编辑
								the.enable();
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg('错误提示',result.message);
							},
							failure : function(){
								//按钮可编辑
								the.enable();
								
							}
						})
				}
				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}
});
//定义修改窗口
Ext.define('Foss.load.expresspackagequery.ModifyWindow',{
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : true,
	title : '修改包信息',
	width: 330,
	items : [load.expresspackagequery.ModifyForm=Ext.create('Foss.load.expresspackagequery.ModifyForm')]
});
//定义包查询结果列表
Ext.define('Foss.load.expresspackagequery.QueryExpressPackageGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '包列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		handOverBillNos = new Array();
		me.store = Ext.create('Foss.load.expresspackagequery.QueryExpressPackageStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : load.expresspackagequery.i18n('foss.load.expresspackagequery.resultGrid.addnewButtonText'),/*新增*/
			disabled : !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridAddnewButton'),//权限配置参数
			hidden : !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridAddnewButton'),//权限配置参数
			handler : function(){
				load.addTab('T_load-expresspackageaddnewindex',//对应打开的目标页面js的onReady里定义的renderTo
							load.expresspackagequery.i18n('foss.load.expresspackagequery.resultGrid.addnewMainTabTitle'),//打开的Tab页的标题
							load.realPath('expresspackageaddnewindex.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
		},'->',{
			xtype : 'button',
			text : '撤销',
			disabled: !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridRepealButton'),
			hidden: !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridRepealButton'),
			handler : function(){
				var selectedList = me.getSelectionModel().getSelection();
				if(selectedList.length == 0){
					Ext.ux.Toast.msg('提示', '请至少勾选一个包！', 'error', 1500);
					return;
				}
				Ext.MessageBox.confirm('提示',
						'被撤销的包无法生成交接单，确定要撤销包吗？',
				function(btn){
					if(btn == 'yes'){
						//收好包号
						var packageNoList = new Array();
						for(var i in selectedList){
							var packageNo = selectedList[i].data.packageNo;
							packageNoList.push(packageNo);
						}
						//构造json
						var data = {
							'packageVo' : {
								'packageNoList' : packageNoList
							}
						};
						var myMask = new Ext.LoadMask(load.expresspackagequery.queryResultGrid, {
								msg:"撤销中，请稍候..."
						});
						myMask.show();
						//请求
						Ext.Ajax.request({
							url : load.realPath('cancelExpressPackage.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
								myMask.hide();
								var result = Ext.decode(response.responseText),
									resultList = result.packageVo.resultList;
								//弹出处理结果窗口
								if(load.expresspackagequery.cancelResultWindow == null){
									load.expresspackagequery.cancelResultWindow = Ext.create('Foss.load.expresspackagequery.CancelResultWindow');
								}
								load.expresspackagequery.queryResultGrid.store.load();
								load.expresspackagequery.cancelResultWindow.show();
								load.expresspackagequery.cancelResultGrid.store.loadData(resultList);
							},
							exception : function(response){
								myMask.hide();
								var result = Ext.decode(response.responseText);
				    			top.Ext.MessageBox.alert('提示','撤销失败，' + result.message);
							},
							failure : function(){
								myMask.hide();
							}
						});
					}
				});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		load.expresspackagequery.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
			xtype : 'actioncolumn',
			width : 40,
			text : '操作',
			align : 'center',
			items : [{
				tooltip : '修改包',
				disabled: !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridModifyPackageButton'),
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					var rec = grid.store.getAt(rowIndex);
					var handOverBillNo = rec.get('packageNo');
					var orgName =rec.get('departOrgName');
					load.expresspackagequery.selectRowIndex = rowIndex;
					//弹出新增发车计划窗口
					if(load.expresspackagequery.ModifyWindow==null){
						load.expresspackagequery.ModifyWindow=Ext.create('Foss.load.expresspackagequery.ModifyWindow');
					}
					load.expresspackagequery.ModifyForm.getForm().findField('packageNo').setValue(handOverBillNo);
					load.expresspackagequery.ModifyForm.getForm().findField('origOrgName').setValue(orgName);
					load.expresspackagequery.ModifyWindow.show();
				}
			},{
				tooltip : '生成交接单',
				disabled: !load.expresspackagequery.isPermission('expresspackagequeryindex/QueryExpressPackageGridCreateHandOverBillButton'),
				iconCls : 'foss_icons_tfr_releaseTask',
				handler : function(grid, rowIndex, colIndex) {
					var packageNo = grid.store.getAt(rowIndex).data.packageNo;
					//需要对包的类型进行校验，如果为空运直达包则不能生成交接单
					var packageType = grid.store.getAt(rowIndex).data.expressPackageType;
					if(packageType=='AIRTHROUGH_ARRIVE'){
						Ext.MessageBox.alert('提示','包号'+packageNo+'为商务专递直达包,不允许FOSS端生成交接单!');
						return;
					}
					load.addTab('T_load-expresshandoverbilladdnewindex',//对应打开的目标页面js的onReady里定义的renderTo
						'新增包交接单',
					load.realPath('expresshandoverbilladdnewindex.action') + '?packageNo=' + packageNo);
				}
			} ],
			renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
				var status = record.get('status');
				if(status != 'FINISHED'){
					this.items[0].iconCls = null;
					this.items[1].iconCls = null;
				}else{
					this.items[0].iconCls = 'deppon_icons_edit';
					this.items[1].iconCls = 'foss_icons_tfr_releaseTask';
				}
			}
		}, {
			dataIndex : 'packageNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '包号'
	}, {
		dataIndex : 'status',
		align : 'center',
		width : 86,
		text : '包状态',
		renderer : function(value){
			if(value == 'CREATED_BILL'){
				return '已生成交接单';
			}else if(value == 'FINISHED'){
				return '未生成交接单';
			}else if(value == 'ALREADY_CANCELED'){
				return '已撤销';
			}
			return value;
		}
	},{
		dataIndex : 'departOrgName',
		align : 'center',
		width : 150,
		text : '出发部门'
	}, {
		dataIndex : 'arriveOrgName',
		align : 'center',
		width : 150,
		text : '到达部门'
	}, {
		dataIndex : 'createUserCode',
		align : 'center',
		width : 100,
		text : '建包员工号'
	}, {
		dataIndex : 'createUserName',
		align : 'center',
		width : 100,
		text : '建包员姓名'
	}, {
		dataIndex : 'createTime',
		align : 'center',
		width : 136,
		text : '建包时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 80,
		text : '重量'
	}, {
		dataIndex : 'volume',
		align : 'center',
		width : 80,
		text : '体积'
	}, {
		dataIndex : 'waybillQty',
		align : 'center',
		width : 80,
		text : '票数'
	},{
		dataIndex : 'goodsQty',
		align : 'center',
		width : 80,
		text : '件数'
	},{
		dataIndex : 'endTime',
		align : 'center',
		width : 150,
		text : '建包结束时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}],
	listeners : {
		'beforeselect' : function(rowModel,record,index,eOpts){
			var status = record.get('status');
			//可勾选意味着可撤销，故不能勾选已生成交接单和已撤销的
			if(status == 'ALREADY_CANCELED'
				|| status == 'CREATED_BILL'){
				return false;
			}
		}
	}
});

//定义包查询结果列表
load.expresspackagequery.queryResultGrid = Ext.create('Foss.load.expresspackagequery.QueryExpressPackageGrid');
load.expresspackagequery.queryForm = Ext.create('Foss.load.expresspackagequery.QueryForm');
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-expresspackagequeryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [load.expresspackagequery.queryForm,load.expresspackagequery.queryResultGrid],
		renderTo : 'T_load-expresspackagequeryindex-body'
	});
});