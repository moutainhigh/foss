/**
 * 查询库区密度信息页面
 * xiaohongye
 * 2015-04-14
 */
//查询实体model
Ext.define('Foss.platform.densityInfo.Model',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//经营本部编码
		name : 'hqCode',
		type : 'string'
	},{//经营本部名称
		name : 'hqName',
		type : 'string'
	},{//库区总容量
		name : 'areaVolumeD',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//日库区密度峰值
		name : 'peakDensityD',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.0000');
			} else {
				return null;
			}
		}
	},{//月库区密度峰值
		name : 'peakDensityM',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.0000');
			} else {
				return null;
			}
		}
	},{//日库区密度峰值时间点
		name : 'peakTimeD',
		type : 'string'
	},{//月库区密度峰值时间点
		name : 'peakTimeM',
		type : 'string'
	}]
});
//查询store
Ext.define('Foss.platform.densityInfo.store',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.densityInfo.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findTfrCtrDensityPeak.action'),
		reader : {
			type : 'json',
			root : 'densityVo.tfrCtrDensityPeakEntities',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.densityInfo.form.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var businessDeptCode = queryForm.findField('queryCondition.businessDeptCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
				'densityVo.densityQcDto.staDate':staDate,
				'densityVo.densityQcDto.hqCode':businessDeptCode
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
//查询条件
Ext.define('Foss.platform.densityInfo.form', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	allowBlank:true,
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
				platform.densityInfo.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityInfo.initDeptInfo(_this,'OUTFIELDCODE');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth:.25
	},{
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
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化外场与经营本部信息
				platform.densityInfo.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				platform.densityInfo.initDeptInfo(businessDeptCode,'HQCODE');
				
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

//查询结果表格
Ext.define('Foss.platform.densityInfo.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	title:'库区密度信息',
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
		header : '经营本部',
		sortable: false,
		dataIndex : 'hqName',
		width:100
	},{
		xtype : 'ellipsiscolumn',
		header : '外场',
		sortable: false,
		align : 'center',
		dataIndex : 'tfrCtrName',
		width:100
	},{
		xtype : 'ellipsiscolumn',
		header : '库区总容量(F)',
		sortable: false,
		align : 'center',
		dataIndex : 'areaVolumeD',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '日库区密度峰值',
		sortable: false,
		dataIndex : 'peakDensityD',
		width:150,
		renderer:function(val){
			return (val*100).toFixed(2)+'%';
		}
	},{
		xtype : 'ellipsiscolumn',
		header : '日库区密度峰值时间点',
		sortable: false,
		align : 'center',
		dataIndex : 'peakTimeD',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '月库区密度峰值',
		sortable: false,
		align : 'center',
		dataIndex : 'peakDensityM',
		width:150,
		renderer:function(val){
			return (val*100).toFixed(2)+'%';
		}
	},{
		xtype : 'ellipsiscolumn',
		header : '月库区密度峰值时间点',
		sortable: false,
		align : 'center',
		dataIndex : 'peakTimeM',
		width:150
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.densityInfo.store');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '无密度信息可供导出', 'error', 2000);
					return;
				}
				//获得查询参数
				var queryForm = platform.densityInfo.form.getForm();
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var businessDeptCode = queryForm.findField('queryCondition.businessDeptCode').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.staDate':staDate,
					'densityVo.densityQcDto.hqCode':businessDeptCode
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportTfrCtrDensityPeak.action'),
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
platform.densityInfo.initDeptInfo = function(item,desc){
	//如果是外场需要被初始化
	if('OUTFIELDCODE' === desc){
		//初始化外场信息
		if(!Ext.isEmpty(platform.densityInfo.outfieldCode)){
			//外场初始化
			item.setCombValue(
				platform.densityInfo.outfield,
				platform.densityInfo.outfieldCode
			);
			item.readOnly = true;
		}
	}else if('HQCODE' === desc){//如果是经营本部需要被初始化
		if(!Ext.isEmpty(platform.densityInfo.outfieldCode)){
			//设置经营本部只读
			item.readOnly = true;
		}else if(Ext.isEmpty(platform.densityInfo.outfieldCode) && !Ext.isEmpty(platform.densityInfo.hqCode)){
			//经营本部初始化
			item.setCombValue(
				platform.densityInfo.hqName,
				platform.densityInfo.hqCode
			);
			//设置经营本部只读
			item.readOnly = true;
		}
	}
}
//查询条件form
platform.densityInfo.form = Ext.create('Foss.platform.densityInfo.form');
//查询结果表格
platform.densityInfo.grid = Ext.create('Foss.platform.densityInfo.grid');
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//页面
	Ext.define('Foss.platform.densityInfo.Panel', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//查询条件form
			var form = platform.densityInfo.form;
			//查询结果表格
			var grid = platform.densityInfo.grid;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	platform.densityInfo.MainPanel = Ext.create('Foss.platform.densityInfo.Panel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-densityInfo_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.densityInfo.MainPanel],
		renderTo : 'T_platform-densityInfo-body'
	});
	
});