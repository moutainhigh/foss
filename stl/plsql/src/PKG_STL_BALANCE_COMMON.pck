CREATE OR REPLACE PACKAGE STV.PKG_STL_BALANCE_COMMON IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/20 9:37:48
  -- Purpose : 期末结公用模块 

  ------------------------------------------------------------------
  --获得起始日期日期(默认当前日期前一天的00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_BEGIN_DATE(P_CURRENT_DATE DATE --结算日期
                               ) RETURN DATE;

  ------------------------------------------------------------------
  --获得截至日期(默认当前日期的00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_END_DATE(P_CURRENT_DATE DATE --结算日期
                             ) RETURN DATE;

  ------------------------------------------------------------------
  --根据日期获得当期日期对应的期间
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PERIOD(P_DATE1 DATE --起始日期
                           ) RETURN STRING;

  ------------------------------------------------------------------
  --根据日期获得对应上期日期的期间
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_LAST_PERIOD(P_PERIOD_DATE DATE --结算对应的期间
                                ) RETURN STRING;

  ------------------------------------------------------------------
  --获得分页后的页数
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PAGE_SIZE(P_ROW_NUM INT --页行数
                              ) RETURN INT;

  ------------------------------------------------------------------
  --是否为第一个记账期间
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_JUNUARY(P_PERIOD STRING --结账期间
                           ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --判断是否为月份的第一天
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_FIRST_DAY(P_CURRENT_DATE DATE --结算日期
                                   ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --判断是否为月份的最后一天
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_LAST_DAY(P_CURRENT_DATE DATE --结算日期
                                  ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --判断是否第一次结算
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_FIRST_BALANCE(P_ORG_CODE     STRING, --组织编码
                                 P_BALANCE_TYPE STRING --结账类型
                                 ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --计算每日期初余额
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_BEGIN(P_BEGIN_DATE DATE, --起始日期                                   
                                   P_PERIOD     STRING --结账期间
                                   ) RETURN BOOLEAN;

  --对每日余额明细进行计算，用于月底对明细进行汇总
  FUNCTION FUNC_DAILY_Detail_END(P_BEGIN_DATE DATE, --起始日期
                                 P_END_DATE   DATE, --结束日期
                                 P_PERIOD     STRING --结账期间
                                 ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --计算明细期末余额
  ------------------------------------------------------------------
  FUNCTION FUNC_MONTH_DETAIL_END_AMOUNT(P_PERIOD STRING --结账期间
                                        ) RETURN BOOLEAN;

  --计算月度余额
  FUNCTION FUNC_MONTH_CUSTOMER_END_AMOUNT(P_PERIOD STRING --结账期间
                                          ) RETURN BOOLEAN;

END PKG_STL_BALANCE_COMMON;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_BALANCE_COMMON IS

  ------------------------------------------------------------------
  --获得起始日期日期(默认当前日期的00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_BEGIN_DATE(P_CURRENT_DATE DATE --结算日期
                               ) RETURN DATE IS
    --声明返回结果
    V_RESULT DATE;
  BEGIN
    --如果参数不为空，则进行处理
    IF P_CURRENT_DATE IS NOT NULL THEN
      --将结算日期的前一天，去掉时分秒
      V_RESULT := TRUNC(P_CURRENT_DATE);
    ELSE
      RAISE_APPLICATION_ERROR(-20100, '参数为空!');
    END IF;
    RETURN V_RESULT;
  END;

  ------------------------------------------------------------------
  --获得截至日期(默认当前日期的第二天00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_END_DATE(P_CURRENT_DATE DATE --结算日期
                             ) RETURN DATE IS
    --声明返回结果
    V_RESULT DATE;
  BEGIN
    --如果参数不为空，则进行处理
    IF P_CURRENT_DATE IS NOT NULL THEN
      --将结算日期 + 1，去掉时分秒
      V_RESULT := TRUNC(P_CURRENT_DATE + 1);
    ELSE
      RAISE_APPLICATION_ERROR(-20100, '参数为空!');
    END IF;
    RETURN V_RESULT;
  END;

  ------------------------------------------------------------------
  --获得结账期间
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PERIOD(P_DATE1 DATE --起始日期
                           ) RETURN STRING IS
  BEGIN
  
    --返回YYYYMM格式的期间
    RETURN TO_CHAR(P_DATE1, 'yyyymm');
  END;

  ------------------------------------------------------------------
  --根据日期获得对应上期日期的期间
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_LAST_PERIOD(P_PERIOD_DATE DATE --结算对应的期间
                                ) RETURN STRING IS
  BEGIN
    --返回上一月份 的期间
    RETURN TO_CHAR(ADD_MONTHS(P_PERIOD_DATE, -1), 'yyyymm');
  END;

  ------------------------------------------------------------------
  --获得分页后的页数
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PAGE_SIZE(P_ROW_NUM INT --页行数
                              ) RETURN INT IS
    --页面大小
    V_PAGE_SIZE INT;
    --取摸
    V_MOD_RESULT INT;
  BEGIN
    --取余
    V_MOD_RESULT := MOD(P_ROW_NUM, PKG_STL_COMMON.MAX_PAGE_ROW_SIZE);
    --异常处理
    IF V_MOD_RESULT = 0 THEN
      V_PAGE_SIZE := P_ROW_NUM / PKG_STL_COMMON.MAX_PAGE_ROW_SIZE;
    ELSE
      V_PAGE_SIZE := P_ROW_NUM / PKG_STL_COMMON.MAX_PAGE_ROW_SIZE + 1;
    END IF;
  
    RETURN V_PAGE_SIZE;
  END;

  ------------------------------------------------------------------
  --判断是否为结账期间为1月
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_JUNUARY(P_PERIOD STRING --结账期间
                           ) RETURN BOOLEAN IS
  BEGIN
    --从第5个字符开始,获取月份
    RETURN SUBSTR(P_PERIOD, 5, 2) = '01';
  END;

  ------------------------------------------------------------------
  --判断是否为月份的第一天
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_FIRST_DAY(P_CURRENT_DATE DATE --结算日期
                                   ) RETURN BOOLEAN IS
  BEGIN
    RETURN TRUNC(P_CURRENT_DATE, 'MM') = TRUNC(P_CURRENT_DATE);
  END;

  ------------------------------------------------------------------
  --判断是否为月份的最后一天
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_LAST_DAY(P_CURRENT_DATE DATE --结算日期
                                  ) RETURN BOOLEAN IS
  BEGIN
    RETURN TRUNC(LAST_DAY(P_CURRENT_DATE)) = TRUNC(P_CURRENT_DATE);
  END;

  ------------------------------------------------------------------
  --判断是否第一次结账
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_FIRST_BALANCE(P_ORG_CODE     STRING, --组织编码
                                 P_BALANCE_TYPE STRING --结账类型
                                 ) RETURN BOOLEAN IS
    --期末余额行数
    V_BALANCE_ROWNUM INT;
  BEGIN
    SELECT COUNT(1)
      INTO V_BALANCE_ROWNUM
      FROM STV.T_STL_BALANCE
     WHERE
    --按组织
     CHECK_OUT_ORG_CODE = P_ORG_CODE
    --结算类型
     and STL_TYPE = P_BALANCE_TYPE
    --只查询1行
     AND ROWNUM = 1;
    --根据返回结果判断是否为第一次结账
    RETURN V_BALANCE_ROWNUM = 0;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(SYSDATE,
                                        SYSDATE,
                                        SYSDATE,
                                        'pkg_stl_balance_common.FUNC_IS_FIRST_BALANCE',
                                        P_BALANCE_TYPE || '判断是否第一次结账');
      --返回失败标志
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --计算期初余额
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_BEGIN(P_BEGIN_DATE DATE, --起始日期
                                   P_PERIOD     STRING --结账期间
                                   ) RETURN BOOLEAN IS
  
    --是否第一次进行操作；
    V_IS_FIRST_OPERATE BOOLEAN := false;
    --结账期间
    V_PERIOD STRING(8);
    --上一结账期间
    V_LAST_PERIOD STRING(8);
    --结账行数
    V_BALANCE_STATUS_ROWS int;
  
  BEGIN
    --获得记账期间
    V_PERIOD := P_PERIOD;
  
     /*    SELECT COUNT(1)
      INTO V_BALANCE_STATUS_ROWS
      FROM STV.T_STL_BALANCE_BATCH
     WHERE ROWNUM = 1
          --已经结账
       and status = pkg_stl_balance_constant.BALANCE_BATCH_END
          --只查询有效的期间
       and active = Pkg_Stl_Common.YES;*/
  
    --如果第一结账
    IF /*V_BALANCE_STATUS_ROWS = 0*/P_BEGIN_DATE<= PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE THEN
      --判断是否第一次结账
      V_IS_FIRST_OPERATE := true;
    end if;
  
    --如果是，从初始化的余额获得原始数据
    IF V_IS_FIRST_OPERATE = TRUE THEN
      --插入初始化的期末大于0的余额
      INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
      --ID,期间,组织
        (ID,
         CHECK_OUT_PERIOD,
         CUSTOMER_CODE,
         CHECK_OUT_ORG_CODE,
         SOURCE_BILL_NO, --来源单号
         SOURCE_BILL_TYPE, --来源单据类别
         STL_TYPE, -- 结算类型
         BILL_TYPE， --单据子类型
         PRODUCT_CODE,
         BEGIN_AMOUNT, --期初
         PRODUCE_AMOUNT, --发生
         WRITEOFF_AMOUNT, --核销
         END_AMOUNT, --期末
         CURRENCY_CODE,
         ACTIVE,
         BALANCE_TIME, --结账日期
         CREATE_TIME, --结账日期
         business_date,
         payment_type,
         invoice_mark --发票标记
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
               NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别
               STL_TYPE AS STL_TYPE, -- 结算类型
               BILL_TYPE AS BILL_TYPE, --单据子类型
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
               END_AMOUNT AS BEGIN_AMOUNT, --本期期初
               0 AS PRODUCE_AMOUNT, --本期发生额
               0 AS WRITEOFF_AMOUNT, --本期核销金额
               0 AS END_AMOUNT, --本期期末余额
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               P_BEGIN_DATE AS BALANCE_TIME,
               SYSDATE AS CREATE_TIME,
               business_date as business_date,
               payment_type as payment_type,
               p.INVOICE_MARK
          FROM STV.t_Stl_Balance_Invoice_Init P
        --只要有效的期初类别
         where p.stl_type in
               (PKG_STL_BALANCE_CONSTANT.BALANCE_TYPE_RECEIVABLE,
                Pkg_Stl_Balance_Constant.BALANCE_TYPE_PAYABLE,
                pkg_stl_balance_constant.BALANCE_TYPE_DEPOSIT_BILL,
                pkg_stl_balance_constant.BALANCE_TYPE_DEPOSIT_TF)
              
              --只需要不等于0的余额，作为下期期初
           and END_AMOUNT != 0;
      --提交
      commit;
    
    ELSE
      --否则获得上一期期末
      V_LAST_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_LAST_PERIOD(P_BEGIN_DATE);
    
      --插入上期大于0的余额
      INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
      --ID,期间,组织
        (ID,
         CHECK_OUT_PERIOD,
         CUSTOMER_CODE,
         CHECK_OUT_ORG_CODE,
         SOURCE_BILL_NO, --来源单号
         SOURCE_BILL_TYPE, --来源单据类别
         STL_TYPE, -- 结算类型
         BILL_TYPE， --单据子类型
         PRODUCT_CODE,
         BEGIN_AMOUNT, --期初
         PRODUCE_AMOUNT, --发生
         WRITEOFF_AMOUNT, --核销
         END_AMOUNT, --期末
         CURRENCY_CODE,
         ACTIVE,
         BALANCE_TIME, --结账日期
         CREATE_TIME, --结账日期
         business_date,
         payment_type,
         invoice_mark--发票标记        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
               NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别
               STL_TYPE AS STL_TYPE, -- 结算类型
               BILL_TYPE AS BILL_TYPE, --单据子类型
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
               END_AMOUNT AS BEGIN_AMOUNT, --本期期初
               0 AS PRODUCE_AMOUNT, --本期发生额
               0 AS WRITEOFF_AMOUNT, --本期核销金额
               0 AS END_AMOUNT, --本期期末余额
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               P_BEGIN_DATE AS BALANCE_TIME,
               SYSDATE AS CREATE_TIME,
               business_date as business_date,
               payment_type as payment_type,
               p.invoice_mark as invoice_mark --发票标记           
          FROM STV.T_STL_BALANCE_DETAIL P
        
         WHERE
        --上期期末
         CHECK_OUT_PERIOD = V_LAST_PERIOD
        
        --只需要不等于0的余额，作为下期期初
         AND END_AMOUNT != 0;
    
      --提交
      commit;
    END IF;
    --默认返回成功
    RETURN TRUE;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_begin',
                                        '期末结账核算期初时发生异常');
    
      --返回成功标志
      RETURN FALSE;
  END;

  --计算客户每日余额
  FUNCTION FUNC_DAILY_Detail_END(P_BEGIN_DATE DATE, --起始日期
                                 P_END_DATE   DATE, --结束日期
                                 P_PERIOD     STRING --结账期间
                                 ) RETURN BOOLEAN IS
  BEGIN
    INSERT INTO STV.T_STL_DAILY_BALANCE
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       STL_TYPE, -- 结算类型
       BILL_TYPE, --结算单据子类型       
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       PRODUCT_CODE,       
       BEGIN_AMOUNT, --本期期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --本期核销金额
       END_AMOUNT, --本期期末余额      
       CURRENCY_CODE,
       ACTIVE,
       BALANCE_TIME,
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记      
       )
      SELECT SYS_GUID() as ID,
             P_PERIOD as CHECK_OUT_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE,
             BILL_TYPE,
             SOURCE_BILL_NO,
             SOURCE_BILL_TYPE,
             PRODUCT_CODE,
             
             BEGIN_AMOUNT,
             PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT,
             END_AMOUNT,
             
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE            as ACTIVE,
             P_BEGIN_DATE                     as BALANCE_TIME,
             SYSDATE                          as CREATE_TIME,
             business_date,
             payment_type,
             invoice_mark as invoice_mark --发票标记            
        from (select CUSTOMER_CODE as customer_code,
                     CHECK_OUT_ORG_CODE,
                     STL_TYPE, -- 结算类型
                     BILL_TYPE, --结算单据子类型             
                     SOURCE_BILL_NO, --来源单号
                     SOURCE_BILL_TYPE, --来源单据类别
                     PRODUCT_CODE,
                     SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT, --本期期初
                     SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
                     SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
                     SUM(BEGIN_AMOUNT) + SUM(PRODUCE_AMOUNT) -
                     SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --本期期末                    
                     min(business_date) as business_date,
                     payment_type,
                     invoice_mark --发票标记                    
                FROM STV.T_STL_DAILY_BALANCE_tmp
               WHERE CHECK_OUT_PERIOD = P_PERIOD --核算期间
                 AND BALANCE_TIME >= P_BEGIN_DATE
                 AND BALANCE_TIME < P_END_DATE              
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --来源单号
                        SOURCE_BILL_TYPE, --来源单据类别
                        payment_type, --付款方式
                        STL_TYPE, -- 结算类型
                        BILL_TYPE,--结算单据子类型
                        invoice_mark --发票标记
                        ); 
    --提交
    commit;
    RETURN TRUE;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_DAILY_CUSTOMER_END_AMOUNT',
                                        '核算客户每日余额明细');
      --返回失败标志
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --计算每月明细期末余额
  ------------------------------------------------------------------
  FUNCTION FUNC_MONTH_DETAIL_END_AMOUNT(P_PERIOD STRING --结账期间
                                        ) RETURN BOOLEAN IS
  
    --结账期间
    V_PERIOD STRING(8);
    --系统时间
    V_SYSDATE DATE;
  
    --结账日期
    V_PERIOD_DATE DATE;
  
    --起始日期
    v_begin_date DATE;
  
    --起始日期
    v_end_date DATE;
  
  BEGIN
    --获得记账期间
    V_PERIOD := P_PERIOD;
  
    --获得系统时间
    V_SYSDATE := SYSDATE();
  
    --获得结账期间对应的日期(最后一天)
    V_PERIOD_DATE := LAST_DAY(TO_DATE(P_PERIOD, 'yyyymm'));
  
    --起始日期
    v_begin_date := TO_DATE(P_PERIOD, 'yyyymm');
  
    --结束日期(该期间最后一天)
    v_end_date := TRUNC(LAST_DAY(V_PERIOD_DATE));
  
    --插入余额表
    INSERT INTO STV.T_STL_BALANCE_DETAIL
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       customer_name,
       CHECK_OUT_ORG_CODE,
       CHECK_OUT_ORG_name,
       CHECK_OUT_Com_CODE,
       CHECK_OUT_Com_Name,
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       STL_TYPE, -- 结算类型
       BILL_TYPE, --结算单据子类型
       PRODUCT_CODE,
       BEGIN_AMOUNT, --本期期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --本期核销金额
       END_AMOUNT, --本期期末余额
       CURRENCY_CODE,
       ACTIVE,
       BALANCE_TIME,
       CREATE_TIME,
       business_date,
       payment_type, --付款方式
       aging_days, --账龄天数
       invoice_mark,--发票标记
       is_init --是否初始化
       )
      SELECT SYS_GUID() as ID,
             V_PERIOD as CHECK_OUT_PERIOD,
             CUSTOMER_CODE as CUSTOMER_CODE,
             CUSTOMER.NAME as customer_name,
             CHECK_OUT_ORG_CODE as CHECK_OUT_ORG_CODE,
             DEPT.name as CHECK_OUT_ORG_Name,
             FINORG.CODE as CHECK_OUT_Com_CODE,
             FINORG.name as CHECK_OUT_Com_name,
             SOURCE_BILL_NO as SOURCE_BILL_NO,
             SOURCE_BILL_TYPE as SOURCE_BILL_TYPE,
             STL_TYPE as STL_TYPE, -- 结算类型
             BILL_TYPE as BILL_TYPE, --结算单据子类型
             PRODUCT_CODE as PRODUCT_CODE,
             BEGIN_AMOUNT as BEGIN_AMOUNT,
             PRODUCE_AMOUNT as PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
             END_AMOUNT AS END_AMOUNT,
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             V_PERIOD_DATE as BALANCE_TIME, --起始记账期间
             V_SYSDATE as CREATE_TIME,
             business_date as business_date, --业务日期
             payment_type,
             (trunc(V_PERIOD_DATE) - trunc(business_date)) aging_days, --账龄(记账日期-业务日期)
             invoice_mark as invoice_mark,--发票标记
             pkg_stl_common.NO as is_init --是否初始化             
        FROM (SELECT CUSTOMER_CODE,
                     CHECK_OUT_ORG_CODE,
                     SOURCE_BILL_NO, --来源单号
                     SOURCE_BILL_TYPE, --来源单据类别
                     STL_TYPE, -- 结算类型
                     BILL_TYPE, --结算单据子类型
                     PRODUCT_CODE,
                     SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT, --本期期初
                     SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
                     SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
                     SUM(END_AMOUNT) AS END_AMOUNT, --本期核销金额
                     min(business_date) as business_date,
                     payment_type as payment_type,
                     invoice_mark as invoice_mark --发票标记
                FROM STV.T_STL_DAILY_BALANCE
               WHERE CHECK_OUT_PERIOD = P_PERIOD --核算期间              
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --来源单号
                        SOURCE_BILL_TYPE, --来源单据类别
                        STL_TYPE, -- 结算类型
                        payment_type,
                        BILL_TYPE,
                        invoice_mark --发票标记
                        ) bal --结算单据子类型
      --关联客户(固定客户、散客、代理)
        LEFT JOIN STV.MV_STV_CUSTOMER CUSTOMER
          ON CUSTOMER.CODE = BAL.CUSTOMER_CODE
      --关联部门 
      --按时间找到相应部门编码及公司，需要考虑一个组织在一个月内会多次修改的问题  
        left JOIN (select CODE,
                          name,
                          SUBSIDIARY_CODE,
                          row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                     from BSE.T_BAS_ORG
                   --该月第一天
                    where TRUNC(CREATE_TIME, 'MM') <= v_begin_date
                      AND trunc(MODIFY_TIME) >= v_end_date) DEPT
          ON DEPT.CODE = BAL.CHECK_OUT_ORG_CODE
         and DEPT.rn = 1
      
      --关联公司
        LEFT JOIN (select CODE,
                          name,
                          row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                     from BSE.T_BAS_FIN_ORG
                    where TRUNC(CREATE_TIME, 'MM') <= v_begin_date
                      AND trunc(MODIFY_TIME) >= v_end_date) FINORG
          ON FINORG.CODE = DEPT.SUBSIDIARY_CODE
         and FINORG.rn = 1;
  
    --提交
    commit;
    --需要关联客户
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        V_PERIOD_DATE,
                                        V_PERIOD_DATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_MONTH_DETAIL_END_AMOUNT',
                                        '核算月度明细期末余额');
      --返回失败标志
      RETURN FALSE;
    
  END;

  --计算客户月度余额
  FUNCTION FUNC_MONTH_CUSTOMER_END_AMOUNT(P_PERIOD STRING --结账期间
                                          ) RETURN BOOLEAN IS
    --系统时间
    V_SYSDATE DATE;
  
    --结账期间
    V_PERIOD STRING(8);
  
    --判断月份是否为第一个月
    V_IS_JANUARY BOOLEAN;
  
    --上一结账期间
    V_LAST_PERIOD STRING(8);
  
    --获得结账期间对应的日期
    V_PERIOD_DATE DATE;
  
  BEGIN
  
    --获得系统时间
    V_SYSDATE := SYSDATE();
  
    --获得当前期间
    V_PERIOD := P_PERIOD;
  
    --获得期间对应的日期
    V_PERIOD_DATE := TRUNC(LAST_DAY(TO_DATE(P_PERIOD, 'yyyymm')));
  
    --获得上一结账期间
    V_LAST_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_LAST_PERIOD(V_PERIOD_DATE);
  
    --获得当前期间的月份，是否为1月
    V_IS_JANUARY := PKG_STL_BALANCE_COMMON.FUNC_IS_JUNUARY(V_PERIOD);
  
    --如果是一月，年度发生额 = 本期发生额
    IF V_IS_JANUARY THEN
      INSERT INTO STV.T_STL_BALANCE
        (ID,
         BILL_TYPE,
         STL_TYPE,
         CHECK_OUT_PERIOD,
         CHECK_OUT_ORG_CODE,
         CHECK_OUT_ORG_NAME, --余额组织
         CHECK_OUT_COM_CODE, --余额公司
         CHECK_OUT_COM_NAME,
         CUSTOMER_CODE,
         CUSTOMER_NAME,
         PRODUCT_CODE,
         BEGIN_AMOUNT,
         PRODUCE_AMOUNT,
         WRITEOFF_AMOUNT,
         END_AMOUNT,
         TOTAL_PRODUCE_AMOUNT,
         TOTAL_WRITEOFF_AMOUNT,
         CREATE_TIME,
         BALANCE_TIME, -- 结账时间
         business_date, --业务日期
         payment_type, --付款方式
         aging_days, --账龄天数
         ACTIVE, --是否有效
         CURRENCY_CODE, --货币代码
         invoice_mark --发票标记
         )
      --增加客户名称、公司名称、部门编码
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE AS BILL_TYPE,
               BAL.STL_TYPE AS STL_TYPE,
               P_PERIOD AS CHECK_OUT_PERIOD,
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               bal.CHECK_OUT_ORG_NAME AS CHECK_OUT_ORG_NAME, --结账组织名称
               bal.CHECK_OUT_COM_CODE AS CHECK_OUT_COM_CODE, --子公司编码
               bal.CHECK_OUT_COM_Name AS CHECK_OUT_COM_NAME,
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               nvl(BAL.CUSTOMER_Name, PKG_stl_common.NULL_VALUE) AS CUSTOMER_NAME,
               BAL.PRODUCT_CODE AS PRODUCT_CODE,
               BAL.BEGIN_AMOUNT AS BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT AS PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT AS WRITEOFF_AMOUNT,
               BAL.END_AMOUNT AS END_AMOUNT,
               BAL.PRODUCE_AMOUNT AS TOTAL_PRODUCE_AMOUNT, --本期发生
               BAL.WRITEOFF_AMOUNT AS TOTAL_WRITEOFF_AMOUNT, --本期核销
               V_SYSDATE AS CREATE_TIME,
               V_PERIOD_DATE AS BALANCE_TIME,
               business_date as business_date, --业务日期
               payment_type as payment_type, --付款方式
               AGING_DAYS as AGING_DAYS, --账龄天数
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- 人民币
               invoice_mark --发票标记
          FROM (SELECT BILL_TYPE,
                       STL_TYPE,
                       D.CHECK_OUT_ORG_CODE,
                       D.CHECK_OUT_ORG_name,
                       D.CHECK_OUT_Com_CODE,
                       D.CHECK_OUT_Com_Name,
                       CUSTOMER_CODE,
                       CUSTOMER_Name,
                       PRODUCT_CODE,
                       SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT,
                       SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT,
                       SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT,
                       SUM(END_AMOUNT) AS END_AMOUNT,
                       min(business_date) as business_date,
                       max(d.AGING_DAYS) as AGING_DAYS, --账龄天数
                       payment_type,
                       d.invoice_mark as invoice_mark --发票标记
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = P_PERIOD --结账期间                
                 GROUP BY D.CHECK_OUT_ORG_CODE,
                          D.CHECK_OUT_ORG_name,
                          D.CHECK_OUT_Com_CODE,
                          D.CHECK_OUT_Com_Name,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          payment_type,
                          PRODUCT_CODE,
                          invoice_mark --发票标记
                          ) BAL;
    
      --提交
      commit;
    
    ELSE
      --等于上期期末 + 本期发生额
      INSERT INTO STV.T_STL_BALANCE
        (ID,
         BILL_TYPE,
         STL_TYPE,
         CHECK_OUT_PERIOD,
         
         CHECK_OUT_ORG_CODE,
         CHECK_OUT_ORG_NAME, --余额组织
         CHECK_OUT_COM_CODE, --余额公司
         CHECK_OUT_COM_NAME,
         CUSTOMER_CODE,
         CUSTOMER_NAME,
         
         PRODUCT_CODE,
         
         CREATE_TIME,
         BALANCE_TIME, -- 结账时间
         business_date, --业务日期
         payment_type, --付款方式
         aging_days, --账龄
         ACTIVE, --是否有效
         CURRENCY_CODE, --货币代码
         
         BEGIN_AMOUNT,
         PRODUCE_AMOUNT,
         WRITEOFF_AMOUNT,
         END_AMOUNT,
         TOTAL_PRODUCE_AMOUNT,
         TOTAL_WRITEOFF_AMOUNT,
         invoice_mark --发票标记
         )
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE,
               BAL.STL_TYPE,
               P_PERIOD,
               
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               BAL.CHECK_OUT_ORG_Name AS CHECK_OUT_ORG_NAME, --结账组织名称
               
               BAL.CHECK_OUT_Com_Code AS CHECK_OUT_COM_CODE,
               BAL.CHECK_OUT_Com_Name AS CHECK_OUT_COM_NAME,
               
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               BAL.CUSTOMER_Name AS CUSTOMER_NAME,
               
               BAL.PRODUCT_CODE as PRODUCT_CODE,
               
               V_SYSDATE                        as CREATE_TIME,
               V_PERIOD_DATE                    as BALANCE_TIME,
               BAL.business_date                as business_date, --业务日期
               BAL.payment_type                 as payment_type, --付款方式
               BAL.aging_days                   as aging_days, --账龄天数
               PKG_STL_COMMON.ACTIVE            as active,
               PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
               
               BAL.BEGIN_AMOUNT as BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT as PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
               BAL.END_AMOUNT as END_AMOUNT,
               BAL.PRODUCE_AMOUNT + nvl(B.TOTAL_PRODUCE_AMOUNT, 0) as TOTAL_PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT + nvl(B.TOTAL_WRITEOFF_AMOUNT, 0) as TOTAL_WRITEOFF_AMOUNT,
               BAL.invoice_mark --发票标记
          FROM (SELECT D.BILL_TYPE,
                       D.STL_TYPE,
                       payment_type as payment_type,
                       min(business_date) as business_date,
                       max(aging_days) as aging_days, --账龄天数
                       D.CHECK_OUT_ORG_CODE,
                       D.CHECK_OUT_ORG_name,
                       D.CHECK_OUT_Com_CODE,
                       D.CHECK_OUT_Com_Name,
                       D.CUSTOMER_CODE,
                       D.CUSTOMER_name,
                       D.PRODUCT_CODE,
                       SUM(nvl(D.BEGIN_AMOUNT, 0)) AS BEGIN_AMOUNT,
                       SUM(nvl(D.PRODUCE_AMOUNT, 0)) AS PRODUCE_AMOUNT,
                       SUM(nvl(D.WRITEOFF_AMOUNT, 0)) AS WRITEOFF_AMOUNT,
                       SUM(nvl(D.END_AMOUNT, 0)) AS END_AMOUNT,
                       d.invoice_mark as invoice_mark --发票标记
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = P_PERIOD --结账期间                   
                 GROUP BY D.CHECK_OUT_ORG_CODE,
                          D.CHECK_OUT_ORG_name,
                          D.CHECK_OUT_Com_CODE,
                          D.CHECK_OUT_Com_Name,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          PRODUCT_CODE,
                          payment_type,
                          invoice_mark --发票标记
                          ) BAL
        --关联上期期末
          left join STV.T_STL_BALANCE B
            on B.CUSTOMER_CODE = BAL.CUSTOMER_CODE --客户
              --组织
           AND B.CHECK_OUT_ORG_CODE = BAL.CHECK_OUT_ORG_CODE
              --结算类型
           AND B.STL_TYPE = BAL.STL_TYPE
              --单据子类型
           AND B.BILL_TYPE = BAL.BILL_TYPE
              --付款方式相同
           and b.payment_type = BAL.payment_type
              --产品类型相同
           and b.product_code = BAL.product_code
              --上期期末
           AND B.CHECK_OUT_PERIOD = V_LAST_PERIOD
           AND B.INVOICE_MARK = BAL.invoice_mark
           ;
    
      --提交
      commit;
    
    END IF;
    RETURN TRUE;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        V_SYSDATE,
                                        V_SYSDATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_MONTH_CUSTOMER_END_AMOUNT',
                                        '核算客户月度期末余额');
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_COMMON;
/
