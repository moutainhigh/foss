
/**
 *  包装材料model
 * @author 046130-foss-xuduowei
 * @date 2012-10-29 下午4:07:46
 */
Ext.define('Foss.FinishPacked.PackedMateModel',{
	extend:'Ext.data.Model',
	fields:[  			 
{name:'valueName'},
{name:'valueCode'}]
});

/**
 *  包装材料store
 * @author 046130-foss-xuduowei
 * @date 2012-10-29 下午4:07:46
 */
Ext.define('Foss.FinishPacked.PackedMateStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.FinishPacked.PackedMateModel',
	proxy: {
	       type: 'ajax',
	       //url : '../packaging/queryPackedMate.action',
	       url : packaging.realPath('queryPackedMate.action'),
	       reader: {
	           type: 'json',
	           root: 'queryUnpackVo.packedMateList'
	       }
	 }, 
	 //自动加载
	 autoLoad: true
		/*data : {
			'items':[
				{'packedMate':'木架'},
				{'packedMate':'木箱'},
				{'packedMate':'纸箱'},
				{'packedMate':'纤袋'}
				]
		},
		 //定义一个代理对象
		proxy: {
			//代理的类型为内存代理
			type: 'memory',
			//定义一个读取器
			reader: {
				//以JSON的方式读取
				type: 'json',
				//定义读取JSON数据的根对象
				root: 'items'
			}
		}*/
});  

/**
 * 包装录入左侧的运单信息
 */
Ext.define('Foss.FinishPacked.PackageForm', {
    extend: 'Ext.form.Panel',
    //id:'Foss_packaging_querypacked_PackageForm_ID',
	defaults : {
		margin:'5 5 5 5',
		anchor: '100%',
		labelWidth:100
	},
	defaultType : 'textfield',
	layout: 'column',
	items : [{
			name: 'wayBillNumber',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.wayBillNumber'),//'运单号',
	        vtype: 'waybill',
	        //id : 'Foss_finishPacked_PackageForm_wayBillNumber_ID',
	        allowBlank: false,
			columnWidth:.5,
			enableKeyEvents :true,
			getPackInfo:function(form,waybillno,packageType){
					var panel = form.up('panel');
					var container = panel.getSerialContainer();
					var oldGrid = container.getOldSerialNumberGrid();
					var newGrid = container.getNewSerialNumberGrid();
					var packerGrid = panel.getPackageEmpGrid();
					packerGrid.getStore().removeAll();
					Ext.Ajax.request({
						//url: '../packaging/queryWaybillInfo.action',
						url : packaging.realPath('queryWaybillInfo.action'),
						params: {
							'queryUnpackVo.waybillno':waybillno,
							'queryUnpackVo.packageType':packageType
						},
						success: function(response){
							var result = Ext.decode(response.responseText);									
							//包装录入主信息
							var waybillForm = form.getForm();

                            //判定是否有当前包装需求
                            if(!Ext.isEmpty(result.queryUnpackVo.waybillPackEntity.msg)){

                                //Ext.MessageBox.alert('提示', '保存成功！！', this);
                                Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), result.queryUnpackVo.waybillPackEntity.msg , 'success', 5000);

                            }

							if(Ext.isEmpty(result.queryUnpackVo.waybillPackEntity.packedMate)){
								result.queryUnpackVo.waybillPackEntity.packedMate = form.getForm().findField('packedMate').getValue();
                            }
							waybillForm.setValues(result.queryUnpackVo.waybillPackEntity);
							//将最大流水号设为全局变量
							Ext.apply(packaging.querypacked,{
								maxSerialNo : result.queryUnpackVo.maxSerialNo
							});
							//为新旧流水号关系设值
							oldGrid.store.loadData(result.queryUnpackVo.serialRelationList);
							//初始化流水号界面显示
							container.initOldSerialNumberDisplay();
							//加载新流水号
							newGrid.store.loadData(result.queryUnpackVo.newSerialList);		
							//加载包装人
							packerGrid.store.loadData(result.queryUnpackVo.packedPersonList);
						},
						failure: function(response){
							var result = Ext.decode(response.responseText);
							//错误  请求失败，请稍后再试
				            Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.wayBillNumber.errorMsg'));   
				        },
				        exception: function(response){
				        	var result = Ext.decode(response.responseText);
				            Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), result.message);   
				        }

					});
			},
			listeners: {	
				//输入运单号回车或用TAB触发查询事件
				specialkey: function(field, e){
					var form = field.up('form');
					var waybillno = field.getValue();
					// e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
					// e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
					if (e.getKey() == e.ENTER || e.getKey() == e.TAB) {									
						if(waybillno.length>7 && waybillno.length<10){
							packaging.querypacked.oldValue = 'WOODEN_FRAME';
							this.getPackInfo(form,waybillno,'');
						}			
				}
			}
			}
		},{
			name: 'packedMate',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.packedMate'),//'包装材料',
	        //下拉框控件
			xtype: 'combo',	
			frame:true,
	        store : Ext.create('Foss.FinishPacked.PackedMateStore'),
	        queryMode: 'local',
			value: 'WOODEN_FRAME',
	        valueField:'valueCode',
		    displayField: 'valueName',
		    //editable : false,
	        allowBlank: false,
			columnWidth:.5,
			listeners: {
				//检测修改事件
				change: function(field,newValue,oldValue,e){
					var panel = field.up('form').up('panel');
					
					if(!Ext.isEmpty(packaging.querypacked.oldValue) && newValue == 'MAKE_WOODEN_STOCK'){
						//选择打木托的时候判定所有的打木架的需求打包完毕了才能打木托
						var oldGrid = panel.getSerialContainer().getOldSerialNumberGrid(),
						oldSerialNumItems = oldGrid.getStore().data.items,
						oldSerialModel = oldGrid.getStore().data;
						for(var i=0;i<oldSerialNumItems.length;i++){
							if(oldSerialNumItems[i].data.packageType=='WOODEN_FRAME' && oldSerialNumItems[i].data.isPacked=='N'){
								field.up('form').getForm().findField('packedMate').setValue(oldValue);
								Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), '请所有需要打木架的货物打包完毕在打木托!'); 
								return;
							}
						}
						
					}else{
						newValue = 'WOODEN_FRAME';
					}
					//如果第一次选择和第二次选择的是一样的打木架类型则不需要去查询数据库
					if(packaging.querypacked.oldValue == newValue){
						return ;
					}
					
					packaging.querypacked.oldValue = newValue;
					var waybillno = panel.items.items[0].items.items[0].value;
					if(waybillno.length>7 && waybillno.length<10){
						panel.items.items[0].items.items[0].getPackInfo(field.up('form'),waybillno,packaging.querypacked.oldValue);
					}
				}
			}
		},{
			name: 'waybillCreateDeptName',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.waybillCreateDeptName'),//'收货部门',
	        readOnly: true,
	        allowBlank: false,
			columnWidth:.5
		},{//收货部门编码
			name: 'waybillCreateDept',
	        hidden:true,
			columnWidth:.5
		},{
			name: 'unPackedVolumeCreate',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.unPackedVolumeCreate'),//'货物原始体积(方)',
	        readOlny : true,
			columnWidth:.5
		},{
			name: 'unPackedVolume',
			fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.unPackedVolume'),//'开单后乘以系数的体积(方)',
			readOlny : true,
			columnWidth:.5
		},{
			name: 'packedVolume',
			fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.packedVolume'),//'包装后体积(方)',
			text: 'numberfield',
			//regex:/^([1-9]{1,5})(.[0-9]{1,3})?$/,
			//regex:/^([1-9]{0,4}).?\d{1,3}$/,
			//regex: /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
			regex: /^\d{0,5}\.?\d{1,3}$/,
			regexText:packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.packedVolume.regexText'),//"体积输入有误.必须是数字,小数点后最大保留3位!",
			maxlength:8,
			allowBlank: false,
			columnWidth:.5
		},{
			name: 'goodsName',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.goodsName'),//'货物名称',
	        //id : 'Foss_finishPacked_PackageForm_goodsName_ID',
	        readOnly: true,
	        allowBlank: false,
			columnWidth:.5
		},{
			name: 'plusNum',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.plusNum'),//'加托个数',
	        text: 'numberfield',
	        regex:/^(0|([1-9]\d{0,8}))$/,
    		regexText:packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.plusNum.regexText'),//"件数输入有误.必须是数字,长度小于等于9位!",
	        allowBlank: false,
			columnWidth:.5,
			listeners:{
			    blur:function(field, event, eOpts ){
			    //获取运单号
			    //获取运单号
			    var maskQty=field.value;
			    //判断非空
			    if(maskQty==null||maskQty==''){
			    	Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'),'请输入加托个数');
			    	return;
			    }
			    //判断加托个数是否为整数
			    if( maskQty.indexOf('.')>0 ){
			    	Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'),'加托个数只能为整数');
			    	this.reset();
			    	return;
			    }
			    
				var waybillNo=field.up('form').items.items[0].value;
				if(waybillNo==''||waybillNo==null){
					Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'),'请输入运单号');
					this.reset();
					return;
				}
				
			    //packaging.querypacked.maskFlag=0;	
				params = {
					'queryUnpackVo.waybillPackEntity.wayBillNumber' : waybillNo,
					'queryUnpackVo.waybillPackEntity.plusNum' : maskQty

				}
			     Ext.Ajax.request({
					url : packaging.realPath('validatePackedMask.action'),
					params : params,
					// 获取当前登录人的所在部门
					success : function(response) {
						//packaging.querypacked.maskFlag=0;
					},
					exception : function(response) {
						//field.value=null;
						//packaging.querypacked.maskFlag=1;
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'),result.message);
						this.reset();
					}
				});
			    	
			    }
			
			
			}
		},{
			name: 'packRequire',
			xtype: 'textareafield',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.packRequire'),//'代包装要求',
	        //id : 'Foss_finishPacked_PackageForm_packRequire_ID',
	        readOnly: true,
	        allowBlank: false,
			columnWidth:.5
		},{
			name: 'remark',
			xtype: 'textareafield',
	        fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.remark'),//'备注',
	        regex:/^.{0,1000}$/,
    		regexText:packaging.querypacked.i18n('foss.packaging.querypacked.packageForm.remark.regexText'),//'长度过长',
			columnWidth:.5
		},{
			name: 'id',
			hidden: true,
			columnWidth:.5
		},{
			name: 'modifyDateOptimistic',
			hidden: true,
			columnWidth:.5
		}]     
});

/**
 * 根据运单号查询包装录入信息，新增和修改时都调用此方法
 */


/**
 *原流水号model
 */
Ext.define('Foss.FinishPacked.OldSerialNumberModel',{
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'oldSerialNo',type:'string'},
		{name: 'newSerialNo',type:'string'},
		{name: 'isPacked',type:'string'},
		{name: 'tempSerialNo',type:'string'},
		{name: 'relationId',type:'string'},
		{name: 'actualId',type:'string'},
		{name: 'packageType',type:'string'},
		{name: 'disRow',type:'string'}
	]
});

/**
 * 原流水号store
 */
Ext.define('Foss.FinishPacked.OldSerialNumberStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.FinishPacked.OldSerialNumberModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//url:'../packaging/queryOldSerial.action',
			url : packaging.realPath('queryOldSerial.action'),
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	listeners:{
		load: function( store, records, successful, eOpts ){
			//Ext.getCmp('Foss_finishPacked_SerialContainer_ID').initOldSerialNumberDisplay();
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 新流水号model
 */
Ext.define('Foss.FinishPacked.NewSerialNumberModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'newSerialNo',type:'string'}
		//{name: 'serialNumberList'}
	]
});

/**
 * 新流水号store
 */
Ext.define('Foss.FinishPacked.NewSerialNumberStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.FinishPacked.NewSerialNumberModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为memory
		type: 'memory'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 包装人员model
 */
Ext.define('Foss.FinishPacked.PackagerModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'empCode',type:'string'},
		{name: 'empName',type:'string'}
	]
});

/**
 * 包装人员store
 */
Ext.define('Foss.FinishPacked.PackagerStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.FinishPacked.PackagerModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为ajax
		type: 'ajax',
		//type: 'memory',
		//url:'../packaging/queryPackedPerson.action',
		url : packaging.realPath('queryPackedPerson.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'queryUnpackVo.packedPersonList'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


/**
 * 显示流水号model
 */
Ext.define('Foss.FinishPacked.ShowSerialNoModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'oldSerialNo',type:'string'}
	]
});

/**
 * 显示流水号store
 */
Ext.define('Foss.FinishPacked.ShowSerialNoStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.FinishPacked.ShowSerialNoModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为memory
		type: 'memory'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 显示原流水号grid
 */
Ext.define('Foss.FinishPacked.ShowSerianoNoGrid', {
    extend: 'Ext.grid.Panel',
	store: Ext.create('Foss.FinishPacked.ShowSerialNoStore'),
	autoScroll:true,
	cls: 'autoWidth',
	hideHeaders: true,
	columns: [{
		width : 40,
		dataIndex: 'oldSerialNo' 
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	bindData : function(record,value,metadata,store,view){
		var newSerialNo = record.get('newSerialNo');
		var grid = view.up('grid'),
		    container = grid.up('container'),
		    oldGrid = container.getOldSerialNumberGrid(),
		    oldStore = oldGrid.getStore();
		var oldArray = new Array();
		oldStore.each(function(item,index){
			if(item.get('newSerialNo')==newSerialNo){
				oldArray.push({'oldSerialNo': item.get('oldSerialNo')});
			}
		})
		this.store.loadData(oldArray);
	}
});

/**
 * 流水号处理页面
 */
Ext.define('Foss.FinishPacked.SerialContainer', {
	extend: 'Ext.container.Container',
	//id:'Foss_finishPacked_SerialContainer_ID',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
	autoScroll:false,
	frame : true,
	border : false,
	items : null,
	oldSerialNumberGrid : null,
	
	/**
	 * 流水号在包装界面显示时，根据是否包装状态来决定是否是已包装的不可编辑
	 */
	initOldSerialNumberDisplay : function(){
		var me = this;
		var oldSerialNumItems = me.getOldSerialNumberGrid().getStore().data.items,
			oldSerialModel = me.getOldSerialNumberGrid().getStore().data;
		//var oldLine = me.getOldSerialNumberGrid().getSelectionModel();
		for(var i=0;i<oldSerialNumItems.length;i++){
			if(oldSerialNumItems[i].data.isPacked=='Y'){
				oldSerialNumItems[i].set('disRow', 'Y');
				me.getOldSerialNumberGrid().getView().addRowCls(oldSerialNumItems[i], 'disabledrow');
			}else{
				oldSerialNumItems[i].set('disRow', 'N');
			}
		}
	},
	/**
	 * 创建旧流水号grid
	 */
	getOldSerialNumberGrid : function(){
		var me = this;
		if(this.oldSerialNumberGrid==null){
			this.oldSerialNumberGrid = Ext.create('Ext.grid.Panel', {
				//徐多为
				title: packaging.querypacked.i18n('foss.packaging.querypacked.serialContainer.oldSerialNumberGrid.title'),//'原流水号明细',
				//id: 'Foss_FinishPacked_OldSerialNumberGrid_ID',
				//itemId: 'oldSerialNumber',
				store: Ext.create('Foss.FinishPacked.OldSerialNumberStore'),
				cls:'checkerHide',
				flex: 1,
				autoScroll: true,
				//hideHeaders: true,//add
				viewConfig : {
					listeners : {
						viewready: function(){
							me.initOldSerialNumberDisplay();
						}
					}
				},
				//用于保存已经合并的记录
				selectedRecords: new Array(),
				addUnSelRecord : function(record){
					this.selectedRecords[this.selectedRecords.length] = record;
				},
                isUnSelRecord: function(record){
                    for(var i=0;i<this.selectedRecords.length;i++){
						if(record == this.selectedRecords[i]){
							return true;
						}else{
                            return false;
                        }
					}
                },
				removeSelectedRecord : function(record){
					for(var i=0;i<this.selectedRecords.length;i++){
						if(record == this.selectedRecords[i]){
                            Ext.Array.remove(this.selectedRecords,record);
						}
					}
				},
				selModel: Ext.create('Ext.selection.CheckboxModel',{
					listeners:{
						'beforeselect': 
						function( SelectionModel, record,rowIndex, eOpts ) {
							if(record.data.disRow == 'Y'){
								return false;
							}
							for(var i=0;i<me.getOldSerialNumberGrid().selectedRecords.length;i++){
								if(record.data.serialNumber == me.getOldSerialNumberGrid().selectedRecords[i]){
									return false;
								}
							}
							return true;
						}
					}
				}),
				columns: [{
					width : 100,
					dataIndex: 'oldSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'newSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'isPacked' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'tempSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'relationId' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'packageType' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'actualId' 
				}]
			});
		}
		return this.oldSerialNumberGrid;		
	},
	/**
	 * 流水号变动功能区，分左移和右移
	 */
	operationSerialContainer : null,
	getOperationSerialContainer : function(){
		var me = this;
		if(this.operationSerialContainer==null){
			this.operationSerialContainer = Ext.create('Ext.container.Container',{
				flex: 0.4,
				buttonAlign : 'center',
				hidden : true,
				autoScroll: false,
				layout : 'column',
				items : [{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:60px;border:none',
						hide:true
					},{
						/**
						 * 右移按钮，是流水号从未包装变成已包装的过程
						 */
						columnWidth : 1,
						xtype : 'button',
						iconCls : 'deppon_icons_turnright',
						handler : function(){
							var oldSm = me.getOldSerialNumberGrid().getSelectionModel(),
								oldSmArray = new Array(),
								oldSmArray = oldSm.getSelection(),
								length = oldSm.getSelection().length;
							
							if (oldSm.getSelection().length > 1) {
								var oldRecords = new Array();
								for(var i=0;i<oldSm.getSelection().length;i++){
                                    // 增加CSS
									oldSm.getSelection()[i].set('newSerialNo', packaging.querypacked.maxSerialNo);
									//标记已合并
									oldSm.getSelection()[i].set('disRow', 'Y');
									me.getOldSerialNumberGrid().getView().addRowCls(oldSm.getSelection()[i], 'disabledrow');
								}
								var newRecord = Ext.create('Foss.FinishPacked.NewSerialNumberModel', {
									newSerialNo : packaging.querypacked.maxSerialNo
									//serialNumberList : oldRecords
								});								
								me.getNewSerialNumberGrid().getStore().insert(me.getNewSerialNumberGrid().getStore().count(),newRecord);
								//获取最大流水号
								Ext.Ajax.request({
									//url: '../packaging/queryMaxSerialNo.action',
									url : packaging.realPath('queryMaxSerialNo.action'),
									params: {
										'queryUnpackVo.serialNo': packaging.querypacked.maxSerialNo
									},
									success: function(response){
										result = Ext.decode(response.responseText);
										packaging.querypacked.maxSerialNo = result.queryUnpackVo.maxSerialNo;
									}								
								});								
								//反选被选择的旧流水号
								oldSm.deselectAll();
								
							}else if(oldSm.getSelection().length == 1){ 
								oldSm.getSelection()[0].set('newSerialNo', oldSm.getSelection()[0].data.oldSerialNo);
								//oldSm.getSelection()[0].set('isChecked', '1');
								//标记已合并
								oldSm.getSelection()[0].set('disRow', 'Y');
								me.getOldSerialNumberGrid().getView().addRowCls(oldSm.getSelection()[0], 'disabledrow');
								var newRecord = Ext.create('Foss.FinishPacked.NewSerialNumberModel', {
									newSerialNo : oldSm.getSelection()[0].data.oldSerialNo
									//serialNumberList : oldRecords
								});								
								me.getNewSerialNumberGrid().getStore().insert(me.getNewSerialNumberGrid().getStore().count(),newRecord);
											
								//反选被选择的旧流水号
								oldSm.deselectAll();	
								
							}else {
								//提示，请选择原流水号码
								Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'),packaging.querypacked.i18n('foss.packaging.querypacked.serialContainer.operationSerialContainer.onlyOldHint'));
								
							}
						}
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						/**
						 * 左移按钮，是流水号从已包装变成未包装的过程
						 */
						columnWidth : 1,
						xtype : 'button',
						//style : 'width: 80px;height:25px;',
						iconCls : 'deppon_icons_turnleft',
						handler : function(){
							var newSm = me.getNewSerialNumberGrid().getSelectionModel();
							if (newSm.getSelection().length == 1) {
								for(var i=0;i<newSm.getSelection().length;i++){
									var newRecord = newSm.getSelection()[i];
									/*for(var j=0;j<newRecord.data.serialNumberList.length;j++){
                                        for(var i=0;i<me.getOldSerialNumberGrid().selectedRecords.length;i++){
                                            me.getOldSerialNumberGrid().removeSelectedRecord(newRecord.data.serialNumberList[j].serialNumber);
                                            //TODO 删除CSS
											var rec= me.getOldSerialNumberGrid().getStore().findRecord('serialNumber', newRecord.data.serialNumberList[j].serialNumber);
											me.getOldSerialNumberGrid().getView().removeRowCls(rec, 'disabledrow');
                                        }
                                        me.getNewSerialNumberGrid().getStore().remove(newRecord);
                                    }*/
									//var oldSerialNumItems = me.getOldSerialNumberGrid().getStore().data.items;
									//var oldLine = me.getOldSerialNumberGrid().getSelectionModel();	
									var oldSerialNumberItems = me.getOldSerialNumberGrid().getStore().data.items;
									var oldSm = me.getOldSerialNumberGrid().getSelectionModel();
									for(var i=0;i<oldSerialNumberItems.length;i++){
										if(oldSerialNumberItems[i].data.newSerialNo==newRecord.data.newSerialNo){
											oldSerialNumberItems[i].set('newSerialNo', '');
											//oldSerialNumberItems[i].set('isChecked', '0');
											//标记未合并
											oldSerialNumberItems[i].set('disRow', 'N');
											me.getOldSerialNumberGrid().getView().removeRowCls(oldSerialNumberItems[i], 'disabledrow');	
											
										}
									}
									 me.getNewSerialNumberGrid().getStore().remove(newRecord);
                                }
							} else if(newSm.getSelection().length > 1){
								//提示  只能选中一个流水号
								Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'),packaging.querypacked.i18n('foss.packaging.querypacked.serialContainer.operationSerialContainer.onlyOneHint'));
							}else {
								//提示  请选择新流水号码
								Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'),packaging.querypacked.i18n('foss.packaging.querypacked.serialContainer.operationSerialContainer.onlyNewHint'));
                            }
                        }
					}]
			});
		}
		return this.operationSerialContainer;		
	},
	/**
	 * 创建新流水号grid
	 */
	newSerialNumberGrid : null,
	getNewSerialNumberGrid : function(){
		if(this.newSerialNumberGrid==null){
			this.newSerialNumberGrid = Ext.create('Ext.grid.Panel', {
				title: packaging.querypacked.i18n('foss.packaging.querypacked.serialContainer.newSerialNumberGrid.title'),//'新流水号明细',
				//id:'FOSS_finishPacked_NewSerialNumberGrid_ID',
				itemId: 'newSerialNumber',
				cls:'checkerHide',
				store: Ext.create('Foss.FinishPacked.NewSerialNumberStore'),
				flex: 1,
				autoScroll: true,
				//hideHeaders: true,//add
				selModel: Ext.create('Ext.selection.CheckboxModel'),
				columns: [{
					xtype: 'tipcolumn',
					tipBodyElement: 'Foss.FinishPacked.ShowSerianoNoGrid',
					//配置tip的一些属性
					tipConfig: {
				        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
						maxWidth: 425,
						width: 100,
						cls:'autoHeight',
						bodyCls:'autoHeight',
				        //Tip的Body是否随鼠标移动
						trackMouse: true
					},
					dataIndex: 'newSerialNo' 
				}]
			});
		}
		return this.newSerialNumberGrid;		
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config),
			newGrid = this.getNewSerialNumberGrid(),
			oldGrid = this.getOldSerialNumberGrid()
		this.items = [ 
			oldGrid, 
			this.getOperationSerialContainer(), 
			newGrid
		];
		
		/*
		var newSerialNumItems = me.getNewSerialNumberGrid().getStore().data.items;
		var k = 0;
		for(var i=0;i<newSerialNumItems.length;i++){
			for(var j=0;j<newSerialNumItems[i].data.serialNumberList.length;j++){
				this.getOldSerialNumberGrid().selectedRecords[k] = newSerialNumItems[i].data.serialNumberList[j][0];
				var rec= this.getOldSerialNumberGrid().getStore().findRecord('serialNumber', newSerialNumItems[i].data.serialNumberList[j][0]);
				console.log(rec);
				this.getOldSerialNumberGrid().getView().addRowCls(rec, 'disabledrow');
				k++;
			}
		}
		newSerialNumItems = null;
		k =null;*/
		me.callParent([cfg]);
	}
});

/**
 * 包装录入的页面
 */
Ext.define('Foss.FinishPacked.DetailInfoPanel', {
    extend: 'Ext.panel.Panel',
    //id: 'Foss_queryPacked_finishPacked_DetailInfoPanel_id',
	title : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.title'),//'详细信息',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
	autoScroll:false,
	frame : true,
	packageForm : null,
	getPackageForm : function(){
		if(this.packageForm==null){
			this.packageForm = Ext.create('Foss.FinishPacked.PackageForm',{
				flex: 1.3
			});
		}
		return this.packageForm;
	},
	packageEmpGridEditer : null,
	getPackageEmpGridEditer : function(){
		if(this.packageEmpGridEditer==null){
			this.packageEmpGridEditer = Ext.create('Ext.grid.plugin.CellEditing', {
											//设置鼠标点击多少次，触发编辑
											clicksToEdit: 1
										});
		}
		return this.packageEmpGridEditer;
	},
	addButton : null,
	getAddButton : function(){
		var me = this;
		if(this.addButton==null){
			this.addButton = Ext.create('Ext.Button', {
				text: packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.addButton'),//'添加',
				hidden : true,
				handler: function() {
					var rec = Ext.create('Foss.FinishPacked.PackagerModel',{
						empCode: '',
						empName: ''
					}), edit = me.getPackageEmpGridEditer();
					edit.cancelEdit();
					me.getPackageEmpGrid().store.insert(0, rec);
					edit.startEditByPosition({
						row: 0,
						column: 0
					});
				}
			});
		}
		return this.addButton;
	},
	actionColumn : null,
	getActionColumn : function(){
		var me = this;
		if(this.actionColumn==null){
			this.actionColumn = Ext.create('Ext.grid.column.Action', {
				hidden: true,
				flex: 0.3,
				items: [{
					iconCls:'deppon_icons_delete',
					tooltip: packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.deleteButton'),//'删除',
					handler: function(grid, rowIndex, colIndex) {
						var rec = grid.getStore().getAt(rowIndex);
						grid.getStore().removeAt(rowIndex);
					}
				}]
			});
		}
		return this.actionColumn;
	},
	packageEmpGrid : null,
	getPackageEmpGrid : function(){
		var me = this;
		if(this.packageEmpGrid==null){
			this.packageEmpGrid = Ext.create('Ext.grid.Panel', {
				//徐多为
				title: packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.packageEmpGrid'),//'包装人员',
				//id:'FOSS_finishPacked_packageEmpGrid_ID',
				border : false,
				allowBlank: false,
				flex: 0.6,
				store: Ext.create('Foss.FinishPacked.PackagerStore'),
				autoScroll:false,
				hideHeaders: true,//add
				
				//单元格编辑插件
				plugins: [ 
					me.getPackageEmpGridEditer()
				],
				columns: [{
					flex: 1,
					dataIndex: 'empCode',
					//编辑框
					field: {
						//readOnly: true,
						xtype: 'commonemployeeselector',
						columnWidth:.25,
						name:'empCode',
						isEnterQuery : false,// 回车查询
						//minChars:1,
						/*xtype:'combo',
						labelWidth: 400,
						maxWidth:800,
						hideLabel: true,
						hideTrigger:true,
						queryParam:'queryUnpackVo.empCode',
						valueField: 'empCode',
						minChars:6,
						name:'empCode',
						//从外面传入
						store: Ext.create('Foss.FinishPacked.PackagerStore'),
						//显示名称
						displayField:'empCode',*/
						listeners: {
							beforequery:function(ueryEvent,eOpts ){/*
								var code = ueryEvent.query;
								var packageEmpItems = me.getPackageEmpGrid().getStore().data.items;
								var combo = ueryEvent.combo;
								var index = combo.up('grid').getStore().data.items;
								var selectedGird = combo.up('grid').getSelectionModel().getSelection();//获得选中的项
								var rowid = combo.up('grid').getStore().indexOf(selectedGird[0]);//获得选中的第一项在store内的行号
								index[rowid].set('empName','');
								for(var i=0;i<packageEmpItems.length;i++){
									
									if(i!=rowid && packageEmpItems[i].data.empCode == code){
										//提示，输入的员工号有重复
										Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.packageEmpItems.inputHint'));									
										return false;
									}
								}
							*/},
							select:function(combo,records,eOpts){
								var empCode = records[0].data.empCode,
									selectedGird = combo.up('grid').getSelectionModel().getSelection(),//获得选中的项
									index = combo.up('grid').getStore().data.items,
									packageEmpItems = me.getPackageEmpGrid().getStore().data.items,
									rowid = combo.up('grid').getStore().indexOf(selectedGird[0]);//获得选中的第一项在store内的行号
								for(var i=0;i<packageEmpItems.length;i++){								
									if(i!=rowid && packageEmpItems[i].data.empCode == empCode){
										index[rowid].set('empCode',null);
										index[rowid].set('empName',null);
										//提示，输入的员工号有重复
										Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.packageEmpItems.inputHint'));	
										index[rowid].set('empCode',null);
										return;
									}
								}
								var empName = records[0].data.empName;
								//var index = combo.up('grid').getStore().data.items;
								//var selectedGird = combo.up('grid').getSelectionModel().getSelection();//获得选中的项
								//var rowid = combo.up('grid').getStore().indexOf(selectedGird[0]);//获得选中的第一项在store内的行号
								index[rowid].set('empName',empName );
							}
						}    
					}
				},{
					flex: 1,
					dataIndex: 'empName',	
			        //编辑框
					field: {
						readOnly: true,
						//定义编辑框的类型，编辑框可以是表单中的所有类型
						xtype: 'textfield'				
					}
				},me.getActionColumn()],
				buttonAlign: 'left',
				buttons: [me.getAddButton()]
			});
		}
		return this.packageEmpGrid;
	},
	serialContainer : null,
	getSerialContainer : function(){
		if(this.serialContainer==null){
			this.serialContainer = Ext.create('Foss.FinishPacked.SerialContainer',{
				flex: 1
			});
		}
		return this.serialContainer;
	},
	editButton : null,
	getEditButton : function(){
		var me = this;
		if(this.editButton==null){
			this.editButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.editButton'),//'编辑',
				disabled : (packaging.querypacked.isPermission('packaging/savePackageInfoButton'))?false:true,
				hidden : (packaging.querypacked.isPermission('packaging/savePackageInfoButton'))?false:true,
				handler: function() {
					me.showEdit();
				}
			});
		}
		return this.editButton;
	},
	exitEditButton : null,
	getExitEditButton : function(){
		var me = this;
		if(this.exitEditButton==null){
			this.exitEditButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.exitEditButton'),//'取消编辑',
				hidden : true,
				handler: function() {
					me.exitEdit();
				}
			});
		}
		return this.exitEditButton;
	},
	resetButton : null,
	getResetButton : function(){
		var me = this;
		if(this.resetButton==null){
			this.resetButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.resetButton'),//'重置',
				hidden : true,
				handler: function() {
					//清空全局变量
					packaging.querypacked.oldValue='';
					if(packaging.querypacked.editStatus=='update'){
						//绑定表格数据到表单上
						//var record = (Ext.getCmp('Foss_querypackedindex_PackageGrid_Id').selModel.getSelection())[0];
						//var waybillno = record.get('wayBillNumber');
						//var waybillPackEntity = me.getPackageForm().getForm().getValues();
						var packform = me.getPackageForm(),						
							form = packform.getForm(),
							waybillno = form.getValues().wayBillNumber,
						    waybill = form.findField('wayBillNumber');
						waybill.getPackInfo(packform,waybillno,'');
						packaging.querypacked.editStatus = 'update';
					}else{
						/*var wayBillNumber = me.getPackageForm().getForm().findField('wayBillNumber').value;
						if(wayBillNumber!=null){
							var packform = me.getPackageForm(),
							    form = packform.getForm(),
							    waybill = form.findField('wayBillNumber'),
							    waybillno = waybill.value;
							waybill.getPackInfo(packform,waybillno);
						}*/
						me.getPackageForm().getForm().reset();
						me.getSerialContainer().getOldSerialNumberGrid().getStore().removeAll();
						me.getSerialContainer().getNewSerialNumberGrid().getStore().removeAll();
						me.getPackageEmpGrid().getStore().removeAll();
					}
					
				}
			});
		}
		return this.resetButton;
	},
	handlerButton : null,
	getHandlerButton : function(me,func){
		//校验包装录入权限
		if(!packaging.querypacked.isPermission('packaging/savePackageInfoButton')){
			return;
		}
		

		var isValid = true;
		var msg = "";
		/**
		 * 对包装主信息的非空校验
		 */
		if(!me.getPackageForm().getForm().isValid()){
			isValid = false;
			msg = packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.saveFormMsg');
		}

		/**
		 * 包装录入的主信息
		 */
		var waybillPackEntity = me.getPackageForm().getForm().getValues();
		if(waybillPackEntity.waybillCreateDeptName!=''
			&&waybillPackEntity.goodsName!=''){
			
		}else{
			isValid = false;
			msg = packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.saveWaybillMsg');
		}
		//console.log(waybillPackEntity);
		/**
		 * 新旧流水号关系
		 */
		var oldSerialNumItems = me.getSerialContainer().getOldSerialNumberGrid().getStore().data.items;
		var oldSerialNumArray = new Array();
		//将新旧流水号关系放入数组中
		for(var i=0;i<oldSerialNumItems.length;i++){
			oldSerialNumArray.push(oldSerialNumItems[i].data);
		}		
		/**
		 * 新流水号列表
		 */
		var newSerialNumItems = me.getSerialContainer().getNewSerialNumberGrid().getStore().data.items;
		var newSerialNumArray = new Array();
		//将新流水号放入数组中
		for(var i=0;i<newSerialNumItems.length;i++){
			newSerialNumArray.push(newSerialNumItems[i].data);
		}

        //新包装新流水号不能能为空
        //请先打包装之后在保存
        if(newSerialNumArray.length==0){
            isValid = false;
            msg = packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.savePackage') + msg;

        }
		/**
		 * 包装人员列表
		 */
		var packageEmpItems = me.getPackageEmpGrid().getStore().data.items;
		var packageEmpArray = new Array();
		for(var i=0;i<packageEmpItems.length;i++){
			if(packageEmpItems[i].data.empName!=''&&packageEmpItems[i].data.empCode!=''){
				packageEmpArray.push(packageEmpItems[i].data);
			}
		}
		if(packageEmpArray.length==0){
			isValid = false;
			msg = packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.savePackagerMsg') + msg;
			
		}
		//用于前端向后台传入，便于后台获取
		var data = {
		 'queryUnpackVo':{
			'waybillPackEntity':waybillPackEntity,
			'serialRelationList':oldSerialNumArray,
			'newSerialList':newSerialNumArray,
			'packedPersonList':packageEmpArray
		}};
		
		
		
		/**
		 * ajax
		 */
		if(isValid == true){
			
			packagingPanel = me.getPackageForm().up('panel');
			
			var loadMask = new Ext.LoadMask(packagingPanel, {
				msg:"数据正在处理，请稍后......"
			});
			loadMask.show();
			
			Ext.Ajax.request({
				//url : '../packaging/addPackageInfo.action',
				url : packaging.realPath('addPackageInfo.action'),
				jsonData: data,
				success : function(response){
					loadMask.hide();
					//Ext.MessageBox.alert('提示', '保存成功！！', this);
					Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.saveSuccess'), 'success', 5000);
					
					//如果不为空则执行
					if(func){
						func();
					}
					
				},
				failure: function(response) {   
					loadMask.hide();
                    //var respText = Ext.util.JSON.decode(resp.responseText);   
                    var result = Ext.decode(response.responseText);
                    //错误，请重试
                    Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.error'));   
                    return;
				},
                exception: function(response){
                	loadMask.hide();
                	var result = Ext.decode(response.responseText);
                	//错误
                    Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), result.message);
                    return;
                }
			});
		}else{
			//提示
			Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), msg);						
		}
	},
	saveButton : null,
	getSaveButton : function(){
		var me = this;
		if(this.saveButton==null){
			this.saveButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.saveButton'),//'保存',
				//hidden : (packaging.querypacked.isPermission('packaging/savePackageInfoButton'))?false:true,
				hidden : true,
				handler: function() {
                    var isValid = false;
                    var oldSerialNumItems = me.getSerialContainer().getOldSerialNumberGrid().getStore().data.items;
                    for(var i=0;i<oldSerialNumItems.length;i++){
                        if(oldSerialNumItems[i].data.disRow != 'Y'){
                            isValid=true;
                            break;
                        }
                    }
                    if(isValid){
                     Ext.MessageBox.confirm(packaging.querypacked.i18n('foss.packaging.querypacked.hint'),"包装没有全部完成,是否继续包装!",function(btn){
                        if(btn=='yes')    {
                            me.getHandlerButton(me,function(){
                                var wayBillNumber = me.getPackageForm().items.items[0];
                                wayBillNumber.getPackInfo(wayBillNumber.up('panel'),wayBillNumber.getValue(),waybillno,packaging.querypacked.oldValue);
                            });
                        }
                     });
                    }else{
					me.getHandlerButton(me);
                    }
                    
              	  this.setDisabled(true);//不可点击
     			 setTimeout(function(){
     				me.saveButton.setDisabled(false);//恢复可点击
     				      }, 2000);
				}
			});
		}
		return this.saveButton;
	},
	continueSaveButton : null,
	getContinueSaveButton : function(){
		var me = this;
		if(this.continueSaveButton==null){
			this.continueSaveButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.continueSaveButton'),//'继续包装',
				//hidden : (packaging.querypacked.isPermission('packaging/savePackageInfoButton'))?false:true,
				hidden : true,
				handler: function() {
							me.getHandlerButton(me,function(){
								var wayBillNumber = me.getPackageForm().items.items[0];
								wayBillNumber.getPackInfo(wayBillNumber.up('panel'),wayBillNumber.getValue(),waybillno,packaging.querypacked.oldValue);
							});
							
						}
			});
		}
			return this.continueSaveButton;
	},
	deleteButton : null,
	getDeleteButton : function(){
		var me = this;
		if(this.deleteButton==null){
			this.deleteButton = Ext.create('Ext.Button', {
				text : packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.deleteButton_del'),//'还原',
				//hidden : (packaging.querypacked.isPermission('packaging/savePackageInfoButton'))?false:true,
				hidden : true,
				handler: function() {
					var waybillPackEntity = me.getPackageForm().getForm().getValues();
					var packedMate = 'WOODEN_FRAME';
					if(waybillPackEntity.packedMate == 'MAKE_WOODEN_STOCK'){
						packedMate = 'MAKE_WOODEN_STOCK';
					}
					
					var data = {
							 'queryUnpackVo':{
								'waybillno':waybillPackEntity.wayBillNumber,
								'packageType':packedMate
							}};
					
					Ext.Ajax.request({
						//url : '../packaging/addPackageInfo.action',
						url : packaging.realPath('deleteWaybillPack.action'),
						jsonData: data,
						success : function(response){
							//Ext.MessageBox.alert('提示', '保存成功！！', this);
							Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), '还原成功!', 'success', 5000);
						},
						failure: function(response) {   
							loadMask.hide();
		                    var result = Ext.decode(response.responseText);
		                    //错误，请重试
		                    Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.error'));   
		                    return;
						},
		                exception: function(response){
		                	var result = Ext.decode(response.responseText);
		                	//错误
		                    Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), result.message);
		                    return;
		                }
					});
					
					
				}
			});
		}
		return this.deleteButton;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		this.items = [
			this.getPackageForm(),{
				border : false,
				xtype : 'container',
				margin : '0 10 0 10',
				cls: 'dp-space-body'
			},
			this.getSerialContainer(),{
				border : false,
				xtype : 'container',
				margin : '0 10 0 10',
				cls: 'dp-space-body'
			},
			this.getPackageEmpGrid()
		];
		//this.loadMarsk = this.getLoadMarsk();
		this.tbar = ['->',this.getEditButton(),this.getDeleteButton()];
		//
		if(packaging.querypacked.createWindow ==1){
			var bbar = [this.getExitEditButton(),this.getResetButton(),'->',this.getContinueSaveButton(),this.getSaveButton()];
		}else{
			var bbar = [this.getExitEditButton(),this.getResetButton(),'->',this.getSaveButton()];
		}
		this.bbar = bbar;
		//设置表单不可编辑
		setFormEditAble(this.getPackageForm(),false);
		me.callParent([cfg]);
		
	},
	showEdit: function(){
		var form = this.getPackageForm().getForm();
		this.getSerialContainer().getOldSerialNumberGrid().removeCls('checkerHide');
		this.getSerialContainer().getNewSerialNumberGrid().removeCls('checkerHide');
		setFormEditAble(this.getPackageForm(),true);
		//设置开单部门不能编辑
		form.findField('waybillCreateDeptName').setReadOnly(true);
		//设置货物名称不能编辑
		form.findField('goodsName').setReadOnly(true);
		//设置包装要求不能编辑
		form.findField('packRequire').setReadOnly(true);
		//设置货物原始体积(方)不能编辑
		form.findField('unPackedVolumeCreate').setReadOnly(true);
		//设置开单后乘以系数的体积(方)不能编辑
		form.findField('unPackedVolume').setReadOnly(true);
		//包装材料不能编辑
		//form.findField('packedMate').initField();
		form.findField('packedMate').setEditable(false);
		if(packaging.querypacked.editStatus=='update'){
			form.findField('wayBillNumber').setReadOnly(true);
		};
		/**
		 * 设置按钮、流水号容器及包装人员列表可见、可编辑
		 */
		this.getExitEditButton().setVisible(true);
		this.getResetButton().setVisible(true);
		this.getSaveButton().setVisible(true);
		this.getContinueSaveButton().setVisible(true);
		this.getSerialContainer().getOperationSerialContainer().show();
		this.getActionColumn().show();
		this.getAddButton().setVisible(true);
		//this.getEditButton().setVisible(false);
		if(this.getPackageEmpGridEditer().editors.items.length==0){
			for(var i=0;i<this.getPackageEmpGrid().columns.length;i++){
					this.getPackageEmpGridEditer().startEditByPosition({
						row: 0,
						column: i
					});
			}
		}
				this.getPackageEmpGridEditer().startEditByPosition({
															row: 0,
															column: 0
														});
		for(var i=0;i<this.getPackageEmpGridEditer().editors.items.length;i++){
		 //用户姓名不能编辑
			var editorField = this.getPackageEmpGridEditer().editors.items[i].field;
			if(editorField.name != 'empName'){
				editorField.setReadOnly(false);
			}		
		}
	},
	exitEdit: function(){
		//this.getSerialContainer().getOldSerialNumberGrid().addClass('checkerHide');
		//this.getSerialContainer().getOldSerialNumberGrid().getView().addRowCls('disabledrow');
		this.getSerialContainer().getNewSerialNumberGrid().addClass('checkerHide');
		/**
		 * 设置包装录入编辑框不能编辑，按钮不可见，流水号和包装人员列表不可更改
		 */
		setFormEditAble(this.getPackageForm(),false);
		this.getExitEditButton().setVisible(false);
		this.getResetButton().setVisible(false);
		this.getSaveButton().setVisible(false);
		this.getContinueSaveButton().setVisible(false);
		this.getSerialContainer().getOperationSerialContainer().hide();
		this.getActionColumn().hide();
		this.getAddButton().setVisible(false);
		//this.getEditButton().setVisible(true);
		if(this.getPackageEmpGridEditer().editors.items.length==0){
			for(var i=0;i<this.getPackageEmpGrid().columns.length;i++){
				this.getPackageEmpGridEditer().startEditByPosition({
													row: 0,
													column: i
												});
			}
		}
		for(var i=0;i<this.getPackageEmpGridEditer().editors.items.length;i++){
			var editorField = this.getPackageEmpGridEditer().editors.items[i].field;
			editorField.setReadOnly(true);
		}
	},
	bindData : function(record,grid,rowBodyElement){
		//绑定表格数据到表单上
		//this.getPackageForm().getForm().loadRecord(record);
		var waybillno = record.get('wayBillNumber');
		var rowNumber = record.get('rowNumber');
		var packageType = record.get('packageType');
		var packform = this.getPackageForm();
		var form = packform.getForm();
		var waybill = form.findField('wayBillNumber');
		waybill.getPackInfo(packform,waybillno,packageType);
		packaging.querypacked.editStatus = 'update';
		//packaging.querypacked.getWaybillPackInfo(waybillno);
		//this.getEditButton();
		//this.getExitEditButton();
		
		if(rowNumber > 1){
			this.getDeleteButton().setVisible(true);
			this.getEditButton().setVisible(true);
		}else{
			this.getDeleteButton().setVisible(false);
			this.getEditButton().setVisible(true);
		}
		
		this.showEdit();
		this.exitEdit();
		
	}
});