//空运跟踪信息model
Ext.define('foss.airfreight.trackFlightModel', {
	extend: 'Ext.data.Model',
	idProperty : 'id',
	//定义字段
	fields: [
		{name: 'airWaybillId',type:'string'}, // ID编号
		{name: 'airWaybillNo',type:'string'}, //正单号
		{name: 'airLineTwoletter',type:'string'}, //航空公司
		{name: 'airAssembleDeptCode',type:'string'}, //配载部门
		{name: 'airAssembleDeptName',type:'string'}, //配载部门
		{name: 'flightDate',type:'string',		//航班日期
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}else{
					return v;
				}
		}},
	    {name: 'flightNo', type: 'string'},	//航班号
	    {name: 'arrvRegionCode', type: 'string'},	//目的站
	    {name: 'arrvRegionName', type: 'string'},	//目的站名称
	    {name: 'actualTakeOffTime',type:'string',		//实飞时间
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
	    },
		{name: 'actualArriveTime',type:'string',		//实到时间
			convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
	    },
	    {name: 'takeOffTime',type:'string',
	    	convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
	    },
	    {name: 'arriveTime',type:'string',
	    	convert: function(v){
				if(v!=null){
					var date = new Date(v);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
	    },
	    {name: 'trackState', type: 'string'},	//跟踪状态
	    {name: 'message', type: 'string',
			convert: function(v){
				if(v == 'null'){
					return '';
				}else{
					return v
				}
			}
	    }	//跟踪信息
	]
});

//航班库存
Ext.define('foss.airfreight.trackFlightStore',{
		extend: 'Ext.data.Store',
		model: 'foss.airfreight.trackFlightModel',
		pageSize:10,
		proxy: {
      	type: 'ajax',
        url: airfreight.realPath('queryAirTrackFlight.action'),
        actionMethods: {read: 'POST'},
        reader: {
            type: 'json',
            root: 'vo.dtoList',
            totalProperty : 'totalCount',
            successProperty: 'success'
        }
    },
    listeners: {
		//store加载前调用，用于传入查询参数
		beforeload : function(store, operation, eOpts) {
			var queryParams = airfreight.trackFlightQueryForm.getValues();
			Ext.apply(operation,{
				params :{
    				'vo.airLineTwoletter' : queryParams.airLineTwoletter,  //航空公司二字码
					'vo.flightNo' : queryParams.flightNo, //航班号
					'vo.airAssembleDeptCode' : queryParams.airAssembleDeptCode,	//空运总调
					'vo.arrvRegionCode' : queryParams.arrvRegionCode, //目的站
					'vo.flightDate' : queryParams.flightDate, //航班日期
					'vo.trackState' :queryParams.trackState, //跟踪状态
					'vo.airWaybillNo' :queryParams.airWaybillNo //正单号
				}
			});
		},
		load: function(store,records,successful,epots){
			Ext.Array.each(airfreight.gridBtns, function(item, index){
				item.enable();
			});
		}
    }
});

//查询条件
Ext.define('foss.airfreight.trackQueryForm', {
		extend:'Ext.form.Panel',
		// 指定容器的标题
		title: airfreight.airTrackFlight.i18n('foss.airfreight.queryCondition'),//查询条件
		id: 'foss_airfreight_trackQueryForm_ID',
		frame: true,
		//收缩
		collapsible: true,
		//动画收缩
		animCollapse: true, 
		//默认边距 间隔
		defaults: {
			margin: '5 10 5 10',   //四边距  上右下左
			msgTarget: 'side',
			labelWidth: 80
		},
		// 指定布局是column
		layout: 'column',
		//默认控件是'textfield'
		defaultType: 'textfield',
		// 定义容器中的项
		items: [{
			name:'airLineTwoletter',
			xtype:'commonairlinesselector',
			displayField : 'code',// 显示名称
			valueField : 'code',// 值 
			allowBlank:false,
			fieldLabel:airfreight.airTrackFlight.i18n('foss.airfreight.airLineTwoletter'),//航空公司
			value:'CZ',
			labelWidth:60,
			columnWidth: .25
		},{
		    xtype:'commonflightselector',
			name:'flightNo',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.flightNo'),//航班号
			columnWidth: .25
		},{
			xtype: 'dynamicorgcombselector',
			name:'airAssembleDeptCode',
			airDispatch: 'Y',
			type:'ORG',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.airAssembleDeptCode'),//空运总调
			allowBlank:false,
			readOnly:true,
			columnWidth: .25
		},{
			xtype:'commoncityselector',
			name:'arrvRegionCode',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.arrvRegionCode'),//目的站
			columnWidth: .25
		},{
			xtype: 'datetimefield_date97',
			id: 'foss_airfreight_trackQueryForm_flightDate',
			name: 'flightDate',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.flightDate'),//航班日期
			allowBlank:false,
			altFormats: 'Y,m,d|Y.m.d',
	  		format: 'Y-m-d',
	  		invalidText: airfreight.airTrackFlight.i18n('foss.airfreight.dateFormIncorrect'),//输入的日期格式不对
			value:  Ext.Date.format(new Date(), 'Y-m-d'),
			time : false,
			dateConfig: {
				el : 'foss_airfreight_trackQueryForm_flightDate-inputEl'
			},
			columnWidth: .25	
		},
		FossDataDictionary.getDataDictionaryCombo('AIR_TRACK_STATE',{
			name: 'trackState',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.trackState'),//跟踪状态
			allowBlank:true,
			queryMode: 'local',
			forceSelection: true,
			editable: true,
			value: 'UN_TRACK',
			labelWidth: 80,
			columnWidth: .25
			},{
				'valueCode':'','valueName': airfreight.airTrackFlight.i18n('foss.airfreight.all')//全部
			})
		,{
			name:'airWaybillNo',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.airWaybillNo'),//正单号
			columnWidth: .25
		},{
			xtype: 'container',
			border : false,
			columnWidth:.25,
			html: '&nbsp;'
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:airfreight.airTrackFlight.i18n('foss.airfreight.reset'),//重置
				columnWidth:.08,
				handler:function(){
					//var cmbAssembleDeptCode = panel.getForm().findField('airAssembleDeptCode');
					this.up('form').getForm().reset();
					var cmbAssembleDeptCode = this.up('form').getForm().findField('airAssembleDeptCode');
					if(airfreight.dept.airDispatch == 'Y'){
						cmbAssembleDeptCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
						cmbAssembleDeptCode.setValue(airfreight.dept.code);
					}
				}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.835,
					html: '&nbsp;'
				},{
					text:airfreight.airTrackFlight.i18n('foss.airfreight.query'),//查询
					cls:'yellow_button',
					columnWidth:.08,
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						  //设定间隔秒数,如果不设置，默认为2秒
						  seconds: 2
					}),
					handler:function(){
						var form = this.up('form').getForm();
						if(form.isValid()){
							airfreight.airTrackPagingBar.moveFirst();
						}
				}
			}]
	}],
	listeners : {
		render : function(panel,text){
			var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
			Ext.Ajax.request({
				url : airfreight.realPath('queryAirDispatch.action'),
				jsonData:array,
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
					airfreight.dept = dept;
					var cmbAssembleDeptCode = panel.getForm().findField('airAssembleDeptCode');
					if(airfreight.dept.airDispatch == 'Y'){
						cmbAssembleDeptCode.getStore().load({params:{'commonOrgVo.name' : airfreight.dept.name}});
						cmbAssembleDeptCode.setValue(airfreight.dept.code);
					}
				}
			});
		}
	}
});


Ext.define('foss.airfreight.trackContentTip',{
	extend: 'Ext.form.Panel',
	height: 120,
	width: 280,
	//autoScroll: true,
	defaultType : 'textarea',
	layout:'fit',
	items:[{
		name:'message',
		fieldLabel: '',
		labelSeparator:'',
		hideMode: 'visibility',
		readOnly:true
	}],
	bindData : function(record,value,metadata,store,view){
		if(Ext.isEmpty(record.data.message)){
			return false;
		};
		//把使用后台拼接的|替换成换行符，每条跟踪信息一行
		record.data.message = record.data.message.replace(/\|/g,'\n');
		this.getForm().loadRecord(record);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//跟踪显示列表Grid
Ext.define('foss.airfreight.trackFlightGrid',{
    extend:'Ext.grid.Panel',
    title:airfreight.airTrackFlight.i18n('foss.airfreight.trackFlightGrid'),//显示列表
    id: 'foss_airfreight_trackFlightGrid_ID',
	//增加表格列的分割线
    columnLines: true,
    cls: 'autoHeight',
    frame: true,
    stripeRows: true,
    collapsible: true,
	animCollapse: true,
	emptyText: airfreight.airTrackFlight.i18n('foss.airfreight.queryNull'),//查询结果为空
	columns: [{
		//字段标题
		header: airfreight.airTrackFlight.i18n('foss.airfreight.airWaybillId'), //正单id
		dataIndex: 'airWaybillId',
		hidden : true
	},{
		//字段标题
		header: airfreight.airTrackFlight.i18n('foss.airfreight.airWaybillNo'), //正单号
		//关联model中的字段名
		dataIndex: 'airWaybillNo',
		flex:1.3				
	},{
		header: airfreight.airTrackFlight.i18n('foss.airfreight.takeOffTime'), //起飞时间
		//关联model中的字段名
		dataIndex: 'takeOffTime',
    	xtype : 'ellipsiscolumn',
		flex:1
	},{
		header: airfreight.airTrackFlight.i18n('foss.airfreight.arriveTime'), //到达时间
		//关联model中的字段名
		dataIndex: 'arriveTime',
    	xtype : 'ellipsiscolumn',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.airLineTwoletter'), //航空公司
		//关联model中的字段名
		dataIndex: 'airLineTwoletter',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.airAssembleDeptCode'), //配载部门
		//关联model中的字段名
		dataIndex: 'airAssembleDeptName',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.flightDate'), //航班日期
		//关联model中的字段名
		dataIndex: 'flightDate',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.flightNo'), //航班号
		//关联model中的字段名
		dataIndex: 'flightNo',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.arrvRegionCode'), //目的站
		//关联model中的字段名
		dataIndex: 'arrvRegionName',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.actualTakeOffTime'), //实飞时间
		//关联model中的字段名
		dataIndex: 'actualTakeOffTime',
		xtype: 'ellipsiscolumn',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.actualArriveTime'), //实到时间
		//关联model中的字段名
		dataIndex: 'actualArriveTime',
		xtype: 'ellipsiscolumn',
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.trackState'), //跟踪状态
		//关联model中的字段名
		dataIndex: 'trackState',
		renderer: function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_TRACK_STATE');
		},
		flex:1
	},{ 
		header: airfreight.airTrackFlight.i18n('foss.airfreight.trackContent'), //跟踪信息
		//关联model中的字段名
		dataIndex: 'message',
		xtype: 'tipcolumn',
		tipConfig: {
	        //Tip的Body是否随鼠标移动
			trackMouse: true,
			//Tip的隐藏延迟时间(单位:ms)
			hideDelay: 500
		},
		tipBodyElement: 'foss.airfreight.trackContentTip',
		flex:2.5
	}],
	listeners: {
		//在选择前判断是否所选的是同一跟踪状态，如果不致则提示，并停止选择
		beforeselect: function(model, record, index){
			var array = model.selected.items;
			if(array.length == 0){
				return true;
			}
			if(record.data.trackState != array[0].data.trackState){
				Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), airfreight.airTrackFlight.i18n('foss.airfreight.keepStateSame'));
				return false;
			}//提示信息    批量操作时请保持跟踪状态一致!
		},
		//在选择时根据跟踪状态判断按钮的可用性
		select: function(model, record, index){
			if(record.data.trackState == FossDataDictionary.rendererSubmitToDisplay('TRACKING','TRACK_STATE')){
				Ext.Array.each(airfreight.gridBtns, function(item, index){
					if(item.name == 'takeOffTrack'){
						item.disable();
					}else{
						item.enable();
					}
				});
			}else if(record.data.trackState == FossDataDictionary.rendererSubmitToDisplay('TRACKEND','TRACK_STATE')){
				Ext.Array.each(airfreight.gridBtns, function(item, index){
					item.disable();
				});
			}else{
				Ext.Array.each(airfreight.gridBtns, function(item, index){
					if(item.name == 'takeOffTrack'){
						item.enable();
					}else{
						item.disable();
					}
				});
			}
		},
		//在取消选择时恢复按钮的可用性
		deselect: function(model, record, index){
			var array = model.selected.items;
			if(array.length == 0){
				Ext.Array.each(airfreight.gridBtns, function(item, index){
					item.enable();
				});
			}
		}
	},
	constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			//设置列的复选框
			me.selModel = Ext.create('Ext.selection.CheckboxModel'),
			//构造表格中对应的store
			me.store = Ext.create('foss.airfreight.trackFlightStore');
			
			me.tbar=[{
				xtype: 'button', 
				text: airfreight.airTrackFlight.i18n('foss.airfreight.takeOffTrack'),//起飞跟踪
				name: 'takeOffTrack',
				handler: function() {
					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
					//判断是否选择
					if (selectRows.length==0){
						Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), airfreight.airTrackFlight.i18n('foss.airfreight.leastPickOne'));
						return;
					}//提示信息   请至少选择一行！'
					//获取弹出窗口   
				//BUG-51791 WEB-航班跟踪为待跟踪时仍然可以进行过程跟踪和到达跟踪
				//判断跟踪状态
					for(var i=0;i<selectRows.length;i++)
					{
						var selectRecord=selectRows[i];
						if(selectRecord.data.trackState!='UN_TRACK')
						{
							Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), '正单：'+selectRecord.data.airWaybillNo+' 不能做起飞跟踪');
							return;
						}
					}
	
					var tackOffTrackWindow = Ext.getCmp('foss_airfreight_takeOffTrackWindow_ID');
					//如果没有创建，则新增，否则直接show
					if(tackOffTrackWindow == null){
						tackOffTrackWindow = Ext.create('foss.airfreight.takeOffTrackWindow');
					}
					tackOffTrackWindow.center().show();
				}
			},{	
				xtype: 'button', 
				text: airfreight.airTrackFlight.i18n('foss.airfreight.processTrack'),//过程跟踪
				name: 'processTrack',
				handler: function(){
					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
					//判断是否选择
					if (selectRows.length==0){
						Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), airfreight.airTrackFlight.i18n('foss.airfreight.leastPickOne'));
						return;
					}//提示信息     请至少选择一行！
					//获取弹出窗口
					//BUG-51791 WEB-航班跟踪为待跟踪时仍然可以进行过程跟踪和到达跟踪
					//判断跟踪状态
						for(var i=0;i<selectRows.length;i++)
						{
							var selectRecord=selectRows[i];
							if(selectRecord.data.trackState!='TRACKING')
							{
								Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), '正单：'+selectRecord.data.airWaybillNo+' 不能做过程跟踪');
								return;
							}
						}
					
					var processTrackWindow = Ext.getCmp('foss_airfreight_processTrackWindow_ID');
					//如果没有创建，则新增，否则直接show
					if(processTrackWindow == null){
						processTrackWindow = Ext.create('foss.airfreight.processTrackWindow');
					}
					processTrackWindow.center().show();
				}
			},{
				xtype: 'button', 
				text: airfreight.airTrackFlight.i18n('foss.airfreight.arriveTrack'),//到达跟踪
				name: 'arriveTrack',
				handler: function(){
					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
					//判断是否选择
					if (selectRows.length==0){
						Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), airfreight.airTrackFlight.i18n('foss.airfreight.leastPickOne'));
						return;
					}//提示信息   请至少选择一行！
					//获取弹出窗口
					//BUG-51791 WEB-航班跟踪为待跟踪时仍然可以进行过程跟踪和到达跟踪
					//判断跟踪状态
						for(var i=0;i<selectRows.length;i++)
						{
							var selectRecord=selectRows[i];
							if(selectRecord.data.trackState!='TRACKING')
							{
								Ext.Msg.alert(airfreight.airTrackFlight.i18n('foss.airfreight.alert'), '正单：'+selectRecord.data.airWaybillNo+' 不能做到达跟踪');
								return;
							}
						}
					
					var arriveTrackWindow = Ext.getCmp('foss_airfreight_arriveTrackWindow_ID');
					//如果没有创建，则新增，否则直接show
					if(arriveTrackWindow == null){
						arriveTrackWindow = Ext.create('foss.airfreight.arriveTrackWindow');
					}
					arriveTrackWindow.center().show();
				}
			}];
			me.bbar =Ext.create('Deppon.StandardPaging',{
				store: me.store,
				pageSize:10,
				plugins: 'pagesizeplugin'
			});
			airfreight.airTrackPagingBar=me.bbar;
			me.callParent([cfg]);
		},
		listeners : {
			render : function(grid,opt){
				var btns = grid.dockedItems.items[2].query('button');
				Ext.Array.each(btns, function(item, index){
					item.hide();
				});
				if(airfreight.airTrackFlight.isPermission('airfreight/takeOffTrackButton')){
					btns[0].show();
				}
				if(airfreight.airTrackFlight.isPermission('airfreight/processTrackButton')){
					btns[1].show();
				}
				if(airfreight.airTrackFlight.isPermission('airfreight/arriveTrackButton')){
					btns[2].show();
				}
			}
		}
});


//起飞跟踪窗口
Ext.define('foss.airfreight.takeOffTrackWindow',{
	extend: 'Ext.window.Window',
	id: 'foss_airfreight_takeOffTrackWindow_ID',
	title: airfreight.airTrackFlight.i18n('foss.airfreight.takeOffTrack'),//起飞跟踪
	width: 400,
	closeAction: 'hide',
	layout: 'fit',
	items:[{
	    xtype:'form',
	    fieldDefaults: {
        msgTarget: 'side',
        margin:'5 10 5 10',
        labelWidth: 80
  	    },
  		items:[{
  			xtype: 'datetimefield_date97',
			name: 'actualTakeOffTime',
			id:'foss_airfreight_takeOffTrackWindow_actualTakeOffTime',
			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.actualTakeOffTime'),//实飞时间
			time : true,
			anchor: '70%',	
			altFormats: 'Y,m,d H:i:s|Y.m.d H:i:s',
	  		format: 'Y-m-d H:i:s',
	  		invalidText: airfreight.airTrackFlight.i18n('foss.airfreight.dateFormIncorrect'),//输入的日期格式不对'
			allowBlank: false,
			value:  Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			dateConfig: {
				el : 'foss_airfreight_takeOffTrackWindow_actualTakeOffTime-inputEl'
			}	
  		},{
  			xtype: 'textareafield',
  			name: 'message',
  			fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.trackContent'),//跟踪信息
  			maxLength: 900,
  			enforceMaxLength: true,
  			anchor: '100%'	
  		}],
  		buttons:[{
  			text:airfreight.airTrackFlight.i18n('foss.airfreight.confirm'),//确定
  			formBind: true,
  			handler: function(){
  				var window = this.up('window');
  				var baseForm = this.up('form').getForm();
  				if(baseForm.isValid()){
  					//获取选择的行
  					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
  					var array = new Array();
  					Ext.Array.each(selectRows, function(item, index){
  						array.push(item.data.airWaybillId);
  					});
  					var vals = baseForm.getValues();
  					//添加正单id数组
  					vals.airWaybillIds = array;
  					Ext.Ajax.request({
  	  					url: airfreight.realPath('takeOffTrack.action'),
  	  					jsonData: {vo: vals},
	  	  				success: function(response, opts) {
	  	  					Ext.ux.Toast.msg(airfreight.airTrackFlight.i18n('foss.airfreight.message'), airfreight.airTrackFlight.i18n('foss.airfreight.trackSuccess'), 'ok', 1000);
	  	  					airfreight.trackFlightGrid.store.load(); //提示   跟踪成功!
	  	  					window.close();
	  	  			    },
	  	  			    failure: function(response, opts) {
	  	  			    	Ext.Msg.alert('failure',airfreight.airTrackFlight.i18n('foss.airfreight.trackFlightFailure'));//起飞跟踪失败！
	  	  			    }
  	  				});
  				}
  			}
  		},{
  			text: airfreight.airTrackFlight.i18n('foss.airfreight.cancell'),//取消
  			handler: function() {
  				this.up('window').close();
  			}
  		}]
	}],
	listeners: {
		beforeshow: function(){
			var form = this.items.items[0];
			form.getForm().reset();
			form.items.items[0].setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		}
	}
});

//过程跟踪窗体
Ext.define('foss.airfreight.processTrackWindow',{
	extend: 'Ext.window.Window',
	id: 'foss_airfreight_processTrackWindow_ID',
	title: airfreight.airTrackFlight.i18n('foss.airfreight.processTrack'),//过程跟踪
	width: 400,
	closeAction: 'hide',
	items:[{
		xtype:'form',
		fieldDefaults: {
	    msgTarget: 'side',
	    margin:'5 10 5 10',
	    labelWidth: 80
		},
  		items:[{
  			xtype: 'textareafield',
  			name:'message',
  			allowBlank: false,
  			maxLength: 900,
  			enforceMaxLength: true,
  			fieldLabel:airfreight.airTrackFlight.i18n('foss.airfreight.trackContent'),//跟踪信息
  			anchor    : '100%'	
  		}],
  		buttons:[{
  			text:airfreight.airTrackFlight.i18n('foss.airfreight.confirm'),//确定
  			formBind: true,
  			handler: function(){
  				var window = this.up('window');
  				var baseForm = this.up('form').getForm();
  				if(baseForm.isValid()){
  					//获取选择的行
  					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
  					var array = new Array();
  					Ext.Array.each(selectRows, function(item, index){
  						array.push(item.data.airWaybillId);
  					});
  					var vals = baseForm.getValues();
  					//添加正单id数组
  					vals.airWaybillIds = array;
  					Ext.Ajax.request({
  	  					url: airfreight.realPath('processTrack.action'),
  	  					jsonData: {vo: vals},
	  	  				success: function(response, opts) {
	  	  					Ext.ux.Toast.msg(airfreight.airTrackFlight.i18n('foss.airfreight.message'), airfreight.airTrackFlight.i18n('foss.airfreight.processTrackSuccess'), 'ok', 1000);
	  	  					airfreight.trackFlightGrid.store.load(); //提示    过程跟踪成功!'
	  	  					window.close();
	  	  			    },
	  	  			    failure: function(response, opts) {
	  	  			    	Ext.Msg.alert('failure',airfreight.airTrackFlight.i18n('foss.airfreight.processTrackFailure'));//过程跟踪失败！
	  	  			    }
  	  				});
  				}
  			}
  		},{
  			text: airfreight.airTrackFlight.i18n('foss.airfreight.cancell'),//取消
  			handler: function() {
  				this.up('window').close();
  			}
  		}]
	}],
	listeners: {
		beforeshow: function(){
			var form = this.items.items[0];
			form.getForm().reset();
		}
	}
});

//到达跟踪窗体
Ext.define('foss.airfreight.arriveTrackWindow',{
	extend: 'Ext.window.Window',
	id: 'foss_airfreight_arriveTrackWindow_ID',
	title: airfreight.airTrackFlight.i18n('foss.airfreight.arriveTrack'),//到达跟踪
	width: 400,
	closeAction: 'hide',
	items:[{
		xtype:'form',
		fieldDefaults: {
		msgTarget: 'side',
		margin:'5 10 5 10',
		labelWidth: 80
  		},
  		items:[{
  			xtype: 'datetimefield_date97',
				name: 'actualArriveTime',
				id:'foss_airfreight_arriveTrackWindow_actualArriveTime',
				fieldLabel: airfreight.airTrackFlight.i18n('foss.airfreight.actualArriveTime'),//实到时间
				anchor: '70%',
				altFormats: 'Y,m,d|Y.m.d',
		  		format: 'Y-m-d H:i:s',
		  		invalidText: airfreight.airTrackFlight.i18n('foss.airfreight.dateFormIncorrect'),//输入的日期格式不对
				allowBlank: false,
				value:  Ext.Date.format(new Date(),'Y-m-d H:i:s'),
				time : true,
				dateConfig: {
					el : 'foss_airfreight_arriveTrackWindow_actualArriveTime-inputEl'
				}	
  		},{
  			xtype: 'textareafield',
  			name:'message',
  			maxLength: 900,
  			enforceMaxLength: true,
  			fieldLabel:airfreight.airTrackFlight.i18n('foss.airfreight.trackContent'),//跟踪信息
  			anchor    : '100%'	
  		}],
  		buttons:[{
  			text:airfreight.airTrackFlight.i18n('foss.airfreight.confirm'),//确定
  			formBind: true,
  			handler: function(){
  				var window = this.up('window');
  				var baseForm = this.up('form').getForm();
  				if(baseForm.isValid()){
  					//获取选择的行
  					var selectRows = airfreight.trackFlightGrid.getSelectionModel().getSelection();
  					var array = new Array();
  					Ext.Array.each(selectRows, function(item, index){
  						array.push(item.data.airWaybillId);
  					});
  					var vals = baseForm.getValues();
  					//添加正单id数组
  					vals.airWaybillIds = array;
  					Ext.Ajax.request({
  	  					url: airfreight.realPath('arriveTrack.action'),
  	  					jsonData: {vo: vals},
	  	  				success: function(response, opts) {
	  	  					Ext.ux.Toast.msg(airfreight.airTrackFlight.i18n('foss.airfreight.message'), airfreight.airTrackFlight.i18n('foss.airfreight.arrivalTrackSuccess'), 'ok', 1000);//到达跟踪成功!
	  	  					airfreight.trackFlightGrid.store.load(); //提示
	  	  					window.close();
	  	  			    },
	  	  			    failure: function(response, opts) {
	  	  			    	Ext.Msg.alert('failure',airfreight.airTrackFlight.i18n('foss.airfreight.arrivalTrackFailure'));//到达跟踪失败！
	  	  			    }
  	  				});
  				}
  			}
  		},{
  			text: airfreight.airTrackFlight.i18n('foss.airfreight.cancell'),//取消
  			handler: function() {
  				this.up('window').close();
  			}
  		}]
	}],
	listeners: {
		beforeshow: function(){
			var form = this.items.items[0];
			form.getForm().reset();
			form.items.items[0].setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		}
	}
});

airfreight.dept = '';

//初始化函数
Ext.onReady(function() {
	Ext.QuickTips.init();
	var trackFlightQueryForm = Ext.create('foss.airfreight.trackQueryForm');
	var trackFlightGrid = Ext.create('foss.airfreight.trackFlightGrid');
	airfreight.trackFlightQueryForm = trackFlightQueryForm;
	airfreight.trackFlightGrid = trackFlightGrid;
	airfreight.gridBtns = trackFlightGrid.dockedItems.items[1].query('button');
	Ext.create('Ext.panel.Panel',{
		id: 'T_airfreight-airTrackFlight_content',
		cls:'panelContent',
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items: [trackFlightQueryForm,trackFlightGrid],
		renderTo: 'T_airfreight-airTrackFlight-body'
	});
});
