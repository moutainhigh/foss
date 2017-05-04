/**
 * 对账单明细导出
 */
writeoff.woodenStatementEdit.exportExcel = function () {
    var columns,
        arrayColumns,
        arrayColumnNames;
    //对账单明细
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单明细列表
    var statementEntryGrid = statementEntryWindow.items.items[4];
    var me = this;
    Ext.MessageBox.show({
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.export'),
        buttons: Ext.MessageBox.YESNO,
        fn: function (e) {
            //如果本期数据为空，则提示不能导出excel
            if (statementEntryGrid.store.data.length == 0) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
                return false;
            }

            if (e == 'yes') {
                //转化列头和列明
                columns = statementEntryGrid.columns;
                arrayColumns = [];
                arrayColumnNames = [];
                //将前台对应列头传入到后台去
                for (var i = 1; i < columns.length; i++) {
                    if (columns[i].isHidden() == false) {
                        var hederName = columns[i].text;
                        var dataIndex = columns[i].dataIndex;
                        arrayColumns.push(dataIndex);
                        arrayColumnNames.push(hederName);
                    }
                }

                if (!Ext.fly('downloadAttachFileForm')) {
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }
                //拼接vo，注入到后台
                searchParams = {
                    'woodenStatementVo.woodenStatementDto.statementBillNo': writeoff.woodenStatementEdit.statementBillNo,
                    'woodenStatementVo.arrayColumns': arrayColumns,
                    'woodenStatementVo.arrayColumnNames': arrayColumnNames
                };
                Ext.Ajax.request({
                    url: writeoff.realPath('woodenExportXLS.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method: 'POST',
                    params: searchParams,
                    isUpload: true,
                    success: function (response, options) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.exportSuccess'));
                    }
                });
            } else {
                return false;
            }
        }
    });
}

//开发环境地址是：http://192.168.17.221:8881/fssc
//测试环境UAT地址是：http://192.168.20.251:8080/fssc
//测试环境SIT地址是：http://192.168.20.148:8080/fssc
//http://192.168.11.59:8881/claim/attachment/query.action
writeoff.woodenStatementEdit.fssc = FossDataDictionary.getDataByTermsCode('SETTLEMENT__FSSC_TYPE')[0].valueName;
//附件查询地址
var attachmentQueryUrl = 'http://' + writeoff.woodenStatementEdit.fssc + '/fssc/attachment/query.action';
//附件下载地址
var attachmentDownLoadUrl = 'http://' + writeoff.woodenStatementEdit.fssc + '/fssc/attachment/download.action';
//附件删除地址
var attachmentDeleteUrl = 'http://' + writeoff.woodenStatementEdit.fssc + '/fssc/attachment/delete.action';
//附件上传地址
var attachmentUploadUrl = 'http://' + writeoff.woodenStatementEdit.fssc + '/fssc/attachment/upload.action';

Ext.define('Fssc.common.AttachMentResource', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'attachguid',
        type: 'string'
    }, {
        name: 'attachname',
        type: 'string'
    }, {
        name: 'attachsize',
        type: 'string'
    }, {
        name: 'attachpath',
        type: 'string'
    }, {
        name: 'id',
        type: 'string'
    }]
});

Ext.define('Fssc.common.upFileStore', {
    extend: 'Ext.data.Store',
    model: 'Fssc.common.AttachMentResource',
    proxy: {
        type: 'ajax',
        url: attachmentQueryUrl,
        reader: {
            type: 'json',
            root: 'resourceList',
            totalProperty: 'totalCount'
        },
        callbackKey: 'queryAttachment',
        // 设置请求方式
        actionMethods: 'POST'
    }
});
function downLoadFile(filePath, fileName) {
    var url = attachmentDownLoadUrl + '?resource.attachpath=' + filePath + '&resource.attachname=' + fileName;
    window.location.href = url;
}

function deleteFile(fileId, attachguid, upfilegridstore) {
    Ext.Ajax.request({
        url: attachmentDeleteUrl,
        params: {
            'queryData.fileId': fileId,
            'queryData.attachguid': attachguid
        },
        success: function (response, options) {
            var data = Ext.decode(response.responseText);
            if (!data.success) {
                Ext.Msg.alert('提示', data.message);
                return;
            }
            if (upfilegridstore) {
                upfilegridstore.loadPage(1);
            }
        },
        failure: function () {
            Ext.Msg.alert('提示', '服务器异常; 请稍后再试');
        }
    });
}

/**
 * 附件上传主界面
 */
Ext.define('Fssc.common.upFileGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.upfilegrid',
    height: '100%',
    attachRelId: null,
    setAutoLoadStore: function (flag) {
        if (flag) {
            this.getPagingToolbar().moveFirst();
        }
    },
    setAttachRelId: function (attachRelId) {
        this.attachRelId = attachRelId;
    },
    getAttachRelId: function () {
        return this.attachRelId;
    },
    downLoadFile: function (value, metaData, record) {
        return Ext.String.format('<a href=\"javascript:downLoadFile(\'{0}\'' + ',' + '\'{1}\'' + ')\">{2}</a>', record.data.attachpath, record.data.attachname, record.data.attachname);
    },
    renderDelFile: function (value, metaData, record) {
        var href2 = Ext.String.format('[<a href=\"javascript:deleteFile(\'{0}\'' + ',\'{1}\')\">{2}</a>]', record.data.id, record.data.attachguid, '删除');
        return href2;
    },
    pagingToolbar: null,
    getPagingToolbar: function () {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store: this.store,
                pageSize: 5,
                columnWidth: 1,
                plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    //设置分页记录最大值，防止输入过大的数值
                    maximumSize: 5
                })
            });
        }
        return this.pagingToolbar;
    },
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        this.store = Ext.create('Fssc.common.upFileStore', {
            pageSize: 10,
            listeners: {
                beforeload: function (store, operation, eOpts) {
                    Ext.apply(operation, {
                        params: {
                            'queryData.attachguid': me.attachRelId
                        }
                    });
                }
            }
        });
        me.columns = [{
            xtype: 'rownumberer',
            text: '序号',
            width: 50
        }, {
            text: '附件名称',
            dataIndex: 'attachguid',
            width: 300,
            flex: 1,
            renderer: me.downLoadFile
        }, {
            text: '附件大小',
            dataIndex: 'attachsize',
            width: 100
        }, {
            xtype: 'actioncolumn',
            text: '操作',
            iconCls: 'deppon_icons_delete',
            width: 100,
            handler: function (g, r, c) {
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

Ext.core.Element.prototype.unselectable = function () {
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
    extend: 'Ext.form.Panel',
    alias: 'widget.upfileform',
    standardSubmit: true,
    attachRelId: null,
    userCode: null,
    upLoadGrid: null,
    bodyCls: 'tbarformcls',
    setUserCode: function (userCode) {
        this.userCode = userCode;
    },
    getUserCode: function () {
        return this.userCode;
    },
    setAttachRelId: function (attachRelId) {
        this.attachRelId = attachRelId;
    },
    getAttachRelId: function () {
        return this.attachRelId;
    },
    setUpLoadGrid: function (upLoadGrid) {
        this.upLoadGrid = upLoadGrid;
    },
    getUpFileGrid: function () {
        return this.upLoadGrid;
    },
    uploadAction: function () {
        var me = this;
        if (this.attachRelId == null || this.attachRelId == '') {
            Ext.Msg.alert('提示', '请先暂存表单后再上传附件');
            return;
        }
        this.getForm().submit({
            url: attachmentUploadUrl,
            method: 'POST',
            params: {
                'resource.createUser': this.userCode,
                'resource.attachguid': this.attachRelId
            },
            success: function (form, action) {
                me.upLoadGrid.getPagingToolbar().moveFirst();
            },
            failure: function (form, action) {
                Ext.Msg.alert('提示', "文件上传失败!");
            }
        });
    },
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        me.items = [{
            xtype: 'fieldcontainer',
            layout: 'column',
            items: [{
                xtype: 'filefield',
                name: 'attach',
                labelWidth: 60,
                width: 350,
                fieldLabel: '上传附件',
                fieldStyle: 'border: solid 1px #B5B8C8!important;background: transparent !important;',
                buttonConfig: {
                    text: "浏览"
                },
                blankText: "不能为空",
                allowBlank: false
            }, {
                text: '上传',
                xtype: 'button',
                height: 22,
                handler: function () {
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
    extend: 'Ext.panel.Panel',
    alias: 'widget.upfilepanel',
    attachRelId: null,
    userCode: null,
    autoLoadGrid: false,
    setUserCode: function (userCode) {
        var uploadForm = this.getUploadForm();
        uploadForm.setUserCode(userCode);
        this.userCode = userCode;
    },
    getUserCode: function () {
        return this.userCode;
    },
    setAttachRelId: function (attachRelId) {
        var uploadForm = this.getUploadForm();
        var uploadGrid = this.getUploadGrid();
        uploadForm.setAttachRelId(attachRelId);
        uploadGrid.setAttachRelId(attachRelId);
        if (attachRelId) {
            uploadGrid.setAutoLoadStore(true);
        }
        this.attachRelId = attachRelId;
    },
    getAttachRelId: function () {
        return this.attachRelId;
    },
    uploadForm: null,
    getUploadForm: function () {
        var me = this;
        if (this.uploadForm == null) {
            this.uploadForm = Ext.create('Fssc.common.upFileForm', {
                relationActiveFun: function () {
                    if (me.relationActiveFun) {
                        me.relationActiveFun();
                    }
                }
            });
        }
        return this.uploadForm;
    },
    fileUpload: function () {
        if (this.uploadForm != null) {
            this.uploadForm.uploadAction();
        }
    },
    uploadGrid: null,
    getUploadGrid: function () {
        if (this.uploadGrid == null) {
            this.uploadGrid = Ext.create('Fssc.common.upFileGrid', {
                height: 268,
                autoScroll: true
            });
        }
        return this.uploadGrid;
    },
    constructor: function (config) {
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
            afterrender: function (obj, eOpts) {
                obj.getUploadGrid().setAutoLoadStore(true);
            }
        };
        me.callParent([cfg]);
    }
});

Ext.define('Foss.woodenStatementEdit.ShangChuanWindow', {
    extend: 'Ext.window.Window',
    width: stl.SCREENWIDTH * 0.7,
    modal: true,
    constrainHeader: true,
    closeAction: 'hide',
    loadMask: null,
    upfilepanel: null,
    getUpfilepanel: function () {
        if (this.upfilepanel == null) {
            this.upfilepanel = Ext.create('Fssc.common.upFilePanel');
            writeoff.woodenStatementEdit.upfilepanel = this.upfilepanel;
        }
        return this.upfilepanel;
    },
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        me.items = [me.getUpfilepanel()];
        me.callParent([cfg]);
    }
});
/**
 * 上传附件
 */
writeoff.woodenStatementEdit.paymentUpload = function () {
    writeoff.woodenStatementEdit.shangChuanWindow = Ext.create('Foss.woodenStatementEdit.ShangChuanWindow');
    writeoff.woodenStatementEdit.upfilepanel.setUserCode(writeoff.woodenStatementEdit.userCode);
    writeoff.woodenStatementEdit.upfilepanel.setAttachRelId(writeoff.woodenStatementEdit.batchNo);
    writeoff.woodenStatementEdit.shangChuanWindow.show();
}

/**
 * 取消操作
 */
writeoff.woodenStatementEdit.paymentCancel = function () {
    var win = this.up('window');
    Ext.Msg.confirm(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.isExistPaymentPage'), function (o) {
            //提示是否要删除
            if (o == 'yes') {
                if (!Ext.isEmpty(win)) {
                    win.hide();
                }
            }
        });
}

/**
 * 付款提交操作
 */
writeoff.woodenStatementEdit.paymentCommit = function () {
    //获取对账单列表
    var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //付款窗口
    var paymentBillWindow = writeoff.woodenStatementEdit.paymentBillWindow;
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
    if (Ext.isEmpty(paymentOrgName)) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.pawriteoff.paymentOrgNameIsNullWarning'));
        return false;
    }

    //部门编码不能为空
    if (Ext.isEmpty(paymentOrgCode)) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentOrgCodeIsNullWarning'));
        return false;
    }

    //公司名称不能为空
    if (Ext.isEmpty(paymentCompanyName)) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyNameIsNullWarning'));
        return false;
    }

    //公司编码不能为空
    if (Ext.isEmpty(paymentCompanyCode)) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentCompanyCodeIsNullWarning'));
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
    var payeeName = paymentBillForm.getForm().findField('payeeName').getValue();
    var payeeCode = paymentBillForm.getForm().findField('payeeCode').getValue();
    var mobilePhone = paymentBillForm.getForm().findField('mobilePhone').getValue();
    var invoiceHeadCode = paymentBillForm.getForm().findField('invoiceHeadCode').getValue();
    var invoiceHeadName = paymentBillForm.getForm().findField('invoiceHeadCode').getRawValue();
    //保理   temFactoring为临时变量
    var factoring = paymentBillForm.getForm().findField('factoring').getValue();
    var temFactoring = paymentBillForm.getForm().findField('temFactoring').getValue();

    //付款类型判断
    if (Ext.isEmpty(paymentType)) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentTypeIsNullWarning'));
        return false;
    }
    //金额判断
    if (Ext.isEmpty(amount) || amount <= 0) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cashToPayAmountLimitWarning'));
        return false;
    }
    //如果为电汇
    if (paymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER) {
        if (Ext.isEmpty(accountNo) || Ext.isEmpty(provinceCode)
            || Ext.isEmpty(cityCode) || Ext.isEmpty(bankHqCode)
            || Ext.isEmpty(bankBranchCode) || Ext.isEmpty(accountType)) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankInfoIsNullWarning'));
            return false;
        }
        //校验界面输入条件
        if (!paymentBillForm.getForm().isValid()) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.validateFailAlert'));
            return false;
        }
        //判断如果为电汇，收款人不能为空
        if (Ext.isEmpty(payeeName) || Ext.isEmpty(mobilePhone)) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.payeeInfoIsNullWarning'));
            return false;
        }
        //发票抬头判断
        if (Ext.isEmpty(invoiceHeadCode) || Ext.isEmpty(invoiceHeadName)) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceTitleIsNullWarning'));
            return false;
        }
    }
    //保理检验
    if (temFactoring == "Y") {
        var factorBeginTime = paymentBillForm.getForm().findField('factorBeginTime').getValue();
        var factorEndTime = paymentBillForm.getForm().findField('factorEndTime').getValue();
        var factorAccount = paymentBillForm.getForm().findField('factorAccount').getValue();
        var curDate = new Date();
        var time = Ext.Date.format(curDate, 'Y-m-d');
        var beginResult = Ext.Date.parse(time, 'Y-m-d') - Ext.Date.parse(factorBeginTime, 'Y-m-d');
        var endResult = Ext.Date.parse(factorEndTime, 'Y-m-d') - Ext.Date.parse(time, 'Y-m-d');
        //检验是否在保理期间
        if (beginResult >= 0 && endResult >= 0) {
            //校验银行账号与保理回款账户
            if (factorAccount != accountNo) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.validatefactoring'));
                return false;
            }
            //不在保理期间不在后台传递数据(根据Y/N 传递其他保理字段的值)
        } else {
            paymentBillForm.getForm().findField('factoring').setValue("N");
        }
    }
    //声明传入action参数
    var vo = new Object();
    //此处需要更新一下模型
    var data = paymentBillForm.getForm().getValues();

    //获取付款单头信息即界面form表单
    vo.paymentEntity = data;
    if (null != writeoff.woodenStatementEdit.batchNo) {
        vo.paymentEntity.batchNo = writeoff.woodenStatementEdit.batchNo;
    }
    vo.statementBillNo = statementBillNo;
    vo.factoring = factoring;
    //遮罩窗口
    paymentBillWindow.getLoadMask().show();
    //提交
    Ext.Ajax.request({
        url: ContextPath.STL_WEB + '/pay/woodenToPayment.action',
        actionMethods: 'POST',
        jsonData: {
            'vo': vo
        },
        success: function (response) {
            //获取返回结果
            var result = Ext.decode(response.responseText);
            //获取付款单号
            var paymentNo = result.vo.paymentEntity.paymentNo;
            //遮罩窗口
            paymentBillWindow.getLoadMask().hide();
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentSuccess')
                + paymentNo);
            //关闭窗口
            paymentBillWindow.close();
            statementEntryWindow.close();
            grid.store.load();
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            //隐藏掉遮罩
            paymentBillWindow.getLoadMask().hide();
            //弹出异常提示
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
        },
        unknownException: function (form, action) {
            //隐藏掉遮罩
            paymentBillWindow.getLoadMask().hide();
        },
        failure: function (form, action) {
            //隐藏掉遮罩
            paymentBillWindow.getLoadMask().hide();
        }
    });
}
/**
 * 付款信息
 */
Ext.define('Foss.woodenStatementEdit.AddPaymentFormPanel', {
    extend: 'Ext.form.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPaymentBill'),
    frame: true,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        labelWidth: 95,
        readOnly: true,
        columnWidth: .3
    },
    defaultType: 'textfield',
    layout: 'column',
    items: [{
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
        name: 'customerName'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
        name: 'customerCode'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),
        name: 'paymentOrgName'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),
        name: 'paymentOrgCode',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),
        name: 'statementBillNo'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
        xtype: 'numberfield',
        labelWidth: 90,
        name: 'currentAmount'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentPaymentAmount'),
        name: 'amount',
        labelWidth: 90,
        xtype: 'numberfield'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),
        name: 'paymentCompanyName'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),
        name: 'paymentCompanyCode',
        hidden: true
    }, {
        xtype: 'combobox',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paymentType'),
        name: 'paymentType',
        editable: false,
        store: null,
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        columnWidth: .3,
        store: FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE, null, null, [writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER]),
        readOnly: false,
        value: writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
    }, {
        xtype: 'textfield',
        readOnly: false,
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhone'),
        regex: /^1[3|4|5|8][0-9]\d{8}$/,
        regexText: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.mobilePhoneRegex'),
        name: 'mobilePhone',
        allowBlank: false
    }, {
        xtype: 'commonsubsidiaryselector',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.invoiceHeadCode'),
        readOnly: false,
        name: 'invoiceHeadCode',
        allowBlank: false
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountNo'),
        xtype: 'commonpayeeinfoselector',
        accountTypes: writeoff.FIN_ACCOUNT_TYPE_PUBLIC + "," + writeoff.FIN_ACCOUNT_TYPE_PRIVATE + "," + writeoff.FIN_ACCOUNT_TYPE_DPSON,
        accSort: '保理类',
        operatorId: FossUserContext.getCurrentUserEmp().empCode,
        name: 'accountNo',
        exactQuery: 'Y', //精确查找
        allowBlank: false,
        disabled: false,
        readOnly: false,
        listeners: {
            'change': function (th, newValue, oldValue) {
                //获取record
                var record = th.findRecordByValue(newValue);
                //获取form表单
                var form = this.up('form').getForm();
                if (!record) {
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
            'select': function (th, records) {
                //获取选中记录
                var record = records[0];
                //获取form表单
                var form = this.up('form').getForm();
                //判断是否选中记录为空
                if (!Ext.isEmpty(record)) {
                    form.findField('provinceName').setValue(record.get('accountbankstateName'));
                    form.findField('provinceCode').setValue(record.get('accountbankstateCode'));
                    form.findField('cityName').setValue(record.get('accountbankcityName'));
                    form.findField('cityCode').setValue(record.get('accountbankcityCode'));
                    form.findField('bankHqCode').setValue(record.get('accountbankCode'));
                    form.findField('bankHqName').setValue(record.get('accountbankName'));
                    form.findField('bankBranchName').setValue(record.get('accountbranchbankName'));
                    form.findField('bankBranchCode').setValue(record.get('accountbranchbankCode'));
                    form.findField('accountType').setValue(record.get('accountType'));
                    form.findField('accountTypeName').setValue(FossDataDictionary. rendererSubmitToDisplay(record.get('accountType'), settlementDict.FIN_ACCOUNT_TYPE));
                    form.findField('payeeName').setValue(record.get('beneficiaryName'));
                    form.findField('payeeCode').setValue(record.get('payeeNo'));
                }
            }
        }
    }, {
        fieldLabel: 'provinceCode',
        name: 'provinceCode',
        hidden: true
    }, {
        fieldLabel: 'sourceBillType',
        name: 'sourceBillType',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.provinceName'),
        name: 'provinceName'
    }, {
        fieldLabel: 'cityCode',
        name: 'cityCode',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cityName'),
        name: 'cityName'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankHqName'),
        name: 'bankHqName'
    }, {
        fieldLabel: 'bankHqCode',
        name: 'bankHqCode',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchName'),
        name: 'bankBranchName'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.bankBranchCode'),
        name: 'bankBranchCode'
    }, {
        fieldLabel: 'accountType',
        name: 'accountType',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.accountType'),
        name: 'accountTypeName'
    }, {
        fieldLabel: 'payeeName',
        name: 'payeeName',
        hidden: true
    }, {
        fieldLabel: 'payeeCode',
        name: 'payeeCode',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.factoring'),
        name: 'factoring'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.factoring'),
        name: 'temFactoring',
        hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.factorBeginTime'),
        name: 'factorBeginTime'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.factorEndTime'),
        name: 'factorEndTime'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.factorAccount'),
        name: 'factorAccount'
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cusCode'),
        name: 'cusCode'
        //hidden:true
    }, {
        xtype: 'textareafield',
        columnWidth: .9,
        height: 60,
        disabled: false,
        readOnly: false,
        autoScroll: true,
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.notes'),
        name: 'notes'
    }, {
        border: 1,
        xtype: 'container',
        columnWidth: 1,
        disabled: false,
        readOnly: false,
        defaultType: 'button',
        layout: 'column',
        items: [{
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.upload'),
            name: 'uploadddw',
            columnWidth: .1,
            handler: writeoff.woodenStatementEdit.paymentUpload
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .6
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.commit'),
            columnWidth: .1,
            handler: writeoff.woodenStatementEdit.paymentCommit
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.cancel'),
            columnWidth: .1,
            handler: writeoff.woodenStatementEdit.paymentCancel
        }]
    }]
});
/**
 * 对账单付款窗体
 */
Ext.define('Foss.woodenStatementEdit.paymentBillWindow', {
    extend: 'Ext.window.Window',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addPayment'),
    width: stl.SCREENWIDTH * 0.7,
    modal: true,
    constrainHeader: true,
    closeAction: 'hide',
    loadMask: null,//遮罩
    getLoadMask: function () {
        var me = this;
        //获取遮罩效果
        if (Ext.isEmpty(me.loadMask)) {
            me.loadMask = new Ext.LoadMask(me, {
                msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.savePaymentMask'),
                removeMask: true// 完成后移除
            });
        }
        return me.loadMask;
    },
    listeners: {
        'close': function () {
            writeoff.woodenStatementEdit.batchNo = null;
        }
    },
    items: [Ext.create('Foss.woodenStatementEdit.AddPaymentFormPanel')]
});
/**
 * 对账单付款操作
 */
writeoff.woodenStatementEdit.payment = function () {
    //录入付款单窗口
    if (Ext.isEmpty(writeoff.woodenStatementEdit.paymentBillWindow)) {
        writeoff.woodenStatementEdit.paymentBillWindow = Ext.create('Foss.woodenStatementEdit.paymentBillWindow');
    }
    var paymentBillWindow = writeoff.woodenStatementEdit.paymentBillWindow;
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
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
    if (billType == writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.YSWoodenAccount'));
        return false;
    }
    //判断对账单状态
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_N || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmPayment'));
        return false;
    }
    //未付金额
    if (currentPayBillsForm.findField('payUnverifyAmount').getValue() == 0) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.verifyPayment'));
        return false;
    }
    //设置付款界面
    paymentBillForm.reset();
    paymentBillForm.findField('customerName').setValue(statementEntryForm.findField('customerName').getValue());
    paymentBillForm.findField('customerCode').setValue(statementEntryForm.findField('customerCode').getValue());
    paymentBillForm.findField('statementBillNo').setValue(statementEntryForm.findField('statementBillNo').getValue());
    paymentBillForm.findField('paymentOrgCode').setValue(statementEntryForm.findField('createOrgCode').getValue());
    paymentBillForm.findField('paymentOrgName').setValue(statementEntryForm.findField('createOrgName').getValue());
    paymentBillForm.findField('paymentCompanyCode').setValue(statementEntryForm.findField('companyCode').getValue());
    paymentBillForm.findField('paymentCompanyName').setValue(statementEntryForm.findField('companyName').getValue());
    paymentBillForm.findField('currentAmount').setValue(currentPayBillsForm.findField('periodUnverifyPayAmount').getValue());
    paymentBillForm.findField('amount').setValue(currentPayBillsForm.findField('payUnverifyAmount').getValue());
    paymentBillForm.findField('sourceBillType').setValue(writeoff.SOURCE_BILL_TYPE__STATEMENT);
    Ext.Ajax.request({
        url: ContextPath.STL_WEB + '/pay/uploadAppendix.action',
        params: {
            'billPaymentVo.billPaymentQueryDto.customerName': statementEntryForm.findField('customerName').getValue(),
            'billPaymentVo.billPaymentQueryDto.customerCode': statementEntryForm.findField('customerCode').getValue(),
            'billPaymentVo.isFactoring': 'Y'
        },
        method: 'post',
        success: function (response) {
            var result = Ext.decode(response.responseText);
            writeoff.woodenStatementEdit.batchNo = result.billPaymentVo.paymentEntity.batchNo;
            writeoff.woodenStatementEdit.userCode = result.billPaymentVo.paymentEntity.createUserCode;
            var factoring = result.billPaymentVo.billPaymentQueryDto.factoring == 'Y' ? writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.yes') :
                result.billPaymentVo.billPaymentQueryDto.factoring == 'N' ? writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.no') : '';
            var temFactoring = result.billPaymentVo.billPaymentQueryDto.factoring;
            var factorAccount = result.billPaymentVo.billPaymentQueryDto.factorAccount;
            var factorBeginTime = result.billPaymentVo.billPaymentQueryDto.factorBeginTime;
            var factorEndTime = result.billPaymentVo.billPaymentQueryDto.factorEndTime;
            var beginTime = Ext.Date.format(new Date(factorBeginTime), "Y-m-d");
            var endTime = Ext.Date.format(new Date(factorEndTime), "Y-m-d");
            var cusCode = result.billPaymentVo.billPaymentQueryDto.cusCode;
            if (null != factorBeginTime) {
                paymentBillForm.findField('factorBeginTime').setValue(beginTime);
            }
            if (null != factorEndTime) {
                paymentBillForm.findField('factorEndTime').setValue(endTime);
            }
            paymentBillForm.findField('temFactoring').setValue(temFactoring);
            paymentBillForm.findField('factoring').setValue(factoring);
            paymentBillForm.findField('factorAccount').setValue(factorAccount);
            paymentBillForm.findField('cusCode').setValue(cusCode);
            paymentBillWindow.show();

        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
        }
    });
    //paymentBillWindow.show();
}
/**
 * 还款提交操作
 */
writeoff.woodenStatementEdit.statementRepaymentComplete = function (repaymentForm) {
    var billRepaymentManageVo,
        billStatementToPaymentQueryDto;
    //还款单FORM
    var form = repaymentForm.getForm();
    if (form.isValid()) {
        //处理对账单号
        var statementBillNos = [];
        var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
        //对账单基础信息
        var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
        //还款单窗口
        var repaymentBillWindow = writeoff.woodenStatementEdit.repaymentBillWindow;
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
            url: ContextPath.STL_WEB + '/pay/woodenToRepayment.action',
            actionMethods: 'POST',
            jsonData: {
                'billRepaymentManageVo': billRepaymentManageVo
            },
            success: function (response) {
                //获取返回结果
                var result = Ext.decode(response.responseText);
                //获取付款单号
                var repaymentNo = result.billRepaymentManageVo.billRepaymentManageDto.repaymentNo;
                //遮罩窗口
                repaymentForm.up('panel').hide();
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addRepaymentSuccess')
                    + repaymentNo);
                //关闭窗口
                repaymentBillWindow.close();
                statementEntryWindow.close();
                grid.store.load();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
            }
        });
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
    }
}
/**
 * 还款对账单信息
 */
Ext.define('Foss.woodenStatementEdit.RepaymentStatementForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementInfo'),
    frame: true,
    layout: {
        type: 'table',
        columns: 3
    },
    defaultType: 'textfield',
    defaults: {
        readOnly: true,
        labelWidth: 70,
        width: 240
    },
    items: [{
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
        style: 'margin-left:100px',
        name: 'customerName',
        colspan: 2
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
        name: 'customerCode',
        style: 'margin-left:100px',
        colspan: 2
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),
        name: 'statementBillNo',
        style: 'margin-left:100px',
        colspan: 3
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
        style: 'margin-left:100px',
        labelWidth: 95,
        xtype: 'numberfield',
        name: 'currentAmount',
        decimalPrecision: 2,
        colspan: 2
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRemainAmount'),
        style: 'margin-left:100px',
        name: 'currentRemainAmount',
        labelWidth: 95,
        xtype: 'numberfield',
        decimalPrecision: 2
    }]
});

/**
 * 还款单信息
 */
Ext.define('Foss.woodenStatementEdit.RepaymentForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentInfo'),
    frame: true,
    layout: {
        type: 'table',
        columns: 3
    },
    defaultType: 'textfield',
    defaults: {
        labelWidth: 70,
        width: (stl.SCREENWIDTH * 0.7 - 180) / 3
    },
    items: [{
        xtype: 'combo',
        name: 'repaymentType',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentType'),
        allowBlank: false,
        style: 'margin-left:100px',
        store: FossDataDictionary.getDataDictionaryStore(
            settlementDict.SETTLEMENT__PAYMENT_TYPE, null, null,
            [writeoff.SETTLEMENT__PAYMENT_TYPE__CASH,
                writeoff.SETTLEMENT__PAYMENT_TYPE__CARD,
                writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE]),
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        value: writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
        editable: false,
        listeners: {
            'select': function (combo) {
                if (combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                    || combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE) {
                    var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
                    this.up('form').getForm().findField('remittanceNumber').enable();
                    this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber') + ':');
                    this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                    this.up('form').getForm().findField('repaymentAmount').disable();
                } else if (combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__CASH
                    || combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__CARD) {
                    var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
                    this.up('form').getForm().findField('remittanceNumber').disable();
                    this.up('form').getForm().findField('remittanceNumber').labelEl.update('&nbsp;&nbsp;' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber') + ':');
                    this.up('form').getForm().findField('remittanceNumber').setValue(null);
                    this.up('form').getForm().findField('remittanceName').setValue(null);
                    this.up('form').getForm().findField('remittanceDate').setValue(null);
                    this.up('form').getForm().findField('availableAmount').setValue(null);
                    this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                    this.up('form').getForm().findField('repaymentAmount').enable();
                }
            }
        }
    }, {
        fieldLabel: '<span style="color:red;">*</span>' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber'),
        name: 'remittanceNumber',
        listeners: {
            'blur': function (th) {
                if (th.getValue() != null && th.getValue() != '') {
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
                        url: ContextPath.STL_WEB + '/pay/queryRemittanceDetail.action',
                        jsonData: {
                            'billRepaymentManageVo': billRepaymentManageVo
                        },
                        method: 'post',
                        success: function (response) {
                            var result = Ext.decode(response.responseText);
                            var remittanceDate = stl.dateFormat(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceDate, 'Y-m-d');
                            form.findField('remittanceName').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceName);
                            form.findField('remittanceDate').setValue(remittanceDate);
                            form.findField('availableAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
                            //比较还款金额和当前电汇、支票的可用金额，显示两者中较小的
                            if (result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount != null
                                && result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount != '') {
                                if (result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount < repaymentAmount) {
                                    form.findField('repaymentAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
                                }
                            }
                        },
                        exception: function (response) {
                            var result = Ext.decode(response.responseText);
                            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                            form.findField('remittanceNumber').setValue(null);
                        }
                    });
                }
            }
        }
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceName'),
        name: 'remittanceName',
        labelWidth: 90,
        disabled: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmount'),
        style: 'margin-left:100px',
        name: 'repaymentAmount',
        disabled: true,
        xtype: 'numberfield',
        allowBlank: false
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceDate'),
        name: 'remittanceDate',
        format: 'Y-m-d',
        xtype: 'datefield',
        disabled: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.availableAmount'),
        name: 'availableAmount',
        disabled: true,
        labelWidth: 90
    }, {
        xtype: 'textareafield',
        name: 'description',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.description'),
        autoScroll: true,
        style: 'margin-left:100px',
        format: 'Y-m-d',
        colspan: 3,
        width: (stl.SCREENWIDTH * 0.7 - 180) * 2 / 3
    }, {
        xtype: 'container',
        colspan: 3,
        width: 660,
        style: 'margin-left:100px',
        defaultType: 'button',
        layout: 'table',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            width: 180
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.return'),
            width: 70,
            cls: 'yellow_button',
            handler: function () {
                this.up('form').up('panel').hide();
            }
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),
            width: 70,
            style: 'margin-left:20px',
            cls: 'yellow_button',
            handler: function () {
                var form = this.up('form');
                var me = this;
                var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
                var repaymentAmount = form.getForm().findField('repaymentAmount').getValue();
                var availableAmount = form.getForm().findField('availableAmount').getValue();
                var repaymentType = form.getForm().findField('repaymentType').getValue();
                var remittanceNumber = form.getForm().findField('remittanceNumber').getValue();
                var repaymentTypeStr = writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticket');
                //还款金额判断
                if (repaymentAmount == null || repaymentAmount <= 0) {
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                        writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMin'));
                    return false;
                }
                //还款金额和应还金额判断
                if (repaymentAmount > currentRemainAmount) {
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                        writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMax'));
                    return false;
                }
                //电汇付款方式判断
                if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER) {
                    repaymentTypeStr = writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticket');
                    //校验汇款编码
                    if (remittanceNumber == null || remittanceNumber == '') {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
                        return false;
                    }
                    //校验还款金额和汇款金额
                    if (repaymentAmount > availableAmount) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticketRepaymentAmountMax'));
                        return false;
                    }
                }
                //支票付款方式判断
                if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE) {
                    repaymentTypeStr = writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.check');
                    //校验汇款编码
                    if (remittanceNumber == null || remittanceNumber == '') {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
                        return false;
                    }
                    //校验还款金额和汇款金额
                    if (repaymentAmount > availableAmount) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRepaymentAmountMax'));
                        return false;
                    }
                }
                //现金付款方式判断
                if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CASH) {
                    repaymentTypeStr = writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.cash');
                }
                //银行卡付款方式判断
                if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CARD) {
                    repaymentTypeStr = writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.bankcard');
                }
                //还款金额和应还金额判断
                if (repaymentAmount < currentRemainAmount) {
                    Ext.MessageBox.show({
                        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType') +
                        repaymentTypeStr + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAlert'),
                        buttons: Ext.MessageBox.YESCANCEL,
                        buttonText: {
                            yes: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.ok'),
                            cancel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.cancel')
                        },
                        fn: function (e) {
                            if (e == 'yes') {
                                //设置该按钮灰掉
                                me.disable(false);
                                //10秒后自动解除灰掉效果
                                setTimeout(function () {
                                    me.enable(true);
                                }, 10000);
                                writeoff.woodenStatementEdit.statementRepaymentComplete(form);
                            } else if (e == 'cancel') {
                                return false;
                            }
                        }
                    });
                    //还款金额和应还金额判断
                } else if (repaymentAmount == currentRemainAmount) {
                    Ext.MessageBox.show({
                        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType') +
                        repaymentTypeStr + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.isRepaymentAlert'),
                        buttons: Ext.MessageBox.YESCANCEL,
                        buttonText: {
                            yes: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.ok'),
                            cancel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.cancel')
                        },
                        fn: function (e) {
                            if (e == 'yes') {
                                //设置该按钮灰掉
                                me.disable(false);
                                //10秒后自动解除灰掉效果
                                setTimeout(function () {
                                    me.enable(true);
                                }, 10000);
                                writeoff.woodenStatementEdit.statementRepaymentComplete(form);
                            } else if (e == 'cancel') {
                                return false;
                            }
                        }
                    });
                }
            }
        }]
    }]
});
/**
 * 对账单还款窗体
 */
Ext.define('Foss.woodenStatementEdit.RepaymentBillWindow', {
    extend: 'Ext.window.Window',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentBill'),
    width: stl.SCREENWIDTH * 0.7,
    modal: true,
    constrainHeader: true,
    closeAction: 'hide',
    items: [Ext.create('Foss.woodenStatementEdit.RepaymentStatementForm'),
        Ext.create('Foss.woodenStatementEdit.RepaymentForm')]
});
/**
 * 对账单还款操作
 */
writeoff.woodenStatementEdit.repayment = function () {
    //还款单窗口
    if (Ext.isEmpty(writeoff.woodenStatementEdit.repaymentBillWindow)) {
        writeoff.woodenStatementEdit.repaymentBillWindow = Ext.create('Foss.woodenStatementEdit.RepaymentBillWindow');
    }
    var repaymentBillWindow = writeoff.woodenStatementEdit.repaymentBillWindow;
    //对账单基础信息窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
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
    if (billType == writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.YFWoodenAccount'));
        return false;
    }
    //对账单确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_N || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmRepayment'));
        return false;
    }
    //未还金额判断
    if (currentRecBillsForm.findField('recUnverifyAmount').getValue() == 0) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.verifyRepayment'));
        return false;
    }
    //设置还款界面
    repaymentStatementForm.reset();
    repaymentStatementForm.findField('customerName').setValue(statementEntryForm.findField('customerName').getValue());
    repaymentStatementForm.findField('customerCode').setValue(statementEntryForm.findField('customerCode').getValue());
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
}
/**
 * 对账单明细MODEL
 */
Ext.define('Foss.woodenStatementEdit.WoodenStatementDModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'packType'
    }, {
        name: 'businessDate'
    }, {
        name: 'payableNo'
    }, {
        name: 'waybillNo'
    }, {
        name: 'customerName'
    }, {
        name: 'customerCode'
    }, {
        name: 'billOrgCode'
    }, {
        name: 'billOrgName'
    }, {
        name: 'payableOrgCode'
    }, {
        name: 'payableOrgName'
    }, {
        name: 'amount',
        type: 'double'
    }, {
        name: 'verifyAmount',
        type: 'double'
    }, {
        name: 'unverifyAmount',
        type: 'double'
    }, {
        name: 'actualFrameVolume'
    }, {
        name: 'actualWoodenVolume'
    }, {
        name: 'actualMaskNumber'
    }, {
        name: 'bagBeltNum'
    }, {
        name: 'woodenBarLong'
    }, {
        name: 'bubbVelamenVolume'
    }, {
        name: 'bindVelamenVolume'
    }]
});
/**
 * 对账单按钮设置
 */
writeoff.woodenStatementEdit.operateButton = function () {
    //对账单明细
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单基础信息
    var baseForm = statementEntryWindow.items.items[0].getForm();
    //对账单操作按钮
    var operateFrom = statementEntryWindow.items.items[3];
    //对账单确认状态
    var confirmStatus = baseForm.findField('confirmStatus').getValue();
    //对账单单据类型
    var billType = baseForm.findField('billType').getValue();
    //确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_N) {
        //确认按钮
        operateFrom.items.items[1].show();
        //反确认按钮
        operateFrom.items.items[2].hide();
    } else {
        //确认按钮
        operateFrom.items.items[1].hide();
        //反确认按钮
        operateFrom.items.items[2].show();
    }
    //单据类型判断
    if (billType == writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT) {
        //还款按钮
        operateFrom.items.items[3].show();
        //付款按钮
        operateFrom.items.items[4].hide();
    } else {
        //还款按钮
        operateFrom.items.items[3].hide();
        //付款按钮
        operateFrom.items.items[4].show();
    }
    //确认反确认权限
    if (!writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/confirmStatement.action')) {
        //确认按钮
        operateFrom.items.items[1].hide();
        //反确认按钮
        operateFrom.items.items[2].hide();
    }
    //还款权限
    if (!writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/repayment.action')) {
        //还款按钮
        operateFrom.items.items[3].hide();
    }
    //付款权限
    if (!writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/batchWriteoffStatement.action')) {
        //付款按钮
        operateFrom.items.items[4].hide();
    }
    operateFrom.items.items[5].show();
    operateFrom.items.items[6].show();
}

/**
 * 判断应收应付对账单
 */
writeoff.woodenStatementEdit.recFormORpayForm = function (statementEntryWindow, result, selection) {
    //代打木架对账单数据集合
    var woodenStatementDto = result.woodenStatementVo.woodenStatementDto;
    //对账单基本数据
    var baseInfo = statementEntryWindow.items.items[0];
    //应收FORM
    var recForm = statementEntryWindow.items.items[1];
    //应付FORM
    var payForm = statementEntryWindow.items.items[2];
    //设置单据类型
    baseInfo.getForm().findField('billType').setValue(woodenStatementDto.billType);
    //对账单单据类型判断
    if (woodenStatementDto.billType == writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT) {
        //隐藏应付FORM
        payForm.hide();
        //显示应收FORM
        recForm.show();
        //设置发生金额
        recForm.getForm().findField('periodAmount').setValue(woodenStatementDto.periodAmount);
        //设置应收总额金额
        recForm.getForm().findField('periodRecAmount').setValue(woodenStatementDto.periodRecAmount);
        //设置应付总额金额
        recForm.getForm().findField('periodPayAmount').setValue(woodenStatementDto.periodPayAmount);
        //设置应收应付差额金额
        recForm.getForm().findField('periodUnverifyRecAmount').setValue(woodenStatementDto.unpaidAmount);
        //设置未还金额
        if (!Ext.isEmpty(selection)) {
            recForm.getForm().findField('recUnverifyAmount').setValue(selection[0].data.unpaidAmount);
        } else {
            recForm.getForm().findField('recUnverifyAmount').setValue(woodenStatementDto.unpaidAmount);
        }
    } else {
        //隐藏应收FORM
        recForm.hide();
        //显示应付FORM
        payForm.show();
        //设置发生金额
        payForm.getForm().findField('periodAmount').setValue(woodenStatementDto.periodAmount);
        //设置应收总额金额
        payForm.getForm().findField('periodRecAmount').setValue(woodenStatementDto.periodRecAmount);
        //设置应付总额金额
        payForm.getForm().findField('periodPayAmount').setValue(woodenStatementDto.periodPayAmount);
        //设置应收应付差额金额
        payForm.getForm().findField('periodUnverifyPayAmount').setValue(woodenStatementDto.unpaidAmount);
        //设置未付金额
        if (!Ext.isEmpty(selection)) {
            payForm.getForm().findField('payUnverifyAmount').setValue(selection[0].data.unpaidAmount);
        } else {
            payForm.getForm().findField('payUnverifyAmount').setValue(woodenStatementDto.unpaidAmount);
        }
    }
}
//ddw2
writeoff.woodenStatementEdit.removeAllStatementD = function () {
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
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
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_Y || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
        return false;
    }
    Ext.MessageBox.show({
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
        buttons: Ext.MessageBox.YESNO,
        fn: function (e) {
            //删除明细列表校验
            if (selections.length == 0) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
                return false;
            }

            if (e == 'yes') {
                //设置参数
                var searchParams = {
                    'woodenStatementVo.woodenStatementDto.statementBillNo': statementBillNo
                };
                Ext.Ajax.request({
                    url: writeoff.realPath('delWoodenStatementD.action'),
                    method: 'POST',
                    params: searchParams,
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText);
                        //刷新对账单明细列表
                        statementEntryGrid.store.load();
                        //设置应收应付金额
                        writeoff.woodenStatementEdit.recFormORpayForm(statementEntryWindow, result, '');
                        //设置操作按钮
                        writeoff.woodenStatementEdit.operateButton();
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                    }
                });
            } else {
                return false;
            }
        }
    });
}

//ddw1
writeoff.woodenStatementEdit.checkDelWoodenStatementD = function () {
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
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
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_Y || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
        return false;
    }
    Ext.MessageBox.show({
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
        buttons: Ext.MessageBox.YESNO,
        fn: function (e) {
            //删除明细列表校验
            if (selections.length == 0) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
                return false;
            }

            if (e == 'yes') {
                //应收应付单号集合
                var numbers = [];
                //删除对账单明细条数
                var length = selections.length;
                //循环编译单号集合
                for (var i = 0; i < length; i++) {
                    numbers.push(selections[i].get('payableNo'));
                }
                //拼接vo，注入到后台
                woodenStatementVo = new Object();
                woodenStatementDto = new Object();
                woodenStatementDto.numbers = numbers;
                woodenStatementDto.statementBillNo = statementBillNo;
                woodenStatementVo.woodenStatementDto = woodenStatementDto;
                Ext.Ajax.request({
                    url: writeoff.realPath('delWoodenStatementD.action'),
                    method: 'POST',
                    jsonData: {
                        'woodenStatementVo': woodenStatementVo
                    },
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText);
                        //刷新对账单明细列表
                        statementEntryGrid.store.load();
                        //设置应收应付金额
                        writeoff.woodenStatementEdit.recFormORpayForm(statementEntryWindow, result, '');
                        //设置操作按钮
                        writeoff.woodenStatementEdit.operateButton();
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                    }
                });
            } else {
                return false;
            }
        }
    });
}

/**
 * 对账单删除明细提交操作
 */
writeoff.woodenStatementEdit.delWoodenStatementDSubmit = function () {
    //删除对账单明细窗口
    var delWoodenStatementDWindow = writeoff.woodenStatementEdit.delWoodenStatementDWindow;
    //删除对账单明细FORM
    var delWoodenStatementDForm = delWoodenStatementDWindow.items.items[0].getForm();
    //删除对账单明细GRID
    var delWoodenStatementDGrid = delWoodenStatementDWindow.items.items[1];
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单明细FORM
    var statementEntryForm = statementEntryWindow.items.items[0].getForm();
    //对账单明细GRID
    var statementEntryGrid = statementEntryWindow.items.items[4];
    //对账单单号
    var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
    var me = this;
    Ext.MessageBox.show({
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
        buttons: Ext.MessageBox.YESNO,
        fn: function (e) {
            //删除明细列表校验
            if (delWoodenStatementDGrid.store.data.length == 0) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
                return false;
            }

            if (e == 'yes') {
                //应收应付单号集合
                var numbers = [];
                //删除对账单明细列表
                var data = delWoodenStatementDGrid.store.data;
                //删除对账单明细条数
                var length = data.items.length;
                //循环编译单号集合
                for (var i = 0; i < length; i++) {
                    numbers.push(data.items[i].get('payableNo'));
                }
                //设置参数
                var searchParams = {
                    'woodenStatementVo.woodenStatementDto.numbers': numbers,
                    'woodenStatementVo.woodenStatementDto.statementBillNo': statementBillNo
                };
                Ext.Ajax.request({
                    url: writeoff.realPath('delWoodenStatementD.action'),
                    method: 'POST',
                    params: searchParams,
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText);
                        //刷新对账单明细列表
                        statementEntryGrid.store.load();
                        //设置应收应付金额
                        writeoff.woodenStatementEdit.recFormORpayForm(statementEntryWindow, result, '');
                        //设置操作按钮
                        writeoff.woodenStatementEdit.operateButton();
                        //关闭对账单明细窗口
                        delWoodenStatementDWindow.close();
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                    }
                });
            } else {
                return false;
            }
        }
    });
}
/**
 * 按应收应付单号查询要删除的明细
 */
writeoff.woodenStatementEdit.statementDelByYFYSNumber = function () {
    //对账单删除明细窗口
    var win = writeoff.woodenStatementEdit.delWoodenStatementDWindow;
    var form = win.items.items[0].getForm();
    var grid = win.items.items[1];
    //输入单号集合
    var numbers = form.findField('numbers').getValue();
    //判断传入单号是否为null或''
    if (Ext.String.trim(numbers) != null && Ext.String.trim(numbers) != '') {
        var billNumberArray = stl.splitToArray(numbers);
        for (var i = 0; i < billNumberArray.length; i++) {
            //循环将空格等剔除掉
            if (Ext.String.trim(billNumberArray[i]) == '') {
                billNumberArray.pop(billNumberArray[i]);
            }
        }
        //判断输入单号是否超过100个
        if (billNumberArray.length > 100) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
            return false;
        }
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
        return false;
    }
    //当界面校验都通过后，才执行查询方法
    if (form.isValid()) {
        writeoff.woodenStatementEdit.dNumbers = numbers;
        //查询后台
        grid.store.loadPage(1, {
            callback: function (records, operation, success) {
                var rawData = grid.store.proxy.reader.rawData;
                if (!success && !rawData.isException) {
                    var result = Ext.decode(operation.response.responseText);
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                }
                if (success) {
                    //对结果进行转化
                    var result = Ext.decode(operation.response.responseText);
                    //判断查询结果
                    if (Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementDList)
                        || result.woodenStatementVo.woodenStatementDto.woodenStatementDList.length == 0) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
                        return false;
                    }
                }
            }
        });
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
        return false;
    }
}
/**
 * 对账单删除明细STORE
 */
Ext.define('Foss.woodenStatementEdit.delWoodenStatementDStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.woodenStatementEdit.WoodenStatementDModel',
    pageSize: 100,
    proxy: {
        type: 'ajax',
        url: writeoff.realPath('queryDelWoodenStatementD.action'),
        actionMethods: 'POST',
        reader: {
            type: 'json',
            root: 'woodenStatementVo.woodenStatementDto.woodenStatementDList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var numbers = stl.splitToArray(writeoff.woodenStatementEdit.dNumbers);
            var searchParams = {
                'woodenStatementVo.woodenStatementDto.numbers': numbers,
                'woodenStatementVo.woodenStatementDto.statementBillNo': writeoff.woodenStatementEdit.statementBillNo
            };
            //设置查询条件
            Ext.apply(operation, {
                params: searchParams
            });
        }
    }
});
/**
 * 对账单删除明细GRID
 */
Ext.define('Foss.woodenStatementEdit.delWoodenStatementDGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    emptyText: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
    frame: true,
    detailWin: null,
    store: Ext.create('Foss.woodenStatementEdit.delWoodenStatementDStore'),
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    height: 500,
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    defaults: {
        align: 'center'
    },
    columns: [{
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.packType'),
        dataIndex: 'packType',
        renderer: function (value) {
            var displayField = null;
            if (!Ext.isEmpty(value)) {
                if (value == "MAP") {
                    displayField = "主要包装";
                } else {
                    displayField = "辅助包装";
                }
            }
            return displayField;
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
        dataIndex: 'payableNo',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
        dataIndex: 'waybillNo',
        width: 150
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgCode'),
        dataIndex: 'billOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgName'),
        dataIndex: 'billOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgCode'),
        dataIndex: 'payableOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgName'),
        dataIndex: 'payableOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.amount'),
        dataIndex: 'amount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.verifyAmount'),
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.unverifyAmount'),
        dataIndex: 'unverifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualFrameVolume'),
        dataIndex: 'actualFrameVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualWoodenVolume'),
        dataIndex: 'actualWoodenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualMaskNumber'),
        dataIndex: 'actualMaskNumber'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bagBeltNum'),
        dataIndex: 'bagBeltNum'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenBarLong'),
        dataIndex: 'woodenBarLong'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bubbVelamenVolume'),
        dataIndex: 'bubbVelamenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bindVelamenVolume'),
        dataIndex: 'bindVelamenVolume'
    }],
    initComponent: function () {
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaultType: 'textfield',
            defaults: {
                margin: '5 0 5 3',
                readOnly: true,
                labelWidth: 60
            },
            items: [{
                xtype: 'button',
                text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delStatementD'),
                columnWidth: .1,
                hidden: !writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
                handler: writeoff.woodenStatementEdit.delWoodenStatementDSubmit
            }]
        }];
        me.callParent();
    }
});
/**
 * 对账单删除明细FORM
 */
Ext.define('Foss.woodenStatementEdit.delWoodenStatementDForm', {
    extend: 'Ext.form.Panel',
    frame: true,
    cls: 'autoHeight',
    bodyCls: 'autoHeight',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
    layout: 'column',
    defaults: {
        labelWidth: 75,
        margin: '5 5 5 5'
    },
    items: [{
        xtype: 'textareafield',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
        allowBlank: false,
        columnWidth: .65,
        regex: /^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,
        labelWidth: 70,
        labelAlign: 'right',
        name: 'numbers',
        autoScroll: true,
        height: 104
    }, {
        xtype: 'container',
        columnWidth: .35,
        layout: 'vbox',
        items: [{
            xtype: 'component',
            padding: '0 0 0 10',
            autoEl: {
                tag: 'div',
                html: '<span style="color:red;">' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryNosDesc') + '</span>'
            }
        }]
    }, {
        xtype: 'container',
        columnWidth: 1,
        layout: 'column',
        defaultType: 'button',
        defaults: {
            width: 80
        },
        items: [{
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.reset'),
            columnWidth: .075,
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .5
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.query'),
            cls: 'yellow_button',
            columnWidth: .075,
            handler: writeoff.woodenStatementEdit.statementDelByYFYSNumber
        }]
    }]
});
/**
 * 对账单删除明细窗体
 */
Ext.define('Foss.woodenStatementEdit.delWoodenStatementDWindow', {
    extend: 'Ext.window.Window',
    width: stl.SCREENWIDTH * 0.9,
    modal: true,
    constrainHeader: true,
    closeAction: 'destory',
    items: [Ext.create('Foss.woodenStatementEdit.delWoodenStatementDForm'),
        Ext.create('Foss.woodenStatementEdit.delWoodenStatementDGrid')]
});
/**
 * 对账单删除明细操作
 */
writeoff.woodenStatementEdit.delWoodenStatementD = function () {
    //对账单删除窗口
    var win = writeoff.woodenStatementEdit.delWoodenStatementDWindow;
    if (Ext.isEmpty(writeoff.woodenStatementEdit.delWoodenStatementDWindow)) {
        writeoff.woodenStatementEdit.delWoodenStatementDWindow = Ext.create('Foss.woodenStatementEdit.delWoodenStatementDWindow');
        win = writeoff.woodenStatementEdit.delWoodenStatementDWindow;
    }
    //清空表单
    win.items.items[0].getForm().reset();
    //清空列表
    win.items.items[1].store.removeAll();
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单基础信息
    var baseForm = statementEntryWindow.items.items[0].getForm();
    //对账单确认状态
    var confirmStatus = baseForm.findField('confirmStatus').getValue();
    //对账单确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_Y || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
        return false;
    }
    win.show();
}
/**
 * 对账单添加明细提交操作
 */
writeoff.woodenStatementEdit.addWoodenStatementDSubmit = function () {
    //添加对账单明细窗口
    //添加对账单明细窗口
    var addWoodenStatementDWindow = writeoff.woodenStatementEdit.addWoodenStatementDWindow;
    //添加对账单明细FORM
    var addWoodenStatementDForm = addWoodenStatementDWindow.items.items[0].getForm();
    //添加对账单明细GRID
    var addWoodenStatementDGrid = addWoodenStatementDWindow.items.items[1];
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单明细基础信息
    var statementEntryForm = statementEntryWindow.items.items[0].getForm();
    //对账单明细GRID
    var statementEntryGrid = statementEntryWindow.items.items[4];
    //对账单单号
    var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
    var me = this;
    Ext.MessageBox.show({
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),
        buttons: Ext.MessageBox.YESNO,
        fn: function (e) {
            //添加明细列表校验
            if (addWoodenStatementDGrid.store.data.length == 0) {
                Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                    writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
                return false;
            }
            if (e == 'yes') {
                //判断是按那种查询进行
                var searchParams;
                if (writeoff.woodenStatementEdit.queryDTabType == writeoff.STATEMENTQUERYTAB_BYCUSTOMER) {
                    //设置参数
                    searchParams = {
                        'woodenStatementVo.woodenStatementDto.periodBeginDate': writeoff.woodenStatementEdit.periodBeginDate,
                        'woodenStatementVo.woodenStatementDto.periodEndDate': writeoff.woodenStatementEdit.periodEndDate,
                        'woodenStatementVo.woodenStatementDto.customerCode': writeoff.woodenStatementEdit.customerCode,
                        'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryDTabType
                    };
                } else {
                    //应收应付单号集合
                    var numbers = [];
                    //添加对账单明细列表
                    var data = addWoodenStatementDGrid.store.data;
                    //添加对账单明细条数
                    var length = data.items.length;
                    //循环编译单号集合
                    for (var i = 0; i < length; i++) {
                        numbers.push(data.items[i].get('payableNo'));
                    }
                    //设置参数
                    searchParams = {
                        'woodenStatementVo.woodenStatementDto.numbers': numbers,
                        'woodenStatementVo.woodenStatementDto.statementBillNo': statementBillNo,
                        'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryDTabType
                    };
                }
                //拼接vo，注入到后台
                Ext.Ajax.request({
                    url: writeoff.realPath('addWoodenStatementD.action'),
                    method: 'POST',
                    params: searchParams,
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText);
                        statementEntryGrid.store.load();
                        //设置应收应付金额
                        writeoff.woodenStatementEdit.recFormORpayForm(statementEntryWindow, result, '');
                        //设置操作按钮
                        writeoff.woodenStatementEdit.operateButton();
                        //关闭对账单明细窗口
                        addWoodenStatementDWindow.close();
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.addWoodenStatementDSuccess'));
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                    }
                });
            } else {
                return false;
            }
        }
    });
}
/**
 * 按应收应付单号查询要添加的明细
 */
writeoff.woodenStatementEdit.statementAddByYFYSNumber = function () {
    //添加对账单明细窗口
    var win = writeoff.woodenStatementEdit.addWoodenStatementDWindow;
    var form = win.items.items[0].getForm();
    var grid = win.items.items[1];
    //输入单号集合
    var numbers = form.findField('numbers').getValue();
    //判断传入单号是否为null或''
    if (Ext.String.trim(numbers) != null && Ext.String.trim(numbers) != '') {
        var billNumberArray = stl.splitToArray(numbers);
        for (var i = 0; i < billNumberArray.length; i++) {
            //循环将空格等剔除掉
            if (Ext.String.trim(billNumberArray[i]) == '') {
                billNumberArray.pop(billNumberArray[i]);
            }
        }
        //判断输入单号是否超过100个
        if (billNumberArray.length > 100) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.queryNosLimit'));
            return false;
        }
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
        return false;
    }
    //当界面校验都通过后，才执行查询方法
    if (form.isValid()) {
        writeoff.woodenStatementEdit.queryDTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
        writeoff.woodenStatementEdit.dNumbers = numbers;
        //查询后台
        grid.store.loadPage(1, {
            callback: function (records, operation, success) {
                var rawData = grid.store.proxy.reader.rawData;
                if (!success && !rawData.isException) {
                    var result = Ext.decode(operation.response.responseText);
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                }
                if (success) {
                    //对结果进行转化
                    var result = Ext.decode(operation.response.responseText);
                    //判断查询结果
                    if (Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementDList)
                        || result.woodenStatementVo.woodenStatementDto.woodenStatementDList.length == 0) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
                        return false;
                    }
                }
            }
        });
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
        return false;
    }
}
/**
 * 对账单添加明细STORE
 */
Ext.define('Foss.woodenStatementEdit.addWoodenStatementDStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.woodenStatementEdit.WoodenStatementDModel',
    pageSize: 100,
    proxy: {
        type: 'ajax',
        url: writeoff.realPath('queryAddWoodenStatementD.action'),
        actionMethods: 'POST',
        reader: {
            type: 'json',
            root: 'woodenStatementVo.woodenStatementDto.woodenStatementDList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var searchParams;
            //对账单明细窗口
            var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
            var customerCode = statementEntryWindow.items.items[0].getForm().findField('customerCode').getValue();
            if (writeoff.woodenStatementEdit.queryDTabType == writeoff.STATEMENTQUERYTAB_BYCUSTOMER) {
                searchParams = {
                    'woodenStatementVo.woodenStatementDto.periodBeginDate': writeoff.woodenStatementEdit.periodBeginDate,
                    'woodenStatementVo.woodenStatementDto.periodEndDate': writeoff.woodenStatementEdit.periodEndDate,
                    'woodenStatementVo.woodenStatementDto.customerCode': writeoff.woodenStatementEdit.customerCode,
                    'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryDTabType
                };
            } else {
                var numbers = stl.splitToArray(writeoff.woodenStatementEdit.dNumbers);
                searchParams = {
                    'woodenStatementVo.woodenStatementDto.numbers': numbers,
                    'woodenStatementVo.woodenStatementDto.customerCode': customerCode,
                    'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryDTabType
                };
            }
            //设置查询条件
            Ext.apply(operation, {
                params: searchParams
            });
        }
    }
});
/**
 * 对账单添加明细GRID
 */
Ext.define('Foss.woodenStatementEdit.addWoodenStatementDGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    emptyText: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
    frame: true,
    detailWin: null,
    store: Ext.create('Foss.woodenStatementEdit.addWoodenStatementDStore'),
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    height: 500,
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    defaults: {
        align: 'center'
    },
    columns: [{
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.packType'),
        dataIndex: 'packType',
        renderer: function (value) {
            var displayField = null;
            if (!Ext.isEmpty(value)) {
                if (value == "MAP") {
                    displayField = "主要包装";
                } else {
                    displayField = "辅助包装";
                }
            }
            return displayField;
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
        dataIndex: 'payableNo',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
        dataIndex: 'waybillNo',
        width: 150
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgCode'),
        dataIndex: 'billOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgName'),
        dataIndex: 'billOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgCode'),
        dataIndex: 'payableOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgName'),
        dataIndex: 'payableOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.amount'),
        dataIndex: 'amount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.verifyAmount'),
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.unverifyAmount'),
        dataIndex: 'unverifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualFrameVolume'),
        dataIndex: 'actualFrameVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualWoodenVolume'),
        dataIndex: 'actualWoodenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualMaskNumber'),
        dataIndex: 'actualMaskNumber'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bagBeltNum'),
        dataIndex: 'bagBeltNum'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenBarLong'),
        dataIndex: 'woodenBarLong'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bubbVelamenVolume'),
        dataIndex: 'bubbVelamenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bindVelamenVolume'),
        dataIndex: 'bindVelamenVolume'
    }],
    initComponent: function () {
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaultType: 'textfield',
            defaults: {
                margin: '5 0 5 3',
                readOnly: true,
                labelWidth: 60
            },
            items: [{
                xtype: 'button',
                text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addStatementD'),
                columnWidth: .1,
                hidden: !writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/addStatementDetail.action'),
                handler: writeoff.woodenStatementEdit.addWoodenStatementDSubmit
            }]
        }];
        me.callParent();
    }
});
/**
 * 对账单添加明细FORM
 */
Ext.define('Foss.woodenStatementEdit.addWoodenStatementDForm', {
    extend: 'Ext.form.Panel',
    frame: true,
    cls: 'autoHeight',
    bodyCls: 'autoHeight',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
    layout: 'column',
    defaults: {
        labelWidth: 75,
        margin: '5 5 5 5'
    },
    items: [{
        xtype: 'textareafield',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
        allowBlank: false,
        columnWidth: .65,
        regex: /^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,
        labelWidth: 70,
        labelAlign: 'right',
        name: 'numbers',
        autoScroll: true,
        height: 104
    }, {
        xtype: 'container',
        columnWidth: .35,
        layout: 'vbox',
        items: [{
            xtype: 'component',
            padding: '0 0 0 10',
            autoEl: {
                tag: 'div',
                html: '<span style="color:red;">' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryNosDesc') + '</span>'
            }
        }]
    }, {
        xtype: 'container',
        columnWidth: 1,
        layout: 'column',
        defaultType: 'button',
        defaults: {
            width: 80
        },
        items: [{
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.reset'),
            columnWidth: .075,
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .5
        }, {
            text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.query'),
            cls: 'yellow_button',
            columnWidth: .075,
            handler: writeoff.woodenStatementEdit.statementAddByYFYSNumber
        }]
    }]
});
/**
 * 对账单添加明细窗体
 */
Ext.define('Foss.woodenStatementEdit.addWoodenStatementDWindow', {
    extend: 'Ext.window.Window',
    width: stl.SCREENWIDTH * 0.9,
    modal: true,
    constrainHeader: true,
    closeAction: 'destory',
    items: [Ext.create('Foss.woodenStatementEdit.addWoodenStatementDForm'),
        Ext.create('Foss.woodenStatementEdit.addWoodenStatementDGrid')]
});
/**
 * 对账单添加明细操作
 */
writeoff.woodenStatementEdit.addWoodenStatementD = function () {
    //添加对账单明细窗口
    var win = writeoff.woodenStatementEdit.addWoodenStatementDWindow;
    if (Ext.isEmpty(writeoff.woodenStatementEdit.addWoodenStatementDWindow)) {
        writeoff.woodenStatementEdit.addWoodenStatementDWindow = Ext.create('Foss.woodenStatementEdit.addWoodenStatementDWindow');
        win = writeoff.woodenStatementEdit.addWoodenStatementDWindow;
    }
    //清空表单
    win.items.items[0].getForm().reset();
    //清空列表
    win.items.items[1].store.removeAll();
    //对账单明细窗口
    var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单基础信息
    var baseForm = statementEntryWindow.items.items[0].getForm();
    //对账单确认状态
    var confirmStatus = baseForm.findField('confirmStatus').getValue();
    //对账单确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_Y || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmAddWooden'));
        return false;
    }
    win.show();
}
/**
 * 对账单确认
 */
writeoff.woodenStatementEdit.statementConfirm = function () {
    //选择对账单数据
    var selection = writeoff.woodenStatementEdit.selection;
    //对账单明细窗口
    var win = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单基础信息
    var baseForm = win.items.items[0].getForm();
    //对账单单号
    var statementBillNo = baseForm.findField('statementBillNo').getValue();
    //对账单确认状态
    var confirmStatus = baseForm.findField('confirmStatus').getValue();
    //对账单确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_Y || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
        return false;
    }
    //对账单单号判断
    if (statementBillNo == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
        return false;
    }
    //对账单金额判断
    if (selection[0].data.periodAmount != selection[0].data.unpaidAmount) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
        return false;
    }
    //拼接vo，注入到后台
    woodenStatementVo = new Object();
    woodenStatementDto = new Object();
    woodenStatementDto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
    woodenStatementDto.statementBillNo = statementBillNo;
    woodenStatementVo.woodenStatementDto = woodenStatementDto;
    Ext.Ajax.request({
        url: writeoff.realPath('confirmWoodenStatement.action'),
        jsonData: {
            'woodenStatementVo': woodenStatementVo
        },
        success: function (response) {
            var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
            //刷新对账单列表
            grid.store.load();
            //设置对账单确认状态
            baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_Y);
            //设置对账单确认日期
            baseForm.findField('confirmTime').setValue(new Date());
            //设置操作按钮
            writeoff.woodenStatementEdit.operateButton();
            Ext.ux.Toast.msg(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
        }
    });
}

/**
 * 对账单反确认
 */
writeoff.woodenStatementEdit.statementUnConfirm = function () {
    //选择对账单数据
    var selection = writeoff.woodenStatementEdit.selection;
    //对账单明细窗口
    var win = writeoff.woodenStatementEdit.statementEntryWindow;
    //对账单基础信息
    var baseForm = win.items.items[0].getForm();
    //对账单单号
    var statementBillNo = baseForm.findField('statementBillNo').getValue();
    //对账单确认状态
    var confirmStatus = baseForm.findField('confirmStatus').getValue();
    //对账单确认状态判断
    if (confirmStatus == writeoff.STATEMENTCONFIRMSTATUS_N || confirmStatus == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
        return false;
    }
    //对账单单号判断
    if (statementBillNo == '') {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
        return false;
    }
    //对账单金额判断
    if (selection[0].data.periodAmount != selection[0].data.unpaidAmount) {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
        return false;
    }
    //拼接vo，注入到后台
    woodenStatementVo = new Object();
    woodenStatementDto = new Object();
    woodenStatementDto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_N;
    woodenStatementDto.statementBillNo = statementBillNo;
    woodenStatementVo.woodenStatementDto = woodenStatementDto;
    Ext.Ajax.request({
        url: writeoff.realPath('confirmWoodenStatement.action'),
        jsonData: {
            'woodenStatementVo': woodenStatementVo
        },
        success: function (response) {
            var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
            //刷新对账单列表
            grid.store.load();
            //设置对账单确认状态
            baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_N);
            //设置对账单确认日期
            baseForm.findField('confirmTime').setValue(null);
            //设置操作按钮
            writeoff.woodenStatementEdit.operateButton();
            Ext.ux.Toast.msg(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
        }
    });
}
/**
 * 对账单明细STORE
 */
Ext.define('Foss.woodenStatementEdit.woodenStatementDStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.woodenStatementEdit.WoodenStatementDModel',
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: writeoff.realPath('queryWoodenDByStatementBillNo.action'),
        actionMethods: 'POST',
        reader: {
            type: 'json',
            root: 'woodenStatementVo.woodenStatementDto.woodenStatementDList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var searchParams = {
                'woodenStatementVo.woodenStatementDto.statementBillNo': writeoff.woodenStatementEdit.statementBillNo
            };
            //设置查询条件
            Ext.apply(operation, {
                params: searchParams
            });
        }
    }
});
/**
 * 对账单明细GRID
 */
Ext.define('Foss.woodenStatementEdit.woodenStatementDGrid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    emptyText: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
    frame: true,
    detailWin: null,
    store: Ext.create('Foss.woodenStatementEdit.woodenStatementDStore'),
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    height: 500,
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    defaults: {
        align: 'center'
    },
    columns: [{
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.packType'),
        dataIndex: 'packType',
        renderer: function (value) {
            var displayField = null;
            if (!Ext.isEmpty(value)) {
                if (value == "MAP") {
                    displayField = "主要包装";
                } else {
                    displayField = "辅助包装";
                }
            }
            return displayField;
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
        dataIndex: 'payableNo',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
        dataIndex: 'waybillNo',
        width: 150
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgCode'),
        dataIndex: 'billOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgName'),
        dataIndex: 'billOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgCode'),
        dataIndex: 'payableOrgCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgName'),
        dataIndex: 'payableOrgName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.amount'),
        dataIndex: 'amount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.verifyAmount'),
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.unverifyAmount'),
        dataIndex: 'unverifyAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualFrameVolume'),
        dataIndex: 'actualFrameVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualWoodenVolume'),
        dataIndex: 'actualWoodenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.actualMaskNumber'),
        dataIndex: 'actualMaskNumber'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bagBeltNum'),
        dataIndex: 'bagBeltNum'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenBarLong'),
        dataIndex: 'woodenBarLong'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bubbVelamenVolume'),
        dataIndex: 'bubbVelamenVolume'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.bindVelamenVolume'),
        dataIndex: 'bindVelamenVolume'
    }],
    initComponent: function () {
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'top',
            layout: 'column',
            defaultType: 'textfield',
            defaults: {
                margin: '5 0 5 3',
                readOnly: true,
                labelWidth: 60
            },
            items: [{
                xtype: 'button',
                text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),
                columnWidth: .1,
                hidden: !writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/addStatementDetail.action'),
                handler: writeoff.woodenStatementEdit.addWoodenStatementD
            }, {
                xtype: 'button',
                text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
                columnWidth: .1,
                hidden: !writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
                handler: writeoff.woodenStatementEdit.delWoodenStatementD
            }, {
                xtype: 'button',
                text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.checkDelWoodenStatementD'),
                columnWidth: .1,
//				hidden:!writeoff.woodenStatementEdit.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
                handler: writeoff.woodenStatementEdit.checkDelWoodenStatementD
            }]
        }, {
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaultType: 'textfield',
            defaults: {
                margin: '5 0 5 3',
                readOnly: true,
                labelWidth: 60
            },
            items: [{
                xtype: 'standardpaging',
                store: me.store,
                columnWidth: .9,
                pageSize: 50,
                plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    //设置分页记录最大值，防止输入过大的数值
                    maximumSize: 1000,
                    sizeList: [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]]
                })
            }]
        }];
        me.callParent();
    }
});
/**
 * 对账单明细操作按钮
 */
Ext.define('Foss.woodenStatementEdit.OperateButtonPanel', {
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
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.statementConfirm
    }, {
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.statementUnConfirm
    }, {
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.repayment
    }, {
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.payment'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.payment
    }, {
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportExcel'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.exportExcel
    }, {
        text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.removeAllStatementD'),
        hidden: true,
        handler: writeoff.woodenStatementEdit.removeAllStatementD
    }]
});
/**
 * 对账单明细应收FROM
 */
Ext.define('Foss.woodenStatementEdit.CurrentRecBillsForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodCurrentBills'),
    layout: 'column',
    frame: true,
    hidden: true,
    defaultType: 'displayfield',
    defaults: {
        labelWidth: 5,
        readOnly: true
    },
    items: [{
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
        columnWidth: .15,
        style: 'margin-left:30px;margin-top:10px;'
    }, {
        value: '=',
        style: 'margin-top:10px;',
        columnWidth: .05
    }, {
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),
        style: 'margin-top:10px;',
        columnWidth: .15
    }, {
        value: '-',
        style: 'margin-top:10px;',
        columnWidth: .05
    }, {
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
        style: 'margin-top:10px;',
        columnWidth: .15
    }, {
        xtype: 'component',
        border: true,
        style: 'margin-left:30px;margin-top:10px;',
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        name: 'periodAmount',
        columnWidth: .2,
        xtype: 'numberfield',
        style: 'margin-left:30px;'
    }, {
        name: 'periodRecAmount',
        columnWidth: .2,
        xtype: 'numberfield'
    }, {
        name: 'periodPayAmount',
        columnWidth: .2,
        xtype: 'numberfield'
    }, {
        xtype: 'component',
        border: true,
        style: 'margin-left:30px;margin-top:10px;',
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        xtype: 'numberfield',
        name: 'periodUnverifyRecAmount',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.Ar'),
        labelWidth: 40,
        style: 'margin-left:20px;margin-top:10px;',
        columnWidth: .19
    }, {
        xtype: 'numberfield',
        name: 'recUnverifyAmount',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.RecUnverifyAmount'),
        labelWidth: 75,
        style: 'margin-top:10px;',
        columnWidth: .19
    }]
});
/**
 * 对账单明细应付FROM
 */
Ext.define('Foss.woodenStatementEdit.CurrentPayBillsForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodCurrentBills'),
    layout: 'column',
    frame: true,
    hidden: true,
    defaultType: 'displayfield',
    defaults: {
        labelWidth: 5,
        readOnly: true
    },
    items: [{
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
        columnWidth: .15,
        style: 'margin-left:30px;margin-top:10px;'
    }, {
        value: '=',
        style: 'margin-top:10px;',
        columnWidth: .05
    }, {
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
        style: 'margin-top:10px;',
        columnWidth: .15
    }, {
        value: '-',
        style: 'margin-top:10px;',
        columnWidth: .05
    }, {
        value: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),
        style: 'margin-top:10px;',
        columnWidth: .15
    }, {
        xtype: 'component',
        border: true,
        style: 'margin-left:30px;margin-top:10px;',
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        name: 'periodAmount',
        columnWidth: .2,
        xtype: 'numberfield',
        style: 'margin-left:30px;'
    }, {
        name: 'periodPayAmount',
        columnWidth: .2,
        xtype: 'numberfield'
    }, {
        name: 'periodRecAmount',
        columnWidth: .2,
        xtype: 'numberfield'
    }, {
        xtype: 'component',
        border: true,
        style: 'margin-left:30px;margin-top:10px;',
        autoEl: {
            tag: 'hr'
        },
        columnWidth: 1
    }, {
        xtype: 'numberfield',
        name: 'periodUnverifyPayAmount',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.Ap'),
        labelWidth: 40,
        style: 'margin-left:20px;margin-top:10px;',
        columnWidth: .19
    }, {
        xtype: 'numberfield',
        name: 'payUnverifyAmount',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.PayUnverifyAmount'),
        labelWidth: 75,
        style: 'margin-top:10px;',
        columnWidth: .19
    }]
});
/**
 * 对账单单据类型
 */
Ext.define('Foss.woodenStatementEdit.BillTypeStore', {
    extend: 'Ext.data.Store',
    fields: ['billTypeCode', 'billTypeName'],
    data: {
        'items': [
            {
                billTypeCode: 'YFWA',
                billTypeName: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.YFWA')
            },
            {
                billTypeCode: 'YSWA',
                billTypeName: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.YSWA')
            }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
/**
 * 对账单基础信息
 */
Ext.define('Foss.woodenStatementEdit.BaseInfo', {
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
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.billType'),
        name: 'billType',
        xtype: 'combo',
        labelWidth: 75,
        columnWidth: .24,
        store: Ext.create('Foss.woodenStatementEdit.BillTypeStore'),
        queryModel: 'local',
        displayField: 'billTypeName',
        valueField: 'billTypeCode'
    }, {
        xtype: 'datefield',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createTime'),
        name: 'createTime',
        labelWidth: 75,
        format: 'Y-m-d H:i:s',
        columnWidth: .28,
        value: new Date()
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.customerCode'),
        name: 'customerCode',
        labelWidth: 75,
        columnWidth: .24
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmStatus'),
        name: 'confirmStatus',
        labelWidth: 75,
        xtype: 'combo',
        store: FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS),
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        columnWidth: .24,
        value: 0
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.periodBeginDate'),
        name: 'periodBeginDate',
        labelWidth: 75,
        xtype: 'datefield',
        format: 'Y-m-d H:i:s',
        columnWidth: .24
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),
        name: 'companyName',
        labelWidth: 75,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),
        name: 'companyCode',
        labelWidth: 75,
        hidden: true,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.customerName'),
        name: 'customerName',
        labelWidth: 75,
        columnWidth: .24
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmTime'),
        name: 'confirmTime',
        labelWidth: 75,
        xtype: 'datefield',
        format: 'Y-m-d H：i:s',
        columnWidth: .24
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.periodEndDate'),
        name: 'periodEndDate',
        labelWidth: 75,
        xtype: 'datefield',
        format: 'Y-m-d H:i:s',
        columnWidth: .24,
        value: new Date()
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),
        name: 'createOrgName',
        labelWidth: 75,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),
        name: 'createOrgCode',
        labelWidth: 75,
        hidden: true,
        columnWidth: .28
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.statementBillNo'),
        name: 'statementBillNo',
        labelWidth: 75,
        columnWidth: .24
    }, {
        fieldLabel: 'id',
        name: 'id',
        columnWidth: .22,
        hidden: true
    }, {
        xtype: 'numberfield',
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.versionNo'),
        name: 'versionNo',
        columnWidth: .22
        , hidden: true
    }, {
        fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unifiedCode'),
        labelWidth: 75,
        name: 'unifiedCode',
        columnWidth: .20
    }]
});
/**
 * 对账单明细窗体
 */
Ext.define('Foss.woodenStatementEdit.StatementEntryWindow', {
    extend: 'Ext.window.Window',
    width: stl.SCREENWIDTH * 0.9,
    modal: true,
    constrainHeader: true,
    closeAction: 'destory',
    items: [
        Ext.create('Foss.woodenStatementEdit.BaseInfo'),
        Ext.create('Foss.woodenStatementEdit.CurrentRecBillsForm'),
        Ext.create('Foss.woodenStatementEdit.CurrentPayBillsForm'),
        Ext.create('Foss.woodenStatementEdit.OperateButtonPanel'),
        Ext.create('Foss.woodenStatementEdit.woodenStatementDGrid')
    ]
});
/**
 * 对账单MODEL
 */
Ext.define('Foss.woodenStatementEdit.GridModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'statementBillNo'
    }, {
        name: 'createOrgCode'
    }, {
        name: 'createOrgName'
    }, {
        name: 'companyCode'
    }, {
        name: 'companyName'
    }, {
        name: 'unifiedCode'
    }, {
        name: 'customerCode'
    }, {
        name: 'customerName'
    }, {
        name: 'billType'
    }, {
        name: 'periodAmount',
        type: 'double'
    }, {
        name: 'periodBeginDate',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'periodEndDate',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'unpaidAmount',
        type: 'double'
    }, {
        name: 'createUserCode'
    }, {
        name: 'createUserName'
    }, {
        name: 'businessDate',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'createTime',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'modifyUserCode'
    }, {
        name: 'modifyUserName'
    }, {
        name: 'confirmUserCode'
    }, {
        name: 'confirmUserName'
    }, {
        name: 'confirmTime',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'modifyTime',
        type: 'Date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'confirmStatus'
    }, {
        name: 'notes'
    }, {
        name: 'versionNo'
    }]
});
/**
 * 按客户查询对账单
 */
writeoff.woodenStatementEdit.statementSelectByCust = function () {
    var form = this.up('form').getForm();
    //判断是否合法
    if (form.isValid()) {
        //开始时间
        writeoff.woodenStatementEdit.periodBeginDate = form.findField('periodBeginDate').getValue();
        //结束时间
        writeoff.woodenStatementEdit.periodEndDate = form.findField('periodEndDate').getValue();
        //客户编码
        writeoff.woodenStatementEdit.customerCode = form.findField('customerCode').getValue();
        //确认状态
        writeoff.woodenStatementEdit.confirmStatus = form.findField('confirmStatus').getValue();
        //结清状态
        writeoff.woodenStatementEdit.settleStatus = form.findField('settleStatus').getValue();
        //查询类型
        writeoff.woodenStatementEdit.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
        //开始日期
        var periodBeginDate = writeoff.woodenStatementEdit.periodBeginDate;
        //结束日期
        var periodEndDate = writeoff.woodenStatementEdit.periodEndDate;
        //校验日期
        if (Ext.isEmpty(periodBeginDate) || Ext.isEmpty(periodEndDate)) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
            return false;
        }
        //比较起始日期和结束日期
        var compareTwoDate = stl.compareTwoDate(periodBeginDate, periodEndDate);
        if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
            return false;
        } else if (compareTwoDate < 1) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
            return false;
        }
        //获取grid
        var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
        grid.store.loadPage(1, {
            callback: function (records, operation, success) {
                var rawData = grid.store.proxy.reader.rawData;
                if (!success) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                    return false;
                }
                //如果成功显示
                if (success) {
                    //对结果进行转化
                    var result = Ext.decode(operation.response.responseText);
                    //判断查询结果
                    if (Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementList)
                        || result.woodenStatementVo.woodenStatementDto.woodenStatementList.length == 0) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
                        return false;
                    }
                }
            }
        });

    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
        return false;
    }
}
/**
 * 按单号查询对账单
 */
writeoff.woodenStatementEdit.statementSelectByNumber = function () {
    var form = this.up('form').getForm();
    //输入单号集合
    var numbers = form.findField('numbers').getValue();
    //判断传入单号是否为null或''
    if (Ext.String.trim(numbers) != null && Ext.String.trim(numbers) != '') {
        var billNumberArray = stl.splitToArray(numbers);
        for (var i = 0; i < billNumberArray.length; i++) {
            //循环将空格等剔除掉
            if (Ext.String.trim(billNumberArray[i]) == '') {
                billNumberArray.pop(billNumberArray[i]);
            }
        }
        //判断输入单号是否超过10个
        if (billNumberArray.length > 10) {
            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
            return false;
        }
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
        return false;
    }
    //当界面校验都通过后，才执行查询方法
    if (form.isValid()) {
        writeoff.woodenStatementEdit.queryTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
        writeoff.woodenStatementEdit.numbers = numbers;
        var grid = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid();
        //查询后台
        grid.store.loadPage(1, {
            callback: function (records, operation, success) {
                var rawData = grid.store.proxy.reader.rawData;
                if (!success && !rawData.isException) {
                    var result = Ext.decode(operation.response.responseText);
                    Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                }
                if (success) {
                    //对结果进行转化
                    var result = Ext.decode(operation.response.responseText);
                    //判断查询结果
                    if (Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementList)
                        || result.woodenStatementVo.woodenStatementDto.woodenStatementList.length == 0) {
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
                        return false;
                    }
                }
            }
        });
    } else {
        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
            writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
        return false;
    }
};
/**
 * 对账单STORE
 */
Ext.define('Foss.woodenStatementEdit.GridStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.woodenStatementEdit.GridModel',
    pageSize: 50,
    proxy: {
        type: 'ajax',
        url: writeoff.realPath('queryWoodenStatement.action'),
        actionMethods: 'POST',
        reader: {
            type: 'json',
            root: 'woodenStatementVo.woodenStatementDto.woodenStatementList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var searchParams;
            if (writeoff.woodenStatementEdit.queryTabType == writeoff.STATEMENTQUERYTAB_BYCUSTOMER) {
                searchParams = {
                    'woodenStatementVo.woodenStatementDto.periodBeginDate': writeoff.woodenStatementEdit.periodBeginDate,
                    'woodenStatementVo.woodenStatementDto.periodEndDate': writeoff.woodenStatementEdit.periodEndDate,
                    'woodenStatementVo.woodenStatementDto.customerCode': writeoff.woodenStatementEdit.customerCode,
                    'woodenStatementVo.woodenStatementDto.confirmStatus': writeoff.woodenStatementEdit.confirmStatus,
                    'woodenStatementVo.woodenStatementDto.settleStatus': writeoff.woodenStatementEdit.settleStatus,
                    'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryTabType
                };
            } else {
                var numbers = stl.splitToArray(writeoff.woodenStatementEdit.numbers);
                searchParams = {
                    'woodenStatementVo.woodenStatementDto.numbers': numbers,
                    'woodenStatementVo.woodenStatementDto.queryTabType': writeoff.woodenStatementEdit.queryTabType
                };
            }
            //设置查询条件
            Ext.apply(operation, {
                params: searchParams
            });
        }
    }
});

/**
 * 对账单GRID
 */
Ext.define('Foss.woodenStatementEdit.Grid', {
    extend: 'Ext.grid.Panel',
    title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    emptyText: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
    frame: true,
    detailWin: null,
    store: Ext.create('Foss.woodenStatementEdit.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height: 500,
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    defaults: {
        align: 'center'
    },
    listeners: {
        'itemdblclick': function (th, record) {
            //对账单明细
            var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
            //对账单单号
            writeoff.woodenStatementEdit.statementBillNo = record.data.statementBillNo;
            //获取选择的对账单数据
            var selection = Ext.getCmp('T_writeoff-woodenStatementEdit_content').getGrid().getSelectionModel().getSelection();
            var me = this;
            var model = new Foss.woodenStatementEdit.GridModel(selection[0].data);
            writeoff.woodenStatementEdit.selection = selection;
            //对账单明细窗口
            var statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
            if (Ext.isEmpty(writeoff.woodenStatementEdit.statementEntryWindow)) {
                writeoff.woodenStatementEdit.statementEntryWindow = Ext.create('Foss.woodenStatementEdit.StatementEntryWindow');
                statementEntryWindow = writeoff.woodenStatementEdit.statementEntryWindow;
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
            //查询明细
            statementEntryWindow.items.items[4].store.loadPage(1, {
                callback: function (records, operation, success) {
                    var rawData = statementEntryWindow.items.items[4].store.proxy.reader.rawData;
                    if (!success) {
                        var result = Ext.decode(operation.response.responseText);
                        Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'), result.message);
                        return false;
                    }
                    //如果成功显示
                    if (success) {
                        //对结果进行转化
                        var result = Ext.decode(operation.response.responseText);
                        //判断查询结果
                        if (Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementDList)
                            || result.woodenStatementVo.woodenStatementDto.woodenStatementDList.length == 0) {
                            Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
                                writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
                            return false;
                        }
                        //设置应收应付金额
                        writeoff.woodenStatementEdit.recFormORpayForm(statementEntryWindow, result, selection);
                        //设置操作按钮
                        writeoff.woodenStatementEdit.operateButton();
                    }
                }
            });
        }
    },
    columns: [{
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.statementBillNo'),
        dataIndex: 'statementBillNo',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),
        dataIndex: 'createOrgCode',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),
        dataIndex: 'createOrgName',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),
        dataIndex: 'companyCode',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),
        dataIndex: 'companyName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unifiedCode'),
        dataIndex: 'unifiedCode',
        width: 120
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.customerCode'),
        dataIndex: 'customerCode',
        width: 150
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.customerName'),
        dataIndex: 'customerName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.billType'),
        dataIndex: 'billType',
        renderer: function (value) {
            var displayField = null;
            if (!Ext.isEmpty(value)) {
                if (value == "YSWA") {
                    displayField = "应收对账单";
                } else if (value == "YFWA") {
                    displayField = "应付对账单";
                }
            }
            return displayField;
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.periodAmount'),
        dataIndex: 'periodAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.paidAmount'),
        dataIndex: 'paidAmount',
        renderer: function (value, m, record) {
            //声明默认值为0
            value = 0;
            //获取本期发生额
            var periodAmount = record.get('periodAmount');
            //获取本期未还金额
            var unpaidAmount = record.get('unpaidAmount');
            //如果本期发生金额小于等于0，则本期已还金额默认为0，反之为本期发生额-本期未还金额
            if (!Ext.isEmpty(periodAmount) && periodAmount > 0) {
                value = stl.subtrAmountPoint(periodAmount, unpaidAmount);
            }
            //返回
            return value;
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unpaidAmount'),
        dataIndex: 'unpaidAmount'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createUserCode'),
        dataIndex: 'createUserCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createUserName'),
        dataIndex: 'createUserName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.createTime'),
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.modifyUserCode'),
        dataIndex: 'modifyUserCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.modifyUserName'),
        dataIndex: 'modifyUserName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmUserCode'),
        dataIndex: 'confirmUserCode'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmUserName'),
        dataIndex: 'confirmUserName'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmTime'),
        dataIndex: 'confirmTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.modifyTime'),
        dataIndex: 'modifyTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmStatus'),
        dataIndex: 'confirmStatus',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
            return displayField;
        }

    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.notes'),
        dataIndex: 'notes'
    }, {
        header: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.versionNo'),
        dataIndex: 'versionNo'
    }],
    initComponent: function () {
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaultType: 'textfield',
            defaults: {
                margin: '5 0 5 3',
                readOnly: true,
                labelWidth: 60
            },
            items: [{
                xtype: 'standardpaging',
                store: me.store,
                columnWidth: .9,
                pageSize: 50,
                plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    //设置分页记录最大值，防止输入过大的数值
                    maximumSize: 1000,
                    sizeList: [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]]
                })
            }]
        }];
        me.callParent();
    }
});

/**
 * 结账状态store
 */
Ext.define('Foss.statementbill.SettleStatusStore', {
    extend: 'Ext.data.Store',
    fields: ['name', 'value'],
    data: {
        'items': [
            {name: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.all'), value: ''},
            {
                name: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.settled'),
                value: writeoff.SETTLESTATUS_Y
            },
            {
                name: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSettle'),
                value: writeoff.SETTLESTATUS_N
            }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

/**
 * 对账单查询页签
 */
Ext.define('Foss.woodenStatementEdit.QueryTab', {
    extend: 'Ext.tab.Panel',
    frame: false,
    bodyCls: 'autoHeight',
    cls: 'innerTabPanel',
    height: 250,
    items: [{
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.woodenStatementByCustomer'),
        tabConfig: {
            width: 120
        },
        layout: 'hbox',
        items: [{
            xtype: 'form',
            width: 920,
            layout: 'column',
            defaults: {
                margin: '10 10 0 10',
                labelWidth: 80
            },
            items: [{
                xtype: 'datefield',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.periodBeginDate'),
                allowBlank: false,
                name: 'periodBeginDate',
                columnWidth: .3,
                format: 'Y-m-d',
                value: stl.getTargetDate(new Date(), -1)
            }, {
                xtype: 'datefield',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.periodEndDate'),
                allowBlank: false,
                name: 'periodEndDate',
                format: 'Y-m-d',
                columnWidth: .3,
                value: stl.getTargetDate(new Date(), +1)
            }, {
                xtype: 'dynamicPackagingSupplierSelector',
                listWidth: 300,
                name: 'customerCode',
                active: 'Y',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.customerDetial'),
                columnWidth: .3
            }, {
                xtype: 'combobox',
                name: 'confirmStatus',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.confirmStatus'),
                store: FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS, null, {
                    'valueCode': '',
                    'valueName': writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.all')
                }),
                queryModel: 'local',
                displayField: 'valueName',
                valueField: 'valueCode',
                value: '',
                columnWidth: .3,
                forceSelection: true,
                listeners: {
                    change: stl.comboSelsct
                }
            }, {
                xtype: 'combobox',
                name: 'settleStatus',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
                store: Ext.create('Foss.statementbill.SettleStatusStore'),
                queryModel: 'local',
                displayField: 'name',
                valueField: 'value',
                value: '',
                columnWidth: .3,
                forceSelection: true,
                listeners: {
                    change: stl.comboSelsct
                }
            }, {
                border: 1,
                xtype: 'container',
                columnWidth: 1,
                defaultType: 'button',
                layout: 'column',
                items: [{
                    text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.reset'),
                    columnWidth: .08,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    xtype: 'container',
                    border: false,
                    columnWidth: .74,
                    html: '&nbsp;'
                }, {
                    text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .08,
                    handler: writeoff.woodenStatementEdit.statementSelectByCust
                }]
            }]
        }]
    }, {
        title: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
        tabConfig: {
            width: 120
        },
        layout: 'fit',
        items: [{
            xtype: 'form',
            layout: 'column',
            defaults: {
                margin: '5 5 5 5'
            },
            items: [{
                xtype: 'textareafield',
                fieldLabel: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
                allowBlank: false,
                columnWidth: .65,
                regex: /^((DZ)?[0-9]{7,10}[;,])*((DZ)?[0-9]{7,10}[;,]?)$/i,
                labelWidth: 70,
                labelAlign: 'right',
                name: 'numbers',
                autoScroll: true,
                height: 104
            }, {
                xtype: 'container',
                columnWidth: .35,
                layout: 'vbox',
                items: [{
                    xtype: 'component',
                    padding: '0 0 0 10',
                    autoEl: {
                        tag: 'div',
                        html: '<span style="color:red;">' + writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.queryByStatementBillNo') + '</span>'
                    }
                }]
            }, {
                xtype: 'container',
                columnWidth: 1,
                layout: 'column',
                defaultType: 'button',
                defaults: {
                    width: 80
                },
                items: [{
                    text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.reset'),
                    columnWidth: .075,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    xtype: 'container',
                    border: false,
                    html: '&nbsp;',
                    columnWidth: .5
                }, {
                    text: writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .075,
                    handler: writeoff.woodenStatementEdit.statementSelectByNumber
                }]
            }]
        }]
    }]
});

Ext.onReady(function () {
    Ext.Ajax.timeout = 60000 * 30;
    //查询表单
    var queryTab = Ext.create('Foss.woodenStatementEdit.QueryTab');
    //查询结果列表
    var grid = Ext.create('Foss.woodenStatementEdit.Grid');
    //创建明细窗口
    if (Ext.isEmpty(writeoff.woodenStatementEdit.statementEntryWindow)) {
        writeoff.woodenStatementEdit.statementEntryWindow = Ext.create('Foss.woodenStatementEdit.StatementEntryWindow');
    }
    //创建panel
    Ext.create('Ext.panel.Panel', {
        id: 'T_writeoff-woodenStatementEdit_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getGrid: function () {
            return grid;
        },
        getTab: function () {
            return queryTab;
        },
        items: [queryTab, grid],
        renderTo: 'T_writeoff-woodenStatementEdit-body'
    });
});