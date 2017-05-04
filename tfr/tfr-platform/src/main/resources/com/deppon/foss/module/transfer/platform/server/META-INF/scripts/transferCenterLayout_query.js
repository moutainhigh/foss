//场内布局图查询form
Ext.define('Foss.platform.transferCenterLayoutQuery.queryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'orgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		allowBlank : false,
		transferCenter : 'Y'
	}, {
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
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入要查询的转运场！', 'error', 1500);
					return;
				}
				var orgCode = form.findField('orgCode').getValue(),
					orgRecord = form.findField('orgCode').store.findRecord('code',orgCode,0,false,true,true),
					orgName = orgRecord.get('name');
				var url = platform.realPath('displayShowPage.action') + '?orgCode='  + orgCode + '&beComeFromQueryPage=1&fullScreen=true';
				var mainTab = Ext.getCmp('mainAreaPanel'),
			    	tab = Ext.getCmp('T_platform-displayShowPage');
				if(tab){
					mainTab.remove(tab,true);
					addTab('T_platform-displayShowPage',orgName,url);
				}else{
					addTab('T_platform-displayShowPage',orgName,url);
				}
				
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

platform.transferCenterLayoutQuery.queryForm = Ext.create('Foss.platform.transferCenterLayoutQuery.queryForm');

//主页面
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-chooseDisplayPage_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.transferCenterLayoutQuery.queryForm],
		renderTo: 'T_platform-chooseDisplayPage-body'
	});
});