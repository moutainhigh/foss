CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_PAYMENT(
																											PAYMENT_NO       VARCHAR2,--付款单号
																											AMOUNT           NUMBER,--金额
																											CUSTOMER_CODE    VARCHAR2,--客户代理编码
																											CUSTOMER_NAME    VARCHAR2,--客户代理名称
																											SOURCE_BILL_NO   VARCHAR2,--来源单号
																											SOURCE_BILL_TYPE VARCHAR2,--来源单据类型
																											PAYMENT_ORG_CODE VARCHAR2,--付款部门编码
																											PAYMENT_ORG_NAME VARCHAR2,--付款部门名称
																											BUSINESS_DATE    DATE,--业务日期
																											ACCOUNT_DATE     DATE,--结账日期
                                                      ACTIVE char,--是否有效
                                                      IS_RED_BACK char,--是否红单
                                                      PAYMENT_TYPE char,--付款方式
                                                      BILL_TYPE VARCHAR2--单据类型
                                                      ) IS 
BEGIN

	INSERT INTO STL.T_STL_BILL_PAYMENT
		(ID,
		 PAYMENT_NO,
		 CURRENCY_CODE,
		 AMOUNT,
		 CUSTOMER_CODE,
		 CUSTOMER_NAME,
		 WORKFLOW_NO,
		 REMIT_STATUS,
		 AUDIT_STATUS,
		 SOURCE_BILL_NO,
		 SOURCE_BILL_TYPE,
		 PAYMENT_ORG_CODE,
		 PAYMENT_ORG_NAME,
		 PAYMENT_COMPANY_CODE,
		 PAYMENT_COMPANY_NAME,
		 ACTIVE,
		 IS_RED_BACK,
		 CREATE_USER_CODE,
		 CREATE_USER_NAME,
		 AUDIT_USER_CODE,
		 AUDIT_USER_NAME,
		 MODIFY_USER_CODE,
		 MODIFY_USER_NAME,
		 DISABLE_USER_CODE,
		 DISABLE_USER_NAME,
		 BUSINESS_DATE,
		 ACCOUNT_DATE,
		 CREATE_TIME,
		 MODIFY_TIME,
		 DISABLE_TIME,
		 BILL_TYPE,
		 IS_INIT,
		 PAYMENT_TYPE,
		 MOBILE_PHONE,
		 ACCOUNT_NO,
		 PAYEE_NAME,
		 ACCOUNT_TYPE,
		 PROVINCE_CODE,
		 CITY_CODE,
		 BANK_HQ_NAME,
		 BANK_BRANCH_NAME,
		 BANK_BRANCH_CODE,
		 AUDIT_OPINION,
		 DISABLE_OPINION,
		 APPLY_WORK_FLOW_NO,
		 NOTES,
		 VERSION_NO,
		 BANK_HQ_CODE,
		 PROVINCE_NAME,
		 CITY_NAME,
		 CREATE_TYPE,
		 BATCH_NO)
	VALUES
		(SYS_GUID(), --ID
		 PAYMENT_NO, --付款单号
		 'RMB',
		 AMOUNT, --金额
		 CUSTOMER_CODE, --客户/代理编码
		 CUSTOMER_NAME, --客户/代理名称
		 '',
		 'NT',
		 'NA',
		 SOURCE_BILL_NO, --来源单据号
		 SOURCE_BILL_TYPE, --来源单据类型
		 PAYMENT_ORG_CODE, --付款部门编码
		 PAYMENT_ORG_NAME, --付款部门名称
		 'TODO',
		 'TODO',
		 ACTIVE,--是否有效
		 IS_RED_BACK,--是否红单
		 '000000',
		 '超级管理员',
		 '',
		 '',
		 '000000',
		 '超级管理员',
		 '',
		 '',
		 BUSINESS_DATE, --业务日期
		 ACCOUNT_DATE, --记账日期
		 SYSDATE,
		 SYSDATE,
		 NULL,
		 BILL_TYPE,--单据类型
		 'N',--是否初始化
		 payment_type,--付款方式
		 '',
		 '',
		 '',
		 'C',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 1,
		 '',
		 '',
		 '',
		 '',
		 '');
	COMMIT;

END PROC_TEST_INSERT_PAYMENT;
/

