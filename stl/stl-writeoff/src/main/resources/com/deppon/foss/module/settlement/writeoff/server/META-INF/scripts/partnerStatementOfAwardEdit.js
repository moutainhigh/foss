writeoff.PAGEFROM_STATEMENTENTRY = 'statementEntry';//从对账单明细界面来
writeoff.PAGEFROM_STATEMENTEDIT = 'statementEdit';//从查询对账单界面
//对账单子类型
writeoff.partnerStatementOfAwardEdit.awardBillType=function(val){
    switch (val) {
    case  "PBPA":
        return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardSta');
   }
};
//合伙人单据父类型
writeoff.partnerStatementOfAwardEdit.billParentType=function(val){
    switch (val) {
    case  "10.YS":
        return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.billReceivable');
    case  "20.YF":
        return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.billpayable');
   }
};
//合伙人单据子类型
writeoff.partnerStatementOfAwardEdit.billType=function(val){
    switch (val) {
        case  "PB":
            return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardPayAward');
        case  "PLE":
            return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardPayLand');
        case  "PP":
            return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecDeduct');
        case  "PER":
            return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecError');
        case  "PTF":
            return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardRecTrain');
    }
};

//审核状态
writeoff.partnerStatementOfAwardEdit.auditStatus=function(val){
    switch (val) {
    case  "NA":
        return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardAuditN');
    case  "AA":
        return writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.pAwardAuditY');
    }
};


//开发环境地址是：http://192.168.17.221:8881/fssc
//测试环境UAT地址是：http://192.168.20.251:8080/fssc
//测试环境SIT地址是：http://192.168.20.148:8080/fssc
//http://192.168.11.59:8881/claim/attachment/query.action
writeoff.partnerStatementOfAwardEdit.fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
//附件查询地址
var attachmentQueryUrl = 'http://'+writeoff.partnerStatementOfAwardEdit.fssc+'/fssc/attachment/query.action';
//附件下载地址
var attachmentDownLoadUrl = 'http://'+writeoff.partnerStatementOfAwardEdit.fssc+'/fssc/attachment/download.action';
//附件删除地址
var attachmentDeleteUrl = 'http://'+writeoff.partnerStatementOfAwardEdit.fssc+'/fssc/attachment/delete.action';
//附件上传地址
var attachmentUploadUrl = 'http://'+writeoff.partnerStatementOfAwardEdit.fssc+'/fssc/attachment/upload.action';

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
            Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'), data.message);
            return;
        }
        if (upfilegridstore) {
            upfilegridstore.loadPage(1);
        }
    },
    failure : function() {
        Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'), 
        		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.serverErrTryAgain'));
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
        text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.sequence'),
        width : 50
    }, {
        text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.attachmentName'),
        dataIndex : 'attachguid',
        width : 300,
        flex : 1,
        renderer : me.downLoadFile
    }, {
        text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.attachmentSize'),
        dataIndex : 'attachsize',
        width : 100
    }, {
        xtype : 'actioncolumn',
        text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.operate'),
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
        Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
        		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.registerFormAndUploadAttachment'));
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
            Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
            		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.uploadFileFailure!'));
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
            fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.upload'),
            fieldStyle : 'border: solid 1px #B5B8C8!important;background: transparent !important;',
            buttonConfig : {
                text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.browser')
            },
            blankText : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.notNull'),
            allowBlank : false
        }, {
            text : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.upload'),
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

Ext.define('Foss.partnerStatementOfAwardEdit.uploadWindow', {
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
			writeoff.partnerStatementOfAwardEdit.upfilepanel = this.upfilepanel;
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
    writeoff.partnerStatementOfAwardEdit.paymentUpload = function(){
	writeoff.partnerStatementOfAwardEdit.uploadWindow =Ext.create('Foss.partnerStatementOfAwardEdit.uploadWindow');
	writeoff.partnerStatementOfAwardEdit.upfilepanel.setUserCode(writeoff.partnerStatementOfAwardEdit.userCode);
	writeoff.partnerStatementOfAwardEdit.upfilepanel.setAttachRelId(writeoff.partnerStatementOfAwardEdit.batchNo);
	writeoff.partnerStatementOfAwardEdit.uploadWindow.show();
}


//定义按合伙人制作查询form
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.producedByPartnerForm',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
        margin :'5 5 5 0',
        labelWidth :100
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[{
    	xtype:'linkagecomboselector',
        eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
        itemId : 'Foss_baseinfo_BigRegion_ID',
        store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
        columnWidth:.3,
        fieldLabel :writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.largeRegion'),
        name : 'largeRegion',
        isPaging: true,
        allowBlank : true,
        value:'',
        minChars : 0,
        displayField : 'name',// 显示名称
        valueField : 'code',
        minChars : 0,
        queryParam : 'commonOrgVo.name'
    },{
        xtype:'linkagecomboselector',
        itemId : 'Foss_baseinfo_SmallRegion_ID',
        eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
        store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
        columnWidth:.3,
        fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.smallRegion'),
        name : 'smallRegion',
        allowBlank : true,
        isPaging: true,
        parentParamsAndItemIds : {
            'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
        },// 此处城市不需要传入
        minChars : 0,
        displayField : 'name',// 显示名称
        valueField : 'code',
        minChars : 0,
        queryParam : 'commonOrgVo.name'
    },{
        xtype : 'dynamicorgcombselector',
        fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.statementOrgName'),
        name : 'orgCode',
        value : stl.currentDept.name,
        columnWidth:.3,
        labelWidth : 85,
        listWidth : 300,// 设置下拉框宽度
        isPaging : true
    },{
        xtype:'datefield',
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.airPaymentPayable.startDate'),  //开始日期
        allowBlank:false,
        name:'periodBeginDate',
        format:'Y-m-d',
        value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
    },{
        xtype:'datefield',
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.airPaymentPayable.endDate'),      //结束日期
        name:'periodEndDate',
        format:'Y-m-d',
        value:stl.getTargetDate(new Date(),+1)
    },{
        xtype: 'commonsaledepartmentselector',
        name:'customerCode',
        fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),  //合伙人名称
        allowBlank: true,
        listWidth:300,//设置下拉框宽度
        isPaging:true //分页
    },{
        xtype : 'combobox',
        name : 'confirmStatus',
        fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),//对账单状态
         store:Ext.create('Ext.data.Store', {
             fields: ['valueName', 'valueCode'],
             data : [
                 {"valueName":"全部", "valueCode":""},
                 {"valueName":"已确认", "valueCode":"Y"},
                 {"valueName":"未确认", "valueCode":"N"}
             ]
         }),
        queryModel : 'local',
        displayField : 'valueName',
        valueField : 'valueCode',
        editable:false,
        value:''
    },{
        xtype : 'combobox',
        name : 'settleStatus',
        fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),//结账状态
         store:Ext.create('Ext.data.Store', {
             fields: ['valueName', 'valueCode'],
             data : [
                 {"valueName":"全部", "valueCode":""},
                 {"valueName":"已结清", "valueCode":"statementSettleStatus"},
                 {"valueName":"未结清", "valueCode":"statementUnSettleStatus"}
             ]
         }),
        queryModel : 'local',
        displayField : 'valueName',
        valueField : 'valueCode',
        editable:false,
        value:''
    },{
        xtype:'container'
    },{
        xtype:'container'
    },{
        xtype:'container'
    },{
        xtype:'container'
    },{
        xtype:'button',
        align:'center',
        width:80,
        text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset'), //重置
        handler:function(){
            this.up('form').getForm().reset();
        }
    },{
        xtype:'container'
    },{
        text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
        width:80,
        cls:'yellow_button',
        xtype:'button',
        handler:function(){
            //点击查询触发事件
            writeoff.partnerStatementOfAwardEdit.store.moveFirst();
        }
    }]
});

//定义按对账单号制作form
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.productionOrderForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),//对账单单号
            name: 'waybillNo',
            height : 80,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10'/*,
                autoEl:{
                    tag:'div',
                    html:'<span style="color:red;">'+'（备注：应收单号、应付单号、运单号、差错编号）'+'</span>'
                }*/
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset'), //重置
                width:80,
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                    text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
                    columnWidth:.1,
                    width:80,
                    cls:'yellow_button',
                    handler:function(){
                        //点击查询触发事件
                        writeoff.partnerStatementOfAwardEdit.store.moveFirst();
                    }

                }]
        }]
});

//定义按对运单号制作form
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.waybillOrderForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.waybillNo'),//运单单号
            name: 'waybillNo',
            height : 80,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10'/*,
                 autoEl:{
                 tag:'div',
                 html:'<span style="color:red;">'+'（备注：应收单号、应付单号、运单号、差错编号）'+'</span>'
                 }*/
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
                columnWidth:.1,
                cls:'yellow_button',
                handler:function(){
                    //点击查询触发事件
                    writeoff.partnerStatementOfAwardEdit.store.moveFirst();
                }

            }]
        }]
});

//定义按部门查询form
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.departmentForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :1
    },
    items:[
        {
            xtype : 'dynamicorgcombselector',
            fieldLabel : writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),
            name : 'orgCode',
            value : stl.currentDept.name,
            columnWidth:.3,
            labelWidth : 85,
            listWidth : 300,// 设置下拉框宽度
            isPaging : true
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.200,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },,{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
                columnWidth:.200,
                cls:'yellow_button',
                handler:function(){
                    //点击查询触发事件
                    writeoff.partnerStatementOfAwardEdit.store.moveFirst();
                }

            }]
        }]
});

//定义对账单model
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditModel',{
    extend:'Ext.data.Model',
    fields:[
        //对账单单号
        {name:'statementBillNo'},
        //部门编码
        {name:'createOrgCode'},
        //部门名称
        {name:'createOrgName'},
        //公司编码
        {name:'companyCode'},
        //公司名称
        {name:'companyName'},
        //部门标杆编码
        {name:'unifiedCode'},
        //客户编码
        {name:'customerCode'},
        //客户名称
        {name:'customerName'},
        //对账单子类型
        {name:'billType'},
        //本期发生金额
        {name:'periodAmount'},
        //本期应收金额
        {name:'periodRecAmount'},
        //本期应付金额
        {name:'periodPayAmount'},
        //本期剩余应收金额
        {name:'periodUnverifyRecAmount'},
        //本期剩余应付金额
        {name:'periodUnverifyPayAmount'},
        //账期开始日期
        {name:'periodBeginDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //账期结束日期
        {name:'periodEndDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //结账次数
        {name:'settleNum'},
        //确认时间1
        {name:'confirmTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //子公司账号
        {name:'companyAccountBankNo'},
        //开户名
        {name:'accountUserName'},
        //支行名称
        {name:'bankBranchName'},
        //对账单状态
        {name:'confirmStatus'},
        //本期已还金额
        {name:'paidAmount'},
        //本期未还金额
        {name:'unpaidAmount'},
        //对账单描述
        {name:'notes'},
        //创建时间
        {name:'createTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }}
    ]
});

//定义对账单store
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardStore',{
    extend:'Ext.data.Store',
    model:'Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditModel',
    pageSize:20,
    //是否自动查询
    autoLoad: false,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryPAwardStatement.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        beforeload : function(store, operation, eOpts){//查询事件
            //查询条件form中的值
            var queryParams;
            var params;
            var waybillNoList = new Array();       //订单号的集合
            var flowType = writeoff.partnerStatementOfAwardEdit.queryTab.getActiveTab().itemId;//定义当前活动Tab
            //按合伙人查询
            if(flowType=='producedByStatementPartnerId'){
                queryParams = writeoff.partnerStatementOfAwardEdit.producedByPartnerForm.getValues();
                var tab = writeoff.STATEMENTQUERYTAB_BYPARTNER;
                params={
            		'partnerStatementOfAwardVo.partnerStatementOfAwardDto.largeRegion': queryParams.largeRegion,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.smallRegion': queryParams.smallRegion,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.orgCode':queryParams.orgCode,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate':queryParams.periodBeginDate,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate':queryParams.periodEndDate,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode':queryParams.customerCode,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.confirmStatus':queryParams.confirmStatus,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.settleStatus':queryParams.settleStatus,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            //按照对账单号
            if(flowType=='productionStatementOrderId'){
                queryParams = writeoff.partnerStatementOfAwardEdit.productionOrderForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                var tab = writeoff.STATEMENTQUERYTAB_BYSTATEMENT_NO;
                params={
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.statementBillNos': waybillNoList,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            //按运单号
            if(flowType=='waybillStatementOrderFormId'){
                queryParams = writeoff.partnerStatementOfAwardEdit.waybillOrderForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                var tab = writeoff.STATEMENTQUERYTAB_BYWAYBILL_NO;
                params={
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.wayBillNos': waybillNoList,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            //按部门查询
            if(flowType=='departmentStatementFormId'){
                queryParams = writeoff.partnerStatementOfAwardEdit.departmentForm.getValues();
                orgCode = queryParams.orgCode;
                var tab = writeoff.STATEMENTQUERYTAB_BYDEPARTMENT;
                params={
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.orgCode': orgCode,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            if(data==undefined){
                Ext.MessageBox.alert(Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"系统错误"));
                return ;
            }
            if(data.success==false){
                Ext.MessageBox.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),data.message);
                return ;
            }
            //总条数
            if(data.totalCount!=null) {
                Ext.getCmp('Foss.partnerStatementOfAwardEdit.statement.total').setValue(data.totalCount);
            }else{
                Ext.getCmp('Foss.partnerStatementOfAwardEdit.statement.total').setValue('0');
            }
        }
    }
});



/**
 * 定义单据子类型的model
 */
Ext.define('Foss.statementbill.queryComboModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name'
    }, {
        name: 'value'
    }]
});

/**
 * 定义对账单操作列store
 */
Ext.define('Foss.statementbill.operateColumnStore',{
    extened:'Ext.data.Store',
    model:'Foss.statementbill.queryComboModel',
    data:{
        'items':[     
            {name:'反确认',value:'1'},
            {name:'打印回执',value:'2'},
            {name:'明细',value:'3'},
            {name:'扣款',value:'4'},
            {name:'付款',value:'5'},
            {name:'确认',value:'6'}
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
 * 取消操作
 */
writeoff.partnerStatementOfAwardEdit.paymentCancel = function(){
	var win = this.up('window');
	Ext.Msg.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.isExistPaymentPage'),function(o){
		//提示是否要删除
		if(o=='yes'){
			if(!Ext.isEmpty(win)){
				win.hide();
			}
		}
	});
}

//付款提交操作
writeoff.partnerStatementOfAwardEdit.paymentCommit = function(){
	//获取对账单列表
	var grid = writeoff.partnerStatementOfAwardEdit.grid;
	//付款窗口
	var paymentBillWindow = writeoff.partnerStatementOfAwardEdit.paymentBillWindow;
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
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.pawriteoff.paymentOrgNameIsNullWarning'));
 		return false;
 	}
 	
 	//部门编码不能为空
 	if(Ext.isEmpty(paymentOrgCode)){
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentOrgCodeIsNullWarning'));
 		return false;
 	}
 	
 	//公司名称不能为空
 	if(Ext.isEmpty(paymentCompanyName)){
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyNameIsNullWarning'));
 		return false;
 	}
 	
 	//公司编码不能为空
 	if(Ext.isEmpty(paymentCompanyCode)){
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyCodeIsNullWarning'));
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
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentTypeIsNullWarning'));
 		return false;
 	}
 	//金额判断
/* 	if(Ext.isEmpty(amount) || amount<=0){
 		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cashToPayAmountLimitWarning'));
 		return false;
 	}*/
 	//如果为电汇
 	if(paymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
 		if(Ext.isEmpty(accountNo)||Ext.isEmpty(provinceCode)
 			||Ext.isEmpty(cityCode)||Ext.isEmpty(bankHqCode)
 			||Ext.isEmpty(bankBranchCode)||Ext.isEmpty(accountType)){
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankInfoIsNullWarning'));
 			return false;
 		}
 		//校验界面输入条件
 		if(!paymentBillForm.getForm().isValid()){
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 					writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.validateFailAlert'));
 			return false;
 		}
 		//判断如果为电汇，收款人不能为空
 		if(Ext.isEmpty(payeeName)||Ext.isEmpty(mobilePhone)){
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.payeeInfoIsNullWarning'));
 			return false;	
 		}
 		//发票抬头判断
 		if(Ext.isEmpty(invoiceHeadCode)||Ext.isEmpty(invoiceHeadName)){
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceTitleIsNullWarning'));
 			return false;	
 		}
 	}
 	//声明传入action参数
 	var vo = new Object();
 	//此处需要更新一下模型
 	var data = paymentBillForm.getForm().getValues();
 	
 	//获取付款单头信息即界面form表单
 	vo.paymentEntity = data;
 	if(null != writeoff.partnerStatementOfAwardEdit.batchNo){
 		vo.paymentEntity.batchNo=writeoff.partnerStatementOfAwardEdit.batchNo;
 	}
 	vo.statementBillNo=statementBillNo;
 	//遮罩窗口
 	paymentBillWindow.getLoadMask().show();
 	//提交
 	Ext.Ajax.request({
 		url:ContextPath.STL_WEB + '/pay/pAwardToPayment.action',
 		method:'POST',
 		jsonData:{
 			'vo':vo
 		},
 		success:function(response){
 			//获取返回结果
 			var result = Ext.decode(response.responseText);	
 			//获取付款单号
 			var paymentNo = result.vo.paymentEntity.paymentNo;
 			//遮罩窗口
 			paymentBillWindow.getLoadMask().hide();
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentSuccess')
 				+paymentNo);
 			//关闭窗口
 			paymentBillWindow.close();
 			//获取基本信息未还金额，设置为0
	        writeoff.partnerStatementOfAwardEdit.BaseInfo.form.findField('unpaidAmount').setValue('0');
 			/*Ext.getCmp('window.Window.partnerStatementOfAwardEdit.detail').close();*/
 			grid.store.load();
 		},
 		exception:function(response){
 			var result = Ext.decode(response.responseText);
 			//隐藏掉遮罩
 			paymentBillWindow.getLoadMask().hide();
 			//弹出异常提示
 			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
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

//定义付款单form
Ext.define('Foss.partnerStatementOfAwardEdit.AddPaymentFormPanel',{
 	extend:'Ext.form.Panel',
 	title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentBill'),
 	frame:true,
 	bodyCls: 'autoHeight',
 	cls: 'autoHeight',
 	defaults:{
 		labelWidth:75,
 		readOnly:true,
 		columnWidth:.3
 	},
 	defaultType:'textfield',
 	layout:'column',
 	items:[{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerCode'),
		name:'customerCode'
	},{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerName'),
		name:'customerName'
	},{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),
 		name:'paymentOrgName'
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),
 		name:'paymentOrgCode',
 		hidden:true
 	},{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),
		name:'statementBillNo'
	},{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.currentPaymentAmount'),
		name:'amount',
		labelWidth:90,
		xtype:'numberfield'
	},{
		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),
 		name:'paymentCompanyName'
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),
 		name:'paymentCompanyCode',
 		hidden:true
 	},{
 		xtype:'combobox',
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentType'),
 		name:'paymentType',
     	editable:false,
 		store:null,
 		queryModel:'local',
 		displayField:'valueName',
 		valueField:'valueCode',
     	columnWidth:.3,
     	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,[writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER]),
     	readOnly:false,
 		value:writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
 	},{
 		xtype:'textfield',
 		readOnly:false,
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhone'),
 		regex:/^1[3|4|5|7|8][0-9]\d{8}$/,
 		regexText:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhoneRegex'),
 		name:'mobilePhone',
 		allowBlank:false
 	},{
 		xtype:'commonsubsidiaryselector',
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceHeadCode'),
 		readOnly:false,
 		name:'invoiceHeadCode',
 		allowBlank:false
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountNo'),
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
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.provinceName'),
 		name:'provinceName'
 	},{
 		fieldLabel:'cityCode',
 		name:'cityCode',
 		hidden:true
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cityName'),
 		name:'cityName'
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankHqName'),
 		name:'bankHqName'
 	},{
 		fieldLabel:'bankHqCode',
 		name:'bankHqCode',
 		hidden:true
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchName'),
 		name:'bankBranchName'
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchCode'),
 		name:'bankBranchCode'
 	},{
 		fieldLabel:'accountType',
 		name:'accountType',
 		hidden:true
 	},{
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountType'),
 		name:'accountTypeName'
 	},{
 		fieldLabel:'payeeName',
 		name:'payeeName',
 		hidden:true
 	},{
 		fieldLabel:'payeeCode',
 		name:'payeeCode',
 		hidden:true
 	},{
 		xtype:'textareafield',
 		columnWidth:.9,
 		height:60,
 		disabled:false,
 		readOnly:false,
 		autoScroll:true,
 		fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.notes'),
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
 			text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.upload'),
 			name:'uploadddw',
 			columnWidth:.1,
 			handler:writeoff.partnerStatementOfAwardEdit.paymentUpload
 		},{
 			xtype:'container',
 			border:false,
 			html:'&nbsp;',
 			columnWidth:.6
 		},{
 			text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.commit'),
 			columnWidth:.1,
 			handler:writeoff.partnerStatementOfAwardEdit.paymentCommit
 		},{
 			text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cancel'),
 			columnWidth:.1,
 			handler:writeoff.partnerStatementOfAwardEdit.paymentCancel
 		}]
 	}]
 });

//定义录入付款单窗口
Ext.define('Foss.partnerStatementOfAwardEdit.paymentBillWindow',{
 	extend:'Ext.window.Window',
 	title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPayment'),
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
 				msg:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.savePaymentMask'),
 			    removeMask : true// 完成后移除
 			});
 		}
 		return me.loadMask;
 	},
 	listeners:{
 		'close':function(){
 			writeoff.partnerStatementOfAwardEdit.batchNo = null;
 		}
 	},
 	items:[Ext.create('Foss.partnerStatementOfAwardEdit.AddPaymentFormPanel')]
 });

//付款操作
writeoff.partnerStatementOfAwardEdit.payment = function(){
	//获取对账单选中行
	var queryParams = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getLastSelected();
//	//获取对账单明细基本信息的确认状态
//	var confirmStatus = writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").getRawValue();
	//录入付款单窗口
	if(Ext.isEmpty(writeoff.partnerStatementOfAwardEdit.paymentBillWindow)){
		writeoff.partnerStatementOfAwardEdit.paymentBillWindow = Ext.create('Foss.partnerStatementOfAwardEdit.paymentBillWindow');
	}
	var paymentBillWindow = writeoff.partnerStatementOfAwardEdit.paymentBillWindow;
	
	//付款单FORM
	var paymentBillForm = paymentBillWindow.items.items[0].getForm();
	//本期未还金额
//	var unpaidAmount = queryParams.get('unpaidAmount');
	//合伙人编码
	var contractOrgCode = queryParams.get('customerCode');
	//合伙人名称
	var contractOrgName = queryParams.get('customerName');
	//对账单号
	var statementBillNo = queryParams.get('statementBillNo');
	//部门编码
	var createOrgCode = queryParams.get('createOrgCode');
	//部门名称
	var createOrgName = queryParams.get('createOrgName');
	//公司编码
	var companyCode = queryParams.get('companyCode');
	//公司名称
	var companyName = queryParams.get('companyName');
	//确认状态
	var confirmStatus = queryParams.get('confirmStatus');
	//重置付款单
	paymentBillForm.reset();
	//剩余未还金额
	if(writeoff.partnerStatementOfAwardEdit.payPeriodAmount==undefined){
		var unpaidAmount = queryParams.get('unpaidAmount');
		paymentBillForm.findField('amount').setValue(unpaidAmount);
	}else{
		var unpaidAmount = writeoff.partnerStatementOfAwardEdit.payPeriodAmount;
		paymentBillForm.findField('amount').setValue(unpaidAmount);
		writeoff.partnerStatementOfAwardEdit.payPeriodAmount = undefined;
	}

	//判断对账单状态--明细页面非确认状态走后台校验
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N && writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").getRawValue()==''){
		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
		return false;
	}
	
	//已还金额判断
	if(unpaidAmount == 0){
		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.verifyPayment'));
		return false;
	}
	//设置付款界面
	paymentBillForm.findField('customerName').setValue(contractOrgName);
	paymentBillForm.findField('customerCode').setValue(contractOrgCode);
	paymentBillForm.findField('statementBillNo').setValue(statementBillNo);
	paymentBillForm.findField('paymentOrgCode').setValue(createOrgCode);
	paymentBillForm.findField('paymentOrgName').setValue(createOrgName);
	paymentBillForm.findField('paymentCompanyCode').setValue(companyCode);
	paymentBillForm.findField('paymentCompanyName').setValue(companyName);
//	paymentBillForm.findField('amount').setValue(unpaidAmount);
	paymentBillForm.findField('sourceBillType').setValue(writeoff.SOURCE_BILL_TYPE__STATEMENT);
	Ext.Ajax.request({
		//获取批次号和当前用户 
		url:ContextPath.STL_WEB + '/pay/uploadAppendix.action',
		success:function(response){
			var result = Ext.decode(response.responseText);
			writeoff.partnerStatementOfAwardEdit.batchNo = result.billPaymentVo.paymentEntity.batchNo;
			writeoff.partnerStatementOfAwardEdit.userCode = result.billPaymentVo.paymentEntity.createUserCode;
		}
	});
	paymentBillWindow.show();
}

//扣款提交
writeoff.partnerStatementOfAwardEdit.deduct = function(){
	var gridList = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getSelection();
	var statementBillNoList = new Array();
	//如果选取的gride不为空把对账单放入list
	if(gridList.length>1){
		for(var i=0;i<gridList.length;i++){
			statementBillNoList.push(gridList[i].data.statementBillNo);
		}
	}
	if(gridList.length == 1){
		statementBillNoList.push(gridList[0].data.statementBillNo);
	}
	if(gridList.length == 0){
		Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
				writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.choseOne'));
		return false;
	}
	
	Ext.Ajax.request({
		//获取批次号和当前用户 
		url:ContextPath.STL_WEB + '/pay/pAwardToDeduct.action',
		method:'POST',
 		jsonData:{
 			'dto':{
 				'statementBillNos':statementBillNoList
 			}
 		},
		success:function(response){
	  	  /*writeoff.partnerStatementOfAwardEdit.detail.store.load();*/
	        writeoff.partnerStatementOfAwardEdit.grid.store.load();
	        //获取基本信息未还金额，设置为0
	        writeoff.partnerStatementOfAwardEdit.BaseInfo.form.findField('unpaidAmount').setValue('0');
	        Ext.ux.Toast.msg(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"扣款成功", 'ok', 1000);
	    },
	    exception:function(response){
	        var result = Ext.decode(response.responseText);
	        Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
	    }
	});
	
}

//对账单明细确认
detailConfirmRecord = function(){
	var confirmStatus = writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").getRawValue();
	//判断对账单状态
		if('N'==confirmStatus){
			//调用确认功能
			Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否确认？",function(val){
				if(val=='yes'){
					confirmRecord(writeoff.PAGEFROM_STATEMENTENTRY);
					//writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").setRawValue('Y');
				}else{
					return false;
				}
			});
		}else{
			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"对账单已确认!");
		}
}

//对账单明细反确认
detailUnconfirmRecord = function(){
	var confirmStatus = writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").getRawValue();;
	//判断对账单状态
		if('Y'==confirmStatus){
			Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否反确认？",function(val){
				if(val=='yes'){
					unconfirmRecord(writeoff.PAGEFROM_STATEMENTENTRY);
					//writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").setRawValue('N');
				}else{
					return false;
				}
			});
		}else{
			Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"对账单未确认!");
		}
	}


/**
 * 定义对账单明细操作按钮
 */
Ext.define('Foss.partnerStatementOfAwardEdit.OperateButtonPanel', {
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
        columnWidth: .25
    }, {
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),//确认
        hidden:false,
        handler: detailConfirmRecord
    }, {
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),//反确认
        hidden:false,
        handler: detailUnconfirmRecord
    }, {
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.payment'),//付款
        hidden:false,
        handler:writeoff.partnerStatementOfAwardEdit.payment
        /*handler:function(){
        	Ext.MessageBox.confirm(Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否付款？",function(val){
                if(val=='yes'){
                	writeoff.partnerStatementOfAwardEdit.payment();
                }else{
                    return false;
                }
            });
        }*/
    }, {
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.deduct'),//扣款
        hidden:false,
        handler:function(){
        	Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否扣款？",function(val){
                if(val=='yes'){
             	   writeoff.partnerStatementOfAwardEdit.deduct();
                }else{
                    return false;
                }
            });
        }
    }, {
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.addStatementEntry'),//添加对账单明细
        hidden:true
        /*handler:function(){
        	
        }*/
    },{
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.expDetail'),//导出对账单明细
        hidden:false,
        handler: function(){
            var	columns,
                arrayColumns,
                arrayColumnNames;
            var statementBillNo = new Array();
            //对账单明细grid
            var statementList = Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.statementBillNo').getValue();
            if(undefined!=statementList){
                statementBillNo.push(statementList);
            }
            //转化列头和列明
            columns = writeoff.partnerStatementOfAwardEdit.detail.columns;
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
            //拼接vo，注入到后台
            searchParams = {
                'partnerStatementOfAwardVo.partnerStatementOfAwardDto.statementBillNos':statementBillNo,
                'partnerStatementOfAwardVo.partnerStatementOfAwardDto.arrayColumns':arrayColumns,
                'partnerStatementOfAwardVo.partnerStatementOfAwardDto.arrayColumnNames':arrayColumnNames
            };
            if(!Ext.fly('downloadAttachFileForm')){
                var frm = document.createElement('form');
                frm.id = 'downloadAttachFileForm';
                frm.style.display = 'none';
                document.body.appendChild(frm);
            }
            Ext.Ajax.request({
                url: writeoff.realPath('pAwardStatementDetailToExcel.action'),
                form: Ext.fly('downloadAttachFileForm'),
                method : 'POST',
                params : searchParams,
                isUpload: true,
                success : function(response,options){
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'), 
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.airPaymentPayable.exportSuccess！'));
                }
            });
        }
         }]
});

/**
 * 定义对账单明细基本对账单信息
 */
Ext.define('Foss.partnerStatementOfAwardEdit.BaseInfo',{
    extend:'Ext.form.Panel',
    layout:'column',
    frame:true,
    //hidden:true,
    defaultType:'textfield',
    layout:'column',
    defaults:{
        labelWidth:65,
        margin:'5 5 5 10',
        readOnly:true
    },
    items:[{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.billType'),
        name:'billType',
        xtype:'combo',
        labelWidth:80,
        columnWidth:.22,
        id:'Foss.partnerStatementOfAwardEdit.datil.billType',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode'
    },{
        xtype:'datefield',
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.airPaymentPayable.createTime'),
        name:'createTime',
        id:'Foss.partnerStatementOfAwardEdit.datil.createTime',
        format:'Y-m-d H:i:s',
        columnWidth:.28
    },{
        xtype:'commoncustomerselector',
        //listWidth:380,
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),
        labelWidth:80,
        singlePeopleFlag:'Y',
        name:'contractOrgCode',
        id:'Foss.partnerStatementOfAwardEdit.datil.contractOrgCode',
        columnWidth:.26
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),
        name:'confirmStatus',
        id:'Foss.partnerStatementOfAwardEdit.datil.confirmStatus',
        xtype:'combo',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode',
        columnWidth:.24,
        value:''
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
        // listWidth:380,
        labelWidth:100,
        name:'periodBeginDate',
        id:'Foss.partnerStatementOfAwardEdit.datil.periodBeginDate',
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.22
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.companyName'),
        name:'companyName',
        id:'Foss.partnerStatementOfAwardEdit.datil.companyName',
        columnWidth:.28
    },{
        fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.companyCode'),
        name: 'companyCode',
        id: 'Foss.partnerStatementOfAwardEdit.datil.companyCode',
        hidden:true,
        columnWidth: .28
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),
        name:'contractOrgName',
        labelWidth:80,
        id:'Foss.partnerStatementOfAwardEdit.datil.contractOrgName',
        columnWidth:.26
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmTime'),
        xtype:'datefield',
        name:'confirmTime',
        id:'Foss.partnerStatementOfAwardEdit.datil.confirmTime',
        format:'Y-m-d H:i:s',
        labelWidth:80,
        columnWidth:.20
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.homeStatementAdd.periodEndDate'),
        name:'periodEndDate',
        labelWidth:100,
        id:'Foss.partnerStatementOfAwardEdit.datil.periodEndDate',
        // listWidth:380,
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.22
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
        name:'createOrgName',
        id:'Foss.partnerStatementOfAwardEdit.datil.createOrgName',
        columnWidth:.28
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.createOrgCode'),
        name:'createOrgCode',
        id:'Foss.partnerStatementOfAwardEdit.datil.createOrgCode',
        hidden:true,
        columnWidth:.28
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        name:'statementBillNo',
        id:'Foss.partnerStatementOfAwardEdit.datil.statementBillNo',
        columnWidth:.30
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
        labelWidth:85,
        name:'unifiedCode',
        id:'Foss.partnerStatementOfAwardEdit.datil.unifiedCode',
        columnWidth:.20
    },{
        xtype:'container',
        columnWidth:.30,
        layout:'vbox',
        items:[{
            xtype:'component',
            padding:'0 0 0 0',
            autoEl:{
                tag:'div',
                html:'<span style="color:red;">'+'请在汇款时备注我司的部门标杆编码'+'</span>'
            }
        }]
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),
        name:'companyAccountBankNo',
        id:'Foss.partnerStatementOfAwardEdit.datil.companyAccountBankNo',
        columnWidth:.22
    },{
        xtype:'container',
        columnWidth:.28,
        layout:'vbox',
        items:[{
            xtype:'component',
            padding:'0 0 0 0',
            autoEl:{
                tag:'div',
                html:'<span style="color:red;">'+'此账号为我司唯一的还款账号'+'</span>'
            }
        }]
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
        name:'accountUserName',
        id:'Foss.partnerStatementOfAwardEdit.datil.accountUserName',
        columnWidth:.5
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
        name:'bankBranchName',
        id:'Foss.partnerStatementOfAwardEdit.datil.bankBranchName',
        columnWidth:.48
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.unpaidAmount'),
        labelWidth:110,
        name:'unpaidAmount',
        id:'Foss.partnerStatementOfAwardEdit.datil.unpaidAmount',
        xtype:'numberfield',
        decimalPrecision:2,
        columnWidth:.5,
        value:0
    },{
        fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
        columnWidth:.48,
        name:'settleNum',
        id:'Foss.partnerStatementOfAwardEdit.datil.settleNum',
        xtype:'numberfield',
        value:0
    }]
});

//对账单明细model
Ext.define('Foss.partnerStatementOfAwardEdit.detailModel',{
    extend:'Ext.data.Model',
    fields:[
        //对账单号
        {name:'statementBillNo'},
        //来源单号
        {name: 'sourceBillNo'},
        //原始来源单号
        {name: 'origSourceBillNo'},
        //单据父类型
        {name:'billParentType'},
        //单据子类型
        {name:'billType'},
        //审核状态
        {name:'auditStatus'},
        //金额
        {name:'amount'},
        //已核销金额
        {name:'verifyAmount'},
        //未核销金额
        {name:'unverifyAmount'},
        //部门编码
        {name:'orgCode'},
        //部门名称
        {name:'orgName'},
        //客户\代理编码
        {name:'customerCode'},
        //客户\代理名称
        {name:'customerName'},
        //业务日期
        {name:'businessDate'},
        //创建时间
        {name:'createTime'},
        //备注
        {name:'notes'},
    ]
});

//对账单明细store
Ext.define('Foss.partnerStatementOfAwardEdit.detailStore',{
    extend:'Ext.data.Store',
    model:'Foss.partnerStatementOfAwardEdit.detailModel',
    //是否自动查询
    autoLoad: false,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryPAwardStatementDetail.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        'beforeLoad': function(store, operation, eOpts){//查询事件
            //获取对账单选中行的值
            var params;
            var queryParams = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getLastSelected();
            if(null == queryParams){
            	return;
            }
            //给对账单明细基本信息面板赋值(进来之前先清空)
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.billType').setValue('合伙人奖罚对账单');
            //制单时间
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createTime').setValue(queryParams.get('createTime'));
            //合伙人编码
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgCode').setValue(queryParams.get('customerCode'));
            //账单开始日期
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodBeginDate').setValue(queryParams.get('periodBeginDate'));
            //所属公司
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyName').setValue(queryParams.get('companyName'));
            //所属公司编码
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyCode').setValue(queryParams.get('companyCode'));
            //合伙人名称
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgName').setValue(queryParams.get('customerName'));
            //确认时间
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmTime').setValue(queryParams.get('confirmTime'));
            //账期结束时间
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodEndDate').setValue(queryParams.get('periodEndDate'));
            //所属部门编码
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgCode').setValue(queryParams.get('createOrgCode'));
            //所属部门
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgName').setValue(queryParams.get('createOrgName'));
            //对账单号
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.statementBillNo').setValue(queryParams.get('statementBillNo'));
            //部门标杆编码
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unifiedCode').setValue(queryParams.get('unifiedCode'));
            //账号
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyAccountBankNo').setValue(queryParams.get('companyAccountBankNo'));
            //开户名
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.accountUserName').setValue(queryParams.get('accountUserName'));
            //省市支行
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.bankBranchName').setValue(queryParams.get('bankBranchName'));
            //结账次数
            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.settleNum').setValue(queryParams.get('settleNum'));
            
            //本期剩余还款金额
//            if(writeoff.partnerStatementOfAwardEdit.payPeriodAmount==undefined){
//            	var unpaidAmount = queryParams.get('unpaidAmount');
//        	}else{
//        		var unpaidAmount = writeoff.partnerStatementOfAwardEdit.payPeriodAmount
//        	}
//            Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unpaidAmount').setValue(unpaidAmount);            
            
            if(writeoff.partnerStatementOfAwardEdit.payPeriodAmount==undefined){
            	var unpaidAmount = queryParams.get('unpaidAmount');
            	Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unpaidAmount').setValue(unpaidAmount);
        	}else{
        		var unpaidAmount = writeoff.partnerStatementOfAwardEdit.payPeriodAmount
        		Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unpaidAmount').setValue(unpaidAmount);
//        		writeoff.partnerStatementOfAwardEdit.payPeriodAmount = undefined;
        	}
            
            //对账单状态
            if(writeoff.partnerStatementOfAwardEdit.updateConfirmStatus==undefined){
            	var confirmStatus = queryParams.get('confirmStatus');
            	Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmStatus').setValue(confirmStatus);
        	}else{
        		var confirmStatus = writeoff.partnerStatementOfAwardEdit.updateConfirmStatus
        		Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmStatus').setValue(confirmStatus);
        		writeoff.partnerStatementOfAwardEdit.updateConfirmStatus = undefined;
        	}

            var statementBillNo = queryParams.get('statementBillNo');
            params={
                'partnerStatementOfAwardVo.partnerStatementOfAwardDto.statementBillNo':statementBillNo
            };
            Ext.apply(operation, {
                params :params
            });
        }
    }
});

//添加对账单明细--按客户制作的Tab的form
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'5 5 5 0',
        labelWidth :100
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[{
            xtype: 'textfield',
            name:'settmentNo',
            fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.statementBillNo'),       //对账单单号
            readOnly:true,
            allowBlank: false
        },{
            xtype:'datefield',
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.homeStatementAdd.periodBeginDate'),  //开始日期
            allowBlank:false,
            name:'periodBeginDate',
            format:'Y-m-d',
            value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
        },{
            xtype:'datefield',
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.homeStatementAdd.periodEndDate'),      //结束日期
            name:'periodEndDate',
            format:'Y-m-d',
            value:stl.getTargetDate(new Date(),+1)
        },
        {
            xtype: 'textfield',
            name:'customerName',
            fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),//客户名称
            readOnly:true,
            allowBlank: false
        },
        {
            xtype: 'textfield',
            name:'customerCode',
            fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),       //客户编码
            readOnly:true,
            allowBlank: false
        },
        {
            xtype:'container'
        },
        {
            xtype:'button',
            align:'center',
            width:80,
            text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset')
        },
        {
            xtype:'container'
        },
        {
            text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
            width:80,
            cls:'yellow_button',
            xtype:'button',
            handler:function(){
                var form  = this.up('form').getForm();
                //校验日期
                var periodBeginDate = form.findField('periodBeginDate').getValue();
                var periodEndDate = form.findField('periodEndDate').getValue();
                //校验日期
                if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
                    return false;
                }
                //比较起始日期和结束日期
                var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
                if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
                    return false;
                }else if(compareTwoDate<1){
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
                    return false;
                }
                //点击查询触发事件
                writeoff.partnerStatementOfAwardEdit.messageGrid.moveFirst();
            }
        }
    ]

});

//添加对账单明细--按单号制作
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.productionOrderAddForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.homeStatementAdd.payableNo'),//运单单号
            name: 'waybillNo',
            height : 40,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10',
                autoEl:{
                    tag:'div',
                    html:'<span style="color:red;">'+'（备注：应收单号、应付单号、运单号、差错编号）'+'</span>'
                }
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },,{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },
                {
                    text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.query'),//查询
                    columnWidth:.1,
                    cls:'yellow_button',
                    handler:function(){
                        var me = this;
                        var form = this.up('form').getForm();
                        //需要传入后台的参数
                        var waybillNo = form.findField('waybillNo').getValue();     //单号
                        if(waybillNo==null||waybillNo==""){
                            Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"单号不能为空！");
                            return;
                        }
                        //点击查询触发事件
                        writeoff.partnerStatementOfAwardEdit.messageGrid.moveFirst();
                    }

                }]
        }
    ]

});

writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm=Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm');
writeoff.partnerStatementOfAwardEdit.productionOrderAddForm=Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.productionOrderAddForm');


//添加对账单明细model
Ext.define('Foss.partnerStatementOfAwardEdit.detailAddModel',{
    extend:'Ext.data.Model',
    fields:[
        //对账单号
        {name:'statementBillNo'},
        //来源单号
        {name: 'sourceBillNo'},
        //原始来源单号
        {name: 'origSourceBillNo'},
        //单据父类型
        {name:'billParentType'},
        //单据子类型
        {name:'billType'},
        //审核状态
        {name:'auditStatus'},
        //金额
        {name:'amount'},
        //已核销金额
        {name:'verifyAmount'},
        //未核销金额
        {name:'unverifyAmount'},
        //部门编码
        {name:'orgCode'},
        //部门名称
        {name:'orgName'},
        //客户\代理编码
        {name:'customerCode'},
        //客户\代理名称
        {name:'customerName'},
        //业务日期
        {name:'businessDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //创建时间
        {name:'createTime',type:'date',
                convert:function(value){
                    if(value!=null){
                        var date = new Date(value);
                        return date;
                    }else{
                        return null;
                    }
                }},
        //备注
        {name:'notes'},
    ]
});

/**
 * 添加对账单明细store
 */
Ext.define('Foss.writeoff.detail.partnerStatementOfAwardEditStore',{
    extend:'Ext.data.Store',
    model:'Foss.partnerStatementOfAwardEdit.detailAddModel',
	pageSize : 20,
    //是否自动查询
    autoLoad: false,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryPartnerStatementOfAwardD.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'partnerStatementOfAwardVo.partnerStatementOfAwardDto.partnerStatementOfAwardDList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        beforeload : function(store, operation, eOpts){//查询事件
            //查询条件form中的值
            var queryParams;
            var params;
            var waybillNoList = new Array();       //订单号的集合
            var flowType = writeoff.partnerStatementOfAwardEdit.detailAdd.getActiveTab().itemId;
            
            //按合伙人、日期查询
            if(flowType=='detailProducedByStatementPartnerId'){
            	var tab = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
                queryParams =   writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm.getValues();
                params={
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodBeginDate': queryParams.periodBeginDate,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.periodEndDate': queryParams.periodEndDate,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerName':queryParams.customerName,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.customerCode':queryParams.customerCode,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            //按单号查询
            if(flowType=='detailProductionStatementOrderId'){
            	var tab = writeoff.STATEMENTQUERYTAB_BYNUMBER;
                queryParams = writeoff.partnerStatementOfAwardEdit.productionOrderAddForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                params={ 
                		'partnerStatementOfAwardVo.partnerStatementOfAwardDto.numbers': waybillNoList,
                		'partnerStatementOfAwardVo.partnerStatementOfAwardDto.queryTabType':tab
                }
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            if(data==undefined){
                Ext.MessageBox.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"系统错误");
                return ;
            }
            if(data.success==false){
                Ext.MessageBox.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),data.message);
                return ;
            }
            //总条数
            /*if(data.totalCount!=null) {
                Ext.getCmp('Foss.detail.partnerStatementOfAwardEdit.totalRows').setValue(data.totalCount);
            }else{
                Ext.getCmp('Foss.detail.partnerStatementOfAwardEdit.totalRows').setValue('0');
            }*/
        }
    }
});

/**
 * 添加对账单明细grid
 */
Ext.define('Foss.writeoff.detail.partnerStatementOfAwardEditGrid',{
    extend: 'Ext.grid.Panel',
    title: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.statementEntry'),
    frame: true,
    height: 600,
    //selModel: Ext.create('Ext.selection.CheckboxModel'),
    //store: Ext.create('Foss.receivableStatement.detailStore'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    /*dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        layout:'column',
        items: [{
            xtype:'textfield',
            readOnly:true,
            name:'totalRows',
            id:'Foss.detail.partnerStatementOfAwardEdit.totalRows',
            columnWidth:.9,
            labelWidth:60,
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.totalCount')
        }]
    }],*/
    columns: [{
        xtype: 'actioncolumn',
        width: 50,
        text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.actionColumn'),
        align: 'center',
        items: [{
            iconCls: 'statementBill_add',
            tooltip: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.add'),
            handler: function (grid, rowIndex, colIndex) {
                //获取对账单号
               var  statementBillNo = writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm.getValues().settmentNo;
                var selection = grid.getStore().getAt(rowIndex);
                var sourceBillNo;
                for (var i = 0; i < selection.stores.length; i++) {
                    if(selection.store.getAt(rowIndex).data.id!=null){
                    	sourceBillNo = selection.store.getAt(rowIndex).data.sourceBillNo;
                    }
                }
                //调用后台的方法
                Ext.Ajax.request({
                    url:writeoff.realPath('addPAwardStatementD.action'),
                    jsonData:{
                        'partnerStatementOfAwardVo':{
                            'partnerStatementOfAwardDto':{
                                'statementBillNo':statementBillNo,
                                'sourceBillNo':sourceBillNo
                            }
                        }
                    },
                    success:function(response){
                    	var result = Ext.decode(response.responseText);
                    	//定义全局变量，接收添加明细更新后的发生金额，赋值明细基本信息页面，作为付款金额
                    	writeoff.partnerStatementOfAwardEdit.payPeriodAmount = result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unpaidAmount;
                    	//定义全局变量，接收添加明细更新后的对账单状态，赋值明细基本信息页面
                    	writeoff.partnerStatementOfAwardEdit.updateConfirmStatus = result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.confirmStatus;
                    	grid.store.load();
                        writeoff.partnerStatementOfAwardEdit.detail.store.load();
                        //writeoff.partnerStatementOfAwardEdit.grid.store.load();
                        Ext.ux.Toast.msg(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"添加成功", 'ok', 1000);
                    },
                    exception:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
                    }
                });
            }
        }]
    },{
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        dataIndex: 'statementBillNo'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.sourceBillNo'),
        dataIndex: 'sourceBillNo'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.origSourceBillNo'),
        dataIndex: 'origSourceBillNo'
       }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.billParentType'),
        dataIndex: 'billParentType',
        renderer:writeoff.partnerStatementOfAwardEdit.billParentType
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.billType'),
        dataIndex: 'billType',
        renderer:writeoff.partnerStatementOfAwardEdit.billType
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.approveStatus'),
        dataIndex: 'auditStatus',
        renderer:writeoff.partnerStatementOfAwardEdit.auditStatus
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.amount'),
        dataIndex: 'amount'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.unverifyAmount'),
        dataIndex: 'unverifyAmount'
    },{
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgCode'),
        dataIndex: 'orgCode'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgName'),
        dataIndex: 'orgName'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.createTime'),
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.notes'),
        dataIndex: 'notes'
    },{
            header: 'id',
            dataIndex: 'id',
            hidden: true
        }],
        initComponent:function(config) {
        var me = this;
        cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.writeoff.detail.partnerStatementOfAwardEditStore');
        //自定义分页控件
        me.bbar = Ext.create('Deppon.StandardPaging',{
            store : me.store,
            pageSize : 20,
            maximumSize : 100,
            plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                sizeList : [['20', 20], ['50', 50],  ['100', 100]]
            })
        });
        writeoff.partnerStatementOfAwardEdit.messageGrid =me.bbar;
        me.callParent([cfg]);
    }
});

/**
 * 添加到对账单明细页面的查询tab
 */
Ext.define('Foss.statementbill.partnerStatementOfAwardEdit.QueryInfoTab',{
    extend:'Ext.tab.Panel',
    frame : false,
    bodyCls : 'autoHeight',
    cls : 'innerTabPanel',
    activeTab : 0,
    columnWidth: 1,
    //columnHeight: 'autoHeight',
    height:180,
    items: [{
        title:writeoff.partnerStatementOfAwardEdit.i18n('foss.statementbill.MakeByCustomer'),   //按合伙人制作
        itemId:'detailProducedByStatementPartnerId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm
        ]
    },{
        title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.makeByNumber'),//按对账单单号
        itemId:'detailProductionStatementOrderId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.partnerStatementOfAwardEdit.productionOrderAddForm
        ]
    }]

});


/**
 * 定义对账单明细记录grid
 */
Ext.define('Foss.partnerStatementOfAwardEdit.StatementEntryEditGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.statementEntry'),
    frame: true,
    height: 600,
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.partnerStatementOfAwardEdit.detailStore'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        layout: 'column',
        items: [{
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .9
        }, {
            xtype: 'button',
            text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.addStatementEntry'),
            columnWidth: .1,
            handler: function () {
                //如果对账单为已确认，则弹出提示
                var status =Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmStatus').getValue();
               if (status=='Y') {
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.auditToAddStatementEntryWarning'));
                    return false;
                }
                //获得对账单添加明细需要的信息
                var form =  writeoff.partnerStatementOfAwardEdit.producedByPartnerAddForm.form;
                //设置对账单单号
                form.findField('settmentNo').setValue(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.statementBillNo').getValue());
                //设置客户名称
                form.findField('customerName').setValue(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgName').getValue());
                //设置客户编码
                form.findField('customerCode').setValue(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgCode').getValue());
                //弹出添加明细窗口
                var win= Ext.create('Ext.window.Window',{
                    width: stl.SCREENWIDTH * 0.9,
                    modal: true,
                    constrainHeader: true,
                    closeAction: 'destory',
                    items: [writeoff.partnerStatementOfAwardEdit.detailAdd,writeoff.partnerStatementOfAwardEdit.Message]
                });
                win.show();

            }
        }]
    }/*, {
        xtype: 'toolbar',
        dock: 'bottom',
        layout: 'column',
        items: [{
            xtype: 'button',
            text: 'X ' + '删除',
            hidden:true,
            columnWidth: .06,
            handler:function(){
                deleteStatementDetail();
            }
        }, {
            xtype: 'textfield',
            readOnly: true,
            name: 'totalRows',
            columnWidth: 1,
            labelWidth: 60,
            fieldLabel: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.totalCount')
        }]
    }*/],
    columns: [{
              xtype: 'actioncolumn',
              width: 50,
              text: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.actionColumn'),
              align: 'center',
              items: [{
                  iconCls: 'statementBill_remove',
                  tooltip: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.delete'),
                  handler: function (grid, rowIndex, colIndex) {
                	  //获取对账单号
                	  var statementBillNo = grid.getStore().getAt(rowIndex).data.statementBillNo;
                	  //获取选择的来源单号
                      var selection = grid.getStore().getAt(rowIndex);
                      for (var i = 0; i < selection.stores.length; i++) {
                          var array = new Array();
                          if(selection.store.getAt(rowIndex).data.sourceBillNo!=null){
                              array.push(selection.store.getAt(rowIndex).data.sourceBillNo);
                          }
                      }
                      //调用后台的方法
                      Ext.Ajax.request({
                          url:writeoff.realPath('delPAwardStatementD.action'),
                          jsonData:{
                              'partnerStatementOfAwardVo':{
                                  'partnerStatementOfAwardDto':{
                                	  'statementBillNo':statementBillNo,
                                      'numbers':array
                                  }
                              }
                          },
                          success:function(response){
                        	  var result = Ext.decode(response.responseText);
	                          //定义全局变量，存放删除明细重新计算后的发生金额，作为付款金额
	                          writeoff.partnerStatementOfAwardEdit.payPeriodAmount = result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.unpaidAmount
	                          //定义全局变量，接收删除明细更新后的对账单状态，赋值明细基本信息页面
	                          writeoff.partnerStatementOfAwardEdit.updateConfirmStatus = result.partnerStatementOfAwardVo.partnerStatementOfAwardDto.confirmStatus;
                        	  writeoff.partnerStatementOfAwardEdit.detail.store.load();
                              /*writeoff.partnerStatementOfAwardEdit.grid.store.load();*/
                              Ext.ux.Toast.msg(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"删除成功", 'ok', 1000);
                          },
                          exception:function(response){
                              var result = Ext.decode(response.responseText);
                              Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
                          }
                      });
                  }
              }]
          }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
        dataIndex: 'statementBillNo'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.sourceBillNo'),
        dataIndex: 'sourceBillNo'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.origSourceBillNo'),
        dataIndex: 'origSourceBillNo'
       }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.billParentType'),
        dataIndex: 'billParentType',
        renderer:writeoff.partnerStatementOfAwardEdit.billParentType
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.billType'),
        dataIndex: 'billType',
        renderer:writeoff.partnerStatementOfAwardEdit.billType
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.approveStatus'),
        dataIndex: 'auditStatus',
        renderer:writeoff.partnerStatementOfAwardEdit.auditStatus
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.amount'),
        dataIndex: 'amount'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.unverifyAmount'),
        dataIndex: 'unverifyAmount'
    },{
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgCode'),
        dataIndex: 'orgCode'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgName'),
        dataIndex: 'orgName'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.createTime'),
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.notes'),
        dataIndex: 'notes'
    },{
            header: 'id',
            dataIndex: 'id',
            hidden: true
        }],
    initComponent:function(config) {
    var me = this;
    cfg = Ext.apply({}, config);
    writeoff.partnerStatementOfAwardEdit.datilStore =me.store;
    me.callParent([cfg]);
}
});

//确认方法
var confirmRecord=function(val){
    //获取选中的数据
    var select = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getSelection();
    var status = 'N';
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('updateStatusByStatementNo.action'),
        jsonData:{
            'partnerStatementOfAwardVo':{
                'partnerStatementOfAwardDto':{
                    'confirmStatus':status,
                    'statementBillNo':select[0].data.statementBillNo
                }
            }
        },
        success:function(response){
        	Ext.ux.Toast.msg(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"更新成功", 'ok', 1000);
        	//确认成功，则更改状态由'N'变为'Y'
        	writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").setRawValue('Y');
        	if(val=='statementEdit'){
        		writeoff.partnerStatementOfAwardEdit.grid.store.load();
        	}
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

//反确认方法
var unconfirmRecord=function(val){
    //获取选中的数据
    var select = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getSelection();
    var status = 'Y';
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('updateStatusByStatementNo.action'),
        jsonData:{
            'partnerStatementOfAwardVo':{
                'partnerStatementOfAwardDto':{
                    'confirmStatus':status,
                    'statementBillNo':select[0].data.statementBillNo
                }
            }
        },
        success:function(response){
        	Ext.ux.Toast.msg(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"更新成功", 'ok', 1000);
        	//反确认成功，则更改状态由'Y'变为'N'
        	writeoff.partnerStatementOfAwardEdit.BaseInfo.getForm().findField("confirmStatus").setRawValue('N');
            if(val=='statementEdit'){
        		writeoff.partnerStatementOfAwardEdit.grid.store.load();
        	}
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

/**
 * 对账单操作列的下拉框
 */
Ext.define('Foss.statementbill.operateColumn', {
    extend:'Ext.form.field.ComboBox',
    typeAhead: true,
    emptyText:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.actionColumn'),
    triggerAction: 'all',
    queryMode: 'local',
    store: Ext.create('Foss.statementbill.operateColumnStore'),
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
            var selection=Ext.getCmp('T_writeoff-partnerStatementOfAwardEdit_content').getStatementQueryGrid().getSelectionModel().getSelection()[0];

            this.store.removeAll();
            this.store.add(
                {name:'反确认',value:'1'},
                {name:'确认',value:'2'},
                {name:'明细',value:'3'},
                {name:'付款',value:'4'},
                {name:'扣款',value:'5'});
            
		        //反确认
		        var unConfirm = this.store.getAt(0);
		        //确认
		        var confirm = this.store.getAt(1);
                //明细
                var detail = this.store.getAt(2);
                //付款
                var pay = this.store.getAt(3);
                //扣款
                var deduct = this.store.getAt(4);
                //判断对账单状态
                //对账单状态选择未确认
                if(selection.get('confirmStatus')=='N'){
                	//移除反确认按钮
                    this.store.remove(unConfirm);
                  //对账单状态选择已确认
                }else{
                	//移除确认按钮
                    this.store.remove(confirm);
                }
                
                //如果已还金额不等于0，说明已付款或已还款，则隐藏确认反确认按钮
                if(selection.get('paidAmount')!=0){
                	this.store.remove(unConfirm);
                	this.store.remove(confirm);
                }
                
                //判断未还金额,未还金额负数,移除扣款按钮
                if((selection.get('periodUnverifyRecAmount')-selection.get('periodUnverifyPayAmount'))<0){
                    this.store.remove(deduct);
                }
                //未还金额正数,移除付款按钮
                if((selection.get('periodUnverifyRecAmount')-selection.get('periodUnverifyPayAmount'))>0){
                    this.store.remove(pay);
                }
                //未还金额为0，同时移除付款、扣款按钮
                if(selection.get('unpaidAmount')==0){
                	this.store.remove(deduct);
                	this.store.remove(pay);
                }
        },
        'select':function(combo){
        	if(combo.value=='1'){
                //反确认
                 Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否反确认？",function(val){
                     if(val=='yes'){
                    	 unconfirmRecord(writeoff.PAGEFROM_STATEMENTEDIT);
                     }else{
                         return false;
                     }
                 });
           }else if(combo.value=='2'){
        	   //确认
               Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否确认？",function(val){
                   if(val=='yes'){
                      confirmRecord(writeoff.PAGEFROM_STATEMENTEDIT);
                   }else{
                       return false;
                   }
               });
           }else if(combo.value=='3'){
                //明细
                Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否要查看明细？",function(val){
                    if(val=='yes'){
                        combo.store.removeAll();

                        var win= Ext.create('Ext.window.Window',{
                            width: stl.SCREENWIDTH * 0.9,
                            /*id:'window.Window.partnerStatementOfAwardEdit.detail',*/
                            modal: true,
                            constrainHeader: true,
                            closeAction: 'destory',
                            listeners:{
                            	'close':function(){
                            		var grid = Ext.getCmp('T_writeoff-partnerStatementOfAwardEdit_content').getStatementQueryGrid();
                            		grid.store.load();
                            	}
                            },
                            items:[writeoff.partnerStatementOfAwardEdit.BaseInfo,writeoff.partnerStatementOfAwardEdit.OperateButtonPanel,writeoff.partnerStatementOfAwardEdit.detail]
                            // 仅仅用来显示一个头部。没有数据，
                        });
                        win.show();
                        writeoff.partnerStatementOfAwardEdit.datilStore.load({
                            scope: this,
                            callback:function(store, records, successful, eOpts){//表格数据加载事件
                            //总条数
                        	//判断store的长度是否为0，如果为0，设置条数值为0
                            if(store.length<=0){
                            	Ext.getCmp('Foss.partnerStatementOfAwardEdit.statement.total').setValue('0');
                            }else{
                                Ext.getCmp('Foss.partnerStatementOfAwardEdit.statement.total').setValue(store[0].store.totalCount);
                            }
                        }});
                    }else{
                        return ;
                    }
                });
           }else if(combo.value=='4'){
               //付款
        	   Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否付款？",function(val){
                   if(val=='yes'){
                	   writeoff.partnerStatementOfAwardEdit.payment();
                   }else{
                       return false;
                   }
               });       	   
           }else if(combo.value=='5'){
               //扣款
        	   Ext.MessageBox.confirm(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),"是否扣款？",function(val){
                   if(val=='yes'){
                	   writeoff.partnerStatementOfAwardEdit.deduct();
                   }else{
                       return false;
                   }
               });
           }
       }
    }
});
            
            
//操作列下拉框
var operateColumn = Ext.create('Foss.statementbill.operateColumn');

//编辑器插件
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit : 1,
    isObservable : false
}) ;

//定义对账单grid
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditGrid',{
    extend:'Ext.grid.Panel',
    title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.statementBill'),
    frame:true,
    height:600,
    store:null,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    plugins:SoperateColumnEditing,
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    defaults:{
        align:'center',
        margin:'5 0 5 0'
    },
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        layout:'column',
        items: [{
            xtype:'textfield',
            readOnly:true,
            id:'Foss.partnerStatementOfAwardEdit.statement.total',
            name:'totalRows',
            columnWidth:.1,
            labelWidth:60,
            fieldLabel:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.totalCount')
        },{
            xtype:'container',
            border:false,
            html:'&nbsp;',
            columnWidth:.9
        },{
            xtype:'button',
            text:'批量扣款',
            hidden:true,
            columnWidth:.1,
            handler:function(){

            }//,
            // hidden:!writeoff.statementAdd.isPermission('/stl-web/writeoff/addStatement.action')
        }]
    },{
        xtype: 'toolbar',
        dock: 'top',
        layout:'column',
        items: [{
            xtype:'button',
            text:'批量扣款',
            hidden:true,
            columnWidth:.15,
            handler:writeoff.partnerStatementOfAwardEdit.deduct
        },{
            xtype:'component',
             autoEl:{
             tag:'div',
             html:'点击表格操作列'+'<span style="color:red;">'+'空白处'+'</span>'+'可进行查看明细等操作！'
             }
        },{
            xtype:'button',
            text:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
            columnWidth:.15,
            handler:function(){
                var	columns,
                    arrayColumns,
                    arrayColumnNames;
                var statementBillNo = new Array();
                //对账单grid
                var statementList = writeoff.partnerStatementOfAwardEdit.grid.getSelectionModel().getSelection();
                if(statementList.length==0){
                    Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                    		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.noDataToExport'));
                    return false;
                }else {
                    for(var i=0;i<statementList.length;i++){
                        statementBillNo.push(statementList[i].data.statementBillNo);
                    }
                }
                //转化列头和列明
                columns = writeoff.partnerStatementOfAwardEdit.grid.columns;
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
                //拼接vo，注入到后台
                searchParams = {
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.statementBillNos':statementBillNo,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.arrayColumns':arrayColumns,
                    'partnerStatementOfAwardVo.partnerStatementOfAwardDto.arrayColumnNames':arrayColumnNames
                };
                if(!Ext.fly('downloadAttachFileForm')){
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }
                Ext.Ajax.request({
                    url: writeoff.realPath('pAwardStatementToExcel.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method : 'POST',
                    params : searchParams,
                    isUpload: true,
                    success : function(response,options){
                        Ext.Msg.alert(writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.alert'),
                        		writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.airPaymentPayable.exportSuccess'));
                    }
                });

            }
        }]
    }],
    columns: [
        {
            header: writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.actionColumn'),
            dataIndex:'isRight',
            editor:operateColumn,
            renderer: function(value){
                var record = operateColumn.findRecord(operateColumn.valueField, value);
                return record ? record.get(operateColumn.displayField): operateColumn.valueNotFoundText;
            },
            sortable: true
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
            dataIndex:'statementBillNo'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgCode'),
            dataIndex:'createOrgCode'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.orgName'),
            dataIndex:'createOrgName'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.companyCode'),
            dataIndex:'companyCode'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.companyName'),
            dataIndex:'companyName'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
            dataIndex:'unifiedCode'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerCode'),
            dataIndex:'customerCode'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.customerName'),
            dataIndex:'customerName'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
            dataIndex:'billType',
            renderer:writeoff.partnerStatementOfAwardEdit.awardBillType
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
            dataIndex:'periodAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.paidAmount'),
            dataIndex:'paidAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.unpaidAmount'),
            dataIndex:'unpaidAmount'/*,
            renderer:function(value,metaData,record,rowIndex,colIndex){
            	return record.data.periodRecAmount - record.data.periodPayAmount;
            }*/
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),
            dataIndex:'periodRecAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
            dataIndex:'periodPayAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyRecAmount'),
            dataIndex:'periodUnverifyRecAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyPayAmount'),
            dataIndex:'periodUnverifyPayAmount'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
            dataIndex:'periodBeginDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
            dataIndex:'periodEndDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
            dataIndex:'settleNum'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmTime'),
            dataIndex:'confirmTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.companyAccountBankNo'),
            dataIndex:'companyAccountBankNo'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
            dataIndex:'accountUserName'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
            dataIndex:'bankBranchName'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
            dataIndex:'confirmStatus',
            renderer:function(val){
                switch(val){
                    case "Y":
                        return "已确认";
                    case "N":
                        return "未确认";
                }
            }
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementCommon.billDescription'),
            dataIndex:'notes'
        },{
            header:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.createTime'),
            dataIndex:'createTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        }],
        initComponent:function(config) {
            var me = this;
            cfg = Ext.apply({}, config);
            me.store = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardStore');
            //自定义分页控件
            me.bbar = Ext.create('Deppon.StandardPaging',{
                store : me.store,
                pageSize : 20,
                maximumSize : 100,
                plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                    sizeList : [['20', 20], ['50', 50],  ['100', 100]]
                })
            });
            writeoff.partnerStatementOfAwardEdit.store =me.bbar;
            me.callParent([cfg]);
        }
});

//创建按合伙人查询form
writeoff.partnerStatementOfAwardEdit.producedByPartnerForm = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.producedByPartnerForm');
//创建按对账查询form
writeoff.partnerStatementOfAwardEdit.productionOrderForm = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.productionOrderForm');
//创建按运单查询form
writeoff.partnerStatementOfAwardEdit.waybillOrderForm = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.waybillOrderForm');
//创建按部门查询form
writeoff.partnerStatementOfAwardEdit.departmentForm = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.departmentForm');

//定义查询Tab
Ext.define('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls: 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,//最初被激活的选项卡
	columnWidth: 1,
	height:230,
	items:[{
		title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerPayStatementEdit.queryByPartner'),   //按合伙人制作
        itemId:'producedByStatementPartnerId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
                 writeoff.partnerStatementOfAwardEdit.producedByPartnerForm
        ]
	},{
		title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatement'),//按对账单单号
        itemId:'productionStatementOrderId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.partnerStatementOfAwardEdit.productionOrderForm
        ]
	},{
        title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.partnerStatementOfAwardEdit.recAndPay'),//按应收应付制作
        itemId:'waybillStatementOrderFormId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.partnerStatementOfAwardEdit.waybillOrderForm
        ]
    },{
        title:writeoff.partnerStatementOfAwardEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),//按部门制作
        itemId:'departmentStatementFormId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.partnerStatementOfAwardEdit.departmentForm
        ]
    }]
});


//定义主函数
Ext.onReady(function(){
	//启动鼠标悬浮提示
	Ext.QuickTips.init();
	if(Ext.getCmp('T_writeoff-partnerStatementOfAwardEdit_content')){
        return;
    }
	
    //判断id是否存在如果存在就销毁
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.billType')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.billType'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createTime')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createTime'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgCode'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmStatus')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmStatus'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodBeginDate')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodBeginDate'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyName'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyCode'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.contractOrgName'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmTime')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.confirmTime'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodEndDate')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.periodEndDate'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgCode'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.createOrgName'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.statementBillNo')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.statementBillNo'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unifiedCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unifiedCode'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyAccountBankNo')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.companyAccountBankNo'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.accountUserName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.accountUserName'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.bankBranchName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.bankBranchName'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unpaidAmount')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.unpaidAmount'));
    }
    if(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.settleNum')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.partnerStatementOfAwardEdit.datil.settleNum'));
    }
	//创建查询Tab
	writeoff.partnerStatementOfAwardEdit.queryTab=Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditTab');
	//对账单明细的基本信息
    writeoff.partnerStatementOfAwardEdit.BaseInfo=Ext.create('Foss.partnerStatementOfAwardEdit.BaseInfo');
    //按钮面板
    writeoff.partnerStatementOfAwardEdit.OperateButtonPanel=Ext.create('Foss.partnerStatementOfAwardEdit.OperateButtonPanel');
    //创建对账单明细grid
    writeoff.partnerStatementOfAwardEdit.detail=Ext.create('Foss.partnerStatementOfAwardEdit.StatementEntryEditGrid');
    //创建对账单grid
	writeoff.partnerStatementOfAwardEdit.grid = Ext.create('Foss.writeoff.partnerStatementOfAwardEdit.partnerStatementOfAwardEditGrid');
	//添加明细的tab
    writeoff.partnerStatementOfAwardEdit.detailAdd = Ext.create('Foss.statementbill.partnerStatementOfAwardEdit.QueryInfoTab');
    //添加对账单明细grid
    writeoff.partnerStatementOfAwardEdit.Message =Ext.create('Foss.writeoff.detail.partnerStatementOfAwardEditGrid');
	//创建整个对账单面板，将上面几块包含在一起
	Ext.create('Ext.panel.Panel',{
        id: 'T_writeoff-partnerStatementOfAwardEdit_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getStatementQueryGrid : function(){
           return   writeoff.partnerStatementOfAwardEdit.grid;
        },
        items: [writeoff.partnerStatementOfAwardEdit.queryTab,writeoff.partnerStatementOfAwardEdit.grid],
        renderTo: 'T_writeoff-partnerStatementOfAwardEdit-body',
        //默认部门编码
        listeners:{
            'boxready':function(th){
                var roles = stl.currentUserDepts;
                var queryByDateTab = writeoff.partnerStatementOfAwardEdit.queryTab.items.items[0].items.items[0];
                var dept = queryByDateTab.getForm().findField("orgCode");
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

                var queryByFailingInvoice =  writeoff.partnerStatementOfAwardEdit.queryTab.items.items[3].items.items[0];
                var failingInvoiceDept = queryByFailingInvoice.getForm().findField("orgCode");
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
        }
    });
});