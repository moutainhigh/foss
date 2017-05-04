
/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.landImportPayAndRec.billLandImportPayAndRecConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.landImportPayAndRec.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


//保存界面显示的数据
agency.landImportPayAndRec.saveLandImportPayAndRec = function(){
	var selectionPayable = Ext.getCmp('Foss_StllandImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.data;
	
	if(selectionPayable.length==0){
		Ext.Msg.alert(agency.landImportPayAndRec.i18n('foss.stl.agency.common.alert'),
				agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.notSelectPayAndRec'));
		return false;
	}
	
	var jsonDataPayable = new Array();
	Ext.getCmp('Foss_StllandImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.each(function(i){
		jsonDataPayable.push(i.data);
	});
	var entity = new Object();
	entity.recAndPaylist = jsonDataPayable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('saveLandImportPayAndRec.action'),
    		jsonData:{'billRecAndPayImportVo':entity},
    		method:'post',
    		success:function(response){
    			var result = Ext.decode(response.responseText);
    			Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAndRecTotalRows_Id').setValue("0");
				Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAmount_Id').setValue("0");
				Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportRecAmount_Id').setValue("0");
    			Ext.getCmp('Foss_StllandImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store.removeAll();
    			Ext.Msg.alert(agency.landImportPayAndRec.i18n('foss.stl.agency.common.alert'),agency.landImportPayAndRec.i18n('foss.stl.agency.common.success'))
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landImportPayAndRec.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landImportPayAndRec.billLandImportPayAndRecConfirmAlert(
			agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.isSavePayAndRec'),yesFn,noFn);
}

//应付单model
Ext.define('Foss.StllandImportPayAndRec.BillImportPayAndRecEntryModel',{
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
		name:'otherPayType'	//其他应付类型 @author 218392 zhangyongxue 2015-07-30
	},{
		name:'recAmount',
		type:'long'
	},{
		name:'payAmount',
		type:'long'
	},{
		name: 'notes'//备注
	}]
});

//应付单Store
Ext.define('Foss.StllandImportPayAndRec.BillImportPayAndRecEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StllandImportPayAndRec.BillImportPayAndRecEntryModel'
});

Ext.define('Foss.StllandImportPayAndRec.StllandImportPayAndRecInfoTab', {
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
	    	fieldLabel :agency.landImportPayAndRec.i18n('foss.stl.agency.landImportPayAndRec.text'),//快递代理其它应收、应付信息
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
		    buttonText: agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.importBtn'),//导入
		    disabled:!agency.landImportPayAndRec.isPermission('/stl-web/agency/landImportExcel.action'),
		    hidden:!agency.landImportPayAndRec.isPermission('/stl-web/agency/landImportExcel.action'),
		    listeners : {
				'change' : function(fb, v) {
					var pattern = /^.+?\.(xls|xlsx)$/;
					if (pattern.test(v)) {
						if (this.up('form').getForm().isValid()) {
							this.up('form').getForm().findField('billRecAndPayImportVo.billRecAndPayImportDto.fileName').setValue(v);
							var temp = v;
							this.up('form').getForm().submit({
								url : agency.realPath('landImportExcel.action'),
								success : function(form, action) {
									var result = action.result;
									if(!Ext.isEmpty(result.errorMessage)){
							    		Ext.Msg.alert(agency.landImportPayAndRec.i18n('foss.stl.agency.common.alert'),result.errorMessage);
							    		return false;
							    	}
									
									var billPayAndRecStore = Ext.getCmp('Foss_StllandImportPayAndRec_StlImportPayAndRecInfoGrid_Id').store;
									var list = action.result.billRecAndPayImportVo.billRecAndPayImportDto.billRecAndPayImportDtoList;
									billPayAndRecStore.loadData(list);
								    Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAndRecTotalRows_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.total);
								    Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAmount_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.totalRecAmount);
								    Ext.getCmp('Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportRecAmount_Id').setValue(action.result.billRecAndPayImportVo.billRecAndPayImportDto.totalPayAmount);
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
				fieldLabel:agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.downLoad'),//下载模板
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
agency.landImportPayAndRec.addGridEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
	clicksToEdit : 1,
	isObservable : false
}) ;

//导入信息列表
Ext.define('Foss.StllandImportPayAndRec.StlImportPayAndRecInfoGrid',{
	extend:'Ext.grid.Panel',
	title:agency.landImportPayAndRec.i18n('foss.stl.agency.landImportPayAndRec.text'),//快递代理其它应收、应付信息
	frame:true,
	height:400,
	plugins:agency.landImportPayAndRec.addGridEditing,
	//selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StllandImportPayAndRec.BillImportPayAndRecEntryStore'),
	columns:[{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.waybillNo'), //代单号(运单号)
		width:200,
		dataIndex:'waybillNo'
	},{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.customerCode'), //代理编码
		width:200,
		hidden:true,
		dataIndex: 'customerCode'
	},{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.customerName'), //代理名称
		width:200,
		dataIndex: 'customerName'
	},{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), //收货部门编码
		width:200,
		hidden:true,
		dataIndex: 'origOrgCode'
	},{
		header:agency.landImportPayAndRec.i18n('foss.stl.agency.airPayAndRec.origOrgName'), //收货部门名称
		width:200,
		dataIndex: 'origOrgName'
	},{//@athor 218392 zhangyongxue 2015-07-29 15:05:09
		header:agency.landImportPayAndRec.i18n('foss.stl.agency.airPayAndRec.otherPayType'), //其他应付类型
		width:200,
		dataIndex: 'otherPayType'
	},{
		header:agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.recAmount'),//应收金额
		width:150,
		type:'long',
		dataIndex: 'recAmount' 
	},{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.payAmount'), //应付金额
		width:150,
		type:'long',
		dataIndex: 'payAmount'				
	},{
		header: agency.landImportPayAndRec.i18n('foss.stl.agency.airPayAndRec.notes'), //备注
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
				fieldLabel:agency.landImportPayAndRec.i18n('foss.stl.agency.common.sum'),//合计
				labelWidth:50
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAndRecTotalRows_Id',
				columnWidth:.1,
				fieldLabel:agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.totalSum'),//总条数
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportPayAmount_Id',
				columnWidth:.13,
				fieldLabel:agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.recAmount'),//应收金额
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandImportPayAndRec_StllandImportPayAndRecInfoGrid_landImportRecAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.landImportPayAndRec.i18n('foss.stl.agency.airImportPayAndRec.payAmount'),//应付金额
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype:'button',
				text:agency.landImportPayAndRec.i18n('foss.stl.agency.common.save'),//保存
				disabled:!agency.landImportPayAndRec.isPermission('/stl-web/agency/saveLandImportPayAndRec.action'),
				hidden:!agency.landImportPayAndRec.isPermission('/stl-web/agency/saveLandImportPayAndRec.action'),
				columnWidth:.1,
				handler:agency.landImportPayAndRec.saveLandImportPayAndRec
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StllandImportPayAndRecInfoTab = Ext.create('Foss.StllandImportPayAndRec.StllandImportPayAndRecInfoTab');
	
	var StlImportPayAndRecInfoGrid = Ext.create('Foss.StllandImportPayAndRec.StlImportPayAndRecInfoGrid',{
		id:'Foss_StllandImportPayAndRec_StlImportPayAndRecInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-landImportPayAndRec_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StllandImportPayAndRecInfoTab,StlImportPayAndRecInfoGrid],
		renderTo: 'T_agency-landImportPayAndRec-body'
	});
});