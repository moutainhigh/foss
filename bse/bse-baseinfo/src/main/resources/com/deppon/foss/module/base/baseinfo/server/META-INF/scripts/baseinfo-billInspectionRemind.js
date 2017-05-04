
var changeLongToDate = function(value) {
	    if (value != null) {
	        var date = new Date(value);
	        return Ext.Date.format(date, "Y-m-d H:i:s");
//	        return date.toLocaleDateString();
	    } else {
	        return null;
	    }
	};

Ext.define('Foss.baseinfo.billInspectionRemind.BillInspectionRemindModel', {
extend: 'Ext.data.Model',
fields : [{name:'id',type:'string'}, 
	  {name:'regionType',type:'string'}, 
	  {name:'regionCode',type:'string'}, 
	  {name:'regionName',type:'string'},  
	  {name:'regionLev',type:'string'}, 
	  {name:'regionLevCode',type:'string'}, 
	  {name:'provinceName',type:'string'}, 
	  {name:'provinceCode',type:'string'}, 
	  {name:'cityName',type:'string'},
	  {name:'cityCode',type:'string'},
	  {name:'countyName',type:'string'}, 
	  {name:'countyCode',type:'string'},
	  {name:'active',type:'string'},
	  {name:'versionNo',type:'string'},
	  {
			name :'createDate',
		   	type: 'date',
		   	format : 'Y-m-d h:i:s',
		   	defaultValue: null,
		    convert: changeLongToDate
	 },
	  {name:'createUser',type:'string'},
	  {name:'modifyDate',type:'string'},
	  {name:'modifyUser',type:'string'},
	  {name:'createUserName',type:'string'}]
});
//------------------------------------STORE----------------------------------
//  STORE
Ext.define('Foss.baseinfo.billInspectionRemind.BillInspectionRemindStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.billInspectionRemind.BillInspectionRemindModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBillInspectionRemind.action'),
		reader : {
			type : 'json',
			root : 'objectVo.billInspectionRemindEntitys',
			totalProperty : 'totalCount'
		}
	}
});

//删除
baseinfo.billInspectionRemind.deletebillInspectionRemind=function(type,recode){
	var grid  = Ext.getCmp('T_baseinfo-billInspectionRemind_content').getQueryResultGrid();//得到grid
	selection=grid.getSelectionModel().getSelection();
	var objectVo=new Object();
	var ids = [];
	if(type==null&&recode==null){
		if(selection.length<=0){
			Ext.MessageBox.alert('提示','请选择你要删除的数据！');
			return;
		}
		var id = '';
		for (var j = 0; j < selection.length; j++) {
			ids.push(selection[j].get('id'));
		}
	}else{
		ids.push(recode.data.id);
	}
	objectVo.ids=ids;
	
	
	Ext.MessageBox.buttonText.yes ='确定';
	Ext.MessageBox.buttonText.no ='取消';
	Ext.Msg.confirm('提示',baseinfo.billInspectionRemind.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url:baseinfo.realPath('deleteBillInspectionRemind.action'),
				jsonData:{'objectVo':objectVo},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);
					var grid  = Ext.getCmp('T_baseinfo-billInspectionRemind_content').getQueryResultGrid();//得到grid
					grid.store.loadPage(1);//用分页的moveFirst()方法
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					baseinfo.showWoringMessage(result.message);
				}
			});
		}
	});
}



//ResultGrid
Ext.define('Foss.baseinfo.billInspectionRemind.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title :'显示列表',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText:'数据为空',							//查询结果为空
	queryForm:null,
	frame: true,
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		//添加头部按钮
		me.tbar = me.getTbar(config);
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
 		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text :'新增',
			disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindAddButton'),   //按钮权限
			hidden:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindAddButton'),   //按钮权限
			handler :function(){
				 me.getBillInspectionRemindAddWindow({}).show();
			} 
		},'-', {
			text :'删除',
			disabled:!baseinfo.billInspectionRemind.isPermission('expressDeliverySmallZone/billInspectionRemindDisableButton'),   //按钮权限
			hidden:!baseinfo.billInspectionRemind.isPermission('expressDeliverySmallZone/billInspectionRemindDisableButton'),   //按钮权限
			handler :function(){
				baseinfo.billInspectionRemind.deletebillInspectionRemind(null,null);
			} 
		}];
	},
	getAgencyDeptWin:function(win,title,viewState,param,id){
		var formRecord = Ext.isEmpty(param)?Ext.create('Foss.baseinfo.billInspectionRemind.BillInspectionRemindModel'):param;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.billInspectionRemind.BillInspectionRemindEditEditWindow',{
				'title':title,
				'model':formRecord,
				'type':viewState,
				'id':id
			});
		}
		return win;
	},
	billInspectionRemindAddWindow:null,
	getBillInspectionRemindAddWindow:function(param){
		var addForm=Ext.getCmp('T_BillInspectionRemind_ADD');
		if(null!=addForm){
			addForm.getBillInspectionRemindEditFrom().getForm().reset();
			return addForm;
		}
		return this.getAgencyDeptWin(this.billInspectionRemindAddWindow,'新增','add',param,'T_BillInspectionRemind_ADD');
	},
	billInspectionRemindUpdateWindow:null,
	getBillInspectionRemindUpdateWindow:function(param){
		
		return this.getAgencyDeptWin(this.billInspectionRemindUpdateWindow,'更新','update',param,null);
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
		return this.pagingToolbar;
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.billInspectionRemind.BillInspectionRemindStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-billInspectionRemind_content').getQueryForm().getForm();//得到查询的FORM表单
					var entity=queryForm.getValues();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
//								
								'objectVo.billInspectionRemindEntity.regionCode':entity.regionCode,
//								 
								'objectVo.billInspectionRemindEntity.regionName':entity.regionName,
//								 
								'objectVo.billInspectionRemindEntity.regionLevCode':entity.regionLevCode
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(config){
		var me = this;
		return [new Ext.grid.RowNumberer({
				  header : "序号",
				  width : 40,
				  renderer:function(value,metadata,record,rowIndex){
				   return 0 + 1 + rowIndex;
				  }
				 }),{
			align : 'center',
			xtype : 'actioncolumn',
			text :' 操作',//操作
			items: [{
				iconCls : 'deppon_icons_edit',
				tooltip :'修改', //修改
				width : 42,
				disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindEditButton'),
                disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindEditButton'),
				handler : function (grid, rowIndex, colIndex) {
    				var formRecord=grid.getStore().getAt(rowIndex);
    				var win= me.getBillInspectionRemindUpdateWindow(formRecord);
					var billInspectionRemind=win.getBillInspectionRemindEditFrom();
    				win.show();
				}
			}, {
				iconCls : 'deppon_icons_cancel',
				width : 42,
                disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindDisableButton'),
				handler : function (grid, rowIndex, colIndex) {
					baseinfo.billInspectionRemind.deletebillInspectionRemind("single",grid.getStore().getAt(rowIndex));
				}
			}]
		},{
			text :'区域类型',									
			dataIndex : 'regionCode',
			flex:1,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'BILL_INSPECTION_REGION');
			}
		},{
			text :'区域级别',
			flex:1,
			dataIndex : 'regionLevCode',
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'BILL_DISTRICT_DEGREE');
			}
		},{
			text :'区域名称',	
			flex:1,
			dataIndex : 'regionName'
		},{
			text :'最后创建人',
			flex:1,
			dataIndex : 'createUserName'
		},{
		 
			text :'最后创建时间',
			flex:1,
			dataIndex : 'createDate',
		}];
	}
});




baseinfo.billInspectionRemind.initWin=function(editForm,type,formRecord){
		var form=editForm.query("form[pname=provinceCityAreaNameContainer]")[0];
		var province=form.query('commonprovinceselector')[0];
		var city=form.query('commonCityByProvinceselector')[0];
		var county=form.query('commonAreaByCityselector')[0];
		var items=form.items.items;
		if(type=='add'){
			if(null!=city){
				form.remove(city);
			}
			if(null!=county){
			form.remove(county); 
			}
		}else if (type=='update'){
		
					var newValue=formRecord.data.regionLevCode;
					editForm.getForm().loadRecord(formRecord);
					if(newValue=='DISTRICT_PROVINCE'){
						 province.setCombValue(formRecord.get('provinceName'),formRecord.get('provinceCode'));	
					 }else if(newValue=='DISTRICT_CITY'){
						var city=form.query('commonCityByProvinceselector')[0];
						 city.parentId=formRecord.data.provinceCode;
						// 公共组件 
						 province.setCombValue(formRecord.get('provinceName'),formRecord.get('provinceCode'));
						 city.setCombValue(formRecord.get('cityName'),formRecord.get('cityCode'));
						
					 }else if(newValue=='DISTRICT_COUNTY'){
						var city=form.query('commonCityByProvinceselector')[0];
						var county=form.query('commonAreaByCityselector')[0];
						 city.parentId=formRecord.data.provinceCode;
						 county.parentId=formRecord.data.cityCode;
						// 公共组件 
						 province.setCombValue(formRecord.get('provinceName'),formRecord.get('provinceCode'));
						 city.setCombValue(formRecord.get('cityName'),formRecord.get('cityCode'));
						 county.setCombValue(formRecord.get('countyName'),formRecord.get('countyCode')); 
					 }
		
		}

}

//编辑WINDOW
Ext.define('Foss.baseinfo.billInspectionRemind.BillInspectionRemindEditEditWindow',{
 extend:'Ext.window.Window',
 title:null,
 type:null,
 model:null,
 width:600,
 closeAction:'hide',
 modal:true,
 billInspectionRemindEditFrom:null,
 getBillInspectionRemindEditFrom:function(){
  if(this.billInspectionRemindEditFrom==null){
    this.billInspectionRemindEditFrom=Ext.create('Foss.baseinfo.billInspectionRemind.BillInspectionRemindEditFrom');
  }
  return this.billInspectionRemindEditFrom;
 },
 constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.fbar = me.getFbar(config);
		me.items =[me.getBillInspectionRemindEditFrom()];
		me.listeners=me.getMyListeners(config);
		me.callParent([cfg]);
	},
getMyListeners:function(){
		var me = this;
		return {
		beforeshow:function(eOpts){
			var editForm= me.getBillInspectionRemindEditFrom();
			baseinfo.billInspectionRemind.initWin(editForm,me.type,me.model);
			},
		hide:function(){
			
		}	
			};
},
 getFbar:function(config){
		var me = this;
		
	return [{
			text : '返回',
			handler :function(){ 
				me.hide();	
			}
		},{
			text : '提交',
			handler :function(){
				var editForm=me.getBillInspectionRemindEditFrom().getForm();
				if(!editForm.isValid()){
					baseinfo.showInfoMsg('请完善所填写的信息！');
					return;
				}
				var addValues=editForm.getValues();
				baseinfo.billInspectionRemind.DataPermissions(me,addValues,me.type,me.model);
			}
		}];	
}		
})

//编辑FORM
Ext.define('Foss.baseinfo.billInspectionRemind.BillInspectionRemindEditFrom',{
  extend:'Ext.form.Panel',
  defaultType : 'textfield',
  layout:{
			type: 'table',
			columns: 1
		},
   constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
	  var me = this;
	  return [{xtype : 'container',
				margin:'15 0 0 0',
				layout:{
					type: 'table',
					columns: 3
				},
				items:[FossDataDictionary.getDataDictionaryCombo('BILL_INSPECTION_REGION',{
				fieldLabel:'区域类型',	
				labelWidth:60,
				allowBlank:false,
				name: 'regionCode', 
			}),{
				xtype:'container',
				html:'&nbsp;',
				width:20
			},FossDataDictionary.getDataDictionaryCombo('BILL_DISTRICT_DEGREE',{
				fieldLabel:'区域级别',
				labelWidth:60,
				allowBlank:false,				
				name: 'regionLevCode',
				listeners:{
					change:function(item,newValue){	
					 var form=item.up().up();
					 var resetform=form.query('form')[0].getForm().reset();
					
					 var form=form.query("form[pname='provinceCityAreaNameContainer']")[0];
					 var province=form.getForm().findField('provinceCode');
					 var city=form.getForm().findField('cityCode');
					 var county=form.getForm().findField('countyCode');
					 var items=form.items.items; 
					 if(newValue=='DISTRICT_PROVINCE'){
						if(null!=city){
						  form.remove(city);
						}
						if(null!=county){
						  form.remove(county);
						}
						
						  						
					 }else if(newValue=='DISTRICT_CITY'){
						  if(city==null){
							form.add(cityObj);	
						  }
						  if(null!=county){
						   form.remove(county);
						  }

					 }else if(newValue=='DISTRICT_COUNTY'){
						 if(city==null){
							form.add(cityObj);	
						  }
						if(county==null){
							form.add(countyObj);	
						  }						  
					 } 
					}
					}
			
			})]},
			{   xtype : 'form',
				margin:'15 0 0 0',
				pname:'provinceCityAreaNameContainer',
				layout:{
					type: 'table',
					columns: 4
				},
				items:[{
							xtype: 'label',
							text: '区域:',
							margin: '0 8 0 30'
						},{ 
						xtype : 'commonprovinceselector',
						name:'provinceCode',
						labelWidth:60,
						listeners:{
							select:function(newValue,oldValue,eOpts){
								var model=this.up().up().getForm().findField('regionLevCode').getValue();
								if(null==model){
								  this.up().up().getForm().reset();
								  Ext.MessageBox.alert('提示','区域级别不能为空！');
								  return; 
								}

								var form=this.up();
								var city= this.up().query('commonCityByProvinceselector')[0];
								if(null!=city){
									city.parentId=oldValue[0].data.code;
								}
								
							}
						}
						}]
				}
			];
		}
})


//queryForm
Ext.define('Foss.baseinfo.billInspectionRemind.BillInspectionRemindQueryForm', {
	extend : 'Ext.form.Panel',
	title:'查询条件',
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
	record:null,
    constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
		var me = this;
		return [
				{   name:'regionCode',
					columnWidth : 0.35,
					labelWidth : 70,
					fieldLabel:'区域类型', //派送属性
					xtype:'combobox',
					valueField:'valueCode',
					displayField:'valueName',
					queryMode:'local',
					triggerAction:'all',
					value:'',
					store:FossDataDictionary.getDataDictionaryStore('BILL_INSPECTION_REGION',null,{
						'valueCode':'',
						'valueName':'全部'
					})
				},
				{   name:'regionLevCode',
					columnWidth : 0.35,
					labelWidth : 70,
					fieldLabel:'区域级别', //派送属性
					xtype:'combobox',
					valueField:'valueCode',
					displayField:'valueName',
					queryMode:'local',
					triggerAction:'all',
					value:'',
					store:FossDataDictionary.getDataDictionaryStore('BILL_DISTRICT_DEGREE',null,{
						'valueCode':'',
						'valueName':'全部'
					})
				},{
			fieldLabel:'区域名称',
			name:'regionName',
		},{
			xtype : 'container',
			colspan:3,
			margin:'15 0 0 0',
			defaultType:'button',
			layout:{
				type: 'table',
				columns: 3
			},
			items : [{
				width: 75,
				text :'重置',
				disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindQueryButton'),//按钮权限
				hidden:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindQueryButton'),//按钮权限
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				width:600 
			},
			{
				width: 75,
				text :'查询',
				disabled:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindQueryButton'),//按钮权限
				hidden:!baseinfo.billInspectionRemind.isPermission('billInspectionRemind/billInspectionRemindQueryButton'),//按钮权限
				cls:'yellow_button',
				handler : function() {
				var grid = Ext.getCmp('T_baseinfo-billInspectionRemind_content').getQueryResultGrid();
					grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				}
			}]}
			];
	}
});
/**
 * 更新提交验证
 */
baseinfo.billInspectionRemind.updateSubmitValidate=function(entity,recode){
	
	if(entity.regionCode!=recode.data.regionCode){
		
		return false;
	}
	
	if(recode.data.regionLevCode!=entity.regionLevCode){
		return false;
	}
	if(recode.data.regionLevCode=='DISTRICT_PROVINCE'){
		if(entity.provinceCode==recode.data.provinceCode){
			return true;	
		}
	}else if(recode.data.regionLevCode=='DISTRICT_CITY'){
		if(entity.provinceCode==recode.data.provinceCode&&
				entity.cityCode==recode.data.cityCode){
			return true;	
		}
	}else if(recode.data.regionLevCode=='DISTRICT_COUNTY'){
		
		if(entity.provinceCode==recode.data.provinceCode&&
				entity.cityCode==recode.data.cityCode&&
				entity.countyCode==recode.data.countyCode
				){
			return true;	
		}
	}
	return false;
}

/**
 * 新增验证
 */
baseinfo.billInspectionRemind.addSubmitValidate=function(entity,recode){
  
	if(entity.regionLevCode=='DISTRICT_PROVINCE'){
		 if(entity.provinceCode==null){
		   entity.cityCode=null;
		   entity.cityName=null;
		   entity.countyCode=null;
		   entity.countyName=null;
		   Ext.MessageBox.alert('提示','省份不能为空！');
		   return false;
		 }
		 
	}else if(entity.regionLevCode=='DISTRICT_CITY'){
		 if(entity.provinceCode==null||entity.cityCode==null){
		   entity.countyCode=null;
		   entity.countyName=null;
		   Ext.MessageBox.alert('提示','省份、城市不能为空！');
		   return false;
		 } 
		 
	}else if(entity.regionLevCode=='DISTRICT_COUNTY'){
		 if(entity.provinceCode==null||entity.cityCode==null||entity.countyCode==null){
		   Ext.MessageBox.alert('提示','省份、城市、区县不能为空！');
		  return false;
		 }
	}
	return true;
}

/**
 * 
 */
baseinfo.billInspectionRemind.DataPermissions=function(win,entity,type,recode){  
	var url=null;
	if(type=='add'){
		url='addBillInspectionRemind.action';
		var flag=baseinfo.billInspectionRemind.addSubmitValidate(entity,recode);
		if(!flag){
		  return;
		}
		
	}else if(type=='update'){
		
		url='updateBillInspectionRemind.action';
		entity.id=recode.data.id;
		var flag=baseinfo.billInspectionRemind.updateSubmitValidate(entity,recode);
		
		if(flag==true){
			Ext.MessageBox.alert('提示','您所修改的数据没有改变！');
			return;
		}
	}
	
	
	
	var objectVo=new Object();
	var billInspectionRemindEntity=new Object();
	objectVo.billInspectionRemindEntity=entity;
	Ext.Ajax.request({
		url:baseinfo.realPath(url),
		jsonData:{'objectVo':objectVo},
		method:'post',
		success:function(response){
			var result = Ext.decode(response.responseText);
			var grid  = Ext.getCmp('T_baseinfo-billInspectionRemind_content').getQueryResultGrid();//得到grid
			win.hide();
			grid.store.loadPage(1);//用分页的moveFirst()方法
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.showWoringMessage(result.message);
		}
	});
}

//----------------------------------------------------------------------------ONREADY--------------------------------------------------------
Ext.onReady(function() {Foss.baseinfo.billInspectionRemind.BillInspectionRemindModel
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-billInspectionRemind_content')) {
		return;
	}
	var queryForm=Ext.create('Foss.baseinfo.billInspectionRemind.BillInspectionRemindQueryForm',{'record':Ext.create('Foss.baseinfo.commonSelector.AgencyDeptModel')}); 
	var queryResultGrid=Ext.create('Foss.baseinfo.billInspectionRemind.QueryResultGrid');
	Ext.getCmp('T_baseinfo-billInspectionRemind').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-billInspectionRemind_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQueryForm:function(){
			
			return queryForm;
		},
		getQueryResultGrid:function(){
			
			return queryResultGrid;
		},
		items:[queryForm,queryResultGrid]
	}));
});

var cityObj={ 
							forceSelection : true,
							xtype : 'commonCityByProvinceselector',
							name:'cityCode',
							queryMode: 'local',
							labelWidth:60,
							listeners:{	
								expand:function(field,eOpts ){					
								var model=field.up().up().getForm().findField('regionLevCode').getValue();
								if(null==model){
								  Ext.MessageBox.alert('提示','区域级别不能为空！');
								  return; 
								}
								if(model=='DISTRICT_CITY'||
									model=='DISTRICT_COUNTY'){
									var province=field.up().query('commonprovinceselector')[0];
									if(null==province.getValue()){
									   Ext.MessageBox.alert('提示','省份不能为空！');
									   return;
									}
								}
									field.getStore().load({
										params: { "cityVo.parentId": field.parentId },
										callback: function(records, operation, success){
										   //console.log(records);
										
										}
									});
								},
								select:function(newValue,oldValue,eOpts){
									var form=this.up();
									var county= this.up().query('commonAreaByCityselector')[0];
									if(null!=county){
									    county.parentId=oldValue[0].data.code;
									}
									
								}
							}
						};

var countyObj={
							xtype : 'commonAreaByCityselector',
							labelWidth:60,
							queryMode: 'local',
							name:'countyCode',
							listeners:{	
							expand:function(field,eOpts ){

								var model=field.up().up().getForm().findField('regionLevCode').getValue();
								if(null==model){
								  Ext.MessageBox.alert('提示','区域级别不能为空！');
								  return; 
								}
								if(model=='DISTRICT_COUNTY'){
									var city=field.up().query('commonCityByProvinceselector')[0];
									if(null==city.getValue()){
									   Ext.MessageBox.alert('提示','城市不能为空！');
									   return;
									}
								}
								field.getStore().load({
									params: { "cityVo.parentId": field.parentId },
									callback: function(records, operation, success){
									  // console.log(records);
									}
								});
							}
						}
						};


