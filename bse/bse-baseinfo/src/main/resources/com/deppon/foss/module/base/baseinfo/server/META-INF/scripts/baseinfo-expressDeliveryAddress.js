//登陆用户员工号
baseinfo.expressDeliveryAddress.currentEmpCode = FossUserContext.getCurrentUserEmp().empCode;
//登录员工名
baseinfo.expressDeliveryAddress.currentEmpName = FossUserContext.getCurrentUserEmp().empName;
//登录用户的部门编码
baseinfo.expressDeliveryAddress.currentDeptCode = FossUserContext.getCurrentDeptCode();
//登录用户的部门名称
baseinfo.expressDeliveryAddress.currentDeptName = FossUserContext.getCurrentDept().name;
/**
 * 地址库基础资料实体
 */
Ext.define('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity',{
	extend : 'Ext.data.Model',
	fields :[{name:'id',type:'string'},
	         {name:'departmentName',type:'string'},
	         {name:'departmentCode',type:"string"},
	         {name:'provinceName',type:'string'},
	         {name:'provinceCode',type:'string'},
	         {name:'cityName',type:'string'},
	         {name:'cityCode',type:'string'},
	         {name:'countyName',type:'string'},
	         {name:'countyCode',type:"string"},
	         {name:'townName',type:"string"},
	         {name:'townCode',type:"string"},
	         {name:'street',type:"string"},
	         {name:'pccAddress',type:"string"},
	         {name:'startHouseNumber',type:"string"},
	         {name:'endHouseNumber',type:"string"},
	         {name:'type',type:"string"},
	         {name:'sign',type:"string"},
	         {name:'startTime',type:'date',convert: dateConvert,defaultValue:null},
	         {name:'endTime',type:'date',convert: dateConvert,defaultValue:null},
	         {name:'createUser',type:"string"},
	         {name:'operatorName',type:"string"},
	         {name:'createDate',type:'date',convert: dateConvert,defaultValue:null},
	         {name:'modifyUser',type:"string"},
	         {name:'modifyDate',type:'date',convert: dateConvert,defaultValue:null},
	         {name:'active',type:"string"}]
});

/**
 * 地址库Store
 */
Ext.define('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity',
	pageSize:25,
	sorters: [{
	     property: 'modifyTime',
	     direction: 'DESC'
	}],
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExpressDeliveryAddressEntitys.action'),
		reader : {
			type : 'json',
			root : 'expressDeliveryAddressVo.expressDeliveryAddressEntityList',
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
 * 新增地址库基础资料窗口
 */
Ext.define('Foss.baseinfo.addOrUpdateExpressDeliveryAddressWin',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	layout : 'auto',
	resizable:false,
	title:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.addOrUpdateWinTitle'),
	closeAction : 'hide',
	width :800,
	formRecord:null,
	isUpdate:false,
	listeners:{
		show:function(opt){
			var form = opt.addOrUpdateForm.getForm();
			var columnWidth = 0.99;
			//如果是修改就设置省市区乡镇的公共选择器columnWidth值为0.22
			if(opt.isUpdate){
				columnWidth = 0.22;
			}
			var configItem = [{
				columnWidth: columnWidth,
				provinceLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.pccName'),
				provinceIsBlank:false,
				provinceLabelWidth:70,
				provinceWidth:217,
				provinceName:'provinceCode',//省名称
				cityName:'cityCode',//市名称
				cityWidth:132,
				cityIsBlank :false,
				countyName : 'countyCode',// 县名称
				countyWidth:132,
				countyIsBlank :false,
				streetName:'townCode',// 镇名称
				streetLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.townName'),
				streetIsBlank :true,
				streetLabelWidth:70,
				streetWidth:217,
				type : 'P-C-C-S',
				xtype : 'linkexpressdeliveryregincombselector'
			}];
			/**
			 *因为每次新增修改的打开的window和form都是同一个，新增的时候省市区乡镇的公共选择器 columnWidth为0.99
			 *修改的时候省市区乡镇的公共选择器 columnWidth为0.22，公共选择器创建之后无法动态修改它的columnWidth，
			 *故每次打开新增修改窗体的时候会先把之前的公共选择器删掉，再动态新增一个
			 *注意，第一次点新增修改的时候因为没有加入公共选择器，所以不能贸然删除opt.addOrUpdateForm.items.getAt(6)
			 */
			if(opt.addOrUpdateForm.items.length==14){//如果items长度为14,那么说明之前打开过新增或修改界面，此前添加的公共选择器需要删掉
				opt.addOrUpdateForm.remove(opt.addOrUpdateForm.items.getAt(6));
			}
			//新增公共选择器
			opt.addOrUpdateForm.insert(6,configItem[0]);
			if(opt.isUpdate){		
				//修改，表单回显初始数据
				form.loadRecord(opt.formRecord);
				//时间回显的格式默认是Thu May 28 2015 08:55:07 GMT+0800 (中国标准时间)，需要转换为2015-5-28 8:55:07
				var startTime = opt.formRecord.get('startTime').getTime();
				form.findField('startTime').setValue(Ext.Date.format(new Date(startTime),'Y-m-d H:i:s'));
				var endTime = opt.formRecord.get('endTime').getTime();
				form.findField('endTime').setValue(Ext.Date.format(new Date(endTime),'Y-m-d H:i:s'));
				//修改的时候只能修改乡镇/街道编码，故需要把公共选择器里面的省市区编码设置为不可见
				form.findField('provinceCode').setVisible(false);
				form.findField('cityCode').setVisible(false);
				form.findField('countyCode').setVisible(false);
				//设置省市区名称为只读的
				form.findField('provinceName').setReadOnly(true);
				form.findField('cityName').setReadOnly(true);
				form.findField('countyName').setReadOnly(true);
				//设置省市区名称为可见的
				form.findField('provinceName').setVisible(true);
				form.findField('cityName').setVisible(true);
				form.findField('countyName').setVisible(true);
				//设置公共选择器当中的乡镇编码为乡镇名称
				form.findField('townCode').setValue(opt.formRecord.get('townName'));
			}else{
				//设置省市区名称为不可见的
				form.findField('provinceName').setVisible(false);
				form.findField('cityName').setVisible(false);
				form.findField('countyName').setVisible(false);
				//设置营业部名称
				form.findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
			}
		},
		hide:function(opt){
			opt.addOrUpdateForm.getForm().reset();
			opt.addOrUpdateForm.getForm().findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
		}
	},
	addOrUpdateForm:null,
	getAddOrUpdateForm:function(){
		if(this.addOrUpdateForm!=null){
			this.addOrUpdateForm.remove('Foss.baseinfo.expressDeliveryAddress_startTime1_ID');
			this.addOrUpdateForm.remove('Foss.baseinfo.expressDeliveryAddress_endTime1_ID');
		}
		this.addOrUpdateForm = Ext.create('Foss.baseinfo.addOrUpdateExpressDeliveryAddressForm');
		return this.addOrUpdateForm;
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getAddOrUpdateForm()];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.cancel'), //取消
					handler : function() {
						me.addOrUpdateForm.getForm().reset();
						me.addOrUpdateForm.getForm().findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
						me.hide();
					}
				},{
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.reset'),  //重置
					width: 60,
					handler : function() {
						baseinfo.expressDeliveryAddress.reset(me);
					}
				},{
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						baseinfo.expressDeliveryAddress.addOrUpdateExpressDeliveryAddress(me);
					}
				}];
	}
});

/**
 * 批量修改
 */
Ext.define('Foss.baseinfo.updateBatchExpressDeliveryAddressWin',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	layout : 'auto',
	title:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.updateBatch'),
	resizable:false,
	closeAction : 'hide',
	width :600,
	listeners:{		
		hide:function(opt){
		
		}
	},
	updateBatchForm:null,
	getUpdateBatchForm:function(){
		if(this.updateBatchForm!=null){
			this.updateBatchForm.remove('Foss.baseinfo.expressDeliveryAddress_startTime2_ID');
			this.updateBatchForm.remove('Foss.baseinfo.expressDeliveryAddress_endTime2_ID');
		}
		this.updateBatchForm = Ext.create('Foss.baseinfo.updateBatchExpressDeliveryAddressForm');
		return this.updateBatchForm;
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getUpdateBatchForm()];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.cancel'), //取消
					handler : function() {
						me.updateBatchForm.getForm().reset();
						me.hide();
					}
				},{
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.reset'),  //重置
					width: 60,
					handler : function() {
						me.updateBatchForm.getForm().reset();
						me.updateBatchForm.getForm().findField('startTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
						me.updateBatchForm.getForm().findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
					}
				},{
					text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						baseinfo.expressDeliveryAddress.updateBatchExpressDeliveryAddress(me);
					}
				}];
	}
});


Ext.define('Foss.baseinfo.Type',{
	extend:'Ext.data.Store',
	fields:['text','value'],
	data:[{'text':baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.townAll'),
		   'value':'TOWN_ALL'},//镇全境
		  {'text':baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.roadAll'),
		   'value':'ROAD_ALL'},//路全境
		  {'text':baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberAll'),
		   'value':'NUMBER_ALL'},//号全境
		  {'text':baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberOdd'),
		   'value':'NUMBER_ODD'},//单号
	      {'text':baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberEven'),
		   'value':'NUMBER_EVEN'}]//双号
});
/**
 * 重置操作
 */
baseinfo.expressDeliveryAddress.reset=function(me){
	var form = me.addOrUpdateForm.getForm();
	if(!me.isUpdate){	
		//重置表单
		form.reset();
		//更新生效时间为当前时间
		form.findField('startTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		//设置部门为当前登录人员的部门
		form.findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
	}else{
		//表单加在数据
		form.loadRecord(me.formRecord);
		var startTime = me.formRecord.get('startTime').getTime();
		//转换生效时间
		form.findField('startTime').setValue(Ext.Date.format(new Date(startTime),'Y-m-d H:i:s'));
		//转换失效时间
		var endTime = me.formRecord.get('endTime').getTime();
		form.findField('endTime').setValue(Ext.Date.format(new Date(endTime),'Y-m-d H:i:s'));
		//设置省市区编码不可见
		form.findField('provinceCode').setVisible(false);
		form.findField('cityCode').setVisible(false);
		form.findField('countyCode').setVisible(false);
		//设置乡镇编码显示的值为乡镇名称
		form.findField('townCode').setValue(me.formRecord.get('townName'));
		//设置省市区名称为只读
		form.findField('provinceName').setReadOnly(true);
		form.findField('cityName').setReadOnly(true);
		form.findField('countyName').setReadOnly(true);
	}
}
/**
 * 增加，修改地址库
 */
baseinfo.expressDeliveryAddress.addOrUpdateExpressDeliveryAddress=function(me){
	//获取新增修改的表单
	var form = me.addOrUpdateForm.getForm();
	//表单校验是否通过
	if (form.isValid()) {
		var type = form.findField('type').getValue();
		form.findField('type').setValue(null);
		var expressDeliveryAddress = Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity');
		var provinceName = form.findField('provinceCode').rawValue;
		var cityName = form.findField('cityCode').rawValue;
		var countyName = form.findField('countyCode').rawValue;
		var townName = form.findField('townCode').rawValue;
		var townCode = form.findField('townCode').getValue();
		//去掉路/街的前后导空白
		var street = form.findField('street').getValue().replace(/^\s*/, "").replace(/\s*$/, "");
		//乡镇、路街必填其一
		if(street==''&&townName==''){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.tsValidMsg'));
			return;
		}
		if(street.length>50){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.streetWarnMsg'));
			return;
		}
		//新增URL
		var ajaxUrl = '../baseinfo/addExpressDeliveryAddressEntity.action';
		var ids = [];
		if(!me.isUpdate){
			//设置创建人
			expressDeliveryAddress.set('createUser',baseinfo.expressDeliveryAddress.currentEmpCode);
		}else{
			provinceName = form.findField('provinceName').getValue();
			cityName = form.findField('cityName').getValue();
			countyName = form.findField('countyName').getValue();
			/*
			 *因为点击修改的时候townCode的值是中文乡镇名称，故需要在保存的时候判断townCode与原数据的townCode是否相同，
			 *如果不同，就说明乡镇没有被修改过，直接取原数据的townCode就行
			 */
			if(townCode==townName){
				townCode = me.formRecord.get('townCode');
			}
			//修改URL
			ajaxUrl = '../baseinfo/updateExpressDeliveryAddressEntity.action';
			//保存修改数据的id
			ids.push(form.findField('id').getValue());
			expressDeliveryAddress.set('id',form.findField('id').getValue());
			//设置修改人
			expressDeliveryAddress.set('modifyUser',baseinfo.expressDeliveryAddress.currentEmpCode);
		}
		//省市区地址进行拼接
		var pccAddress = provinceName+cityName+countyName;
		expressDeliveryAddress.set('departmentName',baseinfo.expressDeliveryAddress.currentDeptName);
		expressDeliveryAddress.set('departmentCode',baseinfo.expressDeliveryAddress.currentDeptCode);
		expressDeliveryAddress.set('provinceCode',form.findField('provinceCode').getValue());
		expressDeliveryAddress.set('provinceName',provinceName);
		expressDeliveryAddress.set('cityCode',form.findField('cityCode').getValue());
		expressDeliveryAddress.set('cityName',cityName);
		expressDeliveryAddress.set('pccAddress',pccAddress);
		expressDeliveryAddress.set('countyCode',form.findField('countyCode').getValue());
		expressDeliveryAddress.set('countyName',countyName);
		expressDeliveryAddress.set('townCode',townCode);
		expressDeliveryAddress.set('townName',townName);
		expressDeliveryAddress.set('street',street);
		//设置操作人
		expressDeliveryAddress.set('operatorName',baseinfo.expressDeliveryAddress.currentEmpName);
		var startHouseNumber = form.findField('startHouseNumber').getValue().replace(/^\s*/, "").replace(/\s*$/, "");
		var endHouseNumber = form.findField('endHouseNumber').getValue().replace(/^\s*/, "").replace(/\s*$/, "");
		if(startHouseNumber==''&&endHouseNumber!=''){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.shnValidMsg'));
			return;
		}else if(startHouseNumber!=''&&endHouseNumber==''){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.ehnValidMsg'));
			return;
		}
		expressDeliveryAddress.set('type',type);
		if(townName!=''&&street==''&&type!='TOWN_ALL'){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.typeValidMsg'));
			return;
		}
		if(type!='TOWN_ALL'&&type!='ROAD_ALL'){
			if(type=='NUMBER_ALL'&&startHouseNumber==''){
				form.findField('type').setValue(type);
				baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.shnValidMsg'));
				return;
			}
			if(startHouseNumber!=''){
				if(endHouseNumber*1<startHouseNumber*1){
					form.findField('type').setValue(type);
					baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.ehnNumberMsg'));
					return;
				}else if(endHouseNumber*1==startHouseNumber*1){
					if(type!='NUMBER_ALL'){
						form.findField('type').setValue(type);
						baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.typeNumberAllValidMsg'));
						return;
					}
				}
			}
		}
		expressDeliveryAddress.set('startHouseNumber',startHouseNumber);
		expressDeliveryAddress.set('endHouseNumber',endHouseNumber);
		var startTime = form.findField('startTime').getValue();
		var endTime = form.findField('endTime').getValue();
		if(endTime<=startTime){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.timeValidMsg'));
			return;
		}
		expressDeliveryAddress.set('startTime',Ext.Date.parse(startTime,'Y-m-d H:i:s'));
		expressDeliveryAddress.set('endTime',Ext.Date.parse(endTime,'Y-m-d H:i:s'));
		var sign = form.findField('sign').getValue();
		if(sign==baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg')){
			sign='';
		}
		sign = sign.replace(/^\s*/, "").replace(/\s*$/, "");//去掉地标前后导空白
		if(sign.length>150){
			form.findField('type').setValue(type);
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signWarnMsg'));
			return;
		}
		expressDeliveryAddress.set('sign',sign);
		Ext.Ajax.request({
			url:ajaxUrl,
			jsonData:{'expressDeliveryAddressVo':{'expressDeliveryAddressEntity':expressDeliveryAddress.data,'ids':ids}},
			timeout:600000,
			success:function(response){
				if(me.isUpdate){
					me.close();
				}
				var grid = Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid();
				var queryForm = Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryForm();
				baseinfo.expressDeliveryAddress.expressDeliveryAddressQuery(queryForm);	
				if(me.title==baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.update')){
					me.hide();
					return;
				}
				me.addOrUpdateForm.getForm().findField('startTime').setValue(
						Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				form.findField('startHouseNumber').setValue('');
				form.findField('endHouseNumber').setValue('');
				form.findField('type').setValue('');
				form.findField('street').setValue('');
				form.findField('sign').setValue(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg'));
			},
			exception:function(response){
				var jsonObject = Ext.decode(response.responseText);
				form.findField('type').setValue(type);
				if(!jsonObject.success){
			     Ext.MessageBox.show({
					title : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.errorMsg'),
					msg : jsonObject.message,
					width : 450,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
					});
					return false;
				} 
			}
		});
	}else{
		baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.formValidMsg'));
		return;
	}
}
/**
 * 批量修改操作
 */
baseinfo.expressDeliveryAddress.updateBatchExpressDeliveryAddress=function(me){
	var form = me.updateBatchForm.getForm();
	if(form.isValid()){
		var startTime = form.findField('startTime').getValue();
		form.findField('startTime').setValue(null);
		var endTime = form.findField('endTime').getValue();
		var selection=Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid().getSelectionModel().getSelection();
		var ids = [];
		//获取选择数据的ID
		for(var i=0;i<selection.length;i++){
			ids.push(selection[i].get('id'));
		}
		if(endTime<=startTime){
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.timeValidMsg'));
			return;
		}
		var expressDeliveryAddress = Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity');
		//部门名称
		expressDeliveryAddress.set('departmentName',baseinfo.expressDeliveryAddress.currentDeptName);
		//部门编码
		expressDeliveryAddress.set('departmentCode',baseinfo.expressDeliveryAddress.currentDeptCode);
		//生效时间
		expressDeliveryAddress.set('startTime',Ext.Date.parse(startTime,'Y-m-d H:i:s'));
		//失效时间
		expressDeliveryAddress.set('endTime',Ext.Date.parse(endTime,'Y-m-d H:i:s'));
		//设置修改人
		expressDeliveryAddress.set('modifyUser',baseinfo.expressDeliveryAddress.currentEmpCode);
		//操作人
		expressDeliveryAddress.set('operatorName',baseinfo.expressDeliveryAddress.currentEmpName);
		Ext.Ajax.request({
			url:baseinfo.realPath("updateBatchExpressDeliveryAddressEntity.action"),
			jsonData:{'expressDeliveryAddressVo':{'ids':ids,expressDeliveryAddressEntity:expressDeliveryAddress.data}},
			timeout:600000,
			success:function(response){
				me.close();
				var queryForm = Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryForm();
    			baseinfo.expressDeliveryAddress.expressDeliveryAddressQuery(queryForm);
			},
			exception:function(response){
				var jsonObject = Ext.decode(response.responseText);
				form.findField('startTime').setValue(startTime);
				if(!jsonObject.success){
			     Ext.MessageBox.show({
					title : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.errorMsg'),
					msg : jsonObject.message,
					width : 450,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
					});
					return false;
				} 
			}
		});
	}else{
		baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.formValidMsg'));
		return;
	}
};

//省市区Store
Ext.define('Foss.baseinfo.RegionStore',{
	extend:'Ext.data.Store',
	fields:['availableName','code'],
    proxy:{
         type: 'ajax',
         url: baseinfo.realPath('queryRegions.action?expressDeliveryAddressVo.administrativeRegionsEntity.degree=DISTRICT_PROVINCE'),
         reader: {
             type: 'json',
             root: 'expressDeliveryAddressVo.regionList'
         }
    }
});

var provinceStore = Ext.create('Foss.baseinfo.RegionStore');
var cityStore = Ext.create('Foss.baseinfo.RegionStore');
var countyStore = Ext.create('Foss.baseinfo.RegionStore');

//作废地址库
baseinfo.expressDeliveryAddress.deleteAcceesPoint=function(){
	var selection=Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid().getSelectionModel().getSelection();
	if(selection.length==0){
		baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.deleteWarmMsg'));
		return;
	}
	var ids = [];
	for(var i=0;i<selection.length;i++){
		//只能删除当前登录用户部门的数据
		if(selection[i].get('departmentCode')!=baseinfo.expressDeliveryAddress.currentDeptCode){
			baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.deleteValidMsg'));
			return;
		}
		ids.push(selection[i].get('id'));
	}
	var expressDeliveryAddress = Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity');
	//设置修改人
	expressDeliveryAddress.set('modifyUser',baseinfo.expressDeliveryAddress.currentEmpCode);
	//操作人
	expressDeliveryAddress.set('operatorName',baseinfo.expressDeliveryAddress.currentEmpName);
	Ext.MessageBox.confirm(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.fossMsg'),
			baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.deleteConfirmMsg'),function(id){
		if(id=='yes'){
			Ext.Ajax.request({
				url:baseinfo.realPath("deleteExpressDeliveryAddressEntityByCodes.action"),
				jsonData:{'expressDeliveryAddressVo':{'ids':ids,expressDeliveryAddressEntity:expressDeliveryAddress.data}},
	    		timeout:60000,
	    		success:function(response){
	    			var queryForm = Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryForm();
	    			baseinfo.expressDeliveryAddress.expressDeliveryAddressQuery(queryForm);
	    		},
	    		exception:function(response){
	    			var jsonObject = Ext.decode(response.responseText);
					if(!jsonObject.success){
				     Ext.MessageBox.show({
						title : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.errorMsg'),
						msg : jsonObject.message,
						width : 450,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
						});
						return false;
					} 
				}
			});
		}
	});
}

/**
 * 地址库基础资料表单
 */

Ext.define('Foss.baseinfo.addOrUpdateExpressDeliveryAddressForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.addOrUpdateTitle'),
	frame:true,
	defaultType : 'textfield',
	defaults:{
		margin : '5 5 5 0',
		allowBlank:false,
		columnWidth: 0.99,
		labelWidth:75
	},
	layout: 'column',
    formRecord:null,
    items:[{
    	columnWidth: 0.44,
		name:'id',
		labelWidth: 70,
		fieldLabel:'ID',
		allowBlank:true,
		readOnly:true,
		hidden:true
	},{
    	columnWidth: 0.44,
		name:'departmentName',
		labelWidth: 70,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.department'),//部门
		readOnly:true
	},{
		columnWidth: 0.55,
		allowBlank:true,
		readOnly:true
	},,{
    	columnWidth: 0.28,
		name:'provinceName',
		labelWidth: 70,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.pccName'),//编码
		allowBlank:true,
		width:170
	},{
    	columnWidth: 0.19,
		name:'cityName',
		labelWidth: 70,
		allowBlank:true,
		width:50
	},{
    	columnWidth: 0.19,
		name:'countyName',
		labelWidth: 70,
		allowBlank:true,
		width:60
	},{
		name:'street',
		labelWidth: 70,
		columnWidth: 0.33,
		width:212,
		regex:/^[\u4e00-\u9fa5]{1,}$/,
		allowBlank:true,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.street'),//路/街
		xtype :'textfield'
	},{
		name:'startHouseNumber',
		labelWidth: 70,
		width:212,
		columnWidth: 0.33,
		allowBlank:true,
		regex:/^\d{0,8}$/,
		listeners:{
			blur:function(option){
				var startHouseNumberVal = option.getValue();
				if(startHouseNumberVal!=''){
					if(startHouseNumberVal*1<0){
						option.markInvalid(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.shnNumberMsg'));
					}
					var streetVal = option.previousSibling().getValue().replace(/^\s*/, "");
					if(streetVal==''){
						option.setValue('');
						baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.blurNumberMsg'));
					}
				}
			}
		},
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.startHouseNumber'),//修改人
		xtype :'textfield'
	},{
		name:'endHouseNumber',
		labelWidth: 70,
		width:212,
		columnWidth: 0.33,
		allowBlank:true,
		regex:/^\d{0,8}$/,
		listeners:{
			blur:function(option){
				var streetVal = option.previousSibling().previousSibling().getValue().replace(/^\s*/, "");
				if(streetVal==''){
					option.setValue('');
					baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.blurNumberMsg'));
				}
				var startHouseNumberVal = option.previousSibling().getValue();
				if(startHouseNumberVal!=''){
					//比较起始门牌号和终止门牌号
					if(option.getValue()*1<startHouseNumberVal*1){
						option.markInvalid(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.ehnNumberMsg'));
					}
				}
			}
		},
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.endHouseNumber'),//修改人
		xtype :'textfield'
	},{
		name:'type',
		labelWidth: 70,
		width:212,
		displayField:'text',
		valueField:'value',
		editable:'false',
		columnWidth: 0.33,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.type'),//类型
		xtype :'combobox',
		store : Ext.create('Foss.baseinfo.Type'),
		listeners:{
			change:function(option){
				var endHouseOption = option.previousSibling();
				var startHouseOption = option.previousSibling().previousSibling();
				var streetOption = option.previousSibling().previousSibling().previousSibling();
				if(option.getValue()==null){//如果没有选择类型，那么起始门牌号，终止门牌号，路/街都可以输入
					endHouseOption.setReadOnly(false);
					startHouseOption.setReadOnly(false);
					streetOption.setReadOnly(false);
				}else if(option.getValue()=='TOWN_ALL'){//如果是镇全境，那么起始门牌号，终止门牌号，路/街都只读，且值都为空字符串
					endHouseOption.setValue('');
					endHouseOption.setReadOnly(true);
					startHouseOption.setValue('');
					startHouseOption.setReadOnly(true);
					streetOption.setValue('');
					streetOption.setReadOnly(true);
				}else if(option.getValue()=='ROAD_ALL'){//如果是路全境，那么起始门牌号，终止门牌号只读，路/街必填
					endHouseOption.setValue('');
					endHouseOption.setReadOnly(true);
					startHouseOption.setValue('');
					startHouseOption.setReadOnly(true);
					streetOption.setReadOnly(false);
				}else{
					endHouseOption.setReadOnly(false);
					startHouseOption.setReadOnly(false);
					streetOption.setReadOnly(false);
				}
			}
		}
	},{
		name:'startTime',
		labelWidth: 70,
		width:212,
		columnWidth: 0.33,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.startTime'),//生效时间
		xtype :'datetimefield_date97',		
		id:'Foss.baseinfo.expressDeliveryAddress_startTime1_ID',
		time:true,
		format: 'Y-m-d H:i:s',
		editable:'false',
		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		dateConfig: {
			el: 'Foss.baseinfo.expressDeliveryAddress_startTime1_ID-inputEl',
			dateFmt: 'Y-m-d H:i:s',
			maxDate: Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s')
		},
		listeners:{
			focus:function(opt){
				var startTimeValue = opt.getValue();
				if(startTimeValue!=''){
					var startTimeDate = Ext.Date.parse(startTimeValue, "Y-m-d H:i:s");
					opt.addDateConfig('minDate',Ext.Date.format(startTimeDate,'Y-m-d H:i:s'));
				}else{
					opt.addDateConfig('minDate',Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			}
		}
	},{
		name:'endTime',
		labelWidth: 70,
		width:212,
		columnWidth: 0.33,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.endTime'),//失效时间
		xtype :'datetimefield_date97',		
		id:'Foss.baseinfo.expressDeliveryAddress_endTime1_ID',
		time:true,
		format: 'Y-m-d H:i:s',
		editable:'false',
		value:Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s'),
		dateConfig: {
			el: 'Foss.baseinfo.expressDeliveryAddress_endTime1_ID-inputEl',
			dateFmt: 'Y-m-d H:i:s',
			maxDate: Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s')
		},
		listeners:{
			focus:function(opt){
				var startTimeValue = opt.previousSibling().getValue();
				if(startTimeValue!=''){
					var startTimeDate = Ext.Date.parse(startTimeValue, "Y-m-d H:i:s");
					opt.addDateConfig('minDate',Ext.Date.format(startTimeDate,'Y-m-d H:i:s'));
				}else{
					opt.addDateConfig('minDate',Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			}
		}
	},{
		name:'sign',
		labelWidth: 70,
		width:212,
		colunmWidth:0.99,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.sign'),//地标
		xtype :'textarea',
		allowBlank:true,
		regex:/^[a-zA-Z0-9_\u4e00-\u9fa5]{0,10}(，[a-zA-Z0-9_\u4e00-\u9fa5]{1,10})*$/,
		regexText:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg'),
//		value:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg'),
		emptyText:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg'), 
		listeners:{
			focus: function(store, operation, eOpts){
				if(this.getValue()==baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg')){
					this.setValue(null);	   		
				}
	   		},
	   		blur:function(store, operation, eOpts){
	   			if(!this.getValue()){
	   			    this.reset();
//	   				this.setValue(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.signMsg'));
	   			}
	   		}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 批量修改form
 */
Ext.define('Foss.baseinfo.updateBatchExpressDeliveryAddressForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.batchUpdateTitle'),
	frame:true,
	defaultType : 'textfield',
	defaults:{
		margin : '5 5 5 0',
		allowBlank:false,
		columnWidth: 0.99,
		labelWidth:75
	},
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,
    items:[{
    	columnWidth: 0.44,
    	colspan:2,
		name:'departmentName',
		labelWidth: 70,
		xtype: 'textfield',
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.department'),//部门
		readOnly:true
	},{
		name:'startTime',
		labelWidth: 70,
		width:226,
		columnWidth: 0.33,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.startTime'),//生效时间
		xtype :'datetimefield_date97',		
		id:'Foss.baseinfo.expressDeliveryAddress_startTime2_ID',
		time:true,
		format: 'Y-m-d H:i:s',
		editable:'false',
		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		dateConfig: {
			el: 'Foss.baseinfo.expressDeliveryAddress_startTime2_ID-inputEl',
			dateFmt: 'Y-m-d H:i:s',
			minDate: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			maxDate: Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s')
		}
	},{
		name:'endTime',
		labelWidth: 70,
		width:226,
		columnWidth: 0.33,
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.endTime'),//失效时间
		xtype :'datetimefield_date97',		
		id:'Foss.baseinfo.expressDeliveryAddress_endTime2_ID',
		time:true,
		format: 'Y-m-d H:i:s',
		editable:'false',
		value:Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s'),
		dateConfig: {
			el: 'Foss.baseinfo.expressDeliveryAddress_endTime2_ID-inputEl',
			dateFmt: 'yyyy-MM-dd HH:mi:ss',
			maxDate: Ext.Date.format(new Date(2099, 0, 1, 0, 0, 0),'Y-m-d H:i:s')
		},
		listeners:{
			focus:function(opt){
				var startTimeValue = opt.previousSibling().getValue();
				if(startTimeValue!=''){
					var startTimeDate = Ext.Date.parse(startTimeValue, "Y-m-d H:i:s");
					opt.addDateConfig('minDate',Ext.Date.format(startTimeDate,'Y-m-d H:i:s'));
				}else{
					opt.addDateConfig('minDate',Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			}
		}
	}]
});

baseinfo.expressDeliveryAddress.expressDeliveryAddressQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var provinceCode = form.findField('provinceCode').getValue();
	var cityCode = form.findField('cityCode').getValue();
	var countyCode = form.findField('countyCode').getValue();
	var townCode = form.findField('townCode').getValue();
	var street = form.findField('street').getValue();
	var departmentCode = form.findField('departmentCode').getValue();
	// 设置参数
	params = {};
	Ext.apply(params, {
		'expressDeliveryAddressVo.condition.provinceCode' : provinceCode,
		'expressDeliveryAddressVo.condition.cityCode' : cityCode,
		'expressDeliveryAddressVo.condition.countyCode' : countyCode,
		'expressDeliveryAddressVo.condition.townCode' : townCode,
		'expressDeliveryAddressVo.condition.street' : street,
		'expressDeliveryAddressVo.condition.departmentCode' : departmentCode
	});
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid();
	grid.store.setSubmitParams(params);
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.errorMsg'),
					msg : rawData.message,
					width : 450,
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

Ext.define("Foss.baseinfo.QueryExpressDeliveryAddressForm",{
	extend : 'Ext.form.Panel',
	title:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.queryCondition'),
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
        columns: 2
    },
    record:null,
    items:[{
		name:'code',
		columnWidth: 0.275,
		colspan:2,
		provinceLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.provinceName'),
		provinceIsBlank:true,
		provinceLabelWidth:80,
		provinceName:'provinceCode',//省名称
		cityLabel : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.cityName'),
		cityName : 'cityCode',//名称
		cityLabelWidth:80,
		cityIsBlank :true,
		countyLabel : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.countyName'),
		countyName : 'countyCode',// 县名称
		countyLabelWidth:80,
		countyIsBlank :true,
		streetLabel : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.townName'),
		streetName : 'townCode',// 县名称
		streetLabelWidth:80,
		streetIsBlank :true,
		type : 'P-C-C-S',
		xtype : 'linkexpressdeliveryregincombselector'
    },{
		xtype: 'textfield',
		name:'street',
		displayField:'text',
		margin:'5 0 0 0',
		valueField:'value',
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.queryStreet'),
		editable:false,
		maxLength:30,
		
	},{
		xtype: 'commonsaledepartmentselector',
		name:'departmentCode',
		displayField:'name',
		margin:'5 0 0 -561',
		valueField:'code',
		fieldLabel:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.queryDepartmentName'),
		maxLength:50
	},{
		xtype : 'container',
		colspan:2,
		defaultType:'button',
		layout:'column',
		items : [{
			width: 75,
			text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.reset'),
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
			text :  baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.query'),
			cls:'yellow_button',
			handler : function() {
				var form = this.up("form");
				baseinfo.expressDeliveryAddress.expressDeliveryAddressQuery(form);
			}
		}]
	}],
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items[3].store=Ext.create('Foss.baseinfo.Type');
		me.items[3].value='';
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity'):me.record);
	}
});

/**
 * 地址库列表grid
 */
Ext.define('Foss.baseinfo.QueryExpressDeliveryAddressGrid',{
	extend:'Ext.grid.Panel',
    title: baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.gridTitle'),
    id:'Foss_baseinfo_QueryExpressDeliveryAddressGrid_id',
    frame:true,
    enableColumnMove:false,
	height:800,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressStore'),
	columns:[{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.id'),
		dataIndex :'id',
		hidden:true
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.provinceName'),
		dataIndex :'provinceName'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.cityName'),
		dataIndex :'cityName'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.countyName'),
		dataIndex :'countyName'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.townName'),
		dataIndex :'townName'
	},{
		text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.street'),
		dataIndex : 'street'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.startHouseNumber'),
		dataIndex : 'startHouseNumber'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.endHouseNumber'),
		dataIndex : 'endHouseNumber'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.type'),
		dataIndex : 'type',
		renderer:function(value){
			if(value=='NUMBER_ODD'){
				return baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberOdd');
			}else if(value=='NUMBER_EVEN'){
				return baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberEven');
			}else if(value=="TOWN_ALL"){
				return baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.townAll');
			}else if(value=="ROAD_ALL"){
				return baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.roadAll');
			}else if(value=="NUMBER_ALL"){
				return baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.numberAll');
			}
		}
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.sign'),
		dataIndex : 'sign'
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.departmentName'),
		dataIndex : 'departmentName',
		width : 160
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.startTime'),
		dataIndex : 'startTime',
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		width : 140
	},{
		text:baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.endTime'),
		dataIndex : 'endTime',
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		width : 140
	},{
		text: baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.operator'),
		dataIndex: 'operatorName'
	}],
	viewConfig: {
		enableTextSelection: true
	},
	addOrUpdateExpressDeliveryAddressWin:null,
	getAddOrUpdateExpressDeliveryAddressWin:function(isUpdate,record){
		if(this.addOrUpdateExpressDeliveryAddressWin==null){
			if(Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_startTime1_ID')){
				Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_startTime1_ID').destroy();
				Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_endTime1_ID').destroy();
			}
			this.addOrUpdateExpressDeliveryAddressWin = Ext.create('Foss.baseinfo.addOrUpdateExpressDeliveryAddressWin');
		}
		this.addOrUpdateExpressDeliveryAddressWin.isUpdate=isUpdate;
		this.addOrUpdateExpressDeliveryAddressWin.formRecord=record;
		this.addOrUpdateExpressDeliveryAddressWin.addOrUpdateForm.getForm().findField('type').reset();
		//每次打开窗体都更新下生效时间为当前时间
		this.addOrUpdateExpressDeliveryAddressWin.addOrUpdateForm.getForm().findField('startTime').setValue(
				Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		return this.addOrUpdateExpressDeliveryAddressWin;
	},
	updateBatchExpressDeliveryAddressWin:null,
	getUpdateBatchExpressDeliveryAddressWin:function(){
		if(this.updateBatchExpressDeliveryAddressWin==null){
			if(Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_startTime2_ID')){
				Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_startTime2_ID').destroy();
				Ext.getCmp('Foss.baseinfo.expressDeliveryAddress_endTime2_ID').destroy();
			}
			this.updateBatchExpressDeliveryAddressWin = Ext.create('Foss.baseinfo.updateBatchExpressDeliveryAddressWin');
		}
		//每次打开窗体都更新下生效时间为当前时间
		this.updateBatchExpressDeliveryAddressWin.updateBatchForm.getForm().findField('startTime').setValue(
				Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		this.updateBatchExpressDeliveryAddressWin.updateBatchForm.getForm().
				findField('departmentName').setValue(baseinfo.expressDeliveryAddress.currentDeptName);
		return this.updateBatchExpressDeliveryAddressWin;
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
				text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.insert'),
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					var win = me.getAddOrUpdateExpressDeliveryAddressWin(false);
					win.show();
				}
			},{
				xtype :'button',
				text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.update'),
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					var selection=Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid().getSelectionModel().getSelection();
					if(selection.length!=1){
						baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.updateWarmMsg'));
						return;
					}
					if(selection[0].get('departmentCode')!=baseinfo.expressDeliveryAddress.currentDeptCode){
						baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.batchUpdateValidMsg'));
						return;
					}
					var win = me.getAddOrUpdateExpressDeliveryAddressWin(true,selection[0]);
					win.show();
				}
			},{	
				xtype :'button',
				text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.delete'),
				columnWidth :.05,
				handler : function(grid, rowIndex, colIndex) {
					baseinfo.expressDeliveryAddress.deleteAcceesPoint();
				}
			},{	
				xtype :'button',
				text : baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.updateBatch'),
				columnWidth :.08,
				handler : function(grid, rowIndex, colIndex) {
					var selection=Ext.getCmp('T_baseinfo-expressDeliveryAddress_content').getQueryGrid().getSelectionModel().getSelection();
					if(selection.length==0){
						baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.batchUpdateWarmMsg'));
						return;
					}
					for(var i=0;i<selection.length;i++){
						if(selection[i].get('departmentCode')!=baseinfo.expressDeliveryAddress.currentDeptCode){
							baseinfo.showInfoMes(baseinfo.expressDeliveryAddress.i18n('foss.baseinfo.expressDeliveryAddress.batchUpdateValidMsg'));
							return;
						}
					}
					var win = me.getUpdateBatchExpressDeliveryAddressWin();
					win.show();
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
	if (Ext.getCmp('T_baseinfo-expressDeliveryAddress_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QueryExpressDeliveryAddressForm',{'record':Ext.create('Foss.baseinfo.expressDeliveryAddress.ExpressDeliveryAddressEntity')});
	var queryGrid  = Ext.create('Foss.baseinfo.QueryExpressDeliveryAddressGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-expressDeliveryAddress').add(Ext.create('Ext.panel.Panel', {
		id:'T_baseinfo-expressDeliveryAddress_content',
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