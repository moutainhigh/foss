//打印查询Model
Ext.define('Foss.partialline.printagentwaybill.model.printAgentWaybillModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'handoverNo', type: 'string'},//交接单号
        {name: 'agentWaybillNo', type: 'string'},	//代理单号
        {name: 'waybillNo',  type: 'string'}, 	//运单号
        {name: 'serialNo',  type: 'string'}, 	//流水号
        {name: 'receiverName',   type: 'string'},	//收货人
        {name: 'receiverTel', type: 'string'},		//电话
        {name: 'receiverAddr', type: 'string'},		//地址
        {name: 'goodsName', type: 'string'},	//货物名称
        //{name: 'goodsQty', type: 'int'},		//件数
        {name: 'loadScanTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//装车扫描时间
        {name: 'printer', type: 'string'},	//跟踪人
        {name: 'printTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//跟踪时间
        //{name: 'printCount', type: 'int'} //打印次数
		{name: 'agentCmpanyName', type: 'string'},	//代理公司名称
		{name: 'agentCompanyCode', type: 'string'},//代理公司编码
		{name: 'status', type: 'string'},	//状态
		{name: 'ispush',type:'string'} //订阅状态
    ]
});


//打印查询数据源
Ext.define('Foss.partialline.printagentwaybill.store.printAgentWaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.printagentwaybill.model.printAgentWaybillModel',
	pageSize:200,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('queryWaybills.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.list',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.printagentwaybill.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				
				Ext.apply(operation, {
					params : {
						'vo.handOverBillNos' : queryParams.handoverNo,
						'vo.waybillNos' : queryParams.waybillNo,
						'vo.dto.beginHandOverTime' : queryParams.beginHandOverTime,
						'vo.dto.endHandOverTime' : queryParams.endHandOverTime,
						'vo.dto.beginInStockTime' : queryParams.beginInStockTime,
						'vo.dto.endInStockTime' : queryParams.endInStockTime
					}
				});	
			}
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
		}
	}
});

//查询form表单
Ext.define('Foss.partialline.printagentwaybill.form.searchForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss.partialline.printagentwaybill.form.searchForm.id',
	title : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.searchCondition.title'), //查询,
	layout:'column',
	frame: true,
//	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
//	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
//		anchor: '90%',
		labelWidth:60
	},
	items: [{
		fieldLabel: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.label'),//运单号
		columnWidth :.3,
		name : 'waybillNo',
		labelWidth : 60,
		xtype : 'textarea',
		emptyText : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation')
	},{
		fieldLabel: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handoverNo.label'),//交接单号
		name: 'handoverNo',
		xtype: 'textarea',
		labelWidth : 60,
		allowBlank:true,
		columnWidth:.3,
		emptyText : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handOverNo.valitation')
	},{//入库时间
		xtype : 'rangeDateField',
		fieldLabel : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.inStockTime.label'),//入库时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.printagentwaybill_inStockTime_RangeDateField_ID',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginInStockTime',
		fromValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), "00", "00", "00"),
				'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), "23", "59", "59"),
				'Y-m-d H:i:s'),
		toName : 'endInStockTime'
//		allowBlank : false,
//		disallowBlank : true		
	},{
		xtype : 'rangeDateField',
		fieldLabel : partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handOverTime.label'),//交接时间
		columnWidth : .4,
		fieldId : 'Foss_partialline_model.printagentwaybill_handOverTime_RangeDateField_ID',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginHandOverTime',
		fromValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), 0, 0, 0),
				'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), 23, 59, 59),
				'Y-m-d H:i:s'),
		toName : 'endHandOverTime'
//		allowBlank : false,
//		disallowBlank : true	
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.reset'),//重置
			handler: function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('beginInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
				form.findField('endInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
				form.findField('beginHandOverTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'00','00','00'), 'Y-m-d H:i:s'));
				form.findField('endHandOverTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));
					
			}
		},{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		},
		
		
		{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.select'),//查询
			cls:'yellow_button',
			handler: function() {
				var searchParms = this.up('form').getForm().getValues();
				if(!this.up('form').getForm().isValid()){
					return;
				}
				
				if(Ext.isEmpty(searchParms.waybillNo) 
						&& Ext.isEmpty(searchParms.handoverNo)
						&& (Ext.isEmpty(searchParms.beginInStockTime) && Ext.isEmpty(searchParms.endInStockTime))
						&& (Ext.isEmpty(searchParms.beginHandOverTime) && Ext.isEmpty(searchParms.endHandOverTime))) {
					Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.condition'), 'error', 3000); 
					return;
				}
				
				// 验证运单号输入的行数
				if (!Ext.isEmpty(searchParms.waybillNo)) {
					var arrayWaybillNo = searchParms.waybillNo.split('\n');
					if (arrayWaybillNo.length > 50) {
						Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation'), 'error', 3000); // 
						return;	
					}
					for (var i = 0; i < arrayWaybillNo.length; i++) {
						if (Ext.isEmpty(arrayWaybillNo[i])) {
							Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation'), 'error', 3000); 
							return;	
						}
					}
				}
				//验证交接单号输入行数
				if (!Ext.isEmpty(searchParms.handoverNo)) {
					var arrayHandOverNo = searchParms.handoverNo.split('\n');
					if (arrayHandOverNo.length > 50) {
						Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handOverNo.valitation'), 'error', 3000); 
						return;	
					}
					for (var i = 0; i < arrayHandOverNo.length; i++) {
						if (Ext.isEmpty(arrayHandOverNo[i])) {
							Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handOverNo.valitation'), 'error', 3000); // 运单号不能录入空回车
							return;	
						}
					}
				}
				
				if((!Ext.isEmpty(searchParms.beginInStockTime) && Ext.isEmpty(searchParms.endInStockTime)) 
						|| (Ext.isEmpty(searchParms.beginInStockTime) && !Ext.isEmpty(searchParms.endInStockTime))) {
							Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.time.valitation'), 'error', 3000); 
							return;
						}
						
				if((!Ext.isEmpty(searchParms.beginHandOverTime) && Ext.isEmpty(searchParms.endHandOverTime)) 
						|| (Ext.isEmpty(searchParms.beginHandOverTime) && !Ext.isEmpty(searchParms.endHandOverTime))) {
							Ext.ux.Toast.msg(partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.time.valitation'), 'error', 3000); 
							return;
						}
				
				if(this.up('form').getForm().isValid( )){
					partialline.printagentwaybill.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

//查询结果
Ext.define('Foss.partialline.printagentwaybill.grid.searchResultGrid', {
	extend:'Ext.grid.Panel',
	id: 'Foss.partialline.printagentwaybill.grid.searchResultGrid.id',
	title : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.waybillinfo.title'), //运单信息,
	height: 500,
	autoScroll: true,
	columnLines: true,
	frame: true,
	forceFit: true,
	enableColumnHide: false,
    //sortableColumns: false,
    collapsible: true,
    animCollapse: true,
    emptyText : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.empty'), //查询结果为空,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns: [{
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.agentWaybillNo.label'),//代理单号
		dataIndex: 'agentWaybillNo',
		width : 80,
		flex: 1.3,
		field:{
			xtype: 'textfield',
			editable:true,
			allowBlank:false,
			//regex : /^([0-9]{0,30}\n?)+$/i,
			regex :/^[A-Za-z0-9]+$/,
			maxLength:30,
			hideTrigger: true
		}
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.label'), //运单号
		dataIndex: 'waybillNo',
		width : 80,
		flex: 1.1
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.serialNo.label'), //流水号
		dataIndex: 'serialNo',
		width : 45,
		flex: 1.1
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.receiver.label'), //收货联系人
		dataIndex: 'receiverName',
		width : 40,
		flex: 0.6
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.phone.label'), //收货人电话
		dataIndex: 'receiverTel',
		width : 65,
		flex: 1.2
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.address.label'), //收货人地址
		dataIndex: 'receiverAddr',
		width : 125,
		flex: 2.9
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.goodsName.label'), //货物名称
		dataIndex: 'goodsName',
		width : 50,
		flex: 1.0
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.loadScanTime.label'), //扫描时间
		dataIndex: 'loadScanTime',
		//fixed: true,
		width : 90,
		flex: 1.8
	},/*{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.pieces.label'), //件数
		dataIndex: 'goodsQty',
		//fixed: true,
		width : 10,
		flex: 0.2
	},*/{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.printer.label'), //跟踪人
		dataIndex: 'printer',
		//fixed: true,
		width : 45,
		flex: 0.6
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.printTime.label'), //跟踪时间
		dataIndex: 'printTime',
		//fixed: true,
		width : 95,
		flex: 1.8
	},/*{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.printCount.label'), //打印次数
		dataIndex: 'printCount',
		//fixed: true,
		width : 15,
		flex: 0.3
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.agentCmpanyName.label'), //代理公司名称
		dataIndex: 'agentCmpanyName',
		//fixed: true,
		width :70,
		flex: 1.8
	},*/{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.status.label'), //状态
		dataIndex: 'status',
		//fixed: true,
		width : 50,
		flex: 1.8
	},{ 
		header: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.orderStatus.label'), //状态
		dataIndex: 'ispush',
		//fixed: true,
		width : 50,
		flex: 1.8
	}],
	printWindow : null,
	getPrintWindow : function() {
		var me = this;
		if (this.printWindow == null) {
			me.printWindow = Ext.create('Foss.partialline.printagentwaybill.win.PrintWindow', {grid:me});
		}
		return me.printWindow;
	},
	editor: null,
    getEditor: function(){
    	if(this.editor==null){
    		this.editor = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			});
    	}
    	return this.editor;
    },
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.partialline.printagentwaybill.store.printAgentWaybillStore');
		me.plugins = [
		  			me.getEditor()
		  		];
		me.tbar = [{
				xtype : 'button',
				text : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.bind'), //绑定,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				handler : function() {
					//alert("打印");
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var agentWaybillNo = selectWaybill[i].data.agentWaybillNo;
						var status = selectWaybill[i].data.status;
						var ispush = selectWaybill[i].data.ispush;
						if (Ext.isEmpty(agentWaybillNo)) {
							Ext.ux.Toast.msg("提示信息",
									selectWaybill[i].data.waybillNo
											+ "运单未输入代理单号，不能绑定！");
							return;
							break;
						}
						if(status == '已绑定') {
							Ext.ux.Toast.msg("提示信息", "运单号："+ selectWaybill[i].data.waybillNo + "流水号：" + selectWaybill[i].data.serialNo + "已绑定，请先作废再绑定！");
							return;
						}
						//只有当订阅状态为“未订阅”、“已退订”时，才可对绑定状态进行更改
						if(ispush != '未订阅'&&ispush !='已退订'){
							Ext.ux.Toast.msg("提示信息", "运单号："+ selectWaybill[i].data.waybillNo + "流水号：" + selectWaybill[i].data.serialNo +ispush+ "，不能绑定！");
							return;
						}
					}
					bind(mygrid);
					//mygrid.getPrintWindow().show();
					//mygrid.getPrintWindow().setPrintType('preview');
					//Ext.create('Foss.partialline.printagentwaybill.win.PrintWindow', me).show();
				}
			}, {
				xtype : 'button',
				text : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.invalid'), //作废,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				margin : '0 10 5 10',
				handler : function() {
					//alert("打印");
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					var currentDeptCode = FossUserContext.getCurrentDept().code;
					var currentDeptName = FossUserContext.getCurrentDept().name;
					var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
					var currentUserName = FossUserContext.getCurrentUser().employee.empName;
					var recordList = new Array();
					var waybillNoList = new Array();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var record = selectWaybill[i];
						var status = record.data.status;
						var ispush = record.data.ispush;
						if(status != '已绑定') {
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo + "未绑定，不能作废！");
							return;
						} 
						//只有当订阅状态为“未订阅”、“已退订”时，才可对绑定状态进行更改
						if(ispush != '未订阅'&&ispush !='已退订'){
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo +ispush+ "，不能作废！");
							return;
						}
						record.data.operatorCode = currentUserCode;
						record.data.operatorName = currentUserName;
						record.data.orgCode = currentDeptCode;
						record.data.orgName = currentDeptName;
						
						recordList.push(record.data);
						waybillNoList.push(record.data.waybillNo);
					}
					
					var myMask = new Ext.LoadMask(this.up('gridpanel'), {
						msg:"作废中，请稍候..."
					});
					var jsonParam = {vo:{listRecordEntities:recordList}};
					myMask.show();
					Ext.Ajax.request({
									url:partialline.realPath('invalidLdpBindRecords.action'),
									jsonData: jsonParam,
									success: function(response){
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg("提示", "作废成功");
										partialline.printagentwaybill.pagingBar.moveFirst();
									},
									exception : function(response) {
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert("提示", result.message);
									}
								});						
								
					/*//校验是否存在有效的落地配外发单
					Ext.Ajax.request({
						url:partialline.realPath('hasLdpExternalBill.action'),
						jsonData: {vo:{waybillNoList:waybillNoList}},
						success: function(response){
							var result = Ext.decode(response.responseText);
							if(result.vo.waybillNos != "") {
								Ext.ux.Toast.msg("提示", "运单号："+ result.vo.waybillNos + "存在有效的快递代理外发单，不能作废！");
								return;
							} else {
								var jsonParam = {vo:{listRecordEntities:recordList}};
								
								myMask.show();
								
								Ext.Ajax.request({
									url:partialline.realPath('invalidLdpBindRecords.action'),
									jsonData: jsonParam,
									success: function(response){
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg("提示", "作废成功");
										partialline.printagentwaybill.pagingBar.moveFirst();
									},
									exception : function(response) {
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert("提示", result.message);
									}
								});
							}
						},
						exception : function(response) {
							myMask.hide();
							var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert("提示", result.message);
							return;
						}
					});*/
					
				}
		},
		{
				xtype : 'button',
				text : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.trackOrder'), //轨迹订阅,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
			//	margin : '0 10 5 10',
				handler : function() {
					
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					var currentDeptCode = FossUserContext.getCurrentDept().code;
					var currentDeptName = FossUserContext.getCurrentDept().name;
					var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
					var currentUserName = FossUserContext.getCurrentUser().employee.empName;
					
					var recordList = new Array();
					var waybillNoList = new Array();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var record = selectWaybill[i];
						var status = record.data.status;
						if(status != '已绑定') {
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo + "未绑定，不能订阅！");
							return;
						} 
						//"未订阅","未退订"时可进行轨迹订阅。
						var ispush = record.data.ispush;
						if(ispush == '已订阅'){
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo + "，已订阅，无需再订阅！");
							return;
						}
						record.data.operatorCode = currentUserCode;
						record.data.operatorName = currentUserName;
						record.data.orgCode = currentDeptCode;
						record.data.orgName = currentDeptName;
						
						recordList.push(record.data);
						waybillNoList.push(record.data.waybillNo);
					}
					var myMask = new Ext.LoadMask(this.up('gridpanel'), {
						msg:"订阅中，请稍候..."
					});
					var jsonParam = {vo:{listRecordEntities:recordList}};
					myMask.show();
					Ext.Ajax.request({
									url:partialline.realPath('trackOrder.action'),
									jsonData: jsonParam,
									success: function(response){
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg("提示", "订阅成功");
										partialline.printagentwaybill.pagingBar.moveFirst();
									},
									exception : function(response) {
										myMask.hide();
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert("提示", result.message);
									},		
									failure : function() {
									myMask.hide();
								 	Ext.MessageBox.alert("提示", "订阅失败！");
								 	myMask.hide();
  								}
								});						
				}
		},{ //停止订阅
				xtype : 'button',
				text : partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.stopOrder'), //停止订阅,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
			//	margin : '0 10 5 10',
				style:'margin-left:15px',
				handler : function() {
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					var currentDeptCode = FossUserContext.getCurrentDept().code;
					var currentDeptName = FossUserContext.getCurrentDept().name;
					var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
					var currentUserName = FossUserContext.getCurrentUser().employee.empName;
					var recordList = new Array();
					var waybillNoList = new Array();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var record = selectWaybill[i];
						var status = record.data.status;
						if(status != '已绑定') {
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo + "未绑定，不能停止订阅！");
							return;
						} 
						//"已退订"的无需再退订
						var ispush = record.data.ispush;
						if(ispush == '已退订' || ispush == '未订阅'){
							Ext.ux.Toast.msg("提示信息", "运单号："+ record.data.waybillNo + "流水号：" + record.data.serialNo + ispush +"，不能停止订阅！");
							return;
						}
						record.data.operatorCode = currentUserCode;
						record.data.operatorName = currentUserName;
						record.data.orgCode = currentDeptCode;
						record.data.orgName = currentDeptName;
						recordList.push(record.data);
						waybillNoList.push(record.data.waybillNo);
					}
					Ext.MessageBox.confirm('提示', '是否停止订阅？', function(button,text ){ 
						 if( button == 'yes'){  
						 	var myMask = new Ext.LoadMask(mygrid, {
								msg:"停止订阅中，请稍候..."
							});
							var jsonParam = {vo:{listRecordEntities:recordList}};
							myMask.show();
							Ext.Ajax.request({
								url:partialline.realPath('stopTrackOrder.action'),
								jsonData: jsonParam,
								success: function(response){
									myMask.hide();
									var result = Ext.decode(response.responseText);
									Ext.ux.Toast.msg("提示", "停止订阅成功！");
									partialline.printagentwaybill.pagingBar.moveFirst();
								},
								exception : function(response) {
									myMask.hide();
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert("提示", result.message);
								},
								failure : function() {
									myMask.hide();
								 	Ext.MessageBox.alert("提示", "停止订阅失败！");
								 	myMask.hide();
  								}
							});		
           				}
					}); 
				}
		},
		 "->", 
		{
			columnWidth:.08,
		//	style:'margin-left:550px',
			xtype : 'button',
			text: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.import'),//导入
			//cls:'yellow_button',
			handler: function() {
				var importWindow = Ext.getCmp('Foss_partialline_printagentwaybill_window_printAgentWaybill_ID');
				if(importWindow == null){
					importWindow=Ext.create('Foss.partialline.printagentwaybill.window.printAgentWaybillStandard');								
				}
				    importWindow.center().show();
				}
		},	
		{
			columnWidth:.12,
			style:'margin-left:15px',
			xtype : 'button',
			text: partialline.printagentwaybill.i18n('Foss.printagentwaybill.grid.searchWaybill.button.downImportModel'),//下载导入模板
			//cls:'yellow_button',
			handler: function() {
				filePath();
				}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['100', 100],['200', 200],['500', 500]]
			})
		});
		partialline.printagentwaybill.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig: {
		enableTextSelection: true
    }
});

//定义打印模版window
Ext.define('Foss.partialline.printagentwaybill.win.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '代理公司选择',//代理公司选择
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	printType: null,
	handOverBillNos : null,
	vehicleNo : null,
	grid : null,
	setPrintType : function(printType) {
		this.printType = printType;
	},
	items : [{
		fieldLabel : '代理公司',//代理公司
		name : 'printTemplate',
		columnWidth: 1,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    defaults: {
			margin: '10 5 10 5'
		},
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ZTO", "value":"中通快递"},
	            {"key":"UC", "value":"优速快递"},
	            {"key":"YUNDA", "value":"韵达快递"},
	            {"key":"STO", "value":"申通快递"},
	            {"key":"YTO", "value":"圆通快递"},
	            {"key":"TTK", "value":"天天快递"},
	            {"key":"SF", "value":"顺丰速运"},
	            {"key":"EMS", "value":"EMS"},
	            {"key":"BEST", "value":"百世汇通"},

	            {"key":"ZJS","value":"宅急送"},      
	            {"key":"QF","value":"全峰快递"}, 
	            {"key":"UNITOP","value":"全一快递"}, 
	            {"key":"ANYTIME","value":"全日通快递"}, 
	            {"key":"QC","value":"全晨快递"}, 
	            {"key":"SUER","value":"速尔快递"}, 
	            {"key":"GTO","value":"国通快递"}, 
	            {"key":"HMJ","value":"黄马甲快递"}, 
	            {"key":"RUFENGDA","value":"如风达快递"}, 
	            {"key":"YUTONG","value":"运通快递"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : '确认',//确认
		handler : function(){
			
			var win = this.up('window'),
				parentGrid = win.grid;
			//中通
			bind(parentGrid);
		}
	}]
});

function bind(parentGrid,win) {
	 win.hide();
	var selectWaybill = parentGrid.getSelectionModel().getSelection();
	var currentDeptCode = FossUserContext.getCurrentDept().code;
	var currentDeptName = FossUserContext.getCurrentDept().name;
	var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
	var currentUserName = FossUserContext.getCurrentUser().employee.empName;
	var printTemplate = win.items.items[0].getValue();
	var recordList = new Array();
	for (var i = 0; i < selectWaybill.length; i++) {
		var record = selectWaybill[i];
		record.data.agentCompanyCode = printTemplate;
		record.data.operatorCode = currentUserCode;
		record.data.operatorName = currentUserName;
		record.data.orgCode = currentDeptCode;
		record.data.orgName = currentDeptName;
		
		recordList.push(record.data);
	}
	
	var jsonParam = {vo:{listRecordEntities:recordList}};
	var myMask = new Ext.LoadMask(parentGrid, {
		msg:"绑定中，请稍候..."
	});
	myMask.show();
	//保存绑定记录
	Ext.Ajax.request({
		url:partialline.realPath('addLdpBindRecords.action'),
		jsonData: jsonParam,
		success: function(response){
			myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg("提示", "绑定成功");
			partialline.printagentwaybill.pagingBar.moveFirst();
		},
		exception : function(response) {
			myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert("提示", result.message);
			
		},
		failure:function(response){
			myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert("提示","服务端连接异常:"+result);
		}
		
	});
};
//add by 257220 
//执行绑定操作
function bind(grid){
	//获取绑定公司简称
	//获取字段
	var selectWaybill = grid.getSelectionModel().getSelection();
	var currentDeptCode = FossUserContext.getCurrentDept().code;
	var currentDeptName = FossUserContext.getCurrentDept().name;
	var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
	var currentUserName = FossUserContext.getCurrentUser().employee.empName;
	//保存绑定记录
	var recordList = new Array();
	for (var i = 0; i < selectWaybill.length; i++) {
		var record = selectWaybill[i];
		record.data.operatorCode = currentUserCode;
		record.data.operatorName = currentUserName;
		record.data.orgCode = currentDeptCode;
		record.data.orgName = currentDeptName;
		recordList.push(record.data);
	}
	
	var jsonParam = {vo:{listRecordEntities:recordList}};
	
	var myMask = new Ext.LoadMask(grid, {
		msg:"绑定中，请稍候..."
	});
	myMask.show();
	Ext.Ajax.request({
		url:partialline.realPath('addLdpBindRecords.action'),
		jsonData: jsonParam,
		success: function(response){
			myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg("提示", "绑定成功");
			partialline.printagentwaybill.pagingBar.moveFirst();
		},
		exception : function(response) {
			myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert("提示", result.message);
		},
		failure:function(response){
			myMask.hide();
		}
	});
}
function print(parentGrid,win){
    win.hide();
	var selectWaybill = parentGrid.getSelectionModel().getSelection();
	var map = new Ext.util.HashMap();
	var waybillNos = '';
	var agentWaybillNos = '';
	var currentDeptCode = FossUserContext.getCurrentDept().code;
	var currentDeptName = FossUserContext.getCurrentDept().name;
	var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
	var currentUserName = FossUserContext.getCurrentUser().employee.empName;
	for(var i = 0;i<selectWaybill.length;i++){
		if(waybillNos.length == 0) {
			
			waybillNos = selectWaybill[i].data.waybillNo;
		} else {
			waybillNos = waybillNos + "," + selectWaybill[i].data.waybillNo;
		}
	}
	for(var i = 0;i<selectWaybill.length;i++){
		if(agentWaybillNos.length == 0) {
			agentWaybillNos = selectWaybill[i].data.agentWaybillNo;
		} else {
			agentWaybillNos = agentWaybillNos + "," + selectWaybill[i].data.agentWaybillNo;
		}
	}
	var printTemplate = win.items.items[0].getValue();
	//打印预览
	if(win.printType == 'preview') {
		if(printTemplate == 'STO') {
			do_printpreview('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'YTO') {
			do_printpreview('ytoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'ZTO') {
			do_printpreview('ztoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'YUNDA') {
			do_printpreview('yundaagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'TTK') {
			do_printpreview('ttkagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'UC') {
			do_printpreview('ucagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'SF') {
			do_printpreview('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'EMS') {
			do_printpreview('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'BEST') {
			do_printpreview('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}
	} else { //打印
		if(printTemplate == 'STO') {
			do_print_noimg('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'YTO') {
			do_print_noimg('ytoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'ZTO') {
			do_print_noimg('ztoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'YUNDA') {
			do_print_noimg('yundaagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'TTK') {
			do_print_noimg('ttkagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		} else if(printTemplate == 'UC') {
			do_print_noimg('ucagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'SF') {
			do_print_noimg('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'EMS') {
			do_print_noimg('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}else if(printTemplate == 'BEST') {
			do_print_noimg('stoagentwaybill',{
				"waybillNos":waybillNos,
				"agentWaybillNos": agentWaybillNos,
				"currentDeptCode":currentDeptCode,
				"currentDeptName":currentDeptName,
				"currentUserCode":currentUserCode,
				"currentUserName":currentUserName,
				"printTemplate":printTemplate
			}, ContextPath.TFR_PARTIALLINE);
		}
	}
	

};
/**********************************************start*************************************************/
//导入表单
Ext.define('Foss.partialline.printagentwaybill.form.printAgentWaybillStandard',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:90
		},
		standardSubmit: true,
		items : [{
			        xtype: 'filefield',
			        name: 'uploadFile',
			        readOnly:false,
			        buttonOnly:false,
			        fieldLabel: '导入文件',//'导入文件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: '浏览',//'浏览',
			      	columnWidth:.85
			    },{
					xtype : 'button',
					columnWidth:.15,
					cls:'cleanBtn',
					text: '清除',//'清除',
					handler: function() {
						this.up('form').getForm().findField('uploadFile').reset();						
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
						text:'取消',//'取消',
						handler: function() {
							
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: '导入',//'导入',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					            	var myMask = new Ext.LoadMask(this.up('form'),  {msg:'正在导入，请稍等...'});//"正在导入，请稍等..."
		 							    myMask.show();
					                form.submit({
					                    url: partialline.realPath('importPrintAgentWaybillInfo.action'),
					                    success: function(form, action) {
					                    	myMask.hide();
					                    	var json =action.result;
					                    	if(json.vo!=null){
					                    		  Ext.MessageBox.alert(partialline.printagentwaybill.i18n('提示'),'导入成功'+json.vo.importTotalCount+'条');
									              partialline.printagentwaybill.pagingBar.moveFirst();
					                    	}
					                    },
										exception : function(form, action) {
											myMask.hide();
					        				json=action.result;
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}
					        				Ext.create('Ext.window.Window', {
					        				    title:'提示',
					        				    height: 200,
					        				    width: 400,
					        				    layout: 'fit',
					        				    items: {  
					        				        xtype: 'form',
					        				        border: false,
					        				        items:[
														{
															xtype : 'textarea',
															fieldLabel: '',
															height: 130,
								        				    width: 380,
								        				    autoScroll:true,
								        				    readOnly:true,
															name: 'message',
															value:msg
														}
					        				              ]
					        				    }
					        				}).show();
										},
					        			failure: function(form, action){
					        				myMask.hide();
					        				json=action.result;					        				
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}
					        				alert(msg);
					        				Ext.create('Ext.window.Window', {
					        				    title: '提示',
					        				    height: 200,
					        				    width: 400,
					        				    layout: 'fit',
					        				    items: {  
					        				        xtype: 'form',
					        				        border: false,
					        				        items:[
														{
															xtype : 'textarea',
															fieldLabel: '',
															height: 130,
								        				    width: 380,
								        				    autoScroll:true,
								        				    readOnly:true,
															name: 'message',
															value:msg
														}
					        				              ]
					        				    }
					        				}).show();
					        			}
					                });
					            }
						}
					}]
				    }
				],
		dockedItems: [],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
});

//导入窗口
Ext.define('Foss.partialline.printagentwaybill.window.printAgentWaybillStandard', {
	extend:'Ext.window.Window',
	id:'Foss_partialline_printagentwaybill_window_printAgentWaybill_ID',
	title: '代理面单导入',//'代理面单导入',
	modal:true,
	closeAction:'hide',
	width: 550,
	height:150,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	listeners:{
		hide:function(comp, eOpts){
			this.down('form').getForm().findField('uploadFile').reset();
		}
	},
	items:[Ext.create('Foss.partialline.printagentwaybill.form.printAgentWaybillStandard')]
});
/************************************end***************************************/
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var searchForm=Ext.create('Foss.partialline.printagentwaybill.form.searchForm');
	var searchResult=Ext.create('Foss.partialline.printagentwaybill.grid.searchResultGrid');
	partialline.printagentwaybill.queryForm=searchForm;
	partialline.printagentwaybill.searchResultGrid=searchResult;
	//显示打印查询界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-printagentwaybillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchForm,searchResult],
		renderTo: 'T_partialline-printagentwaybillIndex-body'
	});	
	searchResult.getStore().load();
});