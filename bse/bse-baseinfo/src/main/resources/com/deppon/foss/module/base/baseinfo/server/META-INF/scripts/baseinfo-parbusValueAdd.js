/**
 * 快递代理增值服务
 * 
 * @author:黎超
 * Build date: 2012-11-30
 * 
 */
//定义一个model
Ext.define('Foss.baseinfo.parbusValueAdd.parbusValueAddModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'virtualCode',  //虚拟编码
		type : 'string'
	}, {
		name : 'simpleCode',       //线路简码
		type : 'string'
	}, {
		name : 'lineName', //线路名称
		type : 'string'
	}, {
		name : 'organizationCode', //管理部门（车队）编码
		type : 'string'
	}, {
		name : 'organizationName',       //管理部门（车队）名称
		type : 'string'
	},{
		name : 'orginalOrganizationCode', //出发部门编码
		type : 'string'
	},{
		name : 'destinationOrganizationCode',  //到达部门编码
		type : 'string'
	},{
		name : 'orginalOrganizationName', //出发部门名称
		type : 'string'
	},{
		name : 'destinationOrganizationName', //到达部门名称
		type : 'string'
	},{
		name : 'orginalCityCode', //出发城市
		type : 'string'
	},{
		name : 'destinationCityCode', //到达城市
		type : 'string'
	},{
		name : 'orginalCityName', //出发城市名称
		type : 'string'
	},{
		name : 'destinationCityName', //到达城市名称
		type : 'string'
	},{
		name : 'transType', //运输类型（汽运，空运）-始发到达
		type : 'string'
	},{
		name : 'lineType', //线路类别 （始发，到达，中转到中转
		type : 'string'
	},{
		name : 'isDefault', //是否默认线路 - 始发到达
		type : 'string'
	},{
		name : 'commonAging', //普车时效
		defaultValue : null
	},{
		name : 'fastAging', //卡车时效（千分之小时）
		defaultValue : null
	},{
		name : 'otherAging', //偏线时效
		defaultValue : null
	},{
		name : 'distance', //线路距离(公里)
		type:'int'
	},{
		name : 'active', //是否有效
		type : 'string'
	},{
		name : 'notes', //备注
		type : 'string'
	},{
		name : 'createUser',     //创建人
		type : 'string'
	}, {
		name : 'createDate',     //创建时间
		type : 'date'
	}, {
		name : 'modifyUser',     //修改人
		type : 'string'
	}, {
		name : 'modifyDate',     //修改时间
		type : 'date'
	} ]
});

//发车标准实体
Ext.define('Foss.baseinfo.parbusValueAdd.StartStandardModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'order',// 班次
        defaultValue : null
    },{
        name : 'virtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'lineVirtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'leaveTime',// 准点发车时间(eg: 0200)
        type : 'string'
    },{
        name : 'arriveTime',// 准点到达时间(eg: 1645)
        type : 'string'
    },{
        name : 'arriveDay', // 准点到达时间的天数,默认是0
        type : 'string'
    },{
        name : 'active',// 是否有效
        type : 'string'
    },{
        name : 'productType',// 时效类型
        type : 'string'
    },{
        name : 'notes', // 备注
        type : 'string'
    },{
		name : 'orginalOrganizationName', //出发部门名称
		type : 'string'
	},{
		name : 'destinationOrganizationName', //到达部门名称
		type : 'string'
	}]
});

//创建一个到达线路的store
Ext.define('Foss.baseinfo.parbusValueAdd.parbusValueAddStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.parbusValueAdd.parbusValueAddModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryparbusValueAdd.action'),
		reader : {
			type : 'json',
			root : 'lineVo.lineEntities',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_parbusValueAdd_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'lineVo.entity.lineName':queryParams.lineName,
						'lineVo.entity.orginalOrganizationCode':queryParams.orginalOrganizationCode,
						'lineVo.entity.destinationOrganizationCode':queryParams.destinationOrganizationCode,
						'lineVo.entity.orginalCityCode':queryParams.orginalCityCode,
						'lineVo.entity.transType':queryParams.transType,
						'lineVo.entity.organizationCode':queryParams.organizationCode,
						'lineVo.entity.simpleCode':queryParams.simpleCode
					}
				});	
			}
		}
	}
});


//创建一个发车标准的store
Ext.define('Foss.baseinfo.parbusValueAdd.StartStandardStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.parbusValueAdd.StartStandardModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//创建一个本地的管理部门（车队）名称store
Ext.define('Foss.baseinfo.parbusValueAdd.TransTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//自定义确认密码校验
Ext.apply(Ext.form.VTypes, {
    confirmPwd : function(val, field) {
        if (field.confirmPwd) {
            var firstPwdId = field.confirmPwd.first;
            var secondPwdId = field.confirmPwd.second;
            this.firstField = Ext.getCmp(firstPwdId);
            this.secondField = Ext.getCmp(secondPwdId);
            var firstPwd = this.firstField.getValue();
            var secondPwd = this.secondField.getValue();
            if (firstPwd == secondPwd) {
                return true;
            } else {
                return false;
            }
        }
    }
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.parbusValueAdd.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.parbusValueAdd.i18n('foss.baseinfo.ldpqueryCondition'),//查询条件
	id : 'Foss_baseinfo_parbusValueAdd_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[ {
		name : 'expressPartbussCode',
		fieldLabel : baseinfo.parbusValueAdd.i18n('foss.baseinfo.expressPartbussCode'),
		columnWidth : .25,
		xtype : 'commonLdpAgencyCompanySelector'
	}, {
		name : 'loadOrgCode',
		fieldLabel : baseinfo.parbusValueAdd.i18n('foss.baseinfo.loadOrgCode'),
		columnWidth : .25,
		xtype : 'dynamicorgcombselector',
        types : 'ORG',
        transferCenter:'Y',//--或者查询外场
        airDispatch  : 'Y'//--空运总调
	}, {
		xtype: 'datetimefield_date97',
		format : 'Y-m-d H:i:s',
		id: 'contrabandSign-QueryPanel-startTime',
		fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.beginTime'),//开始时间
		//allowBlank:false,
		columnWidth:.25,
		editable:false,
		//value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		name: 'beginTime',
		time : true,
		dateConfig: {
			el : 'contrabandSign-QueryPanel-startTime-inputEl'
		}
	}, {
		xtype: 'datetimefield_date97',
		format : 'Y-m-d H:i:s',
		id: 'contrabandSign-QueryPanel-endTime',
		fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.endTime'),//结束时间
		//allowBlank:false,
		columnWidth:.25,
		editable:false,
		//value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		name: 'endTime',
		time : true,
		dateConfig: {
			el : 'contrabandSign-QueryPanel-endTime-inputEl'
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddQueryButton'),
			hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddQueryButton'),
			hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddQueryButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-parbusValueAdd_content').getparbusValueAddGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});


// 到达线路详细信息表单
Ext.define('Foss.baseinfo.parbusValueAdd.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
	items : [ {
			name: 'lineName',
			allowBlank:false,
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.lineName'),
	        readOnly:true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'simpleCode',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.simpleCode'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'organizationName',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.organizationCode'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'orginalOrganizationName',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.orginalOrganizationCode'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'destinationOrganizationName',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.destinationOrganizationCode'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'distance',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.distance'),//'线路距离(公里)'
	        allowBlank:false,
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'isDefaults',
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.isEndDefaults'),
	        allowBlank:true,
	        xtype : 'radiogroup',
	        layout:'column',
			defaultType: 'radio',
			defaults:{
 			width:100
 			},
			items: [{ 
					boxLabel  : baseinfo.parbusValueAdd.i18n('foss.baseinfo.yes'), 
					columnWidth:.5,
					name      : 'isDefault',
					inputValue: 'Y'
				}, { 
					boxLabel  : baseinfo.parbusValueAdd.i18n('foss.baseinfo.no'), 
					columnWidth:.5,
					name      : 'isDefault',
					inputValue: 'N',
					checked   : true
			}]
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxLength:1000,
	        width:450,
	        xtype : 'textareafield'
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

// 新增/修改 快递代理增值服务界面
Ext.define('Foss.baseinfo.parbusValueAdd.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 330,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    //提交表单
    commitInfo : function(){
    	var me = this;
    	//获取表单
    	var basicForm = this.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	
    		//获取model实例
			var record = null;
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{
				record = Ext.create('Foss.baseinfo.parbusValueAdd.parbusValueAddModel');
			}
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			
    		var jsonData = {'lineVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateparbusValueAdd.action');//请求到达线路修改
			}else{
				url = baseinfo.realPath('addparbusValueAdd.action');//请求到达线路新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-parbusValueAdd_content').getparbusValueAddGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
					  
					  if(Ext.isEmpty(json.lineVo.entity)){
						infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.serverException'));//服务端有异常！
						return;
					  }
					  //将返回的值设置到window中
					  me.up('window').infoModel = json.lineVo.entity;
					  if(me.isUpdate){//修改
						return;//返回，不做以下操作
					  }else{//新增
						me.getForm().getFields( ).each(function(item,index,length){
							item.setReadOnly(true);//(对于numberfield的样式社会有问题)
						});//将FORM设置为不可用
						//me.doLayout( );
						me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
						me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
						me.up('window').getStartStandardGrid().getDockedItems()[1].items.items[0].setDisabled(false);//新增发车标准按钮可用
					  }
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [  {
		name : 'expressPartbussCode',
		fieldLabel : baseinfo.parbusValueAdd.i18n('foss.baseinfo.expressPartbussCode'),
		columnWidth : .25,
		xtype : 'commonLdpAgencyCompanySelector'
	}, {
		name : 'loadOrgCode',
		fieldLabel : baseinfo.parbusValueAdd.i18n('foss.baseinfo.loadOrgCode'),
		columnWidth : .25,
		xtype : 'dynamicorgcombselector',
        types : 'ORG',
        transferCenter:'Y',//--或者查询外场
        airDispatch  : 'Y'//--空运总调
	}, {
		xtype: 'datetimefield_date97',
		format : 'Y-m-d H:i:s',
		id: 'contrabandSign-QueryPanel-startTime-add1',
		fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.beginTime'),//开始时间
		//allowBlank:false,
		columnWidth:.25,
		editable:false,
		//value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		name: 'beginTime',
		time : true,
		dateConfig: {
			el : 'contrabandSign-QueryPanel-startTime-inputEl-add1'
		}
	}, {
		xtype: 'datetimefield_date97',
		format : 'Y-m-d H:i:s',
		id: 'contrabandSign-QueryPanel-endTime-add1',
		fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.endTime'),//结束时间
		//allowBlank:false,
		columnWidth:.25,
		editable:false,
		//value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		name: 'endTime',
		time : true,
		dateConfig: {
			el : 'contrabandSign-QueryPanel-endTime-inputEl-add1'
		}
	}, {
			name: 'description',//描述
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.description'),
	        colspan : 2,
	        maxLength:1000,
	        width:450,
	        xtype : 'textareafield'
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.parbusValueAdd.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.parbusValueAdd.parbusValueAddModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

/**
 * 发车标准-FORM
 */
Ext.define('Foss.baseinfo.parbusValueAdd.StandardForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:370,
	collapsible: true,
	isSearchComb:true,
	isUpdate:false,//是否为修改，默认false
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	
    	labelWidth:60,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
     //提交新增信息
    commitInfo:function(){
    	var me = this;
    	//获取表单
    	var basicForm = me.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	    //创建发车标准MODEL
    		var startStandardModel = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardModel');
    		
    		//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(startStandardModel);
    		//准点出发时间
    		var leaveTime = basicForm.findField('leaveTime').getRawValue( ) ;
    		//获取天数
    		var arriveDay = startStandardModel.get('arriveDay');
    		//准点出发时间
    		var leaveT = basicForm.findField('leaveTime').getValue( ) ;
    		//准点到达时间
    		var arriveT = basicForm.findField('arriveTime').getValue( ) ;
    		arriveT = new Date(new Date(arriveT).setDate(new Date(arriveT).getDate()+parseInt(arriveDay)));
    		//准点到达时间
    		var arriveTime = basicForm.findField('arriveTime').getRawValue( ) ;
    		startStandardModel.set('arriveTime',arriveTime);
    		startStandardModel.set('leaveTime',leaveTime);
    		//设置线路虚拟编码(数据来自线路window的属性（AddWindow/UpdateWindow）)
    		startStandardModel.set('lineVirtualCode',me.up('window').parent.up('window').infoModel.virtualCode);
    		
    		var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateStartStandard.action');//请求发车标准修改
				//设置修改信息的ID
				startStandardModel.set('id',me.up('window').infoModel.get('id'));
				//设置修改信息的虚拟编码
				startStandardModel.set('virtualCode',me.up('window').infoModel.get('virtualCode'));
			}else{
				url = baseinfo.realPath('addStartStandard.action');//请求发车标准新增
			}
    		//组织新增数据
    		var jsonData = {'standardVo':{'startStandardEntity':startStandardModel.data}};
    		var infoGrid = Ext.getCmp('T_baseinfo-parbusValueAdd_content').getparbusValueAddGrid();
    		if(arriveT < leaveT){
    			infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.arriveTimeNotice'));
    			return ;
    		}
    		me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
	  		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
    		//Ajax请求
    		Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
					  //新增的发车标准
					  var startStandardModel = json.standardVo.startStandardEntity;	
					  if(me.isUpdate){
					  	//如果是更新 先删除数据
					  	me.up('window').parent.getStore().remove(me.up('window').infoModel);
					  }else{
					  	me.getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮不可用
					  	me.getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮不可用
					  }
					  //加到父元素grid的store中				  
					  me.up('window').parent.getStore().add(new Foss.baseinfo.parbusValueAdd.StartStandardModel(startStandardModel));
    				  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		        	me.getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
		        	me.getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
		              var json = Ext.decode(response.responseText);
		              if(Ext.isEmpty(json)){
		              	infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.requestOvertime'));
		              }else{
		              	//失败消息
		              	infoGrid.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
		              }
		        }
			});
    	}
    },
    items : [{
			name: 'order',//班次
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.order'),
	        step:1,
	        allowBlank:false,
	        maxValue:999999,
	        minValue:1,
	        xtype : 'numberfield'
		},{
	        xtype : 'label'//占空间
		},{
			name: 'orginalOrganizationName',
			readOnly:true,
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.orginalOrganizationCode'),//只读不可编辑，数据从线路中获取
	        xtype : 'textfield'
		},{
			xtype: 'timefield',
			labelWidth:100,
		    fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.leaveTime'),//准点出发时间
            name:'leaveTime',
            format:'H:i',//24小时制
            allowBlank:false
		},{
			name: 'destinationOrganizationName',//到达站
			readOnly:true,//只读不可编辑，数据从线路中获取
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.destinationOrganizationCode'),
	        xtype : 'textfield'
		},{
			xtype : 'container',//一个容器
			defaults : {
		    	colspan : 1,
		    	margin : '0 0 0 0',
		    	labelWidth:100,
		    	
		    	anchor : '100%'
		    },
		    layout: {
		        type: 'table',
		        columns: 3
		    },
		    items:[{
		    	name: 'arriveTime',
				allowBlank:false,
				labelWidth:100,
				width:255,
		        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.arriveTime'),
		        format:'H:i',//24小时制
		        xtype : 'timefield'
		    },{
		    	name: 'arriveDay',
				allowBlank:false,
				width:120,
				labelWidth:40,
				minValue:0,
				decimalPrecision:0,
				labelSeparator:'',
		        fieldLabel: '+',
		        xtype : 'numberfield'
		    },{
		        xtype : 'label',
		        text:baseinfo.parbusValueAdd.i18n('foss.baseinfo.day')//天
		    }]
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.parbusValueAdd.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxlength:1000,
	        width:450,
	        xtype : 'textareafield'
	}],
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
				text :baseinfo.parbusValueAdd.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.parbusValueAdd.parbusValueAddModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
						var infoModel = me.up('window').infoModel;
						var gridStore = me.up('window').parent.getStore();
						//获取班次
						var order = gridStore.count()+1;
						me.getForm().findField('order').setValue(order);
						me.getForm().findField('orginalOrganizationName').setValue(infoModel.get('orginalOrganizationName'));
						me.getForm().findField('destinationOrganizationName').setValue(infoModel.get('destinationOrganizationName'));
					}
				} 
			},{
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
		}];
		
		me.callParent([cfg]);
	}
});

// 定义一个新增的窗口
Ext.define('Foss.baseinfo.parbusValueAdd.AddWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_parbusValueAdd_AddWindow_Id',
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.expressPartbussAddInfo'),
	width : 800,
	height : 700,
	isUpdate:false,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.getStartStandardGrid().getStore().removeAll();//清除发车标准列表
			me.parent.getPagingToolbar().moveFirst();
			
			//把设置为不可编辑的该为可编辑
			me.getAddUpdateForm().getForm().getFields( ).each(function(item,index,length){
				if(item.getName( )=='lineName'||item.getName( )=='orginalCityName'){
					//线路名称、始发城市是只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});//将FORM设置为可用
			
			//设置为不可用的按钮改为可用
			me.getAddUpdateForm().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
			me.getAddUpdateForm().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
		},
		beforeshow:function(me){//窗口显示之前事件
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.parbusValueAdd.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	startStandardGrid : null,
	//获取发车标准列表
	getStartStandardGrid : function(){
		if(Ext.isEmpty(this.startStandardGrid)){
			this.startStandardGrid = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardGrid');
			this.startStandardGrid.parent = this;
		}
		return this.startStandardGrid;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-parbusValueAdd_content').getparbusValueAddGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm(),me.getStartStandardGrid()];
		me.callParent([ cfg ]);
	}
});

// 定义一个修改的窗口
Ext.define('Foss.baseinfo.parbusValueAdd.UpdateWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_parbusValueAdd_UpdateWindow_Id',
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.updateparbusValueAdd'),
	width : 800,
	height : 700,
	isUpdate:true,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.getStartStandardGrid().getStore().removeAll();//清除发车标准列表
			me.parent.getPagingToolbar().moveFirst();
			//设置为不可用的按钮改为可用
//			me.getAddUpdateForm().getDockedItems()[0].items.items[1].setDisabled(false);//重置按钮可用
//			me.getAddUpdateForm().getDockedItems()[0].items.items[0].setDisabled(false);//保存按钮可用
			//设置为可用的按钮改为不可用
//			me.getStartStandardGrid().getDockedItems()[1].items.items[0].setDisabled(true);//新增按钮不可用
		},
		beforeshow:function(me){//窗口显示之前事件
		    me.getAddUpdateForm().getForm().loadRecord(new Foss.baseinfo.parbusValueAdd.parbusValueAddModel(me.infoModel));//加载线路信息
		    //选择器赋值
		    me.getAddUpdateForm().getForm().findField('organizationCode').setCombValue(me.infoModel.organizationName,me.infoModel.organizationCode);
		    me.getAddUpdateForm().getForm().findField('destinationOrganizationCode').setCombValue(me.infoModel.destinationOrganizationName,me.infoModel.destinationOrganizationCode);
		    me.getAddUpdateForm().getForm().findField('orginalOrganizationCode').setCombValue(me.infoModel.orginalOrganizationName,me.infoModel.orginalOrganizationCode);
		    if(!Ext.isEmpty(me.standardList)){
				me.getStartStandardGrid().getStore().loadData(me.standardList);//加载发车标准信息
			}
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.parbusValueAdd.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	startStandardGrid : null,
	//获取发车标准列表
	getStartStandardGrid : function(){
		if(Ext.isEmpty(this.startStandardGrid)){
			this.startStandardGrid = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardGrid');
			this.startStandardGrid.parent = this;
			this.startStandardGrid.getDockedItems()[0].items.items[0].setDisabled(false);//新增按钮可用
		}
		return this.startStandardGrid;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-parbusValueAdd_content').getparbusValueAddGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm(),me.getStartStandardGrid()];
		me.callParent([ cfg ]);
	}
});



// 定义一个表格列表
Ext.define('Foss.baseinfo.parbusValueAdd.parbusValueAddGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_parbusValueAdd_parbusValueAddGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.expressPartbussAddInfo'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	autoScroll:true,
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.parbusValueAdd.InfoPanel'
	} ],
    
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.parbusValueAdd.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.parbusValueAdd.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.warnMsg'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.parbusValueAdd.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.parbusValueAdd.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放ID的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('virtualCode'));
				}
				
				var url = baseinfo.realPath('deleteparbusValueAdd.action');
				var jsonData = {'lineVo':{'codeList':ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
	//Ajax请求
	requestAjax : function(url,jsonData,successFn,failFn)
	{
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.success){
					successFn(result);
				}else{
					failFn(result);
				}
			},
			failure:function(response){
				var result = Ext.decode(response.responseText);
				failFn(result);
			}
		});
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	},
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:10,
					prependButtons: true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
       return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
//							icon : '../images/baseinfo/edit.png',
                            iconCls:'deppon_icons_edit',
							tooltip : baseinfo.parbusValueAdd.i18n('foss.baseinfo.edit'),
							disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddUpdateButton'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								//获取虚拟编码
								var virtualCode = record.get('virtualCode');
								var jsonData = {'lineVo':{'lineVirtualCode' : virtualCode}};
			    				//进行Ajax请求
                                Ext.Ajax.request({
									url:baseinfo.realPath('queryparbusValueAddStandard.action'),
									jsonData:jsonData,
									//作废成功
									success : function(response) {
					                  	var json = Ext.decode(response.responseText);
						                  
					                  	//获得修改窗口
										var updateWindow = grid.up('grid').getUpdateWindow();
										//获取修改的Model信息
										updateWindow.infoModel = json.lineVo.entity;
										//发车标准列表
										updateWindow.standardList = json.lineVo.departureStandardEntityList;
										updateWindow.resetWindow(record,'update');
										updateWindow.show();
					                  	//保存成功列表数据重新加载
					                  	grid.up('grid').getPagingToolbar().moveFirst();
					                  	
						                },
						            //保存失败
						            exception : function(response) {
					                  	var json = Ext.decode(response.responseText);
						            }
								});
							}
						},
						{
//							icon : '../images/baseinfo/delete.png',
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.parbusValueAdd.i18n('foss.baseinfo.void'),
							disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),
									msg: baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteWarnMsg'),//'作废后不可恢复，确认要作废么？'
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											var ids = new Array();//线路ID数组
											ids.push(record.data.virtualCode);
											var url = baseinfo.realPath('deleteparbusValueAdd.action');
											var jsonData = {'lineVo':{'codeList':ids}};
											//调用Ajax请求
											grid.up('grid').ajaxRequest(url,jsonData);
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.loadOrgName'),
				// 关联model中的字段名  配载部门
				dataIndex : 'loadOrgName',
				flex : 1
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.expressPartbussName'),
				// 关联model中的字段名  快递代理公司
				dataIndex : 'expressPartbussName',
				width : 100
			} , {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.active'),
				// 关联model中的字段名  状态
				dataIndex : 'active',
				width : 120
			} , {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.beginTime'),
				// 关联model中的字段名  开始时间
				dataIndex : 'beginTime',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.endTime'),
				// 关联model中的字段名  结束时间
				dataIndex : 'endTime',
				width : 120
			}, 
			 {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.modifyUserName'),
				// 关联model中的字段名  最后创建人
				dataIndex : 'modifyUserName',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.modifyTime'),
				// 关联model中的字段名  最后创建时间
				dataIndex : 'modifyTime',
				width : 120
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.codRate'),
				// 关联model中的字段名  代收货款手续费率
				dataIndex : 'codRate',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.minOilAddFee'),
				// 关联model中的字段名  代收货款手续费最低一票
				dataIndex : 'minOilAddFee',
				width : 120
			},
			 {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.insuranceRate'),
				// 关联model中的字段名  保价费率
				dataIndex : 'insuranceRate',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.minInsuranceFee'),
				// 关联model中的字段名  保价费最低一票
				dataIndex : 'minOilAddFee',
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.parbusValueAdd.parbusValueAddStore');
				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
						xtype : 'button',
						text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.add'),//新增
						disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddAddButton'),
						hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddAddButton'),
						width : 80,
						handler : function() {
						    //调用新增弹出窗口函数
//							var model = Ext.create('Foss.baseinfo.parbusValueAdd.parbusValueAddModel');
                            this.addWindow = me.getAddWindow();
//				            this.addWindow.resetWindow(model,'save');
				            this.addWindow.show();
						}
					},
					{
						xtype : 'button',
						text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.activation'),//激活
						disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						width : 80,
						handler : function() {
						    //调用删除函数
							me.deleteInfos();
						}
					},{
						xtype : 'button',
						text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.delete'),//删除
						disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						width : 80,
						handler : function() {
						    //调用删除函数
							me.deleteInfos();
						}
					},{
						xtype : 'button',
						text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.activatedImmediately'),//立即激活
						disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						width : 80,
						handler : function() {
						    //调用删除函数
							me.deleteInfos();
						}
					},{
						xtype : 'button',
						text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.terminationImmediately'),//立即终止
						disabled:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						hidden:!baseinfo.parbusValueAdd.isPermission('parbusValueAdd/parbusValueAddVoidButton'),
						width : 80,
						handler : function() {
						    //调用删除函数
							me.deleteInfos();
						}
					}
					]
		}], 
		me.callParent([ cfg ]);
	}
	});

/**
 * 新增发车标准信息
 */
Ext.define('Foss.baseinfo.parbusValueAdd.StandardAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.addStandard'),//新增发车标准
	closable : true,
	modal : true,
	isUpdate:false,//是否为修改，默认false
	parent:null,//父元素
	resizable:false,
	infoModel:null,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :750,
	height :450,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
			me.getStandardForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//window显示所进行操作
			var gridStore = me.parent.getStore();
			//获取班次
			var order = gridStore.count()+1;
			me.getStandardForm().getForm().findField('order').setValue(order);
			me.getStandardForm().getForm().findField('orginalOrganizationName').setValue(me.infoModel.get('orginalOrganizationName'));
			me.getStandardForm().getForm().findField('destinationOrganizationName').setValue(me.infoModel.get('destinationOrganizationName'));
		}
	},
	resetWindow : function(record) {
    	//加载数据
//		this.getStandardForm().loadRecord(record);
		this.infoModel = record;
	},
	//发车标准FORM
	standardForm:null,
    getStandardForm : function(){
    	if(Ext.isEmpty(this.standardForm)){
    		this.standardForm = Ext.create('Foss.baseinfo.parbusValueAdd.StandardForm');
    	}
    	return this.standardForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getStandardForm()];
		me.callParent([cfg]);
	}
});

/**
 * 修改发车标准信息
 */
Ext.define('Foss.baseinfo.parbusValueAdd.StandardUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.updateStandard'),
	closable : true,
	modal : true,
	isUpdate:true,//是否为修改
	parent:null,//父元素
	resizable:false,
	closeAction : 'hide',
	infoModel : null,//绑定的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :750,
	height :450,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
			me.getStandardForm().getForm().reset();//表格重置
			
		},
		beforeshow:function(me){//window显示所进行操作
			var record = new Foss.baseinfo.parbusValueAdd.StartStandardModel(me.infoModel.data)
//			var oldLeaveTime = record.get('leaveTime');
//			//封装准点发车时间
//			var leaveTime = oldLeaveTime.substring(0,2)+":"+oldLeaveTime.substring(2,4);
//			var oldArriveTime = record.get('arriveTime');
//			//准点到达时间
//			var arriveTime = oldArriveTime.substring(0,2)+":"+oldArriveTime.substring(2,4);
//			record.set('leaveTime',leaveTime);
//			record.set('arriveTime',arriveTime);
			me.getStandardForm().loadRecord(record);
			
		}
	},
	//发车标准FORM
	standardForm:null,
    getStandardForm : function(){
    	if(Ext.isEmpty(this.standardForm)){
    		this.standardForm = Ext.create('Foss.baseinfo.parbusValueAdd.StandardForm');
    		this.standardForm.isUpdate = true;
    	}
    	return this.standardForm;
    },
    resetWindow : function(record) {
    	//加载数据
//		this.getStandardForm().loadRecord(record);
		this.infoModel = record;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getStandardForm()];
		me.callParent([cfg]);
	}
});

/**
 * 定义发车标准列表
 */
Ext.define('Foss.baseinfo.parbusValueAdd.StartStandardGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	height : 260,
	stripeRows : true,
	parent : null,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	store : null,
	autoScroll:true,
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.warnMsg'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.parbusValueAdd.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.parbusValueAdd.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放ID的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				
				var url = baseinfo.realPath('deleteparbusValueAdd.action');
				var jsonData = {'lineVo':{'entity':{ids:ids}}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	addStandardWindow : null,
	//获取新增发车标准窗口
	getAddStandardWindow : function(){
		if(Ext.isEmpty(this.addStandardwindow)){
			this.addStandardwindow = Ext.create('Foss.baseinfo.parbusValueAdd.StandardAddWindow');
			//设置器父元素
			this.addStandardwindow.parent = this;
		}
		return this.addStandardwindow;
	},
	updateStandardWindow : null,
	//获取修改发车标准窗口
	getUpdateStandardWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updateStandardWindow = Ext.create('Foss.baseinfo.parbusValueAdd.StandardUpdateWindow');
			//设置器父元素
			this.updateStandardWindow.parent = this;
		}
		return this.updateStandardWindow;
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
            }
		});
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	},
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.parbusValueAdd.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
							iconCls:'deppon_icons_edit',
							tooltip : baseinfo.parbusValueAdd.i18n('foss.baseinfo.edit'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								//出发站
								var orginalOrganizationName = grid.up('grid').up('window').infoModel.orginalOrganizationName;
								//到达站
								var destinationOrganizationName = grid.up('grid').up('window').infoModel.destinationOrganizationName;
								
								record.set('orginalOrganizationName',orginalOrganizationName);
								record.set('destinationOrganizationName',destinationOrganizationName);
								
								var updateStandardWindow = grid.up('grid').getUpdateStandardWindow();
								//绑定数据
								updateStandardWindow.resetWindow(record);
								updateStandardWindow.show();
							}
						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.parbusValueAdd.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),
									msg:baseinfo.parbusValueAdd.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											
											var url = baseinfo.realPath('deleteStartStandard.action');
											var jsonData = {'standardVo':{'startStandardEntity':record.data}};
											//调用Ajax请求
											Ext.Ajax.request({
												url:url,
												jsonData:jsonData,
												//作废成功
												success : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //作废成功从Store里面清楚该数据
									                  grid.getStore().remove(record);
									                  //打印成功消息
									                  grid.up('grid').showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
									                },
									            //保存失败
									            exception : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //打印作废失败消息
									                  grid.up('grid').showWarningMsg(baseinfo.parbusValueAdd.i18n('foss.baseinfo.notice'),json.message);
									            }
											});
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.order'),
				// 关联model中的字段名
				dataIndex : 'order',
				flex : 1
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.leaveTimeb'),
				// 关联model中的字段名
				dataIndex : 'leaveTime',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.arriveTime'),
				// 关联model中的字段名
				dataIndex : 'arriveTime',
				width : 120,
				renderer:function(value,metaData ,record){
					return value +'<font color="blue">'+'+'+record.get('arriveDay')+baseinfo.parbusValueAdd.i18n('foss.baseinfo.day')+'</font>';
				}
			},{
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.airagencycompany.remark'),
				// 关联model中的字段名
				dataIndex : 'notes',
				width : 80
			} ],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardStore');
				me.tbar = [{
					text :baseinfo.parbusValueAdd.i18n('foss.baseinfo.addStandard'),
					disabled:true,
					handler :function(){
						//定义一个Model me.up('window').parent.up('window').infoModel.virtualCode
						var record = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardModel');
						//出发站
						var orginalOrganizationName = me.up('window').infoModel.orginalOrganizationName;
						//到达站
						var destinationOrganizationName = me.up('window').infoModel.destinationOrganizationName;
						record.set('orginalOrganizationName',orginalOrganizationName);
						record.set('destinationOrganizationName',destinationOrganizationName);
						var addStandardWindow = me.getAddStandardWindow();
						//绑定数据
						addStandardWindow.resetWindow(record);
						addStandardWindow.show();
					} 
				}];
		me.callParent([ cfg ]);
	}
	});
	
/**
 * 定义发车标准显示信息列表
 */
Ext.define('Foss.baseinfo.parbusValueAdd.StartStandardInfoGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	height : 260,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.standard'),
	collapsible : true,
	animCollapse : true,
	store : null,
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.order'),
				// 关联model中的字段名
				dataIndex : 'order',
				width : 200
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.leaveTimeb'),
				// 关联model中的字段名
				dataIndex : 'leaveTime',
				width : 200
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.arriveTime'),
				// 关联model中的字段名
				dataIndex : 'arriveTime',
				width : 200
			}, {
				// 字段标题
				header : baseinfo.parbusValueAdd.i18n('foss.baseinfo.airagencycompany.remark'),
				// 关联model中的字段名
				dataIndex : 'notes',
				flex : 1
			} ],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardStore');
		me.callParent([ cfg ]);
	}
	});

// 查看记录的详细信息
Ext.define('Foss.baseinfo.parbusValueAdd.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.parbusValueAdd.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取到达线路详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.parbusValueAdd.DetailForm');
		}
		return this.infoForm;
	},
	startStandardGrid:null,
	//获取发车标准列表
	getStartStandardGrid : function(){
		if(Ext.isEmpty(this.startStandardGrid)){
			this.startStandardGrid = Ext.create('Foss.baseinfo.parbusValueAdd.StartStandardInfoGrid');
		}
		return this.startStandardGrid;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [ this.getInfoForm(),this.getStartStandardGrid() ];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		//获取线路虚拟编码
		var virtualCode = record.get('virtualCode');
		var jsonData = {'lineVo':{'lineVirtualCode' : virtualCode}};
		//进行Ajax请求
        Ext.Ajax.request({
			url:baseinfo.realPath('queryparbusValueAddStandard.action'),
			jsonData:jsonData,
			//成功
			success : function(response) {
              	var json = Ext.decode(response.responseText);
				//获取修改的Model信息
				var infoModel = new Foss.baseinfo.parbusValueAdd.parbusValueAddModel(json.lineVo.entity);
				// 绑定表格数据到表单上
				me.getInfoForm().getForm().loadRecord(infoModel);
				//发车标准列表
				var standardList = json.lineVo.departureStandardEntityList;
				me.getStartStandardGrid().getStore().loadData(standardList);//加载发车标准信息
              	
                },
            //查询数据失败
            exception : function(response) {
              	var json = Ext.decode(response.responseText);
            }
		});
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.parbusValueAdd.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.parbusValueAdd.parbusValueAddGrid');

	Ext.getCmp('T_baseinfo-parbusValueAdd').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-parbusValueAdd_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getparbusValueAddGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
	}));
});

