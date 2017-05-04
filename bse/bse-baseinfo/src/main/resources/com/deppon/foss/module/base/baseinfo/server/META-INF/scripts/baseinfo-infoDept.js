/**
 * 信息部model									Foss.baseinfo.infoDept.InfoDeptEntityModel
 * 信息部store									Foss.baseinfo.infoDept.InfoDeptEntityStore
 * 信息部form									Foss.baseinfo.infoDept.QueryConditionForm
 * 信息部grid									Foss.baseinfo.infoDept.QueryResultGrid
 * 信息部winForm									Foss.baseinfo.infoDept.InfoDeptEntityWinForm
 * 信息部winGrid									Foss.baseinfo.infoDept.InfoDeptEntityWinGrid
 * 信息部win										Foss.baseinfo.infoDept.InfoDeptEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.infoDept.regLimit = {
	mobil:/^1\d{10}$/,tel:/^\d{3}[\d\*-\/]{4,37}$/,idCard:/^([\d]{15}|[\d]{18}|[\d]{17}X)$/
};
//附件分隔符
baseinfo.infoDept.sourceFileArray = [];
//附件分隔符
baseinfo.infoDept.split = '@';
//提交时校验 标准得分 数据是否正确
baseinfo.infoDept.verifyFileuploadgrid = function(fileuploadgrid){
	var i = 0,fileArray = [],store = fileuploadgrid.store;
	//最多上传四张图片
	/*if(store.getCount()!=4){
		baseinfo.showInfoMsg(baseinfo.infoDept.i18n('foss.baseinfo.infoDept.Mupload4picture'));
		return null;
	}*/
	for(;i<store.getCount();i++){
		fileArray.push(store.getAt(i));
	}
	return fileArray;
};
//初始化数据时  图片附件 转换成 数组
baseinfo.infoDept.initFileuploadgrid = function(record){
	var i = 0,file = [],fileArray = [],fileDisArray = ['idCardFrontPic','idCardBackPic','operateLicCopy','infoDeptPic'];
	for(var i = 0;i<fileDisArray.length;i++){
		if(!Ext.isEmpty(record.get(fileDisArray[i]))){
			file = record.get(fileDisArray[i]).split(baseinfo.infoDept.split);
			fileArray.push({'fileName':file[0],'relativePath':file[1],'fileType':file[2],'id':file[3],'status':file[4]});
		}
	}
	return fileArray;
};
//部门标准分 默认 数据 【标准名称，标准得分，备注，标准内容，信息部，是否启用】
baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData = [
     {name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard1')
    	 ,stdScore:35,infodeptId:'',active:'Y'
     },{name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard2')
    	 ,stdScore:15,infodeptId:'',active:'Y'
     },{name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard3')
    	 ,stdScore:10,infodeptId:'',active:'Y'
     },{name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard4')
    	 ,stdScore:20,infodeptId:'',active:'Y'
     },{name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard5')
    	 ,stdScore:15,infodeptId:'',active:'Y'
     },{name:'',infoDeptStdScore:0,notes:'',stdContent:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standard6')
    	 ,stdScore:5,infodeptId:'',active:'Y'
}];
//提交时校验 标准得分 数据是否正确
baseinfo.infoDept.verifyScoresStore = function(grid,verifyScoresStoreArray){
	for(var i = 0,store = grid.store;i< store.getCount();i++){
		var record = store.getAt(i);
		verifyScoresStoreArray[0] = record.get('infoDeptStdScore')<=record.get('stdScore');
		if(!verifyScoresStoreArray[0]){
			verifyScoresStoreArray[1] = record;
			return verifyScoresStoreArray;
		}
	}
	return verifyScoresStoreArray;
};
//把信息部标准得分 转换成 信息部前端 标准得分
baseinfo.infoDept.changeData4Dispay = function(grid,scoresStoreArray){
	for(var i = 0,store = grid.store;i< store.getCount();i++){
		var record = store.getAt(i);
		for(var j = 0;!Ext.isEmpty(scoresStoreArray) && j< scoresStoreArray.length;j++){
			if(scoresStoreArray[j].stdId === record.get('id')){
				record.set('infoDeptStdScore',scoresStoreArray[j].infoDeptStdScore);
				record.set('infodeptId',scoresStoreArray[j].infodeptId);
				record.set('scoreId',scoresStoreArray[j].id);
				record.set('notes',scoresStoreArray[j].notes);
				record.set('id',scoresStoreArray[j].stdId);
				record.set('active',scoresStoreArray[j].active);
				record.commit();
			}
		}
	}
};
//把信息部前端 标准得分  转换成  信息部标准得分  返回标准得分 POJO数组
baseinfo.infoDept.changeData4Commit = function(grid){
	var scoresStoreArray = [];
	for(var i = 0,store = grid.store;i< store.getCount();i++){
		var record = store.getAt(i),scoresPojo = {};
		scoresPojo.infoDeptStdScore = record.get('infoDeptStdScore');
		scoresPojo.infodeptId = record.get('infodeptId');
		scoresPojo.id = record.get('scoreId');
		scoresPojo.notes = record.get('notes');
		scoresPojo.stdId = record.get('id');
		scoresPojo.active = record.get('active');
		scoresStoreArray.push(scoresPojo);
	}
	return scoresStoreArray;
};
//初始化 信息部 标准得分 数据
baseinfo.infoDept.initScoresStore = function(grid,scoresStoreArray){
	for(var i = 0,store = grid.store;i< store.getCount();i++){
		var record = store.getAt(i);
		for(var j = 0;j<scoresStoreArray.length;j++){
			//TODO 后台暂时 缺失 实体
			if(scoresStoreArray[j].scoresId === record.get('id')){
				record.set('infoDeptStdScore',scoresStoreArray[j].infoDeptStdScore);
			}else{
				record.set('infoDeptStdScore',0);
			}
			record.commit();
		}
	}
	return scoresStoreArray;
};
//初始化界面数据 
baseinfo.infoDept.initWinData = function(win,viewState,formRecord,gridData,levelType){
	var stdScore = [],grid = win.down('grid'),objectVo ={};
	//信息部界面
	//TODO 标准得分 数据转换
	if(baseinfo.levelType.p === levelType){
		grid.store.loadData(baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData);
		if(baseinfo.viewState.add != viewState){
			objectVo.infoDeptScoresEntity = {infodeptId:formRecord.get('id')};
			//非异步请求 得到信息部 标准得分
			baseinfo.requestAjaxJson(baseinfo.realPath('queryInfoDeptScoresEntityByEntity.action'),{'objectVo':objectVo},function(result){
					stdScore = result.objectVo.infoDeptScoresEntityList;
				},function(result){
					baseinfo.showInfoMsg(result.message);
				},null,false);
			baseinfo.infoDept.changeData4Dispay(grid,stdScore);
		}
	}
	if(baseinfo.viewState.view === viewState){
		var butArray = win.down('fileuploadgrid').query('button');
		butArray[0].setDisabled(true);
		butArray[1].setDisabled(true);
	}
	win.down('fileuploadgrid').store.loadData(baseinfo.infoDept.initFileuploadgrid(formRecord));
	//信息部和部门广告 form 加载数据
	win.down('form').loadRecord(formRecord);
	return win;
};
//保存事件 
baseinfo.infoDept.submitEntity = function(win,viewState,operatEntity,levelType){
	var grid = Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addInfoDeptEntity.action')
		,m_success = baseinfo.infoDept.i18n('foss.baseinfo.saveSuccess'),m_failure = baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.saveFail'),m_dateError = baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.dataError')	
		,stdGrid = win.down('grid'),objectVo = {};
		objectVo.infoDeptEntity = operatEntity;
		objectVo.infoDeptScoresEntityList = baseinfo.infoDept.changeData4Commit(stdGrid);
	//如果是修改 则调用修改 action
	if(baseinfo.viewState.update === viewState){
		url = baseinfo.realPath('updateInfoDeptEntity.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
			//预操作grid加载数据
			grid.store.loadPage(1);
			win.hide();
			//提示保存成功
			baseinfo.showInfoMsg(m_success);
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//作废事件
baseinfo.infoDept.deleteEntityByCode = function(delType,operatRecord,grid,levelType){
//	var grid = Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryGrid()
	var url = baseinfo.realPath('deleteInfoDeptEntity.action')
		,objectVo = {},keyId = 'id';
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delType)&&baseinfo.delType===delType){
			var codeStr = [];
			//批量作废
			url = baseinfo.realPath('deleteInfoDeptEntity.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr.push(selection[j].get(keyId+''));
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = [operatRecord.get(keyId+'')];
		}
		Ext.MessageBox.buttonText.yes = baseinfo.infoDept.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.infoDept.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.infoDept.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.infoDept.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.infoDept.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
baseinfo.infoDept.entityIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('billSloganEntityExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//信息部Model
Ext.define('Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel', {
extend: 'Ext.data.Model',
fields : [//信息部名称
    {name:'name',type:'string'},
    //业务联系人
    {name:'contact',type:'string'},
    //联系电话
    {name:'contactPhone',type:'string'},
    //手机号码
    {name:'mobilePhone',type:'string'},
    //传真号码
    {name:'faxNo',type:'string'},
    //信息部性质
    {name:'property',type:'string'},
    //注册资本
    {name:'registFunds',type:'number'},
    //法人代表
    {name:'legalPerson',type:'string'},
    //营业部执照编号
    {name:'operateLicense',type:'string'},
    //联系地址
    {name:'contactAddress',type:'string'},
    //标准总得分
    {name:'totalScore',type:'number'},
    //采用意见
    {name:'opinion',type:'string'},
    //信息部老板身份证正面
    {name:'idCardFrontPic',type:'string'},
    //信息部老板身份证反面
    {name:'idCardBackPic',type:'string'},
    //营业执照复印件
    {name:'operateLicCopy',type:'string'},
    //信息部照片
    {name:'infoDeptPic',type:'string'},
    //是否启用
    {name:'active',type:'string'}]
});
//标准得分标准Model
Ext.define('Foss.baseinfo.infoDept.InfoDeptScoresModel', {
	extend: 'Ext.data.Model',
	fields : [//得分标准id
        {name:'id',type:'string'},
        //标准分值
        {name:'stdScore',type:'number'},
        //信息部
        {name:'infodeptId',type:'string'},
        
        //标准内容（用户手动填写 所得分值）
        {name:'stdContent',type:'string'},
        //标准得分（用户手动填写 所得分值）
        {name:'infoDeptStdScore',type:'number'},
        //备注
        {name:'notes',type:'string'},
        
        //标准得分id
        {name:'scoreId',type:'string'},
        //是否启用
        {name:'active',type:'string'}]
	});
//标准得分Model
Ext.define('Foss.baseinfo.infoDept.InfoDeptScoresEntityModel', {
	extend: 'Ext.data.Model',
	fields : [//标准ID
      {name:'stdId',type:'string'},
      //标准分值
      {name:'stdScore',type:'number'},
      //信息部ID
      {name:'infodeptId',type:'string'},
      //备注
      {name:'notes',type:'string'},
      //是否启用
      {name:'active',type:'string'}]
	});
//得分标准Model
Ext.define('Foss.baseinfo.infoDept.InfoDeptScoresStdEntityModel', {
	extend: 'Ext.data.Model',
	fields : [
	  //标准id
      {name:'id',type:'string'},
	  //标准内容
      {name:'stdContent',type:'string'},
      //标准得分
      {name:'stdScore',type:'number'},
      //是否启用
      {name:'active',type:'string'}]
});
//------------------------------------STORE----------------------------------
//信息部STORE
Ext.define('Foss.baseinfo.infoDept.InfoDeptEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryInfoDeptEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.infoDeptEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//信息部标准分 STORE
Ext.define('Foss.baseinfo.infoDept.InfoDeptScoresEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.infoDept.InfoDeptScoresModel',
	data:[]
});
//------------------------------------FORM----------------------------------
//信息部 查询条件
Ext.define('Foss.baseinfo.infoDept.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.infoDept.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,												//绑定的model Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		var property = me.getForm().findField('property');
//		property.store.add({'valueCode' : 'ALL','valueName' : '全部'});
//		property.setValue('ALL');
	},
	getItems:function(){
		var me = this;
		var store = FossDataDictionary.getDataDictionaryStore('INFO_DEPT_PROPERTIES',
				null, {
					'valueCode' : '',
					'valueName' : '全部'
				});
		return [{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptName'),							//信息部名称
			name:'name',
			maxLength:80
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.businessContact'),							//业务联系人
			name:'contact',
			maxLength:30
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.customer.phoneNo'),							//手机号码INFO_DEPT_PROPERTIES
			name:'mobilePhone',
			regex:baseinfo.infoDept.regLimit.mobil,
			maxLength:30
		},{
			xtype:'combo',
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptAttr'),								//信息部性质CONTRABAND_LEVEL
			name: 'property',
	    	labelWidth:100,
	    	store:store,
	        displayField: 'valueName',
	        valueField: 'valueCode',
	    	value:''
		},{
			xtype:'container',
			colspan:4,
			defaultType:'button',
			layout:'column',
			items:[{
				width: 75,
				text : baseinfo.infoDept.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexQueryButton'),
				hidden:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexQueryButton'),
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
				text : baseinfo.infoDept.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexQueryButton'),
				hidden:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexQueryButton'),
				cls:'yellow_button',
				handler : function() {
					if(this.up('form').getForm().isValid()){
						var grid  = Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryGrid();//得到grid
						grid.store.loadPage(1);//用分页的moveFirst()方法
					}else{
						baseinfo.showInfoMsg(baseinfo.infoDept.i18n('foss.baseinfo.infoDept.McheckDataRight'));
					}
				}
			}]
		}];
	}
});
//信息部 界面form
Ext.define('Foss.baseinfo.infoDept.InfoDeptEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
	frame:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.infoDept.PickupAndDeliveryInfoDeptEntityStore
    editGrid:null,
    viewState:null,
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
			allowBlank:false,
//			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptName'),							//信息部编码
			name:'name',
			maxLength:80
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.businessContact'),							//信息部名称
			name:'contact',
			maxLength:30
		},FossDataDictionary.getDataDictionaryCombo('INFO_DEPT_PROPERTIES',{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptAttr'),								//信息部性质
			name: 'property',
			allowBlank:(baseinfo.viewState.view === config.viewState),
	    	labelWidth:100,
			readOnly:(baseinfo.viewState.view === config.viewState)
		}),{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.customer.phoneNo'),							//上机部门名称
			name:'mobilePhone',
			regex:baseinfo.infoDept.regLimit.mobil,
			maxLength:30
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.faxNo'),
			allowBlank:true,							//信息部编码
			name:'faxNo',
			maxLength:30
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.tel'),
			allowBlank:true,							//信息部名称
			name:'contactPhone',
			maxLength:30
		},{
			fieldLabel:'注册资本(万元)',
			xtype:'numberfield',
	        hideTrigger:true,
	        mouseWheelEnabled:false,
	        decimalPrecision:0,
	        minValue:0,
	        maxLength:4,
			allowBlank:true,							//上机部门名称
			name:'registFunds'
		},{
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.legalRepresentative'),
			allowBlank:true,							//上机部门名称
			name:'legalPerson',
			maxLength:100
		},{
			colspan:2,
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.licenseNo'),	
			name:'operateLicense',
			width:530,
			maxLength:30
		},{
			colspan:2,
			xtype:'textareafield',
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.linkAddress'),	
			name:'contactAddress',
			width:530,
			height:40,
			maxLength:200
		},config.editGrid,{
			colspan:2,
			width:530,
			name:'totalScore',
			xtype:'numberfield',
	        hideTrigger:true,
	        mouseWheelEnabled:false,
	        decimalPrecision:0,
	        readOnly:true,
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.total')
		},{
			colspan:2,
			width:530,
			xtype:'textareafield',
			name:'opinion',
			fieldLabel:baseinfo.infoDept.i18n('foss.baseinfo.infoDept.adoptSuggest'),
			height:40,
			allowBlank:true,
			maxLength:200
		}
		/****隐藏域****/
		,{
			hidden:true,
			allowBlank:true,
			name:'id'									//与部门标准积分关联
		}];
	}
});
//TODO 信息部 证件图片  界面form
Ext.define('Foss.baseinfo.infoDept.uploadFileForm', {
	extend : 'Ext.form.Panel',
	items : [ Ext.create('Ext.Img', {
		src : 'http://www.sencha.com/img/20110215-feat-html5.png'
	}), {
        xtype: 'filefield',
        name: 'photo',
        labelWidth: 50,
//        msgTarget: 'side',
        allowBlank: false,
        anchor: '100%',
        buttonText: baseinfo.infoDept.i18n('foss.baseinfo.infoDept.localPicture'),
        listeners:{
        	change:function(field,newV,oldV){
        		if(!Ext.isEmpty(newV)){
        			//TODO 调用form的submit方法上传附件
        		}
        	}
        }
    }]
});
//------------------------------------GRID----------------------------------
//信息部 查询结果grid
Ext.define('Foss.baseinfo.infoDept.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.infoDept.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		me.listeners = me.getMyListeners(config);
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text : baseinfo.infoDept.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexAddButton'),
			hidden:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				me.addInfoDeptEntity({}).show();
			} 
		},'-', {
			text : baseinfo.infoDept.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexCancelButton'),
			hidden:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexCancelButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				baseinfo.infoDept.deleteEntityByCode(baseinfo.delType,null,Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryGrid(),baseinfo.levelType.p);
			} 
		}];
	},
	getPagingToolbar : function(config) {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//得到信息部编辑窗体 得到信息部编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getInfoDeptEntityWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.infoDept.InfoDeptEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
		return baseinfo.infoDept.initWinData(win,viewState,formRecord,[],baseinfo.levelType.p);
	},
	addInfoDeptEntityWin:null,						//新增基信息部
	addInfoDeptEntity:function(param){
		return this.getInfoDeptEntityWin(this.addInfoDeptEntityWin,baseinfo.infoDept.i18n('foss.baseinfo.infoDept.addInfoDept'),baseinfo.viewState.add,param);
	},
	updateInfoDeptEntityWin:null,						//修改基信息部
	updateInfoDeptEntity:function(param){
		return this.getInfoDeptEntityWin(this.updateInfoDeptEntityWin,baseinfo.infoDept.i18n('foss.baseinfo.infoDept.alterInfoDept'),baseinfo.viewState.update,param);
	},
	viewInfoDeptEntityWin:null,						//查看基信息部
	viewInfoDeptEntity:function(param){
		return this.getInfoDeptEntityWin(this.viewInfoDeptEntityWin,baseinfo.infoDept.i18n('foss.baseinfo.infoDept.viewInfoDept'),baseinfo.viewState.view,param);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 信息部
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewInfoDeptEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.infoDept.InfoDeptEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//信息部名称
								'objectVo.infoDeptEntity.name':entity.name,
								//业务联系人
								'objectVo.infoDeptEntity.contact':entity.contact,
								//手机号码
								'objectVo.infoDeptEntity.mobilePhone':entity.mobilePhone,
								//信息部性质
								'objectVo.infoDeptEntity.property':entity.property
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			text : baseinfo.infoDept.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.infoDept.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexUpdateButton'),
				handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateInfoDeptEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.infoDept.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexCancelButton'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.infoDept.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,baseinfo.levelType.p);
                }
            }]
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptName'),									//信息部编码
			dataIndex : 'name',
			maxLength:80
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.businessContact'),									//信息部名称
			dataIndex : 'contact',
			maxLength:30
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.tel'),									//上机部门名称
			dataIndex : 'contactPhone',
			maxLength:30
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.customer.phoneNo'),											//队员
			dataIndex : 'mobilePhone',
			maxLength:30
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.faxNo'),									//信息部名称
			dataIndex : 'faxNo',
			maxLength:30
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.infoDeptAttr'),									//信息部性质
			dataIndex : 'property',
			maxLength:30,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'INFO_DEPT_PROPERTIES');
			}
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.licenceNum'),											//队员
			dataIndex : 'operateLicense',
			maxLength:30
		}];
	}
});
// 选择标准  grid
Ext.define('Foss.baseinfo.InfoDeptEntityWinGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.selectStandard'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
//	selType: 'cellmodel', 								// 选择类型设置为：行选择
	emptyText: baseinfo.infoDept.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	viewState:null,										//查看状态
	frame: true,
//	plugins:[Ext.create('Ext.grid.plugin.CellEditing', {
//		clicksToEdit: 1
//	})],
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getGridStore(config);
		me.listeners = me.getMyListeners(config);
		me.callParent([cfg]);
	},
	//监听事件
	getMyListeners:function(config){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	itemdblclick: function(view,record,item,index) {
	    		if(baseinfo.viewState.view != config.viewState){
	    			var win = Ext.create('Ext.window.Window',{
	    				width :300,
	    				height :180,
	    				closable : true,
	    				resizable:false,
	    				modal : true,
	    				items:[{
	    					xtype:'form',
	    				    items: [{
	    				    	xtype:'numberfield',
	    				        fieldLabel: baseinfo.infoDept.i18n('foss.baseinfo.infoDept.score'),
	    				        name: 'infoDeptStdScore',
	    				        minValue:0,
	    				        hideTrigger:true,
	    				        mouseWheelEnabled:false,
	    				        decimalPrecision:0,
	    				        maxValue:record.get('stdScore'),
	    				        allowBlank: false
	    				    },{
	    				    	xtype:'textareafield',
	    				        fieldLabel: baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.remark'),
	    				        name: 'notes',
	    				        maxLength:200,
	    				        height :40
	    				    }],
	    				    buttons: [{
	    				        text: baseinfo.infoDept.i18n('foss.baseinfo.cancel'),
	    				        handler: function() {
	    				            this.up('window').close();
	    				        }
	    				    }, {
	    				        text: baseinfo.infoDept.i18n('foss.baseinfo.save'),
	    				        handler: function() {
	    				            var form = this.up('form').getForm();
	    				            if (form.isValid()) {
	    				            	// 总分赋值
	    								var totalScore = me.up('window').editForm.getForm().findField('totalScore'),
	    								infoDeptStdScore = Ext.isEmpty(record.get('infoDeptStdScore'))||0===record.get('infoDeptStdScore')?0:record.get('infoDeptStdScore'),
	    								total = totalScore.getValue()+form.findField('infoDeptStdScore').getValue()-infoDeptStdScore;
	    								totalScore.setValue(total);
	    								// 更新 grid显示
	    								form.updateRecord(record);
		    				            this.up('window').close();
	    				            }else{
	    				            	baseinfo.showInfoMsg(baseinfo.infoDept.i18n('foss.baseinfo.infoDept.checkDataIsRighte'));
	    				            }
	    				        }
	    				    }]
	    				}]
	    			});
	    			win.down('form').loadRecord(record);
	    			win.show();
	    		}
	    	}
	    };
	},
	//表格数据源
	getGridStore:function(config){
		var me = this;
		return Ext.create('Foss.baseinfo.infoDept.InfoDeptScoresEntityStore');
	},
	//表格数据列
	getColumns:function(config){
		var me = this;
		return [{xtype: 'rownumberer'},{
			xtype:'linebreakcolumn',
			flex:1,
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.selectContant'),						//适用部门
			dataIndex : 'stdContent'
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.score'),						//广告语内容
			dataIndex : 'infoDeptStdScore'
//			xtype: 'numbercolumn',
//			editor:{
//				xtype: 'numberfield',
////				readOnly:('VIEW' == me.status ),
//				minValue:0,
//				maxValue:35.1,
//		        hideTrigger:true,
//		        mouseWheelEnabled:false,
//		        decimalPrecision:0,
//				hideTrigger:true,
//				listeners:{
//					change:function(field,newV,oldV){
//						if(newV != oldV){
//							var totalScore = me.up('window').editForm.getForm().findField('totalScore');
//							totalScore.setValue(totalScore.getValue()+newV-oldV);
//						}
//					}
//				}
//			},
//			renderer:function(v){
////				var totalScore = me.up('window').editForm.getForm().findField('totalScore');
////				totalScore.setValue(totalScore.getValue()+newV-oldV);
//				return v;
//			}
		},{
			xtype:'linebreakcolumn',
			text : baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.remark'),						//广告语内容
			dataIndex : 'notes'
//			editor:{
//				xtype: 'textareafield',
////				disabled:('VIEW' == me.status ),
//				hideTrigger:true
//			}
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	//非异步请求 得到信息部 得分标准
	Ext.Ajax.request({
		url:baseinfo.realPath('queryInfoDeptScoresStdList.action'),
		async:false,//默认是异步
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData = result.objectVo.infoDeptScoresStdList;
				// 数据转换 把 后台的 得分标准 转换成 前端展示的 得分标准
				for(var i in baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData){
					baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData[i].infodeptId = '';
					baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData[i].notes = '';
					baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData[i].infoDeptStdScore = 0;
					baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData[i].scoreId = '';
				}
			}else{
				baseinfo.showInfoMsg(result.message);
				baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData = [];
			}
		},
		exception:function(response){
			baseinfo.showInfoMsg(Ext.decode(response.responseText).message);
			baseinfo.infoDept.InfoDeptScoresEntityStoreDefaultData = [];
		}
	});
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-infoDeptIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.infoDept.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.sMSTempleteIndex.InfoDeptEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.infoDept.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-infoDeptIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-infoDeptIndex_content',
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
		items : [ queryForm, queryGrid,{
			xtype:'button',
			text:baseinfo.infoDept.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexCancelButton'),
			hidden:!baseinfo.infoDept.isPermission('infoDeptIndex/infoDeptIndexCancelButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				baseinfo.infoDept.deleteEntityByCode(baseinfo.delType,null,Ext.getCmp('T_baseinfo-infoDeptIndex_content').getQueryGrid(),baseinfo.levelType.p);
			}
		}]
//		renderTo : 'T_baseinfo-infoDeptIndex-body'
	}));
});
//------------------------------------WINDOW--------------------------------
//信息部界面win
Ext.define('Foss.baseinfo.infoDept.InfoDeptEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.infoDept.i18n('foss.baseinfo.infoDept.addInfoDept'),								//新增信息部   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	autoScroll:true,
	width :900,
	height :600,	
//	layout : {
//		type : 'hbox',
//		align : 'stretch'
//	},
	layout : 'column',
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
			baseinfo.infoDept.sourceFileArray = [];
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//信息部表单Form
	editGrid:null,											//信息部表格Grid
	formRecord:null,										//信息部实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//信息部 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);//'height':265,
		me.editGrid = Ext.create('Foss.baseinfo.InfoDeptEntityWinGrid',{'width':530,'viewState':config.viewState,'colspan':2});
		me.editForm = Ext.create('Foss.baseinfo.infoDept.InfoDeptEntityWinForm',{'editGrid':me.editGrid,'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [{
			xtype:'container',
			width :600,
			layout : {
				type : 'fit',
				align : 'stretch'
			},
			items:[me.editForm]
		},Ext.create('Deppon.ux.FileUploadGrid', {
			columnWidth :1,
			height:500,
			file_upload_limit:4,
	         uploadUrl: baseinfo.realPath('uploadFiles.action'),
	         fileTypes: ['jpg','jpeg','gif','bmp','png'],
	         downLoadUrl: baseinfo.realPath('downLoadFiles.action'),
	         deleteUrl: baseinfo.realPath('deleteFile.action'),
	         imgReviewUrl: baseinfo.realPath('reviewImg.action')
	        })];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text : baseinfo.infoDept.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.infoDept.initWinData(me,config.viewState,config.formRecord,[],baseinfo.levelType.p);
			} 
		},{
			text : baseinfo.infoDept.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				//verifyScoresStoreArray = ['returnV':true,'record':null]
		    	var editForm = me.editForm.getForm(),verifyScoresStoreArray = baseinfo.infoDept.verifyScoresStore(me.editGrid,[true,null]);
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.infoDept.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}else if(!verifyScoresStoreArray[0]){
		    		baseinfo.showInfoMsg('<span style="color:red;">'+verifyScoresStoreArray[1].get('stdContent') + baseinfo.infoDept.i18n('foss.baseinfo.infoDept.standardWriteError'));
		    		return;
		    	}
		    	var fileArray = baseinfo.infoDept.verifyFileuploadgrid(me.down('fileuploadgrid'));
		    	if(!Ext.isEmpty(fileArray)){
		    		//【信息部老板身份证正面，身份证反面，营业执照复印件，信息部照片】
		    		var fileDisArray = ['idCardFrontPic','idCardBackPic','operateLicCopy','infoDeptPic'];
		    		for(var i = 0;i<fileArray.length;i++){
		    			me.formRecord.set(fileDisArray[i],fileArray[i].get('fileName')
		    					+baseinfo.infoDept.split+fileArray[i].get('relativePath')
		    					+baseinfo.infoDept.split+fileArray[i].get('fileType')
		    					+baseinfo.infoDept.split+fileArray[i].get('id')
		    					+baseinfo.infoDept.split+fileArray[i].get('status')
		    					);
		    		}
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		
	    		
	    		baseinfo.infoDept.submitEntity(me,me.viewState,me.formRecord.data,baseinfo.levelType.p);
			}
		}];
	}
});

