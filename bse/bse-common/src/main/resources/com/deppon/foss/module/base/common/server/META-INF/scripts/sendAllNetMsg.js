/**
 * 全网消息发送
 */
common.sendAllNetMsg.submit=function(){
	var form = this.up('form').getForm();
	if(form.isValid()){
		var orgCodeObj=form.findField('msgVo.msgJobEntity.receiveOrgCode');
		form.findField('msgVo.msgJobEntity.receiveOrgName').setValue(orgCodeObj.rawValue);
		var availableDate = form.findField('msgVo.msgJobEntity.expireTime').getValue();
		var beginDate = Ext.Date.format(new Date(), 'Y-m-d H:i:s');
		if(!Ext.isEmpty(availableDate)){
			if(beginDate > availableDate){
			Ext.Msg.alert(common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.warmTips'),common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.availableDateCanNotGreaterThanBeginDate'));
			return false;
		}
		}
		Ext.Ajax.request({
			url:common.realPath('msgSend.action'),
			params:form.getValues(),
			success : function(response) { 
				var json = Ext.decode(response.responseText); 
				Ext.MessageBox.alert(common.sendAllNetMsg.i18n('foss.common.share.alertInfo'), json.message); 
				form.reset();
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(common.sendAllNetMsg.i18n('foss.common.share.alertInfo'), json.message);
			},
			failure:function(response){
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(common.sendAllNetMsg.i18n('foss.common.share.alertInfo'), json.message);
			}
		}); 
	}
}      

//全网消息发送表单
Ext.define('Foss.Messages.SendMsgForm',{
	extend:'Ext.form.Panel',
	title:common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.allNetMsgSend'),
	frame:true,
	collapsible:true,
	animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	},
	items:[{
				xtype : 'dynamicorgcombselector',
				fieldLabel : common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.receiveOrgCode'),
				name : 'msgVo.msgJobEntity.receiveOrgCode',
		        allowBlank: false,
				listeners:{
					change:function(_this,newValue,oldValue, eOpts){
						var form=_this.up('form').getForm();
						var obj_expireTime=form.findField('msgVo.msgJobEntity.expireTime');
						if(newValue=='DIP'){
							obj_expireTime.setDisabled(false);
						}else{
							obj_expireTime.setDisabled(true);
							obj_expireTime.setRawValue('');
						}
					}
				}
		  	},{
				name : 'msgVo.msgJobEntity.receiveOrgName',
				fieldLabel : common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.receiveOrgName'),
				hidden : true
			},{
				xtype:'datetimefield_date97',
				fieldLabel:common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.validTime'),
				id:'Foss_Messages_SendMsgForm_ExpireTime_ID',
				time:true,
				name:'msgVo.msgJobEntity.expireTime',
				editable:'false',
				allowBlank:false,
				dateConfig: {
					el: 'Foss_Messages_SendMsgForm_ExpireTime_ID-inputEl',
					dateFmt: 'yyyy-MM-dd HH:mi:ss'
				}
		  },{
	 			fieldLabel:common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.sendContent'), 
	 			name:'msgVo.msgJobEntity.context',
	 			width:490,
	 			allowBlank:false,
	 			xtype:'textareafield',
	 			maxLength:900,
	 			colspan:2
	 	  },{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:common.sendAllNetMsg.i18n('foss.common.share.reset'),
				  columnWidth:.12,
				  disabled:!common.sendAllNetMsg.isPermission('allNetMsgSendInit/allNetMsgSendInitSendButton'),
				  hidden:!common.sendAllNetMsg.isPermission('allNetMsgSendInit/allNetMsgSendInitSendButton'),
				  handler:function(){
					 this.up('form').getForm().reset();
					 this.up('form').getForm().findField('msgVo.msgJobEntity.expireTime').setDisabled(false);
				  }
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.76
				},
			  	{
				  text:common.sendAllNetMsg.i18n('foss.common.sendAllNetMsg.send'),
				  columnWidth:.12,
				  cls:'yellow_button',  
				  disabled:!common.sendAllNetMsg.isPermission('allNetMsgSendInit/allNetMsgSendInitSendButton'),
				  hidden:!common.sendAllNetMsg.isPermission('allNetMsgSendInit/allNetMsgSendInitSendButton'),
				  handler:common.sendAllNetMsg.submit
			  	}]
		}]  
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_common-allNetMsgSendInit_content')) {
		return;
	} 
	common.sendAllNetMsg.sendMsgForm = Ext.create('Foss.Messages.SendMsgForm');//查询FORM
	Ext.getCmp('T_common-allNetMsgSendInit').add(Ext.create('Ext.panel.Panel', {
		id : 'T_common-allNetMsgSendInit_content',
		cls:"panelContentNToolbar", //必须添加，内容定位用。
		bodyCls:'panelContentNToolbar-body', //必须添加，内容定位用。
		//获得查询FORM
		getQueryForm : function() {
			return common.sendAllNetMsg.sendMsgForm; 
		}, 
		items: [common.sendAllNetMsg.sendMsgForm]
	   }));

});

