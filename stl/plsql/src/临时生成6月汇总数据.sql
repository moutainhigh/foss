  truncate table  STV.T_STL_BALANCE_DETAIL;
  truncate table STV.T_STL_BALANCE;
--插入余额表
    INSERT INTO STV.T_STL_BALANCE_DETAIL
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       customer_name,
       CHECK_OUT_ORG_CODE,
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
       aging_days --账龄天数
       )
      SELECT SYS_GUID() as ID,
             '201306' as CHECK_OUT_PERIOD,
             CUSTOMER_CODE as CUSTOMER_CODE,
             CUSTOMER.NAME as customer_name,
             CHECK_OUT_ORG_CODE as CHECK_OUT_ORG_CODE,
             SOURCE_BILL_NO as SOURCE_BILL_NO,
             SOURCE_BILL_TYPE as SOURCE_BILL_TYPE,
             STL_TYPE as STL_TYPE, -- 结算类型
             BILL_TYPE as BILL_TYPE, --结算单据子类型
             PRODUCT_CODE as PRODUCT_CODE,
             BEGIN_AMOUNT as BEGIN_AMOUNT,
             PRODUCE_AMOUNT as PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
             BEGIN_AMOUNT + PRODUCE_AMOUNT - WRITEOFF_AMOUNT AS END_AMOUNT,
             'RMB' as CURRENCY_CODE,
             'Y' as ACTIVE,
             to_date('20130630','yyyymmdd') as BALANCE_TIME, --起始记账期间
             sysdate as CREATE_TIME,
             business_date as business_date, --业务日期
             payment_type,
             (trunc(to_date('20130630','yyyymmdd')) - trunc(business_date)) aging_days --账龄(记账日期-业务日期)             
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
                     payment_type as payment_type
                FROM STV.T_STL_DAILY_BALANCE
               WHERE CHECK_OUT_PERIOD = '201306' --核算期间
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --来源单号
                        SOURCE_BILL_TYPE, --来源单据类别
                        STL_TYPE, -- 结算类型
                        payment_type,
                        BILL_TYPE) bal --结算单据子类型
      --关联客户(固定客户、散客、代理)
        LEFT JOIN STV.MV_STV_CUSTOMER CUSTOMER
          ON CUSTOMER.CODE = BAL.CUSTOMER_CODE;
          
          commit;
          
          
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
         TOTAL_WRITEOFF_AMOUNT)
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE,
               BAL.STL_TYPE,
               '201306',
               
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               DEPT.NAME              AS CHECK_OUT_ORG_NAME, --结账组织名称
               
               FINORG.CODE AS CHECK_OUT_COM_CODE,
               FINORG.NAME AS CHECK_OUT_COM_NAME,
               
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               BAL.CUSTOMER_Name AS CUSTOMER_NAME,
               
               BAL.PRODUCT_CODE as PRODUCT_CODE,
               
               sysdate                        as CREATE_TIME,
               tO_date('20130630','yyyymmdd')                    as BALANCE_TIME,
               BAL.business_date                as business_date, --业务日期
               BAL.payment_type                 as payment_type, --付款方式
               BAL.aging_days                   as aging_days, --账龄天数
               'Y'            as active,
               'RMB' as CURRENCY_CODE, -- 人民币
               
               BAL.BEGIN_AMOUNT as BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT as PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
               BAL.END_AMOUNT as END_AMOUNT,
               BAL.PRODUCE_AMOUNT + nvl(B.TOTAL_PRODUCE_AMOUNT, 0) as TOTAL_PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT + nvl(B.TOTAL_WRITEOFF_AMOUNT, 0) as TOTAL_WRITEOFF_AMOUNT
          FROM (SELECT D.BILL_TYPE,
                       D.STL_TYPE,
                       payment_type as payment_type,
                       min(business_date) as business_date,
                       max(aging_days) as aging_days, --账龄天数
                       D.CHECK_OUT_ORG_CODE,
                       D.CUSTOMER_CODE,
                       D.CUSTOMER_name,
                       D.PRODUCT_CODE,
                       SUM(nvl(D.BEGIN_AMOUNT, 0)) AS BEGIN_AMOUNT,
                       SUM(nvl(D.PRODUCE_AMOUNT, 0)) AS PRODUCE_AMOUNT,
                       SUM(nvl(D.WRITEOFF_AMOUNT, 0)) AS WRITEOFF_AMOUNT,
                       SUM(nvl(D.END_AMOUNT, 0)) AS END_AMOUNT
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = '201306' --结账期间
                 GROUP BY CHECK_OUT_ORG_CODE,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          PRODUCT_CODE,
                          payment_type) BAL
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
           AND B.CHECK_OUT_PERIOD = '201305'
        --关联部门 
        --按时间找到相应部门编码及公司，需要考虑一个组织在一个月内会多次修改的问题
          left JOIN (select CODE,
                            name,
                            SUBSIDIARY_CODE,
                            row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                       from BSE.T_BAS_ORG
                      where  TRUNC(CREATE_TIME, 'MM') <= to_date('20130601','yyyymmdd')
                        AND trunc(MODIFY_TIME) >= to_date('20130630','yyyymmdd')) DEPT
            ON DEPT.CODE = BAL.CHECK_OUT_ORG_CODE
           and rn = 1
        
        --关联公司
          LEFT JOIN (select CODE,
                            name,
                            row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                       from BSE.T_BAS_FIN_ORG
                      where  TRUNC(CREATE_TIME, 'MM') <= to_date('20130601','yyyymmdd')
                        AND trunc(MODIFY_TIME) >= to_date('20130630','yyyymmdd')) FINORG
            ON FINORG.CODE = DEPT.SUBSIDIARY_CODE
           and DEPT.rn = 1;
           commit;
