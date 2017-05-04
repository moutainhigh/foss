/****************修改理货员货量信息  begin**************/
function getUUIDModify() {
	return Ext.Date.format(new Date(), 'Y-m-d H:i:s');
}
Ext.define('Foss.load.loaderworkmodify.loaderWorksModifyPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	defaultType: 'textfield',
	defaults: {
	    margin: '5 20 5 20'
	},
	items:[{
		name: 'id',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'taskNo',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'totWeightTemp',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'totWaybillQtyTemp',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'loaderName',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.loaderName'),
		columnWidth: .5,
		allowBlank: false,
		readOnly : true,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.loaderNameNotBeNull')
	}, {
		name: 'loaderCode',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.loaderCode'),
		columnWidth:.5,
		readOnly : true,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.loaderCodeNotBeNull')
	}, {
		name: 'weight',
		xtype : 'numberfield',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.weight'),
		columnWidth:.5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.weightNotBeNull'),
		validator : function(value) {
			if(value <= 0) {
				//请输入大于0的值
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.valueMustGTZero');
			}
			return true;
	    }
	}, {
		name: 'waybillQty',
		xtype : 'numberfield',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.waybillQty'),
		columnWidth:.5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.waybillQtyNotBeNull'),
		validator : function(value) {
			if(value <= 0) {
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.valueMustGTZeroInteger');
			}
			var reg = /^(-|\+)?\d+$/;
			if(!reg.test(value)) {
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.valueMustGTZeroInteger');
			}
			return true;
	    }
	}, {
		name: 'goodsQty',
		xtype : 'numberfield',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.expresspackageaddnew.waybillGrid.piecees'),
		columnWidth:.5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.goodsQtyNotBeNull'),
		validator : function(value) {
			if(value <= 0) {
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.valueMustGTZeroInteger');
			}
			var reg = /^(-|\+)?\d+$/;
			if(!reg.test(value)) {
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.valueMustGTZeroInteger');
			}
			return true;
	    }
	},
	
	{
		name: 'loaderOrgName',
//		xtype : 'commonsaledepartmentselector',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.loaderOrgName'),
		columnWidth:.5,
		allowBlank: false,
		readOnly : true,
//		listeners: {
//			'select': function(field, records, eOpts) {
//				var form = this.up('window').items.items[0].form;
//				var record = records[0],
//					name = record.get('name');
//				form.findField('loaderOrgName').setValue(name);
//			}
//		}
	}, {
		name: 'loaderOrgCode',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'notes',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.notes'),
		readOnly : true,
		maxLength:200,
		columnWidth:.5
	}, {
		fieldLabel : load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.beginDate'),
		name : 'beginDate',
		xtype:'datetimefield_date97',
    	id: getUUIDModify() + 'Foss_load_loaderworkmodify_joinTime_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		readOnly : true,
		value: getUUIDModify() + Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: 'Foss_load_loaderworkmodify_joinTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .50
	}, {
		fieldLabel : load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.endDate'),
		name : 'endDate',
		xtype:'datetimefield_date97',
    	id: getUUIDModify() + 'Foss_load_loaderworkmodify_leaveTime_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		readOnly : true,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDModify() + 'Foss_load_loaderworkmodify_leaveTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .50
	}, {
		xtype: 'container',
		columnWidth:.75,
		html: '&nbsp;'
	}, {
		text: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.save'),
		hidden: !load.loaderworkloadmodifyExpress.isPermission('load/modifyLoaderWorkButton'),
		xtype:"button",
		columnWidth:.25,
		height:30,
		handler:function(){
			var form = this.up();
			if(!form.form.isValid()) {
				return;
			}
			var loaderWorkRecord = this.up().form.getValues();
			if(loaderWorkRecord.notes.length > 200) {
				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.saveFail'), load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.notesTooLong'), 'error');
				return;
			}
			var beginTime = new Date(loaderWorkRecord.beginDate).getTime();
			var endTime = new Date(loaderWorkRecord.endDate).getTime();
			if(beginTime >= endTime) {
				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.saveFail'), load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.leaveTimeNotEarlierThanJoinTime'), 'error');
				return;
			}
			var loaderWorkloadVo = {
				'loaderWorkloadVo':{
					'loaderWorkloadDto' : loaderWorkRecord	
				}
			};
			Ext.Ajax.request({
				url : load.realPath('modifyLoaderWorkExpress.action'),
				jsonData : loaderWorkloadVo,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.saveSuccess'), load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.modifyLoadworkSuccess'), 'error');
					form.up('window').hide();
					
					//保存成功后刷新页面
					var taskNo = result.loaderWorkloadVo.loaderWorkloadDto.taskNo;
					Ext.Ajax.request({
						url : load.realPath('queryLoaderWorksByTaskNoExpress.action'),
						params : {
							'loaderWorkloadVo.loaderWorkloadDto.taskNo' : taskNo
						},
						success : function(response) {
							var result = Ext.decode(response.responseText),
								loaderWorkloadList = result.loaderWorkloadVo.loaderWorkloadList;
							load.loaderworkloadmodifyExpress.loaderGridPanel.store.loadData(loaderWorkloadList);
						},
					    exception : function(response) {
		    				var json = Ext.decode(response.responseText);
		    				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.saveFail'), json.message, 'error');
		    			}
					});
				},
			    exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkloadmodify.saveFail'), json.message, 'error');
    			}
			});
		}
	}]
});
/****************修改理货员货量信息  end**************/