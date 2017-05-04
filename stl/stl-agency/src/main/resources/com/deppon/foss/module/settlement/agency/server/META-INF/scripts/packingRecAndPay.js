/* 查询类别 */
agency.packingRecAndPay.QUERY_BY_ACCOUNT = 'TAD';
agency.packingRecAndPay.QUERY_BY_WAYBILL_NO = 'WB';
agency.packingRecAndPay.QUERY_BY_BILL_NO = 'TB';

agency.packingRecAndPay.MAX_RANGE_DAYS = 31;

/* 存个列表界面 方便用 */
agency.packingRecAndPay.gridPanel = {};

agency.packingRecAndPay.oneBar;
agency.packingRecAndPay.twoBar;
agency.packingRecAndPay.threeBar;

/*
 * 让框架加载i18n()
 */
{
    agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.noDataSelected');
    agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.success');
    agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.billNosNotFound');
    agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.exportFailed');
}

agency.packingRecAndPay.reloadGrid = function () {
    var grid = agency.packingRecAndPay.gridPanel;
    var store = agency.packingRecAndPay.gridPanel.getStore();
    store.setSubmitParams(params);
    var totalCount = agency.packingRecAndPay.oneBar.child('#totalCount');
    var recTotalAmount =  agency.packingRecAndPay.twoBar.child('#recTotalAmount');
    var recTotalUnverifyAmount = agency.packingRecAndPay.twoBar.child('#recTotalUnverifyAmount');
    var recTotalVerifyAmount = agency.packingRecAndPay.twoBar.child('#recTotalVerifyAmount');
    var payTotalAmount = agency.packingRecAndPay.threeBar.child('#payTotalAmount');
    var payTotalUnverifyAmount = agency.packingRecAndPay.threeBar.child('#payTotalUnverifyAmount');
    var payTotalVerifyAmount = agency.packingRecAndPay.threeBar.child('#payTotalVerifyAmount');
    store.loadPage(1, {
        callback: function (records, operation, success) {

            // 抛出异常
            var rawData = grid.store.proxy.reader.rawData;
            if (!success && !rawData.isException) {
                store.removeAll();
                agency.packingRecAndPay.alert(rawData.message);
                return false;
            }

            // 正常返回
            if (success) {
                var result = Ext.decode(operation.response.responseText);
                totalCount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.totalCount') + result.packingRecAndPayVo.packingRecAndPayResultDto.totalCount);
                recTotalAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalAmount);
                recTotalUnverifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalUnverifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalUnverifyAmount);
                recTotalVerifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalVerifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalVerifyAmount);
                payTotalAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalAmount);
                payTotalUnverifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalUnverifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalUnverifyAmount);
                payTotalVerifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalVerifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalVerifyAmount);
                if (result.packingRecAndPayVo.packingRecAndPayResultDto.packingRecAndPayDtos.length > 0) {
                    grid.show();
                } else {
                    store.removeAll();
                    Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                        .i18n('foss.stl.agency.packingRecAndPay.noDataFound'));
                    return false;
                }
            }
        }
    });
};

/**
 * 恶心的i18n
 */
agency.packingRecAndPay.alert = function (msgStr) {
    var result = agency.packingRecAndPay.i18n(msgStr);
    if (!result) {
        Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), msgStr);
    } else {
        Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay.i18n(msgStr));
    }
};

/**
 * 查询方法
 */
agency.packingRecAndPay.query = function (form, queryType) {

    if (!form.isValid()) {
        Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
            .i18n('foss.stl.agency.packingRecAndPay.noInput'));
        return false;
    }
    var params = form.getValues();
    if (queryType == agency.packingRecAndPay.QUERY_BY_BILL_NO) {
        var billNos = params['packingRecAndPayVo.packingRecAndPayQueryDto.billNos'].match(/Y[FS]\d{8,}/g);
        if (billNos == null || billNos.length == 0) {
            agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.billNosNotFound');
            return false;
        } else if (billNos != null && billNos.length > 10) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.MoreThanTenNoFound'));
            return false;
        } else {
            Ext.apply(params, {
                'packingRecAndPayVo.packingRecAndPayQueryDto.billNos': billNos
            });
        }
    }

    // 处理部门信息
    if (params['packingRecAndPayVo.packingRecAndPayQueryDto.department'] == stl.currentDept.name) {
        Ext.apply(params, {
            'packingRecAndPayVo.packingRecAndPayQueryDto.department': stl.currentDeptCode
        });
    }
    // 查询方式
    Ext.apply(params, {
        'packingRecAndPayVo.packingRecAndPayQueryDto.queryType': queryType
    });

    // 校验
    if (!agency.packingRecAndPay.validateParams(params, queryType)) {
        return false;
    }
    var grid = agency.packingRecAndPay.gridPanel;
    var store = agency.packingRecAndPay.gridPanel.getStore();
    store.setSubmitParams(params);
    var totalCount = agency.packingRecAndPay.oneBar.child('#totalCount');
    var recTotalAmount =  agency.packingRecAndPay.twoBar.child('#recTotalAmount');
    var recTotalUnverifyAmount = agency.packingRecAndPay.twoBar.child('#recTotalUnverifyAmount');
    var recTotalVerifyAmount = agency.packingRecAndPay.twoBar.child('#recTotalVerifyAmount');
    var payTotalAmount = agency.packingRecAndPay.threeBar.child('#payTotalAmount');
    var payTotalUnverifyAmount = agency.packingRecAndPay.threeBar.child('#payTotalUnverifyAmount');
    var payTotalVerifyAmount = agency.packingRecAndPay.threeBar.child('#payTotalVerifyAmount');
    store.loadPage(1, {
        callback: function (records, operation, success) {

            // 抛出异常
            var rawData = grid.store.proxy.reader.rawData;
            if (!success && !rawData.isException) {
                store.removeAll();
                Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), rawData.message);
                return false;
            }

            // 正常返回
            if (success) {
                var result = Ext.decode(operation.response.responseText);
                totalCount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.totalCount') + result.packingRecAndPayVo.packingRecAndPayResultDto.totalCount);
                recTotalAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalAmount);
                recTotalUnverifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalUnverifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalUnverifyAmount);
                recTotalVerifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalVerifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.recTotalVerifyAmount);
                payTotalAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalAmount);
                payTotalUnverifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalUnverifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalUnverifyAmount);
                payTotalVerifyAmount.setText(agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalVerifyAmount') + result.packingRecAndPayVo.packingRecAndPayResultDto.payTotalVerifyAmount);
                if (result.packingRecAndPayVo.packingRecAndPayResultDto.packingRecAndPayDtos.length > 0) {
                    grid.show();
                } else {
                    store.removeAll();
                    Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                        .i18n('foss.stl.agency.packingRecAndPay.noDataFound'));
                    return false;
                }
            }
        }
    });
};

/**
 * 校验查询参数
 */
agency.packingRecAndPay.validateParams = function (params, queryType) {
    if (queryType == null || queryType == '') {
        Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
            .i18n('foss.stl.agency.packingRecAndPay.jsError'));
        return false;
    }

    if (queryType == agency.packingRecAndPay.QUERY_BY_ACCOUNT) {
        if (!(params['packingRecAndPayVo.packingRecAndPayQueryDto.beginAccountDate'] || params['packingRecAndPayVo.packingRecAndPayQueryDto.endAccountDate'])) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.noDateInpput'));
            return false;
        }
        if (params['packingRecAndPayVo.packingRecAndPayQueryDto.beginAccountDate'] > params['packingRecAndPayVo.packingRecAndPayQueryDto.endAccountDate']) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.beginDateGreaterThanEndDate'));
            return false;
        }

        if (stl.compareTwoDate(params['packingRecAndPayVo.packingRecAndPayQueryDto.beginAccountDate'],
            params['packingRecAndPayVo.packingRecAndPayQueryDto.endAccountDate']) > agency.packingRecAndPay.MAX_RANGE_DAYS) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.dateRangeGreaterThanOneMonth1')
                + agency.packingRecAndPay.MAX_RANGE_DAYS
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.dateRangeGreaterThanOneMonth2'));
            return false;
        }

        if (!(params['packingRecAndPayVo.packingRecAndPayQueryDto.bigArea'] || params['packingRecAndPayVo.packingRecAndPayQueryDto.smallArea'] || params['packingRecAndPayVo.packingRecAndPayQueryDto.department'])) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.noAreaOrDept'));
            return false;
        }

    } else if (queryType == agency.packingRecAndPay.QUERY_BY_BILL_NO) {
        if (!params['packingRecAndPayVo.packingRecAndPayQueryDto.billNos']) {
            Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                .i18n('foss.stl.agency.packingRecAndPay.billNosNotFound'));
            return false;
        }
    }
    return true;
};

/**
 * 导出
 */
agency.packingRecAndPay.export = function (form, queryType) {

    //创建一个form
    if(!Ext.fly('downloadPackingAttachFileForm')){
        var frm = document.createElement('form');
        frm.id = 'downloadPackingAttachFileForm';
        frm.style.display = 'none';
        document.body.appendChild(frm);
    }

    Ext.Ajax.request({
        url : agency.realPath('exportPackingOtherRecAndPay.action'),
        form : Ext.fly('downloadPackingAttachFileForm'),
        method : 'post',
        params : agency.packingRecAndPay.gridPanel.getStore().submitParams,
        isUpload : true,
        success : function(response) {
            var result = Ext.decode(response.responseText);
            // 如果异常信息有值，则弹出提示
            if (!Ext.isEmpty(result.errorMessage)) {
                Ext.Msg.alert(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), result.errorMessage);
                return false;
            }
        }
    });
}
/**
 * 作废/红冲
 */
agency.packingRecAndPay.invalid = function (selectedRecords) {
    if (selectedRecords.length == 0) {
        agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.noDataSelected');
        return false;
    }

    var billNos = [];
    for (var i = 0; i < selectedRecords.length; i++) {
        if (selectedRecords[i].data.auditStatus == agency.packingRecAndPay.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE) {
            var msg = selectedRecords[i].data.billNo
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.invalidApprovedBill');
            agency.packingRecAndPay.alert(msg);
            return false;
        }
        if (selectedRecords[i].data.statementNo.length == 0) {
            msg = selectedRecords[i].data.billNo
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.invalidBillInStatementBill');
            agency.packingRecAndPay.alert(msg);
            return false;
        }
        billNos[i] = selectedRecords[i].data.billNo;
    }
    Ext.Ajax.request({
        url: agency.realPath('packingRecAndPayInvalid.action'),
        actionMethods: 'post',
        params: {
            'packingRecAndPayVo.packingRecAndPayQueryDto.billNos': billNos
        },
        callback: function (options, success, response) {
            var data = Ext.decode(response.responseText);
            if (data.success) {
                agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.success');
                agency.packingRecAndPay.reloadGrid();

            } else {
                agency.packingRecAndPay.alert(data.message);
                return false;
            }
        }
    });
}
/**
 * 反审核
 */
agency.packingRecAndPay.reverseApprove = function (selectedRecords) {
    if (selectedRecords.length == 0) {
        agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.noDataSelected');
        return false;
    }
    var billNos = new Array();
    for (var i = 0; i < selectedRecords.length; i++) {
        if (selectedRecords[i].data.auditStatus == agency.packingRecAndPay.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT) {
            var msg = selectedRecords[i].data.billNo
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.reverseApproveBillStatusIsNotAudit');
            agency.packingRecAndPay.alert(msg);
            return false;
        }
        if (selectedRecords[i].data.statementNo.length == 0) {
            var msg = selectedRecords[i].data.billNo
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.reverseApproveBillInStatementBill');
            agency.packingRecAndPay.alert(msg);
            return false;
        }
        billNos[i] = selectedRecords[i].data.billNo;
    }
    Ext.Ajax.request({
        url: agency.realPath('packingRecAndPayReverseApprove.action'),
        actionMethods: 'post',
        params: {
            'packingRecAndPayVo.packingRecAndPayQueryDto.billNos': billNos
        },
        callback: function (options, success, response) {
            var data = Ext.decode(response.responseText);
            if (data.success) {
                agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.success');
                agency.packingRecAndPay.reloadGrid();

            } else {
                agency.packingRecAndPay.alert(data.message);
                return false;
            }
        }
    });
}

/**
 * 审核
 */
agency.packingRecAndPay.approve = function (selectedRecords) {
    if (selectedRecords.length == 0) {
        agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.noDataSelected');
        return false;
    }
    var billNos = new Array();
    for (var i = 0; i < selectedRecords.length; i++) {
        if (selectedRecords[i].data.auditStatus == agency.packingRecAndPay.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE) {
            var msg = selectedRecords[i].data.billNo
                + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.approveBillStatusIsAudit');
            agency.packingRecAndPay.alert(msg);
            return false;
        }
        billNos[i] = selectedRecords[i].data.billNo;
    }
    Ext.Ajax.request({
        url: agency.realPath('packingRecAndPayApprove.action'),
        actionMethods: 'post',
        params: {
            'packingRecAndPayVo.packingRecAndPayQueryDto.billNos': billNos
        },
        callback: function (options, success, response) {
            var data = Ext.decode(response.responseText);
            if (data.success) {
                agency.packingRecAndPay.alert('foss.stl.agency.packingRecAndPay.success');
                agency.packingRecAndPay.reloadGrid();

            } else {
                agency.packingRecAndPay.alert(data.message);
                return false;
            }
        }
    });
};

/**
 * 查询界面
 */
Ext.define('agency.packingRecAndPay.queryTabPanel', {
    extend: 'Ext.tab.Panel',
    requires: ['Ext.tab.Panel', 'Ext.tab.Tab', 'Ext.form.field.Date', 'Ext.form.field.ComboBox', 'Ext.form.field.TextArea'],
    frameHeader: false,
    frame: false,
    height: 213,
    cls: 'innerTabPanel',
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    title: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.queryByAccountDate'),
                    tabConfig: {
                        width: 150
                    },
                    layout: 'fit',
                    items: [
                        {
                            itemId: agency.packingRecAndPay.QUERY_BY_ACCOUNT,
                            xtype: 'form',
                            layout: 'column',
                            bodyPadding: '20px 5px 20px 5px',
                            defaults: {
                                columnWidth: .25
                            },
                            items: [
                                {
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.beginAccountDate',
                                    xtype: 'datefield',
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.beginTime'),
                                    format: 'Y-m-d',
                                    editable: false,
                                    value: Ext.Date.add(new Date(), Ext.Date.DAY, 1 - agency.packingRecAndPay.MAX_RANGE_DAYS)
                                },
                                {
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.endAccountDate',
                                    xtype: 'datefield',
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.endTime'),
                                    format: 'Y-m-d',
                                    value: new Date(),
                                    editable: false,
                                    maxValue: new Date()
                                },
                                {
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.status',
                                    xtype: 'combo',
                                    editable: false,
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.status'),
                                    store: FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYABLE__APPROVE_STATUS, null, {
                                        'valueCode': '',
                                        'valueName': '全部'
                                    }),
                                    displayField: 'valueName',
                                    valueField: 'valueCode',
                                    value: ''
                                },
                                {
                                    xtype: 'container',
                                    height: 24
                                },
                                {
                                    xtype: 'linkagecomboselector',
                                    eventType: ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
                                    itemId: 'Foss_baseinfo_BigRegion_ID',
                                    store: Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.bigArea'),
                                    value: '',
                                    minChars: 0,
                                    displayField: 'name',// 显示名称
                                    valueField: 'code',
                                    queryParam: 'commonOrgVo.name',
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.bigArea',
                                    allowBlank: true,
                                    listWidth: 300,// 设置下拉框宽度
                                    isPaging: true
                                    // 分页
                                }    ,
                                {
                                    xtype: 'linkagecomboselector',
                                    itemId: 'Foss_baseinfo_SmallRegion_ID',
                                    eventType: ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
                                    store: Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.smallArea',
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.smallArea'),
                                    parentParamsAndItemIds: {
                                        'commonOrgVo.code': 'Foss_baseinfo_BigRegion_ID'
                                    },// 此处城市不需要传入
                                    minChars: 0,
                                    displayField: 'name',// 显示名称
                                    valueField: 'code',
                                    minChars: 0,
                                    queryParam: 'commonOrgVo.name',
                                    allowBlank: true,
                                    listWidth: 300,// 设置下拉框宽度
                                    isPaging: true
                                },
                                {
                                    xtype: 'dynamicorgcombselector',
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.department',
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.department'),
                                    allowblank: true,
                                    listWidth: 300,// 设置下拉框宽度
                                    isPaging: true,
                                    value: stl.currentDept.name
                                },
                                {
                                    xtype: 'container',
                                    height: 24
                                },
                                {
                                    xtype: 'dynamicPackagingSupplierSelector',
                                    listWidth: 300,
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.consumer',
                                    active: 'Y',
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.customer')
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.75,
                                    height: 24
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'button',
                                    columnWidth: 0.1,
                                    text: agency.packingRecAndPay.i18n('foss.stl.agency.common.reset'),
                                    handler: function () {
                                        var form = this.up('form').getForm();
                                        form.reset();
                                        form.findField('packingRecAndPayVo.packingRecAndPayQueryDto.beginAccountDate').setValue(Ext.Date.add(
                                            new Date(), Ext.Date.DAY, 1 - agency.packingRecAndPay.MAX_RANGE_DAYS));
                                        form.findField('packingRecAndPayVo.packingRecAndPayQueryDto.endAccountDate').setValue(new Date());
                                        form.findField('packingRecAndPayVo.packingRecAndPayQueryDto.department').setValue(stl.currentDept.name);
                                    }
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.55,
                                    height: 24
                                },
                                {
                                    xtype: 'button',
                                    cls: 'yellow_button',
                                    columnWidth: 0.1,
                                    text: agency.packingRecAndPay.i18n('foss.stl.agency.common.query'),
                                    handler: function () {
                                        agency.packingRecAndPay.query(this.up('form').getForm(), agency.packingRecAndPay.QUERY_BY_ACCOUNT);
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    itemId: agency.packingRecAndPay.QUERY_BY_BILL_NO,
                    title: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.queryByPayableNo'),
                    tabConfig: {
                        xtype: 'tab',
                        width: 150
                    },
                    layout: 'fit',
                    bodyPadding: 5,
                    items: [
                        {
                            xtype: 'form',
                            layout: 'column',
                            bodyPadding: '20px 5px 20px 5px',
                            items: [
                                {
                                    xtype: 'textareafield',
                                    name: 'packingRecAndPayVo.packingRecAndPayQueryDto.billNos',
                                    columnWidth: 0.75,
                                    regex: /^[\.\;\,\s]*(Y[SF]\d{8,}[\.\;\,\s]*)+$/,
                                    emptyText: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.emptyText'),
                                    fieldLabel: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.payableNos'),
                                    checkChangeBuffer: 70
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.25,
                                    height: 67
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 1,
                                    height: 20
                                },
                                {
                                    xtype: 'button',
                                    columnWidth: 0.1,
                                    text: agency.packingRecAndPay.i18n('foss.stl.agency.common.reset'),
                                    handler: function () {
                                        this.up('form').getForm().reset();
                                    }
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.55,
                                    height: 24
                                },
                                {
                                    xtype: 'button',
                                    columnWidth: 0.1,
                                    cls: 'yellow_button',
                                    text: agency.packingRecAndPay.i18n('foss.stl.agency.common.query'),
                                    handler: function () {
                                        agency.packingRecAndPay.query(this.up('form').getForm(), agency.packingRecAndPay.QUERY_BY_BILL_NO);
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    }
});

Ext.define('agency.packingRecAndPay.Model', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'billNo'
        },
        {
            name: 'waybillNo'
        },
        {
            name: 'billTime'
        },
        {
            name: 'collectionOrPayableType'
        },
        {
            name: 'orgCode'
        },
        {
            name: 'orgName'
        },
        {
            name: 'customerCode'
        },
        {
            name: 'customerName'
        },
        {
            name: 'statementNo'
        },
        {
            name: 'auditStatus'
        },
        {
            name: 'amount'
        },
        {
            name: 'verifyAmount'
        },
        {
            name: 'unverifyAmount'
        },
        {
            name: 'businessDate',
            type: 'Date',
            convert: stl.longToDateConvert
        },
        {
            name: 'accountDate',
            type: 'Date',
            convert: stl.longToDateConvert
        },
        {
            name: 'active'
        },
        {
            name: 'isRedBack'
        },
        {
            name: 'notes'
        },
        {
            name: 'id'
        }
    ]
});
Ext.define('agency.packingRecAndPay.store', {
    extend: 'Ext.data.Store',
    model: 'agency.packingRecAndPay.Model',
    sorters: {
        property: 'createTime',
        direction: 'DESC'
    },
    id: 'agency.packingRecAndPay.store_ID',
    proxy: {
        type: 'ajax',
        url: agency.realPath('queryPackingRecAndPay.action'),
        actionMethods: 'post',
        reader: {
            type: 'json',
            root: 'packingRecAndPayVo.packingRecAndPayResultDto.packingRecAndPayDtos',
            totalProperty: 'packingRecAndPayVo.packingRecAndPayResultDto.totalCount'
        }
    },
    submitParams: {},
    setSubmitParams: function (submitParams) {
        this.submitParams = submitParams;
    },
    listeners: {
        'beforeload': function (store, operation, eOpts) {
            Ext.apply(this.submitParams, {
                start: operation.start,
                limit: operation.limit,
                page: operation.page
            });
            Ext.applyIf(operation, {
                params: this.submitParams
            });
        }
    }
});

/*
 * 查询结果panel
 */
Ext.define('agency.packingRecAndPay.gridPanel', {
    extend: 'Ext.grid.Panel',
    title: '单据信息',
    store: Ext.create('agency.packingRecAndPay.store'),
    cls: 'innerTabPanel',
    frame: true,
    selModel: Ext.create('Ext.selection.CheckboxModel', {
        model: 'MULTI'
    }),
    selType: 'rowmodel',
    height: 600,
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'actioncolumn',
                    header: agency.packingRecAndPay.i18n('foss.stl.agency.common.actionColumn'),
                    width: 90,
                    align: 'center',
                    items: [
                        {
                            iconCls: 'deppon_icons_cancel',
                            tooltip: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.invalid'),
                            handler: function (view, rowIndex, colIndex, item, e, record) {
                                var records = [];
                                records[0] = record;
                                Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                    .i18n('foss.stl.agency.packingRecAndPay.confirmOption')
                                    + ':' + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.invalid'), function (optional) {
                                    if (optional == 'yes') {
                                        agency.packingRecAndPay.invalid(records);
                                    }
                                });
                            },
                            getClass: function () {
                                if (!agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayInvalid.action')) {
                                    return 'statementBill_hide';
                                } else {
                                    return 'deppon_icons_cancel';
                                }
                            }
                        },
                        {
                            iconCls: 'foss_icons_stl_fauditing',
                            tooltip: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.reverseApprove'),
                            handler: function (view, rowIndex, colIndex, item, e, record) {
                                var records = [];
                                records[0] = record;
                                Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                        .i18n('foss.stl.agency.packingRecAndPay.confirmOption')
                                        + ':' + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.reverseApprove'),
                                    function (optional) {
                                        if (optional == 'yes') {
                                            agency.packingRecAndPay.reverseApprove(records);
                                        }
                                    });
                            },
                            getClass: function (v, m, r, rowIndex) {
                                if (!agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayReverseApprove.action')) {
                                    return 'statementBill_hide';
                                } else {
                                    return 'foss_icons_stl_fauditing';
                                }
                            }
                        },
                        {
                            iconCls: 'foss_icons_stl_auditing',
                            tooltip: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.approve'),
                            handler: function (view, rowIndex, colIndex, item, e, record) {
                                var records = new Array();
                                records[0] = record;
                                Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                    .i18n('foss.stl.agency.packingRecAndPay.confirmOption')
                                    + ':' + agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.approve'), function (optional) {
                                    if (optional == 'yes') {
                                        agency.packingRecAndPay.approve(records);
                                    }
                                });
                            },
                            getClass: function () {
                                if (!agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayApprove.action')) {
                                    return 'statementBill_hide';
                                } else {
                                    return 'foss_icons_stl_auditing';
                                }
                            }
                        }
                    ]
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.billNo'),
                    dataIndex: 'billNo'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.collectionOrPayableType'),
                    dataIndex: 'collectionOrPayableType',
                    renderer: function (value, metaData, record) {
                        if (record.data.billNo.match(/YS\d{8,}/)) {
                            return FossDataDictionary.getDataByTermsCode(settlementDict.BILL_RECEIVABLE__COLLECTION_TYPE, value)[0].valueName;
                        } else {
                            return FossDataDictionary.getDataByTermsCode(settlementDict.BILL_PAYABLE__PAYABLE_TYPE, value)[0].valueName;
                        }
                    }
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.waybillNo'),
                    dataIndex: 'waybillNo',
                    hidden: true
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.billTime'),
                    dataIndex: 'billTime'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.orgCode'),
                    dataIndex: 'orgCode'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.orgName'),
                    dataIndex: 'orgName'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.customerCode'),
                    dataIndex: 'customerCode'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.customerName'),
                    dataIndex: 'customerName'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.statementNo'),
                    dataIndex: 'statementNo'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.auditStatus'),
                    dataIndex: 'auditStatus',
                    renderer: function (value) {
                        return FossDataDictionary.getDataByTermsCode(settlementDict.BILL_PAYABLE__APPROVE_STATUS, value)[0].valueName;
                    }
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.amount'),
                    dataIndex: 'amount'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.verifyAmount'),
                    dataIndex: 'verifyAmount'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.unverifyAmount'),
                    dataIndex: 'unverifyAmount'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.businessDate'),
                    dataIndex: 'businessDate',
                    xtype: 'datecolumn',
                    format: 'Y-m-d H:i:s'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.accountDate'),
                    dataIndex: 'accountDate',
                    xtype: 'datecolumn',
                    format: 'Y-m-d H:i:s'
                },

                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.active'),
                    dataIndex: 'active',
                    renderer: function (value) {
                        return FossDataDictionary.getDataByTermsCode('FOSS_BOOLEAN', value)[0].valueName;
                    }
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.isRedBack'),
                    dataIndex: 'isRedBack',
                    renderer: function (value) {
                        return FossDataDictionary.getDataByTermsCode('FOSS_BOOLEAN', value)[0].valueName;
                    }
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.notes'),
                    dataIndex: 'notes'
                },
                {
                    text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.id'),
                    dataIndex: 'id',
                    hidden: true
                }
            ],
            tbar:[
                '->',
                {
                    text:agency.packingRecAndPay.i18n('foss.stl.agency.common.export'),
                    handler:function(){
                        Ext.MessageBox.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'),agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.confirmExport'),function(btn){
                            if(btn =='yes'){
                                agency.packingRecAndPay.export();
                            }
                        } );

                    }
                }
            ],
            bbar: {xtype: 'container',
                items: [
                    agency.packingRecAndPay.oneBar = Ext.create('Ext.toolbar.Toolbar', {
                        padding: '0 0 0 10',
                        items: [
                            {
                                xtype: 'tbtext',
                                itemId: 'totalCount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.totalCount'),
                                width: 220
                            },
                            '->',
                            {
                                width: 90,
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.invalid'),
                                disabled: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayInvalid.action'),
                                hidden: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayInvalid.action'),
                                handler: function () {
                                    Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                        .i18n('foss.stl.agency.packingRecAndPay.confirmOption'), function (optional) {
                                        if (optional == 'yes') {
                                            agency.packingRecAndPay.invalid(agency.packingRecAndPay.gridPanel.getSelectionModel().getSelection())
                                        }
                                    });
                                }
                            },
                            {
                                xtype: 'component',
                                width: 10
                            },
                            {
                                width: 90,
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.reverseApprove'),
                                disabled: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayReverseApprove.action'),
                                hidden: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayReverseApprove.action'),
                                handler: function () {
                                    Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                        .i18n('foss.stl.agency.packingRecAndPay.confirmOption'), function (optional) {
                                        if (optional == 'yes') {
                                            agency.packingRecAndPay.reverseApprove(agency.packingRecAndPay.gridPanel.getSelectionModel()
                                                .getSelection())
                                        }
                                    });
                                }
                            },
                            {
                                xtype: 'component',
                                width: 10
                            },
                            {
                                width: 90,
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.applyAuditPanel.approve'),
                                disabled: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayApprove.action'),
                                hidden: !agency.packingRecAndPay.isPermission('/stl-web/#!agency/packingRecAndPayApprove.action'),
                                handler: function () {
                                    Ext.Msg.confirm(agency.packingRecAndPay.i18n('foss.stl.agency.common.alert'), agency.packingRecAndPay
                                        .i18n('foss.stl.agency.packingRecAndPay.confirmOption'), function (optional) {
                                        if (optional == 'yes') {
                                            agency.packingRecAndPay.approve(agency.packingRecAndPay.gridPanel.getSelectionModel().getSelection())
                                        }
                                    });
                                }
                            }
                        ]
                    }), agency.packingRecAndPay.twoBar = Ext.create('Ext.toolbar.Toolbar', {
                        padding: '0 0 0 10',
                        items: [
                            {
                                xtype: 'tbtext',
                                itemId: 'recTotalAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalAmount'),
                                width: 220
                            },
                            {
                                xtype: 'tbtext',
                                itemId: 'recTotalUnverifyAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalUnverifyAmount'),
                                width: 220
                            },
                            {   xtype: 'tbtext',
                                itemId: 'recTotalVerifyAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.recTotalUnverifyAmount'),
                                width: 220
                            }
                        ]
                    }),
                    agency.packingRecAndPay.threeBar = Ext.create('Ext.toolbar.Toolbar', {padding: '0 0 0 10',
                        items: [
                            {
                                xtype: 'tbtext',
                                itemId: 'payTotalAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalAmount'),
                                width: 220
                            },
                            {
                                xtype: 'tbtext',
                                itemId: 'payTotalUnverifyAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalUnverifyAmount'),
                                width: 220
                            },
                            {
                                xtype: 'tbtext',
                                itemId: 'payTotalVerifyAmount',
                                text: agency.packingRecAndPay.i18n('foss.stl.agency.packingRecAndPay.gridPanel.payTotalUnverifyAmount'),
                                width: 220
                            }
                        ]
                    }),
                    Ext.create('Ext.toolbar.Toolbar', {
                        items: [
                            {
                                xtype: 'standardpaging',
                                store: me.store,
                                pageSize: 25,
                                columnWidth: 0.9,
                                plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                                    // 设置分页记录最大值，防止输入过大的数值
                                    maximumSize: 500
                                })
                            }
                        ]
                    })
                ]}

        });
        me.callParent(arguments);
    }
});

Ext.onReady(function () {
    Ext.QuickTips.init();
    Ext.create('Ext.panel.Panel', {
        id: 'T_agency-packingRecAndPayPage_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        border: false,
        layout: 'auto',
        items: [Ext.create('agency.packingRecAndPay.queryTabPanel'),
            agency.packingRecAndPay.gridPanel = Ext.create('agency.packingRecAndPay.gridPanel')],
        renderTo: 'T_agency-packingRecAndPayPage-body'
    });
});
