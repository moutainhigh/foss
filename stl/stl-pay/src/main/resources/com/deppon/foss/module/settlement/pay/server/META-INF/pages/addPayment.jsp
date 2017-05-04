	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ taglib uri="/ext" prefix="ext"  %>
	<%@taglib prefix="s" uri="/struts-tags"%>
	<%@ include file="../common/settlementConstants.jsp" %>
	<link rel="stylesheet" type="text/css" href="${styles}/style.css">
	<script type="text/javascript">
		//判断pay是否存在，如果在对账单模块加载该jsp，不会生成pay
		if(Ext.isEmpty(pay)){
			var	pay = {};
		}
		//获取后台数据字典的值
		pay.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
		pay.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
		//来源单据编号  应付、预收、对账单
		pay.SOURCE_BILL_TYPE__ADVANCED_PAYMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT"/>';
		pay.SOURCE_BILL_TYPE__DEPOSIT_RECEIVED='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED"/>';
		pay.SOURCE_BILL_TYPE__STATEMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT"/>';
		//获取装卸费、服务补救值、理赔
		pay.SERVICE_FEE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__SERVICE_FEE"/>';
		pay.COMPENSATION = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__COMPENSATION"/>';
		pay.CLAIM = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__CLAIM"/>';
		pay.RENTCAR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__RENT_CAR"/>';
		//外请车
		pay.TRUCK1_FIRST = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST"/>';
		pay.TRUCK1_LAST = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST"/>';
		pay.TRUCK2_FIRST = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST"/>';
		pay.TRUCK2_LAST = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST"/>';
		//外部对公账号、外部对私账户
		pay.FIN_ACCOUNT_TYPE_PUBLIC = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PUBLIC"/>';
		pay.FIN_ACCOUNT_TYPE_PRIVATE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PRIVATE"/>';	
		pay.FIN_ACCOUNT_TYPE_DPSON =  '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_DPSON"/>';
	</script>
	 <script type="text/javascript" >
	  //开发环境地址是：http://192.168.17.221:8881/fssc
	  //测试环境UAT地址是：http://192.168.20.251:8080/fssc
	  //测试环境SIT地址是：http://192.168.20.148:8080/fssc
	  var fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
	  //附件查询地址
	  var attachmentQueryUrl = 'http://'+fssc+'/fssc/attachment/query.action';
	  //附件下载地址
	  var attachmentDownLoadUrl = 'http://'+fssc+'/fssc/attachment/download.action';
	  //附件删除地址
	  var attachmentDeleteUrl = 'http://'+fssc+'/fssc/attachment/delete.action';
	  //附件上传地址
	  var attachmentUploadUrl = 'http://'+fssc+'/fssc/attachment/upload.action';
	  
	  //金额相减
	  pay.amountSub = function(arg1,arg2){
	 	 var t1 = 0,t2 = 0,m,n;
	 	 try{
	 		 t1 = arg1.toString().split(".")[1].length;
	 	 }catch(e){
	 		 // do nothing
	 	 }
	 	 try{
	 		 t2 = arg2.toString().split(".")[1].length;
	 	 }catch(e){
	 	 }
	 	 with(Math){
	 		 n = max(t1,t2);
	 		 m = pow(10,n);
	 		 return ((arg1*m - arg2*m)/m).toFixed(n);
	 	 }
	  }
	
	  /**
	   * ***********************************附件上传 start by z.halo 2013-05-18 15:49**********************************
	   *
	   *
	   */
	  Ext.define('Fssc.common.AttachMentResource', {
	      extend : 'Ext.data.Model',
	      fields : [{
	          name : 'attachguid',
	          type : 'string'
	      }, {
	          name : 'attachname',
	          type : 'string'
	      }, {
	          name : 'attachsize',
	          type : 'string'
	      }, {
	          name : 'attachpath',
	          type : 'string'
	      }, {
	          name : 'id',
	          type : 'string'
	      }]
	  });
	
	  Ext.define('Fssc.common.upFileStore', {
	      extend : 'Ext.data.Store',
	      model : 'Fssc.common.AttachMentResource',
	      proxy : {
	          type : 'ajax',
	          url : attachmentQueryUrl,
	          reader : {
	              type : 'json',
	              root : 'resourceList',
	              totalProperty : 'totalCount'
	          },
	          // 设置请求方式
	          actionMethods : 'POST'
	      }
	  });
	  function downLoadFile(filePath, fileName) {
	      var url = attachmentDownLoadUrl + '?resource.attachpath=' + filePath + '&resource.attachname=' + fileName;
	      window.location.href = url;
	  }
	
	  function deleteFile(fileId, attachguid, upfilegridstore) {
	      Ext.Ajax.request({
	          url : attachmentDeleteUrl,
	          params : {
	              'queryData.fileId' : fileId,
	              'queryData.attachguid' : attachguid
	          },
	          success : function(response, options) {
	              var data = Ext.decode(response.responseText);
	              if (!data.success) {
	                  Ext.Msg.alert('提示', data.message);
	                  return;
	              }
	              if (upfilegridstore) {
	                  upfilegridstore.loadPage(1);
	              }
	          },
	          failure : function() {
	              Ext.Msg.alert('提示', '服务器异常; 请稍后再试');
	          }
	      });
	  }
	
	  /**
	   * 附件上传主界面
	   */
	  Ext.define('Fssc.common.upFileGrid', {
	      extend : 'Ext.grid.Panel',
	      alias : 'widget.upfilegrid',
	      height : '100%',
	      attachRelId : null,
	      setAutoLoadStore : function(flag) {
	          if (flag) {
	              this.getPagingToolbar().moveFirst();
	          }
	      },
	      setAttachRelId : function(attachRelId) {
	          this.attachRelId = attachRelId;
	      },
	      getAttachRelId : function() {
	          return this.attachRelId;
	      },
	      downLoadFile : function(value, metaData, record) {
	          return Ext.String.format('<a href=\"javascript:downLoadFile(\'{0}\'' + ',' + '\'{1}\'' + ')\">{2}</a>', record.data.attachpath, record.data.attachname, record.data.attachname);
	      },
	      renderDelFile : function(value, metaData, record) {
	          var href2 = Ext.String.format('[<a href=\"javascript:deleteFile(\'{0}\'' + ',\'{1}\')\">{2}</a>]', record.data.id, record.data.attachguid, '删除');
	          return href2;
	      },
	      pagingToolbar : null,
	      getPagingToolbar : function() {
	          if (this.pagingToolbar == null) {
	              this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
	                  store : this.store,
	                  pageSize: 5,
	  				 columnWidth:1,
	  				 plugins: Ext.create('Deppon.ux.PageSizePlugin', {
	  					//设置分页记录最大值，防止输入过大的数值
	  					maximumSize: 5
	  				})
	              });
	          }
	          return this.pagingToolbar;
	      },
	      constructor : function(config) {
	          var me = this, cfg = Ext.apply({}, config);
	          this.store = Ext.create('Fssc.common.upFileStore', {
	              pageSize : 10,
	              listeners : {
	                  beforeload : function(store, operation, eOpts) {
	                      Ext.apply(operation, {
	                          params : {
	                              'queryData.attachguid' : me.attachRelId
	                          }
	                      });
	                  }
	              }
	          });
	          me.columns = [{
	              xtype : 'rownumberer',
	              text : '序号',
	              width : 50
	          }, {
	              text : '附件名称',
	              dataIndex : 'attachguid',
	              width : 300,
	              flex : 1,
	              renderer : me.downLoadFile
	          }, {
	              text : '附件大小',
	              dataIndex : 'attachsize',
	              width : 100
	          }, {
	              text : '附件操作',
	              xtype : 'actioncolumn',
	              text : '操作',
	              iconCls : 'deppon_icons_delete',
	              width : 200,
	              handler : function(g, r, c) {
	                  var store = g.getStore();
	                  var record = store.getAt(r);
	                  deleteFile(record.get('id'), record.get('attachguid'), store);
	              }
	          }];
	          this.bbar = me.getPagingToolbar();
	          me.callParent([cfg]);
	      }
	  });
	  // 设置grid中各列内容可复制
	  Ext.view.TableChunker.metaRowTpl = ['<tr class="' + Ext.baseCSSPrefix + 'grid-row {addlSelector} {[this.embedRowCls()]}" {[this.embedRowAttr()]}>', '<tpl for="columns">', '<td class="{cls} ' + Ext.baseCSSPrefix + 'grid-cell ' + Ext.baseCSSPrefix + 'grid-cell-{columnId} {{id}-modified} {{id}-tdCls} {[this.firstOrLastCls(xindex, xcount)]}" {{id}-tdAttr}><div class="' + Ext.baseCSSPrefix + 'grid-cell-inner" style="{{id}-style}; text-align: {align};">{{id}}</div></td>', '</tpl>', '</tr>'];
	
	  Ext.core.Element.prototype.unselectable = function() {
	      var me = this;
	      if (me.dom.className.match(/(x-grid-table|x-grid-view)/)) {
	          return me;
	      }
	      me.dom.unselectable = "on";
	      me.swallowEvent("selectstart", true);
	      me.applyStyles("-moz-user-select:none;-khtml-user-select:none;");
	      me.addCls(Ext.baseCSSPrefix + 'unselectable');
	      return me;
	  };
	  // 选择上传文件
	  Ext.define('Fssc.common.upFileForm', {
	      extend : 'Ext.form.Panel',
	      alias : 'widget.upfileform',
	      attachRelId : null,
	      userCode : null,
	      upLoadGrid : null,
	      bodyCls : 'tbarformcls',
	      setUserCode : function(userCode) {
	          this.userCode = userCode;
	      },
	      getUserCode : function() {
	          return this.userCode;
	      },
	      setAttachRelId : function(attachRelId) {
	          this.attachRelId = attachRelId;
	      },
	      getAttachRelId : function() {
	          return this.attachRelId;
	      },
	      setUpLoadGrid : function(upLoadGrid) {
	          this.upLoadGrid = upLoadGrid;
	      },
	      getUpFileGrid : function() {
	          return this.upLoadGrid;
	      },
	      uploadAction : function() {
	          var me = this;
	          if (this.attachRelId == null || this.attachRelId == '') {
	              Ext.Msg.alert('提示', '请先暂存表单后再上传附件');
	              return;
	          }
	          this.getForm().submit({
	              url : attachmentUploadUrl,
	              method : 'POST',
	              params : {
	                  'resource.createUser' : this.userCode,
	                  'resource.attachguid' : this.attachRelId
	              },
	              success : function(form, action) {
	                  me.upLoadGrid.getPagingToolbar().moveFirst();
	              },
	              failure : function(form, action) {
	                  Ext.Msg.alert('提示', "文件上传失败!");
	              }
	          });
	      },
	      constructor : function(config) {
	          var me = this, cfg = Ext.apply({}, config);
	          me.items = [{
	              xtype : 'fieldcontainer',
	              layout : 'column',
	              items : [{
	                  xtype : 'filefield',
	                  name : 'attach',
	                  labelWidth : 60,
	                  width : 350,
	                  fieldLabel : '上传附件',
	                  fieldStyle : 'border: solid 1px #B5B8C8!important;background: transparent !important;',
	                  buttonConfig : {
	                      text : "浏览"
	                  },
	                  blankText : "不能为空",
	                  allowBlank : false
	              }, {
	                  text : '上传',
	                  xtype : 'button',
	                  height : 22,
	                  handler : function() {
	                      if (me.attachRelId) {
	                          me.uploadAction();
	                      } else {
	                          if (me.relationActiveFun) {
	                              me.relationActiveFun();
	                          }
	                      }
	                  }
	              }]
	          }];
	          me.callParent([cfg]);
	      }
	  });
	  Ext.define('Fssc.common.upFilePanel', {
	      extend : 'Ext.panel.Panel',
	      alias : 'widget.upfilepanel',
	      attachRelId : null,
	      userCode : null,
	      autoLoadGrid : false,
	      setUserCode : function(userCode) {
	          var uploadForm = this.getUploadForm();
	          uploadForm.setUserCode(userCode);
	          this.userCode = userCode;
	      },
	      getUserCode : function() {
	          return this.userCode;
	      },
	      setAttachRelId : function(attachRelId) {
	          var uploadForm = this.getUploadForm();
	          var uploadGrid = this.getUploadGrid();
	          uploadForm.setAttachRelId(attachRelId);
	          uploadGrid.setAttachRelId(attachRelId);
	          if (attachRelId) {
	              uploadGrid.setAutoLoadStore(true);
	          }
	          this.attachRelId = attachRelId;
	      },
	      getAttachRelId : function() {
	          return this.attachRelId;
	      },
	      uploadForm : null,
	      getUploadForm : function() {
	          var me = this;
	          if (this.uploadForm == null) {
	              this.uploadForm = Ext.create('Fssc.common.upFileForm', {
	                  relationActiveFun : function() {
	                      if (me.relationActiveFun) {
	                          me.relationActiveFun();
	                      }
	                  }
	              });
	          }
	          return this.uploadForm;
	      },
	      fileUpload : function() {
	          if (this.uploadForm != null) {
	              this.uploadForm.uploadAction();
	          }
	      },
	      uploadGrid : null,
	      getUploadGrid : function() {
	          if (this.uploadGrid == null) {
	              this.uploadGrid = Ext.create('Fssc.common.upFileGrid', {
	                  height : 268,
	                  autoScroll : true
	              });
	          }
	          return this.uploadGrid;
	      },
	      constructor : function(config) {
	          var me = this, cfg = Ext.apply({}, config);
	          var uploadForm = me.getUploadForm();
	          var uploadGrid = me.getUploadGrid();
	          if (config && config.userCode) {
	              uploadForm.setUserCode(config.userCode);
	          }
	          if (config && config.attachRelId) {
	              uploadGrid.setAttachRelId(config.attachRelId);
	              uploadForm.setAttachRelId(config.attachRelId);
	          }
	          uploadForm.setUpLoadGrid(uploadGrid);
	          me.items = [uploadForm, uploadGrid];
	          me.listeners = {
	              afterrender : function(obj, eOpts) {
	                  obj.getUploadGrid().setAutoLoadStore(true);
	              }
	          };
	          me.callParent([cfg]);
	      }
	  });
	  Ext.define('Fssc.common.upFileViewGrid', {
	      extend : 'Ext.grid.Panel',
	      alias : 'widget.upfileviewgrid',
	      height : '100%',
	      //title : '附件列表',
	      sortableColumns : false,
	      enableColumnHide : false,
	      enableColumnMove : false,
	      stripeRows : true,
	      attachRelId : null,
	      autoLoadStore : false,
	      setAutoLoadStore : function(flag) {
	          if (flag) {
	              this.getPagingToolbar().moveFirst();
	          }
	      },
	      setAttachRelId : function(attachRelId) {
	          this.attachRelId = attachRelId;
	      },
	      getAttachRelId : function() {
	          return this.attachRelId;
	      },
	      pagingToolbar : null,
	      getPagingToolbar : function() {
	          if (this.pagingToolbar == null) {
	              this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
	                  store : this.store
	              });
	          }
	          return this.pagingToolbar;
	      },
	      downLoadFile : function(value, metaData, record) {
	          return Ext.String.format('<a href=\"javascript:downLoadFile(\'{0}\'' + ',' + '\'{1}\'' + ')\">{2}</a>', record.data.attachpath, record.data.attachname, record.data.attachname);
	      },
	      constructor : function(config) {
	          var me = this, cfg = Ext.apply({}, config);
	          if (cfg && cfg.attachRelId) {
	              me.attachRelId = cfg.attachRelId;
	          }
	          this.store = Ext.create('Fssc.common.upFileStore', {
	              pageSize : 10,
	              listeners : {
	                  beforeload : function(store, operation, eOpts) {
	                      Ext.apply(operation, {
	                          params : {
	                              'queryData.attachguid' : me.attachRelId
	                          }
	                      });
	                  }
	              }
	          });
	          me.columns = [{
	              xtype : 'rownumberer',
	              text : '序号',
	              width : 50
	          }, {
	              text : '附件名称',
	              dataIndex : 'attachguid',
	              width : 300,
	              flex : 1,
	              renderer : me.downLoadFile
	          }, {
	              text : '附件大小',
	              dataIndex : 'attachsize',
	              width : 100
	          }];
	          this.bbar = me.getPagingToolbar();
	          if (cfg && cfg.autoLoadStore) {
	              me.setAutoLoadStore(true);
	          }
	          me.callParent([cfg]);
	      }
	  });
	
	  Ext.define('Foss.payment.ShangChuanWindow', {
	 		extend:'Ext.window.Window',
	 		width:stl.SCREENWIDTH*0.7,
	 		modal:true,
	 		constrainHeader: true,
	 		closeAction:'hide',
	 		loadMask:null,
	 		upfilepanel: null,
	 		getUpfilepanel: function(){
	 			if(this.upfilepanel==null){
	 				this.upfilepanel = Ext.create('Fssc.common.upFilePanel');
	 				pay.upfilepanel = this.upfilepanel;
	 			}
	 			return this.upfilepanel;
	 		},
	 		constructor : function(config) {
	 	        var me = this, cfg = Ext.apply({}, config);
	 	        me.items = [me.getUpfilepanel()];
	 	        me.callParent([cfg]);
	 	    }
	  });
	  
	  pay.addPaymentUpload = function(){
		Ext.Ajax.request({
			url:ContextPath.STL_WEB + '/pay/uploadAppendix.action',
			 	params:{
					'billPaymentVo.paymentEntity.batchNo':pay.batchNo
				},
			 	success:function(response){
			 		var result = Ext.decode(response.responseText);
			 		pay.batchNo = result.billPaymentVo.paymentEntity.batchNo;
			 		pay.userCode = result.billPaymentVo.paymentEntity.createUserCode;
			 		pay.shangChuanWindow =Ext.create('Foss.payment.ShangChuanWindow');
			 	 	pay.upfilepanel.setUserCode(pay.userCode);
			 	 	pay.upfilepanel.setAttachRelId(pay.batchNo);
			 		pay.shangChuanWindow.show();
				}
		 });
	  }
	 
	 /**
	  * ***********************************附件上传 end by z.halo 2013-05-18 15:49**********************************
	  */
	 
	 /**
	  * 表格编辑事件处理
	  */
	 pay.addPaymentGridEidt = function(th,e){
	 	var newValue = e.value;//获取本次付款金额修改后值
	 	var record = e.record;//获取当前记录数
	 	//获取上面form表单
	 	var form = e.grid.up('window').items.items[0];
	 	//获取金额组件，当修改本次付款金额时，会弹出提示
	 	var amount = form.getForm().findField('amount');
	 	var currentAmount = amount.getValue();
	 	//获取备注
	 	var notes = record.data.notes;
	 	//获取单据类型
	 	var billType= record.data.billType;
	 	if(record.data.notes!=null){
	 		notes = Ext.String.trim(record.data.notes);
	 	}
	 	//如果修改的是本次付款金额
	 	if(e.field=='currentPaymentAmount'){
	 		var unverifyAmount = record.data.unverifyAmount;//获取未核销金额
	 		//如果本次修改金额为空、小于等于0或者大于未核销金额，则弹出提示
	 		//DDW
	 		if(null != record.data.billType && record.data.billType == 'PFCP'){
	 			if(record.data.currentPaymentAmount< unverifyAmount){
	 				Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.updateAmountWaring"/>');
		 			record.set('currentPaymentAmount',e.originalValue);
	 			}else if(Ext.isEmpty(newValue)|| newValue<=0|| newValue>unverifyAmount){
		 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentAmountWaring"/>');
		 			record.set('currentPaymentAmount',e.originalValue);
		 		}
	 		}else if(Ext.isEmpty(newValue)|| newValue<=0|| newValue>unverifyAmount){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentAmountWaring"/>');
	 			record.set('currentPaymentAmount',e.originalValue);
	 		//如果为装卸费、服务补救则必须全额付款
	 		}else if(!Ext.isEmpty(billType) && (pay.SERVICE_FEE==billType||pay.RENTCAR==billType
	 			||pay.COMPENSATION==billType || pay.CLAIM==billType||pay.TRUCK1_FIRST==billType
	 			||pay.TRUCK1_LAST==billType||pay.TRUCK2_FIRST==billType||pay.TRUCK2_LAST==billType)&& newValue<unverifyAmount){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.fullPaymentWarning"/>');
	 			record.set('currentPaymentAmount',e.originalValue);
	 		}else{
	 			currentAmount = currentAmount-e.originalValue+newValue;
	 			amount.setValue(currentAmount);
	 		}
	 	//修改备注	
	 	}else if(e.field=='notes'){
	 		if(Ext.isEmpty(notes)){
	 			record.set('notes',null);
	 		}	
	 	//临时租车，税金可编辑
	 	}else if(e.field=='taxAmount'){
	 		var unverifyAmount = record.data.unverifyAmount;//获取未核销金额
	 		//如果本次修改金额为空、小于等于0或者大于未核销金额，则弹出提示
	 		if(Ext.isEmpty(newValue)|| newValue<=0|| newValue>unverifyAmount){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.taxAmountWaring"/>');
	 			record.set('taxAmount',e.originalValue);
	 		}else{
	 			//设置税金
	 			var tax = pay.amountSub(unverifyAmount,newValue);
	 			record.set('tax',tax);
	 		}
	 	//业务发生日期	
	 	}else{
	 		if(e.field =='businessOfDate'){
				//获取本月最后一天
				var endDate = Ext.Date.getLastDateOfMonth(new Date());
				var startDate = Ext.Date.getFirstDateOfMonth(new Date());
				//获取上月第一天
				var targetDate  = Ext.Date.add(startDate,Ext.Date.MONTH,-1);
				//判断日期
				if(!Ext.Date.between(newValue,targetDate,endDate)){
					Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.rentCarForPayReport.costDateWarning"/>');
					record.set('businessOfDate',e.originalValue);
					return false;
				}
	 		}
	 	}
	 	//当本次付款金额不等于未核销金额或者备注不为空
	 	if(record.data.currentPaymentAmount!=record.data.unverifyAmount
	 		||!Ext.isEmpty(record.data.notes)){
	 		record.set('isEdit',1);	
	 	}else{
	 		record.set('isEdit',0);	
	 	}
	 	//提交记录
	 	record.commit();
	 }
	
	 /**
	  * 从明细中删除
	  * @param {} grid 表单
	  * @param {} rowIndex 行标
	  * @param {} colIndex 
	  */
	 pay.addPaymentGridEntryRemove = function(grid, rowIndex, colIndex){
	 	Ext.Msg.confirm('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.isDelete"/>',function(o){
	 		//提示是否要删除
	 		if(o=='yes'){
	 			//获取表单
	 			var form = grid.up('window').items.items[0].getForm();
	 			//获取金额组件
	 			var amountComponent = form.findField('amount');
	 			//获取要删除行
	 			var selection = grid.getStore().getAt(rowIndex);
	 			//获取要删除行的本次付款金额
	 			var currentPaymentAmount = selection.get('currentPaymentAmount');
	 			//删除该条记录
	 			grid.store.remove(selection);
	 			//重置金额组件的金额
	 			amountComponent.setValue(amountComponent.getValue()-currentPaymentAmount);
	 		}
	 	});
	 }
	
	 /**
	  * 取消操作
	  */
	 pay.addPaymentCancel = function(){
	 	var win = this.up('window');
	 	Ext.Msg.confirm('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.isExistPaymentPage"/>',function(o){
	 		//提示是否要删除
	 		if(o=='yes'){
	 			if(!Ext.isEmpty(win)){
	 				win.hide();
	 			}
	 		}
	 	});
	 }
	
	 /**
	  * 提交操作
	  */
	 pay.addPaymentCommit = function(){
	 	var win = this.up('window');//获取弹出窗
	 	var form = win.getAddFormPanel();//获取表单
	 	var grid = win.getAddPaymentEntryGrid();//获取表格
	 	//获取form表单数据进行校验
	 	var customerCode = form.getForm().findField('customerCode').getValue();
	 	var customerName = form.getForm().findField('customerName').getValue();
	 	var paymentOrgCode = form.getForm().findField('paymentOrgCode').getValue();
	 	var paymentOrgName = form.getForm().findField('paymentOrgName').getValue();
	 	var paymentCompanyCode = form.getForm().findField('paymentCompanyCode').getValue();
	 	var paymentCompanyName = form.getForm().findField('paymentCompanyName').getValue();
	 	
	 	//部门名称不能为空
	 	if(Ext.isEmpty(paymentOrgName)){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentOrgNameIsNullWarning"/>');
	 		return false;
	 	}
	 	
	 	//部门编码不能为空
	 	if(Ext.isEmpty(paymentOrgCode)){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentOrgCodeIsNullWarning"/>');
	 		return false;
	 	}
	 	
	 	//公司名称不能为空
	 	if(Ext.isEmpty(paymentCompanyName)){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentCompanyNameIsNullWarning"/>');
	 		return false;
	 	}
	 	//公司编码不能为空
	 	if(Ext.isEmpty(paymentCompanyCode)){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentCompanyCodeIsNullWarning"/>');
	 		return false;
	 	}
	 	
	 	//获取表单中信息进行校验	
	 	var paymentType = form.getForm().findField('paymentType').getValue();
	 	var amount =  form.getForm().findField('amount').getValue();
	 	var accountNo =  form.getForm().findField('accountNo').getValue();
	 	var provinceName =  form.getForm().findField('provinceName').getValue();
	 	var provinceCode =  form.getForm().findField('provinceCode').getValue();
	 	var cityName =  form.getForm().findField('cityName').getValue();
	 	var cityCode =  form.getForm().findField('cityCode').getValue();
	 	var bankHqCode =  form.getForm().findField('bankHqCode').getValue();
	 	var bankHqName =  form.getForm().findField('bankHqName').getValue();
	 	var bankBranchName =  form.getForm().findField('bankBranchName').getValue();
	 	var bankBranchCode =  form.getForm().findField('bankBranchCode').getValue();
	 	var accountType =  form.getForm().findField('accountType').getValue();
	 	var payeeName =	form.getForm().findField('payeeName').getValue();
	 	var payeeCode =	form.getForm().findField('payeeCode').getValue();
	 	var mobilePhone = form.getForm().findField('mobilePhone').getValue();
	 	var invoiceHeadCode = form.getForm().findField('invoiceHeadCode').getValue();
	 	var invoiceHeadName = form.getForm().findField('invoiceHeadCode').getRawValue();

	 	//临时租车
	 	var costDeptCode = form.getForm().findField('costDeptCode').getValue();
	 	var costDeptName = form.getForm().findField('costDeptCode').getRawValue();
	 	var costType = form.getForm().findField('costType').getValue();
	 	var isTaxinvoice = form.getForm().findField('isTaxinvoice').getValue();
	 	var taxinvoiceNo = form.getForm().findField('taxinvoiceNo').getValue();
	 	var taxpayerNumber = form.getForm().findField('taxpayerNumber').getValue();
		
	 	//保理
	 	var factoring = form.getForm().getRecord().get('factoring');
	 	//至少存在一条明细			
	 	if(grid.store.data.length==0){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.mustExistOneWarning"/>');
	 		return false;
	 	}
	 	//进行判断
	 	if(Ext.isEmpty(paymentType)){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentTypeIsNullWarning"/>');
	 		return false;
	 	}
	 	//金额判断
	 	if(Ext.isEmpty(amount) || amount<=0){
	 		Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.cashToPayAmountLimitWarning"/>');
	 		return false;
	 	}
	 		
	 	//如果为电汇
	 	if(paymentType==pay.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
	 		if(Ext.isEmpty(accountNo)||Ext.isEmpty(provinceCode)
	 			||Ext.isEmpty(cityCode)||Ext.isEmpty(bankHqCode)
	 			||Ext.isEmpty(bankBranchCode)||Ext.isEmpty(accountType)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.bankInfoIsNullWarning"/>');
	 			return false;	
	 		}
	 		//校验界面输入条件
	 		if(!form.getForm().isValid()){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.common.validateFailAlert"/>');
	 			return false;
	 		}
	 		//判断如果为电汇，收款人不能为空
	 		if(Ext.isEmpty(payeeName)||Ext.isEmpty(mobilePhone)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.payeeInfoIsNullWarning"/>');
	 			return false;	
	 		}
	 		//发票抬头判断
	 		if(Ext.isEmpty(invoiceHeadCode)||Ext.isEmpty(invoiceHeadName)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.invoiceTitleIsNullWarning"/>');
	 			return false;	
	 		}
	 		
	 	}
	 	//如果为临时租车，需要判断必输项
	 	if(!Ext.isEmpty(pay.containRentCar) && pay.containRentCar=='Y'){
	 		//判断费用承担部门不能为空
	 		if(Ext.isEmpty(costDeptCode)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.costDeptCodeNotNullWarning"/>');
	 			return false;	
	 		}
	 		//判断费用类型不能为空
			if(Ext.isEmpty(costType)){
				Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.costTypeNotNullWarning"/>');
	 			return false;	
	 		}
	 		//如果勾选了增值税发票，则发票号必输
	 		if(isTaxinvoice && Ext.isEmpty(taxinvoiceNo)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.taxinvoiceNoNotNullWarning"/>');
	 			return false;	
	 		}
	 		//如果勾选了增值税发票，则纳税人识别号必输
	 		if(isTaxinvoice && Ext.isEmpty(taxpayerNumber)){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.taxpayerNumberNotNullWarning"/>');
	 			return false;	
	 		}
	 		//发票号必须是8位
	 		if(!Ext.isEmpty(taxinvoiceNo) && taxinvoiceNo.length!=8){
	 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.taxinvoiceNoLengthWarning"/>');
	 			return false
	 		}
	 	}
	 	
		//保理校验    379106
	 	if(factoring=="Y"){
	 		var factorBeginTime = form.getForm().findField('factorBeginTime').getValue();
	 		var factorEndTime = form.getForm().findField('factorEndTime').getValue();
	 		var curDate=new Date();
	 		var time=Ext.Date.format(curDate, 'Y-m-d');	
	 		var beginResult = Ext.Date.parse(time,'Y-m-d') - Ext.Date.parse(factorBeginTime,'Y-m-d');
			var endResult = Ext.Date.parse(factorEndTime,'Y-m-d') - Ext.Date.parse(time,'Y-m-d');
			//判断是否在保理期间
			if(beginResult>=0&&endResult>=0){
				var factorAccount = form.getForm().findField('factorAccount').getValue();
				if(paymentType!=pay.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
					||factorAccount!=accountNo){
					Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.factor.valifactorAlert"/>');
			 		return false;
		 		}
			//不在保理期间 后台不传数据(后台根据factoring Y/N 来获取保理其他字段的值)
			}else{
				form.getForm().findField('factoring').setValue("N");
			}
	 	}
	 	
	 	Ext.Msg.confirm('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>',
					'付款金额： <font style ="color:red">'+amount+'；</font></br>费用承担部门为: <font style ="color:red">'+costDeptName+'；</font></br>请核实是否正确! 选择 "是"，则提交成功，选择"否"，则取消提交!',function(o){
			//提示是否要删除
			if(o=='yes'){
				//循环校验明细和总金额的值是否相等
			 	var jsonData = new Array();
			 	//此处定义id集合用于给回传给来源单据刷新界面
			 	var recordIDs = new Array();
			 	grid.store.each(function(record){
			 		jsonData.push(record.data);
			 		recordIDs.push(record.data.id);
			 	});
			 	//声明传入action参数
			 	var vo = new Object();
			 	//此处需要更新一下模型
			 	var record = form.getForm().getRecord();
			 	//设置发票抬头名称
			 	record.set('invoiceHeadName',invoiceHeadName);
			 	record.set('costDeptName',costDeptName);
				record.set('taxpayerNumber',taxpayerNumber);
			 	if(isTaxinvoice){
			 		record.set('isTaxinvoice','Y');
			 	}
			 	record.set('vatInvoice',taxinvoiceNo)
			 	form.getForm().updateRecord(record);
			 	
			 	//获取付款单头信息即界面form表单
			 	vo.paymentEntity = record.data;
			 	vo.addDtoList = jsonData;
			 	if(null != pay.batchNo){
			 		vo.paymentEntity.batchNo=pay.batchNo;
			 	}
			 	//遮罩窗口
			 	win.getLoadMask().show();
			 	//提交
			 	Ext.Ajax.request({
			 		url:ContextPath.STL_WEB + '/pay/savaBillPaymentInfo.action',
			 		actionMethods:'post',
			 		jsonData:{
			 			'vo':vo
			 		},
			 		success:function(response){
			 			//获取返回结果
			 			var result = Ext.decode(response.responseText);	
			 			//获取付款单号
			 			var paymentNo = result.vo.paymentEntity.paymentNo;
			 			//遮罩窗口
			 			win.getLoadMask().hide();
			 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>','<ext:i18nForJsp key="foss.stl.pay.addPayment.addPaymentBill"/>'+paymentNo+'<ext:i18nForJsp key="foss.stl.pay.addPayment.addPaymentSuccess"/>');
			 			//关闭窗口
			 			win.close();
			 			//刷新来源界面数据
			 			//获取来源单据界面类型
			 			var  sourceBillType = form.getForm().findField('sourceBillType').getValue();
			 			//如果为应付单，则刷新应付界面
			 			if(sourceBillType==pay.SOURCE_BILL_TYPE__ADVANCED_PAYMENT){
			 				//调用应付单刷新方法刷新
			 				pay.payable.refresh(recordIDs);
			 			}
			 		},
			 		exception:function(response){
			 			var result = Ext.decode(response.responseText);
			 			//隐藏掉遮罩
			 			win.getLoadMask().hide();
			 			//弹出异常提示
			 			Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.pay.common.alert"/>',result.message);
			 		},
			 		unknownException:function(form,action){
			 			//隐藏掉遮罩
			 			win.getLoadMask().hide();
			 		},
			 		failure:function(form,action){
			 			//隐藏掉遮罩
			 			win.getLoadMask().hide();
			 		}
			 	});
			}
	 	})
	 }
	
	 /**
	  * form的model
	  */
	 Ext.define('Foss.payable.AddFormModel',{
	 	extend:'Ext.data.Model',
	 	fields:[{
	 		name:'customerCode'
	 	},{
	 		name:'customerName'
	 	},{
	 		name:'paymentOrgCode'
	 	},{
	 		name:'paymentOrgName'
	 	},{
	 		name:'paymentCompanyCode'
	 	},{
	 		name:'paymentCompanyName'
	 	},{
	 		name:'paymentType'
	 	},{
	 		name:'amount',
	 		type:'double'
	 	},{
	 		name:'accSort'
		},{
	 		name:'factoring'
		},{
		 	name:"cusCode"
		},{
		 	name:"isfactoring"
		},{
		 	name:'factorBeginTime',
		 	type:'date',
		 	convert: function(value) {
		 			if (value != null&&value!="") {
		 				 var createTime = Ext.Date.format(new Date(value),"Y-m-d");
		 	             return createTime;  
		 			} else {
		 				return null;
		 			}
		 		}
		},{
		 	name:'factorEndTime',
		 	type:'date',
		 	convert: function(value) {
		 			if (value != null&&value!="") {
		 			   var createTime = Ext.Date.format(new Date(value),"Y-m-d");
		 	             return createTime;  
		 			} else {
		 				return null;
		 		}
		 	}
		},{
		 	name:'factorAccount'
		},{
	 		name:'accountNo'
	 	},{
	 		name:'provinceCode'
	 	},{
	 		name:'provinceName'
	 	},{
	 		name:'cityName'
	 	},{
	 		name:'cityCode'
	 	},{
	 		name:'bankHqCode'
	 	},{
	 		name:'bankHqName'
	 	},{
	 		name:'bankBranchName'
	 	},{
	 		name:'bankBranchCode'
	 	},{
	 		name:'accountType'
	 	},{
	 		name:'invoiceHeadCode'
	 	},{
	 		name:'invoiceHeadName'
	 	},{
	 		name:'payeeName'
	 	},{
	 		name:'payeeCode'
	 	},{
	 		name:'mobilePhone'
	 	},{
	 		name:'notes'
	 	},{
 			name:'sourceBillType'
 		},{
	 		name:'costDeptCode'
	 	},{
	 		name:'costDeptName'
	 	},{
	 		name:'costType'
	 	},{
	 		name:'isTaxinvoice'
	 	},{
	 		name:'vatInvoice'
	 	}]
	 });
	 /**
	  * grid的model
	  */
	 Ext.define('Foss.payable.PaybleGridModel',{
	 	extend:'Ext.data.Model',
	 	fields:[{
	 		name:'id'
	 	},{
	 		name:'payableNo'
	 	},{
	 		name:'billType'
	 	},{
	 		name:'amount',
	 		type:'double'
	 	},{
	 		name:'verifyAmount',
	 		type:'double'
	 	},{
	 		name:'unverifyAmount',
	 		type:'double'
	 	},{
	 		name:'currentPaymentAmount',
	 		type:'double'
	 	},{
	 		name:'taxAmount',
	 		type:'double'
	 	},{
	 		name:'tax',
	 		type:'double'
	 	},{
	 		name:'notes'
	 	},{
	 		name:'isEdit',
	 		type:'int',
	 		dafaultValue:0
	 		
	 	},{
	 		name:'businessOfDate',
	 		type:'date',
	 		convert: function(value) {
	 			if (value != null) {
	 				var date = new Date(value);
	 				return date;
	 			} else {
	 				return null;
	 			}
	 		}
	 	},{
	 		name:'accountDate',
	 		type:'date',
	 		convert: function(value) {
	 			if (value != null) {
	 				var date = new Date(value);
	 				return date;
	 			} else {
	 				return null;
	 			}
	 		}
	 	},{
	 		name:'versionNo'
	 	}]
	 });
	 /**
	  * grid的store
	  */
	 Ext.define('Foss.payable.PaybleGridStore',{
	 	extend:'Ext.data.Store',
	 	model:'Foss.payable.PaybleGridModel'
	 });
	 /**
	  * 录入付款单form
	  */
	 Ext.define('Foss.payment.AddFormPanel',{
	 	extend:'Ext.form.Panel',
	 	title:'<ext:i18nForJsp key="foss.stl.pay.addPayment.addPaymentBill"/>',
	 	frame:true,
	 	bodyCls: 'autoHeight',
	 	cls: 'autoHeight',
	 	defaults:{
	 		labelWidth:95,
	 		readOnly:true,
	 		columnWidth:.3
	 	},
	 	defaultType:'textfield',
	 	layout:'column',
	 	items:[{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.customerCode"/>',
	 		name:'customerCode'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.customerName"/>',
	 		name:'customerName'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.payableOrgName"/>',
	 		name:'paymentOrgName'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.payableOrgCode"/>',
	 		name:'paymentOrgCode',
	 		hidden:true
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.company"/>',
	 		name:'paymentCompanyName'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.payableCompanyCode"/>',
	 		name:'paymentCompanyCode',
	 		hidden:true
	 	},{
	 		xtype:'combobox',
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.paymentType"/>',
	 		name:'paymentType',
	     	editable:false,
	 		store:null,
	 		queryMode:'local',
	 		displayField:'valueName',
	 		valueField:'valueCode',
	     	columnWidth:.3,
	     	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,[pay.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,pay.SETTLEMENT__PAYMENT_TYPE__CASH]),
	     	readOnly:false,
	 		value:pay.SETTLEMENT__PAYMENT_TYPE__CASH,
	 		listeners:{
	 			'change':function(th,newValue,oleValue){
	 				//获取表单等控件
	 				var form,//表单
	 					accountNo,//银行账号
	 					provinceName,//开户行省份
	 					cityName,//开户行城市
	 					bankHqCode,//开户银行
	 					bankBranchName,//开户行支行
	 					bankBranchCode,//行号
	 					accountType,//账户类型
	 					mobilePhone,//手机号
	 					invoiceHeadCode,//发票抬头
	 					claimAccountNo,//理赔账号
	 					uploadddw
	 					;
	 				//获取表单	
	 				form= this.up('form').getForm();
	 				//获取下面组件
	 				accountNo = form.findField('accountNo');
	 				provinceName = form.findField('provinceName');
	 				cityName = form.findField('cityName');
	 				bankHqCode = form.findField('bankHqName');
	 				bankBranchName = form.findField('bankBranchName');
	 				bankBranchCode = form.findField('bankBranchCode');
	 				accountType = form.findField('accountType');
	 				accountTypeName = form.findField('accountTypeName');
	 				mobilePhone = form.findField('mobilePhone');
	 				invoiceHeadCode = form.findField('invoiceHeadCode');
	 				invoiceHeadCode = form.findField('invoiceHeadCode');
	 				claimAccountNo = form.findField('claimAccountNo');
	 				payToSystem = FossDataDictionary.getDataByTermsCode('SETTLEMENT__PAYTOSYSTEM_TYPE')[0].valueCode;
	 				uploadddw=this.up('form').down('button');
	 				isTaxinvoice = form.findField('isTaxinvoice');
	 				taxinvoiceNo = form.findField('taxinvoiceNo');
					
	 				//当选择现金时，隐藏银行账号等控件
	 				if(newValue==pay.SETTLEMENT__PAYMENT_TYPE__CASH){
	 					accountNo.hide();
	 					provinceName.hide();
	 					cityName.hide();
	 					bankHqCode.hide();
	 					bankBranchName.hide();
	 					bankBranchCode.hide();
	 					accountTypeName.hide();
	 					mobilePhone.hide();
	 					invoiceHeadCode.hide();
	 					claimAccountNo.hide();
	 					uploadddw.hide();
	 					if(!Ext.isEmpty(pay.containRentCar)){
	 						isTaxinvoice.show();
	 					}
	 					if(Ext.isEmpty(claimAccountNo.getValue())){
	 						//清空值
	 	 					form.findField('provinceName').setValue(null);
	 	 					form.findField('provinceCode').setValue(null);
	 	 					form.findField('cityName').setValue(null);
	 	 					form.findField('cityCode').setValue(null);
	 	 					form.findField('bankHqCode').setValue(null);
	 	 					form.findField('bankHqName').setValue(null);
	 	 					form.findField('bankBranchName').setValue(null);
	 	 					form.findField('bankBranchCode').setValue(null);
	 	 					form.findField('accountType').setValue(null);
	 	 					form.findField('accountTypeName').setValue(null);
	 	 					form.findField('payeeName').setValue(null);
	 	 					form.findField('payeeCode').setValue(null);
	 	 					form.findField('mobilePhone').setValue(null);
	 	 					form.findField('accountNo').setValue(null);
	 					}
	 				}else{
	 					accountNo.show();
	 					provinceName.show();
	 					cityName.show();
	 					bankHqCode.show();
	 					bankBranchName.show();
	 					bankBranchCode.show();
	 					accountTypeName.show();
	 					mobilePhone.show();
	 					invoiceHeadCode.show();
		 				isTaxinvoice.hide();
		 				isTaxinvoice.setValue(false);
		 				taxinvoiceNo.hide();
	 					if(null != payToSystem){
	 						if('Y' == payToSystem){
	 	 						uploadddw.show();
	 	 					}
	 					}
	 					if(!Ext.isEmpty(claimAccountNo.getValue())){
	 						claimAccountNo.show();
	 					}
	 				}
	 			}
	 		}
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.factoring"/>',
	 		name:'factoring',
	 		hidden:true
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.factoring"/>',
	 		name:'isfactoring'
	 	},{ 
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.cusCode"/>',
	 		name:'cusCode',
	 		hidden:true
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.factorBeginTime"/>',
	 		name:'factorBeginTime'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.factorEndTime"/>',
	 		name:'factorEndTime'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.factor.factorAccount"/>',
	 		name:'factorAccount'
	 	},{
	 		xtype:'numberfield',
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.amount"/>',
	 		name:'amount',
	 		allowBlank:false
	 	},{
	 		xtype:'commoncostcenterdeptSelector',
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.rentCarForPayReport.costDept"/>',
			name:'costDeptCode',
			labelWidth:110,
			hidden:true,
			readOnly:false,
			listWidth:300,//设置下拉框宽度
			isPaging:true,
			listeners:{
				'change':function(th,newValue,oldValue,opts){
					if(!Ext.isEmpty(th.getValue()) && !Ext.isEmpty(th.valueModels)){
			 			//如果为应付单，则刷新应付界面
			 			if(!Ext.isEmpty(pay.payFromPage) && pay.payFromPage==pay.SOURCE_BILL_TYPE__ADVANCED_PAYMENT){
			 				//复制一份费用类型
							var costType = th.up('form').getForm().findField('costType');
							//获取费用类型
							var costTypeStore = th.up('form').getForm().findField('costType').store;
							costTypeStore.removeAll();costType.setValue(null);
							//部门类型
							var deptType = th.valueModels[0].get('typeCode');
							var targetSore = pay.payable.getCostTypeDatas(deptType);
							//如果为经营，则需要删除
							costTypeStore.loadData(targetSore);
			 			}
					}
				}
			}
	 	},{
	 		xtype : 'combobox',
			name : 'costType',
			editable:false,
			hidden:true,
			readOnly:false,
			fieldLabel : '<ext:i18nForJsp key="foss.stl.pay.rentCarForPayReport.costType"/>',
			store : FossDataDictionary.getDataDictionaryStore('RENTCAR_COST_TYPE'),
			queryMode : 'local',
			displayField : 'valueName',
			valueField : 'valueCode'
	 	},{
			xtype : 'displayfield',
			hidden:true,
			name:'displayfield1',
			columnWidth : .3,
			height:24
		},{
	 		xtype:'checkbox',
	 		height:24,
	 		allowBlank:true,
	 		hidden:true,
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.isTaxinvoice"/>',
	 		readOnly:false,
	 		labelWidth:130,
	 		name:'isTaxinvoice',
	 		listeners:{
	 			'change':function( th,newValue,oldValue,eOpts){
	 				var taxinvoiceNo = this.up('form').getForm().findField('taxinvoiceNo');
	 				var taxpayerNumber = this.up('form').getForm().findField('taxpayerNumber');
	 				if(newValue){
	 					taxinvoiceNo.show();
	 					taxpayerNumber.show();
	 				}else{
	 					taxinvoiceNo.hide();
	 					taxinvoiceNo.setValue(null);
	 					taxpayerNumber.hide();
	 					taxpayerNumber.setValue(null);
	 				}
	 			}
	 		}
	 	},{
	 		xtype:'textfield',
	 		hidden:true,
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.taxinvoiceNo"/>',
	 		name:'taxinvoiceNo',
	 		readOnly:false,
	 		labelWidth:120,
	 		maxLength:8,
	 		maxText:'<ext:i18nForJsp key="foss.stl.pay.addPayment.taxinvoiceNoWarning"/>'
	 	},{
	 		xtype:'textfield',
	 		hidden:true,
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.taxpayerNumber"/>',
	 		regex:/^(\d|[A-Z]|-){0,30}$/,
	 		regexText:'<ext:i18nForJsp key="foss.stl.pay.addPayment.taxpayerNumberRegex"/>',
	 		name:'taxpayerNumber',
	 		readOnly:false,
	 		labelWidth:100
	 	},{
	 		xtype:'textfield',
	 		readOnly:false,
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.mobilePhone"/>',
	 		hidden:true,
	 		regex:/^1[3|4|5|7|8][0-9]\d{8}$/,
	 		regexText:'<ext:i18nForJsp key="foss.stl.pay.addPayment.mobilePhoneRegex"/>',
	 		name:'mobilePhone',
	 		allowBlank:false
	 	},{
	 		xtype:'commonsubsidiaryselector',
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.invoiceHeadCode"/>',
	 		readOnly:false,
	 		hidden:true,
	 		name:'invoiceHeadCode',
	 		allowBlank:false
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.accountNo"/>',
	 		xtype:'commonpayeeinfoselector',
			accountTypes:pay.FIN_ACCOUNT_TYPE_PUBLIC+","+pay.FIN_ACCOUNT_TYPE_PRIVATE+","+pay.FIN_ACCOUNT_TYPE_DPSON,
			accSort:'保理类',
	 		operatorId:FossUserContext.getCurrentUserEmp().empCode,
	 		isOnlyPartnerAccount:'N',
	 		hidden:true,
	 		name:'accountNo',
	 		exactQuery:'Y', //精确查找
	 		allowBlank:false,
	 		disabled:false,
	 		readOnly:false,
	 		listeners:{
	 			'change':function(th,newValue,oldValue){
	 				//获取record
	 				var record = th.findRecordByValue(newValue);
	 				//获取form表单
	 				var form = this.up('form').getForm();
	 				if(!record){
	 					form.findField('provinceName').setValue(null);
	 					form.findField('provinceCode').setValue(null);
	 					form.findField('cityName').setValue(null);
	 					form.findField('cityCode').setValue(null);
	 					form.findField('bankHqCode').setValue(null);
	 					form.findField('bankHqName').setValue(null);
	 					form.findField('bankBranchName').setValue(null);
	 					form.findField('bankBranchCode').setValue(null);
	 					form.findField('accountType').setValue(null);
	 					form.findField('accountTypeName').setValue(null);
	 					form.findField('payeeName').setValue(null);
	 					form.findField('payeeCode').setValue(null);
					}
	 			},
	 			'select':function(th,records){
	 				//获取选中记录
	 				var record = records[0];
	 				//获取form表单
	 				var form = this.up('form').getForm();
	 				//判断是否选中记录为空
	 				if(!Ext.isEmpty(record)){
	 					form.findField('provinceName').setValue(record.get('accountbankstateName'));
	 					form.findField('provinceCode').setValue(record.get('accountbankstateCode'));
	 					form.findField('cityName').setValue(record.get('accountbankcityName'));
	 					form.findField('cityCode').setValue(record.get('accountbankcityCode'));
	 					form.findField('bankHqCode').setValue(record.get('accountbankCode'));
	 					form.findField('bankHqName').setValue(record.get('accountbankName'));
	 					form.findField('bankBranchName').setValue(record.get('accountbranchbankName'));
	 					form.findField('bankBranchCode').setValue(record.get('accountbranchbankCode'));
	 					form.findField('accountType').setValue(record.get('accountType'));
	 					form.findField('accountTypeName').setValue(FossDataDictionary. rendererSubmitToDisplay (record.get('accountType'),settlementDict.FIN_ACCOUNT_TYPE));
	 					form.findField('payeeName').setValue(record.get('beneficiaryName'));
	 					form.findField('payeeCode').setValue(record.get('payeeNo'));
	 				}
	 			}
	 		}
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.claimAccount"/>',
	 		name:'claimAccountNo',
	 		hidden:true
	 	},{
	 		fieldLabel:'provinceCode',
	 		hidden:true,
	 		name:'provinceCode'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.provinceName"/>',
	 		hidden:true,
	 		name:'provinceName'
	 	},{
	 		fieldLabel:'cityCode',
	 		hidden:true,
	 		name:'cityCode'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.cityName"/>',
	 		hidden:true,
	 		name:'cityName'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.bankHqName"/>',
	 		hidden:true,
	 		name:'bankHqName'
	 	},{
	 		fieldLabel:'bankHqCode',
	 		hidden:true,
	 		name:'bankHqCode'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.bankBranchName"/>',
	 		hidden:true,
	 		name:'bankBranchName'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.bankBranchCode"/>',
	 		hidden:true,
	 		name:'bankBranchCode'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.accountType"/>',
	 		hidden:true,
	 		name:'accountType'
	 	},{
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.common.accountType"/>',
	 		hidden:true,
	 		name:'accountTypeName'
	 	},{
	 		fieldLabel:'payeeName',
	 		hidden:true,
	 		name:'payeeName'
	 	},{
	 		fieldLabel:'payeeCode',
	 		hidden:true,
	 		name:'payeeCode'
	 	},{
	 		xtype:'textareafield',
	 		columnWidth:.9,
	 		height:60,
	 		disabled:false,
	 		readOnly:false,
	 		autoScroll:true,
	 		fieldLabel:'<ext:i18nForJsp key="foss.stl.pay.addPayment.notes"/>'	,
	 		name:'notes'
	 	},{
	 		border: 1,
	 		xtype:'container',
	 		columnWidth:1,
	 		disabled:false,
	 		readOnly:false,
	 		defaultType:'button',
	 		layout:'column',
	 		items:[{
	 			text:'<ext:i18nForJsp key="foss.stl.pay.common.upload"/>',
	 			name:'uploadddw',
	 			columnWidth:.1,
	 			hidden:true,
	 			handler:pay.addPaymentUpload
	 		},{
	 			xtype:'container',
	 			border:false,
	 			html:'&nbsp;',
	 			columnWidth:.6
	 		},{
	 			text:'<ext:i18nForJsp key="foss.stl.pay.common.commit"/>',
	 			columnWidth:.1,
	 			handler:pay.addPaymentCommit
	 		},{
	 			text:'<ext:i18nForJsp key="foss.stl.pay.common.cancel"/>',
	 			columnWidth:.1,
	 			handler:pay.addPaymentCancel
	 		}]
	 	}]
	 });
	
	 //编辑器
	 pay.addPaymentEntryGridEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
	   	clicksToEdit : 1,
	   	isObservable : false,
	   	listeners:{
			'beforeedit':function(edit,e,object){
				if(e.column.dataIndex=='businessOfDate' && pay.businessOfDateEdit=='Y'){
					return false;
				}else{
					return true;
				}
			}
		}
	 }) ;
	
	 /**
	  * 声明付款单明细grid
	  */
	 Ext.define('Foss.payment.AddPaymentEntryGrid', {
	 	extend:'Ext.grid.Panel',
	     frame:true,
	     title:'<ext:i18nForJsp key="foss.stl.pay.addPayment.entry"/>',
	     height:400,
	     bodyCls: 'autoHeight',
	 	cls: 'autoHeight',
	 	plugins:pay.addPaymentEntryGridEditing,
	 	emptyText:'<ext:i18nForJsp key="foss.stl.pay.common.noResult"/>',
	     store:Ext.create('Foss.payable.PaybleGridStore'),
	   	defaults:{
	   		align:'center'
	   	},
	   	viewConfig : {  
	   		enableTextSelection : true,
	         stripeRows: false,
	         getRowClass : function(record,rowIndex,rowParams,store){ 
	             //禁用数据显示红色  
	             if(record.data.isEdit==1){  
	                 return 'x-grid-record-red';  
	             }else{  
	                 return '';  
	             }  
	               
	         }  
	     },
	     columns: [{
	     	xtype:'actioncolumn',
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.actionColumn"/>',
	     	width:70,
	     	align: 'center',
	     	items:[{
	 			iconCls : 'deppon_icons_delete',
	 			tooltip:'<ext:i18nForJsp key="foss.stl.pay.addPayment.delete"/>',
	 			handler:function(grid, rowIndex, colIndex){
	 				pay.addPaymentGridEntryRemove(grid, rowIndex, colIndex);
	 			}
	     	}]
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.payableNo"/>',
	     	dataIndex:'payableNo'
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.billType"/>',
	     	dataIndex:'billType',
	     	renderer:function(value){
	     		//获取应付单单据子类型
	     		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_PAYABLE__BILL_TYPE);
	     		//如果应付单不存在，则获取预收单进行转化
	     		if(displayField==value){
	     			displayField =FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
	     		}																	  		
	     		return displayField;
	     	}
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.amount"/>',
	     	dataIndex:'amount'
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.common.verifyAmount"/>',
	     	dataIndex:'verifyAmount'
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.common.unverifyAmount"/>',
	     	dataIndex:'unverifyAmount'
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.currentPaymentAmount"/>',
	     	dataIndex:'currentPaymentAmount',
	     	editor:{
	     		xtype:'numberfield'
	     	}
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.taxAmount"/>',
	     	dataIndex:'taxAmount',
	     	width:100,
	     	editor:{
	     		xtype:'numberfield'
	     	}
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.tax"/>',
	     	width:100,
	     	dataIndex:'tax'
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.businessDate"/>',
	     	dataIndex:'businessOfDate',
	     	editor:{
	     		xtype:'datefield',
	     		format:'Y-m'
	     	},
	     	renderer : function(value) {
				return stl.dateFormat(value, 'Y-m');
			}
	     },{
	     	header:'<ext:i18nForJsp key="foss.stl.pay.addPayment.notes"/>',
	     	dataIndex:'notes',
	     	width:200,
	     	editor:{
	     		xtype:'textfield'
	     	}
	     }],
	 	listeners:{
	 		'edit':function(th,e){
	 			pay.addPaymentGridEidt(th,e);
	 		}
	 	}
	 });
	
	 Ext.onReady(function(){
	 	//创建表单
	 	var addFormPanel = Ext.create('Foss.payment.AddFormPanel');
	 	var addPaymentEntryGrid = Ext.create('Foss.payment.AddPaymentEntryGrid');
	 	
	 	//声明录入付款单window
	 	Ext.define('Foss.payment.AddPaymentWindow',{
	 		extend:'Ext.window.Window',
	 		title:'<ext:i18nForJsp key="foss.stl.pay.addPayment.addPayment"/>',
	 		width:stl.SCREENWIDTH*0.7,
	 		modal:true,
	 		constrainHeader: true,
	 		closeAction:'hide',
	 		loadMask:null,//遮罩
	 		getLoadMask:function(){
	 			var me = this;
	 			//获取遮罩效果
	 			if(Ext.isEmpty(me.loadMask)){
	 				me.loadMask = new Ext.LoadMask(me, {
	 					msg:'<ext:i18nForJsp key="foss.stl.pay.addPayment.savePaymentMask"/>',
	 				    removeMask : true// 完成后移除
	 				});
	 			}
	 			return me.loadMask;
	 		},
	 		getAddFormPanel:function(){
	 			return addFormPanel;
	 		},
	 		getAddPaymentEntryGrid:function(){
	 			return addPaymentEntryGrid;
	 		},
	 		listeners:{
	 			'close':function(){
	 				pay.batchNo = null;
	 			}
	 		},
	 		items:[addFormPanel,addPaymentEntryGrid]
	 	});
	 });
	
	
	</script>
	
