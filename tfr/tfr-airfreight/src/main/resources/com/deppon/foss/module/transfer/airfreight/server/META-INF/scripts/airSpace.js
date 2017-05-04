//舱位管理模块

//舱位列表信息Model
Ext.define('foss.airfreight.airSapceModel', {
	extend: 'Ext.data.Model',
	//指定model的id,以免重复
	idProperty : 'uuid',
	//定义字段
	fields: [
		{name: 'uuid',type:'string'}, // uuid编号
		{name: 'id',type:'string'}, // ID编号
		{name: 'assembleOrgCode',type:'string'}, //配载部门编号
		{name: 'assembleOrgName',type:'string'}, //配载部门名称
		{name: 'arrvRegionCode',type:'string'}, //目的站编号
		{name: 'arrvRegionName',type:'string'}, //目的站名称
		{name: 'takeOffDate',type:'date',		//出发日期
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}
		}},										
		{name: 'flightType', type: 'string'},	//航班类型
    	{name: 'spaceTotal', type: 'string'},	//总舱位
		{name: 'bookingSpaceTotal', type: 'string'},	//营业部已订舱位
	    {name: 'acceptedSpaceTotal', type: 'string'},	//总调已受理订舱总量
	    {name: 'airWaybillTotal', type: 'string'}	//营业部已开单空运货量
	]
});


/**舱位信息Store*/
Ext.define('foss.airfreight.airSapceStore', {
	extend: 'Ext.data.Store',
    model: 'foss.airfreight.airSapceModel',
    pageSize:10,
    proxy: {
      	type: 'ajax',
        url: airfreight.realPath('queryAirSpace.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.airSpaceDtoList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        }
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = airfreight.querySpaceForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				if(airfreight.isAfterSave!=null){
					queryParams.takeOffDateTo = Ext.Date.format(airfreight.isAfterSave,'Y-m-d') + ' 23:59:59';
				}
				Ext.apply(operation, {
					params : {
						'vo.dto.assembleOrgCode' : queryParams.assembleOrgCode,  //配载部门
						'vo.dto.takeOffDateFrom' : queryParams.takeOffDateFrom,	 //日期开始
						'vo.dto.takeOffDateTo' : queryParams.takeOffDateTo,	//日期结束
						'vo.dto.arrvRegionCode' : queryParams.arrvRegionCode, //目的站
						'vo.dto.flightType' :queryParams.flightType //航班类型
					}
				});	
			}
		},
		load: function(store,records,successful,epots){
		 	if(store.getCount() == 0){
				 Ext.ux.Toast.msg(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'), airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.noData'), 'ok', 1000);  //提示没有查询到数据
			}
		}
		
	}
});


/**查询舱位条件FORM*/
Ext.define('foss.airfreight.airSpaceQueryForm',{
	extend: 'Ext.form.Panel',
	title: airfreight.airSpace.i18n('foss.airfreight.airSpaceQueryForm.title'),    //查询条件
	frame:true,
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 75
	},
	layout:'column',
	items:[{
		xtype: 'dynamicorgcombselector',
		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.assembleOrgCode'),    //部门
		name: 'assembleOrgCode',
		readOnly:true,
		allowBlank:false,
		type:'ORG',
		doAirDispatch:'Y',
		margin: '5 0 5 0',
		columnWidth: .25
	},{
		xtype: 'rangeDateField',
		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.takeOffDate'),    //日期
		dateType: 'datefield',
		fromName: 'takeOffDateFrom',
		dateType: 'datetimefield_date97',
		toName: 'takeOffDateTo',
		dateRange:31, //时间跨度不能大于一个月
		margin: '5 0 5 0',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		columnWidth: .5
	},{
		xtype: 'commoncityselector',
		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.arrvRegionCode'),    //目的站
		name: 'arrvRegionCode',
		margin: '5 0 5 0',
		columnWidth: .25
	},
	FossDataDictionary.getDataDictionaryCombo('AIR_FLIGHT_TYPE',{
		name: 'flightType',
		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.flightType'),    //航班类型
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .25
		},{
			'valueCode':'',
			'valueName': airfreight.airSpace.i18n('foss.airfreight.airSpace.options.all'),    //全部
		})
	,{
		xtype: 'container',
		border : false,
		columnWidth:.75,
		html: '&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
				text:  airfreight.airSpace.i18n('foss.airfreight.airSpaceQueryForm.button.reset'), //重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
					form.findField('takeOffDateFrom').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,'00','00','00'), 'Y-m-d H:i:s'));
					form.findField('takeOffDateTo').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,'23','59','59'), 'Y-m-d H:i:s'));
					var cmbOrgCode = form.findField('assembleOrgCode');
					if(airfreight.dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
						cmbOrgCode.setValue(airfreight.dept.code);
					}
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: airfreight.airSpace.i18n('foss.airfreight.airSpaceQueryForm.button.search'), //查询
				cls:'yellow_button',
				columnWidth:.08,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 2
				}),
				handler:function(){
					var form = this.up('form').getForm();
					airfreight.isAfterSave = null;
					if(form.isValid()){
						airfreight.airSpacePagingBar.moveFirst();
					}
				}
		}]
	}],
	listeners : {
		render : function(panel,text){
			var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
			Ext.Ajax.request({
				url : airfreight.realPath('queryAirDispatch.action'),
				jsonData:array,
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
					airfreight.dept = dept;
					var cmbOrgCode = panel.getForm().findField('assembleOrgCode');
					if(airfreight.dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
						cmbOrgCode.setValue(airfreight.dept.code);
					}
				}
			});
		}
	}
});

/**
 * 舱位信息列表
 */
Ext.define('foss.airfreight.airSpaceGrid',{
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
    columnLines: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
    emptyText: airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.queryIsNull'),    //查询结果为空
    frame: true,
    stripeRows: true,
    title:airfreight.airSpace.i18n('foss.airfreight.airSpaceGrid.title'),    //舱位列表信息
    collapsible: true,
	animCollapse: true,
	columns: [{
		xtype:'actioncolumn',
		width:80,
		text: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.operator'),    //操作
		items: [{
			iconCls: 'deppon_icons_edit',
			tooltip: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.edit'),    //编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				Ext.Ajax.request({
							params: {'vo.dto.id': record.get('id')},
							url: airfreight.realPath('queryAirSpaceById.action'),
							success:function(response){
								var result = Ext.decode(response.responseText);
								var window= Ext.getCmp('foss_airfreight_airSapceInputWindow_Id'); 
								if(window ==  null){
									window=Ext.create('foss.airfreight.airSapceInputWindow');
								}
								var form = Ext.getCmp('foss_airfreight_airSapceForm_ID');
								form.getForm().setValues(result.vo.dto);
		        				var cmbArrvRegionCode = Ext.getCmp('foss_airfreight_airSapceForm_ID').getForm().findField('arrvRegionCode');
		        				cmbArrvRegionCode.getStore().load({params:{'cityVo.name' : record.data.arrvRegionName}});
								form.getForm().findField('takeOffDate').setValue(new Date(result.vo.dto.takeOffDate));
								form.isAddNew = false;
								form.origValue = form.getValues();
								window.center().show();
							},
							failure:function(response){
								 var result = Ext.decode(response.responseText);
								 Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.editFailure') + result);    //提示编辑失败
							}
						});
				
			}
		},{
			iconCls: 'deppon_icons_delete',
			tooltip: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.delete'),    //删除
			handler: function(grid, rowIndex, colIndex) {
				if(airfreight.airSpace.isPermission('airfreight/deleteAirSpaceButton')){
					Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是  
					Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');    //否
					Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.confirmDelete'),function(btn,text){   //提示是否删除选中项
						//询问是否删除，是则发送请求
						if(btn == 'yes'){
							Ext.Ajax.request({
								params: {
									'vo.dto.id': grid.getStore().getAt(rowIndex).get('id'),
									'vo.dto.takeOffDate': grid.getStore().getAt(rowIndex).get('takeOffDate'),
									'vo.dto.flightType': grid.getStore().getAt(rowIndex).get('flightType'),
									'vo.dto.assembleOrgCode': grid.getStore().getAt(rowIndex).get('assembleOrgCode')
								},
								url: airfreight.realPath('deleteAirSpace.action'),
								success:function(response){
									 Ext.ux.Toast.msg(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'), airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.deleteSuccess'), 'ok', 1000);    //提示删除成功!
							         airfreight.airSpaceGrid.store.load(); 
								},
								failure:function(response){
									 var result = Ext.decode(response.responseText);
									 Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.deleteFailure') + result);     //提示删除失败!
								},
								exception : function(response) {
		        					var json = Ext.decode(response.responseText);
		        					Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),json.message);
		        				}
							});
						}
					});
				}else{
					Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.cannotDelete'));
				}
			}
		}]
	},{
		//关联model中的字段名
		dataIndex: 'id',
		hidden : true
	},{
		//字段标题
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.assembleOrgCode'),    //部门 
		//关联model中的字段名
		dataIndex: 'assembleOrgName',
		flex:1				
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.takeOffDate'),    //日期 
		//关联model中的字段名
		dataIndex: 'takeOffDate',
		renderer: Ext.util.Format.dateRenderer('Y-m-d'),
		flex:0.8
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.arrvRegionCode'),    //目的站 
		//关联model中的字段名
		dataIndex: 'arrvRegionName',
		flex:0.8
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.flightType'),    //航班类型 
		//关联model中的字段名
		dataIndex: 'flightType',
		flex:1,
		renderer : function(value){
			if(value === "" || value == null){
				return "";
			}
			return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
		}
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.spaceTotal'),    //舱位总量
		//关联model中的字段名
		dataIndex: 'spaceTotal',
		flex:1
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.bookingSpaceTotal'),    //已订舱总量 
		//关联model中的字段名
		dataIndex: 'bookingSpaceTotal',
		flex:1
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.acceptedSpaceTotal'),    //已受理订舱总量 
		//关联model中的字段名
		dataIndex: 'acceptedSpaceTotal',
		flex:1
	},{ 
		header: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.airWaybillTotal'),    //已开单空运货量 
		//关联model中的字段名
		dataIndex: 'airWaybillTotal',
		flex:1
	}],
	 constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('foss.airfreight.airSapceStore');
			me.tbar=[{
				xtype: 'button', 
				text: airfreight.airSpace.i18n('foss.airfreight.airSpace.button.add'),    //新增
				handler: function() {
					//获取弹出窗口
					var airSapceWindow = Ext.getCmp('foss_airfreight_airSapceInputWindow_Id');
					//如果没有创建，则新增，否则直接show
					if(airSapceWindow == null){
						airSapceWindow = Ext.create('foss.airfreight.airSapceInputWindow');
					}
					Ext.getCmp('foss_airfreight_airSapceForm_ID').isAddNew=true;
					airSapceWindow.center().show();
				}
			}];
			me.bbar =Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			airfreight.airSpacePagingBar=me.bbar;
			me.callParent([cfg]);
	},
	listeners : {
		render : function(grid,opt){
			var btns = grid.dockedItems.items[2].query('button');
			Ext.Array.each(btns, function(item, index){
				item.hide();
			});
			if(airfreight.airSpace.isPermission('airfreight/getNextDayButton')){
				btns[0].show();
			}
		}
	}
});
	 
/**
* 录入舱位信息表单
*/
Ext.define('foss.airfreight.airSapceForm', {
	extend : 'Ext.form.Panel',
	id: 'foss_airfreight_airSapceForm_ID',
	fieldDefaults: {
      msgTarget: 'side',
      margin:'3 3 3 3',
      labelWidth: 120
  	},
  	isAddNew: true,
  	origValue: null,
  	layout:'column',
	items: [{
	  		xtype: 'textfield',
	  		name: 'id',
	  		hidden: true
	  },{
	  		xtype: 'datefield',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.takeOffDate'),    //日期
	  		name: 'takeOffDate',
	  		value: airfreight.nextDay,
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		invalidText: '输入的日期格式不对',
	  		minValue: new Date(),
	  		allowBlank: false,
	  		columnWidth: .45
	  },{
			border : false,
			xtype : 'container',
			columnWidth:.05,
			html: '&nbsp;'
	  },{
	  		xtype: 'commoncityselector',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.arrvRegionCode'),    //目的站
	  		name: 'arrvRegionCode',
	  		allowBlank:false,
	  		regex: /^\S+$/,
	  		columnWidth:.45,
			listeners: {
				'blur': function(field){
					var form = this.up('form').getForm();
					form.findField('arrvRegionName').setValue(field.rawValue);
				}
			}
	  },{
	  		xtype: 'textfield',
	  		name: 'arrvRegionName',
	  		hidden:true
	  },{
			border : false,
			xtype : 'container',
			columnWidth:.05,
			html: '&nbsp;'
	  },{
	  		xtype: 'numberfield',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.morningShift'),    //即日达预定舱位  morningShift
	  		name: 'morningShift',
	  		//regex:/^-?\d+\.?\d{0,1}$/,
	        //regexText:"重量格式输入有误！",
	  		maxValue: 9999999999,
       		minValue: 0,
       		decimalPrecision: 1,
	        //maxLength : 12,
	        //maxLengthText:'早班预定舱位长度已超过最大限制!',
	        listeners: {
	        	blur : function(field){
	        		var form = this.up('form');
	        		//如果是编辑状态且保留有原始值
	        		if(!form.isAddNew && form.origValue != null){
	        			if(field.value < form.origValue.morningShift){
	        				Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是    
						    Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');   //否  
						    Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.confirmUpdate'),function(btn, text){
							    if(btn == 'yes'){
							   		return;
							    }else{
							    	form.getForm().findField('morningShift').setValue(form.origValue.morningShift); 
							    }
							});
	        			}
	        		}
	        	}
	        },
	  		columnWidth:.45
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.kg'),    //公斤
			columnWidth:.05
	  },{
	  		xtype: 'numberfield',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.middleShift'),    //次日达预定舱位 middleShift
	  		name: 'middleShift',
	  		//regex:/^-?\d+\.?\d{0,1}$/,
	        //regexText:"重量格式输入有误！",
	        //maxLength : 14,
	        //maxLengthText:'中班预定舱位长度已超过最大限制!',
	  		maxValue: 999999999999,
       		minValue: 0,
       		decimalPrecision: 1,
	        listeners: {
	        	blur : function(field){
	        		var form = this.up('form');
	        		//如果是编辑状态且保留有原始值
	        		if(!form.isAddNew && form.origValue != null){
	        			if(field.value < form.origValue.middleShift){
	        				Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是    
						    Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');   //否  
						    Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.confirmUpdate'),function(btn, text){
							    if(btn == 'yes'){
							   		return;
							    }else{
							    	form.getForm().findField('middleShift').setValue(form.origValue.middleShift); 
							    }
							});
	        			}
	        		}
	        	}
	        },
	  		columnWidth:.45
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.kg'),    //公斤
			columnWidth:.05
	  },{
	  		xtype: 'numberfield',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.transitShift'),    //航空普运预定舱位 nightShift
	  		name: 'transitShift',
	  		//regex:/^-?\d+\.?\d{0,1}$/,
	        //regexText:"重量格式输入有误！",
	        //maxLength : 7,
	        //maxLengthText:'晚班预定舱位长度已超过最大限制!',
	  		maxValue: 99999,
       		minValue: 0,
       		decimalPrecision: 1,
	        listeners: {
	        	blur : function(field){
	        		var form = this.up('form');
	        		//如果是编辑状态且保留有原始值
	        		if(!form.isAddNew && form.origValue != null){
	        			if(field.value < form.origValue.transitShift){
	        				Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是    
						    Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');   //否  
						    Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.confirmUpdate'),function(btn, text){
							    if(btn == 'yes'){
							   		return;
							    }else{
							    	form.getForm().findField('transitShift').setValue(form.origValue.transitShift); 
							    }
							});
	        			}
	        		}
	        	}
	        },
	  		columnWidth:.45
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.kg'),    //公斤
			columnWidth:.05
	  }/*,{
	  		xtype: 'numberfield',
	  		fieldLabel: airfreight.airSpace.i18n('foss.airfreight.airSpace.label.transitShift'),    //中转预定舱位
	  		//regex:/^-?\d+\.?\d{0,1}$/,
	        //regexText:"重量格式输入有误！",
	        //maxLength : 12,
	        //maxLengthText:'中转预定舱位长度已超过最大限制!',
	        maxValue: 9999999999,
       		minValue: 0,
       		decimalPrecision: 1,
	  		name: 'transitShift',
	  		listeners: {
	        	blur : function(field){
	        		var form = this.up('form');
	        		//如果是编辑状态且保留有原始值
	        		if(!form.isAddNew && form.origValue != null){
	        			if(field.value < form.origValue.transitShift){
	        				Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是    
						    Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');   //否  
						    Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.confirmUpdate'),function(btn, text){
							    if(btn == 'yes'){
							   		return;
							    }else{
							    	form.getForm().findField('transitShift').setValue(form.origValue.transitShift); 
							    }
							});
	        			}
	        		}
	        	}
	        },
	  		columnWidth:.45
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.kg'),    //公斤
			columnWidth:.05
	  }*/],
	  buttons: [{
	      text: airfreight.airSpace.i18n('foss.airfreight.airSpace.button.save'),    //保存
	      formBind: true,
	      cls:'yellow_button',
	      plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			  seconds: 1
		  }),
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  //判断form的有效性
	      	  if (form.isValid()){
	      	  	  var vals = form.getValues();
	      	  	  //用于记数，判断累计四种类型舱位为空的数目
	      	  	  var i=0;
	      	  	  //设置即日达为空时
		      	  if(Ext.isEmpty(Ext.String.trim(vals.morningShift))){
		      	  	vals.morningShift = 0;
		      	  	i++;
		      	  }
		      	  //设置次日达为空时
		      	  if(Ext.isEmpty(Ext.String.trim(vals.middleShift))){
		      	  	vals.middleShift = 0;
		      	  	i++;
		      	  }
		      	  //设置航班普运为空时
		      	  if(Ext.isEmpty(Ext.String.trim(vals.transitShift))){
		      	  	vals.transitShift = 0;
		      	  	i++;
		      	  }
		      	 /* //设置中转为空时
		      	  if(Ext.isEmpty(Ext.String.trim(vals.transitShift))){
		      	  	vals.transitShift = 0;
		      	  	i++;
		      	  }*/
	      	  	  if(i == 3){
	      	  	  	 Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.enterLessOneSpace'));	//提示请至少录入一个舱位!
	      	  	  	 return;
	      	  	  }
	      	  }else{
	      	  	  Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.enterAllInfo'));	//提示请填入完整的信息!
	      	  	  return;
	      	  }
	      	  if(this.up('form').isAddNew){
		      	  var params={vo:{dto:vals}};
		          Ext.Ajax.request({
		        	    url: airfreight.realPath('addAirSpace.action'),
		        	    jsonData: params,
					    success: function(response, opts) {
					       airfreight.isAfterSave = form.findField('takeOffDate').getValue();
					       //保存成功后，重置界面，日期回复到当前日期的下一天
					       form.reset();
					       form.findField('takeOffDate').setValue(airfreight.nextDay);
					       airfreight.airSpaceGrid.store.load(); 
					       Ext.ux.Toast.msg(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'), airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.saveSuccess'), 'ok', 1000);     //保存成功
					    },
					    failure: function(response, opts) {
					       Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.saveFailure'));   //保存失败
					    },
					    exception : function(response) {
	        				var json = Ext.decode(response.responseText);
	        				Ext.MessageBox.buttonText.yes = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.yes');   //是    
						    Ext.MessageBox.buttonText.no = airfreight.airSpace.i18n('foss.airfreight.airSpace.label.no');   //否  
						    if(json.message == airfreight.airSpace.i18n('foss.airfreight.exception.existAirSpace')){
					    	   Ext.Msg.confirm(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'),json.message,function(btn, text){
								   if(btn == 'yes'){
										Ext.Ajax.request({
								        	  url: airfreight.realPath('updateAirSpace.action'),
								        	  jsonData: params,
											  success: function(response, opts) {
											       Ext.getCmp('foss_airfreight_airSapceInputWindow_Id').close();
											       airfreight.airSpaceGrid.store.load(); 
											       Ext.ux.Toast.msg(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'), airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.updateSave'), 'ok', 1000);   //修改成功
											  },
											  failure: function(response, opts) {
											       Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.updateFailure'));   //修改失败
											  }
								        });
								    }				       
					        	});
						    }else{
						    	Ext.Msg.alert(json.message);
						    }
	        			}
		          });
	      	  }else{
	      	  	  var params={vo:{dto:vals}};
		          Ext.Ajax.request({
		        	  url: airfreight.realPath('updateAirSpace.action'),
		        	  jsonData: params,
					  success: function(response, opts) {
					  	  Ext.getCmp('foss_airfreight_airSapceInputWindow_Id').close();
					      airfreight.isAfterSave = form.findField('takeOffDate').getValue();
					      airfreight.airSpaceGrid.store.load(); 
					      Ext.ux.Toast.msg(airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.message'), airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.updateSave'), 'ok', 1000);   //修改成功
					  },
					  failure: function(response, opts) {
					       Ext.Msg.alert(airfreight.airSpace.i18n('foss.airfreight.airSpace.tip.failure'),airfreight.airSpace.i18n('foss.airfreight.airSpace.msg.updateFailure'));   //修改失败
					  }
		          });
	      	  }	  
	      }
	  },{
	      text: airfreight.airSpace.i18n('foss.airfreight.airSpace.button.cancel'),   //取消
	      cls:'yellow_button',
	      handler: function() {
	         this.up('window').close();
	      }
	  }],
	  constructor: function(config){
			var me = this;
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	  },
		listeners : {
			render : function(panel,opt){
				var currentDept = airfreight.dept;
				var form = panel.getForm();
				//获取button
				var btns = form.getBoundItems().items;
				//保存
				if(!airfreight.airSpace.isPermission('airfreight/saveOrUpdateAirHandOverBillButton')){
					btns[btns.length-1].hide();
				}
			}
		}
});

/**
* 录入舱位信息window
*/
Ext.define('foss.airfreight.airSapceInputWindow',{
	extend: 'Ext.window.Window',
	id:'foss_airfreight_airSapceInputWindow_Id',
	title: airfreight.airSpace.i18n('foss.airfreight.airSapceInputWindow.title'),   //录入航空公司舱位
	width: 600,
	modal:true,
	closeAction:'hide',
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.getCmp('foss_airfreight_airSapceForm_ID')==null ? Ext.create('foss.airfreight.airSapceForm'):Ext.getCmp('foss_airfreight_airSapceForm_ID')
	],
    listeners: {
    	//显示的时候判断是否为新增，如果为新增，则isAddNew状态为true
  		show: function(){
  			var form = Ext.getCmp('foss_airfreight_airSapceForm_ID');
  			if(form.isAddNew){
  				Ext.Ajax.request({
  			 		url: airfreight.realPath('getNextDay.action'),
  				    success: function(response, opts) {
  				    	var json = Ext.decode(response.responseText);
  				    	airfreight.nextDay = new Date(json.vo.nextDay);
	  				    form.getForm().reset();
					    form.getForm().findField('takeOffDate').setValue(airfreight.nextDay);
  				    },
  				    exception:function(response, opts){
  				    	date.setDate(date.getDate()+1);
  				    }
  			 	});
  			}
  		}
    }
});

airfreight.dept = '';

//初始入口函数
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var queryForm = Ext.create('foss.airfreight.airSpaceQueryForm');
	airfreight.querySpaceForm=queryForm;
	var airSpaceGrid = Ext.create('foss.airfreight.airSpaceGrid');
	airfreight.airSpaceGrid=airSpaceGrid;
	Ext.create('Ext.panel.Panel',{
	id:'T_airfreight-airSpace_content',
	cls:'panelContent',
	bodyCls:'panelContent-body',
	layout:'auto',
	margin:'0 0 0 0',
	items : [queryForm,airSpaceGrid],
	renderTo: 'T_airfreight-airSpace-body'
});	
 

});