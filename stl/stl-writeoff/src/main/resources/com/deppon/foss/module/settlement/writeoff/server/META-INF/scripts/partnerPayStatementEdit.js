writeoff.partnerPayStatementEdit.pageType=false;

/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
writeoff.partnerPayStatementEdit.statementConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//开发环境地址是：http://192.168.17.221:8881/fssc
//测试环境UAT地址是：http://192.168.20.251:8080/fssc
//测试环境SIT地址是：http://192.168.20.148:8080/fssc
//http://192.168.11.59:8881/claim/attachment/query.action
writeoff.partnerPayStatementEdit.fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
//附件查询地址
var attachmentQueryUrl = 'http://'+writeoff.partnerPayStatementEdit.fssc+'/fssc/attachment/query.action';
//附件下载地址
var attachmentDownLoadUrl = 'http://'+writeoff.partnerPayStatementEdit.fssc+'/fssc/attachment/download.action';
//附件删除地址
var attachmentDeleteUrl = 'http://'+writeoff.partnerPayStatementEdit.fssc+'/fssc/attachment/delete.action';
//附件上传地址
var attachmentUploadUrl = 'http://'+writeoff.partnerPayStatementEdit.fssc+'/fssc/attachment/upload.action';

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
      callbackKey: 'queryAttachment',
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
              Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), data.message);
              return;
          }
          if (upfilegridstore) {
              upfilegridstore.loadPage(1);
          }
      },
      failure : function() {
          Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.serverErrTryAgain'));
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
      var href2 = Ext.String.format('[<a href=\"javascript:deleteFile(\'{0}\'' + ',\'{1}\')\">{2}</a>]', record.data.id, record.data.attachguid, writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.delete'));
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
          text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.sequence'),
          width : 50
      }, {
          text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.attachmentName'),
          dataIndex : 'attachguid',
          width : 300,
          flex : 1,
          renderer : me.downLoadFile
      }, {
          text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.attachmentSize'),
          dataIndex : 'attachsize',
          width : 100
      }, {
          xtype : 'actioncolumn',
          text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.operate'),
          iconCls : 'deppon_icons_delete',
          width : 100,
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
//设置grid中各列内容可复制
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
//选择上传文件
Ext.define('Fssc.common.upFileForm', {
  extend : 'Ext.form.Panel',
  alias : 'widget.upfileform',
  standardSubmit: true,
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
          Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.registerFormAndUploadAttachment'));
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
              Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.uploadFileFailure'));
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
              //labelWidth : 60,
              width : 350,
              fieldLabel : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.uploadAttachment'),
              fieldStyle : 'border: solid 1px #B5B8C8!important;background: transparent !important;',
              buttonConfig : {
                  text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.browser')
              },
              blankText : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.notNull'),
              allowBlank : false
          }, {
              text : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.upload'),
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

Ext.define('Foss.partnerPayStatementEdit.uploadWindow', {
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
			writeoff.partnerPayStatementEdit.upfilepanel = this.upfilepanel;
		}
		return this.upfilepanel;
	},
	constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.items = [me.getUpfilepanel()];
        me.callParent([cfg]);
    }
});

/**
 * 上传附件
 */
writeoff.partnerPayStatementEdit.paymentUpload = function(){
	writeoff.partnerPayStatementEdit.uploadWindow =Ext.create('Foss.partnerPayStatementEdit.uploadWindow');
	writeoff.partnerPayStatementEdit.upfilepanel.setUserCode(writeoff.partnerPayStatementEdit.userCode);
	writeoff.partnerPayStatementEdit.upfilepanel.setAttachRelId(writeoff.partnerPayStatementEdit.batchNo);
	writeoff.partnerPayStatementEdit.uploadWindow.show();
}


/**
 * 导出对账单
 */
writeoff.partnerPayStatementEdit.statementExportExcel = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames;
	//对账单列表
	var statementGrid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	var selections = statementGrid.getSelectionModel().getSelection();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.export'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(selections.length==0){
		  		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedToExport'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = statementGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=2;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						arrayColumns.push(dataIndex);
						arrayColumnNames.push(hederName);
					}
				}
				var statementBillNos = new Array();
				//循环加入选中对账单号
				for(var i=0; i < selections.length; i++){
					statementBillNos.push(selections[i].data.statementBillNo);
				}
				
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				me.disable(false);
				//10秒后自动解除灰掉效果
				setTimeout(function() {
					me.enable(true);
				}, 10000);	
				
				//拼接vo，注入到后台
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.statementBillNos':statementBillNos,
				    'partnerPayStatementVo.arrayColumns':arrayColumns,
				    'partnerPayStatementVo.arrayColumnNames':arrayColumnNames
				};
				Ext.Ajax.request({
				    url: writeoff.realPath('partnerPayExportExl.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportSuccess'));
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * 导出对账单PDF
 */
/*writeoff.partnerPayStatementEdit.statementExportPdf = function(){
	var selections,
		arrayColumns,
		arrayColumnNames,
		me;
	
	//获取选取行数
	selections = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	me = this;
	//判断是否选中1条
	if(selections.length!=1){
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedToExport'));
		return false;
	}
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	
	
	//默认显示列 ddw
	var arrayColumns = ['payableNo','waybillNo','billType','amount',
						'orgCode','orgName','customerCode','customerName',
						'paymentType','receiveMethod','productCode','accountDate',
						'signDate','regionalTransportFee','deliverFee','dispatchFee',
						'deliveryWarehouseFee','signReturnFee','deliveryUpstairsFee',
						'bigUpstairsFee','farDeliveryFee'];

	//默认显示列名称
	arrayColumnNames = [writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.payableNo'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.billType'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.amount'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.regionalTransportFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.dispatchFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryWarehouseFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.signReturnFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryUpstairsFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.bigUpstairsFee'),
	                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.farDeliveryFee')];
	me.disable(false);
	//10秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 10000);	
	//拼接vo，注入到后台
	Ext.Ajax.request({
	    url:writeoff.realPath('exportStatementPdf.action'),
	    form: Ext.fly('downloadAttachFileForm'),
	    method : 'POST',
	    params : {
		    'partnerPayStatementVo.arrayColumns':arrayColumns,
		    'partnerPayStatementVo.arrayColumnNames':arrayColumnNames,
			'partnerPayStatementVo.statementofAccountStr':Ext.JSON.encode(selections[0].data)
	    },
	    isUpload: true,
	    success : function(response,options){
	    	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.exportSuccess'));
	    },
	    failure : function(response,options){
	    	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportFailed'));
	    }
	});
}*/


/**
 * 导出明细对账单
 */
writeoff.partnerPayStatementEdit.statementEntryExportExcel = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames;
	//对账单列表
	var statementGrid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	var statementEntryGrid = writeoff.partnerPayStatementEdit.partnerPayStatementEditGrid;
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportEntry'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(statementEntryGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = statementEntryGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=2;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						arrayColumns.push(dataIndex);
						arrayColumnNames.push(hederName);
					}
				}
				var statementBillNo = statementGrid.getSelectionModel().getSelection()[0].get("statementBillNo");
				
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				me.disable(false);
				//10秒后自动解除灰掉效果
				setTimeout(function() {
					me.enable(true);
				}, 10000);	
				//拼接vo，注入到后台
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.statementBillNo':statementBillNo,
				    'partnerPayStatementVo.arrayColumns':arrayColumns,
				    'partnerPayStatementVo.arrayColumnNames':arrayColumnNames
				};
				Ext.Ajax.request({
				    url: writeoff.realPath('partnerPayEntryExportExl.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportSuccess'));
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}


/**
* 付款界面公用显示方法
*/
writeoff.partnerPayStatementEdit.statementPaymentSuccess = function(paymentBillWindow,selections,payType ,totalAmount){
	var win = paymentBillWindow;
	var paymentBaseInfoForm = win.items.items[0];
	var operateForm = win.items.items[1];
	//对账单界面
	if(payType==writeoff.PAGEFROM_STATEMENTEDIT){
		//金额
		paymentBaseInfoForm.getForm().findField('amount').setValue(totalAmount);
	}
	//明细界面
	if(payType==writeoff.PAGEFROM_STATEMENTENTRY){
		var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo;
		//金额
		var amount = baseInfoForm.getForm().findField("periodPayAmount").getValue();
		paymentBaseInfoForm.getForm().findField('amount').setValue(amount);
	}
	//合伙人编码
	paymentBaseInfoForm.getForm().findField('customerCode').setValue(selections[0].customerCode);
	//合伙人名称
	paymentBaseInfoForm.getForm().findField('customerName').setValue(selections[0].customerName);
	//部门编码
	paymentBaseInfoForm.getForm().findField('paymentOrgCode').setValue(selections[0].createOrgCode);
	//部门名称
	paymentBaseInfoForm.getForm().findField('paymentOrgName').setValue(selections[0].createOrgName);
	//公司编码
	paymentBaseInfoForm.getForm().findField('paymentCompanyCode').setValue(selections[0].companyCode);
	//子公司
	paymentBaseInfoForm.getForm().findField('paymentCompanyName').setValue(selections[0].companyName);
	paymentBaseInfoForm.getForm().findField('sourceBillType').setValue(writeoff.SOURCE_BILL_TYPE__STATEMENT);
	//对账单子类型
	var billType = selections[0].billType;
	//对账单子类型不为委托派费，显示发票信息
	if(billType != writeoff.STATEMENTTYPE_PARTNER__DELEGATION_DELIVERY_FEE){
//		operateForm.items.items[1].show();
		writeoff.partnerPayStatementEdit.myPanel.show();
	}else{
		writeoff.partnerPayStatementEdit.myPanel.hide();
		//隐藏上传附件按钮
//		operateForm.items.items[1].hide();
	}
	//将数据传递给后台
	Ext.Ajax.request({
		url:ContextPath.STL_WEB + '/pay/uploadAppendix.action',
		success:function(response){
				var result = Ext.decode(response.responseText);
				writeoff.partnerPayStatementEdit.batchNo = result.billPaymentVo.paymentEntity.batchNo;
				writeoff.partnerPayStatementEdit.userCode = result.billPaymentVo.paymentEntity.createUserCode;
		}
	});
	
	win.show();
}

/**
 * 对账单确认/反确认
 */
writeoff.partnerPayStatementEdit.statementConfirm = function(payType){
	var yesFn=function(){
		writeoff.partnerPayStatementEdit.statementConfirmChange(payType);
	}
	var noFn=function(){
	 	return false;
	};
	writeoff.partnerPayStatementEdit.statementConfirmAlert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.isConfirm'),yesFn,noFn);
}

/**
 * 对账单确认/反确认(公用方法)
 */
writeoff.partnerPayStatementEdit.statementConfirmChange = function(payType){
	//此处如果加上确认弹窗，则会影响后续弹窗效果（隐藏在页面后面）
	if(!writeoff.partnerPayStatementEdit.orgCodeInspect()){
		return false;
	}
	
	var partnerPayStatementVo = new Object();
	var partnerPayStatementDto = new Object();
	//获取明细界面基本信息
	var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo;
	var operateForm = writeoff.partnerPayStatementEdit.operateButtonPanel;
	var status = baseInfoForm.getForm().findField("statementStatus").getValue();
	var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	var selection=grid.getSelectionModel().getSelection();
	var statementStatus;
	//明细界面
	if(payType == writeoff.PAGEFROM_STATEMENTENTRY){
		statementStatus = status;
	}else{
		statementStatus = selection[0].get('statementStatus');
	}
	var number = selection[0].get('statementBillNo');
	if(number==null || number ==''){
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementNumberIsNullWarning'));
		return false;
	}
	//获取传入后台参数
	partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
	partnerPayStatementVo.partnerPayStatementDto.confirmStatus = statementStatus;
	partnerPayStatementVo.partnerPayStatementDto.statementBillNo = number;
	//访问后台
	Ext.Ajax.request({
		url:writeoff.realPath('partnerConfirmStatement.action'),
		jsonData:{
			'partnerPayStatementVo':partnerPayStatementVo
		},
		success:function(response){
			//明细页面过来的
			if(payType == writeoff.PAGEFROM_STATEMENTENTRY){
				if(status != writeoff.STATEMENTCONFIRMSTATUS_Y){
					writeoff.partnerPayStatementEdit.addEntryGrid.hide();
					//设置确认状态的值
					baseInfoForm.getForm().findField("statementStatus").setValue(writeoff.STATEMENTCONFIRMSTATUS_Y);
					baseInfoForm.getForm().findField("statementStatus").setRawValue(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.comfirmed'));
					//隐藏确认按钮
					operateForm.items.items[2].hide();
					//显示反确认按钮
					operateForm.items.items[3].show();
				}else{
					//设置反确认状态的值
					baseInfoForm.getForm().findField("statementStatus").setValue(writeoff.STATEMENTCONFIRMSTATUS_N);
					baseInfoForm.getForm().findField("statementStatus").setRawValue(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.unConfirm'));
					//显示确认按钮
					operateForm.items.items[2].show();
					//隐藏反确认按钮
					operateForm.items.items[3].hide();
				}
				//设置确认时间
				baseInfoForm.getForm().findField("confirmTime").setValue(new Date());
			}else{
				//对账单页面
	 			 grid.store.load(); 
			}
			
			Ext.ux.Toast.msg(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}


/**
 * 取消操作
 */
writeoff.partnerPayStatementEdit.paymentCancel = function(){
	var win = this.up('window');
	Ext.Msg.confirm(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.isExistPaymentPage'),function(o){
		//提示是否要删除
		if(o=='yes'){
			if(!Ext.isEmpty(win)){
				writeoff.partnerPayStatementEdit.paymentBaseInfoPanel.getForm().reset();
				win.hide();
			}
		}
	});
}


/**
 * 关闭明细添加
 */
writeoff.partnerPayStatementEdit.cancelAddEntry = function(){
	var yesFn=function(){
		writeoff.partnerPayStatementEdit.addEntryGrid.hide();
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.partnerPayStatementEdit.statementConfirmAlert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.confirmToClose'),yesFn,noFn);
}


/**
 * 批量付款
 */
writeoff.partnerPayStatementEdit.statementPaymentBatch = function(){
	writeoff.partnerPayStatementEdit.statementPaymentEdit(writeoff.PAGEFROM_STATEMENTEDIT);
}


/**
 * 查询成功后，后续处理方法
 * @param {} action
 */
writeoff.partnerPayStatementEdit.statementSelectSuccess = function(result){
	var statementQueryGridStore,
		totalAmount;
	
	statementQueryGridStore = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().store;
	//加载后台查询到的数据到grid的store中
	var statementOfAccountList = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementList;
	//声明本次剩余未还总金额
	var totalAmount = result.partnerPayStatementVo.partnerPayStatementDto.totalAmount;
	var totalCount = result.partnerPayStatementVo.partnerPayStatementDto.totalCount;
	if(!Ext.isEmpty(statementOfAccountList)){
		statementQueryGridStore.loadData(statementOfAccountList);
			//动态给总条数设置值
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalCount_ID').setValue(totalCount);
		
		//动态给总金额设置值
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalAmount_ID').setValue(totalAmount);
	}else{
		statementQueryGridStore.removeAll();
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalAmount_ID').setValue(0);
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
	}
		
};


/**
* 控制明细界面的按钮的显示与隐藏
*/
writeoff.partnerPayStatementEdit.statementUnVerifyAmountShow = function(model,operateForm,type){
	var periodPayAmount;
	//如果是弹出窗口，则需要确认反确认按钮显示和隐藏
	if(type==writeoff.PAGEFROM_STATEMENTEDIT){
		//如果已经确认，则显示确认按钮，显示反确认按钮
		if(model.get('statementStatus')==writeoff.STATEMENTCONFIRMSTATUS_Y){
			//隐藏确认按钮
			operateForm.items.items[2].hide();
			//显示反确认按钮
			operateForm.items.items[3].show();
		}else{
			//显示确认按钮
			operateForm.items.items[2].show();
			//隐藏反确认按钮
			operateForm.items.items[3].hide();
		}
	}
	//获取本期剩余金额进行判断
	periodPayAmount = model.get('periodPayAmount');
	//如果本期发生金额不等于0，则显示下面图标
	if(periodPayAmount!=null && periodPayAmount!=0){
		//显示付款图标
		operateForm.items.items[1].show();
	}else{
		//隐藏付款按钮
		operateForm.items.items[1].hide();
		//隐藏反确认按钮
		operateForm.items.items[3].hide();
	}	
	
}

/**
 * 按合伙人查询对账单
 */
writeoff.partnerPayStatementEdit.statementSelectByPartner = function(){
	var form  = this.up('form').getForm();	
	//声明客户编码和客户名称
	var customerCode;
	
	if(form.isValid()){
		//汪文博
		writeoff.partnerPayStatementEdit.statementOrgCode = form.findField('statementOrgCode').getValue();
		writeoff.partnerPayStatementEdit.largeRegion = form.findField('largeRegion').getValue();
		writeoff.partnerPayStatementEdit.smallRegion = form.findField('smallRegion').getValue();
		if(Ext.isEmpty(writeoff.partnerPayStatementEdit.statementOrgCode)
				&&Ext.isEmpty(writeoff.partnerPayStatementEdit.largeRegion)
				&&Ext.isEmpty(writeoff.partnerPayStatementEdit.smallRegion)){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.orgCodeNull'));
			return false;
		}
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH + 1){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取客户信息
		var customerCode =  form.findField('customerCode').getValue();
		var billType =  form.findField('billType').getValue();
		//设置隐藏域数据
		var hiddenfield = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_BYCUSTOMER);
		hiddenfield.getForm().findField('billTypeParam').setValue(billType);
		hiddenfield.getForm().findField('customerCodeParam').setValue(customerCode);
		hiddenfield.getForm().findField('periodBeginDateParam').setValue(form.findField('periodBeginDate').getValue());
		hiddenfield.getForm().findField('periodEndDateParam').setValue(form.findField('periodEndDate').getValue());
		hiddenfield.getForm().findField('confirmStatusParam').setValue(form.getValues().confirmStatus);
		hiddenfield.getForm().findField('settleStatusParam').setValue(form.getValues().settleStatus);
		
		var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.partnerPayStatementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}
};


/**
 * 根据对账单号查询
 */
writeoff.partnerPayStatementEdit.statementSelectByStatementNumber = function(){		
	var form  = this.up('form').getForm();	
	
	var statementBillNumbers = form.findField('statementBillNumbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(statementBillNumbers)!=null && Ext.String.trim(statementBillNumbers)!=''){
		var billNumberArray = stl.splitToArray(statementBillNumbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var hiddenfield = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER);
		hiddenfield.getForm().findField('statementBillNumbersParam').setValue(statementBillNumbers);
		var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.partnerPayStatementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

/**
 * 按运单号查询
 */
writeoff.partnerPayStatementEdit.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(numbers)!=null && Ext.String.trim(numbers)!=''){
		var billNumberArray = stl.splitToArray(numbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var hiddenfield = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER);
		hiddenfield.getForm().findField('numbersParam').setValue(numbers);
		var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.partnerPayStatementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

/**
 * 按部门查询
 */
writeoff.partnerPayStatementEdit.statementFailingInvoice = function(form){
	writeoff.partnerPayStatementEdit.failingInvoiceOrgCode = form.findField('failingInvoiceOrgCode').getValue();
	var hiddenfield = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getHiddenField();
	hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_FAILINGINVOICE);
	var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	//查询后台
	grid.store.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = grid.store.proxy.reader.rawData;
			if(!success && ! rawData.isException){
				var result = Ext.decode(operation.response.responseText);	
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
			if(success){
				var result = Ext.decode(operation.response.responseText);
				writeoff.partnerPayStatementEdit.statementSelectSuccess(result);	
			}
	    }
	});
};


/**
 * 查看明细
 */	
writeoff.partnerPayStatementEdit.statementViewDetail = function(){
	var selection=Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	var partnerPayStatementVo = new Object();
	var partnerPayStatementDto = new Object();
	var partnerPayStatementEntity = new Object();
	partnerPayStatementEntity.statementBillNo = selection[0].get('statementBillNo');
	partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
	partnerPayStatementDto.partnerPayStatementEntity = partnerPayStatementEntity;
	var yesFn=function(){
		Ext.Ajax.request({
			url:writeoff.realPath('queryPayStatementDetailByNumber.action'),
			jsonData:{	
				'partnerPayStatementVo':partnerPayStatementVo
			},
			success:function(response){
				
				var currentBillsFormAdd,
					result,
					model;
				result = Ext.decode(response.responseText);	
				model = new Foss.partnerPayStatementEdit.StatementFormModel(selection[0].data);
				writeoff.partnerPayStatementEdit.editStatementEntryWindow = Ext.create('Foss.partnerPayStatementEdit.EditStatementEntryWindow');
				var win = writeoff.partnerPayStatementEdit.editStatementEntryWindow;
				//操作按钮面板
				var operateForm = writeoff.partnerPayStatementEdit.operateButtonPanel;
				//控制本期金额图标显示和隐藏
				writeoff.partnerPayStatementEdit.statementUnVerifyAmountShow(model,operateForm,writeoff.PAGEFROM_STATEMENTEDIT);
				//给界面添加数据
				win.items.items[0].loadRecord(model);
				var statementEntryEditGrid = win.items.items[2];
				var statementOfAccountDPeriodListData = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList;
				//清空表格
				statementEntryEditGrid.store.removeAll();
				if(!Ext.isEmpty(statementOfAccountDPeriodListData)){
					statementEntryEditGrid.store.loadData(statementOfAccountDPeriodListData);
				}
				//设置明细界面打开标记
				writeoff.partnerPayStatementEdit.pageType=true;
				win.show();
				//对账单明细来源 -- 查询
				writeoff.showEntryType = writeoff.PAGEFROM_STATEMENTEDIT;
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.partnerPayStatementEdit.statementConfirmAlert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.isWatchEntry'),yesFn,noFn);			
}

/**
* 删除对账单明细
*/
writeoff.partnerPayStatementEdit.statementRemoveEntry = function(grid, rowIndex, colIndex){
	var partnerPayStatementDto = new Object();
	var partnerPayStatementVo = new Object();//对账单传递后台vo
	var	statementEntryEditGrid,
		selection;
	statementEntryEditGrid = writeoff.partnerPayStatementEdit.partnerPayStatementEditGrid;
	selection = grid.getStore().getAt(rowIndex);
	//var amountDel = selection.get("amount");
	var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo;
	//账单状态
	var statementStatus = baseInfoForm.getForm().findField("statementStatus").getValue();
	//var amount = baseInfoForm.getForm().findField("periodPayAmount").getValue();
	//var realAmount = amount - amountDel;
	//数据赋值
	partnerPayStatementDto.id=selection.get("id");
	partnerPayStatementDto.statementBillNo=selection.get("statementBillNo");
	partnerPayStatementDto.confirmStatus = statementStatus;
	partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
	Ext.Ajax.request({
		url:writeoff.realPath('deletePartnerStatementEntry.action'),
		jsonData:{
			'partnerPayStatementVo':partnerPayStatementVo
		},
		success:function(response){
			var result = Ext.decode(response.responseText);	
			//获得对账单
			var partnerPayStatementEntity = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementEntity;
			var model = new Foss.partnerPayStatementEdit.StatementFormModel(partnerPayStatementEntity);
			//刷新对账单值
			baseInfoForm.loadRecord(model);
			//获得对账单明细集合
			var partnerPayStatementDList = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList;
			//清空表格
			statementEntryEditGrid.store.removeAll();
			if(!Ext.isEmpty(partnerPayStatementDList)){
				statementEntryEditGrid.store.loadData(partnerPayStatementDList);
			}	
			//改变金额
			//writeoff.partnerPayStatementEdit.baseInfo.getForm().findField("periodPayAmount").setValue(realAmount);
			Ext.ux.Toast.msg(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.deleteSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}


/**
 * 部门校验
 */	
writeoff.partnerPayStatementEdit.orgCodeInspect = function(){
	var selection=Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	writeoff.partnerPayStatementEdit.statementOrgCode = selection[0].data.createOrgCode;
	writeoff.partnerPayStatementEdit.currentOrgCode = FossUserContext.getCurrentDeptCode();
	if(!Ext.isEmpty(writeoff.partnerPayStatementEdit.statementOrgCode) && !Ext.isEmpty(writeoff.partnerPayStatementEdit.currentOrgCode)){
		if(writeoff.partnerPayStatementEdit.statementOrgCode != writeoff.partnerPayStatementEdit.currentOrgCode){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.orgCodeDifferent'));
			return false;
		}
	}else{
		if(Ext.isEmpty(writeoff.partnerPayStatementEdit.statementorgCode)){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementOrgCode'));
			return false;
		}else if(Ext.isEmpty(writeoff.partnerPayStatementEdit.currentOrgCode)){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.currentOrgCode'));
			return false;
		}else{
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.orgCode'));
			return false;
		}
	}
	return true;
}

/**
 * 对账单付款判断
 */
writeoff.partnerPayStatementEdit.statementPaymentEdit = function(payType){
	var yesFn=function(){
		writeoff.partnerPayStatementEdit.statementPayment(payType);
	}
	var noFn=function(){
	 	return false;
	};
	writeoff.partnerPayStatementEdit.statementConfirmAlert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.readToPay'),yesFn,noFn);
}


/**
 * 付款判断（公用）
 */
writeoff.partnerPayStatementEdit.statementPayment = function(payType){
	//对账单
	var selections=Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	if(selections.length == 0){
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
		return false;
	}
	if(!writeoff.partnerPayStatementEdit.orgCodeInspect()){
		return false;
	}
	//定义对账单号数组
	var statementBillNos = [];
	var totalAmount = 0;
	//将最后一行的对账单号加入数组
	statementBillNos.push(selections[selections.length - 1].get('statementBillNo'));
	
	totalAmount = stl.amountAdd(totalAmount, selections[selections.length - 1].get('periodPayAmount'));
	//如果选中的超过一条
	if(selections.length > 1){
		//判断是否为同一合伙人（customerCode，billType） 和应付金额判断
		for(var i = 0; i < selections.length - 1; i++){
			//发生额判断
			if(selections[i].get('periodAmount')<0){
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.apStatementToReceiveWarning'));
				return false;
			}
			if(payType==writeoff.PAGEFROM_STATEMENTEDIT){
				//本期应付金额判断
				if(selections[i].get('periodPayAmount')==0){
					Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.noAmountToReceiveWarning'));
					return false;
				}
				//确认状态判断
				if(selections[i].get('statementStatus')!=writeoff.STATEMENTCONFIRMSTATUS_Y){
					Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
					return false;
				}
			}
			//同一合伙人判断
			if((selections[i].get('customerCode') != selections[i+1].get('customerCode')) ||
					(selections[i].get('billType') != selections[i+1].get('billType'))){
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.notCommonPartner'));
				return false;
			}
			//放入第一条到倒数第二条的对账单号
			statementBillNos.push(selections[i].get('statementBillNo'));
			totalAmount = stl.amountAdd(totalAmount, selections[i].get('periodPayAmount'));
		}
	}else{
		if(selections[0].get('periodAmount')<0){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.apStatementToReceiveWarning'));
			return false;
		}
		if(payType==writeoff.PAGEFROM_STATEMENTEDIT){
			//本期应付金额判断
			if(selections[0].get('periodPayAmount')==0){
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.noAmountToReceiveWarning'));
				return false;
			}
			//确认状态判断
			if(selections[0].get('statementStatus')!=writeoff.STATEMENTCONFIRMSTATUS_Y){
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
				return false;
			}
		}
	}
	writeoff.partnerPayStatementEdit.paymentBillWindow = Ext.create('Foss.partnerPayStatementEdit.PaymentBillWindow');
	//明细界面
	if(payType==writeoff.PAGEFROM_STATEMENTENTRY){
		var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo;
		//金额
		var amount = baseInfoForm.getForm().findField("periodPayAmount").getValue();
		//账单状态
		var statementStatus = baseInfoForm.getForm().findField("statementStatus").getValue();
		if(amount==0){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.noAmountToReceiveWarning'));
			return false;
		}
		//确认状态判断
		if(statementStatus!=writeoff.STATEMENTCONFIRMSTATUS_Y){
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
			return false;
		}
	}
	//如果为批量则放入批量的，如果为单条或者明细的 放入第一条
	writeoff.partnerPayStatementEdit.statementBillNos = statementBillNos;
	//查询费用承担部门
	//获取传入后台参数
	var partnerPayStatementVo = new Object();
	var partnerPayStatementDto = new Object();
	partnerPayStatementDto.statementBillNo = statementBillNos[0];
	partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
	Ext.Ajax.request({
		url:writeoff.realPath('getExpenseCenter.action'),
		jsonData:{
			'partnerPayStatementVo':partnerPayStatementVo
		},
		success:function(response){
				var result = Ext.decode(response.responseText);
				//获取费用承担部门编码
				var expenseBearCode = result.partnerPayStatementVo.partnerPayStatementDto.expenseBearCode;
				var paymentBaseInfoForm = writeoff.partnerPayStatementEdit.paymentBillWindow.items.items[0];
				paymentBaseInfoForm.add(writeoff.partnerPayStatementEdit.myPanel);
				paymentBaseInfoForm.getForm().findField("costDeptCode").setValue(expenseBearCode);
				//付款判断成功后，调用该方法
				writeoff.partnerPayStatementEdit.statementPaymentSuccess(writeoff.partnerPayStatementEdit.paymentBillWindow,[selections[0].data],payType, totalAmount);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}	
	});
}	


/**
 * 付款提交操作
 */
writeoff.partnerPayStatementEdit.paymentCommit = function(){
	//获取对账单列表
	var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	//对账单明细窗口
	var statementEntryWindow = writeoff.partnerPayStatementEdit.editStatementEntryWindow;
	//付款窗口
	var paymentBillWindow = writeoff.partnerPayStatementEdit.paymentBillWindow;
	//付款数据
	var paymentBillForm = paymentBillWindow.items.items[0];
 	//获取form表单数据进行校验
 	var customerCode = paymentBillForm.getForm().findField('customerCode').getValue();
 	var customerName = paymentBillForm.getForm().findField('customerName').getValue();
 	var paymentOrgCode = paymentBillForm.getForm().findField('paymentOrgCode').getValue();
 	var paymentOrgName = paymentBillForm.getForm().findField('paymentOrgName').getValue();
 	var paymentCompanyCode = paymentBillForm.getForm().findField('paymentCompanyCode').getValue();
 	var paymentCompanyName = paymentBillForm.getForm().findField('paymentCompanyName').getValue();
 	var statementBillNo = paymentBillForm.getForm().findField('statementBillNo').getValue();
 	
 	//部门名称不能为空
 	if(Ext.isEmpty(paymentOrgName)){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.pawriteoff.paymentOrgNameIsNullWarning'));
 		return false;
 	}
 	
 	//部门编码不能为空
 	if(Ext.isEmpty(paymentOrgCode)){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentOrgCodeIsNullWarning'));
 		return false;
 	}
 	
 	//公司名称不能为空
 	if(Ext.isEmpty(paymentCompanyName)){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyNameIsNullWarning'));
 		return false;
 	}
 	
 	//公司编码不能为空
 	if(Ext.isEmpty(paymentCompanyCode)){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyCodeIsNullWarning'));
 		return false;
 	}
 	
 	//获取表单中信息进行校验	
 	var paymentType = paymentBillForm.getForm().findField('paymentType').getValue();
 	var amount = paymentBillForm.getForm().findField('amount').getValue();
 	var accountNo = paymentBillForm.getForm().findField('accountNo').getValue();
 	var provinceName = paymentBillForm.getForm().findField('provinceName').getValue();
 	var provinceCode = paymentBillForm.getForm().findField('provinceCode').getValue();
 	var cityName = paymentBillForm.getForm().findField('cityName').getValue();
 	var cityCode = paymentBillForm.getForm().findField('cityCode').getValue();
 	var bankHqCode = paymentBillForm.getForm().findField('bankHqCode').getValue();
 	var bankHqName = paymentBillForm.getForm().findField('bankHqName').getValue();
 	var bankBranchName = paymentBillForm.getForm().findField('bankBranchName').getValue();
 	var bankBranchCode = paymentBillForm.getForm().findField('bankBranchCode').getValue();
 	var accountType = paymentBillForm.getForm().findField('accountType').getValue();
 	var payeeName =	paymentBillForm.getForm().findField('payeeName').getValue();
 	var payeeCode =	paymentBillForm.getForm().findField('payeeCode').getValue();
 	var mobilePhone = paymentBillForm.getForm().findField('mobilePhone').getValue();
 	var invoiceHeadCode = paymentBillForm.getForm().findField('invoiceHeadCode').getValue();
 	var invoiceHeadName = paymentBillForm.getForm().findField('invoiceHeadCode').getRawValue();
 	
 	//付款类型判断
 	if(Ext.isEmpty(paymentType)){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentTypeIsNullWarning'));
 		return false;
 	}
 	//金额判断
 	if(Ext.isEmpty(amount) || amount<=0){
 		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cashToPayAmountLimitWarning'));
 		return false;
 	}
 	//如果为电汇
 	if(paymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
 		if(Ext.isEmpty(accountNo)||Ext.isEmpty(provinceCode)
 			||Ext.isEmpty(cityCode)||Ext.isEmpty(bankHqCode)
 			||Ext.isEmpty(bankBranchCode)||Ext.isEmpty(accountType)){
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankInfoIsNullWarning'));
 			return false;
 		}
 		//校验界面输入条件
 		if(!paymentBillForm.getForm().isValid()){
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 					writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.validateFailAlert'));
 			return false;
 		}
 		//判断如果为电汇，收款人不能为空
 		if(Ext.isEmpty(payeeName)||Ext.isEmpty(mobilePhone)){
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.payeeInfoIsNullWarning'));
 			return false;	
 		}
 		//发票抬头判断
 		if(Ext.isEmpty(invoiceHeadCode)||Ext.isEmpty(invoiceHeadName)){
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceTitleIsNullWarning'));
 			return false;	
 		}
 	}
 	//声明传入action参数
 	var vo = new Object();
 	//此处需要更新一下模型
 	var data = paymentBillForm.getForm().getValues();
 	
 	//获取付款单头信息即界面form表单
 	vo.paymentEntity = data;
 	if(null != writeoff.partnerPayStatementEdit.batchNo){
 		vo.paymentEntity.batchNo=writeoff.partnerPayStatementEdit.batchNo;
 	}
 	vo.billNos=writeoff.partnerPayStatementEdit.statementBillNos;
 	//遮罩窗口
 	paymentBillWindow.getLoadMask().show();
 	//提交
 	Ext.Ajax.request({
 		url:writeoff.realPath('toPayment.action'),
 		jsonData:{
 			'vo':vo
 		},
 		success:function(response){
 			//获取返回结果
 			var result = Ext.decode(response.responseText);
 			
 			
 			//获取付款单号
 			var paymentNo = '';
 			try{
 				paymentNo = result.vo.paymentEntity.paymentNo;
 			}
 			catch(e){
 				paymentNo = '';
 			}
 			
 			//遮罩窗口
 			paymentBillWindow.getLoadMask().hide();
 			//FOSS合伙人到达报销工作流提示
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.worknoisnotgenerate'));
 			//关闭窗口
 			paymentBillWindow.close();
 			statementEntryWindow.close();
 			grid.store.load();
 		},
 		exception:function(response){
 			var result = Ext.decode(response.responseText);
 			//隐藏掉遮罩
 			paymentBillWindow.getLoadMask().hide();
 			//弹出异常提示
 			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
 		},
 		unknownException:function(form,action){
 			//隐藏掉遮罩
 			paymentBillWindow.getLoadMask().hide();
 		},
 		failure:function(form,action){
 			//隐藏掉遮罩
 			paymentBillWindow.getLoadMask().hide();
 		}
 	});
 }



/**
 * 结账状态store
 */
Ext.define('Foss.partnerPayStatementEdit.SettleStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.all'),value:'ALL'},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.settled'),value:writeoff.SETTLESTATUS_Y},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSettle'),value:writeoff.SETTLESTATUS_N}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});


/**
 *  对账单子类型store
 */
Ext.define('Foss.partnerPayStatementEdit.BillTypeStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryFee'),value:writeoff.STATEMENTTYPE_PARTNER__DELIVERY_FEE_PAYABLE}
				//{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.delegationDeliveryFee'),value:writeoff.STATEMENTTYPE_PARTNER__DELEGATION_DELIVERY_FEE}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});
/**
 * 是否增值税发票 
 */
Ext.define('Foss.partnerPayStatementEdit.applyVATInvoiceStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.yes'),value:true},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.no'),value:false}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

/**
* 对账单form表单model
*/
Ext.define('Foss.partnerPayStatementEdit.StatementFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		//客户编码
		name:'customerCode'
	},{
		//单据类型
		name:'billType'
	},{
		name:'billBeginTime',
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
		name:'statementStatus'
	},{
		name:'periodBeginDate',
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
		name:'companyCode'
	},{
		name:'companyName'
	},{
		name:'customerName'
	},{
		name:'confirmTime',
		type:'date',
		convert: function(value) {
			if (value != null) {
				var date =  new Date(value);
				return date;
			} else {
				return null;
			}
		}
	},{
		name:'periodEndDate',
		type:'date',
		convert: function(value) {
			if (value != null) {
				//此处一定要这样写，不能转化成字符串，如果转化成字符串，那么你getRecord拿到的也是字符串。
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	},{
		name:'createOrgCode'
	},{
		name:'createOrgName'
	},{
		name:'unifiedCode'
	},{
		name:'statementBillNo'
	},{
		name:'companyAccountBankNo'
	},{
		name:'accountUserName'
	},{
		name:'bankBranchName'
	},{
		name:'settleNum',
		type:'int'
	},{
		name:'periodAmount',
		type:'double'
	},{
		name:'periodPayAmount',
		type:'double'
	},{
		name:'periodRrpayAmount',
		type:'double'
	},{
		name:'periodNpayAmount',
		type:'double'
	},{
		name:'statementDes'
	}]
});

/**
 * 对账单表格store
 */
Ext.define('Foss.partnerPayStatementEdit.StatementQueryGridStore', {
	extend:'Ext.data.Store',
    model:'Foss.partnerPayStatementEdit.StatementFormModel',
    pageSize:15,
    proxy:{
		type:'ajax',
		url:writeoff.realPath('queryPayStatement.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			
			var hiddenfield = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getHiddenField();
			
			//获取隐藏域的值
			var queryTabType = hiddenfield.getForm().findField('queryTypeParam').getValue();
			var customerCode = hiddenfield.getForm().findField('customerCodeParam').getValue();
			var periodBeginDate = hiddenfield.getForm().findField('periodBeginDateParam').getValue();
			var periodEndDate = hiddenfield.getForm().findField('periodEndDateParam').getValue();
			var confirmStatus = hiddenfield.getForm().findField('confirmStatusParam').getValue();
			var settleStatus = hiddenfield.getForm().findField('settleStatusParam').getValue();
			var billType = hiddenfield.getForm().findField('billTypeParam').getValue();
			var statementBillNumbers = hiddenfield.getForm().findField('statementBillNumbersParam').getValue();
			var numbers = hiddenfield.getForm().findField('numbersParam').getValue();
			//判断是按那种查询进行
			//按合伙人查询
			if(queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){    //在后台有判断
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.periodBeginDate':periodBeginDate,
					'partnerPayStatementVo.partnerPayStatementDto.periodEndDate':periodEndDate,
					'partnerPayStatementVo.partnerPayStatementDto.confirmStatus':confirmStatus,
					'partnerPayStatementVo.partnerPayStatementDto.settleStatus':settleStatus,
					'partnerPayStatementVo.partnerPayStatementDto.customerCode':customerCode,
					'partnerPayStatementVo.partnerPayStatementDto.billType':billType,
					//邓大伟
					'partnerPayStatementVo.partnerPayStatementDto.statementOrgCode':writeoff.partnerPayStatementEdit.statementOrgCode,
					'partnerPayStatementVo.partnerPayStatementDto.largeRegion':writeoff.partnerPayStatementEdit.largeRegion,
					'partnerPayStatementVo.partnerPayStatementDto.smallRegion':writeoff.partnerPayStatementEdit.smallRegion,
					'partnerPayStatementVo.partnerPayStatementDto.queryPage':writeoff.STATEMENTQUERYTAB_BYCUSTOMER																				
				};
				//按账单号查询
			}else if(queryTabType==writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER){
				//获取对账单号
				var billNumberArray = stl.splitToArray(statementBillNumbers);
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.billDetailNos':billNumberArray,
					'partnerPayStatementVo.partnerPayStatementDto.queryPage': writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER																		
				};
				//按运单号查询
			}else if(queryTabType==writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER){
				//获取运单号、应收单号等
				var billNumberArray = stl.splitToArray(numbers);
				searchParams = {
					'partnerPayStatementVo.partnerPayStatementDto.billDetailNos':billNumberArray,
					'partnerPayStatementVo.partnerPayStatementDto.queryPage': writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER																		
				};
				//按部门查询
			}else{
				searchParams = {
						'partnerPayStatementVo.partnerPayStatementDto.statementOrgCode':writeoff.partnerPayStatementEdit.failingInvoiceOrgCode,
						'partnerPayStatementVo.partnerPayStatementDto.queryPage':writeoff.STATEMENTQUERYTAB_FAILINGINVOICE																				
					};
			}
			Ext.apply(operation,{
				params :searchParams
			});  
		}
	}
});


/**
 * 查询tab
 */
Ext.define('Foss.partnerPayStatementEdit.StatementQueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
//	activeTab: 0,
	height:230,
    items: [{
       	title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.queryByPartner'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:890,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{//邓大伟 
		    	//大区
				xtype:'linkagecomboselector', 
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				itemId : 'Foss_baseinfo_BigRegion_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入 可在此处路径下查看到定义的查询
				columnWidth:.3,
				fieldLabel : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.largeRegion'),
				name : 'largeRegion',
				isPaging: true,    //分页标记
				allowBlank : true,  //必填标记
				value:'',
				minChars : 0,
				displayField : 'name',// 下拉框显示的值
				valueField : 'code', //下拉框获取的值
//				minChars : 0,
				queryParam : 'commonOrgVo.name'
			},{
				//小区
				xtype:'linkagecomboselector',
				itemId : 'Foss_baseinfo_SmallRegion_ID',
				eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
				columnWidth:.3,
				fieldLabel : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.smallRegion'),
				name : 'smallRegion',
				allowBlank : true,
				isPaging: true,
				parentParamsAndItemIds : {
					'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
				},// 此处城市不需要传入
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
//				minChars : 0,
				queryParam : 'commonOrgVo.name'
			},{
				//部门
				xtype : 'dynamicorgcombselector',
				fieldLabel : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.statementOrgName'),
				name : 'statementOrgCode',
				value : stl.currentDept.name,
				columnWidth:.3,
				labelWidth : 85,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
			},{
				//制作日期
	        	xtype:'datefield',
	        	fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodBeginDate'),
	        	allowBlank:false,
	        	name:'periodBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-1)
	        },{
	        	xtype:'datefield',
	        	fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.to'),
	        	labelWidth:80,
	        	name:'periodEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	value:new Date()
	        },{
	            xtype: 'commonsaledepartmentselector',
	            name:'customerCode',
	            emptyText:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.selByPartnerCode'),
	        	contcatFlag :'Y',//增加按手机号查询
	        	singlePeopleFlag:'Y',
	            fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'), //合伙人名称
	            allowBlank: false,
	            listWidth:300,//设置下拉框宽度
	            isPaging:true, //分页
	            columnWidth:.3,
	            //readOnly:true,
	            value:stl.currentDept.code
	        },{
	        	//对账单子类型
	        	xtype: 'combobox',
				name:'billType',
		        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
				store:Ext.create('Foss.partnerPayStatementEdit.BillTypeStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:writeoff.STATEMENTTYPE_PARTNER__DELIVERY_FEE_PAYABLE,
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
	        },{
	        	//对账单状态
		   	 	xtype: 'combobox',
				name:'confirmStatus',
		        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS,null,{
					 'valueCode': null,
               		 'valueName': writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.all')
				}), 
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
		    	//结账状态
				xtype: 'combobox',
				name:'settleStatus',
		        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
				store:Ext.create('Foss.partnerPayStatementEdit.SettleStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
						var dept = this.up('form').getForm().findField("statementOrgCode");
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = dept.store;
							var  key = dept.displayField + '';
							var value = dept.valueField+ '';
							
							var m = Ext.create(store.model.modelName);
							m.set(key, displayText);
							m.set(value, valueText);
							
							store.loadRecords([m]);
							dept.setValue(valueText);
						}
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.44,
					html: '&nbsp;'
				},{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.partnerPayStatementEdit.statementSelectByPartner
				}]
		    }]
 		}]
    },{
        title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatement'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
	       	items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:'<span style="color:red;">*</span>'+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
	    		columnWidth:.65,
	    		labelWidth:70,
	    		labelAlign:'right',
	    		name:'statementBillNumbers',
	    		regex:/^(DZ[0-9]{7,10}[;,])*(DZ[0-9]{7,10}[;,]?)$/i,
				regexText:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatementRegex'),
	    		autoScroll:true,
	    		height:104
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.partnerPayStatementEdit.statementSelectByStatementNumber
				}]
        	}]
        }]
    },{
        title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryByWaybillNo'),
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
				columnWidth:.65,
				regex:/^((YS|YF|US|UF)?[0-9]{7,14}[;,])*((YS|YF|US|UF)?[0-9]{7,14}[;,]?)$/i,
				regexText:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.billNosQueryRegexText'),
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:104
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.queryNosDesc')+'</span>'
					}
				}]
		    },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.partnerPayStatementEdit.statementSelectByNumber
				}]
        	}]
        }]
    },{
       	title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:920,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{
				xtype : 'dynamicorgcombselector',
				fieldLabel : writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),
				name : 'failingInvoiceOrgCode',
				value : stl.currentDept.name,
				columnWidth:.3,
				labelWidth : 85,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
			},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
						var failingInvoiceDept = this.up('form').getForm().findField("failingInvoiceOrgCode");
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = failingInvoiceDept.store;
							var  key = failingInvoiceDept.displayField + '';
							var value = failingInvoiceDept.valueField+ '';
							var m = Ext.create(store.model.modelName);
							m.set(key, displayText);
							m.set(value, valueText);
							store.loadRecords([m]);
							failingInvoiceDept.setValue(valueText);
						}
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.44,
					html: '&nbsp;'
				},{
					text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form = this.up('form').getForm();
						writeoff.partnerPayStatementEdit.statementFailingInvoice(form);
					}
				}]
		    }]
 		}]
    }]
});




/**
 * 对账单表格操作列store
 */
Ext.define('Foss.partnerPayStatementEdit.operateColumnStore',{
	extened:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
		         {name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),value:'1'},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),value:'2'},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.entry'),value:'3'},
				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.payment'),value:'4'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

/**
 * 对账单操作列的下拉框
 */
Ext.define('Foss.statementbill.operateColumn', {
	extend:'Ext.form.field.ComboBox',
	typeAhead: true,
	emptyText:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.actionColumn'),
    triggerAction: 'all',
    queryMode: 'local',
    store: Ext.create('Foss.partnerPayStatementEdit.operateColumnStore'),
    valueField: 'value',
    displayField: 'name',
	forceSelection :true,
	listeners:{
		'change':function(combo){
			if(Ext.isEmpty(combo.getValue())){
				combo.setValue("");
			}		
		},//改方法为了根据不同状态显示不同下拉
		'expand':function(field){
			var selection=Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection()[0];
			
			this.store.removeAll();
			
			this.store.add({name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),value:'1'},
    				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),value:'2'},
    				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.entry'),value:'3'},
    				{name:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.payment'),value:'4'});
			//确认record
			var confirmReocrd = this.store.getAt(0);
			//反确认record
			var unConfirmRecord = this.store.getAt(1);
			//明细record
			var entryReocrd = this.store.getAt(2);
			//付款record
			var recRecord = this.store.getAt(3);
			
			//查看是对账单权限   --确认/反确认
//			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/confirmStatement.action')){
//				//移除确认、反确认按钮
//				this.store.remove([confirmReocrd,unConfirmRecord]);
//			}
			//查看对账单权限  --付款
//			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/repayment.action')){
//				//移除确认、反确认按钮
//				this.store.remove(recRecord);
//			}
			if(!Ext.isEmpty(selection.get('statementStatus'))){
				//确认
				if(selection.get('statementStatus')==writeoff.STATEMENTCONFIRMSTATUS_Y){
					//如果为确认，则移除确认按钮
					this.store.remove(confirmReocrd);	
				}else{
					//如果为反确认，则移除反确认按钮
					this.store.remove(unConfirmRecord);	
				}
			}
			
			//如果本期应付金额等于0，则
			if(!Ext.isEmpty(selection.get('periodPayAmount'))){
				if(selection.get('periodPayAmount')==0){
					//如果本期应付金额等于0，且结账次数大于0，则移除付款按钮
					if(!Ext.isEmpty(selection.get('settleNum')) && selection.get('settleNum')>0){
						//移除付款按钮
						this.store.remove(recRecord);
						//移除反确认按钮
						this.store.remove(unConfirmRecord);
						
					}
				}
			}	
		},
		'select':function(combo){
			if(combo.value=='1'){
				//确认
				writeoff.partnerPayStatementEdit.statementConfirm(writeoff.PAGEFROM_STATEMENTEDIT);
			}else if(combo.value=='2'){
				//反确认	
				writeoff.partnerPayStatementEdit.statementConfirm(writeoff.PAGEFROM_STATEMENTEDIT);
			}else if(combo.value=='3'){
				//明细
				writeoff.partnerPayStatementEdit.statementViewDetail();
			}else if(combo.value=='4'){
				//付款	
				writeoff.partnerPayStatementEdit.statementPaymentEdit(writeoff.PAGEFROM_STATEMENTEDIT);
			}
		}
	}
}); 


Ext.define('Foss.partnerPayStatementEdit.QueryEditParamsHiddenfield',{
	extend:'Ext.form.Panel',
	//hidden:true,
	layout:'column',
	defaults:{
		readOnly:true
	},
	items:[{
    	xtype:'datefield',
    	fieldLabel:'periodBeginDateParam',
    	allowBlank:false,
    	name:'periodBeginDateParam',
    	columnWidth: .3,
    	format:'Y-m-d'
    },{
    	xtype:'datefield',
    	fieldLabel:'periodEndDateParam',
    	labelWidth:30,
    	name:'periodEndDateParam',
    	format:'Y-m-d',
    	columnWidth: .3
    },{
   	 	xtype: 'textfield',
		name:'customerCodeParam',
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),
        columnWidth:.4
    },{
   	 	xtype: 'textfield',
		name:'confirmStatusParam',
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
        columnWidth:.3
    },{
		xtype: 'textfield',
		name:'settleStatusParam',
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
        columnWidth:.3
    },{       		
		xtype:'textfield',
		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
		columnWidth:.4,
		labelWidth:70,
		labelAlign:'right',
		name:'billTypeParam',
		height:95
    },{       		
		xtype:'textareafield',
		fieldLabel:'statementBillNumbersParam',
		columnWidth:.5,
		labelWidth:70,
		labelAlign:'right',
		name:'statementBillNumbersParam',
		height:95
    },{       		
		xtype:'textareafield',
		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
		columnWidth:.5,
		labelWidth:70,
		labelAlign:'right',
		name:'numbersParam',
		height:95
    },{
    	xtype:'textfield',
    	fieldLabel:'queryTypeParam',
    	columnWidth:.2,
    	name:'queryTypeParam'
    }]
});

//操作列下拉框
var operateColumn = Ext.create('Foss.statementbill.operateColumn');

//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1,
  	isObservable : false
}) ;

/**
 * 对账单列表
 */
Ext.define('Foss.partnerPayStatementEdit.StatementQueryGrid', {
	extend:'Ext.grid.Panel',
    title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementBill'),
	frame:true,
	height:625,
	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.partnerPayStatementEdit.StatementQueryGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{ 
		header:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.actionColumn'),  
		editor:operateColumn,
		renderer: function(value){
			  var record = operateColumn.findRecord(operateColumn.valueField, value);
			  return record ? record.get(operateColumn.displayField): operateColumn.valueNotFoundText;
		}
	},{//对账单号
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'), 
		dataIndex: 'statementBillNo'
	},{//客户编码
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex: 'customerCode' 
	},{//客户名称
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
	},{ //对账单状态
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
		dataIndex: 'statementStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
    		return displayField;
		}
	},{ //本期发生额
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodAmount'),
		dataIndex: 'periodAmount'
	},{//本期应付
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
		dataIndex: 'periodPayAmount'
	},/*{ //本期已还金额
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.paidAmount'),
		dataIndex: 'periodRrpayAmount',
		hidden:true
	},{ //本期未还金额
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unpaidAmount'),
		dataIndex: 'periodNpayAmount',
		hidden:true
	},*/{ //账期起始日期
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodStartDate'),
		dataIndex: 'periodBeginDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ //账期结束日期
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodEndAmount'),
		dataIndex: 'periodEndDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ //运单开单时间
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex: 'billBeginTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ //对账单子类型
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_PAYABLE__BILL_TYPE);
			return displayField;
		}
	},{//公司编码
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.companyCode'),
		dataIndex: 'companyCode'
	},{//公司名称
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.companyName'),
		dataIndex: 'companyName'
	},{//确认时间
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		dataIndex: 'confirmTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H：i:s');
			}else{
				return null;
			}
		}
	},{//部门编码
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex: 'createOrgCode'
	},{//部门名称
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex: 'createOrgName'
	},{//部门标杆编码
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		dataIndex: 'unifiedCode'
	},{//子公司账号
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.companyAccountBankNo'),
		dataIndex: 'companyAccountBankNo'
	},{//开户名
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		dataIndex: 'accountUserName'
	},{//支行名称
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.bankBranchName'),
		dataIndex: 'bankBranchName'
	},{//结账次数
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
		dataIndex: 'settleNum'
	},{//对账单描述
		header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.billDescription'),
		dataIndex: 'statementDes'
	},{
		header:'id',
		dataIndex:'id',
		hidden:true
	}],
	//批量付款方法
	batchPayment:writeoff.partnerPayStatementEdit.statementPaymentBatch,
	
    initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',		    	
		    defaults:{
		    	margin:'0 0 5 3'
			},		
		    items: [{
		    	//批量付款
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.batchPayment'),
				columnWidth:.1,
				handler:function(){
					this.up('panel').batchPayment();
				}
			},{
				xtype:'displayfield',
				value:'点击表格操作列<span style="color:red;">空白处</span>，可进行查看明细等操作！',
				columnWidth:.7
			},{
				//导出对账单
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
				columnWidth:.1,
				handler:writeoff.partnerPayStatementEdit.statementExportExcel 
			}]
    	},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [
		     {
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_statementBill_StatementQueryGrid_TotalCount_ID',
				columnWidth:.1,
				fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.totalCount')
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.2,
				fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.totalAmount'),
				id:'Foss_statementBill_StatementQueryGrid_TotalAmount_ID'
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				//pageSize:15,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['15',15],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 },{
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.batchPayment'),
				columnWidth:.1,
				handler:function(){
					this.up('panel').batchPayment();
				}
			},{
				columnWidth:.7
			},{
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
				columnWidth:.1,
				handler:writeoff.partnerPayStatementEdit.statementExportExcel
			}]
    	}];
   		 me.callParent();
    }
});

/**
 * 对账单明细表格model
 */
Ext.define('Foss.partnerPayStatementEdit.StatementEntryModel', {
    extend: 'Ext.data.Model',
    fields: [{
        //id
        name: 'id'
    },  {
        //对账单编号
        name: 'statementBillNo'
    },{
        //来源单号
        name: 'payableNo'
    },{
        //运单号
        name: 'waybillNo'
    },  {
        //单据子类型
        name: 'billType'
    },  {
        //审核状态
        name: 'auditStatus'
    }, {
        //金额
        name: 'amount',
        type: 'double'
    },{
        //已核销金额
        name: 'verifyAmount',
        type: 'double'
    },{
        //未核销金额
        name: 'unverifyAmount',
        type: 'double'
    },{
        //部门编码
        name: 'orgCode'
    }, {
        //部门名称
        name: 'orgName'
    }, {
        //始发网点编码
        name: 'origOrgCode'
    }, {
        //始发网点名称
        name: 'origOrgName'
    }, {
        //到达部门名称
        name: 'destOrgName'
    }, {
        //到达部门编码
        name: 'destOrgCode'
    }, {
        //客户编码
        name: 'customerCode'
    }, {
        //客户名称
        name: 'customerName'
    },{
        //始发站
        name: 'deptRegionCode'
    },{
        //目的站
        name: 'arrvRegionCode'
    }, {
        //提货网点
        name: 'customerPickupOrgName'
    },  {
        //货物名称、
        name: 'goodsName'
    }, {
        //发货客户编码
        name: 'deliveryCustomerCode'
    }, {
        //发货客户名称
        name: 'deliveryCustomerName'
    },{
        //付款方式
        name: 'paymentType'
    }, {
        //提货方式
        name: 'receiveMethod'
    }, {
        //运输性质
        name: 'productCode'
    },{
        //删除时间
        name: 'disableTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },  {
        //业务日期
        name: 'businessDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },  {
        //记账日期
        name: 'accountDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },  {
        //运单签收日期
        name: 'signDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        //创建时间
        name: 'createTime',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        //备注
        name: 'notes'
    }, {
        //运单开单时间
        name: 'waybillCreateDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        //支线转运提成
        name: 'regionalTransportFee',
        type: 'double'
    },  {
        //送货费
        name: 'deliveryFee',
        type: 'double'
    }, {
        //派送费
        name: 'dispatchFee',
        type: 'double'
    },{
        //送货进仓费
        name: 'deliveryWarehouseFee',
        type: 'double'
    },{
        //签收单返单费
        name: 'signReturnFee',
        type: 'double'
    },{
        //送货上楼费
        name: 'deliveryUpstairsFee',
        type: 'double'
    },{
        //大件上楼费
        name: 'bigUpstairsFee',
        type: 'double'
    },{
        //超远派送费
        name: 'farDeliveryFee',
        type: 'double'
    }]
});

/**
 * 明细基本信息
 */
Ext.define('Foss.partnerPayStatementEdit.BaseInfo', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    frame: true,
    defaultType: 'textfield',
    layout: 'column',
    defaults: {
        labelWidth: 60,
        margin: '5 10 5 30',
        readOnly: true
    },
    items: [{
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.billType'),
        name: 'billType',
        xtype: 'combo',
        labelWidth: 75,
        columnWidth: .22,
        store: FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE),
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode'
    }, {
        xtype: 'datefield',
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.createTime'),
        name: 'billBeginTime',
        format: 'Y-m-d H：i:s',
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
        name: 'customerCode',
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),
        name: 'statementStatus',
        xtype: 'combo',
        store: FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS),
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        columnWidth: .22,
        value: 0
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
        name: 'periodBeginDate',
        xtype: 'datefield',
        format: 'Y-m-d',
        columnWidth: .22
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyName'),
        name: 'companyName',
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyCode'),
        name: 'companyCode',
        hidden: true,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),
        name: 'customerName',
        labelWidth: 95,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
        name: 'confirmTime',
        xtype: 'datefield',
        format: 'Y-m-d H：i:s',
        columnWidth: .22
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
        name: 'periodEndDate',
        xtype: 'datefield',
        format: 'Y-m-d',
        columnWidth: .22,
        value: new Date()
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
        name: 'createOrgName',
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.createOrgCode'),
        name: 'createOrgCode',
        hidden: true,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        name: 'statementBillNo',
        columnWidth: .22
    }, /*{
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
        name: 'unifiedSettlement',
        columnWidth: .22,
        hidden: true
    },{
        fieldLabel: 'id',
        name: 'id',
        columnWidth: .22,
        hidden: true
    }, {
        xtype: 'numberfield',
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.versionNo'),
        name: 'versionNo',
        columnWidth: .22
        , hidden: true
    }, */{
        xtype: 'component',
        border: true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
        labelWidth: 85,
        name: 'unifiedCode',
        columnWidth: .20
    }, {
        xtype: 'container',
        columnWidth: .30,
        layout: 'vbox',
        items: [{
            xtype: 'component',
            padding: '0 0 0 0',
            autoEl: {
                tag: 'div',
                html: '<span style="color:red;">'+ writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCodeNotes') + '</span>'
            }
        }]
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),
        name: 'companyAccountBankNo',
        columnWidth: .22
    }, {
        xtype: 'container',
        columnWidth: .28,
        layout: 'vbox',
        items: [{
            xtype: 'component',
            padding: '0 0 0 0',
            autoEl: {
                tag: 'div',
                html: '<span style="color:red;">'+ writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNoNotes') + '</span>'
            }
        }]
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
        name: 'accountUserName',
        columnWidth: .5
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
        name: 'bankBranchName',
        columnWidth: .48
    }, {
        xtype: 'component',
        border: true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.unpaidAmount'),
        labelWidth: 110,
        name: 'periodPayAmount',
        xtype: 'numberfield',
        decimalPrecision: 2,
        columnWidth: .5,
        value: 0
    }, {
        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
        columnWidth: .48,
        name: 'settleNum',
        xtype: 'numberfield',
        value: 0
    }]
});

/**
 * 对账单明细store
 */
Ext.define('Foss.partnerPayStatementEdit.StatementEntryEditStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.partnerPayStatementEdit.StatementEntryModel',
    proxy : {
        type : 'ajax',
        url : writeoff.realPath('queryPayStatementDetailByNumber.action'),
        reader : {
            type : 'json',
            root : 'partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList',
            totalProperty : 'totalCount'
        },
        // 设置请求方式
        actionMethods : 'POST'
    },
    sorters: [{
        property: 'billParentType',
        direction: 'ASC'
    }]
});

/**
 * 添加对账单明细store
 */
Ext.define('Foss.partnerPayStatementEdit.StatementEntryEditAddStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.partnerPayStatementEdit.StatementEntryModel',
    autoLoad: false,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryEntryForAdd.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners : {
    	beforeload : function(store, operation, eOpts){//查询事件
    		//校验日期
    		var periodBeginDate = writeoff.partnerPayStatementEdit.addEntryGrid.dockedItems.items[2].items.items[0].getValue();
    		var periodEndDate = writeoff.partnerPayStatementEdit.addEntryGrid.dockedItems.items[2].items.items[1].getValue();
    		//校验日期
    		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
    			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.foss.stl.writeoff.partnerPayStatementEdit.quryDateIsNullWarning'));
    			return false;
    		}
    		//比较起始日期和结束日期
    		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
    		if(compareTwoDate>stl.DATELIMITDAYS_MONTH + 1){
    			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
    			return false;
    		}else if(compareTwoDate<1){
    			Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
    			return false;
    		}
    		//获取客户信息
    		var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
    		var selection=grid.getSelectionModel().getSelection();
    		//合伙人编码
    		var customerCode = selection[0].get('customerCode');
    		//账单子类型
    		var billType = selection[0].get('billType');
    		//部门编码
    		var orgCode = selection[0].get('createOrgCode');
            //查询条件form中的值
            var params;
            params={
                'partnerPayStatementVo.partnerPayStatementDto.periodBeginDate': periodBeginDate,
                'partnerPayStatementVo.partnerPayStatementDto.periodEndDate': periodEndDate,
                'partnerPayStatementVo.partnerPayStatementDto.customerCode':customerCode,
                'partnerPayStatementVo.partnerPayStatementDto.billType':billType,
                'partnerPayStatementVo.partnerPayStatementDto.orgCode':orgCode
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            if(data==undefined){
                Ext.MessageBox.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),"系统错误");
                return ;
            }
            if(data.success==false){
                Ext.MessageBox.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),data.message);
                return ;
            }
            //总条数
//            if(data.totalCount!=null) {
//            	Ext.getCmp('Foss_statementBill_StatementEntryGrid_TotalCount_ID').setValue(data.totalCount);
//            }else{
//                Ext.getCmp('Foss_statementBill_StatementEntryGrid_TotalCount_ID').setValue('0');
//            }
        }
    }
});

/**
 * 对账单明细操作按钮区域
 */
Ext.define('Foss.partnerPayStatementEdit.OperateButton', {
    extend: 'Ext.panel.Panel',
    layout: 'column',
    defaultType: 'button',
    defaults: {
        columnWidth: .1
    },
    items: [{
        xtype: 'container',
        border: false,
        html: '&nbsp;',
        columnWidth: .45
    },{//付款
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.payment'),
        handler: function(){
        	writeoff.partnerPayStatementEdit.statementPayment(writeoff.PAGEFROM_STATEMENTENTRY)
        }
    },{//确认
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),
        handler: function(){
        	writeoff.partnerPayStatementEdit.statementConfirmChange(writeoff.PAGEFROM_STATEMENTENTRY)
        }
    }, {//反确认
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),
        handler: function(){
        	writeoff.partnerPayStatementEdit.statementConfirmChange(writeoff.PAGEFROM_STATEMENTENTRY)
        }
    },{//导出对账单
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
        handler: writeoff.partnerPayStatementEdit.statementExportExcel
    }, {//导出明细excel
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportExcel'),
        handler: writeoff.partnerPayStatementEdit.statementEntryExportExcel
    }
    
    ]
});



/**
 * 对账单明细记录
 */
Ext.define('Foss.partnerPayStatementEdit.StatementEntryEditGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodCurrentEntry'),
    frame: true,
    height: 300,
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.partnerPayStatementEdit.StatementEntryEditStore'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    columns: [{
        xtype: 'actioncolumn',
        width: 60,
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.actionColumn'),
        align: 'center',
        items: [{
            iconCls: 'statementBill_remove',
            tooltip: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.delete'),
            handler: function (grid, rowIndex, colIndex) {
                writeoff.partnerPayStatementEdit.statementRemoveEntry(grid, rowIndex, colIndex);
            }
        }]
    }, {
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {//对账单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        dataIndex: 'statementBillNo'
    }, {//来源单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.payableNo'),
        dataIndex: 'payableNo'
    }, {//运单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
        dataIndex: 'waybillNo'
    },  {//单据子类型
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.billType'),
        dataIndex: 'billType',
        renderer:  function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__BILL_TYPE);
            return displayField;
        }
    }, {//审核状态
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
        dataIndex: 'auditStatus',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
            return displayField;
        }
    },{//金额
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.amount'),
        dataIndex: 'amount'
    }, {//已核销金额
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
        dataIndex: 'verifyAmount',
        renderer: function (v, m, record) {
            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                return '<span style="color:red">' + v + '</span>';
            } else {
                return v;
            }
        }
    }, {//部门编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
        dataIndex: 'orgCode'
    }, {//部门名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
        dataIndex: 'orgName'
    }, {//始发网点编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
        dataIndex: 'origOrgCode'
    }, {//始发网点名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
        dataIndex: 'origOrgName'
    }, {//到达网点编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
        dataIndex: 'destOrgName'
    }, {//到达网点名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
        dataIndex: 'destOrgCode'
    },{//客户编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
        dataIndex: 'customerCode'
    }, {//客户名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
        dataIndex: 'customerName'
    }, {//始发地
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),
        dataIndex: 'deptRegionCode'
    }, {//目的站
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
        dataIndex: 'arrvRegionCode'
    }, {//提货网点
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
        dataIndex: 'customerPickupOrgName'
    },{//货物名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.goodsName'),
        dataIndex: 'goodsName'
    }, {//发货客户编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
        dataIndex: 'deliveryCustomerCode'
    }, {//发货客户名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
        dataIndex: 'deliveryCustomerName'
    }, {//付款方式
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
        dataIndex: 'paymentType',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
            return displayField;
        }
    }, {//提货方式
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
        dataIndex: 'receiveMethod',
        renderer: function (value) {
            //先去汽运提货方式词条中拿
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS);
            //如果汽运提货方式没拿到，则去空运词条中拿
            if (value == displayField || Ext.isEmpty(displayField)) {
                displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS_AIR);
            }
            return displayField;
        }
    },{//运输性质
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
        dataIndex: 'productCode',
        renderer: function (value) {
            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
        }
    }, {//删除时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.disableTime'),
        dataIndex: 'disableTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },  {//记账日期
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
        dataIndex: 'accountDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {//运单签收日期
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
        dataIndex: 'signDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },{//创建时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },{//备注
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.notes'),
        dataIndex: 'notes'
    }, {//运单开单时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.wayBillCreateDate'),
        dataIndex: 'waybillCreateDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },  {//支线转运提成
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.regionalTransportFee'),
        dataIndex: 'regionalTransportFee'
    },{//送货费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
        dataIndex: 'deliveryFee'
    },{//派送费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.dispatchFee'),
        dataIndex: 'dispatchFee'
    },{//送货进仓费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryWarehouseFee'),
        dataIndex: 'deliveryWarehouseFee'
    }, {//签收单返单费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.signReturnFee'),
        dataIndex: 'signReturnFee'
    },  {//送货上楼费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryUpstairsFee'),
        dataIndex: 'deliveryUpstairsFee'
    }, {//大件上楼费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.bigUpstairsFee'),
        dataIndex: 'bigUpstairsFee'
    }, {//超远派送费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.farDeliveryFee'),
        dataIndex: 'farDeliveryFee'
    },{
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
        dataIndex: 'isDelete',
        hidden: true
    },  {
        header: 'id',
        dataIndex: 'id',
        hidden: true
    }]
});


/**
 * 明细添加grid
 */
Ext.define('Foss.partnerPayStatementEdit.StatementEntryEditAddGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.addCurrentEntry'),
    frame: true,
    height: 400,
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.partnerPayStatementEdit.StatementEntryEditAddStore'),
    bodyCls: 'autoHeight',
    hidden:true,
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    columns: [{
        xtype: 'actioncolumn',
        width: 60,
        text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.actionColumn'),
        align: 'center',
        items: [{
            iconCls: 'statementBill_add',
            tooltip: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.addToEntry'),
            handler: function (grid, rowIndex, colIndex) {
            	//添加明细
            	var billSelection = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection()
            	   //获取对账单号
                var  statementBillNo = billSelection[0].get('statementBillNo');
                //获取选中的可添加明细
            	var selection = grid.getStore().getAt(rowIndex);
                 var payableNo;
//                 var amount;
                 //判断
                 for (var i = 0; i < selection.stores.length; i++) {
                     if(selection.store.getAt(rowIndex).data.id!=null){
                    	 payableNo = selection.store.getAt(rowIndex).data.payableNo;
//                    	 amount = selection.store.getAt(rowIndex).data.amount;
                     }
                 }
                 //组装json数据
                 var partnerPayStatementVo = new Object();
                 var partnerPayStatementDto = new Object();
            	 var periodBeginDate = writeoff.partnerPayStatementEdit.addEntryGrid.dockedItems.items[2].items.items[0].getValue();
        		 var periodEndDate = writeoff.partnerPayStatementEdit.addEntryGrid.dockedItems.items[2].items.items[1].getValue();
                 partnerPayStatementDto.partnerPayStatementDEntity = selection.store.getAt(rowIndex).data;
                 partnerPayStatementDto.periodBeginDate = periodBeginDate;
                 partnerPayStatementDto.periodEndDate = periodEndDate;
                 partnerPayStatementDto.statementBillNo = statementBillNo;
                 partnerPayStatementVo.partnerPayStatementDto = partnerPayStatementDto;
                 //调用后台的方法
                 Ext.Ajax.request({
                     url:writeoff.realPath('addPartnerPayStatementDetail.action'),
                     jsonData:{
                         'partnerPayStatementVo':partnerPayStatementVo
                     },
                     success:function(response){
                    	 //获取明细界面
                    	 var win = writeoff.partnerPayStatementEdit.editStatementEntryWindow;
                    	 //获取明细基本信息
                    	 var baseInfoForm = win.items.items[0];
                    	 //获取明细列表
                    	 var entryGrid = win.items.items[2];
                    	 //获取返回结果
                    	 result = Ext.decode(response.responseText);
                    	 partnerPayStatementDList =  result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementDList;
                    	 
                	 	//获得对账单
						var partnerPayStatementEntity = result.partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementEntity;
						var model = new Foss.partnerPayStatementEdit.StatementFormModel(partnerPayStatementEntity);
						//刷新对账单值
						baseInfoForm.loadRecord(model);
                    	 
                    	 //更新可添加明细列表
                    	 grid.store.load();
                         //更新明细列表
                         entryGrid.store.loadData(partnerPayStatementDList);
                         //更新金额
//                         var realAmount = baseInfoForm.getForm().findField("periodPayAmount").getValue() - amount;
//                         baseInfoForm.getForm().findField("periodPayAmount").setValue(realAmount);
                         Ext.ux.Toast.msg(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.addSuccess'), 'ok', 1000);
                     },
                     exception:function(response){
                         var result = Ext.decode(response.responseText);
                         Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
                     }
                 });
            }
        }]
    }, {
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {//对账单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        dataIndex: 'statementBillNo'
    }, {//来源单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.payableNo'),
        dataIndex: 'payableNo'
    }, {//运单号
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
        dataIndex: 'waybillNo'
    },  {//单据子类型
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.billType'),
        dataIndex: 'billType',
        renderer:  function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__BILL_TYPE);
            return displayField;
        }
    }, {//审核状态
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
        dataIndex: 'auditStatus',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
            return displayField;
        }
    },{//金额
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.amount'),
        dataIndex: 'amount'
    }, {//已核销金额
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
        dataIndex: 'verifyAmount',
        hidden: true,
        renderer: function (v, m, record) {
            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                return '<span style="color:red">' + v + '</span>';
            } else {
                return v;
            }
        }
    },{//部门编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
        dataIndex: 'orgCode'
    }, {//部门名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
        dataIndex: 'orgName'
    }, {//始发网点编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
        dataIndex: 'origOrgCode'
    }, {//始发网点名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
        dataIndex: 'origOrgName'
    }, {//到达网点编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
        dataIndex: 'destOrgName'
    }, {//到达网点名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
        dataIndex: 'destOrgCode'
    },{//客户编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
        dataIndex: 'customerCode'
    }, {//客户名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
        dataIndex: 'customerName'
    }, {//始发地
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),
        dataIndex: 'deptRegionCode'
    }, {//目的站
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
        dataIndex: 'arrvRegionCode'
    }, {//提货网点
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
        dataIndex: 'customerPickupOrgName'
    },{//货物名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.goodsName'),
        dataIndex: 'goodsName'
    }, {//发货客户编码
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
        dataIndex: 'deliveryCustomerCode'
    }, {//发货客户名称
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
        dataIndex: 'deliveryCustomerName'
    }, {//付款方式
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
        dataIndex: 'paymentType',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
            return displayField;
        }
    }, {//提货方式
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
        dataIndex: 'receiveMethod',
        renderer: function (value) {
            //先去汽运提货方式词条中拿
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS);
            //如果汽运提货方式没拿到，则去空运词条中拿
            if (value == displayField || Ext.isEmpty(displayField)) {
                displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS_AIR);
            }
            return displayField;
        }
    },{//运输性质
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
        dataIndex: 'productCode',
        renderer: function (value) {
            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
        }
    }, {//删除时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.disableTime'),
        dataIndex: 'disableTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },  {//记账日期
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
        dataIndex: 'accountDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {//运单签收日期
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
        dataIndex: 'signDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },{//创建时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },{//备注
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.notes'),
        dataIndex: 'notes'
    }, {//运单开单时间
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.wayBillCreateDate'),
        dataIndex: 'waybillCreateDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },  {//支线转运提成
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.regionalTransportFee'),
        dataIndex: 'regionalTransportFee'
    },{//送货费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
        dataIndex: 'deliveryFee'
    },{//派送费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.dispatchFee'),
        dataIndex: 'dispatchFee'
    },{//送货进仓费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryWarehouseFee'),
        dataIndex: 'deliveryWarehouseFee'
    }, {//签收单返单费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.signReturnFee'),
        dataIndex: 'signReturnFee'
    },  {//送货上楼费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.deliveryUpstairsFee'),
        dataIndex: 'deliveryUpstairsFee'
    }, {//大件上楼费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.bigUpstairsFee'),
        dataIndex: 'bigUpstairsFee'
    }, {//超远派送费
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.farDeliveryFee'),
        dataIndex: 'farDeliveryFee'
    },{
        header: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
        dataIndex: 'isDelete',
        hidden: true
    },  {
        header: 'id',
        dataIndex: 'id',
        hidden: true
    }],
    
    initComponent:function(){
		var me = this;
		 me.bbar = Ext.create('Deppon.StandardPaging',{
	            store : me.store,
	            pageSize : 20,
	            maximumSize : 100,
	            plugins : Ext.create('Deppon.ux.PageSizePlugin', {
	                sizeList : [['20', 20], ['50', 50],  ['100', 100]]
	            })
	        });
        writeoff.partnerPayStatementEdit.messageGrid =me.bbar;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',		    	
		    defaults:{
		    	margin:'0 0 5 3'
			},		
		    items: [{
				//制作日期
	        	xtype:'datefield',
	        	fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
	        	allowBlank:false,
	        	name:'periodBeginDateEntry',
	        	columnWidth: .2,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-1)
	        },{
	        	xtype:'datefield',
	        	fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.to'),
	        	name:'periodEndDateEntry',
	        	format:'Y-m-d',
	        	columnWidth: .2,
	        	value:new Date()
	        },{
	        	xtype:'container',
	        	columnWidth: .4
	        },{
				//查询可添加的明细信息
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.query'),
				columnWidth:.1,
				handler:function(){
                    //点击查询触发事件
                    writeoff.partnerPayStatementEdit.messageGrid.moveFirst();
                }
			},{
				//关闭添加
				xtype:'button',
				text:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.cancelAddEntry'),
				columnWidth:.1,
				handler:writeoff.partnerPayStatementEdit.cancelAddEntry 
			}]
    	}]
   		 me.callParent();
    }
});



/**
 * 付款单页面的对账单信息
 */
 Ext.define('Foss.partnerPayStatementEdit.PaymentBaseInfoPanel',{
	 	extend:'Ext.form.Panel',
	 	title:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentBill'),
	 	frame:true,
	 	layout: {
	       type: 'table',
	       columns: 3
	   },
	   defaultType:'textfield',
	   defaults:{
	   	readOnly:true,
	   	labelWidth:90
	   },
	 	items:[{
	 		//单号
	 		fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'), 
	 		name: 'statementBillNo',
	 		hidden:true
	 	},{
	 		//合伙人编码
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),
	 		name:'customerCode'
	 	},{
	 		//合伙人名称
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),
	 		name:'customerName'
	 	},{
	 		//部门编码
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
	 		name:'paymentOrgCode',
	 		hidden:true
	 	},{
	 		//部门名称
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
	 		name:'paymentOrgName'
	 	},{
	 		//公司编码
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.applyCompanyName'),
	 		name:'paymentCompanyCode',
	 		hidden:true
	 	},{
	 		//子公司
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.applyCompanyName'),
	 		name:'paymentCompanyName'
	 	},{//付款方式
	 		xtype:'combobox',
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentType'),
	 		name:'paymentType',
	     	editable:false,
	 		store:null,
	 		queryModel:'local',
	 		displayField:'valueName',
	 		valueField:'valueCode',
	     	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,[writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER]),
	     	readOnly:true,
	 		value:writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
		},{
	 		//金额
	 		xtype: 'numberfield', 
        	decimalPrecision: 2,
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.amount'),
	 		name:'amount'
	 	},
	 	{
	 		//收款手机
	 		xtype:'textfield',
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhone'),
	 		regex:/^1[3|4|5|7|8][0-9]\d{8}$/,
	 		regexText:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhoneRegex'),
	 		allowBlank:false,
	 		readOnly:false,
	 		name:'mobilePhone'
	 	},{
	 		//发票抬头
	 		xtype:'commonsubsidiaryselector',
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceHeadCode'),
	 		readOnly:false,
	 		name:'invoiceHeadCode',
	 		allowBlank:false
	 	},{
	 		//银行账号
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountNo'),
	 		xtype:'commonpayeeinfoselector',
			accountTypes:writeoff.FIN_ACCOUNT_TYPE_PUBLIC+","+writeoff.FIN_ACCOUNT_TYPE_PRIVATE,
	 		operatorId:FossUserContext.getCurrentUserEmp().empCode,
	 		name:'accountNo',
	 		isOnlyPartnerAccount:'N',
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
	 		fieldLabel:'provinceCode',
	 		name:'provinceCode',
	 		hidden:true
	 	},{
	 		fieldLabel:'sourceBillType',
	 		name:'sourceBillType',
	 		hidden:true
	 	},{
	 		//开户行省份
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.provinceName'),
	 		name:'provinceName'
	 	},{
	 		fieldLabel:'cityCode',
	 		name:'cityCode',
	 		hidden:true
	 	},{
	 		//开户行城市
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cityName'),
	 		name:'cityName'
	 	},{
	 		//开户银行
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankHqName'),
	 		name:'bankHqName'
	 	},
	 	{
	 		fieldLabel:'bankHqCode',
	 		name:'bankHqCode',
	 		hidden:true
	 	},
	 	{
	 		//开户行支行
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchName'),
	 		name:'bankBranchName'
	 	},{
	 		//行号
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchCode'),
	 		name:'bankBranchCode'
	 	},{
	 		fieldLabel:'accountType',
	 		name:'accountType',
	 		hidden:true
	 	},{
	 		//账户类型
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountType'),
	 		name:'accountTypeName'
	 	},
	 	{
	 		fieldLabel:'payeeName',
	 		name:'payeeName',
	 		hidden:true
	 	},{
	 		fieldLabel:'payeeCode',
	 		name:'payeeCode',
	 		hidden:true
	 	}]
 });
 
 /**
  * 付款账单明细操作按钮区域
  */
 Ext.define('Foss.partnerPayStatementEdit.PayOperateButton', {
     extend: 'Ext.panel.Panel',
     layout: 'column',
     defaultType: 'button',
     defaults: {
         columnWidth: .1
     },
     items: [{
         xtype: 'toolbar',
         border: false,
         html: '&nbsp;',
         columnWidth: .45
     },{
    	 //上传附件
         text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.upload'),
         handler: writeoff.partnerPayStatementEdit.paymentUpload
     },{
         text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.commit'),
         handler: writeoff.partnerPayStatementEdit.paymentCommit
     }, {
         text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cancel'),
         handler: writeoff.partnerPayStatementEdit.paymentCancel
     }]
 });

/**
 * 批量删除付款对账单明细
 */
writeoff.partnerPayStatementEdit.BatchRemoveStatementEntry = function(){
	//付款对账单明细
	var partnerPayStatementDetailGrid = writeoff.partnerPayStatementEdit.partnerPayStatementEditGrid;
	//对账单信息form
	var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo.getForm();
	//付款对账单明细表格中被批量选中的行记录
	var selectRows = partnerPayStatementDetailGrid.getSelectionModel().getSelection();
	//必须选择一条数据
	if(selectRows.length < 1){
		Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
						writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
		return false;
	}
	
	//选中的要删除的对账单明细
	var statementDetails = [];
	for(var i=0;i<selectRows.length;i++){
		var item = selectRows[i];
		statementDetails.push({'id':item.get('id'),'payableNo':item.get('payableNo'),'statementBillNo':item.get('statementBillNo')});
	}
	
	var noFn = function(){
		return false;
	};
	var yesFn = function(){
		//发起ajax请求删除对账单明细
		Ext.Ajax.request({
			url:writeoff.realPath('batchDeleteStatementEntry.action'),
			jsonData:{
				'partnerPayStatementVo':{
					'partnerPayStatementDto':{
						'partnerPayStatementDList':statementDetails
					}
				}
			},
			success:function(response){
				//获取返回结果
				var result = Ext.decode(response.responseText);	
				//获得返回的对账单信息
				var statementDto = result.partnerPayStatementVo.partnerPayStatementDto;
				
				//设置删除之后的对账单总金额
				baseInfoForm.findField('periodPayAmount').setValue(statementDto.periodAmount || 0);
				//账期开始日期
				baseInfoForm.findField('periodBeginDate').setValue(stl.longToDateConvert(statementDto.periodBeginDate));
				//账期结束日期
				baseInfoForm.findField('periodEndDate').setValue(stl.longToDateConvert(statementDto.periodEndDate));
				
				//刷新对账单明细表格数据
				partnerPayStatementDetailGrid.store.load({
					params : {
						'partnerPayStatementVo.partnerPayStatementDto.partnerPayStatementEntity.statementBillNo':statementDto.statementBillNo
					}
				});
				
				Ext.ux.Toast.msg(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.deleteSuccess'), 'ok', 1000);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	};
	//确认是否批量删除
	writeoff.partnerPayStatementEdit.statementConfirmAlert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),yesFn,noFn);
};

/**
 * 合伙人付款对账单页面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建对账单查询tab
	var statementQueryInfoTab = Ext.create('Foss.partnerPayStatementEdit.StatementQueryInfoTab');
	//创建查询隐藏域
	var queryEditHiddenfield =Ext.create('Foss.partnerPayStatementEdit.QueryEditParamsHiddenfield',{hidden:true});
	//创建对账单查询表格
	var statementQueryGrid = Ext.create('Foss.partnerPayStatementEdit.StatementQueryGrid');
	 //对账单明细的基本信息
    writeoff.partnerPayStatementEdit.baseInfo=Ext.create('Foss.partnerPayStatementEdit.BaseInfo');
    //对账单面表格上面的操作按钮panel
    writeoff.partnerPayStatementEdit.operateButtonPanel = Ext.create('Foss.partnerPayStatementEdit.OperateButton');
    //付款单页面上的基本信息
    writeoff.partnerPayStatementEdit.paymentBaseInfoPanel = Ext.create('Foss.partnerPayStatementEdit.PaymentBaseInfoPanel');
    //付款单面表格上面的操作按钮panel
    writeoff.partnerPayStatementEdit.payOperateButtonPanel = Ext.create('Foss.partnerPayStatementEdit.PayOperateButton');
    //添加对账单明细grid
 	writeoff.partnerPayStatementEdit.addEntryGrid =  Ext.create('Foss.partnerPayStatementEdit.StatementEntryEditAddGrid');
    //对账单明细的grid
	writeoff.partnerPayStatementEdit.partnerPayStatementEditGrid = Ext.create('Foss.partnerPayStatementEdit.StatementEntryEditGrid', {
            dockedItems: [{
	                xtype: 'toolbar',
	                dock: 'top',
	                layout: 'column',
	                items: [{
	                	xtype: 'button',
	                    text: '批量删除',
	                    columnWidth: .1,
	                    handler: writeoff.partnerPayStatementEdit.BatchRemoveStatementEntry
	                },{
	                    xtype: 'container',
	                    border: false,
	                    html: '&nbsp;',
	                    columnWidth: .8
	                }, {
	                    xtype: 'button',
	                    text: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.addStatementEntry'),
	                    columnWidth: .1,
	                    handler: function(){
	                    	var baseInfoForm = writeoff.partnerPayStatementEdit.baseInfo;
	                        var statementStatus = baseInfoForm.getForm().findField("statementStatus").getValue();
	                        //如果对账单为已确认，则弹出提示
	                        if (statementStatus == writeoff.STATEMENTCONFIRMSTATUS_Y) {
	                            Ext.Msg.alert(writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.common.alert'),
	                                    writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.statementCommon.auditToAddStatementEntryWarning'),writeoff.partnerPayStatementEdit.editStatementEntryWindow);
	                            return false;
	                        }
	                        writeoff.partnerPayStatementEdit.addEntryGrid.store.removeAll();
	                        //显示
	                        writeoff.partnerPayStatementEdit.addEntryGrid.show();
	                    }
	                }]
	            }]
		});
	
	/**
	 * 整个付款单的窗口
	 */
	 Ext.define('Foss.partnerPayStatementEdit.PaymentBillWindow',{
	 	extend:'Ext.window.Window',
	 	title:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPayment'),
	 	width:stl.SCREENWIDTH*0.7,
	 	modal:true,
	 	constrainHeader: true,
	 	closeAction:'destory',
	 	loadMask:null,//遮罩
	 	getLoadMask:function(){
	 		var me = this;
	 		//获取遮罩效果
	 		if(Ext.isEmpty(me.loadMask)){
	 			me.loadMask = new Ext.LoadMask(me, {
	 				msg:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.savePaymentMask'),
	 			    removeMask : true// 完成后移除
	 			});
	 		}
	 		return me.loadMask;
	 	},
	 	listeners:{
	 		'close':function(){
	 			writeoff.partnerPayStatementEdit.batchNo = null;
	 			writeoff.partnerPayStatementEdit.statementBillNos = null;
	 			writeoff.partnerPayStatementEdit.paymentBaseInfoPanel.getForm().reset();
	 			//如果不是从明细界面打开的付款界面 则刷新对账单数据
	 			if(!writeoff.partnerPayStatementEdit.pageType){
	 				var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	 				grid.store.load(); 
	 			}
	 		}
	 	},
	 	items:[writeoff.partnerPayStatementEdit.paymentBaseInfoPanel,
	 		 writeoff.partnerPayStatementEdit.payOperateButtonPanel
	 	]
	 });
	
    /**
     * 声明对账单明细界面
     */
    Ext.define('Foss.partnerPayStatementEdit.EditStatementEntryWindow', {
        extend: 'Ext.window.Window',
        getBaseInfo: function () {
            return writeoff.partnerPayStatementEdit.baseInfo;
        },
        width: stl.SCREENWIDTH * 0.9,
        modal: true,
        constrainHeader: true,
        closeAction: 'destory',
        listeners:{
	 		'close':function(){
	 			//明细界面关闭设置标志
 				 writeoff.partnerPayStatementEdit.pageType = false;
	 			 var grid = Ext.getCmp('T_writeoff-partnerPayStatementEdit_content').getStatementQueryGrid();
	 			 writeoff.partnerPayStatementEdit.addEntryGrid.hide();
	 			 grid.store.load(); 
	 		}
	 	},
        items: [writeoff.partnerPayStatementEdit.baseInfo,
                writeoff.partnerPayStatementEdit.operateButtonPanel,
                writeoff.partnerPayStatementEdit.partnerPayStatementEditGrid,
                writeoff.partnerPayStatementEdit.addEntryGrid
        ]
    });
    
    //发票信息
    writeoff.partnerPayStatementEdit.myPanel=new Ext.Panel({
    	frame:true,
		colspan:3,
		layout:'column',
		border:true,
		hidden:true,
		margin:'6 2 0 10',
		items:[{//费用承担部门
	 		xtype: 'textfield',
	 		readOnly:true,
            name:'costDeptCode',
        	fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.applyOrgName'),
            columnWidth:1
		},{//是否增值税专用发票
			xtype: 'combobox',
			name:'isTaxinvoice',
	        fieldLabel: writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.isVATInvoices'),
			store:Ext.create('Foss.partnerPayStatementEdit.applyVATInvoiceStore'),
			queryModel:'local',
			displayField:'name',
			valueField:'value',
			labelWidth:130,
			//隐藏组件
			hidden:true,
			//是否为增值税专用发票默认值改为 否
			value : false,
//			value : true,
			labelAlign:'right',
	        columnWidth:.3,
	        forceSelection :true
//	        listeners:{
//	        	'change':function(th, newValue, oldValue){
//	        		var form = th.up('form').getForm();
//	        		//不为是隐藏后面的几项
//	        		if(newValue != true){
//	 					form.findField('vatInvoice').hide();
//	 					form.findField('taxfreePrice').hide();
//	 					form.findField('taxPrice').hide();
//	 					form.findField('taxpayerNumber').hide();
//	        		}else{
//	        			form.findField('vatInvoice').show();
//	 					form.findField('taxfreePrice').show();
//	 					form.findField('taxPrice').show();
//	 					form.findField('taxpayerNumber').show();
//	        		}
//	        	}
//	        }
		},{
	 		//增值税发票号码
 			xtype:'textfield',
 			//隐藏组件
			hidden:true,
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.vATInvoicesNo'),
	 		columnWidth:.4,
	 		labelWidth:130,
	 		labelAlign:'right',
	 		name:'vatInvoice'
	 	},{
	 		//不含税金额
			xtype:'textfield',
			readOnly:true,
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.noTaxesAmount'),
	 		columnWidth:.3, 
	 		labelWidth:100,
	 		labelAlign:'right',
	 		name:'taxfreePrice'
	 	},{
	 		//税额
 			xtype:'textfield',
	 		//隐藏组件
			hidden:true,
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.taxesAmount'),
	 		columnWidth:.3,
	 		labelWidth:130,
	 		labelAlign:'right',
	 		name:'taxPrice',
 			 listeners:{
 				'change':function(th,newValue,oldValue){
 					var form = th.up('form').getForm();
 					var amount = form.findField('amount').getValue();
 					//判断合法性
 	        		if(isNaN(newValue.trim())){
 	        			this.setValue(oldValue);
 	        			return false;
 	        		}
 	        		//判断税额是否大于付款金额
 	        		if(parseFloat(newValue) > parseFloat(amount)){
 	        			this.setValue(oldValue);
 	        			return false;
 	        		}
 	        		//得到相减的金额
 	        		var value = stl.subtrAmountPoint(amount,newValue);
 	        		form.findField('taxfreePrice').setValue(value);
 	        	}
 	        }
	 	},{
	 		//纳税人识别编码码
 			xtype:'textfield',
 			//隐藏组件
			hidden:true,
 			columnWidth:.4,
 			labelWidth:130,
 			labelAlign:'right',
	 		fieldLabel:writeoff.partnerPayStatementEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.taxpayerNo'),
	 		name:'taxpayerNumber'
	 	}]
    });
    
	//创建对账单查询panel	
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-partnerPayStatementEdit_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
			return statementQueryInfoTab;
		},
		getHiddenField:function(){
			return queryEditHiddenfield;
		},
		getStatementQueryGrid:function(){
			return statementQueryGrid;
		},
		items: [statementQueryInfoTab,statementQueryGrid],
		renderTo: 'T_writeoff-partnerPayStatementEdit-body',
		listeners:{
			'boxready':function(th){
				var roles = stl.currentUserDepts;
				var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
				var dept = queryByDateTab.getForm().findField("statementOrgCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = dept.store;
					var  key = dept.displayField + '';
					var value = dept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					dept.setValue(valueText);
				}
				
				var queryByFailingInvoice = th.getQueryInfoTab().items.items[3].items.items[0];
				var failingInvoiceDept = queryByFailingInvoice.getForm().findField("failingInvoiceOrgCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = failingInvoiceDept.store;
					var  key = failingInvoiceDept.displayField + '';
					var value = failingInvoiceDept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					failingInvoiceDept.setValue(valueText);
				}
				if(writeoff.partnerPayStatementEdit.ddw == 'ddw'){
					th.getQueryInfoTab().setActiveTab(3);
					var form = th.getQueryInfoTab().items.items[3].items.items[0].getForm();
					writeoff.partnerPayStatementEdit.statementFailingInvoice(form);
				}
				
				//add by 269044-zhurongrong
				var customerCode = queryByDateTab.getForm().findField("customerCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = customerCode.store;
					var  key = customerCode.displayField + '';
					var value = customerCode.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					customerCode.setValue(valueText);
				}
			}
		}
	});
});