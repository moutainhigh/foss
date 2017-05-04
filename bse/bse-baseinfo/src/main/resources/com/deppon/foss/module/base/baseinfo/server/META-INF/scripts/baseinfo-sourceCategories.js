//登陆用户员工号
baseinfo.sourceCategories.currentEmpCode = FossUserContext.getCurrentUserEmp().empCode;
//登录员工名
baseinfo.sourceCategories.currentEmpName = FossUserContext.getCurrentUserEmp().empName;
//数据字典
baseinfo.sourceCategories.dictionary = 'BSE_SOURCE_CATEGORY';
//上传文件提示大小
baseinfo.sourceCategories.errMsg = "上传的附件文件不能超过2M！！！";
//浏览器兼容提示
baseinfo.sourceCategories.tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用Chrome浏览器。";

baseinfo.sourceCategories.checkFile = function(){
	var maxsize = 2*1024*1024;//2M  
    try{  
        var obj_file = document.getElementsByName("sourceCategoriesVo.uploadFile");
        var filesize = 0;
        filesize = obj_file[obj_file.length-1].files[0].size;  
        if(filesize==-1){  
        	baseinfo.showInfoMes(baseinfo.sourceCategories.tipMsg);  
            return true;  
        }else if(filesize>maxsize){  
        	baseinfo.showInfoMes(baseinfo.sourceCategories.errMsg);  
            return false;  
        }else{ 
            return true;  
        }  
    }catch(e){ 
    }  
}

baseinfo.getStartDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};
baseinfo.getEndDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

/**
 * 行业货源品类基础资料实体
 */
Ext.define('Foss.baseinfo.sourceCategories.SourceCategoriesEntity',{
	extend : 'Ext.data.Model',
	fields :[{name:'id',type:'string'},
	         {name:'category',type:'string'},
	         {name:'name',type:"string"},
	         {name:'createUser',type:"string"},
	         {name:'createDate',
	             type:'date',convert: dateConvert,defaultValue:null
	         },
	         {name:'modifyUser',type:"string"},
	         {name:'modifyDate',
	             type:'date',convert: dateConvert,defaultValue:null
	         },
	         {name:'active',type:"string"}]
});

/**
 * 行业货源品类基础资料查询实体
 */
Ext.define('Foss.baseinfo.sourceCategories.Condition',{
	extend : 'Ext.data.Model',
	fields:[{name:'category',type:'string'},
	        {name:'name',type:"string"},
	        {name:'startTime',type:'date',convert: dateConvert},
	        {name:'endTime',type:'date',convert: dateConvert}]
});

/**
 * 行业货源品类Store
 */
Ext.define('Foss.baseinfo.sourceCategories.SourceCategoriesStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.sourceCategories.SourceCategoriesEntity',
	pageSize:50,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('querySourceCategories.action'),
		reader : {
			type : 'json',
			root : 'sourceCategoriesVo.sourceCategoriesDtos',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 行业货源品类基础资料窗口
 */
Ext.define('Foss.baseinfo.SourceCategoriesWin',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	layout : 'auto',
	resizable:false,
	closeAction : 'hide',
	width :400,
	height :250,	
	editForm:null,
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.AddOrUpdateSourceCategoriesForm');
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.sourceCategories.i18n('foss.baseinfo.cancel'),
					handler : function() {
						me.hide();
					}
				},{
					text : baseinfo.sourceCategories.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						var form = me.editForm.getForm();
						if (form.isValid()) {
							var sourceCategories = Ext.create('Foss.baseinfo.sourceCategories.SourceCategoriesEntity');
							sourceCategories.set('name',form.findField('name').getValue());
							//设置id,如果是新增id为空,如果是修改设置id,后天根据id进行修改
							sourceCategories.set('id',form.findField('id').getValue());
							var ajaxUrl = '';
							if(me.title=='新增'){
								sourceCategories.set('category',form.findField('category').getValue());
								sourceCategories.set('createUser',baseinfo.sourceCategories.currentEmpCode);
								ajaxUrl = baseinfo.realPath('addSourceCategories.action');
							}else if(me.title=='修改'){
								sourceCategories.set('category',FossDataDictionary.
										rendererDisplayToSubmit(form.findField('category').getValue(),baseinfo.sourceCategories.dictionary));
								//设置修改人
								sourceCategories.set('modifyUser',baseinfo.sourceCategories.currentEmpCode);
								ajaxUrl = baseinfo.realPath('updateSourceCategorie.action');
							}
							sourceCategories = sourceCategories.data;
							Ext.Ajax.request({
								url:ajaxUrl,
								jsonData:{'sourceCategoriesVo':{'sourceCategoriesEntity':sourceCategories}},
					    		timeout:600000,
					    		success:function(response){
					    			me.close();
					    			if(me.title=='新增'){
					    				var grid = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid();
					    				grid.getPagingToolbar().moveFirst();
					    				baseinfo.showInfoMes('添加成功!选择当前时间点击查询即可显示');
					    			}else if(me.title=='修改'){
					    				var result = Ext.decode(response.responseText);
					    				var grid = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid();
					    				var selection=grid.getSelectionModel().getSelection();
					    				selection[0].set('category',form.findField('category').getValue());
					    				selection[0].set('name',form.findField('name').getValue());
					    				selection[0].set('modifyUser',baseinfo.sourceCategories.currentEmpName);
					    				selection[0].set('modifyDate',result.sourceCategoriesVo.sourceCategoriesEntity.modifyDate);
					    				//因为品名是不重复的,如果有重复的后天会把重复的全部删除(即无效),前台根据品名去修改该条数据无效
					    				var index = grid.store.indexOf(selection[0]);
					    				var store = grid.store;
					    				var count = store.getCount();
					    				for(var i=0;i<count&&i!=index;i++){
					    					if(store.getAt(i).get('name')==selection[0].get('name')){
					    						store.getAt(i).set('active','无效');
					    					}
					    				}
					    				baseinfo.showInfoMes('修改成功!');
					    			}
					    		},
					    		exception:function(response){
					    			baseinfo.showInfoMes('系统异常!请稍后重试');
					    		}
							});
			            }else{
			            	baseinfo.showInfoMes('请根据提示填写完整！！！');
							return;
			            }
					}
				}];
	}
});

/**
 * 货源品类基础资料表单
 */


Ext.define('Foss.baseinfo.AddOrUpdateSourceCategoriesForm',{
	extend:'Ext.form.Panel',
	title:'行业货源类别基础资料',
	height : '200',
	width:'350',
	frame:true,
	defaultType : 'textfield',
	defaults:{
		margin : '5 5 5 0',
		allowBlank:false,
		labelWidth:85
	},
	layout:{
		type : 'table',
		columns : 2
    },
    formRecord:null,
    items:[{
    	name:'id',
    	hidden:true,
    	allowBlank:true
    },{
    	xtype: 'combobox',
		name:'category',
		editable:false,
		displayField:'text',
		valueField:'value',
		colspan : 2,
		width:330,
		margin:'5 0 0 0',
		displayField:'valueName',
		valueField:'valueCode',
		store:FossDataDictionary.getDataDictionaryStore(baseinfo.sourceCategories.dictionary),
		allowBlack:false,
		colspan : 2,
		fieldLabel : '行业货源品类',  //品类
		maxLength:50
    },{
		name:'name',
		columnWidth: 0.275,
		colspan : 2,
		width:330,
		margin:'5 0 0 0',
		fieldLabel:'品名',//品名
		xtype :'textfield',
		maxLength:50
	}]
});

/**
 * 上传窗体
 */
Ext.define('Foss.baseinfo.AddExcelWin',{
	extend : 'Ext.window.Window',
	closable : true,
	title : '上传',
	modal : true,
	layout : 'auto',
	layout:{
		type:'vbox',
		align:'stretch'
	},
	resizable:false,
	closeAction : 'hide',
	width :350,
	height :200,
	parent:null,
	listeners:{
		beforehide:function(me){
			var form = me.down('form');
			form.getForm().findField('sourceCategoriesVo.uploadFile').reset();
		}
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [
		    		{
		    			xtype:'form',
		    			flex:1,
		    			layout:{
		    				type : 'hbox'
		    			},
		    			defaults : {
		    				margins : '0 5 0 0'
		    			},
		    			items:[{
		    				xtype:'filefield',
		    				name:'sourceCategoriesVo.uploadFile',
		    				fieldLabel:baseinfo.sourceCategories.i18n('foss.baseinfo.pleaseSelectAttachments'),
		    				labelWidth:80,
		    				buttonText:baseinfo.sourceCategories.i18n('foss.baseinfo.browse'),
		    				flex:1
		    			}]
		    		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.sourceCategories.i18n('foss.baseinfo.cancel'),
					handler : function() {
						me.hide();
					}
				},{
					text : baseinfo.sourceCategories.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						var form = me.down('form').getForm();
						var filePath = form.findField('sourceCategoriesVo.uploadFile').getValue();
						if(Ext.isEmpty(filePath)){
							baseinfo.showInfoMes('请选择文件....');
							return;
						}
						var arr = filePath.split('.');
						if(arr[arr.length-1]!='xls'){
							baseinfo.showInfoMes('请使用模板,上传.xls格式的文件....');
							return;
						}
						if(!baseinfo.sourceCategories.checkFile()){
							return;
						}
						var url = baseinfo.realPath('importSourceCategory.action');
						var successFn = function(json){						
							me.close();
							if(Ext.isEmpty(json.sourceCategoriesVo.numList)){
								baseinfo.showInfoMes(baseinfo.sourceCategories.i18n('foss.baseinfo.allDataImportSuccess'));//全部数据导入成功！
							}else{
								var message = baseinfo.sourceCategories.i18n('foss.baseinfo.di')+json.sourceCategoriesVo.numList[0];//第
								for(var i = 1;i<json.sourceCategoriesVo.numList.length;i++){
									message = message + "," +json.sourceCategoriesVo.numList[i];
								}
								message = message+baseinfo.sourceCategories.i18n('foss.baseinfo.lineNoImport');
								baseinfo.showWoringMessage(message);
							}
							Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid().getPagingToolbar().moveFirst();
						};
						var failureFn = function(json){
							me.close();
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.sourceCategories.i18n('foss.baseinfo.formatError'));//baseinfo.sourceCategories.i18n('foss.baseinfo.requestOvertime')
							}else{
								baseinfo.showErrorMes(json.message);
							}
						};
						form.submit({
				            url: url,
				            waitMsg: baseinfo.sourceCategories.i18n('foss.baseinfo.uploadYourAttachments'),
				            success:function(form, action){
				    			var result = action.result;
				    			if(result.success){
				    				successFn(result);
				    			}else{
				    				failureFn(result);
				    			}
				    		},
				    		failure:function(form, action){
				    			var result = action.result;
				    			failureFn(result);
				    		},
				    		exception:function(response){
				    			var result = Ext.decode(response.responseText);
				    			failureFn(result);
				    		}
				        });
					}
				}];
	}
});

Ext.define('Foss.baseinfo.QueryActive',{
	extend:'Ext.data.Store',
	fields:['text','value'],
	data:[{'text':'有效',
		   'value':'Y'},
	      {'text':'无效',
		   'value':'N'}]
});

Ext.define("Foss.baseinfo.QuerySourceCategoriesForm",{
	extend : 'Ext.form.Panel',
	title:'行业货源类别基础资料查询',
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
        columns: 3
    },
    record:null,
    items:[{
    	xtype: 'combobox',
		name:'category',
		displayField:'text',
		editable:false,
		valueField:'value',
		margin:'5 0 0 0',
		displayField:'valueName',
		valueField:'valueCode',
		store:null,
		allowBlack:false,
		fieldLabel : '行业货源品类',  //品类
		maxLength:50
    },{
		name:'name',
		columnWidth: 0.275,
		fieldLabel:'品名',//品名
		xtype :'textfield'
	},{
		xtype: 'rangeDateField',
		fieldLabel: '导入时间',//导入时间,
		dateType: 'datetimefield_date97',
		fromName: 'startTime',
		toName: 'endTime',
		width:460,
		disallowBlank: true,
		editable:false,
		fromValue: Ext.Date.format(baseinfo.getStartDate(new Date(),-10),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: 0.30
	},{
		xtype: 'combobox',
		name:'active',
		displayField:'text',
		margin:'5 0 0 0',
		valueField:'value',
		fieldLabel:'状态',
		editable:false,
		colspan:3,
		maxLength:50
	},{
		xtype : 'container',
		colspan:3,
		defaultType:'button',
		layout:'column',
//		hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptQueryButton'),
		items : [{
			width: 75,
			text : baseinfo.sourceCategories.i18n('foss.baseinfo.reset'),
			handler : function() {
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('category').value='';
				this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()-10, 0, 0, 0),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 59, 59, 59),'Y-m-d H:i:s'));
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.99
		},{
			xtype:'button',
			width: 75,       
			text :  baseinfo.sourceCategories.i18n('foss.baseinfo.query'),
			cls:'yellow_button',
//			hidden:!baseinfo.ldpAgencyDeptIndex.isPermission('ldpAgencyDeptIndex/ldpAgencyDeptQueryButton'),
			handler : function() {
				var queryForm = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryForm().getForm();//得到查询的FORM表单
				var startTime = queryForm.findField('startTime').getValue();
				var endTime = queryForm.findField('endTime').getValue();
				if(Date.parse(endTime)-Date.parse(startTime)>60*60*24*30*1000){
					baseinfo.showInfoMes("查询时间跨度不超过30天");
					return;
				}
				var grid = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid();
				grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
			}
		}]
	}],
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items[0].store=FossDataDictionary.getDataDictionaryStore(baseinfo.sourceCategories.dictionary)
		me.items[3].store=Ext.create('Foss.baseinfo.QueryActive');
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.sourceCategories.SourceCategoriesEntity'):me.record);
	}
});

/**
 * 货源类别列表grid
 */
Ext.define('Foss.baseinfo.QuerySourceCategoriesGrid',{
	extend: 'Ext.grid.Panel',
	title : '行业货源类别基础资料查询显示',
	id:'Foss_baseinfo_QuerySourceCategoriesGrid_id',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 
	frame: true,
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 50,
				prependButtons : false,
					defaults : {
						margin : '0 0 25 3'
					}
			});
		}
		return this.pagingToolbar;
	},getSourceCategoriesWin:function(title){
		var win = Ext.create('Foss.baseinfo.SourceCategoriesWin',{
			'title':title
		});
		return win;
	},addSourceCategoriesWin:function(){
		return this.getSourceCategoriesWin('新增');
	},updateSourceCategoriesWin:function(){
		return this.getSourceCategoriesWin('修改');
	},addExcelWin:function(){
		var win = Ext.create('Foss.baseinfo.AddExcelWin');
		return win;
	},getTbar:function(){
		var me = this;
		return [{
			//新增
			text : '新增',
			handler :function(){
				var win = me.addSourceCategoriesWin();
				win.show();
			} 
		},'-', {
			//修改
			text : '修改',
			handler :function(){
				var grid = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid();
				selection=grid.getSelectionModel().getSelection();
				if(selection.length!=1){
					baseinfo.showInfoMes('请选择一行数据！');
					return ;
				}
				if(selection[0].get('active')=='无效'){
					baseinfo.showInfoMes('无效的数据不能修改！');
					return ;
				}
				var win = me.updateSourceCategoriesWin();
				win.editForm.getForm().findField('category').value=selection[0].get('category');
				win.editForm.getForm().findField('category').rawValue=
					FossDataDictionary.rendererSubmitToDisplay(selection[0].get('category'),baseinfo.sourceCategories.dictionary);
				win.editForm.getForm().findField('id').setValue(selection[0].get('id'));
				win.editForm.getForm().findField('name').setValue(selection[0].get('name'));
				win.show();
			} 
		},'-', {
			//删除
			text : '删除',
			handler :function(){
				var grid = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryGrid();
				selection=grid.getSelectionModel().getSelection();
				if(selection.length==0){
					baseinfo.showInfoMes('请选择至少一行数据进行删除！');
					return ;
				}
				var sourceCategoriesIds = [];
				for(var i=0;i<selection.length;i++){
					if(selection[i].get('active')=='有效'){
						sourceCategoriesIds.push(selection[i].get('id'));
					}
				}
				Ext.MessageBox.confirm('FOSS提醒您','您确定要删除这些么?',function(id){
					if(id=='yes'){
						if(sourceCategoriesIds.length==0){
							baseinfo.showInfoMes('删除成功！');
							return;
						}
						Ext.Ajax.request({
							url:baseinfo.realPath("deleteSourceCategories.action"),
							jsonData:{'sourceCategoriesVo':{'sourceCategoriesIds':sourceCategoriesIds}},
				    		timeout:600000,
				    		success:function(response){
				    			for(var i=0;i<selection.length;i++){
									selection[i].set('active','无效');
								}
				    			baseinfo.showInfoMes('删除成功！');
								return ;
				    		},
				    		exception:function(response){
				    			baseinfo.showInfoMes('系统异常!请稍后重试');
				    		}
						});
					}
				});
			} 
		},'-', {
			//导入
			text : '导入',
			handler :function(){
				var win = me.addExcelWin();
				win.show();
			} 
		},'-', {
			//导出
			text : '导出',
			handler :function(){
				var store = me.store;
				if(store.getCount()<1){
					baseinfo.showInfoMes('没有数据!无法导出');
					return;
				}
				Ext.MessageBox.confirm('FOSS提醒您','是否导出行业货源基础信息?',function(id){
					if(id=='yes'){
						var queryForm = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryForm().getForm();//得到查询的FORM表单
						var startTime = queryForm.findField('startTime').getValue();
						var endTime = queryForm.findField('endTime').getValue();
						var category = queryForm.findField('category').getValue();
						var name = queryForm.findField('name').getValue();
						var active = queryForm.findField('active').getValue();
						var param = "sourceCategoriesVo.template=false";
						if(!Ext.isEmpty(category)){
							param += "&sourceCategoriesVo.condition.category="+category;
						}
						if(!Ext.isEmpty(name)){
							param += "&sourceCategoriesVo.condition.name="+name;
						}
						param += "&sourceCategoriesVo.condition.startTime="+startTime;
						param += "&sourceCategoriesVo.condition.endTime="+endTime;
						param += "&sourceCategoriesVo.condition.active="+active;
						window.location=baseinfo.realPath("exportTemplateCategor.action?"+param);
					}
				});
			} 
		},'-', {
			//模板
			text : '模板',
			handler :function(){
				var param = "sourceCategoriesVo.template=true";
				window.location=baseinfo.realPath("exportTemplateCategor.action?"+param);
			} 
		}];
	},
	getColumns:function(){
		var me = this;
		return [{
			text:'Id',
			dataIndex :'id',
			hidden:true
		},{
			text : '行业货源品类',
			dataIndex : 'category',
			flex : 1,
			renderer:function(value,meta,record,rowIndex,celIndex){
				return FossDataDictionary.rendererSubmitToDisplay(value,baseinfo.sourceCategories.dictionary);
			},
		},{
			text:'品名',
			dataIndex : 'name',
			flex : 1.2
		},{
			text:'创建人',
			dataIndex : 'createUser',
			flex : 0.75
		},{
			text:'创建时间',
			dataIndex : 'createDate',
			renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
			flex : 0.75
		},{
			text:'修改人',
			dataIndex : 'modifyUser',
			flex : 0.75
		},{
			text:'修改时间',
			dataIndex : 'modifyDate',
			renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
			flex : 0.75
		},{
			text:'状态',
			dataIndex : 'active',
			flex : 0.75
		}];
	},getStore:function(){
		return Ext.create('Foss.baseinfo.sourceCategories.SourceCategoriesStore',{
			autoLoad : false,
			pageSize : 50,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-sourceCategories_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var outerBranchEntity = queryForm.record.getData();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//行业货源类别
								'sourceCategoriesVo.condition.category':queryForm.findField('category').getValue(),
								'sourceCategoriesVo.condition.name':queryForm.findField('name').getValue(),
								'sourceCategoriesVo.condition.startTime':queryForm.findField('startTime').getValue(),
								'sourceCategoriesVo.condition.endTime':queryForm.findField('endTime').getValue(),
								'sourceCategoriesVo.condition.active':queryForm.findField('active').getValue()
							}
						});	
					}
				}
		    }
		});
	},constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = me.getStore();
		me.columns = me.getColumns();
		//添加头部按钮
		me.tbar = me.getTbar();
		//添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-sourceCategories_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.QuerySourceCategoriesForm',{'record':Ext.create('Foss.baseinfo.sourceCategories.SourceCategoriesEntity')});
	var queryGrid  = Ext.create('Foss.baseinfo.QuerySourceCategoriesGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-sourceCategories').add(Ext.create('Ext.panel.Panel', {
		id:'T_baseinfo-sourceCategories_content',
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