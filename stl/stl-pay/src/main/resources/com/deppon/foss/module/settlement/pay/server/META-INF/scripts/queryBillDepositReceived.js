/**
 * 提示信息
 *
 * @param {}
 *            message
 * @param {}
 *            yesFn
 * @param {}
 *            noFn
 */
pay.depositReceived.billDepositReceivedConfirmAlert = function (message, yesFn, noFn) {
    Ext.Msg.confirm(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
        message, function (o) {
            if (o == 'yes') {
                yesFn();
            } else {
                noFn();
            }
        });
};

pay.depositReceived.QUERY_BYDATE = 'TD';// 按客户查询
pay.depositReceived.QUERY_BYNUMBER = 'DERE';// 按单号查询
pay.depositReceived.DEPOSIT_RECEIVED_ACCOUNTING = "DEA";
pay.depositReceived.DEPOSIT_RECEIVED_CASHIER = "DEC";

/**
 * 按日期,客户查询
 */
pay.depositReceived.queryBillDepositReceivedEntityParams = function (form, me) {
    pay.depositReceived.startBusinessDate = form.findField('startDatepay')
        .getValue();
    pay.depositReceived.endBusinessDate = form.findField('endDatepay')
        .getValue();
    pay.depositReceived.generatingOrgCodeLast = form
        .findField('generatingOrgDetial').lastValue;
    pay.depositReceived.paymentType = form.findField('paymentType').getValue();
    pay.depositReceived.customerDetialCode = form
        .findField('pay.depositReceived.commoncustomerselector').getValue();
    pay.depositReceived.agencyDetialPACode = form
        .findField('pay.depositReceived.commonvehagencycompselector')
        .getValue();
    pay.depositReceived.agencyDetialAACode = form
        .findField('pay.depositReceived.commonairagencycompanyselector')
        .getValue();
    pay.depositReceived.landStowage = form
        .findField('pay.depositReceived.landStowage').getValue();
    pay.depositReceived.partner = form
        .findField('pay.depositReceived.commonsaledepartmentselector').getValue();
    pay.depositReceived.activeStatus = form.findField('activeStatus')
        .getValue();
    pay.depositReceived.isRedBack = form.findField('isRedBack').getValue();

    pay.depositReceived.isInit = form.findField('isInit').getValue();
    pay.depositReceived.queryByTab = pay.depositReceived.QUERY_BYDATE;
    if (pay.depositReceived.startBusinessDate == null
        || pay.depositReceived.startBusinessDate == '') {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.dateIsNotNull'));
        return false;
    }

    if (pay.depositReceived.endBusinessDate == null
        || pay.depositReceived.endBusinessDate == '') {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.dateIsNotNull'));
        return false;
    }
    var generatingOrgCode = "";
    if (pay.depositReceived.generatingOrgCodeLast == stl.currentDept.name) {
        generatingOrgCode = stl.currentDept.code;
    } else {
        generatingOrgCode = pay.depositReceived.generatingOrgCodeLast;
    }

    var compareTwoDate = stl.compareTwoDate(
        pay.depositReceived.startBusinessDate,
        pay.depositReceived.endBusinessDate);
    if (compareTwoDate > 90) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.queryDateMaxLimit'));
        return false;
    } else if (compareTwoDate < 1) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
        return false;
    }
    var customerCode = "";
    if (pay.depositReceived.customerDetialCode != null
        && pay.depositReceived.customerDetialCode != "") {
        customerCode = pay.depositReceived.customerDetialCode;
    } else if (pay.depositReceived.agencyDetialPACode != null
        && pay.depositReceived.agencyDetialPACode != "") {
        customerCode = pay.depositReceived.agencyDetialPACode;
    } else if (pay.depositReceived.agencyDetialAACode != null
        && pay.depositReceived.agencyDetialAACode != "") {
        customerCode = pay.depositReceived.agencyDetialAACode;
    } else if (pay.depositReceived.landStowage != null
        && pay.depositReceived.landStowage != "") {
        customerCode = pay.depositReceived.landStowage
    }else if (pay.depositReceived.partner != null
        && pay.depositReceived.partner != "") {
        customerCode = pay.depositReceived.partner
    }
    Ext.getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store
        .on('beforeload', function (store, operation, eOpts) {
            Ext.apply(operation, {
                params: {
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate': pay.depositReceived.startBusinessDate,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate': pay.depositReceived.endBusinessDate,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode': generatingOrgCode,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType': pay.depositReceived.paymentType,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode': customerCode,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.active': pay.depositReceived.activeStatus,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.isInit': pay.depositReceived.isInit,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack': pay.depositReceived.isRedBack,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.queryByTab
                }
            });
        });
    if (form.isValid()) {
        // 设置该按钮灰掉
        me.disable(false);
        // 30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext
            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store
            .loadPage(1, {
                callback: function (records, operation, success) {
                    var rawData = Ext
                        .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store.proxy.reader.rawData;
                    // 当success:false ,isException:false --业务异常
                    if (!success && !rawData.isException) {
                        Ext.Msg.alert(pay.depositReceived
                                .i18n('foss.stl.pay.common.alert'),
                            rawData.message);
                        me.enable(true);
                        return false;
                    }
                    if (success) {
                        var result = Ext
                            .decode(operation.response.responseText);
                        Ext
                            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalRows_Id')
                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalNum);
                        Ext
                            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalAmount_Id')
                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalAmount);
                    }
                    me.enable(true);
                }
            });
    } else {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

/**
 * 按预收单号查询
 *
 * @returns {Boolean}
 */
pay.depositReceived.queryByDepositReceivedNOs = function (form, me) {
    pay.depositReceived.depositReceivedNo = form
        .findField('depositReceivedNos').getValue();
    pay.depositReceived.queryByTab = pay.depositReceived.QUERY_BYNUMBER;
    // 单号不能为空
    if (pay.depositReceived.depositReceivedNo == null
        || pay.depositReceived.depositReceivedNo == '') {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.queryNoIsNotNull'));
        return false;
    }
    // 单号个数不能超过10个
    var depositReceivedNosStr = new Array();
    depositReceivedNosStr = stl
        .splitToArray(pay.depositReceived.depositReceivedNo);
    if (depositReceivedNosStr.length > 10) {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived.i18n('foss.stl.pay.common.queryNosLimit'));
        return false;
    }
    Ext.getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store
        .on('beforeload', function (store, operation, eOpts) {
            Ext.apply(operation, {
                params: {
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo': pay.depositReceived.depositReceivedNo,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.queryByTab
                }
            });
        });
    if (form.isValid()) {
        // 设置该按钮灰掉
        me.disable(false);
        // 30秒后自动解除灰掉效果
        setTimeout(function () {
            me.enable(true);
        }, 30000);
        Ext
            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store
            .loadPage(1, {
                callback: function (records, operation, success) {
                    var rawData = Ext
                        .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store.proxy.reader.rawData;
                    // 当success:false ,isException:false --业务异常
                    if (!success && !rawData.isException) {
                        Ext.Msg.alert(pay.depositReceived
                                .i18n('foss.stl.pay.common.alert'),
                            rawData.message);
                        return false;
                    }
                    if (success) {
                        var result = Ext
                            .decode(operation.response.responseText);
                        Ext
                            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalRows_Id')
                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalNum);
                        Ext
                            .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalAmount_Id')
                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalAmount);
                    }
                    me.enable(true);
                }

            });

    } else {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.common.validateFailAlert'));
        me.enable(true);
        return false;
    }
}

// 新增预收单
pay.depositReceived.addDepositReceived = function () {
    // 获取弹出窗口
    var win = Ext.create('Foss.DepositReceived.DepositReceivedAddWindow', {
        id: 'Foss_DepositReceived_DepositReceivedAddWindow_ID'
    });

    var deptAdd = win.child('form').getForm().findField('generatingOrgDetail');

    if (pay.depositReceived
            .isPermission('/stl-web/pay/addBillDepositReceivedAccount.action')) {
        deptAdd.setDisabled(false);
        deptAdd.setValue("");
    }

    win.show();
}
pay.depositReceived.addBillDepositReceived = function (form, cashierAccounting, me) {
    var generatingOrgName = form.findField('generatingOrgDetail').getRawValue();
    var generatingOrgCodeLast = form.findField('generatingOrgDetail').lastValue;
    var customerDetialCode = form.findField('customerDetail').getValue();
    var customerDetialName = form.findField('customerDetail').getRawValue();
    var agencyDetialPACode = form.findField('agencyDetialPA').getValue();
    var agencyDetialPAName = form.findField('agencyDetialPA').getRawValue();
    var agencyDetialAACode = form.findField('agencyDetialAA').getValue();
    var agencyDetialAAName = form.findField('agencyDetialAA').getRawValue();
    var landStowageCode = form.findField('landStowage').getValue();
    var landStowageName = form.findField('landStowage').getRawValue();
    var paymentTypeAdd = form.findField('paymentTypeAdd').getValue();
    var transportType = form.findField('transportType').getValue();
    var remitNo = Ext.String.trim(form.findField('remitNo').getValue());
    var onlinePayCode = Ext.String.trim(form.findField('onlinePayCode').getValue());
    var unionpayNo = Ext.String.trim(form.findField('unionpayNo').getValue());
    var amount = form.findField('amount').getValue();
    var notes = form.findField('notes').getValue();
    var customerCode = "";
    if (customerDetialCode != "" && customerDetialCode != null) {
        customerCode = customerDetialCode;
    } else if (agencyDetialPACode != "" && agencyDetialPACode != null) {
        customerCode = agencyDetialPACode;
    } else if (agencyDetialAACode != "" && agencyDetialAACode != null) {
        customerCode = agencyDetialAACode;
    } else if (landStowageCode != null && landStowageCode != "") {
        customerCode = landStowageCode;
    } else {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.customerCodeNotNull'));
        return false;
    }
    var customerName = "";
    if (customerDetialName != "" && customerDetialName != null) {
        customerName = customerDetialName;
    } else if (agencyDetialPAName != "" && agencyDetialPAName != null) {
        customerName = agencyDetialPAName;
    } else if (agencyDetialAAName != "" && agencyDetialAAName != null) {
        customerName = agencyDetialAAName;
    } else if (!Ext.isEmpty(landStowageName)) {
        customerName = landStowageName;
    } else {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.customerNameNotNull'));
        return false;
    }
    var generatingOrgCode = "";
    if (generatingOrgCodeLast == stl.currentDept.name) {
        generatingOrgCode = stl.currentDept.code;
    } else {
        generatingOrgCode = generatingOrgCodeLast;
    }
    if (paymentTypeAdd == "" || paymentTypeAdd == null) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.paymentTypeText'));
        return false;
    }
    if (paymentTypeAdd == "TT" || paymentTypeAdd == "NT") {
        if (remitNo == "") {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.remitNoNotNull'));
            return false;
        }
        if (amount > pay.depositReceived.noCancelAmount) {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.amountNotGreaterThanRemainingAmount'));
            return false;
        }
    }
    /*
     网上支付编码放入remitNo
     */
    if (paymentTypeAdd == "OL") {
        if (onlinePayCode == "") {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.onlineNoNotNull'));
            return false;
        }
        remitNo = onlinePayCode;
    }
    /*
     银联交易流水号编码放入remitNo
     */
    if (paymentTypeAdd == "CD") {
        if (unionpayNo == "") {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.unionpayNoIsNull'));
            return false;
        }
        remitNo = unionpayNo;
    }
    if (paymentTypeAdd == "CD" || paymentTypeAdd == "CH"
        || paymentTypeAdd == "OL") {
        if (amount <= 0) {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.amountGreaterThanZero'));
            return false;
        }
    }
    if (notes.length >= 300) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.notesNotGreaterThanZero300'));
        return false;
    }
    if (form.isValid()) {
        var params = {
            'billDepositReceivedPayVo.billDepositReceivedPayDto.cashierAccounting': cashierAccounting,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode': generatingOrgCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgName': generatingOrgName,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode': customerCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.customerName': customerName,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType': paymentTypeAdd,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.transportType': transportType,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.remitNo': remitNo,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.amount': amount,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.notes': notes
        }
        var yesFn = function () {
            // 设置该按钮灰掉
            me.disable(false);
            // 30秒后自动解除灰掉效果
            setTimeout(function () {
                me.enable(true);
            }, 30000);
            Ext.Ajax.request({
                url: pay.realPath('addBillDepositReceived.action'),
                params: params,
                success: function (response) {
                    form.reset();
                    Ext.Msg
                        .alert(
                        pay.depositReceived
                            .i18n('foss.stl.pay.common.alert'),
                        pay.depositReceived
                            .i18n('foss.stl.pay.addBillDepositReceived.saveSuccess'));
                    me.enable(true);
                },
                exception: function (response) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert(pay.depositReceived
                            .i18n('foss.stl.pay.common.alert'),
                        result.message);
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
        pay.depositReceived.billDepositReceivedConfirmAlert(pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.isSave'),
            yesFn, noFn);
    } else {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

pay.depositReceived.addBillDepositReceivedAccount = function (form, cashierAccounting, me) {
    var generatingOrgName = form.findField('generatingOrgDetail').getRawValue();
    var generatingOrgCodeLast = form.findField('generatingOrgDetail').lastValue;
    var customerDetialCode = form.findField('customerDetail').getValue();
    var customerDetialName = form.findField('customerDetail').getRawValue();
    var agencyDetialPACode = form.findField('agencyDetialPA').getValue();
    var agencyDetialPAName = form.findField('agencyDetialPA').getRawValue();
    var agencyDetialAACode = form.findField('agencyDetialAA').getValue();
    var agencyDetialAAName = form.findField('agencyDetialAA').getRawValue();
    var landStowageCode = form.findField('landStowage').getValue();
    var landStowageName = form.findField('landStowage').getRawValue();
    var paymentTypeAdd = form.findField('paymentTypeAdd').getValue();
    var transportType = form.findField('transportType').getValue();
    var remitNo = Ext.String.trim(form.findField('remitNo').getValue());
    var onlinePayCode = Ext.String.trim(form.findField('onlinePayCode').getValue());
    var unionpayNo = Ext.String.trim(form.findField('unionpayNo').getValue());
    var amount = form.findField('amount').getValue();
    var notes = form.findField('notes').getValue();
    var customerCode = "";
    if (customerDetialCode != "" && customerDetialCode != null) {
        customerCode = customerDetialCode;
    } else if (agencyDetialPACode != "" && agencyDetialPACode != null) {
        customerCode = agencyDetialPACode;
    } else if (agencyDetialAACode != "" && agencyDetialAACode != null) {
        customerCode = agencyDetialAACode;
    } else if (!Ext.isEmpty(landStowageCode)) {
        customerCode = landStowageCode;
    } else {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.customerCodeNotNull'));
        return false;
    }
    var customerName = "";
    if (customerDetialName != "" && customerDetialName != null) {
        customerName = customerDetialName;
    } else if (agencyDetialPAName != "" && agencyDetialPAName != null) {
        customerName = agencyDetialPAName;
    } else if (agencyDetialAAName != "" && agencyDetialAAName != null) {
        customerName = agencyDetialAAName;
    } else if (!Ext.isEmpty(landStowageName)) {
        customerName = landStowageName;
    } else {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.customerNameNotNull'));
        return false;
    }
    var generatingOrgCode = "";
    if (generatingOrgCodeLast == stl.currentDept.name) {
        generatingOrgCode = stl.currentDept.code;
    } else {
        generatingOrgCode = generatingOrgCodeLast;
    }
    if (paymentTypeAdd == "" || paymentTypeAdd == null) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.paymentTypeText'));
        return false;
    }
    /*
     网上支付编码放入remitNo
     */
    if (paymentTypeAdd == "OL") {
        if (onlinePayCode == "") {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.onlineNoNotNull'));
            return false;
        }
        remitNo = onlinePayCode;
    }
    
    /*
    银联交易流水号编码放入remitNo
    */
   if (paymentTypeAdd == "CD") {
       if (unionpayNo == "") {
           Ext.Msg
               .alert(
               pay.depositReceived
                   .i18n('foss.stl.pay.common.alert'),
               pay.depositReceived
                   .i18n('foss.stl.pay.addBillDepositReceived.unionpayNoIsNull'));
           return false;
       }
       remitNo = unionpayNo;
   }
    if (paymentTypeAdd == "TT" || paymentTypeAdd == "NT") {
        if (remitNo == "") {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.remitNoNotNull'));
            return false;
        }
        if (amount > pay.depositReceived.noCancelAmount) {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.amountNotGreaterThanRemainingAmount'));
            return false;
        }
    }
    if (paymentTypeAdd == "CD" || paymentTypeAdd == "CH"
        || paymentTypeAdd == "OL") {
        if (amount <= 0) {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                pay.depositReceived
                    .i18n('foss.stl.pay.addBillDepositReceived.amountGreaterThanZero'));
            return false;
        }
    }
    if (notes.length >= 300) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.notesNotGreaterThanZero300'));
        return false;
    }
    if (form.isValid()) {
        var params = {
            'billDepositReceivedPayVo.billDepositReceivedPayDto.cashierAccounting': cashierAccounting,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode': generatingOrgCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgName': generatingOrgName,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode': customerCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.customerName': customerName,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType': paymentTypeAdd,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.transportType': transportType,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.remitNo': remitNo,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.amount': amount,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.notes': notes
        }
        var yesFn = function () {
            // 设置该按钮灰掉
            me.disable(false);
            // 30秒后自动解除灰掉效果
            setTimeout(function () {
                me.enable(true);
            }, 30000);
            Ext.Ajax.request({
                url: pay.realPath('addBillDepositReceivedAccount.action'),
                params: params,
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    form.reset();
                    Ext.Msg
                        .alert(
                        pay.depositReceived
                            .i18n('foss.stl.pay.common.alert'),
                        pay.depositReceived
                            .i18n('foss.stl.pay.billDepositReceived.depositReceived')
                        + result.billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo
                        + pay.depositReceived
                            .i18n('foss.stl.pay.addBillDepositReceived.saveSuccess'));
                    me.enable(true);
                },
                exception: function (response) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert(pay.depositReceived
                            .i18n('foss.stl.pay.common.alert'),
                        result.message);
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
        pay.depositReceived.billDepositReceivedConfirmAlert(pay.depositReceived
                .i18n('foss.stl.pay.addBillDepositReceived.isSave'),
            yesFn, noFn);
    } else {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.common.validateFailAlert'));
        return false;
    }
}

// 导出预收单
pay.depositReceived.depositReceivedListExport = function () {
    // 获取表格
    grid = Ext
        .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id');
    // 如果没有查询到数据，则不能进行导出操作
    if (grid.store.data.length == 0) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.exportIsNotNull'));
        return false;
    }

    if (!Ext.fly('downloadAttachFileForm')) {
        var frm = document.createElement('form');
        frm.id = 'downloadAttachFileForm';
        frm.style.display = 'none';
        document.body.appendChild(frm);
    }
    var generatingOrgCode = "";
    if (pay.depositReceived.generatingOrgCodeLast == stl.currentDept.name) {
        generatingOrgCode = stl.currentDept.code;
    } else {
        generatingOrgCode = pay.depositReceived.generatingOrgCodeLast;
    }
    var customerCode = "";
    if (pay.depositReceived.customerDetialCode != null
        && pay.depositReceived.customerDetialCode != "") {
        customerCode = pay.depositReceived.customerDetialCode;
    } else if (pay.depositReceived.agencyDetialPACode != null
        && pay.depositReceived.agencyDetialPACode != "") {
        customerCode = pay.depositReceived.agencyDetialPACode;
    } else if (pay.depositReceived.agencyDetialAACode != null
        && pay.depositReceived.agencyDetialAACode != "") {
        customerCode = pay.depositReceived.agencyDetialAACode;
    } else if (pay.depositReceived.landStowage != null
        && pay.depositReceived.landStowage != "") {
        customerCode = pay.depositReceived.landStowage;
    }else if (pay.depositReceived.partner != null
        && pay.depositReceived.partner != "") {
        customerCode = pay.depositReceived.partner
    }
    if (pay.depositReceived.queryByTab == pay.depositReceived.QUERY_BYDATE) {
        searchParams = {
            'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate': pay.depositReceived.startBusinessDate,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate': pay.depositReceived.endBusinessDate,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode': generatingOrgCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType': pay.depositReceived.paymentType,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode': customerCode,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.active': pay.depositReceived.activeStatus,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.isInit': pay.depositReceived.isInit,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack': pay.depositReceived.isRedBack,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.queryByTab
        }
    } else if (pay.depositReceived.queryByTab == pay.depositReceived.QUERY_BYNUMBER) {
        searchParams = {
            'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo': pay.depositReceived.depositReceivedNo,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.queryByTab
        }
    }

    var yesFn = function () {
        Ext.Ajax.request({
            url: pay.realPath('depositReceivedListExport.action'),
            form: Ext.fly('downloadAttachFileForm'),
            params: searchParams,
            isUpload: true,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                // 如果异常信息有值，则弹出提示
                if (!Ext.isEmpty(result.errorMessage)) {
                    Ext.Msg.alert(pay.depositReceived
                            .i18n('foss.stl.pay.common.alert'),
                        result.errorMessage);
                    return false;
                }
                Ext.ux.Toast
                    .msg(
                    pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.exportSuccess'),
                    'success', 1000);
            },
            failure: function (response) {
                Ext.ux.Toast
                    .msg(
                    pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.exportError'),
                    'error', 1000);
            }
        });
    };
    var noFn = function () {
        return false;
    };
    pay.depositReceived.billDepositReceivedConfirmAlert(pay.depositReceived
            .i18n('foss.stl.pay.billDepositReceived.isExport'), yesFn,
        noFn);
}
// 打印预收单
pay.depositReceived.printBillDepositReceived = function (grid, rowIndex, colIndex) {
    var params = grid.getStore().getAt(rowIndex).data;
    var isRedBack = params.isRedBack;
    var active = params.active;

    if (isRedBack == "Y") {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            '红单不允许进行打印操作,请操作非红单数据!');
        return false;
    } else if (active == "N") {
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            '无效单据不允许进行打印操作,请操作有效数据!');
        return false;
    }
    // 打印
    do_printpreview('billdepositreceived', params, ContextPath.STL_WEB);
}

// 作废预收单
pay.depositReceived.writeBackBillDepositReceived = function (grid, rowIndex, colIndex) {
    var selectionDepositReceived = grid.getStore().getAt(rowIndex);
    var depositReceivedNo = selectionDepositReceived.get("depositReceivedNo");
    var active = selectionDepositReceived.get("active");
    var amount = selectionDepositReceived.get("amount");
    //判断是否是合伙人的如果是则不允许作废
    var transportType=selectionDepositReceived.get("transportType");
    if(transportType=='P'){
        Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),pay.depositReceived.i18n('foss.stl.pay.common.notPartnerInvalid'))
        return false;
    }
    var yesFn = function () {
        var params = {
            'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo': depositReceivedNo,
            'billDepositReceivedPayVo.billDepositReceivedPayDto.active': active
        }
        Ext.Ajax.request({

            url: pay.realPath('writeBackBillDepositReceived.action'),
            params: params,
            success: function (response) {
                Ext
                    .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id').store
                    .load({
                        callback: function (records, operation, success) {
                            var result = Ext
                                .decode(operation.response.responseText);
                            if (result.isException) {
                                Ext.Msg
                                    .alert(
                                    pay.depositReceived
                                        .i18n('foss.stl.pay.common.alert'),
                                    result.message);
                                return false;
                            }
                            Ext
                                .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalRows_Id')
                                .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalNum);
                            Ext
                                .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalAmount_Id')
                                .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.totalAmount);
                        }

                    });
                Ext.Msg
                    .alert(
                    pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.disableSuccess'));
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    result.message);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
            }
        });
    };
    var noFn = function () {
        return false;
    };
    pay.depositReceived
        .billDepositReceivedConfirmAlert(
        pay.depositReceived
            .i18n('foss.stl.pay.billDepositReceived.depositReceivedNo')
        + depositReceivedNo
        + pay.depositReceived
            .i18n('foss.stl.pay.billDepositReceived.isDisableAmountText')
        + amount
        + pay.depositReceived
            .i18n('foss.stl.pay.billDepositReceived.isDisable'),
        yesFn, noFn);
}

pay.depositReceived.detailDepositReceived = function (grid, rowIndex, colIndex) {
    // 获取选中的还款单数据
    var selection = grid.getStore().getAt(rowIndex);

    // 获取弹出窗口
    win = Ext.getCmp('Deposit_Received_Detail_Window_Id');
    if (Ext.isEmpty(win)) {
        win = Ext.create('Foss.DepositReceived.DepositReceivedDetailWindow', {
            id: 'Deposit_Received_Detail_Window_Id'
        });
    }

    var form = win.down('form').getForm();

    //发票标记
    if (selection.get('invoiceMark') != null) {
        var value = selection.get('invoiceMark');
        var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');
        selection.set('invoiceMark', displayField);
    }
    // 加载数据
    win.items.items[0].loadRecord(selection);
    if(selection.get('paymentType') == 'FCUS'){
    	form.findField('paymentType').setValue('到付转预收');
    }
    else if(selection.get('paymentType') == 'CF'){
    	form.findField('paymentType').setValue('委托派费转预收');
    }else if(selection.get('paymentType') == 'KTU'){
    	form.findField('paymentType').setValue('快递差错应付转预收');
    }else if(selection.get('paymentType') == 'JTU'){
    	form.findField('paymentType').setValue('奖励应付转预收');
    }
    win.show();
}

// 预收单退预收
pay.depositReceived.backDepositReceived = function () {

    var selectionDepositReceived = Ext
        .getCmp('Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id')
        .getSelectionModel().getSelection();

    if (selectionDepositReceived.length == 0) {
        Ext.Msg
            .alert(
            pay.depositReceived.i18n('foss.stl.pay.common.alert'),
            pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.backDepositReceivedText'));
        return false;
    }
    var jsonDataDepositReceived = new Array();
    for (var i = 0; i < selectionDepositReceived.length; i++) {
        var active = selectionDepositReceived[i].get("active");
        var depositReceivedNo = selectionDepositReceived[i]
            .get("depositReceivedNo");
        if (active == 'N') {
            Ext.Msg
                .alert(
                pay.depositReceived
                    .i18n('foss.stl.pay.common.alert'),
                depositReceivedNo
                + pay.depositReceived
                    .i18n('foss.stl.pay.billDepositReceived.backDepositReceivedActive'));
            return false;
        }
        jsonDataDepositReceived.push(selectionDepositReceived[i].data);
    }
    var entity = new Object();

    entity.billDepositReceivedEntities = jsonDataDepositReceived;
    var yesFn = function () {
        Ext.Ajax.request({
            url: pay.realPath('backDepositReceived.action'),
            jsonData: {
                'billDepositReceivedPayVo': entity
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                // 获取付款单窗口
                if (Ext.isEmpty(pay.depositReceived.addPaymentWindow)) {
                    pay.depositReceived.addPaymentWindow = Ext
                        .create('Foss.payment.AddPaymentWindow');
                }
                var win = pay.depositReceived.addPaymentWindow;
                var formModel = new Foss.payable.AddFormModel(result.billDepositReceivedPayVo.billDepositReceivedPayDto.paymentEntity);
                // 设置付款单弹出界面来源界面 为预付单
                formModel.set('sourceBillType',
                    pay.SOURCE_BILL_TYPE__DEPOSIT_RECEIVED);// 预付
                win.getAddFormPanel().loadRecord(formModel);
                win.getAddPaymentEntryGrid().store
                    .loadData(result.billDepositReceivedPayVo.billDepositReceivedPayDto.addDtoList);

                var payForm = win.getAddFormPanel().getForm();
                var addpaymentEntryGrid = win.getAddPaymentEntryGrid();

                //获取业务发生日期。临时租车要控制编辑和不可编辑
                var invoceTax = addpaymentEntryGrid.columns[7];
                var tax = addpaymentEntryGrid.columns[8];
                var businessOfDate = addpaymentEntryGrid.columns[9];
                //如果用户先支付了临时租车，再做预收，会有问题。此处需要重置该参数
                pay.containRentCar = null;

                payForm.findField('costDeptCode').hide();
                payForm.findField('costType').hide();
                payForm.findField('displayfield1').hide();
                payForm.findField('isTaxinvoice').hide();
                payForm.findField('costDeptCode').setValue(null);
                invoceTax.hide();
                tax.hide();
                businessOfDate.hide();

                // 绑定发票抬头 为默认
                var comCode = payForm.findField('paymentCompanyCode')
                    .getValue();
                var comName = payForm.findField('paymentCompanyName')
                    .getValue();
                // 获取发票抬头
                if (!Ext.isEmpty(comCode) && !Ext.isEmpty(comName)) {
                    payForm.findField('invoiceHeadCode').setCombValue(comName,
                        comCode);
                }
                // 弹出付款单
                win.show();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    result.message);
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
            }
        });
    };
    var noFn = function () {
        return false;
    };
    pay.depositReceived
        .billDepositReceivedConfirmAlert(
        pay.depositReceived
            .i18n('foss.stl.pay.billDepositReceived.isbackDepositReceived'),
        yesFn, noFn);
}

// 预收单model
Ext.define('Foss.PayDepositReceived.BillDepositReceivedEntityModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'depositReceivedNo'
        },
        {
            name: 'collectionOrgName'
        },
        {
            name: 'collectionOrgCode'
        },
        {
            name: 'approveStatus'
        },
        {
            name: 'collectionCompanyCode'
        },
        {
            name: 'collectionCompanyName'
        },
        {
            name: 'generatingOrgCode'
        },
        {
            name: 'generatingOrgName'
        },
        {
            name: 'customerCode'
        },
        {
            name: 'customerName'
        },
        {
            name: 'transportType'
        },
        {
            name: 'paymentType'
        },
        {
            name: 'amount',
            type: 'long'
        },
        {
            name: 'verifyAmount',
            type: 'long'
        },
        {
            name: 'unverifyAmount',
            type: 'long'
        },
        {
            name: 'paymentAmount',
            type: 'long'
        },
        {
            name: 'remitNo'
        },
        {
            name: 'refundStatus'
        },
        {
            name: 'createUserName'
        },
        {
            name: 'createUserCode'
        },
        {
            name: 'createOrgName'
        },
        {
            name: 'createOrgCode'
        },
        {
            name: 'status'
        },
        {
            name: 'active'
        },
        {
            name: 'businessDate',
            type: 'date',
            convert: function (value) {
                return stl.longToDateConvert(value);
            }
        },
        {
            name: 'accountDate',
            type: 'date',
            convert: function (value) {
                return stl.longToDateConvert(value);
            }
        },
        {
            name: 'isRedBack'
        },
        {
            name: 'isInit'
        },
        {
            name: 'invoiceMark'
        },
        {
            name: 'unifiedSettlement'
        },
        {
            name: 'contractOrgCode'
        },
        {
            name: 'contractOrgName'
        },
        {
            name: 'notes'
        }
    ]
});

// 预收单Store
Ext.define('Foss.PayDepositReceived.BillDepositReceivedEntityStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.PayDepositReceived.BillDepositReceivedEntityModel',
    proxy: {
        type: 'ajax',
        url: pay.realPath('queryDepositReceivedPage.action'),
        reader: {
            type: 'json',
            root: 'billDepositReceivedPayVo.billDepositReceivedPayDto.billDepositReceivedEntityList',
            totalProperty: 'totalCount'
        }
    },
    listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var searchParams;
            var form = Ext.getCmp('T_pay-manageBillDepositReceived_content')
                .getQueryTab().items.items[0].items.items[0].getForm();
            pay.depositReceived.startBusinessDate = form
                .findField('startDatepay').getValue();
            pay.depositReceived.endBusinessDate = form.findField('endDatepay')
                .getValue();
            pay.depositReceived.generatingOrgCodeLast = form
                .findField('generatingOrgDetial').lastValue;
            pay.depositReceived.paymentType = form.findField('paymentType')
                .getValue();
            pay.depositReceived.customerDetialCode = form
                .findField('pay.depositReceived.commoncustomerselector')
                .getValue();
            pay.depositReceived.agencyDetialPACode = form
                .findField('pay.depositReceived.commonvehagencycompselector')
                .getValue();
            pay.depositReceived.agencyDetialAACode = form
                .findField('pay.depositReceived.commonairagencycompanyselector')
                .getValue();
            pay.depositReceived.landStowage = form
                .findField('pay.depositReceived.landStowage').getValue();
            pay.depositReceived.activeStatus = form.findField('activeStatus')
                .getValue();
            pay.depositReceived.isInit = form.findField('isInit').getValue();
            pay.depositReceived.isRedBack = form.findField('isRedBack')
                .getValue();
            var generatingOrgCode = "";
            if (pay.depositReceived.generatingOrgCodeLast == stl.currentDept.name) {
                generatingOrgCode = stl.currentDept.code;
            } else {
                generatingOrgCode = pay.depositReceived.generatingOrgCodeLast;
            }
            var customerCode = "";
            if (pay.depositReceived.customerDetialCode != null
                && pay.depositReceived.customerDetialCode != "") {
                customerCode = pay.depositReceived.customerDetialCode;
            } else if (pay.depositReceived.agencyDetialPACode != null
                && pay.depositReceived.agencyDetialPACode != "") {
                customerCode = pay.depositReceived.agencyDetialPACode;
            } else if (pay.depositReceived.agencyDetialAACode != null
                && pay.depositReceived.agencyDetialAACode != "") {
                customerCode = pay.depositReceived.agencyDetialAACode;
            } else if (!Ext.isEmpty(pay.depositReceived.landStowage)) {
                customerCode = pay.depositReceived.landStowage;
            }else if (pay.depositReceived.partner != null
                && pay.depositReceived.partner != "") {
                customerCode = pay.depositReceived.partner
            }
            // 如果第一次进入界面，直接选择分页查询，则默认按照客户查询
            if (Ext.isEmpty(pay.depositReceived.queryByTab)) {
                // 获取查询条件
                searchParams = {
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.startBusinessDate': pay.depositReceived.startBusinessDate,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.endBusinessDate': pay.depositReceived.endBusinessDate,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.generatingOrgCode': generatingOrgCode,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.paymentType': pay.depositReceived.paymentType,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.customerCode': customerCode,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.active': pay.depositReceived.activeStatus,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.isInit': pay.depositReceived.isInit,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.isRedBack': pay.depositReceived.isRedBack,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.QUERY_BYDATE
                }
            } else if (pay.depositReceived.queryByTab == pay.depositReceived.QUERY_BYNUMBER) {
                searchParams = {
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.depositReceivedNo': pay.depositReceived.depositReceivedNo,
                    'billDepositReceivedPayVo.billDepositReceivedPayDto.queryByTab': pay.depositReceived.queryByTab
                }
            }
            Ext.apply(operation, {
                params: searchParams
            });
        }
    }
});

Ext.define('Foss.PayDepositReceived.PayDepositReceivedQueryInfoTab', {
    extend: 'Ext.tab.Panel',
    frame: false,
    bodyCls: 'autoHeight',
    cls: 'innerTabPanel',
    activeTab: 0,
    height: 200,
    items: [
        {
            title: pay.depositReceived.i18n('foss.stl.pay.common.queryByDate'),
            tabConfig: {
                width: 150
            },
            width: '200',
            layout: 'fit',
            items: [
                {
                    xtype: 'form',
                    defaults: {
                        margin: '5 5 5 5',
                        labelWidth: 90
                    },
                    layout: {
                        type: 'table',
                        columns: 3
                    },
                    items: [
                        {
                            xtype: 'datefield',
                            id: 'startDatepay',
                            name: 'startDate',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.startDatepay'),
                            labelWidth: 85,
                            value: stl.getTargetDate(new Date(), -0),
                            format: 'Y-m-d',
                            allowBlank: false
                        },
                        {
                            xtype: 'datefield',
                            name: 'endDatepay',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.endDatepay'),
                            labelWidth: 85,
                            format: 'Y-m-d',
                            value: new Date(),
                            allowBlank: false,
                            maxValue: new Date()
                        },
                        {
                            xtype: 'dynamicorgcombselector',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.generatingOrgDetail'),
                            name: 'generatingOrgDetial',
                            value: stl.currentDept.name,
                            labelWidth: 85,
                            listWidth: 300,// 设置下拉框宽度
                            isPaging: true
                        },
                        {
                            xtype: 'combobox',
                            name: 'paymentType',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.paymentType'),
                            labelWidth: 85,
                            editable: false,
                            store: FossDataDictionary.getDataDictionaryStore(
                                settlementDict.SETTLEMENT__PAYMENT_TYPE,
                                null,
                                [{
                                    'valueCode': '',
                                    'valueName': pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.select')}
	                                ,{'valueCode':pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__DEST_FEE,'valueName':'委托派费转预收'}
	                                ,{'valueCode':pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__DEST_ADVANCE,'valueName':'到付转预收'}
	                                ,{'valueCode':pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__JTU,'valueName':'奖励应付转预收'}
	                                ,{'valueCode':pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__KTU,'valueName':'快递差错应付转预收'}]
                                ,[
                                    pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CASH,
                                    pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CARD,
                                    pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                                    pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__NOTE,
                                    pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__ONLINE]),
                            queryModel: 'local',
                            displayField: 'valueName',
                            valueField: 'valueCode',
                            value: ''
                        },
                        {
                            xtype: 'combobox',
                            id: 'activeCom',
                            name: 'activeStatus',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.activeStatus'),
                            labelWidth: 85,
                            editable: false,
                            store: FossDataDictionary.getDataDictionaryStore(
                                settlementDict.FOSS_ACTIVE, null, {
                                    'valueCode': '',
                                    'valueName': pay.depositReceived
                                        .i18n('foss.stl.pay.common.all')
                                }),
                            queryModel: 'local',
                            displayField: 'valueName',
                            valueField: 'valueCode',
                            value: ''
                        },
                        {
                            xtype: 'combobox',
                            name: 'isInit',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.isInit'),
                            labelWidth: 85,
                            editable: false,
                            store: FossDataDictionary.getDataDictionaryStore(
                                'FOSS_BOOLEAN', null, {
                                    'valueCode': '',
                                    'valueName': pay.depositReceived
                                        .i18n('foss.stl.pay.common.all')
                                }),
                            queryModel: 'local',
                            displayField: 'valueName',
                            valueField: 'valueCode',
                            value: ''
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.customerType'),
                            name: 'transportType',
                            labelWidth: 85,
                            editable: false,
                            store: FossDataDictionary
                                .getDataDictionaryStore(
                                settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE,
                                null,
                                null,
                                [
                                    pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
                                    pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY,
                                    pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY,
                                    pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE,
                                    pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_PARTNER
                                ]),
                            queryModel: 'local',
                            displayField: 'valueName',
                            valueField: 'valueCode',
                            value: pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
                            columnWidth: .4,
                            listeners: {
                                'change': function (th, newValue, oldValue) {
                                    // 获取表单等控件
                                    var form, // 表单
                                        customerDetail, agencyDetialPA, agencyDetialAA,partnerDetialP;
                                    // 获取表单
                                    form = this.up('form').getForm();
                                    // 获取下面组件
                                    customerDetail = form
                                        .findField('pay.depositReceived.commoncustomerselector');
                                    agencyDetialPA = form
                                        .findField('pay.depositReceived.commonvehagencycompselector');
                                    agencyDetialAA = form
                                        .findField('pay.depositReceived.commonairagencycompanyselector');
                                    landStowage = form
                                        .findField('pay.depositReceived.landStowage');
                                    partnerDetialP = form
                                        .findField('pay.depositReceived.commonsaledepartmentselector');
                                    if (newValue == 'LC') {
                                        customerDetail.show();
                                        agencyDetialPA.hide();
                                        agencyDetialPA.setValue("");
                                        agencyDetialAA.hide();
                                        agencyDetialAA.setValue("");
                                        landStowage.hide();
                                        landStowage.setValue("");
                                        partnerDetialP.hide();
                                        partnerDetialP.setValue("")
                                    } else if (newValue == 'PA') {
                                        customerDetail.hide();
                                        customerDetail.setValue("");
                                        agencyDetialPA.show();
                                        agencyDetialAA.hide();
                                        agencyDetialAA.setValue("");
                                        landStowage.hide();
                                        landStowage.setValue("");
                                        partnerDetialP.hide();
                                        partnerDetialP.setValue("")
                                    } else if (newValue == 'AA') {
                                        customerDetail.hide();
                                        customerDetail.setValue("");
                                        agencyDetialPA.hide();
                                        agencyDetialPA.setValue("");
                                        agencyDetialAA.show();
                                        landStowage.hide();
                                        landStowage.setValue("");
                                        partnerDetialP.hide();
                                        partnerDetialP.setValue("")
                                    } else if (newValue == 'LS') {
                                        landStowage.show();
                                        customerDetail.hide();
                                        customerDetail.setValue("");
                                        agencyDetialPA.hide();
                                        agencyDetialPA.setValue("");
                                        agencyDetialAA.hide();
                                        agencyDetialAA.setValue("");
                                        partnerDetialP.hide();
                                        partnerDetialP.setValue("")
                                    } else if (newValue == 'P') {
                                        partnerDetialP.show();
                                        landStowage.hide();
                                        landStowage.setValue("");
                                        customerDetail.hide();
                                        customerDetail.setValue("");
                                        agencyDetialPA.hide();
                                        agencyDetialPA.setValue("");
                                        agencyDetialAA.hide();
                                        agencyDetialAA.setValue("");
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'commoncustomerselector',
                            listWidth: 300,
                            singlePeopleFlag: 'Y',
                            fieldLabel: "专线客户",
                            all: 'true',
                            name: 'pay.depositReceived.commoncustomerselector',
                            labelWidth: 85,
                            isPaging: true
                            // 分页
                        },
                        {
                            xtype: 'commonairagentselector',
                            fieldLabel: "空运代理",
                            labelWidth: 85,
                            name: 'pay.depositReceived.commonairagencycompanyselector',
                            isPaging: true
                            // 分页
                        },
                        {
                            xtype: 'commonvehagencycompselector',
                            fieldLabel: "偏线代理",
                            labelWidth: 85,
                            name: 'pay.depositReceived.commonvehagencycompselector',
                            isPaging: true
                            // 分页
                        },
                        {
                            xtype: 'commonLdpAgencyCompanySelector',
                            listWidth: 300,
                            fieldLabel: "快递代理",
                            hidden: true,
                            name: 'pay.depositReceived.landStowage',
                            labelWidth: 85,
                            isPaging: true
                            // 分页
                        },
                        {
                            xtype: 'commonsaledepartmentselector',
                            listWidth: 300,
                            fieldLabel: "事业合伙人",
                            hidden: true,
                            name: 'pay.depositReceived.commonsaledepartmentselector',
                            labelWidth: 85,
                            isPaging: true
                            // 分页
                        },
                        {
                            xtype: 'combobox',
                            name: 'isRedBack',
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.isRedBack'),
                            labelWidth: 85,
                            // columnWidth:.3,
                            editable: false,
                            store: [
                                ['', '全部'],
                                ['Y', '是'],
                                ['N', '否']
                            ],
                            displayField: 'valueName',
                            valueField: 'valueCode'
                        },
                        {
                            border: 1,
                            xtype: 'container',
                            colspan: 3,
                            defaultType: 'button',
                            layout: 'column',
                            items: [
                                {
                                    text: pay.depositReceived
                                        .i18n('foss.stl.pay.common.reset'),
                                    columnWidth: .1,
                                    handler: function () {
                                        this.up('form').getForm().reset();
                                    }
                                },
                                {
                                    xtype: 'container',
                                    border: false,
                                    columnWidth: .8,
                                    html: '&nbsp;'
                                },
                                {
                                    text: pay.depositReceived
                                        .i18n('foss.stl.pay.common.query'),
                                    cls: 'yellow_button',
                                    columnWidth: .1,
                                    handler: function () {
                                        var form = this.up('form').getForm();
                                        var me = this;
                                        pay.depositReceived
                                            .queryBillDepositReceivedEntityParams(form, me)
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            title: pay.depositReceived.i18n('foss.stl.pay.common.queryByNo'),
            tabConfig: {
                width: 200
            },
            layout: 'fit',
            items: [
                {
                    xtype: 'form',
                    defaults: {
                        margin: '5 5 5 5',
                        labelWidth: 90
                    },
                    layout: 'column',
                    items: [
                        {
                            xtype: 'textarea',
                            autoScroll: true,
                            emptyText: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.depositReceivedNoEmptyText'),
                            fieldLabel: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'),
                            name: 'depositReceivedNos',
                            height: 80,
                            allowBlank: false,
                            columnWidth: .4,
                            regex: /^((US)[0-9]{7,10}[;,]?)+$/i,
                            regexText: '输入US加上7-10位以内的数字编号'
                        },
                        {
                            border: 1,
                            xtype: 'container',
                            columnWidth: 1,
                            defaultType: 'button',
                            layout: 'column',
                            items: [
                                {
                                    text: pay.depositReceived
                                        .i18n('foss.stl.pay.common.reset'),
                                    columnWidth: .1,
                                    handler: function () {
                                        this.up('form').getForm().reset();
                                    }
                                },
                                {
                                    xtype: 'container',
                                    border: false,
                                    columnWidth: .5,
                                    html: '&nbsp;'
                                },
                                {
                                    text: pay.depositReceived
                                        .i18n('foss.stl.pay.common.query'),
                                    cls: 'yellow_button',
                                    columnWidth: .1,
                                    handler: function () {
                                        var form = this.up('form').getForm();
                                        var me = this;
                                        pay.depositReceived.queryByDepositReceivedNOs(form, me)
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
});

/*
 * 发票标记
 */
var invoiceMarkStore = Ext.create('Ext.data.Store', {
    fields: ['value', 'text'],
    data: [
        {
            'value': 'INVOICE_TYPE_01',
            'text': '01运输专票11%'
        },
        {
            'value': 'INVOICE_TYPE_02',
            'text': '02-非运输专票'
        }
    ]
});
// 查询处理客户发票标记
pay.depositReceived.getCustInvoiceMark = function (custCmp) {
    var form = custCmp.up('form').getForm();
    var cmp = form.findField('invoiceMark');
    var us = form.findField('unifiedSettlement');
    var contract = form.findField('contractOrg');
    var paymentTypeAdd = form.findField('paymentTypeAdd');
    var custInvoiceMarkEntity = {};
    custInvoiceMarkEntity.custNumber = form.findField('customerDetail')
        .getValue();
    if (null == custInvoiceMarkEntity.custNumber || custInvoiceMarkEntity.custNumber.length > 4) {
        Ext.Ajax.request({
            url: ContextPath.STL_WEB + '/consumer/queryCustInvoiceMark.action',
            jsonData: {
                'custInvoiceMarkEntity': custInvoiceMarkEntity
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                cmp.setValue(result.custInvoiceMarkEntity.invoiceMark);
                if (result.custInvoiceMarkEntity.invoiceMark == 'INVOICE_TYPE_02'
                    && result.custInvoiceMarkEntity.unifiedSettlement == 'Y') {
                    us.setValue(pay.depositReceived.i18n('foss.stl.pay.common.yes'));
                    contract.setValue(result.custInvoiceMarkEntity.contractOrgName);
                    //paymentTypeAdd.setValue(pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
                    //paymentTypeAdd.setReadOnly(true);
                } else {
                    //if (paymentTypeAdd.readOnly) {
                    //    paymentTypeAdd.setReadOnly(false);
                    //}
                    us.setValue(pay.depositReceived.i18n('foss.stl.pay.common.no'));
                    contract.setValue('');
                }
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    result.message);
                cmp.setValue('INVOICE_TYPE_02');
            },
            failure: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(pay.depositReceived
                        .i18n('foss.stl.pay.common.alert'),
                    result.message);
                cmp.setValue('INVOICE_TYPE_02');
            }
        });
    } else {
        cmp.setValue('INVOICE_TYPE_02');
    }
}

// 新增window
Ext.define('Foss.DepositReceived.DepositReceivedAddWindow', {
    extend: 'Ext.window.Window',
    title: pay.depositReceived
        .i18n('foss.stl.pay.addBillDepositReceived.saveDepositReceivedTitle'),
    width: 800,
    layout: 'fit',
    modal: true,
    //cls: 'innerTabPanel',
    items: [
        {
            xtype: 'form',
            frame: false,
            defaults: {
                padding: 10,
                labelWidth: 110,
                columnWidth: .5
            },
            style: {
                backgroundImage: 'url(../images/foss/tiny/content_bg.gif)'
            },
            bodyCls: 'autoHeight',
            cls: 'innerTabPanel',
            layout: 'column',
            width: 800,
            items: [
                {
                    xtype: 'dynamicorgcombselector',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.generatingOrgDetail'),//预收部门
                    name: 'generatingOrgDetail',
                    allowBlank: false,
                    disabled: true,
                    value: stl.currentDept.name,
                    listWidth: 300,// 设置下拉框宽度
                    isPaging: true
                    // 分页
                },
                {
                    xtype: 'combobox',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.customerType'),//客户类型
                    name: 'transportType',
                    editable: false,
                    store: FossDataDictionary
                        .getDataDictionaryStore(
                        settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE,
                        null,
                        null,
                        [
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE]),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
                    listeners: {
                        'change': function (th, newValue, oldValue) {
                            // 获取表单等控件
                            var form, // 表单
                                customerDetail, agencyDetial;
                            // 获取表单
                            form = this.up('form').getForm();
                            // 获取下面组件
                            customerDetail = form.findField('customerDetail');
                            agencyDetialPA = form.findField('agencyDetialPA');
                            agencyDetialAA = form.findField('agencyDetialAA');
                            landStowage = form.findField('landStowage');
                            invoiceMark = form.findField('invoiceMark');

                            if (newValue != 'LC') {
                                var us = form.findField('unifiedSettlement');
                                var contract = form.findField('contractOrg');
                                var paymentTypeAdd = form.findField('paymentTypeAdd');
                                us.setValue(pay.depositReceived.i18n('foss.stl.pay.common.no'));
                                contract.setValue(null);

                                if (paymentTypeAdd.readOnly) {
                                    paymentTypeAdd.setReadOnly(false);
                                }
                            }

                            if (newValue == 'LC') {
                                customerDetail.show();
                                customerDetail.labelEl
                                    .update('<span style="color:red;">&nbsp;*</span>'
                                    + pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'));
                                agencyDetialPA.hide();
                                agencyDetialPA.setValue("");
                                agencyDetialPA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
                                agencyDetialAA.hide();
                                agencyDetialAA.setValue("");
                                agencyDetialAA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
                                landStowage.hide();
                                landStowage.setValue("");
                                landStowage.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.landStowage'));
                            } else if (newValue == 'PA') {
                                customerDetail.hide();
                                customerDetail.setValue("");
                                customerDetail.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'));
                                agencyDetialPA.show();
                                agencyDetialPA.labelEl
                                    .update('<span style="color:red;">&nbsp;*</span>'
                                    + pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
                                agencyDetialAA.hide();
                                agencyDetialAA.setValue("");
                                agencyDetialAA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
                                landStowage.hide();
                                landStowage.setValue("");
                                landStowage.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.landStowage'));
                                invoiceMark.setValue('INVOICE_TYPE_02');
                            } else if (newValue == 'AA') {
                                customerDetail.hide();
                                customerDetail.setValue("");
                                customerDetail.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'));
                                agencyDetialPA.hide();
                                agencyDetialPA.setValue("");
                                agencyDetialPA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
                                agencyDetialAA.show();
                                agencyDetialAA.labelEl
                                    .update('<span style="color:red;">&nbsp;*</span>'
                                    + pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
                                landStowage.hide();
                                landStowage.setValue("");
                                landStowage.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.landStowage'));
                                invoiceMark.setValue('INVOICE_TYPE_02');
                            } else if (newValue == 'LS') {
                                landStowage.show();
                                landStowage.labelEl
                                    .update('<span style="color:red">&nbsp;*</span>'
                                    + pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.landStowage'));
                                customerDetail.hide();
                                customerDetail.setValue("");
                                customerDetail.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'));
                                agencyDetialPA.hide();
                                agencyDetialPA.setValue("");
                                agencyDetialPA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'));
                                agencyDetialAA.hide();
                                agencyDetialAA.setValue("");
                                agencyDetialAA.labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'));
                                invoiceMark.setValue('INVOICE_TYPE_02');
                            }
                        }
                    }
                },
                {
                    xtype: 'combobox',
                    name: 'paymentTypeAdd',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.paymentType'),//收款方式
                    allowBlank: false,
                    editable: false,
                    store: FossDataDictionary
                        .getDataDictionaryStore(
                        settlementDict.SETTLEMENT__PAYMENT_TYPE,
                        null,
                        {
                            'valueCode': '',
                            'valueName': '请选择'
                        },
                        [
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CASH,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CARD,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__NOTE,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__ONLINE,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CASH,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CARD,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__NOTE,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__ONLINE
                            ]),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: '',
                    listeners: {
                        'change': function (th, newValue, oldValue) {
                            var form = this.up('form').getForm();
                            var remitNo = form.findField('remitNo');
                            var unionpayNo = form.findField('unionpayNo');
                            var amount = form.findField('amount');
                            // 当选择银行卡时，隐藏汇款编号控件

                            if (oldValue == 'CD') {
                                if (!unionpayNo.isDisabled()) {
                                    form.findField('unionpayNo').labelEl
                                        .update(pay.depositReceived
                                            .i18n('foss.stl.pay.billDepositReceived.unionpayNo')
                                        + ':');
                                    unionpayNo.setValue("");
                                    amount.setValue("");
                                    unionpayNo.setDisabled(true);
                                    unionpayNo.hide();
                                }
                            }
                            if (newValue == 'CD') {
                                if (unionpayNo.isDisabled()) {
                                    unionpayNo.setDisabled(false);
                                    form.findField('unionpayNo').labelEl
                                        .update('<span style="color:red;">&nbsp;*</span>'
                                        + pay.depositReceived
                                            .i18n('foss.stl.pay.billDepositReceived.unionpayNo')
                                        + ':');
                                    amount.setValue("");
                                    unionpayNo.show();
                                }
                            }
                            // 当选择电汇或支票时，隐藏汇款编号控件
                            if (oldValue == 'NT' || oldValue == 'TT' ) {
                                if (!remitNo.isDisabled()) {
                                	form.findField('remitNo').labelEl
                                    .update(pay.depositReceived
                                        .i18n('foss.stl.pay.billDepositReceived.remitNo')
                                    + ':');
                                    remitNo.setValue("");
                                    amount.setValue("");
                                    remitNo.setDisabled(true);
                                    remitNo.hide();
                                }
                            }
                            if (newValue == 'NT' || newValue == 'TT') {
                                if (remitNo.isDisabled()) {//remitNo.isDisabled()
                                    remitNo.setDisabled(false);
                                        form.findField('remitNo').labelEl
                                            .update('<span style="color:red;">&nbsp;*</span>'
                                            + pay.depositReceived
                                                .i18n('foss.stl.pay.billDepositReceived.remitNo')
                                            + ':');
                                    amount.setValue("");
                                    remitNo.show();
                                }
                            }

                            if (newValue == 'OL') {
                                form.findField('onlinePayCode').enable();
                                form.findField('onlinePayCode').show();
                                form.findField('amount').setReadOnly(true);
                            }
                            if (oldValue == 'OL') {
                                form.findField('onlinePayCode').disable();
                                form.findField('onlinePayCode').hide();
                                form.findField('onlinePayCode').setValue(null);
                                form.findField('amount').setReadOnly(false);
                            }
                        }
                    }
                },
                {
                    xtype: 'commoncustomerselector',
                    singlePeopleFlag: 'Y',
                    all: 'true',
                    fieldLabel: '<span style="color:red;">*</span>'
                    + pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'),//专线客户
                    name: 'customerDetail',
                    listWidth: 300,// 设置下拉框宽度
                    isPaging: true, // 分页
                    listeners: {
                        'select': function (me) {
                            pay.depositReceived.getCustInvoiceMark(me);
                        }
                    }
                },
                {
                    xtype: 'commonairagentselector',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialAA'),//空运代理
                    name: 'agencyDetialAA',
                    hidden: true,
                    listWidth: 300,// 设置下拉框宽度
                    isPaging: true
                    // 分页
                },
                {
                    xtype: 'commonvehagencycompselector',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.agencyDetialPA'),//偏线代理
                    name: 'agencyDetialPA',
                    isPaging: true,// 分页
                    hidden: true
                },
                {
                    xtype: 'commonLdpAgencyCompanySelector',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.landStowage'),//快递代理
                    name: 'landStowage',
                    hidden: true,
                    listWidth: 300,// 设置下拉框宽度
                    isPaging: true// 分页
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.remitNo'),//汇款编号
                    name: 'remitNo',
                    regex: /^((\s)*[^\u4e00-\u9fa5]+)(\s)*$/,
                    regexText: '输入非中文的汇款编号',
                    disabled: true,
                    hidden: true,
                    allowBlank: false,
                    listeners: {
                        'blur': function (th) {
                            var form = this.up('form').getForm();
                            // 获取下面组件
                            var remitNo = form.findField('remitNo');
                            var amount = form.findField('amount');
                            var paymentType = form.findField('paymentTypeAdd')
                                .getValue();
                            var generatingOrgCodeLast = form
                                .findField('generatingOrgDetail').lastValue;
                            var generatingOrgCode = "";
                            if (generatingOrgCodeLast == stl.currentDept.name) {
                                generatingOrgCode = stl.currentDept.code;
                            } else {
                                generatingOrgCode = generatingOrgCodeLast;
                            }
                            // 无部门，不查询汇款编号
                            if (generatingOrgCode == null || "" == Ext.String.trim(generatingOrgCode)) {
                                Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
                                    pay.depositReceived.i18n('foss.stl.pay.billDepositReceived.noDeptCode'));
                                th.setValue(null);
                                return;
                            }
                            if (th.getValue() != "" && th.getValue != null) {
                                var reg = /^[\u4E00-\u9FA5]+$/;
                                if (reg.test(th.getValue())) {
                                    Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
                                        pay.depositReceived.i18n('foss.stl.pay.billDepositReceived.noRemitNo'));
                                    remitNo.setValue("");
                                    return false;
                                }
                                var billDepositReceivedPayVo, billDepositReceivedPayDto;
                                // 当选择电汇和支票时，隐藏汇款编号控件
                                billDepositReceivedPayVo = new Object();
                                billDepositReceivedPayDto = new Object();
                                billDepositReceivedPayDto.remitNo = Ext.String.trim(th.getValue());
                                billDepositReceivedPayDto.paymentType = paymentType;
                                billDepositReceivedPayDto.generatingOrgCode = generatingOrgCode;
                                billDepositReceivedPayVo.billDepositReceivedPayDto = billDepositReceivedPayDto;
                                // 调用后台接口根据输入汇款编号获取汇款人、汇款日期，汇款可用金额、
                                Ext.Ajax.request({
                                    url: pay
                                        .realPath('queryPayRemittanceDetail.action'),
                                    jsonData: {
                                        'billDepositReceivedPayVo': billDepositReceivedPayVo
                                    },
                                    method: 'post',
                                    success: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        amount
                                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount);
                                        pay.depositReceived.noCancelAmount = result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount;
                                    },
                                    exception: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        Ext.Msg
                                            .alert(
                                            pay.depositReceived
                                                .i18n('foss.stl.pay.common.alert'),
                                            result.message);
                                        remitNo.setValue("");
                                    },
                                    failure: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        Ext.Msg
                                            .alert(
                                            pay.depositReceived
                                                .i18n('foss.stl.pay.common.alert'),
                                            result.message);
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.unionpayNo'),//银联交易流水号
                    name: 'unionpayNo',
                    regex: /^\d*$/,
                    regexText: '请输入银联交易流水号',
                    disabled: true,
                    hidden: true,
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.onlinePayCode'),//在线支付编号
                    name: 'onlinePayCode',
                    disabled: true,
                    hidden: true,
                    allowBlank: false,
                    listeners: {
                        'blur': function (th) {
                            var form = this.up('form').getForm();
                            // 获取下面组件
                            var remitNo = form.findField('onlinePayCode');
                            var amount = form.findField('amount');
                            var paymentType = form.findField('paymentTypeAdd')
                                .getValue();
                            var generatingOrgCodeLast = form
                                .findField('generatingOrgDetail').lastValue;
                            var generatingOrgCode = "";
                            if (generatingOrgCodeLast == stl.currentDept.name) {
                                generatingOrgCode = stl.currentDept.code;
                            } else {
                                generatingOrgCode = generatingOrgCodeLast;
                            }
                            // 无部门，不查询网上支付编号
                            if (generatingOrgCode == null || "" == Ext.String.trim(generatingOrgCode)) {
                                Ext.Msg.alert(pay.depositReceived.i18n('foss.stl.pay.common.alert'),
                                    pay.depositReceived.i18n('foss.stl.pay.billDepositReceived.noDeptCode'));
                                th.setValue(null);
                                return;
                            }
                            if (th.getValue() != "" && th.getValue != null) {
                                var billDepositReceivedPayVo, billDepositReceivedPayDto;
                                billDepositReceivedPayVo = new Object();
                                billDepositReceivedPayDto = new Object();
                                billDepositReceivedPayDto.remitNo = Ext.String.trim(th.getValue());
                                billDepositReceivedPayDto.paymentType = paymentType;
                                billDepositReceivedPayDto.generatingOrgCode = generatingOrgCode;
                                billDepositReceivedPayVo.billDepositReceivedPayDto = billDepositReceivedPayDto;
                                // 调用后台接口根据输入网上支付编号获取汇款人、汇款日期，汇款可用金额、
                                Ext.Ajax.request({
                                    url: pay
                                        .realPath('queryOnlinePayInfo.action'),
                                    jsonData: {
                                        'billDepositReceivedPayVo': billDepositReceivedPayVo
                                    },
                                    method: 'post',
                                    success: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        amount
                                            .setValue(result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount);
                                        pay.depositReceived.noCancelAmount = result.billDepositReceivedPayVo.billDepositReceivedPayDto.amount;
                                    },
                                    exception: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        Ext.Msg
                                            .alert(
                                            pay.depositReceived
                                                .i18n('foss.stl.pay.common.alert'),
                                            result.message);
                                        remitNo.setValue("");
                                    },
                                    failure: function (response) {
                                        var result = Ext
                                            .decode(response.responseText);
                                        Ext.Msg
                                            .alert(
                                            pay.depositReceived
                                                .i18n('foss.stl.pay.common.alert'),
                                            result.message);
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.amount'),//金额
                    allowBlank: false,
                    decimalPrecision: 2,
                    name: 'amount',
                    html: '&nbsp;'
                },
                {
                    xtype: 'combo',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.invoiceMark'),//发票标记
                    name: 'invoiceMark',
                    store: invoiceMarkStore,
                    queryMode: 'local',
                    displayField: 'text',
                    valueField: 'value',
                    fieldStyle: 'color:#555',
                    editable: false,
                    listeners: {
                        render: function (me) {
                            Ext.create('Ext.tip.ToolTip', {
                                target: me.id,
                                trackMouse: true,
                                html: pay.depositReceived
                                    .i18n('foss.stl.pay.billDepositReceived.invoiceMarkTip')//系统自动获取，不可编辑
                            });
                        }
                    },
                    readOnly: true,
                    value: ''
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.unifiedSettlement'),//是否统一结算
                    name: 'unifiedSettlement',
                    fieldStyle: 'color:#555',
                    editable: false,
                    readOnly: true,
                    value: ''
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.contractOrg'),//合同部门
                    name: 'contractOrg',
                    fieldStyle: 'color:#555',
                    editable: false,
                    readOnly: true,
                    value: ''
                },
                {
                    xtype: 'textarea',
                    autoScroll: true,
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.notes'),//备注
                    name: 'notes',
                    columnWidth: 1,
                    height: 48
                },
                {
                    xtype: 'component',
                    border: false,
                    columnWidth: 1,
                    height: 10
                },
                {
                    border: 1,
                    xtype: 'container',
                    columnWidth: 1,
                    defaultType: 'button',
                    layout: 'column',
                    items: [
                        {
                            text: pay.depositReceived
                                .i18n('foss.stl.pay.common.reset'),
                            columnWidth: .15,
                            handler: function () {
                                this.up('form').getForm().reset();
                                this.up('form').getForm()
                                    .findField('generatingOrgDetail')
                                    .setValue(null);
                            }
                        },
                        {
                            xtype: 'container',
                            border: false,
                            columnWidth: 0.5,
                            html: '&nbsp;'
                        },
                        {
                            text: pay.depositReceived
                                .i18n('foss.stl.pay.addBillDepositReceived.commitAccount'),//会计提交
                            disabled: !pay.depositReceived
                                .isPermission('/stl-web/pay/addBillDepositReceivedAccount.action'),
                            hidden: !pay.depositReceived
                                .isPermission('/stl-web/pay/addBillDepositReceivedAccount.action'),
                            cls: 'yellow_button',
                            columnWidth: 0.15,
                            handler: function () {
                                var form = this.up('form').getForm();
                                var me = this;
                                pay.depositReceived
                                    .addBillDepositReceivedAccount(
                                    form,
                                    pay.depositReceived.DEPOSIT_RECEIVED_ACCOUNTING,
                                    me);
                            }
                        },
                        {
                            xtype: 'container',
                            border: false,
                            columnWidth: 0.05,
                            html: '&nbsp;'
                        },
                        {
                            text: pay.depositReceived
                                .i18n('foss.stl.pay.addBillDepositReceived.commitCashier'),//收银员提交
                            disabled: !pay.depositReceived
                                .isPermission('/stl-web/pay/addBillDepositReceived.action'),
                            hidden: !pay.depositReceived
                                .isPermission('/stl-web/pay/addBillDepositReceived.action'),
                            cls: 'yellow_button',
                            columnWidth: .15,
                            handler: function () {
                                var form = this.up('form').getForm();
                                var me = this;
                                pay.depositReceived
                                    .addBillDepositReceived(
                                    form,
                                    pay.depositReceived.DEPOSIT_RECEIVED_CASHIER,
                                    me);
                            }
                        }
                    ]
                }
            ]
        }
    ]
});

/**
 * 声明预收单明细界面
 */
Ext.define('Foss.DepositReceived.DepositReceivedDetailWindow', {
    id: 'Deposit_Received_Detail_Window_Id',
    extend: 'Ext.window.Window',
    modal: true,
    title: pay.depositReceived
        .i18n('foss.stl.pay.billDepositReceived.depositReceivedDetailTitle'),//预收单查看页面
    height: 550,
    width: 600,
    items: [
        {
            xtype: 'form',
            defaults: {
                margin: '15 5 5 5',
                labelWidth: 70
            },
            layout: {
                type: 'column',
                columns: 4
            },
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'),//预收单号
                    name: 'depositReceivedNo',
                    readOnly: true,
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.generatingOrgDetail'),//预收部门
                    name: 'generatingOrgName',
                    readOnly: true,
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.customerDetail'),//专线客户
                    name: 'customerName',
                    readOnly: true,
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.customerCode'),//客户编码
                    name: 'customerCode',
                    readOnly: true,
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.customerType'),//客户类型
                    name: 'transportType',
                    readOnly: true,
                    store: FossDataDictionary
                        .getDataDictionaryStore(
                        settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE,
                        null,
                        null,
                        [
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE,
                            pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_PARTNER]),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: '',
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'combobox',
                    name: 'paymentType',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.paymentType'),//收款方式
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    readOnly: true,
                    store: FossDataDictionary
                        .getDataDictionaryStore(
                        settlementDict.SETTLEMENT__PAYMENT_TYPE,
                        null,
                        {
                            'valueCode': '',
                            'valueName': ''
                        },
                        [
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CASH,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CARD,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__NOTE,
                            pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__ONLINE]),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: '',
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.remitNo'),//汇款编号
                    readOnly: true,
                    name: 'remitNo',
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.amount'),//金额
                    name: 'amount',
                    html: '&nbsp;',
                    readOnly: true,
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.verifyAmount'),//已核销金额
                    name: 'verifyAmount',
                    html: '&nbsp;',
                    readOnly: true,
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.unverifyAmount'),//未核销金额
                    name: 'unverifyAmount',
                    html: '&nbsp;',
                    readOnly: true,
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.createUserName'),//制单人
                    name: 'createUserName',
                    readOnly: true,
                    columnWidth: .35
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.createOrgName'),//录入部门
                    name: 'createOrgName',
                    readOnly: true,
                    columnWidth: .4
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'datefield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.businessDate'),//业务日期
                    format: 'Y-m-d  H:i:s',
                    name: 'businessDate',
                    readOnly: true,
                    columnWidth: .8

                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .2,
                    html: '&nbsp;'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.active'),//是否有效
                    name: 'active',
                    readOnly: true,
                    columnWidth: .35,
                    store: FossDataDictionary.getDataDictionaryStore(
                        settlementDict.FOSS_ACTIVE, null, {
                            'valueCode': '',
                            'valueName': pay.depositReceived
                                .i18n('foss.stl.pay.common.all')//全部
                        }),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: ''
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .1,
                    html: '&nbsp;'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.isRedBack'),//是否红单
                    name: 'isRedBack',
                    readOnly: true,
                    columnWidth: .4,
                    store: FossDataDictionary.getDataDictionaryStore(
                        settlementDict.SETTLEMENT__IS_RED_BACK, null, {
                            'valueCode': '',
                            'valueName': pay.depositReceived
                                .i18n('foss.stl.pay.common.all')
                        }),
                    queryModel: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    value: ''
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.notes'),
                    name: 'notes',
                    readOnly: true,
                    columnWidth: .8
                },
                {
                    xtype: 'container',
                    border: false,
                    columnWidth: .15,
                    html: '&nbsp;'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.invoiceMark'),
                    name: 'invoiceMark',
                    readOnly: true,
                    columnWidth: .8
                },
                {
                    border: 1,
                    xtype: 'container',
                    columnWidth: 1,
                    defaultType: 'button',
                    layout: 'column',
                    items: [
                        {
                            xtype: 'container',
                            border: false,
                            columnWidth: .4,
                            html: '&nbsp;'
                        },
                        {
                            text: pay.depositReceived
                                .i18n('foss.stl.pay.billDepositReceived.depositReceivedDetailColse'),
                            columnWidth: .2,
                            handler: function () {
                                this.up('window').close();
                            }
                        }
                    ]
                }
            ]
        }
    ]
});

// 预收单列表
Ext.define('Foss.PayDepositReceived.PayDepositReceivedQueryInfoGrid', {
    extend: 'Ext.grid.Panel',
    title: pay.depositReceived
        .i18n('foss.stl.pay.billDepositReceived.depositReceived'),
    frame: true,
    height: 400,
    // plugins:SoperateColumnEditing,
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    store: Ext
        .create('Foss.PayDepositReceived.BillDepositReceivedEntityStore'),
    columns: [
        {
            xtype: 'actioncolumn',
            width: 73,
            text: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.actionColumn'),
            align: 'center',
            items: [
                {
                    iconCls: 'deppon_icons_print',//打印
                    tooltip: pay.depositReceived.i18n('foss.stl.pay.common.print'),
                    handler: function (grid, rowIndex, colIndex) {
                        pay.depositReceived.printBillDepositReceived(grid, rowIndex,
                            colIndex)
                    }
                },
                {
                    iconCls: 'deppon_icons_delete',//删除
                    tooltip: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.disable'),
                    getClass: function (v, m, r, rowIndex) {
                        if (!pay.depositReceived
                                .isPermission('/stl-web/pay/writeBackBillDepositReceived.action')) {
                            return 'statementBill_hide';
                        } else {
                            return 'deppon_icons_delete';
                        }
                    },
                    handler: function (grid, rowIndex, colIndex) {
                        pay.depositReceived.writeBackBillDepositReceived(grid,
                            rowIndex, colIndex)
                    }
                },
                {
                    iconCls: 'deppon_icons_showdetail',//显示细节
                    tooltip: pay.depositReceived
                        .i18n('foss.stl.pay.billDepositReceived.detail'),
                    handler: function (grid, rowIndex, colIndex) {
                        pay.depositReceived.detailDepositReceived(grid, rowIndex,
                            colIndex)
                    }
                }
            ]
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.depositReceivedNo'),
            dataIndex: 'depositReceivedNo'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.collectionOrgName'),
            dataIndex: 'collectionOrgName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.collectionOrgCode'),
            hidden: true,
            dataIndex: 'collectionOrgCode'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.createOrgName'),
            hidden: true,
            dataIndex: 'createOrgName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.generatingOrgName'),
            dataIndex: 'generatingOrgName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.generatingOrgCode'),
            hidden: true,
            dataIndex: 'generatingOrgCode'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.collectionCompanyName'),
            dataIndex: 'collectionCompanyName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.customerName'),
            dataIndex: 'customerName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.customerCode'),
            dataIndex: 'customerCode'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.customerType'),
            dataIndex: 'transportType',
            renderer: function (value) {
                var displayField = FossDataDictionary
                    .rendererSubmitToDisplay(
                    value,
                    settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE);
                return displayField;
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.paymentType'),
            dataIndex: 'paymentType',
            renderer: function (value) {
            	if(value == 'FCUS'){
    				var displayField = '到付转预收';
    				return displayField;
    			}else if (value == 'CF'){
    				var displayField = '委托派费转预收';
    				return displayField;
    			}else if (value == 'JTU'){
    				var displayField = '奖励应付转预收';
    				return displayField;
    			}else if (value == 'KTU'){
    				var displayField = '快递差错应付转预收';
    				return displayField;
    			}else{
    				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
    	    		return displayField;
    			}
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.amount'),
            dataIndex: 'amount',
            align: 'right',
            renderer: stl.amountFormat
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.verifyAmount'),
            dataIndex: 'verifyAmount',
            align: 'right',
            renderer: stl.amountFormat
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.unverifyAmount'),
            dataIndex: 'unverifyAmount',
            align: 'right',
            renderer: stl.amountFormat
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.paymentAmount'),
            dataIndex: 'paymentAmount',
            align: 'right',
            renderer: stl.amountFormat
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.remitNo'),
            dataIndex: 'remitNo'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.refundStatus'),
            dataIndex: 'refundStatus',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(
                    value, 'BILL_DEPOSIT_RECEIVED__REFUND_STATUS');
                return displayField;
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.createUserName'),
            dataIndex: 'createUserName'
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.status'),
            dataIndex: 'status',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(
                    value, settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
                return displayField;
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.active'),
            dataIndex: 'active',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(
                    value, settlementDict.FOSS_ACTIVE);
                return displayField;
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.isInit'),
            dataIndex: 'isInit',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(
                    value, 'FOSS_BOOLEAN');
                return displayField;
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.businessDate'),
            dataIndex: 'businessDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.accountDate'),
            dataIndex: 'accountDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.invoiceMark'),
            dataIndex: 'invoiceMark',
            renderer: function (value) {
                return value ? invoiceMarkStore.findRecord('value', value).data.text : '';
            },
            hidden: true
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.unifiedSettlement'),
            dataIndex: 'unifiedSettlement',
            renderer: function (value) {
                if (value == 'Y') {
                    return pay.depositReceived.i18n('foss.stl.pay.common.yes');
                } else if (value == null || value == '' || value == 'N') {
                    return pay.depositReceived.i18n('foss.stl.pay.common.no');
                } else {
                    return value;
                }
            },
            hidden: true
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.contractOrgCode'),
            dataIndex: 'contractOrgCode',
            hidden: true
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.contractOrgName'),
            dataIndex: 'contractOrgName',
            hidden: true
        },
        {
            header: pay.depositReceived
                .i18n('foss.stl.pay.billDepositReceived.notes'),
            dataIndex: 'notes',
            hidden: true
        }
    ],
    viewConfig: {
        enableTextSelection: true
        // 设置行可以选择，进而可以复制
    },
    initComponent: function () {
        var me = this;
        me.dockedItems = [
            {
                xtype: 'toolbar',
                dock: 'top',
                layout: 'column',
                items: [
                    {
                        xtype: 'button',
                        hidden:!pay.depositReceived
                            .isPermission('/stl-web/pay/depositReceivedAdd.action'),
                        text: pay.depositReceived
                            .i18n('foss.stl.pay.billDepositReceived.add'),//新增
                        handler: function () {
                            pay.depositReceived.addDepositReceived();
                        }
                    },
                    {
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .005
                    },
                    {
                        xtype: 'button',
                        text: pay.depositReceived.i18n('foss.stl.pay.common.export'),//导出
                        disabled: !pay.depositReceived
                        .isPermission('/stl-web/pay/depositReceivedListExport.action'),
                        hidden: !pay.depositReceived
                            .isPermission('/stl-web/pay/depositReceivedListExport.action'),
                        handler: pay.depositReceived.depositReceivedListExport
                    },
                    {
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .005
                    },
                    {
                        xtype: 'button',
                        text: pay.depositReceived
                            .i18n('foss.stl.pay.billDepositReceived.backDepositReceived'),//退预收
                            disabled: !pay.depositReceived
                            .isPermission('/stl-web/pay/backDepositReceived.action'),
                        hidden: !pay.depositReceived
                            .isPermission('/stl-web/pay/backDepositReceived.action'),
                        handler: pay.depositReceived.backDepositReceived
                    }
                ]
            },
            {
                xtype: 'toolbar',
                dock: 'bottom',
                layout: 'column',
                items: [
                    {
                        height: 5,
                        columnWidth: 1
                    },
                    {
                        xtype: 'displayfield',
                        allowBlank: true,
                        id: 'Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalRows_Id',
                        columnWidth: .05,
                        fieldLabel: pay.depositReceived
                            .i18n('foss.stl.pay.billDepositReceived.depositReceivedTotal'),//合计 条数
                        labelWidth: 80
                    },
                    {
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .02
                    },
                    {
                        xtype: 'displayfield',
                        allowBlank: true,
                        id: 'Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_DepositReceivedTotalAmount_Id',
                        columnWidth: .1,
                        fieldLabel: pay.depositReceived
                            .i18n('foss.stl.pay.billDepositReceived.depositReceivedSum'),//金额
                        labelWidth: 50
                    },
                    {
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .02
                    },
                    {
                        xtype: 'standardpaging',
                        store: me.store,
                        columnWidth: .8,
                        plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                            // 设置分页记录最大值，防止输入过大的数值
                            maximumSize: 200
                        })
                    },
                    {
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .1
                    }
                ]
            }
        ]
        me.callParent();
    }
});

// 初始化界面
Ext.onReady(function () {
    Ext.QuickTips.init();

    var payDepositReceivedQueryInfoTab = Ext
        .create('Foss.PayDepositReceived.PayDepositReceivedQueryInfoTab');

    var payDepositReceivedQueryInfoGrid = Ext.create(
        'Foss.PayDepositReceived.PayDepositReceivedQueryInfoGrid', {
            id: 'Foss_PayDepositReceived_PayDepositReceivedQueryInfoGrid_Id'
        });

    Ext.create('Ext.panel.Panel', {
        id: 'T_pay-manageBillDepositReceived_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        items: [payDepositReceivedQueryInfoTab,
            payDepositReceivedQueryInfoGrid],
        getQueryTab: function () {
            return payDepositReceivedQueryInfoTab;
        },
        renderTo: 'T_pay-manageBillDepositReceived-body',
        listeners: {
            'boxready': function (th) {
                var roles = stl.currentUserDepts;
                var queryTab = th.getQueryTab();
                var form = queryTab.down('form');
                form = form.getForm();
                var deptcombselector = form.findField('generatingOrgDetail');

                // 获取下面组件
                var customerDetail = form
                    .findField('pay.depositReceived.commoncustomerselector');
                var agencyDetialPA = form
                    .findField('pay.depositReceived.commonvehagencycompselector');
                var agencyDetialAA = form
                    .findField('pay.depositReceived.commonairagencycompanyselector');

                if (agencyDetialPA) {
                    agencyDetialPA.hide();
                }

                if (agencyDetialAA) {
                    agencyDetialAA.hide();
                }
                if (Ext.getCmp('Foss_DepositReceived_DepositReceivedAddWindow_ID')) {
                    Ext.getCmp('Foss_DepositReceived_DepositReceivedAddWindow_ID').close();
                }
            },
            destroy: function () {
                Ext.getCmp('Foss_DepositReceived_DepositReceivedAddWindow_ID').close();
            },
            hide: function () {
                Ext.getCmp('Foss_DepositReceived_DepositReceivedAddWindow_ID').close();
            }
        }
    });
});