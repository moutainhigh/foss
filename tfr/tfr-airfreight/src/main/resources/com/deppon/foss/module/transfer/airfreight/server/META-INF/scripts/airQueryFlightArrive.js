//定义查询航空到达的model 200968
Ext.define('Foss.airfreight.airQueryFlightArriveModel',{
	extend:'Ext.data.Model',
	fields:[{name: 'airWaybillNo', type:'string'},
	        {name: 'waybillNo', type:'string'},
			{name: 'flightArriveType', type:'string'},
	        {name: 'airLineCode', type:'string'},
	        {name: 'flightNo', type:'string'},
	        {name: 'waybillGoodsQty', type:'string'},
	        {name: 'waybillWeight', type:'string'},
	        {name: 'arriveGoodsQty', type:'string'}, //arriveGoodsQty arriveGoodsWeight
	        {name: 'arriveGoodsWeight', type:'string'},
	        {name: 'agentCompanyName', type:'string'},
	        {name: 'arriveTime', type:'string',
	        	convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}
	        },
	        {name: 'operateUserName', type:'string'},
	        {name: 'note', type:'string'}
	        ]
});

//定义查询正单交接单的store 200968
Ext.define('Foss.airfreight.airQueryFlightArriveStore',{ //Foss.airfreight.handOverStore
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.airQueryFlightArriveModel', //Foss.airfreight.handOverModel
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryFlightArrive.action'),
		//定义一个读取器 存放查询结果集（JSON Reader是用一个代理来读取服务端响应的JSON格式的返回值. 一般用于将结果加载成一个存储集）
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象  airQueryFlightArriveVo.
			root: 'airQueryFlightArriveVo.airQueryFlightArriveDtos',    //root: 响应中包含记录数据的节点对应的根属性名称
			totalProperty : 'totalCount'                      //totalProperty: 数据中的所有记录数属性名
		}
	},
	listeners: {//查询条件 （从前台传递给后台的参数airHandOverBillVO）
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.airQueryFlightArriveIndex.queryPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airQueryFlightArriveVo.airQueryFlightArriveDto.orgCode' : queryParams.orgCode,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : queryParams.airWaybillNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.waybillNo' : queryParams.waybillNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.airLineCode' : queryParams.airLineCode,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.flightNo' : queryParams.flightNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.flightArriveType' : queryParams.flightArriveType,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.beginTime' : queryParams.beginTime,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.endTime' : queryParams.endTime
					}
				});	
			}
		}
	}
});

//定义查询条件的panel  flightArrive
Ext.define('Foss.airfreight.flightArriveQuery',{
	extend:'Ext.form.Panel',
	//title: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.queryflightarrive.title'),//查询条件
	title:"查询条件",
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    //maxLengthText:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.tip.maxLength') //长度已超过最大限制
	    maxLengthText:"长度已超过最大限制!"
	},
	layout:'column',
	items:[{
		xtype:'dynamicorgcombselector',
		//fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.orgCode'), //空运总调
		fieldLabel:"配载部门",
		name:'orgCode',     //配载部门
		type:'ORG',
		readOnly:true,
		doAirDispatch:'Y',
		allowBlank:false,
		columnWidth:.25
	},{
		//fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airflightarrive.label.airWaybillNo'),  //正单号
		fieldLabel:"正单号",
		name:'airWaybillNo', //正单号
		columnWidth:.25
	},{
		//fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.waybillNo'),  //运单号
		fieldLabel:"运单号",
		name:'waybillNo',    //运单号
		columnWidth:.25
	},{
		xtype:'commonairlinesselector', //航空公司
		displayField : 'code',// 显示名称
		valueField : 'code',// 值 
		//fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.public.airLineTwoletter'),
		fieldLabel:"航空公司",
		name: 'airLineCode',
		columnWidth:.25
	},{
	    xtype:'commonflightselector',
		//fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightNo'), //航班号
	    fieldLabel:"航班号",
	    name:'flightNo', //航班号
		columnWidth:.25
	},FossDataDictionary.getDataDictionaryCombo('FLIGHT_ARRIVE_TYPE',{
		name: 'flightArriveType',
/*		fieldLabel:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightNo'), //航班号
*/		fieldLabel:"空运到达类型",
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .25
		},{
			'valueCode':'',
			'valueName': airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.all')
	}),{
		xtype : 'rangeDateField',
		//fieldLabel :departure.i18n('tfr.departure.QueryForm.form.operate.time.label'),// '操作时间',
		fieldLabel: "到达时间",
		columnWidth : .5,
		allowBlank:false,
		disallowBlank:true,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		id : 'Foss_airfreight_arrive_createTime_Id',
		fieldId:'Foss_airfreight_arrive_createTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'beginTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'00', '00', '00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		dateRange:7,
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'23', '59', '59'), 'Y-m-d H:i:s')
	},{
		xtype:'button',
		//text:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.button.reset'),  //重置
		text:"重置",
		width:90,
		handler: function() {
			var form = this.up('form').getForm();
			var records = form.reset();
			form.findField('beginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
			form.findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')); 

			var cmbOrgCode = form.findField('orgCode');
			if(airfreight.airQueryFlightArriveIndex.dept.airDispatch == 'Y'){
				cmbOrgCode.setValue(airfreight.airQueryFlightArriveIndex.deptCode);
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airQueryFlightArriveIndex.deptName}});
			}
		},
		columnWidth:.10
	},{
		readOnly:true,
		columnWidth:.8
	},{
		xtype:'button',
		text:"查询",
		//text:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.button.search'),  //查询
		width:90,
		cls:'yellow_button',
		handler:function(){
			var form = this.up('form').getForm();
			if(form.isValid()){
				airfreight.airQueryFlightArriveIndex.pagingBar.moveFirst();
			}
		},
		columnWidth:.10
	}],
	listeners : {
		render : function(panel,text){
			var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
			Ext.Ajax.request({
				url : airfreight.realPath('queryAirDispatch.action'), //airDispatchVo
				jsonData:array,
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
					airfreight.airQueryFlightArriveIndex.dept = dept;
					airfreight.airQueryFlightArriveIndex.deptCode = dept.code;
					airfreight.airQueryFlightArriveIndex.deptName = dept.name;
					var cmbOrgCode = panel.getForm().findField('orgCode');
					if(dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airQueryFlightArriveIndex.deptName}});
						cmbOrgCode.setValue(airfreight.airQueryFlightArriveIndex.deptCode);
					}
				}
			});
		}
	}
});

//定义展示查询结果的grid zwd 200968
Ext.define('Foss.airfreight.airQueryFlightArriveResult',{ //Foss.airfreight.handOverResult
	extend:'Ext.grid.Panel',
	//title:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.handOverResult.title'),  //显示列表
    title:"显示列表",
	frame: true,
	//height:800,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			//空运到达类型
		 	//  1）AGENT_TO_AIRPORT_PICK_UP 代理到机场提货 
			//	2）GOODS_ARRIVE_AGENCY 货物到达代理处
			var status = record.get('flightArriveType');
			if(status == 'AGENT_TO_AIRPORT_PICK_UP'){
    			return 'predeliver_notice_customer_row_purole';
			}
            if(status == 'GOODS_ARRIVE_AGENCY'){
			    return 'predeliver_notice_customer_row_white';
			}
		}
	},
    columns:[{
    	xtype:'actioncolumn',
		header: "操作",  //操作
        flex : 0.4,
        items:[{
            iconCls: 'deppon_icons_edit',
            tooltip: "修改",  //修改
            handler: function(grid, rowIndex, colIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	var flightArriveType = record.data.flightArriveType;
            	var airWaybillNo = record.data.airWaybillNo;
            	
            	//代理到机场提货
            	if(flightArriveType == 'AGENT_TO_AIRPORT_PICK_UP'){
			
            	//var detailResultStore = airfreight.detailInfoqueryAirFlightArriveResult.getStore();
            	  
            			
						//张卫东
                        // 新增的主界面


					var queryAirWaybillNoStore = Ext.create('Foss.airfreight.queryAirWaybillNoStore');
					var detailInfoWindow = airfreight.airQueryFlightArrivedetailInfoWindow;

					//var infoForm = detailInfoWindow.getEditAirHandOverBillForm();
					var infoForm = detailInfoWindow.getEditAirQueryFlightArriveForm();
					infoForm.getForm().reset();
					infoForm.items.items[0].items.items[2].setVisible(false);
					//infoForm.getForm().findField('flightArriveQuery').setVisible(false);
					infoForm.getForm().findField('flightArriveType').setReadOnly(true);
					infoForm.getForm().findField('airWaybillNo').setReadOnly(true);
                    


					infoForm.getForm().loadRecord(record);
				    airfreight.addPanel=infoForm.getForm();
					airfreight.detailInfoqueryAirFlightArriveResult.getStore().removeAll();	          		

					detailInfoWindow.show();
					airfreight.queryAirFlightArriveResult.getStore().load();  



            			//var detailInfoWindow = airfreight.airQueryFlightArrivedetailInfoWindow;
            			
            			//var editForm = detailInfoWindow.getEditAirHandOverBillForm();
            			//editForm.getForm().reset();

            			//editForm.getForm().loadRecord(record);
            			
            			//detailInfoWindow.show();

//*************************************************************//
                	
            }

            	
            	if(flightArriveType == 'GOODS_ARRIVE_AGENCY'){


//qqq
				var queryAirWaybillNoStore = Ext.create('Foss.airfreight.queryAirWaybillNoStore');
				var detailInfoWindow = airfreight.airQueryFlightArrivedetailInfoWindow;

				//var infoForm = detailInfoWindow.getEditAirHandOverBillForm();
				var infoForm = detailInfoWindow.getEditAirQueryFlightArriveForm();
				infoForm.getForm().reset();
				infoForm.items.items[0].items.items[2].setVisible(false);
				//infoForm.getForm().findField('flightArriveQuery').setVisible(false);
				infoForm.getForm().findField('flightArriveType').setReadOnly(true);
				infoForm.getForm().findField('airWaybillNo').setReadOnly(true);


				infoForm.getForm().loadRecord(record);
				airfreight.addPanel=infoForm.getForm();
				airfreight.detailInfoqueryAirFlightArriveResult.getStore().removeAll();	          		

				detailInfoWindow.show();
				//airfreight.queryAirFlightArriveResult.getStore().load();  
                airfreight.detailInfoqueryAirFlightArriveResult.getStore().load()
            		
            		}
            	
            	
            	}
            	
       
            	
            	
      }]
    },{
	//	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
	    header: "正单号",
		dataIndex: 'airWaybillNo' ,
		flex: 1 
    },{
	//	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
	    header: "运单号",
		dataIndex: 'waybillNo' ,
		flex: 1 
    },{
	//	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
	     dataIndex:'flightArriveType',
	    header: "空运到达类型",
	     renderer : function(value){
			if(value === "" || value == null){
				return "";
			}
			return FossDataDictionary.rendererSubmitToDisplay(value,'FLIGHT_ARRIVE_TYPE');
		},
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightDate'),   //航班日期
		header: "航空公司",
		dataIndex: 'airLineCode' ,
		flex: 1 
    },{
    	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightNo'),   //航班号
		dataIndex: 'flightNo' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightDate'),   //航班日期
		header:"开单件数",
		dataIndex: 'waybillGoodsQty' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.flightDate'),   //航班日期
		header:"开单重量",
		dataIndex: 'waybillWeight' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.takeOffTime'),  //起飞时间
		header:"到达件数",
		dataIndex: 'arriveGoodsQty' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.takeOffTime'),  //起飞时间
		header:"到达重量",
		hidden : true,
		dataIndex: 'arriveGoodsWeight' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.handoverOrg'),   //交货单位
		header:"代理公司", 
		dataIndex: 'agentCompanyName' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.createTime'),   //制单日期
		header:"到达时间",
		dataIndex: 'arriveTime' ,
		flex: 1 
    },{
    	//header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.createUserName'),  //制单人
		header:"操作人",
		dataIndex: 'operateUserName' ,
		flex: 1 
    },{
    	// header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.handoverEmp'),  
		header:"备注",
		dataIndex: 'note' , 
		flex: 1 
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airQueryFlightArriveStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');//查询结果前面的选择框 ExtJS自身封装
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		airfreight.airQueryFlightArriveIndex.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	getSelectRecords : function(){
		return this.getSelectionModel().getSelection();
	}
});

//model 2015-07-02 根据正单号查询运单信息和流水信息
Ext.define('Foss.airfreight.queryAirWaybillNoModel',{
	extend:'Ext.data.Model',
	fields:[
	        {name: 'airLineCode', type: 'string'},
	        {name: 'airWaybillNo', type:'string'},
	        {name: 'flightNo', type:'string'},
	        {name: 'waybillGoodsQty', type:'string'},
	        {name: 'waybillWeight', type:'string'},
	        {name: 'agentCompanyName', type:'string'},
	        {name: 'waybillNo', type:'string'}
	        ]
});


//model 2015-07-30 代理到机场提货 根据正单号查询信息
Ext.define('Foss.airfreight.queryAirFlightArriveModel',{
	extend:'Ext.data.Model',
	fields:[
	        {name: 'airLineCode', type: 'string'},
	        {name: 'flightNo', type:'string'},
	        {name: 'arriveGoodsQty', type:'string'},
	        {name: 'arriveGoodsWeight', type:'string'},
	        {name: 'agentCompanyName', type:'string'}
	        ]
});

//定义航空公司信息的model
Ext.define('Foss.airfreight.queryAirlinesModel',{
	extend:'Ext.data.Model',
	fields:[{name:'code', type:'string'},
	        {name:'simpleName', type:'string'},
	        {name:'name', type:'string'},
	        {name:'logo', type:'string'},
	        {name:'prifixName', type:'string'}
	        ]
});

//定义查询航空公司信息的store
Ext.define('Foss.airfreight.queryAirlinesStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.airfreight.queryAirlinesModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAllAirlines.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'printAirWaybillTagVO.airlinesEntityList'
		}
	}
});

//定义操作按钮的panel
Ext.define('Foss.airfreight.airQueryFlightArriveManagement',{ //Foss.airfreight.handOverManagement
	extend:'Ext.form.Panel',
	layout:'column',
	defaultType: 'button',
	items:[{
		//text:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.button.add'),  //新增
		text:"到达录入",
		width:90,
		handler : function(){
			var record = Ext.create('Foss.airfreight.queryAirWaybillNoStore');
			var detailInfoWindow = airfreight.airQueryFlightArrivedetailInfoWindow;

			//var infoForm = detailInfoWindow.getEditAirHandOverBillForm();
			var infoForm = detailInfoWindow.getEditAirQueryFlightArriveForm();
			infoForm.getForm().reset();
			//infoForm.getForm().loadRecord(record);
		
			airfreight.detailInfoqueryAirFlightArriveResult.getStore().removeAll();			
			detailInfoWindow.show();
		}
	}]/*,
	listeners : {
		render : function(panel,opt){
			var currentDept = airfreight.airQueryFlightArriveIndex.dept;
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			Ext.Array.each(btns, function(item, index){
				item.hide();
			});
			//新增
			if(airfreight.airQueryFlightArriveIndex.isPermission('airfreight/saveOrUpdateAirHandOverBillButton')){
				btns[0].show();
			}
		}
	}*/
});


//新增添空运到达类型为:代理到机场提货 2015-07-30
Ext.define('Foss.airfreight.queryAirFlightArriveStore',{ 
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.queryAirFlightArriveModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAirFlightArrive.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airQueryFlightArriveVo.airQueryFlightArriveDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.addPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : queryParams.airWaybillNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.flightArriveType' : queryParams.flightArriveType
					}
				});	
			}
		}
	}
});

//定义查询正单交接单明细的store
Ext.define('Foss.airfreight.queryAirWaybillNoStore',{ 
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.queryAirWaybillNoModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAirWaybillNo.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airQueryFlightArriveVo.airQueryFlightArriveDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.addPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : queryParams.airWaybillNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.flightArriveType' : queryParams.flightArriveType
					}
				});	
			}
		},

		//此监听用于支持翻页勾选
		'load' : function( store, records, successful, eOpts){
			
			airfreight.airQueryFlightArriveIndex.waybillMap.clear();

		//	var airQueryFlightArriveDtos = store.proxy.reader.rawData.airQueryFlightArriveVo.airQueryFlightArriveDtos;
			var record;
			for(var i in records){
				record = records[i];
				var waybillNo = record.get('waybillNo');
				var mapKey = waybillNo;
				airfreight.airQueryFlightArriveIndex.waybillMap.add(mapKey,record);
				if(airfreight.airQueryFlightArriveIndex.waybillGoodsMap.get(mapKey) != null){
					var selectRecord = airfreight.airQueryFlightArriveIndex.waybillMap.get(mapKey);
					airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid.getSelectionModel().select(selectRecord,true,true);
				}
			}
			//waybillStore_loading.hide();
		}

	}

});

//-----------------
//定义查询正单交接单明细的store


Ext.define('Foss.airfreight.queryAirWaybillNoPickUpStore',{ 
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.queryAirWaybillNoModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAirWaybillNo.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airQueryFlightArriveVo.airQueryFlightArriveDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.addPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : queryParams.airWaybillNo,
						'airQueryFlightArriveVo.airQueryFlightArriveDto.flightArriveType' : queryParams.flightArriveType
					}
				});	
			}
		}
	}
});


//-----------------

//定义查询条件的panel
Ext.define("Foss.airfreight.queryFlightArriveDetailPanel",{
	extend:'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.tip.maxLength')  //长度已超过最大限制
	},
	layout:'column',
	items:[{
		xtype:'combobox',
		name: 'flightArriveType',
		displayField:'valueName',
		//allowBlank: false,
		valueField:'valueCode',
		fieldLabel:"空运到达类型",
		labelWidth:100,
		columnWidth: .25,
		value:'AGENT_TO_AIRPORT_PICK_UP',
		store:FossDataDictionary.getDataDictionaryStore('FLIGHT_ARRIVE_TYPE'),
	    listeners: {
			change : function(field, newValue, oldValue, eOpts) {
			var flightArriveType=field.getValue();
			if(flightArriveType == 'AGENT_TO_AIRPORT_PICK_UP'){
				var me=airfreight.airQueryFlightArrivedetailInfoWindow;
			   me.getEditAirQueryFlightArriveForm().items.items[1].setVisible(true);
			    me.getEditAirQueryFlightArriveForm().items.items[2].setVisible(false);
			}
		 	if(flightArriveType  == 'GOODS_ARRIVE_AGENCY'){
				var me=airfreight.airQueryFlightArrivedetailInfoWindow;
				me.getEditAirQueryFlightArriveForm().items.items[1].setVisible(false);
				me.getEditAirQueryFlightArriveForm().items.items[2].setVisible(true);
			}
			
			}
		}	
	},
	{
		fieldLabel:"正单号",
		name:'airWaybillNo', //正单号
		allowBlank:false,
		columnWidth:.30,
		
	},{
		xtype:'button',
		text:"查询",  //查询
		name:'flightArriveQuery',
		cls:'yellow_button',
		columnWidth:.10,
		handler:function(){
			var form = this.up('form').getForm();
			airfreight.addPanel = form;
			if(form.isValid()){
		//123
	    //1）AGENT_TO_AIRPORT_PICK_UP 代理到机场提货 
		//	2）GOODS_ARRIVE_AGENCY 货物到达代理处
		//	if(flightArriveType.getValue() == 'AGENT_TO_AIRPORT_PICK_UP')
				
			 	var formValues = form.getValues();
            	var flightArriveType = formValues.flightArriveType;
            	if(flightArriveType == 'AGENT_TO_AIRPORT_PICK_UP'){
            		airfreight.queryAirFlightArriveResult.getStore().load();            		
            	}else if(flightArriveType == 'GOODS_ARRIVE_AGENCY'){
            		airfreight.detailInfoqueryAirFlightArriveResult.getStore().load();
            	}else{
            		Ext.Msg.alert("提示","请选择空运到达类型!");
            	}
				//airfreight.store.load();
			}
		}
	}]
});

//定义编辑正单交接单字段的panel 200968 2015-06-29
Ext.define("Foss.airfreight.addAirQueryFlightArriveBillPanel",{ 
	extend:'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.tip.maxLength')  //长度已超过最大限制
	},
	layout:'column',
	items:[{
		fieldLabel:"航空公司",
		name: 'airLineCode',
		readOnly:true,
		columnWidth:.20
	},{
		fieldLabel:"航班号",
	    name:'flightNo', 
		readOnly:true,
		columnWidth:.20
	},{
		fieldLabel:"开单件数",
		name: 'waybillGoodsQty' ,
		readOnly:true,
		columnWidth:.20
	},{
		fieldLabel:"开单重量",
		name: 'waybillWeight' ,
		readOnly:true,
		columnWidth:.20
	},{
		fieldLabel:"代理公司", 
		name: 'agentCompanyName' ,
		readOnly:true,
		columnWidth:.20
	}]
});




//定义到达时间、备注 panel  zwd 200968 2015-06-29
Ext.define("Foss.airfreight.controlFlightArriveDetailPanel",{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	width:'100%',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60,
		maxLength:20,
	    maxLengthText:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.tip.maxLength') //长度已超过最大限制
	},
	items:[{
	
    	xtype:'datetimefield_date97',
    	fieldLabel:"到达时间", 
		id : 'Foss_airfreight_arrive_flightDate_ID',
		dateConfig : {
			el : 'Foss_airfreight_arrive_flightDate_ID-inputEl'
		},
    	allowBlank:false,
		name:'arriveTime', //flightDate
		time : true,
		editable:false,
		value :	Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'00', '00', '00'), 'Y-m-d H:i:s'),
		columnWidth:.30

	},{
    	// header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.handoverEmp'),  
		xtype:'textareafield',
		fieldLabel:"备注",
		name: 'note' , 
		maxLength:50,
	    columnWidth: .70
    }]
});


//定义展示  空运到达类型为:快递到机场提货  根据正单号查出结果来 zwd 200968 2015-07-30 queryAirFlightArrive
Ext.define("Foss.airfreight.queryAirFlightArriveResult",{
	extend:'Ext.grid.Panel',
    frame: true,
   // bodyCls: 'autoHeight',
    height:500,
	cls: 'autoHeight',
	title:"正单信息",
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns:[{
/*    	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airWaybillNo'), //单号
*/		header:"航空公司",
    	dataIndex: 'airLineCode' ,
		flex: 1 
    },{
    	header:"航班号",
		dataIndex: 'flightNo' ,
		flex: 1 
    },{
    	header:"开单件数",
		dataIndex: 'arriveGoodsQty' ,
		flex: 1 
    },{
        header:"开单重量",
    	dataIndex: 'arriveGoodsWeight' ,
		flex: 1 
    },{
    	header:"代理公司",
		dataIndex: 'agentCompanyName' ,
		flex: 1 
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.queryAirFlightArriveStore');
		me.callParent([cfg]);
	}
});


//定义展示正单明细的grid zwd 200968 2015-06-29
//var waybillStore_loading = null;
Ext.define("Foss.airfreight.detailInqueryAirFlightArrivefoResult",{
	extend:'Ext.grid.Panel',
    frame: true,
    height:500,
   // bodyCls: 'autoHeight',
	cls: 'autoHeight',
/*	title:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.handOverResult.title'),  //显示列表
*/	hidden : false,
	title:"运单信息",
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    plugins: [{
        ptype: 'rowexpander',
        pluginId: 'Foss.airfreight.detailInqueryAirFlightArrivefoResult_Plugin_ID',
		rowsExpander: false,
		rowBodyElement : 'Foss.airfreight.airQueryFlightArrive.GoodsGrid' // Foss.stock.stockmanage.GoodsGrid
	}],
    columns:[{
/*    	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airLineName'),  //航空公司
*/		header:"运单号",
    	dataIndex: 'waybillNo' ,
		flex: 1 
    },{
/*    	header: airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.label.airWaybillNo'), //单号
*/		header:"航空公司",
    	dataIndex: 'airLineCode' ,
		flex: 1 
    },{
    	header:"航班号",
		dataIndex: 'flightNo' ,
		flex: 1 
    },{
    	header:"开单件数",
		dataIndex: 'waybillGoodsQty' ,
		flex: 1 
    },{
        header:"开单重量",
    	dataIndex: 'waybillWeight' ,
		flex: 1 
    },{
    	header:"代理公司",
		dataIndex: 'agentCompanyName' ,
		flex: 1 
    }],
	 constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.queryAirWaybillNoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	
	listeners: {
		select : function(rowModel, record, index, eOpts) {
			var grid = this,
			plugin = grid.getPlugin('Foss.airfreight.detailInqueryAirFlightArrivefoResult_Plugin_ID');
			var waybillNo = record.get('waybillNo');
			var airWaybillNo = record.get('airWaybillNo');
			
			var mapKey = waybillNo;

			var goodsStore = Ext.create('Foss.airfreight.airQueryFlightArrive.GoodsStore',{
				listeners: {
					'load': function(store, records, successful, eOpts ){
						var goodsMap = new Ext.util.HashMap();
						//定义 是否已经 已记录入的流水号(默认是否 需要添加到goodsMap中)
						for(var i = 0; i<records.length;i++){
							var flage=true;
							var record = records[i];
							for(var j=0;j<airfreight.airQueryFlightArriveDtosSelected.length;j++){
								  var serialNo_Detail=airfreight.airQueryFlightArriveDtosSelected[j].serialNo;
								  if(serialNo_Detail==record.get('serialNo')){
									  flage=false;
									  break;
								  }
							}
							if(flage){
								 goodsMap.add(record.get('serialNo'),record);
							}
						}
						
						airfreight.airQueryFlightArriveIndex.waybillGoodsMap.add(mapKey,goodsMap);
					}
				}
			});
			
			goodsStore.load({
				params:{'airQueryFlightArriveVo.airQueryFlightArriveDto.waybillNo': waybillNo,
				       'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : airWaybillNo}
			});	
			
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var subWaybillNo = store.getAt(0).get('waybillNo');
				if(subWaybillNo == record.get('waybillNo')){
					item.getSelectionModel().selectAll(true);
				}
			}
		},
		deselect : function(rowModel, record, index, eOpts) {
			var grid = this,
			plugin = grid.getPlugin('Foss.airfreight.detailInqueryAirFlightArrivefoResult_Plugin_ID');
			var waybillNo = record.get('waybillNo');
			var mapKey = waybillNo;
			
			airfreight.airQueryFlightArriveIndex.waybillGoodsMap.removeAtKey(mapKey);
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var subWaybillNo = store.getAt(0).get('waybillNo');
				if(subWaybillNo == record.get('waybillNo')){
					item.getSelectionModel().deselectAll(true);
				}
			}
		}
	}
});

// model 200968 2015-07-06
Ext.define('Foss.airfreight.airQueryFlightArrive.waybillserialModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'waybillNo', type: 'string'},
		{name: 'serialNo' , type: 'string'},
		{name: 'airWaybillNo', type: 'string'}
	]
});


//流水明细 Store 2015-07-08
Ext.define('Foss.airfreight.airQueryFlightArrive.GoodsStore',{//Foss.stock.stockmanage.GoodsStore
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.airQueryFlightArrive.waybillserialModel',// Foss.stock.stockmanage.GoodsModel
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: airfreight.realPath('queryAirWaybillSerialNo.action'), 
		reader : {
			type : 'json',
			root : 'airQueryFlightArriveVo.airQueryFlightArriveDtos'
		}
    }
});
//保存的已经录入的流水号 信息
airfreight.airQueryFlightArriveDtosSelected=null;
//www
//货件库存 Grid （第二层表格） Foss.stock.stockmanage.GoodsGrid
Ext.define('Foss.airfreight.airQueryFlightArrive.GoodsGrid', {
	extend:'Ext.grid.Panel',
	//id:'Foss_stock_stockmanage_GoodsGrid_ID',
	columnLines: true,
    frame: true,
    autoScroll : true,
   // Height:300,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var airWaybillNo = record.get('airWaybillNo');
		var waybillNo = record.get('waybillNo');
		
		var grid = this;
		Ext.Ajax.request({
			url: airfreight.realPath('queryAirWaybillSerialNo.action'),//queryStock.action
			params:{
				    'airQueryFlightArriveVo.airQueryFlightArriveDto.airWaybillNo' : airWaybillNo,
				    'airQueryFlightArriveVo.airQueryFlightArriveDto.waybillNo' : waybillNo
				   // 'airQueryFlightArriveVo.airQueryFlightArriveDto.orgCode' : orgCode
				},
			success:function(response){
				var result = Ext.decode(response.responseText);
				//airQueryFlightArriveDtos airQueryFlightArriveVo.airQueryFlightArriveDtos
				//TODO
				grid.store.loadData(result.airQueryFlightArriveVo.airQueryFlightArriveDtos);
				//添加 已录的上层运单信息
				airfreight.airQueryFlightArriveIndex.waybillMap.add(waybillNo,record);
				airfreight.airQueryFlightArriveDtosSelected=null;
				airfreight.airQueryFlightArriveDtosSelected=result.airQueryFlightArriveVo.airQueryFlightArriveDtosSelected
				var serialNo;
				//清空新增 修改保存的 流水Map	
				//airfreight.airQueryFlightArriveIndex.waybillGoodsMap.clear();
				var mapKey = waybillNo;
				if(airfreight.airQueryFlightArriveDtosSelected.length>0){
					for(var i=0;i<grid.store.getCount();i++){
						serialNo = grid.store.getAt(i).get('serialNo');
						for(var j=0;j<airfreight.airQueryFlightArriveDtosSelected.length;j++){
						  var serialNo_Detail=airfreight.airQueryFlightArriveDtosSelected[j].serialNo;
						  if(serialNo==serialNo_Detail){
//						   airfreight.airQueryFlightArriveIndex.waybillGoodsMap.add(serialNo,
//								   grid.store.getAt(i));
						   grid.getSelectionModel().select(grid.store.getAt(i),true,true);
						   //选中置灰
						  grid.getView().addRowCls(grid.getStore().data.items[i], 'disabledrow');
						  //airfreight.airQueryFlightArriveIndex.waybillMap.clear();
						  //airfreight.airQueryFlightArriveIndex.waybillMap.add(waybillNo,);
						  //airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid.getSelectionModel().select(selectRecord,true,true);
						  }
						}
						/*,,
						for (var key in airfreight.airQueryFlightArriveIndex.waybillGoodsMap) {  
							for (var key2 in key){
								if(serialNo==map[key2].serialNo){
									   grid.getSelectionModel().select(grid.store.getAt(i),true,true);
							   }	
							}
							
				        } */
					}
				}
				 
			}
		})
		
	},
	columns: [{
			header: '流水号', 
			dataIndex: 'serialNo' 
		}],    
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airQueryFlightArrive.GoodsStore');//Foss.stock.stockmanage.GoodsStore
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	
	//airfreight.airQueryFlightArriveIndex.waybillMap      stock.stockmanage.waybillMap
	//airfreight.airQueryFlightArriveIndex.waybillGoodsMap stock.stockmanage.waybillGoodsMap
	
	listeners: {
			select : function(rowModel, record, index, eOpts) {
				var waybillNo = record.get('waybillNo');
				var mapKey = waybillNo;

				var serialNo = record.get('serialNo');
				var goodsMap = airfreight.airQueryFlightArriveIndex.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(serialNo,record);
				airfreight.airQueryFlightArriveIndex.waybillGoodsMap.add(mapKey,goodsMap);
				//勾选上层运单库存
				var selectRecord = airfreight.airQueryFlightArriveIndex.waybillMap.get(mapKey);
				airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid.getSelectionModel().select(selectRecord,true,true);
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var waybillNo = record.get('waybillNo');
				var mapKey = waybillNo;
				
				var serialNo = record.get('serialNo');
				var selectedList = grid.getSelectionModel().selected;
				if(selectedList.length = airfreight.airQueryFlightArriveDtosSelected.length){
					//取消勾选上层运单库存
					var superGrid = this.superGrid;
					var superRecord = airfreight.airQueryFlightArriveIndex.waybillMap.get(mapKey);
					superGrid.getSelectionModel().deselect(superRecord,true);
					airfreight.airQueryFlightArriveIndex.waybillGoodsMap.removeAtKey(mapKey);
					
				}else{
					var goodsMap = airfreight.airQueryFlightArriveIndex.waybillGoodsMap.get(mapKey);
					goodsMap.removeAtKey(serialNo);
					airfreight.airQueryFlightArriveIndex.waybillGoodsMap.add(mapKey,goodsMap);
				}
			}
	}
});

//qq airfreight.addHandOverBillPanel
//airfreight.editAirQueryFlightArriveForm = Ext.create('Foss.airfreight.editAirQueryFlightArriveForm');
airfreight.queryFlightArriveDetailPanel = Ext.create('Foss.airfreight.queryFlightArriveDetailPanel');

/*airfreight.addAirQueryFlightArriveBillPanel = Ext.create('Foss.airfreight.addAirQueryFlightArriveBillPanel');*/
airfreight.controlFlightArriveDetailPanel = Ext.create('Foss.airfreight.controlFlightArriveDetailPanel');

airfreight.detailInfoqueryAirFlightArriveResult = Ext.create('Foss.airfreight.detailInqueryAirFlightArrivefoResult');

airfreight.queryAirFlightArriveResult = Ext.create('Foss.airfreight.queryAirFlightArriveResult');
//定义包含4个子edit detail panel的编辑panel 
Ext.define('Foss.airfreight.editAirQueryFlightArriveForm',{
	extend: 'Ext.form.Panel',
	closeAction: 'hide',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	cls: "panelContentNToolbar",
	frame: true,
	autoScroll : true,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	height:800,
	items: [
	airfreight.queryFlightArriveDetailPanel,
	//airfreight.addAirQueryFlightArriveBillPanel,
	airfreight.queryAirFlightArriveResult,
	airfreight.detailInfoqueryAirFlightArriveResult,
	airfreight.controlFlightArriveDetailPanel,

	{
    	xtype:"button",
		width:90,
    	text:"保存",  //保存
    	
    	handler: function() {
    		var billInfo = this.up('form').getForm();
    		var saveBtn = this;
    		if(billInfo.getValues().arriveTime === null || billInfo.getValues().arriveTime === ''){
    			Ext.Msg.alert("提示","到达时间不能为空");  
    			return;
    		}
    		if(!billInfo.isValid()){
    			return;
    		}
//    		1）AGENT_TO_AIRPORT_PICK_UP 代理到机场提货 
			//	2）GOODS_ARRIVE_AGENCY 货物到达代理处
			/*	if(flightArriveType.getValue() == 'AGENT_TO_AIRPORT_PICK_UP'){	
					formbasic.findField('airfreight.addAirQueryFlightArriveBillPanel').setValue(true);
				}else{
					formbasic.findField('airfreight.detailInfoqueryAirFlightArriveResult').setValue(true);
				}*/
			//	//    		1）AGENT_TO_AIRPORT_PICK_UP 代理到机场提货 
    		if(billInfo.getValues().flightArriveType == 'AGENT_TO_AIRPORT_PICK_UP'){
    			var arriveTime = new Date(billInfo.getValues().arriveTime);
        		arriveTime = new Date(arriveTime.getFullYear(),arriveTime.getMonth(),arriveTime.getDate());
        		var currentServerTime = login.currentServerTime;
        		if(arriveTime > new Date(currentServerTime.getFullYear(),currentServerTime.getMonth(),currentServerTime.getDate())){
        			Ext.Msg.show({
                		title:"提示",  //提示
    					msg:"到达时间晚于当前日期,是否继续录入?", 
    					buttons:Ext.Msg.YESNO,
    					icon: Ext.Msg.QUESTION, 
    					fn : function(btn){
    						if(btn == 'no'){
    							return;
    						}else{
    				    		var billValues = billInfo.getValues();  //获取表单上的value
    							var formRecord = saveBtn.up('form').getForm().getRecord();  //获取当前form上的record
    							//将更改过的表单值赋给record里面相应的属性
    							var arriveTime = billValues.arriveTime;
    							saveBtn.setDisabled(true);　
    							
    				    		//将formRecord和records传入dto
    				    		var array = {airQueryFlightArriveVo:{airQueryFlightArriveDto:{airWaybillNo:billValues.airWaybillNo,arriveTime:billValues.arriveTime,flightArriveType:billValues.flightArriveType,note:billValues.note}}}; 
    				    		//Ajax请求后台的新增action
    				    		Ext.Ajax.request({
    				    			url : airfreight.realPath('addAirFlightArrivePickUp.action'),
    				    			jsonData:array,
    				    			success : function(response) {
    				    				var json = Ext.decode(response.responseText);
    				    				airfreight.airQueryFlightArrivedetailInfoWindow.hide();
    				    				Ext.ux.Toast.msg("提示", "保存成功", 'ok', 1000);  //提示保存成功
    				    			},
    				    			exception : function(response) {
    				    				var json = Ext.decode(response.responseText);
    				    				Ext.Msg.alert("提示","保存失败");
    				    			}
    				    		});
    						}
    					}
        			});//end of Ext.Msg.show
        		}
        		else{
		    		var billValues = billInfo.getValues();  //获取表单上的value
					var formRecord = saveBtn.up('form').getForm().getRecord();  //获取当前form上的record
					//将更改过的表单值赋给record里面相应的属性
					var arriveTime = billValues.arriveTime;
					saveBtn.setDisabled(true);
					
					
		    		//将formRecord和records传入dto
		    		var array = {airQueryFlightArriveVo:{airQueryFlightArriveDto:{airWaybillNo:billValues.airWaybillNo,arriveTime:billValues.arriveTime,flightArriveType:billValues.flightArriveType,note:billValues.note}}}; 
		    		//Ajax请求后台的新增action
		    		Ext.Ajax.request({
		    			url : airfreight.realPath('addAirFlightArrivePickUp.action'),
		    			jsonData:array,
		    			success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				airfreight.airQueryFlightArrivedetailInfoWindow.hide();
		    				Ext.ux.Toast.msg("提示", "保存成功", 'ok', 1000);  //提示保存成功
		    				
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.Msg.alert("提示","保存失败");
		    			}
		    		});
				}
    		}else if(billInfo.getValues().flightArriveType == 'GOODS_ARRIVE_AGENCY'){
    			var arriveTime = new Date(billInfo.getValues().arriveTime);
        		arriveTime = new Date(arriveTime.getFullYear(),arriveTime.getMonth(),arriveTime.getDate());
        		var currentServerTime = login.currentServerTime;
        		if(arriveTime > new Date(currentServerTime.getFullYear(),currentServerTime.getMonth(),currentServerTime.getDate())){
			Ext.Msg.show({
        		title:"提示",  //提示
				msg:"到达时间晚于当前日期,是否继续录入?", 
				buttons:Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION, 
				fn : function(btn){
					if(btn == 'no'){
						return;
					}else{
						
	        			var goodsMapList = airfreight.airQueryFlightArriveIndex.waybillGoodsMap.getValues();
	        			saveBtn.setDisabled(true);
			    		
			    		//var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							//Ext.ux.Toast.msg('提示', '请选择需要出库的货物！', 'error', 2000);
							Ext.ux.Toast.msg("提示", "请选择要保存的流水", 'error', 2000);
							return;
						}
						
						var waybillGoodsList = new Array();
						//var outStockList = new Array();
						
						var RecordMapList;
						
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
	                        if(RecordMapList.length >0){

								for(var j=0;j<RecordMapList.length;j++){
							
								{
									waybillGoodsList.push(RecordMapList[j].data);
								}
							  }

							}else{
							Ext.ux.Toast.msg("提示", "请选择要保存的流水", 'error', 2000);
							return;
							}



							
						}
					
						//AirQueryFlightArriveVo airQueryFlightArriveDtos

							
						var billValues = billInfo.getValues();  //获取表单上的value

				
						
					var jsonParam = {airQueryFlightArriveVo: {airQueryFlightArriveDtos:waybillGoodsList,
						airQueryFlightArriveDto:{arriveTime:billValues.arriveTime,
						flightArriveType:billValues.flightArriveType,
						note:billValues.note}}};
								
					//var jsonParam = {stockVO: {outStockList:outStockList}};
					//Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
					Ext.Ajax.request({
		    			url: airfreight.realPath('addAirFlightArriveAgency.action'),
		    			jsonData:jsonParam,
		    			success:function(response){
		    				//Ext.Msg.hide();
		    				var result = Ext.decode(response.responseText);
		    				airfreight.airQueryFlightArriveIndex.waybillGoodsMap.clear();
		    				airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid.store.load();
		    				airfreight.airQueryFlightArrivedetailInfoWindow.hide();
		    				Ext.ux.Toast.msg("提示", "保存成功", 'ok', 1000);  //提示保存成功
		    			},
		    			exception : function(response) {
		    				//Ext.Msg.hide();
		    				var result = Ext.decode(response.responseText);
		    				Ext.Msg.alert("提示","保存失败");
		    			}
	    			});	
					
				   }
					
					
					
				}
			});
        		}//end of > <
        		else{
					
        			var goodsMapList = airfreight.airQueryFlightArriveIndex.waybillGoodsMap.getValues();
        			saveBtn.setDisabled(true);
		    		
		    		//var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						//Ext.ux.Toast.msg('提示', '请选择需要出库的货物！', 'error', 2000);
						Ext.ux.Toast.msg("提示", "请选择要保存的流水", 'error', 2000);
						return;
					}
					
					var waybillGoodsList = new Array();
					//var outStockList = new Array();
					
					var RecordMapList;
					
					for(var i=0;i<goodsMapList.length;i++){
						RecordMapList = goodsMapList[i].getValues();
                        if(RecordMapList.length >0){

							for(var j=0;j<RecordMapList.length;j++){
						
							{
								waybillGoodsList.push(RecordMapList[j].data);
							}
						  }

						}else{
						Ext.ux.Toast.msg("提示", "请选择要保存的流水", 'error', 2000);
						return;
						}



						
					}
				
					//AirQueryFlightArriveVo airQueryFlightArriveDtos

						
					var billValues = billInfo.getValues();  //获取表单上的value

			
					
				var jsonParam = {airQueryFlightArriveVo: {airQueryFlightArriveDtos:waybillGoodsList,
					airQueryFlightArriveDto:{arriveTime:billValues.arriveTime,
					flightArriveType:billValues.flightArriveType,
					note:billValues.note}}};
							
				//var jsonParam = {stockVO: {outStockList:outStockList}};
				//Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
				Ext.Ajax.request({
	    			url: airfreight.realPath('addAirFlightArriveAgency.action'),
	    			jsonData:jsonParam,
	    			success:function(response){
	    				//Ext.Msg.hide();
	    				var result = Ext.decode(response.responseText);
	    				airfreight.airQueryFlightArriveIndex.waybillGoodsMap.clear();
	    				airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid.store.load();
	    				airfreight.airQueryFlightArrivedetailInfoWindow.hide();
	    				Ext.ux.Toast.msg("提示", "保存成功", 'ok', 1000);  //提示保存成功
	    			},
	    			exception : function(response) {
	    				//Ext.Msg.hide();
	    				var result = Ext.decode(response.responseText);
	    				Ext.Msg.alert("提示","保存失败");
	    			}
    			});	
				
			   }
    		}
    	    
    	}
    },{
    	xtype:"button",
		width:90,
    	text:airfreight.airQueryFlightArriveIndex.i18n('foss.airfreight.airhandovebill.button.close'),
    	handler: function() {
    		var detailInfoWindow = airfreight.airQueryFlightArrivedetailInfoWindow;
    		var a = this.up("form").down("button");
    		detailInfoWindow.close();
    	}
    }]/*,
	listeners : {
		render : function(panel,opt){
			var currentDept = airfreight.airQueryFlightArriveIndex.dept;
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			btns[btns.length-2].hide();
			//保存按钮
			if(airfreight.airQueryFlightArriveIndex.isPermission('airfreight/saveOrUpdateAirHandOverBillButton')){
				btns[btns.length-2].show();
			}
		}
	}*/
});

//airfreight.editAirHandOverBillForm = Ext.create('Foss.airfreight.editAirHandOverBillForm');

//定义弹出的编辑窗口 zwd  200968 2015-06-29
Ext.define('Foss.airfreight.airQueryFlightArriveeditDetailInfoWindows',{ //Foss.airfreight.airQueryFlightArriveeditDetailInfoWindows
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	closable:true,
	width:1200,
	autoScroll : true,
	height:800,
	//bodyCls: 'autoHeight',
	//cls: 'autoHeight',
	//resizable:false,
	//editAirHandOverBillForm
	editAirQueryFlightArriveForm : null,
	getEditAirQueryFlightArriveForm: function(){
		if(this.editAirQueryFlightArriveForm==null){
			this.editAirQueryFlightArriveForm = Ext.create('Foss.airfreight.editAirQueryFlightArriveForm');
		}
		return this.editAirQueryFlightArriveForm;
	},
    listeners : {
		beforehide : function(me) {
			var form =me.getEditAirQueryFlightArriveForm();
			form.getForm().reset();
			airfreight.queryAirFlightArriveResult.getStore().removeAll();	
			airfreight.detailInfoqueryAirFlightArriveResult.getStore().removeAll();	
			form.items.items[0].items.items[2].setVisible(true);
			//form.getForm().findField('flightArriveQuery').setVisible(true);
			form.getForm().findField('flightArriveType').setReadOnly(false);
			form.getForm().findField('airWaybillNo').setReadOnly(false);
		},
		beforeshow:function(me){
			var form =me.getEditAirQueryFlightArriveForm();
			form.items.items[4].setDisabled(false);
			var flightArriveType=form.getForm().findField('flightArriveType').getValue();
			if(flightArriveType == 'AGENT_TO_AIRPORT_PICK_UP'){
			   me.getEditAirQueryFlightArriveForm().items.items[1].setVisible(true);
			    me.getEditAirQueryFlightArriveForm().items.items[2].setVisible(false);
			}
		 	if(flightArriveType  == 'GOODS_ARRIVE_AGENCY'){
				me.getEditAirQueryFlightArriveForm().items.items[1].setVisible(false);
				me.getEditAirQueryFlightArriveForm().items.items[2].setVisible(true);
			}
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditAirQueryFlightArriveForm()
				];
		me.callParent([cfg]);
	}
});

airfreight.airQueryFlightArriveIndex.dept = '';
airfreight.airQueryFlightArriveIndex.deptCode = '';
airfreight.airQueryFlightArriveIndex.deptName = '';


airfreight.airQueryFlightArrivedetailInfoWindow = Ext.create('Foss.airfreight.airQueryFlightArriveeditDetailInfoWindows');

//200968
Ext.onReady(function() {
	Ext.QuickTips.init();//气泡提示（例如：新增成功）
	airfreight.addPanel = null;
	airfreight.airQueryFlightArriveIndex.queryPanel = Ext.create('Foss.airfreight.flightArriveQuery');
	
	//var waybillGrid = Ext.create('Foss.stock.stockmanage.WaybillGrid');
	var waybillGrid = Ext.create('Foss.airfreight.detailInqueryAirFlightArrivefoResult');
	airfreight.detailInfoqueryAirFlightArriveResult.waybillGrid = waybillGrid;
	//Foss.airfreight.airQueryFlightArrive.GoodsGrid

	// var waybillGrid = Ext.create('Foss.stock.stockmanage.WaybillGrid');
	// stock.stockmanage.waybillGrid = waybillGrid;
	/* *查询库存界面：
	 *	库存查询条件表单：Foss.stock.stockmanage.QueryForm
	 *	
	 *	运单库存表格：Foss.stock.stockmanage.WaybillGrid   --  Foss.airfreight.detailInqueryAirFlightArrivefoResult
	 *	货件库存 Grid （第二层表格）：Foss.stock.stockmanage.GoodsGrid
	*/ 
	airfreight.airQueryFlightArriveIndex.waybillMap = new Ext.util.HashMap();//
	airfreight.airQueryFlightArriveIndex.waybillGoodsMap = new Ext.util.HashMap();//用于查询库存界面存放已勾选的货件库存
	
	airfreight.managementPanel = Ext.create('Foss.airfreight.airQueryFlightArriveManagement');
	
	airfreight.airQueryFlightArriveIndex.resultPanel = Ext.create('Foss.airfreight.airQueryFlightArriveResult');
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airQueryFlightArriveIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [airfreight.airQueryFlightArriveIndex.queryPanel,airfreight.managementPanel,airfreight.airQueryFlightArriveIndex.resultPanel],
		renderTo: 'T_airfreight-airQueryFlightArriveIndex-body'
	});
});