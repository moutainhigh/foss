CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_MONTHLY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 每月凭证入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每月凭证汇总入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                P_END_DATE   DATE -- 结束时间 2012-12-22
                                ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 始发月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 到达月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 偏线月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 空运月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 始发到达往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 始发偏线往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 始发空运往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 退代收货款报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
                                   P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                   P_END_DATE   DATE -- 结束时间 2012-12-22
                                   ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 落地配月报表
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 落地配往来月报表
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 营业部核销月报表
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_MONTHLY;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_MONTHLY IS

  -- ==============================================================
  -- Author  : ZHUWEI
  -- Created : 2012-12-07 14:29:42
  -- Purpose : 每月凭证入口
  -- ==============================================================

  -----------------------------------------------------------------
  -- 每月凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- 开始时间 2012-12-21(包含)
                                P_END_DATE   DATE -- 结束时间 2012-12-22(不包含)
                                ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_START_PERIOD VARCHAR2(50); --开始期间
    V_END_PERIOD   VARCHAR2(50); --结束期间

  BEGIN

    --开始期间和结束期间
    V_START_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    -- 删除需要重新统计的月汇总报表
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVS T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
      USING V_START_PERIOD, V_END_PERIOD;

    --统计月汇总数据
    INSERT INTO STV.T_STL_MVS
      (ID,
       PERIOD,
       BUSINESS_CASE,
       PRODUCT_CODE,
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
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME)
      SELECT SYS_GUID(),
             T.PERIOD,
             T.BUSINESS_CASE,
             T.PRODUCT_CODE,
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
             P_BEGIN_DATE,
             P_END_DATE
        FROM STV.T_STL_DVS T
       WHERE T.PERIOD >= V_START_PERIOD
         AND T.PERIOD < V_END_PERIOD
       GROUP BY T.PERIOD,
                T.BUSINESS_CASE,
                T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_START_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY',
                                        '凭证生成月汇总程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_MONTHLY;

  -----------------------------------------------------------------
  -- 始发月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_RFO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_RFO
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UPD_DEST_RCV_WO_FRT,
       UPD_DEST_RCV_WO_PUP,
       UPD_DEST_RCV_WO_DEL,
       UPD_DEST_RCV_WO_PKG,
       UPD_DEST_RCV_WO_DV,
       UPD_DEST_RCV_WO_COD,
       UPD_DEST_RCV_WO_OT,
       UPD_DEST_RCV_NWO_FRT,
       UPD_DEST_RCV_NWO_PUP,
       UPD_DEST_RCV_NWO_DEL,
       UPD_DEST_RCV_NWO_PKG,
       UPD_DEST_RCV_NWO_DV,
       UPD_DEST_RCV_NWO_COD,
       UPD_DEST_RCV_NWO_OT,
       CLAIM_ORIG_WO_INCOME,
       CLAIM_ORIG_COST,
       CLAIM_WO_ORIG_RCV_POD,
       CLAIM_WO_ORIG_RCV_NPOD,
       CLAIM_ORIG_PAY_APPLY,
       CLAIM_DEST_WO_INCOME,
       CLAIM_WO_DEST_RCV_POD,
       CLAIM_WO_DEST_RCV_NPOD,
       SF_PAY_APPLY,
       COD_DEST_RCV_POD,
       COD_DEST_RCV_NPOD,
       COD_ORIG_RCV_POD,
       COD_ORIG_RCV_NPOD,
       CUST_DR_CH,
       CUST_DR_CD,
       CUST_DR_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_POD,
       CUST_DR_ORIG_RCV_NPOD,
       CUST_DR_ORIG_RCV_POD,
       CUST_DR_ORIG_PAY_APPLY,
       EX_ORIG_RCV_POD,
       EX_DEST_RCV_POD,
       BD_ORIG_RCV_POD,
       BD_DEST_RCV_POD,
       OR_CH_AC,
       OR_CH_SI,
       OR_CH_OPAY,
       OR_CH_OTHER,
       OR_CH_MBI,
       OR_CD_AC,
       OR_CD_BANK_IT,
       OR_CD_OPAY,
       OR_CD_OTHER,
       OR_CD_MBI,
       OR_RCV_MBI,
       OR_RCV_WO_UR_CH,
       OR_RCV_WO_UR_CD,
       OR_RCV_WO_COD_PAY,
       OR_RCV_WO_CLAIM_PAY,
       OR_RCV_WO_CUST_DR,
       OR_RCV_WO_BD_DEBT,
       OR_RCV_WO_BD_INCOME,
       AC_CTDTOL_NWO,
       AC_CTDTOL_WO,
       AC_CHCD,
       RD_ORIG_WO_INCOME,
       RD_ORIG_COST,
       RD_ORIG_PAY_APPLY,
       RD_DEST_WO_INCOME,
       CN_ORIG_PAY_APPLY,
       PL_COST_WO_DEST_RCV_POD,
       PL_COST_WO_DEST_RCV_NPOD,
       PL_DR_WO_DEST_RCV_POD,
       PL_DR_WO_DEST_RCV_NPOD,
       AIR_DR_DEST_RCV_POD,
       AIR_DR_DEST_RCV_NPOD,
       AIR_PR_AGENCY_WO_DEST_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
       AIR_PR_OT_WO_DEST_RCV_POD,
       AIR_PR_OTH_WO_DEST_RCV_NPOD,
       DE_CH,
       DE_CD,
       UR_ORIG_CH_NPOD,
       UR_ORIG_CD_NPOD,
       UR_ORIG_CH_POD,
       UR_ORIG_CD_POD,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       POD_CASH_COLLECTED_FRT,
       POD_CASH_COLLECTED_PUP,
       POD_CASH_COLLECTED_DEL,
       POD_CASH_COLLECTED_PKG,
       POD_CASH_COLLECTED_DV,
       POD_CASH_COLLECTED_COD,
       POD_CASH_COLLECTED_OT,
       POD_ORIG_RCV_WO_FRT,
       POD_ORIG_RCV_WO_PUP,
       POD_ORIG_RCV_WO_DEL,
       POD_ORIG_RCV_WO_PKG,
       POD_ORIG_RCV_WO_DV,
       POD_ORIG_RCV_WO_COD,
       POD_ORIG_RCV_WO_OT,
       POD_ORIG_RCV_NWO_FRT,
       POD_ORIG_RCV_NWO_PUP,
       POD_ORIG_RCV_NWO_DEL,
       POD_ORIG_RCV_NWO_PKG,
       POD_ORIG_RCV_NWO_DV,
       POD_ORIG_RCV_NWO_COD,
       POD_ORIG_RCV_NWO_OT,
       POD_DEST_RCV_WO_FRT,
       POD_DEST_RCV_WO_PUP,
       POD_DEST_RCV_WO_DEL,
       POD_DEST_RCV_WO_PKG,
       POD_DEST_RCV_WO_DV,
       POD_DEST_RCV_WO_COD,
       POD_DEST_RCV_WO_OT,
       POD_DEST_RCV_NWO_FRT,
       POD_DEST_RCV_NWO_PUP,
       POD_DEST_RCV_NWO_DEL,
       POD_DEST_RCV_NWO_PKG,
       POD_DEST_RCV_NWO_DV,
       POD_DEST_RCV_NWO_COD,
       POD_DEST_RCV_NWO_OT,
       UPD_CASH_COLLECTED_FRT,
       UPD_CASH_COLLECTED_PUP,
       UPD_CASH_COLLECTED_DEL,
       UPD_CASH_COLLECTED_PKG,
       UPD_CASH_COLLECTED_DV,
       UPD_CASH_COLLECTED_COD,
       UPD_CASH_COLLECTED_OT,
       UPD_ORIG_RCV_WO_FRT,
       UPD_ORIG_RCV_WO_PUP,
       UPD_ORIG_RCV_WO_DEL,
       UPD_ORIG_RCV_WO_PKG,
       UPD_ORIG_RCV_WO_DV,
       UPD_ORIG_RCV_WO_COD,
       UPD_ORIG_RCV_WO_OT,
       UPD_ORIG_RCV_NWO_FRT,
       UPD_ORIG_RCV_NWO_PUP,
       UPD_ORIG_RCV_NWO_DEL,
       UPD_ORIG_RCV_NWO_PKG,
       UPD_ORIG_RCV_NWO_DV,
       UPD_ORIG_RCV_NWO_COD,
       UPD_ORIG_RCV_NWO_OT,
       RD_WO_ORIG_RCV_POD,
       RD_WO_ORIG_RCV_NPOD,
       RD_WO_DEST_RCV_POD,
       RD_WO_DEST_RCV_NPOD)
      SELECT SYS_GUID(),
             P_PERIOD,
             PRODUCT_CODE,
             CUSTOMER_CODE,
             CUSTOMER_NAME,
             CUSTOMER_TYPE,
             ORIG_ORG_CODE,
             ORIG_ORG_NAME,
             DEST_ORG_CODE,
             DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_DEST_RCV_WO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_DEST_RCV_NWO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_WO_INCOME, --理赔冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_COST, --理赔入成本
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_RCV_POD, --理赔冲始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_RCV_NPOD, --理赔冲始发应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_ORIG_PAY_APPLY, --理赔付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --理赔冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --理赔冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --理赔冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.SF_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) SF_PAY_APPLY, --装卸费付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --应付代收货款冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --应付代收货款冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --应付代收货款冲应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --应付代收货款冲应收始发运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_CH, --预收客户现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_CD, --预收客户银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --预收客户冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD, --预收客户冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_RCV_NPOD, --预收客户冲应收始发运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_RCV_POD, --预收客户冲应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_PAY_APPLY, --始发退预收付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.EX_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) EX_ORIG_RCV_POD, --应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.EX_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) EX_DEST_RCV_POD, --应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BD_ORIG_RCV_POD, --坏账冲应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BD_DEST_RCV_POD, --坏账冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_AC,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_AC, --小票现金之事故赔款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_SI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_SI, --小票现金之变卖废品收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_OPAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_OPAY, --小票现金之客户多付运费或盘点长款金额
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_OTHER,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_OTHER, --小票现金之其他
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CH_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CH_MBI, --小票现金主营业务收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_AC,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_AC, --小票银行之事故赔款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_BANK_IT,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_BANK_IT, --小票银行之收银员卡利息
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_OPAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_OPAY, --小票银行之客户多付运费或盘点长款金额
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_OTHER,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_OTHER, --小票银行之其他
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_CD_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_CD_MBI, --小票银行主营业务收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_MBI,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_MBI, --小票应收主营业务收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_UR_CH, --还款现金冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_UR_CD, --还款银行冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_COD_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_COD_PAY, --应付代收货款冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_CLAIM_PAY, --应付理赔冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_CUST_DR,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_CUST_DR, --预收客户冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_BD_DEBT,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_BD_DEBT, --坏账之保险理赔冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_BD_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_BD_INCOME, --坏账之坏账损失冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CTDTOL_NWO,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CTDTOL_NWO, --开单为月结临时欠款网上支付未核销
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CTDTOL_WO,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CTDTOL_WO, --开单为月结临时欠款网上支付已核销
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AC_CHCD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AC_CHCD, --开单为现金银行卡
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_WO_INCOME, --退运费冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_COST, --退运费入成本
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_ORIG_PAY_APPLY, --退运费付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --退运费冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CN_ORIG_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CN_ORIG_PAY_APPLY, --始发服务补救付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --应付偏线代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --应付偏线代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --预收偏线代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD, --预收偏线代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --预收空运代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --预收空运代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            --新增小件业务
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.DE_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) DE_CH, --开单现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.DE_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) DE_CD, --开单银行卡
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_ORIG_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_ORIG_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_CASH_COLLECTED_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_CASH_COLLECTED_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_CASH_COLLECTED_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_CASH_COLLECTED_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_CASH_COLLECTED_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_CASH_COLLECTED_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_CASH_COLLECTED_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_ORIG_RCV_WO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_ORIG_RCV_NWO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_DEST_RCV_WO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_DEST_RCV_WO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_DEST_RCV_WO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_DEST_RCV_WO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_DEST_RCV_WO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_DEST_RCV_WO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_DEST_RCV_WO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) POD_DEST_RCV_NWO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_CASH_COLLECTED_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_ORIG_RCV_WO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_FRT, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_FRT, --公布价运费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PUP, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_PUP, --接货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DEL, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_DEL, --送货费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_PKG, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_PKG, --包装费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_DV, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_DV, --保价费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_COD, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_COD, --代收货款手续费
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
                            NVL(AMOUNT_OT, 0),
                            0)),
                 0) UPD_ORIG_RCV_NWO_OT, --其他费用
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_ORIG_RCV_POD, --退运费_出发部门申请_退运费冲始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_ORIG_RCV_NPOD, --退运费_出发部门申请_退运费冲始发应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --退运费_到达部门申请_退运费冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD --退运费_到达部门申请_退运费冲到达应收未签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.POD_DEST_RCV_NWO,
              PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED,
              PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO,
              PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO,
              PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO,
              PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO,
              PKG_STL_VCH_COMMON.POD_CASH_COLLECTED,
              PKG_STL_VCH_COMMON.POD_ORIG_RCV_WO,
              PKG_STL_VCH_COMMON.POD_ORIG_RCV_NWO,
              PKG_STL_VCH_COMMON.POD_DEST_RCV_WO,
              PKG_STL_VCH_COMMON.CLAIM_ORIG_WO_INCOME,
              PKG_STL_VCH_COMMON.CLAIM_ORIG_COST,
              PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_NPOD,
              PKG_STL_VCH_COMMON.CLAIM_ORIG_PAY_APPLY,
              PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
              PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.SF_PAY_APPLY,
              PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_CH,
              PKG_STL_VCH_COMMON.CUST_DR_CD,
              PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.CUST_DR_ORIG_PAY_APPLY,
              PKG_STL_VCH_COMMON.EX_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.EX_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.BD_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.BD_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.OR_CH_AC,
              PKG_STL_VCH_COMMON.OR_CH_SI,
              PKG_STL_VCH_COMMON.OR_CH_OPAY,
              PKG_STL_VCH_COMMON.OR_CH_OTHER,
              PKG_STL_VCH_COMMON.OR_CH_MBI,
              PKG_STL_VCH_COMMON.OR_CD_AC,
              PKG_STL_VCH_COMMON.OR_CD_BANK_IT,
              PKG_STL_VCH_COMMON.OR_CD_OPAY,
              PKG_STL_VCH_COMMON.OR_CD_OTHER,
              PKG_STL_VCH_COMMON.OR_CD_MBI,
              PKG_STL_VCH_COMMON.OR_RCV_MBI,
              PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CH,
              PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CD,
              PKG_STL_VCH_COMMON.OR_RCV_WO_COD_PAY,
              PKG_STL_VCH_COMMON.OR_RCV_WO_CLAIM_PAY,
              PKG_STL_VCH_COMMON.OR_RCV_WO_CUST_DR,
              PKG_STL_VCH_COMMON.OR_RCV_WO_BD_DEBT,
              PKG_STL_VCH_COMMON.OR_RCV_WO_BD_INCOME,
              PKG_STL_VCH_COMMON.AC_CTDTOL_NWO,
              PKG_STL_VCH_COMMON.AC_CTDTOL_WO,
              PKG_STL_VCH_COMMON.AC_CHCD,
              PKG_STL_VCH_COMMON.RD_ORIG_WO_INCOME,
              PKG_STL_VCH_COMMON.RD_ORIG_COST,
              PKG_STL_VCH_COMMON.RD_ORIG_PAY_APPLY,
              PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
              PKG_STL_VCH_COMMON.CN_ORIG_PAY_APPLY,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.DE_CH,
              PKG_STL_VCH_COMMON.DE_CD,
              PKG_STL_VCH_COMMON.UR_ORIG_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_ORIG_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_ORIG_CH_POD,
              PKG_STL_VCH_COMMON.UR_ORIG_CD_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              --新增落地配场景
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
              PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_NPOD,
              PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD)
       GROUP BY PRODUCT_CODE,
                CUSTOMER_CODE,
                CUSTOMER_NAME,
                CUSTOMER_TYPE,
                ORIG_ORG_CODE,
                ORIG_ORG_NAME,
                DEST_ORG_CODE,
                DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFO',
                                        '凭证生成始发报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_RFO;

  -----------------------------------------------------------------
  -- 到达月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_RFD T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_RFD
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       CLAIM_DEST_WO_INCOME,
       CLAIM_DEST_COST,
       CLAIM_DEST_PAY_APPLY,
       CLAIM_WO_DEST_RCV_POD,
       CLAIM_WO_DEST_RCV_NPOD,
       COD_UR_CH_NPOD,
       COD_UR_CD_NPOD,
       RD_DEST_WO_INCOME,
       RD_DEST_COST,
       RD_DEST_PAY_APPLY,
       CN_DEST_PAY_APPLY,
       RD_WO_DEST_RCV_POD,
       RD_WO_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_POD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --理赔冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_COST, --理赔入成本
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_PAY_APPLY, --理赔付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --理赔冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --理赔冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --还款代收货款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --还款代收货款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --退运费冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_COST,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_COST, --退运费入成本
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_PAY_APPLY, --退运费付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CN_DEST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CN_DEST_PAY_APPLY, --到达服务补救付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --退运费_到达部门申请_退运费冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --退运费_到达部门申请_退运费冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --预收客户冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --预收客户冲应收到付运费已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --精准卡航
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --精准城运
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --精准汽运(长途)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --精准汽运(短途)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --整车
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --经济快递
                                )
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
              PKG_STL_VCH_COMMON.CLAIM_DEST_COST,
              PKG_STL_VCH_COMMON.CLAIM_DEST_PAY_APPLY,
              PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
              PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
              PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
              PKG_STL_VCH_COMMON.RD_DEST_COST,
              PKG_STL_VCH_COMMON.RD_DEST_PAY_APPLY,
              PKG_STL_VCH_COMMON.CN_DEST_PAY_APPLY,
              PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFD',
                                        '凭证生成到达月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_RFD;

  -----------------------------------------------------------------
  -- 偏线月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_PLR T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_PLR
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       PL_COST_WO_DEST_RCV_POD,
       PL_COST_WO_DEST_RCV_NPOD,
       PL_COST_VECH,
       PL_COST_CONFIRM,
       PL_COST_NOT_CONFIRM,
       PL_COST_PAY_APPLY,
       PL_DR_WO_DEST_RCV_POD,
       PL_DR_WO_DEST_RCV_NPOD,
       PL_DR_CH,
       PL_DR_CD,
       PL_DR_PAY_APPLY)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --应付偏线代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --应付偏线代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_VECH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_VECH, --外发单录入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_CONFIRM, --偏线代理成本确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_NOT_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_NOT_CONFIRM, --偏线代理成本反确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_PAY_APPLY, --偏线代理成本付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --预收偏线代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD, --预收偏线代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_CH, --预收偏线代理现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_CD, --预收偏线代理银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_PAY_APPLY --偏线退预收付款申请
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- 偏线
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.PL_COST_VECH,
              PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
              PKG_STL_VCH_COMMON.PL_COST_NOT_CONFIRM,
              PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.PL_DR_CH,
              PKG_STL_VCH_COMMON.PL_DR_CD,
              PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLR',
                                        '凭证生成偏线月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_PLR;

  -----------------------------------------------------------------
  -- 空运月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFR(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_AFR T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_AFR
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       AIR_COD_CD_NPOD,
       AIR_COD_WO_AGENCY_PAY_POD,
       AIR_COD_WO_OTH_PAY_COD,
       AIR_COD_WO_AGENCY_PAY_NPOD,
       AIR_COD_WO_OTH_NPOD,
       APT_COM,
       APT_WO_COM_PAY,
       APT_WO_OTH_PAY,
       BDR_WO_OTH_RCV,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       AIR_COST_COM_CONFIRM,
       AIR_COST_ORIG_AGENCY_CFM,
       AIR_COST_DEST_AGENCY_GEN,
       AIR_COST_DEST_AGENCY_CFM,
       AIR_COST_DEST_AGENCY_NCFM,
       AIR_COST_PAY_APPLY,
       OTH_ENTRY,
       OTH_RCV_CH,
       OTH_RCV_CD,
       AIR_DR_DEST_RCV_POD,
       AIR_DR_DEST_RCV_NPOD,
       AIR_DR_CH,
       AIR_DR_CD,
       AIR_DR_WO_OTHER_RCV,
       AIR_DR_WO_COD_RCV_POD,
       AIR_DR_WO_COD_RCV_NPOD,
       AIR_DR_PAY_APPLY,
       AIR_PR_AGENCY_WO_DEST_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
       AIR_PR_OT_WO_DEST_RCV_POD,
       AIR_PR_OTH_WO_DEST_RCV_NPOD,
       AIR_PR_OTH_WO_OTH_RCV,
       AIR_COD_CH_POD,
       AIR_COD_CD_POD,
       AIR_COD_POD_WO_COD,
       AIR_COD_NPOD_WO_COD,
       AIR_COD_CH_NPOD,
       AIR_COST_OTHER_CONFIRM)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_NPOD, --空运还款代收货款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --空运到达代理应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD, --空运其他应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_NPOD, --空运到达代理应付冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_NPOD, --空运其他应付冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_COM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_COM, --预付航空公司
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_WO_COM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_WO_COM_PAY, --预付冲应付航空公司
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) APT_WO_OTH_PAY, --预付冲其他应付
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.BDR_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) BDR_WO_OTH_RCV, --坏账冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_COM_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_COM_CONFIRM, --空运航空公司运费确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_ORIG_AGENCY_CFM, --空运出发代理应付确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_GEN,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_GEN, --空运到达代理应付生成
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_CFM, --空运到达代理应付成本确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_NCFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_DEST_AGENCY_NCFM, --空运到达代理应付成本反确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_PAY_APPLY, --空运成本付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_ENTRY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_ENTRY, --空运其他应收录入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_RCV_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_RCV_CH, --还款空运其他应收现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OTH_RCV_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OTH_RCV_CD, --还款空运其他应收银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --预收空运代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --预收空运代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_CH, --预收空运代理现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_CD, --预收空运代理银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_OTHER_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_OTHER_RCV, --预收空运代理冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_NPOD, --预收空运代理冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_PAY_APPLY, --空运退预收付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_OTH_RCV, --其他应付冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --空运还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --空运还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --空运签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --空运反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_NPOD, --空运还款代收货款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COST_OTHER_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COST_OTHER_CONFIRM
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --空运
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.AIR_COST_COM_CONFIRM,
              PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_CFM,
              PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_GEN,
              PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_CFM,
              PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_NCFM,
              PKG_STL_VCH_COMMON.AIR_COST_OTHER_CONFIRM,
              PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
              PKG_STL_VCH_COMMON.OTH_ENTRY,
              PKG_STL_VCH_COMMON.OTH_RCV_CH,
              PKG_STL_VCH_COMMON.OTH_RCV_CD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_DR_CH,
              PKG_STL_VCH_COMMON.AIR_DR_CD,
              PKG_STL_VCH_COMMON.AIR_DR_WO_OTHER_RCV,
              PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_OTH_RCV,
              PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
              PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
              PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_CH_NPOD,
              PKG_STL_VCH_COMMON.AIR_COD_CD_NPOD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_NPOD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_NPOD,
              PKG_STL_VCH_COMMON.APT_COM,
              PKG_STL_VCH_COMMON.APT_WO_COM_PAY,
              PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
              PKG_STL_VCH_COMMON.BDR_WO_OTH_RCV)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
                                        '凭证生成空运月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_AFR;

  -----------------------------------------------------------------
  -- 始发到达往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_RFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    --始发
    INSERT INTO STV.T_STL_MVR_RFI
      (ID,
       PERIOD,
       -- PRODUCT_CODE,
       -- CUSTOMER_CODE,
       -- CUSTOMER_NAME,
       -- CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       CLAIM_DEST_WO_INCOME,
       CLAIM_WO_DEST_RCV_POD,
       CLAIM_WO_DEST_RCV_NPOD,
       COD_DEST_RCV_POD,
       COD_DEST_RCV_NPOD,
       COD_ORIG_RCV_POD,
       COD_ORIG_RCV_NPOD,
       COD_UR_CH_NPOD,
       COD_UR_CD_NPOD,
       COD_WO_OR_RCV,
       RD_DEST_WO_INCOME,
       RD_WO_DEST_RCV_POD,
       RD_WO_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_POD)
      SELECT SYS_GUID(),
             P_PERIOD,
             -- T.PRODUCT_CODE,
             -- T.CUSTOMER_CODE,
             -- T.CUSTOMER_NAME,
             -- T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG, --始发
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --理赔冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --理赔冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --理赔冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --应付代收货款冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --应付代收货款冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --应付代收货款冲应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --应付代收货款冲应收始发运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --还款代收货款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --还款代收货款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_WO_OR_RCV, --应付代收货款冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME , --退运费冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --退运费_到达部门申请_退运费冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --退运费_到达部门申请_退运费冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --预收客户冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --预收客户冲应收到付运费已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND ((T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --精准卡航
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --精准城运
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --精准汽运(长途)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --精准汽运(短途)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --整车
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --经济快递
                                )AND
             T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                    PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                    PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                    PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                    PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                    PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                    PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                    PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                    PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                    PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                    PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                    PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD)) OR
             (T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                    PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                    PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD)))
       GROUP BY --T.PRODUCT_CODE,
                --T.CUSTOMER_CODE,
                --T.CUSTOMER_NAME,
                --T.CUSTOMER_TYPE,
                 T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME;

    --到达
    INSERT INTO STV.T_STL_MVR_RFI
      (ID,
       PERIOD,
       -- PRODUCT_CODE,
       -- CUSTOMER_CODE,
       -- CUSTOMER_NAME,
       -- CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       CLAIM_DEST_WO_INCOME,
       CLAIM_WO_DEST_RCV_POD,
       CLAIM_WO_DEST_RCV_NPOD,
       COD_DEST_RCV_POD,
       COD_DEST_RCV_NPOD,
       COD_ORIG_RCV_POD,
       COD_ORIG_RCV_NPOD,
       COD_UR_CH_NPOD,
       COD_UR_CD_NPOD,
       COD_WO_OR_RCV,
       RD_DEST_WO_INCOME,
       RD_WO_DEST_RCV_POD,
       RD_WO_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_NPOD,
       CUST_DR_DEST_RCV_POD)
      SELECT SYS_GUID(),
             P_PERIOD,
             -- T.PRODUCT_CODE,
             -- T.CUSTOMER_CODE,
             -- T.CUSTOMER_NAME,
             -- T.CUSTOMER_TYPE,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST, --到达
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_DEST_WO_INCOME, --理赔冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_POD, --理赔冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_DEST_RCV_NPOD, --理赔冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_POD, --应付代收货款冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_DEST_RCV_NPOD, --应付代收货款冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_POD, --应付代收货款冲应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_ORIG_RCV_NPOD, --应付代收货款冲应收始发运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CH_NPOD, --还款代收货款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_UR_CD_NPOD, --还款代收货款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) COD_WO_OR_RCV, --应付代收货款冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_DEST_WO_INCOME, --退运费冲收入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_POD, --退运费_到达部门申请_退运费冲到达应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) RD_WO_DEST_RCV_NPOD, --退运费_到达部门申请_退运费冲到达应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_NPOD, --预收客户冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_DEST_RCV_POD --预收客户冲应收到付运费已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND ((T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --精准卡航
                                PKG_STL_COMMON.PRODUCT_CODE_FSF, --精准城运
                                PKG_STL_COMMON.PRODUCT_CODE_LRF, --精准汽运(长途)
                                PKG_STL_COMMON.PRODUCT_CODE_SRF, --精准汽运(短途)
                                PKG_STL_COMMON.PRODUCT_CODE_WVH, --整车
                                PKG_STL_COMMON.PRODUCT_CODE_PKG --落地配
                                )AND
             T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
                    PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
                    PKG_STL_VCH_COMMON.COD_WO_OR_RCV,
                    PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME,
                    PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                    PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                    PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                    PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                    PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME,
                    PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD,
                    PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD,
                    PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD,
                    PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD)) OR
             (T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD,
                    PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD,
                    PKG_STL_VCH_COMMON.COD_DEST_RCV_POD,
                    PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD)))
       GROUP BY --T.PRODUCT_CODE,
                --T.CUSTOMER_CODE,
                --T.CUSTOMER_NAME,
                --T.CUSTOMER_TYPE,
                 T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RFI',
                                        '凭证生成始发专线往来月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_RFI;

  -----------------------------------------------------------------
  -- 始发偏线往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_PLI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_PLI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_PLI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       PL_COST_WO_DEST_RCV_POD,
       PL_COST_WO_DEST_RCV_NPOD,
       PL_DR_WO_DEST_RCV_POD,
       PL_DR_WO_DEST_RCV_NPOD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --应付偏线代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --应付偏线代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --预收偏线代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD --预收偏线代理冲应收到付运费未签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- 偏线
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME;

    INSERT INTO STV.T_STL_MVR_PLI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       PL_COST_WO_DEST_RCV_POD,
       PL_COST_WO_DEST_RCV_NPOD,
       PL_DR_WO_DEST_RCV_POD,
       PL_DR_WO_DEST_RCV_NPOD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_PL,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_POD, --应付偏线代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_COST_WO_DEST_RCV_NPOD, --应付偏线代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_POD, --预收偏线代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) PL_DR_WO_DEST_RCV_NPOD --预收偏线代理冲应收到付运费未签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- 偏线
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_PLI',
                                        '凭证生成始发偏线往来月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_PLI;

  -----------------------------------------------------------------
  -- 始发空运往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_AFI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_AFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_AFI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       AIR_DR_DEST_RCV_POD,
       AIR_DR_DEST_RCV_NPOD,
       AIR_DR_WO_COD_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
       AIR_PR_OT_WO_DEST_RCV_POD,
       AIR_PR_OTH_WO_DEST_RCV_NPOD,
       AIR_COD_CH_POD,
       AIR_COD_CD_POD,
       AIR_COD_POD_WO_COD,
       AIR_COD_NPOD_WO_COD,
       AIR_COD_POD_NWO_COD,
       AIR_COD_NPOD_NWO_COD,
       AIR_COD_WO_AGENCY_PAY_POD,
       AIR_COD_WO_OTH_PAY_COD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --预收空运代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --预收空运代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --空运还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --空运还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --空运签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --空运反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_NWO_COD, --空运签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_NWO_COD, --空运反签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --空运到达代理应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD --空运其他应付冲应收代收货款已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
              PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
              PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME;

    INSERT INTO STV.T_STL_MVR_AFI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       UR_DEST_CH_NPOD,
       UR_DEST_CD_NPOD,
       UR_DEST_CH_POD,
       UR_DEST_CD_POD,
       AIR_DR_DEST_RCV_POD,
       AIR_DR_DEST_RCV_NPOD,
       AIR_DR_WO_COD_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_POD,
       AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
       AIR_PR_OT_WO_DEST_RCV_POD,
       AIR_PR_OTH_WO_DEST_RCV_NPOD,
       AIR_COD_CH_POD,
       AIR_COD_CD_POD,
       AIR_COD_POD_WO_COD,
       AIR_COD_NPOD_WO_COD,
       AIR_COD_POD_NWO_COD,
       AIR_COD_NPOD_NWO_COD,
       AIR_COD_WO_AGENCY_PAY_POD,
       AIR_COD_WO_OTH_PAY_COD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_AIR,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_POD, --预收空运代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_DEST_RCV_NPOD, --预收空运代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CH_POD, --空运还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_CD_POD, --空运还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_WO_COD, --空运签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_WO_COD, --空运反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_POD_NWO_COD, --空运签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_NPOD_NWO_COD, --空运反签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_AGENCY_PAY_POD, --空运到达代理应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) AIR_COD_WO_OTH_PAY_COD --空运其他应付冲应收代收货款已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.AIR_COD_CH_POD,
              PKG_STL_VCH_COMMON.AIR_COD_CD_POD,
              PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
                                        '凭证生成始发空运往来月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_AFI;

  -----------------------------------------------------------------
  -- 退代收货款报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
                                   P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                   P_END_DATE   DATE -- 结束时间 2012-12-22
                                   ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_RESULT BOOLEAN;

  BEGIN

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVR_RETURN_COD T WHERE T.PAYMENT_DATE >= :P_BEGIN_DATE AND T.PAYMENT_DATE <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    V_RESULT := PKG_STL_VCH_DAILY_RETURN_COD.FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD,
                                                                          P_BEGIN_DATE,
                                                                          P_END_DATE);

    COMMIT;

    RETURN V_RESULT;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD',
                                        '凭证生成代收货款报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_RETURN_COD;

  -----------------------------------------------------------------
  -- 落地配月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LDD T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_LDD
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       LAND_COST_AGENCY_GEN,
       LAND_COST_AGENCY_CFM,
       LAND_COST_AGENCY_NCFM,
       LAND_COST_OTHER_CONFIRM,
       LAND_COST_PAY_APPLY,
       LAND_OTH_ENTRY,
       LAND_OTH_RCV_CH,
       LAND_OTH_RCV_CD,
       LAND_COD_CH_POD,
       LAND_COD_CD_POD,
       LAND_COD_POD_WO_COD,
       LAND_COD_NPOD_WO_COD,
       LAND_COD_CH_NPOD,
       LAND_COD_CD_NPOD,
       LAND_COD_WO_AGENCY_PAY_POD,
       LAND_COD_WO_OTH_PAY_COD,
       LAND_COD_WO_AGENCY_PAY_NPOD,
       LAND_COD_WO_OTH_NPOD,
       LAND_BDR_WO_OTH_RCV,
       LAND_UR_DEST_CH_NPOD,
       LAND_UR_DEST_CD_NPOD,
       LAND_UR_DEST_CH_POD,
       LAND_UR_DEST_CD_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_NP,
       LAND_PR_OT_WO_DEST_RCV_POD,
       LAND_PR_OTH_WO_DEST_RCV_NPOD,
       LAND_PR_OTH_WO_OTH_RCV,
       LAND_DR_DEST_RCV_POD,
       LAND_DR_DEST_RCV_NPOD,
       LAND_DR_CH,
       LAND_DR_CD,
       LAND_DR_WO_OTHER_RCV,
       LAND_DR_WO_COD_RCV_POD,
       LAND_DR_WO_COD_RCV_NPOD,
       LAND_DR_PAY_APPLY,
       LAND_APT_COM,
       LAND_APT_WO_COM_PAY,
       LAND_APT_WO_OTH_PAY)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_AGENCY_GEN, --落地配代理应付生成
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_AGENCY_CFM, --落地配代理应付成本确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_AGENCY_NCFM, --落地配代理应付成本反确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_OTHER_CONFIRM, --其它应付成本确认
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COST_PAY_APPLY, --落地配成本付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_ENTRY, --落地配其他应收录入
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_RCV_CH, --还款落地配其他应收现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_OTH_RCV_CD, --还款落地配其他应收银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_NPOD, --落地配还款代收货款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_NPOD, --落地配还款代收货款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_NPOD, --落地配到达代理应付冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_NPOD, --落地配其他应付冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_BDR_WO_OTH_RCV, --坏账冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_OTH_RCV, --其他应付冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_CH,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_CH, --预收落地配代理现金
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_CD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_CD, --预收落地配代理银行
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_OTHER_RCV, --预收落地配代理冲其他应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD, --预收落地配代理冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_NPOD, --预收落地配代理冲应收代收货款未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_PAY_APPLY, --落地配退预收付款申请
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_COM,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_COM, --预付落地配公司
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_WO_COM_PAY, --预付冲应付落地配公司
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_APT_WO_OTH_PAY --预付冲其他应付
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG --落地配
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN,
              PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
              PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
              PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
              PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
              PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
              PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
              PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
              PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
              PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
              PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
              PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
              PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
              PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_CH,
              PKG_STL_VCH_COMMON.LAND_DR_CD,
              PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
              PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
              PKG_STL_VCH_COMMON.LAND_APT_COM,
              PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
              PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;
    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
                                        '凭证生成落地配月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_LDD;

  -----------------------------------------------------------------
  -- 落地配往来月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LDI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_LDI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       LAND_COD_CH_POD,
       LAND_COD_CD_POD,
       LAND_COD_POD_NWO_COD,
       LAND_COD_NPOD_NWO_COD,
       LAND_COD_POD_WO_COD,
       LAND_COD_NPOD_WO_COD,
       LAND_COD_WO_AGENCY_PAY_POD,
       LAND_COD_WO_OTH_PAY_COD,
       LAND_UR_DEST_CH_NPOD,
       LAND_UR_DEST_CD_NPOD,
       LAND_UR_DEST_CH_POD,
       LAND_UR_DEST_CD_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_NP,
       LAND_PR_OT_WO_DEST_RCV_POD,
       LAND_PR_OTH_WO_DEST_RCV_NPOD,
       LAND_DR_DEST_RCV_POD,
       LAND_DR_DEST_RCV_NPOD,
       LAND_DR_WO_COD_RCV_POD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.ORIG_ORG_CODE,
             T.ORIG_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_NWO_COD, --落地配签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_NWO_COD, --落地配反签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配到达代理应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD --预收落地配代理冲应收代收货款已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
              PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
              PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
              PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.ORIG_ORG_CODE,
                T.ORIG_ORG_NAME;

    INSERT INTO STV.T_STL_MVR_LDI
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORG_CODE,
       ORG_NAME,
       ORG_TYPE,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       LAND_COD_CH_POD,
       LAND_COD_CD_POD,
       LAND_COD_POD_NWO_COD,
       LAND_COD_NPOD_NWO_COD,
       LAND_COD_POD_WO_COD,
       LAND_COD_NPOD_WO_COD,
       LAND_COD_WO_AGENCY_PAY_POD,
       LAND_COD_WO_OTH_PAY_COD,
       LAND_UR_DEST_CH_NPOD,
       LAND_UR_DEST_CD_NPOD,
       LAND_UR_DEST_CH_POD,
       LAND_UR_DEST_CD_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_POD,
       LAND_PR_AGENCY_WO_DEST_RCV_NP,
       LAND_PR_OT_WO_DEST_RCV_POD,
       LAND_PR_OTH_WO_DEST_RCV_NPOD,
       LAND_DR_DEST_RCV_POD,
       LAND_DR_DEST_RCV_NPOD,
       LAND_DR_WO_COD_RCV_POD)
      SELECT SYS_GUID(),
             P_PERIOD,
             T.PRODUCT_CODE,
             T.CUSTOMER_CODE,
             T.CUSTOMER_NAME,
             T.CUSTOMER_TYPE,
             T.DEST_ORG_CODE,
             T.DEST_ORG_NAME,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_PKG,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_NWO_COD, --落地配签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_NWO_COD, --落地配反签收时未核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配到达代理应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CH_POD, --还款现金已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_UR_DEST_CD_POD, --还款银行已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_DR_WO_COD_RCV_POD --预收落地配代理冲应收代收货款已签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
              PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
              PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
              PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
              PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
              PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD)
       GROUP BY T.PRODUCT_CODE,
                T.CUSTOMER_CODE,
                T.CUSTOMER_NAME,
                T.CUSTOMER_TYPE,
                T.DEST_ORG_CODE,
                T.DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
                                        '凭证生成落地配往来月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_LDI;

  -----------------------------------------------------------------
  -- 营业部核销月报表入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
                            P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                            P_END_DATE   DATE -- 结束时间 2012-12-22
                            ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS

    V_BEGIN_PERIOD VARCHAR2(50);
    V_END_PERIOD   VARCHAR2(50);

  BEGIN

    V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');

    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LWO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
      USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;

    INSERT INTO STV.T_STL_MVR_LWO
      (ID,
       PERIOD,
       PRODUCT_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       VOUCHER_BEGIN_TIME,
       VOUCHER_END_TIME,
       CUST_DR_ORIG_LAND_RCV_NPOD,
       CUST_DR_ORIG_LAND_RCV_POD,
       CLAIM_WO_ORIG_LAND_RCV_POD,
       CLAIM_WO_ORIG_LAND_RCV_NPOD,
       OR_LAND_RCV_WO_CLAIM_PAY,
       OR_LAND_RCV_WO_CUST_DR,
       REFUND_WO_ORIG_LAND_RCV_POD,
       REFUND_WO_ORIG_LAND_RCV_NPOD,
       LAND_CLAIM_WO_ORIG_RCV_POD,
       LAND_CLAIM_WO_ORIG_RCV_NPOD,
       OR_RCV_WO_LAND_CLAIM_PAY,
       LAND_REFUND_WO_ORIG_RCV_POD,
       LAND_REFUND_WO_ORIG_RCV_NPOD)
      SELECT SYS_GUID(),
             P_PERIOD,
             PRODUCT_CODE,
             CUSTOMER_CODE,
             CUSTOMER_NAME,
             CUSTOMER_TYPE,
             ORIG_ORG_CODE,
             ORIG_ORG_NAME,
             DEST_ORG_CODE,
             DEST_ORG_NAME,
             P_BEGIN_DATE,
             P_END_DATE,
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_LAND_RCV_NPOD, --预收客户冲快递应收始发运费未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CUST_DR_ORIG_LAND_RCV_POD, --预收客户冲快递应收始发运费已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_LAND_RCV_POD, --理赔冲快递始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) CLAIM_WO_ORIG_LAND_RCV_NPOD, --理赔冲快递始发应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_LAND_RCV_WO_CLAIM_PAY, --应付理赔冲快递小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_LAND_RCV_WO_CUST_DR, --预收客户冲快递小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) REFUND_WO_ORIG_LAND_RCV_POD, --退运费冲快递始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) REFUND_WO_ORIG_LAND_RCV_NPOD, --退运费冲快递始发应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_CLAIM_WO_ORIG_RCV_POD, --快递理赔冲始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_CLAIM_WO_ORIG_RCV_NPOD, --快递理赔冲始发应收未签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
                            NVL(AMOUNT, 0),
                            0)),
                 0) OR_RCV_WO_LAND_CLAIM_PAY, --快递应付理赔冲小票应收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_REFUND_WO_ORIG_RCV_POD, --快递退运费冲始发应收已签收
             NVL(SUM(DECODE(T.BUSINESS_CASE,
                            PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD,
                            NVL(AMOUNT, 0),
                            0)),
                 0) LAND_REFUND_WO_ORIG_RCV_NPOD --快递退运费冲始发应收未签收
        FROM STV.T_STL_MVS T
       WHERE T.PERIOD >= V_BEGIN_PERIOD
         AND T.PERIOD < V_END_PERIOD
         AND T.BUSINESS_CASE IN
             (PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD,
              PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
              PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
              PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
              PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
              PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
              PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
              PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
              PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
              PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
              PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
              PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD)
       GROUP BY PRODUCT_CODE,
                CUSTOMER_CODE,
                CUSTOMER_NAME,
                CUSTOMER_TYPE,
                ORIG_ORG_CODE,
                ORIG_ORG_NAME,
                DEST_ORG_CODE,
                DEST_ORG_NAME;

    COMMIT;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
                                        V_END_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO',
                                        '凭证生成营业部核销月报表程序异常:' || SQLERRM);

      --返回失败标志
      RETURN FALSE;

  END FUNC_VOUCHER_LWO;

END PKG_STL_VCH_MONTHLY;
/
