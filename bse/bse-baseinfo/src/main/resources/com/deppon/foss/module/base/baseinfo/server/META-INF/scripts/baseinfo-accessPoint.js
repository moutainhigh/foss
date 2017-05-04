//登陆用户员工号
baseinfo.accessPoint.currentEmpCode = FossUserContext.getCurrentUserEmp().empCode;
//登录员工名
baseinfo.accessPoint.currentEmpName = FossUserContext.getCurrentUserEmp().empName;
/**
 * 接驳点基础资料实体
 */
Ext.define('Foss.baseinfo.accessPoint.AcceptPointEntity',{
	extend : 'Ext.data.Model',
	fields :[{name:'id',type:'string'},
	         {name:'name',type:"string"},
	         {name:'code',type:'string'},
	         {name:'bigRegionCode',type:'string'},
	         {name:'transferCode',type:'string'},
	         {name:'statu',type:'string'},
	         {name:'province',type:'string'},
	         {name:'city',type:"string"},
	         {name:'county',type:"string"},
	         {name:'street',type:"string"},
	         {name:'createUser',type:"string"},
	         {name:'createDate',
	             type:'date',convert: dateConvert,defaultValue:null
	         },
	         {name:'modifyUser',type:"string"},
	         {name:'modifyDate',
	             type:'date',convert: dateConvert,defaultValue:null
	         },
	         {name:'statu',type:"string"}]
});

/**
 * 接驳点基础资料查询实体
 */
Ext.define('Foss.baseinfo.accessPoint.Condition',{
	extend : 'Ext.data.Model',
	fields:[{name:'code',type:'string'},
	        {name:'bigRegionCode',type:"string"},
	        {name:'transferCode',type:'string'},
	        {name:'statu',type:'string'}]
});

/**
 * 接驳点Store
 */
Ext.define('Foss.baseinfo.accessPoint.AcceptPointStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.accessPoint.AcceptPointEntity',
	pageSize:25,
	sorters: [{
	     property: 'modifyTime',
	     direction: 'DESC'
	}],
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAccessPoints.action'),
		reader : {
			type : 'json',
			root : 'accessPointVo.accessPointEntityList',
			totalProperty : 'totalCount'
		}
	},
	submitParams:null,
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	}
});

/**
 * 新增接驳点基础资料窗口
 */
Ext.define('Foss.baseinfo.addOrUpdateAcceptPointWin',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	layout : 'auto',
	resizable:false,
	closeAction : 'hide',
	width :885,
	editForm:null,
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		if(config.title=='新增接驳点'){
			me.editForm = Ext.create('Foss.baseinfo.addOrUpdateAcceptPointForm');
			me.editForm.getForm().findField('city').setReadOnly(true);
			me.editForm.getForm().findField('county').setReadOnly(true);
		}else{
			var regionCodes = [];
			regionCodes.push(config.formRecord.get('province'));
			regionCodes.push(config.formRecord.get('city'));
			regionCodes.push(config.formRecord.get('county'));
			if(config.title=='修改接驳点'){
				me.editForm = Ext.create('Foss.baseinfo.addOrUpdateAcceptPointForm',{
					formRecord:config.formRecord
				});
				me.editForm.loadRecord(config.formRecord);
				var combobox = me.editForm.getForm().findField('province');
				var path = baseinfo.realPath('queryRegions.action?accessPointVo.administrativeRegionsEntity.degree=CITY');
            	path += '&accessPointVo.administrativeRegionsEntity.parentDistrictCode='+regionCodes[0];
            	baseinfo.accessPoint.updateRegions(combobox,path,cityStore);
            	path = baseinfo.realPath('queryRegions.action?accessPointVo.administrativeRegionsEntity.degree=DISTRICT_COUNTY');
            	path += '&accessPointVo.administrativeRegionsEntity.parentDistrictCode='+regionCodes[1];
            	baseinfo.accessPoint.updateRegions(me.editForm.getForm().findField('city'),path,countyStore);
            	Ext.Ajax.request({
					url:baseinfo.realPath('queryRegionsByCodes.action'),
					jsonData:{'accessPointVo':{'regionCodes':regionCodes}},
					timeout:600000,
					async: false,//此处采用同步的方式，因为要用到返回结果
					success:function(response){
						var result = Ext.decode(response.responseText);
						var regionList = result.accessPointVo.regionList;
						var regionDegree = ['province','city','county'];
						for(var i = 0;i<regionList.length;i++){
							for(var j  = 0;j<regionDegree.length;j++){
								if(regionList[i]['code']==me.editForm.getForm().findField(regionDegree[j]).getValue()){        		
            						me.editForm.getForm().findField(regionDegree[j]).setRawValue(regionList[i]['availableName']);
            					}
							}
						}
					},
					exception:function(response){
						baseinfo.showInfoMes('系统异常!请稍后重试');
					}
				});
			}else{
				me.editForm = Ext.create('Foss.baseinfo.detailAcceptPointForm',{
					formRecord:config.formRecord
				});
				Ext.Ajax.request({
					url:baseinfo.realPath('queryRegionsByCodes.action'),
					jsonData:{'accessPointVo':{'regionCodes':regionCodes}},
					timeout:600000,
					async: false,//此处采用同步的方式，因为要用到返回结果
					success:function(response){
						var result = Ext.decode(response.responseText);
						var regionList = result.accessPointVo.regionList;
						var regionDegree = ['province','city','county'];
						for(var i = 0;i<regionList.length;i++){
							for(var j  = 0;j<regionDegree.length;j++){
								config.formRecord.set(regionDegree[j],regionList[i]['code']==config.formRecord.get(regionDegree[j])
									?regionList[i]['name']:config.formRecord.get(regionDegree[j]));
							}
						}
					},
					exception:function(response){
						baseinfo.showInfoMes('系统异常!请稍后重试');
					}
				});
				me.editForm.loadRecord(config.formRecord);
			}
		}
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.accessPoint.i18n('foss.baseinfo.cancel'),
					handler : function() {
						me.hide();
					}
				},{
					text : baseinfo.accessPoint.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						baseinfo.accessPoint.addOrUpdateAccessPoint(me);
					}
				}];
	}
});

Ext.define('Foss.baseinfo.QueryActive',{
	extend:'Ext.data.Store',
	fields:['text','value'],
	data:[{'text':'全部',
		   'value':''},
		  {'text':'已启用',
		   'value':'Y'},
	      {'text':'未启用',
		   'value':'N'}]
});

/**
 * 接驳点基础资料明细
 */

Ext.define('Foss.baseinfo.detailAcceptPointForm',{
	extend:'Ext.form.Panel',
	title:'接驳点基础资料',
	frame:true,
	defaultType : 'textfield',
	defaults:{
		margin : '5 0 5 0',
		allowBlank:false,
		labelWidth:60
	},
	layout:{
		type : 'table',
		columns : 5
    },
    formRecord:null,
    items:[{
		name:'bigRegionCode',
		fieldLabel:'经营大区',//经营大区
		xtype :'bigregionselector',
		readOnly : true
	},{
		name:'transferCode',
		labelWidth:75,
		fieldLabel:'出发中转场',//出发中转场站
		xtype :'transfercenterselector',
		readOnly : true
	},{
		name:'createDate',
		fieldLabel:'创建时间',//创建时间
		xtype :'datefield',
		readOnly : true,
		format : 'Y-m-d H:i:s'
	},{
		name:'createUser',
		fieldLabel:'创建人',//创建人
		xtype :'textfield',
		labelWidth:48,
		readOnly:true
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.99,
		colspan:1
	},{
		name:'statu',
		readOnly : true,
		xtype : 'combobox',
		fieldLabel:'状态',
		columnWidth : 0.9,
		displayField : 'text',
		valueField : 'value',
		queryMode : 'local',
		triggerAction : 'all',
		store : Ext.create('Foss.baseinfo.QueryActive')
	},{
		name:'modifyDate',
		fieldLabel:'最后一次修改时间',//创建时间
		xtype :'datefield',
		labelWidth: 125,
		readOnly : true,
		columnWidth : 0.7,
		format : 'Y-m-d H:i:s'
	},{
		name:'modifyUser',
		fieldLabel:'修改人',//创建人
		xtype :'textfield',
		readOnly:true
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.99,
		colspan:2
	},{
		name:'province',
		fieldLabel:'省份',//省份
		xtype :'combobox',
		readOnly : true,
		store:provinceStore,
		displayField:'availableName',
		valueField:'code',
		width : 132
	},{
		name:'city',
		margin:'0 0 0 -53',
		fieldLabel:'城市',//城市
		xtype :'combobox',
		readOnly : true,
		displayField:'availableName',
		valueField:'code',
		width : 132
	},{
		name:'county',
		margin:'0 0 0 -166',
		fieldLabel:'区县',//区县
		xtype :'combobox',
		readOnly : true,
		displayField:'availableName',
		valueField:'code',
		width : 132
	},{
		xtype:'textfield',
		name:'street',
		labelWidth: 85,
		readOnly : true,
		fieldLabel:'道路/街道',//道路/街道
		margin:'0 0 0 -215'
	},{
		xtype:'container',
		html:'接驳点',
		margin:'0 0 0 -76',
		columnWidth:.99,
		colspan:1
	}]
});


/**
 * 增加，修改接驳点
 */
baseinfo.accessPoint.addOrUpdateAccessPoint=function(me){
	var form = me.editForm.getForm();
	if (form.isValid()) {
		var province = form.findField('province').getValue();
		form.findField('province').setValue(null);
		var accessPoint = Ext.create('Foss.baseinfo.accessPoint.AcceptPointEntity');
		var ajaxUrl = '';
		var accessPointName = form.findField('city').rawValue+form.findField('county').rawValue+
			form.findField('street').rawValue.replace( /^\s*/, "");
		accessPoint.set('name',accessPointName);
		var accessPointEntityList = null;
		/**
		 * 接驳点名称不能重复，故在添加和修改前续作判断
		 */
		Ext.Ajax.request({
			url:baseinfo.realPath('queryAccessPointsByName.action'),
			jsonData:{'accessPointVo':{'accessPointEntityCondition':accessPoint.data}},
			timeout:600000,
			async: false,//此处采用同步的方式，因为要用到返回结果
			success:function(response){
				var result = Ext.decode(response.responseText);
				accessPointEntityList = result.accessPointVo.accessPointEntityList;
			},
			exception:function(response){
				form.findField('province').setValue(province);
				baseinfo.showInfoMes('系统异常!请稍后重试');
			}
		});
		if(me.title=='新增接驳点'){
			if(accessPointEntityList!=null&&''!=accessPointEntityList){
				baseinfo.showInfoMes('您添加的【'+accessPointName.replace( /\s*$/, "")+'接驳点】已经存在，请重新命名!!!');
				form.findField('province').setValue(province);
				return;
			}
			accessPoint.set('bigRegionCode',form.findField('bigRegionCode').getValue());
			accessPoint.set('transferCode',form.findField('transferCode').getValue());
			accessPoint.set('province',province);
			accessPoint.set('city',form.findField('city').getValue());
			accessPoint.set('county',form.findField('county').getValue());
			accessPoint.set('street',form.findField('street').getValue());
			accessPoint.set('createUser',baseinfo.accessPoint.currentEmpCode);
			accessPoint.set('modifyUser',baseinfo.accessPoint.currentEmpCode);
			ajaxUrl = baseinfo.realPath('addAccessPoint.action');
		}else if(me.title=='修改接驳点'){
			var code = form.findField('code').getValue();
			if(accessPointEntityList!=null&&''!=accessPointEntityList){
				for(var i=0;i<accessPointEntityList.length;i++){
					if(accessPointEntityList[i].code!=code){
						baseinfo.showInfoMes('您添加的【'+accessPointName.replace( /\s*$/, "")+'接驳点】已经存在，请重新命名!!!');
						form.findField('province').setValue(province);
						return;
					}
				}
			}
			ajaxUrl = baseinfo.realPath('updateAccessPoint.action');
			accessPoint.set('street',form.findField('street').getValue());
			accessPoint.set('code',code);
			var regionDegree = ['province','city','county'];
			for(var i=0;i<regionDegree.length;i++){
				if(form.findField(regionDegree[i]).getValue()==form.findField(regionDegree[i]).lastValue){
					accessPoint.set(regionDegree[i],form.findField(regionDegree[i]).getValue());
				}
			}
		}
		Ext.Ajax.request({
			url:ajaxUrl,
			jsonData:{'accessPointVo':{'accessPointEntity':accessPoint.data}},
			timeout:600000,
			success:function(response){
				me.close();
				var grid = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryGrid();
				var queryForm = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryForm();
				baseinfo.accessPoint.accessPointQuery(queryForm);	
				if(me.title=='新增接驳点'){
					baseinfo.showInfoMes('添加成功!');
				}else if(me.title=='修改接驳点'){
					baseinfo.showInfoMes('修改成功!');
				}
			},
			exception:function(response){
				form.findField('province').setValue(province);
				baseinfo.showInfoMes('系统异常!请稍后重试');
			}
		});
	}else{
		baseinfo.showInfoMes('请根据提示填写完整！！！');
		return;
	}
}

/**
 * 禁用启用
 */
baseinfo.accessPoint.enableAndDisableAccessPoint = function(text){
	var queryGrid = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryGrid();
	var selection=queryGrid.getSelectionModel().getSelection();
	//假定点击的是'启用'按钮，保存这些错误消息
	var errorMsg = '请至少选择一行数据进行启用！';
	var enableAndDisableMsg = '您所选的数据都已启用，请重新选择！';
	var confirmMsg = '您确定要启用这些么?';
	var statu = 'N';
	//如果点击的是'禁用'按钮，则修改这些提示信息
	if(text=='禁用'){
		statu = 'Y';
		errorMsg = '请至少选择一行数据进行禁用！';
		enableAndDisableMsg = '您所选的数据都已禁用，请重新选择！';
		confirmMsg = '您确定要禁用这些么?';
	}
	if(selection.length==0){
		baseinfo.showInfoMes(errorMsg);
		return;
	}
	var flag = false;
	var accessPointCodes = [];
	for(var i=0;i<selection.length;i++){
		if(selection[i].get('statu')==statu){
			accessPointCodes.push(selection[i].get('code'));
		}
	}
	if(accessPointCodes.length==0){
		baseinfo.showInfoMes(enableAndDisableMsg);
		return;
	}
	if(text=='禁用'){
		var acceptPointSalesDeptEntitys = [];
		Ext.Ajax.request({
			url:baseinfo.realPath("queryAcceptPointSalesByAcceptPointCode.action"),
			jsonData:{'accessPointVo':{'accessPointCodes':accessPointCodes}},
    		timeout:600000,
    		async: false,
    		success:function(response){
    			var result = Ext.decode(response.responseText);
				acceptPointSalesDeptEntitys = result.accessPointVo.acceptPointSalesDeptEntitys;
    		},
    		exception:function(response){
    			baseinfo.showInfoMes('系统异常!请稍后重试');
    		}
		});
		if(acceptPointSalesDeptEntitys.length>0){
			var msg = '您禁用的【';
			for(var i=0;i<acceptPointSalesDeptEntitys.length-1;i++){
				for(var j=0;j<selection.length;j++){
					if(acceptPointSalesDeptEntitys[i]['acceptPointCode']==selection[j].get('code')){
						msg += selection[j].get('name')+'，';
					}
				}
			}
			for(var j=0;j<selection.length;j++){
				if(acceptPointSalesDeptEntitys[acceptPointSalesDeptEntitys.length-1]['acceptPointCode']==selection[j].get('code')){
					msg += selection[j].get('name')+']已存在接驳点营业部映射，请重新选择.....';
				}
			}
			baseinfo.showInfoMes(msg);
			return;
		}
	}
	Ext.MessageBox.confirm('FOSS提醒您',confirmMsg,function(id){
		if(id=='yes'){
			Ext.Ajax.request({
				url:baseinfo.realPath("updateAccessPointStatu.action"),
				jsonData:{'accessPointVo':{'accessPointCodes':accessPointCodes}},
	    		timeout:600000,
	    		success:function(response){
	    			var queryForm = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryForm();
	    			baseinfo.accessPoint.accessPointQuery(queryForm);
	    		},
	    		exception:function(response){
	    			baseinfo.showInfoMes('系统异常!请稍后重试');
	    		}
			});
		}
	});
}

//省市区Store
Ext.define('Foss.baseinfo.RegionStore',{
	extend:'Ext.data.Store',
	fields:['availableName','code'],
    proxy:{
         type: 'ajax',
         url: baseinfo.realPath('queryRegions.action?accessPointVo.administrativeRegionsEntity.degree=DISTRICT_PROVINCE'),
         reader: {
             type: 'json',
             root: 'accessPointVo.regionList'
         }
    }
});

var provinceStore = Ext.create('Foss.baseinfo.RegionStore');
var cityStore = Ext.create('Foss.baseinfo.RegionStore');
var countyStore = Ext.create('Foss.baseinfo.RegionStore');

//省市区级联
baseinfo.accessPoint.regions=function(combox,path,store){
	 var nextCombox = combox.nextSibling();
	 if(nextCombox.getValue()!=null&&nextCombox.getValue()!=''){
		nextCombox.setValue('');
		nextCombox.setRawValue('');
	 }
	 var cStore = nextCombox.store;
	 if(cStore!=null){
		 cStore.removeAll();
	 }
	 store.proxy.url=path;
	 store.load();
	 combox.nextSibling().setReadOnly(false);
	 combox.nextSibling().store=store;
	 combox.nextSibling().store.load();
	 combox.nextSibling().focus();
	 combox.nextSibling().expand();
}

baseinfo.accessPoint.updateRegions=function(combox,path,store){
	 var nextCombox = combox.nextSibling();
	 var cStore = nextCombox.store;
	 if(cStore!=null){
		 cStore.removeAll();
	 }
	 store.proxy.url=path;
	 store.load();
	 combox.nextSibling().store=store;
	 combox.nextSibling().store.load();
	 combox.nextSibling().focus();
	 combox.nextSibling().expand();
}

//作废接驳点
baseinfo.accessPoint.deleteAcceesPoint=function(grid,rowIndex){
	var deleteCode = grid.getStore().getAt(rowIndex).get('code');
	Ext.MessageBox.confirm('FOSS提醒您','你确定要作废该条信息么?',function(id){
		if(id=='yes'){
			Ext.Ajax.request({
				url:baseinfo.realPath("deleteAccessPoint.action"),
				jsonData:{'accessPointVo':{'accessPointEntity':{'code':deleteCode}}},
	    		timeout:600000,
	    		success:function(response){
	    			var queryForm = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryForm();
	    			baseinfo.accessPoint.accessPointQuery(queryForm);
	    		},
	    		exception:function(response){
	    			baseinfo.showInfoMes('系统异常!请稍后重试');
	    		}
			});
		}
	});
}

/**
 * 接驳点基础资料表单
 */

Ext.define('Foss.baseinfo.addOrUpdateAcceptPointForm',{
	extend:'Ext.form.Panel',
	title:'接驳点基础资料',
	frame:true,
	defaultType : 'textfield',
	defaults:{
		margin : '5 5 5 0',
		allowBlank:false,
		labelWidth:75
	},
	layout:{
		type : 'table',
		columns : 5
    },
    formRecord:null,
    items:[{
		name:'code',
		labelWidth: 70,
		fieldLabel:'编码',//编码
		allowBlank:true,
		hidden:true
	},{
		name:'bigRegionCode',
		labelWidth: 70,
		fieldLabel:'经营大区',//经营大区
		xtype :'bigregionselector'
	},{
		name:'transferCode',
		labelWidth: 75,
		fieldLabel:'出发中转场',//出发中转场站
		xtype :'transfercenterselector'
	},{
		name:'createUser',
		value:baseinfo.accessPoint.currentEmpName,
		margin:'0 0 0 20',
		labelWidth: 75,
		fieldLabel:'创建人',//创建人
		xtype :'textfield',
		readOnly:true
	},{
		name:'modifyUser',
		value:baseinfo.accessPoint.currentEmpName,
		margin:'0 0 0 20',
		labelWidth: 75,
		fieldLabel:'修改人',//修改人
		xtype :'textfield',
		readOnly:true
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.99,
		colspan:2
	},{
		name:'province',
		labelWidth: 40,
		fieldLabel:'省份',//省份
		xtype :'combobox',
		editable:false,
		store:provinceStore,
		displayField:'availableName',
		valueField:'code',
		width : 132,
		listeners:{      
             select:function(combox, record,index){
            	 var path = baseinfo.realPath('queryRegions.action?accessPointVo.administrativeRegionsEntity.degree=CITY');
            	 path += '&accessPointVo.administrativeRegionsEntity.parentDistrictCode='+this.getValue();
            	 baseinfo.accessPoint.regions(combox,path,cityStore);
            	 var countyCombox = combox.nextSibling().nextSibling();
            	 if(countyCombox.getValue()!=null&&countyCombox.getValue()!=''){
            		 countyCombox.setValue('');
            		 countyCombox.setRawValue('');
            	 }
             }
        }
	},{
		name:'city',
		labelWidth: 40,
		margin:'0 0 0 -78',
		fieldLabel:'城市',//城市
		xtype :'combobox',
		editable:false,
		width : 132,
		store:null,
		displayField:'availableName',
		valueField:'code',
		width : 132,
		listeners:{      
             select:function(combox, record,index){    
            	 var path = baseinfo.realPath('queryRegions.action?accessPointVo.administrativeRegionsEntity.degree=DISTRICT_COUNTY');
            	 path += '&accessPointVo.administrativeRegionsEntity.parentDistrictCode='+this.getValue();
            	 baseinfo.accessPoint.regions(combox,path,countyStore);
             }
        }
	},{
		name:'county',
		labelWidth: 40,
		margin:'0 0 0 -156',
		fieldLabel:'区县',//区县
		xtype :'combobox',
		editable:false,
		store:null,
		width : 132,
		displayField:'availableName',
		valueField:'code',
		width : 132
	},{
		xtype:'textfield',
		name:'street',
		maxLength:40,
		fieldLabel:'道路/街道',//道路/街道
		margin:'0 0 0 -230'
	},{
		xtype:'container',
		html:'接驳点',
		columnWidth:.99
	}]
});

baseinfo.accessPoint.accessPointQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var code = form.findField('code').getValue();
	var bigRegionCode = form.findField('bigRegionCode').getValue();
	var transferCode = form.findField('transferCode').getValue();
	var statu = form.findField('statu').getValue();
	// 设置参数
	params = {};
	Ext.apply(params, {
		'accessPointVo.accessPointEntityCondition.code' : code,
		'accessPointVo.accessPointEntityCondition.bigRegionCode' : bigRegionCode,
		'accessPointVo.accessPointEntityCondition.transferCode' : transferCode,
		'accessPointVo.accessPointEntityCondition.statu' : statu
	});
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-accessPoint_content').getQueryGrid();
	grid.store.setSubmitParams(params);
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			} 
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}

Ext.define("Foss.baseinfo.QueryAcceptPointForm",{
	extend : 'Ext.form.Panel',
	title:'查询条件',
	frame: true,
	collapsible: false,
    defaults : {
    	margin : '5 0 0 0',
    	labelSeparator:'',
    	labelWidth:80,
    	labelAlign:'left'
    },
    height :180,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,
    items:[{
		name:'code',
		columnWidth: 0.275,
		fieldLabel : '接驳点名称',  //接驳点名称
		xtype: 'accesspointselector'
    },{
		name:'bigRegionCode',
		columnWidth: 0.275,
		fieldLabel:'经营大区',//经营大区
		xtype :'bigregionselector'
	},{
		name:'transferCode',
		columnWidth: 0.275,
		fieldLabel:'出发中转场',//出发中转场站
		xtype :'transfercenterselector'
	},{
		xtype: 'combobox',
		name:'statu',
		displayField:'text',
		margin:'5 0 0 0',
		valueField:'value',
		fieldLabel:'状态',
		editable:false,
		maxLength:50
	},{
		xtype : 'container',
		colspan:4,
		defaultType:'button',
		layout:'column',
		items : [{
			width: 75,
			text : baseinfo.accessPoint.i18n('foss.baseinfo.reset'),
			handler : function() {
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.99
		},{
			xtype:'button',
			width: 75,       
			text :  baseinfo.accessPoint.i18n('foss.baseinfo.query'),
			cls:'yellow_button',
			handler : function() {
				var form = this.up("form");
				baseinfo.accessPoint.accessPointQuery(form);
			}
		}]
	}],
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items[3].store=Ext.create('Foss.baseinfo.QueryActive');
		me.items[3].value='';
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.accessPoint.AcceptPointEntity'):me.record);
	}
});

/**
 * 接驳点列表grid
 */
Ext.define('Foss.baseinfo.QueryAcceptPointGrid',{
	extend:'Ext.grid.Panel',
    title: '接驳点列表',
    id:'Foss_baseinfo_QueryAcceptPointGrid_id',
    frame:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.accessPoint.AcceptPointStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:100,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:'修改',
			handler:function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				var win = this.up().up().updateAcceptPointWin(record);
				win.show();
			},
			getClass : function (v, m, r, rowIndex) {
				if(r.get('statu')=='N'){
					return 'deppon_icons_edit';
				}
				return 'statementBill_hide';
			}
    	},{
    		iconCls : 'deppon_icons_showdetail',
			tooltip:'查看',
			handler:function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				var win = this.up().up().detailAccessPointWin(record);
				win.show();
			},
			getClass : function (v, m, r, rowIndex) {
				if(r.get('statu')=='Y'){
					return 'deppon_icons_showdetail';
				}
				return 'statementBill_hide';
			}
    	},{
    		iconCls: 'deppon_icons_cancel',
    		tooltip:'作废',
    		handler:function(grid, rowIndex, colIndex){
    			baseinfo.accessPoint.deleteAcceesPoint(grid, rowIndex);
			},
			getClass : function (v, m, r, rowIndex) {
				if(r.get('statu')=='N'){
					return 'deppon_icons_cancel';
				}
				return 'statementBill_hide';
			}
    	}]
	},{
		text:'编码',
		dataIndex :'code',
		hidden:true
	},{
		text:'省',
		dataIndex :'province',
		hidden:true
	},{
		text:'市',
		dataIndex :'city',
		hidden:true
	},{
		text:'区',
		dataIndex :'county',
		hidden:true
	},{
		text:'街道',
		dataIndex :'street',
		hidden:true
	},{
		text : '接驳点名称',
		dataIndex : 'name',
		flex : 1,
		renderer:function(value,meta,record,rowIndex,celIndex){
			return FossDataDictionary.rendererSubmitToDisplay(value,baseinfo.accessPoint.dictionary);
		},
	},{
		text:'经营大区',
		dataIndex : 'bigRegionCode',
		flex : 0.75
	},{
		text:'出发中转场',
		dataIndex : 'transferCode',
		flex : 0.75
	},{
		text:'创建时间',
		dataIndex : 'createDate',
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		flex : 0.75
	},{
		text:'创建人',
		dataIndex : 'createUser',
		flex : 0.75
	},{
		text:'最后一次修改时间',
		dataIndex : 'modifyDate',
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		flex : 0.75
	},{
		text:'修改人',
		dataIndex : 'modifyUser',
		flex : 0.75
	},{
		header: '状态',
		dataIndex: 'statu',
		renderer:function(value){
			if(value=='Y'){
				return '已启用';
			}else if(value=='N'){
				return '未启用';
			}else{
				return '';
			}
		}
	}],
	viewConfig: {
		enableTextSelection: true
	},getAcceptPointWin:function(title,record){
		var win = Ext.create('Foss.baseinfo.addOrUpdateAcceptPointWin',{
			'title':title,
			formRecord:record
		});
		var editForm = win.items.items[0].form;
		if(title=='新增接驳点'){
			editForm.findField('modifyUser').setVisible(false);
		}else if(title=='修改接驳点'){
			editForm.findField('createUser').setVisible(false);
			editForm.findField('bigRegionCode').setReadOnly(true);
			editForm.findField('transferCode').setReadOnly(true);
		}
		return win;
	},addAcceptPointWin:function(){
		return this.getAcceptPointWin('新增接驳点');
	},updateAcceptPointWin:function(record){
		return this.getAcceptPointWin('修改接驳点',record);
	},detailAccessPointWin:function(record){
		return this.getAcceptPointWin('接驳点信息',record);
	},
	constructor:function(config){
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text : '新增',
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					var win = me.addAcceptPointWin();
					win.show();
				}
			},{
				xtype :'button',
				text : '禁用',
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					baseinfo.accessPoint.enableAndDisableAccessPoint('禁用');
				}
			},{	
				xtype :'button',
				text : '启用',
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					baseinfo.accessPoint.enableAndDisableAccessPoint('启用');
				}
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-accessPoint_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QueryAcceptPointForm',{'record':Ext.create('Foss.baseinfo.accessPoint.AcceptPointEntity')});
	var queryGrid  = Ext.create('Foss.baseinfo.QueryAcceptPointGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-accessPoint').add(Ext.create('Ext.panel.Panel', {
		id:'T_baseinfo-accessPoint_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items : [queryForm,queryGrid]
	}));
});