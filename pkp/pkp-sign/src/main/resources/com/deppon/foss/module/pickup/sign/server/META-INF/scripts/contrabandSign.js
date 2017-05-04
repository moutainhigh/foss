/**
 * @author foss-meiying
 * page:违禁品签收
 */
sign.contrabandSign.expType = 'CG';//异常类型 --违禁品
sign.contrabandSign.unnormalContraband='UNNORMAL_CONTRABAND';//异常违禁品

//签收情况
sign.contrabandSign.situationStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION',null,null, sign.contrabandSign.unnormalContraband);

//违禁品签收信息
Ext.define('Foss.sign.contrabandSign.model.SignInfoModel', {  
	extend : 'Ext.data.Model',
	fields : [
	    {name : 'waybillNo'},/* 运单号*/
	    {name : 'productCode'},/* 运输性质*/
	    {name: 'generateGoodsQty'},// 生成件数
		{name : 'signGoodsQty',type:'int'},/*签收件数  默认为运单在当前登录人所在部门的库存件数*/
	    {name : 'stockGoodsQty',type:'int'},/*签收件数  默认为运单在当前登录人所在部门的库存件数*/
	    {name : 'signTime',type:'date',convert: dateConvert} //获取服务器上当前时间用作签收时间
	]
});
//违禁品签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.contrabandSign.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	fields: ['serialNo'],
	//是否自动查询
	autoLoad: false,
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'deliverbillDetailVo.signDetailEntitys'
        }
	}
	
});
//签收流水号GridPanel
Ext.define('Foss.sign.contrabandSign.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.contrabandSign.i18n('pkp.sign.contrabandSign.emptyText'),//查询结果为空
	columnLines: true,
	height: 293,//高度
	id: 'Foss_sign_contrabandSign_SerialNoOutStorageGridPanel_ID',
	frame: true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    title:sign.contrabandSign.i18n('pkp.sign.contrabandSign.signPieces'),//签收件
	columns: [//流水号
        {header: sign.contrabandSign.i18n('pkp.sign.contrabandSign.serialNo'), dataIndex: 'serialNo', flex: 1,align: 'center' }//流水号
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.contrabandSign.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});
//签收信息
Ext.define('Foss.sign.contrabandSign.ContrabandSignForm',{
	extend: 'Ext.form.Panel',
	title:sign.contrabandSign.i18n('pkp.sign.contrabandSign.signInformation'),//签收信息
	id:'Foss_sign_contrabandSign_SignInfoForm_Id',
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	height:410,
	layout:'hbox',
	items:[{
		xtype: 'form',
		defaults: {
			labelWidth:90,
			padding: '15 15 15 15'
		},
		defaultType : 'textfield',
		frame:true,
		title:sign.contrabandSign.i18n('pkp.sign.contrabandSign.signInformation'),//签收信息
		layout:'column',
		height: 293,//高度
		flex: 7,
		items:[
		{
			xtype: 'combobox',
			name:'situation',
			fieldLabel:sign.contrabandSign.i18n('pkp.sign.contrabandSign.situation'),//签收情况
			allowBlank: false,//不允许为空
			valueField:'valueCode', 
			columnWidth:.43,
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			value:sign.contrabandSign.unnormalContraband,//异常-违禁品
			readOnly : true,
			store:sign.contrabandSign.situationStore
		},{
			xtype: 'datetimefield_date97',
			format : 'Y-m-d H:i:s',
			id: 'contrabandSign-QueryPanel-signTimeEnd',
			fieldLabel: sign.contrabandSign.i18n('pkp.sign.contrabandSign.signTime'),//签收时间
			allowBlank:false,
			columnWidth:.43,
			editable:false,
//			value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			name: 'signTime',
			time : true,
			dateConfig: {
				el : 'contrabandSign-QueryPanel-signTimeEnd-inputEl',
				minDate: '%y-%M-%d 00:00:00',
				maxDate:'%y-%M-%d 23:59:59'
			}
		},{
			name:'stockGoodsQty',
			fieldLabel:sign.contrabandSign.i18n('pkp.sign.contrabandSign.stockGoodsQty'),//库存件数
			xtype:'numberfield',
			hideTrigger: true,
			readOnly:true,
			columnWidth:.43,
			allowDecimals : false//不允许有小数点
		},{
			name:'signGoodsQty',
			fieldLabel:sign.contrabandSign.i18n('pkp.sign.contrabandSign.signGoodsQty'),//签收件数
			xtype:'numberfield',
			hideTrigger: true,
			minValue: 1,
			columnWidth:.43,
			allowBlank: false,//不允许为空
			allowDecimals : false//不允许有小数点
		},{
			name:'signNote',
			maxLength:200,
			fieldLabel:sign.contrabandSign.i18n('pkp.sign.contrabandSign.signNote'),//备注
			columnWidth:.50
		}]
	},Ext.create('Foss.sign.contrabandSign.SerialNoOutStorageGridPanel',{
		flex: 3.0
	})],
	checkButtons  : null,
	getCheckButtons: function(){
		if(this.checkButtons  == null){
			this.checkButtons = Ext.create('Ext.button.Button',{
				text:sign.contrabandSign.i18n('pkp.sign.contrabandSign.submit'),// 提交 
				disabled:!sign.contrabandSign.isPermission('contrabandsignindex/contrabandsignindexsignbutton'),
				hidden:!sign.contrabandSign.isPermission('contrabandsignindex/contrabandsignindexsignbutton'),
				width:'100',
				id:'Foss_sign_contrabandSign_ButtonSignCheck_Id',
				disabled:true,
				style : {
					float : 'center'
				},
				cls:'yellow_button',
				handler:function(){
					var baseForm  = this.up('form');
						form = baseForm.getForm();
					if(!form.isValid()){//签收信息未填写完整!
						Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.notcompleteInfo'),'error',2000);//签收信息未填写完整!
						return;
					}else if(form.findField('signGoodsQty').getValue() == 0){
						Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.signGoodsQtyGreateThanZero'),'error',2000);//签收件数必须大于0
						return;
					}else if(form.findField('stockGoodsQty').getValue() < form.findField('signGoodsQty').getValue()){
						Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.stockGoodsQtyLess'),'error',2000);//签收件数不能大于库存件数
						return;
					}
					var serialNoPanel = Ext.getCmp('Foss_sign_contrabandSign_SerialNoOutStorageGridPanel_ID'),
					  serialNorowObjs = serialNoPanel.getSelectionModel().getSelection();//得到选中的流水号信息
					if(serialNorowObjs.length <= 0 ){//请选择流水号!
						Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.selectSerialNo'),'error',2000);
						return;
					}else if(form.findField('signGoodsQty').getValue() != serialNorowObjs.length){//选择流水号数量必须与签收件数一致，请确认！
						Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.serialNoSignGoodsQtySame'),'error',3000);
						return;
					}
					 Ext.Msg.confirm( sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'), sign.contrabandSign.i18n('pkp.sign.contrabandSign.submitCheck'), function(btn,text){//确定提交信息？
						if(btn == 'yes'){
							baseForm.checkButtons.setDisabled(true);
							var serialNos = new Array();
							for (var i = 0; i < serialNorowObjs.length; i++) {//把前台选中的流水号放入数组里
								serialNos.push({
									'serialNo':serialNorowObjs[i].get("serialNo")//流水号
								});
							}
							var formModel  = form.getRecord();
							Ext.Ajax.request({
								url:sign.realPath('addContrabandInfo.action'),
								method: 'POST',
								jsonData: {
									'vo':{
										'contrabandInfoDto':{
											'signDetailList':serialNos, //流水号信息集合
											'waybillNo':formModel.data.waybillNo,//运单号
											'signNote':form.findField('signNote').getValue(),//备注
											'signTime':Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true),//签收时间
											'signGoodsQty': form.findField('signGoodsQty').getValue(),//签收件数
											'productCode':formModel.data.productCode,//运输性质
											'generateGoodsQty':formModel.data.generateGoodsQty,//生成件数 
											'expType':sign.contrabandSign.expType,//异常类型 
											'situation':form.findField('situation').getValue()//签收情况
										
										}
									}
								},
								success: function(response){
									var json = Ext.decode(response.responseText);
									serialNoPanel.getSelectionModel().deselectAll();
									serialNoPanel.getStore().removeAll();
									form.reset();
									baseForm.checkButtons.setDisabled(true);//提交按钮不可
									Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.submitSuccess'),'ok',2000);
								},exception: function(response){//异常处理
									var json = Ext.decode(response.responseText);
									baseForm.checkButtons.setDisabled(false);//提交按钮可编辑
			              			Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'), json.message, 'error', 3000);
								}
							});
						}
					});
				}
			});
		}
		return  this.checkButtons ;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.buttons = [me.getCheckButtons()];
		me.callParent([cfg]);
	}
});
//查询条件
Ext.define('Foss.sign.contrabandSign.QueryPanel', {
		extend:'Ext.form.Panel',
		id:'Foss_sign_contrabandSign_QueryPanel_Id',
		// 指定容器的标题
		title: sign.contrabandSign.i18n('pkp.sign.contrabandSign.queryBuilder'),//查询条件
		frame:true,
		//收缩
		collapsible: true,
		//动画收缩
		animCollapse: true, 
		bodyCls: 'autoHeight',
		cls: 'autoHeight',
		//默认边距 间隔
		defaults: {
			margin: '5 10 5 10',  //四边距  上右下左
			labelWidth: 95
		},	
		layout: 'column',
		defaultType: 'textfield',
		// 定义容器中的项
		items: [{
			name: 'waybillNo',
			fieldLabel: sign.contrabandSign.i18n('pkp.sign.contrabandSign.waybillNo'),//运单号
			align:'center',
			vtype: 'waybill',
			allowBlank:false,
			columnWidth:.33
		},{
			xtype:'button',
			margin: '5 10 5 30',  //四边距  上右下左
			text:sign.contrabandSign.i18n('pkp.sign.contrabandSign.search'),//查询
			disabled:!sign.contrabandSign.isPermission('contrabandsignindex/contrabandsignindexquerybutton'),
			hidden:!sign.contrabandSign.isPermission('contrabandsignindex/contrabandsignindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.11,
			handler:function(){
				var form = this.up('form').getForm();
				if(form.isValid())
				{
					var serialNosGrid = Ext.getCmp('Foss_sign_contrabandSign_SerialNoOutStorageGridPanel_ID'),
					     signInfoForm = Ext.getCmp('Foss_sign_contrabandSign_SignInfoForm_Id').getForm(),
					      checkButton = Ext.getCmp('Foss_sign_contrabandSign_ButtonSignCheck_Id');
						signInfoForm.reset();
					Ext.Ajax.request({
						url:sign.realPath('queryContrabandInfoByWaybillNo.action'),
						method: 'POST',
						jsonData: {
							'vo':{
								'waybillNo':form.findField('waybillNo').getValue() //运单号
							}
						},
						success: function(response){
							serialNosGrid.getStore().removeAll();
							var json = Ext.decode(response.responseText);
							//得到违禁品签收信息
							var signlModel = Ext.ModelManager.create(json.vo.contrabandInfoDto,
									'Foss.sign.contrabandSign.model.SignInfoModel');
							signInfoForm.loadRecord(signlModel);
							signInfoForm.findField('signTime').setValue(Ext.Date.format(signlModel.get('signTime'),'Y-m-d H:i:s'));
							//提交按钮可用
							checkButton.setDisabled(false);
							serialNosGrid.store.loadData(json.vo.contrabandInfoDto.signDetailList);
						},exception: function(response){//异常处理
							serialNosGrid.getStore().removeAll();
							var json = Ext.decode(response.responseText);
							//提交按钮不可用
							checkButton.setDisabled(true);
	              			Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'), json.message, 'error', 3000);
						}
					});
				}
				else
				{
					Ext.ux.Toast.msg(sign.contrabandSign.i18n('pkp.sign.contrabandSign.tip'),sign.contrabandSign.i18n('pkp.sign.contrabandSign.queryBuilderFail'),'error',2000);//查询条件有误
					return;
				}	
			}
		}]
	});
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Foss.sign.contrabandSign.QueryPanel'); 
	var contrabandSignPanel = Ext.create('Foss.sign.contrabandSign.ContrabandSignForm'); 
	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-contrabandSignIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto', 
		getQueryPanel: function(){
			return queryPanel;
		},
		items: [queryPanel,contrabandSignPanel,{
		
		}],
		renderTo: 'T_sign-contrabandSignIndex-body'
	});
});
