Ext.define('Foss.ababdonGoodsApplication.applyForm', {
    extend: 'Ext.form.Panel',
    height: 278,
    width: 567,
    layout: {
        type: 'column'
    },
    bodyPadding: 10,
    title: '新增预弃货信息',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
				{
				    xtype: 'hiddenfield',
				    name: 'tSrvExceptionId'	
				},
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '运单号',
                    readOnly: true,
                    name: 'waybillNo'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '始发部门',
                    readOnly: true,
                    name: 'origOrgName'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '发货人',
                    readOnly: true,
                    name: 'deliveryCustomerName'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '发货人手机',
                    readOnly: true,
                    name: 'deliveryCustomerMobilephone'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '体积(方)',
                    readOnly: true,
                    name: 'goodsVolumeTotal'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '入库时间',
                    readOnly: true,
                    name: 'inStockTime'
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '仓储时长',
                    readOnly: true,
                    name: ''
                },
                {
                    xtype: 'textfield',
                    columnWidth: .5,
                    fieldLabel: '操作人',
                    readOnly: true,
                    name: 'operator'
                },
                {
                    xtype: ''filefield'',
                    columnWidth: 1,
                    fieldLabel: '弃货证明',
                    buttonConfig: {
                    	text: '浏览'
                    }
                },
                {
                    xtype: 'button',
                    text: '提交'
                    	handler: function() {
            				var form = this.up('form').getForm();
            				if (form.isValid()) { 
            	        		var new_record = form.getRecord();
            	        		form.updateRecord(new_record);
            	        		Ext.Ajax.request({
            	        			url : '../deliver/createAbandonGoodsApplication.action',
            	        			jsonData:{"vo":{"abandonGoodsApplicationDto":new_record}},
            	        			success : function(response) {
            	        				var json = Ext.decode(response.responseText);
            	        				
            	        			},
            	        			exception : function(response) {
            	        				var json = Ext.decode(response.responseText);
            	        				top.Ext.MessageBox.alert(lineinfo.i18n('foss.lineinfo.error'),json.message);
            	        			}
            	        		});
            				}
            			}
                },
                {
                    xtype: 'button',
                    text: '关闭'
                }
            ]
        });

        me.callParent(arguments);
    }

});
Ext.onReady(function() {
	// 1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	// 用法：模块名.自定义变量
	// 2.对象都是用Ext.define定义的方式声明
});
