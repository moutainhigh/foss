//定义正单交接单的model
Ext.define('Foss.airfreight.airhandoverbill.handOverModel',{
	extend:'Ext.data.Model',
	fields:[{name: 'orgCode', type: 'string'},
	        {name: 'flightNo', type:'string'},
	        {name: 'flightDate', type:'string',
	        	convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d');
	 				}else{
		 				return null;
		 			}
	 			}
	        },
	        {name: 'takeOffTime', type:'string',
	        	convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}
	        },
	        {name: 'handoverOrg', type:'string'},
	        {name: 'handoverEmp', type:'string'},
	        {name: 'createUserName', type:'string'},
	        {name: 'createTime', type:'string',
	        	 convert : function(value){
		 				if(value != null && value != ''){
			 				var date = new Date(value);
			 				return Ext.Date.format(date,'Y-m-d');
		 				}else{
			 				return null;
			 			}
		 			}
	        },
	        {name: 'orgName', type:'string'},
	        {name: 'airHandoverNo', type:'string'},
	        {name: 'waybillNo', type:'string'},
	        {name: 'airWaybillNo', type:'string'},
	        {name: 'airWaybillStockState', type:'string'},
	        {name: 'airLevel', type:'string'},
	        {name: 'airHandOverType', type:'string'},
	        {name: 'expressArriveCode', type:'string'},
	        {name: 'expressArriveName', type:'string'}
	        ]
});
//定义查询正单交接单的store
Ext.define('Foss.airfreight.airhandoverbill.handOverStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.airhandoverbill.handOverModel',
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAirHandOverBill.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airHandOverBillVO.airHandOverBillList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.airhandoverbill.queryPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airHandOverBillVO.airHandOverBillDto.flightNo' : queryParams.flightNo,
						'airHandOverBillVO.airHandOverBillDto.flightDate' : queryParams.flightDate,
						'airHandOverBillVO.airHandOverBillDto.orgCode' : queryParams.orgCode,
						'airHandOverBillVO.airHandOverBillDto.waybillNo' : queryParams.waybillNo,
						'airHandOverBillVO.airHandOverBillDto.airWaybillNo' : queryParams.airWaybillNo,
						'airHandOverBillVO.airHandOverBillDto.airHandoverNo' : queryParams.airHandoverNo,
						'airHandOverBillVO.airHandOverBillDto.airHandOverType' : queryParams.airHandOverType

					}
				});	
			}
		}
	}
});
//定义航空公司信息的model
Ext.define('Foss.airfreight.airhandoverbill.queryAirlinesModel',{
	extend:'Ext.data.Model',
	fields:[{name:'code', type:'string'},
	        {name:'simpleName', type:'string'},
	        {name:'name', type:'string'},
	        {name:'logo', type:'string'},
	        {name:'prifixName', type:'string'}
	        ]
});

//定义查询航空公司信息的store
Ext.define('Foss.airfreight.airhandoverbill.queryAirlinesStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.airfreight.airhandoverbill.queryAirlinesModel',
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


//定义正单交接单明细的model
Ext.define('Foss.airfreight.airhandoverbill.handOverBillDetailModel',{
	extend:'Ext.data.Model',
	fields:[{name: 'airLineName', type: 'string'},
	        {name: 'airLineCode', type: 'string'},
	        {name: 'airWaybillNo', type:'string'},
	        {name: 'goodsQty', type:'string'},
	        {name: 'grossWeight', type:'string'},
	        {name: 'billingWeight', type:'string'},
	        {name: 'deptRegionName', type:'string'},
	        {name: 'arrvRegionName', type:'string'},
	        {name: 'goodsName', type:'string'},
	        {name: 'packageStruction', type:'string'},
	        {name: 'feeTotal', type:'string'},
	        {name: 'notes', type:'string'},
	        {name: 'toid', type:'string'},
	        {name: 'id', type:'string'},
	        {name: 'airHandoverbillId', type:'string'},
	        {name:'goodsVolume',type:'string'},
	        {name:'transportType',type:'string'}
	        ]
});
//定义查询正单交接单明细的store
Ext.define('Foss.airfreight.airhandoverbill.handOverBillDetailStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.airhandoverbill.handOverBillDetailModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryHandOverAirWaybill.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airHandOverBillVO.airHandOverBillDetailList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = airfreight.airhandoverbill.addPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'airHandOverBillVO.airHandOverBillDto.flightDate' : queryParams.flightDate,
						'airHandOverBillVO.airHandOverBillDto.flightNo' : queryParams.flightNo
					}
				});	
			}
		},
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//总票数
			var billNoTotal = totalArray.length;
			//总件数
			var goodsQtyTotal = 0;
			//毛重合
			var weightTotal = 0;
			//计费重量
			var billingWeightTotal = 0;
			var dockedItmes = airfreight.airhandoverbill.detailInfoResult.getDockedItems('toolbar[dock="bottom"]');
			var toolbarArray = dockedItmes[0].items.items;
			if(totalArray <= 0){
				toolbarArray[0].setValue(billNoTotal);
				toolbarArray[1].setValue(goodsQtyTotal);
				toolbarArray[2].setValue(weightTotal + '  ' + airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.weight'));
				toolbarArray[2].hideValue = weightTotal;
				toolbarArray[3].setValue(billingWeightTotal + '  ' + airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.weight'));
				toolbarArray[3].hideValue = billingWeightTotal;
				return;
			}
			for(var i=0;i<totalArray.length;i++){
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				weightTotal = weightTotal + Ext.Number.from(totalArray[i].data.grossWeight,0);
				billingWeightTotal = billingWeightTotal + Ext.Number.from(totalArray[i].data.billingWeight,0);
			}
			var count = 0;
			for(var j=0;j<toolbarArray.length;j++){
				if(count===0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count===1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count===2){
					toolbarArray[j].setValue(weightTotal + '  ' + airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.weight'));
					toolbarArray[j].hideValue = weightTotal;
				}else if(count===3){
					toolbarArray[j].setValue(billingWeightTotal + '  ' + airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.weight'));
					toolbarArray[j].hideValue = billingWeightTotal;
				}
				count ++;
			}
			
		}
	}
});













//定义查询条件的panel
Ext.define('Foss.airfreight.airhandoverbill.handOverQuery',{
	extend:'Ext.form.Panel',
	title: airfreight.airhandoverbill.i18n('foss.airfreight.handOverQuery.title'),//查询条件
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	layout : 'column',  
	/*layoutConfig : {  
	      columns : 4 //设置表格布局默认列数为4列  
	 },*/
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength') //长度已超过最大限制
	},
	
/*	layout:'column',
*/	items:[{
		xtype:'dynamicorgcombselector',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.orgCode'), //空运总调
		name:'orgCode',
		type:'ORG',
		readOnly:true,
		doAirDispatch:'Y',
		allowBlank:false,
		columnWidth:.25
	},{
	    xtype:'commonflightselector',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightNo'), //航班号
		name:'flightNo',
		columnWidth:.25
	},{
    	xtype:'datefield',
    	fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightDate'),  //航班日期
    	allowBlank:false,
		name:'flightDate',
		editable:false,
		value: login.currentServerTime,
		format:'Y-m-d',
		columnWidth:.25
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
		name:'airHandoverNo',
		columnWidth:.25
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.waybillNo'),  //运单号
		name:'waybillNo',
		columnWidth:.25
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airWaybillNo'),  //正单号
		name:'airWaybillNo',
		columnWidth:.25
	},{
		fieldLabel : airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandOverType'),
		name : 'airHandOverType',
		labelWidth:100,
		xtype : 'combobox',
		columnWidth:.25,
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"PRECISION_HANDOVER", "value":"零担交接"},
	            {"key":"PACKAGE_HANDOVER", "value":"快递交接"}
	        ]
	    })
	},{
		readOnly:true,
		columnWidth:.25
	},{
		xtype:'button',
		text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.reset'),  //重置
		width:90,
		handler: function() {
			var form = this.up('form').getForm();
			var records = form.reset();
			form.findField('flightDate').setValue(Ext.Date.format(login.currentServerTime,'Y-m-d'));
			
			var cmbOrgCode = form.findField('orgCode');
			if(airfreight.airhandoverbill.dept.airDispatch == 'Y'){
				cmbOrgCode.setValue(airfreight.airhandoverbill.deptCode);
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airhandoverbill.deptName}});
			}
		},
		columnWidth:.10
	},{
		readOnly:true,
		columnWidth:.8
	},{
		xtype:'button',
		text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.search'),  //查询
		width:90,
		cls:'yellow_button',
		handler:function(){
			var form = this.up('form').getForm();
			if(form.isValid()){
				airfreight.airhandoverbill.pagingBar.moveFirst();
			}
		},
		columnWidth:.10
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
					airfreight.airhandoverbill.dept = dept;
					airfreight.airhandoverbill.deptCode = dept.code;
					airfreight.airhandoverbill.deptName = dept.name;
					var cmbOrgCode = panel.getForm().findField('orgCode');
					if(dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airhandoverbill.deptName}});
						cmbOrgCode.setValue(airfreight.airhandoverbill.deptCode);
					}
				}
			});
		}
	}
});








//定义操作按钮的panel
Ext.define('Foss.airfreight.airhandoverbill.handOverManagement',{
	extend:'Ext.form.Panel',
	layout:'column',
	defaultType: 'button',
	items:[{
		text:'新增',
		width:90,
		handler : function(){
			var record = Ext.create('Foss.airfreight.airhandoverbill.handOverBillDetailStore');
			var detailInfoWindow = airfreight.airhandoverbill.detailInfoWindow;
			var infoForm = detailInfoWindow.getEditAirHandOverBillForm();
			infoForm.getForm().reset();
			infoForm.getForm().loadRecord(record);
			
			var cmbHandoverOrg = airfreight.airhandoverbill.addHandOverBillPanel.getForm().findField('handoverOrg');
			cmbHandoverOrg.getStore().load({params:{'commonOrgVo.name' : airfreight.airhandoverbill.deptName}});
			cmbHandoverOrg.setValue(airfreight.airhandoverbill.deptCode);
			
			airfreight.airhandoverbill.detailInfoResult.getStore().removeAll();
			airfreight.airHandOverBillId = null;
			airfreight.airWayBillNos = new Array();
			Ext.Ajax.request({
				url:airfreight.realPath('getAirHandOverBillNo.action'),
				success :function(response){
					var json = Ext.decode(response.responseText);
    				var airHandoverNo = json.airHandOverBillVO.airHandOverBillNo;
    				infoForm.getForm().findField('airHandoverNo').setValue(airHandoverNo);
				},
				exception:function(response){
					var json = Ext.decode(response.responseText);
    				Ext.Msg.alert('失败',json.message);  //提示失败
				}
			});
			detailInfoWindow.show();
		}
	},{
		text:'出库',
		width:90,
		handler : function(){
			var records = airfreight.airhandoverbill.resultPanel.getSelectRecords();
			if(records == null || records.length == 0){
				Ext.Msg.alert('提示','请选择一条数据进行操作!');  //请选择一条数据进行操作!
				return;
			}
			var airHandOverBillDtos = new Array();
			for(var i = 0 ; i < records.length ; i++){
				airHandOverBillDtos.push(records[i].data);
				if(records[i].data.airHandOverType=='PACKAGE_HANDOVER'){
					Ext.Msg.alert('提示','快递交接不做出库');  //快递交接不做出库
					return;
				}
    		}
			//将需要的参数传入vo中的dto中的属性：交接单id，交接部门，交接状态
			var array = {airHandOverBillVO:{airHandOverBillDtos:airHandOverBillDtos}};
			//访问请求的action
			Ext.Ajax.request({
				url : airfreight.realPath('outStockAirHandOverBill.action'),
    			jsonData:array,
    			success : function(response) {
    				var json = Ext.decode(response.responseText);
    				//操作成功后重新load页面
    				airfreight.airhandoverbill.resultPanel.store.load();
    				Ext.ux.Toast.msg('提示','出库成功', 'ok', 1000);  //出库成功
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.Msg.alert('失败',json.message);  //提示失败
    			}
			});
		}
	}],
	listeners : {
		render : function(panel,opt){
			var currentDept = airfreight.airhandoverbill.dept;
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			Ext.Array.each(btns, function(item, index){
				item.hide();
			});
			//新增
			if(airfreight.airhandoverbill.isPermission('airfreight/saveOrUpdateAirHandOverBillButton')){
				btns[0].show();
			}
			//出库
			if(airfreight.airhandoverbill.isPermission('airfreight/outStockAirHandOverBillButton')){
				btns[1].show();
			}
		}
	}
});



//定义展示查询结果的grid
Ext.define('Foss.airfreight.airhandoverbill.handOverResult',{
	extend:'Ext.grid.Panel',
	title:airfreight.airhandoverbill.i18n('foss.airfreight.handOverResult.title'),  //显示列表
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var status = record.get('airWaybillStockState');
			if(status!='Y'){
    			return 'predeliver_notice_customer_row_purole';
			}else{
			    return 'predeliver_notice_customer_row_white';
			}
		}
	},
    columns:[{
    	xtype:'actioncolumn',
		header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.operator'),  //操作
        flex : 0.4,
        items:[{
            iconCls: 'deppon_icons_edit',
            tooltip: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.update'),  //修改
            handler: function(grid, rowIndex, colIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	var detailRecord = null;
            	var id = record.data.id;
            	var array = {airHandOverBillVO:{airHandOverBillDto:{id:id}}};
            	var detailResultStore = airfreight.airhandoverbill.detailInfoResult.getStore();
            	//用于储存移除的正单号
            	airfreight.airWayBillNos = new Array();   
            	//根据交接单ID查找交接单信息和正单明细信息
            	Ext.Ajax.request({
            		url:airfreight.realPath('loadAirHandOverBillInfo.action'),
            		jsonData:array,
            		success : function(response){
                    	detailResultStore.removeAll();
            			var json = Ext.decode(response.responseText);
            			record.data = json.airHandOverBillVO.airHandOverBillDto.airHandOverBillEntity;
            			var airHandOverBillDetailList = json.airHandOverBillVO.airHandOverBillDto.airHandOverBillDetailList;
            			
            			record.data.flightDate = Ext.Date.format(new Date(record.data.flightDate),'Y-m-d');
            			record.data.takeOffTime = Ext.Date.format(new Date(record.data.takeOffTime),'Y-m-d H:i:s');

            			var detailInfoWindow = airfreight.airhandoverbill.detailInfoWindow;
            			var editForm = detailInfoWindow.getEditAirHandOverBillForm();
            			editForm.getForm().reset();
            			
            			//load公共选择器
            			var cmbHandoverOrg = airfreight.airhandoverbill.addHandOverBillPanel.getForm().findField('handoverOrg');
            			cmbHandoverOrg.getStore().load({params:{'commonOrgVo.code' : record.data.handoverOrg}});
            			cmbHandoverOrg.setValue(record.data.handoverOrg);
            			

            			var cmbHandoverEmp = airfreight.airhandoverbill.addHandOverBillPanel.getForm().findField('handoverEmp');
            			cmbHandoverEmp.getStore().load({params:{'employeeVo.employeeDetail.queryParam' : record.data.handoverEmp}});
            			cmbHandoverEmp.setValue(record.data.handoverEmp);
            			
            			var cmpOrg=editForm.getForm().findField('nextTfrOrg');
            			cmpOrg.getStore().load({params:{'commonOrgVo.code' : record.data.expressArriveCode}});
            			cmpOrg.setValue(record.data.expressArriveCode);
        				
            			editForm.getForm().loadRecord(record);
            			editForm.getForm().findField('addAirHandOverType').setValue(record.data.airHandOverType);

            			for(var i = 0 ; i<airHandOverBillDetailList.length; i++){
            				detailRecord= new Ext.data.reader.Json(airHandOverBillDetailList[i]);
            				detailResultStore.add(detailRecord);
            			}
            			var dockedItmes = airfreight.airhandoverbill.detailInfoResult.getDockedItems();
            			var toolbarArray = dockedItmes[dockedItmes.length-1].items.items;
        				toolbarArray[4].setValue(record.data.createUserName);
        				toolbarArray[5].setValue(Ext.Date.format(new Date(record.data.createTime),'Y-m-d'));
        				airfreight.airHandOverBillId = id;
            			detailInfoWindow.show();
            		},
            		exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.failure'),json.message);  //提示失败
        			}
            	});
            }
        }]
    },{
		header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
		dataIndex: 'airHandoverNo' ,
		flex: 1 
    },{
		header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandOverType'),  //空运交接类型
		dataIndex: 'airHandOverType' ,
		flex: 1.2,
		renderer:function(value){
		   return value=='PACKAGE_HANDOVER'? '快递交接单':'零担交接'
		}
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightNo'),   //航班号
		dataIndex: 'flightNo' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightDate'),   //航班日期
		dataIndex: 'flightDate' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.takeOffTime'),  //起飞时间
		dataIndex: 'takeOffTime' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airWaybillStockState'),  //是否已出库
		dataIndex: 'airWaybillStockState' ,
		flex: 1 ,
		renderer : function(value){
			if(value=='Y'){
				return  airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.yes');
			}else if(value=='N'){
				return airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.no');
			}else{
				return '';
			}
		}
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.handoverOrg'),   //交货单位
		dataIndex: 'handoverOrg' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.handoverEmp'),  //交货人
		dataIndex: 'handoverEmp' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.createUserName'),  //制单人
		dataIndex: 'createUserName' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.createTime'),   //制单日期
		dataIndex: 'createTime' ,
		flex: 1 
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airhandoverbill.handOverStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		airfreight.airhandoverbill.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	getSelectRecords : function(){
		return this.getSelectionModel().getSelection();
	}
});


Ext.define('Foss.airfreight.airhandoverbill.airHandoverNoPanel',{
	extend:'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength') //长度已超过最大限制
	},
	layout:'column',
	items:[{
		xtype:'button',
		text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.print'),  //打印
		width:90,
		handler:function(){
			airfreight.airhandoverbill.printIds = '';
			var records = airfreight.airhandoverbill.detailInfoResult.getSelectionModel().getSelection();
			if(records.length == 0){
				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.chooseOne'));  //请选择一条数据进行操作!
				return;
			}
			var ids = ''; 
			for(var i = 0; i<records.length;i++){
				ids += records[i].data.toid+',';
			}
			airfreight.airhandoverbill.printIds = ids;
			Ext.create('Foss.airfreight.airhandoverbill.choosePrintTypeWindow').show();
		}
	},{
	    fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandoverNo'),  //交接单号
		xtype:'textfield',
		name:'airHandoverNo',
		readOnly:true
	}]
});


//定义查询交接单明细条件的panel
Ext.define("Foss.airfreight.airhandoverbill.queryDetailPanel",{
	extend:'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength')  //长度已超过最大限制
	},
	layout:'column',
	items:[{
    	xtype:'datefield',
    	fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightDate'),  //航班日期
    	allowBlank:false,
		name:'flightDate',
		editable:false,
		value: login.currentServerTime,
		columnWidth:.30,
		format:'Y-m-d',
		listeners:{blur:function(text,op){
			var form = this.up('form').getForm();
			if(form.isValid()){
				var array = {airHandOverBillVO:{airHandOverBillDto:{flightDate:form.getValues().flightDate,flightNo:form.getValues().flightNo}}};
				Ext.Ajax.request({
					url : airfreight.realPath('queryTakeOffTime.action'),
	    			jsonData:array,
	    			success : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				var takeOffTime = airfreight.airhandoverbill.detailInfoWindow.down('form').items.items[2].items.items[0]; //根据航班号获取起飞时间
	    				takeOffTime.setValue(json.airHandOverBillVO.airHandOverBillDto.takeOffTime);
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),json);  //提示失败
	    			}
				});
			}
		}}
	},{
	    xtype:'commonflightselector',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.flightNo'),  //航班号
		name:'flightNo',
		allowBlank:false,
		columnWidth:.30,
		listeners:{blur:function(text,op){
			var form = this.up('form').getForm();
			if(form.isValid()){
				var array = {airHandOverBillVO:{airHandOverBillDto:{flightDate:form.getValues().flightDate,flightNo:form.getValues().flightNo}}};
				Ext.Ajax.request({
					url : airfreight.realPath('queryTakeOffTime.action'),
	    			jsonData:array,
	    			success : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				var takeOffTime = airfreight.airhandoverbill.detailInfoWindow.down('form').items.items[2].items.items[0]; //根据航班号获取起飞时间
	    				takeOffTime.setValue(json.airHandOverBillVO.airHandOverBillDto.takeOffTime);
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),json);  //提示失败
	    			}
				});
			}
		}}
	},{
		xtype:'button',
		text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.search'),  //查询
		cls:'yellow_button',
		columnWidth:.10,
		handler:function(){
			var form = this.up('form').getForm();
			airfreight.airhandoverbill.addPanel = form;
			if(form.isValid()){
				airfreight.store.load();
			}
		}
	}]
});
//定义编辑正单交接单字段的panel
Ext.define("Foss.airfreight.airhandoverbill.addHandOverBillPanel",{
	extend:'Ext.form.Panel',
	layout:'column',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength')  //长度已超过最大限制
	},
	layout:'column',
	items:[{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.takeOffTime'),
		name:'takeOffTime',
		readOnly:true,
		maxLength:30,
		allowBlank:false,
		columnWidth:.30
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.bookingNo'),  //订舱号
		name:'bookingNo',
		columnWidth:.20,
		maxLenght:10
	},{
		xtype:'numberfield',
		minValue:0,
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.spaceWeight'),  //舱位重量
		name:'spaceWeight',
		columnWidth:.20,
		maxLength:9,
		maxValue: 999999999
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airLevel'),  //等级
		name:'airLevel',
		columnWidth:.20
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		doAirDispatch:'Y',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.handoverOrg'),  //交接单位
		allowBlank:false,
		name:'handoverOrg',
		columnWidth:.30
	},{
		xtype:'commonemployeeselector',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.handoverEmp'),  //交接人
		allowBlank:false,
		name:'handoverEmp',
		columnWidth:.30
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.notes'),  //备注
		name:'notes',
		maxLength:50,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength'), //长度已超过最大限制
		columnWidth:.30
	},{
		fieldLabel : airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airHandOverType'),//空运交接类型
		name : 'addAirHandOverType',
		labelWidth:100,
		xtype : 'combobox',
		columnWidth:.25,
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'PRECISION_HANDOVER',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"PRECISION_HANDOVER", "value":"零担交接"},
	            {"key":"PACKAGE_HANDOVER", "value":"快递交接"}
	        ]
	    }),
	    
	    listeners: {
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var form = this.up('form').getForm();
	    		if(newValue=='PACKAGE_HANDOVER'){

	    			form.findField('nextTfrOrg').setReadOnly(false);

	    		}else{
	    			form.findField('nextTfrOrg').setReadOnly(true);
	    			form.findField('nextTfrOrg').setValue(null);

	    		}
	    	}
	    	
	    	
	    }
	},{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.nextTfrOrg'),  //下一外场
		allowBlank:true,
		name:'nextTfrOrg',
		columnWidth:.30,
		readOnly:true
	}]
});

//定义根据正单号添加正单明细的panel
Ext.define("Foss.airfreight.airhandoverbill.controlDetailPanel",{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	width:'50%',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60,
		maxLength:20,
	    maxLengthText:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.maxLength') //长度已超过最大限制
	},
	items:[{
		name:'airLineTwoletter',
		displayField:'code',
		readOnly:false,
		valueField:'code',
		editable:false,
		store:Ext.create('Foss.airfreight.airhandoverbill.queryAirlinesStore'),
		xtype:'combobox',
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airLineName'),  //航空公司
		labelWidth:60,
		columnWidth: .40
	},{
		fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airWaybillNo'),  //正单号
		name:'airWaybillNo',
		columnWidth: .40
	},{
		xtype:'button',
		text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.addOne'),  //添加
		columnWidth: .20,
		handler: function(){
			var form = this.up('form').getForm();
			var formValues = form.getValues();
			if(formValues.airWaybillNo === ""){
				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.airWaybillNoIsRequired'));  //正单号都为必填项
				return;
			}
			var airWaybillNo = form.getValues().airWaybillNo;
			var airLineTwoletter = form.getValues().airLineTwoletter;
			var array = {airHandOverBillVO:{airHandOverBillDto:{airWaybillNo:airWaybillNo,airLineTwoletter:airLineTwoletter}}};
			var detailResultStore = airfreight.airhandoverbill.detailInfoResult.getStore();
			detailResultGrid = detailResultStore.data.items;
			Ext.Ajax.request({
				url : airfreight.realPath('querySingleAirWaybill.action'),
				jsonData:array,
				success : function(response) {
    				var json = Ext.decode(response.responseText);
    				var record = new Ext.data.reader.Json(json.airHandOverBillVO.airHandOverBillDetailEntity);
    				var gridRecord = null;
    				for(var i = 0 ; i < detailResultGrid.length; i++){
    					gridRecord = detailResultGrid[i].data;
    					if(gridRecord.airWaybillNo === record.airWaybillNo){
    						return;
    					}
    				}
    				detailResultStore.add(record);
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),json.message);
    			}
			});
		}
	}]
});


//定义展示正单明细的grid
Ext.define("Foss.airfreight.airhandoverbill.detailInfoResult",{
	extend:'Ext.grid.Panel',
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns:[{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airLineName'),  //航空公司
		dataIndex: 'airLineName' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.airWaybillNo'), //单号
		dataIndex: 'airWaybillNo' ,
		flex: 1 
    },{
		header: airfreight.airhandoverbill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),  //运输性质
		dataIndex: 'transportType' ,
		flex: 1.2 ,
		renderer:function(value){
			return value=='PACKAGE_AIR'? '快递空运':'精准空运';
		}
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.goodsQty'),  //件数
		dataIndex: 'goodsQty' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.grossWeight'),  //毛重
		dataIndex: 'grossWeight' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.billingWeight'),  //计费重量
		dataIndex: 'billingWeight' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.goodsVolume'),  //体积
		dataIndex: 'goodsVolume' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.deptRegionName'),  //始发站
		dataIndex: 'deptRegionName' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.arrvRegionName'),  //目的站
		dataIndex: 'arrvRegionName' ,
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.goodsName'),  //货物名称
		dataIndex: 'goodsName' ,
    	xtype : 'ellipsiscolumn',
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.packageStruction'),  //包装
		dataIndex: 'packageStruction' ,
    	xtype : 'ellipsiscolumn',
		flex: 1 
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.feeTotal'),   //运费
		dataIndex: 'feeTotal' ,
		flex: 1,
		renderer : function(value){
			return Ext.Number.from(value,0)/100;
		}
    },{
    	header: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.notes'),   //备注
		dataIndex: 'notes' ,
    	xtype : 'ellipsiscolumn',
		flex: 1 
    }],
    dockedItems:[{
	   xtype:'toolbar',
	   dock:'bottom',
	   layout:'column',
	   defaults:{
		 xtype:'textfield',
		 readOnly:true,
		 value:0,
		 labelWidth:70,
		 width:30
	   },
	   items:[{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.billTotal'),	  //票数
		   labelWidth:40,
		   columnWidth:.10
	   },{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.goodsQty'),  //件数
		   labelWidth:40,
		   columnWidth:.10
	   },{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.grossWeight'),  //毛重
		   labelWidth:40,
		   columnWidth:.15
	   },{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.billingWeight'),  //计费重量
		   columnWidth:.15
	   },{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.createUserName'),  //制单人
		   columnWidth:.15,
		   value: FossUserContext.getCurrentUser().employee.empName
	   },{
		   fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.createTime'),  //制单日期
		   columnWidth:.20,
		   value:Ext.Date.format(login.currentServerTime,'Y-m-d')
	   }]
	}],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airhandoverbill.handOverBillDetailStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.tbar = [{
			xtype:'button',
			text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.move'),  //移除
			handler: function(){
				var records = me.getSelectionModel().getSelection();
				if(records.length<1){
					Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.chooseOne'));  //请选择一条数据进行操作!
					return;
				}
				for(var i = 0 ; i < records.length; i++){
					if(airfreight.airHandOverBillId != null){
						airfreight.airWayBillNos.push(records[i].data.airWaybillNo);
					}
					me.store.remove(records[i]);
				}
			}
		}];
		airfreight.store = me.store;
		me.callParent([cfg]);
	}
});

airfreight.airhandoverbill.queryDetailPanel = Ext.create('Foss.airfreight.airhandoverbill.queryDetailPanel');
airfreight.airhandoverbill.addHandOverBillPanel = Ext.create('Foss.airfreight.airhandoverbill.addHandOverBillPanel');
airfreight.airhandoverbill.controlDetailPanel = Ext.create('Foss.airfreight.airhandoverbill.controlDetailPanel');
airfreight.airhandoverbill.detailInfoResult = Ext.create('Foss.airfreight.airhandoverbill.detailInfoResult');


//定义包含4个子edit detail panel的编辑panel
Ext.define('Foss.airfreight.airhandoverbill.editAirHandOverBillForm',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	cls: "panelContentNToolbar",
	frame: true,
	items: [
	Ext.create('Foss.airfreight.airhandoverbill.airHandoverNoPanel')
	,
	airfreight.airhandoverbill.queryDetailPanel
	,
	airfreight.airhandoverbill.addHandOverBillPanel
	,
	airfreight.airhandoverbill.controlDetailPanel
	,
	airfreight.airhandoverbill.detailInfoResult
	,{
    	xtype:"button",
		width:90,
    	text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.save'),  //保存
    	handler: function() {
    		var billInfo = this.up('form').getForm();
    		var saveBtn = this;
    		if(billInfo.getValues().takeOffTime === null || billInfo.getValues().takeOffTime === ''){
    			Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.takeOffTimeRequired'));  //提示起飞时间不能为空
    			return;
    		}
    		if(!billInfo.isValid()){
    			return;
    		}
    		
    		var airHandoverType=billInfo.findField('addAirHandOverType').getValue();
    		var nextTfrOrg=billInfo.findField('nextTfrOrg').getValue();

    		var isExpress=false;
			//如果为快递  则到达部门不能为空
    		if(airHandoverType=='PACKAGE_HANDOVER'){
    		    
    		    isExpress=true;
    		    
    			if(nextTfrOrg==''|| nextTfrOrg==null){
    				
    				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),'交接类型为快递时必须选择下一外场！');  
    				
    				return;
    				
    			}
    		}
    		
    		//如果是快递需要判断下一目的站所在城市与航空正单明细中的所在城市是否相同
    		if(isExpress){
	    		var detailResultGrid = airfreight.airhandoverbill.detailInfoResult.getStore().data.items;
	    		var records = new Array();
	    		for(var i = 0 ; i < detailResultGrid.length ; i++){
	    			records.push(detailResultGrid[i].data);
	    		}
	    		
	    		//将formRecord和records传入dto
	    		var array = 
	    	   {airHandOverBillVO:{airHandOverBillDto:
	    	    {   airWayBillNos:airfreight.airWayBillNos,
		    		airHandOverBillDetailList:records,
	    		    expressArriveCode:nextTfrOrg
	    		 }}}; 
    			Ext.Ajax.request({
    			    url : airfreight.realPath('judgeExpressAddress.action'),
					jsonData:array,
					success : function(response){
							  var flightDate = new Date(billInfo.getValues().flightDate);
					    		flightDate = new Date(flightDate.getFullYear(),flightDate.getMonth(),flightDate.getDate());
					    		var currentServerTime = login.currentServerTime;
					    		if(flightDate > new Date(currentServerTime.getFullYear(),currentServerTime.getMonth(),currentServerTime.getDate())){
					    			Ext.Msg.show({
					            		title:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),  //提示
										msg:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.isSureDoAfter'),  //航班日期晚于当前日期，确认生成交接单？
										buttons:Ext.Msg.YESNO,
										icon: Ext.Msg.QUESTION, 
										fn : function(btn){
											if(btn == 'no'){
												return;
											}else{
												airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
											}
										}
					    			});
					    		}else{

					    			airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
					    		}
					},
    			    exception : function(response) {
				    		    var json = Ext.decode(response.responseText);
				    		    Ext.Msg.show({
				    				title:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),  //提示
									msg:json.message,  //提示是否进行下一步操作
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
									  if(btn=='no'){
									  	  return;
									  }else{
										  var flightDate = new Date(billInfo.getValues().flightDate);
								    		flightDate = new Date(flightDate.getFullYear(),flightDate.getMonth(),flightDate.getDate());
								    		var currentServerTime = login.currentServerTime;
								    		if(flightDate > new Date(currentServerTime.getFullYear(),currentServerTime.getMonth(),currentServerTime.getDate())){
								    			Ext.Msg.show({
								            		title:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),  //提示
													msg:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.isSureDoAfter'),  //航班日期晚于当前日期，确认生成交接单？
													buttons:Ext.Msg.YESNO,
													icon: Ext.Msg.QUESTION, 
													fn : function(btn){
														if(btn == 'no'){
															return;
														}else{
															airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
														}
													}
								    			});
								    		}else{

								    			airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
								    		}
									  }
									}
				    			});
    			    }
    			});
    	  }else{
    		var flightDate = new Date(billInfo.getValues().flightDate);
    		flightDate = new Date(flightDate.getFullYear(),flightDate.getMonth(),flightDate.getDate());
    		var currentServerTime = login.currentServerTime;
    		if(flightDate > new Date(currentServerTime.getFullYear(),currentServerTime.getMonth(),currentServerTime.getDate())){
    			Ext.Msg.show({
            		title:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),  //提示
					msg:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.isSureDoAfter'),  //航班日期晚于当前日期，确认生成交接单？
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'no'){
							return;
						}else{
							airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
						}
					}
    			});
    		}else{

    			airfreight.airhandoverbill.savaOrUpdateHandover(billInfo,saveBtn);
    		}
    	}
      }
    },{
    	xtype:"button",
		width:90,
    	text:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.button.close'),
    	handler: function() {
    		var detailInfoWindow = airfreight.airhandoverbill.detailInfoWindow;
    		var a = this.up("form").down("button");
    		detailInfoWindow.close();
    	}
    }],
	listeners : {
		render : function(panel,opt){
			var currentDept = airfreight.airhandoverbill.dept;
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			btns[btns.length-2].hide();
			//保存按钮
			if(airfreight.airhandoverbill.isPermission('airfreight/saveOrUpdateAirHandOverBillButton')){
				btns[btns.length-2].show();
			}
		}
	}
});

//定义弹出的编辑窗口
Ext.define('Foss.airfreight.airhandoverbill.editDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	closable:true,
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	editAirHandOverBillForm : null,
	getEditAirHandOverBillForm: function(){
		if(this.editAirHandOverBillForm==null){
			this.editAirHandOverBillForm = Ext.create('Foss.airfreight.airhandoverbill.editAirHandOverBillForm');
		}
		return this.editAirHandOverBillForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditAirHandOverBillForm()
				];
		me.callParent([cfg]);
	}
});



//打印方法
airfreight.print = function (printType,printWeight,airHandoverbillId,deptCode){
	if(printType==1){
		do_printpreview('airHandoverbillNH',{
			'printWeight' : printWeight,
			'deptCode' : deptCode,
			'airHandoverbillId' : airHandoverbillId,
			'createUserName' : FossUserContext.getCurrentUser().employee.empName,
			'ids' : airfreight.airhandoverbill.printIds
		}, ContextPath.TFR_PARTIALLINE);	
	}
	if(printType==2){
		do_printpreview('airHandoverbillGZ',{
			'printWeight' : printWeight,
			'deptCode' : deptCode,
			'airHandoverbillId' : airHandoverbillId,
			'createUserName' : FossUserContext.getCurrentUser().employee.empName,
			'ids' : airfreight.airhandoverbill.printIds
		}, ContextPath.TFR_PARTIALLINE);
	}
	if(printType==3){
		do_printpreview('airHandoverbillGH',{
			'printWeight' : printWeight,
			'deptCode' : deptCode,
			'airHandoverbillId' : airHandoverbillId,
			'createUserName' : FossUserContext.getCurrentUser().employee.empName,
			'ids' : airfreight.airhandoverbill.printIds
		}, ContextPath.TFR_PARTIALLINE);
	}
	
}

//定义选择打印方式的窗口
Ext.define('Foss.airfreight.airhandoverbill.choosePrintTypeWindow',{
	extend:'Ext.window.Window',
	title: airfreight.airhandoverbill.i18n('foss.airfreight.choosePrintTypeWindow.title'),  //打印选项
	modal:true,
	width: 300,
	height: 170,
	layout: 'auto',
	closeAction:'hide',
	items: [
        	Ext.create('Ext.form.Panel',{
        		items: [{
					xtype: 'radiogroup',
					layout:'column',
					width:220,
					margin : '10 10 10 10', 
    		        items: [{
    		        	boxLabel  : airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.NH'),  //南航
    					name      : 'checkstatus',
    					checked : true,
    					width:70,
    					inputValue: '1'
    		        },{
    					boxLabel  : airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.GZ'),  //广州
    					name      : 'checkstatus',
    					width:70,
    					inputValue: '2'
    				},{
    					boxLabel  : airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.SZ'),  //深圳
    					name      : 'checkstatus',
    					width:70,
    					inputValue: '3'
    				}]
        		},{
        			xtype:'numberfield',
        			minValue: 0,
        			maxValue: 99999,
        			maxLength:8,
        			allowDecimals:false,
        			fieldLabel:airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.printWeight'),  //指定打印重量
        			name:'printWeight'
        		},{
        			dockedItems: [{
        		        xtype: 'toolbar',
        		        dock: 'bottom',
        		        items: [{
        		        	xtype: 'container',
        		        	margin: '0 0 0 70'
        		        },{
        		            text: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.confirm'),  //确认
        		            handler:function(){
        		            	var form = this.up('form').getForm();
        		            	if(form.isValid()){
            		    			var airHandoverbillId = airfreight.airHandOverBillId;
            		            	if(airHandoverbillId == null || airHandoverbillId == ''){
            		            		Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.mustSave'));  //提示未保存不能打印
            		            		return null;
            		            	}
            		            	var formValues = form.getValues();
            		            	var radioValue = formValues.checkstatus;
            		            	var printWeight = formValues.printWeight;
            		            	var deptCode = airfreight.airhandoverbill.deptCode; //当前部门
            		            	if(radioValue==null || radioValue==''){
            		            		Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.chooseTemplate'));  //提示选择打印模板
            		            	}else if (radioValue =='1'){	//打印深圳空运总调正单交接单(南航)
            		            		airfreight.print('1',printWeight,airHandoverbillId,deptCode);
            		            	}else if(radioValue == '2'){	//打印广州空运总调正单交接单
            		            		airfreight.print('2',printWeight,airHandoverbillId,deptCode);
            		            	}else{	//打印深圳空运总调正单交接单(国航)
            		            		airfreight.print('3',printWeight,airHandoverbillId,deptCode);
            		            	}
        		            	}
        		            }
        		        },{
        		        	xtype: 'container',
        		        	margin: '0 0 0 10'
        		        },{
        		        	text: airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.label.cancel'),  //取消
        		            handler:function(){
        		            	this.up('window').hide();
        		            }
        		        }]
        		    }]
        		}]
        	})
		]
});







airfreight.airhandoverbill.savaOrUpdateHandover=function(billInfo,saveBtn){
	var billValues = billInfo.getValues();  //获取表单上的value
	var formRecord = saveBtn.up('form').getForm().getRecord();  //获取当前form上的record
	//将更改过的表单值赋给record里面相应的属性
	var flightDate = billValues.flightDate;
	var takeOffTime = billValues.takeOffTime;
	formRecord.data.flightDate = "";
	formRecord.data.takeOffTime = "";
	formRecord.data.flightNo = billValues.flightNo;
	formRecord.data.bookingNo = billValues.bookingNo;
	formRecord.data.spaceWeight = billValues.spaceWeight;
	formRecord.data.handoverOrg = billValues.handoverOrg;
	formRecord.data.handoverEmp = billValues.handoverEmp;
	formRecord.data.notes = billValues.notes;
	formRecord.data.airHandoverNo = billValues.airHandoverNo;
	formRecord.data.airLevel = billValues.airLevel;
	var detailResultGrid = airfreight.airhandoverbill.detailInfoResult.getStore().data.items;
	var records = new Array();
	for(var i = 0 ; i < detailResultGrid.length ; i++){
		records.push(detailResultGrid[i].data);
	}
	var isStock = false;
	if(detailResultGrid.length > 0){
		isStock = true;
	}
	var nextTfrOrg=billInfo.findField('nextTfrOrg').getValue();
	
	var airHandoverType=billInfo.findField('addAirHandOverType').getValue();
	
	var isExpress=false;
	//判断是否是快递 
	if(airHandoverType=='PACKAGE_HANDOVER'){
	    isExpress=true;
	}
	//将formRecord和records传入dto
	var array = {airHandOverBillVO:{airHandOverBillDto:{airWayBillNos:airfreight.airWayBillNos,flightDate:billValues.flightDate,flightNo:billValues.flightNo,airHandOverBillEntity:formRecord.data,airHandOverBillDetailList:records,flightDate:flightDate,takeOffTime:takeOffTime,airLevel:billValues.airLevel,airHandOverType:billValues.addAirHandOverType,expressArriveCode:nextTfrOrg}}}; 
	//this.setDisabled(true);
	//Ajax请求后台的新增action
	Ext.Ajax.request({
		url : airfreight.realPath('saveOrUpdateAirHandOverBill.action'),
		jsonData:array,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			airfreight.airhandoverbill.resultPanel.store.load();
			airfreight.operateStatus = "0";
			var id = json.airHandOverBillVO.airHandOverBillDto.id;
			var orgCode = json.airHandOverBillVO.airHandOverBillDto.orgCode;
			var airHandoverNo = json.airHandOverBillVO.airHandOverBillDto.airHandoverNo;
			var airWaybillStockState = json.airHandOverBillVO.airHandOverBillDto.airWaybillStockState;
			//将返回的ID存入record，标记此条记录已经新增过，再次点击不会新增，只会更新
			formRecord.data.id = id;
			formRecord.data.flightDate = flightDate;
			formRecord.data.takeOffTime = takeOffTime;
			airfreight.airHandOverBillId = formRecord.data.id;
			Ext.ux.Toast.msg(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'), airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.saveSuccess'), 'ok', 1000);   //提示保存成功
			
			//保存成功后自动出库
			if(isStock){
				var airHandOverBillDtos = new Array();
				var airHandOverBillDto = {id:id,orgCode:orgCode,airWaybillStockState:airWaybillStockState,airHandoverNo:airHandoverNo};
				airHandOverBillDtos.push(airHandOverBillDto);
				//将需要的参数传入vo中的dto中的属性：交接单id，交接部门，交接状态
				var outStockArray = {airHandOverBillVO:{airHandOverBillDtos:airHandOverBillDtos}};
				Ext.Ajax.request({
					url : airfreight.realPath('outStockAirHandOverBill.action'),
	    			jsonData:outStockArray,
	    			success : function(response){
	    				var json = Ext.decode(response.responseText);
	    				//操作成功后重新load页面
	    				airfreight.airhandoverbill.resultPanel.store.load();
	    				Ext.ux.Toast.msg(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'), airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.outStockSuccess'), 'ok', 1000);  //提示出库成功
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.msg.message'),json.message);
	    			}
				});
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.Msg.alert(airfreight.airhandoverbill.i18n('foss.airfreight.airhandovebill.tip.failure'),json.message);
		}
	});
}


airfreight.airhandoverbill.dept = '';
airfreight.airhandoverbill.deptCode = '';
airfreight.airhandoverbill.deptName = '';


Ext.onReady(function() {
	Ext.QuickTips.init();
	airfreight.airhandoverbill.addPanel = null;
	
	airfreight.airhandoverbill.detailInfoWindow = Ext.create('Foss.airfreight.airhandoverbill.editDetailInfoWindows');
	
	airfreight.airhandoverbill.queryPanel = Ext.create('Foss.airfreight.airhandoverbill.handOverQuery');
	airfreight.airhandoverbill.managementPanel = Ext.create('Foss.airfreight.airhandoverbill.handOverManagement');
	airfreight.airhandoverbill.resultPanel = Ext.create('Foss.airfreight.airhandoverbill.handOverResult');
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airHandOverBillIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [airfreight.airhandoverbill.queryPanel, airfreight.airhandoverbill.managementPanel, airfreight.airhandoverbill.resultPanel],
		renderTo: 'T_airfreight-airHandOverBillIndex-body'
	});
});
