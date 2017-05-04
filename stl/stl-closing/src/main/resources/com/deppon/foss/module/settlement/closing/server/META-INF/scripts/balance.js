closing.balance.balanceType_balance = 'BALANCE';
closing.balance.balanceType_unbalance = 'UNBALANCE';

closing.balance.balance = function(balanceType,endDate){
	
	var content = Ext.getCmp('T_closing-balanceIndex_content');
	var form = content.down('form');
	
	var currentBalanceDate = form.getForm().findField('currentBalanceDate');
	var isBalanceRun = form.getForm().findField('isBalanceRun');
	var btnBalance = form.getComponent('btnBalance');
	var btnUnBalance = form.getComponent('btnUnBalance');
	
	currentBalanceDate.setValue(null);
	isBalanceRun.setValue(true);
	btnBalance.disable()
	btnUnBalance.disable();
	
	var action = null;
	if(closing.balance.balanceType_balance == balanceType){
		action = closing.realPath('closeBalance.action');
	}else{
		action = closing.realPath('unCloseBalance.action')
	}
	
	//导出Ajax请求
	Ext.Ajax.request({
		url:action, 
		params:{
			'balanceVo.endDate':endDate
		},
		method:'post',
		timeout:3*1000*3600,
		success:function(response){
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert('温馨提示', json.message);
		},
		failure:function(response){
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert('温馨提示', json.message);
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert('温馨提示', json.message);
		}
    });
}

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-balanceIndex_content')) {
		return;
	}

	// 显示到JSP页面
	Ext.getCmp('T_closing-balanceIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_closing-balanceIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ {
			xtype : 'form',
			frame : true,
			bodyCls : 'autoHeight',
			defaults : {
				margin : '10 6 10 20'
			},
			layout : 'column',
			items : [ {
		        // Fieldset in Column 1 - collapsible via toggle button
		        xtype:'fieldset',
		        columnWidth: 0.8,
		        title: '期末结账信息',
		        collapsible: false,
		        defaults: {
		        	anchor: '100%',
		        	labelWidth:200,
		        	width:300
		        },
		        layout: 'vbox',
		        items :[{
		            xtype: 'displayfield',
		            fieldLabel: '结账当前日期',
		            name: 'currentBalanceDate',
		            allowBlank:true,
		            value: null,
		            renderer: Ext.util.Format.dateRenderer('Y-m-d')
		        },{
		        	xtype:'displayfield',
		        	fieldLabel:'结账程序是否在运行',
		        	name:'isBalanceRun',
		        	allowBlank:true,
		        	value:null,
		        	renderer:function(value){
		        		if(Ext.isEmpty(value)){
		        			return '';
		        		}
		        		else if(value == 'true'){
		        			return '运行中...';
		        		}
		        		else if(value == 'false'){
		        			return '未运行';
		        		}
		        		else{
		        			return '';
		        		}
		        	}
		        }]
		    },{
		    	xtype:'datefield',
		    	name : 'endDate',
		    	fieldLabel:'截止时间',
		    	value: stl.getTargetDate(new Date(),-1),
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	columnWidth:.3
			},{
				xtype : 'button',
				text : '期末结账',
				itemId:'btnBalance',
				height:30,
				columnWidth : .2,
				disabled:!closing.balance.isPermission('/stl-web/closing/closeBalance.action'),
				hidden:!closing.balance.isPermission('/stl-web/closing/closeBalance.action'),
				handler : function() {
					var form = this.up('form').getForm();
					var endDate = form.findField('endDate').getValue();
					closing.balance.balance(closing.balance.balanceType_balance,endDate);
				}
			}, {
				xtype : 'button',
				text : '期末反结账',
				itemId:'btnUnBalance',
				height:30,
				columnWidth : .2,
				disabled:!closing.balance.isPermission('/stl-web/closing/unCloseBalance.action'),
				hidden:!closing.balance.isPermission('/stl-web/closing/unCloseBalance.action'),
				handler : function() {
					var form = this.up('form').getForm();
					var endDate = form.findField('endDate').getValue();
					closing.balance.balance(closing.balance.balanceType_unbalance,endDate);
				}
			} ]
		} ],
		listeners:{
			boxready:function(th){
				var content = Ext.getCmp('T_closing-balanceIndex_content');
				var form = content.down('form');
				
				var currentBalanceDate = form.getForm().findField('currentBalanceDate');
				var isBalanceRun = form.getForm().findField('isBalanceRun');
				var btnBalance = form.getComponent('btnBalance');
				var btnUnBalance = form.getComponent('btnUnBalance');
				
				Ext.Ajax.request({
					url:closing.realPath('balanceCurrentInfo.action'), 
					method:'post',
					success:function(response){
						var json = Ext.decode(response.responseText);
						currentBalanceDate.setValue(new Date(json.balanceVo.currentBalanceDate));
						
						balanceRun = false;
						if(json.balanceVo.balanceRun){
							balanceRun = true;
						}
						isBalanceRun.setValue(balanceRun);
						
						if(balanceRun){
							btnBalance.disable();
							btnUnBalance.disable();
						}
					},
					failure:function(response){
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert('温馨提示', json.message);
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert('温馨提示', json.message);
					}
			    });
			}
		}
	}));
});