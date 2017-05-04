//定义一个档案model
Ext.define('Foss.Management.ArchiveModel',{
	extend: 'Ext.data.Model',
	fields: [
	 		{name: 'id', type: 'string'},
	 		{name: 'vehicleNo', type: 'string'},
	 		{name: 'containerNo', type: 'string'},
	 		{name: 'lineName', type: 'string'},
	 		{name: 'lineNo', type: 'string'},
	 		{name: 'driver1Name', type: 'string'},
	 		{name: 'driver1Code', type: 'string'},
	 		{name: 'driver2Name', type: 'string'},
	 		{name: 'driver2Code', type: 'string'},
	 		{name: 'deptRegionName', type: 'string'},
	 		{name: 'arrvRegionName', type: 'string'},
	 		{name: 'weight', type: 'string'},
	 		{name: 'runKm', type: 'string'},
	 		{name: 'volume', type: 'string'},
	 		{name: 'signBillFee', type: 'string'},
	 		{name: 'driverRoyalty', type: 'string'},
	 		{name: 'fuelFee', type: 'string'},
	 		{name: 'tollFee', type: 'string'},
	 		{name: 'payAmount', type: 'string'},
	 		{name: 'departureTime', type: 'string',
	 			convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}
	 		},
	 		{name: 'arriveTime', type: 'string',
	 			convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}	
	 		},
	 		{name: 'transDepartment', type: 'string'},
	 		{name: 'groupCode', type: 'string'},
	 		{name: 'vehicleBrand', type: 'string'},
	 		{name: 'vehicleTypeLength', type: 'string'},
	 		{name: 'startKm', type: 'string'},
	 		{name: 'arriveKm', type: 'string'},
	 		{name: 'fineType', type: 'string'},
	 		{name: 'stipulateDepartureTime', type: 'string',
	 			convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}
	 		},
	 		{name: 'stipulateArriveTime', type: 'string',
	 			convert : function(value){
	 				if(value != null && value != ''){
		 				var date = new Date(value);
		 				return Ext.Date.format(date,'Y-m-d H:i:s');
	 				}else{
		 				return null;
		 			}
	 			}	
	 		},
	 		{name: 'standardPreion', type: 'string'},
	 		{name: 'isLateDeparture', type: 'string'},
	 		{name: 'isLateArrive', type: 'string'},
	 		{name: 'isMutual', type: 'string'},
	 		{name: 'preion', type: 'string'},
	 		{name: 'lateDepartureReason', type: 'string'},
	 		{name: 'lateArriveReason', type: 'string'},
	 		{name: 'fineAmount', type: 'string'},
	 		{name: 'vehicleAssembleNo', type: 'string'},
	 		{name: 'frequencyNo', type: 'string'},
	 		{name: 'fuelQty', type: 'string'},
	 		{name: 'fuelPrice', type: 'string'},
	 		{name: 'onKmFuelConsumption', type: 'string'}
	 	]
});
//定义查询档案store
Ext.define('Foss.Management.ArchiveStore',{
	extend: 'Ext.data.Store',
	autoLoad: false,
	model: 'Foss.Management.ArchiveModel',
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: management.realPath('queryDriveArchive.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'driveArchiveVo.driveArchiveList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = management.driveArchiveIndex.queryPanel;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'driveArchiveVo.driveArchiveDto.transDepartment' : queryParams.transDepartment,
						'driveArchiveVo.driveArchiveDto.groupCode' : queryParams.groupCode,
						'driveArchiveVo.driveArchiveDto.vehicleNo' : queryParams.vehicleNo,
						'driveArchiveVo.driveArchiveDto.vehicleBrand' : queryParams.vehicleBrand,
						'driveArchiveVo.driveArchiveDto.vehicleTypeLength' :queryParams.vehicleTypeLength,
						'driveArchiveVo.driveArchiveDto.containerNo' :queryParams.containerNo,
						'driveArchiveVo.driveArchiveDto.driver1Code' :queryParams.driver1Code,
						'driveArchiveVo.driveArchiveDto.driver2Code' :queryParams.driver2Code,
						'driveArchiveVo.driveArchiveDto.lineNo' :queryParams.lineNo,
						'driveArchiveVo.driveArchiveDto.weight' :queryParams.weight,
						'driveArchiveVo.driveArchiveDto.deptRegionName' :queryParams.deptRegionName,
						'driveArchiveVo.driveArchiveDto.arrvRegionName' :queryParams.arrvRegionName,
						'driveArchiveVo.driveArchiveDto.fineType' :queryParams.fineType,
						'driveArchiveVo.driveArchiveDto.archiveUserCode' :queryParams.archiveUserCode,
						'driveArchiveVo.driveArchiveDto.isLateArrive' :queryParams.isLateArrive,
						'driveArchiveVo.driveArchiveDto.isMutual' :queryParams.isMutual,
						'driveArchiveVo.driveArchiveDto.isLateDeparture' :queryParams.isLateDeparture,
						'driveArchiveVo.driveArchiveDto.beginDate' :queryParams.driveBeginDate,
						'driveArchiveVo.driveArchiveDto.endDate' :queryParams.driveEndDate
					}
				});	
			}
		}
	}
});

//定义一个查询条件的panel
Ext.define('Foss.management.driveArchiveIndex.queryPanel',{
	extend:'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.driveArchiveIndex.queryPanel.title'),  //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80
	},
	layout:'column',
	items:[{
		xtype:'dynamicorgcombselector',
		transDepartment:'Y',
		type:'ORG',  
		fieldLabel: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.transDepartmentCode'),   //车队
        name: 'transDepartmentCode', 
        columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var transDepartment = field.getValue();
				if(transDepartment === ''){
					form.findField('transDepartment').setValue('');
					return;
				}
				form.findField('transDepartment').setValue(field.rawValue);
			}
		}
	},{
		name:'transDepartment',
		hidden:true
	},{
		xtype:'dynamicorgcombselector',
		transTeam:'Y',
		type:'ORG',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.groupCode'),   //小组
		name:'groupCode',
        columnWidth:.25
	},{
		xtype:'commonowntruckselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleNo'),   //车牌号码
		name:'vehicleNo',
        columnWidth:.25
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleBrand'),   //车辆品牌
		name:'vehicleBrand',
        columnWidth:.25,
		maxLength:200,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},{
		xtype:'commonvehicletypeselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleTypeLength'),   //车辆型号
		name:'vehicleTypeLength',
        columnWidth:.25
	},{
		xtype:'commonowntruckselector',
		displayField : 'containerCode',// 显示名称
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		vehicleTypes : 'vehicletype_trailer',
		allowBlank:true,
		readOnly:false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.containerNo'),   //货柜号码
		columnWidth:.25,
		name:'containerNo',
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				form.findField('containerNo').setValue(field.rawValue);
			}
		}
	},{
		xtype:'commonowndriverselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver1Code'),   //司机1
		name:'driver1Code',
        columnWidth:.25
	},{
		xtype:'commonowndriverselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver2Code'),   //司机2
		name:'driver2Code',
        columnWidth:.25
	},{
		xtype : 'dynamicorgcombselector',
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		allowBlank:true,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.deptRegionName'),   //始发站
		name:'deptRegionCode',
        columnWidth:.25,
        listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var deptRegionNameToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionNameToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionNameToLine.getValue() && '' != deptRegionNameToLine.getValue() && null != arrvRegionNameToLine.getValue() && '' != arrvRegionNameToLine.getValue()){
					var lineNameStr = deptRegionNameToLine.rawValue + '-' + arrvRegionNameToLine.rawValue;
					var lineCodeStr = deptRegionNameToLine.getValue() + '-' + arrvRegionNameToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineNo');
    				cmbLineName.setValue(lineNameStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : lineNameStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.setValue(lineCodeStr);
    				}
				}
				form.findField('deptRegionName').setValue(field.rawValue);
			}
		}
	},{
		name:'deptRegionName',
		hidden:true
	},{
		xtype : 'dynamicorgcombselector',
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		allowBlank:true,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arrvRegionName'),   //目的站
		name:'arrvRegionCode',
        columnWidth:.25,
        listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var deptRegionNameToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionNameToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionNameToLine.getValue() && '' != deptRegionNameToLine.getValue() && null != arrvRegionNameToLine.getValue() && '' != arrvRegionNameToLine.getValue()){
					var lineNameStr = deptRegionNameToLine.rawValue + '-' + arrvRegionNameToLine.rawValue;
					var lineCodeStr = deptRegionNameToLine.getValue() + '-' + arrvRegionNameToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineNo');
    				cmbLineName.setValue(lineNameStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : lineNameStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.setValue(lineCodeStr);
    				}
				}
				form.findField('arrvRegionName').setValue(field.rawValue);
			}
		}
	},{
		name:'arrvRegionName',
		hidden:true
	},{
		xtype:'commonlineselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lineNo'),   //线路
		name:'lineNo',
        columnWidth:.25
	},{
		xtype : 'numberfield',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.weight'),   //运输重量
		name:'weight',
        columnWidth:.25,
  		validator : function(value) {
	         if(value <= 0) {
	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
	         } 
	         return true;
	    }
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineType'),   //罚款类型
		name:'fineType',
        columnWidth:.25,
		maxLength:20,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},{
		xtype:'commonemployeeselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.archiveUserCode'),   //制单人
		name:'archiveUserCode',
        columnWidth:.25
	},{
		xtype: 'rangeDateField',
		fieldId:'Foss_Management_QueryFuelConsumption_DriveCreateDateId',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.createTime'),   //创建时间
		dateType: 'datetimefield_date97',
		fromName: 'driveBeginDate',
		dateRange:31, //时间跨度不能大于一个月
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'driveEndDate',
        columnWidth:.5
	},{
		xtype:'checkboxgroup',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateArrive'),   //是否晚到
		items:[{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是 
			name      : 'isLateArrive',  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			name      : 'isLateArrive',  //表单的参数名
			inputValue: 'N',  //表单的参数值
		}],
        columnWidth:.25
	},{
		xtype:'checkboxgroup',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isMutual'),   //是否对发
		items:[{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			name      : 'isMutual',  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			name      : 'isMutual',  //表单的参数名
			inputValue: 'N',  //表单的参数值
		}],
        columnWidth:.25
	},{
		xtype:'checkboxgroup',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateDeparture'),   //是否晚发
		items:[{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			name      : 'isLateDeparture',  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			margin: '5 5 0 5',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			name      : 'isLateDeparture',  //表单的参数名
			inputValue: 'N',  //表单的参数值
		}],
        columnWidth:.25
	},{
		readOnly:true,
		columnWidth:.25
	},{
		xtype:'button',
		text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.reset'),   //重置
		width:90,
		handler: function() {
			var form = this.up('form').getForm();
			form.reset();
			form.findField('driveBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
			form.findField('driveEndDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
		},
        columnWidth:.1
	},{
		readOnly:true,
		columnWidth:.8
	},{
		xtype:'button',
		text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.search'),   //查询
		cls:'yellow_button',
		width:90,
		disabled:management.driveArchiveIndex.isPermission('management/queryDriveArchiveButton')?false:true,
		hidden:management.driveArchiveIndex.isPermission('management/queryDriveArchiveButton')?false:true,
		handler: function() {
			management.driveArchiveIndex.pagingBar.moveFirst();
		},
        columnWidth:.1
	}]
});

//定义一个展示查询结果的grid
Ext.define('Foss.Management.QueryResult',{
	extend:'Ext.grid.Panel',
    title:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.queryResult.title'),   //档案列表
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var id = record.get('id');
			if(id==management.id)
			{
    			return 'predeliver_notice_customer_row_purole';
			}else
			{
			    return 'predeliver_notice_customer_row_white';
			}
		}
	},
	//定义表格列信息
	columns: [{
			xtype:'actioncolumn',
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.operator'),   //操作
	        flex : 1.2,
	        items: [{
	            iconCls: 'deppon_icons_showdetail',
	            tooltip: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.info'),   //查看
	            handler: function(grid, rowIndex, colIndex) {
	            	record = grid.getStore().getAt(rowIndex);
	            	var id = grid.getStore().getAt(rowIndex).data.id;
	            	var array = {driveArchiveVo: {id:id}};
	            	//用ajax从后台读取当前行的最新信息
	    			Ext.Ajax.request({
	        			url : management.realPath('displayDriveArchiveDetail.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				var json = Ext.decode(response.responseText);
	        				//在json中取出对象
	        				var driveArchiveEntity = json.driveArchiveVo.driveArchiveEntity;
	        				if(driveArchiveEntity != null){
	        					//如果不为null则替换record里面的data
	        					record.data = json.driveArchiveVo.driveArchiveEntity;
	        					//将里面的date类型进行转化
		        				if(record.data.departureTime != null && record.data.departureTime != ""){
		    	        			record.data.departureTime = new Date(record.data.departureTime);
		    	        		}
		    	        		if(record.data.arriveTime != null && record.data.arriveTime != ""){
		    	        			record.data.arriveTime = new Date(record.data.arriveTime);
		    	        		}
		    	        		if(record.data.stipulateDepartureTime != null && record.data.stipulateDepartureTime != ""){
		    	        			record.data.stipulateDepartureTime = new Date(record.data.stipulateDepartureTime);
		    	        		}
		    	        		if(record.data.stipulateArriveTime != null && record.data.stipulateArriveTime != ""){
		    	        			record.data.stipulateArriveTime = new Date(record.data.stipulateArriveTime);
		    	        		}
		    	    			var detailInfoWindow = management.detailInfoWindow ;
		    	    			var infoForm = management.archiveInfoForm;
		    	    			//加载grid中的数据到弹出的window中
		    	    			infoForm.getForm().reset();
		    	    			
		    	    			//小组公共选择器
		        				var cmbGroupOrg = management.archiveInfoForm.getForm().findField('groupCode');
		        				cmbGroupOrg.getStore().load({params:{'commonOrgVo.code' : record.data.groupCode}});
		        				cmbGroupOrg.setValue(record.groupCode);
		        				if(null != cmbGroupOrg.getStore() && ''!=cmbGroupOrg.getStore() && (null == cmbGroupOrg.getStore().totalCount || 0 == cmbGroupOrg.getStore().totalCount)){
		        					cmbGroupOrg.setValue(null);
		        				}
		        				
		        				//车队
		        				var cmbTransDepartment = management.archiveInfoForm.getForm().findField('transDepartment');
		        				cmbTransDepartment.getStore().load({params:{'commonOrgVo.code' : record.data.transDepartment}});
		        				cmbTransDepartment.setValue(record.transDepartment);
		    	    			
		    	    			infoForm.getForm().loadRecord(record);
		    	    			management.timeInfoForm.getForm().findField('lateDeparture').setReadOnly(true);
		    	    			management.timeInfoForm.getForm().findField('lateArrive').setReadOnly(true);
		    	    			management.timeInfoForm.getForm().findField('mutual').setReadOnly(true);
		    	    			//展示window
		    	    			detailInfoWindow.show();
	        				}else{
	        					Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
	        				}
	        			},
	        			exception : function(response) {
	        				var json = Ext.decode(response.responseText);
	        				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
	        			}
	        		});
	            }
	        },{
	            iconCls: 'deppon_icons_edit',
	            tooltip: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.edit'),    //编辑
	            disabled:management.driveArchiveIndex.isPermission('management/displayDriveArchiveDetailButton')?false:true,
	            hidden:management.driveArchiveIndex.isPermission('management/displayDriveArchiveDetailButton')?false:true,
	            handler: function(grid, rowIndex, colIndex) {
	            	record = grid.getStore().getAt(rowIndex);
	            	var id = grid.getStore().getAt(rowIndex).data.id;
	            	var array = {driveArchiveVo: {id:id}};
	            	//用ajax从后台读取当前行的最新信息
	    			Ext.Ajax.request({
	        			url : management.realPath('displayDriveArchiveDetail.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				var json = Ext.decode(response.responseText);
	        				//在json中取出对象
	        				var driveArchiveEntity = json.driveArchiveVo.driveArchiveEntity;
	        				if(driveArchiveEntity != null){
	        					//如果不为null则替换record里面的data
	        					record.data = json.driveArchiveVo.driveArchiveEntity;
	        					//将里面的date类型进行转化
		        				if(record.data.departureTime != null && record.data.departureTime != ""){
		    	        			record.data.departureTime = Ext.Date.format(new Date(record.data.departureTime), 'Y-m-d H:i:s');
		    	        		}
		    	        		if(record.data.arriveTime != null && record.data.arriveTime != ""){
		    	        			record.data.arriveTime = Ext.Date.format(new Date(record.data.arriveTime), 'Y-m-d H:i:s');
		    	        		}
		    	        		if(record.data.stipulateDepartureTime != null && record.data.stipulateDepartureTime != ""){
		    	        			record.data.stipulateDepartureTime = Ext.Date.format(new Date(record.data.stipulateDepartureTime), 'Y-m-d H:i:s');
		    	        		}
		    	        		if(record.data.stipulateArriveTime != null && record.data.stipulateArriveTime != ""){
		    	        			record.data.stipulateArriveTime = Ext.Date.format(new Date(record.data.stipulateArriveTime), 'Y-m-d H:i:s');
		    	        		}
		    	    			var detailInfoWindow = management.editDetailInfoWindow;
		    	    			var infoForm = management.editArchiveInfoForm;
		    	    			//加载grid中的数据到弹出的window中
		    	    			infoForm.getForm().reset();
		    	    			
		    	    			//load公共选择器
		        				var cmbDeptRegionCode = management.editDepartureInfoForm.getForm().findField('deptRegionCode'); //出发站-编辑初始化
		        				cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.deptRegionName}});
		        				cmbDeptRegionCode.setValue(record.data.deptRegionName);
		        				
		        				var cmbArrvRegionCode = management.editDepartureInfoForm.getForm().findField('arrvRegionCode'); //目的站-编辑初始化
		        				cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.arrvRegionName}});
		        				cmbArrvRegionCode.setValue(record.data.arrvRegionName);
		        				
		        				var cmbLineName = management.editDepartureInfoForm.getForm().findField('lineNo');
		        				cmbLineName.getStore().load({params:{'lineVo.lineEntity.simpleCode' : record.data.lineNo}});
		        				cmbLineName.setValue(record.data.lineNo);
		        				
		        				var cmbDriver1Code = management.editDepartureInfoForm.getForm().findField('driver1Code');
		        				cmbDriver1Code.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driver1Code}});
		        				cmbDriver1Code.setValue(record.data.driver1Code);
		        				
		        				var cmbDriver2Code = management.editDepartureInfoForm.getForm().findField('driver2Code');
		        				cmbDriver2Code.getStore().load({params:{'driverVo.driverEntity.empCode' : record.data.driver2Code}});
		        				cmbDriver2Code.setValue(record.data.driver1Code);
		        				
		        				//小组公共选择器
		        				var cmbGroupOrg = management.editArchiveInfoForm.getForm().findField('groupCode');
		        				cmbGroupOrg.getStore().load({params:{'commonOrgVo.code' : record.data.groupCode}});
		        				if(null != cmbGroupOrg.getStore() && ''!=cmbGroupOrg.getStore() && (null == cmbGroupOrg.getStore().totalCount || 0 == cmbGroupOrg.getStore().totalCount)){
		        					cmbGroupOrg.setValue(null);
		        				}else{
		        					cmbGroupOrg.setValue(record.groupCode);
		        				}
		        				
		        				//车队
		        				var cmbTransDepartment = management.editArchiveInfoForm.getForm().findField('transDepartment');
		        				cmbTransDepartment.getStore().load({params:{'commonOrgVo.code' : record.data.transDepartment}});
		        				//cmbTransDepartment.setValue(record.transDepartment);
		        				
		        				//货柜号码
		    	    			var cmbContainerNo = management.editArchiveInfoForm.getForm().findField('containerNo'); 
		    	    			cmbContainerNo.getStore().load({params:{'truckVo.truck.orgId' : record.data.containerNo}});
		    	    			cmbContainerNo.setValue(record.data.containerNo);

		        				//把后台传到前台来的数据缓存到全局变量中
		        				management.driveArchiveData={};
		        				management.driveArchiveData.record=record;
		        				
		    	    			infoForm.getForm().loadRecord(record);
		    	    			//展示window
		    	    			detailInfoWindow.show();
	        				}else{
	        					Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
	        				}
	        			},
	        			exception : function(response) {
	        				var json = Ext.decode(response.responseText);
	        				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
	        			}
	        		});
	            }
	        },{
	            iconCls: 'deppon_icons_delete',
	            tooltip: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.delete'),    //删除
	            handler: function(grid, rowIndex, colIndex) {
	            	Ext.Msg.show({
	            		title:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.delete'),    //删除
						msg:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.confirmDelete'),    //确认删除这条记录吗？
						buttons:Ext.Msg.YESNO,
						icon: Ext.Msg.QUESTION, 
						fn : function(btn){
							if(btn == 'yes'){
								var id = grid.getStore().getAt(rowIndex).data.id;
								var array = {driveArchiveVo: {id:id}};
								Ext.Ajax.request({
				        			url : management.realPath('delete.action'),
				        			jsonData:array,
				        			success : function(response) {
				        				var json = Ext.decode(response.responseText);
				        				grid.store.load();
										Ext.ux.Toast.msg(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'), management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.deleteSuccess'), 'ok', 1000);  //提示删除成功
				        			},
				        			exception : function(response) {
				        				var json = Ext.decode(response.responseText);
				        				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
				        			}
				        		});
							}
						}
	            	});
	            }
	        }]
		},{
			//字段标题
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleNo'),   //车牌号
			//关联model中的字段名
			dataIndex: 'vehicleNo' ,
			//自动填列
			flex: 1 
		},{ 
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.containerNo'),   //货柜号
			dataIndex: 'containerNo', 
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lineNo'),   //线路 
	    	xtype : 'ellipsiscolumn',
			dataIndex: 'lineName' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver1Code'),   //司机1 
			dataIndex: 'driver1Name' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver2Code'),   //司机2 
			dataIndex: 'driver2Name' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.deptRegionName'),   //始发站 
	    	xtype : 'ellipsiscolumn',
			dataIndex: 'deptRegionName' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arrvRegionName'),   //目的站 
	    	xtype : 'ellipsiscolumn',
			dataIndex: 'arrvRegionName' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.weight'),   //运输重量 
			dataIndex: 'weight' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.runKm'),   //行驶公里
			dataIndex: 'runKm' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.volume'),   //运输体积
			dataIndex: 'volume' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.signBillFee'),   //签单费
			dataIndex: 'signBillFee' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driverRoyalty'),   //司机提成
			dataIndex: 'driverRoyalty' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fuelFee'),   //燃油费
			dataIndex: 'fuelFee' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.tollFee'),   //路桥费
			dataIndex: 'tollFee' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineAmount'),   //罚款金额 
			dataIndex: 'payAmount' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.departureTime'),   //实际发车时间
	    	xtype : 'ellipsiscolumn',
			dataIndex: 'departureTime' ,
			flex: 1 
		},{
			header: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arriveTime'),   //实际到达时间
	    	xtype : 'ellipsiscolumn',
			dataIndex: 'arriveTime' ,
			flex: 1 
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Management.ArchiveStore');
		me.tbar = [{
			xtype:'button',
			text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.add'),   //新增
			disabled:management.driveArchiveIndex.isPermission('management/saveOrUpdateButton')?false:true,
			hidden:management.driveArchiveIndex.isPermission('management/saveOrUpdateButton')?false:true,
			handler: function(){
				var detailInfoWindow = management.editDetailInfoWindow;
				var infoForm = management.editArchiveInfoForm;
				infoForm.getForm().reset();
				var record = Ext.create('Foss.Management.ArchiveStore');
				infoForm.getForm().loadRecord(record);
				//展示window
				detailInfoWindow.show();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		management.driveArchiveIndex.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
/*-----------展示panel------------------*/
//定义detail里的车辆信息的panel
Ext.define('Foss.Management.VehicleInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.vehicleInfoForm.title'),   //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:true,
		readOnly:true,
		width: 200
	},
	layout:'column',
	items:[{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleAssembleNo'),   //配载车次号
		name:'vehicleAssembleNo'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleNo'),   //车牌号码,
		name:'vehicleNo'
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transTeam:'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.groupCode'),   //小组
		name:'groupCode'
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.transDepartmentCode'),   //车队
		name:'transDepartment'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleBrand'),   //车辆品牌
		name:'vehicleBrand'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.containerNo'),   //货柜号码
		name:'containerNo'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleTypeLength'),   //车辆型号
		name:'vehicleTypeLength'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.frequencyNo'),   //班次号
		name:'frequencyNo'
	}]
});
//定义detail中的发车信息的panel
Ext.define('Foss.Management.DepartureInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:true,
		readOnly:true,
		width: 200
	},
	layout:'column',
	items:[{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.deptRegionName'),   //始发站
		name:'deptRegionName'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arrvRegionName'),   //目的站
		name:'arrvRegionName'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lineNo'),   //线路
		name:'lineName'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.weight'),   //运输重量
		name:'weight'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.startKm'),   //出发公里数
		name:'startKm'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.arriveKm'),   //到达公里数
		name:'arriveKm'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.runKm'),   //行驶公里数
		name:'runKm'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.volume'),   //运输体积
		name:'volume'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver1Code'),   //司机1
		name:'driver1Name'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver2Code'),   //司机2
		name:'driver2Name'
	}]
});

//定义edit detail中的油耗信息的panel
Ext.define('Foss.Management.FuelConsumptionInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.title'),   //油耗信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'numberfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		readOnly:true,
		allowBlank:false
	},
	layout:'column',
	items:[{
		name:'fuelQty',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.fuelQty'),   //加油升数
		columnWidth:.25,
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		name:'fuelPrice',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.fuelPrice'),   //加油单价
		columnWidth:.25,
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		name:'onKmFuelConsumption',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.onKmFuelConsumption'),   //百公里油耗
		columnWidth:.25,
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	}]
});

//定义detail中的费用信息的panel
Ext.define('Foss.Management.FeeInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.editFeeInfoForm.title'),   //费用信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:true,
		readOnly:true,
		width: 200
	},
	layout:'column',
	items:[{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.signBillFee'),   //签单费
		name:'signBillFee'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driverRoyalty'),   //司机提成
		name:'driverRoyalty'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fuelFee'),   //燃油费
		name:'fuelFee'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.tollFee'),   //路桥费
		name:'tollFee'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineType'),   //罚款类型
		name:'fineType'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineAmount'),   //罚款金额
		name:'fineAmount'
	}]
});
//定义detail中的时效信息的panel
Ext.define('Foss.Management.TimeInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.timeInfoForm.title'),   //时效信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:true,
		readOnly:true,
	},
	layout:'column',
	items:[{
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.stipulateDepartureTime'),   //规定发车时间
		width:220,
		name:'stipulateDepartureTime'
	},{
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.stipulateArriveTime'),   //规定到达时间
		width:220,
		name:'stipulateArriveTime'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.standardPrescription'),   //标准时效
		labelWidth:60,
		width:210,
		name:'standardPreion'
	},{
		xtype:'radiogroup',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateDeparture'),   //是否晚发
		labelWidth:60,
		width:180,
		name:'lateDeparture',
		items:[{
			name: 'isLateDeparture',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:50,  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			name: 'isLateDeparture',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			width:50,
			inputValue: 'N',  //表单的参数值
		}]
	},{
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.departureTime'),   //实际发车时间,
		width:220,
		name:'departureTime'
	},{
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arriveTime'),   //实际到达时间
		width:220,
		name:'arriveTime'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.prescription'),   //实际时效
		labelWidth:60,
		width:210,
		name:'preion'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateArrive'),   //是否晚到
		xtype:'radiogroup',
		labelWidth:60,
		name:'lateArrive',
		width:180,
		items:[{
			name:'isLateArrive',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:50,  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			name:'isLateArrive',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			width:50,
			inputValue: 'N',  //表单的参数值
		}]
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lateDepartureReason'),   //晚发原因
		width:220,
		name:'lateDepartureReason'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lateArriveReason'),   //晚到原因
		width:220,
		name:'lateArriveReason'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.archiveUserCode'),   //制单人
		labelWidth:60,
		width:210,
		name:'archiveUserName'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isMutual'),   //是否对发
		labelWidth:60,
		xtype:'radiogroup',
		width:180,
		name:'mutual',
		items:[{
			name:'isMutual',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:50,  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			name:'isMutual',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			width:50,
			inputValue: 'N',  //表单的参数值
		}]
	}]
});
/*-----------展示panel over------------------*/

/*-----------编辑panel------------------*/
//定义edit detail里的车辆信息的panel
Ext.define('Foss.Management.EditVehicleInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.vehicleInfoForm.title'),   //车辆信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:false,
		maxLength:20,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleAssembleNo'),   //配载车次号
		columnWidth:.25,
		name:'vehicleAssembleNo',
		listeners:{
			'blur' : function(field) {
				var vehicleAssembleNo = field.getValue();
				if(vehicleAssembleNo != '' && vehicleAssembleNo != null){
					var form = this.up('form').getForm();
					var array ={driveArchiveVo : {driveArchiveDto :{vehicleAssembleNo: vehicleAssembleNo}}};
					Ext.Ajax.request({
						url:management.realPath('queryVehicleNoByVehicleAssembleNo.action'),
						jsonData:array,
						success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var vehicleInfo = json.driveArchiveVo.archiveVehicleInfoDto;
		    				
		    				form.getFields().items[1].setValue(vehicleInfo.vehicleNo);
		    				form.getFields().items[1].fireEvent('blur',form.getFields().items[1]);
		    				form.getFields().items[5].setValue(vehicleInfo.containerNo);
		    				form.getFields().items[7].setValue(vehicleInfo.frequencyNo);
		    				management.editDepartureInfoForm.items.items[10].setValue(vehicleInfo.examineVolume);
		    				management.editDepartureInfoForm.items.items[15].setValue(vehicleInfo.ratedLoad);
		    				management.editDepartureInfoForm.getForm().findField('lineName').setValue(vehicleInfo.lineName);
		    				if(vehicleInfo.actualDepartTime != null){
		    					management.editDepartureInfoForm.items.items[6].setValue(Ext.Date.format(new Date(vehicleInfo.actualDepartTime), 'Y-m-d H:i:s'));
		    				}
		    				if(vehicleInfo.actualArriveTime != null){
			    				management.editTimeInfoForm.items.items[5].setValue(Ext.Date.format(new Date(vehicleInfo.actualArriveTime), 'Y-m-d H:i:s'));
		    				}
		    				management.editTimeInfoForm.items.items[6].setValue(vehicleInfo.preion);
		    				//加载线路
		    				var cmbLineName = management.editDepartureInfoForm.getForm().findField('lineNo');
		    				cmbLineName.rawValue = null;
	        				cmbLineName.getStore().load({params:{'lineVo.lineEntity.simpleCode' : vehicleInfo.line}});
	        				cmbLineName.setValue(vehicleInfo.line);
	        				cmbLineName.fireEvent('blur',cmbLineName);
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'),json.message);
		    			}
					});
				}
			}
		}
	},{
		xtype:'commonowntruckselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleNo'),   //车牌号码,
		name:'vehicleNo',
		columnWidth:.25,
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				var vehicleNo = field.getValue();
				if(vehicleNo != '' && vehicleNo != null){
					var array = {driveArchiveVo:{driveArchiveDto:{vehicleNo:vehicleNo}}};
					Ext.Ajax.request({
						url:management.realPath('queryVehicleInfoDriveArchive.action'),
						jsonData:array,
						success : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				var record = json.driveArchiveVo.driveArchiveEntity;
		    				
		    				//小组公共选择器
	        				var cmbGroupOrg = management.editArchiveInfoForm.getForm().findField('groupCode');
	        				cmbGroupOrg.getStore().load({params:{'commonOrgVo.code' : record.groupCode}});
	        				cmbGroupOrg.setValue(record.groupCode);
	        				
	        				//车队
	        				var cmbTransDepartment = management.editArchiveInfoForm.getForm().findField('transDepartment');
	        				cmbTransDepartment.getStore().load({params:{'commonOrgVo.code' : record.transDepartment}});
	        				cmbTransDepartment.setValue(record.transDepartment);
		    				
		    				form.findField('vehicleBrand').setValue(record.vehicleBrand);
		    				form.findField('vehicleTypeLength').setValue(record.vehicleTypeLength);
		    			},
		    			exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'),json.message);
		    			}
					});
				}
			}
		}
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transTeam:'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.groupCode'),   //小组
		columnWidth:.25,
		name:'groupCode'
	},{
		xtype:'dynamicorgcombselector',
		type:'ORG',
		transDepartment:'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.transDepartmentCode'),   //车队
		columnWidth:.25,
		name:'transDepartment'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleBrand'),   //车辆品牌
		columnWidth:.25,
		name:'vehicleBrand'
	},{
		xtype:'commonowntruckselector',
		displayField : 'containerCode',// 显示名称
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		vehicleTypes : 'vehicletype_trailer',
		allowBlank:true,
		readOnly:false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.containerNo'),   //货柜号码
		columnWidth:.25,
		name:'containerNo',
		listeners: {
			'blur': function(field) {
				var form = this.up('form').getForm();
				form.findField('containerNo').setValue(field.rawValue);
			}
		}
	},{
		xtype:'commonvehicletypeselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.vehicleTypeLength'),   //车辆型号
		columnWidth:.25,
		name:'vehicleTypeLength'
	},{
		xtype:'numberfield',
		allowNegative: false,
		allowDecimals: false,
		minValue: 1,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.frequencyNo'),   //班次号
		columnWidth:.25,
		name:'frequencyNo'
	}]
});
//定义edit detail中的发车信息的panel
Ext.define('Foss.Management.EditDepartureInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.title'),   //发车信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		allowBlank:false,
		maxLength:20,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype : 'dynamicorgcombselector',
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.deptRegionName'),   //始发站
		name:'deptRegionCode',
        columnWidth:.25,
        listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var deptRegionNameToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionNameToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionNameToLine.getValue() && '' != deptRegionNameToLine.getValue() && null != arrvRegionNameToLine.getValue() && '' != arrvRegionNameToLine.getValue()){
					var lineNameStr = deptRegionNameToLine.rawValue + '-' + arrvRegionNameToLine.rawValue;
					var lineCodeStr = deptRegionNameToLine.getValue() + '-' + arrvRegionNameToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineNo');
    				cmbLineName.setValue(lineNameStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : lineNameStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.setValue(lineCodeStr);
    				}
				}
				form.findField('deptRegionName').setValue(field.rawValue);
			}
		}
	},{
		name:'deptRegionName',
		hidden:true
	},{
		xtype : 'dynamicorgcombselector',
		types : 'ORG,AIRPORT,PX,KY',
		salesDepartment: 'Y',
		transferCenter: 'Y',
		doAirDispatch: 'Y',
		billingGroup: 'Y',
		isDeliverSchedule: 'Y',
		airDispatch: 'Y',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arrvRegionName'),   //目的站
		name:'arrvRegionCode',
        columnWidth:.25,
        listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var deptRegionNameToLine = form.findField('deptRegionCode');//获取出发站
				var arrvRegionNameToLine = form.findField('arrvRegionCode'); //获取目的站
				if(null != deptRegionNameToLine.getValue() && '' != deptRegionNameToLine.getValue() && null != arrvRegionNameToLine.getValue() && '' != arrvRegionNameToLine.getValue()){
					var lineNameStr = deptRegionNameToLine.rawValue + '-' + arrvRegionNameToLine.rawValue;
					var lineCodeStr = deptRegionNameToLine.getValue() + '-' + arrvRegionNameToLine.getValue();
					lineNameStr = lineNameStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
					lineCodeStr = lineCodeStr.replace(/(^\s+)|(\s+$)/g,"").replace(/\s/g,"");
    				var cmbLineName = form.findField('lineNo');
    				cmbLineName.setValue(lineNameStr);
    				cmbLineName.getStore().load({params:{'lineVo.lineEntity.lineName' : lineNameStr}});
    				if(undefined == cmbLineName.getStore().totalCount || cmbLineName.getStore().totalCount == 0)
    				{
    					cmbLineName.setValue(null);	
    				}else{
    					cmbLineName.setValue(lineCodeStr);
    				}
				}
				form.findField('arrvRegionName').setValue(field.rawValue);
			}
		}
	},{
		name:'arrvRegionName',
		hidden:true
	},{
		xtype:'commonlineselector',
		allowBlank:false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lineNo'),   //线路
		columnWidth:.25,
		name:'lineNo',
		maxLength:50,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength'),     //长度已超过最大限制!
		listeners: {
			'blur': function(field){
				if(field.valueModels != null){
					if(field.getValue() != '' && field.getValue() != null){
						var form = this.up('form').getForm();
						var frequencyNo = management.editVehicleInfoForm.getValues().frequencyNo;
						var lineNo = field.getValue();
						var baseDate = form.getFields().items[6].getValue();
						if(field.rawValue != ''){
							form.getFields().items[5].setValue(field.rawValue);
						}
						var array = {driveArchiveVo:{driveArchiveDto:{lineNo:lineNo,frequencyNo:frequencyNo,baseDate:baseDate}}};
						Ext.Ajax.request({
							url:management.realPath('queryLineInfoByLineSequence.action'),
							jsonData:array,
							success : function(response) {
								var json = Ext.decode(response.responseText);
			    				var record = Ext.create('Foss.Management.ArchiveModel');
			    				var lineInfo = json.driveArchiveVo.driveArchiveLineInfoDto;
			    				//将后台的数据录入到record
			    				management.editTimeInfoForm.items.items[0].setValue(lineInfo.stipulateDepartureTime);
			    				management.editTimeInfoForm.items.items[1].setValue(lineInfo.stipulateArriveTime);
			    				management.editTimeInfoForm.items.items[2].setValue(lineInfo.standardPreion);
			    				
			    				
			    				var cmbDeptRegionCode = management.editDepartureInfoForm.getForm().findField('deptRegionCode'); //出发站
								cmbDeptRegionCode.setValue(lineNo.split('-')[0]);
								cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : lineInfo.deptRegionName}});
								
								var cmbArrvRegionCode = management.editDepartureInfoForm.getForm().findField('arrvRegionCode'); //目的站
								cmbArrvRegionCode.setValue(lineNo.split('-')[1]);
								cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : lineInfo.arriveRegionName}});
								
								management.editDepartureInfoForm.getForm().findField('deptRegionName').setValue(lineInfo.deptRegionName);
								management.editDepartureInfoForm.getForm().findField('arrvRegionName').setValue(lineInfo.arriveRegionName);
								
			    			},
			    			exception : function(response) {
			    				var json = Ext.decode(response.responseText);
			    				Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'),json.message);
			    			}
						});
					}
				}
			}
		}
	},{
		name:'lineName',
		hidden:true
	},{
		xtype:'datetimefield_date97',
    	fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.departureTime'),   //实际发车时间
    	allowBlank:false,
    	id:Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editDepartureInfoForm_departureTimeId',
		time:true,
		name:'departureTime',
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editDepartureInfoForm_departureTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth:.25
	},{
		xtype:'numberfield',
		allowNegative: false,
		minValue: 0,
		columnWidth:.25,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.startKm'),   //出发公里数
		name:'startKm',
		listeners: {
			'blur': function(field) {
				var arriveKm = management.editDepartureInfoForm.items.items[8].getValue();
				var runKm = Ext.Number.from(arriveKm,0) - Ext.Number.from(field.getValue(),0);
				management.editDepartureInfoForm.items.items[9].setValue(runKm);
			}
		},
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',		
		allowNegative: false,
		minValue: 0,
		columnWidth:.25,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.arriveKm'),   //到达公里数
		width:240,
		name:'arriveKm',
		listeners: {
			'blur': function(field) {
				var startKm = management.editDepartureInfoForm.items.items[7].getValue();
				if(startKm >= field.getValue()){
					Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'),"到达公里数要大于出发公里数");
					return;
				}
				var runKm = Ext.Number.from(field.getValue(),0) - Ext.Number.from(startKm,0);
				management.editDepartureInfoForm.items.items[9].setValue(runKm);
			}
		},
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		columnWidth:.25,
		readOnly:true,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.runKm'),   //行驶公里数
		name:'runKm',
		listeners: {
			'change': function(field){
				var fuelQty = management.editFuelConsumptionInfoForm.items.items[0].getValue();
				if( null === field.getValue || field.getValue() === 0){
					return;
				}
				var onKmFuelConsumption = Ext.Number.from(fuelQty,0)/Ext.Number.from(field.getValue(),0) * 100;
				management.editFuelConsumptionInfoForm.items.items[2].setValue(onKmFuelConsumption);
			}
		},
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		allowNegative: false,
		minValue: 0,
		columnWidth:.25,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.departureInfoForm.label.volume'),   //运输体积
		name:'volume',
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'commonowndriverselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver1Code'),   //司机1
		columnWidth:.25,
		name:'driver1Code',
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				form.getFields().items[12].setValue(field.rawValue);
			}
		}
	},{
		name:'driver1Name',
		hidden:true
	},{
		xtype:'commonowndriverselector',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driver2Code'),   //司机2
		columnWidth:.25,
		width:240,
		name:'driver2Code',
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				form.getFields().items[14].setValue(field.rawValue);
			}
		}
	},{
		name:'driver2Name',
		hidden:true
	},{
		xtype:'numberfield',
		allowNegative: false,
		minValue: 0,
		columnWidth:.25,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.weight'),   //运输重量
		name:'weight',
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	}]
});

//定义edit detail中的油耗信息的panel
Ext.define('Foss.Management.EditFuelConsumptionInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.title'),   //油耗信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'numberfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:false,
		maxLength:10,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype:'numberfield',
		name:'fuelQty',
		allowNegative: false,
		allowBlank:true,
		minValue: 0,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.fuelQty'),   //加油升数
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var fuelPrice = form.getFields().items[1].getValue();
				var fuelFee = Ext.Number.from(field.getValue(),0) * Ext.Number.from(fuelPrice,0);
				management.editFeeInfoForm.getForm().getFields().items[2].setValue(fuelFee);
				var runKm = management.editDepartureInfoForm.getForm().getFields().items[9].rawValue;
				if( null === runKm || runKm === 0){
					return;
				}
				var onKmFuelConsumption = Ext.Number.from(field.getValue(),0)/Ext.Number.from(runKm,0) * 100;
				management.editFuelConsumptionInfoForm.items.items[2].setValue(onKmFuelConsumption);
			}
		},
  		validator : function(value) {
 	         if(!Ext.isEmpty(value) && value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		allowNegative: false,
		allowBlank:true,
		minValue: 0,
		name:'fuelPrice',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.fuelPrice'),   //加油单价
		columnWidth:.25,
		listeners: {
			'blur': function(field){
				var form = this.up('form').getForm();
				var fuelQty = form.getFields().items[0].getValue();
				var fuelFee = Ext.Number.from(field.getValue(),0) * Ext.Number.from(fuelQty,0);
				management.editFeeInfoForm.items.items[2].setValue(fuelFee);
			}
		},
  		validator : function(value) {
  			 if(!Ext.isEmpty(value) && value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		name:'onKmFuelConsumption',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.fuelConsumptionInfoForm.label.onKmFuelConsumption'),   //百公里油耗
		//readOnly:true,
		minValue: 0,
		allowBlank:false,
		columnWidth:.25,
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	}]
});

//定义edit detail中的费用信息的panel
Ext.define('Foss.Management.EditFeeInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.editFeeInfoForm.title'),   //费用信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 90,
		allowBlank:true,
		maxLength:10,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype:'numberfield',
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.signBillFee'),   //签单费
		columnWidth:.25,
		name:'signBillFee',
  		validator : function(value) {
 	         if(value <= 0 && value != '') {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.driverRoyalty'),   //司机提成
		columnWidth:.25,
		name:'driverRoyalty',
  		validator : function(value) {
 	         if(value <= 0 && value != '') {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fuelFee'),   //燃油费
		//readOnly:true,
		minValue: 0,
		allowBlank:false,
		columnWidth:.25,
		name:'fuelFee',
  		validator : function(value) {
 	         if(value <= 0 && value != '') {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'numberfield',
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.tollFee'),   //路桥费
		columnWidth:.25,
		name:'tollFee',
  		validator : function(value) {
 	         if(value <= 0 && value != '') {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineType'),   //罚款类型
		columnWidth:.25,
		name:'fineType',
		maxLength:20,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},{
		xtype:'numberfield',
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.fineAmount'),   //罚款金额
		columnWidth:.25,
		name:'fineAmount',
  		validator : function(value) {
 	         if(value <= 0 && value != '') {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	}]
});

//定义edit detail中的时效信息的panel
Ext.define('Foss.Management.EditTimeInfoForm',{
	extend: 'Ext.form.Panel',
	title: management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.timeInfoForm.title'),   //时效信息
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 85,
		allowBlank:true,
		maxLength:20,
	    maxLengthText:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.maxLength')     //长度已超过最大限制!
	},
	layout:'column',
	items:[{
		xtype:'datetimefield_date97',
    	fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.stipulateDepartureTime'),   //规定发车时间
    	allowBlank:false,
    	id:Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_stipulateDepartureTimeId',
		time:true,
		name:'stipulateDepartureTime',
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_stipulateDepartureTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		width:240
	},{
		xtype:'datetimefield_date97',
    	fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.stipulateArriveTime'),   //规定到达时间
    	allowBlank:false,
    	id:Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_stipulateArriveTimeId',
		time:true,
		name:'stipulateArriveTime',
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_stipulateArriveTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		width:240
	},{
		xtype: 'numberfield',
		allowBlank:false,
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.standardPrescription'),   //标准时效
		labelWidth:60,
		width:210,
		name:'standardPreion',
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		xtype:'radiogroup',
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateDeparture'),   //是否晚发
		labelWidth:60,
		width:150,
		items:[{
			name: 'isLateDeparture',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:40,  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			name: 'isLateDeparture',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			width:40,
			checked :true,
			inputValue: 'N',  //表单的参数值
		}]
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.archiveUserCode'),   //制单人
		width:240,
		readOnly : true,
		value:FossUserContext.getCurrentUser().employee.empName,
		name:'archiveUserName'
	},{
		xtype:'datetimefield_date97',
    	fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.arriveTime'),   //实际到达时间
    	allowBlank:false,
    	id:Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_arriveTimeId',
		time:true,
		name:'arriveTime',
		editable:false,
		dateConfig: {
			el: Ext.Date.format(new Date(),'YmdHis')+'Foss_management_editTimeInfoForm_arriveTimeId-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		width:240
	},{
		xtype: 'numberfield',
		allowBlank:false,
		allowNegative: false,
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.prescription'),   //实际时效
		labelWidth:60,
		width:210,
		name:'preion',
  		validator : function(value) {
 	         if(value <= 0) {
 	        	 return management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.enterGreaterThanZero');  //请输入大于0的值!
 	         } 
 	         return true;
 	    }
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isLateArrive'),   //是否晚到
		xtype:'radiogroup',
		labelWidth:60,
		width:150,
		items:[{
			name:'isLateArrive',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:40,  //表单的参数名
			inputValue: 'Y'  //表单的参数值
		},{
			name:'isLateArrive',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			checked :true,
			width:40,
			inputValue: 'N',  //表单的参数值
		}]
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lateDepartureReason'),   //晚发原因
		width:240,
		name:'lateDepartureReason'
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.lateArriveReason'),   //晚到原因
		width:240,
		name:'lateArriveReason'
	},{
		allowBlank:true,
		readOnly:true,
		width:210
	},{
		fieldLabel:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.label.isMutual'),   //是否对发
		labelWidth:60,
		xtype:'radiogroup',
		width:150,
		items:[{
			name:'isMutual',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.yes'),   //是  
			width:40,  //表单的参数名
			checked :true,
			inputValue: 'Y'  //表单的参数值
		},{
			name:'isMutual',
			boxLabel  : management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.tip.no'),   //否
			width:40,
			inputValue: 'N',  //表单的参数值
		}]
	},{
		hidden:true,
		maxLength:100,
		name:'id'
	}]
});
/*-----------编辑panel over------------------*/

management.vehicleInfoForm = Ext.create('Foss.Management.VehicleInfoForm');
management.departureInfoForm = Ext.create('Foss.Management.DepartureInfoForm');
management.fuelConsumptionInfoForm = Ext.create('Foss.Management.FuelConsumptionInfoForm');
management.feeInfoForm = Ext.create('Foss.Management.FeeInfoForm');
management.timeInfoForm = Ext.create('Foss.Management.TimeInfoForm');

//定义包含4个子panel的detail展示panel
Ext.define('Foss.Management.ArchiveInfoForm',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	cls: "panelContentNToolbar",
	frame: true,
	items: [
		management.vehicleInfoForm
	,
		management.departureInfoForm
	,
		management.fuelConsumptionInfoForm
	,
		management.feeInfoForm
	,
		management.timeInfoForm
	,{
    	xtype:"button",
		width:90,
    	text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.close'),   //关闭
    	handler: function() {
        	var detailInfoWindow = management.detailInfoWindow ;
			detailInfoWindow.hide();
			
    	}
    }]
    
});

management.editVehicleInfoForm = Ext.create('Foss.Management.EditVehicleInfoForm');
management.editDepartureInfoForm = Ext.create('Foss.Management.EditDepartureInfoForm');
management.editFuelConsumptionInfoForm = Ext.create('Foss.Management.EditFuelConsumptionInfoForm');
management.editFeeInfoForm = Ext.create('Foss.Management.EditFeeInfoForm');
management.editTimeInfoForm = Ext.create('Foss.Management.EditTimeInfoForm');

//定义包含4个子edit detail panel的编辑panel
Ext.define('Foss.Management.EditArchiveForm',{
	extend: 'Ext.form.Panel',
	bodyCls: 'panelContentNToolbar-body',
	layout: 'auto',
	cls: "panelContentNToolbar",
	frame: true,
	items: [
	    management.editVehicleInfoForm
	,
		management.editDepartureInfoForm
	,
		management.editFuelConsumptionInfoForm
	,
		management.editFeeInfoForm
	,
		management.editTimeInfoForm
	,{
		xtype:"button",
		width:90,
		disabled:management.driveArchiveIndex.isPermission('management/saveOrUpdateButton')?false:true,
		hidden:management.driveArchiveIndex.isPermission('management/saveOrUpdateButton')?false:true,
		text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.save'),   //保存
		handler: function() {
			var form = this.up('form').getForm();
			var btnSave = this;
	    	if(form.isValid()){
	    		var formValues = form.getValues();
	    		//定义几个用于存储时间的变量
	    		var departureTime = "";
	    		var arriveTime = "";
	    		var stipulateDepartureTime = "";
	    		var stipulateArriveTime = "";
	    		//给临时变量赋值，同时清空自己本身的值，这是为了向实体设置值的时候不出现格式转化错误
    			departureTime = formValues.departureTime;
    			formValues.departureTime = null;
    			
    			arriveTime = formValues.arriveTime;
    			formValues.arriveTime = null;
    			
    			stipulateDepartureTime = formValues.stipulateDepartureTime;
    			formValues.stipulateDepartureTime = null;
    			
    			stipulateArriveTime = formValues.stipulateArriveTime;
    			formValues.stipulateArriveTime = null;
    			
    			//往DTO传值，由于直接传date类型的数据不兼容火狐浏览器，所以不能直接将时间格式的数据传入实体，于是前面用的临时变量和清空自己本身的值都是为了能够将除时间格式以外其他的值直接传入实体
    			//时间格式的值单独传入DTO中对应的属性，在service重新添加
	    		btnSave.setDisabled(true);
	    		var array = {driveArchiveVo:{driveArchiveDto:{departureTime:departureTime,arriveTime:arriveTime,stipulateDepartureTime:stipulateDepartureTime,stipulateArriveTime:stipulateArriveTime,driveArchiveEntity:formValues}}};
	    		Ext.Ajax.request({
					url : management.realPath('saveOrUpdate.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.message'), management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.saveSuccess'), 'ok', 1000);     //提示保存成功
				    	var detailInfoWindow = management.editDetailInfoWindow;
						detailInfoWindow.hide();
		            	management.id = json.driveArchiveVo.driveArchiveDto.id;
						management.resultPanel.store.load();
			    		btnSave.setDisabled(false);
						
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
			    		btnSave.setDisabled(false);
					},
					error : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.msg.failure'),json.message);
			    		btnSave.setDisabled(false);
					}
	    		});
	    	}
		}
	},{
    	xtype:"button",
		width:90,
    	text:management.driveArchiveIndex.i18n('foss.management.driveArchiveIndex.button.reset'),   //重置
    	handler: function() {
        	
    		//清理页面数据
        	this.up('form').getForm().reset();
        	//拿全局变量重新填充进表单
        	var record = management.driveArchiveData.record;
        	
			var cmbDeptRegionCode = management.editDepartureInfoForm.getForm().findField('deptRegionCode'); //出发站-编辑初始化
			cmbDeptRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.deptRegionName}});
			cmbDeptRegionCode.setValue(record.data.deptRegionName);
			
			var cmbArrvRegionCode = management.editDepartureInfoForm.getForm().findField('arrvRegionCode'); //目的站-编辑初始化
			cmbArrvRegionCode.getStore().load({params:{'commonOrgVo.name' : record.data.arrvRegionName}});
			cmbArrvRegionCode.setValue(record.data.arrvRegionName);
        	
        	this.up('form').getForm().loadRecord(record);
        	
    	}
    }]
});
//定义弹出的窗口
Ext.define('Foss.Management.DetailInfoWindows',{
	extend: 'Ext.window.Window',
	closeAction: 'hide',
	closable:true,
	width: 1000,
	modal:true,
	bodyCls: 'autoHeight',
	resizable:false,
	archiveInfoForm : null,
	getArchiveInfoForm: function(){
		if(this.archiveInfoForm==null){
			this.archiveInfoForm = Ext.create('Foss.Management.ArchiveInfoForm');
			management.archiveInfoForm = this.archiveInfoForm;
		}
		return this.archiveInfoForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getArchiveInfoForm()
				];
		me.callParent([cfg]);
	}
});

management.detailInfoWindow = Ext.create('Foss.Management.DetailInfoWindows')

//定义弹出的编辑窗口
Ext.define('Foss.Management.EditDetailInfoWindows',{
	extend: 'Ext.window.Window',
	closeAction: 'hide',
	closable:true,
	width: 1000,
	modal:true,
	bodyCls: 'autoHeight',
	resizable:false,
	editArchiveInfoForm : null,
	getEditArchiveInfoForm: function(){
		if(this.editArchiveInfoForm==null){
			this.editArchiveInfoForm = Ext.create('Foss.Management.EditArchiveForm');
			management.editArchiveInfoForm = this.editArchiveInfoForm;
		}
		return this.editArchiveInfoForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
				    me.getEditArchiveInfoForm()
				];
		me.callParent([cfg]);
	}
});

management.editDetailInfoWindow = Ext.create('Foss.Management.EditDetailInfoWindows');

Ext.onReady(function() {
	Ext.QuickTips.init();
	management.driveArchiveIndex.queryPanel = Ext.create('Foss.management.driveArchiveIndex.queryPanel');
	management.resultPanel = Ext.create('Foss.Management.QueryResult');
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-driveArchiveIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [management.driveArchiveIndex.queryPanel,management.resultPanel],
		renderTo: 'T_management-driveArchiveIndex-body'
	});
});
