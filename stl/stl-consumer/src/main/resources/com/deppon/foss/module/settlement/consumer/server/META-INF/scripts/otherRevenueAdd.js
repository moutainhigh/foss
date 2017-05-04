if ($ != undefined) {
    var jq = $.noConflict();
}
// 由于平台正则BUG，为了加载message
consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseInputNumber');

// 定义主营非主营常量
consumer.addOtherRevenue.PRIMARY = {
    CODE: "PRIMARY",
    NAME: '主营收入'
};
consumer.addOtherRevenue.SECONDARY = {
    CODE: "SECONDARY",
    NAME: '非主营收入'
};

/**
 * 问题处理 ISSUE-3584 小票收入类别与运单号关联 modifiy liqin 2013-08-01
 */
// 运单相关和非运单相关
consumer.addOtherRevenue.WAYBILL = {
    CODE: "WAYBILL",
    NAME: '运单相关'
};
consumer.addOtherRevenue.NOTWAYBILL = {
    CODE: "NOTWAYBILL",
    NAME: '非运单相关'
};

/**
 *
 * 小票的收款类别：
 *
 *    A 事故赔偿
 *    G 盘点长款金额
 *    D 加收送货费
 *    DU 送货上楼费
 *    E 超期预收款
 *    FC 富余仓库转租收入
 *    F 放空费
 *    H 收银员卡利息
 *    B 客户多夫运费
 *    O 其他/租房违约金
 *    P 包装费
 *    PD 自提改派送
 *    POS POS机手续费
 *    R 卖废品
 *    RB 还借支
 *    RFC 更改费
 *    W 仓储费/保管费
 *  WE  进仓费
 *  SB 网银盾返款
 *  DR 押金回收
 *  AD 加收派送费
 *  EC 外部赔款
 *  RF 备用金上缴
 *  FO 叉车费
 *  AC 异常代收货款
 *  HC 手续费
 *  WS 外发收入
 *  STORAGE 仓储费
 *  SU 系统使用费
 *  BU 品牌使用费
 *  EH 异常货处理
 *  ZF 咨询服务费--add by 269044-配合合伙人项目
 */
consumer.addOtherRevenue.dataValueCode = ['A', 'G', 'D', 'DU', 'E', 'FC', 'F', 'H', 'B', 'O', 'P', 'PD', 'POS',
    'R', 'RB', 'RFC', 'W', 'WE', 'SB', 'DR', 'AD', 'EC', 'RF', 'FO', 'AC','HC', 'WS', 'STORAGE','SU','BU','EH','ZF'];

/**
 * 获取收入类别Store
 *
 * modifiy by liqin 2013-07-17 ISSUE-3224 为保证小票实际业务和系统中一致，避免人为误操作生成错误的财务数据，
 * 现需要在小票提交环节增加"收款方式"和"收入类别"的对应校验。
 *
 * 现状： 始发月报表有两列："小票现金之变卖废品收入"和"小票银行之收银员卡利息"， 两列只允许出现凭证报表中规定的收款方式。现在小票生成的环节没有做控制，
 * 导致小票收入类别为变卖废品收入的存在除现金之外的其它收款方式、 收银员卡利息也出现了除银行卡之外的其它收款方式。
 *
 * 解决方案：现需要在小票提交环节增加"收款方式"和"收入类别"的对应校验，满足条件后，方可提交成功。
 * 1.当小票的收入类别为："卖废品"时，收款方式只能选择：现金 
 * 2.当小票的收入类别为："收银员卡利息"时，收款方式只能选择：银行卡
 * 3.当小票的收入类别不是数据字典中的主营收入类型，且不是系统使用费、品牌使用费时，收款方式不能选择：临时欠款或月结。 
 * 4、当小票的收入类别是系统使用费或品牌使用费时：收款方式默认为临时欠款；发票标记默认为：02—非运输专票；是否统一结算为：否；
 * extAttribute1: PRIMARY 主营业收入
 * extAttribute1: SECONDARY 非主营收入
 * 
 * 
 */
consumer.addOtherRevenue.getCollectionTypeStore = function () {
    // 从数据库获取Store
    store = FossConsumerDataDictionary.getDataDictionaryStore('BILL_RECEIVABLE__COLLECTION_TYPE', null, null);

    // 加载Store
    store.load();

    // 检查是否有超期预收款权限，如果没有，则不显示超期预收款选项
    permission = consumer.addOtherRevenue.isPermission('/stl-web/consumer/initAddOtherRevenueCollectionType_E.action')
    if (!permission) {
        index = store.find('valueCode', 'E')// 超期预收款
        if (index != -1) {
            store.removeAt(index)
        }
    }

    return store
}

/** ******************************************************************* */
/*******************************************************************************
 * 获取小票的收入类型业务字典提供方法
 */
// 业务字典信息类
Ext.define('FossConsumerDataDictionary', {
    singleton: true,
    /**
     * 通过词条代码获得业务字典数据
     *
     * @param termsCode
     *            词条代码
     * @param valueCodes
     *            条目编码数组
     * @returns 业务字典数据
     */
    getDataByTermsCode: function (termsCode, valueCodes) {
        if (login.dataDictionary != null && termsCode != null) {
            var data = Ext.clone(login.dataDictionary.get(termsCode));
            if (!Ext.isEmpty(valueCodes)) {
                var reslutArray = new Array();
                if (Ext.isArray(valueCodes)) {
                    for (var i = 0; i < data.length; i++) {
                        if (Ext.Array.contains(valueCodes, data[i].valueCode)) {
                            reslutArray.push(data[i]);
                        }
                    }
                } else {
                    for (var i = 0; i < data.length; i++) {
                        if (valueCodes == data[i].valueCode) {
                            reslutArray.push(data[i]);
                        }
                    }
                }
                // 此处当valueCodes为数组，但是内容无法识别(undefined)时，返回全部数据
                if (reslutArray != null && reslutArray.length > 0) {
                    return reslutArray;
                } else {
                    return data;
                }
            }
            return data;
        }
        return null;
    },

    /**
     * 根据数据字典名称获取对应的store,如果没有则返回[],不影响整个页面的渲染
     *
     * @param termsCode
     *            词条代码
     * @param id
     *            如果想要通过store id 查询store的话就传id,否则可以不用传
     * @param anyRecords
     *            如果想增加一些记录到这个数据字典中，可以通过这个参数传入， 些参数可以是一个数据数组，也可以是一个数据
     * @param valueCodes
     *            条目编码数组
     * @returns
     */
    getDataDictionaryStore: function (termsCode, id, anyRecords, valueCodes) {
        var data = FossDataDictionary.getDataByTermsCode(termsCode, consumer.addOtherRevenue.dataValueCode);
        if (!Ext.isEmpty(data)) {
            if (!Ext.isEmpty(anyRecords)) {
                if (Ext.isArray(anyRecords)) {
                    for (var i = 0; i < anyRecords.length; i++) {
                        data.unshift(anyRecords[i]);
                    }
                } else {
                    data.unshift(anyRecords);
                }
            }
            var json = {
                fields: ['valueCode', 'valueName', 'extAttribute1', 'extAttribute2'],
                data: data
            };
            if (!Ext.isEmpty(id)) {
                json["id"] = id;
            }
            return Ext.create('Ext.data.Store', json);
        } else {
            return [];
        }
    },
    /**
     * 将业务字典的displayValue（数据字典显示值）转换成描述submitValue（提交值）
     * 使用方法rendererDictionary(displayValue,’abc’);
     *
     * @param value
     *            所要转换的值
     *
     *
     * @param termsCode
     *            词条代码
     */
    rendererDisplayToSubmit: function (displayValue, termsCode) {
        var data = FossDataDictionary.getDataByTermsCode(termsCode);
        if (!Ext.isEmpty(displayValue) && !Ext.isEmpty(data)) {
            for (var i = 0; i < data.length; i++) {
                if (displayValue == data[i].valueName) {
                    return data[i].valueCode;
                }
            }
        }
        return displayValue;
    },
    /**
     * 将业务字典的submitValue（提交值）转换成描述displayValue（数据字典显示值）
     * 使用方法rendererDictionary(value,’abc’);
     *
     * @param value
     *            所要转换的值
     * @param termsCode
     *            词条代码
     */
    rendererSubmitToDisplay: function (submitValue, termsCode) {
        var data = FossDataDictionary.getDataByTermsCode(termsCode);
        if (!Ext.isEmpty(submitValue) && !Ext.isEmpty(data)) {
            for (var i = 0; i < data.length; i++) {
                if (submitValue == data[i].valueCode) {
                    return data[i].valueName;
                }
            }
        }
        return submitValue;
    }
});
//ddw
consumer.addOtherRevenue.unifiedSettlement = function (me) {
    var form = me.up('form').getForm();
    // 业务类型
    var businessCase = form.findField('vo.dto.businessCase').getValue();
    // 客户类型
    var customerType = form.findField('vo.dto.customerType').getValue();
    // 发票标记
    var invoiceMark = form.findField('vo.dto.invoiceMark').getValue();
    // 付款方式
    var paymentType = form.findField('vo.dto.paymentType').getValue();
    // 客户编码
    var customerCode = form.findField('consumer.addOtherRevenue.commoncustomerselector').getValue();
    // 收入类型
    var incomeCategories = form.findField('vo.dto.incomeCategories').getValue();
    // 收入类别：主营收入、非主营收入
    var extAttribute1 = form.findField('vo.dto.incomeCategories').lastSelection[0];
    // 是否统一结算
    var unifiedSettlement = consumer.addOtherRevenue.isUnifiedSettlement;
    // 合同部门编码
    var contractOrgCode = consumer.addOtherRevenue.contractOrgCode;
    // 合同部门名称
    var contractOrgName = consumer.addOtherRevenue.contractOrgName;
    // 非主营收入不进统一结算
    if (Ext.isEmpty(incomeCategories) || extAttribute1.data.extAttribute1 != 'PRIMARY') {
        form.findField('vo.dto.unifiedSettlement').setValue('N');
        form.findField('vo.dto.contractOrgCode').setValue(null);
        form.findField('vo.dto.contractOrgName').setValue(null);
        // 非专线客户不进统一结算
    } else if (Ext.isEmpty(customerType) || customerType != 'LC') {
        form.findField('vo.dto.unifiedSettlement').setValue('N');
        form.findField('vo.dto.contractOrgCode').setValue(null);
        form.findField('vo.dto.contractOrgName').setValue(null);
        // 发票标记为01不进统一结算
    } else if (Ext.isEmpty(invoiceMark) || invoiceMark == 'INVOICE_TYPE_01') {
        form.findField('vo.dto.unifiedSettlement').setValue('N');
        form.findField('vo.dto.contractOrgCode').setValue(null);
        form.findField('vo.dto.contractOrgName').setValue(null);
        // 付款方式为现金或银行卡不进统一结算
    } else if (Ext.isEmpty(paymentType) || paymentType == 'CH' || paymentType == 'CD') {
        form.findField('vo.dto.unifiedSettlement').setValue('N');
        form.findField('vo.dto.contractOrgCode').setValue(null);
        form.findField('vo.dto.contractOrgName').setValue(null);
        // 客户编码为空不进统一结算
    } else if (Ext.isEmpty(customerCode)) {
        form.findField('vo.dto.unifiedSettlement').setValue('N');
        form.findField('vo.dto.contractOrgCode').setValue(null);
        form.findField('vo.dto.contractOrgName').setValue(null);
        // 获取客户信息：是否统一结算、合同部门名称、合同部门编码
    } else {
        form.findField('vo.dto.unifiedSettlement').setValue(unifiedSettlement);
        form.findField('vo.dto.contractOrgCode').setValue(contractOrgCode);
        form.findField('vo.dto.contractOrgName').setValue(contractOrgName);
    }
}

// ddw
consumer.addOtherRevenue.invoice = function (me) {
    var form = me.up('form').getForm();
    // 客户编码
    var customerCode = form.findField('consumer.addOtherRevenue.commoncustomerselector').getValue();
    // 业务类型
    var businessCase = form.findField('vo.dto.businessCase').getValue();
    // 客户类型
    var customerType = form.findField('vo.dto.customerType').getValue();
    // 运单号
    var waybillNo = form.findField('vo.dto.waybillNo').getValue();
    // 收入类型
    var incomeCategories = form.findField('vo.dto.incomeCategories').getValue();
    // 收入类别
    var extAttribute1 = form.findField('vo.dto.incomeCategories').lastSelection[0];
    // 判断收入类型是否为空
    if (Ext.isEmpty(incomeCategories)) {
        form.findField('vo.dto.invoiceMark').setValue('');
        // 收入类别为系统使用费、品牌使用费时，发票标记默认为：02—非运输专票，以及咨询服务费时，也是02
    } else if (incomeCategories == 'SU' || incomeCategories == 'BU' || incomeCategories == 'ZF') {
        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
        // 非主营收入：事故赔款、收银员卡利息、客户多付运费、盘点长款金额、卖废品、其他、网银盾返款、押金回收、外部赔款、备用金上缴、叉车费、异常代收货款
    } else if (extAttribute1.data.extAttribute1 != 'PRIMARY') {
        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
        // 先判断为快递，则默认为02，如果为零担或者为空，则进行下面逻辑
    } else if (!Ext.isEmpty(businessCase) && businessCase == 'EP') {
        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
        // 如果为偏线或者空运，则默认为02
    } else if (customerType == 'PA' || customerType == 'AA') {
        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
        // 判断运单号和客户是否同时为空
    } else if (Ext.isEmpty(waybillNo) && Ext.isEmpty(customerCode)) {
        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
    } else {
        // 如果为客户，则需要根据运单号判断
        if (Ext.isEmpty(waybillNo)) {
            var custInvoiceMarkEntity = new Object();
            custInvoiceMarkEntity.custNumber = customerCode;
            Ext.Ajax.request({
                url: ContextPath.STL_WEB + '/consumer/queryCustInvoiceMark.action',
                jsonData: {
                    'custInvoiceMarkEntity': custInvoiceMarkEntity
                },
                method: 'post',
                async: false,
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    var invoiceMark = result.custInvoiceMarkEntity.invoiceMark;
                    consumer.addOtherRevenue.isUnifiedSettlement = result.custInvoiceMarkEntity.unifiedSettlement;
                    consumer.addOtherRevenue.contractOrgName = result.custInvoiceMarkEntity.contractOrgName;
                    consumer.addOtherRevenue.contractOrgCode = result.custInvoiceMarkEntity.contractOrgCode;
					consumer.addOtherRevenue.dunningOrgName = result.custInvoiceMarkEntity.dunningOrgName;
					consumer.addOtherRevenue.dunningOrgCode = result.custInvoiceMarkEntity.dunningOrgCode;
                    if (!Ext.isEmpty(invoiceMark)) {
                        form.findField('vo.dto.invoiceMark').setValue(invoiceMark);
                    } else {
                        if (!Ext.isEmpty(form.findField('vo.dto.customerName').getValue())) {
                            form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
                        } else {
                            form.findField('vo.dto.invoiceMark').setValue('');
                        }
                    }
                }
            });
        } else {
            Ext.Ajax.request({
                url: consumer.realPath('queryWaybill.action'),
                params: {
                    'vo.dto.waybillNo': waybillNo
                },
                method: 'post',
                async: false,
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    var productCode = result.vo.dto.productCode;
                    var invoiceMark = result.vo.dto.invoiceMark;
                    if (!Ext.isEmpty(productCode)) {
                        if (productCode == 'PACKAGE') {
                            form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
                        } else {
                            form.findField('vo.dto.invoiceMark').setValue(invoiceMark);
                        }
                    } else {
                        form.findField('vo.dto.invoiceMark').setValue('INVOICE_TYPE_02');
                    }
                }
            });
            if (!Ext.isEmpty(customerCode)) {
                var custInvoiceMarkEntity = new Object();
                custInvoiceMarkEntity.custNumber = customerCode;
                Ext.Ajax.request({
                    url: ContextPath.STL_WEB + '/consumer/queryCustInvoiceMark.action',
                    jsonData: {
                        'custInvoiceMarkEntity': custInvoiceMarkEntity
                    },
                    method: 'post',
                    async: false,
                    success: function (response) {
                        var result = Ext.decode(response.responseText);
                        consumer.addOtherRevenue.isUnifiedSettlement = result.custInvoiceMarkEntity.unifiedSettlement;
                        consumer.addOtherRevenue.contractOrgName = result.custInvoiceMarkEntity.contractOrgName;
                        consumer.addOtherRevenue.contractOrgCode = result.custInvoiceMarkEntity.contractOrgCode;
                        consumer.addOtherRevenue.dunningOrgName = result.custInvoiceMarkEntity.dunningOrgName;
                        consumer.addOtherRevenue.dunningOrgCode = result.custInvoiceMarkEntity.dunningOrgCode;
                    }
                });
            }
        }
    }
    consumer.addOtherRevenue.unifiedSettlement(me);
}

/**
 * 客户类型选择组件监听
 */
consumer.addOtherRevenue.customerTypeChangeListener = function (value, form) {
    var customerTypes = {
        'LC': 'consumer.addOtherRevenue.commoncustomerselector',
        'PA': 'consumer.addOtherRevenue.commonvehagencycompselector',
        'AA': 'consumer.addOtherRevenue.commonairagencycompanyselector',
        'LS': 'consumer.addOtherRevenue.landStowage'
    }

    var typeCmpName = customerTypes[value];
    var waybillNo = form.findField('vo.dto.waybillNo').getValue();
    var businessCase = form.findField('vo.dto.businessCase');
    for (item in customerTypes) {
        var cmp = form.findField(customerTypes[item]);
        if (item == value) {
            cmp.show();
            // 如果为快递代理，则业务类型默认为包裹，且不可编辑
            if (value == 'LS') {
                businessCase.disable();
                if (Ext.isEmpty(waybillNo) || Ext.isEmpty(Ext.String.trim(waybillNo))) {
                    businessCase.setValue('EP');
                }
            } else if (Ext.isEmpty(waybillNo) || Ext.isEmpty(Ext.String.trim(waybillNo))) {
                if (value == 'PA' || value == 'AA') {
                    businessCase.disable();
                    businessCase.setValue('BB');
                } else {
                    businessCase.enable();
                    businessCase.setValue('');
                }
            }
        } else {
            cmp.reset();
            cmp.hide();
        }
    }
}

/**
 * 客户编码值改变时监听
 */
consumer.addOtherRevenue.customerChangeListener = function (me, tEvent, eOpts) {
    var customerCode = me.value;
    var form = me.up('form').getForm();
    // 如果为快递代理，则直接从选择器中拿用户手机号和名称
    var customerType = form.findField('vo.dto.customerType').getValue();
    if (customerType == 'LS' && !Ext.isEmpty(customerCode)) {
        var cstNameObj = form.findField('vo.dto.customerName');
        var cstPhoneObj = form.findField('vo.dto.customerPhone');
        var custName = me.getRawValue();
        var custPhone = me.lastSelection[0].get('contactPhone');
        var mobilePhone = me.lastSelection[0].get('mobilePhone');
        cstNameObj.setValue(custName);
        if (!Ext.isEmpty(mobilePhone)) {
            cstPhoneObj.setValue(mobilePhone);
        } else {
            cstPhoneObj.setValue(custPhone);
        }

        // 如果不为空，则默认不可编辑
        if (!Ext.isEmpty(custName)) {
            cstNameObj.setReadOnly(true);
            cstPhoneObj.setReadOnly(true);
        }
        return false;
    }
    consumer.addOtherRevenue.queryCustomerInfoByCode(me, customerCode, form);
}

/**
 * 根据客户编码查询客户信息
 */
consumer.addOtherRevenue.queryCustomerInfoByCode = function (me, customerCode, form) {

    var cstNameObj = form.findField('vo.dto.customerName');
    var cstPhoneObj = form.findField('vo.dto.customerPhone');
    var newValue = form.findField('vo.dto.paymentType').getValue();
    var customerType = form.findField('vo.dto.customerType').getValue();

    if (Ext.isEmpty(customerCode)) {
        consumer.addOtherRevenue.clearCustNameAndPhone(cstNameObj, cstPhoneObj);
        return;
    }

    Ext.Ajax.request({
        url: consumer.realPath('queryCustomerInfo.action'),
        params: {
            'vo.custDto.code': customerCode,
            'vo.custDto.type': customerType
        },
        method: 'post',
        success: function (response) {
            var result = Ext.decode(response.responseText);
            var customerDto = result.vo.custDto;
            if (customerDto) {
                var custName = customerDto.name;
                var custPhone = customerDto.phone;
                cstNameObj.setValue(custName);
                cstPhoneObj.setValue(custPhone);

                if (!Ext.isEmpty(custName)) {
                    cstNameObj.setReadOnly(true);
                    cstPhoneObj.setReadOnly(true);
                } else {
                    consumer.addOtherRevenue.clearCustNameAndPhone(cstNameObj, cstPhoneObj);
                    // 客户不存在,请先到CRM中录入客户信息，再进行操作
                    Ext.MessageBox
                        .alert(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
                        consumer.addOtherRevenue
                            .i18n('foss.stl.consumer.otherRevenue.custNotExist.pleaseInputFirst'));
                }
            } else {
                consumer.addOtherRevenue.clearCustNameAndPhone(cstNameObj, cstPhoneObj);
            }
            consumer.addOtherRevenue.invoice(me);
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            consumer.addOtherRevenue.clearCustNameAndPhone(cstNameObj, cstPhoneObj);
            consumer.addOtherRevenue.invoice(me);
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), result.message, 'error',
                2000);
        }
    });
}

/**
 * 清空客户名称和电话
 */
consumer.addOtherRevenue.clearCustNameAndPhone = function (custNameObj, custPhoneObj) {
    custNameObj.setReadOnly(false);
    custPhoneObj.setReadOnly(false);
    custNameObj.reset();
    custPhoneObj.reset();
}

consumer.addOtherRevenue.addRevenue = function (form) {
    // 获取运单号、客户类型、业务类型
    var waybillNo = form.findField('vo.dto.waybillNo').getValue();
    var businessCase = form.findField('vo.dto.businessCase').getValue();
    // 如果运单号为空，且业务类型为空，则跑出异常
    if ((Ext.isEmpty(waybillNo) || Ext.isEmpty(Ext.String.trim(waybillNo))) && Ext.isEmpty(businessCase)) {
        Ext.Msg.alert(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
            .i18n('foss.stl.consumer.common.businessCaseIsNullWarning'));
        return false;
    }
    var params = form.getValues();
    Ext.apply(params, {
        'vo.dto.customerCode': consumer.addOtherRevenue.getCustomerCode(form),
				'vo.dto.businessCase' : businessCase,
				'vo.dto.dunningOrgCode':consumer.addOtherRevenue.dunningOrgCode,
				'vo.dto.dunningOrgName':consumer.addOtherRevenue.dunningOrgName
    })

    // 后台默认传递属性到struts报错，所以手动删除该对象
    if (!Ext.isEmpty(params["consumer.addOtherRevenue.commonairagencycompanyselector"])) {
        delete params["consumer.addOtherRevenue.commonairagencycompanyselector"];
    }

    if (!Ext.isEmpty(params["consumer.addOtherRevenue.commoncustomerselector"])) {
        delete params["consumer.addOtherRevenue.commoncustomerselector"];
    }
    if (!Ext.isEmpty(params["consumer.addOtherRevenue.commonvehagencycompselector"])) {
        delete params["consumer.addOtherRevenue.commonvehagencycompselector"];
    }

    Ext.Ajax.request({
        url: consumer.realPath('addOtherRevenue.action'),
        params: params,
        method: 'post',
        success: function (response) {
            var result = Ext.decode(response.responseText);

            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), result.message, 'ok’',
                2000);
            Ext.MessageBox.buttonText.yes = consumer.addOtherRevenue.i18n('foss.stl.consumer.common.OK');
            Ext.MessageBox.buttonText.no = consumer.addOtherRevenue.i18n('foss.stl.consumer.common.cancel');

            // 是否打印?
            Ext.Msg.confirm(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                .i18n('foss.stl.consumer.otherRevenue.isPrint'), function (btn, text) {
                if (btn == 'yes') {
                    var id = result.vo.dto.id;
                    consumer.addOtherRevenue.printOtherRevenueNo(form, id);
                }
                form.reset();
                var businessCase = form.findField('vo.dto.businessCase');
                businessCase.enable();
            });

        },

        exception: function (response) {
            var result = Ext.decode(response.responseText);
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), result.message, 'error',
                5000);
        }
    });
}

/**
 * 获取客户编码
 */
consumer.addOtherRevenue.getCustomerCode = function (form) {
    var customerTypes = {
        'LC': 'consumer.addOtherRevenue.commoncustomerselector',
        'PA': 'consumer.addOtherRevenue.commonvehagencycompselector',
        'AA': 'consumer.addOtherRevenue.commonairagencycompanyselector',
        'LS': 'consumer.addOtherRevenue.landStowage'
    }

    for (item in customerTypes) {
        var cmp = form.findField(customerTypes[item]);
        if (cmp) {
            if (!Ext.isEmpty(cmp.getValue())) {
                return cmp.getValue();
            }
        }
    }

    return null;
}

/**
 * 提交Form表单
 */
consumer.addOtherRevenue.submitForm = function () {
    var form = this.up('form').getForm();

    var me = this;
    // 设置该按钮灰掉
    me.disable(false);

    setTimeout(function () {
        me.enable(true);
    }, 10000);

    if (form.isValid()) {
        var cstName = form.findField('vo.dto.customerName').getValue();
        var paymentType = form.findField('vo.dto.paymentType').getValue();
        if (!cstName) {
            // 请录入客户名称
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                .i18n('foss.stl.consumer.otherRevenue.pleaseInputCustName'), 'error', 2000);
            return;
        }

        // 临欠或月结
        if (paymentType == 'CT' || paymentType == 'DT') {
            var customerCode = consumer.addOtherRevenue.getCustomerCode(form);
            if (Ext.isEmpty(customerCode)) {

                // 请录入客户名称
                Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                    .i18n('foss.stl.consumer.otherRevenue.custNotExist.pleaseInputFirst'), 'error', 2000);
                return;
            }
        }

        // 银行卡
        var batchNo = form.findField('vo.dto.batchNo').getValue().trim();
        if (paymentType == 'CD') {
            if (/^[0-9]*$/.test(batchNo) == false) {
                Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                    .i18n('foss.stl.consumer.otherRevenue.batchNoError'), 'error', 2000)
            }
        }
        // 获取收款类别 提交时校验规则
        var incomeCategories = form.findField('vo.dto.incomeCategories').getValue();
        var extAttribute = form.findField('vo.dto.incomeCategories').lastSelection[0].data.extAttribute1;

        if (incomeCategories == 'H' && paymentType != 'CH') {
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                .i18n('foss.stl.consumer.otherRevenue.incomeCategoriesHtoCD'), 'error', 2000);
            return false;
        } else if (incomeCategories == 'R' && paymentType != 'CH') {
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                .i18n('foss.stl.consumer.otherRevenue.incomeCategoriesRtoCH'), 'error', 2000)
            return false;
            //当小票的收入类别不是数据字典中的主营收入类型，且不是系统使用费、品牌使用费，咨询服务费时，收款方式不能选择：临时欠款或月结
        } else if (extAttribute == 'SECONDARY' && (incomeCategories != 'BU' && incomeCategories != 'SU'&& incomeCategories != 'ZF') 
		        	&& (paymentType == 'DT' || paymentType == 'CT')) {
            Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
                .i18n('foss.stl.consumer.otherRevenue.incomeCategoriesNotPrimary'), 'error', 2000)
            return false;
        }
        var params = form.getValues();
        // 后台默认传递属性到struts报错，所以手动删除该对象
        if (!Ext.isEmpty(params["consumer.addOtherRevenue.commonairagencycompanyselector"])) {
            delete params["consumer.addOtherRevenue.commonairagencycompanyselector"];
        }

        if (!Ext.isEmpty(params["consumer.addOtherRevenue.commoncustomerselector"])) {
            delete params["consumer.addOtherRevenue.commoncustomerselector"];
        }
        if (!Ext.isEmpty(params["consumer.addOtherRevenue.commonvehagencycompselector"])) {
            delete params["consumer.addOtherRevenue.commonvehagencycompselector"];
        }

        // 判断小票号是否连续
        Ext.Ajax.request({
            url: consumer.realPath('isConsecutive.action'),
            params: params,
            method: 'post',
            success: function (response) {

                // 如果非连续，结出提示
                var result = Ext.decode(response.responseText);
                var showTips = false;
                if (!result.consecutive) {
                    showTips = true;
                }

                // 判断是否保存
                if (showTips) {

                    // 小票号不是连续的，是否继续?
                    Ext.Msg.confirm(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'),
                        consumer.addOtherRevenue
                            .i18n('foss.stl.consumer.otherRevenue.otherRevenueNoIsNotConsecutive'), function (btn,
                                                                                                              text) {
                            if (btn == 'yes') {
                                consumer.addOtherRevenue.addRevenue(form);
                                return;
                            }
                        });
                } else {
                    // 可以保存，则提交数据库
                    consumer.addOtherRevenue.addRevenue(form);
                }
                me.enable(true);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.ux.Toast.msg(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), result.message,
                    'error', 2000);
                me.enable(true);
            }
        });

    } else {

        // 请检查输入条件是否合法
        Ext.Msg.alert(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
            .i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
        me.enable(true);
    }
}

/**
 * 打印明细
 */
consumer.addOtherRevenue.printOtherRevenueNo = function (form, id) {
    // 获取Form信息
    if (!form) {

        // 系统异常
        Ext.Msg.alert(consumer.addOtherRevenue.i18n('foss.stl.consumer.common.warmTips'), consumer.addOtherRevenue
            .i18n('foss.stl.consumer.otherRevenue.systemException'));
    }
    // 获取打印参数信息
    var formParams = {
        'id': id
    };
    do_printpreview('otherrenvenueno', formParams, ContextPath.STL_WEB);
}

// 业务类型
Ext.define('Foss.otherRevenue.BusinessCase', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'BB',
            valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.breakbulk')
        },// 零担
            {
                valueCode: 'EP',
                valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.express')
            }// 快递
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
// ddw
// 发票标记
Ext.define('Foss.otherRevenue.Invoice', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'INVOICE_TYPE_01',
            valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.InvoiceMarkOne')
        },// 01—运输专票11%
            {
                valueCode: 'INVOICE_TYPE_02',
                valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.InvoiceMarkTwo')
            }// 02—非运输专票
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

Ext.define('Foss.otherRevenue.UnifiedSettlement', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'Y',
            valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.common.yes')
        },
            {
                valueCode: 'N',
                valueName: consumer.addOtherRevenue.i18n('foss.stl.consumer.common.no')
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

// 客户类型
Ext.define('Foss.otherRevenue.customerTypeStore', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'code',
        type: 'string'
    }, {
        name: 'name',
        type: 'string'
    }],
    data: {
        'items': [{
            code: 'LC',
            name: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.W')
        }, {
            code: 'PA',
            name: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.PD')
        }, {
            code: 'AA',
            name: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType.AD')
        }/*
         * ,
         * {code:'LS',name:consumer.addOtherRevenue.i18n('foss.stl.consumer.common.landStowage')}
         */
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

// 新增小票 FORM
Ext.define('Foss.otherRevenue.OtherRevenueAddForm', {
    extend: 'Ext.form.Panel',
    //title: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.newOtherRevenue'),// 新增小票
    frame: false,
    layout: 'column',
    width: 900,
    height: 430,
    //bodyCls: 'autoHeight',
    defaultType: 'textfield',
    margin: 20,
    defaults: {
        margin: '10 10 10 10',
        labelWidth: 100,
        columnWidth: .33
    },
    items: [{
        name: 'vo.dto.otherRevenueNo',
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.otherRevenueNo'), // 小票单号
        allowBlank: false,
        regex: /^[0-9]{8}$/,
        regexText: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseInputNumber')
        // 请输入8位数字
    }, {
        xtype: 'combo',
        name: "vo.dto.incomeCategories",
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.incomeCategoriesType'),// 收入类别
        allowBlank: false,
        editable: false,
        value: '',
        valueField: 'valueCode',
        displayField: 'valueName',
        queryMode: 'local',
        store: consumer.addOtherRevenue.getCollectionTypeStore(),
        listeners: {
            'change': function (me, newValue, oldValue, eOpts) {
                var data;
                var incomeCategories = me.value;
                var form = me.up('form').getForm();
                var paymentType = form.findField('vo.dto.paymentType').getValue();

                var waybillNo_id = Ext.getCmp('consumer.addOtherRevenue.waybillNo_id');
                
                // 获取业务类型
                var businessCase = form.findField('vo.dto.businessCase');
                
                // 收入类别为 “其它” 时必填备注
                var notes = form.findField('vo.dto.notes');
                if (Ext.isEmpty(me.lastSelection[0])) {
                    form.findField('vo.dto.paymentType').setValue(null);
                } else {
                    var selectExtAttribute = me.lastSelection[0].data.extAttribute1;
                    var selectExtAttribute2 = me.lastSelection[0].data.extAttribute2;
                    form.findField('vo.dto.paymentType').setValue(null);
                    form.findField('vo.dto.paymentType').setRawValue(null);
                    
                    Ext.apply(businessCase, {
                        allowBlank: true
                    });
                    
                    // 与运单关联时，运单必填
                    if (selectExtAttribute2 == 'WAYBILL') {
                        Ext.apply(waybillNo_id, {
                            allowBlank: false
                        });
                        waybillNo_id.doComponentLayout();
                    } else {
                        Ext.apply(waybillNo_id, {
                            allowBlank: true
                        });
                        waybillNo_id.doComponentLayout();
                    }
                    /**
                     * @author 218392 zhangyongxue 小票需求
                     * @date 2015-09-30 14:45:02 
                     * 这6中类型的备注为必填
                     * O  租房违约金   SECONDARY 
                     * RB 还借支       SECONDARY NOTWAYBILL
					 * AC 异常代收货款 SECONDARY NOTWAYBILL
					 * A  事故赔款     SECONDARY NOTWAYBILL
					 * EC 外部赔款     SECONDARY NOTWAYBILL
					 * DR 押金回收     SECONDARY NOTWAYBILL
                     */
                    if ((incomeCategories == 'O' || incomeCategories == 'RB' || incomeCategories == 'AC' 
                    	|| incomeCategories == 'A' || incomeCategories == 'EC' || incomeCategories == 'DR') 
                    	&& selectExtAttribute == 'SECONDARY') {
                        Ext.apply(notes, {
                            allowBlank: false
                        });
                        /**
                         * @author 218392 zhangyongxue 小票需求:2、	6种必填备注中，设置提醒内容，
                         * 单击提醒内容可编辑，类型与备注提醒内容一对一对应
                         */
                        if(incomeCategories == 'RB'){
                        	notes.emptyText = '请备注借支工作流号;';
                        }else if(incomeCategories == 'AC'){
                        	notes.emptyText = '请备注汇款编号+代收货款差错编号;';
                        }else if(incomeCategories == 'A'){
                        	notes.emptyText = '请备注报销工作流号+差错编号;';
                        }else if(incomeCategories == 'EC'){
                        	notes.emptyText = '请备注报销工作流号+差错编号;';
                        }else if(incomeCategories == 'DR'){
                        	notes.emptyText = '请备注押金部门+客户+押金类型;';
                        }else if(incomeCategories == 'O'){
                        	notes.emptyText = '请备注收入来源+款项用途;';
                        }
                        notes.enable();
                    	notes.reset();
                        notes.doComponentLayout();
                    }else if(incomeCategories == 'FC' && selectExtAttribute == 'SECONDARY'){ //@author 218392 zhangyongxue富余仓库转租收入,这个类型备注选填，意思可以为空
                        Ext.apply(notes, {
                            allowBlank: true
                        });
                        notes.emptyText = ' ';
                        notes.enable();
                        notes.reset();
                        notes.doComponentLayout();
                    }else { //@author 218392 zhangyongxue 其他16种小票类型取消备注
                        Ext.apply(notes, {
                            allowBlank: true
                        });
                        notes.emptyText = ' ';
                        notes.disable();
                        notes.reset();
                        notes.doComponentLayout();
                    }
                    // 收银员卡利息、网银盾返款、收废品
                    if ((incomeCategories == 'H' || incomeCategories == 'SB' || incomeCategories == 'R') && selectExtAttribute == 'SECONDARY') {
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                        data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['CH']);
                        consumer.addOtherRevenue.paymentType = data;
                        // 富余仓库转租收入、还借支、叉车费、银票手续费
                    } else if (incomeCategories == 'FC' || incomeCategories == 'RB' || incomeCategories == 'FO' || incomeCategories == 'HC') {
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                        data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['CD', 'CH']);
                        consumer.addOtherRevenue.paymentType = data;
                    //No:347069 异常货物
                    }else if(incomeCategories == 'EH' && selectExtAttribute == 'SECONDARY'){
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                        //设置业务类型不为空
                        Ext.apply(businessCase, {
                            allowBlank: false
                        });
                        //收款方式只能是现金或者银行卡
                        data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['CD', 'CH']);
                        consumer.addOtherRevenue.paymentType = data;
                        //@author 272005 huanglewei 系统使用费，品牌使用费
                        //modify by 269044-zhurongrong 咨询服务费
                    } else if(incomeCategories == 'SU' || incomeCategories == 'BU' || incomeCategories == 'ZF' ){
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                    	//收款方式只能是临时欠款
                    	data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['DT']);
                        consumer.addOtherRevenue.paymentType = data;
                        // 其他非主营收入
                    }else if (!(incomeCategories == 'R' && incomeCategories == 'H' && incomeCategories == 'SB') && selectExtAttribute == 'SECONDARY') {
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                        data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['CD', 'CH']);
                        consumer.addOtherRevenue.paymentType = data;
                    } else {// 主营收入
                        var store = form.findField('vo.dto.paymentType').store;
                        store.removeAll();
                        data = FossDataDictionary.getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE', ['CH', 'CD', 'CT', 'DT']);
                        consumer.addOtherRevenue.paymentType = data;
                    }
                }
                form.isValid();
                consumer.addOtherRevenue.invoice(me);

                if (!!form.findField('vo.dto.businessCase') && form.findField('vo.dto.businessCase').lastValue == 'EP') {
                    Ext.apply(waybillNo_id, {
                        allowBlank: false
                    });
                    waybillNo_id.doComponentLayout();
                }
            }
        }
    }, {
        name: 'vo.dto.waybillNo',
        id: 'consumer.addOtherRevenue.waybillNo_id',
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.waybillNo'),// 运单单号
        regex: /^[0-9]{8,14}$/,//329757-放开运单号14位校验
        regexText: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.pleaseInputNumber'),// 请输入8-14位数字
        listeners: {
            'blur': function (th) {
                // 获取运单号
                var value = th.getValue();
                var form = th.up('form').getForm();
                // 获取业务类型
                var businessCase = form.findField('vo.dto.businessCase');
                var custType = form.findField('vo.dto.customerType');
                // 如果运单号不为空,则业务类型为灰色不可编辑
                if (!Ext.isEmpty(value) && !Ext.isEmpty(Ext.String.trim(value))) {
                    businessCase.setValue(null);
                    businessCase.disable();
                } else if (custType.getValue() != 'LS') {
                    if (custType.getValue() == 'PA' || custType.getValue() == 'AA') {
                        businessCase.setValue('BB');
                        businessCase.disable();
                    } else {
                        businessCase.enable();
                    }
                } else {
                    businessCase.setValue('EP');
                }
                // ddw
                consumer.addOtherRevenue.invoice(th);
            }
        }
    }, FossDataDictionary.getDataDictionaryCombo('OTHER_REVENUE__PAYMENT_TYPE', {
        name: "vo.dto.paymentType",
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.paymentType'),// 收款方式
        value: '',
        allowBlank: false,
        editable: false,
        queryMode: 'local',
        value: 'CD',
        listeners: {
            'expand': function (field) {
                this.setValue(null);
                if (Ext.isEmpty(consumer.addOtherRevenue.paymentType)) {
                    consumer.addOtherRevenue.paymentType = FossDataDictionary
                        .getDataByTermsCode('OTHER_REVENUE__PAYMENT_TYPE');
                }

                this.store.removeAll();
                this.store.loadData(consumer.addOtherRevenue.paymentType);
            },
            'change': function (me, newValue, oldValue, eOpts) {

                if (newValue == oldValue) {
                    return false;
                }

                // ddw
                consumer.addOtherRevenue.invoice(me);

            },
            select: function (me, record, eOpts) {
                var form = me.up('form');
                var batch = form.getComponent('batchContainer');
                var batchEl = jq('#' + batch.el.id);
                var b = form.getForm().findField('vo.dto.batchNo');
                if (me.value == 'CD') {
                    if (batchEl.hidden == undefined) {
                        batchEl.show('slow', function () {
                            b.reset();
                        });
                    }
                } else {
                    if (!batchEl.hidden) {
                        batchEl.hide('slow', function () {
                            b.reset();
                        });
                    }
                }
            }
        }
    }), {
        xtype: 'numberfield',
        name: 'vo.dto.amount',
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.receiveAmount'), // 收款金额
        allowBlank: false,
        decimalPrecision: 2,
        minValue: 0,
        maxValue: 99999999
    }, {
        xtype: 'combo',
        name: 'vo.dto.businessCase',
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.BusinessCase'),// 业务类型
        editable: false,
        valueField: 'valueCode',
        displayField: 'valueName',
        queryMode: 'local',
        store: Ext.create('Foss.otherRevenue.BusinessCase'),
        listeners: {
            'change': function (me, newValue, oldValue, eOpts) {
                consumer.addOtherRevenue.invoice(me);
                var form = me.up('form').getForm();
                var selectExtAttribute2 = '';
                var va = '';
                if (form.findField('vo.dto.incomeCategories').lastSelection[0]) {
                    selectExtAttribute2 = form.findField('vo.dto.incomeCategories').lastSelection[0].data.extAttribute2;
                }
                if (me.lastSelection[0]) {
                    va = me.lastSelection[0].data.valueCode;
                }
                //表单
                var formtype = me.up('form').form._fields.items[1].lastValue;
                var waybillNo_id = Ext.getCmp('consumer.addOtherRevenue.waybillNo_id');
                //347069
                if (selectExtAttribute2 == 'WAYBILL' || va == 'EP' && formtype != 'EH') {
                    Ext.apply(waybillNo_id, {
                        allowBlank: false
                    });
                    waybillNo_id.doComponentLayout();
                } else {
                    Ext.apply(waybillNo_id, {
                        allowBlank: true
                    });
                    waybillNo_id.doComponentLayout();
                }
            }
        }

    }, {
        xtype: 'container',
        itemId: 'batchContainer',
        columnWidth: 1,
        layout: 'column',
        margin: 0,
        defaults: {
            labelWidth: 100,
            margin: '10 10 10 10',
            columnWidth: 0.33
        },
        defaultType: 'textfield',
        items: [{
            itemId: 'batchNoId',
            name: 'vo.dto.batchNo',
            fieldLabel: '银联交易流水号',
           regex: /^[0-9]*$/,//^\d{11}$
            regexText: '请输入格式为数字的银联交易流水号'
        }, {
            xtype: 'component',
            height: 24
        }, {
            xtype: 'component',
            height: 24
        }]
    }, {
        xtype: 'combo',
        name: 'vo.dto.customerType',
        fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerType'), // 客户类型
        editable: false,
        allowBlank: false,
        displayField: 'name',
        valueField: 'code',
        queryMode: 'local',
        triggerAction: 'all',
        store: Ext.create('Foss.otherRevenue.customerTypeStore'),
        value: 'LC',
        listeners: {
            'change': function (me, newValue, oldValue, eOpts) {
                var form = me.up('form').getForm();
                consumer.addOtherRevenue.customerTypeChangeListener(newValue, form);
                // ddw
                consumer.addOtherRevenue.invoice(me);
            }
        }
    }, /*
     * { name:'vo.dto.customerCode',
     * fieldLabel:consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerCode'),
     * //客户编码 allowBlank:false, listeners:{ blur:function(_this, the,
     * eOpts){ var customerCode=_this.value;
     * if(Ext.isEmpty(customerCode)){ return; } var form
     * =this.up('form').getForm();
     * consumer.addOtherRevenue.queryCustomerInfoByCode(customerCode,form); } } },
     */
        {
            xtype: 'commoncustomerselector',
            listWidth: 300,
            // allowBlank:false,
            fieldLabel: '客户信息',
            emptyText: '客户编码、名称均可',
            name: 'consumer.addOtherRevenue.commoncustomerselector',
            isPaging: true, // 分页
            all: 'true',
            singlePeopleFlag: 'Y',
            listeners: {
                blur: consumer.addOtherRevenue.customerChangeListener
            }
        }, {
            xtype: 'commonairagentselector',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.airAgency'),
            name: 'consumer.addOtherRevenue.commonairagencycompanyselector',
            isPaging: true, // 分页
            listeners: {
                blur: consumer.addOtherRevenue.customerChangeListener
            }
        }, {
            xtype: 'commonvehagencycompselector',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.partLineAgency'),
            name: 'consumer.addOtherRevenue.commonvehagencycompselector',
            isPaging: true, // 分页
            listeners: {
                blur: consumer.addOtherRevenue.customerChangeListener
            }
        }, {
            xtype: 'commonLdpAgencyCompanySelector',
            listWidth: 300,
            hidden: true,
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.common.landStowage'),
            name: 'consumer.addOtherRevenue.landStowage',
            isPaging: true, // 分页
            listeners: {
                blur: consumer.addOtherRevenue.customerChangeListener
            }
        }, {
            name: 'vo.dto.customerName',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerName'),// 客户名称
            allowBlank: false,
            cls: 'readonlyhaveborder'
        }, {
            name: 'vo.dto.customerPhone',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.customerPhone'), // 客户电话
            cls: 'readonlyhaveborder'
        }, {// ddw
            xtype: 'combo',
            name: 'vo.dto.invoiceMark',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.Invoice'),// 发票标记
            editable: false,
            // disabled:true,
            readOnly: true,

            valueField: 'valueCode',
            displayField: 'valueName',
            queryMode: 'local',
            store: Ext.create('Foss.otherRevenue.Invoice')
        }, {// ddw
            xtype: 'combo',
            name: 'vo.dto.unifiedSettlement',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.UnifiedSettlement'),// 是否统一结算
            editable: false,
            readOnly: true,
            valueField: 'valueCode',
            displayField: 'valueName',
            queryMode: 'local',
            store: Ext.create('Foss.otherRevenue.UnifiedSettlement')
        }, {// ddw
            name: 'vo.dto.contractOrgCode',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.ContractOrgCode'),// 合同部门编码
            editable: false,
            readOnly: true
        }, {// ddw
            name: 'vo.dto.contractOrgName',
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.ContractOrgName'),// 合同部门名称
            editable: false,
            readOnly: true
        }, {
            xtype: 'textarea',
            autoScroll: true,
            fieldLabel: consumer.addOtherRevenue.i18n('foss.stl.consumer.otherRevenue.note'),// 备注
            name: 'vo.dto.notes',
            maxLength: 50,//@author 218392 张永雪 原来是500，小票发票需求限制最多50个字
            height: 80,
            columnWidth: .99
        }, {
            border: 1,
            xtype: 'container',
            columnWidth: .99,
            defaultType: 'button',
            layout: 'column',
            items: [{
                text: consumer.addOtherRevenue.i18n('foss.stl.consumer.common.reset'),
                columnWidth: .12,
                handler: function () {
                    var form = this.up('form').getForm();
                    form.reset();
                    form.findField('vo.dto.paymentType').fireEvent('select', form.findField('vo.dto.paymentType'));
                    var cstNameObj = form.findField('vo.dto.customerName');
                    var cstPhoneObj = form.findField('vo.dto.customerPhone');
                    cstNameObj.setValue('');
                    cstPhoneObj.setValue('');
                    cstNameObj.setReadOnly(false);
                    cstPhoneObj.setReadOnly(false);
                }
            }, {
                xtype: 'container',
                border: false,
                html: '&nbsp;',
                columnWidth: .76
            }, {
                text: consumer.addOtherRevenue.i18n('foss.stl.consumer.common.commit'),
                cls: 'yellow_button',
                columnWidth: .12,
                handler: consumer.addOtherRevenue.submitForm,
                disabled: !consumer.addOtherRevenue.isPermission('/stl-web/consumer/addOtherRevenue.action'),
                hidden: !consumer.addOtherRevenue.isPermission('/stl-web/consumer/addOtherRevenue.action')
            }]
        }
    ]

})
;

Ext.onReady(function () {
    Ext.QuickTips.init();

    if (Ext.getCmp('T_consumer-initAddOtherRevenue_content')) {
        return;
    }
    var addForm = Ext.create('Foss.otherRevenue.OtherRevenueAddForm');// 查询FORM
    Ext.create('Ext.panel.Panel', {
        id: 'T_consumer-initAddOtherRevenue_content',
        cls: "panelContentNToolbar", // 必须添加,内容定位用。
        bodyCls: 'panelContentNToolbar-body', // 必须添加,内容定位用。
        layout: 'auto',
        // 获得查询FORM
        getAddForm: function () {
            return addForm;
        },
        items: [addForm],
        renderTo: 'T_consumer-initAddOtherRevenue-body',
        listeners: {
            'boxready': function (me) {
                form = me.getAddForm().getForm();
                form.findField('consumer.addOtherRevenue.commonvehagencycompselector').hide();
                form.findField('consumer.addOtherRevenue.commonairagencycompanyselector').hide();
            }
        }
    });
});