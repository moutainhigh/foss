//转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//发送Ajax请求，发送并接收 json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getEl().mask("数据加载中,请稍等...");
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		timeout:60000,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
			Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getEl().unmask();
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
			Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getEl().unmask();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
			Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getEl().unmask();
		}
	});
};
//Grid中的Store中的模型Model
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDModel', {
    extend: 'Ext.data.Model',
    fields:[{
		name:'id',//ID
		type:'string'
	},{
		name :'startProvinceName',//出发省name
		type : 'string'
	},{
		name : 'startCityName',//出发市name
		type : 'string'
	},{
		name :'startCountyName',//出发县name
		type : 'string'
	},{
		name : 'startProvinceCode',//出发省Code
		type : 'string'
	},{
		name :'startCityCode',//出发市Code
		type : 'string'
	},{
		name : 'startCountyCode',//出发县code
		type : 'string'
	},{
		name :'reachProvinceName',//收货省name
		type : 'string'
	},{
		name : 'reachCityName',//收货市name
		type : 'string'
	},{
		name :'reachCountyName',//收货区name
		type : 'string'
	},{
		name : 'reachProvinceCode',//收货省code
		type : 'string'
	},{
		name :'reachCityCode',//收货市code
		type : 'string'
	},{
		name : 'reachCountyCode',//收货区code
		type : 'string'
	},{
		name :'startAddress',//发货地址
		type : 'string'
	},{
		name : 'reachAddress',//收货地址
		type : 'string'
	},{
		name :'bigHeadOrJBD',//集包地或者大头笔   北京市-北京市    上海宝山运作部
		type : 'string'
	},{
		name :'transferCode',//外场编号
		type : 'string'
	},{
		name : 'type',//  集包地 大头笔
		type : 'string'
	},{
		name :'typeCode',//类型编码 值为 0 或者1
		type : 'string'
	},{
		name :'jbdCode',//集包地专门的编码
		type : 'string'
	},{
		name : 'virtualSiteCode',//
		type : 'string'
	},{
		name:'createDate', //创建时间          
		type : 'date',
		defaultValue : null,
		convert : baseinfo.changeLongToDate    
	},{
		name:'modifyDate', //修改时间                                
		type : 'date',
		defaultValue : null,
		convert : baseinfo.changeLongToDate  
	},{
		name:'createUser',//创建人工号           
		type:'String'                                 
	},{
		name:'modifyUser',//更新人工号 
		type:'String'    
	},{
		name:'active',//是否启用     
		type:'String'     
	},{
		name:'versionNo',//版本号
		defaultValue : null
	}]
});
//store
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDModel',// 
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo
						.realPath('queryRookieWaybillJBDByCondition.action'),// 请求地址
				reader : {
					type : 'json',
					root : 'rookieWaybillJBDVo.rookieWaybillJBDEntityList',// 获取的数据
					totalProperty : 'totalCount'// 总个数
				}
			}
		});
Ext.define('Foss.baseinfo.rookieWaybillJBD.ParameterStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'valueCode',
						type : 'string'
					}, {
						name : 'valueName',
						type : 'string'
					}],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items' // 定义读取JSON数据的根对象
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
//form
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDQueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame: true,
	collapsible: true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	//layout:'column',//列布局
	items :[{
			xtype : 'container',
			//labelWidth : 60,
			//width : 250,
			layout : {
				type : 'table',
				columns : 3,
				margin : '5 5 30 5'
			},
			//layout:'column',
			fieldLabel : '发货地址',// '行政区域',
			items : [{
				xtype : 'commonprovinceselector',
				name : 'startProvinceCode',
				fieldLabel : '发货地址:   省',
				//columnWidth:0.33,
				//allowBlank:false,
				width:250,
				labelWidth : 80,
				listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var city = this.up()
								.query('commonCityByProvinceselector')[0];
						if (null != city) {
							city.parentId = oldValue[0].data.code;
						}

					}
				}
			}, {
				fieldLabel : '市',
				forceSelection : true,
				//allowBlank:false,
				xtype : 'commonCityByProvinceselector',
				name : 'startCityCode',
				queryMode : 'local',
				labelWidth : 25,
				width:180,
				//columnWidth:0.33,
				listeners : {
					expand : function(field, eOpts) {
						var province = field.up()
								.query('commonprovinceselector')[0];
						if (null == province.getValue()
								|| "" == province.getValue()) {
							Ext.MessageBox.alert('提示', '省份不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
					},
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var county = this.up()
								.query('commonAreaByCityselector')[0];
						if (null != county) {
							county.parentId = oldValue[0].data.code;
						}

					}
				}

			}, {
				fieldLabel : '县',
				//columnWidth:0.33,
				//allowBlank:false,
				xtype : 'commonAreaByCityselector',
				labelWidth : 25,
				width:180,
				queryMode : 'local',
				name : 'startCountyCode',
				listeners : {
					expand : function(field, eOpts) {
						var city = field.up()
								.query('commonCityByProvinceselector')[0];
						if (null == city.getValue() || "" == city.getValue()) {
							Ext.MessageBox.alert('提示', '城市不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);
									}
								});
					}
//					select : function(comb, records, obj) {
//						
//				}
		}
		}]
		
		},{
			xtype : 'container',
			//labelWidth : 60,
			//width : 250,
			layout : {
				type : 'table',
				columns : 4,
				margin : '5 5 30 5'
			},
			//layout:'column',
			//fieldLabel : '发货地址',// '行政区域',
			items : [{
				xtype : 'commonprovinceselector',
				name : 'reachProvinceCode',
				//allowBlank:false,
				fieldLabel : '收货地址: 省',
				columnWidth:0.33,
				width:250,
				labelWidth : 80,
				listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var city = this.up()
								.query('commonCityByProvinceselector')[0];
						if (null != city) {
							city.parentId = oldValue[0].data.code;
						}

					}
				}
			}, {
				fieldLabel : '市',
				forceSelection : true,
				//allowBlank:false,
				xtype : 'commonCityByProvinceselector',
				name : 'reachCityCode',
				queryMode : 'local',
				labelWidth : 25,
				width:180,
				columnWidth:0.33,
				listeners : {
					expand : function(field, eOpts) {
						var province = field.up()
								.query('commonprovinceselector')[0];
						if (null == province.getValue()
								|| "" == province.getValue()) {
							Ext.MessageBox.alert('提示', '省份不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
					},
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var county = this.up()
								.query('commonAreaByCityselector')[0];
						if (null != county) {
							county.parentId = oldValue[0].data.code;
						}	
					}
				}

			}, {
				fieldLabel : '县',
				columnWidth:0.33,
				//allowBlank:false,
				xtype : 'commonAreaByCityselector',
				labelWidth : 25,
				width:180,
				queryMode : 'local',
				name : 'reachCountyCode',
				listeners : {
					expand : function(field, eOpts) {
						var city = field.up()
								.query('commonCityByProvinceselector')[0];
						if (null == city.getValue() || "" == city.getValue()) {
							Ext.MessageBox.alert('提示', '城市不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);
									}
								});
					},
					select : function(comb, records, obj) {
				}
		}
		},{
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '类型',//
			//width : 280,
			//colspan : 1,
			width:250,
			labelWidth : 80,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.rookieWaybillJBD.ParameterStore', {
						data : {
							'items' : [{
								'valueCode' : '2',
								'valueName' : '全部'
							},{
								//'valueCode' : 'BigHeadPenType',
								'valueCode' : '0',
								'valueName' : '大头笔'
							}, {
								//'valueCode' : 'JBDType',
								'valueCode' : '1',
								'valueName' : '集包地'
							}]
						}
					}),
		value : '2'
		}]
		
		},{
		xtype : 'container',
		columnWidth:1, 
		border: 1,
		defaultType:'button',
		layout:'column',
		items : [{
			xtype : 'button', 
			text:baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDQueryButton'),
			hidden:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDQueryButton'),
			columnWidth:.1,
			handler: function(){
				var form=this.up('form').getForm();
				form.reset();
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.8
		},{
			xtype : 'button', 
			width:70,
			columnWidth:.1,
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDQueryButton'),
			hidden:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDQueryButton'),
			cls:'yellow_button',
			handler : function() {
				Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getRookieWaybillJBDGrid().getPagingToolbar().moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//Grid
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDGrid', {
	extend: 'Ext.grid.Panel',
	title : '菜鸟电子运单集包地信息',
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//新增WINDOW
	rookieWaybillJBDAddWindow:null,
	getRookieWaybillJBDAddWindow:function(){
		if (this.rookieWaybillJBDAddWindow == null) {
			this.rookieWaybillJBDAddWindow = Ext.create('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDAddWindow');
			this.rookieWaybillJBDAddWindow.parent = this;//父元素
		}
		return this.rookieWaybillJBDAddWindow;
	},
	//修改WINDOW
	rookieWaybillJBDUpdateWindow:null,
	getRookieWaybillJBDUpdateWindow:function(){
		if (this.rookieWaybillJBDUpdateWindow == null) {
			this.rookieWaybillJBDUpdateWindow = Ext.create('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDUpdateWindow');
			this.rookieWaybillJBDUpdateWindow.parent = this;//父元素
		}
		return this.rookieWaybillJBDUpdateWindow;
	},
	//作废
	toVoid: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		if(selections.length>10){
			baseinfo.showWoringMessage('每次最多只能批量作废10条！');//请选择一条进行作废操作！
			return;
		}
		for(var i=0; i<selections.length-1;i++){
			if(selections[i].data.typeCode!=selections[i+1].data.typeCode){
				baseinfo.showWoringMessage('每次批量作废的类型必须一致！');
				return;
			}
		}
		baseinfo.showQuestionMes('是否作废这条数据',function(e){//是否要作废这些？
			if(e=='yes'){//询问是否删除，是则发送请求
				var ids = new Array();//ID数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				var params = {'rookieWaybillJBDVo':{'ids':ids}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes(baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.requestTimeout'));//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteRookieWaybillJBD.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
		
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : '序号'//序号
		},{
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.operate'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
					//获取选中的那一行数据
    				var record=grid.getStore().getAt(rowIndex);
    				var updatewindow=me.getRookieWaybillJBDUpdateWindow();
    				//赋值给更新窗口的属性，用于检验未修改情况下的保存
    				updatewindow.startProvinceName=record.data.startProvinceName;
    				updatewindow.startCityName=record.data.startCityName;
    				updatewindow.startCountyName=record.data.startCountyName;
    				updatewindow.reachProvinceName=record.data.reachProvinceName;
    				updatewindow.reachCityName=record.data.reachCityName;
    				updatewindow.reachCountyName=record.data.reachCountyName;
    				updatewindow.bigHeadOrJBD=record.data.bigHeadOrJBD;
    				updatewindow.jbdCode=record.data.jbdCode;
    				var form=updatewindow.down('form').getForm();
    		form.findField('ID').setValue(record.data.id);		
    		form.findField('startProvinceCode').setCombValue(record.data.startProvinceName,record.data.startProvinceCode);
		   	form.findField('startCityCode').setCombValue(record.data.startCityName,record.data.startCityCode);	
		   	form.findField('startCountyCode').setCombValue(record.data.startCountyName,record.data.startCountyCode);
		   	form.findField('reachProvinceCode').setCombValue(record.data.reachProvinceName,record.data.reachProvinceCode);	
		   	form.findField('reachCityCode').setCombValue(record.data.reachCityName,record.data.reachCityCode);
		   	form.findField('reachCountyCode').setCombValue(record.data.reachCountyName,record.data.reachCountyCode);	
		   	form.findField('jbdCode').setValue(record.data.jbdCode);
		   	form.findField('startProvinceCode').setReadOnly(true);
		   	form.findField('startCityCode').setReadOnly(true);	
		   	form.findField('startCountyCode').setReadOnly(true);
		   	form.findField('reachProvinceCode').setReadOnly(true);	
		   	form.findField('reachCityCode').setReadOnly(true);
		   	form.findField('reachCountyCode').setReadOnly(true);
		   	form.findField('type').setReadOnly(true)
		   	var typeCode=record.data.typeCode;
		   	if(typeCode==='0'){
		   		form.findField('bigHeadPen').setValue(record.data.bigHeadOrJBD);
		   		form.findField('type').setValue(record.data.typeCode);
		   		form.findField('bigHeadPen').setDisabled(false);
				form.findField('JBDName').setDisabled(true);
		   		form.findField('bigHeadPen').setReadOnly(false);
		   		form.findField('JBDName').setReadOnly(true);
		   		form.findField('jbdCode').setReadOnly(true);
		   		form.findField('jbdCode').setDisabled(true);
		   	}else{
		   		form.findField('bigHeadPen').setDisabled(true);
				form.findField('JBDName').setDisabled(false);
		   		form.findField('JBDName').setReadOnly(false);
		   		form.findField('jbdCode').setReadOnly(false);
		   		form.findField('jbdCode').setDisabled(false);
		   		form.findField('bigHeadPen').setReadOnly(true);
		   		form.findField('JBDName').setCombValue(record.data.bigHeadOrJBD,record.data.transferCode);
		   		form.findField('type').setValue(record.data.typeCode);
		   		
		   	}
    			updatewindow.show();
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDDeleteButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes('是否作废这条数据',function(e){//是否要作废这个？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var ids = new Array();//ID数组
            				ids.push(record.get('id'));
            				var params = {'rookieWaybillJBDVo':{'ids':ids}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteRookieWaybillJBD.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : '发货省份',//外场编号
			dataIndex : 'startProvinceName'
		},{
			text : '发货城市',//外场名称
			dataIndex : 'startCityName'
		},{
			text : '发货区域',//
			dataIndex : 'startCountyName'
		},{
			text : '收货省份',//备注
			//flex : 2,
			dataIndex : 'reachProvinceName'
		},{
			text : '收货城市',//
			dataIndex : 'reachCityName'
		},{
			text : '收货区域',//
			dataIndex : 'reachCountyName'
		},{
			text : '大头笔或集包地',//外场编号
			dataIndex : 'bigHeadOrJBD'
		},{
			text : '类型',//外场名称
			dataIndex : 'type'
		},{
			text : '集包地编号',//
			dataIndex : 'jbdCode'
		}];
		me.store = Ext.create('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryRookieWaybillJBDForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//查询大查询，查询条件组织
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.startProvinceCode':queryForm.getForm().findField('startProvinceCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.startCityCode':queryForm.getForm().findField('startCityCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.startCountyCode':queryForm.getForm().findField('startCountyCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.reachProvinceCode':queryForm.getForm().findField('reachProvinceCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCityCode':queryForm.getForm().findField('reachCityCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCountyCode':queryForm.getForm().findField('reachCountyCode').getValue(),
								'rookieWaybillJBDVo.rookieWaybillJBDEntity.typeCode':queryForm.getForm().findField('type').getValue()
							}
						});	
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
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [{
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDAddButton'),
			hidden:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDAddButton'),
			handler :function(){
				me.getRookieWaybillJBDAddWindow().show();
			} 
        },'-',{
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDDeleteButton'),
			hidden:!baseinfo.rookieWaybillJBD.isPermission('rookieWaybillJBD/rookieWaybillJBDDeleteButton'),
			handler :function(){
				me.toVoid();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDAddWindow',{
	extend : 'Ext.window.Window',
	title : '新增',//新增
	closable : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :350,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	form:null,
	getForm:function(){
		if(this.form ==null){
			this.form= Ext.create('Foss.baseinfo.rookieWaybillJBD.Form');
		}
		return this.form;
	},
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			//this.down('form').getForm().findField('shortFieldCode').reset();
			var form=this.down('form').getForm();
			form.findField('startProvinceCode').setReadOnly(false);
			form.findField('startCityCode').setReadOnly(false);
			form.findField('startCountyCode').setReadOnly(false);
			form.reset();
		},
		beforeshow:function(me, eOpts){
			this.down('form').getForm().findField('bigHeadPen').setDisabled(false);
			this.down('form').getForm().findField('JBDName').setDisabled(true);
			this.down('form').getForm().findField('JBDName').setReadOnly(true);
			this.down('form').getForm().findField('bigHeadPen').setReadOnly(false);
			this.down('form').getForm().findField('jbdCode').setDisabled(true);
			this.down('form').getForm().findField('jbdCode').setReadOnly(true);
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//新增窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
					var form=me.down('form').getForm();
//					var typeCode=form.findField('type').getValue();
//					var startProvinceCode=form.findField('startProvinceCode').getValue();
//					var startCityCode=form.findField('startCityCode').getValue();
//					if(typeCode==='0'){
//						if(null==startProvinceCode||""==startProvinceCode||null==startCityCode||""==startCityCode){
//							baseinfo.showInfoMes('发货地址省市不能为空！');
//							return;
//						}
//					}
				if(form.isValid()){//校验form是否通过非空校验
//		    		var model = new Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDModel();
//		    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		var params = {
				'rookieWaybillJBDVo' : {
					'rookieWaybillJBDEntity' : {'startProvinceName' :form.findField('startProvinceCode').getRawValue() }
				}
			};
			params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startProvinceCode=form.findField('startProvinceCode').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCityName=	form.findField('startCityCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCityCode=form.findField('startCityCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCountyName=	form.findField('startCountyCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCountyCode=form.findField('startCountyCode').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachProvinceName=	form.findField('reachProvinceCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachProvinceCode=form.findField('reachProvinceCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCityName=	form.findField('reachCityCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCityCode=form.findField('reachCityCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCountyName=form.findField('reachCountyCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCountyCode=form.findField('reachCountyCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.typeCode=form.findField('type').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.jbdCode=form.findField('jbdCode').getValue();
		    if(form.findField('type').getValue()==='0'){
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.bigHeadOrJBD=form.findField('bigHeadPen').getValue();
		    
		    }
		    if(form.findField('type').getValue()==='1'){
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.transferCode=form.findField('JBDName').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.bigHeadOrJBD=form.findField('JBDName').getRawValue();
		    
		    }
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.type=form.findField('type').getRawValue();
		    		var successFun = function(json){
		    			baseinfo.showInfoMes(json.message);//提示保存成功
		    			var form=me.down('form').getForm();
		    			var typeCode=form.findField('type').getValue();
		    			if(typeCode==='0'){
		    				form.findField('type').setValue('1');
		    				form.findField('bigHeadPen').reset();
		    				form.findField('JBDName').reset();
		    				form.findField('jbdCode').reset();
		    				form.findField('bigHeadPen').setDisabled(true);
		    				form.findField('bigHeadPen').setReadOnly(true);
		    				form.findField('JBDName').setDisabled(false);
		    				form.findField('jbdCode').setDisabled(false);
		    				form.findField('JBDName').setReadOnly(false);
		    				form.findField('jbdCode').setReadOnly(false);
		    				form.findField('startProvinceCode').setReadOnly(true);
		    				form.findField('startCityCode').setReadOnly(true);
		    				form.findField('startCountyCode').setReadOnly(true);
		    				form.findField('startProvinceCode').reset();
		    				form.findField('startCityCode').reset();
		    				form.findField('startCountyCode').reset();
		    			}else{
		    				form.findField('type').setValue('0');
		    				form.findField('bigHeadPen').reset();
		    				form.findField('JBDName').reset();
		    				form.findField('jbdCode').reset();
		    				form.findField('bigHeadPen').setDisabled(false);
		    				form.findField('bigHeadPen').setReadOnly(false);
		    				form.findField('JBDName').setDisabled(true);
		    				form.findField('jbdCode').setDisabled(true);
		    				form.findField('JBDName').setReadOnly(true);
		    				form.findField('jbdCode').setReadOnly(true);
		    				form.findField('startProvinceCode').setReadOnly(false);
		    				form.findField('startCityCode').setReadOnly(false);
		    				form.findField('startCountyCode').setReadOnly(false);
		    			}
						//me.close();
						//刷新查询结果
						Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getRookieWaybillJBDGrid().getPagingToolbar().moveFirst();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							baseinfo.showErrorMes(baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}else{
							baseinfo.showErrorMes(json.message);//提示失败原因
						}
					};
					var url = baseinfo.realPath('addRookieWaybillJBD.action');
					baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				}
				}
		}];
		me.items = [me.getForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '修改',//修改
/*	code:null,
	shortFieldCode:null,*/
	closable : true,
	startProvinceName:null,
	startCityName:null,
	startCountyName:null,
	reachProvinceName:null,
	reachCityName:null,
	reachCountyName:null,
	bigHeadOrJBD:null,
	jbdCode:null,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :350,
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.down('form').getForm().reset();//表格重置
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//修改窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.rookieWaybillJBD.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form=me.down('form');
//				var typeCode=form.getForm().findField('type').getValue();
//				var startProvinceCode=form.getForm().findField('startProvinceCode').getValue();
//				var startCityCode=form.getForm().findField('startCityCode').getValue();
//				if(typeCode==='0'){
//					if(null==startProvinceCode||""==startProvinceCode||null==startCityCode||""==startCityCode){
//						baseinfo.showInfoMes('发货地址省市不能为空！');
//						return;
//					}
//				}
				if(form.getForm().isValid()){//校验form是否通过非空校验
						var startProvinceNam=form.getForm().findField('startProvinceCode').getRawValue();
						var startCityNam=form.getForm().findField('startCityCode').getRawValue();
						var startCountyNam=form.getForm().findField('startCountyCode').getRawValue();
						var reachProvinceNam=form.getForm().findField('reachProvinceCode').getRawValue();
						var reachCityNam=form.getForm().findField('reachCityCode').getRawValue();
						var reachCountyNam=form.getForm().findField('reachCountyCode').getRawValue();
						var jbdCod=form.getForm().findField('jbdCode').getValue();
						var bigHeadOrJB=null;
						if(null==form.getForm().findField('bigHeadPen').getValue()||""==form.getForm().findField('bigHeadPen').getValue()){
							bigHeadOrJB=form.getForm().findField('JBDName').getRawValue();
						}else{
							bigHeadOrJB=form.getForm().findField('bigHeadPen').getValue();
						}
						var bool=startProvinceNam==me.startProvinceName&&startCityNam==me.startCityName&&startCountyNam==me.startCountyName;
						var boole=reachProvinceNam==me.reachProvinceName&&reachCityNam==me.reachCityName&&reachCountyNam==me.reachCountyName;
					if(bool&&boole&&bigHeadOrJB==me.bigHeadOrJBD&&jbdCod==me.jbdCode){
			    		baseinfo.showInfoMes('保存成功!');
			    		me.close();	
					}else{
			    	var params = {
					'rookieWaybillJBDVo' : {
						'rookieWaybillJBDEntity' : {'startProvinceName' :form.getForm().findField('startProvinceCode').getRawValue() }
				}
			};
			params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startProvinceCode=form.getForm().findField('startProvinceCode').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCityName=	form.getForm().findField('startCityCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCityCode=form.getForm().findField('startCityCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCountyName=	form.getForm().findField('startCountyCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.startCountyCode=form.getForm().findField('startCountyCode').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachProvinceName=	form.getForm().findField('reachProvinceCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachProvinceCode=form.getForm().findField('reachProvinceCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCityName=	form.getForm().findField('reachCityCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCityCode=form.getForm().findField('reachCityCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCountyName=form.getForm().findField('reachCountyCode').getRawValue();	
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.reachCountyCode=form.getForm().findField('reachCountyCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.typeCode=form.getForm().findField('type').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.jbdCode=form.getForm().findField('jbdCode').getValue();
		   	params.rookieWaybillJBDVo.rookieWaybillJBDEntity.id=form.getForm().findField('ID').getValue();
		    if(form.getForm().findField('type').getValue()==='0'){
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.bigHeadOrJBD=form.getForm().findField('bigHeadPen').getValue();
		    
		    }
		    if(form.getForm().findField('type').getValue()==='1'){
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.transferCode=form.getForm().findField('JBDName').getValue();
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.bigHeadOrJBD=form.getForm().findField('JBDName').getRawValue();
		    
		    }
		    params.rookieWaybillJBDVo.rookieWaybillJBDEntity.type=form.getForm().findField('type').getRawValue();
			    		var successFun = function(json){
							baseinfo.showInfoMes(json.message);//提示新增成功
							me.close();
							//刷新查询结果
							Ext.getCmp('T_baseinfo-rookieWaybillJBD_content').getRookieWaybillJBDGrid().getPagingToolbar().moveFirst();
						};
						var failureFun = function(json){
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.rookieWaybillJBD_content.i18n('foss.baseinfo.requestTimeout'));//请求超时
							}else{
								baseinfo.showErrorMes(json.message);//提示失败原因
							}
						};
						var url = baseinfo.realPath('updateRookieWaybillJBD.action');
						baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
					}
		    	}
		    	} 
		}];
		me.items = [Ext.create('Foss.baseinfo.rookieWaybillJBD.Form')];
		me.callParent([cfg]);
	}
});
//新增窗口、修改窗口内部的Form
//新增窗口、修改窗口内部的Form
Ext.define('Foss.baseinfo.rookieWaybillJBD.Form', {
	extend : 'Ext.form.Panel',
	frame: true,
//	height : 300,
//	width:300,
	collapsible: true,
		defaults: {
		margin:'0 5 5 5',
		anchor: '99%'
		//labelWidth:80
	},
	layout:'column',
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'container',
			//labelWidth : 60,
			//width : 250,
			layout : {
				type : 'table',
				columns : 3,
				margin : '5 5 30 5'
			},
			//layout:'column',
			fieldLabel : '发货地址',// '行政区域',
			items : [{
				xtype : 'commonprovinceselector',
				name : 'startProvinceCode',
				fieldLabel : '发货地址:   省',
				//columnWidth:0.33,
				//allowBlank:false,
				width:250,
				labelWidth : 80,
				listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var city = this.up()
								.query('commonCityByProvinceselector')[0];
						if (null != city) {
							city.parentId = oldValue[0].data.code;
						}

					}
				}
			}, {
				fieldLabel : '市',
				forceSelection : true,
				//allowBlank:false,
				xtype : 'commonCityByProvinceselector',
				name : 'startCityCode',
				queryMode : 'local',
				labelWidth : 25,
				width:180,
				//columnWidth:0.33,
				listeners : {
					expand : function(field, eOpts) {
						var province = field.up()
								.query('commonprovinceselector')[0];
						if (null == province.getValue()
								|| "" == province.getValue()) {
							Ext.MessageBox.alert('提示', '省份不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
					},
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var county = this.up()
								.query('commonAreaByCityselector')[0];
						if (null != county) {
							county.parentId = oldValue[0].data.code;
						}
							var rookieForm = me.getForm();
						rookieForm.findField('bigHeadPen').reset();
						var startCity=rookieForm.findField('startCityCode').getRawValue();
						var reachCity=rookieForm.findField('reachCityCode').getRawValue();
						var typeCode=rookieForm.findField('type').getValue();
						if(typeCode==='0'){
							if(null==reachCity||""==reachCity){
								rookieForm.findField('bigHeadPen').setValue(startCity);
							}else{
								rookieForm.findField('bigHeadPen').setValue(startCity+'-'+reachCity);
							}
						}
					}
				}

			}, {
				fieldLabel : '县',
				//columnWidth:0.33,
				//allowBlank:false,
				xtype : 'commonAreaByCityselector',
				labelWidth : 25,
				width:180,
				queryMode : 'local',
				name : 'startCountyCode',
				listeners : {
					expand : function(field, eOpts) {
						var city = field.up()
								.query('commonCityByProvinceselector')[0];
						if (null == city.getValue() || "" == city.getValue()) {
							Ext.MessageBox.alert('提示', '城市不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);
									}
								});
					}
//					select : function(comb, records, obj) {
//						
//				}
		}
		}]
		
		},{
			xtype : 'container',
			//labelWidth : 60,
			//width : 250,
			layout : {
				type : 'table',
				columns : 3,
				margin : '5 5 30 5'
			},
			//layout:'column',
			//fieldLabel : '发货地址',// '行政区域',
			items : [{
				xtype : 'commonprovinceselector',
				name : 'reachProvinceCode',
				allowBlank:false,
				fieldLabel : '收货地址: 省',
				columnWidth:0.33,
				width:250,
				labelWidth : 80,
				listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var city = this.up()
								.query('commonCityByProvinceselector')[0];
						if (null != city) {
							city.parentId = oldValue[0].data.code;
						}

					}
				}
			}, {
				fieldLabel : '市',
				forceSelection : true,
				//allowBlank:false,
				xtype : 'commonCityByProvinceselector',
				name : 'reachCityCode',
				queryMode : 'local',
				labelWidth : 25,
				width:180,
				columnWidth:0.33,
				listeners : {
					expand : function(field, eOpts) {
						var province = field.up()
								.query('commonprovinceselector')[0];
						if (null == province.getValue()
								|| "" == province.getValue()) {
							Ext.MessageBox.alert('提示', '省份不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
					},
					select : function(newValue, oldValue, eOpts) {
						var form = this.up();
						var county = this.up()
								.query('commonAreaByCityselector')[0];
						if (null != county) {
							county.parentId = oldValue[0].data.code;
						}
						
						var rookieForm = me.getForm();
						rookieForm.findField('bigHeadPen').reset();
						var startCity=rookieForm.findField('startCityCode').getRawValue();
						var reachCity=rookieForm.findField('reachCityCode').getRawValue();
						var typeCode=rookieForm.findField('type').getValue();
						if(typeCode==='0'){
							if(null==startCity||""==startCity){
									rookieForm.findField('bigHeadPen').setValue(reachCity);
							}else{
								rookieForm.findField('bigHeadPen').setValue(startCity+'-'+reachCity);
							}
						}
						
					}
				}

			}, {
				fieldLabel : '县',
				columnWidth:0.33,
				//allowBlank:false,
				xtype : 'commonAreaByCityselector',
				labelWidth : 25,
				width:180,
				queryMode : 'local',
				name : 'reachCountyCode',
				listeners : {
					expand : function(field, eOpts) {
						var city = field.up()
								.query('commonCityByProvinceselector')[0];
						if (null == city.getValue() || "" == city.getValue()) {
							Ext.MessageBox.alert('提示', '城市不能为空！');
							return;
						}
						field.getStore().load({
									params : {
										"cityVo.parentId" : field.parentId
									},
									callback : function(records, operation,
											success) {
										// console.log(records);
									}
								});
					},
					select : function(comb, records, obj) {
//						var form = me.getForm();
//						form.findField('bigHeadPen').reset();
//						var startCity=form.findField('startCityCode').getRawValue();
//						var reachCity=form.findField('reachCityCode').getRawValue();
//						var typeCode=form.findField('type').getValue();
//						if(typeCode==='BigHeadPenType'){
//							if(null==startCity||""==startCity){
//								Ext.MessageBox.alert('提示', '请先选择发货地址！');
//								form.findField('reachCountyCode').reset();
//							}else{
//								form.findField('bigHeadPen').setValue(startCity+'-'+reachCity);
//							}
//						}
				}
		}
		}]
		
		},{
			xtype : 'textfield',
			fieldLabel : '大头笔信息',
			name : 'bigHeadPen',
			width:250,
			labelWidth : 80,
			allowBlank:false
			//width :400,
			//hidden:true,
			//labelWidth : 80,
			//columnWidth:0.4
	},{
			
			name: 'JBDName',//集包地code
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			transferCenter:'Y',//查询外场
			//columnWidth:0.5,
			//labelWidth:100,
			//width:100,
			//allowBlank:false,
			fieldLabel : '集包地信息',
			width:250,
			labelWidth : 80,
			allowBlank:false
			//readOnly:true
			//hidden:true
	},{
			xtype : 'textfield',
			fieldLabel : '集包地编码',
			//columnWidth:0.5,
			name : 'jbdCode',
			width:250,
			labelWidth : 80,
			regex:/^\d{0,6}$/
			//allowBlank:false
			//readOnly:true
			//width : 30,
			//hidden:true
		}, {
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '类型',//
			//width : 280,
			//colspan : 1,
			width:250,
			labelWidth : 80,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.rookieWaybillJBD.ParameterStore', {
						data : {
							'items' : [{
								//'valueCode' : 'BigHeadPenType',
								'valueCode' : '0',
								'valueName' : '大头笔'
							}, {
								//'valueCode' : 'JBDType',
								'valueCode' : '1',
								'valueName' : '集包地'
							}]
						}
					}),
			 listeners : {
					select : function(newValue, oldValue, eOpts) {
						var form = this.up('form').getForm();
						if(oldValue[0].data.valueCode==='0'){
							form.findField('startProvinceCode').setReadOnly(false);
							form.findField('startCityCode').setReadOnly(false);
							form.findField('startCountyCode').setReadOnly(false);
							form.findField('bigHeadPen').setDisabled(false);
							form.findField('JBDName').setDisabled(true);
							form.findField('JBDName').reset();
							form.findField('jbdCode').reset();
							form.findField('bigHeadPen').setReadOnly(false);
							form.findField('JBDName').setReadOnly(true);
							form.findField('jbdCode').setReadOnly(true);
							form.findField('jbdCode').setDisabled(true);
							var startCity=form.findField('startCityCode').getRawValue();
							var reachCity=form.findField('reachCityCode').getRawValue();
							if(null==startCity||""==startCity){
								form.findField('bigHeadPen').setValue(reachCity);
							}else{
								if(null==reachCity||""==reachCity){
									form.findField('bigHeadPen').setValue(startCity);
								}else{
									form.findField('bigHeadPen').setValue(startCity+'-'+reachCity);
								}
							}
						}
						if(oldValue[0].data.valueCode==='1'){
							form.findField('startProvinceCode').setReadOnly(true);
							form.findField('startCityCode').setReadOnly(true);
							form.findField('startCountyCode').setReadOnly(true);
							form.findField('startProvinceCode').reset();
							form.findField('startCityCode').reset();
							form.findField('startCountyCode').reset();
							form.findField('bigHeadPen').reset();
							form.findField('jbdCode').setDisabled(false);
							form.findField('bigHeadPen').setDisabled(true);
							form.findField('JBDName').setDisabled(false);
							form.findField('JBDName').setReadOnly(false);
							form.findField('jbdCode').setReadOnly(false);
							form.findField('bigHeadPen').setReadOnly(true);
						}
					}
			 },
			 value : '0'
	     },{
//			xtype : 'textfield',
//			fieldLabel : '发货省份',
//			hidden:true,
//			name : 'startProvinceName',
//			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : '发货市',
			hidden:true,
			name : 'startCityName',
			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : '发货区域',
			hidden:true,
			name : 'startCountyName',
			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : '收货省份',
			hidden:true,
			name : 'reachProvinceName',
			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : '收货市',
			hidden:true,
			name : 'reachCityName',
			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : '收货区',
			hidden:true,
			name : 'reachCountyName',
			width : 30
		},{
			xtype : 'textfield',
			fieldLabel : 'ID',
			name : 'ID',
			hidden:true,
			width:250
			//width :400,
			//hidden:true,
			//labelWidth : 80,
			//columnWidth:0.4
	}];
		me.callParent([cfg]);
	}
});
//程序执行的开始
Ext.onReady(function() {
			Ext.QuickTips.init();
			if (Ext.getCmp('T_baseinfo-rookieWaybillJBD_content')) {
				return;
			};
			var queryRookieWaybillJBDForm = Ext
					.create('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDQueryForm'); // 查询FORM
			var rookieWaybillJBDGrid = Ext
					.create('Foss.baseinfo.rookieWaybillJBD.RookieWaybillJBDGrid'); // 查询结果GRID
			Ext.getCmp('T_baseinfo-rookieWaybillJBD').add(Ext.create(
					'Ext.panel.Panel', {
						id : 'T_baseinfo-rookieWaybillJBD_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						// 获得查询FORM
						getQueryRookieWaybillJBDForm : function() {
							return queryRookieWaybillJBDForm;
						},
						// 获得查询结果GRID
						getRookieWaybillJBDGrid : function() {
							return rookieWaybillJBDGrid;
						},
						items : [queryRookieWaybillJBDForm,rookieWaybillJBDGrid]
					}));
		});