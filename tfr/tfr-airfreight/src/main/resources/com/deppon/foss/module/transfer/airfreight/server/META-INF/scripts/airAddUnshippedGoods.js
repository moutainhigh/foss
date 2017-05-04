//定义新增拉货信息中代单号的model
Ext.define('Foss.airfreight.addUnshippedGoodsBillNoModel',{
	extend:'Ext.data.Model',
	fields:[{name:'serialNo', type:'string'}
	        ]
});

//定义新增拉货信息的model
Ext.define('Foss.airfreight.addUnshippedGoodsModel',{
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
Ext.define('Foss.airfreight.addUnshippedGoodsStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.airfreight.addUnshippedGoodsBillNoModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('querySerialNo.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airUnshippedGoodsVO.airWaybillSerialNoList'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var addPanel = airfreight.addPanel;
			if (addPanel != null) {
				var addPanelValues = addPanel.getValues();
				var billNo = '';
				if(addPanelValues.billNoType === 'AIR_WAY_BILL_NO'){
					billNo = addPanelValues.airWaybillNo;
				}
				if(addPanelValues.billNoType === 'WAY_BILL_NO'){
					billNo = addPanelValues.waybillNo;
				}
				Ext.apply(operation, {
					params : {
						'airUnshippedGoodsVO.airUnshippedGoodsDto.billNoType' : addPanelValues.billNoType,
						'airUnshippedGoodsVO.airUnshippedGoodsDto.billNo' : billNo
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

//定义数据录入的panel
Ext.define('Foss.airfreight.addUnshippedGoodsPanel',{
	extend:'Ext.form.Panel',
	width:'100%',
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60,
		maxLength:20,
	    maxLengthText:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.maxLength')   //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype:'radiogroup',
		columnWidth: .3,
		items:[{
			boxLabel  : airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airWaybillNo'), //正单号
			name      : 'billNoType',  
			inputValue: 'AIR_WAY_BILL_NO',  //表示是正单号
			checked : true
		},{
			boxLabel  : airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.waybillNo'),    //代单号 
			name      : 'billNoType',  
			inputValue: 'WAY_BILL_NO',  //表示是代单号
		}],
		listeners:{change:function(text,newValue,oldValue){
			var form = this.up('form').getForm();
			var fields = form.getFields().items;
			if(newValue.billNoType === 'AIR_WAY_BILL_NO'){
				fields[5].setDisabled(true);
				fields[5].setValue('');
				fields[3].setDisabled(false);
				fields[4].setDisabled(false);
				fields[14].setReadOnly(false);
				airfreight.store.load();
			}else{
				fields[3].setDisabled(true);
				fields[4].setDisabled(true);
				fields[3].setValue('');
				fields[4].setValue('');
				fields[5].setDisabled(false);
				fields[14].setReadOnly(true);
			}
			fields[6].setValue('');
			fields[7].setValue('');
			fields[8].setValue('');
			fields[9].setValue('');
			fields[10].setValue('');
			fields[11].setValue('');
			fields[14].setValue('');
		}}
	},{
		name:'airLine',
		displayField:'code',
		valueField:'code',
		editable:false,
		store:Ext.create('Foss.airfreight.queryAirlinesStore'),
		xtype:'combobox',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airWaybillNo'),  //正单号
		labelWidth:60,
		columnWidth: .14,
		listeners:{
			change:function(newValue,oldValue,eOpts){
				var record = Ext.create('Foss.airfreight.addUnshippedGoodsModel');
				var airWaybillNo = airfreight.addPanel.getValues().airWaybillNo; 
				var airLine = newValue.value;
				if(airWaybillNo === "" || airWaybillNo == null || airLine == null || airLine === ""){
					airfreight.addPanel.loadRecord(record);
					airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
					return;
				}else{
					var formValues = airfreight.addPanel.getValues();
					var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {billNoType : formValues.billNoType,billNo: airWaybillNo,airLine:newValue.value}}};
					Ext.Ajax.request({
						url:airfreight.realPath('queryUnshippedGoodsByBillNo.action'),
		        		jsonData:array,
		        		success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var airUnshippedGoodsEntity = json.airUnshippedGoodsVO.airUnshippedGoodsEntity
		    				var form = airfreight.addPanel.getForm();
		    				form.findField('arrvRegionName').setValue(airUnshippedGoodsEntity.arrvRegionName);
		    				form.findField('arrvRegionCode').setValue(airUnshippedGoodsEntity.arrvRegionCode);
		    				form.findField('airAssembleType').setValue(airUnshippedGoodsEntity.airAssembleType);
		    				form.findField('receiverName').setValue(airUnshippedGoodsEntity.receiverName);
		    				form.findField('receiverCode').setValue(airUnshippedGoodsEntity.receiverCode);
		    				form.findField('airAgencyName').setValue(airUnshippedGoodsEntity.airAgencyName);
		    				form.findField('airAgencyCode').setValue(airUnshippedGoodsEntity.airAgencyCode);
		    				form.findField('flightNo').setValue(airUnshippedGoodsEntity.flightNo);
		    				form.findField('weightTotal').setValue(airUnshippedGoodsEntity.weightTotal);
		    				form.findField('goodsQtyTotal').setValue(airUnshippedGoodsEntity.goodsQtyTotal);
		    				//airfreight.addPanel.loadRecord(record);
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				airfreight.addPanel.loadRecord(record);
		    				airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
		    				airfreight.addPanel.getForm().findField('airWaybillNo').setValue(airWaybillNo);
		    				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
		    			}
					});
				}
			}
		}
	},{
		allowBlank:false,
		name:'airWaybillNo',
		emptyText:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.enterAirWaybillNo'),  //输入正单号
		columnWidth: .26,
		listeners:{blur:function(text,op){
			var record = Ext.create('Foss.airfreight.addUnshippedGoodsModel');
			var airWaybillNo = text.getValue(); 
			var airlineTwoletter = airfreight.addPanel.getValues().airLine;
			if(airWaybillNo === "" || airWaybillNo == null || airlineTwoletter == null || airlineTwoletter === ""){
				airfreight.addPanel.loadRecord(record);
				airfreight.addPanel.getForm().findField('airWaybillNo').setValue(airWaybillNo);
				airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
				return;
			}else{
				var formValues = airfreight.addPanel.getValues();
				var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {billNoType : formValues.billNoType,billNo: airWaybillNo,airLine:formValues.airLine}}};
				Ext.Ajax.request({
					url:airfreight.realPath('queryUnshippedGoodsByBillNo.action'),
	        		jsonData:array,
	        		success : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				var airUnshippedGoodsEntity = json.airUnshippedGoodsVO.airUnshippedGoodsEntity
	    				var form = airfreight.addPanel.getForm();
	    				form.findField('arrvRegionName').setValue(airUnshippedGoodsEntity.arrvRegionName);
	    				form.findField('arrvRegionCode').setValue(airUnshippedGoodsEntity.arrvRegionCode);
	    				form.findField('airAssembleType').setValue(airUnshippedGoodsEntity.airAssembleType);
	    				form.findField('receiverCode').setValue(airUnshippedGoodsEntity.receiverCode);
	    				form.findField('receiverName').setValue(airUnshippedGoodsEntity.receiverName);
	    				form.findField('airAgencyName').setValue(airUnshippedGoodsEntity.airAgencyName);
	    				form.findField('airAgencyCode').setValue(airUnshippedGoodsEntity.airAgencyCode);
	    				form.findField('flightNo').setValue(airUnshippedGoodsEntity.flightNo);
	    				form.findField('weightTotal').setValue(airUnshippedGoodsEntity.weightTotal);
	    				form.findField('goodsQtyTotal').setValue(airUnshippedGoodsEntity.goodsQtyTotal);
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				airfreight.addPanel.loadRecord(record);
	    				airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
	    				airfreight.addPanel.getForm().findField('airWaybillNo').setValue(airWaybillNo);
	    				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
	    			}
				});
			}
		}}
	},{
		name:'waybillNo',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.waybillNo'),    //代单号
		allowBlank:false,
		disabled: true,
		columnWidth: .3,
		listeners:{blur:function(text,op){
			airfreight.store.load();
			var record = Ext.create('Foss.airfreight.addUnshippedGoodsModel');
			var waybillNo = text.getValue();
			if(text.getValue().trim() === "" || text.getValue() === null){
				airfreight.addPanel.loadRecord(record);
				return;
			}
			var formValues = airfreight.addPanel.getValues();
			var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {billNoType : formValues.billNoType,billNo: text.getValue()}}};
			Ext.Ajax.request({
				url:airfreight.realPath('queryUnshippedGoodsByBillNo.action'),
        		jsonData:array,
        		success : function(response) {
    				var json = Ext.decode(response.responseText);
    				var airUnshippedGoodsEntity = json.airUnshippedGoodsVO.airUnshippedGoodsEntity
    				var form = airfreight.addPanel.getForm();
    				form.findField('arrvRegionName').setValue(airUnshippedGoodsEntity.arrvRegionName);
    				form.findField('arrvRegionCode').setValue(airUnshippedGoodsEntity.arrvRegionCode);
    				form.findField('airAssembleType').setValue(airUnshippedGoodsEntity.airAssembleType);
    				form.findField('receiverCode').setValue(airUnshippedGoodsEntity.receiverCode);
    				form.findField('receiverName').setValue(airUnshippedGoodsEntity.receiverName);
    				form.findField('airAgencyName').setValue(airUnshippedGoodsEntity.airAgencyName);
    				form.findField('airAgencyCode').setValue(airUnshippedGoodsEntity.airAgencyCode);
    				form.findField('flightNo').setValue(airUnshippedGoodsEntity.flightNo);
    				form.findField('weightTotal').setValue(airUnshippedGoodsEntity.weightTotal);
    				form.findField('goodsQtyTotal').setValue(airUnshippedGoodsEntity.goodsQtyTotal);
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				airfreight.addPanel.loadRecord(record);
					airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
					airfreight.addPanel.getForm().findField('waybillNo').setValue(waybillNo);
    				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
    			}
			});
		}}
	},{
		readOnly:true,
		name:'arrvRegionName',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.arrvRegionName'),   //目的站
		columnWidth: .3
	},{
		readOnly:true,
		xtype:'combobox',
		name:'airAssembleType',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.airAssembleType'),  //配载类型
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('AIR_ASSEMBLE_TYPE'),
		columnWidth: .3
	},{
		readOnly:true,
		name:'receiverName',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.receiverName'),   //收货人
		columnWidth: .3
	},{
		readOnly:true,
		name:'flightNo',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.flightNo'),  //航班号
		columnWidth: .3
	},{
		readOnly:true,
		name:'weightTotal',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.editUnshippedGoodsPanel.label.weightTotal'),  //总重量
		columnWidth: .3
	},{
		readOnly:true,
		name:'goodsQtyTotal',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.editUnshippedGoodsPanel.label.goodsQtyTotal'),  //总件数
		columnWidth: .3
	},{
	    xtype:'commonflightselector',
		name:'reassignFlightNo',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.reassignFlightNo'),  //改配航班
		columnWidth: .3
	},{
		allowBlank:false,
		name:'unshippedWeight',
		xtype:'numberfield',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedWeight'),  //拉货重量
		maxLength : 5,
		minValue : 1,
	    maxLengthText:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.maxLength'),
		columnWidth: .3
	},{
		allowBlank:false,
		xtype:'numberfield',
		name:'unshippedGoodsQty',
		minValue : 1,
		allowDecimals: false,
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedGoodsQty'),  //拉货件数
		columnWidth: .3
	},{
		xtype:'datetimefield_date97',
    	fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedTime'),  //拉货时间
    	allowBlank:false,
    	id:'Foss_airfreight_addUnshippedGoodsPanel_unshippedTimeId',
		time:true,
		name:'unshippedTime',
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: 'Foss_airfreight_addUnshippedGoodsPanel_unshippedTimeId-inputEl',
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
Ext.define('Foss.airfreight.addUnshippedGoodsNotePanel',{
	extend:'Ext.form.Panel',
	width:'50%',
	layout: 'column',
	height:150,
    bodyStyle:'padding:5px 5px 5px 5px',
	items:[{
		xtype:'textarea',
		fieldLabel:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.unshippedNotes'),  //拉货备注
		height:130,
		name:'notes',
		columnWidth:.8,
		maxLength:100,
	    maxLengthText:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.maxLength')   //长度已超过最大限制!
	}]
});

//定义展示拉货信息的grid
Ext.define('Foss.airfreight.airUnshippedGoodsBillNoGrid',{
	extend:'Ext.grid.Panel',
	title:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsBillNoGrid.title'),  //代单流水号
	autoScorll:false,
	height:150,
    width:'50%',
    bodyStyle:'padding:5px 5px 5px 5px',
    columns:[{
    	header: airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.serialNo'),  //流水号
    	dataIndex:'serialNo',
    	flex:1
    }],
    constructor: function(config){
    	var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.addUnshippedGoodsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
		airfreight.store = me.store;
    },
    listeners:{selectionchange:function(model,selected,eOpts){
    	airfreight.addPanel.getForm().getFields().items[14].setValue(selected.length);
    }}
});

//定义操作按钮的panel
Ext.define('Foss.airfreight.addUnshippedGoodsButtonPanel',{
	extend:'Ext.form.Panel',
	layout: 'column',
	width:'100%',
    bodyStyle:'padding:5px 5px 5px 5px',
	items:[{
		xtype:'button',
		text:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.button.save'),  //保存
		width:80,
		plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			seconds:5
		}),
		handler : function(){
			if(!airfreight.allPanel.getForm().isValid()){
				return;
			}
			var saveBtn = this;
			var formValues = airfreight.allPanel.getValues();
			//判断拉货件数
			if(Ext.Number.from(formValues.unshippedGoodsQty,0) > Ext.Number.from(formValues.goodsQtyTotal,0)){
				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustLessTotalQty'));  //拉货件数不能大于总件数
				return;
			}
			//判断拉货重量
			if(Ext.Number.from(formValues.unshippedWeight,0) > Ext.Number.from(formValues.weightTotal,0)){
				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustLessTotalWeight'));  //拉货重量不能大于总重量
				return;
			}
			var fields = airfreight.addPanel.getForm().getFields().items;
			var selectedRecords = airfreight.billNoGrid.getSelectionModel().getSelection();
			if(formValues.billNoType === 'AIR_WAY_BILL_NO'){
				formValues.billNo = formValues.airWaybillNo;
			}else{
				formValues.billNo = formValues.waybillNo;
				//当为代单号是校验是否选择了流水号
				if(selectedRecords.length < 1){
					Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.mustChoiseOneSeriaNo'));  //必须选择至少一条流水号
					return;
				}
			}
			var records = new Array();
			for(var i = 0 ; i < selectedRecords.length ; i++){
				selectedRecords[i].data.airWaybillDetailId = formValues.id;
    			records.push(selectedRecords[i].data);
    		}
			formValues.unshippedType = formValues.billNoType;
			var unshippedTime = formValues.unshippedTime;
			formValues.unshippedTime = '';
			saveBtn.setDisabled(true);
			//参数：拉货信息实体，流水号集合，配载类型，运单号,拉货时间
			var array = {airUnshippedGoodsVO : {airUnshippedGoodsDto : {airUnshippedGoodsEntity : formValues,airUnshippedSerialNoList:records,billNoType:formValues.billNoType,billNo:formValues.billNo,unshippedTime:unshippedTime}}};
			Ext.Ajax.request({
				url:airfreight.realPath('saveOrUpdateAirUnshippedGoods.action'),
        		jsonData:array,
        		success : function(response) {
    				var json = Ext.decode(response.responseText);
    				var record = Ext.create('Foss.airfreight.addUnshippedGoodsModel');
    				fields[21].setValue(json.airUnshippedGoodsVO.airUnshippedGoodsDto.id);   //存入ID到formValues中，标记此条记录是录入过的，下次保存将执行更新
    				Ext.ux.Toast.msg(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.saveSuccess'), 'ok', 1000);  //保存成功
    				fields[0].setDisabled(true);
    				fields[3].setDisabled(true);
    				fields[4].setDisabled(true);
    				fields[5].setDisabled(true);
    				if(formValues.reassignFlightNo === '' || formValues.reassignFlightNo === null){
    					Ext.Msg.show({
    	            		title:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'),
    						msg:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.isInStock'),  //是否入库？
    						buttons:Ext.Msg.YESNO,
    						icon: Ext.Msg.QUESTION, 
    						fn : function(btn){
    							if(btn == 'yes'){
    								//参数：流水号集合，配载类型，运单号
    								var array = {airUnshippedGoodsVO:{airUnshippedGoodsDto:{airUnshippedSerialNoList:records,billNoType:formValues.billNoType,billNo:formValues.billNo,unshippedGoodsQty:formValues.unshippedGoodsQty}}};
    			    				Ext.Ajax.request({
    			    					url:airfreight.realPath('unshippedGoodsInStock.action'),
    			    	        		jsonData:array,
    			    	        		success : function(response) {
    			    	        			var json = Ext.decode(response.responseText);
    			    	    				Ext.ux.Toast.msg(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.message'), airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.msg.inStockSuccess'), 'ok', 1000);  //入库成功
    			    	        		},
    			    	        		exception : function(response) {
    			    	    				var json = Ext.decode(response.responseText);
    			    	    				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
    			    	    			}
    			    				});
    							}
    						}
    	            	});
    				}
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				saveBtn.setDisabled(false);
    				Ext.Msg.alert(airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.tip.failure'),json.message);
    			}
			});
		}
	},{
		xtype:'button',
		text:airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.unshippedGoodsIndex.label.clear'),  //清空
		width:80,
		handler : function(){
			var fields = airfreight.addPanel.getForm().getFields().items;
			fields[0].setReadOnly(false);
			//重置的时候根据选择的类型调整控件的属性
			if(fields[0].getValue().billNoType === 'AIR_WAY_BILL_NO'){
			    fields[3].setValue('');
				fields[4].setReadOnly(false);
				fields[4].setDisabled(false);
				fields[5].setReadOnly(false);
				fields[5].setDisabled(true);
			}else{
				fields[4].setReadOnly(false);
				fields[4].setDisabled(true);
				fields[5].setReadOnly(false);
				fields[5].setDisabled(false);
			}
			var record = Ext.create('Foss.airfreight.addUnshippedGoodsModel');
			airfreight.addPanel.loadRecord(record);
			airfreight.addNotePanel.loadRecord(record);
			airfreight.store.load();
			airfreight.addPanel.getForm().findField('unshippedTime').setValue(Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00');
		} 
	}],
	listeners : {
		render : function(panel,opt){
			var currentDept = FossUserContext.getCurrentDept();
			var form = panel.getForm();
			//获取button
			var btns = panel.items.items;
			btns[btns.length-2].hide();
			//保存按钮
			if(airfreight.addUnshippedGoodsIndex.isPermission('airfreight/saveOrUpdateAirUnshippedGoodsButton')){
				btns[btns.length-2].show();
			}
		}
	}
});

airfreight.addPanel = Ext.create('Foss.airfreight.addUnshippedGoodsPanel');
airfreight.addNotePanel = Ext.create('Foss.airfreight.addUnshippedGoodsNotePanel');
airfreight.billNoGrid = Ext.create('Foss.airfreight.airUnshippedGoodsBillNoGrid');
airfreight.controlBtn = Ext.create('Foss.airfreight.addUnshippedGoodsButtonPanel');

//定义数据录入的总panel
Ext.define('Foss.airfreight.addUnshippedGoodsAllPanel',{
	extend:'Ext.form.Panel',
	title: airfreight.addUnshippedGoodsIndex.i18n('foss.airfreight.airUnshippedGoodsResult.title'),  //查询条件
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	items:[
		airfreight.addPanel,
		airfreight.addNotePanel,
		airfreight.billNoGrid,
		airfreight.controlBtn
	       ]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	airfreight.allPanel = Ext.create('Foss.airfreight.addUnshippedGoodsAllPanel');
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-addUnshippedGoodsIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [airfreight.allPanel],
		renderTo: 'T_airfreight-addUnshippedGoodsIndex-body'
	});
});