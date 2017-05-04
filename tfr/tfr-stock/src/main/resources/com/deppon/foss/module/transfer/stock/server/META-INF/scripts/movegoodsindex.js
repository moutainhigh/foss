
//审核状态
Ext.define('Foss.stock.movegoods.Store',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
      	{"valueName": "全部", "valueCode": "0"},
	    {"valueName": "未审核", "valueCode": "1"},
	    {"valueName": "已审核", "valueCode": "2"},
	    {"valueName": "已迁移", "valueCode": "3"},
	    {"valueName": "已撤销", "valueCode": "4"},
	    {"valueName": "已作废", "valueCode": "5"},
	    {"valueName": "已退回", "valueCode": "6"},
	    {"valueName": "迁移中", "valueCode": "7"}
		]
});


//库存迁移查询条件表单
Ext.define('Foss.stock.movegoods.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_stock_movegoods_QueryForm_ID',
	layout: 'column',
	frame: true,
	border: false,
	title : '查询条件',
//	title : stock.movegoods.i18n('foss.stock.query.movegoods'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: '部门',
//		fieldLabel: stock.prioritygoods.i18n('foss.stock.org'),
		name: 'org_name',
		hidden: true
	},{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'org_code',
		hidden: true
	},{
		xtype: 'rangeDateField',
		fieldLabel: '申请时间',
//		fieldLabel: stock.movegoods.i18n('foss.stock.applicationtime'),
		fieldId: 'Foss_stock_movegoods_QueryForm_ApplicationTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'createtime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'),		
		toName: 'finishtime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		blankText:'字段不能为空',
		//blankText: stock.movegoods.i18n('foss.stock.notnull'),
		columnWidth: .5
	},{
		xtype: 'combo',
		fieldLabel: '审核状态',
//		fieldLabel: stock.movegoods.i18n('foss.stock.movegoodsstate'),
		name: 'state',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: '0',
		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.movegoods.Store')
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: '重置',
//			text: stock.movegoods.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){
				stock.movegoodsindex.queryform.getForm().reset();
				stock.movegoodsindex.queryform.getForm().findField('createtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'));
				stock.movegoodsindex.queryform.getForm().findField('finishtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
		//		stock.prioritygoods.queryform.getForm().findField('org_code').setValue(stock.prioritygoods.orgCode);
		//		stock.prioritygoods.queryform.getForm().findField('org_name').setValue(stock.prioritygoods.orgName);
				
			}
		},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
		},{
			text: '查询',
//			text: stock.movegoods.i18n('foss.stock.search'),
//			hidden: !stock.prioritygoods.isPermission('stock/queryPriorityGoodsButton'),              
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				var startTime = stock.movegoodsindex.queryform.getValues().createtime;
				var endTime = stock.movegoodsindex.queryform.getValues().finishtime;
				var difTime = 0;
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 31){
				Ext.MessageBox.alert('警告', '查询时间跨度不能超过31天'); //查询时间跨度不能超过31天
				//	Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.span.limit')); //“任务创建时间”跨度不能超过7天
					
					return;
				}
				
				if(stock.movegoodsindex.queryform.getForm().isValid()){
					stock.movegoodsindex.pagingBar.moveFirst();       //pagingBar   分页用的
				}
				
			}
		}]
	}
	],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//运单库存表格
Ext.define('Foss.stock.movegoods.WaybillGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_stock_movegoods_WaybillGrid_ID',
	height: 450,
	autoScroll:true,
	columnLines: true,
    frame: true,
    plugins: [{
        ptype: 'rowexpander',
        pluginId: 'Foss.stock.movegoodsindex.WaybillGrid_Plugin_ID',
		rowsExpander: false,
		rowBodyElement : 'Foss.stock.movegoodsindex.GoodsGrid'
	}],
	columns: [{
			header: 'ID', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'id',
			width : 100,
			align: 'center',
			hidden: true
		},{
			header: '操作', 
//			header: stock.prioritygoods.i18n('foss.stock.operate'), 
			dataIndex: 'operate',
			width : 100,
			align: 'center',
			xtype:'actioncolumn',
			items: [{
  //          tooltip: stockchecking.i18n('foss.stock.modify'),
  			tooltip:'修改',
 // 			hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsModifyButton'),
 				hidden: true,
 				
          iconCls:'deppon_icons_edit',
            handler: function(grid, rowIndex, colIndex) {
            var state = grid.getStore().getAt(rowIndex).data.state;
           	//校验审核状态
						if(state != '未审核' && state != '已退回'){
								Ext.MessageBox.alert('提示','只能对未审核或已退回的数据进行修改！');	
		//				 Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));					
								return;
						}
           	
            	//打开库存迁移窗口
            		Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsModifyWindow().show();
            		//获取id   
                	var stTaskId = grid.getStore().getAt(rowIndex).data.id;							
			//		由stTaskId获取库区信息、清仓人信息、库存快照
					Ext.Ajax.request({
						url: stock.realPath('viewMoveGoodsById.action'),
						params: {
							'stockVO.moveGoodsEntityId':stTaskId
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							//载入库存迁移信息  
							//把id的值给id(隐藏)
							stock.movegoodsindex.moveGoodsModifyForm.getForm().findField('id').setValue(stTaskId);
							stock.movegoodsindex.moveGoodsModifyForm.getForm().findField('goods_type').setValue(result.stockVO.moveGoodsStockQueryDto.goods_type);
							stock.movegoodsindex.moveGoodsModifyForm.getForm().findField('remarks').setValue(result.stockVO.moveGoodsStockQueryDto.remarks);
//									stock.movegoodsindex.moveGoodsModifyForm.getForm().findField('goods_type').setReadOnly(false);
//									stock.movegoodsindex.moveGoodsModifyForm.getForm().findField('remarks').setReadOnly(false);
							//载入库存移出部门信息						
							stock.movegoodsindex.moveoutModifyForm.getForm().findField('moveout_name').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_name);
							stock.movegoodsindex.moveoutModifyForm.getForm().findField('moveout_code').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_code);
							stock.movegoodsindex.moveoutModifyForm.getForm().findField('orgCodeName').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_areaname);
							stock.movegoodsindex.moveoutModifyForm.getForm().findField('outAreaCode').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_areacode);
//								stock.movegoodsindex.moveoutModifyForm.getForm().findField('moveout_name').setReadOnly(false);
							//载入库存移入部门信息
							stock.movegoodsindex.moveinModifyForm.getForm().findField('movein_name').setValue(result.stockVO.moveGoodsStockQueryDto.movein_name);
							stock.movegoodsindex.moveinModifyForm.getForm().findField('movein_code').setValue(result.stockVO.moveGoodsStockQueryDto.movein_code);
							stock.movegoodsindex.moveinModifyForm.getForm().findField('orgCodeName').setValue(result.stockVO.moveGoodsStockQueryDto.movein_areaname);
							stock.movegoodsindex.moveinModifyForm.getForm().findField('inAreaCode').setValue(result.stockVO.moveGoodsStockQueryDto.movein_areacode);
//							stock.movegoodsindex.moveinModifyForm.getForm().findField('movein_name').setReadOnly(false);
						
						
						},
						exception : function(response) {
					    	var result = Ext.decode(response.responseText);
					    		top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
					    	}
						});
            }
        },{
  //          tooltip: stockchecking.i18n('Foss.stockchecking.button.dealGap'),
  			tooltip:'查看',
            iconCls:'deppon_icons_showdetail',
            handler: function(grid, rowIndex, colIndex) {
            	//打开库存迁移窗口
				Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsLookWindow().show();
				//获取id
                var stTaskId = grid.getStore().getAt(rowIndex).data.id;							
                //由stTaskId获取库区信息、清仓人信息、库存快照
				Ext.Ajax.request({
					url: stock.realPath('viewMoveGoodsById.action'),
					params: {
						'stockVO.moveGoodsEntityId':stTaskId
					},
					success: function(response){
						result = Ext.decode(response.responseText);
						//载入库存迁移信息   
						stock.movegoodsindex.moveGoodsLookForm.getForm().findField('goods_type').setValue(result.stockVO.moveGoodsStockQueryDto.goods_type);
						stock.movegoodsindex.moveGoodsLookForm.getForm().findField('remarks').setValue(result.stockVO.moveGoodsStockQueryDto.remarks);
						stock.movegoodsindex.moveGoodsLookForm.getForm().findField('goods_type').setReadOnly(true);
						stock.movegoodsindex.moveGoodsLookForm.getForm().findField('remarks').setReadOnly(true);
						//载入库存移出部门信息
						stock.movegoodsindex.moveoutLookForm.getForm().findField('moveout_name').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_name);
						stock.movegoodsindex.moveoutLookForm.getForm().findField('orgCodeName').setValue(result.stockVO.moveGoodsStockQueryDto.moveout_areaname);
						stock.movegoodsindex.moveoutLookForm.getForm().findField('moveout_name').setReadOnly(true);
						//载入库存移入部门信息
						stock.movegoodsindex.moveinLookForm.getForm().findField('movein_name').setValue(result.stockVO.moveGoodsStockQueryDto.movein_name);
						stock.movegoodsindex.moveinLookForm.getForm().findField('orgCodeName').setValue(result.stockVO.moveGoodsStockQueryDto.movein_areaname);
						stock.movegoodsindex.moveinLookForm.getForm().findField('movein_name').setReadOnly(true);           				
					},
					exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
	    		}
				});
            }
       
            
        }],
        //外场经理角色可以看到修改按钮,其余的人不可以看到
        renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
        	//如果角色不是外场经理，则隐藏修改按钮
        	if(stock.movegoodsindex.isTransferManager == 'Y'){
        			this.items[0].iconCls = 'deppon_icons_edit';
        	}else{
							this.items[0].iconCls = '';
					}
        
        }
		},{ 
			header: '申请时间', 
//			header: stock.prioritygoods.i18n('foss.stock.applicationTime'), 
			dataIndex: 'applicant_time',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			header: '库存移出部门', 
//			header: stock.prioritygoods.i18n('foss.stock.removedDepartment'),
			dataIndex: 'moveout_name',
			width : 100,
			align: 'center'
		},{ 
			header: '库存移入部门', 
//			header: stock.prioritygoods.i18n('foss.stock.toDepartment'),
			dataIndex: 'movein_name',
			width : 100,
			align: 'center'
		},{ 
			header: '迁移货物类型', 
//			header: stock.prioritygoods.i18n('foss.stock.goodsType'),
			dataIndex: 'goods_type',
			width : 100,
			align: 'center'
		},{ 
			header: '审核状态', 
//			header: stock.prioritygoods.i18n('foss.stock.checkState'),
			dataIndex: 'state',
			width : 100,
			align: 'center'
		},{ 
			header: '申请人',
//			header: stock.prioritygoods.i18n('foss.stock.applicant'),
			dataIndex: 'applicant_name',
			width : 100,
			align: 'center'
		},{ 
			header: '申请人工号', 
//			header: stock.prioritygoods.i18n('foss.stock.applicantCode'),
			dataIndex: 'applicant_code',
			width : 100,
			align: 'center'
		},{
			header: '库存移出部门code',
			dataIndex: 'moveout_code',
			width : 100,
			align: 'center',
			hidden:true
		},{
			header: '库存移出部门库区code',	
			dataIndex: 'moveout_areacode',
			width : 100,
			align: 'center',
			hidden:true
		},{
			header: '库存移入部门code',	
			dataIndex: 'movein_code',
			width : 100,
			align: 'center',
			hidden:true	
		},{
			header: '库存移入部门库区code',	
			dataIndex: 'movein_areacode',
			width : 100,
			align: 'center',
			hidden:true		
		},{ 
			header: '备注', 
//			header: stock.prioritygoods.i18n('foss.stock.comment'),
			dataIndex: 'remarks',
			width : 100,
			align: 'center'
		}],

		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.movegoods.WaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.tbar = [{
				xtype: 'container',
				columnWidth:.50,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '申请',
//				text: stock.stockmanage.i18n('foss.stock.apply'),
				disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsApplyButton'),
				hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsApplyButton'),
				gridContainer: this,
				handler: function() {
					stock.movegoodsindex.moveGoodsWindow = Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsWindow();
						stock.movegoodsindex.moveGoodsWindow.show();									
					}
				},{
					xtype: 'button',
					text: '撤销',
					//只能外场经理有权限
//					text: stock.stockmanage.i18n('foss.stock.cancel'),
					disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsRevocationButton'),		
					hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsRevocationButton'),		
					gridContainer: this,
					handler: function() {
						var goodsMapList = stock.movegoodsindex.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							Ext.ux.Toast.msg('提示', '请选择需要撤销的申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
						}else if(goodsMapList.length>=2){
							Ext.ux.Toast.msg('提示', '一次只能撤销一条申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
							
						}
						
						var moveGoodsEntity = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验审核状态
								if(!(RecordMapList[j].data.state == "未审核" || RecordMapList[j].data.state == "已退回")){
										Ext.MessageBox.alert('提示','只能撤销未审核和已退回的数据！');	
//								 Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));		
									return;
								}else{
									moveGoodsEntity.push(RecordMapList[j].data);
								}
							}
						}
						Ext.MessageBox.confirm('提示', '确认将选中的数据撤销吗？',function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){					
							var jsonParam = {stockVO: {moveGoodsEntityId:moveGoodsEntity[0].id}};
	//						Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								//撤销action   revocation
			    			url: stock.realPath('revocationStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				stock.movegoodsindex.waybillGoodsMap.clear();
			    				stock.movegoodsindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '撤销成功！', 'ok', 3000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('撤销失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});							
					}
				}, {
					xtype: 'button',
					text: '审核受理',
					//搬迁员 有此权限
//					text: stock.stockmanage.i18n('foss.stock.check'),
					disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsAuditorButton'),	
					hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsAuditorButton'),	
					gridContainer: this,
					handler: function() {
						var goodsMapList = stock.movegoodsindex.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							Ext.ux.Toast.msg('提示', '请选择需要审核的申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
						}else if(goodsMapList.length>=2){
							Ext.ux.Toast.msg('提示', '一次只能审核一条申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
							
						}
							
						var moveGoodsEntity = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验审核状态
								if(RecordMapList[j].data.state != "未审核"){
									Ext.MessageBox.alert('提示','只能审核未审核的数据！');	
//								 	Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));		
									return;
								}else{
									moveGoodsEntity.push(RecordMapList[j].data);
								}
							}
						}
						Ext.MessageBox.confirm('提示', '确认审核选中的数据吗？',function(btn){
	//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
							if(btn == 'yes'){
								var jsonParam = {stockVO: {moveGoodsEntityId:moveGoodsEntity[0].id}};
	//							Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
								Ext.Ajax.request({						
									//审核action   auditor
					    			url: stock.realPath('auditorStock.action'),
					    			jsonData:jsonParam,
					    			success:function(response){
					    				Ext.Msg.hide();
					    				var result = Ext.decode(response.responseText);
					    				stock.movegoodsindex.waybillGoodsMap.clear();
					    				stock.movegoodsindex.waybillGrid.store.load();
					    				Ext.ux.Toast.msg('提示', '审核受理成功！', 'ok', 3000);
					    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
					    			},
					    			exception : function(response) {
					    				Ext.Msg.hide();
					    				var result = Ext.decode(response.responseText);
					    				Ext.ux.Toast.msg('审核受理失败', result.message, 'error', 3000);
					    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
					    			}
					    		});	
							}
						});
					}
				}, {
					xtype: 'button',
					text: '作废',
					//搬迁员  有此权限
//					text: stock.stockmanage.i18n('foss.stock.invalid'),
					disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsInvalidateButton'),
					hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsInvalidateButton'),
					gridContainer: this,
					handler: function() {
						var goodsMapList = stock.movegoodsindex.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							Ext.ux.Toast.msg('提示', '请选择需要作废的申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
						}else if(goodsMapList.length>=2){
							Ext.ux.Toast.msg('提示', '一次只能作废一条申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
							
						}
						var moveGoodsEntity = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验审核状态
								if(RecordMapList[j].data.state != "已审核"){
										Ext.MessageBox.alert('提示','只能作废已审核的数据！');	
//								 Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));		
									return;
								}else{
								moveGoodsEntity.push(RecordMapList[j].data);
								}
							}
						}
						Ext.MessageBox.confirm('提示', '确认作废选中的数据吗？',function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){
							
							var jsonParam = {stockVO: {moveGoodsEntityId:moveGoodsEntity[0].id}};
//							Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								//作废action   invalidate
			    			url: stock.realPath('invalidateStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				stock.movegoodsindex.waybillGoodsMap.clear();
			    				stock.movegoodsindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '作废成功！', 'ok', 3000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('作废失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});
						
					}
			},{
					xtype: 'button',
					text: '退回',
					//搬迁员  有此权限
//					text: stock.stockmanage.i18n('foss.stock.sendback'),
					disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsReturnButton'),
					hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsReturnButton'),
					gridContainer: this,
					handler: function(item) {
						var goodsMapList = stock.movegoodsindex.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							Ext.ux.Toast.msg('提示', '请选择需要退回的申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
						}else if(goodsMapList.length>=2){
							Ext.ux.Toast.msg('提示', '一次只能退回一条申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
							
						}
							
						var moveGoodsEntity = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验审核状态
								if(RecordMapList[j].data.state != "未审核"){
										Ext.MessageBox.alert('提示','只能退回未审核的数据！');	
//								 Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));		
									return;
								}else{
								moveGoodsEntity.push(RecordMapList[j].data);
								}
							}
						}
						Ext.MessageBox.confirm('提示', '确认退回选中的数据吗？',function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){
							
							var jsonParam = {stockVO: {moveGoodsEntityId:moveGoodsEntity[0].id}};
							Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								//作废action   return
			    			url: stock.realPath('returnStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				stock.movegoodsindex.waybillGoodsMap.clear();
			    				stock.movegoodsindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '退回成功！', 'ok', 3000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('退回失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});
						
				}
			},{
				xtype: 'container',
				columnWidth:.10,
				html: '&nbsp;'
			},{
				xtype: 'button',
				text: '确认迁移',
				//搬迁员 有此权限
//				text: stock.stockmanage.i18n('foss.stock.move'),
				disabled: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsConfirmButton'),
				hidden: !stock.movegoodsindex.isPermission('stock/queryMoveGoodsConfirmButton'),
				gridContainer: this,
				handler: function(item) {
					var goodsMapList = stock.movegoodsindex.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要确认迁移的申请！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
					}else if(goodsMapList.length>=2){
						Ext.ux.Toast.msg('提示', '一次只能对一条申请进行确认迁移动作！', 'error', 2000);
//							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
						return;
						
					}
					//清空选中的数据
//					stock.movegoodsindex.waybillGoodsMap.clear();
//			    stock.movegoodsindex.waybillGrid.store.load();
			    				
					var moveGoodsEntity = new Array();
					var RecordMapList;
					for(var i=0;i<goodsMapList.length;i++){
						RecordMapList = goodsMapList[i].getValues();
						for(var j=0;j<RecordMapList.length;j++){
							//校验审核状态
							if(RecordMapList[j].data.state != "已审核"){
									Ext.MessageBox.alert('提示','只能已审核的数据进行确认迁移动作！');	
//								 Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));		
								return;
							}else{
								moveGoodsEntity.push(RecordMapList[j].data);
							}
						}
					}
					Ext.MessageBox.confirm('提示', '是否对此条数据进行确认迁移动作？',function(btn){
	//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){
							if(moveGoodsEntity[0].goods_type =="零担货"){
									moveGoodsEntity[0].goods_type = 1 ;
								}
							var jsonParam = {stockVO: {moveGoodsStockQueryDto:moveGoodsEntity[0]}};
//							Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({						
								//作废action   confirm
			    			url: stock.realPath('confirmStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				//清空选中的数据
			    				stock.movegoodsindex.waybillGoodsMap.clear();
			    				stock.movegoodsindex.waybillGrid.store.load();
			    				Ext.ux.Toast.msg('提示', '确认迁移成功！', 'ok', 3000);
			    				//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('确认迁移失败', result.message, 'error', 3000);
			    			//	Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});
				}
			}];
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			stock.movegoodsindex.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		//select监听
		listeners: {
				select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;

				var id = record.get('id');
				var goodsMap = stock.movegoodsindex.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				//这一行是添加数据的吧?
				stock.movegoodsindex.waybillGoodsMap.add(mapKey,goodsMap);	
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var applicant_code = record.get('applicant_code');
				var state = record.get('id');
				var mapKey = applicant_code + state;
				var selectedList = grid.getSelectionModel().selected;
				stock.movegoodsindex.waybillGoodsMap.removeAtKey(mapKey);
				
			}
				
			
		}
});


//运单库存 model
Ext.define('Foss.stock.movegoods.WaybillModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',type:'string'},
		{name: 'applicant_time' , type: 'date',convert: dateConvert},
		{name: 'moveout_name' , type: 'string'},
		{name: 'movein_name' , type: 'string'},
		{name: 'goods_type', type: 'int',
			convert: function(value) {
				if (value == 1) {					
					return '零担货';
				}
			}
		},
		{name: 'state', type: 'string',
			convert: function(value) {
				if (value == '1') {					
					return '未审核';
				}else if(value == '2'){
					return '已审核';
				}else if(value == '3'){
					return '已迁移';
				}else if(value == '4'){
					return '已撤销';
				}else if(value == '5'){
					return '已作废';
				}else if(value == '6'){
					return '已退回';
				}else if(value == '7'){
					return '迁移中';
				}
			}
		},
		{name: 'applicant_name' , type: 'string'},
		{name: 'applicant_code' , type: 'string'},
		{name: 'moveout_code' , type: 'string'},
		{name: 'movein_code' , type: 'string'},
		{name: 'moveout_areacode' , type: 'string'},
		{name: 'movein_areacode' , type: 'string'},
		{name: 'remarks', type: 'string'}

	]
});


//运单库存 Store
Ext.define('Foss.stock.movegoods.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.movegoods.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: stock.realPath('queryMoveGoodsStock.action'),
		reader : {
			type : 'json',
			root : 'stockVO.moveGoodsStockQueryDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = stock.movegoodsindex.queryform.getValues();
				
				if(queryParams.state == '0'){
					queryParams.state = '';
				}
				
				Ext.apply(operation, {
					params : {
//						'stockVO.moveGoodsStockDto.org_name' : '0000',//queryParams.org_name,
						'stockVO.moveGoodsStockDto.state' : queryParams.state,
						'stockVO.moveGoodsStockDto.beginInStockTime' : queryParams.createtime,
						'stockVO.moveGoodsStockDto.endInStockTime' : queryParams.finishtime

					}
				});	
		},
		select : function(rowModel, record, index, eOpts) {
				var grid = this;
		
				
			}
	
	}
	
});





/************************************************************库存迁移申请界面****************************************/

//迁移货物类型
Ext.define('Foss.stock.movegoodsType.Store',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'int'}
	],
	data:[
		    {"valueName": "零担货", "valueCode": 1 }
		]
});

//迁移货物类型表单
Ext.define('Foss.stock.movegoods.MoveStockForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'迁移货物类型',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'combo',
		readOnly : false,
		fieldLabel: '迁移货物类型',
//		fieldLabel: stock.movegoods.i18n('foss.stock.movegoodsstate'),
		name: 'goods_type',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: '',
		columnWidth:.4,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		allowBlank: false,
		blankText:'字段不能为空',
//	blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		store:Ext.create('Foss.stock.movegoodsType.Store')
	},{
			xtype: 'container',
			columnWidth:.2,
			html: '&nbsp;'
	},{
		xtype: 'textarea',  //textfield
		maxLength : 100,
		readOnly : false,
		fieldLabel: '备注',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'remarks',
		columnWidth:.4
	},{
		xtype: 'textfield',  
		maxLength : 100,
		fieldLabel: 'ID',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'id',
		hidden:true,
		columnWidth:.4	
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//库存货物移出组织(申请用的)
Ext.define('Foss.stock.movegoods.MoveOutStockForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物移出组织',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'移出部门名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'moveout_name',
		readOnly : false,
		allowBlank: false,
		blankText:'字段不能为空',
//					blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
			listeners : {
		     select : function() {
		     	 var orgCode = stock.movegoodsindex.moveoutForm.form.findField('moveout_name').getValue();
		     	 var orgName = stock.movegoodsindex.moveoutForm.form.findField('moveout_name').getRawValue();
		     	 var jsonParam = {stockVO:{stockOrgCode:orgCode,stockOrgName:orgName}};
		     	 Ext.Ajax.request({						
				    			url: stock.realPath('areaByOrgcode.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				if(result.stockVO.areaByOrgcodeList!=null&&result.stockVO.areaByOrgcodeList!=''){
				    					  //moveout_code隐藏的值,方便修改时使用
				    						stock.movegoodsindex.moveoutForm.form.findField('moveout_code').setValue(orgCode);
				    						stock.movegoodsindex.moveoutForm.form.findField('orgCodeName').setValue(result.stockVO.areaByOrgcodeList[0].valueName);
				    						stock.movegoodsindex.moveoutForm.form.findField('outAreaCode').setValue(result.stockVO.areaByOrgcodeList[0].valueCode);
				    				}else{
				    						stock.movegoodsindex.moveoutForm.form.findField('moveout_code').setValue(orgCode);
				    						stock.movegoodsindex.moveoutForm.form.findField('orgCodeName').setValue('');
				    						stock.movegoodsindex.moveoutForm.form.findField('outAreaCode').setValue('');
				    				}
				    			}			    		
				   });	
		     }
		}      
		
	},{
			xtype: 'container',                                                               
			columnWidth:.2,
			html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '移出部门code',
		name: 'moveout_code',
		hidden: true, 
		columnWidth:.4
			
	},{		
		xtype: 'textfield',
		fieldLabel: '移出库区名称',  
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.4
	},{		
		xtype: 'textfield',
		fieldLabel: '移出库区Code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'outAreaCode',
		hidden: true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



//库存货物移出组织-->修改时用的
Ext.define('Foss.stock.movegoods.MoveOutStockModifyForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物移出组织',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'移出部门名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'moveout_name',
		readOnly : false,
		allowBlank: false,
		blankText:'字段不能为空',
//					blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
			listeners : {
		     select : function() {
		     	//这个得到的还是name啊,code没有加载进来哈
		     	 var orgCode = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_name').getValue();
		     	 var orgName = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_name').getRawValue();
		     	 var jsonParam = {stockVO:{stockOrgCode:orgCode,stockOrgName:orgName}};
		     	 Ext.Ajax.request({						
				    			url: stock.realPath('areaByOrgcode.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				if(result.stockVO.areaByOrgcodeList!=null&&result.stockVO.areaByOrgcodeList!=''){
				    					  //moveout_code隐藏的值,方便修改时使用
				    						stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_code').setValue(orgCode);
				    						stock.movegoodsindex.moveoutModifyForm.form.findField('orgCodeName').setValue(result.stockVO.areaByOrgcodeList[0].valueName);
				    						stock.movegoodsindex.moveoutModifyForm.form.findField('outAreaCode').setValue(result.stockVO.areaByOrgcodeList[0].valueCode);
				    				}else{
				    						stock.movegoodsindex.moveoutModifyForm.form.findField('orgCodeName').setValue('');
				    						stock.movegoodsindex.moveoutModifyForm.form.findField('outAreaCode').setValue('');
				    				}
				    			}			    		
				   });	
		     }
		}      
		
	},{
			xtype: 'container',                                                               
			columnWidth:.2,
			html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '移出部门code',
		name: 'moveout_code',
		hidden: true, 
		columnWidth:.4
			
	},{		
		xtype: 'textfield',
		fieldLabel: '移出库区名称',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.4
	},{		
		xtype: 'textfield',
		fieldLabel: '移出库区Code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'outAreaCode',
		hidden: true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//库存货物移入组织
Ext.define('Foss.stock.movegoods.MoveInStockForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物移入组织',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'移入部门名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'movein_name',
		readOnly : false,
		allowBlank: false,
		blankText:'字段不能为空',
//					blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
		listeners : {
		     select : function() {
		     	var orgCode = stock.movegoodsindex.moveinForm.form.findField('movein_name').getValue();
		     	var orgName = stock.movegoodsindex.moveinForm.form.findField('movein_name').getRawValue();
		     	var jsonParam = {stockVO:{stockOrgCode:orgCode,stockOrgName:orgName}};
//		     	 var jsonParam = {stockVO:{stockOrgCode:orgName}};
		     	 Ext.Ajax.request({						
				    			url: stock.realPath('areaByOrgcode.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				if(result.stockVO.areaByOrgcodeList!=null&&result.stockVO.areaByOrgcodeList!=''){
				    						//movein_code隐藏的值,方便修改时使用
				    						stock.movegoodsindex.moveinForm.form.findField('movein_code').setValue(orgCode);
				    						stock.movegoodsindex.moveinForm.form.findField('orgCodeName').setValue(result.stockVO.areaByOrgcodeList[0].valueName);
				    						stock.movegoodsindex.moveinForm.form.findField('inAreaCode').setValue(result.stockVO.areaByOrgcodeList[0].valueCode);
				    				}else{
				    						stock.movegoodsindex.moveinForm.form.findField('movein_code').setValue(orgCode);
				    						stock.movegoodsindex.moveinForm.form.findField('orgCodeName').setValue('');
				    	   				stock.movegoodsindex.moveinForm.form.findField('inAreaCode').setValue('');
				    				}
				    			}			    		
				   });	
		     }
		} 
	
		
		
	},{
			xtype: 'container',
			columnWidth:.2,
			html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '移入部门code',
		name: 'movein_code',
		hidden: true, 
		columnWidth:.4
			
	},{		
		xtype: 'textfield',
		fieldLabel: '移入库区名称',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.4
	},{		
		xtype: 'textfield',
		fieldLabel: '移入库区Code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'inAreaCode',
		hidden: true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//库存货物移入组织(修改时用的)
Ext.define('Foss.stock.movegoods.MoveInStockModifyForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:'库存货物移入组织',
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:.4
	},
	items: [{
		xtype: 'dynamicorgcombselector',
		fieldLabel:'移入部门名称',
//		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.deptcode'),
		name: 'movein_name',
		readOnly : false,
		allowBlank: false,
		blankText:'字段不能为空',
//					blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth:.4,
		listeners : {
		     select : function() {
		     	var orgCode = stock.movegoodsindex.moveinModifyForm.form.findField('movein_name').getValue();
		     	var orgName = stock.movegoodsindex.moveinModifyForm.form.findField('movein_name').getRawValue();
		     	var jsonParam = {stockVO:{stockOrgCode:orgCode,stockOrgName:orgName}};
//		     	 var jsonParam = {stockVO:{stockOrgCode:orgName}};
		     	 Ext.Ajax.request({						
				    			url: stock.realPath('areaByOrgcode.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				if(result.stockVO.areaByOrgcodeList!=null&&result.stockVO.areaByOrgcodeList!=''){
				    						//movein_code隐藏的值,方便修改时使用
				    						stock.movegoodsindex.moveinModifyForm.form.findField('movein_code').setValue(orgCode);
				    						stock.movegoodsindex.moveinModifyForm.form.findField('orgCodeName').setValue(result.stockVO.areaByOrgcodeList[0].valueName);
				    						stock.movegoodsindex.moveinModifyForm.form.findField('inAreaCode').setValue(result.stockVO.areaByOrgcodeList[0].valueCode);
				    				}else{
				    						stock.movegoodsindex.moveinModifyForm.form.findField('orgCodeName').setValue('');
				    	   				stock.movegoodsindex.moveinModifyForm.form.findField('inAreaCode').setValue('');
				    				}
				    			}			    		
				   });	
		     }
		} 
	
		
		
	},{
			xtype: 'container',
			columnWidth:.2,
			html: '&nbsp;'
	},{
		xtype: 'textfield',
		fieldLabel: '移入部门code',
		name: 'movein_code',
		hidden: true, 
		columnWidth:.4
			
	},{		
		xtype: 'textfield',
		fieldLabel: '移入库区名称',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.4
	},{		
		xtype: 'textfield',
		fieldLabel: '移入库区Code',
//		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'inAreaCode',
		hidden: true
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



//申请库存迁移窗口
Ext.define('Foss.stock.movegoods.moveGoodsWindow', {
	extend: 'Ext.window.Window',
	title: '迁移货物库存',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var moveGoodsPanel = Ext.create('Foss.stock.movegoods.MoveGoodsPanel'); 	
		me.items = [
		    moveGoodsPanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.movegoodsindex.moveGoodsForm.form.reset();
//			Ext.getCmp('T_stock-movegoods_MoveOutStockForm').allowBlank = true;
			stock.movegoodsindex.moveoutForm.form.reset();
			stock.movegoodsindex.moveinForm.form.reset();
		}
	}
});

//修改库存迁移窗口
Ext.define('Foss.stock.movegoods.moveGoodsModifyWindow', {
	extend: 'Ext.window.Window',
	title: '迁移货物库存',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var moveGoodsModifyPanel = Ext.create('Foss.stock.movegoods.MoveGoodsModifyPanel'); 	
		me.items = [
		    moveGoodsModifyPanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.movegoodsindex.moveGoodsModifyForm.form.reset();
//			Ext.getCmp('T_stock-movegoods_MoveOutStockForm').allowBlank = true;
			stock.movegoodsindex.moveoutModifyForm.form.reset();
			stock.movegoodsindex.moveinModifyForm.form.reset();
		}
	}
});

//查看库存迁移窗口
Ext.define('Foss.stock.movegoods.moveGoodsLookWindow', {
	extend: 'Ext.window.Window',
	title: '迁移货物库存',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);	
		var moveGoodsLookPanel = Ext.create('Foss.stock.movegoods.MoveGoodsLookPanel'); 	
		me.items = [
		    moveGoodsLookPanel   
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.movegoodsindex.moveGoodsLookForm.form.reset();
//			Ext.getCmp('T_stock-movegoods_MoveOutStockForm').allowBlank = true;
			stock.movegoodsindex.moveoutLookForm.form.reset();
			stock.movegoodsindex.moveinLookForm.form.reset();
		}
	}
});


//申请页面
Ext.define('Foss.stock.movegoods.MoveGoodsPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.movegoodsindex.moveGoodsForm = Ext.create('Foss.stock.movegoods.MoveStockForm');	
			stock.movegoodsindex.moveoutForm = Ext.create('Foss.stock.movegoods.MoveOutStockForm');
			stock.movegoodsindex.moveinForm = Ext.create('Foss.stock.movegoods.MoveInStockForm');
			stock.movegoodsindex.moveGoodsForm.getForm().findField('goods_type').setReadOnly(false);
			stock.movegoodsindex.moveGoodsForm.getForm().findField('remarks').setReadOnly(false);
			
			stock.movegoodsindex.moveinForm.getForm().findField('movein_name').setReadOnly(false);
			stock.movegoodsindex.moveoutForm.getForm().findField('moveout_name').setReadOnly(false);
			
			
			
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.movegoodsindex.moveGoodsForm,
			    stock.movegoodsindex.moveoutForm,
			    stock.movegoodsindex.moveinForm
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [{
				text: '重置',
//			text: stock.movegoods.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){  
					stock.movegoodsindex.moveGoodsForm.form.reset();
					stock.movegoodsindex.moveoutForm.form.reset();
				stock.movegoodsindex.moveinForm.form.reset();
				
			}
				
		},{
				text: '取消',
//			text: stock.movegoods.i18n('foss.stock.reset'),
				handler: function(){  
					Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsWindow().close();
//					 	Ext.getCmp('Foss.stock.movegoods.moveGoodsWindow_id').close();
				}	
				
			},{
			xtype: 'container',
			columnWidth:.80,
			html: '&nbsp;'
			},{ 
		  	text: '申请迁移',
//		  text: stock.stockmanage.i18n('foss.stock.confirminstock'),
		  	cls : 'yellow_button',
		  	columnWidth:.08,
//		  	hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
			gridContainer: this,
		    handler: function(){                                                     
		    		var goods_type =  stock.movegoodsindex.moveGoodsForm.form.findField('goods_type').getRawValue();
		    		var moveout = stock.movegoodsindex.moveoutForm.form.findField('moveout_name').getRawValue();
		    		var movein =stock.movegoodsindex.moveinForm.form.findField('movein_name').getRawValue();
		    		//当前部门code
		    		var current_org_code = stock.movegoodsindex.org_code;
		    		//移出部门code
		    		var moveout_org_code = stock.movegoodsindex.moveoutForm.form.findField('moveout_code').getValue();
		    		//移入部门code
		    		var movein_org_code = stock.movegoodsindex.moveinForm.form.findField('movein_code').getValue();
		    		//移出库区code    outAreaCode orgCodeName
		    		var moveout_area_code = stock.movegoodsindex.moveoutForm.form.findField('outAreaCode').getValue();
		    		//移出库区name
		    		var moveout_areaname = stock.movegoodsindex.moveoutForm.form.findField('orgCodeName').getValue();
		    		//移入库区code
		    		var movein_area_code = stock.movegoodsindex.moveinForm.form.findField('inAreaCode').getValue();
		    		//移入库区name
		    		var movein_areaname = stock.movegoodsindex.moveinForm.form.findField('orgCodeName').getValue();
		    		if(stock.movegoodsindex.moveGoodsForm.form.findField('remarks').getValue().length>100){
								Ext.ux.Toast.msg('提示', '备注长度不能超过100个字符', 'error', 2000);
								return;
						}
		    		if(Ext.isEmpty(goods_type)){
		    			Ext.ux.Toast.msg('提示', '请选择迁移货物类型！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		if(Ext.isEmpty(moveout)){
		    			Ext.ux.Toast.msg('提示', '请选择移出部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		if(Ext.isEmpty(movein)){
		    			Ext.ux.Toast.msg('提示', '请选择移入部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        		//移出库区code为空,给予其提示不让迁移
		    		}if(Ext.isEmpty(moveout_area_code)){
		    			Ext.ux.Toast.msg('提示', '移出库区code为空,不允许迁移', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        	//移入库区code为空,给予其提示不让迁移
		    		}if(Ext.isEmpty(movein_area_code)){
		    			Ext.ux.Toast.msg('提示', '移入库区code为空,不允许迁移', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        		//如果移入部门和移出部门 都不等于申请人所在部门    给予其提示
		    		}if(current_org_code != moveout_org_code && current_org_code!= movein_org_code){
		    			Ext.ux.Toast.msg('提示', '移出部门和移入部门必须有一个是当前部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		//下面是移出部门的提示信息
		    		if(moveout_area_code == 'N/A'){
		    			var moveoutMessage = '['+moveout+']';
		    		}else{
		    			var moveoutMessage = '['+moveout+']的'+'['+moveout_areaname+']库区';
		    		}
		    		//移入部门的提示信息
		    		if(movein_area_code == 'N/A'){
		    			var moveinMessage = '['+movein+']';
		    		}else{
		    			var moveinMessage = '['+movein+']的'+'['+movein_areaname+']库区';	
		    		}
		    		
		    		Ext.MessageBox.confirm('库存货物迁移确认', '确认将'+moveoutMessage+'的货物移动到'+moveinMessage+'中吗?' , function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
					
						
							  //把需要的参数全赋给jsonParam      
							 	//确认入库迁移 ******************************************************************************************************************
							 	var goods_type = stock.movegoodsindex.moveGoodsForm.form.findField('goods_type').value;
							 	var remarks = stock.movegoodsindex.moveGoodsForm.form.findField('remarks').value;
							 	var moveout_name = stock.movegoodsindex.moveoutForm.form.findField('moveout_name').getRawValue();
							 	var moveout_code = stock.movegoodsindex.moveoutForm.form.findField('moveout_name').getValue();
							 	var moveout_areaname = stock.movegoodsindex.moveoutForm.form.findField('orgCodeName').getValue();
							 	var moveout_areacode = stock.movegoodsindex.moveoutForm.form.findField('outAreaCode').getRawValue();
							 	var movein_name = stock.movegoodsindex.moveinForm.form.findField('movein_name').getRawValue();
							 	var movein_code = stock.movegoodsindex.moveinForm.form.findField('movein_name').getValue();
							 	var movein_areaname = stock.movegoodsindex.moveinForm.form.findField('orgCodeName').getValue();
							 	var movein_areacode =  stock.movegoodsindex.moveinForm.form.findField('inAreaCode').getRawValue();
							  var jsonParam = {'stockVO':{'moveGoodsStockQueryDto':{
							 				'goods_type':goods_type,
							 				'remarks':remarks,
							 				'moveout_name':moveout_name,
							 				'moveout_code':moveout_code,
							 				'moveout_areaname':moveout_areaname,
							 				'moveout_areacode':moveout_areacode,
							 				'movein_name':movein_name,
							 				'movein_code':movein_code,
							 				'movein_areaname':movein_areaname,
							 				'movein_areacode':movein_areacode
							 				}}};
	 
	 						if(btn == 'yes'){
					
	        			Ext.Ajax.request({
	    						url: stock.realPath('moveGoodsInStock.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				//响应信息   只要能相应就关掉页面,重新加载数据
				    				Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsWindow().close();
	    							stock.movegoodsindex.waybillGrid.store.load();
				    			},
				    			exception : function(response) {
				    				var result = Ext.decode(response.responseText);
				    				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
				    			//	Ext.ux.Toast.msg(stock.movegoodsindex.i18n('error.query.transfer.center.org'), result.message, 'error', 3000);
				    			}				    				
	    					}); 
	        			
	    				}
				 }); 
				
			}
				
		}]
		
});
				
//修改页面
Ext.define('Foss.stock.movegoods.MoveGoodsModifyPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.movegoodsindex.moveGoodsModifyForm = Ext.create('Foss.stock.movegoods.MoveStockForm');	
			stock.movegoodsindex.moveoutModifyForm = Ext.create('Foss.stock.movegoods.MoveOutStockModifyForm');
			stock.movegoodsindex.moveinModifyForm = Ext.create('Foss.stock.movegoods.MoveInStockModifyForm');
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.movegoodsindex.moveGoodsModifyForm,
			    stock.movegoodsindex.moveoutModifyForm,
			    stock.movegoodsindex.moveinModifyForm
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [{
				text: '重置',
//			text: stock.movegoods.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){  
					stock.movegoodsindex.moveGoodsModifyForm.form.reset();
					stock.movegoodsindex.moveoutModifyForm.form.reset();
				stock.movegoodsindex.moveinModifyForm.form.reset();
				
			}
				
		},{
				text: '取消',
//			text: stock.movegoods.i18n('foss.stock.reset'),
				handler: function(){  
					Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsModifyWindow().close();
//					 	Ext.getCmp('Foss.stock.movegoods.moveGoodsWindow_id').close();
				}	
				
			},{
				text: '确认修改',
				cls : 'yellow_button',
//			text: stock.movegoods.i18n('foss.stock.reset'),
				handler: function(){          		                                                    
		    		var goods_type =  stock.movegoodsindex.moveGoodsModifyForm.form.findField('goods_type').getRawValue();
		    		var moveout = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_name').getRawValue();
		    		var movein = stock.movegoodsindex.moveinModifyForm.form.findField('movein_name').getRawValue();
		    		//当前部门code
		    		var current_org_code = stock.movegoodsindex.org_code;
		    		//移出部门code
		    		var moveout_org_code = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_code').getValue();
		    		//移入部门code
		    		var movein_org_code = stock.movegoodsindex.moveinModifyForm.form.findField('movein_code').getValue();
		    		//移出库区code    outAreaCode orgCodeName
		    		var moveout_area_code = stock.movegoodsindex.moveoutModifyForm.form.findField('outAreaCode').getValue();
		    		//移出库区name
		    		var moveout_areaname = stock.movegoodsindex.moveoutModifyForm.form.findField('orgCodeName').getValue();
		    		//移入库区code
		    		var movein_area_code = stock.movegoodsindex.moveinModifyForm.form.findField('inAreaCode').getValue();
		    		//移入库区name
		    		var movein_areaname = stock.movegoodsindex.moveinModifyForm.form.findField('orgCodeName').getValue();
		    		if(stock.movegoodsindex.moveGoodsModifyForm.form.findField('remarks').getValue().length>100){
								Ext.ux.Toast.msg('提示', '备注长度不能超过100个字符', 'error', 2000);
								return;
						}
		    		if(Ext.isEmpty(goods_type)){
		    			Ext.ux.Toast.msg('提示', '请选择迁移货物类型！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		if(Ext.isEmpty(moveout)){
		    			Ext.ux.Toast.msg('提示', '请选择移出部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		if(Ext.isEmpty(movein)){
		    			Ext.ux.Toast.msg('提示', '请选择移入部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;	
	        		//移出库区code为空,给予其提示不让迁移
		    		}if(Ext.isEmpty(moveout_area_code)){
		    			Ext.ux.Toast.msg('提示', '移出库区code为空,不允许迁移', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        	//移入库区code为空,给予其提示不让迁移
		    		}if(Ext.isEmpty(movein_area_code)){
		    			Ext.ux.Toast.msg('提示', '移入库区code为空,不允许迁移', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        		//如果移入部门和移出部门 都不等于申请人所在部门    给予其提示
		    		}if(current_org_code != moveout_org_code && current_org_code!= movein_org_code){
		    			Ext.ux.Toast.msg('提示', '移出部门和移入部门必须有一个是当前部门！', 'error', 2000);
	        		//Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
		    		}
		    		
		    		
		    		Ext.MessageBox.confirm('修改库存货物迁移确认', '确认修改移入移出部门吗?' , function(btn){
//						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
					
						
							  //把需要的参数全赋给jsonParam
							 	//确认修改入库迁移 ************************************************************************************
							 	
							 	//获取id   
//           		  var stTaskId = grid.getStore().getAt(rowIndex).data.id;	
           		  var id = stock.movegoodsindex.moveGoodsModifyForm.form.findField('id').value;
							 	var goods_type = stock.movegoodsindex.moveGoodsModifyForm.form.findField('goods_type').value;
							 	var remarks = stock.movegoodsindex.moveGoodsModifyForm.form.findField('remarks').value;
							 	var moveout_name = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_name').getRawValue();
							 	var moveout_code = stock.movegoodsindex.moveoutModifyForm.form.findField('moveout_code').getValue();
							 	var moveout_areaname = stock.movegoodsindex.moveoutModifyForm.form.findField('orgCodeName').getValue();
							 	var moveout_areacode = stock.movegoodsindex.moveoutModifyForm.form.findField('outAreaCode').getRawValue();
							 	var movein_name = stock.movegoodsindex.moveinModifyForm.form.findField('movein_name').getRawValue();
							 	var movein_code = stock.movegoodsindex.moveinModifyForm.form.findField('movein_code').getValue();
							 	var movein_areaname = stock.movegoodsindex.moveinModifyForm.form.findField('orgCodeName').getValue();
							 	var movein_areacode =  stock.movegoodsindex.moveinModifyForm.form.findField('inAreaCode').getRawValue();
							  var jsonParam = {'stockVO':{'moveGoodsStockQueryDto':{
							  			'id' : id,
							 				'goods_type':goods_type,
							 				'remarks':remarks,
							 				'moveout_name':moveout_name,
							 				'moveout_code':moveout_code,
							 				'moveout_areaname':moveout_areaname,
							 				'moveout_areacode':moveout_areacode,
							 				'movein_name':movein_name,
							 				'movein_code':movein_code,
							 				'movein_areaname':movein_areaname,
							 				'movein_areacode':movein_areacode
							 				}}};
	 
	 						if(btn == 'yes'){
					
	        			Ext.Ajax.request({
	    						url: stock.realPath('moveGoodsModifyInStock.action'),
				    			jsonData:jsonParam,
				    			success:function(response){
//				    				Ext.Msg.hide();
				    				var result = Ext.decode(response.responseText);
				    				//响应信息
				    				stock.movegoodsindex.waybillGrid.store.load();
										Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsModifyWindow().close();
				    			}	
				    			//确定完后把此页面给关闭掉
				    				
	    					}); 	
	    				}
				 }); 
				}				
		}]
		
});



//查看迁移页面
Ext.define('Foss.stock.movegoods.MoveGoodsLookPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.movegoodsindex.moveGoodsLookForm = Ext.create('Foss.stock.movegoods.MoveStockForm');	
			stock.movegoodsindex.moveoutLookForm = Ext.create('Foss.stock.movegoods.MoveOutStockForm');
			stock.movegoodsindex.moveinLookForm = Ext.create('Foss.stock.movegoods.MoveInStockForm');
			stock.movegoodsindex.moveGoodsLookForm.getForm().findField('goods_type').setReadOnly(false);
			stock.movegoodsindex.moveGoodsLookForm.getForm().findField('remarks').setReadOnly(false);
			
			stock.movegoodsindex.moveinLookForm.getForm().findField('movein_name').setReadOnly(false);
			stock.movegoodsindex.moveoutLookForm.getForm().findField('moveout_name').setReadOnly(false);
			
			
			
	//		stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.movegoodsindex.moveGoodsLookForm,
			    stock.movegoodsindex.moveoutLookForm,
			    stock.movegoodsindex.moveinLookForm
			    //, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		
		
		buttons: [{
				text: '确定',
				cls : 'yellow_button',
//			text: stock.movegoods.i18n('foss.stock.reset'),
				handler: function(){  
						Ext.getCmp('T_stock-movegoodsindex_content').getMoveGoodsLookWindow().close();
				}				
		}]
		
});


Ext.onReady(function(){
	Ext.Ajax.request({
		url: stock.realPath('canLookMoveGoods.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			stock.movegoodsindex.org_code = result.stockVO.stockOrgCode;
			stock.movegoodsindex.org_name = result.stockVO.stockOrgName;
			
			Ext.QuickTips.init();

			var queryform = Ext.create('Foss.stock.movegoods.QueryForm');
			stock.movegoodsindex.queryform = queryform;
			var waybillGrid = Ext.create('Foss.stock.movegoods.WaybillGrid');
			stock.movegoodsindex.waybillGrid = waybillGrid;
			
			stock.movegoodsindex.waybillGoodsMap = new Ext.util.HashMap();//用于查询库存迁移界面存放已勾选的库存迁移申请
			
			Ext.create('Ext.panel.Panel',{
			id:'T_stock-movegoodsindex_content',
			cls:"panelContentNToolbar",
		  	bodyCls:'panelContent-body',
		  	//申请的页面
		  	moveGoodsWindow:null,
		  	getMoveGoodsWindow: function() {
					if(this.moveGoodsWindow == null) {
						this.moveGoodsWindow = Ext.create('Foss.stock.movegoods.moveGoodsWindow');
					}
					return this.moveGoodsWindow;
				},
				//修改带出的页面
				moveGoodsModifyWindow:null,
				getMoveGoodsModifyWindow: function() {
				if(this.moveGoodsModifyWindow == null) {
					this.moveGoodsModifyWindow = Ext.create('Foss.stock.movegoods.moveGoodsModifyWindow');
				}
				return this.moveGoodsModifyWindow;
				},
				//查看的页面
				moveGoodsLookWindow:null,
				getMoveGoodsLookWindow: function() {
				if(this.moveGoodsLookWindow == null) {
					this.moveGoodsLookWindow = Ext.create('Foss.stock.movegoods.moveGoodsLookWindow');
				}
				return this.moveGoodsLookWindow;
				},
				items : [queryform,waybillGrid],
				renderTo: 'T_stock-movegoodsindex-body'
			});
			stock.movegoodsindex.queryform.getForm().findField('org_code').setValue(stock.movegoodsindex.org_code);
			stock.movegoodsindex.queryform.getForm().findField('org_name').setValue(stock.movegoodsindex.org_name);
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('获取外场部门失败', result.message, 'error', 3000);
		//	Ext.ux.Toast.msg(stock.movegoodsindex.i18n('error.query.transfer.center.org'), result.message, 'error', 3000);
		}
	});
	//向后台发送一个判断当前用户角色的请求   如果有外场经理角色  就显示修改按钮  否则不显示
	stock.movegoodsindex.isTransferManager = 'N';
	Ext.Ajax.request({
				url: stock.realPath('selectUserRoleByUserCode.action'),
				success: function(response){
					result = Ext.decode(response.responseText);
					stock.movegoodsindex.isTransferManager = result.stockVO.moveGoodsStockDto.isMoveGoodsMan;
				}
			});
});

