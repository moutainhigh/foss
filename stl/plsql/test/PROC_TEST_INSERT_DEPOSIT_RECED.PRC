CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_DEPOSIT_RECED(P_DEPOSIT_RECEIVED_NO     VARCHAR2, --  预收单号
																															P_CUSTOMER_CODE           VARCHAR2, --  客户编码
																															P_CUSTOMER_NAME           VARCHAR2, --  客户名称
																															P_AMOUNT                  NUMBER, --  金额
																															P_VERIFY_AMOUNT           NUMBER, --  已核销金额
																															P_UNVERIFY_AMOUNT         NUMBER, --  未核销金额
																															P_CREATE_ORG_CODE         VARCHAR2, --  录入部门编码
																															P_CREATE_ORG_NAME         VARCHAR2, --  录入部门名称
																															P_COLLECTION_ORG_CODE     VARCHAR2, --  收款部门编码
																															P_COLLECTION_ORG_NAME     VARCHAR2, --  收款部门名称
																															P_COLLECTION_COMPANY_CODE VARCHAR2, --  收款部门所属子公司编码
																															P_COLLECTION_COMPANY_NAME VARCHAR2, --  收款部门所属子公司名称
																															P_GENERATING_ORG_CODE     VARCHAR2, --  收入部门编码
																															P_GENERATING_ORG_NAME     VARCHAR2, --  收入部门名称
																															P_GENERATING_COMPANY_CODE VARCHAR2, --  收入部门所属子公司编码
																															P_GENERATING_COMPANY_NAME VARCHAR2, --  收入部门所属子公司名称
																															P_WORKFLOW_NO             VARCHAR2, --  工作流号
																															P_REFUND_STATUS           VARCHAR2, --  退款状态
																															P_REMIT_NO                VARCHAR2, --  汇款编号
																															P_REMITER_NAME            VARCHAR2, --  汇款人名称
																															P_REMIT_ORG_CODE          VARCHAR2, --  汇款所属部门编码
																															P_REMIT_ORG_NAME          VARCHAR2, --  汇款所属部门名称
																															P_BILL_TYPE               VARCHAR2, --  单据类型
																															P_STATUS                  VARCHAR2, --  单据状态
																															P_ACTIVE                  CHAR, --  是否有效
																															P_IS_RED_BACK             CHAR, --  是否红单
																															P_PAYMENT_TYPE            VARCHAR2, --  支付方式
																															P_BUSINESS_DATE           DATE, --  业务日期
																															P_ACCOUNT_DATE            DATE, --  记账日期
																															P_CREATE_TIME             DATE, --  创建时间
																															P_CASH_CONFIRM_TIME       DATE, --  收银确认时间
																															P_IS_INIT                 CHAR, --  是否初始化
																															P_STATEMENT_BILL_NO       VARCHAR2, --  对账单号
																															P_PAYMENT_AMOUNT          NUMBER, --  付款金额
																															P_TRANSPORT_TYPE          VARCHAR2, --  运输类型
																															P_PAYMENT_NO              VARCHAR2 --  付款单号
																															) IS
BEGIN

	INSERT INTO STL.T_STL_BILL_DEPOSIT_RECEIVED
		(ID, --  ID
		 DEPOSIT_RECEIVED_NO, --  预收单号
		 CUSTOMER_CODE, --  客户编码
		 CUSTOMER_NAME, --  客户名称
		 CURRENCY_CODE, --  币种
		 AMOUNT, --  金额
		 VERIFY_AMOUNT, --  已核销金额
		 UNVERIFY_AMOUNT, --  未核销金额
		 CREATE_ORG_CODE, --  录入部门编码
		 CREATE_ORG_NAME, --  录入部门名称
		 COLLECTION_ORG_CODE, --  收款部门编码
		 COLLECTION_ORG_NAME, --  收款部门名称
		 COLLECTION_COMPANY_CODE, --  收款部门所属子公司编码
		 COLLECTION_COMPANY_NAME, --  收款部门所属子公司名称
		 GENERATING_ORG_CODE, --  收入部门编码
		 GENERATING_ORG_NAME, --  收入部门名称
		 GENERATING_COMPANY_CODE, --  收入部门所属子公司编码
		 GENERATING_COMPANY_NAME, --  收入部门所属子公司名称
		 WORKFLOW_NO, --  工作流号
		 REFUND_STATUS, --  退款状态
		 REMIT_NO, --  汇款编号
		 REMITER_NAME, --  汇款人名称
		 REMIT_ORG_CODE, --  汇款所属部门编码
		 REMIT_ORG_NAME, --  汇款所属部门名称
		 BILL_TYPE, --  单据类型
		 STATUS, --  单据状态
		 ACTIVE, --  是否有效
		 IS_RED_BACK, --  是否红单
		 PAYMENT_TYPE, --  支付方式
		 CREATE_USER_CODE, --  制单人工号
		 CREATE_USER_NAME, --  制单人名称
		 AUDIT_USER_CODE, --  审核人工号
		 AUDIT_USER_NAME, --  审核人名称
		 MODIFY_USER_CODE, --  修改人工号
		 MODIFY_USER_NAME, --  修改人名称
		 DISABLE_USER_CODE, --  做废人工号
		 DISABLE_USER_NAME, --  做废人名称
		 CASH_CONFIRM_USER_CODE, --  收银确认人工号
		 CASH_CONFIRM_USER_NAME, --  收银确认人名称
		 BUSINESS_DATE, --  业务日期
		 ACCOUNT_DATE, --  记账日期
		 CREATE_TIME, --  创建时间
		 MODIFY_TIME, --  修改时间
		 DISABLE_TIME, --  做废时间
		 CASH_CONFIRM_TIME, --  收银确认时间
		 IS_INIT, --  是否初始化
		 STATEMENT_BILL_NO, --  对账单号
		 NOTES, --  备注
		 VERSION_NO, --  版本号
		 PAYMENT_AMOUNT, --  付款金额
		 PAYMENT_NOTES, --  付款备注
		 TRANSPORT_TYPE, --  运输类型
		 PAYMENT_NO --  付款单号

		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_DEPOSIT_RECEIVED_NO, --  预收单号
		 P_CUSTOMER_CODE, --  客户编码
		 P_CUSTOMER_NAME, --  客户名称
		 'RMB', --  币种
		 P_AMOUNT, --  金额
		 P_VERIFY_AMOUNT, --  已核销金额
		 P_UNVERIFY_AMOUNT, --  未核销金额
		 P_CREATE_ORG_CODE, --  录入部门编码
		 P_CREATE_ORG_NAME, --  录入部门名称
		 P_COLLECTION_ORG_CODE, --  收款部门编码
		 P_COLLECTION_ORG_NAME, --  收款部门名称
		 P_COLLECTION_COMPANY_CODE, --  收款部门所属子公司编码
		 P_COLLECTION_COMPANY_NAME, --  收款部门所属子公司名称
		 P_GENERATING_ORG_CODE, --  收入部门编码
		 P_GENERATING_ORG_NAME, --  收入部门名称
		 P_GENERATING_COMPANY_CODE, --  收入部门所属子公司编码
		 P_GENERATING_COMPANY_NAME, --  收入部门所属子公司名称
		 P_WORKFLOW_NO, --  工作流号
		 P_REFUND_STATUS, --  退款状态
		 P_REMIT_NO, --  汇款编号
		 P_REMITER_NAME, --  汇款人名称
		 P_REMIT_ORG_CODE, --  汇款所属部门编码
		 P_REMIT_ORG_NAME, --  汇款所属部门名称
		 P_BILL_TYPE, --  单据类型
		 P_STATUS, --  单据状态
		 P_ACTIVE, --  是否有效
		 P_IS_RED_BACK, --  是否红单
		 P_PAYMENT_TYPE, --  支付方式
		 '000123', --  制单人工号
		 '张三', --  制单人名称
		 '000123', --  审核人工号
		 '张三', --  审核人名称
		 NULL, --  修改人工号
		 NULL, --  修改人名称
		 NULL, --  做废人工号
		 NULL, --  做废人名称
		 NULL, --  收银确认人工号
		 NULL, --  收银确认人名称
		 P_BUSINESS_DATE, --  业务日期
		 P_ACCOUNT_DATE, --  记账日期
		 P_CREATE_TIME, --  创建时间
		 NULL, --  修改时间
		 NULL, --  做废时间
		 P_CASH_CONFIRM_TIME, --  收银确认时间
		 P_IS_INIT, --  是否初始化
		 P_STATEMENT_BILL_NO, --  对账单号
		 '', --  备注
		 1, --  版本号
		 P_PAYMENT_AMOUNT, --  付款金额
		 '', --  付款备注
		 P_TRANSPORT_TYPE, --  运输类型
		 P_PAYMENT_NO --  付款单号
		 );
	COMMIT;

END PROC_TEST_INSERT_DEPOSIT_RECED;
/

