Ext.define('Foss.load.updateLFDrivingFileModel',{
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
Ext.define('Foss.load.updateLFDrivingFile.type', {
	 extend: 'Ext.data.Store',
     fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	
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
     data:  [
            {'valueName':'空驶年审' , 'valueCode': 'DE_Year_Check'},
            {'valueName':'空驶维修保养' , 'valueCode': 'DE_Maintenance'},
            {'valueName':'空驶提车柜' , 'valueCode': 'DE_Car_Cabinet'},
            {'valueName': '空驶救援', 'valueCode': 'DE_SOS'},
             {'valueName':'空驶补货' , 'valueCode': 'DE_Restock'},
            {'valueName': '混合发车', 'valueCode': 'mix_depart'},
            {'valueName':'分段发车' , 'valueCode': 'subsection_depart'},
            {'valueName':'其他' , 'valueCode': 'other'}]
     
});
//Store
Ext.define('Foss.load.updateLFDrivingFileStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.updateLFDrivingFileModel',
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
Ext.define('Foss.load.updateLFDrivingFileForm',{
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
	 //xtype:'dynamicorgcombselector',
	 //type:'ORG',
	   xtype:'commonvehicledrivingselector',
	 //transDepartment:'Y',
	 name:'orgIdCode',
	 forceSelection: true,
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.orgIdName'),//'所属车队'
	 allowBlank : false
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
     decimalPrecision:2//小数点后允许的最大精度 。
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
     listeners:{
	  'blur':function(cmp,eo,eOpts){
	  if(!Ext.isEmpty(cmp.getValue())){
	  	 var form = load.updateLFDrivingFileForm.getForm();
		 var departDistance= form.findField('departDistance').getValue();
		 if(cmp.getValue()*1<departDistance*1){
		   Ext.MessageBox.alert('提示','到达公里数必须大于出发公里数！');
		   form.findField('departDistance').setValue(null);
		   form.findField('arriveDistance').setValue(null);
		 }
	   }
	  }
     }
	},{
	 xtype :'numberfield',
	 name:'consumeFuelTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelTotal'),//总油升数 ,
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:2999999,
     minValue:0.00,
     decimalPrecision:2//小数点后允许的最大精度 。
	},{
	 xtype :'numberfield',
	 name:'consumeFuelFeeTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.consumeFuelFeeTotal'),//总油费 ,
	 allowBlank : false,
	 step:1,
     allowBlank: false,
     maxValue:999999,
     minValue:0.00,
     decimalPrecision:2//小数点后允许的最大精度 。
	},{
	 xtype :'numberfield',
	 name:'toolFeeTotal',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.toolFeeTotal'),//路桥费'
	 allowBlank : false,
	step:1,
    allowBlank: false,
    maxValue:999999,
    minValue:0.00,
    decimalPrecision:2//小数点后允许的最大精度 。
	},{
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleNo'),//车牌号
	 name:'vehicleNo',
	 xtype : 'commontruckselector',
	 //queryParam : 'truckVo.truck.vehicleNoNoLike',
	 //queryAllFlag : false,
	 allowBlank : false,
	 listeners : {
	   'blur' : function(cmp,eObject,eOpts){
			var form = load.updateLFDrivingFileForm.getForm();
	   	    var vehicleNo=form.findField('vehicleNo');
			if(!Ext.isEmpty(vehicleNo.getValue())){
				var urlss=load.realPath('lfDrivingFileVehicle.action');
				Ext.Ajax.request({
				  url : urlss,
				  params:{'lfDrivingFileVo.vehicleNo': vehicleNo.getValue()},
				  timeout : 300000,
				  success : function(response){
					  var result = Ext.decode(response.responseText);
					  var form = load.updateLFDrivingFileForm.getForm();
					  vehicleAssociationDto=result.lfDrivingFileVo.vehicleAssociationDto
					  form.findField('vehicleTypeName').setValue(vehicleAssociationDto.vehicleLengthName);
					  form.findField('vehicleType').setValue(vehicleAssociationDto.vehicleLengthCode);
					  if(load.lfDrivingFileUpdate.OldvehicleNo!=vehicleNo.getValue){
					  	 load.lfDrivingFileUpdate.OldvehicleNo=vehicleNo.getValue;
					  	 load.updatelfDrivingFileDetails =new Array();
				         load.deletelfDrivingFileDetails  =new Array();
				         /**
						 * 明细修改Map
						 */
						load.updatelfDrFileDetailMap = new Ext.util.HashMap();
						/**
						 * 明细删除Map
						 */
						load.deletelfDrFileDetailMap  = new Ext.util.HashMap();
						 var stores=load.updateLFDrivingFileGrid.getStore();
						 stores.removeAll();
						  //记录车牌和 配载单 修改的时候 是否有改变  默认无
		                load.lfDrivingFileUpdate.isChange='Y';
		                load.lfDrivingFileUpdate.OldvehicleassembleNo=null;
					  }
				  },
				  exception : function(response) {
					  var result = Ext.decode(response.responseText);
					   var form = load.addLFDrivingFileForm.getForm();
					  form.findField('vehicleTypeName').setValue(null);
					  form.findField('vehicleType').setValue(null);
					  top.Ext.MessageBox.alert('提示','查询车型失败：，' + result.message);
				  },
				  failure : function(){
					  console.log('查询车型 时服务端异常！');
				  }
				});
			}
		}
	 }
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
	 forceSelection: true
	},{
	 xtype : 'commondriverselector',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.driverNameTwo'),//司机
	 name:'driverCodeTwo',
	 forceSelection: true,
	 allowBlank : false
	},{
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.vehicleType'),//车型
	 name:'vehicleType',
	 hidden:true
	},{
	 fieldLabel:'异常备注',
	 name:'note',
	 maxLength :200,
	 maxLengthText :'备注信息不能超过200字'
	},{
	 xtype:'dynamicorgmulticombselector',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.lineTransferName'),//线路途径外场 ,
	 name:'lineTransferCode',
	 type:'ORG',
	 //transferCenter:'Y',
	 width:800,
	 allowBlank : false
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
Ext.define('Foss.load.updateLFDrivingFileGrid',{
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
		me.tbar=[{
		    xtype:'button',
		    text:'新增',
		    id : 'Foss_load_updateLFDrivingFileGrid_addButton_ID',
		    handler:function(){
		    	var form =load.updateLFDrivingFileForm.getForm();
		    	if(form.isValid()){
		    	  var lfDrivingFile=load.checkLFDrivingFile(form);
					  if(!lfDrivingFile){
						 return;
					  }
				   var grid = this.up('grid'),
					   store = grid.getStore();
				   if(load.updatelfDrivingFileDetailWindown == null){
					   load.updatelfDrivingFileDetailWindown=Ext.create('Foss.load.updatelfDrivingFileDetailWindown',{
				       isDeivingEmpty:load.lfDrivingFileUpdate.isDeivingEmpty,
				       isModify:'N',title:'长途车辆行驶档案 配载信息 新增 '});//是否空驶,
					}else{
					   load.updatelfDrivingFileDetailWindown.isDeivingEmpty=load.lfDrivingFileUpdate.isDeivingEmpty;
					   load.updatelfDrivingFileDetailWindown.isModify='N';
					   load.updatelfDrivingFileDetailWindown.setTitle('长途车辆行驶档案 配载信息 新增 ');
					}
				       load.updatelfDrivingFileDetailWindown.show();
		    	}
		    }
		},{
		    xtype:'button',
		    text:'修改',
		    id : 'Foss_load_updateLFDrivingFileGrid_updateButton_ID',
		    handler:function(){
		    		 var grid= this.up('grid');
				var selects=grid.getSelectionModel().getSelection();
				if(selects.length>0 ){
					if(selects.length>1 ){
						 Ext.MessageBox.alert('提示','只能操作一行！');
						 return;
					}
					var form =load.updateLFDrivingFileForm.getForm();
		    	if(form.isValid()){
		    	  var lfDrivingFile=load.checkLFDrivingFile(form);
					  if(!lfDrivingFile){
						 return;
					  }
				   if(load.updatelfDrivingFileDetailWindown == null){
					   load.updatelfDrivingFileDetailWindown=Ext.create('Foss.load.updatelfDrivingFileDetailWindown',{
				       isDeivingEmpty:load.lfDrivingFileUpdate.isDeivingEmpty,
				       isModify:'Y',
				       title:'长途车辆行驶档案 配载信息 修改 '}
					   );//是否空驶,
					}else{
					   load.updatelfDrivingFileDetailWindown.isDeivingEmpty=load.lfDrivingFileUpdate.isDeivingEmpty;
					   load.updatelfDrivingFileDetailWindown.isModify='Y';
					   load.updatelfDrivingFileDetailWindown.setTitle('长途车辆行驶档案 配载信息 修改 ');
					}
				       load.updatelfDrivingFileDetailWindown.show();
				       var form=load.updatelfDrivingFileDetailForm.getForm();
				           form.loadRecord(selects[0]);
				           load.lfDrivingFileUpdate.OldvehicleassembleNo=selects[0].data.vehicleassembleNo;
				           form.findField('origOrgCode').setCombValue(
									selects[0].data.origOrgName,
									selects[0].data.origOrgCode);
						   form.findField('destOrgCode').setCombValue(
								    selects[0].data.destOrgName,
								    selects[0].data.destOrgCode);
				       
		    	}
				}else{
					   Ext.MessageBox.alert('提示','请选择要修改的一行！');
				}
		    	
		    }
		},{
		    xtype:'button',
		    id : 'Foss_load_updateLFDrivingFileGrid_deleteButton_ID',
		    text:'删除',
		    handler: function(grid, rowIndex, colIndex) {
		    var gridDetail= this.up('grid');
			var selects=gridDetail.getSelectionModel().getSelection();
			if(selects.length>0){
				Ext.MessageBox.confirm('提示框', '您确定要进行该操作',function (btn) {  
		        if (btn == 'yes') {  
//		        	var record;
		            for (var i = 0; i < selects.length; i++) {  
	            		var rowid =  gridDetail.getStore().indexOf(selects[i]);
						//var cell = sm.getSelectedCell();
			           //var record = store.getAt(cell[0]);
						record = gridDetail.getStore().getAt(rowid);
	                       // var cell = sm.getSelectedCell();
	                        //var record = store.getAt(cell[0]);
						gridDetail.getStore().remove(record);
			             if(!Ext.isEmpty(record.data.id)){
						   load.deletelfDrFileDetailMap.add(record.data.id,record.data.id);
						 }
			            
		            }  

		        }  
		    }); 
			}else{
					Ext.MessageBox.alert('提示','请选择要删除的一行！');
			}
				
         }
     }];
		me.store = Ext.create('Foss.load.updateLFDrivingFileStore');

			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
	});

		
		//me.plugins = [me.getEditor()];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.load.updatelfDrivingFileDetailForm',{
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
	  hidden:true
	},{
	        xtype: 'textfield',
			allowBlank:false,
			fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.vehicleassembleNo'),//配载车次号
			name:'vehicleassembleNo',
			listeners:{
				'blur': function(cmp,eo,eOpts) {
					if(!Ext.isEmpty(cmp.getValue())){
						var form = load.updatelfDrivingFileDetailForm.getForm();
						var updateLFDrivingFileStores=load.updateLFDrivingFileGrid.getStore();
						var selects=load.updateLFDrivingFileGrid.getSelectionModel().getSelection();
						var rowid=null;
						if(selects.length>0){
						  rowid =  updateLFDrivingFileStores.indexOf(selects[0]);
						}
						for(var i in updateLFDrivingFileStores.data.items){
							var recode=updateLFDrivingFileStores.data.items[i];
						   if(i!=rowid||load.updatelfDrivingFileDetailWindown.isModify=='N'){
						     if(cmp.getValue()==recode.get('vehicleassembleNo')){
						       Ext.MessageBox.alert('提示','保存失败，不能添加相同的配载单');
						       form.findField('vehicleassembleNo').setValue(null);
						       return;
						    }
						   }
						}
						load.lfDrivingFileDetails=null;
						Ext.Ajax.request({
							url:load.realPath('qureyVehicleassembleInfo.action'),
							//jsonData : data,
							params:{'lfDrivingFileVo.vehicleassembleNo': cmp.getValue()},
							timeout:300000,
							success:function(response){
								var result = Ext.decode(response.responseText),
								lfDrivingFileDetailEntity=result.lfDrivingFileVo.lfDrivingFileDetailEntity;
								load.lfDrivingFileDetails=result.lfDrivingFileVo.lfDrivingFileDetails;
								var form2=load.updateLFDrivingFileForm.getForm();
								/*if(load.lfDrivingFileAdd.isDeivingEmpty=='N'){
									form.findField('origOrgCode').setReadOnly(true);
								    form.findField('departTime').setReadOnly(true);
								    form.findField('destOrgCode').setReadOnly(true);
								    form.findField('arriveTime').setReadOnly(true);
									form.findField('weightTotal').setReadOnly(true);
									form.findField('volumeTotal').setReadOnly(true);
								}else{
									 form.findField('origOrgCode').setReadOnly(false);
								     form.findField('departTime').setReadOnly(false);
									 form.findField('destOrgCode').setReadOnly(false);
									 form.findField('arriveTime').setReadOnly(false);
								     form.findField('weightTotal').setReadOnly(false);
								     form.findField('volumeTotal').setReadOnly(false);
								}*/
							   if(load.lfDrivingFileUpdate.isDeivingEmpty=='N' &&
				                lfDrivingFileDetailEntity ==null){
				                Ext.MessageBox.alert('提示','保存失败，非空驶明细只能添加已经有的配载车次号，请重新输入配载车次号！');
				                form.findField('vehicleassembleNo').setValue();
						           return;
				               }
								if(lfDrivingFileDetailEntity != null ){
									/**var lineTransferCodes=form2.findField('lineTransferCode').getValue();
							        if(!(lineTransferCodes.indexOf( lfDrivingFileDetailEntity.origOrgCode)>-1)||
					                   !(lineTransferCodes.indexOf( lfDrivingFileDetailEntity.destOrgCode)>-1)){
					                   Ext.MessageBox.alert('提示','保存失败，“配载部门”或“到达部门”不符合“线路途经外场”，请重新输入配载车次号！');
					                   form.findField('vehicleassembleNo').setValue();
							             return;
					                }**/
									form.findField('origOrgCode').setCombValue(
									lfDrivingFileDetailEntity.origOrgName,
									lfDrivingFileDetailEntity.origOrgCode);
									var departTime = new Date(lfDrivingFileDetailEntity.departTime); 						
									var formatStr = 'Y-m-d H:i:s';
								    form.findField('departTime').setValue(Ext.Date.format(departTime, formatStr));
								    form.findField('destOrgCode').setCombValue(
								    lfDrivingFileDetailEntity.destOrgName,
								    lfDrivingFileDetailEntity.destOrgCode);
								    var arriveTime = new Date(lfDrivingFileDetailEntity.arriveTime);
								    form.findField('arriveTime').setValue(Ext.Date.format(arriveTime, formatStr));
									form.findField('weightTotal').setValue(lfDrivingFileDetailEntity.weightTotal);
									form.findField('volumeTotal').setValue(lfDrivingFileDetailEntity.volumeTotal);
									
								}
								
							 if(!Ext.isEmpty(load.lfDrivingFileUpdate.OldvehicleassembleNo)){
							 	if(load.lfDrivingFileUpdate.OldvehicleassembleNo!=cmp.getValue()){
							     load.lfDrivingFileUpdate.OldvehicleassembleNo=null;
								  //记录车牌和 配载单 修改的时候 是否有改变  默认无
		                          load.lfDrivingFileUpdate.isChange='Y';
		                          load.lfDrivingFileUpdate.isChangeVLBNo='Y';
							  }else{
							    load.lfDrivingFileUpdate.isChangeVLBNo='N';
							  }
							 }else{
							  load.lfDrivingFileUpdate.isChangeVLBNo='N';
							 }
							},
							exception:function(response) {
				    			var result = Ext.decode(response.responseText);
				    			top.Ext.MessageBox.alert('提示','加载失败!' + result.message);
				    		},
				    		failure:function(){
				    			console.log('根据配载单号 查询时服务端异常！');
				    		}
						});					
				     }
				}
			}
	   },{ 
	   xtype:'dynamicorgcombselector',
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.origOrgName'),//配载部门
	   name:'origOrgCode',
	   type:'ORG',
	   transferCenter:'Y',
	   displayField : 'name',// 显示名称
	   valueField : 'code',// 值
	   allowBlank:false,
	   listeners:{
		'blur': function(cmp,eo,eOpts) {
			if(!Ext.isEmpty(cmp.getValue())){
				var form= load.updateLFDrivingFileForm.getForm();
				var lineTransferCodes=form.findField('lineTransferCode').getValue();
					if(lineTransferCodes.length>0){
					if(!(lineTransferCodes.indexOf(cmp.getValue())>-1)){
					    Ext.MessageBox.alert('提示','保存失败，“配载部门”不符合“线路途经外场”，请重新输入！');
						return;
				    }
					}
						
			}
		}
	   }
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.departTime'),//配载时间
	   name:'departTime',
	   id : 'updatelfDrivingFileDetailFormdepartTime',
	   //xtype : 'datefield',
	   xtype:'datetimefield_date97',
	   //format:'Y-m-d H:i:s',
	   allowBlank:false,
	   dateConfig: {
		el: 'updatelfDrivingFileDetailFormdepartTime-inputEl'
		//,
		//dateFmt: 'yyyy-MM-dd hh:mi:ss',
		//startDate:'%y-%M-01 00:00:00'
	  }
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.destOrgName'),//到达部门NAME
	   name:'destOrgCode',
	   xtype:'dynamicorgcombselector',
	   type:'ORG',
	   transferCenter : 'Y',
	   displayField : 'name',// 显示名称
	   valueField : 'code',// 值
	   allowBlank:false,
	   listeners:{
		'blur': function(cmp,eo,eOpts) {
			if(!Ext.isEmpty(cmp.getValue())){
				var form= load.updateLFDrivingFileForm.getForm();
				var lineTransferCodes=form.findField('lineTransferCode').getValue();
					if(lineTransferCodes.length>0){
					if(!(lineTransferCodes.indexOf(cmp.getValue())>-1)){
					    Ext.MessageBox.alert('提示','保存失败，“到达部门”不符合“线路途经外场”，请重新输入！');
						return;
				    }
					}
						
			}
		}
	   }
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.arriveTime'),//到达时间
	   name:'arriveTime',
	   id: 'updatelfDrivingFileDetailFormarriveTime',
	  // xtype : 'datefield',
	   xtype:'datetimefield_date97',
	   //format:'Y-m-d H:i:s',
	   allowBlank:false,
	   dateConfig: {
		el: 'updatelfDrivingFileDetailFormarriveTime-inputEl'
	   }
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.weightTotal'),//总重量
	   name:'weightTotal',
	   step:1,
       allowBlank: false,
       maxValue:2999999,
       minValue:0,
       decimalPrecision:2,//小数点后允许的最大精度 。
       xtype : 'numberfield'
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.volumeTotal'),//总体积
	   name:'volumeTotal',
	   step:1,
       allowBlank: false,
       maxValue:2999999,
       minValue:0,
       decimalPrecision:2,//小数点后允许的最大精度 。
       xtype : 'numberfield'	   
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.type'),
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
	   name:'type',
	   xtype: 'combobox',
	    editable : false,
	    displayField: 'valueName',
		valueField:'valueCode', 
		value: '',
		queryMode:'local',
		triggerAction:'all',
	   store:Ext.create('Foss.load.updateLFDrivingFile.type'),
	   allowBlank:false	   
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 *配载信息 新增 window 
 */
Ext.define('Foss.load.updatelfDrivingFileDetailWindown',{
      extend:'Ext.window.Window',
      title:'长途车辆行驶档案 配载信息 新增',
      closeAction:'hide',
	  modal : true,
      height:300,
      width:650,
      isDeivingEmpty:null,//是否空驶
      isModify:null,
      updatelfDrivingFileDetailForm:null,
      //新增
      getUpdatelfDrivingFileDetailForm:function(){
      	if(this.updatelfDrivingFileDetailForm==null){
      	 this.updatelfDrivingFileDetailForm=Ext.create('Foss.load.updatelfDrivingFileDetailForm');
      	 load.updatelfDrivingFileDetailForm=this.updatelfDrivingFileDetailForm;
      	}
      	return this.updatelfDrivingFileDetailForm;
      },
      listeners : {
	    'beforeshow' : function(){
		var window =load.updatelfDrivingFileDetailWindown;
		var form =load.updatelfDrivingFileDetailForm.getForm();
			form.reset();
			load.lfDrivingFileUpdate.OldvehicleassembleNo=null;
			//为非空驶明细的时候 
			if("N"==load.lfDrivingFileUpdate.isDeivingEmpty){
			form.findField('origOrgCode').setReadOnly(true);
			form.findField('departTime').setReadOnly(true);
			form.findField('destOrgCode').setReadOnly(true);
			form.findField('arriveTime').setReadOnly(true);
			form.findField('weightTotal').setReadOnly(true);
			form.findField('volumeTotal').setReadOnly(true);
			
			form.findField('departTime').setVisible(false);
			form.findField('type').setVisible(false);
			form.findField('arriveTime').setVisible(false);
			
			form.findField('departTime').allowBlank=true;
			form.findField('type').allowBlank=true;
			form.findField('arriveTime').allowBlank=true;
			}else{
			form.findField('origOrgCode').setReadOnly(false);
			form.findField('departTime').setReadOnly(false);
			form.findField('destOrgCode').setReadOnly(false);
			form.findField('arriveTime').setReadOnly(false);
			form.findField('weightTotal').setReadOnly(false);
			form.findField('volumeTotal').setReadOnly(false);
			
			form.findField('departTime').setVisible(true);
			form.findField('type').setVisible(true);
			form.findField('arriveTime').setVisible(true);
			
			form.findField('departTime').allowBlank=false;
			form.findField('type').allowBlank=false;
			form.findField('arriveTime').allowBlank=false;
			}
		}
	 },
       constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getUpdatelfDrivingFileDetailForm(),{
			border : false,
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			defaults : {
				margin : '5 0 5 0'
			},
			items : [{
			    columnWidth : .08,
				xtype : 'button',
				name : 'canselButton',
				text : '取消',
				handler : function() {
					var window =this.up('window');
					var form =window.getUpdatelfDrivingFileDetailForm().getForm();
					form.reset();
					form.findField('departTime').setReadOnly(false);
					form.findField('destOrgCode').setReadOnly(false);
					form.findField('arriveTime').setReadOnly(false);
					form.findField('weightTotal').setReadOnly(false);
					form.findField('volumeTotal').setReadOnly(false);
					window.hide();
				}
			}, {
				border : false,
				columnWidth : .84,
				html : '&nbsp;'
		    },{
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				text : '保存',
				handler : function() {
					var form = load.updatelfDrivingFileDetailForm.getForm();
					if(form.isValid()){
						var model = Ext.ModelManager.create(form.getValues(),'Foss.load.updateLFDrivingFileModel');
						model.data.origOrgName=form.findField('origOrgCode').getRawValue();
						model.data.destOrgName=form.findField('destOrgCode').getRawValue();
						//修改了配载单号
						if( load.lfDrivingFileUpdate.isChangeVLBNo=='Y'){
                         load.updatelfDrivingFileDetails =new Array();
						 load.deletelfDrivingFileDetails  =new Array();
						 /**
						  * 明细修改Map
						  */
						load.updatelfDrFileDetailMap = new Ext.util.HashMap();
						/**
						 * 明细删除Map
						 */
						load.deletelfDrFileDetailMap  = new Ext.util.HashMap();
						var stores=load.updateLFDrivingFileGrid.getStore();
						stores.removeAll();
						}else{
						var stores=load.updateLFDrivingFileGrid.getStore();
					    var selects=load.updateLFDrivingFileGrid.getSelectionModel().getSelection();
					     var rowid =null;
					    if(selects.length>0){
					    rowid=stores.indexOf(selects[0]);
					    }
						for(var i in stores.data.items){
							var recode=stores.data.items[i];
						   if(i!=rowid){
						     if(model.data.vehicleassembleNo==recode.get('vehicleassembleNo')){
						       Ext.MessageBox.alert('提示','保存失败，不能添加相同的配载单');
						       return;
						    }
						   }
						}
						}
						var form2=load.updateLFDrivingFileForm.getForm();
						/**var lineTransferCodes=form2.findField('lineTransferCode').getValue();
						if(!(lineTransferCodes.indexOf( model.data.origOrgCode)>-1)||
				             !( lineTransferCodes.indexOf( model.data.destOrgCode)>-1)){
				                Ext.MessageBox.alert('提示','保存失败，“配载部门”或“到达部门”不符合“线路途经外场”，请重新输入配载车次号！');
						           return;
				            }**/
				            
				         //判断线路是否符合包括 根据配载单带出来的线路 （默认符合）
				            var lfarge=true;
				            //默认提示
				            var lfmassege='';
				        /** if(load.lfDrivingFileDetails!=null&&load.lfDrivingFileDetails.length>0&&!Ext.isEmpty(load.lfDrivingFileDetails[0])){
				          Ext.each(load.lfDrivingFileDetails,function(i){
				          	if(!(lineTransferCodes.indexOf(i.origOrgCode)>-1)){
				                lfarge=false;
				                lfmassege=lfmassege+i.origOrgName+",";
				              }
				             if(!(lineTransferCodes.indexOf( i.destOrgCode)>-1)){
				               lfarge=false;
				                lfmassege=lfmassege+i.destOrgName+",";
				             }
				            });			  
				          }**/
						if(lfarge){
							    if('Y'==load.updatelfDrivingFileDetailWindown.isModify){
						           var recode= stores.getAt(rowid);
		                           stores.remove(recode);
							     }
								 stores.insert(0, model); 
								 if('Y'==load.updatelfDrivingFileDetailWindown.isModify
								 &&!Ext.isEmpty(model.data.id)){
								 if(load.updatelfDrFileDetailMap.get(model.data.id)!=null){
								   load.updatelfDrFileDetailMap.removeAtKey(model.data.id);
								 }
								 load.updatelfDrFileDetailMap.add(model.data.id,model.data);
								 } 
							  if(load.lfDrivingFileDetails!=null&&load.lfDrivingFileDetails.length>0&&!Ext.isEmpty(load.lfDrivingFileDetails[0])){
							     Ext.each(load.lfDrivingFileDetails,function(i){
					             var models = Ext.ModelManager.create(i,'Foss.load.updateLFDrivingFileModel');
					             //判断带出的配载单是否能添加
					             var  infalage=true;
						             for(var i in stores.data.items){
										var recode=stores.data.items[i];
									    if(models.data.vehicleassembleNo==recode.get('vehicleassembleNo')){
									       infalage=false;
									    }
								     }		
								     if(infalage){
								      stores.insert(0, models); 
								     }
					             }) 
							  }
						      load.updatelfDrivingFileDetailWindown.hide();
						 }else{
						  Ext.MessageBox.alert('提示','保存失败，'+lfmassege+'不在途径外场之内，请先行添加！');
						  return;
						 }
					}
				}
			}]
		
		}
		];
		me.callParent([cfg]);
	}
});
load.checkLFDrivingFile=function(form){
	var lfDrivingFile =form.getValues();
	    //出发公里
        lfDrivingFile.departDistance=lfDrivingFile.departDistance*1;
        //到达公里
		lfDrivingFile.arriveDistance=lfDrivingFile.arriveDistance*1;
		 //行驶公里
		lfDrivingFile.drivingDistance=
		lfDrivingFile.arriveDistance*1-lfDrivingFile.departDistance*1;
		if(lfDrivingFile.drivingDistance<=0){
		  Ext.MessageBox.alert('提示','到达公里数必须大于出发公里数！')
		  return false;
		}
		//总油升数
		lfDrivingFile.consumeFuelTotal=lfDrivingFile.consumeFuelTotal*1;
		//总油费
		lfDrivingFile.consumeFuelFeeTotal=lfDrivingFile.consumeFuelFeeTotal*1;
		//路桥费
		lfDrivingFile.toolFeeTotal=lfDrivingFile.toolFeeTotal*1;
		//平均油价=总油费/总油升数
		lfDrivingFile.consumeFuelFee=
		(lfDrivingFile.consumeFuelFeeTotal/
		lfDrivingFile.consumeFuelTotal).toFixed(2);
		//百公里油耗=总油升数*100/行驶公里数；
		lfDrivingFile.consumeFuel=
		(lfDrivingFile.consumeFuelTotal*100/
		lfDrivingFile.drivingDistance).toFixed(2);
		 //公里路桥费=路桥费/行驶公里数
		lfDrivingFile.toolFee=
		(lfDrivingFile.toolFeeTotal/
		lfDrivingFile.drivingDistance).toFixed(2);
		var lineTransferCodes=form.findField('lineTransferCode').getValue();
		var lineTransferNames=form.findField('lineTransferCode').getRawValue();
		lfDrivingFile.orgIdName=form.findField('orgIdCode').getRawValue();
		lfDrivingFile.driverNameOne=form.findField('driverCodeOne').getRawValue();
		lfDrivingFile.driverNameTwo=form.findField('driverCodeTwo').getRawValue();
		lfDrivingFile.isDeivingEmpty=load.lfDrivingFileUpdate.isDeivingEmpty;
		  if(lineTransferCodes.length>10){
			 Ext.MessageBox.alert('提示','线路最多只能选择10个！');
			return false;
		 }
		 lfDrivingFile.lineTransferCode=lineTransferCodes[0];
		 if(lineTransferCodes.length>1){
		  for(var i=1;i<lineTransferCodes.length;i++){
		 	lfDrivingFile.lineTransferCode=lfDrivingFile.lineTransferCode+","+lineTransferCodes[i];
		  }
		 }
	    lfDrivingFile.lineTransferName=lineTransferNames;
		if(lfDrivingFile.consumeFuel<15||lfDrivingFile.consumeFuel>60){
		 Ext.MessageBox.alert('提示','“百公里油耗”超出【15,60】正常值范围，请检查“总油升数”、“出发公里数”、“到达公里数”是否填写正确！')
		 return false;
		}
		if(lfDrivingFile.toolFee<1||lfDrivingFile.toolFee>8){
		 Ext.MessageBox.alert('提示','“公里路桥费”超出【1,8】正常值范围，请检查“路桥费”、“出发公里数”、“到达公里数”是否填写正确！')
		 return false;
		}
		//判断线路是否符合包括 根据配载单带出来的线路 （默认符合）
		 var lfarge=true;
	    //默认提示
		 var lfmassege='';
		var stores=load.updateLFDrivingFileGrid.getStore();
		if(stores.getCount()>0){
		for(var i in stores.data.items){
			var record = stores.data.items[i];
			if(!(lineTransferCodes.indexOf(record.data.origOrgCode)>-1)){
				 lfarge=false;
				 lfmassege=lfmassege+record.data.origOrgName+",";
			}
			if(!(lineTransferCodes.indexOf( record.data.destOrgCode)>-1)){
				lfarge=false;
				lfmassege=lfmassege+record.data.destOrgName+",";
			}
			if(Ext.isEmpty(record.data.departTime)||Ext.isEmpty(record.data.arriveTime)){
			 Ext.MessageBox.alert('提示','保存失败，'+record.data.vehicleassembleNo+'的配载时间或者到达时间为空，请修改！');
	         return false;
			}
	    }	
	    if(!lfarge){
	     Ext.MessageBox.alert('提示','保存失败，'+lfmassege+'不在途径外场之内，请先行添加！');
	     return false;
	    }
		}
		return lfDrivingFile;
}
load.updateLFDrivingFileForm=Ext.create('Foss.load.updateLFDrivingFileForm');
load.updateLFDrivingFileGrid=Ext.create('Foss.load.updateLFDrivingFileGrid');
/**
 * 明细修改list
 */
load.updatelfDrivingFileDetails = new Array();
/**
 * 明细删除list
 */
load.deletelfDrivingFileDetails = new Array();
/**
 * 明细修改Map
 */
load.updatelfDrFileDetailMap = new Ext.util.HashMap();
/**
 * 明细删除Map
 */
load.deletelfDrFileDetailMap  = new Ext.util.HashMap();
/**
 * 新增长途车辆行驶档案 window
 */
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.create('Ext.panel.Panel', {
		id : 'T_load-lfDrivingFileUpdateindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ load.updateLFDrivingFileForm, load.updateLFDrivingFileGrid,{// 定义运单列表 (新增界面 行驶档案明细)
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ {
					xtype : 'container',
					columnWidth : .92,
					html : '&nbsp'
			}, {
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				id : 'Foss_load_lfDrivingFileUpdate_mainPage_saveButton_ID',
				text : '保存',
				handler : function() {
					var form= load.updateLFDrivingFileForm.getForm();
					if(form.isValid()){
						 
						 var stores=load.updateLFDrivingFileGrid.getStore();
						 if(stores.getCount()<1){
						   Ext.MessageBox.alert('提示','请添加配载信息！');
						   return ;
						 }
						 var lfDrivingFile=load.checkLFDrivingFile(form);
						 if(!lfDrivingFile){
						 	return;
						 }
						 var lfDrivingFileDetails = new Array();
						 for(var i in stores.data.items){
						    var record = stores.data.items[i];
						    lfDrivingFileDetails.push(record.data);
						 }
						 load.updatelfDrivingFileDetails =new Array();
				         load.deletelfDrivingFileDetails  =new Array();
				         /**
						 * 明细修改Map
						 */
						var updateList=load.updatelfDrFileDetailMap.getValues();
						/**
						 * 明细删除Map
						 */
						var deleteList=load.deletelfDrFileDetailMap.getValues();
						if(updateList.length>0){
						  for(var i=0;i<updateList.length;i++){
							load.updatelfDrivingFileDetails.push(updateList[i]);
							}
						}
						if(deleteList.length>0){
						 for(var j=0;j<deleteList.length;j++){
							load.deletelfDrivingFileDetails.push(deleteList[j]);
						  }
						}
						//构造传到后台的json数据
						var data = {
								'lfDrivingFileVo' : {'lfDrivingFile': lfDrivingFile,
								'lfDrivingFileDetails':lfDrivingFileDetails,
								 //记录车牌和 配载单 修改的时候 是否有改变  默认无
		                         'isChange':load.lfDrivingFileUpdate.isChange,
								'updatelfDrivingFileDetails':load.updatelfDrivingFileDetails,
								'ids':load.deletelfDrivingFileDetails
								}
						};
						
						//mask
						var mainPanel = Ext.getCmp('T_load-lfDrivingFileIndex_content');
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存行驶档案数据
						Ext.Ajax.request({
							url : load.realPath('updateLFDrivingFile.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
							var result = Ext.decode(response.responseText);
			    		    Ext.MessageBox.alert('提示','保存成功，行驶编码为' + result.message);
							myMask.hide();
							var formCmps = form.getFields().getRange(0,form.getFields().getCount());
							for(var i in formCmps){
									formCmps[i].setReadOnly(true);
							}
							    //隐藏“保存”按钮
								Ext.getCmp('Foss_load_lfDrivingFileUpdate_mainPage_saveButton_ID').setVisible(false);
								//禁用快速添加里的输入框、按钮
								Ext.getCmp('Foss_load_updateLFDrivingFileGrid_addButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_updateLFDrivingFileGrid_updateButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_updateLFDrivingFileGrid_deleteButton_ID').setVisible(false);
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert('提示','保存失败，' + result.message);
			    				myMask.hide();
			    			},
			    			failure : function(){
			    				myMask.hide();
			    				console.log('保存长途车辆行驶档案时服务端异常！');
			    			}
						});
					}
				}
			} ]
		} ],
		renderTo : 'T_load-lfDrivingFileUpdateindex-body'
  
		});
		
	load.lfDrivingFileDetails=null;
	/*form.findField('origOrgCode').setReadOnly(true);
	form.findField('origOrgCode').setCombValue(
		lfDrivingFileUpdate.origOrgName,
	    lfDrivingFileUpdate.origOrgCode);
	    form.loadRecord(lfDrivingFileUpdate)*/
	//var model = Ext.ModelManager.create(result.vo.airNotifyCustomersDto,'Foss.airfreight.model.NoticeCustomer');,
	/*form.findField('drivingNo').setValue(lfDrivingFileUpdate.drivingNo);
	form.findField('departDistance').setValue(lfDrivingFileUpdate.departDistance);
	form.findField('arriveDistance').setValue(lfDrivingFileUpdate.arriveDistance);
	form.findField('consumeFuelTotal').setValue(lfDrivingFileUpdate.consumeFuelTotal);
	form.findField('consumeFuelFeeTotal').setValue(lfDrivingFileUpdate.consumeFuelFeeTotal);
	form.findField('toolFeeTotal').setValue(lfDrivingFileUpdate.toolFeeTotal);
	form.findField('vehicleNo').setValue(lfDrivingFileUpdate.vehicleNo);
	form.findField('vehicleTypeName').setValue(lfDrivingFileUpdate.vehicleTypeName);
	form.findField('driverCodeOne').setValue(lfDrivingFileUpdate.driverCodeOne);
	*/
    Ext.Ajax.request({
	  url : load.realPath('queryLfDrivingFile.action'),
	  params:{'lfDrivingFileVo.drivingNo': load.lfDrivingFileUpdate.drivingNo},
	  timeout : 300000,
	  success : function(response){
		var result = Ext.decode(response.responseText);
		var form= load.updateLFDrivingFileForm.getForm();
		var lfDrivingFile=result.lfDrivingFileVo.lfDrivingFile;
		 load.lfDrivingFileUpdate.isDeivingEmpty=lfDrivingFile.isDeivingEmpty;
		 //历史车牌号  记录修改的时候有么有改动车牌号
		 load.lfDrivingFileUpdate.OldvehicleNo=lfDrivingFile.vehicleNo;
		 //记录车牌和 配载单 修改的时候 是否有改变  默认无
		 load.lfDrivingFileUpdate.isChange='N';
		 var model=Ext.ModelManager.create(lfDrivingFile, 'Foss.load.lfDrivingFileModel');
		 model.data.lineTransferCode=null;
		 form.loadRecord(model);
		 var stores=load.updateLFDrivingFileGrid.getStore();
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
         load.updatelfDrivingFileDetails =new Array();
         load.deletelfDrivingFileDetails  =new Array();
         /**
		 * 明细修改Map
		 */
		load.updatelfDrFileDetailMap = new Ext.util.HashMap();
		/**
		 * 明细删除Map
		 */
		load.deletelfDrFileDetailMap  = new Ext.util.HashMap();
	},
	exception : function(response) {
		var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('提示','加载失败，' + result.message);
	},
	failure : function(){
			console.log('加载长途车辆行驶档案时服务端异常！');
	}
  });
});
