/**营业部订舱界面*/

//营业部订舱Model
Ext.define('foss.airfreight.bookingSpaceModel', {
	extend: 'Ext.data.Model',
	idProperty : 'id',
	//定义字段
	fields: [
		{name: 'id',type:'string'}, 			//id
		{name: 'bookingNo',type:'string'}, 		//订舱编号
		{name: 'acceptOrgName',type:'string'},  //受理部门名称
		{name: 'acceptOrgCode',type:'string'}, 	//受理部门编码
		{name: 'takeOffDate',type:'date',		//走货日期
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}
		}},
		{name: 'deptRegionName',type:'string'}, //始发站名称
		{name: 'deptRegionCode',type:'string'}, //始发站编码
		{name: 'arrvRegionCode', type: 'string'},	//目的站编码
	    {name: 'arrvRegionName', type: 'string'},	//目的站名称
	    {name: 'airlines', type: 'string'},	//航班公司
	    {name: 'flightType', type: 'string'},	//航班类型
	    {name: 'flightNo', type: 'string'},	//航班号
	    {name: 'goodsName', type: 'string'},//货物名称
	    {name: 'volume', type: 'string'},	//货物体积
	    {name: 'weight', type: 'string'},	//货物重量
	    {name: 'notes', type: 'string'},	//备注
	    {name: 'applyOrgCode', type: 'string'},	//申请部门编码
	    {name: 'applyOrgName', type: 'string'},	//申请部门名称
	    {name: 'createUserCode', type: 'string'},	 //订舱人编码
	    {name: 'createUserName', type: 'string'},	//订舱人名称
	    {name: 'createTime',type:'date',		//订舱申请时间
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}
		}},
	    {name: 'acceptUserCode', type: 'string'},	//受理人编码
	    {name: 'acceptUserName', type: 'string'},	//受理人名称
	    {name: 'acceptState', type: 'string'},	//受理状态
	    {name: 'acceptNotes', type: 'string'}, //受理备注
	    {name: 'waybillNo', type: 'string'} //受理备注
	]
});


//营业部订舱Store
Ext.define('foss.airfreight.bookingSpaceStore', {
	extend: 'Ext.data.Store',
    model: 'foss.airfreight.bookingSpaceModel',
    pageSize:5,
    proxy: {
      	type: 'ajax',
        url: airfreight.realPath('queryBookingSpace.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.bookingList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        }
    },
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = airfreight.bookingSpace.QueryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.applyOrgCode' : queryParams.applyOrgCode,  //申请部门
						'vo.acceptOrgCode' : queryParams.acceptOrgCode,	 //受理部门
						'vo.acceptState' : queryParams.acceptState,	//受理状态
						'vo.createTimeFrom' : queryParams.createTimeFrom, //申请开始时间
						'vo.createTimeTo' : queryParams.createTimeTo, //申请结束时间
						'vo.arrvRegionCode' :queryParams.arrvRegionCode //目的城市
					}
				});	
			}
		},
		load: function(store,records,successful,epots){
			if(store.getCount() == 0){
				Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.noData'), 'ok', 1000);  //提示没有查询到数据
			}
		},
		datachanged: function(store, eOpts){
			
		}
	}
});

//营业部订舱查询表单
Ext.define('foss.airfreight.bookingSpace.QueryForm',{
	extend: 'Ext.form.Panel',
	title: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpaceQueryForm.title'),    //查询条件
	frame:true,
	fieldDefaults: {
		msgTarget: 'side',
		margin: '5 0 5 0',
		labelWidth: 75
	},
	layout:'column',
	items:[{
		xtype: 'dynamicorgcombselector',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.applyOrgCode'),    //申请部门
		name: 'applyOrgCode',
		columnWidth: .25
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptOrgCode'),    //受理部门
		name: 'acceptOrgCode',
		columnWidth: .25
	},
	FossDataDictionary.getDataDictionaryCombo('AIR_ACCEPT_STATUS',{
		name: 'acceptState',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptState'),    //受理状态
		queryMode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		editable: true,
		value: 'UN_ACCEPT',
		columnWidth: .25
		},{
			'valueCode':'',
			'valueName': airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.options.all')    //全部
	})
	,{
		xtype: 'commoncityselector',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.arrvRegionCode'),    //目的城市
		name: 'arrvRegionCode',
		columnWidth: .25
	},{
		xtype: 'rangeDateField',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.createTime'),    //申请时间
		fieldId: 'foss_airfreight_bookingSpaceQueryForm_createTime',
		dateType: 'datetimefield_date97',
		fromName: 'createTimeFrom',
		dateRange:31, //时间跨度不能大于一个月
		toName: 'createTimeTo',
		columnWidth: .5
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: airfreight.bookingSpace.i18n('foss.airfreight.airSpaceQueryForm.button.reset'), //重置
			columnWidth: .1,
			handler:function(){
				var form = this.up('form').getForm();
				form.reset();
				Ext.Ajax.request({
					  url: airfreight.realPath('initBookingQueryForm.action'),
				      success: function(response, opts) {
				    	var result = Ext.decode(response.responseText);
				    	form.findField('createTimeFrom').setValue(Ext.Date.format(new Date(result.vo.createTimeFrom),'Y-m-d H:i:s'));
				    	form.findField('createTimeTo').setValue(Ext.Date.format(new Date(result.vo.createTimeTo),'Y-m-d H:i:s'));
				    	form.findField('acceptState').setValue('UN_ACCEPT');
				    	if('Y' === airfreight.dept.airDispatch){
				    		var cmbAcceptOrgCode = form.findField('acceptOrgCode');;
				    		cmbAcceptOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
				    		cmbAcceptOrgCode.setValue(airfreight.dept.code);
				    		cmbAcceptOrgCode.setReadOnly(true);
				    	}else{
				    		var cmbApplyOrgCode = form.findField('applyOrgCode');;
				    		cmbApplyOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
				    		cmbApplyOrgCode.setValue(airfreight.dept.code);
				    		cmbApplyOrgCode.setReadOnly(true);
				    	}
				      },
				      failure: function(response, opts) {
				         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.initFailure'), 'error', 2000);   //初始化查询条件失败
				      }
				 });
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.8,
			html: '&nbsp;'
	    },{
			text: airfreight.bookingSpace.i18n('foss.airfreight.airSpaceQueryForm.button.search'), //查询
			cls:'yellow_button',
			columnWidth:.1,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 2
			}),
			handler:function(){
				airfreight.bookingSpacePagingBar.moveFirst();
			}
		}]
	}],
	listeners: {
		afterrender: function(queryForm){
			Ext.Ajax.request({
				  url: airfreight.realPath('initBookingQueryForm.action'),
			      success: function(response, opts) {
			    	var result = Ext.decode(response.responseText);
			    	form = queryForm.getForm();
			    	form.findField('createTimeFrom').setValue(Ext.Date.format(new Date(result.vo.createTimeFrom),'Y-m-d H:i:s'));
			    	form.findField('createTimeTo').setValue(Ext.Date.format(new Date(result.vo.createTimeTo),'Y-m-d H:i:s'));
			    	var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
					Ext.Ajax.request({
						url : airfreight.realPath('queryAirDispatch.action'),
						jsonData:array,
						success : function(response) {
							var json = Ext.decode(response.responseText);
							var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
							airfreight.dept = dept;
					    	if('Y' === airfreight.dept.airDispatch){
					    		var cmbAcceptOrgCode = form.findField('acceptOrgCode');;
					    		cmbAcceptOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
					    		cmbAcceptOrgCode.setValue(airfreight.dept.code);
					    		cmbAcceptOrgCode.setReadOnly(true);
					    	}else{
					    		var cmbApplyOrgCode = form.findField('applyOrgCode');;
					    		cmbApplyOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
					    		cmbApplyOrgCode.setValue(airfreight.dept.code);
					    		cmbApplyOrgCode.setReadOnly(true);
					    	}
						}
					});
			      },
			      failure: function(response, opts) {
			         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.initFailure'), 'error', 2000);   //初始化查询条件失败
			      }
			 });
		}
	}
});

//备注表单
Ext.define('foss.airfreight.bookingSpace.notesForm',{
	extend: 'Ext.form.Panel',
	id: 'foss_airfreight_notesForm_ID',
	layout:'hbox',
	items:[{
    	xtype: 'textareafield', 
    	name: 'notes',
    	readOnly:true,
    	width : 300,
    	labelWidth: 80,
    	fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.notes')    //申请备注
    	
    },{
    	xtype: 'textareafield', 
    	name: 'acceptNotes',
    	readOnly:true,
    	width : 300,
    	labelWidth: 80,
    	fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptNotes')    //受理备注
    }]	
});

//订舱信息显示列表Grid
Ext.define('foss.airfreight.bookingSpace.Grid',{
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
    columnLines: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	emptyText: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.queryIsNull'),    //查询结果为空
	collapsible: true,
	animCollapse: true,
    frame: true,
    title:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.bookingSpaceGrid.title'),    //舱位列表信息	
	columns: [{
		xtype:'actioncolumn',
		width: 40,		
		text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.operator'),    //操作
		locked : true,
		items: [{
			iconCls: 'deppon_icons_edit',
			tooltip: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.edit'),    //编辑
			//编辑事件
			handler: function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				if(record.get('acceptState') != 'UN_ACCEPT'){
					Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.failure'),airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.wasAccept'));  //该记录已经受理，不磕更改！
					return;
				}
				Ext.Ajax.request({
					params: {'vo.id': record.get('id')},
					url: airfreight.realPath('queryBookingSpaceById.action'),
					success:function(response){
						var result = Ext.decode(response.responseText);
						record.data = new Ext.data.reader.Json(result.vo.dto);
        				var form = airfreight.bookingSpaceWindow.getBookingSpaceForm();
        				form.getForm().reset();
						//load公共选择器
    					var cmbAcceptOrgCode = airfreight.bookingSpaceForm.getForm().findField('acceptOrgCode');
    					cmbAcceptOrgCode.getStore().load({params:{'commonOrgVo.name' : record.data.acceptOrgName}});
    					cmbAcceptOrgCode.setValue(record.data.acceptOrgCode);
    					
        				var cmbArrvRegionCode = airfreight.bookingSpaceForm.getForm().findField('arrvRegionCode');
        				cmbArrvRegionCode.getStore().load({params:{'cityVo.name' : record.data.arrvRegionName}});
        				cmbArrvRegionCode.setValue(record.data.arrvRegionCode);
        				
        				record.data.takeOffDate = new Date(record.data.takeOffDate);
        				form.getForm().loadRecord(record);        				
						form.isAddNew = false;
						airfreight.bookingSpaceWindow.center().show();
					},
					failure:function(response){
						 var result = Ext.decode(response.responseText);
						 Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.failure'),airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.editFailure') + result);  //编辑失败！
					}
				});
			}
		}]
	},{
		//字段标题
		dataIndex: 'id',
		hidden : true
	},{
		//字段标题
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptState'),    //受理状态 
		//关联model中的字段名
		locked : true,
		dataIndex: 'acceptState',
		renderer: function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_ACCEPT_STATUS');
	    },
		width: 70				
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingNo'),    //订舱编号
    	xtype : 'ellipsiscolumn',
		locked : true,
		//关联model中的字段名
		dataIndex: 'bookingNo',
		columnWidth: 90	
	},{ 
		header: '运单号',    //订舱编号
    	xtype : 'ellipsiscolumn',
		//关联model中的字段名
		dataIndex: 'waybillNo',
		columnWidth: 90	
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.applyOrgCode'),    //申请部门 
    	xtype : 'ellipsiscolumn',
		//关联model中的字段名
		dataIndex: 'applyOrgName',
		columnWidth: 120	
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptOrgCode'),    //受理部门 
    	xtype : 'ellipsiscolumn',
		//关联model中的字段名
		dataIndex: 'acceptOrgName',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.takeOffDate'),    //走货日期
    	xtype : 'ellipsiscolumn',
		//关联model中的字段名
		dataIndex: 'takeOffDate',
		columnWidth: 100
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.arrvRegionCode'),    //目的城市 
		//关联model中的字段名
		dataIndex: 'arrvRegionName',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.airlines'),    //航空公司
		//关联model中的字段名
		dataIndex: 'airlines',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.flightType'),    //航班类型
		//关联model中的字段名
		dataIndex: 'flightType',
		renderer: function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
	    },
		columnWidth: 40
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.flightNo'),    //航班号
		//关联model中的字段名
		dataIndex: 'flightNo',
		columnWidth: 60
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptNotes'),    //受理备注 
		//关联model中的字段名
		dataIndex: 'acceptNotes',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptUserName'),    //受理人
		//关联model中的字段名
		dataIndex: 'acceptUserName',
		columnWidth: 80
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.volume'),    //货物体积(方)
		//关联model中的字段名
		dataIndex: 'volume',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.weight'),    //货物重量(公斤)
		//关联model中的字段名
		dataIndex: 'weight',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.createUserName'),    //订舱申请人
		//关联model中的字段名
		dataIndex: 'createUserName',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.createTime'),    //申请时间 
		//关联model中的字段名
		dataIndex: 'createTime',
		columnWidth: 120
	},{ 
		header: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.notes'),    //申请备注 
		//关联model中的字段名
		dataIndex: 'notes',
		columnWidth: 120
	}],
	viewConfig: {
        stripeRows: true
    },
	constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.selModel = Ext.create('Ext.selection.CheckboxModel'),
			me.store = Ext.create('foss.airfreight.bookingSpaceStore');
			var currentDept = airfreight.dept;
			me.tbar=[{
				xtype: 'button', 
				text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.bookingSpcae'),    //订舱
				handler: function() {
					//获取弹出窗口
					var bookingSpaceWindow = airfreight.bookingSpaceWindow;
					airfreight.bookingSpaceForm.isAddNew=true;
					bookingSpaceWindow.center().show();
					
				},
				listeners: {
					beforerender : function(){
						if(currentDept != null && currentDept.doAirDispatch == 'Y'){
							//this.disable(true);
						}else{
							//不设置
						}
					}
				}
			},{
				xtype: 'button', 
				text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.agreeAccept'),    //同意受理
				handler: function(){
					var selectRows = airfreight.bookingSpace.Grid.getSelectionModel().getSelection();
					if (selectRows.length==0){
						Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.choiseOne'));    //请选择一行
						return;
					}
					//获取弹出窗口
					var acceptBookingSpaceWindow = Ext.getCmp('foss_airfreight_acceptBookingSpaceWindow_Id');
					//如果没有创建，则新增，否则直接show
					if(acceptBookingSpaceWindow == null){
						acceptBookingSpaceWindow = Ext.create('foss.airfreight.acceptBookingSpaceWindow');
					}
					acceptBookingSpaceWindow.show();
					acceptBookingSpaceWindow.items.items[0].setValue('');
					acceptBookingSpaceWindow.acceptType = true;
				},
				listeners: {
					beforerender : function(){
						if(currentDept != null && currentDept.doAirDispatch == 'N'){
							this.disable(true);
						}
					}
				}
			},{
				xtype: 'button', 
				text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.refuseAccept'),    //拒绝受理
				handler: function(){
					var selectRows = airfreight.bookingSpace.Grid.getSelectionModel().getSelection();
					if (selectRows.length==0){
						Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.choiseOne'));    //请选择一行
						return;
					}
					//获取弹出窗口
					var acceptBookingSpaceWindow = Ext.getCmp('foss_airfreight_acceptBookingSpaceWindow_Id');
					//如果没有创建，则新增，否则直接show
					if(acceptBookingSpaceWindow == null){
						acceptBookingSpaceWindow = Ext.create('foss.airfreight.acceptBookingSpaceWindow');
					}
					acceptBookingSpaceWindow.show();
					acceptBookingSpaceWindow.items.items[0].setValue('');
					acceptBookingSpaceWindow.acceptType = false;
				},
				listeners: {
					beforerender : function(){
						if(currentDept != null && currentDept.doAirDispatch == 'N'){
							this.disable(true);
						}
					}
				}
			}];
//			//TODO需要进行权限验证,创建总调未受理条数显示
			if(airfreight.dept.doAirDispatch === 'Y'){
				me.dockedItems = Ext.create('widget.toolbar',{
						   xtype:'toolbar',
						   dock:'bottom',
						   items:[{
							   xtype:'textfield',
							   name: 'noAcceptCount',
							   fieldLabel:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.notAcceptCount'),    //总调未受理条数	
							   labelWidth:120,
							   value:'0',
							   readOnly: true
						   }]
				});
				// 根据需要设置 是否定时刷新，调用Ext.TaskManager.start
				Ext.Ajax.request({
					 url: airfreight.realPath('queryNoAcceptCount.action'),
					 success: function(response, opts) {
						 var result = Ext.decode(response.responseText);
						 var textItem = airfreight.bookingSpace.Grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');
						 textItem[0].setValue(result.vo.noAcceptCount);
				     },
				     failure: function(response, opts) {
				         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.loadNotAcceptFailure'), 'error', 2000);   //加载未受理总条数失败!
				     }
				});
			}else{
				me.dockedItems = Ext.create('widget.toolbar',{
					   xtype:'toolbar',
					   dock:'bottom',
					   items:[{
						   xtype:'textfield',
						   fieldLabel:'', 
						   labelWidth:120,
						   readOnly: true
					   }]
			});
			};
			plugin=Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['5',5],['10', 10], ['25', 25], ['50', 50], ['100', 100],['200',200]]
			});
			me.bbar =Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins:plugin
				//plugins: 'pagesizeplugin'
			});
			airfreight.bookingSpacePagingBar=me.bbar;
			me.callParent([cfg]);
	},
	listeners:{
		select: function(model, record){
			Ext.getCmp('foss_airfreight_notesForm_ID').getForm().loadRecord(record);
		},
		render : function(grid,opt){
			var btns = grid.dockedItems.items[2].query('button');
			Ext.Array.each(btns, function(item, index){
				item.hide();
			});
			if(airfreight.bookingSpace.isPermission('airfreight/addBookingSpaceButton')){
				btns[0].show();
			}
			if(airfreight.bookingSpace.isPermission('airfreight/acceptBookingSpaceButton')){
				btns[1].show();
			}
			if(airfreight.bookingSpace.isPermission('airfreight/refuseBookingSpaceButton')){
				btns[2].show();
			}
		}
	}
});

//营业部订舱Form
Ext.define('foss.airfreight.bookingSpaceForm', {
	extend : 'Ext.form.Panel',
	fieldDefaults: {
      msgTarget: 'side',
      margin:'3 5 3 3',
      labelWidth: 80
  	},
  	defaultType: 'textfield',
  	layout:'column',
  	isAddNew: true,
	items: [{
	  		xtype: 'textfield',
	  		name: 'id',
	  		hidden: true
	  },{
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingNo'),    //订舱编号
	  		name: 'bookingNo',
	  		allowBlank:false,
	  		readOnly:true,
	  		allowBlank:false,
	  		columnWidth:.33
	  },{
		  	xtype:'dynamicorgcombselector',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptOrgCode'),    //受理部门
	  		name: 'acceptOrgCode',
	  		airDispatch:'Y',
	  		allowBlank:false,
	  		columnWidth:.33,	  		
			listeners: {
				'blur': function(field){
					var form = this.up('form').getForm();
					form.getFields().items[3].setValue(field.rawValue);
					var array = {vo:{acceptOrgCode:field.getValue()}};
					Ext.Ajax.request({
						url : airfreight.realPath('queryDeptRegion.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				var json = Ext.decode(response.responseText);
	  				    	var deptRegionCode = json.vo.dto.deptRegionCode;
	  				    	var deptRegionName = json.vo.dto.deptRegionName;

	  				    	if(deptRegionCode === "" || deptRegionName ===""){
	  				    		Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'),airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.notFinddeptRegion'));    //根据当前空运总调为找到想对应的始发站，请检查配置！
	  				    		deptRegionCode = '';
	  				    		deptRegionName = '';
	  				    	}
  	  						airfreight.bookingSpaceForm.getForm().findField('deptRegionCode').setValue(deptRegionCode);
  	  						airfreight.bookingSpaceForm.getForm().findField('deptRegionName').setValue(deptRegionName);
	        			},
	        			exception : function(response){
	        				var json = Ext.decode(response.responseText);
	        				Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'),json.message);
	        			}
					});
				}
			}
	  },{
	  		name: 'acceptOrgName',
	  		allowBlank:false,
	  		hidden:true
	  },{
	  		xtype: 'datefield',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.takeOffDate'),    //走货日期
	  		name: 'takeOffDate',
	  		altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		invalidText: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.dateFormatError'),    //输入的日期格式不对
	  		allowBlank: false,
	  		minValue: new Date(),
	  		columnWidth: .33
	  },{
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.deptRegionName'),    //始发站
	  		readOnly:true,
	  		name: 'deptRegionName',
	  		allowBlank:false,
	  		columnWidth:.33
	  },{
	  		name: 'deptRegionCode',
	  		allowBlank:false,
	  		hidden:true
	  },{
	  		fieldLabel: '运单号',   
		    name: 'waybillNo',
	  		columnWidth:.33,
	  		listeners : {
	  			'blur' : function(field){
	  				var form = this.up('form').getForm();
	  				var waybillNo = field.value;
	  				if(waybillNo != ''){
		  				var params= {'vo':{'waybillNo': waybillNo}};
		  				Ext.Ajax.request({
		  					url: airfreight.realPath('queryWaybillInfoByNo.action'),
			   	        	  jsonData: params,
			   			      success: function(response, opts) {
			   			    	var json = Ext.decode(response.responseText);
			   			    	var dto = json.vo.dto;
			   			    	form.findField('flightType').setValue(dto.flightType);
			   			    	form.findField('goodsName').setValue(dto.goodsName);
			   			    	form.findField('volume').setValue(dto.volume);
			   			    	form.findField('weight').setValue(dto.weight);
			   			    	form.findField('arrvRegionName').setValue(dto.arrvRegionName);
			   			    	
			   			    	var cmbArrvRegion = airfreight.bookingSpaceForm.getForm().findField('arrvRegionCode');
			   			    	cmbArrvRegion.getStore().load({params:{'cityVo.name' : dto.arrvRegionName}});
			   			    	cmbArrvRegion.setValue(dto.arrvRegionCode);
			   			    	
			   			      },
			   			      failure: function(response, opts) {
			       				 var json = Ext.decode(response.responseText);
			       				 Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), json.message);   
			   			      },
				   			  exception : function(response) {
		       					 var json = Ext.decode(response.responseText);
		       					 Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), json.message);
				   			  }
		  				});
	  				}
	  			}
	  		}
	  },{
		  	xtype:'commoncityselector',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.arrvRegionName'),    //目的站
	  		name: 'arrvRegionCode',
	  		allowBlank:false,
	  		columnWidth:.33,
			listeners: {
				'blur': function(field){
					var form = this.up('form').getForm();
					form.findField('arrvRegionName').setValue(field.rawValue);
					//field.setValue(field.rawValue);
				}
			}
	  },{
	  		name: 'arrvRegionName',
	  		allowBlank:false,
	  		hidden:true
	  },
	  FossDataDictionary.getDataDictionaryCombo('AIR_FLIGHT_TYPE',{
			name: 'flightType',
			fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.flightType'),    //航班类型
			queryMode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			editable: true,
			allowBlank: false,
			value: '',
			columnWidth: .33
	  })
	  ,{
		    xtype:'commonflightselector',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.flightNo'),    //航班号
	  		name: 'flightNo',
	        maxLength : 12,
	        maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.maxLength'),    //长度已超过最大限制
	  		columnWidth:.33
	  },{
		  	fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.goodsName'),    //货物名称
	  		name: 'goodsName',
	  		allowBlank: false,
	  		columnWidth:.33,
			maxLength:200,
		    maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.airhandovebill.tip.maxLength')  //长度已超过最大限制
	  },{
		  	xtype:'numberfield',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingSpaceForm.volume'),    //货物体积
	  		name: 'volume',
	  		regex:/^-?\d+\.?\d{0,2}$/,
	        regexText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.formatError'),    //格式输入有误
	        maxLength : 14,
	        maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.maxLength'),    //长度已超过最大限制
	        allowBlank: false,
	  		columnWidth:.3,
	  		validator : function(value) {
	  	         if(value <= 0) {
	  	        	 return airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.enterGreaterThanZero');    //请输入大于0的值
	  	         } 
	  	         return true;
	  	    }
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingSpaceForm.square'),    //方
			columnWidth:.03
	  },{
		  	xtype:'numberfield',
	  		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingSpaceForm.weight'),    //货物重量
	  		name: 'weight',
	  		regex:/^-?\d+\.?\d{0,1}$/,
	        regexText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.formatError'),    //格式输入有误
	        maxLength : 7,
	        maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.maxLength'),    //长度已超过最大限制
	        allowBlank: false,
	  		columnWidth:.28,
	  		validator : function(value) {
	  	         if(value <= 0) {
	  	        	 return airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.enterGreaterThanZero');    //请输入大于0的值
	  	         } 
	  	         return true;
	  	    }
	  },{
	  		xtype: 'label',
	  		margin: '10 0 0 0',
			text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.bookingSpaceForm.kg'),    //公斤
			columnWidth:.05
	  },{
	  		fieldLabel: '备注',
	  		name: 'notes',
	  		margin:'3 5 3 0',
	  		labelWidth: 47,
	  		maxLength : 200,
	        maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.maxLength'),    //长度已超过最大限制
	  		columnWidth:.33
	  }],
	  buttons: [{
	      text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.save'),    //保存
	      cls:'yellow_button',
			plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
				seconds:5
			}),
	      handler: function() {
	      	  var form = this.up('form');
	      	  if(form.getForm().isValid()){
	      		  var vals = form.getForm().getValues();
	      		  var params= {'vo':{'dto': vals}};
	      		  if(form.isAddNew){
	      			 var wd = airfreight.bookingSpaceWindow; 
	      			 Ext.Ajax.request({
		   	        	  url: airfreight.realPath('addBookingSpace.action'),
		   	        	  jsonData: params,
		   			      success: function(response, opts) {
		   			         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.saveSuccess'), 'ok', 1000);   //保存成功
		   			         wd.close();
		   			      },
		   			      failure: function(response, opts) {
		   			         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.saveFailure'), 'error', 2000);   //保存失败
		   			      },
			   			  exception : function(response) {
	       					 var json = Ext.decode(response.responseText);
	       					 Ext.Msg.alert('exception',json.message);
	       					 wd.close();
			   			  }
	      			 });
	      		  }else{
	      			  Ext.Ajax.request({
			        	  url: airfreight.realPath('updateBookingSpace.action'),
			        	  jsonData: params,
						  success: function(response, opts) {
							  airfreight.bookingSpace.Grid.store.load(); 
							  airfreight.bookingSpaceWindow.close();
						      Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.updateSave'), 'ok', 1000);     //修改成功
						  },
						  failure: function(response, opts) {
						       Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.failure'),airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.updateFailure'));    //修改失败
						  }
			          });
	      		  }
	      	  }
	      }
	  },{
	      text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.cancel'),   //取消
	      cls:'yellow_button',
	      handler: function() {
	         this.up('window').close();
	      }
	  }]
});

//营业部订舱window
Ext.define('foss.airfreight.bookingSpaceWindow',{
	extend: 'Ext.window.Window',
	id:'foss_airfreight_bookingSpaceWindow_Id_' + (new Date().getUTCMilliseconds()),
	title: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.bookingSpaceWindow.title'),   //营业部订舱
	width: 800,
	modal:true,
	closeAction:'hide',
	bodyCls: 'autoHeight',
	layout: 'auto',
	bookingSpaceForm : null,
	getBookingSpaceForm: function(){
		if(this.bookingSpaceForm==null){
			this.bookingSpaceForm = Ext.create('foss.airfreight.bookingSpaceForm');
		}
		airfreight.bookingSpaceForm = this.bookingSpaceForm;
		return this.bookingSpaceForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getBookingSpaceForm()
				];
		me.callParent([cfg]);
	},
	listeners: {
		//显示的时候判断是否为新增，如果为新增，重置并初始化页面
		beforeshow: function(){
			 var form = airfreight.bookingSpaceForm;
  			 if(form.isAddNew){
  				  form.getForm().reset();
  				  Ext.Ajax.request({
  					  url: airfreight.realPath('initBookingSpace.action'),
  				      success: function(response, opts) {
  				    	var result = Ext.decode(response.responseText);
  				    	var form = airfreight.bookingSpaceForm.getForm();
  				    	form.findField('bookingNo').setValue(result.vo.dto.bookingNo);
  				    	
  				    	var acceptOrgCode = result.vo.dto.acceptOrgCode;
  				    	var acceptOrgName = result.vo.dto.acceptOrgName;
  				    	var deptRegionCode = result.vo.dto.deptRegionCode;
  				    	var deptRegionName = result.vo.dto.deptRegionName;

  				    	if(acceptOrgCode === "" || acceptOrgName ===""){
  				    		Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'),airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.notFindairDispatch'));    //根据当前部门为找到想对应的空运总调，请检查配置！
  				    	}else{
  	  						//load公共选择器
  	  						var cmbAcceptOrgCode = airfreight.bookingSpaceForm.getForm().findField('acceptOrgCode');
  	  						cmbAcceptOrgCode.setValue(acceptOrgCode);
  	  						cmbAcceptOrgCode.getStore().load({params:{'commonOrgVo.airDispatch' : acceptOrgName}});
  	  						
  	  						airfreight.bookingSpaceForm.getForm().findField('acceptOrgName').setValue(acceptOrgName);
  				    	}
  						airfreight.bookingSpaceForm.getForm().findField('deptRegionCode').setValue(deptRegionCode);
  						airfreight.bookingSpaceForm.getForm().findField('deptRegionName').setValue(deptRegionName);
  				      },
  				      failure: function(response, opts) {
  				         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.initPageFailure'), 'error', 2000);   //初始化界面失败!
  				      },
  				      exception: function(response, opts){
  				    	var json = Ext.decode(response.responseText);
  				    	Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'),json.message);
  				      }
  				 });
  			  }
		}
	}
});

airfreight.bookingSpaceWindow = Ext.create('foss.airfreight.bookingSpaceWindow');
 
//受理备注window
Ext.define('foss.airfreight.acceptBookingSpaceWindow',{
	extend: 'Ext.window.Window',
	id:'foss_airfreight_acceptBookingSpaceWindow_Id',
	title: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptNotes'),    //'受理备注',
	width: 400,
	modal:true,
	closeAction:'hide',
	bodyCls: 'autoHeight',
	layout: 'anchor',	
	acceptType: null,
	items:[{
		xtype: 'textareafield',
		itemId: 'acceptNotes',
		grow: true,
		anchor: '100%',
		labelWidth: 80,
		maxLength : 200,
        maxLengthText:airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.tip.maxLength'),    //长度已超过最大限制
		name: 'acceptNotes',
		fieldLabel: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.label.acceptNotes')    //受理备注
	}],
	buttons: [{ 
		text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.ok'),    //确定
		cls:'yellow_button',
		handler: function(){
			var window = this.up('window');
			if(!window.getComponent('acceptNotes').isValid()){
			    return;
			}
			var acceptNotes = window.getComponent('acceptNotes').getValue();
			if(acceptNotes == null || acceptNotes.trim() == ''){
				Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.enterAcceptNotes'));		//请输入受理备注
			}else{
				//选中的操作列表
				var acceptIds =  new Array();
				var selectRows = airfreight.bookingSpace.Grid.getSelectionModel().getSelection();
				for(var index in selectRows){
					if(selectRows[index].data.acceptState != 'UN_ACCEPT'){
						Ext.Msg.alert(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.bookingNoIs')+selectRows[index].data.bookingNo + airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.messageWasAccepted'));   //信息已受理，不可再次受理
						return;
					}
					//订舱编号
					acceptIds.push(selectRows[index].data.bookingNo);   
				}
				var acceptType = this.up('window').acceptType;
				Ext.Ajax.request({
		        	  url: airfreight.realPath('acceptBookingSpace.action'),
		        	  params: {
		        		  'vo.acceptIds' : acceptIds,
		        		  'vo.acceptType' : acceptType,
		        		  'vo.acceptNotes': acceptNotes
		        	  },
		        	  success: function(response, opts) {
		        		 if(acceptType){
		        			 Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.acceptedSuccess'), 'ok', 1000);   //提示受理成功
		        		 }else{
		        			 Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.unacceptedSuccess'), 'ok', 1000);   //提示拒绝成功
		        		 }
				         airfreight.bookingSpace.Grid.store.load(); 
				         //刷新未受理条数
						 Ext.Ajax.request({
							 url: airfreight.realPath('queryNoAcceptCount.action'),
							 success: function(response, opts) {
								 var result = Ext.decode(response.responseText);
								 var textItem = airfreight.bookingSpace.Grid.getDockedItems('toolbar[dock="bottom"]')[0].query('textfield');
								 textItem[0].setValue(result.vo.noAcceptCount);
						     },
						     failure: function(response, opts) {
						         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.loadNotAcceptFailure'), 'error', 2000);   //加载未受理总条数失败!
						     }
						 });
				         window.close();
				      },
				      failure: function(response, opts) {
				         Ext.ux.Toast.msg(airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.message'), airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.msg.acceptedFailure'), 'error', 2000);   //提示受理失败
				      }
				});
			}
		}
	},{ 
		text: airfreight.bookingSpace.i18n('foss.airfreight.bookingSpace.button.cancel'),   //取消
		cls:'yellow_button',
		handler: function(){
			 this.up('window').close();
		}
	}],
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

airfreight.dept = '';

//初始化入口
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var bookingSpaceQueryForm = Ext.create('foss.airfreight.bookingSpace.QueryForm');
	airfreight.bookingSpace.QueryForm = bookingSpaceQueryForm;
	var bookingSpaceGrid = Ext.create('foss.airfreight.bookingSpace.Grid');
	airfreight.bookingSpace.Grid = bookingSpaceGrid;
	var notesForm = Ext.create('foss.airfreight.bookingSpace.notesForm')
	airfreight.bookingSpace.notesForm = notesForm;
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-bookingSpace_content',
		cls:'panelContent',
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [bookingSpaceQueryForm,bookingSpaceGrid,notesForm],
		renderTo: 'T_airfreight-bookingSpace-body'
	});	
	
});