Ext.define('Foss.baseinfo.expressBranchSalesDept.ActiveStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询已配置(快递点部营业部映射关系)的快递点部FORM

Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm',{
	extend:'Ext.form.Panel',
	title:'查询条件',
	frame:true,
	height:200,
	defaults:{
		margin :'20 0 0 10',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :4
	},
	items : [
				{
					xtype:'commonsaledepartmentselector',
					fieldLabel:'营业部名称',
					name:'salesDeptCode',
					labelWidth :80,
					columnWidth:.26,
				},{
					xtype:'dynamicorgcombselector',
					fieldLabel:'快递点部名称',
					name:'expressPartCode',
					type:'ORG',
					expressPart:'Y',
					labelWidth :90,
					columnWidth:.27
				},{
					xtype : 'datetimefield_date97',
					fieldLabel:'业务日期',
					editable:false,
					name:'businessTime',
					labelWidth :70,
					columnWdth:.25,
					time : true,
					id : 'Foss_baseinfo_expressBranchSalesDept_businessTime_ID',
					dateConfig: {
						el : 'Foss_baseinfo_expressBranchSalesDept_businessTime_ID-inputEl'
					}
				},{
					xtype : 'combobox',
					name : 'active',
					fieldLabel : '是否有效',
					labelWidth :70,
					columnWidth : .21,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : Ext.create('Foss.baseinfo.expressBranchSalesDept.ActiveStore', {
						data : {
							'items' : [{
									'valueCode' : '',
									'valueName' : '全部'
								}, {
									'valueCode' : 'N',
									'valueName' : '否'
								}, {
									'valueCode' : 'Y',
									'valueName' : '是'
								}
							]
						}
					}),
					value : ''
				},{
					xtype : 'container',
					columnWidth : 1,
					defaultType:'button',
					layout:'column',
					items : [{
						xtype : 'button', 
						text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.reset'),
						columnWidth:.1,
						handler: function(){
							var form=this.up('form').getForm();
							form.reset();
						}
					},{
						xtype:'container',
						html:'&nbsp;',
						columnWidth:.8
					},{
						xtype : 'button', 
						width:70,
						columnWidth:.1,
						text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.query'),//查询
						cls:'yellow_button',
						handler : function() {
						}
					}]
				
				}
				]			
});
//--------------------------------------------------部营业信息界面 end------------------------------------------------------

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressBranchSalesDeptindex_content')) {
		return;
	} 
	
	//查询已配置(快递点部营业部映射关系)的快递点部FORM
	var expressBranchSalesDeptQueryForm = Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm');

	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-expressBranchSalesDeptindex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getExpressPartSalesDeptQueryForm:function(){
			return expressBranchSalesDeptQueryForm;
		},
		items: [expressBranchSalesDeptQueryForm],
		renderTo : 'T_baseinfo-expressBranchSalesDeptindex-body'
	});
});