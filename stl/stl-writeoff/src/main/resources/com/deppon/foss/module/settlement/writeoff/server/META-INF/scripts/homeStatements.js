/**
 * 对账单明细导出
 */
writeoff.homeStatements.exportExcel = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames;
	//对账单明细
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单明细列表
	var statementEntryGrid = statementEntryWindow.items.items[4];
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.export'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(statementEntryGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = statementEntryGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						arrayColumns.push(dataIndex);
						arrayColumnNames.push(hederName);
					}
				}
		
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				//拼接vo，注入到后台
				searchParams = {
					'vo.homedto.statementBillNo':writeoff.homeStatements.statementBillNo,
				    'vo.arrayColumns':arrayColumns,
				    'vo.arrayColumnNames':arrayColumnNames
				};
				Ext.Ajax.request({
				    url: writeoff.realPath('homeExportXLS.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.exportSuccess'));
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

//开发环境地址是：http://192.168.17.221:8881/fssc
//测试环境UAT地址是：http://192.168.20.251:8080/fssc
//测试环境SIT地址是：http://192.168.20.148:8080/fssc
//http://192.168.11.59:8881/claim/attachment/query.action
writeoff.homeStatements.fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
//附件查询地址
var attachmentQueryUrl = 'http://'+writeoff.homeStatements.fssc+'/fssc/attachment/query.action';
//附件下载地址
var attachmentDownLoadUrl = 'http://'+writeoff.homeStatements.fssc+'/fssc/attachment/download.action';
//附件删除地址
var attachmentDeleteUrl = 'http://'+writeoff.homeStatements.fssc+'/fssc/attachment/delete.action';
//附件上传地址
var attachmentUploadUrl = 'http://'+writeoff.homeStatements.fssc+'/fssc/attachment/upload.action';

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

Ext.define('Foss.homeStatements.ShangChuanWindow', {
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
			writeoff.homeStatements.upfilepanel = this.upfilepanel;
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
writeoff.homeStatements.paymentUpload = function(){
	writeoff.homeStatements.shangChuanWindow =Ext.create('Foss.homeStatements.ShangChuanWindow');
	writeoff.homeStatements.upfilepanel.setUserCode(writeoff.homeStatements.userCode);
	writeoff.homeStatements.upfilepanel.setAttachRelId(writeoff.homeStatements.batchNo);
	writeoff.homeStatements.shangChuanWindow.show();
}

/**
  * 取消操作
  */
writeoff.homeStatements.paymentCancel = function(){
 	var win = this.up('window');
 	Ext.Msg.confirm(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 		writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.isExistPaymentPage'),function(o){
 		//提示是否要删除
 		if(o=='yes'){
 			if(!Ext.isEmpty(win)){
 				win.hide();
 			}
 		}
 	});
}

/**
 * 付款提交操作
 */
writeoff.homeStatements.paymentCommit = function(){
	//获取对账单列表
	var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//付款窗口
	var paymentBillWindow = writeoff.homeStatements.paymentBillWindow;
	//付款数据
	var paymentBillForm = paymentBillWindow.items.items[0];
	//获取form表单数据进行校验
 	var customerCode = paymentBillForm.getForm().findField('homeSupplyCode').getValue();//家装供应商编码
 	var customerName = paymentBillForm.getForm().findField('homeSupplyName').getValue();//家装供应商名称
 	var paymentOrgCode = paymentBillForm.getForm().findField('paymentOrgCode').getValue();//付款部门编号
 	var paymentOrgName = paymentBillForm.getForm().findField('paymentOrgName').getValue();//付款部门名称=创建部门
 	var paymentCompanyCode = paymentBillForm.getForm().findField('paymentCompanyCode').getValue();//所属子公司编码
 	var paymentCompanyName = paymentBillForm.getForm().findField('paymentCompanyName').getValue();//所属子公司名称
 	var statementBillNo = paymentBillForm.getForm().findField('statementBillNo').getValue();//对账单号
 	
 	//部门名称不能为空
 	if(Ext.isEmpty(paymentOrgName)){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.pawriteoff.paymentOrgNameIsNullWarning'));
 		return false;
 	}
 	
 	//部门编码不能为空
 	if(Ext.isEmpty(paymentOrgCode)){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.paymentOrgCodeIsNullWarning'));
 		return false;
 	}
 	
 	//所属子公司名称不能为空
 	if(Ext.isEmpty(paymentCompanyName)){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyNameIsNullWarning'));
 		return false;
 	}
 	
 	//所属子公司编码不能为空
 	if(Ext.isEmpty(paymentCompanyCode)){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyCodeIsNullWarning'));
 		return false;
 	}
 	
 	//获取表单中信息进行校验	
 	var paymentType = paymentBillForm.getForm().findField('paymentType').getValue();//付款类型
 	var amount = paymentBillForm.getForm().findField('amount').getValue();//金额
 	var accountNo = paymentBillForm.getForm().findField('accountNo').getValue();//银行账号
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
 	
 	var addedTaxes=paymentBillForm.getForm().findField('addedTaxes').getValue();//是否增值税专用发票
 	var addedTaxesNo=paymentBillForm.getForm().findField('addedTaxesNo').getValue();//增值税发票号码
 	var noTaxesAmount=paymentBillForm.getForm().findField('noTaxesAmount').getValue();//不含税金额
 	var TaxesAmount=paymentBillForm.getForm().findField('TaxesAmount').getValue();//税额
 	var TaxesPersonNo=paymentBillForm.getForm().findField('TaxesPersonNo').getValue();//纳税人识别编号
 	//是否是增值税
 	if(addedTaxes=="是"){
 		//发票号码判断
 		if(Ext.isEmpty(addedTaxesNo)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 		 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addedTaxesNoIsNullWarning'));
 		 		return false;
 		}
 		//不含税金额判断
 		if(Ext.isEmpty(noTaxesAmount)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 		 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.noTaxesAmountIsNullWarning'));
 		 		return false;
 		}
 		//税额判断
 		if(Ext.isEmpty(TaxesAmount)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 		 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.TaxesAmountIsNullWarning'));
 		 		return false;
 		}
 		//纳税人识别编号判断
 		if(Ext.isEmpty(TaxesPersonNo)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 		 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.TaxesPersonNoIsNullWarning'));
 		 		return false;
 		}
 	}
 	
 	//付款类型判断
 	if(Ext.isEmpty(paymentType)){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.paymentTypeIsNullWarning'));
 		return false;
 	}
 	//金额判断
 	if(Ext.isEmpty(amount) || amount<=0){
 		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.cashToPayAmountLimitWarning'));
 		return false;
 	}
 	//如果为电汇
 	if(paymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
 		if(Ext.isEmpty(accountNo)||Ext.isEmpty(provinceCode)
 			||Ext.isEmpty(cityCode)||Ext.isEmpty(bankHqCode)
 			||Ext.isEmpty(bankBranchCode)||Ext.isEmpty(accountType)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.bankInfoIsNullWarning'));
 			return false;
 		}
 		//校验界面输入条件
 		if(!paymentBillForm.getForm().isValid()){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 					writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.validateFailAlert'));
 			return false;
 		}
 		//判断如果为电汇，收款人不能为空
 		if(Ext.isEmpty(payeeName)||Ext.isEmpty(mobilePhone)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.payeeInfoIsNullWarning'));
 			return false;	
 		}
 		//发票抬头判断
 		if(Ext.isEmpty(invoiceHeadCode)||Ext.isEmpty(invoiceHeadName)){
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceTitleIsNullWarning'));
 			return false;	
 		}
 	}
 	//声明传入action参数
 	var vo = new Object();
 	//此处需要更新一下模型
 	var data = paymentBillForm.getForm().getValues();
 	
 	//获取付款单头信息即界面form表单
 	vo.paymentEntity = data;
 	if(null != writeoff.homeStatements.batchNo){
 		vo.paymentEntity.batchNo=writeoff.homeStatements.batchNo;
 	}
 	vo.paymentEntity.customerCode=customerCode;
 	vo.paymentEntity.customerName=customerName;
 	vo.statementBillNo=statementBillNo;
 	vo.paymentEntity.isTaxinvoice=(addedTaxes=="是")?"Y":"N";
 	//alert(vo.paymentEntity.isTaxinvoice);
 	if(vo.paymentEntity.isTaxinvoice=="Y"){
 		vo.paymentEntity.taxpayerNumber=TaxesPersonNo;
 		vo.paymentEntity.taxfreePrice=noTaxesAmount;
 		vo.paymentEntity.taxPrice=TaxesAmount;
 		vo.paymentEntity.vatInvoice=addedTaxesNo;
 	}
 	//遮罩窗口
 	paymentBillWindow.getLoadMask().show();
 	//提交
 	Ext.Ajax.request({
 		url:ContextPath.STL_WEB + '/pay/dopToPayment.action',
 		actionMethods:'POST',
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
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
 				writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.addPaymentSuccess')
 				+paymentNo+writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.addPaymentSuccesss'));
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
 			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
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
 * 从明细中删除
 * @param {} grid 表单
 * @param {} rowIndex 行标
 * @param {} colIndex
 */
/*writeoff.homeStatements.addPaymentGridEntryRemove = function(grid, rowIndex, colIndex){
	Ext.Msg.confirm(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),writeoff.homeStatements.i18n('foss.stl.writeoff.common.delete'),function(o){
		//提示是否要删除
		if(o=='yes'){
			//获取表单
			var form = grid.up('window').items.items[0].getForm();
			//获取金额组件
			var amountComponent = form.findField('amount');
			//获取要删除行
			var selection = grid.getStore().getAt(rowIndex);
			//获取要删除行的本次付款金额
			var currentPaymentAmount = selection.get('unverifyAmount');
			//删除该条记录
			grid.store.remove(selection);
			//重置金额组件的金额
			amountComponent.setValue(amountComponent.getValue()-currentPaymentAmount);
		}
	});
}*/
/**
 * 付款单明细MODEL
 */
Ext.define('Foss.homeStatements.WoodenStatementDModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'payableNo'//单号
	},{
		name : 'billType'//单据子类型
	}, {
		name : 'amount',//金额
		type : 'double'
	}, {
		name : 'verifyAmount',//已核销金额
		type : 'double'
	}, {
		name : 'unverifyAmount',//未核销金额
		type : 'double'
	}, {
		name : 'unverifyAmount',//本次付款金额
		type : 'double'
	}, {
		name : 'notes'//备注
	}]
});
/**
 * 付款单明细STORE
 */
Ext.define('Foss.homeStatements.homePayDStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatements.WoodenStatementDModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		timeout: 30000,
		url:writeoff.realPath('queryHomeStatementBillNo.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.homedto.homeStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams = {
					'vo.homedto.statementBillNo':writeoff.homeStatements.statementBillNo
				};
			//设置查询条件-
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
//编辑器
/*writeoff.homeStatements.addPaymentEntryGridEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1,
  	isObservable : false
}) ;*/
/**
 * 付款单明细GRID  ----下
 */
Ext.define('Foss.homeStatements.homePaymentFormPanel',{
	extend:'Ext.grid.Panel',
	title: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paymentFormPanel'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//plugins:writeoff.homeStatements.addPaymentEntryGridEditing,
	emptyText:writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'),//查询结果为空!
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatements.homePayDStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
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
    defaults:{
  		align:'center'
  	},
  	columns: [/*{
     	xtype:'actioncolumn',
     	header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.payFu'),
     	width:70,
     	align: 'center',
     	items:[{
 			iconCls : 'deppon_icons_delete',
 			tooltip:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.delete'),
 			handler:function(grid, rowIndex, colIndex){
 				writeoff.homeStatements.addPaymentGridEntryRemove(grid, rowIndex, colIndex);
 			}
     	}]
	},*/{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatementAdd.payableNo'),
		dataIndex:'payableNo',//单据编号
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		dataIndex:'billType',//单据子类型
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "HIR") {
					displayField = "应收对账单";
				} else if (value == "HIP") {
					displayField = "应付对账单";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'amount'//金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount'//已核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'//未核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.payAmount'),
		dataIndex:'unverifyAmount'//本次付款金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatementAdd.notes'),
		dataIndex:'notes'//备注
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.9,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 }]
		}];
		me.callParent();
	}
	/*listeners:{
 		'edit':function(th,e){
 			writeoff.homeStatements.addPaymentGridEidt(th,e);
 		}
 	}*/
});
/**
 * 表格编辑事件处理
 */
/*writeoff.homeStatements.addPaymentGridEidt = function(th,e){
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
		if(Ext.isEmpty(newValue)|| newValue<=0|| newValue>unverifyAmount){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),'本次付款金额必须大于等于0，且不能大于未核销金额！');
			record.set('currentPaymentAmount',e.originalValue);
		//如果为装卸费、服务补救则必须全额付款
		}else if(!Ext.isEmpty(billType) && (pay.SERVICE_FEE==billType
			||pay.COMPENSATION==billType || pay.CLAIM==billType||pay.TRUCK1_FIRST==billType
			||pay.TRUCK1_LAST==billType||pay.TRUCK2_FIRST==billType||pay.TRUCK2_LAST==billType)&& newValue<unverifyAmount){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),'装卸费、服务补救、外请车、临时租车或理赔应付单必须全额付款');
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
}*/
/**
 * 付款信息 --上面
 */
Ext.define('Foss.homeStatements.AddPaymentFormPanel',{
 	extend:'Ext.form.Panel',
 	title:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentBill'),
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
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		name:'homeSupplyName',//家装供应商名称
		labelWidth:100
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		name:'homeSupplyCode',//假装供应商编码
		labelWidth:100
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
 		name:'paymentCompanyName',//所属子公司名称
 		labelWidth:100
 	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
 		name:'paymentCompanyCode',//所属子公司编码
 		labelWidth:100,
 		hidden:true
 	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.statementBillNo'),
 		name:'statementBillNo',//对账单号
 		labelWidth:100,
 		hidden:true
 	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),//剩余未付金额
		name:'amount',
	   id:"amount",
		labelWidth:90,
		xtype:'numberfield'
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),//公司名称
 		name:'paymentOrgName',
 		hidden:true
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),//公司编码
 		name:'paymentOrgCode',
 		hidden:true
 	},{
 		xtype:'combobox',
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.paymentType'),//付款方式不能为空
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
 	},
 	{
 		xtype:'combobox',
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addedTaxes'),//是否增值税专用发票
 		name:'addedTaxes',
     	editable:false,
 		queryModel:'local',
 		displayField:'valueName',
 		valueField:'valueCode',
 		labelWidth:120,
     	columnWidth:.3,
     	store:['是','否'],
     	readOnly:false,
     	value:"是",
     	listeners:{
     		"change":function(ethis, newValue,oldValue, ob ){
     			if(newValue!=null&&newValue!=""){
     				if(newValue=="否"){
         				var addedTaxesNo=Ext.getCmp("addedTaxesNo");
         				var noTaxesAmount=Ext.getCmp("noTaxesAmount");
         				var TaxesAmount=Ext.getCmp("TaxesAmount");
         				var TaxesPersonNo=Ext.getCmp("TaxesPersonNo");
         				addedTaxesNo.allowBlank=true;
         				noTaxesAmount.allowBlank=true;
         				TaxesAmount.allowBlank=true;
         				TaxesPersonNo.allowBlank=true;
         				addedTaxesNo.hide();
         				noTaxesAmount.hide();
         				TaxesAmount.hide();
         				TaxesPersonNo.hide();
         			}else if(newValue=="是"){
         				var addedTaxesNo=Ext.getCmp("addedTaxesNo");
         				var noTaxesAmount=Ext.getCmp("noTaxesAmount");
         				var TaxesAmount=Ext.getCmp("TaxesAmount");
         				var TaxesPersonNo=Ext.getCmp("TaxesPersonNo");
         				addedTaxesNo.allowBlank=false;
         				noTaxesAmount.allowBlank=false;
         				TaxesAmount.allowBlank=false;
         				TaxesPersonNo.allowBlank=false;
         				addedTaxesNo.show();
         				noTaxesAmount.show();
         				TaxesAmount.show();
         				TaxesPersonNo.show();
         			}
     			}
     			
     		}
     		
     	}
 	},
 	{
 		xtype:'textfield',
 		readOnly:false,
 		id:"addedTaxesNo",
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addedTaxesNo'),//增值税发票号码
 		labelWidth:120,
 		name:'addedTaxesNo',
 		allowBlank:false
 	},
 	{
 		xtype:'textfield',
 		readOnly:true,
 		id:"noTaxesAmount",
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.noTaxesAmount'),//不含税金额
 		name:'noTaxesAmount',
 		allowBlank:true,
 		renderer:function(val){
 			return val.toFixed(2);
 		}
 	},
 	{
 		xtype:'textfield',
 		readOnly:false,
 		id:"TaxesAmount",
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.TaxesAmount'),//税额
 		name:'TaxesAmount',
 		regex:/^\d+|\d+.\d{1,2}$/,
 		allowBlank:false,
 		listeners:{"blur":function(the, e, obj){
 			var noTaxesAmount=Ext.getCmp("noTaxesAmount"); 
 			var amount=Ext.getCmp("amount");
 			var m=(amount.getValue()-the.getValue()).toFixed(2);
 			noTaxesAmount.setValue(m);	
 		}}
 	},
 	{
 		xtype:'textfield',
        readOnly:false,
        id:"TaxesPersonNo",
        fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.TaxesPersonNo'),//纳税人识别编号
        regex:/^(\d|[A-Z]|\-){0,30}$/,
        maxLength:30,
        labelWidth:120,
        regexText:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.TaxesPersonNoRegex'),
        name:'TaxesPersonNo',
        allowBlank:false
 	},
 	{
 		xtype:'textfield',
 		readOnly:false,
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhone'),//收款手机
 		regex:/^1[3|4|5|8][0-9]\d{8}$/,
 		regexText:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhoneRegex'),
 		name:'mobilePhone',
 		allowBlank:false
 	},{
 		xtype:'commonsubsidiaryselector',
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceHeadCode'),//发票抬头
 		readOnly:false,
 		name:'invoiceHeadCode',
 		allowBlank:false
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.accountNo'),//银行账号
 		xtype:'commonpayeeinfoselector',
		accountTypes:writeoff.FIN_ACCOUNT_TYPE_PUBLIC+","+writeoff.FIN_ACCOUNT_TYPE_PRIVATE,
 		//operatorId:FossUserContext.getCurrentUserEmp().empCode,
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
 		fieldLabel:'provinceCode',
 		name:'provinceCode',
 		hidden:true
 	},{
 		fieldLabel:'sourceBillType',
 		name:'sourceBillType',
 		hidden:true
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.provinceName'),//开户行省份
 		name:'provinceName'
 	},{
 		fieldLabel:'cityCode',
 		name:'cityCode',
 		hidden:true
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.cityName'),//开户行城市
 		name:'cityName'
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.bankHqName'),//开户银行
 		name:'bankHqName'
 	},{
 		fieldLabel:'bankHqCode',
 		name:'bankHqCode',
 		hidden:true
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchName'),//开户行支行
 		name:'bankBranchName'
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchCode'),//行号
 		name:'bankBranchCode'
 	},{
 		fieldLabel:'accountType',
 		name:'accountType',
 		hidden:true
 	},{
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.accountType'),//账户类型
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
 		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatementAdd.notes'),//备注
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
 			text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.upload'),//上传附件
 			name:'uploadddw',
 			columnWidth:.1,
 			handler:writeoff.homeStatements.paymentUpload
 		},{
 			xtype:'container',
 			border:false,
 			html:'&nbsp;',
 			columnWidth:.6
 		},{
 			text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.commit'),//提交
 			columnWidth:.1,
 			handler:writeoff.homeStatements.paymentCommit
 		},{
 			text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.cancel'),//取消
 			columnWidth:.1,
 			handler:writeoff.homeStatements.paymentCancel
 		}]
 	}]
 });
/**
 * 对账单付款窗体
 */
Ext.define('Foss.homeStatements.paymentBillWindow',{
 	extend:'Ext.window.Window',
 	title:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addPayment'),
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
 				msg:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.savePaymentMask'),
 			    removeMask : true// 完成后移除
 			});
 		}
 		return me.loadMask;
 	},
 	listeners:{
 		'close':function(){
 			writeoff.homeStatements.batchNo = null;
 		}
 	},
 	items:[Ext.create('Foss.homeStatements.AddPaymentFormPanel'),
 	       Ext.create('Foss.homeStatements.homePaymentFormPanel')]//付款单信息窗口
 });
/**
 * 对账单付款操作
 */
writeoff.homeStatements.payment = function(){
	//录入付款单窗口
	if(Ext.isEmpty(writeoff.homeStatements.paymentBillWindow)){
		writeoff.homeStatements.paymentBillWindow = Ext.create('Foss.homeStatements.paymentBillWindow');//830
	}
	var paymentBillWindow = writeoff.homeStatements.paymentBillWindow;
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//应付金额
	var currentPayBillsForm = statementEntryWindow.items.items[2].getForm();
	//付款单FORM
	var paymentBillForm = paymentBillWindow.items.items[0].getForm();
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//对账单单据类型：应收对账单、应付对账单
	var billType = statementEntryForm.findField('billType').getValue();
	//单据类型判断
	if(billType==writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.YSWoodenAccount'));
		return false;
	}
	//判断对账单状态
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
		return false;
	}
	//未付金额
	if(currentPayBillsForm.findField('payUnverifyAmount').getValue() == 0){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.verifyPayment'));
		return false;
	}
	//设置付款界面
	paymentBillForm.reset();
	paymentBillForm.findField('homeSupplyName').setValue(statementEntryForm.findField('homeSupplyName').getValue());//家装供应商名称
	paymentBillForm.findField('homeSupplyCode').setValue(statementEntryForm.findField('homeSupplyCode').getValue());//假装供应商编码
	paymentBillForm.findField('paymentCompanyName').setValue(statementEntryForm.findField('subCompanyName').getValue());//所属子公司名称
	paymentBillForm.findField('paymentCompanyCode').setValue(statementEntryForm.findField('subCompanyCode').getValue());//所属子公司编码
	paymentBillForm.findField('amount').setValue(currentPayBillsForm.findField('payUnverifyAmount').getValue());//金额
	paymentBillForm.findField('sourceBillType').setValue(writeoff.SOURCE_BILL_TYPE__STATEMENT);
	//paymentBillForm.findField('currentAmount').setValue(currentPayBillsForm.findField('periodUnverifyPayAmount').getValue());//金额
	paymentBillForm.findField('statementBillNo').setValue(statementEntryForm.findField('statementBillNo').getValue());//对账单号
	paymentBillForm.findField('paymentOrgCode').setValue(statementEntryForm.findField('orgCode').getValue());//部门编码
	paymentBillForm.findField('paymentOrgName').setValue(statementEntryForm.findField('orgName').getValue());//部门名称
	Ext.Ajax.request({
		url:ContextPath.STL_WEB + '/pay/uploadAppendix.action',
		success:function(response){
			var result = Ext.decode(response.responseText);
			writeoff.homeStatements.batchNo = result.billPaymentVo.paymentEntity.batchNo;
			writeoff.homeStatements.userCode = result.billPaymentVo.paymentEntity.createUserCode;
		}
	});
	//查询明细
	paymentBillWindow.items.items[1].store.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = paymentBillWindow.items.items[1].store.proxy.reader.rawData;
			if(!success){
				var result = Ext.decode(operation.response.responseText);	
				Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
				return false;
			}
			//如果成功显示
			if(success){
				//对结果进行转化
				var result = Ext.decode(operation.response.responseText);
				//判断查询结果
				if(Ext.isEmpty(result.vo.homedto.homeStatementDList) 
						||result.vo.homedto.homeStatementDList.length==0){
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
						writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
					return false;
				}
				//设置应收应付金额
				//writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,selection);
				//设置操作按钮
				writeoff.homeStatements.operateButton();
			}
	    }
	});
	paymentBillWindow.show();
}
/**
 * 还款提交操作
 */
/*writeoff.homeStatements.statementRepaymentComplete = function(repaymentForm){
	var billRepaymentManageVo,
	billStatementToPaymentQueryDto;
	//还款单FORM
	var form=repaymentForm.getForm();
	if(form.isValid()){
		//处理对账单号
		var statementBillNos = [];
		var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
		//对账单基础信息
		var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
		//还款单窗口
		var repaymentBillWindow = writeoff.homeStatements.repaymentBillWindow;
		//还款单FORM
		var repaymentStatementForm = repaymentBillWindow.items.items[0].getForm();
		//对账单单号
		statementBillNos = stl.splitToArray(repaymentStatementForm.findField('statementBillNo').getValue());
		//设置对账单参数
		billStatementToPaymentQueryDto = new Object();
		billStatementToPaymentQueryDto.customerCode = repaymentStatementForm.findField('customerCode').getValue();
		billStatementToPaymentQueryDto.customerName = repaymentStatementForm.findField('customerName').getValue();
		billStatementToPaymentQueryDto.repaymentType = form.findField('repaymentType').getValue();
		billStatementToPaymentQueryDto.repaymentAmount = form.findField('repaymentAmount').getValue();
		billStatementToPaymentQueryDto.description = form.findField('description').getValue();
		billStatementToPaymentQueryDto.remittanceNumber = form.findField('remittanceNumber').getValue();
		billStatementToPaymentQueryDto.remittanceName = form.findField('remittanceName').getValue();
		billStatementToPaymentQueryDto.statementBillNos = statementBillNos;
		billRepaymentManageVo = new Object();
		billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
		//将数据传递给后台
		Ext.Ajax.request({
			url:ContextPath.STL_WEB + '/pay/woodenToRepayment.action',
			actionMethods:'POST',
			jsonData:{	
				'billRepaymentManageVo':billRepaymentManageVo
			},
			success:function(response){
				//获取返回结果
				var result = Ext.decode(response.responseText);
				//获取付款单号
	 			var repaymentNo = result.billRepaymentManageVo.billRepaymentManageDto.repaymentNo;
	 			//遮罩窗口
	 			repaymentForm.up('panel').hide();
				Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
					writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addRepaymentSuccess')
					+repaymentNo);
				//关闭窗口
				repaymentBillWindow.close();
	 			statementEntryWindow.close();
	 			grid.store.load();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.validateFailAlert'));
	}	
}*/
/**
 * 还款对账单信息
 */
/*Ext.define('Foss.homeStatements.RepaymentStatementForm',{
	extend:'Ext.form.Panel',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.statementInfo'),
	frame:true,
	layout: {
        type: 'table',
        columns: 3
    },
    defaultType:'textfield',
    defaults:{
  	    readOnly:true,
  	    labelWidth:70,
  	    width:240
    },
	items:[{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.common.customerName'),
		style:'margin-left:100px',
		name:'customerName',
		colspan:2
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.common.customerCode'),
		name:'customerCode',
		style:'margin-left:100px',
		colspan:2
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),
		name:'statementBillNo',
		style:'margin-left:100px',
		colspan:3
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
		style:'margin-left:100px',
		labelWidth:95,
		xtype:'numberfield',
		name:'currentAmount',
		decimalPrecision:2,
		colspan:2
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.currentRemainAmount'),
		style:'margin-left:100px',
		name:'currentRemainAmount',
		labelWidth:95,
		xtype:'numberfield',
		decimalPrecision:2
	}]
});*/

/**
* 还款单信息
*/
/*Ext.define('Foss.homeStatements.RepaymentForm',{
	extend:'Ext.form.Panel',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentInfo'),
	frame:true,
	layout: {
        type: 'table',
        columns: 3
    },
    defaultType:'textfield',
    defaults:{
  	    labelWidth:70,
  	    width:(stl.SCREENWIDTH*0.7-180)/3
    },
	items:[{
		xtype:'combo',
		name:'repaymentType',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentType'),
		allowBlank : false,
		style:'margin-left:100px',
		store:FossDataDictionary.getDataDictionaryStore(
				settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,
				[writeoff.SETTLEMENT__PAYMENT_TYPE__CASH,
				 writeoff.SETTLEMENT__PAYMENT_TYPE__CARD,
				 writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
				 writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE]),
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		value:writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
		editable:false,
		listeners:{
			'select':function(combo){
				if(combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
						||combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE){
					var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
					this.up('form').getForm().findField('remittanceNumber').enable();
					this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>'+writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.remittanceNumber')+':');
					this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
					this.up('form').getForm().findField('repaymentAmount').disable();
				}else if(combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__CASH
						||combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__CARD){
					var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
					this.up('form').getForm().findField('remittanceNumber').disable();
					this.up('form').getForm().findField('remittanceNumber').labelEl.update('&nbsp;&nbsp;'+writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.remittanceNumber')+':');
					this.up('form').getForm().findField('remittanceNumber').setValue(null);
					this.up('form').getForm().findField('remittanceName').setValue(null);
					this.up('form').getForm().findField('remittanceDate').setValue(null);
					this.up('form').getForm().findField('availableAmount').setValue(null);
					this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
					this.up('form').getForm().findField('repaymentAmount').enable();
				}
			}
		}
	},{
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.remittanceNumber'),
		name:'remittanceNumber',
		listeners:{
			'blur':function(th){
				if(th.getValue()!=null&&th.getValue()!=''){
					var form = this.up('form').getForm()
					var repaymentAmount = form.findField('repaymentAmount').getValue();
					var repaymentType = form.findField('repaymentType').getValue();
			
					//设置参数体	
					var billRepaymentManageVo,
						billStatementToPaymentQueryDto;
					billStatementToPaymentQueryDto = new Object();
					billRepaymentManageVo = new Object();
					billStatementToPaymentQueryDto.remittanceNumber = th.getValue();
					billStatementToPaymentQueryDto.repaymentType = repaymentType;
					billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
					
					//调用后台接口根据输入汇款编号获取汇款人、汇款日期，汇款可用金额、
					Ext.Ajax.request({
						url:ContextPath.STL_WEB + '/pay/queryRemittanceDetail.action',
						jsonData:{
							'billRepaymentManageVo':billRepaymentManageVo
						},
						method:'post',
						success:function(response){
							var result = Ext.decode(response.responseText);	
							var remittanceDate = stl.dateFormat(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceDate,'Y-m-d');
							form.findField('remittanceName').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceName);
							form.findField('remittanceDate').setValue(remittanceDate);
							form.findField('availableAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);							
							//比较还款金额和当前电汇、支票的可用金额，显示两者中较小的
							if(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount!=null
									&&result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount!=''){
								if(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount<repaymentAmount){
									form.findField('repaymentAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
								}
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);	
							Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
							form.findField('remittanceNumber').setValue(null);
						}		
					});
				}
			}
		}
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.remittanceName'),
		name:'remittanceName',
		labelWidth:90,
		disabled:true
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentAmount'),
		style:'margin-left:100px',
		name:'repaymentAmount',
		disabled:true,
		xtype:'numberfield',
		allowBlank : false
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.remittanceDate'),
		name:'remittanceDate',
		format:'Y-m-d',
		xtype:'datefield',
		disabled:true
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.availableAmount'),
		name:'availableAmount',
		disabled:true,
		labelWidth:90
	},{
		xtype:'textareafield',
		name:'description',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.description'),
		autoScroll : true,
		style:'margin-left:100px',
		format:'Y-m-d',
		colspan:3,
		width:(stl.SCREENWIDTH*0.7-180)*2/3
	},{
		xtype:'container',
		colspan:3,
		width:660,
		style:'margin-left:100px',
		defaultType:'button',
		layout:'table',
		items:[{
			xtype:'container',
			html:'&nbsp;',
			width:180
		},{
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.return'),
			width:70,
			cls:'yellow_button',
			handler:function(){
				this.up('form').up('panel').hide();		
			}
		},{
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.repayment'),
			width:70,
			style:'margin-left:20px',
			cls:'yellow_button',
			handler:function(){
				var form = this.up('form');
				var me = this;
				var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
				var repaymentAmount = form.getForm().findField('repaymentAmount').getValue();
				var availableAmount = form.getForm().findField('availableAmount').getValue();
				var repaymentType = form.getForm().findField('repaymentType').getValue();
				var remittanceNumber = form.getForm().findField('remittanceNumber').getValue();
				var repaymentTypeStr =writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.ticket'); 
				//还款金额判断
				if(repaymentAmount==null||repaymentAmount<=0){
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
						writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMin'));
					return false;
				}
				//还款金额和应还金额判断
				if(repaymentAmount>currentRemainAmount){
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
						writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMax'));
					return false;
				}	
				//电汇付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
					repaymentTypeStr=writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.ticket');
					//校验汇款编码
					if(remittanceNumber==null||remittanceNumber==''){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
						return false;
					}
					//校验还款金额和汇款金额
					if(repaymentAmount>availableAmount){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.ticketRepaymentAmountMax'));
						return false;
					}
				}
				//支票付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE){
					repaymentTypeStr=writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.check');
					//校验汇款编码
					if(remittanceNumber==null||remittanceNumber==''){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
						return false;
					}
					//校验还款金额和汇款金额
					if(repaymentAmount>availableAmount){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.checkRepaymentAmountMax'));
						return false;
					}
				}
				//现金付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__CASH){
					repaymentTypeStr=writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.cash');
				}
				//银行卡付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__CARD){
					repaymentTypeStr=writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.bankcard');
				}
				//还款金额和应还金额判断
				if(repaymentAmount<currentRemainAmount){
					Ext.MessageBox.show({
				        title:writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
				        msg:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+
				            repaymentTypeStr+writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentAlert'),
				        buttons:Ext.MessageBox.YESCANCEL,
				        buttonText:{ 
				            yes: writeoff.homeStatements.i18n('foss.stl.writeoff.common.ok'),
				            cancel:writeoff.homeStatements.i18n('foss.stl.writeoff.common.cancel')
				        },
				        fn: function(e){
				        	if(e=='yes'){
			        			//设置该按钮灰掉
								me.disable(false);
								//10秒后自动解除灰掉效果
								setTimeout(function() {
									me.enable(true);
								}, 10000);
								writeoff.homeStatements.statementRepaymentComplete(form);					
				        	}else if(e=='cancel'){
				        		return false;					
				        	}
				        }
					});
				//还款金额和应还金额判断
				}else if(repaymentAmount==currentRemainAmount){
					Ext.MessageBox.show({
				        title:writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
				        msg:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+
				        	repaymentTypeStr+writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.isRepaymentAlert'),
				        buttons:Ext.MessageBox.YESCANCEL,
				        buttonText:{ 
				            yes: writeoff.homeStatements.i18n('foss.stl.writeoff.common.ok'),
				            cancel:writeoff.homeStatements.i18n('foss.stl.writeoff.common.cancel')
				        },
				        fn: function(e){
				        	if(e=='yes'){
			        			//设置该按钮灰掉
								me.disable(false);
								//10秒后自动解除灰掉效果
								setTimeout(function() {
									me.enable(true);
								}, 10000);
								writeoff.homeStatements.statementRepaymentComplete(form);					
				        	}else if(e=='cancel'){
				        		return false;					
				        	}
				        }
					});
				}
			}
		}]
	}]
});*/
/**
 * 对账单还款窗体
 */
/*Ext.define('Foss.homeStatements.RepaymentBillWindow',{
	extend:'Ext.window.Window',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.repaymentBill'),
	width:stl.SCREENWIDTH*0.7,
	modal:true,
	constrainHeader: true,
	closeAction:'hide',
	items:[Ext.create('Foss.homeStatements.RepaymentStatementForm'),//984
		Ext.create('Foss.homeStatements.RepaymentForm')]//1033
});*/
/**
 * 对账单还款操作
 */
/*writeoff.homeStatements.repayment = function(){
	//还款单窗口
	if(Ext.isEmpty(writeoff.homeStatements.repaymentBillWindow)){//
		writeoff.homeStatements.repaymentBillWindow = Ext.create('Foss.homeStatements.RepaymentBillWindow');
	}
	var repaymentBillWindow = writeoff.homeStatements.repaymentBillWindow;
	//对账单基础信息窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//应收信息
	var currentRecBillsForm = statementEntryWindow.items.items[1].getForm();
	//还款对账单单信息
	var repaymentStatementForm = repaymentBillWindow.items.items[0].getForm();
	//还款单信息
	var repaymentForm = repaymentBillWindow.items.items[1].getForm();
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//单据类型
	var billType = statementEntryForm.findField('billType').getValue();
	//单据类型判断
	if(billType==writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.YFHomeAccount'));
		return false;
	}
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmRepayment'));
		return false;
	}
	//未还金额判断
	if(currentRecBillsForm.findField('recUnverifyAmount').getValue() == 0){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.verifyRepayment'));
		return false;
	}
	//设置还款界面
	repaymentStatementForm.reset();
	repaymentStatementForm.findField('customerName').setValue(statementEntryForm.findField('homeSupplyName').getValue());
	repaymentStatementForm.findField('customerCode').setValue(statementEntryForm.findField('homeSupplyCode').getValue());
	repaymentStatementForm.findField('statementBillNo').setValue(statementEntryForm.findField('statementBillNo').getValue());
	repaymentStatementForm.findField('currentAmount').setValue(currentRecBillsForm.findField('periodUnverifyRecAmount').getValue());
	repaymentStatementForm.findField('currentRemainAmount').setValue(currentRecBillsForm.findField('recUnverifyAmount').getValue());
	repaymentForm.reset();
	repaymentForm.findField('repaymentType').enable();
	repaymentForm.findField('remittanceNumber').enable();
	repaymentForm.findField('description').enable();
	repaymentForm.findField('repaymentAmount').setValue(currentRecBillsForm.findField('recUnverifyAmount').getValue());
	repaymentForm.findField('repaymentAmount').disable();
	repaymentBillWindow.show();
}*/
/**
 * 对账单明细MODEL
 */
Ext.define('Foss.homeStatements.WoodenStatementDModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'payableNo'//单号
	},{
		name : 'businessDate'//业务日期
	}, {
		name : 'waybillNo'//运单号
	}, {
		name : 'orgName'//部门名称
	}, {
		name : 'orgCode'//部门编码
	}, {
		name : 'subCompanyName'//所属子公司名称
	}, {
		name : 'subCompanyCode'//所属子公司编码
	}, {
		name : 'billType'//单据子类型
	}, {
		name : 'homeSupplyName'//家装供应商名称
	}, {
		name : 'homeSupplyCode'//家装供应商编码
	}, {
		name : 'amount',//金额
		type : 'double'
	}, {
		name : 'verifyAmount',//已核销金额
		type : 'double'
	}, {
		name : 'unverifyAmount',//未核销金额
		type : 'double'
	}, {
		name : 'notes'//备注
	}]
});
/**
 * 对账单按钮设置
 */
writeoff.homeStatements.operateButton = function(){
	//对账单明细
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单操作按钮
	var operateFrom = statementEntryWindow.items.items[3];
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单单据类型
	var billType = baseForm.findField('billType').getValue();
	//确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N){
		//确认按钮
		operateFrom.items.items[1].show();
		//反确认按钮
		operateFrom.items.items[2].hide();
	}else{
		//确认按钮
		operateFrom.items.items[1].hide();
		//反确认按钮
		operateFrom.items.items[2].show();
	}
	//单据类型判断
	if(billType==writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT){
		//还款按钮
		operateFrom.items.items[3].hide();
		//付款按钮
		operateFrom.items.items[4].show();
	}else{
		//还款按钮
		operateFrom.items.items[3].hide();
		//付款按钮
		operateFrom.items.items[4].show();
	}
	//确认反确认权限
	if(!writeoff.homeStatements.isPermission('/stl-web/writeoff/confirmStatement.action')){
		//确认按钮
		operateFrom.items.items[1].hide();
		//反确认按钮
		operateFrom.items.items[2].hide();
	}
	//还款权限
	/*if(!writeoff.homeStatements.isPermission('/stl-web/writeoff/repayment.action')){
		//还款按钮
		operateFrom.items.items[3].hide();
	}*/
	//付款权限
	if(!writeoff.homeStatements.isPermission('/stl-web/writeoff/batchWriteoffStatement.action')){
		//付款按钮
		operateFrom.items.items[4].hide();
	}
	operateFrom.items.items[5].show();
	operateFrom.items.items[6].show();
}

/**
 * 判断应收应付对账单
 */
writeoff.homeStatements.recFormORpayForm = function(statementEntryWindow,result,selection){
	//家装对账单数据集合
	var homedto = result.vo.homedto;
	//对账单基本数据
	var baseInfo = statementEntryWindow.items.items[0];
	//应收FORM
	var recForm = statementEntryWindow.items.items[1];
	//应付FORM
	var payForm = statementEntryWindow.items.items[2];
	//设置单据类型
	baseInfo.getForm().findField('billType').setValue(homedto.billType);
	//对账单单据类型判断   writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT "YSHA"
	/*if(homedto.billType == writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT){
		//隐藏应付FORM
		payForm.hide();
		//显示应收FORM
		recForm.show();
		//设置发生金额
		recForm.getForm().findField('periodAmount').setValue(homedto.periodAmount);
		//设置应收总额金额
		recForm.getForm().findField('periodRecAmount').setValue(homedto.periodRecAmount);
		//设置应付总额金额
		recForm.getForm().findField('periodPayAmount').setValue(homedto.periodPayAmount);
		//设置应收应付差额金额
		recForm.getForm().findField('periodUnverifyRecAmount').setValue(homedto.unpaidAmount);
		//设置未还金额
		if(!Ext.isEmpty(selection)){
			recForm.getForm().findField('recUnverifyAmount').setValue(selection[0].data.unverifyAmount);
		}else{
			recForm.getForm().findField('recUnverifyAmount').setValue(homedto.unpaidAmount);
		}
	}else{*/
		//隐藏应收FORM
		recForm.hide();
		//显示应付FORM
		payForm.show();
		//设置发生金额
		payForm.getForm().findField('periodAmount').setValue(homedto.periodAmount);
		//设置应收总额金额
		payForm.getForm().findField('periodRecAmount').setValue(homedto.periodRecAmount);
		//设置应付总额金额
		payForm.getForm().findField('periodPayAmount').setValue(homedto.periodPayAmount);
		//设置应收应付差额金额
		payForm.getForm().findField('periodUnverifyPayAmount').setValue(homedto.periodAmount);
		//设置未付金额
		if(!Ext.isEmpty(selection)){
			payForm.getForm().findField('payUnverifyAmount').setValue(selection[0].data.unverifyAmount);
		}else{
			payForm.getForm().findField('payUnverifyAmount').setValue(homedto.periodAmount);
		}
	//}
}
//ddw2 删除所有明细
writeoff.homeStatements.removeAllStatementD = function(){
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[4];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//勾选的单号
	var selections = statementEntryGrid.store.data;
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	Ext.MessageBox.show({
		title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),//删除明细
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//删除明细列表校验
		  	if(selections.length==0){
		  		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
				//设置参数
				var searchParams = {
						'vo.homedto.statementBillNo':statementBillNo
					};
				Ext.Ajax.request({
				    url: writeoff.realPath('delHomeStatement.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置应收应付金额
				    	writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,'');
				    	//设置操作按钮
				    	writeoff.homeStatements.operateButton();
				    	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

//ddw1   勾选删除
writeoff.homeStatements.checkDelWoodenStatementD = function(){
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[4];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//勾选的单号
	var selections = statementEntryGrid.getSelectionModel().getSelection();
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	Ext.MessageBox.show({
		title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),//温馨提示
		msg: writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),//删除明细
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//删除明细列表校验
		  	if(selections.length==0){
		  		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//应收应付单号集合
				var numbers = [];
				//删除对账单明细条数
				var length = selections.length;
				//循环编译单号集合
				for(var i=0;i<length;i++){
					numbers.push(selections[i].get('payableNo'));
				}
		  		//拼接vo，注入到后台
		  		vo = new Object();
		  		homedto = new Object();
		  		homedto.numbers = numbers;
		  		homedto.statementBillNo = statementBillNo;
		  		vo.homedto = homedto;
				Ext.Ajax.request({
				    url: writeoff.realPath('delHomeStatement.action'),
				    method : 'POST',
		  			jsonData:{
		  				'vo':vo
		  			},
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置应收应付金额
				    	writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,'');
				    	//设置操作按钮
				    	writeoff.homeStatements.operateButton();
				    	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * 对账单删除明细提交操作
 */
writeoff.homeStatements.delWoodenStatementDSubmit = function(){
	//删除对账单明细窗口
	var delWoodenStatementDWindow = writeoff.homeStatements.delWoodenStatementDWindow;
	//删除对账单明细FORM
	var delWoodenStatementDForm = delWoodenStatementDWindow.items.items[0].getForm();
	//删除对账单明细GRID
	var delWoodenStatementDGrid = delWoodenStatementDWindow.items.items[1];
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[4];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//删除明细列表校验
		  	if(delWoodenStatementDGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//应收应付单号集合
				var numbers = [];
				//删除对账单明细列表
				var data = delWoodenStatementDGrid.store.data;
				//删除对账单明细条数
				var length = data.items.length;
				//循环编译单号集合
				for(var i=0;i<length;i++){
					numbers.push(data.items[i].get('payableNo'));
				}
				//设置参数
				var searchParams = {
						'vo.homedto.numbers':numbers,
						'vo.homedto.statementBillNo':statementBillNo
					};
				Ext.Ajax.request({
				    url: writeoff.realPath('delHomeStatement.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置应收应付金额
				    	writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,'');
				    	//设置操作按钮
				    	writeoff.homeStatements.operateButton();
				    	//关闭对账单明细窗口
				    	delWoodenStatementDWindow.close();
				    	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));//删除明细成功！
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 按应收应付单号查询要删除的明细
 */
writeoff.homeStatements.statementDelByYFYSNumber = function(){
	//对账单删除明细窗口
	var win = writeoff.homeStatements.delWoodenStatementDWindow;
	var form = win.items.items[0].getForm();
	var grid = win.items.items[1];
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
		 //判断输入单号是否超过100个
		 if(billNumberArray.length>100){
		 	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.homeStatements.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.homeStatements.dNumbers = numbers;
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.vo.homedto.homeStatementDList) 
							||result.vo.homedto.homeStatementDList.length==0){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
}
/**
 * 对账单删除明细STORE
 */
Ext.define('Foss.homeStatements.delWoodenStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatements.WoodenStatementDModel',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryDelHomeByNo.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.homedto.homeStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var numbers = stl.splitToArray(writeoff.homeStatements.dNumbers);
			var searchParams = {
					'vo.homedto.numbers':numbers,
					'vo.homedto.statementBillNo':writeoff.homeStatements.statementBillNo
				};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 对账单删除明细GRID
 */
Ext.define('Foss.homeStatements.delWoodenStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.woodenStatementMessage'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatements.delWoodenStatementDStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
		dataIndex:'payableNo',//单号
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.businessDate'),
		dataIndex:'businessDate',//业务日期
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
		dataIndex:'waybillNo',//运单号
		width:150
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		dataIndex:'orgName'//部门名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		dataIndex:'orgCode'//部门编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		dataIndex:'subCompanyName'//所属子公司名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		dataIndex:'subCompanyCode'//所属子公司编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		dataIndex:'billType',//单据子类型
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "HIR") {
					displayField = "应收对账单";
				} else if (value == "HIP") {
					displayField = "应付对账单";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		dataIndex:'homeSupplyName'//家装供应商名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		dataIndex:'homeSupplyCode'//家装供应商编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'amount'//金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount'//已核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'//未核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.notes'),
		dataIndex:'notes'//备注
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'button',
				text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.delStatementD'),
				columnWidth:.1,
				//hidden:!writeoff.homeStatements.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
				handler:writeoff.homeStatements.delWoodenStatementDSubmit
			}]
		}];
		me.callParent();
	}
});
/**
 * 对账单删除明细FORM
 */
Ext.define('Foss.homeStatements.delWoodenStatementDForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{       		
		xtype:'textareafield',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.number'),
		allowBlank:false,
		columnWidth:.65,
		regex:/^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,
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
				html:'<span style="color:red;">'+writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.queryNosDesc')+'</span>'
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
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.reset'),//重置
			columnWidth:.075,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.5
		},{
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.query'),//查询
			cls:'yellow_button',
			columnWidth:.075,
			handler:writeoff.homeStatements.statementDelByYFYSNumber
		}]
	}]
});
/**
 * 对账单删除明细窗体
 */
Ext.define('Foss.homeStatements.delWoodenStatementDWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[Ext.create('Foss.homeStatements.delWoodenStatementDForm'),
	       Ext.create('Foss.homeStatements.delWoodenStatementDGrid')]
});
/**
 * 对账单删除明细操作
 */
writeoff.homeStatements.delWoodenStatementD = function(){
	//对账单删除窗口
	var win = writeoff.homeStatements.delWoodenStatementDWindow;
	if(Ext.isEmpty(writeoff.homeStatements.delWoodenStatementDWindow)){
		writeoff.homeStatements.delWoodenStatementDWindow = Ext.create('Foss.homeStatements.delWoodenStatementDWindow');
		win = writeoff.homeStatements.delWoodenStatementDWindow;
	}
	//清空表单
	win.items.items[0].getForm().reset();
	//清空列表
	win.items.items[1].store.removeAll();
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	win.show();
}
/**
 * 对账单添加明细提交操作
 */
writeoff.homeStatements.addWoodenStatementDSubmit = function(){
	//添加对账单明细窗口
	var addWoodenStatementDWindow = writeoff.homeStatements.addWoodenStatementDWindow;
	//添加对账单明细FORM
	var addWoodenStatementDForm = addWoodenStatementDWindow.items.items[0].getForm();
	//添加对账单明细GRID
	var addWoodenStatementDGrid = addWoodenStatementDWindow.items.items[1];
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单明细基础信息
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[4];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//获取客户编码
	var homeSupplyCode = statementEntryForm.findField('homeSupplyCode').getValue();
	//添加对账单明细列表
	var datas = addWoodenStatementDGrid.store.data;
	//添加对账单明细条数
	var lengths = datas.items.length;
	//循环集合
	for(var i=0;i<lengths;i++){
		if(datas.items[i].get('homeSupplyCode') != homeSupplyCode){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.noData'));
				return false;
		}
	}
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),//添加明细
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//添加明细列表校验
		  	if(addWoodenStatementDGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	if(e=='yes'){
		  		//判断是按那种查询进行
				var searchParams;
				if(writeoff.homeStatements.queryDTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					//设置参数
					searchParams = {
							'vo.homedto.curDate':writeoff.homeStatements.periodBeginDate,	
							'vo.homedto.preDate':writeoff.homeStatements.periodEndDate,	
							'vo.homedto.homeSupplyCode':writeoff.homeStatements.customerCode,
							'vo.homedto.queryTabType':writeoff.homeStatements.queryDTabType
					};
				}else{
					//应收应付单号集合
					var numbers = [];
					//添加对账单明细列表
					var data = addWoodenStatementDGrid.store.data;
					//添加对账单明细条数
					var length = data.items.length;
					//循环编译单号集合
					for(var i=0;i<length;i++){
						numbers.push(data.items[i].get('payableNo'));
					}
					//设置参数
					searchParams = {
						'vo.homedto.numbers':numbers,
						'vo.homedto.statementBillNo':statementBillNo,
						'vo.homedto.queryTabType':writeoff.homeStatements.queryDTabType
					};
				}
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('addHomeStatement.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	statementEntryGrid.store.load();
				    	//设置应收应付金额
				    	writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,'');
				    	//设置操作按钮
				    	writeoff.homeStatements.operateButton();
				    	//关闭对账单明细窗口
				    	addWoodenStatementDWindow.close();
				    	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.addWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 按应收应付单号查询要添加的明细
 */
writeoff.homeStatements.statementAddByYFYSNumber = function(){
	//添加对账单明细窗口
	var win = writeoff.homeStatements.addWoodenStatementDWindow;
	var form = win.items.items[0].getForm();
	var grid = win.items.items[1];
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
		 //判断输入单号是否超过100个
		 if(billNumberArray.length>100){
		 	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.homeStatements.queryDTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
		writeoff.homeStatements.dNumbers = numbers;
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.vo.homedto.homeStatementDList) 
							||result.vo.homedto.homeStatementDList.length==0){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
}
/**
 * 对账单添加明细STORE
 */
Ext.define('Foss.homeStatements.addWoodenStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatements.WoodenStatementDModel',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryAddHomeByNo.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.homedto.homeStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var numbers = stl.splitToArray(writeoff.homeStatements.dNumbers);
			var searchParams = {
					'vo.homedto.numbers':numbers,
					'vo.homedto.statementBillNo':writeoff.homeStatements.statementBillNo
				};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 对账单添加明细GRID
 */
Ext.define('Foss.homeStatements.addWoodenStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.woodenStatementMessage'),//家装对账单
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'),//查询结果为空!
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatements.addWoodenStatementDStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
		dataIndex:'payableNo',//单号
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.businessDate'),
		dataIndex:'businessDate',//业务日期
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
		dataIndex:'waybillNo',//运单号
		width:150
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		dataIndex:'orgName'//部门名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		dataIndex:'orgCode'//部门编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		dataIndex:'subCompanyName'//所属子公司名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		dataIndex:'subCompanyCode'//所属子公司编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		dataIndex:'billType',//单据子类型
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "HIR") {
					displayField = "应收对账单";
				} else if (value == "HIP") {
					displayField = "应付对账单";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		dataIndex:'homeSupplyName'//家装供应商名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		dataIndex:'homeSupplyCode'//家装供应商编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'amount'//金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount'//已核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'//未核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.notes'),
		dataIndex:'notes'//备注
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'button',
				text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addStatementD'),
				columnWidth:.1,
				//hidden:!writeoff.homeStatements.isPermission('/stl-web/writeoff/addStatementDetail.action'),
				handler:writeoff.homeStatements.addWoodenStatementDSubmit
			}]
		}];
		me.callParent();
	}
});
/**
 * 对账单添加明细FORM
 */
Ext.define('Foss.homeStatements.addWoodenStatementDForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),//按单号查询
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{       		
		xtype:'textareafield',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.number'),//单号
		allowBlank:false,
		columnWidth:.65,
		regex:/^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,
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
				html:'<span style="color:red;">'+writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.queryNosDesc')+'</span>'
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
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.reset'),//重置
			columnWidth:.075,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.5
		},{
			text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.query'),//查询
			cls:'yellow_button',
			columnWidth:.075,
			handler:writeoff.homeStatements.statementAddByYFYSNumber//2135
		}]
	}]
});
/**
 * 对账单添加明细窗体
 */
Ext.define('Foss.homeStatements.addWoodenStatementDWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[Ext.create('Foss.homeStatements.addWoodenStatementDForm'),//2437
	       Ext.create('Foss.homeStatements.addWoodenStatementDGrid')]//2332
});
/**
 * 对账单添加明细操作
 */
writeoff.homeStatements.addWoodenStatementD = function(){
	//添加对账单明细窗口
	var win = writeoff.homeStatements.addWoodenStatementDWindow;
	if(Ext.isEmpty(writeoff.homeStatements.addWoodenStatementDWindow)){
		writeoff.homeStatements.addWoodenStatementDWindow = Ext.create('Foss.homeStatements.addWoodenStatementDWindow');
		win = writeoff.homeStatements.addWoodenStatementDWindow;
	}
	//清空表单
	win.items.items[0].getForm().reset();
	//清空列表
	win.items.items[1].store.removeAll();
	//对账单明细窗口
	var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmAddWooden'));
		return false;
	}
	win.show();
}
/**
 * 对账单确认
 */
writeoff.homeStatements.statementConfirm = function(){
	//选择对账单数据
	var selection=writeoff.homeStatements.selection;
	//对账单明细窗口
	var win = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var baseForm = win.items.items[0].getForm();
	//对账单单号
	var statementBillNo = baseForm.findField('statementBillNo').getValue();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
		return false;
	}
	//对账单单号判断
	if(statementBillNo==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
		return false;
	}
	//对账单金额判断
	if(selection[0].data.periodAmount != selection[0].data.unverifyAmount){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
		return false;
	}
	//拼接vo，注入到后台
	vo = new Object();
	homedto = new Object();
	homedto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
	homedto.statementBillNo = statementBillNo;
	vo.homedto = homedto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmHomeStatement.action'),
		jsonData:{
			'vo':vo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
			//刷新对账单列表
			grid.store.load();
			//设置对账单确认状态
			baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_Y);
			//设置对账单确认日期
			baseForm.findField('confirmTime').setValue(new Date());
			//设置操作按钮
			writeoff.homeStatements.operateButton();
			Ext.ux.Toast.msg(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
				writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}

/**
 * 对账单反确认
 */
writeoff.homeStatements.statementUnConfirm = function(){
	//选择对账单数据
	var selection=writeoff.homeStatements.selection;
	//对账单明细窗口
	var win = writeoff.homeStatements.statementEntryWindow;
	//对账单基础信息
	var baseForm = win.items.items[0].getForm();
	//对账单单号
	var statementBillNo = baseForm.findField('statementBillNo').getValue();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N||confirmStatus==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
		return false;
	}
	//对账单单号判断
	if(statementBillNo==''){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
		return false;
	}
	//对账单金额判断
	if(selection[0].data.periodAmount != selection[0].data.unverifyAmount){
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
		return false;
	}
	//拼接vo，注入到后台
	vo = new Object();
	homedto = new Object();
	homedto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_N;
	homedto.statementBillNo = statementBillNo;
	vo.homedto = homedto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmHomeStatement.action'),
		jsonData:{
			'vo':vo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
			//刷新对账单列表
			grid.store.load();
			//设置对账单确认状态
			baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_N);
			//设置对账单确认日期
			baseForm.findField('confirmTime').setValue(null);
			//设置操作按钮
			writeoff.homeStatements.operateButton();
			Ext.ux.Toast.msg(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'), 
				writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}
/**
 * 对账单明细STORE
 */
Ext.define('Foss.homeStatements.woodenStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatements.WoodenStatementDModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		timeout: 30000,
		url:writeoff.realPath('queryHomeStatementBillNo.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.homedto.homeStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams = {
					'vo.homedto.statementBillNo':writeoff.homeStatements.statementBillNo
				};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 对账单明细GRID  ----下
 */
Ext.define('Foss.homeStatements.woodenStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.homeStatements.i18n('foss.stl.writeoff.statementCommon.statementEntry'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'),//查询结果为空!
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatements.woodenStatementDStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
		dataIndex:'payableNo',//单号
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.businessDate'),
		dataIndex:'businessDate',//业务日期
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
		dataIndex:'waybillNo',//运单号
		width:150
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		dataIndex:'orgName'//部门名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		dataIndex:'orgCode'//部门编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		dataIndex:'subCompanyName'//所属子公司名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		dataIndex:'subCompanyCode'//所属子公司编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		dataIndex:'billType',//单据子类型
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "HIR") {
					displayField = "应收对账单";
				} else if (value == "HIP") {
					displayField = "应付对账单";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		dataIndex:'homeSupplyName'//家装供应商名称
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		dataIndex:'homeSupplyCode'//家装供应商编码
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'amount'//金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount'//已核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'//未核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatementAdd.notes'),
		dataIndex:'notes'//备注
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'button',
				text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),//添加明细
				columnWidth:.1,
				//hidden:!writeoff.homeStatements.isPermission('/stl-web/writeoff/addStatementDetail.action'),
				handler:writeoff.homeStatements.addWoodenStatementD
			},{
				xtype:'button',
				text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),//删除明细
				columnWidth:.1,
				//hidden:!writeoff.homeStatements.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
				handler:writeoff.homeStatements.delWoodenStatementD
			},{
				xtype:'button',
				text:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.checkDelWoodenStatementD'),//勾选删除
				columnWidth:.1,
				//hidden:!writeoff.homeStatements.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
				handler:writeoff.homeStatements.checkDelWoodenStatementD
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
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.9,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 }]
		}];
		me.callParent();
	}
});
/**
 * 对账单明细操作按钮
 */
Ext.define('Foss.homeStatements.OperateButtonPanel',{
	extend:'Ext.panel.Panel',
	layout:'column',
	defaultType:'button',
	defaults:{
		columnWidth:.1
	},
	items:[{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		columnWidth:.25
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.confirm'),//确认
		hidden:true,
		handler:writeoff.homeStatements.statementConfirm//2449
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.unConfirm'),//反确认
		hidden:true,
		handler:writeoff.homeStatements.statementUnConfirm//2514
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.repayment'),//还款
		hidden:true,
		handler:writeoff.homeStatements.repayment//1323
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.payment'),//付款
		hidden:true,
		handler:writeoff.homeStatements.payment
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.exportExcel'),//导出明细EXCEL
		hidden:true,
		handler:writeoff.homeStatements.exportExcel//4
	},{
		text:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.removeAllStatementD'),//删除所有明细
		hidden:true,
		handler:writeoff.homeStatements.removeAllStatementD//1529
	}]
});
/**
 * 对账单明细应收FROM
 */
Ext.define('Foss.homeStatements.CurrentRecBillsForm',{
	extend:'Ext.form.Panel',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodCurrentBills'),//本期账单
	layout:'column',
	frame:true,
	hidden:true,
	defaultType:'displayfield',
	defaults:{
		labelWidth:5,
		readOnly:true
	},
	items:[{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodAmount'),//本期发生金额
		columnWidth:.15,
		style:'margin-left:30px;margin-top:10px;'
	},{
		value:'=',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),//本期应收
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),//本期应付
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		 xtype: 'component',
		 border:true,
		 style:'margin-left:30px;margin-top:10px;',
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		name:'periodAmount',//发生金额
		columnWidth:.2,
		xtype:'numberfield',
		style:'margin-left:30px;'
	},{
		name:'periodRecAmount',//本期应收
		columnWidth:.2,
		xtype:'numberfield'
	},{
		name:'periodPayAmount',//本期应付
		columnWidth:.2,
		xtype:'numberfield'
	},{
		 xtype: 'component',
		 border:true,
		 style:'margin-left:30px;margin-top:10px;',
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		xtype:'numberfield',
		name:'periodUnverifyRecAmount',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.Ar'),//应收
		labelWidth:40,
		style:'margin-left:20px;margin-top:10px;',
		columnWidth:.19
	},{
		xtype:'numberfield',
		name:'recUnverifyAmount',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.RecUnverifyAmount'),//未还金额
		labelWidth:75,
		style:'margin-top:10px;',
		columnWidth:.19
	}]
});
/**
 * 对账单明细应付FROM
 */
Ext.define('Foss.homeStatements.CurrentPayBillsForm',{
	extend:'Ext.form.Panel',
	title:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodCurrentBills'),//本期账单
	layout:'column',
	frame:true,
	hidden:true,
	defaultType:'displayfield',
	defaults:{
		labelWidth:5,
		readOnly:true
	},
	items:[{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodAmount'),//本期发生金额
		columnWidth:.15,
		style:'margin-left:30px;margin-top:10px;'
	},{
		value:'=',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),//本期应付
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),//本期应收
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		 xtype: 'component',
		 border:true,
		 style:'margin-left:30px;margin-top:10px;',
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		name:'periodAmount',
		columnWidth:.2,
		xtype:'numberfield',
		style:'margin-left:30px;'
	},{
		name:'periodPayAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		name:'periodRecAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		 xtype: 'component',
		 border:true,
		 style:'margin-left:30px;margin-top:10px;',
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		xtype:'numberfield',
		name:'periodUnverifyPayAmount',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.Ap'),//应付
		labelWidth:40,
		style:'margin-left:20px;margin-top:10px;',
		columnWidth:.19
	},{
		xtype:'numberfield',
		name:'payUnverifyAmount',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementEdit.PayUnverifyAmount'),//未付金额
		labelWidth:75,
		style:'margin-top:10px;',
		columnWidth:.19
	}]
});
/**
 * 对账单单据类型
 */
Ext.define('Foss.homeStatements.BillTypeStore',{
	extend:'Ext.data.Store',
	fields:['billTypeCode','billTypeName'],
	data:{
		'items':[
			{billTypeCode:'YFHA',billTypeName:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.YFHA')},
			{billTypeCode:'YSHA',billTypeName:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.YSHA')}
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
 * 对账单基础信息--上
 */
Ext.define('Foss.homeStatements.BaseInfo',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:60,
		margin:'5 10 5 30',
		readOnly:true
	},
	items:[{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		name:'billType',//单据类型
		xtype:'combo',
		labelWidth:75,
		columnWidth:.24,
		store:Ext.create('Foss.homeStatements.BillTypeStore'), 
		queryModel:'local',
		displayField:'billTypeName',
		valueField:'billTypeCode'
	},{
		xtype:'datefield',
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.createTime'),
		name:'createTime',//创建时间
		labelWidth:75,
		format:'Y-m-d H:i:s',
		columnWidth:.24,
		value:new Date()
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		name:'orgName',//部门名称
		hidden:true,
		labelWidth:75,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		name:'orgCode',//部门编码
		hidden:true,
		labelWidth:75,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		name:'homeSupplyCode',//家装供应商编码
		labelWidth:100,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		name:'homeSupplyName',//家装供应商名称
		labelWidth:100,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.periodBeginDate'),
		name:'businessDate',//开始时间
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		name:'subCompanyName',//所属子公司名称
		labelWidth:100,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		name:'subCompanyCode',//所属子公司编码
		hidden:true,
		labelWidth:100,
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmStatus'),
		name:'confirmStatus',//确认状态
		labelWidth:75,
		xtype:'combo',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		columnWidth:.24,
		value:0
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmTime'),
		name:'confirmTime',//确认时间
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H：i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.periodEndDate'),
		name:'periodEndDate',//结束时间
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24,
		value:new Date()
	},{
		fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.statementBillNo'),
		name:'statementBillNo',//对账单号
		labelWidth:100,
		columnWidth:.24
	}]
});
/**
 * 对账单明细窗体
 */
Ext.define('Foss.homeStatements.StatementEntryWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[
	       Ext.create('Foss.homeStatements.BaseInfo'),//对账单基础信息   2993
	       Ext.create('Foss.homeStatements.CurrentRecBillsForm'),//对账单明细应收FROM 2815
	       Ext.create('Foss.homeStatements.CurrentPayBillsForm'),//对账单明细应付FROM 2894
	       Ext.create('Foss.homeStatements.OperateButtonPanel'),//对账单明细操作按钮 2774
		   Ext.create('Foss.homeStatements.woodenStatementDGrid')//对账单明细GRID  2643
	]
});
/**
 * 对账单MODEL
 */
Ext.define('Foss.homeStatements.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'statementBillNo'//家装对账单号
	}, {
		name : 'subCompanyName'//所属子公司名称
	}, {
		name : 'subCompanyCode'//所属子公司编码
	}, {
		name : 'orgName'//部门名称
	}, {
		name : 'orgCode'//部门编码
	}, {
		name : 'homeSupplyName'//家装供应商名称
	}, {
		name : 'homeSupplyCode'//家装供应商编码
	}, {
		name : 'billType'//单据子类型
	}, {
		name : 'periodAmount',//金额
		type : 'double'
	}, {
		name : 'verifyAmount',//已核销金额
		type : 'double'
	}, {
		name : 'unverifyAmount',//未核销金额
		type : 'double'
	}, {
		name : 'createUserName'
	}, {
		name : 'createUserCode'
	}, {
		name : 'businessDate',
		type : 'Date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'createTime',
		type : 'Date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'modifyUserName'
	}, {
		name : 'modifyUserCode'
	}, {
		name : 'confirmUserName'
	}, {
		name : 'confirmUserCode'
	}, {
		name : 'confirmTime',
		type : 'Date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'modifyTime',
		type : 'Date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'confirmStatus'
	}, {
		name : 'versionNo'
	} ]
});
/**
 * 按客户查询对账单
 */
writeoff.homeStatements.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//开始时间
		writeoff.homeStatements.periodBeginDate = form.findField('curDate').getValue();
		//结束时间
		writeoff.homeStatements.periodEndDate = form.findField('preDate').getValue();
		//客户编码
		writeoff.homeStatements.customerCode = form.findField('homeSupplyCode').getValue();
		//所属子公司编码
		writeoff.homeStatements.subCompanyCode = form.findField('subCompanyCode').getValue();
		//确认状态
		writeoff.homeStatements.confirmStatus = form.findField('confirmStatus').getValue();
		//结清状态
		writeoff.homeStatements.settleStatus = form.findField('settled').getValue();
		//查询类型
		writeoff.homeStatements.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		//开始日期
		var periodBeginDate = writeoff.homeStatements.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.homeStatements.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatements.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取grid
		var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.vo.homedto.homeList) 
							||result.vo.homedto.homeList.length==0){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}
/**
 * 按单号查询对账单
 */
writeoff.homeStatements.statementSelectByNumber = function(){		
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
		 	Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.homeStatements.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.homeStatements.queryTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
		writeoff.homeStatements.numbers = numbers;
		var grid = Ext.getCmp('T_writeoff-homeStatements_content').getGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.vo.homedto.homeList) 
							||result.vo.homedto.homeList.length==0){
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatements.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};
/**
 * 对账单STORE
 */
Ext.define('Foss.homeStatements.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatements.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryHome.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'vo.homedto.homeList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.homeStatements.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'vo.homedto.curDate':writeoff.homeStatements.periodBeginDate,	
					'vo.homedto.preDate':writeoff.homeStatements.periodEndDate,	
					'vo.homedto.homeSupplyCode':writeoff.homeStatements.customerCode,
					'vo.homedto.subCompanyCode':writeoff.homeStatements.subCompanyCode,
					'vo.homedto.confirmStatus':writeoff.homeStatements.confirmStatus,
					'vo.homedto.settled':writeoff.homeStatements.settleStatus,
					'vo.homedto.queryTabType':writeoff.homeStatements.queryTabType
				};
			}else{
				var numbers = stl.splitToArray(writeoff.homeStatements.numbers);
				searchParams = {
					'vo.homedto.numbers':numbers,
					'vo.homedto.queryTabType':writeoff.homeStatements.queryTabType
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

/**
 * 对账单GRID
 */
Ext.define('Foss.homeStatements.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.woodenStatementMessage'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatements.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	listeners : {
		'itemdblclick' : function(th, record) {
			//对账单明细
			var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
			//对账单单号
			writeoff.homeStatements.statementBillNo = record.data.statementBillNo;
			//获取选择的对账单数据
			var selection=Ext.getCmp('T_writeoff-homeStatements_content').getGrid().getSelectionModel().getSelection();
			var me = this;
			var model = new Foss.homeStatements.GridModel(selection[0].data);
			writeoff.homeStatements.selection = selection;
			//对账单明细窗口
			var statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
			if(Ext.isEmpty(writeoff.homeStatements.statementEntryWindow)){
				writeoff.homeStatements.statementEntryWindow = Ext.create('Foss.homeStatements.StatementEntryWindow');
				statementEntryWindow = writeoff.homeStatements.statementEntryWindow;
			}
			//隐藏应收FORM
			statementEntryWindow.items.items[1].hide();
			//隐藏应付FORM
			statementEntryWindow.items.items[2].hide();
			//对账单操作按钮
			var operateFrom = statementEntryWindow.items.items[3];
			//确认按钮
			operateFrom.items.items[1].hide();
			//反确认按钮
			operateFrom.items.items[2].hide();
			//还款按钮
			operateFrom.items.items[3].hide();
			//付款按钮
			operateFrom.items.items[4].hide();
			//导出按钮
			operateFrom.items.items[5].hide();
			//删除按钮
			operateFrom.items.items[6].hide();
			//设置对账单基础信息
			statementEntryWindow.items.items[0].loadRecord(model);
			statementEntryWindow.show();
			//清楚--下
			statementEntryWindow.items.items[4].store.load();
			//查询明细
			statementEntryWindow.items.items[4].store.loadPage(1,{
				callback: function(records, operation, success) {
					var rawData = statementEntryWindow.items.items[4].store.proxy.reader.rawData;
					if(!success){
						var result = Ext.decode(operation.response.responseText);	
						Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),result.message);
						return false;
					}
					//如果成功显示
					if(success){
						//对结果进行转化
						var result = Ext.decode(operation.response.responseText);
						//判断查询结果
						if(Ext.isEmpty(result.vo.homedto.homeStatementDList) 
								||result.vo.homedto.homeStatementDList.length==0){
							Ext.Msg.alert(writeoff.homeStatements.i18n('foss.stl.writeoff.common.alert'),
								writeoff.homeStatements.i18n('foss.stl.writeoff.common.noResult'));
							return false;
						}
						//设置应收应付金额
						writeoff.homeStatements.recFormORpayForm(statementEntryWindow,result,selection);
						//设置操作按钮
						writeoff.homeStatements.operateButton();
					}
			    }
			});
		}
	},
  	columns: [{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.statementBillNo'),
		dataIndex:'statementBillNo',//对账单号
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		dataIndex:'subCompanyName',//所属子公司名称
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		dataIndex:'subCompanyCode',//所属子公司编码
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		dataIndex:'orgName',//部门名称
		hidden:true,
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		dataIndex:'orgCode',//部门编码
		hidden:true,
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		dataIndex:'homeSupplyName',//家装代理名称
		width:120
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		dataIndex:'homeSupplyCode',//家装代理编码
		width:150
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.billType'),
		dataIndex:'billType',//单据类型
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "YSHA") {
					displayField = "应收对账单";
				} else if (value == "YFHA") {
					displayField = "应付对账单";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'periodAmount'//金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount',//已核销金额
		renderer:function(value,m,record){
			//声明默认值为0
			value = 0;
			//获取本期发生额
			var periodAmount = record.get('periodAmount');
			//获取本期未还金额
			var unpaidAmount = record.get('unverifyAmount');
			//如果本期发生金额小于等于0，则本期已还金额默认为0，反之为本期发生额-本期未还金额
			if(!Ext.isEmpty(periodAmount) && periodAmount>0){
				value = stl.subtrAmountPoint(periodAmount,unpaidAmount);
			}
			//返回
			return value;
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'//未核销金额
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.createUserName'),
		dataIndex:'createUserName'
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.createUserCode'),
		dataIndex:'createUserCode'
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.businessDate'),
		dataIndex:'businessDate',//业务日期
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.createTime'),
		dataIndex:'createTime',//
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.modifyUserName'),
		dataIndex:'modifyUserName'
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.modifyUserCode'),
		dataIndex:'modifyUserCode'
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmUserName'),
		dataIndex:'confirmUserName'//确认人名字
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmUserCode'),
		dataIndex:'confirmUserCode'//确认人工号
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmTime'),
		dataIndex:'confirmTime',//确认时间
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.modifyTime'),
		dataIndex:'modifyTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmStatus'),
		dataIndex:'confirmStatus',//确认状态
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
    		return displayField;
		}
		
	},{
		header:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.versionNo'),
		dataIndex:'versionNo'//版本号
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.9,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 }]
		}];
		me.callParent();
	}
});

/**
 * 结账状态store
 */
Ext.define('Foss.statementbill.SettleStatusStore',{
	extend:'Ext.data.Store',
	fields:['name','value'],
	data:{
		'items':[
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.all'),value:''},
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.settled'),value:writeoff.SETTLESTATUS_Y},
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.unSettle'),value:writeoff.SETTLESTATUS_N}
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
 * 确认状态store
 */
Ext.define('Foss.homeStatements.bill.SettleStatusStore',{
	extend:'Ext.data.Store',
	fields:['name','value'],
	data:{
		'items':[
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.all'),value:''},
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.settled'),value:writeoff.SETTLESTATUS_Y},
				{name:writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.unSettle'),value:writeoff.SETTLESTATUS_N}
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
 * 对账单查询页签
 */
Ext.define('Foss.homeStatements.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.homeStatements.i18n('foss.stl.writeoff.common.woodenStatementByCustomer'),
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
		    	xtype:'datefield',//开始时间
		    	fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'curDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
		    },{
		    	xtype:'datefield',//结束时间
		    	fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.periodEndDate'),
		    	allowBlank:false,
		    	name:'preDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),0)
		    },{
		    	xtype:'supplierSelector',//客户信息
		    	allowBlank:false,
		    	listWidth:300,
		    	name:'homeSupplyCode',
		    	active:'Y',
		    	fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.common.customerDetial'),
		    	columnWidth:.3
		    },{
		   	 	xtype: 'commonsubsidiaryselector',//所属子公司名称
		   	 	allowBlank:true,
		   	 	listWidth:320,
				name:'subCompanyCode',
				active:'Y',
		        fieldLabel: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
				columnWidth:.3
		    },{
				xtype: 'combobox',//确认状态
				name:'confirmStatus',
		        fieldLabel: writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.confirmStatus'),
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS,null,{
					 'valueCode': '',
               		 'valueName': writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				xtype: 'combobox',//结账状态
				name:'settled',
		        fieldLabel: writeoff.homeStatements.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
				store:Ext.create('Foss.statementbill.SettleStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:'',
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
					text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.reset'),//重置
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.74,
					html: '&nbsp;'
				},{
					text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.query'),//查询
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.homeStatements.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.homeStatements.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
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
				fieldLabel:writeoff.homeStatements.i18n('foss.stl.writeoff.statementAdd.number'),
				allowBlank:false,
				columnWidth:.65,
				regex:/^((DZ)?[0-9]{7,10}[;,])*((DZ)?[0-9]{7,10}[;,]?)$/i,
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
						html:'<span style="color:red;">'+writeoff.homeStatements.i18n('foss.stl.writeoff.homeStatements.queryByStatementBillNo')+'</span>'
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
					text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.reset'),//重置
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.homeStatements.i18n('foss.stl.writeoff.common.query'),//查询
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.homeStatements.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	//查询表单
	var queryTab = Ext.create('Foss.homeStatements.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.homeStatements.Grid');
	//创建明细窗口
	if(Ext.isEmpty(writeoff.homeStatements.statementEntryWindow)){
		writeoff.homeStatements.statementEntryWindow = Ext.create('Foss.homeStatements.StatementEntryWindow');
	}
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-homeStatements_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getGrid:function(){
			return grid;
		},
		getTab:function(){
			return queryTab;
		},
		items: [queryTab,grid],
		renderTo: 'T_writeoff-homeStatements-body'
	});
});