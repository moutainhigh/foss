<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/ext" prefix="ext" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../common/stlUtil.jsp" %>
<%--此处不可以采用直接include方式，会导致jsp编译java文件超过65535--%>
<jsp:include page="../common/settlementConstants.jsp">
    <jsp:param name="constants" value="_constants"/>
</jsp:include>
<%@ include file="../common/productType.jsp" %>
<%--此处不可以采用直接include方式，会导致jsp编译java文件超过65535--%>
<jsp:include page="../pay/addPayment.jsp">
    <jsp:param name="payParam" value="_programForm"/>
</jsp:include>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<ext:module subModule="statementCommon"/>
<ext:permission
        urls="/stl-web/writeoff/confirmStatement.action,/stl-web/writeoff/repayment.action,/stl-web/writeoff/batchWriteoffStatement.action,/stl-web/writeoff/deleteStatementEntry.action,/stl-web/writeoff/addStatementDetail.action,/stl-web/writeoff/addStatementConfReceipt.action,/stl-web/writeoff/exportStatementDetail.action,/stl-web/pay/payFromStatement.action"/>
<script type="text/javascript">
    //付款方式
    writeoff.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
    writeoff.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
    writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
    writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
    writeoff.SETTLEMENT__PAYMENT_TYPE__ONLINE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
    //获取结算是否有效字段
    writeoff.YES = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';
    //确认、反确认
    writeoff.STATEMENTCONFIRMSTATUS_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM"/>';
    writeoff.STATEMENTCONFIRMSTATUS_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM"/>';
    //对账单类型
    writeoff.STATEMENTTYPE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE__CUSTOMER_ACCOUNT"/>';//客户
    writeoff.STATEMENTTYPE_AGENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE__AGENT_ACCOUNT"/>';//偏线代理
    writeoff.STATEMENTTYPE_AIRAGENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE__AIR_FREIGHT_ACCOUNT"/>';//空运代理
    writeoff.STATEMENTTYPE_AIR = 'AFA';//航空公司 --此处只给前台用，不需要从后台获取
     //付款对账单子类型
    writeoff.STATEMENTTYPE_PARTNER__DELIVERY_FEE_PAYABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE"/>';//到达提成
    writeoff.STATEMENTTYPE_PARTNER__DELEGATION_DELIVERY_FEE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE"/>';//委托派费
    //结账状态
    writeoff.SETTLESTATUS_Y = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';//客户
    writeoff.SETTLESTATUS_N = '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';//客户
    //单据类型
    writeoff.STATEMENTDETAIL_RECEIVABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE"/>';
    writeoff.STATEMENTDETAIL_PAYABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE"/>';
    writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED"/>';
    writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT"/>';
    writeoff.STATEMENTDETAIL_PARTNER__DELIVERY_FEE_PAYABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_D__PARENT_TYPE__PARTNER__DELIVERY_FEE_PAYABLE"/>';
    writeoff.STATEMENTDETAIL_PARTNER__DELEGATION_DELIVERY_FEE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_D__PARENT_TYPE__PARTNER__DELEGATION_DELIVERY_FEE"/>';
    //tab 页查询常量设置
    writeoff.STATEMENTQUERYTAB_BYCUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
    writeoff.STATEMENTQUERYTAB_BYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询
    writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DZ_BILL_NO"/>';//按对账单号查询
    writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_WAYBILL_NO"/>';//按运单号
    writeoff.STATEMENTQUERYTABTAB_BYAIRNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_AIR_WAYBILL_NO"/>';//按航空正单号
    writeoff.STATEMENTQUERYTAB_FAILINGINVOICE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_FAILING_INVOICE"/>';//未开发票
    //是否要发邮件
    writeoff.SENDEMAIL_Y = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';
    writeoff.SENDEMAIL_N = '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';
    //发票申请状态
    writeoff.STATEMENTINVOICESTATUS_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT_INVOICE_STATUS_APPLIED"/>';
    writeoff.STATEMENTINVOICESTATUS_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT_INVOICE_STATUS_NO_APPLY"/>';
    writeoff.SETTLEMENTINVOICEAPPLYINVOICE_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT_INVOICE_APPLY_INVOICE_YES"/>';
    writeoff.SETTLEMENTINVOICEAPPLYINVOICE_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT_INVOICE_APPLY_INVOICE_NO"/>';
    //是否统一结算
    writeoff.RECEIVABLEUNIFORM_Y = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';
    writeoff.RECEIVABLEUNIFORM_N = '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';
</script>
<script type="text/javascript">
    //自定义对账单操作类型 --仅前台使用
    writeoff.STATEMENT_OPERATE_ADD = "add";//增加明细
    writeoff.STATEMENT_OPERATE_DELETE = "delete";//删除明细
    //自定义对账单操当前操作界面从哪里跳转过来的 --仅前台使用
    writeoff.PAGEFROM_STATEMENTENTRY = 'statementEntry';//从对账单明细界面来
    writeoff.PAGEFROM_STATEMENTEDIT = 'statementEdit';//从查询对账单界面来
    writeoff.PAGEFROM_STATEMENTADD = 'statementAdd';//从新增对账单明细界面来 
    /**
     * 重置
     */
    writeoff.statementReset = function () {
        this.up('form').getForm().reset();
    }

    /**
     * 格式化分录的单据子类型
     */
    writeoff.statementFormatBillType = function (value, record) {
        var displayField = value;
        //如果为应收单，则读取应收单的单据子类型
        if (record.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
            displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
        } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE) {
            displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__BILL_TYPE);
        } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
            displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
        } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT) {
            displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_ADVANCED_PAYMENT__BILL_TYPE);
        } else {
            displayField = value;
        }
        return displayField;
    }
    /**
     * 计算本期剩余金额
     */
    writeoff.statementCalulate = function (currentBillsForm) {
        var periodUnverifyRecAmountNew = 0,// 本期剩余应收金额
                periodUnverifyPayAmountNew = 0,// 本期剩余应收金额
                periodUnverifyPreAmountNew = 0,// 本期剩余预收金额
                periodUnverifyAdvAmountNew = 0,
                periodUnverifyRecAmount,// 本期剩余应收金额
                periodUnverifyPayAmount,// 本期剩余应收金额
                periodUnverifyPreAmount,// 本期剩余预收金额
                periodUnverifyAdvAmount, periodAmount, periodRecAmount, periodPayAmount, periodPreAmount, periodAdvAmount;// 本期剩余预付金额

        periodAmount = currentBillsForm.getForm().findField('periodAmount').getValue();
        periodRecAmount = currentBillsForm.getForm().findField('periodRecAmount').getValue();
        periodPayAmount = currentBillsForm.getForm().findField('periodPayAmount').getValue();
        periodPreAmount = currentBillsForm.getForm().findField('periodPreAmount').getValue();
        periodAdvAmount = currentBillsForm.getForm().findField('periodAdvAmount').getValue();
        periodUnverifyRecAmount = currentBillsForm.getForm().findField('periodUnverifyRecAmount').getValue();
        periodUnverifyPayAmount = currentBillsForm.getForm().findField('periodUnverifyPayAmount').getValue();
        periodUnverifyPreAmount = currentBillsForm.getForm().findField('periodUnverifyPreAmount').getValue();
        periodUnverifyAdvAmount = currentBillsForm.getForm().findField('periodUnverifyAdvAmount').getValue();
        if (periodAmount > 0) {
            if (periodAmount > periodAdvAmount) {
                periodUnverifyRecAmountNew = periodAmount - periodAdvAmount;
                periodUnverifyAdvAmountNew = periodAmount - periodUnverifyRecAmountNew;
            } else {
                periodUnverifyAdvAmountNew = periodAmount;
            }
        } else if (periodAmount < 0) {
            if (-periodAmount > periodPayAmount) {
                periodUnverifyPayAmountNew = periodPayAmount;
                periodUnverifyPreAmountNew = -periodAmount - periodPayAmount;
            } else {
                periodUnverifyPayAmountNew = -periodAmount;
            }
        }
        currentBillsForm.getForm().findField('periodUnverifyRecAmount').setValue(periodUnverifyRecAmountNew);
        currentBillsForm.getForm().findField('periodUnverifyPayAmount').setValue(periodUnverifyPayAmountNew);
        currentBillsForm.getForm().findField('periodUnverifyPreAmount').setValue(periodUnverifyPreAmountNew);
        currentBillsForm.getForm().findField('periodUnverifyAdvAmount').setValue(periodUnverifyAdvAmountNew);
        //返回本期剩余未还金额
        return periodUnverifyRecAmountNew;
    }

    /**
     * 更新代收货款上移下移、标记、反标记影响本期和往期金额
     */
    writeoff.statementOperate = function (baseInfoForm, beginBillsForm, currentBillsForm, selection, type) {
        var beginPeriodAmount = beginBillsForm.getForm().findField('beginPeriodAmount');
        var beginPeriodRecAmount = beginBillsForm.getForm().findField('beginPeriodRecAmount');
        var beginPeriodPayAmount = beginBillsForm.getForm().findField('beginPeriodPayAmount');
        var beginPeriodPreAmount = beginBillsForm.getForm().findField('beginPeriodPreAmount');
        var beginPeriodAdvAmount = beginBillsForm.getForm().findField('beginPeriodAdvAmount');

        var periodAmount = currentBillsForm.getForm().findField('periodAmount');
        var periodRecAmount = currentBillsForm.getForm().findField('periodRecAmount');
        var periodPayAmount = currentBillsForm.getForm().findField('periodPayAmount');
        var periodPreAmount = currentBillsForm.getForm().findField('periodPreAmount');
        var periodAdvAmount = currentBillsForm.getForm().findField('periodAdvAmount');

        if (type == writeoff.STATEMENT_OPERATE_ADD) {
            if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() - selection.get('unverifyAmount'));
                beginPeriodRecAmount.setValue(beginPeriodRecAmount.getValue() - selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() + selection.get('unverifyAmount'));
                periodRecAmount.setValue(periodRecAmount.getValue() + selection.get('unverifyAmount'));
            } else if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() + selection.get('unverifyAmount'));
                beginPeriodPayAmount.setValue(beginPeriodPayAmount.getValue() - selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() - selection.get('unverifyAmount'));
                periodPayAmount.setValue(periodPayAmount.getValue() + selection.get('unverifyAmount'));
            } else if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() + selection.get('unverifyAmount'));
                beginPeriodPreAmount.setValue(beginPeriodPreAmount.getValue() - selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() - selection.get('unverifyAmount'));
                periodPreAmount.setValue(periodPreAmount.getValue() + selection.get('unverifyAmount'));
            } else {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() - selection.get('unverifyAmount'));
                beginPeriodAdvAmount.setValue(beginPeriodAdvAmount.getValue() - selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() + selection.get('unverifyAmount'));
                periodAdvAmount.setValue(periodAdvAmount.getValue() + selection.get('unverifyAmount'));
            }
        } else if (type == writeoff.STATEMENT_OPERATE_DELETE) {
            if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() + selection.get('unverifyAmount'));
                beginPeriodRecAmount.setValue(beginPeriodRecAmount.getValue() + selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() - selection.get('unverifyAmount'));
                periodRecAmount.setValue(periodRecAmount.getValue() - selection.get('unverifyAmount'));
            } else if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() - selection.get('unverifyAmount'));
                beginPeriodPayAmount.setValue(beginPeriodPayAmount.getValue() + selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() + selection.get('unverifyAmount'));
                periodPayAmount.setValue(periodPayAmount.getValue() - selection.get('unverifyAmount'));
            } else if (selection.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() - selection.get('unverifyAmount'));
                beginPeriodPreAmount.setValue(beginPeriodPreAmount.getValue() + selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() + selection.get('unverifyAmount'));
                periodPreAmount.setValue(periodPreAmount.getValue() - selection.get('unverifyAmount'));

            } else {
                beginPeriodAmount.setValue(beginPeriodAmount.getValue() + selection.get('unverifyAmount'));
                beginPeriodAdvAmount.setValue(beginPeriodAdvAmount.getValue() + selection.get('unverifyAmount'));
                periodAmount.setValue(periodAmount.getValue() - selection.get('unverifyAmount'));
                periodAdvAmount.setValue(periodAdvAmount.getValue() - selection.get('unverifyAmount'));
            }
        }
        //计算本期剩余金额
        writeoff.statementCalulate(currentBillsForm);
        //计算本期剩余金额 ,获取本期剩余未还金额
        var periodUnverifyRecAmountNew = writeoff.statementCalulate(currentBillsForm);
        //如果本期金额大于0，则本期剩余未还金额等于本期应收金额，如果应收金额为0，则为0
        //反之如果本期发生金额金额小于0，则本期剩余未还金额等于本期发生金额
        if (periodAmount.getValue() > 0) {
            baseInfoForm.getForm().findField('unpaidAmount').setValue(periodUnverifyRecAmountNew);
        } else {
            //将本期剩余未还金额设置为本期发生金额
            baseInfoForm.getForm().findField('unpaidAmount').setValue(periodAmount.getValue());
        }

        var baseFormRecord = baseInfoForm.getForm().getRecord();
        var beginBillsFormRecord = beginBillsForm.getForm().getRecord();
        var currentBillsFormRecord = currentBillsForm.getForm().getRecord();
        //更新base的数据源
        baseInfoForm.getForm().updateRecord(baseFormRecord);
        //下面一句让表格里面的数据也更新了：
        beginBillsForm.getForm().updateRecord(beginBillsFormRecord);
        //下面一句让表格里面的数据也更新了：
        currentBillsForm.getForm().updateRecord(currentBillsFormRecord);

        writeoff.statementUnVerifyAmountShow(currentBillsFormRecord, currentBillsForm, writeoff.PAGEFROM_STATEMENTADD);
    }

    /**
     *根据当前操作类型获取对账单实体
     *@param selectionDatas --操作行数
     */
    writeoff.statementEntity = function (selectionDatas) {
        //声明变量
        var baseInfoAdd,
                beginBillsFormAdd,
                currentBillsFormAdd,
                statementEntryAddGrid,
                statementOfAccountMakeQueryResultVo,//对账单vo
                statementOfAccountMakeQueryResultDto,//对账单vo中的dto
                statementOfAccount//对账单vo中dto的对账单实体
                ;
        baseInfoAdd = writeoff.baseInfo;
        beginBillsFormAdd = writeoff.beginBillsForm;
        currentBillsFormAdd = writeoff.currentBillsForm;
        statementEntryAddGrid = writeoff.statementEntryEditGrid;

        //获取对账单表单数据
        statementOfAccount = Ext.merge(baseInfoAdd.getRecord().data, beginBillsFormAdd.getRecord().data, currentBillsFormAdd.getRecord().data);

        //将前台属性注入到dto中
        statementOfAccountMakeQueryResultDto = new Object();

        //封装当前操作行对象
        if (!Ext.isEmpty(selectionDatas)) {
            var jsonDataCurrent = new Array();
            for (var i = 0; i < selectionDatas.length; i++) {
                var record = selectionDatas[i];
                //拼接本期账单数据
                var currentRecord = new Object();
                currentRecord.sourceBillNo = record.get('sourceBillNo');//来源单号
                currentRecord.billParentType = record.get('billParentType');//单据父类型
                currentRecord.unverifyAmount = record.get('unverifyAmount');//未核销金额
                currentRecord.auditStatus = record.get('auditStatus');//审核状态
                currentRecord.versionNo = record.get('versionNo');//版本号
                jsonDataCurrent.push(currentRecord);
            }
            ;
            statementOfAccountMakeQueryResultDto.statementOfAccountDetailPeriodList = jsonDataCurrent;
            //封装对账单头、期初、本期
            statementOfAccountMakeQueryResultDto.statementOfAccount = statementOfAccount;
        }//封装当前明细本期明细	
        statementOfAccountMakeQueryResultDto.statementOfAccountList = [statementOfAccount];
        //将dto注入到后台中
        statementOfAccountMakeQueryResultVo = new Object();
        statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
        //返回VO
        return statementOfAccountMakeQueryResultVo;
    }

    writeoff.orgCodeInspect = function () {
        writeoff.statementOrgCode = writeoff.baseInfo.getForm().findField('createOrgCode').getValue();
        writeoff.currentOrgCode = FossUserContext.getCurrentDeptCode();
        if (!Ext.isEmpty(writeoff.statementOrgCode) && !Ext.isEmpty(writeoff.currentOrgCode)) {
            if (writeoff.statementOrgCode != writeoff.currentOrgCode) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.orgCodeDifferent"/>');
                return false;
            }
        } else {
            if (Ext.isEmpty(writeoff.statementorgCode)) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.statementOrgCode"/>');
                return false;
            } else if (Ext.isEmpty(writeoff.currentOrgCode)) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.currentOrgCode"/>');
                return false;
            } else {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.orgCode"/>');
                return false;
            }
        }
        return true;
    }

    /**
     * 确认对账单是否要发送邮件
     */
    writeoff.statementConfirm_SendEmail = function (sendFlag) {
        var statementOfAccountMakeQueryResultVo,
                baseInfo;
        baseInfo = writeoff.baseInfo;
        number = baseInfo.getForm().findField('statementBillNo').getValue();

        if (number == null || number == '') {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementNumberIsNullWarning"/>');
            return false;
        }
        if (baseInfo.getForm().findField('confirmStatus').getValue() == writeoff.STATEMENTCONFIRMSTATUS_Y) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.errorStatusToConfirmWarning"/>');
            return false;
        }
        //获取传入后台参数，当没有操作行时，传入null
        statementOfAccountMakeQueryResultVo = writeoff.statementEntity(null);
        statementOfAccountMakeQueryResultVo.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
        statementOfAccountMakeQueryResultVo.applyInvoice = writeoff.applyInvoice;
        //增加发送邮件标志
        statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.sendMailFlag = sendFlag;

        //打开注释
        Ext.Ajax.request({
            url: writeoff.realPath('confirmStatement.action'),
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                //因为此处没有返回值，故而需要手动去添加版本号 和 确认状态  和确认时间
                baseInfo.getForm().findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_Y);
                var versionNo = baseInfo.getForm().findField('versionNo');
                var confirmTime = baseInfo.getForm().findField('confirmTime');
                versionNo.setValue(versionNo.getValue() + 1);
                confirmTime.setValue(new Date());

                var baseFormRecord = baseInfo.getForm().getRecord();
                //下面一句让表格里面的数据也更新了：
                baseInfo.getForm().updateRecord(baseFormRecord);
                //隐藏确认按钮，显示反确认按钮
                writeoff.operateButtonPanel.items.items[1].hide();
                writeoff.operateButtonPanelDown.items.items[1].hide();
                writeoff.operateButtonPanel.items.items[2].show();
                writeoff.operateButtonPanelDown.items.items[2].show();
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.confirmSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    writeoff.ApplyInvoiceYES = function () {
        writeoff.applyInvoice = 'Y';
        writeoff.statementConfirm_SendEmail(writeoff.sendFlag);
        writeoff.ApplyInvoiceWindow.close();
    };

    writeoff.ApplyInvoiceNO = function () {
        writeoff.applyInvoice = 'N';
        writeoff.statementConfirm_SendEmail(writeoff.sendFlag);
        writeoff.ApplyInvoiceWindow.close();
    };

    Ext.define('Foss.statementbill.ApplyInvoiceForm', {
        extend: 'Ext.form.Panel',
        layout: 'column',
        width: stl.SCREENWIDTH * 0.42,
        defaultType: 'textfield',
        layout: 'column',
        defaults: {
            margin: '10 0 0 0',
            readOnly: true
        },
        items: [{
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .1
        }, {
            xtype: 'displayfield',
            value: '是否需要申请发票',
            columnWidth: .3
        }, {
            border: 1,
            xtype: 'container',
            columnWidth: .99,
            defaultType: 'button',
            layout: 'column',
            items: [{
                xtype: 'container',
                border: false,
                html: '&nbsp;',
                columnWidth: .05
            }, {
                text: '<ext:i18nForJsp key="foss.stl.writeoff.common.yes"/>',
                handler: writeoff.ApplyInvoiceYES,
                columnWidth: .15
            }, {
                xtype: 'container',
                border: false,
                html: '&nbsp;',
                columnWidth: .02
            }, {
                text: '<ext:i18nForJsp key="foss.stl.writeoff.common.no"/>',
                handler: writeoff.ApplyInvoiceNO,
                columnWidth: .15
            }]
        }
        ]
    });

    Ext.define('Foss.statementbill.ApplyInvoiceWindow', {
        extend: 'Ext.window.Window',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
        width: 250,
        height: 155,
        modal: true,
        constrainHeader: true,
        closeAction: 'destory',
        items: [Ext.create('Foss.statementbill.ApplyInvoiceForm')]
    });

    /**
     * 对账单确认
     */
    writeoff.statementConfirm = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var statementEntryEditGrid = writeoff.statementEntryEditGrid;
        //如果本期数据为空，则提示不能打印
        if (statementEntryEditGrid.store.data.length == 0) {
            Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noCurrentDataToConfirm"/>', 'error', 1000);
            return false;
        }
        //是否要发送邮件提示
        Ext.Msg.confirm('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '是否要发送邮件通知？', function (o) {
            if (o == 'yes') {
                writeoff.sendFlag = writeoff.SENDEMAIL_Y;
                writeoff.ApplyInvoiceWindow = Ext.create('Foss.statementbill.ApplyInvoiceWindow');
                writeoff.ApplyInvoiceWindow.show();
                //writeoff.statementConfirm_SendEmail(sendFlag);
            } else {
                writeoff.sendFlag = writeoff.SENDEMAIL_N;
                writeoff.ApplyInvoiceWindow = Ext.create('Foss.statementbill.ApplyInvoiceWindow');
                writeoff.ApplyInvoiceWindow.show();
                //writeoff.statementConfirm_SendEmail(sendFlag);
            }
        });
    }

    /**
     * 反确认对账单
     */
    writeoff.statementUnConfirm = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        //声明后面要用的参数
        var statementOfAccountMakeQueryResultVo,
                baseInfo,
        //声明对账单基本信息
                baseInfo = writeoff.baseInfo;
        //获取对账单号
        number = baseInfo.getForm().findField('statementBillNo').getValue();

        var statementEntryEditGrid = writeoff.statementEntryEditGrid;
        //如果本期数据为空，则提示不能反确认
        //if(statementEntryEditGrid.store.data.length==0){
        //Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noCurrentDataToUnConfirm"/>', 'error', 1000);
        //return false;
        //}

        //判断单号是否为空
        if (number == null || number == '') {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementNumberIsNullWarning"/>');
            return false;
        }
        //判断对账单界面对账单确认状态
        if (baseInfo.getForm().findField('confirmStatus').getValue() == writeoff.STATEMENTCONFIRMSTATUS_N) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.errorStatusToUnConfirmWarning"/>');
            return false;
        }
        //获取传入后台参数，当没有操作行时，传入null
        statementOfAccountMakeQueryResultVo = writeoff.statementEntity(null);
        statementOfAccountMakeQueryResultVo.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_N;

        //打开注释
        Ext.Ajax.request({
            url: writeoff.realPath('confirmStatement.action'),
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                //因为此处没有返回值，故而需要手动去添加版本号 和 确认状态  和确认时间
                baseInfo.getForm().findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_N);
                //获取版本号和确认时间
                var versionNo = baseInfo.getForm().findField('versionNo');
                var confirmTime = baseInfo.getForm().findField('confirmTime');
                //手动修改版本号和确认时间
                versionNo.setValue(versionNo.getValue() + 1);
                confirmTime.setValue(null);//反确认将清空时间
                //获取form表的的record
                var baseFormRecord = baseInfo.getForm().getRecord();
                //下面一句让更新表单的record
                baseInfo.getForm().updateRecord(baseFormRecord);

                //隐藏确认按钮，显示反确认按钮
                writeoff.operateButtonPanel.items.items[2].hide();
                writeoff.operateButtonPanelDown.items.items[2].hide();
                writeoff.operateButtonPanel.items.items[1].show();
                writeoff.operateButtonPanelDown.items.items[1].show();
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.unConfirmSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });

    }

    /**
     * 还款/批量还款提交后台公共方法
     */
    writeoff.statementRepaymentSuccess = function (repaymentBillWindow, number, selections, repayType) {
        var statementOfAccountMakeQueryResultDto,
                statementOfAccountMakeQueryResultVo,
                unifiedSettlement
                ;
        //设置还款来源界面
        writeoff.repayType = repayType;
        //获取当前所选航数据	
        statementOfAccountMakeQueryResultDto = new Object();
        statementOfAccountMakeQueryResultDto.statementOfAccountList = selections;
        statementOfAccountMakeQueryResultDto.repayType = repayType;//还款来源 区分 还款是从对账单明细界面 还是对账单查询界面，从而刷新对应界面
        //将dto注入到后台中
        statementOfAccountMakeQueryResultVo = new Object();
        statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;

        //将数据传递给后台
        Ext.Ajax.request({
            url: writeoff.realPath('repayment.action'),
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                //刷新对应界面	
                if (repayType == writeoff.PAGEFROM_STATEMENTENTRY) {
                    //进行判断是否为空
                    if (!Ext.isEmpty(writeoff.baseInfo)) {
                        //获取核销完毕的对账单实体
                        statementOfAccount = result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto.statementOfAccount;
                        //ddw
                        unifiedSettlement = statementOfAccount.unifiedSettlement;
                        var model = new Foss.statementbill.StatementFormModel(statementOfAccount);
                        //更新数据record,此处需要3个地方都更新，不然merge时候，另外两个版本号未更新会有问题
                        writeoff.baseInfo.loadRecord(model);
                        writeoff.beginBillsForm.loadRecord(model);
                        writeoff.currentBillsForm.loadRecord(model);
                        //刷新界面  包含一些按钮显示和隐藏 
                        var model = new Foss.statementbill.StatementFormModel(statementOfAccount);
                        //声明
                        var currentBillsFormAdd = writeoff.currentBillsForm;
                        writeoff.statementUnVerifyAmountShow(model, currentBillsFormAdd, writeoff.PAGEFROM_STATEMENTEDIT);
                    }
                } else {
                    var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
                    var selections = grid.getSelectionModel().getSelection();
                    for (var i = 0; i < selections.length; i++) {
                        //判断是否统一结算
                        if (unifiedSettlement == null) {
                            unifiedSettlement = selections[i].get('unifiedSettlement');
                        } else {
                            if (unifiedSettlement != selections[i].get('unifiedSettlement')) {
                                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                                        '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>');
                                return false;
                            }
                        }
                    }
                    if (!Ext.isEmpty(grid)) {
                        grid.store.load();
                    }
                }
                //获取还款单数据进行封装
                var repaymentStatementForm = repaymentBillWindow.items.items[0];
                var repaymentForm = repaymentBillWindow.items.items[1];
                //创建对账单界面数据模型 --此处需要将后台数据放入
                var repaymentStatementFormModel = new Foss.statementbill.RepaymentStatementModel(result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto);
                repaymentStatementFormModel.set('statementBillNo', number);
                writeoff.unifiedSettlement = unifiedSettlement;
                //将对账单数据加载到还款单界面
                repaymentStatementForm.loadRecord(repaymentStatementFormModel);
                repaymentForm.getForm().reset();
                repaymentForm.getForm().findField('repaymentType').enable();
                repaymentForm.getForm().findField('remittanceNumber').enable();
                repaymentForm.getForm().findField('description').enable();
                repaymentForm.getForm().findField('statementBillNo').setValue(number);
                repaymentForm.getForm().findField('customerCode').setValue(result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto.customerCode);
                repaymentForm.getForm().findField('customerName').setValue(result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto.customerName);
                repaymentForm.getForm().findField('versionNos').setValue(result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto.versionNos);
                repaymentForm.getForm().findField('repaymentAmount').setValue(result.statementOfAccountMakeQueryResultVo.statementToPaymentResultDto.currentRemainAmount);
                repaymentForm.getForm().findField('repaymentAmount').disable();
                repaymentBillWindow.show();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 还款
     */
    writeoff.statementRepayment = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        if (Ext.isEmpty(writeoff.repaymentBillWindow)) {
            writeoff.repaymentBillWindow = Ext.create('Foss.statementbill.RepaymentBillWindow');
        }
        repaymentBillWindow = writeoff.repaymentBillWindow;
        var baseInfo = writeoff.baseInfo;
        var number = baseInfo.getForm().findField('statementBillNo').getValue();
        var unpaidAmount = baseInfo.getForm().findField('unpaidAmount').getValue();

        if (unpaidAmount == 0) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.choiceStatement"/>' + number + '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.noAmountToReceiveWarning"/>');
            return false;
        }
        //此处baseInfo的getRecord 就可以获取整个对账单信息。因为其record绑定的model包含整个对账单
        writeoff.statementRepaymentSuccess(repaymentBillWindow, number, [baseInfo.getRecord().data], writeoff.PAGEFROM_STATEMENTENTRY);
    }

    /**
     * 最终还款/批量操作
     */
    writeoff.statementRepaymentComplete = function (me) {
        var billRepaymentManageVo,
                billStatementToPaymentQueryDto;

        var form = me.getForm();
        if (form.isValid()) {
            //处理对账单号
            var statementBillNos = [];
            var versionNos = [];//版本号集合
            statementBillNos = stl.splitToArray(form.findField('statementBillNo').getValue());
            //处理版本号、
            versionNos = stl.splitToArray(form.findField('versionNos').getValue());

            billStatementToPaymentQueryDto = new Object();
            billStatementToPaymentQueryDto.customerCode = form.findField('customerCode').getValue();
            billStatementToPaymentQueryDto.customerName = form.findField('customerName').getValue();
            billStatementToPaymentQueryDto.repaymentType = form.findField('repaymentType').getValue();
            billStatementToPaymentQueryDto.repaymentAmount = form.findField('repaymentAmount').getValue();
            billStatementToPaymentQueryDto.repaymentNotes = form.findField('description').getValue();
            billStatementToPaymentQueryDto.remittanceNumber = form.findField('remittanceNumber').getValue();
            billStatementToPaymentQueryDto.remittanceName = form.findField('remittanceName').getValue();
            billStatementToPaymentQueryDto.statementBillNos = statementBillNos;
            billStatementToPaymentQueryDto.versionNos = versionNos;
            billStatementToPaymentQueryDto.notes = me.up('window').items.items[0].getForm().findField('notes').getValue();

            /*
             * 网上支付时设置网上支付编号
             */
            if (billStatementToPaymentQueryDto.repaymentType == 'OL') {
                billStatementToPaymentQueryDto.onlinePaymentNo = billStatementToPaymentQueryDto.remittanceNumber;
                billStatementToPaymentQueryDto.remittanceNumber = '';
            }

            billRepaymentManageVo = new Object();
            billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
            //将数据传递给后台
            Ext.Ajax.request({
                timeout: 1800000,
                url: ContextPath.STL_WEB + '/pay/toRepayment.action',
                jsonData: {
                    'billRepaymentManageVo': billRepaymentManageVo
                },
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentSuccess"/>');
                    me.up('panel').hide();
                    //如果是从对账单明细界面来的，则关闭对账单明细界面
                    if (writeoff.repayType == writeoff.PAGEFROM_STATEMENTENTRY) {
                        var win = Ext.getCmp('Foss_statementbill_EditStatementEntryWindow_ID');
                        if (!Ext.isEmpty(win)) {
                            win.close();
                        }
                        //如果从对账单查询界面来，则需要进行刷新
                        if (!Ext.isEmpty(writeoff.showEntryType)) {
                            if (writeoff.showEntryType == writeoff.PAGEFROM_STATEMENTEDIT) {
                                var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
                                if (!Ext.isEmpty(grid)) {
                                    grid.store.load();
                                }
                            }
                        }

                        //如果是从查询对账单界面来的则刷新界面
                    } else {
                        var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
                        if (!Ext.isEmpty(grid)) {
                            grid.store.load();
                        }
                    }
                },
                exception: function (response) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
                }
            });
        } else {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.validateFailAlert"/>');
        }
    }

    /**
     * 核销完成后跳到付款界面
     */
    writeoff.statementWriteoffQuery = function (writeoffBillWin, selections, verifyType) {

        var statementOfAccountMakeQueryResultDto,
                statementOfAccountMakeQueryResultVo
                ;
        //获取当前所选航数据	
        statementOfAccountMakeQueryResultDto = new Object();
        statementOfAccountMakeQueryResultDto.statementOfAccountList = selections;
        //将dto注入到后台中
        statementOfAccountMakeQueryResultVo = new Object();
        statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;


        //将数据传递给后台
        Ext.Ajax.request({
            url: writeoff.realPath('batchWriteoffStatement.action'),
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var list = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDUnverifyList;
                if (!Ext.isEmpty(list)) {
                    writeoffBillWin.items.items[0].store.loadData(list);
                    writeoffBillWin.show();
                }
                ;
                //核销默认确认对账单，隐藏对账单确认按钮显示反确认按钮
                writeoff.operateButtonPanel.items.items[1].hide();
                writeoff.operateButtonPanelDown.items.items[1].hide();
                writeoff.operateButtonPanel.items.items[2].show();
                writeoff.operateButtonPanelDown.items.items[2].show();

                if (verifyType == writeoff.PAGEFROM_STATEMENTEDIT) {
                    var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
                    if (!Ext.isEmpty(grid)) {
                        grid.store.load();
                    }
                } else {
                    //获取核销完毕的对账单实体
                    statementOfAccount = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccount;
                    //构造对账单模型
                    var model = new Foss.statementbill.StatementFormModel(statementOfAccount);
                    //获取对账单头信息
                    writeoff.baseInfo.loadRecord(model);
                    writeoff.beginBillsForm.loadRecord(model);
                    writeoff.currentBillsForm.loadRecord(model);
                    //声明
                    var currentBillsFormAdd = writeoff.currentBillsForm;
                    //刷新界面  包含一些按钮显示和隐藏 
                    writeoff.statementUnVerifyAmountShow(model, currentBillsFormAdd, writeoff.PAGEFROM_STATEMENTEDIT);
                }
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.writeoffSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });

    }

    /**
     * 核销
     */
    writeoff.statementWriteoff = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        //获取对账单信息
        var baseInfo = writeoff.baseInfo.getRecord().data;
        if (Ext.isEmpty(writeoff.unVerifyEntryWindow)) {
            writeoff.unVerifyEntryWindow = Ext.create('Foss.statementbill.UnVerifyEntryWindow');
        }
        //跳转页面
        writeoff.statementWriteoffQuery(writeoff.unVerifyEntryWindow, [baseInfo], writeoff.PAGEFROM_STATEMENTENTRY)
    }

    /**
     * 付款界面的付款功能
     */
    writeoff.payment = function () {
        //获取grid
        var grid = this.up('grid');
        //判断是否选中值
        var selections = grid.getSelectionModel().getSelection();
        //如果数组值为0表示没有选中
        if (selections.length == 0) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.unSelectedToPayWarning"/>');
            return false;
        }
        //声明要付款的单号集合
        var billNos = [];
        //循环获取单号集合
        for (var i = 0; i < selections.length; i++) {
            //如果来源单号为空，则提示
            if (Ext.isEmpty(selections[i].get('sourceBillNo'))) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.waybillOfStatementEntry"/>' + selections[i].get('waybillNo') + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.sourceNoIsNullWarning"/>');
                return false;
            }
            //获取来源单号
            billNos.push(selections[i].get('sourceBillNo'));
        }
        var vo = new Object();
        vo.billNos = billNos;
        //发送请求传递信息到后台
        Ext.Ajax.request({
            url: ContextPath.STL_WEB + '/pay/payFromStatement.action',
            jsonData: {
                'vo': vo
            },
            method: 'POST',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                //获取付款单窗口
                if (Ext.isEmpty(pay.addPaymentWindow)) {
                    pay.addPaymentWindow = Ext.create('Foss.payment.AddPaymentWindow');
                }
                var win = pay.addPaymentWindow;
                var formModel = new Foss.payable.AddFormModel(result.vo.paymentEntity);
                //设置付款单弹出界面来源界面 为应付单
                formModel.set('sourceBillType', pay.SOURCE_BILL_TYPE__STATEMENT);//对账单
                win.getAddFormPanel().loadRecord(formModel);
                var payForm = win.getAddFormPanel().getForm();
                //如果账户非空，将其绑定到银行账户控件上 --理赔
                if (!Ext.isEmpty(formModel.get('accountNo'))) {
                    payForm.findField('accountNo').setCombValue(formModel.get('payeeName'), formModel.get('accountNo'));
                    //手动设置账户类型值，单传的loadRecord不会进行转化
                    payForm.findField('accountTypeName').setValue(FossDataDictionary.rendererSubmitToDisplay(formModel.get('accountType'), settlementDict.FIN_ACCOUNT_TYPE));
                    //设置银行账号从理赔带过来标志
                    payForm.findField('claimAccountNo').setValue(formModel.get('accountNo'));
                    payForm.findField('accountNo').setReadOnly(true);
                } else {
                    payForm.findField('accountNo').setReadOnly(false);
                    //设置银行账号从理赔带过来标志
                    payForm.findField('claimAccountNo').setValue(null);
                }
                //绑定发票抬头 为默认
                var comCode = payForm.findField('paymentCompanyCode').getValue();
                var comName = payForm.findField('paymentCompanyName').getValue();
                //获取发票抬头
                if (!Ext.isEmpty(comCode) && !Ext.isEmpty(comName)) {
                    payForm.findField('invoiceHeadCode').setCombValue(comName, comCode);
                }
                win.getAddPaymentEntryGrid().store.loadData(result.vo.addDtoList);
                //弹出付款单
                win.show();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 删除对账单明细
     */
    writeoff.statementRemoveEntry = function (grid, rowIndex, colIndex) {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var statementOfAccountMakeQueryResultVo,//对账单传递后台vo
                statementEntryEditGrid,
                statementEntryEditStore,
                beginBillsForm,
                currentBillsForm,
                selection;
        statementEntryEditGrid = writeoff.statementEntryEditGrid;
        statementEntryEditStore = statementEntryEditGrid.store;
        beginBillsForm = writeoff.beginBillsForm;
        currentBillsForm = writeoff.currentBillsForm;
        selection = grid.getStore().getAt(rowIndex);

        statementOfAccountMakeQueryResultVo = writeoff.statementEntity([selection]);

        Ext.Ajax.request({
            url: writeoff.realPath('deleteStatementEntry.action'),
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            method: 'POST',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                writeoff.statementEditRefresh(result);
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.deleteSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 批量删除
     */
    writeoff.statementRemoveEntryBatch = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var statementOfAccountMakeQueryResultVo,
                selection,
                statementEntryEditGrid,
                statementEntryEditStore,
                beginBillsForm,
                currentBillsForm,
                jsonData;

        selection = writeoff.statementEntryEditGrid.getSelectionModel().getSelection();
        if (selection.length == 0) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.unSelectedWarning"/>');
            return false;
        }
        statementEntryEditGrid = writeoff.statementEntryEditGrid;
        statementEntryEditStore = statementEntryEditGrid.store;
        beginBillsForm = writeoff.beginBillsForm;
        currentBillsForm = writeoff.currentBillsForm;

        statementOfAccountMakeQueryResultVo = writeoff.statementEntity(selection);
        Ext.Ajax.request({
            url: writeoff.realPath('deleteStatementEntry.action'),
            method: 'POST',
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                writeoff.statementEditRefresh(result);
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.deleteSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 修改对账单--增加对账单明细
     */
    writeoff.statementAddEntryEdit = function (grid, rowIndex, colIndex) {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var statementOfAccountMakeQueryResultVo,
                statementEntryEditGrid,
                statementEntryStore,
                beginBillsForm,
                currentBillsForm,
                baseInfo,
                number,
                selection;

        statementEntryEditGrid = writeoff.statementEntryEditGrid;
        statementEntryStore = statementEntryEditGrid.store;
        beginBillsForm = writeoff.beginBillsForm;
        currentBillsForm = writeoff.currentBillsForm;
        baseInfo = writeoff.baseInfo;
        number = baseInfo.getForm().findField('statementBillNo').getValue();
        selection = grid.getStore().getAt(rowIndex);

        //获取vo对象
        statementOfAccountMakeQueryResultVo = writeoff.statementEntity([selection]);

        Ext.Ajax.request({
            url: writeoff.realPath('addStatementDetail.action'),
            method: 'POST',
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                writeoff.statementEditRefresh(result);
                //本期数据从后台重新查出来刷新
                Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.addSuccess"/>', 'ok', 1000);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 添加对账单到明细查询 --按客户
     */
    writeoff.statementQueryForAddByCustInfo = function () {
        var form = this.up('form').getForm();
        var queryFormHiddenField = this.up('window').items.items[1].getForm();//!!
        var customerCode,
                periodBeginDateEdit,
                periodEndDateEdit,
                editBillType,
                queryPage;
        writeoff.customerCode = form.getValues().customerCode;
        customerCode = form.getValues().customerCode;
        writeoff.selectDateFlag = form.getValues().selectDateFlag;
        periodBeginDateEdit = form.findField('periodBeginDateEdit').getValue();
        periodEndDateEdit = form.findField('periodEndDateEdit').getValue();
        editBillType = form.getValues().editBillType;
        queryPage = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
        //至少选择一种进行查询
        if (editBillType == undefined) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.noSelectBillTypeWarning"/>');
            return false;
        }

        //校验日期
        if (Ext.isEmpty(periodBeginDateEdit) || Ext.isEmpty(periodEndDateEdit)) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.quryDateIsNullWarning"/>');
            return false;
        }

        if (form.isValid()) {
            //校验日期
            var periodBeginDateEdit = form.findField('periodBeginDateEdit').getValue();
            var periodEndDateEdit = form.findField('periodEndDateEdit').getValue();
            //比较起始日期和结束日期
            var compareTwoDate = stl.compareTwoDate(periodBeginDateEdit, periodEndDateEdit);
            if (compareTwoDate > stl.DATELIMITMAXDAYS_WEEK) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.queryDateMaxLimit"/>');
                return false;
            } else if (compareTwoDate < 1) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.dateEndBeforeStatrtWarining"/>');
                return false;
            }


            //每次查询完后，重置表单查询条件,将新的查询条件赋值进去
            queryFormHiddenField.reset();
            queryFormHiddenField.findField('customerCodeParam').setValue(customerCode);
            queryFormHiddenField.findField('periodBeginDateParam').setValue(periodBeginDateEdit);
            queryFormHiddenField.findField('periodEndDateParam').setValue(periodEndDateEdit);
            if (!Ext.isArray(editBillType)) {
                queryFormHiddenField.findField('billTypeParam').setValue([editBillType]);
            } else {
                queryFormHiddenField.findField('billTypeParam').setValue(editBillType);
            }

            queryFormHiddenField.findField('queryPage').setValue(queryPage);
            writeoff.addStatementVerifyEntryGrid.store.removeAll();
            //查询条件在初始化方法中加beforeLoad事件中
            writeoff.addStatementVerifyEntryGrid.store.load({
                callback: function (records, operation, success) {
                    var rawData = writeoff.addStatementVerifyEntryGrid.store.proxy.reader.rawData;
                    //当success:false ,isException:false  --业务异常
                    if (!success && !rawData.isException) {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', rawData.message);
                        return false;
                    }
                    if (success) {
                        var result = Ext.decode(operation.response.responseText);
                        //如果没有查询到数据，则提示
                        if (writeoff.addStatementVerifyEntryGrid.store.data.length == 0) {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.noResult"/>');
                            return false;
                        }
                        writeoff.addCurrentBillsForm.loadRecord(writeoff.currentBillsForm.getRecord());
                        //查询后显示隐藏组件
                        writeoff.addCurrentBillsForm.show();
                        writeoff.addStatementVerifyEntryGrid.show();

                    }
                }
            });
        } else {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.validateFailAlert"/>');
            return false;
        }
    }
    /**
     * 添加对账单到明细查询 --按单号
     */
    writeoff.statementQueryForAddByNumber = function () {
        var form = this.up('form').getForm();
        //获取隐藏域面板
        var queryFormHiddenField = this.up('window').items.items[1].getForm();//!!
        var billDetailNos = form.findField('billDetailNos').getValue();
        writeoff.customerCode = form.getValues().customerCode;
        var customerCode = form.getValues().customerCode;
        var queryPage = writeoff.STATEMENTQUERYTAB_BYNUMBER;
        //校验界面输入条件
        var billDetailNoArray = [];

        if (Ext.String.trim(billDetailNos) != null && Ext.String.trim(billDetailNos) != '') {
            var array = stl.splitToArray(billDetailNos);
            for (var i = 0; i < array.length; i++) {
                if (Ext.String.trim(array[i]) != '') {
                    billDetailNoArray.push(array[i]);
                }
            }
        } else {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.billNosIsNullWarning"/>');
            return false;
        }

        if (billDetailNoArray.length > 10) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.queryNosLimit"/>');
            return false;
        }
        //选择单据子类型
        var selectBillTypes = this.up('form').getValues().editBillType;
        if (selectBillTypes == undefined) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.noSelectBillTypeWarning"/>');
            return false;
        }
        if (selectBillTypes.length == 2 && selectBillTypes[0] == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
                && selectBillTypes[1] == writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.onlyPreAndAdvWarning"/>');
            return false;
        } else if (selectBillTypes == writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.onlyAdvWarning"/>');
            return false;
        }
        writeoff.billDetailTypes = selectBillTypes;

        if (form.isValid()) {
            //每次查询完后，重置表单查询条件,将新的查询条件赋值进去
            queryFormHiddenField.reset();
            queryFormHiddenField.findField('customerCodeParam').setValue(customerCode);
            queryFormHiddenField.findField('billDetailNosParam').setValue(billDetailNos);
            queryFormHiddenField.findField('queryPage').setValue(queryPage);
            //清空数据
            writeoff.addStatementVerifyEntryGrid.store.removeAll();
            //查询条件在初始化方法中加beforeLoad事件中
            writeoff.addStatementVerifyEntryGrid.store.load({
                callback: function (records, operation, success) {
                    var rawData = writeoff.addStatementVerifyEntryGrid.store.proxy.reader.rawData;
                    //当success:false ,isException:false  --业务异常
                    if (!success && !rawData.isException) {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', rawData.message);
                        return false;
                    }
                    if (success) {
                        var result = Ext.decode(operation.response.responseText);
                        //如果没有查询到数据，则提示
                        if (writeoff.addStatementVerifyEntryGrid.store.data.length == 0) {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.noResult"/>');
                            return false;
                        }

                        writeoff.addCurrentBillsForm.loadRecord(writeoff.currentBillsForm.getRecord());
                        //查询后显示隐藏组件
                        writeoff.addCurrentBillsForm.show();
                        writeoff.addStatementVerifyEntryGrid.show();
                    }
                }
            });
        } else {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.validateFailAlert"/>');
            return false;
        }
    }

    /**
     * 控制本期金额下面的 本期剩余未还金额图标显示和隐藏
     * @param {} model 模型 currentBillsFormEdit显示对象
     */
    writeoff.statementUnVerifyAmountShow = function (model, currentBillsForm, type) {
        var periodAmount,
                periodUnverifyRecAmount,
                periodUnverifyPayAmount,
                periodUnverifyPreAmount,
                periodUnverifyAdvAmount;
        //获取本期剩余金额进行判断
        periodAmount = model.get('periodAmount');
        unpaidAmount = model.get('unpaidAmount');
        periodUnverifyRecAmount = model.get('periodUnverifyRecAmount');
        periodUnverifyPayAmount = model.get('periodUnverifyPayAmount');
        periodUnverifyPreAmount = model.get('periodUnverifyPreAmount');
        periodUnverifyAdvAmount = model.get('periodUnverifyAdvAmount');
        //如果本期发生金额不等于0，则显示下面图标
        if (periodAmount != null && periodAmount != 0) {
            //显示图标
            currentBillsForm.items.items[15].show();
        } else {
            currentBillsForm.items.items[15].hide();
        }
        //如果是弹出窗口，则需要盘大u年核销和还款按钮显示和隐藏
        if (type == writeoff.PAGEFROM_STATEMENTEDIT) {
            //如果剩余未还金额大于0，则还款
            if (unpaidAmount != null && unpaidAmount > 0) {
                writeoff.operateButtonPanel.items.items[4].hide();
                writeoff.operateButtonPanelDown.items.items[4].hide();
                writeoff.operateButtonPanel.items.items[3].show();
                writeoff.operateButtonPanelDown.items.items[3].show();
                //如果剩余未还金额小于0，则核销
            } else if (unpaidAmount != null && unpaidAmount < 0) {
                writeoff.operateButtonPanel.items.items[3].hide();
                writeoff.operateButtonPanelDown.items.items[3].hide()
                writeoff.operateButtonPanel.items.items[4].show();
                writeoff.operateButtonPanelDown.items.items[4].show();
                //如果剩余金额等于0，则需要判断结账次数。如果结账次数>0,则隐藏还款和核销按钮，反之显示核销按钮
            } else if (unpaidAmount != null && unpaidAmount == 0) {
                //如果本次结账次数大于0，则隐藏还款和核销按钮
                if (model.get('settleNum') > 0) {
                    writeoff.operateButtonPanel.items.items[4].hide();
                    writeoff.operateButtonPanelDown.items.items[4].hide();
                    writeoff.operateButtonPanel.items.items[3].hide();
                    writeoff.operateButtonPanelDown.items.items[3].hide();
                    //反之显示核销按钮
                } else {
                    writeoff.operateButtonPanel.items.items[3].hide();
                    writeoff.operateButtonPanelDown.items.items[3].hide()
                    writeoff.operateButtonPanel.items.items[4].show();
                    writeoff.operateButtonPanelDown.items.items[4].show();
                }
            }

            //如果已经确认，则显示确认按钮，显示反确认按钮
            if (model.get('confirmStatus') == writeoff.STATEMENTCONFIRMSTATUS_Y) {
                //隐藏确认按钮
                writeoff.operateButtonPanel.items.items[1].hide();
                writeoff.operateButtonPanelDown.items.items[1].hide();
                //显示反确认按钮
                writeoff.operateButtonPanel.items.items[2].show();
                writeoff.operateButtonPanelDown.items.items[2].show();
            } else {
                //隐藏反确认按钮
                writeoff.operateButtonPanel.items.items[2].hide();
                writeoff.operateButtonPanelDown.items.items[2].hide();
                //显示确认按钮
                writeoff.operateButtonPanel.items.items[1].show();
                writeoff.operateButtonPanelDown.items.items[1].show();
            }

            //刷新界面版本号和确认状态，确认时间等
            var baseInfo = writeoff.baseInfo;
            baseInfo.getForm().findField('confirmStatus').setValue(model.get('confirmStatus'));
            baseInfo.getForm().findField('versionNo').setValue(model.get('versionNo'));
            baseInfo.getForm().findField('confirmTime').setValue(model.get('confirmTime'));
        }

        var recComponent = currentBillsForm.getForm().findField('periodUnverifyRecAmount');
        var payComponent = currentBillsForm.getForm().findField('periodUnverifyPayAmount');
        var preComponent = currentBillsForm.getForm().findField('periodUnverifyPreAmount');
        var advComponent = currentBillsForm.getForm().findField('periodUnverifyAdvAmount');
        //如果应收存在，则显示
        if (periodUnverifyRecAmount != null && periodUnverifyRecAmount != 0) {
            recComponent.show();
        } else {
            recComponent.hide();
        }
        //应付
        if (periodUnverifyPayAmount != null && periodUnverifyPayAmount != 0) {
            payComponent.show();
        } else {
            payComponent.hide();
        }
        //预收
        if (periodUnverifyPreAmount != null && periodUnverifyPreAmount != 0) {
            preComponent.show();
        } else {
            preComponent.hide();
        }
        //预付
        if (periodUnverifyAdvAmount != null && periodUnverifyAdvAmount != 0) {
            advComponent.show();
        } else {
            advComponent.hide();
        }

        //设置按钮权限 --确认
        if (!writeoff.isPermission('/stl-web/writeoff/confirmStatement.action')) {
            writeoff.operateButtonPanel.items.items[1].hide();
            writeoff.operateButtonPanelDown.items.items[1].hide();
        }
        //设置按钮权限--反确认
        if (!writeoff.isPermission('/stl-web/writeoff/confirmStatement.action')) {
            writeoff.operateButtonPanel.items.items[2].hide();
            writeoff.operateButtonPanelDown.items.items[2].hide();
        }
        //设置按钮权限--还款
        if (!writeoff.isPermission('/stl-web/writeoff/repayment.action')) {
            writeoff.operateButtonPanel.items.items[3].hide();
            writeoff.operateButtonPanelDown.items.items[3].hide();
        }
        //设置按钮权限--核销
        if (!writeoff.isPermission('/stl-web/writeoff/batchWriteoffStatement.action')) {
            writeoff.operateButtonPanel.items.items[4].hide();
            writeoff.operateButtonPanelDown.items.items[4].hide();
        }
    }

    /**
     * 重新load页面
     * @param {} result 返回结构集
     */
    writeoff.statementEditRefresh = function (result) {
        var baseInfoAdd,
                beginBillsFormAdd,
                currentBillsFormAdd,
                statementEntryAddGrid,
                statementOfAccountDBeginPeriodList,
                statementOfAccountDPeriodList,
                statementOfAccount,
                periodAmount,
                periodUnverifyRecAmount,
                periodUnverifyPayAmount,
                periodUnverifyPreAmount,
                periodUnverifyAdvAmount,
                model;

        baseInfoAdd = writeoff.baseInfo;
        beginBillsFormAdd = writeoff.beginBillsForm;
        currentBillsFormAdd = writeoff.currentBillsForm;
        statementEntryAddGrid = writeoff.statementEntryEditGrid;
        //获取表单后台数据
        statementOfAccount = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccount;
        //statementOfAccountDBeginPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDBeginPeriodList;
        statementOfAccountDPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDPeriodList;
        model = new Foss.statementbill.StatementFormModel(statementOfAccount);
        //控制本期金额图标显示和隐藏
        writeoff.statementUnVerifyAmountShow(model, currentBillsFormAdd, writeoff.PAGEFROM_STATEMENTEDIT);

        //如果已经确认，则显示确认按钮，显示反确认按钮
        if (model.get('confirmStatus') == writeoff.STATEMENTCONFIRMSTATUS_Y) {
            //隐藏确认按钮
            writeoff.operateButtonPanel.items.items[1].hide();
            writeoff.operateButtonPanelDown.items.items[1].hide();
            //显示反确认按钮
            writeoff.operateButtonPanel.items.items[2].show();
            writeoff.operateButtonPanelDown.items.items[2].show();
        } else {
            //隐藏反确认按钮
            writeoff.operateButtonPanel.items.items[2].hide();
            writeoff.operateButtonPanelDown.items.items[2].hide();
            //显示确认按钮
            writeoff.operateButtonPanel.items.items[1].show();
            writeoff.operateButtonPanelDown.items.items[1].show();
        }

        //form表单load后台数据
        baseInfoAdd.loadRecord(model);
        beginBillsFormAdd.loadRecord(model);
        currentBillsFormAdd.loadRecord(model);
        //刷新本期数据
        statementEntryAddGrid.store.removeAll();
        if (statementOfAccountDPeriodList != null) {
            statementEntryAddGrid.store.loadData(statementOfAccountDPeriodList);
        }
        statementEntryAddGrid.down('textfield').setValue(statementEntryAddGrid.store.data.length);
    }

    /**
     * 增加对账单到明细界面 --添加对账单到明细中
     */
    writeoff.statementAddEntryAdd = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var grid,
                selection,
                beginBillsForm,
                currentBillsForm,
                grid,
                statementOfAccountMakeQueryResultVo,
                myMaskEditQuery;
        grid = this.up('grid');
        selection = grid.getSelectionModel().getSelection();
        if (selection.length == 0) {
            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.unSelectedToAddWarning"/>');
            return false;
        }

        for (var i = 0; i < selection.length; i++) {
            var record = selection[i];
            var customerCode = record.get('customerCode');
            if (writeoff.customerCode != customerCode) {
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerErrorWarning"/>');
                return false;
            }
        }
        statementOfAccountMakeQueryResultVo = writeoff.statementEntity(selection);

        if (myMaskEditQuery == null) {
            myMaskEditQuery = new Ext.LoadMask(writeoff.AddStatementEntryWindow, {
                msg: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.saveStatementMask"/>',
                removeMask: true// 完成后移除
            });
        }
        myMaskEditQuery.show();

        Ext.Ajax.request({
            url: writeoff.realPath('addStatementDetail.action'),
            method: 'POST',
            jsonData: {
                'statementOfAccountMakeQueryResultVo': statementOfAccountMakeQueryResultVo
            },
            success: function (response) {
                //重新刷新界面
                var result = Ext.decode(response.responseText);
                myMaskEditQuery.hide();
                writeoff.statementEditRefresh(result);
                //保存成功隐藏界面
                currentBillsForm = writeoff.addCurrentBillsForm;
                currentBillsForm.hide();
                grid.store.removeAll();
                grid.hide();
                writeoff.AddStatementEntryWindow.hide();
            },
            failure: function (form, action) {
                myMaskEditQuery.hide();
            },
            unknownException: function (form, action) {
                myMaskEditQuery.hide();
            },
            exception: function (response) {
                myMaskEditQuery.hide();
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     *打印对账单
     */
    writeoff.statementPrint = function () {
        if (!writeoff.orgCodeInspect()) {
            return false;
        }
        var me = this;
        //获取对账单信息
        var statementInfo = writeoff.baseInfo.getRecord();
        var statementEntryEditGrid = writeoff.statementEntryEditGrid;

        Ext.MessageBox.show({
            title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
            msg: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.printRemind"/>',
            buttons: Ext.MessageBox.YESNOCANCEL,
            buttonText: {
                yes: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.defaultPrint"/>',
                no: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerPrint"/>',
                cancel: '<ext:i18nForJsp key="foss.stl.writeoff.common.cancel"/>'
            },
            fn: function (e) {
                //如果本期数据为空，则提示不能打印
                if (statementEntryEditGrid.store.data.length == 0) {
                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noCurrentDataToPrint"/>', 'error', 1000);
                    return false;
                }
                if (e == 'yes') {
                    //默认显示列
                    var arrayColumns = ['businessDate', 'waybillNo', 'arrvRegionCode', 'productCode',
                        'deliveryCustomerName', 'unitPrice', 'insuranceAmount', 'qty',
                        'billWeight', 'billingVolume', 'insuranceFee', 'deliverFee',
                        'packagingFee', 'otherFee', 'receiveMethod', 'paymentType',
                        'unverifyAmount'];
                    //后台传递东西
                    var targetObject = statementInfo.copy().data;

                    //拼接列头
                    targetObject.arrayColumns = arrayColumns;
                    targetObject.columnName1 = '<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>';
                    targetObject.columnName2 = '<ext:i18nForJsp key="foss.stl.writeoff.common.sourceBillNo"/>';
                    targetObject.columnName3 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.arrvRegionCode"/>';
                    targetObject.columnName4 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>';
                    targetObject.columnName5 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerName"/>';
                    targetObject.columnName6 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unitPrice"/>';
                    targetObject.columnName7 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceAmount"/>';
                    targetObject.columnName8 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.qty"/>';
                    targetObject.columnName9 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billWeight"/>';
                    targetObject.columnName10 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billingVolume"/>';
                    targetObject.columnName11 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceFee"/>';
                    targetObject.columnName12 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliverFee"/>';
                    targetObject.columnName13 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.packagingFee"/>';
                    targetObject.columnName14 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.otherFee"/>';
                    targetObject.columnName15 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveMethod"/>';
                    targetObject.columnName16 = '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>';
                    targetObject.columnName17 = '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>';

                    //设置该按钮灰掉
                    me.disable(false);
                    //10秒后自动解除灰掉效果
                    setTimeout(function () {
                        me.enable(true);
                    }, 10000);
                    //打印
                    do_printpreview('statementbill', targetObject, ContextPath.STL_WEB);

                } else if (e == 'no') {
                    //转化列头和列明
                    var columns = statementEntryEditGrid.columns;
                    var arrayColumns = [];
                    var index = 0;//列标
                    //传入后台打印，与index拼接为后台列明
                    var headerStr = "{";
                    //声明单据父类型、单据子类型
                    var billParentType, billType;

                    for (var i = 1; i < columns.length; i++) {
                        if (columns[i].isHidden() == false) {
                            var hederName = columns[i].text;
                            if (i != columns.length - 1) {
                                //如果存在单据子类型则必须存在单据父类型
                                if (columns[i].dataIndex == "billParentType") {
                                    billParentType = columns[i].dataIndex;
                                }
                                //如果所选列存在单据子类型，但是不存在单据父类型，则
                                if (columns[i].dataIndex == 'billType' && Ext.isEmpty(billParentType)) {
                                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noParentTypeWarning"/>', 'error', 1000);
                                    return false;
                                }
                                //获取操作列
                                if (columns[i].text != '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.actionColumn"/>') {
                                    index = index + 1;
                                    headerStr = headerStr + '\'' + 'columnName' + index + '\':\'' + hederName + '\',';
                                    arrayColumns.push(columns[i].dataIndex);
                                }
                            } else {
                                index = index + 1;
                                arrayColumns.push(columns[i].dataIndex);
                                headerStr = headerStr + '\'' + 'columnName' + index + '\':\'' + hederName + '\'';
                            }
                        }
                    }
                    //控制打印列数
                    if (arrayColumns.length > 17) {
                        Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.printColumnsLimit"/>', 'error', 1000);
                        return false;
                    }
                    headerStr = headerStr + '}';

                    var selections = new Array();
                    statementEntryEditGrid.store.each(function (record) {
                        selections.push(record.data);
                    });

                    var headerObject = Ext.decode(headerStr);
                    var targetObject = Ext.merge(headerObject, statementInfo.copy().data);

                    targetObject.arrayColumns = arrayColumns;
                    //设置该按钮灰掉
                    me.disable(false);
                    //10秒后自动解除灰掉效果
                    setTimeout(function () {
                        me.enable(true);
                    }, 10000);
                    //打印
                    do_printpreview('statementbill', targetObject, ContextPath.STL_WEB);
                } else {
                    return false;
                }

            }
        });
    }
    /**
     * 导出对账单
     */
    writeoff.statementExportPDF = function () {
        var statementInfo,
                columns,
                selections,
                arrayColumns,
                arrayColumnNames,
                statementEntryEditGrid;
        //获取对账单信息
        statementInfo = writeoff.baseInfo.getRecord().data;
        statementEntryEditGrid = writeoff.statementEntryEditGrid;
        var me = this;
        Ext.MessageBox.show({
            title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
            msg: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.exportRemind"/>',
            buttons: Ext.MessageBox.YESNOCANCEL,
            buttonText: {
                yes: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.defaultExport"/>',
                no: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerExport"/>',
                cancel: '<ext:i18nForJsp key="foss.stl.writeoff.common.cancel"/>'
            },
            fn: function (e) {
                //如果本期数据为空，则提示不能打印
                if (statementEntryEditGrid.store.data.length == 0) {
                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noDataToExport"/>', 'error', 1000);
                    return false;
                }

                if (e == 'yes') {

                    //默认显示列
                    arrayColumns = ['businessDate', 'waybillNo', 'arrvRegionCode', 'productCode',
                        'deliveryCustomerName', 'unitPrice', 'insuranceAmount', 'qty',
                        'billWeight', 'billingVolume', 'insuranceFee', 'deliverFee',
                        'packagingFee', 'otherFee', 'receiveMethod', 'paymentType',
                        'unverifyAmount'];

                    //拼接列头
                    arrayColumnNames = ['<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.common.sourceBillNo"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.arrvRegionCode"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerName"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unitPrice"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceAmount"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.qty"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billWeight"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billingVolume"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceFee"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliverFee"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.packagingFee"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.otherFee"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveMethod"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>'];

                } else if (e == 'no') {
                    //转化列头和列明
                    columns = statementEntryEditGrid.columns;
                    arrayColumns = [];
                    arrayColumnNames = [];
                    //声明单据父类型、单据子类型
                    var billParentType, billType;
                    //将前台对应列头传入到后台去
                    for (var i = 1; i < columns.length; i++) {
                        if (columns[i].isHidden() == false) {
                            var hederName = columns[i].text;
                            var dataIndex = columns[i].dataIndex;
                            if (columns[i].text != '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.actionColumn"/>') {
                                //如果存在单据子类型则必须存在单据父类型
                                if (columns[i].dataIndex == "billParentType") {
                                    billParentType = columns[i].dataIndex;
                                }
                                //如果所选列存在单据子类型，但是不存在单据父类型，则
                                if (columns[i].dataIndex == 'billType' && Ext.isEmpty(billParentType)) {
                                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noParentTypeWarning"/>', 'error', 1000);
                                    return false;
                                }
                                arrayColumns.push(dataIndex);
                                arrayColumnNames.push(hederName);
                            }
                        }
                    }
                    //控制打印列数
                    if (arrayColumns.length > 17) {
                        Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.printColumnsLimit"/>', 'error', 1000);
                        return false;
                    }
                } else {
                    return false;
                }

                var selections = new Array();
                statementEntryEditGrid.store.each(function (record) {
                    selections.push(record.data);
                });
                if (!Ext.fly('downloadAttachFileForm')) {
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }

                //设置该按钮灰掉
                me.disable(false);
                //10秒后自动解除灰掉效果
                setTimeout(function () {
                    me.enable(true);
                }, 10000);
                //拼接vo，注入到后台
                Ext.Ajax.request({
                    url: writeoff.realPath('exportStatement.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method: 'POST',
                    params: {
                        'statementOfAccountMakeQueryResultVo.arrayColumns': arrayColumns,
                        'statementOfAccountMakeQueryResultVo.arrayColumnNames': arrayColumnNames,
                        'statementOfAccountMakeQueryResultVo.exportType': 'byStatementDt',
                        'statementOfAccountMakeQueryResultVo.statementofAccountStr': Ext.JSON.encode(statementInfo),
                        'statementOfAccountMakeQueryResultVo.statementofAccountDetailStr': Ext.JSON.encode(selections)
                    },
                    isUpload: true,
                    success: function (response, options) {
                        Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.exportSuccess"/>', 'success', 1000);
                    }
                });
            }
        });
    }

    /**
     * 导出对账单
     */
    writeoff.statementExcel = function () {
        var statementInfo,
                columns,
                arrayColumns,
                arrayColumnNames,
                statementEntryEditGrid,
                statementNo;
        //获取对账单信息
        statementInfo = writeoff.baseInfo.getRecord().data;
        statementEntryEditGrid = writeoff.statementEntryEditGrid;
        statementNo = writeoff.baseInfo.getRecord().get('statementBillNo');
        var me = this;
        Ext.MessageBox.show({
            title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
            buttons: Ext.MessageBox.YESNOCANCEL,
            buttonText: {
                yes: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.defaultExport"/>',
                no: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerExport"/>',
                cancel: '<ext:i18nForJsp key="foss.stl.writeoff.common.cancel"/>'
            },
            fn: function (e) {
                //如果本期数据为空，则提示不能导出excel
                if (statementEntryEditGrid.store.data.length == 0) {
                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noDataToExport"/>', 'error', 1000);
                    return false;
                }

                if (e == 'yes') {
                    //默认显示列
                    arrayColumns = ['sourceBillNo', 'waybillNo', 'billParentType', 'billType',
                        'customerCode', 'customerName', 'amount', 'verifyAmount',
                        'unverifyAmount', 'orgCode', 'orgName', 'businessDate',
                        'accountDate', 'paymentType', 'productCode'];
                    //默认显示列名称
                    arrayColumnNames = ['<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.waybillNo"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billParentType"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.totalAmount"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.verifyAmount"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.orgCode"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.orgName"/>', '<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>',
                        '<ext:i18nForJsp key="foss.stl.writeoff.common.accountDate"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>'];
                } else if (e == 'no') {
                    //转化列头和列明
                    columns = statementEntryEditGrid.columns;
                    arrayColumns = [];
                    arrayColumnNames = [];
                    //声明单据父类型、单据子类型
                    var billParentType, billType;
                    //将前台对应列头传入到后台去
                    for (var i = 1; i < columns.length; i++) {
                        if (columns[i].isHidden() == false) {
                            var hederName = columns[i].text;
                            var dataIndex = columns[i].dataIndex;
                            if (columns[i].text != '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.actionColumn"/>') {
                                //如果存在单据子类型则必须存在单据父类型
                                if (columns[i].dataIndex == "billParentType") {
                                    billParentType = columns[i].dataIndex;
                                }
                                //如果所选列存在单据子类型，但是不存在单据父类型，则
                                if (columns[i].dataIndex == 'billType' && Ext.isEmpty(billParentType)) {
                                    Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.noParentTypeWarning"/>', 'error', 1000);
                                    return false;
                                }
                                arrayColumns.push(dataIndex);
                                arrayColumnNames.push(hederName);
                            }
                        }
                    }
                } else {
                    return false;
                }

                if (!Ext.fly('downloadAttachFileForm')) {
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }

                //拼接vo，注入到后台
                Ext.Ajax.request({
                    url: writeoff.realPath('exportStatementDetail.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method: 'POST',
                    params: {
                        'statementOfAccountMakeQueryResultVo.arrayColumns': arrayColumns,
                        'statementOfAccountMakeQueryResultVo.arrayColumnNames': arrayColumnNames,
                        'statementOfAccountMakeQueryResultVo.statementNo': statementNo
                    },
                    isUpload: true,
                    success: function (response, options) {
                        Ext.ux.Toast.msg('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.exportSuccess"/>', 'success', 1000);
                    }
                });
            }
        });
    }

    /**************************************qiaolifeng 对账单回执**************************************************/
    /**
     * 生成打印对账单回执
     */
    writeoff.statementConfReceipt = function (statementBillId) {
        var statementBillId,
                statementOfAccountMakeQueryVo,
                statementOfAccountMakeQueryDto;

        //将dto注入到后台中
        statementOfAccountMakeQueryVo = new Object();
        statementOfAccountMakeQueryDto = new Object();
        statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto = statementOfAccountMakeQueryDto;
        statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.statementBillId = statementBillId;

        //提交后台生产对账单回执，并返回回执数据
        Ext.Ajax.request({
            url: writeoff.realPath('addStatementConfReceipt.action'),
            method: 'POST',
            jsonData: {
                'statementOfAccountMakeQueryVo': statementOfAccountMakeQueryVo
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);
                if (Ext.isEmpty(writeoff.confReceiptStatementEntryWindow)) {
                    writeoff.confReceiptStatementEntryWindow = Ext.create('Foss.statementbill.ConfReceiptStatementEntryWindow');
                    win = writeoff.confReceiptStatementEntryWindow;
                }
                //获取弹出窗口
                win = writeoff.confReceiptStatementEntryWindow;
                var model = new Foss.statementbill.StatementFormModel(result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccount);
                win.items.items[0].loadRecord(model);
                win.items.items[1].loadRecord(model);
                win.items.items[1].getForm().findField('paidAmount').setValue(result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.paidAmount);
                win.items.items[1].getForm().findField('createUserName').setValue(stl.emp.empName);
                win.show();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 查询最新打印的对账单回执
     */
    writeoff.queryNewestStatementReceipt = function () {
        var statementBillNo,
                cfsTopForm,
                statementConfReceiptVo,
                statementConfReceiptDto;

        cfsTopForm = writeoff.confReceiptStatementTopForm;
        statementBillNo = cfsTopForm.getForm().findField('statementBillNo').getValue();

        //将dto注入到后台中
        statementConfReceiptVo = new Object();
        statementConfReceiptDto = new Object();
        statementConfReceiptVo.statementConfReceiptDto = statementConfReceiptDto;
        statementConfReceiptVo.statementConfReceiptDto.statementBillNo = statementBillNo;

        //提交后台生产对账单回执，并返回回执数据
        Ext.Ajax.request({
            url: writeoff.realPath('queryStatementReceipt.action'),
            jsonData: {
                'statementConfReceiptVo': statementConfReceiptVo
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);

                //如果没有打印过回执，提示
                if (result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity == null ||
                        result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity == '') {
                    Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
                    return false;
                } else {
                    if (result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CASH) {
                        result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.cash"/>';
                    } else if (result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CARD) {
                        result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.bankcard"/>';
                    } else if (result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE) {
                        result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.check"/>';
                    } else if (result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER) {
                        result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity.paymentType = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticket"/>';
                    }
                }

                //获取弹出窗口
                win = writeoff.confReceiptStatementEntryQueryWindow;
                if (Ext.isEmpty(writeoff.confReceiptStatementEntryQueryWindow)) {
                    writeoff.confReceiptStatementEntryQueryWindow = Ext.create('Foss.statementbill.ConfReceiptStatementEntryQueryWindow');
                    win = writeoff.confReceiptStatementEntryQueryWindow;
                }

                var model = new Foss.statementbill.StatementConfReceiptFormModel(result.statementConfReceiptVo.statementConfReceiptDto.statementConfReceiptEntity);
                win.items.items[0].loadRecord(model);
                win.show();
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    /**
     * 打印对账单回执
     */
    writeoff.printStatementReceipt = function () {
        var statementBillId,
                cfsTopForm,
                statementConfReceiptVo,
                statementConfReceiptDto;

        cfsTopForm = writeoff.confReceiptStatementTopForm;
        statementBillId = cfsTopForm.getForm().findField('id').getValue();

        //将dto注入到后台中
        statementConfReceiptVo = new Object();
        statementConfReceiptDto = new Object();
        statementConfReceiptVo.statementConfReceiptDto = statementConfReceiptDto;
        statementConfReceiptVo.statementConfReceiptDto.statementBillId = statementBillId;


        //提交后台生产对账单回执，并返回回执数据
        Ext.Ajax.request({
            url: writeoff.realPath('printStatementReceipt.action'),
            jsonData: {
                'statementConfReceiptVo': statementConfReceiptVo
            },
            method: 'post',
            success: function (response) {
                var result = Ext.decode(response.responseText);

                //调用打印
                do_printpreview('statementconfreceipt', {
                    'statementBillNo': statementBillId,
                    'createUserName': stl.emp.empName
                }, ContextPath.STL_WEB);
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
            }
        });
    }

    //客户类型：store
    Ext.define('Foss.statementbill.CustomerTypeStore', {
        extend: 'Ext.data.Store',
        fields: ['customerTypeCode', 'customerTypeName'],
        data: {
            'items': [
                {
                    customerTypeCode: '01',
                    customerTypeName: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerDetial"/>'
                },
                {
                    customerTypeCode: '02',
                    customerTypeName: '<ext:i18nForJsp key="foss.stl.writeoff.common.vehAgencyDetial"/>'
                },
                {
                    customerTypeCode: '03',
                    customerTypeName: '<ext:i18nForJsp key="foss.stl.writeoff.common.airAgencyDetial"/>'
                },
                {
                    customerTypeCode: '04',
                    customerTypeName: '<ext:i18nForJsp key="foss.stl.writeoff.common.airDetial"/>'
                },
                {
                    customerTypeCode: '05',
                    customerTypeName: '<ext:i18nForJsp key="foss.stl.writeoff.common.landStowage"/>'
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
     * 下拉框model的模型
     */
    Ext.define('Foss.statementbill.ComboModel', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'name'
        }, {
            name: 'value',
            type: 'int'
        }]
    });

    /**
     * 单据子类型的model
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
     * 单据子类型的store
     */
    Ext.define('Foss.statementbill.StatementBillType', {
        extend: 'Ext.data.Store',
        model: 'Foss.statementbill.queryComboModel',
        data: {
            'items': [
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerStatement"/>', value: 'CA'},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.angencyStatement"/>', value: 'AA'},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.airStatement"/>', value: 'AF'}
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
     * 还款单的Form对应的model
     */
    Ext.define('Foss.statementbill.RepaymentStatementModel', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'customerName'
        }, {
            name: 'customerCode'
        }, {
            name: 'idCard'
        }, {
            name: 'phone'
        }, {
            name: 'address'
        }, {
            name: 'statementBillNo'
        }, {
            name: 'currentAmount',
            type: 'double'
        }, {
            name: 'createTime',
            type: 'Date'
        }, {
            name: 'startDate',
            type: 'Date'
        }, {
            name: 'endDate',
            type: 'Date'
        }, {
            name: 'currentRemainAmount',
            type: 'double'
        }]
    });

    /**
     * 还款单付款方式store
     */
    Ext.define('Foss.statementbill.RepaymentType', {
        extend: 'Ext.data.Store',
        model: 'Foss.statementbill.ComboModel',
        data: {
            'items': [
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.cash"/>', value: 1},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.bankcard"/>', value: 2},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticket"/>', value: 3},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.check"/>', value: 4}
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
     * 对账单form表单model
     */
    Ext.define('Foss.statementbill.StatementFormModel', {
        extend: 'Ext.data.Model',
        fields: [{
            //客户编码
            name: 'customerCode'
        }, {
            //单据类型
            name: 'billType'
        }, {
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
            name: 'confirmStatus'
        }, {
            name: 'periodBeginDate',
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
            name: 'companyCode'
        }, {
            name: 'companyName'
        }, {
            name: 'customerName'
        }, {
            name: 'confirmTime',
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
            name: 'periodEndDate',
            type: 'date',
            convert: function (value) {
                if (value != null) {
                    //此处一定要这样写，不能转化成字符串，如果转化成字符串，那么你getRecord拿到的也是字符串。
                    var date = new Date(value);
                    return date;
                } else {
                    return null;
                }
            }
        }, {
            name: 'createOrgCode'
        }, {
            name: 'createUserName'
        }, {
            name: 'createOrgName'
        }, {
            name: 'unifiedCode'
        }, {
            name: 'statementBillNo'
        }, {
            name: 'companyAccountBankNo'
        }, {
            name: 'accountUserName'
        }, {
            name: 'bankBranchName'
        }, {
            name: 'unpaidAmount',
            type: 'double'
        }, {
            name: 'settleNum',
            type: 'int'
        }, {
            name: 'beginPeriodAmount',
            type: 'double'
        }, {
            name: 'beginPeriodRecAmount',
            type: 'double'
        }, {
            name: 'beginPeriodPayAmount',
            type: 'double'
        }, {
            name: 'beginPeriodAdvAmount',
            type: 'double'
        }, {
            name: 'beginPeriodPreAmount',
            type: 'double'
        }, {
            name: 'periodAmount',
            type: 'double'
        }, {
            name: 'periodRecAmount',
            type: 'double'
        }, {
            name: 'periodPayAmount',
            type: 'double'
        }, {
            name: 'periodPreAmount',
            type: 'double'
        }, {
            name: 'periodAdvAmount',
            type: 'double'
        }, {
            name: 'periodUnverifyRecAmount',
            type: 'double'
        }, {
            name: 'periodUnverifyPayAmount',
            type: 'double'
        }, {
            name: 'periodUnverifyPreAmount',
            type: 'double'
        }, {
            name: 'periodUnverifyAdvAmount',
            type: 'double'
        }, {
            name: 'notes'
        }, {
            name: 'id'
        }, {
            name: 'versionNo',
            type: 'short'
        }, {
            name: 'invoiceStatus'
        }, {
            name: 'applyInvoice'
        }, {
            //统一结算
            name: 'unifiedSettlement'
        }]
    });

    /**
     * 对账单确认状态store
     */
    Ext.define('Foss.statementbill.ConfirmStatusStore', {
        extend: 'Ext.data.Store',
        model: 'Foss.statementbill.queryComboModel',
        data: {
            'items': [
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.all"/>', value: null},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.comfirmed"/>', value: 'C'},
                {name: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.unConfirm"/>', value: 'N'}
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
     * 对账单明细表格model
     */
    Ext.define('Foss.statementbill.StatementEntryModel', {
        extend: 'Ext.data.Model',
        fields: [{
            //id
            name: 'id'
        }, {
            //运单号
            name: 'waybillNo'
        }, {
            //原始来源单据编号
            name: 'origSourceBillNo'
        }, {
            //版本号
            name: 'versionNo',
            type: 'short'
        }, {
            //总金额
            name: 'amount',
            type: 'double'
        }, {
            //已核销金额
            name: 'verifyAmount',
            type: 'double'
        }, {
            //未核销金额
            name: 'unverifyAmount',
            type: 'double'
        }, {
            //付款方式
            name: 'paymentType'
        }, {
            //运输性质
            name: 'productCode'
        }, {
            //提货方式
            name: 'receiveMethod'
        }, {
            //计费重量
            name: 'billWeight',
            type: 'double'
        }, {
            //计费体积
            name: 'billingVolume',
            type: 'double'
        }, {
            //目的站
            name: 'arrvRegionCode'
        }, {
            //提货网点
            name: 'customerPickupOrgName'
        }, {
            //收货人
            name: 'receiveCustomerName'
        }, {
            //收货人编码
            name: 'receiveCustomerCode'
        }, {
            //货物名称、
            name: 'goodsName'
        }, {
            //件数
            name: 'qty',
            type: 'int'
        }, {
            //公布价运费
            name: 'transportFee',
            type: 'double'
        }, {
            //送货费
            name: 'deliverFee',
            type: 'double'
        }, {
            //接货费
            name: 'pickupFee',
            type: 'double'
        }, {
            //保价费
            name: 'insuranceFee',
            type: 'double'
        }, {
            //包装费
            name: 'packagingFee',
            type: 'double'
        }, {
            //代收货款手续费
            name: 'codFee',
            type: 'double'
        }, {
            //增值费用
            name: 'valueAddFee',
            type: 'double'
        }, {
            //其他费用
            name: 'otherFee',
            type: 'double'
        }, {
            //优惠费用
            name: 'promotionsFee',
            type: 'double'
        }, {
            //单据子类型
            name: 'billType'
        }, {
            //来源单号
            name: 'sourceBillNo'
        }, {
            //对账单编号
            name: 'statementBillNo'
        }, {
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
        }, {
            //会计日期
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
            //始发网点编码
            name: 'origOrgCode'
        }, {
            //始发网点名称
            name: 'origOrgName'
        }, {
            //部门编码
            name: 'orgCode'
        }, {
            //部门名称
            name: 'orgName'
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
        }
            , {
                //发货客户编码
                name: 'deliveryCustomerCode'
            }, {
                //发货客户名称
                name: 'deliveryCustomerName'
            }, {
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
                //审核状态
                name: 'auditStatus'
            }, {
                //备注
                name: 'notes'
            }, {
                //是否删除
                name: 'isDelete',
                defaultValue: 'N'
            }, {
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
            }, {
                //单据父类型
                name: 'billParentType'
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
                //费率
                name: 'unitPrice',
                type: 'double'
            }, {
                //保价
                name: 'insuranceAmount',
                type: 'double'
            }, {
                //发货联系人
                name: 'deliveryCustomerContact'
            }, {
                //统一结算
                name: 'unifiedSettlement'
            }]
    });

    /**
     * 对账单明细store
     */
    Ext.define('Foss.statementbill.StatementEntryEditStore', {
        extend: 'Ext.data.Store',
        model: 'Foss.statementbill.StatementEntryModel',
        sorters: [{
            property: 'billParentType',
            direction: 'ASC'
        }]
    });

    /**
     * 添加对账单明细页面grid的store
     */
    Ext.define('Foss.statementbill.StatementEntryAddStore', {
        extend: 'Ext.data.Store',
        model: 'Foss.statementbill.StatementEntryModel',
        pageSize: 5,
        sorters: [{
            property: 'billParentType',
            direction: 'ASC'
        }],
        proxy: {
            type: 'ajax',
            url: writeoff.realPath('queryStatementForAdd.action'),
            actionMethods: 'post',
            reader: {
                type: 'json',
                root: 'statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDPeriodList',
                totalProperty: 'totalCount'
            }
        }
    });

    /**************************************还款单界面*******************************************/

    /**
     * 还款单页面的对账单信息
     */
    Ext.define('Foss.statementbill.RepaymentStatementForm', {
        extend: 'Ext.form.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementInfo"/>',
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
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            style: 'margin-left:100px',
            name: 'customerName',
            colspan: 2
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.phone"/>',
            name: 'phone'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            name: 'customerCode',
            style: 'margin-left:100px',
            colspan: 2
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.idCard"/>',
            name: 'idCard'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.address"/>',
            name: 'address',
            colspan: 3,
            style: 'margin-left:100px'
        }, {
            colspan: 3
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementBillNo"/>',
            name: 'statementBillNo',
            style: 'margin-left:100px',
            colspan: 3
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodAmount"/>',
            style: 'margin-left:100px',
            labelWidth: 95,
            xtype: 'numberfield',
            name: 'currentAmount',
            decimalPrecision: 2,
            colspan: 2
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.currentRemainAmount"/>',
            name: 'currentRemainAmount',
            labelWidth: 95,
            xtype: 'numberfield',
            decimalPrecision: 2
        }, {
            xtype: 'textareafield',
            name: 'notes',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.billDescription"/>',
            autoScroll: true,
            style: 'margin-left:100px',
            format: 'Y-m-d',
            readOnly: false,
            colspan: 3,
            width: (stl.SCREENWIDTH * 0.7 - 180) * 3 / 4
        }]
    });

    /**
     * 还款单信息
     */
    Ext.define('Foss.statementbill.RepaymentForm', {
        extend: 'Ext.form.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentInfo"/>',
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
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentType"/>',
            allowBlank: false,
            style: 'margin-left:100px',
            //store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE),
            store: FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE, null, null, [writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER]),
            queryModel: 'local',
            displayField: 'valueName',
            valueField: 'valueCode',
            value: writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
            editable: false,
            listeners: {
                //ddw
                'expand': function (field) {
                    var repaymentType;
                    if (!Ext.isEmpty(writeoff.unifiedSettlement) &&
                            writeoff.RECEIVABLEUNIFORM_Y == writeoff.unifiedSettlement) {
                        repaymentType = FossDataDictionary.getDataDictionaryStore(
                                settlementDict.SETTLEMENT__PAYMENT_TYPE, null, null, [
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__ONLINE]);
                    } else {
                        repaymentType = FossDataDictionary.getDataDictionaryStore(
                                settlementDict.SETTLEMENT__PAYMENT_TYPE, null, null, [
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__CASH,
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__CARD,
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE,
                                    writeoff.SETTLEMENT__PAYMENT_TYPE__ONLINE]);
                    }

                    //this.store.removeAll();
                    this.store.loadData(repaymentType.data.items);
                },
                'select': function (combo) {
                    if (combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                            || combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE
                            || combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__ONLINE) {

                        var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();

                        this.up('form').getForm().findField('remittanceNumber').enable();
                        this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceNumber"/>' + ':');
                        this.up('form').getForm().findField('remittanceNumber').regex = '';
                        this.up('form').getForm().findField('remittanceNumber').regexText = '';

                        this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                        this.up('form').getForm().findField('repaymentAmount').disable();

                    } else if (combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__CASH) {
                        var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();

                        this.up('form').getForm().findField('remittanceNumber').disable();
                        this.up('form').getForm().findField('remittanceNumber').labelEl.update('&nbsp;&nbsp;' + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceNumber"/>' + ':');
                        this.up('form').getForm().findField('remittanceNumber').setValue(null);
                        this.up('form').getForm().findField('remittanceName').setValue(null);
                        this.up('form').getForm().findField('remittanceDate').setValue(null);
                        this.up('form').getForm().findField('availableAmount').setValue(null);
                        this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                        this.up('form').getForm().findField('repaymentAmount').enable();
                    } else if (combo.value == writeoff.SETTLEMENT__PAYMENT_TYPE__CARD) {
                        this.up('form').getForm().findField('remittanceNumber').enable();
                        this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.batchNumber"/>' + ':');
                        this.up('form').getForm().findField('remittanceNumber').regex = /^\d*$/;
                        this.up('form').getForm().findField('remittanceNumber').regexText = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.inputBatchNumber"/>';

                        this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                        this.up('form').getForm().findField('repaymentAmount').enable();
                    }
                }
            }
        }, {
            fieldLabel: '<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceNumber"/>',
            name: 'remittanceNumber',
            listeners: {
                'blur': function (th) {
                    if (th.getValue() != null && th.getValue() != '') {
                        var form = this.up('form').getForm()
                        var repaymentAmount = form.findField('repaymentAmount').getValue();
                        var repaymentType = form.findField('repaymentType').getValue();

                        /** 付款方式为银行卡时不需要获取远程数据 */
                        if(repaymentType == 'CD'){
                            return false;
                        }

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
                                Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', result.message);
                                form.findField('remittanceNumber').setValue(null);
                            }
                        });
                    }
                }
            }
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceName"/>',
            name: 'remittanceName',
            labelWidth: 90,
            disabled: true
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentAmount"/>',
            style: 'margin-left:100px',
            name: 'repaymentAmount',
            disabled: true,
            xtype: 'numberfield',
            allowBlank: false
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceDate"/>',
            name: 'remittanceDate',
            format: 'Y-m-d',
            xtype: 'datefield',
            disabled: true
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.availableAmount"/>',
            name: 'availableAmount',
            disabled: true,
            labelWidth: 90
        }, {
            xtype: 'textareafield',
            name: 'description',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.description"/>',
            autoScroll: true,
            style: 'margin-left:100px',
            format: 'Y-m-d',
            colspan: 3,
            width: (stl.SCREENWIDTH * 0.7 - 180) * 2 / 3
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementBillNo"/>',
            name: 'statementBillNo',
            hidden: true
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.versionNo"/>',
            name: 'versionNos'
            , hidden: true
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            name: 'customerCode',
            hidden: true
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            name: 'customerName',
            hidden: true
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
                text: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.return"/>',
                width: 70,
                cls: 'yellow_button',
                handler: function () {
                    this.up('form').up('panel').hide();
                }
            }, {
                text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.repayment"/>',
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
                    var repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticket"/>';
                    var description = this.up('window').items.items[0].getForm().findField('notes').getValue();

                    if (repaymentAmount == null || repaymentAmount <= 0) {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentAmountMin"/>');
                        return false;
                    }
                    if (repaymentAmount > currentRemainAmount) {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentAmountMax"/>');
                        return false;
                    }
                    if (Ext.isEmpty(repaymentType)) {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentTypeIsNull"/>');
                        return false;
                    }
                    if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER) {
                        repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticket"/>';
                        if (remittanceNumber == null || remittanceNumber == '') {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull"/>');
                            return false;
                        }
                        if (repaymentAmount > availableAmount) {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticketRepaymentAmountMax"/>');
                            return false;
                        }
                    }
                    if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__ONLINE) {
                        repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.online"/>';
                        if (remittanceNumber == null || remittanceNumber == '') {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.checkOnlineRemittanceNumberIsNull"/>');
                            return false;
                        }
                        if (repaymentAmount > availableAmount) {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.onlineRepaymentAmountMax"/>');
                            return false;
                        }
                    }
                    if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE) {
                        repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.check"/>';
                        if (remittanceNumber == null || remittanceNumber == '') {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull"/>');
                            return false;
                        }
                        if (repaymentAmount > availableAmount) {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.checkRepaymentAmountMax"/>');
                            return false;
                        }
                    }
                    if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CASH) {
                        repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.cash"/>';
                    }
                    if (repaymentType == writeoff.SETTLEMENT__PAYMENT_TYPE__CARD) {
                        repaymentTypeStr = '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.bankcard"/>';
                        if (remittanceNumber == null || remittanceNumber == '') {
                            Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.checkBatchNumberIsNull"/>');
                            return false;
                        }
                    }
                    if (repaymentAmount < currentRemainAmount && description == "") {
                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.takeBillDescription"/>');
                        return false;
                    }

                    if (repaymentAmount < currentRemainAmount && description != "") {
                        Ext.MessageBox.show({
                            title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                            msg: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.currentRepaymentType"/>' + repaymentTypeStr + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentAlert"/>',
                            buttons: Ext.MessageBox.YESCANCEL,
                            buttonText: {
                                yes: '<ext:i18nForJsp key="foss.stl.writeoff.common.ok"/>',
                                cancel: '<ext:i18nForJsp key="foss.stl.writeoff.common.cancel"/>'
                            },
                            fn: function (e) {
                                if (e == 'yes') {
                                    //设置该按钮灰掉
                                    me.disable(false);
                                    //10秒后自动解除灰掉效果
                                    setTimeout(function () {
                                        me.enable(true);
                                    }, 10000);
                                    writeoff.statementRepaymentComplete(form);
                                } else if (e == 'cancel') {
                                    return false;
                                }
                            }
                        });
                    } else if (repaymentAmount == currentRemainAmount) {
                        Ext.MessageBox.show({
                            title: '<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                            msg: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.currentRepaymentType"/>' + repaymentTypeStr + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.isRepaymentAlert"/>',
                            buttons: Ext.MessageBox.YESCANCEL,
                            buttonText: {
                                yes: '<ext:i18nForJsp key="foss.stl.writeoff.common.ok"/>',
                                cancel: '<ext:i18nForJsp key="foss.stl.writeoff.common.cancel"/>'
                            },
                            fn: function (e) {
                                if (e == 'yes') {
                                    //设置该按钮灰掉
                                    me.disable(false);
                                    //10秒后自动解除灰掉效果
                                    setTimeout(function () {
                                        me.enable(true);
                                    }, 10000);
                                    writeoff.statementRepaymentComplete(form);
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
     * 整个还款单的窗口
     */
    Ext.define('Foss.statementbill.RepaymentBillWindow', {
        extend: 'Ext.window.Window',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentBill"/>',
        width: stl.SCREENWIDTH * 0.7,
        modal: true,
        constrainHeader: true,
        closeAction: 'hide',
        items: [
            Ext.create('Foss.statementbill.RepaymentStatementForm'),
            Ext.create('Foss.statementbill.RepaymentForm')
        ],
        listeners:{
            show : function(th){
                th.down('[name=remittanceNumber]').labelEl.update('<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.remittanceNumber"/>' + ':');
            }
        }
    });


    /*********************************核销付款**************************************************/
    /**
     * 未完全核销完明细Store
     */
    Ext.define('Foss.statementbill.UnVerifyEntryStore', {
        extened: 'Ext.data.Store',
        model: 'Foss.statementbill.StatementEntryModel'
    });

    /**
     * 未完全核销明细grid
     */
    Ext.define('Foss.statementbill.UnVerifyEntryGrid', {
        extend: 'Ext.grid.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.unVerifyEntry"/>',
        height: 500,
        frame: true,
        bodyCls: 'autoHeight',
        cls: 'autoHeight',
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        store: Ext.create('Foss.statementbill.UnVerifyEntryStore'),
        viewConfig: {
            enableTextSelection: true//设置行可以选择，进而可以复制
        },
        columns: [{
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>',
            dataIndex: 'sourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.waybillNo"/>',
            dataIndex: 'waybillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.origSourceBillNo"/>',
            dataIndex: 'origSourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billParentType"/>',
            dataIndex: 'billParentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
            dataIndex: 'billType',
            renderer: function (v, m, record) {
                return writeoff.statementFormatBillType(v, record);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            dataIndex: 'customerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            dataIndex: 'customerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.totalAmount"/>',
            dataIndex: 'amount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.verifyAmount"/>',
            dataIndex: 'verifyAmount',
            hidden: true,
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>',
            dataIndex: 'unverifyAmount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgCode"/>',
            dataIndex: 'orgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgName"/>',
            dataIndex: 'orgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>',
            dataIndex: 'businessDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.accountDate"/>',
            dataIndex: 'accountDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>',
            dataIndex: 'paymentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>',
            dataIndex: 'productCode',
            renderer: function (value) {
                return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveMethod"/>',
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
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billWeight"/>',
            dataIndex: 'billWeight'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billingVolume"/>',
            dataIndex: 'billingVolume'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.arrvRegionCode"/>',
            dataIndex: 'arrvRegionCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerPickupOrgName"/>',
            dataIndex: 'customerPickupOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerName"/>',
            dataIndex: 'receiveCustomerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerCode"/>',
            dataIndex: 'receiveCustomerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.goodsName"/>',
            dataIndex: 'goodsName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.qty"/>',
            dataIndex: 'qty'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.transportFee"/>',
            dataIndex: 'transportFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliverFee"/>',
            dataIndex: 'deliverFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.pickupFee"/>',
            dataIndex: 'pickupFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceFee"/>',
            dataIndex: 'insuranceFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.packagingFee"/>',
            dataIndex: 'packagingFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.codFee"/>',
            dataIndex: 'codFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.valueAddFee"/>',
            dataIndex: 'valueAddFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.otherFee"/>',
            dataIndex: 'otherFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.promotionsFee"/>',
            dataIndex: 'promotionsFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgCode"/>',
            dataIndex: 'origOrgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgName"/>',
            dataIndex: 'origOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgName"/>',
            dataIndex: 'destOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgCode"/>',
            dataIndex: 'destOrgCode'
        }
            , {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerCode"/>',
                dataIndex: 'deliveryCustomerCode'
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerName"/>',
                dataIndex: 'deliveryCustomerName'
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.signDate"/>',
                dataIndex: 'signDate',
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.auditStatus"/>',
                dataIndex: 'auditStatus',
                renderer: function (value) {
                    var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                    return displayField;
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.notes"/>',
                dataIndex: 'notes',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.isDelete"/>',
                dataIndex: 'isDelete',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
                dataIndex: 'statementBillNo',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.disableTime"/>',
                dataIndex: 'disableTime',
                hidden: true,
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.createTime"/>',
                dataIndex: 'createTime',
                hidden: true,
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: 'id',
                dataIndex: 'id',
                hidden: true
            }],
        dockedItems: [{
            xtype: 'toolbar',
            dock: 'bottom',
            margin: '10 0 0 0 ',
            layout: 'column',
            items: [{
                xtype: 'container',
                border: false,
                html: '&nbsp;',
                columnWidth: .9
            }, {
                xtype: 'button',
                text: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.payment"/>',
                columnWidth: .1,
                handler: writeoff.payment,
                hidden: !writeoff.isPermission('/stl-web/pay/payFromStatement.action')
            }]
        }]
    });

    /**
     * 未完全核销单据弹出界面
     */
    Ext.define('Foss.statementbill.UnVerifyEntryWindow', {
        extend: 'Ext.window.Window',
        width: 900,
        modal: true,
        constrainHeader: true,
        closeAction: 'hide',
        items: [
            Ext.create('Foss.statementbill.UnVerifyEntryGrid')
        ]
    });


    /********************************对账单明细****************************************************************/
    /**
     * 基本信息
     */
    Ext.define('Foss.statementbill.BaseInfo', {
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
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billType"/>',
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
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.createTime"/>',
            name: 'createTime',
            format: 'Y-m-d H:i:s',
            columnWidth: .28,
            value: new Date()
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            name: 'customerCode',
            columnWidth: .24
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.confirmStatus"/>',
            name: 'confirmStatus',
            xtype: 'combo',
            store: FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS),
            queryModel: 'local',
            displayField: 'valueName',
            valueField: 'valueCode',
            columnWidth: .24,
            value: 0
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodBeginDate"/>',
            name: 'periodBeginDate',
            xtype: 'datefield',
            format: 'Y-m-d',
            columnWidth: .22
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.companyName"/>',
            name: 'companyName',
            columnWidth: .28
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.companyCode"/>',
            name: 'companyCode',
            hidden: true,
            columnWidth: .28
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerName"/>',
            name: 'customerName',
            labelWidth: 95,
            columnWidth: .24
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.confirmTime"/>',
            name: 'confirmTime',
            xtype: 'datefield',
            format: 'Y-m-d H：i:s',
            columnWidth: .24
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodEndDate"/>',
            name: 'periodEndDate',
            xtype: 'datefield',
            format: 'Y-m-d',
            columnWidth: .22,
            value: new Date()
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.createOrgName"/>',
            name: 'createOrgName',
            columnWidth: .28
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.createOrgCode"/>',
            name: 'createOrgCode',
            hidden: true,
            columnWidth: .28
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
            name: 'statementBillNo',
            columnWidth: .22
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unifiedSettlement"/>',
            name: 'unifiedSettlement',
            columnWidth: .22,
            hidden: true
        }, {
            fieldLabel: 'id',
            name: 'id',
            columnWidth: .22,
            hidden: true
        }, {
            xtype: 'numberfield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.versionNo"/>',
            name: 'versionNo',
            columnWidth: .22
            , hidden: true
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unifiedCode"/>',
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
                    html: '<span style="color:red;"><ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unifiedCodeNotes"/></span>'
                }
            }]
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.companyAccountBankNo"/>',
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
                    html: '<span style="color:red;"><ext:i18nForJsp key="foss.stl.writeoff.statementAdd.companyAccountBankNoNotes"/></span>'
                }
            }]
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.accountUserName"/>',
            name: 'accountUserName',
            columnWidth: .5
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.bankBranchName"/>',
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
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unpaidAmount"/>',
            labelWidth: 110,
            name: 'unpaidAmount',
            xtype: 'numberfield',
            decimalPrecision: 2,
            columnWidth: .5,
            value: 0
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.settleNum"/>',
            columnWidth: .48,
            name: 'settleNum',
            xtype: 'numberfield',
            value: 0
        }]
    });

    /**
     * 期初账单的form  --对账单期初跟许明明确认不用了。制作完后就不用了要隐藏掉
     */
    Ext.define('Foss.statementbill.BeginBillsForm', {
        extend: 'Ext.form.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodBeginBills"/>',
        layout: 'column',
        hidden: true,
        collapsible: true,
        animCollapse: true,
        frame: true,
        defaultType: 'displayfield',
        defaults: {
            labelWidth: 5,
            readOnly: true
        },
        items: [{
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.beginPeriodAmount"/>',
            columnWidth: .15,
            style: 'margin-left:30px;margin-top:10px;'
        }, {
            value: '=',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.beginPeriodRecAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '-',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.beginPeriodPayAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '+',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.beginPeriodAdvAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '-',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.beginPeriodPreAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            xtype: 'component',
            style: 'margin-left:30px;margin-top:10px;',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            name: 'beginPeriodAmount',
            xtype: 'numberfield',
            columnWidth: .2,
            style: 'margin-left:30px;'
        }, {
            name: 'beginPeriodRecAmount',
            xtype: 'numberfield',
            columnWidth: .2
        }, {
            name: 'beginPeriodPayAmount',
            xtype: 'numberfield',
            columnWidth: .2
        }, {
            name: 'beginPeriodAdvAmount',
            xtype: 'numberfield',
            columnWidth: .2
        }, {
            name: 'beginPeriodPreAmount',
            xtype: 'numberfield',
            columnWidth: .2
        }]
    });

    /**
     * 本期账单的form
     */
    Ext.define('Foss.statementbill.CurrentBillsForm', {
        extend: 'Ext.form.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodCurrentBills"/>',
        layout: 'column',
        frame: true,
        defaultType: 'displayfield',
        defaults: {
            labelWidth: 5,
            readOnly: true
        },
        items: [{
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodAmount"/>',
            columnWidth: .15,
            style: 'margin-left:30px;margin-top:10px;'
        }, {
            value: '=',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodRecAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '-',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodPayAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '+',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodAdvAmount"/>',
            style: 'margin-top:10px;',
            columnWidth: .15
        }, {
            value: '-',
            style: 'margin-top:10px;',
            columnWidth: .05
        }, {
            value: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodPreAmount"/>',
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
            name: 'periodAdvAmount',
            columnWidth: .2,
            xtype: 'numberfield'
        }, {
            name: 'periodPreAmount',
            columnWidth: .2,
            xtype: 'numberfield'
        }, {
            xtype: 'label',
            height: 24,
            hidden: true,
            cls: 'statementBill_total',
            text: '  ',
            style: 'margin-left:30px;',
            columnWidth: .06
        }, {
            xtype: 'numberfield',
            name: 'periodUnverifyRecAmount',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.Ar"/>',
            labelWidth: 40,
            style: 'margin-top:5px;',
            hidden: true,
            columnWidth: .12
        }, {
            xtype: 'numberfield',
            name: 'periodUnverifyPayAmount',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.Ap"/>',
            labelWidth: 40,
            style: 'margin-top:5px;',
            hidden: true,
            columnWidth: .12
        }, {
            xtype: 'numberfield',
            name: 'periodUnverifyPreAmount',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.Pre"/>',
            style: 'margin-top:5px;',
            labelWidth: 40,
            hidden: true,
            columnWidth: .12
        }, {
            xtype: 'numberfield',
            name: 'periodUnverifyAdvAmount',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.Adv"/>',
            style: 'margin-top:5px;',
            labelWidth: 40,
            hidden: true,
            columnWidth: .12
        }]
    });

    /**
     * 对账单操作按钮区域
     */
    Ext.define('Foss.statementbill.OperateButtonPanel', {
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
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.confirm"/>',
            handler: writeoff.statementConfirm
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.unConfirm"/>',
            handler: writeoff.statementUnConfirm,
            hidden: true
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.repayment"/>',
            handler: writeoff.statementRepayment
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.verify"/>',
            handler: writeoff.statementWriteoff
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.printReceipt"/>',
            handler: function () {
                var baseInfo = writeoff.baseInfo;
                var statementBillId = baseInfo.getForm().findField('id').getValue();
                writeoff.statementConfReceipt(statementBillId);
            }
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.printStatement"/>',
            handler: writeoff.statementPrint
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.exportStatement"/>',
            handler: writeoff.statementExportPDF
        }, {
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementEdit.exportExcel"/>',
            hidden: !writeoff.isPermission('/stl-web/writeoff/exportStatementDetail.action'),
            handler: writeoff.statementExcel
        }]
    });

    /**
     * 对账单明细记录
     */
    Ext.define('Foss.statementbill.StatementEntryEditGrid', {
        extend: 'Ext.grid.Panel',
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.periodCurrentEntry"/>',
        frame: true,
        height: 600,
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        store: Ext.create('Foss.statementbill.StatementEntryEditStore'),
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
            width: 50,
            text: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.actionColumn"/>',
            align: 'center',
            items: [{
                iconCls: 'statementBill_remove',
                tooltip: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.delete"/>',
                getClass: function (v, m, r, rowIndex) {
                    if (!writeoff.isPermission('/stl-web/writeoff/deleteStatementEntry.action')) {
                        return 'statementBill_hide';
                    } else {
                        return 'statementBill_remove';
                    }
                },
                handler: function (grid, rowIndex, colIndex) {
                    writeoff.statementRemoveEntry(grid, rowIndex, colIndex);
                }
            }]
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>',
            dataIndex: 'businessDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.waybillNo"/>',
            dataIndex: 'waybillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.arrvRegionCode"/>',
            dataIndex: 'arrvRegionCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>',
            dataIndex: 'productCode',
            renderer: function (value) {
                return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerName"/>',
            dataIndex: 'deliveryCustomerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unitPrice"/>',
            dataIndex: 'unitPrice'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceAmount"/>',
            dataIndex: 'insuranceAmount'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.qty"/>',
            dataIndex: 'qty'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billWeight"/>',
            dataIndex: 'billWeight'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billingVolume"/>',
            dataIndex: 'billingVolume'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceFee"/>',
            dataIndex: 'insuranceFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliverFee"/>',
            dataIndex: 'deliverFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.packagingFee"/>',
            dataIndex: 'packagingFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.otherFee"/>',
            dataIndex: 'otherFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveMethod"/>',
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
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>',
            dataIndex: 'paymentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>',
            dataIndex: 'unverifyAmount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.totalAmount"/>',
            dataIndex: 'amount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>',
            dataIndex: 'sourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerContact"/>',
            dataIndex: 'deliveryCustomerContact'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.origSourceBillNo"/>',
            dataIndex: 'origSourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billParentType"/>',
            dataIndex: 'billParentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
            dataIndex: 'billType',
            renderer: function (v, m, record) {
                return writeoff.statementFormatBillType(v, record);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            dataIndex: 'customerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            dataIndex: 'customerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.verifyAmount"/>',
            dataIndex: 'verifyAmount',
            hidden: true,
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgCode"/>',
            dataIndex: 'orgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgName"/>',
            dataIndex: 'orgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.accountDate"/>',
            dataIndex: 'accountDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerPickupOrgName"/>',
            dataIndex: 'customerPickupOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerName"/>',
            dataIndex: 'receiveCustomerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerCode"/>',
            dataIndex: 'receiveCustomerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.goodsName"/>',
            dataIndex: 'goodsName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.transportFee"/>',
            dataIndex: 'transportFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.pickupFee"/>',
            dataIndex: 'pickupFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.codFee"/>',
            dataIndex: 'codFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.valueAddFee"/>',
            dataIndex: 'valueAddFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.promotionsFee"/>',
            dataIndex: 'promotionsFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgCode"/>',
            dataIndex: 'origOrgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgName"/>',
            dataIndex: 'origOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgName"/>',
            dataIndex: 'destOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgCode"/>',
            dataIndex: 'destOrgCode'
        }
            , {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerCode"/>',
                dataIndex: 'deliveryCustomerCode'
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.signDate"/>',
                dataIndex: 'signDate',
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.auditStatus"/>',
                dataIndex: 'auditStatus',
                renderer: function (value) {
                    var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                    return displayField;
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.notes"/>',
                dataIndex: 'notes',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.isDelete"/>',
                dataIndex: 'isDelete',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
                dataIndex: 'statementBillNo',
                hidden: true
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.disableTime"/>',
                dataIndex: 'disableTime',
                hidden: true,
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: '<ext:i18nForJsp key="foss.stl.writeoff.common.createTime"/>',
                dataIndex: 'createTime',
                hidden: true,
                renderer: function (value) {
                    if (value != null) {
                        return Ext.Date.format(new Date(value), 'Y-m-d');
                    } else {
                        return null;
                    }
                }
            }, {
                header: 'id',
                dataIndex: 'id',
                hidden: true
            }]
    });

    /**
     * 添加到对账单明细页面的查询tab
     */
    Ext.define('Foss.statementbill.QueryInfoTab', {
        extend: 'Ext.tab.Panel',
        bodyCls: 'autoHeight',
        cls: 'autoHeight',
        activeTab: 1,//默认显示按单号制作
        height: 200,
        items: [{
            title: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.makeByCustomer"/>',
            tabConfig: {
                width: 120
            },
            layout: 'fit',
            items: [{
                xtype: 'form',
                defaults: {
                    margin: '5,5,5,5 ',
                    labelWidth: 80
                },
                layout: 'column',
                items: [{
                    xtype: 'textfield',
                    readOnly: true,
                    name: 'statementBillNo',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
                    columnWidth: .25
                }, {
                    xtype: 'fieldcontainer',
                    defaultType: 'radiofield',
                    margin: '10 0 0 0',
                    columnWidth: .25,
                    layout: 'hbox',
                    items: [{
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.businessDate"/>',
                        checked: true,
                        name: 'selectDateFlag',
                        inputValue: 'BU',// 表单的参数值
                        width: 85
                    }, {
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.signDate"/>',
                        name: 'selectDateFlag',
                        inputValue: 'CO',// 表单的参数值
                        width: 85
                    }]
                }, {
                    xtype: 'datefield',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.accountPeriod"/>',
                    labelWidth: 60,
                    name: 'periodBeginDateEdit',
                    columnWidth: .2,
                    format: 'Y-m-d',
                    value: stl.getTargetDate(new Date(), -stl.DATELIMITDAYS_MONTH)
                }, {
                    xtype: 'datefield',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.to"/>',
                    labelWidth: 30,
                    name: 'periodEndDateEdit',
                    format: 'Y-m-d',
                    columnWidth: .2,
                    value: new Date()
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
                    allowBlank: true,
                    columnWidth: .4,
                    defaultType: 'checkboxfield',
                    defaults: {
                        width: 60
                    },
                    layout: 'hbox',
                    items: [{
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billReceivable"/>',
                        name: 'editBillType',
                        inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
                    }, {
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billpayable"/>',
                        name: 'editBillType',
                        inputValue: writeoff.STATEMENTDETAIL_PAYABLE
                    }, {
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billDepositReceive"/>',
                        name: 'editBillType',
                        inputValue: writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
                    }, {
                        boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billAdvance"/>',
                        name: 'editBillType',
                        inputValue: writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT
                    }]
                }, {
                    xtype: 'textfield',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
                    name: 'customerName',
                    columnWidth: .3,
                    readOnly: true
                }, {
                    xtype: 'textfield',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
                    name: 'customerCode',
                    columnWidth: .3,
                    readOnly: true
                }, {
                    border: 1,
                    xtype: 'container',
                    margin: '0 0 5 0',
                    columnWidth: 1,
                    defaultType: 'button',
                    layout: 'column',
                    items: [{
                        xtype: 'container',
                        border: false,
                        html: '&nbsp;',
                        columnWidth: .88
                    }, {
                        text: '<ext:i18nForJsp key="foss.stl.writeoff.common.query"/>',
                        cls: 'yellow_button',
                        columnWidth: .08,
                        handler: writeoff.statementQueryForAddByCustInfo
                    }]
                }]
            }]
        }, {
            title: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.makeByNumber"/>',
            tabConfig: {
                width: 120
            },
            layout: 'fit',
            items: [{
                xtype: 'form',
                defaults: {
                    margin: '5 5 5 5'
                },
                layout: 'column',
                items: [{
                    xtype: 'textareafield',
                    fieldLabel: '<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>',
                    columnWidth: .65,
                    labelWidth: 70,
                    labelAlign: 'right',
                    name: 'billDetailNos',
                    autoScroll: true,
                    height: 65
                }, {
                    xtype: 'hiddenfield',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
                    name: 'customerCode',
                    readOnly: true
                }, {
                    xtype: 'hiddenfield',
                    name: 'statementBillNo',
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
                    columnWidth: .4
                }, {
                    xtype: 'container',
                    columnWidth: .35,
                    layout: 'vbox',
                    items: [{
                        xtype: 'component',
                        padding: '0 0 0 10',
                        autoEl: {
                            tag: 'div',
                            html: '<span style="color:red;">' + '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.queryNosDesc"/>' + '</span>'
                        }
                    }]
                }, {
                    xtype: 'container',
                    columnWidth: 1,
                    layout: 'column',
                    margin: '0 0 5 0',
                    defaultType: 'button',
                    defaults: {
                        width: 80
                    },
                    items: [{
                        text: '<ext:i18nForJsp key="foss.stl.writeoff.common.reset"/>',
                        columnWidth: .075,
                        handler: writeoff.statementReset
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
                        allowBlank: true,
                        columnWidth: .5,
                        defaultType: 'checkboxfield',
                        defaults: {
                            width: 60
                        },
                        layout: 'hbox',
                        items: [{
                            boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billReceivable"/>',
                            checked: true,
                            name: 'editBillType',
                            inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
                        }, {
                            boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billpayable"/>',
                            name: 'editBillType',
                            checked: true,
                            inputValue: writeoff.STATEMENTDETAIL_PAYABLE
                        }, {
                            boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billDepositReceive"/>',
                            name: 'editBillType',
                            checked: true,
                            inputValue: writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
                        }, {
                            boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billAdvance"/>',
                            name: 'editBillType',
                            checked: true,
                            inputValue: writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT
                        }]
                    }, {
                        text: '<ext:i18nForJsp key="foss.stl.writeoff.common.query"/>',
                        cls: 'yellow_button',
                        columnWidth: .075,
                        handler: writeoff.statementQueryForAddByNumber
                    }]
                }]
            }]
        }]
    });

    /**
     * 查询条件统一丢到一个隐藏panel中，为后续使用
     */
    Ext.define('Foss.statementbill.QueryParamsHiddenfield', {
        extend: 'Ext.form.Panel',
        hidden: true,
        layout: 'column',
        defaults: {
            readOnly: true
        },
        items: [{
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.accountPeriod"/>',
            name: 'periodBeginDateParam',
            columnWidth: .3,
            format: 'Y-m-d'
        }, {
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.to"/>',
            labelWidth: 30,
            name: 'periodEndDateParam',
            format: 'Y-m-d',
            columnWidth: .24
        }, {
            xtype: 'fieldcontainer',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
            allowBlank: true,
            columnWidth: .4,
            defaultType: 'checkboxfield',
            defaults: {
                width: 60
            },
            layout: 'hbox',
            items: [{
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billReceivable"/>',
                name: 'billTypeParam',
                inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billpayable"/>',
                name: 'billTypeParam',
                inputValue: writeoff.STATEMENTDETAIL_PAYABLE
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billDepositReceive"/>',
                name: 'billTypeParam',
                inputValue: writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.billAdvance"/>',
                name: 'billTypeParam',
                inputValue: writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT
            }]
        }, {
            xtype: 'textfield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            name: 'customerCodeParam',
            columnWidth: .3,
            readOnly: true
        }, {
            xtype: 'textareafield',
            fieldLabel: '<span style="color:red;">*</span>' + '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>',
            columnWidth: .5,
            labelWidth: 70,
            labelAlign: 'right',
            name: 'billDetailNosParam',
            autoScroll: true,
            columnWidth: .3
        }, {
            xtype: 'textfield',
            fieldLabel: 'queryPage',
            columnWidth: .3,
            name: 'queryPage'
        }]
    });


    /**
     * 添加到对账单功能页面的grid
     */
    Ext.define('Foss.statementbill.AddStatementVerifyEntryGrid', {
        extend: 'Ext.grid.Panel',
        frame: true,
        height: 380,
        title: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementEntry"/>',
        store: Ext.create('Foss.statementbill.StatementEntryAddStore'),
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        viewConfig: {
            enableTextSelection: true//设置行可以选择，进而可以复制
        },
        columns: [{
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.number"/>',
            dataIndex: 'sourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.waybillNo"/>',
            dataIndex: 'waybillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.origSourceBillNo"/>',
            dataIndex: 'origSourceBillNo'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billParentType"/>',
            dataIndex: 'billParentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.billType"/>',
            dataIndex: 'billType',
            renderer: function (v, m, record) {
                return writeoff.statementFormatBillType(v, record);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            dataIndex: 'customerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            dataIndex: 'customerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.totalAmount"/>',
            dataIndex: 'amount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.verifyAmount"/>',
            dataIndex: 'verifyAmount',
            hidden: true,
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.originalUnverifyAmount"/>',
            dataIndex: 'unverifyAmount',
            renderer: function (v, m, record) {
                if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
                    return '<span style="color:red">' + v + '</span>';
                } else {
                    return v;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgCode"/>',
            dataIndex: 'orgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgName"/>',
            dataIndex: 'orgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.businessDate"/>',
            dataIndex: 'businessDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.accountDate"/>',
            dataIndex: 'accountDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>',
            dataIndex: 'paymentType',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.productCode"/>',
            dataIndex: 'productCode',
            renderer: function (value) {
                return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveMethod"/>',
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
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billWeight"/>',
            dataIndex: 'billWeight'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.billingVolume"/>',
            dataIndex: 'billingVolume'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.arrvRegionCode"/>',
            dataIndex: 'arrvRegionCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerPickupOrgName"/>',
            dataIndex: 'customerPickupOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerName"/>',
            dataIndex: 'receiveCustomerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.receiveCustomerCode"/>',
            dataIndex: 'receiveCustomerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.goodsName"/>',
            dataIndex: 'goodsName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.qty"/>',
            dataIndex: 'qty'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.transportFee"/>',
            dataIndex: 'transportFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliverFee"/>',
            dataIndex: 'deliverFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.pickupFee"/>',
            dataIndex: 'pickupFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.insuranceFee"/>',
            dataIndex: 'insuranceFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.packagingFee"/>',
            dataIndex: 'packagingFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.codFee"/>',
            dataIndex: 'codFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.valueAddFee"/>',
            dataIndex: 'valueAddFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.otherFee"/>',
            dataIndex: 'otherFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.promotionsFee"/>',
            dataIndex: 'promotionsFee'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgCode"/>',
            dataIndex: 'origOrgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.origOrgName"/>',
            dataIndex: 'origOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgName"/>',
            dataIndex: 'destOrgName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.destOrgCode"/>',
            dataIndex: 'destOrgCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerCode"/>',
            dataIndex: 'deliveryCustomerCode'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.deliveryCustomerName"/>',
            dataIndex: 'deliveryCustomerName'
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.signDate"/>',
            dataIndex: 'signDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.auditStatus"/>',
            dataIndex: 'auditStatus',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                return displayField;
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.notes"/>',
            dataIndex: 'notes',
            hidden: true
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.isDelete"/>',
            dataIndex: 'isDelete',
            hidden: true
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
            dataIndex: 'statementBillNo',
            hidden: true
        }, {
            header: 'disableTime',
            dataIndex: 'disableTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: '<ext:i18nForJsp key="foss.stl.writeoff.common.createTime"/>',
            dataIndex: 'createTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: 'id',
            dataIndex: 'id',
            hidden: true
        }],
        initComponent: function () {
            var me = this;
            me.dockedItems = [{
                xtype: 'toolbar',
                dock: 'bottom',
                layout: 'column',
                defaults: {
                    margin: '0 0 5 3'
                },
                items: [{
                    xtype: 'textfield',
                    readOnly: true,
                    name: 'totalRows',
                    columnWidth: .3,
                    labelWidth: 60,
                    fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.totalCount"/>'
                }, {
                    xtype: 'container',
                    border: false,
                    html: '&nbsp;',
                    columnWidth: .85
                }, {
                    xtype: 'button',
                    text: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.addToStatement"/>',
                    columnWidth: .15,
                    hidden: !writeoff.isPermission('/stl-web/writeoff/addStatementDetail.action'),//添加对账单
                    handler: writeoff.statementAddEntryAdd
                }]
            }];

            //添加beforeLoad事件
            me.store.on('beforeLoad', function (store, operation, eOpts) {
                //获取隐藏组件值
                var queryForm = me.up('window').items.items[1].getForm();
                if (queryForm.findField('queryPage').getValue() == '') {
                    Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>', '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.queryTypeIsNullWarning"/>');
                    return false;
                }
                //查询条件
                var searchParams;

                //按客户查询
                if (queryForm.findField('queryPage').getValue() == 'TD') {
                    var customerCode,
                            periodBeginDateEdit,
                            periodEndDateEdit,
                            editBillType,
                            queryPage;

                    customerCode = queryForm.findField('customerCodeParam').getValue();
                    periodBeginDate = queryForm.findField('periodBeginDateParam').getValue();
                    periodEndDate = queryForm.findField('periodEndDateParam').getValue();
                    editBillType = queryForm.getValues().billTypeParam;
                    queryPage = queryForm.findField('queryPage').getValue();

                    searchParams = {
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode': customerCode,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodBeginDate': periodBeginDate,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodEndDate': periodEndDate,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailTypes': editBillType,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.dateType': writeoff.selectDateFlag,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.receivableUnified': writeoff.unifiedSettlement,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': queryPage
                    };
                    //按单号查询	
                } else {
                    var customerCode,
                            billDetailNoArray,
                            billDetailNos,
                            queryPage;

                    billDetailNoArray = [];
                    billDetailNos = queryForm.findField('billDetailNosParam').getValue();
                    customerCode = queryForm.findField('customerCodeParam').getValue();
                    queryPage = queryForm.findField('queryPage').getValue();

                    var array = stl.splitToArray(billDetailNos);
                    for (var i = 0; i < array.length; i++) {
                        if (Ext.String.trim(array[i]) != '') {
                            billDetailNoArray.push(array[i]);
                        }
                    }

                    searchParams = {
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos': billDetailNoArray,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode': customerCode,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': queryPage,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.receivableUnified': writeoff.unifiedSettlement,
                        'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailTypes': writeoff.billDetailTypes
                    };
                }

                Ext.apply(operation, {
                    params: searchParams
                });
            });

            me.store.on('load', function (th, records, success, o) {
                if (success == true) {
                    writeoff.addStatementVerifyEntryGrid.down('toolbar').query('textfield')[0].setValue(records.length);
                }
            });
            me.callParent();
        }
    });

    //声明查询tab
    if (Ext.isEmpty(writeoff.queryInfoTab)) {
        writeoff.queryInfoTab = Ext.create('Foss.statementbill.QueryInfoTab');
    }

    //添加对账单明细 --本期数据
    if (Ext.isEmpty(writeoff.addCurrentBillsForm)) {
        writeoff.addCurrentBillsForm = Ext.create('Foss.statementbill.CurrentBillsForm', {hidden: true});
    }
    //添加对账单明细 --表格
    if (Ext.isEmpty(writeoff.addStatementVerifyEntryGrid)) {
        writeoff.addStatementVerifyEntryGrid = Ext.create('Foss.statementbill.AddStatementVerifyEntryGrid', {hidden: true});
    }
    /**
     * 添加对账单明细弹出页面
     */
    Ext.define('Foss.statementbill.AddStatementEntryWindow', {
        extend: 'Ext.window.Window',
        width: stl.SCREENWIDTH * 0.7,
        constrainHeader: true,
        modal: true,
        closeAction: 'destory',
        listeners: {
            'close': function () {
                //查询后显示隐藏组件
                writeoff.addCurrentBillsForm.hide();
                writeoff.addStatementVerifyEntryGrid.hide();
            }
        },
        items: [
            writeoff.queryInfoTab,
            Ext.create('Foss.statementbill.QueryParamsHiddenfield'),
            writeoff.addCurrentBillsForm,
            writeoff.addStatementVerifyEntryGrid
        ]
    });
    if (Ext.isEmpty(writeoff.AddStatementEntryWindow)) {
        writeoff.AddStatementEntryWindow = Ext.create('Foss.statementbill.AddStatementEntryWindow');
    }

    /**************************************qiaolifeng 对账单回执**********************************************×××/

     /**
     * 对账单回执form表单model
     */
    Ext.define('Foss.statementbill.StatementConfReceiptFormModel', {
        extend: 'Ext.data.Model',
        fields: [{
            //确认回执编码
            name: 'conReceiptNo'
        }, {
            //对账单号
            name: 'statementBillNo'
        }, {
            //部门编码
            name: 'orgCode'
        }, {
            //部门名称
            name: 'orgName'
        }, {
            //客户编码
            name: 'customerCode'
        }, {
            //客户名称
            name: 'customerName'
        }, {
            //实收金额
            name: 'receivedAmount',
            type: 'double'
        }, {
            //还款方式
            name: 'paymentType'
        }, {
            //还款时间
            name: 'paymentDate',
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
            //收款人名称
            name: 'receiveEmpName'
        }, {
            //创建人名称
            name: 'createUserName'
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
            //客户意见
            name: 'customerIdea'
        }, {
            //对账单ID
            name: 'id'
        }]
    });

    /*
     * 显示查询的最新对账单回执Form
     * */
    Ext.define('Foss.statementbill.ConfReceiptStatementQueryForm', {
        extend: 'Ext.form.Panel',
        layout: 'column',
        width: stl.SCREENWIDTH * 0.42,
        frame: true,
        defaultType: 'textfield',
        layout: 'column',
        defaults: {
            margin: '10 0 0 0',
            readOnly: true
        },
        items: [{
            style: 'font-weight:bold',
            xtype: 'container',
            border: false,
            columnWidth: .4,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementReceiptEntry"/>',
            labelSeparator: '',
            columnWidth: .25
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .15,
            html: '&nbsp;'
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
            name: 'statementBillNo',
            height: 24,
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .5,
            height: 26,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgCode"/>',
            name: 'orgCode',
            height: 24,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.orgName"/>',
            name: 'orgName',
            height: 24,
            columnWidth: .5
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerCode"/>',
            name: 'customerCode',
            height: 24,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.customerName"/>',
            name: 'customerName',
            height: 24,
            columnWidth: .5
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.receivedAmount"/>',
            name: 'receivedAmount',
            height: 24,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.repaymentType"/>',
            name: 'paymentType',
            height: 24,
            columnWidth: .5
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.paymentDate"/>',
            name: 'paymentDate',
            format: 'Y-m-d',
            height: 24,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.receiveEmpName"/>',
            name: 'receiveEmpName',
            height: 24,
            columnWidth: .5
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.createUserName"/>',
            name: 'createUserName',
            height: 24,
            columnWidth: .4
        }, {
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.common.createTime"/>',
            name: 'createTime',
            format: 'Y-m-d H:i:s',
            height: 24,
            columnWidth: .5
        }, {
            xtype: 'container',
            border: false,
            columnWidth: 1,
            height: 1,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .1,
            height: 26,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerIdea"/>',
            name: 'customerIdea',
            height: 24,
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .5,
            height: 26,
            html: '&nbsp;'

        }]
    });

    /*
     * 对账单回执Form_top
     * */
    Ext.define('Foss.statementbill.ConfReceiptStatementTopForm', {
        extend: 'Ext.form.Panel',
        layout: 'column',
        width: stl.SCREENWIDTH * 0.61,
        frame: false,
        defaultType: 'textfield',
        layout: 'column',
        defaults: {
            margin: '0 0 0 0',
            readOnly: true
        },
        items: [{
            style: 'font-weight:bold',
            xtype: 'container',
            border: false,
            columnWidth: .45,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.statementReceiptComfirm"/>',
            labelSeparator: '',
            columnWidth: .15
        }, {
            style: 'font-size:20px',
            xtype: 'container',
            border: false,
            columnWidth: .4,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .75,
            height: 20,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.statementBillNo"/>',
            name: 'statementBillNo',
            height: 20,
            columnWidth: .25
        }, {
            fieldLabel: 'id',
            name: 'id',
            columnWidth: .22,
            hidden: true
        }]
    });

    /*
     * 对账单回执Form
     * */
    Ext.define('Foss.statementbill.ConfReceiptStatementForm', {
        extend: 'Ext.form.Panel',
        layout: 'column',
        width: stl.SCREENWIDTH * 0.61,
        height: stl.SCREENHEIGHT * 0.88,
        frame: true,
        defaultType: 'textfield',
        layout: 'column',
        defaults: {
            margin: '10 0 0 0',
            readOnly: true
        },
        items: [{
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerName"/>',
            name: 'customerName',
            labelWidth: 95,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.customerCode"/>',
            name: 'customerCode',
            labelWidth: 95,
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.createOrgName"/>',
            name: 'createOrgName',
            labelWidth: 95,
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.createUserName"/>',
            name: 'createUserName',
            labelWidth: 95,
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.periodBeginDate"/>',
            name: 'periodBeginDate',
            format: 'Y-m-d',
            columnWidth: .26
        }, {
            xtype: 'datefield',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.to"/>',
            name: 'periodEndDate',
            format: 'Y-m-d',
            columnWidth: .24
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .3,
            height: 24,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.paidAmount"/>',
            name: 'paidAmount',
            columnWidth: .4
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.unpaidAmount"/>',
            name: 'unpaidAmount',
            columnWidth: .4
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.unifiedCode"/>',
            name: 'unifiedCode',
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.companyAccountBankNo"/>',
            name: 'companyAccountBankNo',
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.accountUserName"/>',
            name: 'accountUserName',
            columnWidth: .7
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.bankBranchName"/>',
            name: 'bankBranchName',
            columnWidth: .7
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.costDetail"/>',
            name: 'costDetail',
            columnWidth: .8
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.periodRecAmount"/>',
            name: 'periodRecAmount',
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.periodPayAmount"/>',
            name: 'periodPayAmount',
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.periodPreAmount"/>',
            name: 'periodPreAmount',
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.periodAdvAmount"/>',
            name: 'periodAdvAmount',
            columnWidth: .4
        }, {
            style: 'font-weight:bold;background-color:white;',
            xtype: 'container',
            fieldStyle: 'color:white;',
            border: false,
            height: 30,
            columnWidth: .6,
            html: '&nbsp;'
        }, {
            style: 'font-weight:bold;background-color:white;color:black;',
            fieldStyle: 'font-weight:bold;color:black;',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.total"/>',
            name: 'periodAmount',
            height: 28,
            columnWidth: .4
        }, {
            xtype: 'component',
            border: true,
            autoEl: {
                tag: 'hr'
            },
            columnWidth: 1
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            xtype: 'fieldcontainer',
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.paymentType"/>',
            allowBlank: true,
            columnWidth: .8,
            height: 24,
            defaultType: 'checkboxfield',
            layout: 'hbox',
            readOnly: true,
            items: [{
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.cash"/>',
                name: 'payType',
                readOnly: true,
                columnWidth: .15
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.ticket"/>',
                name: 'payType',
                readOnly: true,
                columnWidth: .15
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.bankcard"/>',
                name: 'payType',
                readOnly: true,
                columnWidth: .15
            }, {
                boxLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.check"/>',
                name: 'payType',
                readOnly: true,
                columnWidth: .15
            }]
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerSign"/>',
            name: 'customerSign',
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.receivedAmount"/>',
            name: 'actualRecAmount',
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.payeeSign"/>',
            name: 'receiverSign',
            columnWidth: .4
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.receivedDate"/>',
            name: 'receivedDate',
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .19,
            height: 24,
            html: '&nbsp;'
        }, {
            fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.customerIdea"/>',
            name: 'customerOpinion',
            columnWidth: .4
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .4,
            height: 24,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .99,
            height: 24,
            html: '&nbsp;'
        }, {
            xtype: 'container',
            border: false,
            columnWidth: .99,
            height: 24,
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
                columnWidth: .2,
                height: 24,
                html: '&nbsp;'
            }, {
                text: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.watchNewReceipt"/>',
                cls: 'yellow_button',
                columnWidth: .25,
                height: 24,
                handler: writeoff.queryNewestStatementReceipt
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .1,
                height: 24,
                html: '&nbsp;'
            }, {
                text: '<ext:i18nForJsp key="foss.stl.writeoff.common.print"/>',
                cls: 'yellow_button',
                columnWidth: .25,
                height: 24,
                handler: writeoff.printStatementReceipt
            }, {
                xtype: 'container',
                border: false,
                columnWidth: .2,
                height: 24,
                html: '&nbsp;'
            }]
        }]
    });

    if (Ext.isEmpty(writeoff.confReceiptStatementTopForm)) {
        writeoff.confReceiptStatementTopForm = Ext.create('Foss.statementbill.ConfReceiptStatementTopForm');
    }
    /**
     * 声明对账单回执界面
     */
    Ext.define('Foss.statementbill.ConfReceiptStatementEntryWindow', {
        extend: 'Ext.window.Window',
        width: stl.SCREENWIDTH * 0.63,
        height: stl.SCREENHEIGHT * 1.22,
        modal: true,
        constrainHeader: true,
        closeAction: 'destory',
        items: [
            writeoff.confReceiptStatementTopForm,
            Ext.create('Foss.statementbill.ConfReceiptStatementForm')
        ]
    });

    /**
     * 声明显示最新打印的对账单回执界面
     */
    Ext.define('Foss.statementbill.ConfReceiptStatementEntryQueryWindow', {
        extend: 'Ext.window.Window',
        width: stl.SCREENWIDTH * 0.445,
        height: stl.SCREENHEIGHT * 0.57,
        modal: true,
        constrainHeader: true,
        closeAction: 'destory',
        items: [Ext.create('Foss.statementbill.ConfReceiptStatementQueryForm')]
    });

    /**************************************qiaolifeng 对账单回执**********************************************×××/


     /**
     *
     */
    Ext.onReady(function () {
        Ext.Ajax.timeout = 180000;
        //对账单修改界面的基本信息panel
        if (Ext.isEmpty(writeoff.baseInfo)) {
            writeoff.baseInfo = Ext.create('Foss.statementbill.BaseInfo');
        }
        //对账单修改界面的期初信息panel
        if (Ext.isEmpty(writeoff.beginBillsForm)) {
            writeoff.beginBillsForm = Ext.create('Foss.statementbill.BeginBillsForm');
        }
        //对账单修改界面的本期信息panel
        if (Ext.isEmpty(writeoff.currentBillsForm)) {
            writeoff.currentBillsForm = Ext.create('Foss.statementbill.CurrentBillsForm');
        }
        //对账单修改界面表格上面的操作按钮panel
        if (Ext.isEmpty(writeoff.operateButtonPanel)) {
            writeoff.operateButtonPanel = Ext.create('Foss.statementbill.OperateButtonPanel');
        }

        //对账单修改界面的本期表格信息
        if (Ext.isEmpty(writeoff.statementEntryEditGrid)) {
            writeoff.statementEntryEditGrid =
                    Ext.create('Foss.statementbill.StatementEntryEditGrid', {
                        //批量删除
                        deleteBatch: writeoff.statementRemoveEntryBatch,
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
                                text: '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.addStatementEntry"/>',
                                columnWidth: .1,
                                handler: function () {
                                    var baseInfo = writeoff.baseInfo;
                                    var beginBillsForm = writeoff.beginBillsForm;
                                    var currentBillsForm = writeoff.currentBillsForm;
                                    //如果对账单为已确认，则弹出提示
                                    if (baseInfo.getRecord().get('confirmStatus') == writeoff.STATEMENTCONFIRMSTATUS_Y) {
                                        Ext.Msg.alert('<ext:i18nForJsp key="foss.stl.writeoff.common.alert"/>',
                                                '<ext:i18nForJsp key="foss.stl.writeoff.statementCommon.auditToAddStatementEntryWarning"/>');
                                        return false;
                                    }
                                    var queryByCustomerForm = writeoff.queryInfoTab.items.items[0].down('form');
                                    var queryByNumberForm = writeoff.queryInfoTab.items.items[1].down('form');
                                    if ('LA' != baseInfo.getRecord().get('billType')) {
                                        queryByCustomerForm.items.items[1].hide();
                                    }
                                    if ('CA' == baseInfo.getRecord().get('billType')) {
                                        writeoff.unifiedSettlement = baseInfo.getForm().findField('unifiedSettlement').getValue();
                                    }
                                    //将对账单数据带给查询界面
                                    queryByCustomerForm.loadRecord(baseInfo.getRecord());
                                    queryByNumberForm.loadRecord(baseInfo.getRecord());
                                    //此处平台控件不能load数据进来，故而手动去
                                    writeoff.AddStatementEntryWindow.items.items[1].loadRecord(beginBillsForm.getRecord());
                                    writeoff.AddStatementEntryWindow.items.items[2].loadRecord(currentBillsForm.getRecord());
                                    writeoff.AddStatementEntryWindow.show();
                                }
                            }]
                        }, {
                            xtype: 'toolbar',
                            dock: 'bottom',
                            layout: 'column',
                            items: [{
                                xtype: 'button',
                                hidden: !writeoff.isPermission('/stl-web/writeoff/deleteStatementEntry.action'),
                                text: 'X ' + '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.delete"/>',
                                columnWidth: .06,
                                handler: function () {
                                    var grid = writeoff.statementEntryEditGrid;
                                    grid.deleteBatch();
                                }
                            }, {
                                xtype: 'textfield',
                                readOnly: true,
                                name: 'totalRows',
                                columnWidth: 1,
                                labelWidth: 60,
                                fieldLabel: '<ext:i18nForJsp key="foss.stl.writeoff.statementAdd.totalCount"/>'
                            }]
                        }]
                    });
        }
        //对账单修改界面表格下面操作按钮区域		
        if (Ext.isEmpty(writeoff.operateButtonPanelDown)) {
            writeoff.operateButtonPanelDown = Ext.create('Foss.statementbill.OperateButtonPanel');
        }
        /**
         * 声明对账单修改界面
         */
        Ext.define('Foss.statementbill.EditStatementEntryWindow', {
            extend: 'Ext.window.Window',
            getBaseInfo: function () {
                return baseInfo;
            },
            width: stl.SCREENWIDTH * 0.9,
            modal: true,
            constrainHeader: true,
            closeAction: 'destory',
            items: [writeoff.baseInfo,
                writeoff.beginBillsForm,
                writeoff.currentBillsForm,
                writeoff.operateButtonPanel,
                writeoff.statementEntryEditGrid,
                writeoff.operateButtonPanelDown
            ]
        });
    });
</script>
