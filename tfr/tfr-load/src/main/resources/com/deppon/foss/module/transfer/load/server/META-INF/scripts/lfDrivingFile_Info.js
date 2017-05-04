Ext.define('Foss.load.infoLFDrivingFileModel',{
	extend:'Ext.data.Model',
	fields:[
	{
		name:'id',type:'String'//id
	},{
		name:'drivingNo',type:'String'//行车编码
	},{
		name:'vehicleassembleNo',type:'String'//配载车次号
	},{
		name:'origOrgCode',type:'String'//配载部门CODE
	},{
		name:'origOrgName',type:'String'//配载部门NAME
	},{
		name:'departTime',type:'date',//配载时间
		convert : function(value) {
         if (!value) return '';
            var date = new Date(value);
            //解决火狐不兼容
         if (date == 'Invalid Date') {
               return value;
         }
          return Ext.Date.format(date, 'Y-m-d H:i:s');
         }
	},{
		name:'destOrgCode',type:'String'//到达部门CODE
	},{
		name:'destOrgName',type:'String'//到达部门Name
	},{
		name:'arriveTime',type:'date',//到达时间
		convert : function(value) {
         if (!value) return '';
            var date = new Date(value);
         if (date == 'Invalid Date') {
               return value;
         }
          return Ext.Date.format(date, 'Y-m-d H:i:s');
         }
	},{
		name:'weightTotal',type:'String'//总重量';
	},{
		name:'volumeTotal',type:'String'//总体积
	},{
		name:'type',type:'String',//
		/**
    * 类型:空驶年审:DE_Year_Check        
	* 空驶维修保养:DE_Maintenance  
	* 空驶提车柜:DE_Car_Cabinet    
	* 空驶救援:DE_SOS              
	* 空驶补货:DE_Restock          
	* 混合发车:mix_depart          
	* 分段发车:subsection_depart    
	* 其他:other'
	 */
		convert : function(value) {
			if('DE_Year_Check'==value){
			 return "空驶年审";
			}else if('DE_Maintenance'==value){
				 return "空驶维修保养";
			}else if('DE_Car_Cabinet'==value){
				 return "空驶提车柜";
			}else if('DE_SOS'==value){
				 return "空驶救援";
			}else if('DE_Restock'==value){
				 return "空驶补货";
			}else if('mix_depart'==value){
				 return "混合发车";
			}else if('subsection_depart'==value){
				 return "分段发车";
			}else if('other'==value){
				 return "其他";
			}else{
			    return value;
			}
		}
	}]
});

//Store
Ext.define('Foss.load.infoLFDrivingFileStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.infoLFDrivingFileModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
/**
 * 新增车辆行驶档案Form
 */
Ext.define('Foss.load.infoLFDrivingFileForm',{
	extend:'Ext.form.Panel',
	frame : true,
	//height:200,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 80,
		anchor: '99%',
		xtype : 'textfield'
	},
	layout : 'column',
	items:[{
	name:'id',
	hidden:true
	},{
	 name:'drivingNo',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingNo'),//行车编码 ,
	 allowBlank: false,
	 readOnly:true
	},{
	 xtype:'dynamicorgcombselector',
	 type:'ORG',
	 //transDepartment:'Y',
	 name:'orgIdCode',
	 forceSelection: true,
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.orgIdName'),//'所属车队'
	 allowBlank : false,
	 readOnly:true
	},{
	 xtype :'numberfield',
	 name:'departDistance',
	 //出发公里数
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.departDistance'),
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:2999999,
     minValue:0.00,
     decimalPrecision:2,//小数点后允许的最大精度 。
     readOnly:true
	},{
	 xtype :'numberfield',
	 name:'arriveDistance',
	 //到达公里数
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.arriveDistance'),
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:2999999,
     minValue:0.00,
     decimalPrecision:2,//小数点后允许的最大精度 。
     readOnly:true
	},{
	 xtype :'numberfield',
	 name:'consumeFuelTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelTotal'),//总油升数 ,
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:2999999,
     minValue:0.00,
     decimalPrecision:2,//小数点后允许的最大精度 。
     readOnly:true
	},{
	 xtype :'numberfield',
	 name:'consumeFuelFeeTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelFeeTotal'),//总油费 ,
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:999999,
     minValue:0.00,
     decimalPrecision:2,//小数点后允许的最大精度 。
     readOnly:true
	},{
	 xtype :'numberfield',
	 name:'toolFeeTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.toolFeeTotal'),//路桥费'
	 allowBlank : false,
	step:1,
    allowBlank: false,
    maxValue:999999,
    minValue:0.00,
    decimalPrecision:2,//小数点后允许的最大精度 。
    readOnly:true
	},{
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleNo'),//车牌号
	 name:'vehicleNo',
	 xtype : 'commontruckselector',
	 queryParam : 'truckVo.truck.vehicleNoNoLike',
	 //queryAllFlag : false,
	 allowBlank : false,
	 readOnly:true
	 
	},{
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleType'),//车型
	 name:'vehicleTypeName',
	 allowBlank : false,
	 editable : false,
	 readOnly  :true
	},{
	 xtype : 'commondriverselector',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.driverNameOne'),//司机1name 
	 name:'driverCodeOne',
	 allowBlank : false,
	 forceSelection: true,
	 readOnly:true
	},{
	 xtype : 'commondriverselector',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.driverNameTwo'),//司机
	 name:'driverCodeTwo',
	 forceSelection: true,
	 allowBlank : false,
	 readOnly:true
	},{
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleType'),//车型
	 name:'vehicleType',
	 hidden:true
	},{
	 fieldLabel:'异常备注',
	 name:'note',
	 maxLength :200,
	 maxLengthText :'备注信息不能超过200字',
	 readOnly:true
	},{
	 xtype:'dynamicorgmulticombselector',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.lineTransferName'),//线路途径外场 ,
	 name:'lineTransferCode',
	 type:'ORG',
	 //transferCenter:'Y',
	 width:800,
	 allowBlank : false,
	 readOnly:true
	 }],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 * 新增行驶档案Grid
 */
Ext.define('Foss.load.infoLFDrivingFileGrid',{
	extend:'Ext.grid.Panel',
	autoScroll : true,
	columnLines: true,
	collapsible : true,
	animCollapse : true,
	//height:300,
    width:880,
	columns:[{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.vehicleassembleNo'),//配载车次号
	   dataIndex:'vehicleassembleNo',
	   flex : 2
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.origOrgName'),//配载部门
	   dataIndex:'origOrgName',
	   xtype: 'ellipsiscolumn',
	   flex : 2
	},{
	   dataIndex:'origOrgCode',
	   hidden:true
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.destOrgName'),//到达部门NAME
	   dataIndex:'destOrgName',
	   xtype: 'ellipsiscolumn',
	   flex : 1
	},{
	   dataIndex:'destOrgCode',
	   hidden:true
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.weightTotal'),//总重量
	   dataIndex:'weightTotal',
	   flex : 1
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.volumeTotal'),//总体积
	   dataIndex:'volumeTotal',
	   flex : 1
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.arriveTime'),//到达时间
	   dataIndex:'arriveTime',
	  xtype: 'ellipsiscolumn',
	   flex : 2/*,
	   renderer : function(value) {
			if(!Ext.isEmpty(value)){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d H:i:s');									
			}else{
					return null;
	}}*/
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.departTime'),//配载时间
	   dataIndex:'departTime',
	   xtype: 'ellipsiscolumn',
	   flex : 2/*,
	   renderer : function(value) {
		if(!Ext.isEmpty(value)){
			var date = new Date(value);
			return Ext.Date.format(date, 'Y-m-d H:i:s');									
		}else{
		     return null;
	    }
	   }*/
	},{
	   header:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.type'),
    /**
    * 类型:空驶年审:DE_Year_Check        
	* 空驶维修保养:DE_Maintenance  
	* 空驶提车柜:DE_Car_Cabinet    
	* 空驶救援:DE_SOS              
	* 空驶补货:DE_Restock          
	* 混合发车:mix_depart          
	* 分段发车:subsection_depart    
	* 其他:other'
	 */
	   dataIndex:'typeName',
	   renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
			if('DE_Year_Check'==record.data.type){
			 return "空驶年审";
			}else if('DE_Maintenance'==record.data.type){
				 return "空驶维修保养";
			}else if('DE_Car_Cabinet'==record.data.type){
				 return "空驶提车柜";
			}else if('DE_SOS'==record.data.type){
				 return "空驶救援";
			}else if('DE_Restock'==record.data.type){
				 return "空驶补货";
			}else if('mix_depart'==record.data.type){
				 return "混合发车";
			}else if('subsection_depart'==record.data.type){
				 return "分段发车";
			}else if('other'==record.data.type){
				 return "其他";
			}else{
			    return record.data.type;
			}
		},
	   flex : 1
	},{
	   dataIndex:'type',
	   hidden:true   
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.infoLFDrivingFileStore');
		//me.plugins = [me.getEditor()];
		me.callParent([cfg]);
	}
});
load.infoLFDrivingFileForm=Ext.create('Foss.load.infoLFDrivingFileForm');
load.infoLFDrivingFileGrid=Ext.create('Foss.load.infoLFDrivingFileGrid');
/**
 * 新增长途车辆行驶档案 window
 */
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.create('Ext.panel.Panel', {
		id : 'T_load-lfDrivingFileInfoindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ load.infoLFDrivingFileForm, load.infoLFDrivingFileGrid ],
		renderTo : 'T_load-lfDrivingFileInfoindex-body'
  
		});
    Ext.Ajax.request({
	  url : load.realPath('queryLfDrivingFile.action'),
	  params:{'lfDrivingFileVo.drivingNo': load.lfDrivingFileInfo.drivingNo},
	  timeout : 300000,
	  success : function(response){
		var result = Ext.decode(response.responseText);
		var form= load.infoLFDrivingFileForm.getForm();
		var lfDrivingFile=result.lfDrivingFileVo.lfDrivingFile;
		 load.lfDrivingFileInfo.isDeivingEmpty=lfDrivingFile.isDeivingEmpty;
		 var model=Ext.ModelManager.create(lfDrivingFile, 'Foss.load.lfDrivingFileModel');
		 model.data.lineTransferCode=null;
		 form.loadRecord(model);
		 var stores=load.infoLFDrivingFileGrid.getStore();
		 stores.loadData(lfDrivingFile.lfDrivingFileDetailList,null);
		 var driverCodeOne=form.findField('driverCodeOne');
		 driverCodeOne.setCombValue(lfDrivingFile.driverNameOne,lfDrivingFile.driverCodeOne);
		 var driverCodeTwo=form.findField('driverCodeTwo');
		 driverCodeTwo.setCombValue(lfDrivingFile.driverNameTwo,lfDrivingFile.driverCodeTwo);
		 var orgIdCode=form.findField('orgIdCode');
		 orgIdCode.setCombValue(lfDrivingFile.orgIdName,lfDrivingFile.orgIdCode);
		 orgIdCode.setReadOnly(true);
		 if (lfDrivingFile.lineTransferEntitys != null) {
            var mcombo2 = form.findField('lineTransferCode');
               mcombo2.getStore().loadData(lfDrivingFile.lineTransferEntitys);
               mcombo2.setValue(lfDrivingFile.lineTransferCodeList);
         }
	},
	exception : function(response) {
		var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('提示','查询失败，' + result.message);
	},
	failure : function(){
			console.log('查询长途车辆行驶档案时服务端异常！');
	}
  });
});
