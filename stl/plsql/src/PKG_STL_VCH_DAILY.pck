CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 每日凭证入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每日凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                              P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                              P_END_DATE   DATE -- 结束时间 2012-12-22
                              ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 每日凭证入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每日凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                              P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                              P_END_DATE   DATE -- 结束时间 2012-12-22
                              ) RETURN BOOLEAN IS
    V_RESULT BOOLEAN;
  
  BEGIN
  
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVD T WHERE T.PERIOD = :P_PERIOD'
      USING P_PERIOD;
  
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVS T WHERE T.PERIOD = :P_PERIOD'
      USING P_PERIOD;
  
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVD_RETURN_COD T WHERE T.PERIOD = :P_PERIOD'
      USING P_PERIOD;
  
    V_RESULT := PKG_STL_VCH_DAILY_AC.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                               P_BEGIN_DATE,
                                                               P_END_DATE);
    COMMIT;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_AIR_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_AIR_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_AIR_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                     P_BEGIN_DATE,
                                                                     P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_AIR_PR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                     P_BEGIN_DATE,
                                                                     P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_APT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_BD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_BDR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_CLAIM.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                    P_BEGIN_DATE,
                                                                    P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_CN.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_CUST_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_DE.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_EX.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_OR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_OTH.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_PL_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_PL_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                    P_BEGIN_DATE,
                                                                    P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_POD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_RD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_SF.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                 P_BEGIN_DATE,
                                                                 P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_UPD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_UR_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_UR_ORIG.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    --退代收货款报表
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_RETURN_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                         P_BEGIN_DATE,
                                                                         P_END_DATE);
      COMMIT;
    END IF;
  
    --快递新加列
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_BDR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                        P_BEGIN_DATE,
                                                                        P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_OTH.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_PR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_UR_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                           P_BEGIN_DATE,
                                                                           P_END_DATE);
      COMMIT;
    END IF;
  
    --营业部核销月报表明细
    IF V_RESULT THEN
      V_RESULT := PKG_STL_VCH_DAILY_LAND_WO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                      P_BEGIN_DATE,
                                                                      P_END_DATE);
      COMMIT;
    END IF;
  
    --计算日汇总
    INSERT INTO STV.T_STL_DVS
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.BUSINESS_CASE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             SUM(T.AMOUNT),
             SUM(T.AMOUNT_FRT),
             SUM(T.AMOUNT_PUP),
             SUM(T.AMOUNT_DEL),
             SUM(T.AMOUNT_PKG),
             SUM(T.AMOUNT_DV),
             SUM(T.AMOUNT_COD),
             SUM(T.AMOUNT_OT),
             T.PRODUCT_CODE
        FROM STV.T_STL_DVD T
       WHERE T.PERIOD = P_PERIOD
       GROUP BY T.BUSINESS_CASE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME,
                T.PRODUCT_CODE;
  
    COMMIT;
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY',
                                        '凭证生成日处理程序异常:' || SQLERRM);
    
      --返回失败标志
      RETURN FALSE;
    
  END FUNC_VOUCHER_DAILY;

END PKG_STL_VCH_DAILY;
/
