//定义车辆油耗信息总的model
Ext.define('Foss.Management.FuelConsumptionModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'vehicleNo', type: 'string'},
	        {name: 'id', type: 'string'},
	        {name: 'vehicleId', type: 'string'},
	        {name: 'vehicleType', type: 'string'},
	        {name: 'vehicleBrand', type: 'string'},
	        {name: 'divisionOrgName', type: 'string'},
	        {name: 'transDepartmentName', type: 'string'},
	        {name: 'groupOrgName', type: 'string'},
	        {name: 'regionOrgName', type: 'string'},
	        {name: 'departureTypeCode', type: 'string'},
	        {name: 'remark', type: 'string'},
	        {name: 'fuelStandard', type: 'string'},
	        {name: 'lineCode', type: 'string'},
	        {name: 'lineName', type: 'string'},
	        {name: 'deptRegionCode', type: 'string'},
	        {name: 'deptRegionName', type: 'string'},
	        {name: 'arrvRegionCode', type: 'string'},
	        {name: 'arrvRegionName', type: 'string'},
	        {name: 'startKm', type: 'string'},
	        {name: 'arriveKm', type: 'string'},
	        {name: 'actualLoad', type: 'string'},
	        {name: 'runKm', type: 'string'},
	        {name: 'driver1Code', type: 'string'},
	        {name: 'driver2Code', type: 'string'},
	        {name: 'driver1Name', type: 'string'},
	        {name: 'driver2Name', type: 'string'},
	        {name: 'volume', type: 'string'},
	        {name: 'departureMode', type: 'string'},
	        {name: 'fuelAmount', type: 'string'},
	        {name: 'averagePrice', type: 'string'},//单次发车平均加油单价
	        {name: 'fuelFeeTotal', type: 'string'},
	        {name: 'roadTollTotal', type: 'string'},
	        {name: 'assemblyNo', type: 'string'},
	        {name: 'hdKmFuel',type: 'string'}, //单次发车百公里油耗
	        {name: 'fuelQtyTotal',type: 'string'},//单次发车加油升数合计
	        
	        {name: 'notes', type: 'string'},
	        {name:'fuelDate', type:'date',
				convert: function(v){
					if(v!=null){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d');
					}
				}},{name: 'roadToll', type: 'string'},
				{name:'departureDate', type:'date',
					convert: function(v){
						if(v!=null){
							var date = new Date(v);
							return Ext.Date.format(date,'Y-m-d');
						}
					}}]

});

//定义车辆油耗信息总的store
Ext.define('Foss.Management.FuelConsumptionStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelConsumptionModel',
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: management.realPath('queryFuelConsumption.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'fuelConsumptionVo.fuelConsumptionDtoList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = management.queryFuelConsumption;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				if(queryParams.departureTypeCode === 'CHOISE'){
					queryParams.departureTypeCode = "";
				}
				if(queryParams.fuelPayType === 'CHOISE'){
					queryParams.fuelPayType = "";
				}
				Ext.apply(operation, {
					params : {
						'fuelConsumptionVo.fuelConsumptionDto.vehicleNo' : queryParams.vehicleNo,
						'fuelConsumptionVo.fuelConsumptionDto.divisionOrgName' : queryParams.divisionOrgName,
						'fuelConsumptionVo.fuelConsumptionDto.regionOrgName' : queryParams.regionOrgName,
						'fuelConsumptionVo.fuelConsumptionDto.transDepartmentName' : queryParams.transDepartmentName,
						'fuelConsumptionVo.fuelConsumptionDto.groupOrgName' :queryParams.groupOrgName,
						'fuelConsumptionVo.fuelConsumptionDto.lineCode' :queryParams.lineCode,
						'fuelConsumptionVo.fuelConsumptionDto.driverCode' :queryParams.driverCode,
						'fuelConsumptionVo.fuelConsumptionDto.departureTypeCode' :queryParams.departureTypeCode,
						'fuelConsumptionVo.fuelConsumptionDto.fuelType' :queryParams.fuelType,
						'fuelConsumptionVo.fuelConsumptionDto.fuelPayType' :queryParams.fuelPayType,
						'fuelConsumptionVo.fuelConsumptionDto.fuelGrade' :queryParams.fuelGrade,
						'fuelConsumptionVo.fuelConsumptionDto.fuelDate' :queryParams.fuelDate,
						'fuelConsumptionVo.fuelConsumptionDto.beginDate' :queryParams.beginDate,
						'fuelConsumptionVo.fuelConsumptionDto.endDate' :queryParams.endDate,
						'fuelConsumptionVo.fuelConsumptionDto.departureDate' :queryParams.departureDate
					}
				});	
			}
		},
		'load': function(store, records) {
			var totalDto = store.proxy.reader.rawData.fuelConsumptionVo.totalDto;
			if(totalDto != null) {
				management.fuelConsumptionTotalInfo.getForm().findField('runKmAll').setValue(totalDto.runKmAll);
				management.fuelConsumptionTotalInfo.getForm().findField('fuelQtyAll').setValue(totalDto.fuelQtyAll);
				management.fuelConsumptionTotalInfo.getForm().findField('fuelFeeAll').setValue(totalDto.fuelFeeAll);
				management.fuelConsumptionTotalInfo.getForm().findField('roadTollAll').setValue(totalDto.roadTollAll);
				management.fuelConsumptionTotalInfo.getForm().findField('avgPriceAll').setValue(totalDto.avgPriceAll);
				management.fuelConsumptionTotalInfo.getForm().findField('kmRoadTollAll').setValue(totalDto.kmRoadTollAll);
				management.fuelConsumptionTotalInfo.getForm().findField('hdKmFuelAll').setValue(totalDto.hdKmFuelAll);
				management.fuelConsumptionTotalInfo.getForm().findField('sideLoadAll').setValue(totalDto.sideLoadAll);
				management.fuelConsumptionTotalInfo.getForm().findField('thdKmFuelAll').setValue(totalDto.thdKmFuelAll);
			} else {
				management.fuelConsumptionTotalInfo.getForm().findField('runKmAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('fuelQtyAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('fuelFeeAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('roadTollAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('avgPriceAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('kmRoadTollAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('hdKmFuelAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('sideLoadAll').setValue(0);
				management.fuelConsumptionTotalInfo.getForm().findField('thdKmFuelAll').setValue(0);
			}
		}
	}
});

//定义车辆信息
Ext.define('Foss.Management.FuelVehicleModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'id',type: 'string'},
	        {name: 'vehicleNo',type: 'string'},
	        {name: 'vehicleType',type: 'string'},
	        {name: 'vehicleBrand',type: 'string'},
	        {name: 'divisionOrgName',type: 'string'},
	        {name: 'transDepartmentName',type: 'string'},
	        {name: 'groupOrgName',type: 'string'},
	        {name: 'regionOrgName',type: 'string'},
	        {name: 'departureTypeCode',type: 'string'},
	        {name: 'remark',type: 'string'},
	        ]
});
//车辆store
Ext.define('Foss.Management.FuelVehicleStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelVehicleModel'
});

//定义发车model
Ext.define('Foss.Management.FuelDepartureModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'id',type: 'string'},
	        {name: 'vehicleId',type: 'string'},
	        {name: 'assemblyNo',type: 'string'},
	        {name:'departureDate', type:'date',
				convert: function(v){
					if(v!=null){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d');
					}
				}},
	        {name: 'startKm',type: 'string'},
	        {name: 'arriveKm',type: 'string'},
	        {name: 'deptRegionCode',type: 'string'},
	        {name: 'deptRegionName',type: 'string'},
	        {name: 'arrvRegionCode',type: 'string'},
	        {name: 'arrvRegionName',type: 'string'},
	        {name: 'lineCode',type: 'string'},
	        {name: 'lineName',type: 'string'},
	        {name: 'departureMode',type: 'string'},
	        {name: 'actualLoad',type: 'string'},
	        {name: 'volume',type: 'string'},
	        {name: 'driver1Code',type: 'string'},
	        {name: 'driver1Name',type: 'string'},
	        {name: 'driver2Code',type: 'string'},
	        {name: 'driver2Name',type: 'string'},
	        {name: 'runKm',type: 'string'},
	        {name: 'fuelStandard',type: 'string'},
	        {name: 'fuelFeeTotal',type: 'string'},
	        {name: 'roadTollTotal',type: 'string'},
	        {name: 'hdKmFuel',type: 'string'}, //单次发车百公里油耗
	        {name: 'fuelQtyTotal',type: 'string'},//单次发车加油升数合计
	        {name: 'averagePrice', type: 'string'},//单次发车平均加油单价
	        //{name: 'kmRoadToll', type: 'string'},//单次发车公里路桥费
	        {name: 'remark',type: 'string'},
	        {name: 'fuelDetailList',type: 'auto'},
	        {name: 'fuelRoadTollList',type:'auto'}
	        ]
});
//发车store
Ext.define('Foss.Management.FuelDepartureStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelDepartureModel'
});

//定义加油信息的model
Ext.define('Foss.Management.FuelDetailModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'departureId', type: 'string'},
	        {name: 'fuelQty', type: 'string'},
	        {name: 'unitPrice', type: 'string'},
	        {name: 'fuelFee', type: 'string'},
	        {name: 'fuelType', type: 'string'},
	        {name: 'fuelSupplier', type: 'string'},
	        {name: 'fuelGrade', type: 'string'},
	        {name: 'fuelAddress', type: 'string'},
	        {name: 'balance', type: 'string'},
	        {name: 'fuelPayType', type: 'string'},
	        {name: 'fuelTime', type: 'string',
	        	convert: function(v){
					if(Ext.isNumber(v)){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d H:i:s');
					}
					if(Ext.isDate(v)){
						return Ext.Date.format(v,'Y-m-d H:i:s');
					}
					return v;
				}
	        }]
});
//定义加油信息的store
Ext.define('Foss.Management.FuelDetailStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelDetailModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: management.realPath('queryFuelDetailById.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'fuelConsumptionVo.fuelDetailList',
			successProperty: 'success'
		}
	}
});

//定义路桥费信息的model
Ext.define('Foss.Management.FuelRoadTollModel',{
	extend: 'Ext.data.Model',
	fields:[{name: 'departureId', type: 'string'},
	        {name: 'roadToll', type: 'string'},
	        {name: 'roadAddress', type: 'string'},
	        {name: 'roadTime', type: 'string',
	        	convert: function(v){
					if(Ext.isNumber(v)){
						var date = new Date(v);
						return Ext.Date.format(date,'Y-m-d H:i:s');
					}
					if(Ext.isDate(v)){
						return Ext.Date.format(v,'Y-m-d H:i:s');
					}
					return v;
				}
	        },
	        {name: 'payment', type: 'string'}]
});
//路桥费store
Ext.define('Foss.Management.FuelRoadTollStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.FuelRoadTollModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: management.realPath('queryFuelRoadTollById.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'fuelConsumptionVo.fuelRoadTollList',
			successProperty: 'success'
		}
	}
});

//定义车辆信息的panel
Ext.define('Foss.Management.FuelConsumptionVehicleInfo',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionVehicleInfo.title'),   //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		allowBlank:true,
		readOnly: false,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype:'commontruckselector',
		name:'vehicleNo',
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var vehicleNo = field.getValue();
				if(vehicleNo != '' && vehicleNo != null){
					var array = {fuelConsumptionVo:{fuelConsumptionDto:{vehicleNo:vehicleNo}}};
					Ext.Ajax.request({
						url:management.realPath('queryVehicleInfo.action'),
						jsonData:array,
						success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var fuelVehicleEntity = json.fuelConsumptionVo.fuelVehicleEntity;
		    				form.findField('vehicleType').setValue(fuelVehicleEntity.vehicleType); //车型
		    				form.findField('vehicleBrand').setValue(fuelVehicleEntity.vehicleBrand);  //车辆品牌
		    				form.findField('transDepartmentName').setValue(fuelVehicleEntity.transDepartmentName);  //车队 
		    				form.findField('groupOrgName').setValue(fuelVehicleEntity.groupOrgName);  //小组
		    				form.findField('divisionOrgName').setValue(fuelVehicleEntity.divisionOrgName);  //事业部
		    				//form.findField('fuelStandard').setValue(fuelConsumptionEntity.fuelStandard);  //百公里油耗
		    				form.findField('regionOrgName').setValue(fuelVehicleEntity.regionOrgName);  //所属大区
	        				form.findField('departureTypeCode').setValue(fuelVehicleEntity.departureTypeCode);//发车性质
		    				//小组公共选择器
	        				var cmbGroupOrg = management.fuelConsumptionVehicleInfo.getForm().findField('groupOrgCode');
	        				cmbGroupOrg.setValue(fuelVehicleEntity.groupOrgName);
	        				cmbGroupOrg.getStore().load({params:{'commonOrgVo.name' : fuelVehicleEntity.groupOrgName}});
	        				
	        				//车队公共选择器
	        				var cmbLargeDistrict = management.fuelConsumptionVehicleInfo.getForm().findField('transDepartmentCode');
	        				cmbLargeDistrict.setValue(fuelVehicleEntity.transDepartmentName);
	        				cmbLargeDistrict.getStore().load({params:{'commonOrgVo.transTeam' : fuelVehicleEntity.transDepartmentName}});
	        				
	        				//事业部公共选择器
	        				var cmbDivisionOrgCode = management.fuelConsumptionVehicleInfo.getForm().findField('divisionOrgCode');
	        				cmbDivisionOrgCode.setValue(fuelVehicleEntity.divisionOrgName);
	        				cmbDivisionOrgCode.getStore().load({params:{'commonOrgVo.division' : fuelVehicleEntity.divisionOrgName}});
	        				
	        				//大区公共选择器
	        				var cmbLargeDistrictCode = management.fuelConsumptionVehicleInfo.getForm().findField('regionOrgCode');
	        				cmbLargeDistrictCode.setValue(fuelVehicleEntity.regionOrgName);
	        				cmbLargeDistrictCode.getStore().load({params:{'commonMotorcadeVo.commonMotorcadeDto.name' : fuelVehicleEntity.regionOrgName}});
	        				
	        				//发车性质
		    				var departureType = form.findField('departureTypeCode');
		    				
		    				if(Ext.isEmpty(fuelVehicleEntity.vehicleType)) {
		    					form.findField('vehicleType').setReadOnly(false);
		    				} else {
		    					form.findField('vehicleType').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.vehicleBrand)) {
		    					form.findField('vehicleBrand').setReadOnly(false);
		    				} else {
		    					form.findField('vehicleBrand').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.transDepartmentName)) {
		    					form.findField('transDepartmentCode').setReadOnly(false);
		    				} else {
		    					form.findField('transDepartmentCode').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.groupOrgName)) {
		    					form.findField('groupOrgCode').setReadOnly(false);
		    				} else {
		    					form.findField('groupOrgCode').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.divisionOrgName)) {
		    					form.findField('divisionOrgCode').setReadOnly(false);
		    				} else {
		    					form.findField('divisionOrgCode').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.regionOrgName)) {
		    					form.findField('regionOrgCode').setReadOnly(false);
		    				} else {
		    					form.findField('regionOrgCode').setReadOnly(true);
		    				}
		    				if(Ext.isEmpty(fuelVehicleEntity.departureTypeCode)) {
		    					form.findField('departureTypeCode').setReadOnly(false);
		    				} else {
		    					form.findField('departureTypeCode').setReadOnly(true);
		    				}
	        				
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
		    			}
					});
				}
			}
		}
	},{
		xtype:'commonvehicletypeselector',
		name:'vehicleType',
		//allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleTypeLength'),   //车型
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var vehicleType = field.getValue();
				if(vehicleType === ''){
					form.findField('vehicleType').setValue('');
					return;
				}
				form.findField('vehicleType').setValue(field.rawValue);
			}
		}
	},{
		name:'vehicleBrand',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleBrand'),   //车辆品牌
		columnWidth:.25
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		division:'Y',
		name:'divisionOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.divisionOrgCode'),   //事业部
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var divisionOrg = field.getValue();
				if(divisionOrg === ''){
					form.findField('divisionOrgName').setValue('');
					return;
				}
				form.findField('divisionOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'divisionOrgName',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		name:'transDepartmentCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.transDepartmentCode'),   //车队
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var transDepartment = field.getValue();
				if(transDepartment === ''){
					form.findField('transDepartmentName').setValue('');
					return;
				}
				form.findField('transDepartmentName').setValue(field.rawValue);
			}
		}
	},{
		name:'transDepartmentName',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transTeam:'Y',
		name:'groupOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.groupOrgCode'),   //小组
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var groupOrg = field.getValue();
				if(groupOrg === ''){
					form.findField('groupOrgName').setValue('');
					return;
				}
				form.findField('groupOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'groupOrgName',
		hidden:true
	},{
		xtype:'commonmotorcadeselector',
		isFullFleetOrgFlag : 'Y', //查询全网顶级车队标识
		name:'regionOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.largeDistrictCode'),   //所属大区
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var largeDistrict = field.getValue();
				if(largeDistrict === ''){
					form.findField('regionOrgName').setValue('');
					return;
				}
				form.findField('regionOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'regionOrgName',
		hidden:true
	},{
		xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		value:'CHOISE',
		store:FossDataDictionary.getDataDictionaryStore('ARRIVE_TYPE'),
		name:'departureTypeCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureTypeCode'),   //出车性质
		columnWidth:.25
	}]
});

//定义发车信息的panel
Ext.define('Foss.Management.FuelConsumptionDeparture',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionDeparture.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		allowBlank:true,
		labelWidth: 85,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		name:'id',
		readOnly:true,
		hidden:true
	},{
		name:'vehicleId',
		readOnly:true,
		hidden:true
	},{
		name: 'assemblyNo',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleAssembleNo'),//配载车次号
		//allowBlank: true,
		columnWidth:.25,
		maxLength:50,
		listeners: {
			'blur' : function(field) {
				var vehicleAssembleNo = field.getValue();
				if(vehicleAssembleNo != '' && vehicleAssembleNo != null){
					var form = this.up('form').getForm();
					var array = {fuelConsumptionVo:{fuelConsumptionDto:{assemblyNo:vehicleAssembleNo}}};
					Ext.Ajax.request({
						url:management.realPath('queryAssembleInfo.action'),
						jsonData:array,
						success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var assemblyInfo = json.fuelConsumptionVo.fuelConsumptionDto;
		    				
		    				form.findField('assemblyNo').setValue(assemblyInfo.assemblyNo);
		    				form.findField('deptRegionCode').setValue(assemblyInfo.deptRegionCode);
		    				form.findField('deptRegionName').setValue(assemblyInfo.deptRegionName);
		    				form.findField('arrvRegionCode').setValue(assemblyInfo.arrvRegionCode);
		    				form.findField('arrvRegionName').setValue(assemblyInfo.arrvRegionName);
		    				form.findField('volume').setValue(assemblyInfo.volume);
		    				form.findField('actualLoad').setValue(assemblyInfo.actualLoad);
		    				form.findField('driver1Code').setValue(assemblyInfo.driver1Code);
		    				form.findField('driver1Name').setValue(assemblyInfo.driver1Name);
		    				form.findField('lineName').setValue(assemblyInfo.lineName);
		    				//发车时间
		    				if(assemblyInfo.departureDate != null){
		    					form.findField('departureDate').setValue(Ext.Date.format(new Date(assemblyInfo.departureDate), 'Y-m-d'));
		    				}
		    				//出发部门
		    				var cmbDeptRegionCode = form.findField('deptRegionCode'); //出发站-编辑初始化
		    				cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : assemblyInfo.deptRegionName}});
		    				cmbDeptRegionCode.setValue(assemblyInfo.deptRegionCode);
		    				//到达部门
		    				var cmbArrvRegionCode = form.findField('arrvRegionCode');  //目的站-编辑初始化
		    				cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : assemblyInfo.arrvRegionName}});
		    				cmbArrvRegionCode.setValue(assemblyInfo.arrvRegionCode);
		    				//司机
		    				var driver1 = form.findField('driver1Code');
		    				driver1.getStore().load({params:{'driverVo.driverEntity.empName' : assemblyInfo.driver1Name}});
		    				driver1.setValue(assemblyInfo.driver1Code);
		    				//线路
		    				var lineCode = form.findField('lineCode');
		    				lineCode.getStore().load({params:{'lineVo.lineEntity.lineName' : assemblyInfo.lineName}});
		    				lineCode.setValue(assemblyInfo.lineName);
		    				lineCode.fireEvent('blur',lineCode);
		    				
		    				/*var cmbLineName = form.findField('lineCode');
		    				record.data.lineName = assemblyInfo.lineName.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
		    				record.data.lineCode = record.data.lineCode.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
		    				cmbLineName.setValue(assemblyInfo.lineName);
		    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : assemblyInfo.lineName}});
		    				cmbLineName.setValue(assemblyInfo.lineName);*/
		    				
		    				//加载线路
		    				
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
		    			}
					});
				}
			}
		}
	},{
		xtype: 'datefield',
		name: 'departureDate',
		allowBlank: false,
		fieldId:'Foss_Management_AddFuelConsumption_FuelDepartureDateId',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureDate'),   //发车日期
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth: .25,
		listeners: {
			'change':function(field) {
				var form = this.up('form').getForm();
				var departureDate = form.findField('departureDate').getValue();
				var vehicleNo;
				//新增界面
				if(this.up('form').up('form').getForm().findField('vehicleNo') == undefined) {
					vehicleNo = management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').getValue();
				} else {
					//编辑界面
					vehicleNo = this.up('form').up('form').getForm().findField('vehicleNo').getValue();
				}
				management.queryFuelStandard(form,vehicleNo,departureDate);
			}
		}
	},{
		name:'startKm',
		xtype:'numberfield',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.startKm'),   //出发公里数
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				
				var form = this.up('form').getForm();
				//正则表达式
				var patt= /^\d+(\.\d+)?$/g;
				if(patt.test(field.rawValue)){
					var arriveKm = form.findField('arriveKm').getValue();
					var runKm = Ext.Number.from(arriveKm,0) - Ext.Number.from(field.getValue(),0);
					form.findField('runKm').setValue(runKm);
				}else{
					if(null == field.rawValue || '' == field.rawValue){
						//无处理
					}else{
						form.findField('startKm').setValue(null);
						form.findField('runKm').setValue(null);
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
								management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.startKm') 
							  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示出发公里数为非负数！
						return;
					}
				}

			}
		}
	},{
		name:'arriveKm',
		xtype:'numberfield',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arriveKm'),   //到达公里数
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				//正则表达式
				var patt= /^\d+(\.\d+)?$/g;
				if(patt.test(field.rawValue)){
					
					var startKm = form.findField('startKm').getValue();
					if(startKm >= field.getValue()){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.arriveKmMustGTRunKm'));   //提示到达公里数要大于出发公里数
						return;
					}
					var runKm = Ext.Number.from(field.getValue(),0) - Ext.Number.from(startKm,0);
					form.findField('runKm').setValue(runKm);
					
				}else{
					if(null == field.rawValue || '' == field.rawValue){
						//无处理
					}else{
						form.findField('arriveKm').setValue(null);
						form.findField('runKm').setValue(null);
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
								management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arriveKm') 
							  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示到达公里数为非负数！
						return;
					}
				}

			}
		}
	},{
		xtype : 'dynamicorgcombselector',
		name:'deptRegionCode',
		//allowBlank:true,
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.deptRegionCode'),   //出发站
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				
				var deptRegionCodeToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionCodeToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionCodeToLine.getValue() && '' != deptRegionCodeToLine.getValue() && null != arrvRegionCodeToLine.getValue() && '' != arrvRegionCodeToLine.getValue()){
					
					var lineNameStr = deptRegionCodeToLine.rawValue + '-' + arrvRegionCodeToLine.rawValue;
					var lineCodeStr = deptRegionCodeToLine.getValue() + '-' + arrvRegionCodeToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineCode');
    				cmbLineName.setValue(lineCodeStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.simpleCode' : lineCodeStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.rawValue = lineNameStr;
    					form.findField('lineName').setValue(lineNameStr);
    				}
				}
				form.findField('deptRegionName').setValue(field.rawValue);
				if(null == deptRegionCodeToLine.getValue() || '' == deptRegionCodeToLine.getValue()){
					form.findField('deptRegionName').setValue('');
					form.findField('deptRegionCode').setValue('');
				}
			}
		}
	},{
		name:'deptRegionName',
		//allowBlank:true,
		hidden:true
	},{
		xtype : 'dynamicorgcombselector',
		name:'arrvRegionCode',
		//allowBlank:true,
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arrvRegionCode'),   //目的站
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				
				var deptRegionCodeToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionCodeToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionCodeToLine.getValue() && '' != deptRegionCodeToLine.getValue() && null != arrvRegionCodeToLine.getValue() && '' != arrvRegionCodeToLine.getValue()){
					
					var lineNameStr = deptRegionCodeToLine.rawValue + '-' + arrvRegionCodeToLine.rawValue;
					var lineCodeStr = deptRegionCodeToLine.getValue() + '-' + arrvRegionCodeToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineCode');
    				cmbLineName.setValue(lineCodeStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.simpleCode' : lineCodeStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.rawValue = lineNameStr;
    					form.findField('lineName').setValue(lineNameStr);
    				}
				}
				form.findField('arrvRegionName').setValue(field.rawValue);
				if(null == arrvRegionCodeToLine.getValue() || '' == arrvRegionCodeToLine.getValue()){
					form.findField('arrvRegionName').setValue('');
					form.findField('arrvRegionCode').setValue('');
				}
			}
		}
	},{
		name:'arrvRegionName',
		//allowBlank:true,
		hidden:true
	},{
		xtype:'commonlineselector',
		name:'lineCode',
		//allowBlank:true,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路
		columnWidth:.25,
		maxLength:50,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				form.findField('lineCode').setValue(field.getValue());
				form.findField('lineName').setValue(field.rawValue);
				if(null == field.getValue() || '' == field.getValue()){
					form.findField('lineCode').setValue('');
					form.findField('lineName').setValue('');
				}
			}
		}
	},{
		name:'lineName',
		//allowBlank:true,
		hidden:true
	},{
		xtype:'combobox',
		displayField:'valueName',
		//allowBlank: false,
		valueField:'valueCode',
		value:'CHOISE',
		store:FossDataDictionary.getDataDictionaryStore('DEPARTURE_MODEL'),
		name:'departureMode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureMode'),   //发车模式
		columnWidth:.25
	},{
		name:'actualLoad',
		xtype:'numberfield',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ratedLoad'),   //实际载重(吨)
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				//正则表达式
				var patt= /^\d+(\.\d+)?$/g;
				if(patt.test(field.rawValue)){
					//无处理
				}else{
					if(null == field.rawValue || '' == field.rawValue){
						//无处理
					}else{
						form.findField('actualLoad').setValue(null);
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
								management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.ratedLoad'));  //提示载重为非负数！
						return;
					}
				}
			}
		}
	},{
		name:'volume',
		xtype:'numberfield',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.volume'),   //实际体积
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				//正则表达式
				var patt= /^\d+(\.\d+)?$/g;
				if(patt.test(field.rawValue)){
					//无处理
				}else{
					if(null == field.rawValue || '' == field.rawValue){
						//无处理
					}else{
						form.findField('volume').setValue(null);
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
								management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.volume') 
							  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示到达公里数为非负数！
						return;
					}
				}

			}
		}
	},{
		xtype:'commondriverselector',
		name:'driver1Code',
		allowBlank: false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver1Code'),   //司机1
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				form.findField('driver1Code').setValue(field.getValue());
				form.findField('driver1Name').setValue(field.rawValue);
				if(null == field.getValue() || '' == field.getValue()){
					form.findField('driver1Code').setValue('');
					form.findField('driver1Name').setValue('');
				}
			}
		}
	},{
		name:'driver1Name',
		hidden:true
	},{
		xtype:'commondriverselector',
		name:'driver2Code',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver2Code'),   //司机2
		allowBlank:true,
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				form.findField('driver2Code').setValue(field.getValue());
				form.findField('driver2Name').setValue(field.rawValue);
				if(null == field.getValue() || '' == field.getValue()){
					form.findField('driver2Code').setValue('');
					form.findField('driver2Name').setValue('');
				}
			}
		}
	},{
		name:'driver2Name',
		//allowBlank:true,
		hidden:true
	},{
		name:'runKm',
		//allowBlank:true,
		readOnly:true,
		xtype:'numberfield',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.runKm'),   //行驶公里数
		columnWidth:.25,
		listeners: {
			'change': function(field) {
				//行驶公里数
				var runKm = field.getValue();
				//加油升数
				var fuelQtyTotal = this.up('form').getForm().findField('fuelQtyTotal').getValue();
				if(runKm > 0 && fuelQtyTotal > 0){
					//百公里油耗
					var ohKmFuelConsumption = Ext.Number.from(fuelQtyTotal,0) / Ext.Number.from(runKm,0) * 100;
					ohKmFuelConsumption = Math.round(ohKmFuelConsumption*Math.pow(10,2))/Math.pow(10,2);
					this.up('form').getForm().findField('hdKmFuel').setValue(ohKmFuelConsumption);
				} else {
					this.up('form').getForm().findField('hdKmFuel').setValue(0);
				}
			}
		}
	},{
		name:'fuelStandard',
		readOnly:true,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准
		columnWidth:.25
	},{
		name:'hdKmFuel',
		hidden: true,
		readOnly: true,
		value: 0
    },{
    	name:'fuelQtyTotal',
    	hidden: true,
		readOnly: true,
		value: 0,
		listeners: {
			'change': function(field) {
				//加油升数
				var fuelQtyTotal = field.getValue();
				//行驶公里数
				var runKm = this.up('form').getForm().findField('runKm').getValue();
				if(fuelQtyTotal > 0 && runKm > 0 ){
					var ohKmFuelConsumption = Ext.Number.from(fuelQtyTotal,0) / Ext.Number.from(runKm,0) * 100;
					ohKmFuelConsumption = Math.round(ohKmFuelConsumption*Math.pow(10,2))/Math.pow(10,2);
					this.up('form').getForm().findField('hdKmFuel').setValue(ohKmFuelConsumption);
				} else {
					this.up('form').getForm().findField('hdKmFuel').setValue(0);
				}
			}
		}
    },{
    	name:'averagePrice',
    	hidden: true,
		readOnly: true,
		value: 0	
    },{
		name:'fuelFeeTotal',
		xtype:'textfield',
		readOnly: true,
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFeeTotal'),   //燃油费总计
		columnWidth:.25	
	},{
		name:'roadTollTotal',
		xtype:'textfield',
		readOnly: true,
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadFeeTotal'),   //路桥费总计
		columnWidth:.25
	}]
});

//定义新增发车信息grid
Ext.define('Foss.Management.FuelConsumptionDepartureGrid',{
	extend: 'Ext.grid.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionDeparture.title'),   //发车信息
	frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    width:'100%',
    columns:[{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.operator'),   //操作
    	xtype:'actioncolumn',
		flex: 1.5,
		items:[{
            iconCls: 'deppon_icons_delete',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
            handler: function(grid, rowIndex, colIndex){
            	grid.store.remove(grid.store.data.items[rowIndex]);
            	//发车信息不为空时，车牌号不可编辑
            	if(grid.store.data.length > 0) {
            		management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(true);
            		management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(true);
            	} else {
            		management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(false);
            		management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(false);
            	}
            }
        },{
            iconCls: 'deppon_icons_showdetail',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.info'),   //查看
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
				var detailInfoWindow = management.departureDetailReadWindows;
				var readForm = detailInfoWindow.getEditFuelConsumptionForm();
				readForm.getForm().reset();
				readForm.getForm().loadRecord(record);
				//移除所有的数据重新加载
			     management.fuelDetailRead.getStore().removeAll();
				 management.roadTollDetailRead.getStore().removeAll();
				
				var fuelDetailList = record.data.fuelDetailList;
				var roadTollList = record.data.fuelRoadTollList;
				for(var i = 0 ; i<fuelDetailList.length; i++){
    				detailRecord= new Ext.data.reader.Json(fuelDetailList[i]);
					detailRecord.fuelTime = Ext.Date.format(new Date(detailRecord.fuelTime),'Y-m-d H:i:s');
    				management.fuelDetailRead.getStore().add(detailRecord);
    			}
				for(var i = 0 ; i<roadTollList.length; i++){
					var raodTollRecord= new Ext.data.reader.Json(roadTollList[i]);
					raodTollRecord.roadTime = Ext.Date.format(new Date(raodTollRecord.roadTime),'Y-m-d H:i:s');
					management.roadTollDetailRead.getStore().add(raodTollRecord);
				}
				
				detailInfoWindow.show();
    					
            }
        },{
            iconCls: 'deppon_icons_edit',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.edit'),   //'编辑',
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
				var detailInfoWindow = management.departureInfoWindow;
				var editForm = detailInfoWindow.getEditFuelConsumptionForm();
				editForm.getForm().reset();
				//load公共选择器
				var cmbLargeDistrict = management.fuelConsumptionVehicleInfo.getForm().findField('divisionOrgCode');
				cmbLargeDistrict.getStore().load({params:{'commonOrgVo.division' : record.data.divisionOrgName}});
				cmbLargeDistrict.setValue(record.data.divisionOrgName);
				
				var cmbGroupOrg = management.fuelConsumptionVehicleInfo.getForm().findField('groupOrgCode');
				cmbGroupOrg.getStore().load({params:{'commonOrgVo.name' : record.data.groupOrgName}});
				cmbGroupOrg.setValue(record.data.groupOrgName);
				
				var cmbLargeDistrict = management.fuelConsumptionVehicleInfo.getForm().findField('transDepartmentCode');
				cmbLargeDistrict.getStore().load({params:{'commonOrgVo.transTeam' : record.data.transDepartmentName}});
				cmbLargeDistrict.setValue(record.data.transDepartmentName);
				
				var cmbLargeDistrict = management.fuelConsumptionVehicleInfo.getForm().findField('regionOrgCode');
				cmbLargeDistrict.getStore().load({params:{'commonMotorcadeVo.commonMotorcadeDto.name' : record.data.regionOrgName}});
				cmbLargeDistrict.setValue(record.data.regionOrgName);

				var cmbDeptRegionCode = management.fuelConsumptionDeparture.getForm().findField('deptRegionCode'); //出发站-编辑初始化
				cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.deptRegionName}});
				cmbDeptRegionCode.setValue(record.data.deptRegionCode);
				
				var cmbArrvRegionCode = management.fuelConsumptionDeparture.getForm().findField('arrvRegionCode'); //目的站-编辑初始化
				cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.arrvRegionName}});
				cmbArrvRegionCode.setValue(record.data.arrvRegionCode);

				var cmbLineName = management.fuelConsumptionDeparture.getForm().findField('lineCode');
				record.data.lineName = record.data.lineName.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
				if(record.data.lineCode == undefined) {
					record.data.lineCode = '';
				} else {
					record.data.lineCode = record.data.lineCode.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
				}
				cmbLineName.setValue(record.data.lineName);
				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : record.data.lineName}});
				cmbLineName.setValue(record.data.lineCode);
				
				var cmbDriver1Code = management.fuelConsumptionDeparture.getForm().findField('driver1Code');
				cmbDriver1Code.getStore().load({params:{'driverVo.driverEntity.empName' : record.data.driver1Name}});
				cmbDriver1Code.setValue(record.data.driver1Code);
				
				var cmbDriver2Code = management.fuelConsumptionDeparture.getForm().findField('driver2Code');
				cmbDriver2Code.getStore().load({params:{'driverVo.driverEntity.empName' : record.data.driver2Name}});
				cmbDriver2Code.setValue(record.data.driver1Code);
				//load数据
				editForm.getForm().loadRecord(record);
				management.fuelDetailFuelCard.getStore().removeAll();
				management.roadToll.getStore().removeAll();
				
				var fuelDetailList = record.data.fuelDetailList;
				var roadTollList = record.data.fuelRoadTollList;
				for(var i = 0 ; i<fuelDetailList.length; i++){
					
    				detailRecord = new Ext.data.reader.Json(fuelDetailList[i]);
    				
					detailRecord.fuelTime = Ext.Date.format(new Date(detailRecord.fuelTime),'Y-m-d H:i:s');
					
    				management.fuelDetailFuelCard.getStore().add(detailRecord);
    			}
				
				for(var i = 0 ; i<roadTollList.length; i++){
					
					roadTollRecord= new Ext.data.reader.Json(roadTollList[i]);
					
					roadTollRecord.roadTime = Ext.Date.format(new Date(roadTollRecord.roadTime),'Y-m-d H:i:s');
					
					management.roadToll.getStore().add(roadTollRecord);
				}
				
				detailInfoWindow.show();
            }
        }]
    },{
    	dataIndex: 'id',
    	hidden: true,
    	flex: 1
    },{
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleAssembleNo'),   //配载车次号
		dataIndex: 'assemblyNo' ,
		flex: 1
    },{
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准
		dataIndex: 'fuelStandard' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureDate'),   //发车日期
		dataIndex: 'departureDate',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路
		dataIndex: 'lineCode' ,
		hidden: true,
		flex: 1
    },{
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路名称
		dataIndex: 'lineName' ,
		flex: 1
    },{	
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.startKm'),   //出发公里数
		dataIndex: 'startKm' ,
		flex: 1
	},{	
		header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arriveKm'),   //到达公里数
		dataIndex: 'arriveKm' ,
		flex: 1
	},{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureMode'),   //发车模式
		dataIndex: 'departureMode' ,
		flex: 1,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'DEPARTURE_MODEL');
		}	
    },{
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.deptRegionCode'),   //出发站
		dataIndex: 'deptRegionCode' ,
		hidden:true,
		flex: 1
    },{
    	header:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.deptRegionCode'),   //出发站名称
		dataIndex: 'deptRegionName' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arrvRegionCode'),   //目的站
    	dataIndex: 'arrvRegionCode' ,
    	hidden:true,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arrvRegionCode'),   //目的站
    	dataIndex: 'arrvRegionName' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ratedLoad'),   //实际载重（吨）
		dataIndex: 'actualLoad',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver1Code'),   //司机1
		dataIndex: 'driver1Code',
		hidden: true,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver1Code'),   //司机1姓名
		dataIndex: 'driver1Name',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver2Code'),   //司机2
		dataIndex: 'driver2Code',
		hidden: true,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver2Code'),   //司机2姓名
		dataIndex: 'driver2Name',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.runKm'),   //行驶公里数
		dataIndex: 'runKm',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ohKmFuelConsumption'),   //单次发车百公里油耗
		dataIndex: 'hdKmFuel',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelAmount'),   //单次发车加油升数
		dataIndex: 'fuelQtyTotal',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.averagePrice'),   //单次发车平均加油单价
		dataIndex: 'averagePrice',
		flex: 1
    },/*{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.perroadFeeAmount'),   //单次发车公里路桥费
		dataIndex: 'kmRoadToll',
		flex: 1,
		field: {
			xtype: 'textfield',
			allowBlank:false,
			maxLength:20,
		    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')
		}
    },*/{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFeeTotal'),   //燃油费总计
		dataIndex: 'fuelFeeTotal',
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadFeeTotal'),   //路桥费总计
		dataIndex: 'roadTollTotal',
		flex: 1
    }],
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
		me.store = Ext.create('Foss.Management.FuelConsumptionStore');
		me.plugins = [
			me.getEditor()
		];
		me.tbar = [{
			xtype:'button',
			text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.add'),   //新增
			handler: function(){
				//新增发车信息前，车牌号不能为空
				var vehicleNo = management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').getValue();
				//出车性质，根据出车性质修改必填项
				var departrueType = management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').getValue();
				
				if(Ext.isEmpty(vehicleNo) || Ext.isEmpty(departrueType) || departrueType == 'CHOISE') {
					//提示录入车牌号和出车性质
					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.vehicleNo'));  
					return;
				}
				var detailInfoWindow = management.departureInfoWindow;
				var editForm = detailInfoWindow.getEditFuelConsumptionForm().getForm();
				editForm.reset();
				
				//长途时必填项
				if(departrueType == 'LONG_DISTANCE') {
					//发车模式可编辑
					editForm.findField('departureMode').setReadOnly(false);
					editForm.findField('departureMode').setValue('CHOISE');
					
					editForm.findField('deptRegionCode').allowBlank = false;
					editForm.findField('arrvRegionCode').allowBlank = false;
					editForm.findField('lineCode').allowBlank = false;
					editForm.findField('driver2Code').allowBlank = false;
					
				} else {
					//发车模式不可编辑
					editForm.findField('departureMode').setReadOnly(true);
					editForm.findField('departureMode').setValue('');
					editForm.findField('deptRegionCode').allowBlank = true;
					editForm.findField('arrvRegionCode').allowBlank = true;
					editForm.findField('lineCode').allowBlank = true;
					editForm.findField('driver2Code').allowBlank = true;
				}
				var departureDate = Ext.Date.format(new Date(), 'Y-m-d');
				//根据车牌号和发车日期查询油耗标准
				management.queryFuelStandard(editForm,vehicleNo,departureDate);
				
				management.fuelDetailFuelCard.getStore().removeAll();
				management.roadToll.getStore().removeAll();
				detailInfoWindow.show();
			}
		}];
		me.callParent([cfg]);
	}
});


//根据车牌号和发车信息查询油耗标准
management.queryFuelStandard = function(form,vehicleNo,departureDate){
	var url=management.realPath('queryFuelStandardValue.action');
	if(vehicleNo == null || departureDate == null) {
		return;
	}
	// 构建查询参数
	var queryParams={
						'fuelConsumptionVo.fuelConsumptionDto.vehicleNo':vehicleNo,//车牌号
						'fuelConsumptionVo.fuelConsumptionDto.departureDate':departureDate//发车日期
					};
	//执行查询
	Ext.Ajax.request({
		url : url,
		params:queryParams,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//油耗标准
			var standard = json.fuelConsumptionVo.fuelStandardValue;
			form.findField('fuelStandard').setValue(standard);
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
		}		
		});	
}

//定义加油信息的grid
Ext.define('Foss.Management.FuelDetailFuelCard',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelDetailFuelCard.title'),   //油卡加油信息
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    listeners : {
		cellclick : function(me, td, cellIndex, record, tr,
				rowIndex, e, eOpts) {
			// 单元格为
			if (cellIndex == 10) {
				var payType = record.data.fuelPayType;
				if (payType == 'FUEL_CARD') {
					return true;
				} else {
					// 油卡余额不可编辑
					return false;
				}
			}
		}
    },
    width:'100%',
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
		me.store = Ext.create('Foss.Management.FuelDetailStore');
		me.plugins = [
			me.getEditor()
		];
		me.tbar = [{
			xtype:'button',
			text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.add'),   //新增
			handler: function(){
				var nullNewRow = Ext.create('Foss.Management.FuelDetailModel'),
					edit = me.getEditor();
				edit.cancelEdit();
				me.store.insert(0,nullNewRow );
				edit.startEditByPosition({
					row: 0,
					column: 0
				});
			}
		}];
		me.columns = [{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.operator'),   //操作
	    	xtype:'actioncolumn',
			flex: 0.2,
			items:[{
	            iconCls: 'deppon_icons_delete',
	            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
	            handler: function(grid, rowIndex, colIndex){
	            	grid.store.remove(grid.store.data.items[rowIndex]);
	            	
	            	//重新计算各项数据
	            	if(grid.store.data.items.length > 0) {
	            		var records = grid.store.data.items;
	            		//加油升数合计
	            		var fuelQtyTotal = 0;
	            		//燃油费总计
	            		var fuelFeeTotal = 0;
	            		//平均加油单价
	            		var price = 0;
	            		for(var i = 0;i < records.length;i++) {
	            			fuelQtyTotal += Ext.Number.from(records[i].data.fuelQty,0);
	            			fuelFeeTotal +=  Ext.Number.from(records[i].data.fuelFee,0);
	            		}
	            		if(fuelQtyTotal > 0 && fuelFeeTotal > 0) {
	            			price = Math.round((fuelFeeTotal/fuelQtyTotal)*Math.pow(10,2))/Math.pow(10,2);
	            		} else {
	            			price = 0;
	            		}
	            		
	            		this.up('form').up('form').getForm().findField('fuelQtyTotal').setValue(fuelQtyTotal);
	            		this.up('form').up('form').getForm().findField('fuelFeeTotal').setValue(fuelFeeTotal);
	            		this.up('form').up('form').getForm().findField('averagePrice').setValue(price);
	            	} else {
	            		this.up('form').up('form').getForm().findField('fuelQtyTotal').setValue(0);
	            		this.up('form').up('form').getForm().findField('fuelFeeTotal').setValue(0);
	            		this.up('form').up('form').getForm().findField('averagePrice').setValue(0);
	            	}
	            }
	        }]
	    },{
			dataIndex: 'id' ,
			hidden: true
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelQty'),   //加油升数
			dataIndex: 'fuelQty' ,
			flex: 0.5,
			field: {
				xtype: 'numberfield',
				allowBlank:false,
				listeners: {
					'change': function(field) {
						var rowValue = this.up('grid').getSelectionModel().selected.items[0];
						var unitPrice = rowValue.data.unitPrice;
						var fuelFee = Ext.Number.from(field.getValue(),0) * Ext.Number.from(unitPrice,0);
						fuelFee = Math.round(fuelFee*Math.pow(10,2))/Math.pow(10,2);
						//正则表达式
						var patt= /^\d+(\.\d+)?$/g;
						if(!patt.test(field.rawValue)){
							if(null == field.rawValue || '' == field.rawValue){
								return;
							}else{
								
								field.setValue(null);
								
								Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
										management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelQty') 
									  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示加油升数为非负数！
								return;
							}
						}else{
							rowValue.set('fuelFee',fuelFee);
							rowValue.set('fuelQty',field.getValue());
						}
						
						var fuelCardRecords = this.up('grid').store.data.items;
						
						var totalFuelQty = 0;
						var totalFuelFee = 0;
						for(var i = 0 ; i<fuelCardRecords.length;i++){
							//加油升数合计
							totalFuelQty += Ext.Number.from(fuelCardRecords[i].data.fuelQty,0);
							//燃油费总计
							totalFuelFee += Ext.Number.from(fuelCardRecords[i].data.fuelFee,0);
						}
						
						this.up('form').up('form').getForm().findField('fuelQtyTotal').setValue(totalFuelQty);
						this.up('form').up('form').getForm().findField('fuelFeeTotal').setValue(totalFuelFee);
						
						if(totalFuelQty > 0) {
							var price =Math.round((totalFuelFee/totalFuelQty)*Math.pow(10,2))/Math.pow(10,2);
							this.up('form').up('form').getForm().findField('averagePrice').setValue(price);
						} else {
							this.up('form').up('form').getForm().findField('averagePrice').setValue(0);
						}
					}
				},
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unitPrice'),   //单价
			dataIndex: 'unitPrice' ,
			flex: 0.5,
			field: {
				xtype: 'numberfield',
				allowBlank:false,
				listeners: {
					'change': function(field) {
						var rowValue = this.up('grid').getSelectionModel().selected.items[0];
						var fuelQty = rowValue.data.fuelQty;
						var fuelFee = Ext.Number.from(field.getValue(),0) * Ext.Number.from(fuelQty,0);
						fuelFee = Math.round(fuelFee*Math.pow(10,2))/Math.pow(10,2);
						
						//正则表达式
						var patt= /^\d+(\.\d+)?$/g;
						if(!patt.test(field.rawValue)){
							if(null == field.rawValue || '' == field.rawValue){
								return;
							}else{
								field.setValue(null);
								Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
										management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unitPrice') 
									  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示单价为非负数！
								return;
							}
						}else{
							rowValue.set('fuelFee',fuelFee);
							rowValue.set('unitPrice',field.getValue());
						}
						
						var fuelCardRecords = this.up('grid').store.data.items;
						var totalFuelFee = 0;
						var totalFuelQty = 0;
						for(var i = 0 ; i<fuelCardRecords.length;i++){
							totalFuelFee += Ext.Number.from(fuelCardRecords[i].data.fuelFee,0);
							totalFuelQty += Ext.Number.from(fuelCardRecords[i].data.fuelQty,0);
						}
						var hdKmFuel = totalFuelQty
						
						this.up('form').up('form').getForm().findField('fuelQtyTotal').setValue(totalFuelQty);
						this.up('form').up('form').getForm().findField('fuelFeeTotal').setValue(totalFuelFee);
						
						if(totalFuelQty > 0) {
							var price =Math.round((totalFuelFee/totalFuelQty)*Math.pow(10,2))/Math.pow(10,2);
							this.up('form').up('form').getForm().findField('averagePrice').setValue(price);
						} else {
							this.up('form').up('form').getForm().findField('averagePrice').setValue(0);
						}
						
					}
				},
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFee'),   //油费
			dataIndex: 'fuelFee' ,
			flex: 0.5,
			field: {
				xtype: 'numberfield',
				readOnly:true
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelType'),   //加油方式
			dataIndex: 'fuelType' ,
			flex: 0.8,
			field: {
				xtype: 'combobox',
				allowBlank:false,
				displayField:'valueName',
				valueField:'valueCode',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('FUEL_TYPE')
			},
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_TYPE');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelSupplier'),   //加油供应商
			dataIndex: 'fuelSupplier' ,
			flex: 1,
			field: {
				xtype: 'textfield',
				allowBlank:false,
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelGrade'),   //加油标号
			dataIndex: 'fuelGrade' ,
			flex: 0.8,
			field: {
				xtype: 'combobox',
				allowBlank:false,
				displayField:'valueName',
				valueField:'valueCode',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('FUEL_LABEL')
			},
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_LABEL');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelAddress'),   //加油地点
			dataIndex: 'fuelAddress' ,
			flex: 1,
			field: {
				xtype: 'textfield',
				allowBlank:false,
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollPayment'),   //付款方式 
			dataIndex: 'fuelPayType' ,
			flex: 0.5,
			field: {
				xtype: 'combobox',
				allowBlank:false,
				displayField:'valueName',
				valueField:'valueCode',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('PAY_TYPE')
			},
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PAY_TYPE');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.balance'),   //油卡余额
			dataIndex: 'balance',
			flex: 0.5,
			field: {
				xtype: 'numberfield',
				allowBlank:true,
				minValue: 0,
				readOnly:true,
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength') ,    //长度已超过最大限制!
			    listeners: {
			    	focus :function(cmp, eo, eOpts) {
			    		var rowValue = this.up('grid').getSelectionModel().selected.items[0];
						var fuelPayType = rowValue.data.fuelPayType;
						if (fuelPayType == 'FUEL_CARD') {
							cmp.setReadOnly(false);
						} else {
							cmp.setReadOnly(true);
						}
					}
			    }
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelTime'),   //加油时间
			dataIndex: 'fuelTime',
			flex: 1.3,
			field: {
				xtype:'datetimefield_date97',
		    	allowBlank:false,
		    	id: config.fuelDetailFuelCardFuelTimeId,//'Foss_Management_FuelDetailFuelCard_fuelTime',
				time:true,
				name:'fuelTime',
				value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
				dateConfig: {
					el: config.fuelDetailFuelCardFuelTimeId + '-inputEl',
					dateFmt: 'yyyy-MM-dd hh:mi:ss'
				},
				allowBlank:false
			}
	    }];
		me.callParent([cfg]);
	}
});

//定义路桥费信息的grid
Ext.define('Foss.Management.roadToll',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadToll'),   //路桥费信息
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    width:'100%',
    editor: null,
    getEditor: function(){
    	if(this.editor==null){
    		this.editor = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1,
			});
    	}
    	return this.editor;
    },
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelRoadTollStore');
		me.plugins = [
			me.getEditor()
		];
		me.tbar = [{
			xtype:'button',
			text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.add'),   //新增
			handler: function(){
				var nullNewRow = Ext.create('Foss.Management.FuelRoadTollModel'),
					edit = me.getEditor();
				edit.cancelEdit();
				me.store.insert(0,nullNewRow );
				edit.startEditByPosition({
					row: 0,
					column: 0
				});
			}
		}];
		me.columns = [{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.operator'),   //操作 
	    	xtype:'actioncolumn',
			flex: 0.3,
			items:[{
	            iconCls: 'deppon_icons_delete',
	            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
	            handler: function(grid, rowIndex, colIndex){
	            	grid.store.remove(grid.store.data.items[rowIndex]);
	            	
	            	//重新计算各项数据
	            	if(grid.store.data.items.length > 0) {
	            		var records = grid.store.data.items;
	            		//路桥费合计
	            		var roadTollTotal = 0;
	            		
	            		//公里路桥费
	            		for(var i = 0;i < records.length;i++) {
	            			roadTollTotal += Ext.Number.from(records[i].data.roadToll,0);
	            		}
	            		
	            		this.up('form').up('form').getForm().findField('roadTollTotal').setValue(roadTollTotal);
	            	} else {
	            		this.up('form').up('form').getForm().findField('roadTollTotal').setValue(0);
	            	}
	            }
	        }]
	    },{
			dataIndex: 'id' ,
			hidden: true,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollCount'),   //金额 
			dataIndex: 'roadToll' ,
			flex: 1,
			field: {
				xtype: 'numberfield',
				allowBlank: false,
				minValue: 0,
				listeners: {
					'change': function(field) {
						var rowValue =this.up('grid').getSelectionModel().selected.items[0];
						
						//正则表达式
						var patt= /^\d+(\.\d+)?$/g;
						if(!patt.test(field.rawValue)){
							if(null == field.rawValue || '' == field.rawValue){
								return;
							}else{
								field.setValue(null);
								Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
										management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unitPrice') 
									  + management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.non.negative'));  //提示单价为非负数！
								return;
							}
						}else{
							rowValue.set('roadToll',field.getValue());
						}
						
						var roadTollRecords = this.up('grid').store.data.items;
						var totalRoadToll = 0;
						for(var i = 0 ; i<roadTollRecords.length;i++){
							totalRoadToll += Ext.Number.from(roadTollRecords[i].data.roadToll,0);
						}
						
						this.up('form').up('form').getForm().findField('roadTollTotal').setValue(totalRoadToll);
					}
				},
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollAddress'),   //地点 
			dataIndex: 'roadAddress' ,
			flex: 1,
			field: {
				xtype: 'textfield',
				allowBlank:false,
				maxLength:20,
			    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollTime'),   //时间
			dataIndex: 'roadTime' ,
			flex: 1,
			field: {
				xtype:'datetimefield_date97',
		    	allowBlank:false,
		    	id: config.roadTollTimeId,
		    	//id:'Foss_Management_roadToll_roadTollTime',
				time:true,
				name:'roadTime',
				//editable:false,
				value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
				dateConfig: {
					el: config.roadTollTimeId + '-inputEl',
					dateFmt: 'yyyy-MM-dd hh:mi:ss'
				},
				allowBlank:false
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollPayment'),   //付款方式 
			dataIndex: 'payment' ,
			flex: 1,
			field: {
				xtype: 'combobox',
				allowBlank:false,
				displayField:'valueName',
				valueField:'valueCode',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('PAYMENTMODE')
			},
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PAYMENTMODE');
			}
	    }],
		me.callParent([cfg]);
	}
});

//发车信息保存panel
Ext.define('Foss.Management.DepartureInfoSave',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	items:[{
		name:'notes',
		readOnly:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.notes'),   //备注
		columnWidth:.4
	},{
		readOnly:true,
		columnWidth:.35
	},{
		readOnly:true,
		columnWidth:.8
	},{
		hidden:true,
		name:'id'
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.save'),   //保存
		columnWidth:.1,
		handler: function(){
			var formValues = management.fuelConsumptionAll.getValues();
			//发车信息
			var departureValues = management.fuelConsumptionDeparture.getValues();
			//备注信息
			var remark =  this.up('form').getForm().findField('notes').getValue();
			var btnSave = this;
			
			if(management.fuelConsumptionDeparture.getForm().isValid()){
				if(departureValues.departureTypeCode === 'CHOISE'){
					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.choiseDepartureTypeCode'));  //提示选择出车性质
					return;
				}
				if(!management.fuelConsumptionDeparture.getForm().findField('departureMode').readOnly
						&& departureValues.departureMode === 'CHOISE'){
					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.choiseDepartureMode'));  //提示选择发车模式
					return;
				}
				//加油信息
				var cardStore = management.fuelDetailFuelCard.store.data.items;
				//路桥费信息
				var tollStore = management.roadToll.store.data.items;
				var records = new Array();
				var fuelRecords = new Array();
				var roadRecords = new Array();
				for(var i = 0 ; i < cardStore.length ; i++){
					fuelRecords.push(cardStore[i].data);
	    		}
				for(var i = 0 ; i < tollStore.length ; i++){
					roadRecords.push(tollStore[i].data);
	    		}
				//检验油耗明细字段是否存在空值
				for(var i = 0 ; i < fuelRecords.length ; i++){
					if(fuelRecords[i].fuelQty === "" || fuelRecords[i].fuelQty === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].unitPrice === "" || fuelRecords[i].unitPrice === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelFee === "" || fuelRecords[i].fuelFee === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelType === "" || fuelRecords[i].fuelType === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelSupplier === "" || fuelRecords[i].fuelSupplier === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelGrade === "" || fuelRecords[i].fuelGrade === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelAddress === "" || fuelRecords[i].fuelAddress === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelTime === "" || fuelRecords[i].fuelTime === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelPayType === "FUEL_CARD"){
						if(fuelRecords[i].balance === "" || fuelRecords[i].balance === null){
							Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
							return;
						}
					}
				}
				
				//校验路桥费信息
				for(var i = 0 ; i < roadRecords.length ; i++){
					if(roadRecords[i].roadToll === "" || roadRecords[i].roadToll === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].roadAddress === "" || roadRecords[i].roadAddress === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].roadTime === "" || roadRecords[i].roadTime === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].payment === "" || roadRecords[i].payment === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
				}
				Ext.Msg.show({
            		title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),     //提示
					msg:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.save'),     //车牌号录入后不允许修改，确认录入无误后保存？
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'no'){
							return;
						}else{
							btnSave.setDisabled(true);
							
							var departureGrid =  management.FuelConsumptionDepartureGrid,
							departureStore = departureGrid.store,
							record = Ext.create('Foss.Management.FuelDepartureModel');
							
							//存储发车信息
							record.data.assemblyNo = departureValues.assemblyNo;
							record.data.departureDate = departureValues.departureDate;
							record.data.lineCode = departureValues.lineCode;
							record.data.lineName = departureValues.lineName;
							record.data.startKm = departureValues.startKm;
							record.data.arriveKm = departureValues.arriveKm;
							record.data.departureMode = departureValues.departureMode;
							record.data.deptRegionCode = departureValues.deptRegionCode;
							record.data.deptRegionName = departureValues.deptRegionName;
							record.data.arrvRegionCode = departureValues.arrvRegionCode;
							record.data.arrvRegionName = departureValues.arrvRegionName;
							record.data.actualLoad = departureValues.actualLoad;
							record.data.volume = departureValues.volume;
							record.data.driver1Code = departureValues.driver1Code;
							record.data.driver1Name = departureValues.driver1Name;
							record.data.driver2Code = departureValues.driver2Code;
							record.data.driver2Name = departureValues.driver2Name;
							record.data.runKm = departureValues.runKm;
							record.data.fuelFeeTotal = departureValues.fuelFeeTotal;
							record.data.roadTollTotal = departureValues.roadTollTotal;
							record.data.fuelStandard = departureValues.fuelStandard;
							record.data.remark = remark;
							record.data.fuelDetailList = fuelRecords;
							record.data.fuelRoadTollList = roadRecords;
							record.data.fuelQtyTotal = departureValues.fuelQtyTotal;
							record.data.hdKmFuel = departureValues.hdKmFuel;
							record.data.averagePrice = departureValues.averagePrice;
							
							if(departureValues.id === ""){
								var id = new Date();
								record.data.id = id.getTime().toString();
								departureStore.insert(departureStore.getCount(),record);
							} else {
								var record1 = departureStore.getById(departureValues.id);
								departureStore.remove(record1);
								record.data.id = departureValues.id;
								departureStore.insert(departureStore.getCount(),record);
							}
							
							
							btnSave.setDisabled(false);
							management.departureInfoWindow.close();
							//发车信息有数据时，车牌号不可编辑
							if(departureStore.data.length > 0) {
								management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(true);
								management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(true);
							} else {
								management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(false);
								management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(false);
							}
						}
					}
				});
			}	
		}
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.cancel'),   //取消
		columnWidth:.1,
		handler: function(){
			management.departureInfoWindow.close();
		}
	}]
});

management.fuelDetailFuelCard = Ext.create('Foss.Management.FuelDetailFuelCard',{
	fuelDetailFuelCardFuelTimeId: 'Foss_Management_FuelDetailFuelCard_fuelTime_Add'
});
management.roadToll = Ext.create('Foss.Management.roadToll',{
	roadTollTimeId: 'Foss_Management_roadToll_roadTollTime_Add'
});
management.departureInfoSave = Ext.create('Foss.Management.DepartureInfoSave');
//定义新增时加油信息的panel
Ext.define('Foss.Management.FuelDetailAddAll',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelDetailEditAll.title'),   //加油信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
			management.fuelDetailFuelCard,
			management.roadToll
	       ]
});

management.fuelConsumptionDeparture = Ext.create('Foss.Management.FuelConsumptionDeparture');
management.fuelDetailAddAll = Ext.create('Foss.Management.FuelDetailAddAll');


//定义发车信息新增panel
Ext.define('Foss.Management.DepartureInfoAll',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionDeparture.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
			management.fuelConsumptionDeparture,
			management.fuelDetailAddAll,
			management.departureInfoSave
	       ]
});

//定义保存车辆信息panel
Ext.define('Foss.Management.VehicleInfoSave',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	items:[{
		name:'notes',
		readOnly:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.notes'),   //备注
		columnWidth:.4
	},{
		readOnly:true,
		columnWidth:.35
	},{
		readOnly:true,
		columnWidth:.8
	},{
		hidden:true,
		name:'id'
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.save'),   //保存
		columnWidth:.1,
		handler: function(){
			//车辆信息
			var formValues = management.fuelConsumptionAll.getValues();
			var btnSave = this;
			if(management.fuelConsumptionAll.getForm().isValid()){
				var departureStore = management.FuelConsumptionDepartureGrid.store.data.items;
				var records = new Array();
				for(var i = 0 ; i < departureStore.length ; i++){
	    			records.push(departureStore[i].data);
	    		}
				
				if(records.length < 1){
					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.enterFuelDetail'));  //提示录入油耗明细
					return;
				} 
				//检验油耗明细字段是否存在空值
				/*for(var i = 0 ; i < records.length ; i++){
					if(records[i].fuelQty === "" || records[i].fuelQty === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].unitPrice === "" || records[i].unitPrice === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelFee === "" || records[i].fuelFee === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelType === "" || records[i].fuelType === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelSupplier === "" || records[i].fuelSupplier === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelGrade === "" || records[i].fuelGrade === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelAddress === "" || records[i].fuelAddress === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelTime === "" || records[i].fuelTime === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(records[i].fuelPayType === "FUEL_CARD"){
						if(records[i].balance === "" || records[i].balance === null){
							Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
							return;
						}
					}
				}*/
				Ext.Msg.show({
            		title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),     //提示
					msg:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.sureSave'),     //车牌号录入后不允许修改，确认录入无误后保存？
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'no'){
							return;
						}else{
							
							btnSave.setDisabled(true);		

							var array = {fuelConsumptionVo:{
								'fuelVehicleEntity':formValues,
								'fuelDepartureList':records}
							};
							Ext.Ajax.request({
								url:management.realPath('saveOrUpdateFuelConsumption.action'),
								jsonData:array,
								success : function(response) {
				    				var json = Ext.decode(response.responseText);
				    				Ext.ux.Toast.msg(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'), management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.saveSuccess'), 'ok', 1000);   //提示保存成功
				    				//management.fuelConsumptionIndex.addDetailInfoWindow.close();
				    				management.addVehicleAndDepartureDetailInfoWindow.close();
				    				//management.fuelConsumptionIndex.pagingBar.moveFirst();
				    				management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(false);
				    				management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(false);
				    				management.fuelPagingBar.moveFirst();
				    				btnSave.setDisabled(false);
				    			},
				    			exception : function(response) {
				    				var json = Ext.decode(response.responseText);
				    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
									btnSave.setDisabled(false);
				    			}
							});
						}
					}
				});
			}	
		}
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.cancel'),   //取消
		columnWidth:.1,
		handler: function(){
			management.addVehicleAndDepartureDetailInfoWindow.close();
			management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(false);
			management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(false);
		}
	}]
});

management.vehicleInfoSave = Ext.create('Foss.Management.VehicleInfoSave');
management.fuelConsumptionVehicleInfo = Ext.create('Foss.Management.FuelConsumptionVehicleInfo');
management.FuelConsumptionDepartureGrid = Ext.create('Foss.Management.FuelConsumptionDepartureGrid');

//定义新增车辆信息panel
Ext.define('Foss.Management.FuelConsumptionAddAll',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionEditAll.title')+ '<font color="red" size="1px" style="font-weight : lighter">   注：若需修改车牌号，请先清空发车信息</font>',   //车辆费用信息
	cls: "panelContentNToolbar",
	frame: true,
	items:[
		management.fuelConsumptionVehicleInfo,
		management.FuelConsumptionDepartureGrid,
		management.vehicleInfoSave
		]
});


//查询条件
Ext.define('Foss.Management.QueryFuelConsumption',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	title:management.fuelConsumptionIndex.i18n('foss.management.queryFuelConsumption.title'),   //油耗查询
	frame: true,
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:false
	},
	items:[{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		division:'Y',
		name:'divisionOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.divisionOrgCode'),   //事业部
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var divisionOrg = field.getValue();
				if(divisionOrg === ''){
					form.findField('divisionOrgName').setValue('');
					return;
				}
				form.findField('divisionOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'divisionOrgName',
		hidden:true
	},{
		xtype:'commonmotorcadeselector',
		isFullFleetOrgFlag : 'Y', //查询全网顶级车队标识
		name:'regionOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.largeDistrictCode'),   //所属大区
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var largeDistrict = field.getValue();
				if(largeDistrict === ''){
					form.findField('regionOrgName').setValue('');
					return;
				}
				form.findField('regionOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'regionOrgName',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		name:'transDepartmentCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.transDepartmentCode'),   //车队
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var transDepartment = field.getValue();
				if(transDepartment === ''){
					form.findField('transDepartmentName').setValue('');
					return;
				}
				form.findField('transDepartmentName').setValue(field.rawValue);
			}
		}
	},{
		name:'transDepartmentName',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transTeam:'Y',
		name:'groupOrgCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.groupOrgCode'),   //小组
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var groupOrg = field.getValue();
				if(groupOrg === ''){
					form.findField('groupOrgName').setValue('');
					return;
				}
				form.findField('groupOrgName').setValue(field.rawValue);
			}
		}
	},{
		name:'groupOrgName',
		hidden:true
	},{
		xtype:'commonlineselector',
		name:'lineCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路
		columnWidth:.25
	},{
		xtype:'commondriverselector',
		name:'driverCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driverCode'),   //司机姓名
		columnWidth:.25
	},{
		xtype:'commontruckselector',
		name:'vehicleNo',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		columnWidth:.25
	},{
		xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('ARRIVE_TYPE'),
		value:'CHOISE',
		name:'departureTypeCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureTypeCode'),   //出车性质
		columnWidth:.25
	},{
		xtype: 'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('FUEL_TYPE'),
		name:'fuelType',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelType'),   //加油方式
		columnWidth:.25
	},{
		xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('PAY_TYPE'),
		value:'CHOISE',
		name:'fuelPayType',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelPayType'),   //加油付款类型
		columnWidth:.25
	},{
		xtype: 'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore('FUEL_LABEL'),
		name:'fuelGrade',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelGrade'),   //加油标号
		columnWidth:.25
	},{
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelDate'),   //加油日期
		fieldId:'Foss_Management_QueryFuelConsumption_FuelFuelDateId',
		xtype: 'datefield',
		name: 'fuelDate',
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth: .25
	},{
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.createTime'),   //创建时间
		fieldId:'Foss_Management_QueryFuelConsumption_FuelCreateDateId',
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fromName: 'beginDate',
		dateRange:31, //时间跨度不能大于一个月
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
        disallowBlank: true,
		columnWidth: .5
	},{
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureDate'),   //发车日期
		fieldId:'Foss_Management_QueryFuelConsumption_FuelDepartureDateId',
		xtype: 'datefield',
		name: 'departureDate',
		value: Ext.Date.format(new Date(), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth: .25
	},{
		readOnly:true,
		columnWidth: .25
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.reset'),   //重置
		columnWidth: .1,
		handler: function(){
			var form = this.up('form').getForm();
			form.reset();
			form.findField('beginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
			form.findField('endDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		readOnly:true,
		columnWidth: .7
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.search'),   //查询
		cls:'yellow_button',
		disabled:management.fuelConsumptionIndex.isPermission('management/queryFuelConsumptionButton')?false:true,
		hidden:management.fuelConsumptionIndex.isPermission('management/queryFuelConsumptionButton')?false:true,
		columnWidth: .1,
		handler: function(){
			var form = this.up('form').getForm();
			if(form.isValid()) {
				var data = {fuelConsumptionVo: {transDepartment:""}};
				var queryParams = form.getValues();
				
				if(Ext.isEmpty(queryParams.divisionOrgCode))
				{
					queryParams.divisionOrgName='';
				}
				if(Ext.isEmpty(queryParams.regionOrgCode))
				{
					queryParams.regionOrgName='';
				}
				if(Ext.isEmpty(queryParams.transDepartmentCode))
				{
					queryParams.transDepartmentName='';
				}
				if(Ext.isEmpty(queryParams.groupOrgCode))
				{
					queryParams.groupOrgName='';
				}
				//判断所在部门是否车队组织
				Ext.Ajax.request({
					url:management.realPath('hasTransDepartment.action'),
	    			jsonData:data,
	    			success : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				
	    				var isTransDepartment = json.fuelConsumptionVo.transDepartment;
	    				if(isTransDepartment != 'YES') {
	    					//事业部为空且大区、车队、小组、线路、司机、车牌号同时为空时给出提示
	    					if(Ext.isEmpty(queryParams.divisionOrgName)
	      						   && (Ext.isEmpty(queryParams.regionOrgName) && Ext.isEmpty(queryParams.transDepartmentName) 
	          								&& Ext.isEmpty(queryParams.groupOrgName)	&& Ext.isEmpty(queryParams.lineCode)
	          								&& Ext.isEmpty(queryParams.driverCode) && Ext.isEmpty(queryParams.vehicleNo))) {
	    							//非车队组织要求输入事业部
	    							Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
	    									management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.division')); 
	    							return;
	    					}
	    				}
	    				management.fuelPagingBar.moveFirst();
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.failure'),json.message);
	    			}
	    		});
			}
		}
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.export'),   //导出
		cls:'yellow_button',
		disabled:management.fuelConsumptionIndex.isPermission('management/fuelConsumptionExportExcelButton')?false:true,
		hidden:management.fuelConsumptionIndex.isPermission('management/fuelConsumptionExportExcelButton')?false:true,
		columnWidth: .1,
		handler: function(){
			var form = this.up('form').getForm();
			if(form.isValid()) {
				var data = {fuelConsumptionVo: {transDepartment:""}};
				var queryParams = form.getValues();
				
				if(Ext.isEmpty(queryParams.divisionOrgCode))
				{
					queryParams.divisionOrgName='';
				}
				if(Ext.isEmpty(queryParams.regionOrgCode))
				{
					queryParams.regionOrgName='';
				}
				if(Ext.isEmpty(queryParams.transDepartmentCode))
				{
					queryParams.transDepartmentName='';
				}
				if(Ext.isEmpty(queryParams.groupOrgCode))
				{
					queryParams.groupOrgName='';
				}
				var me = this;
				//判断所在部门是否车队组织
				Ext.Ajax.request({
					url:management.realPath('hasTransDepartment.action'),
	    			jsonData:data,
	    			success : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				var isTransDepartment = json.fuelConsumptionVo.transDepartment;
	    				if(isTransDepartment != 'YES') {
	    					//事业部为空时给出提示
	    					if(Ext.isEmpty(queryParams.divisionOrgName)
	     						   && (Ext.isEmpty(queryParams.regionOrgName) && Ext.isEmpty(queryParams.transDepartmentName) 
	       								&& Ext.isEmpty(queryParams.groupOrgName)	&& Ext.isEmpty(queryParams.lineCode)
	       								&& Ext.isEmpty(queryParams.driverCode) && Ext.isEmpty(queryParams.vehicleNo))) {
		    					//非车队组织要求输入事业部
	    						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),
	    								management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.division'));
		    					return;
	    					}
	    				}
	    				
	    				if(queryParams.departureTypeCode === 'CHOISE'){
	    					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.choiseDepartureTypeCode'));
	    					return;
	    				}
	    				
	    				if(queryParams.fuelPayType === 'CHOISE'){
	    					queryParams.fuelPayType = "";
	    				}
	    				
	    				if(!Ext.fly('downloadAttachFileForm')){
	    				    var frm = document.createElement('form');
	    				    frm.id = 'downloadAttachFileForm';
	    				    frm.style.display = 'none';
	    				    document.body.appendChild(frm);
	    				}
	    				
	    				var params = {
	    					'fuelConsumptionVo.fuelConsumptionDto.vehicleNo' : queryParams.vehicleNo,
	    					'fuelConsumptionVo.fuelConsumptionDto.divisionOrgName' : queryParams.divisionOrgName,
	    					'fuelConsumptionVo.fuelConsumptionDto.regionOrgName' : queryParams.regionOrgName,
	    					'fuelConsumptionVo.fuelConsumptionDto.transDepartmentName' : queryParams.transDepartmentName,
	    					'fuelConsumptionVo.fuelConsumptionDto.groupOrgName' :queryParams.groupOrgName,
	    					'fuelConsumptionVo.fuelConsumptionDto.lineCode' :queryParams.lineCode,
	    					'fuelConsumptionVo.fuelConsumptionDto.driverCode' :queryParams.driverCode,
	    					'fuelConsumptionVo.fuelConsumptionDto.departureTypeCode' :queryParams.departureTypeCode,
	    					'fuelConsumptionVo.fuelConsumptionDto.fuelType' :queryParams.fuelType,
	    					'fuelConsumptionVo.fuelConsumptionDto.fuelPayType' :queryParams.fuelPayType,
	    					'fuelConsumptionVo.fuelConsumptionDto.fuelGrade' :queryParams.fuelGrade,
	    					'fuelConsumptionVo.fuelConsumptionDto.beginDate' :queryParams.beginDate,
	    					'fuelConsumptionVo.fuelConsumptionDto.endDate' :queryParams.endDate
	    				};
	    				
	    				//判断是否有数据
	    				/*if(management.resultFuelConsumption.store.data.length==0){
	    					Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作！');
	    					return false;
	    				}*/
	    				
	    				Ext.Msg.confirm('温馨提示','确定要导出数据?',function(btn,text){
	    					if('yes' == btn){
	    						//导出Ajax请求
	    						Ext.Ajax.request({
	    							url:management.realPath('exportSynchronized.action'), 
	    							params:params,
	    							//jsonData:array,
	    							method:'post',
	    							success:function(response){
	    								var result = Ext.JSON.decode(response.responseText);
	    								me.disable();
	    								if(result.syncExport){
	    									
	    									//创建一个form
	    									if(!Ext.fly('downloadAttachFileForm')){
	    										var frm = document.createElement('form');
	    										frm.id = 'exportmvrAfiDetailForm';
	    										frm.style.display = 'none';
	    										document.body.appendChild(frm);
	    									}
	    									
	    									//导出Ajax请求
	    									Ext.Ajax.request({
	    										url:management.realPath('exportExcelSync.action'), 
	    										form: Ext.fly('downloadAttachFileForm'),
	    										params:params,
	    										//jsonData:array,
	    										method:'post',
	    										isUpload: true,
	    										timeout:3*60*1000,
	    										success:function(response){
	    											var result = Ext.decode(response.responseText);
	    									    	//如果异常信息有值，则弹出提示
	    									    	if(!Ext.isEmpty(result.errorMessage)){
	    									    		Ext.Msg.alert('温馨提示',result.errorMessage);
	    									    		return false;
	    									    	}
	    											Ext.ux.Toast.msg('温馨提示','导出成功！', 'success', 1000);
	    										},
	    									    failure : function(response){
	    											Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
	    									    }
	    									});
	    									me.enable();
	    								}else{
	    									
	    									Ext.Msg.confirm('温馨提示','导出数据量比较大，系统将自动转为后台导出',function(btn){
	    										if(btn == 'yes'){
	    											//导出Ajax请求
	    											Ext.Ajax.request({
	    												url:management.realPath('exportExcelAsyn.action'), 
	    												params:params,
	    												//jsonData:array,
	    												method:'post',
	    												timeout:10*60*1000,
	    												success:function(response){
	    													var result = Ext.decode(response.responseText);
	    											    	//如果异常信息有值，则弹出提示
	    											    	if(!Ext.isEmpty(result.errorMessage)){
	    											    		Ext.Msg.alert('温馨提示',result.errorMessage);
	    											    		return false;
	    											    	}
	    											    	me.enable();
	    													Ext.ux.Toast.msg('温馨提示','异步导出任务提交成功，稍后请到【运作基础数据->Excel文件下载】功能界面中下载', 'success', 10000);
	    												},
	    											    failure : function(response){
	    											    	me.enable();
	    													Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
	    											    }
	    											});
	    										}else{
	    											me.enable();
	    										}
	    									});
	    								}
	    							},
	    						    failure : function(response){
	    								Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
	    						    }
	    					    });
	    						
	    					}
	    				});
	    			},
	    			exception : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.failure'),json.message);
	    			}
	    		});
			 }	
		}
	}]
});

//查询时加油明细二级panel
Ext.define('Foss.Management.FuelDetailGrid',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelDetailFuelCard.title'),//加油信息
	frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns: [{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelQty'),   //加油升数 
		dataIndex: 'fuelQty' ,
		flex: 0.5
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unitPrice'),   //单价 
		dataIndex: 'unitPrice' ,
		flex: 0.5
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFee'),   //油费 
		dataIndex: 'fuelFee' ,
		flex: 0.5
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelType'),   //加油方式 
		dataIndex: 'fuelType' ,
		flex: 0.8,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_TYPE');
		}
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelSupplier'),   //加油供应商 
		dataIndex: 'fuelSupplier' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelGrade'),   //加油标号 
		dataIndex: 'fuelGrade' ,
		flex: 0.8,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_LABEL');
		}
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelAddress'),   //加油地点 
		dataIndex: 'fuelAddress' ,
		flex: 1
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelPayType'),   //付款方式 
		dataIndex: 'fuelPayType' ,
		flex: 0.5,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'PAY_TYPE');
		}
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.balance'),   //油卡余额 
		dataIndex: 'balance',
		flex: 0.5
    },{
    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelTime'),   //时间 
		dataIndex: 'fuelTime' ,
		flex: 1.3
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelDetailStore');
		me.callParent([ cfg ]);
	}
});

//查询时路桥费二级grid
Ext.define('Foss.Management.roadTollGrid',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadToll'),//路桥信息
	frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    columns: [{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollCount'),   //金额
			dataIndex: 'roadToll' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollAddress'),   //地点
			dataIndex: 'roadAddress' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollTime'),   //时间 
			dataIndex: 'roadTime' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollPayment'),   //付款方式 
			dataIndex: 'payment' ,
			flex: 1,
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PAYMENTMODE');
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
	    }
    ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelRoadTollStore');
		me.callParent([ cfg ]);
	}
});

//定义查询时加油和路桥的二级grid
Ext.define('Foss.Management.FuelAndRoadTollDetailRead',{
	extend: 'Ext.form.Panel',
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	bindData : function(record){
		var departureId = record.get('id');
		var items = this.items.items;
		for(var i=0;i<items.length;i++) {
				var recordsMap = items[i].store.load({
					params : {
						'fuelConsumptionVo.fuelConsumptionDto.id' : departureId
					}
				});
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.Management.FuelDetailGrid'),
			Ext.create('Foss.Management.roadTollGrid')
		];
   		me.callParent([ cfg ]); 		
	}
});

//展示查询的结果
Ext.define('Foss.Management.ResultFuelConsumption',{
	extend:'Ext.grid.Panel',
    title:management.fuelConsumptionIndex.i18n('foss.management.resultFuelConsumption.title'),   //档案列表
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
 // 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.Management.FuelAndRoadTollDetailRead'
	}],
    columns: [{
    	xtype:'actioncolumn',
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.operator'),   //操作
        flex : 1.2,
        items: [{
            iconCls: 'deppon_icons_delete',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
            hidden:management.fuelConsumptionIndex.isPermission('management/deleteFuelConsumptionButton')?false:true,
            handler: function(grid, rowIndex, colIndex) {
            	Ext.Msg.show({
            		title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.delete'),   //删除
					msg:management.fuelConsumptionIndex.i18n('foss.management.fuelDetailFuelCard.msg.confirmDelete'),   //确认删除这条记录吗?
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'yes'){
							var id = grid.getStore().getAt(rowIndex).data.id;
							var vehicleId = grid.getStore().getAt(rowIndex).data.vehicleId;
							var array = {fuelConsumptionVo: {fuelConsumptionDto :{id:id,vehicleId:vehicleId}}};
							Ext.Ajax.request({
								url:management.realPath('deleteFuelConsumption.action'),
			        			jsonData:array,
			        			success : function(response) {
			        				var json = Ext.decode(response.responseText);
			        				grid.store.load();
									Ext.ux.Toast.msg(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'), management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.deleteSuccess'), 'ok', 1000);  //提示删除成功
			        			},
			        			exception : function(response) {
			        				var json = Ext.decode(response.responseText);
			        				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.failure'),json.message);
			        			}
			        		});
						}
					}
            	});
            }
        },{
            iconCls: 'deppon_icons_showdetail',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.info'),   //查看
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	var id = {fuelConsumptionVo : {fuelConsumptionDto:{id:record.get('id')}}};
            	Ext.Ajax.request({
    				url:management.realPath('loadFuelConsumption.action'),
    				jsonData:id,
    				success : function(response) {
        				var json = Ext.decode(response.responseText);
        				var detailInfoWindow = management.readDetailInfoWindow;
        				var readForm = detailInfoWindow.getReadFuelConsumptionForm();
        				readForm.getForm().reset();
        				record.date = json.fuelConsumptionVo.fuelConsumptionEntity;
        				readForm.getForm().loadRecord(record);
        				//移除所有的数据重新加载
        				management.fuelDetailFuelCardRead.getStore().removeAll();
        				management.roadTollRead.getStore().removeAll();

        				var fuelDetailList = json.fuelConsumptionVo.fuelDetailList;
        				var roadTollList = json.fuelConsumptionVo.fuelRoadTollList;
        				
        				for(var i = 0 ; i<fuelDetailList.length; i++){
            				detailRecord= new Ext.data.reader.Json(fuelDetailList[i]);
        					//detailRecord.fuelTime = Ext.Date.format(new Date(detailRecord.fuelTime),'Y-m-d H:i:s');
        					//加载加油费数据
            				management.fuelDetailFuelCardRead.getStore().add(detailRecord);
            			}
        				
        				for(var i = 0 ; i<roadTollList.length; i++){
            				roadTollRecord= new Ext.data.reader.Json(roadTollList[i]);
            				//roadTollRecord.roadTime = Ext.Date.format(new Date(roadTollRecord.roadTime),'Y-m-d H:i:s');
        					//加载加路桥费数据
            				management.roadTollRead.getStore().add(roadTollRecord);
            			}
        				
        				var vehicleNo = record.data.vehicleNo;
        				var departureDate = record.data.departureDate;
        				//查询油耗标准值
        				management.queryFuelStandard(readForm.getForm(),vehicleNo,departureDate);
        				
        				detailInfoWindow.show();
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
        			}
    			});
            }
        },{
            iconCls: 'deppon_icons_edit',
            tooltip: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.edit'),   //'编辑',
            hidden: !management.fuelConsumptionIndex.isPermission('management/loadFuelConsumptionButton'), //油耗 - 编辑
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	var array = {fuelConsumptionVo : {fuelConsumptionDto:{id:record.get('id')}}};
            	Ext.Ajax.request({
    				url:management.realPath('loadFuelConsumption.action'),
    				jsonData:array,
    				success : function(response) {
        				var json = Ext.decode(response.responseText);
        				var detailInfoWindow = management.editDetailInfoWindow;
        				var editForm = detailInfoWindow.getEditFuelConsumptionForm();
        				editForm.getForm().reset();
        				
        				//load公共选择器
        				var cmbLargeDistrict = management.fuelConsumptionVehicleInfoEdit.getForm().findField('divisionOrgCode');
        				cmbLargeDistrict.getStore().load({params:{'commonOrgVo.division' : record.data.divisionOrgName}});
        				cmbLargeDistrict.setValue(record.data.divisionOrgName);
        				
        				var cmbGroupOrg = management.fuelConsumptionVehicleInfoEdit.getForm().findField('groupOrgCode');
        				cmbGroupOrg.getStore().load({params:{'commonOrgVo.name' : record.data.groupOrgName}});
        				cmbGroupOrg.setValue(record.data.groupOrgName);
        				
        				var cmbLargeDistrict = management.fuelConsumptionVehicleInfoEdit.getForm().findField('transDepartmentCode');
        				cmbLargeDistrict.getStore().load({params:{'commonOrgVo.transTeam' : record.data.transDepartmentName}});
        				cmbLargeDistrict.setValue(record.data.transDepartmentName);
        				
        				var cmbLargeDistrict = management.fuelConsumptionVehicleInfoEdit.getForm().findField('regionOrgCode');
        				cmbLargeDistrict.getStore().load({params:{'commonMotorcadeVo.commonMotorcadeDto.name' : record.data.regionOrgName}});
        				cmbLargeDistrict.setValue(record.data.regionOrgName);

        				var cmbDeptRegionCode = management.fuelConsumptionDepartureEdit.getForm().findField('deptRegionCode'); //出发站-编辑初始化
        				cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.deptRegionName}});
        				cmbDeptRegionCode.setValue(record.data.deptRegionCode);
        				
        				var cmbArrvRegionCode = management.fuelConsumptionDepartureEdit.getForm().findField('arrvRegionCode'); //目的站-编辑初始化
        				cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.arrvRegionName}});
        				cmbArrvRegionCode.setValue(record.data.arrvRegionCode);

        				var cmbLineName = management.fuelConsumptionDepartureEdit.getForm().findField('lineCode');
        				record.data.lineName = record.data.lineName.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
        				record.data.lineCode = record.data.lineCode.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
        				cmbLineName.setValue(record.data.lineName);
        				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : record.data.lineName}});
        				cmbLineName.setValue(record.data.lineCode);
        				
        				var cmbDriver1Code = management.fuelConsumptionDepartureEdit.getForm().findField('driver1Code');
        				cmbDriver1Code.getStore().load({params:{'driverVo.driverEntity.empName' : record.data.driver1Name}});
        				cmbDriver1Code.setValue(record.data.driver1Code);
        				
        				var cmbDriver2Code = management.fuelConsumptionDepartureEdit.getForm().findField('driver2Code');
        				cmbDriver2Code.getStore().load({params:{'driverVo.driverEntity.empName' : record.data.driver2Name}});
        				cmbDriver2Code.setValue(record.data.driver1Code);
        				//load数据
        				editForm.getForm().loadRecord(record);
        				
        				//编辑时，若发车模式为请选择，则设为不可编辑
        				if(record.data.departureMode == 'CHOISE') {
        					management.fuelConsumptionDepartureEdit.getForm().findField('departureMode').setReadOnly(true);
        				} else {
        					management.fuelConsumptionDepartureEdit.getForm().findField('departureMode').setReadOnly(false);
        				}
        				
        				management.fuelDetailFuelCardEdit.getStore().removeAll();
        				management.roadTollEdit.getStore().removeAll();
        				
        				var fuelDetailList = json.fuelConsumptionVo.fuelDetailList;
        				var roadTollList = json.fuelConsumptionVo.fuelRoadTollList;
        				
        				for(var i = 0 ; i<fuelDetailList.length; i++){
            				detailRecord= new Ext.data.reader.Json(fuelDetailList[i]);
        					detailRecord.fuelTime = Ext.Date.format(new Date(detailRecord.fuelTime),'Y-m-d H:i:s');
        					//加载加油费数据
            				management.fuelDetailFuelCardEdit.getStore().add(detailRecord);
            			}
        				
        				for(var i = 0 ; i<roadTollList.length; i++){
            				roadTollRecord= new Ext.data.reader.Json(roadTollList[i]);
            				roadTollRecord.roadTime = Ext.Date.format(new Date(roadTollRecord.roadTime),'Y-m-d H:i:s');
        					//加载加路桥费数据
            				management.roadTollEdit.getStore().add(roadTollRecord);
            			}
        				var vehicleNo = record.data.vehicleNo;
        				var departureDate = record.data.departureDate;
        				//查询油耗标准值
        				management.queryFuelStandard(editForm.getForm(),vehicleNo,departureDate);
        				
        				detailInfoWindow.show();
        				//编辑界面车辆信息不可编辑
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('vehicleNo').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('vehicleType').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('vehicleBrand').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('divisionOrgCode').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('transDepartmentCode').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('groupOrgCode').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('regionOrgCode').setReadOnly(true);
        				management.fuelConsumptionVehicleInfoEdit.getForm().findField('departureTypeCode').setReadOnly(true);
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
        			}
    			});
            }
        }]
    },{
		dataIndex: 'id' ,
		hidden: true,
		flex: 1 
    },{
		dataIndex: 'vehicleId' ,
		hidden: true,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号 
		dataIndex: 'vehicleNo' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.label.vehicleAssembleNo'),   //配载车次号 
		dataIndex: 'assemblyNo' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准 
		dataIndex: 'fuelStandard' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路 
    	xtype : 'ellipsiscolumn',
		dataIndex: 'lineName' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ratedLoad'),   //载重 
		dataIndex: 'actualLoad' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureMode'),   //发车模式 
		dataIndex: 'departureMode' ,
		flex: 1 ,
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'DEPARTURE_MODEL');
		}
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.runKm'),   //行驶公里数 
		dataIndex: 'runKm' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ohKmFuelConsumption'),   //单次发车百公里油耗 
		dataIndex: 'hdKmFuel' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelQty'),   //单次发车加油升数 
		dataIndex: 'fuelQtyTotal' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.averagePrice'),   //单次发车平均加油单价
		dataIndex: 'averagePrice' ,
		flex: 1 
    },/*{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFee'),   //单次发车公里路桥费
		dataIndex: 'kmRoadToll' ,
		flex: 1 
    },*/{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver1Code'),   //司机1 
		dataIndex: 'driver1Name' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver2Code'),   //司机2 
		dataIndex: 'driver2Name' ,
		flex: 1 
    },/*{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelDate'),   //加油日期 
		dataIndex: 'fuelDate' ,
		flex: 1 
    },*/{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFeeTotal'),   //燃油费总计
		dataIndex: 'fuelFeeTotal' ,
		flex: 1 
    },{
		header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadToll'),   //路桥费总计
		dataIndex: 'roadTollTotal' ,
		flex: 1 
    },{
		dataIndex: 'notes' , //备注
		hidden: true,
		flex: 1 
    }],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelConsumptionStore');
		me.tbar = [{
			xtype:'button',
			text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.add'),   //新增
			hidden: !management.fuelConsumptionIndex.isPermission('management/saveOrUpdateFuelConsumptionButton'), //油耗 - 新增
			handler: function(){
				var detailInfoWindow = management.addVehicleAndDepartureDetailInfoWindow;
				
				var editForm = detailInfoWindow.getEditFuelConsumptionForm();
				editForm.getForm().reset();
				management.FuelConsumptionDepartureGrid.getStore().removeAll();
				detailInfoWindow.show();
				management.fuelConsumptionVehicleInfo.getForm().findField('vehicleNo').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('vehicleType').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('vehicleBrand').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('divisionOrgCode').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('transDepartmentCode').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('groupOrgCode').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('regionOrgCode').setReadOnly(false);
				management.fuelConsumptionVehicleInfo.getForm().findField('departureTypeCode').setReadOnly(false);
			}
		},'->',{
				xtype : 'button',
				text : management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.import'),//'导入车辆油耗标准'
				hidden: !management.fuelConsumptionIndex.isPermission('management/importFuelConsumptionButton'), 
				handler : function(){
					addTab('T_management-fuelStandardIndex',
							management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.import'),
							management.realPath('importFuelStandard.action'));
					}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.fuelPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//定义查看车辆信息的panel
Ext.define('Foss.Management.FuelConsumptionVehicleInfoRead',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionVehicleInfo.title'),   //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true
	},
	layout:'column',
	items:[{
		xtype:'commontruckselector',
		name:'vehicleNo',
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleNo'),   //车牌号
		columnWidth:.25
	},{
		name:'vehicleType',
		allowBlank:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleTypeLength'),   //车型
		columnWidth:.25
	},{
		name:'vehicleBrand',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleBrand'),   //车辆品牌
		columnWidth:.25
	},{
		name:'divisionOrgName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.divisionOrgCode'),   //事业部
		columnWidth:.25
	},{
		name:'transDepartmentName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.transDepartmentCode'),   //车队
		columnWidth:.25
	},{
		name:'groupOrgName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.groupOrgCode'),   //小组
		columnWidth:.25
	},{
		name:'regionOrgName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.largeDistrictCode'),   //所属大区
		columnWidth:.25
	},{
		xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		value:'CHOISE',
		store:FossDataDictionary.getDataDictionaryStore('ARRIVE_TYPE'),
		name:'departureTypeCode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureTypeCode'),   //出车性质
		columnWidth:.25
	}]
});

//定义查看发车信息的panel
Ext.define('Foss.Management.FuelConsumptionDepartureRead',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.fuelConsumptionDeparture.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		readOnly:true,
		labelWidth: 85
	},
	layout:'column',
	items:[{
		name: 'assemblyNo',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.vehicleAssembleNo'),//配载车次号
		columnWidth:.25
	},{
		name: 'departureDate',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureDate'),   //发车日期
		columnWidth: .25
	},{
		name:'startKm',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.startKm'),   //出发公里数
		columnWidth:.25
	},{
		name:'arriveKm',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arriveKm'),   //到达公里数
		columnWidth:.25
	},{
		name:'deptRegionName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.deptRegionCode'),   //出发站
		columnWidth:.25
	},/*{
		name:'deptRegionName',
		allowBlank:true,
		hidden:true
	},*/{
		name:'arrvRegionName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.arrvRegionCode'),   //目的站
		columnWidth:.25
	},/*{
		name:'arrvRegionName',
		allowBlank:true,
		hidden:true
	},*/{
		name:'lineName',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.lineCode'),   //线路
		columnWidth:.25
	},{
		xtype:'combobox',
		displayField:'valueName',
		valueField:'valueCode',
		value:'CHOISE',
		store:FossDataDictionary.getDataDictionaryStore('DEPARTURE_MODEL'),
		name:'departureMode',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.departureMode'),   //发车模式
		columnWidth:.25
	},{
		name:'actualLoad',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ratedLoad'),   //实际载重(吨)
		columnWidth:.25
	},{
		name:'volume',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.volume'),   //实际体积
		columnWidth:.25
	},{
		name:'driver1Name',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver1Code'),   //司机1
		columnWidth:.25
	},{
		name:'driver2Name',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.driver2Code'),   //司机2
		columnWidth:.25
	},{
		name:'runKm',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.runKm'),   //行驶公里数
		columnWidth:.25,
	},{
		name:'fuelStandard',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.standardConsumption'),   //油耗标准
		columnWidth:.25
	},{
		name:'fuelFeeTotal',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFeeTotal'),   //燃油费总计
		columnWidth:.25	
	},{
		name:'roadTollTotal',
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadFeeTotal'),   //路桥费总计
		columnWidth:.25	
	}]
});

//定义查看油卡加油信息的grid
Ext.define('Foss.management.fuelDetailFuelCardRead',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelDetailFuelCard.title'),   //油卡加油信息
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    width:'100%',
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelDetailStore');
	    me.columns = [{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelQty'),   //加油升数 
			dataIndex: 'fuelQty' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unitPrice'),   //单价 
			dataIndex: 'unitPrice' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFee'),   //油费 
			dataIndex: 'fuelFee' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelType'),   //加油方式 
			dataIndex: 'fuelType' ,
			flex: 1,
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_TYPE');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelSupplier'),   //加油供应商 
			dataIndex: 'fuelSupplier' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelGrade'),   //加油标号 
			dataIndex: 'fuelGrade' ,
			flex: 1,
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'FUEL_LABEL');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelAddress'),   //加油地点 
			dataIndex: 'fuelAddress' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelPayType'),   //付款方式 
			dataIndex: 'fuelPayType' ,
			flex: 1,
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PAY_TYPE');
			}
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.balance'),   //油卡余额 
			dataIndex: 'balance',
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelTime'),   //时间 
			dataIndex: 'fuelTime' ,
			type: 'datecolumn',
			format: 'Y-m-d H:i:s',
			flex: 1
	    }];
		me.callParent([cfg]);
	}
});

//定义查看路桥费的grid
Ext.define('Foss.Management.roadTollRead',{
	extend:'Ext.grid.Panel',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadToll'),   //路桥费信息
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	collapsible: true,
    animCollapse: true,
    width:'100%',
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.FuelRoadTollStore');
		me.columns = [{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollCount'),   //金额
			dataIndex: 'roadToll' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollAddress'),   //地点
			dataIndex: 'roadAddress' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollTime'),   //时间 
			dataIndex: 'roadTime' ,
			flex: 1
	    },{
	    	header: management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadTollPayment'),   //付款方式 
			dataIndex: 'payment' ,
			flex: 1,
			renderer : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PAYMENTMODE');
			}
	    }];
		me.callParent([cfg]);
	}
});

//定义加油合计信息的展示panel
Ext.define('Foss.Management.FuelConsumptionTotalRead',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true
	},
	items:[{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.return'),   //返回
		columnWidth:.1,
		handler: function(){
			management.readDetailInfoWindow.close();
		}
	}]
});

Ext.define('Foss.Management.DepartureReadReturn',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true
	},
	items:[{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.return'),   //返回
		columnWidth:.1,
		handler: function(){
			management.departureDetailReadWindows.close();
		}
	}]
});

Ext.define('Foss.Management.DepartureEditSave',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true,
		maxLength:20,
	    maxLengthText:management.fuelConsumptionIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	items:[{
		name:'notes',
		readOnly:false,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.notes'),   //备注
		columnWidth:.4
	},{
		readOnly:true,
		columnWidth:.35
	},{
		readOnly:true,
		columnWidth:.8
	},{
		hidden:true,
		name:'id'
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.save'),   //保存
		columnWidth:.1,
		handler: function(){
			//车辆信息
			var vehicleValue = management.fuelConsumptionVehicleInfoEdit.getValues();
			//发车信息
			var departureValue = management.fuelConsumptionDepartureEdit.getValues();
			
			var btnSave = this;
			if(management.fuelConsumptionVehicleInfoEdit.getForm().isValid() 
					&& management.fuelConsumptionDepartureEdit.getForm().isValid()){
				if(!management.fuelConsumptionDepartureEdit.getForm().findField('departureMode').readOnly
						&& departureValue.departureMode === 'CHOISE'){
					Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.choiseDepartureMode'));  //提示选择发车模式
					return;
				}
				
				//加油信息
				var cardStore = management.fuelDetailFuelCardEdit.store.data.items;
				//路桥费信息
				var tollStore = management.roadTollEdit.store.data.items;
				
				var fuelRecords = new Array();
				var roadRecords = new Array();
				
				for(var i = 0 ; i < cardStore.length ; i++){
					fuelRecords.push(cardStore[i].data);
	    		}
				for(var i = 0 ; i < tollStore.length ; i++){
					roadRecords.push(tollStore[i].data);
	    		}
				
				//检验油耗明细字段是否存在空值
				for(var i = 0 ; i < fuelRecords.length ; i++){
					if(fuelRecords[i].fuelQty === "" || fuelRecords[i].fuelQty === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].unitPrice === "" || fuelRecords[i].unitPrice === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelFee === "" || fuelRecords[i].fuelFee === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelType === "" || fuelRecords[i].fuelType === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelSupplier === "" || fuelRecords[i].fuelSupplier === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelGrade === "" || fuelRecords[i].fuelGrade === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelAddress === "" || fuelRecords[i].fuelAddress === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelTime === "" || fuelRecords[i].fuelTime === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(fuelRecords[i].fuelPayType === "FUEL_CARD"){
						if(fuelRecords[i].balance === "" || fuelRecords[i].balance === null){
							Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fFuelDetailIsNotNull'));  //提示油耗明细不能有空值
							return;
						}
					}
				}
				
				//校验路桥费信息
				for(var i = 0 ; i < roadRecords.length ; i++){
					if(roadRecords[i].roadToll === "" || roadRecords[i].roadToll === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].roadAddress === "" || roadRecords[i].roadAddress === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].roadTime === "" || roadRecords[i].roadTime === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
					if(roadRecords[i].payment === "" || roadRecords[i].payment === null){
						Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.fRoadTollIsNotNull'));  //提示油耗明细不能有空值
						return;
					}
				}
				
				Ext.Msg.show({
            		title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),     //提示
					msg:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.save'),     //车牌号录入后不允许修改，确认录入无误后保存？
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn : function(btn){
						if(btn == 'no'){
							return;
						}else{
							
							btnSave.setDisabled(true);		

							var array = {fuelConsumptionVo:{
								'fuelVehicleEntity':vehicleValue,
								'fuelConsumptionDto':departureValue,
								'fuelDetailList':fuelRecords,
								'fuelRoadTollList':roadRecords}
							};
							Ext.Ajax.request({
								url:management.realPath('updateFuelConsumption.action'),
								jsonData:array,
								success : function(response) {
				    				var json = Ext.decode(response.responseText);
				    				Ext.ux.Toast.msg(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'), management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.saveSuccess'), 'ok', 1000);   //提示保存成功
				    				management.editDetailInfoWindow.close();
				    				//management.fuelConsumptionIndex.pagingBar.moveFirst();
				    				management.fuelPagingBar.moveFirst();
				    				btnSave.setDisabled(false);
				    			},
				    			exception : function(response) {
				    				var json = Ext.decode(response.responseText);
				    				Ext.Msg.alert(management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.msg.message'),json.message);
									btnSave.setDisabled(false);
				    			}
							});
						}
					}
				});
			}	
		}
	},{
		xtype:'button',
		text:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.button.cancel'),   //取消
		columnWidth:.1,
		handler: function(){
			management.editDetailInfoWindow.close();
		}
	}]
	
});

//management.fuelDetailFuelCardRead = Ext.create('Foss.management.fuelDetailFuelCardRead');
management.fuelDetailFuelCardRead = Ext.create('Foss.Management.FuelDetailGrid');
//management.roadTollRead = Ext.create('Foss.Management.roadTollRead');
management.roadTollRead = Ext.create('Foss.Management.roadTollGrid');
management.fuelConsumptionTotalRead = Ext.create('Foss.Management.FuelConsumptionTotalRead');

//定义加油信息的panel
Ext.define('Foss.Management.FuelDetailReadAll',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelDetailEditAll.title'),   //加油信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
			management.fuelDetailFuelCardRead,
			management.roadTollRead,
			management.fuelConsumptionTotalRead
	       ]
});

management.fuelConsumptionVehicleInfoRead = Ext.create('Foss.Management.FuelConsumptionVehicleInfoRead');
management.fuelConsumptionDepartureRead = Ext.create('Foss.Management.FuelConsumptionDepartureRead');
management.fuelDetailAllRead = Ext.create('Foss.Management.FuelDetailReadAll');

//定义整个展示的panel集合
Ext.define('Foss.Management.FuelConsumptionReadAll',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionEditAll.title'),   //车辆费用信息
	cls: "panelContentNToolbar",
	frame: true,
	items:[
		management.fuelConsumptionVehicleInfoRead,
		management.fuelConsumptionDepartureRead,
		management.fuelDetailAllRead
		]
});


management.fuelDetailFuelCardEdit = Ext.create('Foss.Management.FuelDetailFuelCard',{
	fuelDetailFuelCardFuelTimeId: 'Foss_Management_FuelDetailFuelCard_fuelTime_Edit',
});
management.roadTollEdit = Ext.create('Foss.Management.roadToll',{
	roadTollTimeId:'Foss_Management_roadToll_roadTollTime_Edit'
});
//定义编辑时加油信息的panel
Ext.define('Foss.Management.FuelDetailEditAll',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelDetailEditAll.title'),   //加油信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
	       management.fuelDetailFuelCardEdit,
	       management.roadTollEdit
	       ]
});

management.fuelConsumptionVehicleInfoEdit = Ext.create('Foss.Management.FuelConsumptionVehicleInfo');
management.fuelConsumptionDepartureEdit = Ext.create('Foss.Management.FuelConsumptionDeparture');
management.fuelDetailEditAll = Ext.create('Foss.Management.FuelDetailEditAll');
management.departureEditSave = Ext.create('Foss.Management.DepartureEditSave');

Ext.define('Foss.Management.FuelConsumptionEditAll',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	title:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionEditAll.title'),   //车辆费用信息
	cls: "panelContentNToolbar",
	frame: true,
	items:[
			management.fuelConsumptionVehicleInfoEdit,
			management.fuelConsumptionDepartureEdit,
			management.fuelDetailEditAll,
			management.departureEditSave
		]
});

//新增发车信息时查看明细
management.fuelDetailRead = Ext.create('Foss.management.fuelDetailFuelCardRead');
management.roadTollDetailRead = Ext.create('Foss.Management.roadTollRead');

//定义编辑时加油信息的panel
Ext.define('Foss.Management.FuelDetailRead',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelDetailEditAll.title'),   //加油信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
	       management.fuelDetailRead,
	       management.roadTollDetailRead
	       ]
});

management.departureRead = Ext.create('Foss.Management.FuelConsumptionDepartureRead');
management.departureFuelRead = Ext.create('Foss.Management.FuelDetailRead');
management.departureReadReturn = Ext.create('Foss.Management.DepartureReadReturn');

Ext.define('Foss.Management.DepartureDetailRead',{
	extend: 'Ext.form.Panel',
	title: management.fuelConsumptionIndex.i18n('foss.management.fuelDetailEditAll.title'),   //加油信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	layout:'auto',
	items:[
	       management.departureRead,
	       management.departureFuelRead,
	       management.departureReadReturn
	       ]
});

//新增车辆界面窗口
Ext.define('Foss.Management.DepartureDetailReadWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	editFuelConsumptionForm : null,
	getEditFuelConsumptionForm: function(){
		if(this.editFuelConsumptionForm==null){
			this.editFuelConsumptionForm = Ext.create('Foss.Management.DepartureDetailRead');
		}
		management.fuelConsumptionAll = this.editFuelConsumptionForm;
		return this.editFuelConsumptionForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditFuelConsumptionForm()
				];
		me.callParent([cfg]);
	}
});

management.departureDetailReadWindows = Ext.create('Foss.Management.DepartureDetailReadWindows');

//新增车辆界面窗口
Ext.define('Foss.Management.addDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	editFuelConsumptionForm : null,
	getEditFuelConsumptionForm: function(){
		if(this.editFuelConsumptionForm==null){
			this.editFuelConsumptionForm = Ext.create('Foss.Management.FuelConsumptionAddAll');
		}
		management.fuelConsumptionAll = this.editFuelConsumptionForm;
		return this.editFuelConsumptionForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditFuelConsumptionForm()
				];
		me.callParent([cfg]);
	}
});

//新增发车信息窗口
Ext.define('Foss.Management.DepartureInfoWindow',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1000,
	bodyCls: 'autoHeight',
	resizable:false,
	editFuelConsumptionForm : null,
	getEditFuelConsumptionForm: function(){
		if(this.editFuelConsumptionForm==null){
			this.editFuelConsumptionForm = Ext.create('Foss.Management.DepartureInfoAll');
		}
		management.fuelStandardInfoAll = this.editFuelConsumptionForm;
		return this.editFuelConsumptionForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditFuelConsumptionForm()
				];
		me.callParent([cfg]);
	}
	
});

//查看窗口
Ext.define('Foss.Management.readDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	readFuelConsumptionForm : null,
	getReadFuelConsumptionForm: function(){
		if(this.readFuelConsumptionForm==null){
			this.readFuelConsumptionForm = Ext.create('Foss.Management.FuelConsumptionReadAll');
		}
		management.fuelConsumptionAllRead = this.readFuelConsumptionForm;
		return this.readFuelConsumptionForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getReadFuelConsumptionForm()
				];
		me.callParent([cfg]);
	}
});

//编辑窗口
Ext.define('Foss.Management.editDetailInfoWindows',{
	extend: 'Ext.window.Window',
	modal:true,
	closeAction: 'hide',
	width:1200,
	bodyCls: 'autoHeight',
	resizable:false,
	editFuelConsumptionForm : null,
	getEditFuelConsumptionForm: function(){
		if(this.editFuelConsumptionForm==null){
			this.editFuelConsumptionForm = Ext.create('Foss.Management.FuelConsumptionEditAll');
		}
		management.fuelConsumptionAll = this.editFuelConsumptionForm;
		return this.editFuelConsumptionForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditFuelConsumptionForm()
				];
		me.callParent([cfg]);
	}
});

management.departureInfoWindow = Ext.create('Foss.Management.DepartureInfoWindow');
//management.fuelConsumptionIndex.addDetailInfoWindow = Ext.create('Foss.Management.addDetailInfoWindows');
management.addVehicleAndDepartureDetailInfoWindow = Ext.create('Foss.Management.addDetailInfoWindows');
management.readDetailInfoWindow = Ext.create('Foss.Management.readDetailInfoWindows');
management.editDetailInfoWindow = Ext.create('Foss.Management.editDetailInfoWindows');

//定义总合计信息的展示panel
Ext.define('Foss.Management.FuelConsumptionTotalInfo',{
	extend: 'Ext.form.Panel',
	bodyStyle:'padding:5px 5px 0',
	layout:'column',
	defaultType:'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		readOnly:true
	},
	items:[{
		name:'runKmAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.kmAmount'),   //公里数合计
		columnWidth:.25
	},{
		name:'fuelQtyAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelAmount'),   //加油升数合计
		columnWidth:.25
	},{
		name:'fuelFeeAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.fuelFeeAmount'),   //油费合计
		columnWidth:.25
	},{
		name:'roadTollAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.roadFeeAmount'),   //路桥费合计
		columnWidth:.25
	},{
		name:'avgPriceAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.averagePrice'),   //平均加油单价
		columnWidth:.25
	},{
		name:'kmRoadTollAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.perroadFeeAmount'),   //公里路桥费
		columnWidth:.25
	},{
		name:'hdKmFuelAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.ohKmFuelConsumption'),   //百公里油耗
		columnWidth:.25
	},{
		name:'sideLoadAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.unilateralLoad'),   //单边载重
		columnWidth:.25
	},{
		name:'thdKmFuelAll',
		value: 0,
		fieldLabel:management.fuelConsumptionIndex.i18n('foss.management.fuelConsumptionIndex.label.tohKmFuelConsumption'),   //吨百公里油耗
		columnWidth:.25
	},{
		readOnly:true,
		columnWidth:.35
	},{
		readOnly:true,
		columnWidth:.8
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	management.queryFuelConsumption = Ext.create('Foss.Management.QueryFuelConsumption');
	management.resultFuelConsumption = Ext.create('Foss.Management.ResultFuelConsumption');
	management.fuelConsumptionTotalInfo = Ext.create('Foss.Management.FuelConsumptionTotalInfo');
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-fuelConsumptionIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [management.queryFuelConsumption,management.resultFuelConsumption,management.fuelConsumptionTotalInfo],
		renderTo: 'T_management-fuelConsumptionIndex-body'
	});
});