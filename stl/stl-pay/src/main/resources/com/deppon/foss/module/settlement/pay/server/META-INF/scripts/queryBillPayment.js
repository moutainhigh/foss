//开发环境地址是：http://192.168.17.221:8881/fssc
//测试环境UAT地址是：http://192.168.20.251:8080/fssc
//测试环境SIT地址是：http://192.168.20.148:8080/fssc
//http://192.168.11.59:8881/claim/attachment/query.action
pay.payment.fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
//附件查询地址
var attachmentQueryUrl = 'http://'+pay.payment.fssc+'/fssc/attachment/query.action';
//附件下载地址
var attachmentDownLoadUrl = 'http://'+pay.payment.fssc+'/fssc/attachment/download.action';
//附件删除地址
var attachmentDeleteUrl = 'http://'+pay.payment.fssc+'/fssc/attachment/delete.action';
//附件上传地址
var attachmentUploadUrl = 'http://'+pay.payment.fssc+'/fssc/attachment/upload.action';

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
            xtype : 'actioncolumn',
            text : '操作',
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
            Ext.Msg.alert('提示', '请先暂存表单后再上传附件');
            return;
        }
        this.getForm().submit({
            url : attachmentUploadUrl,
            method : 'POST',
//            waitMsg : '正在上传数据,请稍等...',
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
/**
 * 使用说明：
 *
 * <pre>
 * Ext.onReady(function() {
 *  Ext.create('Ext.panel.Panel', {
 *      id : 'T_attachment-index_content',
 *      cls : &quot;panelContentNToolbar&quot;,
 *      bodyCls : 'panelContentNToolbar-body',
 *      items : [ {
 *          xtype : 'upfilepanel',
 *          attachRelId : 'FSSCBASE0001',
 *          userCode : '000000'
 *      } ],
 *      renderTo : 'T_attachment-index-body'
 *  });
 * });
 *
 * 或者：
 *
 * Ext.onReady(function() {
 *  var upfilepanel = Ext.create('Fssc.common.upFilePanel');
 *   upfilepanel.setUserCode('000000');
 *   upfilepanel.setAttachRelId('FSSC000000001');
 *
 *  Ext.create('Fssc.panel.BasePanel', {
 *      id : 'T_attachment-index_content',
 *      cls : &quot;panelContentNToolbar&quot;,
 *      bodyCls : 'panelContentNToolbar-body',
 *      getUpfilepanel : function(){
 *          return upfilepanel;
 *      },
 *      items : [ upfilepanel ],
 *      renderTo : 'T_attachment-index-body'
 *  });
 * });
 *
 * </pre>
 *
 */
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

/**
 *<pre>
 * Ext.create('Ext.panel.Panel', {
 *     id : 'T_attachment-index_content',
 *     cls :'panelContentNToolbar',
 *     bodyCls : 'panelContentNToolbar-body',
 *     items : [ {
 *          xtype : 'upfileviewgrid',
 *          attachRelId : 'FSSCBASE0001',
 *          autoLoadStore : true
 *     } ]
 * });
 * </pre>
 */
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

//邓大伟
//var userCode = null;
//var batchNo = null;
//var userName = null;
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
			pay.payment.upfilepanel = this.upfilepanel;
		}
		return this.upfilepanel;
	},
	constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.items = [me.getUpfilepanel()];
        me.callParent([cfg]);
    }
});

pay.payment.addPaymentUpload = function(){
	pay.payment.shangChuanWindow =Ext.create('Foss.payment.ShangChuanWindow');
 	pay.payment.upfilepanel.setUserCode(pay.payment.userCode);
 	pay.payment.upfilepanel.setAttachRelId(pay.payment.batchNo);
	pay.payment.shangChuanWindow.show();
 }

/**
 * ***********************************附件上传 end by z.halo 2013-05-18 15:49**********************************
 */

/**
 * 付款查询日期限制
 */
pay.payment.PAYMENT_MAX = 60;  // 查询时间间隔最大不超过60天
pay.payment.REVADUIT_MAX = 2;  // 反审核时间间隔最大不超过60天
pay.payment.PAYMENT_TENDAYS = 10;  // 时间相差10天
pay.payment.MAXSHOWNUM = 500;  // 默认最大显示条数
pay.payment.ONEPAGESIZE = 20; // 每页显示的条数

pay.payment.QUERY_BY_DATE='QBD';// 按时间查询
pay.payment.QUERY_BY_NOS='QNS';// 按单号查询
pay.payment.QUERY_BY_SOURCE_NOS='QSNS';// 按来源单号查询
pay.payment.QUERY_BY_WORKFLOW_NOS='WKFNS';//按工作流号查询

//银行帐号性质：收银员卡
pay.payment.FIN_ACCOUNT_TYPE_CASHIER = 2; 
pay.payment.FIN_ACCOUNT_TYPE_CASHIER_ACCOUNTTYPE = '2'; 
pay.payment.FIN_ACCOUNT_TYPE_CASHIER_COMPANY = '1'; 

/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.payment.billPaymentConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(pay.payment.i18n('foss.stl.pay.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


/**
 * Form重置方法
 */
pay.payment.paymentQueryReset=function(){
	this.up('form').getForm().reset();
}

/**
 * Form查询方法
 */
pay.payment.paymentQuery=function(f,me,queryType){
	var form = f.getForm();
	if(pay.payment.QUERY_BY_DATE==queryType){
		var startBusinessDate = form.findField('billPaymentVo.billPaymentQueryDto.startAccountDate').getValue();
		var endBusinessDate = form.findField('billPaymentVo.billPaymentQueryDto.endAccountDate').getValue();
		
		if(startBusinessDate==null || startBusinessDate==''){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.startAccountDateIsNotNull'));
			return false;
		}

		if(endBusinessDate==null || endBusinessDate==''){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.endAccountDateIsNotNull'));
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(startBusinessDate,endBusinessDate);
		if(compareTwoDate>pay.payment.PAYMENT_MAX){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.startToEndAccountDateIsNotPaymentMaxDay'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.endAccountDateIsNotBeforeStartAccountDate'));
			return false;
		}
	}

	
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	if(form.isValid()){
		var params=pay.payment.setQueryParams(form,queryType);
	
		// 参数设置异常时直接返回
		if(!params){
			return false;
		}
		
		// 设置查询参数
		grid.store.setSubmitParams(params);
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		// 设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),rawData.message);
				me.enable(true);
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
				toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
				
				if(result.billPaymentVo.billPaymentResultDto.billPaymentEntityList.length>0){
					grid.show();
				}else{
					Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.noDateUnderThisQuery'));
					me.enable(true);
					return false;
				}
				me.enable(true);
	    	}
	       }
	    }); 
	}else {
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.common.validateFailAlert'));
	}
}


// 设置参数
pay.payment.setQueryParams=function(form,queryType){
	// 定义查询参数
	var params={};
	
	// 按日期查询
	if(pay.payment.QUERY_BY_DATE==queryType){
		
		// 获取FORM所有值
		params = form.getValues();
		
		var customerDetial = form.findField('customerDetial').getValue();
		var airDetial = form.findField('airDetial').getValue();
		var airAgencyDetial = form.findField('airAgencyDetial').getValue();
		var vehAgencyDetial = form.findField('vehAgencyDetial').getValue();
		var landStowage= form.findField('landStowage').getValue();
		var packagingCode= form.findField('packagingCode').getValue();
		var leaseddriver = form.findField('leaseddriver').getValue();
		var homesupply = form.findField('homesupply').getValue();//设置参数
		var partner = form.findField('partner').getValue();
		var arrVal = [customerDetial,
		             airDetial,
		             airAgencyDetial,
		             vehAgencyDetial,
		             landStowage,
		             packagingCode,
		             leaseddriver,
		             homesupply,
		             partner];
		
		for(var i=0;i<arrVal.length;i++) { 
			if (!Ext.isEmpty(arrVal[i])){
				Ext.apply(params, {
					'billPaymentVo.billPaymentQueryDto.customerCode' : arrVal[i]
				});
			} 
		} 
		
	
	// 按单号查询
	}else if(pay.payment.QUERY_BY_NOS==queryType){ 
	
		// 获取FORM所有值
		params = form.getValues();
		
		var billNos = form.findField('billNos').getValue();
		var billNosArray_tmp = stl.splitToArray(billNos);
		var billNosArray=new Array();
		
		for(var i=0;i<billNosArray_tmp.length;i++){
			if(billNosArray_tmp[i].trim()!=''){
				billNosArray.push(billNosArray_tmp[i].trim());
			} 
		}
		 
		if(billNosArray.length==0){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosNotInputAlert'));
		 	return false;
		}
		if(billNosArray.length>10){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosInputToMore'));
		 	return false;
		}
		
		Ext.apply(params,{
			'billPaymentVo.billPaymentQueryDto.billNoArray' : billNosArray
		});
		
	//按来源单号查询
	}else if(pay.payment.QUERY_BY_SOURCE_NOS==queryType){
		
		// 获取FORM所有值
		params = form.getValues();
		
		var sourceBillNos = form.findField('sourceBillNos').getValue();
		var sourceBillNosArray_tmp = stl.splitToArray(sourceBillNos);
		var sourceBillNosArray=new Array();
		
		for(var i=0;i<sourceBillNosArray_tmp.length;i++){
			if(sourceBillNosArray_tmp[i].trim()!=''){
				sourceBillNosArray.push(sourceBillNosArray_tmp[i].trim());
			} 
		}
		 
		if(sourceBillNosArray.length==0){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNosNotInputAlert'));
		 	return false;
		}
		if(sourceBillNosArray.length>10){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosInputToMore'));
		 	return false;
		}
		
		Ext.apply(params,{
			'billPaymentVo.billPaymentQueryDto.sourceBillNoArray' : sourceBillNosArray
		});
	//按工作流号查询	
	}else if(pay.payment.QUERY_BY_WORKFLOW_NOS==queryType){
		
		// 获取FORM所有值
		params = form.getValues();
		
		var workFlowNos = form.findField('workFlowNos').getValue();
		var workFlowNosArray_tmp = stl.splitToArray(workFlowNos);
		var workFlowNosArray=new Array();
		
		for(var i=0;i<workFlowNosArray_tmp.length;i++){
			if(workFlowNosArray_tmp[i].trim()!=''){
				workFlowNosArray.push(workFlowNosArray_tmp[i].trim());
			} 
		}
		 
		if(workFlowNosArray.length==0){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.workFlowNosNotInputAlert'));
		 	return false;
		}
		if(workFlowNosArray.length>10){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosInputToMore'));
		 	return false;
		}
		
		Ext.apply(params,{
			'billPaymentVo.billPaymentQueryDto.workFlowNosArray' : workFlowNosArray
		});
	}
	return params;
}

/*
 // 审核付款单
pay.payment.aduitPayment = function(grid, rowIndex, colIndex){
	
	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 无效或红单的付款单不能审核
	if(selection.get('active')==pay.payment.INACTIVE||selection.get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为无效的或红单，不能审核');
		return false;
	}
	// 以审核的付款单不能再次审核
	if(selection.get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单已审核，不能审核');
		return false;
	}
	//已汇款的付款单不能审核
	if(selection.get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单不是未汇款状态，不能审核');
		return false;
	}
	
	
	 Ext.MessageBox.show({
         title: '付款上缴 审核意见',
         msg: '审核意见',
         width:300,
         buttons: Ext.MessageBox.OKCANCEL,
         buttonText:{ 
             ok: '确定',
             cancel:'取消'
         },
         multiline: true,
         fn: function(e,text){
        	 if(e=='ok'){

        			// 付款单的grid
        			var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
        			// 调用
        			Ext.Ajax.request({
        				//url:'../pay/aduitBillPayment.action',
        				url:pay.realPath('aduitBillPayment.action'),
        				params:{
        					'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selection.data.paymentNo,
        					'billPaymentVo.billPaymentQueryDto.opinionNotes':text
        				},
        				success:function(response){
        					
        					// 重新载入数据
        					grid.store.load({
        						callback: function(records, operation, success) {
        							var result =   Ext.decode(operation.response.responseText);
        							
        							toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
        							toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
        							toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
        					       }
        					});
        					
        					Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),"审核付款单成功");
        				},
        				exception:function(response){
        					var result = Ext.decode(response.responseText);	
        					Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
        					
        					// 重新载入数据
        					grid.store.load({
        						callback: function(records, operation, success) {
        							var result =   Ext.decode(operation.response.responseText);
        							
        							toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
        							toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
        							toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
        					       }
        					});
        				}
        			});	
        		
        	 }else{
        		 return false;
        	 }
         }
     });
}

// 反审核付款单
pay.payment.revAduitPayment = function(grid, rowIndex, colIndex){
		
	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 无效或红单的付款单不能审核
	if(selection.get('active')==pay.payment.INACTIVE||selection.get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为无效的或红单，不能反审核');
		return false;
	}
	// 以审核的付款单不能再次审核
	if(selection.get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为反审核，不能反审核');
		return false;
	}
	//已汇款的付款单不能审核
	if(selection.get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单不是未汇款状态，不能反审核');
		return false;
	}
	//会计日期超过30天的付款单不允许反审核
	var compareTwoDateTemp = stl.compareTwoDate(
			new Date(), selection.get('accountDate'));
	if (compareTwoDateTemp > pay.payment.REVADUIT_MAX) {
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'超过30天的付款单不允许反审核');
	} 
	
	Ext.MessageBox.show({
        title: '付款上缴 反审核意见',
        msg: '反审核意见',
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: '确定',
            cancel:'取消'
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
       	// 付款单的grid
     		var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
     		// 调用
     		Ext.Ajax.request({
     			//url:'../pay/revAduitBillPayment.action',
     			url:pay.realPath('revAduitBillPayment.action'),
     			params:{
     				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selection.data.paymentNo,
     				'billPaymentVo.billPaymentQueryDto.opinionNotes':text
     			},
     			success:function(response){
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     				
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),"反审核付款单成功");
     			},
     			exception:function(response){
     				var result = Ext.decode(response.responseText);	
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     			}
     		});
       	 }else{
       		 return false;
       	 }
        }
    });
}
 
 // 批量审核付款单
pay.payment.aduitPaymentBatch = function(){
	
	// 付款单的grid
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'请至少选择一条记录进行审核操作！');
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		// 无效或红单的付款单不能审核
		if(selections[i].get('active')==pay.payment.INACTIVE||selections[i].get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为无效的或红单，不能审核');
			return false;
		}
		// 以审核的付款单不能再次审核
		if(selections[i].get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单已审核，不能审核');
			return false;
		}
		//已汇款的付款单不能审核
		if(selections[i].get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单不是未汇款状态，不能审核！');
			return false;
		}
		selectBillRepayNos.push(selections[i].get('paymentNo'));
	}
	
	Ext.MessageBox.show({
        title: '付款上缴 审核意见',
        msg: '审核意见',
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: '确定',
            cancel:'取消'
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
     		// 调用
     		Ext.Ajax.request({
     			//url:'../pay/aduitBillPayment.action',
     			url:pay.realPath('aduitBillPayment.action'),
     			params:{
     				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selectBillRepayNos,
     				'billPaymentVo.billPaymentQueryDto.opinionNotes':text
     			},
     			success:function(response){
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     				
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),"审核付款单成功");
     			},
     			exception:function(response){
     				var result = Ext.decode(response.responseText);	
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     			}
     		});	
       	 }else{
       		 return false;
       	 }
        }
    });
}

// 批量反审核付款单
pay.payment.revAduitPaymentBatch = function(){
	
	// 付款单的grid
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'请至少选择一条记录进行反审核操作！');
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		
		// 无效或红单的付款单不能审核
		if(selections[i].get('active')==pay.payment.INACTIVE||selections[i].get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为无效的或红单，不能反审核');
			return false;
		}
		// 以审核的付款单不能再次审核
		if(selections[i].get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单为反审核，不能反审核');
			return false;
		}
		//已汇款的付款单不能审核
		if(selections[i].get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'当前付款单不是未汇款状态，不能审核！');
			return false;
		}
		
		//会计日期超过30天的付款单不允许反审核
		var compareTwoDateTemp = stl.compareTwoDate(
				new Date(), selections[i].get('accountDate'));
		if (compareTwoDateTemp > pay.payment.REVADUIT_MAX) {
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'超过30天的付款单不允许反审核');
		} 
		selectBillRepayNos.push(selections[i].get('paymentNo'));
	}
	
	Ext.MessageBox.show({
        title: '付款上缴 反审核意见',
        msg: '反审核意见',
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: '确定',
            cancel:'取消'
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
     		// 调用
     		Ext.Ajax.request({
     			//url:'../pay/revAduitBillPayment.action',
     			url:pay.realPath('revAduitBillPayment.action'),
     			params:{
     				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selectBillRepayNos,
     				'billPaymentVo.billPaymentQueryDto.opinionNotes':text
     			},
     			success:function(response){
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     				
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),"反审核付款单成功");
     			},
     			exception:function(response){
     				var result = Ext.decode(response.responseText);	
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     			}
     		});
       	 }else{
       		 return false;
       	 }
        }
    });
}
 
 * */

//查看明细
pay.payment.showDetialPayment = function(grid, rowIndex, colIndex){

	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	var paymentNo = selection.data.paymentNo;
	var active = selection.data.active;
	var isRedBack = selection.data.isRedBack;
	var id = selection.data.id;
	
	var paramV = {
			'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':paymentNo,
			'billPaymentVo.billPaymentQueryDto.active':active,
			'billPaymentVo.billPaymentQueryDto.isRedBack':isRedBack,
			'billPaymentVo.billPaymentQueryDto.id':id
		};
	
	// 获取弹出窗口
	win = pay.payment.billPaymentDetailWindow;
	if(Ext.isEmpty(pay.payment.billPaymentDetailWindow)){
		pay.payment.billPaymentDetailWindow = Ext.create('Foss.payment.billPaymentDetailWindow');
		win = pay.payment.billPaymentDetailWindow;
	}
	win.show();	
	
	var store = win.items.items[1].store;
	store.setSubmitParams(paramV);
	
	store.loadPage(1,{
		callback : function(records, operation, success) {
			var rawData = store.proxy.reader.rawData;
			if(!success && !rawData.isException){
				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),rawData.message);
				return false;
			}
			if(success){
				
				var result = Ext.decode(operation.response.responseText);  
				if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity!=null){
					
					if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==null||result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==''){
						result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo=result.billPaymentVo.billPaymentResultDto.billPaymentEntity.applyWorkFlowNo
					}
					
					if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=null && result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=''){	
						var displayField = null;
						if(displayField==null){
			    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.CRM_ACCOUNT_NATURE);
			    		}
			    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
			    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.FIN_ACCOUNT_TYPE);
			    		}
			    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
			    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
			    		}
			    		result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType = displayField;
					}
					
					var model = new Foss.payment.PaymentGridModel(result.billPaymentVo.billPaymentResultDto.billPaymentEntity);
					var billPaymentDetailForm = win.items.items[0];
					billPaymentDetailForm.loadRecord(model);	
					//到付转预收code转中文 - 231438
					if(selection.get('paymentType') == 'FCUS'){
                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('到付转预收');
                    }
					if(selection.get('paymentType') == 'CF'){
                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('委托派费转预收');
                    }
                    if(selection.get('paymentType') == 'JTU'){
                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('奖励应付自动返');
                    }
                    if(selection.get('paymentType') == 'KTU'){
                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('快递差错应付自动返');
                    }
				}
				
			}
		}
	});
	
	
	// 打开注释
	/*Ext.Ajax.request({
		//url:'../pay/detailPayment.action',
		url:pay.realPath('detailPayment.action'),
		params:paramV,
		success:function(response){
			
			grid.store.setSubmitParams(paramV);
			
			var result = Ext.decode(response.responseText);	
			// 获取弹出窗口
			win = pay.payment.billPaymentDetailWindow;
			if(Ext.isEmpty(pay.payment.billPaymentDetailWindow)){
				pay.payment.billPaymentDetailWindow = Ext.create('Foss.payment.billPaymentDetailWindow');
				win = pay.payment.billPaymentDetailWindow;
			}
			
			if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity!=null){
				if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==null||result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==''){
					result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo=result.billPaymentVo.billPaymentResultDto.billPaymentEntity.applyWorkFlowNo
				}
				if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=null && result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=''){	
					var displayField = null;
					if(displayField==null){
		    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.CRM_ACCOUNT_NATURE);
		    		}
		    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
		    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.FIN_ACCOUNT_TYPE);
		    		}
		    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
		    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
		    		}
		    		result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType = displayField;
				}
			}
			
			var model = new Foss.payment.PaymentGridModel(result.billPaymentVo.billPaymentResultDto.billPaymentEntity);
			var billPaymentDetailForm = win.items.items[0];
			billPaymentDetailForm.loadRecord(model);	
			//win.items.items[1].store.loadData(result.billPaymentVo.billPaymentResultDto.billPaymentAddDtoList);
			win.items.items[1].store.loadPage(1,{
				callback : function(records, operation, success) {
					var rawData = store.proxy.reader.rawData;
					if(!success && !rawData.isException){
						Ext.Msg.alert('',rawData.message);
						return false;
					}
				}
			});
			
			win.show();				
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
		}			
	});*/

}

// 作废付款单
pay.payment.disabledPayment = function(grid, rowIndex, colIndex){

	// 获取选中的款付单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//当前录入人不能作废自己的付款单
	if(selection.get('createUserCode')==stl.user.userName){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),selection.get('createUserName')+pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByCreateUser'));
		return false;
	}
	
	// 无效或红单的付款单不能作废
	if(selection.get('active')==pay.payment.INACTIVE||selection.get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByInvalidOrRed'));
		return false;
	}
	// 系统生成的付款单不能作废
	if(selection.get('createType')==pay.payment.SETTLEMENT__CREATE_TYPE__AUTO){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByAutoCreateType'));
		return false;
	}
	
	//已汇款和汇款中的付款单不能作废
	if(selection.get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByNotTransfer'));
		return false;
	}
	
	Ext.MessageBox.show({
        title: pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledMessageBoxTitle'),
        msg: pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledMessageBoxMsg'),
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: pay.payment.i18n('foss.stl.pay.common.ok'),
            cancel:pay.payment.i18n('foss.stl.pay.common.cancel')
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
       	// 付款单的grid
     		var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
     	
     		// 调用
     		Ext.Ajax.request({
     			//url:'../pay/disabledBillPayment.action',
     			url:pay.realPath('disabledBillPayment.action'),
     			params:{
     				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selection.data.paymentNo,
     				'billPaymentVo.billPaymentQueryDto.opinionNotes':text
     			},
     			success:function(response){
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     				
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledSuccessAlert'));
     			},
     			exception:function(response){
     				var result = Ext.decode(response.responseText);	
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     			}
     		});	
       	 }else{
       		 return false;
       	 }
        }
    });
}




// 批量作废付款单
pay.payment.disabledPaymentBatch = function(){
	var me = this;
	// 付款单的grid
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByNoSelectedData'));
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		//当前录入人不能作废自己的付款单
		if(selections[i].get('createUserCode')==stl.user.userName){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),selections[i].get('createUserName')+pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByCreateUser'));
			return false;
		}
		
		// 无效或红单的付款单不能作废
		if(selections[i].get('active')==pay.payment.INACTIVE||selections[i].get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByInvalidOrRed'));
			return false;
		}
		// 系统生成的付款单不能作废
		if(selections[i].get('createType')==pay.payment.SETTLEMENT__CREATE_TYPE__AUTO){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByAutoCreateType'));
			return false;
		}

		//已汇款和汇款中的付款单不能作废
		if(selections[i].get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledFailAlertByNotTransfer'));
			return false;
		}
		
		selectBillRepayNos.push(selections[i].get('paymentNo'));
	}
	
	Ext.MessageBox.show({
        title: pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledMessageBoxTitle'),
        msg: pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledMessageBoxMsg'),
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: pay.payment.i18n('foss.stl.pay.common.ok'),
            cancel:pay.payment.i18n('foss.stl.pay.common.cancel')
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
       		 
       	
    		me.disable(false);
    		//10秒后自动解除灰掉效果
    		setTimeout(function() {
    		me.enable(true);
    		}, 10000);
       		 
     		// 调用
     		Ext.Ajax.request({
     			url:pay.realPath('disabledBillPayment.action'),
     			params:{
     				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selectBillRepayNos,
     				'billPaymentVo.billPaymentQueryDto.opinionNotes':text
     			},
     			success:function(response){
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     				
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.disabledSuccessAlert'));
     			},
     			exception:function(response){
     				var result = Ext.decode(response.responseText);	
     				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
     				
     				// 重新载入数据
     				grid.store.load({
     					callback: function(records, operation, success) {
     						var result =   Ext.decode(operation.response.responseText);
     						
     						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
     						toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
     						toolBar.getComponent('paymentTotalAmount').setValue(stl.amountFormat(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount));
     				       }
     				});
     			}
     		});	
       	 }else{
       		 return false;
       	 }
        }
    });
}

// 申请备用金工作流
pay.payment.applySpareMoney = function(){
	var me = this;
	// 付款单的grid
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.applyFailAlertByNoSelectedData'));
		return false;
	}
	
	var selectBillRepayNos = [];
	var applayTotalAmount = 0;
	var isReturnBack = null;//是否押回单到达
	var workFlowType = selections[0].get('workFlowType');
	var payToSystem = FossDataDictionary.getDataByTermsCode('SETTLEMENT__PAYTOSYSTEM_TYPE')[0].valueCode;
	for(var i=0;i<selections.length;i++){
		// 无效或红单的付款单不能申请备用金
		if(selections[i].get('active')==pay.payment.INACTIVE||selections[i].get('isRedBack')==pay.payment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.applyFailAlertByInvalidOrRed'));
			return false;
		}
		
		if(selections[i].get('paymentType')!='CH'){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.payment')+selections[i].get('paymentNo')+pay.payment.i18n('foss.stl.pay.queryBillPayment.applyFailAlertByNotCH'));
			return false;
		}
		
		if(selections[i].get('remitStatus')!=pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER){
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.payment')+selections[i].get('paymentNo')+pay.payment.i18n('foss.stl.pay.queryBillPayment.applyFailAlertByNotTransfer'));
			return false;
		}
		//不同工作流类型不能同时申请备用金
		if(selections[i].get('workFlowType')!=workFlowType){
			if(workFlowType=='11' || workFlowType=='12' || 
				selections[i].get('workFlowType')=='11'|| selections[i].get('workFlowType')=='12'){
				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.errorRentCarWorkFlowToShareMoney'));
				return false;
			}
			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.notApplySpareMoneyByWorkFlowTypeNotUnique'));
			return false;
		}
		//判断押回单到达付款
		if(!Ext.isEmpty(payToSystem)&& pay.payment.PAYTOSYSTEM_TYPE_FSSC==payToSystem){
			var auditOpinionValue = selections[i].get('auditOpinion');
			if(!Ext.isEmpty(auditOpinionValue)){
				if(Ext.isEmpty(isReturnBack)){
					if(auditOpinionValue=="RBK"){
						isReturnBack = "Y";
					}else if(auditOpinionValue=="MH"){
						isReturnBack = "MH";
					}else{
						isReturnBack = "N";
					}
				}else{
					var isReturnBackNew = null;
					if(auditOpinionValue=="RBK"){
						isReturnBackNew = "Y";
					}else if(auditOpinionValue=="MH"){
						isReturnBackNew = "MH";
					}else{
						isReturnBackNew = "N";
					}
					//如果所选付款单不都为押回单到达，则抛异常
					if(isReturnBack!=isReturnBackNew){
						Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),'月结与非月结不能一起申请！押回单到达与非押回单到达不能一起申请！');
						return false;
					}
				}
			}
		}
		selectBillRepayNos.push(selections[i].get('paymentNo'));
		
		//计算总金额
		applayTotalAmount=applayTotalAmount+selections[i].get('amount');
	}
			
	var yesFn=function(){
		
		me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 10000);
		
		// 获取弹出窗口
		win = pay.payment.billPaymentEntityWindow;
		if(Ext.isEmpty(pay.payment.billPaymentEntityWindow)){
			pay.payment.billPaymentEntityWindow = Ext.create('Foss.payment.applySpareMoneyWindow');
			win = pay.payment.billPaymentEntityWindow;
		}
		var applySpareMoneyForm = win.items.items[0];
		applySpareMoneyForm.getForm().reset();
		applySpareMoneyForm.getForm().findField('selectBillPaymentNos').setValue(selectBillRepayNos);
		applySpareMoneyForm.getForm().findField('applayTotalAmount').setValue(stl.amountFormat(applayTotalAmount));
		var isAutoAbatementLoan = applySpareMoneyForm.getForm().findField('isAutoAbatementLoan');
		//如果对接财务共享且付款为外请车，则显示是否自动冲借支
		var payToSystem = FossDataDictionary.getDataByTermsCode('SETTLEMENT__PAYTOSYSTEM_TYPE')[0].valueCode;
		if(workFlowType==pay.payment.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY && !Ext.isEmpty(payToSystem) 
						&& pay.payment.PAYTOSYSTEM_TYPE_FSSC==payToSystem){
			isAutoAbatementLoan.show();
		}
		
		Ext.Ajax.request({
			url:pay.realPath('uploadAppendix.action'),
			success:function(response){
				var result = Ext.decode(response.responseText);
				pay.payment.batchNo = result.billPaymentVo.paymentEntity.batchNo;
				pay.payment.userCode = result.billPaymentVo.paymentEntity.createUserCode;
				pay.payment.payToSystem = result.billPaymentVo.payToSystem; 
				if('Y' == pay.payment.payToSystem){
					win.down('form').down('button').show();
				}
			}
		});
		win.show();
	}
	var noFn=function(){
	 	return false;
	};
	pay.payment.billPaymentConfirmAlert(pay.payment.i18n('foss.stl.pay.queryBillPayment.isConfirmApplyAlert'),yesFn,noFn);	
}

//保存申请备用金工作流
pay.payment.saveApplySpareMoney = function(me,form,window){

	var form=form.getForm();
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	
	if(form.isValid()){
		var selectBillPaymentNos = form.findField('selectBillPaymentNos').getValue();
		var accountNo = form.findField('accountNo').getValue();
		var payeeName = form.findField('payeeName').getValue();
		var payeeCode = form.findField('payeeCode').getValue();
		var mobilePhone = form.findField('mobilePhone').getValue();
		//var accountType=form.findField('accountType').getValue();
		var applyAmount = form.findField('applayTotalAmount').getValue();
		var bankBranchCode = form.findField('bankBranchCode').getValue();
		var bankBranchName = form.findField('bankBranchName').getValue();
		var bankHqCode = form.findField('bankHqCode').getValue();
		var bankHqName = form.findField('bankHqName').getValue();
		var cityCode = form.findField('cityCode').getValue();
		var cityName = form.findField('cityName').getValue();
		var provinceCode = form.findField('provinceCode').getValue();
		var provinceName = form.findField('provinceName').getValue();
		var invoiceHeadCode = form.findField('invoiceHeadCode').getValue();
		var invoiceHeadName = form.findField('invoiceHeadCode').getRawValue();
		var isAutoAbatementLoan = form.findField('isAutoAbatementLoan').getValue();
		var notes = form.findField('notes').getValue();
		//设置冲借支值
		if(!Ext.isEmpty(isAutoAbatementLoan) && isAutoAbatementLoan){
			isAutoAbatementLoan = pay.payment.AUTOABATEMENTLOAN_Y;
		}else{
			isAutoAbatementLoan = pay.payment.AUTOABATEMENTLOAN_N;
		}
		
		me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
		me.enable(true);
		}, 10000);
		
		Ext.Ajax.request({
			//url:'../pay/saveApplySpareMoney.action',
			url:pay.realPath('saveApplySpareMoney.action'),
			params:{
				'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':selectBillPaymentNos,
				'billPaymentVo.billPaymentQueryDto.payBillBankNo':accountNo,
				'billPaymentVo.billPaymentQueryDto.payBillPayeeName':payeeName,
				'billPaymentVo.billPaymentQueryDto.payBillPayeeCode':payeeCode,
				'billPaymentVo.billPaymentQueryDto.accountType':pay.payment.FIN_ACCOUNT_TYPE_CASHIER,
				'billPaymentVo.billPaymentQueryDto.provinceCode':provinceCode,
				'billPaymentVo.billPaymentQueryDto.provinceName':provinceName,
				'billPaymentVo.billPaymentQueryDto.cityCode':cityCode,
				'billPaymentVo.billPaymentQueryDto.cityName':cityName,
				'billPaymentVo.billPaymentQueryDto.bankHqCode':bankHqCode,
				'billPaymentVo.billPaymentQueryDto.bankHqName':bankHqName,
				'billPaymentVo.billPaymentQueryDto.bankBranchName':bankBranchName,
				'billPaymentVo.billPaymentQueryDto.bankBranchCode':bankBranchCode,
				'billPaymentVo.billPaymentQueryDto.invoiceHeadCode':invoiceHeadCode,
				'billPaymentVo.billPaymentQueryDto.invoiceHeadName':invoiceHeadName,
				'billPaymentVo.billPaymentQueryDto.mobilePhone':mobilePhone,
				'billPaymentVo.billPaymentQueryDto.applyAmount':applyAmount,
				'billPaymentVo.billPaymentQueryDto.isAutoAbatementLoan':isAutoAbatementLoan,
				'billPaymentVo.billPaymentQueryDto.batchNo':pay.payment.batchNo,
				'billPaymentVo.billPaymentQueryDto.notes':notes
			},
			success:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.applySuccessAlert'));
				window.close();	
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
			}
		});		
	}
}

//导出付款单
pay.payment.paymentListExport = function(){
	var me = this;
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	if(grid.getStore().data.length<1){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.exportFailAlertByNoData'));
		return false;
	}
	
	Ext.MessageBox.buttonText.yes = pay.payment.i18n('foss.stl.pay.common.ok');  
	Ext.MessageBox.buttonText.no = pay.payment.i18n('foss.stl.pay.common.cancel'); 
	
	Ext.Msg.confirm( pay.payment.i18n('foss.stl.pay.common.alert'), pay.payment.i18n('foss.stl.pay.queryBillPayment.isConfirmExportAlert'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			if(!Ext.fly('paymentBillListExportForm')){
				var frm = document.createElement('form');
				frm.id = 'paymentBillListExportForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
	
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			Ext.Ajax.request({
				//url:'../pay/exportPaymentBillList.action',
				url:pay.realPath('exportPaymentBillList.action'),
				form: Ext.fly('paymentBillListExportForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				}
			});
		}
	});
}
/**
 * 打印付款单
 */
pay.payment.paymentListPrint = function(){
	var me = this;
	var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
	//校验表格是否有数据，如果没有则提示不能打印
	if(grid.store.data.length==0){
		Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.queryBillPayment.printFailAlertByNoData'));
		return false;
	}
	//提示是否要打印
	Ext.Msg.confirm( pay.payment.i18n('foss.stl.pay.common.alert'), pay.payment.i18n('foss.stl.pay.queryBillPayment.isConfirmPrintAlert'), function(btn,text){
		if(btn=='yes'){
			var params=grid.store.submitParams;
			//将当前登录部门传递给后台
			params.paymentOrgCode = FossUserContext. getCurrentDeptCode();
			params.empCode = FossUserContext. getCurrentUserEmp().empCode;
			
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			
			//打印
			do_printpreview('billpayment',params,ContextPath.STL_WEB);
		}
	});
}


// -----------------------------------------------------------------------------------------------------------------------------

//客户类型：Model
Ext.define('Foss.payment.PaymentCustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.payment.PaymentCustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.payment.PaymentCustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'11',customerTypeName:pay.payment.i18n('foss.stl.pay.queryBillPayment.customerDetial')},
			{customerTypeCode:'22',customerTypeName:pay.payment.i18n('foss.stl.pay.queryBillPayment.airDetial')},
			{customerTypeCode:'33',customerTypeName:pay.payment.i18n('foss.stl.pay.queryBillPayment.airAgencyDetial')},
			{customerTypeCode:'44',customerTypeName:pay.payment.i18n('foss.stl.pay.queryBillPayment.vehAgencyDetial')},
			{customerTypeCode:'55',customerTypeName:pay.payment.i18n('foss.stl.pay.common.landStowage')},
			{customerTypeCode:'66',customerTypeName:pay.payment.i18n('foss.stl.pay.common.packagingSupplier')},
			{customerTypeCode:'77',customerTypeName:pay.payment.i18n('foss.stl.pay.common.leaseddriver')},
			{customerTypeCode:'88',customerTypeName:pay.payment.i18n('foss.stl.pay.common.homesupply')},
			{customerTypeCode:'99',customerTypeName:pay.payment.i18n('foss.stl.pay.common.partner')}
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

// 付款方式model
Ext.define('Foss.payment.PaymentTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

// 付款方式Store
Ext.define('Foss.payment.paymentTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.payment.PaymentTypeModel',
	data:{
		'items':[
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.all'),value:''},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.CH'),value:pay.payment.SETTLEMENT__PAYMENT_TYPE__CASH},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.telegraphTransfer'),value:pay.payment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.destAdvance') , value:pay.payment.SETTLEMENT__PAYMENT_TYPE__DEST_ADVANCE},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.destFee') , value:pay.payment.SETTLEMENT__PAYMENT_TYPE__DEST_FEE},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.rewardReturn') , value:pay.payment.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN},
			{name:pay.payment.i18n('foss.stl.pay.queryBillPayment.expressErrorReturn') , value:pay.payment.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	},
	autoLoad: true
});

// 付款状态model
Ext.define('Foss.payment.RemitStatusModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * FORM 定义
 */

// 按日期查询 Form
Ext.define('Foss.payment.PaymentQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout : 'column',
	items : [
				{
					xtype : 'datefield',
					name : 'billPaymentVo.billPaymentQueryDto.startAccountDate',
					fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayment.startAccountDate'),
					labelWidth : 85,
					value : stl.getTargetDate(new Date(),
							-pay.payment.PAYMENT_TENDAYS),
					format : 'Y-m-d',
					columnWidth : .3,
					allowBlank : false
				},
				{
					xtype : 'datefield',
					name : 'billPaymentVo.billPaymentQueryDto.endAccountDate',
					fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayment.endAccountDate'),
					labelWidth : 85,
					format : 'Y-m-d',
					value : new Date(),
					allowBlank : false,
					columnWidth : .3,
					maxValue : new Date()
				},
				{
					xtype : 'combobox',
					name : 'billPaymentVo.billPaymentQueryDto.paymentType',
					fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentType'),
					labelWidth : 85,
					store : Ext.create('Foss.payment.paymentTypeStore'),
					queryModel : 'local',
					displayField : 'name',
					valueField : 'value',
					columnWidth : .3,
					editable:false,
					value:''
				},{

			    	xtype:'combobox',
			    	name:'customerType',
			    	fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.customerType'),//客户类型
			    	labelWidth : 85,
					store:Ext.create('Foss.payment.PaymentCustomerTypeStore'),
					queryModel:'local',
					value:'11',
					editable:false,
					columnWidth : .3,
					displayField:'customerTypeName',
					valueField:'customerTypeCode',
			    	listeners:{
						"select": {
							fn: function(_combo, _r){
								var cusValue=_combo.ownerCt.getForm().findField('customerType').getValue();
								var form =this.up('form').getForm();

								var customerDetial = form.findField('customerDetial');
								var airDetial = form.findField('airDetial');
								var airAgencyDetial = form.findField('airAgencyDetial');
								var vehAgencyDetial = form.findField('vehAgencyDetial');
								var landStowage= form.findField('landStowage');
								var packagingCode= form.findField('packagingCode');
								var leaseddriver = form.findField('leaseddriver');
								var homesupply = form.findField('homesupply');
								var partner = form.findField('partner');
								var arrCode = ['11','22','33','44','55','66','77','88','99'];
								var arrEl = [customerDetial,
								             airDetial,
								             airAgencyDetial,
								             vehAgencyDetial,
								             landStowage,
								             packagingCode,
								             leaseddriver,
								             homesupply,
								             partner
								             ];
								
								for(var i=0;i<arrCode.length;i++) { 
									
									if (arrCode[i] == cusValue){ // 选择某种客户
										arrEl[i].show();
										arrEl[i].setReadOnly(false);
										arrEl[i].setValue(null);
									}else {
										arrEl[i].hide();
										arrEl[i].setReadOnly(true);
										arrEl[i].setValue(null);
									}
								}
								
							}
						}
					}
				},{				
						xtype:'commoncustomerselector',
						all:'true',
						singlePeopleFlag : 'Y',
						fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.customerDetial'),
						labelWidth : 85,
						name:'customerDetial',
						allowBlank:true,
						columnWidth : .3,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页		
				 },{	
			        	xtype:'commonairlinesselector',
			        	all:'true',
						fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.airDetial'),
						labelWidth : 85,
						name:'airDetial',
						hidden:true,
						allowBlank:true,
						columnWidth : .3,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页
				 },{	
				    	xtype:'commonairagentselector',
				    	all:'true',
						fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.airAgencyDetial'),
						labelWidth : 85,
						columnWidth : .3,
						name:'airAgencyDetial',
						hidden:true,
						allowBlank:true,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页		
				 },{	
				    	xtype:'commonvehagencycompselector',
				    	all:'true',
						fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.vehAgencyDetial'),
						labelWidth : 85,
						name:'vehAgencyDetial',
						hidden:true,
						allowBlank:true,
						columnWidth : .3,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页				
				},{				
					xtype:'commonLdpAgencyCompanySelector',
					fieldLabel:pay.payment.i18n('foss.stl.pay.common.landStowage'),
					labelWidth : 85,
					name:'landStowage',
					allowBlank:true,
					columnWidth : .3,
					hidden:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true //分页		
				 },{
			    	xtype:'dynamicPackagingSupplierSelector',
				    listWidth:300,
				    name:'packagingCode',
				    columnWidth : .3,
				    fieldLabel : pay.payment.i18n('foss.stl.pay.common.packagingSupplier'),
				    labelWidth : 85,
				    active:'Y',
				    hidden:true
			 	},{
			    	xtype:'commonleaseddriverselector',
				    listWidth:300,
				    name:'leaseddriver',
				    columnWidth : .3,
				    fieldLabel : pay.payment.i18n('foss.stl.pay.common.leaseddriver'),
				    labelWidth : 85,
				    active:'Y',
				    hidden:true
			 	},{ //新增家装代理
			    	xtype:'supplierSelector',
				    listWidth:300,
				    name:'homesupply',
				    columnWidth : .3,
				    fieldLabel : pay.payment.i18n('foss.stl.pay.common.homesupply'),//家装代理
				    labelWidth : 85,
				    active:'Y',
				    hidden:true
			 	}, {
					xtype : 'commonsaledepartmentselector',
					all:'true',
					name : 'partner',
					fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayable.partnerName'),
					allowBlank : true,
					isPaging : true,
					columnWidth : .3,
					height:24,
					singlePeopleFlag : 'Y',
					hidden:true
				},{
					xtype : 'combobox',
					name : 'billPaymentVo.billPaymentQueryDto.remitStatus',
					fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayment.remitStatus'),
					labelWidth : 85,
					columnWidth : .3,
					store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYMENT__REMIT_STATUS,null,{
						 'valueCode': '',
	               		 'valueName': pay.payment.i18n('foss.stl.pay.queryBillPayment.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value:'',
					editable:false
				},{

			    	xtype:'combobox',
			    	name:'billPaymentVo.billPaymentQueryDto.isInit',
			    	fieldLabel:pay.payment.i18n('foss.stl.pay.common.isInit'),
			    	labelWidth : 85,
			    	columnWidth : .3,
					store:FossDataDictionary. getDataDictionaryStore('FOSS_BOOLEAN',null,{
						 'valueCode': '',
	               		 'valueName': pay.payment.i18n('foss.stl.pay.queryBillPayment.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value:'',
					editable:false
					
				}/*,{
			    	xtype: 'dynamicorgcombselector',
					name:'createOrgCode',
			        fieldLabel:  pay.payment.i18n('foss.stl.pay.queryBillPayment.createOrgCode'),
			        allowblank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true, //分页
		    		columnWidth : .3
		    	}*/,{
					xtype:'container',
					border:false,
					columnWidth : 1,
					html:'&nbsp;'
				},{
					border: 1,
					xtype:'container',
					columnWidth:1,
					defaultType:'button',
					layout:'column',
					items:[{
						  text:pay.payment.i18n('foss.stl.pay.common.reset'),
						  columnWidth:.1,
						  handler:pay.payment.paymentQueryReset
					  	},{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.8
						},
					  	{
						  text:pay.payment.i18n('foss.stl.pay.common.query'),
						  columnWidth:.1,
						  cls:'yellow_button',  
						  handler:function(){
							  var form=this.up('form');
							  var me = this;
							  pay.payment.paymentQuery(form,me,pay.payment.QUERY_BY_DATE)
						  }
					  	}]
				}]			
});

// 按单号查询 Form
Ext.define('Foss.payment.PaymentQueryInfoByDateNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.billNos'),
		//354658-校验至14位运单号
		regex:/^((\s)*[A-Za-z0-9]{8,14}[;,])*((\s)*[A-Za-z0-9]{8,14}[;,]?)(\s)*$/i,
		regexText:pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosRegexText'),
		emptyText: pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'billNos',
		columnWidth:.4
	},{

		xtype:'container',
		columnWidth:.3,
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.payment.i18n('foss.stl.pay.queryBillPayment.billNosNots')+'</span>'
			}
		}]
    
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:pay.payment.i18n('foss.stl.pay.common.reset'),
			  columnWidth:.1,
			  handler:pay.payment.paymentQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},
		  	{
			  text:pay.payment.i18n('foss.stl.pay.common.query'),
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  var me = this;
				  pay.payment.paymentQuery(form,me,pay.payment.QUERY_BY_NOS)
			  }
		  	}]
	}]
});

//按来源单号查询 Form
Ext.define('Foss.payment.PaymentQueryInfoBySourceNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNos'),
		regex:/^((\s)*[A-Za-z0-9]{8,20}[;,])*((\s)*[A-Za-z0-9]{8,20}[;,]?)(\s)*$/i,
		emptyText: pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'sourceBillNos',
		columnWidth:.4
	},{
		xtype:'container',
		columnWidth:.3,
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNosNots')+'</span>'
			}
		}]
    
	
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:pay.payment.i18n('foss.stl.pay.common.reset'),
			  columnWidth:.1,
			  handler:pay.payment.paymentQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},
		  	{
			  text:pay.payment.i18n('foss.stl.pay.common.query'),
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  var me = this;
				  pay.payment.paymentQuery(form,me,pay.payment.QUERY_BY_SOURCE_NOS)
			  }
		  	}]
	}]
});

//按工作流号号查询 Form
Ext.define('Foss.payment.PaymentQueryInfoByWorkFlowNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.workFlowNo'),
		regex:/^((\s)*[A-Za-z0-9]{6,50}[;,])*((\s)*[A-Za-z0-9]{6,50}[;,]?)(\s)*$/i,
		emptyText: pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'workFlowNos',
		columnWidth:.4
	},{
		xtype:'container',
		columnWidth:.3,
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.payment.i18n('foss.stl.pay.common.WorkFlowNosNots')+'</span>'
			}
		}]
    
	
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:pay.payment.i18n('foss.stl.pay.common.reset'),
			  columnWidth:.1,
			  handler:pay.payment.paymentQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},
		  	{
			  text:pay.payment.i18n('foss.stl.pay.common.query'),
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  var me = this;
				  pay.payment.paymentQuery(form,me,pay.payment.QUERY_BY_WORKFLOW_NOS)
			  }
		  	}]
	}]
});
// 查询tab
Ext.define('Foss.payment.PaymentQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	//width : 1060,
	height : 260,
	items : [ {
		title: pay.payment.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.payment.PaymentQueryInfoByDate')
               ]
	}, {
		title: pay.payment.i18n('foss.stl.pay.common.queryByNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.payment.PaymentQueryInfoByDateNos')
               ]
	},{

		title: pay.payment.i18n('foss.stl.pay.common.queryBySourceNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.payment.PaymentQueryInfoBySourceNos')
               ]
	
	},{
		title: pay.payment.i18n('foss.stl.pay.common.queryByWorkFlowNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.payment.PaymentQueryInfoByWorkFlowNo')
               ]
	}]
});


/**
 * 付款单模型
 */
Ext.define('Foss.payment.PaymentGridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'remitStatus'
	},{	
		name:'paymentNo'
	},{
		name:'workflowNo'
	},{
		name:'customerCode'
	},{
		name:'customerName'		
	},{
		name:'amount'		
	},{
		name:'auditStatus'		
	},{
		name:'sourceBillNo'
	},{
		name:'sourceBillType'
	},{	
		name:'paymentOrgCode'
	},{
		name:'paymentOrgName'
	},{
		name:'paymentCompanyCode'
	},{
		name:'paymentCompanyName'
	},{	
		name:'active'
	},{	
		name:'isRedBack'
	},{	
		name:'createUserCode'
	},{
		name:'createUserName'
	},{
		name:'auditUserCode'
	},{
		name:'auditUserName'
	},{
		name:'modifyUserCode'
	},{
		name:'modifyUserName'
	},{
		name:'disableUserCode'
	},{
		name:'disableUserName'
	},{
		name:'businessDate'
	},{
		name:'accountDate',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'createTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'createType'	
	},{
		name:'modifyTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'disableTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{	
		name:'billType'
	},{
		name:'paymentType'
	},{
		name:'mobilePhone'
	},{
		name:'accountNo'
	},{
		name:'payeeName'
	},{
		name:'payeeName'
	},{
		name:'accountType'
	},{
		name:'provinceCode'
	},{
		name:'provinceName'		
	},{
		name:'cityCode'
	},{
		name:'cityName'		
	},{
		name:'accountBankCity'
	},{
		name:'bankHqName'
	},{
		name:'bankBranchName'
	},{
		name:'bankBranchCode'		
	},{
		name:'invoiceHeadCode'
	},{
		name:'invoiceHeadName'		
	},{
		name:'bankHqCode'
	},{
		name:'auditOpinion'
	},{
		name:'disableOpinion'
	},{
		name:'applyWorkFlowNo'
	},{
		name:'notes'
	},{
		name:'isInit'
	},{
		name:'versionNo'
	},{
		name:'workFlowType'
	}]
});

/**
 * 付款单表格store
 */
Ext.define('Foss.payment.PaymentGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.payment.PaymentGridModel',
	pageSize: pay.payment.ONEPAGESIZE ,
	sorters: [{
	     property: 'paymentNo',
	     direction: 'ASC'
	 }],
	proxy:{
		type:'ajax',
		//url:'../pay/queryPaymentOrderBill.action',
		url:pay.realPath('queryPaymentOrderBill.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billPaymentVo.billPaymentResultDto.billPaymentEntityList',
			totalProperty:'totalCount'
			
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

// 付款单列表
Ext.define('Foss.payment.PaymentGrid',{
	extend:'Ext.grid.Panel',
    title: pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentGridTitle'),
    frame:true,
    hidden:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.payment.PaymentGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
	// store:null,
    hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	paymentTotalAmount:null,// 总金额
	getPaymentTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.paymentTotalAmount)){
			me.paymentTotalAmount=0;
		}
		return me.paymentTotalAmount;
	},
	paymentTotalRows:null,// 总条数
	getPaymentTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.paymentTotalRows)){
			me.paymentTotalRows=0;
		}
		return me.paymentTotalRows;
	},
	listeners : {
		'itemdblclick' : function(th, record) {
			var paymentNo = record.data.paymentNo;
			var active = record.data.active;
			var isRedBack = record.data.isRedBack;
			var me = this;
			var paramV = {
					'billPaymentVo.billPaymentQueryDto.selectBillPaymentNos':paymentNo,
					'billPaymentVo.billPaymentQueryDto.active':active,
					'billPaymentVo.billPaymentQueryDto.isRedBack':isRedBack
				};
			
			// 获取弹出窗口
			win = pay.payment.billPaymentDetailWindow;
			if(Ext.isEmpty(pay.payment.billPaymentDetailWindow)){
				pay.payment.billPaymentDetailWindow = Ext.create('Foss.payment.billPaymentDetailWindow');
				win = pay.payment.billPaymentDetailWindow;
			}
			win.show();	
			
			var store = win.items.items[1].store;
			store.setSubmitParams(paramV);
			
			store.loadPage(1,{
				callback : function(records, operation, success) {
					var rawData = store.proxy.reader.rawData;
					if(!success && !rawData.isException){
						Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),rawData.message);
						return false;
					}
					if(success){
						
						var result = Ext.decode(operation.response.responseText);  
						if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity!=null){
							
							if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==null||result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==''){
								result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo=result.billPaymentVo.billPaymentResultDto.billPaymentEntity.applyWorkFlowNo
							}
							
							if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=null && result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=''){	
								var displayField = null;
								if(displayField==null){
					    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.CRM_ACCOUNT_NATURE);
					    		}
					    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
					    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.FIN_ACCOUNT_TYPE);
					    		}
					    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
					    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
					    		}
					    		result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType = displayField;
							}
							
							var model = new Foss.payment.PaymentGridModel(result.billPaymentVo.billPaymentResultDto.billPaymentEntity);
							var billPaymentDetailForm = win.items.items[0];
							billPaymentDetailForm.loadRecord(model);	
							//到付转预收code转中文 - 231438
							if(selection.get('paymentType') == 'FCUS'){
		                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('到付转预收');
		                    }
							if(selection.get('paymentType') == 'CF'){
		                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('委托派费转预收');
		                    }
		                    if(selection.get('paymentType') == 'JTU'){
	                    		billPaymentDetailForm.getForm().findField('paymentType').setValue('奖励应付自动返');
		                    }
		                    if(selection.get('paymentType') == 'KTU'){
		                    	billPaymentDetailForm.getForm().findField('paymentType').setValue('快递差错应付自动返');
	                   	 	}
						}
						
					}
				}
			});
			// TODO xx
			
			// 打开注释
			/*Ext.Ajax.request({
				//url:'../pay/detailPayment.action',
				url:pay.realPath('detailPayment.action'),
				params:paramV,
				success:function(response){
					
					var result = Ext.decode(response.responseText);	
					// 获取弹出窗口
					win = pay.payment.billPaymentDetailWindow;
					if(Ext.isEmpty(pay.payment.billPaymentDetailWindow)){
						pay.payment.billPaymentDetailWindow = Ext.create('Foss.payment.billPaymentDetailWindow');
						win = pay.payment.billPaymentDetailWindow;
					}
					
					if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity!=null){
						
						if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==null||result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo==''){
							result.billPaymentVo.billPaymentResultDto.billPaymentEntity.workflowNo=result.billPaymentVo.billPaymentResultDto.billPaymentEntity.applyWorkFlowNo
						}
						
						if(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=null && result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType!=''){	
							var displayField = null;
							if(displayField==null){
				    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.CRM_ACCOUNT_NATURE);
				    		}
				    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
				    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.FIN_ACCOUNT_TYPE);
				    		}
				    		if(displayField==result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType){
				    			displayField = FossDataDictionary.rendererSubmitToDisplay(result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
				    		}
				    		result.billPaymentVo.billPaymentResultDto.billPaymentEntity.accountType = displayField;
						}
					}
					
					var model = new Foss.payment.PaymentGridModel(result.billPaymentVo.billPaymentResultDto.billPaymentEntity);
					var billPaymentDetailForm = win.items.items[0];
					billPaymentDetailForm.loadRecord(model);	
					//win.items.items[1].store.loadData(result.billPaymentVo.billPaymentResultDto.billPaymentAddDtoList);
								
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),result.message);
				}			
			});*/
		}
	},
	columns:[{
    	xtype:'actioncolumn',
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.operateColumns'),
    	width:120,
    	align: 'center',
    	items:[{
//				iconCls : 'foss_icons_stl_auditing',
//				tooltip:pay.payment.i18n('foss.stl.pay.common.audit'),
//				handler:function(grid, rowIndex, colIndex){
//					pay.payment.aduitPayment(grid, rowIndex, colIndex)
//				},
//				getClass:function(v,m,r,rowIndex){
//					if(r.get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE){
//						return 'statementBill_hide';
//					}else {
//						return 'foss_icons_stl_auditing';
//					}
//				}
//    	},{
//    		iconCls : 'foss_icons_stl_fauditing',
//			tooltip:pay.payment.i18n('foss.stl.pay.common.unAudit'),
//			getClass:function(v,m,r,rowIndex){
//				if(r.get('auditStatus')==pay.payment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT){
//					return 'statementBill_hide';
//				}else {
//					return 'foss_icons_stl_fauditing';
//				}
//			},
//			handler:function(grid, rowIndex, colIndex){
//				pay.payment.revAduitPayment(grid, rowIndex, colIndex)
//			}
//    	},{
    		iconCls : 'deppon_icons_delete',
			tooltip:pay.payment.i18n('foss.stl.pay.common.disabled'),
			getClass:function(v,m,r,rowIndex){
				if(!pay.payment.isPermission('/stl-web/pay/disabledBillPayment.action')){
					return 'statementBill_hide';
				}else{
					return 'deppon_icons_delete';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				pay.payment.disabledPayment(grid, rowIndex, colIndex)
			}
    	},{
    		iconCls : 'deppon_icons_showdetail',
			tooltip:pay.payment.i18n('foss.stl.pay.common.detial'),
			getClass:function(v,m,r,rowIndex){
				if(!pay.payment.isPermission('/stl-web/pay/detailPayment.action')){
					return 'statementBill_hide';
				}else{
					return 'deppon_icons_showdetail';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				pay.payment.showDetialPayment(grid, rowIndex, colIndex)
			}
    	}]
    
	},{
		dataIndex: 'id',
		hidden:true
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentNo'),
		dataIndex: 'paymentNo'
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.workflowNo'),
		dataIndex: 'workflowNo',
		renderer:function(value,o,r){
			if(value==null||value==''){
				return r.get('applyWorkFlowNo')
			}else{
				return value;
			}
		}
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__IS_RED_BACK);
    		return displayField;
		}
	},{
//		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillNo'),
//		dataIndex: 'sourceBillNo',
//		renderer:function(value){
//			if(pay.payment.DEFAULT_BILL_NO==value){
//				return "";
//			}else{
//				return value;
//			}
//		}
//			
//	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentOrgName'),
		dataIndex: 'paymentOrgName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.customerName'), 
		dataIndex: 'customerName'		
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.customerCode'),
		dataIndex: 'customerCode'
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
			
	},{
		header: '外请车类型',
		dataIndex: 'auditOpinion',
		renderer:function(value){
    		var displayField = null;
    		if(!Ext.isEmpty(value)){
    			if(value=="RBK"){
    				displayField = "押回单到达"; 
    			}else if(value =="MH"){
    				displayField = "月结"; 
    			}
    		}
    		return displayField;
    	}
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.remitStatus'),
		dataIndex: 'remitStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_PAYMENT__REMIT_STATUS);
    		return displayField;
    	}		
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.amount'),
		dataIndex: 'amount',
		align: 'right', 
		//xtype:'numbercolumn'
		renderer:stl.amountFormat	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.createType'),
		dataIndex: 'createType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__CREATE_TYPE);
    		return displayField;
    	}	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.createTime'),
		dataIndex: 'createTime',
		width:140,
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.createUserName'),
		dataIndex: 'createUserName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.createUserCode'),
		dataIndex: 'createUserCode',
		hidden:true
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			if(value == 'FCUS'){
				var displayField = '到付转预收';
				return displayField;
			}else if(value == 'CF'){
				var displayField = '委托派费转预收';
				return displayField;
			}else if(value == 'JTU'){
				var displayField = '奖励应付自动返';
				return displayField;
			}else if(value == 'KTU'){
				var displayField = '快递差错应付自动返';
				return displayField;
			}
			else{
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
	    		return displayField;
			}
    	}		
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.notes'),
		dataIndex: 'notes'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.accountNo'),
		dataIndex: 'accountNo'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.accountType'),
		dataIndex: 'accountType',
		renderer:function(value){
			var displayField = null;
			if(displayField==null){
    			displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.CRM_ACCOUNT_NATURE);
    		}
    		if(displayField==value){
    			displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FIN_ACCOUNT_TYPE);
    		}
    		if(displayField==value){
    			displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
    		}
    		return displayField;
    	}	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.provinceName'),
		dataIndex: 'provinceName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.cityName'),
		dataIndex: 'cityName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.bankHqName'),
		dataIndex: 'bankHqName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchName'),
		dataIndex: 'bankBranchName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchCode'),
		dataIndex: 'bankBranchCode'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.payeeName'),
		dataIndex: 'payeeName'	
	},{
		header: pay.payment.i18n('foss.stl.pay.queryBillPayment.disableOpinion'),
		dataIndex: 'disableOpinion'	
	},{
		header: pay.payment.i18n('foss.stl.pay.common.isInit'),
		dataIndex: 'isInit',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
		}
	}],
	listeners:{
		'selectionchange':function(th,selected,eOpts ){
			var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
			//获取当前选择条数
			var tbars = grid.dockedItems.items[3].query('displayfield');
			var selectTotalAmount = 0;
			//计算金额
			for(var i=0;i<selected.length;i++){
				selectTotalAmount =stl.amountAdd(selectTotalAmount,selected[i].get('amount'));
			}
			tbars[2].setValue(selected.length);
			tbars[3].setValue(selectTotalAmount);
		}
	},
	constructor:function(config){
	
		var me = this;
		me.store=Ext.create('Foss.payment.PaymentGridStore');

		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.85
			},{
				xtype :'button',
				text :pay.payment.i18n('foss.stl.pay.common.export'),
				columnWidth :.06,
				handler: pay.payment.paymentListExport,
				disabled: !pay.payment.isPermission('/stl-web/pay/exportPaymentBillList.action'),
				hidden: !pay.payment.isPermission('/stl-web/pay/exportPaymentBillList.action')
			},{
				xtype :'button',
				text :pay.payment.i18n('foss.stl.pay.common.print'),
				columnWidth :.06,
				handler :pay.payment.paymentListPrint
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'paymentTotalRows',
				name:'paymentTotalRows',
				columnWidth:.2,
				labelWidth:100,
				fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentTotalRows'),
				value:me.getPaymentTotalRows()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.1
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'paymentTotalAmount',
				name:'paymentTotalAmount',
				columnWidth:.2,
				labelWidth:90,
				fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentTotalAmount'),
				value:me.getPaymentTotalAmount()
			},{
				xtype:'displayfield',
				allowBlank:true,
				name:'selectTotalRows',
				columnWidth:.25,
				labelWidth:130,
				fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.selectTotalRows'),
				value:0
			},{
				xtype:'displayfield',
				allowBlank:true,
				name:'selectTotalAmount',
				columnWidth:.25,
				labelWidth:90,
				fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentTotalAmount'),
				value:0
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:1,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				height:1,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.8
//			},{
//				xtype: 'button', 
//				text: pay.payment.i18n('foss.stl.pay.common.audit'),
//				columnWidth:.05,
//				handler:pay.payment.aduitPaymentBatch
//			},{
//				xtype: 'button', 
//				text: pay.payment.i18n('foss.stl.pay.common.unAudit'),
//				columnWidth:.05,
//				handler:pay.payment.revAduitPaymentBatch
			},{
				xtype: 'button', 
				text: pay.payment.i18n('foss.stl.pay.common.disabled'),
				columnWidth:.05,
				handler:pay.payment.disabledPaymentBatch,
				disabled: !pay.payment.isPermission('/stl-web/pay/disabledBillPayment.action'),
				hidden: !pay.payment.isPermission('/stl-web/pay/disabledBillPayment.action')
			},{
				xtype:'button',
				text:pay.payment.i18n('foss.stl.pay.queryBillPayment.applySpareMoney'),
				columnWidth:.15,
				handler:pay.payment.applySpareMoney,
				disabled: !pay.payment.isPermission('/stl-web/pay/saveApplySpareMoney.action'),
				hidden: !pay.payment.isPermission('/stl-web/pay/saveApplySpareMoney.action')
			}]
		}];	
		me.callParent();
	}
});



/*
 * 显示申请备用金界面form
 */
Ext.define('Foss.payment.applySpareMoneyForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	width:stl.SCREENWIDTH*0.77,
	frame:true,
	title:pay.payment.i18n('foss.stl.pay.queryBillPayment.applySpareMoneyFormTitle'),
	defaultType:'textfield',
	layout:'column',
	defaults:{
		margin:'5 5 5 5',
		//readOnly:true,
		columnWidth:.3
	},
	layout:{
		type :'column',
		columns :3
	},
	items:[{
		xtype : 'commonbankaccountselector',
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.accountNo'),
		name:'accountNo',
		accountTypes:pay.payment.FIN_ACCOUNT_TYPE_CASHIER_ACCOUNTTYPE+","+pay.payment.FIN_ACCOUNT_TYPE_CASHIER_COMPANY,
		operatorId:FossUserContext.getCurrentUserEmp().empCode,
		allowBlank : false,
		exactQuery:'Y', //精确查找
		deptCode : stl.currentDeptCode,
		height:24,
		listWidth : 340,// 设置下拉框宽度
		listeners:{
			'select':function(combo,records,eOpts){
				if(records.length>0){
					var form = this.up('form').getForm();
					var record = records[0];
					form.findField('provinceName').setValue(record.get('provName'));
 					form.findField('provinceCode').setValue(record.get('provCd'));
 					form.findField('cityName').setValue(record.get('cityName'));
 					form.findField('cityCode').setValue(record.get('cityCd'));
 					form.findField('bankHqCode').setValue(record.get('bankCd'));
 					form.findField('bankHqName').setValue(record.get('bankName'));
 					form.findField('bankBranchName').setValue(record.get('subbranchName'));
 					form.findField('bankBranchCode').setValue(record.get('subbranchCd'));
 					form.findField('payeeName').setValue(record.get('bankAccName'));
 					form.findField('payeeCode').setValue(record.get('payeeNo'));
 					pay.payment.FIN_ACCOUNT_TYPE_CASHIER=record.get('accountType');
 					if(record.get('accountType')!=null&&record.get('accountType')!=''){
 			    		var displayField = FossDataDictionary.rendererSubmitToDisplay(record.get('accountType'),settlementDict.FIN_ACCOUNT_TYPE);
 			    		form.findField('accountType').setValue(displayField);
 					}
				}
			}
		}
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.provinceName'),
		name:'provinceName',
		readOnly:true,
		height:24
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.provinceCode'),
		name:'provinceCode',
		readOnly:true,
		height:24,
		hidden:true
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.cityName'),
		name:'cityName',
		readOnly:true,
		height:24
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.cityCode'),
		name:'cityCode',
		readOnly:true,
		height:24,
		hidden:true
	},{
		xtype:'container',
		columnWidth:1,
//		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.payment.i18n('foss.stl.pay.common.accountNoText')+'</span>'
			}
		}]
    },{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankHqName'),
		name:'bankHqName',
		readOnly:true,
		height:24	
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankHqCode'),
		name:'bankHqCode',
		readOnly:true,
		height:24,
		hidden:true
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchName'),
		name:'bankBranchName',
		readOnly:true,
		height:24		
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchCode'),
		name:'bankBranchCode',
		readOnly:true,
		height:24			
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.accountType'),
		name:'accountType',
		readOnly:true,
		height:24
		//value:pay.payment.i18n('foss.stl.pay.queryBillPayment.accountTypeDefaultValue')
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.payeeName'),
		name:'payeeName',
		readOnly:true,
		height:24	
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.payeeCode'),
		name:'payeeCode',
		readOnly:true,
		height:24
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.applayTotalAmount'),
		name:'applayTotalAmount',
		xtype:'displayfield',
		readOnly:true,
		height:26
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.mobilePhone'),
		name:'mobilePhone',
		regex:/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/,
		allowBlank : false,
		height:24
		//hidden:true
	},{
 		xtype:'commonsubsidiaryselector',
 		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.invoiceHeadCode'),
 		readOnly:false,
 		name:'invoiceHeadCode',
 		allowBlank:false,
		height:24
	},{
 		xtype:'checkbox',
 		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.isAutoAbatementLoan'),
 		readOnly:false,
 		allowBlank:true,
 		name:'isAutoAbatementLoan',
 		hidden:true,
		height:24
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.cityCode'),
		name:'cityCode',
		height:24,
		hidden:true
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankHqCode'),
		name:'bankHqCode',
		height:24,
		hidden:true
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.selectBillPaymentNos'),
		name:'selectBillPaymentNos',
		height:24,
		hidden:true
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.notes'),
		xtype:'textarea',
		name:'notes',
		columnWidth:1
	},{

		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
	 			text:pay.payment.i18n('foss.stl.pay.common.upload'),
	 			name:'uploadddw',
	 			columnWidth:.1,
	 			hidden:true,
	 			handler:pay.payment.addPaymentUpload
 			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.27
			},{
				text:pay.payment.i18n('foss.stl.pay.common.ok'),
			  	columnWidth:.08,
			  	cls:'yellow_button',  
			  	disabled: !pay.payment.isPermission('/stl-web/pay/saveApplySpareMoney.action'),
			  	hidden: !pay.payment.isPermission('/stl-web/pay/saveApplySpareMoney.action'),
			  	handler:function(){
			  		var me = this;
			  		var form=me.up('form');
			  		var window = me.up('window');
			  		
			  		if(form.getForm().isValid()){
			  			pay.payment.saveApplySpareMoney(me,form,window);
				  		
				  		// 付款单的grid
				  		var grid = Ext.getCmp('T_pay-manageBillPayment_content').getPaymentGrid();
				  		// 重新载入数据
						grid.store.load({
							callback: function(records, operation, success) {
								var result =   Ext.decode(operation.response.responseText);
			
								toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
								toolBar.getComponent('paymentTotalRows').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalRows);
								toolBar.getComponent('paymentTotalAmount').setValue(result.billPaymentVo.billPaymentResultDto.paymentTotalAmount);
						       }
						});
			  		}else{
			  			Ext.Msg.alert(pay.payment.i18n('foss.stl.pay.common.alert'),pay.payment.i18n('foss.stl.pay.common.validateFailAlert'));
			  			return false;
			  		}
			  	
			  	}
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.2
			},{
				text:pay.payment.i18n('foss.stl.pay.common.cancel'),
				columnWidth:.08,
				handler:function(){
					this.up('window').close();		
				}	
		  	},{
		  		xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.27
		  	}]
	
	}]
});

/**
 * 声明申请备用金windows
 */
Ext.define('Foss.payment.applySpareMoneyWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.8,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[
	       Ext.create('Foss.payment.applySpareMoneyForm')]
});


/**
 * 付款单明细form
 */
Ext.define('Foss.payment.BillPaymentDetailForm',{
	extend:'Ext.form.Panel',
	title:pay.payment.i18n('foss.stl.pay.queryBillPayment.BillPaymentDetailFormTitle'),
	frame:true,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	defaults:{
		margin:'5 5 5 5',
		labelWidth:120,
		readOnly:true,
		columnWidth:.33
	},
	defaultType:'textfield',
	layout:'column',
	items:[{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentNoByPaymentDetailForm'),
		name:'paymentNo'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.workflowNo'),
		name:'workflowNo'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.active'),
		name:'active',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.FOSS_ACTIVE),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.isRedBack'),
		name:'isRedBack',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__IS_RED_BACK),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.sourceBillType'),
		name:'sourceBillType',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYMENT__SOURCE_BILL_TYPE),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentOrgName'),
		name:'paymentOrgName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.customerName'),
		name:'customerName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.customerCode'),
		name:'customerCode'
	},{
		xtype : 'datefield',
		name : 'accountDate',
		fieldLabel : pay.payment.i18n('foss.stl.pay.queryBillPayment.accountDate')
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.remitStatus'),
		name:'remitStatus',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYMENT__REMIT_STATUS),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		xtype:'numberfield',
		decimalPrecision:2,
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.amountByPaymentDetailForm'),
		name:'amount'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.createType'),
		name:'createType',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__CREATE_TYPE),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'
	},{
		xtype : 'datefield',
		name : 'createTime',
		fieldLabel :pay.payment.i18n('foss.stl.pay.queryBillPayment.createTime')
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.createUserName'),
		name:'createUserName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentType'),
		name:'paymentType',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'	
				
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.notes'),
		name:'notes'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.accountNo'),
		name:'accountNo'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.accountType'),
		name:'accountType',
		xtype : 'combobox',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.COD__PUBLIC_PRIVATE_FLAG),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode'		
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.provinceName'),
		name:'provinceName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.cityName'),
		name:'cityName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankHqName'),
		name:'bankHqName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchName'),
		name:'bankBranchName'
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.bankBranchCode'),
		name:'bankBranchCode'
	
	},{
		fieldLabel:pay.payment.i18n('foss.stl.pay.queryBillPayment.payeeName'),
		name:'payeeName'
	},{
		fieldLabel: pay.payment.i18n('foss.stl.pay.queryBillPayment.disableOpinion'),
		name: 'disableOpinion',
		columnWidth:1
	}]
});

/**
 * 付款单明细相关
 */
Ext.define('Foss.payment.BillPaymentDetailModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	fields:[{
		name:'paymentNo'
	},{
		name:'payableNo'
	},{
		name:'waybillNo'		
	},{
		name:'sourceBillNo'
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
		name:'notes'
	},{
		name:'businessDate',
		type:'date',
		convert: stl.longToDateConvert
	}]
});
Ext.define('Foss.payment.BillPaymentDetailStore',{
	extend:'Ext.data.Store',
	pageSize:10,
	model:'Foss.payment.BillPaymentDetailModel',
	proxy:{
		type:'ajax',
		actionMethods : 'post',
		url:pay.realPath('detailPayment.action'),
		reader:{
			type:'json',
			root:'billPaymentVo.billPaymentResultDto.billPaymentAddDtoList',
			totalProperty:'billPaymentVo.billPaymentResultDto.paymentDetialTotal'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});
Ext.define('Foss.payment.BillPaymentDetailGrid', {
	extend:'Ext.grid.Panel',
    frame:true,
    emptyText: pay.payment.i18n('foss.stl.pay.queryBillPayment.BillPaymentDetailGridTitle'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    store:null,
  	defaults:{
  		align:'center'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentNo'),
    	dataIndex:'paymentNo'
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.paymentNoByPaymentDetailGrid'),
    	dataIndex:'payableNo'
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayable.waybillNo'),
    	dataIndex:'waybillNo'		
    },{
		header:pay.payment.i18n('foss.stl.pay.common.sourceBillNo'),
		dataIndex:'sourceBillNo'
	},{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.billType'),
    	dataIndex:'billType',
		renderer:function(value){
			var displayField = null;
			if(displayField==null){
				displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
			}
			if(displayField==value){
				displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_PAYABLE__BILL_TYPE);
			}
			return displayField;
		}	
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.amountPaymentDetail'),
    	dataIndex:'amount',
    	align: 'right',  
		renderer:stl.amountFormat	
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.verifyAmount'),
    	dataIndex:'verifyAmount',
    	align: 'right',  
		renderer:stl.amountFormat	
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.unverifyAmount'),
    	dataIndex:'unverifyAmount',
    	align: 'right',  
		renderer:stl.amountFormat	
    },{
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.currentPaymentAmount'),
    	dataIndex:'currentPaymentAmount',
    	align: 'right',  
		renderer:stl.amountFormat	
    },{
    	header: pay.payment.i18n('foss.stl.pay.queryBillPayment.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return stl.dateFormat(value, 'Y-m-d H:i:s');
			}
		} 				
    },{	
    	header:pay.payment.i18n('foss.stl.pay.queryBillPayment.notes'),
    	dataIndex:'notes',
    	flex:1,
    	editor:{
    		xtype:'textfield'
    	}
    }],
	initComponent:function(){
		var me = this;
		me.bbar = Ext.create('Deppon.StandardPaging', {
            store : me.store,
            pageSize: 10,
			columnWidth:1,
			plugins: Ext.create('Deppon.ux.PageSizePlugin', {
				//设置分页记录最大值，防止输入过大的数值
				maximumSize: 200
			})
        });
		
			
		/*me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',	
		    defaults:{
				margin:'0 0 5 0'
			},	
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:0.65,
				pageSize: 10,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 500
				})
			  },{
				xtype:'container',
				columnWidth:0.25,
				width:10,
				html:'&nbsp;',
				border:0
			 }]
		}];*/
		 me.callParent();
	},
	constructor:function(config){
		var me = this;
		var cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.payment.BillPaymentDetailStore');
		me.callParent([cfg]);
	} 
});

//付款单明细windows
Ext.define('Foss.payment.billPaymentDetailWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.8,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[
	       Ext.create('Foss.payment.BillPaymentDetailForm'),
	       Ext.create('Foss.payment.BillPaymentDetailGrid')]
});



// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_pay-manageBillPayment_content')) {
		return;
	} 
	
	var paymentQueryInfoTab = Ext.create('Foss.payment.PaymentQueryInfoTab');
	var paymentGrid = Ext.create('Foss.payment.PaymentGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-manageBillPayment_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getPaymentQueryInfoTab:function(){
			return paymentQueryInfoTab;
		},
		getPaymentGrid:function(){
			return paymentGrid;
		},
		items: [paymentQueryInfoTab,paymentGrid],
		renderTo: 'T_pay-manageBillPayment-body'
	});
});