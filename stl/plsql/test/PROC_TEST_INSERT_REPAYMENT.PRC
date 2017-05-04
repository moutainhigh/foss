CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_REPAYMENT(REPAYMENT_NO    VARCHAR2,
																											 CUSTOMER_CODE   VARCHAR2,
																											 RECEIVABLE_CODE VARCHAR2,
																											 AMOUNT          NUMBER,
																											 PAYMENT_TYPE    VARCHAR2,
																											 ACTIVE          CHAR,
																											 IS_RED_BACK     CHAR,
																											 BUSINESS_DATE   DATE,
																											 ACCOUNT_DATE    DATE) IS
BEGIN
	--生成现金还款单
	INSERT INTO STL.T_STL_BILL_REPAYMENT
		(ID,
		 REPAYMENT_NO,
		 CUSTOMER_CODE,
		 COLLECTION_ORG_CODE,
		 AMOUNT,
		 TRUE_AMOUNT,
		 BVERIFY_AMOUNT,
		 CREATE_TYPE,
		 PAYMENT_TYPE,
		 ACTIVE,
		 IS_RED_BACK,
		 ACCOUNT_DATE,
		 BUSINESS_DATE,
		 CREATE_TIME,
		 IS_INIT,
		 VERSION_NO,
		 BILL_TYPE)
	VALUES
		(SYS_GUID(),
		 REPAYMENT_NO,
		 CUSTOMER_CODE,
		 RECEIVABLE_CODE,
		 AMOUNT,
		 0,
		 0,
		 'A',
		 PAYMENT_TYPE,
		 ACTIVE,
		 IS_RED_BACK,
		 BUSINESS_DATE,
		 ACCOUNT_DATE,
		 SYSDATE,
		 'N',
		 1,
		 'HK');

	COMMIT;
END;
/

