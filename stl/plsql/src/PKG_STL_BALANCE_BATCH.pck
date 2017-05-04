CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_BATCH IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/12/10 11:07:34
  -- Purpose : 结账批次操作
  ------------------------------------------------------------------
  --标记开始结账
  ------------------------------------------------------------------
  FUNCTION FUNC_BEGIN_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --核算组织
                                    P_BEGIN_DATE IN DATE -- 起始日期
                                    ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --标记结账完成
  ------------------------------------------------------------------
  FUNCTION FUNC_END_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --核算组织
                                  P_BEGIN_DATE IN DATE -- 起始日期
                                  ) RETURN BOOLEAN;
  ------------------------------------------------------------------
  --初始化结账日期
  ------------------------------------------------------------------
  PROCEDURE PROC_INIT_BALANCE_BATCH(P_BEGIN_DATE IN DATE);

END PKG_STL_BALANCE_BATCH;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_BATCH IS

  ------------------------------------------------------------------
  --标记开始结账
  ------------------------------------------------------------------
  FUNCTION FUNC_BEGIN_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --核算组织
                                    P_BEGIN_DATE IN DATE) RETURN BOOLEAN -- 起始日期
   IS
    --更新行数
    V_UPDATE_ROWS INT;
    --当前期间
    v_period varchar2(10);
  BEGIN
    --获得当前期间
    v_period := pkg_stl_balance_common.FUNC_GET_PERIOD(P_DATE1 => P_BEGIN_DATE);
  
    UPDATE STV.T_STL_BALANCE_BATCH
    --将状态更新为准备结账
       SET STATUS     = Pkg_Stl_Balance_Constant.BALANCE_BATCH_BEGIN,
           begin_time = sysdate
     WHERE ORG_CODE = P_ORG_CODE --组织编码
       AND BUSINESS_DATE = P_BEGIN_DATE --结账日期范围
          --只更新有效的批次状态
       and active = PKG_STL_Common.ACTIVE
          --指定期间
       and period = v_period
          --结账状态等于初始化
       AND STATUS = Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED;
  
    --判断更新行数
    V_UPDATE_ROWS := SQL%ROWCOUNT;
  
    --提交
    commit;
  
    RETURN V_UPDATE_ROWS = 1;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_batch.FUNC_BEGIN_BALANCE_BATCH',
                                        P_ORG_CODE || '标记开始结账');
      --返回失败标志
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --标记结账完成
  ------------------------------------------------------------------
  FUNCTION FUNC_END_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --核算组织
                                  P_BEGIN_DATE IN DATE) RETURN BOOLEAN -- 起始日期
   IS
    --更新行数
    V_UPDATE_ROWS INT;
    --当前期间
    v_period varchar2(10);
  BEGIN
    --获得当前期间
    v_period := pkg_stl_balance_common.FUNC_GET_PERIOD(P_DATE1 => P_BEGIN_DATE);
  
    UPDATE STV.T_STL_BALANCE_BATCH
    --将状态更新为准备结账
       SET STATUS   = Pkg_Stl_Balance_Constant.BALANCE_BATCH_END,
           end_time = sysdate
     WHERE ORG_CODE = P_ORG_CODE --组织编码
       AND BUSINESS_DATE = P_BEGIN_DATE --结账日期范围
          --只更新有效的批次状态
       and active = PKG_STL_Common.ACTIVE
          --指定期间
       and period = v_period
          --结账状态等于 开始结账
       AND STATUS = Pkg_Stl_Balance_Constant.BALANCE_BATCH_BEGIN;
    RETURN TRUE;
    --判断更新行数
    V_UPDATE_ROWS := SQL%ROWCOUNT;
  
    --立即提交
    COMMIT;
    RETURN V_UPDATE_ROWS = 1;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_batch.FUNC_END_BALANCE_BATCH',
                                        P_ORG_CODE || '标记结账完成');
      --返回失败标志
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --初始化结账日期
  ------------------------------------------------------------------
  PROCEDURE PROC_INIT_BALANCE_BATCH(P_BEGIN_DATE IN DATE) IS
    --起始日期
    V_BEGIN_DATE DATE;
    --截至日期
    V_END_DATE DATE;
    --结账期间
    V_PERIOD CHAR(6);
  BEGIN
    --获得当前日期
    V_BEGIN_DATE := TRUNC(P_BEGIN_DATE);
    V_END_DATE   := V_BEGIN_DATE + 1;
    V_PERIOD     := TO_CHAR(V_BEGIN_DATE, 'yyyymm');
  
    --初始化结账期间（按组织）
    INSERT INTO STV.T_STL_BALANCE_BATCH
      (ID, PERIOD, BUSINESS_DATE, ORG_CODE, ACTIVE, STATUS, create_time)
      SELECT SYS_GUID(),
             V_PERIOD,
             V_BEGIN_DATE AS BUSINESS_DATE,
             ORG_CODE,
             PKG_STL_COMMON.YES AS ACTIVE,
             Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED AS STATUS,
             sysdate as create_time
        FROM (select org_code
                from (
                      --预收
                      select b3.collection_org_code as org_code
                        from stl.t_stl_bill_deposit_received b3
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --签收记录(关联应收组织)
                      union all
                      select bill.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.REPAYMENT_SOURCE_BILL_TYPE_W
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_pod
                               where pod_date >= V_BEGIN_DATE
                                 and pod_date < V_END_DATE
                                 and pod_type in
                                     (PKG_STL_COMMOn.POD__POD_TYPE__UPD,
                                      PKG_STL_COMMON.POD__POD_TYPE__POD))
                      
						 --异常签收记录(关联应收组织)
                      --仅限始发应收
                      union all
                      select bill.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.RECEIVABLE_SOURCE_BILL_TYPE_W
                         and bill.bill_type =
                             pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_out_stock_exception
                               where create_time >= V_BEGIN_DATE
                                 and create_time < V_END_DATE)
                      --签收记录(关联代收货款应付组织)
                      union all
                      select bill.payable_org_code as org_code
                        from stl.t_stl_bill_payable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.REPAYMENT_SOURCE_BILL_TYPE_W
                         and bill.bill_type =
                             PKG_STL_Common.PAYABLE_BILL_TYPE_APC
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_pod
                               where pod_date >= V_BEGIN_DATE
                                 and pod_date < V_END_DATE
                                 and pod_type in
                                     (PKG_STL_COMMOn.POD__POD_TYPE__UPD,
                                      PKG_STL_COMMON.POD__POD_TYPE__POD))
                      union all
                      --应收
                      select b.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable b
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --其它应收
                      union all
                      --应付
                      select b1.payable_org_code as org_code
                        from stl.t_stl_bill_payable b1
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --付款组织
                      union all
                      select PAYMENT_ORG_CODE as org_code
                        from stl.T_STL_BILL_PAYMENT
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --核销
                      union all
                      select org_code
                        from stl.t_stl_bill_writeoff
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE)
               group by org_code);
    commit;
  
    --增加一个全局组织，用于保证事务完整性
    INSERT INTO STV.T_STL_BALANCE_BATCH
      (ID, PERIOD, BUSINESS_DATE, ORG_CODE, ACTIVE, STATUS, create_time)
    values
      (SYS_GUID(),
       V_PERIOD,
       V_BEGIN_DATE,
       Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
       PKG_STL_COMMON.YES,
       Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED,
       sysdate);
    --立即提交
    COMMIT;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance_batch.PROC_INIT_BALANCE_BATCH',
                                        '记账批次表初始化异常');
  END;

END PKG_STL_BALANCE_BATCH;
/
