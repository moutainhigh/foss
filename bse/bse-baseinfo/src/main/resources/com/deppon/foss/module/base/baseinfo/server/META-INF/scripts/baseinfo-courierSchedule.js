/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
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
 * 根据小区编码查询主责快递员from快递收派小区
 */
baseinfo.courierSchedule.queryCourierUserData=function(field){
	var form=field.getForm();
	var expressSmallZoneCode = form.findField('expressSmallZoneCode').getValue();
	if(Ext.isEmpty(expressSmallZoneCode)){
		return;	
	}
	var objectVo = {},expressDeliverySmallZoneEntity = {};
	expressDeliverySmallZoneEntity.regionCode = expressSmallZoneCode;
	objectVo.expressDeliverySmallZoneEntity = expressDeliverySmallZoneEntity;
	
	//自动生成 区域编码
	baseinfo.requestAjaxJson(baseinfo.realPath('querySmallCourierByCode.action'), {
		'objectVo' : objectVo
	}, function(result) {
		 if(null!=result){
			 var obj=result.objectVo.expressDeliverySmallZoneEntity;
			 form.findField('courierCode').setCombValue(obj.courierName,obj.courierCode);//
		 }
	}, function(result) {
		baseinfo.showInfoMsg(result.message);
	});
}

/**
 * 查询用户快递点部门数据权限
 */
baseinfo.courierSchedule.queryCourierUserDeptData=function(queryForm){
	Ext.Ajax.request({
				url:baseinfo.realPath('queryCourierUserDeptData.action'),
				success:function(response){
					var json =Ext.decode(response.responseText);
					queryForm.userOrgDatas =json.vo.userOrgDatas;	
					},
				exception:function(response){
					var json =Ext.decode(response.responseText);
					}
				});
},
/**
 * Model
 */
Ext.define('Foss.baseinfo.courierSchedule.courierScheduleModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'schedulingDate',//排班日期
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{
		name:'expressSmallZoneCode',//收派小区编码
		type:'string'
	},{
		name:'expressSmallZoneName',//收派小区名称
		type:'string'
	},{
		name:'startFloor',//起始楼层
		type:'number'
	},{
		name:'endFloor',//结束楼层
		type:'number'
	},{
		name:'courierCode',//快递员编码
		type:'string'
	},{
		name:'courierName',//快递员名称
		type:'string'
	},{
		name:'courierNature',//快递员属性
		type:'string'
	},{
		name:'planType',//工作类别
		type:'string'
	},{
		name: 'vehicleNo',// 车牌号
		type:'string'
	},{
		name: 'vehicleLenghtCode',// 车型
		type: 'string'
	},{
		name:'courierPhone',//快递员电话
		type:'string'
	},{
		name:'expressPartCode',//所属快递点部
		type:'string'
	},{
		name:'expressPartName',//所属快递点部名称
		type:'string'
	},{
		name:'yearMonth',//排班年月
		type:'string'
	},{
		name:'startTime',//起始日期
		convert : dateConvert,
		type : 'date'
	},{
		name:'endTime',//结束日期
		convert : dateConvert,
		type : 'date'
	},{
		name:'createDate',//排班操作时间
//		format : 'Y-m-d h:m:s',
		/*convert : function(v){
			if(!Ext.isEmpty(v)){
				return Ext.Date.format(new Date(v),'Y-m-d h:m:s')
			}
			return null;
		},*/
		convert : dateConvert,
		type : 'date'
	}]
});
/**
 * Store
 */
Ext.define('Foss.baseinfo.courierSchedule.courierScheduleStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.courierSchedule.courierScheduleModel',
	pageSize:15,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCourierScheduleList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.courierScheduleList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * =====================================主界面============================
 */
/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.courierSchedule.QueryCourierScheduleForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.courierSchedule.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	userOrgDatas:new Array(),//用户数据权限
	validUserOrgData:function(val,valName){
		var me =this,flag =false;
		if(me.userOrgDatas.length==0){
			baseinfo.showErrorMes('用户没有任何快递点部的查询权限，请先配置部门数据权限');
			flag =true;
		}else{
			if(!Ext.isEmpty(val)){
				if(!Ext.Array.contains(me.userOrgDatas,val)){
					baseinfo.showErrorMes('用户没有'+valName+'的查询权限，请先配置部门数据权限');
					flag =true;
				}else{
					flag =false;
				}
			}
		}
		return flag;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressPartName'),//快递点部
			name:'expressPartCode',
			columnWidth:0.3,
			type : 'ORG',
			expressPart:'Y',
			salesDepartment:'Y'
		},{
			xtype:'commonExpressemployeeselectorYingYeName',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courier'),//快递员
			name:'courierCode',
			columnWidth:0.3
		},{
			xtype:'expressSmallZoneSelector',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressSmallZone'),//收派小区
			name:'expressSmallZoneCode',
			columnWidth:0.3
		},{
			xtype: 'rangeDateField',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleDate'),//排班日期,
			dateType: 'datetimefield_date97',
			fromName: 'startTime',
			toName: 'endTime',
			disallowBlank: true,
			editable:false,
			fromValue:Ext.Date.format(baseinfo.getStartDate(new Date(),-10),'Y-m-d H:i:s'),
			toValue:Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'),
			columnWidth: 0.55
		},{
			xtype:'container' 
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.courierSchedule.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleQueryButton'),
				  hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleQueryButton'),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.courierSchedule.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleQueryButton'),
				  hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleQueryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					if(me.getForm().isValid()){
						var expressPartCode=me.getForm().findField('expressPartCode');
						var flag =me.validUserOrgData(expressPartCode.value,expressPartCode.rawValue);
						if(flag){
							return;
						}
						me.up().getQueryCourierScheduleGrid().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查询结果列表
 */
Ext.define('Foss.baseinfo.courierSchedule.QueryCourierScheduleGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.courierSchedule.i18n('foss.baseinfo.queryGrid'),//'排班查询结果列表',
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.courierSchedule.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	//新增窗口
	addCourierScheduleWin:null,
	getAddCourierScheduleWin:function(){
		if(this.addCourierScheduleWin==null){
			this.addCourierScheduleWin=Ext.create('Foss.baseinfo.courierSchedule.AddCourierScheduleWin');
			this.addCourierScheduleWin.parent=this;
		}
		return this.addCourierScheduleWin;
	},
	//修改窗口
	updateCourierScheduleWin:null,
	getUpdateCourierScheduleWin:function(){
		if(this.updateCourierScheduleWin==null){
			this.updateCourierScheduleWin =Ext.create('Foss.baseinfo.courierSchedule.UpdateCourierScheduleWin');
			this.updateCourierScheduleWin.parent =this;
		}
		return this.updateCourierScheduleWin;
	},
	//导入窗口
	importCourierScheduleWin:null,
	getImportCourierScheduleWin:function(){
		if(this.importCourierScheduleWin==null){
			this.importCourierScheduleWin =Ext.create('Foss.baseinfo.courierSchedule.ImportCourierScheduleWin');
		}
		return this.importCourierScheduleWin;
	},
	//报表式查看窗口
	queryByReportWin:null,
	getQueryByReportWin:function(){
		if(this.queryByReportWin==null){
			this.queryByReportWin =Ext.create('Foss.baseinfo.courierSchedule.QueryByReportWin');
		}
		return this.queryByReportWin;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text : baseinfo.courierSchedule.i18n('foss.baseinfo.operate'),//操作
			items:[{
				iconCls: 'deppon_icons_edit',
				tooltip:baseinfo.courierSchedule.i18n('foss.baseinfo.edit'),//编辑
				//disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/updateButton'),
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					var schedulingDate=rowModel.get('schedulingDate');
					var old=Ext.Date.format(baseinfo.getStartDate(schedulingDate,0),'Y-m-d');
					var now=Ext.Date.format(baseinfo.getStartDate(new Date(),0),'Y-m-d');
					if(old<now){
						baseinfo.showErrorMes('不能对今天之前的排班信息进行修改！');
						return;
					}
					var updateWin =me.getUpdateCourierScheduleWin();
					updateWin.courierScheduleModel =rowModel;
					updateWin.show();
				}
			},{
				iconCls: 'deppon_icons_cancel',
				tooltip:baseinfo.courierSchedule.i18n('foss.baseinfo.void'),//作废
				//disabled:!baseinfo.satellitePartSalesDept.isPermission('satellitePartSalesDept/updateButton'),
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					var ids =new Array();
					ids.push(rowModel.data.id);
					//判断是否要作废
					baseinfo.showQuestionMes(baseinfo.courierSchedule.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
						if(e=='yes'){
							Ext.Ajax.request({
								jsonData:{'vo':{'ids':ids}},
								url:baseinfo.realPath('deleteCourierScheduleMore.action'),
								success:function(response){
									var json =Ext.decode(response.responseText);
									baseinfo.showInfoMes(json.message);
				    				me.getPagingToolbar().moveFirst();
								},
								exception:function(response){
									var json =Ext.decode(response.responseText);
									baseinfo.showErrorMes(json.message);
								}
							});
						}
					});
				}
			}]
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleDate'),//排班日期
			dataIndex:'schedulingDate',
			xtype: 'datecolumn',   
			format:'Y-m-d'
			
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressSmallZone'),//收派小区
			dataIndex:'expressSmallZoneName',
			width:140
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.startFloor'),//起始楼层
			dataIndex:'startFloor',
			renderer:function(value){
			 if(value==0){
			 	return '';
			 }else{
			 	return value;
			 }
			}
			
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.endFloor'),//结束楼层
			dataIndex:'endFloor',
			renderer:function(value){
			 if(value==0){
			 	return '';
			 }else{
			 	return value;
			 }
			}
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courier'),//快递员
			dataIndex:'courierName'
			
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courierNature'),//快递员属性
			dataIndex:'courierNature',
			renderer:function(val){
			 if(Ext.isEmpty(val)){
			  	return;
			 }
			 if(val=='FIXED'){
			    return '主责';
			 }else if(val =='MOTORIZED'){
			 	return '替班';
			 }else{
			 	return val;
			 }
			}
		},{
			text:'排班操作时间',//快递员
			dataIndex:'createDate',
			format:'Y-m-d H:i:s',
			xtype: 'datecolumn',
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.planType'),//工作类别
			dataIndex:'planType',
			renderer:function(val){
				if(Ext.isEmpty(val)){
			  		return;
				 }
				 if(val=='WORK'){
				    return '出车';
				 }else if(val =='REST'){
				 	return '休息';
				 }else if(val =='DUTY'){
				 	return '值班';
				 }else if(val =='LEAVE'){
				 	return '离岗';
				 }else if(val =='TRAINING'){
				 	return '培训';
				 }
			}
		},{
			text:baseinfo.courierSchedule.i18n('foss.baseinfo.vehicle.vehicleNo'),//车牌
			dataIndex:'vehicleNo'
		},{
			text:baseinfo.courierSchedule.i18n('foss.baseinfo.LTRmodles'),//车型foss.baseinfo.LTRmodles
			dataIndex:'vehicleLenghtCode'
			
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courierPhone'),//快递员电话
			dataIndex:'courierPhone'
			
		},{
			text:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressPart'),//快递员所属快递点部
			dataIndex:'expressPartName',
			width:140
		}];
		me.store=Ext.create('Foss.baseinfo.courierSchedule.courierScheduleStore',{
			autoLoad:false,
			pageSize:15,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-courierSchedule_content').getQueryCourierScheduleForm();
					if(queryForm !=null){
						var queryFormValue =queryForm.getForm().getValues();
						Ext.apply(operation,{
							params:{
								'vo.courierScheduleEntity.expressPartCode':queryFormValue.expressPartCode,
								'vo.courierScheduleEntity.courierCode':queryFormValue.courierCode,
								//'vo.courierScheduleEntity.expressSmallZoneCode':queryFormValue.expressSmallZoneCode, 要求改成通过参数传给后台
								'vo.courierScheduleEntity.expressSmallZoneName':queryForm.getForm().findField('expressSmallZoneCode').rawValue,
								'vo.courierScheduleEntity.startTime':queryFormValue.startTime,
								'vo.courierScheduleEntity.endTime':queryFormValue.endTime
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			text:'新增', //新增
			disabled:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleAddButton'),
			hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleAddButton'),
			handler:function(){
				me.getAddCourierScheduleWin().show();
			}
		},{
			text:'作废',//作废
			disabled:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleDeleteButton'),
			hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleDeleteButton'),
			handler:function(){
				var selections = me.getSelectionModel().getSelection();
				var ids =new Array();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.courierSchedule.i18n('foss.baseinfo.announcement.existNoActiveRecord'));
					return ;
				}
				for ( var i = 0; i < selections.length; i++) {
					ids.push(selections[i].data.id);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.courierSchedule.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'ids':ids}},
							url:baseinfo.realPath('deleteCourierScheduleMore.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showInfoMes(json.message);
			    				me.getPagingToolbar().moveFirst();
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				});
			}
		},
	/*	{
			text:'快递员排班导入',//快递员排班导入
			hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleImportButton'),
			handler:function(){
				me.getImportCourierScheduleWin().show();
			}
		},*/
		{
			text:'报表式查看',//报表式查看
			disabled:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleReportQueryButton'),
			hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleReportQueryButton'),
			handler:function(){
				me.getQueryByReportWin().show();
			}
		}
		/*,{
			text:'模板下载',//模板下载
			hidden:!baseinfo.courierSchedule.isPermission('courierSchedule/courierScheduleDownloadButton'),
			handler:function(){
				 downloadCourierExcelPath();
			}
		}*/
		];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/**
 * ----------------------------表单------------------------------
 */
/**
 * 新增、修改Form
 */
Ext.define('Foss.baseinfo.courierSchedule.AddOrUpdateCourierScheduleForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleInfo'),// 排班信息
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:80
	},
	status:null,
	isUpdate:false,//默认是新增form
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype: 'rangeDateField',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleDate'),//排班日期,
			dateType: 'datefield',
			fromName: 'startTime',
			toName: 'endTime',
			disallowBlank: (config.status==='ADD'),
			editable:false,
			fromEditable:false,
			hidden:(config.status==='UPDATE'),
//			fromValue:Ext.Date.format(baseinfo.getStartDate(new Date(),1),'Y-m-d'),
			//toValue:Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'),
			columnWidth: 0.5
		},{
			xtype: 'datefield',
			format:'Y-m-d',
			allowBlank:(config.status==='ADD'),
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleDate'),//'排班日期',
			name: 'schedulingDate',
			hidden:(config.status==='ADD'),
			columnWidth:.25
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressSmallZone'),//收派小区
			xtype:'expressSmallZoneSelector',
			name:'expressSmallZoneCode',
			allowBlank:false,
			columnWidth:.25,
			listeners:{
				select:function(field,newValue,oldValue){
				/*	this.up('panel').getForm().findField('courierCode').setValue(field.rawValue);
					win.editForm.down('commonemployeeselector').setCombValue(formRecord.get('courierName'),formRecord.get('courierCode'));//
				*/
					baseinfo.courierSchedule.queryCourierUserData(field.up('form'));
				}
			}
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.planType'),//'工作类别',
			name: 'planType',
			xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank:false,
			columnWidth:.25,
			value:'',
			store:FossDataDictionary.getDataDictionaryStore('EXPRESS_PLAN_TYPE'),
			listeners:{
				select:function(_this,e,eOpts){
					/*var value =	_this.getValue();
					if(Ext.isEmpty(value)){
						return;
					}
					//若值不是出车状态
					if(value !='WORK'){
						_this.up('form').getForm().getFields().each(function(item){
							if(item.name =='startFloor'||item.name =='endFloor'){
								item.allowBlank =true;
								item.setValue(0);
								item.setReadOnly(true);
							}else if(item.name =='courierNature'||item.name =='expressSmallZoneCode'){
								item.allowBlank =true;
								item.setValue('');
								item.setReadOnly(true);
							}
						});
					}else if(value =='WORK'){
						_this.up('form').getForm().getFields().each(function(item){
								item.allowBlank =false;
								item.setReadOnly(false);
						});
					}
					//若是为修改的话,
					if(_this.up('form').getForm().isUpdate){
						_this.up('form').getForm().getFields().each(function(item){
							if(item.name =='courierCode'||item.name =='schedulingDate'){
								item.allowBlank =true;
								item.setReadOnly(true);
							}
						});
					}*/
				}
			}
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courier'),//快递员
			xtype:'commonExpressemployeeselectorYingYeName',
			name:'courierCode',
			allowBlank:false,
			columnWidth:.25
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.courierNature'),//'快递员属性',
			name: 'courierNature',
			xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank:false,
			columnWidth:.25,
			value:'FIXED',
			//store:FossDataDictionary.getDataDictionaryStore('COURIER_NATURE'),
			store:FossDataDictionary.getDataDictionaryStore('COURIER_NATURE')
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.startFloor'),//'起始楼层',
			xtype: 'numberfield',
			name: 'startFloor',
			allowBlank:true,
			maxValue: 99,
		    minValue: 0,
			columnWidth:.25
		},{
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.endFloor'),//'结束楼层',
			name: 'endFloor',
			xtype: 'numberfield',
			allowBlank:true,
			maxValue: 99,
		    minValue: 0,
			columnWidth:.25
		}];
		me.callParent([cfg]);
	}
});
//导入form
Ext.define('Foss.baseinfo.courierSchedule.ImportCourierScheduleForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleInfo'),// 导入排班信息
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	bodyCls: 'autoHeight',
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressPartName'),//快递点部
			name:'vo.courierScheduleEntity.expressPartCode',
			columnWidth:0.45,
			allowBlank:false,
			type : 'ORG',
			expressPart:'Y',
			salesDepartment:'Y'
		},{
			xtype: 'datefield',
			format:'Y-m',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleYearMonth'),//'排班年月',
			name: 'vo.courierScheduleEntity.yearMonth',//
			allowBlank:false,
			columnWidth:.45
		},{
			 xtype: 'filefield',
		     name: 'uploadFile',
		     readOnly:false,
		     buttonOnly:false,
		     fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.uploadFile'),//'导入文件',
		     msgTarget: 'side',
		     cls:'uploadFile',
		     allowBlank: false,			        
		     buttonText: baseinfo.courierSchedule.i18n('foss.baseinfo.browse'),//'浏览',
		     columnWidth:.85
		},{
			xtype : 'button',
			columnWidth:.15,
			cls:'cleanBtn',
			text: baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.cleanUp'),//'清除',
			handler: function() {
				this.up('form').getForm().findField('uploadFile').reset();						
			}
		}];
		me.fbar=[{
			text:'取消',//取消
			handler:function(){
				me.up('window').close();
			}
		},{
			text:'导入',//导入
			handler:function(){
				if(me.getForm().isValid()){
					//加上罩
					var myMask = new Ext.LoadMask(me,  {msg:'正在导入，请稍等...'});//"正在导入，请稍等..."
		 			myMask.show();
		 			me.getForm().submit({
		 				url:baseinfo.realPath('importCourierSchedule.action'),
		 				success:function(form, action){
		 					myMask.hide();
					        var json =action.result;
					        baseinfo.showInfoMes('导入成功'+json.vo.importToTal+'条');
					        if(!Ext.isEmpty(json.message)){
					        	baseinfo.showInfoMes(json.message);
					        	return;
					        }
					    	me.up().close();
							
		 				},
		 				failure:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				},
		 				exception:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				}
		 			});
				}
			}
		}];
		me.callParent([cfg]);
	}
});
//报表式查看form
Ext.define('Foss.baseinfo.courierSchedule.QueryByReportForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleInfo'), 
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype:'dynamicorgmulticombselector',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.expressPartName'),//快递点部【多选】
			name:'expressPartCodes',//多选
			columnWidth:0.55,
			allowBlank:false,
			type : 'ORG',
			salesDepartment:'Y',
			expressPart:'Y'
		},{
			xtype: 'datefield',
			format:'Y-m',
			fieldLabel:baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.scheduleYearMonth'),//'排班年月',
			name: 'yearMonth',//
			allowBlank:false,
			columnWidth:.45
		}];
		me.fbar=[{
			text:'取消',//取消
			handler:function(){
				me.up('window').close();
			}
		},{
			text:'确定',//确定
			handler:function(){
				if(me.getForm().isValid()){
					var vals =me.getForm().getValues();
					 baseinfo.courierSchedule.ymd =vals.yearMonth;
					 baseinfo.courierSchedule.expressPartCodes =vals.expressPartCodes;
					 if(vals.expressPartCodes.length>3){
						 baseinfo.showInfoMes('查询报表最多只能同时查询三个部门下小区'+baseinfo.courierSchedule.ymd+'排班信息');
						 return;
					 }
					 baseinfo.courierSchedule.expressPartNames=me.getForm().findField('expressPartCodes').rawValue;
	            	//先判断，如过已经打开报表则先关闭，在新增tab页签
	            	if(Ext.getCmp('T_baseinfo-queryCourierSchedule')!=null){
	            		removeTab('T_baseinfo-queryCourierSchedule');
	            	}					            	
	            	//打开新的标签进行操作
	            	addTab('T_baseinfo-queryCourierSchedule',//对应打开的目标页面js的onReady里定义的renderTo
						baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.queryByScheduleInfo'),//'制定短途排班',//打开的Tab页的标题
						baseinfo.realPath('queryCourierSchedule.action')
						+ '?ymd="' + baseinfo.courierSchedule.ymd + '"'
						+ '&expressPartCodes="' + baseinfo.courierSchedule.expressPartCodes + '"'
						);
					me.up().close();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * ---------------------------窗口-----------------------------
 */
/**
 * 新增window
 */
Ext.define('Foss.baseinfo.courierSchedule.AddCourierScheduleWin',{
	extend:'Ext.window.Window',
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.addSchedule'),//新增排班任务
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	status:'add',
	width :800,
	height :230,
	listeners:{
		'beforehide':function(me){
			me.getAddCourierScheduleForm().getForm().reset();
			me.getAddCourierScheduleForm().getForm().getFields().each(function(item){
				if(item.readOnly){
					item.setReadOnly(false);
				}
			});
		}
	},
	addCourierScheduleForm:null,
	getAddCourierScheduleForm:function(){
		if(this.addCourierScheduleForm ==null){
			this.addCourierScheduleForm= Ext.create('Foss.baseinfo.courierSchedule.AddOrUpdateCourierScheduleForm',{
				isUpdate:false,
				status:'ADD'
			});
		}
		return this.addCourierScheduleForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items =[me.getAddCourierScheduleForm()];
		me.fbar =[{
			text:'重置',//重置
			handler:function(){
				me.getAddCourierScheduleForm().getForm().reset();
				me.getAddCourierScheduleForm().getForm().getFields().each(function(item){
					if(item.readOnly){
						item.setReadOnly(false);
					}
				});
			}
		},{
			text:'保存',//保存
			cls:'yellow_button',
			handler:function(){
				if(me.getAddCourierScheduleForm().getForm().isValid()){
					var model =new Foss.baseinfo.courierSchedule.courierScheduleModel();
					me.getAddCourierScheduleForm().getForm().updateRecord(model);
					var field =me.getAddCourierScheduleForm().getForm().findField('expressSmallZoneCode');
					//若收派小区不为空
					if(!Ext.isEmpty(field.getValue())){
						model.set('expressSmallZoneName',field.rawValue);
					}else{
						model.set('expressSmallZoneName','');
					}
					var params ={'vo':{'courierScheduleEntity':model.data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('addCourierSchedule.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-courierSchedule_content').getQueryCourierScheduleGrid().getPagingToolbar().moveFirst();
							me.close();
						},
						exception:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showErrorMes(json.message);
						}
					});
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改window
 */
Ext.define('Foss.baseinfo.courierSchedule.UpdateCourierScheduleWin',{
	extend:'Ext.window.Window',
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.updateSchedule'),//修改排班任务
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	status:'update',
	width :800,
	height :230,
	courierScheduleModel:null,
	listeners:{
		'beforeshow':function(me){
			if(!Ext.isEmpty(me.courierScheduleModel)){
				var model =me.courierScheduleModel;
				me.getUpdateCourierScheduleForm().getForm().loadRecord(model);
				me.getUpdateCourierScheduleForm().getForm().findField('schedulingDate').setReadOnly(true);
				if(!Ext.isEmpty(model.get('expressSmallZoneCode'))){
					me.getUpdateCourierScheduleForm().getForm().findField('expressSmallZoneCode').setCombValue(model.get('expressSmallZoneName'),model.get('expressSmallZoneCode'));
				}
				me.getUpdateCourierScheduleForm().getForm().findField('courierCode').setCombValue(model.get('courierName'),model.get('courierCode'));				
				if(!Ext.isEmpty(model.get('planType'))){
					//若是出车，则....不可填项
					if(model.get('planType')!='WORK'){
						me.getUpdateCourierScheduleForm().getForm().getFields().each(function(item){
							if(item.name =='startFloor'||item.name =='endFloor'||item.name =='courierNature'||item.name =='expressSmallZoneCode'){
								item.allowBlank =true;
								item.setReadOnly(true);
							}
						});
					}else{//若不是出车
					
					}
				}
			}
		},
		'beforehide':function(me){
			me.getUpdateCourierScheduleForm().getForm().reset();
			me.getUpdateCourierScheduleForm().getForm().getFields().each(function(item){
				if(item.readOnly){
					item.setReadOnly(false);
				}
			});
		}
	},
	updateCourierScheduleForm:null,
	getUpdateCourierScheduleForm:function(){
		if(this.updateCourierScheduleForm ==null){
			this.updateCourierScheduleForm= Ext.create('Foss.baseinfo.courierSchedule.AddOrUpdateCourierScheduleForm',{
				isUpdate:true,
				status:'UPDATE'
			});
		}
		return this.updateCourierScheduleForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items=[me.getUpdateCourierScheduleForm()];
		me.fbar =[{
			text:'重置',//重置
			handler:function(){
				var model =me.courierScheduleModel;
				me.getUpdateCourierScheduleForm().getForm().loadRecord(me.courierScheduleModel);
				me.getUpdateCourierScheduleForm().getForm().findField('schedulingDate').setReadOnly(true);
				if(!Ext.isEmpty(model.get('expressSmallZoneCode'))){
					me.getUpdateCourierScheduleForm().getForm().findField('expressSmallZoneCode').setCombValue(model.get('expressSmallZoneName'),model.get('expressSmallZoneCode'));
				}
				me.getUpdateCourierScheduleForm().getForm().findField('courierCode').setCombValue(model.get('courierName'),model.get('courierCode'));				
				if(!Ext.isEmpty(model.get('planType'))){
					//若是出车，则....不可填项
					if(model.get('planType')!='WORK'){
						me.getUpdateCourierScheduleForm().getForm().getFields().each(function(item){
							if(item.name =='startFloor'||item.name =='endFloor'||item.name =='courierNature'||item.name =='expressSmallZoneCode'){
								item.allowBlank =true;
								item.setReadOnly(true);
							}
						});
					}
				}
			}
		},{
			text:'保存',//保存
			cls:'yellow_button',
			handler:function(){
				if(me.getUpdateCourierScheduleForm().getForm().isValid()){
					var newModel =me.courierScheduleModel;
					var data=Ext.Object.merge(new Object(),newModel.data,me.getUpdateCourierScheduleForm().getForm().getValues());
					var field =me.getUpdateCourierScheduleForm().getForm().findField('expressSmallZoneCode');
					//若收派小区不为空
					if(!Ext.isEmpty(field.getValue())){
//						model.set('expressSmallZoneName',field.rawValue);
						data.expressSmallZoneName=field.rawValue;
					}else{
//						model.set('expressSmallZoneName','');
						data.expressSmallZoneName='';
					}
					//
					var params ={'vo':{'courierScheduleEntity':data}};
					Ext.Ajax.request({
						jsonData:params,
						url:baseinfo.realPath('updateCourierSchedule.action'),
						success:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showInfoMes(json.message);
							Ext.getCmp('T_baseinfo-courierSchedule_content').getQueryCourierScheduleGrid().getPagingToolbar().moveFirst();
							me.close();
						},
						exception:function(response){
							var json =Ext.decode(response.responseText);
							baseinfo.showErrorMes(json.message);
						}
					});
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 导入窗口
 */
Ext.define('Foss.baseinfo.courierSchedule.ImportCourierScheduleWin',{
	extend:'Ext.window.Window',
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.importSchedule'),//修改排班任务
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : 'auto',
	bodyCls: 'autoHeight',
	width :700,
	listeners:{
		'beforehide':function(me){
			me.getImprotCourierScheduleForm().getForm().reset();
		}
	},
	improtCourierScheduleForm:null,
	getImprotCourierScheduleForm:function(){
		if(this.improtCourierScheduleForm ==null){
			this.improtCourierScheduleForm= Ext.create('Foss.baseinfo.courierSchedule.ImportCourierScheduleForm');
		}
		return this.improtCourierScheduleForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items=[me.getImprotCourierScheduleForm()];
		me.callParent([cfg]);
	}
});
/**
 * 报表式查看窗口
 */
Ext.define('Foss.baseinfo.courierSchedule.QueryByReportWin',{
	extend:'Ext.window.Window',
	title : baseinfo.courierSchedule.i18n('baseinfo.courierSchedule.queryBySchedule'),//修改排班任务
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : 'auto',
	height:280,
	width :700,
	listeners:{
		'beforeshow':function(me){
			//设置默认的排班年月
			me.getQueryByReportForm().getForm().findField('yearMonth').setValue(Ext.Date.format(new Date(),'Y-m'));
		},
		'beforehide':function(me){
			me.getQueryByReportForm().getForm().reset();
		}
	},
	queryByReportForm:null,
	getQueryByReportForm:function(){
		if(this.queryByReportForm ==null){
			this.queryByReportForm= Ext.create('Foss.baseinfo.courierSchedule.QueryByReportForm');
		}
		return this.queryByReportForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items=[me.getQueryByReportForm()];
		me.callParent([cfg]);
	}
});
/**
 * ----------界面入口--------------
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-courierSchedule_content')) {
		return;
	}
	var queryCourierScheduleForm =Ext.create('Foss.baseinfo.courierSchedule.QueryCourierScheduleForm');
	var queryCourierScheduleGrid =Ext.create('Foss.baseinfo.courierSchedule.QueryCourierScheduleGrid');
	baseinfo.courierSchedule.queryCourierUserDeptData(queryCourierScheduleForm);
	Ext.getCmp('T_baseinfo-courierSchedule').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-courierSchedule_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryCourierScheduleForm : function() {
			return queryCourierScheduleForm;
		},
		getQueryCourierScheduleGrid : function() {
			return queryCourierScheduleGrid;
		},
		items: [queryCourierScheduleForm,queryCourierScheduleGrid]
	}));
});