Ext.define('Foss.Baseinfo.PressureTest', {
	extend:'Ext.form.Panel',
	bodyCls: 'autoHeight',
	title: '压力测试界面',
	defaults:{
    	margin:'10 0 0 5',
    	labelWidth:85
	 },
    items:[{
    	xtype: 'textfield',
		name:'code',
        fieldLabel: '组织编码'
    }],
    buttons: [{
		text:'重置',
		handler:function(){
			this.up('form').getForm().reset();
		}
	},'->',{
		text:'查询',
		cls:'yellow_button',
		handler:function(){		
			
			var code = this.up('form').getForm().findField('code').getValue();
			var jsonData = {'vo':{'code':code}};
			Ext.Ajax.request({
				url : baseinfo.realPath('pressTestByCode.action'),
				jsonData:jsonData,
//				params : {
//					"vo.code" : code
//				},
//				method : 'post',
				success : function(response) {
					var result = Ext.decode(response.responseText);
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
				},
				exception : function (response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.show({
					title : '温馨提示',
					msg : json.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
			}
			});
		}
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var PressureTestTab = Ext.create('Foss.Baseinfo.PressureTest');
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-pressureTest_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [PressureTestTab],
		renderTo : 'T_baseinfo-pressureTest-body'
	});
});