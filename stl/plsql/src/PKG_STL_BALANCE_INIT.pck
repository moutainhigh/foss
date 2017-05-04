CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_INIT IS
  --结账初始化代码
  --作者：黄小波
  --创建时间：2013-06-21
  --初始化期初

  --初始化预收
  function FUNC_INIT_DEPOSIT_BILL(P_ORG_CODE     STRING, --组织编码
                                  P_BEGIN_DATE   DATE, --起始日期
                                  P_PERIOD       STRING, --结账期间
                                  P_BALANCE_TYPE STRING -- 结算类型
                                  ) return boolean;

  --初始化应收未签收（预收中转）
  function FUNC_INIT_DEPOSIT_TF(P_ORG_CODE     STRING, --组织编码
                                P_BEGIN_DATE   DATE, --起始日期
                                P_PERIOD       STRING, --结账期间
                                P_BALANCE_TYPE STRING -- 结算类型
                                ) return boolean;

  --初始化应收已签收
  function FUNC_INIT_receivable(P_ORG_CODE     STRING, --组织编码
                                P_BEGIN_DATE   DATE, --起始日期
                                P_PERIOD       STRING, --结账期间
                                P_BALANCE_TYPE STRING -- 结算类型
                                ) return boolean;

  --初始化应付
  function FUNC_INIT_payable(P_ORG_CODE     STRING, --组织编码
                             P_BEGIN_DATE   DATE, --起始日期
                             P_PERIOD       STRING, --结账期间
                             P_BALANCE_TYPE STRING -- 结算类型
                             ) return boolean;

end PKG_STL_BALANCE_INIT;
/
CREATE OR REPLACE PACKAGE body PKG_STL_BALANCE_INIT IS
  --结账初始化代码
  --作者：黄小波
  --创建时间：2013-06-21
  --初始化期初

  --初始化预收
  function FUNC_INIT_DEPOSIT_BILL(P_ORG_CODE     STRING, --组织编码
                                  P_BEGIN_DATE   DATE, --起始日期
                                  P_PERIOD       STRING, --结账期间
                                  P_BALANCE_TYPE STRING -- 结算类型
                                  ) return boolean is
  begin
    --生成初始化的预收
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --本期发生金额
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.Generating_Org_Code,
             --DDW 2013-06-18 给预收明细账添加来源单号
             BILL.DEPOSIT_RECEIVED_NO  AS SOURCE_BILL_NO, --来源单号
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE, --来源单据类别
             --结算类型
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --单据子类型
             --DDW 2013-06-18 给预收明细账添加产品类型
             BILL.TRANSPORT_TYPE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             0 AS WRITEOFF_AMOUNT, --本期核销金额
             0 AS END_AMOUNT, --本期期末余额
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --起始日期作为结账
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED BILL
       WHERE
      --查询初始化的预收
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --应收组织
       AND BILL.generating_org_Code = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                bill.generating_org_Code,
                BILL.DEPOSIT_RECEIVED_NO,
                BILL.TRANSPORT_TYPE,
                BILL_TYPE, -- 单据类型
                payment_type --付款方式
      HAVING SUM(AMOUNT) != 0;
  
    --生成初始化的核销(作为主方)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 给预收结账添加产品类型和来源单号
             B.TRANSPORT_TYPE AS PRODUCT_CODE, --产品类型
             B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.id = SBW.BEGIN_Id --辅助单号
       WHERE sbw.ORG_CODE = B.Generating_Org_Code
         and b.generating_org_code = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                b.payment_type,
                b.DEPOSIT_RECEIVED_NO,
                b.TRANSPORT_TYPE,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --预收(作为辅助方)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 给预收结账添加产品类型和来源单号
             B.TRANSPORT_TYPE AS PRODUCT_CODE, --产品类型
             B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.id = SBW.END_Id --辅助单号
       WHERE sbw.ORG_CODE = B.Generating_Org_Code
         and b.generating_org_code = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                b.payment_type,
                b.DEPOSIT_RECEIVED_NO,
                b.TRANSPORT_TYPE,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --生成初始化的期末
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- 结算类型
             BILL_TYPE, --结算单据子类型
             SOURCE_BILL_NO, --来源单号
             SOURCE_BILL_TYPE, --来源单据类别
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --本期期末
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --计算类型
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --核算组织
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE, --来源单据类别
                payment_type, --付款方式
                STL_TYPE, -- 结算类型
                BILL_TYPE; --结算单据子类型
    --默认返回true
    return true;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_banlance_deposit.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-预收期末结账核算初始化余额');
    
      --返回成功标志
      RETURN FALSE;
  end;

  --初始化应收未签收（预收中转）
  function FUNC_INIT_DEPOSIT_TF(P_ORG_CODE     STRING, --组织编码
                                P_BEGIN_DATE   DATE, --起始日期
                                P_PERIOD       STRING, --结账期间
                                P_BALANCE_TYPE STRING -- 结算类型
                                ) return boolean is
  
  begin
  
    --生成初始化的应收
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --本期发生金额
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.RECEIVABLE_ORG_CODE,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别 
             --结算类型
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --单据子类型
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             0 AS WRITEOFF_AMOUNT, --本期核销金额
             0 AS END_AMOUNT, --本期期末余额
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --起始日期作为结账
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --目前按照记账日期
       WHERE
      --查询初始化的应收单
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --应收组织
       AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                RECEIVABLE_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- 单据类型
                payment_type, --付款方式
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE --来源单据类别
      HAVING SUM(AMOUNT) != 0;
    --生成初始化的核销（作为主方）
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.BEGIN_Id --辅助单号
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
    --作为辅助方
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.END_Id --辅助单号
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --生成初始化的期末
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- 结算类型
             BILL_TYPE, --结算单据子类型             
             SOURCE_BILL_NO, --来源单号
             SOURCE_BILL_TYPE, --来源单据类别
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --本期期末
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --计算类型
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --核算组织 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE, --来源单据类别
                payment_type, --付款方式
                STL_TYPE, -- 结算类型
                BILL_TYPE; --结算单据子类型 
    --默认返回true
    return true;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-应预收期末结账核算初始化期初');
    
      --返回成功标志
      RETURN FALSE;
  end;

  --初始化应收
  function FUNC_INIT_receivable(P_ORG_CODE     STRING, --组织编码
                                P_BEGIN_DATE   DATE, --起始日期
                                P_PERIOD       STRING, --结账期间
                                P_BALANCE_TYPE STRING -- 结算类型
                                ) return boolean is
  
  begin
  
    --生成初始化的应收
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --本期发生金额
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.RECEIVABLE_ORG_CODE,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别 
             --结算类型
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --单据子类型
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             0 AS WRITEOFF_AMOUNT, --本期核销金额
             0 AS END_AMOUNT, --本期期末余额
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --起始日期作为结账
             SYSDATE AS CREATE_TIME,
             business_date as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --目前按照记账日期
       WHERE
      --查询初始化的应收单
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --应收组织
       AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                RECEIVABLE_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- 单据类型
                payment_type, --付款方式
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE --来源单据类别
      HAVING SUM(AMOUNT) != 0;
    --生成初始化的核销
    --作为主方核销
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.business_date as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.Id = SBW.Begin_Id --辅助单号
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
    --作为辅助方核销
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.business_date as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.End_Id --辅助单号
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- 应收单类型
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --生成初始化的期末
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- 结算类型
             BILL_TYPE, --结算单据子类型             
             SOURCE_BILL_NO, --来源单号
             SOURCE_BILL_TYPE, --来源单据类别
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --本期期末
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             business_date as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --计算类型
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --核算组织 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE, --来源单据类别
                payment_type, --付款方式
                STL_TYPE, -- 结算类型
                BILL_TYPE; --结算单据子类型 
    --默认返回true
    return true;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-应收期末结账核算初始化期初');
    
      --返回成功标志
      RETURN FALSE;
  end;

  --初始化应付
  function FUNC_INIT_payable(P_ORG_CODE     STRING, --组织编码
                             P_BEGIN_DATE   DATE, --起始日期
                             P_PERIOD       STRING, --结账期间
                             P_BALANCE_TYPE STRING -- 结算类型
                             ) return boolean is
  
  begin
  
    --生成初始化的应付
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --本期发生金额
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.payable_org_code,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别 
             --结算类型
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --单据子类型
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             0 AS WRITEOFF_AMOUNT, --本期核销金额
             0 AS END_AMOUNT, --本期期末余额
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --起始日期作为结账
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_Payable BILL
      --目前按照记账日期
       WHERE
      --查询初始化的应付单
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --应付组织
       AND BILL.payable_org_code = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                payable_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- 单据类型
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE; --来源单据类别
    --生成初始化的核销(作为主方)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应付单
       INNER JOIN STL.T_STL_BILL_Payable B
          ON B.Payable_No = SBW.BEGIN_No --辅助单号
       WHERE sbw.ORG_CODE = B.Payable_ORG_CODE
         and b.Payable_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                B.BILL_TYPE; -- 单据类型
  
    --核销(作为辅助方)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应付单
       INNER JOIN STL.T_STL_BILL_Payable B
          ON B.Payable_No = SBW.END_No --辅助单号
       WHERE sbw.ORG_CODE = B.Payable_ORG_CODE
         and b.Payable_ORG_CODE = P_ORG_CODE
            --初始化的单据
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                B.BILL_TYPE; -- 单据类型
  
    --生成核销余额
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- 结算类型
             BILL_TYPE, --结算单据子类型             
             SOURCE_BILL_NO, --来源单号
             SOURCE_BILL_TYPE, --来源单据类别
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --本期期初
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --本期发生额
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --本期核销金额
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --本期期末
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- 人民币
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --计算类型
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --核算组织 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --来源单号
                SOURCE_BILL_TYPE, --来源单据类别
                payment_type, --付款方式
                STL_TYPE, -- 结算类型
                BILL_TYPE; --结算单据子类型 
    --默认返回true
    return true;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-应付期末结账核算初始化期初');
    
      --返回成功标志
      RETURN FALSE;
  end;

end PKG_STL_BALANCE_INIT;
/
