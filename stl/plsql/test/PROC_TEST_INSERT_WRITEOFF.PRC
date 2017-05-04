CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_WRITEOFF(WRITEOFF_BATCH_NO  VARCHAR2, --核销批次号
																											WRITEOFF_BILL_NO   IN VARCHAR2, --核销单号
																											AMOUNT             NUMBER, --核销金额
																											WRITEOFF_TYPE      VARCHAR2, --核销类型
																											BEGIN_WAYBILL_NO   VARCHAR2, --开始来源运单号
                                                      BEGIN_NO           VARCHAR2, --开始单号
																											BEGIN_ACCOUNT_DATE DATE, --开始记账日期
                                                      END_WAYBILL_NO     VARCHAR2, --结束来源运单号
																											END_NO             VARCHAR2, --结束单号
																											END_ACCOUNT_DATE   DATE, --结束记账日期
																											ACTIVE             CHAR, --是否有效
																											IS_RED_BACK        CHAR, --是否红单
																											WRITEOFF_TIME      DATE, --核销时间
																											ACCOUNT_DATE       DATE, --记账日期
																											CUSTOMER_CODE      VARCHAR2, --客户编码
																											CUSTOMER_NAME      VARCHAR2, --客户名称
																											ORG_CODE           VARCHAR2, --部门编码
																											ORG_NAME           VARCHAR2, --部门名称
																											VERSION_NO         NUMBER) IS
BEGIN
	INSERT INTO STL.T_STL_BILL_WRITEOFF
		(ID,
		 WRITEOFF_BATCH_NO,
		 WRITEOFF_BILL_NO,
		 BEGIN_WAYBILL_NO,
		 BEGIN_NO,
		 BEGIN_ACCOUNT_DATE,
		 END_WAYBILL_NO,
		 END_NO,
		 END_ACCOUNT_DATE,
		 STATEMENT_BILL_NO,
		 CREATE_TYPE,
		 WRITEOFF_TYPE,
		 ORG_CODE,
		 ORG_NAME,
		 CUSTOMER_CODE,
		 CUSTOMER_NAME,
		 AMOUNT,
		 ACTIVE,
		 IS_RED_BACK,
		 CREATE_USER_CODE,
		 CREATE_USER_NAME,
		 RED_IMPACT_USER_CODE,
		 RED_IMPACT_USER_NAME,
		 WRITEOFF_TIME,
		 ACCOUNT_DATE,
		 IS_INIT,
		 VERSION_NO)
	VALUES
		(SYS_GUID(), --ID
		 WRITEOFF_BATCH_NO, --核销批次号
		 WRITEOFF_BILL_NO, --核销单号
		 BEGIN_WAYBILL_NO, --开始来源运单号
		 BEGIN_NO, --应收单号
		 BEGIN_ACCOUNT_DATE, --开始结账日期 
		 END_WAYBILL_NO, --结束运单号
		 END_NO, --结束来源单号
		 END_ACCOUNT_DATE, --结束来源运单号
		 '',
		 'M', --创建方式
		 WRITEOFF_TYPE, --核销类型
		 ORG_CODE, --部门编码
		 ORG_NAME, --部门名称
		 CUSTOMER_CODE, --客户名称
		 CUSTOMER_NAME, --客户编码
		 AMOUNT, --金额
		 active, --是否有效
		 IS_RED_BACK, --是否红单 
		 '000000',
		 '客户1',
		 '',
		 '',
		 WRITEOFF_TIME, --核销时间
		 ACCOUNT_DATE, --记账时间
		 'N',
		 VERSION_NO --版本号
		 );
	COMMIT;

END PROC_TEST_INSERT_WRITEOFF;
/

