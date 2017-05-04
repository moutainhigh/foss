//导入数据
pay.posExceptionManager.importData = function(){
	var form  = this.up('form').getForm();
	//输入单号集合
	var paramstr = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(paramstr)==null || Ext.String.trim(paramstr)==''){
		Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.notnullWarning'));
		return false;
	} 
	Ext.MessageBox.show({
		title: pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),
		msg: pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.isSureimport'),//
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				
				searchParams = {
					'vo.posCardManageDto.exceptionMessage':paramstr
				};
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: pay.realPath('importExceptionData.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var mess = result.vo.posCardManageDto.retMessage;
				    	Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),mess);
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);
						// 如果异常信息有值，则弹出提示
						if (!Ext.isEmpty(result.message)) {
							Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'), result.message);
							return false;
						}
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

//修改数据
pay.posExceptionManager.updateData = function(){
	var form  = this.up('form').getForm();
	//输入单号集合
	var paramstr = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(paramstr)==null || Ext.String.trim(paramstr)==''){
		Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.notnullWarning'));
		return false;
	} 
	Ext.MessageBox.show({
		title: pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),
		msg: pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.isSureUpdate'),//
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				
				searchParams = {
					'vo.posCardManageDto.exceptionMessage':paramstr
				};
				 
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: pay.realPath('updateExceptionData.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var mess = result.vo.posCardManageDto.retMessage;
				    	Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),mess);
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);
						// 如果异常信息有值，则弹出提示
						if (!Ext.isEmpty(result.message)) {
							Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'), result.message);
							return false;
						}
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

//删除数据
pay.posExceptionManager.deleteData = function(){
	var form  = this.up('form').getForm();
	//输入单号集合
	var paramstr = form.findField('serialNo').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(paramstr)==null || Ext.String.trim(paramstr)==''){
		Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.notnullWarning'));
		return false;
	}
	if(Ext.String.trim(paramstr).length != 12){
		Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.tradeSerialNosIsWarning'));
		return false;
	}
	Ext.MessageBox.show({
		title: pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),
		msg: pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.isSureDelete'),//
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
			if(e=='yes'){

				var searchParams;

				searchParams = {
					'vo.posCardManageDto.exceptionMessage':paramstr
				};

				//拼接vo，注入到后台
				Ext.Ajax.request({
					url: pay.realPath('deleteExceptionData.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : searchParams,
					isUpload : true,
					success : function(response,options){
						var result = Ext.decode(response.responseText);
						var mess = result.vo.posCardManageDto.retMessage;
						Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'),mess);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						// 如果异常信息有值，则弹出提示
						if (!Ext.isEmpty(result.message)) {
							Ext.Msg.alert(pay.posExceptionManager.i18n('foss.stl.pay.common.alert'), result.message);
							return false;
						}
					}
				});
			}else{
				return false;
			}
		}
	});
}

//创建查询表单
Ext.define('Foss.posExceptionManager.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:200,
	items:[{
        title: pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.importData'),
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.exceptionData'),
				columnWidth:.65,
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:80
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.message')+'</span>'
					}
				}]
		    },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:pay.posExceptionManager.i18n('foss.stl.pay.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.import'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.posExceptionManager.importData
				}]
        	}]
        }]
    },{
        title: '修改异常数据',
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.exceptionData'),
				columnWidth:.65,
				/*regex:/^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,*/
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:80
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.message')+'</span>'
					}
				}]
		    },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:pay.posExceptionManager.i18n('foss.stl.pay.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.update'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.posExceptionManager.updateData
				}]
        	}]
        }]
    },{
		title: '删除异常数据',
		tabConfig:{
			width:120
		},
		layout:'fit',
		items:[{
			xtype:'form',
			layout:'column',
			defaults:{
				margin:'5 5 5 5'
			},
			items:[{
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.deleteExceptionData'),
				columnWidth:.65,
				regex:/^([0-9]{12})$/,
				labelWidth:70,
				labelAlign:'right',
				name:'serialNo',
				autoScroll:true,
				height:80
			},{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.deleteMessage')+'</span>'
					}
				}]
			},{
				xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:pay.posExceptionManager.i18n('foss.stl.pay.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:pay.posExceptionManager.i18n('foss.stl.pay.posExceptionManager.delete'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.posExceptionManager.deleteData
				}]
			}]
		}]
	}]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	Ext.QuickTips.init();
	//查询表单
	var queryTab = Ext.create('Foss.posExceptionManager.QueryTab');
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-posExceptionManager_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getTab:function(){
			return queryTab;
		},
		items: [queryTab],
		renderTo: 'T_pay-posExceptionManager-body'
	});
});