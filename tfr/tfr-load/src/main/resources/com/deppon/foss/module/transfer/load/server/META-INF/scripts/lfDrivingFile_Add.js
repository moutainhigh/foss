Ext.define('Foss.load.addLFDrivingFileModel',{
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
		name:'type',type:'String'//
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
	}]
});
Ext.define('Foss.load.addLFDrivingFile.type', {
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
Ext.define('Foss.load.addLFDrivingFileStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.addLFDrivingFileModel',
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
Ext.define('Foss.load.addLFDrivingFileForm',{
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
	 name:'drivingNo',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.drivingNo'),//行车编码 ,
	 allowBlank: false,
	 readOnly:true
	},{
	// xtype:'dynamicorgcombselector',
	 xtype:'commonvehicledrivingselector',
	// type:'ORG',
	 //transDepartment:'Y',
	 name:'orgIdCode',
	 fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.orgIdName'),//'所属车队'
	 allowBlank : false,
	 listeners:{
	  'blur':function(cmp,eo,eOpts){
	     	var form = load.addLFDrivingFileForm.getForm();
	   	    var orgIdCode=form.findField('orgIdCode');
			if(!Ext.isEmpty(orgIdCode.getValue())){
			  	Ext.Ajax.request({
				  url:load.realPath('queryLatestDrivingNo.action'),
				  params:{'lfDrivingFileVo.orgIdCode': orgIdCode.getValue()},
				  timeout:300000,
				  success:function(response){
				         var result = Ext.decode(response.responseText),
							 drivingNo=result.lfDrivingFileVo.drivingNo;
						 var form = load.addLFDrivingFileForm.getForm();
							 form.findField('drivingNo').setValue(drivingNo);
				  },
				  exception:function(response) {
						 var result = Ext.decode(response.responseText);
						     top.Ext.MessageBox.alert('提示','生成行驶编码失败!' + result.message);
			      },
				 failure:function(){
						   console.log('根据车队查询 行驶编码 时服务端异常！');
				 }
			   });	
	      }else{
	      	 var form = load.addLFDrivingFileForm.getForm();
				 form.findField('drivingNo').setValue(null);
	      }
	  }
	 }
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
	  if(!Ext.isEmpty(cmp.getValue() )){
	  	 var form = load.addLFDrivingFileForm.getForm();
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
			var form = load.addLFDrivingFileForm.getForm();
	   	    var vehicleNo=form.findField('vehicleNo');
			if(!Ext.isEmpty(vehicleNo.getValue())){
				var urlss=load.realPath('lfDrivingFileVehicle.action');
				Ext.Ajax.request({
				  url : urlss,
				  params:{'lfDrivingFileVo.vehicleNo': vehicleNo.getValue()},
				  timeout : 300000,
				  success : function(response){
					  var result = Ext.decode(response.responseText);
					  var form = load.addLFDrivingFileForm.getForm();
					  vehicleAssociationDto=result.lfDrivingFileVo.vehicleAssociationDto
					  form.findField('vehicleTypeName').setValue(vehicleAssociationDto.vehicleLengthName);
					  form.findField('vehicleType').setValue(vehicleAssociationDto.vehicleLengthCode);
				  },
				  exception : function(response) {
					  var result = Ext.decode(response.responseText);
					   var form = load.addLFDrivingFileForm.getForm();
					  form.findField('vehicleTypeName').setValue(null);
					  form.findField('vehicleType').setValue(null);
					  top.Ext.MessageBox.alert('提示','查询车型失败：' + result.message);
				  },
				  failure : function(){
					  console.log('查询车型 时服务端异常！');
				  }
				});
			}else{
			  
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
	 width:800,
	 //transferCenter:'Y',
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
Ext.define('Foss.load.addLFDrivingFileGrid',{
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
	}}*/
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
		    id : 'Foss_load_addLFDrivingFileGrid_addButton_ID',
		    handler:function(){
		    	var form =load.addLFDrivingFileForm.getForm();
		    	if(form.isValid()){
		        var lfDrivingFile=load.checkLFDrivingFile(form);
				 if(!lfDrivingFile){
						 	return;
				}
				   var grid = this.up('grid'),
					   store = grid.getStore();
				   if(load.lfDrivingFileDetailWindown == null){
					   load.lfDrivingFileDetailWindown=Ext.create('Foss.load.lfDrivingFileDetailWindown',{
				       isDeivingEmpty:load.lfDrivingFileAdd.isDeivingEmpty});//是否空驶,
					}else{
					   load.lfDrivingFileDetailWindown.isDeivingEmpty=load.lfDrivingFileAdd.isDeivingEmpty;
					}
				       load.lfDrivingFileDetailWindown.show();
		    	}
		    }
		},{
		    xtype:'button',
		    id : 'Foss_load_addLFDrivingFileGrid_deleteButton_ID',
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
				var recode= gridDetail.getStore().getAt(rowid);
                       // var cell = sm.getSelectedCell();
                        //var record = store.getAt(cell[0]);
                   gridDetail.getStore().remove(recode);
                   
		            }  
		        }  
		    });
			}else{
					Ext.MessageBox.alert('提示','请选择要删除的一行！');
			}
				
         }
     }];
		me.store = Ext.create('Foss.load.addLFDrivingFileStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
			
		});
		//me.plugins = [me.getEditor()];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.load.lfDrivingFileDetailForm',{
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
			allowBlank:false,
			fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFileDetial.vehicleassembleNo'),//配载车次号
			name:'vehicleassembleNo',
			listeners:{
				'blur': function(cmp,eo,eOpts) {
					if(!Ext.isEmpty(cmp.getValue())){
						var form = load.lfDrivingFileDetailForm.getForm();
						var addLFDrivingFileStores=load.addLFDrivingFileGrid.getStore();
						for(var i in addLFDrivingFileStores.data.items){
							var recode=addLFDrivingFileStores.data.items[i];
						     if(cmp.getValue()==recode.get('vehicleassembleNo')){
						       Ext.MessageBox.alert('提示','保存失败，不能添加相同的配载单');
						       form.findField('vehicleassembleNo').setValue(null);
						       return;
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
								var form2= load.addLFDrivingFileForm.getForm();
							    load.lfDrivingFileDetails=result.lfDrivingFileVo.lfDrivingFileDetails;
				                if(load.lfDrivingFileAdd.isDeivingEmpty=='N' &&
				                lfDrivingFileDetailEntity ==null){
				                Ext.MessageBox.alert('提示','保存失败，非空驶明细只能添加已经有的配载车次号，请重新输入配载车次号！');
				                form.findField('vehicleassembleNo').setValue();
						           return;
				               }
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
									var departTime =new Date(lfDrivingFileDetailEntity.departTime);						
									var formatStr = 'Y-m-d H:i:s';
								    form.findField('departTime').setValue(Ext.Date.format(departTime, formatStr));
								    form.findField('destOrgCode').setCombValue(
								    lfDrivingFileDetailEntity.destOrgName,
								    lfDrivingFileDetailEntity.destOrgCode);
								    var arriveTime = new Date(lfDrivingFileDetailEntity.arriveTime);
								    //Ext.Date.parse(lfDrivingFileDetailEntity.arriveTime, "Y-m-d H:i:s", true);
								    form.findField('arriveTime').setValue(Ext.Date.format(arriveTime, formatStr));
									form.findField('weightTotal').setValue(lfDrivingFileDetailEntity.weightTotal);
									form.findField('volumeTotal').setValue(lfDrivingFileDetailEntity.volumeTotal);
									
								}else{
								    form.findField('origOrgCode').setCombValue('', '');
								    form.findField('departTime').setValue(null);
								    form.findField('destOrgCode').setCombValue('', '');
								    form.findField('arriveTime').setValue(null);
									form.findField('weightTotal').setValue(null);
									form.findField('volumeTotal').setValue(null);
									
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
				var form= load.addLFDrivingFileForm.getForm();
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
	   id : 'lfDrivingFileDetailFormdepartTime',
	   //xtype : 'datefield',
	   xtype:'datetimefield_date97',
	   //format:'Y-m-d H:i:s',
	   allowBlank:false,
	   dateConfig: {
		el: 'lfDrivingFileDetailFormdepartTime-inputEl',
		dateFmt: 'yyyy-MM-dd hh:mi:ss'
	  }
	},{
	   fieldLabel:load.lfDrivingFile.i18n('Foss.load.lfDrivingFile.destOrgName'),//到达部门NAME
	   name:'destOrgCode',
	   xtype:'dynamicorgcombselector',
	   type:'ORG',
	   transferCenter:'Y',
	   displayField : 'name',// 显示名称
	   valueField : 'code',// 值
	   allowBlank:false,
	   listeners:{
		'blur': function(cmp,eo,eOpts) {
			if(!Ext.isEmpty(cmp.getValue())){
				var form= load.addLFDrivingFileForm.getForm();
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
	   id: 'lfDrivingFileDetailFormarriveTime',
	  // xtype : 'datefield',
	   xtype:'datetimefield_date97',
	   //format:'Y-m-d H:i:s',
	   allowBlank:false,
	   dateConfig: {
		el: 'lfDrivingFileDetailFormarriveTime-inputEl',
		dateFmt: 'yyyy-MM-dd hh:mi:ss'
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
	   store:Ext.create('Foss.load.addLFDrivingFile.type'),
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
Ext.define('Foss.load.lfDrivingFileDetailWindown',{
      extend:'Ext.window.Window',
      title:'长途车辆行驶档案 配载信息 新增',
      closeAction:'hide',
	  modal : true,
      height:300,
      width:650,
      isDeivingEmpty:null,//是否空驶
      lfDrivingFileDetailForm:null,
      //新增
      getlfDrivingFileDetailForm:function(){
      	if(this.lfDrivingFileDetailForm==null){
      	 this.lfDrivingFileDetailForm=Ext.create('Foss.load.lfDrivingFileDetailForm');
      	 load.lfDrivingFileDetailForm=this.lfDrivingFileDetailForm;
      	}
      	return this.lfDrivingFileDetailForm;
      },
      listeners : {
	    'beforeshow' : function(){
		var window =load.lfDrivingFileDetailWindown;
		var form =load.lfDrivingFileDetailForm.getForm();
			form.reset();
			//为非空驶明细的时候 
			if("N"==load.lfDrivingFileAdd.isDeivingEmpty){
			form.findField('origOrgCode').setReadOnly(true);
			form.findField('departTime').setReadOnly(true);
			form.findField('destOrgCode').setReadOnly(true);
			form.findField('arriveTime').setReadOnly(true);
			form.findField('weightTotal').setReadOnly(true);
			form.findField('volumeTotal').setReadOnly(true);
			
			form.findField('departTime').setVisible(false);
			form.findField('type').setVisible(false);
			form.findField('arriveTime').setVisible(false);
			
			//form.findField('departTime').allowBlank=true;
			  form.findField('type').allowBlank=true;
			//form.findField('arriveTime').allowBlank=true;
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
			
			//form.findField('departTime').allowBlank=false;
			form.findField('type').allowBlank=false;
			//form.findField('arriveTime').allowBlank=false;
			}
		}
	 },
       constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getlfDrivingFileDetailForm(),{
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
					var form =window.getlfDrivingFileDetailForm().getForm();
					form.reset();
				    form.findField('origOrgCode').setReadOnly(false);
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
					var form = load.lfDrivingFileDetailForm.getForm();
					if(form.isValid()){
						var model = Ext.ModelManager.create(form.getValues(),'Foss.load.addLFDrivingFileModel');
						model.data.origOrgName=form.findField('origOrgCode').getRawValue();
						model.data.destOrgName=form.findField('destOrgCode').getRawValue();
						var stores=load.addLFDrivingFileGrid.getStore();
						for(var i in stores.data.items){
							var recode=stores.data.items[i];
						    if(model.vehicleassembleNo==recode.get('vehicleassembleNo')){
						       Ext.MessageBox.alert('提示','保存失败，不能添加相同的配载单');
						       return;
						    }
						}
						var form= load.addLFDrivingFileForm.getForm();
						/**var lineTransferCodes=form.findField('lineTransferCode').getValue();
				           if(!(lineTransferCodes.indexOf( model.data.origOrgCode)>-1)||
				              !(lineTransferCodes.indexOf( model.data.destOrgCode)>-1)){
				                Ext.MessageBox.alert('提示','保存失败，“配载部门”或“到达部门”不符合“线路途经外场”，请重新输入配载车次号！');
						           return;
				            }**/
				            //判断线路是否符合包括 根据配载单带出来的线路 （默认符合）
				            var lfarge=true;
				            //默认提示
				            var lfmassege='';
				        /**if(load.lfDrivingFileDetails!=null&&load.lfDrivingFileDetails.length>0&&!Ext.isEmpty(load.lfDrivingFileDetails[0])){
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
						 	 stores.insert(0, model); 
						     if(load.lfDrivingFileDetails!=null&&load.lfDrivingFileDetails.length>0&&!Ext.isEmpty(load.lfDrivingFileDetails[0])){
							     Ext.each(load.lfDrivingFileDetails,function(i){
					             var models = Ext.ModelManager.create(i,'Foss.load.addLFDrivingFileModel');
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
						     load.lfDrivingFileDetailWindown.hide();
						 }else{
						  Ext.MessageBox.alert('提示','保存失败，'+lfmassege+'不在途径外场之内，请先行添加！');
						  return;
						 }
					}
				}
			}]
		}];
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
		lfDrivingFile.isDeivingEmpty=load.lfDrivingFileAdd.isDeivingEmpty;
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
		var stores=load.addLFDrivingFileGrid.getStore();
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
load.addLFDrivingFileForm=Ext.create('Foss.load.addLFDrivingFileForm');
load.addLFDrivingFileGrid=Ext.create('Foss.load.addLFDrivingFileGrid');

/**
 * 新增长途车辆行驶档案 window
 */
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.create('Ext.panel.Panel', {
		id : 'T_load-lfDrivingFileAddindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ load.addLFDrivingFileForm, load.addLFDrivingFileGrid,{// 定义运单列表 (新增界面 行驶档案明细)
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
				id : 'Foss_load_lfDrivingFileAdd_mainPage_saveButton_ID',
				text : '保存',
				handler : function() {
					var form= load.addLFDrivingFileForm.getForm();
					if(form.isValid()){
						 
						 var stores=load.addLFDrivingFileGrid.getStore();
						 if(stores.getCount()<1){
						   Ext.MessageBox.alert('提示','请添加配载信息！');
						   return;
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
						//构造传到后台的json数据
						var data = {
								'lfDrivingFileVo' : {'lfDrivingFile': lfDrivingFile,
								'lfDrivingFileDetails':lfDrivingFileDetails
								}
						};
						
						//mask
						var mainPanel = Ext.getCmp('T_load-lfDrivingFileIndex_content');
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存行驶档案数据
						Ext.Ajax.request({
							url : load.realPath('saveLFDrivingFile.action'),
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
								Ext.getCmp('Foss_load_lfDrivingFileAdd_mainPage_saveButton_ID').setVisible(false);
								//禁用快速添加里的输入框、按钮
								Ext.getCmp('Foss_load_addLFDrivingFileGrid_addButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_addLFDrivingFileGrid_deleteButton_ID').setVisible(false);
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
		renderTo : 'T_load-lfDrivingFileAddindex-body'
  
		});
		load.lfDrivingFileDetails=null;
});
