//库存状态
Ext.define('Foss.airfreight.StockStatusStore',{
	extend:'Ext.data.Store',
	fields:[
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data:{
		'items':[ 
			{code:'',name:backupquery.airCargoVolume.i18n('foss.airfreight.all')},	
			{code:'NOTLEAVE',name:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.billing')},
			{code:'HANDOVER',name: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.handover')},
			{code:'INSTORE',name: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.instore')},
			{code:'Y',name: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.load')}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type: 'json',
			root:'items'
		}
	}
});

//查询面板
Ext.define('Foss.airfreight.queryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_airfreight_QueryAirCarVolumeForm_ID',
	layout: 'column',
	frame: true,
	border: false,
	title : backupquery.airCargoVolume.i18n('foss.airfreight.airSpaceQueryForm.title'),	
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 75
	},
	layout:'column',
	defaults: {
		margin: '5 5 5 5'
	},
	items: [{
		xtype: 'combo',
		fieldLabel: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.stockStatus'),
		name: 'stockStatus',
		displayField: 'name',
		valueField:'code', 
		columnWidth:.25,
		//因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		//如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode:'local',
		//默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction:'all',
		//延时查询，与下面的参数配合
		//typeAhead:true,
		//默认250
		//typeAheadDelay:3000,
		//false则不可编辑，默认为 true	
		value: '',
		editable:false,	
		store:Ext.create('Foss.airfreight.StockStatusStore')		
	},{		
		xtype: 'commoncityselector',
		fieldLabel: backupquery.airCargoVolume.i18n('foss.airfreight.arrvRegionName'),//目地站
		name: 'arrvRegionCode',
		columnWidth:.25,
		listeners: {
			'change': function(field){
				var form = this.up('form').getForm();
				if(null != field.value && '' != field.value){
					form.findField('arrvRegionName').setValue(field.rawValue);
				}else{
					form.findField('arrvRegionName').setValue('');
				}
			}
		}
	},{
		name:'arrvRegionName',
		xtype:'textfield',
		hidden:true
	},FossDataDictionary.getDataDictionaryCombo('AIR_FLIGHT_TYPE',{
		name: 'flightType',
		fieldLabel: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.flightType'),
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .25
		},{
			'valueCode':'',
			'valueName': backupquery.airCargoVolume.i18n('foss.airfreight.all')
	}),{
		xtype:'dynamicorgcombselector',
		fieldLabel:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.assembleOrgName'),
		name: 'assembleOrgName',	
		type:'ORG',
		doAirDispatch:'Y',
		readOnly:true,
		allowBlank: false,
		columnWidth: .25
	},{
		xtype:'radiogroup',
		name: 'dateType',  
		columnWidth: .2,
		items:[{
			boxLabel  : backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.rangeDateField'),
			name      : 'dateType',  
			checked : true,
			inputValue: 'createTime'  //表示是开单时间
		},{
			boxLabel  : backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.handoverTime'),
			name      : 'dateType',  
			inputValue: 'handoverTime'  //表示是配载时间
		}]
	},{
		xtype: 'rangeDateField',
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_airfreight_queryAirCarVolumeForm_createTime',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		dateRange : 30,
		allowBlank:false,
		disallowBlank:true,
		fromName: 'beginTime',
		toName: 'endTime',
		format:'Y-m-d H:i:s',
		fromValue:Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',
		allowBlank: false,
		columnWidth: .40
	},{
		xtype : 'container',
		columnWidth : .5,
		html : '&nbsp;'
	},{				
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items : [{
			text : backupquery.airCargoVolume.i18n('foss.airfreight.airSpaceQueryForm.button.reset'),  //重置
			columnWidth : .1,
			handler : function() {
				this.up('form').getForm().reset();	
				this.up('form').getForm().findField('beginTime')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
				var cmbOrgCode = this.up('form').getForm().findField('assembleOrgName');				
				if(backupquery.airCargoVolume.dept.airDispatch == 'Y'){
					cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airCargoVolume.dept.name}});
					cmbOrgCode.setValue(backupquery.airCargoVolume.dept.code);
				}

			}
		},{
			xtype: 'container',
			columnWidth:.7,
			html: '&nbsp;'
		},{
			text : backupquery.airCargoVolume.i18n('foss.airfreight.airSpaceQueryForm.button.search'),//查询
			columnWidth : .1,
			cls:'yellow_button',
			handler : function() {
				var form = this.up('form').getForm();
		    	if(form.isValid()){
		    		backupquery.airCargoVolume.pagingBar.moveFirst();
					backupquery.airCargoVolume.store.load();
		    	}
			}
		},{
			text: backupquery.airCargoVolume.i18n('foss.airfreight.airSpaceQueryForm.button.export'), //导出
			cls:'yellow_button',
			columnWidth:.1,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 2
			}),
			handler:function(){
				var form = this.up('form').getForm();
				var queryParams = form.getValues();
				
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				if(queryParams.dateType === 'handoverTime'){
					array = {
						'airCargoVolumeVo.assembleOrgName' : queryParams.assembleOrgName,  //配载部门								
						'airCargoVolumeVo.handoverTimeBegin' :queryParams.beginTime,	 //配载日期开始
						'airCargoVolumeVo.handoverTimeEnd' : queryParams.endTime,	//配载日期结束
						'airCargoVolumeVo.airCargoVolumeEntity.arrvRegionName' : queryParams.arrvRegionName, //目的站
						'airCargoVolumeVo.airCargoVolumeEntity.stockStatus' : queryParams.stockStatus, //库存状态
						'airCargoVolumeVo.airCargoVolumeEntity.flightType' :queryParams.flightType //航班类型
					};
				}else{
					array = {
							'airCargoVolumeVo.assembleOrgName' : queryParams.assembleOrgName,  //配载部门								
							'airCargoVolumeVo.beginCreateTime' :queryParams.beginTime,	 //日期开始
							'airCargoVolumeVo.endCreateTime' : queryParams.endTime,	//日期结束
							'airCargoVolumeVo.airCargoVolumeEntity.arrvRegionName' : queryParams.arrvRegionName, //目的站
							'airCargoVolumeVo.airCargoVolumeEntity.stockStatus' : queryParams.stockStatus, //库存状态
							'airCargoVolumeVo.airCargoVolumeEntity.flightType' :queryParams.flightType //航班类型
						};
				}
				Ext.Ajax.request({
					url:backupquery.realPath('airCargoVolumeExportExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : array,
	    			isUpload: true,
	    			success : function(response) {
	    				Ext.ux.Toast.msg("提示","导出失败", 'ok', 1000);   //提示导出失败
	    			},
	    			error : function(response) {
	    				var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert("提示",json.message);
	    			}
				});
			}
		}]
	}
	],	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		render : function(panel,text){
			var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
			Ext.Ajax.request({
				url : backupquery.realPath('queryAirDispatch.action'),
				jsonData:array,
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
					backupquery.airCargoVolume.dept = dept;
					var cmbOrgCode = panel.getForm().findField('assembleOrgName');				
					if(backupquery.airCargoVolume.dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : backupquery.airCargoVolume.dept.name}});
						cmbOrgCode.setValue(backupquery.airCargoVolume.dept.code);
					}
				}
			});
						
		}
	}
});

//airCargoVolume model
Ext.define('Foss.airfreight.airCargoVolumeModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo' , type: 'string'},
		{name: 'createOrgCode', type: 'string'},
		{name: 'createOrgName', type: 'string'},
		{name: 'arrvRegionCode', type: 'string'},	
		{name: 'arrvRegionName', type: 'string'},		
		{name: 'createTime',type:'string'},
		{name: 'targetOrgCode', type: 'string'},
		{name: 'stockStatus', type: 'string'},
		{name: 'makeWaybillWay', type: 'string'},
		{name: 'flightType', type: 'string'},
		{name: 'goodsSize', type: 'string'},
		{name: 'goodsWeight'},
		{name: 'goodsVolume'},
		{name: 'goodsName', type: 'string'},
		{name: 'billWeight', type: 'string'},
		{name: 'flightNo', type: 'string'},
		{name: 'pickupType', type: 'string',
			convert : function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'PICKUPGOODSAIR');
			}
		},
		{name: 'deptOrgName', type: 'string'},
		{name: 'airWaybillNo', type: 'string'},
		{name: 'fee', type: 'string'},
		{name: 'handoverTime', type: 'string'},
		//ISSUE-3780 统计空运货量中增加收货部门和代收货款字段
		{name:'receviceDept',type:'string'},
		{name:'codAmount',type:'string'}
	]
});


//airSpaceDetail   Model  线路舱位明细列表
Ext.define('Foss.airfreight.airSpaceDetailModel',{
	extend: 'Ext.data.Model',
	fields: [      
		{name: 'arrvRegionName' , type: 'string'},
		{name: 'takeOffDate' ,type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
			}	
		},
		/*
		{name: 'todaySpaceTotal', type: 'string'},		
		{name: 'tomorrowSpaceTotal', type: 'string'},		
		{name: 'commonSpaceTotal', type: 'string'},
		 */
		{name: 'earlySpaceTotal', type: 'string'},		
		{name: 'middaySpaceTotal', type: 'string'},		
/*		{name: 'nightSpaceTotal', type: 'string'},			
*/		{name: 'transitSpaceTotal', type: 'string'}
	]
});


//airCargoVolume Store
Ext.define('Foss.airfreight.airCargoVolumeStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.airCargoVolumeModel',
	pageSize:5,
	autoLoad: false,
	proxy: {
	        type: 'ajax',
	        url: backupquery.realPath('queryAirCargoVolume.action'),//"queryAirCargoVolume"
	        actionMethods: {read: 'POST'},
	        reader: {
	            type: 'json',
	            root: 'airCargoVolumeVo.airCargoVolumeList',
	            totalProperty : 'totalCount',
	            successProperty: 'success'
	        }
	    },
		listeners: {			
			datachanged: function(store, operation, epots){
				var queryForm = backupquery.queryForm;
				if (queryForm != null) {
					var goodsWeightTotal=0;//总重量
					var goodsVolumeTotal=0;//总体积
					var queryParams = queryForm.getValues();
					var assembleOrgName= queryParams.assembleOrgName;					
					var beginTime=queryParams.beginTime;
					var endTime=queryParams.endTime;					
					var arrvRegionName=queryParams.arrvRegionName;
					var stockStatus=queryParams.stockStatus;
					var flightType=queryParams.flightType;
					var array=null;
					if(queryParams.dateType === 'handoverTime'){
						array={airCargoVolumeVo:{handoverTimeBegin:beginTime,handoverTimeEnd:endTime,assembleOrgName:assembleOrgName,airCargoVolumeEntity:{arrvRegionName:arrvRegionName,stockStatus:stockStatus,flightType:flightType}}};
					}else{
						array={airCargoVolumeVo:{assembleOrgName:assembleOrgName,beginCreateTime:beginTime,endCreateTime:endTime,airCargoVolumeEntity:{arrvRegionName:arrvRegionName,stockStatus:stockStatus,flightType:flightType}}};
					}
					Ext.Ajax.request({
						url : backupquery.realPath('queryAirCargoVolumeTotal.action'),
						jsonData:array,
						success : function(response) {
							var json = Ext.decode(response.responseText);							
							//在json中取出对象
							var airCargoVolumeList = json.airCargoVolumeVo.airCargoVolumeList;						
							if(airCargoVolumeList != null && airCargoVolumeList.length>0 ){	
								if(airCargoVolumeList[0]!=null){
									goodsWeightTotal=airCargoVolumeList[0].goodsWeightTotal;
									goodsVolumeTotal=airCargoVolumeList[0].goodsVolumeTotal;
								}
								
								var count = 0;
								var toolbarArray = backupquery.airCargoVolumeGrid.down('toolbar').query('textfield');
								for(var j=0;j<toolbarArray.length;j++){
									if(count==0){
										toolbarArray[j].setValue(goodsWeightTotal);
									}else if(count==1){
										toolbarArray[j].setValue(goodsVolumeTotal);
									}
									count ++;
								}
								 				    	
							}else{
								top.Ext.MessageBox.alert(backupquery.airCargoVolume.i18n('foss.airfreight.messageBox.alert.tip.title'),json.message);
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							top.Ext.MessageBox.alert(backupquery.airCargoVolume.i18n('foss.airfreight.messageBox.alert.tip.title'),json.message);
						}
					});
				}				

			},
			beforeload : function(store, operation, eOpts) {				
				var queryForm = backupquery.queryForm;
				if (queryForm != null) {					
					var queryParams = queryForm.getValues();
					var assembleOrgName= queryParams.assembleOrgName;					
					var beginTime=queryParams.beginTime;
					var endTime=queryParams.endTime;					
					var arrvRegionName=queryParams.arrvRegionName;
					var stockStatus=queryParams.stockStatus;
					var flightType=queryParams.flightType;
					var array=null;
					if(queryParams.dateType === 'handoverTime'){
						array = {
							'airCargoVolumeVo.assembleOrgName' : queryParams.assembleOrgName,  //配载部门								
							'airCargoVolumeVo.handoverTimeBegin' :queryParams.beginTime,	 //配载日期开始
							'airCargoVolumeVo.handoverTimeEnd' : queryParams.endTime,	//配载日期结束
							'airCargoVolumeVo.airCargoVolumeEntity.arrvRegionName' : queryParams.arrvRegionName, //目的站
							'airCargoVolumeVo.airCargoVolumeEntity.stockStatus' : queryParams.stockStatus, //库存状态
							'airCargoVolumeVo.airCargoVolumeEntity.flightType' :queryParams.flightType //航班类型
						};
					}else{
						array = {
								'airCargoVolumeVo.assembleOrgName' : queryParams.assembleOrgName,  //配载部门								
								'airCargoVolumeVo.beginCreateTime' :queryParams.beginTime,	 //日期开始
								'airCargoVolumeVo.endCreateTime' : queryParams.endTime,	//日期结束
								'airCargoVolumeVo.airCargoVolumeEntity.arrvRegionName' : queryParams.arrvRegionName, //目的站
								'airCargoVolumeVo.airCargoVolumeEntity.stockStatus' : queryParams.stockStatus, //库存状态
								'airCargoVolumeVo.airCargoVolumeEntity.flightType' :queryParams.flightType //航班类型
							};
					}
					Ext.apply(operation, {
						params : array
					});	
				}
			},
			load: function(store,records,successful,epots){
			 	
			}
			
	}
});


//airSpaceDetail Store
Ext.define('Foss.airfreight.airSpaceDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.airSpaceDetailModel',	
	autoLoad: false,
	proxy: {
	        type: 'ajax',
	        url: backupquery.realPath('queryAirSpaceForVolume.action'),
	        actionMethods: {read: 'POST'},
	        reader: {
	            type: 'json',
	            root: 'airCargoVolumeVo.airSpaceDetaiList',	           
	            successProperty: 'success'
	        }
	    },
		listeners: {
			beforeload : function(store, operation, eOpts) {				
				var queryForm = backupquery.queryForm;
				if (queryForm != null) {					
					var queryParams = queryForm.getValues();
					Ext.apply(operation, {
						params : {
							'airCargoVolumeVo.airSpaceDetaiEntity.assembleOrgName' : queryParams.assembleOrgName,  //配载部门	
							'airCargoVolumeVo.airSpaceDetaiEntity.arrvRegionName' : queryParams.arrvRegionName //目的站
						}
					});	
				}
			},
			load: function(store,records,successful,epots){
			 	
			}
		}
	
});



//货量Grid （第一层表格）
Ext.define('Foss.airfreight.airCargoVolume.airCargoVolumeGrid', {
	extend:'Ext.grid.Panel',
	//定义表格的标题
	title: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.airCargoVolumeGrid.title'),
	frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    viewConfig: {
    	stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var status = record.get('stockStatus');
			if(status=='INSTORE'){
    			return 'predeliver_notice_customer_row_green';
			}else if(status=='NOTLEAVE'){
			    return 'predeliver_notice_customer_row_orange';
			}else if(status=='Y'){
				return "predeliver_notice_customer_row_white";
			}else{
				return 'predeliver_notice_customer_row_yellow';
			}
		}
	},
	columns: [{
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.waybillNo'), 
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'waybillNo',
			flex: 1.3     
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.createOrgName'), 
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'createOrgName',
			flex: 1.3  
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.handoverTime'), 
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'handoverTime',
			flex: 1.3  
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.arrvRegionName'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'arrvRegionName',
			flex: 1.3  
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.airWaybillNo'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'airWaybillNo' ,
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.flightNo'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'flightNo' ,
			flex: 1.3 
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.stockStatus'),
			//关联model中的字段名
			dataIndex: 'stockStatus',
			renderer:function(value){
				if(value=='NOTLEAVE') return backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.billing');
				else if(value=='INSTORE') return backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.instore');
				else  if(value=='Y') return backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.load');
				else  return backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.handover');
				},
				flex: 1.3 
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.makeWaybillWay'),
			//关联model中的字段名
			dataIndex: 'makeWaybillWay',	
				renderer: function(value){
					return FossDataDictionary.rendererSubmitToDisplay(value,'MAKE_WAYBILL_WAY');
				},
				flex: 1.3 
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.flightType'),	
			//关联model中的字段名
			dataIndex: 'flightType' ,	
			renderer: function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
			},
			flex: 1.3 		
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.goodsSize'),	
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'goodsSize',
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.goodsWeight'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'goodsWeight' ,
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.billWeight'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'billWeight' ,
			flex: 1.3 
		},{ 
			//字段标题
			header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.goodsVolume'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'goodsVolume',
			flex: 1.3  
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.fee'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'fee' ,
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.goodsName'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'goodsName' ,
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.pickupType'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'pickupType' ,
			flex: 1.3
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.deptOrgName'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'deptOrgName' ,
			flex: 1.3 
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.receviceDept'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'receviceDept' ,
			flex: 1.3
		},{ 
			//字段标题
			header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.codAmount'),
	    	xtype : 'ellipsiscolumn',
			//关联model中的字段名
			dataIndex: 'codAmount' ,
			flex: 1.3 
		}],
		dockedItems:[{
			   xtype:'toolbar',
			   dock:'bottom',
			   layout:'column',
			   defaults:{
				 xtype:'textfield',
				 value:'0',
				 readOnly:true,
				 labelWidth:60,
				 width:30
			   },
			   items:[{
				   fieldLabel:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.weightTotal'),	
				   labelWidth:70,
				   columnWidth:.15,
				   dataIndex: 'weightTotal'
			   },{
				   fieldLabel:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.volumeTotal'),
				   columnWidth:.15,
				   dataIndex: 'volumeTotal'
			   }]
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.airfreight.airCargoVolumeStore');
			me.bbar =Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			backupquery.airCargoVolume.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});

//提示信息
Ext.define('Foss.airfreight.airCargoVolume.TaskExplainContainer',{
	extend:'Ext.container.Container',
	layout:'column',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	margin: '5 0 5 60',
	defaults: {
		xtype : 'panel',
		border : false,
		height: 14,
		width: 18
	},
	items:[{
			margin: '2 5 0 0',
			cls: 'predeliver_notice_customer_row_white'
	},{
		html:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.load'),
		columnWidth:.25
	},{
			margin: '2 5 0 0',
			cls: 'predeliver_notice_customer_row_green'
	},{
		html:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.instore'),
		columnWidth:.25
	},{
			margin: '2 5 0 0',
			cls: 'predeliver_notice_customer_row_orange'
	},{
		html:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.billing'),
		columnWidth:.25
	},{
			margin: '2 5 0 0',
			cls: 'predeliver_notice_customer_row_yellow'
	},{
		html:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.handover'),
		columnWidth:.25
	}]
});


//线路舱位
Ext.define('Foss.airfreight.airCargoVolume.airSpaceDetailGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.airSpaceDetailGrid.title'),
	cls:'autoHeight',
	bodyCls:'autoHeight',
	emptyText : backupquery.airCargoVolume.i18n('foss.airfreight.emptyText'),
	store: null,
	selModel: null,	
	autoScroll:true,	
	columns: [{		
		flex: 1, 
		sortable: false, 
		//字段标题
		header:  backupquery.airCargoVolume.i18n('foss.airfreight.arrvRegionName'),
		//关联model中的字段名
		dataIndex: 'arrvRegionName' 
	},{
		flex: 1, 
		sortable: false, 
		//字段标题
		header:  backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.takeOffDate'),
		//关联model中的字段名
		dataIndex: 'takeOffDate'
	},{
		flex: 1, 
		sortable: false, 
		//字段标题
		header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.earlySpaceTotal'), //即日达总仓位</br>(公斤)
		//关联model中的字段名
		dataIndex: 'earlySpaceTotal'
	},{
		flex: 1, 
		sortable: false, 
		//字段标题
		header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.middaySpaceTotal'), //次日达总仓位</br>(公斤), 
		//关联model中的字段名
		dataIndex: 'middaySpaceTotal'
	},{
		flex: 1, 
		sortable: false, 
		//字段标题
		header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.transitSpaceTotal'), //航空普运总仓位</br>(公斤) 
		//关联model中的字段名
		dataIndex: 'transitSpaceTotal'
	}/*,{
		flex: 1, 
		sortable: false, 
		//字段标题
		header: backupquery.airCargoVolume.i18n('foss.airfreight.airCargoVolume.label.transitSpaceTotal'), //'中转总仓位</br>（公斤）', 
		//关联model中的字段名
		dataIndex: 'transitSpaceTotal'
	}*/],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airSpaceDetailStore');			
		backupquery.airCargoVolume.store=me.store;
	
		me.callParent([cfg]);
	}	
});

backupquery.airCargoVolume.dept = '';

Ext.onReady(function() {
	//1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	//	用法：模块名.自定义变量
	//2.对象都是用Ext.define定义的方式声明
	
	//查询表单
	var queryForm = Ext.create('Foss.airfreight.queryForm');
	backupquery.queryForm=queryForm;
	
	//查询结果
	var airCargoVolumeGrid = Ext.create('Foss.airfreight.airCargoVolume.airCargoVolumeGrid');
	backupquery.airCargoVolumeGrid=airCargoVolumeGrid;
	
	var airSpaceDetailGrid=Ext.create('Foss.airfreight.airCargoVolume.airSpaceDetailGrid');
	backupquery.airSpaceDetailGrid=airSpaceDetailGrid;
	
	var taskExplain = Ext.create('Foss.airfreight.airCargoVolume.TaskExplainContainer');
	
	//显示偏线界面
	Ext.create('Ext.panel.Panel',{
		id:'T_backupquery-airCargoVolume_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [queryForm,airCargoVolumeGrid,taskExplain,airSpaceDetailGrid],
		renderTo: 'T_backupquery-airCargoVolume-body'
	});
});