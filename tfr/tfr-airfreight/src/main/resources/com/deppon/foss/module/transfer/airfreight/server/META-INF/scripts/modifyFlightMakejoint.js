//查询_修改合大票修改
//查询model
Ext.define('Foss.airfreight.makePickGoodsList.EditPickGoodsQueryModel',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionName',type:'string'},
	      {name:'destOrgName',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'flightDate',type:'date'},
	      {name:'airLineName',type:'string'},
	      {name:'airWaybillNo',type:'string'},
	      {name:'waybillNo',type:'string'}
	]
});

Ext.define('Foss.airfreight.makePickGoodsList.EditPickGoodsForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	defaults: {
		margin:'5 5 5 5'
	},
	items:[
	       //导出合大票清单
		Ext.create('Ext.toolbar.Toolbar', {
				   xtype:'toolbar',
				   dock:'right',
				   layout:'column',
				   defaultType:'button',
				   width:1024,
				   items:[{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.94
				   },{
					   text:'导出',
					   columnWidth:.06,
					   handler: function(){
							Ext.MessageBox.buttonText.yes = "是";  
							Ext.MessageBox.buttonText.no = "否"; 
							Ext.Msg.confirm('提示信息','是否将数据发送到EDI平台？', function(btn,text){
								if(btn == 'yes'){
									//发送则调用EDI系统
									Ext.ux.Toast.msg('提示信息', '导出成功!');
								}else{
									//不发送保存在本地
									Ext.ux.Toast.msg('提示信息', '导出成功!');
								}
							})
					   }
				   }]
			}),
		//查询条件
		Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:590,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
    			xtype:'commonairlinesselector',
    			fieldLabel:'航空公司',
    			displayField : 'code',// 显示名称
    			valueField : 'code',// 值 
    			name: 'airLineTwoletter',
    			queryMode: 'local',
    			displayField: 'code',
    			valueField: 'code',
    			columnWidth:.45
			},{
				fieldLabel:'正单号',
				name:'airWaybillNo',
				columnWidth:.45
			},{
				xtype:'button',
				text:'查询',
				columnWidth:.10,
				handler:function(){
					var v = airfreight.EditPickGoodsForm.getForm().getValues();
					if(Ext.isEmpty(v.airWaybillNo)){
						Ext.Msg.alert('提示信息','请输入正单号!');
						return null;
					}
					var params= {
						'airTransPickupBillVo.airTransPickupBillDto.airLineTwoletter' : v.airLineTwoletter,
						'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo' : v.airWaybillNo
					}
					Ext.Ajax.request({
						url:airfreight.realPath('queryAirPickupbillList.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//将查询结果赋值给records
								var records = result.airTransPickupBillVo;
								//将records绑定到grid列表中
								airfreight.EditPickGoodsResult.store.loadData(records.resultAirWaybillDetailList);
								//获取合大票清单
								var airWaybillEntity = records.airWaybillEntity;
								var flightDate = Ext.Date.format(new Date(airWaybillEntity.flightDate),'Y-m-d');
								airfreight.EditPickGoodsQueryModel.data.destOrgName = airWaybillEntity.arrvRegionName;
								airfreight.EditPickGoodsQueryModel.data.flightNo = airWaybillEntity.flightNo;
								//保留查询条件
								airfreight.EditPickGoodsQueryModel.data.airWaybillNo = v.airWaybillNo;
								airfreight.EditPickGoodsQueryModel.data.arrvRegionName = airWaybillEntity.arrvRegionName;
								airfreight.EditPickGoodsQueryModel.data.flightDate = flightDate;
								airfreight.EditPickGoodsForm.loadRecord(airfreight.EditPickGoodsQueryModel);
								if(records.resultAirWaybillDetailList.length>0){
									var printMap = new Ext.util.HashMap();
									printMap.add('airWayBillNo', v.airWaybillNo);
									airfreight.airWayBillNo = printMap;
								}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					});
				}
			}]
		}),
		//运单号添加
		Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:440,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel:'运单号',
				name:'waybillNo',
				allowBlank: false,
				columnWidth:.65
			},{
				xtype:'button',
				text:'添加',
				columnWidth:.15,
				handler:function(){
					//获取运单号
					var waybillNo = airfreight.pickGoodsForm.getForm().getValues()['waybillNo'];
					if(Ext.isEmpty(waybillNo)){
						Ext.Msg.alert('提示信息','运单号不能为空!');
						return null;
					}
					var pickGoodsstoreLength =  airfreight.pickGoodsResult.store.data.items.length;
					var pickGoodsResult = airfreight.pickGoodsResult.store.data.items;
					for(var i=0;i<pickGoodsstoreLength;i++){
						if(waybillNo==pickGoodsResult[i].data.waybillNo){
							Ext.Msg.alert('提示信息','清单明细列表已存在运单号为['+waybillNo+']的运单不允许重复添加!');
							return null;
						}
					}
					var params= {
	    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': waybillNo
	    			};
					Ext.Ajax.request({
						url:airfreight.realPath('addAirPickupbillDetailInfo.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//获取合票清单明细
							var transfersNumber = result.airTransPickupBillVo.resultAirWaybillDetailInfo;
							if(!Ext.isEmpty(transfersNumber)){
								airfreight.pickGoodsResult.store.add(transfersNumber);
								 Ext.ux.Toast.msg('提示信息', '添加成功!');
								return  null;
							}else{
								Ext.Msg.alert('提示信息','添加失败此运单号不存在!');
								return  null;
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					});
				}
			}]
		}),
		//提货清单信息
		Ext.create('Ext.form.FieldSet',{
		    height:60,
		    width:1040,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel:'到达网点',
				name:'destOrgName',
				allowBlank: false,
				disabled:true,
				columnWidth:.25
			},{
				fieldLabel:'航班号',
				name:'flightNo',
				disabled:true,
				allowBlank: false,
				columnWidth:.25
			},{
				fieldLabel:'目的站',
				name:'arrvRegionName',
				allowBlank: false,
				disabled:true,
				columnWidth:.25
			},{
				xtype: 'datefield',
		        fieldLabel: '航班日期',
		        name: 'flightDate',
		        format: 'Y-m-d',
		        allowBlank: false,
		        columnWidth:.25
			}]
		}),
		//运单号添加、查询条件
		Ext.create('Ext.container.Container',{
			width : 1080,
			layout : 'column',
			items: [{
          		width:100,
          		items:[{
          			xtype:'button',
          			text:'删除',
          			handler:function(){
          				var record = airfreight.pickGoodsResult.getSelectionModel().getSelection();
          				if(record.length!=0){
          					//将选择的record对象从列表中移除
          					airfreight.pickGoodsResult.store.remove(record);
          					Ext.Msg.alert('提示信息', '删除成功!');
          				}else{
          					Ext.Msg.alert('提示信息','请选择需要删除的清单！');
          				}
          			}
          		},{
          			xtype:'button',
          			text:'中转',
          			handler:function(){
          				var record = airfreight.pickGoodsResult.getSelectionModel().getSelection();
          				if(record.length==0){
          					Ext.Msg.alert('提示信息','请选择需要标记的清单!');
          					return null;
          				}
      					//将选择的record对象从列表中移除
      					for(var i=0;i<record.length;i++){
      						if(record[i].data.beTransfer=='1'){
      							record[i].data.beTransfer='0';	
      						}else {
      							record[i].data.beTransfer='1';
      						}
      					}
      					airfreight.pickGoodsResult.store.update(record);
      					Ext.Msg.alert('提示信息', '标记成功!');
          			}
          		}]
			}]
		})
	]
});



//运单明细列表
Ext.define('Foss.airfreight.makePickGoodsList.EditPickGoodsResult',{
	extend:'Ext.grid.Panel',
	title:'清单明细',
	frame:true,
	border:true,
	layout:'column',
	emptyText: "查询结果为空",
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				//获取当前record对象
				var record = grid.getStore().getAt(rowIndex);
				//window显示
				Ext.create('Foss.airfreight.makePickGoodsList.updateWindowPickGoodsList').show();
				//将grid列中的数据绑定到window中的form中
				airfreight.updateWindowPickGoodsList.getForm().loadRecord(record);
			}
		}],
		text:'操作',
	    width: 40,
	    dataIndex: 'id'
	},{
		text: '运单号',
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: '目的站',
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: '品名',
		flex: 0.6,
		dataIndex: 'goodsName'
	},{
		text: '件数',
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		text: '重量<br/>(公斤)',
		flex: 0.6,
		dataIndex: 'grossWeight'
	},{
		text: '计费重量<br/>(公斤)',
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text: '是否中转',
		flex: 0.6,
		dataIndex: 'beTransfer',
		renderer :function(value){
			return value=='1'?'是':'否';
		}
	},{
		text: '提货方式',
		flex: 0.6,
		dataIndex: 'pickupType'
	},{
		text: '送货费',
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: '到付费',
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: '代收款',
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: '备注',
		flex: 0.6,
		dataIndex: 'notes'
	},{
		
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:'票数',
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{
			   fieldLabel:'件数',
			   columnWidth:.10,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   fieldLabel:'毛重',
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel:'公斤送货费',
			   labelWidth:80,
			   columnWidth:.15,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:'到付费',
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'arrivalFeeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.pickGoodsResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.callParent([cfg]);
	}
});

Ext.define('Foss.airfreight.makePickGoodsList.EditToolbarMessage',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 margin:'10 0 0 0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:'',
			   columnWidth:.60
		   },{
			   fieldLabel:'制单人',
			   labelWidth:55,
			   columnWidth:.15,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:'制单时间',
			   labelWidth:75,
			   columnWidth:.20,
			   dataIndex: 'createTime'
		   },{
			   fieldLabel:'',
			   columnWidth:.91
		   },{
			   xtype:'button',
			   text:'保存',
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
				   var record = airfreight.pickGoodsResult.store.data.items;
				   if(record.length==0){
					   Ext.Msg.alert('提示信息','清单明细列表中没有数据!');
					   return null;
				   }
				   var airWayBillNo = airfreight.airWayBillNo.get('airWayBillNo');
				   var boolean = airfreight.validateAirwaybillNoisNotExist(airWayBillNo);
		       	   if(boolean){
		       		   Ext.Msg.alert('提示信息','正单号为['+airWayBillNo+']已存在,不能重复保存!');
		       		   return null;
		       	   }
				   var arrys = {};
				   var fromValues = airfreight.pickGoodsForm.getForm().getValues();
				   //获取中转单号
				   arrys['airTransferPickupbillNo'] = fromValues['airTransferPickupbillNo'];
				   //获取目的站
				   arrys['arrvRegionName'] = fromValues['arrvRegionName'];
				   //获取到达网点
				   arrys['destOrgName'] = fromValues['destOrgName'];
				   //获取中转航班号
				   arrys['transferFlightNo'] = fromValues['flightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['flightDate'];
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.pickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.toolbarMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				   //航空正单组建id
				   arrys['airWaybillId'] = record[0].data.airWaybillId;
				   //获取航空正单明细组建id
				   var ids = "";
				   var jsonArry = [];
	           	   for(var i=0;i<record.length;i++){
	           		 var temp = record[i].data; 
	           		 var obj = record[i].data
	           		     obj.weight = temp.grossWeight;
	           		     if(temp.beTransfer==''){
	           		    	temp.beTransfer='0';
	           		     }
	           		     obj.airWaybillNo = airfreight.airWayBillNo.get('airWayBillNo');
			      	 jsonArry.push(obj);
	           			if(record.length-1==i){
	           				ids = ids + obj.id;
				 			continue;
				 		}
	           			ids = ids + obj.id+',';
	           	    }
	           	   arrys['ids'] = ids;
	           	   var records = Ext.create('Foss.airfreight.makePickGoodsList.AirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {airTransPickupBillVo:{airTranDataCollectionEntity:records.data,airPickupbillDetailList:jsonArry}};
	           	   Ext.Ajax.request({
		           		url:airfreight.realPath('addAirPickBILLAirPickupBill.action'),
		           		jsonData:jsons,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.ux.Toast.msg('提示信息', '保存成功!');		        		
		        		},
		        		exception:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alter('提示信息',result.message);
		        		}
	           	   });
			   }
		   }]
	}]
});

