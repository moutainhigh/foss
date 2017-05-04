
/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.importPayAndRec.billAirImportPayAndRecConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.importPayAndRec.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


//保存界面显示的数据
agency.importPayAndRec.saveAirImportPayAndRec = function(me){
	var selectionPayable = Ext.getCmp('Foss_StlairImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.data;
	
	if(selectionPayable.length==0){
		Ext.Msg.alert(agency.importPayAndRec.i18n('foss.stl.agency.common.alert'),
				agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.notSelectPayAndRec'));
		return false;
	}
	
	var jsonDataPayable = new Array();
	Ext.getCmp('Foss_StlairImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.each(function(i){
		jsonDataPayable.push(i.data);
	});
	var entity = new Object();
	entity.recAndPaylist = jsonDataPayable;
	var yesFn=function(){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
    	Ext.Ajax.request({
    		url:agency.realPath('saveAirImportPayAndRec.action'),
    		jsonData:{'billRecAndPayImportVo':entity},
    		method:'post',
    		success:function(response){
    			var result = Ext.decode(response.responseText);
    			Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAndRecTotalRows_Id').setValue("0");
				Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAmount_Id').setValue("0");
				Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportRecAmount_Id').setValue("0");
    			Ext.getCmp('Foss_StlairImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.removeAll();
    			Ext.Msg.alert(agency.importPayAndRec.i18n('foss.stl.agency.common.alert'),agency.importPayAndRec.i18n('foss.stl.agency.common.success'))
    			me.enable(true);
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.importPayAndRec.i18n('foss.stl.agency.common.alert'),result.message);
    			me.enable(true);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    			me.enable(true);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.importPayAndRec.billAirImportPayAndRecConfirmAlert(
			agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.isSavePayAndRec'),yesFn,noFn);
}

//应付单model
Ext.define('Foss.StlairImportPayAndRec.BillImportPayAndRecEntryModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'payableNo'
	},{
		name:'receivableNo'
	},{
		name:'sourceBillNo'
	},{
		name:'waybillNo'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'origOrgCode'
	},{
		name:'origOrgName'
	},{
		name:'recAmount',
		type:'long'
	},{
		name:'payAmount',
		type:'long'
	},{
		name: 'notes'
	},{
		name: 'productName'
	},{
		name: 'productCode'
	}]
});

//应付单Store
Ext.define('Foss.StlairImportPayAndRec.BillImportPayAndRecEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlairImportPayAndRec.BillImportPayAndRecEntryModel'
});

Ext.define('Foss.StlairImportPayAndRec.StlairImportPayAndRecInfoTab', {
	extend:'Ext.form.Panel',
	frame:true,
	activeTab: 0,
	height : 100,
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'10 5 0 5',
        	labelWidth:85,
        	colspan : 1 
   		 },
    	layout:{
   			type :'table',
   			columns :3
   		},
	    items:[{
	    	xtype: 'textfield',
	    	fieldLabel :agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.text'),
	    	name : 'billRecAndPayImportVo.billRecAndPayImportDto.fileName',
	    	colspan:2,
	    	labelWidth:150,
	    	width:500
	    },{	
	    	xtype:'filefield',
	    	fieldLabel:'',
			name:'billRecAndPayImportVo.billRecAndPayImportDto.file',
			colspan:1,
			allowBlank:true,
		    buttonText: agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.importBtn'),
		    disabled:!agency.importPayAndRec.isPermission('/stl-web/agency/importExcel.action'),
		    hidden:!agency.importPayAndRec.isPermission('/stl-web/agency/importExcel.action'),
		    listeners : {
				'change' : function(fb, v) {
					var pattern = /^.+?\.(xls|xlsx)$/;
					if (pattern.test(v)) {
						if (this.up('form').getForm().isValid()) {
							this.up('form').getForm().findField('billRecAndPayImportVo.billRecAndPayImportDto.fileName').setValue(v);
							var temp = v;
							this.up('form').getForm().submit({
								url : agency.realPath('importExcel.action'),
								success : function(form, action) {
									var result = action.result;
									if(!Ext.isEmpty(result.errorMessage)){
							    		Ext.Msg.alert(agency.importPayAndRec.i18n('foss.stl.agency.common.alert'),result.errorMessage);
							    		return false;
							    	}
									
									var billPayAndRecStore = Ext.getCmp('Foss_StlairImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store;
									var list = action.result.billRecAndPayImportVo.billRecAndPayImportDto.billRecAndPayImportDtoList;
									billPayAndRecStore.loadData(list);
								    Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAndRecTotalRows_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.total);
								    Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAmount_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.totalRecAmount);
								    Ext.getCmp('Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportRecAmount_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.totalPayAmount);
								},
								exception:function(response){
									var result = Ext.decode(response.responseText);	
									Ext.Msg.alert('提示信息',result.message);
								},
								failure : function(form, action) {
									Ext.Msg.alert('Failed', '操作失败');
								}
							});
						}
					} else {
						Ext.Msg.alert('警告', "请导入EXECL文件!");
					}
				}
	    	}
	    },{	
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			colspan:3,
			layout:'column',
			items:[{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.05,
				style : 'color:red',
				fieldLabel:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.downLoad'),
				labelWidth:80
			},{
				xtype: 'container',
				border : false,
				columnWidth:.0001,
				html: '&nbsp;'
			},{
				xtype:'button',
				text:'',
				iconCls:'deppon_icons_downloadOnBtn',
				handler: function(){
    				filePath();
    			}
			}]
	    }]
    }]
});

//编辑器
agency.importPayAndRec.addGridEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
	clicksToEdit : 1,
	isObservable : false
}) ;

//导入信息列表
Ext.define('Foss.StlairImportPayAndRec.StlImportPayAndRecInfoGrid',{
	extend:'Ext.grid.Panel',
	title:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.text'),
	frame:true,
	height:400,
	plugins:agency.importPayAndRec.addGridEditing,
	//selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlairImportPayAndRec.BillImportPayAndRecEntryStore'),
	columns:[{
		header:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.sourceBillNo'),
		width:150,
		dataIndex: 'sourceBillNo'
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.waybillNo'), 
		width:200,
		dataIndex:'waybillNo'
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.customerCode'), 
		width:200,
		hidden:true,
		dataIndex: 'customerCode'
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.customerName'), 
		width:200,
		dataIndex: 'customerName'
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), 
		width:200,
		hidden:true,
		dataIndex: 'origOrgCode'
	},{
		header:agency.importPayAndRec.i18n('foss.stl.agency.airPayAndRec.origOrgName'), 
		width:200,
		dataIndex: 'origOrgName'
	},{
		header:agency.importPayAndRec.i18n('foss.stl.agency.common.transportType'), 
		width:200,
		dataIndex: 'productName'
	},{
		header:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.recAmount'),
		width:150,
		type:'long',
		dataIndex: 'recAmount' 
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.payAmount'), 
		width:150,
		type:'long',
		dataIndex: 'payAmount'				
	},{
		header: agency.importPayAndRec.i18n('foss.stl.agency.airPayAndRec.notes'), 
		width:300,
		dataIndex: 'notes',
		editor:{
    		xtype:'textfield'
    		//,decimalPrecision : 2
    	}
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems=[{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.05,
				fieldLabel:agency.importPayAndRec.i18n('foss.stl.agency.common.sum'),
				labelWidth:50
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAndRecTotalRows_Id',
				columnWidth:.1,
				fieldLabel:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.totalSum'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportPayAmount_Id',
				columnWidth:.13,
				fieldLabel:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.recAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairImportPayAndRec_StlairImportPayAndRecInfoGrid_airImportRecAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.importPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.payAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype:'button',
				text:agency.importPayAndRec.i18n('foss.stl.agency.common.save'),
				disabled:!agency.importPayAndRec.isPermission('/stl-web/agency/saveAirImportPayAndRec.action'),
				hidden:!agency.importPayAndRec.isPermission('/stl-web/agency/saveAirImportPayAndRec.action'),
				columnWidth:.1,
				handler:function(){
					var me = this;
    				agency.importPayAndRec.saveAirImportPayAndRec(me)
    			}
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairImportPayAndRecInfoTab = Ext.create('Foss.StlairImportPayAndRec.StlairImportPayAndRecInfoTab');
	
	var StlImportPayAndRecInfoGrid = Ext.create('Foss.StlairImportPayAndRec.StlImportPayAndRecInfoGrid',{
		id:'Foss_StlairImportPayAndRec_StlImportPayAndRecInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-airImportPayAndRec_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlairImportPayAndRecInfoTab,StlImportPayAndRecInfoGrid],
		renderTo: 'T_agency-airImportPayAndRec-body'
	});
});