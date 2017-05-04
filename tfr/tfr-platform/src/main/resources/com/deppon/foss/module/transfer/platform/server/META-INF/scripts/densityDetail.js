/**
 * 查询库区密度明细界面分三个页签：待叉区、驻地派送库区、中转库区
 * xiaohongye
 * 2015-04-13
 */
//待叉区查询实体model
Ext.define('Foss.platform.densityDetail.ModelOfFork',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//待叉区编码
		name : 'areaCode',
		type : 'string'
	},{//待叉区体积
		name : 'areaVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//货物体积
		name : 'goodsVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//待叉区密度
		name : 'density',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.0000');
			} else {
				return 0.0000;
			}
		}
	}]
});
//驻地派送库区查询实体model
Ext.define('Foss.platform.densityDetail.ModelOfDpt',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//分区名称
		name : 'zoneName',
		type : 'string'
	},{//件区名称
		name : 'itemAreaName',
		type : 'string'
	},{//件区容量
		name : 'itemAreaVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//件区货量
		name : 'goodsVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//件区密度
		name : 'density',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.0000');
			} else {
				return 0.0000;
			}
		}
	}]
});
//中转库区查询实体model
Ext.define('Foss.platform.densityDetail.ModelOfTfr',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//库区类别
		name : 'areaUsage',
		type : 'string'
	},{//库区名称
		name : 'areaName',
		type : 'string'
	},{//库区容量
		name : 'areaVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//库区货量
		name : 'goodsVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return 0.00;
			}
		}
	},{//库区密度
		name : 'density',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.0000');
			} else {
				return 0.0000
			}
		}
	}]
});
//待叉区界面的store
Ext.define('Foss.platform.densityDetail.storeOfFork',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.densityDetail.ModelOfFork',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findForkAreaDensity.action'),
		reader : {
			type : 'json',
			root : 'densityVo.forkAreaDensityEntities',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.densityDetail.formOfFork.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staHour = queryForm.findField('queryCondition.staHour').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
				'densityVo.densityQcDto.staDate':staDate,
				'densityVo.densityQcDto.staHour':staHour
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});
//驻地派送库区界面的store
Ext.define('Foss.platform.densityDetail.storeOfDpt',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.densityDetail.ModelOfDpt',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findDptAreaDensity.action'),
		reader : {
			type : 'json',
			root : 'densityVo.dptAreaDensityEntities',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.densityDetail.formOfDpt.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staHour = queryForm.findField('queryCondition.staHour').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
				'densityVo.densityQcDto.staDate':staDate,
				'densityVo.densityQcDto.staHour':staHour
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});
//中转库区界面的store
Ext.define('Foss.platform.densityDetail.storeOfTfr',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.densityDetail.ModelOfTfr',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findTfrAreaDensity.action'),
		reader : {
			type : 'json',
			root : 'densityVo.tfrAreaDensityEntities',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.densityDetail.formOfTfr.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staHour = queryForm.findField('queryCondition.staHour').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var goodsAreaUsage = queryForm.findField('queryCondition.goodsAreaUsage').getValue();
			var goodsAreaCode = queryForm.findField('queryCondition.goodsAreaCode').getValue();
			var params = {
				'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
				'densityVo.densityQcDto.staDate':staDate,
				'densityVo.densityQcDto.staHour':staHour,
				'densityVo.densityQcDto.goodsAreaUsage':goodsAreaUsage,
				'densityVo.densityQcDto.goodsAreaCode':goodsAreaCode
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});
//待叉区查询条件
Ext.define('Foss.platform.densityDetail.formOfFork', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		allowBlank:false,
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'OUTFIELDCODE');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value:login.currentServerTime,
		maxValue:login.currentServerTime,
		format:'Y-m-d',
		columnWidth:.25
	},FossDataDictionary.getDataDictionaryCombo('TFR_TWENTY_FOUR_OCLOCK',{
		fieldLabel : '时间点',
		forceSelection :true,
		listeners:{
			//使得combobox中的内容可删除
			change:function(combo){
				if(Ext.isEmpty(combo.getValue())){
					combo.setValue("");
				}
			}
		},
		name : 'queryCondition.staHour',
		displayField : 'valueName',
	    valueField : 'valueCode',
		allowBlank : false,
		columnWidth:.25
	}),{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.densityDetail.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				//获得查询条件的经营本部
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化经营本部
				platform.densityDetail.initDeptInfo(businessDeptCode,'HQCODE');
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//加载数据
				th.up('form').up('panel').down('grid').store.load();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//驻地派送库区查询条件
Ext.define('Foss.platform.densityDetail.formOfDpt', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		allowBlank:false,
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'OUTFIELDCODE');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value:login.currentServerTime,
		maxValue:login.currentServerTime,
		format:'Y-m-d',
		columnWidth:.25
	},FossDataDictionary.getDataDictionaryCombo('TFR_TWENTY_FOUR_OCLOCK',{
		fieldLabel : '时间点',
		forceSelection :true,
		listeners:{
			//使得combobox中的内容可删除
			change:function(combo){
				if(Ext.isEmpty(combo.getValue())){
					combo.setValue("");
				}
			}
		},
		name : 'queryCondition.staHour',
		displayField : 'valueName',
	    valueField : 'valueCode',
		allowBlank : false,
		columnWidth:.25
	}),{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.densityDetail.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				//获得查询条件的经营本部
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化经营本部
				platform.densityDetail.initDeptInfo(businessDeptCode,'HQCODE');
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//加载数据
				th.up('form').up('panel').down('grid').store.load();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//中转库区查询条件
Ext.define('Foss.platform.densityDetail.formOfTfr', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		columnWidth : 0.25,
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityDetail.initDeptInfo(_this,'OUTFIELDCODE');
			},
			select:function(th,records,e){
				//获取库区名称
				var goodsAreaCode = th.up('form').getForm().findField('queryCondition.goodsAreaCode');
				goodsAreaCode.reset();
				goodsAreaCode.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					//设置外场参数
					searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = th.getValue();
					Ext.apply(operation, {
						params : searchParams 
					});
				}); 
				goodsAreaCode.store.load();
			},
			change:function(th,newValue,oldValue){
				if(Ext.isEmpty(th.getValue())){
					th.setValue("");
				}
				if(Ext.isEmpty(newValue)){
					//获取库区名称
					var goodsAreaCode = th.up('form').getForm().findField('queryCondition.goodsAreaCode');
					goodsAreaCode.reset();
					goodsAreaCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						//设置外场参数
						searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = th.getValue();
						Ext.apply(operation, {
							params : searchParams 
						});
					});
					goodsAreaCode.store.load();
				}
			}
		}
	},{
		name: 'queryCondition.goodsAreaUsage',
		queryMode: 'local',
		displayField: 'valueName',
		valueField: 'valueCode',
		columnWidth: 0.25,
		forceSelection :true,
		listeners:{
			//使得combobox中的内容可删除
			change:function(combo){
				if(Ext.isEmpty(combo.getValue())){
					combo.setValue("");
				}
			}
		},
		store: FossDataDictionary.getDataDictionaryStore('BSE_GOODSAREA_USAGE'),
		fieldLabel: '库区类别', 
		xtype: 'combo'
	},{
		name : 'queryCondition.goodsAreaCode',
		fieldLabel : '库区名称',
		columnWidth : 0.25,
		//对应的外场code
		deptCode:platform.densityDetail.outfieldCode,
		xtype : 'commongoodsareaselector'
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value:login.currentServerTime,
		maxValue:login.currentServerTime,
		format:'Y-m-d',
		columnWidth:.25
	},FossDataDictionary.getDataDictionaryCombo('TFR_TWENTY_FOUR_OCLOCK',{
		fieldLabel : '时间点',
		forceSelection :true,
		listeners:{
			//使得combobox中的内容可删除
			change:function(combo){
				if(Ext.isEmpty(combo.getValue())){
					combo.setValue("");
				}
			}
		},
		name : 'queryCondition.staHour',
		displayField : 'valueName',
	    valueField : 'valueCode',
		allowBlank : false,
		columnWidth:.25
	}),{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.densityDetail.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				//获得查询条件的经营本部
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化经营本部
				platform.densityDetail.initDeptInfo(businessDeptCode,'HQCODE');
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//加载数据
				th.up('form').up('panel').down('grid').store.load();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//待叉区密度信息查询结果表格
Ext.define('Foss.platform.densityDetail.gridOfFork', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title:'待叉区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : false,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '外场',
		sortable: false,
		dataIndex : 'tfrCtrName',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '待叉区编号',
		sortable: false,
		align : 'center',
		dataIndex : 'areaCode',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '待叉区容量(F)',
		sortable: false,
		align : 'center',
		dataIndex : 'areaVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '待叉区货量(F)',
		sortable: false,
		dataIndex : 'goodsVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '待叉区密度',
		sortable: false,
		align : 'center',
		dataIndex : 'density',
		renderer:function(val){
			return (val*100).toFixed(2) + '%';
		},
		flex:0.2
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.densityDetail.storeOfFork');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '列表中无密度信息可供导出！', 'error', 2000);
					return;
				}
				//获取导出条件
				var queryForm = platform.densityDetail.formOfFork.getForm();
				if (!queryForm.isValid()) {
					Ext.ux.Toast.msg('提示', '导出失败，请输入有效导出条件!', 'error', 2000);
					return false;
				}
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staHour = queryForm.findField('queryCondition.staHour').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.staDate':staDate,
					'densityVo.densityQcDto.staHour':staHour
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportForkAreaDensity.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});

//驻地派送库区密度信息查询结果表格
Ext.define('Foss.platform.densityDetail.gridOfDpt', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title:'驻地派送库区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : false,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '外场',
		sortable: false,
		dataIndex : 'tfrCtrName',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '分区名称',
		sortable: false,
		align : 'center',
		dataIndex : 'zoneName',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '件区名称',
		sortable: false,
		align : 'center',
		dataIndex : 'itemAreaName',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '件区容量(F)',
		sortable: false,
		dataIndex : 'itemAreaVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '件区货量(F)',
		sortable: false,
		dataIndex : 'goodsVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '件区密度',
		sortable: false,
		align : 'center',
		dataIndex : 'density',
		renderer:function(val){
			return (val*100).toFixed(2) + '%';
		},
		flex:0.2
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.densityDetail.storeOfDpt');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '列表中无密度信息可供导出！', 'error', 2000);
					return;
				}
				//获取导出条件
				var queryForm = platform.densityDetail.formOfDpt.getForm();
				if (!queryForm.isValid()) {
					Ext.ux.Toast.msg('提示', '导出失败，请输入有效导出条件!', 'error', 2000);
					return false;
				}
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staHour = queryForm.findField('queryCondition.staHour').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.staDate':staDate,
					'densityVo.densityQcDto.staHour':staHour
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportDptAreaDensity.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});
//中转库区密度信息查询结果表格
Ext.define('Foss.platform.densityDetail.gridOfTfr', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title:'中转库区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : false,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '外场',
		sortable: false,
		dataIndex : 'tfrCtrName',
		width: 120
	},{
		xtype : 'ellipsiscolumn',
		header : '库区类别',
		sortable: false,
		align : 'center',
		dataIndex : 'areaUsage',
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'BSE_GOODSAREA_USAGE');
		},
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '库区名称',
		sortable: false,
		align : 'center',
		dataIndex : 'areaName',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '库区容量(F)',
		sortable: false,
		dataIndex : 'areaVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '库区货量(F)',
		sortable: false,
		dataIndex : 'goodsVolume',
		flex:0.2
	},{
		xtype : 'ellipsiscolumn',
		header : '库区密度',
		sortable: false,
		align : 'center',
		dataIndex : 'density',
		renderer:function(val){
			return (val*100).toFixed(2) + '%';
		},
		flex:0.2
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.densityDetail.storeOfTfr');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '列表中无密度信息可供导出！', 'error', 2000);
					return;
				}
				//获取导出条件
				var queryForm = platform.densityDetail.formOfTfr.getForm();
				if (!queryForm.isValid()) {
					Ext.ux.Toast.msg('提示', '导出失败，请输入有效导出条件!', 'error', 2000);
					return false;
				}
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staHour = queryForm.findField('queryCondition.staHour').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				var goodsAreaUsage = queryForm.findField('queryCondition.goodsAreaUsage').getValue();
				var goodsAreaCode = queryForm.findField('queryCondition.goodsAreaCode').getValue();
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.staDate':staDate,
					'densityVo.densityQcDto.staHour':staHour,
					'densityVo.densityQcDto.goodsAreaUsage':goodsAreaUsage,
					'densityVo.densityQcDto.goodsAreaCode':goodsAreaCode
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportTfrAreaDensity.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});
//填充外场与经营本部信息并设置外场combobox的readOnly属性
platform.densityDetail.initDeptInfo = function(item,desc){
	//如果是外场需要被初始化
	if('OUTFIELDCODE' === desc){
		//初始化外场信息
		if(!Ext.isEmpty(platform.densityDetail.outfieldCode)){
			//外场初始化
			item.setCombValue(
				platform.densityDetail.outfield,
				platform.densityDetail.outfieldCode
			);
			item.readOnly = true;
		}
	}else if('HQCODE' === desc){//如果是经营本部需要被初始化
		if(!Ext.isEmpty(platform.densityDetail.outfieldCode)){
			//设置经营本部只读
			item.readOnly = true;
		}else if(Ext.isEmpty(platform.densityDetail.outfieldCode) && !Ext.isEmpty(platform.densityDetail.hqCode)){
			//经营本部初始化
			item.setCombValue(
				platform.densityDetail.hqName,
				platform.densityDetail.hqCode
			);
			//设置经营本部只读
			item.readOnly = true;
		}
	}
}
//待叉区查询条件form
platform.densityDetail.formOfFork = Ext.create('Foss.platform.densityDetail.formOfFork');
//待叉区查询结果表格
platform.densityDetail.gridOfFork = Ext.create('Foss.platform.densityDetail.gridOfFork');
//驻地派送库区查询条件form
platform.densityDetail.formOfDpt = Ext.create('Foss.platform.densityDetail.formOfDpt');
//驻地派送库区查询结果表格
platform.densityDetail.gridOfDpt = Ext.create('Foss.platform.densityDetail.gridOfDpt');
//中转库区查询条件form
platform.densityDetail.formOfTfr = Ext.create('Foss.platform.densityDetail.formOfTfr');
//中转库区查询结果表格
platform.densityDetail.gridOfTfr = Ext.create('Foss.platform.densityDetail.gridOfTfr');
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//待叉区页签页面
	Ext.define('Foss.platform.densityDetail.PanelOfFork', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//待叉区查询条件form
			var form = platform.densityDetail.formOfFork;
			//快递中转货物流动轨迹查询结果表格
			var grid = platform.densityDetail.gridOfFork;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	//驻地派送库区页签页面
	Ext.define('Foss.platform.densityDetail.PanelOfDpt', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//驻地派送库区查询条件form
			var form = platform.densityDetail.formOfDpt;
			//快递中转货物流动轨迹查询结果表格
			var grid = platform.densityDetail.gridOfDpt;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	//中转库区页签页面
	Ext.define('Foss.platform.densityDetail.PanelOfTfr', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//中转库区查询条件form
			var form = platform.densityDetail.formOfTfr;
			//快递中转货物流动轨迹查询结果表格
			var grid = platform.densityDetail.gridOfTfr;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	/**
	 * 快递场内货物流动轨迹界面两个页签panel
	 */
	Ext.define('Foss.platform.densityDetail.MainTabPanel', {
		extend : 'Ext.tab.Panel',
		cls : "innerTabPanel",
		bodyCls : "overrideChildLeft",
		activeTab : 0,
		autoScroll : false,
		frame : false,
		items : [{
			tabConfig : {
				width : 100
			},
			title : '待叉区',
			items : Ext.create('Foss.platform.densityDetail.PanelOfFork')
		}, {
			tabConfig : {
				width : 100
			},
			title : '驻地派送库区',
			items : Ext.create('Foss.platform.densityDetail.PanelOfDpt')
		}, {
			tabConfig : {
				width : 100
			},
			title : '中转库区',
			items : Ext.create('Foss.platform.densityDetail.PanelOfTfr')
		}]
	});
	platform.densityDetail.MainTabPanel = Ext.create('Foss.platform.densityDetail.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-densityDetail_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.densityDetail.MainTabPanel],
		renderTo : 'T_platform-densityDetail-body'
	});
	
});