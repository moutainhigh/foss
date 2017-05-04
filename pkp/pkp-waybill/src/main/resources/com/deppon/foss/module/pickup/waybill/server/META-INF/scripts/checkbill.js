waybill.checkbill.num=null;
waybill.checkbill.counts=null;
waybill.checkbill.imagefile=null;
winImgageBillForm = null;
winCheckBillForm = null;
waybill.checkbill.x=0;
waybill.checkbill.z=0;
//正确信息提示
waybill.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',//title:waybill.util.i18n('i18n.waybill-util.fossAlert'),
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
//错误
waybill.showErrorMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
		title:'FOSS提醒您:',//title:waybill.util.i18n('i18n.waybill-util.fossAlert'),
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.ERROR,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
//ajax请求
waybill.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
waybill.checkbill.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

waybill.checkbill.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};
// 创建一个待办事项枚举store
Ext.define('Foss.ToDo.Store.ToDoTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	// 定义一个代理对象
	proxy: {
		// 代理的类型为内存代理
		type: 'memory',
		// 定义一个读取器
		reader: {
			// 以JSON的方式读取
			type: 'json',
			// 定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
// 查询条件
Ext.define('Foss.Supple.Form.checkbillForm',{
	extend:'Ext.form.Panel',
	title: waybill.checkbill.i18n('pkp.waybill.todoAction.queryCondition'),// 查询条件,
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 60
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',
		fieldLabel:waybill.checkbill.i18n('pkp.waybill.todoAction.waybillNo'),// 运单号,
		vtype: 'waybill',
		minLength:8,
		maxLength:60,
		columnWidth: 0.2
	},{
		name:'operator',
		fieldLabel:waybill.checkbill.i18n('pkp.waybill.todoAction.waybillperson'),// 开单人,
		columnWidth: 0.2
	},{
		name:'belongOrgCode',
		labelWidth: 30,
		xtype : 'dynamicorgcombselector',
		fieldLabel:waybill.checkbill.i18n('pkp.waybill.todoAction.createOrgName'),   // 开单部门
		labelWidth: 80,
		columnWidth: 0.3,
		allowBlank : false,
	},{
		xtype: 'rangeDateField',
		fieldLabel: waybill.checkbill.i18n('waybill.waybillToSupple.billTime'),// 开单时间,
		dateType: 'datetimefield_date97',
		fieldId: 'FOSS_checkbill_Id',
		fromName: 'createTimeStart',
		toName: 'createTimeEnd',
		disallowBlank:true,
		editable:false,
		fromValue: Ext.Date.format(waybill.checkbill.getTargetDate(new Date(),-1),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(waybill.checkbill.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .44,
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		name: 'checkbillstatus',
		value:'',
		fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus'),// 审单状态,
		columnWidth: 0.2,
		editable : false,
		store:Ext.create('Foss.ToDo.Store.ToDoTypeStore',{
		  data:{
	       'items':[
	            {'code':'','name':waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.all')},
				{'code':'E','name':waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.notrial')},
				{'code':'Y','name':waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.normalex')},
				{'code':'N','name':waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.Abnormalex')}
		   ]}
		})
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:waybill.checkbill.i18n('pkp.waybill.todoAction.reset'),// 重置,
			columnWidth:.08,
			handler:function(){
				waybill.checkbill.checkbillForm.getForm().reset();
				var form = this.up('form').getForm();
				form.findField('waybillNo').setValue("");
				form.findField('operator').setValue("");
				form.findField('belongOrgCode').setValue(FossUserContext.getCurrentDept().code);
				form.findField('belongOrgCode').getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				form.findField('belongOrgCode').addCls('readonlyhaveborder');
				form.findField('createTimeEnd').setValue(Ext.Date.format(waybill.checkbill.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
				form.findField('createTimeStart').setValue(Ext.Date.format(waybill.checkbill.getTargetDate(new Date(),-1),'Y-m-d H:i:s'));
				form.findField('checkbillstatus').setValue("");
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:waybill.checkbill.i18n('pkp.waybill.todoAction.query'),// 查询,
			disabled:!waybill.checkbill.isPermission('checkbillIndex/checkbillIndexquerybutton'),
			hidden:!waybill.checkbill.isPermission('checkbillIndex/checkbillIndexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				// 查询起止时间不能大于2
				var beginBillTime = form.findField('createTimeStart').getValue();
				var endBillTime = form.findField('createTimeEnd').getValue();
				var result = Ext.Date.parse(endBillTime,'Y-m-d H:i:s') - Ext.Date.parse(beginBillTime,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >2){
					Ext.ux.Toast.msg(waybill.checkbill.i18n('pkp.waybill.todoAction.tip'), waybill.checkbill.i18n('foss.pkp.waybill.waybillManagerService.exception.overthwoDays'), 'error', 3000);
					return;
				}
				if(form.isValid()){
					var grid = Ext.getCmp('T_waybill-checkbill_content').getcheckbillGrid();
		        	grid.getPagingToolbar().moveFirst();
				}
			}
		}]
	}]
});

// Foss.waybill.checkBillWindow
Ext.define('Foss.waybill.checkBillWindow',{
	extend : 'Ext.window.Window',
	title: waybill.checkbill.i18n('pkp.waybill.checkbill.picturechecbill'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'close',
	parent:null,
	//width :1200,
	//height :500,
	grid:null,
	//x:0,
	//y:100,
//	checkBillForm:null,
    getcheckBillForm:function(){
    	if(Ext.isEmpty(this.checkBillForm)){
    		winCheckBillForm = Ext.create('Foss.waybill.checkBill.AddcheckBillForm');
    	}
    	return winCheckBillForm;
    },
    imgageBillForm:null,
    getimageBillForm:function(){
    	if(Ext.isEmpty(this.imgageBillForm)){
    		this.imgageBillForm = Ext.create('Foss.waybill.checkBill.AddimageBillForm');
    	}
    	return this.imgageBillForm;
    },
    constructor : function(config) {
    	waybill.checkbill.x++;
    	var currutrow = 0;
    	var currutrowmsg ='';
    	var rowidx = waybill.checkbill.num;
    	var rowcounts = waybill.checkbill.counts;
    	var grid=Ext.getCmp('checkbillid').getStore().getAt(rowidx);
		var me = this; 
		me.width = document.documentElement.clientWidth;
		me.height = document.documentElement.clientHeight;
		cfg = Ext.apply({}, config);
		me.items = [me.getimageBillForm(),me.getcheckBillForm()];//设置window的元素
		me.callParent([cfg]);
		
		winCheckBillForm.getForm().reset();
		winCheckBillForm.getForm().loadRecord(grid);
		var form =winCheckBillForm.getForm()
		
		var deliveryCustomerMobilephone= grid.get('deliveryCustomerMobilephone');
		var deliveryCustomerPhone = grid.get('deliveryCustomerPhone');
		var receiveCustomerMobilephone = grid.get('receiveCustomerMobilephone');
		var receiveCustomerphone = grid.get('receiveCustomerPhone');
		form.findField('productCode').setFieldStyle('color:blue');
		form.findField('receiveMethod').setFieldStyle('color:blue');
		form.findField('insuranceAmount').setFieldStyle('color:red');
		form.findField('orderPaidMethod').setFieldStyle('color:red');
		form.findField('codAmount').setValue(grid.get('codAmount')/100).setFieldStyle('color:red');
		form.findField('packageFee').setValue(grid.get('packageFee')/100);
		if(deliveryCustomerMobilephone!=''&&deliveryCustomerPhone!=''){
			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone+'/'+deliveryCustomerPhone);
		}else if(deliveryCustomerMobilephone==''){
			form.findField('deliveryphone').setValue(deliveryCustomerPhone);
		}else if(deliveryCustomerPhone==''){
			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone);
		}
		if(receiveCustomerMobilephone!=''&&receiveCustomerphone!=''){
			form.findField('receivephone').setValue(receiveCustomerMobilephone+'/'+receiveCustomerphone);
		}else if(receiveCustomerMobilephone==''){
			form.findField('receivephone').setValue(receiveCustomerphone);
		}else if(receiveCustomerphone==''){
			form.findField('receivephone').setValue(receiveCustomerMobilephone);
		}
		form.findField('strpaperNum').setValue(grid.get('strpaperNum'));
		var productCode = grid.get('productCode');
		if(productCode=='AF'){
			form.findField('productCode').setValue('精准空运');
    	}else if(productCode=='FLF'){
    		form.findField('productCode').setValue('精准卡航');
    	}else if(productCode=='FSF'){
    		form.findField('productCode').setValue('精准城运');
    	}else if(productCode=='LRF'){
    		form.findField('productCode').setValue('精准汽运(长途)');
    	}else if(productCode=='SRF'){
    		form.findField('productCode').setValue('精准汽运(短途)');
    	}else if(productCode=='AIR_FREIGHT'){
    		form.findField('productCode').setValue('空运');
    	}else if(productCode=='PLF'){
    		form.findField('productCode').setValue('汽运偏线');
    	}else if(productCode=='TRANS_VEHICLE'){
    		form.findField('productCode').setValue('汽运');
    	}
		var receiveMethod = grid.get('receiveMethod');
		if(receiveMethod=='DELIVER_NOUP'){
			form.findField('receiveMethod').setValue('送货(不含上楼)');
    	}else if(receiveMethod=='DELIVER_FLOOR'){
    		form.findField('receiveMethod').setValue('送货上楼安装(家居)');
    	}else if(receiveMethod=='SELF_PICKUP'){
    		form.findField('receiveMethod').setValue('自提');
    	}else if(receiveMethod=='DELIVER'){
    		form.findField('receiveMethod').setValue('免费送货');
    	}else if(receiveMethod=='DELIVER_INGA'){
    		form.findField('receiveMethod').setValue('送货进仓');
    	}else if(receiveMethod=='DELIVER_UP'){
    		form.findField('receiveMethod').setValue('送货上楼');
    	}else if(receiveMethod=='INNER_PICKUP'){
    		form.findField('receiveMethod').setValue('内部带货自提');
    	}else if(receiveMethod=='LARGE_DELIVER_UP'){
    		form.findField('receiveMethod').setValue('大件上楼');
    	}else if(receiveMethod=='SELF_PICKUP_FREE_AIR'){
    		form.findField('receiveMethod').setValue('空运免费自提');
    	}else if(receiveMethod=='SELF_PICKUP_AIR'){
    		form.findField('receiveMethod').setValue('空运自提(不含机场提货费)');
    	}else if(receiveMethod=='DELIVER_AIR'){
    		form.findField('receiveMethod').setValue('空运免费送货');
    	}else if(receiveMethod=='AIRPORT_PICKUP'){
    		form.findField('receiveMethod').setValue('空运机场自提');
    	}else if(receiveMethod=='DELIVER_UP_AIR'){
    		form.findField('receiveMethod').setValue('空运送货上楼');
    	}else if(receiveMethod=='DELIVER_INGA_AIR'){
    		form.findField('receiveMethod').setValue('空运送货进仓');
    	}
		var returnBillType = grid.get('returnBillType');
		if(returnBillType=='NONE'){
			form.findField('returnBillType').setValue('无需反单');
    	}else if(returnBillType=='ORIGINAL'){
    		form.findField('returnBillType').setValue('原件签收单返回');
    	}else if(returnBillType=='FAX'){
    		form.findField('returnBillType').setValue('传真件签收单返回');
    	}else if(returnBillType=='SCANNED'){
    		form.findField('returnBillType').setValue('扫描件返回');
    	}else if(returnBillType=='ARRIVESHEET_FAX'){
    		form.findField('returnBillType').setValue('传真返回');
    	}
		var ordermethod = grid.get('orderPaidMethod');
		if(ordermethod=='CD'){
			form.findField('orderPaidMethod').setValue('银行卡');
		}else if(ordermethod=='CH'){
			form.findField('orderPaidMethod').setValue('现金');
		}else if(ordermethod=='OL'){
			form.findField('orderPaidMethod').setValue('网上支付');
		}else if(ordermethod=='CT'){
			form.findField('orderPaidMethod').setValue('月结');
		}else if(ordermethod=='FC'){
			form.findField('orderPaidMethod').setValue('到付');
		}else if(ordermethod=='DT'){
			form.findField('orderPaidMethod').setValue('临时欠款');
		}else if(ordermethod=='TT'){
			form.findField('orderPaidMethod').setValue('电汇');
		}else if(ordermethod=='NT'){
			form.findField('orderPaidMethod').setValue('支票');
		}
		Ext.getCmp('waybillimage'+waybill.checkbill.x).setSrc(waybill.checkbill.imagefile+''+grid.get('filePath'));
		//document.getElementById('waybillimage'+waybill.checkbill.x).width=500;
  		//document.getElementById('waybillimage'+waybill.checkbill.x).height=500;
		Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(800);
		Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(800);
		waybill.checkbill.z=0;
		
	}
});

Ext.define('Foss.waybill.checkBill.AddBtnBillPanel', {
	extend : 'Ext.panel.Panel',
	title : waybill.checkbill.i18n('审单'),//图片运单
	frame: true,
    defaults : {
    	margin : '10 0 0 0',
    },
    layout:{
    	type: 'column',
        columns: 1,
	},
    constructor : function(config) {
		var me = this;
		var cWith = document.documentElement.clientWidth*0.2*0.8;
		var cpWith = cWith*0.6;
		me.width = cWith;
		me.height = (document.documentElement.clientHeight)*0.53;
		
    	var currutrow = 0;
    	var currutrowmsg ='';
    	var rowidx = waybill.checkbill.num;
    	var rowcounts = waybill.checkbill.counts;
    	var grid=Ext.getCmp('checkbillid').getStore().getAt(rowidx);
		
		cfg = Ext.apply({}, config);
		me.items = [//me.getimagebox()
		             {
		            	 text : waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.Abnormalex'),  //取消
		            	 xtype:'button',
//		            	 id   : 'btnPanelCancle',
		     			 width:cpWith,
		     			 style:'left:30px;top:100px;',

		     			 handler :function(){
		     				if(currutrow-1<rowcounts){
		     					var params = {'vo':{'waybillPictureDto':
		     	                   {'id':grid.get('id'),
		     		                'checkbillstatus':'N'}}
		     	                    };
		     	               //ajax请求
		     	              var url = waybill.realPath('upcheckbillstatus.action');			
		     	               //成功提示
		     	              var successFun = function(json){
		     	              waybill.showInfoMes(json.message);
		     	              grid.set('checkbillstatus','N');
		     	              if(currutrow==rowidx){
		     	            	  currutrow++;
		     	              }
		     	              var currutidx = 0;
		                 	  if(currutrow>=rowcounts&&rowcounts-1!=rowidx){
		                 		  currutidx = rowcounts-1;
		                 	  }else if(currutrow>=rowcounts&&rowcounts-1==rowidx){
		                 		  currutidx = rowcounts-2;
		                 	  }else{
		                 		  currutidx = currutrow;
		                 	  }
		       	             grid =Ext.getCmp('checkbillid').getStore().getAt(currutidx); ;
		       	          	 winCheckBillForm.getForm().reset();
		       	          	 winCheckBillForm.getForm().loadRecord(grid);
		     	      		 var form =winCheckBillForm.getForm();
		     	      		 var deliveryCustomerMobilephone= grid.get('deliveryCustomerMobilephone');
		     	      		 var deliveryCustomerPhone = grid.get('deliveryCustomerPhone');
		     	      		 var receiveCustomerMobilephone = grid.get('receiveCustomerMobilephone');
		     	      		 var receiveCustomerphone = grid.get('receiveCustomerPhone');
		     	      		form.findField('codAmount').setValue(grid.get('codAmount')/100);
		     	      		form.findField('packageFee').setValue(grid.get('packageFee')/100);
		     	      		 if(deliveryCustomerMobilephone!=''&&deliveryCustomerPhone!=''){
		     	      			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone+'/'+deliveryCustomerPhone);
		     	      		 }else if(deliveryCustomerMobilephone==''){
		     	      			form.findField('deliveryphone').setValue(deliveryCustomerPhone);
		     	      		}else if(deliveryCustomerPhone==''){
		     	      			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone);
		     	      		}
		     	      		if(receiveCustomerMobilephone!=''&&receiveCustomerphone!=''){
		     	      			form.findField('receivephone').setValue(receiveCustomerMobilephone+'/'+receiveCustomerphone);
		     	      		}else if(receiveCustomerMobilephone==''){
		     	      			form.findField('receivephone').setValue(receiveCustomerphone);
		     	      		}else if(receiveCustomerphone==''){
		     	      			form.findField('receivephone').setValue(receiveCustomerMobilephone);
		     	      		}
		     	      		form.findField('strpaperNum').setValue(grid.get('strpaperNum'));
		     	      		var productCode = grid.get('productCode');
		     	      		if(productCode=='AF'){
		     	      			form.findField('productCode').setValue('精准空运');
		     	          	}else if(productCode=='FLF'){
		     	          		form.findField('productCode').setValue('精准卡航');
		     	          	}else if(productCode=='FSF'){
		     	          		form.findField('productCode').setValue('精准城运');
		     	          	}else if(productCode=='LRF'){
		     	          		form.findField('productCode').setValue('精准汽运(长途)');
		     	          	}else if(productCode=='SRF'){
		     	          		form.findField('productCode').setValue('精准汽运(短途)');
		     	          	}else if(productCode=='AIR_FREIGHT'){
		     	          		form.findField('productCode').setValue('空运');
		     	          	}else if(productCode=='PLF'){
		     	          		form.findField('productCode').setValue('汽运偏线');
		     	          	}else if(productCode=='TRANS_VEHICLE'){
		     	          		form.findField('productCode').setValue('汽运');
		     	          	}
		     	      		var receiveMethod = grid.get('receiveMethod');
		     	      		if(receiveMethod=='DELIVER_NOUP'){
		     	    			form.findField('receiveMethod').setValue('送货(不含上楼)');
		     	        	}else if(receiveMethod=='DELIVER_FLOOR'){
		     	        		form.findField('receiveMethod').setValue('送货上楼安装(家居)');
		     	        	}else if(receiveMethod=='SELF_PICKUP'){
		     	        		form.findField('receiveMethod').setValue('自提');
		     	        	}else if(receiveMethod=='DELIVER'){
		     	        		form.findField('receiveMethod').setValue('免费送货');
		     	        	}else if(receiveMethod=='DELIVER_INGA'){
		     	        		form.findField('receiveMethod').setValue('送货进仓');
		     	        	}else if(receiveMethod=='DELIVER_UP'){
		     	        		form.findField('receiveMethod').setValue('送货上楼');
		     	        	}else if(receiveMethod=='INNER_PICKUP'){
		     	        		form.findField('receiveMethod').setValue('内部带货自提');
		     	        	}else if(receiveMethod=='LARGE_DELIVER_UP'){
		     	        		form.findField('receiveMethod').setValue('大件上楼');
		     	        	}else if(receiveMethod=='SELF_PICKUP_FREE_AIR'){
		     	        		form.findField('receiveMethod').setValue('空运免费自提');
		     	        	}else if(receiveMethod=='SELF_PICKUP_AIR'){
		     	        		form.findField('receiveMethod').setValue('空运自提(不含机场提货费)');
		     	        	}else if(receiveMethod=='DELIVER_AIR'){
		     	        		form.findField('receiveMethod').setValue('空运免费送货');
		     	        	}else if(receiveMethod=='AIRPORT_PICKUP'){
		     	        		form.findField('receiveMethod').setValue('空运机场自提');
		     	        	}else if(receiveMethod=='DELIVER_UP_AIR'){
		     	        		form.findField('receiveMethod').setValue('空运送货上楼');
		     	        	}else if(receiveMethod=='DELIVER_INGA_AIR'){
		     	        		form.findField('receiveMethod').setValue('空运送货进仓');
		     	        	}
		     	      		var returnBillType = grid.get('returnBillType');
		     	      		if(returnBillType=='NONE'){
		     	      			form.findField('returnBillType').setValue('无需反单');
		     	          	}else if(returnBillType=='ORIGINAL'){
		     	          		form.findField('returnBillType').setValue('原件签收单返回');
		     	          	}else if(returnBillType=='FAX'){
		     	          		form.findField('returnBillType').setValue('传真件签收单返回');
		     	          	}else if(returnBillType=='SCANNED'){
		     	          		form.findField('returnBillType').setValue('扫描件返回');
		     	          	}else if(returnBillType=='ARRIVESHEET_FAX'){
		     	          		form.findField('returnBillType').setValue('传真返回');
		     	          	}
		     	      		var ordermethod = grid.get('orderPaidMethod');
		     	      		if(ordermethod=='CD'){
		     	    			form.findField('orderPaidMethod').setValue('银行卡');
		     	    		}else if(ordermethod=='CH'){
		     	    			form.findField('orderPaidMethod').setValue('现金');
		     	    		}else if(ordermethod=='OL'){
		     	    			form.findField('orderPaidMethod').setValue('网上支付');
		     	    		}else if(ordermethod=='CT'){
		     	    			form.findField('orderPaidMethod').setValue('月结');
		     	    		}else if(ordermethod=='FC'){
		     	    			form.findField('orderPaidMethod').setValue('到付');
		     	    		}else if(ordermethod=='DT'){
		     	    			form.findField('orderPaidMethod').setValue('临时欠款');
		     	    		}else if(ordermethod=='TT'){
		     	    			form.findField('orderPaidMethod').setValue('电汇');
		     	    		}else if(ordermethod=='NT'){
		     	    			form.findField('orderPaidMethod').setValue('支票');
		     	    		}
		     	      		Ext.getCmp('waybillimage'+waybill.checkbill.x).setSrc(waybill.checkbill.imagefile+''+grid.get('filePath'));
		     	      		//document.getElementById('waybillimage'+waybill.checkbill.x).width=500;
		     	      		//document.getElementById('waybillimage'+waybill.checkbill.x).height=300;
		     	      		document.getElementById('waybillimage'+waybill.checkbill.x).style.webkitTransform = "rotateZ(0deg)";
		     	      		Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(800);
		     	    		Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(800);
		     	      		currutrow++;
		     	      		if(currutrow==rowidx){
		     	      	     currutrow++;
		     	      		}
		                   };
		                        //失败提示
		                        var failureFun = function(json){
		                     	waybill.showErrorMes(json.message);
		                      };
		                     //调用ajax请求
		                       waybill.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
		     				}else{
		     					waybill.showInfoMes("已是最后一条运单！");
		     				}
		     			}
		     		
		     			 
		     			 
		             },{
		            		text : waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus.item.normalex'),//提交
		            		xtype:'button',
//		            		id   : 'btnPanelSubmit',
		            		width:cpWith,
		        			style:'left:30px;top:140px;',
		        			
		        			handler :function(){
		        				if(currutrow-1<rowcounts){
		        				var params = {'vo':{'waybillPictureDto':
		                             {'id':grid.get('id'),
		        	                'checkbillstatus':'Y'}}
		                       };
		                       // ajax请求
		                      var url = waybill.realPath('upcheckbillstatus.action');			
		                      // 成功提示
		                      var successFun = function(json){
		                    	  waybill.showInfoMes(json.message);
		                    	  grid.set('checkbillstatus','Y');
		                    	  if(currutrow==rowidx){
		        	            	  currutrow++;
		        	              }
		                    	  var currutidx = 0;
		                    	  if(currutrow>=rowcounts&&rowcounts-1!=rowidx){
		                    		  currutidx = rowcounts-1;
		                    	  }else if(currutrow>=rowcounts&&rowcounts-1==rowidx){
		                    		  currutidx = rowcounts-2;
		                    	  }else{
		                    		  currutidx = currutrow;
		                    	  }
		          	             grid =Ext.getCmp('checkbillid').getStore().getAt(currutidx);
		          	             winCheckBillForm.getForm().reset();
			       	          	 winCheckBillForm.getForm().loadRecord(grid);
			     	      		 var form =winCheckBillForm.getForm();
		        	      		 var deliveryCustomerMobilephone= grid.get('deliveryCustomerMobilephone');
		        	      		 var deliveryCustomerPhone = grid.get('deliveryCustomerPhone');
		        	      		 var receiveCustomerMobilephone = grid.get('receiveCustomerMobilephone');
		        	      		 var receiveCustomerphone = grid.get('receiveCustomerPhone');
		        	      		form.findField('codAmount').setValue(grid.get('codAmount')/100);
		        	      		form.findField('packageFee').setValue(grid.get('packageFee')/100);
		        	      		 if(deliveryCustomerMobilephone!=''&&deliveryCustomerPhone!=''){
		        	      			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone+'/'+deliveryCustomerPhone);
		        	      		 }else if(deliveryCustomerMobilephone==''){
		        	      			form.findField('deliveryphone').setValue(deliveryCustomerPhone);
		        	      		}else if(deliveryCustomerPhone==''){
		        	      			form.findField('deliveryphone').setValue(deliveryCustomerMobilephone);
		        	      		}
		        	      		if(receiveCustomerMobilephone!=''&&receiveCustomerphone!=''){
		        	      			form.findField('receivephone').setValue(receiveCustomerMobilephone+'/'+receiveCustomerphone);
		        	      		}else if(receiveCustomerMobilephone==''){
		        	      			form.findField('receivephone').setValue(receiveCustomerphone);
		        	      		}else if(receiveCustomerphone==''){
		        	      			form.findField('receivephone').setValue(receiveCustomerMobilephone);
		        	      		}
		        	      		form.findField('strpaperNum').setValue(grid.get('strpaperNum'));
		        	      		var productCode = grid.get('productCode');
		        	      		if(productCode=='AF'){
		        	      			form.findField('productCode').setValue('精准空运');
		        	          	}else if(productCode=='FLF'){
		        	          		form.findField('productCode').setValue('精准卡航');
		        	          	}else if(productCode=='FSF'){
		        	          		form.findField('productCode').setValue('精准城运');
		        	          	}else if(productCode=='LRF'){
		        	          		form.findField('productCode').setValue('精准汽运(长途)');
		        	          	}else if(productCode=='SRF'){
		        	          		form.findField('productCode').setValue('精准汽运(短途)');
		        	          	}else if(productCode=='AIR_FREIGHT'){
		        	          		form.findField('productCode').setValue('空运');
		        	          	}else if(productCode=='PLF'){
		        	          		form.findField('productCode').setValue('汽运偏线');
		        	          	}else if(productCode=='TRANS_VEHICLE'){
		        	          		form.findField('productCode').setValue('汽运');
		        	          	}
		        	      		var receiveMethod = grid.get('receiveMethod');
		        	      		if(receiveMethod=='DELIVER_NOUP'){
		        	    			form.findField('receiveMethod').setValue('送货(不含上楼)');
		        	        	}else if(receiveMethod=='DELIVER_FLOOR'){
		        	        		form.findField('receiveMethod').setValue('送货上楼安装(家居)');
		        	        	}else if(receiveMethod=='SELF_PICKUP'){
		        	        		form.findField('receiveMethod').setValue('自提');
		        	        	}else if(receiveMethod=='DELIVER'){
		        	        		form.findField('receiveMethod').setValue('免费送货');
		        	        	}else if(receiveMethod=='DELIVER_INGA'){
		        	        		form.findField('receiveMethod').setValue('送货进仓');
		        	        	}else if(receiveMethod=='DELIVER_UP'){
		        	        		form.findField('receiveMethod').setValue('送货上楼');
		        	        	}else if(receiveMethod=='INNER_PICKUP'){
		        	        		form.findField('receiveMethod').setValue('内部带货自提');
		        	        	}else if(receiveMethod=='LARGE_DELIVER_UP'){
		        	        		form.findField('receiveMethod').setValue('大件上楼');
		        	        	}else if(receiveMethod=='SELF_PICKUP_FREE_AIR'){
		        	        		form.findField('receiveMethod').setValue('空运免费自提');
		        	        	}else if(receiveMethod=='SELF_PICKUP_AIR'){
		        	        		form.findField('receiveMethod').setValue('空运自提(不含机场提货费)');
		        	        	}else if(receiveMethod=='DELIVER_AIR'){
		        	        		form.findField('receiveMethod').setValue('空运免费送货');
		        	        	}else if(receiveMethod=='AIRPORT_PICKUP'){
		        	        		form.findField('receiveMethod').setValue('空运机场自提');
		        	        	}else if(receiveMethod=='DELIVER_UP_AIR'){
		        	        		form.findField('receiveMethod').setValue('空运送货上楼');
		        	        	}else if(receiveMethod=='DELIVER_INGA_AIR'){
		        	        		form.findField('receiveMethod').setValue('空运送货进仓');
		        	        	}
		        	      		var returnBillType = grid.get('returnBillType');
		        	      		if(returnBillType=='NONE'){
		        	      			form.findField('returnBillType').setValue('无需反单');
		        	          	}else if(returnBillType=='ORIGINAL'){
		        	          		form.findField('returnBillType').setValue('原件签收单返回');
		        	          	}else if(returnBillType=='FAX'){
		        	          		form.findField('returnBillType').setValue('传真件签收单返回');
		        	          	}else if(returnBillType=='SCANNED'){
		        	          		form.findField('returnBillType').setValue('扫描件返回');
		        	          	}else if(returnBillType=='ARRIVESHEET_FAX'){
		        	          		form.findField('returnBillType').setValue('传真返回');
		        	          	}
		        	      		var ordermethod = grid.get('orderPaidMethod');
		        	      		if(ordermethod=='CD'){
		        	    			form.findField('orderPaidMethod').setValue('银行卡');
		        	    		}else if(ordermethod=='CH'){
		        	    			form.findField('orderPaidMethod').setValue('现金');
		        	    		}else if(ordermethod=='OL'){
		        	    			form.findField('orderPaidMethod').setValue('网上支付');
		        	    		}else if(ordermethod=='CT'){
		        	    			form.findField('orderPaidMethod').setValue('月结');
		        	    		}else if(ordermethod=='FC'){
		        	    			form.findField('orderPaidMethod').setValue('到付');
		        	    		}else if(ordermethod=='DT'){
		        	    			form.findField('orderPaidMethod').setValue('临时欠款');
		        	    		}else if(ordermethod=='TT'){
		        	    			form.findField('orderPaidMethod').setValue('电汇');
		        	    		}else if(ordermethod=='NT'){
		        	    			form.findField('orderPaidMethod').setValue('支票');
		        	    		}
		        	      		Ext.getCmp('waybillimage'+waybill.checkbill.x).setSrc(waybill.checkbill.imagefile+''+grid.get('filePath'));
		        	      		//document.getElementById('waybillimage'+waybill.checkbill.x).width=500;
		        	      		//document.getElementById('waybillimage'+waybill.checkbill.x).height=300;
		        	      		document.getElementById('waybillimage'+waybill.checkbill.x).style.webkitTransform = "rotateZ(0deg)";
		        	      		Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(800);
		        	    		Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(800);
		        	      		currutrow++;
		        	      		if(currutrow==rowidx){
		        	      	     currutrow++;
		        	      		}
		                      };
		                       // 失败提示
		                      var failureFun = function(json){
		                    	  waybill.showErrorMes(json.message);
		                          };
		                      // 调用ajax请求
		                       waybill.requestJsonAjax(url,params,successFun,failureFun);// 发送AJAX请求
		        				}else{
		        					waybill.showInfoMes("已是最后一条运单！");
		        				}
		        			} 
		             }
		            	 
		            ];
		me.callParent([cfg]);
		
	}
    
});

Ext.define('Foss.waybill.checkBill.AddPictureBillPanel', {
	extend : 'Ext.panel.Panel',
	title : waybill.checkbill.i18n('图片'),//图片运单
	frame: true,
	//height:350,
	//width:550,
	autoScroll:true,
	//style:'float:left',
    defaults : {
    	margin : '0 0 0 0',
    },
    layout:{
    	type: 'column',
        columns: 1,
	},
    constructor : function(config) {
		var me = this;
		me.width = document.documentElement.clientWidth*0.8;
		me.height = (document.documentElement.clientHeight)*0.53;
		cfg = Ext.apply({}, config);
		me.items = [//me.getimagebox()
		            {
		            	xtype:'image',
		            	id:'waybillimage'+waybill.checkbill.x
		             }
		            ];
		me.callParent([cfg]);
		
		Ext.get(document.body).on('mousewheel',function(e){
			var delta=e.getWheelDelta();
			var imageipt =  document.getElementById('waybillimage'+waybill.checkbill.x); 
			Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(imageipt.width+5*delta);
			Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(imageipt.height+5*delta);
		});
	}
    
});

Ext.define('Foss.waybill.checkBill.AddimageBillForm', {
	extend : 'Ext.panel.Panel',
	title : waybill.checkbill.i18n('司机上传图片'),//图片运单
	frame: true,
	//height:350,
	//width:550,
//	autoScroll:true,
	//style:'float:left',
    defaults : {
    	margin : '0 0 0 0',
    },
    layout:{
    	type: 'column',
        columns: 2,
	},
	pictureBillPanel:null,
	getPictureBillPanel:function(){
    	if(Ext.isEmpty(this.imgageBillForm)){
    		this.pictureBillPanel = Ext.create('Foss.waybill.checkBill.AddPictureBillPanel');
    	}
    	return this.pictureBillPanel;
    },
    btnBillPanel:null,
    getBtnBillPanel:function(){
    	if(Ext.isEmpty(this.imgageBillForm)){
    		this.btnBillPanel = Ext.create('Foss.waybill.checkBill.AddBtnBillPanel');
    	}
    	return this.btnBillPanel;
    },    
    constructor : function(config) {
		var me = this;
		me.width = document.documentElement.clientWidth;
		me.height = (document.documentElement.clientHeight)*0.53;
		cfg = Ext.apply({}, config);
		me.items = [//me.getimagebox()
		            me.getPictureBillPanel(),
		            me.getBtnBillPanel()
		            ];
		me.bbar =[{
    	 text :'左旋转',
    	 
    	 handler:function(){
    	waybill.checkbill.z -= 90;
    	document.getElementById('waybillimage'+waybill.checkbill.x).style.webkitTransform = "rotate("+waybill.checkbill.z+"deg)";
    	//rotate('waybillimage'+waybill.checkbill.x,'left'); 
    	 }
     },
     {
    	 text :'右旋转',
    	 handler:function(){
    	 waybill.checkbill.z += 90;
         document.getElementById('waybillimage'+waybill.checkbill.x).style.webkitTransform = "rotate("+waybill.checkbill.z+"deg)";	 
    	 //rotate('waybillimage'+waybill.checkbill.x,'right');
    	 }
     },{
    	 text :'放大',
    	 handler:function(){
         var imageipt =  document.getElementById('waybillimage'+waybill.checkbill.x); 
         Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(imageipt.width+20);
         Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(imageipt.height+20);
         //imageipt.width = imageipt.width+20;
         //imageipt.height = imageipt.height+20;
    	 }
     },{
    	 text :'缩小',
    	 handler:function(){
    	 var imageipt =  document.getElementById('waybillimage'+waybill.checkbill.x); 
    	 Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(imageipt.width-20);
         Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(imageipt.height-20);
    	 }
     },];
		me.callParent([cfg]);
		
		Ext.get(document.body).on('mousewheel',function(e){
			var delta=e.getWheelDelta();
			var imageipt =  document.getElementById('waybillimage'+waybill.checkbill.x); 
			Ext.getCmp('waybillimage'+waybill.checkbill.x).setWidth(imageipt.width+20*delta);
			Ext.getCmp('waybillimage'+waybill.checkbill.x).setHeight(imageipt.height+20*delta);
		});
	}
    
});
Ext.define('Foss.waybill.checkBill.AddcheckBillForm', {
	extend : 'Ext.form.Panel',
	title : waybill.checkbill.i18n('pkp.waybill.checkbill.checbillinfo'),//新增作废数据
	frame: true,
	//height:350,
	//width:550,
	//style:'float:right',
    defaults : {
    	margin : '0 0 0 0',
    },
    layout:{
    	type: 'table',
        columns: 5
	},
    constructor : function(config) {
		var me = this;
		me.width = document.documentElement.clientWidth;
		me.height = (document.documentElement.clientHeight)*0.45;
		cfg = Ext.apply({}, config);
		me.items = [/*{
			    name: '',
		        xtype : 'textfield',
		        readOnly:true,
		        value:'    ',
		        width:300,
		        labelWidth:50,
			},*/{
			name: 'waybillNo',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.waybillno'),
	        xtype : 'textfield',
	        readOnly:true,
//	        width:150,
	        labelWidth:50,
	        colspan:2,
	        align: 'middle'
//	        pack: 'center'
	        
		},{
			name: 'orderNo',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.orderNo'),
	        xtype : 'textfield',
	        readOnly:true,
	        colspan:3,
	        labelWidth:100,
	        width:500
		},{
			name: 'deliveryCustomerContact',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.deliveryCustomerContact'),
	        xtype : 'textfield',
	        readOnly:true,
	        colspan:2,
	        width:400,
	        labelWidth:80
		},{
			name: 'receiveCustomerContact',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.receiveCustomerContact'),
	        xtype : 'textfield',
	        readOnly:true,
	        labelWidth:80,
	        width:400,
	        colspan:3
		},{
			name: 'deliveryphone',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.deliveryphone'),
	        xtype : 'textfield',
	        labelWidth:100,
	        readOnly:true,
	        width:300,
	        colspan:2
		},{
			name: 'receivephone',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.receivephone'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100,
	        colspan:3
		},{
			name: 'receiveCustomerAddress',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.receiveCustomerAddress'),
	        xtype : 'textarea',
	        readOnly:true,
	        colspan:2,
	        width:320,
	        height:30,
	        labelWidth:80,
	        listeners : { 
		         render : function(field, t) { 
		                 Ext.QuickTips.init(); 
		                 Ext.QuickTips.register({ 
		                 target : field.el, 
		                 text : field.getValue()
		                }) 
		             } 
		          } 

		},{
			name: 'targetOrgCode',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.targetOrgCode'),
	        xtype : 'textfield',
	        readOnly:true,
	        labelWidth:50,
	        width:200,
	        colspan:3
		},{
			name: 'orderPaidMethod',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.orderPaidMethod'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:150,
	        labelWidth:60,
	        colspan:1
		},{
			name: 'productCode',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.productCode'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:200,
	        labelWidth:60,
	        colspan:1
		},{
			name: 'goodsname',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.goodsname'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:200,
	        labelWidth:80,
	        colspan:1
		},{
			name: 'dgoodsWeightTotal',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.goodsWeightTotal')+'(kg)',
	        xtype : 'textfield',
	        readOnly:true,
	        width:150,
	        labelWidth:60,
	        colspan:1
		},{
			name: 'strpaperNum',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.paperNum'),
	        xtype : 'textfield',
	        readOnly:true,
	        labelWidth:40,
	        width:200,
	        colspan:1
		}/*,{
			name: 'deliverycustomercode',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.deliverycustomercode'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100
		}*/,{
			name: 'insuranceAmount',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.insuranceAmount'),
	        xtype : 'textfield',
	        readOnly:true,
//	        rowspan:1,
	        colspan:1,
	        width:150,
	        labelWidth:40
		},{
			name: 'receiveMethod',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.receiveMethod'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:200,
	        colspan:1,
	        labelWidth:60
		},{
			name: 'goodsQtyTotal',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.goodsQtyTotal'),
	        xtype : 'textfield',
	        readOnly:true,
	        colspan:1,
	        labelWidth:40,
	        width:150,
		},{
			name: 'dgoodsVolumeTotal',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.goodsVolumeTotal')+'(m³)',
	        xtype : 'textfield',
	        colspan:1,
	        readOnly:true,
	        labelWidth:65,
	        width:150,
		},{
			name: 'packageFee',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.packageFee'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100,
	        colspan:1
		}
		,{
			name: 'codAmount',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.codAmount'),
	        xtype : 'textfield',
	        readOnly:true,
	        colspan:1,
	        width:150,
	        labelWidth:40
		},{
			name: 'refundType',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.refundType'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100,
	        colspan:2
		}
		,{
			name: 'goodsSize',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.goodsSize'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:40,
	        colspan:2
		}
		,{
			name: 'dcServicefee',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.dcServicefee'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100,
	        colspan:1
		}/*提货方式*/,{
			name: 'returnBillType',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.returnBillType'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:200,
	        labelWidth:60,
	        colspan:2,
	        listeners : { 
		         render : function(field, t) { 
		                 Ext.QuickTips.init(); 
		                 Ext.QuickTips.register({ 
		                 target : field.el, 
		                 text : field.getValue()
		                }) 
		             } 
		          } 
		},{
			name: 'accountCode',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.accountCode'),
	        xtype : 'textfield',
	        readOnly:true,
	        width:300,
	        labelWidth:100,
	        colspan:2,
	        listeners : { 
		         render : function(field, t) { 
		                 Ext.QuickTips.init(); 
		                 Ext.QuickTips.register({ 
		                 target : field.el, 
		                 text : field.getValue()
		                }) 
		             } 
		          } 
		}	
		
		,{
			name: 'innerNotes',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.innerNotes'),
	        xtype : 'textarea',
	        readOnly:true,
	        colspan:2,
//	        width:500,
	        height:30,
	        labelWidth:60,
	        listeners : { 
		         render : function(field, t) { 
		                 Ext.QuickTips.init(); 
		                 Ext.QuickTips.register({ 
		                 target : field.el, 
		                 text : field.getValue()
		                }) 
		             } 
		          } 
		}
		
		,{
			name: 'packageRemark',
	        fieldLabel: waybill.checkbill.i18n('pkp.waybill.checkbill.packageRemark'),
	        xtype : 'textfield',
	        readOnly:true,
//	        width:300,
	        labelWidth:100,
	        colspan:3
		}
		
		
		];
		me.callParent([cfg]);
		
	},
	
});
// 图片运单列表
Ext.define('Foss.Supple.checkbillResultGrid', {
	extend:'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines: true,
	id:'checkbillid',
	emptyText:waybill.checkbill.i18n('pkp.waybill.todoAction.emptyText'),// 查询结果为空！,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	// 表格对象增加一个边框
	frame: true,
	stripeRows: true,
	// 增加表格列的分割线
	columnLines: true,
	// 定义表格的标题
	title:waybill.checkbill.i18n('pkp.waybill.checkbill.waybillpicturelist'),// 作废记录结果表,
	collapsible: true,
	animCollapse: true,
	store: null,
	// ----------定义某行的颜色结束---------------
	// 定义表格列信息
	columns: [
	{
		header:'ID',// id,
		dateIndex:'ID',
		hidden: true,
		align: 'center'
	},{
		// 字段标题
		header: waybill.checkbill.i18n('pkp.waybill.todoAction.number'),// 序号
		flex:0.1,
		xtype: "rownumberer",
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.waybillRfcAccount.waybill.no'),  //运单号
		// 关联model中的字段名
		dataIndex: 'waybillNo',
		flex:0.1,
		align: 'center',
		renderer:function(value,e,a,x){
			return '<a href="#" onclick="newwindow('+x+')">'+value+'</a>';
		}
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.checkbillstatus'), //审单状态
		// 关联model中的字段名
		dataIndex: 'checkbillstatus',
		flex:0.1,
		align: 'center',
		renderer : function(value){
		     if(value=='Y'){
		    	    return '审单正常';
		    	 }else if(value=='N'){
		    		return '审单异常';
		    	 }else if(value==null||value==''){
		    		return '未审单';
		    	 }
		　　　}
	},{
		header: waybill.checkbill.i18n('waybill.waybillToSupple.orderNo'),  //订单号
		// 关联model中 的字段名
		dataIndex: 'orderNo',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('waybill.waybillToSupple.billTime'), //开单时间
		// 关联model中的字段名
		dataIndex: 'billtime',
		flex:0.1,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.waybillpicturestatus'),  //图片运单状态
		// 关联model中的字段名
		dataIndex: 'pendgingType',
		flex:0.1,
		align: 'center',
		renderer : function(value,me,aa,bb){
	     if(value=='PDA_ACTIVE'){
	    	    return '已开单';
	    	 }else if(value=='PDA_PENDING'){
	    		return '已录入';
	    	 }
	　　　}
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.waybilloperatorcode'),
		// 关联model中的字段名
		dataIndex: 'operator',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.waybilloperatorname'),
		// 关联model中的字段名
		dataIndex: 'operatorname',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.driverCode'),
		// 关联model中的字段名
		dataIndex: 'driverCode',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.driverName'),
		// 关联model中的字段名
		dataIndex: 'driverName',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.checkbill.truckCode'),
		// 关联model中的字段名
		dataIndex: 'truckCode',
		flex:0.1,
		align: 'center'
	},{
		header: waybill.checkbill.i18n('pkp.waybill.todoAction.receiveCustomerName'),
		// 关联model中的字段名
		dataIndex: 'receiveorgname',
		flex:0.1,
		align: 'center'
	}],
	pagingToolbar : null,	
  	getPagingToolbar : function() {	
  		if (this.pagingToolbar == null) {	
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:this.store,
// plugins: 'pagesizeplugin',
			plugins: {
        		ptype: 'pagesizeplugin',
       	 		// 超出输入最大限制是否提示，true则提示，默认不提示
        		alertOperation: true,
        		// 自定义分页comobo数据
        		sizeList: [['5', 5], ['10', 10], ['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]],
        		// 入最大限制，默认为200
        		maximumSize: 1000
        	},
			displayInfo: true
		});
  		}	
  		return this.pagingToolbar;	
  	},
	/*
	 * constructor: function(config){ var me = this, cfg = Ext.apply({},
	 * config); me.store = Ext.create('Foss.Supple.Store.WaybillSuppleStore');
	 * //添加分页工具条 me.bbar = me.getPagingToolbar(); me.callParent([cfg]); }
	 */
	constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.Supple.Store.CheckbillStore');
			//添加分页工具条
			me.bbar = me.getPagingToolbar();
			me.callParent([cfg]);
		}
});
//创建一个待办事项枚举store
Ext.define('Foss.Supple.Store.CheckbillStore',{
	extend: 'Ext.data.Store',
	fields: [
	         
	         
	    { name: 'id',type:'string'},//ID
	    { name: 'waybillNo',type:'string' },//运单号
		{ name: 'checkbillstatus',type:'string' },//审单状态
		{ name: 'orderNo',type:'string' },//订单号
		{ name: 'billtime',convert:dateConvert },//开单时间
	    { name: 'pendgingType',type:'string' }, //图片运单状态
		{ name: 'operator',type:'string' }, //开单人工号
		{ name: 'operatorname',type:'string' },  //开单人姓名
		{ name: 'driverCode',type:'string' },  //司机工号
		{ name: 'driverName',type:'string' },//司机姓名
		{ name: 'truckCode',type:'string' },//车牌号
		{ name: 'receiveorgname',type:'string' },//收获部门
		{ name: 'deliveryCustomerContact',type:'string' },
		{ name: 'deliveryCustomerMobilephone',type:'string' },
		{ name: 'receiveCustomerContact',type:'string' },
		{ name: 'receiveCustomerMobilephone',type:'string' },
		{ name: 'receiveCustomerAddress',type:'string' },
		{ name: 'targetOrgCode',type:'string' },
		{ name: 'innerNotes',type:'string' },
		{ name: 'strpaperNum',type:'string' },
		{ name: 'goodsQtyTotal',type:'int' },
		{ name: 'dgoodsWeightTotal',type:'float' },
		{ name: 'dgoodsVolumeTotal',type:'float' },
		{ name: 'insuranceAmount',type:'int' },
		{ name: 'codAmount',type:'int' },
		{ name: 'productCode',type:'string' },
		{ name: 'receiveMethod',type:'string' },
		{ name: 'orderPaidMethod',type:'string' },
		{ name: 'goodsSize',type:'string' },
		{ name: 'returnBillType',type:'string' },
		{ name: 'filePath',type:'string' },
		{ name: 'deliveryCustomerPhone',type:'string' },
		{ name: 'receiveCustomerPhone',type:'string' },
		{ name: 'deliverycustomercode',type:'string'},
		{ name: 'goodsname',type: 'string'},
		{ name: 'refundType',type: 'string'},
		{ name: 'accountCode',type: 'string'},
		{ name: 'packageFee',type: 'string'},
		{ name: 'packageRemark',type: 'string'},
		{ name: 'dcServicefee',type: 'string'}
	],
	//定义一个代理对象
	//是否自动查询
	autoLoad: false,
	//默认每页数据大小
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'GET',
		url:waybill.realPath('querycheckbill.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.waybillPictureEntityList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			var queryParams = waybill.checkbill.checkbillForm.getValues();
			var form = waybill.checkbill.checkbillForm.getForm();
			if(!form.isValid()){
				return false;
			}
			Ext.apply(operation, {
				params : {					
					'vo.waybillPictureDto.waybillNo': queryParams.waybillNo,
					'vo.waybillPictureDto.operator': queryParams.operator,
					'vo.waybillPictureDto.checkbillstatus': queryParams.checkbillstatus,
					'vo.waybillPictureDto.createTimeStart': queryParams.createTimeStart,
					'vo.waybillPictureDto.createTimeEnd': queryParams.createTimeEnd,
					'vo.waybillPictureDto.belongOrgCode': queryParams.belongOrgCode
				}
			});	
		}
	}
});
Ext.onReady(function(){
	Ext.Loader.setConfig({enabled:true}); 
	Ext.QuickTips.init();
	if (Ext.getCmp('T_waybill-checkbillIndex_content')) {
		return;
	}
	var myDate = new Date();
	waybill.checkbill.x= parseInt(myDate.getDate()+''+myDate.getHours()+''+myDate.getMinutes()+''+myDate.getSeconds()+''+myDate.getMilliseconds());
	waybill.checkbill.imagefile = document.getElementById('url').value+'filePath=';
	waybill.checkbill.checkbillForm = Ext.create('Foss.Supple.Form.checkbillForm');
	waybill.checkbill.checkbillGrid =Ext.create('Foss.Supple.checkbillResultGrid'); 
	Ext.getCmp('T_waybill-checkbill').add(Ext.create('Ext.panel.Panel',{
		id: 'T_waybill-checkbill_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getcheckbillForm : function() {
			return waybill.checkbill.checkbillForm;
		},
		// 获得查询结果GRID
		getcheckbillGrid : function() {
			return waybill.checkbill.checkbillGrid;
		},
		items: [waybill.checkbill.checkbillForm,waybill.checkbill.checkbillGrid],
	}));
	waybill.checkbill.checkbillForm.getForm().findField('belongOrgCode').setValue(FossUserContext.getCurrentDept().code);
	waybill.checkbill.checkbillForm.getForm().findField('belongOrgCode').getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
	waybill.checkbill.checkbillForm.getForm().findField('belongOrgCode').addCls('readonlyhaveborder');
});
window.newwindow=function(rowIndex){
	var counts=Ext.getCmp('checkbillid').getStore().getCount();;
	waybill.checkbill.num = rowIndex;
	waybill.checkbill.counts = counts ;	
    var win = Ext.create('Foss.waybill.checkBillWindow').show();
}