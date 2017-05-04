/****************添加理货员货量信息  begin**************/
function getUUIDAddNew() {
	return Ext.Date.format(new Date(), 'Y-m-d H:i:s');
}

//任务类型store
Ext.define('Foss.load.loaderworkloadmodify.dataSource.Store',{
	extend: 'Ext.data.Store',
	fields: [
	         {name: 'code',  type: 'string'},
	         {name: 'name',  type: 'string'}
	         ],
	         proxy: {
	        	 type: 'memory',
	        	 reader: {
	        		 type: 'json',
	        		 root: 'items'
	        	 }
	         },
	         constructor: function(config){
	        	 var me = this,
	        	 cfg = Ext.apply({}, config);
	        	 me.callParent([cfg]);
	         }
});
Ext.define('Foss.load.loaderworkaddnew.loaderWorksAddNewPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	defaultType: 'textfield',
	defaults: {
	    margin: '5 20 5 20'
	},
	items:[{
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
		//理货员
		name: 'loaderName',
		xtype : 'commonemployeeselector',
		parentOrgCode : load.loaderworkloadmodifyExpress.superOrgCode,
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.loaderCode'),
		columnWidth: .5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.wrong.loaderCodeNotBeNull'),
		listeners: {
			'select': function(field, records, eOpts) {
				var form = this.up('window').items.items[0].form;
				var record = records[0],
					empName = record.get('empName'),
					empCode = record.get('empCode'),
					department = record.get('department'),
					deptCode = department['code'],
					deptName = department['name'];
				form.findField('loaderName').setValue(empName);
				form.findField('loaderCode').setValue(empCode);
				form.findField('loaderOrgCode').setValue(deptCode);
				form.findField('loaderOrgName').setValue(deptName);
//				var cmbOrgCode = form.findField('loaderOrgCode');
//				cmbOrgCode.setValue(deptCode);
//				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : deptName}});
			}
		}
	}, {
		name: 'loaderCode',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.empNo'),
		columnWidth:.5,
		readOnly: true,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.wrong.empNoNotBeNull')
	}, {
		name: 'weight',
		xtype : 'numberfield',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.weight'),
		columnWidth:.5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.weightNotBeNull'),
		validator : function(value) {
			if(value <= 0) {
				//请输入大于0的值
				return load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.valueMustGTZero');
			}
			return true;
	    }
	}, {
		name: 'waybillQty',
		xtype : 'numberfield',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.waybillQty'),
		columnWidth:.5,
		allowBlank: false,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.waybillQtyNotBeNull'),
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
	},{
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

			xtype: 'combobox',
			fieldLabel : load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.dataSource'),
			name : 'dataSource',
			columnWidth : .50,
			displayField: 'name',
			valueField:'code', 
			queryMode:'local',
			editable:false,
			store: Ext.create('Foss.load.loaderworkloadmodify.dataSource.Store',{
				data: {
					'items':[
						{'code':'FOSS','name':'FOSS'},
						{'code':'WUKONG','name':'WUKONG'}
						]
				}
			})
		
		}, 
		{

		name: 'loaderOrgCode',
//		xtype : 'commonsaledepartmentselector',
		columnWidth:.0,
		hidden: true
//		listeners: {
//			'select': function(field, records, eOpts) {
//				var form = this.up('window').items.items[0].form;
//				var record = records[0],
//					name = record.get('name');
//				form.findField('loaderOrgName').setValue(name);
//			}
//		}
	}, {
		name: 'loaderOrgName',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.loaderOrgCode'),
		columnWidth:.5,
		allowBlank: false,
		readOnly: true,
		blankText: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.wrong.loaderOrgCodeNotBeNull'),
	}, {
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.joinTime'),
		name : 'beginDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_load_loaderworkaddnew_joinTime_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_load_loaderworkaddnew_joinTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .50
	}, {
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.leaveTime'),
		name : 'endDate',
		xtype:'datetimefield_date97',
    	id: getUUIDAddNew() + 'Foss_load_loaderworkaddnew_leaveTime_ID',
    	allowBlank:false,
		time:true,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: getUUIDAddNew() + 'Foss_load_loaderworkaddnew_leaveTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd hh:mi:ss'
		},
		columnWidth: .50
	},  {
		name: 'notes',
		fieldLabel: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.notes'),
		maxLength:200,
		columnWidth:.5
	},{
		xtype: 'container',
		columnWidth:.75,
		html: '&nbsp;'
	}, {
		text: load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.save'),
		hidden: !load.loaderworkloadmodifyExpress.isPermission('load/modifyLoaderWorkButton'),
		xtype:"button",
		plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			seconds: 5
		}),
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
				//离开任务时间不可以早于起始任务时间
				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.saveFail'), load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addnew.wrong.leaveTimeNotEarlierThanJoinTime'), 'error');
				return;
			}
			var loaderWorkloadVo = {
				'loaderWorkloadVo':{
					'loaderWorkloadDto' : loaderWorkRecord	
				}
			};
			Ext.Ajax.request({
				url : load.realPath('saveLoaderWorkExpress.action'),
				jsonData : loaderWorkloadVo,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.saveSuccess'), load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.addLoadworkSuccess'), 'error');
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
    				Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.saveFail'), json.message, 'error');
    			}
			});
		}
	}]
});
/****************添加理货员货量信息  end**************/