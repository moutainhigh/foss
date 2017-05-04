/* 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.cashCashierConfirm.billCashCashierConfirmAlert = function (message, yesFn, noFn) {
    Ext.Msg.confirm(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), message, function (o) {
        if (o == 'yes') {
            yesFn();
        } else {
            noFn();
        }
    });
};

pay.cashCashierConfirm.QUERY_BYDATE = 'TD';//按客户查询
pay.cashCashierConfirm.QUERY_BYNUMBER = 'DCP';//按单号查询
pay.cashCashierConfirm.QUERY_BYWAYBILLNUMBER = 'WB';//按来源单号查询
pay.cashCashierConfirm.QUERY_BYBATCHNO = 'BB';//按银联交易流水号查询
pay.cashCashierConfirm.queryByTab = "";

pay.cashCashierConfirm.deptCode = stl.currentDept.code;//当前登录部门

pay.cashCashierConfirm.queryCashCashierConfirmParams = function (form, me) {
    pay.cashCashierConfirm.startBusinessDate = Ext.Date.parse(form.findField('startDate').getValue(), 'Y-m-d H:i:s');
    pay.cashCashierConfirm.endBusinessDate = Ext.Date.parse(form.findField('endDate').getValue(), 'Y-m-d H:i:s');
    pay.cashCashierConfirm.billType = form.findField('billTpye').getValue();
    pay.cashCashierConfirm.collectionOrgCode = form.findField('collectionOrgDetial').queryOrgCode;
    pay.cashCashierConfirm.paymentType = form.findField('paymentType').getValue();
    pay.cashCashierConfirm.nosName = form.findField('nosName').getValue();
    pay.cashCashierConfirm.createUserCode = form.findField('createUserCode').getValue();
    pay.cashCashierConfirm.queryByTab = pay.cashCashierConfirm.QUERY_BYDATE;
    pay.cashCashierConfirm.posSerialNum = form.findField('posSerialNum').getValue();
    pay.cashCashierConfirm.settleApproach = form.findField('settleApproach').getValue();

    if (pay.cashCashierConfirm.startBusinessDate == null || pay.cashCashierConfirm.startBusinessDate == '') {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.dateIsNotNull'));
        return false;
    }

    if (pay.cashCashierConfirm.endBusinessDate == null || pay.cashCashierConfirm.endBusinessDate == '') {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.dateIsNotNull'));
        return false;
    }

    var compareTwoDate = stl.compareTwoDate(new Date(pay.cashCashierConfirm.startBusinessDate), new Date(pay.cashCashierConfirm.endBusinessDate));
    if (compareTwoDate > 30) {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.queryDateMaxLimit'));
        return false;
    } else if (compareTwoDate < 1) {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
        return false;
    }
    var params = {
        'billCashCashierConfirmVo.billCashCashierConfirmDto.startBusinessDate': pay.cashCashierConfirm.startBusinessDate,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.endBusinessDate': pay.cashCashierConfirm.endBusinessDate,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.billType': pay.cashCashierConfirm.billType,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgCode': pay.cashCashierConfirm.collectionOrgCode,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.paymentType': pay.cashCashierConfirm.paymentType,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.billNo': pay.cashCashierConfirm.nosName,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.createUserCode': pay.cashCashierConfirm.createUserCode,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.queryByTab': pay.cashCashierConfirm.queryByTab,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.posSerialNum': pay.cashCashierConfirm.posSerialNum,
        'billCashCashierConfirmVo.billCashCashierConfirmDto.settleApproach':pay.cashCashierConfirm.settleApproach
    }
    if (form.isValid()) {
        //设置该按钮灰掉
        me.disable(false);
        //30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext.Ajax.request({
            url: pay.realPath('queryCashCashierConfirmParams.action'),
            params: params,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var billUnCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store;
                //加载后台查询到的数据到grid的store中
                billUnCashCashierConfirmStore.loadData(result.billCashCashierConfirmVo.billCashCashierConfirmDtos);
                Ext.getCmp('totalCashNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashNum);
                Ext.getCmp('totalCashAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashAmount);
                Ext.getCmp('totalCardNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardNum);
                Ext.getCmp('totalCardAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardAmount);
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferNum);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferAmount);
                Ext.getCmp('totalNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalNum);
                Ext.getCmp('totalAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalAmount);

                var billCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store;
                if (billCashCashierConfirmStore != null) {
                    billCashCashierConfirmStore.removeAll();
                }
                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
                me.enable(true);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.message);
                me.enable(true);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
                me.enable(true);
            }
        });
    } else {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

pay.cashCashierConfirm.queryCashCashierConfirmNos = function (form, me) {
    if (form.isValid()) {
        pay.cashCashierConfirm.billNo = form.findField('billNos').getValue();
        pay.cashCashierConfirm.queryByTab = pay.cashCashierConfirm.QUERY_BYNUMBER;
        if (pay.cashCashierConfirm.billNo == null || pay.cashCashierConfirm.billNo == '') {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryNoIsNotNull'));
            return false;
        }
        //'最多只能输入10个单据编号'
        var advancesNosStr = new Array();
        advancesNosStr = stl.splitToArray(pay.cashCashierConfirm.billNo);
        if (advancesNosStr.length > 10) {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryNosLimit'));
            return false;
        }

        var params = {
            'billCashCashierConfirmVo.billCashCashierConfirmDto.billNo': pay.cashCashierConfirm.billNo,
            'billCashCashierConfirmVo.billCashCashierConfirmDto.queryByTab': pay.cashCashierConfirm.queryByTab
        };
        //设置该按钮灰掉
        me.disable(false);
        //30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext.Ajax.request({
            url: pay.realPath('queryCashCashierConfirmParams.action'),
            params: params,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var billUnCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store;
                //加载后台查询到的数据到grid的store中
                billUnCashCashierConfirmStore.loadData(result.billCashCashierConfirmVo.billCashCashierConfirmDtos);
                Ext.getCmp('totalCashNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashNum);
                Ext.getCmp('totalCashAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashAmount);
                Ext.getCmp('totalCardNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardNum);
                Ext.getCmp('totalCardAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardAmount);
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferNum);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferAmount);
                Ext.getCmp('totalNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalNum);
                Ext.getCmp('totalAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalAmount);

                var billCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store;
                if (billCashCashierConfirmStore != null) {
                    billCashCashierConfirmStore.removeAll();
                }
                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
                me.enable(true);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.message);
                me.enable(true);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
                me.enable(true);
            }
        });
    } else {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

pay.cashCashierConfirm.queryCashCashierConfirmWaybillNos = function (form, me) {
    if (form.isValid()) {
        pay.cashCashierConfirm.waybillNo = form.findField('WaybillNos').getValue();
        pay.cashCashierConfirm.queryByTab = pay.cashCashierConfirm.QUERY_BYWAYBILLNUMBER;
        if (pay.cashCashierConfirm.waybillNo == null || pay.cashCashierConfirm.waybillNo == '') {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryNoIsNotNull'));
            return false;
        }
        //'最多只能输入10个单据编号'
        var advancesNosStr = new Array();
        advancesNosStr = stl.splitToArray(pay.cashCashierConfirm.waybillNo);
        if (advancesNosStr.length > 10) {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryNosLimit'));
            return false;
        }

        var params = {
            'billCashCashierConfirmVo.billCashCashierConfirmDto.waybillNo': pay.cashCashierConfirm.waybillNo,
            'billCashCashierConfirmVo.billCashCashierConfirmDto.queryByTab': pay.cashCashierConfirm.queryByTab
        };
        //设置该按钮灰掉
        me.disable(false);
        //30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext.Ajax.request({
            url: pay.realPath('queryCashCashierConfirmParams.action'),
            params: params,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var billUnCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store;
                //加载后台查询到的数据到grid的store中
                billUnCashCashierConfirmStore.loadData(result.billCashCashierConfirmVo.billCashCashierConfirmDtos);
                Ext.getCmp('totalCashNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashNum);
                Ext.getCmp('totalCashAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashAmount);
                Ext.getCmp('totalCardNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardNum);
                Ext.getCmp('totalCardAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardAmount);
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferNum);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferAmount);
                Ext.getCmp('totalNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalNum);
                Ext.getCmp('totalAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalAmount);

                var billCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store;
                if (billCashCashierConfirmStore != null) {
                    billCashCashierConfirmStore.removeAll();
                }
                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
                me.enable(true);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.message);
                me.enable(true);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
                me.enable(true);
            }
        });
    } else {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

/**
 * 按交易流水号查询
 */
pay.cashCashierConfirm.queryCashCashierConfirmBatchNos = function () {
    if (this.up('form').getForm().isValid()) {
        pay.cashCashierConfirm.batchNo = this.up('form').getForm().findField('batchNos').getValue();
        pay.cashCashierConfirm.queryByTab = pay.cashCashierConfirm.QUERY_BYBATCHNO;
        if (pay.cashCashierConfirm.batchNo == null || pay.cashCashierConfirm.batchNo == '') {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.batchNoIsNullWarning'));
            return false;
        }
        //'最多只能输入10个单据编号'
        var advancesNosStr = new Array();
        advancesNosStr = stl.splitToArray(pay.cashCashierConfirm.batchNo);
        if (advancesNosStr.length > 10) {
            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryNosLimit'));
            return false;
        }

        var params = {
            'billCashCashierConfirmVo.billCashCashierConfirmDto.batchNo': pay.cashCashierConfirm.batchNo,
            'billCashCashierConfirmVo.billCashCashierConfirmDto.queryByTab': pay.cashCashierConfirm.queryByTab
        };

        Ext.Ajax.request({
            url: pay.realPath('queryCashCashierConfirmParams.action'),
            params: params,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var billUnCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store;
                //加载后台查询到的数据到grid的store中
                billUnCashCashierConfirmStore.loadData(result.billCashCashierConfirmVo.billCashCashierConfirmDtos);
                Ext.getCmp('totalCashNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashNum);
                Ext.getCmp('totalCashAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCashAmount);
                Ext.getCmp('totalCardNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardNum);
                Ext.getCmp('totalCardAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalCardAmount);
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferNum);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalTelegpaphTransferAmount);
                Ext.getCmp('totalNum_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalNum);
                Ext.getCmp('totalAmount_unId').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.totalAmount);

                var billCashCashierConfirmStore = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store;
                if (billCashCashierConfirmStore != null) {
                    billCashCashierConfirmStore.removeAll();
                }
                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.message);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
            }
        });
    } else {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

//导出现金确认收银列表
pay.cashCashierConfirm.billCashCashierConfirmsExport = function () {
    //获取表格
    grid = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id');
    //如果没有查询到数据，则不能进行导出操作
    if (grid.store.data.length == 0) {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.billDepositReceived.exportIsNotNull'));
        return false;
    }

    if (!Ext.fly('downloadAttachFileForm')) {
        var frm = document.createElement('form');
        frm.id = 'downloadAttachFileForm';
        frm.style.display = 'none';
        document.body.appendChild(frm);
    }
    var selections = new Array();
    Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {
        selections.push(i.data);
    });
    var yesFn = function () {
        Ext.Ajax.request({
            url: pay.realPath('billCashCashierConfirmsExport.action'),
            isUpload: true,
            form: Ext.fly('downloadAttachFileForm'),
            params: {
                'billCashCashierConfirmVo.exportBillCashCashierConfirm': Ext.JSON.encode(selections)
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                //如果异常信息有值，则弹出提示
                if (!Ext.isEmpty(result.errorMessage)) {
                    Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.errorMessage);
                    return false;
                }
                Ext.ux.Toast.msg(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                    pay.cashCashierConfirm.i18n('foss.stl.pay.billDepositReceived.exportSuccess'), 'success', 1000);
            },
            failure: function (response) {
                Ext.ux.Toast.msg(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                    pay.cashCashierConfirm.i18n('foss.stl.pay.billDepositReceived.exportError'), 'error', 1000);
            }
        });
    };
    var noFn = function () {
        return false;
    };
    pay.cashCashierConfirm.billCashCashierConfirmAlert("是否导出收银确认信息？", yesFn, noFn);
}

//收银确认
pay.cashCashierConfirm.cashCashierConfirmCommit = function (me) {
    var cashCashierConfirmData = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.data;

    if (cashCashierConfirmData.length == 0) {
        Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
            pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmText'));
        return false;
    }

    var jsonDataConfirm = new Array();
    Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {
        jsonDataConfirm.push(i.data);
    });

    var entity = new Object();

    entity.billCashCashierConfirmDtos = jsonDataConfirm;
    var yesFn = function () {
        //设置该按钮灰掉
        me.disable(false);
        //30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext.Ajax.request({
            url: pay.realPath('cashCashierConfirmCommit.action'),
            jsonData: {'billCashCashierConfirmVo': entity},
            actionMethods: 'post',
            success: function (response) {
                Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.removeAll();
                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),
                    pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmSuccess'));
                me.enable(true);
                pay.cashCashierConfirm.queryUnconfirmedCodRelatedBill();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), result.message);
                me.enable(true);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
                me.enable(true);
            }
        });
    };
    var noFn = function () {
        return false;
    };
    pay.cashCashierConfirm.billCashCashierConfirmAlert(
        pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.isCashCashierConfirm'), yesFn, noFn);
}

pay.cashCashierConfirm.cashCashierConfirmDetail = function (grid, rowIndex, colIndex) {
    //获取选中的单数据
    var selection = grid.getStore().getAt(rowIndex);


    win = Ext.getCmp('Foss_DepositReceived_DepositReceivedDetailWindow_ID');
    if (win == null) {
        win = Ext.create('Foss.cashCashierConfirm.DepositReceivedDetailWindow', {
            id: 'Foss_Cash_CashierConfirm_Detail_Window_Id'
        });
    }

    //加载数据
    win.items.items[0].loadRecord(selection);

    //转换成中文
    var billType = FossDataDictionary.rendererSubmitToDisplay(selection.get('billType'), settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);
    win.down('form').getForm().findField('billType').setValue(billType);

    var paymentType = FossDataDictionary.rendererSubmitToDisplay(selection.get('paymentType'),
        settlementDict.SETTLEMENT__PAYMENT_TYPE);
    win.down('form').getForm().findField('paymentType').setValue(paymentType);

    var status = FossDataDictionary.rendererSubmitToDisplay(selection.get('status'), settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
    win.down('form').getForm().findField('status').setValue(status);

    win.show();

    /*if(selection.get('billNo').substr(0, 2) == 'HK'){
     var billNo = selection.get('billNo');
     var params = {
     'billCashCashierConfirmVo.billCashCashierConfirmDto.billNo':billNo
     }
     Ext.Ajax.request({
     url:pay.realPath('cashCashierConfirmDetail.action'),
     params:params,
     actionMethods:'post',
     success:function(response){
     var result = Ext.decode(response.responseText);
     //获取弹出窗口
     win = Ext.getCmp('Foss_DepositReceived_DepositReceivedDetailWindow_ID');
     if(win==null ){
     win = Ext.create('Foss.cashCashierConfirm.DepositReceivedDetailWindow',{
     id:'Foss_Cash_CashierConfirm_Detail_Window_Id'
     });
     }
     //加载数据
     win.down('form').getForm().findField('billNo').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.billNo);
     win.down('form').getForm().findField('waybillNo').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.waybillNo);

     //转换成中文
     var billType = FossDataDictionary.rendererSubmitToDisplay(result.billCashCashierConfirmVo.billCashCashierConfirmDto.billType,settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);							
     win.down('form').getForm().findField('billType').setValue(billType);

     win.down('form').getForm().findField('amount').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.amount);
     //
     var paymentType = FossDataDictionary.rendererSubmitToDisplay(result.billCashCashierConfirmVo.billCashCashierConfirmDto.paymentType,
     settlementDict.SETTLEMENT__PAYMENT_TYPE);					
     win.down('form').getForm().findField('paymentType').setValue(paymentType);

     win.down('form').getForm().findField('accountDate').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.accountDate);
     win.down('form').getForm().findField('customerCode').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.customerCode);
     win.down('form').getForm().findField('customerName').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.customerName);
     win.down('form').getForm().findField('collectionOrgName').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgName);
     win.down('form').getForm().findField('collectionOrgCode').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgCode);
     win.down('form').getForm().findField('createUserName').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.createUserName);
     win.down('form').getForm().findField('createUserCode').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.createUserCode);
     win.down('form').getForm().findField('versionNo').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.versionNo);
     //
     var status = FossDataDictionary.rendererSubmitToDisplay(result.billCashCashierConfirmVo.billCashCashierConfirmDto.status,settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
     win.down('form').getForm().findField('status').setValue(status);

     win.down('form').getForm().findField('collectionOrgName').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgName);
     win.down('form').getForm().findField('collectionOrgCode').setValue(result.billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgCode);

     win.show();
     },
     exception:function(response){
     var result = Ext.decode(response.responseText);	
     Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'),result.message);
     },
     failure:function(response){
     var result = Ext.decode(response.responseText);
     }
     });
     }else{
     win = Ext.getCmp('Foss_DepositReceived_DepositReceivedDetailWindow_ID');
     if(win==null ){
     win = Ext.create('Foss.cashCashierConfirm.DepositReceivedDetailWindow',{
     id:'Foss_Cash_CashierConfirm_Detail_Window_Id'
     });
     }

     //加载数据
     win.items.items[0].loadRecord(selection);

     //转换成中文
     var billType = FossDataDictionary.rendererSubmitToDisplay(selection.get('billType'),settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);							
     win.down('form').getForm().findField('billType').setValue(billType);

     var paymentType = FossDataDictionary.rendererSubmitToDisplay(selection.get('paymentType'),
     settlementDict.SETTLEMENT__PAYMENT_TYPE);					
     win.down('form').getForm().findField('paymentType').setValue(paymentType);

     var status = FossDataDictionary.rendererSubmitToDisplay(selection.get('status'),settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
     win.down('form').getForm().findField('status').setValue(status);

     win.show();*/
}
/**
 * 声明明细界面
 */
Ext.define('Foss.cashCashierConfirm.DepositReceivedDetailWindow', {
    id: 'Cash_CashierConfirm_Detail_Window_Id',
    extend: 'Ext.window.Window',
    modal: true,
    title: '明细界面',
    height: 380,
    width: 580,
    items: [{
        xtype: 'form',
        defaults: {
            margin: '15 5 5 5',
            labelWidth: 90
        },
        layout: {
            type: 'column',
            columns: 4
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billNo'),
            name: 'billNo',
            readOnly: true,
            colspan: 1
            //columnWidth:.8
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.waybillNo'),
            name: 'waybillNo',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billType'),
            name: 'billType',
            readOnly: true,
            colspan: 1
//        			render:function(value){
//        				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);
//        				return displayField;
//        	  	}
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.amount'),
            name: 'amount',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.paymentType'),
            name: 'paymentType',
            readOnly: true,
            colspan: 1
//        			listeners:{
//        				'render':function(this, eOpts){
//            	  			var displayField = FossDataDictionary.rendererSubmitToDisplay(this.value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
//            	  			return displayField;
//            	      	}
//        			}       			
        }, {
            xtype: 'datefield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.accountDate'),
            name: 'accountDate',
            readOnly: true,
            colspan: 1,
            fomat: 'Y-m-d'
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.customerCode'),
            name: 'customerCode',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.customerName'),
            name: 'customerName',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgName'),
            name: 'collectionOrgName',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgCode'),
            name: 'collectionOrgCode',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.createUserName'),
            name: 'createUserName',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.createUserCode'),
            name: 'createUserCode',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.versionNo'),
            name: 'versionNo',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.status'),
            name: 'status',
            readOnly: true,
            colspan: 1
//        			render:function(value){
//        	      		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
//        	      		return displayField;
//        	      	}
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgName1'),
            name: 'collectionOrgName',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'textfield',
            fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgCode1'),
            name: 'collectionOrgCode',
            readOnly: true,
            colspan: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .15,
            html: '&nbsp;'
        }, {
            border: 1,
            xtype: 'container',
            columnWidth: 1,
            defaultType: 'button',
            layout: 'column',
            items: [{
                xtype: 'container',
                border: false,
                columnWidth: .4,
                html: '&nbsp;'
            }, {
                text: pay.cashCashierConfirm.i18n('foss.stl.pay.billDepositReceived.depositReceivedDetailColse'),
                columnWidth: .2,
                handler: function () {
                    this.up('window').close();
                }
            }]
        }]
    }]
});
//收银列表
pay.cashCashierConfirm.gridColumns = function () {
    return [{
        xtype: 'actioncolumn',
        width: 23,
        text: '',
        align: 'center',
        items: [{
            iconCls: 'deppon_icons_showdetail',
            tooltip: pay.cashCashierConfirm.i18n('foss.stl.pay.billDepositReceived.detail'),
            handler: function (grid, rowIndex, colIndex) {
                pay.cashCashierConfirm.cashCashierConfirmDetail(grid, rowIndex, colIndex)
            }
        }]
    }, {
        header: 'id',
        hidden: true,
        dataIndex: 'id'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billNo'),
        dataIndex: 'billNo'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.settleApproach'),
        dataIndex: 'settleApproach',
        renderer:function (value) {
            if(value =='PC'){
                return 'PC端'
            } else if(value =='MOBILE'){
                return '移动端';
            }
            return value;
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.common.waybillNo'),
        dataIndex: 'waybillNo'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billType'),
        dataIndex: 'billType',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);
            return displayField;
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.common.amount'),
        dataIndex: 'amount',
        align: 'right',
        renderer: stl.amountFormat
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.paymentType'),
        dataIndex: 'paymentType',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
            return displayField;
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.common.accountDate'),
        dataIndex: 'accountDate',
        renderer: function (value) {
            return stl.dateFormat(value, 'Y-m-d H:i:s');
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.common.customerCode'),
        dataIndex: 'customerCode'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.common.customerName'),
        dataIndex: 'customerName'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgName'),
        dataIndex: 'collectionOrgName'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgCode'),
        dataIndex: 'collectionOrgCode'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.createUserName'),
        dataIndex: 'createUserName'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.createUserCode'),
        dataIndex: 'createUserCode'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.versionNo'),
        dataIndex: 'versionNo'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.status'),
        dataIndex: 'status',
        renderer: function (value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
            return displayField;
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashConfirmTime'),
        hidden: true,
        dataIndex: 'cashConfirmTime',
        renderer: function (value) {
            return stl.dateFormat(value, 'Y-m-d');
        }
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashConfirmUserName'),
        hidden: true,
        dataIndex: 'cashConfirmUserName'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashConfirmUserCode'),
        hidden: true,
        dataIndex: 'cashConfirmUserCode'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgName1'),
        dataIndex: 'collectionOrgName'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgCode1'),
        dataIndex: 'collectionOrgCode'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.posSerialNum'),
        dataIndex: 'posSerialNum'
    }, {
        header: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.batchNo'),
        dataIndex: 'batchNo'
    }]
};

pay.cashCashierConfirm.unconfirmedCodRelatedBillNos = new Array();
pay.cashCashierConfirm.oneTimeFlag=true;
pay.cashCashierConfirm.noticetemplate = 'Notice:单号{0}存在代收货款已结清但未收银确认的单号，为不影响退款时效，请及时收银！';
pay.cashCashierConfirm.getNoticeStr = function(isLong){
    //不为空
    if(pay.cashCashierConfirm.unconfirmedCodRelatedBillNos == null
        || pay.cashCashierConfirm.unconfirmedCodRelatedBillNos.length == 0 ){
        return '';
    }
    var s='';
    for(var i = 0; i < pay.cashCashierConfirm.unconfirmedCodRelatedBillNos.length;i++){
        if(i == 0){
            s = s + pay.cashCashierConfirm.unconfirmedCodRelatedBillNos[i];
            continue;
        }
        if(i == 5 && !isLong && pay.cashCashierConfirm.unconfirmedCodRelatedBillNos.length > 5){
            s = s + '...等';
            break;
        }
        s = s + ',' + pay.cashCashierConfirm.unconfirmedCodRelatedBillNos[i];
    }
    return Ext.String.format(pay.cashCashierConfirm.noticetemplate,s);
};

/* 查询代收货款相关单据编号 */
pay.cashCashierConfirm.queryUnconfirmedCodRelatedBill = function(){
    Ext.Ajax.request({
        url: pay.realPath('queryUnconfirmedCodRelatedBill.action'),
        params: { 'billCashCashierConfirmVo.billCashCashierConfirmDto.collectionOrgCode': pay.cashCashierConfirm.deptCode},
        success: function (response) {
            var result = Ext.decode(response.responseText);

            if(result.billCashCashierConfirmVo.unconfirmedCodRelatedBillList != null
                && result.billCashCashierConfirmVo.unconfirmedCodRelatedBillList.length > 0){
                pay.cashCashierConfirm.unconfirmedCodRelatedBillNos = result.billCashCashierConfirmVo.unconfirmedCodRelatedBillList;
                pay.cashCashierConfirm.notice.update(pay.cashCashierConfirm.getNoticeStr(true));
                if(pay.cashCashierConfirm.oneTimeFlag){
                    pay.cashCashierConfirm.oneTimeFlag = false;
                    pay.cashCashierConfirm.notice.show(pay.cashCashierConfirm.QueryInfoTab.getActiveTab().down('#notice'));
                    setTimeout(function(){
                        pay.cashCashierConfirm.QueryInfoTab.getActiveTab().down('#notice').update(pay.cashCashierConfirm.getNoticeStr(false));
                        pay.cashCashierConfirm.notice.hide(pay.cashCashierConfirm.QueryInfoTab.getActiveTab().down('#notice'));
                    },3000);
                } else {
                    pay.cashCashierConfirm.QueryInfoTab.getActiveTab().down('#notice').update(pay.cashCashierConfirm.getNoticeStr(false));
                }
            }
        },
        failure:function(){
            pay.cashCashierConfirm.oneTimeFlag = false;
        }
    });
};

//弹出的提醒框
pay.cashCashierConfirm.notice = Ext.create('Ext.window.Window', {
    title:'Notice',
    modal:true,
    closable:false,
    layout: 'fit',
    padding: '10 20 20 20',
    width:700,
    html:'',
    bodyStyle:{
        color:'red',
        fontSize:'16px'
    }
});

Ext.define('Foss.PayCashCashierConfirm.PayCashCashierConfirmQueryInfoTab', {
    extend: 'Ext.tab.Panel',
    frame: false,
    bodyCls: 'autoHeight',
    cls: 'innerTabPanel',
    activeTab: 0,
    height: 200,
    listeners:{
        boxready:function(th){
            pay.cashCashierConfirm.queryUnconfirmedCodRelatedBill();
        },
        tabchange:function( tabPanel, newCard, oldCard, eOpts ){
            newCard.down('#notice').update(pay.cashCashierConfirm.getNoticeStr(false));
        }
    },
    items: [{
        title: pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryByDate'),
        tabConfig: {
            width: 150
        },
        width: '200',
        layout: 'fit',
        items: [{
            xtype: 'form',
            defaults: {
                margin: '5 5 5 5',
                labelWidth: 90
            },
            layout: 'column',
            items: [{
                xtype: 'datetimefield_date97',
                id: 'Pay_CashCashierConfirm_startDate',
                name: 'startDate',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.startDatepay'),
                labelWidth: 85,
                editable: 'false',
                value: Ext.Date.format(stl.getTargetDate(new Date(), -1), stl.FORMAT_DATE) + stl.START_PREFIX,
                dateConfig: {
                    el: 'Pay_CashCashierConfirm_startDate-inputEl',
                    dateFmt: 'yyyy-MM-dd HH:mi:ss'
                },
                allowBlank: false,
                columnWidth: .3
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'datetimefield_date97',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.endDatepay'),
                labelWidth: 85,
                id: 'Pay_CashCashierConfirm_endDate',
                time: true,
                name: 'endDate',
                editable: 'false',
                value: stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX,
                dateConfig: {
                    el: 'Pay_CashCashierConfirm_endDate-inputEl',
                    dateFmt: 'yyyy-MM-dd HH:mi:ss',
                    maxDate: stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX
                },
                allowBlank: false,
                columnWidth: .3
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'combobox',
                name: 'billTpye',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billType'),
                labelWidth: 85,
                editable: false,
                store: FossDataDictionary.getDataDictionaryStore(settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE, null, {
                    'valueCode': '',
                    'valueName': pay.cashCashierConfirm.i18n('foss.stl.pay.common.all')
                }, [pay.cashCashierConfirm.BILL_TYPE__CASH_COLLECTION,
                    pay.cashCashierConfirm.BILL_TYPE__DEPOSIT_RECEIVED,
                    pay.cashCashierConfirm.BILL_TYPE__REPAYMENT]),
                queryModel: 'local',
                displayField: 'valueName',
                valueField: 'valueCode',
                value: '',
                columnWidth: .2
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'dynamicorgcombselector',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.collectionOrgName'),
                name: 'collectionOrgDetial',
                labelWidth: 85,
                queryOrgCode: stl.currentDept.code,
                value: stl.currentDept.name,
                columnWidth: .3,
                listWidth: 300,//设置下拉框宽度
                isPaging: true, //分页
                listeners: {
                    'change': function (th, newValue, oldValue) {
                        this.up('form').getForm().findField('collectionOrgDetial').queryOrgCode = newValue;
                    }
                }
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'commonemployeeselector',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.createUserName'),
                labelWidth: 85,
                isPaging: true, // 分页
                name: 'createUserCode',
                columnWidth: .3
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'combobox',
                name: 'paymentType',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.paymentType'),
                labelWidth: 85,
                editable: false,
                store: FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE, null, {
                    'valueCode': '',
                    'valueName': pay.cashCashierConfirm.i18n('foss.stl.pay.common.all')
                }, [pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__CASH,
                    pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__CARD,
                    pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                    pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__NOTE]),
                queryModel: 'local',
                displayField: 'valueName',
                valueField: 'valueCode',
                value: '',
                columnWidth: .2
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'textfield',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billNo'),
                labelWidth: 85,
                name: 'nosName',
                columnWidth: .3
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'textfield',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.posSerialNum'),
                labelWidth: 85,
                name: 'posSerialNum',
                columnWidth: .3
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .03,
                html: '&nbsp;'
            }, {
                xtype: 'combobox',
                name: 'settleApproach',
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.settleApproach'),
                labelWidth: 85,
                editable: false,
                store: Ext.create('Ext.data.Store', {
                    fields: ['code', 'name'],
                    data: [
                        {"code": "", "name": "全部"},
                        {"code": "PC", "name": "PC端"},
                        {"code": "MOBILE", "name": "移动端"}
                        //...
                    ]
                }),
                queryModel: 'local',
                displayField: 'name',
                valueField: 'code',
                value: '',
                columnWidth: .2
            }, {
                border: 1,
                xtype: 'container',
                columnWidth: 1,
                defaultType: 'button',
                layout: 'column',
                items: [{
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.reset'),
                    columnWidth: .1,
                    handler: function () {
                        this.up('form').getForm().reset();
                        this.up('form').getForm().findField('collectionOrgDetial').queryOrgCode = stl.currentDept.code;
                    }
                }, {
                    itemId:'notice',
                    xtype: 'box',
                    border: false,
                    padding: '0 20 0 20',
                    columnWidth: .66,
                    html: '&nbsp;',
                    style:{
                        color:'red',
                        fontSize:14,
                        fontFamily:'微软雅黑'
                    }
                }, {
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .1,
                    handler: function () {
                        var form = this.up('form').getForm();
                        var me = this;
                        pay.cashCashierConfirm.queryCashCashierConfirmParams(form, me)
                    }
                }]
            }]
        }]
    }, {
        title: pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryByNo'),
        tabConfig: {
            width: 200
        },
        layout: 'fit',
        items: [{
            xtype: 'form',
            defaults: {
                margin: '10 5 5 5',
                labelWidth: 90
            },
            layout: 'column',
            items: [{
                xtype: 'textarea',
                autoScroll: true,
                emptyText: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmEmptyText'),
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.billNo'),
                name: 'billNos',
                height: 80,
                allowBlank: false,
                columnWidth: .4
                //regex:/^((YS)[0-9]{7,10}[;,]?)+$/i,
                //regexText:'输入YS加上7-10位以内的数字编号' 
            }, {
                border: 1,
                xtype: 'container',
                columnWidth: 1,
                defaultType: 'button',
                layout: 'column',
                items: [{
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.reset'),
                    columnWidth: .1,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    itemId:'notice',
                    xtype: 'box',
                    border: false,
                    padding: '0 20 0 20',
                    columnWidth: .66,
                    html: '&nbsp;',
                    style:{
                        color:'red',
                        fontSize:14,
                        fontFamily:'微软雅黑'
                    }
                }, {
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .1,
                    handler: function () {
                        var form = this.up('form').getForm();
                        var me = this;
                        pay.cashCashierConfirm.queryCashCashierConfirmNos(form, me)
                    }
                }]
            }]
        }]
    }, {
        title: pay.cashCashierConfirm.i18n('foss.stl.pay.common.queryByWayNo'),
        tabConfig: {
            width: 200
        },
        layout: 'fit',
        items: [{
            xtype: 'form',
            defaults: {
                margin: '10 5 5 5',
                labelWidth: 90
            },
            layout: 'column',
            items: [{
                xtype: 'textarea',
                autoScroll: true,
                emptyText: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmEmptyText'),
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.common.waybillNo'),
                name: 'WaybillNos',
                height: 80,
                allowBlank: false,
                columnWidth: .4
                //regex:/^((YS)[0-9]{7,10}[;,]?)+$/i,
                //regexText:'输入YS加上7-10位以内的数字编号' 
            }, {
                border: 1,
                xtype: 'container',
                columnWidth: 1,
                defaultType: 'button',
                layout: 'column',
                items: [{
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.reset'),
                    columnWidth: .1,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    itemId:'notice',
                    xtype: 'box',
                    border: false,
                    padding: '0 20 0 20',
                    columnWidth: .66,
                    html: '&nbsp;',
                    style:{
                        color:'red',
                        fontSize:14,
                        fontFamily:'微软雅黑'
                    }
                }, {
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .1,
                    handler: function () {
                        var form = this.up('form').getForm();
                        var me = this;
                        pay.cashCashierConfirm.queryCashCashierConfirmWaybillNos(form, me)
                    }
                }]
            }]
        }]
    }, {
        title: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.queryByBatchNo'),
        tabConfig: {
            width: 200
        },
        layout: 'fit',
        items: [{
            xtype: 'form',
            defaults: {
                margin: '10 5 5 5',
                labelWidth: 90
            },
            layout: 'column',
            items: [{
                xtype: 'textarea',
                autoScroll: true,
                emptyText: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmEmptyText'),
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.batchNo'),
                name: 'batchNos',
                height: 80,
                allowBlank: false,
                columnWidth: .7
            }, {
                border: 1,
                xtype: 'container',
                columnWidth: 1,
                defaultType: 'button',
                layout: 'column',
                items: [{
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.reset'),
                    columnWidth: .1,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    itemId:'notice',
                    xtype: 'box',
                    border: false,
                    padding: '0 20 0 20',
                    columnWidth: .66,
                    html: '&nbsp;',
                    style:{
                        color:'red',
                        fontSize:14,
                        fontFamily:'微软雅黑'
                    }
                }, {
                    text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.query'),
                    cls: 'yellow_button',
                    columnWidth: .1,
                    handler: pay.cashCashierConfirm.queryCashCashierConfirmBatchNos
                }]
            }]
        }]
    }]
});

//预收单model
Ext.define('Foss.PayCashCashierConfirm.BillCashCashierConfirmEntityModel', {
    extend: 'Ext.data.Model',
    fields: [{
        //id号
        name: 'id'
    }, {
        //单号
        name: 'billNo'
    }, {
        //结清方式
        name: 'settleApproach'
    },{
        name: 'waybillNo'
    }, {
        name: 'amount',
        type: 'long'
    }, {
        //单据类型
        name: 'billType'
    }, {
        //收款方式
        name: 'paymentType'
    }, {
        // 客户编码
        name: 'customerCode'
    }, {
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
    }, {
        //客户名称
        name: 'customerName'
    }, {
        //制单人工号
        name: 'createUserCode'
    }, {
        //制单人名称
        name: 'createUserName'
    }, {
        //收款部门名称
        name: 'collectionOrgName'
    }, {
        //收款部门编码
        name: 'collectionOrgCode'
    }, {
        //版本号
        name: 'versionNo'
    }, {
        name: 'status'
    }, {
        //收银确认人名称
        name: 'cashConfirmUserCode'
    }, {
        //收银确认人工号
        name: 'cashConfirmUserName'
    }, {
        //收银确认时间
        name: 'cashConfirmTime',
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
        //收银部门名称
        name: 'collectionOrgName'
    }, {
        //收银部门编码
        name: 'collectionOrgCode'
    }, {
        //POS串号
        name: 'posSerialNum'
    }, {
        //银联交易流水号
        name: 'batchNo'
    }]
});

//预收单Store
Ext.define('Foss.PayCashCashierConfirm.BillCashCashierConfirmEntityStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.PayCashCashierConfirm.BillCashCashierConfirmEntityModel'
});

//未收银列表
Ext.define('Foss.PayCashCashierConfirm.PayUnCashCashierConfirmQueryInfoGrid', {
    extend: 'Ext.grid.Panel',
    title: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.uncashCashierConfirm'),
    frame: true,
    //plugins:SoperateColumnEditing,
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.PayCashCashierConfirm.BillCashCashierConfirmEntityStore'),
    listeners: {
        'itemdblclick': function (view, record) {
            Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.insert(0, record);
            //获取未确认收银金额和总条数
            var untotalNum = Ext.getCmp('totalNum_unId').getValue();
            var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
            var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
            var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
            var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();
            var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
            var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
            var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
            //获取待确认收银金额和总条数
            var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
            var totalNum = Ext.getCmp('totalNum_Id').getValue();
            var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
            var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
            var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
            var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
            var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
            var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
            var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();
            var paymentType = record.get('paymentType');
            var amount = record.get('amount');
            if (paymentType == 'CH') {
                //如果是现金，则增加待确认收银现金的总条数和金额总数
                Ext.getCmp('totalCashNum_Id').setValue(totalCashNum * 1 + 1);
                //Ext.getCmp('totalCashAmount_Id').setValue((totalCashAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalCashAmount_Id').setValue(stl.amountAdd(totalCashAmount, amount));
                //相应减少未确认收银现金的总条数和金额总数
                Ext.getCmp('totalCashNum_unId').setValue(untotalCashNum * 1 - 1);
                //Ext.getCmp('totalCashAmount_unId').setValue((untotalCashAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalCashAmount_unId').setValue(stl.amountSub(untotalCashAmount, amount));
            }
            else if (paymentType == 'CD') {
                //如果是银行卡，则增加待确认收银银行卡的总条数和金额总数
                Ext.getCmp('totalCardNum_Id').setValue(totalCardNum * 1 + 1);
                //Ext.getCmp('totalCardAmount_Id').setValue((totalCardAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalCardAmount_Id').setValue(stl.amountAdd(totalCardAmount, amount));
                //相应减少未确认收银银行卡的总条数和金额总数
                Ext.getCmp('totalCardNum_unId').setValue(untotalCardNum * 1 - 1);
                //Ext.getCmp('totalCardAmount_unId').setValue((untotalCardAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalCardAmount_unId').setValue(stl.amountSub(untotalCardAmount, amount));
            }
            else if (paymentType == 'TT' || paymentType == 'NT' || paymentType == 'OL') {
                //如果是电汇，则增加待确认收银电汇的总条数和金额总数
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(totalTelegpaphTransferNum * 1 + 1);
                //Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue((totalTelegpaphTransferAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue(stl.amountAdd(totalTelegpaphTransferAmount, amount));
                //相应减少未确认收银电汇的总条数和金额总数
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(untotalTelegpaphTransferNum * 1 - 1);
                //Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue((untotalTelegpaphTransferAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(stl.amountSub(untotalTelegpaphTransferAmount, amount));
            }
            //增加待确认收银总条数和总金额
            Ext.getCmp('totalNum_Id').setValue(totalNum * 1 + 1);
            //Ext.getCmp('totalAmount_Id').setValue((totalAmount*10000 + amount*10000)/10000);
            Ext.getCmp('totalAmount_Id').setValue(stl.amountAdd(totalAmount, amount));
            //减少未确认收银总条数和总金额
            Ext.getCmp('totalNum_unId').setValue(untotalNum * 1 - 1);
            //Ext.getCmp('totalAmount_unId').setValue((untotalAmount*10000 - amount*10000)/10000);
            Ext.getCmp('totalAmount_unId').setValue(stl.amountSub(untotalAmount, amount));
            Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store.remove(record);
        }
    },
    initComponent: function () {
        var me = this;
        me.columns = pay.cashCashierConfirm.gridColumns();
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            items: [{
                xtype: 'displayfield',
                id: 'totalCashNum_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalCashNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCashAmount_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCardNum_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalCardNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCardAmount_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalTelegpaphTransferNum_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalTelegpaphTransferNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalTelegpaphTransferAmount_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalNum_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalAmount_unId',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalAmount'),
                labelWidth: 80
            }]
        }];
        me.callParent();
    }
});

//待收银列表
Ext.define('Foss.PayCashCashierConfirm.PayCashCashierConfirmQueryInfoGrid', {
    extend: 'Ext.grid.Panel',
    title: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirm'),
    frame: true,
    //plugins:SoperateColumnEditing,
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.PayCashCashierConfirm.BillCashCashierConfirmEntityStore'),
    listeners: {
        'itemdblclick': function (view, record) {
            Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store.insert(0, record);
            //获取未确认收银金额和总条数
            var untotalNum = Ext.getCmp('totalNum_unId').getValue();
            var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
            var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
            var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
            var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();
            var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
            var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
            var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
            //获取待确认收银金额和总条数
            var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
            var totalNum = Ext.getCmp('totalNum_Id').getValue();
            var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
            var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
            var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
            var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
            var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
            var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
            var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();
            var paymentType = record.get('paymentType');
            var amount = record.get('amount');
            if (paymentType == 'CH') {
                //如果是现金，则增加待确认收银现金的总条数和金额总数
                Ext.getCmp('totalCashNum_unId').setValue(untotalCashNum * 1 + 1);
                //Ext.getCmp('totalCashAmount_unId').setValue((untotalCashAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalCashAmount_unId').setValue(stl.amountAdd(untotalCashAmount, amount));
                //相应减少未确认收银现金的总条数和金额总数
                Ext.getCmp('totalCashNum_Id').setValue(totalCashNum * 1 - 1);
                //Ext.getCmp('totalCashAmount_Id').setValue((totalCashAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalCashAmount_Id').setValue(stl.amountSub(totalCashAmount, amount));
            }
            else if (paymentType == 'CD') {
                //如果是银行卡，则增加待确认收银银行卡的总条数和金额总数
                Ext.getCmp('totalCardNum_unId').setValue(untotalCardNum * 1 + 1);
                //Ext.getCmp('totalCardAmount_unId').setValue((untotalCardAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalCardAmount_unId').setValue(stl.amountAdd(untotalCardAmount, amount));
                //相应减少未确认收银银行卡的总条数和金额总数
                Ext.getCmp('totalCardNum_Id').setValue(totalCardNum * 1 - 1);
                //Ext.getCmp('totalCardAmount_Id').setValue((totalCardAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalCardAmount_Id').setValue(stl.amountSub(totalCardAmount, amount));
            }
            else if (paymentType == 'TT' || paymentType == 'NT' || paymentType == 'OL') {
                //如果是电汇，则增加待确认收银电汇的总条数和金额总数
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(untotalTelegpaphTransferNum * 1 + 1);
                //Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue((untotalTelegpaphTransferAmount*10000 + amount*10000)/10000);
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(stl.amountAdd(untotalTelegpaphTransferAmount, amount));
                //相应减少未确认收银电汇的总条数和金额总数
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(totalTelegpaphTransferNum * 1 - 1);
                //Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue((totalTelegpaphTransferAmount*10000 - amount*10000)/10000);
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue(stl.amountSub(totalTelegpaphTransferAmount, amount));
            }
            //增加待确认收银总条数和总金额
            Ext.getCmp('totalNum_unId').setValue(untotalNum * 1 + 1);
            //Ext.getCmp('totalAmount_unId').setValue((untotalAmount*10000 + amount*10000)/10000);
            Ext.getCmp('totalAmount_unId').setValue(stl.amountAdd(untotalAmount, amount));
            //减少未确认收银总条数和总金额
            Ext.getCmp('totalNum_Id').setValue(totalNum * 1 - 1);
            //Ext.getCmp('totalAmount_Id').setValue((totalAmount*10000 - amount*10000)/10000);
            Ext.getCmp('totalAmount_Id').setValue(stl.amountSub(totalAmount, amount));
            Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.remove(record);
        }
    },
    initComponent: function () {
        var me = this;
        me.columns = pay.cashCashierConfirm.gridColumns();
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            items: [{
                xtype: 'displayfield',
                id: 'totalCashNum_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalCashNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCashAmount_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCardNum_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalCardNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalCardAmount_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalTelegpaphTransferNum_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalTelegpaphTransferNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalTelegpaphTransferAmount_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.amount'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalNum_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalNum'),
                labelWidth: 80
            }, {
                xtype: 'displayfield',
                id: 'totalAmount_Id',
                value: '0',
                allowBlank: true,
                columnWidth: .5,
                fieldLabel: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.totalAmount'),
                labelWidth: 80
            }]
        }];
        me.callParent();
    }
});

pay.cashCashierConfirm.rightChange = function (selections) {
    //已选择客户store
    var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").store;
    //添加到已选择客户store里
    chooseStores.insert(0, selections);
    //获取未确认收银金额和总条数
    var untotalNum = Ext.getCmp('totalNum_unId').getValue();
    var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
    var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
    var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
    var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();
    var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
    var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
    var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
    //获取待确认收银金额和总条数
    var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
    var totalNum = Ext.getCmp('totalNum_Id').getValue();
    var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
    var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
    var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
    var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
    var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
    var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
    var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();
    var paymentType = selections.data.paymentType;
    var amount = selections.data.amount;
    if (paymentType == 'CH') {
        //如果是现金，则增加待确认收银现金的总条数和金额总数
        Ext.getCmp('totalCashNum_Id').setValue(totalCashNum * 1 + 1);
        Ext.getCmp('totalCashAmount_Id').setValue((totalCashAmount * 10000 + amount * 10000) / 10000);
        //相应减少未确认收银现金的总条数和金额总数
        Ext.getCmp('totalCashNum_unId').setValue(untotalCashNum * 1 - 1);
        Ext.getCmp('totalCashAmount_unId').setValue((untotalCashAmount * 10000 - amount * 10000) / 10000);
    }
    else if (paymentType == 'CD') {
        //如果是银行卡，则增加待确认收银银行卡的总条数和金额总数
        Ext.getCmp('totalCardNum_Id').setValue(totalCardNum * 1 + 1);
        Ext.getCmp('totalCardAmount_Id').setValue((totalCardAmount * 10000 + amount * 10000) / 10000);
        //相应减少未确认收银银行卡的总条数和金额总数
        Ext.getCmp('totalCardNum_unId').setValue(untotalCardNum * 1 - 1);
        Ext.getCmp('totalCardAmount_unId').setValue((untotalCardAmount * 10000 - amount * 10000) / 10000);
    }
    else if (paymentType == 'TT' || paymentType == 'NT' || paymentType == 'OL') {
        //如果是电汇，则增加待确认收银电汇的总条数和金额总数
        Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(totalTelegpaphTransferNum * 1 + 1);
        Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue((totalTelegpaphTransferAmount * 10000 + amount * 10000) / 10000);
        //相应减少未确认收银电汇的总条数和金额总数
        Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(untotalTelegpaphTransferNum * 1 - 1);
        Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue((untotalTelegpaphTransferAmount * 10000 - amount * 10000) / 10000);
    }
    //增加待确认收银总条数和总金额
    Ext.getCmp('totalNum_Id').setValue(totalNum * 1 + 1);
    Ext.getCmp('totalAmount_Id').setValue((totalAmount * 10000 + amount * 10000) / 10000);
    //减少未确认收银总条数和总金额
    Ext.getCmp('totalNum_unId').setValue(untotalNum * 1 - 1);
    Ext.getCmp('totalAmount_unId').setValue((untotalAmount * 10000 - amount * 10000) / 10000);

    Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store.remove(selections);
}

Ext.define('Foss.PayCashCashierConfirm.ButtonPanelRole', {
    extend: 'Ext.panel.Panel',
    buttonAlign: 'center',
    layout: 'column',
    items: [{
        columnWidth: 1,
        xtype: 'container',
        style: 'padding-top:100px;border:none',
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'button',
            text: '',
            iconCls: 'deppon_icons_turnright',
            height: 23,
            columnWidth: .8,
            // 添加所选客户
            handler: function () {
                //得到所选客户
                var selections = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").getSelectionModel().getSelection();
                pay.cashCashierConfirm.rightChange(selections[0]);
                if ("" == selections || null == selections) {
                    Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请选择未收银确认单号进行操作！');
                    return false;
                }
            }
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        style: 'padding-top:20px;border:none',
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'button',
            text: '',
            iconCls: 'deppon_icons_turnrightall',
            height: 23,
            columnWidth: .8,
            // 添加所选客户
            handler: function () {//得到所选客户
                var selections = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store.data.items;
                //已选择客户store
                var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").store;
                //添加到已选择客户store里
                chooseStores.insert(0, selections);
                //获取未确认收银金额和总条数
                var untotalNum = Ext.getCmp('totalNum_unId').getValue();
                var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
                var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
                var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
                var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();
                var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
                var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
                var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
                //获取待确认收银金额和总条数
                var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
                var totalNum = Ext.getCmp('totalNum_Id').getValue();
                var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
                var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
                var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
                var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
                var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
                var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
                var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();

                if (totalCashNum > 0) {
                    Ext.getCmp('totalCashNum_Id').setValue(totalCashNum * 1 + untotalCashNum * 1);
                } else {
                    Ext.getCmp('totalCashNum_Id').setValue(untotalCashNum * 1);
                }
                if (totalCashAmount > 0) {
                    Ext.getCmp('totalCashAmount_Id').setValue((totalCashAmount * 10000 + untotalCashAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalCashAmount_Id').setValue(untotalCashAmount * 1);
                }
                if (totalCardNum > 0) {
                    Ext.getCmp('totalCardNum_Id').setValue(totalCardNum * 1 + untotalCardNum * 1);
                } else {
                    Ext.getCmp('totalCardNum_Id').setValue(untotalCardNum * 1);
                }
                if (totalCardAmount > 0) {
                    Ext.getCmp('totalCardAmount_Id').setValue((totalCardAmount * 10000 + untotalCardAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalCardAmount_Id').setValue(untotalCardAmount * 1);
                }
                if (totalTelegpaphTransferNum > 0) {
                    Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(totalTelegpaphTransferNum * 1 + untotalTelegpaphTransferNum * 1);
                } else {
                    Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(untotalTelegpaphTransferNum * 1);
                }
                if (totalTelegpaphTransferAmount > 0) {
                    Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue((totalTelegpaphTransferAmount * 10000 + untotalTelegpaphTransferAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue(untotalTelegpaphTransferAmount * 1);
                }
                if (totalNum > 0) {
                    Ext.getCmp('totalNum_Id').setValue(totalNum * 1 + untotalNum * 1);
                } else {
                    Ext.getCmp('totalNum_Id').setValue(untotalNum * 1);
                }
                if (totalAmount > 0) {
                    Ext.getCmp('totalAmount_Id').setValue((totalAmount * 10000 + untotalAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalAmount_Id').setValue(untotalAmount * 1);
                }

                Ext.getCmp('totalCashNum_unId').setValue("0");
                Ext.getCmp('totalCashAmount_unId').setValue("0");
                Ext.getCmp('totalCardNum_unId').setValue("0");
                Ext.getCmp('totalCardAmount_unId').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_unId').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue("0");
                Ext.getCmp('totalNum_unId').setValue("0");
                Ext.getCmp('totalAmount_unId').setValue("0");

                Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store.removeAll();

            }
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        height: 80,
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        layout: 'column',
        items: [{
            xtype: 'textfield',
            fieldLabel: '',
            emptyText: '请输入单号',
            name: 'selectBillNo',
            columnWidth: 1,
            //移除所选客户
            listeners: {
                change: function (field, newValue, oldValue) {
                    var value = "";
                    for (var i = 0; i < newValue.length; i++) {
                        if (newValue.substring(i, i + 1) == "(") {
                            value = newValue.substring(0, i);
                            Ext.getCmp('Foss_PayCashCashierConfirm_ButtonPanelRole_Id').items.items[3].items.items[0].setValue(value);
                            break;
                        }
                    }
                },
                specialkey: function (field, e) {
                    if (e.getKey() == Ext.EventObject.ENTER) {
                        var billNo = Ext.getCmp('Foss_PayCashCashierConfirm_ButtonPanelRole_Id').items.items[3].items.items[0].getValue();
                        if ("" == billNo || null == billNo) {
                            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请输入未收银确认单号！');
                            return false;
                        }
                        var targetArray = [];
                        Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {
                            if (null != i) {
                                if ("" == i.data || null == i.data) {
                                    Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请输入未收银确认列表中的单号进行操作！');
                                    return false;
                                }
                                if (billNo == i.data.waybillNo || billNo == i.data.billNo) {
                                    targetArray.push(i);
                                }
                            }
                        });

                        if (targetArray.length <= 0) {
                            Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请输入未收银确认列表中的单号进行操作！');
                        }

                        for (var j = 0; j < targetArray.length; j++) {
                            pay.cashCashierConfirm.rightChange(targetArray[j]);
                        }
                        var s = 0;
                        Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {

                            var cells = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').getView().getNodes()[s].children;

                            if ((null != i) && (billNo == i.data.waybillNo || billNo == i.data.billNo)) {
                                for (var j = 0; j < cells.length; j++) {
                                    cells[j].style.backgroundColor = '#EE7700';
                                }
                            } else {
                                for (var j = 0; j < cells.length; j++) {
                                    cells[j].style.backgroundColor = '#FFFFFF';
                                }

                            }
                            s++;
                        });
                        Ext.getCmp('Foss_PayCashCashierConfirm_ButtonPanelRole_Id').items.items[3].items.items[0].setValue("");
                    }
                }
            }
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'button',
            text: '定位右移',
            cls: 'specialbtn',
            name: 'selectBillNoBut',
            columnWidth: .8,
            handler: function () {
                var billNo = Ext.getCmp('Foss_PayCashCashierConfirm_ButtonPanelRole_Id').items.items[3].items.items[0].getValue();
                if ("" == billNo || null == billNo) {
                    Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请输入未收银确认单号！');
                    return false;
                }
                var targetArray = [];
                Ext.getCmp('Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {
                    if (null != i) {
                        if (billNo == i.data.waybillNo || billNo == i.data.billNo) {
                            targetArray.push(i);
                        }
                    }
                });

                if (targetArray.length <= 0) {
                    Ext.Msg.alert(pay.cashCashierConfirm.i18n('foss.stl.pay.common.alert'), '请输入未收银确认列表中的单号进行操作！');
                }

                for (var j = 0; j < targetArray.length; j++) {
                    pay.cashCashierConfirm.rightChange(targetArray[j]);
                }

                var s = 0;
                Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').store.each(function (i) {
                    var cells = Ext.getCmp('Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id').getView().getNodes()[s].children;

                    if ((null != i) && (billNo == i.data.waybillNo || billNo == i.data.billNo)) {
                        for (var j = 0; j < cells.length; j++) {
                            cells[j].style.backgroundColor = '#EE7700';
                        }
                    } else {
                        for (var j = 0; j < cells.length; j++) {
                            cells[j].style.backgroundColor = '#FFFFFF';
                        }
                    }
                    s++;
                });
                Ext.getCmp('Foss_PayCashCashierConfirm_ButtonPanelRole_Id').items.items[3].items.items[0].setValue("");
            }
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        style: 'padding-top:50px;border:none',
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'button',
            text: '',
            iconCls: 'deppon_icons_turnleft',
            height: 23,
            columnWidth: .8,
            //移除所选客户
            handler: function () {
                //得到所选客户
                var selections = Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").getSelectionModel().getSelection();
                //已选择客户store
                var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
                Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").store.remove(selections);
                //添加到已选择客户store里
                chooseStores.insert(0, selections);
                //获取未确认收银金额和总条数
                var untotalNum = Ext.getCmp('totalNum_unId').getValue();
                var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
                var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
                var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
                var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();
                var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
                var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
                var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
                //获取待确认收银金额和总条数
                var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
                var totalNum = Ext.getCmp('totalNum_Id').getValue();
                var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
                var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
                var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
                var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
                var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
                var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
                var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();
                var paymentType = selections[0].get('paymentType');
                var amount = selections[0].get('amount');
                if (paymentType == 'CH') {
                    //如果是现金，则增加待确认收银现金的总条数和金额总数
                    Ext.getCmp('totalCashNum_unId').setValue(untotalCashNum * 1 + 1);
                    Ext.getCmp('totalCashAmount_unId').setValue((untotalCashAmount * 10000 + amount * 10000) / 10000);
                    //相应减少未确认收银现金的总条数和金额总数
                    Ext.getCmp('totalCashNum_Id').setValue(totalCashNum * 1 - 1);
                    Ext.getCmp('totalCashAmount_Id').setValue((totalCashAmount * 10000 - amount * 10000) / 10000);
                }
                else if (paymentType == 'CD') {
                    //如果是银行卡，则增加待确认收银银行卡的总条数和金额总数
                    Ext.getCmp('totalCardNum_unId').setValue(untotalCardNum * 1 + 1);
                    Ext.getCmp('totalCardAmount_unId').setValue((untotalCardAmount * 10000 + amount * 10000) / 10000);
                    //相应减少未确认收银银行卡的总条数和金额总数
                    Ext.getCmp('totalCardNum_Id').setValue(totalCardNum * 1 - 1);
                    Ext.getCmp('totalCardAmount_Id').setValue((totalCardAmount * 10000 - amount * 10000) / 10000);
                }
                else if (paymentType == 'TT' || paymentType == 'NT' || paymentType == 'OL') {
                    //如果是电汇，则增加待确认收银电汇的总条数和金额总数
                    Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(untotalTelegpaphTransferNum * 1 + 1);
                    Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue((untotalTelegpaphTransferAmount * 10000 + amount * 10000) / 10000);
                    //相应减少未确认收银电汇的总条数和金额总数
                    Ext.getCmp('totalTelegpaphTransferNum_Id').setValue(totalTelegpaphTransferNum * 1 - 1);
                    Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue((totalTelegpaphTransferAmount * 10000 - amount * 10000) / 10000);
                }
                //增加待确认收银总条数和总金额
                Ext.getCmp('totalNum_unId').setValue(untotalNum * 1 + 1);
                Ext.getCmp('totalAmount_unId').setValue((untotalAmount * 10000 + amount * 10000) / 10000);
                //减少未确认收银总条数和总金额
                Ext.getCmp('totalNum_Id').setValue(totalNum * 1 - 1);
                Ext.getCmp('totalAmount_Id').setValue((totalAmount * 10000 - amount * 10000) / 10000);
            }
        }]
    }, {
        columnWidth: 1,
        xtype: 'container',
        style: 'padding-top:20px;border:none',
        layout: 'column',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'button',
            text: '',
            iconCls: 'deppon_icons_turnleftall',
            height: 23,
            columnWidth: .8,
            //移除所选客户
            handler: function () {
                var selections = Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").store.data.items;
                //已选择客户store
                //获取待确认收银金额和总条数
                var chooseStores = Ext.getCmp("Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id").store;
                var totalNum = Ext.getCmp('totalNum_Id').getValue();
                var totalCashNum = Ext.getCmp('totalCashNum_Id').getValue();
                var totalCardNum = Ext.getCmp('totalCardNum_Id').getValue();
                var totalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_Id').getValue();
                var totalAmount = Ext.getCmp('totalAmount_Id').getValue();
                var totalCashAmount = Ext.getCmp('totalCashAmount_Id').getValue();
                var totalCardAmount = Ext.getCmp('totalCardAmount_Id').getValue();
                var totalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_Id').getValue();
                //获取未确认收银金额和总条数
                var untotalCashNum = Ext.getCmp('totalCashNum_unId').getValue();
                var untotalCashAmount = Ext.getCmp('totalCashAmount_unId').getValue();
                var untotalCardNum = Ext.getCmp('totalCardNum_unId').getValue();
                var untotalCardAmount = Ext.getCmp('totalCardAmount_unId').getValue();
                var untotalTelegpaphTransferNum = Ext.getCmp('totalTelegpaphTransferNum_unId').getValue();
                var untotalTelegpaphTransferAmount = Ext.getCmp('totalTelegpaphTransferAmount_unId').getValue();
                var untotalNum = Ext.getCmp('totalNum_unId').getValue();
                var untotalAmount = Ext.getCmp('totalAmount_unId').getValue();

                if (untotalCashNum > 0) {
                    Ext.getCmp('totalCashNum_unId').setValue(totalCashNum * 1 + untotalCashNum * 1);
                } else {
                    Ext.getCmp('totalCashNum_unId').setValue(totalCashNum * 1);
                }
                if (untotalCashAmount > 0) {
                    Ext.getCmp('totalCashAmount_unId').setValue((totalCashAmount * 10000 + untotalCashAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalCashAmount_unId').setValue(totalCashAmount * 1);
                }
                if (untotalCardNum > 0) {
                    Ext.getCmp('totalCardNum_unId').setValue(totalCardNum * 1 + untotalCardNum * 1);
                } else {
                    Ext.getCmp('totalCardNum_unId').setValue(totalCardNum * 1);
                }
                if (untotalCardAmount > 0) {
                    Ext.getCmp('totalCardAmount_unId').setValue((totalCardAmount * 10000 + untotalCardAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalCardAmount_unId').setValue(totalCardAmount * 1);
                }
                if (untotalTelegpaphTransferNum > 0) {
                    Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(totalTelegpaphTransferNum * 1 + untotalTelegpaphTransferNum * 1);
                } else {
                    Ext.getCmp('totalTelegpaphTransferNum_unId').setValue(totalTelegpaphTransferNum * 1);
                }
                if (untotalTelegpaphTransferAmount > 0) {
                    Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue((totalTelegpaphTransferAmount * 10000 + untotalTelegpaphTransferAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalTelegpaphTransferAmount_unId').setValue(totalTelegpaphTransferAmount * 1);
                }
                if (untotalNum > 0) {
                    Ext.getCmp('totalNum_unId').setValue(totalNum * 1 + untotalNum * 1);
                } else {
                    Ext.getCmp('totalNum_unId').setValue(totalNum * 1);
                }
                if (untotalAmount > 0) {
                    Ext.getCmp('totalAmount_unId').setValue((totalAmount * 10000 + untotalAmount * 10000) / 10000);
                } else {
                    Ext.getCmp('totalAmount_unId').setValue(totalAmount * 1);
                }

                Ext.getCmp('totalCashNum_Id').setValue("0");
                Ext.getCmp('totalCashAmount_Id').setValue("0");
                Ext.getCmp('totalCardNum_Id').setValue("0");
                Ext.getCmp('totalCardAmount_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferNum_Id').setValue("0");
                Ext.getCmp('totalTelegpaphTransferAmount_Id').setValue("0");
                Ext.getCmp('totalNum_Id').setValue("0");
                Ext.getCmp('totalAmount_Id').setValue("0");
                //添加到已选择客户store里
                chooseStores.insert(0, selections);
                Ext.getCmp("Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id").store.removeAll();
            }
        }]
    }]
});


/**
 * 列表布局
 */
Ext.define('Foss.PayCashCashierConfirm.PayCashCashierConfirmPanel', {
    extend: 'Ext.panel.Panel',
    frame: true,
    height: 560,
    layout: 'column',
    items: [Ext.create('Foss.PayCashCashierConfirm.PayUnCashCashierConfirmQueryInfoGrid', {
        id: 'Foss_PayCashCashierConfirm_PayUnCashCashierConfirmQueryInfoGrid_Id',
        height: 480,
        columnWidth: .46
    }), Ext.create('Foss.PayCashCashierConfirm.ButtonPanelRole', {
        id: 'Foss_PayCashCashierConfirm_ButtonPanelRole_Id',
        height: 480,
        columnWidth: .08
    }), Ext.create('Foss.PayCashCashierConfirm.PayCashCashierConfirmQueryInfoGrid', {
        id: 'Foss_PayCashCashierConfirm_PayCashCashierConfirmQueryInfoGrid_Id',
        columnWidth: .46,
        height: 480
    })],
    initComponent: function () {
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            items: [{
                xtype: 'container',
                html: '&nbsp;',
                columnWidth: .8
            }, {
                xtype: 'button',
                text: pay.cashCashierConfirm.i18n('foss.stl.pay.common.export'),
                disabled: !pay.cashCashierConfirm.isPermission('/stl-web/pay/billCashCashierConfirmsExport.action'),
                hidden: !pay.cashCashierConfirm.isPermission('/stl-web/pay/billCashCashierConfirmsExport.action'),
                columnWidth: .08,
                handler: pay.cashCashierConfirm.billCashCashierConfirmsExport
            }, {
                xtype: 'container',
                html: '&nbsp;',
                columnWidth: .01
            }, {
                xtype: 'button',
                text: pay.cashCashierConfirm.i18n('foss.stl.pay.cashCashierConfirm.cashCashierConfirmCommit'),
                disabled: !pay.cashCashierConfirm.isPermission('/stl-web/pay/cashCashierConfirmCommit.action'),
                hidden: !pay.cashCashierConfirm.isPermission('/stl-web/pay/cashCashierConfirmCommit.action'),
                columnWidth: .08,
                handler: pay.cashCashierConfirm.cashCashierConfirmCommit
            }]
        }]
        me.callParent();
    }
});
pay.cashCashierConfirm.QueryInfoTab = Ext.create('Foss.PayCashCashierConfirm.PayCashCashierConfirmQueryInfoTab');
//初始化界面
Ext.onReady(function () {
    Ext.QuickTips.init();

    var payCashCashierConfirmPanel = Ext.create('Foss.PayCashCashierConfirm.PayCashCashierConfirmPanel');
    Ext.create('Ext.panel.Panel', {
        id: 'T_pay-cashCashierConfirm_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getGrid: function () {
            return payCashCashierConfirmQueryInfoGrid;
        },
        items: [pay.cashCashierConfirm.QueryInfoTab, payCashCashierConfirmPanel],
        renderTo: 'T_pay-cashCashierConfirm-body',
        listeners: {
            'boxready': function () {
                if (pay.cashCashierConfirm.billingGroup == "N") {
                    pay.cashCashierConfirm.QueryInfoTab.items.items[0].down('form').getForm().findField("collectionOrgDetial").setDisabled(true);
                }
            }
        }
    });
});