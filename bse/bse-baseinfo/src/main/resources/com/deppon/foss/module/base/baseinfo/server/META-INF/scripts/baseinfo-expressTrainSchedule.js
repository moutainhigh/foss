//方案model
Ext.define('Foss.baseinfo.expressTrainSchedule.ProgramModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{//方案名称
		name:'programName',
		type:'string'
	},{//城市编码
		name:'cityCode',
		type:'string'
	},{//城市名称
		name:'cityName',
		type:'string'
	},{//装卸时间
		name:'handlingTime',
		type:'number'
	},{//车速
		name:'speed',
		type:'number'
	},{//线路条数
		name:'lineCount',
		type:'number'
	},{//所需车辆数
		name:'vehicleCount',
		type:'number'
	},{//所覆盖营业部数
		name:'salesDeptCount',
		type:'number'
	},{//起始时间
		name:'startTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{//结束时间
		name:'endTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{//最后修改人
		name:'modifyUser',
		type:'string'
	},{//最后修改人
		name:'modifyUserName',
		type:'string'
	},{//最后修改时间
		name:'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{//出发外场
		name:'originOutfieldCode',
		type:'string'
	},{//出发外场名称
		name:'originOutfieldName',
		type:'string'
	},{//方案描述
		name:'programRemarks',
		type:'string'
	}]
});

//方案结果store
Ext.define('Foss.baseinfo.expressTrainSchedule.TrainProgramStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressTrainSchedule.ProgramModel',
	pageSize:15,
	proxy:{
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExpressTrainProgramList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.programEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
//线段时刻表Model
Ext.define('Foss.baseinfo.expressTrainSchedule.LineScheduleModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{//城市编码
		name:'cityCode',
		type:'string'
	},{//城市名称
		name:'cityName',
		type:'string'
	},{//线路名称
		name:'lineName',
		type:'string'
	},{//方案名称
		name:'programName',
		type:'string'
	},{//出发部门GIS坐标编码
		name:'originDeptGisId',
		type:'string'
	},{//到达部门GIS坐标编码
		name:'arriveDeptGisId',
		type:'string'
	},{//出发部门编码
		name:'originDeptCode',
		type:'string'
	},{//出发部门名称
		name:'originDeptName',
		type:'string'
	},{//到达部门编码
		name:'arriveDeptCode',
		type:'string'
	},{//到达部门名称
		name:'arriveDeptName',
		type : 'string'
	},{//出发时间
		name:'departTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{//行驶距离
		name:'travelDistance',
		type:'number'
	},{//行驶时效
		name:'travelTime',
		type:'string'
	},{//到达时间
		name:'arriveTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{//线段顺序编码
		name:'lineOrderCode',
		type:'string'
	}]
});
//输出线路时刻表的Store
Ext.define('Foss.baseinfo.expressTrainSchedule.LineScheduleStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressTrainSchedule.LineScheduleModel',
	pageSize:10,
	proxy:{
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryExpressLineScheduleList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.lineScheduleEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
//快递班车时刻表查询表单
Ext.define('Foss.baseinfo.expressTrainSchedule.QueryForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 100,
		width : 120
	},
	height :160,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me =this,cfg=Ext.apply({},config);
		me.items=[{
			name:'cityCode',
			xtype:'commoncityselector',
			fieldLabel:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.airagencycompany.city'),//城市
			columnWidth:0.3
		},{
			xtype:'container' ,
			columnWidth:0.7
		},{
			xtype:'container' ,
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleQueryButton'),
				  hidden:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleQueryButton'),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleQueryButton'),
				  hidden:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleQueryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid().getPagingToolbar().moveFirst();
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}
});
//快递班车时刻表查询列表
Ext.define('Foss.baseinfo.expressTrainSchedule.QueryGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.queryGrid'),//'方案结果列表',
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
	addProgramWin:null,
	getAddProgramWin:function(){
		if(this.addProgramWin ==null){
			this.addProgramWin =Ext.create('Foss.baseinfo.expressTrainSchedule.AddProgramWin');
			this.addProgramWin.parent =this;
		}
		return this.addProgramWin;
	},
	updateProgramWin:null,
	getUpdateProgramWin:function(){
		if(this.updateProgramWin ==null){
			this.updateProgramWin =Ext.create('Foss.baseinfo.expressTrainSchedule.UpdateProgramWin');
			this.updateProgramWin.parent =this;
		}
		return this.updateProgramWin;
	},
	queryProgramWin:null,
	getQueryProgramWin:function(){
		if(this.queryProgramWin ==null){
			this.queryProgramWin =Ext.create('Foss.baseinfo.expressTrainSchedule.QueryProgramWin');
			this.queryProgramWin.parent =this;
		}
		return this.queryProgramWin;
	},
	constructor:function(config){
		var me =this,cfg=Ext.apply({},config);
		me.columns=[{
			xtype: 'rownumberer',
			width:40,
			text : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.sequence')//序号
		},{
			align:'center',
			xtype:'actioncolumn',
			text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.operate'),
			items:[{
				iconCls: 'deppon_icons_edit',
				disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleEditButton'),
				tooltip:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.edit'),//编辑
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					me.getUpdateProgramWin().expressTrainProgramModel =rowModel;
					me.getUpdateProgramWin().show();
				}
			},{
				iconCls: 'deppon_icons_cancel',
				tooltip:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.void'),//作废
				disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleDeleteButton'),
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var programList =new Array();
					programList.push(grid.getStore().getAt(rowIndex).data);
					//判断是否要作废
					baseinfo.showQuestionMes(baseinfo.expressTrainSchedule.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
						if(e=='yes'){
							Ext.Ajax.request({
								jsonData:{'vo':{'programEntityList':programList}},
								url:baseinfo.realPath('deleteProgramMore.action'),
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
			},{
				iconCls: 'deppon_icons_showdetail',
				tooltip:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.details'),//查询详细信息
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					me.getQueryProgramWin().expressTrainProgramModel =rowModel;
					me.getQueryProgramWin().show();
				}
			}]
		},{
			dataIndex:'programName',
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.programName') //方案名称
		},{
			dataIndex:'cityName',
			text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.airagencycompany.city') //城市
		},{
			dataIndex:'lineCount',
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.lineCount') //线路条数
		},{
			dataIndex:'vehicleCount',
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.vehicleCount') //所需车辆数
		},{
			dataIndex:'salesDeptCount',
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.salesCount') //所覆盖的营业部
		},{
			dataIndex:'startTime',
			format:'H:i',
			xtype: 'datecolumn',   
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.beginTime')//起始时间
		},{
			dataIndex:'endTime',
			format:'H:i',
			xtype: 'datecolumn',   
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.endTime')//终止时间
		},{
			dataIndex:'modifyUserName',
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.lastModifyUser')//最后修改人
		},{
			dataIndex:'modifyDate',
			format:'Y-m-d h:i:s',
			xtype: 'datecolumn',  
			width:160,
			text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.lastModifyTime')//最后修改时间
		}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.store = Ext.create('Foss.baseinfo.expressTrainSchedule.TrainProgramStore',{
			autoLoad:false,
			pageSize:15,
			listeners:{
				beforeload:function(store,operation,eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryForm();
					if(queryForm !=null){
						Ext.apply(operation,{
							params:{
								'vo.expressTrainProgramDto.expressTrainProgramEntity.cityCode':queryForm.getForm().findField('cityCode').value
							}
						});
					}
				}
			}
		});
		me.tbar =[{
			text:'新增方案',
			disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleAddButton'),
			hidden:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleAddButton'),
			handler:function(){
				me.getAddProgramWin().show();
			}
		},{
			text:'作废方案',
			disabled:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleDeleteButton'),
			hidden:!baseinfo.expressTrainSchedule.isPermission('expressTrainSchedule/expressTrainScheduleDeleteButton'),
			handler:function(){
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.expressTrainSchedule.i18n('foss.baseinfo.announcement.existNoActiveRecord'));
					return ;
				}
				var programList =new Array();
				for ( var i = 0; i < selections.length; i++) {
					programList.push(selections[i].data);
				}
				//判断是否要作废
				baseinfo.showQuestionMes(baseinfo.expressTrainSchedule.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'programEntityList':programList}},
							url:baseinfo.realPath('deleteProgramMore.action'),
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
		}];
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
 * ----------------------------------新增/修改/详细方案的输入form-----------------------------------------
 */
Ext.define('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.innerParams'),// 输入参数
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 80,
		width : 100
	},
	margin: '0 0 0 0',
	height :220,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	isUpdate:false,
	salesDepartments:new Array(), //营业部集合
	addSalesDeptWin:null,
	getAddSalesDept:function(){
		if(this.addSalesDeptWin ==null){
			this.addSalesDeptWin =Ext.create('baseinfo.expressTrainSchedule.AddSaleDeptWin');
		}
		return this.addSalesDeptWin;
	},
	constructor:function(config){
		var me =this, cfg=Ext.apply({},config);
		me.items=[{
				name:'programName',
				fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.programName'),//方案名称
				columnWidth:0.25,
				allowBlank:false
			},{
				name:'cityCode',
				xtype:'commoncityselector',
				fieldLabel:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.airagencycompany.city'),//城市
				columnWidth:0.25,
				allowBlank:false
			},{
				xtype:'textfield',
				name:'handlingTime',
				fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.handlingTime'),//装卸时间
				columnWidth:0.2,
				regex:/^\d{1,3}$/,
				allowBlank:false
			},{
				xtype:'label',
				columnWidth:0.05,
				align: 'middle',
				margin: '5 2 0 2',
				text:' 分钟'
			},{
				xtype:'numberfield',
				name:'speed',
				fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.speed'),//车速
				labelWidth : 60,
				decimalPrecision:2,
				minValue:0.00,
				maxValue:120.00,
				columnWidth:0.2,
				allowBlank:false	
			},{
				xtype:'label',
				columnWidth:0.05,
				align: 'middle',
				margin: '5 2 0 2',
				text:'Km/h'
			},{
				fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.beginToEndTime'),//起止时间
		       	xtype: 'rangedatefield',
		        dateType: 'timefield',
		        //时间间隔，以分钟为单位。
		        increment: 60,
		        //起始日期组件的name属性。
		        fromName: 'startTime',
		        //终止日期组件的name属性。
		        toName: 'endTime',
		        //不允许为空
		        disallowBlank: true,
		        columnWidth:0.5
			},{
				fieldLabel:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.originOutfield'),//出发外场
				xtype:'dynamicorgcombselector',
				name:'originOutfieldCode',
				allowBlank:false,
				type : 'ORG',
			    transferCenter:'Y',//--或者查询外场
				columnWidth:0.25,
				listeners:{
					change:function(_this,newV,oldV){
						if(newV){
							_this.up('form').salesDepartments = new Array(), //营业部集合清空
							_this.up('form').getForm().findField('salesText').setValue('');
						}
					}
				}
			},{
				xtype:'textareafield',
				name:'programRemarks',
				fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.programRemarks'),//方案描述
				columnWidth:0.5
			},{
				xtype:'button',
				text:'选择营业部',
				columnWidth:0.15,
				handler:function(){
					var originOutfield =this.up('form').getForm().findField('originOutfieldCode');
					if(Ext.isEmpty(originOutfield.getValue())){
						baseinfo.showInfoMes('需要输入出发外场，请核对导入信息！');
						return;
					}
					me.getAddSalesDept().originOutfield.Code =originOutfield.getValue();
					me.getAddSalesDept().originOutfield.Name =originOutfield.rawValue;
					me.getAddSalesDept().show();
				}
			},{
				xtype:'textareafield',
				name:'salesText',
				readOnly:true,
				fieldLabel: baseinfo.expressTrainSchedule.i18n(''),//营业部槽
				columnWidth:0.35
			},{
				xtype:'container',
				defaultType:'button',
				columnWidth:1,
				layout:'column',
				items:[{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.8
				},{
					text:'重置',
					columnWidth:0.1,
					handler:function(){
						me.getForm().reset();
					}
				},{
					text:'生成',
					columnWidth:0.1,
					handler:function(){
						if(me.getForm().isValid()){
							if(me.salesDepartments.length ==0){
								baseinfo.showInfoMes('请选择营业部!');
								return;
							}
							//加上罩
							var myMask = new Ext.LoadMask(me,  {msg:'正在生成方案，请稍等...'});//
							//定义一个model 
							var programModel =new Foss.baseinfo.expressTrainSchedule.ProgramModel();
							me.getForm().updateRecord(programModel);
							//设置城市名称，外场名称
							programModel.set('cityName',me.getForm().findField('cityCode').rawValue);
							programModel.set('originOutfieldName',me.getForm().findField('originOutfieldCode').rawValue);
							myMask.show();
							Ext.Ajax.request({
								jsonData:{'vo':{'expressTrainProgramDto':{'expressTrainProgramEntity':programModel.data,'salesList':me.salesDepartments}}},
								url:baseinfo.realPath('addExpressTrainProgram.action'),
								success:function(response){
									var json =Ext.decode(response.responseText);
									myMask.hide();
									baseinfo.showInfoMes(json.message); //保存成功！
									//提示用户,
									baseinfo.showInfoMes('数据提交成功，后台正在计算，第二天将会输出结果！');
									me.up('window').close();
									Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid().getPagingToolbar().moveFirst();
								},
								exception:function(response){
									var json =Ext.decode(response.responseText);
									myMask.hide();
									baseinfo.showInfoMes(json.message);
								}
							});
						}
					}
				}]
			}];
		me.callParent([cfg]);
	}
});
//输出结果列表
Ext.define('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.outerResult'),//'输出结果
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	pagingToolbar : null,
	margin: '5 0 0 0',
	operateType:'update', //默认操作类型是修改
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	deleteLineCount:0, //默认减少的为0
	//查看网点电子地图
	mainGisShowWindow:null,
	getMainGisShowWindow:function(){
		   var me =this;
		   if(Ext.isEmpty(this.mainGisShowWindow)){
			   this.mainGisShowWindow =Ext.create('Foss.baseinfo.expressTrainSchedule.MainGisWindow',{
				   'parent':this,
				   listeners:{
					   //显示之前的事件
					   beforeshow:function(window){
						   Ext.defer(function(){
							   var m = new DpMap(window.items.items[0].getId(), {center : window.city,zoom : 13}, function (map) {  //实例化一个新类  
					                var route=new DpMap.service.RouteSearch(map);
					                var points=[];
					                for ( var i = 0; i < window.orgGisList.length; i++) {
										var point =new Object();
										point.lng =window.orgGisList[i].longitude;
										point.lat =window.orgGisList[i].latitude;
										point.name =window.orgGisList[i].orgName;
										points.push(point);
									}
					                route.drawRoutePath(points); 
							   });
						   },700,this);
					   },
					   //隐藏之前的事件
					   beforehide:function(window){
						   window.removeAll();
		   				   window.add(Ext.create('Ext.container.Container',{
		   					   height:500
		   				   })); 
					   }
				   }
			   });
		   }
		   return this.mainGisShowWindow;
	  },
	updateExpressLineWin:null,
	getUpdateExpressLineWin:function(){
			if(this.updateExpressLineWin ==null){
				this.updateExpressLineWin =Ext.create('Foss.baseinfo.expressTrainSchedule.UpdateExpressLineScheduleWin');
				this.updateExpressLineWin.parent =this;
			}
			return this.updateExpressLineWin;
	},  
	//一系列组件作为挂靠组件被添加到panel中
	dockedItems:{
		xtype:'toolbar',
		dock:'top',
		layout:'column',
		defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:100,
			 width:30
		},
		items:[{
			fieldLabel:'线路条数',
			dataIndex:'lineCount',
			columnWidth:0.2
		},{
			fieldLabel:'所需要车辆数',
			dataIndex:'vehicleCount',
			columnWidth:0.2
		},{
			fieldLabel:'覆盖营业部数',
			dataIndex:'salesDeptCount',
			columnWidth:0.2
		},{
			text:'作废',
			xtype:'button',
			columnWidth:0.2,
			width:45,
			handler:function(){
				var me =this.up().up();
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					baseinfo.showErrorMes(baseinfo.expressTrainSchedule.i18n('foss.baseinfo.announcement.existNoActiveRecord'));
					return ;
				}
				var lineNameList =new Array();
				//根据线路名称去作废，作废只能作废整条线路，而非一个线段
				for ( var i = 0; i < selections.length; i++) {
					lineNameList.push(selections[i].data.lineName);
				}
				var programName =selections[0].data.programName,
				//去重
				lineNameList =Ext.Array.unique(lineNameList);
				//判断是否要作废
				baseinfo.showQuestionMes('每次作废一个线段时都会作废整条线路，确定要作废?',function(e){
					if(e=='yes'){
						Ext.Ajax.request({
							jsonData:{'vo':{'lineNameList':lineNameList,'expressLineScheduleEntity':{'programName':programName}}},
							url:baseinfo.realPath('deleteLineScheduleMore.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showInfoMes(json.message);
								me.deleteLineCount =lineNameList.length;
			    				//作废成功的时候
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
		},{
			text:'导出',
			xtype:'button',
			columnWidth:0.2,
			width:45,
			handler:function(){
				var me =this.up().up();
				var store =me.store;
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				if(store.getCount()!=0){
					var programName =store.data.items[0].data.programName;
					Ext.Ajax.request({
						url : baseinfo.realPath('exportLineSchedule.action'),
						form : Ext.fly('downloadAttachFileForm'),
						method :'POST',
						params : {
							'vo.expressLineScheduleEntity.programName':programName
						},
						isUpload:true,
					});
				}else{
					Ext.MessageBox.show({
						title : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
						msg : baseinfo.expressTrainSchedule.i18n('foss.baseinfo.leasedVan.noRecord'),
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});		
				}
			}
		}]
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.columns=[{
				align:'center',
				xtype:'actioncolumn',
				text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.operate'),
				items:[{
					iconCls: 'deppon_icons_edit',
					tooltip:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.edit'),//编辑
					width:30,
					getClass:function(value,metadata,record,rowIndex,colIndex,store){
						//获取面板状态
						var operateType =me.operateType;
						if(operateType == 'update'){
							return 'deppon_icons_edit';
						}else{
							return 'deppon_icons_edit_hide';
						}
					},
					handler:function(grid,rowIndex,colIndex){
						var rowModel =grid.getStore().getAt(rowIndex);
						me.getUpdateExpressLineWin().lineScheduleModel =rowModel;
						me.getUpdateExpressLineWin().show();
					}
				}]
			},{
				dataIndex:'cityName',
				text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.airagencycompany.city')//城市名称
			},{
				dataIndex:'lineName',
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.lineName')//线路名称
			},{
				align:'center',
				xtype:'actioncolumn',
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.queryMapByGis'),//查询Gis地图
				items:[{
					iconCls: 'deppon_icons_showdetail',
					tooltip:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.details'),//查询详细信息
					width:30,
					handler:function(grid,rowIndex,colIndex){
						var rowModel =grid.getStore().getAt(rowIndex);
						var params ={'vo':{'expressLineScheduleEntity':{'lineName':rowModel.data.lineName,'programName':rowModel.data.programName}}};
						Ext.Ajax.request({
							jsonData:params,
							url:baseinfo.realPath('queryDeptGisIdsByLineName.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								//得到该线路部门GisId数组
								var orgGisList =json.vo.orgGisList;
								var mainGisShow =me.getMainGisShowWindow();
								mainGisShow.city =rowModel.data.cityName, 
								mainGisShow.orgGisList =orgGisList;
								mainGisShow.show();
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				}]
			},{
				dataIndex:'originDeptName',
				text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.startingSector')//出发部门
			},{
				dataIndex:'arriveDeptName',
				text:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.reachDepartment')//到达部门
			},{
				dataIndex:'travelDistance',
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.travelDistance')//行驶距离(千米)
			},{
				dataIndex:'departTime',
				format:'H:i',
				xtype: 'datecolumn',   
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.departTime')//出发时间
			},{
				dataIndex:'travelTime',
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.travelTime')//行使时效
			},{
				dataIndex:'arriveTime',
				format:'H:i',
				xtype: 'datecolumn',   
				text:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.arriveTime')//结束时间
			}];
		me.store =Ext.create('Foss.baseinfo.expressTrainSchedule.LineScheduleStore',{
			autoLoad:false,
			pagSize:10,
			listeners:{
				beforeload:function(store,operation,eOpts){
					var queryGrid =Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid();
					var programName =null;
					//若操作类型是修改
					if(me.operateType =='update'){
						var updateProgramWin =queryGrid.getUpdateProgramWin();
						if(!Ext.isEmpty(updateProgramWin.expressTrainProgramModel.data)){
							programName =updateProgramWin.expressTrainProgramModel.data.programName;
						}
					//若操作类型是查询
					}else if(me.operateType =='query'){
						var queryProgramWin =queryGrid.getQueryProgramWin();
						if(!Ext.isEmpty(queryProgramWin.expressTrainProgramModel.data)){
							programName =queryProgramWin.expressTrainProgramModel.data.programName;
						}
					}
					if(!Ext.isEmpty(programName)){
						Ext.apply(operation,{
								params:{
									'vo.expressLineScheduleEntity.programName':programName
								}
							});
					}
				},
				//当Store中的现有数据有任何形式的更改时触发此事件 - 包括了添加、删除数据, 或更改现有数据事件
				dataChanged:function(store,eOpts){
					var queryGrid =Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid();
					var lineCount =0, vehicleCount=0,salesCount =0;
					var model =null;
					//若操作类型是修改
					if(me.operateType =='update'){
						var updateProgramWin =queryGrid.getUpdateProgramWin();
						model =updateProgramWin.expressTrainProgramModel;
					//若操作类型是查询
					}else if(me.operateType =='query'){
						var queryProgramWin =queryGrid.getQueryProgramWin();
						model =queryProgramWin.expressTrainProgramModel;
					}
					//判断store 不为空,而且要求里面有记录数
					if((!Ext.isEmpty(store))&&store.getCount()>0){
						lineCount =model.data.lineCount - me.deleteLineCount;
						vehicleCount =model.data.vehicleCount - me.deleteLineCount;
						salesCount =model.data.salesDeptCount
					}
					//设置挂件的值
					var  textArray=me.getDockedItems('toolbar')[0].items.items;
					textArray[0].setValue(lineCount);
					textArray[1].setValue(vehicleCount);
					textArray[2].setValue(salesCount);
				}
			}
			
		});
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
		me.bbar=me.getPagingToolbar();
		me.getPagingToolbar().store =me.store;
		me.callParent([cfg]);
	}
});
/**
* ---------GIS：网点电子地图窗口-----------------------------------------------
*/
Ext.define('Foss.baseinfo.expressTrainSchedule.MainGisWindow',{
extend:'Ext.window.Window',						
closeAction:'hide',
width:700,
city:null,
orgGisList:new Array(),
parent:null,//Foss.baseinfo.VehicleAgencyDeptWinForm
constructor : function(config) {
	var me = this, 
	cfg = Ext.apply({}, config);
	me.items = [{
		xtype: 'container',
		height:700
		}];
	me.callParent([cfg]);
	},
	height:700
	});
/**
 * ---------------------------------修改线路信息-----------------------------------------
 */
Ext.define('Foss.baseinfo.expressTrainSchedule.UpdateExpressLineScheduleForm',{
	extend:'Ext.form.Panel',
	title : '线路时刻表信息',// 输入参数
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 80,
		width : 100
	},
	margin: '0 0 0 0',
	height :260,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items=[{
			name:'lineName',
			fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.lineName'),//线路名称
			columnWidth:0.33,
			readOnly:true
		},{
			name:'programName',
			fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.programName'),//方案名称
			columnWidth:0.33,
			readOnly:true
		},{
			name:'cityCode',
			xtype:'commoncityselector',
			fieldLabel:baseinfo.expressTrainSchedule.i18n('foss.baseinfo.airagencycompany.city'),//城市
			columnWidth:0.33,
			readOnly:true
		},{
			xtype:'dynamicorgcombselector',
			name:'originDeptCode',
			fieldLabel: baseinfo.expressTrainSchedule.i18n('foss.baseinfo.startingSector'),//出发部门
			columnWidth:0.33,
			type : 'ORG',
			readOnly:true
		},{
			xtype:'dynamicorgcombselector',
			name:'arriveDeptCode',
			fieldLabel: baseinfo.expressTrainSchedule.i18n('foss.baseinfo.reachDepartment'),//到达部门
			columnWidth:0.33,
			type : 'ORG',
			readOnly:true
		},{
			xtype:'numberfield',
			name:'travelDistance',
			fieldLabel: '行驶距离',//行驶距离
			minValue:0,
			maxValue:99999,
			columnWidth:0.29,
		},{
			xtype:'label',
			columnWidth:0.04,
			align: 'middle',
			margin: '5 2 0 2',
			text:'KM'
		},{
			fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.departTime'),//出发时间
	        xtype: 'timefield',
	        name:'departTime',
	        //时间间隔，以分钟为单位。
	        increment: 60,
	        format:'H:i',
	        columnWidth:0.33
		},{
			fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.arriveTime'),//到达时间
	        xtype: 'timefield',
	        name:'arriveTime',
	        //时间间隔，以分钟为单位。
	        increment: 60,
	        format:'H:i',
	        columnWidth:0.33
		},{
			xtype:'textfield',
			name:'travelTime',
			regex:/^[-\+]?\d+(\.\d{1,2})?$/,
			fieldLabel: baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.travelTime'),//行驶距离
			columnWidth:0.45
		},{
			xtype:'label',
			columnWidth:0.05,
			align: 'middle',
			margin: '5 2 0 2',
			text:'小时'
		},{
			xtype:'container',
			defaultType:'button',
			columnWidth:1,
			layout:'column',
			items:[{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.8
			},{
				text:'重置',
				columnWidth:0.1,
				handler:function(){
					var model =me.up().lineScheduleModel;
					me.getForm().loadRecord(model);
					me.getForm().findField('cityCode').setCombValue(model.get('cityName'),model.get('cityCode'));
					me.getForm().findField('originDeptCode').setCombValue(model.get('originDeptName'),model.get('originDeptCode'));
					me.getForm().findField('arriveDeptCode').setCombValue(model.get('arriveDeptName'),model.get('arriveDeptCode'));
				}
			},{
				text:'保存',
				columnWidth:0.1,
				handler:function(){
					if(me.getForm().isValid()){
						var model =me.up().lineScheduleModel;
						me.getForm().updateRecord(model);
						var departTime =me.getForm().findField('departTime').value;
						var arriveTime =me.getForm().findField('arriveTime').value;
						//定义两个用来判断
						 var getStartTime =departTime.getHours(); 
					     var getEndTime = arriveTime.getHours();
						if(getEndTime<getStartTime){
							baseinfo.showErrorMes("到达时间不能小于出发时间");
							return ;
						}else if((getEndTime==getStartTime)&&(departTime.getMinutes()>arriveTime.getMinutes())){
							baseinfo.showErrorMes("到达时间不能小于出发时间");
							return ;
						}
						var params ={'vo':{'expressLineScheduleEntity':model.data}};
						Ext.Ajax.request({
							jsonData:params,
							url:baseinfo.realPath('updateExpressLineSchedule.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showInfoMes(json.message);
								var queryGrid =Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid().getUpdateProgramWin().getLineScheduleGrid();
								me.up().close();
								//重新查询grid
								queryGrid.getPagingToolbar().moveFirst();
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
					}
				}
			}]
		}];
		me.callParent([cfg])
	}
});
Ext.define('Foss.baseinfo.expressTrainSchedule.UpdateExpressLineScheduleWin',{
	extend:'Ext.window.Window',
	title : '修改方案线路信息',//修改方案线路信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	width :700,
	height :'auto',
	lineScheduleModel:null,
	listeners:{
		beforeshow:function(me){
			if(!Ext.isEmpty(me.lineScheduleModel)){
				var model =me.lineScheduleModel;
				me.getUpdateExpressLineForm().getForm().loadRecord(model);
				me.getUpdateExpressLineForm().getForm().findField('cityCode').setCombValue(model.get('cityName'),model.get('cityCode'));
				me.getUpdateExpressLineForm().getForm().findField('originDeptCode').setCombValue(model.get('originDeptName'),model.get('originDeptCode'));
				me.getUpdateExpressLineForm().getForm().findField('arriveDeptCode').setCombValue(model.get('arriveDeptName'),model.get('arriveDeptCode'));
			}
		},
		beforehide:function(me){
			me.getUpdateExpressLineForm().getForm().reset();
		}
	},
	updateExpressLineForm:null,
	getUpdateExpressLineForm:function(){
		if(this.updateExpressLineForm ==null){
			this.updateExpressLineForm =Ext.create('Foss.baseinfo.expressTrainSchedule.UpdateExpressLineScheduleForm');
		}
		return this.updateExpressLineForm;
	},
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items=[me.getUpdateExpressLineForm()];
		me.callParent([cfg])
	}
});
/**
 * -----------------------------------Add/Update/showDetail Program WINDOW---------------------------------------
 */
//新增方案
Ext.define('Foss.baseinfo.expressTrainSchedule.AddProgramWin',{
	extend:'Ext.window.Window',
	title : baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.addProgram'),//新增方案
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	width :800,
	height :'auto',
	listeners:{
		beforeshow:function(me){
			//设置作废按钮为不可编辑
			var  textArray=me.getLineScheduleGrid().getDockedItems('toolbar')[0].items.items;
			textArray[3].setDisabled(true);
		},
		beforehide:function(me){
			me.getAddLineScheduleForm().getForm().reset();
			me.getAddLineScheduleForm().salesDepartments =new Array();
			me.getLineScheduleGrid().getStore().removeAll();
			//设置作废按钮为不可编辑
			var  textArray=me.getLineScheduleGrid().getDockedItems('toolbar')[0].items.items;
			textArray[3].setDisabled(false);
		}
	},
	addLineScheduleForm:null,
	getAddLineScheduleForm:function(){
		if(this.addLineScheduleForm ==null){
			this.addLineScheduleForm =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramForm',{
				isUpdate:false
			});
		}
		return this.addLineScheduleForm;
	},
	lineScheduleGrid:null,
	getLineScheduleGrid:function(){
		if(this.lineScheduleGrid ==null){
		 this.lineScheduleGrid =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramGrid',{
			 operateType:'query',
		 });
		}
		return this.lineScheduleGrid;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[me.getAddLineScheduleForm(),me.getLineScheduleGrid()];
		me.callParent([cfg])
	}
});
//修改方案
Ext.define('Foss.baseinfo.expressTrainSchedule.UpdateProgramWin',{
	extend:'Ext.window.Window',
	title : baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.UpdateProgram'),//修改方案
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	expressTrainProgramModel:null,
	width :800,
	height :'auto',
	listeners:{
		'beforeshow':function(me){
			if(!Ext.isEmpty(me.expressTrainProgramModel)){
				var model =me.expressTrainProgramModel;
				me.getUpdateLineScheduleForm().getForm().loadRecord(model);
				me.getUpdateLineScheduleForm().getForm().findField('originOutfieldCode').setCombValue(model.data.originOutfieldName,model.data.originOutfieldCode);
				me.getUpdateLineScheduleForm().getForm().findField('cityCode').setCombValue(model.get('cityName'),model.get('cityCode'));
				me.getUpdateLineScheduleForm().getForm().getFields().each(function(item){
					item.setReadOnly(true);
				});
				me.getUpdateLineScheduleForm().query('button')[0].setDisabled(true);
				me.getUpdateLineScheduleForm().query('button')[1].setDisabled(true);
				me.getUpdateLineScheduleForm().query('button')[2].setDisabled(true);
				me.getLineScheduleGrid().getPagingToolbar().moveFirst();
			}
		},
		'beforehide':function(me){
			me.getUpdateLineScheduleForm().getForm().reset();
			me.getUpdateLineScheduleForm().getForm().getFields().each(function(item){
				item.setReadOnly(false);
			});
			me.getUpdateLineScheduleForm().query('button')[0].setDisabled(false);
			me.getUpdateLineScheduleForm().query('button')[1].setDisabled(false);
			me.getUpdateLineScheduleForm().query('button')[2].setDisabled(false);
			me.getUpdateLineScheduleForm().salesDepartments =new Array();
			me.getLineScheduleGrid().getStore().removeAll();
		}
	},
	updateLineScheduleForm:null,
	getUpdateLineScheduleForm:function(){
		if(this.updateLineScheduleForm ==null){
			this.updateLineScheduleForm =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramForm',{
				isUpdate:true
			});
		}
		return this.updateLineScheduleForm;
	},
	lineScheduleGrid:null,
	getLineScheduleGrid:function(){
		if(this.lineScheduleGrid ==null){
		 this.lineScheduleGrid =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramGrid',{
			 operateType:'update',
		 });
		}
		return this.lineScheduleGrid;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[me.getUpdateLineScheduleForm(),me.getLineScheduleGrid()];
		me.callParent([cfg])
	}
});
//查询详情方案
Ext.define('Foss.baseinfo.expressTrainSchedule.QueryProgramWin',{
	extend:'Ext.window.Window',
	title : baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.QueryProgram'),//修改方案
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'auto',
		align : 'stretch'
	},
	expressTrainProgramModel:null,
	programName:null,
	width :800,
	height :'auto',
	listeners:{
		'beforeshow':function(me){
			if(!Ext.isEmpty(me.expressTrainProgramModel)){
				var model =me.expressTrainProgramModel;
				me.getQueryLineScheduleForm().getForm().loadRecord(model);
				me.getQueryLineScheduleForm().getForm().findField('originOutfieldCode').setCombValue(model.get('originOutfieldName'),model.get('originOutfieldCode'));
				me.getQueryLineScheduleForm().getForm().findField('cityCode').setCombValue(model.get('cityName'),model.get('cityCode'));
				me.getQueryLineScheduleForm().getForm().getFields().each(function(item){
					item.setReadOnly(true);
				});
				me.getQueryLineScheduleForm().query('button')[0].setDisabled(true);
				me.getQueryLineScheduleForm().query('button')[1].setDisabled(true);
				me.getQueryLineScheduleForm().query('button')[2].setDisabled(true);
				me.getLineScheduleGrid().getPagingToolbar().moveFirst();
				
				//设置作废按钮不可编辑
				var  textArray=me.getLineScheduleGrid().getDockedItems('toolbar')[0].items.items;
				textArray[3].setDisabled(true);
			}
		},
		'beforehide':function(me){
			me.getQueryLineScheduleForm().getForm().reset();
			me.getQueryLineScheduleForm().getForm().getFields().each(function(item){
				item.setReadOnly(false);
			});
			me.getQueryLineScheduleForm().query('button')[0].setDisabled(false);
			me.getQueryLineScheduleForm().query('button')[1].setDisabled(false);
			me.getQueryLineScheduleForm().query('button')[2].setDisabled(false);
			me.getQueryLineScheduleForm().salesDepartments =new Array();
			me.getLineScheduleGrid().getStore().removeAll();
			var  textArray=me.getLineScheduleGrid().getDockedItems('toolbar')[0].items.items;
			textArray[3].setDisabled(false);
		}
	},
	queryLineScheduleForm:null,
	getQueryLineScheduleForm:function(){
		if(this.queryLineScheduleForm ==null){
			this.queryLineScheduleForm =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramForm',{
				isUpdate:true
			});
		}
		return this.queryLineScheduleForm;
	},
	lineScheduleGrid:null,
	getLineScheduleGrid:function(){
		if(this.lineScheduleGrid ==null){
		 this.lineScheduleGrid =Ext.create('Foss.baseinfo.expressTrainSchedule.AddUpdateProgramGrid',{
			 operateType:'query',
		 });
		}
		return this.lineScheduleGrid;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[me.getQueryLineScheduleForm(),me.getLineScheduleGrid()];
		me.callParent([cfg])
	}
});

/**
 * --------------------------------------Add SaleDept Info Module--------------------------------------------
 */
//添加营业部的Form
Ext.define('Foss.baseinfo.expressTrainSchedule.AddSaleDeptForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.expressTrainSchedule.i18n(''),// 
	frame : true,
	collapsible : true,
	margin:'0 5 0 5',
	defaults: {
		readOnly : false,
		anchor: '100%',
		labelWidth : 80,
		width : 100
	},
	height :'auto',
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 3
	},
	//外场关联营业部
	salesDepts:new Array(),
	//可选营业部
	conSelectSales:null,
	getConSelectSales:function(){
		if(this.conSelectSales==null){
			this.conSelectSales =Ext.create('Foss.baseinfo.expressTrainSchedule.ConSelectSales');
		}
		return this.conSelectSales;
	},
	//操作按钮
	selectionButton:null,
	getSelectionButton:function(){
		if(this.selectionButton==null){
			this.selectionButton =Ext.create('Foss.baseinfo.expressTrainSchedule.selectionButton');
		}
		return this.selectionButton;
	},
	//已选营业部
	hasSelectedSales:null,
	getHasSelectedSales:function(){
		if(this.hasSelectedSales==null){
			this.hasSelectedSales =Ext.create('Foss.baseinfo.expressTrainSchedule.SelectedSales');
		}
		return this.hasSelectedSales;
	},
	constructor:function(config){
		var me =this,cfg=Ext.apply({},config);
		me.items=[{
			xtype:'dynamicorgcombselector',
			name:'outfield',
			type : 'ORG',
		    transferCenter:'Y',//--或者查询外场
			width:250,
			fieldLabel:baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.originOutfield'),
			colspan:3,
			readOnly:true,
			listeners:{
				change:function(_this,newV,oldV,eOpts){
					if(newV){
						var params ={'vo.expressTrainProgramDto.expressTrainProgramEntity.originOutfieldCode':newV}
						Ext.Ajax.request({
							params:params,
							url:baseinfo.realPath('querySalesByOutfield.action'),
							success:function(response){
								var json =Ext.decode(response.responseText);
								var deptData =json.vo.orgList;
								var salesdepts =new Array();
								if(deptData.length >0){
									for (var i = 0; i < deptData.length; i++) {
										//给选择框加入三个值，（编码，名称）
										var data ={'name':deptData[i].name,'code':deptData[i].code,'depCoordinate':deptData[i].depCoordinate};
										salesdepts.push(data);
									}
									me.salesDepts =salesdepts;
									me.getConSelectSales().getStore().removeAll();
									me.getConSelectSales().getStore().loadData(salesdepts);
								}
							},
							exception:function(response){
								var json =Ext.decode(response.responseText);
							}
						});
					}
				}
			}
		},me.getConSelectSales(),me.getSelectionButton(),me.getHasSelectedSales(),{
			xtype:'container',
			defaultType:'button',
			layout:{
				type:'table',
				columns: 3
			},
			colspan:3,
			items:[{
				  text : '重置',//重置
				  width:50,
				  handler : function() {
					  me.getConSelectSales().getStore().removeAll();
					  me.getHasSelectedSales().getStore().removeAll();
					  me.getConSelectSales().getStore().loadData(me.salesDepts);
					}
			  	},{
					xtype:'container',
					border:false,
					width:300,
					html:'&nbsp;',
				},{
				  text : '确定',
				  width:50,
				  cls:'yellow_button',  
				  handler:function() {
					  //获取store
					var store = me.getHasSelectedSales().getStore();
					var addForm =Ext.getCmp('T_baseinfo-expressTrainSchedule_content').getQueryGrid().getAddProgramWin().getAddLineScheduleForm();
					addForm.getForm().findField('salesText').setValue('');
					if(store.data.items.length==0){
						baseinfo.showInfoMes('请选择营业部 ！');
						return ;
					}
					/*if(store.data.items.length>100){
						baseinfo.showInfoMes('营业部不能超过100个 ！');
						return ;
					}*/
					var salesDepts =new Array();
					var salesText ='';
					store.data.each(function(item){
						var salesDept =new Object();
						salesDept.code =item.data.code;
						salesDept.name =item.data.name;
						salesText +=item.data.name+',';
						salesDepts.push(salesDept);
					})
					addForm.salesDepartments =salesDepts;
					addForm.getForm().findField('salesText').setValue(salesText);
					baseinfo.showInfoMes('成功导入部门资料 ！');
					me.up('window').close();
				  }
			  	}]
		}]
		me.callParent([cfg]);
	}
});
//可选营业部
Ext.define('Foss.baseinfo.expressTrainSchedule.ConSelectSales',{
	extend:'Ext.grid.Panel',
	sortableColumns:false,
   	enableColumnHide:false,
   	enableColumnMove:false,
    columns: [
       { header:'已选营业部',dataIndex: 'name' ,titlehidden:true,flex:1},//可选营业部
       { hidden:true, dataIndex: 'code'},
       { hidden:true,dataIndex:'depCoordinate'}
    ],
   height: 250,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.tbar =[{
			xtype:'textfield',
			labelWidth : 60,
			fieldLabel:'部门名称',
			name:'searchOrgName',
			width:140,
		},{
			xtype:'button',
			text:'查询',
			width:50,
			handler:function(){
				var store=me.getStore();
				var textArray=me.getDockedItems('toolbar')[0].items.items;
				var searchOrgName =textArray[0].getValue();
				var flag =false;
				var searchOrg =null;
				store.data.each(function(item){
					if(item.data.name ==searchOrgName){
						flag =true;
						searchOrg =item;
					}
				});
				if(flag){
					store.remove(searchOrg);
					store.insert(0,searchOrg);
					var cells = me.getView().getNodes()[0].children;
					cells[0].style.backgroundColor='#ff0000';
				}else{
					baseinfo.showInfoMes('没有该营业部，请按营业部名称查询 ！');
					return;
				}
			}
		}];
		me.store = baseinfo.getStore(null,null,['name','code','depCoordinate'],[]),
		me.callParent([cfg]);
	}
});
//已选营业部
Ext.define('Foss.baseinfo.expressTrainSchedule.SelectedSales',{
	extend:'Ext.grid.Panel',
	sortableColumns:false,
   	enableColumnHide:false,
   	enableColumnMove:false,
   columns: [
       { header:'可选营业部',dataIndex: 'name' ,titlehidden:true,flex:1},//可选营业部
       { hidden:true, dataIndex: 'code'},
       { hidden:true,dataIndex:'depCoordinate'}
   ],
   height: 250,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store = baseinfo.getStore(null,null,['name','code','depCoordinate'],[]),
		me.callParent([cfg]);
	}
});
//出发按钮panel
Ext.define('Foss.baseinfo.expressTrainSchedule.selectionButton', {
   extend:'Ext.panel.Panel',
   height: 200,
   width: 80,
   //右移全部
   rightMoveAll:function(){
	   var me = this;
	   var  rightStore = me.up().getHasSelectedSales().getStore();
	   var  leftStore = me.up().getConSelectSales().getStore();
	   var errorText ='';
	   var flag =false;
	   //校验部门编码是否为空
	   leftStore.each(function(record){
		   if(Ext.isEmpty(record.get('depCoordinate'))){
			   errorText +=record.data.name+',';
			   flag =true;
		   }
	   });
	   if(flag){
		   baseinfo.showInfoMes('存在营业部'+errorText+'的服务坐标编码为空，请先去行政组织中描点');
		   return ;
	   }
	   leftStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code'),'depCoordinate':record.get('depCoordinate')};
		   rightStore.add(moveRecord);
	   });
	   leftStore.removeAll();
   },
   //右移
   rightMove:function(){
	   var me = this; 
	   var  rightStore = me.up().getHasSelectedSales().getStore();
	   var  selections = me.up().getConSelectSales().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   if(Ext.isEmpty(selections[0].get('depCoordinate'))){
		   baseinfo.showInfoMes('该营业部'+selections[0].get('name')+'的服务坐标编码为空，请先去行政组织中描点');
		   return ;
	   }
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code'),'depCoordinate':selections[0].get('depCoordinate')};
	   rightStore.add(moveRecord);
	   me.up().getConSelectSales().getStore().remove(selections);
   },
    //左移全部
   leftMoveAll:function(){
	   var me = this; 
	   var  rightStore = me.up().getHasSelectedSales().getStore();
	   var  leftStore = me.up().getConSelectSales().getStore();
	   rightStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code'),'depCoordinate':record.get('depCoordinate')};
		   leftStore.add(moveRecord);
	   });
	   rightStore.removeAll();
   },
   //左移
   leftMove:function(){
	   var me = this; 
	   var  selections = me.up().getHasSelectedSales().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var  leftStore = me.up().getConSelectSales().getStore();
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code'),'depCoordinate':selections[0].get('depCoordinate')};
	   leftStore.add(moveRecord);
	   me.up().getHasSelectedSales().getStore().remove(selections);
   },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.defaults ={
				xtype:'button',
				width:60,
				//disabled:true,
				height:20,
				margin:'8 0 0 10'
		};
		me.items = [{
			 text: '-->>',
			 margin:'20 0 0 10',
		     handler: function() {
		    	 me.rightMoveAll();
		     }
		},{
			text: '-->',
		     handler: function() {
		        me.rightMove();
		     }
		},{
			text: '<--',
		     handler: function() {
		    	 me.leftMove();
		     }
		},{
			text: '<<--',
		     handler: function() {
		    	 me.leftMoveAll();
		     }
		}]
		me.callParent([cfg]);
	}
});
//添加营业部window
Ext.define('baseinfo.expressTrainSchedule.AddSaleDeptWin',{
	extend:'Ext.window.Window',
	title : baseinfo.expressTrainSchedule.i18n('baseinfo.expressTrainSchedule.addOrg'),//新增部门
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width:550,
	height: 420,
	originOutfield:new Object(),
	listeners:{
		beforeshow:function(){
			var me =this;
			if(Ext.isEmpty(me.originOutfield)){
				return;
			}
			me.getAddSaleDeptForm().getForm().findField('outfield').setCombValue(me.originOutfield.Name,me.originOutfield.Code);
		},
		beforehide:function(){
			var me =this;
			 me.getAddSaleDeptForm().getForm().reset();
			 me.getAddSaleDeptForm().getConSelectSales().getStore().removeAll();
			 me.getAddSaleDeptForm().getHasSelectedSales().getStore().removeAll();
		}
	},
	addSaleDeptForm:null,
	getAddSaleDeptForm:function(){
		if(this.addSaleDeptForm ==null){
			this.addSaleDeptForm =Ext.create('Foss.baseinfo.expressTrainSchedule.AddSaleDeptForm');
		}
		return this.addSaleDeptForm;
	},
	constructor:function(config){
	 	var me =this,cfg =Ext.apply({},config);
	 	me.items=[me.getAddSaleDeptForm()];
	 	me.callParent([cfg]);
	}
});
/**
 * ----程序入口----
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressTrainSchedule_content')) {
		return;
	}
	var queryForm =Ext.create('Foss.baseinfo.expressTrainSchedule.QueryForm');
	var queryGrid =Ext.create('Foss.baseinfo.expressTrainSchedule.QueryGrid');
	Ext.getCmp('T_baseinfo-expressTrainSchedule').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-expressTrainSchedule_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm : function(){
			return queryForm;
		},
		getQueryGrid : function(){
			return queryGrid;
		},
		items: [queryForm,queryGrid]
	}));
});