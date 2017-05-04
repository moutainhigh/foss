consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__CASH='CH'; //现金
consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__CARD='CD'; //银行卡
consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER='TT'; //电汇
consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__NOTE='NT';//支票
consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__ONLINE='OL';//在线支付

Ext.define('Foss.codAudit.codAuditModel', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id',
        type: 'String'
    }, {
        name: 'waybillNo',
        type: 'String'
    }, {
        name: 'codAmount',
        type: 'String'
    },{
        name: 'destOrgName',
        type: 'String'
    },{
        name: 'origOrgCode',
        type: 'String'
    },{
        name: 'destOrgCode',
        type: 'String'
    },{
        name: 'origEquesDest',
        type: 'String',
        convert: function (value, record) {
            if (record.data.origOrgCode == record.data.destOrgCode) {
                return 'Y';
            } else {
                return 'N'
            }
        }
    }, {
        name:'changeAmount',
        type:'String'
    },{
        name:'billSignDiffer',
        type:'String'
    },{
        name: 'signBillDiffer',
        type: 'int'
    }, {
        name: 'hasTrack',
        type: 'String'
    }, {
        name: 'codType',
        type: 'Strings'
    }, {
        name: 'paymentType',
        type: 'String'
    }, {
        name: 'lockStatus',
        type: 'String'
    }, {
    	name:'billTime',
    	type:'date',
		convert: dateConvert,
		defaultValue:null
    },{
        name: 'comfirmTime',
        type: 'date',
		convert: dateConvert,
		defaultValue:null
    }, {
        name: 'sigTime',
        type: 'date',
		convert: dateConvert,
		defaultValue:null
    }, {
        name: 'origOrgName',
        type: 'String'
    }, {
        name: 'customerCode',
        type: 'String'
    }, {
        name: 'customerName',
        type: 'String'
    }, {
        name: 'mobileNo',
        type: 'String'
    }, {
        name: 'accountNo',
        type: 'String'
    },{
        name: 'bank',
        type: 'String'
    },{
        name:'codAmountOfCH',//现金金额
        type: 'String'
    },{
        name:'codAmountOfCD',//银行卡金额
        type: 'String'
    },{
        name:'codAmountOfTT',//电汇金额
        type: 'String'
    },/*{
        name: 'manageDepartment',//经营本部
        type: 'String'
    },*/{
        name: 'destCashierName',//到达部门收银员姓名
        type: 'String'
    },{
        name: 'destMobilePhone',//到达部门电话
        type: 'String'
    },{
        name: 'destMobilePhone',//到达部门电话
        type: 'String'
    },{
        name: 'codFCAmount',//代收货款到付金额
        type: 'String'
    }/*,{
    	name:'batchNo',
    	type:'String'
    }*/]
})
Ext.define('Foss.codAudit.codAuditStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.codAudit.codAuditModel',
    pageSize: 20,
    proxy: {
        type:'ajax',
        actionMethods:'post',
        url:consumer.realPath('queryByCondition.action'),
        reader:{
            type:'json',
            root:'vo.codAuditList',
            totalProperty:'totalCount'
        }

    }
})
/**
 * 定义下拉框中的store 是否 全部
 */
Ext.define('Foss.codAudit.YESandNo.Model',{
    extend:'Ext.data.Model',
    fields:[{name:'name',type:'String'},
        {name:'value',type:'String'}]
})
Ext.define('Foss.codAudit.YESandNO.store',{
    extend:'Ext.data.Store',
    model:'Foss.codAudit.YESandNo.Model',
    data:[{value:'',name:'全部'},{value:'Y',name:'是'},{value:'N',name:'否'}],
    proxy:{
        type:'memory',
        reader:{
            type:'json',
            root:'items'
        }
    }
})
/**
 * 代收货款额度
 * @218392 张永雪
 * @2015-12-28 14:17:26
 */
Ext.define('Foss.codAudit.CodAmount.store',{
    extend:'Ext.data.Store',
    model:'Foss.codAudit.YESandNo.Model',
    data:[{value:'',name:'全部'},{value:'50000',name:'≥50000'}],
    proxy:{
        type:'memory',
        reader:{
            type:'json',
            root:'items'
        }
    }
})
/**
 * 签收开单时长
 * @218392 zhangyongxue 
 * @date 2015-12-28 14:34:50
 */
 Ext.define('Foss.codAudit.SignLong.store',{
	    extend:'Ext.data.Store',
	    model:'Foss.codAudit.YESandNo.Model',
	    data:[{value:'',name:'全部'},{value:'12',name:'≤12小时'}],
	    proxy:{
	        type:'memory',
	        reader:{
	            type:'json',
	            root:'items'
	        }
	    }
	})

Ext.define('Foss.codAudit.lockStatus.store',{
    extend:'Ext.data.Store',
    model:'Foss.codAudit.YESandNo.Model',
    data:[{value:'',name:'全部'},{value:'FA',name:'资金部待审核'},{value:'FL',name:'资金部锁定'},{
        value:'RA',name:'复核会计待审核'},{value:'RL',name:'复核会计锁定'},{value:'RO',name:'审核通过'},
        {value:'SLL',name:'长期冻结'},{value:'SSL',name:'冻结'}//@218392 zhangyongxue长期未退款长期冻结、短期冻结
    ],
    proxy:{
        type:'memory',
        reader:{
            type:'json',
            root:'items'
        }
    }

})

consumer.lockStatusConvert = function(key){
    //如果不存在则创建
    if(typeof CodAuditLockStatus ==='undefined'){
        CodAuditLockStatus = new Ext.util.MixedCollection();
        Ext.create('Foss.codAudit.lockStatus.store').each(function(){
            var me = this;
            CodAuditLockStatus.add(me.data.value,me.data.name);
        });
    }

    return CodAuditLockStatus.get(key);

}
/**
 * 根据条件查询
 * @param params
 */
consumer.queryByCondition = function(params){
    var grid = Ext.getCmp('Foss_codAudit_queryResult');
    grid.setSubmitParams(params);
    grid.store.loadPage(1,{
        callback : function(records, operation, success) {
            var rawData = grid.store.proxy.reader.rawData;
            if(!success && !rawData.isException){
                Ext.Msg.alert('提示',rawData.message);
                return false;
            }
            if(success){
                var result = Ext.decode(operation.response.responseText);
                Ext.getCmp('unlockAuditTotalCount').setValue(result.vo.unlockCount);
                Ext.getCmp('lockAuditTotalCount').setValue(result.vo.lockCount);
                Ext.getCmp('auditTotalCount').setValue(result.vo.totalCount);
                Ext.getCmp('shortFreezeCount').setValue(result.vo.shortFreeze);
                Ext.getCmp('longFreezeCount').setValue(result.vo.longFreeze);
            }
        }});
}

Ext.define('Foss.codAudit.queryByCondition', {
    extend: 'Ext.form.Panel',
    frame: 'false',
    id:'Foss_codAudit_queryByConditionID',
    layout: {type: 'column'},
    defaults: {margin: '10 0 0 0', columnWidth: .25},
    autoScroll : true,
    items: [ {
        xtype: 'combo',
        name: 'codType',
        fieldLabel: '代收货款类型',
        store: FossDataDictionary.getDataDictionaryStore('COD__COD_TYPE', null, {
            'valueCode': '',
            'valueName': consumer.codAudit.i18n('foss.stl.consumer.common.all')
        }),
        editable: false,
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        value: ''
    },{
        //@author 218392 zhangyongxue 2015-12-28 15:15:15 截止签收时间
		xtype : 'datetimefield_date97',
		name: 'signEndDate',
		fieldLabel : '截止签收时间',
		id : 'FOSS_Consumer_CodAudit_SignEndDate_ID',
		time : true,
		editable : 'false',
		value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.START_PREFIX,
		dateConfig : {
			el : 'FOSS_Consumer_CodAudit_SignEndDate_ID-inputEl',
			dateFmt : 'yyyy-MM-dd HH:mi:ss'
		}
    }, {
        xtype: 'numberfield',
        name: 'amountBegin',
        fieldLabel: '退款金额（起）',
        maxValue: 99999999,
        minValue: 0,
        value: '0'
    }, {
        xtype: 'numberfield',
        name: 'amountEnd',
        fieldLabel: '退款金额（止）',
        maxValue: 99999999,
        minValue: 0,
        value: '99999999'
    }, {
        xtype: 'combo',
        name: 'paymentType',
        fieldLabel: '支付方式',
        store: FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE, null,
            {'valueCode': '','valueName': '全部'},
            [consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__CASH='CH', //现金
                consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__CARD='CD',//银行卡
                consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER='TT'//电汇
//                consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__NOTE='NT',//支票
//                consumer.codAudit.SETTLEMENT__PAYMENT_TYPE__ONLINE='OL'//在线支付
            ]),
        editable: false,
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        value: ''
    }, {
//        xtype: 'numberfield',
//        name: 'signLong',
//        fieldLabel: '签收开单时长',
//        //labelWidth:120,
//        maxValue: 99999,
//        minValue: 0,
//        value: 0
        xtype: 'combo',
        name: 'signLong',
        fieldLabel: '签收开单时长',
        store: Ext.create('Foss.codAudit.SignLong.store'),//@218392 张永雪 2015-12-28 14:30:24
        queryModel: 'local',
        displayField: 'name',
        valueField: 'value',
        value: '',
        /*renderer:function(value){
			var param = value;
			return param.toString();
	}	*/
    }, {
        xtype: 'combo',
        name: 'lockStatus',
        fieldLabel: '审核状态',
        store: Ext.create('Foss.codAudit.lockStatus.store'),
        queryModel: 'local',
        displayField: 'name',
        valueField: 'value',
        value: ''
    }, {
        xtype: 'linkagecomboselector',
        eventType: ['focus'],
        itemId: 'Foss_baseinfo_BigRegion_ID',
        store: Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),
        fieldLabel: '到达部门名称',
        value: '',
        minChars: 0,
        displayField: 'name',
        valueField: 'code',
        queryParam: 'commonOrgVo.name',
        name: 'destOrg',
        allowBlank: true,
        isPaging: true
    },{
    	//@310970  2016-3-28
        xtype: 'commonbankmultiselector',
        fieldLabel: '开户行',
        value: '',
        minChars: 0,
        displayField: 'name',
        valueField: 'code',
        name: 'bankList',
        headOffice:'Y',
        allowBlank: true,//
        showContent : '{name}',
        isPaging: true//fen分页
    }, {
        xtype: 'combo',
        name: 'changeAmount',
        fieldLabel: '代收更改额度',
        store: Ext.create('Foss.codAudit.CodAmount.store'),//@218392 张永雪 2015-12-28 14:12:09
        queryModel: 'local',
        displayField: 'name',
        valueField: 'value',
        value: ''
    }, {
        xtype: 'combo',
        name: 'hasTrack',
        fieldLabel: '有无货物轨迹',
        store: Ext.create('Foss.codAudit.YESandNO.store'),
        queryModel: 'local',
        displayField: 'name',
        valueField: 'value',
        value: ''
    }, {
        xtype: 'combo',
        fieldLabel: '出发到达部门是否相同',
        labelWidth:140,
        name: 'origEquesDest',
        store: Ext.create('Foss.codAudit.YESandNO.store'),
        queryModel: 'local',
        displayField: 'name',
        valueField: 'value',
        value: ''
    },/*{//@310970  2016-3-28
        xtype: 'orgCombSelector',
        fieldLabel: '经营本部',
        value: '',
        isManageDepartment:'Y',
        minChars: 0,
        displayField: 'name',
        valueField: 'code',
        name: 'manageDepartmentList',
        allowBlank: true,
        isPaging: true
    }, */{
        border: 1,
        xtype: 'container',
        columnWidth: 1,
        defaultType: 'button',
        layout: 'column',
        margin: '20 0 0 0',
        items: [{
            text: consumer.codAudit.i18n('foss.stl.consumer.common.reset'),
            columnWidth: .08,
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .54
        }, {
            text: consumer.codAudit.i18n('foss.stl.consumer.common.query'),
            cls: 'yellow_button',
            columnWidth: .08,
            handler: function () {
            	
            	consumer.initQueryParms();
                
            }
        }]
    }]
})
Ext.define('Foss.codAudit.queryByWaybillNos', {
    extend: 'Ext.form.Panel',
    frame: 'false',
    id:'Foss_codAudit_queryByWaybillNosID',
    layout: {type: 'column'},
    defaults: {margin: '10 0 0 0', columnWidth: .25},
    items: [{
        xtype: 'textarea',
        name: 'waybillNos',
        columnWidth: .7,
        height: 100,
        emptyText: consumer.codAudit.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen'),
        allowBlank: false,
        regex: /^([0-9]{7,14})(,[0-9]{7,14}){0,9},?$/i,
        regexText: consumer.codAudit.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen')
    }, {
        border: 1,
        xtype: 'container',
        columnWidth: 1,
        defaultType: 'button',
        layout: 'column',
        items: [{
            text: consumer.codAudit.i18n('foss.stl.consumer.common.reset'),
            columnWidth: .08,
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .54
        }, {
            text: consumer.codAudit.i18n('foss.stl.consumer.common.query'),
            cls: 'yellow_button',
            columnWidth: .08,
            handler: function () {
                var form = this.up('form').getForm();
                if(form.isValid()){
                    var waybillNos = form.findField('waybillNos').getValue();
                    var waybillArray = waybillNos.split(',');
                    var params = {'vo.queryWaybillNos':waybillArray,'vo.activeTab':0};
                    consumer.queryByCondition(params);
                }else {
                    Ext.MessageBox.alert('提示信息','检查收入信息');
                }
            }
        }]
    }]
})
Ext.define('Foss.codAudit.queryTabs', {
    extend: 'Ext.tab.Panel',
    bodyCls: 'autoHeight',
    cls: 'innerTabPanel',
    activeTab: 0,
    height: 270,
    items: [{
        title: '按单号查询',
        tabConfig: {width: 150},
        layout: 'fit',
        name:'queryByWaybillNos',
        items: [Ext.create('Foss.codAudit.queryByWaybillNos')]
    }, {
        title: '按条件查询', tabConfig: {width: 150},
        name:'queryByCondition',
        layout: 'fit', items: [Ext.create('Foss.codAudit.queryByCondition')]
    }]
})
/*win.down('textarea[name=////]').getValue();*/  
//定义资金部审核按钮弹出时的审核意见框
/*Ext.define('Foss.codAudit.AddcodAuditWin', {
	extend : 'Ext.window.Window',
	id:'Foss_codAudit_AddcodAuditWin_id',
	title : '审核意见框',
	modal : true,
	resizable : false,
	closeAction: 'hide',
	items : [{
		xtype : 'form',
		items: [{
			xtype : 'textarea',
			fieldLabel: '审核意见',
			width:360,
			name : 'codAuditSuggestion',
			labelWidth: 60,	
			hight: 18
		},{
			xytpe: 'container',
			layout: 'hbox',
			items: [ {
				xtype:'filefield',
				fieldLabel: '附件',
				labelWidth: 60,	
				name:'uploadFile',
				buttonText: '浏览',
			    border:1
			}, {
				xtype : 'button',
				text: '删除',
				height: 22,
				margin: '5 0 0 5',
				width:70,
				handler : function(btn) {
					btn.previousSibling('filefield').reset();
				}
			}, {
				xtype: 'hidden',
				name: 'vo.queryWaybillNos'
				
			}, {
				xtype: 'hidden',
				name: 'vo.auditStatus'
			}]
		}],
		buttons: [{
			text: '确认',
			handler : function(btn) {
				 var form = this.up('form').getForm();
		           if(form.isValid()){
		                form.submit({
		                    url: consumer.realPath('RAFundAudit.action'),
		                    method: 'POST',
		                    waitMsg: '请稍等,正在审核',
		                    success: function(fp, o) {
		                        Ext.Msg.alert('Success', 'Your File "' + o.result.file + '" has been uploaded.');
		                    },
		                    success:function(response){
		                        //获取响应的json字符串
		                        consumer.queryByCondition(Ext.getCmp('Foss_codAudit_queryResult').submitParams);
		                    	consumer.initQueryParms();
		                        //导出失败
		                        Ext.Msg.alert('提示信息','审核成功');
		                    },
		                    failure:function(response){
		                        var json = Ext.decode(response.responseText);
		                        Ext.MessageBox.alert('提示信息',json.message);
		                    },
		                    exception : function(response) {
		                        var json = Ext.decode(response.responseText);
		                        Ext.MessageBox.alert('提示信息',json.message);
		                    }
		                });
		            }
			}
		},{
			text: '取消',
			handler : function(btn) {
				btn.up('window').close();
			}
		}]
	}]
,
	constructor: function(config){			}
		}
	]
  }]
});

		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});*/
//定义会计组审核
/*Ext.define('Foss.codAudit.ReviewcodAuditWin', {
	extend : 'Ext.window.Window',
	id:'Foss_codAudit_ReviewcodAuditWin_id',
	title : '审核意见框',
	modal : true,
	resizable : false,
	closeAction: 'hide',
	items : [{
		xtype : 'form',
		items: [{
			xtype : 'textarea',
			fieldLabel: '审核意见',
			width:360,
			name : 'codAuditSuggestion',
			labelWidth: 60,	
			hight: 18
		},{
			xytpe: 'container',
			layout: 'hbox',
			items: [ {
				xtype:'filefield',
				fieldLabel: '附件',
				labelWidth: 60,	
				name:'uploadFile',
				buttonText: '浏览',
			    border:1
			}, {
				xtype : 'button',
				text: '删除',
				height: 22,
				margin: '5 0 0 5',
				width:70,
				handler : function(btn) {
					btn.previousSibling('filefield').reset();
				}
			}, {
				xtype: 'hidden',
				name: 'vo.queryWaybillNos'
				
			}, {
				xtype: 'hidden',
				name: 'vo.auditStatus'
			}]
		}],
		buttons: [{
			text: '确认',
			handler : function(btn) {
				 var form = this.up('form').getForm();
		           if(form.isValid()){
		                form.submit({
		                    url:consumer.realPath('reviewFundAudit.action'),
		                    method: 'POST',
		                    waitMsg: '请稍等,正在审核',
		                    success: function(fp, o) {
		                        Ext.Msg.alert('Success', 'Your File "' + o.result.file + '" has been uploaded.');
		                    },
		                    success:function(response){
		                        //获取响应的json字符串
		                        consumer.queryByCondition(Ext.getCmp('Foss_codAudit_queryResult').submitParams);
		                    	consumer.initQueryParms();
		                        //导出失败
		                        Ext.Msg.alert('提示信息','审核成功');
		                    },
		                    failure:function(response){
		                        var json = Ext.decode(response.responseText);
		                        Ext.MessageBox.alert('提示信息',json.message);
		                    },
		                    exception : function(response) {
		                        var json = Ext.decode(response.responseText);
		                        Ext.MessageBox.alert('提示信息',json.message);
		                    }
		                });
		            }
			}
		},{
			text: '取消',
			handler : function(btn) {
				btn.up('window').close();
			}
		}]
	}]
,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});*/
consumer.initQueryParms=function(){
	var tabName=Ext.getCmp('Foss_codAudit_queryTabs').getActiveTab().name;
	if(tabName=='queryByWaybillNos'){
		var form = Ext.getCmp('Foss_codAudit_queryByWaybillNosID').getForm();
		if(form.isValid()){
            var waybillNos = form.findField('waybillNos').getValue();
            var waybillArray = waybillNos.split(',');
            var params = {'vo.queryWaybillNos':waybillArray,'vo.activeTab':0};
            consumer.queryByCondition(params);
        }else {
            Ext.MessageBox.alert('提示信息','检查收入信息');
        }
	}else{
		var form = Ext.getCmp('Foss_codAudit_queryByConditionID').getForm();
	    if(form.isValid()){
	        var params = {'vo.activeTab':1};
	        var  codType = form.findField('codType').getValue();//代收货款类型
	        Ext.apply(params,{'vo.queryDto.codType':codType});
	            
	        var  signEndDate= form.findField('signEndDate').getValue();//截止签收时间
	        Ext.apply(params,{'vo.queryDto.signTime':signEndDate});
	        
	        var  amountBegin = form.findField('amountBegin').getValue();//退款金额（起）
	        Ext.apply(params,{'vo.queryDto.codAmountBegin':amountBegin});
	        
	        var  amountEnd= form.findField('amountEnd').getValue();//退款金额（止）
	        Ext.apply(params,{'vo.queryDto.codAmountEnd':amountEnd});

	        var  paymentType = form.findField('paymentType').getValue();//支付方式
	        Ext.apply(params,{'vo.queryDto.paymentType':paymentType});

	        var  signLong= form.findField('signLong').getValue();
	        if(signLong){
	        	Ext.apply(params,{'vo.queryDto.signBillDiffer':signLong});
	        }
	            
	        var  lockStatus= form.findField('lockStatus').getValue();
	        Ext.apply(params,{'vo.queryDto.lockStatus':lockStatus});
	        
	        var  destOrg= form.findField('destOrg').getValue();
	        Ext.apply(params,{'vo.queryDto.destOrgCod':destOrg});

	        var  changeAmount= form.findField('changeAmount').getValue();
	        if(changeAmount){
	        	Ext.apply(params,{'vo.queryDto.codAmountDiffer':changeAmount});
	        }

	    
	        var  hasTrack= form.findField('hasTrack').getValue();
	        Ext.apply(params,{'vo.queryDto.hasTrack':hasTrack});
	        
	        var  origEquesDest= form.findField('origEquesDest').getValue();
	        Ext.apply(params,{'vo.queryDto.destEqOrig':origEquesDest});
	        
	        var  bankList= form.findField('bankList').getValue();
	        Ext.apply(params,{'vo.queryDto.bankList':bankList});
	        
	        /*var  manageDepartment= form.findField('manageDepartmentList').getValue();
	        Ext.apply(params,{'vo.queryDto.manageDepartmentList':manageDepartment});*/
	        
	        
	        
	        consumer.queryByCondition(params);
	    }else{
            Ext.MessageBox.alert('提示信息','检查收入信息');
        }
	
    }
}

Ext.define('Foss.codAudit.queryResult', {
    extend: 'Ext.grid.Panel',
    selModel:Ext.create('Ext.selection.CheckboxModel',{
        listeners : {
            //deselect:function(rthis,record,index,eOpts){
            //    rthis.view.getEl().dom.onclick = function(){
            //        Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').setValue(false);
            //        Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').setValue(false);
            //    };
        }

    }),
    frame: true,
    cls: 'autoHeight',
    bodyCls: 'autoHeight',
    height: 500,
    margin: '10 5 20 5',
    store:null,
    defaults: {},
    columns: [{
        xtype:'actioncolumn',
        width:73,
        text :'操作列',
        align: 'center',
        items:[{
            iconCls : 'foss_icons_stl_freeze',
            tooltip : '锁定',
            handler:function(grid, rowIndex, colIndex){
                var selection = grid.getStore().getAt(rowIndex);
                var waybillNos = [];
                var params = {};
                waybillNos.push(selection.get('waybillNo'));
                if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund')&&
                    consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund')){
                    Ext.Msg.alert('提示信息','不能同时拥有资金部权限和审核会计权限');
                    return;
                }else if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund')){
                    params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'FL'};
                }else if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund')){
                    params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RL'};
                }else{
                    Ext.Msg.alert('提示信息','没有权限锁定');
                    return;
                }

                //导出Ajax请求
                Ext.Ajax.request({
                    //timeout: 1000,
                    url:consumer.realPath('fundLock.action'),
                    //form: Ext.fly('exportCodForm'),
                    params:params,
                    method:'post',
                    isUpload: true,
                    success:function(response){
                        //获取响应的json字符串
                       // consumer.queryByCondition(grid.submitParams);
                    	consumer.initQueryParms();
                        Ext.Msg.alert('提示信息','锁定成功');
                    },
                    failure:function(response){
                        var json = Ext.decode(response.responseText);
                        Ext.MessageBox.alert('提示信息',json.message);
                    },
                    exception : function(response) {
                        var json = Ext.decode(response.responseText);
                        Ext.MessageBox.alert('提示信息',json.message);
                    }
                });
            },
            getClass:function(v,m,r,rowIndex){// 根据代收货款类型判断显示哪种功能(锁定/取消锁定)
                if(r.get('lockStatus') ==='FL'
                    ||r.get('lockStatus')==='RL'){
                    return 'statement_consumer_hide';
                }else {
                    return 'foss_icons_stl_freeze';
                }
            }
        },{
            iconCls : 'foss_icons_stl_noFreeze',
            tooltip :'取消锁定',
            handler:function(grid, rowIndex, colIndex){
                var selection = grid.getStore().getAt(rowIndex);
                var params = {};
                var waybillNos = [];
                waybillNos.push(selection.get('waybillNo'));
                if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund')&&
                    consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund')){
                    Ext.Msg.alert('提示信息','不能同时拥有资金部权限和审核会计权限');
                    return;
                }else if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund')){
                    params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'FCL'};
                }else if(consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund')){
                    params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RCL'};
                }else{
                    Ext.Msg.alert('提示信息','没有权限取消锁定');
                    return;
                }
                //取消冻结Ajax
                Ext.Ajax.request({
                    //timeout: 1000,
                    url:consumer.realPath('fundLock.action'),
                    params:params,
                    method:'post',
                    isUpload: true,
                    success:function(response){
                        //获取响应的json字符串
                      //  consumer.queryByCondition(grid.submitParams);
                    	consumer.initQueryParms();
                        //导出失败
                        Ext.Msg.alert('提示信息','锁定成功');
                    },
                    failure:function(response){
                        var json = Ext.decode(response.responseText);
                        Ext.MessageBox.alert('提示信息',json.message);
                    },
                    exception : function(response) {
                        var json = Ext.decode(response.responseText);
                        Ext.MessageBox.alert('提示信息',json.message);
                    }
                });
            },
            getClass:function(v,m,r,rowIndex){// 根据代收货款类型判断显示哪种功能(锁定/取消锁定)
                if(r.get('lockStatus')!='FL'
                    &&r.get('lockStatus')!='RL'){
                    return 'statement_consumer_hide';
                }else {
                    return 'foss_icons_stl_noFreeze';
                }
            }
        }
        ]
    },{
        header: 'id',
        dataIndex: 'id',
        hidden: true
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.waybillNo'),
        dataIndex: 'waybillNo'
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.codAmount'),
        dataIndex: 'codAmount',
        xtype:'numbercolumn',
        format:'0.00'	
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.destOrgName'),
        dataIndex: 'destOrgName'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.codAmountOfCH'),//现金金额
        dataIndex: 'codAmountOfCH',
        xtype:'numbercolumn',
        format:'0.00'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.codAmountOfCD'),//银行卡金额
        dataIndex: 'codAmountOfCD',
		 xtype:'numbercolumn',
	     format:'0.00'        	
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.codAmountOfTT'),//电汇金额
        dataIndex: 'codAmountOfTT',
    	 xtype:'numbercolumn',
         format:'0.00'
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.origEquesDest'),
        dataIndex: 'origEquesDest',
        renderer:function(value){if('Y'== value){return '是'}else {return '否'}}
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.signBillDiffer'),
        dataIndex: 'billSignDiffer'
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.codChangeAmount'),
        dataIndex: 'changeAmount',
        xtype:'numbercolumn',
        format:'0.00'	
    }, {
    	header: '有无货物轨迹',
    	dataIndex: 'hasTrack',
    	renderer:function(value){
    		if('Y'==value){
    			return '是'
    		}else{
    			return '否'
    		}
    	}
    },{
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.codType'),
        dataIndex: 'codType',
        renderer:function(value){
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__COD_TYPE');
            return displayField;
        }
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.paymentType'),
        dataIndex: 'paymentType',
        renderer:function(value){
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'SETTLEMENT__PAYMENT_TYPE');
            return displayField;
        }
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.lockStatus'),
        dataIndex: 'lockStatus',
        renderer:function(value){return consumer.lockStatusConvert(value)}
    }, {
    	header : consumer.codAudit.i18n('foss.stl.consumer.codAudit.billTime'),
    	dataIndex:'billTime',
        renderer:function(value){
            if(!Ext.isEmpty(value)){
                return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
            }else{
                return value;
            }
        }
    },{
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.comfirmTime'),
        dataIndex: 'comfirmTime',
        renderer:function(value){
            if(!Ext.isEmpty(value)){
                return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
            }else{
                return value;
            }
        }
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.sigTime'),
        dataIndex: 'sigTime',
        align : 'center',
        renderer:function(value){
            if(!Ext.isEmpty(value)){
                return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
            }else{
                return null;
            }

        }
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.origOrgName'),
        dataIndex: 'origOrgName'
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.customerName'),
        dataIndex: 'customerName'
    }, {
        header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.mobileNo'),
        dataIndex: 'mobileNo'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.codAudit.accountNo'),
        dataIndex: 'accountNo'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.bank'),//开户行
        dataIndex: 'bank'
    }, /*{header: consumer.codAudit.i18n('foss.stl.consumer.cod.manageDepartment'),//经营本部
        dataIndex: 'manageDepartment'
    },*/ {header: consumer.codAudit.i18n('foss.stl.consumer.cod.codFCAmount'),//代收货款到付金额
        dataIndex: 'codFCAmount',
    	 xtype:'numbercolumn',
         format:'0.00'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.destCashierName'),//到达部门收银员姓名
        dataIndex: 'destCashierName'
    }, {header: consumer.codAudit.i18n('foss.stl.consumer.cod.destMobilePhone'),//到达部门电话
        dataIndex: 'destMobilePhone'
    }/*, {header: consumer.codAudit.i18n('foss.stl.consumer.otherRevenue.posBatchNo'),//交易流水号
    	dataIndex: 'batchNo'
    }*/],
    tbar: [{
        xtype: 'checkboxfield',
        boxLabel: '全选已锁定',
        id:'codAuditSelectAllLok',
        listeners: {
            render: function (el) {
                el.getEl().addCls('label-class-green');
            },
            afterrender: function (obj) {
                obj.getEl().dom.onclick = function(){
                    var checkLock =  Ext.getCmp('codAuditSelectAllLok');
                    var checkUnLock =  Ext.getCmp('codAuditSelectAllUnlok');
                    checkUnLock.setValue(false);
                    var grid = Ext.getCmp('Foss_codAudit_queryResult');
                    var selectModel = grid.getSelectionModel();
                    var store = grid.getStore();
                    var optRows =[];
                    for(var i = 0;i<store.count();i++){
                        var tmpRow  = store.getAt(i);
                        if(tmpRow.get('lockStatus') ==='FL'
                            ||tmpRow.get('lockStatus')==='RL'){
                            optRows.push(tmpRow);
                        }
                        selectModel.deselectAll();

                        if(checkLock.getValue()){
                            selectModel.select(optRows);
                        }
                    }


                }
            }
        }
    },{
        xtype: 'container',
        columnWidth: 0.05,
        width: 10,
        html: '&nbsp;',
        border: 0
    }, {
        xtype: 'checkboxfield',
        boxLabel: '全选未锁定',
        id: 'codAuditSelectAllUnlok',
        listeners: {
            render: function (el) {
                el.getEl().addCls('label-class-red');
            },
            afterrender: function (obj) {
                obj.getEl().dom.onclick = function(){
                    var checkUnLock =  Ext.getCmp('codAuditSelectAllUnlok');
                    var checkLock =  Ext.getCmp('codAuditSelectAllLok');
                    checkLock.setValue(false);
                    var grid = Ext.getCmp('Foss_codAudit_queryResult');
                    var selectModel = grid.getSelectionModel();
                    var store = grid.getStore();
                    var optRows =[];
                    for(var i = 0;i<store.count();i++){
                        var tmpRow  = store.getAt(i);
                        if(tmpRow.get('lockStatus') !='FL'
                            &&tmpRow.get('lockStatus')!='RL'){
                            optRows.push(tmpRow);
                        }
                        selectModel.deselectAll();

                        if(checkUnLock.getValue()){
                            selectModel.select(optRows);
                        }
                    }


                }
            }
        }
    }, {
        xtype: 'container',
        columnWidth: 0.05,
        width: 10,
        html: '&nbsp;',
        border: 0}, {
        xtype: 'button',
        text: '锁定',
        hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund'),
        handler: function () {
            var grid = Ext.getCmp('Foss_codAudit_queryResult');
            var selectionModel = grid.getSelectionModel();
            var rows = selectionModel.getSelection();
            var waybillNos = [];
            if(rows.length <1){
                Ext.MessageBox.alert('提示信息','至少选择一列数据进行锁定');
                return;
            }
            for(var i =0;i<rows.length;i++){
                waybillNos.push(rows[i].get('waybillNo'));
            }
            var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'FL'};
            Ext.Ajax.request({
                //timeout: 1000,
                url:consumer.realPath('fundLock.action'),
                //form: Ext.fly('exportCodForm'),
                params:params,
                method:'post',
                isUpload: true,
                success:function(response){
                    //重新刷新页面
                    //consumer.queryByCondition(grid.submitParams);
                	consumer.initQueryParms();

                    //获取响应的json字符串
                    Ext.MessageBox.alert('提示信息','锁定成功');

                },
                failure:function(response){
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                }
            });
        }
    }, {
        xtype: 'container',
        columnWidth: 0.05,
        width: 10,
        html: '&nbsp;',
        border: 0
    }, {
        xtype: 'button',
        text: '取消锁定',
        hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund'),
        handler: function () {
            var grid = Ext.getCmp('Foss_codAudit_queryResult');
            var selectionModel = grid.getSelectionModel();
            var rows = selectionModel.getSelection();
            var waybillNos = [];
            if(rows.length <1){
                Ext.MessageBox.alert('提示信息','至少选择一列数据进行取消锁定');
                return;
            }
            for(var i =0;i<rows.length;i++){
                waybillNos.push(rows[i].get('waybillNo'));
            }
            var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'FCL'};
            Ext.Ajax.request({
                //timeout: 1000,
                url:consumer.realPath('fundLock.action'),
                //form: Ext.fly('exportCodForm'),
                params:params,
                method:'post',
                isUpload: true,
                success:function(response){
                    //刷新界面
                   // consumer.queryByCondition(grid.submitParams);
                	consumer.initQueryParms();
                    //获取响应的json字符串
                    Ext.Msg.alert('提示信息','取消锁定成功');

                },
                failure:function(response){
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                }
            });
        }
    }, {
        xtype: 'button',
        text: '锁定',
        hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund'),
        handler: function () {
            var grid = Ext.getCmp('Foss_codAudit_queryResult');
            var selectionModel = grid.getSelectionModel();
            var rows = selectionModel.getSelection();
            var waybillNos = [];
            if(rows.length <1){
                Ext.MessageBox.alert('提示信息','至少选择一列数据锁定');
                return;
            }
            for(var i =0;i<rows.length;i++){
                waybillNos.push(rows[i].get('waybillNo'));
            }
            var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RL'};
            Ext.Ajax.request({
                ////timeout: 1000,
                url:consumer.realPath('reviewFundLock.action'),
                //form: Ext.fly('exportCodForm'),
                params:params,
                method:'post',
                isUpload: true,
                success:function(response){
                    //刷新页面
                   // consumer.queryByCondition(grid.submitParams);
                	consumer.initQueryParms();
                    //获取响应的json字符串
                    Ext.Msg.alert('提示信息','锁定成功');
                },
                failure:function(response){
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                }
            });
        }
    }, {
        xtype: 'container',
        columnWidth: 0.05,
        width: 10,
        html: '&nbsp;',
        border: 0
    }, {
        xtype: 'button',
        text: '取消锁定',
        hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund'),
        handler: function () {
            var grid = Ext.getCmp('Foss_codAudit_queryResult');
            var selectionModel = grid.getSelectionModel();
            var rows = selectionModel.getSelection();
            var waybillNos = [];
            if(rows.length <1){
                Ext.MessageBox.alert('提示信息','至少选择一列数据取消锁定');
                return;
            }
            for(var i =0;i<rows.length;i++){
                waybillNos.push(rows[i].get('waybillNo'));
            }
            var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RCL'};
            Ext.Ajax.request({
                ////timeout: 1000,
                url:consumer.realPath('reviewFundLock.action'),
                //form: Ext.fly('exportCodForm'),
                params:params,
                method:'post',
                isUpload: true,
                success:function(response){
                    //consumer.queryByCondition(grid.submitParams);
                	consumer.initQueryParms();
                    //导出失败
                    Ext.Msg.alert('提示信息','取消锁定成功');
                },
                failure:function(response){
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                }
            });
        }
    },{
        xtype: 'container',
        columnWidth: 0.05,
        width: 10,
        html: '&nbsp;',
        border: 0
    },{
        xtype: 'button',
        text: '取消冻结',//@218392 zhangyongxue @date 2016-07-08 15:51:00
        hidden:!consumer.codAudit.isPermission('/stl-web/consumer/shortcodAuditFundLock'),//@218392 zhangyongxue 
        handler: function () {
            var grid = Ext.getCmp('Foss_codAudit_queryResult');
            var selectionModel = grid.getSelectionModel();
            var rows = selectionModel.getSelection();
            var waybillNos = [];
            if(rows.length <1){
                Ext.MessageBox.alert('提示信息','至少选择一列数据取消锁定');
                return;
            }
            for(var i =0;i<rows.length;i++){
                waybillNos.push(rows[i].get('waybillNo'));
            }
            var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RSSL'};//复核会计组审核通过
            Ext.Ajax.request({
                ////timeout: 1000,
                url:consumer.realPath('reviewFundAudit.action'),
                //form: Ext.fly('exportCodForm'),
                params:params,
                method:'post',
                isUpload: true,
                success:function(response){
                    //consumer.queryByCondition(grid.submitParams);
                	consumer.initQueryParms();
                    //导出失败
                    Ext.Msg.alert('提示信息','取消冻结成功');
                },
                failure:function(response){
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('提示信息',json.message);
                }
            });
        }
    }],
initComponent:function(){
   var me = this;
   me.dockedItems = [{
			xtype: 'toolbar',
			dock: 'bottom',
			layout: 'column',
			defaults: {margin: '0 0 5 0'},
        items: [{
        	 height: 5, 
        	 columnWidth: 1
          },{
                xtype: 'container',
                columnWidth:0.48,
                border: 0,
                layout:'column',
                defaults:{  columnWidth:.2,labelWidth:75 },
                items:[
                    {
                        xtype: 'displayfield',
                        fieldLabel: '合计总条数',
                        name:'auditTotalCount',
                        id:'auditTotalCount',
                        allowBlank:true,
                        value: '0'
                    },{
                        xtype: 'displayfield',
                        fieldLabel: '锁定条数',
                        name:'lockAuditTotalCount',
                        id:'lockAuditTotalCount',
                        allowBlank:true,
                        value: '0'
                    },{
                        xtype: 'displayfield',
                        fieldLabel: '未锁定条数',
                        name:'unlockAuditTotalCount',
                        id:'unlockAuditTotalCount',
                        allowBlank:true,
                        value: '0'
                    },{
                        xtype: 'displayfield',
                        fieldLabel: '冻结条数',
                        name:'shortFreezeCount',
                        id:'shortFreezeCount',
                        allowBlank:true,
                        value: '0'
                    },{
                        xtype: 'displayfield',
                        fieldLabel: '永久冻结数',
                        name:'longFreezeCount',
                        id:'longFreezeCount',
                        allowBlank:true,
                        value: '0'
                    }
                   // Ext.getCmp('shortFreezeCount').setValue(result.vo.shortFreeze);
                    //Ext.getCmp('longFreezeCount').setValue(result.vo.longFreeze);
                ]

            },{
    			xtype:'standardpaging',
    			store:me.store,
    			columnWidth:0.52,
    			pageSize: 20,
    			plugins: Ext.create('Deppon.ux.PageSizePlugin', {
    				//设置分页记录最大值，防止输入过大的数值
    				maximumSize: 500
    			})
    		  },{xtype: 'container', columnWidth: .80, width: 10, html: '&nbsp;', border: 0},{
                xtype: 'button',
                hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditFund'),
                text: '审核',
                width: 54,
                columnWidth: 0.05,
                handler: function () {
                	var grid = Ext.getCmp('Foss_codAudit_queryResult');
                    var selectionModel = grid.getSelectionModel();
                    var rows = selectionModel.getSelection();
                    var waybillNos = [];
                    if(rows.length <1){
                        Ext.MessageBox.alert('提示信息','至少选择一列数据进行审核');
                        return;
                    }
                    for(var i =0;i<rows.length;i++){
                        waybillNos.push(rows[i].get('waybillNo'));
                    }
                    var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RA'};
                    Ext.Ajax.request({
                        //timeout: 1000,
                        url:consumer.realPath('fundAudit.action'),
                        //form: Ext.fly('exportCodForm'),
                        params:params,
                        method:'post',
                        isUpload: true,
                        success:function(response){

                            //consumer.queryByCondition(grid.submitParams);
                        	consumer.initQueryParms();
                            //导出失败
                            Ext.Msg.alert('提示信息','资金部审核通过');
                        },
                        failure:function(response){
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        },
                        exception : function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        }
                    });
                }
            }, {xtype: 'container', columnWidth: 0.02, width: 10, html: '&nbsp;', border: 0}, {
                xtype: 'button',
                text: '审核',
                hidden:!consumer.codAudit.isPermission('/stl-web/consumer/codAuditReviewFund'),
                width: 54,
                columnWidth: 0.05,
                handler: function () {
                	
                	  var grid = Ext.getCmp('Foss_codAudit_queryResult');
                      var selectionModel = grid.getSelectionModel();
                      var rows = selectionModel.getSelection();
                      var waybillNos = [];
                      if(rows.length <1){
                          Ext.MessageBox.alert('提示信息','至少选择一列数据进行审核');
                          return;
                      }
                      for(var i =0;i<rows.length;i++){
                          waybillNos.push(rows[i].get('waybillNo'));
                      }
                  
                	
                	/*var win;
                	if(!Ext.getCmp('Foss_codAudit_ReviewcodAuditWin_id')){
                		win=Ext.create('Foss.codAudit.ReviewcodAuditWin');
                	} else {
                		win = Ext.getCmp('Foss_codAudit_ReviewcodAuditWin_id');
                	}
                	
                	win.down('hidden[name=vo.queryWaybillNos]').setValue(waybillNos.toString());
                	win.down('hidden[name=vo.auditStatus]').setValue('RO');
                	win.show();*/
                  
                   var params = {'vo.queryWaybillNos':waybillNos,'vo.auditStatus':'RO'};
                    Ext.Ajax.request({
                        //timeout: 1000,
                        url:consumer.realPath('reviewFundAudit.action'),
                        //form: Ext.fly('exportCodForm'),
                        params:params,
                        method:'post',
                        isUpload: true,
                        success:function(response){
                            //获取响应的json字符串
                            //consumer.queryByCondition(grid.submitParams);
                        	consumer.initQueryParms();
                            //导出失败
                            Ext.Msg.alert('提示信息','复核会计审核成功');
                        },
                        failure:function(response){
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        },
                        exception : function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        }
                    });
                }
            }, {xtype: 'container', columnWidth: 0.02, width: 10, html: '&nbsp;', border: 0
            },{
                xtype: 'button',
                text: '导出',
                width: 54,
                columnWidth: 0.05,
                /**
                 * @author 218392 zhangyongxue 2015-01-12 15:10:00
                 * 线上BUG
                 */
                handler: function () {
                    var grid = Ext.getCmp('Foss_codAudit_queryResult');
                    var selectionModel = grid.getSelectionModel();
                    /**
                     * @author 218392 zhangyongxue 
                     * @date 2015-01-12 15:15:26
                     * 这里加上个判断如果是选择性的，那么传入后台的参数就以选择的为准；如果没有勾选，就以页面查询条件作为参数
                     * 为准进行导出。
                     */
                    var rows = selectionModel.getSelection();
                    var waybillNos = [];
                    if(rows.length > 0){
                        for(var i =0;i<rows.length;i++){
                            waybillNos.push(rows[i].get('waybillNo'));
                        }
                        var params = {'vo.queryWaybillNos':waybillNos};
                    }else{
                    	var params = grid.submitParams;
                    }
                    if(Ext.isEmpty(params)){
                        return ;
                    }
                    
                    //创建一个form
                    if(!Ext.fly('exportCodAuditForm')){
                        var frm = document.createElement('form');
                        frm.id = 'exportCodAuditForm';
                        frm.style.display = 'none';
                        document.body.appendChild(frm);
                    }
                    Ext.Ajax.request({
                        //timeout: 1000,
                        url:consumer.realPath('exprtExcel.action'),
                        form: Ext.fly('exportCodAuditForm'),
                        params:params,
                        method:'post',
                        isUpload: true,
                        success:function(response){
                            //获取响应的json字符串
                            var jsonText = Ext.decode(response.responseText.trim());
                            //导出失败
                            Ext.Msg.alert('提示信息','导出成功');
                        },
                        failure:function(response){
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        },
                        exception : function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.MessageBox.alert('提示信息',json.message);
                        }
                    });
                }
            }]
    }];
    me.callParent();
},   
    submitParams:null,
    setSubmitParams: function(submitParams){
        this.submitParams = submitParams;
    },
    constructor:function(config){
        var me = this,cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.codAudit.codAuditStore',{
            listeners : {
                // 数据加载前，form表单值 设置
                beforeload : function(store, operation, eOpts) {
                    Ext.apply(me.submitParams, {
                        "limit":operation.limit,
                        "page":operation.page,
                        "start":operation.start
                    });

                    Ext.apply(operation, {
                        params : me.submitParams
                    });

                }
            }
        });
        me.callParent([ cfg ]);

    }
})
Ext.onReady(function () {
    Ext.QuickTips.init();
    queryTab = Ext.getCmp('Foss_codAudit_queryTabs');
    if (!queryTab) {
        queryTab = Ext.create('Foss.codAudit.queryTabs', {id: 'Foss_codAudit_queryTabs'});
    }
    queryResultGrid = Ext.create('Foss.codAudit.queryResult', {id: 'Foss_codAudit_queryResult'});
    Ext.create('Ext.panel.Panel', {
        id: 'T_consumer-codAudit_content',
        cls: 'panelContentNToolbar',
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        items: [queryTab, queryResultGrid],
        renderTo: 'T_consumer-codAudit-body'
    });
});