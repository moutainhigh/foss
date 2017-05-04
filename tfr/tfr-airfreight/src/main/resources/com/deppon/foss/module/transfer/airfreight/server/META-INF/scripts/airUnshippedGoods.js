//定义航空拉货信息的model
Ext.define('Foss.airfreight.airUnshippedGoodsModel',{
	extend:'Ext.data.Model',
	fields:[{name:'unshippedTime', type:'string',
				convert : function(value){
					if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d');
					}else{
						return null;
		 			}
				}
			},
	        {name:'arrvRegionName', type:'string'},
	        {name:'billingWeight', type:'string'},
	        {name:'billNo', type:'string'},
	        {name:'airAssembleType', type:'string'},
	        {name:'flightNo', type:'string'},
	        {name:'goodsQtyTotal', type:'string'},
	        {name:'weightTotal', type:'string'},
	        {name:'unshippedGoodsQty', type:'string'},
	        {name:'unshippedType', type:'string'},
	        {name:'receiverName', type:'string'},
	        {name:'receiverName', type:'string'},
	        {name:'reassignFlightNo', type:'string'},
	        {name:'createUserName', type:'string'},
	        {name:'unshippedWeight', type:'string'},
	        {name:'notes', type:'string'},
	        {name:'airAssembleOrgName', type:'string'},
	        {name:'airAgencyName', type:'string'},
	        {name:'airLine', type:'string'},
	        {name:'createdUserName', type:'string'},
	        {name:'noticeTime', type:'string',
	        	convert : function(value){
					if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d');
					}else{
						return null;
		 			}
				}
	        }
	        ]
});

//定义航空拉货信息的store
Ext.define('Foss.airfreight.airUnshippedGoodsStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.airUnshippedGoodsModel',
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryUnshippedGoods.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airUnshippedGoodsVO.airUnshippedGoodsList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts){
			var queryPanel = airfreight.unshippedGoodsIndex.queryPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				if(queryParams.billNoType === 'WAY_BILL_NO'){
					queryParams.airLine = '';
				}
				Ext.apply(operation, {
					params : {
						'airUnshippedGoodsVO.airUnshippedGoodsDto.airAssembleType' : queryParams.airAssembleType,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.unshippedTimeBeginDate' : queryParams.unshippedBeginDate,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.unshippedTimeEndDate' : queryParams.unshippedEndDate,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.billNo' : queryParams.billNo,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.airAssembleOrgCode' : queryParams.airAssembleOrgCode,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.arrvRegionCode' : queryParams.arrvRegionCode,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.flightNo' : queryParams.flightNo,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.airLine' : queryParams.airLine,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.agenctCode' : queryParams.agenctCode,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.billNoType' : queryParams.billNoType
					}
				});	
			}
		}
	}
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

//定义查询拉货信息条件的panel
Ext.define('Foss.airfreight.queryAirUnshippedGoodsPanel',{
	extend:'Ext.form.Panel',
	title: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.queryAirUnshippedGoodsPanel.title'),   //查询条件
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	layout:'column',
	items:[{
		allowBlank:false,
		xtype: 'rangeDateField',
		disallowBlank:true,
		fieldId:'Foss_Airfreight_QueryAirUnshippedGoodsPanel_UnshippedBeginDateId',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedTime'),  //拉货时间
		dateType: 'datetimefield_date97',
		fromName: 'unshippedBeginDate',
		dateRange:31, //时间跨度不能大于一个月
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'unshippedEndDate',
		columnWidth: .4
	},{
		xtype:'combobox',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleType'),  //配载类型
		name:'airAssembleType',
		displayField:'valueName',
		valueField:'valueCode',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore('AIR_ASSEMBLE_TYPE'),
		columnWidth: .2
	},{
		xtype:'combobox',
		name:'billNoType',
		displayField:'valueName',
		valueField:'valueCode',
		value:'AIR_WAY_BILL_NO',
		store:FossDataDictionary.getDataDictionaryStore('BILLNO_TYPE'),
		listeners:{
			change:function(newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
			    if(newValue.value === 'WAY_BILL_NO'){
			    	form.getFields().items[5].hide();
			    }else{
			    	form.getFields().items[5].show();
			    }
			}
		},
		columnWidth: .1
	},{
		name:'billNo',
		columnWidth: .12
	},{
		name:'airLine',
		displayField:'code',
		readOnly:false,
		valueField:'simpleName',
		editable:false,
		store:Ext.create('Foss.airfreight.queryAirlinesStore'),
		xtype:'combobox',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airLine'),  //航空公司
		labelWidth:60,
		columnWidth: .14
	},{
		xtype:'commonairagencycompanyselector',
		name:'agenctCode',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.agenctCode'),  //外发代理
		columnWidth: .2
	},{
		xtype:'dynamicorgcombselector',
		name:'airAssembleOrgCode',
		allowBlank:false,
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleOrgCode'),  //配载部门
		columnWidth: .2
	},{
		xtype:'commoncityselector',
		name:'arrvRegionCode',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.arrvRegionCode'), //目的站
		columnWidth: .2
	},{
	    xtype:'commonflightselector',
		name:'flightNo',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.flightNo'),  //航班号
		columnWidth: .2
	},{
		readOnly:true,
		columnWidth: .2
	},{
		xtype:'button',
		text:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.button.reset'),  //重置
		columnWidth: .1,
		handler: function() {
			var form = this.up('form').getForm();
			form.reset();
			form.findField('unshippedBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
			form.findField('unshippedEndDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
			var cmbAirAssembleOrg = form.findField('airAssembleOrgCode');
			if(airfreight.unshippedGoodsIndex.dept.airDispatch == 'Y'){
				cmbAirAssembleOrg.getStore().load({params:{'commonOrgVo.name' : airfreight.unshippedGoodsIndex.dept.name}});
				cmbAirAssembleOrg.setValue(airfreight.unshippedGoodsIndex.dept.code);
			}
		}
	},{
		readOnly:true,
		columnWidth: .8
	},{
		xtype:'button',
		cls : 'yellow_button',
		text:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.button.search'),  //查询
		columnWidth: .1,
		handler:function(){
			var form = this.up('form').getForm();
			if(form.isValid()){
				airfreight.unshippedGoodsIndex.pagingBar.moveFirst();
			}
		}
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
					airfreight.unshippedGoodsIndex.dept = dept;
					airfreight.unshippedGoodsIndex.deptCode = dept.code;
					airfreight.unshippedGoodsIndex.deptName = dept.name;
					var cmbAirAssembleOrg = panel.getForm().findField('airAssembleOrgCode');
					if(airfreight.unshippedGoodsIndex.dept.airDispatch == 'Y'){
						cmbAirAssembleOrg.getStore().load({params:{'commonOrgVo.name' : airfreight.unshippedGoodsIndex.dept.name}});
						cmbAirAssembleOrg.setValue(airfreight.unshippedGoodsIndex.dept.code);
					}
				}
			});
		}
	}
});

//发送拉货通知
airfreight.unshippedMessage = function(id){
	var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {id : id}}};
	Ext.Ajax.request({
		url:airfreight.realPath('addUnshippedMessage.action'),
		jsonData:array,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.sendMsgSuccess'), 'ok', 1000);  //提示通知成功
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
		}
	});
};

//点击查看根据ID查找数据
airfreight.queryAirUnshippedGoodsById = function(id,rowIndex){
	var detailResultStore = airfreight.unshippedGoodsIndex.billNoGrid.getStore();
	var detailResultSelectModel = airfreight.unshippedGoodsIndex.billNoGrid.getSelectionModel();
	var record = airfreight.unshippedGoodsIndex.resultPanel.getStore().getAt(rowIndex);
	detailResultStore.removeAll();
	var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {id:id,airAssembleType:record.data.unshippedType,billNo:record.data.billNo}}};
	Ext.Ajax.request({
		url:airfreight.realPath('queryAirUnshippedGoodsById.action'),
		jsonData:array,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			record.data = json.airUnshippedGoodsVO.airUnshippedGoodsEntity;
			var airWaybillSerialNoList = json.airUnshippedGoodsVO.airUnshippedGoodsDto.airWaybillSerialNoList;
			
			record.data.unshippedTime = Ext.Date.format(new Date(record.data.unshippedTime),'Y-m-d H:i:s');
			

			var detailInfoWindow = airfreight.unshippedGoodsIndex.detailInfoWindow;
			var editForm = detailInfoWindow.getEditUnshippedGoodsAllForm();
			
			if(record.data.unshippedType === 'AIR_WAY_BILL_NO'){
				record.data.airWaybillNo = record.data.billNo;
			}else{
				record.data.waybillNo = record.data.billNo;
				editForm.getForm().findField('unshippedGoodsQty').setReadOnly(true);
			}

			editForm.getForm().reset();
			editForm.getForm().loadRecord(record);
			detailInfoWindow.show();
			airfreight.unshippedGoodsIndex.addPanel.getForm().findField('airUnshippedType').setReadOnly(true);
			//初始化流水号的值
			if(null != airWaybillSerialNoList){
				for(var i = 0 ; i<airWaybillSerialNoList.length; i++){
					detailRecord= new Ext.data.reader.Json(airWaybillSerialNoList[i]);
					detailResultStore.add(detailRecord);
				}
				//默认勾选
				for(var i = 0 ; i<detailResultStore.data.length; i++){
					if(detailResultStore.data.items[i].data.defaultselect){
						detailResultSelectModel.select(detailResultStore.data.items[i],true);
					}
				}
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
		}
	});
};

//删除拉货记录
airfreight.deleteAirUnshippedGoods = function(id){
	Ext.Msg.show({
		title:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),
		msg:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.confirmDelete'),  //确认删除本条记录？
		buttons:Ext.Msg.YESNO,
		icon: Ext.Msg.QUESTION, 
		fn : function(btn){
			if(btn == 'yes'){
				var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {id : id}}};
				Ext.Ajax.request({
					url:airfreight.realPath('deleteAirUnshippedGoods.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						airfreight.unshippedGoodsIndex.pagingBar.moveFirst();
						Ext.ux.Toast.msg(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.deleteSuccess'), 'ok', 1000);   //提示删除成功!
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
					}
				});
			}
		}
	});
};

//定义展示拉货信息的grid
Ext.define('Foss.airfreight.airUnshippedGoodsResult',{
	extend:'Ext.grid.Panel',
	title:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsResult.title'),  //拉货信息
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns:[{
		text: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsResult.label.operator'),  //操作
		flex: 2.5,
		renderer: function(v, metadata, record, rowIndex, columnIndex, store) { 
			var url='';			
			
    		var takeCertificateBag = '<a href="javascript:airfreight.unshippedMessage(\''+record.data.id+'\',\''+rowIndex+'\');" style="color:blue;text-decoration:none; " >'+airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.sendMsg')+'</a>';  //起草通知 
    		url+=takeCertificateBag;
    		
    		var queryAirUnshippedGoodsById = '<a href="javascript:airfreight.queryAirUnshippedGoodsById(\''+record.data.id+'\',\''+rowIndex+'\');" style="color:blue;text-decoration:none;" >'+airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.info')+'</a>';  //查看
    		url+=queryAirUnshippedGoodsById;
    		
        	var deleteAirUnshippedGoods = '<a href="javascript:airfreight.deleteAirUnshippedGoods(\''+record.data.id+'\');" style="color:blue;text-decoration:none;" >'+airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.delete')+'</a>';  // 删除
        	return url+deleteAirUnshippedGoods;
		}
	},{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedTime'),  //拉货时间
    	xtype : 'ellipsiscolumn',
		dataIndex: 'unshippedTime' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.arrvRegionName'),   //目的站
    	xtype : 'ellipsiscolumn',
		dataIndex: 'arrvRegionName' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airLine'),   //航空公司
    	xtype : 'ellipsiscolumn',
		dataIndex: 'airLine' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.billNo'),  //正单号/运单号
    	xtype : 'ellipsiscolumn',
		dataIndex: 'billNo' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleType'),  //配载类型
		dataIndex: 'airAssembleType' ,
		flex: 1.5,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_ASSEMBLE_TYPE');
		}
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.flightNo'),  //航班号
    	xtype : 'ellipsiscolumn',
		dataIndex: 'flightNo' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.goodsQtyTotal'),  //总件数
    	xtype : 'ellipsiscolumn',
		dataIndex: 'goodsQtyTotal' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.weightTotal'),  //总公斤<br>(公斤)
    	xtype : 'ellipsiscolumn',
		dataIndex: 'weightTotal' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedGoodsQty'),   //拉货<br>(件数)
    	xtype : 'ellipsiscolumn',
		dataIndex: 'unshippedGoodsQty' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedWeight'),   //拉货重量<br>(公斤)
    	xtype : 'ellipsiscolumn',
		dataIndex: 'unshippedWeight' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.receiverName'),   //收货人
    	xtype : 'ellipsiscolumn',
		dataIndex: 'receiverName' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.reassignFlightNo'),  //改配航班
    	xtype : 'ellipsiscolumn',
		dataIndex: 'reassignFlightNo' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.createUserName'),  //登记人
    	xtype : 'ellipsiscolumn',
		dataIndex: 'createUserName' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.notes'),  //备注
    	xtype : 'ellipsiscolumn',
		dataIndex: 'notes' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.createdUserName'),  //通知人
    	xtype : 'ellipsiscolumn',
		dataIndex: 'createdUserName' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.noticeTime'),   //通知时间
    	xtype : 'ellipsiscolumn',
		dataIndex: 'noticeTime' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleOrgName'),   //配载部<br>门名称
    	xtype : 'ellipsiscolumn',
		dataIndex: 'airAssembleOrgName' ,
		flex: 1
    },{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAgencyName'),  //代理名称
    	xtype : 'ellipsiscolumn',
		dataIndex: 'airAgencyName' ,
		flex: 1
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airUnshippedGoodsStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin'
		});
		airfreight.unshippedGoodsIndex.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


airfreight.unshippedGoodsIndex.queryPanel = Ext.create('Foss.airfreight.queryAirUnshippedGoodsPanel');
airfreight.unshippedGoodsIndex.resultPanel = Ext.create('Foss.airfreight.airUnshippedGoodsResult');

//定义新增拉货信息中代单号的model
Ext.define('Foss.airfreight.editUnshippedGoodsBillNoModel',{
	extend:'Ext.data.Model',
	fields:[{name:'serialNo', type:'string'},
	        {name:'undeselect',type:'boolean'},
	        {name:'defaultselect',type:'boolean'}
	        ]
});

//定义新增拉货信息的model
Ext.define('Foss.airfreight.editUnshippedGoodsModel',{
	extend:'Ext.data.Model',
	fields:[{name:'airAssembleType', type:'string'},
	        {name:'arrvRegionCode', type:'string'},
	        {name:'arrvRegionName', type:'string'},
	        {name:'flightNo', type:'string'},
	        {name:'receiverCode', type:'string'},
	        {name:'receiverName', type:'string'},
	        {name:'goodsQtyTotal', type:'string'},
	        {name:'unshippedGoodsQty', type:'string'},
	        {name:'weightTotal', type:'string'},
	        {name:'reassignFlightNo', type:'string'},
	        {name:'unshippedTime', type:'string'},
	        {name:'unshippedWeight', type:'string'},
	        {name:'notes', type:'string'},
	        {name:'airWaybillNo', type:'string'},
	        {name:'waybillNo', type:'string'},
	        {name:'airAgencyName', type:'string'},
	        {name:'airAgencyCode', type:'string'},
	        {name:'id', type:'string'}
	        ]
});

//定义新增拉货信息中代单号的store
Ext.define('Foss.airfreight.editUnshippedGoodsStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.editUnshippedGoodsBillNoModel'
});

//定义数据录入的panel
Ext.define('Foss.airfreight.editUnshippedGoodsPanel',{
	extend:'Ext.form.Panel',
	width:'100%',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	layout:'column',
	items:[{
		xtype:'radiogroup',
		name: 'airUnshippedType',  
		columnWidth: .3,
		items:[{
			boxLabel  : airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airWaybillNo'),   //正单号
			name      : 'unshippedType',  
			inputValue: 'AIR_WAY_BILL_NO'  //表示是正单号
		},{
			boxLabel  : airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.waybillNo'),   //代单号
			name      : 'unshippedType',  
			inputValue: 'WAY_BILL_NO'  //表示是代单号
		}]
	},{
		name:'airLine',
		displayField:'code',
		valueField:'simpleName',
		hidden:true,  //是否需要航空公司二字码还不知道
		editable:false,
		store:Ext.create('Foss.airfreight.queryAirlinesStore'),
		xtype:'combobox',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airWaybillNo'),  //正单号
		labelWidth:60,
		columnWidth: .14
	},{
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airWaybillNo'),  //正单号
		name:'airWaybillNo',
		columnWidth: .26
	},{
		name:'waybillNo',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.waybillNo'),  //代单号
		readOnly:true,
		columnWidth: .3
	},{
		name:'arrvRegionName',
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.arrvRegionName'),  //目的站
		columnWidth: .3
	},{
		xtype:'combobox',
		readOnly:true,
		name:'airAssembleType',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleType'),  //配载类型
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('AIR_ASSEMBLE_TYPE'),
		columnWidth: .3
	},{
		name:'receiverName',
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.receiverName'),  //收货人
		columnWidth: .3
	},{
		name:'flightNo',
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.flightNo'),  //航班号
		columnWidth: .3
	},{
		name:'weightTotal',
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.editUnshippedGoodsPanel.label.weightTotal'),  //总重量
		columnWidth: .3
	},{
		name:'goodsQtyTotal',
		readOnly:true,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.editUnshippedGoodsPanel.label.goodsQtyTotal'),  //总件数
		columnWidth: .3
	},{
	    xtype:'commonflightselector',
		name:'reassignFlightNo',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.reassignFlightNo'),  //改配航班
		columnWidth: .3
	},{
		allowBlank:false,
		name:'unshippedWeight',
		xtype:'numberfield',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedWeight'),  //拉货重量
		maxLength : 5,
		minValue : 1,
	    maxLengthText:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.maxLength'),  //长度已超过最大限制!
		columnWidth: .3
	},{
		allowBlank:false,
		xtype:'numberfield',
		name:'unshippedGoodsQty',
		minValue : 1,
		allowDecimals: false,
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedGoodsQty'),  //拉货件数
		columnWidth: .3
	},{
		xtype:'datetimefield_date97',
    	fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedTime'),  //拉货时间
    	allowBlank:false,
    	id:Ext.Date.format(new Date(),'YmdHis')+'Foss_airfreight_editUnshippedGoodsPanel_unshippedTimeId',
		time:true,
		name:'unshippedTime',
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_airfreight_editUnshippedGoodsPanel_unshippedTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .3
	},{
		readOnly:true,
		columnWidth:.7
	},{
		hidden:true,
		name:'airAgencyCode' //隐藏字段，外发代理code
	},{
		hidden:true,
		name:'airAgencyName' //隐藏字段，外发代理名称
	},{
		hidden:true,
		name:'arrvRegionCode' //隐藏字段，目的站code
	},{
		hidden:true,
		name:'receiverCode'  //隐藏字段，收货人code
	},{
		hidden:true,
		name:'id'  //隐藏字段，拉货信息id
	}]
});

//定义录入拉货中备注的panel
Ext.define('Foss.airfreight.editUnshippedGoodsNotePanel',{
	extend:'Ext.form.Panel',
	width:'50%',
	layout: 'column',
	height:150,
    bodyStyle:'padding:5px 5px 5px 5px',
	items:[{
		xtype:'textarea',
		fieldLabel:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedNotes'),  //拉货备注
		height:130,
		name:'notes',
		columnWidth:.8,
		maxLength:100,
	    maxLengthText:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.maxLength')   //长度已超过最大限制!
	}]
});

//定义展示拉货信息的grid
Ext.define('Foss.airfreight.airUnshippedGoodsBillNoGrid',{
	extend:'Ext.grid.Panel',
	title:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsBillNoGrid.title'),  //代单流水号
	autoScorll:false,
	height:150,
    width:'50%',
    bodyStyle:'padding:5px 5px 5px 5px',
    columns:[{
    	header: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.serialNo'),  //流水号
    	dataIndex:'serialNo',
    	flex:1
    }],
    constructor: function(config){
    	var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.editUnshippedGoodsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			listeners:{beforedeselect:function(model,record,index,opts){
					if(record.data.undeselect){
						return false;
					}
				}	
		    }
		});
		me.callParent([cfg]);
		airfreight.store = me.store;
    },
    listeners:{selectionchange:function(model,selected,eOpts){
    	airfreight.unshippedGoodsIndex.addPanel.getForm().getFields().items[14].setValue(selected.length);
    }}
});

//定义操作按钮的panel
Ext.define('Foss.airfreight.editUnshippedGoodsButtonPanel',{
	extend:'Ext.form.Panel',
	layout: 'column',
	width:'100%',
    bodyStyle:'padding:5px 5px 5px 5px',
	items:[{
		xtype:'button',
		text:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.button.save'),  //保存
		width:80,
		plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			seconds:5
		}),
		handler : function(){
			if(!airfreight.allPanel.getForm().isValid()){
				return;
			}
			var formValues = airfreight.allPanel.getValues();
			//判断拉货件数
			if(Ext.Number.from(formValues.unshippedGoodsQty,0) > Ext.Number.from(formValues.goodsQtyTotal,0)){
				Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustLessTotalQty'));  //拉货件数不能大于总件数
				return;
			}
			//判断拉货重量
			if(Ext.Number.from(formValues.unshippedWeight,0) > Ext.Number.from(formValues.weightTotal,0)){
				Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustLessTotalWeight'));  //拉货重量不能大于总重量
				return;
			}
			var fields = airfreight.unshippedGoodsIndex.addPanel.getForm().getFields().items;
			var selectedRecords = airfreight.unshippedGoodsIndex.billNoGrid.getSelectionModel().getSelection();
			if(formValues.unshippedType === 'AIR_WAY_BILL_NO'){
				formValues.billNo = formValues.airWaybillNo;
			}else{
				formValues.billNo = formValues.waybillNo;
				//当为代单号是校验是否选择了流水号
				if(selectedRecords.length < 1){
					Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustChoiseOneSeriaNo'));  //必须选择至少一条流水号
					return;
				}
			}
			var records = new Array();
			for(var i = 0 ; i < selectedRecords.length ; i++){
				selectedRecords[i].data.airWaybillDetailId = formValues.id;
    			records.push(selectedRecords[i].data);
    		}
			formValues.billNoType = formValues.unshippedType;
			var unshippedTime = formValues.unshippedTime;
			formValues.unshippedTime = '';
			//参数：拉货信息实体，流水号集合，配载类型，运单号,拉货时间
			var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {airUnshippedGoodsEntity : formValues,airUnshippedSerialNoList:records,billNoType:formValues.unshippedType,billNo:formValues.billNo,unshippedTime:unshippedTime}}};
			Ext.Ajax.request({
				url:airfreight.realPath('saveOrUpdateAirUnshippedGoods.action'),
        		jsonData:array,
        		success : function(response) {
    				var json = Ext.decode(response.responseText);
    				var record = Ext.create('Foss.airfreight.editUnshippedGoodsModel');
    				fields[21].setValue(json.airUnshippedGoodsVO.airUnshippedGoodsDto.id);   //存入ID到formValues中，标记此条记录是录入过的，下次保存将执行更新
    				Ext.ux.Toast.msg(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.saveSuccess'), 'ok', 1000);  //保存成功
    				if(formValues.reassignFlightNo === '' || formValues.reassignFlightNo === null){
    					Ext.Msg.show({
    	            		title:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),
    						msg:airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.isInStock'),  //是否入库？
    						buttons:Ext.Msg.YESNO,
    						icon: Ext.Msg.QUESTION, 
    						fn : function(btn){
    							if(btn == 'yes'){
    								//参数：流水号集合，配载类型，运单号
    								var array = {airUnshippedGoodsVO:{airUnshippedGoodsDto:{airUnshippedSerialNoList:records,billNoType:formValues.unshippedType,billNo:formValues.billNo}}};
    			    				Ext.Ajax.request({
    			    					url:airfreight.realPath('unshippedGoodsInStock.action'),
    			    	        		jsonData:array,
    			    	        		success : function(response) {
    			    	        			var json = Ext.decode(response.responseText);
    			    	    				Ext.ux.Toast.msg(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.inStockSuccess'), 'ok', 1000);  //入库成功
    			    	        		},
    			    	        		exception : function(response) {
    			    	    				var json = Ext.decode(response.responseText);
    			    	    				Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
    			    	    			}
    			    				});
    							}
    						}
    	            	});
    				}
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.Msg.alert(airfreight.unshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
    			}
			});
		}
	}],
	listeners : {
		render : function(panel,opt){
			var currentDept = airfreight.unshippedGoodsIndex.dept;
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			//保存按钮
			if(!airfreight.unshippedGoodsIndex.isPermission('airfreight/saveOrUpdateAirUnshippedGoodsButton')){
				btns[btns.length-1].hide();
			}
		}
	}
});

airfreight.unshippedGoodsIndex.addPanel = Ext.create('Foss.airfreight.editUnshippedGoodsPanel');
airfreight.unshippedGoodsIndex.addNotePanel = Ext.create('Foss.airfreight.editUnshippedGoodsNotePanel');
airfreight.unshippedGoodsIndex.billNoGrid = Ext.create('Foss.airfreight.airUnshippedGoodsBillNoGrid');
airfreight.unshippedGoodsIndex.controlBtn = Ext.create('Foss.airfreight.editUnshippedGoodsButtonPanel');

//定义数据录入的总panel
Ext.define('Foss.airfreight.editUnshippedGoodsAllPanel',{
	extend:'Ext.form.Panel',
	title: airfreight.unshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsResult.title'),  //查询条件
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	items:[
		airfreight.unshippedGoodsIndex.addPanel,
		airfreight.unshippedGoodsIndex.addNotePanel,
		airfreight.unshippedGoodsIndex.billNoGrid,
		airfreight.unshippedGoodsIndex.controlBtn
	       ]
});

//定义弹出的编辑窗口
Ext.define('Foss.airfreight.editDetailInfoWindows',{
	extend: 'Ext.window.Window',
	closeAction: 'hide',
	width:1200,
	modal:true,
	bodyCls: 'autoHeight',
	resizable:false,
	editUnshippedGoodsAllForm : null,
	getEditUnshippedGoodsAllForm: function(){
		if(this.editUnshippedGoodsAllForm==null){
			this.editUnshippedGoodsAllForm = Ext.create('Foss.airfreight.editUnshippedGoodsAllPanel');
		}
		airfreight.allPanel = this.editUnshippedGoodsAllForm;
		return this.editUnshippedGoodsAllForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditUnshippedGoodsAllForm()
				];
		me.callParent([cfg]);
	}
});

airfreight.unshippedGoodsIndex.detailInfoWindow = Ext.create('Foss.airfreight.editDetailInfoWindows');

airfreight.unshippedGoodsIndex.dept = '';
airfreight.unshippedGoodsIndex.deptCode = '';
airfreight.unshippedGoodsIndex.deptName = '';

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-unshippedGoodsIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [airfreight.unshippedGoodsIndex.queryPanel,airfreight.unshippedGoodsIndex.resultPanel],
		renderTo: 'T_airfreight-unshippedGoodsIndex-body'
	});
});