CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_OTHER_REVENUE(P_OTHER_REVENUE_NO    VARCHAR2, --  小票单号
																													 P_WAYBILL_NO          VARCHAR2, --  运单号
																													 P_GENERATING_ORG_CODE VARCHAR2, --  收入部门编码
																													 P_GENERATING_ORG_NAME VARCHAR2, --  收入部门名称
																													 P_GENERATING_COM_CODE VARCHAR2, --  收入子公司编码
																													 P_GENERATING_COM_NAME VARCHAR2, --  收入子公司名称
																													 P_CUSTOMER_CODE       VARCHAR2, --  客户编号
																													 P_CUSTOMER_NAME       VARCHAR2, --  客户名称
																													 P_CUSTOMER_TYPE       VARCHAR2, --  客户类型
																													 P_PAYMENT_TYPE        VARCHAR2, --  收款方式
																													 P_INCOME_CATEGORIES   VARCHAR2, --  收入类别
																													 P_AMOUNT              NUMBER default 10000, --  金额
																													 P_BUSINESS_DATE       DATE, --  业务日期
																													 P_ACTIVE              CHAR, --  是否有效
																													 P_IS_RED_BACK         CHAR, --  是否红单
																													 P_IS_DISABLE        CHAR --  是否作废
																													 ) IS
BEGIN

	INSERT INTO STL.T_STL_OTHER_REVENUE
		(ID, --  ID
		 OTHER_REVENUE_NO, --  小票单号
		 WAYBILL_NO, --  运单号
		 GENERATING_ORG_CODE, --  收入部门编码
		 GENERATING_ORG_NAME, --  收入部门名称
		 GENERATING_COM_CODE, --  收入子公司编码
		 GENERATING_COM_NAME, --  收入子公司名称
		 CUSTOMER_CODE, --  客户编号
		 CUSTOMER_NAME, --  客户名称
		 CUSTOMER_PHONE, --  客户电话
		 CUSTOMER_TYPE, --  客户类型
		 PAYMENT_TYPE, --  收款方式
		 INCOME_CATEGORIES, --  收入类别
		 AMOUNT, --  金额
		 BUSINESS_DATE, --  业务日期
		 VERSION_NO, --  版本号
		 ACTIVE, --  是否有效
		 IS_RED_BACK, --  是否红单
		 CREATE_USER_CODE, --  制单人编码
		 CREATE_USER_NAME, --  制单人名称
		 CREATE_ORG_CODE, --  制单部门编码
		 CREATE_ORG_NAME, --  制单部门名称
		 CREATE_TIME, --  创建时间
		 MODIFY_TIME, --  修改时间
		 MODIFY_USER_CODE, --  修改人编码
		 MODIFY_USER_NAME, --  修改人
		 IS_DISABLE, --  是否作废
		 DISABLE_USER_CODE, --  作废人编码
		 DISABLE_USER_NAME, --  作废人
		 DISABLE_TIME, --  作废时间
		 NOTES, --  备注
		 CURRENCY_CODE --  币种
		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_OTHER_REVENUE_NO, --  小票单号
		 P_WAYBILL_NO, --  运单号
		 P_GENERATING_ORG_CODE, --  收入部门编码
		 P_GENERATING_ORG_NAME, --  收入部门名称
		 P_GENERATING_COM_CODE, --  收入子公司编码
		 P_GENERATING_COM_NAME, --  收入子公司名称
		 P_CUSTOMER_CODE, --  客户编号
		 P_CUSTOMER_NAME, --  客户名称
		 '15962840777', --  客户电话
		 P_CUSTOMER_TYPE, --  客户类型
		 P_PAYMENT_TYPE, --  收款方式
		 P_INCOME_CATEGORIES, --  收入类别
		 P_AMOUNT, --  金额
		 P_BUSINESS_DATE, --  业务日期
		 1, --  版本号
		 P_ACTIVE, --  是否有效
		 P_IS_RED_BACK, --  是否红单
		 '000000',
		 '超级管理员',
		 '', --  制单部门编码
		 '', --  制单部门名称
		 SYSDATE, --  创建时间
		 SYSDATE, --  修改时间
		 '', --  修改人编码
		 '', --  修改人
		 P_IS_DISABLE, --  是否作废
		 '000000',
		 '超级管理员',
		 SYSDATE, --  作废时间
		 '', --  备注
		 'RMB' --  币种
		 );
	COMMIT;

END PROC_TEST_INSERT_OTHER_REVENUE;
/

