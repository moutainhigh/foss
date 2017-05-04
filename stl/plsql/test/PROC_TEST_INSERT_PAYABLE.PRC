CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_PAYABLE(PAYABLE_NO       VARCHAR2, --应付单号
                                                     WAYBILL_NO       VARCHAR2, --运单号
                                                     WAYBILL_ID       VARCHAR2, --运单id
                                                     SOURCE_BILL_NO   VARCHAR2, --来源单据编号
                                                     BILL_TYPE        VARCHAR2, --单据类型
                                                     SOURCE_BILL_TYPE VARCHAR2, --来源单据类型
                                                     ACCOUNT_DATE     IN DATE, --记账日期
                                                     BUSINESS_DATE    DATE, --业务日期
                                                     SIGN_DATE        DATE, --签收日期
                                                     AMOUNT           NUMBER, --金额
                                                     ACTIVE           CHAR, --单据状态
                                                     IS_RED_BACK      CHAR, --是否红单
                                                     CUSTOMER_CODE    VARCHAR2, --客户编码
                                                     CUSTOMER_NAME    VARCHAR2, --客户名称
                                                     ORIG_ORG_CODE    VARCHAR2, --始发部门编码
                                                     ORIG_ORG_NAME    VARCHAR2, --始发部门名称
                                                     DEST_ORG_CODE    VARCHAR2, --到达部门编码
                                                     DEST_ORG_NAME    VARCHAR2, --到达部门名称
                                                     PAYABLE_ORG_CODE  VARCHAR2, --应付部门编码
                                                     PAYABLE_ORG_NAME  VARCHAR2,  --应付部门名称
                                                     PAYMENT_NO VARCHAR2 default '', --默认为空
                                                     PAYMENT_AMOUNT number default null,--付款金额
                                                     PRODUCT_CODE varchar2 default '10'
                                                     ) IS
BEGIN

  --插入应付单数据
  INSERT INTO STL.T_STL_BILL_PAYABLE
    (ID,
     PAYABLE_NO,
     WAYBILL_NO,
     WAYBILL_ID,
     PAYMENT_NO,
     CREATE_TYPE,
     BILL_TYPE,
     SOURCE_BILL_NO,
     SOURCE_BILL_TYPE,
     PRODUCT_CODE,
     PAYABLE_ORG_CODE,
     PAYABLE_ORG_NAME,
     PAYABLE_COM_CODE,
     PAYABLE_COM_NAME,
     ORIG_ORG_CODE,
     ORIG_ORG_NAME,
     DEST_ORG_CODE,
     DEST_ORG_NAME,
     CUSTOMER_CODE,
     CUSTOMER_NAME,
     AMOUNT,
     VERIFY_AMOUNT,
     UNVERIFY_AMOUNT,
     CURRENCY_CODE,
     PRODUCT_ID,
     ACCOUNT_DATE,
     BUSINESS_DATE,
     SIGN_DATE,
     EFFECTIVE_DATE,
     CREATE_USER_NAME,
     CREATE_USER_CODE,
     CREATE_ORG_CODE,
     CREATE_ORG_NAME,
     ACTIVE,
     IS_RED_BACK,
     IS_INIT,
     VERSION_NO,
     EFFECTIVE_STATUS,
     EFFECTIVE_USER_NAME,
     EFFECTIVE_USER_CODE,
     FROZEN_STATUS,
     FROZEN_TIME,
     FROZEN_USER_NAME,
     FROZEN_USER_CODE,
     PAY_STATUS,
     PAYMENT_STATUS,
     STATEMENT_BILL_NO,
     CUSTOMER_CONTACT,
     CUSTOMER_CONTACT_NAME,
     CUSTOMER_PHONE,
     CREATE_TIME,
     MODIFY_TIME,
     MODIFY_USER_NAME,
     MODIFY_USER_CODE,
     OTHERSIDE_ORG_CODE,
     OTHERSIDE_ORG_NAME,
     WORKFLOW_NO,
     LGDRIVER_CODE,
     LGDRIVER_NAME,
     PAYER_TYPE,
     PAYABLE_TYPE,
     DELIVER_FEE,
     OUTGOING_FEE,
     AUDIT_USER_CODE,
     AUDIT_USER_NAME,
     AUDIT_DATE,
     APPROVE_STATUS,
     IS_DISABLE,
     DISABLE_USER_CODE,
     DISABLE_USER_NAME,
     DISABLE_TIME,
     NOTES,
     COD_TYPE,
     PAYEE_NAME,
     BANK_HQ_CODE,
     BANK_HQ_NAME,
     ACCOUNT_NO,
     PROVINCE_CODE,
     PROVINCE_NAME,
     CITY_CODE,
     CITY_NAME,
     BANK_BRANCH_CODE,
     BANK_BRANCH_NAME,
     PAYMENT_AMOUNT,
     PAYMENT_NOTES)
  VALUES
    (SYS_GUID(),
     PAYABLE_NO, --'YF7' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --应付单号
     WAYBILL_NO, --'YDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --运单号
     WAYBILL_ID, --'YDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --运单id
     PAYMENT_NO,
     'A',
     BILL_TYPE, --单据类型
     SOURCE_BILL_NO, --'WFDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --前缀为WFD来源单据编号
     SOURCE_BILL_TYPE, --来源单据类型  偏线外发单
     PRODUCT_CODE, --产品类型 汽运偏线
     PAYABLE_ORG_CODE, --应付部门编码
     PAYABLE_ORG_NAME, --应付部门名称
     'TODO',
     'TODO1',
     ORIG_ORG_CODE, --始发部门编码
     ORIG_ORG_NAME, --始发部门名称
     DEST_ORG_CODE, --偏线外发部门编码
     DEST_ORG_NAME, --外发部门名称
     CUSTOMER_CODE, --客户编码/代理编码
     CUSTOMER_NAME, --客户名称/代理名称
     AMOUNT, --金额
     0, --已核销金额
     AMOUNT, --未核销金额
     'RMB',
     '',
     ACCOUNT_DATE, -- TO_DATE('11-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), --记账日期
     BUSINESS_DATE, -- TO_DATE('11-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), ---业务日期
     SIGN_DATE, -- TO_DATE('12-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), --签收日期
     NULL, --生效日期
     '张三',
     '000123',
     '1',
     '德邦物流',
     ACTIVE,
     IS_RED_BACK,
     'N',
     1,
     'N',
     '',
     '',
     'N',
     NULL,
     '',
     '',
     'N',
     'NP',
     'N/A',
     '',
     '',
     '',
     SYSDATE,
     SYSDATE,
     '张三',
     '000123',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     100000,
     100000,
     '',
     '',
     NULL,
     '',
     '',
     '',
     'N',
     NULL,
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     PAYMENT_AMOUNT,
     '');
  COMMIT;
END PROC_TEST_INSERT_PAYABLE;
/
