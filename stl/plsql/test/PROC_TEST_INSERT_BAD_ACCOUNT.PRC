CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_BAD_ACCOUNT(P_BAD_DEBAT_BILL_NO varchar2, --  坏账单编号
																												 P_APPLY_ORG_CODE varchar2, --  申请部门编码
																												 P_APPLY_ORG_NAME varchar2, --  申请部门名称
																												 P_APPLY_USER_CODE varchar2, --  申请人工号
																												 P_APPLY_USER_NAME varchar2, --  申请人名称
																												 P_ERROR_HANDING_NO varchar2, --  差错处理编号
																												 P_RECEIVABLE_NO varchar2, --  应收单号
																												 P_WAYBILL_NO varchar2, --  运单号
																												 P_SOURCE_BILL_TYPE varchar2, --  来源单据类型
																												 P_ORIG_ORG_CODE varchar2, --  出发部门编码
																												 P_ORIG_ORG_NAME varchar2, --  出发部门名称
																												 P_DEST_ORG_CODE varchar2, --  收货部门编码
																												 P_DEST_ORG_NAME varchar2, --  收货部门名称
																												 P_FCUSTOMER_CODE varchar2, --  发货客户编码
																												 P_FCUSTOMER_NAME varchar2, --  发货客户名称
																												 P_ARRV_REGION_CODE varchar2, --  目的站
																												 P_RECEIVE_CUSTOMER_CODE varchar2, --  收货客户名称
																												 P_RECEIVE_CUSTOMER_NAME varchar2, --  收货客户编码
																												 P_TOTAL_FEE number, --  总运费
																												 P_PAYMENT_TYPE varchar2, --  收款方式
																												 P_BAD_AMOUNT number, --  坏账金额
																												 P_AUDIT_STATUS varchar2, --  审核状态
																												 P_VERIFY_STATUS CHAR --  是否核销
																												 ) IS
BEGIN

	INSERT INTO STL.T_STL_BILL_BAD_ACCOUNT
		(ID, --  ID
		 BAD_DEBAT_BILL_NO, --  坏账单编号
		 APPLY_ORG_CODE, --  申请部门编码
		 APPLY_ORG_NAME, --  申请部门名称
		 APPLY_USER_CODE, --  申请人工号
		 APPLY_USER_NAME, --  申请人名称
		 ERROR_HANDING_NO, --  差错处理编号
		 APPLY_REASON, --  申请事由
		 RECEIVABLE_NO, --  应收单号
		 WAYBILL_NO, --  运单号
		 SOURCE_BILL_TYPE, --  来源单据类型
		 ORIG_ORG_CODE, --  出发部门编码
		 ORIG_ORG_NAME, --  出发部门名称
		 DEST_ORG_CODE, --  收货部门编码
		 DEST_ORG_NAME, --  收货部门名称
		 FCUSTOMER_CODE, --  发货客户编码
		 FCUSTOMER_NAME, --  发货客户名称
		 DELIVERY_CUSTOMER_PHONE, --  发货客户电话
		 ARRV_REGION_CODE, --  目的站
		 RECEIVE_CUSTOMER_CODE, --  收货客户名称
		 RECEIVE_CUSTOMER_NAME, --  收货客户编码
		 TOTAL_FEE, --  总运费
		 GOODS_NAME, --  货物名称
		 PAYMENT_TYPE, --  收款方式
		 BAD_AMOUNT, --  坏账金额
		 AUDIT_STATUS, --  审核状态
		 WORKFLOW_NO, --  工作流号
		 CREATE_ORG_CODE, --  创建部门编码
		 CREATE_ORG_NAME, --  创建部门名称
		 CREATE_USER_CODE, --  创建人工号
		 CREATE_USER_NAME, --  创建人名称
		 VERIFY_STATUS, --  是否核销
		 CREATE_TIME, --  创建时间
		 NOTES, --  备注
		 CURRENCY_CODE --  币种

		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_BAD_DEBAT_BILL_NO, --  坏账单编号
		 P_APPLY_ORG_CODE, --  申请部门编码
		 P_APPLY_ORG_NAME, --  申请部门名称
		 P_APPLY_USER_CODE, --  申请人工号
		 P_APPLY_USER_NAME, --  申请人名称
		 P_ERROR_HANDING_NO, --  差错处理编号
		 '', --  申请事由
		 P_RECEIVABLE_NO, --  应收单号
		 P_WAYBILL_NO, --  运单号
		 P_SOURCE_BILL_TYPE, --  来源单据类型
		 P_ORIG_ORG_CODE, --  出发部门编码
		 P_ORIG_ORG_NAME, --  出发部门名称
		 P_DEST_ORG_CODE, --  收货部门编码
		 P_DEST_ORG_NAME, --  收货部门名称
		 P_FCUSTOMER_CODE, --  发货客户编码
		 P_FCUSTOMER_NAME, --  发货客户名称
		 '1596284077', --  发货客户电话
		 P_ARRV_REGION_CODE, --  目的站
		 P_RECEIVE_CUSTOMER_CODE, --  收货客户名称
		 P_RECEIVE_CUSTOMER_NAME, --  收货客户编码
		 P_TOTAL_FEE, --  总运费
		 'GOODS_NAME', --  货物名称
		 P_PAYMENT_TYPE, --  收款方式
		 P_BAD_AMOUNT, --  坏账金额
		 P_AUDIT_STATUS, --  审核状态
		 '', --  工作流号
		 '', --  创建部门编码
		 '', --  创建部门名称
		 '000000', --  创建人工号
		 '超级管理员', --  创建人名称
		 P_VERIFY_STATUS, --  是否核销
		 SYSDATE, --  创建时间
		 '', --  备注
		 'RMB' --  币种

		 );
	COMMIT;

END PROC_TEST_INSERT_BAD_ACCOUNT;
/

