CREATE OR REPLACE PACKAGE PKG_STL_VCH_JOBS IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 凭证任务入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每日凭证任务
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_JOBS_DAILY(P_PERIOD VARCHAR2 -- 账期yyyymmdd，例子20121221
                                   ) RETURN BOOLEAN;

  -----------------------------------------------------------------
  -- 每月凭证任务
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_JOBS_MONTHLY(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
                                     ) RETURN BOOLEAN;

  --  每日凭证
  PROCEDURE PROC_VOUCHER_JOBS_DAILY;

  -- 每月凭证
  PROCEDURE PROC_VOUCHER_JOBS_MONTHLY;

END PKG_STL_VCH_JOBS;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_JOBS IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 凭证任务入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每日凭证任务
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_JOBS_DAILY(P_PERIOD VARCHAR2 -- 账期yyyymmdd，例子20121221
                                   ) RETURN BOOLEAN IS
  
    V_RESULT     BOOLEAN;
    V_BEGIN_DATE DATE := TO_DATE(P_PERIOD, 'YYYYMMDD');
    V_END_DATE   DATE := V_BEGIN_DATE + 1;
  
  BEGIN
  
    V_RESULT := PKG_STL_VCH_DAILY.FUNC_VOUCHER_DAILY(P_PERIOD,
                                                     V_BEGIN_DATE,
                                                     V_END_DATE);
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        V_BEGIN_DATE,
                                        V_END_DATE,
                                        'PKG_STL_VCH_JOBS.FUNC_VOUCHER_JOBS_DAILY',
                                        '凭证报表JOB日明细生成异常:' || SQLERRM);
    
      --返回失败标志
      RETURN FALSE;
    
  END FUNC_VOUCHER_JOBS_DAILY;

  -----------------------------------------------------------------
  -- 每月凭证任务
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_JOBS_MONTHLY(P_PERIOD VARCHAR2 -- 账期yyyymm，例子201212
                                     ) RETURN BOOLEAN IS
  
    CURSOR CUR_PERIOD IS
      SELECT T.ID, T.PERIOD, T.VOUCHER_BEGIN_TIME, T.VOUCHER_END_TIME
        FROM STV.T_STL_ACCOUNTING_PERIODS T
       WHERE T.PERIOD = P_PERIOD
         AND T.ACTIVE = PKG_STL_COMMON.ACTIVE;
  
    V_BEGIN_DATE DATE;
    V_END_DATE   DATE;
    V_RESULT     BOOLEAN := TRUE;
  
  BEGIN
  
    FOR V_CUR_ROW IN CUR_PERIOD LOOP
    
      V_BEGIN_DATE := V_CUR_ROW.VOUCHER_BEGIN_TIME;
      V_END_DATE   := V_CUR_ROW.VOUCHER_END_TIME;
    
      --统计汇总
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY(V_BEGIN_DATE,
                                                             V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFO(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFD(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLR(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFI(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLI(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD(P_PERIOD,
                                                                V_BEGIN_DATE,
                                                                V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LDD(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LDI(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO(P_PERIOD,
                                                         V_BEGIN_DATE,
                                                         V_END_DATE);
      END IF;
    
      IF V_RESULT THEN
        UPDATE STV.T_STL_ACCOUNTING_PERIODS T
           SET T.ACTIVE = PKG_STL_COMMON.INACTIVE
         WHERE T.ID = V_CUR_ROW.ID;
        COMMIT;
      END IF;
    
    END LOOP;
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        V_BEGIN_DATE,
                                        V_END_DATE,
                                        'PKG_STL_VCH_JOBS.FUNC_VOUCHER_JOBS_MONTHLY',
                                        '凭证报表JOB月报表生成异常:' || SQLERRM);
    
      --返回失败标志
      RETURN FALSE;
    
  END FUNC_VOUCHER_JOBS_MONTHLY;

  --  每日凭证
  PROCEDURE PROC_VOUCHER_JOBS_DAILY IS
  
    V_PERIOD VARCHAR2(50) := TO_CHAR(SYSDATE - 1, 'YYYYMMDD');
    V_RESULT BOOLEAN;
  
  BEGIN
  
    V_RESULT := FUNC_VOUCHER_JOBS_DAILY(V_PERIOD);
  
  END PROC_VOUCHER_JOBS_DAILY;

  -- 每月凭证
  PROCEDURE PROC_VOUCHER_JOBS_MONTHLY IS
  
    V_END_DATE DATE := TRUNC(SYSDATE);
    V_COUNT    INT;
    V_PERIOD   VARCHAR2(50);
    V_RESULT   BOOLEAN;
  
  BEGIN
  
    SELECT COUNT(1)
      INTO V_COUNT
      FROM STV.T_STL_ACCOUNTING_PERIODS T
     WHERE T.VOUCHER_END_TIME = V_END_DATE
       AND T.ACTIVE = PKG_STL_COMMON.ACTIVE;
  
    IF V_COUNT > 0 THEN
    
      SELECT T.PERIOD
        INTO V_PERIOD
        FROM STV.T_STL_ACCOUNTING_PERIODS T
       WHERE T.VOUCHER_END_TIME = V_END_DATE
         AND T.ACTIVE = PKG_STL_COMMON.ACTIVE
         AND ROWNUM <= 1;
    
      V_RESULT := FUNC_VOUCHER_JOBS_MONTHLY(V_PERIOD);
    
    END IF;
  
  END PROC_VOUCHER_JOBS_MONTHLY;

END PKG_STL_VCH_JOBS;
/
