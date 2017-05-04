Ext.define('Foss.load.lfDrivingFileModel',{
	extend:'Ext.data.Model',
	fields:[
		{name:'id',type:'String'},
		    //期间''}, 
	  {type:'String' ,name:'drivingDate'},
	  //所属车队编码code 
	  {type:'String' ,name:'orgIdCode'},
	  //所属车队名称 
	  {type:'String' ,name:'orgIdName'},
	  //行车编码  
	  {type:'String' ,name:'drivingNo'},
	  //车牌号
	  {type:'String' ,name:'vehicleNo'},
	  //车型
	  {type:'String' ,name:'vehicleType'},
	  //车型
	  {type:'String' ,name:'vehicleTypeName'},
	  //线路Code''},
	  {type:'String' ,name:'lineCode'},
	  //线路NAME''},
	  {type:'String' ,name:'lineName'},
	  //出发公里数''},
	  {type:'number', name:'departDistance'},
	  //到达公里数''},
	  {type:'number', name:'arriveDistance'},
	   //行驶公里数''},
	  {type:'number', name:'drivingDistance'},
	   //总油升数''},
	  {type:'number', name:'consumeFuelTotal'},
	   //总油费''}, 
	  {type:'number', name:'consumeFuelFeeTotal'},
	   //平均油价''},
	  {type:'number', name:'consumeFuelFee'},
	   //路桥费''}, 
	  {type:'number', name:'toolFeeTotal'},
	   //百公里油耗''},
	  {type:'number', name:'consumeFuel'},
	   //公里路桥费''},
	  {type:'number', name:'toolFee'},
	   //司机1code  
	  {type:'String' ,name:'driverCodeOne'},
	   //司机1name 
	  {type:'String' ,name:'driverNameOne'},
	  //司机1 code+name
	  {type:'String' ,name:'driverOne'},
	   //司机2code
	  {type:'String' ,name:'driverCodeTwo'},
	    //司机2 code+name
	  {type:'String' ,name:'driverTwo'},
	   //司机name 
	  {type:'String' ,name:'driverNameTwo'},
	   //最后操作人name  
	  {type:'String' ,name:'modifyUserName'},
	  //最后操作人 code+name
	  {type:'String' ,name:'modifyUser'},
	   //最后操作人code''},
	  {type:'String' ,name:'modifyUserCode'},
	   //最后操作时间
	  {type:'date', convert: dateConvert,name:'modifyTime',
	  convert: function(value) {
         if (!value) return '';
            var date = new Date(value);
         if (date == 'Invalid Date') {
               return value;
         }
          return Ext.Date.format(date, 'Y-m-d H:i:s');
         }
      },
	   //"线路途径外场Code
	  {type:'String' ,name:'lineTransferCode'}, 
	   //"线路途径外场NAME 
	  {type:'String' ,name:'lineTransferName'},
	   //制单人姓名''},
	  {type:'String' ,name:'createUserName'},
	   //制单人编码''},
	  {type:'String' ,name:'createUserCode'},
	   //是否有效（Y是N否）
	  {type:'String' ,name:'active'},
	   //是否空驶线路或中途换车头（Y是N否）''},
	  {type:'String' ,name:'isDeivingEmpty'},
	   //创建时间''},
	  {
	  type:'String',name:'note'//异常信息备注
	  },
	  {type:'date', convert: dateConvert,name:'createTime',
	  convert: function(value) {
		if(!value) return '';
		var date = new Date(value);	
			   //解决火狐不兼容
        if (date == 'Invalid Date') {
               return value;
         }
		var formatStr = 'Y-m-d H:i:s';
		return Ext.Date.format(date, formatStr);
	  }
	}
	]
});
//长途车辆行驶档案 查询form
Ext.define('Foss.load.lfDrivingFileForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	collapsible : true,
	animCollapse : true,
	title:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.titleForm'),//'查询条件',
	farme:true,
	defaults:{
		margin:'5 5 5 5',
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	items:[{
		//xtype:'monthdatefield',
		xtype: 'datefield',
		format:'Ym',
	    fieldLabel: load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingDate'),//期间',
        name:'drivingDate',
        allowBlank : false,
        fieldId : 'Foss_lfDrivingFile_QueryForm_drivingDate_fieldID',
		id : 'Foss_lfDrivingFile_QueryForm_drivingDate_ID',
		value : new Date()
	},{
		/*xtype:'dynamicorgcombselector',
		type:'ORG',*/
		xtype:'commonvehicledrivingselector',
		//transDepartment:'Y',
		name:'orgIdCode',
		fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.orgIdName')//'所属车队'
	},{
		fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingNo'),// =行车编码
		name:'drivingNo'
	},{
		xtype: 'rangeDateField',
		fieldLabel: '创建时间',
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_lfDrivingFile_CreateTime_Id',
		dateType: 'datetimefield_date97',
		dateRange: 15, //时间跨度不能大于10天
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank: false,
		columnWidth: .50
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.restButton'),//重置
			handler : function() {
				var form = this.up('form').getForm();
				var ss=form.findField('orgIdCode');
				var name =ss.getRawValue();
				var code=ss.getValue();
				form.reset();
				//个长途车队只能查看修改自己车队的数据，但是长途运输服务组(W0000001563)可以查看8个长途车队所录入的数据。
				if(FossUserContext.getCurrentUserDept().code == 'W0000001563'){
					var ss=form.findField('orgIdCode');
					ss.setReadOnly(false);
					ss.setCombValue('','');
				}else{
					var ss=form.findField('orgIdCode');
					ss.setCombValue(name,code);
					ss.setReadOnly(true);
				}
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text :load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.qureyButton'),//'查询',
			handler : function(){
			var form = this.up('form').getForm();
				if(form.isValid()){
					load.lfDrivingFile.pagingBar.moveFirst();
			}
			}
		} ]
	
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//长途车辆行驶档案 Store
Ext.define('Foss.load.lfDrivingFileStore',{
   extend:'Ext.data.Store',
   autoLoad:false,
   model:'Foss.load.lfDrivingFileModel',
   pageSize:20,
   proxy:{
   	type:'ajax',
   	actionMethods:'POST',
   	url: load.realPath('querylfDrivingFileList.action'),
   	reader:{
   		type:'json',
   		root:'lfDrivingFileVo.lfDrivingFiles',
   		totalProperty : 'totalCount',
		successProperty: 'success'
   	}
   },
   listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = load.lfDrivingFileForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'lfDrivingFileVo.lfDrivingFile.drivingDate':queryParams.drivingDate,
					'lfDrivingFileVo.lfDrivingFile.orgIdCode' : queryParams.orgIdCode,
					'lfDrivingFileVo.lfDrivingFile.beginDate' : queryParams.beginDate,
					'lfDrivingFileVo.lfDrivingFile.endDate' : queryParams.endDate,
					'lfDrivingFileVo.lfDrivingFile.drivingNo' : queryParams.drivingNo
					
				}
			});	
		}
	},
   	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//长途车辆行驶档案 查询结果grid
Ext.define('Foss.load.lfDrivingFileGrid',{
	extend:'Ext.grid.Panel',
	title:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.titleGrid'),//查询结果
    frame: true,
    columnLines: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
	emptyText : '暂无数据',
	columns:[{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingDate'),//期间
		dataIndex:'drivingDate',
		width : 70
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.orgIdName'),//所属车队
		dataIndex:'orgIdName',
		xtype: 'ellipsiscolumn',
		width : 100
	},{
		dataIndex:'orgIdCode',
		hidden:true
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.isDeivingEmpty'),//是否空驶
		dataIndex:'isDeivingEmpty',
		width : 30,
		renderer: function (v){
		 if(!Ext.isEmpty(v)){
		  if(v=='Y'){
		  	return "是";
		  }else{
		  	return "否";
		  }
		 }
		}
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingNo'),//行车编码
		dataIndex:'drivingNo',
		xtype: 'ellipsiscolumn',
		width : 120
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleNo'),//车牌号
		dataIndex:'vehicleNo',
		xtype: 'ellipsiscolumn',
		width :80
	},{
		dataIndex:'vehicleType',
		hidden:true
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleType'),//车型
		dataIndex:'vehicleTypeName',
		xtype: 'ellipsiscolumn',
		width : 120
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.lineName'),//线路
		dataIndex:'lineName',
		xtype: 'ellipsiscolumn',
		width : 120
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.departDistance'),//出发公里数
		dataIndex:'departDistance',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.arriveDistance'),//到达公里数
		dataIndex:'arriveDistance',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingDistance'),//行驶公里数
		dataIndex:'drivingDistance',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelTotal'),//总油升数
		dataIndex:'consumeFuelTotal',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelFeeTotal'),//总油费
		dataIndex:'consumeFuelFeeTotal',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelFee'),//平均油价
		dataIndex:'consumeFuelFee',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.toolFeeTotal'),//路桥费
		dataIndex:'toolFeeTotal',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuel'),//百公里油耗
		dataIndex:'consumeFuel',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.toolFee'),//公里路桥费
		dataIndex:'toolFee',
		width : 60
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.driverNameOne'),//司机1
		dataIndex:'driverOne',
		renderer: function (v, metadata, record, rowIndex, columnIndex, store) {
		 if(!Ext.isEmpty(record.data.driverCodeOne)){
		   return "【"+record.data.driverCodeOne
		   +"】"+record.data.driverNameOne
		 }else{
		 return null;
		 }
		},
		width : 120
		
	},{
		dataIndex:'driverCodeOne',
		hidden:true
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.driverNameTwo'),//司机2
		dataIndex:'driverTwo',
		renderer: function (v, metadata, record, rowIndex, columnIndex, store) {
		 if(!Ext.isEmpty(record.data.driverCodeTwo)){
		   return "【"+record.data.driverCodeTwo
		   +"】"+record.data.driverNameTwo
		 }else{
		 return null;
		 }
		},
		width :120
	},{
		dataIndex:'driverCodeTwo',
		hidden:true
	},{
		header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.modifyUserName'),//最后操作人
		dataIndex:'modifyUser',
		renderer: function (v, metadata, record, rowIndex, columnIndex, store) {
		 if(!Ext.isEmpty(record.data.modifyUserCode)){
		   return "【"+record.data.modifyUserCode
		   +"】"+record.data.modifyUserName
		 }else{
		 return null;
		 }
		},
		width : 120
	},{//线路途径外场
	 dataIndex:'lineTransferName',
	 hidden:true
	}
	],
	store: Ext.create('Foss.load.lfDrivingFileStore'),
	constructor:function(config){
		var me=this,
		    cfg=Ext.apply({},config);
		    me.tbar=[{
		    	xtype:'button',
		    	text:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.addButton'),//'新增',
		    	disabled:!load.lfDrivingFile.isPermission('load/lfDrivingFileAddButton'),//
		    	hidden:!load.lfDrivingFile.isPermission('load/lfDrivingFileAddButton'),
		    	handler:function(){
		    	  Ext.MessageBox.confirm('提示','新增档案是否包含空驶线路或者中途换车头线路？',function(btn) {
					if (btn == 'yes') {
						load.addTab('T_load-lfDrivingFileAddindex',
					    //对应打开的目标页面js的onReady里定义的renderTo
						'空驶明细 新增',load.realPath('lfDrivingFileAddindex.action')+ 
						'?isDeivingEmpty=Y');//对应的页面URL，可以在url后使用?x=123这种形式传参
					} else {
				        load.addTab('T_load-lfDrivingFileAddindex',
					    //对应打开的目标页面js的onReady里定义的renderTo
						'非空驶明细 新增',load.realPath('lfDrivingFileAddindex.action')+
						'?isDeivingEmpty=N');
					}
				 });
		     }
		    },{
		    	xtype:'button',
		    	text:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.modifyButton'),//'修改',
		    	disabled:!load.lfDrivingFile.isPermission('load/lfDrivingFileModifyButton'),//
		    	hidden:!load.lfDrivingFile.isPermission('load/lfDrivingFileModifyButton'),
		    	handler:function(){
		    	 var grid= this.up('grid');
				 var selects=grid.getSelectionModel().getSelection();
				 if(selects.length==1){
					var createTime=Ext.Date.parse(selects[0].data.createTime, "Y-m-d H:i:s", true);
					var nowDate=new Date();
					var falage=false;
				   if(nowDate.getDate()>10&&nowDate.getMonth()!=createTime.getMonth()){
				  	Ext.MessageBox.alert('提示','10日之后只能修改当月的数据！');
				   	return;
				   }
				   if(nowDate.getDate()<11&&new Date(nowDate-30*24*60*60*1000)>createTime){
				   	Ext.MessageBox.alert('提示','11日之前只能修改30天内的数据！');
				   	return;
				   }
				   if('Y'==selects[0].data.isDeivingEmpty){
				   load.addTab('T_load-lfDrivingFileUpdateindex',
					    //对应打开的目标页面js的onReady里定义的renderTo
						'空驶明细修改',load.realPath('lfDrivingFileUpdateindex.action')+
						'?drivingNo='+selects[0].data.drivingNo);
				   }else{
				   load.addTab('T_load-lfDrivingFileUpdateindex',
				  	    //对应打开的目标页面js的onReady里定义的renderTo
						'非空驶明细修改',load.realPath('lfDrivingFileUpdateindex.action')+
						'?drivingNo='+selects[0].data.drivingNo);
				   }
				}else{
					   Ext.MessageBox.alert('提示','请选择要修改的一行！');
				}
		     }
		    },{
		    	xtype:'button',
		    	text:'详情',//load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.modifyButton'),//'修改',
		    	//disabled:!load.lfDrivingFile.isPermission('load/lfDrivingFileModifyButton'),//
		    	//hidden:!load.lfDrivingFile.isPermission('load/lfDrivingFileModifyButton'),
		    	handler:function(){
		    	 var grid= this.up('grid');
				 var selects=grid.getSelectionModel().getSelection();
				 if(selects.length==1){
				   if('Y'==selects[0].data.isDeivingEmpty){
				   load.addTab('T_load-lfDrivingFileInfoindex',
					    //对应打开的目标页面js的onReady里定义的renderTo
						'空驶明细修改',load.realPath('lfDrivingFileInfoindex.action')+
						'?drivingNo='+selects[0].data.drivingNo);
				   }else{
				   load.addTab('T_load-lfDrivingFileInfoindex',
				  	    //对应打开的目标页面js的onReady里定义的renderTo
						'非空驶明细修改',load.realPath('lfDrivingFileInfoindex.action')+
						'?drivingNo='+selects[0].data.drivingNo);
				   }
				}else{
					   Ext.MessageBox.alert('提示','请选择要查看的一行！');
				}
		     }
		    
		    }];
		 me.store = Ext.create('Foss.load.lfDrivingFileStore');
		 /*me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 2,
			maximumSize : 200,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['30', 30], ['50', 50],['80', 80],['100', 100],['150', 150],['200', 200]]
			})
		});*/
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
		load.lfDrivingFile.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
Ext.onReady(function(){
	Ext.QuickTips.init();
	load.lfDrivingFileForm=Ext.create('Foss.load.lfDrivingFileForm');
	load.lfDrivingFileGrid=Ext.create('Foss.load.lfDrivingFileGrid');
	Ext.create('Ext.panel.Panel',{
		    id : 'T_load-lfDrivingFileIndex_content',
			cls:'panelContentNToolbar',
			bodyCls:'panelContentNToolbar-body',
			layout:'auto',
			items:[	load.lfDrivingFileForm,load.lfDrivingFileGrid],
		renderTo : 'T_load-lfDrivingFileIndex-body'
		});
		//个长途车队只能查看修改自己车队的数据，但是长途运输服务组(W0000001563)可以查看8个长途车队所录入的数据。
		if(FossUserContext.getCurrentUserDept().code == 'W0000001563'){
			var ss=load.lfDrivingFileForm.getForm().findField('orgIdCode');
			ss.setReadOnly(false);
			ss.setCombValue('','');
		}else{
			var ss=load.lfDrivingFileForm.getForm().findField('orgIdCode');
			Ext.Ajax.request({
			  url : load.realPath('queryOrgIdCode.action'),
			  params:{'lfDrivingFileVo.orgIdCode': FossUserContext.getCurrentUserDept().code},
			  timeout : 300000,
			  success : function(response){
				var result = Ext.decode(response.responseText);
				var lfDrivingFile=result.lfDrivingFileVo.lfDrivingFile;
				    ss.setCombValue(lfDrivingFile.orgIdName,lfDrivingFile.orgIdCode);
					ss.setReadOnly(true);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					ss.setReadOnly(true);
					top.Ext.MessageBox.alert('提示','获取车队失败' + result.message);
						
				},
				failure : function(){
					ss.setReadOnly(true);
					console.log('加载长途车辆行驶档案所属车队时服务端异常！');
				}
			  });
	 }
});