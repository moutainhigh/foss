/**
 * 包装供应商信息
 * 
 * @author:187862-杜军辉
 * Build date: 2014-5-9 上午9:38:26
 * 
 */
//正则表达式:有效时间限制”以8位数字开头,中间为字符-,以8位数字结束“
baseinfo.regCodeLimit = {effectiveDate:/^\d{8}-\d{8}$/};
//定义一个Model
Ext.define('Foss.baseinfo.packagingSupplier.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'orgCode',  //部门名称
		type : 'string'
	}, {
		name : 'orgCodeCode',  //部门名称编码
		type : 'string'
	}, {
		name : 'packagingSupplierCode', //包装供应商代码
		type : 'string'
	}, {
		name : 'packagingSupplier',  //包装供应商名字
		type : 'string'
	}, {
		name : 'packagingSupplierPhone',     //包装供应商电话
		type : 'string'
	}, {
		name : 'woodenFrame',  //打木架单价
		type : 'string'
	}, {
		name : 'woodPallet', //打木托单价
		type : 'string'
	}, {
		name : 'bagLine',  //打包带单价
		type : 'string'
	}, {
		name : 'wood',     //木条单价
		type : 'string'
	}, {
		name : 'bubblefilm',  //气泡膜单价
		type : 'string'
	},{
		name : 'wrappingFilm', //缠绕膜单价
		type : 'string'
	}, {
		name : 'woodBox',  //打木箱单价
		type : 'string'
	}, {
		name : 'breakageRate', //破损率参数
		type : 'string'
	}, {
		name : 'woodenFrameStartVolume',  //打木架起步体积
		type : 'string'
	}, {
		name : 'woodenFrameMin',     //打木架最低一票
		type : 'string'
	}, {
		name : 'woodBoxStartVolume',  //打木箱起步体积
		type : 'string'
	},{
		name : 'woodBoxMin', //打木箱最低一票
		type : 'string'
	},{
		name : 'factoring', //是否保理
		type : 'string'
	}, {
		name : 'effectiveDate',  //有效时间
		type : 'string'
	}, {
		name : 'createDate',     //创建时间
		type : 'date',
		convert:dateConvert
	}, {
		name : 'createUser',  //创建人
		type : 'string'
	},{
		name : 'modifyDate',     //修改时间
		type : 'date',
		convert:dateConvert
	},{
		name : 'modifyUser', //修改人
		type : 'string'
	},{
		name : 'factoringDisplay', //修改人
		type : 'string'
	},{
		name : 'factoring', //修改人
		type : 'string'
	} ,{
		name : 'cusCode', //贷款客户编码
		type : 'string'
	},{
		name : 'account', //保理回款账号
		type : 'string'
	},{
		name : 'factorBeginTime', //保理开始日期
		type : 'date',
		convert:dateConvert
	},{
		name : 'factorEndTime', //保理结束日期
		type : 'date',
		convert:dateConvert
	}]
});

//创建包装供应商信息管理的Store
Ext.define('Foss.baseinfo.packagingSupplier.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定Model
    model: 'Foss.baseinfo.packagingSupplier.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryPackagingSupplier.action'),
		reader : {
			type : 'json',
			root : 'vo.entityList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_packagingSupplier_QueryForm_Id').getForm();
			if (queryForm != null) {
				var orgCode = queryForm.findField('orgCode').rawValue;
				Ext.apply(operation, {
					params : {
						'vo.entity.orgCode':orgCode //部门名称
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.packagingSupplier.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.packagingSupplier.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_packagingSupplier_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[
		    {
				/*xtype : 'textfield',
				columnWidth : 0.3,
				name : 'orgCode',
				fieldLabel : '外场名称'*/
			    
				xtype : 'dynamicorgcombselector',//dynamicorgcombselector commontransfercenterselector
				type : 'ORG',
				columnWidth : 0.3,
				name : 'orgCode',  
				forceSelection : true,
				transferCenter:'Y',
				fieldLabel : '外场名称'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.packagingSupplier.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.packagingSupplier.isPermission('packagingSupplier/autoResetButton'),
			hidden:!baseinfo.packagingSupplier.isPermission('packagingSupplier/autoResetButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('orgCode').setValue('');
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.packagingSupplier.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.packagingSupplier.isPermission('packagingSupplier/autoResetButton'),
			hidden:!baseinfo.packagingSupplier.isPermission('packagingSupplier/autoResetButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-packagingSupplier_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});

// 新增/修改包装供应商信息表单
Ext.define('Foss.baseinfo.packagingSupplier.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 500,
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
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
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
				record =new Foss.baseinfo.packagingSupplier.OriginatingLineModel();
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			//设置部门名称，部门编码，开始时间，结束时间，备注
			var orgCode =basicForm.findField('orgCode').rawValue,
			orgCodeCode =basicForm.findField('orgCode').getValue(),
			packagingSupplierCode =basicForm.findField('packagingSupplierCode').getValue();
			packagingSupplier =basicForm.findField('packagingSupplier').getValue();
			packagingSupplierPhone =basicForm.findField('packagingSupplierPhone').getValue();
			woodenFrame =basicForm.findField('woodenFrame').getValue();
			woodPallet =basicForm.findField('woodPallet').getValue();
			wood =basicForm.findField('wood').getValue();
			bubblefilm =basicForm.findField('bubblefilm').getValue();
			wrappingFilm =basicForm.findField('wrappingFilm').getValue();
			woodBox =basicForm.findField('woodBox').getValue();
			breakageRate =basicForm.findField('breakageRate').getValue();
			factoring=basicForm.findField('factoring').getValue();
			woodenFrameStartVolume =basicForm.findField('woodenFrameStartVolume').getValue();
			woodenFrameMin =basicForm.findField('woodenFrameMin').getValue();
			woodBoxStartVolume =basicForm.findField('woodBoxStartVolume').getValue();
			woodBoxMin =basicForm.findField('woodBoxMin').getValue();
			effectiveDate =basicForm.findField('effectiveDate').getValue();
			
			record.set('orgCode',orgCode);
			record.set('orgCodeCode',orgCodeCode);
			record.set('packagingSupplierCode',packagingSupplierCode);		
			record.set('packagingSupplier',packagingSupplier);
			record.set('packagingSupplierPhone',packagingSupplierPhone);
			record.set('woodenFrame',woodenFrame);
			record.set('woodPallet',woodPallet);
			record.set('wood',wood);	
			record.set('bubblefilm',bubblefilm);
			record.set('wrappingFilm',wrappingFilm);
			record.set('woodBox',woodBox);
			record.set('breakageRate',breakageRate);
			record.set('woodenFrameStartVolume',woodenFrameStartVolume);
			record.set('woodenFrameMin',woodenFrameMin);
			record.set('woodBoxStartVolume',woodBoxStartVolume);
			record.set('woodBoxMin',woodBoxMin);
			record.set('effectiveDate',effectiveDate);
			record.set('factoring',factoring);
    		var jsonData = {'vo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updatePackagingSupplier.action');//请求包装供应商信息修改
			}else{
				url = baseinfo.realPath('addPackagingSupplier.action');//请求包装供应商信息新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-packagingSupplier_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [{
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			columnWidth : 0.6,
			name : 'orgCode',  	
			allowBlank: false,
			forceSelection : true,
			transferCenter:'Y',
			fieldLabel : '外场名称'
	},{	
        fieldLabel: '包装供应商代码',
        name: 'packagingSupplierCode',
        allowBlank: false,
        xtype : 'textfield'
	},{
		fieldLabel:'包装供应商名字',
		name:'packagingSupplier',
		allowBlank: false,
		xtype : 'textfield'
	},{
		name: 'packagingSupplierPhone',
        fieldLabel: '包装供应商电话',
        allowBlank: false,
        xtype : 'textfield'
	},{
		name: 'woodenFrame',
        fieldLabel: '打木架单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodPallet',
        fieldLabel: '打木托单价/个',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'bagLine',
        fieldLabel: '打包带单价/根',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'wood',
        fieldLabel: '木条单价/米',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'bubblefilm',
        fieldLabel: '气泡膜单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'wrappingFilm',
        fieldLabel: '缠绕膜单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBox',
        fieldLabel: '打木箱单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'breakageRate',
        fieldLabel: '破损率参数（%）',
        allowBlank: false,
        maxValue: 100,
        minValue: 0,
        //allowDecimals:false,
        maxText:'此处为百分比，输入最大值为100',
        minText:'此处为百分比，输入最小值为0',
        xtype : 'numberfield'
	},{
		name: 'woodenFrameStartVolume',
        fieldLabel: '打木架起步体积',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodenFrameMin',
        fieldLabel: '打木架最低一票',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBoxStartVolume',
        fieldLabel: '打木箱起步体积',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBoxMin',
        fieldLabel: '打木箱最低一票',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'effectiveDate',
        fieldLabel: '有效时间',
        allowBlank: false,
        //emptyText:'20130101-20180101',
        afterSubTpl:'(输入格式，例如：20130101-20180101)',
        regex: baseinfo.regCodeLimit.effectiveDate,
        xtype : 'textfield'
		
        /*xtype : 'datetimefield_date97',
		fieldLabel:'有效时间',
		editable:false,
		name:'effectiveDate',
		labelWidth :70,
		columnWdth:.25,
		time : true,
		id : 'Foss_baseinfo_packagingSupplier_effectiveDate_ID',
		dateConfig: {
			el : 'Foss_baseinfo_packagingSupplier_effectiveDate_ID-inputEl'
		}*/
	},{
		name:'factoring',
		fieldLabel: '是否保理',
	    allowBlank: false,
	    xtype : 'combo',	    
	    store:baseinfo.getStore(null,null,['valueCode','valueName']
	    ,[
	      {'valueCode':'N','valueName':'否'},
	      {'valueCode':'Y','valueName':'是'}
	      ]),
	     displayField: 'valueName',
		 valueField: 'valueCode',
		 editable:false,
		 value:'N',
		 readOnly:true
	    
	}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text: baseinfo.packagingSupplier.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text: baseinfo.packagingSupplier.i18n('foss.baseinfo.reset'),//重置
				handler:function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.packagingSupplier.OriginatingLineModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text: baseinfo.packagingSupplier.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

//修改包装供应商信息表单
Ext.define('Foss.baseinfo.packagingSupplier.UpdateExchForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 500,
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
				record =new Foss.baseinfo.packagingSupplier.OriginatingLineModel();
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			//更新开始时间，结束时间，备注
			var orgCode =basicForm.findField('orgCode').rawValue,
			orgCodeCode =basicForm.findField('orgCode').getValue(),
			packagingSupplierCode =basicForm.findField('packagingSupplierCode').getValue();
			packagingSupplier =basicForm.findField('packagingSupplier').getValue();
			packagingSupplierPhone =basicForm.findField('packagingSupplierPhone').getValue();
			woodenFrame =basicForm.findField('woodenFrame').getValue();
			woodPallet =basicForm.findField('woodPallet').getValue();
			bagLine =basicForm.findField('bagLine').getValue();
			wood =basicForm.findField('wood').getValue();
			bubblefilm =basicForm.findField('bubblefilm').getValue();
			wrappingFilm =basicForm.findField('wrappingFilm').getValue();
			woodBox =basicForm.findField('woodBox').getValue();
			breakageRate =basicForm.findField('breakageRate').getValue();
			woodenFrameStartVolume =basicForm.findField('woodenFrameStartVolume').getValue();
			woodenFrameMin =basicForm.findField('woodenFrameMin').getValue();
			woodBoxStartVolume =basicForm.findField('woodBoxStartVolume').getValue();
			woodBoxMin =basicForm.findField('woodBoxMin').getValue();
			factoring=basicForm.findField('factoring').getValue();
			effectiveDate =basicForm.findField('effectiveDate').getValue();
			
			record.set('orgCode',orgCode);
			record.set('orgCodeCode',orgCodeCode);
			record.set('packagingSupplierCode',packagingSupplierCode);		
			record.set('packagingSupplier',packagingSupplier);
			record.set('packagingSupplierPhone',packagingSupplierPhone);
			record.set('woodenFrame',woodenFrame);
			record.set('woodPallet',woodPallet);
			record.set('bagLine',bagLine);
			record.set('wood',wood);	
			record.set('bubblefilm',bubblefilm);
			record.set('wrappingFilm',wrappingFilm);
			record.set('woodBox',woodBox);
			record.set('breakageRate',breakageRate);
			record.set('woodenFrameStartVolume',woodenFrameStartVolume);
			record.set('woodenFrameMin',woodenFrameMin);
			record.set('woodBoxStartVolume',woodBoxStartVolume);
			record.set('woodBoxMin',woodBoxMin);
			record.set('effectiveDate',effectiveDate);
			record.set('factoring',factoring);
 		var jsonData = {'vo':{entity:record.data}};
 		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updatePackagingSupplier.action');//请求修改
				record.set('orgCodeCode',basicForm.findField('orgCodeCode').getValue());
			}else{
				url = baseinfo.realPath('addPackagingSupplier.action');//请求新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-packagingSupplier_content').getOriginatingLineGrid(); 
         Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
	items : [{
		name: 'orgCodeCode',
        xtype : 'textfield',
        hidden:true
	},{
		name: 'orgCode',
        fieldLabel: '外场名称',
        allowBlank:false,
        readOnly:true,
        xtype : 'textfield'
	},{
		fieldLabel: '包装供应商代码',
        name: 'packagingSupplierCode',
        allowBlank: false,
        readOnly:true,
        xtype : 'textfield'
	},{
		fieldLabel:'包装供应商名字',
		name:'packagingSupplier',
		allowBlank: false,
		xtype : 'textfield'
	},{
		name: 'packagingSupplierPhone',
        fieldLabel: '包装供应商电话',
        allowBlank: false,
        xtype : 'textfield'
	},{
		name: 'woodenFrame',
        fieldLabel: '打木架单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodPallet',
        fieldLabel: '打木托单价/个',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'bagLine',
        fieldLabel: '打包带单价/根',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'wood',
        fieldLabel: '木条单价/米',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'bubblefilm',
        fieldLabel: '气泡膜单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'wrappingFilm',
        fieldLabel: '缠绕膜单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBox',
        fieldLabel: '打木箱单价/方',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'breakageRate',
        fieldLabel: '破损率参数（%）',
        allowBlank: false,
        maxValue: 100,
        minValue: 0,
        //allowDecimals:false,
        maxText:'此处为百分比，输入最大值为100',
        minText:'此处为百分比，输入最小值为0',
        xtype : 'numberfield'
	},{
		name: 'woodenFrameStartVolume',
        fieldLabel: '打木架起步体积',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodenFrameMin',
        fieldLabel: '打木架最低一票',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBoxStartVolume',
        fieldLabel: '打木箱起步体积',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'woodBoxMin',
        fieldLabel: '打木箱最低一票',
        allowBlank: false,
        minValue: 0,
        xtype : 'numberfield'
	},{
		name: 'effectiveDate',
        fieldLabel: '有效时间',
        allowBlank: false,
        //emptyText:'20130101-20180101',
        afterSubTpl:'(输入格式，例如：20130101-20180101)',
        regex: baseinfo.regCodeLimit.effectiveDate,
        xtype : 'textfield'
	},{
		name:'factoring',
		fieldLabel: '是否保理',
	    allowBlank: false,
	    xtype : 'combo',
	    store:baseinfo.getStore(null,null,['valueCode','valueName']
	    ,[
	      {'valueCode':'Y','valueName':'是'},
	      {'valueCode':'N','valueName':'否'}
	      ]),
	     displayField: 'valueName',
		 valueField: 'valueCode',
		 readOnly:true
	    
	}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text: baseinfo.packagingSupplier.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text: baseinfo.packagingSupplier.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});
// 定义一个新增的窗口
Ext.define('Foss.baseinfo.packagingSupplier.AddWindow', {
	extend : 'Ext.window.Window',
	title : '新增包装供应商信息',//'新增包装供应商信息'
	width : 700,
	height : 600,
	isUpdate:false,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){//窗口显示之前事件
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.packagingSupplier.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-packagingSupplier_content').getOriginatingLineGrid();
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
		me.items = [me.getAddUpdateForm()];
		me.callParent([ cfg ]);
	}
});

// 定义一个修改的窗口
Ext.define('Foss.baseinfo.packagingSupplier.UpdateWindow', {
	extend : 'Ext.window.Window',
	title : '修改包装供应商信息',//'修改包装供应商信息'
	width : 700,
	height : 600,
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
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){//窗口显示之前事件
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.packagingSupplier.UpdateExchForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	startStandardGrid : null,
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-packagingSupplier_content').getOriginatingLineGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.getAddUpdateForm().getForm().findField('orgCode').setValue(record.get('orgCode'));	
		this.getAddUpdateForm().getForm().findField('packagingSupplierCode').setValue(record.get('packagingSupplierCode'));
		this.getAddUpdateForm().getForm().findField('packagingSupplier').setValue(record.get('packagingSupplier'));
		this.getAddUpdateForm().getForm().findField('packagingSupplierPhone').setValue(record.get('packagingSupplierPhone'));	
		this.getAddUpdateForm().getForm().findField('woodenFrame').setValue(record.get('woodenFrame'));
		this.getAddUpdateForm().getForm().findField('woodPallet').setValue(record.get('woodPallet'));
		this.getAddUpdateForm().getForm().findField('bagLine').setValue(record.get('bagLine'));
		this.getAddUpdateForm().getForm().findField('wood').setValue(record.get('wood'));	
		this.getAddUpdateForm().getForm().findField('bubblefilm').setValue(record.get('bubblefilm'));
		this.getAddUpdateForm().getForm().findField('wrappingFilm').setValue(record.get('wrappingFilm'));
		this.getAddUpdateForm().getForm().findField('woodBox').setValue(record.get('woodBox'));
		this.getAddUpdateForm().getForm().findField('breakageRate').setValue(record.get('breakageRate'));
		this.getAddUpdateForm().getForm().findField('woodenFrameStartVolume').setValue(record.get('woodenFrameStartVolume'));
		this.getAddUpdateForm().getForm().findField('woodenFrameMin').setValue(record.get('woodenFrameMin'));
		this.getAddUpdateForm().getForm().findField('woodBoxStartVolume').setValue(record.get('woodBoxStartVolume'));
		this.getAddUpdateForm().getForm().findField('woodBoxMin').setValue(record.get('woodBoxMin'));
		this.getAddUpdateForm().getForm().findField('effectiveDate').setValue(record.get('effectiveDate'));
		this.getAddUpdateForm().getForm().findField('factoring').setValue(record.get('factoring'));
		
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([ cfg ]);
	}
});



// 定义一个表格列表
Ext.define('Foss.baseinfo.packagingSupplier.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_packagingSupplier_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.packagingSupplier.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,  
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.packagingSupplier.InfoPanel'
	} ],
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.packagingSupplier.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.packagingSupplier.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据

		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),baseinfo.packagingSupplier.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		Ext.Msg.confirm(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),baseinfo.packagingSupplier.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.packagingSupplier.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.packagingSupplier.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放虚拟编码的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('deletePackagingSupplier.action');
				var jsonData = {'vo':{'codeList':ids}};
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
                  me.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.packagingSupplier.i18n('foss.baseinfo.notice'),json.message);
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
				flex : 1,
				text : baseinfo.packagingSupplier.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
                    iconCls:'deppon_icons_edit',
					tooltip : baseinfo.packagingSupplier.i18n('foss.baseinfo.edit'),
					disabled:!baseinfo.packagingSupplier.isPermission('packagingSupplier/autoEditButton'),
					// 编辑事件
					handler : function(grid, rowIndex,colIndex) {
						//获取选中的数据
						var record = grid.getStore().getAt(rowIndex);
						//获得修改窗口
						var updateWindow = grid.up('grid').getUpdateWindow();
						var breakageRate = record.data.breakageRate*100;
						record.set('breakageRate',breakageRate);
						updateWindow.resetWindow(record,'update');
						updateWindow.show();
					}
				}]
	},{
				// 字段标题
				header : '外场名称',
				// 关联model中的字段名
				dataIndex : 'orgCode',
				flex : 1
			}, {
				// 字段标题
				header : '包装供应商代码',
				// 关联model中的字段名
				dataIndex : 'packagingSupplierCode',
				flex : 1
			}, {
				// 字段标题
				header : '包装供应商名字',
				// 关联model中的字段名
				dataIndex : 'packagingSupplier',
				flex : 1
			}, {
				// 字段标题
				header : '包装供应商电话',
				// 关联model中的字段名
				dataIndex : 'packagingSupplierPhone',
				flex : 1
			}, {
				// 字段标题
				header : '打木架单价/方',
				// 关联model中的字段名
				dataIndex : 'woodenFrame',
				flex : 1
			},{
				// 字段标题
				header : '打木托单价/个',
				// 关联model中的字段名
				dataIndex : 'woodPallet',
				flex : 1
//			},{
//				// 字段标题
//				header : '打包带单价/根',
//				// 关联model中的字段名
//				dataIndex : 'bagLine',
//				flex : 1
//			}, {
//				// 字段标题
//				header : '木条单价/米',
//				// 关联model中的字段名
//				dataIndex : 'wood',
//				flex : 1
//			}, {
//				// 字段标题
//				header : '气泡膜单价/方',//
//				// 关联model中的字段名
//				dataIndex : 'bubblefilm',
//				flex : 1
//			}, {
//				// 字段标题
//				header : '缠绕膜单价/方',//
//				// 关联model中的字段名
//				dataIndex : 'wrappingFilm',
//				flex : 1
//			}, {
//				// 字段标题
//				header : '打木箱单价/方',
//				// 关联model中的字段名
//				dataIndex : 'woodBox',
//				flex : 1
			},{
				// 字段标题
				header : '破损率参数',
				// 关联model中的字段名
				dataIndex : 'breakageRate',
				flex : 1
			}, {
				// 字段标题
				header : '打木架起步体积',
				// 关联model中的字段名
				dataIndex : 'woodenFrameStartVolume',
				flex : 1
			}, {
				// 字段标题
				header : '打木架最低一票',
				// 关联model中的字段名
				dataIndex : 'woodenFrameMin',
				flex : 1
			}, {
				// 字段标题
				header : '打木箱起步体积',//
				// 关联model中的字段名
				dataIndex : 'woodBoxStartVolume',
				flex : 1
			}, {
				// 字段标题
				header : '是否保理',//
				// 关联model中的字段名
				dataIndex : 'factoring',
				flex : 1,
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						if(value=='Y'){
							return '是';
						}
						if(value=='N'){
							return '否';
						}
					}else{
						return "";
					}
				}
			} ,{
				// 字段标题
				header : '打木箱最低一票',
				// 关联model中的字段名
				dataIndex : 'woodBoxMin',
				flex : 1
			},{
				// 字段标题
				header : '贷款客户编码',
				// 关联model中的字段名
				dataIndex : 'cusCode',
				flex : 1
			},{
				// 字段标题
				header : '保理回款账号',
				// 关联model中的字段名
				dataIndex : 'account',
				flex : 1
			},{
				// 字段标题
				header : '保理开始日期',
				// 关联model中的字段名
				dataIndex : 'factorBeginTime',
				flex : 1
			},{
				// 字段标题
				header : '保理结束日期',
				// 关联model中的字段名
				dataIndex : 'factorEndTime',
				flex : 1
			}
//			},{
//				// 字段标题
//				header : '有效时间',
//				// 关联model中的字段名
//				dataIndex : 'effectiveDate',
//				flex : 1
//			},{
//				// 字段标题
//				header : '创建人姓名',
//				// 关联model中的字段名
//				dataIndex : 'createUser',
//				flex : 1
//			}, {
//				// 字段标题
//				header : '创建时间',
//				// 关联model中的字段名
//				dataIndex : 'createDate',
//				flex : 1,
//				renderer:function(value){
//					if(!Ext.isEmpty(value)){
//						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
//					}else{
//						return null;
//					}
//				}
//			}, {
//				// 字段标题
//				header : '修改人姓名',//
//				// 关联model中的字段名
//				dataIndex : 'modifyUser',
//				flex : 1
//			,{
//				// 字段标题
//				header : '修改时间',//'操作时间'
//				// 关联model中的字段名
//				dataIndex : 'modifyDate',
//				flex : 1,
//				renderer:function(value){
//					if(!Ext.isEmpty(value)){
//						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
//					}else{
//						return null;
//					}
//				}
//			}
			],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.packagingSupplier.OriginatingLineStore');
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
						text : baseinfo.packagingSupplier.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.packagingSupplier.isPermission('packagingSupplier/exchangeAddButton'),
						hidden:!baseinfo.packagingSupplier.isPermission('packagingSupplier/exchangeAddButton'),
						width : 80,
						handler : function() {// 新增包装供应商信息
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.packagingSupplier.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.packagingSupplier.isPermission('packagingSupplier/exchangeVoidButton'),
						hidden:!baseinfo.packagingSupplier.isPermission('packagingSupplier/exchangeVoidButton'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							me.deleteInfos();
						}
					}]
		}], 
		me.callParent([ cfg ]);
	}
	});
//查看包装供应商详细信息
Ext.define('Foss.baseinfo.packagingSupplier.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.packagingSupplier.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取自动调度详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.packagingSupplier.DetailForm');
		}
		return this.infoForm;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [ this.getInfoForm()];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		me.getInfoForm().getForm().loadRecord(record);
	}
});
//详细包装供应商信息表单
Ext.define('Foss.baseinfo.packagingSupplier.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	height : 500,
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
			name: 'orgCode',
	        fieldLabel: '外场名称'
		},{
			name: 'packagingSupplierCode',
	        fieldLabel: '包装供应商代码'
		},{
			name: 'packagingSupplier',
	        fieldLabel: '包装供应商名字'
		},{
			name: 'packagingSupplierPhone',
	        fieldLabel: '包装供应商电话'
		},{
			name: 'woodenFrame',
	        fieldLabel: '打木架单价/方'
		},{
			name: 'woodPallet',
	        fieldLabel: '打木托单价/个'
		},{
			name: 'bagLine',
	        fieldLabel: '打包带单价/根'
		},{
			name: 'wood',
	        fieldLabel: '木条单价/米'
		},{
			name: 'bubblefilm',
	        fieldLabel: '气泡膜单价/方'
		},{
			name: 'wrappingFilm',
	        fieldLabel: '缠绕膜单价/方'
		},{
			name: 'woodBox',
	        fieldLabel: '打木箱单价/方'
		},{
			name: 'breakageRate',
	        fieldLabel: '破损率参数'
		},{
			name: 'woodenFrameStartVolume',
	        fieldLabel: '打木架起步体积'
		},{
			name: 'woodenFrameMin',
	        fieldLabel: '打木架最低一票'
		},{
			name: 'woodBoxStartVolume',
	        fieldLabel: '打木箱起步体积'
		},{
			name: 'woodBoxMin',
	        fieldLabel: '打木箱最低一票'
		},{
			name: 'effectiveDate',
	        fieldLabel: '有效时间'
		},{
			name: 'createUser',
	        fieldLabel: '创建人姓名'
		},{
			xtype:'datefield',
			name: 'createDate',
	        fieldLabel: '创建时间',
			format:'Y-m-d H:i:s'
		},{
			name: 'modifyUser',
	        fieldLabel: '修改人姓名'
		},{
			xtype:'datefield',
			name: 'modifyDate',
	        fieldLabel: '修改时间',
			format:'Y-m-d H:i:s'	
		},{
			xtype:'datefield',
			name: 'factorBeginTime',
	        fieldLabel: '保理开始时间',
			format:'Y-m-d H:i:s'	
		},{
			xtype:'datefield',
			name: 'factorEndTime',
	        fieldLabel: '保理结束时间',
			format:'Y-m-d H:i:s'	
		},{
			name: 'cusCode',
	        fieldLabel: '贷款客户编码'
		},{
			name: 'account',
	        fieldLabel: '保理汇款账号'
		},{
			name: 'factoringDisplay',
	        fieldLabel: '是否保理'/*,
	        renderer:function(value){
				if(!Ext.isEmpty(value)){
					if(value=='Y'){
						return '是';
					}
					if(value=='N'){
						return '否';
					}
				}else{
					return "";
				}
			}*/
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.packagingSupplier.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.packagingSupplier.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-packagingSupplier').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-packagingSupplier_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getOriginatingLineGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
	}));
});